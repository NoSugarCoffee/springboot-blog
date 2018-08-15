package com.dll.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dll.model.StatisticsBo;
import com.dll.service.CommentService;
import com.dll.service.ContentService;
import com.dll.service.LinkService;
import com.dll.util.Commons;

@Controller
@RequestMapping("/admin")
public class AdminIndexController {

	@Autowired
	private ContentService contentService;
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private LinkService linkService;
	
	@GetMapping("/index")
	public String login(HttpServletRequest request) {
		boolean flag=true;
		//没有登录或者没有按保存
		if(request.getSession().getAttribute("user_in_session")==null) {
			flag=false;
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				System.out.println(cookie.getName());
				if(cookie.getName().equals("user_in_cookie")) {
					//可以登录
					flag=true;
					break;
				}
			}
		}
		if(flag) {
			int articles = contentService.getTotal();
			int comments=commentService.getTotal();
			int links=linkService.getTotal();
			StatisticsBo statisticsBo=new StatisticsBo(articles,comments,links);
			request.setAttribute("comments", commentService.findAll());;
			request.setAttribute("articles", contentService.findTop5NewsArticles());;
			request.setAttribute("commons", new Commons());
			request.setAttribute("statistics", statisticsBo);
			return "admin/index";
		}
		return "admin/login";
	}	
	
}
