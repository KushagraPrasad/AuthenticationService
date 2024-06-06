package com.authenticationservice.bootstrap;

import java.util.Optional;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.authenticationservice.dto.RegisterUserDto;
import com.authenticationservice.entity.Role;
import com.authenticationservice.entity.RoleEnum;
import com.authenticationservice.entity.User;
import com.authenticationservice.repository.RoleRepository;
import com.authenticationservice.repository.UserRepository;

@Component
@DependsOn("roleRepository")
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {

	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public AdminSeeder(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		createSuperAdministrator();
	}

	private void createSuperAdministrator() {
		RegisterUserDto userDto = new RegisterUserDto();
		userDto.setFirstName("Admin");
		userDto.setLastName("role");
		userDto.setEmail("admin@email.com");
		userDto.setPassword("123456");
		userDto.setMobile("9867556423");
		userDto.setRole("ADMIN");

		Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.ADMIN);
		Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail());

		if (optionalRole.isPresent() && !existingUser.isPresent()) {
			Role role = optionalRole.get();
			User newUser = new User();
			newUser.setFirstName(userDto.getFirstName());
			newUser.setLastName(userDto.getLastName());
			newUser.setEmail(userDto.getEmail());
			newUser.setMobile(userDto.getMobile());
			newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
			newUser.setRole(role);

			userRepository.save(newUser);
		}
	}
}