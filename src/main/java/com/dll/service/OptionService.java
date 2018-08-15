package com.dll.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dll.dao.OptionMapper;
import com.dll.model.Option;

@Service
public class OptionService {
	
	@Autowired
	private OptionMapper optionDao;
	
	/**
	 * 获取所有的option 
	 * @return
	 */
	public List<Option> getOptions(){
		return optionDao.getOptions();
	}
}
