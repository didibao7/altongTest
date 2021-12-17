package com.altong.web.rest.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altong.web.logic.V2RankVO;
import com.altong.web.logic.answer.AnswerVO;
import com.altong.web.logic.common.TranslateVO;
import com.altong.web.logic.member.MemberVO;
import com.altong.web.logic.question.QuestionVO;
import com.altong.web.logic.reply.ReplyVO;
import com.altong.web.logic.util.CommonUtil;
import com.altong.web.rest.service.RestCommonService;
import com.altong.web.rest.service.RestQuestionService;
import com.altong.web.rest.service.RestTransferService;
import com.altong.web.service.answer.AnswerService;
import com.altong.web.service.common.CommonService;
import com.altong.web.service.member.MemberService;
import com.altong.web.service.question.QuestionService;
import com.altong.web.service.reply.ReplyService;
import com.altong.web.service.translate.TranslateService;
import com.google.common.collect.ImmutableMap;

@Service
public class RestTransferServiceImpl implements RestTransferService {
	
	@Autowired
	TranslateService translateService;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	ReplyService replyService;
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	AnswerService answerService;
	
	@Autowired
	RestCommonService restCommonService;
	
	@Autowired
	RestQuestionService restQuestionService;
	
	@Override
	public Map<String, Object> restTransferGetTransferVote(Integer transfer, String contentType, HttpServletRequest request, HttpServletResponse response, Locale locale) {
		try {
			HashMap<String, Object> vParam = new HashMap<String, Object>();
			vParam.put("contentSeq", transfer);
			vParam.put("contentType", contentType);

			HashMap<String, Object> voteItem = commonService.getQuestionVoteSum(vParam);

			final Map<String, Object> result = ImmutableMap.<String, Object> builder()
					.put( "code", "success" )
					.put( "qtGood", voteItem.get("good") )
					.put( "qtBad", voteItem.get("bad") )
					.build();

			return result;
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}
	
	@Override
	public Map<String, Object> restTransferPutTransferVote(Integer transfer, String act, String contentType, HttpServletRequest request, HttpServletResponse response, Locale locale) {
		try {
			return restQuestionService.questionVote(act, transfer, contentType, request, response);
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}
		
	@Override
	public Map<String, Object> transProcess(int contentSeq, String contentType, HttpServletRequest request, HttpServletResponse response, Locale locale) {

		String trnChk = "N";

		try {

			final int userSeq = restCommonService.getRestCookieUser(request, response);

			HashMap<String, Object> localeItem = CommonUtil.getLocale(locale);
			String lang = String.valueOf(localeItem.get("lang"));
			String targetLang = lang;
			if(userSeq > 0) {
				MemberVO mVo = memberService.getAlmoneyMyJoin(userSeq);
				targetLang = mVo.getLang();
			}

			String targetLang_ = targetLang;
			if(targetLang.contains("-")) {
				String[] targetLangArr = targetLang.split("-");
				targetLang_ = targetLangArr[0];
			}

			HashMap<String, Object> qtParam = new HashMap<String, Object>();
			qtParam.put("replyType", contentType); 	// Q:질문, A:답변, R:댓글
			qtParam.put("orgSeq", contentSeq); 	// 질문/답변/댓글 의 Seq
			qtParam.put("lang", targetLang_);// 번역 언어

			int trnCnt = commonService.getTrnslateTotalCntBySeq(qtParam);
			int perSonTrnsCnt = 0;
			int machineTrnsCnt = 0;

			int tSeq = 0;
			int tUserSeq = 0;
			String tTitle = "";
			String tContents = "";
			String tNickName = "";
			String tFlagNickName = "";
			int trnType = 1; //1:기계번역, 2:사람번역
			String tLang = "";
			int tSirenN = 0;
			int tUserLv = 0;
			BigDecimal tExtraAlmoney = new BigDecimal("0.0");
			int tCount = 0;
			int qtGood = 0;
			int qtBad = 0;

			int ai_seq = commonService.getUserSeqForTrnslateByNick("AI_T");

			SimpleDateFormat dType = new SimpleDateFormat("yyyy-MM-dd aa hh:mm:ss", Locale.KOREA);
			Calendar beginDay = Calendar.getInstance();
			String bHour = dType.format(beginDay.getTime());

			if(trnCnt == 0) {
				trnChk = subTranslate(contentSeq, contentType, targetLang, bHour, ai_seq);

				HashMap<String, Object> mParam = new HashMap<String, Object>();
				mParam.put("trnType", 1); 		// 1:기계번역, 2:사람번역
				mParam.put("replyType", contentType); 	// Q:질문, A:답변, R:질문 댓글, S:답변 댓글
				mParam.put("orgSeq", contentSeq); 	// 질문/답변/댓글 의 Seq
				mParam.put("lang", targetLang_);// 번역 언어

				machineTrnsCnt = commonService.getTrnslateCntBySeq(mParam);
			}
			else {
				//사람 번역이 있으면 사람 번역을
				//사람 번역이 없으면 기계 번역을
				//가져올것

				HashMap<String, Object> tParam = new HashMap<String, Object>();
				tParam.put("trnType", 2); 		// 1:기계번역, 2:사람번역
				tParam.put("replyType", contentType); 	// Q:질문, A:답변, R:질문 댓글, S:답변 댓글
				tParam.put("orgSeq", contentSeq); 	// 질문/답변/댓글 의 Seq
				tParam.put("lang", targetLang_);// 번역 언어

				perSonTrnsCnt = commonService.getTrnslateCntBySeq(tParam);

				HashMap<String, Object> mParam = new HashMap<String, Object>();
				mParam.put("trnType", 1); 		// 1:기계번역, 2:사람번역
				mParam.put("replyType", contentType); 	// Q:질문, A:답변, R:댓글
				mParam.put("orgSeq", contentSeq); 	// 질문/답변/댓글 의 Seq
				mParam.put("lang", targetLang_);// 번역 언어

				machineTrnsCnt = commonService.getTrnslateCntBySeq(mParam);
			}
			//System.out.println("machineTrnsCnt : " + machineTrnsCnt);
			if(perSonTrnsCnt > 0) {
				HashMap<String, Object> ptParam = new HashMap<String, Object>();
				ptParam.put("trnType", 2); 		// 1:기계번역, 2:사람번역
				ptParam.put("replyType", contentType); 	// Q:질문, A:답변, R:질문 댓글, S:답변 댓글
				ptParam.put("orgSeq", contentSeq); 	// 질문/답변/댓글 의 Seq
				ptParam.put("lang", targetLang_);// 번역 언어

				TranslateVO ptVo = commonService.getTrnslateBySeq(ptParam);


				HashMap<String, Object> qvParam = new HashMap<String, Object>();
				qvParam.put("userSeq", ptVo.getUserSeq());
				qvParam.put("contentsType", "T");			//추후 수정
				qvParam.put("contentsSeq", ptVo.getSeq());

				V2RankVO qmVo = commonService.getQtUserInfoBySeq(qvParam);


				tSeq = ptVo.getSeq();
				tUserSeq = ptVo.getUserSeq();
				tTitle = ptVo.getTitle();
				tContents = ptVo.getComment(); //comment
				tNickName = qmVo.getNickName();
				tFlagNickName = ptVo.getFlagNickName();
				trnType = ptVo.getTrnType(); //1:기계번역, 2:사람번역
				tLang = ptVo.getLang();
				tSirenN = Integer.parseInt(qmVo.getSirenN());
				tUserLv = Integer.parseInt(qmVo.getLv());
				tExtraAlmoney = qmVo.getExtraAlmoney();
				tCount = ptVo.getReadCount();

				HashMap<String, Object> qtVoteParam = new HashMap<String, Object>();
				qtVoteParam.put("contentSeq", tSeq);
				qtVoteParam.put("contentType", "T");
				int qtVoteCount = commonService.getQuestionVoteCountSum(qtVoteParam);

				HashMap<String, Object> qtVote = null;

				if(qtVoteCount > 0) {
					qtVote = commonService.getQuestionVoteSum(qtVoteParam);
					qtGood = Integer.parseInt( String.valueOf( qtVote.get("good") ) );
					qtBad = Integer.parseInt( String.valueOf( qtVote.get("bad") ) );
				}

			}
			else if(machineTrnsCnt > 0) {
				HashMap<String, Object> mtParam = new HashMap<String, Object>();
				mtParam.put("trnType", 1); 		// 1:기계번역, 2:사람번역
				mtParam.put("replyType", contentType); 	// Q:질문, A:답변, R:질문의 댓글 S:답변의 댓글
				mtParam.put("orgSeq", contentSeq); 	// 질문/답변/댓글 의 Seq
				mtParam.put("lang", targetLang_);// 번역 언어

				//기계번역의 경우 6개월 경과 여부에 따라 다시 번역시도
				TranslateVO machineTrn = commonService.getTrnslateBySeq(mtParam);
				//System.out.println("getCreateDt : " + machineTrn.getCreateDt());
				boolean machineTrnChk6month = CommonUtil.checkMachineTranslate(machineTrn.getCreateDt());

				if(machineTrnChk6month == true) {
					trnChk = subTranslate(contentSeq, contentType, targetLang, bHour, ai_seq);
				}
				else {
					trnChk = "Y";
				}

				if(targetLang.contains("-")) {
					String[] detectLangArr = targetLang.split("-");
					targetLang = detectLangArr[0];
				}

				HashMap<String, Object> ptParam = new HashMap<String, Object>();
				ptParam.put("trnType", 1); 		// 1:기계번역, 2:사람번역
				ptParam.put("replyType", contentType); 	// Q:질문, A:답변, R:질문의 댓글 S:답변의 댓글
				ptParam.put("orgSeq", contentSeq); 	// 질문/답변/댓글 의 Seq
				ptParam.put("lang", targetLang_);// 번역 언어
				TranslateVO ptVo = commonService.getTrnslateBySeq(ptParam);

				HashMap<String, Object> qvParam = new HashMap<String, Object>();
				qvParam.put("userSeq", ptVo.getUserSeq());
				qvParam.put("contentsType", "T");			//추후 수정
				qvParam.put("contentsSeq", ptVo.getSeq());

				V2RankVO qmVo = commonService.getQtUserInfoBySeq(qvParam);

				tSeq = ptVo.getSeq();
				tUserSeq = ptVo.getUserSeq();
				tTitle = ptVo.getTitle();
				tContents = ptVo.getComment(); //comment
				tNickName = qmVo.getNickName();
				tFlagNickName = ptVo.getFlagNickName();
				trnType = ptVo.getTrnType(); //1:기계번역, 2:사람번역
				tLang = ptVo.getLang();
				tSirenN = Integer.parseInt(qmVo.getSirenN());
				tUserLv = Integer.parseInt(qmVo.getLv());
				tExtraAlmoney = qmVo.getExtraAlmoney();
				tCount = ptVo.getReadCount();
				//System.out.println("trnChk : " + trnChk);

				HashMap<String, Object> qtVoteParam = new HashMap<String, Object>();
				qtVoteParam.put("contentSeq", tSeq);
				qtVoteParam.put("contentType", "T");
				int qtVoteCount = commonService.getQuestionVoteCountSum(qtVoteParam);

				HashMap<String, Object> qtVote = null;

				if(qtVoteCount > 0) {
					qtVote = commonService.getQuestionVoteSum(qtVoteParam);
					qtGood = Integer.parseInt( String.valueOf( qtVote.get("good") ) );
					qtBad = Integer.parseInt( String.valueOf( qtVote.get("bad") ) );
				}
			}

			//카운터를 다시 구할것
			int trnReCnt = commonService.getTrnslateTotalCntBySeq(qtParam);

			//번역 내용 구하기
			final Map<String, Object> result = ImmutableMap.<String, Object> builder()
					.put( "trnChk", trnChk )
					.put( "trnCnt", trnReCnt )
					.put( "tSeq", tSeq )
					.put( "tUserSeq", tUserSeq )
					.put( "tTitle", tTitle )
					.put( "tContents", tContents )
					.put( "tNickName", tNickName )
					.put( "tFlagNickName", tFlagNickName )
					.put( "trnType", trnType )
					.put( "tLang", tLang )
					.put( "tSirenN", tSirenN )
					.put( "tUserLv", tUserLv )
					.put( "tExtraAlmoney", tExtraAlmoney )
					.put( "tCount", tCount )
					.put( "qtGood", qtGood )
					.put( "qtBad", qtBad )
					.build();

			return result;
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}
	
	@Override
	public String subTranslate(int contentSeq, String contentType, String targetLang, String bHour, int ai_seq) throws Exception {
		String title = "";
		String contents = "";
		String trnChk = "N";
		String event = "N";

		if(contentType.equals("Q")) {
			QuestionVO qVo = questionService.getQueInfoForTransBySeq(contentSeq);
			//질문
			title = qVo.getTitle();
			contents = qVo.getContents();


			String detectTitleLang = CommonUtil.detectLanguage(title);

			String detectLang = "";

			//기계 번역
			if(!detectTitleLang.equals(targetLang)) {
				// 이벤트 인지 구분하기
				HashMap<String, Object> evParam = new HashMap<String, Object>();
				evParam.put("seq", contentSeq);

				int eventChk = commonService.getSaveEventSubCount(evParam);

				if(eventChk > 0) {
					event = "Y";
				}

				detectLang = CommonUtil.detectLanguage(title);

				String transTitle = CommonUtil.strToTranslate(detectTitleLang, targetLang, title, event);
				String transComment = CommonUtil.strToTranslate(detectTitleLang, targetLang, contents, event);

				if(transComment.equals("")) {
					transComment = contents;
				}

				if(detectLang.contains("-")) {
					String[] detectLangArr = detectLang.split("-");
					detectLang = detectLangArr[0];
				}

				if(targetLang.contains("-")) {
					String[] targetLangArr = targetLang.split("-");
					targetLang = targetLangArr[0];
				}

				HashMap<String, Object> tParam = new HashMap<String, Object>();
				tParam.put("trnType", 1);
				tParam.put("replyType", "Q");
				tParam.put("orgSeq", contentSeq);
				tParam.put("userSeq", ai_seq);
				tParam.put("flagNickName", "Y");
				tParam.put("langOrg", detectLang);
				tParam.put("lang", targetLang);
				tParam.put("title", transTitle);
				tParam.put("comment", transComment);
				tParam.put("createDt", bHour);

				commonService.addTranslate(tParam);

				trnChk = "Y";
			}
		}
		else if(contentType.equals("A")) {
			AnswerVO aVo = answerService.getAnsInfoForTransBySeq(contentSeq);
			//답글
			contents = aVo.getAnswer();
			String detectContentsLang = CommonUtil.detectLanguage(contents);

			//기계 번역
			if(!detectContentsLang.equals(targetLang)) {
				String transComment = CommonUtil.strToTranslate(detectContentsLang, targetLang, contents, "N");

				if(detectContentsLang.contains("-")) {
					String[] detectContentsLangArr = detectContentsLang.split("-");
					detectContentsLang = detectContentsLangArr[0];
				}

				if(targetLang.contains("-")) {
					String[] targetLangArr = targetLang.split("-");
					targetLang = targetLangArr[0];
				}

				HashMap<String, Object> tParam = new HashMap<String, Object>();
				tParam.put("trnType", 1);
				tParam.put("replyType", "A");
				tParam.put("orgSeq", contentSeq);
				tParam.put("userSeq", ai_seq);
				tParam.put("flagNickName", "Y");
				tParam.put("langOrg", detectContentsLang);
				tParam.put("lang", targetLang);
				tParam.put("title", "");
				tParam.put("comment", transComment);
				tParam.put("createDt", bHour);

				commonService.addTranslate(tParam);

				trnChk = "Y";
			}
		}
		else if(contentType.equals("R")){ // 질문 댓글 번역

			HashMap<String, Object> rParam = new HashMap<String, Object>();
			rParam.put("flag", "Q");
			rParam.put("targetSeq", contentSeq);

			ReplyVO rVo = replyService.getReplyForTrnInfo(rParam);

			//댓글
			contents = rVo.getReply();

			String detectContentsLang = CommonUtil.detectLanguage(contents);

			if(detectContentsLang.equals("und")) { detectContentsLang = targetLang; }

			//기계 번역
			if(!detectContentsLang.equals(targetLang)) {
				String transComment = CommonUtil.strToTranslate(detectContentsLang, targetLang, contents, "N");

				if(detectContentsLang.contains("-")) {
					String[] detectContentsLangArr = detectContentsLang.split("-");
					detectContentsLang = detectContentsLangArr[0];
				}

				if(targetLang.contains("-")) {
					String[] targetLangArr = targetLang.split("-");
					targetLang = targetLangArr[0];
				}

				HashMap<String, Object> tParam = new HashMap<String, Object>();
				tParam.put("trnType", 1);
				tParam.put("replyType", "R");
				tParam.put("orgSeq", contentSeq);
				tParam.put("userSeq", ai_seq);
				tParam.put("flagNickName", "Y");
				tParam.put("langOrg", detectContentsLang);
				tParam.put("lang", targetLang);
				tParam.put("title", "");
				tParam.put("comment", transComment);
				tParam.put("createDt", bHour);

				commonService.addTranslate(tParam);

				trnChk = "Y";
			}
		}
		else if(contentType.equals("S")){ // 답변 댓글 번역
			HashMap<String, Object> rParam = new HashMap<String, Object>();
			rParam.put("flag", "A");
			rParam.put("targetSeq", contentSeq);

			ReplyVO rVo = replyService.getReplyForTrnInfo(rParam);

			//댓글
			contents = rVo.getReply();

			String detectContentsLang = CommonUtil.detectLanguage(contents);
			System.out.println("detectContentsLang : " + detectContentsLang);
			if(detectContentsLang.equals("und")) { detectContentsLang = targetLang; }

			//기계 번역
			if(!detectContentsLang.equals(targetLang)) {
				String transComment = CommonUtil.strToTranslate(detectContentsLang, targetLang, contents, "N");

				if(detectContentsLang.contains("-")) {
					String[] detectContentsLangArr = detectContentsLang.split("-");
					detectContentsLang = detectContentsLangArr[0];
				}

				if(targetLang.contains("-")) {
					String[] targetLangArr = targetLang.split("-");
					targetLang = targetLangArr[0];
				}

				HashMap<String, Object> tParam = new HashMap<String, Object>();
				tParam.put("trnType", 1);
				tParam.put("replyType", "S");
				tParam.put("orgSeq", contentSeq);
				tParam.put("userSeq", ai_seq);
				tParam.put("flagNickName", "Y");
				tParam.put("langOrg", detectContentsLang);
				tParam.put("lang", targetLang);
				tParam.put("title", "");
				tParam.put("comment", transComment);
				tParam.put("createDt", bHour);

				commonService.addTranslate(tParam);

				trnChk = "Y";
			}
		}

		return trnChk;
	}
}
