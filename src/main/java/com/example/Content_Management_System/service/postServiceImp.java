package com.example.Content_Management_System.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Content_Management_System.model.Post;
import com.example.Content_Management_System.repository.PostMapper;

@Service
public class postServiceImp implements PostService{

	@Autowired
	private PostMapper postMapper;
	
	@Override
	public int postInsertion(Post post) {
		
		return postMapper.postInsertion(post);
	}

	@Override
	public List<Post> getAllPosts() {
		return postMapper.getAllPosts();
	}

	@Override
	public Post getPostById(int id) {
		
		return postMapper.getPostById(id);
	}

	@Override
	public int deletePost(int id ) {
		
		return postMapper.deletePost(id);
	}

	@Override
	public int updatePost(Post post) {
		
		return postMapper.updatePost(post);
	}

}
