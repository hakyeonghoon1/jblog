package com.douzone.jblog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.jblog.service.AdminService;
import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(@ModelAttribute UserVo vo) {
		
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo vo, BindingResult result, Model model) {
		System.out.println(vo);
		if(result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "user/join";
		}
		userService.join(vo);
		
		BlogVo bVo = new BlogVo();
		bVo.setId(vo.getId());
		bVo.setTitle(vo.getName()+"님의 블로그");
		bVo.setLogo("기본이미지");
		adminService.insertBlog(bVo);
		
		CategoryVo cVo = new CategoryVo();
		cVo.setBlogId(bVo.getId());
		cVo.setName("미분류");
		cVo.setDesc("미분류");
		adminService.insertCategory(cVo);
		
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {

		return "user/login";
	}
	
	@RequestMapping(value="/login/{blogId}", method=RequestMethod.GET)
	public String login(@PathVariable("blogId") String blogId, Model model) {

		model.addAttribute("blogId", blogId);
		return "user/login";
	}
	
}
