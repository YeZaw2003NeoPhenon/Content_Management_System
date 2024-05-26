package com.example.Content_Management_System.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Content_Management_System.fileHandler.multiPartFileHandler;
import com.example.Content_Management_System.model.Post;
import com.example.Content_Management_System.service.postServiceImp;

import jakarta.servlet.ServletContext;

@Controller
public class postController {
	
	@Autowired
	private postServiceImp postServiceImp;
	
	@Autowired
	private multiPartFileHandler multiPartFileHandler; 
	
  @RequestMapping( value = {"/posts" , "/" }, method = RequestMethod.GET)
  public ModelAndView getAllPosts() {
	 ModelAndView modelAndView = new ModelAndView();
	 List<Post>	posts = postServiceImp.getAllPosts();
	 modelAndView.addObject("posts", posts);
	 modelAndView.setViewName("posts");
		return modelAndView;
	}
   
  @RequestMapping( value = "/createNewPost" , method = RequestMethod.GET)
  public ModelAndView insertIntoPosts() {
	 ModelAndView modelAndView = new ModelAndView();
	 Post post = new Post();
	 modelAndView.addObject("post", post);
	 modelAndView.setViewName("createNewPost");
		return modelAndView;
	}
  
  // we will dexterously handle multipart that we graciously banged away with in fronend (imgURL) and get across file data here
  @RequestMapping( value = "/saveNewPosts" , method = RequestMethod.POST)
  public ModelAndView saveNewPost( @ModelAttribute Post postToBeSaved , RedirectAttributes redirectAttributes , @RequestParam("imageFile") MultipartFile multipartFile){
	 
	  ModelAndView modelAndView = new ModelAndView();
	  
	   String imgUrl = multiPartFileHandler.saveFile(multipartFile);
	 
	  	if( imgUrl == null ) {
	  		redirectAttributes.addFlashAttribute("errorMessage", "there is something patronyzingly wrong with image file");
	  		modelAndView.setViewName("redirect:/createNewPost");
	  		return modelAndView;
	  	}
	  postToBeSaved.setImgURL(imgUrl);
	  
	  int result = 	postServiceImp.postInsertion(postToBeSaved);
	   	
	  if( result > 0 ) {
		  redirectAttributes.addFlashAttribute("SuccessMessage", "Post Created Unblemishedly ");
	  }
	 
	  else {
		  redirectAttributes.addFlashAttribute("clutteredMessage", "Post failed to be created Successfully ");
	  }
	  
		 modelAndView.setViewName("redirect:/posts");
		return modelAndView;
  }
  


@RequestMapping( value = "/posts/delete/{id}" , method = RequestMethod.GET)
  public ModelAndView deletePost( @PathVariable int id , RedirectAttributes redirectAttributes ) {
	  ModelAndView modelAndView = new ModelAndView();
	  int result =  postServiceImp.deletePost(id);
	  if( result > 0 ) {
		  redirectAttributes.addFlashAttribute("SuccessMessage", "Post Deleted Successfully ");
	  }
	  else {
		  redirectAttributes.addFlashAttribute("clutteredMessage", "Post Failed to be updated Successfully ");
	  }
	  modelAndView.setViewName("redirect:/posts");
	  return modelAndView;
  }
  
  @RequestMapping( value = "/posts/edit/{id}" , method = RequestMethod.GET)
  public ModelAndView editPost(@PathVariable int id) {
	  ModelAndView modelAndView = new ModelAndView();
	 Post postToBeUpdated =   postServiceImp.getPostById(id);
	 modelAndView.addObject("post", postToBeUpdated);
	  modelAndView.setViewName("updatePost");
	  return modelAndView;
  }
  
  @RequestMapping( value = "/update" , method = RequestMethod.POST)
  public ModelAndView updatePost( @ModelAttribute Post post , RedirectAttributes redirectAttributes , @RequestParam("imageFile") MultipartFile multipartFile ) {
	  ModelAndView modelAndView = new ModelAndView();
	  
	  if( !multipartFile.isEmpty() ) {
		  String imgUrl = multiPartFileHandler.saveFile(multipartFile);
		  if( imgUrl != null ) {
			  post.setImgURL(imgUrl);
		  }
		  else {
			  redirectAttributes.addFlashAttribute("clutteredMessage", "Failed to upload image file");
	            modelAndView.setViewName("redirect:/posts");
	            return modelAndView;
		  }
	  }
	  int result =  postServiceImp.updatePost(post);
	  if( result > 0 ) {
		  redirectAttributes.addFlashAttribute("SuccessMessage", "Posts Updated Successfully!" );
	  }
	  else {
		  redirectAttributes.addFlashAttribute("clutteredMessage", "Posts slugishingly Failed to update" );
	  }
	  
	  modelAndView.setViewName("redirect:/posts");
	  return modelAndView;
  }
  
}
