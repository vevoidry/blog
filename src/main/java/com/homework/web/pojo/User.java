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
@Table(name = "user")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Integer id;

	@Column(name = "username")
	String username;

	@Column(name = "password")
	String password;

	@Column(name = "role")
	String role;// admin为管理员，vip为普通用户

	@Column(name = "nickname")
	String nickname;

	@Column(name = "image")
	String image;

	@Column(name = "gender")
	Boolean gender;// true为男，false为女

	@Column(name = "birthday")
	Date birthday;

	@Column(name = "profile")
	String profile;

}
