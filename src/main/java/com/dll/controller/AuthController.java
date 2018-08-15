package com.dll.controller;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dll.exception.TipException;
import com.dll.model.RestResponseBo;
import com.dll.model.User;
import com.dll.service.UserService;

/**
 * 用户后台登录/登出
 */
@Controller
@RequestMapping("/admin")
public class AuthController{

	@Autowired
	private UserService userService;
	/**
	 * 返回登录页面
	 * @return
	 */
    @GetMapping({"","/login"})
    public String login(HttpServletRequest req) {
        return "/admin/login";
    }
    
    /**
     * 登录验证
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public RestResponseBo doLogin(
    		@RequestParam String username,
            @RequestParam String password,
            @RequestParam(required = false) String remeber_me,
            HttpServletRequest req,
            HttpServletResponse resp) {
    	try {
    		User user=null;
//    		Cookie[] cookies = req.getCookies();
//    		for (Cookie cookie : cookies) {
//				if(cookie.getName().equals("user_in_cookie")) {
//					user = userService.findById(Integer.parseInt(cookie.getValue()));
//					req.getSession().setAttribute("user_in_session",user);
//				}
//			}
//    		if(user!=null) {
//    			return RestResponseBo.ok();
//    		}
    		
    		user = userService.login(username, password);
    		req.getSession().setAttribute("user_in_session",user);
    		if("on".equals(remeber_me)) {
    			//这里增加cookie
    			Cookie cookie=new Cookie("user_in_cookie",user.getUid().toString());
    			cookie.setMaxAge(60*60);
    			resp.addCookie(cookie);
    			System.out.println("add "+cookie.getName()+":"+cookie.getValue());
    		}
    	}catch(Exception e){
    		String msg="出问题了";
    		if(e instanceof TipException){
    			msg=e.getMessage();
    		}
    		return RestResponseBo.fail(msg);
    	}
    	return RestResponseBo.ok();
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response) {
    	request.getSession().removeAttribute("user_in_session");
    	Cookie[] cookies = request.getCookies();
    	for (Cookie cookie : cookies) {
			if(cookie.getName().equals("user_in_cookie")) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}
		return "admin/login";
    }
    

}
