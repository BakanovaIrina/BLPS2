package com.blps.filters;

import com.blps.entity.Role;
import com.blps.entity.User;
import com.blps.services.MyUserDetailsService;
import com.blps.services.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private AuthService authService;

    @Autowired
    MyUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !isHeaderVaild(header)) {
            filterChain.doFilter(request, response);
            return;
        }

        User user = authService.parseAuthHeader(header);
        //authService.registerUser(user, Role.OWNER);
        UserDetails userDetails;

        try {
            userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        }
        catch (UsernameNotFoundException e){
            filterChain.doFilter(request, response);
            return;
        }


        if (!authService.authenticateUser(user)) {
            filterChain.doFilter(request, response);
            return;
        }


        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken
                (userDetails, null, userDetails.getAuthorities());


        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        filterChain.doFilter(request, response);
    }

    private boolean isHeaderVaild(String header) {
        String[] splittedHeader = header.split(":");

        return splittedHeader.length == 2;
    }

}



