package com.example.demo.service;

import com.example.demo.entity.AccountUser;
import com.example.demo.entity.LoginUser;

public interface UsersService {
	AccountUser loginCheck(LoginUser loginUser);
	boolean registerAccount(AccountUser accountUser);
	boolean deleteAccount(AccountUser accountUser);//AccountUser型に変更2023/12/18
}