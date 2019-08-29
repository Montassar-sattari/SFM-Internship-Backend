package com.example.service;

import lombok.Data;

@Data
public class RegisterForm {
	
	private String prenom;
	private String nom;
	private String email;
	private String username;
	private String password;
	private String repassword;
	private String numeroTel;
	
}
