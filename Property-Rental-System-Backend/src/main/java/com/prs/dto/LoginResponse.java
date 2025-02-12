package com.prs.dto;

import com.prs.pojos.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    
	private User user;
    private String token;

    // Getters and Setters
}