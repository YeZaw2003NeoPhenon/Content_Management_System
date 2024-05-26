package com.example.Content_Management_System.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Content_Management_System.model.userDetailModel;
import com.example.Content_Management_System.repository.accountCreationRepository;

@Service
public class AccountCreateServiceImp implements accountCreationService {

	@Autowired
	private accountCreationRepository accountCreationRepository;
	
	@Override
	public int accountCreateProcess(userDetailModel userDetailModel) {
		
	  userDetailModel.setPassword( new BCryptPasswordEncoder().encode(userDetailModel.getPassword()));
		
	  int userAccountCreationOutput = accountCreationRepository.createUserAccount(userDetailModel);
	  int adminAccountCreationOutput = accountCreationRepository.createAdminAccount(userDetailModel);
	  
	  int BouncedTogetherResult = userAccountCreationOutput + adminAccountCreationOutput;
	  
	  if( BouncedTogetherResult > 1 ) {
		  return BouncedTogetherResult;
	  }
	  else {
		  return -1;
	  }
	}
	
}
