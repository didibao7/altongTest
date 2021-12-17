package com.altong.web.controller;

import java.io.File;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.altong.web.logic.ChoiceVO;
import com.altong.web.logic.LogEstimateVO;
import com.altong.web.logic.V2RankVO;
import com.altong.web.logic.ad.AdVO;
import com.altong.web.logic.almoney.AlmoneyVO;
import com.altong.web.logic.answer.AnswerVO;
import com.altong.web.logic.common.CommonLogQAVO;
import com.altong.web.logic.common.TranslateVO;
import com.altong.web.logic.config.ConfigVO;
import com.altong.web.logic.files.FileVO;
import com.altong.web.logic.member.MemberVO;
import com.altong.web.logic.question.QuestionVO;
import com.altong.web.logic.reply.ReplyVO;
import com.altong.web.logic.section.SectionVO;
import com.altong.web.logic.util.CodeUtil;
import com.altong.web.logic.util.CommonUtil;
import com.altong.web.logic.util.CookieBox;
import com.altong.web.logic.util.UtilFile;
import com.altong.web.service.CookieLoginService;
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
import com.google.gson.Gson;

import jasp.vbs.vb;

@Controller
@RequestMapping("answer/*")
public class AnswerController {
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
	CookieLoginService cookieLoginService;

	@Autowired
	CookieLocaleResolver localResolver;

	@Autowired
	MessageSource messageSource;

	class MemberMapper implements RowMapper<MemberVO> {
        @Override
		public MemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        	MemberVO Member = new MemberVO();
        	Member.setSeq(rs.getInt("seq"));
        	Member.setRankA(rs.getInt("rankA"));
        	Member.setRankQ(rs.getInt("rankQ"));
        	Member.setPhoto(rs.getString("photo"));
        	Member.setNickName(rs.getString("nickName"));
        	Member.setSumA(rs.getInt("sumA"));
        	Member.setCountA(rs.getInt("countA"));

            return Member;
        }
    }

	class V2RankMapper implements RowMapper<V2RankVO> {
        @Override
		public V2RankVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        	V2RankVO v2Rank = new V2RankVO();
        	v2Rank.setRownum(rs.getInt("rownum"));
        	v2Rank.setSeq(rs.getInt("Seq"));
        	v2Rank.setQuestionSeq(rs.getInt("QuestionSeq"));
			v2Rank.setAnswer(rs.getString("Answer"));
			v2Rank.setReadCount(rs.getInt("ReadCount"));
			v2Rank.setReadAlmoney(rs.getInt("ReadAlmoney"));
			v2Rank.setPointCount(rs.getInt("PointCount"));
			v2Rank.setSection1(rs.getString("Section1"));

            return v2Rank;
        }
    }

	@RequestMapping("answer/rankMember")
	public ModelAndView rankMember(HttpServletRequest request
			, HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView();

		String flagOption = "";
		String curPageName = "/answer/rankMember";
		String req_trec = "";
		String src_Sort = "";
		String section1 = "";
		int n_curpage = 1;
		int n_pagesize = 30;
		int n_totalpage = 0;
		int n_pagescnt = 5;
		String flag2 = "";
		int st_num = 0;
		int en_num = 0;
		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(request.getParameter("FlagOption") != null) {
			flagOption = request.getParameter("FlagOption");
		}

		if(request.getParameter("trec") != null) {
			req_trec = request.getParameter("trec");
		}

		if(request.getParameter("src_Sort") != null) {
			src_Sort = request.getParameter("src_Sort");
		}

		if(request.getParameter("Section1") != null) {
			section1 = request.getParameter("Section1");
		}

		if(request.getParameter("pg") != null) {
			n_curpage = Integer.parseInt( request.getParameter("pg") );
		}
		if(request.getParameter("pg") == "") {
			n_curpage = 1;
		}
		if(request.getParameter("Section1") == "") {
			section1 = "0";
		}
		//System.out.println("flagOption : " + flagOption);
		if(flagOption.equals("Money")) {
			flag2 = "SumA";
		}
		else {
			flag2 = "CountA";
		}
		//System.out.println("flag2 : " + flag2);

		st_num = (n_pagesize) * (n_curpage - 1) + 1;
		en_num = (n_pagesize) * (n_curpage);

		// totalMember
		int totalMember = memberService.getTotalMember();

		n_totalpage =  -vb.CInt( -(totalMember / n_pagesize) );


		// 회원 랭킹 정보 리스트
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("flag2", flag2);
		param.put("st_num", st_num);
		param.put("en_num", en_num);

		List<MemberVO> userRanking = memberService.getRankMember(param);


		// 알머니 로그정보
		int sumChoice = memberService.getAnswerChoiceSum();
		String sumChoiceStr = String.format("%,d", sumChoice);

		// 수익 로그 정보
		int sumViewA = memberService.getAnswerSumViewA();
		String sumViewAStr = String.format("%,d", sumViewA);

		if(curPageName.equals("RankMember")) {
			curPageName  = "QuestionList";
		}


		String url = curPageName + "?FlagOption=" + flagOption + "&trec=" + req_trec + "&src_Sort=" + src_Sort + "&Section1=" + section1;
		String paging_Tag = CommonUtil.get_Paging_Tag_New_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);


		mav.addObject("FlagOption", flagOption);
		mav.addObject("curPageName", "RankMember");
		mav.addObject("req_trec", req_trec);
		mav.addObject("src_Sort", src_Sort);
		mav.addObject("section1", section1);
		mav.addObject("n_curpage", n_curpage);
		mav.addObject("n_pagesize", n_pagesize);
		mav.addObject("n_totalpage", n_totalpage);
		mav.addObject("n_pagescnt", n_pagescnt);
		mav.addObject("flag2", flag2);
		mav.addObject("st_num", st_num);
		mav.addObject("en_num", en_num);
		mav.addObject("totalMember", totalMember);
		mav.addObject("SumChoice", sumChoiceStr);
		mav.addObject("SumViewA", sumViewAStr);
		mav.addObject("paging_Tag", paging_Tag);

		mav.addObject("userRanking", userRanking);
		mav.addObject("userSeq", userSeq);

		return mav;
	}

	@RequestMapping("answer/rankAnswer")
	public ModelAndView rankAnswer(HttpServletRequest request
			, HttpServletResponse response, Locale locale) throws Exception {

		ModelAndView mav = new ModelAndView();

		String flagOption = "";
		String curPageName = "/answer/rankAnswer";
		String req_trec = "";
		int n_trec = 0;
		int n_rsCnt = 0;
		//String s_tmp = "";
		String src_Sort = "";
		String section1 = "";
		int n_curpage = 1;
		int n_pagesize = 25;
		int n_totalpage = 0;
		int n_pagescnt = 5;
		String flag2 = "";
		int st_num = 0;
		int en_num = 0;


		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);


		HashMap<String, Object> localeItem = CommonUtil.getLocale(locale);
		String lang = String.valueOf(localeItem.get("lang"));
		String sourceLang = lang;
		if(userSeq > 0) {
			MemberVO mVo = memberService.getAlmoneyMyJoin(userSeq);
			sourceLang = mVo.getLang();
		}

		String targetLang = request.getParameter("targetLang");
		if(targetLang == null) { targetLang = ""; }
		String langString = CommonUtil.getLanguageString(sourceLang, targetLang);
		String langSel = CommonUtil.getLanguageSelString(sourceLang, targetLang);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("n_total", 50);

		int totalCnt = 50;
		//totalCnt = v2rankService.getAnswerRankCount(param);

		n_trec = totalCnt;

		if(request.getParameter("FlagOption") != null) {
			flagOption = request.getParameter("FlagOption");
		}

		if(request.getParameter("trec") != null) {
			req_trec = request.getParameter("trec");
		}

		if(request.getParameter("src_Sort") != null) {
			src_Sort = request.getParameter("src_Sort");
		}

		if(request.getParameter("Section1") != null) {
			section1 = request.getParameter("Section1");
		}

		if(request.getParameter("pg") != null) {
			n_curpage = Integer.parseInt( request.getParameter("pg") );
		}
		if(request.getParameter("pg") == "") {
			n_curpage = 1;
		}
		if(request.getParameter("Section1") == "") {
			section1 = "0";
		}


		n_totalpage = n_trec / n_pagesize;

		if((n_trec % n_pagesize) > 0) {
			n_totalpage = n_totalpage + 1;
		}


		if(request.getParameter("src_Sort") != null) {
			req_trec = request.getParameter("src_Sort");
		}

		if(request.getParameter("Section1") != null) {
			section1 = request.getParameter("Section1");
		}

		if(request.getParameter("Section1") == "") {
			section1 = "0";
		}

		if(flagOption == "Money") {
			flag2 = "SumA";
		}
		else {
			flag2 = "CountA";
		}

		st_num = (n_pagesize) * (n_curpage - 1) + 1;
		en_num = (n_pagesize) * (n_curpage);

		param.put("lang", targetLang);
		param.put("st_num", st_num);
		param.put("en_num", en_num);

		List<V2RankVO> answerRankA2 = v2rankService.getAnswerRank(param);

		// 알머니 로그정보
		int sumChoice = memberService.getEarnAnsChoiceSum();
		String sumChoiceStr = String.format("%,d", sumChoice);

		// 수익 로그 정보
		int sumViewA = memberService.getAnswerSumViewA();
		String sumViewAStr = String.format("%,d", sumViewA);

		if(curPageName.equals("RankMember")) {
			curPageName  = "QuestionList";
		}

		String url = curPageName + "?FlagOption=" + flagOption + "&trec=" + req_trec + "&src_Sort=" + src_Sort + "&Section1=" + section1 + "&targetLang=" + targetLang;
		String paging_Tag = CommonUtil.get_Paging_Tag_New_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);


		mav.addObject("langString", langString);
		mav.addObject("langSel", langSel);
		mav.addObject("targetLang", targetLang);
		mav.addObject("FlagOption", flagOption);
		mav.addObject("curPageName", "RankAnswer");
		mav.addObject("req_trec", req_trec);
		mav.addObject("n_rsCnt", n_rsCnt);
		mav.addObject("src_Sort", src_Sort);
		mav.addObject("section1", section1);
		mav.addObject("n_curpage", n_curpage);
		mav.addObject("n_pagesize", n_pagesize);
		mav.addObject("n_totalpage", n_totalpage);
		mav.addObject("n_pagescnt", n_pagescnt);
		mav.addObject("st_num", st_num);
		mav.addObject("en_num", en_num);

		mav.addObject("SumChoice", sumChoiceStr);
		mav.addObject("SumViewA", sumViewAStr);
		mav.addObject("paging_Tag", paging_Tag);

		mav.addObject("userLv", userLv);
		mav.addObject("userSeq", userSeq);

		mav.addObject("answerRankA2", answerRankA2);
		mav.addObject("sourceLang", sourceLang);

		return mav;
	}

	@RequestMapping("answer/answerList")
	public ModelAndView answerList(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		ModelAndView mav = new ModelAndView();

		final int uSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);
		final String sessExpire = !cookieLoginService.getCookieSessExpire(request, response).equals("") ? cookieLoginService.getCookieSessExpire(request, response) : "1";

		int maxSiren = 3; //블라인드 처리 할 신고 횟수

		int questionSeq = request.getParameter("Seq") != null ? Integer.parseInt( request.getParameter("Seq") ) : 0;
		String curPageName = request.getParameter("CurPageName");
		String section1 = request.getParameter("Section1");
		String src_Sort = request.getParameter("src_Sort");
		String src_OrderBy = request.getParameter("src_OrderBy");
		String src_Text = request.getParameter("src_Text");
		String developSeq  = request.getParameter("devSeq");
		String answerSeq = request.getParameter("AnswerSeq");
		String aSeq = request.getParameter("ASeq");
		String QRSeq = request.getParameter("QRSeq");
		Integer nQrSeq = 0;
		String arSeq = request.getParameter("ARSeq");
		Integer nArSeq = 0;
		Integer nAseq = 0;
		String cSeq = request.getParameter("CSeq");
		Integer nCseq = 0;
		String recmd = request.getParameter("recmd");
		String ticketQueChk = "";
		String ticketAnsChk = "";
		String ticketReplChk = "";
		int nQtSeq = request.getParameter("qtSeq") != null ? Integer.parseInt( request.getParameter("qtSeq") ) : 0;

		HashMap<String, Object> localeItem = CommonUtil.getLocale(locale);
		String lang = String.valueOf(localeItem.get("lang"));
		String targetLang = lang;
		if(uSeq > 0) {
			MemberVO mVo = memberService.getAlmoneyMyJoin(uSeq);
			targetLang = mVo.getLang();
		}

		//질문글의 기계번역 카운터
		HashMap<String, Object> tqParam = new HashMap<String, Object>();
		tqParam.put("trnType", 1);
		tqParam.put("replyType", "Q");
		tqParam.put("orgSeq", questionSeq);
		tqParam.put("lang", targetLang);
		int machineCnt = commonService.getMachineTrnsCount(tqParam);

		//질문글의 기계번역 한건 가져오기
		//getTrnslateBySeq
		TranslateVO queTrn = commonService.getTrnslateBySeq(tqParam);
		int queMachineTrnSeq = 0;
		if(machineCnt> 0) {
			queMachineTrnSeq = queTrn.getSeq();
		}

		if(request.getParameter("ticketQueChk") != null) {
			ticketQueChk = request.getParameter("ticketQueChk");

			if(ticketQueChk.equals("")) {
				ticketQueChk = "N";
			}
		}
		if(request.getParameter("ticketAnsChk") != null) {
			ticketAnsChk = request.getParameter("ticketAnsChk");

			if(ticketAnsChk.equals("")) {
				ticketAnsChk = "N";
			}
		}
		if(request.getParameter("ticketReplChk") != null) {
			ticketReplChk = request.getParameter("ticketReplChk");

			if(ticketReplChk.equals("")) {
				ticketReplChk = "N";
			}
		}

		// 알뱅크와 어드민알뱅크의 링크를 AnswerSeq 에서 ASeq로 수정했으나 아직 본섭에는 적용하지 않았다. 이 부분을 본섭에 적용하게되면 이 부분은 삭제해도 된다.
		if(answerSeq != null && answerSeq != "") {
			aSeq = answerSeq;
		}
		if(curPageName == null) {
			curPageName = "";
		}
		if(section1 == null) {
			section1 = "";
		}
		if(src_Sort == null) {
			src_Sort = "";
		}
		if(src_OrderBy == null) {
			src_OrderBy = "";
		}

		if(QRSeq != null && QRSeq != "") {
			nQrSeq = Integer.parseInt(QRSeq);
			questionSeq = replyService.getQuestionSeqBySeq(nQrSeq);
		}
		else if(arSeq != null && arSeq != "") {
			nArSeq = Integer.parseInt(arSeq);
			ReplyVO reply = replyService.getAnswerSeqBySeq(nArSeq);

			nAseq = reply.getAnswerSeq().intValue();
			questionSeq = reply.getQuestionSeq().intValue();
		}
		else if(aSeq != null && aSeq != "") {
			nAseq = Integer.parseInt(aSeq);
			questionSeq = answerService.getAnserForQuestionSeqBySeq(nAseq);
		}

		if(cSeq != null && cSeq != "") {
			nCseq = Integer.parseInt(cSeq);
		}


		if((recmd != null && recmd != "") && uSeq > 0) {
			//setcookie('Code', $_GET["recmd"], time() + (60 * 60 * 24), '/');

			Cookie cod = new Cookie("Code", recmd );
			cod.setVersion( 0 );
			cod.setMaxAge( Integer.parseInt(sessExpire) * 24 * 60 * 60 );
			cod.setPath("/");

			response.addCookie(cod);
		}

		//int almoneyMoveQuestion = configService.getAlmoneyMoveQuestion();

		ConfigVO config = configService.getConfigList();
		int viewMoneySum = config.getViewMoneySum();
		int almoneyMoveQuestion = config.getAlmoneyMoveQuestion();

		if(uSeq > 0) {

			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("userSeq", uSeq);
			param.put("questionSeq", questionSeq);
			commonService.setLogView(param);
		}
		else {
			commonService.setQuestionReadCount(questionSeq);
		}



		HashMap<String, Object> qtParam = new HashMap<String, Object>();
		qtParam.put("seq", nQtSeq); 		// 번역 고유 아이디


		CommonLogQAVO mem = memberService.getQuestionByQuestionSeq(questionSeq);

		int MemberSeq = mem.getMemberSeq();
		String Title = mem.getTitle();
		String Contents = mem.getContents();
		int qtSeq = 0;
		int qtUserSeq = 0;
		String qtNickName = "";
		String qtFlagNickName = "";
		int qtSirenN = 0;
		int qtUserLv = 0;
		BigDecimal qtExtraAlmoney = new BigDecimal("0.0");
		int qtCount = 0;
		int qtTrnType = 0;
		String qtLang = "";

		if(nQtSeq > 0) {
			//번역문 카운터 증가
			commonService.setTranslateCount(nQtSeq);

			TranslateVO qtVo = commonService.getTrnslateViewBySeq(qtParam);
			Title = qtVo.getTitle();
			Contents = qtVo.getComment();
			qtFlagNickName = qtVo.getFlagNickName();
			qtCount = qtVo.getReadCount();

			// 답변의 번역 기능 참고하여
			//qtSeq
			qtSeq = qtVo.getSeq();
			//qtUserSeq
			qtUserSeq = qtVo.getUserSeq();

			qtTrnType = qtVo.getTrnType();
			qtLang = qtVo.getLang();

			HashMap<String, Object> qvParam = new HashMap<String, Object>();
			qvParam.put("userSeq", qtUserSeq);
			qvParam.put("contentsType", "Q");
			qvParam.put("contentsSeq", qtSeq);

			V2RankVO qmVo = commonService.getQtUserInfoBySeq(qvParam);

			qtSirenN = Integer.parseInt(qmVo.getSirenN());
			qtUserLv = Integer.parseInt(qmVo.getLv());
			qtNickName = qmVo.getNickName();
			qtExtraAlmoney = qmVo.getExtraAlmoney();
		}

		BigDecimal Almoney = mem.getThankAlmoney();
		String FlagNickNameQ = mem.getFlagNickName();

		String DateReg = mem.getConDate();
		String nickName = (mem.getNickName() == "" || mem.getNickName() == null) ? CommonUtil.getLangMsg(request, "msg_0237") : mem.getNickName();
		String nickNameCheat = (mem.getNickName() == "" || mem.getNickName() == null) ? "기부천사" : mem.getNickName();
		int Lv = mem.getLv();
		String Photo = ( mem.getPhoto() == "" ||  mem.getPhoto() == null) ? "img_thum_base0.jpg" : mem.getPhoto();
		String Intro = mem.getIntro();
		String queNation = mem.getNation();
		String queLang = mem.getLang();
		BigDecimal QuestionMoney = mem.getQ_Almoney();
		BigDecimal AnswerMoney = mem.getA_Almoney();
		BigDecimal ExtraAlmoney = mem.getExtraAlmoney();
		BigDecimal EarnTotal = mem.getEarnTotal();

		String msg1 = messageSource.getMessage("msg_1018", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0331", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		if(userLv == 99) {
			if(mem.getFlagNickName() == "N") {
				nickName += msg1;	//msg1 활용
			}
			FlagNickNameQ = "Y";
		}

		int countC = 0;
		int countN = 0;
		int countQuestion = 0;
		int rateChoice = 0;

		if(FlagNickNameQ.equals("N")) {
			if(uSeq > 0) {
				if( mem.getMemberSeq() == uSeq )
					nickName += msg1;	//msg1 활용
				else
					nickName = msg2;	//msg2 활용
			}
			Photo = "img_thum_base0.jpg";
			Lv = 0;
			Intro = "";
			QuestionMoney = new BigDecimal(0.0);
			AnswerMoney = new BigDecimal(0.0);
			countC = 0;
		}
		else {
			HashMap<String, Object> res = answerService.getCountCAndCountN(mem.getMemberSeq());

			countC = Integer.parseInt(res.get("CountC").toString());
			countN = Integer.parseInt(res.get("CountN").toString());

			countQuestion = countC + countN;

			rateChoice = countC == 0 ? 0 : (int)Math.round((countC / (double)countQuestion) * 100 );

			//rateChoice = Math.round((rateChoice * 10)/10); // 소수점 첫째리 반올림 하기 위한 계산
		}

		NumberFormat nf = NumberFormat.getCurrencyInstance();
		int readCount = mem.getReadCount();

		String RecommendSeq = "";
		String RecommendCode = "";
		if(uSeq > 0) {
			RecommendSeq = String.valueOf(uSeq);
			RecommendCode = RecommendSeq.substring(0, 4) + "-" + RecommendSeq.substring(RecommendSeq.length()-4, RecommendSeq.length());
		}


		String Status = commonService.getEventStatus(questionSeq);
		if(Status == null) { Status = "0";}

		String fileName = fileService.getFileNameBySeq(questionSeq);

		int SumReply = replyService.getReplyQuestionCntBySeq(questionSeq);
		int AnswerCnt = answerService.getAnswerCnt(questionSeq);

		String BestNumber = questionService.getBestNumberBySeq(questionSeq);

		List<ReplyVO> replyList = replyService.getReplyQuestionListBySeq(questionSeq);

		HashMap<String, Object> param2 = new HashMap<String, Object>();
		param2.put("questionSeq", questionSeq);
		param2.put("userSeq", uSeq);
		//System.out.println("MemberSeq : " + MemberSeq);

		int QuestionReportCnt = reportService.getQuestionReportCnt(param2);

		int ai_seq = commonService.getUserSeqForTrnslateByNick("AI_T");

		//상위 세개 목록을 구하고 번역 실행
		/*
		HashMap<String, Object> paramT = new HashMap<String, Object>();
		paramT.put("questionSeq", questionSeq);
		paramT.put("userSeq", MemberSeq);
		paramT.put("lang", targetLang);
		List<V2RankVO> answerListTop3 = v2rankService.getPointRankByUserSeqTop3(param2);

		SimpleDateFormat dType = new SimpleDateFormat("yyyy-MM-dd aa hh:mm:ss", Locale.KOREA);
		Calendar beginDay = Calendar.getInstance();
		String bHour = dType.format(beginDay.getTime());
		//System.out.println("bHour : " + bHour);
		for(int n = 0; n < answerListTop3.size(); n++) {
			//Seq : AnswerSeq
			//QuestionSeq : 질문 번호
			//Answer :답변내용
			//lang

			int answerSeqT = answerListTop3.get(n).getSeq();
			String langOrg = answerListTop3.get(n).getLang();
			String orgComment = answerListTop3.get(n).getAnswer(); // html 제거 필요
			//System.out.println("orgComment : " + orgComment);

			HashMap<String, Object> mtParam = new HashMap<String, Object>();
			mtParam.put("trnType", 1); 		// 1:기계번역, 2:사람번역
			mtParam.put("replyType", "A"); 	// Q:질문, A:답변, R:댓글
			mtParam.put("orgSeq", answerSeqT); 	// 질문/답변/댓글 의 Seq
			mtParam.put("lang", targetLang);// 번역 언어

			int machineTrnCnt = commonService.getTrnslateCntBySeq(mtParam);
			//System.out.println("machineTrnCnt : " + machineTrnCnt);
			String detectLang = CommonUtil.detectLanguage(orgComment);
			String transComment = "";

			if(!detectLang.equals(targetLang)) { // 원문과 다른 언어 선택인 경우 진행
				transComment = CommonUtil.strToTranslate(detectLang, targetLang, orgComment, "N");
				//System.out.println("detectLang : " + detectLang);
				//System.out.println("targetLang : " + targetLang);
				//System.out.println("orgComment : " + orgComment);
				//System.out.println("transComment : " + transComment);
				if(!langOrg.equals(detectLang)) { langOrg = detectLang; }

				if(langOrg.contains("-")) {
					String[] langOrgArr = langOrg.split("-");
					langOrg = langOrgArr[0];
				}

				if(targetLang.contains("-")) {
					String[] targetLangArr = targetLang.split("-");
					targetLang = targetLangArr[0];
				}

				HashMap<String, Object> tParam = new HashMap<String, Object>();
				tParam.put("trnType", 1);
				tParam.put("replyType", "A");
				tParam.put("orgSeq", answerSeqT);
				tParam.put("userSeq", ai_seq);
				tParam.put("flagNickName", "Y");
				tParam.put("langOrg", langOrg);
				tParam.put("lang", targetLang);
				tParam.put("title", "");
				tParam.put("comment", transComment);
				tParam.put("createDt", bHour);

				if(machineTrnCnt == 0) {
					//최초 번역
					commonService.addTranslate(tParam);
				}
				else {
					//6개월 경과 일 경우
					TranslateVO machineTrn = commonService.getTrnslateBySeq(mtParam);
					//System.out.println("getCreateDt : " + machineTrn.getCreateDt());
					boolean machineTrnChk6month = CommonUtil.checkMachineTranslate(machineTrn.getCreateDt());
					//System.out.println("machineTrnChk6month : " + machineTrnChk6month);
					if(machineTrnChk6month == true) {
						commonService.addTranslate(tParam);
					}
				}
			}
		}


		List<V2RankVO> answerList = v2rankService.getPointRankByUserSeq(param2);
		List<V2RankVO> newAnswerList = new ArrayList<V2RankVO>();

		for(int i = 0; i < answerList.size(); i++) {
			V2RankVO vVo = new V2RankVO();
			int answerSeqT = answerList.get(i).getSeq();
			String langOrg = answerList.get(i).getLang();
			String orgComment = answerList.get(i).getAnswer(); // html 제거 필요
			//System.out.println("orgComment : " + orgComment);

			HashMap<String, Object> mtParam = new HashMap<String, Object>();
			mtParam.put("trnType", 1); 		// 1:기계번역, 2:사람번역
			mtParam.put("replyType", "A"); 	// Q:질문, A:답변, R:댓글
			mtParam.put("orgSeq", answerSeqT); 	// 질문/답변/댓글 의 Seq
			mtParam.put("lang", targetLang);// 번역 언어

			int machineTrnCnt = commonService.getTrnslateCntBySeq(mtParam);

			if(machineTrnCnt > 0) {
				TranslateVO tVo = commonService.getTrnslateBySeq(mtParam);
				if(uSeq != tVo.getUserSeq()) {
					vVo.settSeq(tVo.getSeq());
					vVo.setAnswer(tVo.getComment());
					//System.out.println("tSeq : " + vVo.gettSeq());
				}
				else {
					vVo.settSeq(0);
					vVo.setAnswer(answerList.get(i).getAnswer());
				}
			}
			else {
				vVo.settSeq(0);
				vVo.setAnswer(answerList.get(i).getAnswer());
			}

			//공통
			vVo.setSeq(answerSeqT);
			vVo.setMemberSeq(answerList.get(i).getMemberSeq());

			vVo.setFlagNickName(answerList.get(i).getFlagNickName());
			vVo.setFlagChoice(answerList.get(i).getFlagChoice());
			vVo.setReadCount(answerList.get(i).getReadCount());
			vVo.setRegdate(answerList.get(i).getRegdate());
			vVo.setLang(answerList.get(i).getLang());
			vVo.setNation(answerList.get(i).getNation());
			vVo.setNickName(answerList.get(i).getNickName());
			vVo.setPhoto(answerList.get(i).getPhoto());
			vVo.setLv(answerList.get(i).getLv());
			vVo.setQ_Almoney(answerList.get(i).getQ_Almoney());
			vVo.setA_Almoney(answerList.get(i).getA_Almoney());
			vVo.setEarnTotal(answerList.get(i).getEarnTotal());
			vVo.setA_ChoicedCount(answerList.get(i).getA_ChoicedCount());
			vVo.setA_Count(answerList.get(i).getA_Count());
			vVo.setIntro(answerList.get(i).getIntro());
			vVo.setExtraAlmoney(answerList.get(i).getExtraAlmoney());
			vVo.setSirenN(answerList.get(i).getSirenN());

			vVo.setQuestionSeq(answerList.get(i).getQuestionSeq());
			vVo.setPointCount1(answerList.get(i).getPointCount1());
			vVo.setPointCount2(answerList.get(i).getPointCount2());
			vVo.setPointCount3(answerList.get(i).getPointCount3());
			vVo.setPointCount4(answerList.get(i).getPointCount4());
			vVo.setPointCount5(answerList.get(i).getPointCount5());
			vVo.setPointCount6(answerList.get(i).getPointCount6());
			vVo.setPointSum(answerList.get(i).getPointSum());
			vVo.setPointCountNo(answerList.get(i).getPointCountNo());
			vVo.setPointCount6_Yn(answerList.get(i).getPointCount6_Yn());
			vVo.setcNTSUMReplayanswer(answerList.get(i).getcNTSUMReplayanswer());

			newAnswerList.add(vVo);
		}
		*/
		List<V2RankVO> answerList = v2rankService.getPointRankByUserSeq(param2);

		String format = "yyyy-MM-dd";
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String todayDate = type.format(today.getTime());

		param2.put("startDt", todayDate);
		param2.put("endDt", todayDate);
		param2.put("section1", section1);

		List<AdVO> answerAd = commonService.getAnswerAd(param2);


		HashMap<String, Object> voteParam = new HashMap<String, Object>();
		voteParam.put("contentSeq", questionSeq);
		voteParam.put("contentType", "Q");
		int voteCount = commonService.getQuestionVoteCountSum(voteParam);

		HashMap<String, Object> vote = null;

		int good = 0;
		int bad = 0;
		if(voteCount > 0) {
			vote = commonService.getQuestionVoteSum(voteParam);
			good = Integer.parseInt( String.valueOf( vote.get("good") ) );
			bad = Integer.parseInt( String.valueOf( vote.get("bad") ) );
		}

		int qtGood = 0;
		int qtBad = 0;
		if(qtSeq > 0) {
			HashMap<String, Object> qtVoteParam = new HashMap<String, Object>();
			qtVoteParam.put("contentSeq", qtSeq);
			qtVoteParam.put("contentType", "T");
			int qtVoteCount = commonService.getQuestionVoteCountSum(qtVoteParam);

			HashMap<String, Object> qtVote = null;

			if(qtVoteCount > 0) {
				qtVote = commonService.getQuestionVoteSum(qtVoteParam);
				qtGood = Integer.parseInt( String.valueOf( qtVote.get("good") ) );
				qtBad = Integer.parseInt( String.valueOf( qtVote.get("bad") ) );
			}
		}


		//최초 좋아요/싫어요 데이터 설정
		mav.addObject("good", good);
		mav.addObject("bad", bad);

		mav.addObject("machineCnt", machineCnt);
		mav.addObject("queMachineTrnSeq", queMachineTrnSeq);
		mav.addObject("qtSeq", qtSeq);
		mav.addObject("qtUserSeq", qtUserSeq);
		mav.addObject("qtNickName", qtNickName);
		mav.addObject("qtFlagNickName", qtFlagNickName);
		mav.addObject("qtSirenN", qtSirenN);
		mav.addObject("qtUserLv", qtUserLv);
		mav.addObject("qtExtraAlmoney", qtExtraAlmoney);
		mav.addObject("qtCount", qtCount);
		mav.addObject("qtTrnType", qtTrnType);
		mav.addObject("qtLang", qtLang);

		//질문 번역글 좋아요/싫어요 데이터
		mav.addObject("qtGood", qtGood);
		mav.addObject("qtBad", qtBad);

		mav.addObject("queNation", queNation);
		mav.addObject("queLang", queLang);

		//mav.addObject("global", global); // 미사용
		mav.addObject("maxSiren", maxSiren);
		mav.addObject("mem", mem);
		mav.addObject("QuestionSeq", questionSeq);
		mav.addObject("CurPageName", curPageName);
		mav.addObject("Section1", section1);
		mav.addObject("src_Sort", src_Sort);
		mav.addObject("src_OrderBy", src_OrderBy);
		mav.addObject("src_Text", src_Text);
		mav.addObject("developSeq", developSeq);
		mav.addObject("AnswerSeq", answerSeq);
		mav.addObject("Aseq", nAseq);
		mav.addObject("ARSeq", arSeq);
		mav.addObject("QRSeq", nQrSeq);
		mav.addObject("CSeq", nCseq);
		mav.addObject("ViewMoneySum", viewMoneySum);
		mav.addObject("AlmoneyMoveQuestion", almoneyMoveQuestion);
		mav.addObject("NickName", nickName);
		mav.addObject("nickNameCheat",nickNameCheat);

		mav.addObject("FlagNickNameQ", FlagNickNameQ);
		mav.addObject("CountC", countC);
		mav.addObject("RateChoice", rateChoice);
		mav.addObject("ReadCount", readCount);
		mav.addObject("RecommendSeq", RecommendSeq);
		mav.addObject("RecommendCode", RecommendCode);
		mav.addObject("MemberSeq", MemberSeq);
		mav.addObject("Title", Title);
		mav.addObject("Contents", Contents);
		mav.addObject("Almoney", Almoney);
		mav.addObject("DateReg", DateReg);
		mav.addObject("Lv", Lv);
		mav.addObject("Photo", Photo);
		mav.addObject("Intro", Intro);
		mav.addObject("QuestionMoney", QuestionMoney);
		mav.addObject("AnswerMoney", AnswerMoney);
		mav.addObject("ExtraAlmoney", ExtraAlmoney);
		mav.addObject("EarnTotal", EarnTotal);
		mav.addObject("Status", Status);
		mav.addObject("FileName", fileName);
		mav.addObject("SumReply", SumReply);
		mav.addObject("AnswerCnt", AnswerCnt);
		mav.addObject("BestNumber", BestNumber);
		mav.addObject("QuestionReportCnt", QuestionReportCnt);
		mav.addObject("replyList", replyList);
		mav.addObject("answerList", answerList);//newAnswerList(상위 3개 자동번역 반영 리스트 변수)
		mav.addObject("adData", answerAd);

		mav.addObject("ticketQueChk", ticketQueChk);
		mav.addObject("ticketAnsChk", ticketAnsChk);
		mav.addObject("ticketReplChk", ticketReplChk);

		mav.addObject("targetLang", targetLang);


		if(uSeq > 0) {
			String act = "E";
			String format2 = "yyyy-MM-dd";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
			Calendar today2 = Calendar.getInstance();
			SimpleDateFormat type2 = new SimpleDateFormat(format2);
			String regDate = type2.format(today2.getTime());

			HashMap<String, Object> param_sell = new HashMap<String, Object>();
			param_sell.put("userSeq", uSeq);
			param_sell.put("act", act);
			param_sell.put("regDate", regDate);

			BigDecimal Balmoney = commonService.getChkUseAlmoney(param_sell);

			int almoney = 0;
			if(Balmoney.compareTo(BigDecimal.ZERO) < 0) {
				almoney = Balmoney.intValue() * (-1);
			}
			else {
				almoney = Balmoney.intValue();
			}
			int maxMoney = 0;

			if( userLv > 3) {
				maxMoney= 300000;
			}else {
				maxMoney = 30000;
			}

			mav.addObject("no_money", maxMoney-almoney);
		}

		String flagSelFAnswer = cookieLoginService.getCookieFlagSelfAnswer(request, response);
		int memberType = cookieLoginService.getCookieMemberType(request, response);
		String userNickName = cookieLoginService.getCookieUserNickName(request, response);
		BigDecimal userAlmoney = cookieLoginService.getCookieUserAlmoney(request, response);
		String adminSecu = cookieLoginService.getCookieAdminSecu(request, response);

		mav.addObject("UserSeq", uSeq);
		mav.addObject("UserLv", userLv);
		mav.addObject("FlagSelFAnswer", flagSelFAnswer);
		mav.addObject("MemberType", memberType);
		mav.addObject("UserNickName", userNickName);
		mav.addObject("UserAlmoney", userAlmoney);
		mav.addObject("AdminSecu", adminSecu);

