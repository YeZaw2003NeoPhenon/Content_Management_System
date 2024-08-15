package com.example.Content_Management_System.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Content_Management_System.model.userDetailModel;
import com.example.Content_Management_System.repository.userDetailRepository;

@Service
public class userDetailServiceImp implements UserDetailsService{
	
	@Autowired
	private userDetailRepository userDetailRepository ;
	
	Logger logger = LoggerFactory.getLogger(userDetailServiceImp.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		 userDetailModel userDetailModel = userDetailRepository.loginProcess(username).orElseThrow(() -> {
			 logger.error("User not found with username: {}", username);
	         return new UsernameNotFoundException("User not found with username: " + username);
	     });
		 
			return User.withUsername(userDetailModel.getUsername())
					   .password(userDetailModel.getPassword())
					   .authorities(userDetailModel.getAuthorities())
					   .accountExpired(!userDetailModel.isEnabled())
					   .build();
	}
	
	public String getCurrentUser() {
		
	 Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
	 
	 if( authentication != null && authentication.isAuthenticated()) {
		Object principal  = authentication.getPrincipal();
		
		if(principal instanceof userDetailModel) {
			return ((userDetailModel) principal).getUsername();
		}
		else {
			return principal.toString();
		}
	 }
	 return null;
	}
	
}
