package com.altong.web.rest.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altong.web.rest.service.RestQuestionService;

@RestController
@RequestMapping("/api/questions")
public class RestQuestionController{
	
	@Autowired
	RestQuestionService service;
	
	// SSR 첫 화면 렌더시 qeustion + answer 리스트
	@GetMapping("/{question}")
	public List<Map<String, Object>> restGetQeustionsAnswerList( @PathVariable("question") String questions, HttpServletRequest request )
	{	
		return service.restQeustionsAnswerList(questions, request);
	}
	
	// 해당 질문의 좋아요 싫어요 투표수 가져오기
	@GetMapping("/{question}/vote")
	public Map<String, Object> restGetQuestionVote(@PathVariable("question") String question)
	{
		return service.restQuestionVote(question);
	}
	
	@GetMapping("/{question}/almoney")
	public Map<String, Object> restGetQuestionAlmoney(@PathVariable("question") String question)
	{
		return service.getRestQuestionExtraAlmoney(Integer.parseInt(question));		// 질문 알머니 수 보내기
	}
	
	// PUT question 유저 찜 (작업 완료)
	@PutMapping("/{question}/zzim")
	public Map<String, Object> restPutZzimAdd(@PathVariable("question") String question, HttpServletRequest request, HttpServletResponse response )
	{
		return service.restZzimAdd(question, request, response);
	}
	
	// POST 질문 훈훈알 주기 (작업 완료)
	@PostMapping("/{question}/almoney")
	public Map<String, Object> restPostQuestionAlmoney(@RequestBody Map<String, Object> param, 
			@PathVariable("question") String question, HttpServletRequest request, HttpServletResponse response)
	{
		return service.restQuestionPutAlmoney( (int) param.get("extraAlmoney"), question, request, response);
	}
	
	// Post 질문 댓글 작성(작업 완료)
	@PostMapping("/{question}/reply")
	public Map<String, Object> restPostQuestionReply(@PathVariable("question") String question, 
			HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> param )
	{
		return service.restQuestionReplyPut(question, request, response, (String) param.get("text"));
	}
	
	// 질문 댓글 가져오기 GET
	@GetMapping("/{question}/reply")
	public List<Map<String, Object>> restGetQuestionReplys( @PathVariable("question") String question )
	{
		return service.restGetQuestionReplys(question);
	}
	
	// Patch 질문글 좋아요 싫어요 클릭 (테스트 이전)
	@PatchMapping("/{question}/vote")
	public Map<String, Object> restPatchQuestionVote(@PathVariable("question") String question, 
			HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> param )
	{
		return service.restPutQuestionVote(question, request, response, (String) param.get("estiSeq"));
	}
	
	// Patch 질문 꼭대기 (작업 완료)
	@PatchMapping("/{question}/top")
	public Map<String, Object> restPatchQuestionMoveTop(@PathVariable("question") Integer question, HttpServletRequest request, HttpServletResponse response)
	{
		return service.restQuestionPutMoveTop(question, request, response);
	}
	
	// GET 받은 훈훈알 클릭시, 증정회원 표시(작업 완료) contentsType=Q(질문)/A(답변), contentsSeq=질문 또는 답변의 Seq
	@GetMapping("/{question}/{contentsType}/extra-lists")
	public Map<String, Object> restGetAnswerExtraUsers(@PathVariable("question") Integer question, @PathVariable("contentsType") String contentsType,
			HttpServletRequest request, HttpServletResponse response)
	{
		return service.restQuestionGetExtraUsers(question, contentsType, request, response);
	}
	
	// GET 질문 AI 버튼 클릭시 받아야 할 값 가져오기(작업 완료), param = question(질문 seq)
	@GetMapping("/{question}/transfer")
	public Map<String, Object> restGetQuestionTransfer(@PathVariable("question") Integer question,
			HttpServletRequest request, HttpServletResponse response, Locale locale)
	{
		return service.restQuestionGetTransfer(question, request, response, locale);
	}
	
	// GET 질문의 댓글 AI 버튼 클릭시(작업 완료), param = rseq(질문 댓글 seq)
	@GetMapping("/replys/{reply}/transfer")
	public Map<String, Object> restGetQuestionTransferReply(@PathVariable("reply") Integer reply,
			HttpServletRequest request, HttpServletResponse response, Locale locale)
	{
		return service.restQuestionGetTransferReply(reply, request, response, locale);
	}
}
