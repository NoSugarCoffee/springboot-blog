package com.dll.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.dll.model.Category;

@Repository
public interface CategoryMapper {
	@Select("select * from categories")
	public List<Category> findAll();
	
	@Select("select * from categories where caid=#{caid}")
	public Category findById(Integer id);

	@Insert("insert into categories(name) values(#{name})")
	public void save(Category category);

	@Delete("delete from categories where caid=#{caid}")
	public void deleteById(int caid);
	
	@Update("update Categories set name=#{name} where caid=#{caid}")
	public void update(Category category);
}
