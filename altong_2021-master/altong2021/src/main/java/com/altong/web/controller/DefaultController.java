package com.altong.web.controller;

import java.io.File;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.altong.web.logic.NoticeVO;
import com.altong.web.logic.common.EncodeLibrary;
import com.altong.web.logic.config.ConfigVO;
import com.altong.web.logic.member.MemberVO;
import com.altong.web.logic.util.CallSecurity;
import com.altong.web.logic.util.CodeUtil;
import com.altong.web.logic.util.CommonUtil;
import com.altong.web.logic.util.CookieBox;
import com.altong.web.logic.util.EncLibrary;
import com.altong.web.logic.util.ExcelUtil;
import com.altong.web.logic.util.MD5Class;
import com.altong.web.logic.util.SignatureUtil;
import com.altong.web.logic.util.UtilFile;
import com.altong.web.service.CookieLoginService;
import com.altong.web.service.common.CommonService;
import com.altong.web.service.config.ConfigService;
import com.altong.web.service.file.FileService;
import com.altong.web.service.member.MemberService;
import com.google.gson.Gson;

import jasp.buildin.Response;
import jasp.buildin.Server;
import jasp.buildin.Session;
import jasp.vbs.variant;
import jasp.vbs.vb;
import net.coobird.thumbnailator.Thumbnails;

@Controller
@RequestMapping("default/*")
public class DefaultController {
	@Autowired
	MemberService memberService;

	@Autowired
	CommonService commonService;

	@Autowired
	FileService fileService;

	@Autowired
	ConfigService configService;

	@Autowired
	CookieLocaleResolver localeResolver;

	@Autowired
	MessageSource messageSource;

	@Autowired
	CookieLoginService cookieLoginService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("default/main")
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		ModelAndView mav = new ModelAndView();

		String org_lang = "";
		String org_Country = "";

		String s_lang = request.getParameter("s_lang");

		if(request.getParameter("LoginFlag") != "") {
			mav.addObject("LoginFlag", request.getParameter("LoginFlag"));
		}

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject global = null;


		//System.out.println("locale : " + locale.toString());
		if(locale.toString().equals("ko")) {
			new Locale("ko", "KR");
			org_lang = "ko";
			org_Country = "KR";
		}
		else {
			org_lang = locale.getLanguage();
			org_Country = locale.getCountry();
		}



		HashMap<String, Object> localeItem = CommonUtil.getLocale(locale);

    	String lang = String.valueOf(localeItem.get("lang"));
    	String nation = String.valueOf(localeItem.get("nation2byte"));

	    if(lang.contains("_")) {
			String[] langArr = lang.split("_");
			lang = langArr[0];
		}


	    if(!lang.equals("ko") && !lang.equals("zh") && !lang.equals("ja")) {
	    	lang = "en";
	    	nation = "US";
	    }

	    if(s_lang == null) { s_lang = lang; }
	    else {
	    	String[] langArr2 = s_lang.split("_");
			String lang2 = langArr2[0];

			s_lang = lang2;
			nation = langArr2[1];
	    }

	    //System.out.println("s_lang : " +  s_lang);
	    //System.out.println("nation : " +  nation);
	    //System.out.println("locale : " +  locale.toString());
	    Locale new_locale = new Locale(s_lang, nation);
	    //localeResolver.setLocale(request, response, new_locale);

	    //System.out.println("hello : " +  messageSource.getMessage("hello", null, new_locale));
	    //mav.addObject("hello", messageSource.getMessage("hello", null, new_locale));
	    String lvNameDefault = "";
	    if(userSeq > 0) {
	    	lvNameDefault = CommonUtil.getLvName( userLv, s_lang );
	    }

	    mav.addObject("userSeq", userSeq);
	    mav.addObject("lvNameDefault", lvNameDefault);
	    mav.addObject("mainLang", s_lang.toUpperCase());


	    Cookie cLang = cookieBox.getCookie("myLang");
		Cookie cCountry = cookieBox.getCookie("myCountry");

		if(cLang == null && cCountry == null) {
			Cookie info1 = new Cookie("myLang", org_lang);
			info1.setVersion( 0 );
			info1.setMaxAge( 10000 * 24 * 60 * 60 );
			info1.setPath("/");

			response.addCookie(info1);

			Cookie info2 = new Cookie("myCountry", org_Country);
			info2.setVersion( 0 );
			info2.setMaxAge( 10000 * 24 * 60 * 60 );
			info2.setPath("/");

			response.addCookie(info2);
		}

