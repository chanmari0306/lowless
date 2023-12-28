package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AccountUser;
import com.example.demo.entity.LoginUser;
import com.example.demo.repository.AccountRepository;

@Service
public class UsersServiceImpl implements UsersService{

	@Autowired
	AccountRepository repository;
	
	@Override
	public AccountUser loginCheck(LoginUser loginUser) {
		AccountUser result = repository.findAccount(loginUser);
		return result;
	}
	
	@Override
	public boolean registerAccount(AccountUser accountUser) {
		boolean result = repository.findAccount(accountUser);//アカウントの重複を確認する方のメソッド
		if(result) {//resultにはtrue アカウント重複なし
			if(repository.insertAccount(accountUser)) {//登録するメソッド
			return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean deleteAccount(AccountUser accountUser) {
		boolean result = repository.deleteAccount(accountUser);
		if(result) {
			return true;
		}else {
			return false;
		}
	}
	

}
