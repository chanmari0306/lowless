package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Posts {

	private Integer post_id;
	private Integer thread_id;
	private String user_id;
	private String post;
//	private LocalDateTime post_date;
}
