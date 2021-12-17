package com.altong.web.dao.common;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.altong.web.logic.ad.AdVO;

@Repository
public class AdDAO {
	@Autowired
	public SqlSession sqlSession;
	
	public List<AdVO> getAnswerAd(HashMap<String, Object> param) {
		return sqlSession.selectList("getAnswerAd", param);
	}
	
	public int getAdLimiCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getAdLimiCount", param);
	}
	
	public List<AdVO> getAdLimitList(HashMap<String, Object> param) {
		return sqlSession.selectList("getAdLimitList", param);
	}
	
	public int getAdMaxCode() {
		return sqlSession.selectOne("getAdMaxCode");
	}
	
	public void setAd(HashMap<String, Object> param) {
		sqlSession.insert("setAd", param);
	}
	
	public AdVO getAdView(int seq) {
		return sqlSession.selectOne("getAdView", seq);
	}
	
	public void updateAd(HashMap<String, Object> param) {
		sqlSession.update("updateAd", param);
	}
	
	public void deleteAd(int seq) {
		sqlSession.delete("deleteAd", seq);
	}
}
