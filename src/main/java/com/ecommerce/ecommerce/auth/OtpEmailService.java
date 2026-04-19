package com.ecommerce.ecommerce.auth;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class OtpEmailService {

	private static final Logger log = LoggerFactory.getLogger(OtpEmailService.class);

	private final JavaMailSender mailSender;
	private final String smtpUsername;
	private final String smtpPassword;
	private final String fromAddress;
	private final String mailHost;

	public OtpEmailService(
			@Autowired(required = false) JavaMailSender mailSender,
			@Value("${spring.mail.host:}") String mailHost,
			@Value("${spring.mail.username:}") String smtpUsername,
			@Value("${spring.mail.password:}") String smtpPassword,
			@Value("${spring.mail.from:}") String mailFrom) {
		this.mailSender = mailSender;
		this.mailHost = mailHost == null ? "" : mailHost.trim();
		this.smtpUsername = smtpUsername == null ? "" : smtpUsername.trim();
		String rawPass = smtpPassword == null ? "" : smtpPassword.trim();
		this.smtpPassword = isGmailSmtp(this.mailHost) ? normalizeGmailAppPassword(rawPass) : rawPass;
		String from = mailFrom == null ? "" : mailFrom.trim();
		this.fromAddress = from.isEmpty() ? this.smtpUsername : from;
	}

	public OtpSendResult sendPasswordResetOtp(String toEmail, String otp, int validMinutes) {
		if (mailSender == null) {
			log.warn(
					"No JavaMailSender bean. spring.mail.host must be set (e.g. smtp.gmail.com). Current host property: \"{}\"",
					mailHost);
			return OtpSendResult.fail(OtpSendFailure.NO_MAIL_SENDER, "spring.mail.host is missing or mail starter did not configure a sender.");
		}
		if (smtpUsername.isEmpty() || smtpPassword.isEmpty()) {
			log.warn("SMTP username or password is empty. Set MAIL_USERNAME and MAIL_PASSWORD (or spring.mail.*).");
			return OtpSendResult.fail(OtpSendFailure.MISSING_CREDENTIALS, null);
		}
		if (fromAddress.isEmpty()) {
			log.warn("From address is empty. Set spring.mail.username or spring.mail.from.");
			return OtpSendResult.fail(OtpSendFailure.MISSING_CREDENTIALS, null);
		}
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, StandardCharsets.UTF_8.name());
			helper.setFrom(fromAddress);
			helper.setTo(toEmail);
			helper.setSubject("Password reset code | رمز إعادة تعيين كلمة المرور");
			helper.setText(
					"Your password reset code (valid " + validMinutes + " minutes):\n"
							+ otp
							+ "\n\nرمز إعادة تعيين كلمة المرور (صالح "
							+ validMinutes
							+ " دقائق):\n"
							+ otp,
					false);
			mailSender.send(mimeMessage);
			log.info("Password reset OTP email sent to {}", toEmail);
			return OtpSendResult.ok();
		}
		catch (Exception e) {
			log.error("SMTP send failed for {}", toEmail, e);
			String hint = e.getClass().getSimpleName() + ": " + truncate(e.getMessage(), 200);
			return OtpSendResult.fail(OtpSendFailure.SMTP_REJECTED, hint);
		}
	}

	private static boolean isGmailSmtp(String host) {
		return host != null && host.toLowerCase(Locale.ROOT).contains("gmail");
	}

	private static String normalizeGmailAppPassword(String raw) {
		return raw.replaceAll("\\s+", "").replace("-", "");
	}

	private static String truncate(String s, int max) {
		if (s == null) {
			return "";
		}
		return s.length() <= max ? s : s.substring(0, max) + "...";
	}
}
