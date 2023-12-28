package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.AccountUser;
import com.example.demo.entity.LoginUser;
import com.example.demo.entity.Posts;
import com.example.demo.entity.Threads;
import com.example.demo.service.PostsService;
import com.example.demo.service.ThreadsService;
import com.example.demo.service.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LawlessController {

	@Autowired
	UsersService usersService;
	
	@Autowired
	ThreadsService threadsService;
	
	@Autowired
	PostsService postsService;

	@Autowired
	HttpSession session;
	
	//ログインページ
	@GetMapping("welcome")
	public String welcomeView() {
		session.removeAttribute("loginUser");//お前てぃね！のやつ
		session.invalidate();//みんなてぃね！のやつ
		return "welcome";
	}
	
	@GetMapping("login")
	public String loginView() {
		session.removeAttribute("loginUser");
		session.invalidate();
		return "login";
	}
	
	//ログイン完了画面
	@PostMapping("loginOK")
	public String loginResult(LoginUser loginUser,Model model) {
	AccountUser result = usersService.loginCheck(loginUser);
		if(result == null) {
			return "login";
		}
		session.setAttribute("accountUser", result);
		model.addAttribute("accountUser" ,result);
		return "loginOK";
	}
	
	@GetMapping("loginOK")
	public String loginOKView() {
		return "loginOK";
	}
	
	//スレッド一覧表示
	@GetMapping("threads")
	public String threadsView(Model model) {
		List<Threads> threadsList = threadsService.findThreads();
		model.addAttribute("threadsList", threadsList);
		return "threads";
	}
	
	//各スレッドを表示(ここは時間があれば改良)
	//とりまゲームの話題のスレッドを作る
	//各スレの情報を持ってきて表示するページに変更済 12/20
	//この変更に伴い、html名をpostに変更する必要あり
	@GetMapping("/{id}")
	//@DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
	public String postsView(@PathVariable Integer id,Model model) {//idはthreads.htmlから受け取ったthread_idを格納する
		List<Posts> postsList = postsService.findPosts(id);
		Threads threads = new Threads();
		threads.getThread_id();
		threads.getTitle();
		session.setAttribute("threads",threads);
		model.addAttribute("threads",threads);
		model.addAttribute("postsList",postsList);
		//model.addAttributeはhtml(View)に情報を送るためのもの
		//modelは寿命が指示された時一回だけなので名前が被らなければいくつも使える
		return "posts";
	}
	
	@PostMapping("tweet")
	//@DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
//	@PathVariable Integer id
	public String postTweet(@PathVariable Integer id,Model model, @RequestParam String pos) {
		
		AccountUser accountUser = (AccountUser) session.getAttribute("accountUser");
		Threads threads = (Threads)session.getAttribute("threads");
		
		Posts posts = new Posts();
//		posts.setThread_id(id);
		posts.setThread_id(threads.getThread_id());
		posts.setUser_id(accountUser.getUser_id());
		posts.setPost(pos);
//		posts.setPost_date(posts.getPost_date());
		
		boolean result = postsService.registerPost(posts);
		model.addAttribute("postsList", posts);
		if(result == false) {
//			System.out.println("つぶやきを入力してください");//htmlで表示されないといみない
			return "posts";
		}
		List<Posts> allPostsList = postsService.findPosts(id);
		model.addAttribute("postsList",allPostsList);
		return "posts";
	}
	
	//ログアウト確認画面
	@GetMapping("logout")
	public String logoutView(Model model) {
		AccountUser accountUser = (AccountUser) session.getAttribute("accountUser");
		model.addAttribute("accountUser",accountUser);
		System.out.println("あかうんと？:"+accountUser);
		return "logout";
	}
	
	//ログアウト完了画面
	@GetMapping("logoutOK")
	public String logoutOKView(Model model) {
		AccountUser accountUser = (AccountUser) session.getAttribute("accoubtUser");
		if(accountUser == null) {
			return "redirect:welcome";//welcome.htmlにした 2023/12/15 10:06
		}
		model.addAttribute("accountUser", accountUser);
		return "logoutOK";
	}
		
	//退会確認画面
	@GetMapping("delete")
	public String deleteView(Model model) {
		AccountUser accountUser = (AccountUser) session.getAttribute("accountUser");
//		session.setAttribute("loginUser", loginUser);これ要らなかった
		model.addAttribute("accountUser" , accountUser);
		return "delete";
	}
	
	//退会完了画面 ここで退会処理でよき？？
	//deleteAccountの引数をRegisterUserに変更2023/12/15
	//RegisterUserをAccountUserに名前変更2023/12/18
	@GetMapping("deleteOK")
	public String deleteOKView(Model model) {
//		LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
		AccountUser accountUser = (AccountUser)session.getAttribute("accountUser");
		model.addAttribute("accountUser" , accountUser);
		boolean result =usersService.deleteAccount(accountUser);
		if(result == true) {
			System.err.println("ここ？");
			return "deleteOK";
		}
		System.out.println("わァ…！");
		return "delete";
	}
	
	//新規登録画面
	@GetMapping("register")
	public String registerView(AccountUser registerUser) {
		return "register";
	}
	
	//新規登録完了画面
	@PostMapping("registerOK")
	public String RegisterOKView(@Validated AccountUser accountUser,
			BindingResult bindingResult,Model model){
		//ここでModelだけで情報が引き取れるのは、
		//RegisterUserが持ってる情報とpostで送られてきた情報が同じ個数だから？らしい
		//Postのloginも同じかも
		if(bindingResult.hasErrors()) {
			return "register";
		}
		
		boolean result = usersService.registerAccount(accountUser);
		if(result) {
			return "registerOK";
		}else{
			return "redirect:register";
		}
	}
}
