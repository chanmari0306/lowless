package com.example.demo.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountUser {
	
	@Id
	@NotBlank(message="ユーザーIDを入力してください")
	@Pattern(regexp="[a-zA-Z0-9]*",message="ユーザーIDは半角英数字で入力してください")
	@Length(min=3,message="ユーザーIDは3文字以上で入力してください")
	private String user_id;
	
	@NotBlank(message="名前を入力してください")
	private String name;
	
	@Range(min=0,max=130,message="年齢は0から130の間で入力してください")
	private int age;
	
	@NotBlank(message="パスワードを入力してください")
	@Pattern(regexp="[a-zA-Z0-9]*",message="パスワードは半角英数字で入力してください")
	@Length(min=3,message="パスワードは3文字以上入力してください")//validatorの方2種類ある
	private String pass;
	
	@NotBlank(message="メールアドレスを入力してください")
	private String mail;
}
