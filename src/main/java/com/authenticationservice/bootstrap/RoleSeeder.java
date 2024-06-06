package com.authenticationservice.bootstrap;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.authenticationservice.entity.Role;
import com.authenticationservice.entity.RoleEnum;
import com.authenticationservice.repository.RoleRepository;

@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {
	private final RoleRepository roleRepository;

	public RoleSeeder(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		this.loadRoles();
	}

	private void loadRoles() {
		RoleEnum[] roleNames = { RoleEnum.EMPLOYEE, RoleEnum.ADMIN, RoleEnum.EMPLOYER };
		Map<RoleEnum, String> roleDescriptionMap = new HashMap<>();
		roleDescriptionMap.put(RoleEnum.EMPLOYEE, "Employee role");
		roleDescriptionMap.put(RoleEnum.ADMIN, "Administrator role");
		roleDescriptionMap.put(RoleEnum.EMPLOYER, "Employer role");

		for (RoleEnum roleName : roleNames) {
			Optional<Role> optionalRole = roleRepository.findByName(roleName);
			if (optionalRole.isEmpty()) {
				Role roleToCreate = new Role();
				roleToCreate.setName(roleName);
				roleToCreate.setDescription(roleDescriptionMap.get(roleName));
				roleRepository.save(roleToCreate);
			} else {
				System.out.println(optionalRole.get());
			}
		}
	}
}