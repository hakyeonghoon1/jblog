package com.douzone.jblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.jblog.security.Auth;
import com.douzone.jblog.security.AuthUser;
import com.douzone.jblog.vo.UserVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {

	
	@RequestMapping("")
	public String main(@PathVariable("id") String blogId, Model model) {
		
		model.addAttribute("blogId", blogId);
		return "blog/blog-main";
	}
	
	@Auth
	@RequestMapping("/admin")
	public String adminBasic(@PathVariable("id") String blogId,
							 @AuthUser UserVo authUser) {
		System.out.println(blogId);
		System.out.println(authUser.getId());
		
		return "blog/blog-admin-basic";
	}
}
