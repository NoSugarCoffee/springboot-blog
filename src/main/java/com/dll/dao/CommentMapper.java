package com.dll.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dll.model.Comment;
import com.github.pagehelper.PageInfo;

@Repository
public interface CommentMapper {

	@Insert("insert into comments(cid,created,author,url,content,mail) values(#{cid},#{created},#{author},#{url},#{content},#{mail})")
	public void save(Comment comment);

	@Select("select * from comments where cid=#{cid}")
	public List<Comment> findById(Integer cid);

	@Select("select * from comments")
	public List<Comment> findAll();
	
	@Update("update comments set status='approved' where coid=#{coid}")
	public void updateStatusById(Integer coid);

	@Delete("delete from comments where coid=#{coid}")
	public void deleteById(Integer coid);

	@Select("select * from comments where cid=#{cid} and status=#{status}")
	public List<Comment> findByIdAndStatus(@Param("cid")Integer cid, @Param("status")String status);
	
	@Select("select count(*) from comments where cid=#{cid} and status='approved'")
	public int countByContentId(Integer cid);
}
