package com.altong.web.service.config;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altong.web.dao.config.ConfigDAO;
import com.altong.web.logic.config.ConfigVO;
import com.altong.web.service.config.ConfigService;

@Service
public class ConfigServiceImpl implements ConfigService {
	@Autowired
	ConfigDAO configDAO;
	
	public String getNickNameCheck() {
		return configDAO.getNickNameCheck();
	}
	
	public int getAlmoneyMoveQuestion() {
		return configDAO.getAlmoneyMoveQuestion();
	}
	
	public ConfigVO getConfigList() {
		return configDAO.getConfigList();
	}
	
	public ConfigVO getBoardConfig() {
		return configDAO.getBoardConfig();
	}
	
	public void configOriginalUpdate(HashMap<String, Object> param) {
		configDAO.configOriginalUpdate(param);
	}
	
	public List<HashMap<String, Object>> getConfigForLv() {
		return configDAO.getConfigForLv();
	}
	
	public void configNewUpdate(HashMap<String, Object> param) {
		configDAO.configNewUpdate(param);
	}
	
	public void configExchangeUpdate(HashMap<String, Object> param) {
		configDAO.configExchangeUpdate(param);
	}
	
	public List<HashMap<String, Object>> getConfigLv() {
		return configDAO.getConfigLv();
	}
	
	public List<HashMap<String, Object>> getConfigLv2() {
		return configDAO.getConfigLv2();
	}
	
	public int getConfigLvCnt() {
		return configDAO.getConfigLvCnt();
	}
}
