package com.altong.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.altong.web.service.LogEstimateService;
import com.altong.web.service.answer.AnswerService;
import com.altong.web.service.common.CommonService;
import com.altong.web.service.common.V2RankService;
import com.altong.web.service.config.ConfigService;
import com.altong.web.service.file.FileService;
import com.altong.web.service.member.MemberService;
import com.altong.web.service.question.QuestionService;
import com.altong.web.service.reply.ReplyService;
import com.altong.web.service.report.ReportService;
import com.altong.web.service.rest.RestService;

@CrossOrigin
@Controller
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rest")
public class RestController {
	@Autowired
	MemberService memberService;

	@Autowired
	CommonService commonService;

	@Autowired
	V2RankService v2rankService;

	@Autowired
	ReplyService replyService;

	@Autowired
	AnswerService answerService;

	@Autowired
	ConfigService configService;

	@Autowired
	FileService fileService;

	@Autowired
	QuestionService questionService;

	@Autowired
	ReportService reportService;

	@Autowired
	LogEstimateService logEstimateService;

	@Autowired
	RestService restService;

	// SSR 첫 화면 렌더시 qeustion + answer 리스트
	@RequestMapping(value="/questions/{questions}", method =  {RequestMethod.GET, RequestMethod.POST})
	public List<Map<String, Object>> restQeustionsAnswerList( @PathVariable("questions") String questions, HttpServletRequest request )
	{	
		return restService.restQeustionsAnswerList(questions, request);
	}

	// 해당 질문의 좋아요 싫어요 투표수 가져오기
	@RequestMapping(value="/questions/{question}/vote", method = RequestMethod.GET)
	public Map<String, Object> restQuestionVote(@PathVariable("question") String question)
	{
		return restService.restQuestionVote(question);
	}
	
	// 해당 답변의 좋아요 싫어요 투표수 가져오기
	@RequestMapping(value="/answers/{answer}/vote", method = RequestMethod.GET)
	public Map<String, Object> restAnswerVote(@PathVariable("answer") String answer)
	{
		return restService.restAnswerVote(answer);
	}
	
	@RequestMapping(value="/questions/{question}/almoney", method = RequestMethod.GET)
	public Map<String, Object> restQuestionAlmoney(@PathVariable("question") String question)
	{
		return restService.getRestQuestionExtraAlmoney(Integer.parseInt(question));		// 질문 알머니 수 보내기
	}

	@RequestMapping(value="/answers/{answer}/almoney", method = RequestMethod.GET)
	public Map<String, Object> restAnswerAlmoney(@PathVariable("answer") String answer)
	{
		return restService.getRestAnswerExtraAlmoney(Integer.parseInt(answer));			// 답변 알머니 수 보내기
	}
	
	// POST question 유저 찜 (작업 완료)
	@RequestMapping(value="/questions/{question}/Zzim", method = RequestMethod.POST)
	public Map<String, Object> restZzimAdd(@PathVariable("question") String question, HttpServletRequest request, HttpServletResponse response )
	{
		return restService.restZzimAdd(question, request, response);
	}
	
	// POST 질문글 훈훈알 현재 사용가능 알수 (작업 완료)
	@RequestMapping(value="/questions/{question}/almoney/extra", method = RequestMethod.POST)
	public Map<String, Object> restQeustionExtraAlmoney(@PathVariable("question") String question, HttpServletRequest request, HttpServletResponse response )
	{
		return restService.restQeustionExtraAlmoney(question, request, response);
	}
	
	// POST 답변글 훈훈알 현재 사용가능 알수 (작업 완료)
	@RequestMapping(value="/answers/{answer}/almoney/extra", method = RequestMethod.POST)
	public Map<String, Object> restAnswerExtraAlmoney(@PathVariable("answer") String answer, HttpServletRequest request, HttpServletResponse response )
	{
		return restService.restAnswerExtraAlmoney(answer, request, response);
	}
	
	// POST 질문 훈훈알 주기 (작업 완료)
	@RequestMapping(value="/questions/{question}/almoney", method = RequestMethod.PUT)
	public Map<String, Object> restQuestionPutAlmoney(@RequestBody Map<String, Object> param, 
			@PathVariable("question") String question, HttpServletRequest request, HttpServletResponse response)
	{
		return restService.restQuestionPutAlmoney( (int) param.get("extraAlmoney"), question, request, response);
	}
	
