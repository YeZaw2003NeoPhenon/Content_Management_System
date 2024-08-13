package com.example.Content_Management_System.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.example.Content_Management_System.model.Post;

@Mapper
public interface PostMapper {
	public abstract int postInsertion(Post post);
	public abstract List<Post> getAllPosts();
	public abstract Optional<Post> getPostById( int id );
	public abstract int deletePost(int id );
	public abstract int updatePost( Post post );
}
