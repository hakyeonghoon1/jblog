package com.douzone.jblog.repository;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.exception.UserRepositoryException;
import com.douzone.jblog.vo.UserVo;

@Repository
public class UserRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public void insert(@Valid UserVo vo) {
		sqlSession.insert("user.insert",vo);
		
	}

	public UserVo getUser(String id, String password) throws UserRepositoryException {
		Map<String, String> map = new HashMap<>();
		map.put("id", id);
		map.put("password", password);
		return sqlSession.selectOne("user.getUserByIdAndPassword",map);
	}

}
