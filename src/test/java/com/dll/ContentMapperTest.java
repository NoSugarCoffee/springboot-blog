package com.dll;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dll.dao.ContentMapper;
import com.dll.model.Archive;
import com.dll.model.Category;
import com.dll.model.Content;
import com.dll.util.DateKit;

public class ContentMapperTest {
	private SqlSession sqlSession;
	private ContentMapper contentMapper;
	@Before
	public void pre() throws IOException {
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("MyBatis.xml"));
		sqlSession = sqlSessionFactory.openSession();
		contentMapper=sqlSession.getMapper(ContentMapper.class);
	}
	@After
	public void aft() {
		sqlSession.close();
	}
	//select * from contents contents,categories where contents.category=categories.caid 
	@Test
	public void testGetContents() {
		List<Content> contents = contentMapper.findAll();
		for (Content content : contents) {
			System.out.println(content);
		}
	}
	//select * from contents,categories where contents.category=categories.caid and cid=? 
	@Test
	public void testFindById() {
		Content findOne = contentMapper.findById(2);
		System.out.println(findOne);
	}
	
	public void testSave() {
		Content content=new Content();
		Category category=new Category();
		category.setCaid(1);
		content.setTitle("this is title");
		content.setCategory(category);
		content.setContent("## this is content");
		contentMapper.save(content);
		sqlSession.commit();
		
	}
	public void testArchiveByCreateTime() throws ParseException {
		List<Archive> archives = contentMapper.archiveByCreateTime();
		
		for (Archive archive : archives) {
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(DateKit.dateFormat(archive.getDate(),"yyyy-MM"));
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
			Date parse = sdf.parse(archive.getDate());
			long start = calendar.getTimeInMillis();
			calendar.add(Calendar.MONTH, 1);
			long end = calendar.getTimeInMillis();
			List<Content> lists = contentMapper.findByTime(start/1000,end/1000);
			archive.setContents(lists);
		}
	}
	
	@Test
	public void getArchiveBySearch() {
		List<Content> findByTitleLikeOrContentLike = contentMapper.findByTitleLikeOrContentLike("%hello%");
		for (Content content : findByTitleLikeOrContentLike) {
			System.out.println(content);
		}
	}
}
