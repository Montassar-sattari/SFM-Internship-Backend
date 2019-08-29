package com.example.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.model.AppUser;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private AccountServiceInterface accountServiceInterface;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user=accountServiceInterface.findUserByUserName(username);
		
		if  (user == null) throw new UsernameNotFoundException(username);
		Collection< GrantedAuthority> authorities =new ArrayList<>();
		user.getAppRoles().forEach(r-> {
			authorities.add(new SimpleGrantedAuthority(r.getName()));
		});
		return new User(user.getUsername(), user.getPassword(), authorities);
	}

}
