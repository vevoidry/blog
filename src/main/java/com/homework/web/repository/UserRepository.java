package com.homework.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.homework.web.pojo.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	@Query(value = "select * from user where username = :username", nativeQuery = true)
	User selectByUsername(String username);
}
