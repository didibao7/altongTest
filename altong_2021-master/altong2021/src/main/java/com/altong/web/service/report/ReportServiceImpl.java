package com.altong.web.service.report;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altong.web.dao.report.ReportDAO;

@Service
public class ReportServiceImpl implements ReportService {
	@Autowired
	ReportDAO reportDAO;
	
	public int getQuestionReportCnt(HashMap<String, Object> param) {
		return reportDAO.getQuestionReportCnt(param);
	}
	
	public int getReportActionCount(HashMap<String, Object> param) {
		return reportDAO.getReportActionCount(param);
	}
}
