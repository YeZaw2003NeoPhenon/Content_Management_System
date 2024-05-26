package com.example.Content_Management_System.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	 userDetailModel userDetailModel = userDetailRepository.loginProcess(username);
	 if( userDetailModel == null ) {
		 new UsernameNotFoundException(" User still stuck on tie to be found!");
	 }
	 List<GrantedAuthority> authorities = new ArrayList<>();
	 authorities.add( new SimpleGrantedAuthority("ROLE_" + userDetailModel.getAuthority() ));
	 
		return new User(userDetailModel.getUsername(), userDetailModel.getPassword(), authorities );
	}

}
