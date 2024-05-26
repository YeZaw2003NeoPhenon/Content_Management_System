package com.example.Content_Management_System.model;


import lombok.Getter;
import lombok.Setter;

public class userDetailModel{

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

}
