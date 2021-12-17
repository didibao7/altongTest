package com.altong.web.service.rest;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RestApiService {

	Map<String, Object> restQuestionPutMoveTop(Integer question, HttpServletRequest request, HttpServletResponse response);
	Map<String, Object> restCommonSiren(Map<String, Object> param, Integer seq, HttpServletRequest request, HttpServletResponse response);
	Map<String, Object> restMemberPartnerSave(Integer partnerSeq, String flagPartner, HttpServletRequest request, HttpServletResponse response);
	Map<String, Object> restMessageSend(Map<String, Object> param, Integer receiverSeq, HttpServletRequest request, HttpServletResponse response);
	Map<String, Object> restAnswerPutChoice(Integer answer, Integer memberSeq, HttpServletRequest request, HttpServletResponse response);
	Map<String, Object> restAnswerPutReplyDel(Integer reply, String flag, HttpServletRequest request, HttpServletResponse response);
	Map<String, Object> restAnswerGetEstimate(Integer seq, HttpServletRequest request, HttpServletResponse response);
	Map<String, Object> restAnswerGetExtraUsers(Integer contentsSeq, String contentsType, HttpServletRequest request, HttpServletResponse response);
	Map<String, Object> restAnswerGetAdsView(Integer answer, HttpServletRequest request, HttpServletResponse response);
	Map<String, Object> restAdsGet();
	Map<String, Object> restAnswerPutAdsView(Integer answer, Integer ads, HttpServletRequest request, HttpServletResponse response);
	Map<String, Object> restAnswerPutBestSet(Integer question, String bestRank, HttpServletRequest request, HttpServletResponse response);
	Map<String, Object> restAnswerPutBestCancel(Integer question, HttpServletRequest request, HttpServletResponse response);
	Map<String, Object> restAnswerGetAnsweredCheck(Integer question, HttpServletRequest request, HttpServletResponse response);
	Map<String, Object> restAnswerGetChoicedCheck(Integer answer, HttpServletRequest request, HttpServletResponse response);

	//여기부터 번역
	Map<String, Object> restQuestionGetTransfer(Integer question, HttpServletRequest request, HttpServletResponse response, Locale locale);
	Map<String, Object> restAnswerGetTransfer(Integer answer, HttpServletRequest request, HttpServletResponse response, Locale locale);

	Map<String, Object> restTransferGetTransferVote(Integer transfer, String contentType, HttpServletRequest request, HttpServletResponse response, Locale locale);
	Map<String, Object> restTransferPutTransferVote(Integer transfer, String act, String contentType, HttpServletRequest request, HttpServletResponse response, Locale locale);

	Map<String, Object> restQuestionGetTransferReply(Integer rseq, HttpServletRequest request, HttpServletResponse response, Locale locale);
	Map<String, Object> restAnswerGetTransferReply(Integer rseq, HttpServletRequest request, HttpServletResponse response, Locale locale);
	//여기까지 번역
}
