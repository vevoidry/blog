package com.homework.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.homework.web.pojo.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	@Query(value = "select * from comment where article_id = :article_id", nativeQuery = true)
	List<Comment> selectByArticle_id(Integer article_id);
}
