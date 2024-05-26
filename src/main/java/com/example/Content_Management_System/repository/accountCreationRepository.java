package com.example.Content_Management_System.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.Content_Management_System.model.userDetailModel;

@Mapper
public interface accountCreationRepository {
	public abstract int createUserAccount(userDetailModel userDetailModel);
	public abstract int createAdminAccount( userDetailModel userDetailModel);
}
