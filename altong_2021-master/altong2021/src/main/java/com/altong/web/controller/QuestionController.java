package com.altong.web.controller;

import java.io.File;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.altong.web.logic.V2RankVO;
import com.altong.web.logic.config.ConfigVO;
import com.altong.web.logic.files.FileVO;
import com.altong.web.logic.member.MemberVO;
import com.altong.web.logic.question.QuestionVO;
import com.altong.web.logic.util.CommonUtil;
import com.altong.web.logic.util.UtilFile;
import com.altong.web.service.CookieLoginService;
import com.altong.web.service.answer.AnswerService;
import com.altong.web.service.common.CommonService;
import com.altong.web.service.config.ConfigService;
import com.altong.web.service.file.FileService;
import com.altong.web.service.member.MemberService;
import com.altong.web.service.question.QuestionService;

import jasp.vbs.vb;

@Controller
@RequestMapping("question/*")
public class QuestionController {
	@Autowired
	CommonService commonService;

	@Autowired
	QuestionService questionService;

	@Autowired
	FileService fileService;

	@Autowired
	ConfigService configService;

	@Autowired
	MemberService memberService;

	@Autowired
	AnswerService answerService;

	@Autowired
	CookieLocaleResolver localResolver;

	@Autowired
	MessageSource messageSource;

	@Autowired
	CookieLoginService cookieLoginService;

	@RequestMapping("question/bestList")
	public ModelAndView bestList(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		ModelAndView mav = new ModelAndView();

		String curPageName = "/question/bestList";
		String req_PG = request.getParameter("pg");

		int pg = 0;
		if(req_PG == "" || req_PG == null) { pg = 1; }
		else {
			pg = Integer.parseInt(req_PG);
		}


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


		HashMap<String, Object> cParam = new HashMap<String, Object>();
		cParam.put("lang", targetLang);

		int questionBestCnt = questionService.getQuestionBestCnt(cParam);


		int n_pagesize = 20;
		int n_pagescnt = 5;
		int n_curpage = 1;
		int n_totalpage = 0;

		n_curpage = pg;

		//List<QuestionVO>  questionBestList = bestListCommon(pg);
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("lang", targetLang);
		param.put("pg", n_curpage);
		param.put("maxRow", n_pagesize);

		List<QuestionVO> questionBestList = questionService.getQestionBestList(param);


		int st_num = (n_pagesize) * (n_curpage - 1) + 1;
		int en_num = (n_pagesize) * (n_curpage);

		int totalCnt = questionService.getQestionBestCount();
		n_totalpage =  -vb.CInt( -(totalCnt / n_pagesize) );

		//String url = curPageName + "?trec=" + req_TREC + "&src_Sort=" + src_Sort;
		String url = curPageName + "?src_Sort=&targetLang=" + targetLang;
		String pageStr = CommonUtil.get_Paging_Tag_New_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);


		mav.addObject("questionBestList", questionBestList);
		mav.addObject("questionBestCnt", questionBestCnt);
		mav.addObject("pg", n_curpage);
		mav.addObject("paging", pageStr);
		mav.addObject("n_totalpage", n_totalpage);
		mav.addObject("langString", langString);
		mav.addObject("langSel", langSel);
		mav.addObject("userLv", userLv);
		mav.addObject("userSeq", userSeq);
		mav.addObject("sourceLang", sourceLang);

