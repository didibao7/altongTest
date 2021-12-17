package com.altong.web.logic.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.function.BiPredicate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.altong.web.logic.LogAlmoneyVO;
import com.altong.web.logic.event.EventVO;
import com.altong.web.service.common.CommonService;
import com.altong.web.service.config.ConfigService;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.difflib.DiffUtils;
import com.github.difflib.algorithm.DiffException;
import com.github.difflib.algorithm.myers.MyersDiff;
import com.github.difflib.patch.Patch;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.cloud.translate.v3.DetectLanguageRequest;
import com.google.cloud.translate.v3.DetectLanguageResponse;
import com.google.cloud.translate.v3.DetectedLanguage;
import com.google.cloud.translate.v3.LocationName;
import com.google.cloud.translate.v3.TranslationServiceClient;
import com.google.gson.Gson;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

import jasp.util.pVector;
import jasp.vbs.operator;
import jasp.vbs.variant;
import jasp.vbs.vb;

public class CommonUtil {
	@Autowired
	static
	ConfigService configService;

	private static String SessExpire;
	private static JSONObject SESS;
	private static int Q_UserSeq;
	private static int UserSeq;
	private static String Ver = "0";

	private static int p_pagescnt = 1;
	private static int p_curpage = 1;
	private static int p_totpagecnt = 30;

	@Autowired
	CommonService commonService;

	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	public static MessageSource messageSource(String lang) {
	    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();    //리로드 가능(운영중 메시지 변경 가능)

	    messageSource.setBasename("classpath:/locale/messages_" + lang);

	    //messageSource.setCacheSeconds(3);    //캐싱 시간은 최대 3초
	    return messageSource;
	}

	public static String getLangMsg(HttpServletRequest request, String code) throws Exception {
		Locale locale = request.getLocale();
		String lang = locale.getLanguage();

		CookieBox cookieBox = new CookieBox(request);
		Cookie s_lang = cookieBox.getCookie("s_lang");

		if(s_lang != null) {
			lang = s_lang.getValue();
		}

		if(lang.contains("_")) {
			String[] langArr = lang.split("_");
			lang = langArr[0];
		}

		MessageSource messageSource = messageSource(lang);
		Locale new_locale = new Locale(lang);
		//System.out.println("new_locale : " + new_locale.toString());
		String msg1 = messageSource.getMessage(code, null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		return msg1;
	}

	public static String getUserIP(HttpServletRequest request) {

		String ip = request.getHeader("X-FORWARDED-FOR");
		//logger.info("TEST : X-FORWARDED-FOR : "+ip);

		if (ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
			//logger.info("TEST : Proxy-Client-IP : "+ip);
		}

		if (ip == null) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			//logger.info("TEST : WL-Proxy-Client-IP : "+ip);
		}

		if (ip == null) {
			ip = request.getHeader("HTTP_CLIENT_IP");
			//logger.info("TEST : HTTP_CLIENT_IP : "+ip);
		}

		if (ip == null) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			//logger.info("TEST : HTTP_X_FORWARDED_FOR : "+ip);
		}

