package com.authenticationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.authenticationservice.dto.RecipientEmailDto;
import com.authenticationservice.service.OtpEmailService;

@RestController
public class OtpEmailController {

	@Autowired
	private OtpEmailService otpEmailService;

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
}
