package com.authenticationservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.authenticationservice.dto.RegisterUserDto;
import com.authenticationservice.entity.Role;
import com.authenticationservice.entity.RoleEnum;
import com.authenticationservice.entity.User;
import com.authenticationservice.repository.RoleRepository;
import com.authenticationservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserServices {

	private UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public List<User> showAll() {
		return userRepository.findAll();
	}

	public User updateUser(User user, Long id) {

		Optional<User> existingUserOptional = userRepository.findById(id);

		if (existingUserOptional.isPresent()) {
			// Update the existing users fields with the new values
			User existingUser = existingUserOptional.get();
			existingUser.setFirstName(user.getFirstName());
			existingUser.setEmail(user.getEmail());
			existingUser.setMobile(user.getMobile());
			return userRepository.save(existingUser);
		} else {
			throw new RuntimeException("Entered invalid user id, please enter a valid user id.");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOptional = userRepository.findByEmail(username);
		return userOptional
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
	}

	@Override
	public User addUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User createAdministrator(RegisterUserDto input) {
		if (userRepository.findByEmail(input.getEmail()).isPresent()) {
			throw new IllegalArgumentException("User with email " + input.getEmail() + " already exists");
		}

		Optional<Role> adminRoleOptional = roleRepository.findByName(RoleEnum.ADMIN);
		Role adminRole = adminRoleOptional.orElseThrow(() -> new IllegalStateException("Admin role not found"));

		User user = new User();
		user.setFirstName(input.getFirstName());
		user.setLastName(input.getLastName());
		user.setEmail(input.getEmail());
		user.setMobile(input.getMobile());
		user.setPassword(passwordEncoder.encode(input.getPassword()));
		user.setRole(adminRole);

		return userRepository.save(user);
	}
}
