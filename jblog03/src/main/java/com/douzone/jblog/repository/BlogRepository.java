package com.douzone.jblog.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;

@Repository
public class BlogRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public void update(BlogVo vo) {
		sqlSession.update("blog.update",vo);
		
	}

	public BlogVo getBlogBasic(String blogId) {
		
		return sqlSession.selectOne("blog.findById",blogId);
	}

	public List<CategoryVo> getBlogCategory(String blogId) {
		List<CategoryVo> list = new ArrayList<>();
		list = sqlSession.selectList("blog.findCategoryById", blogId);
		return list;
	}

}
