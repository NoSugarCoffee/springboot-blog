package com.dll.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.dll.model.Option;

public interface OptionMapper {
	@Select("select * from options")
	public List<Option> getOptions();
}
