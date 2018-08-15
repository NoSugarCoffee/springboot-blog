package com.dll.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.dll.model.User;

@Repository
public interface UserMapper {
	//1.根据用户名查询
	@Select("select * from users where username =#{username}")
	public User queryByUsername(String username);

	@Select("select * from users where uid=#{id}")
	public User findById(Integer id);
}
