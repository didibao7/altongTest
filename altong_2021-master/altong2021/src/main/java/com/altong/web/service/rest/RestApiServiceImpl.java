package com.altong.web.service.rest;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altong.web.dao.RestDAO;
import com.altong.web.dao.answer.AnswerDAO;
import com.altong.web.logic.V2RankVO;
import com.altong.web.logic.ad.AdVO;
import com.altong.web.logic.almoney.AlmoneyVO;
import com.altong.web.logic.answer.AnswerVO;
import com.altong.web.logic.common.TranslateVO;
import com.altong.web.logic.member.MemberVO;
import com.altong.web.logic.question.QuestionVO;
import com.altong.web.logic.reply.ReplyVO;
import com.altong.web.logic.util.CommonUtil;
import com.altong.web.service.MessageService;
import com.altong.web.service.answer.AnswerService;
import com.altong.web.service.common.CommonService;
import com.altong.web.service.member.MemberService;
import com.altong.web.service.question.QuestionService;
import com.altong.web.service.reply.ReplyService;
import com.google.common.collect.ImmutableMap;

@Service
public class RestApiServiceImpl implements RestApiService {

	@Autowired
	AnswerDAO answerDAO;

	@Autowired RestDAO restDAO;

	@Autowired
	RestService restService;

	@Autowired
	CommonService commonService;

	@Autowired
	MemberService memberService;

	@Autowired
	MessageService messageService;

	@Autowired
	AnswerService answerService;

	@Autowired
	ReplyService replyService;

	@Autowired
	QuestionService questionService;

