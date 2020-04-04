package com.homework.security;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.homework.web.pojo.User;
import com.homework.web.service.impl.UserServiceImpl;

@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	@Autowired
	private UserServiceImpl userServiceImpl;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		logger.info("用户名为[" + authentication.getName() + "]的用户成功通过身份认证");
		// 将用户的基本信息放入session
		User user = userServiceImpl.selectByUsername(
				((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername());
		request.getSession().setAttribute("user", user);
//		System.out.println("1:"+user.getBirthday());
		// 跳转
		redirectStrategy.sendRedirect(request, response, "/");
	}
}
