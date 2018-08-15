package com.dll.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.dll.model.Archive;
import com.dll.model.Category;
import com.dll.model.Content;
import com.github.pagehelper.PageInfo;

@Repository
public interface ContentMapper {
	public Content findById(Integer cid);

	public void save(Content content);

	public List<Content> findAll();
	
	public List<Content> findByCategory(Integer caid);
	
	
	public List<Archive> archiveByCreateTime();

	@Select("select * from contents where created >=#{start} and created <#{end}")
	public List<Content> findByTime(@Param("start")long start,@Param("end") long end);
		
	public List<Content> findByTitleLikeOrContentLike(@Param("keywords")String keywords);

	@Select("select count(*) from contents where category=#{caid}")
	public int countByCategory(Integer caid);

	@Update("update contents set title=#{title},content=#{content},tags=#{tags},category=#{category.caid},status=#{status} where cid=#{cid}")
	public void update(Content content);

	@Select("select * from contents order by created desc limit 0,5")
	public List<Content> findTop5NewsArticles();

	public List<Content> findAllAndStatus();

	@Delete("delete from contents where cid=#{cid}")
	public void deleteById(Integer cid);
	
}
