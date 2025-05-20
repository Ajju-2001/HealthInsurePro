package com.healthinsurance.healthproject.hepler;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.healthinsurance.healthproject.personaldetailsrepository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;    
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component 
public class JwtRequestFilter extends OncePerRequestFilter{

    private final UserRepository userRepository;
	
	//jwtUtil for handling JWT operations (like extracting username, validating token).
	private final JwtUtil jwtUtil;
	
	//userDetailsService to load user details from username.
    private final UserDetailsService userDetailsService;  
    
    public JwtRequestFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }
 

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
    	
    	 
    	String path = request.getRequestURI();
        String method = request.getMethod(); 

        // Skip OPTIONS (preflight) requests
        if ("OPTIONS".equalsIgnoreCase(method)) {
            chain.doFilter(request, response);
            return;
        }
        
        if (path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-resources") ||
                path.startsWith("/webjars") ||
                path.contains("favicon.ico")) {
                chain.doFilter(request, response);
                return;
            }
    	

    	//Retrieves the Authorization header from the HTTP request (where the JWT token is typically sent).
        String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        Integer userId = null;
        String email = null;
        String role = null;
        String jwt = null;
      


        System.err.println("token >>> "+authorizationHeader);
        
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);//extracts the JWT token by removing "Bearer " prefix (the first 7 chars).
            try {
                username = jwtUtil.extractUsername(jwt);   
                userId = jwtUtil.extractUserId(jwt);
                email = jwtUtil.extractEmail(jwt);
                role = jwtUtil.extractRole(jwt);
                
             System.err.println("username ####>> "+username);
           
             System.err.println("userId ####>> "+userId);
             System.err.println("email $$$$ >> "+email);
             System.err.println("role ####>> "+role);

             
             
             
            } catch (ExpiredJwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Token expired\"}");
                return;  
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Invalid token\"}"); 
                return; 
            }
        }
 
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            System.err.println("userDetails >>> "+userDetails); 
//            System.err.println("true >>> "+jwtUtil.validateToken(jwt, userId, username,email, role)); 
//            if (jwtUtil.validateToken(jwt,userId ,username,email, role)) {
//                UsernamePasswordAuthenticationToken authToken =
//                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                SecurityContextHolder.getContext().setAuthentication(authToken); 
//                //Set this authentication token in the SecurityContextHolder — meaning the user is now authenticated for this request.
//            }
//        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            System.err.println("userDetails >>> "+userDetails); 
//            System.err.println("true >>> "+jwtUtil.validateToken(jwt, userId, username,email, role)); 
            if (username.equals(userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        System.err.println("request    "+request);
        System.err.println("response -----"+response);
       chain.doFilter(request, response);
        //Passes the request and response along the filter chain so other filters or controllers can process it.
    }
    
   
    
}
