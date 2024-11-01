package com.example.Content_Management_System.security.user;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

public class userDetailModel implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String username;
	
	@Getter
	@Setter
	private String password;
	
	@Getter
	@Setter
	private boolean enabled;
	
	@Getter
	@Setter
	private String authority;
	
//	public userDetailModel(UserDetails user) {
//		this.username = user.getUsername();
//		this.password = user.getPassword();
//		this.enabled = user.isEnabled();
//		this.authority = user.getAuthorities().stream()
//				.map(GrantedAuthority::getAuthority)
//				.collect(Collectors.joining(" "));
//	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
	    if (authority == null || authority.isEmpty()) {
	        return Collections.emptyList();
	    }
	    return Arrays.stream(authority.split(" "))
	    			.map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
	    			.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}
	
	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
