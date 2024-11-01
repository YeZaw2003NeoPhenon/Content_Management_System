package com.example.Content_Management_System.security.service.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Content_Management_System.security.repository.accountCreationRepository;
import com.example.Content_Management_System.security.service.accountCreationService;
import com.example.Content_Management_System.security.user.userDetailModel;

@Service
public class AccountCreateServiceImp implements accountCreationService {

	@Autowired
	private accountCreationRepository accountCreationRepository;
	
	@Override
	public int accountCreateProcess(userDetailModel userDetailModel) {
		
	  userDetailModel.setPassword( new BCryptPasswordEncoder().encode(userDetailModel.getPassword()));
	  userDetailModel.setEnabled(true);
	  
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
