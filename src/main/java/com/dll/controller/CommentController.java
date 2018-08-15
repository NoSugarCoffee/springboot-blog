package com.dll.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dll.model.RestResponseBo;
import com.dll.service.CommentService;
import com.dll.util.Commons;

@Controller
@RequestMapping("/admin/comments")
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	@GetMapping("")
	public String findAll(@RequestParam(value = "page", defaultValue = "1") int index,@RequestParam(value = "limit", defaultValue = "15") int pageSize, HttpServletRequest request) {
		request.setAttribute("comments", commentService.findAll(index,pageSize));
		request.setAttribute("commons",new Commons());
		return "admin/comment_list";
	}
	
	@PostMapping("/status")
	@ResponseBody
	public RestResponseBo changeStatus(@RequestParam(value="coid") Integer coid, HttpServletRequest request) {
		commentService.updateStatusById(coid);
		request.setAttribute("commons",new Commons());
		return RestResponseBo.ok();
	}
	
	@PostMapping("/delete")
	@ResponseBody
	public RestResponseBo deleteById(@RequestParam("coid")Integer coid, HttpServletRequest request) {
		commentService.deleteById(coid);
		request.setAttribute("commons",new Commons());
		return RestResponseBo.ok();
	}
	
}
