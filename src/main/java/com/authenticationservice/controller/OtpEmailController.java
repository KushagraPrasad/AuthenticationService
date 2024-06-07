package com.authenticationservice.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.authenticationservice.dto.OtpVerificationDto;
import com.authenticationservice.dto.PasswordResetDto;
import com.authenticationservice.dto.RecipientEmailDto;
import com.authenticationservice.entity.User;
import com.authenticationservice.repository.UserRepository;
import com.authenticationservice.service.OtpEmailService;

@RestController
public class OtpEmailController {

	@Autowired
	private OtpEmailService otpEmailService;

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/sendOtp")
	public String sendOtpEmail(@RequestBody RecipientEmailDto recipientEmailDto) {
		try {
			otpEmailService.sendOtpEmail(recipientEmailDto.getRecipientEmail());
			return "OTP email sent successfully";
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed to send OTP email: " + e.getMessage();
		}
	}

	@PostMapping("/verifyOtp")
	public String verifyOtp(@RequestBody OtpVerificationDto otpVerificationDto) {
		Optional<User> userOptional = userRepository.findByEmail(otpVerificationDto.getEmail());
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			String storedOtp = user.getOtp();
			if (storedOtp.equals(otpVerificationDto.getOtp())) {
				user.setOtp(null);
				user.setIsEmailVerified(true);
				user.setOtpExpiration(null);
				userRepository.save(user);
				return "OTP verified successfully";
			} else {
				return "Incorrect OTP";
			}
		} else {
			return "User not found with email: " + otpVerificationDto.getEmail();
		}
	}

	@PostMapping("/requestPasswordReset")
	public String requestPasswordReset(@RequestBody RecipientEmailDto recipientEmailDto) {
		try {
			otpEmailService.sendResetPasswordOtp(recipientEmailDto.getRecipientEmail());
			return "Password reset OTP email sent successfully";
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed to send password reset OTP email: " + e.getMessage();
		}
	}

	@PostMapping("/resetPassword")
	public String resetPassword(@RequestBody PasswordResetDto passwordResetDto) {
		boolean isReset = otpEmailService.resetPassword(passwordResetDto.getEmail(), passwordResetDto.getOtp(),
				passwordResetDto.getNewPassword());
		if (isReset) {
			return "Password reset successfully";
		} else {
			return "Failed to reset password";
		}
	}
}
