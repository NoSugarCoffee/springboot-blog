package com.dll.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dll.model.Category;
import com.dll.model.Content;
import com.dll.model.RestResponseBo;
import com.dll.model.Tag;
import com.dll.service.CategoryService;
import com.dll.service.ContentService;
import com.dll.util.AdminCommons;
import com.dll.util.Commons;
import com.github.pagehelper.PageInfo;

@Controller
public class CategoryController {
	@Autowired
	private ContentService contentService;
	@Autowired
	private CategoryService categoryService;
	
	
	@GetMapping("/category/{caid}")
	public String findByCategory(@RequestParam(name="page",defaultValue="1")Integer page,@RequestParam(name="pageSize",defaultValue="6")Integer pageSize,@PathVariable("caid")Integer caid,HttpServletRequest request) {
		Category categroy = categoryService.findById(caid);
		PageInfo<Content> articles = contentService.findByCategory(page,pageSize,caid);
		request.setAttribute("articles", articles);
		request.setAttribute("commons",new Commons());
		request.setAttribute("keyword", categroy.getName());
		return "themes/default/page-category";
	}
	
	@GetMapping("/admin/category")
	public String findAllCategories(HttpServletRequest request) {
		System.out.println("xxxx");
		List<Category> categories=categoryService.findAll();
		List<Content> contents = contentService.findAll();
		StringBuilder sb=new StringBuilder();
		for (Content content : contents) {
			if(content.getTags()!=null && !"".equals(content.getTags())) {
				sb.append(content.getTags());
				sb.append(",");
			}
		}
		String[] split = sb.toString().split(",");
		
		List<Tag> lists=new ArrayList<Tag>();
		Map<String,Tag> map=new HashMap<String,Tag>();
		for (String string : split) {
			if(map.get(string)==null) {
				map.put(string,new Tag(string,1));
			}else {
				map.get(string).setCount(map.get(string).getCount()+1);
			}
		}
		
		request.setAttribute("adminCommons", new AdminCommons());
		Set<Entry<String, Tag>> entrySet = map.entrySet();
		for (Entry<String, Tag> entry : entrySet) {
			System.out.println(entry.getValue());
			lists.add(entry.getValue());
		}
		request.setAttribute("categories", categories);
		request.setAttribute("tags", lists);
		return "/admin/category";
	}
	
	@PostMapping("/admin/category/saveOrUpdate")
	@ResponseBody
	public RestResponseBo saveCategory(Category category,HttpServletRequest request) {
		try {
			categoryService.saveOrUpdate(category);
		}catch(Exception e) {
			return RestResponseBo.fail();
		}
		return RestResponseBo.ok();
	}
	
	@PostMapping("/admin/category/delete")
	@ResponseBody
	public RestResponseBo deleteCategoryById(HttpServletRequest request) {
		System.out.println("delete categroy");
		try {
			String caid = request.getParameter("caid");
			categoryService.deleteCategoryById(caid);
		}catch(Exception e) {
			return RestResponseBo.fail();
		}
		return RestResponseBo.ok();
	}
}
