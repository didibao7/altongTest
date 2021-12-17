package com.altong.web.rest.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altong.web.rest.service.RestAnswerService;
import com.altong.web.rest.service.RestCommonService;

@RestController
@RequestMapping("/api/answers")
public class RestAnswerController{

	@Autowired
	RestAnswerService service;
	
	@Autowired
	RestCommonService restCommonService;
	
	
	// 해당 답변의 좋아요 싫어요 투표수 가져오기
	@GetMapping("/{answer}/vote")
	public Map<String, Object> restGetAnswerVote(@PathVariable("answer") String answer)
	{
		return service.restAnswerVote(answer);
	}
	
	@GetMapping("/{answer}/almoney")
	public Map<String, Object> restGetAnswerAlmoney(@PathVariable("answer") String answer)
	{
		return service.getRestAnswerExtraAlmoney(Integer.parseInt(answer));			// 답변 알머니 수 보내기
	}
		
	// POST 답변 훈훈알 주기 (작업 완료)
	@PostMapping("/{answer}/almoney")
	public Map<String, Object> restPostAnswerAlmoney(@RequestBody Map<String, Object> param,
			@PathVariable("answer") String answer, HttpServletRequest request, HttpServletResponse response)
	{
		return service.restAnswerPutAlmoney( (int) param.get("extraAlmoney"), answer, request, response);
	}
	
	// Post 답변 댓글 작성(작업 테스트 이전)
	@PostMapping("/{answer}/reply")
	public Map<String, Object> restPostAnswerReply(@PathVariable("answer") String answer, 
			HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> param )
	{
		return service.restAnswerReplyPut(answer, request, response, (String) param.get("text"));
	}
	
	// GET 답변 댓글 가져오기 GET
	@GetMapping("/{answer}/reply")
	public List<Map<String, Object>> restGetAnswerReplys( @PathVariable("answer") String answer )
	{
		return service.restGetAnswerReplys(answer);
	}
	
	// GET 답변 평가 가져오기(분리)
	@GetMapping("/{answer}/estimate")
	public Map<String, Object> restGetAnswerEstimate(@PathVariable("answer") String answer)
	{
		return service.restGetAnswerEstimate(answer);
	}
	
	// Patch 답변 평가 클릭(작업 완료)
	@PatchMapping("/{answer}/estimate")
	public Map<String, Object> restPatchAnswerEstimate(@PathVariable("answer") String answer, 
			HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> param )
	{
		return service.restPutAnswerEstimate(answer, request, response, (int)param.get("esti"));
	}
	
	// Patch 답변글 좋아요 싫어요 클릭
	@PatchMapping("/{answer}/vote")
	public Map<String, Object> restPatchAnswerVote(@PathVariable("answer") String answer, 
			HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> param )
	{
		return service.restPutAnswerVote(answer, request, response, (String) param.get("estiSeq"));
	}
	
	// Patch 답변 채택하기(작업 완료) param = answer(답변 Seq), memberSeq(답변작성자 Seq)
	@PatchMapping("/{answer}/{memberSeq}/choose")
	public Map<String, Object> restPatchAnswerChoice(@PathVariable("answer") Integer answer, @PathVariable("memberSeq") Integer memberSeq,
														HttpServletRequest request, HttpServletResponse response)
	{
		return service.restAnswerPutChoice(answer, memberSeq, request, response);
	}
	
	// Patch 답변평가(작업 완료) param = AnswerMemberSeq(작성자 Seq), QuestionSeq(질문 Seq), Gubun(구분), EstimateSeq
	@PatchMapping("/{answer}/get/estimate")
	public Map<String, Object> restPatchAnswerEstimate(@PathVariable("answer") Integer seq,
			HttpServletRequest request, HttpServletResponse response)
	{
		return service.restAnswerGetEstimate(seq, request, response);
	}
	
