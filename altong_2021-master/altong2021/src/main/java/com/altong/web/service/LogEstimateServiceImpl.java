package com.altong.web.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altong.web.dao.LogEstimateDAO;
import com.altong.web.logic.LogEstimateVO;

@Service
public class LogEstimateServiceImpl implements LogEstimateService {
	@Autowired
	LogEstimateDAO logExtimateDAO;
	
	public LogEstimateVO getLogEstimateBySeq(HashMap<String, Object> param) {
		return logExtimateDAO.getLogEstimateBySeq(param);
	}
}
