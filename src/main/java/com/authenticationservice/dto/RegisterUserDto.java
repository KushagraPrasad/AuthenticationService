package com.authenticationservice.dto;

public class RegisterUserDto {

	private String email;
	private String password;
	private String firstname;
	private String lastname;
	private String mobile;
	private String role;

	public String getEmail() {
		return email;
	}

	public RegisterUserDto setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public RegisterUserDto setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getFirstName() {
		return firstname;
	}

	public RegisterUserDto setFirstName(String firstname) {
		this.firstname = firstname;
		return this;
	}

	public String getLastName() {
		return lastname;
	}

	public RegisterUserDto setLastName(String lastname) {
		this.lastname = lastname;
		return this;
	}

	public String getMobile() {
		return mobile;
	}

	public RegisterUserDto setMobile(String mobile) {
		this.mobile = mobile;
		return this; // Return the instance of RegisterUserDto
	}

	public String getRole() {
		return role;
	}

	public RegisterUserDto setRole(String role) {
		this.role = role;
		return this;
	}

	public RegisterUserDto(String email, String password, String firstname, String lastname, String mobile,
			String role) {
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.mobile = mobile;
		this.role = role;
	}

	public RegisterUserDto() {
	}
}
