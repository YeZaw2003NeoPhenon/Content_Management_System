package com.example.Content_Management_System.fileHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;

@Component
public class multiPartFileHandler {
	
	@Autowired
	 private ServletContext servletContext;
	
	public String saveFile(MultipartFile multipartFile) {
			  String Upload_Dir = servletContext.getRealPath("/uploads/");
			  String filename = multipartFile.getOriginalFilename();
			  Path uploadPath = Paths.get(Upload_Dir);
			 try {
			  if( !Files.exists(uploadPath) ) {
				  
				Files.createDirectories(uploadPath);
				}
			  Path filePath = uploadPath.resolve(filename);
		      Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		      return "/uploads/" + filename;
			  }
			 catch (IOException e) {
					
					e.printStackTrace();
					return null;
				}
	}
}
