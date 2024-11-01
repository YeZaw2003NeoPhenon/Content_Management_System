package com.example.Content_Management_System.security.user;

import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

@Mapper
public interface userDetailRepository {
	public abstract Optional<userDetailModel> loginProcess(@RequestParam(name = "username") String username);	
}
