package com.homework.web.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.homework.web.pojo.Article;
import com.homework.web.pojo.Comment;
import com.homework.web.pojo.User;
import com.homework.web.service.impl.ArticleServiceImpl;
import com.homework.web.service.impl.CommentServiceImpl;
import com.homework.web.service.impl.UserServiceImpl;

@Controller
@RequestMapping("/articles")
public class ArticleController {

	@Autowired
	ArticleServiceImpl articleServiceImpl;
	@Autowired
	UserServiceImpl userServiceImpl;
	@Autowired
	CommentServiceImpl commentServiceImpl;

	@PostMapping
	public String post(Integer category_id, String title, String content, Authentication authentication) {
		String username = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal())
				.getUsername();
		User me = userServiceImpl.selectByUsername(username);
		title = title.trim();
		content = content.trim();
		if (title.equals("")) {
			throw new RuntimeException("标题不可为空");
		}
		if (content.equals("")) {
			throw new RuntimeException("内容不可为空");
		}
		Article article = new Article();
		article.setTitle(title);
		article.setContent(content);
		article.setUser_id(me.getId());
		article.setCategory_id(category_id);
		Article article2 = articleServiceImpl.insert(article);
		return "redirect:/articles/" + article2.getId();
	}

	@GetMapping("/{id:[0-9]*}")
	public String get(@PathVariable Integer id, Model model) {
		Article article = articleServiceImpl.selectById(id);
		LinkedHashMap<Comment, User> map = new LinkedHashMap<Comment, User>();
		List<Comment> commentList = commentServiceImpl.selectByArticle_id(id);
		Iterator<Comment> iterator = commentList.iterator();
		while (iterator.hasNext()) {
			Comment next = iterator.next();
			User user = userServiceImpl.selectById(next.getUser_id());
			map.put(next, user);
		}
		model.addAttribute("article", article);
		model.addAttribute("map", map);
		return "article";
	}

}
