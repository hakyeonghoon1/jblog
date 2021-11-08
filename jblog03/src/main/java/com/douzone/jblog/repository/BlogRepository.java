package com.douzone.jblog.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Repository
public class BlogRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public void update(BlogVo vo) {
		sqlSession.update("blog.update",vo);
		
	}

	public BlogVo getBlogBasic(String blogId) {
		
		return sqlSession.selectOne("blog.findBlogById",blogId);
	}

	public List<CategoryVo> getBlogCategory(String blogId) {
		List<CategoryVo> list = new ArrayList<>();
		list = sqlSession.selectList("blog.findCategoryById", blogId);
		return list;
	}

	public boolean insertCategory(CategoryVo vo) {
		int count = sqlSession.insert("blog.insertCategory", vo);
		return count ==1;
	}

	public boolean insertPost(PostVo vo) {
		int count = sqlSession.insert("blog.insertPost",vo);
		return count ==1;
		
	}

	public List<PostVo> getPostList(Long categoryNo, String blogId) {
		Map<String, Object> map = new HashMap<>();
		map.put("categoryNo", categoryNo);
		map.put("blogId", blogId);
		return sqlSession.selectList("blog.selectPostByCategoryNo", map);
	}

	public PostVo getPost(String blogId, Long categoryNo, Long postNo) {
		Map<String, Object> map = new HashMap<>();
		map.put("blogId", blogId);
		map.put("categoryNo", categoryNo);
		map.put("postNo", postNo);
		
		return sqlSession.selectOne("blog.selectPost", map);
	}

}
