package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Threads;

public interface ThreadsService {
	List<Threads> findThreads();
	boolean registerTheads(Threads threads);//この辺の引数と戻り値は適当変更の可能性大
}
