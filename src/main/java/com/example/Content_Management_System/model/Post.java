package com.example.Content_Management_System.model;

import lombok.Getter;
import lombok.Setter;

public class Post {
	
	@Getter
	@Setter
	private int id;
	
	@Getter
	@Setter
	private String title;
	
	@Getter
	@Setter
	private String content;
	
	@Getter
	@Setter
	private String imgURL;
	
}
