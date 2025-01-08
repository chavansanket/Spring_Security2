package com.project2.sec.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//This filter is responsible for checking and validating the JWT on each request that requires authentication.

//It extends the OncePerRequestFilter class, which ensures that the doFilterInternal method is only invoked once per request.


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private JWTTokenProvider jwtTokenProvider;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	
	
	//Constructor
    public JwtAuthenticationFilter(JWTTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }
    

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    	// Exclude login and public endpoints from JWT validation
        String path = request.getRequestURI();
        return path.startsWith("/api/auth/");
//    	return super.shouldNotFilter(request);
    }

    // This method is executed for every request intercepted by the filter.
    //And, it extract the token from the request header and validate the token.
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	    try {
			//Get JWT token from HTTP request
			String token = getTokenFromRequest(request);
			System.out.println("do.Filter.Internal: ");
			
			//Validate Token
			if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
				//get username from token
				String username = jwtTokenProvider.getUsername(token);
				
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					 userDetails, 
					 null,
					 userDetails.getAuthorities()
				);
				
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				
			}


	        // Your JWT authentication logic here
	        filterChain.doFilter(request, response);
	    } catch (Exception ex) {
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        response.setContentType("application/json");
	        Map<String, String> error = new HashMap<>();
	        error.put("error", ex.getMessage());
	        new ObjectMapper().writeValue(response.getWriter(), error);
	    }
		
	}
	
	//Extract the token
	private String getTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		
		if(StringUtils.hasText(bearerToken) &&bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		
		return null;
	}

}
