package com.douzone.jblog.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.security.Auth;
import com.douzone.jblog.security.AuthUser;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.UserVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {

	@Autowired
	private BlogService blogService;
	
	@Autowired
	private ServletContext servletContext;
	
	@RequestMapping("")
	public String main(@PathVariable("id") String blogId, Model model) {
		System.out.println(blogId);
		
		model.addAttribute("blogId", blogId);
		return "blog/blog-main";
	}
	
	@Auth
	@RequestMapping("/adminBasic")
	public String adminBasic(@PathVariable("id") String blogId,
							 @AuthUser UserVo authUser,
							 Model model) {
		if(!blogId.equals(authUser.getId())) {
			return "blog/blog-main";
		}
		
		BlogVo blogVo = blogService.getBlogBasic(authUser.getId());
		model.addAttribute("blogVo", blogVo);
		
		return "blog/blog-admin-basic";
	}
	
	@Auth
	@RequestMapping("/admin/updateBasic")
	public String updateBasic(String title, @RequestParam(value="logo-file") MultipartFile multipartFile, @AuthUser UserVo authUser, Model model) {
		
		blogService.update(title, multipartFile, authUser.getId());
		BlogVo blogVo = blogService.getBlogBasic(authUser.getId());
		//servletContext.setAttribute("blogVo", blogVo);
		System.out.println(blogVo);
		model.addAttribute("blogVo", blogVo);
		return "blog/blog-admin-basic";
	}
	
	@Auth
	@RequestMapping("/adminCategory")
	public String adminCategory(@PathVariable("id") String blogId,
							 @AuthUser UserVo authUser, Model model) {
		if(!blogId.equals(authUser.getId())) {
			return "blog/blog-main";
		}
		List<CategoryVo> list = new ArrayList<>(); 
		list = blogService.getBlogCategory(blogId);
		model.addAttribute("blogId", blogId);
		model.addAttribute("list", list);
		return "blog/blog-admin-category";
	}
	
	@Auth
	@RequestMapping("/adminWrite")
	public String adminWrite(@PathVariable("id") String blogId,
							 @AuthUser UserVo authUser) {
		if(!blogId.equals(authUser.getId())) {
			return "blog/blog-main";
		}
		
		return "blog/blog-admin-write";
	}

}
