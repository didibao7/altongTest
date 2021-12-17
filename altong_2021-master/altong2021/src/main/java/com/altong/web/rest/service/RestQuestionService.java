package com.altong.web.rest.service;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RestQuestionService {
	List<Map<String, Object>> restQeustionsAnswerList(String questions, HttpServletRequest request);	// Controller GET, POST /questions/{questions}
	Map<String, Object> restQuestionVote(String question);	// Controller GET 질문글 좋아요 싫어요 	 				/questions/{question}/vote
	Map<String, Object> getRestQuestionExtraAlmoney(int seq);
	Map<String, Object> restZzimAdd(String question, HttpServletRequest request, HttpServletResponse response);									// Controller POST question 유저 찜 			 		/questions/{question}/Zzim
	Map<String, Object> restQuestionPutAlmoney(int extraAlmoney, String question, HttpServletRequest request, HttpServletResponse response);	// Controller PUT 답변글 훈훈알 현재 사용가능 알수  	/questions/{question}/almoney
	Map<String, Object> restQuestionReplyPut(String question, HttpServletRequest request, HttpServletResponse response, String text);	// Controller PUT 질문 댓글 작성 					/questions/{question}/reply
	List<Map<String, Object>> restGetQuestionReplys(String question);			// Contoller GET 질문 댓글 가져오기			/questions/{question}/reply
	Map<String, Object> restPutQuestionVote(String question, HttpServletRequest request, HttpServletResponse response, String estiSeq);	// Controller PUT 질문글 좋아요 싫어요 클릭	 			/questions/{question}/vote
	Map<String, Object> restQuestionPutMoveTop(Integer question, HttpServletRequest request, HttpServletResponse response);
	Map<String,Object> getRestQuestionRunExcept(Map<String, Object> param, Calendar cal);
	Map<String,Object> getRestConvertSumExtraAlmoneyInfo(Map<String, Object> param);
	Map<String, Object> getOneRestQuestion(Map<String, Object> param);
	List<Map<String, Object>> getRestQuestionReplys(Map<String, Object> param);
	Map<String, Object> questionVote(String act, int contentSeq, String contentType, HttpServletRequest request,
			HttpServletResponse response);
	Map<String, Object> restQuestionGetExtraUsers(Integer contentsSeq, String contentsType, HttpServletRequest request, HttpServletResponse response);
	Map<String, Object> restQuestionGetTransfer(Integer question, HttpServletRequest request, HttpServletResponse response, Locale locale);
	Map<String, Object> restQuestionGetTransferReply(Integer rseq, HttpServletRequest request, HttpServletResponse response, Locale locale);
}
