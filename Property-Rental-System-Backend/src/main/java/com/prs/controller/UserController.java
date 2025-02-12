package com.prs.controller;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prs.dao.UserDao;
import com.prs.dto.LoginRequest;
import com.prs.pojos.Role;
import com.prs.pojos.User;
import com.prs.security.JwtUtil;

@RestController
//@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserDao userDao;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private JavaMailSender mailSender;

    private Map<String, String> resetTokens = new HashMap<>(); // Store temporary reset tokens

    
    @GetMapping("/register")
    public Role[] getRoles() {
    	return Role.values();
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
        return "User registered successfully!";
    }

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        User user = userDao.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println(user.getId());
        if (passwordEncoder.matches(password, user.getPassword())) {
        	Map<String, Object> response = new HashMap<>();
            response.put("user", user);
            response.put("token", jwtUtil.generateToken(user.getEmail(), user.getRole().name()));
            return ResponseEntity.status(HttpStatus.OK).body(response);
            
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
    
   
    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers(){
    	return ResponseEntity.status(HttpStatus.OK).body(userDao.findAll());
    }
    
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id){
    	return ResponseEntity.status(HttpStatus.OK).body(userDao.findById(id)); 
    }
    
    
    @DeleteMapping("/admin/users/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
    	User u = userDao.findById(id).orElseThrow();
    	userDao.delete(u);
    	return ResponseEntity.status(HttpStatus.OK).body("Delete Successful");
    }
   
    // Forgot Password - Generate Reset Token
    @PostMapping("/password/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        Optional<User> userOpt = userDao.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Email not found!");
        }

        String resetToken = UUID.randomUUID().toString(); // Generate random token
        resetTokens.put(resetToken, email); // Store token with email mapping

        // Send email with reset link
        sendResetEmail(email, resetToken);

        return ResponseEntity.ok("Password reset link has been sent to your email.");
    }

    // Reset Password API
    @PostMapping("/password/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token,@RequestBody Map<String, String> request) {
    	String password = request.get("newPassword"); 
    	System.out.println(password);
    	String email = resetTokens.get(token);
        if (email == null) {
            return ResponseEntity.badRequest().body("Invalid or expired reset token!");
        }

        User user = userDao.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(password)); // Encrypt new password
        userDao.save(user);

        resetTokens.remove(token); // Remove token after use

        return ResponseEntity.ok("Password has been reset successfully!");
    }

    // Helper Method: Send Reset Email
    private void sendResetEmail(String email, String resetToken) {
        String resetLink = "http://localhost:3000/reset-password?token=" + resetToken;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("Click the following link to reset your password: " + resetLink);
        mailSender.send(message);
    }
    
   
    
//    
//    @PutMapping("/users/{id}")
//    public String updateUser(@RequestBody User user) {
//    	return userDao.sa
//    }

}

