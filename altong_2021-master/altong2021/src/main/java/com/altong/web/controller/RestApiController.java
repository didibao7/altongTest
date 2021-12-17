package com.altong.web.controller;

import java.util.Locale;
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
import org.springframework.web.bind.annotation.RestController;

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
import com.altong.web.service.rest.RestApiService;
import com.altong.web.service.rest.RestService;

@CrossOrigin
@Controller
@RestController
@RequestMapping("/restApi")
public class RestApiController {
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

	@Autowired
	RestApiService restApiService;


	// PUT 질문 꼭대기 (작업 완료)
	@RequestMapping(value="/questions/{question}/movetop", method = RequestMethod.PUT)
	public Map<String, Object> restQuestionPutMoveTop(@PathVariable("question") Integer question, HttpServletRequest request, HttpServletResponse response)
	{
		return restApiService.restQuestionPutMoveTop(question, request, response);
	}

	// PUT 신고(작업 완료) param = ACT, H_Type, H_Seq, H_Reason, H_Reason_txt
	@RequestMapping(value="/questions/{seq}/siren", method = {RequestMethod.GET, RequestMethod.PUT})
	public Map<String, Object> restCommonSiren(@RequestBody Map<String, Object> param, @PathVariable("seq") Integer seq, HttpServletRequest request, HttpServletResponse response)
	{
		return restApiService.restCommonSiren(param, seq, request, response);
	}

	// PUT 친구추가/멘토추가(작업 완료)
	@RequestMapping(value="/users/{partnerSeq}/{flagPartner}/partner-save", method = {RequestMethod.GET, RequestMethod.PUT})
	public Map<String, Object> restMemberPartnerSave(@PathVariable("partnerSeq") Integer partnerSeq, @PathVariable("flagPartner") String flagPartner,
														HttpServletRequest request, HttpServletResponse response)
	{
		return restApiService.restMemberPartnerSave(partnerSeq, flagPartner, request, response);
	}

	// PUT 쪽지 보내기 (작업 완료) param = Contents
	@RequestMapping(value="/messages/{receiverSeq}/send", method = {RequestMethod.GET, RequestMethod.PUT})
	public Map<String, Object> restMessageSend(@RequestBody Map<String, Object> param, @PathVariable("receiverSeq") Integer receiverSeq,
														HttpServletRequest request, HttpServletResponse response)
	{
		return restApiService.restMessageSend(param, receiverSeq, request, response);
	}

	// PUT 답변 채택하기(작업 완료) param = answer(답변 Seq), memberSeq(답변작성자 Seq)
	@RequestMapping(value="/answers/{answer}/{memberSeq}/answer-choice", method = {RequestMethod.GET, RequestMethod.PUT})
	public Map<String, Object> restAnswerPutChoice(@PathVariable("answer") Integer answer, @PathVariable("memberSeq") Integer memberSeq,
														HttpServletRequest request, HttpServletResponse response)
	{
		return restApiService.restAnswerPutChoice(answer, memberSeq, request, response);
	}

	// PUT 댓글 삭제하기(작업 완료) param = ReplySeq(댓글 Seq), Flag(A: 답변의 댓글)
	@RequestMapping(value="/answers/{reply}/{flag}/reply-del", method = {RequestMethod.GET, RequestMethod.DELETE})
	public Map<String, Object> restAnswerPutReplyDel(@PathVariable("reply") Integer reply, @PathVariable("flag") String flag,
			HttpServletRequest request, HttpServletResponse response)
	{
		return restApiService.restAnswerPutReplyDel(reply, flag, request, response);
	}

	// GET 답변평가(작업 완료) param = AnswerMemberSeq(작성자 Seq), QuestionSeq(질문 Seq), Gubun(구분), EstimateSeq
	@RequestMapping(value="/answers/{seq}/estimate", method = RequestMethod.GET)
	public Map<String, Object> restAnswerGetEstimate(@PathVariable("seq") Integer seq,
			HttpServletRequest request, HttpServletResponse response)
	{
		return restApiService.restAnswerGetEstimate(seq, request, response);
	}

	// GET 받은 훈훈알 클릭시, 증정회원 표시(작업 완료) contentsType=Q(질문)/A(답변), contentsSeq=질문 또는 답변의 Seq
	@RequestMapping(value="/answers/{contentsSeq}/{contentsType}/extra-users", method = RequestMethod.GET)
	public Map<String, Object> restAnswerGetExtraUsers(@PathVariable("contentsSeq") Integer contentsSeq, @PathVariable("contentsType") String contentsType,
			HttpServletRequest request, HttpServletResponse response)
	{
		return restApiService.restAnswerGetExtraUsers(contentsSeq, contentsType, request, response);
	}

	// GET 답변 노출 시, 광고 팝업 여부를 서버에게 묻고 열기-광고 열람 여부(작업 완료) param = question(질문 번호), answer(답변 번호)
	@RequestMapping(value="/answers/{answer}/ads-view", method = RequestMethod.GET)
	public Map<String, Object> restAnswerGetAdsView(@PathVariable("answer") Integer answer,
			HttpServletRequest request, HttpServletResponse response)
	{
		return restApiService.restAnswerGetAdsView(answer, request, response);
	}

	// GET 광고 정보(작업 완료), param 없음
	@RequestMapping(value="/ads", method = RequestMethod.GET)
	public Map<String, Object> restAdsGet()
	{
		return restApiService.restAdsGet();
	}

