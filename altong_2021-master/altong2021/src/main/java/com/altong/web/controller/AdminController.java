package com.altong.web.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.altong.web.logic.LogAlmoneyVO;
import com.altong.web.logic.ad.AdVO;
import com.altong.web.logic.answer.AnswerVO;
import com.altong.web.logic.config.ConfigVO;
import com.altong.web.logic.files.FileVO;
import com.altong.web.logic.member.MemberAdminVO;
import com.altong.web.logic.member.MemberCertVO;
import com.altong.web.logic.member.MemberVO;
import com.altong.web.logic.question.QuestionVO;
import com.altong.web.logic.quiz.QuizAnsVO;
import com.altong.web.logic.quiz.QuizGameQueVO;
import com.altong.web.logic.quiz.QuizGameVO;
import com.altong.web.logic.quiz.QuizLogVO;
import com.altong.web.logic.quiz.QuizQueVO;
import com.altong.web.logic.section.SectionVO;
import com.altong.web.logic.util.CodeUtil;
import com.altong.web.logic.util.CommonUtil;
import com.altong.web.logic.util.CookieBox;
import com.altong.web.logic.util.EncLibrary;
import com.altong.web.logic.util.MD5Class;
import com.altong.web.logic.util.UtilFile;
import com.altong.web.service.alpay.AlpayService;
import com.altong.web.service.answer.AnswerService;
import com.altong.web.service.common.CommonService;
import com.altong.web.service.config.ConfigService;
import com.altong.web.service.file.FileService;
import com.altong.web.service.member.MemberService;
import com.altong.web.service.question.QuestionService;
import com.altong.web.service.quiz.QuizService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import jasp.vbs.vb;

@Controller
@RequestMapping("aadmin/*")
public class AdminController {
	final int configInput 			= 0b00000000000000000000001;		//환경설정
	final int configInput_new 		= 0b00000000000000000000010;	//환경설정 - 신규 승천 시스템 환경 설정
	final int memberList	 		= 0b00000000000000000000100;	//회원관리 - 회원관리
	final int memberCertList	 	= 0b00000000000000000001000;	//회원관리 - 회원 실명인증 관리
	final int recommendView		 	= 0b00000000000000000010000;	//추천인 관리
	final int exchangeAskList	 	= 0b00000000000000000100000;	//출금 관리
	final int exchReadyList		 	= 0b00000000000000001000000;	//출금관리 - 회원 레벨별 출금 대기자 현황
	final int alpayLogCheck		 	= 0b00000000000000010000000;	//출금관리 - 알머니 -> 알페이 전환 Log
	final int alpayExchList		 	= 0b00000000000000100000000;	//출금관리 - 현금 출금 신청 현황
	final int sectionList	 		= 0b00000000000001000000000;	//카테고리 관리
	final int eventList		 		= 0b00000000000010000000000;	//이벤트 관리
	final int adList		 		= 0b00000000000100000000000;	//광고 관리
	final int almoneyList	 		= 0b00000000001000000000000;	//알머니 현황
	final int levelUpReadyList		= 0b00000000010000000000000;	//회원레벨 관리 - 회원 레벨별 승천 대기자 현황
	final int levelUpReadyList_new	= 0b00000000100000000000000;	//회원레벨 관리 - 신규 승천 시스템
	final int notice				= 0b00000001000000000000000;	//공지 사항
	final int reportList			= 0b00000010000000000000000;	//신고
	final int blackList				= 0b00000100000000000000000;	//신고 - 블랙리스트 보기
	final int storeInput			= 0b00001000000000000000000;	//알맹이 - 알맹이 입력
	final int storePrepayment		= 0b00010000000000000000000;	//알맹이 - 선입금 관리
	final int memberView			= 0b00100000000000000000000;	//회원정보 수정/탈퇴
	final int memberViewLv			= 0b01000000000000000000000;	//회원정보 - 레벨 수정
	final int memberViewAlmoney		= 0b10000000000000000000000;	//회원정보 - 알머니 지급

	final int authority_Cnt = 23; 	// 권한 갯수

	@Autowired
	ConfigService configService;

	@Autowired
	MemberService memberService;

	@Autowired
	QuestionService questionService;

	@Autowired
	CommonService commonService;

	@Autowired
	AnswerService answerService;

	@Autowired
	FileService fileService;

	@Autowired
	AlpayService alpayService;

	@Autowired
	QuizService quizService;

	@Autowired
	CookieLocaleResolver localResolver;

	@Autowired
	MessageSource messageSource;

	@RequestMapping("index")
	public ModelAndView index(HttpServletRequest request) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView();