	@Override
	public Map<String, Object> restQuestionPutMoveTop(Integer question, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restService.getRestCookieUser(request, response);

			if(userSeq == 0) return ImmutableMap.of( "code", "notlogin" );

			//이 기능의 사용 빈도가 많아지면 아래의 쿼리를 프로시저로 만들자.
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("userSeq", userSeq);
			param.put("questionSeq", question);

			HashMap<String, Object> r = answerDAO.setMoveQuestion(param);

			final int returnCode = r != null ?  Integer.parseInt(r.get("ReturnCode").toString()) : 0 ;

			switch(returnCode) {
			case 1 :
				return ImmutableMap.of( "msg", true );
			case 2 :
				return ImmutableMap.of( "msg","알머니 잔액이 부족합니다." );
			case 51 :
				return ImmutableMap.of( "msg","대상 글이 존재하지 않습니다." );
			case 52 :
				return ImmutableMap.of( "msg","더 이상 올릴 수 없습니다." );
			default:
				return ImmutableMap.of( "msg","예상치 못한 오류가 발생하였습니다." );
			}

		} catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	// param = ACT, H_Type, H_Reason, H_Reason_txt
	@Override
	public Map<String, Object> restCommonSiren(Map<String, Object> param, Integer seq, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restService.getRestCookieUser(request, response);

			if(userSeq == 0) return ImmutableMap.of( "code", "notlogin" );

			String act = String.valueOf(param.get("ACT"));
			String hType = String.valueOf(param.get("H_Type"));
			String hReason = String.valueOf(param.get("H_Reason"));
			String hReasonTxt = String.valueOf(param.get("H_Reason_txt"));

			String table = "";
			switch(hType) {
				case "A" :
					table = "T_ANSWER";
					break;
				case "AR" :
					table = "T_REPLY_ANSWER";
					break;
				case "Q" :
					table = "T_QUESTION";
					break;
				case "QR" :
					table = "T_REPLY_QUESTION";
					break;
				case "T":
					table = "T_TRANSLATE";
					break;
				default :
					table = "";
			}


			if(act.equals("CheckSiren")) {
				HashMap<String, Object> param1 = new HashMap<String, Object>();
				param1.put("hType", hType);
				param1.put("hSeq", seq);
				param1.put("userSeq", userSeq);

				String sType = commonService.getSirenReporter(param1);

				if(sType != null) {
					return ImmutableMap.of( "msg", "이미 신고한 글입니다." );
				}
				else {
					return ImmutableMap.of( "msg", true );
				}
			}
			else if(act.equals("RegSiren")) {
				if(request.getParameter("H_Reason") == "" || request.getParameter("H_Reason") == null) {
					return ImmutableMap.of( "msg", "신고 사유를 선택하여주세요." );
				}

				HashMap<String, Object> param2 = new HashMap<String, Object>();
				param2.put("hType", hType);
				param2.put("hSeq", seq);
				param2.put("userSeq", userSeq);
				param2.put("hReason", hReason);
				param2.put("hReasonTxt", hReasonTxt);
				param2.put("table", table);

				HashMap<String, Object> regSiren = commonService.setRegSiren(param2);

				final String regSirenCode = String.valueOf(regSiren.get("ReturnCode"));

				switch(regSirenCode) {
					case "1":
						return ImmutableMap.of( "msg", "신고 처리가 완료되었습니다." );
					case "51":
						return ImmutableMap.of( "msg", "이미 신고한 글 입니다." );
					case "52":
						return ImmutableMap.of( "msg", "대상 글이 존재하지 않습니다." );
					case "53":
						return ImmutableMap.of( "msg", "본인의 글은 신고할 수 없습니다." );
					default:
						return ImmutableMap.of( "msg", "예상치 못한 오류가 발생하였습니다." );
				}
			}
			return ImmutableMap.of( "msg", "예상치 못한 오류가 발생하였습니다." );
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	@Override
	public Map<String, Object> restMemberPartnerSave(Integer partnerSeq, String flagPartner, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restService.getRestCookieUser(request, response);

			if(userSeq == 0) return ImmutableMap.of( "code", "notlogin" );

			String format = "yyyy-MM-dd aa hh:mm:ss";
			Calendar todays = Calendar.getInstance();
			SimpleDateFormat type = new SimpleDateFormat(format);
			String todayDate = type.format(todays.getTime());


			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("userSeq", userSeq);
			param.put("partnerSeq", partnerSeq);
			param.put("flagPartner", flagPartner);
			param.put("dateReg", todayDate);

			final int returnCode = memberService.setPartnerSave(param);

			switch(returnCode) {
				case 0:
					return ImmutableMap.of( "msg", "정상적으로 추가되었습니다." );
				case 1:
					return ImmutableMap.of( "msg","이미 등록된 회원입니다." );
				default:
					return ImmutableMap.of( "msg","처리 중 오류가 발생하였습니다.\n잠시 후 다시 시도해주세요." );
			}
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	@Override
	public Map<String, Object> restMessageSend(Map<String, Object> param, Integer receiverSeq, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restService.getRestCookieUser(request, response);

			String contents = param.get("Contents") != null ? String.valueOf(param.get("Contents")) : "";


			if(userSeq == 0) return ImmutableMap.of( "code", "notlogin" );
			if(receiverSeq == userSeq) return ImmutableMap.of( "msg", "자신에게는 쪽지를 보낼 수 없습니다." );
			if(contents.equals("")) return ImmutableMap.of( "msg", "내용을 입력하세요." );

			HashMap<String, Object> param1 = new HashMap<String, Object>();
			param1.put("userSeq", userSeq);
			param1.put("blockUserSeq", receiverSeq);
			param1.put("contents", contents);

			final int returnCode = messageService.setSendMsg(param1);

			switch(returnCode) {
				case 1:
					return ImmutableMap.of( "msg", "차단하신 회원님께는 쪽지를 발송하실 수 없습니다." );
				case 2:
					return ImmutableMap.of( "msg","회원님으로부터의 쪽지 수신을 차단한 상태이므로 \n해당 회원님께 쪽지를 발송하실 수 없습니다." );
				case 3:
					return ImmutableMap.of( "msg", "알 수 없는 이유로 쪽지 전송에 실패했습니다." );
				default:
					return ImmutableMap.of( "msg", true );
			}
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	@Override
	public Map<String, Object> restAnswerPutChoice(Integer answer, Integer memberSeq, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restService.getRestCookieUser(request, response);

			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("ans_seq", answer);
			param.put("answerMemberSeq", memberSeq);

			HashMap<String, Object> choice = answerService.setAnswerChoice(param);

			final String returnCode = String.valueOf(choice.get("RETURN"));

			switch(returnCode) {
			case "0" :
				return ImmutableMap.of( "msg", "success" );
			default :
				return ImmutableMap.of( "msg", "fail" );
			}
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	@Override
	public Map<String, Object> restAnswerPutReplyDel(Integer reply, String flag, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restService.getRestCookieUser(request, response);

			if(userSeq > 0) {

				//본인 댓글인지 체크
				HashMap<String, Object> sourceParam = new HashMap<String, Object>();
				sourceParam.put("flag", flag);
				sourceParam.put("targetSeq", reply);
				ReplyVO replyVo = replyService.getReplyInfo(sourceParam);
				//System.out.println("replyVo memberSeq : " + replyVo.getMemberSeq());
				//System.out.println("userSeq : " + userSeq);
				if(replyVo.getMemberSeq() == userSeq) {
					HashMap<String, Object> replyParam = new HashMap<String, Object>();
					replyParam.put("flag", flag);
					replyParam.put("replySeq", reply);

					replyService.deleteReply(replyParam);

					return ImmutableMap.of( "msg", "success" );
				}
				else {
					return ImmutableMap.of( "msg", "fail" );
				}
			}
			else {
				return ImmutableMap.of( "msg", "fail" );
			}
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	@Override
	public Map<String, Object> restAnswerGetEstimate(Integer seq, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restService.getRestCookieUser(request, response);

			if(userSeq == 0) {
				return ImmutableMap.of( "code", "error", "returnCode", "not login");
			}
			else {

				HashMap<String, Object> eParam = new HashMap<String, Object>();
				eParam.put("gubun", "A");
				eParam.put("contentSeq", seq);
				eParam.put("userSeq", userSeq);

				int myEstimateNo = restDAO.getRestUserExtimate(eParam);
				System.out.println("myEstimateNo : " + myEstimateNo);
				final Map<String, Object> result = ImmutableMap.<String, Object> builder()
						.put( "code", "success" )
						.put( "myEstimateNo", myEstimateNo ).build();

				return result;
			}
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	@Override
	public Map<String, Object> restAnswerGetExtraUsers(Integer contentsSeq, String contentsType, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restService.getRestCookieUser(request, response);

			if(userSeq == 0) {
				return ImmutableMap.of( "code", "error", "returnCode", "not login");
			}
			else {
				HashMap<String ,Object> param = new HashMap<String, Object>();
				param.put("contentsSeq", contentsSeq);
				param.put("cType", contentsType);

				//System.out.println("contentsSeq : " + param.get("contentsSeq"));
				//System.out.println("cType : " + param.get("cType"));

				List<AlmoneyVO> extraAlmoneyList = commonService.getExtraAlmoneyList(param);
				//System.out.println("extraAlmoneyList.size() : " + extraAlmoneyList.size());

				final Map<String, Object> result = ImmutableMap.<String, Object> builder()
						.put( "code", "success" )
						.put( "value", "array" )
						.put( "ExtraAlmoneyList", extraAlmoneyList ).build();

				return result;
			}
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	@Override
	public Map<String, Object> restAnswerGetAdsView(Integer answer, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restService.getRestCookieUser(request, response);
			AnswerVO answerVo = restDAO.getRestAnswerMemberInfo(answer);
			int question = Integer.parseInt(answerVo.getQuestionSeq().toString());
			int questionMemberNo = restDAO.getRestQuestionMemberSeq(question);
			int answerMemberNo = answerVo.getMemberSeq();

			if(userSeq > 0) {
				HashMap<String, Object> vParam = new HashMap<String, Object>();
				vParam.put("answerSeq", answer);
				vParam.put("userSeq", userSeq);
				int almoneyCnt = restDAO.getAnswerLogViewInfo(vParam);

				// 질문작성자, 답변작성자는 광고 열람 유무 제외
				boolean viewFlag = true; // 광고를 열람한 상태
				String[] seqArr = {String.valueOf(questionMemberNo), String.valueOf(answerMemberNo)};
				if (almoneyCnt <= 0 && CommonUtil.useArraysBinarySearch(seqArr, String.valueOf(userSeq)) == false) {
					viewFlag = false; // 광고를 열람하지 않은 상태
				}

				final Map<String, Object> result = ImmutableMap.<String, Object> builder()
						.put( "code", "success" )
						.put( "viewFlag", viewFlag).build();

				return result;
			}
			else {
				final Map<String, Object> result = ImmutableMap.<String, Object> builder()
						.put( "code", "success" )
						.put( "viewFlag",  true).build();

				return result;
			}
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	@Override
	public Map<String, Object> restAdsGet() {
		try {
			String format = "yyyy-MM-dd";
			Calendar today = Calendar.getInstance();
			SimpleDateFormat type = new SimpleDateFormat(format);
			String todayDate = type.format(today.getTime());

			final Map<String, Object> adPparam = ImmutableMap.<String, Object> builder()
					.put( "startDt", todayDate )
					.put( "endDt", todayDate)
					.put( "section1", "").build();
			AdVO answerAd = restDAO.getRestAnswerAd(adPparam);

			int adSeq = answerAd.getSeq();
			String adFile = answerAd.getAdFile();
			String adFileExt = answerAd.getAdFileExt();
			String adUrl = !answerAd.getUrl().equals("") ? answerAd.getUrl() : "";
			int flagAd = answerAd.getFlagAd();

			final Map<String, Object> adData = ImmutableMap.<String, Object> builder()
					.put( "adSeq", adSeq )
					.put( "adFile", adFile)
					.put( "adFileExt", adFileExt)
					.put( "adUrl", adUrl)
					.put( "flagAd", flagAd)
					.build();


			return adData;

		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}
	@Override
	public Map<String, Object> restAnswerPutAdsView(Integer answer, Integer ads, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restService.getRestCookieUser(request, response);

			AnswerVO answerVo = restDAO.getRestAnswerMemberInfo(answer);
			int question = Integer.parseInt(answerVo.getQuestionSeq().toString());
			int questionMemberNo = restDAO.getRestQuestionMemberSeq(question);
			int answerMemberNo = answerVo.getMemberSeq();
			int retCode = 0;

			boolean viewFlag = true; // 광고를 열람한 상태

			if(userSeq > 0) {
				HashMap<String, Object> vParam = new HashMap<String, Object>();
				vParam.put("answerSeq", answer);
				vParam.put("userSeq", userSeq);
				int almoneyCnt = restDAO.getAnswerLogViewInfo(vParam);

				// 질문작성자, 답변작성자는 광고 열람 유무 제외

				String[] seqArr = {String.valueOf(questionMemberNo), String.valueOf(answerMemberNo)};
				if (almoneyCnt <= 0 && CommonUtil.useArraysBinarySearch(seqArr, String.valueOf(userSeq)) == false) {
					viewFlag = false; // 광고를 열람하지 않은 상태
				}

				if(viewFlag == false) {
					// 카운트 증가
					HashMap<String ,Object> aParam = new HashMap<String, Object>();
					aParam.put("aSeq", answer);
					aParam.put("userSeq", userSeq);
					aParam.put("adSeq", ads);

					HashMap<String, Object> processInfo = commonService.setAlmoneyProcess(aParam);

					if(processInfo != null) {
						retCode = Integer.parseInt(processInfo.get("result_code").toString());
						viewFlag = true;
					}
				}
			}

			HashMap<String ,Object> param2 = new HashMap<String, Object>();
			param2.put("answerSeq", answer);
			param2.put("ipAddr", request.getRemoteAddr());
			commonService.setIpLog(param2);

			final Map<String, Object> result = ImmutableMap.<String, Object> builder()
					.put( "code", "success" )
					.put( "viewFlag", viewFlag)
					.put( "retCode", retCode)
					.build();


			return result;
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	@Override
	public Map<String, Object> restAnswerPutBestSet(Integer question, String bestRank, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restService.getRestCookieUser(request, response);
			final int userLv = restService.getRestCookieUserLv(request, response);

			if(userSeq == 0) {
				return ImmutableMap.of( "code", "error", "error", "로그인 후에 다시 글을 등록하여 주십시오!" );
			}

			if(userLv != 99) {
				return ImmutableMap.of( "code", "error", "error", "관리자만 수정이 가능합니다!" );
			}

			if(bestRank == null) {
				bestRank = "0";
			}
			int nBestRank = Integer.parseInt(bestRank);

			if(nBestRank < 1) nBestRank = 1;

			String bestNumber = questionService.getBestNumberBySeq(question);

			if(bestNumber == null || bestNumber.equals("0")) {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("bestRank", nBestRank);
				param.put("questionSeq", question);

				questionService.setBestChoiceMulti(param);

				final Map<String, Object> result = ImmutableMap.<String, Object> builder()
						.put( "code", "success" )
						.build();

				return result;
			}
			else {
				final Map<String, Object> result = ImmutableMap.<String, Object> builder()
						.put( "code", "success" )
						.build();

				return result;
			}
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	@Override
	public Map<String, Object> restAnswerPutBestCancel(Integer question, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restService.getRestCookieUser(request, response);
			final int userLv = restService.getRestCookieUserLv(request, response);

			if(userSeq == 0) {
				return ImmutableMap.of( "code", "error", "error", "로그인 후에 다시 글을 등록하여 주십시오!" );
			}

			if(userLv != 99) {
				return ImmutableMap.of( "code", "error", "error", "관리자만 수정이 가능합니다!" );
			}

			questionService.setBestChoiceZero(question);

			final Map<String, Object> result = ImmutableMap.<String, Object> builder()
					.put( "code", "success" )
					.build();

			return result;
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	@Override
	public Map<String, Object> restAnswerGetAnsweredCheck(Integer question, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restService.getRestCookieUser(request, response);

			if(userSeq == 0) {
				return ImmutableMap.of( "code", "error", "error", "로그인 후에 다시 글을 등록하여 주십시오!" );
			}
			else {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("seq", question);
				param.put("userSeq", userSeq);

				int answeredCheck = restDAO.getRestAnsweredCheck(param);

				final boolean check = answeredCheck > 0 ? false : true;

				final Map<String, Object> result = ImmutableMap.<String, Object> builder()
						.put( "check", check )
						.build();

				return result;
			}
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	@Override
	public Map<String, Object> restAnswerGetChoicedCheck(Integer answer, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restService.getRestCookieUser(request, response);

			if(userSeq == 0) {
				return ImmutableMap.of( "code", "error", "error", "로그인 후에 다시 글을 등록하여 주십시오!" );
			}
			else {
				AnswerVO answerVo = restDAO.getRestAnswerMemberInfo(answer);
				int question = Integer.parseInt(answerVo.getQuestionSeq().toString());
				int questionMemberNo = restDAO.getRestQuestionMemberSeq(question);

				if(userSeq == questionMemberNo) {
					// 채택 가능
					// 이미 채택 한것인지 체크
					final boolean check = answerVo.getFlagChoice().equals("N") ? true : false;

					final Map<String, Object> result = ImmutableMap.<String, Object> builder()
							.put( "check", check )
							.build();

					return result;
				}
				else {
					// 채택 불가능
					return ImmutableMap.of( "code", "error", "error", "질문자만 채택 가능합니다." );
				}
			}
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	private String subTranslate(int contentSeq, String contentType, String targetLang, String bHour, int ai_seq) throws Exception {
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

	private Map<String, Object> transProcess(int contentSeq, String contentType, HttpServletRequest request, HttpServletResponse response, Locale locale) {

		String trnChk = "N";

		try {

			final int userSeq = restService.getRestCookieUser(request, response);

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
	public Map<String, Object> restQuestionGetTransfer(Integer question, HttpServletRequest request, HttpServletResponse response, Locale locale) {
		try {
			final String contentType = "Q";

			Map<String, Object> transResult = transProcess(question, contentType, request, response, locale);

			return transResult;
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	@Override
	public Map<String, Object> restAnswerGetTransfer(Integer answer, HttpServletRequest request, HttpServletResponse response, Locale locale) {
		try {
			final String contentType = "A";

			Map<String, Object> transResult = transProcess(answer, contentType, request, response, locale);

			return transResult;
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	public Map<String, Object> questionVote(String act, int contentSeq, String contentType,
												HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restService.getRestCookieUser(request, response);

			if(userSeq > 0 && contentSeq > 0) {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("contentSeq", contentSeq);
				param.put("contentType", contentType);
				param.put("userSeq", userSeq);
				int voteCount = commonService.getQuestionVoteCount(param);

				HashMap<String, Object> vote = null;
				int good = 0;
				int bad = 0;
				if(voteCount > 0) {
					vote = commonService.getQuestionVote(param);
					good = Integer.parseInt( String.valueOf( vote.get("good") ) );
					bad = Integer.parseInt( String.valueOf( vote.get("bad") ) );
				}


				HashMap<String, Object> vParam = new HashMap<String, Object>();
				vParam.put("userSeq", userSeq);
				vParam.put("contentSeq", contentSeq);
				vParam.put("contentType", contentType);

				if(voteCount > 0) {
					if(act.equals("G")) {
						if(bad > 0) {
							vParam.put("bad", (bad - 1));
						}
						else {
							vParam.put("bad", bad);
						}
						if(good == 0) {
							vParam.put("good", (good + 1));
						}
						else {
							vParam.put("good", good);
						}
					}
					else {
						if(good > 0) {
							vParam.put("good", (good - 1));
						}
						else {
							vParam.put("good", good);
						}
						if(bad == 0) {
							vParam.put("bad", (bad + 1));
						}
						else {
							vParam.put("bad", bad);
						}
					}

					commonService.setQuestionVote(vParam);
				}
				else {
					if(act.equals("G")) {
						vParam.put("good", 1);
						vParam.put("bad", 0);
					}
					else {
						vParam.put("good", 0);
						vParam.put("bad", 1);
					}
					commonService.addQuestionVote(vParam);
				}

				HashMap<String, Object> voteItem = commonService.getQuestionVoteSum(vParam);

				final Map<String, Object> result = ImmutableMap.<String, Object> builder()
						.put( "code", "success" )
						.put( "qtGood", voteItem.get("good") )
						.put( "qtBad", voteItem.get("bad") )
						.build();

				return result;

			} else {
				return ImmutableMap.of( "code", "error", "error", "잘못된 접근입니다." );
			}
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

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
			return questionVote(act, transfer, contentType, request, response);
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	@Override
	public Map<String, Object> restQuestionGetTransferReply(Integer rseq, HttpServletRequest request, HttpServletResponse response, Locale locale) {
		try {
			final String contentType = "R";

			Map<String, Object> transResult = transProcess(rseq, contentType, request, response, locale);

			return transResult;
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	@Override
	public Map<String, Object> restAnswerGetTransferReply(Integer rseq, HttpServletRequest request, HttpServletResponse response, Locale locale) {
		try {
			final String contentType = "S";

			Map<String, Object> transResult = transProcess(rseq, contentType, request, response, locale);

			return transResult;
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}
}
