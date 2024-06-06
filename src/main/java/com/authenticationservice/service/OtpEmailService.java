package com.authenticationservice.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class OtpEmailService {

	private static final String SMTP_HOST = "smtp.gmail.com";
	private static final String SMTP_PORT = "587";
	private static final String SENDER_EMAIL = "kushagraprasad24@gmail.com";
	private static final String SENDER_PASSWORD = "zjpu tnym ikdq fwkn ";

	public void sendOtpEmail(String recipientEmail) {

		String otp = generateOtp();
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
			message.setFrom(new InternetAddress(SENDER_EMAIL));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
			message.setSubject("Your OTP Code");
			message.setText("Your OTP code is: " + otp);

			Transport.send(message);
			System.out.println("Email sent successfully.");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	private static String generateOtp() {
		int otp = 100000 + new java.util.Random().nextInt(900000);
		return String.valueOf(otp);
	}
}