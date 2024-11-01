package com.example.Content_Management_System.security.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class authenticationController {
	
	@RequestMapping(value = "/login" , method =  RequestMethod.GET)
	private ModelAndView login(@RequestParam(value = "error", required = false) String error,
			 @RequestParam(value = "logout", required = false) String logout , RedirectAttributes redirectAttributes) {
		
		ModelAndView modelAndView = new ModelAndView();
		
	     if(error != null) {
	    	 redirectAttributes.addFlashAttribute("errorMessage", "You have got discorterous error!");
	      }
	      if (logout != null) {
	    	  redirectAttributes.addFlashAttribute("successMessage", "You have been logged out successfully");
	       }
	        modelAndView.setViewName("login");
	        return modelAndView;
	}
	
	@RequestMapping( value = "/logout" , method =  RequestMethod.GET)
	private ModelAndView logout(HttpServletRequest request , HttpServletResponse response) {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
	ModelAndView modelAndView = new ModelAndView();

		if( authentication != null && authentication.isAuthenticated() ) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}
		
		modelAndView.setViewName("redirect:/login?logout=success");
		
		 return modelAndView;
	}
}
