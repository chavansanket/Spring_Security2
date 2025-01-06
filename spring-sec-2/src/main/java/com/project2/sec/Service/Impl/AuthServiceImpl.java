package com.project2.sec.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.project2.sec.Dto.LoginDto;
import com.project2.sec.Service.AuthService;
import com.project2.sec.config.JWTTokenProvider;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTTokenProvider jwtTokenProvider;
	
	@Override
	public String login(LoginDto loginDto) {
		
        // 01 - AuthenticationManager is used to authenticate the user
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginDto.getUsername(), 
				loginDto.getPassword()
				));
		
		/* 02 - SecurityContextHolder is used to allows the rest of the application to know that
		 the user is authenticated and can use user data from Authentication object*/
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		 // 03 - Generate the token based on username and secret key
		String token = jwtTokenProvider.generateToken(authentication);
		
        // 04 - Return the token to controller
        return token;
		
		
	}

}
