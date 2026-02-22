package com.voting.votingsystem.service;

import com.voting.votingsystem.entity.PasswordResetToken;
import com.voting.votingsystem.entity.User;
import com.voting.votingsystem.repository.PasswordResetTokenRepository;
import com.voting.votingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    @Value("${spring.mail.username}")
    private String fromEmail;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    /**
     * Initiates forgot-password flow:
     * - Finds user by email
     * - Generates a 6-digit OTP valid for 10 minutes
     * - Sends the OTP via email
     */
    @Transactional
    public String forgotPassword(String email) {
        if (email == null || email.isBlank()) {
            throw new RuntimeException("Email address is required.");
        }

        String normalised = email.toLowerCase().trim();

        userRepository.findByEmail(normalised).ifPresent(user -> {
            // Delete any existing tokens for this user
            tokenRepository.deleteByUserId(user.getId());

            // Create 6-digit OTP
            String otp = String.format("%06d", (int) (Math.random() * 1000000));

            PasswordResetToken prt = new PasswordResetToken();
            prt.setToken(otp); // We'll store the OTP in the 'token' field
            prt.setUser(user);
            prt.setExpiresAt(LocalDateTime.now().plusMinutes(10)); // 10 min for OTP
            prt.setUsed(false);
            tokenRepository.save(prt);

            // Send email
            sendOtpEmail(user, otp);
        });

        return "If an account with that email exists, a 6-digit OTP has been sent.";
    }

    /**
     * Validates OTP and resets the user's password.
     */
    @Transactional
    public String resetPassword(String otp, String newPassword) {
        if (otp == null || otp.isBlank()) {
            throw new RuntimeException("OTP is missing.");
        }
        if (newPassword == null || newPassword.length() < 6) {
            throw new RuntimeException("Password must be at least 6 characters.");
        }

        PasswordResetToken prt = tokenRepository.findByToken(otp.trim())
                .orElseThrow(() -> new RuntimeException("Invalid OTP."));

        if (prt.isUsed()) {
            throw new RuntimeException("This OTP has already been used.");
        }
        if (prt.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("This OTP has expired. Please request a new one.");
        }

        User user = prt.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        prt.setUsed(true);
        tokenRepository.save(prt);

        return "Your password has been reset successfully. You can now log in.";
    }

    private void sendOtpEmail(User user, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(user.getEmail());
        message.setSubject("VoteSecure — Password Reset OTP");
        message.setText(
                "Hello " + user.getName() + ",\n\n" +
                        "Your One-Time Password (OTP) for resetting your VoteSecure password is:\n\n" +
                        otp + "\n\n" +
                        "This OTP is valid for 10 minutes.\n\n" +
                        "If you did not request this, please ignore this email.\n\n" +
                        "— The VoteSecure Team");
        mailSender.send(message);
    }
}
