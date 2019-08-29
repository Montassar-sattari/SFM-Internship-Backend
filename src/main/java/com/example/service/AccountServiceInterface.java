package com.example.service;


import com.example.model.AppRole;
import com.example.model.AppUser;

public interface AccountServiceInterface {
	
	public AppUser saveUser(AppUser appUser);
	public AppRole saveRole(AppRole appRole);
	
	public void AddRoleToUser( String username, String roleName);
	
	public AppUser findUserByUserName(String username);
	
	
	

}
