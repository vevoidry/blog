package com.homework.web.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homework.web.pojo.User;
import com.homework.web.repository.UserRepository;
import com.homework.web.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public User selectByUsername(String username) {
		return userRepository.selectByUsername(username);
	}

	@Override
	public User insert(User user) {
		Date date = new Date();
		user.setRole("vip");
		user.setNickname(date.getTime() + "");
		user.setImage("x.png");
		user.setGender(true);
		user.setBirthday(date);
		user.setProfile("该用户简介为空");
		return userRepository.save(user);
	}

	@Override
	public User selectById(Integer id) {
		return userRepository.findById(id).get();
	}

	@Override
	public User update(User user) {
		return userRepository.save(user);
	}

}
