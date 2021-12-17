package com.altong.web.dao.alpay;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AlpayDAO {
	@Autowired
	public SqlSession sqlSession;
	
	public int getUserAlpayKR(int userSeq) {
		return sqlSession.selectOne("getUserAlpayKR", userSeq);
	}
	
	public HashMap<String, Object> getAlpayLastAccount(int userSeq) {
		return sqlSession.selectOne("getAlpayLastAccount", userSeq);
	}
	
	public int setAlpayExchange(HashMap<String, Object> param) {
		return sqlSession.selectOne("setAlpayExchange", param);
	}
	
	public List<HashMap<String, Object>> getAlpayExchLimitBySeq(HashMap<String, Object> param) {
		return sqlSession.selectList("getAlpayExchLimitBySeq", param);
	}
	
	public List<HashMap<String, Object>> getAlpayExchLimitAll(HashMap<String, Object> param) {
		return sqlSession.selectList("getAlpayExchLimitAll", param);
	}
	
	public HashMap<String, Object> updateExch(HashMap<String, Object> param) {
		return sqlSession.selectOne("updateExch", param);
	}
	
	public HashMap<String, Object> rejectExch(HashMap<String, Object> param) {
		return sqlSession.selectOne("rejectExch", param);
	}
	
	public List<HashMap<String, Object>> getAlpayExchList() {
		return sqlSession.selectList("getAlpayExchList");
	}
	
	public HashMap<String, Object> changeExchangeAsk(HashMap<String, Object> param) {
		return sqlSession.selectOne("changeExchangeAsk", param);
	}
	
	public void getMemoExchReadySetSP(int lv) {
		sqlSession.insert("getMemoExchReadySetSP", lv);
	}
	
	public int getMemoExchReadyCntBySeq(int seq) {
		return sqlSession.selectOne("getMemoExchReadyCntBySeq", seq);
	}
	
	public List<HashMap<String, Object>> getMemoExchReadyAllBySeq(HashMap<String, Object> param) {
		return sqlSession.selectList("getMemoExchReadyAllBySeq", param);
	}
	
	public int getMemoExchReadyCntByLv(int lv) {
		return sqlSession.selectOne("getMemoExchReadyCntByLv", lv);
	}
	
	public List<HashMap<String, Object>> getMemoExchReadyAllByLv(HashMap<String, Object> param) {
		return sqlSession.selectList("getMemoExchReadyAllByLv", param);
	}
	
	public List<HashMap<String, Object>> getAlpayAdmLogBySeq(HashMap<String, Object> param) {
		return sqlSession.selectList("getAlpayAdmLogBySeq", param);
	}
	
	public List<HashMap<String, Object>> getAlpayAdmLogAll(HashMap<String, Object> param) {
		return sqlSession.selectList("getAlpayAdmLogAll", param);
	}
}
