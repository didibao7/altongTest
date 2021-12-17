package com.altong.web.service;

import java.util.HashMap;

import com.altong.web.logic.LogEstimateVO;

public interface LogEstimateService {
	LogEstimateVO getLogEstimateBySeq(HashMap<String, Object> param);
}
