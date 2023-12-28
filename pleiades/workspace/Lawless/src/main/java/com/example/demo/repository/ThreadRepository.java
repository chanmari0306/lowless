package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Threads;


@Repository
public class ThreadRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	//スレッドを取得(スレッドの名前を表示させる)
	public List<Threads> findThreads() {
		List<Threads> threadList = new ArrayList<>();
		String sql = "SELECT THREAD_ID,TITLE FROM THREADS ORDER BY THREAD_ID DESC";
		List<Map<String,Object>> resultList = jdbcTemplate.queryForList(sql);
		for(Map<String,Object> result:resultList) {
			Integer thread_Id = (Integer)result.get("THREAD_ID");
			String title = (String)result.get("TITLE");
			Threads threads = new Threads(thread_Id,title);
			//Postsエンティティークラスを作る
			threadList.add(threads);
		}
		if(resultList.size() != 0 && resultList != null) {
			System.out.println(resultList.size());
			return threadList;
		}else {
			return null;
		}
	}
	
	//スレッド作成(threadsテーブルに追加)
	public boolean registerThread(Threads threads) {
		String sql = "INSERT INTO (USER_ID,POST) VALUES(?,?)";
		int result = jdbcTemplate.update(sql,threads.getThread_id(),threads.getTitle());
		if(result == 0 && threads.getThread_id() == null && threads.getTitle().length() == 0) {
			return false;
		}else{
			return true;
		}
	}
	
}
