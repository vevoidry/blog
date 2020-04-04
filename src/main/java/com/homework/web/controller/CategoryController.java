package com.homework.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homework.web.pojo.Article;
import com.homework.web.pojo.Category;
import com.homework.web.service.impl.ArticleServiceImpl;
import com.homework.web.service.impl.CategoryServiceImpl;

@Controller
@RequestMapping("/categorys")
public class CategoryController {

	@Autowired
	CategoryServiceImpl categoryServiceImpl;
	@Autowired
	ArticleServiceImpl articleServiceImpl;

	@GetMapping("/{category_id:[0-9]*}/page")
	public String page(@PathVariable Integer category_id, Model model) {
		Integer count = articleServiceImpl.selectCountByCategory_id(category_id);
		Integer page = (count - 1) / 10 + 1;
		ArrayList<Integer> pageList = new ArrayList<Integer>();
		for (int i = 1; i <= page; i++) {
			pageList.add(i);
		}
		model.addAttribute("pageList", pageList);
		model.addAttribute("category_id", category_id);
		return "fragment/index_fragment::page";
	}

	@GetMapping("/{category_id:[0-9]*}/article/{page:[0-9]*}")
	public String article(@PathVariable Integer category_id, @PathVariable Integer page, Model model) {
		List<Article> articleList = articleServiceImpl.selectByCategory_idPage(category_id, page);
		model.addAttribute("articleList", articleList);
		return "fragment/index_fragment::article";
	}

	@PostMapping
	public String post(String name) {
		name = name.trim();
		if (name.equals("")) {
			throw new RuntimeException("分类名不可为空");
		}
		Category category2 = categoryServiceImpl.selectByName(name);
		if (category2 != null) {
			throw new RuntimeException("该分类名已存在");
		}
		Category category = new Category();
		category.setName(name);
		categoryServiceImpl.insert(category);
		return "redirect:/admin";
	}

	@GetMapping("/{category_id:[0-9]*}/is_using")
	public String is_using(@PathVariable Integer category_id) {
		Category category = categoryServiceImpl.selectById(category_id);
		category.setIs_using(!category.getIs_using());
		categoryServiceImpl.update(category);
		return "redirect:/admin";
	}
}
