package com.dll;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dll.dao.CategoryMapper;
import com.dll.model.Category;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CategroyMapperTest {
	@Autowired
	CategoryMapper categoryMapper;
	/*@Before
	public void pre() throws IOException {
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("MyBatis.xml"));
		sqlSession = sqlSessionFactory.openSession();
		categoryMapper=sqlSession.getMapper(CategoryMapper.class);
	}
	@After
	public void aft() {
		sqlSession.close();
	}*/
	@Test
	public void testCountByCategory() {
		List<Category> findAll = categoryMapper.findAll();
		for (Category category : findAll) {
			System.out.println(category);
		}
	}
}
