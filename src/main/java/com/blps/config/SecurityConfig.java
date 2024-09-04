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

/*
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

        private Map<String, AuthUser> users = new HashMap<>();

        @PostConstruct
        public void init() {
                try {
                        File file = ResourceUtils.getFile("classpath:users.xml");
                        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                        org.w3c.dom.Document document = builder.parse(file);
                        org.w3c.dom.NodeList nodeList = document.getElementsByTagName("user");
                        for (int i = 0; i < nodeList.getLength(); i++) {
                                org.w3c.dom.Node node = nodeList.item(i);
                                if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                                        org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                                        String username = element.getElementsByTagName("username").item(0).getTextContent();
                                        String password = element.getElementsByTagName("password").item(0).getTextContent();
                                        String roles = element.getElementsByTagName("roles").item(0).getTextContent();
                                        users.put(username, new AuthUser(username, password, roles));
                                }
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                        .csrf().disable()
                        .authorizeHttpRequests(requests -> requests
                                .requestMatchers("/api/buyer/**")
                                .hasAuthority(UserRole.BUYER.name())
                                .requestMatchers("/api/owner/**")
                                .hasAuthority(UserRole.OWNER.name())
                                .anyRequest().authenticated())

                        .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(authenticationEntryPoint()));
                return http.build();
        }

 */

/*

        @Bean
        public BasicAuthenticationEntryPoint authenticationEntryPoint() {
                BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
                entryPoint.setRealmName("demo-realm");
                return entryPoint;
        }

        @Bean
        public UserDetailsService userDetailsService() {
                InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
                for (AuthUser user : users.values()) {
                        manager.createUser(User.withDefaultPasswordEncoder()
                                .username(user.getUsername())
                                .password(user.getPassword())
                                .roles(user.getUserRole().split(","))
                                .build());
                }
                return manager;
        }
}

 */

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

