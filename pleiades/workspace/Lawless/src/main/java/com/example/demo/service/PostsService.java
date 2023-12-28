package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Posts;

public interface PostsService {

	List<Posts> findPosts(Integer id);
	boolean registerPost(Posts posts);
}
