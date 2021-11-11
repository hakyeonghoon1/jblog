package com.douzone.jblog.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.jblog.dto.JsonResult;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.PostVo;

@Controller
@RequestMapping("/api/{id:(?!assets$|images$).*}")
public class ApiController {
	
	@Autowired
	private BlogService blogService;

	@ResponseBody
	@RequestMapping("/{categoryNo}")
	public Object postList(
				@PathVariable("id") String blogId,
				@PathVariable("categoryNo") Long categoryNo		
			) {
		System.out.println("blogId:"+blogId + " categoryNo:"+categoryNo);
		List<PostVo> postList = new ArrayList<PostVo>();
		postList = blogService.getPostList(categoryNo,blogId);
		return JsonResult.success(postList);
	}
}
