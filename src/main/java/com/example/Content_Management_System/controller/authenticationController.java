package com.example.Content_Management_System.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class authenticationController {
	
	@RequestMapping( value = "/login" , method =  RequestMethod.GET)
	private ModelAndView login( @RequestParam(value = "error", required = false) String error,
			 @RequestParam(value = "logout", required = false) String logout,
			 @RequestParam(value = "sessionExpired", required = false) String sessionExpired) {
		
		ModelAndView modelAndView = new ModelAndView();
		
	     if (error != null) {
	    	 modelAndView.addObject("errorMessage", "Invalid username or password");
	        }
	        if (logout != null) {
	    
	        	modelAndView.addObject("successMessage", "You have been logged out successfully");
	        }
	        if (sessionExpired != null) {
	        	modelAndView.addObject("warningMessage", "Your session has expired, please log in again");
	        }
		
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	@RequestMapping( value = "/logout" , method =  RequestMethod.GET)
	private String logout(HttpServletRequest request , HttpServletResponse response) {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if( authentication != null && authentication.isAuthenticated() ) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}
		
		 return "redirect:/login?logout=success";
	}
}
