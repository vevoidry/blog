package com.homework.web.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homework.web.pojo.Comment;
import com.homework.web.repository.CommentRepository;
import com.homework.web.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentRepository commentRepository;

	@Override
	public Comment insert(Comment comment) {
		comment.setCreate_time(new Date());
		return commentRepository.save(comment);
	}

	@Override
	public List<Comment> selectByArticle_id(Integer article_id) {
		return commentRepository.selectByArticle_id(article_id);
	}

}
