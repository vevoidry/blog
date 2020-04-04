package com.homework.web.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homework.web.pojo.Article;
import com.homework.web.pojo.Comment;
import com.homework.web.repository.ArticleRepository;
import com.homework.web.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	ArticleRepository articleRepository;

	@Override
	public Integer selectCountByCategory_id(Integer category_id) {
		return articleRepository.selectCountByCategory_id(category_id);
	}

	@Override
	public List<Article> selectByCategory_idPage(Integer category_id, Integer page) {
		Integer limit_start = (page - 1) * 10;
		Integer limit_end = 10;
		return articleRepository.selectByCategory_idPage(category_id, limit_start, limit_end);
	}

	@Override
	public Article insert(Article article) {
		article.setCreate_time(new Date());
		return articleRepository.save(article);
	}

	@Override
	public Article selectById(Integer id) {
		return articleRepository.findById(id).get();
	}


}
