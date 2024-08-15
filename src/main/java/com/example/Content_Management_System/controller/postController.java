package com.example.Content_Management_System.controller;


import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Content_Management_System.exception.FileUploadException;
import com.example.Content_Management_System.exception.PostNotFoundException;
import com.example.Content_Management_System.fileHandler.multiPartFileHandler;
import com.example.Content_Management_System.model.Post;
import com.example.Content_Management_System.service.postServiceImp;
import com.example.Content_Management_System.service.userDetailServiceImp;


@Controller
public class postController {
	
	@Autowired
	private postServiceImp postServiceImp;
	
	@Autowired
	private multiPartFileHandler multiPartFileHandler; 
	

	@Autowired
	private userDetailServiceImp userDetailServiceImp;
	
	private Logger LOGGER = LoggerFactory.getLogger(postController.class);
	
  @RequestMapping( value = {"/posts" , "/" }, method = RequestMethod.GET)
  public ModelAndView getAllPosts() {
	 ModelAndView modelAndView = new ModelAndView();
	 List<Post>	posts = postServiceImp.getAllPosts();
	 modelAndView.addObject("posts", posts);
	 modelAndView.setViewName("posts");
	String curr_user = userDetailServiceImp.getCurrentUser();
	System.out.println("Current User" + curr_user);
	
	 LOGGER.info("Scraching around the collections all posts");
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
  public ModelAndView saveNewPost(@ModelAttribute Post postToBeSaved , RedirectAttributes redirectAttributes , @RequestParam("imageFile") MultipartFile multipartFile){
	 
	  ModelAndView modelAndView = new ModelAndView();
	  
	   String imgUrl = multiPartFileHandler.saveFile(multipartFile);
	 
	  if( imgUrl == null ) {
	  	  throw new FileUploadException("Error uploading image file.");
	  	}
	  	
	  postToBeSaved.setImgURL(imgUrl);
	  
	  int result = 	postServiceImp.postInsertion(postToBeSaved);
	   	
	  if( result > 0 ) {
		  redirectAttributes.addFlashAttribute("SuccessMessage", "Post Created Unblemishedly ");
		  LOGGER.info("Post created Unblemishedly : {}"+postToBeSaved.getId());
	  }
	 
	  else {
		  redirectAttributes.addFlashAttribute("clutteredMessage", "Post failed to be created Successfully ");
		  LOGGER.error("Post failed to be created Successfully : {}"+postToBeSaved.getId());
	  }
	  
		 modelAndView.setViewName("redirect:/posts");
		return modelAndView;
  }
 

@RequestMapping( value = "/posts/delete/{id}" , method = RequestMethod.GET)
  public ModelAndView deletePost( @PathVariable(name = "id") int id , RedirectAttributes redirectAttributes ) {
	  ModelAndView modelAndView = new ModelAndView();
	  
	 Optional<Post> op_Post=  postServiceImp.getPostById(id);
	 
	   if (!op_Post.isPresent()) {
	        throw new PostNotFoundException("Post not found with ID: " + id);
	    }
	 
	  int result =  postServiceImp.deletePost(id);
	  if( result > 0 ) {
		  redirectAttributes.addFlashAttribute("SuccessMessage", "Post Deleted Successfully ");
		  LOGGER.info("Post Deleted Successfully : {}",id );
	  }
	  else {
		  redirectAttributes.addFlashAttribute("clutteredMessage", "Post Failed to be Deleted Successfully ");
		  LOGGER.error("Post Failed to be Deleted Successfully:{}",id );
	  }
	  modelAndView.setViewName("redirect:/posts");
	  return modelAndView;
  }

  @RequestMapping( value = "/posts/edit/{id}" , method = RequestMethod.GET)
  public ModelAndView editPost(@PathVariable int id) {
	  ModelAndView modelAndView = new ModelAndView();
	  
	 Post postToBeUpdated =   postServiceImp.getPostById(id).orElseThrow(() -> new PostNotFoundException("Post with ID " + id + " not found."));
	 
	 modelAndView.addObject("post", postToBeUpdated);
	  modelAndView.setViewName("updatePost");
	  return modelAndView;
  }
  
  @RequestMapping( value = "/update" , method = RequestMethod.POST)
  public ModelAndView updatePost( @ModelAttribute Post post , RedirectAttributes redirectAttributes , @RequestParam("imageFile") MultipartFile multipartFile ) {
	  ModelAndView modelAndView = new ModelAndView();
	  
	  if( !multipartFile.isEmpty()) {
		  String imgUrl = multiPartFileHandler.saveFile(multipartFile);
		  if( imgUrl != null ) {
			post.setImgURL(imgUrl);
		  }
		  else {
			  redirectAttributes.addFlashAttribute("clutteredMessage", "Failed to upload image file");
			  LOGGER.warn("Failed to upload new image for post: {}", post.getId());
	            modelAndView.setViewName("redirect:/posts");
	            return modelAndView;
		  }
	  }
	  
	  int result =  postServiceImp.updatePost(post);
	  if( result > 0 ) {
		  redirectAttributes.addFlashAttribute("SuccessMessage", "Posts Updated Successfully!" );
		  LOGGER.info("Posts Updated Successfully : {}" , post.getId());
	  }
	  else {
		  redirectAttributes.addFlashAttribute("clutteredMessage", "Posts slugishingly Failed to update" );
		  LOGGER.error("Posts slugishingly Failed to update : {}" ,post.getId());
	  }
	  
	  modelAndView.setViewName("redirect:/posts");
	  return modelAndView;
  }
  
}
