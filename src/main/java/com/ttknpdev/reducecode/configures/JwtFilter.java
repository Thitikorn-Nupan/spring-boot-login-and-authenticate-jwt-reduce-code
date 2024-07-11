package com.ttknpdev.reducecode.configures;


import com.ttknpdev.reducecode.exception.TokenExpiredNotAllowed;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;

@Service("jwtFilterOption") // i have to chang bean name cause jwtFilter exist
public class JwtFilter extends GenericFilterBean {

    private Logger logger;

    @Value("${JWT.SECRET}")
    private String secret;

    public JwtFilter() {
        logger = LoggerFactory.getLogger(JwtFilter.class);
    }

    // The filter is responsible for verifying the JWT token. The filter class extends the GenericFilter class and overrides the doFilter() method.
    // ** this method always do when user request
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        // response this case It's optional
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        // *** will log like this /api/store/books cause ,i set filter.addUrlPatterns("/api/store/books");
        logger.info("request.getRequestURI() : {} ", request.getRequestURI());
        logger.info("response.getStatus() : {} ", response.getStatus()); // it's 200 follow my endpoint

        // ** get value form key Authorization on http header
        final String Authorization = request.getHeader("Authorization");
        logger.info("Authorization : {} ", Authorization);

        if (Authorization == null || !Authorization.startsWith("Bearer ")) {
            // if no token will throw this error
            throw new ServletException("An exception occurred");
        }

        // *** Validate token block
        try {

            // Sub token
            final String TOKEN = Authorization.substring(7);

            Claims claims = Jwts
                    .parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(TOKEN)
                    .getBody();

            Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            logger.info("claims.getSubject() : {} , has token : {}  , expiration time : {}", claims.getSubject(), TOKEN, formatter.format(claims.getExpiration())); // validateToken : true

            /**
            if token is expired getAllClaimsFromToken(TOKEN) will throw error instead
            boolean validateToken = jwtGenerator.validateToken(TOKEN, claims.getSubject());

            if (validateToken) {
                filterChain.doFilter(request, response);
            }
            */

            /**
            work for ??? May it uses for security
            request.setAttribute("claims", claims);
            request.setAttribute("token", TOKEN);
            */

            filterChain.doFilter(request, response);

            // remember Catch will work if exception matched Now i have to catch ExpiredJwtException error (follow my method)
        } catch (ExpiredJwtException expiredJwtException) {
            // expiredJwtException.getMessage() returns JWT expired at 2567-07-10T15:18:30Z. Current time: 2567-07-10T15:22:04Z, a difference of 214734 milliseconds.  Allowed clock skew: 0 milliseconds
            throw new TokenExpiredNotAllowed("Token was expired");
        }
    }
}