		return mav;
	}

	@RequestMapping("configInput")
	public ModelAndView configInput(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		ModelAndView mav = new ModelAndView();

		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, configInput, userSeq);
		if(admChk == true) {
			ConfigVO conf = configService.getConfigList();

			if(conf.getLv_LevelUpAuto_Yn() == null || conf.getLv_LevelUpAuto_Yn().length() == 0) { conf.setLv_LevelUpAuto_Yn("N"); }

			if(conf.getLv_LevelUpAuto_Yn().equals("N")) {
				conf.setLv2_LevelUpAuto_Yn("N");
				conf.setLv3_LevelUpAuto_Yn("N");
				conf.setLv4_LevelUpAuto_Yn("N");
				conf.setLv5_LevelUpAuto_Yn("N");
				conf.setLv6_LevelUpAuto_Yn("N");
				conf.setLv7_LevelUpAuto_Yn("N");
				conf.setLv8_LevelUpAuto_Yn("N");
			}

			CodeUtil code = new CodeUtil(request);

			mav.addObject("conf", conf);
			mav.addObject("codeCd", code.getCODE_MEM_JOIN_CERT_TYPE_CD());
			mav.addObject("codeNm", code.getCODE_MEM_JOIN_CERT_TYPE_NM());
			mav.addObject("codeMemLvNm", code.getCODE_MEM_LV_NM());
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}

		return mav;
	}

	@RequestMapping("configInput_new")
	public ModelAndView configInput_new(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		ModelAndView mav = new ModelAndView();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, configInput_new, userSeq);
		if(admChk == true) {
			List<HashMap<String, Object>> conf = configService.getConfigForLv();

			mav.addObject("conf", conf);
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}

		return mav;
	}

	@RequestMapping("memberList")
	public ModelAndView memberList(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		ModelAndView mav = new ModelAndView();
		EncLibrary enc = new EncLibrary();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, memberList, userSeq);
		if(admChk == true) {
			String curPageName = "memberList";

			String req_TREC = CommonUtil.fn_Word_In( request.getParameter("trec") != null ? request.getParameter("trec").trim() : "0");
			String req_PG = CommonUtil.fn_Word_In( request.getParameter("pg") != null ? request.getParameter("pg").trim() : "");
			String src_Sort = CommonUtil.fn_Word_In( request.getParameter("src_Sort") != null ? request.getParameter("src_Sort").trim() : "");
			String src_Kind = CommonUtil.fn_Word_In( request.getParameter("src_Kind") != null ? request.getParameter("src_Kind").trim() : "");
			String src_Text = CommonUtil.fn_Word_In( request.getParameter("src_Text") != null ? request.getParameter("src_Text").trim() : "" );

			//System.out.println("src_Text : " + src_Text);
			if(req_PG == "") {
				req_PG = "1";
			}

			if(src_Sort.equals("")) {
				src_Sort = "Seq";
			}

			String s_tmp = "";
			int n_trec = 0;
			s_tmp = req_TREC;

			if(CommonUtil.isNumeric(s_tmp) && s_tmp.length() > 0) {
				n_trec = Integer.parseInt(s_tmp);
			}
			String tmp = src_Text;

			if(!src_Kind.equals("")) {
				if(src_Kind.equals("Phone")) {
					src_Text = enc.AlmoneyEncrypt(src_Text);
				}
			}


			HashMap<String, Object> mParam = new HashMap<String, Object>();
			mParam.put("psrc_Kind", src_Kind);
			mParam.put("psrc_Text", src_Text);


			int memCount = memberService.getMemberList2Count(mParam);

			n_trec = memCount;

			int n_pagesize = 20;
			int n_pagescnt = 10;
			int n_curpage = 1;
			int n_totalpage = 0;
			int st_num = 0;
			int en_num = 0;

			s_tmp = req_PG;

			if(CommonUtil.isNumeric(s_tmp) == true && s_tmp.length() > 0) {
				n_curpage = Integer.parseInt(s_tmp);
			}
			n_totalpage = Math.round(n_trec / n_pagesize);

			if(n_trec % n_pagesize > 0) {
				n_totalpage = n_totalpage + 1;
			}

			if(n_curpage <= 0) {
				n_curpage = 1;
			}

			if(n_curpage > n_totalpage) {
				n_curpage = n_totalpage;
			}

			st_num = (n_pagesize) * (n_curpage - 1) + 1;
			en_num = (n_pagesize) * (n_curpage);


			HashMap<String, Object> lParam = new HashMap<String, Object>();
			lParam.put("p_Option", (n_pagesize * n_curpage));
			lParam.put("psrc_Kind", src_Kind);
			lParam.put("psrc_Text", src_Text);
			lParam.put("psrc_Cond", src_Sort);
			lParam.put("st_num", st_num);
			lParam.put("en_num", en_num);

			List<MemberVO> memList = memberService.getMemberList2Limit(lParam);

			String url = "/aadmin/memberList?trec=" + req_TREC + "&src_Sort=" + src_Sort + "&src_Kind=" + src_Kind + "&src_Text=" + src_Text;

			String paging = CommonUtil.get_Paging_Tag_New2_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);



			src_Text = tmp;
			mav.addObject("n_trec", n_trec);
			mav.addObject("src_Text", src_Text);
			mav.addObject("curPageName", curPageName);
			mav.addObject("n_curpage", n_curpage);
			mav.addObject("n_pagesize", n_pagesize);
			mav.addObject("memList", memList);
			mav.addObject("paging", paging);
			mav.addObject("src_Kind",src_Kind);
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}

		return mav;
	}

	@RequestMapping("memberView")
	public ModelAndView memberView(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();
		EncLibrary enc = new EncLibrary();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, memberView, userSeq);
		if(admChk == true) {
			int seq = (request.getParameter("Seq") != null) ? Integer.parseInt( CommonUtil.fn_Word_In( request.getParameter("Seq") ) ) : 0;
			if(seq > 0) {
				MemberVO mem = memberService.getMemberList2forSeq(seq);

				if(mem.getEmail() != null && !mem.getEmail().equals("") && !mem.getEmail().equals("null")) {
					mem.setEmail( enc.AlmoneyDecrypt( mem.getEmail() ) );
				}

				if(mem.getPhone() != null && !mem.getPhone().equals("") && !mem.getPhone().equals("null")) {
					mem.setPhone( enc.AlmoneyDecrypt( mem.getPhone() ) );
				}

				mav.addObject("mem", mem);
			}
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}

		return mav;
	}

	@RequestMapping("memberSave")
	public void memberSave(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		EncLibrary enc = new EncLibrary();
		MD5Class md5 = new MD5Class();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, memberView, userSeq);
		if(admChk == true) {
			int seq = (request.getParameter("Seq") != null) ? Integer.parseInt( CommonUtil.fn_Word_In( request.getParameter("Seq") ) ) : 0;
			if(seq > 0) {

				String name = request.getParameter("Name");
				String nickName = request.getParameter("NickName");
				String password = request.getParameter("Password");
				String email = (request.getParameter("Email") != null && !request.getParameter("Email").equals(""))? request.getParameter("Email").trim() : "";
				String chuCode = (request.getParameter("ChuCode") != null && !request.getParameter("ChuCode").equals("")) ? request.getParameter("ChuCode").trim() : "";
				String phone = (request.getParameter("Phone") != null && !request.getParameter("Phone").equals("")) ? request.getParameter("Phone").trim() : "";

				String memberType = (request.getParameter("MemberType") != null && !request.getParameter("MemberType").equals("")) ? request.getParameter("MemberType").trim() : "";
				String nickNameOrg = (request.getParameter("NickNameOrg") != null && !request.getParameter("NickNameOrg").equals("")) ? request.getParameter("NickNameOrg").trim() : "";
				String memo = (request.getParameter("Memo") != null && !request.getParameter("Memo").equals("")) ? CommonUtil.fn_Word_In( request.getParameter("Memo") ) : "";


				if(memo.equals("")) {
					memo = null;
				}

				String userPW_md5 = null;
				if(password.equals("")) {
					userPW_md5 = "";
				}
				else {
					userPW_md5 = md5.md5_encode(password);
				}

				if(!email.equals("")) {
					email = enc.AlmoneyEncrypt(email);
				}

				if(!phone.equals("")) {
					phone = enc.AlmoneyEncrypt(phone);
				}


				HashMap<String, Object> chkNick = memberService.getChkNick(nickName);

				if(!nickName.equals(nickNameOrg) && Integer.parseInt(String.valueOf( chkNick.get("CheckNick2") ) ) > 0) {
					CommonUtil.jspAlert(response, "이미 사용중인 닉네임입니다.", "", "");
				}
				else {
					HashMap<String, Object> param = new HashMap<String, Object>();
					param.put("mem_seq", seq);
					param.put("mem_name", name);
					param.put("mem_nickName", nickName);
					param.put("mem_pw", userPW_md5);
					param.put("mem_email", email);
					param.put("mem_chucode", chuCode);
					param.put("mem_phone", phone);
					param.put("mem_type", memberType);
					param.put("memo", memo);


					int retVal = memberService.setAdminMemUpdate(param);

					//System.out.println("retVal : " + retVal);

					CommonUtil.jspAlertReload(response, "정상적으로 수정되었습니다.", "", "");
				}
			}
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}
	}

	@RequestMapping("member_q")
	public ModelAndView member_q(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();
		EncLibrary enc = new EncLibrary();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, memberView, userSeq);
		if(admChk == true) {
			String curPageName = "/aadmin/member_q";

			String req_TREC = CommonUtil.fn_Word_In( request.getParameter("trec") != null ? request.getParameter("trec").trim() : "0");
			String req_PG = CommonUtil.fn_Word_In( request.getParameter("pg") != null ? request.getParameter("pg").trim() : "");
			String src_Sort = CommonUtil.fn_Word_In( request.getParameter("src_Sort") != null ? request.getParameter("src_Sort").trim() : "");
			String src_OrderBy = CommonUtil.fn_Word_In( request.getParameter("src_OrderBy") != null ? request.getParameter("src_OrderBy").trim() : "");
			String section1 = CommonUtil.fn_Word_In( request.getParameter("Section1") != null ? request.getParameter("Section1").trim() : "" );
			String nickName = CommonUtil.fn_Word_In( request.getParameter("NickName") != null ? request.getParameter("NickName").trim() : "" );

			int seq = Integer.parseInt( CommonUtil.fn_Word_In( request.getParameter("Seq") != null ? request.getParameter("Seq").trim() : "0" )  );


			if(section1.equals("")) {
				section1 = "0";
			}

			if(req_PG.equals("")) {
				req_PG = "1";
			}

			if(src_Sort.equals("")) {
				src_Sort = "Seq";
			}

			if(src_OrderBy.equals("")) {
				src_OrderBy = "DESC";
			}

			String s_tmp = "";
			int n_trec = 0;
			s_tmp = req_TREC;

			if(CommonUtil.isNumeric(s_tmp) && s_tmp.length() > 0) {
				n_trec = Integer.parseInt(s_tmp);
			}

			HashMap<String, Object> qParam = new HashMap<String, Object>();
			qParam.put("section1", section1);
			qParam.put("seq", seq);
			qParam.put("flagUse", "");

			int qCount = questionService.getMemberQlistCount(qParam);

			n_trec = qCount;

			int n_pagesize = 30;
			int n_pagescnt = 10;
			int n_curpage = 1;
			int n_totalpage = 0;
			int st_num = 0;
			int en_num = 0;

			s_tmp = req_PG;

			if(CommonUtil.isNumeric(s_tmp) == true && s_tmp.length() > 0) {
				n_curpage = Integer.parseInt(s_tmp);
			}
			n_totalpage = Math.round(n_trec / n_pagesize);

			if(n_trec % n_pagesize > 0) {
				n_totalpage = n_totalpage + 1;
			}

			if(n_curpage <= 0) {
				n_curpage = 1;
			}

			if(n_curpage > n_totalpage) {
				n_curpage = n_totalpage;
			}

			st_num = (n_pagesize) * (n_curpage - 1) + 1;
			en_num = (n_pagesize) * (n_curpage);


			HashMap<String, Object> lParam = new HashMap<String, Object>();
			lParam.put("p_Option", (n_pagesize * n_curpage));
			lParam.put("psrc_Kind", src_Sort);
			lParam.put("seq", seq);
			lParam.put("flagUse", "");
			lParam.put("section1", section1);
			lParam.put("st_num", st_num);
			lParam.put("en_num", en_num);

			List<QuestionVO> qList = questionService.getMemberQlistLimit(lParam);

			List<SectionVO> sectionVO = commonService.getBoardSection1();

			String url = curPageName + "?trec=" + req_TREC + "&src_Sort=" + src_Sort + "&src_OrderBy=" + src_OrderBy + "&Section1=" + section1 + "&NickName=" + nickName + "&Seq=" + seq;
			String paging = CommonUtil.get_Paging_Tag_New_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);

			mav.addObject("nickName", nickName);
			mav.addObject("n_trec", n_trec);
			mav.addObject("section1", section1);
			mav.addObject("curPageName", curPageName);
			mav.addObject("n_curpage", n_curpage);
			mav.addObject("n_pagesize", n_pagesize);
			mav.addObject("qList", qList);
			mav.addObject("sectionVO", sectionVO);
			mav.addObject("paging", paging);
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}

		return mav;
	}

	@RequestMapping("member_a")
	public ModelAndView member_a(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();
		EncLibrary enc = new EncLibrary();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, memberView, userSeq);
		if(admChk == true) {
			String curPageName = "/aadmin/member_a";

			String req_TREC = CommonUtil.fn_Word_In( request.getParameter("trec") != null ? request.getParameter("trec").trim() : "0");
			String req_PG = CommonUtil.fn_Word_In( request.getParameter("pg") != null ? request.getParameter("pg").trim() : "");
			String src_Sort = CommonUtil.fn_Word_In( request.getParameter("src_Sort") != null ? request.getParameter("src_Sort").trim() : "");
			String src_OrderBy = CommonUtil.fn_Word_In( request.getParameter("src_OrderBy") != null ? request.getParameter("src_OrderBy").trim() : "");
			String flagChoice = CommonUtil.fn_Word_In( request.getParameter("FlagChoice") != null ? request.getParameter("FlagChoice").trim() : "" );
			String nickName = CommonUtil.fn_Word_In( request.getParameter("NickName") != null ? request.getParameter("NickName").trim() : "" );

			int seq = Integer.parseInt( CommonUtil.fn_Word_In( request.getParameter("Seq") != null ? request.getParameter("Seq").trim() : "0" )  );


			if(req_PG.equals("")) {
				req_PG = "1";
			}

			if(src_Sort.equals("")) {
				src_Sort = "Seq";
			}

			if(src_OrderBy.equals("")) {
				src_OrderBy = "DESC";
			}

			String s_tmp = "";
			int n_trec = 0;
			s_tmp = req_TREC;

			if(CommonUtil.isNumeric(s_tmp) && s_tmp.length() > 0) {
				n_trec = Integer.parseInt(s_tmp);
			}

			HashMap<String, Object> aParam = new HashMap<String, Object>();
			aParam.put("flagChoice", flagChoice);
			aParam.put("seq", seq);
			aParam.put("flagUse", "");

			int aCount = answerService.getMemberAlistCount(aParam);

			n_trec = aCount;

			int n_pagesize = 30;
			int n_pagescnt = 10;
			int n_curpage = 1;
			int n_totalpage = 0;
			int st_num = 0;
			int en_num = 0;

			s_tmp = req_PG;

			if(CommonUtil.isNumeric(s_tmp) == true && s_tmp.length() > 0) {
				n_curpage = Integer.parseInt(s_tmp);
			}
			n_totalpage = Math.round(n_trec / n_pagesize);

			if(n_trec % n_pagesize > 0) {
				n_totalpage = n_totalpage + 1;
			}

			if(n_curpage <= 0) {
				n_curpage = 1;
			}

			if(n_curpage > n_totalpage) {
				n_curpage = n_totalpage;
			}

			st_num = (n_pagesize) * (n_curpage - 1) + 1;
			en_num = (n_pagesize) * (n_curpage);


			HashMap<String, Object> lParam = new HashMap<String, Object>();
			lParam.put("p_Option", (n_pagesize * n_curpage));
			lParam.put("seq", seq);
			lParam.put("flagUse", "");
			lParam.put("flagChoice", flagChoice);
			lParam.put("st_num", st_num);
			lParam.put("en_num", en_num);

			List<AnswerVO> aList = answerService.getMemberAlistLimit(lParam);

			List<SectionVO> sectionVO = commonService.getBoardSection1();

			String url = curPageName + "?trec=" + req_TREC + "&src_Sort=" + src_Sort + "&src_OrderBy=" + src_OrderBy + "&FlagChoice=" + flagChoice + "&NickName=" + nickName + "&Seq=" + seq;
			String paging = CommonUtil.get_Paging_Tag_New_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);

			mav.addObject("nickName", nickName);
			mav.addObject("n_trec", n_trec);
			mav.addObject("flagChoice", flagChoice);
			mav.addObject("curPageName", curPageName);
			mav.addObject("n_curpage", n_curpage);
			mav.addObject("n_pagesize", n_pagesize);
			mav.addObject("aList", aList);
			mav.addObject("sectionVO", sectionVO);
			mav.addObject("paging", paging);

		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}

		return mav;
	}

	@RequestMapping("member_v")
	public ModelAndView member_v(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();
		EncLibrary enc = new EncLibrary();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, memberView, userSeq);
		if(admChk == true) {

			String curPageName = "/member/myView";

			String req_TREC = CommonUtil.fn_Word_In( request.getParameter("trec") != null ? request.getParameter("trec").trim() : "0");
			String req_PG = CommonUtil.fn_Word_In( request.getParameter("pg") != null ? request.getParameter("pg").trim() : "");
			String src_Sort = CommonUtil.fn_Word_In( request.getParameter("src_Sort") != null ? request.getParameter("src_Sort").trim() : "");
			String src_OrderBy = CommonUtil.fn_Word_In( request.getParameter("src_OrderBy") != null ? request.getParameter("src_OrderBy").trim() : "");
			String section1 = CommonUtil.fn_Word_In( request.getParameter("Section1") != null ? request.getParameter("Section1").trim() : "" );
			String nickName = CommonUtil.fn_Word_In( request.getParameter("NickName") != null ? request.getParameter("NickName").trim() : "" );

			int seq = Integer.parseInt( CommonUtil.fn_Word_In( request.getParameter("Seq") != null ? request.getParameter("Seq").trim() : "0" )  );

			if(section1.equals("")) {
				section1 = "0";
			}

			if(req_PG.equals("")) {
				req_PG = "1";
			}

			if(src_Sort.equals("")) {
				src_Sort = "Seq";
			}

			if(src_OrderBy.equals("")) {
				src_OrderBy = "DESC";
			}

			String s_tmp = "";
			int n_trec = 0;
			s_tmp = req_TREC;

			if(CommonUtil.isNumeric(s_tmp) && s_tmp.length() > 0) {
				n_trec = Integer.parseInt(s_tmp);
			}

			HashMap<String, Object> aParam = new HashMap<String, Object>();
			aParam.put("section1", section1);
			aParam.put("seq", seq);

			int aCount = answerService.getMemberVlistCount(aParam);

			n_trec = aCount;

			int n_pagesize = 30;
			int n_pagescnt = 10;
			int n_curpage = 1;
			int n_totalpage = 0;
			int st_num = 0;
			int en_num = 0;

			s_tmp = req_PG;

			if(CommonUtil.isNumeric(s_tmp) == true && s_tmp.length() > 0) {
				n_curpage = Integer.parseInt(s_tmp);
			}
			n_totalpage = Math.round(n_trec / n_pagesize);

			if(n_trec % n_pagesize > 0) {
				n_totalpage = n_totalpage + 1;
			}

			if(n_curpage <= 0) {
				n_curpage = 1;
			}

			if(n_curpage > n_totalpage) {
				n_curpage = n_totalpage;
			}

			st_num = (n_pagesize) * (n_curpage - 1) + 1;
			en_num = (n_pagesize) * (n_curpage);


			HashMap<String, Object> lParam = new HashMap<String, Object>();
			lParam.put("p_Option", (n_pagesize * n_curpage));
			lParam.put("seq", seq);
			lParam.put("section1", section1);
			lParam.put("st_num", st_num);
			lParam.put("en_num", en_num);

			List<AnswerVO> aList = answerService.getMemberVlistLimit(lParam);

			List<SectionVO> sectionVO = commonService.getBoardSection1();

			String url = "/aadmin/member_v?trec=" + req_TREC + "&src_Sort=" + src_Sort + "&src_OrderBy=" + src_OrderBy + "&Section1=" + section1 + "&NickName=" + nickName + "&Seq=" + seq;
			String paging = CommonUtil.get_Paging_Tag_New_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);

			mav.addObject("nickName", nickName);
			mav.addObject("n_trec", n_trec);
			mav.addObject("section1", section1);
			mav.addObject("curPageName", curPageName);
			mav.addObject("n_curpage", n_curpage);
			mav.addObject("n_pagesize", n_pagesize);
			mav.addObject("aList", aList);
			mav.addObject("sectionVO", sectionVO);
			mav.addObject("paging", paging);
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}

		return mav;
	}

	@RequestMapping("memberViewAlmoney")
	public ModelAndView memberViewAlmoney(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, memberViewAlmoney, userSeq);
		if(admChk == true) {
			int seq = Integer.parseInt( (request.getParameter("Seq") != null && !request.getParameter("Seq").equals("")) ? request.getParameter("Seq") : "0" );

			if(seq > 0) {
				MemberVO m = memberService.getMemSeqAndName(seq);

				seq = m.getSeq();
				String name = m.getName();

				mav.addObject("seq", seq);
				mav.addObject("name", name);
			}
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}

		return mav;
	}

	@RequestMapping(value="memberViewAlmoneyAjax", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String memberViewAlmoneyAjax(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String act = request.getParameter("ACT");
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, memberViewAlmoney, userSeq);

		if(admChk == true) {
			if(act.equals("UpdateAlmoney")) {
				int seq = Integer.parseInt( (request.getParameter("Seq") != null && !request.getParameter("Seq").equals("")) ? request.getParameter("Seq") : "0" );

				if(seq > 0) {
					BigDecimal mem_almoney = BigDecimal.ZERO;

					if(request.getParameter("Almoney") != null && !request.getParameter("Almoney").equals("")) {
						mem_almoney = new BigDecimal( request.getParameter("Almoney") );
					}

					String almoney_flag = "";

					if(request.getParameter("AlmoneyFlag") != null) {
						almoney_flag = request.getParameter("AlmoneyFlag");
					}

					if(mem_almoney.compareTo(BigDecimal.ZERO) > 0 && !almoney_flag.equals("")) {
						HashMap<String, Object> param = new HashMap<String, Object>();

						BigDecimal minusNum = new BigDecimal(-1.0);
						if(almoney_flag.equals("51")) { mem_almoney = mem_almoney.multiply(minusNum); }

						param.put("mem_seq", seq);
						param.put("mem_almoney", mem_almoney);
						param.put("almoney_flag", almoney_flag);

						//System.out.println("mem_seq : " + param.get("mem_seq"));
						//System.out.println("mem_almoney : " + param.get("mem_almoney"));
						//System.out.println("almoney_flag : " + param.get("almoney_flag"));

						int returnCode = commonService.setUpdateAlmoney(param);
						//int returnCode = 1;

						switch(returnCode) {
							case 1:
								r_res.put("result", returnCode);
								result = CommonUtil.libJsonExit(act, r_res);
								break;
							case 2:
								result = CommonUtil.libJsonExit("알머니를 입력해주세요.", r_res);
								break;
							default:
								result = CommonUtil.libJsonExit("죄송합니다.\\n알수 없는 오류가 발생하여 알머니 지급이 완료되지 않았습니다.", r_res);
								break;
						}
					}
					else {
						result = CommonUtil.libJsonExit("입력정보를 확인하여 주세요.", r_res);
					}
				}
			}
		} else {
			result = CommonUtil.libJsonExit("접근 권한이 없습니다.", r_res);
		}

		return result;
	}

	@RequestMapping("memberViewLv")
	public ModelAndView memberViewLv(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, memberViewLv, userSeq);
		if(admChk == true) {
			int seq = Integer.parseInt( (request.getParameter("Seq") != null && !request.getParameter("Seq").equals("")) ? request.getParameter("Seq") : "0" );

			if(seq > 0) {
				MemberVO m = memberService.getMemSeqAndName(seq);

				seq = m.getSeq();
				String name = m.getName();
				String lv = m.getLv();

				mav.addObject("seq", seq);
				mav.addObject("name", name);
				mav.addObject("lv", lv);
			}
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}

		return mav;
	}

	@RequestMapping(value="memberViewLvAjax", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String memberViewLvAjax(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String act = request.getParameter("ACT");
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, memberViewLv, userSeq);

		if(admChk == true) {
			if(act.equals("UpdateLv")) {
				int seq = Integer.parseInt( (request.getParameter("Seq") != null && !request.getParameter("Seq").equals("")) ? request.getParameter("Seq") : "0" );

				if(seq > 0) {
					String strLv = request.getParameter("Lv");
					String strLv_flag = request.getParameter("LvFlag");

					if(!strLv.equals("") && !strLv_flag.equals("")) {

						HashMap<String, Object> param = new HashMap<String, Object>();
						param.put("mem_seq", seq);
						param.put("mem_lv", strLv);
						param.put("mem_lv_flag", strLv_flag);
						param.put("userSeq", userSeq);
						//System.out.println("mem_seq : " + param.get("mem_seq"));
						//System.out.println("mem_lv : " + param.get("mem_lv"));
						//System.out.println("mem_lv_flag : " + param.get("mem_lv_flag"));
						//System.out.println("userSeq : " + param.get("userSeq"));

						int returnCode = memberService.setMemUpdateLv(param);
						//int returnCode = 1;
						//System.out.println("returnCode : " + returnCode);
						switch(returnCode) {
							case 0:
								r_res.put("result", 1);
								result = CommonUtil.libJsonExit(act, r_res);
								break;
							case 1:
								r_res.put("result", returnCode);
								result = CommonUtil.libJsonExit(act, r_res);
								break;
							case 2:
								result = CommonUtil.libJsonExit("레벨 변경 정보를 선택해주세요.", r_res);
								break;
							case 3:
								result = CommonUtil.libJsonExit("레벨을 입력해주세요.", r_res);
								break;
							default:
								result = CommonUtil.libJsonExit("죄송합니다.\\n알수 없는 오류가 발생하여 레벨 변경 처리가 완료되지 않았습니다.", r_res);
								break;
						}

					}
					else {
						result = CommonUtil.libJsonExit("입력정보를 확인하여 주세요..", r_res);
					}
				}
			}
		} else {
			result = CommonUtil.libJsonExit("접근 권한이 없습니다.", r_res);
		}

		return result;
	}

	@RequestMapping(value="memberLeave", method = {RequestMethod.GET, RequestMethod.POST})
	public void memberLeave(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용
		HttpSession session = request.getSession(true);
		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;
		int userLv = 0;
		boolean chk = true;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);

			if(globalVal != null) {
				userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
				userLv = Integer.parseInt(String.valueOf( globalVal.get("UserLv") ) );
			}
			else {
				CommonUtil.jspAlert(response, "관리자 권한이 올바르지 않습니다!", "back", "top");
				chk = false;
			}
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, configInput, userSeq);
		if(admChk == true) {
			if(userLv != 99) {
				CommonUtil.jspAlert(response, "관리자 권한이 올바르지 않습니다!", "back", "top");
				chk = false;
			}
			int seq = Integer.parseInt( (request.getParameter("Seq") != null && !request.getParameter("Seq").equals("")) ? request.getParameter("Seq") : "0" );
			if(seq == 0) {
				CommonUtil.jspAlert(response, "전달인자(회원번호) 정의가 올바르지 않습니다!", "back", "top");
				chk = false;
			}

			if(chk == true) {
				String format = "yyyy-MM-dd aa hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
				Calendar today = Calendar.getInstance();
				SimpleDateFormat type = new SimpleDateFormat(format);
				String regDate = type.format(today.getTime());

				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("dt", regDate);
				param.put("seq", seq);

				memberService.updateLeave(param);

				LocalTime currentTime = LocalTime.now();
				CommonUtil.jspAlert(response, "회원 탙퇴 처리하였습니다.", "/aadmin/memberList?time=" + currentTime, "top");
			}

		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}
	}

	// 회원 목록 검색 기능 개선후 재 개발 예정(후 순위 개발 - 2020-11-06)
	@RequestMapping("excel")
	public ModelAndView excel(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();
		EncLibrary enc = new EncLibrary();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, memberList, userSeq);
		if(admChk == true) {

			String curPageName = "/aadmin/memberList";

			String req_TREC = CommonUtil.fn_Word_In( request.getParameter("trec") != null ? request.getParameter("trec").trim() : "0");
			String req_PG = CommonUtil.fn_Word_In( request.getParameter("pg") != null ? request.getParameter("pg").trim() : "");
			String src_Sort = CommonUtil.fn_Word_In( request.getParameter("src_Sort") != null ? request.getParameter("src_Sort").trim() : "");
			String src_Kind = CommonUtil.fn_Word_In( request.getParameter("src_Kind") != null ? request.getParameter("src_Kind").trim() : "");
			String src_Text = CommonUtil.fn_Word_In( request.getParameter("src_Text") != null ? request.getParameter("src_Text").trim() : "" );

			if(req_PG == "") {
				req_PG = "1";
			}

			if(src_Sort.equals("")) {
				src_Sort = "Seq";
			}

			String s_tmp = "";
			int n_trec = 0;
			s_tmp = req_TREC;



			HashMap<String, Object> mParam = new HashMap<String, Object>();
			mParam.put("psrc_Kind", src_Kind);
			mParam.put("psrc_Text", src_Text);

			int memCount = memberService.getMemberListCount(mParam);

			n_trec = memCount;

			int n_pagesize = 10000;
			int n_pagescnt = 10000;
			int n_curpage = 1;
			int n_totalpage = 0;
			int st_num = 0;
			int en_num = 0;

			s_tmp = req_PG;

			if(CommonUtil.isNumeric(s_tmp) && s_tmp.length() > 0) {
				n_trec = Integer.parseInt(s_tmp);
			}

			if(!src_Kind.equals("")) {
				if(src_Kind.equals("Phone")) {
					src_Text = enc.AlmoneyDecrypt(src_Text);
				}
			}


			if(n_trec % n_pagesize > 0) {
				n_totalpage = n_totalpage + 1;
			}

			if(n_curpage <= 0) {
				n_curpage = 1;
			}

			if(n_curpage > n_totalpage) {
				n_curpage = n_totalpage;
			}

			st_num = (n_pagesize) * (n_curpage - 1) + 1;
			en_num = (n_pagesize) * (n_curpage);

			HashMap<String, Object> lParam = new HashMap<String, Object>();
			lParam.put("p_Option", (n_pagesize * n_curpage));
			lParam.put("src_Kind", src_Kind);
			lParam.put("psrc_Text", src_Text);
			lParam.put("psrc_Cond", src_Sort);
			lParam.put("st_num", st_num);
			lParam.put("en_num", en_num);

			List<MemberVO> memList = memberService.getMemberExcelListLimit(lParam);

			mav.addObject("n_trec", n_trec);
			mav.addObject("src_Text", src_Text);
			mav.addObject("curPageName", curPageName);
			mav.addObject("n_curpage", n_curpage);
			mav.addObject("n_pagesize", n_pagesize);
			mav.addObject("memList", memList);

		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}

		return mav;
	}

	@RequestMapping("memberCertList")
	public ModelAndView memberCertList(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();
		EncLibrary enc = new EncLibrary();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, memberCertList, userSeq);
		if(admChk == true) {

			String curPageName = "/aadmin/memberCertList";
			int maxRow = 50;

			mav.addObject("curPageName", curPageName);
			mav.addObject("maxRow", maxRow);
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}

		return mav;
	}

	@RequestMapping(value="memberCertListAjax", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String memberCertListAjax(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String act = request.getParameter("ACT");
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		String curPageName = "/aadmin/memberCertList";
		int maxRow = 50;

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, memberCertList, userSeq);
		if(admChk == true) {

			if(act.equals("GetMemberCert")) {
				int offset = ( Integer.parseInt(request.getParameter("pg")) - 1) * Integer.parseInt( request.getParameter("H_MaxRow"));

				int hSeq = (request.getParameter("H_Seq") != null && !request.getParameter("H_Seq").equals("")) ? Integer.parseInt(request.getParameter("H_Seq")) : 0;
				int hMaxRow = (request.getParameter("H_MaxRow") != null && !request.getParameter("H_MaxRow").equals("")) ? Integer.parseInt(request.getParameter("H_MaxRow")) : maxRow;

				List<MemberCertVO> list = null;
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("offset", offset);
				param.put("maxRow", hMaxRow);

				//getMemCertListAll 의 조건중 CertStatus != 0 => C.id_card != '' 로 변경함
				if(hSeq > 0) {
					param.put("seq", hSeq);
					list = memberService.getMemCertListBySeq(param);
				}
				else {
					list = memberService.getMemCertListAll(param);
				}

				r_res.put("pg", request.getParameter("pg"));
				r_res.put("rows", list);

				result = CommonUtil.libJsonExit(act, r_res);
			}

			// CertStatus = 1 : 인증대기, 2 : 인증완료, 3 : 인증거부
			if(act.equals("CertOK")) {
				int hSeq = (request.getParameter("H_MemberSeq") != null && !request.getParameter("H_MemberSeq").equals("")) ? Integer.parseInt(request.getParameter("H_MemberSeq")) : 0;

				HashMap<String, Object> r = null;
				if(hSeq > 0) {
					r = memberService.setMemCertConfirmBySeq(hSeq);
				}

				result = CommonUtil.libJsonExit(act, r);
			}

			if(act.equals("CertNO")) {
				String sql_check = "";
				if(request.getParameter("chk_IDCard") != null && request.getParameter("chk_IDCard").equals("Y")) {
					sql_check += ",id_card = ''";
				}
				if(request.getParameter("chk_AddiInfo") != null && request.getParameter("chk_AddiInfo").equals("Y")) {
					sql_check += ",Job = ''";
				}
				if(sql_check.equals("")) {
					result = CommonUtil.libJsonExit("거부 사유를 체크해주세요.", r_res);
				}

				int hSeq = (request.getParameter("H_MemberSeq") != null && !request.getParameter("H_MemberSeq").equals("")) ? Integer.parseInt(request.getParameter("H_MemberSeq")) : 0;

				String actMessage = (request.getParameter("H_actMessage") != null && !request.getParameter("H_actMessage").equals("")) ? request.getParameter("H_actMessage") : "";

				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("seq", hSeq);
				param.put("message", actMessage);
				param.put("sql_check", sql_check);

				HashMap<String, Object> r = null;
				if(!sql_check.equals("") && hSeq > 0) {
					r = memberService.setMemCertRejectBySeq(param);
				}

				result = CommonUtil.libJsonExit(act, r);
			}

			if(act.equals("GetAddiInfo")) {
				int hSeq = (request.getParameter("H_MemberSeq") != null && !request.getParameter("H_MemberSeq").equals("")) ? Integer.parseInt(request.getParameter("H_MemberSeq")) : 0;

				MemberCertVO r = null;
				if(hSeq > 0) {;
					r = memberService.getMemCertAddInfo(hSeq);
				}

				result = CommonUtil.libJsonExit(act, r);
			}

			if(act.equals("GetCancelMsgInfo")) {
				int hSeq = (request.getParameter("H_MemberSeq") != null && !request.getParameter("H_MemberSeq").equals("")) ? Integer.parseInt(request.getParameter("H_MemberSeq")) : 0;

				String r = "";
				if(hSeq > 0) {
					r = memberService.getMemCertCancelMsgInfo(hSeq);
				}

				result = CommonUtil.libJsonExit(act, r);
			}

			if(act.equals("GetCancelMsg")) {
				int hSeq = (request.getParameter("H_MemberSeq") != null && !request.getParameter("H_MemberSeq").equals("")) ? Integer.parseInt(request.getParameter("H_MemberSeq")) : 0;

				String r = "";
				if(hSeq > 0) {
					r = memberService.getMemCertCancelMsg(hSeq);
				}

				result = CommonUtil.libJsonExit(act, r);
			}
		}
		else {
			result = CommonUtil.libJsonExit("접근 권한이 없습니다.", r_res);
		}

		return result;
	}

	@RequestMapping("adminConfig")
	public ModelAndView adminConfig(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, memberCertList, userSeq);
		if(admChk == false) {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}

		int maxRow = 50;
		mav.addObject("maxRow", maxRow);
		mav.addObject("authority_Cnt", authority_Cnt);

		return mav;
	}

	@RequestMapping(value="adminConfigAjax", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String adminConfigAjax(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String act = request.getParameter("ACT");
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		String curPageName = "/aadmin/adminConfig";
		int maxRow = 50;

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, memberCertList, userSeq);
		if(admChk == true) {

			if(act.equals("GetAdminInfo")) {
				int offset = ( Integer.parseInt(request.getParameter("pg")) - 1) * Integer.parseInt( request.getParameter("H_MaxRow"));

				//int hSeq = (request.getParameter("H_Seq") != null && !request.getParameter("H_Seq").equals("")) ? Integer.parseInt(request.getParameter("H_Seq")) : 0;
				int hMaxRow = (request.getParameter("H_MaxRow") != null && !request.getParameter("H_MaxRow").equals("")) ? Integer.parseInt(request.getParameter("H_MaxRow")) : maxRow;

				List<MemberAdminVO> list = null;
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("offset", offset);
				param.put("maxRow", hMaxRow);

				list = memberService.getAdmListAll(param);

				r_res.put("pg", request.getParameter("pg"));
				r_res.put("rows", list);

				result = CommonUtil.libJsonExit(act, r_res);
			}

			// CertStatus = 1 : 인증대기, 2 : 인증완료, 3 : 인증거부
			if(act.equals("AddAdmin")) {
				int hSeq = (request.getParameter("H_AddAdminSeq") != null && !request.getParameter("H_AddAdminSeq").equals("")) ? Integer.parseInt(request.getParameter("H_AddAdminSeq")) : 0;
				//System.out.println("hSeq : " + hSeq);
				HashMap<String, Object> r = new HashMap<String, Object>();
				if(hSeq > 0) {
					int res = memberService.addAdmin(hSeq);

					switch(res) {
						case 1:
							r.put("ReturnCode", res);
							result = CommonUtil.libJsonExit(act, r);
							break;
						case 2:
							result = CommonUtil.libJsonExit("회원 정보가 올바르지 않습니다.", r);
							break;
						case 3:
							result = CommonUtil.libJsonExit("해당 회원은 이미 관리자로 등록되어 있습니다.", r);
							break;
						case 4:
							result = CommonUtil.libJsonExit("관리자 추가에 실패하였습니다.", r);
							break;
						default:
							result = CommonUtil.libJsonExit("죄송합니다.\\n알수 없는 오류가 발생하여 관리자 추가가 완료되지 않았습니다.", r);
					}
				}
			}

			if(act.equals("AuthorityModify")) {
				int hSeq = (request.getParameter("H_MemberSeq") != null && !request.getParameter("H_MemberSeq").equals("")) ? Integer.parseInt(request.getParameter("H_MemberSeq")) : 0;

				HashMap<String, Object> r = null;

				if(hSeq > 0) {
					String authority = memberService.getAuthorityBySeq(hSeq);
					//System.out.println("authority : " + authority);
					int n = 0;
					if(authority.equals("*")) {
						n = (int)(Math.pow(2, authority_Cnt) - 1);
					}
					else {
						n = Integer.parseInt(authority);
					}
					String author = Integer.toBinaryString(n);

					String[] bitArray = new String[authority_Cnt];
					for(int i = 0; i < authority_Cnt; i++) {
						if(i < author.length()) {
							//System.out.println((author.length() - i - 1) + " : " + ( author.charAt( author.length() - i - 1 ) == '1') );
							bitArray[i] = author.charAt( author.length() - i - 1 ) == '1' ? "true": "false";
						}
						else {
							//System.out.println(i + " : false");
							bitArray[i] = "false";
						}
					}

					result = CommonUtil.libJsonExit(act, bitArray);
				}
			}

			if(act.equals("AuthorityModifyOK")) {
				int hSeq = (request.getParameter("H_MemberSeq") != null && !request.getParameter("H_MemberSeq").equals("")) ? Integer.parseInt(request.getParameter("H_MemberSeq")) : 0;

				String[] chk_Authority = (request.getParameterValues("chk_Authority[]") != null && !request.getParameterValues("chk_Authority[]").equals("")) ? request.getParameterValues("chk_Authority[]") : null;
				int modify_Authority = 0;
				//System.out.println("chk_Authority : " + chk_Authority);
				if(chk_Authority != null) {
					for(int i = 0; i < chk_Authority.length; i++) {
						modify_Authority += (int)(Math.pow(2, Integer.parseInt(chk_Authority[i]) ));
					}
				}
				//System.out.println("modify_Authority : " + modify_Authority);
				HashMap<String, Object> r = new HashMap<String, Object>();
				if(hSeq > 0) {;
					HashMap<String, Object> param = new HashMap<String, Object>();
					param.put("authority", modify_Authority);
					param.put("seq", hSeq);

					int res = memberService.setAuthority(param);

					r.put("result", res);
				}

				result = CommonUtil.libJsonExit(act, r);
			}

			if(act.equals("DeleteAdmin")) {
				int hSeq = (request.getParameter("H_MemberSeq") != null && !request.getParameter("H_MemberSeq").equals("")) ? Integer.parseInt(request.getParameter("H_MemberSeq")) : 0;

				HashMap<String, Object> r = new HashMap<String, Object>();
				if(hSeq > 0) {
					int res = memberService.deleteAuthority(hSeq);

					r.put("result", res);
				}

				result = CommonUtil.libJsonExit(act, r);
			}
		}
		else {
			result = CommonUtil.libJsonExit("접근 권한이 없습니다.", r_res);
		}

		return result;
	}

	@RequestMapping("recommend/recommendView")
	public ModelAndView recommendView(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();
		EncLibrary enc = new EncLibrary();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, recommendView, userSeq);
		if(admChk == true) {
			int memberCnt = memberService.getRecommendTotal();

			HashMap<String, Object> logAlmoney = commonService.getTotalAlmoney();

			BigDecimal totalAlmoney = new BigDecimal( String.valueOf( logAlmoney.get("Total") ) );

			CodeUtil code = new CodeUtil(request);

			mav.addObject("memberCnt", memberCnt);
			mav.addObject("totalAlmoney", totalAlmoney);
			mav.addObject("codeMemLvNm", code.getCODE_MEM_LV_NM());
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}

		return mav;
	}

	@RequestMapping(value="getTreeData", produces="application/json;charset=UTF-8", method = RequestMethod.GET)
	public @ResponseBody String getTreeData(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();
		EncLibrary enc = new EncLibrary();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, recommendView, userSeq);
		if(admChk == true) {

			String rootNode = request.getParameter("rootNode") != null ? request.getParameter("rootNode") : "";
			String flagParent = request.getParameter("flagParent") != null ? request.getParameter("flagParent") : "";

			if(!rootNode.equals("")) {
				List<HashMap<String, Object>> rs = memberService.getRecommendChildView( Integer.parseInt( rootNode ));

				List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
				for(HashMap<String, Object> obj : rs) {
					Iterator<String> iteratorK = obj.keySet().iterator();

					HashMap<String, Object> obj_in = new HashMap<String, Object>();

					while (iteratorK.hasNext()) {
	    				String key = iteratorK.next();
	    				Object val = obj.get(key);
	    				if(key.equals("DateReg")) {
	    					val = ""+val+"";
	    				}
	    				if(key.equals("Phone")) {
	    					val = enc.AlmoneyDecrypt( String.valueOf( val ) );
	    				}
	    				obj_in.put(key, val);
	    			}
					list.add(obj_in);
				}

				result = new Gson().toJson(list);
			}
		}
		else {
			result = CommonUtil.libJsonExit("접근 권한이 없습니다.", r_res);
		}

		return result;
	}

	@RequestMapping(value="getMemberSeq", produces="application/json;charset=UTF-8", method = RequestMethod.GET)
	public @ResponseBody String getMemberSeq(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		EncLibrary enc = new EncLibrary();
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, recommendView, userSeq);
		if(admChk == true) {

			String searchType = request.getParameter("searchType") != null ? request.getParameter("searchType") : "";
			String searchTarget = request.getParameter("searchTarget") != null ? request.getParameter("searchTarget") : "";
			//System.out.println("searchType : " + searchType);
			//System.out.println("searchTarget : " + searchTarget);
			if(searchType.equals("Phone")) {
				searchTarget = enc.AlmoneyEncrypt(searchTarget);
			}

			if(!searchTarget.equals("") && !searchType.equals("")) {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("searchType", searchType);
				param.put("searchTarget", searchTarget);

				String memberSeq = memberService.getRecommendSeq(param);

				int nMemberSeq = 0;
				if(!memberSeq.equals("")) {
					nMemberSeq = Integer.parseInt(memberSeq);
				}

				String parentSeq = memberService.getRecommendParent(nMemberSeq);

				int nParentSeq = -1;
				if(!parentSeq.equals("")) {
					nParentSeq = Integer.parseInt(parentSeq);
				}

				r_res.put("ParentSeq", nParentSeq);
				r_res.put("MemberSeq", nMemberSeq);

				result = new Gson().toJson(r_res);
				//System.out.println("ParentSeq : " + r_res.get("ParentSeq"));
				//System.out.println("MemberSeq : " + r_res.get("MemberSeq"));
			}
		}
		else {
			result = CommonUtil.libJsonExit("접근 권한이 없습니다.", r_res);
		}

		return result;
	}

	@RequestMapping(value="resetTree", method = RequestMethod.POST)
	public void resetTree(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용
		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, recommendView, userSeq);
		if(admChk == true) {
			memberService.setRecommendTreeReset();
		}
	}

	@RequestMapping(value="changeTree", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String changeTree(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		EncLibrary enc = new EncLibrary();
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, recommendView, userSeq);
		if(admChk == true) {

			String action = request.getParameter("action") != null ? request.getParameter("action") : "";
			String target1 = request.getParameter("target1") != null ? request.getParameter("target1") : "";
			String target2 = request.getParameter("target2") != null ? request.getParameter("target2") : "";


			if(!action.equals("") && !target1.equals("") && !target2.equals("")) {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("node_1", target1);
				param.put("node_2", target2);

				HashMap<String, Object>  res = new HashMap<String, Object>();

				if(action.equals("switch2nodeWithChild")) {
					param.put("child_flag", "Y");

					res = memberService.setRecommendModeSwitch(param);
				}
				else if(action.equals("switch2nodeWithOutChild")) {
					param.put("child_flag", "N");

					res = memberService.setRecommendModeSwitch(param);
				}
				else if(action.equals("changeNode")) {
					res = memberService.setRecommendModeChange(param);
				}
				else if(action.equals("append")) {
					String format = "yyyy-MM-dd hh:mm:ss";
					Calendar today = Calendar.getInstance();
					SimpleDateFormat type = new SimpleDateFormat(format);
					String now = type.format(today.getTime());

					param.put("date_reg", now);

					res = memberService.setRecommendModeAppend(param);
				}

				r_res.put("result", res.get("RetCode"));

				result = new Gson().toJson(r_res);
				//System.out.println("result : " + result);
			}
		}
		else {
			result = CommonUtil.libJsonExit("접근 권한이 없습니다.", r_res);
		}

		return result;
	}

	@RequestMapping(value="backUpTree", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String backUpTree(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		//EncLibrary enc = new EncLibrary();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, recommendView, userSeq);
		if(admChk == true) {

			//String action = request.getParameter("action") != null ? request.getParameter("action") : "";
			//String target1 = request.getParameter("target1") != null ? request.getParameter("target1") : "";
			//String target2 = request.getParameter("target2") != null ? request.getParameter("target2") : "";
			String memo = request.getParameter("memo") != null ? request.getParameter("memo") : "";

			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("memo", memo);

			String res = memberService.setRecommendBackup(param);

			r_res.put("result", res);

			result = new Gson().toJson(r_res);
		}
		else {
			result = CommonUtil.libJsonExit("접근 권한이 없습니다.", r_res);
		}

		return result;
	}

	@RequestMapping(value="getBackUpList", produces="application/json;charset=UTF-8", method = RequestMethod.GET)
	public @ResponseBody String getBackUpList(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		//EncLibrary enc = new EncLibrary();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, recommendView, userSeq);
		if(admChk == true) {
			List<HashMap<String, Object>> list = memberService.getRecommendBackupList();

			result = new Gson().toJson(list);
			//System.out.println("result : " + result);
		}
		else {
			result = CommonUtil.libJsonExit("접근 권한이 없습니다.", r_res);
		}

		return result;
	}

	@RequestMapping(value="restoreTree", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String restoreTree(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		//EncLibrary enc = new EncLibrary();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, recommendView, userSeq);
		if(admChk == true) {
			String target = request.getParameter("target") != null ? request.getParameter("target") : "";

			String res = memberService.setRecommendRestore(target);

			r_res.put("result", res);

			result = new Gson().toJson(r_res);
		}
		else {
			result = CommonUtil.libJsonExit("접근 권한이 없습니다.", r_res);
		}

		return result;
	}

	@RequestMapping("exchange/exchReadyList")
	public ModelAndView exchReadyList(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용
		//EncLibrary enc = new EncLibrary();
		CodeUtil code = new CodeUtil(request);

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, exchReadyList, userSeq);
		if(admChk == true) {
			List<HashMap<String, Object>> list = configService.getConfigLv();

			mav.addObject("cfgExch", new Gson().toJson(list));
			mav.addObject("maxRow", 50);

			mav.addObject("codeCd", code.getCODE_MEM_LV_CD());
			mav.addObject("codeNm", code.getCODE_MEM_LV_NM());
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}

		return mav;
	}

	@RequestMapping("exchange/exchangeAskList")
	public ModelAndView exchangeAskList(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();
		//EncLibrary enc = new EncLibrary();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, exchangeAskList, userSeq);
		if(admChk == true) {

		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}

		return mav;
	}

	@RequestMapping("exchange/idCardView")
	public ModelAndView idCardView(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();
		EncLibrary enc = new EncLibrary();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, memberCertList, userSeq);
		if(admChk == true) {
			int memberSeq = (request.getParameter("MemberSeq") != null && !request.getParameter("MemberSeq").equals("")) ? Integer.parseInt(request.getParameter("MemberSeq")) : 0;

			if(memberSeq > 0) {
				String[] fileList = null;
				String prefix = "";

				String r = memberService.getMemCertIdCard(memberSeq);

				if(r != null) {
					if(!r.equals("OLD")) {
						fileList = r.split("\\*");
						prefix = "/UploadFile/Cert/";
					}
					else {
						List<FileVO> files = fileService.getOldFiles(memberSeq);
						fileList = new String[files.size()];
						for(int i = 0; i < files.size(); i++) {
							fileList[i] = files.get(i).getFileName();
						}
						prefix = "/UploadFile/Exchange/";
					}
				}

				int fileCount = 0;
				if(fileList != null) {
					fileCount = fileList.length;
				}

				mav.addObject("fCnt", fileCount);
				mav.addObject("prefix", prefix);
				mav.addObject("fileList", fileList);
			}
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}

		return mav;
	}

	@RequestMapping("exchange/alpayExchList")
	public ModelAndView alpayExchList(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();
		//EncLibrary enc = new EncLibrary();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, alpayExchList, userSeq);
		if(admChk == true) {
			mav.addObject("maxRow", 50);
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}

		return mav;
	}

	@RequestMapping(value="alpayExchListAjax", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String alpayExchListAjax(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		//EncLibrary enc = new EncLibrary();
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, alpayExchList, userSeq);
		if(admChk == true) {
			String act = request.getParameter("ACT") != null ? request.getParameter("ACT") : "";
			int hSeq = (request.getParameter("H_Seq") != null && !request.getParameter("H_Seq").equals("")) ? Integer.parseInt(request.getParameter("H_Seq")) : 0;

			if(act.equals("GetAlpayExch")) {
				int pg = (request.getParameter("pg") != null && !request.getParameter("pg").equals("")) ? Integer.parseInt(request.getParameter("pg")) : 1;
				int hMaxRow = (request.getParameter("H_MaxRow") != null && !request.getParameter("H_MaxRow").equals("")) ? Integer.parseInt(request.getParameter("H_MaxRow")) : 1;

				int offset = (pg - 1) * hMaxRow;

				List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("offset", offset);
				param.put("maxRow", hMaxRow);

				if(hSeq > 0) {
					param.put("seq", hSeq);

					list = alpayService.getAlpayExchLimitBySeq(param);
				}
				else {
					list = alpayService.getAlpayExchLimitAll(param);
				}

				r_res.put("pg", pg);
				r_res.put("rows", list);

				result = CommonUtil.libJsonExit(act, r_res);
			}
			if(act.equals("ExchOK")) {
				int hMemberSeq = (request.getParameter("H_MemberSeq") != null && !request.getParameter("H_MemberSeq").equals("")) ? Integer.parseInt(request.getParameter("H_MemberSeq")) : 0;
				String regdate = request.getParameter("H_regdate") != null ? request.getParameter("H_regdate") : "";

				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("seq", hMemberSeq);
				param.put("regdate", regdate);

				HashMap<String, Object> res = alpayService.updateExch(param);

				result = CommonUtil.libJsonExit(act, res);
			}
			if(act.equals("ExchNO")) {
				int hMemberSeq = (request.getParameter("H_MemberSeq") != null && !request.getParameter("H_MemberSeq").equals("")) ? Integer.parseInt(request.getParameter("H_MemberSeq")) : 0;
				String regdate = request.getParameter("H_regdate") != null ? request.getParameter("H_regdate") : "";
				BigDecimal newSeq = new BigDecimal( CommonUtil.getTimestamp() );

				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("seq", hMemberSeq);
				param.put("regdate", regdate);
				param.put("newSeq", newSeq);

				HashMap<String, Object> res = alpayService.rejectExch(param);
				int r = res.get("result") != null ? Integer.parseInt( String.valueOf( res.get("result") ) ) : 0;

				switch(r) {
					case 1:
						result = CommonUtil.libJsonExit(act, res);
						break;
					case 2:
						result = CommonUtil.libJsonExit("회원 정보가 올바르지 않습니다.", r_res);
						break;
					case 3:
						result = CommonUtil.libJsonExit("환전 금액을 불러오는 데 실패하였습니다.", r_res);
						break;
					case 4:
						result = CommonUtil.libJsonExit("해당 회원의 알페이 환전에 실패하였습니다.", r_res);
						break;
					case 5:
						result = CommonUtil.libJsonExit("환전 후 잔액을 불러오는 데 실패하였습니다.", r_res);
						break;
					case 6:
						result = CommonUtil.libJsonExit("환전 거부에 따른 로그 누적에 실패하였습니다.", r_res);
						break;
					default:
						result = CommonUtil.libJsonExit("죄송합니다.\\n알수 없는 오류가 발생하여 출금 거부 처리가 완료되지 않았습니다.", r_res);
				}
			}
		}
		else {
			result = CommonUtil.libJsonExit("접근 권한이 없습니다.", r_res);
		}

		return result;
	}

	@RequestMapping(value="exchange/process/getExchangeList", produces="application/json;charset=UTF-8", method = RequestMethod.GET)
	public @ResponseBody String getExchangeList(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		//EncLibrary enc = new EncLibrary();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용
		CodeUtil code = new CodeUtil(request);
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, recommendView, userSeq);
		if(admChk == true) {
			List<HashMap<String, Object>> list = alpayService.getAlpayExchList();


			List<HashMap<String, Object>> arr = new ArrayList<HashMap<String, Object>>();

			Map<String, String>  codeAlExchNm = code.getCODE_ALMONEY_EXCHANGE_STATUS_NM();
			Map<String, String>  codeAlExchCd = code.getCODE_ALMONEY_EXCHANGE_STATUS_CD();

			for(HashMap<String, Object> obj : list) {
				Iterator<String> iteratorK = obj.keySet().iterator();

				HashMap<String, Object> obj_in = new HashMap<String, Object>();

				while (iteratorK.hasNext()) {
    				String key = iteratorK.next();
    				Object val = obj.get(key);


    				if(key.equals("ExchagneStatus")) {
    					String exchangeStatusCode = String.valueOf(val);
    					String exchangeStatusNm = "";

    					for(int i = 1; i <= codeAlExchCd.size(); i++) {
    						if(codeAlExchCd.get(String.valueOf(i)).equals(exchangeStatusCode)) {
    							exchangeStatusNm = codeAlExchNm.get(String.valueOf(i));
    							break;
    						}
    					}

    					if(exchangeStatusCode.equals(codeAlExchCd.get("1"))) {
    						obj_in.put("buttonFlag", true);
    					}
    					else {
    						obj_in.put("buttonFlag", false);
    					}

    					val = exchangeStatusNm;
    				}

    				if(key.equals("DateLvUp") || key.equals("DateExchange")) {
    					val = ""+val+"";
    				}
    				if(key.equals("Phone")) {
    					//val = enc.AlmoneyDecrypt( String.valueOf( val ) );
    					val = "-";
    				}
    				obj_in.put(key, val);
    			}
				arr.add(obj_in);
			}

			//r_res.put("result", res);

			result = new Gson().toJson(arr);
			//System.out.println("result : " + result);
		}
		else {
			result = CommonUtil.libJsonExit("접근 권한이 없습니다.", r_res);
		}

		return result;
	}

	//exchange/idDocView -> idCardView 로 변경함(idDocView 는 개발 되어 있지 않음)

	@RequestMapping(value="exchange/process/changeExchangeAsk", method = RequestMethod.POST)
	public @ResponseBody String changeExchangeAsk(@RequestBody String filterJSON, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		//EncLibrary enc = new EncLibrary();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용
		CodeUtil code = new CodeUtil(request);
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, recommendView, userSeq);
		if(admChk == true) {
			String format = "yyyy-MM-dd hh:mm:ss";
			Calendar today = Calendar.getInstance();
			SimpleDateFormat type = new SimpleDateFormat(format);
			String now = type.format(today.getTime());

			JSONObject j = null;
			JSONParser jsonParse = new JSONParser();
			j = (JSONObject) jsonParse.parse(filterJSON);

			String action = String.valueOf(j.get("Action"));
			int hSeq = Integer.parseInt( String.valueOf(j.get("ExchangeSeq")) );

			boolean chk = true;
			String exchangeStatusCode = "";

			Map<String, String> cd = code.getCODE_ALMONEY_EXCHANGE_STATUS_CD();

			if(action.equals("admit")) {
				exchangeStatusCode = cd.get("2");
			}
			else if(action.equals("reject")) {
				exchangeStatusCode = cd.get("3");
			}
			else if(action.equals("postpone")) {
				exchangeStatusCode = cd.get("4");
			}
			else {
				result = "***ERROR***";
				chk = false;
			}

			if(chk == true) {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("exchangeSeq", hSeq);
				param.put("alExchStatusCd1", cd.get("1"));
				param.put("exchangeStatusCode", exchangeStatusCode);
				param.put("dateReg", now);
				param.put("userSeq", userSeq);
				param.put("alExchStatusCd2", cd.get("2"));
				param.put("alExchStatusCd3", cd.get("3"));

				HashMap<String, Object> changeExch = alpayService.changeExchangeAsk(param);

				String returnCode = String.valueOf(changeExch.get("ReturnCode"));

				if(returnCode.equals("1")) {
					result = "***OK***";
				}
				else if(returnCode.equals("2")) {
					result = "***LACK***";
				}
				else if(returnCode.equals("11")) {
					result = "***NOT_EXIST***";
				}
			}
		}

		return result;
	}

	@RequestMapping(value="exchange/process/refreshExchReadyData", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String refreshExchReadyData(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String act = request.getParameter("ACT");
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		//EncLibrary enc = new EncLibrary();
		CodeUtil code = new CodeUtil(request);

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, exchReadyList, userSeq);
		if(admChk == true) {

			if(act.equals("SetExchStamp")) {
				int lv = configService.getConfigLvCnt();

				int minLv = 2;
				int maxLv = lv;

				for(int i = minLv; i < maxLv; i++) {
					alpayService.getMemoExchReadySetSP(i);
				}

				result = CommonUtil.libJsonExit("데이터가 갱신되었습니다.", r_res);
			}
			else if(act.equals("GetExchStamp")) {
				int pg = (request.getParameter("pg") != null && !request.getParameter("pg").equals("")) ? Integer.parseInt(request.getParameter("pg")) : 1;
				int hMaxRow = (request.getParameter("H_MaxRow") != null && !request.getParameter("H_MaxRow").equals("")) ? Integer.parseInt(request.getParameter("H_MaxRow")) : 1;
				int hSeq = (request.getParameter("H_Seq") != null && !request.getParameter("H_Seq").equals("")) ? Integer.parseInt(request.getParameter("H_Seq")) : 0;
				int hLv = (request.getParameter("H_Lv") != null && !request.getParameter("H_Lv").equals("")) ? Integer.parseInt(request.getParameter("H_Lv")) : 0;

				int offset = (pg - 1) * hMaxRow;

				List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("offset", offset);
				param.put("maxRow", hMaxRow);

				int tCnt = 0;

				if(hSeq > 0) {
					param.put("seq", hSeq);

					if(pg == 1) {
						tCnt = alpayService.getMemoExchReadyCntBySeq(hSeq);
					}

					list = alpayService.getMemoExchReadyAllBySeq(param);
				}
				else {
					if(pg == 1) {
						tCnt = alpayService.getMemoExchReadyCntByLv(hLv);
					}
					param.put("lv", hLv);
					list = alpayService.getMemoExchReadyAllByLv(param);
				}

				r_res.put("pg", pg);
				r_res.put("tcnt", tCnt);
				r_res.put("rows", list);

				result = CommonUtil.libJsonExit(act, r_res);
			}
			else if(act.equals("GetWeekData")) {
				List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
				int hSeq = (request.getParameter("H_MemberSeq") != null && !request.getParameter("H_MemberSeq").equals("")) ? Integer.parseInt(request.getParameter("H_MemberSeq")) : 0;
				String dateReg = request.getParameter("H_XDate");

				HashMap<String, Object> param = new HashMap<String, Object>();

				if(hSeq > 0) {
					param.put("seq", hSeq);
					param.put("dateReg", dateReg);

					list = commonService.getExchWeekData(param);
				}

				result = CommonUtil.libJsonExit(act, list);
			}
			else if(act.equals("IncreaseStamp")) {
				HashMap<String, Object> item = new HashMap<String, Object>();
				int hIncrease = (request.getParameter("H_Increase") != null && !request.getParameter("H_Increase").equals("")) ? Integer.parseInt(request.getParameter("H_Increase")) : 0;
				int hSeq = (request.getParameter("H_MemberSeq") != null && !request.getParameter("H_MemberSeq").equals("")) ? Integer.parseInt(request.getParameter("H_MemberSeq")) : 0;
				String dateReg = request.getParameter("H_XDate");

				HashMap<String, Object> param = new HashMap<String, Object>();

				if(hSeq > 0) {
					param.put("seq", hSeq);
					param.put("hIncrease", hIncrease);
					param.put("dateReg", dateReg);

					item = commonService.getExchIncreassStamp(param);
				}

				if(item.get("ResultCode") == "9") {
					result = CommonUtil.libJsonExit("환전한 주에는 스탬프를 추가/감소할 수 없습니다.", item);//환전한 주에 스탬프가 모두 채워져 환전이 가능하게 된다면 무한 환전이 가능해진다.
				}
				else {
					result = CommonUtil.libJsonExit(act, item);
				}
			}
		}

		return result;
	}


	@RequestMapping("exchange/alpayLogCheck")
	public ModelAndView alpayLogCheck(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();
		//EncLibrary enc = new EncLibrary();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, alpayLogCheck, userSeq);
		if(admChk == true) {
			mav.addObject("maxRow", 50);
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}

		return mav;
	}

	@RequestMapping(value="exchange/alpayLogCheckAjax", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String alpayLogCheckAjax(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String act = request.getParameter("ACT");
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		//EncLibrary enc = new EncLibrary();
		//CodeUtil code = new CodeUtil(request);

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, alpayLogCheck, userSeq);
		if(admChk == true) {

			if(act.equals("GetAlpayLog")) {
				int pg = (request.getParameter("pg") != null && !request.getParameter("pg").equals("")) ? Integer.parseInt(request.getParameter("pg")) : 1;
				int hMaxRow = (request.getParameter("H_MaxRow") != null && !request.getParameter("H_MaxRow").equals("")) ? Integer.parseInt(request.getParameter("H_MaxRow")) : 1;
				int hSeq = (request.getParameter("H_Seq") != null && !request.getParameter("H_Seq").equals("")) ? Integer.parseInt(request.getParameter("H_Seq")) : 0;

				int offset = (pg - 1) * hMaxRow;

				List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("offset", offset);
				param.put("maxRow", hMaxRow);

				if(hSeq > 1) {
					param.put("seq", hSeq);

					list = alpayService.getAlpayAdmLogBySeq(param);
				}
				else {
					list = alpayService.getAlpayAdmLogAll(param);
				}

				r_res.put("pg", pg);;
				r_res.put("rows", list);

				result = CommonUtil.libJsonExit(act, r_res);
			}
			else if(act.equals("GetWeekData")) {
				List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
				int hSeq = (request.getParameter("H_MemberSeq") != null && !request.getParameter("H_MemberSeq").equals("")) ? Integer.parseInt(request.getParameter("H_MemberSeq")) : 0;
				String dateReg = request.getParameter("H_XDate");

				HashMap<String, Object> param = new HashMap<String, Object>();

				if(hSeq > 0) {
					param.put("seq", hSeq);
					param.put("dateReg", dateReg);

					list = commonService.getExchWeekData(param);
				}

				result = CommonUtil.libJsonExit(act, list);
			}
		}

		return result;
	}


	@RequestMapping("category/classify")
	public ModelAndView classify(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();
		//EncLibrary enc = new EncLibrary();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		HashMap<String, Object> localeItem = CommonUtil.getLocale(locale);
		String lang = String.valueOf(localeItem.get("lang"));
		String targetLang = lang;

		mav.addObject("targetLang", targetLang);

		return mav;
	}

	@RequestMapping(value="/category/getQuestionCount", produces="application/json;charset=UTF-8", method = RequestMethod.GET)
	public @ResponseBody String getQuestionCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();

		int qCnt = questionService.getQuestionCountAdm();

		r_res.put("Cnt", qCnt);

		result = new Gson().toJson(r_res);

		return result;
	}

	@RequestMapping(value="/category/getQuestion", produces="application/json;charset=UTF-8", method = RequestMethod.GET)
	public @ResponseBody String getQuestion(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int page = (request.getParameter("Page") != null && !request.getParameter("Page").equals("")) ? Integer.parseInt(request.getParameter("Page")) : 1;
		String result = null;

		List<HashMap<String, Object>> list = questionService.getQuestionAdm(page);

		result = new Gson().toJson(list);

		return result;
	}

	@RequestMapping(value="/category/changeLangQuestion", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String changeLangQuestion(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();

		int seq = request.getParameter("seq") != null ? Integer.parseInt(request.getParameter("seq")) : 0;
		String lang = request.getParameter("lang") != null ? request.getParameter("lang") : "";

		if(seq == 0 || lang.equals("")) {
			r_res.put("result", false);
		}
		else {
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("lang", lang);
			param.put("seq", seq);
			questionService.setChangeLangQuestion(param);

			r_res.put("result", true);
		}
		result = new Gson().toJson(r_res);

		return result;
	}

	@RequestMapping(value="/category/setCategory", method = RequestMethod.POST)
	public void setCategory(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용
		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject global = null;
		int userSeq = 0;

		if(cookies1 != null) {
			global = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( global.get("UserSeq") ) );
		}

		if(userSeq != 99 && !global.get("UserSeq").equals("10000110") && !global.get("UserSeq").equals("10000564") && !global.get("UserSeq").equals("10000092") && !global.get("UserSeq").equals("10000703") && global.get("UserSeq").equals("") && !global.get("UserSeq").equals("10010318") ) {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "back", "parent.parent");
		}
		else {
			int seq = (request.getParameter("Seq") != null && !request.getParameter("Seq").equals("0")) ? Integer.parseInt(request.getParameter("Seq")) : 0;

			if(seq > 0) {
				String[] section = new String[5];

				if(request.getParameter("Code1") != null && !request.getParameter("Code1").equals("0")) { section[0] = request.getParameter("Code1"); } else { section[0] = "0"; }
				if(request.getParameter("Code2") != null && !request.getParameter("Code2").equals("0")) { section[1] = request.getParameter("Code2"); } else { section[1] = "0"; }
				if(request.getParameter("Code3") != null && !request.getParameter("Code3").equals("0")) { section[2] = request.getParameter("Code3"); } else { section[2] = "0"; }
				if(request.getParameter("Code4") != null && !request.getParameter("Code4").equals("0")) { section[3] = request.getParameter("Code4"); } else { section[3] = "0"; }
				if(request.getParameter("Code5") != null && !request.getParameter("Code5").equals("0")) { section[4] = request.getParameter("Code5"); } else { section[4] = "0"; }

				if(!section[0].equals("0")) {
					HashMap<String, Object> param = new HashMap<String, Object>();
					param.put("section1", section[0]);
					param.put("section2", section[1]);
					param.put("section3", section[2]);
					param.put("section4", section[3]);
					param.put("section5", section[4]);
					param.put("seq", seq);

					questionService.setCategoryQuestionAdm(param);
				}
			}
		}
	}

	@RequestMapping("eventList")
	public ModelAndView eventList(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();
		//EncLibrary enc = new EncLibrary();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, eventList, userSeq);
		if(admChk == true) {

			mav.addObject("curPageName", "/aadmin/eventList");
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "back", "top");
		}

		return mav;
	}

	@RequestMapping(value="eventSave", method = RequestMethod.POST)
	public void eventSave(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용
		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject global = null;
		int userSeq = 0;

		if(cookies1 != null) {
			global = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( global.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, eventList, userSeq);
		if(admChk == true) {
			String periodStart = (request.getParameter("PeriodStart") != null && !request.getParameter("PeriodStart").equals("")) ? CommonUtil.fn_Word_In(request.getParameter("PeriodStart")) : "";
			String periodEnd = (request.getParameter("PeriodEnd") != null && !request.getParameter("PeriodEnd").equals("")) ? CommonUtil.fn_Word_In(request.getParameter("PeriodEnd")) : "";
			String flagUse = (request.getParameter("FlagUse") != null && !request.getParameter("FlagUse").equals("")) ? CommonUtil.fn_Word_In(request.getParameter("FlagUse")) : "";
			int questionSeq = (request.getParameter("QuestionSeq") != null && !request.getParameter("QuestionSeq").equals("")) ? Integer.parseInt( CommonUtil.fn_Word_In(request.getParameter("QuestionSeq")) ) : 0;


			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("q_seq", questionSeq);
			param.put("st_date", periodStart);
			param.put("end_date", periodEnd);
			param.put("banner_img", "");

			if(questionSeq > 0 && !periodStart.equals("") && !periodEnd.equals("")) {
				int resultCode = commonService.setEventMake(param);

				if(resultCode == 0) {
					CommonUtil.jspAlert(response, "정상적으로 등록되었습니다.", "/aadmin/eventList", "parent.parent");
				}
				else {
					CommonUtil.jspAlert(response, "등록 실패!", "/aadmin/eventList", "parent.parent");
				}
			}
			else {
				CommonUtil.jspAlert(response, "등록 실패!", "/aadmin/eventList", "parent.parent");
			}
 		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "parent.parent");
		}
	}

	@RequestMapping("adList")
	public ModelAndView adList(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();
		//EncLibrary enc = new EncLibrary();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, adList, userSeq);
		if(admChk == true) {

			List<SectionVO> section = commonService.getSectionCode();

			String req_TREC = CommonUtil.fn_Word_In( request.getParameter("trec") != null ? request.getParameter("trec").trim() : "0");
			String req_PG = CommonUtil.fn_Word_In( request.getParameter("pg") != null ? request.getParameter("pg").trim() : "");
			String src_Sort = CommonUtil.fn_Word_In( request.getParameter("src_Sort") != null ? request.getParameter("src_Sort").trim() : "");
			String src_Kind = CommonUtil.fn_Word_In( request.getParameter("src_Kind") != null ? request.getParameter("src_Kind").trim() : "");
			String src_Text = CommonUtil.fn_Word_In( request.getParameter("src_Text") != null ? request.getParameter("src_Text").trim() : "" );

			if(req_PG == "") {
				req_PG = "1";
			}

			if(src_Sort.equals("")) {
				src_Sort = "Seq";
			}

			String s_tmp = "";
			int n_trec = 0;
			s_tmp = req_TREC;

			if(CommonUtil.isNumeric(s_tmp) && s_tmp.length() > 0) {
				n_trec = Integer.parseInt(s_tmp);
			}

			HashMap<String, Object> adParam = new HashMap<String, Object>();
			adParam.put("psrc_Kind", src_Kind);
			adParam.put("psrc_Text", src_Text);

			int adCount = commonService.getAdLimiCount(adParam);

			n_trec = adCount;

			int n_pagesize = 10;
			int n_pagescnt = 10;
			int n_curpage = 1;
			int n_totalpage = 0;
			int st_num = 0;
			int en_num = 0;

			s_tmp = req_PG;

			if(CommonUtil.isNumeric(s_tmp) == true && s_tmp.length() > 0) {
				n_curpage = Integer.parseInt(s_tmp);
			}
			n_totalpage = Math.round(n_trec / n_pagesize);

			if(n_trec % n_pagesize > 0) {
				n_totalpage = n_totalpage + 1;
			}

			if(n_curpage <= 0) {
				n_curpage = 1;
			}

			if(n_curpage > n_totalpage) {
				n_curpage = n_totalpage;
			}

			st_num = (n_pagesize) * (n_curpage - 1) + 1;
			en_num = (n_pagesize) * (n_curpage);



			HashMap<String, Object> lParam = new HashMap<String, Object>();
			lParam.put("p_Option", (n_pagesize * n_curpage));
			lParam.put("psrc_Kind", src_Kind);
			lParam.put("psrc_Text", src_Text);
			lParam.put("psrc_Cond", src_Sort);
			lParam.put("st_num", st_num);
			lParam.put("en_num", en_num);

			List<AdVO> adList = commonService.getAdLimitList(lParam);

			String url = "/aadmin/adList?trec=" + req_TREC + "&src_Sort=" + src_Sort + "&src_Kind=" + src_Kind + "&src_Text=" + src_Text;

			String paging = CommonUtil.get_Paging_Tag_New2_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);


			HashMap<String, Object> sect = new HashMap<String, Object>();
			for(int i = 0; i < section.size(); i++) {
				sect.put(String.valueOf(section.get(i).getCode()), section.get(i).getCodeName());
			}


			mav.addObject("curPageName", "/aadmin/adList");
			mav.addObject("n_curpage", n_curpage);
			mav.addObject("section", section);
			mav.addObject("sect", sect);
			mav.addObject("n_trec", n_trec);
			mav.addObject("adList", adList);
			mav.addObject("paging", paging);
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "back", "top");
		}

		return mav;
	}

	@RequestMapping(value="adSave", method = RequestMethod.POST)
	public void adSave(MultipartHttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		UtilFile utilFile = new UtilFile();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject global = null;
		int userSeq = 0;

		if(cookies1 != null) {
			global = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( global.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, eventList, userSeq);
		if(admChk == true) {
			String defaultFolder = "AD";
			String FilePath = utilFile.getSaveLocation(request, defaultFolder);

			List<MultipartFile> fileList = request.getFiles("ADFile");
			String section1 = request.getParameter("Section1");
			String periodStart = request.getParameter("PeriodStart");
			String periodEnd = request.getParameter("PeriodEnd");
			String flagUse = request.getParameter("FlagUse");
			String flagAd = request.getParameter("FlagAd");
			String url = request.getParameter("Url");

			if(url == null) { url = ""; }

			if(fileList.size() > 0) {
				String originFileName = "";
				String ThumbFileSaveName = "";
				int seq = commonService.getAdMaxCode();


				for (MultipartFile mf : fileList) {
					originFileName = mf.getOriginalFilename(); // 원본 파일 명
					if(CommonUtil.fn_FileType_Check(originFileName).equals("N")) {
		        		CommonUtil.jspAlert(response, "등록할 수 없는 파일입니다." , "", "parent.parent");
					}
					else {
						int maxSeq = (seq + 1);
						String FileReName = maxSeq + "_" + section1;
			        	String FileExtension = FilenameUtils.getExtension(originFileName);
			        	String FileSaveName = FileReName + "." + FileExtension;


			        	File fileDir = new File(FilePath);

				        if (!fileDir.exists()) {
				        	fileDir.mkdirs();
				        }

				        try { // 파일생성
				        	//원본 파일 크기로 업로드 처리
				        	File original = new File(FilePath, FileSaveName);
				        	mf.transferTo(original);


				        	ThumbFileSaveName = FileReName + "." + FileExtension;


				        	boolean chk = true;
				        	if(periodStart.equals("") || periodEnd.equals("")) { chk = false; }

				        	if(chk == true) {
					        	File thumbnail = new File(FilePath, ThumbFileSaveName);


					        	//db 저장
					        	HashMap<String, Object> param = new HashMap<String, Object>();
					        	param.put("seq", maxSeq);
					        	param.put("section1", section1);
					        	param.put("adFile", FileReName);
					        	param.put("adFileExt", FileExtension);
					        	param.put("periodStart", periodStart);
					        	param.put("periodEnnd", periodEnd);
					        	param.put("flagUse", flagUse);
					        	param.put("url", url);
					        	param.put("flagAd", flagAd);

					        	commonService.setAd(param);

					        	if(thumbnail.exists()) {
					        		//업로드 원본 파일 삭제
					        		if(original.delete()){
					        			utilFile.deleteFile(original);
					        		}
					        	}

					        	CommonUtil.jspAlert(response, "정상적으로 등록되었습니다." , "/aadmin/adList", "parent.parent");
				        	}
				        	else {
				        		CommonUtil.jspAlert(response, "등록 실패 - 게시기간 입력 누락" , "", "parent.parent");
				        	}
				        } catch (Exception e) {
				        	CommonUtil.jspAlert(response, "등록 실패" + e.toString() , "", "parent.parent");
				        } finally {
				        }

					}

				}

			}
			else {
				CommonUtil.jspAlert(response, "파일을 선택하여 주세요.", "", "parent.parent");
			}
 		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "back", "parent.parent");
		}
	}

	@RequestMapping("adView")
	public ModelAndView adView(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();
		//EncLibrary enc = new EncLibrary();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, adList, userSeq);
		if(admChk == true) {

			List<SectionVO> section = commonService.getSectionCode();

			int seq = (request.getParameter("Seq") != null) ? Integer.parseInt(request.getParameter("Seq").trim()) : 0;

			if(seq > 0) {
				AdVO adInfo = commonService.getAdView(seq);

				HashMap<String, Object> sect = new HashMap<String, Object>();
				for(int i = 0; i < section.size(); i++) {
					sect.put(String.valueOf(section.get(i).getCode()), section.get(i).getCodeName());
				}

				mav.addObject("curPageName", "/aadmin/adList");
				mav.addObject("seq", seq);
				mav.addObject("section", section);
				mav.addObject("sect", sect);
				mav.addObject("adInfo", adInfo);
			}
			else {
				CommonUtil.jspAlert(response, "잘못된 접근입니다.", "back", "top");
			}
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "back", "top");
		}

		return mav;
	}

	@RequestMapping(value="adEdit", method = RequestMethod.POST)
	public void adEdit(MultipartHttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용
		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject global = null;
		int userSeq = 0;

		if(cookies1 != null) {
			global = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( global.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, eventList, userSeq);
		if(admChk == true) {
			int seq = (request.getParameter("Seq") != null) ? Integer.parseInt(request.getParameter("Seq").trim()) : 0;

			String section1 = request.getParameter("Section1");
			String periodStart = request.getParameter("PeriodStart");
			String periodEnd = request.getParameter("PeriodEnd");
			String flagUse = request.getParameter("FlagUse");
			String flagAd = request.getParameter("FlagAd");
			String url = request.getParameter("Url");


			if(seq > 0) {
				//db 저장
	        	HashMap<String, Object> param = new HashMap<String, Object>();
	        	param.put("seq", seq);
	        	param.put("section1", section1);
	        	param.put("periodStart", periodStart);
	        	param.put("periodEnd", periodEnd);
	        	param.put("flagUse", flagUse);
	        	param.put("url", url);
	        	param.put("flagAd", flagAd);

	        	commonService.updateAd(param);

	        	CommonUtil.jspAlert(response, "정상적으로 수정되었습니다.", "/aadmin/adView?Seq=" + seq, "parent.parent");
			}
			else {
				CommonUtil.jspAlert(response, "잘못된 접근입니다.", "/", "parent.parent");
			}
 		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "back", "parent.parent");
		}
	}

	@RequestMapping(value="adDel", method = RequestMethod.POST)
	public void adDel(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용
		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject global = null;
		int userSeq = 0;

		if(cookies1 != null) {
			global = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( global.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, eventList, userSeq);
		if(admChk == true) {
			int seq = (request.getParameter("Seq") != null) ? Integer.parseInt(request.getParameter("Seq").trim()) : 0;

			if(seq > 0) {

	        	commonService.deleteAd(seq);

	        	CommonUtil.jspAlert(response, "정상적으로 삭제되었습니다.", "/aadmin/adList", "parent.parent");
			}
			else {
				CommonUtil.jspAlert(response, "잘못된 접근입니다.", "/", "parent.parent");
			}
 		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "back", "parent.parent");
		}
	}

	@RequestMapping("almoneyList")
	public ModelAndView almoneyList(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();
		//EncLibrary enc = new EncLibrary();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, almoneyList, userSeq);
		if(admChk == true) {

			String req_TREC = CommonUtil.fn_Word_In( request.getParameter("trec") != null ? request.getParameter("trec").trim() : "0");
			String req_PG = CommonUtil.fn_Word_In( request.getParameter("pg") != null ? request.getParameter("pg").trim() : "");
			String src_Sort = CommonUtil.fn_Word_In( request.getParameter("src_Sort") != null ? request.getParameter("src_Sort").trim() : "");
			String src_Kind = CommonUtil.fn_Word_In( request.getParameter("src_Kind") != null ? request.getParameter("src_Kind").trim() : "");
			String src_Text = CommonUtil.fn_Word_In( request.getParameter("src_Text") != null ? request.getParameter("src_Text").trim() : "" );

			if(req_PG == "") {
				req_PG = "1";
			}

			if(src_Sort.equals("")) {
				src_Sort = "Seq";
			}

			String s_tmp = "";
			int n_trec = 0;
			s_tmp = req_TREC;

			if(CommonUtil.isNumeric(s_tmp) && s_tmp.length() > 0) {
				n_trec = Integer.parseInt(s_tmp);
			}

			int logCnt = commonService.getAdmAlmoneyCount();

			n_trec = logCnt;


			int n_pagesize = 20;
			int n_pagescnt = 10;
			int n_curpage = 1;
			int n_totalpage = 0;
			int st_num = 0;
			int en_num = 0;

			s_tmp = req_PG;

			if(CommonUtil.isNumeric(s_tmp) == true && s_tmp.length() > 0) {
				n_curpage = Integer.parseInt(s_tmp);
			}
			n_totalpage = Math.round(n_trec / n_pagesize);

			if(n_trec % n_pagesize > 0) {
				n_totalpage = n_totalpage + 1;
			}

			if(n_curpage <= 0) {
				n_curpage = 1;
			}

			if(n_curpage > n_totalpage) {
				n_curpage = n_totalpage;
			}

			st_num = (n_pagesize) * (n_curpage - 1) + 1;
			en_num = (n_pagesize) * (n_curpage);


			HashMap<String, Object> lParam = new HashMap<String, Object>();
			lParam.put("p_Option", (n_pagesize * n_curpage));
			lParam.put("psrc_Kind", src_Kind);
			lParam.put("psrc_Text", src_Text);
			lParam.put("psrc_Cond", src_Sort);
			lParam.put("st_num", st_num);
			lParam.put("en_num", en_num);

			List<LogAlmoneyVO> list = commonService.getAdmAlmoneyLimit(lParam);

			String url = "/aadmin/almoneyList?trec=" + req_TREC + "&src_Sort=" + src_Sort + "&src_Kind=" + src_Kind + "&src_Text=" + src_Text;

			String paging = CommonUtil.get_Paging_Tag_New2_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);

			mav.addObject("curPageName", "/aadmin/almoneyList");
			mav.addObject("n_curpage", n_curpage);
			mav.addObject("n_trec", n_trec);
			mav.addObject("list", list);
			mav.addObject("paging", paging);
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "back", "top");
		}

		return mav;
	}

	@RequestMapping("levelup/levelUpReadyList_new")
	public ModelAndView levelUpReadyList_new(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();
		//EncLibrary enc = new EncLibrary();
		CodeUtil code = new CodeUtil(request);
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, levelUpReadyList_new, userSeq);
		if(admChk == true) {
			int maxRow = 50;

			List<HashMap<String, Object>> list = configService.getConfigLv2();

			mav.addObject("curPageName", "/aadmin/almoneyList");
			mav.addObject("cfgLvUp", new Gson().toJson(list));
			mav.addObject("codeCd", code.getCODE_MEM_LV_CD());
			mav.addObject("codeNm", code.getCODE_MEM_LV_NM());
			mav.addObject("maxRow", maxRow);
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "back", "top");
		}

		return mav;
	}

	@RequestMapping(value="levelup/process/refreshLvReadyData_new_Ajax", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String refreshLvReadyData_new_Ajax(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String act = request.getParameter("ACT");
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		//EncLibrary enc = new EncLibrary();
		CodeUtil code = new CodeUtil(request);

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, exchReadyList, userSeq);
		if(admChk == true) {
			if(act.equals("SetLvStamp")) {
				int lv = configService.getConfigLvCnt();

				int minLv = request.getParameter("H_Lv").equals("1") ? 0 : 2;
				int maxLv = request.getParameter("H_Lv").equals("1") ? 2 : lv;

				for(int i = minLv; i < maxLv; i++) {
					commonService.getMemoLvReadySetSP(i);
				}

				result = CommonUtil.libJsonExit("데이터가 갱신되었습니다.", r_res);

			}
			else if(act.equals("GetLvStamp")) {
				int pg = (request.getParameter("pg") != null && !request.getParameter("pg").equals("")) ? Integer.parseInt(request.getParameter("pg")) : 1;
				int hMaxRow = (request.getParameter("H_MaxRow") != null && !request.getParameter("H_MaxRow").equals("")) ? Integer.parseInt(request.getParameter("H_MaxRow")) : 1;
				int hSeq = (request.getParameter("H_Seq") != null && !request.getParameter("H_Seq").equals("")) ? Integer.parseInt(request.getParameter("H_Seq")) : 0;
				int hLv = (request.getParameter("H_Lv") != null && !request.getParameter("H_Lv").equals("")) ? Integer.parseInt(request.getParameter("H_Lv")) : 0;

				int offset = (pg - 1) * hMaxRow;

				List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("offset", offset);
				param.put("maxRow", hMaxRow);

				int tCnt = 0;

				if(hSeq > 0) {
					param.put("seq", hSeq);

					if(pg == 1) {
						tCnt = commonService.getMemoLvReadyCntBySeq(hSeq);
					}

					list = commonService.getMemoLvReadyAllBySeq(param);
				}
				else {
					param.put("lv", hLv);

					if(pg == 1) {
						tCnt = commonService.getMemoLvReadyCntByLv(hLv);
					}

					list = commonService.getMemoLvReadyAllByLv(param);
				}

				r_res.put("pg", pg);
				r_res.put("tcnt", tCnt);
				r_res.put("rows", list);

				result = CommonUtil.libJsonExit(act, r_res);

			}
			else if(act.equals("GetWeekData")) {
				List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
				int hSeq = (request.getParameter("H_MemberSeq") != null && !request.getParameter("H_MemberSeq").equals("")) ? Integer.parseInt(request.getParameter("H_MemberSeq")) : 0;
				String dateReg = request.getParameter("H_XDate");

				HashMap<String, Object> param = new HashMap<String, Object>();

				if(hSeq > 0) {
					param.put("seq", hSeq);
					param.put("dateReg", dateReg);

					list = commonService.getLvWeekData(param);
				}

				result = CommonUtil.libJsonExit(act, list);
			}
			else if(act.equals("SetEducation")) {
				HashMap<String, Object> item = new HashMap<String, Object>();
				int hSeq = (request.getParameter("H_MemberSeq") != null && !request.getParameter("H_MemberSeq").equals("")) ? Integer.parseInt(request.getParameter("H_MemberSeq")) : 0;
				String dateReg = request.getParameter("H_XDate");
				//System.out.println("dateReg : " + dateReg);
				HashMap<String, Object> param = new HashMap<String, Object>();
				List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
				List<HashMap<String, Object>> listData = new ArrayList<HashMap<String, Object>>();

				if(hSeq > 0) {
					param.put("seq", hSeq);
					param.put("dateReg", dateReg);

					list = commonService.setEducation(param);

					HashMap<String, Object> data1 = new HashMap<String, Object>();
					HashMap<String, Object> data2 = new HashMap<String, Object>();

					for(int i = 0; i < list.size(); i++) {
						if(i == 0) {
							JsonElement json = new Gson().toJsonTree(list.get(i));

			    			JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
			    			JSONParser jsonParse = new JSONParser();
			    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

			    	    	data1.put("MemberSeq", jsonObj2.get("MemberSeq"));
			    	    	data1.put("Lv", jsonObj2.get("Lv"));
			    	    	data1.put("regdate", jsonObj2.get("regdate"));
			    	    	data1.put("LvUpStamp", jsonObj2.get("LvUpStamp"));
			    	    	data1.put("ExchStamp", jsonObj2.get("ExchStamp"));
			    	    	data1.put("QusRegCnt", jsonObj2.get("QusRegCnt"));
			    	    	data1.put("QusChoiceCnt", jsonObj2.get("QusChoiceCnt"));
			    	    	data1.put("AnsRegCnt", jsonObj2.get("AnsRegCnt"));
			    	    	data1.put("AnsChoicedCnt", jsonObj2.get("AnsChoicedCnt"));
			    	    	data1.put("AnsEstiCnt", jsonObj2.get("AnsEstiCnt"));
			    	    	data1.put("ReplyCnt", jsonObj2.get("ReplyCnt"));
			    	    	data1.put("EducationCnt", jsonObj2.get("EducationCnt"));

			    	    	listData.add(data1);
						}
						if(i == 1) {
							JsonElement json = new Gson().toJsonTree(list.get(i));

			    			JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
			    			JSONParser jsonParse = new JSONParser();
			    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

			    	    	data2.put("MemberSeq", jsonObj2.get("MemberSeq"));
			    	    	data2.put("Lv", jsonObj2.get("Lv"));
			    	    	data2.put("Nickname", jsonObj2.get("Nickname"));
			    	    	data2.put("Name", jsonObj2.get("Name"));
			    	    	data2.put("Photo", jsonObj2.get("Photo"));
			    	    	data2.put("Almoney", jsonObj2.get("Almoney"));
			    	    	data2.put("RecmdLv_1", jsonObj2.get("RecmdLv_1"));
			    	    	data2.put("RecmdCnt_1", jsonObj2.get("RecmdCnt_1"));
			    	    	data2.put("EducationCnt", jsonObj2.get("EducationCnt"));
			    	    	data2.put("StampCnt", jsonObj2.get("StampCnt"));
			    	    	data2.put("QusRegCnt", jsonObj2.get("QusRegCnt"));
			    	    	data2.put("QusChoiceCnt", jsonObj2.get("QusChoiceCnt"));
			    	    	data2.put("AnsRegCnt", jsonObj2.get("AnsRegCnt"));
			    	    	data2.put("AnsChoicedCnt", jsonObj2.get("AnsChoicedCnt"));
			    	    	data2.put("AnsEstiCnt", jsonObj2.get("AnsEstiCnt"));
			    	    	data2.put("ReplyCnt", jsonObj2.get("ReplyCnt"));
			    	    	data2.put("LvDate", jsonObj2.get("LvDate"));
			    	    	data2.put("isUpOK", jsonObj2.get("isUpOK"));

			    	    	listData.add(data2);
						}
					}
				}

				result = CommonUtil.libJsonExit(act, listData);

			}
			else if(act.equals("IncreaseStamp")) {
				HashMap<String, Object> item = new HashMap<String, Object>();
				int hIncrease = (request.getParameter("H_Increase") != null && !request.getParameter("H_Increase").equals("")) ? Integer.parseInt(request.getParameter("H_Increase")) : 0;
				int hSeq = (request.getParameter("H_MemberSeq") != null && !request.getParameter("H_MemberSeq").equals("")) ? Integer.parseInt(request.getParameter("H_MemberSeq")) : 0;
				String dateReg = request.getParameter("H_XDate");

				HashMap<String, Object> param = new HashMap<String, Object>();

				if(hSeq > 0) {
					param.put("seq", hSeq);
					param.put("hIncrease", hIncrease);
					param.put("dateReg", dateReg);

					item = commonService.getLvIncreassStamp(param);
				}

				result = CommonUtil.libJsonExit(act, item);
			}
		}

		return result;
	}

	@RequestMapping("notice")
	public ModelAndView notice(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();
		//EncLibrary enc = new EncLibrary();
		CodeUtil code = new CodeUtil(request);
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, notice, userSeq);
		if(admChk == true) {
			String page = request.getParameter("Page") != null ? request.getParameter("Page") : "0";
			mav.addObject("page", page);
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "back", "top");
		}

		return mav;
	}

	@RequestMapping(value="notice/writeNotice", method = RequestMethod.POST)
	public void writeNotice(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용
		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject global = null;
		int userSeq = 0;

		if(cookies1 != null) {
			global = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( global.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, eventList, userSeq);
		if(admChk == true) {
			int seq = (request.getParameter("Seq") != null && !request.getParameter("Seq").equals("")) ? Integer.parseInt(request.getParameter("Seq").trim()) : 0;
			String title = (request.getParameter("Title") != null && !request.getParameter("Title").equals("")) ? request.getParameter("Title").trim() : "";
			String content = (request.getParameter("Content") != null && !request.getParameter("Content").equals("")) ? request.getParameter("Content").trim().replace(System.getProperty("line.separator"), "<br>") : "";
			//String top = (request.getParameter("Top") != null) ? request.getParameter("Top").trim() : "";
			String lang = (request.getParameter("lang") != null && !request.getParameter("lang").equals("")) ? request.getParameter("lang").trim() : "";


			if(!title.equals("") && !content.equals("")) {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("title", title);
				param.put("contents", content);
				param.put("lang", lang);

				if(seq > 0) { // 수정, 개발 예정
					param.put("seq", seq);
		        	commonService.updateNoticeBySeq(param);

		        	CommonUtil.jspAlert(response, "수정 되었습니다.", "/aadmin/notice", "parent.parent");
				}
				else {
					//System.out.println("title" + param.get("title"));
					//System.out.println("contents" + param.get("contents"));
					commonService.setNotice(param);

					CommonUtil.jspAlert(response, "작성 완료!", "/aadmin/notice", "parent.parent");
				}
			}
 		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "back", "parent.parent");
		}
	}

	@RequestMapping(value="notice/delNotice", method = RequestMethod.GET)
	public void delNotice(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용
		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject global = null;
		int userSeq = 0;

		if(cookies1 != null) {
			global = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( global.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, eventList, userSeq);
		if(admChk == true) {
			int seq = (request.getParameter("Seq") != null) ? Integer.parseInt(request.getParameter("Seq").trim()) : 0;

			if(seq > 0) {

	        	commonService.deleteNoticeBySeq(seq);

	        	CommonUtil.jspAlert(response, "정상적으로 삭제되었습니다.", "/aadmin/notice", "parent.parent");
			}
			else {
				CommonUtil.jspAlert(response, "잘못된 접근입니다.", "/", "parent.parent");
			}
 		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "back", "parent.parent");
		}
	}

	@RequestMapping("report/reportList")
	public ModelAndView reportList(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();
		//EncLibrary enc = new EncLibrary();
		//CodeUtil code = new CodeUtil(request);
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, reportList, userSeq);
		if(admChk == true) {
			//String page = request.getParameter("Page") != null ? request.getParameter("Page") : "0";
			//mav.addObject("page", page);
			String curPageName = "/aadmin/report/reportList";
			String req_PG = request.getParameter("pg");

			int pg = 0;
			if(req_PG == "" || req_PG == null) { pg = 1; }
			else {
				pg = Integer.parseInt(req_PG);
			}

			int totalCnt = commonService.getReportListPgCntAdm();

			int n_pagesize = 30;
			int n_pagescnt = 5;
			int n_curpage = 1;
			int n_totalpage = 0;

			n_curpage = pg;

			int st_num = (n_pagesize) * (n_curpage - 1) + 1;
			int en_num = (n_pagesize) * (n_curpage);

			n_totalpage =  -vb.CInt( -(totalCnt / n_pagesize) );

			HashMap<String, Object> sParam = new HashMap<String, Object>();
			sParam.put("p_Option", (n_pagesize * n_curpage));
			sParam.put("st_num", st_num);
			sParam.put("en_num", en_num);

			List<HashMap<String, Object>> list = commonService.getReportListPgAdm(sParam);

			for(int i = 0; i < list.size(); i++) {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("hType", list.get(i).get("Type"));
				param.put("hContentsSeq", list.get(i).get("ContentsSeq"));

				List<HashMap<String, Object>> reporter = commonService.getReporter(param);

				list.get(i).put("reporter", reporter);
			}

			String url = curPageName + "?CurPageName=" + curPageName;
			String pageStr = CommonUtil.get_Paging_Tag_New2_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);

			mav.addObject("list", list);
			mav.addObject("paging", pageStr);
			mav.addObject("n_totalpage", n_totalpage);
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "back", "top");
		}

		return mav;
	}

	@RequestMapping(value="report/reportListAjax", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String reportListAjax(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String act = request.getParameter("ACT");
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, reportList, userSeq);

		if(admChk == true) {
			String hType = request.getParameter("H_Type") != null ? request.getParameter("H_Type") : "";
			String hContentsSeq = request.getParameter("H_ContentsSeq") != null ? request.getParameter("H_ContentsSeq") : "";
			String tbl = "";

			switch(hType) {
				case "A":
					tbl = "T_ANSWER";
					break;
				case "AR":
					tbl = "T_REPLY_ANSWER";
					break;
				case "Q":
					tbl = "T_QUESTION";
					break;
				case "QR":
					tbl = "T_REPLY_QUESTION";
					break;
				default:
					break;
			}

			if(act.equals("GetList")) {
				List<HashMap<String, Object>> list = commonService.getReportListAdm();

				for(int i = 0; i < list.size(); i++) {
					HashMap<String, Object> param = new HashMap<String, Object>();
					param.put("hType", list.get(i).get("Type"));
					param.put("hContentsSeq", list.get(i).get("ContentsSeq"));

					List<HashMap<String, Object>> reporter = commonService.getReporter(param);

					list.get(i).put("reporter", reporter);
				}

				result = CommonUtil.libJsonExit(act, list);
			}
			else if(act.equals("GetBlackList_Siren")) {
				List<HashMap<String, Object>> list = commonService.getBlackList_Siren();

				result = CommonUtil.libJsonExit(act, list);
			}
			else if(act.equals("GetBlackList_Reporter")) {
				List<HashMap<String, Object>> list = commonService.getBlackList_Reporter();

				result = CommonUtil.libJsonExit(act, list);
			}
			else if(act.equals("SetDelete")) {
				if(!tbl.equals("")) {
					HashMap<String, Object> pParam = new HashMap<String, Object>();
					pParam.put("tbl", tbl);
					pParam.put("hType", hType);
					pParam.put("hContentsSeq", hContentsSeq);
					HashMap<String, Object> res = commonService.setSirenDelete(pParam);

					result = CommonUtil.libJsonExit(act, res);
				}
				else {
					result = CommonUtil.libJsonExit("", r_res);
				}
			}

			//163줄 이하 switch ($_POST['ACT']) 처리
			int res = 0;
			int mPoint = 0;
			String flagUse = "";
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date currentDate = new Date();
			//System.out.println("toDate : " + dateFormat.format(currentDate));

			// convert date to calendar
			Calendar c = Calendar.getInstance();
	        c.setTime(currentDate);
			Date dd = c.getTime();

			switch(act) {
				case "Cancel":
					c.add(Calendar.YEAR, 1000);
					dd = c.getTime();
					flagUse = "Y";
					break;
				case "SetDanger":
					res = 1;
					mPoint = (hType.equals("Q") || hType.equals("A")) ? 10 : 3;
					flagUse = "S";
					break;
				case "SetWarning":
					res = 2;
					mPoint = (hType.equals("Q") || hType.equals("A")) ? 3 : 1;
					flagUse = "S";
					break;
				case "SetReject":
					res = 3;
					mPoint = 0;
					flagUse = "Y";
					break;
				default:
					dd = null;
					break;
			}


			if(dd != null && !hContentsSeq.equals("") && !tbl.equals("")) {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("tbl", tbl);
				param.put("res", res);
				param.put("mPoint", mPoint);
				param.put("dd", dd);
				param.put("hType", hType);
				param.put("hContentsSeq", hContentsSeq);
				param.put("flagUse", flagUse);

				List<HashMap<String, Object>> data = commonService.setSirenLog(param);

				List<HashMap<String, Object>> listTotal = new ArrayList<HashMap<String, Object>>();
				HashMap<String, Object> data1 = new HashMap<String, Object>();
				HashMap<String, Object> data2 = new HashMap<String, Object>();

				for(int i = 0; i < data.size(); i++) {
					if(i == 0) {
						JsonElement json = new Gson().toJsonTree(data.get(i));

		    			JsonObject jsonObj = json.getAsJsonObject();
		    			JSONParser jsonParse = new JSONParser();
		    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

		    	    	data1.put("Type", jsonObj2.get("Type"));
		    	    	data1.put("ContentsSeq", jsonObj2.get("ContentsSeq"));
		    	    	data1.put("MemberSeq", jsonObj2.get("MemberSeq"));
		    	    	data1.put("NickName", jsonObj2.get("NickName"));
		    	    	data1.put("MPoint", jsonObj2.get("MPoint"));
		    	    	data1.put("Result", jsonObj2.get("Result"));
		    	    	data1.put("conDate", jsonObj2.get("conDate"));

		    	    	listTotal.add(data1);
					}
					if(i == 1) {
						JsonElement json = new Gson().toJsonTree(data.get(i));

		    			JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
		    			JSONParser jsonParse = new JSONParser();
		    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

		    	    	data2.put("ReturnCode", jsonObj2.get("ReturnCode"));
		    	    	data2.put("ErrText", jsonObj2.get("ErrText"));
					}
				}

				if(listTotal.size() > 0 && data2.get("ReturnCode") == null) {
					result = CommonUtil.libJsonExit(act, data1);
				}
				else {
					result = CommonUtil.libJsonExit("경고: 신고를 처리하지 못하였습니다.", r_res);
				}
			}
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "back", "top");
		}

		return result;
	}

	@RequestMapping(value="report/process/getReportDetail", produces="application/json;charset=UTF-8", method = RequestMethod.GET)
	public @ResponseBody String getReportDetail(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		CodeUtil code = new CodeUtil(request);
		String result = null;
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, levelUpReadyList, userSeq);
		boolean admChk2 = CommonUtil.libIsAdminRet(response, blackList, userSeq);
		boolean admChk3 = CommonUtil.libIsAdminRet(response, reportList, userSeq);

		if(admChk == true || admChk2 == true || admChk3 == true) {
			int reportSeq = request.getParameter("reportSeq") != null && !request.getParameter("reportSeq").equals("") ? Integer.parseInt(request.getParameter("reportSeq")) : 0;

			if(reportSeq > 0) {
				Map<String, String> cd = code.getCODE_REPORT_REASON_CD();
				Map<String, String> nm = code.getCODE_REPORT_REASON_NM();

				HashMap<String, Object> cdReport = new HashMap<String, Object>();
				for(int i = 1; i <= cd.size(); i++) {
					cdReport.put("CODE_REPORT_NM_" + cd.get(i), nm.get(i));
				}

				HashMap<String, Object> report = commonService.getReportDetailAdm(reportSeq);

				HashMap<String, Object> content = new HashMap<String, Object>();

				content.put("type", report.get("contentType"));

				int contentSeq = Integer.parseInt( String.valueOf( report.get("contentSeq") ) );

				HashMap<String, Object> item = new HashMap<String, Object>();
				switch(String.valueOf(report.get("contentType"))) {
					case "Question":
						content.put("typeName", "질문");

						item = commonService.getReportDetailQue( contentSeq );

						content.put("title", item.get("Title"));
						content.put("text", item.get("Contents"));
						content.put("memberSeq", item.get("MemberSeq"));

						break;
					case "QuestionReply":
						content.put("typeName", "질문 댓글");

						item = commonService.getReportDetailQueRepl( contentSeq );

						content.put("questionSeq", item.get("QuestionSeq"));
						content.put("title", item.get("Title"));
						content.put("text", item.get("Reply"));
						content.put("memberSeq", item.get("MemberSeq"));

						break;
					case "Answer":
						content.put("typeName", "답변");

						item = commonService.getReportDetailAns( contentSeq );

						content.put("questionSeq", item.get("QuestionSeq"));
						content.put("title", item.get("Title"));
						content.put("text", item.get("Reply"));
						content.put("memberSeq", item.get("MemberSeq"));

						break;

					case "AnswerReply":
						content.put("typeName", "답변 댓글");

						item = commonService.getReportDetailAnsRepl( contentSeq );

						content.put("answerSeq", item.get("AnswerSeq"));
						content.put("text", item.get("Reply"));
						content.put("memberSeq", item.get("MemberSeq"));

						int queSeq = commonService.getReportDetailAnsSeq(contentSeq);

						String title = "진문(" + queSeq + ")의 답변(" + content.get("answerSeq") + ")의 댓글";

						content.put("questionSeq", queSeq);
						content.put("title", title);

						break;
				}

				int memSeq = Integer.parseInt( String.valueOf( content.get("memberSeq") ) );

				String nickName = memberService.getMemCertCancelMsgInfo(memSeq);

				content.put("memberNickName", nickName);


				report.remove("contentType");
				report.put("content", content);

				report.put("reason", cdReport.get("CODE_REPORT_NM_" + report.get("adminStatus") ));

				result = new Gson().toJson(report);

			}
		}

		return result;
	}

	@RequestMapping(value="report/process/getCode", produces="application/json;charset=UTF-8", method = RequestMethod.GET)
	public @ResponseBody String getCode(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		CodeUtil code = new CodeUtil(request);
		String result = null;
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, levelUpReadyList, userSeq);
		boolean admChk2 = CommonUtil.libIsAdminRet(response, blackList, userSeq);
		boolean admChk3 = CommonUtil.libIsAdminRet(response, reportList, userSeq);

		if(admChk == true || admChk2 == true || admChk3 == true) {
			HashMap<String, Object> codeJson = new HashMap<String, Object>();
			HashMap<String, Object> reasonList = new HashMap<String, Object>();
			HashMap<String, Object> adminStatusList = new HashMap<String, Object>();
			HashMap<String, Object> contentsList = new HashMap<String, Object>();

			Map<String, String> cd = code.getCODE_REPORT_REASON_CD();
			Map<String, String> nm = code.getCODE_REPORT_REASON_NM();
			for(int i = 1; i <= cd.size(); i++) {
				reasonList.put(cd.get(i), nm.get(i));
			}

			Map<String, String> st_cd = code.getCODE_REPORT_ADMIN_STATUS_CD();
			Map<String, String> st_nm = code.getCODE_REPORT_ADMIN_STATUS_NM();
			for(int i = 1; i <= st_cd.size(); i++) {
				adminStatusList.put(st_cd.get(i), st_nm.get(i));
			}

			Map<String, String> co_cd = code.getCODE_REPORT_CONTENTS_CD();
			Map<String, String> co_nm = code.getCODE_REPORT_CONTENTS_NM();
			for(int i = 1; i <= co_cd.size(); i++) {
				contentsList.put(co_cd.get(i), co_nm.get(i));
			}

			codeJson.put("reason", reasonList);
			codeJson.put("adminStatus", adminStatusList);
			codeJson.put("contents", contentsList);

			result = new Gson().toJson(codeJson);
		}

		return result;
	}

	@RequestMapping(value="report/process/updateComment", method = RequestMethod.POST)
	public @ResponseBody String updateComment(@RequestBody String filterJSON, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		//EncLibrary enc = new EncLibrary();
		CodeUtil code = new CodeUtil(request);
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, recommendView, userSeq);
		if(admChk == true) {
			JSONObject jo = null;
			JSONParser jsonParse0 = new JSONParser();
			jo = (JSONObject) jsonParse0.parse(filterJSON);

			String reportSeq = CommonUtil.fn_Word_In( String.valueOf(jo.get("reportSeq")) );
			String adminComment = CommonUtil.fn_Word_In( String.valueOf(jo.get("adminComment")) );

			if(!reportSeq.equals("")) {
				String pattern = "[^-0-9]";
				boolean regex = Pattern.matches(pattern, reportSeq);

				if(regex == false) {
					String format = "yyyy-MM-dd hh:mm:ss";
					Calendar today = Calendar.getInstance();
					SimpleDateFormat type = new SimpleDateFormat(format);
					String now = type.format(today.getTime());

					HashMap<String, Object> param = new HashMap<String, Object>();
					param.put("adminComment", adminComment);
					param.put("userSeq", userSeq);
					param.put("dateReg", now);
					param.put("reportSeq", reportSeq);

					commonService.setReportAdm(param);
				}
				else {
					result = "invalid parameters";
				}
			}
		}

		return result;
	}

	@RequestMapping(value="report/process/chargeReport", method = RequestMethod.POST)
	public @ResponseBody String chargeReport(@RequestBody String filterJSON, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		//EncLibrary enc = new EncLibrary();
		CodeUtil code = new CodeUtil(request);
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, recommendView, userSeq);
		if(admChk == true) {
			JSONObject jo = null;
			JSONParser jsonParse0 = new JSONParser();
			jo = (JSONObject) jsonParse0.parse(filterJSON);

			String reportSeq = String.valueOf(jo.get("reportSeq"));
			String chargeAction = String.valueOf(jo.get("chargeAction"));


			String format = "yyyy-MM-dd hh:mm:ss";
			Calendar today = Calendar.getInstance();
			SimpleDateFormat type = new SimpleDateFormat(format);
			String now = type.format(today.getTime());

			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("userSeq", userSeq);
			param.put("dateReg", now);
			param.put("chargeAction", chargeAction);
			param.put("reportSeq", reportSeq);

			commonService.setReportChankAdm(param);


			if(chargeAction.equals(code.getCODE_REPORT_ADMIN_STATUS_DELETE())) {
				result = "eee";

				HashMap<String, Object> dParam = new HashMap<String, Object>();
				dParam.put("reportSeq", reportSeq);
				dParam.put("userSeq", userSeq);

				commonService.deleteReportContentSP(dParam);
			}
		}

		return result;
	}

	@RequestMapping(value="report/process/getReportList", produces="application/json;charset=UTF-8", method = RequestMethod.GET)
	public @ResponseBody String getReportList(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		CodeUtil code = new CodeUtil(request);
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, levelUpReadyList, userSeq);
		boolean admChk2 = CommonUtil.libIsAdminRet(response, blackList, userSeq);
		boolean admChk3 = CommonUtil.libIsAdminRet(response, reportList, userSeq);

		if(admChk == true || admChk2 == true || admChk3 == true) {
			String contentsType = request.getParameter("contentsType") != null ? request.getParameter("contentsType") : "";
			String reportReason = request.getParameter("reportReason") != null ? request.getParameter("reportReason") : "";
			String startDate = request.getParameter("startDate") != null ? request.getParameter("startDate") : "";
			String endDate = request.getParameter("endDate") != null ? request.getParameter("endDate") : "";
			String adminStatus = request.getParameter("adminStatus") != null ? request.getParameter("adminStatus") : "";
			int pageSize = request.getParameter("pageSize") != null ? Integer.parseInt(request.getParameter("pageSize")) : 30;
			int pageCursor = request.getParameter("pageCursor") != null ? Integer.parseInt(request.getParameter("pageCursor")) : 1;

			Map<String, String> cd = code.getCODE_REPORT_REASON_CD();
			Map<String, String> nm = code.getCODE_REPORT_REASON_NM();

			HashMap<String, Object> cdReport = new HashMap<String, Object>();
			for(int i = 1; i <= cd.size(); i++) {
				cdReport.put("CODE_REPORT_NM_" + cd.get(i), nm.get(i));
			}

			Map<String, String> st_cd = code.getCODE_REPORT_ADMIN_STATUS_CD();
			Map<String, String> st_nm = code.getCODE_REPORT_ADMIN_STATUS_NM();

			HashMap<String, Object> stReport = new HashMap<String, Object>();
			for(int i = 1; i <= st_cd.size(); i++) {
				stReport.put("CODE_REPORT_ADMIN_STATUS_NM_" + st_cd.get(i), st_nm.get(i));
			}


			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("contentsType", contentsType);
			param.put("reportReason", reportReason);
			param.put("startDate", startDate);
			param.put("endDate", endDate);
			param.put("adminStatus", adminStatus);
			param.put("pageSize", pageSize);
			param.put("pageCursor", pageCursor);

			List<HashMap<String, Object>> list = commonService.getReportListAjax(param);

			List<HashMap<String, Object>> listTotal = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> data1 = new HashMap<String, Object>();
			HashMap<String, Object> data2 = new HashMap<String, Object>();

			for(int i = 0; i < list.size(); i++) {
				if(i == 0) {
					JsonElement json = new Gson().toJsonTree(list.get(i));

	    			JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
	    			JSONParser jsonParse = new JSONParser();
	    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

	    	    	data1.put("totalPage", jsonObj2.get("totalPage"));
	    	    	data1.put("totalCount", jsonObj2.get("totalCount"));
				}
				if(i == 1) {
	    	    	JsonElement json = new Gson().toJsonTree(list.get(i));
					//System.out.println("json : " + json.getAsJsonArray().size());

					for(int j = 0; j < json.getAsJsonArray().size(); j++) {
						JsonObject jsonObj = json.getAsJsonArray().get(j).getAsJsonObject();
		    			JSONParser jsonParse = new JSONParser();
		    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());


		    	    	data2.put("ReportReason", cdReport.get("CODE_REPORT_NM_" + jsonObj2.get("ReportReason") ) );
		    	    	data2.put("AdminStatusCD", jsonObj2.get("AdminStatus") );
		    	    	data2.put("AdminStatus", stReport.get("CODE_REPORT_ADMIN_STATUS_NM_" + jsonObj2.get("AdminStatus") ) );

		    	    	String[] parentSeqList = String.valueOf( jsonObj2.get("ContentsParent") ).split(";");

		    	    	HashMap<String, Object> parentSeq = new HashMap<String, Object>();

		    	    	String cType = String.valueOf( jsonObj2.get("ContentsType") );
		    	    	switch(cType) {
		    	    	case "Question":
		    	    		parentSeq.put("Question", parentSeqList[0]);
		    	    		break;
		    	    	case "QuestionReply":
		    	    		parentSeq.put("Question", parentSeqList[0]);
		    	    		parentSeq.put("Reply", parentSeqList[1]);
		    	    		break;
		    	    	case "Answer":
		    	    		parentSeq.put("Question", parentSeqList[0]);
		    	    		parentSeq.put("Answer", parentSeqList[1]);
		    	    		break;
		    	    	case "AnswerReply":
		    	    		parentSeq.put("Question", parentSeqList[0]);
		    	    		parentSeq.put("Answer", parentSeqList[1]);
		    	    		parentSeq.put("Reply", parentSeqList[2]);
		    	    		break;
		    	    	default:
		    	    		parentSeq.put("result", "error");
		    	    		break;
		    	    	}

		    	    	data2.put("parentSeq", parentSeq);

		    	    	data2.put("Idx", jsonObj2.get("Idx"));
		    	    	data2.put("Seq", jsonObj2.get("Seq"));
		    	    	data2.put("ContentsSeq", jsonObj2.get("ContentsSeq"));
		    	    	data2.put("ContentsType", jsonObj2.get("ContentsType"));
		    	    	data2.put("ReportMemberSeq", jsonObj2.get("ReportMemberSeq"));
		    	    	data2.put("NickName", jsonObj2.get("NickName"));
		    	    	data2.put("ReportedMember", jsonObj2.get("ReportedMember"));
		    	    	data2.put("ReportedContents", jsonObj2.get("ReportedContents"));
		    	    	//data2.put("ContentsParent", jsonObj2.get("ContentsParent"));
		    	    	//data2.put("ReportReason", jsonObj2.get("ReportReason"));
		    	    	data2.put("ReportEtc", jsonObj2.get("ReportEtc"));
		    	    	data2.put("ReportUrl", jsonObj2.get("ReportUrl"));
		    	    	data2.put("DateReg", jsonObj2.get("DateReg"));
		    	    	//data2.put("AdminStatus", jsonObj2.get("AdminStatus"));
		    	    	data2.put("ResultMemberNickName", jsonObj2.get("ResultMemberNickName"));
		    	    	data2.put("AdminEtc", jsonObj2.get("AdminEtc"));
		    	    	data2.put("DateResult", jsonObj2.get("DateResult"));

		    	    	listTotal.add(data2);
					}
				}
			}


			int totalCount = Integer.parseInt( String.valueOf( data1.get("totalCount") ) );
			int totalPage = Integer.parseInt( String.valueOf( data1.get("totalPage") ) );


			r_res.put("totalCount", totalCount);
			r_res.put("totalPage", totalPage);
			r_res.put("list", listTotal);

			result = new Gson().toJson(r_res);
		}
		return result;
	}


	@RequestMapping("report/blackList")
	public ModelAndView blackList(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();
		//EncLibrary enc = new EncLibrary();
		//CodeUtil code = new CodeUtil(request);
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, blackList, userSeq);
		if(admChk == true) {
			//String page = request.getParameter("Page") != null ? request.getParameter("Page") : "0";
			//mav.addObject("page", page);
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "back", "top");
		}

		return mav;
	}

	@RequestMapping(value="configInputAjax", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String configInputAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String act = request.getParameter("ACT");
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();

		if(act.equals("SetLvUp")) {
			String[] hPhoto = request.getParameterValues("H_Photo[]");
			String[] hBaseAlmoney = request.getParameterValues("H_BaseAlmoney[]");
			String[] hStamp = request.getParameterValues("H_Stamp[]");
			String[] hQusReg = request.getParameterValues("H_QusReg[]");

			String[] hQusChoiceRate = request.getParameterValues("H_QusChoiceRate[]");
			String[] hAnsReg = request.getParameterValues("H_AnsReg[]");
			String[] hAnsChoiced = request.getParameterValues("H_AnsChoiced[]");
			String[] hAnsEsti = request.getParameterValues("H_AnsEsti[]");

			String[] hReply = request.getParameterValues("H_Reply[]");
			String[] hEducation = request.getParameterValues("H_Education[]");
			String[] hLvUpRecmdLv_1 = request.getParameterValues("H_LvUpRecmdLv_1[]");
			String[] hLvUpRecmdCnt_1 = request.getParameterValues("H_LvUpRecmdCnt_1[]");


			for(int i = 0; i < hStamp.length; i++) {
				HashMap<String, Object> param = new HashMap<String, Object>();

				param.put("lvUpPhoto", hPhoto[i]);
				param.put("lvUpBaseAlmoney", hBaseAlmoney[i]);
				param.put("lvUpStampCnt", hStamp[i]);
				param.put("lvUpQusRegCnt", hQusReg[i]);
				param.put("lvUpQusChoiceRate", hQusChoiceRate[i]);
				param.put("lvUpAnsRegCnt", hAnsReg[i]);
				param.put("lvUpAnsChoicedCnt", hAnsChoiced[i]);
				param.put("lvUpAnsEstiCnt", hAnsEsti[i]);
				param.put("lvUpReplyCnt", hReply[i]);
				param.put("lvUpEducationCnt", hEducation[i]);
				param.put("lvUpRecmdLv_1", hLvUpRecmdLv_1[i]);
				param.put("lvUpRecmdCnt_1", hLvUpRecmdCnt_1[i]);
				param.put("lv", i);

				configService.configNewUpdate(param);
			}

			result = CommonUtil.libJsonExit(act, r_res);
		}
		else if(act.equals("SetExch")) {
			String[] hBaseAlmoney = request.getParameterValues("H_BaseAlmoney[]");
			String[] hLimitAlmoney = request.getParameterValues("H_LimitAlmoney[]");
			String[] hAlmoneyTexRate = request.getParameterValues("H_AlmoneyTexRate[]");
			String[] hStamp = request.getParameterValues("H_Stamp[]");
			String[] hQusReg = request.getParameterValues("H_QusReg[]");
			String[] hQusChoiceRate = request.getParameterValues("H_QusChoiceRate[]");
			String[] hAnsReg = request.getParameterValues("H_AnsReg[]");
			String[] hAnsChoiced = request.getParameterValues("H_AnsChoiced[]");
			String[] hAnsEsti = request.getParameterValues("H_AnsEsti[]");

			for(int i = 0; i < hStamp.length; i++) {
				HashMap<String, Object> param = new HashMap<String, Object>();

				param.put("exchBaseAlmoney", hBaseAlmoney[i]);
				param.put("exchLimitAlmoney", hLimitAlmoney[i]);
				param.put("exchAlmoneyTexRate", hAlmoneyTexRate[i]);
				param.put("exchStampCnt", hStamp[i]);
				param.put("exchQusRegCnt", hQusReg[i]);
				param.put("exchQusChoiceRate", hQusChoiceRate[i]);
				param.put("exchAnsRegCnt", hAnsReg[i]);
				param.put("exchAnsChoicedCnt", hAnsChoiced[i]);
				param.put("exchAnsEstiCnt", hAnsEsti[i]);
				param.put("lv", i);

				configService.configExchangeUpdate(param);
			}

			result = CommonUtil.libJsonExit(act, r_res);
		}

		return result;
	}

	@RequestMapping(value="/aadmin/configSave", method = {RequestMethod.GET, RequestMethod.POST})
	public void configSave(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용
		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userSeq = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
		}

		boolean admChk = CommonUtil.libIsAdminRet(response, configInput, userSeq);
		if(admChk == true) {

			int almoneyJoin = request.getParameter("AlmoneyJoin") != null && request.getParameter("AlmoneyJoin") != "" ? Integer.parseInt(request.getParameter("AlmoneyJoin")) : 0;
			int almoneyExchange = request.getParameter("AlmoneyExchange") != null && request.getParameter("AlmoneyExchange") != "" ? Integer.parseInt(request.getParameter("AlmoneyExchange")) : 0;
			int almoneyMoveQuestion = request.getParameter("AlmoneyMoveQuestion") != null && request.getParameter("AlmoneyMoveQuestion") != "" ? Integer.parseInt(request.getParameter("AlmoneyMoveQuestion")) : 0;
			int questionMin = request.getParameter("QuestionMin") != null && request.getParameter("QuestionMin") != "" ? Integer.parseInt(request.getParameter("QuestionMin")) : 0;
			int questionMax = request.getParameter("QuestionMax") != null && request.getParameter("QuestionMax") != "" ? Integer.parseInt(request.getParameter("QuestionMax")) : 0;
			int writeMax = request.getParameter("WriteMax") != null && request.getParameter("WriteMax") != "" ? Integer.parseInt(request.getParameter("QuestionMax")) : 0;
			int fileMax = request.getParameter("FileMax") != null && request.getParameter("FileMax") != "" ? Integer.parseInt(request.getParameter("FileMax")) : 0;
			int viewMoneySum = request.getParameter("ViewMoneySum") != null && request.getParameter("ViewMoneySum") != "" ? Integer.parseInt(request.getParameter("ViewMoneySum")) : 0;
			int viewMoneyQ = request.getParameter("ViewMoneyQ") != null && request.getParameter("ViewMoneyQ") != "" ? Integer.parseInt(request.getParameter("ViewMoneyQ")) : 0;
			int viewMoneyA = request.getParameter("ViewMoneyA") != null && request.getParameter("ViewMoneyA") != "" ? Integer.parseInt(request.getParameter("ViewMoneyA")) : 0;
			String nickNotUse = request.getParameter("NickNotUse") != null && request.getParameter("NickNotUse") != "" ? request.getParameter("NickNotUse") : "";
			int moneyCompany = request.getParameter("MoneyCompany") != null && request.getParameter("MoneyCompany") != "" ? Integer.parseInt(request.getParameter("MoneyCompany")) : 0;
			BigDecimal donationMoney = BigDecimal.ZERO;
			BigDecimal chuCodeQ = BigDecimal.ZERO;
			BigDecimal chuCodeA = BigDecimal.ZERO;

			//회원가입시 인증방식 선택과 sms 발송 횟수 제한 설정 [추가 2018.01.22 차건환]
			String memJoinCertType = request.getParameter("MemJoinCertType") != null && request.getParameter("MemJoinCertType") != "" ? request.getParameter("MemJoinCertType") : "";
			int memJoinSmsLimitCnt = request.getParameter("MemJoinSmsLimitCnt") != null && request.getParameter("MemJoinSmsLimitCnt") != "" ? Integer.parseInt(request.getParameter("MemJoinSmsLimitCnt")) : 0;
			int memJoinSmsTimeOut = request.getParameter("MemJoinSmsTimeOut") != null && request.getParameter("MemJoinSmsTimeOut") != "" ? Integer.parseInt(request.getParameter("MemJoinSmsTimeOut")) : 0;
			int memPwSmsLimitCnt = request.getParameter("MemPwSmsLimitCnt") != null && request.getParameter("MemPwSmsLimitCnt") != "" ? Integer.parseInt(request.getParameter("MemPwSmsLimitCnt")) : 0;

			//회원 레벨별 글쓰기 제한설정 조건 [추가(2018.02.26): 김현구]
			int lv1_LimitQueDayRegistCnt = request.getParameter("Lv1_LimitQueDayRegistCnt") != null && request.getParameter("Lv1_LimitQueDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv1_LimitQueDayRegistCnt")) : 0;
			int lv1_LimitQueDayDupRegistCnt = request.getParameter("Lv1_LimitQueDayDupRegistCnt") != null && request.getParameter("Lv1_LimitQueDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv1_LimitQueDayDupRegistCnt")) : 0;
			int lv1_LimitQueContinueRegistTime = request.getParameter("Lv1_LimitQueContinueRegistTime") != null && request.getParameter("Lv1_LimitQueContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv1_LimitQueContinueRegistTime")) : 0;
			int lv1_LimitAnsDayRegistCnt = request.getParameter("Lv1_LimitAnsDayRegistCnt") != null && request.getParameter("Lv1_LimitAnsDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv1_LimitAnsDayRegistCnt")) : 0;
			int lv1_LimitAnsDayDupRegistCnt = request.getParameter("Lv1_LimitAnsDayDupRegistCnt") != null && request.getParameter("Lv1_LimitAnsDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv1_LimitAnsDayDupRegistCnt")) : 0;
			int lv1_LimitAnsContinueRegistTime = request.getParameter("Lv1_LimitAnsContinueRegistTime") != null && request.getParameter("Lv1_LimitAnsContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv1_LimitAnsContinueRegistTime")) : 0;
			int lv1_LimitRepDayRegistCnt = request.getParameter("Lv1_LimitRepDayRegistCnt") != null && request.getParameter("Lv1_LimitRepDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv1_LimitRepDayRegistCnt")) : 0;
			int lv1_LimitRepDayDupRegistCnt = request.getParameter("Lv1_LimitRepDayDupRegistCnt") != null && request.getParameter("Lv1_LimitRepDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv1_LimitRepDayDupRegistCnt")) : 0;
			int lv1_LimitRepContinueRegistTime = request.getParameter("Lv1_LimitRepContinueRegistTime") != null && request.getParameter("Lv1_LimitRepContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv1_LimitRepContinueRegistTime")) : 0;

			int lv2_LimitQueDayRegistCnt = request.getParameter("Lv2_LimitQueDayRegistCnt") != null && request.getParameter("Lv2_LimitQueDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv2_LimitQueDayRegistCnt")) : 0;
			int lv2_LimitQueDayDupRegistCnt = request.getParameter("Lv2_LimitQueDayDupRegistCnt") != null && request.getParameter("Lv2_LimitQueDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv2_LimitQueDayDupRegistCnt")) : 0;
			int lv2_LimitQueContinueRegistTime = request.getParameter("Lv2_LimitQueContinueRegistTime") != null && request.getParameter("Lv2_LimitQueContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv2_LimitQueContinueRegistTime")) : 0;
			int lv2_LimitAnsDayRegistCnt = request.getParameter("Lv2_LimitAnsDayRegistCnt") != null && request.getParameter("Lv2_LimitAnsDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv2_LimitAnsDayRegistCnt")) : 0;
			int lv2_LimitAnsDayDupRegistCnt = request.getParameter("Lv2_LimitAnsDayDupRegistCnt") != null && request.getParameter("Lv2_LimitAnsDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv2_LimitAnsDayDupRegistCnt")) : 0;
			int lv2_LimitAnsContinueRegistTime = request.getParameter("Lv2_LimitAnsContinueRegistTime") != null && request.getParameter("Lv2_LimitAnsContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv2_LimitAnsContinueRegistTime")) : 0;
			int lv2_LimitRepDayRegistCnt = request.getParameter("Lv2_LimitRepDayRegistCnt") != null && request.getParameter("Lv2_LimitRepDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv2_LimitRepDayRegistCnt")) : 0;
			int lv2_LimitRepDayDupRegistCnt = request.getParameter("Lv2_LimitRepDayDupRegistCnt") != null && request.getParameter("Lv2_LimitRepDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv2_LimitRepDayDupRegistCnt")) : 0;
			int lv2_LimitRepContinueRegistTime = request.getParameter("Lv2_LimitRepContinueRegistTime") != null && request.getParameter("Lv2_LimitRepContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv2_LimitRepContinueRegistTime")) : 0;

			int lv3_LimitQueDayRegistCnt = request.getParameter("Lv3_LimitQueDayRegistCnt") != null && request.getParameter("Lv3_LimitQueDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv3_LimitQueDayRegistCnt")) : 0;
			int lv3_LimitQueDayDupRegistCnt = request.getParameter("Lv3_LimitQueDayDupRegistCnt") != null && request.getParameter("Lv3_LimitQueDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv3_LimitQueDayDupRegistCnt")) : 0;
			int lv3_LimitQueContinueRegistTime = request.getParameter("Lv3_LimitQueContinueRegistTime") != null && request.getParameter("Lv3_LimitQueContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv3_LimitQueContinueRegistTime")) : 0;
			int lv3_LimitAnsDayRegistCnt = request.getParameter("Lv3_LimitAnsDayRegistCnt") != null && request.getParameter("Lv3_LimitAnsDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv3_LimitAnsDayRegistCnt")) : 0;
			int lv3_LimitAnsDayDupRegistCnt = request.getParameter("Lv3_LimitAnsDayDupRegistCnt") != null && request.getParameter("Lv3_LimitAnsDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv3_LimitAnsDayDupRegistCnt")) : 0;
			int lv3_LimitAnsContinueRegistTime = request.getParameter("Lv3_LimitAnsContinueRegistTime") != null && request.getParameter("Lv3_LimitAnsContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv3_LimitAnsContinueRegistTime")) : 0;
			int lv3_LimitRepDayRegistCnt = request.getParameter("Lv3_LimitRepDayRegistCnt") != null && request.getParameter("Lv3_LimitRepDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv3_LimitRepDayRegistCnt")) : 0;
			int lv3_LimitRepDayDupRegistCnt = request.getParameter("Lv3_LimitRepDayDupRegistCnt") != null && request.getParameter("Lv3_LimitRepDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv3_LimitRepDayDupRegistCnt")) : 0;
			int lv3_LimitRepContinueRegistTime = request.getParameter("Lv3_LimitRepContinueRegistTime") != null && request.getParameter("Lv3_LimitRepContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv3_LimitRepContinueRegistTime")) : 0;

			int lv4_LimitQueDayRegistCnt = request.getParameter("Lv4_LimitQueDayRegistCnt") != null && request.getParameter("Lv4_LimitQueDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv4_LimitQueDayRegistCnt")) : 0;
			int lv4_LimitQueDayDupRegistCnt = request.getParameter("Lv4_LimitQueDayDupRegistCnt") != null && request.getParameter("Lv4_LimitQueDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv4_LimitQueDayDupRegistCnt")) : 0;
			int lv4_LimitQueContinueRegistTime = request.getParameter("Lv4_LimitQueContinueRegistTime") != null && request.getParameter("Lv4_LimitQueContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv4_LimitQueContinueRegistTime")) : 0;
			int lv4_LimitAnsDayRegistCnt = request.getParameter("Lv4_LimitAnsDayRegistCnt") != null && request.getParameter("Lv4_LimitAnsDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv4_LimitAnsDayRegistCnt")) : 0;
			int lv4_LimitAnsDayDupRegistCnt = request.getParameter("Lv4_LimitAnsDayDupRegistCnt") != null && request.getParameter("Lv4_LimitAnsDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv4_LimitAnsDayDupRegistCnt")) : 0;
			int lv4_LimitAnsContinueRegistTime = request.getParameter("Lv4_LimitAnsContinueRegistTime") != null && request.getParameter("Lv4_LimitAnsContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv4_LimitAnsContinueRegistTime")) : 0;
			int lv4_LimitRepDayRegistCnt = request.getParameter("Lv4_LimitRepDayRegistCnt") != null && request.getParameter("Lv4_LimitRepDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv4_LimitRepDayRegistCnt")) : 0;
			int lv4_LimitRepDayDupRegistCnt = request.getParameter("Lv4_LimitRepDayDupRegistCnt") != null && request.getParameter("Lv4_LimitRepDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv4_LimitRepDayDupRegistCnt")) : 0;
			int lv4_LimitRepContinueRegistTime = request.getParameter("Lv4_LimitRepContinueRegistTime") != null && request.getParameter("Lv4_LimitRepContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv4_LimitRepContinueRegistTime")) : 0;

			int lv5_LimitQueDayRegistCnt = request.getParameter("Lv5_LimitQueDayRegistCnt") != null && request.getParameter("Lv5_LimitQueDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv5_LimitQueDayRegistCnt")) : 0;
			int lv5_LimitQueDayDupRegistCnt = request.getParameter("Lv5_LimitQueDayDupRegistCnt") != null && request.getParameter("Lv5_LimitQueDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv5_LimitQueDayDupRegistCnt")) : 0;
			int lv5_LimitQueContinueRegistTime = request.getParameter("Lv5_LimitQueContinueRegistTime") != null && request.getParameter("Lv5_LimitQueContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv5_LimitQueContinueRegistTime")) : 0;
			int lv5_LimitAnsDayRegistCnt = request.getParameter("Lv5_LimitAnsDayRegistCnt") != null && request.getParameter("Lv5_LimitAnsDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv5_LimitAnsDayRegistCnt")) : 0;
			int lv5_LimitAnsDayDupRegistCnt = request.getParameter("Lv5_LimitAnsDayDupRegistCnt") != null && request.getParameter("Lv5_LimitAnsDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv5_LimitAnsDayDupRegistCnt")) : 0;
			int lv5_LimitAnsContinueRegistTime = request.getParameter("Lv5_LimitAnsContinueRegistTime") != null && request.getParameter("Lv5_LimitAnsContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv5_LimitAnsContinueRegistTime")) : 0;
			int lv5_LimitRepDayRegistCnt = request.getParameter("Lv5_LimitRepDayRegistCnt") != null && request.getParameter("Lv5_LimitRepDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv5_LimitRepDayRegistCnt")) : 0;
			int lv5_LimitRepDayDupRegistCnt = request.getParameter("Lv5_LimitRepDayDupRegistCnt") != null && request.getParameter("Lv5_LimitRepDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv5_LimitRepDayDupRegistCnt")) : 0;
			int lv5_LimitRepContinueRegistTime = request.getParameter("Lv5_LimitRepContinueRegistTime") != null && request.getParameter("Lv5_LimitRepContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv5_LimitRepContinueRegistTime")) : 0;

			int lv6_LimitQueDayRegistCnt = request.getParameter("Lv6_LimitQueDayRegistCnt") != null && request.getParameter("Lv6_LimitQueDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv6_LimitQueDayRegistCnt")) : 0;
			int lv6_LimitQueDayDupRegistCnt = request.getParameter("Lv6_LimitQueDayDupRegistCnt") != null && request.getParameter("Lv6_LimitQueDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv6_LimitQueDayDupRegistCnt")) : 0;
			int lv6_LimitQueContinueRegistTime = request.getParameter("Lv6_LimitQueContinueRegistTime") != null && request.getParameter("Lv6_LimitQueContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv6_LimitQueContinueRegistTime")) : 0;
			int lv6_LimitAnsDayRegistCnt = request.getParameter("Lv6_LimitAnsDayRegistCnt") != null && request.getParameter("Lv6_LimitAnsDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv6_LimitAnsDayRegistCnt")) : 0;
			int lv6_LimitAnsDayDupRegistCnt = request.getParameter("Lv6_LimitAnsDayDupRegistCnt") != null && request.getParameter("Lv6_LimitAnsDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv6_LimitAnsDayDupRegistCnt")) : 0;
			int lv6_LimitAnsContinueRegistTime = request.getParameter("Lv6_LimitAnsContinueRegistTime") != null && request.getParameter("Lv6_LimitAnsContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv6_LimitAnsContinueRegistTime")) : 0;
			int lv6_LimitRepDayRegistCnt = request.getParameter("Lv6_LimitRepDayRegistCnt") != null && request.getParameter("Lv6_LimitRepDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv6_LimitRepDayRegistCnt")) : 0;
			int lv6_LimitRepDayDupRegistCnt = request.getParameter("Lv6_LimitRepDayDupRegistCnt") != null && request.getParameter("Lv6_LimitRepDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv6_LimitRepDayDupRegistCnt")) : 0;
			int lv6_LimitRepContinueRegistTime = request.getParameter("Lv6_LimitRepContinueRegistTime") != null && request.getParameter("Lv6_LimitRepContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv6_LimitRepContinueRegistTime")) : 0;

			int lv7_LimitQueDayRegistCnt = request.getParameter("Lv7_LimitQueDayRegistCnt") != null && request.getParameter("Lv7_LimitQueDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv7_LimitQueDayRegistCnt")) : 0;
			int lv7_LimitQueDayDupRegistCnt = request.getParameter("Lv7_LimitQueDayDupRegistCnt") != null && request.getParameter("Lv7_LimitQueDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv7_LimitQueDayDupRegistCnt")) : 0;
			int lv7_LimitQueContinueRegistTime = request.getParameter("Lv7_LimitQueContinueRegistTime") != null && request.getParameter("Lv7_LimitQueContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv7_LimitQueContinueRegistTime")) : 0;
			int lv7_LimitAnsDayRegistCnt = request.getParameter("Lv7_LimitAnsDayRegistCnt") != null && request.getParameter("Lv7_LimitAnsDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv7_LimitAnsDayRegistCnt")) : 0;
			int lv7_LimitAnsDayDupRegistCnt = request.getParameter("Lv7_LimitAnsDayDupRegistCnt") != null && request.getParameter("Lv7_LimitAnsDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv7_LimitAnsDayDupRegistCnt")) : 0;
			int lv7_LimitAnsContinueRegistTime = request.getParameter("Lv7_LimitAnsContinueRegistTime") != null && request.getParameter("Lv7_LimitAnsContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv7_LimitAnsContinueRegistTime")) : 0;
			int lv7_LimitRepDayRegistCnt = request.getParameter("Lv7_LimitRepDayRegistCnt") != null && request.getParameter("Lv7_LimitRepDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv7_LimitRepDayRegistCnt")) : 0;
			int lv7_LimitRepDayDupRegistCnt = request.getParameter("Lv7_LimitRepDayDupRegistCnt") != null && request.getParameter("Lv7_LimitRepDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv7_LimitRepDayDupRegistCnt")) : 0;
			int lv7_LimitRepContinueRegistTime = request.getParameter("Lv7_LimitRepContinueRegistTime") != null && request.getParameter("Lv7_LimitRepContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv7_LimitRepContinueRegistTime")) : 0;

			int lv8_LimitQueDayRegistCnt = request.getParameter("Lv8_LimitQueDayRegistCnt") != null && request.getParameter("Lv8_LimitQueDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv8_LimitQueDayRegistCnt")) : 0;
			int lv8_LimitQueDayDupRegistCnt = request.getParameter("Lv8_LimitQueDayDupRegistCnt") != null && request.getParameter("Lv8_LimitQueDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv8_LimitQueDayDupRegistCnt")) : 0;
			int lv8_LimitQueContinueRegistTime = request.getParameter("Lv8_LimitQueContinueRegistTime") != null && request.getParameter("Lv8_LimitQueContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv8_LimitQueContinueRegistTime")) : 0;
			int lv8_LimitAnsDayRegistCnt = request.getParameter("Lv8_LimitAnsDayRegistCnt") != null && request.getParameter("Lv8_LimitAnsDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv8_LimitAnsDayRegistCnt")) : 0;
			int lv8_LimitAnsDayDupRegistCnt = request.getParameter("Lv8_LimitAnsDayDupRegistCnt") != null && request.getParameter("Lv8_LimitAnsDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv8_LimitAnsDayDupRegistCnt")) : 0;
			int lv8_LimitAnsContinueRegistTime = request.getParameter("Lv8_LimitAnsContinueRegistTime") != null && request.getParameter("Lv8_LimitAnsContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv8_LimitAnsContinueRegistTime")) : 0;
			int lv8_LimitRepDayRegistCnt = request.getParameter("Lv8_LimitRepDayRegistCnt") != null && request.getParameter("Lv8_LimitRepDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv8_LimitRepDayRegistCnt")) : 0;
			int lv8_LimitRepDayDupRegistCnt = request.getParameter("Lv8_LimitRepDayDupRegistCnt") != null && request.getParameter("Lv8_LimitRepDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv8_LimitRepDayDupRegistCnt")) : 0;
			int lv8_LimitRepContinueRegistTime = request.getParameter("Lv8_LimitRepContinueRegistTime") != null && request.getParameter("Lv8_LimitRepContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv8_LimitRepContinueRegistTime")) : 0;

			int lv9_LimitQueDayRegistCnt = request.getParameter("Lv9_LimitQueDayRegistCnt") != null && request.getParameter("Lv9_LimitQueDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv9_LimitQueDayRegistCnt")) : 0;
			int lv9_LimitQueDayDupRegistCnt = request.getParameter("Lv9_LimitQueDayDupRegistCnt") != null && request.getParameter("Lv9_LimitQueDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv9_LimitQueDayDupRegistCnt")) : 0;
			int lv9_LimitQueContinueRegistTime = request.getParameter("Lv9_LimitQueContinueRegistTime") != null && request.getParameter("Lv9_LimitQueContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv9_LimitQueContinueRegistTime")) : 0;
			int lv9_LimitAnsDayRegistCnt = request.getParameter("Lv9_LimitAnsDayRegistCnt") != null && request.getParameter("Lv9_LimitAnsDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv9_LimitAnsDayRegistCnt")) : 0;
			int lv9_LimitAnsDayDupRegistCnt = request.getParameter("Lv9_LimitAnsDayDupRegistCnt") != null && request.getParameter("Lv9_LimitAnsDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv9_LimitAnsDayDupRegistCnt")) : 0;
			int lv9_LimitAnsContinueRegistTime = request.getParameter("Lv9_LimitAnsContinueRegistTime") != null && request.getParameter("Lv9_LimitAnsContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv9_LimitAnsContinueRegistTime")) : 0;
			int lv9_LimitRepDayRegistCnt = request.getParameter("Lv9_LimitRepDayRegistCnt") != null && request.getParameter("Lv9_LimitRepDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv9_LimitRepDayRegistCnt")) : 0;
			int lv9_LimitRepDayDupRegistCnt = request.getParameter("Lv9_LimitRepDayDupRegistCnt") != null && request.getParameter("Lv9_LimitRepDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv9_LimitRepDayDupRegistCnt")) : 0;
			int lv9_LimitRepContinueRegistTime = request.getParameter("Lv9_LimitRepContinueRegistTime") != null && request.getParameter("Lv9_LimitRepContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv9_LimitRepContinueRegistTime")) : 0;

			int lv10_LimitQueDayRegistCnt = request.getParameter("Lv10_LimitQueDayRegistCnt") != null && request.getParameter("Lv10_LimitQueDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv10_LimitQueDayRegistCnt")) : 0;
			int lv10_LimitQueDayDupRegistCnt = request.getParameter("Lv10_LimitQueDayDupRegistCnt") != null && request.getParameter("Lv10_LimitQueDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv10_LimitQueDayDupRegistCnt")) : 0;
			int lv10_LimitQueContinueRegistTime = request.getParameter("Lv10_LimitQueContinueRegistTime") != null && request.getParameter("Lv10_LimitQueContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv10_LimitQueContinueRegistTime")) : 0;
			int lv10_LimitAnsDayRegistCnt = request.getParameter("Lv10_LimitAnsDayRegistCnt") != null && request.getParameter("Lv10_LimitAnsDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv10_LimitAnsDayRegistCnt")) : 0;
			int lv10_LimitAnsDayDupRegistCnt = request.getParameter("Lv10_LimitAnsDayDupRegistCnt") != null && request.getParameter("Lv10_LimitAnsDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv10_LimitAnsDayDupRegistCnt")) : 0;
			int lv10_LimitAnsContinueRegistTime = request.getParameter("Lv10_LimitAnsContinueRegistTime") != null && request.getParameter("Lv10_LimitAnsContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv10_LimitAnsContinueRegistTime")) : 0;
			int lv10_LimitRepDayRegistCnt = request.getParameter("Lv10_LimitRepDayRegistCnt") != null && request.getParameter("Lv10_LimitRepDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv10_LimitRepDayRegistCnt")) : 0;
			int lv10_LimitRepDayDupRegistCnt = request.getParameter("Lv10_LimitRepDayDupRegistCnt") != null && request.getParameter("Lv10_LimitRepDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv10_LimitRepDayDupRegistCnt")) : 0;
			int lv10_LimitRepContinueRegistTime = request.getParameter("Lv10_LimitRepContinueRegistTime") != null && request.getParameter("Lv10_LimitRepContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv10_LimitRepContinueRegistTime")) : 0;

			int lv11_LimitQueDayRegistCnt = request.getParameter("Lv11_LimitQueDayRegistCnt") != null && request.getParameter("Lv11_LimitQueDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv11_LimitQueDayRegistCnt")) : 0;
			int lv11_LimitQueDayDupRegistCnt = request.getParameter("Lv11_LimitQueDayDupRegistCnt") != null && request.getParameter("Lv11_LimitQueDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv11_LimitQueDayDupRegistCnt")) : 0;
			int lv11_LimitQueContinueRegistTime = request.getParameter("Lv11_LimitQueContinueRegistTime") != null && request.getParameter("Lv11_LimitQueContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv11_LimitQueContinueRegistTime")) : 0;
			int lv11_LimitAnsDayRegistCnt = request.getParameter("Lv11_LimitAnsDayRegistCnt") != null && request.getParameter("Lv11_LimitAnsDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv11_LimitAnsDayRegistCnt")) : 0;
			int lv11_LimitAnsDayDupRegistCnt = request.getParameter("Lv11_LimitAnsDayDupRegistCnt") != null && request.getParameter("Lv11_LimitAnsDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv11_LimitAnsDayDupRegistCnt")) : 0;
			int lv11_LimitAnsContinueRegistTime = request.getParameter("Lv11_LimitAnsContinueRegistTime") != null && request.getParameter("Lv11_LimitAnsContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv11_LimitAnsContinueRegistTime")) : 0;
			int lv11_LimitRepDayRegistCnt = request.getParameter("Lv11_LimitRepDayRegistCnt") != null && request.getParameter("Lv11_LimitRepDayRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv11_LimitRepDayRegistCnt")) : 0;
			int lv11_LimitRepDayDupRegistCnt = request.getParameter("Lv11_LimitRepDayDupRegistCnt") != null && request.getParameter("Lv11_LimitRepDayDupRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv11_LimitRepDayDupRegistCnt")) : 0;
			int lv11_LimitRepContinueRegistTime = request.getParameter("Lv11_LimitRepContinueRegistTime") != null && request.getParameter("Lv11_LimitRepContinueRegistTime") != "" ? Integer.parseInt(request.getParameter("Lv11_LimitRepContinueRegistTime")) : 0;



			//회원 레벨별 승천기준 설정 조건 [추가(2018.03.14, 2018.03.06, 2018.02.26): 김현구]



			String lv_LevelUpAuto_Yn = request.getParameter("Lv_LevelUpAuto_Yn") != null && request.getParameter("Lv_LevelUpAuto_Yn") != "" ? request.getParameter("Lv_LevelUpAuto_Yn") : "N";
			String lv2_LevelUpAuto_Yn = request.getParameter("Lv2_LevelUpAuto_Yn") != null && request.getParameter("Lv2_LevelUpAuto_Yn") != "" ? request.getParameter("Lv2_LevelUpAuto_Yn") : "N";
			String lv3_LevelUpAuto_Yn = request.getParameter("Lv3_LevelUpAuto_Yn") != null && request.getParameter("Lv3_LevelUpAuto_Yn") != "" ? request.getParameter("Lv3_LevelUpAuto_Yn") : "N";
			String lv4_LevelUpAuto_Yn = request.getParameter("Lv4_LevelUpAuto_Yn") != null && request.getParameter("Lv4_LevelUpAuto_Yn") != "" ? request.getParameter("Lv4_LevelUpAuto_Yn") : "N";
			String lv5_LevelUpAuto_Yn = request.getParameter("Lv5_LevelUpAuto_Yn") != null && request.getParameter("Lv5_LevelUpAuto_Yn") != "" ? request.getParameter("Lv5_LevelUpAuto_Yn") : "N";
			String lv6_LevelUpAuto_Yn = request.getParameter("Lv6_LevelUpAuto_Yn") != null && request.getParameter("Lv6_LevelUpAuto_Yn") != "" ? request.getParameter("Lv6_LevelUpAuto_Yn") : "N";
			String lv7_LevelUpAuto_Yn = request.getParameter("Lv7_LevelUpAuto_Yn") != null && request.getParameter("Lv7_LevelUpAuto_Yn") != "" ? request.getParameter("Lv7_LevelUpAuto_Yn") : "N";
			String lv8_LevelUpAuto_Yn = request.getParameter("Lv8_LevelUpAuto_Yn") != null && request.getParameter("Lv8_LevelUpAuto_Yn") != "" ? request.getParameter("Lv8_LevelUpAuto_Yn") : "N";
			String lv9_LevelUpAuto_Yn = request.getParameter("Lv9_LevelUpAuto_Yn") != null && request.getParameter("Lv9_LevelUpAuto_Yn") != "" ? request.getParameter("Lv9_LevelUpAuto_Yn") : "N";
			String lv10_LevelUpAuto_Yn = request.getParameter("Lv10_LevelUpAuto_Yn") != null && request.getParameter("Lv10_LevelUpAuto_Yn") != "" ? request.getParameter("Lv10_LevelUpAuto_Yn") : "N";
			String lv11_LevelUpAuto_Yn= request.getParameter("Lv11_LevelUpAuto_Yn") != null && request.getParameter("Lv11_LevelUpAuto_Yn") != "" ? request.getParameter("Lv11_LevelUpAuto_Yn") : "N";

			if(lv_LevelUpAuto_Yn.equals("N")) {
				lv2_LevelUpAuto_Yn = "N";
				lv3_LevelUpAuto_Yn = "N";
				lv4_LevelUpAuto_Yn = "N";
				lv5_LevelUpAuto_Yn = "N";
				lv6_LevelUpAuto_Yn = "N";
				lv7_LevelUpAuto_Yn = "N";
				lv8_LevelUpAuto_Yn = "N";
			}



			int lv2_LimitJoinAfterDay = request.getParameter("Lv2_LimitJoinAfterDay") != null && request.getParameter("Lv2_LimitJoinAfterDay") != "" ? Integer.parseInt(request.getParameter("Lv2_LimitJoinAfterDay")) : 0;
			BigDecimal lv2_LimitAlmoney = request.getParameter("Lv2_LimitAlmoney") != null && request.getParameter("Lv2_LimitAlmoney") != "" ? new BigDecimal(request.getParameter("Lv2_LimitAlmoney")) : BigDecimal.ZERO;
			int lv2_LimitAnsEstiCnt = request.getParameter("Lv2_LimitAnsEstiCnt") != null && request.getParameter("Lv2_LimitAnsEstiCnt") != "" ? Integer.parseInt(request.getParameter("Lv2_LimitAnsEstiCnt")) : 0;
			int lv2_LimitChuMemCnt = request.getParameter("Lv2_LimitChuMemCnt") != null && request.getParameter("Lv2_LimitChuMemCnt") != "" ? Integer.parseInt(request.getParameter("Lv2_LimitChuMemCnt")) : 0;
			String lv2_LimitPhotoYn = request.getParameter("Lv2_LimitPhotoYn") != null && request.getParameter("Lv2_LimitPhotoYn") != "" ? request.getParameter("Lv2_LimitPhotoYn") : "N";
			int lv2_LimitReplyCnt = request.getParameter("Lv2_LimitReplyCnt") != null && request.getParameter("Lv2_LimitReplyCnt") != "" ? Integer.parseInt(request.getParameter("Lv2_LimitReplyCnt")) : 0;





			int lv3_LimitLvMtPeriod = request.getParameter("Lv3_LimitLvMtPeriod") != null && request.getParameter("Lv3_LimitLvMtPeriod") != "" ? Integer.parseInt(request.getParameter("Lv3_LimitLvMtPeriod")) : 0;
			int lv3_LimitQueRegistCnt = request.getParameter("Lv3_LimitQueRegistCnt") != null && request.getParameter("Lv3_LimitQueRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv3_LimitQueRegistCnt")) : 0;
			int lv3_LimitAnsRegistCnt = request.getParameter("Lv3_LimitAnsRegistCnt") != null && request.getParameter("Lv3_LimitAnsRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv3_LimitAnsRegistCnt")) : 0;
			int lv3_LimitAnsChoiceCnt = request.getParameter("Lv3_LimitAnsChoiceCnt") != null && request.getParameter("Lv3_LimitAnsChoiceCnt") != "" ? Integer.parseInt(request.getParameter("Lv3_LimitAnsChoiceCnt")) : 0;
			int lv3_LimitAnsEstiCnt = request.getParameter("Lv3_LimitAnsEstiCnt") != null && request.getParameter("Lv3_LimitAnsEstiCnt") != "" ? Integer.parseInt(request.getParameter("Lv3_LimitAnsEstiCnt")) : 0;
			int lv3_LimitAnsEstiPoint = request.getParameter("Lv3_LimitAnsEstiPoint") != null && request.getParameter("Lv3_LimitAnsEstiPoint") != "" ? Integer.parseInt(request.getParameter("Lv3_LimitAnsEstiPoint")) : 0;
			int lv3_LimitChuMemCnt = request.getParameter("Lv3_LimitChuMemCnt") != null && request.getParameter("Lv3_LimitChuMemCnt") != "" ? Integer.parseInt(request.getParameter("Lv3_LimitChuMemCnt")) : 0;
			int lv3_LimitReplyCnt = request.getParameter("Lv3_LimitReplyCnt") != null && request.getParameter("Lv3_LimitReplyCnt") != "" ? Integer.parseInt(request.getParameter("Lv3_LimitReplyCnt")) : 0;

			int lv4_LimitLvMtPeriod = request.getParameter("Lv4_LimitLvMtPeriod") != null && request.getParameter("Lv4_LimitLvMtPeriod") != "" ? Integer.parseInt(request.getParameter("Lv4_LimitLvMtPeriod")) : 0;
			int lv4_LimitQueRegistCnt = request.getParameter("Lv4_LimitQueRegistCnt") != null && request.getParameter("Lv4_LimitQueRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv4_LimitQueRegistCnt")) : 0;
			int lv4_LimitAnsRegistCnt = request.getParameter("Lv4_LimitAnsRegistCnt") != null && request.getParameter("Lv4_LimitAnsRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv4_LimitAnsRegistCnt")) : 0;
			int lv4_LimitAnsChoiceCnt = request.getParameter("Lv4_LimitAnsChoiceCnt") != null && request.getParameter("Lv4_LimitAnsChoiceCnt") != "" ? Integer.parseInt(request.getParameter("Lv4_LimitAnsChoiceCnt")) : 0;
			int lv4_LimitAnsEstiCnt = request.getParameter("Lv4_LimitAnsEstiCnt") != null && request.getParameter("Lv4_LimitAnsEstiCnt") != "" ? Integer.parseInt(request.getParameter("Lv4_LimitAnsEstiCnt")) : 0;
			int lv4_LimitAnsEstiPoint = request.getParameter("Lv4_LimitAnsEstiPoint") != null && request.getParameter("Lv4_LimitAnsEstiPoint") != "" ? Integer.parseInt(request.getParameter("Lv4_LimitAnsEstiPoint")) : 0;
			int lv4_LimitChuMemCnt = request.getParameter("Lv4_LimitChuMemCnt") != null && request.getParameter("Lv4_LimitChuMemCnt") != "" ? Integer.parseInt(request.getParameter("Lv4_LimitChuMemCnt")) : 0;
			int lv4_LimitReplyCnt = request.getParameter("Lv4_LimitReplyCnt") != null && request.getParameter("Lv4_LimitReplyCnt") != "" ? Integer.parseInt(request.getParameter("Lv4_LimitReplyCnt")) : 0;

			int lv5_LimitLvMtPeriod = request.getParameter("Lv5_LimitLvMtPeriod") != null && request.getParameter("Lv5_LimitLvMtPeriod") != "" ? Integer.parseInt(request.getParameter("Lv5_LimitLvMtPeriod")) : 0;
			int lv5_LimitQueRegistCnt = request.getParameter("Lv5_LimitQueRegistCnt") != null && request.getParameter("Lv5_LimitQueRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv5_LimitQueRegistCnt")) : 0;
			int lv5_LimitAnsRegistCnt = request.getParameter("Lv5_LimitAnsRegistCnt") != null && request.getParameter("Lv5_LimitAnsRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv5_LimitAnsRegistCnt")) : 0;
			int lv5_LimitAnsChoiceCnt = request.getParameter("Lv5_LimitAnsChoiceCnt") != null && request.getParameter("Lv5_LimitAnsChoiceCnt") != "" ? Integer.parseInt(request.getParameter("Lv5_LimitAnsChoiceCnt")) : 0;
			int lv5_LimitAnsEstiCnt = request.getParameter("Lv5_LimitAnsEstiCnt") != null && request.getParameter("Lv5_LimitAnsEstiCnt") != "" ? Integer.parseInt(request.getParameter("Lv5_LimitAnsEstiCnt")) : 0;
			int lv5_LimitAnsEstiPoint = request.getParameter("Lv5_LimitAnsEstiPoint") != null && request.getParameter("Lv5_LimitAnsEstiPoint") != "" ? Integer.parseInt(request.getParameter("Lv5_LimitAnsEstiPoint")) : 0;
			int lv5_LimitChuMemCnt = request.getParameter("Lv5_LimitChuMemCnt") != null && request.getParameter("Lv5_LimitChuMemCnt") != "" ? Integer.parseInt(request.getParameter("Lv5_LimitChuMemCnt")) : 0;
			int lv5_LimitReplyCnt = request.getParameter("Lv5_LimitReplyCnt") != null && request.getParameter("Lv5_LimitReplyCnt") != "" ? Integer.parseInt(request.getParameter("Lv5_LimitReplyCnt")) : 0;

			String lv6_LimitLvMtPeriod = request.getParameter("Lv6_LimitLvMtPeriod") != null && request.getParameter("Lv6_LimitLvMtPeriod") != "" ? request.getParameter("Lv6_LimitLvMtPeriod") : "0";
			int lv6_LimitQueRegistCnt = request.getParameter("Lv6_LimitQueRegistCnt") != null && request.getParameter("Lv6_LimitQueRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv6_LimitQueRegistCnt")) : 0;
			int lv6_LimitAnsRegistCnt = request.getParameter("Lv6_LimitAnsRegistCnt") != null && request.getParameter("Lv6_LimitAnsRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv6_LimitAnsRegistCnt")) : 0;
			int lv6_LimitAnsChoiceCnt = request.getParameter("Lv6_LimitAnsChoiceCnt") != null && request.getParameter("Lv6_LimitAnsChoiceCnt") != "" ? Integer.parseInt(request.getParameter("Lv6_LimitAnsChoiceCnt")) : 0;
			int lv6_LimitAnsEstiCnt = request.getParameter("Lv6_LimitAnsEstiCnt") != null && request.getParameter("Lv6_LimitAnsEstiCnt") != "" ? Integer.parseInt(request.getParameter("Lv6_LimitAnsEstiCnt")) : 0;
			int lv6_LimitAnsEstiPoint = request.getParameter("Lv6_LimitAnsEstiPoint") != null && request.getParameter("Lv6_LimitAnsEstiPoint") != "" ? Integer.parseInt(request.getParameter("Lv6_LimitAnsEstiPoint")) : 0;
			int lv6_LimitChuMemCnt = request.getParameter("Lv6_LimitChuMemCnt") != null && request.getParameter("Lv6_LimitChuMemCnt") != "" ? Integer.parseInt(request.getParameter("Lv6_LimitChuMemCnt")) : 0;
			int lv6_LimitReplyCnt = request.getParameter("Lv6_LimitReplyCnt") != null && request.getParameter("Lv6_LimitReplyCnt") != "" ? Integer.parseInt(request.getParameter("Lv6_LimitReplyCnt")) : 0;

			String lv7_LimitLvMtPeriod = request.getParameter("Lv7_LimitLvMtPeriod") != null && request.getParameter("Lv7_LimitLvMtPeriod") != "" ? request.getParameter("Lv7_LimitLvMtPeriod") : "0";
			int lv7_LimitQueRegistCnt = request.getParameter("Lv7_LimitQueRegistCnt") != null && request.getParameter("Lv7_LimitQueRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv7_LimitQueRegistCnt")) : 0;
			int lv7_LimitAnsRegistCnt = request.getParameter("Lv7_LimitAnsRegistCnt") != null && request.getParameter("Lv7_LimitAnsRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv7_LimitAnsRegistCnt")) : 0;
			int lv7_LimitAnsChoiceCnt = request.getParameter("Lv7_LimitAnsChoiceCnt") != null && request.getParameter("Lv7_LimitAnsChoiceCnt") != "" ? Integer.parseInt(request.getParameter("Lv7_LimitAnsChoiceCnt")) : 0;
			int lv7_LimitAnsEstiCnt = request.getParameter("Lv7_LimitAnsEstiCnt") != null && request.getParameter("Lv7_LimitAnsEstiCnt") != "" ? Integer.parseInt(request.getParameter("Lv7_LimitAnsEstiCnt")) : 0;
			int lv7_LimitAnsEstiPoint = request.getParameter("Lv7_LimitAnsEstiPoint") != null && request.getParameter("Lv7_LimitAnsEstiPoint") != "" ? Integer.parseInt(request.getParameter("Lv7_LimitAnsEstiPoint")) : 0;
			int lv7_LimitChuMemCnt = request.getParameter("Lv7_LimitChuMemCnt") != null && request.getParameter("Lv7_LimitChuMemCnt") != "" ? Integer.parseInt(request.getParameter("Lv7_LimitChuMemCnt")) : 0;
			int lv7_LimitReplyCnt = request.getParameter("Lv7_LimitReplyCnt") != null && request.getParameter("Lv7_LimitReplyCnt") != "" ? Integer.parseInt(request.getParameter("Lv7_LimitReplyCnt")) : 0;

			String lv8_LimitLvMtPeriod = request.getParameter("Lv8_LimitLvMtPeriod") != null && request.getParameter("Lv8_LimitLvMtPeriod") != "" ? request.getParameter("Lv8_LimitLvMtPeriod") : "0";
			int lv8_LimitQueRegistCnt = request.getParameter("Lv8_LimitQueRegistCnt") != null && request.getParameter("Lv8_LimitQueRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv8_LimitQueRegistCnt")) : 0;
			int lv8_LimitAnsRegistCnt = request.getParameter("Lv8_LimitAnsRegistCnt") != null && request.getParameter("Lv8_LimitAnsRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv8_LimitAnsRegistCnt")) : 0;
			int lv8_LimitAnsChoiceCnt = request.getParameter("Lv8_LimitAnsChoiceCnt") != null && request.getParameter("Lv8_LimitAnsChoiceCnt") != "" ? Integer.parseInt(request.getParameter("Lv8_LimitAnsChoiceCnt")) : 0;
			int lv8_LimitAnsEstiCnt = request.getParameter("Lv8_LimitAnsEstiCnt") != null && request.getParameter("Lv8_LimitAnsEstiCnt") != "" ? Integer.parseInt(request.getParameter("Lv8_LimitAnsEstiCnt")) : 0;
			int lv8_LimitAnsEstiPoint = request.getParameter("Lv8_LimitAnsEstiPoint") != null && request.getParameter("Lv8_LimitAnsEstiPoint") != "" ? Integer.parseInt(request.getParameter("Lv8_LimitAnsEstiPoint")) : 0;
			int lv8_LimitChuMemCnt = request.getParameter("Lv8_LimitChuMemCnt") != null && request.getParameter("Lv8_LimitChuMemCnt") != "" ? Integer.parseInt(request.getParameter("Lv8_LimitChuMemCnt")) : 0;
			int lv8_LimitReplyCnt = request.getParameter("Lv8_LimitReplyCnt") != null && request.getParameter("Lv8_LimitReplyCnt") != "" ? Integer.parseInt(request.getParameter("Lv8_LimitReplyCnt")) : 0;

			String lv9_LimitLvMtPeriod = request.getParameter("Lv9_LimitLvMtPeriod") != null && request.getParameter("Lv9_LimitLvMtPeriod") != "" ? request.getParameter("Lv9_LimitLvMtPeriod") : "0";
			int lv9_LimitQueRegistCnt = request.getParameter("Lv9_LimitQueRegistCnt") != null && request.getParameter("Lv9_LimitQueRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv9_LimitQueRegistCnt")) : 0;
			int lv9_LimitAnsRegistCnt = request.getParameter("Lv9_LimitAnsRegistCnt") != null && request.getParameter("Lv9_LimitAnsRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv9_LimitAnsRegistCnt")) : 0;
			int lv9_LimitAnsChoiceCnt = request.getParameter("Lv9_LimitAnsChoiceCnt") != null && request.getParameter("Lv9_LimitAnsChoiceCnt") != "" ? Integer.parseInt(request.getParameter("Lv9_LimitAnsChoiceCnt")) : 0;
			int lv9_LimitAnsEstiCnt = request.getParameter("Lv9_LimitAnsEstiCnt") != null && request.getParameter("Lv9_LimitAnsEstiCnt") != "" ? Integer.parseInt(request.getParameter("Lv9_LimitAnsEstiCnt")) : 0;
			int lv9_LimitAnsEstiPoint = request.getParameter("Lv9_LimitAnsEstiPoint") != null && request.getParameter("Lv9_LimitAnsEstiPoint") != "" ? Integer.parseInt(request.getParameter("Lv9_LimitAnsEstiPoint")) : 0;
			int lv9_LimitChuMemCnt = request.getParameter("Lv9_LimitChuMemCnt") != null && request.getParameter("Lv9_LimitChuMemCnt") != "" ? Integer.parseInt(request.getParameter("Lv9_LimitChuMemCnt")) : 0;
			int lv9_LimitReplyCnt = request.getParameter("Lv9_LimitReplyCnt") != null && request.getParameter("Lv9_LimitReplyCnt") != "" ? Integer.parseInt(request.getParameter("Lv9_LimitReplyCnt")) : 0;

			String lv10_LimitLvMtPeriod = request.getParameter("Lv10_LimitLvMtPeriod") != null && request.getParameter("Lv10_LimitLvMtPeriod") != "" ? request.getParameter("Lv10_LimitLvMtPeriod") : "0";
			int lv10_LimitQueRegistCnt = request.getParameter("Lv10_LimitQueRegistCnt") != null && request.getParameter("Lv10_LimitQueRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv10_LimitQueRegistCnt")) : 0;
			int lv10_LimitAnsRegistCnt = request.getParameter("Lv10_LimitAnsRegistCnt") != null && request.getParameter("Lv10_LimitAnsRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv10_LimitAnsRegistCnt")) : 0;
			int lv10_LimitAnsChoiceCnt = request.getParameter("Lv10_LimitAnsChoiceCnt") != null && request.getParameter("Lv10_LimitAnsChoiceCnt") != "" ? Integer.parseInt(request.getParameter("Lv10_LimitAnsChoiceCnt")) : 0;
			int lv10_LimitAnsEstiCnt = request.getParameter("Lv10_LimitAnsEstiCnt") != null && request.getParameter("Lv10_LimitAnsEstiCnt") != "" ? Integer.parseInt(request.getParameter("Lv10_LimitAnsEstiCnt")) : 0;
			int lv10_LimitAnsEstiPoint = request.getParameter("Lv10_LimitAnsEstiPoint") != null && request.getParameter("Lv10_LimitAnsEstiPoint") != "" ? Integer.parseInt(request.getParameter("Lv10_LimitAnsEstiPoint")) : 0;
			int lv10_LimitChuMemCnt = request.getParameter("Lv10_LimitChuMemCnt") != null && request.getParameter("Lv10_LimitChuMemCnt") != "" ? Integer.parseInt(request.getParameter("Lv10_LimitChuMemCnt")) : 0;
			int lv10_LimitReplyCnt = request.getParameter("Lv10_LimitReplyCnt") != null && request.getParameter("Lv10_LimitReplyCnt") != "" ? Integer.parseInt(request.getParameter("Lv10_LimitReplyCnt")) : 0;

			String lv11_LimitLvMtPeriod= request.getParameter("Lv11_LimitLvMtPeriod") != null && request.getParameter("Lv11_LimitLvMtPeriod") != "" && request.getParameter("Lv11_LimitLvMtPeriod") != "" ? request.getParameter("Lv11_LimitLvMtPeriod") : "0";
			int lv11_LimitQueRegistCnt = request.getParameter("Lv11_LimitQueRegistCnt") != null && request.getParameter("Lv11_LimitQueRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv11_LimitQueRegistCnt")) : 0;
			int lv11_LimitAnsRegistCnt = request.getParameter("Lv11_LimitAnsRegistCnt") != null && request.getParameter("Lv11_LimitAnsRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv11_LimitAnsRegistCnt")) : 0;
			int lv11_LimitAnsChoiceCnt = request.getParameter("Lv11_LimitAnsChoiceCnt") != null && request.getParameter("Lv11_LimitAnsChoiceCnt") != "" ? Integer.parseInt(request.getParameter("Lv11_LimitAnsChoiceCnt")) : 0;
			int lv11_LimitAnsEstiCnt = request.getParameter("Lv11_LimitAnsEstiCnt") != null && request.getParameter("Lv11_LimitAnsEstiCnt") != "" ? Integer.parseInt(request.getParameter("Lv11_LimitAnsEstiCnt")) : 0;
			int lv11_LimitAnsEstiPoint = request.getParameter("Lv11_LimitAnsEstiPoint") != null && request.getParameter("Lv11_LimitAnsEstiPoint") != "" ? Integer.parseInt(request.getParameter("Lv11_LimitAnsEstiPoint")) : 0;
			int lv11_LimitChuMemCnt = request.getParameter("Lv11_LimitChuMemCnt") != null && request.getParameter("Lv11_LimitChuMemCnt") != "" ? Integer.parseInt(request.getParameter("Lv11_LimitChuMemCnt")) : 0;
			int lv11_LimitReplyCnt = request.getParameter("Lv11_LimitReplyCnt") != null && request.getParameter("Lv11_LimitReplyCnt") != "" ? Integer.parseInt(request.getParameter("Lv11_LimitReplyCnt")) : 0;

			//환전 신청 기준 [추가(2018.03.13, 2018.03.08): 김현구]
			BigDecimal lv2_ExchangeBaseDeductAlmoney = request.getParameter("Lv2_ExchangeBaseDeductAlmoney") != null && request.getParameter("Lv2_ExchangeBaseDeductAlmoney") != "" ? new BigDecimal(request.getParameter("Lv2_ExchangeBaseDeductAlmoney")) : BigDecimal.ZERO;
			BigDecimal lv2_ExchangeLimitAlmoney = request.getParameter("Lv2_ExchangeLimitAlmoney") != null && request.getParameter("Lv2_ExchangeLimitAlmoney") != "" ? new BigDecimal(request.getParameter("Lv2_ExchangeLimitAlmoney")) : BigDecimal.ZERO;
			BigDecimal lv2_ExchangeBaseUnitAlmoney = request.getParameter("Lv2_ExchangeBaseUnitAlmoney") != null && request.getParameter("Lv2_ExchangeBaseUnitAlmoney") != "" ? new BigDecimal(request.getParameter("Lv2_ExchangeBaseUnitAlmoney")) : BigDecimal.ZERO;
			BigDecimal lv2_ExchangeRealMoneyDeductRate = request.getParameter("Lv2_ExchangeRealMoneyDeductRate") != null && request.getParameter("Lv2_ExchangeRealMoneyDeductRate") != "" ? new BigDecimal(request.getParameter("Lv2_ExchangeRealMoneyDeductRate")) : BigDecimal.ZERO;

			BigDecimal lv3_ExchangeBaseDeductAlmoney = request.getParameter("Lv3_ExchangeBaseDeductAlmoney") != null && request.getParameter("Lv3_ExchangeBaseDeductAlmoney") != "" ? new BigDecimal(request.getParameter("Lv3_ExchangeBaseDeductAlmoney")) : BigDecimal.ZERO;
			BigDecimal lv3_ExchangeLimitAlmoney = request.getParameter("Lv3_ExchangeLimitAlmoney") != null && request.getParameter("Lv3_ExchangeLimitAlmoney") != "" ? new BigDecimal(request.getParameter("Lv3_ExchangeLimitAlmoney")) : BigDecimal.ZERO;
			BigDecimal lv3_ExchangeBaseUnitAlmoney = request.getParameter("Lv3_ExchangeBaseUnitAlmoney") != null && request.getParameter("Lv3_ExchangeBaseUnitAlmoney") != "" ? new BigDecimal(request.getParameter("Lv3_ExchangeBaseUnitAlmoney")) : BigDecimal.ZERO;
			BigDecimal lv3_ExchangeRealMoneyDeductRate = request.getParameter("Lv3_ExchangeRealMoneyDeductRate") != null && request.getParameter("Lv3_ExchangeRealMoneyDeductRate") != "" ? new BigDecimal(request.getParameter("Lv3_ExchangeRealMoneyDeductRate")) : BigDecimal.ZERO;

			BigDecimal lv4_ExchangeBaseDeductAlmoney = request.getParameter("Lv4_ExchangeBaseDeductAlmoney") != null && request.getParameter("Lv4_ExchangeBaseDeductAlmoney") != "" ? new BigDecimal(request.getParameter("Lv4_ExchangeBaseDeductAlmoney")) : BigDecimal.ZERO;
			BigDecimal lv4_ExchangeLimitAlmoney = request.getParameter("Lv4_ExchangeLimitAlmoney") != null && request.getParameter("Lv4_ExchangeLimitAlmoney") != "" ? new BigDecimal(request.getParameter("Lv4_ExchangeLimitAlmoney")) : BigDecimal.ZERO;
			BigDecimal lv4_ExchangeBaseUnitAlmoney = request.getParameter("Lv4_ExchangeBaseUnitAlmoney") != null && request.getParameter("Lv4_ExchangeBaseUnitAlmoney") != "" ? new BigDecimal(request.getParameter("Lv4_ExchangeBaseUnitAlmoney")) : BigDecimal.ZERO;
			BigDecimal lv4_ExchangeRealMoneyDeductRate = request.getParameter("Lv4_ExchangeRealMoneyDeductRate") != null && request.getParameter("Lv4_ExchangeRealMoneyDeductRate") != "" ? new BigDecimal(request.getParameter("Lv4_ExchangeRealMoneyDeductRate")) : BigDecimal.ZERO;

			BigDecimal lv5_ExchangeBaseDeductAlmoney = request.getParameter("Lv5_ExchangeBaseDeductAlmoney") != null && request.getParameter("Lv5_ExchangeBaseDeductAlmoney") != "" ? new BigDecimal(request.getParameter("Lv5_ExchangeBaseDeductAlmoney")) : BigDecimal.ZERO;
			BigDecimal lv5_ExchangeLimitAlmoney = request.getParameter("Lv5_ExchangeLimitAlmoney") != null && request.getParameter("Lv5_ExchangeLimitAlmoney") != "" ? new BigDecimal(request.getParameter("Lv5_ExchangeLimitAlmoney")) : BigDecimal.ZERO;
			BigDecimal lv5_ExchangeBaseUnitAlmoney = request.getParameter("Lv5_ExchangeBaseUnitAlmoney") != null && request.getParameter("Lv5_ExchangeBaseUnitAlmoney") != "" ? new BigDecimal(request.getParameter("Lv5_ExchangeBaseUnitAlmoney")) : BigDecimal.ZERO;
			BigDecimal lv5_ExchangeRealMoneyDeductRate = request.getParameter("Lv5_ExchangeRealMoneyDeductRate") != null && request.getParameter("Lv5_ExchangeRealMoneyDeductRate") != "" ? new BigDecimal(request.getParameter("Lv5_ExchangeRealMoneyDeductRate")) : BigDecimal.ZERO;

			BigDecimal lv6_ExchangeBaseDeductAlmoney = request.getParameter("Lv6_ExchangeBaseDeductAlmoney") != null && request.getParameter("Lv6_ExchangeBaseDeductAlmoney") != "" ? new BigDecimal(request.getParameter("Lv6_ExchangeBaseDeductAlmoney")) : BigDecimal.ZERO;
			BigDecimal lv6_ExchangeLimitAlmoney = request.getParameter("Lv6_ExchangeLimitAlmoney") != null && request.getParameter("Lv6_ExchangeLimitAlmoney") != "" ? new BigDecimal(request.getParameter("Lv6_ExchangeLimitAlmoney")) : BigDecimal.ZERO;
			BigDecimal lv6_ExchangeBaseUnitAlmoney = request.getParameter("Lv6_ExchangeBaseUnitAlmoney") != null && request.getParameter("Lv6_ExchangeBaseUnitAlmoney") != "" ? new BigDecimal(request.getParameter("Lv6_ExchangeBaseUnitAlmoney")) : BigDecimal.ZERO;
			float lv6_ExchangeRealMoneyDeductRate = request.getParameter("Lv6_ExchangeRealMoneyDeductRate") != null && request.getParameter("Lv6_ExchangeRealMoneyDeductRate") != "" ? Float.parseFloat(request.getParameter("Lv6_ExchangeRealMoneyDeductRate")) : 0.0f;

			BigDecimal lv7_ExchangeBaseDeductAlmoney = request.getParameter("Lv7_ExchangeBaseDeductAlmoney") != null && request.getParameter("Lv7_ExchangeBaseDeductAlmoney") != "" ? new BigDecimal(request.getParameter("Lv7_ExchangeBaseDeductAlmoney")) : BigDecimal.ZERO;
			BigDecimal lv7_ExchangeLimitAlmoney = request.getParameter("Lv7_ExchangeLimitAlmoney") != null && request.getParameter("Lv7_ExchangeLimitAlmoney") != "" ? new BigDecimal(request.getParameter("Lv7_ExchangeLimitAlmoney")) : BigDecimal.ZERO;
			BigDecimal lv7_ExchangeBaseUnitAlmoney = request.getParameter("Lv7_ExchangeBaseUnitAlmoney") != null && request.getParameter("Lv7_ExchangeBaseUnitAlmoney") != "" ? new BigDecimal(request.getParameter("Lv7_ExchangeBaseUnitAlmoney")) : BigDecimal.ZERO;
			float lv7_ExchangeRealMoneyDeductRate = request.getParameter("Lv7_ExchangeRealMoneyDeductRate") != null && request.getParameter("Lv7_ExchangeRealMoneyDeductRate") != "" ? Float.parseFloat(request.getParameter("Lv7_ExchangeRealMoneyDeductRate")) : 0.0f;

			BigDecimal lv8_ExchangeBaseDeductAlmoney = request.getParameter("Lv8_ExchangeBaseDeductAlmoney") != null && request.getParameter("Lv8_ExchangeBaseDeductAlmoney") != "" ? new BigDecimal(request.getParameter("Lv8_ExchangeBaseDeductAlmoney")) : BigDecimal.ZERO;
			BigDecimal lv8_ExchangeLimitAlmoney = request.getParameter("Lv8_ExchangeLimitAlmoney") != null && request.getParameter("Lv8_ExchangeLimitAlmoney") != "" ? new BigDecimal(request.getParameter("Lv8_ExchangeLimitAlmoney")) : BigDecimal.ZERO;
			BigDecimal lv8_ExchangeBaseUnitAlmoney = request.getParameter("Lv8_ExchangeBaseUnitAlmoney") != null && request.getParameter("Lv8_ExchangeBaseUnitAlmoney") != "" ? new BigDecimal(request.getParameter("Lv8_ExchangeBaseUnitAlmoney")) : BigDecimal.ZERO;
			float lv8_ExchangeRealMoneyDeductRate = request.getParameter("Lv8_ExchangeRealMoneyDeductRate") != null && request.getParameter("Lv8_ExchangeRealMoneyDeductRate") != "" ? Float.parseFloat(request.getParameter("Lv8_ExchangeRealMoneyDeductRate")) : 0.0f;

			BigDecimal lv9_ExchangeBaseDeductAlmoney = request.getParameter("Lv9_ExchangeBaseDeductAlmoney") != null && request.getParameter("Lv9_ExchangeBaseDeductAlmoney") != "" ? new BigDecimal(request.getParameter("Lv9_ExchangeBaseDeductAlmoney")) : BigDecimal.ZERO;
			BigDecimal lv9_ExchangeLimitAlmoney = request.getParameter("Lv9_ExchangeLimitAlmoney") != null && request.getParameter("Lv9_ExchangeLimitAlmoney") != "" ? new BigDecimal(request.getParameter("Lv9_ExchangeLimitAlmoney")) : BigDecimal.ZERO;
			BigDecimal lv9_ExchangeBaseUnitAlmoney = request.getParameter("Lv9_ExchangeBaseUnitAlmoney") != null && request.getParameter("Lv9_ExchangeBaseUnitAlmoney") != "" ? new BigDecimal(request.getParameter("Lv9_ExchangeBaseUnitAlmoney")) : BigDecimal.ZERO;
			float lv9_ExchangeRealMoneyDeductRate = request.getParameter("Lv9_ExchangeRealMoneyDeductRate") != null && request.getParameter("Lv9_ExchangeRealMoneyDeductRate") != "" ? Float.parseFloat(request.getParameter("Lv9_ExchangeRealMoneyDeductRate")) : 0.0f;

			BigDecimal lv10_ExchangeBaseDeductAlmoney = request.getParameter("Lv10_ExchangeBaseDeductAlmoney") != null && request.getParameter("Lv10_ExchangeBaseDeductAlmoney") != "" ? new BigDecimal(request.getParameter("Lv10_ExchangeBaseDeductAlmoney")) : BigDecimal.ZERO;
			BigDecimal lv10_ExchangeLimitAlmoney = request.getParameter("Lv10_ExchangeLimitAlmoney") != null && request.getParameter("Lv10_ExchangeLimitAlmoney") != "" ? new BigDecimal(request.getParameter("Lv10_ExchangeLimitAlmoney")) : BigDecimal.ZERO;
			BigDecimal lv10_ExchangeBaseUnitAlmoney = request.getParameter("Lv10_ExchangeBaseUnitAlmoney") != null && request.getParameter("Lv10_ExchangeBaseUnitAlmoney") != "" ? new BigDecimal(request.getParameter("Lv10_ExchangeBaseUnitAlmoney")) : BigDecimal.ZERO;
			float lv10_ExchangeRealMoneyDeductRate = request.getParameter("Lv10_ExchangeRealMoneyDeductRate") != null && request.getParameter("Lv10_ExchangeRealMoneyDeductRate") != "" ? Float.parseFloat(request.getParameter("Lv10_ExchangeRealMoneyDeductRate")) : 0.0f;

			BigDecimal lv11_ExchangeBaseDeductAlmoney = request.getParameter("Lv11_ExchangeBaseDeductAlmoney") != null && request.getParameter("Lv11_ExchangeBaseDeductAlmoney") != "" ? new BigDecimal(request.getParameter("Lv11_ExchangeBaseDeductAlmoney")) : BigDecimal.ZERO;
			BigDecimal lv11_ExchangeLimitAlmoney = request.getParameter("Lv11_ExchangeLimitAlmoney") != null && request.getParameter("Lv11_ExchangeLimitAlmoney") != "" ? new BigDecimal(request.getParameter("Lv11_ExchangeLimitAlmoney")) : BigDecimal.ZERO;
			BigDecimal lv11_ExchangeBaseUnitAlmoney = request.getParameter("Lv11_ExchangeBaseUnitAlmoney") != null && request.getParameter("Lv11_ExchangeBaseUnitAlmoney") != "" ? new BigDecimal(request.getParameter("Lv11_ExchangeBaseUnitAlmoney")) : BigDecimal.ZERO;
			float lv11_ExchangeRealMoneyDeductRate = request.getParameter("Lv11_ExchangeRealMoneyDeductRate") != null && request.getParameter("Lv11_ExchangeRealMoneyDeductRate") != "" ? Float.parseFloat(request.getParameter("Lv11_ExchangeRealMoneyDeductRate")) : 0.0f;


			//회원 레벨별 환전 신청 체크 조건 [추가(2018.03.14): 김현구]
			int lv2_ExchangeMtPeriod = request.getParameter("Lv2_ExchangeMtPeriod") != null && request.getParameter("Lv2_ExchangeMtPeriod") != "" ? Integer.parseInt(request.getParameter("Lv2_ExchangeMtPeriod")) : 0;
			int lv2_ExchangeQueRegistCnt = request.getParameter("Lv2_ExchangeQueRegistCnt") != null && request.getParameter("Lv2_ExchangeQueRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv2_ExchangeQueRegistCnt")) : 0;
			int lv2_ExchangeAnsRegistCnt = request.getParameter("Lv2_ExchangeAnsRegistCnt") != null && request.getParameter("Lv2_ExchangeAnsRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv2_ExchangeAnsRegistCnt")) : 0;
			int lv2_ExchangeEstiCnt = request.getParameter("Lv2_ExchangeEstiCnt") != null && request.getParameter("Lv2_ExchangeEstiCnt") != "" ? Integer.parseInt(request.getParameter("Lv2_ExchangeEstiCnt")) : 0;
			int lv2_ExchangeEstiPoint = request.getParameter("Lv2_ExchangeEstiPoint") != null && request.getParameter("Lv2_ExchangeEstiPoint") != "" ? Integer.parseInt(request.getParameter("Lv2_ExchangeEstiPoint")) : 0;

			int lv3_ExchangeMtPeriod = request.getParameter("Lv3_ExchangeMtPeriod") != null && request.getParameter("Lv3_ExchangeMtPeriod") != "" ? Integer.parseInt(request.getParameter("Lv3_ExchangeMtPeriod")) : 0;
			int lv3_ExchangeQueRegistCnt = request.getParameter("Lv3_ExchangeQueRegistCnt") != null && request.getParameter("Lv3_ExchangeQueRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv3_ExchangeQueRegistCnt")) : 0;
			int lv3_ExchangeAnsRegistCnt = request.getParameter("Lv3_ExchangeAnsRegistCnt") != null && request.getParameter("Lv3_ExchangeAnsRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv3_ExchangeAnsRegistCnt")) : 0;
			int lv3_ExchangeEstiCnt = request.getParameter("Lv3_ExchangeEstiCnt") != null && request.getParameter("Lv3_ExchangeEstiCnt") != "" ? Integer.parseInt(request.getParameter("Lv3_ExchangeEstiCnt")) : 0;
			int lv3_ExchangeEstiPoint = request.getParameter("Lv3_ExchangeEstiPoint") != null && request.getParameter("Lv3_ExchangeEstiPoint") != "" ? Integer.parseInt(request.getParameter("Lv3_ExchangeEstiPoint")) : 0;

			int lv4_ExchangeMtPeriod = request.getParameter("Lv4_ExchangeMtPeriod") != null && request.getParameter("Lv4_ExchangeMtPeriod") != "" ? Integer.parseInt(request.getParameter("Lv4_ExchangeMtPeriod")) : 0;
			int lv4_ExchangeQueRegistCnt = request.getParameter("Lv4_ExchangeQueRegistCnt") != null && request.getParameter("Lv4_ExchangeQueRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv4_ExchangeQueRegistCnt")) : 0;
			int lv4_ExchangeAnsRegistCnt = request.getParameter("Lv4_ExchangeAnsRegistCnt") != null && request.getParameter("Lv4_ExchangeAnsRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv4_ExchangeAnsRegistCnt")) : 0;
			int lv4_ExchangeEstiCnt = request.getParameter("Lv4_ExchangeEstiCnt") != null && request.getParameter("Lv4_ExchangeEstiCnt") != "" ? Integer.parseInt(request.getParameter("Lv4_ExchangeEstiCnt")) : 0;
			int lv4_ExchangeEstiPoint = request.getParameter("Lv4_ExchangeEstiPoint") != null && request.getParameter("Lv4_ExchangeEstiPoint") != "" ? Integer.parseInt(request.getParameter("Lv4_ExchangeEstiPoint")) : 0;

			int lv5_ExchangeMtPeriod = request.getParameter("Lv5_ExchangeMtPeriod") != null && request.getParameter("Lv5_ExchangeMtPeriod") != "" ? Integer.parseInt(request.getParameter("Lv5_ExchangeMtPeriod")) : 0;
			int lv5_ExchangeQueRegistCnt = request.getParameter("Lv5_ExchangeQueRegistCnt") != null && request.getParameter("Lv5_ExchangeQueRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv5_ExchangeQueRegistCnt")) : 0;
			int lv5_ExchangeAnsRegistCnt = request.getParameter("Lv5_ExchangeAnsRegistCnt") != null && request.getParameter("Lv5_ExchangeAnsRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv5_ExchangeAnsRegistCnt")) : 0;
			int lv5_ExchangeEstiCnt = request.getParameter("Lv5_ExchangeEstiCnt") != null && request.getParameter("Lv5_ExchangeEstiCnt") != "" ? Integer.parseInt(request.getParameter("Lv5_ExchangeEstiCnt")) : 0;
			int lv5_ExchangeEstiPoint = request.getParameter("Lv5_ExchangeEstiPoint") != null && request.getParameter("Lv5_ExchangeEstiPoint") != "" ? Integer.parseInt(request.getParameter("Lv5_ExchangeEstiPoint")) : 0;

			String lv6_ExchangeMtPeriod = request.getParameter("Lv6_ExchangeMtPeriod") != null && request.getParameter("Lv6_ExchangeMtPeriod") != "" ? request.getParameter("Lv6_ExchangeMtPeriod") : "0";
			int lv6_ExchangeQueRegistCnt = request.getParameter("Lv6_ExchangeQueRegistCnt") != null && request.getParameter("Lv6_ExchangeQueRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv6_ExchangeQueRegistCnt")) : 0;
			int lv6_ExchangeAnsRegistCnt = request.getParameter("Lv6_ExchangeAnsRegistCnt") != null && request.getParameter("Lv6_ExchangeAnsRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv6_ExchangeAnsRegistCnt")) : 0;
			int lv6_ExchangeEstiCnt = request.getParameter("Lv6_ExchangeEstiCnt") != null && request.getParameter("Lv6_ExchangeEstiCnt") != "" ? Integer.parseInt(request.getParameter("Lv6_ExchangeEstiCnt")) : 0;
			int lv6_ExchangeEstiPoint = request.getParameter("Lv6_ExchangeEstiPoint") != null && request.getParameter("Lv6_ExchangeEstiPoint") != "" ? Integer.parseInt(request.getParameter("Lv6_ExchangeEstiPoint")) : 0;


			String lv7_ExchangeMtPeriod = request.getParameter("Lv7_ExchangeMtPeriod") != null && request.getParameter("Lv7_ExchangeMtPeriod") != "" ? request.getParameter("Lv7_ExchangeMtPeriod") : "0";
			int lv7_ExchangeQueRegistCnt = request.getParameter("Lv7_ExchangeQueRegistCnt") != null && request.getParameter("Lv7_ExchangeQueRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv7_ExchangeQueRegistCnt")) : 0;
			int lv7_ExchangeAnsRegistCnt = request.getParameter("Lv7_ExchangeAnsRegistCnt") != null && request.getParameter("Lv7_ExchangeAnsRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv7_ExchangeAnsRegistCnt")) : 0;
			int lv7_ExchangeEstiCnt = request.getParameter("Lv7_ExchangeEstiCnt") != null && request.getParameter("Lv7_ExchangeEstiCnt") != "" ? Integer.parseInt(request.getParameter("Lv7_ExchangeEstiCnt")) : 0;
			int lv7_ExchangeEstiPoint = request.getParameter("Lv7_ExchangeEstiPoint") != null && request.getParameter("Lv7_ExchangeEstiPoint") != "" ? Integer.parseInt(request.getParameter("Lv7_ExchangeEstiPoint")) : 0;

			String lv8_ExchangeMtPeriod = request.getParameter("Lv8_ExchangeMtPeriod") != null && request.getParameter("Lv8_ExchangeMtPeriod") != "" ? request.getParameter("Lv8_ExchangeMtPeriod") : "0";
			int lv8_ExchangeQueRegistCnt = request.getParameter("Lv8_ExchangeQueRegistCnt") != null && request.getParameter("Lv8_ExchangeQueRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv8_ExchangeQueRegistCnt")) : 0;
			int lv8_ExchangeAnsRegistCnt = request.getParameter("Lv8_ExchangeAnsRegistCnt") != null && request.getParameter("Lv8_ExchangeAnsRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv8_ExchangeAnsRegistCnt")) : 0;
			int lv8_ExchangeEstiCnt = request.getParameter("Lv8_ExchangeEstiCnt") != null && request.getParameter("Lv8_ExchangeEstiCnt") != "" ? Integer.parseInt(request.getParameter("Lv8_ExchangeEstiCnt")) : 0;
			int lv8_ExchangeEstiPoint = request.getParameter("Lv8_ExchangeEstiPoint") != null && request.getParameter("Lv8_ExchangeEstiPoint") != "" ? Integer.parseInt(request.getParameter("Lv8_ExchangeEstiPoint")) : 0;

			String lv9_ExchangeMtPeriod = request.getParameter("Lv9_ExchangeMtPeriod") != null && request.getParameter("Lv9_ExchangeMtPeriod") != "" ? request.getParameter("Lv9_ExchangeMtPeriod") : "0";
			int lv9_ExchangeQueRegistCnt = request.getParameter("Lv9_ExchangeQueRegistCnt") != null && request.getParameter("Lv9_ExchangeQueRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv9_ExchangeQueRegistCnt")) : 0;
			int lv9_ExchangeAnsRegistCnt = request.getParameter("Lv9_ExchangeAnsRegistCnt") != null && request.getParameter("Lv9_ExchangeAnsRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv9_ExchangeAnsRegistCnt")) : 0;
			int lv9_ExchangeEstiCnt = request.getParameter("Lv9_ExchangeEstiCnt") != null && request.getParameter("Lv9_ExchangeEstiCnt") != "" ? Integer.parseInt(request.getParameter("Lv9_ExchangeEstiCnt")) : 0;
			int lv9_ExchangeEstiPoint = request.getParameter("Lv9_ExchangeEstiPoint") != null && request.getParameter("Lv9_ExchangeEstiPoint") != "" ? Integer.parseInt(request.getParameter("Lv9_ExchangeEstiPoint")) : 0;

			String lv10_ExchangeMtPeriod = request.getParameter("Lv10_ExchangeMtPeriod") != null && request.getParameter("Lv10_ExchangeMtPeriod") != "" ? request.getParameter("Lv10_ExchangeMtPeriod") : "0";
			int lv10_ExchangeQueRegistCnt = request.getParameter("Lv10_ExchangeQueRegistCnt") != null && request.getParameter("Lv10_ExchangeQueRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv10_ExchangeQueRegistCnt")) : 0;
			int lv10_ExchangeAnsRegistCnt = request.getParameter("Lv10_ExchangeAnsRegistCnt") != null && request.getParameter("Lv10_ExchangeAnsRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv10_ExchangeAnsRegistCnt")) : 0;
			int lv10_ExchangeEstiCnt = request.getParameter("Lv10_ExchangeEstiCnt") != null && request.getParameter("Lv10_ExchangeEstiCnt") != "" ? Integer.parseInt(request.getParameter("Lv10_ExchangeEstiCnt")) : 0;
			int lv10_ExchangeEstiPoint = request.getParameter("Lv10_ExchangeEstiPoint") != null && request.getParameter("Lv10_ExchangeEstiPoint") != "" ? Integer.parseInt(request.getParameter("Lv10_ExchangeEstiPoint")) : 0;

			String lv11_ExchangeMtPeriod = request.getParameter("Lv11_ExchangeMtPeriod") != null && request.getParameter("Lv11_ExchangeMtPeriod") != "" ? request.getParameter("Lv11_ExchangeMtPeriod") : "0";
			int lv11_ExchangeQueRegistCnt = request.getParameter("Lv11_ExchangeQueRegistCnt") != null && request.getParameter("Lv11_ExchangeQueRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv11_ExchangeQueRegistCnt")) : 0;
			int lv11_ExchangeAnsRegistCnt = request.getParameter("Lv11_ExchangeAnsRegistCnt") != null && request.getParameter("Lv11_ExchangeAnsRegistCnt") != "" ? Integer.parseInt(request.getParameter("Lv11_ExchangeAnsRegistCnt")) : 0;
			int lv11_ExchangeEstiCnt = request.getParameter("Lv11_ExchangeEstiCnt") != null && request.getParameter("Lv11_ExchangeEstiCnt") != "" ? Integer.parseInt(request.getParameter("Lv11_ExchangeEstiCnt")) : 0;
			int lv11_ExchangeEstiPoint = request.getParameter("Lv11_ExchangeEstiPoint") != null && request.getParameter("Lv11_ExchangeEstiPoint") != "" ? Integer.parseInt(request.getParameter("Lv11_ExchangeEstiPoint")) : 0;

			//질문 등록시 사례알머니 기준 [추가(2018.03.13): 김현구]
			String lv1_QueRegAlmoney = request.getParameter("Lv1_QueRegAlmoney") != null && request.getParameter("Lv1_QueRegAlmoney") != "" ? request.getParameter("Lv1_QueRegAlmoney") : "";
			String lv2_QueRegAlmoney = request.getParameter("Lv2_QueRegAlmoney") != null && request.getParameter("Lv2_QueRegAlmoney") != "" ? request.getParameter("Lv2_QueRegAlmoney") : "";
			String lv3_QueRegAlmoney = request.getParameter("Lv3_QueRegAlmoney") != null && request.getParameter("Lv3_QueRegAlmoney") != "" ? request.getParameter("Lv3_QueRegAlmoney") : "";
			String lv4_QueRegAlmoney = request.getParameter("Lv4_QueRegAlmoney") != null && request.getParameter("Lv4_QueRegAlmoney") != "" ? request.getParameter("Lv4_QueRegAlmoney") : "";
			String lv5_QueRegAlmoney = request.getParameter("Lv5_QueRegAlmoney") != null && request.getParameter("Lv5_QueRegAlmoney") != "" ? request.getParameter("Lv5_QueRegAlmoney") : "";
			String lv6_QueRegAlmoney = request.getParameter("Lv6_QueRegAlmoney") != null && request.getParameter("Lv6_QueRegAlmoney") != "" ? request.getParameter("Lv6_QueRegAlmoney") : "";
			String lv7_QueRegAlmoney = request.getParameter("Lv7_QueRegAlmoney") != null && request.getParameter("Lv7_QueRegAlmoney") != "" ? request.getParameter("Lv7_QueRegAlmoney") : "";
			String lv8_QueRegAlmoney = request.getParameter("Lv8_QueRegAlmoney") != null && request.getParameter("Lv8_QueRegAlmoney") != "" ? request.getParameter("Lv8_QueRegAlmoney") : "";
			String lv9_QueRegAlmoney = request.getParameter("Lv9_QueRegAlmoney") != null && request.getParameter("Lv9_QueRegAlmoney") != "" ? request.getParameter("Lv9_QueRegAlmoney") : "";
			String lv10_QueRegAlmoney= request.getParameter("Lv10_QueRegAlmoney") != null && request.getParameter("Lv10_QueRegAlmoney") != "" ? request.getParameter("Lv10_QueRegAlmoney") : "";
			String lv11_QueRegAlmoney = request.getParameter("Lv11_QueRegAlmoney") != null && request.getParameter("Lv11_QueRegAlmoney") != "" ? request.getParameter("Lv11_QueRegAlmoney") : "";



			//[환경설정] DB에 UPDATE 실행
			HashMap<String, Object> param = new HashMap<String, Object>();

			param.put("almoneyJoin", almoneyJoin);

			param.put("almoneyExchange", almoneyExchange);
			param.put("questionMin", questionMin);
			param.put("questionMax", questionMax);
			param.put("writeMax", writeMax);
			param.put("fileMax", fileMax);
			param.put("viewMoneySum", viewMoneySum);
			param.put("viewMoneyQ", viewMoneyQ);
			param.put("viewMoneyA", viewMoneyA);
			param.put("nickNotUse", nickNotUse);
			param.put("moneyCompany", moneyCompany);
			param.put("memJoinCertType", memJoinCertType);
			param.put("memJoinSmsLimitCnt", memJoinSmsLimitCnt);
			param.put("memJoinSmsTimeOut", memJoinSmsTimeOut);
			param.put("memPwSmsLimitCnt", memPwSmsLimitCnt);

			param.put("lv1_LimitQueDayRegistCnt", lv1_LimitQueDayRegistCnt);
			param.put("lv1_LimitQueDayDupRegistCnt", lv1_LimitQueDayDupRegistCnt);
			param.put("lv1_LimitQueContinueRegistTime", lv1_LimitQueContinueRegistTime);
			param.put("lv1_LimitAnsDayRegistCnt", lv1_LimitAnsDayRegistCnt);
			param.put("lv1_LimitAnsDayDupRegistCnt", lv1_LimitAnsDayDupRegistCnt);
			param.put("lv1_LimitAnsContinueRegistTime", lv1_LimitAnsContinueRegistTime);
			param.put("lv1_LimitRepDayRegistCnt", lv1_LimitRepDayRegistCnt);
			param.put("lv1_LimitRepDayDupRegistCnt", lv1_LimitRepDayDupRegistCnt);
			param.put("lv1_LimitRepContinueRegistTime", lv1_LimitRepContinueRegistTime);


			param.put("lv2_LimitQueDayRegistCnt", lv2_LimitQueDayRegistCnt);
			param.put("lv2_LimitQueDayDupRegistCnt", lv2_LimitQueDayDupRegistCnt);
			param.put("lv2_LimitQueContinueRegistTime", lv2_LimitQueContinueRegistTime);
			param.put("lv2_LimitAnsDayRegistCnt", lv2_LimitAnsDayRegistCnt);
			param.put("lv2_LimitAnsDayDupRegistCnt", lv2_LimitAnsDayDupRegistCnt);
			param.put("lv2_LimitAnsContinueRegistTime", lv2_LimitAnsContinueRegistTime);
			param.put("lv2_LimitRepDayRegistCnt", lv2_LimitRepDayRegistCnt);
			param.put("lv2_LimitRepDayDupRegistCnt", lv2_LimitRepDayDupRegistCnt);
			param.put("lv2_LimitRepContinueRegistTime", lv2_LimitRepContinueRegistTime);

			param.put("lv3_LimitQueDayRegistCnt", lv3_LimitQueDayRegistCnt);
			param.put("lv3_LimitQueDayDupRegistCnt", lv3_LimitQueDayDupRegistCnt);
			param.put("lv3_LimitQueContinueRegistTime", lv3_LimitQueContinueRegistTime);
			param.put("lv3_LimitAnsDayRegistCnt", lv3_LimitAnsDayRegistCnt);
			param.put("lv3_LimitAnsDayDupRegistCnt", lv3_LimitAnsDayDupRegistCnt);
			param.put("lv3_LimitAnsContinueRegistTime", lv3_LimitAnsContinueRegistTime);
			param.put("lv3_LimitRepDayRegistCnt", lv3_LimitRepDayRegistCnt);
			param.put("lv3_LimitRepDayDupRegistCnt", lv3_LimitRepDayDupRegistCnt);
			param.put("lv3_LimitRepContinueRegistTime", lv3_LimitRepContinueRegistTime);

			param.put("lv4_LimitQueDayRegistCnt", lv4_LimitQueDayRegistCnt);
			param.put("lv4_LimitQueDayDupRegistCnt", lv4_LimitQueDayDupRegistCnt);
			param.put("lv4_LimitQueContinueRegistTime", lv4_LimitQueContinueRegistTime);
			param.put("lv4_LimitAnsDayRegistCnt", lv4_LimitAnsDayRegistCnt);
			param.put("lv4_LimitAnsDayDupRegistCnt", lv4_LimitAnsDayDupRegistCnt);
			param.put("lv4_LimitAnsContinueRegistTime", lv4_LimitAnsContinueRegistTime);
			param.put("lv4_LimitRepDayRegistCnt", lv4_LimitRepDayRegistCnt);
			param.put("lv4_LimitRepDayDupRegistCnt", lv4_LimitRepDayDupRegistCnt);
			param.put("lv4_LimitRepContinueRegistTime", lv4_LimitRepContinueRegistTime);

			param.put("lv5_LimitQueDayRegistCnt", lv5_LimitQueDayRegistCnt);
			param.put("lv5_LimitQueDayDupRegistCnt", lv5_LimitQueDayDupRegistCnt);
			param.put("lv5_LimitQueContinueRegistTime", lv5_LimitQueContinueRegistTime);
			param.put("lv5_LimitAnsDayRegistCnt", lv5_LimitAnsDayRegistCnt);
			param.put("lv5_LimitAnsDayDupRegistCnt", lv5_LimitAnsDayDupRegistCnt);
			param.put("lv5_LimitAnsContinueRegistTime", lv5_LimitAnsContinueRegistTime);
			param.put("lv5_LimitRepDayRegistCnt", lv5_LimitRepDayRegistCnt);
			param.put("lv5_LimitRepDayDupRegistCnt", lv5_LimitRepDayDupRegistCnt);
			param.put("lv5_LimitRepContinueRegistTime", lv5_LimitRepContinueRegistTime);

			param.put("lv6_LimitQueDayRegistCnt", lv6_LimitQueDayRegistCnt);
			param.put("lv6_LimitQueDayDupRegistCnt", lv6_LimitQueDayDupRegistCnt);
			param.put("lv6_LimitQueContinueRegistTime", lv6_LimitQueContinueRegistTime);
			param.put("lv6_LimitAnsDayRegistCnt", lv6_LimitAnsDayRegistCnt);
			param.put("lv6_LimitAnsDayDupRegistCnt", lv6_LimitAnsDayDupRegistCnt);
			param.put("lv6_LimitAnsContinueRegistTime", lv6_LimitAnsContinueRegistTime);
			param.put("lv6_LimitRepDayRegistCnt", lv6_LimitRepDayRegistCnt);
			param.put("lv6_LimitRepDayDupRegistCnt", lv6_LimitRepDayDupRegistCnt);
			param.put("lv6_LimitRepContinueRegistTime", lv6_LimitRepContinueRegistTime);

			param.put("lv7_LimitQueDayRegistCnt", lv7_LimitQueDayRegistCnt);
			param.put("lv7_LimitQueDayDupRegistCnt", lv7_LimitQueDayDupRegistCnt);
			param.put("lv7_LimitQueContinueRegistTime", lv7_LimitQueContinueRegistTime);
			param.put("lv7_LimitAnsDayRegistCnt", lv7_LimitAnsDayRegistCnt);
			param.put("lv7_LimitAnsDayDupRegistCnt", lv7_LimitAnsDayDupRegistCnt);
			param.put("lv7_LimitAnsContinueRegistTime", lv7_LimitAnsContinueRegistTime);
			param.put("lv7_LimitRepDayRegistCnt", lv7_LimitRepDayRegistCnt);
			param.put("lv7_LimitRepDayDupRegistCnt", lv7_LimitRepDayDupRegistCnt);
			param.put("lv7_LimitRepContinueRegistTime", lv7_LimitRepContinueRegistTime);

			param.put("lv8_LimitQueDayRegistCnt", lv8_LimitQueDayRegistCnt);
			param.put("lv8_LimitQueDayDupRegistCnt", lv8_LimitQueDayDupRegistCnt);
			param.put("lv8_LimitQueContinueRegistTime", lv8_LimitQueContinueRegistTime);
			param.put("lv8_LimitAnsDayRegistCnt", lv8_LimitAnsDayRegistCnt);
			param.put("lv8_LimitAnsDayDupRegistCnt", lv8_LimitAnsDayDupRegistCnt);
			param.put("lv8_LimitAnsContinueRegistTime", lv8_LimitAnsContinueRegistTime);
			param.put("lv8_LimitRepDayRegistCnt", lv8_LimitRepDayRegistCnt);
			param.put("lv8_LimitRepDayDupRegistCnt", lv8_LimitRepDayDupRegistCnt);
			param.put("lv8_LimitRepContinueRegistTime", lv8_LimitRepContinueRegistTime);

			param.put("lv9_LimitQueDayRegistCnt", lv9_LimitQueDayRegistCnt);
			param.put("lv9_LimitQueDayDupRegistCnt", lv9_LimitQueDayDupRegistCnt);
			param.put("lv9_LimitQueContinueRegistTime", lv9_LimitQueContinueRegistTime);
			param.put("lv9_LimitAnsDayRegistCnt", lv9_LimitAnsDayRegistCnt);
			param.put("lv9_LimitAnsDayDupRegistCnt", lv9_LimitAnsDayDupRegistCnt);
			param.put("lv9_LimitAnsContinueRegistTime", lv9_LimitAnsContinueRegistTime);
			param.put("lv9_LimitRepDayRegistCnt", lv9_LimitRepDayRegistCnt);
			param.put("lv9_LimitRepDayDupRegistCnt", lv9_LimitRepDayDupRegistCnt);
			param.put("lv9_LimitRepContinueRegistTime", lv9_LimitRepContinueRegistTime);

			param.put("lv10_LimitQueDayRegistCnt", lv10_LimitQueDayRegistCnt);
			param.put("lv10_LimitQueDayDupRegistCnt", lv10_LimitQueDayDupRegistCnt);
			param.put("lv10_LimitQueContinueRegistTime", lv10_LimitQueContinueRegistTime);
			param.put("lv10_LimitAnsDayRegistCnt", lv10_LimitAnsDayRegistCnt);
			param.put("lv10_LimitAnsDayDupRegistCnt", lv10_LimitAnsDayDupRegistCnt);
			param.put("lv10_LimitAnsContinueRegistTime", lv10_LimitAnsContinueRegistTime);
			param.put("lv10_LimitRepDayRegistCnt", lv10_LimitRepDayRegistCnt);
			param.put("lv10_LimitRepDayDupRegistCnt", lv10_LimitRepDayDupRegistCnt);
			param.put("lv10_LimitRepContinueRegistTime", lv10_LimitRepContinueRegistTime);

			param.put("lv11_LimitQueDayRegistCnt", lv11_LimitQueDayRegistCnt);
			param.put("lv11_LimitQueDayDupRegistCnt", lv11_LimitQueDayDupRegistCnt);
			param.put("lv11_LimitQueContinueRegistTime", lv11_LimitQueContinueRegistTime);
			param.put("lv11_LimitAnsDayRegistCnt", lv11_LimitAnsDayRegistCnt);
			param.put("lv11_LimitAnsDayDupRegistCnt", lv11_LimitAnsDayDupRegistCnt);
			param.put("lv11_LimitAnsContinueRegistTime", lv11_LimitAnsContinueRegistTime);
			param.put("lv11_LimitRepDayRegistCnt", lv11_LimitRepDayRegistCnt);
			param.put("lv11_LimitRepDayDupRegistCnt", lv11_LimitRepDayDupRegistCnt);
			param.put("lv11_LimitRepContinueRegistTime", lv11_LimitRepContinueRegistTime);

			param.put("lv_LevelUpAuto_Yn", lv_LevelUpAuto_Yn);
			param.put("lv2_LevelUpAuto_Yn", lv2_LevelUpAuto_Yn);
			param.put("lv3_LevelUpAuto_Yn", lv3_LevelUpAuto_Yn);
			param.put("lv4_LevelUpAuto_Yn", lv4_LevelUpAuto_Yn);
			param.put("lv5_LevelUpAuto_Yn", lv5_LevelUpAuto_Yn);
			param.put("lv6_LevelUpAuto_Yn", lv6_LevelUpAuto_Yn);
			param.put("lv7_LevelUpAuto_Yn", lv7_LevelUpAuto_Yn);

			param.put("lv2_LimitJoinAfterDay", lv2_LimitJoinAfterDay);
			param.put("lv2_LimitAlmoney", lv2_LimitAlmoney);
			param.put("lv2_LimitAnsEstiCnt", lv2_LimitAnsEstiCnt);
			param.put("lv2_LimitChuMemCnt", lv2_LimitChuMemCnt);
			param.put("lv2_LimitPhotoYn", lv2_LimitPhotoYn);
			param.put("lv2_LimitReplyCnt", lv2_LimitReplyCnt);

			param.put("lv3_LimitLvMtPeriod", lv3_LimitLvMtPeriod);
			param.put("lv3_LimitQueRegistCnt", lv3_LimitQueRegistCnt);
			param.put("lv3_LimitAnsRegistCnt", lv3_LimitAnsRegistCnt);
			param.put("lv3_LimitAnsChoiceCnt", lv3_LimitAnsChoiceCnt);
			param.put("lv3_LimitAnsEstiCnt", lv3_LimitAnsEstiCnt);
			param.put("lv3_LimitAnsEstiPoint", lv3_LimitAnsEstiPoint);
			param.put("lv3_LimitChuMemCnt", lv3_LimitChuMemCnt);
			param.put("lv3_LimitReplyCnt", lv3_LimitReplyCnt);

			param.put("lv4_LimitLvMtPeriod", lv4_LimitLvMtPeriod);
			param.put("lv4_LimitQueRegistCnt", lv4_LimitQueRegistCnt);
			param.put("lv4_LimitAnsRegistCnt", lv4_LimitAnsRegistCnt);
			param.put("lv4_LimitAnsChoiceCnt", lv4_LimitAnsChoiceCnt);
			param.put("lv4_LimitAnsEstiCnt", lv4_LimitAnsEstiCnt);
			param.put("lv4_LimitAnsEstiPoint", lv4_LimitAnsEstiPoint);
			param.put("lv4_LimitChuMemCnt", lv4_LimitChuMemCnt);
			param.put("lv4_LimitReplyCnt", lv4_LimitReplyCnt);

			param.put("lv5_LimitLvMtPeriod", lv5_LimitLvMtPeriod);
			param.put("lv5_LimitQueRegistCnt", lv5_LimitQueRegistCnt);
			param.put("lv5_LimitAnsRegistCnt", lv5_LimitAnsRegistCnt);
			param.put("lv5_LimitAnsChoiceCnt", lv5_LimitAnsChoiceCnt);
			param.put("lv5_LimitAnsEstiCnt", lv5_LimitAnsEstiCnt);
			param.put("lv5_LimitAnsEstiPoint", lv5_LimitAnsEstiPoint);
			param.put("lv5_LimitChuMemCnt", lv5_LimitChuMemCnt);
			param.put("lv5_LimitReplyCnt", lv5_LimitReplyCnt);

			param.put("lv6_LimitLvMtPeriod", lv6_LimitLvMtPeriod);
			param.put("lv6_LimitQueRegistCnt", lv6_LimitQueRegistCnt);
			param.put("lv6_LimitAnsRegistCnt", lv6_LimitAnsRegistCnt);
			param.put("lv6_LimitAnsChoiceCnt", lv6_LimitAnsChoiceCnt);
			param.put("lv6_LimitAnsEstiCnt", lv6_LimitAnsEstiCnt);
			param.put("lv6_LimitAnsEstiPoint", lv6_LimitAnsEstiPoint);
			param.put("lv6_LimitChuMemCnt", lv6_LimitChuMemCnt);
			param.put("lv6_LimitReplyCnt", lv6_LimitReplyCnt);

			param.put("lv7_LimitLvMtPeriod", lv7_LimitLvMtPeriod);
			param.put("lv7_LimitQueRegistCnt", lv7_LimitQueRegistCnt);
			param.put("lv7_LimitAnsRegistCnt", lv7_LimitAnsRegistCnt);
			param.put("lv7_LimitAnsChoiceCnt", lv7_LimitAnsChoiceCnt);
			param.put("lv7_LimitAnsEstiCnt", lv7_LimitAnsEstiCnt);
			param.put("lv7_LimitAnsEstiPoint", lv7_LimitAnsEstiPoint);
			param.put("lv7_LimitChuMemCnt", lv7_LimitChuMemCnt);
			param.put("lv7_LimitReplyCnt", lv7_LimitReplyCnt);

			param.put("lv8_LimitLvMtPeriod", lv8_LimitLvMtPeriod);
			param.put("lv8_LimitQueRegistCnt", lv8_LimitQueRegistCnt);
			param.put("lv8_LimitAnsRegistCnt", lv8_LimitAnsRegistCnt);
			param.put("lv8_LimitAnsChoiceCnt", lv8_LimitAnsChoiceCnt);
			param.put("lv8_LimitAnsEstiCnt", lv8_LimitAnsEstiCnt);
			param.put("lv8_LimitAnsEstiPoint", lv8_LimitAnsEstiPoint);
			param.put("lv8_LimitChuMemCnt", lv8_LimitChuMemCnt);
			param.put("lv8_LimitReplyCnt", lv8_LimitReplyCnt);

			param.put("lv9_LimitLvMtPeriod", lv9_LimitLvMtPeriod);
			param.put("lv9_LimitQueRegistCnt", lv9_LimitQueRegistCnt);
			param.put("lv9_LimitAnsRegistCnt", lv9_LimitAnsRegistCnt);
			param.put("lv9_LimitAnsChoiceCnt", lv9_LimitAnsChoiceCnt);
			param.put("lv9_LimitAnsEstiCnt", lv9_LimitAnsEstiCnt);
			param.put("lv9_LimitAnsEstiPoint", lv9_LimitAnsEstiPoint);
			param.put("lv9_LimitChuMemCnt", lv9_LimitChuMemCnt);
			param.put("lv9_LimitReplyCnt", lv9_LimitReplyCnt);

			param.put("lv10_LimitLvMtPeriod", lv10_LimitLvMtPeriod);
			param.put("lv10_LimitQueRegistCnt", lv10_LimitQueRegistCnt);
			param.put("lv10_LimitAnsRegistCnt", lv10_LimitAnsRegistCnt);
			param.put("lv10_LimitAnsChoiceCnt", lv10_LimitAnsChoiceCnt);
			param.put("lv10_LimitAnsEstiCnt", lv10_LimitAnsEstiCnt);
			param.put("lv10_LimitAnsEstiPoint", lv10_LimitAnsEstiPoint);
			param.put("lv10_LimitChuMemCnt", lv10_LimitChuMemCnt);
			param.put("lv10_LimitReplyCnt", lv10_LimitReplyCnt);

			param.put("lv11_LimitLvMtPeriod", lv11_LimitLvMtPeriod);
			param.put("lv11_LimitQueRegistCnt", lv11_LimitQueRegistCnt);
			param.put("lv11_LimitAnsRegistCnt", lv11_LimitAnsRegistCnt);
			param.put("lv11_LimitAnsChoiceCnt", lv11_LimitAnsChoiceCnt);
			param.put("lv11_LimitAnsEstiCnt", lv11_LimitAnsEstiCnt);
			param.put("lv11_LimitAnsEstiPoint", lv11_LimitAnsEstiPoint);
			param.put("lv11_LimitChuMemCnt", lv11_LimitChuMemCnt);
			param.put("lv11_LimitReplyCnt", lv11_LimitReplyCnt);

			param.put("lv2_ExchangeBaseDeductAlmoney", lv2_ExchangeBaseDeductAlmoney);
			param.put("lv2_ExchangeLimitAlmoney", lv2_ExchangeLimitAlmoney);
			param.put("lv2_ExchangeBaseUnitAlmoney", lv2_ExchangeBaseUnitAlmoney);
			param.put("lv2_ExchangeRealMoneyDeductRate", lv2_ExchangeRealMoneyDeductRate);

			param.put("lv3_ExchangeBaseDeductAlmoney", lv3_ExchangeBaseDeductAlmoney);
			param.put("lv3_ExchangeLimitAlmoney", lv3_ExchangeLimitAlmoney);
			param.put("lv3_ExchangeBaseUnitAlmoney", lv3_ExchangeBaseUnitAlmoney);
			param.put("lv3_ExchangeRealMoneyDeductRate", lv3_ExchangeRealMoneyDeductRate);

			param.put("lv4_ExchangeBaseDeductAlmoney", lv4_ExchangeBaseDeductAlmoney);
			param.put("lv4_ExchangeLimitAlmoney", lv4_ExchangeLimitAlmoney);
			param.put("lv4_ExchangeBaseUnitAlmoney", lv4_ExchangeBaseUnitAlmoney);
			param.put("lv4_ExchangeRealMoneyDeductRate", lv4_ExchangeRealMoneyDeductRate);

			param.put("lv5_ExchangeBaseDeductAlmoney", lv5_ExchangeBaseDeductAlmoney);
			param.put("lv5_ExchangeLimitAlmoney", lv5_ExchangeLimitAlmoney);
			param.put("lv5_ExchangeBaseUnitAlmoney", lv5_ExchangeBaseUnitAlmoney);
			param.put("lv5_ExchangeRealMoneyDeductRate", lv5_ExchangeRealMoneyDeductRate);

			param.put("lv6_ExchangeBaseDeductAlmoney", lv6_ExchangeBaseDeductAlmoney);
			param.put("lv6_ExchangeLimitAlmoney", lv6_ExchangeLimitAlmoney);
			param.put("lv6_ExchangeBaseUnitAlmoney", lv6_ExchangeBaseUnitAlmoney);
			param.put("lv6_ExchangeRealMoneyDeductRate", lv6_ExchangeRealMoneyDeductRate);

			param.put("lv7_ExchangeBaseDeductAlmoney", lv7_ExchangeBaseDeductAlmoney);
			param.put("lv7_ExchangeLimitAlmoney", lv7_ExchangeLimitAlmoney);
			param.put("lv7_ExchangeBaseUnitAlmoney", lv7_ExchangeBaseUnitAlmoney);
			param.put("lv7_ExchangeRealMoneyDeductRate", lv7_ExchangeRealMoneyDeductRate);

			param.put("lv8_ExchangeBaseDeductAlmoney", lv8_ExchangeBaseDeductAlmoney);
			param.put("lv8_ExchangeLimitAlmoney", lv8_ExchangeLimitAlmoney);
			param.put("lv8_ExchangeBaseUnitAlmoney", lv8_ExchangeBaseUnitAlmoney);
			param.put("lv8_ExchangeRealMoneyDeductRate", lv8_ExchangeRealMoneyDeductRate);

			param.put("lv9_ExchangeBaseDeductAlmoney", lv9_ExchangeBaseDeductAlmoney);
			param.put("lv9_ExchangeLimitAlmoney", lv9_ExchangeLimitAlmoney);
			param.put("lv9_ExchangeBaseUnitAlmoney", lv9_ExchangeBaseUnitAlmoney);
			param.put("lv9_ExchangeRealMoneyDeductRate", lv9_ExchangeRealMoneyDeductRate);

			param.put("lv10_ExchangeBaseDeductAlmoney", lv10_ExchangeBaseDeductAlmoney);
			param.put("lv10_ExchangeLimitAlmoney", lv10_ExchangeLimitAlmoney);
			param.put("lv10_ExchangeBaseUnitAlmoney", lv10_ExchangeBaseUnitAlmoney);
			param.put("lv10_ExchangeRealMoneyDeductRate", lv10_ExchangeRealMoneyDeductRate);

			param.put("lv11_ExchangeBaseDeductAlmoney", lv11_ExchangeBaseDeductAlmoney);
			param.put("lv11_ExchangeLimitAlmoney", lv11_ExchangeLimitAlmoney);
			param.put("lv11_ExchangeBaseUnitAlmoney", lv11_ExchangeBaseUnitAlmoney);
			param.put("lv11_ExchangeRealMoneyDeductRate", lv11_ExchangeRealMoneyDeductRate);

			param.put("lv2_ExchangeMtPeriod", lv2_ExchangeMtPeriod);
			param.put("lv2_ExchangeQueRegistCnt", lv2_ExchangeQueRegistCnt);
			param.put("lv2_ExchangeAnsRegistCnt", lv2_ExchangeAnsRegistCnt);
			param.put("lv2_ExchangeEstiCnt", lv2_ExchangeEstiCnt);
			param.put("lv2_ExchangeEstiPoint", lv2_ExchangeEstiPoint);

			param.put("lv3_ExchangeMtPeriod", lv3_ExchangeMtPeriod);
			param.put("lv3_ExchangeQueRegistCnt", lv3_ExchangeQueRegistCnt);
			param.put("lv3_ExchangeAnsRegistCnt", lv3_ExchangeAnsRegistCnt);
			param.put("lv3_ExchangeEstiCnt", lv3_ExchangeEstiCnt);
			param.put("lv3_ExchangeEstiPoint", lv3_ExchangeEstiPoint);

			param.put("lv4_ExchangeMtPeriod", lv4_ExchangeMtPeriod);
			param.put("lv4_ExchangeQueRegistCnt", lv4_ExchangeQueRegistCnt);
			param.put("lv4_ExchangeAnsRegistCnt", lv4_ExchangeAnsRegistCnt);
			param.put("lv4_ExchangeEstiCnt", lv4_ExchangeEstiCnt);
			param.put("lv4_ExchangeEstiPoint", lv4_ExchangeEstiPoint);

			param.put("lv5_ExchangeMtPeriod", lv5_ExchangeMtPeriod);
			param.put("lv5_ExchangeQueRegistCnt", lv5_ExchangeQueRegistCnt);
			param.put("lv5_ExchangeAnsRegistCnt", lv5_ExchangeAnsRegistCnt);
			param.put("lv5_ExchangeEstiCnt", lv5_ExchangeEstiCnt);
			param.put("lv5_ExchangeEstiPoint", lv5_ExchangeEstiPoint);

			param.put("lv6_ExchangeMtPeriod", lv6_ExchangeMtPeriod);
			param.put("lv6_ExchangeQueRegistCnt", lv6_ExchangeQueRegistCnt);
			param.put("lv6_ExchangeAnsRegistCnt", lv6_ExchangeAnsRegistCnt);
			param.put("lv6_ExchangeEstiCnt", lv6_ExchangeEstiCnt);
			param.put("lv6_ExchangeEstiPoint", lv6_ExchangeEstiPoint);

			param.put("lv7_ExchangeMtPeriod", lv7_ExchangeMtPeriod);
			param.put("lv7_ExchangeQueRegistCnt", lv7_ExchangeQueRegistCnt);
			param.put("lv7_ExchangeAnsRegistCnt", lv7_ExchangeAnsRegistCnt);
			param.put("lv7_ExchangeEstiCnt", lv7_ExchangeEstiCnt);
			param.put("lv7_ExchangeEstiPoint", lv7_ExchangeEstiPoint);

			param.put("lv8_ExchangeMtPeriod", lv8_ExchangeMtPeriod);
			param.put("lv8_ExchangeQueRegistCnt", lv8_ExchangeQueRegistCnt);
			param.put("lv8_ExchangeAnsRegistCnt", lv8_ExchangeAnsRegistCnt);
			param.put("lv8_ExchangeEstiCnt", lv8_ExchangeEstiCnt);
			param.put("lv8_ExchangeEstiPoint", lv8_ExchangeEstiPoint);

			param.put("lv9_ExchangeMtPeriod", lv9_ExchangeMtPeriod);
			param.put("lv9_ExchangeQueRegistCnt", lv9_ExchangeQueRegistCnt);
			param.put("lv9_ExchangeAnsRegistCnt", lv9_ExchangeAnsRegistCnt);
			param.put("lv9_ExchangeEstiCnt", lv9_ExchangeEstiCnt);
			param.put("lv9_ExchangeEstiPoint", lv9_ExchangeEstiPoint);

			param.put("lv10_ExchangeMtPeriod", lv10_ExchangeMtPeriod);
			param.put("lv10_ExchangeQueRegistCnt", lv10_ExchangeQueRegistCnt);
			param.put("lv10_ExchangeAnsRegistCnt", lv10_ExchangeAnsRegistCnt);
			param.put("lv10_ExchangeEstiCnt", lv10_ExchangeEstiCnt);
			param.put("lv10_ExchangeEstiPoint", lv10_ExchangeEstiPoint);

			param.put("lv11_ExchangeMtPeriod", lv11_ExchangeMtPeriod);
			param.put("lv11_ExchangeQueRegistCnt", lv11_ExchangeQueRegistCnt);
			param.put("lv11_ExchangeAnsRegistCnt", lv11_ExchangeAnsRegistCnt);
			param.put("lv11_ExchangeEstiCnt", lv11_ExchangeEstiCnt);
			param.put("lv11_ExchangeEstiPoint", lv11_ExchangeEstiPoint);

			param.put("lv1_QueRegAlmoney", lv1_QueRegAlmoney);
			param.put("lv2_QueRegAlmoney", lv2_QueRegAlmoney);
			param.put("lv3_QueRegAlmoney", lv3_QueRegAlmoney);
			param.put("lv4_QueRegAlmoney", lv4_QueRegAlmoney);
			param.put("lv5_QueRegAlmoney", lv5_QueRegAlmoney);
			param.put("lv6_QueRegAlmoney", lv6_QueRegAlmoney);
			param.put("lv7_QueRegAlmoney", lv7_QueRegAlmoney);
			param.put("lv8_QueRegAlmoney", lv8_QueRegAlmoney);
			param.put("lv9_QueRegAlmoney", lv9_QueRegAlmoney);
			param.put("lv10_QueRegAlmoney", lv10_QueRegAlmoney);
			param.put("lv11_QueRegAlmoney", lv11_QueRegAlmoney);


			configService.configOriginalUpdate(param);


			CommonUtil.jspAlert(response, "정상적으로 등록되었습니다." , "/aadmin/configInput", "parent.parent");
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}
	}
	/*
	@RequestMapping("process/refreshLvReadyData")
	public int refreshLvReadyData(HttpServletRequest request, HttpServletResponse response){
		double lv = (double) request.getAttribute("lv");
		//
		return 0;
	}
	*/

	@RequestMapping("quiz/quizList")
	public ModelAndView quizList(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용
		ModelAndView mav = new ModelAndView();

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userLv = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userLv = Integer.parseInt(String.valueOf( globalVal.get("UserLv") ) );
		}

		if(userLv == 99) {
			String curPageName = "/aadmin/quiz/quizList";

			String req_TREC = CommonUtil.fn_Word_In( request.getParameter("trec") != null ? request.getParameter("trec").trim() : "0");
			String req_PG = CommonUtil.fn_Word_In( request.getParameter("pg") != null ? request.getParameter("pg").trim() : "");
			String src_Sort = CommonUtil.fn_Word_In( request.getParameter("src_Sort") != null ? request.getParameter("src_Sort").trim() : "");
			String src_Kind = CommonUtil.fn_Word_In( request.getParameter("src_Kind") != null ? request.getParameter("src_Kind").trim() : "");
			String src_Text = CommonUtil.fn_Word_In( request.getParameter("src_Text") != null ? request.getParameter("src_Text").trim() : "" );

			if(req_PG == "") {
				req_PG = "1";
			}

			if(src_Sort.equals("")) {
				src_Sort = "Seq";
			}

			String s_tmp = "";
			int n_trec = 0;
			s_tmp = req_TREC;

			if(CommonUtil.isNumeric(s_tmp) && s_tmp.length() > 0) {
				n_trec = Integer.parseInt(s_tmp);
			}


			int quizCount = quizService.getQuizQueCount();

			n_trec = quizCount;

			int n_pagesize = 20;
			int n_pagescnt = 10;
			int n_curpage = 1;
			int n_totalpage = 0;
			int st_num = 0;
			int en_num = 0;

			s_tmp = req_PG;

			if(CommonUtil.isNumeric(s_tmp) == true && s_tmp.length() > 0) {
				n_curpage = Integer.parseInt(s_tmp);
			}
			n_totalpage = Math.round(n_trec / n_pagesize);

			if(n_trec % n_pagesize > 0) {
				n_totalpage = n_totalpage + 1;
			}

			if(n_curpage <= 0) {
				n_curpage = 1;
			}

			if(n_curpage > n_totalpage) {
				n_curpage = n_totalpage;
			}

			st_num = (n_pagesize) * (n_curpage - 1) + 1;
			en_num = (n_pagesize) * (n_curpage);


			HashMap<String, Object> lParam = new HashMap<String, Object>();
			lParam.put("p_Option", (n_pagesize * n_curpage));
			//lParam.put("psrc_Kind", src_Kind);
			//lParam.put("psrc_Text", src_Text);
			//lParam.put("psrc_Cond", src_Sort);
			lParam.put("st_num", st_num);
			lParam.put("en_num", en_num);

			List<QuizQueVO> quizList = null;

			if(quizCount > 0) {
				quizList = quizService.getQuizQueList(lParam);
			}

			String url = "/aadmin/quiz/quizList?trec=" + req_TREC + "&src_Sort=" + src_Sort + "&src_Kind=" + src_Kind + "&src_Text=" + src_Text;

			String paging = CommonUtil.get_Paging_Tag_New2_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);

			mav.addObject("n_trec", n_trec);
			mav.addObject("src_Text", src_Text);
			mav.addObject("curPageName", curPageName);
			mav.addObject("n_curpage", n_curpage);
			mav.addObject("n_pagesize", n_pagesize);
			mav.addObject("quizList", quizList);
			mav.addObject("paging", paging);
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}

		return mav;
	}

	@RequestMapping("quiz/quizInput")
	public ModelAndView quizInput(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userLv = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userLv = Integer.parseInt(String.valueOf( globalVal.get("UserLv") ) );
		}

		if(userLv == 99) {
			int uid = (request.getParameter("uid") != null && !request.getParameter("uid").equals("")) ? Integer.parseInt(request.getParameter("uid")) : 0;

			if(uid > 0) {
				QuizQueVO quiz = quizService.getQuizQue(uid);

				List<QuizAnsVO> ansList = null;
				if(quiz != null) {
					ansList = quizService.getQuizAnsList(quiz.getUid());
				}

				mav.addObject("uid", uid);
				mav.addObject("quiz", quiz);
				mav.addObject("ansList", ansList);
			}
			else {
				//
			}
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}

		return mav;
	}

	@RequestMapping(value="/quiz/quizQueSave", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public void questionZzim(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용
		String uid = request.getParameter("uid") != null ? request.getParameter("uid") : "";
		String useChk = request.getParameter("useChk");
		String correct = request.getParameter("correct");
		String rtime1 = request.getParameter("rtime1") != null && !request.getParameter("rtime1").equals("") ? request.getParameter("rtime1") : "0";
		String rtime2 = request.getParameter("rtime2") != null && !request.getParameter("rtime2").equals("") ? request.getParameter("rtime2") : "0";
		String stime1 = request.getParameter("stime1") != null && !request.getParameter("stime1").equals("") ? request.getParameter("stime1") : "0";
		String stime2 = request.getParameter("stime2") != null && !request.getParameter("stime2").equals("") ? request.getParameter("stime2") : "0";
		String quest = request.getParameter("quest") != null ? request.getParameter("quest") : "";
		String hint = request.getParameter("hint") != null ? request.getParameter("hint") : "";
		String comment = request.getParameter("comment") != null ? request.getParameter("comment") : "";

		String[] anoParam = request.getParameterValues("ano[]");
		String[] answerParam = request.getParameterValues("answer[]");


		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");

		JSONObject global = null;

		if(cookies1 != null) {
			global = CommonUtil.getGlobal(request, response);
		}

		if(global == null) {
			CommonUtil.jspAlert(response, "로그인후 이용 가능합니다.", "", "parent.parent");
		}
		else {
			//correct 는 addQuizQue를 통해서 입력
			//anoParam 과 answerParam 는 동적 배열을 만들어서 addQuizAns 를 이용하여 입력하되, 기존 데이터 가 있으면 deleteAllQuizAns 를 이용하여 삭제 한다. (주의 QuizQue 테블의 uid가 있는 경우에만 처리 한다.)

			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("useChk", useChk);
			param.put("correct", correct);
			param.put("rtime1", rtime1);
			param.put("rtime2", rtime2);
			param.put("stime1", stime1);
			param.put("stime2", stime2);
			param.put("quest", quest);
			param.put("hint", hint);
			param.put("comment", comment);

			String format = "yyyy-MM-dd hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
			Calendar today = Calendar.getInstance();
			SimpleDateFormat type = new SimpleDateFormat(format);
			String createDt = type.format(today.getTime());

			int chk = 0;
			if(uid.equals("")) {
				// Quiz 신규 등록
				param.put("createDt", createDt);

				quizService.addQuizQue(param);
				int quid = (int) param.get("uid");

				// Ans 신규 등록
				for(int i = 0; i < anoParam.length; i++) {
					HashMap<String, Object> aParam = new HashMap<String, Object>();
					aParam.put("ano", anoParam[i]);
					aParam.put("quid", quid);
					aParam.put("answer", answerParam[i]);
					aParam.put("createDt", createDt);

					quizService.addQuizAns(aParam);
				}

				chk = 1;
			}
			else {
				// Quiz 수정
				param.put("updateDt", createDt);
				param.put("uid", uid);
				quizService.updateQuizQue(param);

				//QUIZ_ANS 삭제
				String quid = uid;
				quizService.deleteAllQuizAns( Integer.parseInt(quid) );

				// Ans 신규 등록
				for(int i = 0; i < anoParam.length; i++) {
					HashMap<String, Object> aParam = new HashMap<String, Object>();
					aParam.put("ano", anoParam[i]);
					aParam.put("quid", quid);
					aParam.put("answer", answerParam[i]);
					aParam.put("createDt", createDt);

					quizService.addQuizAns(aParam);
				}

				chk = 1;
			}

			if(chk > 0) {
				CommonUtil.jspAlert(response, "질문이 저장되었습니다.", "/aadmin/quiz/quizList", "parent.parent");
			}
			else {
				CommonUtil.jspAlert(response, "퀴즈 정보 저장 실패!!!", "back", "parent.parent");
			}
		}
	}

	@RequestMapping(value="quiz/quizQueDel", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String quizQueDel(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		//int userSeq = 0;
		int userLv = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			//userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
			userLv = Integer.parseInt(String.valueOf( globalVal.get("UserLv") ) );
		}

		if(userLv > 0) {
			String uid = request.getParameter("uid") != null ? request.getParameter("uid") : "";

			if(!uid.equals("")) {
				int quid = Integer.parseInt(uid);

				// 보기 삭제
				quizService.deleteAllQuizAns(quid);

				// 퀴즈 삭제
				quizService.deleteOneQuizQue(quid);

				result = CommonUtil.libJsonExit("데이터가 갱신되었습니다.", r_res);
			}
		}

		return result;
	}


	@RequestMapping("quiz/quizGameList")
	public ModelAndView quizGame(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용
		ModelAndView mav = new ModelAndView();

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userLv = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userLv = Integer.parseInt(String.valueOf( globalVal.get("UserLv") ) );
		}

		if(userLv == 99) {
			String curPageName = "/aadmin/quiz/quizGameList";

			String req_TREC = CommonUtil.fn_Word_In( request.getParameter("trec") != null ? request.getParameter("trec").trim() : "0");
			String req_PG = CommonUtil.fn_Word_In( request.getParameter("pg") != null ? request.getParameter("pg").trim() : "");
			String src_Sort = CommonUtil.fn_Word_In( request.getParameter("src_Sort") != null ? request.getParameter("src_Sort").trim() : "");
			String src_Kind = CommonUtil.fn_Word_In( request.getParameter("src_Kind") != null ? request.getParameter("src_Kind").trim() : "");
			String src_Text = CommonUtil.fn_Word_In( request.getParameter("src_Text") != null ? request.getParameter("src_Text").trim() : "" );

			if(req_PG == "") {
				req_PG = "1";
			}

			if(src_Sort.equals("")) {
				src_Sort = "Seq";
			}

			String s_tmp = "";
			int n_trec = 0;
			s_tmp = req_TREC;

			if(CommonUtil.isNumeric(s_tmp) && s_tmp.length() > 0) {
				n_trec = Integer.parseInt(s_tmp);
			}


			int quizCount = quizService.getQuizGameCount();

			n_trec = quizCount;

			int n_pagesize = 20;
			int n_pagescnt = 10;
			int n_curpage = 1;
			int n_totalpage = 0;
			int st_num = 0;
			int en_num = 0;

			s_tmp = req_PG;

			if(CommonUtil.isNumeric(s_tmp) == true && s_tmp.length() > 0) {
				n_curpage = Integer.parseInt(s_tmp);
			}
			n_totalpage = Math.round(n_trec / n_pagesize);

			if(n_trec % n_pagesize > 0) {
				n_totalpage = n_totalpage + 1;
			}

			if(n_curpage <= 0) {
				n_curpage = 1;
			}

			if(n_curpage > n_totalpage) {
				n_curpage = n_totalpage;
			}

			st_num = (n_pagesize) * (n_curpage - 1) + 1;
			en_num = (n_pagesize) * (n_curpage);


			HashMap<String, Object> lParam = new HashMap<String, Object>();
			lParam.put("p_Option", (n_pagesize * n_curpage));
			//lParam.put("psrc_Kind", src_Kind);
			//lParam.put("psrc_Text", src_Text);
			//lParam.put("psrc_Cond", src_Sort);
			lParam.put("st_num", st_num);
			lParam.put("en_num", en_num);

			List<QuizGameVO> quizList = null;

			if(quizCount > 0) {
				quizList = quizService.getQuizGameList(lParam);
			}

			String url = "/aadmin/quiz/quizGameList?trec=" + req_TREC + "&src_Sort=" + src_Sort + "&src_Kind=" + src_Kind + "&src_Text=" + src_Text;

			String paging = CommonUtil.get_Paging_Tag_New2_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);



			mav.addObject("n_trec", n_trec);
			mav.addObject("src_Text", src_Text);
			mav.addObject("curPageName", curPageName);
			mav.addObject("n_curpage", n_curpage);
			mav.addObject("n_pagesize", n_pagesize);
			mav.addObject("quizList", quizList);
			mav.addObject("paging", paging);
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}

		return mav;
	}

	@RequestMapping("quiz/quizGameInput")
	public ModelAndView quizGameInput(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userLv = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userLv = Integer.parseInt(String.valueOf( globalVal.get("UserLv") ) );
		}

		if(userLv == 99) {
			int uid = (request.getParameter("uid") != null && !request.getParameter("uid").equals("")) ? Integer.parseInt(request.getParameter("uid")) : 0;

			if(uid > 0) {
				QuizGameVO quiz = quizService.getQuizGame(uid);

				List<QuizGameQueVO> ansList = null;
				if(quiz != null) {
					ansList = quizService.getQuizGameQueList(quiz.getUid());
				}

				mav.addObject("uid", uid);
				mav.addObject("quiz", quiz);
				mav.addObject("ansList", ansList);
			}
			else {
				//
			}
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}

		return mav;
	}

	@RequestMapping(value="getQuizQueAjax", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String getQuizQueAjax(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String act = request.getParameter("ACT");
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userLv = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userLv = Integer.parseInt(String.valueOf( globalVal.get("UserLv") ) );
		}

		if(userLv == 99) {
			int uid = (request.getParameter("uid") != null && !request.getParameter("uid").equals("")) ? Integer.parseInt(request.getParameter("uid")) : 0;

			String quest = quizService.getQuizQueQuest(uid);

			if(quest != null) {
				result = CommonUtil.libJsonExit(quest, r_res);
			}
			else {
				result = CommonUtil.libJsonExit("false", r_res);
			}

		} else {
			result = CommonUtil.libJsonExit("access", r_res);
		}

		return result;
	}

	@RequestMapping("quiz/getQuiz")
	public ModelAndView getQuiz(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용
		ModelAndView mav = new ModelAndView();

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userLv = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userLv = Integer.parseInt(String.valueOf( globalVal.get("UserLv") ) );
		}

		if(userLv == 99) {
			String curPageName = "/aadmin/quiz/getQuiz";

			String req_TREC = CommonUtil.fn_Word_In( request.getParameter("trec") != null ? request.getParameter("trec").trim() : "0");
			String req_PG = CommonUtil.fn_Word_In( request.getParameter("pg") != null ? request.getParameter("pg").trim() : "");
			String src_Sort = CommonUtil.fn_Word_In( request.getParameter("src_Sort") != null ? request.getParameter("src_Sort").trim() : "");
			String src_Kind = CommonUtil.fn_Word_In( request.getParameter("src_Kind") != null ? request.getParameter("src_Kind").trim() : "");
			String src_Text = CommonUtil.fn_Word_In( request.getParameter("src_Text") != null ? request.getParameter("src_Text").trim() : "" );

			if(req_PG == "") {
				req_PG = "1";
			}

			if(src_Sort.equals("")) {
				src_Sort = "Seq";
			}

			String s_tmp = "";
			int n_trec = 0;
			s_tmp = req_TREC;

			if(CommonUtil.isNumeric(s_tmp) && s_tmp.length() > 0) {
				n_trec = Integer.parseInt(s_tmp);
			}


			int quizCount = quizService.getQuizQueCount();

			n_trec = quizCount;

			int n_pagesize = 20;
			int n_pagescnt = 10;
			int n_curpage = 1;
			int n_totalpage = 0;
			int st_num = 0;
			int en_num = 0;

			s_tmp = req_PG;

			if(CommonUtil.isNumeric(s_tmp) == true && s_tmp.length() > 0) {
				n_curpage = Integer.parseInt(s_tmp);
			}
			n_totalpage = Math.round(n_trec / n_pagesize);

			if(n_trec % n_pagesize > 0) {
				n_totalpage = n_totalpage + 1;
			}

			if(n_curpage <= 0) {
				n_curpage = 1;
			}

			if(n_curpage > n_totalpage) {
				n_curpage = n_totalpage;
			}

			st_num = (n_pagesize) * (n_curpage - 1) + 1;
			en_num = (n_pagesize) * (n_curpage);


			HashMap<String, Object> lParam = new HashMap<String, Object>();
			lParam.put("p_Option", (n_pagesize * n_curpage));
			//lParam.put("psrc_Kind", src_Kind);
			//lParam.put("psrc_Text", src_Text);
			//lParam.put("psrc_Cond", src_Sort);
			lParam.put("st_num", st_num);
			lParam.put("en_num", en_num);

			List<QuizQueVO> quizList = null;

			if(quizCount > 0) {
				quizList = quizService.getQuizQueList(lParam);
			}

			String url = "/aadmin/quiz/getQuiz?trec=" + req_TREC + "&src_Sort=" + src_Sort + "&src_Kind=" + src_Kind + "&src_Text=" + src_Text;

			String paging = CommonUtil.get_Paging_Tag_New2_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);

			mav.addObject("n_trec", n_trec);
			mav.addObject("src_Text", src_Text);
			mav.addObject("curPageName", curPageName);
			mav.addObject("n_curpage", n_curpage);
			mav.addObject("n_pagesize", n_pagesize);
			mav.addObject("quizList", quizList);
			mav.addObject("paging", paging);
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}

		return mav;
	}

	@RequestMapping(value="getCarryoverMoney", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String getCarryoverMoney(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userLv = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userLv = Integer.parseInt(String.valueOf( globalVal.get("UserLv") ) );
		}

		if(userLv == 99) {
			int carryoverCount = quizService.getCarryoverMoneyCnt();

			int carryoverMoney = 0;

			if(carryoverCount > 0) {
				carryoverMoney = quizService.getCarryoverMoney();
			}

			result = CommonUtil.libJsonExit(carryoverMoney, r_res);

		} else {
			result = CommonUtil.libJsonExit("access", r_res);
		}

		return result;
	}

	@RequestMapping(value="/quiz/quizGameSave", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public void quizGameSave(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용
		int uid = request.getParameter("uid") != null ? Integer.parseInt(request.getParameter("uid")) : 0;
		String step = request.getParameter("step");
		String subject = request.getParameter("subject");
		int admAlmoney = request.getParameter("admAlmoney") != null ? Integer.parseInt(request.getParameter("admAlmoney")) : 0;
		int attendAlmoney = request.getParameter("attendAlmoney") != null ? Integer.parseInt(request.getParameter("attendAlmoney")) : 0;
		int carryoverMoney = request.getParameter("carryoverMoney") != null ? Integer.parseInt(request.getParameter("carryoverMoney")) : 0;

		String startYmd = request.getParameter("startYmd") != null && !request.getParameter("startYmd").equals("") ? request.getParameter("startYmd") : "";
		String startDtType = request.getParameter("startDtType") != null && !request.getParameter("startDtType").equals("") ? request.getParameter("startDtType") : "";
		String startDtH = request.getParameter("startDtH") != null && !request.getParameter("startDtH").equals("") ? request.getParameter("startDtH") : "";
		String startDtM = request.getParameter("startDtM") != null && !request.getParameter("startDtM").equals("") ? request.getParameter("startDtM") : "";

		String useChk = request.getParameter("useChk");


		String[] ordParam = request.getParameterValues("ord[]");
		String[] queidParam = request.getParameterValues("queid[]");
		String[] questParam = request.getParameterValues("quest[]");


		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");

		JSONObject global = null;

		if(cookies1 != null) {
			global = CommonUtil.getGlobal(request, response);
		}

		if(global == null) {
			CommonUtil.jspAlert(response, "로그인후 이용 가능합니다.", "", "parent.parent");
		}
		else {
			//correct 는 addQuizQue를 통해서 입력
			//anoParam 과 answerParam 는 동적 배열을 만들어서 addQuizAns 를 이용하여 입력하되, 기존 데이터 가 있으면 deleteAllQuizAns 를 이용하여 삭제 한다. (주의 QuizQue 테블의 uid가 있는 경우에만 처리 한다.)

			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("step", step);
			param.put("subject", subject);
			param.put("admAlmoney", admAlmoney);
			param.put("attendAlmoney", attendAlmoney);
			param.put("carryoverMoney", carryoverMoney);
			param.put("startYmd", startYmd);
			param.put("startDtType", startDtType);
			param.put("startDtH", startDtH);
			param.put("startDtM", startDtM);
			param.put("useChk", useChk);

			String format = "yyyy-MM-dd hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
			Calendar today = Calendar.getInstance();
			SimpleDateFormat type = new SimpleDateFormat(format);
			String createDt = type.format(today.getTime());

			int chk = 0;
			if(uid == 0) {
				// Quiz 신규 등록
				param.put("createDt", createDt);

				quizService.addQuizGame(param);
				uid = (int) param.get("uid");

				// QuizGame 신규 등록
				for(int i = 0; i < ordParam.length; i++) {
					HashMap<String, Object> aParam = new HashMap<String, Object>();
					aParam.put("ord", ordParam[i]);
					aParam.put("quid", uid);
					aParam.put("queid", queidParam[i]);
					aParam.put("quest", questParam[i]);
					aParam.put("createDt", createDt);

					quizService.addQuizGameQue(aParam);
				}

				chk = 1;
			}
			else {
				// Quiz 수정
				//param.put("updateDt", createDt);
				param.put("uid", uid);
				quizService.updateQuizGame(param);

				//QUIZ_ANS 삭제
				quizService.deleteAllQuizGameQue( uid );

				// QuizGame 신규 등록
				for(int i = 0; i < ordParam.length; i++) {
					HashMap<String, Object> aParam = new HashMap<String, Object>();
					aParam.put("ord", ordParam[i]);
					aParam.put("quid", uid);
					aParam.put("queid", queidParam[i]);
					aParam.put("quest", questParam[i]);
					aParam.put("createDt", createDt);

					quizService.addQuizGameQue(aParam);
				}

				chk = 1;
			}

			if(chk > 0) {
				//QUIZ_GAME complete = 'C' AND uid < (현재uid) 인것을 찾아서 'Y' 로 수정
				if(carryoverMoney > 0) {
					quizService.setQuizGameComplete(uid);
				}

				CommonUtil.jspAlert(response, "퀴즈게임이 저장되었습니다.", "/aadmin/quiz/quizGameList", "parent.parent");
			}
			else {
				CommonUtil.jspAlert(response, "퀴즈게임 저장 실패!!!", "back", "parent.parent");
			}
		}
	}

	@RequestMapping(value="quiz/quizGameDel", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String quizGameDel(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		//int userSeq = 0;
		int userLv = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			//userSeq = Integer.parseInt(String.valueOf( globalVal.get("UserSeq") ) );
			userLv = Integer.parseInt(String.valueOf( globalVal.get("UserLv") ) );
		}

		if(userLv > 0) {
			String uid = request.getParameter("uid") != null ? request.getParameter("uid") : "";

			if(!uid.equals("")) {
				int quid = Integer.parseInt(uid);

				// 보기 삭제
				quizService.deleteAllQuizGameQue(quid);

				// 퀴즈 삭제
				quizService.deleteOneQuizGame(quid);

				result = CommonUtil.libJsonExit("데이터가 갱신되었습니다.", r_res);
			}
		}

		return result;
	}

	@RequestMapping("quiz/getQuizWinner")
	public ModelAndView getQuizProc(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용
		ModelAndView mav = new ModelAndView();

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userLv = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userLv = Integer.parseInt(String.valueOf( globalVal.get("UserLv") ) );
		}

		if(userLv == 99) {
			String curPageName = "/aadmin/quiz/getQuizWinner";

			int uid = request.getParameter("uid") != null ? Integer.parseInt(request.getParameter("uid")) : 0;
			String req_TREC = CommonUtil.fn_Word_In( request.getParameter("trec") != null ? request.getParameter("trec").trim() : "0");
			String req_PG = CommonUtil.fn_Word_In( request.getParameter("pg") != null ? request.getParameter("pg").trim() : "");
			String src_Sort = CommonUtil.fn_Word_In( request.getParameter("src_Sort") != null ? request.getParameter("src_Sort").trim() : "");
			String src_Kind = CommonUtil.fn_Word_In( request.getParameter("src_Kind") != null ? request.getParameter("src_Kind").trim() : "");
			String src_Text = CommonUtil.fn_Word_In( request.getParameter("src_Text") != null ? request.getParameter("src_Text").trim() : "" );

			if(req_PG == "") {
				req_PG = "1";
			}

			if(src_Sort.equals("")) {
				src_Sort = "Seq";
			}

			String s_tmp = "";
			int n_trec = 0;
			s_tmp = req_TREC;

			if(CommonUtil.isNumeric(s_tmp) && s_tmp.length() > 0) {
				n_trec = Integer.parseInt(s_tmp);
			}

			HashMap<String, Object> rParam = new HashMap<String, Object>();
			rParam.put("gqid", uid);

			int reqLogCount = quizService.getQuizReqLogCount(rParam);

			HashMap<String, Object> qParam = new HashMap<String, Object>();
			qParam.put("gqid", uid);
			qParam.put("success", "Y");

			int succssLogCount = quizService.getQuizSuccessLogCount(qParam);
			//System.out.println("succssLogCount : " + succssLogCount);
			n_trec = succssLogCount;

			int n_pagesize = 20;
			int n_pagescnt = 10;
			int n_curpage = 1;
			int n_totalpage = 0;
			int st_num = 0;
			int en_num = 0;

			s_tmp = req_PG;

			if(CommonUtil.isNumeric(s_tmp) == true && s_tmp.length() > 0) {
				n_curpage = Integer.parseInt(s_tmp);
			}
			n_totalpage = Math.round(n_trec / n_pagesize);

			if(n_trec % n_pagesize > 0) {
				n_totalpage = n_totalpage + 1;
			}

			if(n_curpage <= 0) {
				n_curpage = 1;
			}

			if(n_curpage > n_totalpage) {
				n_curpage = n_totalpage;
			}

			st_num = (n_pagesize) * (n_curpage - 1) + 1;
			en_num = (n_pagesize) * (n_curpage);


			HashMap<String, Object> lParam = new HashMap<String, Object>();
			lParam.put("p_Option", (n_pagesize * n_curpage));
			lParam.put("gqid", uid);
			lParam.put("success", "Y");
			lParam.put("st_num", st_num);
			lParam.put("en_num", en_num);

			List<QuizLogVO> quizLogList = null;
			if(succssLogCount > 0) {
				quizLogList = quizService.getQuizSuccessLogList(lParam);
			}

			String url = "/aadmin/quiz/getQuizWinner?uid="+uid+"&trec=" + req_TREC + "&src_Sort=" + src_Sort + "&src_Kind=" + src_Kind + "&src_Text=" + src_Text;

			String paging = CommonUtil.get_Paging_Tag_New2_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);


			QuizGameVO quiz = quizService.getQuizGame(uid);

			mav.addObject("uid", uid);
			mav.addObject("succssLogCount", succssLogCount);
			mav.addObject("reqLogCount", reqLogCount);
			mav.addObject("n_trec", n_trec);
			mav.addObject("src_Text", src_Text);
			mav.addObject("curPageName", curPageName);
			mav.addObject("n_curpage", n_curpage);
			mav.addObject("n_pagesize", n_pagesize);
			mav.addObject("quizLogList", quizLogList);
			mav.addObject("quiz", quiz);
			mav.addObject("paging", paging);
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}

		return mav;
	}

	@RequestMapping("quiz/getQuizReqUser")
	public ModelAndView getQuizReqUser(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용
		ModelAndView mav = new ModelAndView();

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userLv = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userLv = Integer.parseInt(String.valueOf( globalVal.get("UserLv") ) );
		}

		if(userLv == 99) {
			String curPageName = "/aadmin/quiz/getQuizReqUser";

			int uid = request.getParameter("uid") != null ? Integer.parseInt(request.getParameter("uid")) : 0;
			String req_TREC = CommonUtil.fn_Word_In( request.getParameter("trec") != null ? request.getParameter("trec").trim() : "0");
			String req_PG = CommonUtil.fn_Word_In( request.getParameter("pg") != null ? request.getParameter("pg").trim() : "");
			String src_Sort = CommonUtil.fn_Word_In( request.getParameter("src_Sort") != null ? request.getParameter("src_Sort").trim() : "");
			String src_Kind = CommonUtil.fn_Word_In( request.getParameter("src_Kind") != null ? request.getParameter("src_Kind").trim() : "");
			String src_Text = CommonUtil.fn_Word_In( request.getParameter("src_Text") != null ? request.getParameter("src_Text").trim() : "" );

			if(req_PG == "") {
				req_PG = "1";
			}

			if(src_Sort.equals("")) {
				src_Sort = "Seq";
			}

			String s_tmp = "";
			int n_trec = 0;
			s_tmp = req_TREC;

			if(CommonUtil.isNumeric(s_tmp) && s_tmp.length() > 0) {
				n_trec = Integer.parseInt(s_tmp);
			}

			HashMap<String, Object> qParam = new HashMap<String, Object>();
			qParam.put("gqid", uid);
			qParam.put("success", "Y");

			int succssLogCount = quizService.getQuizSuccessLogCount(qParam);


			HashMap<String, Object> rParam = new HashMap<String, Object>();
			rParam.put("gqid", uid);

			int reqLogCount = quizService.getQuizReqLogCount(rParam);
			//System.out.println("succssLogCount : " + succssLogCount);
			n_trec = reqLogCount;

			int n_pagesize = 20;
			int n_pagescnt = 10;
			int n_curpage = 1;
			int n_totalpage = 0;
			int st_num = 0;
			int en_num = 0;

			s_tmp = req_PG;

			if(CommonUtil.isNumeric(s_tmp) == true && s_tmp.length() > 0) {
				n_curpage = Integer.parseInt(s_tmp);
			}
			n_totalpage = Math.round(n_trec / n_pagesize);

			if(n_trec % n_pagesize > 0) {
				n_totalpage = n_totalpage + 1;
			}

			if(n_curpage <= 0) {
				n_curpage = 1;
			}

			if(n_curpage > n_totalpage) {
				n_curpage = n_totalpage;
			}

			st_num = (n_pagesize) * (n_curpage - 1) + 1;
			en_num = (n_pagesize) * (n_curpage);


			HashMap<String, Object> lParam = new HashMap<String, Object>();
			lParam.put("p_Option", (n_pagesize * n_curpage));
			lParam.put("gqid", uid);
			lParam.put("st_num", st_num);
			lParam.put("en_num", en_num);

			List<QuizLogVO> quizLogList = null;
			if(reqLogCount > 0) {
				quizLogList = quizService.getQuizReqLogList(lParam);
			}

			String url = "/aadmin/quiz/getQuizReqUser?uid="+uid+"&trec=" + req_TREC + "&src_Sort=" + src_Sort + "&src_Kind=" + src_Kind + "&src_Text=" + src_Text;

			String paging = CommonUtil.get_Paging_Tag_New2_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);


			QuizGameVO quiz = quizService.getQuizGame(uid);

			mav.addObject("uid", uid);
			mav.addObject("succssLogCount", succssLogCount);
			mav.addObject("reqLogCount", reqLogCount);
			mav.addObject("n_trec", n_trec);
			mav.addObject("src_Text", src_Text);
			mav.addObject("curPageName", curPageName);
			mav.addObject("n_curpage", n_curpage);
			mav.addObject("n_pagesize", n_pagesize);
			mav.addObject("quizLogList", quizLogList);
			mav.addObject("quiz", quiz);
			mav.addObject("paging", paging);
		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}

		return mav;
	}

	@RequestMapping(value="procQuizWinnerAjax", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String procQuizWinnerAjax(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userLv = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userLv = Integer.parseInt(String.valueOf( globalVal.get("UserLv") ) );
		}

		if(userLv == 99) {
			int uid = (request.getParameter("uid") != null && !request.getParameter("uid").equals("")) ? Integer.parseInt(request.getParameter("uid")) : 0;

			QuizGameVO quiz = quizService.getQuizGame(uid);

			String format = "yyyy-MM-dd hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
			Calendar today = Calendar.getInstance();
			SimpleDateFormat type = new SimpleDateFormat(format);
			String createDt = type.format(today.getTime());

			HashMap<String, Object> rParam = new HashMap<String, Object>();
			rParam.put("gqid", uid);

			int reqLogCount = quizService.getQuizReqLogCount(rParam);


			HashMap<String, Object> qParam = new HashMap<String, Object>();
			qParam.put("gqid", uid);
			qParam.put("success", "Y");

			int succssLogCount = quizService.getQuizSuccessLogCount(qParam);

			int total = quiz.getAdmAlmoney() + (quiz.getAttendAlmoney() * reqLogCount) + quiz.getCarryoverMoney();

			int divPrice = 0;
			if(succssLogCount > 0)
				divPrice = total / succssLogCount;

			HashMap<String, Object> lParam = new HashMap<String, Object>();
			lParam.put("gqid", uid);
			lParam.put("success", "Y");

			List<QuizLogVO> quizLogList = null;
			if(succssLogCount > 0) {
				quizLogList = quizService.getQuizSuccessLogListAll(lParam);
			}

			if(quizLogList.size() > 0) {
				for(int i = 0; i < quizLogList.size(); i++) {
					int userSeq = quizLogList.get(i).getUserSeq();

					HashMap<String, Object> mParam = new HashMap<String, Object>();
					mParam.put("mem_seq", userSeq);
					mParam.put("mem_almoney", divPrice);
					mParam.put("almoney_flag", "46");

					//회원별 우승알 지급
					commonService.setUpdateAlmoney(mParam);
				}
				//getQuizGame complete = Y:완료
				HashMap<String, Object> cParam = new HashMap<String, Object>();
				cParam.put("uid", uid);
				cParam.put("complete", "Y");
				cParam.put("updateDt", createDt);
				quizService.updateQuizGameComplete(cParam);
			}
			else {
				//getQuizGame complete = C:이월 처리 하기
				HashMap<String, Object> cParam = new HashMap<String, Object>();
				cParam.put("uid", uid);
				cParam.put("complete", "C");
				cParam.put("updateDt", createDt);
				quizService.updateQuizGameComplete(cParam);
			}

			result = CommonUtil.libJsonExit("true", r_res);

		} else {
			result = CommonUtil.libJsonExit("access", r_res);
		}

		return result;
	}

	@RequestMapping(value="quizGameEnableChk", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String quizGameEnableChk(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userLv = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userLv = Integer.parseInt(String.valueOf( globalVal.get("UserLv") ) );
		}

		if(userLv == 99) {
			int uid = (request.getParameter("uid") != null && !request.getParameter("uid").equals("")) ? Integer.parseInt(request.getParameter("uid")) : 0;

			int enableCnt = 0;
			if(uid > 0) {
				enableCnt = quizService.getQuizGameOnCountByUid(uid);
			}
			else {
				enableCnt = quizService.getQuizGameOnCountByNonUid();
			}

			result = CommonUtil.libJsonExit(enableCnt, r_res);

		} else {
			result = CommonUtil.libJsonExit("access", r_res);
		}

		return result;
	}

	@RequestMapping("quiz/roulletteStatistics")
	public ModelAndView roulletteStatistics(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		locale = new Locale("ko", "KR"); // 운영자 로케일 설정 전용
		ModelAndView mav = new ModelAndView();

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject globalVal = null;
		int userLv = 0;

		if(cookies1 != null) {
			globalVal = CommonUtil.getGlobal(request, response);
			userLv = Integer.parseInt(String.valueOf( globalVal.get("UserLv") ) );
		}

		if(userLv == 99) {
			// 총 발행 이용권
			int total = commonService.getRulTicketTotalCount();
			int totalSum = commonService.getRulAlmoneyTotalSum();
			int userCount = commonService.getRulUserTotalCount();
			int disCount = commonService.getRulDisUseTotalCount();

			HashMap<String, Object> LVcountS =commonService.getRulTicketLVCountS();
			HashMap<String, Object> LVcountS2 =commonService.getRulTicketALCountS();
			HashMap<String, Object> LVcountS3 =commonService.getRulUseUserCountS();

			HashMap<String, Object> part1 =commonService.getRulPartCountS();
			HashMap<String, Object> part2 =commonService.getRulPartMoneyCountS();

			mav.addObject("total", total);
			mav.addObject("totalSum", totalSum);
			mav.addObject("userCount", userCount);
			mav.addObject("disCount", disCount);
			mav.addObject("LVcountS", LVcountS);
			mav.addObject("LVcountS2", LVcountS2);
			mav.addObject("LVcountS3", LVcountS3);
			mav.addObject("part1", part1);
			mav.addObject("part2", part2);

		}
		else {
			CommonUtil.jspAlert(response, "접근 권한이 없습니다.", "/", "top");
		}

		return mav;
	}


	@RequestMapping(value="quiz/getTopStatistics", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String getTopStatistics(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String code1 = request.getParameter("topRank").toString();


		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("numTOP", code1);

		List<HashMap<String, Object>> RTop =commonService.getTopStatistics(param);

		String result = new Gson().toJson(RTop);

		return result;
	}


	@RequestMapping(value="quiz/getDateStatistics", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String getDateStatistics(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String datePre = request.getParameter("datePre").toString();
		String datePOST = request.getParameter("datePOST").toString();


		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("datePre", datePre);
		param.put("datePOST", datePOST);

		List<HashMap<String, Object>> getDate = commonService.getDateStatistics(param);
		getDate.add(commonService.getRulDatePartCountS(param));
		getDate.add(commonService.getRulPartDateMoneyCountS(param));

		//System.out.println(commonService.getRulDatePartCountS(param));
		//System.out.println(commonService.getRulPartDateMoneyCountS(param));
		//System.out.println("getDate : " + getDate);


		int total = commonService.getRulDateTicketTotalCount(param);
		int totalSum = commonService.getRulDateAlmoneyTotalSum(param);
		int userCount = commonService.getRulDateUserTotalCount(param);
		int disCount = commonService.getRulDateDisUseTotalCount(param);

		HashMap<String, Object> intVal = new HashMap<String, Object>();

		intVal.put("total", total);
		intVal.put("totalSum", totalSum);
		intVal.put("userCount", userCount);
		intVal.put("disCount", disCount);

		getDate.add(intVal);

		String result = new Gson().toJson(getDate);

		return result;
	}


	@RequestMapping(value="quiz/getDateTopStatistics", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String getDateTopStatistics(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String datePre = request.getParameter("datePre").toString();
		String datePOST = request.getParameter("datePOST").toString();
		String DateTop = request.getParameter("DateTop").toString();


		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("datePre", datePre);
		param.put("datePOST", datePOST);
		param.put("DateTop", DateTop);


		List<HashMap<String, Object>> getDate = commonService.getDateTopStatistics(param);

		String result = new Gson().toJson(getDate);

		return result;
	}


	@RequestMapping(value="quiz/getChartStatistics", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String getChartStatistics(HttpServletRequest request, HttpServletResponse response) throws Exception {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		// 시작일이 2021-02-19

		Date day20210219 = format.parse("2021-02-19");
		Date nowDay = new Date();

		//System.out.println("test 중");

		List<HashMap<String, Object>> resultA = new ArrayList<>();

		cal.setTime(day20210219);

		for ( int i = 0 ; i < 300 ; i++ ) {
			if(day20210219.before(nowDay)) {
				day20210219 = cal.getTime();
				//System.out.println("date str = " + format.format(day20210219));
				cal.add(Calendar.DATE, 1);
			}else {
				break;
			}
		}
		//if(day20210219.before(nowDay))

		//List<HashMap<String, Object>> getDate = commonService.getChartStatistics();

		String result = new Gson().toJson("");

		return result;
	}

}
