package com.ttknpdev.reducecode.configures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class JwtFilterConfig {

    // it is optional logic , i have to inject , because i got a @Value annotation on JwtFilter
    private JwtFilter jwtFilterOption;

    @Autowired // inject difference bean named
    public JwtFilterConfig(@Qualifier("jwtFilterOption") JwtFilter jwtFilterOption) {
        this.jwtFilterOption = jwtFilterOption;
    }

    @Bean
    public FilterRegistrationBean jwtFilter() {
        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(jwtFilterOption);
        // provide endpoints which needs to be restricted. ** another url won authenticate ex, api/store/
        filter.addUrlPatterns("/api/store/books");
        return filter;
    }
}