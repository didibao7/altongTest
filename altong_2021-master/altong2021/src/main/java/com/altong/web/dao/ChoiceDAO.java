package com.altong.web.dao;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.altong.web.logic.ChoiceVO;

@Repository
public class ChoiceDAO {
	@Autowired
	public SqlSession sqlSession;
	
	public ChoiceVO getAnswerQuestionSpChoice() {
		return sqlSession.selectOne("getAnswerQuestionSpChoice");
	}
	
	public ChoiceVO getAnswerQuestionChoice(HashMap<String, Object> param) {
		return sqlSession.selectOne("getAnswerQuestionChoice", param);
	}
}
