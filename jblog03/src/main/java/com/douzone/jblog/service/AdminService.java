package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.AdminRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;
	
	public void insertCategory(CategoryVo vo) {
		adminRepository.insertCategory(vo);
	}
	
	public void insertBlog(BlogVo vo) {
		adminRepository.insertBlog(vo);
	}
}
