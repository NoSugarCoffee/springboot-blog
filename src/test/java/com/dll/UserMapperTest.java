package com.dll;

/*import java.io.IOException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dll.dao.UserMapper;
import com.dll.model.User;


public class UserMapperTest {
	private SqlSession sqlSession;
	@Before
	public void pre() throws IOException {
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("MyBatis.xml"));
		sqlSession = sqlSessionFactory.openSession();
		
	}
	@After
	public void aft() {
		sqlSession.close();
	}
	
	@Test
	public void testQueryByUsername() {
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		User res = userMapper.queryByUsername("xx");
		System.out.println(res);
		
	}
}*/

