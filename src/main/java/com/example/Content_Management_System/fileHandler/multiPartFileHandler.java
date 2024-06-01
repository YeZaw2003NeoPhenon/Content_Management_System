package com.example.Content_Management_System.fileHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class multiPartFileHandler implements fileHandlerService{
	
	@Autowired
	private ServletContext servletContext;

	private Logger LOGGER = LoggerFactory.getLogger(multiPartFileHandler.class);
	
	@Override
	public String saveFile(MultipartFile multipartFile) {
			  String filename = multipartFile.getOriginalFilename();
			 String upload_Dir =  servletContext.getRealPath("/uploads/"); 
			 Path uploadPath = Paths.get(upload_Dir);
			 try {
			  if( !Files.exists(uploadPath) ) {
				  
				Files.createDirectories(uploadPath);
				LOGGER.debug("Created directories for upload path: {}",upload_Dir);
				}
			  Path filePath = uploadPath.resolve(filename);
		      Files.copy(multipartFile.getInputStream() , filePath, StandardCopyOption.REPLACE_EXISTING);
		      LOGGER.info("File saved successfully: {}", filename);
		      return "/uploads/" + filename;
			  }
			 catch (IOException e) {
				 LOGGER.error("Failed to save file: {}",filename , e );
					e.printStackTrace();
					return null;
				}
	}
}
