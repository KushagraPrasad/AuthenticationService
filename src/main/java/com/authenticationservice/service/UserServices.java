package com.authenticationservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.authenticationservice.dto.RegisterUserDto;
import com.authenticationservice.entity.User;

@Service
public interface UserServices extends UserDetailsService {

	public User addUser(User user);

	public List<User> showAll();

	public User createAdministrator(RegisterUserDto input);

	public User updateUser(User user, Long id);

	public Optional<User> findByEmail(String email);

}
