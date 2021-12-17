package com.altong.web.service.translate;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TranslateService {
	String transProcess(HttpServletRequest request, HttpServletResponse response, Locale locale);
	String getQuestionOrgTitle(HttpServletRequest request, HttpServletResponse response);
	String getAnsOrgTitle(HttpServletRequest request, HttpServletResponse response);
	String getReplyOrgTitle(HttpServletRequest request, HttpServletResponse response);
	String getNoticeOrgTitle(HttpServletRequest request, HttpServletResponse response);
}
