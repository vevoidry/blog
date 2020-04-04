package com.homework.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.homework.web.pojo.Comment;
import com.homework.web.pojo.User;
import com.homework.web.service.impl.CommentServiceImpl;
import com.homework.web.service.impl.UserServiceImpl;

@Controller
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	CommentServiceImpl commentServiceImpl;
	@Autowired
	UserServiceImpl userServiceImpl;

	@PostMapping
	public String post(Integer article_id, String content, Authentication authentication) {
		content = content.trim();
		if (content.equals("")) {
			throw new RuntimeException("回复不可为空");
		}
		String username = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal())
				.getUsername();
		User me = userServiceImpl.selectByUsername(username);
		Comment comment = new Comment();
		comment.setArticle_id(article_id);
		comment.setContent(content);
		comment.setUser_id(me.getId());
		commentServiceImpl.insert(comment);
		return "redirect:/articles/" + article_id;
	}

}
