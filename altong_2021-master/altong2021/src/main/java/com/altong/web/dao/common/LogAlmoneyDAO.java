package com.altong.web.dao.common;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.altong.web.logic.LogAlmoneyVO;

@Repository
public class LogAlmoneyDAO {
	@Autowired
	public SqlSession sqlSession;
	
	public LogAlmoneyVO getAnswerViewLog(HashMap<String, Object> param) {
		return sqlSession.selectOne("getAnswerViewLog", param);
	}
	
	public LogAlmoneyVO getAlmoneyLogSum(HashMap<String, Object> param) {
		return sqlSession.selectOne("getAlmoneyLogSum", param);
	}
	
	public HashMap<String, Object> getAlmoneyLogTotal(int userSeq) {
		return sqlSession.selectOne("getAlmoneyLogTotal", userSeq);
	}
	
	public int getOtherViewCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getOtherViewCount", param);
	}
	
	public List<LogAlmoneyVO> getOtherViewList(HashMap<String, Object> param) {
		return sqlSession.selectList("getOtherViewList", param);
	}
	
	public HashMap<String, Object> getTotalAlmoney() {
		return sqlSession.selectOne("getTotalAlmoney");
	}
	
	public int getAdmAlmoneyCount() {
		return sqlSession.selectOne("getAdmAlmoneyCount");
	}
	
	public List<LogAlmoneyVO> getAdmAlmoneyLimit(HashMap<String, Object> param) {
		return sqlSession.selectList("getAdmAlmoneyLimit", param);
	}
}
