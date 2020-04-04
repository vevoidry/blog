package com.homework.web.service;

import java.util.List;

import com.homework.web.pojo.Article;
import com.homework.web.pojo.Comment;

public interface ArticleService {
	Integer selectCountByCategory_id(Integer category_id);
	
	List<Article> selectByCategory_idPage(Integer category_id, Integer page);
	
	Article insert(Article article);
	
	Article selectById(Integer id);
	
}
