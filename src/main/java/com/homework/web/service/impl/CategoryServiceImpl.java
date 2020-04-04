package com.homework.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homework.web.pojo.Category;
import com.homework.web.repository.CategoryRepository;
import com.homework.web.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public List<Category> selectAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Category insert(Category category) {
		category.setIs_using(true);
		return categoryRepository.save(category);
	}

	@Override
	public Category selectById(Integer id) {
		return categoryRepository.findById(id).get();
	}

	@Override
	public Category update(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public Category selectByName(String name) {
		return categoryRepository.selectByName(name);
	}

}
