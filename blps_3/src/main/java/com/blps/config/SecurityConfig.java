package com.blps.config;

import com.blps.entity.Role;
import com.blps.filters.AuthFilter;
import com.blps.security.MyBasicAuthenticationEntryPoint;
import com.blps.security.UserRole;
//import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Autowired
        private AuthFilter authFilter;

        @Autowired
        private MyBasicAuthenticationEntryPoint authenticationEntryPoint;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                        .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                        .csrf().disable()
                        .authorizeHttpRequests(requests -> requests
                                .antMatchers("/auth/**")
                                .permitAll()
                                .antMatchers("/api/buyer/**")
                                .hasAuthority(Role.BUYER.name())
                                .antMatchers("/api/owner/**")
                                .hasAuthority(Role.OWNER.name())
                                .anyRequest()
                                .permitAll())
                        .httpBasic().authenticationEntryPoint(authenticationEntryPoint);
                return http.build();
        }
}

