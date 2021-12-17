package com.altong.web.service.report;

import java.util.HashMap;

public interface ReportService {
	int getQuestionReportCnt(HashMap<String, Object> param);
	int getReportActionCount(HashMap<String, Object> param);
}
