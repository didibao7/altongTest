package com.altong.web.dao.report;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDAO {
	@Autowired
	public SqlSession sqlSession;
	
	public int getQuestionReportCnt(HashMap<String, Object> param) {
		return sqlSession.selectOne("getQuestionReportCnt", param);
	}
	
	public int getReportActionCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getReportActionCount", param);
	}
}
