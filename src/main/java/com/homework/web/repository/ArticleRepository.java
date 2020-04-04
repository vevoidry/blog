package com.homework.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.homework.web.pojo.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
	@Query(value = "select count(*) from article where category_id = :category_id", nativeQuery = true)
	Integer selectCountByCategory_id(Integer category_id);

	@Query(value = "select * from article where category_id = :category_id limit :limit_start,:limit_end", nativeQuery = true)
	List<Article> selectByCategory_idPage(Integer category_id, Integer limit_start, Integer limit_end);
}
