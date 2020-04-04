package com.homework.web.service;

import java.util.List;

import com.homework.web.pojo.Comment;

public interface CommentService {
	Comment insert(Comment comment);
	
	List<Comment> selectByArticle_id(Integer article_id);
}
