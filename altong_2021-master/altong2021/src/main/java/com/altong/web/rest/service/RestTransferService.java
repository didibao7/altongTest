package com.altong.web.rest.service;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RestTransferService {

	Map<String, Object> restTransferGetTransferVote(Integer transfer, String contentType, HttpServletRequest request, HttpServletResponse response, Locale locale);
	Map<String, Object> restTransferPutTransferVote(Integer transfer, String act, String contentType, HttpServletRequest request, HttpServletResponse response, Locale locale);

	Map<String, Object> transProcess(int contentSeq, String contentType, HttpServletRequest request,
			HttpServletResponse response, Locale locale);

	String subTranslate(int contentSeq, String contentType, String targetLang, String bHour, int ai_seq)
			throws Exception;
}
