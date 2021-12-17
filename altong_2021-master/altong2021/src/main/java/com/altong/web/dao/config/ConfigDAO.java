package com.altong.web.dao.config;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.altong.web.logic.config.ConfigVO;

@Repository
public class ConfigDAO {
	@Autowired
	public SqlSession sqlSession;
	
	public String getNickNameCheck() {
		return sqlSession.selectOne("getNickNameCheck");
	}
	
	public int getAlmoneyMoveQuestion() {
		return sqlSession.selectOne("getAlmoneyMoveQuestion");
	}
	
	public ConfigVO getConfigList() {
		return sqlSession.selectOne("getConfigList");
	}
	
	public ConfigVO getBoardConfig() {
		return sqlSession.selectOne("getBoardConfig");
	}
	
	public void configOriginalUpdate(HashMap<String, Object> param) {
		sqlSession.update("configOriginalUpdate", param);
	}
	
	public List<HashMap<String, Object>> getConfigForLv() {
		return sqlSession.selectList("getConfigForLv");
	}
	
	public void configNewUpdate(HashMap<String, Object> param) {
		sqlSession.update("configNewUpdate", param);
	}
	
	public void configExchangeUpdate(HashMap<String, Object> param) {
		sqlSession.update("configExchangeUpdate", param);
	}
	
	public List<HashMap<String, Object>> getConfigLv() {
		return sqlSession.selectList("getConfigLv");
	}
	
	public List<HashMap<String, Object>> getConfigLv2() {
		return sqlSession.selectList("getConfigLv2");
	}
	
	public int getConfigLvCnt() {
		return sqlSession.selectOne("getConfigLvCnt");
	}
}
