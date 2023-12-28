package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Threads;
import com.example.demo.repository.ThreadRepository;

@Service
public class ThreadsServiceImpl implements ThreadsService{
	
	@Autowired
	ThreadRepository repository;
	
	public List<Threads> findThreads() {
		List<Threads> threadsList = new ArrayList<>();
		threadsList = repository.findThreads();
		return threadsList;//Threadsが格納されたThreadsListを返す
	}
	
	public boolean registerTheads(Threads threads) {
		boolean result = repository.registerThread(threads);
		 if(result) {
			 return true;
		 }else{
			 return false;
			}
	}
}
