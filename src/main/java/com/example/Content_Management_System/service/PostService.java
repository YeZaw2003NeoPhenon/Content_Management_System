package com.example.Content_Management_System.service;

import java.util.List;

import com.example.Content_Management_System.model.Post;

public interface PostService {
	public abstract int postInsertion(Post post);
	public abstract List<Post> getAllPosts();
	public abstract Post getPostById( int id );
	public abstract int deletePost(int id );
	public abstract int updatePost( Post post );
}
