package com.altong.web.service.rest;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

public interface RestService {

	List<Map<String, Object>> getRestAnswerList(Map<String, Object> param);
	List<Map<String, Object>> getRestQuestionReplys(Map<String, Object> param);
	Map<String, Object> getOneRestQuestion(Map<String, Object> param);

	// 디비에서 가져온 값을 json 형태로 바꾸어줍니다.
	Map<String, Object> questionToJson(Map<String, Object> param);
	Map<String, Object> AnswerToJson(Map<String, Object> param);

	// 좋아요, 싫어요 - 투표 수
	Map<String, Object> getRestVoteSum(Map<String, Object> param);
	
	// 훈훈알
	Map<String, Object> getRestAnswerExtraAlmoney(int seq);
	Map<String, Object> getRestQuestionExtraAlmoney(int seq);

	int getRestCookieUser(HttpServletRequest request, HttpServletResponse response) throws Exception;
	String getRestCookieUserNick(HttpServletRequest request, HttpServletResponse response) throws Exception;
	int getRestCookieUserLv(HttpServletRequest request, HttpServletResponse response) throws Exception;

	int getRestZzimCountUp(HashMap<String, Object> param);
	String restGetLevelName(int userLv);
	int getRestTodayUseAl(int userSeq);
	
	// 댓글 작성하기 (기존과 동일)
	Map<String, Object> putRestReplyQA(Map<String, Object> param);
	
	// 평가하기
	Map<String, Object> putAnswerEstimate(Map<String, Object> param);
	// 좋아요 싫어요 투표하기
	Map<String, Object> putAnswerQuestionVote(Map<String, Object> param);
	
	// 공통 컨트롤러

	List<Map<String, Object>> restQeustionsAnswerList(String questions, HttpServletRequest request);	// Controller GET, POST /questions/{questions}
	Map<String, Object> restQeustionExtraAlmoney(String question, HttpServletRequest request, HttpServletResponse response);	// Controller POST 질문글 훈훈알 현재 사용가능 알수
	Map<String, Object> restAnswerExtraAlmoney(String answer, HttpServletRequest request, HttpServletResponse response);	// Controller POST 답변글 훈훈알 현재 사용가능 알수 
	Map<String, Object> restQuestionPutAlmoney(int extraAlmoney, String question, HttpServletRequest request, HttpServletResponse response);	// Controller PUT 답변글 훈훈알 현재 사용가능 알수  	/questions/{question}/almoney
	Map<String, Object> restAnswerPutAlmoney( int extraAlmoney, String answer, HttpServletRequest request, HttpServletResponse response);	// Controller PUT 답변글 훈훈알 현재 사용가능 알수  		/answers/{answer}/almoney
	Map<String, Object> restQuestionReplyPut(String question, HttpServletRequest request, HttpServletResponse response, String text);	// Controller PUT 질문 댓글 작성 					/questions/{question}/reply
	Map<String, Object> restAnswerReplyPut(String answer, HttpServletRequest request, HttpServletResponse response, String text);	// Controller PUT 답변글 댓글 작성	 					/answers/{answer}/reply
	Map<String, Object> restPutAnswerEstimate(String answer, HttpServletRequest request, HttpServletResponse response, int estimateSt);	// Controller PUT 답변 평가 클릭	 					/answers/{answer}/estimate
	Map<String, Object> restGetAnswerEstimate(String answer);	// Controller PUT 답변 평가 가져오기 					/answers/{answer}/estimate
	
	Map<String, Object> restQuestionVote(String question);	// Controller GET 질문글 좋아요 싫어요 	 				/questions/{question}/vote
	Map<String, Object> restAnswerVote(String answer);		// Controller GET 질문글 좋아요 싫어요 	 				/questions/{question}/vote
	Map<String, Object> restPutQuestionVote(String question, HttpServletRequest request, HttpServletResponse response, String estiSeq);	// Controller PUT 질문글 좋아요 싫어요 클릭	 			/questions/{question}/vote
	Map<String, Object> restPutAnswerVote(String answer, HttpServletRequest request, HttpServletResponse response, String estiSeq);	// Controller PUT 답변글 좋아요 싫어요 클릭	 				/answers/{answer}/vote
	
	Map<String, Object> restZzimAdd(String question, HttpServletRequest request, HttpServletResponse response);									// Controller POST question 유저 찜 			 		/questions/{question}/Zzim
	Map<String, Object> restGetUser(HttpServletRequest request, HttpServletResponse response);													// Controller GET 계정정보 가져오기	 					/user
	List<Map<String, Object>> restGetQuestionReplys(String question);			// Contoller GET 질문 댓글 가져오기			/questions/{question}/reply
	List<Map<String, Object>> restGetAnswerReplys(String answer);				// Contoller GET 답변 댓글 가져오기			/answers/{answer}/reply
	
	
	// 그대로 가져와서 재작성한 서비스
	// CommonService - getSumExtraAlmoneyInfo
	Map<String,Object> getRestConvertSumExtraAlmoneyInfo(Map<String, Object> param);
	Map<String,Object> getRestAnswerConvertSumExtraAlmoneyInfo(Map<String, Object> param);
	
	// 룰렛 게임 티켓을 위한 give count 서비스
	Map<String,Object> getRestGameRouletteGiveTicketCountUpService(Map<String, Object> param);
	
	Map<String,Object> getRestQuestionRunExcept(Map<String, Object> param, Calendar cal);
	Map<String,Object> getRestAnswerRunExcept(Map<String, Object> param, Calendar cal);
	int getRestCookieUserInfo(HttpServletRequest request, HttpServletResponse response, String target) throws Exception;
	
}
