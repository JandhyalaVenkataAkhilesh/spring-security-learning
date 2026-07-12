package com.example.module5.filter;

import com.example.module5.entities.User;
import com.example.module5.service.JWTService;
import com.example.module5.service.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserServiceImpl userService;
    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // From Header I fetch token. Header contains key value pair
            //her Authorization is key and requestToken store the value of key
            final String requestToken = request.getHeader("Authorization");
            //Every token starts in the format : Bearer Token
            //Example "Bearer klsjd094rnlkdsjlsd.kldjfsd023-4.dosjfosdfssdf"

            //now what you have to do here is you have to check is the request token
            // is null or not starts with Bearer that means there is missing of header, wrong format
            // or not a Bearer token. Then we have to move to next filter
            if(requestToken==null || !requestToken.startsWith("Bearer")){
                filterChain.doFilter(request,response);
                return;
            }
            // Get the exact token from request header(requestToken) but avoiding Bearer in the token
            final String token = requestToken.split("Bearer ")[1];
            //Extract the user id stored inside the JWT token
            final Long id = jwtService.getUserId(token);
            // If user id exists and no authentication is present,
            // load the user and create an Authentication object.
            // store that Authentication inside the SecurityContext
            //if authentication present then it  will skip the below if statement
            if(id!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                User user = userService.getUserById(id);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user,null,null);
                //Before sending the token i want to send extra details
                //It will contain about Ip address, session related data about the user
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            //after completion of this requested filter. we have to go to next filter
            filterChain.doFilter(request,response);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request,response,null,e);
        }

    }
}
