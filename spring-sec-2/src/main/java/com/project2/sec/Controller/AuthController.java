package com.project2.sec.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project2.sec.Dto.AuthResponseDto2;
import com.project2.sec.Dto.LoginDto;
import com.project2.sec.Service.AuthService;

import lombok.AllArgsConstructor;

//AuthController for taking JWT token from the login API

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;

	//Build Login REST API
	@PostMapping("/login")
	public ResponseEntity<AuthResponseDto2> login(@RequestBody LoginDto loginDto){
		
		//01 - Receive the token from AuthService
		String token = authService.login(loginDto);
		
		 //02 - Set the token as a response using JwtAuthResponse Dto class
		AuthResponseDto2 authResponseDto2 = new AuthResponseDto2();
		authResponseDto2.setAccessToken(token);
		
		//03 - Return the response to the user
		return new ResponseEntity<>(authResponseDto2, HttpStatus.OK);
	}
}