	// GET 받은 훈훈알 클릭시, 증정회원 표시(작업 완료) contentsType=Q(질문)/A(답변), contentsSeq=질문 또는 답변의 Seq
	@GetMapping("/{answer}/{contentsType}/extra-lists")
	public Map<String, Object> restGetAnswerExtraUsers(@PathVariable("answer") Integer answer, @PathVariable("contentsType") String contentsType,
			HttpServletRequest request, HttpServletResponse response)
	{
		return service.restAnswerGetExtraUsers(answer, contentsType, request, response);
	}
	
	// PATCH 베스트 지정(작업 완료), param = question(질문 seq),  bestRank(1이상 지정)
	@PatchMapping("/{answer}/best/{bestRank}")
	public Map<String, Object> restPatchAnswerBestSet(@PathVariable("answer") Integer answer, @PathVariable("bestRank") String bestRank,
			HttpServletRequest request, HttpServletResponse response)
	{
		return service.restAnswerPutBestSet(answer, bestRank, request, response);
	}
	
	// DELETE 베스트 해제(작업 완료), param = question(질문 seq)
	@DeleteMapping("/{answer}/best")
	public Map<String, Object> restDeleteAnswerBestCancel(@PathVariable("answer") Integer answer,
			HttpServletRequest request, HttpServletResponse response)
	{
		return service.restAnswerPutBestCancel(answer, request, response);
	}
	
	// GET 답변 가능 여부 체크(작업 완료), param = question(질문 seq), 결과 : 답변 가능 - true
	@GetMapping("/{answer}/answered-check")
	public Map<String, Object> restGetAnswerAnsweredCheck(@PathVariable("answer") Integer answer,
			HttpServletRequest request, HttpServletResponse response)
	{
		return service.restAnswerGetAnsweredCheck(answer, request, response);
	}
	
	// GET 답변 채택 가능 여부 체크(작업 완료), param = answer(답변 seq)
	@GetMapping("/{answer}/choiced-check")
	public Map<String, Object> restGetAnswerChoicedCheck(@PathVariable("answer") Integer answer,
			HttpServletRequest request, HttpServletResponse response)
	{
		return service.restAnswerGetChoicedCheck(answer, request, response);
	}
	
	// Delete 댓글 삭제하기(작업 완료) param = ReplySeq(댓글 Seq), Flag(A: 답변의 댓글)
	@DeleteMapping("/{reply}/{flag}")
	public Map<String, Object> restDeleteAnswerReplyDel(@PathVariable("reply") Integer reply, @PathVariable("flag") String flag,
			HttpServletRequest request, HttpServletResponse response)
	{
		return service.restAnswerPutReplyDel(reply, flag, request, response);
	}
	
	// GET 광고 정보(작업 완료), param 없음
	@GetMapping("/ads")
	public Map<String, Object> restGetAds()
	{
		return service.restAdsGet();
	}
	
	// PATCH 광고 카운터 올리기(작업 완료), param = answer(답변 seq), ads(광고 seq)
	@PatchMapping("/{answer}/ads/{ads}")
	public Map<String, Object> restPatchAnswerAdsView(@PathVariable("answer") Integer answer, @PathVariable("ads") Integer ads,
			HttpServletRequest request, HttpServletResponse response)
	{
		return service.restAnswerPutAdsView(answer, ads, request, response);
	}
	
	// GET 답변 AI 버튼 클릭시 받아야 할 값 가져오기(작업 완료), param = answer(답변 seq)
	@GetMapping("/{answer}/transfer")
	public Map<String, Object> restGetAnswerTransfer(@PathVariable("answer") Integer answer,
			HttpServletRequest request, HttpServletResponse response, Locale locale)
	{
		return service.restAnswerGetTransfer(answer, request, response, locale);
	}
	
	// GET 답변의 댓글 AI 버튼 클릭시(작업 완료), param = rseq(답변 댓글 seq)
	@GetMapping("/replys/{reply}/transfer")
	public Map<String, Object> restGetAnswerTransferReply(@PathVariable("reply") Integer reply,
			HttpServletRequest request, HttpServletResponse response, Locale locale)
	{
		return service.restAnswerGetTransferReply(reply, request, response, locale);
	}
}
