package com.altong.web.dao;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.altong.web.logic.LogEstimateVO;

@Repository
public class LogEstimateDAO {
	@Autowired
	public SqlSession sqlSession;
	
	public LogEstimateVO getLogEstimateBySeq(HashMap<String, Object> param) {
		return sqlSession.selectOne("getLogEstimateBySeq", param);
	}
}
