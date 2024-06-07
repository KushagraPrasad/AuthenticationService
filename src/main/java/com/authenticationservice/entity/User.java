package com.authenticationservice.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User implements UserDetails, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(nullable = false)
	private String firstname;

	@Column(nullable = false)
	private String lastname;

	@Column(unique = true, length = 100, nullable = false)
	private String email;

	@Column(unique = true, nullable = false)
	private String mobile;

	@Column(nullable = false)
	private String password;

	@CreationTimestamp
	@Column(updatable = false)
	private Date createdAt;

	@UpdateTimestamp
	private Date updatedAt;

	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
	private Role role;

	@Column(nullable = false)
	private boolean isEmailVerified = false;

	@Column(nullable = true)
	private String otp;

	@Column(nullable = false)
	private boolean otpVerified = false;

	@Column(nullable = true)
	private Date otpExpiration;

	public User() {
		this.isEmailVerified = false;
		this.otpVerified = false;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstname;
	}

	public User setFirstName(String firstname) {
		this.firstname = firstname;
		return this;
	}

	public String getLastName() {
		return lastname;
	}

	public User setLastName(String lastname) {
		this.lastname = lastname;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getMobile() {
		return mobile;
	}

	public User setMobile(String mobile) {
		this.mobile = mobile;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Role getRole() {
		return role;
	}

	public User setRole(Role role) {
		this.role = role;
		return this;
	}

	public boolean getIsEmailVerified() {
		return isEmailVerified;
	}

	public void setIsEmailVerified(boolean isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public boolean getOtpVerified() {
		return otpVerified;
	}

	public void setOtpVerified(boolean otpVerified) {
		this.otpVerified = otpVerified;
	}

	public Date getOtpExpiration() {
		return otpExpiration;
	}

	public void setOtpExpiration(Date otpExpiration) {
		this.otpExpiration = otpExpiration;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.getName().toString()));
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
