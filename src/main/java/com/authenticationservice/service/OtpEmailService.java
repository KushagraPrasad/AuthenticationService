package com.authenticationservice.service;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.authenticationservice.entity.User;
import com.authenticationservice.repository.UserRepository;

@Service
public class OtpEmailService {

	private static final String SMTP_HOST = "smtp.gmail.com";
	private static final String SMTP_PORT = "587";
	private static final String SENDER_EMAIL = "kushagraprasad24@gmail.com";
	private static final String SENDER_PASSWORD = "zjpu tnym ikdq fwkn";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public void sendOtpEmail(String recipientEmail) {
		Optional<User> userOptional = userRepository.findByEmail(recipientEmail);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			String otp = generateOtp();
			user.setOtp(otp);
			user.setOtpExpiration(new Date(System.currentTimeMillis() + 300000)); // 5 minutes expiry
			user.setOtpVerified(false);
			userRepository.save(user);
			sendEmail(recipientEmail, otp, "Your OTP Code");
		} else {
			throw new RuntimeException("User not found with email: " + recipientEmail);
		}
	}

	public void sendResetPasswordOtp(String recipientEmail) {
		sendOtpEmail(recipientEmail);
	}

	public boolean verifyOtp(String email, String otp) {
		Optional<User> userOptional = userRepository.findByEmail(email);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			if (user.getOtp().equals(otp) && user.getOtpExpiration().after(new Date())) {
				user.setOtpVerified(true);
				user.setIsEmailVerified(true);
				userRepository.save(user);
				return true;
			}
		}
		return false;
	}

	public boolean resetPassword(String email, String otp, String newPassword) {
		Optional<User> userOptional = userRepository.findByEmail(email);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			if (user.getOtp().equals(otp) && user.getOtpExpiration().after(new Date())) {
				String encodedPassword = passwordEncoder.encode(newPassword);
				user.setPassword(encodedPassword);
				user.setOtp(null);
				user.setIsEmailVerified(true);
				user.setOtpExpiration(null);
				userRepository.save(user);
				return true;
			}
		}
		return false;
	}

	private String generateOtp() {
		SecureRandom random = new SecureRandom();
		int otp = 100000 + random.nextInt(900000);
		return String.valueOf(otp);
	}

	private void sendEmail(String recipientEmail, String otp, String subject) {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", SMTP_HOST);
		props.put("mail.smtp.port", SMTP_PORT);

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
			}
		});

		try {
			Message message = new MimeMessage(session);
			try {
				message.setFrom(new InternetAddress(SENDER_EMAIL, "OTP Service"));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
			message.setSubject(subject);
			message.setText("Your OTP code is: " + otp);
			Transport.send(message);
			System.out.println("Email sent successfully.");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
