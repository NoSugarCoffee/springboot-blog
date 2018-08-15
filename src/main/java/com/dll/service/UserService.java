package com.dll.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.result.HeaderResultMatchers;

import com.dll.dao.UserMapper;
import com.dll.exception.TipException;
import com.dll.model.User;
import com.dll.util.TaleUtils;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userDao;
	
	public User login(String username, String password) {
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new TipException("用户名和密码不能为空");
        }
		User user = userDao.queryByUsername(username);
		if(user==null || !user.getPassword().equals(TaleUtils.MD5encode(password))) {
			System.out.println(user.getPassword());
			System.out.println(TaleUtils.MD5encode(password));
			throw new TipException("用户名或密码错误");
		}
		return user;
	}
	
	public User findById(Integer id) {
		return userDao.findById(id);
	}
}
