package com.altong.web.dao;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MyZzimDAO {
	@Autowired
	public SqlSession sqlSession;
	
	public int getAnswerZimChk(HashMap<String, Object> param) {
		return sqlSession.selectOne("getAnswerZimChk", param);
	}
	
	public void setAnswerZzim(HashMap<String, Object> param) {
		sqlSession.insert("setAnswerZzim", param);
	}
	
	public void deleteZzim(HashMap<String, Object> param) {
		sqlSession.delete("deleteZzim", param);
	}
}
