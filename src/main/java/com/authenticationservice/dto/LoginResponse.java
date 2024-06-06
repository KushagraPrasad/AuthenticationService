package com.authenticationservice.dto;

public class LoginResponse {

	private String token;

	private long expiresIn;

	private String email;

	private String firstname;

	private String lastname;

	private String mobile;

	private String role;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstname;
	}

	public void setFirstName(String firstname) {
		this.firstname = firstname;
	}

	public String getLastName() {
		return lastname;
	}

	public void setLastName(String lastname) {
		this.lastname = lastname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public LoginResponse(String token, long expiresIn, String email, String firstname, String lastname, String mobile,
			String role) {
		super();
		this.token = token;
		this.expiresIn = expiresIn;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.mobile = mobile;
		this.role = role;
	}

	public LoginResponse() {
		super();
	}

}
