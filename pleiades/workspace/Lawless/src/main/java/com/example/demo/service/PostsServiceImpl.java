package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Posts;
import com.example.demo.repository.PostRepository;

@Service
public class PostsServiceImpl implements PostsService{
	
	@Autowired
	PostRepository repository;
	
	@Override
	public List<Posts> findPosts(Integer id){
		List<Posts> postList = new ArrayList<>();
		postList = repository.findPosts(id);
		return postList;//Mutterが格納されたMutterListを返す？
	}
	
	@Override
	public boolean registerPost(Posts posts) {
		boolean result = repository.registerPost(posts);
		 if(result) {
			 return true;
		 }else{
			 return false;
			}
	}
}
