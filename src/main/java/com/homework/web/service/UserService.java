package com.homework.web.service;

import com.homework.web.pojo.User;

public interface UserService {
	User selectByUsername(String username);

	User insert(User user);
	
	User selectById(Integer id);
	
	User update(User user);
}
