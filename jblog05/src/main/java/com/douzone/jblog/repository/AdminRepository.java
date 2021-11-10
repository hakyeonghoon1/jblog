package com.douzone.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;

@Repository
public class AdminRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public void insertCategory(CategoryVo vo) {
		sqlSession.insert("admin.insertCategory",vo);
	}

	public void insertBlog(BlogVo vo) {
		sqlSession.insert("admin.insertBlog",vo);
		
	}
	
	
}
