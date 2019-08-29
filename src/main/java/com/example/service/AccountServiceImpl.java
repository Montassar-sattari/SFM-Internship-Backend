package com.example.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.AppRole;
import com.example.model.AppUser;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
@Service
@Transactional
public class AccountServiceImpl  implements AccountServiceInterface  {
	
	
	
	public AccountServiceImpl() {
		super();
		
	}


	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Override
	public AppUser saveUser(AppUser appUser) {
		String HashPWD=bCryptPasswordEncoder.encode(appUser.getPassword());
		appUser.setPassword(HashPWD);
		return userRepository.save(appUser);
	}

	@Override
	public AppRole saveRole(AppRole appRole) {
	
		return roleRepository.save(appRole);
		
	}

	@Override
	public void AddRoleToUser(String username, String roleName) {
		
		AppRole role = roleRepository.findByName(roleName);
		AppUser user = userRepository.findByUsername(username);
		if(user!= null && role != null) {
			Collection<AppRole> roles= new ArrayList<AppRole>();
			roles.add(role);
			user.setAppRoles(roles);
		}
	} 

	@Override
	public AppUser findUserByUserName(String username) {
		
		return userRepository.findByUsername(username);
	}

}
