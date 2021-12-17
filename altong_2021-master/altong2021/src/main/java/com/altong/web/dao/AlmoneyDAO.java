package com.altong.web.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.altong.web.logic.LogAlmoneyVO;
import com.altong.web.logic.almoney.AlmoneyVO;

@Repository
public class AlmoneyDAO {
	@Autowired
	public SqlSession sqlSession;
	
	public AlmoneyVO getMemberLogAlmoneyTotal(int userSeq) {
		return sqlSession.selectOne("getMemberLogAlmoneyTotal", userSeq);
	}
	
	public AlmoneyVO getMemberLogAlmoney(int userSeq) {
		return sqlSession.selectOne("getMemberLogAlmoney", userSeq);
	}
	
	public BigDecimal getEarnAnswerer(int userSeq) {
		return sqlSession.selectOne("getEarnAnswerer", userSeq);
	}
	
	public HashMap<String, Object> setExchange(HashMap<String, Object> param) {
		return sqlSession.selectOne("setExchange", param);
	}
	
	public int setUpdateAlmoney(HashMap<String, Object> param) {
		return sqlSession.selectOne("setUpdateAlmoney", param);
	}
}
