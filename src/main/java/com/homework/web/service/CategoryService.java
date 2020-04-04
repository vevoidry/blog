package com.homework.web.service;

import java.util.List;

import com.homework.web.pojo.Category;

public interface CategoryService {

	List<Category> selectAll();
	
	Category insert(Category category);
	
	Category selectById(Integer id);
	
	Category update(Category category);
	
	Category selectByName(String name);
}
