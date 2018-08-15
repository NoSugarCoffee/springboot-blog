package com.dll.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dll.exception.TipException;
import com.dll.model.Archive;
import com.dll.model.Comment;
import com.dll.model.Content;
import com.dll.model.Option;
import com.dll.model.RestResponseBo;
import com.dll.service.CommentService;
import com.dll.service.ContentService;
import com.dll.service.LinkService;
import com.dll.service.OptionService;
import com.dll.util.Commons;
import com.github.pagehelper.PageInfo;

@Controller
public class IndexController {
	@Autowired
	private ContentService contentService;
	@Autowired
	private LinkService linkService;
	@Autowired
	private OptionService optionService;
	@Autowired
	private CommentService commentService;
	
	/**
	 * 网站首页
	 * @return
	 * @author DELL
	 */
	@GetMapping("/")
	public String index(HttpServletRequest request,@RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {
		return this.index(request, 1, pageSize);
	}
	
	@GetMapping("/page/{index}")
	public String index(HttpServletRequest request, @PathVariable int index, @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {
		PageInfo<Content> pageInfo = contentService.findByPageAndStatus(index, pageSize);
		//设置文章信息
		request.setAttribute("articles", pageInfo);
		//设置social信息
		setInfo(request);
		return "themes/default/index";
	}
	/**
	 * 根据文章id查询
	 * @return
	 */
	@GetMapping("/article/{cid}")
	public String getArticle(@PathVariable Integer cid,HttpServletRequest request) {
		request.setAttribute("article", contentService.findById(cid));
		
		request.setAttribute("comments", commentService.findByIdAndStatus(cid,"approved"));
		setInfo(request);
		request.setAttribute("is_post", true);
		return "themes/default/post";
	}
	
	@GetMapping("/links")
	public String links(HttpServletRequest request) {
		request.setAttribute("links", linkService.findAll());
		setInfo(request);		
		return "themes/default/links";
	}
	
	@GetMapping("/archives")
	public String archives(HttpServletRequest request) throws ParseException {
		List<Archive> archives = contentService.archives();
		request.setAttribute("archives", archives);
		setInfo(request);
		return "themes/default/archives";
	}
	
	@GetMapping("/about")
	public String about(HttpServletRequest request) {
		Content content = contentService.findById(1);
		request.setAttribute("article", content);
		setInfo(request);
		return "themes/default/page";
	}
	
	@GetMapping("/search/{keywords}")
	public String search(HttpServletRequest request,@PathVariable String keywords) {
		request.setAttribute("articles", contentService.findByTitleLikeOrContentLike(keywords));
		setInfo(request);
		return "themes/default/page-category";
	}
	
	@PostMapping("/comment")
	@ResponseBody
	public RestResponseBo comment(Comment comment) {
		try {
			commentService.save(comment);
			return RestResponseBo.ok();
		}catch(Exception e) {
			String msg="评论失败!";
			if(e instanceof TipException) {
				msg=e.getMessage();
			}
			return RestResponseBo.fail(msg);
		}
	}
	
	public void setInfo(HttpServletRequest request) {
		List<Option> options = optionService.getOptions();
		request.setAttribute("social", options);
		request.setAttribute("commons",new Commons());
	}
	
}