//		System.out.println("UserSeq" + uSeq);
//		System.out.println("UserLv" + userLv);
//		System.out.println("FlagSelFAnswer" + flagSelFAnswer);
//		System.out.println("MemberType" + memberType);
//		System.out.println("UserNickName" + userNickName);
//		System.out.println("UserAlmoney" + userAlmoney);
//		System.out.println("AdminSecu" + adminSecu);

 		return mav;
	}

	@RequestMapping("/questionList_SP")
	public ModelAndView questionList_SP(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();

		String pg = request.getParameter("pg");
		if(pg == null) pg = "1";
		int maxRow = 20;
		int totN = 0;

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final String sp = !cookieLoginService.getCookieUniv(request, response).equals("") ? cookieLoginService.getCookieUniv(request, response) : "1";


		HashMap<String, Object> localeItem = CommonUtil.getLocale(locale);
		String lang = String.valueOf(localeItem.get("lang"));
		String sourceLang = lang;
		if(userSeq > 0) {
			MemberVO mVo = memberService.getAlmoneyMyJoin(userSeq);
			sourceLang = mVo.getLang();
		}

		String targetLang = request.getParameter("targetLang");
		if(targetLang == null) { targetLang = ""; }
		String langString = CommonUtil.getLanguageString(sourceLang, targetLang);

		String msg1 = messageSource.getMessage("msg_1019", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		if(sp == null || sp == "null" || sp == "0") {
			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용
		}

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("sp", sp);
		param.put("pg", pg);
		param.put("maxRow", maxRow);

		List<QuestionVO> questionList = questionService.getQestionSpList(param);

		HashMap<String, Object> cParam = new HashMap<String, Object>();
		cParam.put("lang", targetLang);
		totN = questionService.getQuestionBestCnt(cParam);


		ChoiceVO choice = commonService.getAnswerQuestionSpChoice();

		BigDecimal sum_a_choicedAlmoney = choice.getSum_A_ChoicedAlmoney().setScale(0, RoundingMode.HALF_EVEN);
		BigDecimal sum_a_viewAlmoney = choice.getSum_A_ViewAlmoney().setScale(0, RoundingMode.HALF_EVEN);

		mav.addObject("sp", sp);
		mav.addObject("pg", pg);
		mav.addObject("questionList", questionList);
		mav.addObject("totN", totN);
		mav.addObject("sum_a_choicedAlmoney", sum_a_choicedAlmoney);
		mav.addObject("sum_a_viewAlmoney", sum_a_viewAlmoney);

		return mav;
	}

	@RequestMapping(value="question/questionList_SpAjax", produces="application/json;charset=UTF-8", method = RequestMethod.GET)
	public @ResponseBody String questionList_SpAjax(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		String pg = request.getParameter("pg");
		if(pg == null) pg = "1";
		final String sp = !cookieLoginService.getCookieUniv(request, response).equals("") ? cookieLoginService.getCookieUniv(request, response) : "1";
		int maxRow = 20;


		String msg1 = messageSource.getMessage("msg_1019", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		if(sp == null || sp == "null" || sp == "0") {
			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용
		}

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("sp", sp);
		param.put("pg", pg);
		param.put("maxRow", maxRow);

		List<QuestionVO> questionList = questionService.getQestionSpList(param);

		String result = CommonUtil.libJsonArrExit("SUCCESS", questionList);

		return result;
	}

	@RequestMapping("/questionList")
	public ModelAndView questionList(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();

		String old_url = request.getHeader("referer");
		//System.out.println("old_url : " + old_url);
		String code1 = request.getParameter("Code1");
		String code2 = request.getParameter("Code2");
		String code3 = request.getParameter("Code3");
		String code4 = request.getParameter("Code4");
		String code5 = request.getParameter("Code5");

		if(code1 == null) code1="";
		if(code2 == null) code2="";
		if(code3 == null) code3="";
		if(code4 == null) code4="";
		if(code5 == null) code5="";


		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		HashMap<String, Object> localeItem = CommonUtil.getLocale(locale);
		String lang = String.valueOf(localeItem.get("lang"));
		String sourceLang = lang;
		if(userSeq > 0) {
			MemberVO mVo = memberService.getAlmoneyMyJoin(userSeq);
			sourceLang = mVo.getLang();
		}

		String targetLang = request.getParameter("targetLang");
		if(targetLang == null) { targetLang = ""; }
		String langString = CommonUtil.getLanguageString(sourceLang, targetLang);
		String langSel = CommonUtil.getLanguageSelString(sourceLang, targetLang);


		String CurPageName = request.getParameter("CurPageName");
		if(CurPageName == null) CurPageName = "/answer/questionList";
		String req_PG = request.getParameter("pg");
		if(req_PG == null) req_PG = "";
		String src_Sort = request.getParameter("src_Sort");
		if(src_Sort == null) src_Sort = "";
		String src_OrderBy = request.getParameter("src_OrderBy");
		if(src_OrderBy == null) src_OrderBy = "";
		String Section1 = request.getParameter("Section1");
		if(Section1 == null) Section1 = "";
		String src_trn = request.getParameter("src_trn");
		if(src_trn == null) src_trn = "";

		if(Section1.equals("")) Section1 = "0";
		if(src_Sort.equals("") || src_Sort == null) src_Sort = "DateReg";
		if(src_OrderBy.equals("") || src_OrderBy == null) src_OrderBy = "DESC";

		// 페이징 시작
		int n_pagesize = 30;
		int n_pagescnt = 5;
		int n_curpage = 1;
		int n_totalpage = 0;
		// 페이징 종료

		//String sectionCodeParameter = "" + sectionCode;
		String sectionCodeParameter = "";

		int nReq_PG = 0;
		if(req_PG.equals("0") || req_PG == null || req_PG.equals("")) {
			nReq_PG = 1;
		}
		else {
			nReq_PG = Integer.parseInt(req_PG);
		}

		n_curpage = nReq_PG;

		for(int i = 1; i <= 5; i++) {
			String sectionCode = request.getParameter("Code" + i);
			if(sectionCode == null || sectionCode.equals("")) {
				sectionCode = "0";
			}
			if(sectionCodeParameter.equals("")) {
				sectionCodeParameter += sectionCode;
			}
			else {
				sectionCodeParameter += "," + sectionCode;
			}
		}

		String sortCode = "0";

		if(src_Sort.equals("AnswerCount")){
			sortCode = "1";
		}
		else if(src_Sort.equals("TransCount")){
			sortCode = "2";
		}

		int nSortCode = Integer.parseInt(sortCode);

		String[] sectionArr = sectionCodeParameter.split(",");


		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("call_flag", 1);
		param.put("order_flag", nSortCode);
		param.put("lang", targetLang);
		param.put("page_num", nReq_PG);
		param.put("page_size", n_pagesize);

		for(int j = 0; j < sectionArr.length; j++) {
			param.put("code"+(j+1), sectionArr[j]);
		}

		List<QuestionVO> list1 = answerService.getSpQuestionListByLang(param);

		HashMap<String, Object> param2 = new HashMap<String, Object>();
		param2.put("call_flag", 0);
		param2.put("order_flag", nSortCode);
		param2.put("lang", targetLang);
		param2.put("page_num", nReq_PG);
		param2.put("page_size", n_pagesize);

		for(int j = 0; j < sectionArr.length; j++) {
			param2.put("code"+(j+1), sectionArr[j]);
		}

		List<QuestionVO> list2 = answerService.getSpQuestionListByLang(param2);

		HashMap<String, Object> param3 = new HashMap<String, Object>();
		param3.put("call_flag", 1);
		param3.put("order_flag", nSortCode);
		param3.put("lang", targetLang);
		param3.put("page_num", nReq_PG);
		param3.put("page_size", n_pagesize);

		for(int j = 0; j < sectionArr.length; j++) {
			param3.put("code"+(j+1), sectionArr[j]);
		}

		int totalCnt = answerService.getSpQuestionCountByLang(param3);

		n_totalpage =  (totalCnt / n_pagesize);

		if(n_totalpage*n_pagesize < totalCnt) {
			n_totalpage++;
		}


		String url = "/answer/questionList?Section1=" + Section1 + "&src_Sort=" + src_Sort + "&src_OrderBy=" + src_OrderBy + "&Code1="+code1+ "&Code2="+code2+ "&Code3="+code3+ "&Code4="+code4+ "&Code5="+code5 + "&targetLang=" + targetLang;
		String pageStr = CommonUtil.get_Paging_Tag_New_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);

		mav.addObject("langString", langString);
		mav.addObject("langSel", langSel);
		mav.addObject("lang", lang);
		mav.addObject("targetLang", targetLang);
		mav.addObject("CurPageName", CurPageName);
		mav.addObject("req_PG", req_PG);
		mav.addObject("src_Sort", src_Sort);
		mav.addObject("Section1", Section1);
		mav.addObject("n_pagesize", n_pagesize);
		mav.addObject("n_pagescnt", n_pagescnt);
		mav.addObject("n_curpage", n_curpage);
		mav.addObject("n_totalpage", n_totalpage);

		mav.addObject("questionList", list2);
		mav.addObject("userLv", userLv);
		mav.addObject("userSeq", userSeq);

		mav.addObject("pageStr", pageStr);
		mav.addObject("sourceLang", sourceLang);

		return mav;
	}

	@RequestMapping(value="/answerView/getQuestion", produces="application/json;charset=UTF-8", method = RequestMethod.GET) //, produces="application/json;charset=UTF-8"
	public @ResponseBody String getQuestion(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=UTF-8");

		String req_PG = request.getParameter("pg");
		String n_pagesize = request.getParameter("page_size");

		//String sectionCodeParameter = "" + sectionCode;
		String sectionCodeParameter = "";

		int nReq_PG = 0;
		if(req_PG == "0" || req_PG == null || req_PG == "") {
			nReq_PG = 1;
		}
		else {
			nReq_PG = Integer.parseInt(req_PG);
		}

		int pageSize = 0;
		if(n_pagesize == "0" || n_pagesize == null || n_pagesize == "") {
			pageSize = 30;
		}
		else {
			pageSize = Integer.parseInt(n_pagesize);
		}

		for(int i = 1; i <= 5; i++) {
			String sectionCode = request.getParameter("Code" + i);
			if(sectionCode == "" || sectionCode == null) {
				sectionCode = "0";
			}
			if(sectionCodeParameter == "") {
				sectionCodeParameter += sectionCode;
			}
			else {
				sectionCodeParameter += "," + sectionCode;
			}
		}

		String sortCode = request.getParameter("src_Sort");

		if(sortCode.equals("AnswerCount")){
			sortCode = "1";
		}
		else {
			sortCode = "0";
		}
		int nSortCode = Integer.parseInt(sortCode);

		String[] sectionArr = sectionCodeParameter.split(",");

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("call_flag", 1);
		param.put("order_flag", nSortCode);
		param.put("page_num", nReq_PG);
		param.put("page_size", pageSize);

		for(int j = 0; j < sectionArr.length; j++) {
			param.put("code"+(j+1), sectionArr[j]);
		}

		List<QuestionVO> list1 = answerService.getSpQuestionList(param);

		HashMap<String, Object> param2 = new HashMap<String, Object>();
		param2.put("call_flag", 0);
		param2.put("order_flag", nSortCode);
		param2.put("page_num", nReq_PG);
		param2.put("page_size", pageSize);

		for(int j = 0; j < sectionArr.length; j++) {
			param2.put("code"+(j+1), sectionArr[j]);
		}

		List<QuestionVO> list2 = answerService.getSpQuestionList(param2);

		String result = "[";
		result += new Gson().toJson(list1);
		result += ",";
		result += new Gson().toJson(list2);
		result += "]";

		return result;
	}


	@RequestMapping("/getChoiceAndViewA")
	public @ResponseBody HashMap<String, Object> getChoiceAndViewA(HttpServletRequest request){
		return answerService.getChoiceAndViewA();
	}

	@RequestMapping("/fn_SQL_Answer_Section1")
	public @ResponseBody List<HashMap<String, Object>> fn_SQL_Answer_Section1(){
		return answerService.fn_SQL_Answer_Section1();
	}


	@RequestMapping(value="/answer/extraAlmoney", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String extraAlmoney(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		String act = request.getParameter("ACT");

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		if(userSeq == 0) {
			//

			return "<!--LOGOFF-->";
		}


		int MinExtraAlmoney = 300;
		int MaxExtraAlmoney = 10000;
		//NumberFormat nf = NumberFormat.getCurrencyInstance();
		DecimalFormat nf = new DecimalFormat("###,###");

		//System.out.println("ContentsSeq : " + request.getParameter("AnswerSeq"));
		if(act.equals("ExtraAlmoneyList")) {
			String ContentsSeq = "";
			String cType = "";

			if(!request.getParameter("AnswerSeq").equals("0")) {
				ContentsSeq = request.getParameter("AnswerSeq");
				cType = "A";
			}
			else {
				ContentsSeq = request.getParameter("QuestionSeq");
				cType = "Q";
			}

			HashMap<String ,Object> param = new HashMap<String, Object>();
			param.put("contentsSeq", ContentsSeq);
			param.put("cType", cType);

			//System.out.println("contentsSeq : " + param.get("contentsSeq"));
			//System.out.println("cType : " + param.get("cType"));

			List<AlmoneyVO> extraAlmoneyList = commonService.getExtraAlmoneyList(param);
			//System.out.println("extraAlmoneyList.size() : " + extraAlmoneyList.size());
			return CommonUtil.libJsonArrExit("ExtraAlmoneyList", extraAlmoneyList);

		}
		else if(act.equals("tExtraAlmoneyList")) {
			String ContentsSeq = "";
			String cType = "T";

			ContentsSeq = request.getParameter("tSeq");

			HashMap<String ,Object> param = new HashMap<String, Object>();
			param.put("contentsSeq", ContentsSeq);
			param.put("cType", cType);

			//System.out.println("contentsSeq : " + param.get("contentsSeq"));
			//System.out.println("cType : " + param.get("cType"));

			List<AlmoneyVO> extraAlmoneyList = commonService.getExtraAlmoneyList(param);
			//System.out.println("extraAlmoneyList.size() : " + extraAlmoneyList.size());
			return CommonUtil.libJsonArrExit("tExtraAlmoneyList", extraAlmoneyList);
		}
		else if(act.equals("tExtraAlmoney")) {

		}
		else if(act.equals("ExtraAlmoney")) {
			int lv = userLv;
			HashMap<String ,Object> param = new HashMap<String, Object>();
			List<HashMap<String ,Object>> list = new ArrayList<HashMap<String ,Object>>();

			String msg1 = messageSource.getMessage("msg_1020", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			String msg2 = messageSource.getMessage("msg_1021", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			String msg3 = messageSource.getMessage("msg_1022", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			String msg4 = messageSource.getMessage("msg_0294", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			String msg5 = messageSource.getMessage("msg_1023", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			String msg6 = messageSource.getMessage("msg_1024", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			String msg7 = messageSource.getMessage("msg_1025", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			String msg8 = messageSource.getMessage("msg_1026", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			String msg9 = messageSource.getMessage("msg_1027", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			String msg10 = messageSource.getMessage("msg_1016", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			String msg11 = messageSource.getMessage("msg_1028", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			String msg12 = messageSource.getMessage("msg_1029", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			String msg13 = messageSource.getMessage("msg_1030", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			String msg14 = messageSource.getMessage("msg_1031", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			String msg15 = messageSource.getMessage("msg_0318", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			//필요한 만큼 선언후 사용

			if (lv < 2) {
				param.put("msg", CommonUtil.getLevelName(lv, request) + msg1); // msg1 활용
				list.add(param);
				return CommonUtil.libJsonArrExit("ExtraAlmoney_focus", list);
			}

			if(request.getParameter("ExtraAlmoney") == "") {
				param.put("msg", msg2);// msg2 활용
				list.add(param);
				return CommonUtil.libJsonArrExit("ExtraAlmoney_focus", list);
			}

			if(Integer.parseInt(request.getParameter("ExtraAlmoney")) < MinExtraAlmoney) {
				param.put("msg", msg3 + nf.format( MinExtraAlmoney ) + msg4);// msg3, msg4 활용
				list.add(param);
				return CommonUtil.libJsonArrExit("ExtraAlmoney_focus", list);
			}

			if(Integer.parseInt(request.getParameter("ExtraAlmoney")) > MaxExtraAlmoney) {
				param.put("msg", msg5 + nf.format( MaxExtraAlmoney ) + msg4);// msg5, msg4 활용
				list.add(param);
				return CommonUtil.libJsonArrExit("ExtraAlmoney_focus", list);
			}

			/*
			if ($_POST['ExtraAlmoney'] != (int)$_POST['ExtraAlmoney'] || $_POST['ExtraAlmoney'] % 10)
				libJsonExit('ExtraAlmoney_focus', array('msg' => "10알 단위로 입력해주세요."));
			*/
			int n = (int) (Math.floor(Integer.parseInt(request.getParameter("ExtraAlmoney"))/100) * 100);

			if(Integer.parseInt(request.getParameter("ExtraAlmoney")) != n) {
				param.put("msg", msg6);// msg6 활용
				list.add(param);
				return CommonUtil.libJsonArrExit("ExtraAlmoney_focus", list);
			}
			// 전처리 삽입 : 같은 유저가 n초(default:10) 안에 두번 이상 알을 주지 못하도록 설정 (2021-02-03 김주윤)
			if(Integer.parseInt(request.getParameter("ExtraAlmoney")) >= 1000) {
				String ContentsSeq_me = "";
				String cType_me = "";

				if(!request.getParameter("AnswerSeq").equals("0")) {
					ContentsSeq_me = request.getParameter("AnswerSeq");
					cType_me = "A";
				}
				else {
					ContentsSeq_me = request.getParameter("QuestionSeq");
					cType_me = "Q";
				}

				HashMap<String ,Object> param_me = new HashMap<String, Object>();
				param_me.put("contentsSeq", ContentsSeq_me);
				param_me.put("cType", cType_me);
				param_me.put("userSeq", userSeq);

				AlmoneyVO extraAlmoneyListUserTime = commonService.getExtraAlmoneyListUserTime(param_me);

				SimpleDateFormat format1_me = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
				Date time_me = new Date();
				Calendar cal = Calendar.getInstance();
				cal.setTime(time_me);
				cal.add(Calendar.SECOND, -30);

				time_me = cal.getTime();

				if(extraAlmoneyListUserTime!=null) {
					Date userTime = format1_me.parse(extraAlmoneyListUserTime.getConDate());
					if(userTime.getTime() > time_me.getTime()) {
						param.put("msg", msg7 + "\\n" + msg8 + (userTime.getTime() - time_me.getTime())/1000 + msg9);// msg7, msg8, msg9 활용
						list.add(param);
						return CommonUtil.libJsonArrExit("ExtraAlmoney_focus", list);
					}
				}

			}			// 전처리 종료 (2021-02-03 김주윤)

			String ContentsSeq = "";
			String Type = "";
			int TypeMe = 0;
			int TypeYou = 0;
			String QusAns = "";
			if(!request.getParameter("AnswerSeq").equals("0")) {
				ContentsSeq = request.getParameter("AnswerSeq");
				Type = "A";
				TypeMe = 4;
				TypeYou = 5;
				QusAns = "ANSWER";
			}
			else {
				ContentsSeq = request.getParameter("QuestionSeq");
				Type = "Q";
				TypeMe = 2;
				TypeYou = 3;
				QusAns = "QUESTION";
			}

			int extraAlmoney = Integer.parseInt(request.getParameter("ExtraAlmoney"));

			HashMap<String ,Object> param2 = new HashMap<String, Object>();
			param2.put("contentsSeq", ContentsSeq);
			param2.put("userSeq", userSeq);
			param2.put("typeMe", TypeMe);
			param2.put("minusExtraAlmoney", (extraAlmoney * -1));
			param2.put("extraAlmoney", extraAlmoney);
			param2.put("typeYou", TypeYou);
			param2.put("qusAns", QusAns);
			param2.put("cType", Type); //nf.format( MaxExtraAlmoney )
			param2.put("extraAlmoneyFmt", nf.format( extraAlmoney ));
			param2.put("maxExtraAlmoney", MaxExtraAlmoney);

			param2.put("tbl", "T_REPLY_"+QusAns);
			param2.put("qusAnsTbl", "T_"+QusAns);
			param2.put("qusAnsCol", QusAns + "Seq");
			String ticketReplChk = "N";

			AlmoneyVO sumExtraAlmoney = commonService.getSumExtraAlmoneyInfo(param2);
			//System.out.println("ReturnCode  : " + sumExtraAlmoney.getReturnCode());
			//System.out.println("ErrText  : " + sumExtraAlmoney.getErrText());
			int retCode = sumExtraAlmoney.getReturnCode();
			HashMap<String ,Object> param3 = new HashMap<String, Object>();
			List<HashMap<String ,Object>> list2 = new ArrayList<HashMap<String ,Object>>();
			switch(retCode) {
				case 1 :
					if(extraAlmoney >= 1000) {
						// 증정시 1000원 이상이라면 오늘 게임 스택 1회 증가 - 20210225 김주윤 - 시작
						HashMap<String, Object> gConfig = commonService.getTicketConfig();
						int hunCountConfig = Integer.parseInt( String.valueOf( gConfig.get("hunCount") ) );

						int stackCnt = commonService.getTicketStackCnt(userSeq);
						//int queCount = 0;
						int hunCount = 0;
						//int replCount = 0;

						//System.out.println("stackCnt : " + stackCnt);

						HashMap<String, Object> cParam = new HashMap<String, Object>();
						cParam.put("flag", Type);
						cParam.put("targetSeq", ContentsSeq);
						String sourceDate = commonService.getContentsDateBySeq(cParam);

						boolean checkRouletteKind = CommonUtil.checkRoulettePublish(sourceDate); // true 일 경우만 stackCnt 추가
						//System.out.println("checkRouletteKind : " + checkRouletteKind);


						HashMap<String, Object> stackInfo = null;
						if(stackCnt > 0) {
							HashMap<String, Object> sParam = new HashMap<String, Object>();
							sParam.put("userSeq", userSeq);
							sParam.put("mode", "hun");

							// 스택 증가
							if(checkRouletteKind == true) {
								commonService.setAddTickStackUse(sParam);
							}
							stackInfo =  commonService.getTicketStack(userSeq);
						}
						else {
							HashMap<String, Object> sParam = new HashMap<String, Object>();
							sParam.put("userSeq", userSeq);
							sParam.put("queCount", 0);
							sParam.put("ansCount", 0);
							sParam.put("replCount", 0);
							sParam.put("hunCount", 1);
							sParam.put("estiCount", 0);

							if(checkRouletteKind == true) {
								commonService.addTicketStack(sParam);
							}
							//System.out.println("userSeq : " + userSeq);
							stackInfo =  commonService.getTicketStack(userSeq);
						}

						//queCount = Integer.parseInt( String.valueOf( stackInfo.get("queCount") ) );
						hunCount = Integer.parseInt( String.valueOf( stackInfo.get("hunCount") ) );
						//replCount = Integer.parseInt( String.valueOf( stackInfo.get("replCount") ) );

						//스택 증가

						if(hunCount >= hunCountConfig) {
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)

							Calendar cal = Calendar.getInstance();
							String startDt = formatter.format(cal.getTime());

							//cal.add(Calendar.DATE, 2);
							//String endDt = formatter.format(cal.getTime());

							HashMap<String, Object> tParam = new HashMap<String, Object>();
							tParam.put("userSeq", userSeq);
							tParam.put("startDt", startDt);
							tParam.put("endDt", startDt); // 당일 23시 59분 59초 이내로 설정 변경
							tParam.put("gType", 1); // 1: 알머니, 2:사은품
							tParam.put("rType", "hun");

							// 이용권 추가
							commonService.addTicket(tParam);


							HashMap<String, Object> sParam = new HashMap<String, Object>();
							sParam.put("userSeq", userSeq);
							sParam.put("mode", "hun");
							sParam.put("hunCount", hunCountConfig); //queCount = 3 or ansCount = 1 or replCount = 5

							ticketReplChk="Y";
							// 스택 차감
							commonService.setSubTickStackUse(sParam);

						}

						// 증정시 1000원 이상이라면 오늘 게임 스택 1회 증가 - 20210225 김주윤 - 종료
					}

					param3.put("Type", Type);
					param3.put("ContentsSeq", ContentsSeq);
					param3.put("sumExtraAlmoney", sumExtraAlmoney.getSumExtraAlmoney());
					param3.put("ticketReplChk", ticketReplChk);

					list2.add(param3);
					return CommonUtil.libJsonArrExit(act, list2);
				case 2 :
					return CommonUtil.libJsonArrExit(msg10, list2);// msg10 활용
				case 51 :
					return CommonUtil.libJsonArrExit(msg11, list2);// msg11 활용
				case 52 :
					return CommonUtil.libJsonArrExit(msg12, list2);// msg12 활용
				case 53 :
					return CommonUtil.libJsonArrExit(msg13 + nf.format( MaxExtraAlmoney ) + msg14, list2);// msg13, msg14 활용
				default:
					return CommonUtil.libJsonArrExit(sumExtraAlmoney.getReturnCode() + msg15 + sumExtraAlmoney.getErrText(), list2);// msg15 활용
			}
		}


		return null;
	}

	@RequestMapping(value="/answer/answerList_AReply", method = RequestMethod.POST)
	public ModelAndView answerList_AReply(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		//int maxSiren = 3;
		int targetSeq = Integer.parseInt(request.getParameter("TargetSeq"));

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("flag", "A");
		param.put("targetSeq", targetSeq);

		List<ReplyVO> list = replyService.getReplyList(param);

		mav.addObject("loopCnt", list.size());
		mav.addObject("AnswerSeq", targetSeq);
		mav.addObject("answerList", list);

		return mav;
	}

	@RequestMapping(value="/answer/answerList_QReply", method = RequestMethod.POST)
	public ModelAndView answerList_QReply(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		//int maxSiren = 3;
		int targetSeq = Integer.parseInt(request.getParameter("TargetSeq"));

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("flag", "Q");
		param.put("targetSeq", targetSeq);

		List<ReplyVO> list = replyService.getReplyList(param);

		mav.addObject("loopCnt", list.size());
		mav.addObject("QuestionSeq", targetSeq);
		mav.addObject("questionList", list);

		return mav;
	}

	//moveQuestion
	@RequestMapping(value="/answer/moveQuestion", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String moveQuestion(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		String act = request.getParameter("ACT");

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq == 0) {
			return "<!--LOGOFF-->";
		}

		if(act.equals("GoTopQuestion")) {
			//이 기능의 사용 빈도가 많아지면 아래의 쿼리를 프로시저로 만들자.
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("userSeq", userSeq);
			param.put("questionSeq", request.getParameter("QuestionSeq"));

			HashMap<String, Object> r = answerService.setMoveQuestion(param);
			int returnCode = 0;
			//String errText = "시스템 오류가 발생하였습니다.";

			if(r != null) {
				returnCode = Integer.parseInt(r.get("ReturnCode").toString());
				//errText = r.get("ErrText").toString();
			}

			List<HashMap<String ,Object>> list = new ArrayList<HashMap<String ,Object>>();

			String msg1 = messageSource.getMessage("msg_1016", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			String msg2 = messageSource.getMessage("msg_1028", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			String msg3 = messageSource.getMessage("msg_1032", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			String msg4 = messageSource.getMessage("msg_0318", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

			switch(returnCode) {
				case 1:
					return CommonUtil.libJsonArrExit(act, list);
				case 2:
					return CommonUtil.libJsonArrExit(msg1, list); //msg1 활용
				case 51:
					return CommonUtil.libJsonArrExit(msg2, list); //msg2 활용
				case 52:
					return CommonUtil.libJsonArrExit(msg3, list); //msg3 활용
				default:
					return CommonUtil.libJsonArrExit(msg4, list); //msg4 활용
			}
		}

		return null;
	}

	@RequestMapping(value="/answer/questionZzim", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String questionZzim(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		String act = request.getParameter("ACT");

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		JSONObject global = null;

		if(cookies1 != null) {
			global = CommonUtil.getGlobal(request, response);
		}

		String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1033", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_1034", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		if(userSeq == 0) {
			return CommonUtil.libJsonExit(msg1, null); // msg1 활용
		}
		else {
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("userSeq", userSeq);
			param.put("questionSeq", request.getParameter("QuestionSeq"));

			int chk = commonService.getAnswerZimChk(param);

			if(chk > 0) {
				return CommonUtil.libJsonExit(msg2, null); // msg2 활용
			}
			else {
				commonService.setAnswerZzim(param);

				return CommonUtil.libJsonExit(msg3, null); // msg3 활용
			}
		}
	}

	@RequestMapping(value="/answer/answerProcess", produces="application/json;charset=UTF-8", method = RequestMethod.GET)
	public @ResponseBody String answerProcess(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		String answerSeq = CommonUtil.fn_Word_In(request.getParameter("AnswerSeq"));
		int adSeq = Integer.parseInt(CommonUtil.fn_Word_In(request.getParameter("AdSeq")));
		int retCode = 0;
		String viewMoneySum = "0";

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int ver = cookieLoginService.getCookieVer(request, response);

		HashMap<String ,Object> param = new HashMap<String, Object>();
		param.put("aSeq", answerSeq);
		param.put("userSeq", userSeq);
		param.put("adSeq", adSeq);

		HashMap<String, Object> processInfo = commonService.setAlmoneyProcess(param);




		if(processInfo != null) {
			retCode = Integer.parseInt(processInfo.get("result_code").toString());
			viewMoneySum = processInfo.get("MEM_ALMONEY").toString();
		}

		if(userSeq > 0) {
			BigDecimal viewMoney = new BigDecimal(viewMoneySum);
			BigDecimal UserAlmoneyRedim = viewMoney.setScale(0, BigDecimal.ROUND_FLOOR);


			List<Map<String, Object>> alarmList = commonService.getAlarmBySeq( userSeq );
			int alarmCnt = 0;
			for(int i = 0; i < alarmList.size(); i++) {
				if(alarmList.get(i).get("ANS_CHOICE") != null) { alarmCnt += Integer.parseInt( String.valueOf( alarmList.get(i).get("ANS_CHOICE") ) ); }
				if(alarmList.get(i).get("ANS_REGIST") != null) { alarmCnt += Integer.parseInt( String.valueOf( alarmList.get(i).get("ANS_REGIST") ) ); }
				if(alarmList.get(i).get("FAVORITE_QUE_REGIST") != null) { alarmCnt += Integer.parseInt( String.valueOf( alarmList.get(i).get("FAVORITE_QUE_REGIST") ) ); }
				if(alarmList.get(i).get("CMT_REGIST") != null) { alarmCnt += Integer.parseInt( String.valueOf( alarmList.get(i).get("CMT_REGIST") ) ); }
				if(alarmList.get(i).get("ANS_CHOICE_READY") != null) { alarmCnt += Integer.parseInt( String.valueOf( alarmList.get(i).get("ANS_CHOICE_READY") ) ); }
				if(alarmList.get(i).get("ALMONEY_INCOME") != null) { alarmCnt += Integer.parseInt( String.valueOf( alarmList.get(i).get("ALMONEY_INCOME") ) ); }
				if(alarmList.get(i).get("ALMONEY_PAYING") != null) { alarmCnt += Integer.parseInt( String.valueOf( alarmList.get(i).get("ALMONEY_PAYING") ) ); }
				if(alarmList.get(i).get("MEM_LEVEL_UP") != null) { alarmCnt += Integer.parseInt( String.valueOf( alarmList.get(i).get("MEM_LEVEL_UP") ) ); }
				if(alarmList.get(i).get("REPORT") != null) { alarmCnt += Integer.parseInt( String.valueOf( alarmList.get(i).get("REPORT") ) ); }
				if(alarmList.get(i).get("MENTEE") != null) { alarmCnt += Integer.parseInt( String.valueOf( alarmList.get(i).get("MENTEE") ) ); }
				if(alarmList.get(i).get("MENTEE_UNSET") != null) { alarmCnt += Integer.parseInt( String.valueOf( alarmList.get(i).get("MENTEE_UNSET") ) ); }
				if(alarmList.get(i).get("RECOMM_MEM_JOIN") != null) { alarmCnt += Integer.parseInt( String.valueOf( alarmList.get(i).get("RECOMM_MEM_JOIN") ) ); }
				if(alarmList.get(i).get("NOTICE") != null) { alarmCnt += Integer.parseInt( String.valueOf( alarmList.get(i).get("NOTICE") ) ); }
			}
		}

		HashMap<String ,Object> param2 = new HashMap<String, Object>();
		param2.put("answerSeq", answerSeq);
		param2.put("ipAddr", request.getRemoteAddr());
		commonService.setIpLog(param2);

		//System.out.println("/answer/answerProcess 실행 완료!");
		List<HashMap<String ,Object>> list = new ArrayList<HashMap<String ,Object>>();
		return CommonUtil.libJsonArrExit("success", list);
		//return "success";
	}

	@RequestMapping(value="/answerView/getFullAnswer", method = RequestMethod.GET)
	public ModelAndView getFullAnswer(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int answerLv = cookieLoginService.getCookieUserLv(request, response);

		String strAnswerSeq = CommonUtil.fn_Word_In(request.getParameter("AnswerSeq"));
		int tSeq = 0;
		if(request.getParameter("tSeq") != null) {
			tSeq = Integer.parseInt(request.getParameter("tSeq"));
		}



		int answerSeq = Integer.parseInt(strAnswerSeq);
		BigInteger seq = new BigInteger(strAnswerSeq);
		AnswerVO answer = answerService.getQuestionAnswerViewSP(seq);

		HashMap<String, Object> getFlag = commonService.getFlagChoice(answer.getQuestionSeq());


		Long PointCountNo = (long) 0;
		String PointCount6_Yn = "N";

		if(userSeq > 0) {
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("answerSeq", answerSeq);
			param.put("userSeq", userSeq);


			LogEstimateVO  logEstimate = logEstimateService.getLogEstimateBySeq(param);

			if(logEstimate != null) {
				PointCountNo = logEstimate.getPointCountNo();
				PointCount6_Yn = logEstimate.getPointCount6_Yn();
			}

			param.put("questionSeq", answer.getQuestionSeq());
			commonService.setLogViewByMultiParam(param);
		}
		else {
			//로그인하지 않았으면 무조건 조회수 증가
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("answerSeq", answerSeq);
			param.put("questionSeq", answer.getQuestionSeq());

			if(tSeq == 0) {
				answerService.setAnswerReadUpdate(param);
			}
		}

		HashMap<String, Object> param2 = new HashMap<String, Object>();
		param2.put("strData1", answerSeq);
		param2.put("strData2", "Answer");

		List<FileVO> files = fileService.getFile(param2);

		HashMap<String, Object> param3 = new HashMap<String, Object>();
		param3.put("flag", "A");
		param3.put("targetSeq", answerSeq);
		int answerReplyCount = replyService.getReplySum(param3);

		HashMap<String, Object> param4 = new HashMap<String, Object>();
		param4.put("contentsSeq", answerSeq);
		param4.put("contentsType", "Answer");
		param4.put("reportMemSeq", userSeq);
		int answerReportCnt = reportService.getReportActionCount(param4);


		HashMap<String, Object> localeItem = CommonUtil.getLocale(locale);
		String lang = String.valueOf(localeItem.get("lang"));
		String sourceLang = lang;
		if(answerLv > 0) {
			MemberVO mVo = memberService.getAlmoneyMyJoin(userSeq);
			sourceLang = mVo.getLang();
		}

		//답변글의 기계번역 카운터
		HashMap<String, Object> tqParam = new HashMap<String, Object>();
		tqParam.put("trnType", 1);
		tqParam.put("replyType", "A");
		tqParam.put("orgSeq", answerSeq);
		tqParam.put("lang", sourceLang);
		int ansMachineCnt = commonService.getMachineTrnsCount(tqParam);

		//답변글의 기계번역 한건 가져오기
		//getTrnslateBySeq
		TranslateVO ansTrn = commonService.getTrnslateBySeq(tqParam);
		int ansMachineTrnSeq = 0;
		if(ansMachineCnt> 0) {
			ansMachineTrnSeq = ansTrn.getSeq();
		}


		// 번역문 처리
		int tUserSeq = 0;
		int tUserLv = 0;
		String tAiNickName = "";
		String tAiNation = "";
		String tAiLang = "";
		int trnType = 0;
		int tAiCount = 0;
		int tGood = 0;
		int tBad = 0;
		int tSirenN = 0;
		int tMaxSiren = 3;
		BigDecimal tExtraAlmoney = new BigDecimal("0.0");

		int sGood = 0;
		int sBad = 0;

		if(tSeq > 0) {
			HashMap<String, Object> mtParam = new HashMap<String, Object>();
			mtParam.put("seq", tSeq);

			TranslateVO tVo = commonService.getTrnslateViewBySeq(mtParam);

			HashMap<String, Object> vParam = new HashMap<String, Object>();
			vParam.put("userSeq", tVo.getUserSeq());
			vParam.put("contentsSeq", tSeq);
			vParam.put("answerSeq", answerSeq);

			V2RankVO mVo = commonService.getUserInfoBySeq(vParam);

			//현재 선택된 번역 정보 - 닉네임(tNickName), 국적(tNation), 언어별 번역 카운터(tEnCnt 등)
			trnType = tVo.getTrnType();
			tUserSeq = tVo.getUserSeq();
			tUserLv = Integer.parseInt(mVo.getLv());
			tAiNickName = mVo.getNickName();
			tAiNation = mVo.getNation();
			tAiLang = tVo.getLang();
			tAiCount = tVo.getReadCount();
			tSirenN = Integer.parseInt(mVo.getSirenN());
			tExtraAlmoney = mVo.getExtraAlmoney();

			//2차 개발을 위한 준비
			int tKoCnt = 0;
			int tEnCnt = 0;
			int tEsCnt = 0;
			int tFrCnt = 0;
			int tPtCnt = 0;
			int tDeCnt = 0;
			int tArCnt = 0;
			int tFaCnt = 0;
			int tRuCnt = 0;
			int tJaCnt = 0;
			int tItCnt = 0;
			int tZhCnt = 0;
			int tViCnt = 0;
			int tHiCnt = 0;
			int tBnCnt = 0;
			int tIdCnt = 0;
			int tMsCnt = 0;
			int tTrCnt = 0;
			int tThCnt = 0;
			int tMnCnt = 0;

			if(trnType == 1) {
				//기계 번역은 언어별로 한개씩만 허용, 자신에게 해당되는 언어 개수
				//첫 화면에 자신의 언어 두개까지 보여 준다.

				// 아래 화살표로 보이는 다른 나라 언어는 동적으로 구성한다.
			}
			else {
				//사람 번역은 언어별도 두개까지 허용, 자신에게 해당되는 언어 개수(2차개발 사항)

				//첫 화면에 자신의 언어 두개까지 보여 준다.

				// 아래 화살표로 보이는 다른 나라 언어는 동적으로 구성한다.
			}

			//번역문 카운터 증가
			commonService.setTranslateCount(tSeq);

			answer.setAnswer(tVo.getComment());

			HashMap<String, Object> voteParam = new HashMap<String, Object>();
			voteParam.put("contentSeq", tSeq);
			voteParam.put("contentType", "T");
			int voteCount = commonService.getQuestionVoteCountSum(voteParam);

			HashMap<String, Object> vote = null;

			if(voteCount > 0) {
				vote = commonService.getQuestionVoteSum(voteParam);
				tGood = Integer.parseInt( String.valueOf( vote.get("good") ) );
				tBad = Integer.parseInt( String.valueOf( vote.get("bad") ) );
			}
		}

		HashMap<String, Object> ansVoteParam = new HashMap<String, Object>();
		ansVoteParam.put("contentSeq", answerSeq);
		ansVoteParam.put("contentType", "A");
		int ansVoteCount = commonService.getQuestionVoteCountSum(ansVoteParam);

		HashMap<String, Object> ansVote = null;

		if(ansVoteCount > 0) {
			ansVote = commonService.getQuestionVoteSum(ansVoteParam);
			sGood = Integer.parseInt( String.valueOf( ansVote.get("good") ) );
			sBad = Integer.parseInt( String.valueOf( ansVote.get("bad") ) );
		}



		List<ReplyVO> replyList = replyService.getReplyList(param3);


		mav.addObject("AnswerSeq", answerSeq);
		//mav.addObject("pointRank", pointRank);
		mav.addObject("answer", answer);

		mav.addObject("answerLv", answerLv);

		mav.addObject("MemberSeq", getFlag.get("MemberSeq"));
		mav.addObject("FlagChoiceQ", getFlag.get("FlagChoice"));
		mav.addObject("pointCountNo", PointCountNo);
		mav.addObject("pointCount6_Yn", PointCount6_Yn);
		mav.addObject("files", files);
		mav.addObject("answerReplyCount", answerReplyCount);
		mav.addObject("answerReportCnt", answerReportCnt);

		mav.addObject("replyList", replyList);

		mav.addObject("tSeq", tSeq);
		mav.addObject("aUserSeq", userSeq);
		mav.addObject("tUserSeq", tUserSeq);
		mav.addObject("tUserLv", tUserLv);
		mav.addObject("tAiNickName", tAiNickName);
		mav.addObject("tExtraAlmoney", tExtraAlmoney);
		mav.addObject("tAiNation", tAiNation);
		mav.addObject("tAiLang", tAiLang);
		mav.addObject("tAiCount", tAiCount);
		mav.addObject("trnType", trnType);

		//최초 좋아요/싫어요 데이터 설정
		mav.addObject("tGood", tGood);
		mav.addObject("tBad", tBad);
		mav.addObject("tMaxSiren", tMaxSiren);
		mav.addObject("tSirenN", tSirenN);

		mav.addObject("ansMachineCnt", ansMachineCnt);
		mav.addObject("ansMachineTrnSeq", ansMachineTrnSeq);

		return mav;
	}

	//answerAdLog
	@RequestMapping(value="/answer/answerAdLog", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public void answerAdLog(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		String adSeq = request.getParameter("AdSeq");
		String actionType = request.getParameter("ActionType");
		String questionSeq = "";

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq > 0) {
			/*
			String format = "yyyy-MM-dd hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
			Calendar today = Calendar.getInstance();
			SimpleDateFormat type = new SimpleDateFormat(format);
			String now = type.format(today.getTime());
			*/
			String Referer = request.getHeader("referer");
			URL url = new URL(Referer);
			String queryStr = url.getQuery();

			String[] params = queryStr.split("&");
			for (String param: params) {
			    String key = param.substring(0, param.indexOf("="));
			    String val = param.substring(param.indexOf("=") + 1);

			    if(key.equals("Seq")) {
			    	questionSeq = val;
			    	break;
			    }
			}
			/*
			System.out.println("answerAdLog Referer : " + Referer);
	        System.out.println("answerAdLog userSeq : " +  global.get("UserSeq").toString());
	        System.out.println("answerAdLog aType : " + actionType);
	        System.out.println("answerAdLog questionSeq : " + questionSeq);
	        System.out.println("answerAdLog ipAddr : " + request.getRemoteAddr());
			*/
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("adSeq", adSeq);
			param.put("userSeq", userSeq);
			param.put("aType", actionType);
			param.put("questionSeq", questionSeq);
			param.put("ipAddr", request.getRemoteAddr());

			commonService.setLogAd(param);

		}
	}

	@RequestMapping("answer/answerWrite")
	public ModelAndView answerWrite(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		String eEvent = CommonUtil.fn_Word_In( request.getParameter("Event") );
		String aSeq = CommonUtil.fn_Word_In( request.getParameter("AnswerSeq") );
		String questionSeq = CommonUtil.fn_Word_In( request.getParameter("QuestionSeq") );
		String curPageName = CommonUtil.fn_Word_In( request.getParameter("CurPageName") );
		String section1 = CommonUtil.fn_Word_In( request.getParameter("Section1") );
		String src_Sort = CommonUtil.fn_Word_In( request.getParameter("src_Sort") );
		String src_OrderBy = CommonUtil.fn_Word_In( request.getParameter("src_OrderBy") );

		if(curPageName == null) { curPageName = ""; }
		if(src_Sort == null) { src_Sort = ""; }
		if(src_OrderBy == null) { src_OrderBy = ""; }

		String answer = "";
		String flagNickName = "";

		int writeMax = 0;
		int fileMax  = 0;

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");

		JSONObject global = null;

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		String sourceLang = "";
		if(userSeq > 0) {
			MemberVO mVo = memberService.getAlmoneyMyJoin(userSeq);
			sourceLang = mVo.getLang();
		}

		if(eEvent == "") {
			eEvent = "N";
		}
		if(aSeq != "" && aSeq != null) {
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("userLv", userLv);
			param.put("userSeq", userSeq);
			param.put("seq", aSeq);

			AnswerVO answerInfo = answerService.getAnswerView(param);

			answer = answerInfo.getAnswer();
			flagNickName = answerInfo.getFlagNickName();
			sourceLang = answerInfo.getLang();
		}

		ConfigVO conf = configService.getBoardConfig();
		writeMax = conf.getWriteMax();
		fileMax = conf.getFileMax();

		AnswerVO answerInfo = answerService.getAnswerBySeq(Integer.parseInt(questionSeq));


		HashMap<String, Object> param2 = new HashMap<String, Object>();
		param2.put("strData", cookieLoginService.getCookieAnswer(request, response));
		param2.put("seq", aSeq);

		List<FileVO> files = fileService.getBoardFile(param2);
		int fileCount = 0;
		if(files != null) {
			fileCount = files.size();
		}

		mav.addObject("eEvent", eEvent);
		mav.addObject("seq", aSeq);
		mav.addObject("questionSeq", questionSeq);
		mav.addObject("curPageName", curPageName);
		mav.addObject("section1", section1);
		mav.addObject("src_Sort", src_Sort);
		mav.addObject("src_OrderBy", src_OrderBy);
		mav.addObject("answer", answer);
		mav.addObject("flagNickName", flagNickName);
		mav.addObject("writeMax", writeMax);
		mav.addObject("fileMax", fileMax);
		mav.addObject("answerInfo", answerInfo);

		mav.addObject("fileCount", fileCount);
		mav.addObject("files", files);
		mav.addObject("sourceLang", sourceLang);

		return mav;
	}

	@RequestMapping(value="/answer/answerSave", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public void answerSave(MultipartHttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");

		String sp = request.getParameter("SP");
		int maxLv = 11;
		//int erro_no = 0;
		String defaultFolder = "Answer";
		UtilFile utilFile = new UtilFile();
		String FilePath = utilFile.getSaveLocation(request, defaultFolder);

		String Seq = CommonUtil.fn_Word_In(request.getParameter("Seq"));
		String QuestionSeq = CommonUtil.fn_Word_In(request.getParameter("QuestionSeq"));
		String CurPageName = CommonUtil.fn_Word_In(request.getParameter("CurPageName"));
		String Section1 = CommonUtil.fn_Word_In(request.getParameter("Section1"));
		String src_Sort = CommonUtil.fn_Word_In(request.getParameter("src_Sort"));
		String src_OrderBy = CommonUtil.fn_Word_In(request.getParameter("src_OrderBy"));
		String EEvent = CommonUtil.fn_Word_In(request.getParameter("EEvent"));

		String Contents = CommonUtil.fn_Word_In(request.getParameter("Contents"));
		String FlagNickName = CommonUtil.fn_Word_In(request.getParameter("FlagNickName"));
		String FlagUse = CommonUtil.fn_Word_In(request.getParameter("FlagUse"));

		int FileMax = Integer.parseInt(request.getParameter("FileMax"));

		if(Seq == null) { Seq = ""; }

		String nation = "";
		String detectLang = "";


		int Lv_LimitAnsDayRegistCnt = 0;
		int Lv_LimitAnsDayDupRegistCnt = 0;
		int Lv_LimitAnsContinueRegistTime = 0;

		long DateDiffSecond = 0;

		String format = "yyyy-MM-dd";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String dateReg = type.format(today.getTime());


		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);


		if(userSeq == 0) {
			String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용
		}
		else {
			int MaxSeq = 0;

			int isAnswered = 0;

			MemberVO mVo = memberService.getAlmoneyMyJoin(userSeq);
			nation = mVo.getNation();

			detectLang = request.getParameter("lang");

			if(detectLang.contains("-")) {
				String[] detectLangArr = detectLang.split("-");
				detectLang = detectLangArr[0];
			}


			//[추가(2019.03.27): 김태구] 답변 등록 시 똑같은 답변 여러번 등록 제한 CHECK
			HashMap<String, Object> param2 = new HashMap<String, Object>();
			param2.put("questionSeq", QuestionSeq);
			param2.put("userSeq", userSeq);
			isAnswered = commonService.getIsAnswered(param2);

			boolean confirmPost = true;
			if(Seq == "" || Seq.equals("")) {

				if(userLv != 99) {
					if(userLv >= 1 && userLv <= maxLv) {
						//[환경설정] DB 조회
						ConfigVO conf = configService.getBoardConfig();

						switch(userLv) {
							case 1:
								Lv_LimitAnsDayRegistCnt = conf.getLv1_LimitAnsDayRegistCnt();
								Lv_LimitAnsDayDupRegistCnt = conf.getLv1_LimitAnsDayDupRegistCnt();
								Lv_LimitAnsContinueRegistTime = conf.getLv1_LimitAnsContinueRegistTime();
								break;
							case 2:
								Lv_LimitAnsDayRegistCnt = conf.getLv2_LimitAnsDayRegistCnt();
								Lv_LimitAnsDayDupRegistCnt = conf.getLv2_LimitAnsDayDupRegistCnt();
								Lv_LimitAnsContinueRegistTime = conf.getLv2_LimitAnsContinueRegistTime();
								break;
							case 3:
								Lv_LimitAnsDayRegistCnt = conf.getLv3_LimitAnsDayRegistCnt();
								Lv_LimitAnsDayDupRegistCnt = conf.getLv3_LimitAnsDayDupRegistCnt();
								Lv_LimitAnsContinueRegistTime = conf.getLv3_LimitAnsContinueRegistTime();
								break;
							case 4:
								Lv_LimitAnsDayRegistCnt = conf.getLv4_LimitAnsDayRegistCnt();
								Lv_LimitAnsDayDupRegistCnt = conf.getLv4_LimitAnsDayDupRegistCnt();
								Lv_LimitAnsContinueRegistTime = conf.getLv4_LimitAnsContinueRegistTime();
								break;
							case 5:
								Lv_LimitAnsDayRegistCnt = conf.getLv5_LimitAnsDayRegistCnt();
								Lv_LimitAnsDayDupRegistCnt = conf.getLv5_LimitAnsDayDupRegistCnt();
								Lv_LimitAnsContinueRegistTime = conf.getLv5_LimitAnsContinueRegistTime();
								break;
							case 6:
								Lv_LimitAnsDayRegistCnt = conf.getLv6_LimitAnsDayRegistCnt();
								Lv_LimitAnsDayDupRegistCnt = conf.getLv6_LimitAnsDayDupRegistCnt();
								Lv_LimitAnsContinueRegistTime = conf.getLv6_LimitAnsContinueRegistTime();
								break;
							case 7:
								Lv_LimitAnsDayRegistCnt = conf.getLv7_LimitAnsDayRegistCnt();
								Lv_LimitAnsDayDupRegistCnt = conf.getLv7_LimitAnsDayDupRegistCnt();
								Lv_LimitAnsContinueRegistTime = conf.getLv7_LimitAnsContinueRegistTime();
								break;
							case 8:
								Lv_LimitAnsDayRegistCnt = conf.getLv8_LimitAnsDayRegistCnt();
								Lv_LimitAnsDayDupRegistCnt = conf.getLv8_LimitAnsDayDupRegistCnt();
								Lv_LimitAnsContinueRegistTime = conf.getLv8_LimitAnsContinueRegistTime();
								break;
							case 9:
								Lv_LimitAnsDayRegistCnt = conf.getLv9_LimitAnsDayRegistCnt();
								Lv_LimitAnsDayDupRegistCnt = conf.getLv9_LimitAnsDayDupRegistCnt();
								Lv_LimitAnsContinueRegistTime = conf.getLv9_LimitAnsContinueRegistTime();
								break;
							case 10:
								Lv_LimitAnsDayRegistCnt = conf.getLv10_LimitAnsDayRegistCnt();
								Lv_LimitAnsDayDupRegistCnt = conf.getLv10_LimitAnsDayDupRegistCnt();
								Lv_LimitAnsContinueRegistTime = conf.getLv10_LimitAnsContinueRegistTime();
								break;
							case 11:
								Lv_LimitAnsDayRegistCnt = conf.getLv11_LimitAnsDayRegistCnt();
								Lv_LimitAnsDayDupRegistCnt = conf.getLv11_LimitAnsDayDupRegistCnt();
								Lv_LimitAnsContinueRegistTime = conf.getLv11_LimitAnsContinueRegistTime();
								break;
						}

						//[추가(2019.01.17): 김태환] 본인의 글에 답변을 다는 경우 제한
						int memberSeq = commonService.getQuestionMeberSeqBySeq(Integer.parseInt(QuestionSeq));

						//[추가(2018.02.01): 김현구] 1일 기준, 답변글 등록수 제한 CHECK
						HashMap<String, Object> param = new HashMap<String, Object>();
						param.put("userSeq", userSeq);
						param.put("dateReg", dateReg);
						int answerCnt = commonService.getAnswerCntForAnswer(param);

						//[추가(2018.02.01): 김현구] 1일 기준, 동일 제목 또는 내용의 답변글 입력 제한 CHECK
						HashMap<String, Object> param3 = new HashMap<String, Object>();
						param3.put("userSeq", userSeq);
						param3.put("dateReg", dateReg);
						param3.put("contents", Contents);
						int answerCnt2 = commonService.getAnswerCnt2(param3);

						//[추가(2018.02.01): 김현구] 1일 기준, 답변글 연속 등록여부 CHECK
						String diffDateReg = commonService.getAnswerDateReg(userSeq);

						String msg2 = messageSource.getMessage("msg_1035", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
						String msg3 = messageSource.getMessage("msg_1036", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
						String msg4 = messageSource.getMessage("msg_1037", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
						String msg5 = messageSource.getMessage("msg_1038", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
						String msg6 = messageSource.getMessage("msg_1039", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
						String msg7 = messageSource.getMessage("msg_1040", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
						String msg8 = messageSource.getMessage("msg_1041", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
						String msg9 = messageSource.getMessage("msg_1042", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
						String msg10 = messageSource.getMessage("msg_1043", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

						if(memberSeq == userSeq) {
							confirmPost = false;
						}
						else if(answerCnt >= Lv_LimitAnsDayRegistCnt) {
							confirmPost = false;
							CommonUtil.jspAlert(response, msg2 + Lv_LimitAnsDayRegistCnt + msg3, "back", ""); //msg2, msg3 활용
						}
						else if(isAnswered > 0) {
							confirmPost = false;
							CommonUtil.jspAlert(response, msg4, "back", ""); //msg4 활용
						}
						else if(answerCnt2 > Lv_LimitAnsDayDupRegistCnt) {
							confirmPost = false;
							if(Lv_LimitAnsDayDupRegistCnt == 0) {
								CommonUtil.jspAlert(response, msg5 + "\\n" + msg6, "back", ""); //msg5, msg6 활용
							}
							else {
								 //msg7, msg8, msg9 활용
								CommonUtil.jspAlert(response, msg7 + Lv_LimitAnsDayDupRegistCnt + msg8, "back", "");
							}
						}
						else if(diffDateReg != null) {
							if(diffDateReg.substring(0, 10) == dateReg) {
								//이전 등록일시와 현재 등록일시 까지의 시간(초) 차이 비교
								//DateDiffSecond

								SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
								Date d1 = f.parse(diffDateReg.substring(0, 10));
								Date d2 = f.parse(dateReg);
								long diff = d1.getTime() - d2.getTime();
								DateDiffSecond = diff / 1000;
								//System.out.println("DateDiffSecond : " + DateDiffSecond);

								if(DateDiffSecond <= Long.valueOf(Lv_LimitAnsContinueRegistTime) ) {
									confirmPost = false;
									CommonUtil.jspAlert(response, msg9 + Lv_LimitAnsContinueRegistTime + msg10, "back", "");
								}
							}
						}
					}
				}

				//답변글 MAX 등록번호 조회
				int nSeq = answerService.getAnswerMaxSeq();

				if(nSeq == 0) {
					MaxSeq = 1;
				}
				else {
					//[수정(2018.03.17): 김현구] 변환함수 오류 발생해 수정함
					MaxSeq = nSeq + 1;
				}
			}
			else {
				MaxSeq = Integer.parseInt(Seq);
			}

			int boardMaxFileSeq = fileService.getBoardMaxFileSeq();
			int MaxFileSeq = 0;

			if(boardMaxFileSeq == 0) {
				MaxFileSeq = 1;
			}
			else {
				//[수정(2018.10.07): 김태환] 32783을 cInt할 때 에러가 발생하는 문제 해결
				MaxFileSeq = boardMaxFileSeq + 1;
			}

			FileMax = FileMax + 1;

			String ticketAnsChk = "";
			if(Seq == "" || Seq.equals("")) {
				if(isAnswered == 0 && confirmPost == true) {
					/*
					detectLang = CommonUtil.detectLanguage(Contents);

					if(detectLang.contains("-")) {
						String[] detectLangArr = detectLang.split("-");
						detectLang = detectLangArr[0];
					}
					*/

					HashMap<String, Object> params = new HashMap<String, Object>();
					params.put("questionSeq", QuestionSeq);
					params.put("userSeq", userSeq);
					params.put("contents", Contents);
					params.put("flagNickName", FlagNickName);
					params.put("flagUse", FlagUse);
					params.put("nation", nation);
					params.put("lang", detectLang);
					params.put("dateReg", dateReg);

					answerService.setAnswer(params);

					HashMap<String, Object> gConfig = commonService.getTicketConfig();
					int ansCountConfig = Integer.parseInt( String.valueOf( gConfig.get("ansCount") ) );
					int queCountConfig = Integer.parseInt( String.valueOf( gConfig.get("queCount") ) );

					int stackCnt = commonService.getTicketStackCnt(userSeq);
					int queCount = 0;
					int ansCount = 0;
					//int replCount = 0;

					//System.out.println("stackCnt : " + stackCnt);

					HashMap<String, Object> stackInfo = null;
					if(stackCnt > 0) {
						HashMap<String, Object> sParam = new HashMap<String, Object>();
						sParam.put("userSeq", userSeq);
						sParam.put("mode", "ans");

						// 스택 증가
						commonService.setAddTickStackUse(sParam);

						stackInfo =  commonService.getTicketStack(userSeq);
					}
					else {
						HashMap<String, Object> sParam = new HashMap<String, Object>();
						sParam.put("userSeq", userSeq);
						sParam.put("queCount", 0);
						sParam.put("ansCount", 1);
						sParam.put("replCount", 0);
						sParam.put("hunCount", 0);
						sParam.put("estiCount", 0);

						commonService.addTicketStack(sParam);
						//System.out.println("userSeq : " + userSeq);
						stackInfo =  commonService.getTicketStack(userSeq);
					}

					queCount = Integer.parseInt( String.valueOf( stackInfo.get("queCount") ) );
					ansCount = Integer.parseInt( String.valueOf( stackInfo.get("ansCount") ) );
					//replCount = Integer.parseInt( String.valueOf( stackInfo.get("replCount") ) );

					//스택 증가

					if(ansCount >= ansCountConfig && queCount >= queCountConfig) {
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)

						Calendar cal = Calendar.getInstance();
						String startDt = formatter.format(cal.getTime());

						//cal.add(Calendar.DATE, 2);
						//String endDt = formatter.format(cal.getTime());

						HashMap<String, Object> tParam = new HashMap<String, Object>();
						tParam.put("userSeq", userSeq);
						tParam.put("startDt", startDt);
						tParam.put("endDt", startDt); // 당일 23시 59분 59초 이내로 설정 변경
						tParam.put("gType", 1); // 1: 알머니, 2:사은품
						tParam.put("rType", "ans");

						// 이용권 추가
						commonService.addTicket(tParam);


						HashMap<String, Object> sParam = new HashMap<String, Object>();
						sParam.put("userSeq", userSeq);
						sParam.put("mode", "que_ans");
						sParam.put("ansCount", ansCountConfig);
						sParam.put("queCount", ansCountConfig);

						// 스택 차감
						commonService.setSubTickStackUse(sParam);

						ticketAnsChk = "Y";
					}
				}
			}
			else {
				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("contents", Contents);
				params.put("flagUse", FlagUse);
				params.put("dateReg", dateReg);
				params.put("seq", Seq);
				params.put("lang", detectLang);
				params.put("userSeq", userSeq);

				answerService.setAnswerUpdate(params);
			}

			File fileDir = new File(FilePath);

	        if (!fileDir.exists()) {
	        	fileDir.mkdirs();
	        }

			List<MultipartFile> fileList = request.getFiles("Photo");
			for(int LoopK = 0; LoopK < FileMax; LoopK++) {
				/*
				 * DefaultController uploadProfileImg 메서드 참고
				 * */
				MultipartFile file = request.getFile("File"+(LoopK+1));
				String FileName = "";
				if(file != null) {
					FileName = CommonUtil.fn_File_In(file.getOriginalFilename());

					if(FileName != "") {
						if(CommonUtil.fn_FileType_Check(FileName) == "N") {
							String msg11 = messageSource.getMessage("msg_1044", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
							CommonUtil.jspAlert(response, msg11, "back", "");// msg11 활용
						}
					}

					String inTime = new java.text.SimpleDateFormat("HHmmss").format(new java.util.Date());
					String dt = dateReg.replaceAll("-", "");
					long FileSize = file.getSize();
					String FileExtension = FilenameUtils.getExtension(FileName);

					String FileSaveName = MaxSeq + "_" + (LoopK + 1) + "_" + dt + inTime + "." + FileExtension;

					if(FileName != "") {
						File original = new File(FilePath, FileSaveName);
						file.transferTo(original);

						HashMap<String, Object> fileParam = new HashMap<String, Object>();
						fileParam.put("userSeq", userSeq);
						fileParam.put("defaultFolder", defaultFolder);
						fileParam.put("maxSeq", MaxSeq);
						fileParam.put("fileSaveName", FileSaveName);
						fileParam.put("fileSize", FileSize);

						fileService.setBoardFile(fileParam);

						MaxFileSeq = MaxFileSeq + 1;
					}
				}

			}

			if(Seq == "") {
				HashMap<String, Object> tempParam = new HashMap<String, Object>();
				tempParam.put("userSeq", userSeq);
				tempParam.put("questionSeq", QuestionSeq);

				commonService.setQuestionTempDelete(tempParam);

				PrintWriter out = response.getWriter();
				out.println("<script>parent.document.isSaved=true;</script>");
				out.flush();
			}

			if(FlagUse == "T") { //임시 저장을 새로 만들었으므로 이 곳으로 들어올 수 없다.
				PrintWriter out2 = response.getWriter();
				out2.println("<script>");
				out2.println("$('input[name=Seq]', parent.document).attr('value', " + MaxSeq + ");");
				//out2.println("alert('임시로 저장되었습니다. 임시저장된 글은 임시저장 메뉴에서 확인 가능합니다.');");
				out2.println("</script>");
				out2.flush();
			}
			else {
				CommonUtil.jspAlert(response, "", "/answer/answerList?SP=" + sp + "&Seq=" + QuestionSeq + "&CurPageName=" + CurPageName + "&Section1=" + Section1 + "&src_Sort=" + src_Sort + "&src_OrderBy=" + src_OrderBy + "&ticketAnsChk=" + ticketAnsChk, "parent.parent");
			}
		}
	}

	@RequestMapping(value="/answer/reply", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String reply(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");

		String TargetSeq = request.getParameter("TargetSeq");
		String Contents = "";
		String Flag = request.getParameter("Flag");
		String ticketReplChk = "";
		String nation = "";
		String detectLang = "";

		String msg1 = messageSource.getMessage("msg_1045", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_1046", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_1047", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_1048", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg6 = messageSource.getMessage("msg_1049", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg7 = messageSource.getMessage("msg_1050", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg8 = messageSource.getMessage("msg_1051", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg9 = messageSource.getMessage("msg_1052", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg10 = messageSource.getMessage("msg_1053", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg11 = messageSource.getMessage("msg_1054", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg12 = messageSource.getMessage("msg_1055", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg13 = messageSource.getMessage("msg_1043", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		if(request.getParameter("Contents") == "") {
			return CommonUtil.libJsonExit(msg1, null); // msg1 활용
		}
		else {
			Contents = CommonUtil.fn_Word_In(request.getParameter("Contents"));


			String format = "yyyy-MM-dd";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
			Calendar today = Calendar.getInstance();
			SimpleDateFormat type = new SimpleDateFormat(format);
			String regDate = type.format(today.getTime());

			final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
			final int userLv = cookieLoginService.getCookieUserLv(request, response);

			if(userSeq == 0) {
				//CommonUtil.jspAlert(response, "로그인후 이용 가능합니다.", "", "");
				return CommonUtil.libJsonExit(msg2, null); // msg2 활용
			}
			else {
				HashMap<String, Object> localeItem = CommonUtil.getLocale(locale);
				String lang = String.valueOf(localeItem.get("lang"));
				String sourceLang = lang;

				if(userSeq > 0) {
					MemberVO mVo = memberService.getAlmoneyMyJoin(userSeq);
					sourceLang = mVo.getLang();
					nation = mVo.getNation();
				}


				//		김주윤 20201222
				String AdminSecu = "";

				AdminSecu = commonService.getAuthority(userSeq);
				if(AdminSecu == null) {
					Contents=Contents.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
				}

				detectLang = CommonUtil.detectLanguage(Contents);
				//System.out.println("detectLang : " + detectLang);

				if(userLv != 99) {
					if(userLv >= 1 && userLv <= 11) {
						//[환경설정] DB 조회
						ConfigVO conf = configService.getBoardConfig();

						int Lv_LimitRepDayRegistCnt = 0;
						int Lv_LimitRepDayDupRegistCnt = 0;
						int Lv_LimitRepContinueRegistTime = 0;

						switch(userLv) {
							case 1:
								Lv_LimitRepDayRegistCnt = conf.getLv1_LimitRepDayRegistCnt();
								Lv_LimitRepDayDupRegistCnt = conf.getLv1_LimitRepDayDupRegistCnt();
								Lv_LimitRepContinueRegistTime = conf.getLv1_LimitRepContinueRegistTime();
								break;
							case 2:
								Lv_LimitRepDayRegistCnt = conf.getLv2_LimitRepDayRegistCnt();
								Lv_LimitRepDayDupRegistCnt = conf.getLv2_LimitRepDayDupRegistCnt();
								Lv_LimitRepContinueRegistTime = conf.getLv2_LimitRepContinueRegistTime();
								break;
							case 3:
								Lv_LimitRepDayRegistCnt = conf.getLv3_LimitRepDayRegistCnt();
								Lv_LimitRepDayDupRegistCnt = conf.getLv3_LimitRepDayDupRegistCnt();
								Lv_LimitRepContinueRegistTime = conf.getLv3_LimitRepContinueRegistTime();
								break;
							case 4:
								Lv_LimitRepDayRegistCnt = conf.getLv4_LimitRepDayRegistCnt();
								Lv_LimitRepDayDupRegistCnt = conf.getLv4_LimitRepDayDupRegistCnt();
								Lv_LimitRepContinueRegistTime = conf.getLv4_LimitRepContinueRegistTime();
								break;
							case 5:
								Lv_LimitRepDayRegistCnt = conf.getLv5_LimitRepDayRegistCnt();
								Lv_LimitRepDayDupRegistCnt = conf.getLv5_LimitRepDayDupRegistCnt();
								Lv_LimitRepContinueRegistTime = conf.getLv5_LimitRepContinueRegistTime();
								break;
							case 6:
								Lv_LimitRepDayRegistCnt = conf.getLv6_LimitRepDayRegistCnt();
								Lv_LimitRepDayDupRegistCnt = conf.getLv6_LimitRepDayDupRegistCnt();
								Lv_LimitRepContinueRegistTime = conf.getLv6_LimitRepContinueRegistTime();
								break;
							case 7:
								Lv_LimitRepDayRegistCnt = conf.getLv7_LimitRepDayRegistCnt();
								Lv_LimitRepDayDupRegistCnt = conf.getLv7_LimitRepDayDupRegistCnt();
								Lv_LimitRepContinueRegistTime = conf.getLv7_LimitRepContinueRegistTime();
								break;
							case 8:
								Lv_LimitRepDayRegistCnt = conf.getLv8_LimitRepDayRegistCnt();
								Lv_LimitRepDayDupRegistCnt = conf.getLv8_LimitRepDayDupRegistCnt();
								Lv_LimitRepContinueRegistTime = conf.getLv8_LimitRepContinueRegistTime();
								break;
							case 9:
								Lv_LimitRepDayRegistCnt = conf.getLv9_LimitRepDayRegistCnt();
								Lv_LimitRepDayDupRegistCnt = conf.getLv9_LimitRepDayDupRegistCnt();
								Lv_LimitRepContinueRegistTime = conf.getLv9_LimitRepContinueRegistTime();
								break;
							case 10:
								Lv_LimitRepDayRegistCnt = conf.getLv10_LimitRepDayRegistCnt();
								Lv_LimitRepDayDupRegistCnt = conf.getLv10_LimitRepDayDupRegistCnt();
								Lv_LimitRepContinueRegistTime = conf.getLv10_LimitRepContinueRegistTime();
								break;
							case 11:
								Lv_LimitRepDayRegistCnt = conf.getLv11_LimitRepDayRegistCnt();
								Lv_LimitRepDayDupRegistCnt = conf.getLv11_LimitRepDayDupRegistCnt();
								Lv_LimitRepContinueRegistTime = conf.getLv11_LimitRepContinueRegistTime();
								break;
						}

						//[추가(2018.02.01): 김현구] 1일 기준, 댓글 등록수 제한 CHECK
						HashMap<String, Object> param = new HashMap<String, Object>();
						param.put("regDate", regDate);
						param.put("userSeq", userSeq);

						int replyCnt1 = commonService.getUserReplyCnt(param);

						if(replyCnt1 >= Lv_LimitRepDayRegistCnt) {
							 // msg3, msg4, msg5 활용
							return CommonUtil.libJsonExit(msg3 + Lv_LimitRepDayRegistCnt + msg4 + "\\n" + msg5, null);
						}

						//[추가(2018.02.01): 김현구] 1일 기준, 동일 내용의 댓글 입력 제한 CHECK
						param.put("contents", Contents);
						int replyCnt2 = commonService.getUserReplyCnt2(param);

						if(replyCnt2 > Lv_LimitRepDayDupRegistCnt) {
							if(Lv_LimitRepDayDupRegistCnt == 0) {
								// msg6, msg7 활용
								return CommonUtil.libJsonExit(msg6 + "\\n" + msg7, null);
							}
							else {
								// msg8, msg9, msg10 활용
								return CommonUtil.libJsonExit(msg8 + Lv_LimitRepDayDupRegistCnt + msg9 + "\\n" + msg10, null);
							}
							//return "";
						}

						//[추가(2018.02.01): 김현구] 1일 기준, 댓글 연속 등록여부 CHECK
						//[수정(2019.01.02): 김태환] 컬럼 타입 변경으로 인한 계산 방식 변경 / 1일 기준은 제거
						int DateDiffSecond = 0;
						DateDiffSecond = commonService.getDateDiffSecondForReply(userSeq);

						//이전 등록일시와 현재 등록일시 까지의 시간(초) 차이 비교
						if(DateDiffSecond != 0 && DateDiffSecond <= Lv_LimitRepContinueRegistTime) {
							// msg11, msg12, msg13 활용
							return CommonUtil.libJsonExit(msg11 + "\\n" + msg12 + Lv_LimitRepContinueRegistTime + msg13, null);
						}
					}
				}
				String format2 = "yyyy-MM-dd aa hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
				Calendar today2 = Calendar.getInstance();
				SimpleDateFormat type2 = new SimpleDateFormat(format2);
				String dateReg = type2.format(today2.getTime());

				if(detectLang.contains("-")) {
					String[] detectLangArr = detectLang.split("-");
					detectLang = detectLangArr[0];
				}
				//System.out.println("detectLang : " + detectLang);
				if(detectLang.equals("und")) {
					detectLang = sourceLang;
				}

				HashMap<String, Object> replyParam = new HashMap<String, Object>();
				replyParam.put("flag", Flag);
				replyParam.put("targetSeq", TargetSeq);
				replyParam.put("userSeq", userSeq);
				replyParam.put("contents", Contents);
				replyParam.put("lang", detectLang);
				replyParam.put("dateReg", dateReg);

				replyService.setReply(replyParam);



				HashMap<String, Object> gConfig = commonService.getTicketConfig();
				int replCountConfig = Integer.parseInt( String.valueOf( gConfig.get("replCount") ) );
				int estiCountConfig = Integer.parseInt( String.valueOf( gConfig.get("estiCount") ) );

				int stackCnt = commonService.getTicketStackCnt(userSeq);
				//int queCount = 0;
				//int ansCount = 0;
				int replCount = 0;
				int estiCount = 0;

				//System.out.println("stackCnt : " + stackCnt);

				HashMap<String, Object> cParam = new HashMap<String, Object>();
				cParam.put("flag", Flag);
				cParam.put("targetSeq", TargetSeq);
				String sourceDate = commonService.getContentsDateBySeq(cParam);
				//System.out.println("sourceDate : " + sourceDate);
				boolean checkRouletteKind = CommonUtil.checkRoulettePublish(sourceDate); // true 일 경우만 stackCnt 추가
				//System.out.println("checkRouletteKind : " + checkRouletteKind);

				HashMap<String, Object> stackInfo = null;
				if(stackCnt > 0) {
					HashMap<String, Object> sParam = new HashMap<String, Object>();
					sParam.put("userSeq", userSeq);
					sParam.put("mode", "repl");

					// 스택 증가
					if(checkRouletteKind == true) {
						commonService.setAddTickStackUse(sParam);
					}

					stackInfo =  commonService.getTicketStack(userSeq);
				}
				else {
					HashMap<String, Object> sParam = new HashMap<String, Object>();
					sParam.put("userSeq", userSeq);
					sParam.put("queCount", 0);
					sParam.put("ansCount", 0);
					sParam.put("replCount", 1);
					sParam.put("hunCount", 0);
					sParam.put("estiCount", 0);

					if(checkRouletteKind == true) {
						commonService.addTicketStack(sParam);
					}
					//System.out.println("userSeq : " + userSeq);
					stackInfo =  commonService.getTicketStack(userSeq);
				}

				replCount = Integer.parseInt( String.valueOf( stackInfo.get("replCount") ) );
				estiCount = Integer.parseInt( String.valueOf( stackInfo.get("estiCount") ) );

				//스택 증가

				if(replCount >= replCountConfig && estiCount >= estiCountConfig) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)

					Calendar cal = Calendar.getInstance();
					String startDt = formatter.format(cal.getTime());

					//cal.add(Calendar.DATE, 2);
					//String endDt = formatter.format(cal.getTime());

					HashMap<String, Object> tParam = new HashMap<String, Object>();
					tParam.put("userSeq", userSeq);
					tParam.put("startDt", startDt);
					tParam.put("endDt", startDt); // 당일 23시 59분 59초 이내로 설정 변경
					tParam.put("gType", 1); // 1: 알머니, 2:사은품
					tParam.put("rType", "repl");

					// 이용권 추가
					commonService.addTicket(tParam);


					HashMap<String, Object> sParam = new HashMap<String, Object>();
					sParam.put("userSeq", userSeq);
					sParam.put("mode", "repl_esti");
					sParam.put("replCount", replCountConfig);
					sParam.put("estiCount", estiCountConfig);

					// 스택 차감
					commonService.setSubTickStackUse(sParam);

					ticketReplChk = "Y";
				}

			}
		}

		//return "<!--SUCCESS-->";

		return CommonUtil.libJsonExit("SUCCESS", ticketReplChk);
	}

	@RequestMapping(value="/answer/replydel",  method = RequestMethod.POST)
	public @ResponseBody String replydel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");

		String Flag = request.getParameter("Flag");
		String ReplySeq = request.getParameter("ReplySeq");

		HashMap<String, Object> replyParam = new HashMap<String, Object>();
		replyParam.put("flag", Flag);
		replyParam.put("replySeq", ReplySeq);

		replyService.deleteReply(replyParam);

		return "<!--SUCCESS-->";
	}

	@RequestMapping(value="/answer/siren", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String siren(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String act = request.getParameter("ACT");

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq == 0) {
			return "<!--LOGOFF-->";
		}

		List<HashMap<String ,Object>> list = new ArrayList<HashMap<String ,Object>>();

		String table = "";

		switch(request.getParameter("H_Type")) {
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
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("hType", request.getParameter("H_Type"));
			param.put("hSeq", request.getParameter("H_Seq"));
			param.put("userSeq", userSeq);

			String sType = commonService.getSirenReporter(param);
			String msg1 = messageSource.getMessage("msg_0321", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

			if(sType != null) {
				return CommonUtil.libJsonArrExit(msg1, list);// msg1 활용
			}
			else {
				return CommonUtil.libJsonArrExit(act, list);
			}
		}
		else if(act.equals("RegSiren")) {
			String msg1 = messageSource.getMessage("msg_1056", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			String msg2 = messageSource.getMessage("msg_1057", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			String msg3 = messageSource.getMessage("msg_0321", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			String msg4 = messageSource.getMessage("msg_1028", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			String msg5 = messageSource.getMessage("msg_1058", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			String msg6 = messageSource.getMessage("msg_0318", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

			if(request.getParameter("H_Reason") == "" || request.getParameter("H_Reason") == null) {
				return CommonUtil.libJsonArrExit(msg1, list);// msg1 활용
			}

			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("hType", request.getParameter("H_Type"));
			param.put("hSeq", request.getParameter("H_Seq"));
			param.put("userSeq", userSeq);
			param.put("hReason", request.getParameter("H_Reason"));
			param.put("hReasonTxt", request.getParameter("H_Reason_txt"));
			param.put("table", table);

			HashMap<String, Object> regSiren = commonService.setRegSiren(param);

			HashMap<String ,Object> result = new HashMap<String, Object>();
			switch(String.valueOf(regSiren.get("ReturnCode"))) {
				case "1":
					result.put("msg", msg2);// msg2 활용
					return CommonUtil.libJsonMsgExit(act, result);
				case "51":
					result.put("msg", msg3);// msg3 활용
					return CommonUtil.libJsonMsgExit(act, result);
				case "52":
					result.put("msg", msg4);// msg4 활용
					return CommonUtil.libJsonMsgExit(act, result);
				case "53":
					result.put("msg", msg5);// msg5 활용
					return CommonUtil.libJsonMsgExit(act, result);
				default:
					result.put("msg", msg6);// msg6 활용
					return CommonUtil.libJsonMsgExit(act, result);
			}
		}

		return "";
	}

	@RequestMapping(value="/answer/getCategoryCodeName", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String getCategoryCodeName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String code1 = request.getParameter("Code1");
		String code2 = request.getParameter("Code2");
		String code3 = request.getParameter("Code3");
		String code4 = request.getParameter("Code4");
		String code5 = request.getParameter("Code5");

		if(code1 == "" || code1 == null) code1 = "0";
		if(code2 == "" || code2 == null) code2 = "0";
		if(code3 == "" || code3 == null) code3 = "0";
		if(code4 == "" || code4 == null) code4 = "0";
		if(code5 == "" || code5 == null) code5 = "0";

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("code1", code1);
		param.put("code2", code2);
		param.put("code3", code3);
		param.put("code4", code4);
		param.put("code5", code5);

		List<SectionVO> section = commonService.getCategoryName(param);

		String result = new Gson().toJson(section);

		//System.out.println("/answer/getCategoryCodeName result : " + result);

		return result;
	}

	@RequestMapping("answer/answerEdit")
	public ModelAndView answerEdit(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		ModelAndView mav = new ModelAndView();

		String Seq = "";
		String QuestionSeq = "";
		String CurPageName = "";
		String Section1 = "";
		String src_Sort = "";
		String src_OrderBy = "";

		if(request.getParameter("Seq") == null || request.getParameter("Seq") == "null") {
			Seq = "";
		}
		else {
			Seq = CommonUtil.fn_Word_In(request.getParameter("Seq"));
		}

		if(request.getParameter("QuestionSeq") == null || request.getParameter("QuestionSeq") == "null") {
			QuestionSeq = "";
		}
		else {
			QuestionSeq = CommonUtil.fn_Word_In(request.getParameter("QuestionSeq"));
		}

		if(request.getParameter("CurPageName") == null || request.getParameter("CurPageName") == "null") {
			CurPageName = "";
		}
		else {
			CurPageName = CommonUtil.fn_Word_In(request.getParameter("CurPageName"));
		}
		if(request.getParameter("Section1") == null || request.getParameter("Section1") == "null") {
			Section1 = "";
		}
		else {
			Section1 = CommonUtil.fn_Word_In(request.getParameter("Section1"));
		}
		if(request.getParameter("src_Sort") == null || request.getParameter("src_Sort") == "null") {
			src_Sort = "";
		}
		else {
			src_Sort = CommonUtil.fn_Word_In(request.getParameter("src_Sort"));
		}
		if(request.getParameter("src_OrderBy") == null || request.getParameter("src_OrderBy") == "null") {
			src_OrderBy = "";
		}
		else {
			src_OrderBy = CommonUtil.fn_Word_In(request.getParameter("src_OrderBy"));
		}


		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		if(userSeq > 0) {
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("userLv", userLv);
			param.put("userSeq", userSeq);
			param.put("seq", Seq);

			AnswerVO answer = answerService.getAnswerView(param);
			ConfigVO config = configService.getBoardConfig();
			QuestionVO question = questionService.getQuestionDetailBySeq(Integer.parseInt(QuestionSeq));

			HashMap<String, Object> fileParam = new HashMap<String, Object>();
			fileParam.put("strData", "Answer");
			fileParam.put("seq", Seq);
			List<FileVO> files = fileService.getBoardFile(fileParam);


			mav.addObject("Answer", answer.getAnswer());
			mav.addObject("FlagNickName", answer.getFlagNickName());

			mav.addObject("WriteMax", config.getWriteMax());
			mav.addObject("FileMax", config.getFileMax());

			mav.addObject("Title", question.getTitle());
			mav.addObject("Contents", question.getContents());

			mav.addObject("files", files);
		}

		mav.addObject("Seq", Seq);
		mav.addObject("QuestionSeq", QuestionSeq);
		mav.addObject("CurPageName", CurPageName);
		mav.addObject("Section1", Section1);
		mav.addObject("src_Sort", src_Sort);
		mav.addObject("src_OrderBy", src_OrderBy);

		return mav;
	}

	@RequestMapping(value="/answer/answerSave_E", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public void answerSave_E(MultipartHttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");

		String defaultFolder = "Answer";
		UtilFile utilFile = new UtilFile();
		String FilePath = utilFile.getSaveLocation(request, defaultFolder);

		String Seq = CommonUtil.fn_Word_In(request.getParameter("Seq"));
		String QuestionSeq = CommonUtil.fn_Word_In(request.getParameter("QuestionSeq"));
		String CurPageName = CommonUtil.fn_Word_In(request.getParameter("CurPageName"));
		String Section1 = CommonUtil.fn_Word_In(request.getParameter("Section1"));
		String src_Sort = CommonUtil.fn_Word_In(request.getParameter("src_Sort"));
		String src_OrderBy = CommonUtil.fn_Word_In(request.getParameter("src_OrderBy"));

		String Contents = CommonUtil.fn_Word_In(request.getParameter("Contents"));
		String FlagNickName = CommonUtil.fn_Word_In(request.getParameter("FlagNickName"));
		String FlagUse = CommonUtil.fn_Word_In(request.getParameter("FlagUse"));

		int FileMax = Integer.parseInt(request.getParameter("FileMax"));

		if(Seq == null) { Seq = ""; }

		long DateDiffSecond = 0;

		String format = "yyyy-MM-dd";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String dateReg = type.format(today.getTime());

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		if(userSeq == 0) {
			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용
		}
		else {
			int MaxSeq = 0;

			if(Seq == "" || Seq.equals("")) {
				//답변글 MAX 등록번호 조회
				int nSeq = answerService.getAnswerMaxSeq();

				if(nSeq == 0) {
					MaxSeq = 1;
				}
				else {
					//[수정(2018.03.17): 김현구] 변환함수 오류 발생해 수정함
					MaxSeq = nSeq + 1;
				}
			}
			else {
				MaxSeq = Integer.parseInt(Seq);
			}

			int boardMaxFileSeq = fileService.getBoardMaxFileSeq();
			int MaxFileSeq = 0;

			if(boardMaxFileSeq == 0) {
				MaxFileSeq = 1;
			}
			else {
				//[수정(2018.10.07): 김태환] 32783을 cInt할 때 에러가 발생하는 문제 해결
				MaxFileSeq = boardMaxFileSeq + 1;
			}

			FileMax = FileMax + 1;
			//System.out.println("FlagNickName : " + FlagNickName);
			//System.out.println("userLv : " + global.get("UserLv"));
			//System.out.println("seq : " + Seq);
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("contents", Contents);
			params.put("flagUse", FlagUse);
			params.put("dateReg", dateReg);
			params.put("seq", Seq);
			params.put("userLv", userLv);
			params.put("flagNickName", FlagNickName);
			params.put("userSeq", userSeq);

			answerService.setAnswerUpdate(params);



			File fileDir = new File(FilePath);

	        if (!fileDir.exists()) {
	        	fileDir.mkdirs();
	        }

			List<MultipartFile> fileList = request.getFiles("Photo");
			int Erro_no = 0;
			String msg2 = messageSource.getMessage("msg_1044", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			for(int LoopK = 0; LoopK < FileMax; LoopK++) {
				/*
				 * DefaultController uploadProfileImg 메서드 참고
				 * */
				MultipartFile file = request.getFile("File"+(LoopK+1));
				String FileName = "";
				if(file != null) {
					FileName = CommonUtil.fn_File_In(file.getOriginalFilename());

					if(FileName != "") {
						if(CommonUtil.fn_FileType_Check(FileName) == "N") {
							CommonUtil.jspAlert(response, msg2, "", "");// msg2 활용
							Erro_no++;
						}
					}

					String inTime = new java.text.SimpleDateFormat("HHmmss").format(new java.util.Date());
					String dt = dateReg.replaceAll("-", "");
					long FileSize = file.getSize();
					String FileExtension = FilenameUtils.getExtension(FileName);

					String FileSaveName = MaxSeq + "_" + (LoopK + 1) + "_" + dt + inTime + "." + FileExtension;

					if(FileName != "") {
						File original = new File(FilePath, FileSaveName);
						file.transferTo(original);

						HashMap<String, Object> fileParam = new HashMap<String, Object>();
						fileParam.put("userSeq", userSeq);
						fileParam.put("defaultFolder", defaultFolder);
						fileParam.put("maxSeq", MaxSeq);
						fileParam.put("fileSaveName", FileSaveName);
						fileParam.put("fileSize", FileSize);

						fileService.setBoardFile(fileParam);

						MaxFileSeq = MaxFileSeq + 1;
					}
				}

			}

			if(Erro_no == 0) { //임시 저장을 새로 만들었으므로 이 곳으로 들어올 수 없다.
				CommonUtil.jspAlert(response, "", "/answer/answerList?Seq=" + QuestionSeq, "parent.parent");
			}
			else {
				String msg3 = messageSource.getMessage("msg_1059", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
				CommonUtil.jspAlert(response, msg3, "", "document"); // msg3 활용
			}
		}
	}

	@RequestMapping(value="/answer/answerDelete", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public void answerDelete(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");

		String AnswerSeq = request.getParameter("AnswerSeq");
		int nAnswerSeq = Integer.parseInt(AnswerSeq);

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("answerSeq", nAnswerSeq);
		AnswerVO answerChoice = answerService.getAnswerChoiceInfo(param);

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		if(userSeq > 0) {
			if(answerChoice.getMemberSeq() != userSeq && userSeq != 99) {
				return;
			}


			Long nQuestionSeq = answerChoice.getQuestionSeq();
			int nQuestionMemSeq = answerChoice.getMemberSeq();

			if(request.getParameter("Debug") != null) {
				return;
			}

			String msg1 = messageSource.getMessage("msg_1060", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			String msg2 = messageSource.getMessage("msg_0318", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			String msg3 = messageSource.getMessage("msg_1061", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

			//System.out.println("getFlagChoice : " + answerChoice.getFlagChoice());
			//[2018.01.16: 김현구] 답변채택여부 CHECK
			if(answerChoice.getFlagChoice() == "Y") {
				//[추가(2018.01.16): 김현구] 관리자여부 CHECK
				if(userLv == 99) {
					String ReturnCode = "";

					String format = "yyyy-MM-dd hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
					Calendar today = Calendar.getInstance();
					SimpleDateFormat type = new SimpleDateFormat(format);
					String now = type.format(today.getTime());

					HashMap<String, Object> updateParam = new HashMap<String, Object>();
					updateParam.put("answerSeq", nAnswerSeq);
					updateParam.put("dateReg", now);

					HashMap<String, Object> answerDelResult = answerService.setAnswerCancelOrDelete(updateParam);

					ReturnCode = String.valueOf(answerDelResult.get("RetureCode"));

					if(ReturnCode.equals("0")) {
						PrintWriter out = response.getWriter();
						out.println("<script>");
						out.println("alert('" + msg1 + "');"); // msg1 활용
						out.println("parent.location.reload();");
						out.println("</script>");
						out.flush();
					}
					else {
						PrintWriter out = response.getWriter();
						out.println("<script>");
						out.println("alert('" + msg2 + "');"); // msg2 활용
						out.println("parent.location.reload();");
						out.println("</script>");
						out.flush();
					}
				}
				else {
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('" + msg3 + "');"); // msg3 활용
					out.println("parent.location.reload();");
					out.println("</script>");
					out.flush();
				}
			}
			else {
				//[2018.01.16: 김현구] 답변 삭제 처리
				String format = "yyyy-MM-dd hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
				Calendar today = Calendar.getInstance();
				SimpleDateFormat type = new SimpleDateFormat(format);
				String now = type.format(today.getTime());

				HashMap<String, Object> updateParam = new HashMap<String, Object>();
				updateParam.put("answerSeq", nAnswerSeq);
				updateParam.put("dateReg", now);
				updateParam.put("questionSeq", nQuestionSeq);
				updateParam.put("questionMemSeq", nQuestionMemSeq);

				answerService.answerDelete(updateParam);
				//System.out.println("삭제 완료 : ");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('" + msg1 + "');"); // msg1 활용
				out.println("parent.location.reload();");
				out.println("</script>");
				out.flush();
			}
		}
	}

	@RequestMapping(value="/answer/answerEstimate", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String answerEstimate(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String result = "";

		String AnswerMemberSeq = request.getParameter("AnswerMemberSeq");
		String QuestionSeq = request.getParameter("QuestionSeq");

		if(AnswerMemberSeq == null) AnswerMemberSeq = "";
		if(QuestionSeq == null) QuestionSeq = "";

		String ticketReplChk = "N";


		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);


		if(userSeq == 0) {
			String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용

			result = "{\"returnCode\" : -1}";

			return new Gson().toJson(result);
		}
		else if( AnswerMemberSeq.equals( String.valueOf(userSeq) ) ){
			result = "{\"returnCode\" : -2}";

			return new Gson().toJson(result);
		}


		String Gubun = CommonUtil.fn_Word_In(request.getParameter("Gubun"));
		String EstimateSeq = CommonUtil.fn_Word_In(request.getParameter("EstimateSeq"));

		String Seq = "";
		String ArrayNum = "";
		if(EstimateSeq != "") {
			String[] splitStr = EstimateSeq.split("_");
			Seq = splitStr[0];
			ArrayNum = splitStr[1];
		}

		if(Gubun == "" || Seq == "") {
			result = "{\"returnCode\" : -3}";

			return new Gson().toJson(result);
		}

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("gubun", Gubun);
		param.put("ans_seq", Integer.parseInt(Seq));
		param.put("mem_seq", userSeq);
		param.put("point_count_no", Integer.parseInt(ArrayNum));
		param.put("date_reg", "");

		HashMap<String, Object> estimate = answerService.setAnswerEstimate(param);

		String returnCode = "";
		String PointCount = "";
		String returnMSG = "";


		returnCode = String.valueOf( estimate.get("ReturnCode") );
		PointCount = String.valueOf( estimate.get("PointCount") );
		returnMSG = String.valueOf( estimate.get("ReturnMsg") );


		if(returnCode.equals("0")) {
			// 답변평가 시, 오늘 게임 스택 1회 증가 - 20210225 김주윤 - 시작
			HashMap<String, Object> gConfig = commonService.getTicketConfig();
			int estiCountConfig = Integer.parseInt( String.valueOf( gConfig.get("estiCount") ) );
			int replCountConfig = Integer.parseInt( String.valueOf( gConfig.get("replCount") ) );

			int stackCnt = commonService.getTicketStackCnt(userSeq);
			//int queCount = 0;
			int estiCount = 0;
			int replCount = 0;

			//System.out.println("stackCnt : " + stackCnt);
			//System.out.println("targetSeq :" + Integer.parseInt(Seq));
			HashMap<String, Object> cParam = new HashMap<String, Object>();
			cParam.put("flag", "A");
			cParam.put("targetSeq", Integer.parseInt(Seq));
			String sourceDate = commonService.getContentsDateBySeq(cParam);

			boolean checkRouletteKind = CommonUtil.checkRoulettePublish(sourceDate); // true 일 경우만 stackCnt 추가
			//System.out.println("checkRouletteKind : " + checkRouletteKind);

			HashMap<String, Object> stackInfo = null;
			if(stackCnt > 0) {
				HashMap<String, Object> sParam = new HashMap<String, Object>();
				sParam.put("userSeq", userSeq);
				sParam.put("mode", "esti");

				// 스택 증가
				if(checkRouletteKind == true) {
					commonService.setAddTickStackUse(sParam);
				}
				stackInfo =  commonService.getTicketStack(userSeq);
			}
			else {
				HashMap<String, Object> sParam = new HashMap<String, Object>();
				sParam.put("userSeq", userSeq);
				sParam.put("queCount", 0);
				sParam.put("ansCount", 0);
				sParam.put("replCount", 0);
				sParam.put("hunCount", 0);
				sParam.put("estiCount", 1);

				if(checkRouletteKind == true) {
					commonService.addTicketStack(sParam);
				}
				//System.out.println("userSeq : " + userSeq);
				stackInfo =  commonService.getTicketStack(userSeq);
			}

			//queCount = Integer.parseInt( String.valueOf( stackInfo.get("queCount") ) );
			estiCount = Integer.parseInt( String.valueOf( stackInfo.get("estiCount") ) );
			replCount = Integer.parseInt( String.valueOf( stackInfo.get("replCount") ) );

			//스택 증가

			if(estiCount >= estiCountConfig && replCount >= replCountConfig) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)

				Calendar cal = Calendar.getInstance();
				String startDt = formatter.format(cal.getTime());

				//cal.add(Calendar.DATE, 2);
				//String endDt = formatter.format(cal.getTime());

				HashMap<String, Object> tParam = new HashMap<String, Object>();
				tParam.put("userSeq", userSeq);
				tParam.put("startDt", startDt);
				tParam.put("endDt", startDt); // 당일 23시 59분 59초 이내로 설정 변경
				tParam.put("gType", 1); // 1: 알머니, 2:사은품
				tParam.put("rType", "esti");

				// 이용권 추가
				commonService.addTicket(tParam);


				HashMap<String, Object> sParam = new HashMap<String, Object>();
				sParam.put("userSeq", userSeq);
				sParam.put("mode", "repl_esti");
				sParam.put("estiCount", estiCountConfig);
				sParam.put("replCount", replCountConfig);

				// 스택 차감
				commonService.setSubTickStackUse(sParam);

				ticketReplChk = "Y";

			}

			// 증정시 1000원 이상이라면 오늘 게임 스택 1회 증가 - 20210225 김주윤 - 종료
		}
		if(returnMSG == "") { returnMSG = "OK"; }

		result = "{\"returnCode\" : " + returnCode + ", \"PointCount\" : " + PointCount + ", \"answerSeq\" : " + Seq + ", \"estiIdx\" : " + ArrayNum + ", \"returnMSG\" : \"" + returnMSG + "\", \"ticketReplChk\" : \"" +ticketReplChk  +"\"}";



		return new Gson().toJson(result);
	}

	@RequestMapping(value="/answer/answerChoice", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public void answerChoice(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");

		String AnswerSeq = request.getParameter("AnswerSeq");
		int nAnswerSeq = Integer.parseInt(AnswerSeq);
		String MemberSeq = request.getParameter("MemberSeq");
		int answerMemberSeq = Integer.parseInt(MemberSeq);

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("ans_seq", nAnswerSeq);
		param.put("answerMemberSeq", answerMemberSeq);
		//System.out.println("nAnswerSeq: " + nAnswerSeq);
		HashMap<String, Object> choice = answerService.setAnswerChoice(param);

		//System.out.println("RETURN: " + choice.get("RETURN"));
		if(choice.get("RETURN").equals(0)) {
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("parent.location.reload();");
			out.println("</script>");
			out.flush();
		}
		else {
			String msg1 = messageSource.getMessage("msg_0320", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("parent.alert('" + msg1 + "');"); // msg1 활용
			out.println("</script>");
			out.flush();
		}
	}

	@RequestMapping(value="/answer/bestQuestionSet", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public void bestQuestionSet(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");

		String QuestionSeq = request.getParameter("QuestionSeq");
		int nQuestionSeq = Integer.parseInt(QuestionSeq);
		String BestRank = request.getParameter("BestRank");
		if(BestRank == null) {
			BestRank = "0";
		}
		int nBestRank = Integer.parseInt(BestRank);

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		String msg1 = messageSource.getMessage("msg_1062", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용


		if(userSeq > 0) {
			if(userLv != 99) {
				CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용
				return;
			}
		}
		else {
			CommonUtil.jspAlert(response, msg2, "", ""); // msg2 활용
			return;
		}

		if(nBestRank < 1) nBestRank = 1;

		String bestNumber = questionService.getBestNumberBySeq(nQuestionSeq);

		if(bestNumber == null || bestNumber.equals("0")) {
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("bestRank", nBestRank);
			param.put("questionSeq", nQuestionSeq);

			questionService.setBestChoiceMulti(param);

			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("parent.location.reload();");
			out.println("</script>");
			out.flush();
		}
		else {
			questionService.setBestChoiceZero(nQuestionSeq);

			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("parent.location.reload();");
			out.println("</script>");
			out.flush();
		}
	}

	@RequestMapping("/favoriteList")
	public ModelAndView favoriteList(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();
		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);
		// 페이징 시작
		int n_pagesize = 30;
		int n_pagescnt = 5;
		int n_curpage = 1;
		int n_totalpage = 0;
		// 페이징 종료

		String sortCode = "";
		String s_tmp = "";
		int n_trec = 0;

		String sectionCodeParameter = "";

		String CurPageName = "/answer/favoriteList";
		String req_TREC = request.getParameter("trec");
		if(req_TREC == null) req_TREC = "0";
		String req_PG = request.getParameter("pg");
		if(req_PG == null) req_PG = "1";
		String src_Sort = request.getParameter("src_Sort");
		if(src_Sort == null) src_Sort = "";
		String src_OrderBy = request.getParameter("src_OrderBy");
		if(src_OrderBy == null) src_OrderBy = "";
		String Section1 = request.getParameter("Section1");
		if(Section1 == null) Section1 = "";
		String Section2 = request.getParameter("Section2");
		if(Section2 == null) Section2 = "";
		String Section3 = request.getParameter("Section3");
		if(Section3 == null) Section3 = "";
		String Section4 = request.getParameter("Section4");
		if(Section4 == null) Section4 = "";
		String Section5 = request.getParameter("Section5");
		if(Section5 == null) Section5 = "";

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");

		String format = "yyyy-MM-dd HH:mm:ss";
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String now = type.format(today.getTime());

		if(userSeq > 0) {

			if(request.getParameter("CallAlarmFlag")!=null && request.getParameter("CallAlarmFlag").equals("Y")) {
				CodeUtil code = new CodeUtil(request);
				String codeMemAlarmViewFile = code.getCODE_MEM_ALARM_VIEW_FIELD_CD_FAVORITE_QUE_REGIST();

				HashMap<String, Object> alarmParam = new HashMap<String, Object>();
				alarmParam.put("mem_seq", userSeq);
				alarmParam.put("alarm", codeMemAlarmViewFile);
				alarmParam.put("dateReg", now);

				commonService.setAlarmLog(alarmParam);

			}

			for(int i = 1; i <= 5; i++) {
				String sectionCode = request.getParameter("SectionCode" + i);
				if(sectionCode == "" || sectionCode == null) {
					sectionCode = "0";
				}
				if(sectionCodeParameter == "") {
					sectionCodeParameter += sectionCode;
				}
				else {
					sectionCodeParameter += "," + sectionCode;
				}
			}
		}

		if(sectionCodeParameter.equals("0,0,0,0,0")) {
			String forcedSelect1th = "";

			List<HashMap<String, Object>> myFavoriteCategory = commonService.getMyFavoriteCategory(userSeq);

			if(myFavoriteCategory.size() > 0) {
				forcedSelect1th = "Y";

				sectionCodeParameter = "";
				sectionCodeParameter += myFavoriteCategory.get(0).get("SectionCode1").toString() + ",";
				sectionCodeParameter += myFavoriteCategory.get(0).get("SectionCode2").toString() + ",";
				sectionCodeParameter += myFavoriteCategory.get(0).get("SectionCode3").toString() + ",";
				sectionCodeParameter += myFavoriteCategory.get(0).get("SectionCode4").toString() + ",";
				sectionCodeParameter += myFavoriteCategory.get(0).get("SectionCode5").toString();
			}
		}


		if(Section1 == "") Section1 = "0";
		if(src_Sort == "") src_Sort = "DateReg";
		if(src_OrderBy == "") src_OrderBy = "DESC";


		if(src_Sort.equals("AnswerCount")) {
			sortCode = "1";
		}
		else {
			sortCode = "0";
		}


		s_tmp = req_TREC;

		if(s_tmp != null && CommonUtil.isNumeric(s_tmp) == true && s_tmp.length() > 0) {
			n_trec = Integer.parseInt(s_tmp);
		}

		int nSortCode = Integer.parseInt(sortCode);
		int nReq_PG = Integer.parseInt(req_PG);
		//System.out.println("sectionCodeParameter : " + sectionCodeParameter);
		String[] sectionArr = sectionCodeParameter.split(",");


		HashMap<String, Object> localeItem = CommonUtil.getLocale(locale);
		String lang = String.valueOf(localeItem.get("lang"));
		String sourceLang = lang;
		if(userSeq > 0) {
			MemberVO mVo = memberService.getAlmoneyMyJoin(userSeq);
			sourceLang = mVo.getLang();
		}

		String targetLang = request.getParameter("targetLang");
		if(targetLang == null) { targetLang = ""; }
		String langString = CommonUtil.getLanguageString(sourceLang, targetLang);
		String langSel = CommonUtil.getLanguageSelString(sourceLang, targetLang);


		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("call_flag", 1);
		param.put("order_flag", nSortCode);
		param.put("lang", targetLang);
		param.put("page_num", nReq_PG);
		param.put("page_size", n_pagesize);

		for(int j = 0; j < sectionArr.length; j++) {
			param.put("code"+(j+1), sectionArr[j]);
		}


		int qCount = answerService.getQuestionCount(param);

		n_trec = qCount;

		n_curpage = nReq_PG;

		s_tmp = req_PG;

		if(CommonUtil.isNumeric(s_tmp) == true && s_tmp.length() > 0) {
			n_curpage = Integer.parseInt(s_tmp);
		}
		n_totalpage = n_trec / n_pagesize;

		if(n_trec % n_pagesize > 0) {
			n_totalpage = n_totalpage + 1;
		}

		if(n_curpage <= 0) {
			n_curpage = 1;
		}
		else if(n_curpage > n_totalpage) {
			n_curpage = n_totalpage;
		}

		HashMap<String, Object> param2 = new HashMap<String, Object>();
		param2.put("call_flag", 0);
		param2.put("order_flag", nSortCode);
		param2.put("lang", targetLang);
		param2.put("page_num", nReq_PG);
		param2.put("page_size", n_pagesize);

		for(int j = 0; j < sectionArr.length; j++) {
			int k = j + 1;
			param2.put("code"+(k), sectionArr[j]);
		}

		List<QuestionVO> list = answerService.getSpQuestionList(param2);

		Section1 = String.valueOf( param2.get("code1") );
		Section2 = String.valueOf( param2.get("code2") );
		Section3 = String.valueOf( param2.get("code3") );
		Section4 = String.valueOf( param2.get("code4") );
		Section5 = String.valueOf( param2.get("code5") );

		String url = CurPageName + "?trec=" + req_TREC + "&src_Sort=" + src_Sort + "&src_OrderBy=" + src_OrderBy + "&SectionCode1=" + Section1 + "&SectionCode2=" + Section2 + "&SectionCode3=" + Section3 + "&SectionCode4=" + Section4 + "&SectionCode5=" + Section5 + "&targetLang=" + targetLang;
		String pageStr = CommonUtil.get_Paging_Tag_New_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);

		mav.addObject("langString", langString);
		mav.addObject("langSel", langSel);
		mav.addObject("targetLang", targetLang);
		mav.addObject("CurPageName", CurPageName);
		mav.addObject("src_Sort", src_Sort);
		mav.addObject("src_OrderBy", src_OrderBy);
		mav.addObject("SectionCode1", Section1);
		mav.addObject("SectionCode2", Section2);
		mav.addObject("SectionCode3", Section3);
		mav.addObject("SectionCode4", Section4);
		mav.addObject("SectionCode5", Section5);
		mav.addObject("n_pagesize", n_pagesize);
		mav.addObject("n_pagescnt", n_pagescnt);
		mav.addObject("n_curpage", n_curpage);
		mav.addObject("n_totalpage", n_totalpage);

		mav.addObject("userLv", userLv);
		mav.addObject("userSeq", userSeq);

		mav.addObject("pageStr", pageStr);
		mav.addObject("trec", req_TREC);

		mav.addObject("list", list);
		mav.addObject("listCount", list.size());
		mav.addObject("sourceLang", sourceLang);

		return mav;
	}

	@RequestMapping(value="/answer/answerView", method = RequestMethod.GET)
	public ModelAndView answerView(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);
		String userNick = "";
		String userFlagSelfAnswer = "";
		String userMemberType = "";

		if(userSeq > 0) {
			userFlagSelfAnswer = cookieLoginService.getCookieFlagSelfAnswer(request, response);
			userMemberType = String.valueOf( cookieLoginService.getCookieMemberType(request, response) );
			userNick = cookieLoginService.getCookieUserNickName(request, response);
		}

		String eventSeq = CommonUtil.fn_Word_In( request.getParameter("EventSeq") );
		String answerSeq = CommonUtil.fn_Word_In( request.getParameter("AnswerSeq") );
		String questionSeq = CommonUtil.fn_Word_In( request.getParameter("QuestionSeq") );
		String curPageName = CommonUtil.fn_Word_In( request.getParameter("CurPageName") );

		String section1 = CommonUtil.fn_Word_In( request.getParameter("Section1") );
		String src_Sort = CommonUtil.fn_Word_In( request.getParameter("src_Sort") );
		String src_OrderBy = CommonUtil.fn_Word_In( request.getParameter("src_OrderBy") );
		String pointRank = CommonUtil.fn_Word_In( request.getParameter("PointRank") );


		QuestionVO q = questionService.getGetQuestionSP3(questionSeq);




		String flagUse = q.getFlagUse();
		String msg1 = messageSource.getMessage("msg_1063", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		if(!flagUse.equals("Y")) {
			CommonUtil.jspAlert(response, msg1, "back", "");// msg1 활용
			return null;
		}

		int memberSeq = q.getMemberSeq();
		String title = q.getTitle();
		String contents = q.getContents();
		BigDecimal almoney = q.getThankAlmoney();
		String flagNickNameQ = q.getFlagNickName();
		int readCount = q.getReadCount() + q.getReadCount_Answ();
		String dateReg = q.getConDate();
		String nickName = q.getNickName();
		int lv = q.getLv();
		String photo = q.getPhoto();
		String intro = q.getIntro();
		BigDecimal questionMoney = q.getQ_almoney();
		BigDecimal answerMoney = q.getA_almoney();
		String flagChoiceQ = q.getFlagChoice();
		String strLv = "";
		int countC = 0;
		int countN = 0;
		BigDecimal rateChoice = BigDecimal.ZERO;
		int countQuestion = 0;
		int pointCountNo = 0;
		String pointCount6_Yn = "";

		if(userLv != 99) {
			flagChoiceQ = "Y";
		}
		//System.out.println("flagChoiceQ : " + flagChoiceQ);
		String msg2 = messageSource.getMessage("msg_1018", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0236", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		if(flagChoiceQ.equals("N")) {
			if(memberSeq == userSeq) {
				nickName = nickName + msg2; // msg2 활용
			}
			else {
				nickName = msg3; // msg3 활용
			}

			photo = "";
			strLv = msg3; // msg3 활용
			intro = "";
			questionMoney = BigDecimal.ZERO;
			answerMoney = BigDecimal.ZERO;
			countC = 0;
			rateChoice = BigDecimal.ZERO;
		}
		else {
			strLv = CommonUtil.getLevelName(lv, request);

			HashMap<String, Object> ansQueChoice = answerService.getCountCAndCountN(memberSeq);

			countC = Integer.parseInt( String.valueOf( ansQueChoice.get("CountC") ) );
			countN = Integer.parseInt( String.valueOf( ansQueChoice.get("CountN") ) );

			countQuestion = countC + countN;

			if(countC > 0) {
				rateChoice = new BigDecimal(( countC / (double)countQuestion ) * 100);
			}
		}

		if(photo.equals("")) {
			photo = "img_thum_base0.jpg";
		}

		if(request.getParameter("PointCountNo") != null) {
			pointCountNo = Integer.parseInt( request.getParameter("PointCountNo") );
		}

		if(request.getParameter("PointCount6_Yn") != null) {
			pointCount6_Yn = request.getParameter("PointCount6_Yn");
		}

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("strData1", questionSeq);
		param.put("strData2", "Question");

		List<FileVO> fList = fileService.getFile(param);


		HashMap<String, Object> param2 = new HashMap<String, Object>();
		param2.put("targetSeq", questionSeq);
		param2.put("flag", "Q");

		int replyCnt = replyService.getAnswerSumView(param2);

		int answerCnt = answerService.getAnswerCnt(Integer.parseInt(questionSeq));

		List<ReplyVO> replyList = replyService.getReplyListOrg(param2);


		//getQuestionAnswerView
		AnswerVO answerView = answerService.getQuestionAnswerView(Integer.parseInt(answerSeq));

		//answerView file
		HashMap<String, Object> fParam = new HashMap<String, Object>();
		fParam.put("strData1", answerSeq);
		fParam.put("strData2", "Answer");
		List<FileVO> ansFileList = fileService.getFile(fParam);

		HashMap<String, Object> aParam = new HashMap<String, Object>();
		aParam.put("targetSeq", answerSeq);
		aParam.put("flag", "A");
		int answerReplysum  = replyService.getAnswerSumView(aParam);

		HashMap<String, Object> param3 = new HashMap<String, Object>();
		param3.put("answerSeq", answerSeq);
		param3.put("questionSeq", questionSeq);

		if(userSeq != 0) {
			param3.put("userSeq", userSeq);
			commonService.setLogViewByMultiParam(param3);
		}
		else {
			answerService.setAnswerReadUpdate(param3);
		}

		MemberVO mInfo = memberService.getAnswerMemberInfo(answerView.getMemberSeq());

		HashMap<String, Object> cParam = new HashMap<String, Object>();
		cParam.put("strData1", "T_ANSWER");
		cParam.put("userSeq", answerView.getMemberSeq());
		ChoiceVO choice = commonService.getAnswerQuestionChoice(cParam);

		HashMap<String, Object> param4 = new HashMap<String, Object>();
		param4.put("flag", "A");
		param4.put("targetSeq", answerSeq);
		List<ReplyVO> ansReplList = replyService.getReplyListOrg(param4);


		mav.addObject("userSeq", userSeq);
		mav.addObject("userFlagSelfAnswer", userFlagSelfAnswer);
		mav.addObject("userMemberType", userMemberType);
		mav.addObject("userNick", userNick);
		mav.addObject("userLv", userLv);
		mav.addObject("memberSeq", memberSeq);
		mav.addObject("flagUse", flagUse);
		mav.addObject("eventSeq", eventSeq);
		mav.addObject("answerSeq", answerSeq);
		mav.addObject("questionSeq", questionSeq);
		mav.addObject("curPageName", curPageName);
		mav.addObject("section1", section1);
		mav.addObject("src_Sort", src_Sort);
		mav.addObject("src_OrderBy", src_OrderBy);
		mav.addObject("pointRank", pointRank);
		mav.addObject("title", title);
		mav.addObject("contents", contents);
		mav.addObject("almoney", almoney);
		mav.addObject("flagNickNameQ", flagNickNameQ);
		mav.addObject("readCount", readCount);
		mav.addObject("dateReg", dateReg);
		mav.addObject("nickName", nickName);
		mav.addObject("lv", strLv);
		mav.addObject("photo", photo);
		mav.addObject("intro", intro);
		mav.addObject("questionMoney", questionMoney);
		mav.addObject("answerMoney", answerMoney);
		mav.addObject("flagChoiceQ", flagChoiceQ);
		mav.addObject("countC", countC);
		mav.addObject("countN", countN);
		mav.addObject("rateChoice", rateChoice);
		mav.addObject("countQuestion", countQuestion);
		mav.addObject("fList", fList);
		mav.addObject("replyCnt", replyCnt);
		mav.addObject("answerCnt", answerCnt);
		mav.addObject("replyList", replyList);
		mav.addObject("answerView", answerView);
		mav.addObject("ansFileList", ansFileList);
		mav.addObject("answerReplysum", answerReplysum);
		mav.addObject("pointCountNo", pointCountNo);
		mav.addObject("pointCount6_Yn", pointCount6_Yn);
		mav.addObject("mInfo", mInfo);
		mav.addObject("choice", choice);
		mav.addObject("ansReplList", ansReplList);

		return mav;
	}
}
