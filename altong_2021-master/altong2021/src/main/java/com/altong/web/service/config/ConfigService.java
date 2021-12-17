package com.altong.web.service.config;

import java.util.HashMap;
import java.util.List;

import com.altong.web.logic.config.ConfigVO;

public interface ConfigService {
	public String getNickNameCheck();
	int getAlmoneyMoveQuestion();
	ConfigVO getConfigList();
	ConfigVO getBoardConfig();
	void configOriginalUpdate(HashMap<String, Object> param);
	List<HashMap<String, Object>> getConfigForLv();
	void configNewUpdate(HashMap<String, Object> param);
	void configExchangeUpdate(HashMap<String, Object> param);
	List<HashMap<String, Object>> getConfigLv();
	List<HashMap<String, Object>> getConfigLv2();
	int getConfigLvCnt();
}
