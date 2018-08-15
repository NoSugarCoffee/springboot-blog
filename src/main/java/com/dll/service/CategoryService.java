package com.dll.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dll.dao.CategoryMapper;
import com.dll.dao.ContentMapper;
import com.dll.model.Category;

@Service
public class CategoryService {
	@Autowired
	private CategoryMapper categoryDao;

	@Autowired
	private ContentMapper contentDao;
	//当查询分类的时候 分类对应的数量也需要一起查出来
	public List<Category> findAll() {
		List<Category> categories=categoryDao.findAll();
		for (Category category : categories) {
			int count=contentDao.countByCategory(category.getCaid());
			category.setCount(count);
		}
		return categories;
	}
	
	public Category findById(Integer id) {
		return categoryDao.findById(id);
	}
	public void saveOrUpdate(Category category) {
		// TODO Auto-generated method stub
		if(category.getCaid()==null || category.getCaid().equals("")) {
			System.out.println("save");
			categoryDao.save(category);
		}else {
			System.out.println("update");
			categoryDao.update(category);
		}
	}

	public void deleteCategoryById(String caid) {
		// TODO Auto-generated method stub
		categoryDao.deleteById(Integer.parseInt(caid));
	}

}