	// POST 답변 훈훈알 주기 (작업 완료)
	@RequestMapping(value="/answers/{answer}/almoney", method = RequestMethod.PUT)
	public Map<String, Object> restAnswerPutAlmoney(@RequestBody Map<String, Object> param,
			@PathVariable("answer") String answer, HttpServletRequest request, HttpServletResponse response)
	{
		return restService.restAnswerPutAlmoney( (int) param.get("extraAlmoney"), answer, request, response);
	}

	// POST 꼭대기
	public Map<String, Object> restMoveQuestion(@PathVariable("question") String question, HttpServletRequest request, HttpServletResponse response )
	{
		try {
			
			return null;
		
		}catch (Exception e) {
			final Map<String, Object> map = new HashMap<String, Object>() {
				private static final long serialVersionUID = -8431224123124L;
				{
					put("message",e);
				}
			};
			return map;
		}
	}
	
	// 질문 댓글 작성(작업 완료)
	@RequestMapping(value="/questions/{question}/reply", method = RequestMethod.PUT)
	public Map<String, Object> restQuestionReplyPut(@PathVariable("question") String question, 
			HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> param )
	{
		return restService.restQuestionReplyPut(question, request, response, (String) param.get("text"));
	}
	
	// 답변 댓글 작성(작업 테스트 이전)
	@RequestMapping(value="/answers/{answer}/reply", method = RequestMethod.PUT)
	public Map<String, Object> restAnswerReplyPut(@PathVariable("answer") String answer, 
			HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> param )
	{
		return restService.restAnswerReplyPut(answer, request, response, (String) param.get("text"));
	}
	
	// 질문 댓글 가져오기 GET
	@RequestMapping(value="/questions/{question}/reply", method = RequestMethod.GET)
	public List<Map<String, Object>> restGetQuestionReplys( @PathVariable("question") String question )
	{
		return restService.restGetQuestionReplys(question);
	}
	
	// 답변 댓글 가져오기 GET
	@RequestMapping(value="/answers/{answer}/reply", method = RequestMethod.GET)
	public List<Map<String, Object>> restGetAnswerReplys( @PathVariable("answer") String answer )
	{
		return restService.restGetAnswerReplys(answer);
	}
	
	// 계정정보 가져오기
	@RequestMapping(value="/user", method = RequestMethod.GET)
	public Map<String, Object> restGetUser(HttpServletRequest request, HttpServletResponse response )
	{
		return restService.restGetUser(request, response);
	}
	
	// GET 답변 평가 가져오기(분리)
	@RequestMapping(value="/answers/{answer}/estimate", method = RequestMethod.GET)
	public Map<String, Object> restGetAnswerEstimate(@PathVariable("answer") String answer)
	{
		return restService.restGetAnswerEstimate(answer);
	}
	
	// PUT 답변 평가 클릭(작업 완료)
	@RequestMapping(value="/answers/{answer}/estimate", method = RequestMethod.PUT)
	public Map<String, Object> restPutAnswerEstimate(@PathVariable("answer") String answer, 
			HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> param )
	{
		return restService.restPutAnswerEstimate(answer, request, response, (int)param.get("esti"));
	}
	
	// PUT 질문글 좋아요 싫어요 클릭 (테스트 이전)
	@RequestMapping(value="/questions/{question}/vote", method = RequestMethod.PUT)
	public Map<String, Object> restPutQuestionVote(@PathVariable("question") String question, 
			HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> param )
	{
		return restService.restPutQuestionVote(question, request, response, (String) param.get("estiSeq"));
	}
	
	// PUT 답변글 좋아요 싫어요 클릭
	@RequestMapping(value="/answers/{answer}/vote", method = RequestMethod.PUT)
	public Map<String, Object> restPutAnswerVote(@PathVariable("answer") String answer, 
			HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> param )
	{
		return restService.restPutAnswerVote(answer, request, response, (String) param.get("estiSeq"));
	}
	
}

