package com.example.Content_Management_System.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Content_Management_System.model.userDetailModel;
import com.example.Content_Management_System.service.AccountCreateServiceImp;

@Controller
public class accountCreationController {
	
	@Autowired
	private AccountCreateServiceImp accountCreateServiceImp;
	
	@RequestMapping( value = "/createAccount" , method = RequestMethod.GET )
	public ModelAndView accountCreation() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("createAccount");
		return modelAndView;
	}
	
	@RequestMapping( value = "/accountCreationProcess", method = RequestMethod.POST )
	public ModelAndView accountCreationProcess( @ModelAttribute userDetailModel userObjTobeAuthorized , RedirectAttributes redirectAttributes ) {
		ModelAndView modelAndView = new ModelAndView();
		
		int totalAccountCreationResult = accountCreateServiceImp.accountCreateProcess(userObjTobeAuthorized);
		String aspiringResult = "Welcome Aboard! User Account Created!";
		String failiureHandlerMessage = "Oops! Something Went Wrong Creating the Accounting Entry";
		 if( totalAccountCreationResult == 2 ) {
			 redirectAttributes.addFlashAttribute("accountSucessMessage", aspiringResult);
		 }
		 else {
			 redirectAttributes.addFlashAttribute("accountFailedMessage", failiureHandlerMessage);
		 }
		 
		 modelAndView.setViewName("redirect:/posts");
		return modelAndView;
	}
}
