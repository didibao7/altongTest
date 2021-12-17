package com.altong.web.rest.service;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RestAnswerService {
	
	Map<String, Object> restAnswerVote(String answer);		// Controller GET 질문글 좋아요 싫어요 	 				/questions/{question}/vote
	Map<String, Object> getRestAnswerExtraAlmoney(int seq);
	Map<String, Object> restAnswerPutAlmoney( int extraAlmoney, String answer, HttpServletRequest request, HttpServletResponse response);	// Controller PUT 답변글 훈훈알 현재 사용가능 알수  		/answers/{answer}/almoney
	Map<String, Object> restAnswerReplyPut(String answer, HttpServletRequest request, HttpServletResponse response, String text);	// Controller PUT 답변글 댓글 작성	 					/answers/{answer}/reply
	List<Map<String, Object>> restGetAnswerReplys(String answer);				// Contoller GET 답변 댓글 가져오기			/answers/{answer}/reply
	Map<String, Object> restGetAnswerEstimate(String answer);	// Controller PUT 답변 평가 가져오기 					/answers/{answer}/estimate
	Map<String, Object> restPutAnswerEstimate(String answer, HttpServletRequest request, HttpServletResponse response, int estimateSt);	// Controller PUT 답변 평가 클릭	 					/answers/{answer}/estimate
	Map<String, Object> restPutAnswerVote(String answer, HttpServletRequest request, HttpServletResponse response, String estiSeq);	// Controller PUT 답변글 좋아요 싫어요 클릭	 				/answers/{answer}/vote
	Map<String, Object> restAnswerPutChoice(Integer answer, Integer memberSeq, HttpServletRequest request, HttpServletResponse response);
	Map<String, Object> restAnswerPutReplyDel(Integer reply, String flag, HttpServletRequest request, HttpServletResponse response);
	Map<String, Object> restAnswerGetEstimate(Integer seq, HttpServletRequest request, HttpServletResponse response);
	Map<String, Object> restAnswerGetExtraUsers(Integer contentsSeq, String contentsType, HttpServletRequest request, HttpServletResponse response);
	Map<String, Object> restAnswerPutBestSet(Integer question, String bestRank, HttpServletRequest request, HttpServletResponse response);
	Map<String, Object> restAnswerPutBestCancel(Integer question, HttpServletRequest request, HttpServletResponse response);
	Map<String, Object> restAnswerGetAnsweredCheck(Integer question, HttpServletRequest request, HttpServletResponse response);
	Map<String, Object> restAnswerGetChoicedCheck(Integer answer, HttpServletRequest request, HttpServletResponse response);
	Map<String,Object> getRestAnswerRunExcept(Map<String, Object> param, Calendar cal);
	Map<String,Object> getRestAnswerConvertSumExtraAlmoneyInfo(Map<String, Object> param);
	// 댓글 작성하기 (기존과 동일)
	Map<String, Object> putAnswerEstimate(Map<String, Object> param);
	List<Map<String, Object>> getRestAnswerList(Map<String, Object> param);
	Map<String, Object> restAdsGet();
	Map<String, Object> restAnswerPutAdsView(Integer answer, Integer ads, HttpServletRequest request, HttpServletResponse response);
	Map<String, Object> restAnswerGetTransfer(Integer answer, HttpServletRequest request, HttpServletResponse response, Locale locale);
	Map<String, Object> restAnswerGetTransferReply(Integer rseq, HttpServletRequest request, HttpServletResponse response, Locale locale);
}