		return mav;
	}

	@RequestMapping(value="question/bestListAjax", produces="application/json;charset=UTF-8", method = RequestMethod.GET)
	public @ResponseBody String bestListAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int pg = 1;

		if(request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}

		List<QuestionVO>  questionBestList = bestListCommon(pg);
		//System.out.println("questionBestList.size() : "  + questionBestList.size());

		String result = CommonUtil.libJsonArrExit("SUCCESS", questionBestList);
		//System.out.println(" bestListAjax result : "  + result);
		return result;
	}

	public List<QuestionVO> bestListCommon(int pg) throws Exception {
		int maxRow = 20;

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("pg", pg);
		param.put("maxRow", maxRow);

		List<QuestionVO> questionBestList = questionService.getQestionBestList(param);
		//json data 처리
		//libJsonExit('SUCCESS', $r);

		return questionBestList;
	}


	//질문 -> 이벤트 리스트
	@RequestMapping("question/eventList")
	public ModelAndView eventList(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		ModelAndView mav = new ModelAndView();

		String status = request.getParameter("Status");
		String curPageName = "/question/eventList";
		String req_PG = request.getParameter("pg");

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

		int pg = 0;
		if(req_PG == "" || req_PG == null) { pg = 1; }
		else {
			pg = Integer.parseInt(req_PG);
		}

		if(status == null){
			status = "1";
		}

		// eventList count
		HashMap<String, Object> eParam = new HashMap<String, Object>();
		eParam.put("lang", targetLang);
		eParam.put("status", status);
		int totalCnt = questionService.getEventCount(eParam);

		int n_pagesize = 20;
		int n_pagescnt = 5;
		int n_curpage = 1;
		int n_totalpage = 0;

		n_curpage = pg;

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("lang", targetLang);
		param.put("pg", n_curpage);
		param.put("maxRow", n_pagesize);
		param.put("status", status);

		// 파라미터 방식으로 수정 예정
		List<QuestionVO>  eventList = questionService.getEventList(param);

		int st_num = (n_pagesize) * (n_curpage - 1) + 1;
		int en_num = (n_pagesize) * (n_curpage);

		n_totalpage =  -vb.CInt( -(totalCnt / n_pagesize) );


		String url = curPageName + "?Status=" + status + "&targetLang=" + targetLang;
		String pageStr = CommonUtil.get_Paging_Tag_New_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);

		//System.out.println("status :: "  + status);
		mav.addObject("langString", langString);
		mav.addObject("langSel", langSel);
		mav.addObject("targetLang", targetLang);
		mav.addObject("eventList", eventList);
		mav.addObject("Status", status);
		mav.addObject("paging", pageStr);
		mav.addObject("totalCnt", totalCnt);
		mav.addObject("n_totalpage", n_totalpage);
		mav.addObject("userLv", userLv);
		mav.addObject("userSeq", userSeq);
		mav.addObject("sourceLang", sourceLang);

		return mav;
	}


	@RequestMapping("/event/interviewList")
	public ModelAndView interviewList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		String curPageName = "/question/event/interviewList";
		String req_PG = request.getParameter("pg");

		int pg = 0;
		if(req_PG == "" || req_PG == null) { pg = 1; }
		else {
			pg = Integer.parseInt(req_PG);
		}

		// interview count
		int totalCnt = questionService.getInterviewCount();

		int n_pagesize = 20;
		int n_pagescnt = 5;
		int n_curpage = 1;
		int n_totalpage = 0;

		n_curpage = pg;

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("pg", n_curpage);
		param.put("maxRow", n_pagesize);

		List<QuestionVO> interViewList = questionService.getInterviewList(param);

		int st_num = (n_pagesize) * (n_curpage - 1) + 1;
		int en_num = (n_pagesize) * (n_curpage);

		n_totalpage =  -vb.CInt( -(totalCnt / n_pagesize) );

		String url = curPageName + "?Status=";
		String pageStr = CommonUtil.get_Paging_Tag_New_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);

		mav.addObject("userSeq", userSeq);
		mav.addObject("interViewList", interViewList);
		mav.addObject("paging", pageStr);
		mav.addObject("totalCnt", totalCnt);
		mav.addObject("n_totalpage", n_totalpage);

		return mav;
	}


	@RequestMapping("/event/interView")
	public ModelAndView interView(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		ModelAndView mav = new ModelAndView();

		if(request.getParameter("idx") == null || request.getParameter("idx") == null || request.getParameter("idx") == "") {
			String msg1 = messageSource.getMessage("msg_1019", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용
			return null;
		}
		else {
			int idx = Integer.parseInt(request.getParameter("idx"));

			QuestionVO interview = questionService.getInterview(idx);
			questionService.plusPageView(idx);

			mav.addObject("idx", idx);
			mav.addObject("interview", interview);
		}
		return mav;
	}

   @RequestMapping("/rankQuestion")
   public ModelAndView rankQuestion(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
	   ModelAndView mav = new ModelAndView();


	   String CurPageName = "/question/rankQuestion";
	   String req_TREC = request.getParameter("trec");
	   String req_PG = request.getParameter("pg");
	   String src_Sort = request.getParameter("src_Sort");

	   int nReqPG = 0;
	   if(req_PG == "" || req_PG == null) { nReqPG = 1; }
	   else nReqPG = Integer.parseInt(req_PG);

	   if(src_Sort == "" || src_Sort == null) { src_Sort = "ReadAlmoney"; }

	   int n_RsCnt = 0;
	   int n_pagesize = 25;
	   int n_pagescnt = 5;
	   int n_curpage = 1;
	   int n_totalpage = 0;

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
	   //System.out.println("targetLang : " + targetLang);

	   n_curpage = nReqPG;

	   int st_num = (n_pagesize) * (n_curpage - 1) + 1;
	   int en_num = (n_pagesize) * (n_curpage);

	   HashMap<String, Object> rParam = new HashMap<String, Object>();
	   rParam.put("lang", targetLang);
	   int totalCnt = questionService.getRankCount(rParam);
	   n_totalpage =  -vb.CInt( -(totalCnt / n_pagesize) );

	   String url = CurPageName + "?trec=" + req_TREC + "&src_Sort=" + src_Sort + "&targetLang=" + targetLang;
	   String pageStr = CommonUtil.get_Paging_Tag_New_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);

	   List<V2RankVO> rankList = new ArrayList<V2RankVO>();
	   if(totalCnt > 0) {
		   HashMap<String, Object> param = new HashMap<String, Object>();
		   param.put("lang", targetLang);
		   param.put("psrc_Cond", src_Sort);
		   param.put("st_num", st_num);
		   param.put("en_num", en_num);

		   rankList = questionService.getRankList(param);

		   n_RsCnt = rankList.size();
	   }


	   mav.addObject("langString", langString);
	   mav.addObject("langSel", langSel);
	   mav.addObject("targetLang", targetLang);
	   mav.addObject("rankList",rankList);
	   mav.addObject("n_RsCnt", n_RsCnt);

	   mav.addObject("n_pagescnt", n_pagescnt);
	   mav.addObject("n_curpage", n_curpage);
	   mav.addObject("n_totalpage", n_totalpage);

	   mav.addObject("CurPageName", CurPageName);
	   mav.addObject("src_Sort", src_Sort);
	   mav.addObject("pg", req_PG);
	   mav.addObject("trec", req_TREC);

	   mav.addObject("userLv", userLv);
	   mav.addObject("userSeq", userSeq);

	   mav.addObject("pageStr", pageStr);
	   mav.addObject("sourceLang", sourceLang);

	   return mav;
   }

   @RequestMapping("/rankUser")
   public ModelAndView rankUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
	   ModelAndView mav = new ModelAndView();


	   String CurPageName = "/question/rankUser";
	   String flagOption = request.getParameter("FlagOption");
	   String req_TREC = CommonUtil.fn_Word_In(request.getParameter("trec"));
	   String req_PG = CommonUtil.fn_Word_In(request.getParameter("pg"));
	   String src_Sort = CommonUtil.fn_Word_In(request.getParameter("src_Sort"));
	   String section1 = CommonUtil.fn_Word_In(request.getParameter("Section1"));
	   final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

	   int nReqPG = 0;
	   if(req_PG == "" || req_PG == null) { nReqPG = 1; }
	   else nReqPG = Integer.parseInt(req_PG);

	   if(src_Sort == "" || src_Sort == null) { src_Sort = "ReadAlmoney"; }

	   int n_RsCnt = 0;
	   int n_pagesize = 30;
	   int n_pagescnt = 5;
	   int n_curpage = 1;
	   int n_totalpage = 0;

	   n_curpage = nReqPG;

	   if(section1 == "" || section1 == null) {
		   section1 = "0";
	   }

	   String flag2 = "";
	   if(flagOption.equals("Money")) {
		   flag2 = "SumQ";
	   }
	   else {
		   flag2 = "CountQ";
	   }

	   int st_num = (n_pagesize) * (n_curpage - 1) + 1;
	   int en_num = (n_pagesize) * (n_curpage);

	   int totalMember = memberService.getTotalMember();
	   if(totalMember > 0) { totalMember = 1000; }

	   n_totalpage =  -vb.CInt( -(totalMember / n_pagesize) );

	   String url = CurPageName + "?FlagOption=" + flagOption + "&trec=" + req_TREC + "&src_Sort=" + src_Sort + "&Section1=" + section1;
	   String pageStr = CommonUtil.get_Paging_Tag_New_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);

	   List<MemberVO> rankList = new ArrayList<MemberVO>();
	   if(totalMember > 0) {
		   HashMap<String, Object> param = new HashMap<String, Object>();
		   param.put("flag2", flag2);
		   param.put("st_num", st_num);
		   param.put("en_num", en_num);

		   if(flagOption.equals("Money")) {
			   rankList = memberService.getUserRankingSum(param);
		   }else {
			   rankList = memberService.getUserRanking(param);
		   }


		   n_RsCnt = rankList.size();
	   }


	   mav.addObject("userSeq", userSeq);
	   mav.addObject("rankList",rankList);
	   mav.addObject("n_RsCnt", n_RsCnt);
	   mav.addObject("flagOption", flagOption);
	   mav.addObject("flag2", flag2);
	   mav.addObject("section1", section1);

	   mav.addObject("n_pagescnt", n_pagescnt);
	   mav.addObject("n_curpage", n_curpage);
	   mav.addObject("n_totalpage", n_totalpage);

	   mav.addObject("curPageName", CurPageName);
	   mav.addObject("src_Sort", src_Sort);
	   mav.addObject("pg", req_PG);
	   mav.addObject("trec", req_TREC);

	   mav.addObject("paging", pageStr);

	   return mav;
   }

   @RequestMapping("question/questionWrite")
   public ModelAndView questionWrite(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		ModelAndView mav = new ModelAndView();

		String sp = request.getParameter("SP");
		if(sp == null) { sp = ""; }
		String seq = request.getParameter("QuestionSeq");
		if(seq == null) { seq = ""; }
		Integer section1 = 0;
		String title = "";
		String contents = "";
		String questionBet = "0";
		String flagNickName = "";
		String flagMinor = "";
		List<FileVO> files = new ArrayList<FileVO>();
		int questionMin = 0;
		int questionMax = 0;
		int writeMax = 0;
		int fileMax = 0;
		int flagNickPrice = 0;
		String[] CODE_ANSWER_ALMONEY;
		String userQueRegAlmoney = "";

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		String msg1 = messageSource.getMessage("msg_1019", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1149", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		QuestionVO question = new QuestionVO();
		String sourceLang = "";
		if(userSeq > 0) {
			MemberVO mVo = memberService.getAlmoneyMyJoin(userSeq);
			sourceLang = mVo.getLang();

			if(seq != "" && seq != null) {
				String admin = userLv == 99 ? "1=1 or" : "";

				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("questionSeq", seq);
				param.put("admin", admin);
				param.put("userSeq", userSeq);

				question = questionService.getQuestionInfoBySeq(param);

				files = fileService.getQuestionFile(param);

				if(question == null) {
					CommonUtil.jspAlert(response, msg1, "back", "parent.parent"); // msg1 활용
				}
				else {
					section1 = question.getSection1();
					title = question.getTitle();
					contents = question.getContents();
					questionBet = String.valueOf(question.getAlmoney().intValue());
					flagNickName = question.getFlagNickName();
					flagMinor = question.getFlagMinor();
					flagNickPrice = question.getFlagNickPrice();
					sourceLang = question.getLang();
				}
			}

			ConfigVO config = configService.getConfigList();


			switch( userLv ) {
				case 1:
					userQueRegAlmoney = config.getLv1_QueRegAlmoney();
					break;
				case 2:
					userQueRegAlmoney = config.getLv2_QueRegAlmoney();
					break;
				case 3:
					userQueRegAlmoney = config.getLv3_QueRegAlmoney();
					break;
				case 4:
					userQueRegAlmoney = config.getLv4_QueRegAlmoney();
					break;
				case 5:
					userQueRegAlmoney = config.getLv5_QueRegAlmoney();
					break;
				case 6:
					userQueRegAlmoney = config.getLv6_QueRegAlmoney();
					break;
				case 7:
					userQueRegAlmoney = config.getLv7_QueRegAlmoney();
					break;
				case 8:
					userQueRegAlmoney = config.getLv8_QueRegAlmoney();
					break;
				case 9:
					userQueRegAlmoney = config.getLv9_QueRegAlmoney();
					break;
				case 10:
					userQueRegAlmoney = config.getLv10_QueRegAlmoney();
					break;
				case 11:
					userQueRegAlmoney = config.getLv11_QueRegAlmoney();
					break;
				default:
					userQueRegAlmoney = config.getLv1_QueRegAlmoney();
			}

			CODE_ANSWER_ALMONEY = userQueRegAlmoney.split("/");

			if(CODE_ANSWER_ALMONEY[0] == null) {
				CommonUtil.jspAlert(response, msg2, "back", "parent.parent"); // msg2, msg3 활용 --> msg2만 활용
				return null;
			}

			questionMin = config.getQuestionMin();
			questionMax = config.getQuestionMax();
			writeMax = config.getWriteMax();
			fileMax = config.getFileMax();
			if(questionBet == null || questionBet == "") questionBet = String.valueOf(questionMin);
			mav.addObject("SP", sp);
			mav.addObject("Seq", seq);
			mav.addObject("Section1", section1);
			mav.addObject("title", title);
			mav.addObject("contents", contents);
			mav.addObject("questionBet", Integer.parseInt(questionBet));
			mav.addObject("flagNickName", flagNickName);
			mav.addObject("flagMinor", flagMinor);
			mav.addObject("files", files);
			mav.addObject("questionMin", questionMin);
			mav.addObject("questionMax", questionMax);
			mav.addObject("writeMax", writeMax);
			mav.addObject("fileMax", fileMax);
			mav.addObject("CODE_ANSWER_ALMONEY", CODE_ANSWER_ALMONEY);
			mav.addObject("flagNickPrice", flagNickPrice);

			mav.addObject("lang", sourceLang);
		}
		else {
			CommonUtil.jspAlert(response, msg3, "/default/login", "parent.parent"); // msg4 활용 --> msg3
			return null;
		}

		return mav;
	}

   @RequestMapping("question/questionWriteMng")
   public ModelAndView questionWriteMng(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		ModelAndView mav = new ModelAndView();

		String sp = request.getParameter("SP");
		if(sp == null) { sp = ""; }
		String seq = request.getParameter("QuestionSeq");
		if(seq == null) { seq = ""; }
		Integer section1 = 0;
		String title = "";
		String contents = "";
		String questionBet = "0";
		String flagNickName = "";
		String flagMinor = "";
		List<FileVO> files = new ArrayList<FileVO>();
		int questionMin = 0;
		int questionMax = 0;
		int writeMax = 0;
		int fileMax = 0;
		String[] CODE_ANSWER_ALMONEY;
		String userQueRegAlmoney = "";

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		String msg1 = messageSource.getMessage("msg_1019", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1149", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		QuestionVO question = new QuestionVO();
		if(userSeq > 0) {
			String admin = userLv == 99 ? "1=1 or" : "";

			if(seq != "" && seq != null) {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("questionSeq", seq);
				param.put("admin", admin);
				param.put("userSeq", userSeq);

				question = questionService.getQuestionInfoBySeq(param);

				files = fileService.getQuestionFile(param);

				if(question == null) {
					CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용
				}
				else {
					section1 = question.getSection1();
					title = question.getTitle();
					contents = question.getContents();
					questionBet = String.valueOf(question.getAlmoney().intValue());
					flagNickName = question.getFlagNickName();
					flagMinor = question.getFlagMinor();
				}
			}

			ConfigVO config = configService.getConfigList();

			switch( userLv ) {
				case 1:
					userQueRegAlmoney = config.getLv1_QueRegAlmoney();
					break;
				case 2:
					userQueRegAlmoney = config.getLv2_QueRegAlmoney();
					break;
				case 3:
					userQueRegAlmoney = config.getLv3_QueRegAlmoney();
					break;
				case 4:
					userQueRegAlmoney = config.getLv4_QueRegAlmoney();
					break;
				case 5:
					userQueRegAlmoney = config.getLv5_QueRegAlmoney();
					break;
				case 6:
					userQueRegAlmoney = config.getLv6_QueRegAlmoney();
					break;
				case 7:
					userQueRegAlmoney = config.getLv7_QueRegAlmoney();
					break;
				case 8:
					userQueRegAlmoney = config.getLv8_QueRegAlmoney();
					break;
				case 9:
					userQueRegAlmoney = config.getLv9_QueRegAlmoney();
					break;
				case 10:
					userQueRegAlmoney = config.getLv10_QueRegAlmoney();
					break;
				case 11:
					userQueRegAlmoney = config.getLv11_QueRegAlmoney();
					break;
				default:
					userQueRegAlmoney = config.getLv1_QueRegAlmoney();
			}

			CODE_ANSWER_ALMONEY = userQueRegAlmoney.split("/");

			if(CODE_ANSWER_ALMONEY[0] == null) {
				CommonUtil.jspAlert(response, msg2, "back", ""); // msg2
				return null;
			}

			questionMin = config.getQuestionMin();
			questionMax = config.getQuestionMax();
			writeMax = config.getWriteMax();
			fileMax = config.getFileMax();
			if(questionBet == null || questionBet == "") questionBet = String.valueOf(questionMin);

			mav.addObject("SP", sp);
			mav.addObject("Seq", seq);
			mav.addObject("Section1", section1);
			mav.addObject("title", title);
			mav.addObject("contents", contents);
			mav.addObject("questionBet", Integer.parseInt(questionBet));
			mav.addObject("flagNickName", flagNickName);
			mav.addObject("flagMinor", flagMinor);
			mav.addObject("files", files);
			mav.addObject("questionMin", questionMin);
			mav.addObject("questionMax", questionMax);
			mav.addObject("writeMax", writeMax);
			mav.addObject("fileMax", fileMax);
			mav.addObject("CODE_ANSWER_ALMONEY", CODE_ANSWER_ALMONEY);
		}
		else {
			CommonUtil.jspAlert(response, msg3, "back", ""); // msg3 활용
		}

		return mav;
	}

	@RequestMapping(value="/question/questionSave", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public void questionSave(MultipartHttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");

		String sp = request.getParameter("SP");
		int maxLv = 7;

		String defaultFolder = "Question";
		UtilFile utilFile = new UtilFile();
		String FilePath = utilFile.getSaveLocation(request, defaultFolder);

		String Seq = CommonUtil.fn_Word_In(request.getParameter("Seq"));
		String Title = CommonUtil.fn_Word_In(request.getParameter("Title"));


		// 김주윤 20201222 제목에서 스크립트 추가시 문제 발생 -> 처리
		Title=Title.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");

		String Contents = CommonUtil.fn_Word_In(request.getParameter("Contents"));
		String Section1 = "0";
		String Almoney = CommonUtil.fn_Word_In(request.getParameter("Section2"));
		String FlagNickName = CommonUtil.fn_Word_In(request.getParameter("FlagNickName"));
		String FlagMinor = CommonUtil.fn_Word_In(request.getParameter("FlagMinor"));
		String FlagUse = CommonUtil.fn_Word_In(request.getParameter("FlagUse"));
		int FileMax = Integer.parseInt(request.getParameter("FileMax"));

		String EditFlag = CommonUtil.fn_Word_In(request.getParameter("EditFlag"));
		String Orin_Almoney = CommonUtil.fn_Word_In(request.getParameter("Orin_Almoney"));
		String[] CODE_ANSWER_ALMONEY = new String[] {};
		String userQueRegAlmoney = "";
		int flagNickPrice = Integer.parseInt(request.getParameter("flagNickPrice"));

		if(Seq == null) { Seq = ""; }
		if(Orin_Almoney == null) { Orin_Almoney = "0"; }
		if(FlagNickName == null) { FlagNickName = ""; }
		if(FlagMinor == null) { FlagMinor = ""; }

		int Lv_LimitQueDayRegistCnt = 0;
		int Lv_LimitQueDayDupRegistCnt = 0;
		int Lv_LimitQueContinueRegistTime = 0;

		String nation = "";
		String detectLang = "";

		Long nAlmoney = (long) 0;
		if(Almoney != "") {
			nAlmoney = Long.parseLong(Almoney);
		}

		Long nOrin_Almoney = (long) 0;
		if(Orin_Almoney != "") {
			nOrin_Almoney = Long.parseLong(Orin_Almoney);
		}

		int MaxSeq = 0;
		int memberAlmoneyInfo = 0;
		BigDecimal section_Special = new BigDecimal(0);
		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		if(userSeq > 0) {
			MemberVO mVo = memberService.getAlmoneyMyJoin(userSeq);
			nation = mVo.getNation();

			memberAlmoneyInfo = memberService.getUserAlmoneyBySeq(userSeq);
			ConfigVO config = configService.getConfigList();

			switch( userLv ) {
				case 1:
					userQueRegAlmoney = config.getLv1_QueRegAlmoney();
					break;
				case 2:
					userQueRegAlmoney = config.getLv2_QueRegAlmoney();
					break;
				case 3:
					userQueRegAlmoney = config.getLv3_QueRegAlmoney();
					break;
				case 4:
					userQueRegAlmoney = config.getLv4_QueRegAlmoney();
					break;
				case 5:
					userQueRegAlmoney = config.getLv5_QueRegAlmoney();
					break;
				case 6:
					userQueRegAlmoney = config.getLv6_QueRegAlmoney();
					break;
				case 7:
					userQueRegAlmoney = config.getLv7_QueRegAlmoney();
					break;
				case 8:
					userQueRegAlmoney = config.getLv8_QueRegAlmoney();
					break;
				case 9:
					userQueRegAlmoney = config.getLv9_QueRegAlmoney();
					break;
				case 10:
					userQueRegAlmoney = config.getLv10_QueRegAlmoney();
					break;
				case 11:
					userQueRegAlmoney = config.getLv11_QueRegAlmoney();
					break;
				default:
					userQueRegAlmoney = config.getLv1_QueRegAlmoney();
			}

			CODE_ANSWER_ALMONEY = userQueRegAlmoney.split("/");
		}
		else {
			String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			CommonUtil.jspAlert(response, msg1, "", ""); // msg1 활용
			return;
		}

		String format = "yyyy-MM-dd aa hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String dateReg = type.format(today.getTime());

		if(Seq.equals("")) {
			if(cookieLoginService.getCookieUniv(request, response).equals("")) {
				section_Special = new BigDecimal( cookieLoginService.getCookieUniv(request, response) );
			}

			if(userLv != 99) {
				if(userLv >= 1 && userLv <= maxLv) {
					//[환경설정] DB 조회
					ConfigVO conf = configService.getBoardConfig();

					switch(userLv) {
						case 1:
							Lv_LimitQueDayRegistCnt = conf.getLv1_LimitQueDayRegistCnt();
							Lv_LimitQueDayDupRegistCnt = conf.getLv1_LimitQueDayDupRegistCnt();
							Lv_LimitQueContinueRegistTime = conf.getLv1_LimitQueContinueRegistTime();
							break;
						case 2:
							Lv_LimitQueDayRegistCnt = conf.getLv2_LimitQueDayRegistCnt();
							Lv_LimitQueDayDupRegistCnt = conf.getLv2_LimitQueDayDupRegistCnt();
							Lv_LimitQueContinueRegistTime = conf.getLv2_LimitQueContinueRegistTime();
							break;
						case 3:
							Lv_LimitQueDayRegistCnt = conf.getLv3_LimitQueDayRegistCnt();
							Lv_LimitQueDayDupRegistCnt = conf.getLv3_LimitQueDayDupRegistCnt();
							Lv_LimitQueContinueRegistTime = conf.getLv3_LimitQueContinueRegistTime();
							break;
						case 4:
							Lv_LimitQueDayRegistCnt = conf.getLv4_LimitQueDayRegistCnt();
							Lv_LimitQueDayDupRegistCnt = conf.getLv4_LimitQueDayDupRegistCnt();
							Lv_LimitQueContinueRegistTime = conf.getLv4_LimitQueContinueRegistTime();
							break;
						case 5:
							Lv_LimitQueDayRegistCnt = conf.getLv5_LimitQueDayRegistCnt();
							Lv_LimitQueDayDupRegistCnt = conf.getLv5_LimitQueDayDupRegistCnt();
							Lv_LimitQueContinueRegistTime = conf.getLv5_LimitQueContinueRegistTime();
							break;
						case 6:
							Lv_LimitQueDayRegistCnt = conf.getLv6_LimitQueDayRegistCnt();
							Lv_LimitQueDayDupRegistCnt = conf.getLv6_LimitQueDayDupRegistCnt();
							Lv_LimitQueContinueRegistTime = conf.getLv6_LimitQueContinueRegistTime();
							break;
						case 7:
							Lv_LimitQueDayRegistCnt = conf.getLv7_LimitQueDayRegistCnt();
							Lv_LimitQueDayDupRegistCnt = conf.getLv7_LimitQueDayDupRegistCnt();
							Lv_LimitQueContinueRegistTime = conf.getLv7_LimitQueContinueRegistTime();
							break;

						case 8:
							Lv_LimitQueDayRegistCnt = conf.getLv8_LimitQueDayRegistCnt();
							Lv_LimitQueDayDupRegistCnt = conf.getLv8_LimitQueDayDupRegistCnt();
							Lv_LimitQueContinueRegistTime = conf.getLv8_LimitQueContinueRegistTime();
							break;
						case 9:
							Lv_LimitQueDayRegistCnt = conf.getLv9_LimitQueDayRegistCnt();
							Lv_LimitQueDayDupRegistCnt = conf.getLv9_LimitQueDayDupRegistCnt();
							Lv_LimitQueContinueRegistTime = conf.getLv9_LimitQueContinueRegistTime();
							break;
						case 10:
							Lv_LimitQueDayRegistCnt = conf.getLv10_LimitQueDayRegistCnt();
							Lv_LimitQueDayDupRegistCnt = conf.getLv10_LimitQueDayDupRegistCnt();
							Lv_LimitQueContinueRegistTime = conf.getLv10_LimitQueContinueRegistTime();
							break;
						case 11:
							Lv_LimitQueDayRegistCnt = conf.getLv11_LimitQueDayRegistCnt();
							Lv_LimitQueDayDupRegistCnt = conf.getLv11_LimitQueDayDupRegistCnt();
							Lv_LimitQueContinueRegistTime = conf.getLv11_LimitQueContinueRegistTime();
							break;
					}

					//[추가(2018.02.01): 김현구] 1일 기준, 질문글 등록수 제한 CHECK
					int questionCnt1 = commonService.getQuestionCntForQuestion(userSeq);

					if(questionCnt1 >= Lv_LimitQueDayRegistCnt) {
						String msg2 = messageSource.getMessage("msg_1150", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
						String msg3 = messageSource.getMessage("msg_1151", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
						String msg4 = messageSource.getMessage("msg_1048", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
						// msg2, msg3, msg4 활용
						CommonUtil.jspAlert(response, msg2 + Lv_LimitQueDayRegistCnt + msg3 + "\\n" + msg4, "", "");
						return;
					}

					//[추가(2018.02.01): 김현구] 1일 기준, 동일 제목 또는 내용의 질문글 입력 제한 CHECK
					HashMap<String, Object> param = new HashMap<String, Object>();
					param.put("userSeq", userSeq);
					param.put("title", Title);
					param.put("contents", Contents);
					int questionCnt2 = commonService.getQuestionCntForQuestion2(param);

					String msg5 = messageSource.getMessage("msg_1152", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
					String msg6 = messageSource.getMessage("msg_1153", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
					String msg7 = messageSource.getMessage("msg_1154", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
					String msg8 = messageSource.getMessage("msg_1155", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
					String msg9 = messageSource.getMessage("msg_1156", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
					String msg10 = messageSource.getMessage("msg_1157", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
					String msg11 = messageSource.getMessage("msg_1055", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
					String msg12 = messageSource.getMessage("msg_1043", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
					if(questionCnt2 > Lv_LimitQueDayDupRegistCnt) {
						if(Lv_LimitQueDayDupRegistCnt == 0) {
							// msg5, msg6 활용
							CommonUtil.jspAlert(response, msg5 + "\\n" + msg6, "", "");
						}
						else {
							// msg7, msg8, msg9 활용
							CommonUtil.jspAlert(response, msg7 + "" + Lv_LimitQueDayDupRegistCnt + "" + msg8 + "\\n" + msg9, "", "");
						}
						return;
					}

					int questionDiff = commonService.getQuestionDiff(userSeq);

					if(questionDiff <= Lv_LimitQueContinueRegistTime) {
						// msg10, msg11, msg12 활용
						CommonUtil.jspAlert(response, msg10 + "\\n" + msg11 + "" + Lv_LimitQueContinueRegistTime + "" + msg12, "", "");
						return;
					}
				}
			}


			//질문글 MAX 등록번호 조회
			int questionMaxSeq = questionService.getQuestionMaxSeq();
			if(questionMaxSeq == 0) {
				MaxSeq = 1;
			}
			else {
				MaxSeq = questionMaxSeq + 1;
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
		int memberSeq = 0;
		BigDecimal memberAlmoney = new BigDecimal(0);


		detectLang = request.getParameter("lang");

		if(detectLang.contains("-")) {
			String[] detectLangArr = detectLang.split("-");
			detectLang = detectLangArr[0];
		}

		String ticketQueChk = "";
		if(Seq.equals("")) {
			//[추가(2018.02.22): 김현구] 회원 보유 알머니 조회
			if(memberAlmoneyInfo < nAlmoney) {
				String msg13 = messageSource.getMessage("msg_1016", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
				CommonUtil.jspAlert(response, msg13, "", "document"); // msg13 활용
				return;
			}

			//detectLang = CommonUtil.detectLanguage(Title); // 언어 감지 비용 문제로 비활성 처리 2021.05.06 조인현 팀장
			HashMap<String, Object> qParam = new HashMap<String, Object>();
			qParam.put("flagUse", FlagUse);
			qParam.put("userSeq", userSeq);
			qParam.put("section1", Section1);
			qParam.put("title", Title);
			qParam.put("contents", Contents);
			qParam.put("almoney", Almoney);
			qParam.put("flagNickName", FlagNickName);
			qParam.put("flagMinor", FlagMinor);
			qParam.put("flagUse", FlagUse);
			qParam.put("dateReg", dateReg);
			qParam.put("univ", section_Special);
			qParam.put("nation", nation);
			qParam.put("lang", detectLang);

			if(FlagNickName.equals("N")) {
				qParam.put("flagNickPrice", 100);
			}
			else {
				qParam.put("flagNickPrice", 0);
			}

			questionService.setQuestion(qParam);
			//System.out.println("질문 테이블 등록 완료!");


			HashMap<String, Object> gConfig = commonService.getTicketConfig();
			int queCountConfig = Integer.parseInt( String.valueOf( gConfig.get("queCount") ) );
			int ansCountConfig = Integer.parseInt( String.valueOf( gConfig.get("ansCount") ) );

			int stackCnt = commonService.getTicketStackCnt(userSeq);
			int queCount = 0;
			int ansCount = 0;
			//int replCount = 0;

			//System.out.println("stackCnt : " + stackCnt);

			HashMap<String, Object> stackInfo = null;
			if(stackCnt > 0) {
				HashMap<String, Object> sParam = new HashMap<String, Object>();
				sParam.put("userSeq", userSeq);
				sParam.put("mode", "que");

				// 스택 증가
				commonService.setAddTickStackUse(sParam);

				stackInfo =  commonService.getTicketStack(userSeq);
			}
			else {
				HashMap<String, Object> sParam = new HashMap<String, Object>();
				sParam.put("userSeq", userSeq);
				sParam.put("queCount", 1);
				sParam.put("ansCount", 0);
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

			if(queCount >= queCountConfig && ansCount >= ansCountConfig) {
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
				tParam.put("rType", "que");

				// 이용권 추가
				commonService.addTicket(tParam);


				HashMap<String, Object> sParam = new HashMap<String, Object>();
				sParam.put("userSeq", userSeq);
				sParam.put("mode", "que_ans");
				sParam.put("queCount", queCountConfig);
				sParam.put("ansCount", queCountConfig);

				// 스택 차감
				commonService.setSubTickStackUse(sParam);

				ticketQueChk = "Y";
			}
		}
		else {
			//[추가(2018.02.22): 김현구] 관리자인 경우 CHECK
			HashMap<String, Object> qParam = new HashMap<String, Object>();
			qParam.put("userSeq", userSeq);
			qParam.put("userLv", userLv);
			qParam.put("seq", Seq);
			//System.out.println("userSeq : " + userSeq);
			//System.out.println("userLv : " + userLv);
			//System.out.println("seq : " + Seq);

			QuestionVO question = questionService.getMemberQuestionAlmoney(qParam);

			memberSeq = question.getMemberSeq();
			memberAlmoney = question.getAlmoney();

			BigDecimal bZero = new BigDecimal(0);
			BigDecimal bAlmoney = new BigDecimal(nAlmoney);
			BigDecimal bOrin_Almoney = new BigDecimal(nOrin_Almoney);

			//[추가(2018.02.26): 김현구] 회원 보유 알머니와 사례알 알머니 값 비교 CHECK 305
			if( (memberAlmoney.subtract(bAlmoney)).add(bOrin_Almoney).compareTo(BigDecimal.ZERO) < 0 ) {
				String msg14 = messageSource.getMessage("msg_1016", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
				CommonUtil.jspAlert(response, msg14, "", "document"); // msg14 활용
				return;
			}
			if(userLv == 99) {
				HashMap<String, Object> uParam = new HashMap<String, Object>();
				uParam.put("section1", Section1);
				uParam.put("title", Title);
				uParam.put("contents", Contents);
				uParam.put("almoney", Almoney);
				uParam.put("flagNickName", FlagNickName);
				uParam.put("flagMinor", FlagMinor);
				uParam.put("flagUse", FlagUse);
				uParam.put("dateReg", dateReg);
				uParam.put("seq", Seq);
				uParam.put("memberSeq", userSeq);
				uParam.put("lang", detectLang);

				if(FlagNickName.equals("N") && flagNickPrice == 0) {
					//System.out.println("flagNickPrice N : " + flagNickPrice);
					uParam.put("flagNickPrice", 100);
				}
				else {
					//System.out.println("flagNickPrice Y : " + flagNickPrice);
					uParam.put("flagNickPrice", flagNickPrice);
				}
				//System.out.println("detectLang adm : " + detectLang);
				questionService.updateQuestionAdmin(uParam);
			}
			else {
				HashMap<String, Object> uParam = new HashMap<String, Object>();
				uParam.put("section1", Section1);
				uParam.put("title", Title);
				uParam.put("contents", Contents);
				uParam.put("almoney", Almoney);
				uParam.put("flagNickName", FlagNickName);
				uParam.put("flagMinor", FlagMinor);
				uParam.put("flagUse", FlagUse);
				uParam.put("dateReg", dateReg);
				uParam.put("seq", Seq);
				uParam.put("userSeq", userSeq);
				uParam.put("lang", detectLang);

				if(FlagNickName.equals("N") && flagNickPrice == 0) {
					uParam.put("flagNickPrice", 100);
				}
				else {
					uParam.put("flagNickPrice", flagNickPrice);
				}
				//System.out.println("detectLang : " + detectLang);
				questionService.updateQuestion(uParam);
			}
		}

		//318줄 파일 처리
		File fileDir = new File(FilePath);

        if (!fileDir.exists()) {
        	fileDir.mkdirs();
        }


        String format2 = "yyyy-MM-dd";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
		Calendar today2 = Calendar.getInstance();
		SimpleDateFormat type2 = new SimpleDateFormat(format2);
		String dateReg2 = type2.format(today2.getTime());

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
						String msg15 = messageSource.getMessage("msg_1044", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
						CommonUtil.jspAlert(response, msg15, "back", ""); // msg15 활용
						return;
					}
				}

				String inTime = new java.text.SimpleDateFormat("HHmmss").format(new java.util.Date());
				String dt = dateReg2.replaceAll("-", "");
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

        //[2017.12.13: 추가 - 김현구] 선택한 사례알머니가 정의된 코드값이 맞는지 CHECK
        int chkAlmoneyCnt = 0;

        for(int i = 0; i < CODE_ANSWER_ALMONEY.length; i++) {
        	if(nAlmoney == Integer.parseInt(CODE_ANSWER_ALMONEY[i])) {
        		chkAlmoneyCnt++;
        	}
        }


        if(chkAlmoneyCnt == 0 && FlagUse != "T") {
        	String msg16 = messageSource.getMessage("msg_1158", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
        	CommonUtil.jspAlert(response, msg16, "back", ""); // msg16 활용
        	return;
        }



        if(Seq.equals("")) {
        	commonService.setQuestionTempQueDelete(userSeq);

        	PrintWriter out = response.getWriter();
			out.println("<script>parent.document.isSaved=true;</script>");
			out.flush();
        }

        //임시 저장을 새로 만들었으므로 이 곳으로 들어올 수 없다.
        if(FlagUse == "T") {
        	PrintWriter out2 = response.getWriter();
        	out2.println("<script>");
        	//out2.println("alert('임시로 저장되었습니다. 임시저장된 글은 임시저장 메뉴에서 확인 가능합니다.');");
			out2.println("$('input[name=Seq]', parent.document).attr('value', " + MaxSeq + ");");
			out2.println("</script>");
			out2.flush();
        }
        else {
        	//[2018.01.15: 김현구] 신규 등록일 경우
        	if(EditFlag.equals("")) {
        		//[수정(2018.01.15): 김현구] 회원DB 질문수 UPDATE (+1) [호출: /Common/SQL/Question.vbs]
        		memberService.updateQuestionCount(userSeq);

        		ConfigVO boardConfig = configService. getBoardConfig();

        		int newSeq = 0;
        		newSeq = questionService.getQuestionNewSeq();

        		HashMap<String, Object> cntParam = new HashMap<String, Object>();
        		cntParam.put("userSeq", userSeq);
        		cntParam.put("contentsSeq", newSeq);

        		int changeAlmoneyCnt = commonService.getChangeQueAlmoneyCount(cntParam);

        		if(changeAlmoneyCnt == 0) {
	        		Almoney = Integer.toString((Integer.parseInt(Almoney) * -1));
	        		HashMap<String, Object> cParam = new HashMap<String, Object>();
	        		cParam.put("typeCode", 43);
	        		cParam.put("userSeq", userSeq);
	        		cParam.put("almoney", Almoney);
	        		cParam.put("contentsSeq", newSeq);
	        		HashMap<String, Object> changeAlmoney = commonService.setChangeAlmoneySP(cParam);

	        		BigDecimal viewMoney = new BigDecimal(Almoney);
	        		BigDecimal userAlmoney = cookieLoginService.getCookieUserAlmoney(request, response);
	        		BigDecimal changeSaveAlmoney = userAlmoney.subtract(viewMoney);

	        		BigDecimal UserAlmoneyRedim = changeSaveAlmoney.setScale(0, BigDecimal.ROUND_FLOOR);
	        		//Session("UserAlmoney") = Session("UserAlmoney") - Almoney

	        		if(FlagNickName.equals("N")) {
	        			BigDecimal minusQty = new BigDecimal(-1);
	        			BigDecimal nickAlBig = new BigDecimal( 100 );
	            		BigDecimal minusNick = nickAlBig.multiply(minusQty);
	            		HashMap<String, Object> cParam3 = new HashMap<String, Object>();
	            		cParam3.put("typeCode", 53);
	            		cParam3.put("userSeq", memberSeq);
	            		cParam3.put("almoney", minusNick);
	            		cParam3.put("contentsSeq", Seq);
	            		HashMap<String, Object> changeAlmoney3 = commonService.setChangeAlmoneySP(cParam3);

	            		BigDecimal changeSaveAlmoney3 = UserAlmoneyRedim.subtract(nickAlBig);
	            		UserAlmoneyRedim = changeSaveAlmoney3.setScale(0, BigDecimal.ROUND_FLOOR);
	        		}
        		}
        	}
        	else {
        		//[추가(2018.01.15): 김현구] 수정일 경우
        		//수정 알머니와 원래 등록 알머니가 다를 경우

        		if(Almoney != Orin_Almoney) {
        			//[추가(2018.02.27): 김현구] 관리자인 경우 CHECK
        			if(userLv == 99) {
        				//exec SP_CHANGE_ALMONEY 16, " & MemberSeq & ", " & Orin_Almoney & ", " & Seq & ";"
        				HashMap<String, Object> cParam = new HashMap<String, Object>();
                		cParam.put("typeCode", 16);
                		cParam.put("userSeq", memberSeq);
                		cParam.put("almoney", Orin_Almoney);
                		cParam.put("contentsSeq", Seq);
                		HashMap<String, Object> changeAlmoney = commonService.setChangeAlmoneySP(cParam);

        				//MemberAlmoney = MemberAlmoney + Orin_Almoney
                		BigDecimal userAlmoney = new BigDecimal( Orin_Almoney );
                		BigDecimal changeSaveAlmoney = memberAlmoney.add(userAlmoney);


        				//exec SP_CHANGE_ALMONEY 43, " & MemberSeq & ", -" & Almoney & ", " & Seq & ";
        				//MemberAlmoney = MemberAlmoney - Almoney
                		BigDecimal minusQty = new BigDecimal(-1);
                		BigDecimal almoneyBig = new BigDecimal( Almoney );
                		BigDecimal minusAlmoney = almoneyBig.multiply(minusQty);
                		HashMap<String, Object> cParam2 = new HashMap<String, Object>();
                		cParam2.put("typeCode", 43);
                		cParam2.put("userSeq", memberSeq);
                		cParam2.put("almoney", minusAlmoney);
                		cParam2.put("contentsSeq", Seq);
                		HashMap<String, Object> changeAlmoney2 = commonService.setChangeAlmoneySP(cParam2);

                		BigDecimal changeSaveAlmoney2 = changeSaveAlmoney.subtract(almoneyBig);
                		BigDecimal UserAlmoneyRedim = changeSaveAlmoney2.setScale(0, BigDecimal.ROUND_FLOOR);

                		if(FlagNickName.equals("N") && flagNickPrice == 0) {
                			BigDecimal nickAlBig = new BigDecimal( 100 );
                    		BigDecimal minusNick = nickAlBig.multiply(minusQty);
                    		HashMap<String, Object> cParam3 = new HashMap<String, Object>();
                    		cParam3.put("typeCode", 53);
                    		cParam3.put("userSeq", memberSeq);
                    		cParam3.put("almoney", minusNick);
                    		cParam3.put("contentsSeq", Seq);
                    		HashMap<String, Object> changeAlmoney3 = commonService.setChangeAlmoneySP(cParam3);

                    		BigDecimal changeSaveAlmoney3 = UserAlmoneyRedim.subtract(nickAlBig);
                    		UserAlmoneyRedim = changeSaveAlmoney3.setScale(0, BigDecimal.ROUND_FLOOR);
                		}
        			}
        			else {
        				//exec SP_CHANGE_ALMONEY 16, " & Session("UserSeq") & ", " & Orin_Almoney & ", " & Seq & ";
        				HashMap<String, Object> cParam = new HashMap<String, Object>();
                		cParam.put("typeCode", 16);
                		cParam.put("userSeq", userSeq);
                		cParam.put("almoney", Orin_Almoney);
                		cParam.put("contentsSeq", Seq);
                		HashMap<String, Object> changeAlmoney = commonService.setChangeAlmoneySP(cParam);

        				//MemberAlmoney = MemberAlmoney + Orin_Almoney
                		BigDecimal userAlmoney = new BigDecimal( Orin_Almoney );
                		BigDecimal changeSaveAlmoney = memberAlmoney.add(userAlmoney);


        				//exec SP_CHANGE_ALMONEY 43, " & Session("UserSeq") & ", -" & Almoney & ", " & Seq & ";
                		BigDecimal minusQty = new BigDecimal(-1);
                		BigDecimal almoneyBig = new BigDecimal( Almoney );
                		BigDecimal minusAlmoney = almoneyBig.multiply(minusQty);
                		HashMap<String, Object> cParam2 = new HashMap<String, Object>();
                		cParam2.put("typeCode", 43);
                		cParam2.put("userSeq", userSeq);
                		cParam2.put("almoney", minusAlmoney);
                		cParam2.put("contentsSeq", Seq);
                		HashMap<String, Object> changeAlmoney2 = commonService.setChangeAlmoneySP(cParam2);

                		//MemberAlmoney = MemberAlmoney - Almoney
                		BigDecimal changeSaveAlmoney2 = changeSaveAlmoney.subtract(almoneyBig);
                		BigDecimal UserAlmoneyRedim = changeSaveAlmoney2.setScale(0, BigDecimal.ROUND_FLOOR);

                		if(FlagNickName.equals("N") && flagNickPrice == 0) {
                			BigDecimal nickAlBig = new BigDecimal( 100 );
                    		BigDecimal minusNick = nickAlBig.multiply(minusQty);
                    		HashMap<String, Object> cParam3 = new HashMap<String, Object>();
                    		cParam3.put("typeCode", 53);
                    		cParam3.put("userSeq", memberSeq);
                    		cParam3.put("almoney", minusNick);
                    		cParam3.put("contentsSeq", Seq);
                    		HashMap<String, Object> changeAlmoney3 = commonService.setChangeAlmoneySP(cParam3);

                    		BigDecimal changeSaveAlmoney3 = UserAlmoneyRedim.subtract(nickAlBig);
                    		UserAlmoneyRedim = changeSaveAlmoney3.setScale(0, BigDecimal.ROUND_FLOOR);
                		}
        			}
        		}
        	}
        	commonService.setQuestionTempQueDelete(userSeq);

        	CommonUtil.jspAlert(response, "", "/answer/answerList?Seq=" + MaxSeq + "&CurPageName=bestList&Section1=" + Section1 + "&src_Sort=Seq&src_OrderBy=DESC&SP=" + sp + "&ticketQueChk=" + ticketQueChk, "parent.parent");

        	return;

        }
	}

	@RequestMapping(value="/question/questionSaveMng", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public void questionSaveMng(MultipartHttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");

		String sp = request.getParameter("SP");
		int maxLv = 7;

		String Seq = CommonUtil.fn_Word_In(request.getParameter("Seq"));
		String Title = CommonUtil.fn_Word_In(request.getParameter("Title"));
		String Contents = CommonUtil.fn_Word_In(request.getParameter("Contents"));


		if(Seq == null) { Seq = ""; }

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1062", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_1159", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		if(userLv < 1) {
			CommonUtil.jspAlert(response, msg1, "", ""); // msg1 활용
			return;
		}

		String format = "yyyy-MM-dd aa hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String dateReg = type.format(today.getTime());


		if(!Seq.equals("")) {
			if(userLv == 99) {
				HashMap<String, Object> uParam = new HashMap<String, Object>();
				uParam.put("title", Title);
				uParam.put("contents", Contents);
				uParam.put("seq", Seq);

				questionService.updateQuestionAdminBySeq(uParam);

				CommonUtil.jspAlert(response, "", "/answer/answerList?Seq=" + Seq + "&SP=" + sp, "parent.parent");
				return;
			}
			else {
				CommonUtil.jspAlert(response, msg2, "", "document"); // msg2 활용
				return;
			}
		}
		else {
			CommonUtil.jspAlert(response, msg3, "", "document"); // msg3 활용
			return;
		}

	}

	@RequestMapping(value="/question/questionDelete", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public void questionDelete(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");

		String flagSelect = request.getParameter("FlagSelect");
		String questionSeq = request.getParameter("QuestionSeq");
		String answerSeq = request.getParameter("AnswerSeq");
		String flag = request.getParameter("Flag");
		String sp = request.getParameter("SP");

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1159", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_1060", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_1160", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		if(userSeq < 1) {
			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용
			return;
		}

		//[추가(2017.12.20): 김현구] 전달인자 CHECK
		if(questionSeq == null || questionSeq == "" || questionSeq == "0") {
			CommonUtil.jspAlert(response, msg2, "back", ""); // msg2 활용
			return;
		}

		//String format = "yyyy-MM-dd aa hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
		String format = "hh:mm";
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String strTime = type.format(today.getTime());

		//[추가/수정(2017.12.20): 김현구] 사용구분(FlagUse) = "임시저장(T)"인 경우
		if(flag == "T") {
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("userLv", userLv);
			param.put("flag", flag);
			param.put("userSeq", userSeq);

			//[질문] 임시저장 글일 경우
			if(flagSelect == "Q") {
				param.put("seq", questionSeq);
				questionService.deleteQuestionAsParams(param);
			}
			else {
				param.put("seq", answerSeq);
				answerService.deleteAnswerAsParams(param);
			}

			CommonUtil.jspAlert(response, "", "/member/myTemp?FlagSelect=ALL&sw=" + strTime, "parent");
		}
		else {
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("que_seq", questionSeq);
			param.put("mem_seq", userSeq);

			String returnCode = questionService.deleteQuestionSpAsParams(param);

			String sURL = "";
			if(returnCode.equals("0")) {
				if(sp == "" || sp == "0") {
					sURL = "/answer/questionList";
				}
				else {
					sURL = "/answer/questionList_SP";
				}
				//System.out.println("삭제됨!");
				CommonUtil.jspAlert(response, msg3, sURL, "parent"); // msg3 활용
			}
			else if(returnCode.equals("1")) {
				//System.out.println("삭제 실패!");
				PrintWriter out2 = response.getWriter();
	        	out2.println("<script>");
	        	out2.println("alert(msg4);"); // msg4 활용
				out2.println("parent.window.location.reload();");
				out2.println("</script>");
				out2.flush();
			}
		}
	}

	@RequestMapping(value="/question/questionSearch", method = RequestMethod.GET)
	public ModelAndView questionSearch(HttpServletRequest request) throws Exception {

		ModelAndView mav = new ModelAndView();

		String curPageName = "/question/questionSearch";
		String req_TREC = CommonUtil.fn_Word_In( request.getParameter("trec") );
		String req_PG = CommonUtil.fn_Word_In( request.getParameter("pg") );
		String src_Sort = CommonUtil.fn_Word_In( request.getParameter("src_Sort") );
		String src_Text = CommonUtil.fn_Word_In( request.getParameter("src_Text") );

		int pg = 0;
		if(request.getParameter("pg") == null || request.getParameter("pg") == "") {
			req_PG = "1";
		}

		pg = Integer.parseInt(req_PG);

		if(request.getParameter("src_Sort") == null || request.getParameter("src_Sort") == "") {
			src_Sort = "R";
		}

		int n_pagesize = 20;
		int n_pagescnt = 5;
		int n_curpage = 1;
		int n_totalpage = 0;

		String[] keyWordStr = null;
		int pageTotalcnt = 0;
		int kwyword_count = 0;


		int searchKeyWorkdChCount = questionService.getSearchKeyWordChCount(src_Text);

		HashMap<String, Object> keyParam = new HashMap<String, Object>();
		keyParam.put("src_Text", src_Text);
		keyParam.put("filter_str", "&");
		List<QuestionVO> searchKeyWorkdChData = questionService.getSearchKeyWordChData(keyParam);


		if(searchKeyWorkdChCount > 0) {
			kwyword_count = searchKeyWorkdChCount;
			keyWordStr =  new String[kwyword_count];
			for(int i = 0; i < searchKeyWorkdChData.size(); i++) {
				keyWordStr[i] = searchKeyWorkdChData.get(i).getDisplay_term();
			}
		}

		List<QuestionVO> searchData = new ArrayList<QuestionVO>();
		if(kwyword_count > 0) {
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("page_num", pg);
			param.put("page_size", n_pagesize);
			param.put("search_str", src_Text);
			param.put("order_type", src_Sort);

			searchData = questionService.getSearchData(param);
		}

		if(searchData.size() > 0) {
			pageTotalcnt= questionService.getSearchDataCnt(src_Text);
		}

		//String page_get_data = "src_Text=" + src_Text + "&src_Sort=" + src_Sort;
		//CommonUtil.get_Paging_Tag_New_02(pageTotalcnt, pg, n_pagesize, n_pagescnt, curPageName, page_get_data);

		String url = "/question/questionSearch?src_Text=" + src_Text + "&src_Sort=" + src_Sort;
		String paging = CommonUtil.get_Paging_Tag_New_ver2(n_pagescnt, pg, pageTotalcnt, "frm_sch", url);

		mav.addObject("CurPageName", curPageName);

		mav.addObject("req_TREC", req_TREC);
		mav.addObject("req_PG", req_PG);
		mav.addObject("src_Sort", src_Sort);
		mav.addObject("src_Text", src_Text);
		mav.addObject("n_pagesize", n_pagesize);
		mav.addObject("n_pagescnt", n_pagescnt);
		mav.addObject("n_curpage", n_curpage);
		mav.addObject("n_totalpage", n_totalpage);

		mav.addObject("pageTotalcnt", pageTotalcnt);
		mav.addObject("kwyword_count", kwyword_count);
		mav.addObject("keyWordStr", keyWordStr);
		mav.addObject("searchData", searchData);
		mav.addObject("paging", paging);

		return mav;
	}
}
