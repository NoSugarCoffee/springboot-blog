package com.dll.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.dll.model.Link;

@Repository
public interface LinkMapper {
	@Select("select * from links")
	public List<Link> findAll();

	@Insert("insert into links(name,value) values(#{name},#{value})")
	public void save(Link link);
	
	@Update("update links set name=#{name},value=#{value} where lid=#{lid}")
	public void update (Link link);

	@Delete("delete from links where lid=#{lid}")
	public void delete(int parseInt);
}
