package com.example.service;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.AppRole;
import com.example.model.AppUser;
import com.example.repository.RoleRepository;
@RestController
public class AccountRestController {
	
	@Autowired	
	 private AccountServiceImpl accountService;
	@Autowired
	private RoleRepository roleRepository;
	
	
	@PostMapping("/register")
	public AppUser register( @RequestBody RegisterForm FormUser) {
		
		if (!FormUser.getPassword().equals(FormUser.getRepassword()))throw new RuntimeException("you must confirm your password");
				
		AppUser user= accountService.findUserByUserName(FormUser.getUsername());
		if (user != null)throw new RuntimeException("This user already exsists"); 
		ZonedDateTime today;
		today=ZonedDateTime.now();
		AppUser appUser= new AppUser();
		appUser.setUsername(FormUser.getUsername());
		appUser.setEmail(FormUser.getEmail());
		appUser.setNom(FormUser.getNom());
		appUser.setNumeroTel( FormUser.getNumeroTel());
		appUser.setPrenom(FormUser.getPrenom());
		appUser.setPassword(FormUser.getPassword());
		appUser.setJoinedOn(today);
		AppRole role =roleRepository.findByName("USER");
		if (role!=null) {
			appUser.getAppRoles().add(role);
		}
		//accountService.AddRoleToUser(FormUser.getUsername(), "USER");
		return accountService.saveUser(appUser);
		 
		
		
	}
}
