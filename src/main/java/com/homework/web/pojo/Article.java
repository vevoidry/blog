package com.homework.web.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "article")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Integer id;

	@Column(name = "category_id")
	Integer category_id;

	@Column(name = "user_id")
	Integer user_id;

	@Column(name = "create_time")
	Date create_time;

	@Column(name = "title")
	String title;

	@Column(name = "content")
	String content;

}