		if (ip == null) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}

	public static String fn_Word_In(String checkVal) {
		if(checkVal != null) {
			/*.replaceAll("</script", "")
			.replaceAll("select", "")
			.replaceAll("update", "")
			.replaceAll("delete", "")
			.replaceAll("insert", "")
			.replaceAll("union", "")
			.replaceAll("applet", "")
			.replaceAll("layer", "")
			.replaceAll("ilayer", "")
			.replaceAll("body", "")
			.replaceAll("embed", "")
			.replaceAll("meta", "")
			.replaceAll("onerror", "")
			.replaceAll("frameset", "")
			.replaceAll("frame", "")
			.replaceAll("<style", "")
			.replaceAll("marquee", "")
			.replaceAll("&", "&amp;")
			.replaceAll("'", "&quot;")
			.replaceAll("|", "&#124;")
			.replaceAll("<", "&lt;")
			.replaceAll(Character.toString((char)34), "")*/
			checkVal.replaceAll("'", "&quot;")
								.replaceAll(">", "&gt;")
								.replaceAll(Character.toString((char)13) + "" + Character.toString((char)10), "");
		}

		return checkVal;
	}

	public static String fn_Word_Out(String checkVal) {
		if(checkVal != null) {
			checkVal.replaceAll("&quot;", "");
		}

		return checkVal;
	}

	public static String fn_File_In(String checkVal) {
		if(checkVal != null) {
			checkVal.replaceAll("~", "")
					.replaceAll("!", "")
					.replaceAll("@", "")
					.replaceAll("#", "")
					.replaceAll("$", "")
					.replaceAll("%", "")
					.replaceAll("^", "")
					.replaceAll("&", "")
					.replaceAll("\\*", "")
					.replaceAll("\\{", "")
					.replaceAll("\\}", "")
					.replaceAll("'", "")
					.replaceAll(";", "")
					.replaceAll("`", "");
		}
		return checkVal;
	}

	public static String fn_NickName_chk(String nickName) {
		Integer ret = 0;

		String nickNotUse = configService.getNickNameCheck();
		String[] nickNotUseList =  nickNotUse.split("/");

		for(String str : nickNotUseList) {
			if(str.contains(nickName)) {
				ret = 1;
				break;
			}
		}

		return nickName;
	}

	public static int nickNameChk(String nickName, String nickNotUse) {
		int result = 0;

		String[] arrNickNotUse = nickNotUse.split("/");
		String nickNotUseStr = "";
		for(int i = 0; i < arrNickNotUse.length; i++) {
			nickNotUseStr = arrNickNotUse[i];
			if(nickNotUseStr.contains(nickName)) {
				result = 1;
				break;
			}
		}

		return result;
	}

	public static void jspAlert(HttpServletResponse response, String strMsg, String strUrl, String strTarget)
			throws IOException {

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		//System.out.println(strMsg);
		out.println("<script>");

		if(!strMsg.trim().equals("")) {
			out.println("alert('" + strMsg + "');");
		}

		if(strUrl.trim().equals("back")) {
			out.println("history.back();");
		}
		else if(!strUrl.trim().equals("")) {
			if(strTarget.equals("")) {
				strTarget = "parent.parent";
			}
			out.println(strTarget + ".location.href = '" + strUrl + "';");
		}

		out.println("</script>");

		out.flush();
	}

	public static void jspAlertReload(HttpServletResponse response, String strMsg, String strUrl, String strTarget)
			throws IOException {

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		//System.out.println(strMsg);
		out.println("<script>");
		out.println("parent.location.reload();");
		if(strMsg.trim() != "") {
			out.println("alert('" + strMsg + "');");
		}

		if(strUrl.trim() == "back") {
			out.println("history.back();");
		}
		else if(strUrl.trim() != "") {
			out.println(strTarget + ".location.href = '" + strUrl + "';");
		}

		out.println("</script>");

		out.flush();
	}

	public static void jspCertSend(HttpServletResponse response, String strMsg) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println("<script>");

		if(strMsg.trim() != "") {
			out.println("alert('" + strMsg + "');");
		}

		out.println("parent.document.frm_sch.CertNum.focus();");

		out.println("</script>");

		out.flush();
	}

	public static void jspCertSend_n2(HttpServletResponse response, String strMsg) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println("<script>");

		if(strMsg.trim() != "") {
			out.println("alert('" + strMsg + "');");
		}

		out.println("parent.document.join_frm_sch.CertNum.focus();");

		out.println("</script>");

		out.flush();
	}

	public static void jspCertSendChking(HttpServletResponse response, boolean certChk) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println("<script>");

		out.println("parent.document.certChk_ing = "+ certChk +";");

		out.println("</script>");

		out.flush();
	}

	public static void jspNickPass(HttpServletResponse response, String strMsg, String strUrl, String strTarget) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println("<script>");

		if(strMsg.trim() != "") {
			out.println("alert('" + strMsg + "');");
		}

		out.println("parent.document.frm_sch.FlagNickName.value = 'Y';");

		out.println("</script>");

		out.flush();
	}

	public static void jspNickPass_n4(HttpServletResponse response, HttpServletRequest request, int nickUse) throws IOException {
		response.setContentType("text/html; charset=UTF-8");

		Locale locale = request.getLocale();
		String lang = locale.getLanguage();
		MessageSource messageSource = messageSource(lang);
		Locale new_locale = new Locale(lang);

		PrintWriter out = response.getWriter();

		String msg1 = messageSource.getMessage("msg_0999", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1000", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		out.println("<script>");

		if(nickUse > 0) {
			out.println("alert('" + msg1 + "');"); // '내용' 대신에 alert('" + msg1 + "') 로 수정
			out.println("parent.document.firstFrm.FlagNickName.value = 'N';");
		}
		else {
			out.println("alert('" + msg2 + "');"); // '내용' 대신에 alert('" + msg2 + "') 로 수정
			out.println("parent.document.firstFrm.FlagNickName.value = 'Y';");
		}

		out.println("</script>");

		out.flush();
	}

	public static void jspNickPass_n3(HttpServletResponse response, HttpServletRequest request, int nickUse) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		Locale locale = request.getLocale();
		String lang = locale.getLanguage();
		MessageSource messageSource = messageSource(lang);
		Locale new_locale = new Locale(lang);

		String msg1 = messageSource.getMessage("msg_0999", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0999", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		String msg3 = messageSource.getMessage("msg_1000", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_1000", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		out.println("<script>");

		if(nickUse > 0) {
			out.println("alert('" + msg1 + "');");	// '내용' 대신에 alert('" + msg1 + "') 로 수정
			out.println("parent.document.getElementById('atm_flag_nickname_check').innerHTML = '" + msg2 + "';");	// '내용' 대신에 alert('" + msg2 + "') 로 수정
			out.println("parent.document.getElementById('atm_flag_nickname_check').style.color = '#ff5001';");
			out.println("parent.document.join_frm_sch.FlagNickName.value = 'N';");
		}
		else {
			out.println("alert('" + msg3 + "');");		// '내용' 대신에 alert('" + msg3 + "') 로 수정
			out.println("parent.document.getElementById('atm_flag_nickname_check').innerHTML = '" + msg4 + "';");	// '내용' 대신에 alert('" + msg4 + "') 로 수정
			out.println("parent.document.getElementById('atm_flag_nickname_check').style.color = '#2ac1bc';");
			out.println("parent.document.join_frm_sch.FlagNickName.value = 'Y';");
		}

		out.println("</script>");

		out.flush();
	}

	public static void jspNickPass_n2(HttpServletResponse response, HttpServletRequest request, int nickUse) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		Locale locale = request.getLocale();
		String lang = locale.getLanguage();
		MessageSource messageSource = messageSource(lang);
		Locale new_locale = new Locale(lang);

		String msg1 = messageSource.getMessage("msg_0999", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0999", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		String msg3 = messageSource.getMessage("msg_1000", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_1000", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		out.println("<script>");

		if(nickUse > 0) {
			out.println("alert('" + msg1 + "');");	// '내용' 대신에 alert('" + msg1 + "') 로 수정
			out.println("parent.document.getElementById('atm_flag_nickname_check').innerHTML = '" + msg2 + "';");	// '내용' 대신에 alert('" + msg2 + "') 로 수정
			out.println("parent.document.getElementById('atm_flag_nickname_check').style.color = '#ff5001';");
			out.println("parent.document.frm_sch.FlagNickName.value = 'N';");
		}
		else {
			out.println("alert('" + msg3 +"');");		// '내용' 대신에 alert('" + msg3 + "') 로 수정
			out.println("parent.document.getElementById('atm_flag_nickname_check').innerHTML = '" + msg4 + "';");	// '내용' 대신에 alert('" + msg4 + "') 로 수정
			out.println("parent.document.getElementById('atm_flag_nickname_check').style.color = '#2ac1bc';");
			out.println("parent.document.frm_sch.FlagNickName.value = 'Y';");
		}

		out.println("</script>");

		out.flush();
	}

	public static void jspCertTimeChk(HttpServletResponse response, String msg, boolean phone2Chk, boolean phone3Chk, String flagCertNum) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println("<script>");

		if(msg.trim() != "") {
			out.println("alert('" + msg + "');");
		}

		out.println("parent.document.frm_sch.Phone2.readOnly = " + phone2Chk + ";");
		out.println("parent.document.frm_sch.Phone3.readOnly = " + phone3Chk + ";");
		out.println("parent.document.frm_sch.FlagCertNum.value = '" + flagCertNum + "';");

		out.println("</script>");

		out.flush();
	}

	public static void jspCertTimeChk_n2(HttpServletResponse response, String msg, boolean phone2Chk, boolean phone3Chk, String flagCertNum) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println("<script>");

		if(msg.trim() != "") {
			out.println("alert('" + msg + "');");
		}

		out.println("parent.document.join_frm_sch.Phone0.readOnly = " + phone2Chk + ";");
		out.println("parent.document.join_frm_sch.Phone1.readOnly = " + phone3Chk + ";");
		out.println("parent.document.join_frm_sch.FlagCertNum.value = '" + flagCertNum + "';");

		out.println("</script>");

		out.flush();
	}

	public static void jspAlertOpener(HttpServletResponse response, String strMsg, String strUrl, String strTarget) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println("<script>");

		if(strMsg.trim() != "") {
			out.println("alert('" + strMsg + "');");
		}
		out.println("opener.location.herf = '"+ strUrl +"';");
		//out.println("parent.document.frm_sch.FlagNickName.value = 'Y';");
		out.println("window.open('','_self');");
		out.println("window.close();");
		out.println("</script>");

		out.flush();
	}

	public static void jspCertConfirm(HttpServletResponse response, HttpServletRequest request) throws IOException {
		response.setContentType("text/html; charset=UTF-8");

		Locale locale = request.getLocale();
		String lang = locale.getLanguage();
		MessageSource messageSource = messageSource(lang);
		Locale new_locale = new Locale(lang);

		String msg1 = messageSource.getMessage("msg_1001", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1002", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_1003", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		PrintWriter out = response.getWriter();

		out.println("<script>");

		//다국어변수 적용예: out.println("if(confirm('" + msg1 + "\\n" + msg2 + "\\n" + msg3 + "')) { location.href = '/member/cert/index'; }");
		out.println("var result = confirm('" + msg1 + "\\n" + msg2 + "\\n" + msg3 + "');");
		out.println("if(confirm(result)) { location.href = '/member/cert/index'; }");
		out.println("else { history.back(); }");
		out.println("</script>");

		out.flush();
	}

	public static void jspAlert2(HttpServletResponse response, String strMsg, String strUrl, String strTarget)
			throws IOException {

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println("<script>");

		if(strMsg.trim() != "") {
			out.println("alert('" + strMsg + "');");
		}

		if(strUrl.trim() == "back") {
			out.println("history.back();");
		}
		else if(strUrl.trim() != "") {
			out.println(strTarget + ".location.replace('" + strUrl + "');");
		}

		out.println("</script>");

		out.flush();
	}

	public static String htmlSpecialChars(String msg) {
		StringBuffer sb = new StringBuffer();

		for(int i=0; i < msg.length(); i++){

			char c = msg.charAt(i);

			switch (c) {
			    case '<' :
			      sb.append("&lt;");
			      break;
			    case '>' :
			      sb.append("&gt;");
			      break;
			    case '&' :
			      sb.append("&amp;");
			      break;
			    case '"' :
			      sb.append("&quot;");
			      break;
			    case '\'' :
			      sb.append("&apos;");
			      break;
			    default:
			      sb.append(c);

			}
		}

		return sb.toString();
	}

	public static void jspLog(HttpServletResponse response, String val) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println("<script>");
		out.println("console.log('" + val + "');");
		out.println("</script>");

		out.flush();
	}

	public static Integer fn_StringLen(String strString) {
		Integer iLen = 0;
		char[] strChar;

		for(int iPos = 1; iPos <= strString.length(); iPos++) {
			strChar = Mid(strString, iPos, 1).toCharArray();

			if(strChar[0] < 0) //Asc(strChar)
				iLen = iLen + 2;
			else
				iLen = iLen + 1;
		}

		return iLen;
	}

	public static String Left(String str, int num) {
		if(num <= 0)
			return "";
		else if(num > str.length())
			return str;
		else
			return str.substring(0, num);
	}
	public static String Right(String str, int num) {
		if(num <= 0)
			return "";
		else if(num > str.length())
			return str;
		else {
			return str.substring(num, str.length());
		}
	}
	public static String Mid(String str, int start, int len) {
		return (Left(Right(str, str.length() - start), len));
	}

	public static String fn_Level(String strData, HttpServletRequest request) {
		Locale locale = request.getLocale();
		String lang = locale.getLanguage();

		MessageSource messageSource = messageSource(lang);

		Locale new_locale = new Locale(lang);

		String msg1 = messageSource.getMessage("msg_0137", null, locale);
		String msg2 = messageSource.getMessage("msg_0138", null, locale);
		String msg3 = messageSource.getMessage("msg_0139", null, locale);
		String msg4 = messageSource.getMessage("msg_0140", null, locale);
		String msg5 = messageSource.getMessage("msg_0141", null, locale);
		String msg6 = messageSource.getMessage("msg_0142", null, locale);
		String msg7 = messageSource.getMessage("msg_0143", null, locale);
		String msg8 = messageSource.getMessage("msg_0144", null, locale);
		String msg9 = messageSource.getMessage("msg_0145", null, locale);
		String msg10 = messageSource.getMessage("msg_0146", null, locale);
		String msg11 = messageSource.getMessage("msg_0147", null, locale);
		String msg12 = messageSource.getMessage("msg_0148", null, new_locale);
		String msg13 = messageSource.getMessage("msg_0149", null, new_locale);
		String msg14 = messageSource.getMessage("msg_0701", null, new_locale);

		String levelText = "";

		if(strData != "") {
			switch(strData) {
				case "1": 	levelText = msg1; 	break; //"알천사" 대신 msg1 변수명으로 대체,	levelText = msg1;// 이런식으로 나머지 코드로 작성.
				case "2": 	levelText = msg2; 	break; //"나비천사" 대신 msg2 변수명으로 대체
				case "3": 	levelText = msg3; 	break; //"미소천사" 대신 msg3 변수명으로 대체
				case "4": 	levelText = msg4; 	break; //"열혈천사" 대신 msg4 변수명으로 대체
				case "5": 	levelText = msg5; 	break; //"황금천사" 대신 msg5 변수명으로 대체
				case "6": 	levelText = msg6; 	break; //"수호천사" 대신 msg6 변수명으로 대체
				case "7": 	levelText = msg7; 	break; //"빛의천사" 대신 msg7 변수명으로 대체
				case "8": 	levelText = msg8; 	break; //"천사장" 대신 msg8 변수명으로 대체
				case "9": 	levelText = msg9; 	break; //"대천사" 대신 msg9 변수명으로 대체
				case "10": 	levelText = msg10; 	break; //"대천사장" 대신 msg10 변수명으로 대체
				case "11": 	levelText = msg11; 	break; //"알통천사" 대신 msg11 변수명으로 대체
				case "98": 	levelText = msg12; 	break; //"알돌이" 대신 msg12 변수명으로 대체
				case "99": 	levelText = msg13; 	break; //"관리자" 대신 msg13 변수명으로 대체
				default: 	levelText = msg14;				 //"준비중" 대신 msg14 변수명으로 대체
			}
		}

		return levelText;
	}

	public static String fn_LevelWithLocale(String strData, Locale locale) {
		String lang = locale.getLanguage();

		MessageSource messageSource = messageSource(lang);

		Locale new_locale = new Locale(lang);

		String msg1 = messageSource.getMessage("msg_0137", null, locale);
		String msg2 = messageSource.getMessage("msg_0138", null, locale);
		String msg3 = messageSource.getMessage("msg_0139", null, locale);
		String msg4 = messageSource.getMessage("msg_0140", null, locale);
		String msg5 = messageSource.getMessage("msg_0141", null, locale);
		String msg6 = messageSource.getMessage("msg_0142", null, locale);
		String msg7 = messageSource.getMessage("msg_0143", null, locale);
		String msg8 = messageSource.getMessage("msg_0144", null, locale);
		String msg9 = messageSource.getMessage("msg_0145", null, locale);
		String msg10 = messageSource.getMessage("msg_0146", null, locale);
		String msg11 = messageSource.getMessage("msg_0147", null, locale);
		String msg12 = messageSource.getMessage("msg_0148", null, new_locale);
		String msg13 = messageSource.getMessage("msg_0149", null, new_locale);
		String msg14 = messageSource.getMessage("msg_0701", null, new_locale);

		String levelText = "";

		if(strData != "") {
			switch(strData) {
				case "1": 	levelText = msg1; 	break; //"알천사" 대신 msg1 변수명으로 대체,	levelText = msg1;// 이런식으로 나머지 코드로 작성.
				case "2": 	levelText = msg2; 	break; //"나비천사" 대신 msg2 변수명으로 대체
				case "3": 	levelText = msg3; 	break; //"미소천사" 대신 msg3 변수명으로 대체
				case "4": 	levelText = msg4; 	break; //"열혈천사" 대신 msg4 변수명으로 대체
				case "5": 	levelText = msg5; 	break; //"황금천사" 대신 msg5 변수명으로 대체
				case "6": 	levelText = msg6; 	break; //"수호천사" 대신 msg6 변수명으로 대체
				case "7": 	levelText = msg7; 	break; //"빛의천사" 대신 msg7 변수명으로 대체
				case "8": 	levelText = msg8; 	break; //"천사장" 대신 msg8 변수명으로 대체
				case "9": 	levelText = msg9; 	break; //"대천사" 대신 msg9 변수명으로 대체
				case "10": 	levelText = msg10; 	break; //"대천사장" 대신 msg10 변수명으로 대체
				case "11": 	levelText = msg11; 	break; //"알통천사" 대신 msg11 변수명으로 대체
				case "98": 	levelText = msg12; 	break; //"알돌이" 대신 msg12 변수명으로 대체
				case "99": 	levelText = msg13; 	break; //"관리자" 대신 msg13 변수명으로 대체
				default: 	levelText = msg14;				 //"준비중" 대신 msg14 변수명으로 대체
			}
		}

		return levelText;
	}

	public static String fn_RandomCode(String strString) {
		long seed = System.currentTimeMillis();
		Random rand = new Random(seed);
		int bound = 9;
		String randomCode = Integer.toBinaryString(1+ rand.nextInt(bound)); // 0~8 범위 균등분포에서 랜덤 정수형을 가져와서 + 1 = 1~9 숫자 발생

		return randomCode;
	}

	public static Long uploadMaxSize(Long maxSize) {
		Long uploadMaxSize = maxSize * 1024 * 1024;

		return uploadMaxSize;
	}

	public static String fn_FileType_Check(String fileName) {
		String fileExt;
		String checkValue = "N";

		String[] spl_FileExt = fileName.split("\\.");

		Integer spl_Count = spl_FileExt.length;

		fileExt = spl_FileExt[spl_Count - 1].toUpperCase();

		if(fileExt.equals("JPG") || fileExt.equals("GIF") || fileExt.equals("PNG") || fileExt.equals("JPEG")) {
			checkValue = "Y";
		}

		return checkValue;
	}

	public static String charToString(char str) {
		String change = Character.toString(str);

		return change;
	}

	public static String fn_CutStr(String str, Integer cutLen) {
		String strRes = "";

		Integer strLen = 0;
		Integer strByte = 0;
		char strCut;
		char ch;

		for(int i = 1; i <= strLen; i++) {
			char[] strCutArr = Mid(str, i, 1).toCharArray();
			strCut = strCutArr[0];

			char[] chArr = Left( Character.toString(strCut), 1).toCharArray();
			ch = chArr[0]; // Asc(strCut)

			if(ch == '-')
				strByte = strByte + 2;
			else
				strByte = strByte + 1;

			if(cutLen < strByte) {
				strRes = strRes + "";
				break;
			}
			else {
				strRes = strRes + strCut;
			}
		}

		return strRes;
	}

	public static String fn_Bank(String strData, HttpServletRequest request) {
		Locale locale = request.getLocale();
		String lang = locale.getLanguage();
		MessageSource messageSource = messageSource(lang);
		Locale new_locale = new Locale(lang);

		String msg1 = messageSource.getMessage("msg_0731", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0722", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0722", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_1004", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_0724", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg6 = messageSource.getMessage("msg_0723", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg7 = messageSource.getMessage("msg_0725", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg8 = messageSource.getMessage("msg_0726", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg9 = messageSource.getMessage("msg_0727", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg10 = messageSource.getMessage("msg_0732", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg11 = messageSource.getMessage("msg_0183", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg12 = messageSource.getMessage("msg_1005", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg13 = messageSource.getMessage("msg_1006", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg14 = messageSource.getMessage("msg_0660", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg15 = messageSource.getMessage("msg_1007", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg16 = messageSource.getMessage("msg_1007", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg17 = messageSource.getMessage("msg_0807", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg18 = messageSource.getMessage("msg_0245", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg19 = messageSource.getMessage("msg_0230", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg20 = messageSource.getMessage("msg_0230", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg21 = messageSource.getMessage("msg_0230", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg22 = messageSource.getMessage("msg_0230", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg23 = messageSource.getMessage("msg_0733", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		String tradeText = "";

		if(strData != "") {
			switch(strData) {
				case "Join": 			tradeText = msg1; 		break;	// tradeText = msg1; // 이런식으로 나머지 코드도 작성. 가입금 --> 기본활동자금
				case "Question": 		tradeText = msg2; 		break;  // 질문비용 --> 감사알
				case "Answer": 			tradeText = msg3; 		break;  // 답변채택 --> 감사알
				case "View": 			tradeText = msg4; 		break;  // 광고 없는 게시물 열람 시 알이 차감되는 경우
				case "ViewQ": 			tradeText = msg5; 		break;  // 질문열람 --> 열람(질문)
				case "ViewA": 			tradeText = msg6; 		break;  // 답변수익 --> 열람(답변)
				case "Esti": 			tradeText = msg7; 		break;
				case "ViewRQ": 			tradeText = msg8;		break;
				case "ViewRA": 			tradeText = msg9; 		break;
				case "Refund": 			tradeText = msg10; 		break;
				case "Event": 			tradeText = msg11; 		break;
				case "Withdraw": 		tradeText = msg12; 		break;
				case "Exchange": 		tradeText = msg13; 		break;
				case "ExchangeAlpay": 	tradeText = msg14; 		break;
				case "Answerer": 		tradeText = msg15;		break;
				case "Answerer_Admin": 	tradeText = msg16; 		break;
				case "Loan": 			tradeText = msg17; 		break;
				case "1": 				tradeText = msg18; 		break;
				case "2": 				tradeText = msg19; 		break;
				case "3": 				tradeText = msg20; 		break;
				case "4": 				tradeText = msg21; 		break;
				case "5": 				tradeText = msg22; 		break;
				default: 				tradeText = msg23;
			}
		}

		return tradeText;
	}

	public static String fn_Phone(String strData) {
		String phoneNumber = "";

		if(strData.length() == 11)
			phoneNumber = Left(strData, 3) + " - " + Mid(strData, 4, 4) + " - " + Right(strData, 4);
		else if(strData.length() == 10)
			phoneNumber = Left(strData, 3) + " - " + Mid(strData, 4, 3) + " - " + Right(strData, 4);
		else
			phoneNumber = strData;

		return phoneNumber;
	}

	public static String get_Paging_Tag_New_02(Integer pageTotalcnt, Integer page_num, Integer page_size,
												Integer intNumOfPage, String page_url, String page_get_data) {
		Integer total_block = 0;
		Integer preNum = 0;
		Integer nextNum = 0;
		Integer intStart = 0;
		Integer intEnd = 0;
		Integer blockPreNum = 0;
		Integer blockNextNum = 0;
		String s_tag = "";
		Integer n_page = 0;

		if(pageTotalcnt > 0) {
			total_block = (int)Math.floor(pageTotalcnt / page_size);
			if(pageTotalcnt % page_size > 0) {
				total_block = total_block + 1;
			}

			intStart = (page_num-1) / intNumOfPage * intNumOfPage + 1;
			intEnd = ((page_num-1) + intNumOfPage) / intNumOfPage * intNumOfPage;

			if(intEnd > total_block)
				intEnd = total_block;

			if(page_num > 1)
				preNum = page_num - 1;

			if(intEnd > page_num)
				nextNum = page_num + 1;

			if(intStart > intNumOfPage)
				blockPreNum	= intStart - 1;

			if(intEnd < total_block)
				blockNextNum	= intEnd + 1;

			//처음,이전버튼
			if(page_num > 0 ) {
				if(blockPreNum > 0) {
					s_tag = s_tag + "<a href='"+ page_url +"?pg="+ blockPreNum +"&"+ page_get_data +"'><p class='atm_boardnavi_el'>&lt;&lt;</p></a>";
				}
				if(preNum > 0) {
					s_tag = s_tag + "<a href='"+ page_url +"?pg="+ preNum +"&"+ page_get_data +"'><p class='atm_boardnavi_el'>&lt;</p></a>";
				}
			}

			//페이징번호
			if(intEnd > 1) {
				for(n_page = intStart; n_page <= intEnd; n_page++) {
					if(n_page == page_num) {
						s_tag = s_tag + "<a href='#'><p class='atm_boardnavi_el_on'>" + n_page + "</p>";
					}
					else {
						s_tag = s_tag + "<a href='"+ page_url +"?pg="+ n_page +"&"+ page_get_data +"'><p class='atm_boardnavi_el'>" + n_page + "</p>";
					}
				}
			}

			if(nextNum > 0) {
				s_tag = s_tag + "<a href='"+ page_url +"?pg="+ nextNum +"&"+ page_get_data +"'><p class='atm_boardnavi_el'>&gt;</p></a>";
			}
			if(blockNextNum > 0) {
				s_tag = s_tag + "<a href='"+ page_url +"?pg="+ blockNextNum +"&"+ page_get_data +"'><p class='atm_boardnavi_el'>&gt;&gt;</p></a>";
			}
		}

		return s_tag;
	}

	public static String textChanger(String contents, String[] changerArray) {
		for(int i = 0; i < changerArray.length; i++) {
			contents = contents.replace(changerArray[i], "<strong>" + changerArray[i] + "</strong>");
		}
		return contents;
	}

	// ASP getAlarmCountSum() 미사용
	/*
	public static String getAlarmCountSum() {
		String returnValue = "";



		return returnValue;
	}
	*/

	public static String bytesToStr(byte[] bytes) {
		return bytes.toString();
	}

	// ASP JsonData(ByVal rs) SP2_ALMONEY_STATISTICS_VIEW Returns(Integer) : output
	public static String JsonData(variant rs) throws Exception {
		String data = "";
        int columnCount = 0;
        int colIndex = 0;
        int rowIndex = 0;
        int rowCount = 0;
        variant rsArray = new variant();
        String JSONData = "";
        //Converts recordset to JSON data
        if (!(operator.get(rs,"EOF").toBool())) {
            data = "[";
            rsArray.set(operator.get(rs,"GetRows"));
            rowIndex = 0;
        } else {
            if(true) return JSONData;
        }
        //Retrieve the total no. of rows (second dimension of the array)
        rowCount = vb.UBound(rsArray, 2);
        //Retrive the total no. of columns/fields (first dimension of the array)
        columnCount = vb.UBound(rsArray, 1);
        //Loop through the array holding the resultset and display records
        //Loop through rows as the outer loop
        for(rowIndex = 0; rowIndex <= rowCount; rowIndex += 1){
            data = data + "{";
            //Loop through columns/fields as inner loop
            for(colIndex = 0; colIndex <= columnCount; colIndex += 1){
                if (operator.get(operator.invoke(rs,"Fields",new pVector().add(colIndex)),"Name").equals(new variant("Phone")) && rsArray.elementAt(colIndex ,rowIndex).unequals(new variant(""))) {
                    data = data + "\"" + operator.get(operator.invoke(rs,"Fields",new pVector().add(colIndex)),"Name") + "\"" + ":\"" + rsArray.elementAt(colIndex ,rowIndex) + "\"";
                } else {
                    data = data + "\"" + operator.get(operator.invoke(rs,"Fields",new pVector().add(colIndex)),"Name") + "\"" + ":\"" + rsArray.elementAt(colIndex ,rowIndex) + "\"";
                }
                if (colIndex < columnCount) {
                    data = data + ",";
                }
            }
            //Move on to next column/field is there is one
            data = data + "}";
            if (rowIndex < rowCount) {
                data = data + ",";
            }
        }
        //Move on to next row if there is one
        data = data + "]";
        operator.invoke(rs,"Close",null);
        JSONData = data;
        return JSONData;
	}


	private final static String[] hexBits = {
        "0000", "0001", "0010", "0011",
        "0100", "0101", "0110", "0111",
        "1000", "1001", "1010", "1011",
        "1100", "1101", "1110", "1111"
    };

	/**
     *
     * @param hexStr
     * @return
     */
    private boolean isHex(String hexStr)
    {
        for (int i = 0; i < hexStr.length(); i++) {
            if (0 > Character.digit(hexStr.toLowerCase().charAt(i), 16)) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param hexStr
     */
    private static String convertHexToBin(String hexStr)
    {
        String result = null;
        //System.out.printf("The equivalent binary for hexadecimal %1$s is ", hexStr);

        for (int i = 0; i < hexStr.length(); i++) {
            int j = Character.digit(hexStr.toLowerCase().charAt(i), 16);
            //System.out.printf("%1$s ", hexBits[j]);

            result += hexBits[j];
        }
        //System.out.println();

        return result;
    }

	public static String libEncode(String sess) throws Exception {

		//aes-256-cbc = 32(AES-256)
		//System.out.println("libEncode sess : " + sess);

		String s = AES256Util.aes_encode(sess);

		return s;
	}

	public static String libDecode(String sess) throws Exception {

		//System.out.println("sess = " + sess);
		String s = AES256Util.aes_decode(sess);

		return s;
	}


	public static void libSetSessCookie(Object jsonObject, HttpServletResponse response) throws Exception {

		LocalTime currentTime = LocalTime.now();
		String json = new Gson().toJson(jsonObject);
		String enText = libEncode(json);

		//System.out.println("SessExpire : " + SessExpire);
		Integer exp = (int) (( (SessExpire != null && SessExpire != "null") ? Integer.parseInt(SessExpire) : 1) * 3600 * 24);

		//쿠키 저장

		Cookie cookie = new Cookie("SESS", enText);
		cookie.setPath("/");
		cookie.setMaxAge(exp);

		Cookie sesc = new Cookie("SESC", Ver);
		sesc.setPath("/");
		sesc.setMaxAge(exp);
	}

	public static Map<String, Object> libLogin(Cookie sess, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		Locale locale = request.getLocale();
		//System.out.println("toLanguageTag : " + locale.getLanguage());
		String[] langArr = locale.toString().split("_");
		String lang = locale.getLanguage();

		MessageSource messageSource = messageSource(lang);

		Locale new_locale = new Locale(lang);
		//System.out.println("new_locale2 : " + new_locale.toString());
		CookieBox cookieBox = new CookieBox(request);
		ObjectMapper mapper = new ObjectMapper(new JsonFactory());
		String encode_data =  sess.getValue();
		//System.out.println("libLogin encode_data : " + encode_data);
		String decode_data = libDecode(encode_data);
		//System.out.println("libLogin decode_data : " + decode_data);
		String url_decode = URLDecoder.decode(decode_data,"UTF-8");
		//System.out.println("libLogin url_decode : " + url_decode);

		String mapString = mapper.writeValueAsString(url_decode);
		//System.out.println("libLogin mapString : " + mapString);

		ObjectMapper objectMapper = new ObjectMapper();

		Map<String, Object> map_data = objectMapper.readValue(url_decode, new TypeReference<Map<String, Object>>() {});
		String converted = objectMapper.writeValueAsString(map_data).replaceAll(" ","");


		JSONParser jsonParse = new JSONParser();
    	JSONObject jsonObj = (JSONObject) jsonParse.parse(converted);


		SESS = jsonObj;
		//System.out.println("jsonObj : " + jsonObj);
		if(jsonObj.get("UserSeq") != null) {
			UserSeq = Integer.parseInt(jsonObj.get("UserSeq").toString());
			result.put("Q_UserSeq", UserSeq);
			result.put("UserSeq", UserSeq);
			result.put("SESS", SESS);
		}
		else {
			result.put("error", false);
			result.put("errorType", 0);
			result.put("errorMsg", "");
			result.put("SESS", "");
		}

		//System.out.println("libLogin result : " + result);


		if(cookieBox.getCookie("SESS") != null) { libSetSessCookie(result.get("SESS"), response); }

		String uu = "00000001";
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime dd = LocalDateTime.of(2019, 1, 27, 15, 0, 0);

		String msg1 = messageSource.getMessage("msg_0134", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0135", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		//System.out.println("msg1 : " + msg1);
		if(Integer.toString(UserSeq) == uu && now.isBefore(dd)) {
			result.put("error", true);
			result.put("errorType", 2);

			// result.put("errorMsg", msg1 + " " + dd + "" + msg2); // 처럼 아래 코드를 수정.
			result.put("errorMsg", msg1 + dd + msg2);
		}

		return result;
	}

	public static String libJsonExit(Object sess, Object arr) {
		Map<String, Object> arrs = new HashMap<String, Object>();
		arrs.put("result", sess);
		arrs.put("arr", arr);

		String json = new Gson().toJson(arrs);

		return json;
	}

	public static String libJsonArrExit(Object sess, List<?> arr) {
		Map<String, Object> arrs = new HashMap<String, Object>();
		arrs.put("result", sess);
		arrs.put("arr", arr);

		String json = new Gson().toJson(arrs);

		return json;
	}

	public static String libJsonMsgExit(Object sess, HashMap<String, Object> msg) {
		Map<String, Object> arrs = new HashMap<String, Object>();
		arrs.put("result", sess);
		arrs.put("arr", msg);

		String json = new Gson().toJson(arrs);

		return json;
	}

	public static String get_Paging_Tag_New(int pagescnt, int curpage, int totpagecnt, String formName) {
		p_pagescnt = pagescnt;
		p_curpage = curpage;
		p_totpagecnt = totpagecnt;

		int n_start_page = 1;
		int n_end_page = 0;
		String s_tag = "";
		int n_page = 0;

		if(p_totpagecnt > 0) {
			n_start_page = ( ((p_curpage - 1) / p_pagescnt ) * p_pagescnt ) + 1;
			n_end_page = n_start_page + (p_pagescnt - 1);

			if(n_end_page >= p_totpagecnt) {
				n_end_page = p_totpagecnt;
			}
		}

		//페이징번호
		if(n_start_page > 0 && n_end_page > 0 && n_end_page >= n_start_page) {
			n_page = n_start_page;

			for(n_page = n_start_page; n_page <= n_end_page; n_page++) {
				if(n_page == p_curpage) {
					s_tag = s_tag + "<a href='#'><p class='atm_boardnavi_el_on'>" + n_page + "</p>";
				}
				else {
					s_tag = s_tag + "<a href=\"javascript:go_list_page('" + formName + "', '" + n_page + "');\"><p class='atm_boardnavi_el'>" + n_page + "</p>";
				}
			}
		}

		//처음,이전버튼
		if(p_curpage != 1 && p_totpagecnt > 0) { //첫 페이지가 아닌 경우
			int prevPage = p_curpage - 1;
			prevPage = (int)((Math.ceil(p_curpage / 7) - 1) * 7);

			s_tag = s_tag + "<div class='atm_boardnavi_arrow'><a href=\"javascript:go_list_page('" + formName + "', '1');\"><p class='atm_boardnavi_el'><<</p></a>";
			s_tag = s_tag + "<a href=\"javascript:go_list_page('" + formName + "', '" + prevPage + "');\"><p class='atm_boardnavi_el'><</p></a></div>";
		}


		//다음,마지막으로 버튼
		if(p_curpage != p_totpagecnt && p_totpagecnt > 0) {
			int nextPage = p_curpage - 1;
			nextPage = ((nextPage - (nextPage % p_pagescnt)) / p_pagescnt) * p_pagescnt + (p_pagescnt + 1);

			s_tag = s_tag + "<div class='atm_boardnavi_arrow'><a href=\"javascript:go_list_page('" + formName + "', '" + nextPage + "');\"><p class='atm_boardnavi_el'>></p></a>";
			s_tag = s_tag + "<a href=\"javascript:go_list_page('" + formName + "', '" + p_totpagecnt + "');\"><p class='atm_boardnavi_el'>>></p></a></div>";
		}

		return s_tag;
	}

	public static String get_Paging_Tag_New_ver2(int pagescnt, int curpage, int totpagecnt, String formName, String url) {
		p_pagescnt = pagescnt;
		p_curpage = curpage;
		p_totpagecnt = totpagecnt;

		int n_start_page = 1;
		int n_end_page = 0;
		String s_tag = "<div class='center'>";
		s_tag = s_tag + "<div class='atm_boardnavi'><ul>";
		int n_page = 0;

		if(p_totpagecnt > 0) {
			n_start_page = ( ((p_curpage - 1) / p_pagescnt ) * p_pagescnt ) + 1;
			n_end_page = n_start_page + (p_pagescnt - 1);

			if(n_end_page >= p_totpagecnt) {
				n_end_page = p_totpagecnt;
			}
		}

		//페이징번호
		if(n_start_page > 0 && n_end_page > 0 && n_end_page >= n_start_page) {
			n_page = n_start_page;

			for(n_page = n_start_page; n_page <= n_end_page; n_page++) { // url + "&pg=" + req_PG
				if(n_page == p_curpage) {
					s_tag = s_tag + "<li class='on'><a  href='" + url + "&pg="+ n_page +"'>" + n_page + "</a></li>";
				}
				else {
					s_tag = s_tag + "<li><a href='" + url + "&pg="+ n_page +"'>" + n_page + "</a></li>";
				}
			}
		}
		s_tag = s_tag +"</ul></div>";

		s_tag = s_tag +"<div class='atm_boardnavi_arrow'>";
		//처음,이전버튼
		if(p_curpage != 1 && p_totpagecnt > 0) { //첫 페이지가 아닌 경우
			int prevPage = p_curpage - 1;
			//prevPage = (int)((Math.ceil(p_curpage / 5) - 1) * 5);
			//if(prevPage == 0) prevPage = 1;

			s_tag = s_tag + "<div class='p_left'><div class='arrow_strong_left' onclick='location.href=\""+url+"&pg="+1+"\"'><a><img src='/Common/images/strong_left_arrow.svg'></a></div>";
			s_tag = s_tag + "<div class='arrow_left' onclick='location.href=\""+url+"&pg="+prevPage+"\"'><a><img src='/Common/images/left_arrow.svg'></a></div></div>";
		}


		//다음,마지막으로 버튼
		if(p_curpage != p_totpagecnt && p_totpagecnt > 0) {
			int nextPage = p_curpage - 1;
			nextPage = ((nextPage - (nextPage % p_pagescnt)) / p_pagescnt) * p_pagescnt + (p_pagescnt + 1);

			s_tag = s_tag + "<div class='p_right'><div class='arrow_strong_right' onclick='location.href=\""+url+"&pg="+nextPage+"\"'><a><img src='/Common/images/right_arrow.svg'></a></div>";
			s_tag = s_tag + "<div class='arrow_right' onclick='location.href=\""+url+"&pg="+p_totpagecnt+"\"'><a><img src='/Common/images/strong_right_arrow.svg'></a></div></div>";
		}
		s_tag = s_tag + "</div>";
		s_tag = s_tag + "</div>";
		return s_tag;
	}

	public static String get_Paging_Tag_New2(int pagescnt, int curpage, int totpagecnt, String formName) {
		p_pagescnt = pagescnt;
		p_curpage = curpage;
		p_totpagecnt = totpagecnt;

		int n_start_page = 1;
		int n_end_page = 0;
		String s_tag = "";
		int n_page = 0;

		if(p_totpagecnt > 0) {
			n_start_page = ( ((p_curpage - 1) / p_pagescnt ) * p_pagescnt ) + 1;
			n_end_page = n_start_page + (p_pagescnt - 1);

			if(n_end_page >= p_totpagecnt) {
				n_end_page = p_totpagecnt;
			}
		}

		//처음,이전버튼
		if(p_curpage != 1 && p_totpagecnt > 0) { //첫 페이지가 아닌 경우
			s_tag = s_tag + "<a href=\"javascript:go_list_page('" + formName + "', '" + 1 + "');\"><처음</a>";
			s_tag = s_tag + "<a href=\"javascript:go_list_page('" + formName + "', '" + (n_start_page - 1) + "');\"><</a>";
		}

		//페이징번호
		if(n_start_page > 0 && n_end_page > 0 && n_end_page >= n_start_page) {
			for(n_page = n_start_page; n_page <= n_end_page; n_page++) {
				if(n_page == p_curpage) {
					s_tag = s_tag + "<a href='#' style='color:red;'>" + n_page + "";
				}
				else {
					s_tag = s_tag + "<a href=\"javascript:go_list_page('" + formName + "', '" + n_page + "');\">" + n_page + "";
				}
			}
		}


		//다음,마지막으로 버튼
		if(p_curpage != p_totpagecnt && p_totpagecnt > 0) {
			s_tag = s_tag + "<a href=\"javascript:go_list_page('" + formName + "', '" + (n_end_page + 1) + "');\">></a>";
			s_tag = s_tag + "<a href=\"javascript:go_list_page('" + formName + "', '" + p_totpagecnt + "');\">>마지막</a>";
		}

		return s_tag;
	}

	public static String get_Paging_Tag_New2_ver2(int pagescnt, int curpage, int totpagecnt, String formName, String url) {
		p_pagescnt = pagescnt;
		p_curpage = curpage;
		p_totpagecnt = totpagecnt;

		int n_start_page = 1;
		int n_end_page = 0;
		String s_tag = "";
		int n_page = 0;

		if(p_totpagecnt > 0) {
			n_start_page = ( ((p_curpage - 1) / p_pagescnt ) * p_pagescnt ) + 1;
			n_end_page = n_start_page + (p_pagescnt - 1);

			if(n_end_page >= p_totpagecnt) {
				n_end_page = p_totpagecnt;
			}
		}
		//" + url + "&pg=" + p_totpagecnt + "
		//처음,이전버튼
		if(p_curpage != 1 && p_totpagecnt > 0) { //첫 페이지가 아닌 경우
			s_tag = s_tag + "<a href=\"" + url + "&pg=" + 1 + "\"><처음</a>";
			s_tag = s_tag + "<a href=\"" + url + "&pg=" + (n_start_page - 1) + "\"><</a>";
		}

		//페이징번호
		if(n_start_page > 0 && n_end_page > 0 && n_end_page >= n_start_page) {
			for(n_page = n_start_page; n_page <= n_end_page; n_page++) {
				if(n_page == p_curpage) {
					s_tag = s_tag + "<a href='#' style='color:red;'>" + n_page + "";
				}
				else {
					s_tag = s_tag + "<a href=\"" + url + "&pg=" + n_page + "\">" + n_page + "";
				}
			}
		}


		//다음,마지막으로 버튼
		if(p_curpage != p_totpagecnt && p_totpagecnt > 0) {
			s_tag = s_tag + "<a href=\"" + url + "&pg=" + (n_end_page + 1) + "\">></a>";
			s_tag = s_tag + "<a href=\"" + url + "&pg=" + p_totpagecnt + "\">>마지막</a>";
		}

		return s_tag;
	}

	public static boolean isNumeric(String input) {
		try {
			Double.parseDouble(input);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}

	public static String arrayToString(String[] targetList) {
		String result = "";

		for(int i = 0; i < targetList.length; i++) {
			result += "'" + targetList[i] + "'";

			if(i < (targetList.length - 1)) {
				result += ",";
			}
		}

		return result;
	}

	public static boolean useKeyCheckValue(String targetValue, Map<String, String> chkarray) {
	  return chkarray.containsValue(targetValue);
	}

	public static <T> Patch<T> diff(List<T> original, List<T> revised,
	    BiPredicate<T, T> equalizer) throws DiffException {
	    if (equalizer != null) {
	        return DiffUtils.diff(original, revised,
	        new MyersDiff<>(equalizer));
	    }
	    return DiffUtils.diff(original, revised, new MyersDiff<>());
	}

	public static HashMap<String, Object> convertJsonToHash(HttpServletRequest request, JSONObject jsonObj2) throws Exception {
		HashMap<String, Object> trandData = new HashMap<String, Object>();

		Iterator iter = jsonObj2.keySet().iterator();

		while(iter.hasNext()){
		   String key = (String) iter.next();
		   Object value = jsonObj2.get(key);

		   double dblValue = Double.parseDouble(value.toString());

		   String keyValue = key;

		   if(key.equals("감사알")) { keyValue = getLangMsg(request, "msg_0722"); }
		   else if(key.equals("훈훈알(답변)")) { keyValue = getLangMsg(request, "msg_0729"); }
		   else if(key.equals("답변 열람")) { keyValue = getLangMsg(request, "msg_0723"); }
		   else if(key.equals("ANSWERer")) { keyValue = getLangMsg(request, "msg_1007"); }
		   else if(key.equals("추천인(답변)")) { keyValue = getLangMsg(request, "msg_0727"); }
		   else if(key.equals("추천인(질문)")) { keyValue = getLangMsg(request, "msg_0726"); }
		   else if(key.equals("답변 평가")) { keyValue = getLangMsg(request, "msg_0725"); }
		   else if(key.equals("이벤트(답변)")) { keyValue = getLangMsg(request, "msg_0730"); }
		   else if(key.equals("활동 자금")) { keyValue = getLangMsg(request, "msg_0731"); }
		   else if(key.equals("훈훈알(질문)")) { keyValue = getLangMsg(request, "msg_0728"); }
		   else if(key.equals("질문 열람")) { keyValue = getLangMsg(request, "msg_0724"); }
		   else if(key.equals("환급")) { keyValue = getLangMsg(request, "msg_0732"); }
		   else if(key.equals("기타")) { keyValue = getLangMsg(request, "msg_0733"); }
		   else if(key.equals("퀴즈")) { keyValue = getLangMsg(request, "msg_0735"); }
		   else if(key.equals("퀴즈신청취소환급")) { keyValue = getLangMsg(request, "msg_0753"); }
		   else if(key.equals("이벤트(룰렛)")) { keyValue = getLangMsg(request, "msg_0754"); }
		   else if(key.equals("번역")) { keyValue = getLangMsg(request, "msg_0755"); }
		   else if(key.equals("꼭대기")) { keyValue = getLangMsg(request, "msg_0245"); }
		   else if(key.equals("퀴즈참가")) { keyValue = getLangMsg(request, "msg_0756"); }
		   else if(key.equals("번역의뢰")) { keyValue = getLangMsg(request, "msg_0757"); }
		   else if(key.equals("알통환수")) { keyValue = getLangMsg(request, "msg_1071"); }
		   else if(key.equals("훈훈알(댓글)")) { keyValue = getLangMsg(request, "msg_1184"); }

		   trandData.put(keyValue, CommonUtil.convertDoubleToDecimal(dblValue));

		}

		return trandData;
	}

	public static String convertDoubleToDecimal( double str) {
		DecimalFormat format = new DecimalFormat("######.0000");

		String result = format.format(str);

		return result;
	}

	public static String convertToDecimal( Object str) {
		DecimalFormat format = new DecimalFormat("######.0000");

		BigDecimal f = new BigDecimal("0.0000");
		if(str != null) {
			f = new BigDecimal(str.toString());
			f = f.setScale(1, BigDecimal.ROUND_FLOOR);
		}

		String result = format.format(f);

		return result;
	}

	public static float GetDifferenceOfDate ( String today, String targetDay) {
		long d1,d2;

		// 객체 생성
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		String[] todayArr = today.split("-");
		int year1 = Integer.parseInt(todayArr[0]);
		int mon1 = Integer.parseInt(todayArr[1]);
		int day1 = Integer.parseInt(todayArr[2]);

		String[] targetDayArr = targetDay.split("-");
		int year2 = Integer.parseInt(targetDayArr[0]);
		int mon2 = Integer.parseInt(targetDayArr[1]);
		int day2 = Integer.parseInt(targetDayArr[2]);

		// 날짜 지정
		c1.set(year1,mon1,day1);
		c2.set(year2,mon2,day2);

		// MilliSecond 로 변환
		d1 = c1.getTime().getTime();
		d2 = c2.getTime().getTime();

		// 계산
		int days = (int)((d1 - d2)/(1000*60*60*24));

		return days;
	}

	//static HashMap<String, Object>
	public static HashMap<String, Object> convertHistoryData(
			JSONObject jsonObj,
			Map<String, String> earnCode,
			Map<String, String> conCode,
			Map<String, String> tradeTypeCode) {


		HashMap<String, String> etc = new HashMap<String, String>();
		HashMap<String, Object> iterData = new HashMap<String, Object>();
		String tradeType = "";

		etc.put("1", "'Answerer'");
		etc.put("2", "'Answerer_Admin'");
		etc.put("3", "'Etc'");

		iterData.put("flag", 0);

		//String tradeType = "" + jsonObj.
		Map<String, String> source = jsonObj;

		//System.out.println("source: " + source);
		iterData.put("regdate", source.get("regdate"));
		iterData.put("regdate2", source.get("regdate2"));
		iterData.put("Almoney", source.get("Almoney"));
		iterData.put("Balance", source.get("Balance"));

		//System.out.println("jsonObj : " + (Map<String, String>)jsonObj);
		//tradeType = "'" + source.get("TradeType") + "'";
		tradeType = source.get("TradeType");
		//System.out.println("tradeType : " + tradeType);
		if(useKeyCheckValue(tradeType, earnCode) || useKeyCheckValue(tradeType, etc)) {
			iterData.put("flag", 1);
		}
		else if(useKeyCheckValue(tradeType, conCode)) {
			iterData.put("flag", 2);
		}
		else {
			iterData.put("flag", 3);
		}
		//System.out.println("jsonObj : " + iterData.get("flag"));
		BigDecimal zero = BigDecimal.ZERO;
		BigDecimal al = new BigDecimal( String.valueOf(source.get("Almoney")) );
		int compareResult = zero.compareTo(al);
		//System.out.println("Almoney : " + al );
		// 음/양수는 타입에 따라 결정되어지고 있으나 Almoney의 상태에 따라서 그대로 음/양수가 표현되어야 하는 경우에 대한 정리
		if(useKeyCheckValue(tradeType, etc) && compareResult < 0) {
			iterData.put("flag", 2);
		}

		if(source.get("ContentsSeq") != "" && "/ViewRQ/ViewRA/Refund/Event/Etc/etc/Join/Withdraw/Answerer/Exchange/ExchangeAlpay/31/Roulette/".indexOf( "/" + source.get("TradeType") + "/" ) < 0 ) {
			String link = "";
			String contentsSeq = String.valueOf(source.get("ContentsSeq"));
			if("/4/5/".indexOf(  "/" + source.get("TradeType") + "/" ) != -1) {
				link = "/answer/answerList?ASeq="+contentsSeq+"&devSeq="+contentsSeq;
			}
			else if("/ViewA/Esti/View/Answer/".indexOf(  "/" + source.get("TradeType") + "/" ) != -1) {
				link = "/answer/answerList?ASeq="+contentsSeq;
			}
			else {
				link = "/answer/answerList?Seq="+contentsSeq;
			}

			iterData.put("link", link);
		}

		iterData.put("TradeType", tradeTypeCode.get( source.get("TradeType").toLowerCase() ));
		//System.out.println("iterData : " + iterData);
		return iterData;
	}

	public static String get_fn_answer_rankAnswer(String flag2, int st_num, int en_num) {
		String str_sql = "SELECT * ";
		str_sql += " FROM ( ";
		str_sql += " 		SELECT	 ";
		str_sql += " 			ROW_NUMBER() OVER(ORDER BY "+ flag2 +" DESC) AS Idx, *,  ";
		str_sql += " 			RANK() OVER(ORDER BY "+ flag2 +" DESC) AS rankA,  ";
		str_sql += " 			RANK() OVER(ORDER BY CountQ DESC) AS rankQ  ";
		str_sql += " 		FROM V_MEMBERS  ";
		str_sql += " 		WHERE Lv != 99  ";
		str_sql += " 	) T  ";
		str_sql += " WHERE  ";
		str_sql += " 	T.Idx BETWEEN "+ st_num +" AND "+ en_num +" ";

		return str_sql;
	}

	public static String get_fn_answer_RankA2Sql(int st_num, int en_num) {

		String str_sql = "SELECT * ";
		str_sql += " FROM  ";
		str_sql += " 	(SELECT ";
		str_sql += " 		ROW_NUMBER() OVER(ORDER BY ReadAlmoney desc) AS rownum, ";
		str_sql += " 		Seq,  ";
		str_sql += " 		QuestionSeq,  ";
		str_sql += " 		Answer,  ";
		str_sql += " 		ReadCount,  ";
		str_sql += " 		ReadAlmoney,  ";
		str_sql += " 		PointCount, ";
		str_sql += " 		Section1  ";
		str_sql += "  	FROM V2_RANKA  with(nolock) ";
		str_sql += " 	) A  ";
		str_sql += " WHERE  ";
		str_sql += " 	rownum BETWEEN "+st_num+" AND "+en_num+" ";

		return str_sql;
	}

	public static boolean checkShowAlarm(String colVal, String alamrCount) {
		boolean returnValue = false;

		if(colVal.equals("Y") && Integer.parseInt(alamrCount) > 0) {
			returnValue = true;
		}
		//returnValue = true;
		return returnValue;
	}

	public static String convertJsonToString(Object obj, Object arr) {

		JSONObject jsonTotal = new JSONObject();

		jsonTotal.put("result", obj);
    	jsonTotal.put("arr", arr);

    	String jsonString = JSONValue.toJSONString(jsonTotal);

		return jsonString;
	}


	public static String getProfilePhoto(int userSeq) {
		HashMap<String, Object> profile = new HashMap<String, Object>();

		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		SimpleDriverDataSource ds = (SimpleDriverDataSource) context.getBean("dataSource");

		DataSource source = ds;
		JdbcTemplate jt = new JdbcTemplate(source);
		SqlRowSet  rs = null;

		rs = jt.queryForRowSet("SELECT seq, photo FROM T_MEMBERS WHERE seq = '" + userSeq + "' ");

		String photo = null;
		if(rs.next()) {

			photo = rs.getString("photo");
		}

		return photo;
	}
	public static String getUserLV(int userSeq) {
		HashMap<String, Object> profile = new HashMap<String, Object>();

		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		SimpleDriverDataSource ds = (SimpleDriverDataSource) context.getBean("dataSource");

		DataSource source = ds;
		JdbcTemplate jt = new JdbcTemplate(source);
		SqlRowSet  rs = null;

		rs = jt.queryForRowSet("SELECT seq, Lv FROM T_MEMBERS WHERE seq = '" + userSeq + "' ");

		String photo = null;
		if(rs.next()) {

			photo = rs.getString("Lv");
		}

		return photo;
	}

	public static LogAlmoneyVO getAlmoneyOne() {
		LogAlmoneyVO almoney = new LogAlmoneyVO();

		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		SimpleDriverDataSource ds = (SimpleDriverDataSource) context.getBean("dataSource");

		DataSource source = ds;
		JdbcTemplate jt = new JdbcTemplate(source);
		SqlRowSet  rs = null;

		rs = jt.queryForRowSet("SELECT SUM_Q_Almoney, SUM_Q_ViewAlmoney FROM T_STATUS ");

		if(rs.next()) {

			almoney.setSum_Q_Almoney(rs.getBigDecimal("SUM_Q_Almoney"));
			almoney.setSum_Q_ViewAlmoney(rs.getBigDecimal("SUM_Q_ViewAlmoney"));
		}

		return almoney;
	}

	public static List<HashMap<String, Object>> geAnswer_Section1() {

		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		SimpleDriverDataSource ds = (SimpleDriverDataSource) context.getBean("dataSource");

		DataSource source = ds;
		JdbcTemplate jt = new JdbcTemplate(source);
		SqlRowSet  rs = null;

		rs = jt.queryForRowSet("SELECT Code AS SectionCode1, CodeName AS SectionName1 FROM T_SECTION_T	WHERE FlagUse = 'Y'	");

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		while(rs.next()) {
			HashMap<String, Object> item = new HashMap<String, Object>();

			item.put("SectionCode1", rs.getInt("SectionCode1"));
			item.put("SectionName1", rs.getString("SectionName1"));

			list.add(item);
		}

		return list;
	}

	public static List<HashMap<String, Object>> getMyFavoriteCategoryGetSp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");

		JSONObject global = null;

		List<HashMap<String, Object>> out = new ArrayList<HashMap<String, Object>>();
		//System.out.println("cookies1");
		if(cookies1 != null) {
			global = CommonUtil.getGlobal(request, response);

			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			SimpleDriverDataSource ds = (SimpleDriverDataSource) context.getBean("dataSource");

			DataSource source = ds;
			JdbcTemplate jt = new JdbcTemplate(source);
			SqlRowSet  rs = null;

			int userSeq = Integer.parseInt( String.valueOf( global.get("UserSeq") ) );

			rs = jt.queryForRowSet("{call SP2_MYFAVORITE_CATEGORY_GET("+userSeq+")}");

			while(rs.next()) {
				HashMap<String, Object> item = new HashMap<String, Object>();

				item.put("SEQNO", rs.getInt("SEQNO"));
				item.put("MemberSeq", rs.getInt("MemberSeq"));
				item.put("SectionCode1", rs.getString("SectionCode1"));
				item.put("SectionCode2", rs.getString("SectionCode2"));
				item.put("SectionCode3", rs.getString("SectionCode3"));
				item.put("SectionCode4", rs.getString("SectionCode4"));
				item.put("SectionCode5", rs.getString("SectionCode5"));
				item.put("SectionName1", rs.getString("SectionName1"));
				item.put("SectionName2", rs.getString("SectionName2"));
				item.put("SectionName3", rs.getString("SectionName3"));
				item.put("SectionName4", rs.getString("SectionName4"));
				item.put("SectionName5", rs.getString("SectionName5"));

				out.add(item);
			}
		}
		return out;
	}

	public static List<EventVO> getEventForHeader(HttpServletRequest request, HttpServletResponse response) throws Exception {
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		SimpleDriverDataSource ds = (SimpleDriverDataSource) context.getBean("dataSource");

		DataSource source = ds;
		JdbcTemplate jt = new JdbcTemplate(source);
		SqlRowSet  rs = null;

		rs = jt.queryForRowSet("SELECT * FROM V_EVENT WHERE [Status] = 1");

		List<EventVO> list = new ArrayList<EventVO>();

		while(rs.next()) {
			EventVO event = new EventVO();
			event.setStatus(rs.getInt("Status"));
			event.setQ_seq( BigInteger.valueOf( rs.getLong("Q_Seq") ) );
			event.setEv_seq(rs.getInt("EV_Seq"));
			event.setStartDate(rs.getString("StartDate"));
			event.setEndDate(rs.getString("EndDate"));
			event.setBannerImg(rs.getString("BannerImg"));
			event.setFlagUse(rs.getInt("FlagUse"));
			event.setFlagType(rs.getInt("FlagType"));
			event.setSection1(rs.getInt("Section1"));
			event.setSection2(rs.getInt("Section2"));
			event.setSection3(rs.getInt("Section3"));
			event.setTitle(rs.getString("Title"));
			event.setContents(rs.getString("Contents"));
			event.setAlmoney(rs.getBigDecimal("Almoney"));
			event.setReadCount(rs.getInt("ReadCount"));
			event.setDateReg(rs.getString("DateReg"));
			event.setAnswerCount(rs.getInt("AnswerCount"));

			list.add(event);
		}

		return list;
	}

	public static List<Map<String, Object>> getMyInfoLv(int userSeq) throws SQLException {
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		SimpleDriverDataSource ds = (SimpleDriverDataSource) context.getBean("dataSource");

		DataSource source = ds;
		JdbcTemplate jt = new JdbcTemplate(source);
		SqlRowSet  rs = null;

		ResultSet res = null;

		String str_sql = "exec SP2_MEMO_LV_UP_READY_SET 0, " + userSeq + ", 1 ";


		List<Map<String, Object>> list = jt.queryForList(str_sql);
		//System.out.print(list.size() + " = " + list.size());
		for(int i = 0; i < list.size(); i++) {
			for(Map.Entry<String, Object> entry: list.get(i).entrySet()) {

	    		//System.out.print(entry.getKey() + " = " + entry.getValue());

	    		//hObjFilter.put(entry.getKey(), entry.getValue());
	    	}
		}
		/*
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("param_lv", 0);
		param.put("userSeq", userSeq);
		param.put("isreturnCFG", 1);
		List<Map<String, Object>> list2 = commonService.getMemoLvUpReadySetSP(param);

		System.out.print("list2.size() : " + list2.size());*/

		return list;
	}

	public static int getAnswerFilecount(int seq) {
		int cnt = 0;
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		SimpleDriverDataSource ds = (SimpleDriverDataSource) context.getBean("dataSource");

		DataSource source = ds;
		JdbcTemplate jt = new JdbcTemplate(source);
		SqlRowSet  rs = null;

		rs = jt.queryForRowSet("select count(Seq) as [count] from T_FILES with(nolock) where ParentSeq = "+seq+" and ParentName = 'Answer' and FlagUse = 'Y' ");

		if(rs.next()) {

			cnt = rs.getInt("count");
		}

		return cnt;
	}

	public static boolean useArraysBinarySearch(String[] arr, String targetValue) {

	  int a =  Arrays.binarySearch(arr, targetValue);

	  if(a > -1)

	    return true;

	  else

	    return false;

	}

	public static JSONObject getGlobal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CookieBox cookieBox = new CookieBox(request);

		Cookie cookies1 = cookieBox.getCookie("SESS");

		Map<String, Object> globalVal = null;

		if(cookies1 != null || request.getAttribute("SESS") != null) {

			globalVal = libLogin((cookieBox.getCookie("SESS") == null && request.getAttribute("SESS") != null) ? (Cookie) request.getAttribute("SESS") : cookieBox.getCookie("SESS")
							,request, response);

		}

		JSONObject global = null;
		if(globalVal != null) {
			String globalStr = globalVal.get("SESS").toString();
			JSONParser jsonParse = new JSONParser();

			if(globalStr != null && !globalStr.equals("")) {
				global = (JSONObject) jsonParse.parse(globalStr);
			}
		}

		return global;
	}

	public static String getLevelName(int lv, HttpServletRequest request) {
		Locale locale = request.getLocale();
		String lang = locale.getLanguage();

		CookieBox cookieBox = new CookieBox(request);
		Cookie s_lang = cookieBox.getCookie("s_lang");

		if(s_lang != null) {
			lang = s_lang.getValue();
		}

		if(lang.contains("_")) {
			String[] langArr = lang.split("_");
			lang = langArr[0];
		}

		MessageSource messageSource = messageSource(lang);

		Locale new_locale = new Locale(lang);

		String msg1 = messageSource.getMessage("msg_0136", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0137", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0138", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_0139", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_0140", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg6 = messageSource.getMessage("msg_0141", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg7 = messageSource.getMessage("msg_0142", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg8 = messageSource.getMessage("msg_0143", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg9 = messageSource.getMessage("msg_0144", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg10 = messageSource.getMessage("msg_0145", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg11 = messageSource.getMessage("msg_0146", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg12 = messageSource.getMessage("msg_0147", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg13 = messageSource.getMessage("msg_0148", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg14 = messageSource.getMessage("msg_0149", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		String[] Level = new String[100];
		Level[0] = msg1;			// Level[0] = msg1; // 처럼 작성하거나 Level[0] = messageSource.getMessage("hello", null, locale);// 처럼 변수 선언없이 값을 바로 대입할 수도 있습니다.
		Level[1] = msg2;
		Level[2] = msg3;
		Level[3] = msg4;
		Level[4] = msg5;
		Level[5] = msg6;
		Level[6] = msg7;
		Level[7] = msg8;
		Level[8] = msg9;
		Level[9] = msg10;
		Level[10] = msg11;
		Level[11] = msg12;
		Level[98] = msg13;
		Level[99] = msg14;

		return Level[lv].toString();
	}

	public static String getLvName(int lv, String s_lang) {
		MessageSource messageSource = messageSource(s_lang);

		Locale new_locale = new Locale(s_lang);

		String msg1 = messageSource.getMessage("msg_0136", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0137", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0138", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_0139", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_0140", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg6 = messageSource.getMessage("msg_0141", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg7 = messageSource.getMessage("msg_0142", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg8 = messageSource.getMessage("msg_0143", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg9 = messageSource.getMessage("msg_0144", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg10 = messageSource.getMessage("msg_0145", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg11 = messageSource.getMessage("msg_0146", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg12 = messageSource.getMessage("msg_0147", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg13 = messageSource.getMessage("msg_0148", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg14 = messageSource.getMessage("msg_0149", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		String[] Level = new String[100];
		Level[0] = msg1;			// Level[0] = msg1; // 처럼 작성하거나 Level[0] = messageSource.getMessage("hello", null, locale);// 처럼 변수 선언없이 값을 바로 대입할 수도 있습니다.
		Level[1] = msg2;
		Level[2] = msg3;
		Level[3] = msg4;
		Level[4] = msg5;
		Level[5] = msg6;
		Level[6] = msg7;
		Level[7] = msg8;
		Level[8] = msg9;
		Level[9] = msg10;
		Level[10] = msg11;
		Level[11] = msg12;
		Level[98] = msg13;
		Level[99] = msg14;

		return Level[lv].toString();
	}

	public static HashMap<String, Object> getFilterDecimalZero(HashMap<String, Object> data, HttpServletRequest request) {
		HashMap<String, Object> hObjFilter = new HashMap<String, Object>();

		Locale locale = request.getLocale();
		String lang = locale.getLanguage();
		MessageSource messageSource = messageSource(lang);
		Locale new_locale = new Locale(lang);


		String msg1 = messageSource.getMessage("msg_0722", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0729", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0723", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_0727", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_0726", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg6 = messageSource.getMessage("msg_0725", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg7 = messageSource.getMessage("msg_0183", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg8 = messageSource.getMessage("msg_0731", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg9 = messageSource.getMessage("msg_0728", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg10 = messageSource.getMessage("msg_0724", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg11 = messageSource.getMessage("msg_0732", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg12 = messageSource.getMessage("msg_1007", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg13 = messageSource.getMessage("msg_0723", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg14 = messageSource.getMessage("msg_0722", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg15 = messageSource.getMessage("msg_0728", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg16 = messageSource.getMessage("msg_0729", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg17 = messageSource.getMessage("msg_0660", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg18 = messageSource.getMessage("msg_0245", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg19 = messageSource.getMessage("msg_0733", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용


		HashMap<String, Object> ratioNameMapper = new HashMap<String, Object>();
		ratioNameMapper.put("EarnAnsChoice", msg1);			// ratioNameMapper.put("EarnAnsChoice", msg1); // 아래 코드들도 비스한 형식으로 작성
		ratioNameMapper.put("EarnAnsExt", msg2);
		ratioNameMapper.put("EarnAnsView", msg3);
		ratioNameMapper.put("EarnChildAns", msg4);
		ratioNameMapper.put("EarnChildQue", msg5);
		ratioNameMapper.put("EarnEsti", msg6);
		ratioNameMapper.put("EarnEvent", msg7);
		ratioNameMapper.put("EarnJoin", msg8);
		ratioNameMapper.put("EarnQueExt", msg9);
		ratioNameMapper.put("EarnQueView", msg10);
		ratioNameMapper.put("EarnRefund", msg11);
		ratioNameMapper.put("EarnAnswerer", msg12);
		ratioNameMapper.put("ConAnsView", msg13);
		ratioNameMapper.put("ConQueWrite", msg14);
		ratioNameMapper.put("ConQueExt", msg15);
		ratioNameMapper.put("ConAnsExt", msg16);
		ratioNameMapper.put("ConExchange", msg17);
		ratioNameMapper.put("ConQueMove", msg18);
		ratioNameMapper.put("Etc", msg19);

    	for(Map.Entry<String, Object> entry: data.entrySet()) {
    		//BigDecimal tmp = new BigDecimal( String.valueOf(entry.getValue()) );
    		if( entry.getValue().equals(".0000") || entry.getValue().equals("0.0000")) { continue; }
    		//System.out.print(entry.getKey() + " = " + entry.getValue());
    		String key = entry.getKey();
    		if(ratioNameMapper.get(key) == null) { key = msg19; } // key = msg19; // 형식으로 작성
    		hObjFilter.put(entry.getKey(), entry.getValue());
    	}

		return hObjFilter;
	}

	public static BigDecimal addBigDecimal(BigDecimal sum, BigDecimal p) {
		return sum.add(p);
	}

	public static String getTimestamp() throws ParseException {
		//php echo microtime(); //0.07563600 1199169216   마이크로 seconds와 Unix timestamp (sec 단위)가 나오는 것


		long mstime = System.currentTimeMillis();
		long seconds = mstime / 1000;
		BigDecimal decimal = new BigDecimal( (mstime - (seconds * 1000)) / 1000d ).setScale(8, RoundingMode.HALF_EVEN);

	    //System.out.println(decimal + " " + seconds);


		String result = String.valueOf(seconds) +""+ String.valueOf(decimal).substring(2, 5);


		//System.out.println("result : " + result);


		return result;
	}

	public static String fnGenPlainData(HashMap<String, Object> param) {
		String aRequestNO = String.valueOf( param.get("sRequestNO") );
		String aSiteCode = String.valueOf( param.get("sSiteCode") );
		String aAuthType = String.valueOf( param.get("sAuthType") );
		String aReturnUrl = String.valueOf( param.get("sReturnUrl") );
		String aErrorUrl = String.valueOf( param.get("sErrorUrl") );
		String popgubun = String.valueOf( param.get("popgubun") );
		String sGender = String.valueOf( param.get("sGender") );

		String retPlainData = "7:REQ_SEQ" + fnGetDataLength(aRequestNO) + ":" + aRequestNO + "\\n";
		retPlainData += "8:SITECODE" + fnGetDataLength(aSiteCode) + ":" + aSiteCode + "\\n";
		retPlainData += "9:AUTH_TYPE" + fnGetDataLength(aAuthType) + ":" + aAuthType + "\\n";
		retPlainData += "7:RTN_URL" + fnGetDataLength(aReturnUrl) + ":" + aReturnUrl + "\\n";
		retPlainData += "7:ERR_URL" + fnGetDataLength(aErrorUrl) + ":" + aErrorUrl + "\\n";
		retPlainData += "11:POPUP_GUBUN" + fnGetDataLength(popgubun) + ":" + popgubun + "\\n";
		retPlainData += "6:GENDER" + fnGetDataLength(sGender) + ":" + sGender;

		return retPlainData;
	}

	public static int fnGetDataLength(String aData) {
		int iData_len = 0;

		if(aData.length() > 0) {
			for(int i = 0; i < aData.length(); i++) {
				int index = aData.charAt(i);

				if(index >= 48 && index <= 57) {
					iData_len = iData_len + 1;
				}
				else if(index >= 65 && index <= 122) {
					iData_len = iData_len + 1;
				}
				else {
					iData_len = iData_len + 2;
				}
			}
		}

		return iData_len;
	}

	public static String requestReplace(String paramValue, String gubun) {

        String result = "";

        if (paramValue != null) {

        	paramValue = paramValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

        	paramValue = paramValue.replaceAll("\\*", "");
        	paramValue = paramValue.replaceAll("\\?", "");
        	paramValue = paramValue.replaceAll("\\[", "");
        	paramValue = paramValue.replaceAll("\\{", "");
        	paramValue = paramValue.replaceAll("\\(", "");
        	paramValue = paramValue.replaceAll("\\)", "");
        	paramValue = paramValue.replaceAll("\\^", "");
        	paramValue = paramValue.replaceAll("\\$", "");
        	paramValue = paramValue.replaceAll("'", "");
        	paramValue = paramValue.replaceAll("@", "");
        	paramValue = paramValue.replaceAll("%", "");
        	paramValue = paramValue.replaceAll(";", "");
        	paramValue = paramValue.replaceAll(":", "");
        	paramValue = paramValue.replaceAll("-", "");
        	paramValue = paramValue.replaceAll("#", "");
        	paramValue = paramValue.replaceAll("--", "");
        	paramValue = paramValue.replaceAll("-", "");
        	paramValue = paramValue.replaceAll(",", "");

        	if(gubun != "encodeData"){
        		paramValue = paramValue.replaceAll("\\+", "");
        		paramValue = paramValue.replaceAll("/", "");
        		paramValue = paramValue.replaceAll("=", "");
        	}

        	result = paramValue;

        }
        return result;
	}

	public static boolean libhasAuthority(int pageName, int userSeq) {

		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		SimpleDriverDataSource ds = (SimpleDriverDataSource) context.getBean("dataSource");

		DataSource source = ds;
		JdbcTemplate jt = new JdbcTemplate(source);
		SqlRowSet  rs = null;

		rs = jt.queryForRowSet("SELECT	Authority FROM	T_MEMBERS_ADMIN  WHERE	MemberSeq = " + userSeq + " ");

		String authority = "";
		if(rs.next()) {

			authority = rs.getString("Authority");
		}

		if(authority.equals("*")) {
			return true;
		}
		else {
			if(authority.equals("")) {
				return false;
			}
			else {
				return (pageName & Integer.parseInt(authority) ) == 1 ? true : false;
			}

		}
	}
	public static void libIsAdmin(HttpServletResponse response, HttpServletRequest request, int pageName, int userSeq) throws Exception {
		/*
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
		*/
		int authority_cnt = 23; //권한 갯수

		Locale locale = request.getLocale();
		String lang = locale.getLanguage();
		MessageSource messageSource = messageSource(lang);
		Locale new_locale = new Locale(lang);

		if(libhasAuthority(pageName, userSeq) == false) {
			//echo "<!--LOGOFF-->"; //ajax 호출시 로그오프 되었음을 판단할 수 있도록
			String msg1 = messageSource.getMessage("msg_1008", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

			// jspAlert(response, msg1, "/", "top"); // 처럼 작성.
			jspAlert(response, msg1, "/", "top");
		}
	}

	public static boolean libIsAdminRet(HttpServletResponse response, int pageName, int userSeq) throws Exception {
		boolean result = true;
		if(libhasAuthority(pageName, userSeq) == false) {
			//echo "<!--LOGOFF-->"; //ajax 호출시 로그오프 되었음을 판단할 수 있도록
			//jspAlert(response, "접근 권한이 없습니다.", "/", "top");
			result = false;
		}

		return result;
	}

	public static String fn_memberType(String strData, HttpServletRequest request) {
		Locale locale = request.getLocale();
		String lang = locale.getLanguage();
		MessageSource messageSource = messageSource(lang);
		Locale new_locale = new Locale(lang);

		String msg1 = messageSource.getMessage("hello", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("hello", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("hello", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("hello", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("hello", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		String memberTypeText = "";

		if(!strData.equals("")) {
			switch(strData) {
				case "0":
					memberTypeText = "일반회원";			// memberTypeText = msg1; // 형식으로 아래 코드들도 작성
					break;
				case "1":
					memberTypeText = "RS팀";
					break;
				case "2":
					memberTypeText = "천사단";
					break;
				case "99":
					memberTypeText = "고스트(GHOST)";
					break;
				default:
					memberTypeText = "준비중";
			}
		}

		return memberTypeText;
	}

	public static String sendRESTSms(String rcv_number, String msg, HttpServletRequest request) throws Exception {
		String jsonObject = "";
		String snd_number = "023303000"; //발신자번호

		Locale locale = request.getLocale();
		String lang = locale.getLanguage();
		MessageSource messageSource = messageSource(lang);
		Locale new_locale = new Locale(lang);

		URL url = new URL("https://rest.surem.com/intl/text");

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setDoOutput(true);

		conn.setRequestMethod("POST"); // 보내는 타입

		conn.setRequestProperty("Content-Type", "application/json");

		conn.setRequestProperty("Accept", "application/json");

		// 데이터

		String param = "{";
		param += "\"usercode\": \"altong\", \"deptcode\" : \"JZ-SWM-MR\", ";
		param += "\"from\":\"" + snd_number + "\", \"text\":\"" + msg + "\", ";
		param += "\"messages\": ";
		param += "[{";
		param += "\"message_id\":\"1\",";
		param += "\"to\":\"" + rcv_number + "\"";
		param += "}]";
		param += "}";


		JSONParser parser = new JSONParser();
		Object obj = parser.parse( param );
		JSONObject jsonObj = (JSONObject) obj;

		String json = new Gson().toJson(jsonObj);
		//System.out.println("json : " + json);

		// 전송

		OutputStreamWriter osw = new OutputStreamWriter(

		conn.getOutputStream());

		try {
			osw.write(json);
			osw.flush();

			// 응답

			BufferedReader br = null;

			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

			StringBuilder sb = new StringBuilder();

			String line = null;

			while ((line = br.readLine()) != null) {
				//System.out.println("line : " + line); // json 변환 필요함
				line = line.replace("[", "").replace("]", "");
				sb.append(line);
			}

			//String json = new Gson().toJson(jsonObject);
			jsonObject = sb.toString();
			//System.out.println("jsonObject : " + jsonObject);
			// 닫기

			osw.close();

			br.close();

		} catch (MalformedURLException e) {
			//e.printStackTrace();
			jsonObject = "{\"success\" : \"false\", \"message\" : \"MalformedURLException\"}";

		} catch (ProtocolException e) {
			//e.printStackTrace();
			jsonObject = "{\"success\" : \"false\", \"message\" : \"ProtocolException\"}";

		} catch (UnsupportedEncodingException e) {
			//e.printStackTrace();
			jsonObject = "{\"success\" : \"false\", \"message\" : \"UnsupportedEncodingException\"}";

		} catch (IOException e) {
			//e.printStackTrace();
			String msg1 = messageSource.getMessage("msg_1009", null, new_locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

			//jsonObject = "{\"success\" : \"false\", \"message\" : \"" + msg1 + "\"}"; // 처럼 작성
			jsonObject = "{\"success\" : \"false\", \"message\" : \"" + msg1 + "\"}";
		}

		return jsonObject;
	}


	public static void setLog(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    String user_login_ip =  CommonUtil.getUserIP(request);

	    CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		JSONObject global = null;

		int userSeq = 0;
		if(cookies1 != null) {
			global = CommonUtil.getGlobal(request, response);
			userSeq = Integer.parseInt( String.valueOf(global.get("UserSeq") ) );
		}

	    WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		SimpleDriverDataSource ds = (SimpleDriverDataSource) context.getBean("dataSource");

		DataSource source = ds;
		JdbcTemplate jt = new JdbcTemplate(source);
		SqlRowSet  rs = null;

		rs = jt.queryForRowSet("SELECT Country, Phone, Pw FROM	T_MEMBERS  WHERE Seq = " + userSeq + " ");

		String userCountry = "";
		String userPhone = "";
		String userPasswd = "";
		if(rs.next()) {

			userCountry = rs.getString("Country");
			userPhone = rs.getString("Phone");
			userPasswd = rs.getString("Pw");
		}


	    String format = "yyyy-MM-dd aa hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String now = type.format(today.getTime());


		String driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";//ds.getDriver().getClass().toString();
		String url = ds.getUrl();
		String username = ds.getUsername();
		String password = ds.getPassword();

	    Class.forName(driverClass);
		Connection conn;
		conn = DriverManager.getConnection(url,username,password);

		CallableStatement cs = null;

		cs = conn.prepareCall("{call SP_MEMBER_LOGIN_CH6(?,?,?,?,?)}");
		cs.setInt(1, Integer.parseInt(userCountry) );
		cs.setNString(2, userPhone);
		cs.setNString(3, userPasswd);
		cs.setNString(4, user_login_ip);
		cs.setNString(5, now);
		cs.executeQuery();
	}

	public static HashMap<String, Object> getLocale(Locale locale) {
		String code = locale.getCountry();
		//System.out.println("code : " + code);
		Locale locales = new Locale(locale.getLanguage(), code);

		String nation = locales.getISO3Country(); // 3자리 코드
		String language = locale.getLanguage();

		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		int areaCode = phoneUtil.getCountryCodeForRegion(code);



		HashMap<String, Object> item = new HashMap<String, Object>();

		item.put("nation", nation);
		item.put("nation2byte", code);
		item.put("lang", language);
		item.put("areaCode", areaCode);

		return item;
	}

	public static boolean checkRoulettePublish(String regDate) throws ParseException {
		boolean chk = false;

		SimpleDateFormat sType = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		Date sParseDt = sType.parse(regDate);

		//System.out.println("sParseDt : " + sParseDt.getTime());
		//System.setProperty("user.timezone", "Asia/Seoul");

		// 168시간전
		//TimeZone jst = TimeZone.getTimeZone("Asia/Seoul");
		//Date today = new Date();

		SimpleDateFormat dType2 = new SimpleDateFormat("yyyy-MM-dd aa hh:mm:ss", Locale.KOREA);
		Calendar today2 = Calendar.getInstance();
		today2.add(Calendar.DATE, -7);

		String bHour = dType2.format(today2.getTime());
		Date bHourFormat = dType2.parse(bHour);
		//System.out.println("bHour : " + bHour);
		//System.out.println("168시간 전 : " + bHourFormat.getTime());

		if(sParseDt.getTime() >= bHourFormat.getTime()) {
			chk = true;
		}
		//System.out.println("chk : " + chk);
		return chk;
	}

	public static boolean checkMachineTranslate(String regDate) throws ParseException {
		//기계 번역 글이 6개월 경과 되었는지 체크
		boolean chk = false;

		SimpleDateFormat sType = new SimpleDateFormat("yyyy-MM-dd aa hh:mm:ss", Locale.KOREA);

		Date sParseDt = sType.parse(regDate);

		//System.out.println("sParseDt : " + sParseDt.getTime());
		//System.setProperty("user.timezone", "Asia/Seoul");

		// 168시간전
		//TimeZone jst = TimeZone.getTimeZone("Asia/Seoul");
		//Date today = new Date();

		SimpleDateFormat dType2 = new SimpleDateFormat("yyyy-MM-dd aa hh:mm:ss", Locale.KOREA);
		Calendar today2 = Calendar.getInstance();

		String bHour = dType2.format(today2.getTime());
		Date bHourFormat = dType2.parse(bHour);
		//System.out.println("bHour : " + bHour);
		//System.out.println("168시간 전 : " + bHourFormat.getTime());
		long diff = sParseDt.getTime() - bHourFormat.getTime();
		int result = (int)( (diff / 1000) / 60 / 60 / 24 / 30 );

		//System.out.println("result : " + result);
		if(result >= 6) {
			chk = true;
		}
		//System.out.println("chk : " + chk);

		return chk;
	}

	public static String detectLanguage(String text) throws IOException {
		String lang = null;
		// Initialize client that will be used to send requests. This client only needs to be created
	    // once, and can be reused for multiple requests. After completing all of your requests, call
	    // the "close" method on the client to safely clean up any remaining background resources.
	    try (TranslationServiceClient client = TranslationServiceClient.create()) {
	      // Supported Locations: `global`, [glossary location], or [model location]
	      // Glossaries must be hosted in `us-central1`
	      // Custom Models must use the same location as your model. (us-central1)
	      LocationName parent = LocationName.of("altong-translate", "global");

	      // Supported Mime Types: https://cloud.google.com/translate/docs/supported-formats
	      DetectLanguageRequest request =
	          DetectLanguageRequest.newBuilder()
	              .setParent(parent.toString())
	              .setMimeType("text/plain")
	              .setContent(text)
	              .build();

	      DetectLanguageResponse response = client.detectLanguage(request);

	      // Display list of detected languages sorted by detection confidence.
	      // The most probable language is first.
	      for (DetectedLanguage language : response.getLanguagesList()) {
	    	  // The language detected
	    	  lang = language.getLanguageCode();
	    	  //System.out.printf("Language code: %s\n", language.getLanguageCode());
	    	  // Confidence of detection result for this language
	    	  //System.out.printf("Confidence: %s\n", language.getConfidence());
	      }
	    }

		return lang;
	}

	public static String strToTranslate(String detectLang, String targetLang, String str, String event) throws Exception {
		StringBuilder sb = new StringBuilder();
		if(!detectLang.equals(targetLang)) {

			String[] splitStr = str.split("</p>");
			if(event.equals("Y")) {
				splitStr = str.split("<br>");
			}
			//System.out.println("length : " + splitStr.length);
			if(splitStr.length > 1) {
				for(int i = 0; i < splitStr.length; i++) {
					//System.out.println(i + " : " + removeTag(splitStr[i]));
					if(!event.equals("Y")) {
						String convTranslate = "";
						if(!splitStr[i].equals("")) {
							if(!splitStr[i].contains("<img")) {
								convTranslate = convertToTranslate(detectLang, targetLang, removeTag(splitStr[i]));
							}
							else {
								convTranslate = splitStr[i];
							}
						}

						sb.append("<p>" + convTranslate + "</p>");
					}
					else if(!event.equals("O")) {
						String convTranslate = "";
						if(!splitStr[i].equals("")) {
							if(!splitStr[i].contains("<img")) {
								convTranslate = convertToTranslate(detectLang, targetLang, removeTag(splitStr[i]));
							}
							else {
								convTranslate = splitStr[i];
							}
						}

						sb.append(convTranslate + "<br>");
					}
					else {
						String convTranslate = "";
						if(i == 0) {
							convTranslate = splitStr[i];
						}
						else {
							if(!splitStr[i].equals("")) {
								convTranslate = convertToTranslate(detectLang, targetLang, removeTag(splitStr[i]));
							}
						}

						sb.append(convTranslate + "<br>");
					}
				}
			}
			else {
				String convTranslate = convertToTranslate(detectLang, targetLang, removeTag(str));
				sb.append(convTranslate);
			}
		}

		return sb.toString();
	}

	public static String convertToTranslate(String detectLang, String targetLang, String str) {
		Translate translate = TranslateOptions.getDefaultInstance().getService();
		Translation translation = translate.translate(str,
														TranslateOption.sourceLanguage(detectLang),
														TranslateOption.targetLanguage(targetLang)
													);

		return translation.getTranslatedText();
	}


	public static String removeTag(String str) throws Exception {
		//return str.replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", "");
		return str.replaceAll("<[^>]*>", " ");
	}

	public static String getNationString(String str) throws Exception {
		String nationString = "";
		switch(str) {
			case "AFG": nationString = "افغانستان"; break;
			case "ALB": nationString = "Shqipëria";	break;
			case "DZA": nationString = "الجزائر"; break;
			case "AND": nationString = "Andorra"; break;
			case "AGO": nationString = "Angola"; break;
			case "ATG": nationString = "Antiguans"; break;
			case "ARG": nationString = "Argentina"; break;
			case "ARM": nationString = "Հայաստան"; break;
			case "AUS": nationString = "Australia";	break;
			case "AUT": nationString = "Österreich"; break;
			case "AZE": nationString = "Azərbaycan"; break;
			case "BHS": nationString = "The Bahamas"; break;
			case "BHR": nationString = "البحرين"; break;
			case "BGD": nationString = "বাংলাদেশ"; break;
			case "BRB": nationString = "Barbados"; break;
			case "BLR": nationString = "Беларусь"; break;
			case "BEL": nationString = "Belgium"; break;
			case "BLZ": nationString = "Belize"; break;
			case "BEN": nationString = "Bénin"; break;
			case "BTN": nationString = "འབྲུག་ཡུལ"; break;
			case "BOL": nationString = "Bolivia"; break;
			case "BIH": nationString = "Bosnia and Herzegovina"; break;
			case "BWA": nationString = "Botswana"; break;
			case "BRA": nationString = "Brasil"; break;
			case "IVB": nationString = "British Virgin Islands"; break;
			case "BRN": nationString = "Brunei"; break;
			case "BGR": nationString = "Bulgaria"; break;
			case "BFA": nationString = "Burkina Faso"; break;
			case "BDI": nationString = "Burundi"; break;
			case "KHM": nationString = "កម្ពុជា"; break;
			case "CMR": nationString = "Cameroon"; break;
			case "CAN": nationString = "Canada"; break;
			case "CPV": nationString = "Cabo Verde"; break;
			case "CAF": nationString = "Central African Rep."; break;
			case "TCD": nationString = "Chad"; break;
			case "CHL": nationString = "Chile"; break;
			case "CHN": nationString = "中国"; break;
			case "COL": nationString = "Colombia"; break;
			case "COG": nationString = "Republic of the Congo";	break;
			case "COD": nationString = "Democratic Republic of the Congo"; break;
			case "CRI": nationString = "Costa Rica"; break;
			case "CIV": nationString = "Côte d'Ivoire"; break;
			case "HRV": nationString = "Hrvatska"; break;
			case "CUB": nationString = "Cuba"; break;
			case "CYP": nationString = "Cyprus"; break;
			case "CZE": nationString = "Česko";	break;
			case "DNK": nationString = "Denmark"; break;
			case "DJI": nationString = "Djibouti"; break;
			case "DMA": nationString = "Dominica"; break;
			case "DOM": nationString = "República Dominicana"; break;
			case "TLS": nationString = "East Timor"; break;
			case "ECU": nationString = "Ecuador"; break;
			case "EGY": nationString = "مصر"; break;
			case "SLV": nationString = "El Salvador"; break;
			case "GNQ": nationString = "Equatorial"; break;
			case "ERI": nationString = "Eritrea"; break;
			case "EST": nationString = "Eesti"; break;
			case "SWZ": nationString = "Eswatini"; break;
			case "ETH": nationString = "Ethiopia"; break;
			case "FSM": nationString = "F.S. Micronesia"; break;
			case "FJI": nationString = "Fiji"; break;
			case "FIN": nationString = "Suomi"; break;
			case "FRA": nationString = "France"; break;
			case "GAB": nationString = "République gabonaise"; break;
			case "GMB": nationString = "The Gambia"; break;
			case "GEO": nationString = "საქართველო"; break;
			case "DEU": nationString = "Deutschland"; break;
			case "GHA": nationString = "Ghana"; break;
			case "GIB": nationString = "Gibraltar"; break;
			case "GRC": nationString = "Ελλάς"; break;
			case "GRL": nationString = "Kalaallit Nunaat"; break;
			case "GRD": nationString = "Grenada"; break;
			case "GTM": nationString = "Guatemala"; break;
			case "GUF": nationString = "Guiana (French)"; break;
			case "GIN": nationString = "Guinea"; break;
			case "GNB": nationString = "Guiné-Bissau"; break;
			case "GUY": nationString = "Guyana"; break;
			case "HTI": nationString = "Haiti"; break;
			case "HND": nationString = "Honduras"; break;
			case "HKG": nationString = "香港"; break;
			case "HUN": nationString = "Magyarország"; break;
			case "ISL": nationString = "Ísland"; break;
			case "IND": nationString = "India"; break;
			case "IDN": nationString = "Indonesia"; break;
			case "IRN": nationString = "ایران"; break;
			case "IRQ": nationString = "العراق"; break;
			case "IRL": nationString = "Éire"; break;
			case "ISR": nationString = "Israel"; break;
			case "ITA": nationString = "Italia"; break;
			case "JAM": nationString = "Jamaica"; break;
			case "JPN": nationString = "日本"; break;
			case "JOR": nationString = "الأردن"; break;
			case "KAZ": nationString = "Қазақстан";	break;
			case "KEN": nationString = "Kenya"; break;
			case "KIR": nationString = "Kiribati";	break;
			case "PRK": nationString = "북한"; break;
			case "KOR": nationString = "한국"; break;
			case "KWT": nationString = "الكويت"; break;
			case "KGZ": nationString = "Кыргызстан"; break;
			case "LAO": nationString = "ປະເທດລາວ";	break;
			case "LVA": nationString = "Latvija"; break;
			case "LBN": nationString = "لبنان";	break;
			case "LSO": nationString = "Lesotho"; break;
			case "LBR": nationString = "Liberia"; break;
			case "LBY": nationString = "ليبيا"; break;
			case "LIE": nationString = "Liechtenstein"; break;
			case "LTU": nationString = "Lithuania";	break;
			case "LUX": nationString = "Luxembourg"; break;
			case "MDG": nationString = "Madagascar"; break;
			case "MWI": nationString = "Malawi"; break;
			case "MYS": nationString = "Malaysia"; break;
			case "MDV": nationString = "ދިވެހިރާއްޖެ"; break;
			case "MLI": nationString = "Mali"; break;
			case "MLT": nationString = "Malta";	break;
			case "MHL": nationString = "Marshall Islands"; break;
			case "MRT": nationString = "موريتانيا"; break;
			case "MUS": nationString = "Mauritius"; break;
			case "MEX": nationString = "Mexico"; break;
			case "MDA": nationString = "Moldova"; break;
			case "MCO": nationString = "Monaco"; break;
			case "MNG": nationString = "Монгол Улс"; break;
			case "MNE": nationString = "Crna Gora"; break;
			case "MAR": nationString = "Morocco"; break;
			case "MOZ": nationString = "Moçambique"; break;
			case "MMR": nationString = "Myanmar (Burma)"; break;
			case "NAM": nationString = "Namibia"; break;
			case "NRU": nationString = "Nauru"; break;
			case "NPL": nationString = "Nepal";	break;
			case "NLD": nationString = "Netherlands"; break;
			case "NZL": nationString = "New Zealand"; break;
			case "NIC": nationString = "Nicaragua"; break;
			case "NER": nationString = "Niger"; break;
			case "NGA": nationString = "Nigeria"; break;
			case "NIU": nationString = "Niue"; break;
			case "MKD": nationString = "North Macedonia"; break;
			case "NOR": nationString = "Norway"; break;
			case "OMN": nationString = "عُمان"; break;
			case "PAK": nationString = "پاکستان"; break;
			case "PLW": nationString = "Palau"; break;
			case "PSE": nationString = "فلسطين"; break;
			case "PAN": nationString = "Panamá"; break;
			case "PNG": nationString = "Papua New Guinea"; break;
			case "PRY": nationString = "Paraguay"; break;
			case "PER": nationString = "Peru"; break;
			case "PHL": nationString = "Philippines"; break;
			case "POL": nationString = "Polska"; break;
			case "PRT": nationString = "Portugal"; break;
			case "PRI": nationString = "Puerto Rico"; break;
			case "QAT": nationString = "قطر";	break;
			case "ROU": nationString = "România"; break;
			case "RUS": nationString = "Россия"; break;
			case "RWA": nationString = "Rwanda"; break;
			case "WSM": nationString = "Samoa"; break;
			case "SAU": nationString = "العربية السعودية"; break;
			case "SEN": nationString = "Senegal"; break;
			case "SRB": nationString = "Србија"; break;
			case "SYC": nationString = "Seychelles"; break;
			case "SLE": nationString = "Sierra Leone"; break;
			case "SGP": nationString = "Singapore"; break;
			case "SVK": nationString = "Slovensko"; break;
			case "SVN": nationString = "Slovenija"; break;
			case "SLB": nationString = "Solomon Islands"; break;
			case "SOM": nationString = "Somalia"; break;
			case "ZAF": nationString = "South Africa"; break;
			case "SSD": nationString = "South Sudan"; break;
			case "ESP": nationString = "España"; break;
			case "LKA": nationString = "Sri Lanka"; break;
			case "SDN": nationString = "السودان"; break;
			case "SUR": nationString = "Suriname"; break;
			case "SWE": nationString = "Sverige"; break;
			case "CHE": nationString = "Switzerland"; break;
			case "SYR": nationString = "سوريا"; break;
			case "TWN": nationString = "臺灣"; break;
			case "TJK": nationString = "Тоҷикистон"; break;
			case "TZA": nationString = "Tanzania"; break;
			case "THA": nationString = "ไทย"; break;
			case "TGO": nationString = "Togo"; break;
			case "TON": nationString = "Tonga"; break;
			case "TTO": nationString = "Trinidad and Tobago"; break;
			case "TUN": nationString = "Tunisia"; break;
			case "TUR": nationString = "Türkiye"; break;
			case "TKM": nationString = "Türkmenistan"; break;
			case "TUV": nationString = "Tuvalu"; break;
			case "UGA": nationString = "Uganda"; break;
			case "UKR": nationString = "Україна"; break;
			case "ARE": nationString = "United Arab Emirates"; break;
			case "GBR": nationString = "United Kingdom"; break;
			case "USA": nationString = "United States"; break;
			case "URY": nationString = "Uruguay"; break;
			case "UZB": nationString = "Ўзбекистон"; break;
			case "VUT": nationString = "Vanuatu"; break;
			case "VEN": nationString = "Venezuela";	break;
			case "VNM": nationString = "Việt Nam"; break;
			case "YEM": nationString = "Yemen"; break;
			case "ZMB": nationString = "Zambia"; break;
			case "ZWE": nationString = "Zimbabwe"; break;
			default: nationString = "";
		}

		return nationString;
	}

	public static String getLanguageString(String sourceLang, String targetLang) throws Exception {
		StringBuilder sb = new StringBuilder();

		if(!sourceLang.equals(targetLang)) {
			sourceLang = targetLang;
		}

		if(sourceLang.equals("ko")) {
			sb.append("<input type=\"submit\" value=\"전체\" onclick=\"javascript:goLangSearch('');\">");
			sb.append("<input type=\"submit\" value=\"한글\" onclick=\"javascript:goLangSearch('ko');\" style='border: 1px solid #FF3300;'>");
			sb.append("<input type=\"submit\" value=\"EN\" onclick=\"javascript:goLangSearch('en');\">");
			sb.append("<input type=\"submit\" value=\"中文\" onclick=\"javascript:goLangSearch('zh');\">");
			sb.append("<input type=\"submit\" value=\"FR\" onclick=\"javascript:goLangSearch('fr');\">");
			sb.append("<input type=\"submit\" value=\"PT\" onclick=\"javascript:goLangSearch('pt');\">");
			sb.append("<input type=\"submit\" value=\"DE\" onclick=\"javascript:goLangSearch('de');\">");
			sb.append("<input type=\"submit\" value=\"AR\" onclick=\"javascript:goLangSearch('ar');\">");
			sb.append("<input type=\"submit\" value=\"FA\" onclick=\"javascript:goLangSearch('fa');\">");
			sb.append("<input type=\"submit\" value=\"RU\" onclick=\"javascript:goLangSearch('ru');\">");
			sb.append("<input type=\"submit\" value=\"JA\" onclick=\"javascript:goLangSearch('ja');\">");
			sb.append("<input type=\"submit\" value=\"IT\" onclick=\"javascript:goLangSearch('it');\">");
			sb.append("<input type=\"submit\" value=\"VI\" onclick=\"javascript:goLangSearch('vi');\">");
			sb.append("<input type=\"submit\" value=\"HI\" onclick=\"javascript:goLangSearch('hi');\">");
			sb.append("<input type=\"submit\" value=\"BN\" onclick=\"javascript:goLangSearch('bn');\">");
			sb.append("<input type=\"submit\" value=\"ID\" onclick=\"javascript:goLangSearch('id');\">");
			sb.append("<input type=\"submit\" value=\"MS\" onclick=\"javascript:goLangSearch('ms');\">");
			sb.append("<input type=\"submit\" value=\"TR\" onclick=\"javascript:goLangSearch('tr');\">");
			sb.append("<input type=\"submit\" value=\"TH\" onclick=\"javascript:goLangSearch('th');\">");
			sb.append("<input type=\"submit\" value=\"MN\" onclick=\"javascript:goLangSearch('mn');\">");
		}
		else if(sourceLang.equals("en")) {
			sb.append("<input type=\"submit\" value=\"전체\" onclick=\"javascript:goLangSearch('');\">");
			sb.append("<input type=\"submit\" value=\"EN\" onclick=\"javascript:goLangSearch('en');\" style='border: 1px solid #FF3300;'>");
			sb.append("<input type=\"submit\" value=\"한글\" onclick=\"javascript:goLangSearch('ko');\">");
			sb.append("<input type=\"submit\" value=\"中文\" onclick=\"javascript:goLangSearch('zh');\">");
			sb.append("<input type=\"submit\" value=\"FR\" onclick=\"javascript:goLangSearch('fr');\">");
			sb.append("<input type=\"submit\" value=\"PT\" onclick=\"javascript:goLangSearch('pt');\">");
			sb.append("<input type=\"submit\" value=\"DE\" onclick=\"javascript:goLangSearch('de');\">");
			sb.append("<input type=\"submit\" value=\"AR\" onclick=\"javascript:goLangSearch('ar');\">");
			sb.append("<input type=\"submit\" value=\"FA\" onclick=\"javascript:goLangSearch('fa');\">");
			sb.append("<input type=\"submit\" value=\"RU\" onclick=\"javascript:goLangSearch('ru');\">");
			sb.append("<input type=\"submit\" value=\"JA\" onclick=\"javascript:goLangSearch('ja');\">");
			sb.append("<input type=\"submit\" value=\"IT\" onclick=\"javascript:goLangSearch('it');\">");
			sb.append("<input type=\"submit\" value=\"VI\" onclick=\"javascript:goLangSearch('vi');\">");
			sb.append("<input type=\"submit\" value=\"HI\" onclick=\"javascript:goLangSearch('hi');\">");
			sb.append("<input type=\"submit\" value=\"BN\" onclick=\"javascript:goLangSearch('bn');\">");
			sb.append("<input type=\"submit\" value=\"ID\" onclick=\"javascript:goLangSearch('id');\">");
			sb.append("<input type=\"submit\" value=\"MS\" onclick=\"javascript:goLangSearch('ms');\">");
			sb.append("<input type=\"submit\" value=\"TR\" onclick=\"javascript:goLangSearch('tr');\">");
			sb.append("<input type=\"submit\" value=\"TH\" onclick=\"javascript:goLangSearch('th');\">");
			sb.append("<input type=\"submit\" value=\"MN\" onclick=\"javascript:goLangSearch('mn');\">");
		}
		else if(sourceLang.equals("zh")) {
			sb.append("<input type=\"submit\" value=\"전체\" onclick=\"javascript:goLangSearch('');\">");
			sb.append("<input type=\"submit\" value=\"中文\" onclick=\"javascript:goLangSearch('zh');\" style='border: 1px solid #FF3300;'>");
			sb.append("<input type=\"submit\" value=\"한글\" onclick=\"javascript:goLangSearch('ko');\">");
			sb.append("<input type=\"submit\" value=\"EN\" onclick=\"javascript:goLangSearch('en');\">");
			sb.append("<input type=\"submit\" value=\"FR\" onclick=\"javascript:goLangSearch('fr');\">");
			sb.append("<input type=\"submit\" value=\"PT\" onclick=\"javascript:goLangSearch('pt');\">");
			sb.append("<input type=\"submit\" value=\"DE\" onclick=\"javascript:goLangSearch('de');\">");
			sb.append("<input type=\"submit\" value=\"AR\" onclick=\"javascript:goLangSearch('ar');\">");
			sb.append("<input type=\"submit\" value=\"FA\" onclick=\"javascript:goLangSearch('fa');\">");
			sb.append("<input type=\"submit\" value=\"RU\" onclick=\"javascript:goLangSearch('ru');\">");
			sb.append("<input type=\"submit\" value=\"JA\" onclick=\"javascript:goLangSearch('ja');\">");
			sb.append("<input type=\"submit\" value=\"IT\" onclick=\"javascript:goLangSearch('it');\">");
			sb.append("<input type=\"submit\" value=\"VI\" onclick=\"javascript:goLangSearch('vi');\">");
			sb.append("<input type=\"submit\" value=\"HI\" onclick=\"javascript:goLangSearch('hi');\">");
			sb.append("<input type=\"submit\" value=\"BN\" onclick=\"javascript:goLangSearch('bn');\">");
			sb.append("<input type=\"submit\" value=\"ID\" onclick=\"javascript:goLangSearch('id');\">");
			sb.append("<input type=\"submit\" value=\"MS\" onclick=\"javascript:goLangSearch('ms');\">");
			sb.append("<input type=\"submit\" value=\"TR\" onclick=\"javascript:goLangSearch('tr');\">");
			sb.append("<input type=\"submit\" value=\"TH\" onclick=\"javascript:goLangSearch('th');\">");
			sb.append("<input type=\"submit\" value=\"MN\" onclick=\"javascript:goLangSearch('mn');\">");
		}
		else if(sourceLang.equals("fr")) {
			sb.append("<input type=\"submit\" value=\"전체\" onclick=\"javascript:goLangSearch('');\">");
			sb.append("<input type=\"submit\" value=\"FR\" onclick=\"javascript:goLangSearch('fr');\" style='border: 1px solid #FF3300;'>");
			sb.append("<input type=\"submit\" value=\"한글\" onclick=\"javascript:goLangSearch('ko');\">");
			sb.append("<input type=\"submit\" value=\"EN\" onclick=\"javascript:goLangSearch('en');\">");
			sb.append("<input type=\"submit\" value=\"中文\" onclick=\"javascript:goLangSearch('zh');\">");
			sb.append("<input type=\"submit\" value=\"PT\" onclick=\"javascript:goLangSearch('pt');\">");
			sb.append("<input type=\"submit\" value=\"DE\" onclick=\"javascript:goLangSearch('de');\">");
			sb.append("<input type=\"submit\" value=\"AR\" onclick=\"javascript:goLangSearch('ar');\">");
			sb.append("<input type=\"submit\" value=\"FA\" onclick=\"javascript:goLangSearch('fa');\">");
			sb.append("<input type=\"submit\" value=\"RU\" onclick=\"javascript:goLangSearch('ru');\">");
			sb.append("<input type=\"submit\" value=\"JA\" onclick=\"javascript:goLangSearch('ja');\">");
			sb.append("<input type=\"submit\" value=\"IT\" onclick=\"javascript:goLangSearch('it');\">");
			sb.append("<input type=\"submit\" value=\"VI\" onclick=\"javascript:goLangSearch('vi');\">");
			sb.append("<input type=\"submit\" value=\"HI\" onclick=\"javascript:goLangSearch('hi');\">");
			sb.append("<input type=\"submit\" value=\"BN\" onclick=\"javascript:goLangSearch('bn');\">");
			sb.append("<input type=\"submit\" value=\"ID\" onclick=\"javascript:goLangSearch('id');\">");
			sb.append("<input type=\"submit\" value=\"MS\" onclick=\"javascript:goLangSearch('ms');\">");
			sb.append("<input type=\"submit\" value=\"TR\" onclick=\"javascript:goLangSearch('tr');\">");
			sb.append("<input type=\"submit\" value=\"TH\" onclick=\"javascript:goLangSearch('th');\">");
			sb.append("<input type=\"submit\" value=\"MN\" onclick=\"javascript:goLangSearch('mn');\">");
		}
		else if(sourceLang.equals("pt")) {
			sb.append("<input type=\"submit\" value=\"전체\" onclick=\"javascript:goLangSearch('');\">");
			sb.append("<input type=\"submit\" value=\"PT\" onclick=\"javascript:goLangSearch('pt');\" style='border: 1px solid #FF3300;'>");
			sb.append("<input type=\"submit\" value=\"한글\" onclick=\"javascript:goLangSearch('ko');\">");
			sb.append("<input type=\"submit\" value=\"EN\" onclick=\"javascript:goLangSearch('en');\">");
			sb.append("<input type=\"submit\" value=\"中文\" onclick=\"javascript:goLangSearch('zh');\">");
			sb.append("<input type=\"submit\" value=\"FR\" onclick=\"javascript:goLangSearch('fr');\">");
			sb.append("<input type=\"submit\" value=\"DE\" onclick=\"javascript:goLangSearch('de');\">");
			sb.append("<input type=\"submit\" value=\"AR\" onclick=\"javascript:goLangSearch('ar');\">");
			sb.append("<input type=\"submit\" value=\"FA\" onclick=\"javascript:goLangSearch('fa');\">");
			sb.append("<input type=\"submit\" value=\"RU\" onclick=\"javascript:goLangSearch('ru');\">");
			sb.append("<input type=\"submit\" value=\"JA\" onclick=\"javascript:goLangSearch('ja');\">");
			sb.append("<input type=\"submit\" value=\"IT\" onclick=\"javascript:goLangSearch('it');\">");
			sb.append("<input type=\"submit\" value=\"VI\" onclick=\"javascript:goLangSearch('vi');\">");
			sb.append("<input type=\"submit\" value=\"HI\" onclick=\"javascript:goLangSearch('hi');\">");
			sb.append("<input type=\"submit\" value=\"BN\" onclick=\"javascript:goLangSearch('bn');\">");
			sb.append("<input type=\"submit\" value=\"ID\" onclick=\"javascript:goLangSearch('id');\">");
			sb.append("<input type=\"submit\" value=\"MS\" onclick=\"javascript:goLangSearch('ms');\">");
			sb.append("<input type=\"submit\" value=\"TR\" onclick=\"javascript:goLangSearch('tr');\">");
			sb.append("<input type=\"submit\" value=\"TH\" onclick=\"javascript:goLangSearch('th');\">");
			sb.append("<input type=\"submit\" value=\"MN\" onclick=\"javascript:goLangSearch('mn');\">");
		}
		else if(sourceLang.equals("de")) {
			sb.append("<input type=\"submit\" value=\"전체\" onclick=\"javascript:goLangSearch('');\">");
			sb.append("<input type=\"submit\" value=\"DE\" onclick=\"javascript:goLangSearch('de');\" style='border: 1px solid #FF3300;'>");
			sb.append("<input type=\"submit\" value=\"한글\" onclick=\"javascript:goLangSearch('ko');\">");
			sb.append("<input type=\"submit\" value=\"EN\" onclick=\"javascript:goLangSearch('en');\">");
			sb.append("<input type=\"submit\" value=\"中文\" onclick=\"javascript:goLangSearch('zh');\">");
			sb.append("<input type=\"submit\" value=\"FR\" onclick=\"javascript:goLangSearch('fr');\">");
			sb.append("<input type=\"submit\" value=\"PT\" onclick=\"javascript:goLangSearch('pt');\">");
			sb.append("<input type=\"submit\" value=\"AR\" onclick=\"javascript:goLangSearch('ar');\">");
			sb.append("<input type=\"submit\" value=\"FA\" onclick=\"javascript:goLangSearch('fa');\">");
			sb.append("<input type=\"submit\" value=\"RU\" onclick=\"javascript:goLangSearch('ru');\">");
			sb.append("<input type=\"submit\" value=\"JA\" onclick=\"javascript:goLangSearch('ja');\">");
			sb.append("<input type=\"submit\" value=\"IT\" onclick=\"javascript:goLangSearch('it');\">");
			sb.append("<input type=\"submit\" value=\"VI\" onclick=\"javascript:goLangSearch('vi');\">");
			sb.append("<input type=\"submit\" value=\"HI\" onclick=\"javascript:goLangSearch('hi');\">");
			sb.append("<input type=\"submit\" value=\"BN\" onclick=\"javascript:goLangSearch('bn');\">");
			sb.append("<input type=\"submit\" value=\"ID\" onclick=\"javascript:goLangSearch('id');\">");
			sb.append("<input type=\"submit\" value=\"MS\" onclick=\"javascript:goLangSearch('ms');\">");
			sb.append("<input type=\"submit\" value=\"TR\" onclick=\"javascript:goLangSearch('tr');\">");
			sb.append("<input type=\"submit\" value=\"TH\" onclick=\"javascript:goLangSearch('th');\">");
			sb.append("<input type=\"submit\" value=\"MN\" onclick=\"javascript:goLangSearch('mn');\">");
		}
		else if(sourceLang.equals("ar")) {
			sb.append("<input type=\"submit\" value=\"전체\" onclick=\"javascript:goLangSearch('');\">");
			sb.append("<input type=\"submit\" value=\"AR\" onclick=\"javascript:goLangSearch('ar');\" style='border: 1px solid #FF3300;'>");
			sb.append("<input type=\"submit\" value=\"한글\" onclick=\"javascript:goLangSearch('ko');\">");
			sb.append("<input type=\"submit\" value=\"EN\" onclick=\"javascript:goLangSearch('en');\">");
			sb.append("<input type=\"submit\" value=\"中文\" onclick=\"javascript:goLangSearch('zh');\">");
			sb.append("<input type=\"submit\" value=\"FR\" onclick=\"javascript:goLangSearch('fr');\">");
			sb.append("<input type=\"submit\" value=\"PT\" onclick=\"javascript:goLangSearch('pt');\">");
			sb.append("<input type=\"submit\" value=\"DE\" onclick=\"javascript:goLangSearch('de');\">");
			sb.append("<input type=\"submit\" value=\"FA\" onclick=\"javascript:goLangSearch('fa');\">");
			sb.append("<input type=\"submit\" value=\"RU\" onclick=\"javascript:goLangSearch('ru');\">");
			sb.append("<input type=\"submit\" value=\"JA\" onclick=\"javascript:goLangSearch('ja');\">");
			sb.append("<input type=\"submit\" value=\"IT\" onclick=\"javascript:goLangSearch('it');\">");
			sb.append("<input type=\"submit\" value=\"VI\" onclick=\"javascript:goLangSearch('vi');\">");
			sb.append("<input type=\"submit\" value=\"HI\" onclick=\"javascript:goLangSearch('hi');\">");
			sb.append("<input type=\"submit\" value=\"BN\" onclick=\"javascript:goLangSearch('bn');\">");
			sb.append("<input type=\"submit\" value=\"ID\" onclick=\"javascript:goLangSearch('id');\">");
			sb.append("<input type=\"submit\" value=\"MS\" onclick=\"javascript:goLangSearch('ms');\">");
			sb.append("<input type=\"submit\" value=\"TR\" onclick=\"javascript:goLangSearch('tr');\">");
			sb.append("<input type=\"submit\" value=\"TH\" onclick=\"javascript:goLangSearch('th');\">");
			sb.append("<input type=\"submit\" value=\"MN\" onclick=\"javascript:goLangSearch('mn');\">");
		}
		else if(sourceLang.equals("fa")) {
			sb.append("<input type=\"submit\" value=\"전체\" onclick=\"javascript:goLangSearch('');\">");
			sb.append("<input type=\"submit\" value=\"FA\" onclick=\"javascript:goLangSearch('fa');\" style='border: 1px solid #FF3300;'>");
			sb.append("<input type=\"submit\" value=\"한글\" onclick=\"javascript:goLangSearch('ko');\">");
			sb.append("<input type=\"submit\" value=\"EN\" onclick=\"javascript:goLangSearch('en');\">");
			sb.append("<input type=\"submit\" value=\"中文\" onclick=\"javascript:goLangSearch('zh');\">");
			sb.append("<input type=\"submit\" value=\"FR\" onclick=\"javascript:goLangSearch('fr');\">");
			sb.append("<input type=\"submit\" value=\"PT\" onclick=\"javascript:goLangSearch('pt');\">");
			sb.append("<input type=\"submit\" value=\"DE\" onclick=\"javascript:goLangSearch('de');\">");
			sb.append("<input type=\"submit\" value=\"AR\" onclick=\"javascript:goLangSearch('ar');\">");
			sb.append("<input type=\"submit\" value=\"RU\" onclick=\"javascript:goLangSearch('ru');\">");
			sb.append("<input type=\"submit\" value=\"JA\" onclick=\"javascript:goLangSearch('ja');\">");
			sb.append("<input type=\"submit\" value=\"IT\" onclick=\"javascript:goLangSearch('it');\">");
			sb.append("<input type=\"submit\" value=\"VI\" onclick=\"javascript:goLangSearch('vi');\">");
			sb.append("<input type=\"submit\" value=\"HI\" onclick=\"javascript:goLangSearch('hi');\">");
			sb.append("<input type=\"submit\" value=\"BN\" onclick=\"javascript:goLangSearch('bn');\">");
			sb.append("<input type=\"submit\" value=\"ID\" onclick=\"javascript:goLangSearch('id');\">");
			sb.append("<input type=\"submit\" value=\"MS\" onclick=\"javascript:goLangSearch('ms');\">");
			sb.append("<input type=\"submit\" value=\"TR\" onclick=\"javascript:goLangSearch('tr');\">");
			sb.append("<input type=\"submit\" value=\"TH\" onclick=\"javascript:goLangSearch('th');\">");
			sb.append("<input type=\"submit\" value=\"MN\" onclick=\"javascript:goLangSearch('mn');\">");
		}
		else if(sourceLang.equals("ru")) {
			sb.append("<input type=\"submit\" value=\"전체\" onclick=\"javascript:goLangSearch('');\">");
			sb.append("<input type=\"submit\" value=\"RU\" onclick=\"javascript:goLangSearch('ru');\" style='border: 1px solid #FF3300;'>");
			sb.append("<input type=\"submit\" value=\"한글\" onclick=\"javascript:goLangSearch('ko');\">");
			sb.append("<input type=\"submit\" value=\"EN\" onclick=\"javascript:goLangSearch('en');\">");
			sb.append("<input type=\"submit\" value=\"中文\" onclick=\"javascript:goLangSearch('zh');\">");
			sb.append("<input type=\"submit\" value=\"FR\" onclick=\"javascript:goLangSearch('fr');\">");
			sb.append("<input type=\"submit\" value=\"PT\" onclick=\"javascript:goLangSearch('pt');\">");
			sb.append("<input type=\"submit\" value=\"DE\" onclick=\"javascript:goLangSearch('de');\">");
			sb.append("<input type=\"submit\" value=\"AR\" onclick=\"javascript:goLangSearch('ar');\">");
			sb.append("<input type=\"submit\" value=\"FA\" onclick=\"javascript:goLangSearch('fa');\">");
			sb.append("<input type=\"submit\" value=\"JA\" onclick=\"javascript:goLangSearch('ja');\">");
			sb.append("<input type=\"submit\" value=\"IT\" onclick=\"javascript:goLangSearch('it');\">");
			sb.append("<input type=\"submit\" value=\"VI\" onclick=\"javascript:goLangSearch('vi');\">");
			sb.append("<input type=\"submit\" value=\"HI\" onclick=\"javascript:goLangSearch('hi');\">");
			sb.append("<input type=\"submit\" value=\"BN\" onclick=\"javascript:goLangSearch('bn');\">");
			sb.append("<input type=\"submit\" value=\"ID\" onclick=\"javascript:goLangSearch('id');\">");
			sb.append("<input type=\"submit\" value=\"MS\" onclick=\"javascript:goLangSearch('ms');\">");
			sb.append("<input type=\"submit\" value=\"TR\" onclick=\"javascript:goLangSearch('tr');\">");
			sb.append("<input type=\"submit\" value=\"TH\" onclick=\"javascript:goLangSearch('th');\">");
			sb.append("<input type=\"submit\" value=\"MN\" onclick=\"javascript:goLangSearch('mn');\">");
		}
		else if(sourceLang.equals("ja")) {
			sb.append("<input type=\"submit\" value=\"전체\" onclick=\"javascript:goLangSearch('');\">");
			sb.append("<input type=\"submit\" value=\"JA\" onclick=\"javascript:goLangSearch('ja');\" style='border: 1px solid #FF3300;'>");
			sb.append("<input type=\"submit\" value=\"한글\" onclick=\"javascript:goLangSearch('ko');\">");
			sb.append("<input type=\"submit\" value=\"EN\" onclick=\"javascript:goLangSearch('en');\">");
			sb.append("<input type=\"submit\" value=\"中文\" onclick=\"javascript:goLangSearch('zh');\">");
			sb.append("<input type=\"submit\" value=\"FR\" onclick=\"javascript:goLangSearch('fr');\">");
			sb.append("<input type=\"submit\" value=\"PT\" onclick=\"javascript:goLangSearch('pt');\">");
			sb.append("<input type=\"submit\" value=\"DE\" onclick=\"javascript:goLangSearch('de');\">");
			sb.append("<input type=\"submit\" value=\"AR\" onclick=\"javascript:goLangSearch('ar');\">");
			sb.append("<input type=\"submit\" value=\"FA\" onclick=\"javascript:goLangSearch('fa');\">");
			sb.append("<input type=\"submit\" value=\"RU\" onclick=\"javascript:goLangSearch('ru');\">");
			sb.append("<input type=\"submit\" value=\"IT\" onclick=\"javascript:goLangSearch('it');\">");
			sb.append("<input type=\"submit\" value=\"VI\" onclick=\"javascript:goLangSearch('vi');\">");
			sb.append("<input type=\"submit\" value=\"HI\" onclick=\"javascript:goLangSearch('hi');\">");
			sb.append("<input type=\"submit\" value=\"BN\" onclick=\"javascript:goLangSearch('bn');\">");
			sb.append("<input type=\"submit\" value=\"ID\" onclick=\"javascript:goLangSearch('id');\">");
			sb.append("<input type=\"submit\" value=\"MS\" onclick=\"javascript:goLangSearch('ms');\">");
			sb.append("<input type=\"submit\" value=\"TR\" onclick=\"javascript:goLangSearch('tr');\">");
			sb.append("<input type=\"submit\" value=\"TH\" onclick=\"javascript:goLangSearch('th');\">");
			sb.append("<input type=\"submit\" value=\"MN\" onclick=\"javascript:goLangSearch('mn');\">");
		}
		else if(sourceLang.equals("it")) {
			sb.append("<input type=\"submit\" value=\"전체\" onclick=\"javascript:goLangSearch('');\">");
			sb.append("<input type=\"submit\" value=\"IT\" onclick=\"javascript:goLangSearch('it');\" style='border: 1px solid #FF3300;'>");
			sb.append("<input type=\"submit\" value=\"한글\" onclick=\"javascript:goLangSearch('ko');\">");
			sb.append("<input type=\"submit\" value=\"EN\" onclick=\"javascript:goLangSearch('en');\">");
			sb.append("<input type=\"submit\" value=\"中文\" onclick=\"javascript:goLangSearch('zh');\">");
			sb.append("<input type=\"submit\" value=\"FR\" onclick=\"javascript:goLangSearch('fr');\">");
			sb.append("<input type=\"submit\" value=\"PT\" onclick=\"javascript:goLangSearch('pt');\">");
			sb.append("<input type=\"submit\" value=\"DE\" onclick=\"javascript:goLangSearch('de');\">");
			sb.append("<input type=\"submit\" value=\"AR\" onclick=\"javascript:goLangSearch('ar');\">");
			sb.append("<input type=\"submit\" value=\"FA\" onclick=\"javascript:goLangSearch('fa');\">");
			sb.append("<input type=\"submit\" value=\"RU\" onclick=\"javascript:goLangSearch('ru');\">");
			sb.append("<input type=\"submit\" value=\"JA\" onclick=\"javascript:goLangSearch('ja');\">");
			sb.append("<input type=\"submit\" value=\"VI\" onclick=\"javascript:goLangSearch('vi');\">");
			sb.append("<input type=\"submit\" value=\"HI\" onclick=\"javascript:goLangSearch('hi');\">");
			sb.append("<input type=\"submit\" value=\"BN\" onclick=\"javascript:goLangSearch('bn');\">");
			sb.append("<input type=\"submit\" value=\"ID\" onclick=\"javascript:goLangSearch('id');\">");
			sb.append("<input type=\"submit\" value=\"MS\" onclick=\"javascript:goLangSearch('ms');\">");
			sb.append("<input type=\"submit\" value=\"TR\" onclick=\"javascript:goLangSearch('tr');\">");
			sb.append("<input type=\"submit\" value=\"TH\" onclick=\"javascript:goLangSearch('th');\">");
			sb.append("<input type=\"submit\" value=\"MN\" onclick=\"javascript:goLangSearch('mn');\">");
		}
		else if(sourceLang.equals("vi")) {
			sb.append("<input type=\"submit\" value=\"전체\" onclick=\"javascript:goLangSearch('');\">");
			sb.append("<input type=\"submit\" value=\"VI\" onclick=\"javascript:goLangSearch('vi');\" style='border: 1px solid #FF3300;'>");
			sb.append("<input type=\"submit\" value=\"한글\" onclick=\"javascript:goLangSearch('ko');\">");
			sb.append("<input type=\"submit\" value=\"EN\" onclick=\"javascript:goLangSearch('en');\">");
			sb.append("<input type=\"submit\" value=\"中文\" onclick=\"javascript:goLangSearch('zh');\">");
			sb.append("<input type=\"submit\" value=\"FR\" onclick=\"javascript:goLangSearch('fr');\">");
			sb.append("<input type=\"submit\" value=\"PT\" onclick=\"javascript:goLangSearch('pt');\">");
			sb.append("<input type=\"submit\" value=\"DE\" onclick=\"javascript:goLangSearch('de');\">");
			sb.append("<input type=\"submit\" value=\"AR\" onclick=\"javascript:goLangSearch('ar');\">");
			sb.append("<input type=\"submit\" value=\"FA\" onclick=\"javascript:goLangSearch('fa');\">");
			sb.append("<input type=\"submit\" value=\"RU\" onclick=\"javascript:goLangSearch('ru');\">");
			sb.append("<input type=\"submit\" value=\"JA\" onclick=\"javascript:goLangSearch('ja');\">");
			sb.append("<input type=\"submit\" value=\"IT\" onclick=\"javascript:goLangSearch('it');\">");
			sb.append("<input type=\"submit\" value=\"HI\" onclick=\"javascript:goLangSearch('hi');\">");
			sb.append("<input type=\"submit\" value=\"BN\" onclick=\"javascript:goLangSearch('bn');\">");
			sb.append("<input type=\"submit\" value=\"ID\" onclick=\"javascript:goLangSearch('id');\">");
			sb.append("<input type=\"submit\" value=\"MS\" onclick=\"javascript:goLangSearch('ms');\">");
			sb.append("<input type=\"submit\" value=\"TR\" onclick=\"javascript:goLangSearch('tr');\">");
			sb.append("<input type=\"submit\" value=\"TH\" onclick=\"javascript:goLangSearch('th');\">");
			sb.append("<input type=\"submit\" value=\"MN\" onclick=\"javascript:goLangSearch('mn');\">");
		}
		else if(sourceLang.equals("hi")) {
			sb.append("<input type=\"submit\" value=\"전체\" onclick=\"javascript:goLangSearch('');\">");
			sb.append("<input type=\"submit\" value=\"HI\" onclick=\"javascript:goLangSearch('hi');\" style='border: 1px solid #FF3300;'>");
			sb.append("<input type=\"submit\" value=\"한글\" onclick=\"javascript:goLangSearch('ko');\">");
			sb.append("<input type=\"submit\" value=\"EN\" onclick=\"javascript:goLangSearch('en');\">");
			sb.append("<input type=\"submit\" value=\"中文\" onclick=\"javascript:goLangSearch('zh');\">");
			sb.append("<input type=\"submit\" value=\"FR\" onclick=\"javascript:goLangSearch('fr');\">");
			sb.append("<input type=\"submit\" value=\"PT\" onclick=\"javascript:goLangSearch('pt');\">");
			sb.append("<input type=\"submit\" value=\"DE\" onclick=\"javascript:goLangSearch('de');\">");
			sb.append("<input type=\"submit\" value=\"AR\" onclick=\"javascript:goLangSearch('ar');\">");
			sb.append("<input type=\"submit\" value=\"FA\" onclick=\"javascript:goLangSearch('fa');\">");
			sb.append("<input type=\"submit\" value=\"RU\" onclick=\"javascript:goLangSearch('ru');\">");
			sb.append("<input type=\"submit\" value=\"JA\" onclick=\"javascript:goLangSearch('ja');\">");
			sb.append("<input type=\"submit\" value=\"IT\" onclick=\"javascript:goLangSearch('it');\">");
			sb.append("<input type=\"submit\" value=\"VI\" onclick=\"javascript:goLangSearch('vi');\">");
			sb.append("<input type=\"submit\" value=\"BN\" onclick=\"javascript:goLangSearch('bn');\">");
			sb.append("<input type=\"submit\" value=\"ID\" onclick=\"javascript:goLangSearch('id');\">");
			sb.append("<input type=\"submit\" value=\"MS\" onclick=\"javascript:goLangSearch('ms');\">");
			sb.append("<input type=\"submit\" value=\"TR\" onclick=\"javascript:goLangSearch('tr');\">");
			sb.append("<input type=\"submit\" value=\"TH\" onclick=\"javascript:goLangSearch('th');\">");
			sb.append("<input type=\"submit\" value=\"MN\" onclick=\"javascript:goLangSearch('mn');\">");
		}
		else if(sourceLang.equals("bn")) {
			sb.append("<input type=\"submit\" value=\"전체\" onclick=\"javascript:goLangSearch('');\">");
			sb.append("<input type=\"submit\" value=\"BN\" onclick=\"javascript:goLangSearch('bn');\" style='border: 1px solid #FF3300;'>");
			sb.append("<input type=\"submit\" value=\"한글\" onclick=\"javascript:goLangSearch('ko');\">");
			sb.append("<input type=\"submit\" value=\"EN\" onclick=\"javascript:goLangSearch('en');\">");
			sb.append("<input type=\"submit\" value=\"中文\" onclick=\"javascript:goLangSearch('zh');\">");
			sb.append("<input type=\"submit\" value=\"FR\" onclick=\"javascript:goLangSearch('fr');\">");
			sb.append("<input type=\"submit\" value=\"PT\" onclick=\"javascript:goLangSearch('pt');\">");
			sb.append("<input type=\"submit\" value=\"DE\" onclick=\"javascript:goLangSearch('de');\">");
			sb.append("<input type=\"submit\" value=\"AR\" onclick=\"javascript:goLangSearch('ar');\">");
			sb.append("<input type=\"submit\" value=\"FA\" onclick=\"javascript:goLangSearch('fa');\">");
			sb.append("<input type=\"submit\" value=\"RU\" onclick=\"javascript:goLangSearch('ru');\">");
			sb.append("<input type=\"submit\" value=\"JA\" onclick=\"javascript:goLangSearch('ja');\">");
			sb.append("<input type=\"submit\" value=\"IT\" onclick=\"javascript:goLangSearch('it');\">");
			sb.append("<input type=\"submit\" value=\"VI\" onclick=\"javascript:goLangSearch('vi');\">");
			sb.append("<input type=\"submit\" value=\"HI\" onclick=\"javascript:goLangSearch('hi');\">");
			sb.append("<input type=\"submit\" value=\"ID\" onclick=\"javascript:goLangSearch('id');\">");
			sb.append("<input type=\"submit\" value=\"MS\" onclick=\"javascript:goLangSearch('ms');\">");
			sb.append("<input type=\"submit\" value=\"TR\" onclick=\"javascript:goLangSearch('tr');\">");
			sb.append("<input type=\"submit\" value=\"TH\" onclick=\"javascript:goLangSearch('th');\">");
			sb.append("<input type=\"submit\" value=\"MN\" onclick=\"javascript:goLangSearch('mn');\">");
		}
		else if(sourceLang.equals("id")) {
			sb.append("<input type=\"submit\" value=\"전체\" onclick=\"javascript:goLangSearch('');\">");
			sb.append("<input type=\"submit\" value=\"ID\" onclick=\"javascript:goLangSearch('id');\" style='border: 1px solid #FF3300;'>");
			sb.append("<input type=\"submit\" value=\"한글\" onclick=\"javascript:goLangSearch('ko');\">");
			sb.append("<input type=\"submit\" value=\"EN\" onclick=\"javascript:goLangSearch('en');\">");
			sb.append("<input type=\"submit\" value=\"中文\" onclick=\"javascript:goLangSearch('zh');\">");
			sb.append("<input type=\"submit\" value=\"FR\" onclick=\"javascript:goLangSearch('fr');\">");
			sb.append("<input type=\"submit\" value=\"PT\" onclick=\"javascript:goLangSearch('pt');\">");
			sb.append("<input type=\"submit\" value=\"DE\" onclick=\"javascript:goLangSearch('de');\">");
			sb.append("<input type=\"submit\" value=\"AR\" onclick=\"javascript:goLangSearch('ar');\">");
			sb.append("<input type=\"submit\" value=\"FA\" onclick=\"javascript:goLangSearch('fa');\">");
			sb.append("<input type=\"submit\" value=\"RU\" onclick=\"javascript:goLangSearch('ru');\">");
			sb.append("<input type=\"submit\" value=\"JA\" onclick=\"javascript:goLangSearch('ja');\">");
			sb.append("<input type=\"submit\" value=\"IT\" onclick=\"javascript:goLangSearch('it');\">");
			sb.append("<input type=\"submit\" value=\"VI\" onclick=\"javascript:goLangSearch('vi');\">");
			sb.append("<input type=\"submit\" value=\"HI\" onclick=\"javascript:goLangSearch('hi');\">");
			sb.append("<input type=\"submit\" value=\"BN\" onclick=\"javascript:goLangSearch('bn');\">");
			sb.append("<input type=\"submit\" value=\"MS\" onclick=\"javascript:goLangSearch('ms');\">");
			sb.append("<input type=\"submit\" value=\"TR\" onclick=\"javascript:goLangSearch('tr');\">");
			sb.append("<input type=\"submit\" value=\"TH\" onclick=\"javascript:goLangSearch('th');\">");
			sb.append("<input type=\"submit\" value=\"MN\" onclick=\"javascript:goLangSearch('mn');\">");
		}
		else if(sourceLang.equals("ms")) {
			sb.append("<input type=\"submit\" value=\"전체\" onclick=\"javascript:goLangSearch('');\">");
			sb.append("<input type=\"submit\" value=\"MS\" onclick=\"javascript:goLangSearch('ms');\" style='border: 1px solid #FF3300;'>");
			sb.append("<input type=\"submit\" value=\"한글\" onclick=\"javascript:goLangSearch('ko');\">");
			sb.append("<input type=\"submit\" value=\"EN\" onclick=\"javascript:goLangSearch('en');\">");
			sb.append("<input type=\"submit\" value=\"中文\" onclick=\"javascript:goLangSearch('zh');\">");
			sb.append("<input type=\"submit\" value=\"FR\" onclick=\"javascript:goLangSearch('fr');\">");
			sb.append("<input type=\"submit\" value=\"PT\" onclick=\"javascript:goLangSearch('pt');\">");
			sb.append("<input type=\"submit\" value=\"DE\" onclick=\"javascript:goLangSearch('de');\">");
			sb.append("<input type=\"submit\" value=\"AR\" onclick=\"javascript:goLangSearch('ar');\">");
			sb.append("<input type=\"submit\" value=\"FA\" onclick=\"javascript:goLangSearch('fa');\">");
			sb.append("<input type=\"submit\" value=\"RU\" onclick=\"javascript:goLangSearch('ru');\">");
			sb.append("<input type=\"submit\" value=\"JA\" onclick=\"javascript:goLangSearch('ja');\">");
			sb.append("<input type=\"submit\" value=\"IT\" onclick=\"javascript:goLangSearch('it');\">");
			sb.append("<input type=\"submit\" value=\"VI\" onclick=\"javascript:goLangSearch('vi');\">");
			sb.append("<input type=\"submit\" value=\"HI\" onclick=\"javascript:goLangSearch('hi');\">");
			sb.append("<input type=\"submit\" value=\"BN\" onclick=\"javascript:goLangSearch('bn');\">");
			sb.append("<input type=\"submit\" value=\"ID\" onclick=\"javascript:goLangSearch('id');\">");
			sb.append("<input type=\"submit\" value=\"TR\" onclick=\"javascript:goLangSearch('tr');\">");
			sb.append("<input type=\"submit\" value=\"TH\" onclick=\"javascript:goLangSearch('th');\">");
			sb.append("<input type=\"submit\" value=\"MN\" onclick=\"javascript:goLangSearch('mn');\">");
		}
		else if(sourceLang.equals("tr")) {
			sb.append("<input type=\"submit\" value=\"전체\" onclick=\"javascript:goLangSearch('');\">");
			sb.append("<input type=\"submit\" value=\"TR\" onclick=\"javascript:goLangSearch('tr');\" style='border: 1px solid #FF3300;'>");
			sb.append("<input type=\"submit\" value=\"한글\" onclick=\"javascript:goLangSearch('ko');\">");
			sb.append("<input type=\"submit\" value=\"EN\" onclick=\"javascript:goLangSearch('en');\">");
			sb.append("<input type=\"submit\" value=\"中文\" onclick=\"javascript:goLangSearch('zh');\">");
			sb.append("<input type=\"submit\" value=\"FR\" onclick=\"javascript:goLangSearch('fr');\">");
			sb.append("<input type=\"submit\" value=\"PT\" onclick=\"javascript:goLangSearch('pt');\">");
			sb.append("<input type=\"submit\" value=\"DE\" onclick=\"javascript:goLangSearch('de');\">");
			sb.append("<input type=\"submit\" value=\"AR\" onclick=\"javascript:goLangSearch('ar');\">");
			sb.append("<input type=\"submit\" value=\"FA\" onclick=\"javascript:goLangSearch('fa');\">");
			sb.append("<input type=\"submit\" value=\"RU\" onclick=\"javascript:goLangSearch('ru');\">");
			sb.append("<input type=\"submit\" value=\"JA\" onclick=\"javascript:goLangSearch('ja');\">");
			sb.append("<input type=\"submit\" value=\"IT\" onclick=\"javascript:goLangSearch('it');\">");
			sb.append("<input type=\"submit\" value=\"VI\" onclick=\"javascript:goLangSearch('vi');\">");
			sb.append("<input type=\"submit\" value=\"HI\" onclick=\"javascript:goLangSearch('hi');\">");
			sb.append("<input type=\"submit\" value=\"BN\" onclick=\"javascript:goLangSearch('bn');\">");
			sb.append("<input type=\"submit\" value=\"ID\" onclick=\"javascript:goLangSearch('id');\">");
			sb.append("<input type=\"submit\" value=\"MS\" onclick=\"javascript:goLangSearch('ms');\">");
			sb.append("<input type=\"submit\" value=\"TH\" onclick=\"javascript:goLangSearch('th');\">");
			sb.append("<input type=\"submit\" value=\"MN\" onclick=\"javascript:goLangSearch('mn');\">");
		}
		else if(sourceLang.equals("th")) {
			sb.append("<input type=\"submit\" value=\"전체\" onclick=\"javascript:goLangSearch('');\">");
			sb.append("<input type=\"submit\" value=\"TH\" onclick=\"javascript:goLangSearch('th');\" style='border: 1px solid #FF3300;'>");
			sb.append("<input type=\"submit\" value=\"한글\" onclick=\"javascript:goLangSearch('ko');\">");
			sb.append("<input type=\"submit\" value=\"EN\" onclick=\"javascript:goLangSearch('en');\">");
			sb.append("<input type=\"submit\" value=\"中文\" onclick=\"javascript:goLangSearch('zh');\">");
			sb.append("<input type=\"submit\" value=\"FR\" onclick=\"javascript:goLangSearch('fr');\">");
			sb.append("<input type=\"submit\" value=\"PT\" onclick=\"javascript:goLangSearch('pt');\">");
			sb.append("<input type=\"submit\" value=\"DE\" onclick=\"javascript:goLangSearch('de');\">");
			sb.append("<input type=\"submit\" value=\"AR\" onclick=\"javascript:goLangSearch('ar');\">");
			sb.append("<input type=\"submit\" value=\"FA\" onclick=\"javascript:goLangSearch('fa');\">");
			sb.append("<input type=\"submit\" value=\"RU\" onclick=\"javascript:goLangSearch('ru');\">");
			sb.append("<input type=\"submit\" value=\"JA\" onclick=\"javascript:goLangSearch('ja');\">");
			sb.append("<input type=\"submit\" value=\"IT\" onclick=\"javascript:goLangSearch('it');\">");
			sb.append("<input type=\"submit\" value=\"VI\" onclick=\"javascript:goLangSearch('vi');\">");
			sb.append("<input type=\"submit\" value=\"HI\" onclick=\"javascript:goLangSearch('hi');\">");
			sb.append("<input type=\"submit\" value=\"BN\" onclick=\"javascript:goLangSearch('bn');\">");
			sb.append("<input type=\"submit\" value=\"ID\" onclick=\"javascript:goLangSearch('id');\">");
			sb.append("<input type=\"submit\" value=\"MS\" onclick=\"javascript:goLangSearch('ms');\">");
			sb.append("<input type=\"submit\" value=\"TR\" onclick=\"javascript:goLangSearch('tr');\">");
			sb.append("<input type=\"submit\" value=\"MN\" onclick=\"javascript:goLangSearch('mn');\">");
		}
		else if(sourceLang.equals("mn")) {
			sb.append("<input type=\"submit\" value=\"전체\" onclick=\"javascript:goLangSearch('');\">");
			sb.append("<input type=\"submit\" value=\"MN\" onclick=\"javascript:goLangSearch('mn');\" style='border: 1px solid #FF3300;'>");
			sb.append("<input type=\"submit\" value=\"한글\" onclick=\"javascript:goLangSearch('ko');\">");
			sb.append("<input type=\"submit\" value=\"EN\" onclick=\"javascript:goLangSearch('en');\">");
			sb.append("<input type=\"submit\" value=\"中文\" onclick=\"javascript:goLangSearch('zh');\">");
			sb.append("<input type=\"submit\" value=\"FR\" onclick=\"javascript:goLangSearch('fr');\">");
			sb.append("<input type=\"submit\" value=\"PT\" onclick=\"javascript:goLangSearch('pt');\">");
			sb.append("<input type=\"submit\" value=\"DE\" onclick=\"javascript:goLangSearch('de');\">");
			sb.append("<input type=\"submit\" value=\"AR\" onclick=\"javascript:goLangSearch('ar');\">");
			sb.append("<input type=\"submit\" value=\"FA\" onclick=\"javascript:goLangSearch('fa');\">");
			sb.append("<input type=\"submit\" value=\"RU\" onclick=\"javascript:goLangSearch('ru');\">");
			sb.append("<input type=\"submit\" value=\"JA\" onclick=\"javascript:goLangSearch('ja');\">");
			sb.append("<input type=\"submit\" value=\"IT\" onclick=\"javascript:goLangSearch('it');\">");
			sb.append("<input type=\"submit\" value=\"VI\" onclick=\"javascript:goLangSearch('vi');\">");
			sb.append("<input type=\"submit\" value=\"HI\" onclick=\"javascript:goLangSearch('hi');\">");
			sb.append("<input type=\"submit\" value=\"BN\" onclick=\"javascript:goLangSearch('bn');\">");
			sb.append("<input type=\"submit\" value=\"ID\" onclick=\"javascript:goLangSearch('id');\">");
			sb.append("<input type=\"submit\" value=\"MS\" onclick=\"javascript:goLangSearch('ms');\">");
			sb.append("<input type=\"submit\" value=\"TR\" onclick=\"javascript:goLangSearch('tr');\">");
			sb.append("<input type=\"submit\" value=\"TH\" onclick=\"javascript:goLangSearch('th');\">");
		}
		else {
			sb.append("<input type=\"submit\" value=\"전체\" onclick=\"javascript:goLangSearch('');\" style='border: 1px solid #FF3300;'>");
			sb.append("<input type=\"submit\" value=\"한글\" onclick=\"javascript:goLangSearch('ko');\">");
			sb.append("<input type=\"submit\" value=\"EN\" onclick=\"javascript:goLangSearch('en');\">");
			sb.append("<input type=\"submit\" value=\"中文\" onclick=\"javascript:goLangSearch('zh');\">");
			sb.append("<input type=\"submit\" value=\"FR\" onclick=\"javascript:goLangSearch('fr');\">");
			sb.append("<input type=\"submit\" value=\"PT\" onclick=\"javascript:goLangSearch('pt');\">");
			sb.append("<input type=\"submit\" value=\"DE\" onclick=\"javascript:goLangSearch('de');\">");
			sb.append("<input type=\"submit\" value=\"AR\" onclick=\"javascript:goLangSearch('ar');\">");
			sb.append("<input type=\"submit\" value=\"FA\" onclick=\"javascript:goLangSearch('fa');\">");
			sb.append("<input type=\"submit\" value=\"RU\" onclick=\"javascript:goLangSearch('ru');\">");
			sb.append("<input type=\"submit\" value=\"JA\" onclick=\"javascript:goLangSearch('ja');\">");
			sb.append("<input type=\"submit\" value=\"IT\" onclick=\"javascript:goLangSearch('it');\">");
			sb.append("<input type=\"submit\" value=\"VI\" onclick=\"javascript:goLangSearch('vi');\">");
			sb.append("<input type=\"submit\" value=\"HI\" onclick=\"javascript:goLangSearch('hi');\">");
			sb.append("<input type=\"submit\" value=\"BN\" onclick=\"javascript:goLangSearch('bn');\">");
			sb.append("<input type=\"submit\" value=\"ID\" onclick=\"javascript:goLangSearch('id');\">");
			sb.append("<input type=\"submit\" value=\"MS\" onclick=\"javascript:goLangSearch('ms');\">");
			sb.append("<input type=\"submit\" value=\"TR\" onclick=\"javascript:goLangSearch('tr');\">");
			sb.append("<input type=\"submit\" value=\"TH\" onclick=\"javascript:goLangSearch('th');\">");
			sb.append("<input type=\"submit\" value=\"MN\" onclick=\"javascript:goLangSearch('mn');\">");
		}
		return sb.toString();
	}

	public static String getLanguageSelString(String sourceLang, String targetLang) throws Exception {
		StringBuilder sb = new StringBuilder();

		if(!sourceLang.equals(targetLang)) {
			sourceLang = targetLang;
		}
		sb.append("<select id='langSel' onChange=\"javascript:goLangChange();\">");
		if(sourceLang.equals("ko")) {
			sb.append("<option value=''>전체</opton>");
			sb.append("<option value='ko' selected='selected'>한글</opton>");
			sb.append("<option value='en'>EN</opton>");
			sb.append("<option value='zh'>中文</opton>");
			sb.append("<option value='fr'>FR</opton>");
			sb.append("<option value='pt'>PT</opton>");
			sb.append("<option value='de'>DE</opton>");
			sb.append("<option value='ar'>AR</opton>");
			sb.append("<option value='fa'>FA</opton>");
			sb.append("<option value='ru'>RU</opton>");
			sb.append("<option value='ja'>JA</opton>");
			sb.append("<option value='it'>IT</opton>");
			sb.append("<option value='vi'>VI</opton>");
			sb.append("<option value='hi'>HI</opton>");
			sb.append("<option value='bn'>BN</opton>");
			sb.append("<option value='id'>ID</opton>");
			sb.append("<option value='ms'>MS</opton>");
			sb.append("<option value='tr'>TR</opton>");
			sb.append("<option value='th'>TH</opton>");
			sb.append("<option value='mn'>MN</opton>");
		}
		else if(sourceLang.equals("en")) {
			sb.append("<option value=''>전체</opton>");
			sb.append("<option value='en' selected='selected'>EN</opton>");
			sb.append("<option value='ko'>한글</opton>");
			sb.append("<option value='zh'>中文</opton>");
			sb.append("<option value='fr'>FR</opton>");
			sb.append("<option value='pt'>PT</opton>");
			sb.append("<option value='de'>DE</opton>");
			sb.append("<option value='ar'>AR</opton>");
			sb.append("<option value='fa'>FA</opton>");
			sb.append("<option value='ru'>RU</opton>");
			sb.append("<option value='ja'>JA</opton>");
			sb.append("<option value='it'>IT</opton>");
			sb.append("<option value='vi'>VI</opton>");
			sb.append("<option value='hi'>HI</opton>");
			sb.append("<option value='bn'>BN</opton>");
			sb.append("<option value='id'>ID</opton>");
			sb.append("<option value='ms'>MS</opton>");
			sb.append("<option value='tr'>TR</opton>");
			sb.append("<option value='th'>TH</opton>");
			sb.append("<option value='mn'>MN</opton>");
		}
		else if(sourceLang.equals("zh")) {
			sb.append("<option value=''>전체</opton>");
			sb.append("<option value='zh' selected='selected'>中文</opton>");
			sb.append("<option value='ko'>한글</opton>");
			sb.append("<option value='en'>EN</opton>");
			sb.append("<option value='fr'>FR</opton>");
			sb.append("<option value='pt'>PT</opton>");
			sb.append("<option value='de'>DE</opton>");
			sb.append("<option value='ar'>AR</opton>");
			sb.append("<option value='fa'>FA</opton>");
			sb.append("<option value='ru'>RU</opton>");
			sb.append("<option value='ja'>JA</opton>");
			sb.append("<option value='it'>IT</opton>");
			sb.append("<option value='vi'>VI</opton>");
			sb.append("<option value='hi'>HI</opton>");
			sb.append("<option value='bn'>BN</opton>");
			sb.append("<option value='id'>ID</opton>");
			sb.append("<option value='ms'>MS</opton>");
			sb.append("<option value='tr'>TR</opton>");
			sb.append("<option value='th'>TH</opton>");
			sb.append("<option value='mn'>MN</opton>");
		}
		else if(sourceLang.equals("fr")) {
			sb.append("<option value=''>전체</opton>");
			sb.append("<option value='fr' selected='selected'>FR</opton>");
			sb.append("<option value='ko'>한글</opton>");
			sb.append("<option value='en'>EN</opton>");
			sb.append("<option value='zh'>中文</opton>");
			sb.append("<option value='pt'>PT</opton>");
			sb.append("<option value='de'>DE</opton>");
			sb.append("<option value='ar'>AR</opton>");
			sb.append("<option value='fa'>FA</opton>");
			sb.append("<option value='ru'>RU</opton>");
			sb.append("<option value='ja'>JA</opton>");
			sb.append("<option value='it'>IT</opton>");
			sb.append("<option value='vi'>VI</opton>");
			sb.append("<option value='hi'>HI</opton>");
			sb.append("<option value='bn'>BN</opton>");
			sb.append("<option value='id'>ID</opton>");
			sb.append("<option value='ms'>MS</opton>");
			sb.append("<option value='tr'>TR</opton>");
			sb.append("<option value='th'>TH</opton>");
			sb.append("<option value='mn'>MN</opton>");
		}
		else if(sourceLang.equals("pt")) {
			sb.append("<option value=''>전체</opton>");
			sb.append("<option value='pt' selected='selected'>PT</opton>");
			sb.append("<option value='ko'>한글</opton>");
			sb.append("<option value='en'>EN</opton>");
			sb.append("<option value='zh'>中文</opton>");
			sb.append("<option value='fr'>FR</opton>");
			sb.append("<option value='de'>DE</opton>");
			sb.append("<option value='ar'>AR</opton>");
			sb.append("<option value='fa'>FA</opton>");
			sb.append("<option value='ru'>RU</opton>");
			sb.append("<option value='ja'>JA</opton>");
			sb.append("<option value='it'>IT</opton>");
			sb.append("<option value='vi'>VI</opton>");
			sb.append("<option value='hi'>HI</opton>");
			sb.append("<option value='bn'>BN</opton>");
			sb.append("<option value='id'>ID</opton>");
			sb.append("<option value='ms'>MS</opton>");
			sb.append("<option value='tr'>TR</opton>");
			sb.append("<option value='th'>TH</opton>");
			sb.append("<option value='mn'>MN</opton>");
		}
		else if(sourceLang.equals("de")) {
			sb.append("<option value=''>전체</opton>");
			sb.append("<option value='de' selected='selected'>DE</opton>");
			sb.append("<option value='ko'>한글</opton>");
			sb.append("<option value='en'>EN</opton>");
			sb.append("<option value='zh'>中文</opton>");
			sb.append("<option value='fr'>FR</opton>");
			sb.append("<option value='pt'>PT</opton>");
			sb.append("<option value='ar'>AR</opton>");
			sb.append("<option value='fa'>FA</opton>");
			sb.append("<option value='ru'>RU</opton>");
			sb.append("<option value='ja'>JA</opton>");
			sb.append("<option value='it'>IT</opton>");
			sb.append("<option value='vi'>VI</opton>");
			sb.append("<option value='hi'>HI</opton>");
			sb.append("<option value='bn'>BN</opton>");
			sb.append("<option value='id'>ID</opton>");
			sb.append("<option value='ms'>MS</opton>");
			sb.append("<option value='tr'>TR</opton>");
			sb.append("<option value='th'>TH</opton>");
			sb.append("<option value='mn'>MN</opton>");
		}
		else if(sourceLang.equals("ar")) {
			sb.append("<option value=''>전체</opton>");
			sb.append("<option value='ar' selected='selected'>AR</opton>");
			sb.append("<option value='ko'>한글</opton>");
			sb.append("<option value='en'>EN</opton>");
			sb.append("<option value='zh'>中文</opton>");
			sb.append("<option value='fr'>FR</opton>");
			sb.append("<option value='pt'>PT</opton>");
			sb.append("<option value='de'>DE</opton>");
			sb.append("<option value='fa'>FA</opton>");
			sb.append("<option value='ru'>RU</opton>");
			sb.append("<option value='ja'>JA</opton>");
			sb.append("<option value='it'>IT</opton>");
			sb.append("<option value='vi'>VI</opton>");
			sb.append("<option value='hi'>HI</opton>");
			sb.append("<option value='bn'>BN</opton>");
			sb.append("<option value='id'>ID</opton>");
			sb.append("<option value='ms'>MS</opton>");
			sb.append("<option value='tr'>TR</opton>");
			sb.append("<option value='th'>TH</opton>");
			sb.append("<option value='mn'>MN</opton>");
		}
		else if(sourceLang.equals("fa")) {
			sb.append("<option value=''>전체</opton>");
			sb.append("<option value='fa' selected='selected'>FA</opton>");
			sb.append("<option value='ko'>한글</opton>");
			sb.append("<option value='en'>EN</opton>");
			sb.append("<option value='zh'>中文</opton>");
			sb.append("<option value='fr'>FR</opton>");
			sb.append("<option value='pt'>PT</opton>");
			sb.append("<option value='de'>DE</opton>");
			sb.append("<option value='ar'>AR</opton>");
			sb.append("<option value='ru'>RU</opton>");
			sb.append("<option value='ja'>JA</opton>");
			sb.append("<option value='it'>IT</opton>");
			sb.append("<option value='vi'>VI</opton>");
			sb.append("<option value='hi'>HI</opton>");
			sb.append("<option value='bn'>BN</opton>");
			sb.append("<option value='id'>ID</opton>");
			sb.append("<option value='ms'>MS</opton>");
			sb.append("<option value='tr'>TR</opton>");
			sb.append("<option value='th'>TH</opton>");
			sb.append("<option value='mn'>MN</opton>");
		}
		else if(sourceLang.equals("ru")) {
			sb.append("<option value=''>전체</opton>");
			sb.append("<option value='ru' selected='selected'>RU</opton>");
			sb.append("<option value='ko'>한글</opton>");
			sb.append("<option value='en'>EN</opton>");
			sb.append("<option value='zh'>中文</opton>");
			sb.append("<option value='fr'>FR</opton>");
			sb.append("<option value='pt'>PT</opton>");
			sb.append("<option value='de'>DE</opton>");
			sb.append("<option value='ar'>AR</opton>");
			sb.append("<option value='fa'>FA</opton>");
			sb.append("<option value='ja'>JA</opton>");
			sb.append("<option value='it'>IT</opton>");
			sb.append("<option value='vi'>VI</opton>");
			sb.append("<option value='hi'>HI</opton>");
			sb.append("<option value='bn'>BN</opton>");
			sb.append("<option value='id'>ID</opton>");
			sb.append("<option value='ms'>MS</opton>");
			sb.append("<option value='tr'>TR</opton>");
			sb.append("<option value='th'>TH</opton>");
			sb.append("<option value='mn'>MN</opton>");
		}
		else if(sourceLang.equals("ja")) {
			sb.append("<option value=''>전체</opton>");
			sb.append("<option value='ja' selected='selected'>JA</opton>");
			sb.append("<option value='ko'>한글</opton>");
			sb.append("<option value='en'>EN</opton>");
			sb.append("<option value='zh'>中文</opton>");
			sb.append("<option value='fr'>FR</opton>");
			sb.append("<option value='pt'>PT</opton>");
			sb.append("<option value='de'>DE</opton>");
			sb.append("<option value='ar'>AR</opton>");
			sb.append("<option value='fa'>FA</opton>");
			sb.append("<option value='ru'>RU</opton>");
			sb.append("<option value='it'>IT</opton>");
			sb.append("<option value='vi'>VI</opton>");
			sb.append("<option value='hi'>HI</opton>");
			sb.append("<option value='bn'>BN</opton>");
			sb.append("<option value='id'>ID</opton>");
			sb.append("<option value='ms'>MS</opton>");
			sb.append("<option value='tr'>TR</opton>");
			sb.append("<option value='th'>TH</opton>");
			sb.append("<option value='mn'>MN</opton>");
		}
		else if(sourceLang.equals("it")) {
			sb.append("<option value=''>전체</opton>");
			sb.append("<option value='it' selected='selected'>IT</opton>");
			sb.append("<option value='ko'>한글</opton>");
			sb.append("<option value='en'>EN</opton>");
			sb.append("<option value='zh'>中文</opton>");
			sb.append("<option value='fr'>FR</opton>");
			sb.append("<option value='pt'>PT</opton>");
			sb.append("<option value='de'>DE</opton>");
			sb.append("<option value='ar'>AR</opton>");
			sb.append("<option value='fa'>FA</opton>");
			sb.append("<option value='ru'>RU</opton>");
			sb.append("<option value='ja'>JA</opton>");
			sb.append("<option value='vi'>VI</opton>");
			sb.append("<option value='hi'>HI</opton>");
			sb.append("<option value='bn'>BN</opton>");
			sb.append("<option value='id'>ID</opton>");
			sb.append("<option value='ms'>MS</opton>");
			sb.append("<option value='tr'>TR</opton>");
			sb.append("<option value='th'>TH</opton>");
			sb.append("<option value='mn'>MN</opton>");
		}
		else if(sourceLang.equals("vi")) {
			sb.append("<option value=''>전체</opton>");
			sb.append("<option value='vi' selected='selected'>VI</opton>");
			sb.append("<option value='ko'>한글</opton>");
			sb.append("<option value='en'>EN</opton>");
			sb.append("<option value='zh'>中文</opton>");
			sb.append("<option value='fr'>FR</opton>");
			sb.append("<option value='pt'>PT</opton>");
			sb.append("<option value='de'>DE</opton>");
			sb.append("<option value='ar'>AR</opton>");
			sb.append("<option value='fa'>FA</opton>");
			sb.append("<option value='ru'>RU</opton>");
			sb.append("<option value='ja'>JA</opton>");
			sb.append("<option value='it'>IT</opton>");
			sb.append("<option value='hi'>HI</opton>");
			sb.append("<option value='bn'>BN</opton>");
			sb.append("<option value='id'>ID</opton>");
			sb.append("<option value='ms'>MS</opton>");
			sb.append("<option value='tr'>TR</opton>");
			sb.append("<option value='th'>TH</opton>");
			sb.append("<option value='mn'>MN</opton>");
		}
		else if(sourceLang.equals("hi")) {
			sb.append("<option value=''>전체</opton>");
			sb.append("<option value='hi' selected='selected'>HI</opton>");
			sb.append("<option value='ko'>한글</opton>");
			sb.append("<option value='en'>EN</opton>");
			sb.append("<option value='zh'>中文</opton>");
			sb.append("<option value='fr'>FR</opton>");
			sb.append("<option value='pt'>PT</opton>");
			sb.append("<option value='de'>DE</opton>");
			sb.append("<option value='ar'>AR</opton>");
			sb.append("<option value='fa'>FA</opton>");
			sb.append("<option value='ru'>RU</opton>");
			sb.append("<option value='ja'>JA</opton>");
			sb.append("<option value='it'>IT</opton>");
			sb.append("<option value='vi'>VI</opton>");
			sb.append("<option value='bn'>BN</opton>");
			sb.append("<option value='id'>ID</opton>");
			sb.append("<option value='ms'>MS</opton>");
			sb.append("<option value='tr'>TR</opton>");
			sb.append("<option value='th'>TH</opton>");
			sb.append("<option value='mn'>MN</opton>");
		}
		else if(sourceLang.equals("bn")) {
			sb.append("<option value=''>전체</opton>");
			sb.append("<option value='bn' selected='selected'>BN</opton>");
			sb.append("<option value='ko'>한글</opton>");
			sb.append("<option value='en'>EN</opton>");
			sb.append("<option value='zh'>中文</opton>");
			sb.append("<option value='fr'>FR</opton>");
			sb.append("<option value='pt'>PT</opton>");
			sb.append("<option value='de'>DE</opton>");
			sb.append("<option value='ar'>AR</opton>");
			sb.append("<option value='fa'>FA</opton>");
			sb.append("<option value='ru'>RU</opton>");
			sb.append("<option value='ja'>JA</opton>");
			sb.append("<option value='it'>IT</opton>");
			sb.append("<option value='vi'>VI</opton>");
			sb.append("<option value='hi'>HI</opton>");
			sb.append("<option value='id'>ID</opton>");
			sb.append("<option value='ms'>MS</opton>");
			sb.append("<option value='tr'>TR</opton>");
			sb.append("<option value='th'>TH</opton>");
			sb.append("<option value='mn'>MN</opton>");
		}
		else if(sourceLang.equals("id")) {
			sb.append("<option value=''>전체</opton>");
			sb.append("<option value='id' selected='selected'>ID</opton>");
			sb.append("<option value='ko'>한글</opton>");
			sb.append("<option value='en'>EN</opton>");
			sb.append("<option value='zh'>中文</opton>");
			sb.append("<option value='fr'>FR</opton>");
			sb.append("<option value='pt'>PT</opton>");
			sb.append("<option value='de'>DE</opton>");
			sb.append("<option value='ar'>AR</opton>");
			sb.append("<option value='fa'>FA</opton>");
			sb.append("<option value='ru'>RU</opton>");
			sb.append("<option value='ja'>JA</opton>");
			sb.append("<option value='it'>IT</opton>");
			sb.append("<option value='vi'>VI</opton>");
			sb.append("<option value='hi'>HI</opton>");
			sb.append("<option value='bn'>BN</opton>");
			sb.append("<option value='ms'>MS</opton>");
			sb.append("<option value='tr'>TR</opton>");
			sb.append("<option value='th'>TH</opton>");
			sb.append("<option value='mn'>MN</opton>");
		}
		else if(sourceLang.equals("ms")) {
			sb.append("<option value=''>전체</opton>");
			sb.append("<option value='ms' selected='selected'>MS</opton>");
			sb.append("<option value='ko'>한글</opton>");
			sb.append("<option value='en'>EN</opton>");
			sb.append("<option value='zh'>中文</opton>");
			sb.append("<option value='fr'>FR</opton>");
			sb.append("<option value='pt'>PT</opton>");
			sb.append("<option value='de'>DE</opton>");
			sb.append("<option value='ar'>AR</opton>");
			sb.append("<option value='fa'>FA</opton>");
			sb.append("<option value='ru'>RU</opton>");
			sb.append("<option value='ja'>JA</opton>");
			sb.append("<option value='it'>IT</opton>");
			sb.append("<option value='vi'>VI</opton>");
			sb.append("<option value='hi'>HI</opton>");
			sb.append("<option value='bn'>BN</opton>");
			sb.append("<option value='id'>ID</opton>");
			sb.append("<option value='tr'>TR</opton>");
			sb.append("<option value='th'>TH</opton>");
			sb.append("<option value='mn'>MN</opton>");
		}
		else if(sourceLang.equals("tr")) {
			sb.append("<option value=''>전체</opton>");
			sb.append("<option value='tr' selected='selected'>TR</opton>");
			sb.append("<option value='ko'>한글</opton>");
			sb.append("<option value='en'>EN</opton>");
			sb.append("<option value='zh'>中文</opton>");
			sb.append("<option value='fr'>FR</opton>");
			sb.append("<option value='pt'>PT</opton>");
			sb.append("<option value='de'>DE</opton>");
			sb.append("<option value='ar'>AR</opton>");
			sb.append("<option value='fa'>FA</opton>");
			sb.append("<option value='ru'>RU</opton>");
			sb.append("<option value='ja'>JA</opton>");
			sb.append("<option value='it'>IT</opton>");
			sb.append("<option value='vi'>VI</opton>");
			sb.append("<option value='hi'>HI</opton>");
			sb.append("<option value='bn'>BN</opton>");
			sb.append("<option value='id'>ID</opton>");
			sb.append("<option value='ms'>MS</opton>");
			sb.append("<option value='th'>TH</opton>");
			sb.append("<option value='mn'>MN</opton>");
		}
		else if(sourceLang.equals("th")) {
			sb.append("<option value=''>전체</opton>");
			sb.append("<option value='th' selected='selected'>TH</opton>");
			sb.append("<option value='ko'>한글</opton>");
			sb.append("<option value='en'>EN</opton>");
			sb.append("<option value='zh'>中文</opton>");
			sb.append("<option value='fr'>FR</opton>");
			sb.append("<option value='pt'>PT</opton>");
			sb.append("<option value='de'>DE</opton>");
			sb.append("<option value='ar'>AR</opton>");
			sb.append("<option value='fa'>FA</opton>");
			sb.append("<option value='ru'>RU</opton>");
			sb.append("<option value='ja'>JA</opton>");
			sb.append("<option value='it'>IT</opton>");
			sb.append("<option value='vi'>VI</opton>");
			sb.append("<option value='hi'>HI</opton>");
			sb.append("<option value='bn'>BN</opton>");
			sb.append("<option value='id'>ID</opton>");
			sb.append("<option value='ms'>MS</opton>");
			sb.append("<option value='tr'>TR</opton>");
			sb.append("<option value='mn'>MN</opton>");
		}
		else if(sourceLang.equals("mn")) {
			sb.append("<option value=''>전체</opton>");
			sb.append("<option value='mn' selected='selected'>MN</opton>");
			sb.append("<option value='ko'>한글</opton>");
			sb.append("<option value='en'>EN</opton>");
			sb.append("<option value='zh'>中文</opton>");
			sb.append("<option value='fr'>FR</opton>");
			sb.append("<option value='pt'>PT</opton>");
			sb.append("<option value='de'>DE</opton>");
			sb.append("<option value='ar'>AR</opton>");
			sb.append("<option value='fa'>FA</opton>");
			sb.append("<option value='ru'>RU</opton>");
			sb.append("<option value='ja'>JA</opton>");
			sb.append("<option value='it'>IT</opton>");
			sb.append("<option value='vi'>VI</opton>");
			sb.append("<option value='hi'>HI</opton>");
			sb.append("<option value='bn'>BN</opton>");
			sb.append("<option value='id'>ID</opton>");
			sb.append("<option value='ms'>MS</opton>");
			sb.append("<option value='tr'>TR</opton>");
			sb.append("<option value='th'>TH</opton>");
		}
		else {
			sb.append("<option value='' selected='selected'>전체</opton>");
			sb.append("<option value='ko'>한글</opton>");
			sb.append("<option value='en'>EN</opton>");
			sb.append("<option value='zh'>中文</opton>");
			sb.append("<option value='fr'>FR</opton>");
			sb.append("<option value='pt'>PT</opton>");
			sb.append("<option value='de'>DE</opton>");
			sb.append("<option value='ar'>AR</opton>");
			sb.append("<option value='fa'>FA</opton>");
			sb.append("<option value='ru'>RU</opton>");
			sb.append("<option value='ja'>JA</opton>");
			sb.append("<option value='it'>IT</opton>");
			sb.append("<option value='vi'>VI</opton>");
			sb.append("<option value='hi'>HI</opton>");
			sb.append("<option value='bn'>BN</opton>");
			sb.append("<option value='id'>ID</opton>");
			sb.append("<option value='ms'>MS</opton>");
			sb.append("<option value='tr'>TR</opton>");
			sb.append("<option value='th'>TH</opton>");
			sb.append("<option value='mn'>MN</opton>");
		}
		sb.append("</select>");

		return sb.toString();
	}

	public static void setLanguage(String lang, HttpServletResponse response) {
		Cookie info = new Cookie("s_lang", lang );

		info.setVersion( 0 );
		info.setMaxAge( 10000 * 24 * 60 * 60 );
		info.setPath("/");

		response.addCookie(info);
	}

	public static String getLanguage(HttpServletRequest request, HttpServletResponse response) {
		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("s_lang");
		String lang = "";

		if(cookies1 != null) {
			lang = String.valueOf( cookieBox.getCookie("s_lang") );
		}

		return lang;
	}

	public static String getDecriptString(String str) {
		String result = "";
		try {
			EncLibrary enc = new EncLibrary();
			result = enc.AlmoneyDecrypt( str );
		}
		catch(Exception e) {
			result = str;
		}
		return result;
	}

	public static boolean writeTraceLogForJsp(String from, String msg) {

		boolean flag = false;

		if(from != null && !from.isEmpty()) {
			if(msg != null && !msg.isEmpty()) {
				logger.trace("["+from+"] " + msg);
				flag = true;
			}else {
				if(msg == null) {
					logger.trace("Log Message is Null. from : " + from);
				}else {
					logger.trace("Log Message is Empty. from : " + from);
				}
			}
		}else {
			if(from == null) {
				logger.trace("Log From is Null. message : " + msg);
			}else {
				logger.trace("Log From is Empty. message : " + msg);
			}
		}

		return flag;
	}
}
