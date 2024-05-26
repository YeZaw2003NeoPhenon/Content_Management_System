package com.example.Content_Management_System.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class authenticationController {
	
	@RequestMapping( value = "/login" , method =  RequestMethod.GET)
	private ModelAndView login() {
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("login");
		return modelAndView;
	}
}
