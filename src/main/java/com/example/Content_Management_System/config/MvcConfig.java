package com.example.Content_Management_System.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer{
	
	
	public void addResourceHandler( ResourceHandlerRegistry registry ) {
		// this method registeration euphemistically serve handlers for static
		//resources such images and css files and other
		//headers dynamic resoures for efficient handling
		registry.addResourceHandler("/uploads/**")
		   .addResourceLocations("classpath:/uploads/");
	}
}