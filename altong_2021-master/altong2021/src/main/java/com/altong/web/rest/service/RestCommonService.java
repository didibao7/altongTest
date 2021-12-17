package com.altong.web.rest.service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RestCommonService {
	int getRestCookieUser(HttpServletRequest request, HttpServletResponse response) throws Exception;
	int getRestCookieUserLv(HttpServletRequest request, HttpServletResponse response) throws Exception;
	int getRestCookieUserInfo(HttpServletRequest request, HttpServletResponse response, String target) throws Exception;
	Map<String,Object> getRestGameRouletteGiveTicketCountUpService(Map<String, Object> param);
	String restGetLevelName(int userLv);
	// 좋아요 싫어요 투표하기
	Map<String, Object> putAnswerQuestionVote(Map<String, Object> param);
	Map<String, Object> putRestReplyQA(Map<String, Object> param);
	
	Map<String, Object> questionToJson(Map<String, Object> param);
	Map<String, Object> AnswerToJson(Map<String, Object> param);
	
	// 좋아요, 싫어요 - 투표 수
	Map<String, Object> getRestVoteSum(Map<String, Object> param);
	
	int getRestZzimCountUp(HashMap<String, Object> param);
	int getRestTodayUseAl(int userSeq);
	String getRestCookieUserNick(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	Map<String, Object> restExtraAlmoney(HttpServletRequest request, HttpServletResponse response);	// Controller POST 질문글 훈훈알 현재 사용가능 알수
}
