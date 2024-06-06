package com.authenticationservice.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authenticationservice.dto.LoginResponse;
import com.authenticationservice.dto.LoginUserDto;
import com.authenticationservice.dto.RegisterUserDto;
import com.authenticationservice.entity.User;
import com.authenticationservice.service.AuthenticationService;
import com.authenticationservice.service.JwtService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

	private final JwtService jwtService;

	private final AuthenticationService authenticationService;

	public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
		this.jwtService = jwtService;
		this.authenticationService = authenticationService;
	}

	@PostMapping("/signup")
	public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
		User registeredUser = authenticationService.signup(registerUserDto);
		return ResponseEntity.ok(registeredUser);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
		Optional<User> authenticatedUser = authenticationService.authenticate(loginUserDto);
		User user = authenticatedUser.orElseThrow(() -> new UsernameNotFoundException("Invalid login credentials"));
		String jwtToken = jwtService.generateToken(user);
		long expiresIn = jwtService.getExpirationTime();
		LoginResponse loginResponse = new LoginResponse(jwtToken, expiresIn, user.getEmail(), user.getFirstName(),
				user.getLastName(), user.getMobile(), user.getRole().getName().toString());

		return ResponseEntity.ok(loginResponse);
	}
}