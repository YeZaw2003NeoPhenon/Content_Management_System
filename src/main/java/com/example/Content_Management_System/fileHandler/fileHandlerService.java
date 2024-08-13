package com.example.Content_Management_System.fileHandler;

import org.springframework.web.multipart.MultipartFile;

public interface fileHandlerService {
	public String saveFile(MultipartFile multipartFile);

}