	// PUT 광고 카운터 올리기(작업 완료), param = answer(답변 seq), ads(광고 seq)
	@RequestMapping(value="/answers/{answer}/{ads}/ads-view", method = RequestMethod.PUT)
	public Map<String, Object> restAnswerPutAdsView(@PathVariable("answer") Integer answer, @PathVariable("ads") Integer ads,
			HttpServletRequest request, HttpServletResponse response)
	{
		return restApiService.restAnswerPutAdsView(answer, ads, request, response);
	}

	// PUT 베스트 지정(작업 완료), param = question(질문 seq),  bestRank(1이상 지정)
	@RequestMapping(value="/answers/{question}/{bestRank}/best-set", method = RequestMethod.PUT)
	public Map<String, Object> restAnswerPutBestSet(@PathVariable("question") Integer question, @PathVariable("bestRank") String bestRank,
			HttpServletRequest request, HttpServletResponse response)
	{
		return restApiService.restAnswerPutBestSet(question, bestRank, request, response);
	}

	// PUT 베스트 해제(작업 완료), param = question(질문 seq)
	@RequestMapping(value="/answers/{question}/best-cancel", method = RequestMethod.PUT)
	public Map<String, Object> restAnswerPutBestCancel(@PathVariable("question") Integer question,
			HttpServletRequest request, HttpServletResponse response)
	{
		return restApiService.restAnswerPutBestCancel(question, request, response);
	}

	// GET 답변 가능 여부 체크(작업 완료), param = question(질문 seq), 결과 : 답변 가능 - true
	@RequestMapping(value="/answers/{question}/answered-check", method = RequestMethod.GET)
	public Map<String, Object> restAnswerGetAnsweredCheck(@PathVariable("question") Integer question,
			HttpServletRequest request, HttpServletResponse response)
	{
		return restApiService.restAnswerGetAnsweredCheck(question, request, response);
	}

	// GET 답변 채택 가능 여부 체크(작업 완료), param = answer(답변 seq)
	@RequestMapping(value="/answers/{answer}/choiced-check", method = RequestMethod.GET)
	public Map<String, Object> restAnswerGetChoicedCheck(@PathVariable("answer") Integer answer,
			HttpServletRequest request, HttpServletResponse response)
	{
		return restApiService.restAnswerGetChoicedCheck(answer, request, response);
	}

	// GET 질문 AI 버튼 클릭시 받아야 할 값 가져오기(작업 완료), param = question(질문 seq)
	@RequestMapping(value="/questions/{question}/transfer", method = RequestMethod.GET)
	public Map<String, Object> restQuestionGetTransfer(@PathVariable("question") Integer question,
			HttpServletRequest request, HttpServletResponse response, Locale locale)
	{
		return restApiService.restQuestionGetTransfer(question, request, response, locale);
	}

	// GET 답변 AI 버튼 클릭시 받아야 할 값 가져오기(작업 완료), param = answer(답변 seq)
	@RequestMapping(value="/answers/{answer}/transfer", method = RequestMethod.GET)
	public Map<String, Object> restAnswerGetTransfer(@PathVariable("answer") Integer answer,
			HttpServletRequest request, HttpServletResponse response, Locale locale)
	{
		return restApiService.restAnswerGetTransfer(answer, request, response, locale);
	}

	// GET 질문 번역 좋아요/싫어요 수 가져오기(작업 완료), param = transfer(번역글 seq), contentType(T:기계번역, Q:질문, A:답변)
	@RequestMapping(value="/transfers/{transfer}/vote/{contentType}", method = RequestMethod.GET)
	public Map<String, Object> restQuestionGetTransferVote(@PathVariable("transfer") Integer transfer, @PathVariable("contentType") String contentType,
			HttpServletRequest request, HttpServletResponse response, Locale locale)
	{
		return restApiService.restTransferGetTransferVote(transfer, contentType, request, response, locale);
	}

	// PUT 질문 번역에 대한 평가 클릭하기(작업 완료), param = transfer(번역글 seq), act(G:좋아요, B:싫어요), contentType(T:기계번역, Q:질문, A:답변)
	@RequestMapping(value="/transfers/{transfer}/vote/{act}/{contentType}", method = RequestMethod.PUT)
	public Map<String, Object> restQuestionPutTransferVote(@PathVariable("transfer") Integer transfer, @PathVariable("act") String act, @PathVariable("contentType") String contentType,
			HttpServletRequest request, HttpServletResponse response, Locale locale)
	{
		return restApiService.restTransferPutTransferVote(transfer, act, contentType, request, response, locale);
	}

	// GET 질문의 댓글 AI 버튼 클릭시(작업 완료), param = rseq(질문 댓글 seq)
	@RequestMapping(value="/questions/reply/{rseq}/transfer", method = RequestMethod.GET)
	public Map<String, Object> restQuestionGetTransferReply(@PathVariable("rseq") Integer rseq,
			HttpServletRequest request, HttpServletResponse response, Locale locale)
	{
		return restApiService.restQuestionGetTransferReply(rseq, request, response, locale);
	}

	// GET 답변의 댓글 AI 버튼 클릭시(작업 완료), param = rseq(답변 댓글 seq)
	@RequestMapping(value="/answers/reply/{rseq}/transfer", method = RequestMethod.GET)
	public Map<String, Object> restAnswerGetTransferReply(@PathVariable("rseq") Integer rseq,
			HttpServletRequest request, HttpServletResponse response, Locale locale)
	{
		return restApiService.restAnswerGetTransferReply(rseq, request, response, locale);
	}

}
