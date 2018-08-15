package com.dll.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dll.model.Link;
import com.dll.model.RestResponseBo;
import com.dll.service.LinkService;

@Controller
public class LinkController {
	@Autowired
	private LinkService linkService;
	
	@GetMapping("/admin/links")
	public String getLinks(HttpServletRequest request) {
		try {
			request.setAttribute("links",linkService.findAll());
		}catch(Exception e) {
			e.printStackTrace();
			return "comm/error_404";
		}
		return "admin/links";
	}
	
	@PostMapping("/admin/links/save")
	@ResponseBody
	public RestResponseBo<?> saveOrUpdate(Link link,HttpServletRequest request) {
		try {
			linkService.saveOrUpdate(link);
		}catch(Exception e) {
			return RestResponseBo.fail();
		}
		return RestResponseBo.ok();
	}
	
	@PostMapping("/admin/links/delete")
	@ResponseBody
	public RestResponseBo delete(String lid,HttpServletRequest request) {
		try {
			linkService.delete(lid);
		}catch(Exception e){
			return RestResponseBo.fail();
		}
		return RestResponseBo.ok();
	}
}
