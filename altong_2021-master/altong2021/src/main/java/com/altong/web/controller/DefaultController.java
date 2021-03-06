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
		String sEncData = ""; // ??????????????? ????????? ??? ?????????

		String sSiteCode = "BB965"; 			//NICE????????? ???????????? ????????? ??????
		String sSitePassword = "PnWuAfNp2nyN";	//NICE????????? ???????????? ????????? ????????????

		String sAuthType = "M";					//????????? ?????? ????????????, M: ?????????, C: ??????, X: ???????????????
		String popgubun = "Y";					//Y : ???????????? ??????, N : ???????????? ??????
		String customize = "";					//????????? ?????? ???????????? / Mobile : ??????????????????
		String sGender = "";					//????????? ?????? ?????? ???, 0 : ??????, 1 : ??????

		//CheckPlus(????????????) ?????? ???, ?????? ???????????? ?????? ???????????? ??????????????? ?????? http?????? ???????????????.
		//??????url??? ?????? ??? ?????????????????? ???????????? ??? url??? ???????????? ?????????. ex) ?????? ??? url : http://www.~ ?????? url : http://www.~


		String sReturnUrl = request.getScheme() + "://" + request.getServerName() + "/default/joinInput_n";			//????????? ????????? URL
		String sErrorUrl = request.getScheme() + "://" + request.getServerName() + "/default/check/checkplus_fail";			//????????? ????????? URL

		//String sRequestNO = "REQ0000000001";	//?????? ??????, ?????? ??????/???????????? ?????? ????????? ??????????????? ?????????
												//????????? ???????????? ???????????? ?????????, ????????? ?????? ????????????.

		String sRequestNO = niceCheck.getRequestNO(sSiteCode);

		HttpSession session = request.getSession(true);
		session.setAttribute("REQ_SEQ", sRequestNO);

		// ????????? plain ???????????? ?????????.
		String sPlainData = "7:REQ_SEQ" + sRequestNO.getBytes().length + ":" + sRequestNO +
                "8:SITECODE" + sSiteCode.getBytes().length + ":" + sSiteCode +
                "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType +
                "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl +
                "7:ERR_URL" + sErrorUrl.getBytes().length + ":" + sErrorUrl +
                "11:POPUP_GUBUN" + popgubun.getBytes().length + ":" + popgubun +
                "9:CUSTOMIZE" + customize.getBytes().length + ":" + customize +
				"6:GENDER" + sGender.getBytes().length + ":" + sGender;



		//???????????? ?????????
		String sMessage = "";
		int iReturn = niceCheck.fnEncode(sSiteCode, sSitePassword, sPlainData);

		String msg1 = messageSource.getMessage("msg_1066", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg2 = messageSource.getMessage("msg_1067", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg3 = messageSource.getMessage("msg_1068", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg4 = messageSource.getMessage("msg_1069", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg5 = messageSource.getMessage("msg_0318", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????

		if(iReturn == 0) {
			sEncData = niceCheck.getCipherData();
		}
		else if( iReturn == -1)
	    {
	        sMessage = msg1; // msg1 ??????
	    }
	    else if( iReturn == -2)
	    {
	        sMessage = msg2; // msg2 ??????
	    }
	    else if( iReturn == -3)
	    {
	        sMessage = msg3; // msg3 ??????
	    }
	    else if( iReturn == -9)
	    {
	        sMessage = msg4; // msg4 ??????
	    }
	    else
	    {
	        sMessage = msg5 + iReturn; // msg5 ??????
	    }


		if(sMessage != "") {
			CommonUtil.jspAlert(response, sMessage , "", "");
			//-1 : ????????? ????????? ???????????????.
			//-2 : ????????? ?????????????????????.
			//-3 : ????????? ????????? ???????????????.
			//-4 : ?????? ????????? ???????????????.

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



		String sEncData = ""; // ??????????????? ????????? ??? ?????????

		String sSiteCode = "BB965"; 			//NICE????????? ???????????? ????????? ??????
		String sSitePassword = "PnWuAfNp2nyN";	//NICE????????? ???????????? ????????? ????????????

		String sAuthType = "M";					//????????? ?????? ????????????, M: ?????????, C: ??????, X: ???????????????
		String popgubun = "Y";					//Y : ???????????? ??????, N : ???????????? ??????
		String customize = "";					//????????? ?????? ???????????? / Mobile : ??????????????????
		String sGender = "";					//????????? ?????? ?????? ???, 0 : ??????, 1 : ??????




		//CheckPlus(????????????) ?????? ???, ?????? ???????????? ?????? ???????????? ??????????????? ?????? http?????? ???????????????.
				//??????url??? ?????? ??? ?????????????????? ???????????? ??? url??? ???????????? ?????????. ex) ?????? ??? url : http://www.~ ?????? url : http://www.~
		String sReturnUrl = request.getScheme() + "://" + request.getServerName() + "/default/joinInput_n";			//????????? ????????? URL
		String sErrorUrl = request.getScheme() + "://" + request.getServerName() + "/default/check/checkplus_fail";			//????????? ????????? URL

		String sRequestNO = niceCheck.getRequestNO(sSiteCode);

		HttpSession session = request.getSession(true);
		session.setAttribute("REQ_SEQ", sRequestNO);

		// ????????? plain ???????????? ?????????.
		String sPlainData = "7:REQ_SEQ" + sRequestNO.getBytes().length + ":" + sRequestNO +
                "8:SITECODE" + sSiteCode.getBytes().length + ":" + sSiteCode +
                "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType +
                "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl +
                "7:ERR_URL" + sErrorUrl.getBytes().length + ":" + sErrorUrl +
                "11:POPUP_GUBUN" + popgubun.getBytes().length + ":" + popgubun +
                "9:CUSTOMIZE" + customize.getBytes().length + ":" + customize +
				"6:GENDER" + sGender.getBytes().length + ":" + sGender;

		//???????????? ?????????
		String sMessage = "";
		String msg1 = messageSource.getMessage("msg_1066", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg2 = messageSource.getMessage("msg_1067", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg3 = messageSource.getMessage("msg_1068", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg4 = messageSource.getMessage("msg_1069", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg5 = messageSource.getMessage("msg_0318", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????

		int iReturn = niceCheck.fnEncode(sSiteCode, sSitePassword, sPlainData);

		if(iReturn == 0) {
			sEncData = niceCheck.getCipherData();
		}
		else if( iReturn == -1)
	    {
	        sMessage = msg1; // msg1 ??????
	    }
	    else if( iReturn == -2)
	    {
	        sMessage = msg2; // msg2 ??????
	    }
	    else if( iReturn == -3)
	    {
	        sMessage = msg3; // msg3 ??????
	    }
	    else if( iReturn == -9)
	    {
	        sMessage = msg4; // msg4 ??????
	    }
	    else
	    {
	        sMessage = msg5 + iReturn; // msg5 ??????
	    }


		if(sMessage != "") {
			CommonUtil.jspAlert(response, sMessage , "", "");
			//-1 : ????????? ????????? ???????????????.
			//-2 : ????????? ?????????????????????.
			//-3 : ????????? ????????? ???????????????.
			//-4 : ?????? ????????? ???????????????.

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
			String msg1 = messageSource.getMessage("msg_1019", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
			CommonUtil.jspAlert(response, msg1 , "/default/main", "parent.parent"); // msg1 ??????
			return null;
		}


		String sEncodeData = CommonUtil.requestReplace(request.getParameter("EncodeData"), "encodeData");

	    String sSiteCode = "BB965";			// NICE????????? ???????????? ????????? ??????
	    String sSitePassword = "PnWuAfNp2nyN";	// NICE????????? ???????????? ????????? ????????????

	    String sCipherTime = "";			// ???????????? ??????
	    String sRequestNumber = "";			// ?????? ??????
	    String sResponseNumber = "";		// ?????? ????????????
	    String sAuthType = "";				// ?????? ??????
	    String sName = "";					// ??????
	    String sDupInfo = "";				// ???????????? ????????? (DI_64 byte)
	    String sConnInfo = "";				// ???????????? ????????? (CI_88 byte)
	    String sBirthDate = "";				// ????????????(YYYYMMDD)
	    String sGender = "";				// ??????
	    String sNationalInfo = "";			// ???/??????????????? (??????????????? ??????)
		String sMobileNo = "";				// ???????????????
		String sMobileCo = "";				// ?????????
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

	        // ???????????? ???????????????.
	        java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);

	        sRequestNumber  = (String)mapresult.get("REQ_SEQ");
	        sResponseNumber = (String)mapresult.get("RES_SEQ");
	        sAuthType		= (String)mapresult.get("AUTH_TYPE");
	        //sName			= (String)mapresult.get("NAME");
			sName			= (String)mapresult.get("UTF8_NAME"); //charset utf8 ????????? ?????? ?????? ??? ??????
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
	        	String msg2 = messageSource.getMessage("msg_1109", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
	            sMessage = msg2; // msg2 ??????
	            sResponseNumber = "";
	            sAuthType = "";
	        }
	    }
	    else if( iReturn == -1)
	    {
	    	String msg3 = messageSource.getMessage("msg_1112", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
	        sMessage = msg3; // msg3 ??????
	    }
	    else if( iReturn == -4)
	    {
	    	String msg4 = messageSource.getMessage("msg_1113", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
	        sMessage = msg4; // msg4 ??????
	    }
	    else if( iReturn == -5)
	    {
	    	String msg5 = messageSource.getMessage("msg_1114", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
	        sMessage = msg5; // msg5 ??????
	    }
	    else if( iReturn == -6)
	    {
	    	String msg6 = messageSource.getMessage("msg_1115", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
	        sMessage = msg6; // msg6 ??????
	    }
	    else if( iReturn == -9)
	    {
	    	String msg7 = messageSource.getMessage("msg_1116", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
	        sMessage = msg7; // msg7 ??????
	    }
	    else if( iReturn == -12)
	    {
	    	String msg8 = messageSource.getMessage("msg_1117", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
	        sMessage = msg8; // msg8 ??????
	    }
	    else
	    {
	    	String msg9 = messageSource.getMessage("msg_0318", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
	        sMessage = msg9 + iReturn; // msg9 ??????
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
	    	String msg10 = messageSource.getMessage("msg_1124", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
	    	String msg11 = messageSource.getMessage("msg_0319", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
			CommonUtil.jspAlert(response, msg10 + "\\n\\n" + msg11, "/default/main", "parent.parent"); // msg10, msg11 ??????

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

		String format = "yyyy-MM-dd";//yyyy-MM-dd aa hh:mm:ss(??????/?????? ??????)
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

	    String sSiteCode = "BB965";				// NICE????????? ???????????? ????????? ??????
	    String sSitePassword = "PnWuAfNp2nyN";	// NICE????????? ???????????? ????????? ????????????

	    String sCipherTime = "";			// ???????????? ??????
	    String sRequestNumber = "";			// ?????? ??????
	    String sErrorCode = "";				// ?????? ????????????
	    String sAuthType = "";				// ?????? ??????
	    String sMessage = "";
	    String sPlainData = "";

	    HttpSession session = request.getSession(true);

	    String msg1 = messageSource.getMessage("msg_1109", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
	    String msg2 = messageSource.getMessage("msg_1110", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
	    String msg3 = messageSource.getMessage("msg_1112", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
	    String msg4 = messageSource.getMessage("msg_1113", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
	    String msg5 = messageSource.getMessage("msg_1114", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
	    String msg6 = messageSource.getMessage("msg_1115", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
	    String msg7 = messageSource.getMessage("msg_1116", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
	    String msg8 = messageSource.getMessage("msg_1117", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
	    String msg9 = messageSource.getMessage("msg_0318", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
	    String msg10 = messageSource.getMessage("msg_1125", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????

	    int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

	    if( iReturn == 0 )
	    {
	        sPlainData = niceCheck.getPlainData();
	        sCipherTime = niceCheck.getCipherDateTime();

	        // ???????????? ???????????????.
	        java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);

	        sRequestNumber 	= (String)mapresult.get("REQ_SEQ");
	        sErrorCode 		= (String)mapresult.get("ERR_CODE");
	        sAuthType 		= (String)mapresult.get("AUTH_TYPE");

	        if(session.getAttribute("REQ_SEQ") != sRequestNumber) {
	        	sMessage = msg1 + msg2 + "<br>"; // msg1, msg2 ??????
	        }
	    }
	    else if( iReturn == -1)
	    {
	        sMessage = msg3; // msg3 ??????
	    }
	    else if( iReturn == -4)
	    {
	        sMessage = msg4; // msg4 ??????
	    }
	    else if( iReturn == -5)
	    {
	        sMessage = msg5; // msg5 ??????
	    }
	    else if( iReturn == -6)
	    {
	        sMessage = msg6; // msg6 ??????
	    }
	    else if( iReturn == -9)
	    {
	        sMessage = msg7; // msg7 ??????
	    }
	    else if( iReturn == -12)
	    {
	        sMessage = msg8; // msg8 ??????
	    }
	    else
	    {
	        sMessage = msg9 + iReturn; // msg9 ??????
	    }

	    mav.addObject("sResultMessage",  msg10 + "[" + sPlainData + "]<br>"); // msg10 ??????
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

	    String sSiteCode = "BB965";				// NICE????????? ???????????? ????????? ??????
	    String sSitePassword = "PnWuAfNp2nyN";	// NICE????????? ???????????? ????????? ????????????

	    String sCipherTime = "";			// ???????????? ??????
	    String sRequestNumber = "";			// ?????? ??????
	    String sResponseNumber = "";		// ?????? ????????????
	    String sAuthType = "";				// ?????? ??????
	    String sName = "";					// ??????
	    String sDupInfo = "";				// ???????????? ????????? (DI_64 byte)
	    String sConnInfo = "";				// ???????????? ????????? (CI_88 byte)
	    String sBirthDate = "";				// ??????
	    String sGender = "";				// ??????
	    String sNationalInfo = "";			// ???/????????? ?????? (????????? ????????? ??????)
	    String sMobileNo = "";				// ???????????????
	    String sMobileCo = "";				// ?????????
	    String sMessage = "";
	    String sPlainData = "";

	    HttpSession session = request.getSession(true);

	    String msg1 = messageSource.getMessage("msg_1109", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
	    String msg2 = messageSource.getMessage("msg_1110", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
	    String msg3 = messageSource.getMessage("msg_1112", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
	    String msg4 = messageSource.getMessage("msg_1113", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
	    String msg5 = messageSource.getMessage("msg_1114", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
	    String msg6 = messageSource.getMessage("msg_1115", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
	    String msg7 = messageSource.getMessage("msg_1116", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
	    String msg8 = messageSource.getMessage("msg_1117", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
	    String msg9 = messageSource.getMessage("msg_1118", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????

	    int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

	    if( iReturn == 0 )
	    {
	        sPlainData = niceCheck.getPlainData();
	        sCipherTime = niceCheck.getCipherDateTime();

	        // ???????????? ???????????????.
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
	        	sMessage = msg1 + msg2 + "<br>"; // msg1, msg2 ??????
	        }
	    }
	    else if( iReturn == -1)
	    {
	        sMessage = msg3; // msg3 ??????
	    }
	    else if( iReturn == -4)
	    {
	        sMessage = msg4; // msg4 ??????
	    }
	    else if( iReturn == -5)
	    {
	        sMessage = msg5; // msg5 ??????
	    }
	    else if( iReturn == -6)
	    {
	        sMessage = msg6; // msg6 ??????
	    }
	    else if( iReturn == -9)
	    {
	        sMessage = msg7; // msg7 ??????
	    }
	    else if( iReturn == -12)
	    {
	        sMessage = msg8; // msg8 ??????
	    }
	    else
	    {
	        sMessage = msg9 + iReturn; // msg9 ??????
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

		String msg1 = messageSource.getMessage("msg_1019", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg2 = messageSource.getMessage("msg_1126", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg3 = messageSource.getMessage("msg_1127", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg4 = messageSource.getMessage("msg_1124", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg5 = messageSource.getMessage("msg_1128", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg6 = messageSource.getMessage("msg_0952", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg7 = messageSource.getMessage("msg_0318", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg8 = messageSource.getMessage("msg_0319", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????

		if(ch_encodeData == null || ch_encodeData.length() == 0) {
			CommonUtil.jspAlert(response, msg1 , "", ""); // msg1 ??????
		}
		else {

			String sSiteCode = "BB965";				// NICE????????? ???????????? ????????? ??????
		    String sSitePassword = "PnWuAfNp2nyN";	// NICE????????? ???????????? ????????? ????????????

		    String sCipherTime = "";			// ???????????? ??????
		    String sRequestNumber = "";			// ?????? ??????
		    String sResponseNumber = "";		// ?????? ????????????
		    String sAuthType = "";				// ?????? ??????
		    String sName = "";					// ??????
		    String sDupInfo = "";				// ???????????? ????????? (DI_64 byte)
		    String sConnInfo = "";				// ???????????? ????????? (CI_88 byte)
		    String sBirthDate = "";				// ??????
		    String sGender = "";				// ??????
		    String sNationalInfo = "";			// ???/????????? ?????? (????????? ????????? ??????)
		    String sMobileNo = "";				// ???????????????
		    String sMobileCo = "";				// ?????????
		    String sMessage = "";
		    String sPlainData = "";

		    int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, ch_encodeData);

		    if( iReturn == 0 )
		    {
		        sPlainData = niceCheck.getPlainData();
		        sCipherTime = niceCheck.getCipherDateTime();

		        // ???????????? ???????????????.
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
		    	CommonUtil.jspAlert(response, msg1 , "", ""); // msg1 ??????
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
						CommonUtil.jspAlert(response, msg2 , "back", ""); // msg2 ??????
						chk = false;
						break;
					}
				}


				if(chk == true) {
					if(!password1.equals(password2) || nickName.length() == 0 || nickName == null || email1 == null || email1.length() == 0 || email2 == null || email2.length() == 0) {
						chk = false;
						CommonUtil.jspAlert(response, msg1 , "back", ""); // msg1 ??????
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


					String format = "yyyy-MM-dd";//yyyy-MM-dd aa hh:mm:ss(??????/?????? ??????)
					Calendar today = Calendar.getInstance();
					SimpleDateFormat type = new SimpleDateFormat(format);
					String regDate = type.format(today.getTime());

					HashMap<String, Object> mParam = new HashMap<String, Object>();
					//userPhone = "55C0F484B1857E8C3C1188B5896CBE8814D3BC7DDCAF15BCF65AB39220CD2B7C";
					//sName = "?????????";
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
						CommonUtil.jspAlertOpener(response, msg3 , "/default/main", ""); // msg3 ??????
					}
					else if(result_code == 1) {
						CommonUtil.jspAlertOpener(response, msg4 , "/default/main", ""); // msg4 ??????
					}
					else if(result_code == 2) {
						CommonUtil.jspAlert(response, msg5 + "\\n\\n" + msg6 , "back", ""); // msg5, msg6 ??????
					}
					else if(result_code == 99) {
						CommonUtil.jspAlertOpener(response, msg7 + "\\n\\n" + msg8 , "/default/main", ""); // msg7, msg8 ??????
					}
					else {
						CommonUtil.jspAlertOpener(response, msg7 + "\\n\\n" + msg8 , "/default/main", ""); // msg7, msg8 ??????
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

		String format = "yyyy-MM-dd aa hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(??????/?????? ??????)
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

		//sName = "?????????";
		//phone = "01011111111"; // ???????????? ?????? ??????
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

		//???????????? ????????????
		//randCode = "1111";
		//certNum = "1111";

		userPW_md5 = md5.md5_encode(password1);

		String nickNotUse = configService.getNickNameCheck();
		/*
		ch_encodeData = "TEST"; // ????????? ????????? ?????? ??????
		System.out.println("randCode : " + randCode);
		System.out.println("ch_encodeData : " + ch_encodeData);
		System.out.println("certNum : " + certNum);
		System.out.println("userPW_md5 : " + userPW_md5);
		System.out.println("password1 : " + password1);
		System.out.println("password2 : " + password2);
		System.out.println("nickName : " + nickName);
		System.out.println("nickName chk : " + CommonUtil.nickNameChk(nickName, nickNotUse));
		*/

		String msg1 = messageSource.getMessage("msg_1019", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg2 = messageSource.getMessage("msg_1124", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg3 = messageSource.getMessage("msg_0318", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg4 = messageSource.getMessage("msg_0319", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????

		if(randCode.equals("") || randCode == null || certNum == null || certNum.equals("") ) {
			CommonUtil.jspAlert(response, msg1 , "/default/main", "parent.parent"); // msg1 ??????
		}
		else {
			if(!password1.equals(password2) || nickName.length() == 0) {
				CommonUtil.jspAlert(response, msg1 , "/default/main", "parent.parent"); // msg1 ??????
				//System.out.println("(1) : ");
			}
			else if(CommonUtil.nickNameChk(nickName, nickNotUse) == 1) {
				CommonUtil.jspAlert(response, msg1 , "back", ""); // msg1 ??????
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

				//?????? ?????? ??????
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
				// ???????????? ?????? ??????

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
				CommonUtil.jspAlert(response, msg2 , "back", ""); // msg2 ??????
			}
			else if(result_code.equals("2")) {
				//System.out.println("(5) : ");
				CommonUtil.jspAlert(response, msg2 , "back", ""); // msg2 ??????
			}
			else {
				CommonUtil.jspAlert(response, msg3 + "\\n" + msg4 , "/default/main", "parent.parent"); // msg3, msg4 ??????
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

		String msg1 = messageSource.getMessage("msg_0203", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg2 = messageSource.getMessage("msg_1126", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg3 = messageSource.getMessage("msg_0999", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg4 = messageSource.getMessage("msg_1000", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????

		if(nickName == "" || nickName.length() == 0) {
			CommonUtil.jspAlert(response, msg1, "", ""); // msg1 ??????
		}
		else {
			//[??????(2018.01.03): ?????????] '?????? ???????????? ??????'?????? "????????? ????????????" ???????????? CHECK
			String nickNotUse = configService.getNickNameCheck();

			String[] arrNickNotUse = nickNotUse.split("/");
			String nickNotUseStr = "";
			for(int i = 0; i < arrNickNotUse.length; i++) {
				nickNotUseStr = arrNickNotUse[i];
				if(nickNotUseStr.contains(nickName)) {
					CommonUtil.jspAlert(response, msg2, "", ""); // msg2 ??????
					break;
				}
			}

			int nickUse = memberService.getNickNameSelectCount(nickName);

			if(nickUse > 0) {
				CommonUtil.jspAlert(response, msg3, "", ""); // msg3 ??????
			}
			else {
				CommonUtil.jspNickPass(response, msg4, "", ""); // msg4 ??????
			}
		}
	}

	@RequestMapping(value="default/nickNameCheck_n2", method = {RequestMethod.GET, RequestMethod.POST})
	public void nickNameCheck_n2(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String nickName = CommonUtil.fn_Word_In(request.getParameter("NickName"));

		String msg1 = messageSource.getMessage("msg_0203", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg2 = messageSource.getMessage("msg_1126", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????

		if(nickName == "" || nickName.length() == 0) {
			CommonUtil.jspAlert(response, msg1, "", ""); // msg1 ??????
		}
		else {
			//[??????(2018.01.03): ?????????] '?????? ???????????? ??????'?????? "????????? ????????????" ???????????? CHECK
			String nickNotUse = configService.getNickNameCheck();

			String[] arrNickNotUse = nickNotUse.split("/");
			String nickNotUseStr = "";
			for(int i = 0; i < arrNickNotUse.length; i++) {
				nickNotUseStr = arrNickNotUse[i];
				if(nickNotUseStr.contains(nickName)) {
					CommonUtil.jspAlert(response, msg2, "", ""); // msg2 ??????
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

		String msg1 = messageSource.getMessage("msg_0203", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg2 = messageSource.getMessage("msg_1126", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????

		if(nickName == "" || nickName.length() == 0) {
			CommonUtil.jspAlert(response, msg1, "", ""); // msg1 ??????
		}
		else {
			//[??????(2018.01.03): ?????????] '?????? ???????????? ??????'?????? "????????? ????????????" ???????????? CHECK
			String nickNotUse = configService.getNickNameCheck();

			String[] arrNickNotUse = nickNotUse.split("/");
			String nickNotUseStr = "";
			for(int i = 0; i < arrNickNotUse.length; i++) {
				nickNotUseStr = arrNickNotUse[i];
				if(nickNotUseStr.contains(nickName)) {
					CommonUtil.jspAlert(response, msg2, "", ""); // msg2 ??????
					break;
				}
			}

			int nickUse = memberService.getNickNameSelectCount(nickName);

			CommonUtil.jspNickPass_n4(response, request, nickUse);
		}
	}

	@RequestMapping(value="cert/joinCert", method = {RequestMethod.GET, RequestMethod.POST})
	public void joinCert(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String phone0 = CommonUtil.fn_Word_In(request.getParameter("Phone0"));	//????????????

		String phone1 = request.getParameter("Phone1");
		String rcv_number = "";
		String sms_content = "";
		int dbReturnCode = 0;
		String rcv_number_surem = "";
		EncLibrary en = new EncLibrary();

		String format = "yyyyMMdd";//yyyy-MM-dd aa hh:mm:ss(??????/?????? ??????)
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String rdate = type.format(today.getTime());

		String format2 = "hhmmss";//yyyy-MM-dd aa hh:mm:ss(??????/?????? ??????)
		SimpleDateFormat type2 = new SimpleDateFormat(format);
		String rtime = type2.format(today.getTime());


		int randomValue = (int) (Math.random() * 100000) + 100000;
		String randValue = String.valueOf(randomValue);
		//System.out.println("randValue : "+ randValue);
		String tempPassword = randValue.substring(0, 5);
		//System.out.println("tempPassword : "+ tempPassword);
		String snd_number = "023303000"; //???????????????


		String msg1 = messageSource.getMessage("msg_1129", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg2 = messageSource.getMessage("msg_1130", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg3 = messageSource.getMessage("msg_1131", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg4 = messageSource.getMessage("msg_1132", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg5 = messageSource.getMessage("msg_1124", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg6 = messageSource.getMessage("msg_1133", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg7 = messageSource.getMessage("msg_1134", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg8 = messageSource.getMessage("msg_1135", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg9 = messageSource.getMessage("msg_1136", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg10 = messageSource.getMessage("msg_1137", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg11 = messageSource.getMessage("msg_1138", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg12 = messageSource.getMessage("msg_0318", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg13 = messageSource.getMessage("msg_0319", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????

		if(phone0.equals("82")) {
			rcv_number  = phone1; //????????? ??????-????????????

			// sms_content = "[" + msg1 + " " + msg2 + "] " + msg3 + " [" + tempPassword + "]" + msg4; // ???????????? ????????? ?????? ??????
			sms_content = "[" + msg1 + " " + msg2 + "] " + msg3 + " [" + tempPassword + "]" + msg4;

			if(rcv_number.length() > 0 && rcv_number.substring(0, 1).equals("0")) {
				rcv_number = rcv_number.substring( 1, rcv_number.length());
			}
			//System.out.println("rcv_number : "+ rcv_number);

			rcv_number_surem = phone0 + "-" + rcv_number; //????????? ????????? ??????
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
			rcv_number  = phone1; //????????? ??????-????????????
			// sms_content = "[" + msg1 + " " + msg2 + "] " + msg3 + " [" + tempPassword + "]" + msg4; // ???????????? ????????? ?????? ??????
			sms_content = "[" + msg1 + " " + msg2 + "] " + msg3 + " [" + tempPassword + "]" + msg4;

			//System.out.println("rcv_number : "+ rcv_number);

			rcv_number_surem = phone0 + "-" + rcv_number; //????????? ????????? ??????
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
		//dbReturnCode = 0;//????????? ??? ?????? ?????? ??????
		if(dbReturnCode == 1) {
			CommonUtil.jspAlert(response, msg5, "", "");  // msg5 ??????
		}
		else if(dbReturnCode == 2) {
			CommonUtil.jspAlert(response, msg6, "", ""); // msg6 ??????
		}
		else if(dbReturnCode == 3) {
			CommonUtil.jspAlert(response, msg7 + "\\n" + msg8 + "\\n" + msg9 , "", ""); // msg7, msg8, msg9 ??????
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
				CommonUtil.jspCertSend_n2(response, msg10); // msg10 ??????
			}
			else {
				CommonUtil.jspAlert(response, msg11, "", ""); // msg11 ??????
			}
		}
		else {
			CommonUtil.jspAlert(response, msg12 + "\\n" + msg13, "", ""); // ????????? ????????? ????????? ?????? ??????. \\n ??? ???????????? ??????.   // msg12, msg13 ??????
		}
		CommonUtil.jspCertSendChking(response, false);
	}

	@RequestMapping(value="cert/cert", method = {RequestMethod.GET, RequestMethod.POST})
	public void cert(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String certNumber = request.getParameter("CertNum");
		String country = CommonUtil.fn_Word_In(request.getParameter("Phone0"));	//????????????
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

		String msg1 = messageSource.getMessage("msg_1139", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg2 = messageSource.getMessage("msg_1140", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg3 = messageSource.getMessage("msg_1141", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg4 = messageSource.getMessage("msg_1142", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg5 = messageSource.getMessage("msg_1143", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg6 = messageSource.getMessage("msg_1144", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg7 = messageSource.getMessage("msg_1145", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		//... ????????? ????????? ???????????? ??????


		if(randCodeTryCnt > 50 || el_time > memJoinSmsTimeOut) {
			CommonUtil.jspCertTimeChk_n2(response, msg1 + "\\n" +msg2, false, false, "N"); // msg1, msg2 ??????
		}
		else if(certNumber.length() < 4) {
			CommonUtil.jspCertTimeChk_n2(response, msg3, false, false, "N"); // msg3 ??????
		}
		else if(certNumber.equals(secCode)) {
			CommonUtil.jspCertTimeChk_n2(response, msg4, true, true, "Y"); // msg4 ??????
		}
		else {
			memberService.setMemSmsCnt(param);

			if(randCodeTryCnt >= 50) {
				CommonUtil.jspAlert(response, msg5, "", ""); // msg5 ??????
			}
			else {
				CommonUtil.jspAlert(response, msg6 + "\\n" +msg7, "", ""); // msg6, msg7 ??????
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
			info.setMaxAge( 60 * 60 * 24 * 1 ); // ?????? ???????????? ??????(1???)
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
		// ?????? ??????
		HttpSession session = request.getSession(true);

		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject global = null;

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

	    if(userSeq > 0) {
	    	CommonUtil.setLog(request, response);

	    	session.invalidate();


			// ?????? ??????
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
		//????????? ??????????????? ???????????? ?????? ?????????
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

		// ?????? ???????????? SP_MEMBER_LOGIN_CH6 ??????
		Map<String, Object> param2 = new HashMap<String, Object>();
		param2.put("LOGIN_COUNTRY", UserCountry);
		param2.put("LOGIN_ID", UserPhone_secure);
		param2.put("LOGIN_PW", UserPW_md5);
		param2.put("LOGIN_IP", user_login_ip);
		param2.put("LOGIN_DATE", now); // 2020-09-23 ?????? 6:10:14

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


		String msg1 = messageSource.getMessage("msg_1146", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????

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

				session.setAttribute("Ver", 0); // Tomcat ?????? : 0
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

				// 2021-03-16 ?????? ????????? ?????? ??????
				if(session.getAttribute("LoginDefault") == "Y") {
					session.setAttribute("SessExpire", 147483647);
				}
				else {
					session.setAttribute("SessExpire", 147483647);
				}

				//????????? ???????????? AutoLogin ?????? ?????? ??????
				session.setAttribute("AutoLogin", "Y");

				//[??????(2018.03.08): ?????????] ?????? ?????? ??????
				String UserEmail = res.getString("Email");
				session.setAttribute("UserEmail", UserEmail);

				//[??????(2018.10.07): ?????????] ?????? ?????? ??????
				BigDecimal UserAlpayKR = res.getBigDecimal("AlpayKR");
				session.setAttribute("UserAlpayKR", UserAlpayKR);

				//[??????(2018.10.17): ?????????] ?????? ?????? ??????
				session.setAttribute("Almaeng", vb.CDbl(vb.Trim("0" + res.getInt("Almaeng") + " ")) );
				session.setAttribute("AlmaengCode", vb.CDbl(vb.Trim("0" + res.getString("AlmaengCode") + " ")) );

				//[??????(2019.05.23): ?????????] ?????? ?????? ??????
				String Univ = vb.Trim(res.getDouble("Univ") + " ");

				if(!Univ.equals("null") && !Univ.equals(null) && !Univ.equals("")) {
					session.setAttribute("Univ", vb.CDbl(Univ));
				}

				//[??????(2018.04.02): ?????????]
				//?????? ?????? ????????????
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
					//[??????(2018.02.12): ?????????] ????????? ??? ??????????????? ?????? ???????????? ?????? ????????? ?????? ???????????? ??????
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
				strMsg = msg1; // msg1 ??????
				strUrl = "/default/login";
				strTarget = "self";
			}
		}
		if(strUrl == "") {
			strMsg = msg1; // msg1 ??????
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

		// CookieBox ???????????? ???????????? request????????? ?????? ????????? ??????
	    CookieBox cookieBox = new CookieBox(request);

	    Cookie cookies1 = cookieBox.getCookie("Cookies1");
	    Cookie cookies2 = cookieBox.getCookie("Cookies2");

	    String userPhone = EncodeLibrary.AlmoneyDecrypt(cookies1.toString());
	    String userPasswd = EncodeLibrary.AlmoneyDecrypt(cookies2.toString());
	    String user_login_ip =  CommonUtil.getUserIP(request);

	    // ????????? ????????? ????????? ?????????????????? ??????
	    if (userPasswd.isEmpty()) {
	    	userPasswd = "";
	    }

	    //????????? ???????????? AutoLogin ?????? ?????? ??????

	    //[??????(2018.11.21): ?????????] ?????? ?????? !
	    //??????????????? ????????? ???????????? ????????? ????????????,
	    //????????? ??? ????????? ??????????????? ??? ????????? ????????? ????????????.
	    //????????? ??? ?????? "????????? ?????? ??????"??? ????????? ????????? ????????? ??? ??? ??? ????????????,
	    //??? ??? Cookies1/Cookies2??? ??????????????? ??? ????????? ????????? ???????????? ??????.
	    Session.setItem("AutoLogin", "Y");


	    String format = "yyyy-MM-dd aa hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(??????/?????? ??????)
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
	    	// ????????? ??????
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


	    			// ?????? ??????
	    			//session
	    			Session.setItem("Ver", 1);
	    			Session.setItem("UserSeq", userSeq);
	    			Session.setItem("UserPhoto", userPhoto);

	    			//MenuItem / MyInfo
	    			Session.setItem("UserNickName", userNickName);

	    			//AnswerList / AnswerView / MenuItem / MyInfo / MyRecomd / ?????? / AlPay
	    			Session.setItem("UserLv", userLv);

	    			//?????????????????? ?????? ??? ?????? ???????????? ?????????
	    			Session.setItem("UserDateReg", userDateReg);

	    			//MyInfo / MyRecomd
	    			Session.setItem("UserAlmoney", userAlmoney);

	    			//AlPay / AnswerList ??? ?????? ????????? ??????
	    			Session.setItem("RankQ", rankQ);

	    			//MenuItem / MyInfo
	    			Session.setItem("RankA", rankA);

	    			//MenuItem / MyInfo
	    			Session.setItem("FlagSelfAnswer", flagSelfAnswer);

	    			Session.setItem("MemberType", memberType);

	    			Session.setItem("SessExpire", 30);

	    			Session.setItem("UserEmail", userEmail);

	    			Session.setItem("UserAlpayKR", userAlpayKR);

	    			//[??????(2018.10.17): ?????????] ?????? ?????? ??????
	    			Session.setItem("Almaeng", almaeng);

	    			//AlPay
	    			Session.setItem("AlmaengCode", almaengCode);

	    			Session.setItem("nation", nation);			// ??????
	    			Session.setItem("lang", lang);				// ??????


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
	    				//[??????(2018.10.07): ?????????] ???????????? ????????? ???????????? ????????? ????????? https ??????????????? ???????????? ????????? CurrentPage??? ?????????????????? ???????????? ????????? MORMAL_SEND_URL??? ???????????? ????????? ??????
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
	    			// ????????? ??????
	    		}
	    	}


	    }
	    else {
	    	// ????????? ??????
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

			// UtilFile ?????? ??????
	        UtilFile utilFile = new UtilFile();

	        String originFileName = "";

	        String ThumbFileSaveName = "";

	        for (MultipartFile mf : fileList) {
	        	originFileName = mf.getOriginalFilename(); // ?????? ?????? ???

	        	if(CommonUtil.fn_FileType_Check(originFileName).equals("N")) {
					return "N";
				}

	        	Date time = new Date();
				SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmSS");
				String today = format1.format(time);

	        	String FileReName = userSeq + "_" + today;
	        	String FileExtension = FilenameUtils.getExtension(originFileName);
	        	String FileSaveName = FileReName + "." + FileExtension;

	        	// ?????? ????????? ???????????? path??? ????????????(?????? fileUpload() ??????????????? ?????? ????????? ???????????? ?????????)
		        String defaultFolder = "Profile";

		        String FilePath = utilFile.getSaveLocation(request, defaultFolder);
		        //System.out.println("FilePath : " + FilePath);

		        File fileDir = new File(FilePath);

		        if (!fileDir.exists()) {
		        	fileDir.mkdirs();
		        }

		        try { // ????????????
		        	//?????? ?????? ????????? ????????? ??????
		        	File original = new File(FilePath, FileSaveName);
		        	mf.transferTo(original);


		        	ThumbFileSaveName = FileReName + "_thumb." + FileExtension;


		        	File thumbnail = new File(FilePath, ThumbFileSaveName);

		        	Thumbnails.of(original).size(80, 80).outputFormat(FileExtension).toFile(thumbnail);

		        	if(thumbnail.exists()) {


			        	// ?????? ????????? ?????? db??? ??????
			        	HashMap<String, Object> param = new HashMap<String, Object>();
			        	param.put("thumbFileSaveName", ThumbFileSaveName);
			        	param.put("userseq", userSeq);
				        memberService.setUserProfileImg(param);


		        		//????????? ?????? ?????? ??????
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

			// UtilFile ?????? ??????
	        UtilFile utilFile = new UtilFile();

	        String originFileName = "";

	        int userseq = 0;
	        String ThumbFileSaveName = "";

	        String msg1 = messageSource.getMessage("msg_1044", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????

	        for (MultipartFile mf : fileList) {
	        	originFileName = mf.getOriginalFilename(); // ?????? ?????? ???

	        	if(CommonUtil.fn_FileType_Check(originFileName).equals("N")) {
	        		CommonUtil.jspAlert(response, msg1, "", ""); // msg1 ??????
					return "";
				}

	        	Date time = new Date();
				SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmSS");
				String today = format1.format(time);

	        	String FileReName = userseq + "_" + today;
	        	String FileExtension = FilenameUtils.getExtension(originFileName);
	        	String FileSaveName = FileReName + "." + FileExtension;

	        	// ?????? ????????? ???????????? path??? ????????????(?????? fileUpload() ??????????????? ?????? ????????? ???????????? ?????????)
		        String defaultFolder = "Profile";

		        String FilePath = utilFile.getSaveLocation(request, defaultFolder);


		        File fileDir = new File(FilePath);

		        if (!fileDir.exists()) {
		        	fileDir.mkdirs();
		        }

		        try { // ????????????
		        	//?????? ?????? ????????? ????????? ??????
		        	File original = new File(FilePath, FileSaveName);
		        	mf.transferTo(original);


		        	ThumbFileSaveName = FileReName + "_thumb." + FileExtension;


		        	File thumbnail = new File(FilePath, ThumbFileSaveName);

		        	Thumbnails.of(original).size(160, 160).outputFormat(FileExtension).toFile(thumbnail);

		        	if(thumbnail.exists()) {
		        		//????????? ?????? ?????? ??????
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


		String rcv_number_surem = country_code + "-" + rcv_number; //????????? ????????? ??????

		int randomValue = (int) (Math.random() * 10000) + 10000;
		String randValue = String.valueOf(randomValue);

		String tempPassword = randValue.substring(0, 4);

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("call_flag", 0);
		param.put("mem_country", country_code);
		param.put("mem_phone", en.AlmoneyEncrypt(rcv_number));
		param.put("sec_code", tempPassword);
		int dbReturnCode = memberService.setJoinCertSms(param);

		String msg1 = messageSource.getMessage("msg_1147", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg2 = messageSource.getMessage("msg_1148", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????

		if(dbReturnCode == 1) {
			String sms_content = msg1 + "[" + tempPassword + "]" + msg2; // msg1, msg2 ??????

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

		//String phone0 = CommonUtil.fn_Word_In(request.getParameter("Phone0"));	//????????????
		//String phone1 = request.getParameter("Phone1").substring(0, 3);
		//String phone2 = request.getParameter("Phone2").substring(0, 4);
		//String phone3 = request.getParameter("Phone3").substring(0, 4);

		/*
		????????????
		1: ??????
		86: ??????
		91: ??????
		84: ?????????
		62: ???????????????
		81: ??????
		82: ??????
		0: ??????
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

		String format = "yyyyMMdd";//yyyy-MM-dd aa hh:mm:ss(??????/?????? ??????)
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String rdate = type.format(today.getTime());

		String format2 = "hhmmss";//yyyy-MM-dd aa hh:mm:ss(??????/?????? ??????)
		SimpleDateFormat type2 = new SimpleDateFormat(format);
		String rtime = type2.format(today.getTime());

		int randomValue = (int) (Math.random() * 100000) + 100000;
		String randValue = String.valueOf(randomValue);
		//System.out.println("randValue : "+ randValue);
		String tempPassword = randValue.substring(0, 5);
		//System.out.println("tempPassword : "+ tempPassword);
		String snd_number = "023303000"; //???????????????

		rcv_number  = phone1 + phone2 + phone3; //????????? ??????-????????????

		String msg1 = messageSource.getMessage("msg_1129", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg2 = messageSource.getMessage("msg_1130", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg3 = messageSource.getMessage("msg_1131", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg4 = messageSource.getMessage("msg_1132", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????

		sms_content = "[" + msg1 + " " + msg2 + "] " + msg3 + " [" + tempPassword + "]" + msg4; // msg1 ~ 5 ??? ?????? ?????? --> msg1 ~ 4

		if(rcv_number.length() > 0 && rcv_number.substring(0, 1).equals("0")) {
			rcv_number = rcv_number.substring( 1, rcv_number.length());
		}
		//System.out.println("rcv_number : "+ rcv_number);

		rcv_number_surem = phone0 + "-" + rcv_number; //????????? ????????? ??????
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


		String msg6 = messageSource.getMessage("msg_1124", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg7 = messageSource.getMessage("msg_1133", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg8 = messageSource.getMessage("msg_1134", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg9 = messageSource.getMessage("msg_1135", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg10 = messageSource.getMessage("msg_1136", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg11 = messageSource.getMessage("msg_1137", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg12 = messageSource.getMessage("msg_1138", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg13 = messageSource.getMessage("msg_0318", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg14 = messageSource.getMessage("msg_0319", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		// ????????? ?????? ????????? ?????? ?????? ?????? ???????????? ???????????? ???????????? ??????.

		//dbReturnCode = 0;//????????? ??? ?????? ?????? ??????
		if(dbReturnCode == 1) {
			CommonUtil.jspAlert(response, msg6, "", "parent.parent"); // msg6 ??????
		}
		else if(dbReturnCode == 2) {
			CommonUtil.jspAlert(response, msg7, "", "parent.parent"); // msg7 ??????
		}
		else if(dbReturnCode == 3) {
			// msg8, msg9, msg10 ??????
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


			//System.out.println(sr.getMember());	// ????????? ???????????? ??????
			//System.out.println(sr.getStatus());	// ?????? ?????? (O : ??????)
			//System.out.println(String.format("SR: %s", sr)); // member = ????????? ?????????, status = O(??????) ?????????

			smsRet = String.valueOf(obj.get("result"));
			System.out.println("smsRet : " + smsRet);
			/*smsRet = sr.getStatus();*/

			if(smsRet.equals("success")) {
				CommonUtil.jspCertSend_n2(response, msg11); // msg11 ??????
			}
			else {
				CommonUtil.jspAlert(response, msg12, "", "parent.parent"); // msg12 ??????
			}
		}
		else {
			CommonUtil.jspAlert(response, msg13 + "\\n" + msg14, "", "parent.parent"); // msg13, msg14 ??????
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

        //?????? ??? ??????

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

            //?????? ??? (??????????????? ??????????????? 0??? ??????)
            //?????? ??? ????????? ?????????????????? FOR?????? ????????? ????????????
            XSSFSheet sheet = workbook.getSheetAt(shno);
            //?????? ???
            int rows = sheet.getPhysicalNumberOfRows();
            for(rowindex = 2; rowindex <= rows; rowindex++){
                //???????????????
                XSSFRow row = sheet.getRow(rowindex);
                if(row != null){
                    //?????? ???
                    //int cells=row.getPhysicalNumberOfCells();

                	XSSFCell cell0 = row.getCell(0);
                	XSSFCell cell1 = row.getCell(2);

                	String code = ExcelUtil.cellValue(cell0);
                	String codeValue = ExcelUtil.cellValue(cell1);

                	if(codeValue.contains("??????") || codeValue.equals("")) { continue; }

                	//System.out.println(rowindex+"??? / code : " + code +" / codeValue: " + codeValue);

                	System.out.println("UPDATE " + tbl + " SET " + col + " = '" + codeValue + "' WHERE Code = '" + code + "';");
                }
            }

        }catch(Exception e) {
            e.printStackTrace();
        }



        //System.out.println("@@@@@@@@@@@@@@@ExcelUp END@@@@@@@@@@@@@@@");

    }
}
