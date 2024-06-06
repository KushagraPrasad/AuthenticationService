package com.authenticationservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authenticationservice.dto.RegisterUserDto;
import com.authenticationservice.entity.User;
import com.authenticationservice.service.UserServices;

@RequestMapping("/admins")
@RestController
public class AdminController {

	private final UserServices userService;

	public AdminController(UserServices userService) {
		this.userService = userService;
	}
 
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> createAdministrator(@RequestBody RegisterUserDto registerUserDto) {
		User createdAdmin = userService.createAdministrator(registerUserDto);
		return ResponseEntity.ok(createdAdmin);
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<User>> showAll() {
		List<User> allUsers = userService.showAll();
		return ResponseEntity.ok(allUsers);
	}

}