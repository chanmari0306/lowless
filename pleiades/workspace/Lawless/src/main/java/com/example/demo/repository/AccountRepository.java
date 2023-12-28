package com.example.demo.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.AccountUser;
import com.example.demo.entity.LoginUser;

@Repository

public class AccountRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	//ログインするときに登録されたアカウントを探すメソッド
	public AccountUser findAccount(LoginUser loginUser) {
		String sql ="SELECT USER_ID,PASS,MAIL,NAME,AGE FROM ACCOUNTS WHERE USER_ID = ? AND PASS = ?";
		//一致するレコードがなかったら空のリストが返る(nullではない)
		List<Map<String,Object>> resultList = jdbcTemplate.queryForList
				(sql,loginUser.getUser_Id(),loginUser.getPass());
		System.out.println(resultList);
		if(resultList.size() != 0 && resultList != null){
			for(Map<String,Object> result:resultList) {
				String name = (String)result.get("NAME");
				String pass = (String)result.get("PASS");
				String user_Id = (String)result.get("USER_ID");
				String mail = (String)result.get("MAIL");
				int age = (int)result.get("AGE");
				AccountUser accountUser = new AccountUser(user_Id,name,age,pass,mail);
				//AccountUserクラスの定義順とそろえる。
				//コンストラクタがこの順で生成されているため。
				return accountUser;
			}
			//System.err.println(loginUser.getUser_Id());これなんだっけ？
		}
		return null;
	}
	
	//アカウントの重複を確認するメソッド
	public boolean findAccount(AccountUser accountUser) {
		String sql = "SELECT COUNT(*) FROM ACCOUNTS WHERE USER_ID = ?";
		int result = jdbcTemplate.queryForObject(sql, Integer.class,accountUser.getUser_id());
		if(result == 0) {//一致するデータがなかった=未登録なので新規登録可能
			return true;//登録可能
		}
		return false;//登録不可
	}
	
	//入力情報をDBに登録するメソッド
	public boolean insertAccount(AccountUser accountUser) {
		String sql = "INSERT INTO ACCOUNTS(USER_ID,PASS,MAIL,NAME,AGE) VALUES(?,?,?,?,?)";
		int result = jdbcTemplate.update(sql,accountUser.getUser_id(),accountUser.getPass(),accountUser.getMail(),accountUser.getName(),accountUser.getAge());
		if (result == 0) {
			return false;//登録失敗
		}
		return true;//登録成功
	}
	
	//登録情報をDBから削除するメソッド
	public boolean deleteAccount(AccountUser accountUser) {
		String sql = "DELETE FROM accounts WHERE user_id = ?";
		int result = jdbcTemplate.update(sql,accountUser.getUser_id());
		if(result == 0) {
			return false;
		}else {
			return true;
		}
	}
	
	

}
