package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Posts;

@Repository
public class PostRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	//つぶやきを取得
	public List<Posts> findPosts(@Param("threadId") Integer threadId) {
		List<Posts> postList = new ArrayList<>();
		String sql = "SELECT POST_ID,USER_ID,POST,POST_DATE FROM POSTS WHERE THREAD_ID = ? ORDER BY POST_ID DESC";
		List<Map<String,Object>> resultList = jdbcTemplate.queryForList(sql,threadId);
		for(Map<String,Object> result:resultList) {
			Integer post_id = (Integer)result.get("POST_ID");
			Integer thread_id = (Integer)result.get("THREAD_ID");
			String user_id = (String)result.get("USER_ID");
			String post = (String)result.get("POST");
//			LocalDateTime post_date  = (LocalDateTime)result.get("POST_DATE");
			Posts posts = new Posts(post_id,thread_id,user_id,post);
			postList.add(posts);
		}
		if(resultList.size() != 0 && resultList != null) {
			System.out.println(resultList.size());
			return postList;
		}else {
			return null;
		}
	}
	
	//つぶやき登録
	public boolean registerPost(Posts posts) {
		String sql = "INSERT INTO POSTS(THREAD_ID,USER_ID,POST) VALUES(?,?,?)";
		int result = jdbcTemplate.update(sql,posts.getThread_id(),posts.getUser_id(),posts.getPost());
		if(result == 0 && posts.getThread_id() == null && posts.getUser_id() == null && posts.getPost().length() == 0) {
			return false;
		}else{
			return true;
		}
	}
	
}
