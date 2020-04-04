package com.homework.web.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.homework.web.pojo.User;
import com.homework.web.service.impl.UserServiceImpl;

@Controller
@RequestMapping("/users")
public class UserController<V> {

	@Autowired
	UserServiceImpl userServiceImpl;
	@Autowired
	private AuthenticationManager authenticationManager;

	// 注册
	@PostMapping
	@ResponseBody
	public HashMap<String, String> post(User user, HttpServletRequest request) {
		// 获取用户名与密码并去除两侧空格
		String username = user.getUsername().trim();
		String password = user.getPassword().trim();
		// 判断位数是否符合要求
		if (username.length() < 6 || username.length() > 15 || password.length() < 6 || password.length() > 15) {
			throw new RuntimeException("用户名与密码的长度必须为6-15位");
		}
		// 判断输入的数据是否符合要求（必须为大小写字母或数字）
		Pattern p = Pattern.compile("[0-9A-Za-z]*");
		Matcher m1 = p.matcher(username);
		Matcher m2 = p.matcher(password);
		if (!m1.matches() || !m2.matches()) {
			throw new RuntimeException("用户名与密码只能为数字或大小写字母");
		}
		// 判断用户名是否已经被使用
		User user2 = userServiceImpl.selectByUsername(username);
		if (user2 != null) {
			throw new RuntimeException("该用户名已被使用，请重新输入");
		}
		// 根据注册信息创建新用户
		User user3 = userServiceImpl.insert(user);
		// 以下为实现自动登录功能
		// 生成Authentication
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user3.getUsername(),
				user3.getPassword());
		try {
			token.setDetails(new WebAuthenticationDetails(request));
			Authentication authenticatedUser = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
			request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
					SecurityContextHolder.getContext());
			// 设置session的user数据
			request.getSession().setAttribute("user", user3);
		} catch (Exception e) {
			System.out.println("Authentication failed: " + e.getMessage());
		}
		// 重定向
		return new HashMap<String, String>();
	}

	@GetMapping("/{id:[0-9]*}")
	public String get(@PathVariable Integer id, Model model) {
		User user = userServiceImpl.selectById(id);
		model.addAttribute("user", user);
		return "user";
	}

	@GetMapping("/edit")
	public String edit() {
		return "user_edit";
	}

	@PostMapping("/update")
	@ResponseBody
	public HashMap<String, String> update(String nickname, String profile, String birthday, Boolean gender,
			Authentication authentication, HttpServletRequest request, MultipartFile file_name) throws Exception {
		String username = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal())
				.getUsername();
		User me = userServiceImpl.selectByUsername(username);
		nickname = nickname.trim();
		if (nickname.equals("")) {
			throw new RuntimeException("昵称不可为空");
		}
		me.setNickname(nickname);
		profile = profile.trim();
		me.setProfile(profile);
		me.setGender(gender);
		if (!birthday.equals("")) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = simpleDateFormat.parse(birthday);
			me.setBirthday(date);
		}
		// 判断传输的文件名是否为空
		if (!file_name.getOriginalFilename().equals("")) {
			// 使用UUID新建随机字符串，作为文件保存时的真实命名，避免冲突
			String real_name = UUID.randomUUID().toString() + file_name.getOriginalFilename();
			// 获取用于保存上传文件的文件夹路径
			String directoryPath = ResourceUtils.getURL("src").getPath() + "main/resources/static/image/";
			// 保存文件
			file_name.transferTo(new File(directoryPath, real_name));
			me.setImage(real_name);
		}
		User user = userServiceImpl.update(me);
		request.getSession().setAttribute("user", user);
		return new HashMap<String, String>();
	}

}
