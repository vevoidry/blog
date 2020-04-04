package com.homework.web.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.homework.web.pojo.Category;
import com.homework.web.service.impl.CategoryServiceImpl;

@Controller
public class PageController {

	@Autowired
	CategoryServiceImpl categoryServiceImpl;

	@GetMapping("/")
	public String index(Model model) {
		// 取出所有目录
		List<Category> categoryList = categoryServiceImpl.selectAll();
		ArrayList<Category> categoryList2 = new ArrayList<Category>();
		Iterator<Category> iterator = categoryList.iterator();
		while (iterator.hasNext()) {
			Category category = iterator.next();
			if (category.getIs_using()) {
				categoryList2.add(category);
			}
		}
		model.addAttribute("categoryList", categoryList2);
		return "index";
	}

	@GetMapping("/login_register")
	public String login_register() {
		return "login_register";
	}

	@GetMapping("/admin")
	public String admin(Model model) {
		// 取出所有目录
		List<Category> categoryList = categoryServiceImpl.selectAll();
		model.addAttribute("categoryList", categoryList);
		return "admin";
	}

}
