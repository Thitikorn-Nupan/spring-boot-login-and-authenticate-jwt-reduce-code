package com.ttknpdev.reducecode.service;

import com.ttknpdev.reducecode.entities.User;
import com.ttknpdev.reducecode.repository.UserRepository;
import com.ttknpdev.reducecode.service.jwt.JwtGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private JwtGenerator jwtGenerator;
    private Logger logger;

    @Autowired
    public UserService(UserRepository userRepository, JwtGenerator jwtGenerator) {
        this.userRepository = userRepository;
        this.jwtGenerator = jwtGenerator;
        passwordEncoder = new BCryptPasswordEncoder();
        logger = LoggerFactory.getLogger(UserService.class);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Map<String, String> userLoggedInAndGetToken(User user) {
        User findUser = getUserByUsername(user.getUsername());
        if (findUser != null) {
            logger.info("find user : {} ", findUser.getUsername());
            boolean encodedPassword = passwordEncoder.matches(user.getPassword(), findUser.getPassword());
            if (encodedPassword) {
                logger.info("user : {} logged in", findUser.getUsername());
                return jwtGenerator.generateToken(findUser.getUsername());
            }
        }
        return null;
    }

    /**
    public Boolean authenticationToken(String token) {
        try {
            Claims claims = jwtGenerator.getAllClaimsFromToken(token);
            return true;
        } catch (ExpiredJwtException expiredJwtException) {
            throw new TokenExpiredNotAllowed("Token expired");
        }
    }
    */

}
