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
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
                http
                        .csrf().disable()
                        .authorizeRequests()
                        .antMatchers("/api/admin/**").hasRole("ADMIN")
                        .antMatchers("/api/seller/**").hasRole("SELLER")
                        .antMatchers("/api/buyer/**").hasRole("BUYER")
                        .antMatchers("/api/public/**").permitAll()
                        .anyRequest().authenticated()
                        .and()
                        .httpBasic();
        }

//        @Bean
//        public PasswordEncoder passwordEncoder() {
//                return new BCryptPasswordEncoder();
//        }
}

