package com.beehive.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final UserPrincipalService userPrincipalService;

    public JwtFilter(
            HandlerExceptionResolver handlerExceptionResolver,
            JwtService jwtService,
            UserPrincipalService userPrincipalService) {
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.jwtService = jwtService;
        this.userPrincipalService = userPrincipalService;
    }

    /*
     * Method to lazily fetch the UserService bean from the ApplicationContext
     * This is done to avoid Circular Dependency issues
     */
  /*private UserPrincipalService getUserPrincipalService() {
    return applicationContext.getBean(UserPrincipalService.class);
  }*/

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // Extracting token from the request header
            final Cookie[] cookies = request.getCookies();
            //      String authHeader = request.getHeader("Authorization");
            String token = null;
            String username = null;

      /*if (authHeader != null && authHeader.startsWith("Bearer ")) {
        // Extracting the token from the Authorization header
        token = authHeader.substring(7);
        // Extracting username from the token
        username = jwtService.extractUserName(token);
      }*/

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")) {
                        token = cookie.getValue();
                        username = jwtService.extractUserName(token);
                        break;
                    }
                }
            }

            // If username is extracted and there is no authentication in the current SecurityContext
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Loading UserDetails by username extracted from the token
                UserDetails userDetails = userPrincipalService.loadUserByUsername(username);

                // Validating the token with loaded UserDetails
                if (jwtService.validateToken(token, userDetails)) {
                    // Creating an authentication token using UserDetails
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                    // Setting authentication details
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // Setting the authentication token in the SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            // Proceeding with the filter chain
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}
