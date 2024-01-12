package com.lelisay.CooPayroll10.coremodule.user.security.jwt;


import com.lelisay.CooPayroll10.coremodule.user.security.UserRegistrationDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAutheticationFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final UserRegistrationDetailsService userRegistrationDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token =null;
        String userName = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
             userName = jwtService.extractUsernameFromToken(token);
        }

        //explain if user is autheticated [we have user in database]
        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userRegistrationDetails = userRegistrationDetailsService.loadUserByUsername(userName);
            if(jwtService.validateToken(token,userRegistrationDetails)){
                var authToken = new UsernamePasswordAuthenticationToken(userRegistrationDetails,
                        null,userRegistrationDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
