package com.douzone.jblog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.security.Admin;
import com.douzone.jblog.security.AuthUser;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;

@Controller
@RequestMapping("/{id:(?!assets$|images$).*}")
public class BlogController {

	@Autowired
	private BlogService blogService;
	
	@RequestMapping({"/{categoryNo}/{postNo}","/{categoryNo}",""})
	public String main(@PathVariable("id") String blogId,
					   @PathVariable(value="categoryNo", required=false) Long categoryNo,
					   @PathVariable(value="postNo", required=false) Long postNo,
					   Model model) {
		System.out.println(blogId+"cNo:"+categoryNo+"pNo:"+postNo);
		if( categoryNo == null && postNo==null ) {
			categoryNo=blogService.getRecentPostCategoryNo(blogId);
			List<PostVo> postList = new ArrayList<PostVo>();
			BlogVo blogVo = blogService.getBlogBasic(blogId);
			List<CategoryVo> categoryList = blogService.getBlogCategory(blogId);
			postList = blogService.getPostList(categoryNo,blogId);
			PostVo postVo = blogService.getPost(blogId, categoryNo, postNo); 
			
			model.addAttribute("blogId", blogId);
			model.addAttribute("blogVo", blogVo);
			model.addAttribute("categoryList", categoryList);
			model.addAttribute("postList", postList);
			model.addAttribute("postVo", postVo);
			
			return "blog/blog-main";
			
		} else if(categoryNo != null && postNo==null) {
			
			List<PostVo> postList = new ArrayList<PostVo>();
			BlogVo blogVo = blogService.getBlogBasic(blogId);
			List<CategoryVo> categoryList = blogService.getBlogCategory(blogId);
			postList = blogService.getPostList(categoryNo,blogId);
			PostVo postVo = blogService.getPost(blogId, categoryNo, postNo);
			
			model.addAttribute("blogId", blogId);
			model.addAttribute("blogVo", blogVo);
			model.addAttribute("categoryList", categoryList);
			model.addAttribute("postList", postList);
			model.addAttribute("postVo", postVo);
			
			return "blog/blog-main";
		} else {
			
			List<PostVo> postList = new ArrayList<PostVo>();
			BlogVo blogVo = blogService.getBlogBasic(blogId);
			List<CategoryVo> categoryList = blogService.getBlogCategory(blogId);
			postList = blogService.getPostList(categoryNo,blogId);
			PostVo postVo = blogService.getPost(blogId, categoryNo, postNo);
			
			model.addAttribute("blogId", blogId);
			model.addAttribute("blogVo", blogVo);
			model.addAttribute("categoryList", categoryList);
			model.addAttribute("postList", postList);
			model.addAttribute("postVo", postVo);
			
			return "blog/blog-main";
		}
	}
		
	@Admin
	@RequestMapping("/admin/basic")
	public String adminBasic(@PathVariable("id") String blogId,
							 @AuthUser UserVo authUser,
							 Model model) {
//		if(!blogId.equals(authUser.getId())) {
//			return "redirect:/"+blogId;
//		}
		
		BlogVo blogVo = blogService.getBlogBasic(authUser.getId());
		model.addAttribute("blogVo", blogVo);
		
		return "blog/blog-admin-basic";
	}
	
	@Admin
	@RequestMapping("/admin/updateBasic")
	public String updateBasic(String title, @RequestParam(value="logo-file") MultipartFile multipartFile, @AuthUser UserVo authUser, Model model) {
		
		blogService.update(title, multipartFile, authUser.getId());
		BlogVo blogVo = blogService.getBlogBasic(authUser.getId());
		//servletContext.setAttribute("blogVo", blogVo);
		System.out.println(blogVo);
		model.addAttribute("blogVo", blogVo);
		return "blog/blog-admin-basic";
	}
	
	@Admin
	@RequestMapping("/admin/category")
	public String adminCategory(@PathVariable("id") String blogId,
							 @AuthUser UserVo authUser, Model model) {
		if(!blogId.equals(authUser.getId())) {
			return "redirect:/"+blogId;
		}
		List<CategoryVo> list = new ArrayList<>(); 
		list = blogService.getBlogCategory(blogId);
		BlogVo blogVo = blogService.getBlogBasic(authUser.getId());
		
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("blogId", blogId);
		model.addAttribute("list", list);
		return "blog/blog-admin-category";
	}
	
	@Admin
	@RequestMapping("/admin/categoryInsert")
	public String adminCategoryInsert(String name, String desc,
									  @PathVariable("id") String blogId,
									  @AuthUser UserVo authUser) {
		if(!blogId.equals(authUser.getId())) {
			return "redirect:/"+blogId;
		}
		CategoryVo vo = new CategoryVo();
		vo.setBlogId(blogId);
		vo.setDesc(desc);
		vo.setName(name);
		blogService.insertCategory(vo);
		return "redirect:/"+blogId+"/adminCategory";
	}
	
	@Admin
	@RequestMapping("/admin/category/delete/{categoryNo}")
	public String adminCategoryDelete(@PathVariable("categoryNo") Long categoryNo,
									  @PathVariable("id") String blogId
									  ) {
		
		blogService.deleteCategory(categoryNo);
		return "redirect:/"+blogId+"/adminCategory";
	}
	
	@Admin
	@RequestMapping("/admin/write")
	public String adminWrite(@PathVariable("id") String blogId,
							 @AuthUser UserVo authUser, Model model) {
		if(!blogId.equals(authUser.getId())) {
			return "redirect:/"+blogId;
		}
		List<CategoryVo> categoryList = blogService.getBlogCategory(blogId);
		BlogVo blogVo = blogService.getBlogBasic(authUser.getId());
		
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("blogId", blogId);
		return "blog/blog-admin-write";
	}
	
	@Admin
	@RequestMapping("/admin/writeInsert")
	public String postInsert(String title, String content,
							@PathVariable("id") String blogId,
							@AuthUser UserVo authUser,
							Long category) {
		if(!blogId.equals(authUser.getId())) {
			return "redirect:/"+blogId;
		}
		PostVo vo = new PostVo();
		vo.setCategoryNo(category);
		vo.setContents(content);
		vo.setTitle(title);
		
		blogService.insertPost(vo);
		return "redirect:/"+blogId;
	}

}