		return mav;
	}

	@RequestMapping("default/mainNew")
	public ModelAndView mainNew(HttpServletRequest request, Locale locale) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView();

		if(request.getParameter("LoginFlag") != "") {
			mav.addObject("LoginFlag", request.getParameter("LoginFlag"));
		}

		return mav;
	}

	@RequestMapping("default/login")
	public ModelAndView login(HttpServletRequest request, Locale locale) {

		ModelAndView mav = new ModelAndView();

		CookieBox cookieBox = new CookieBox(request);
		Cookie cLang = cookieBox.getCookie("myLang");
		Cookie cCountry = cookieBox.getCookie("myCountry");

		String myLang = "";
		String myCountry = "";

		if(cLang != null) {
			myLang = cLang.getValue();
		}

		if(cCountry != null) {
			myCountry = cCountry.getValue();
		}

		//System.out.println("locale 1 : " + locale.toString());
		locale = new Locale(myLang, myCountry);
		//System.out.println("locale 2 : " + locale.toString());

		HashMap<String, Object> localeItem = CommonUtil.getLocale(locale);
		String areaCode = String.valueOf(localeItem.get("areaCode"));
		//System.out.println("areaCode : " + areaCode);

		mav.addObject("areaCode", areaCode);

		return mav;
	}

	@RequestMapping("default/joinRule")
	public ModelAndView joinRule(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		ModelAndView mav = new ModelAndView();

		ConfigVO conf = configService.getConfigList();

		NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

		String memJoinCertType = conf.getMemJoinCertType();
		String sEncData = ""; // 업체정보를 암호화 한 데이타

		String sSiteCode = "BB965"; 			//NICE로부터 부여받은 사이트 코드
		String sSitePassword = "PnWuAfNp2nyN";	//NICE로부터 부여받은 사이트 패스워드

		String sAuthType = "M";					//없으면 기본 선택화면, M: 핸드폰, C: 카드, X: 공인인증서
		String popgubun = "Y";					//Y : 취소버튼 있음, N : 취소버튼 없음
		String customize = "";					//없으면 기본 웹페이지 / Mobile : 모바일페이지
		String sGender = "";					//없으면 기본 선택 값, 0 : 여자, 1 : 남자

		//CheckPlus(본인인증) 처리 후, 결과 데이타를 리턴 받기위해 다음예제와 같이 http부터 입력합니다.
		//리턴url은 인증 전 인증페이지를 호출하기 전 url과 동일해야 합니다. ex) 인증 전 url : http://www.~ 리턴 url : http://www.~


		String sReturnUrl = request.getScheme() + "://" + request.getServerName() + "/default/joinInput_n";			//성공시 이동될 URL
		String sErrorUrl = request.getScheme() + "://" + request.getServerName() + "/default/check/checkplus_fail";			//실패시 이동될 URL

		//String sRequestNO = "REQ0000000001";	//요청 번호, 이는 성공/실패후에 같은 값으로 되돌려주게 되므로
												//업체에 적절하게 변경하여 쓰거나, 아래와 같이 생성한다.

		String sRequestNO = niceCheck.getRequestNO(sSiteCode);

		HttpSession session = request.getSession(true);
		session.setAttribute("REQ_SEQ", sRequestNO);

		// 입력될 plain 데이타를 만든다.
		String sPlainData = "7:REQ_SEQ" + sRequestNO.getBytes().length + ":" + sRequestNO +
                "8:SITECODE" + sSiteCode.getBytes().length + ":" + sSiteCode +
                "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType +
                "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl +
                "7:ERR_URL" + sErrorUrl.getBytes().length + ":" + sErrorUrl +
                "11:POPUP_GUBUN" + popgubun.getBytes().length + ":" + popgubun +
                "9:CUSTOMIZE" + customize.getBytes().length + ":" + customize +
				"6:GENDER" + sGender.getBytes().length + ":" + sGender;



		//실제적인 암호화
		String sMessage = "";
		int iReturn = niceCheck.fnEncode(sSiteCode, sSitePassword, sPlainData);

		String msg1 = messageSource.getMessage("msg_1066", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1067", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_1068", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_1069", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_0318", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		if(iReturn == 0) {
			sEncData = niceCheck.getCipherData();
		}
		else if( iReturn == -1)
	    {
	        sMessage = msg1; // msg1 활용
	    }
	    else if( iReturn == -2)
	    {
	        sMessage = msg2; // msg2 활용
	    }
	    else if( iReturn == -3)
	    {
	        sMessage = msg3; // msg3 활용
	    }
	    else if( iReturn == -9)
	    {
	        sMessage = msg4; // msg4 활용
	    }
	    else
	    {
	        sMessage = msg5 + iReturn; // msg5 활용
	    }


		if(sMessage != "") {
			CommonUtil.jspAlert(response, sMessage , "", "");
			//-1 : 암호화 시스템 에러입니다.
			//-2 : 암호화 처리오류입니다.
			//-3 : 암호화 데이터 오류입니다.
			//-4 : 입력 데이터 오류입니다.

			return null;
		}

		HashMap<String, Object> localeItem = CommonUtil.getLocale(locale);
    	String lang = String.valueOf(localeItem.get("lang"));


		mav.addObject("memJoinCertType", memJoinCertType);
		mav.addObject("sMessage", sMessage);
		mav.addObject("sEncData", sEncData);
		mav.addObject("lang", lang);

		return mav;
	}

	@RequestMapping("default/pop_certi_n")
	public ModelAndView pop_certi_n(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		ModelAndView mav = new ModelAndView();

		NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();


		Cookie info = new Cookie("CH_EncodeData", "");
		info.setVersion( 0 );
		info.setMaxAge( 0 );
		info.setPath("/");
		response.addCookie(info);



		String sEncData = ""; // 업체정보를 암호화 한 데이타

		String sSiteCode = "BB965"; 			//NICE로부터 부여받은 사이트 코드
		String sSitePassword = "PnWuAfNp2nyN";	//NICE로부터 부여받은 사이트 패스워드

		String sAuthType = "M";					//없으면 기본 선택화면, M: 핸드폰, C: 카드, X: 공인인증서
		String popgubun = "Y";					//Y : 취소버튼 있음, N : 취소버튼 없음
		String customize = "";					//없으면 기본 웹페이지 / Mobile : 모바일페이지
		String sGender = "";					//없으면 기본 선택 값, 0 : 여자, 1 : 남자




		//CheckPlus(본인인증) 처리 후, 결과 데이타를 리턴 받기위해 다음예제와 같이 http부터 입력합니다.
				//리턴url은 인증 전 인증페이지를 호출하기 전 url과 동일해야 합니다. ex) 인증 전 url : http://www.~ 리턴 url : http://www.~
		String sReturnUrl = request.getScheme() + "://" + request.getServerName() + "/default/joinInput_n";			//성공시 이동될 URL
		String sErrorUrl = request.getScheme() + "://" + request.getServerName() + "/default/check/checkplus_fail";			//실패시 이동될 URL

		String sRequestNO = niceCheck.getRequestNO(sSiteCode);

		HttpSession session = request.getSession(true);
		session.setAttribute("REQ_SEQ", sRequestNO);

		// 입력될 plain 데이타를 만든다.
		String sPlainData = "7:REQ_SEQ" + sRequestNO.getBytes().length + ":" + sRequestNO +
                "8:SITECODE" + sSiteCode.getBytes().length + ":" + sSiteCode +
                "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType +
                "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl +
                "7:ERR_URL" + sErrorUrl.getBytes().length + ":" + sErrorUrl +
                "11:POPUP_GUBUN" + popgubun.getBytes().length + ":" + popgubun +
                "9:CUSTOMIZE" + customize.getBytes().length + ":" + customize +
				"6:GENDER" + sGender.getBytes().length + ":" + sGender;

		//실제적인 암호화
		String sMessage = "";
		String msg1 = messageSource.getMessage("msg_1066", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1067", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_1068", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_1069", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_0318", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		int iReturn = niceCheck.fnEncode(sSiteCode, sSitePassword, sPlainData);

		if(iReturn == 0) {
			sEncData = niceCheck.getCipherData();
		}
		else if( iReturn == -1)
	    {
	        sMessage = msg1; // msg1 활용
	    }
	    else if( iReturn == -2)
	    {
	        sMessage = msg2; // msg2 활용
	    }
	    else if( iReturn == -3)
	    {
	        sMessage = msg3; // msg3 활용
	    }
	    else if( iReturn == -9)
	    {
	        sMessage = msg4; // msg4 활용
	    }
	    else
	    {
	        sMessage = msg5 + iReturn; // msg5 활용
	    }


		if(sMessage != "") {
			CommonUtil.jspAlert(response, sMessage , "", "");
			//-1 : 암호화 시스템 에러입니다.
			//-2 : 암호화 처리오류입니다.
			//-3 : 암호화 데이터 오류입니다.
			//-4 : 입력 데이터 오류입니다.

			return null;
		}


		mav.addObject("sEncData", sEncData);

		return mav;
	}

	@RequestMapping("/default/joinInput_n")
	public ModelAndView joinInput_n(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		ModelAndView mav = new ModelAndView();

		NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();


		if(request.getParameter("EncodeData") == null) {
			String msg1 = messageSource.getMessage("msg_1019", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			CommonUtil.jspAlert(response, msg1 , "/default/main", "parent.parent"); // msg1 활용
			return null;
		}


		String sEncodeData = CommonUtil.requestReplace(request.getParameter("EncodeData"), "encodeData");

	    String sSiteCode = "BB965";			// NICE로부터 부여받은 사이트 코드
	    String sSitePassword = "PnWuAfNp2nyN";	// NICE로부터 부여받은 사이트 패스워드

	    String sCipherTime = "";			// 복호화한 시간
	    String sRequestNumber = "";			// 요청 번호
	    String sResponseNumber = "";		// 인증 고유번호
	    String sAuthType = "";				// 인증 수단
	    String sName = "";					// 성명
	    String sDupInfo = "";				// 중복가입 확인값 (DI_64 byte)
	    String sConnInfo = "";				// 연계정보 확인값 (CI_88 byte)
	    String sBirthDate = "";				// 생년월일(YYYYMMDD)
	    String sGender = "";				// 성별
	    String sNationalInfo = "";			// 내/외국인정보 (개발가이드 참조)
		String sMobileNo = "";				// 휴대폰번호
		String sMobileCo = "";				// 통신사
	    String sMessage = "";
	    String sPlainData = "";

	    String code = request.getParameter("code");
	    String chuCode1 = "";
	    String chuCode2 = "";
	    //System.out.println("code : " + code);
	    if(code != null) {
	    	String[] codeArr = code.split("-");
	    	chuCode1 = codeArr[0];
	    	chuCode2 = codeArr[1];
	    }

	    int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

	    if( iReturn == 0 ) {
	    	HttpSession session = request.getSession(true);
	    	sPlainData = niceCheck.getPlainData();
	        sCipherTime = niceCheck.getCipherDateTime();

	        // 데이타를 추출합니다.
	        java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);

	        sRequestNumber  = (String)mapresult.get("REQ_SEQ");
	        sResponseNumber = (String)mapresult.get("RES_SEQ");
	        sAuthType		= (String)mapresult.get("AUTH_TYPE");
	        //sName			= (String)mapresult.get("NAME");
			sName			= (String)mapresult.get("UTF8_NAME"); //charset utf8 사용시 주석 해제 후 사용
	        sBirthDate		= (String)mapresult.get("BIRTHDATE");
	        sGender			= (String)mapresult.get("GENDER");
	        sNationalInfo  	= (String)mapresult.get("NATIONALINFO");
	        sDupInfo		= (String)mapresult.get("DI");
	        sConnInfo		= (String)mapresult.get("CI");
	        sMobileNo		= (String)mapresult.get("MOBILE_NO");
	        sMobileCo		= (String)mapresult.get("MOBILE_CO");

	        String session_sRequestNumber = (String)session.getAttribute("REQ_SEQ");
	        if(!sRequestNumber.equals(session_sRequestNumber))
	        {
	        	String msg2 = messageSource.getMessage("msg_1109", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
	            sMessage = msg2; // msg2 활용
	            sResponseNumber = "";
	            sAuthType = "";
	        }
	    }
	    else if( iReturn == -1)
	    {
	    	String msg3 = messageSource.getMessage("msg_1112", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
	        sMessage = msg3; // msg3 활용
	    }
	    else if( iReturn == -4)
	    {
	    	String msg4 = messageSource.getMessage("msg_1113", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
	        sMessage = msg4; // msg4 활용
	    }
	    else if( iReturn == -5)
	    {
	    	String msg5 = messageSource.getMessage("msg_1114", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
	        sMessage = msg5; // msg5 활용
	    }
	    else if( iReturn == -6)
	    {
	    	String msg6 = messageSource.getMessage("msg_1115", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
	        sMessage = msg6; // msg6 활용
	    }
	    else if( iReturn == -9)
	    {
	    	String msg7 = messageSource.getMessage("msg_1116", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
	        sMessage = msg7; // msg7 활용
	    }
	    else if( iReturn == -12)
	    {
	    	String msg8 = messageSource.getMessage("msg_1117", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
	        sMessage = msg8; // msg8 활용
	    }
	    else
	    {
	    	String msg9 = messageSource.getMessage("msg_0318", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
	        sMessage = msg9 + iReturn; // msg9 활용
	    }

	    if(sMessage != "") {
			CommonUtil.jspAlert(response, sMessage , "", "");

			return null;
		}


	    int result_code = 0;
	    EncLibrary enc = new EncLibrary();

	    //SP_MEMBER_JOIN_CH
	    HashMap<String, Object> jParam = new HashMap<String, Object>();
	    jParam.put("mem_name", sName);
	    jParam.put("mem_phone", enc.AlmoneyEncrypt(sMobileNo));
	    jParam.put("mem_birth", sBirthDate);
	    jParam.put("mem_gender", sGender);
	    jParam.put("dup_info", sDupInfo);

	    result_code = memberService.getMemberJoinFlag(jParam);


	    if(result_code > 0) {
	    	String msg10 = messageSource.getMessage("msg_1124", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
	    	String msg11 = messageSource.getMessage("msg_0319", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			CommonUtil.jspAlert(response, msg10 + "\\n\\n" + msg11, "/default/main", "parent.parent"); // msg10, msg11 활용

			return null;
		}


	    mav.addObject("sEncodeData", sEncodeData);
	    mav.addObject("sMobileCo", sMobileCo);
	    mav.addObject("sAuthType", sAuthType);
	    mav.addObject("sName", sName);
	    mav.addObject("sBirthDate", sBirthDate);
	    mav.addObject("sGender", sGender);
	    mav.addObject("sCipherTime", sCipherTime);
	    mav.addObject("sRequestNumber", sRequestNumber);
	    mav.addObject("sResponseNumber", sResponseNumber);
	    mav.addObject("sNationalInfo", sNationalInfo);
	    mav.addObject("sMobileNo", sMobileNo);
	    mav.addObject("chuCode1", chuCode1);
	    mav.addObject("chuCode2", chuCode2);

		return mav;
	}

	@RequestMapping("default/joinInput_n2")
	public ModelAndView joinInput_n2(HttpServletRequest request, HttpServletResponse response, Locale locale) {

		ModelAndView mav = new ModelAndView();

		ConfigVO conf = configService.getConfigList();

		Integer smsTimeLimit = conf.getMemJoinSmsTimeOut();

		HashMap<String, Object> localeItem = CommonUtil.getLocale(locale);
		String nation = String.valueOf(localeItem.get("nation"));
    	String lang = String.valueOf(localeItem.get("lang"));
    	String areaCode = String.valueOf(localeItem.get("areaCode"));

    	if(!lang.equals("ko") && !lang.equals("zh")) { lang = "en"; }


		final String code = cookieLoginService.getCookieChuCode(request, response);

		String chuCode1 = "";
		String chuCode2 = "";

		if(code != null && !code.equals("")) {
			String[] codeArr = code.split("-");
	    	chuCode1 = codeArr[0];
	    	chuCode2 = codeArr[1];
		}
		//System.out.println("chuCode1 : " + chuCode1);
		//System.out.println("chuCode2 : " + chuCode2);

		String chuCodeFlag = "false";
		if(chuCode1 != "" && chuCode2 != "") {
			chuCodeFlag = "true";
		}

		String format = "yyyy-MM-dd";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String regDate = type.format(today.getTime());

		mav.addObject("smsTimeLimit", smsTimeLimit);
		mav.addObject("chuCode1", chuCode1);
		mav.addObject("chuCode2", chuCode2);
		mav.addObject("now", regDate);
		mav.addObject("chuCodeFlag", chuCodeFlag);

		mav.addObject("nation", nation.toUpperCase());
		mav.addObject("lang", lang);
		mav.addObject("areaCode", areaCode);

		return mav;
	}

	@RequestMapping(value="join/checkRecommender", produces="application/json;charset=UTF-8", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String exchangeAskAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String result = null;
		String recommenderSeq = request.getParameter("RecommenderSeq");

		EncLibrary enc = new EncLibrary();
		String name = "";
		String country = "";
		String phone = "";
		String phone1 = "";
		String phone2 = "";
		String phone3 = "";

		if(recommenderSeq != null) {
			MemberVO mem = memberService.getMemberCertInfo(Integer.parseInt(recommenderSeq));

			if(mem.getName() != null) {
				name = mem.getName();
				country = String.valueOf( mem.getCountry() );
				phone = enc.AlmoneyDecrypt( mem.getPhone() );

				phone1 = phone.substring(0, 2);
				phone2 = "**" + phone.substring( phone.length() - 6, phone.length()).substring(0, 2);
				phone3 = "**" + phone.substring( phone.length() - 2, phone.length());
				phone = "+" + country + " " + phone1 + phone2 + phone3;


				HashMap<String, Object> total = new HashMap<String, Object>();
		    	total.put("name", name);
		    	total.put("phone", phone);

		    	result = JSONValue.toJSONString(total);
			}
		}

		return result;
	}

	@RequestMapping(value="check/checkplus_fail", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView checkplus_fail(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		ModelAndView mav = new ModelAndView();

		NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

		String sEncodeData = CommonUtil.requestReplace(request.getParameter("EncodeData"), "encodeData");

	    String sSiteCode = "BB965";				// NICE로부터 부여받은 사이트 코드
	    String sSitePassword = "PnWuAfNp2nyN";	// NICE로부터 부여받은 사이트 패스워드

	    String sCipherTime = "";			// 복호화한 시간
	    String sRequestNumber = "";			// 요청 번호
	    String sErrorCode = "";				// 인증 결과코드
	    String sAuthType = "";				// 인증 수단
	    String sMessage = "";
	    String sPlainData = "";

	    HttpSession session = request.getSession(true);

	    String msg1 = messageSource.getMessage("msg_1109", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
	    String msg2 = messageSource.getMessage("msg_1110", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
	    String msg3 = messageSource.getMessage("msg_1112", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
	    String msg4 = messageSource.getMessage("msg_1113", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
	    String msg5 = messageSource.getMessage("msg_1114", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
	    String msg6 = messageSource.getMessage("msg_1115", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
	    String msg7 = messageSource.getMessage("msg_1116", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
	    String msg8 = messageSource.getMessage("msg_1117", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
	    String msg9 = messageSource.getMessage("msg_0318", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
	    String msg10 = messageSource.getMessage("msg_1125", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

	    int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

	    if( iReturn == 0 )
	    {
	        sPlainData = niceCheck.getPlainData();
	        sCipherTime = niceCheck.getCipherDateTime();

	        // 데이타를 추출합니다.
	        java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);

	        sRequestNumber 	= (String)mapresult.get("REQ_SEQ");
	        sErrorCode 		= (String)mapresult.get("ERR_CODE");
	        sAuthType 		= (String)mapresult.get("AUTH_TYPE");

	        if(session.getAttribute("REQ_SEQ") != sRequestNumber) {
	        	sMessage = msg1 + msg2 + "<br>"; // msg1, msg2 활용
	        }
	    }
	    else if( iReturn == -1)
	    {
	        sMessage = msg3; // msg3 활용
	    }
	    else if( iReturn == -4)
	    {
	        sMessage = msg4; // msg4 활용
	    }
	    else if( iReturn == -5)
	    {
	        sMessage = msg5; // msg5 활용
	    }
	    else if( iReturn == -6)
	    {
	        sMessage = msg6; // msg6 활용
	    }
	    else if( iReturn == -9)
	    {
	        sMessage = msg7; // msg7 활용
	    }
	    else if( iReturn == -12)
	    {
	        sMessage = msg8; // msg8 활용
	    }
	    else
	    {
	        sMessage = msg9 + iReturn; // msg9 활용
	    }

	    mav.addObject("sResultMessage",  msg10 + "[" + sPlainData + "]<br>"); // msg10 활용
	    mav.addObject("sMessage", sMessage);
	    mav.addObject("sCipherTime", sCipherTime);
	    mav.addObject("sRequestNumber", sRequestNumber);
	    mav.addObject("sErrorCode", sErrorCode);
	    mav.addObject("sAuthType", sAuthType);

		return mav;
	}

	@RequestMapping(value="check/checkplus_success", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView checkplus_success(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		ModelAndView mav = new ModelAndView();

		NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

		String sEncodeData = CommonUtil.requestReplace(request.getParameter("EncodeData"), "encodeData");

	    String sSiteCode = "BB965";				// NICE로부터 부여받은 사이트 코드
	    String sSitePassword = "PnWuAfNp2nyN";	// NICE로부터 부여받은 사이트 패스워드

	    String sCipherTime = "";			// 복호화한 시간
	    String sRequestNumber = "";			// 요청 번호
	    String sResponseNumber = "";		// 인증 고유번호
	    String sAuthType = "";				// 인증 수단
	    String sName = "";					// 성명
	    String sDupInfo = "";				// 중복가입 확인값 (DI_64 byte)
	    String sConnInfo = "";				// 연계정보 확인값 (CI_88 byte)
	    String sBirthDate = "";				// 생일
	    String sGender = "";				// 성별
	    String sNationalInfo = "";			// 내/외국인 정보 (사용자 매뉴얼 참조)
	    String sMobileNo = "";				// 휴대폰번호
	    String sMobileCo = "";				// 통신사
	    String sMessage = "";
	    String sPlainData = "";

	    HttpSession session = request.getSession(true);

	    String msg1 = messageSource.getMessage("msg_1109", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
	    String msg2 = messageSource.getMessage("msg_1110", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
	    String msg3 = messageSource.getMessage("msg_1112", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
	    String msg4 = messageSource.getMessage("msg_1113", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
	    String msg5 = messageSource.getMessage("msg_1114", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
	    String msg6 = messageSource.getMessage("msg_1115", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
	    String msg7 = messageSource.getMessage("msg_1116", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
	    String msg8 = messageSource.getMessage("msg_1117", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
	    String msg9 = messageSource.getMessage("msg_1118", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

	    int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

	    if( iReturn == 0 )
	    {
	        sPlainData = niceCheck.getPlainData();
	        sCipherTime = niceCheck.getCipherDateTime();

	        // 데이타를 추출합니다.
	        java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);

	        sRequestNumber 	= (String)mapresult.get("REQ_SEQ");
	        sAuthType 		= (String)mapresult.get("AUTH_TYPE");
	        sResponseNumber = (String)mapresult.get("RES_SEQ");
	        sAuthType 		= (String)mapresult.get("AUTH_TYPE");
	        sName			= (String)mapresult.get("UTF8_NAME");
	        sBirthDate		= (String)mapresult.get("BIRTHDATE");
	        sGender			= (String)mapresult.get("GENDER");
	        sNationalInfo	= (String)mapresult.get("NATIONALINFO");
	        sDupInfo		= (String)mapresult.get("DI");
	        sConnInfo		= (String)mapresult.get("CI");
	        sMobileNo		= (String)mapresult.get("MOBILE_NO");
	        sMobileCo		= (String)mapresult.get("MOBILE_CO");

	        if(session.getAttribute("REQ_SEQ") != sRequestNumber) {
	        	sMessage = msg1 + msg2 + "<br>"; // msg1, msg2 활용
	        }
	    }
	    else if( iReturn == -1)
	    {
	        sMessage = msg3; // msg3 활용
	    }
	    else if( iReturn == -4)
	    {
	        sMessage = msg4; // msg4 활용
	    }
	    else if( iReturn == -5)
	    {
	        sMessage = msg5; // msg5 활용
	    }
	    else if( iReturn == -6)
	    {
	        sMessage = msg6; // msg6 활용
	    }
	    else if( iReturn == -9)
	    {
	        sMessage = msg7; // msg7 활용
	    }
	    else if( iReturn == -12)
	    {
	        sMessage = msg8; // msg8 활용
	    }
	    else
	    {
	        sMessage = msg9 + iReturn; // msg9 활용
	    }

	    if(iReturn != 0 || sMessage != "") {
	    	CommonUtil.jspAlert(response, sMessage , "", "");
	    }


	    mav.addObject("sMessage", sMessage);
	    mav.addObject("sCipherTime", sCipherTime);
	    mav.addObject("sRequestNumber", sRequestNumber);
	    mav.addObject("sResponseNumber", sResponseNumber);
	    mav.addObject("sAuthType", sAuthType);
	    mav.addObject("sName", sName);
	    mav.addObject("sGender", sGender);
	    mav.addObject("sBirthDate", sBirthDate);
	    mav.addObject("sNationalInfo", sNationalInfo);
	    mav.addObject("sDupInfo", sDupInfo);
	    mav.addObject("sConnInfo", sConnInfo);
	    mav.addObject("sMobileNo", sMobileNo);
	    mav.addObject("sMobileCo", sMobileCo);

		return mav;
	}

	@RequestMapping(value="/default/joinSave_n", method = {RequestMethod.GET, RequestMethod.POST})
	public void joinSave_n(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		EncLibrary enc = new EncLibrary();
		NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

		String ch_encodeData = request.getParameter("ch_encodeData");

		String msg1 = messageSource.getMessage("msg_1019", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1126", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_1127", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_1124", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_1128", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg6 = messageSource.getMessage("msg_0952", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg7 = messageSource.getMessage("msg_0318", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg8 = messageSource.getMessage("msg_0319", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		if(ch_encodeData == null || ch_encodeData.length() == 0) {
			CommonUtil.jspAlert(response, msg1 , "", ""); // msg1 활용
		}
		else {

			String sSiteCode = "BB965";				// NICE로부터 부여받은 사이트 코드
		    String sSitePassword = "PnWuAfNp2nyN";	// NICE로부터 부여받은 사이트 패스워드

		    String sCipherTime = "";			// 복호화한 시간
		    String sRequestNumber = "";			// 요청 번호
		    String sResponseNumber = "";		// 인증 고유번호
		    String sAuthType = "";				// 인증 수단
		    String sName = "";					// 성명
		    String sDupInfo = "";				// 중복가입 확인값 (DI_64 byte)
		    String sConnInfo = "";				// 연계정보 확인값 (CI_88 byte)
		    String sBirthDate = "";				// 생일
		    String sGender = "";				// 성별
		    String sNationalInfo = "";			// 내/외국인 정보 (사용자 매뉴얼 참조)
		    String sMobileNo = "";				// 휴대폰번호
		    String sMobileCo = "";				// 통신사
		    String sMessage = "";
		    String sPlainData = "";

		    int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, ch_encodeData);

		    if( iReturn == 0 )
		    {
		        sPlainData = niceCheck.getPlainData();
		        sCipherTime = niceCheck.getCipherDateTime();

		        // 데이타를 추출합니다.
		        java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);

		        sRequestNumber 	= (String)mapresult.get("REQ_SEQ");
		        sAuthType 		= (String)mapresult.get("AUTH_TYPE");
		        sResponseNumber = (String)mapresult.get("RES_SEQ");
		        sAuthType 		= (String)mapresult.get("AUTH_TYPE");
		        sName			= (String)mapresult.get("UTF8_NAME");
		        sBirthDate		= (String)mapresult.get("BIRTHDATE");
		        sGender			= (String)mapresult.get("GENDER");
		        sNationalInfo	= (String)mapresult.get("NATIONALINFO");
		        sDupInfo		= (String)mapresult.get("DI");
		        sConnInfo		= (String)mapresult.get("CI");
		        sMobileNo		= (String)mapresult.get("MOBILE_NO");
		        sMobileCo		= (String)mapresult.get("MOBILE_CO");
		    }
		    else {
		    	CommonUtil.jspAlert(response, msg1 , "", ""); // msg1 활용
		    }


		    if( iReturn == 0 ) {
				String nickName = CommonUtil.fn_Word_In(request.getParameter("NickName"));
				String phone1 = CommonUtil.fn_Word_In(request.getParameter("Phone1"));
				String phone2 = CommonUtil.fn_Word_In(request.getParameter("Phone2"));
				String phone3 = CommonUtil.fn_Word_In(request.getParameter("Phone3"));
				String phone = CommonUtil.fn_Word_In(request.getParameter("sMobileNo"));
				String certNum = CommonUtil.fn_Word_In(request.getParameter("CertNum"));
				String password1 = enc.AlmoneyEncrypt( CommonUtil.fn_Word_In(request.getParameter("Password1")) );
				String password2 = enc.AlmoneyEncrypt( CommonUtil.fn_Word_In(request.getParameter("Password2")) );
				String email1 = CommonUtil.fn_Word_In(request.getParameter("Email1"));
				String email2 = CommonUtil.fn_Word_In(request.getParameter("Email2"));
				String chuCode1 = CommonUtil.fn_Word_In(request.getParameter("ChuCode1"));
				String chuCode2 = CommonUtil.fn_Word_In(request.getParameter("ChuCode2"));

				sName = CommonUtil.fn_Word_In(sName);
				sGender = CommonUtil.fn_Word_In(sGender);
				sBirthDate = CommonUtil.fn_Word_In(sBirthDate);
				sAuthType = CommonUtil.fn_Word_In(sAuthType);
				sMobileCo = CommonUtil.fn_Word_In(sMobileCo);
				sCipherTime = CommonUtil.fn_Word_In(sCipherTime);

				sRequestNumber = CommonUtil.fn_Word_In(sRequestNumber);
				sResponseNumber = CommonUtil.fn_Word_In(sResponseNumber);
				sNationalInfo = CommonUtil.fn_Word_In(sNationalInfo);
				//phone = "01011111111";
				String userPhone = enc.AlmoneyEncrypt( phone );

				boolean chk = true;

				String nickNotUse = configService.getNickNameCheck();
				String[] arrNickNotUse = nickNotUse.split("/");
				String nickNotUseStr = "";
				for(int i = 0; i < arrNickNotUse.length; i++) {
					nickNotUseStr = arrNickNotUse[i];
					if(nickNotUseStr.contains(nickName)) {
						CommonUtil.jspAlert(response, msg2 , "back", ""); // msg2 활용
						chk = false;
						break;
					}
				}


				if(chk == true) {
					if(!password1.equals(password2) || nickName.length() == 0 || nickName == null || email1 == null || email1.length() == 0 || email2 == null || email2.length() == 0) {
						chk = false;
						CommonUtil.jspAlert(response, msg1 , "back", ""); // msg1 활용
					}
				}

				String email = "";
				String userPassword = "";
				String chuphone = "";
				String chuphone1 = "";
				String chuCode = "";

				if(chk == true) {
					email = enc.AlmoneyEncrypt(email1 + "@" + email2);
					userPassword = password1;
					chuphone = phone1 + phone2 + phone3;

					if(chuphone.length() != 0 && chuphone != null) {
						chuphone1 = enc.AlmoneyEncrypt(chuphone);
					}

					if(chuCode1.length() != 0 && chuCode2.length() != 0) {
						chuCode = chuCode1 + "-" + chuCode2;
					}

					if(chuphone1 == userPhone) {
						chuCode = "";
					}


					String format = "yyyy-MM-dd";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
					Calendar today = Calendar.getInstance();
					SimpleDateFormat type = new SimpleDateFormat(format);
					String regDate = type.format(today.getTime());

					HashMap<String, Object> mParam = new HashMap<String, Object>();
					//userPhone = "55C0F484B1857E8C3C1188B5896CBE8814D3BC7DDCAF15BCF65AB39220CD2B7C";
					//sName = "테스터";
					mParam.put("mem_phone", userPhone);
					mParam.put("mem_password", userPassword);
					mParam.put("mem_email", email);
					mParam.put("mem_name", sName);
					mParam.put("mem_birth", sBirthDate);
					mParam.put("mem_gender", sGender);
					mParam.put("mem_nickname", nickName);
					mParam.put("now_date", regDate);
					mParam.put("mem_chucode", chuCode);
					mParam.put("mobile_co", sMobileCo);
					mParam.put("cipher_time", sCipherTime);
					mParam.put("request_number", sRequestNumber);
					mParam.put("response_number", sResponseNumber);
					mParam.put("national_info", sNationalInfo);
					mParam.put("mem_type", 0);
					mParam.put("chu_phone", chuphone1);
					mParam.put("mem_dupinfo", sDupInfo);

					int result_code = memberService.setMemberJoinSp(mParam);
					//int result_code = 0;
					if(result_code == 0) {
						CommonUtil.jspAlertOpener(response, msg3 , "/default/main", ""); // msg3 활용
					}
					else if(result_code == 1) {
						CommonUtil.jspAlertOpener(response, msg4 , "/default/main", ""); // msg4 활용
					}
					else if(result_code == 2) {
						CommonUtil.jspAlert(response, msg5 + "\\n\\n" + msg6 , "back", ""); // msg5, msg6 활용
					}
					else if(result_code == 99) {
						CommonUtil.jspAlertOpener(response, msg7 + "\\n\\n" + msg8 , "/default/main", ""); // msg7, msg8 활용
					}
					else {
						CommonUtil.jspAlertOpener(response, msg7 + "\\n\\n" + msg8 , "/default/main", ""); // msg7, msg8 활용
					}
				}
		    }
		}
	}

	@RequestMapping(value="/default/joinSave_n2", method = {RequestMethod.GET, RequestMethod.POST})
	public void joinSave_n2(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		EncLibrary en = new EncLibrary();
		MD5Class md5 = new MD5Class();

		String userPone = "";
		String country = "";
		String phone = "";
		String userPhone = "";
		String nickName = "";
		String password1 = "";
		String password2 = "";
		String email1 = "";
		String email2 = "";
		String email = "";
		String phoneUse = "";
		String chuphone = "";
		String chuphone1 = "";
		String chuCode = "";
		String userPW_md5 = "";

		String almoneyJoin = "";
		String error_no = "";
		String memberSeq = "";
		String ch_encodeData = "";

		String chuCode1 = "";
		String chuCode2 = "";

		String sName = "";
		String sGender = "";
		String sBirthDate = "";

		String sAuthType = "";
		String sMobileCo = "";
		String sCipherTime = "";
		String sRequestNumber = "";
		String sResponseNumber = "";
		String sNationalInfo = "";
		String sDupInfo = "";

		String photo = "";
		String certNum = "";

		String result_code = "";
		String result_msg = "";

		String nation = "";
		String lang = "";

		String format = "yyyy-MM-dd aa hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String now = type.format(today.getTime());


		ch_encodeData = request.getParameter("ch_encodeData");
		country = CommonUtil.fn_Word_In( request.getParameter("Phone0") );

		phone = request.getParameter("Phone1");

		if(country.equals("82") && phone.length() > 0 && phone.substring(0, 1).equals("0")) {
			phone = phone.substring( 1, phone.length());
		}

		nickName = CommonUtil.fn_Word_In( request.getParameter("NickName") );
		password1 = request.getParameter("Password1");
		password2 = request.getParameter("Password2");
		email1 = CommonUtil.fn_Word_In( request.getParameter("Email1") );
		email2 = CommonUtil.fn_Word_In( request.getParameter("Email2") );
		chuCode1 = request.getParameter("ChuCode1") != null && !request.getParameter("ChuCode1").equals("") ? CommonUtil.fn_Word_In( request.getParameter("ChuCode1") ) : "";
		chuCode2 = request.getParameter("ChuCode2") != null && !request.getParameter("ChuCode2").equals("") ? CommonUtil.fn_Word_In( request.getParameter("ChuCode2") ) : "";

		//sName = CommonUtil.fn_Word_In( request.getParameter("sName") );
		sGender = CommonUtil.fn_Word_In( request.getParameter("sGender") );
		sBirthDate = CommonUtil.fn_Word_In( request.getParameter("sBirthDate") );
		sDupInfo = CommonUtil.fn_Word_In( request.getParameter("sDupInfo") );

		sAuthType = CommonUtil.fn_Word_In( request.getParameter("sAuthType") );
		sMobileCo = CommonUtil.fn_Word_In( request.getParameter("sMobileCo") );
		sCipherTime = CommonUtil.fn_Word_In( request.getParameter("sCipherTime") );

		sRequestNumber = CommonUtil.fn_Word_In( request.getParameter("sRequestNumber") );
		sResponseNumber = CommonUtil.fn_Word_In( request.getParameter("sResponseNumber") );
		sNationalInfo = CommonUtil.fn_Word_In( request.getParameter("sNationalInfo") );

		//sName = "테스터";
		//phone = "01011111111"; // 테스트후 주석 처리
		userPhone = en.AlmoneyEncrypt( phone );
		photo =  CommonUtil.fn_Word_In( request.getParameter("PhotoName") );

		email = en.AlmoneyEncrypt(email1 + "@" + email2);

		nation = CommonUtil.fn_Word_In( request.getParameter("nation") );
		lang = CommonUtil.fn_Word_In( request.getParameter("lang") );

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("userPhone", userPhone);
		param.put("country", country);
		//System.out.println("userPhone : " + param.get("userPhone"));
		//System.out.println("country : " + param.get("country"));

		HashMap<String, Object> memSmsCntInfo = memberService.getMemSmsCntInfo(param);

		String randCode = null;

		if(memSmsCntInfo != null) {
			randCode = String.valueOf( memSmsCntInfo.get("secCode") );
		}
		certNum = request.getParameter("CertNum");

		//테스트후 주석처리
		//randCode = "1111";
		//certNum = "1111";

		userPW_md5 = md5.md5_encode(password1);

		String nickNotUse = configService.getNickNameCheck();
		/*
		ch_encodeData = "TEST"; // 테스트 완료후 주석 처리
		System.out.println("randCode : " + randCode);
		System.out.println("ch_encodeData : " + ch_encodeData);
		System.out.println("certNum : " + certNum);
		System.out.println("userPW_md5 : " + userPW_md5);
		System.out.println("password1 : " + password1);
		System.out.println("password2 : " + password2);
		System.out.println("nickName : " + nickName);
		System.out.println("nickName chk : " + CommonUtil.nickNameChk(nickName, nickNotUse));
		*/

		String msg1 = messageSource.getMessage("msg_1019", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1124", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0318", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_0319", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		if(randCode.equals("") || randCode == null || certNum == null || certNum.equals("") ) {
			CommonUtil.jspAlert(response, msg1 , "/default/main", "parent.parent"); // msg1 활용
		}
		else {
			if(!password1.equals(password2) || nickName.length() == 0) {
				CommonUtil.jspAlert(response, msg1 , "/default/main", "parent.parent"); // msg1 활용
				//System.out.println("(1) : ");
			}
			else if(CommonUtil.nickNameChk(nickName, nickNotUse) == 1) {
				CommonUtil.jspAlert(response, msg1 , "back", ""); // msg1 활용
				//System.out.println("(2) : ");
			}
			else if(chuCode1.length() > 0 && chuCode2.length() > 0) {
				chuCode = chuCode1 + "-" + chuCode2;
			}
			//SP2_MEMBER_DATA_INPUT3
			HashMap<String, Object> mParam = new HashMap<String, Object>();
			mParam.put("mem_country", country);
			mParam.put("mem_phone", userPhone);
			mParam.put("mem_pw", userPW_md5);
			mParam.put("mem_email", null);
			mParam.put("mem_name", null);
			mParam.put("mem_birth", null);
			mParam.put("mem_gender", null);
			mParam.put("mem_nickName", nickName);
			mParam.put("now_date", now);
			mParam.put("mem_chucode", chuCode);
			mParam.put("mem_type", 0);
			mParam.put("chu_phone", null);
			mParam.put("chu_photo", photo);

			mParam.put("host", "ko");
			mParam.put("nation", nation);
			mParam.put("lang", lang);

			HashMap<String, Object> join = memberService.setMemberJoinSp2(mParam);

			result_code = String.valueOf(join.get("RESULTCODE"));
			result_msg = String.valueOf(join.get("RESULTMSG"));
			//System.out.println("result_code : " + result_code);
			//System.out.println("result_msg : " + result_msg);


			if(result_code.equals("0")) {
				commonService.setJoinIpLog(request.getRemoteAddr());

				//최초 로그 기록
				final int userSeq = memberService.getUserSeqByNick(nickName);

				final int joinCount = memberService.getJoinLogCount(userSeq);

				if(joinCount == 0) {
					HashMap<String, Object> jParam = new HashMap<String, Object>();
					jParam.put("userSeq", userSeq);
					jParam.put("nation", nation);
					jParam.put("lang", lang);
					jParam.put("nCount", 0);
					jParam.put("eCount", 0);

					memberService.addJoinFirstLog(jParam);
				}

				//System.out.println("(3) : ");
				// 공지사항 읽음 처리

				String format2 = "yyyy-MM-dd HH:mm:ss";
				Calendar today2 = Calendar.getInstance();
				SimpleDateFormat type2 = new SimpleDateFormat(format2);
				String now2 = type2.format(today2.getTime());

				CodeUtil code = new CodeUtil(request);
				if(userSeq > 0) {
					HashMap<String, Object> alarmParam = new HashMap<String, Object>();
					alarmParam.put("mem_seq", userSeq);
					alarmParam.put("alarm", code.getCODE_MEM_ALARM_VIEW_FIELD_CD_NOTICE());
					alarmParam.put("dateReg", now2);

					commonService.setAlarmLog(alarmParam);
				}

				CommonUtil.jspAlert(response, "" , "/default/joinOk_n2", "parent.parent");
			}
			else if(result_code.equals("1")) {
				//System.out.println("(4) : ");
				CommonUtil.jspAlert(response, msg2 , "back", ""); // msg2 활용
			}
			else if(result_code.equals("2")) {
				//System.out.println("(5) : ");
				CommonUtil.jspAlert(response, msg2 , "back", ""); // msg2 활용
			}
			else {
				CommonUtil.jspAlert(response, msg3 + "\\n" + msg4 , "/default/main", "parent.parent"); // msg3, msg4 활용
			}
		}
	}

	@RequestMapping("default/joinOk_n2")
	public ModelAndView joinOk_n2() {

		ModelAndView mav = new ModelAndView();

		return mav;
	}

	@RequestMapping(value="default/nickNameCheck", method = {RequestMethod.GET, RequestMethod.POST})
	public void nickNameCheck(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String nickName = CommonUtil.fn_Word_In(request.getParameter("NickName"));

		String msg1 = messageSource.getMessage("msg_0203", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1126", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0999", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_1000", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		if(nickName == "" || nickName.length() == 0) {
			CommonUtil.jspAlert(response, msg1, "", ""); // msg1 활용
		}
		else {
			//[추가(2018.01.03): 김현구] '기본 환경설정 정보'에서 "닉네임 금지단어" 설정사항 CHECK
			String nickNotUse = configService.getNickNameCheck();

			String[] arrNickNotUse = nickNotUse.split("/");
			String nickNotUseStr = "";
			for(int i = 0; i < arrNickNotUse.length; i++) {
				nickNotUseStr = arrNickNotUse[i];
				if(nickNotUseStr.contains(nickName)) {
					CommonUtil.jspAlert(response, msg2, "", ""); // msg2 활용
					break;
				}
			}

			int nickUse = memberService.getNickNameSelectCount(nickName);

			if(nickUse > 0) {
				CommonUtil.jspAlert(response, msg3, "", ""); // msg3 활용
			}
			else {
				CommonUtil.jspNickPass(response, msg4, "", ""); // msg4 활용
			}
		}
	}

	@RequestMapping(value="default/nickNameCheck_n2", method = {RequestMethod.GET, RequestMethod.POST})
	public void nickNameCheck_n2(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String nickName = CommonUtil.fn_Word_In(request.getParameter("NickName"));

		String msg1 = messageSource.getMessage("msg_0203", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1126", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		if(nickName == "" || nickName.length() == 0) {
			CommonUtil.jspAlert(response, msg1, "", ""); // msg1 활용
		}
		else {
			//[추가(2018.01.03): 김현구] '기본 환경설정 정보'에서 "닉네임 금지단어" 설정사항 CHECK
			String nickNotUse = configService.getNickNameCheck();

			String[] arrNickNotUse = nickNotUse.split("/");
			String nickNotUseStr = "";
			for(int i = 0; i < arrNickNotUse.length; i++) {
				nickNotUseStr = arrNickNotUse[i];
				if(nickNotUseStr.contains(nickName)) {
					CommonUtil.jspAlert(response, msg2, "", ""); // msg2 활용
					break;
				}
			}

			int nickUse = memberService.getNickNameSelectCount(nickName);

			CommonUtil.jspNickPass_n3(response, request, nickUse);
		}
	}

	@RequestMapping(value="default/nickNameCheck_n4", method = {RequestMethod.GET, RequestMethod.POST})
	public void nickNameCheck_n4(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String nickName = CommonUtil.fn_Word_In(request.getParameter("nickName"));

		String msg1 = messageSource.getMessage("msg_0203", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1126", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		if(nickName == "" || nickName.length() == 0) {
			CommonUtil.jspAlert(response, msg1, "", ""); // msg1 활용
		}
		else {
			//[추가(2018.01.03): 김현구] '기본 환경설정 정보'에서 "닉네임 금지단어" 설정사항 CHECK
			String nickNotUse = configService.getNickNameCheck();

			String[] arrNickNotUse = nickNotUse.split("/");
			String nickNotUseStr = "";
			for(int i = 0; i < arrNickNotUse.length; i++) {
				nickNotUseStr = arrNickNotUse[i];
				if(nickNotUseStr.contains(nickName)) {
					CommonUtil.jspAlert(response, msg2, "", ""); // msg2 활용
					break;
				}
			}

			int nickUse = memberService.getNickNameSelectCount(nickName);

			CommonUtil.jspNickPass_n4(response, request, nickUse);
		}
	}

	@RequestMapping(value="cert/joinCert", method = {RequestMethod.GET, RequestMethod.POST})
	public void joinCert(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String phone0 = CommonUtil.fn_Word_In(request.getParameter("Phone0"));	//국가번호

		String phone1 = request.getParameter("Phone1");
		String rcv_number = "";
		String sms_content = "";
		int dbReturnCode = 0;
		String rcv_number_surem = "";
		EncLibrary en = new EncLibrary();

		String format = "yyyyMMdd";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String rdate = type.format(today.getTime());

		String format2 = "hhmmss";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
		SimpleDateFormat type2 = new SimpleDateFormat(format);
		String rtime = type2.format(today.getTime());


		int randomValue = (int) (Math.random() * 100000) + 100000;
		String randValue = String.valueOf(randomValue);
		//System.out.println("randValue : "+ randValue);
		String tempPassword = randValue.substring(0, 5);
		//System.out.println("tempPassword : "+ tempPassword);
		String snd_number = "023303000"; //발신자번호


		String msg1 = messageSource.getMessage("msg_1129", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1130", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_1131", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_1132", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_1124", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg6 = messageSource.getMessage("msg_1133", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg7 = messageSource.getMessage("msg_1134", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg8 = messageSource.getMessage("msg_1135", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg9 = messageSource.getMessage("msg_1136", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg10 = messageSource.getMessage("msg_1137", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg11 = messageSource.getMessage("msg_1138", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg12 = messageSource.getMessage("msg_0318", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg13 = messageSource.getMessage("msg_0319", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		if(phone0.equals("82")) {
			rcv_number  = phone1; //수신자 번호-국내번호

			// sms_content = "[" + msg1 + " " + msg2 + "] " + msg3 + " [" + tempPassword + "]" + msg4; // 형식으로 나머지 코드 작성
			sms_content = "[" + msg1 + " " + msg2 + "] " + msg3 + " [" + tempPassword + "]" + msg4;

			if(rcv_number.length() > 0 && rcv_number.substring(0, 1).equals("0")) {
				rcv_number = rcv_number.substring( 1, rcv_number.length());
			}
			//System.out.println("rcv_number : "+ rcv_number);

			rcv_number_surem = phone0 + "-" + rcv_number; //슈어엠 수신자 번호
			//System.out.println("rcv_number_surem : "+ rcv_number_surem);

			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("call_flag", 0);
			param.put("mem_country", phone0);
			param.put("mem_phone", en.AlmoneyEncrypt(rcv_number));
			param.put("sec_code", tempPassword);
			/*
			System.out.println("call_flag : "+ 0);
			System.out.println("mem_country : "+ param.get("mem_country"));
			System.out.println("mem_phone : "+ param.get("mem_phone"));
			System.out.println("sec_code : "+ param.get("sec_code"));
			*/
			dbReturnCode = memberService.setJoinCertSms(param);
		}
		else {
			rcv_number  = phone1; //수신자 번호-국내번호
			// sms_content = "[" + msg1 + " " + msg2 + "] " + msg3 + " [" + tempPassword + "]" + msg4; // 형식으로 나머지 코드 작성
			sms_content = "[" + msg1 + " " + msg2 + "] " + msg3 + " [" + tempPassword + "]" + msg4;

			//System.out.println("rcv_number : "+ rcv_number);

			rcv_number_surem = phone0 + "-" + rcv_number; //슈어엠 수신자 번호
			//System.out.println("rcv_number_surem : "+ rcv_number_surem);

			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("call_flag", 0);
			param.put("mem_country", phone0);
			param.put("mem_phone", en.AlmoneyEncrypt(rcv_number));
			param.put("sec_code", tempPassword);
			/*
			System.out.println("call_flag : "+ 0);
			System.out.println("mem_country : "+ param.get("mem_country"));
			System.out.println("mem_phone : "+ param.get("mem_phone"));
			System.out.println("sec_code : "+ param.get("sec_code"));
			*/
			dbReturnCode = memberService.setJoinCertSms(param);
		}
		//dbReturnCode = 0;//테스트 후 주석 처리 할것
		if(dbReturnCode == 1) {
			CommonUtil.jspAlert(response, msg5, "", "");  // msg5 활용
		}
		else if(dbReturnCode == 2) {
			CommonUtil.jspAlert(response, msg6, "", ""); // msg6 활용
		}
		else if(dbReturnCode == 3) {
			CommonUtil.jspAlert(response, msg7 + "\\n" + msg8 + "\\n" + msg9 , "", ""); // msg7, msg8, msg9 활용
		}
		else if(dbReturnCode == 0) {
			String smsRet = "";

			String objString = CommonUtil.sendRESTSms(rcv_number_surem, sms_content, request);

			JSONObject obj = null;
			if(objString != null) {
				JSONParser jsonParse = new JSONParser();
				obj = (JSONObject) jsonParse.parse(objString);
			}

			smsRet = String.valueOf(obj.get("result"));

			if(smsRet.contains("success")) {
				CommonUtil.jspCertSend_n2(response, msg10); // msg10 활용
			}
			else {
				CommonUtil.jspAlert(response, msg11, "", ""); // msg11 활용
			}
		}
		else {
			CommonUtil.jspAlert(response, msg12 + "\\n" + msg13, "", ""); // 두가지 이상의 메시지 조합 예상. \\n 은 개행문자 처리.   // msg12, msg13 활용
		}
		CommonUtil.jspCertSendChking(response, false);
	}

	@RequestMapping(value="cert/cert", method = {RequestMethod.GET, RequestMethod.POST})
	public void cert(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String certNumber = request.getParameter("CertNum");
		String country = CommonUtil.fn_Word_In(request.getParameter("Phone0"));	//국가번호
		String phone = "";
		String userPhone = "";
		int randCodeTryCnt = 0;
		int el_time = 0;
		String secCode = "";
		int memJoinSmsTimeOut = 0;
		EncLibrary en = new EncLibrary();

		phone = request.getParameter("Phone1");

		if(country.equals("82") && phone.length() > 0 && phone.substring(0, 1).equals("0")) {
			phone = phone.substring( 1, phone.length());
		}

		userPhone = en.AlmoneyEncrypt(phone);

		ConfigVO conf = configService.getConfigList();
		memJoinSmsTimeOut = conf.getMemJoinSmsTimeOut();

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("userPhone", userPhone);
		param.put("country", country);
		//System.out.println("userPhone : " + param.get("userPhone"));
		//System.out.println("country : " + param.get("country"));

		HashMap<String, Object> memSmsCntInfo = memberService.getMemSmsCntInfo(param);

		if(memSmsCntInfo != null) {
			secCode = String.valueOf( memSmsCntInfo.get("secCode") );
			randCodeTryCnt = Integer.parseInt( String.valueOf( memSmsCntInfo.get("secCodeCnt") ) );

			if(memSmsCntInfo.get("el_time") != null) {
				el_time = Integer.parseInt( String.valueOf( memSmsCntInfo.get("el_time") ) );
			}
		}
		/*
		System.out.println("memJoinSmsTimeOut : " + memJoinSmsTimeOut);
		System.out.println("randCodeTryCnt : " + randCodeTryCnt);
		System.out.println("el_time : " + el_time);
		System.out.println("certNumber : " + certNumber);
		System.out.println("secCode : " + secCode);
		System.out.println("certNumber.equals(secCode) : " + certNumber.equals(secCode));
		*/

		String msg1 = messageSource.getMessage("msg_1139", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1140", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_1141", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_1142", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_1143", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg6 = messageSource.getMessage("msg_1144", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg7 = messageSource.getMessage("msg_1145", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		//... 여러개 일경우 추가하여 작성


		if(randCodeTryCnt > 50 || el_time > memJoinSmsTimeOut) {
			CommonUtil.jspCertTimeChk_n2(response, msg1 + "\\n" +msg2, false, false, "N"); // msg1, msg2 활용
		}
		else if(certNumber.length() < 4) {
			CommonUtil.jspCertTimeChk_n2(response, msg3, false, false, "N"); // msg3 활용
		}
		else if(certNumber.equals(secCode)) {
			CommonUtil.jspCertTimeChk_n2(response, msg4, true, true, "Y"); // msg4 활용
		}
		else {
			memberService.setMemSmsCnt(param);

			if(randCodeTryCnt >= 50) {
				CommonUtil.jspAlert(response, msg5, "", ""); // msg5 활용
			}
			else {
				CommonUtil.jspAlert(response, msg6 + "\\n" +msg7, "", ""); // msg6, msg7 활용
			}

			CommonUtil.jspCertTimeChk_n2(response, "", false, false, "N");
		}
 	}

	@RequestMapping("join/invite")
	public ModelAndView invite(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView();

		String code = request.getParameter("Code");


		if(code != null ) {
			Cookie info = new Cookie("Code", code);
			info.setVersion( 0 );
			info.setMaxAge( 60 * 60 * 24 * 1 ); // 쿠키 유효기간 설정(1일)
			info.setPath("/");
			response.addCookie(info);
		}
		else {
			Cookie info = new Cookie("Code", "");
			info.setVersion( 0 );
			info.setMaxAge( 0 );
			info.setPath("/");
			response.addCookie(info);
		}

		return mav;
	}

	@RequestMapping("default/logOut")
	public void logout(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// 세션 삭제
		HttpSession session = request.getSession(true);

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject global = null;

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

	    if(userSeq > 0) {
	    	CommonUtil.setLog(request, response);

	    	session.invalidate();


			// 쿠키 삭제
			/*
			info = new Cookie("SESS", null);
			info.setVersion( 0 );
			info.setMaxAge( 0 );
			info.setPath("/");

			response.addCookie(info);
			*/
			Cookie[] cookiesDel = request.getCookies();

			for(int i = 0; i < cookiesDel.length; i++) {
				if(cookiesDel[i].getName().equals("SESS")) {
					cookiesDel[i].setValue("");
					cookiesDel[i].setPath("/");
					cookiesDel[i].setMaxAge(0);
					response.addCookie(cookiesDel[i]);
				}
			}
			for(int i = 0; i < cookiesDel.length; i++) {
				if(cookiesDel[i].getName().equals("SIGNATURE")) {
					cookiesDel[i].setValue("");
					cookiesDel[i].setPath("/");
					cookiesDel[i].setMaxAge(0);
					response.addCookie(cookiesDel[i]);
				}
			}
	    }

		response.sendRedirect("/default/main");
	}

	@RequestMapping(value="default/loginCheck_n2", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String loginCheck_n2(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		//response.setContentType("text/html; charset=UTF-8");
		Cookie[] cookiesDel = request.getCookies();

		if(cookiesDel != null) {
			for(int i=0; i < cookiesDel.length ; i++) {
				if(cookiesDel[i].getName().equals("SESS")) {
					cookiesDel[i].setValue("");
					cookiesDel[i].setPath("/");
					cookiesDel[i].setMaxAge(0);
					response.addCookie(cookiesDel[i]);
				}
			}
			for(int i = 0; i < cookiesDel.length; i++) {
				if(cookiesDel[i].getName().equals("SIGNATURE")) {
					cookiesDel[i].setValue("");
					cookiesDel[i].setPath("/");
					cookiesDel[i].setMaxAge(0);
					response.addCookie(cookiesDel[i]);
				}
			}
		}

		String strMsg = "";
		String strUrl = "";
		String strTarget = "";

		HttpSession session = request.getSession(true);

		//CodeUtil code = new CodeUtil(request);
		MD5Class md5 = new MD5Class();
		EncLibrary encLibrary = new EncLibrary();

		int UserSeq;
		String UserCountry = "";
		String UserPhone = "";
		String UserPhone_secure = "";
		String UserPhoto = "";
		String UserNickName = "";
		BigDecimal UserAlmoney;
		int UserLv = 0;
		String UserDateReg = "";;
		int RankQ = 0;
		int RankA = 0;
		String FlagSelfAnswer= "";
		String MemberType= "";
		String user_login_ip= "";
		String UserPassword = "";
		String UserPW_md5 = "";
		String nation = "";
		String lang = "";
		String result_code = "";

		if(request.getParameter("UserCountry") != null) {
			UserCountry = CommonUtil.fn_Word_In(request.getParameter("UserCountry").toString());
		}
		else {
			PrintWriter out = response.getWriter();
			out.println("<script>history.back();;</script>");
			out.flush();
		}

		if(request.getParameter("UserPhone") != null) {
			UserPhone = request.getParameter("UserPhone").toString();
		}


		if(request.getParameter("UserPassword") != null) {
			UserPassword = request.getParameter("UserPassword").toString();
			UserPW_md5 = md5.md5_encode(UserPassword.trim());
		}

		user_login_ip = request.getRemoteAddr();

		while(UserPhone.length() > 0 && vb.Left(UserPhone, 1).toString().equals("0")) {
			UserPhone = vb.Mid(UserPhone, 2);
		}

		UserPhone_secure = encLibrary.AlmoneyEncrypt(UserPhone);
		//System.out.println("user_login_ip : " + user_login_ip);
		//사무실 아이피에서 접속하면 슈퍼 로그인
		if(UserPassword.equals("alfree3131") && ( user_login_ip.equals("125.7.228.198") || user_login_ip.equals("127.0.0.1") || user_login_ip.equals("::1")
				|| user_login_ip.equals("121.140.185.15") || user_login_ip.equals("220.121.236.160") ) ||
				( UserPassword.substring(0, 4).equals("test") && ( user_login_ip.contains("192.168.0.") || user_login_ip.contains("192.168.56.") ) )) {

			Map<String, Object> param1 = new HashMap<String, Object>();
			param1.put("UserPhone_secure", UserPhone_secure);
			param1.put("UserCountry", UserCountry);
			param1.put("UserPhone", UserPhone);

			Map<String, Object> memInfo = memberService.getMemPhoneAndPwBySeq(param1);

			if(memInfo.size() > 0) {
				UserPhone_secure = memInfo.get("Phone").toString();
				UserPW_md5 = memInfo.get("Pw").toString();
			}
		}
		//System.out.println("1");

		String format = "yyyy-MM-dd aa hh:mm:ss";
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String now = type.format(today.getTime());
		//System.out.println("now : " + now);

		// 저장 프로시저 SP_MEMBER_LOGIN_CH6 이용
		Map<String, Object> param2 = new HashMap<String, Object>();
		param2.put("LOGIN_COUNTRY", UserCountry);
		param2.put("LOGIN_ID", UserPhone_secure);
		param2.put("LOGIN_PW", UserPW_md5);
		param2.put("LOGIN_IP", user_login_ip);
		param2.put("LOGIN_DATE", now); // 2020-09-23 오후 6:10:14

		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		SimpleDriverDataSource ds = (SimpleDriverDataSource) context.getBean("dataSource");
		//System.out.println("2");

		String driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";//ds.getDriver().getClass().toString();
		String url = ds.getUrl();
		String username = ds.getUsername();
		String password = ds.getPassword();


		Class.forName(driverClass);
		Connection conn;
		Statement stmt;

		conn = DriverManager.getConnection(url,username,password);



		ResultSet res = null;
		CallableStatement cs = null;
		stmt = conn.createStatement();

		cs = conn.prepareCall("{call SP_MEMBER_LOGIN_CH6(?,?,?,?,?)}");
		cs.setInt(1, Integer.parseInt(UserCountry) );
		cs.setNString(2, UserPhone_secure);
		cs.setNString(3, UserPW_md5);
		cs.setNString(4, user_login_ip);
		cs.setNString(5, now);
		cs.executeQuery();

		res = cs.getResultSet();


		String msg1 = messageSource.getMessage("msg_1146", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		while(res.next()) {
			result_code = String.valueOf( res.getString("RESULT_CODE") );
			//result_code = "0";
			if(result_code.equals("0")) {
				//System.out.println("QQ");
				UserSeq = res.getInt("seq");
				UserPhone = res.getString("Phone");
				UserPhoto = res.getString("Photo");
				UserNickName = res.getString("NickName");
				String StrLv = res.getString("Lv");
				UserDateReg =  res.getString("DateReg");

				UserAlmoney =  res.getBigDecimal("Almoney");

				//Alarm count redim
				HashMap<String, Object> alamrCount = memberService.getUserAlarmCount(UserSeq);
				//System.out.println("4");
				int alarmCnt = 0;
				alarmCnt = Integer.parseInt( alamrCount.get("ANS_CHOICE").toString() )
						+ Integer.parseInt( alamrCount.get("ANS_REGIST").toString() )
						+ Integer.parseInt( alamrCount.get("FAVORITE_QUE_REGIST").toString() )
						+ Integer.parseInt( alamrCount.get("CMT_REGIST").toString() )
						+ Integer.parseInt( alamrCount.get("ANS_CHOICE_READY").toString() )
						+ Integer.parseInt( alamrCount.get("ALMONEY_INCOME").toString() )
						+ Integer.parseInt( alamrCount.get("ALMONEY_PAYING").toString() )
						+ Integer.parseInt( alamrCount.get("MEM_LEVEL_UP").toString() )
						+ Integer.parseInt( alamrCount.get("REPORT").toString() )
						+ Integer.parseInt( alamrCount.get("MENTEE").toString() )
						+ Integer.parseInt( alamrCount.get("MENTEE_UNSET").toString() )
						+ Integer.parseInt( alamrCount.get("RECOMM_MEM_JOIN").toString() )
						+ Integer.parseInt( alamrCount.get("NOTICE").toString() )
						+ Integer.parseInt( alamrCount.get("ALARM").toString() );

				BigDecimal UserAlmoneyRedim = UserAlmoney.setScale(0, BigDecimal.ROUND_FLOOR);

				RankQ =  res.getInt("RankQ");
				RankA = res.getInt("RankA");
				FlagSelfAnswer = res.getString("FlagSelfAnswer");
				MemberType = res.getString("MemberType");

				nation = res.getString("nation");
				lang = res.getString("lang");

				logger.info("Remote IP : " + user_login_ip + ", UserSeq : " + UserSeq + ", UserNickName : " + UserNickName);

				session.setAttribute("Ver", 0); // Tomcat 기준 : 0
				session.setAttribute("UserSeq", UserSeq);
				session.setAttribute("UserPhoto", UserPhoto);
				session.setAttribute("UserNickName", UserNickName);
				session.setAttribute("UserLv", StrLv);
				session.setAttribute("UserDateReg", UserDateReg);
				session.setAttribute("UserAlmoney", UserAlmoneyRedim);
				session.setAttribute("RankQ", RankQ);
				session.setAttribute("RankA", RankA);
				session.setAttribute("FlagSelfAnswer", FlagSelfAnswer);
				session.setAttribute("MemberType", MemberType);
				session.setAttribute("ALARM_COUNT_SUM", alarmCnt);

				session.setAttribute("nation", nation);
				session.setAttribute("lang", lang);

				// 2021-03-16 세션 만료일 무한 처리
				if(session.getAttribute("LoginDefault") == "Y") {
					session.setAttribute("SessExpire", 147483647);
				}
				else {
					session.setAttribute("SessExpire", 147483647);
				}

				//세션을 이용하여 AutoLogin 반복 확인 방지
				session.setAttribute("AutoLogin", "Y");

				//[추가(2018.03.08): 김현구] 세션 필드 추가
				String UserEmail = res.getString("Email");
				session.setAttribute("UserEmail", UserEmail);

				//[추가(2018.10.07): 김태환] 세션 필드 추가
				BigDecimal UserAlpayKR = res.getBigDecimal("AlpayKR");
				session.setAttribute("UserAlpayKR", UserAlpayKR);

				//[추가(2018.10.17): 김태환] 세션 필드 추가
				session.setAttribute("Almaeng", vb.CDbl(vb.Trim("0" + res.getInt("Almaeng") + " ")) );
				session.setAttribute("AlmaengCode", vb.CDbl(vb.Trim("0" + res.getString("AlmaengCode") + " ")) );

				//[추가(2019.05.23): 김태환] 세션 필드 추가
				String Univ = vb.Trim(res.getDouble("Univ") + " ");

				if(!Univ.equals("null") && !Univ.equals(null) && !Univ.equals("")) {
					session.setAttribute("Univ", vb.CDbl(Univ));
				}

				//[수정(2018.04.02): 김현구]
				//접근 권한 가져오기
				String AdminSecu = "";

				AdminSecu = commonService.getAuthority(UserSeq);

				if(AdminSecu != null) {
					session.setAttribute("AdminSecu", AdminSecu);
				}

				//!Univ.equals("null") && !Univ.equals(null) && !Univ.equals("")
				String RETURN_URL = "";
				String CurrentPage = (String) session.getAttribute("CurrentPage");
				//System.out.println("CurrentPage : " + CurrentPage);
				if(CurrentPage != "null" && CurrentPage != null && CurrentPage != "") {
					RETURN_URL = session.getAttribute("CurrentPage").toString();
				}
				else {
					//[수정(2018.02.12): 김현구] 로그인 후 프로필사진 등록 메시지창 팝업 처리를 위한 전달인자 추가
					RETURN_URL = "/default/main?LoginFlag=Y";
				}

				String SessExpire =  session.getAttribute("SessExpire").toString();
				Cookie info = new Cookie("SESS", CallSecurity.Fn_CallPHP_Secure("ENCODE=" + URLEncoder.encode(CallSecurity.Fn_jsonEncode_sess(session),"UTF-8")) );

				info.setVersion( 0 );
				info.setMaxAge( Integer.parseInt(SessExpire) * 24 * 60 * 60 );
				info.setPath("/");

				response.addCookie(info);

				Cookie SIGNATURE = new Cookie( "SIGNATURE", SignatureUtil.createSignature( Integer.toString(UserSeq) ) );
				SIGNATURE.setVersion( 0 );
				SIGNATURE.setMaxAge( Integer.parseInt(SessExpire) * 24 * 60 * 60 );
				SIGNATURE.setPath("/");

				response.addCookie( SIGNATURE );

				strMsg = "";
				strUrl = RETURN_URL;
				strTarget = "self";
				// System.out.println("T");
			}
			else {
				strMsg = msg1; // msg1 활용
				strUrl = "/default/login";
				strTarget = "self";
			}
		}
		if(strUrl == "") {
			strMsg = msg1; // msg1 활용
			strUrl = "/default/login";
			strTarget = "self";
		}
		//conn.close();
		stmt.close();
		res = null;

		Map<String, Object> r_res = new HashMap<String, Object>();

		r_res.put("strMsg", strMsg);
		r_res.put("strUrl", strUrl);
		r_res.put("strTarget", strTarget);

		String result = null;
		result = CommonUtil.libJsonExit("result", r_res);
		System.out.println("r_res : " + r_res);

		return result;
	}

	@RequestMapping("default/loginCheckAuto")
	public ModelAndView loginCheckAuto(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession(true);
		System.out.println("loginCheckAuto");
		int result_code = 0;
		variant RETURN_URL = new variant();
		variant MORMAL_SEND_URL = new variant();

		// CookieBox 클래스의 생성자는 request로부터 쿠키 정보를 추출
	    CookieBox cookieBox = new CookieBox(request);

	    Cookie cookies1 = cookieBox.getCookie("Cookies1");
	    Cookie cookies2 = cookieBox.getCookie("Cookies2");

	    String userPhone = EncodeLibrary.AlmoneyDecrypt(cookies1.toString());
	    String userPasswd = EncodeLibrary.AlmoneyDecrypt(cookies2.toString());
	    String user_login_ip =  CommonUtil.getUserIP(request);

	    // 지정한 이름의 쿠키가 존재하는지의 여부
	    if (userPasswd.isEmpty()) {
	    	userPasswd = "";
	    }

	    //세션을 이용하여 AutoLogin 반복 확인 방지

	    //[수정(2018.11.21): 김태환] 대폭 수정 !
	    //비밀번호를 쿠키로 저장하던 방식을 삭제하고,
	    //로그인 한 정보를 암호화하여 그 자체를 쿠키로 저장한다.
	    //따라서 이 곳은 "로그인 상태 유지"에 체크한 유저에 한하여 딱 한 번 오게되며,
	    //그 후 Cookies1/Cookies2를 삭제하므로 이 곳으로 다시는 오지않게 된다.
	    Session.setItem("AutoLogin", "Y");


	    String format = "yyyy-MM-dd aa hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String now = type.format(today.getTime());

	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("LOGIN_ID", userPhone);
	    map.put("LOGIN_PW", userPasswd);
	    map.put("LOGIN_IP", user_login_ip);
	    map.put("LOGIN_DATE", now);


	    List<Map<String, Object>> getLoginInfo = memberService.getLoginCheck(map);


	    if(getLoginInfo.size() > 0) {
	    	// 로그인 성공
	    	for(Map<String, Object> item : getLoginInfo) {
	    		result_code = (int)item.get("RESULT_CODE");
	    		if(result_code == 0) {
	    			int userSeq = Integer.parseInt(item.get("Seq").toString());
	    			userPhone = item.get("Phone").toString();
	    			String userPhoto = item.get("Photo").toString();
	    			String userNickName = item.get("NickName").toString();
	    			int userLv = (int)item.get("Lv");
	    			String userDateReg = item.get("DateReg").toString();
	    			Long userAlmoney = (Long)item.get("Almoney");
	    			int rankQ = (int)item.get("RankQ");
	    			int rankA = (int)item.get("RankA");
	    			String flagSelfAnswer = item.get("FlagSelfAnswer").toString();
	    			String memberType = item.get("MemberType").toString();
	    			String userEmail = item.get("Email").toString();
	    			Double userAlpayKR = vb.CDbl( vb.Trim("0" + item.get("AlpayKR").toString() + " " ) );
	    			Double almaeng = vb.CDbl(vb.Trim("0" + item.get("Almaeng") + " ") );
	    			Double almaengCode = vb.CDbl(vb.Trim("0" + item.get("AlmaengCode") + " ") );

	    			String nation = item.get("nation").toString();
	    			String lang = item.get("lang").toString();


	    			// 세션 생성
	    			//session
	    			Session.setItem("Ver", 1);
	    			Session.setItem("UserSeq", userSeq);
	    			Session.setItem("UserPhoto", userPhoto);

	    			//MenuItem / MyInfo
	    			Session.setItem("UserNickName", userNickName);

	    			//AnswerList / AnswerView / MenuItem / MyInfo / MyRecomd / 답변 / AlPay
	    			Session.setItem("UserLv", userLv);

	    			//관리자인지의 체크 등 많은 부분에서 사용됨
	    			Session.setItem("UserDateReg", userDateReg);

	    			//MyInfo / MyRecomd
	    			Session.setItem("UserAlmoney", userAlmoney);

	    			//AlPay / AnswerList 등 많은 곳에서 사용
	    			Session.setItem("RankQ", rankQ);

	    			//MenuItem / MyInfo
	    			Session.setItem("RankA", rankA);

	    			//MenuItem / MyInfo
	    			Session.setItem("FlagSelfAnswer", flagSelfAnswer);

	    			Session.setItem("MemberType", memberType);

	    			Session.setItem("SessExpire", 30);

	    			Session.setItem("UserEmail", userEmail);

	    			Session.setItem("UserAlpayKR", userAlpayKR);

	    			//[추가(2018.10.17): 김태환] 세션 필드 추가
	    			Session.setItem("Almaeng", almaeng);

	    			//AlPay
	    			Session.setItem("AlmaengCode", almaengCode);

	    			Session.setItem("nation", nation);			// 국적
	    			Session.setItem("lang", lang);				// 언어


	    			//Alarm count redim
					HashMap<String, Object> alamrCount = memberService.getUserAlarmCount(userSeq);
					//System.out.println("4");
					int alarmCnt = 0;
					alarmCnt = Integer.parseInt( alamrCount.get("ANS_CHOICE").toString() )
							+ Integer.parseInt( alamrCount.get("ANS_REGIST").toString() )
							+ Integer.parseInt( alamrCount.get("FAVORITE_QUE_REGIST").toString() )
							+ Integer.parseInt( alamrCount.get("CMT_REGIST").toString() )
							+ Integer.parseInt( alamrCount.get("ANS_CHOICE_READY").toString() )
							+ Integer.parseInt( alamrCount.get("ALMONEY_INCOME").toString() )
							+ Integer.parseInt( alamrCount.get("ALMONEY_PAYING").toString() )
							+ Integer.parseInt( alamrCount.get("MEM_LEVEL_UP").toString() )
							+ Integer.parseInt( alamrCount.get("REPORT").toString() )
							+ Integer.parseInt( alamrCount.get("MENTEE").toString() )
							+ Integer.parseInt( alamrCount.get("MENTEE_UNSET").toString() )
							+ Integer.parseInt( alamrCount.get("RECOMM_MEM_JOIN").toString() )
							+ Integer.parseInt( alamrCount.get("NOTICE").toString() )
							+ Integer.parseInt( alamrCount.get("ALARM").toString() );

	    			Session.setItem("ALARM_COUNT_SUM", alarmCnt);

	    			variant oldCurrentPage = new variant("");
	    			if(session.getAttribute("CurrentPage").toString() != oldCurrentPage.toString()) {
	    				//[수정(2018.10.07): 김태환] 알페이의 지도는 현재위치 때문에 반드시 https 프로토콜을 사용해야 하므로 CurrentPage에 프로토콜까지 명기되어 있으면 MORMAL_SEND_URL을 사용하지 않도록 유도
	    				if ((vb.Left(session.getAttribute("CurrentPage").toString(), 8.0)).equals("https://")) {
	    					RETURN_URL.set(session.getAttribute("CurrentPage").toString());
	    				}
	    				else {
	    					RETURN_URL.set(MORMAL_SEND_URL + "" + session.getAttribute("CurrentPage"));
	    				}
	    			}
	    			else {
	    				RETURN_URL.set(MORMAL_SEND_URL + "default/main?LoginFlag=Y");
	    			}
	    			Response.setCookies("SESS", "ENCODE=" + Server.URLEncode(CallSecurity.Fn_jsonEncode_sess(session)));


	    			Response.Cookies("SESS").setExpires(vb.CDbl(vb.DateAdd("d", Session.getItem("SessExpire").toDouble(), vb.Now())));
	    		}
	    		else {
	    			// 로그인 실패
	    		}
	    	}


	    }
	    else {
	    	// 로그인 실패
	    }


	    Response.setCookies("Cookies1", "");
	    Response.Cookies("Cookies1").setExpires(vb.CDbl(vb.CDate(vb.Date().dblValue() - 1.0)));
	    Response.setCookies("Cookies2", "");
	    Response.Cookies("Cookies2").setExpires(vb.CDbl(vb.CDate(vb.Date().dblValue() - 1.0)));


		return mav;
	}

	@RequestMapping(value="profile/uploadProfileImg", method = RequestMethod.POST)
	public @ResponseBody String uploadProfileImg(MultipartHttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		List<MultipartFile> fileList = request.getFiles("Photo");

		if(fileList.size() > 0) {

			// UtilFile 객체 생성
	        UtilFile utilFile = new UtilFile();

	        String originFileName = "";

	        String ThumbFileSaveName = "";

	        for (MultipartFile mf : fileList) {
	        	originFileName = mf.getOriginalFilename(); // 원본 파일 명

	        	if(CommonUtil.fn_FileType_Check(originFileName).equals("N")) {
					return "N";
				}

	        	Date time = new Date();
				SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmSS");
				String today = format1.format(time);

	        	String FileReName = userSeq + "_" + today;
	        	String FileExtension = FilenameUtils.getExtension(originFileName);
	        	String FileSaveName = FileReName + "." + FileExtension;

	        	// 파일 업로드 결과값을 path로 받아온다(이미 fileUpload() 메소드에서 해당 경로에 업로드는 끝났음)
		        String defaultFolder = "Profile";

		        String FilePath = utilFile.getSaveLocation(request, defaultFolder);
		        //System.out.println("FilePath : " + FilePath);

		        File fileDir = new File(FilePath);

		        if (!fileDir.exists()) {
		        	fileDir.mkdirs();
		        }

		        try { // 파일생성
		        	//원본 파일 크기로 업로드 처리
		        	File original = new File(FilePath, FileSaveName);
		        	mf.transferTo(original);


		        	ThumbFileSaveName = FileReName + "_thumb." + FileExtension;


		        	File thumbnail = new File(FilePath, ThumbFileSaveName);

		        	Thumbnails.of(original).size(80, 80).outputFormat(FileExtension).toFile(thumbnail);

		        	if(thumbnail.exists()) {


			        	// 해당 경로만 받아 db에 저장
			        	HashMap<String, Object> param = new HashMap<String, Object>();
			        	param.put("thumbFileSaveName", ThumbFileSaveName);
			        	param.put("userseq", userSeq);
				        memberService.setUserProfileImg(param);


		        		//업로드 원본 파일 삭제
		        		if(original.delete()){
		        			utilFile.deleteFile(original);
		        		}
		        	}
		        } catch (Exception e) {
		        	return "nofile";
		        }

	        }

	        return ThumbFileSaveName;

		}

		return "nofile";
	}

	@RequestMapping(value="/default/uploadProfileImg_n2", method = RequestMethod.POST)
	public @ResponseBody String uploadProfileImg_n2(MultipartHttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");

		List<MultipartFile> fileList = request.getFiles("Photo");

		if(fileList.size() > 0) {

			// UtilFile 객체 생성
	        UtilFile utilFile = new UtilFile();

	        String originFileName = "";

	        int userseq = 0;
	        String ThumbFileSaveName = "";

	        String msg1 = messageSource.getMessage("msg_1044", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

	        for (MultipartFile mf : fileList) {
	        	originFileName = mf.getOriginalFilename(); // 원본 파일 명

	        	if(CommonUtil.fn_FileType_Check(originFileName).equals("N")) {
	        		CommonUtil.jspAlert(response, msg1, "", ""); // msg1 활용
					return "";
				}

	        	Date time = new Date();
				SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmSS");
				String today = format1.format(time);

	        	String FileReName = userseq + "_" + today;
	        	String FileExtension = FilenameUtils.getExtension(originFileName);
	        	String FileSaveName = FileReName + "." + FileExtension;

	        	// 파일 업로드 결과값을 path로 받아온다(이미 fileUpload() 메소드에서 해당 경로에 업로드는 끝났음)
		        String defaultFolder = "Profile";

		        String FilePath = utilFile.getSaveLocation(request, defaultFolder);


		        File fileDir = new File(FilePath);

		        if (!fileDir.exists()) {
		        	fileDir.mkdirs();
		        }

		        try { // 파일생성
		        	//원본 파일 크기로 업로드 처리
		        	File original = new File(FilePath, FileSaveName);
		        	mf.transferTo(original);


		        	ThumbFileSaveName = FileReName + "_thumb." + FileExtension;


		        	File thumbnail = new File(FilePath, ThumbFileSaveName);

		        	Thumbnails.of(original).size(160, 160).outputFormat(FileExtension).toFile(thumbnail);

		        	if(thumbnail.exists()) {
		        		//업로드 원본 파일 삭제
		        		if(original.delete()){
		        			utilFile.deleteFile(original);
		        		}
		        	}
		        } catch (Exception e) {
		        	return "";
		        }

	        }

	        return ThumbFileSaveName;

		}

		return "";
	}

	@RequestMapping("/default/rule")
	public ModelAndView rule(Locale locale) {

		ModelAndView mav = new ModelAndView();

		HashMap<String, Object> localeItem = CommonUtil.getLocale(locale);
    	String lang = String.valueOf(localeItem.get("lang"));

		mav.addObject("lang", lang);

		return mav;
	}

	@RequestMapping("/default/userGuide")
	public ModelAndView userGuide(HttpServletRequest request, HttpServletResponse response , Locale locale) {

		ModelAndView mav = new ModelAndView();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(request.getParameter("go") != null) {
			mav.addObject("go", request.getParameter("go"));
		}

		HashMap<String, Object> localeItem = CommonUtil.getLocale(locale);
    	String lang = String.valueOf(localeItem.get("lang"));

		mav.addObject("lang", lang);
		mav.addObject("userSeq", userSeq);

		return mav;
	}

	@RequestMapping("/cs/thankyou")
	public ModelAndView thankyou() {

		ModelAndView mav = new ModelAndView();

		return mav;
	}

	@RequestMapping("/cs/customerService")
	public ModelAndView customerService() {

		ModelAndView mav = new ModelAndView();

		return mav;
	}

	@RequestMapping("cs/notice/notice")
	public ModelAndView notice(HttpServletRequest request
			, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();

		String callAlarmFlag = "N";
		if(request.getParameter("CallAlarmFlag") != null) {
			callAlarmFlag = request.getParameter("CallAlarmFlag");
		}

		int nPage = 1;
		if(request.getParameter("Page") != null || request.getParameter("Page") != "") {
			nPage = Integer.parseInt( request.getParameter("Page") );
		}

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		HashMap<String, Object> localeItem = CommonUtil.getLocale(locale);
		String lang = String.valueOf(localeItem.get("lang"));

		if(lang.contains("_")) {
			String[] langArr = lang.split("_");
			lang = langArr[0];
		}

		if(!lang.equals("ko")) {
		   	lang = "en";
		}

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		Map<String, Object> globalVal = null;

		JSONObject global = null;

		if(cookies1 != null) {
			globalVal = CommonUtil.libLogin(cookies1, request, response);
		}
		if(globalVal != null && !globalVal.get("SESS").equals("")) {
			String globalStr = globalVal.get("SESS").toString();
			JSONParser jsonParse = new JSONParser();
	    	global = (JSONObject) jsonParse.parse(globalStr);
	    }

		if(callAlarmFlag.equals("Y")) {
			String format = "yyyy-MM-dd HH:mm:ss";
			Calendar today = Calendar.getInstance();
			SimpleDateFormat type = new SimpleDateFormat(format);
			String now = type.format(today.getTime());

			CodeUtil code = new CodeUtil(request);
			if(userSeq > 0) {
				HashMap<String, Object> alarmParam = new HashMap<String, Object>();
				alarmParam.put("mem_seq", userSeq);
				alarmParam.put("alarm", code.getCODE_MEM_ALARM_VIEW_FIELD_CD_NOTICE());
				alarmParam.put("dateReg", now);

				commonService.setAlarmLog(alarmParam);
			}
		}

		mav.addObject("page", nPage);
		mav.addObject("lang", lang);

		return mav;
	}

	@RequestMapping("cs/notice/noticeView")
	public ModelAndView noticeView(HttpServletRequest request
			, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();

		String seq = request.getParameter("Seq");

		int nPage = 1;
		if(request.getParameter("Page") != null || request.getParameter("Page") != "") {
			nPage = Integer.parseInt( request.getParameter("Page") );
		}
		if(seq == null || seq.length() == 0) {
			seq = "1";
		}

		HashMap<String, Object> localeItem = CommonUtil.getLocale(locale);
		String lang = String.valueOf(localeItem.get("lang"));

		if(lang.contains("_")) {
			String[] langArr = lang.split("_");
			lang = langArr[0];
		}

		if(!lang.equals("ko")) {
		   	lang = "en";
		}

		mav.addObject("page", nPage);
		mav.addObject("seq", seq);
		mav.addObject("lang", lang);

		return mav;
	}

	@RequestMapping(value="cs/notice/getNotice", produces="application/json;charset=UTF-8", method = RequestMethod.GET)
	public @ResponseBody String getNotice(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String seq = request.getParameter("Seq");
		int page = 1;
		String result = null;

		if(request.getParameter("Page") != null) {
			if(request.getParameter("Page").equals("0")) {
				page = 1;
			}
			else {
				page = Integer.parseInt( request.getParameter("Page") );
			}
		}

		HashMap<String, Object> localeItem = CommonUtil.getLocale(locale);
		String lang = String.valueOf(localeItem.get("lang"));

		if(lang.contains("_")) {
			String[] langArr = lang.split("_");
			lang = langArr[0];
		}

		if(!lang.equals("ko")) {
		   	lang = "en";
		}

		if(seq == null || seq.length() == 0) {

			int noticeCnt = commonService.getNoticeCount(lang);


			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("page", page);
			param.put("lang", lang);

			List<NoticeVO> noticeList = commonService.getNoticeList(param);


			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("NoticeCount", noticeCnt);

			List<HashMap<String, Object>> cntList = new ArrayList<HashMap<String, Object>>();
			cntList.add(data);

			result = "[";
			result += new Gson().toJson(cntList);
			result += ",";
			result += new Gson().toJson(noticeList);
			result += "]";

			//System.out.println("result : " + result);
		}
		else {
			List<NoticeVO> noticeList = commonService.getNoticeListBySeq(Integer.parseInt(seq));

			result = new Gson().toJson(noticeList);

			commonService.setNoticeCountBySeq(Integer.parseInt(seq));
		}

		return result;
	}

	@RequestMapping(value="cs/notice/getNoticeAdmin", produces="application/json;charset=UTF-8", method = RequestMethod.GET)
	public @ResponseBody String getNoticeAdmin(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String seq = request.getParameter("Seq");
		int page = 1;
		String result = null;

		if(request.getParameter("Page") != null) {
			if(request.getParameter("Page").equals("0")) {
				page = 1;
			}
			else {
				page = Integer.parseInt( request.getParameter("Page") );
			}
		}

		if(seq == null || seq.length() == 0) {

			int noticeCnt = commonService.getNoticeAdmCount();


			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("page", page);

			List<NoticeVO> noticeList = commonService.getNoticeAdmList(param);


			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("NoticeCount", noticeCnt);

			List<HashMap<String, Object>> cntList = new ArrayList<HashMap<String, Object>>();
			cntList.add(data);

			result = "[";
			result += new Gson().toJson(cntList);
			result += ",";
			result += new Gson().toJson(noticeList);
			result += "]";

			//System.out.println("result : " + result);
		}
		else {
			List<NoticeVO> noticeList = commonService.getNoticeListBySeq(Integer.parseInt(seq));

			result = new Gson().toJson(noticeList);

			commonService.setNoticeCountBySeq(Integer.parseInt(seq));
		}

		return result;
	}

	@RequestMapping("password/passwordReset")
	public ModelAndView passwordReset(HttpServletRequest request
			, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();

		CookieBox cookieBox = new CookieBox(request);
		Cookie cLang = cookieBox.getCookie("myLang");
		Cookie cCountry = cookieBox.getCookie("myCountry");

		String myLang = "";
		String myCountry = "";

		if(cLang != null) {
			myLang = cLang.getValue();
		}

		if(cCountry != null) {
			myCountry = cCountry.getValue();
		}

		//System.out.println("locale 1 : " + locale.toString());
		locale = new Locale(myLang, myCountry);
		//System.out.println("locale 2 : " + locale.toString());

		HashMap<String, Object> localeItem = CommonUtil.getLocale(locale);
		String areaCode = String.valueOf(localeItem.get("areaCode"));
		//System.out.println("areaCode : " + areaCode);

		mav.addObject("areaCode", areaCode);

		return mav;
	}

	@RequestMapping("password/passwordResetOk")
	public ModelAndView passwordResetOk(HttpServletRequest request
			, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		return mav;
	}

	@RequestMapping(value="password/passwordResetProcess",method = RequestMethod.GET)
	public @ResponseBody String passwordResetProcess(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");

		String result = "";
		MD5Class md5 = new MD5Class();
		EncLibrary en = new EncLibrary();

		String country_code = CommonUtil.fn_Word_In(request.getParameter("country"));
		String rcv_number  = request.getParameter("phone");

		String phone1 = rcv_number.substring(0, 3);
		String phone2 = rcv_number.substring(3, 7);
		String phone3 = rcv_number.substring(7, 11);
		//System.out.println("phone1 : " + phone1);
		//System.out.println("phone2 : " + phone2);
		//System.out.println("phone3 : " + phone3);

		if(rcv_number.length() > 0 && rcv_number.substring(0, 1).equals("0")) {
			rcv_number = rcv_number.substring( 1, rcv_number.length());
		}

		String smsRet = "";


		String rcv_number_surem = country_code + "-" + rcv_number; //슈어엠 수신자 번호

		int randomValue = (int) (Math.random() * 10000) + 10000;
		String randValue = String.valueOf(randomValue);

		String tempPassword = randValue.substring(0, 4);

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("call_flag", 0);
		param.put("mem_country", country_code);
		param.put("mem_phone", en.AlmoneyEncrypt(rcv_number));
		param.put("sec_code", tempPassword);
		int dbReturnCode = memberService.setJoinCertSms(param);

		String msg1 = messageSource.getMessage("msg_1147", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1148", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		if(dbReturnCode == 1) {
			String sms_content = msg1 + "[" + tempPassword + "]" + msg2; // msg1, msg2 활용

			String objString = CommonUtil.sendRESTSms(rcv_number_surem, sms_content, request);
		//	System.out.println("objString : " + objString);

			JSONObject obj = null;
			if(objString != null) {
				JSONParser jsonParse = new JSONParser();
				obj = (JSONObject) jsonParse.parse(objString);
			}


			smsRet = String.valueOf(obj.get("result"));

			if(smsRet.contains("success")) {
				HashMap<String, Object> mParam = new HashMap<String, Object>();
				mParam.put("tempPassword", md5.md5_encode(tempPassword));
				mParam.put("rcv_number", en.AlmoneyEncrypt(rcv_number));
				mParam.put("country_code", country_code);
				memberService.updatePwd(mParam);

				result = "1";
			}
			else {
				result = String.valueOf(dbReturnCode);
			}

		}

		return result;
	}

	@RequestMapping("default/restSms")
	public ModelAndView restSms(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String smsRet = "";
		ModelAndView mav = new ModelAndView();

		//String phone0 = CommonUtil.fn_Word_In(request.getParameter("Phone0"));	//국가번호
		//String phone1 = request.getParameter("Phone1").substring(0, 3);
		//String phone2 = request.getParameter("Phone2").substring(0, 4);
		//String phone3 = request.getParameter("Phone3").substring(0, 4);

		/*
		국가번호
		1: 미국
		86: 중국
		91: 인도
		84: 베트남
		62: 인도네시아
		81: 일본
		82: 한국
		0: 기타
		 */
		String phone0 = "82";
		String phone1 = "010";
		String phone2 = "2336";
		String phone3 = "6183";

		String rcv_number = "";
		String sms_content = "";
		int dbReturnCode = 0;
		String rcv_number_surem = "";
		EncLibrary en = new EncLibrary();

		String format = "yyyyMMdd";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String rdate = type.format(today.getTime());

		String format2 = "hhmmss";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
		SimpleDateFormat type2 = new SimpleDateFormat(format);
		String rtime = type2.format(today.getTime());

		int randomValue = (int) (Math.random() * 100000) + 100000;
		String randValue = String.valueOf(randomValue);
		//System.out.println("randValue : "+ randValue);
		String tempPassword = randValue.substring(0, 5);
		//System.out.println("tempPassword : "+ tempPassword);
		String snd_number = "023303000"; //발신자번호

		rcv_number  = phone1 + phone2 + phone3; //수신자 번호-국내번호

		String msg1 = messageSource.getMessage("msg_1129", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1130", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_1131", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_1132", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		sms_content = "[" + msg1 + " " + msg2 + "] " + msg3 + " [" + tempPassword + "]" + msg4; // msg1 ~ 5 의 변수 조합 --> msg1 ~ 4

		if(rcv_number.length() > 0 && rcv_number.substring(0, 1).equals("0")) {
			rcv_number = rcv_number.substring( 1, rcv_number.length());
		}
		//System.out.println("rcv_number : "+ rcv_number);

		rcv_number_surem = phone0 + "-" + rcv_number; //슈어엠 수신자 번호
		//System.out.println("rcv_number_surem : "+ rcv_number_surem);

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("call_flag", 0);
		param.put("mem_country", phone0);
		param.put("mem_phone", en.AlmoneyEncrypt(rcv_number));
		param.put("sec_code", tempPassword);
		/*
		System.out.println("call_flag : "+ 0);
		System.out.println("mem_country : "+ param.get("mem_country"));
		System.out.println("mem_phone : "+ param.get("mem_phone"));
		System.out.println("sec_code : "+ param.get("sec_code"));
		*/
		//dbReturnCode = memberService.setJoinCertSms(param);
		dbReturnCode = 0;


		String msg6 = messageSource.getMessage("msg_1124", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg7 = messageSource.getMessage("msg_1133", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg8 = messageSource.getMessage("msg_1134", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg9 = messageSource.getMessage("msg_1135", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg10 = messageSource.getMessage("msg_1136", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg11 = messageSource.getMessage("msg_1137", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg12 = messageSource.getMessage("msg_1138", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg13 = messageSource.getMessage("msg_0318", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg14 = messageSource.getMessage("msg_0319", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		// 필요한 만큼 변수를 선언 하여 아래 메시지에 조합하는 형식으로 적용.

		//dbReturnCode = 0;//테스트 후 주석 처리 할것
		if(dbReturnCode == 1) {
			CommonUtil.jspAlert(response, msg6, "", "parent.parent"); // msg6 활용
		}
		else if(dbReturnCode == 2) {
			CommonUtil.jspAlert(response, msg7, "", "parent.parent"); // msg7 활용
		}
		else if(dbReturnCode == 3) {
			// msg8, msg9, msg10 활용
			CommonUtil.jspAlert(response, msg8 + "\\n" + msg9 + "\\n" + msg10, "", "parent.parent");
		}
		else if(dbReturnCode == 0) {
			String objString = CommonUtil.sendRESTSms(rcv_number_surem, sms_content, request);
		//	System.out.println("objString : " + objString);

			JSONObject obj = null;
			if(objString != null) {
				JSONParser jsonParse = new JSONParser();
				obj = (JSONObject) jsonParse.parse(objString);
			}


			//System.out.println(sr.getMember());	// 발송한 메시지의 키값
			//System.out.println(sr.getStatus());	// 발송 상태 (O : 성공)
			//System.out.println(String.format("SR: %s", sr)); // member = 메시지 고유값, status = O(성공) 결과값

			smsRet = String.valueOf(obj.get("result"));
			System.out.println("smsRet : " + smsRet);
			/*smsRet = sr.getStatus();*/

			if(smsRet.equals("success")) {
				CommonUtil.jspCertSend_n2(response, msg11); // msg11 활용
			}
			else {
				CommonUtil.jspAlert(response, msg12, "", "parent.parent"); // msg12 활용
			}
		}
		else {
			CommonUtil.jspAlert(response, msg13 + "\\n" + msg14, "", "parent.parent"); // msg13, msg14 활용
		}


		mav.addObject("smsRet", smsRet);

		return mav;
	}


	@RequestMapping("default/cate_lang_update")
	public ModelAndView cate_lang_update(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		mav.addObject("userSeq", userSeq);
		mav.addObject("userLv", userLv);

		return mav;
	}

	@RequestMapping(value = "/default/excelCateUpdate", method = RequestMethod.POST)
    public void ExcelUp(MultipartHttpServletRequest req, @RequestParam("excleFile") MultipartFile file, HttpServletResponse rep){
	//	System.out.println("@@@@@@@@@@@@@@@ExcelUp START@@@@@@@@@@@@@@@");

        //위치 및 파일

        String tbl = "T_SECTION_T"; //T_SECTION_T	T_SECTION2	T_SECTION3_1	T_SECTION4	T_SECTION5$
        String col = "codeName_en"; // codeName_en		codeName_zh
        tbl = req.getParameter("tbl");
        col = req.getParameter("col");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tbl", tbl);
        map.put("col", col);
        //System.out.println("tbl : " + tbl);
        //System.out.println("col : " + col);


        int shno = 0;
        int cells = 2;

        if(tbl.equals("T_SECTION2")) {
        	shno = 1;
        }
        else if(tbl.equals("T_SECTION3_1")) {
        	shno = 2;
        }
		else if(tbl.equals("T_SECTION4")) {
			shno = 3;
		}
		else if(tbl.equals("T_SECTION5$")) {
			shno = 4;
		}


        try {
            //FileInputStream file = new FileInputStream("E:/cate(en).xlsx");
        //	System.out.println("file : " + file.getInputStream());
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());

            int rowindex = 0;

            //시트 수 (첫번째에만 존재하므로 0을 준다)
            //만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
            XSSFSheet sheet = workbook.getSheetAt(shno);
            //행의 수
            int rows = sheet.getPhysicalNumberOfRows();
            for(rowindex = 2; rowindex <= rows; rowindex++){
                //행을읽는다
                XSSFRow row = sheet.getRow(rowindex);
                if(row != null){
                    //셀의 수
                    //int cells=row.getPhysicalNumberOfCells();

                	XSSFCell cell0 = row.getCell(0);
                	XSSFCell cell1 = row.getCell(2);

                	String code = ExcelUtil.cellValue(cell0);
                	String codeValue = ExcelUtil.cellValue(cell1);

                	if(codeValue.contains("삭제") || codeValue.equals("")) { continue; }

                	//System.out.println(rowindex+"번 / code : " + code +" / codeValue: " + codeValue);

                	System.out.println("UPDATE " + tbl + " SET " + col + " = '" + codeValue + "' WHERE Code = '" + code + "';");
                }
            }

        }catch(Exception e) {
            e.printStackTrace();
        }



        //System.out.println("@@@@@@@@@@@@@@@ExcelUp END@@@@@@@@@@@@@@@");

    }
}
