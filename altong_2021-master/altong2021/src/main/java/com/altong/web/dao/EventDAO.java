package com.altong.web.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.altong.web.logic.event.EventVO;

@Repository
public class EventDAO {
	@Autowired
	public SqlSession sqlSession;

	public List<EventVO> getEventForHeader() {
		return sqlSession.selectList("getEventForHeader");
	}

	public HashMap<String, Object> getTicketConfig() {
		return sqlSession.selectOne("getTicketConfig");
	}

	public int getTicket(HashMap<String, Object> param) {
		return sqlSession.selectOne("getTicket", param);
	}

	public int getTicketCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getTicketCount", param);
	}

	public int getTicketStackCnt(int userSeq) {
		return sqlSession.selectOne("getTicketStackCnt", userSeq);
	}
	public HashMap<String, Object> getTicketStack(int userSeq) {
		return sqlSession.selectOne("getTicketStack", userSeq);
	}

	public void setTickUse(HashMap<String, Object> param) {
		sqlSession.update("setTickUse", param);
	}

	public void addTicket(HashMap<String, Object> param) {
		sqlSession.insert("addTicket", param);
	}

	public void addTicketStack(HashMap<String, Object> param) {
		sqlSession.insert("addTicketStack", param);
	}

	public void setAddTickStackUse(HashMap<String, Object> param) {
		sqlSession.update("setAddTickStackUse", param);
	}

	public void setSubTickStackUse(HashMap<String, Object> param) {
		sqlSession.update("setSubTickStackUse", param);
	}
	
	// 통계 적용 부분
	public int getRulTicketTotalCount() {
		return sqlSession.selectOne("getRulTicketTotalCount");
	}
	
	public int getRulAlmoneyTotalSum() {
		return sqlSession.selectOne("getRulAlmoneyTotalSum");
	}

	public int getRulUserTotalCount() {
		return sqlSession.selectOne("getRulUserTotalCount");
	}

	public int getRulDisUseTotalCount() {
		return sqlSession.selectOne("getRulDisUseTotalCount");
	}

	public int getRulTicketUseTotalCount() {
		return sqlSession.selectOne("getRulTicketUseTotalCount");
	}
	
	public HashMap<String, Object> getRulTicketLVCountS() {
		return sqlSession.selectOne("getRulTicketLVCountS");
	}
	
	public HashMap<String, Object> getRulTicketALCountS() {
		return sqlSession.selectOne("getRulTicketALCountS");
	}
	
	public HashMap<String, Object> getRulUseUserCountS() {
		return sqlSession.selectOne("getRulUseUserCountS");
	}
	
	public HashMap<String, Object> getRulPartCountS() {
		return sqlSession.selectOne("getRulPartCountS");
	}
	
	public HashMap<String, Object> getRulPartMoneyCountS() {
		return sqlSession.selectOne("getRulPartMoneyCountS");
	}
	
	public List<HashMap<String, Object>> getTopStatistics(HashMap<String, Object> param) {
		return sqlSession.selectList("getTopStatistics", param);
	}
	
	public List<HashMap<String, Object>> getDateStatistics(HashMap<String, Object> param) {
		return sqlSession.selectList("getDateStatistics", param);
	}

	public HashMap<String, Object> getRulDatePartCountS(HashMap<String, Object> param) {
		return sqlSession.selectOne("getRulDatePartCountS",param);
	}
	
	public HashMap<String, Object> getRulPartDateMoneyCountS(HashMap<String, Object> param) {
		return sqlSession.selectOne("getRulPartDateMoneyCountS",param);
	}
	
	public int getRulDateTicketTotalCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getRulDateTicketTotalCount",param);
	}
	
	public int getRulDateAlmoneyTotalSum(HashMap<String, Object> param) {
		return sqlSession.selectOne("getRulDateAlmoneyTotalSum",param);
	}

	public int getRulDateUserTotalCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getRulDateUserTotalCount",param);
	}

	public int getRulDateDisUseTotalCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getRulDateDisUseTotalCount",param);
	}

	public List<HashMap<String, Object>> getDateTopStatistics(HashMap<String, Object> param) {
		return sqlSession.selectList("getDateTopStatistics", param);
	}

	public List<HashMap<String, Object>> getChartStatistics() {
		return sqlSession.selectList("getChartStatistics");
	}
	
	public int getSaveEventSubCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getSaveEventSubCount", param);
	}
}
