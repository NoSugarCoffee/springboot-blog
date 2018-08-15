package com.dll.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dll.exception.TipException;
import com.dll.model.Category;
import com.dll.model.Content;
import com.dll.model.RestResponseBo;
import com.dll.service.CategoryService;
import com.dll.service.ContentService;
import com.dll.util.Commons;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/admin/article")
public class ArticleController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ContentService contentService;
	/**
	 * 发布博客
	 */
	@PostMapping("/publish")
	@ResponseBody
	public RestResponseBo saveOrUpdateArticle(HttpServletRequest request ,Content content) {
		String category = request.getParameter("cate");
		String tags=request.getParameter("tags");
		try {
			contentService.publish(content,category);
		}catch(TipException te) {
			return RestResponseBo.fail(te.getMessage());
		}
		request.setAttribute("commons",new Commons());
		return  RestResponseBo.ok();
	}
	
	/**
	 * 发布博客界面
	 * @param request
	 * @return
	 */
	@GetMapping("/publish")
	public String article_edit(HttpServletRequest request) {
		List<Category> categories = categoryService.findAll();
        System.out.println(categories);
		request.setAttribute("categories", categories);
        request.setAttribute("commons",new Commons());
		return "/admin/article_edit";
	}
	/**
	 * 编辑博客页面
	 * @param request
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public String edit(HttpServletRequest request,@PathVariable Integer id) {
		Content content = contentService.findById(id);
		request.setAttribute("contents", content);
		request.setAttribute("commons",new Commons());
	    request.setAttribute("categories", categoryService.findAll());
		return "/admin/article_edit";
	}
	
	
	@GetMapping
	public String listArticles(@RequestParam(defaultValue="1") Integer page,@RequestParam(defaultValue="6") Integer pageSize,HttpServletRequest request) {
		PageInfo<Content> articles = contentService.findByPage(page, pageSize);
		request.setAttribute("articles", articles);
		request.setAttribute("commons",new Commons());
		return "/admin/article_list";
	}
	
	@PostMapping("/delete")
	@ResponseBody
	public RestResponseBo delete(@RequestParam Integer cid) {
		try {
			contentService.deleteById(cid);
		}catch(Exception e) {
			return RestResponseBo.fail();
		}
		return RestResponseBo.ok();
	}
	
	
}
