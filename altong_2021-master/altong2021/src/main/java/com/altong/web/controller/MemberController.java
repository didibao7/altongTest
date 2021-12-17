package com.altong.web.controller;

import java.io.File;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.altong.web.logic.LogAlmoneyVO;
import com.altong.web.logic.LogViewVO;
import com.altong.web.logic.almoney.AlmoneyVO;
import com.altong.web.logic.answer.AnswerVO;
import com.altong.web.logic.member.MemberVO;
import com.altong.web.logic.member.MyZzimVO;
import com.altong.web.logic.member.PartnerVO;
import com.altong.web.logic.question.QuestionVO;
import com.altong.web.logic.reply.ReplyVO;
import com.altong.web.logic.section.SectionVO;
import com.altong.web.logic.util.CodeUtil;
import com.altong.web.logic.util.CommonUtil;
import com.altong.web.logic.util.EncLibrary;
import com.altong.web.logic.util.MD5Class;
import com.altong.web.logic.util.UtilFile;
import com.altong.web.service.CookieLoginService;
import com.altong.web.service.answer.AnswerService;
import com.altong.web.service.common.CommonService;
import com.altong.web.service.member.MemberService;
import com.altong.web.service.question.QuestionService;
import com.altong.web.service.reply.ReplyService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.coobird.thumbnailator.Thumbnails;

@Controller
@RequestMapping("member/*")
public class MemberController {
	@Autowired
	MemberService memberService;

	@Autowired
	CommonService commonService;

	@Autowired
	AnswerService answerService;

	@Autowired
	ReplyService replyService;

	@Autowired
	QuestionService questionService;

	@Autowired
	CookieLocaleResolver localResolver;

	@Autowired
	MessageSource messageSource;

	@Autowired
	CookieLoginService cookieLoginService;

	@RequestMapping("alarm/alarmConfig")
	public ModelAndView alarmConfig (HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();
		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq == 0) {
			mav.setViewName("redirect:/default/main");

			return mav;
		}

		Map<String, Object> getAlarmInfo = memberService.getUserAlarmConfig(userSeq);

		HashMap<String, Object> alamrConfig = new HashMap<String, Object>();

		if(getAlarmInfo != null) {
			alamrConfig.put("AnsChoice_Alarm_Yn".toUpperCase(), 		getAlarmInfo.get("AnsChoice_Alarm_Yn").toString());
			alamrConfig.put("AnsRegist_Alarm_Yn".toUpperCase(), 		getAlarmInfo.get("AnsRegist_Alarm_Yn").toString());
			alamrConfig.put("FavoriteQueRegist_Alarm_Yn".toUpperCase(), getAlarmInfo.get("FavoriteQueRegist_Alarm_Yn").toString());
			alamrConfig.put("CmtRegist_Alarm_Yn".toUpperCase(), 		getAlarmInfo.get("CmtRegist_Alarm_Yn").toString());
			alamrConfig.put("AnsChoiceReady_Alarm_Yn".toUpperCase(), 	getAlarmInfo.get("AnsChoiceReady_Alarm_Yn").toString());
			alamrConfig.put("AlmoneyIncome_Alarm_Yn".toUpperCase(), 	getAlarmInfo.get("AlmoneyIncome_Alarm_Yn").toString());
			alamrConfig.put("AlmoneyPaying_Alarm_Yn".toUpperCase(), 	getAlarmInfo.get("AlmoneyPaying_Alarm_Yn").toString());
			alamrConfig.put("MemLevelUp_Alarm_Yn".toUpperCase(), 		getAlarmInfo.get("MemLevelUp_Alarm_Yn").toString());
			alamrConfig.put("Report_Alarm_Yn".toUpperCase(), 			getAlarmInfo.get("Report_Alarm_Yn").toString());
			alamrConfig.put("Mentee_Alarm_Yn".toUpperCase(), 			getAlarmInfo.get("Mentee_Alarm_Yn").toString());
			alamrConfig.put("MenteeUnset_Alarm_Yn".toUpperCase(), 		getAlarmInfo.get("MenteeUnset_Alarm_Yn").toString());
			alamrConfig.put("RecommMemJoin_Alarm_Yn".toUpperCase(), 	getAlarmInfo.get("RecommMemJoin_Alarm_Yn").toString());
			alamrConfig.put("Notice_Alarm_Yn".toUpperCase(), 			getAlarmInfo.get("Notice_Alarm_Yn").toString());
			alamrConfig.put("Message_Alarm_Yn".toUpperCase(), 			getAlarmInfo.get("Message_Alarm_Yn").toString());
		}
		else {
			alamrConfig.put("AnsChoice_Alarm_Yn".toUpperCase(), 		"Y"); //AnsChoice_Alarm_Yn
			alamrConfig.put("AnsRegist_Alarm_Yn".toUpperCase(), 		"Y"); //AnsRegist_Alarm_Yn
			alamrConfig.put("FavoriteQueRegist_Alarm_Yn".toUpperCase(), "Y"); //FavoriteQueRegist_Alarm_Yn
			alamrConfig.put("CmtRegist_Alarm_Yn".toUpperCase(), 		"Y"); //CmtRegist_Alarm_Yn
			alamrConfig.put("AnsChoiceReady_Alarm_Yn".toUpperCase(), 	"Y"); //AnsChoiceReady_Alarm_Yn
			alamrConfig.put("AlmoneyIncome_Alarm_Yn".toUpperCase(), 	"Y"); //AlmoneyIncome_Alarm_Yn
			alamrConfig.put("AlmoneyPaying_Alarm_Yn".toUpperCase(), 	"Y"); //AlmoneyPaying_Alarm_Yn
			alamrConfig.put("MemLevelUp_Alarm_Yn".toUpperCase(), 		"Y"); //MemLevelUp_Alarm_Yn
			alamrConfig.put("Report_Alarm_Yn".toUpperCase(), 			"Y"); //Report_Alarm_Yn
			alamrConfig.put("Mentee_Alarm_Yn".toUpperCase(), 			"Y"); //Mentee_Alarm_Yn
			alamrConfig.put("MenteeUnset_Alarm_Yn".toUpperCase(), 		"Y"); //MenteeUnset_Alarm_Yn
			alamrConfig.put("RecommMemJoin_Alarm_Yn".toUpperCase(), 	"Y"); //RecommMemJoin_Alarm_Yn
			alamrConfig.put("Notice_Alarm_Yn".toUpperCase(), 			"Y"); //Notice_Alarm_Yn
			alamrConfig.put("Message_Alarm_Yn".toUpperCase(), 			"Y"); //Notice_Alarm_Yn
		}

		mav.addObject("alamrConfig", alamrConfig);

		return mav;
	}

	@RequestMapping(value="alarm/alarmConfigSave", method = RequestMethod.POST)
	public @ResponseBody String alarmConfigSave(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String result = null;

		CodeUtil code = null;

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);


		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		SimpleDriverDataSource ds = (SimpleDriverDataSource) context.getBean("dataSource");

		String driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";//ds.getDriver().getClass().toString();
		String url = ds.getUrl();
		String username = ds.getUsername();
		String password = ds.getPassword();

		Class.forName(driverClass);
		Connection conn;
		Statement stmt;

		conn = DriverManager.getConnection(url,username,password);

		CallableStatement cs = null;
		stmt = conn.createStatement();

		if(userSeq > 0) {
			cs = conn.prepareCall("{call SP2_MEMBER_ALARM_CONFIG_SET(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setInt(1, userSeq );

			code = new CodeUtil(request);


			HashMap<String, Object> paramHash = new HashMap<String, Object>();
			for(int i = 1; i <= code.getCODE_MEM_ALARM_VIEW_FIELD_CD().size(); i++) {
				String codeName = code.getCODE_MEM_ALARM_VIEW_FIELD_CD().get(String.valueOf(i));


				if(codeName.equals("ANS_CHOICE"))			paramHash.put("ANS_CHOICE", String.valueOf(request.getParameter(codeName)).equals("on") ? "Y" : "N" );
				if(codeName.equals("ANS_REGIST")) 			paramHash.put("ANS_REGIST", String.valueOf(request.getParameter(codeName)).equals("on") ? "Y" : "N" );
				if(codeName.equals("FAVORITE_QUE_REGIST")) 	paramHash.put("FAVORITE_QUE_REGIST", String.valueOf(request.getParameter(codeName)).equals("on")? "Y" : "N" );
				if(codeName.equals("CMT_REGIST")) 			paramHash.put("CMT_REGIST", String.valueOf(request.getParameter(codeName)).equals("on")? "Y" : "N" );
				if(codeName.equals("ANS_CHOICE_READY")) 	paramHash.put("ANS_CHOICE_READY", "Y" ); //String.valueOf(request.getParameter(codeName)).equals("on") ? "Y" : "N"
				if(codeName.equals("ALMONEY_INCOME")) 		paramHash.put("ALMONEY_INCOME", String.valueOf(request.getParameter(codeName)).equals("on") ? "Y" : "N" );
				if(codeName.equals("ALMONEY_PAYING")) 		paramHash.put("ALMONEY_PAYING", String.valueOf(request.getParameter(codeName)).equals("on") ? "Y" : "N" );
				if(codeName.equals("MEM_LEVEL_UP")) 		paramHash.put("MEM_LEVEL_UP", String.valueOf(request.getParameter(codeName)).equals("on") ? "Y" : "N" );
				if(codeName.equals("REPORT")) 				paramHash.put("REPORT", String.valueOf(request.getParameter(codeName)).equals("on") ? "Y" : "N" );
				if(codeName.equals("MENTEE")) 				paramHash.put("MENTEE", String.valueOf(request.getParameter(codeName)).equals("on") ? "Y" : "N" );
				if(codeName.equals("MENTEE_UNSET")) 		paramHash.put("MENTEE_UNSET", String.valueOf(request.getParameter(codeName)).equals("on") ? "Y" : "N" );
				if(codeName.equals("RECOMM_MEM_JOIN"))	 	paramHash.put("RECOMM_MEM_JOIN", String.valueOf(request.getParameter(codeName)).equals("on") ? "Y" : "N" );
				if(codeName.equals("NOTICE")) 				paramHash.put("NOTICE", String.valueOf(request.getParameter(codeName)).equals("on") ? "Y" : "N" );

			}


			cs.setString(2, paramHash.get("ANS_CHOICE").toString() );
			cs.setString(3, paramHash.get("ANS_REGIST").toString() );
			cs.setString(4, paramHash.get("FAVORITE_QUE_REGIST").toString() );
			cs.setString(5, paramHash.get("CMT_REGIST").toString() );
			cs.setString(6, paramHash.get("ANS_CHOICE_READY").toString() );
			cs.setString(7, paramHash.get("ALMONEY_INCOME").toString() );
			cs.setString(8, paramHash.get("ALMONEY_PAYING").toString() );
			cs.setString(9, paramHash.get("MEM_LEVEL_UP").toString() );
			cs.setString(10, paramHash.get("REPORT").toString() );
			cs.setString(11, paramHash.get("MENTEE").toString() );
			cs.setString(12, paramHash.get("MENTEE_UNSET").toString() );
			cs.setString(13, paramHash.get("RECOMM_MEM_JOIN").toString() );
			cs.setString(14, paramHash.get("NOTICE").toString() );


			cs.executeQuery();
		}

		return result;
	}

	@RequestMapping("alarm/alarm")
	public ModelAndView alarmx(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		HttpSession session = request.getSession(true);
		Map<String, Object> globalVal = null;


		JSONObject global = null;
		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if( userSeq != 0 ) {
		}
		else {
			//System.out.println("알람 쿠키 없음");
			mav.setViewName("redirect:/default/main");

			return mav;
		}


		HashMap<String, Object> alamrCount = memberService.getUserAlarmCount(userSeq);

		mav.addObject("ANS_CHOICE", 					Integer.parseInt( alamrCount.get("ANS_CHOICE").toString() ) );
		mav.addObject("ANS_CHOICE_DATE_REG", 			alamrCount.get("ANS_CHOICE_DATE_REG") );
		mav.addObject("ANS_REGIST", 					Integer.parseInt( alamrCount.get("ANS_REGIST").toString() ));
		mav.addObject("ANS_REGIST_DATE_REG", 			alamrCount.get("ANS_REGIST_DATE_REG") );
		mav.addObject("FAVORITE_QUE_REGIST", 			Integer.parseInt( alamrCount.get("FAVORITE_QUE_REGIST").toString() ));
		mav.addObject("FAVORITE_QUE_REGIST_DATE_REG", 	alamrCount.get("FAVORITE_QUE_REGIST_DATE_REG") );
		mav.addObject("CMT_REGIST", 					Integer.parseInt( alamrCount.get("CMT_REGIST").toString() ));
		mav.addObject("CMT_REGIST_DATE_REG", 			alamrCount.get("CMT_REGIST_DATE_REG") );
		mav.addObject("ANS_CHOICE_READY", 				Integer.parseInt( alamrCount.get("ANS_CHOICE_READY").toString() ));
		mav.addObject("ANS_CHOICE_READY_DATE_REG", 		alamrCount.get("ANS_CHOICE_READY_DATE_REG") );
		mav.addObject("ALMONEY_INCOME", 				Integer.parseInt( alamrCount.get("ALMONEY_INCOME").toString() ));
		mav.addObject("ALMONEY_INCOME_DATE_REG", 		alamrCount.get("ALMONEY_INCOME_DATE_REG") );
		mav.addObject("ALMONEY_PAYING", 				Integer.parseInt( alamrCount.get("ALMONEY_PAYING").toString() ));
		mav.addObject("ALMONEY_PAYING_DATE_REG", 		alamrCount.get("ALMONEY_PAYING_DATE_REG") );
		mav.addObject("MEM_LEVEL_UP", 					Integer.parseInt( alamrCount.get("MEM_LEVEL_UP").toString() ));
		mav.addObject("MEM_LEVEL_UP_DATE_REG", 			alamrCount.get("MEM_LEVEL_UP_DATE_REG") );
		mav.addObject("REPORT", 						Integer.parseInt( alamrCount.get("REPORT").toString() ));
		mav.addObject("REPORT_DATE_REG", 				alamrCount.get("REPORT_DATE_REG") );
		mav.addObject("MENTEE", 						Integer.parseInt( alamrCount.get("MENTEE").toString() ));
		mav.addObject("MENTEE_DATE_REG", 				alamrCount.get("MENTEE_DATE_REG") );
		mav.addObject("MENTEE_UNSET", 					Integer.parseInt( alamrCount.get("MENTEE_UNSET").toString() ));
		mav.addObject("MENTEE_UNSET_DATE_REG", 			alamrCount.get("MENTEE_UNSET_DATE_REG") );
		mav.addObject("RECOMM_MEM_JOIN", 				Integer.parseInt( alamrCount.get("RECOMM_MEM_JOIN").toString() ));
		mav.addObject("RECOMM_MEM_JOIN_DATE_REG", 		alamrCount.get("RECOMM_MEM_JOIN_DATE_REG") );
		mav.addObject("NOTICE", 						Integer.parseInt( alamrCount.get("NOTICE").toString() ));
		mav.addObject("NOTICE_DATE_REG", 				alamrCount.get("NOTICE_DATE_REG") );
		mav.addObject("ALARM", 							Integer.parseInt( alamrCount.get("ALARM").toString() ));
		mav.addObject("ALARM_DATE_REG", 				alamrCount.get("ALARM_DATE_REG") );



		Map<String, Object> getAlarmInfo = memberService.getUserAlarmConfig(userSeq);

		if(getAlarmInfo != null) {

			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_ANS_CHOICE", CommonUtil.checkShowAlarm(getAlarmInfo.get("AnsChoice_Alarm_Yn").toString(), alamrCount.get("ANS_CHOICE").toString()) );

			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_ANS_REGIST", CommonUtil.checkShowAlarm(getAlarmInfo.get("AnsRegist_Alarm_Yn").toString(), alamrCount.get("ANS_REGIST").toString()) );

			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_FAVORITE_QUE_REGIST", CommonUtil.checkShowAlarm(getAlarmInfo.get("FavoriteQueRegist_Alarm_Yn").toString(), alamrCount.get("FAVORITE_QUE_REGIST").toString()) );

			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_CMT_REGIST", CommonUtil.checkShowAlarm(getAlarmInfo.get("CmtRegist_Alarm_Yn").toString(), alamrCount.get("CMT_REGIST").toString()) );

			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_ANS_CHOICE_READY", CommonUtil.checkShowAlarm(getAlarmInfo.get("AnsChoiceReady_Alarm_Yn").toString(), alamrCount.get("ANS_CHOICE_READY").toString()) );

			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_ALMONEY_INCOME", CommonUtil.checkShowAlarm(getAlarmInfo.get("AlmoneyIncome_Alarm_Yn").toString(), alamrCount.get("ALMONEY_INCOME").toString()) );

			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_ALMONEY_PAYING", CommonUtil.checkShowAlarm(getAlarmInfo.get("AlmoneyPaying_Alarm_Yn").toString(), alamrCount.get("ALMONEY_PAYING").toString()) );

			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_MEM_LEVEL_UP", CommonUtil.checkShowAlarm(getAlarmInfo.get("AnsChoice_Alarm_Yn").toString(), alamrCount.get("MEM_LEVEL_UP").toString()) );

			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_REPORT", CommonUtil.checkShowAlarm(getAlarmInfo.get("Report_Alarm_Yn").toString(), alamrCount.get("REPORT").toString()) );

			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_MENTEE", CommonUtil.checkShowAlarm(getAlarmInfo.get("Mentee_Alarm_Yn").toString(), alamrCount.get("MENTEE").toString()) );

			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_MENTEE_UNSET", CommonUtil.checkShowAlarm(getAlarmInfo.get("MenteeUnset_Alarm_Yn").toString(), alamrCount.get("MENTEE_UNSET").toString()) );

			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_RECOMM_MEM_JOIN", CommonUtil.checkShowAlarm(getAlarmInfo.get("RecommMemJoin_Alarm_Yn").toString(), alamrCount.get("RECOMM_MEM_JOIN").toString()) );

			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_NOTICE", CommonUtil.checkShowAlarm(getAlarmInfo.get("Notice_Alarm_Yn").toString(), alamrCount.get("NOTICE").toString()) );

			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_ALARM", CommonUtil.checkShowAlarm(getAlarmInfo.get("Message_Alarm_Yn").toString(), alamrCount.get("ALARM").toString()) );


		}
		else {
			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_ANS_CHOICE", CommonUtil.checkShowAlarm("Y", alamrCount.get("ANS_CHOICE").toString()) );

			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_ANS_REGIST", CommonUtil.checkShowAlarm("Y", alamrCount.get("ANS_REGIST").toString()) );

			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_FAVORITE_QUE_REGIST", CommonUtil.checkShowAlarm("Y", alamrCount.get("FAVORITE_QUE_REGIST").toString()) );

			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_CMT_REGIST", CommonUtil.checkShowAlarm("Y", alamrCount.get("CMT_REGIST").toString()) );

			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_ANS_CHOICE_READY", CommonUtil.checkShowAlarm("Y", alamrCount.get("ANS_CHOICE_READY").toString()) );

			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_ALMONEY_INCOME", CommonUtil.checkShowAlarm("Y", alamrCount.get("ALMONEY_INCOME").toString()) );

			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_ALMONEY_PAYING", CommonUtil.checkShowAlarm("Y", alamrCount.get("ALMONEY_PAYING").toString()) );

			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_MEM_LEVEL_UP", CommonUtil.checkShowAlarm("Y", alamrCount.get("MEM_LEVEL_UP").toString()) );

			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_REPORT", CommonUtil.checkShowAlarm("Y", alamrCount.get("REPORT").toString()) );

			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_MENTEE", CommonUtil.checkShowAlarm("Y", alamrCount.get("MENTEE").toString()) );

			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_MENTEE_UNSET", CommonUtil.checkShowAlarm("Y", alamrCount.get("MENTEE_UNSET").toString()) );

			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_RECOMM_MEM_JOIN", CommonUtil.checkShowAlarm("Y", alamrCount.get("RECOMM_MEM_JOIN").toString()) );

			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_NOTICE", CommonUtil.checkShowAlarm("Y", alamrCount.get("NOTICE").toString()) );

			mav.addObject("CODE_MEM_ALARM_VIEW_FIELD_CD_ALARM", CommonUtil.checkShowAlarm("Y", alamrCount.get("ALARM").toString()) );

		}

		return mav;
	}

	@RequestMapping("bank/index")
	public ModelAndView bankIndex(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();


		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		if(userSeq == 0) {
			String msg1 = messageSource.getMessage("msg_1008", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			CommonUtil.jspAlert(response, msg1, "/", "parent.parent"); // msg1 활용
			return null;
		}

		if(request.getParameter("CallAlarmFlag") != null) {
			ZonedDateTime date_default_timezone_set = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
			Date today = new Date();
			String format = "yyyy-MM-dd HH:mm:ss";
			Calendar todays = Calendar.getInstance();
			SimpleDateFormat type = new SimpleDateFormat(format);
			String currentTime = type.format(todays.getTime());

			//System.out.println("currentTime " + currentTime);

			String CallAlarmFlag = request.getParameter("CallAlarmFlag");
			//출금/입금'의 알람 카운트 재계산

			/*
			exec SP2_MEMBER_ALARM_LOG_INSERT ?, ?, ?;
			를 두번 실행

			Data 1: UserSeq, 'ALMONEY_INCOME', currentTime
		 	Data 2: UserSeq, 'ALMONEY_PAYING', currentTime

			*/
			if(CallAlarmFlag.equals("Y")) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("userSeq", userSeq);
				param.put("alamCode", "ALMONEY_INCOME");
				param.put("dateReg", currentTime);
				memberService.setMemberAlarmLog(param);

				Map<String, Object> param2 = new HashMap<String, Object>();
				param2.put("userSeq", userSeq);
				param2.put("alamCode", "ALMONEY_PAYING");
				param2.put("dateReg", currentTime);

				memberService.setMemberAlarmLog(param2);
			}
		}

		mav.addObject("userLv", userLv);

		return mav;
	}

	@RequestMapping(value="bank/indexAjax", method = RequestMethod.GET, produces = "application/text; charset=utf8")
	public @ResponseBody String bankIndexAjax(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		ModelAndView mav = new ModelAndView();

		String act = request.getParameter("act");
		Map<String, String> ratioNameMapper = new HashMap<String, String>();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq == 0) {
			String msg = messageSource.getMessage("msg_1008", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			CommonUtil.jspAlert(response, msg, "/", "parent.parent"); // msg 활용
			return null;
		}

		if(act.equals("RATIO")) {

			JSONObject jsonTotal = new JSONObject();

			if(userSeq > 0) {
		    	List<HashMap<String, Object>> myAllInfo = memberService.myAlmoneyInfoAll(userSeq);

	    		HashMap<String, Object> total = new HashMap<String, Object>();
		    	HashMap<String, Object> totalData = new HashMap<String, Object>();
		    	HashMap<String, Object> data = new HashMap<String, Object>();
	    		HashMap<String, Object> trendData = new HashMap<String, Object>();

		    	for(int i = 0; i < myAllInfo.size(); i++) {

		    		if(i == 0) {
		    			JsonElement json = new Gson().toJsonTree(myAllInfo.get(i));

		    			JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
		    			JSONParser jsonParse = new JSONParser();
		    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

		    	    	totalData.put("total", ""+CommonUtil.convertToDecimal(jsonObj2.get("Almoney"))+"");
		    	    	//System.out.println("total : " + jsonObj2.get("Almoney"));
		    		}
		    		if(i == 1) {
		    			JsonElement json = new Gson().toJsonTree(myAllInfo.get(i));

		    			JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
		    			JSONParser jsonParse = new JSONParser();
		    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

		    	    	totalData.put("earn", ""+CommonUtil.convertToDecimal(jsonObj2.get("EarnTotal"))+"");
		    	    	totalData.put("con", ""+CommonUtil.convertToDecimal(jsonObj2.get("ConTotal"))+"");
		    	    	totalData.put("etc", ""+CommonUtil.convertToDecimal(jsonObj2.get("EtcTotal"))+"");
		    		}
		    		if(i == 2) {
		    			JsonElement json = new Gson().toJsonTree(myAllInfo.get(i));

		    			JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
		    			JSONParser jsonParse = new JSONParser();
		    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

		    	    	HashMap<String, Object> hObj = CommonUtil.convertJsonToHash(request, jsonObj2);
		    	    	//System.out.println("data earn : " + hObj);

		    	    	HashMap<String, Object> hObjFilter = CommonUtil.getFilterDecimalZero(hObj, request);

		    			data.put("earn", hObjFilter);
		    		}
		    		if(i == 3) {
		    			JsonElement json = new Gson().toJsonTree(myAllInfo.get(i));

		    			JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
		    			JSONParser jsonParse = new JSONParser();
		    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

		    	    	HashMap<String, Object> hObj = CommonUtil.convertJsonToHash(request, jsonObj2);
		    	    	//System.out.println("data con : " + hObj);

		    	    	HashMap<String, Object> hObjFilter = CommonUtil.getFilterDecimalZero(hObj, request);

		    	    	data.put("con", hObjFilter);
		    		}
		    		if(i == 4) {
		    			JsonElement json = new Gson().toJsonTree(myAllInfo.get(i));

		    			JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
		    			JSONParser jsonParse = new JSONParser();
		    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

		    	    	HashMap<String, Object> hObj = CommonUtil.convertJsonToHash(request, jsonObj2);
		    	    	//System.out.println("trand earm : " + hObj);
		    	    	trendData.put("earn", hObj);
		    		}
		    		if(i == 5) {
		    			JsonElement json = new Gson().toJsonTree(myAllInfo.get(i));

		    			JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
		    			JSONParser jsonParse = new JSONParser();
		    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

		    	    	HashMap<String, Object> hObj = CommonUtil.convertJsonToHash(request, jsonObj2);
		    	    	//System.out.println("trand con : " + hObj);
		    	    	trendData.put("con", hObj);
		    		}

		    	}

		    	//etc = etcList
		    	List<HashMap<String, Object>> myEtcInfo = memberService.getMemerEtcData(userSeq);

		    	//System.out.println("myEtcInfo : " + myEtcInfo);
		    	if(myEtcInfo == null) {
		    		ArrayList pitches = new ArrayList();
		    		data.put("etc", pitches);
		    	}
		    	else {
		    		List<HashMap<String, Object>> etc = new ArrayList<HashMap<String, Object>>();
		    		for(HashMap<String, Object> obj : myEtcInfo) {
		    			Iterator<String> iteratorK = obj.keySet().iterator();

		    			HashMap<String, Object> obj_in = new HashMap<String, Object>();
		    			while (iteratorK.hasNext()) {
		    				String key = iteratorK.next();
		    				Object val = obj.get(key);
		    				if(key.equals("regdate")) {
		    					val = ""+val+"";
		    				}
		    				obj_in.put(key, val);
		    			}
		    			etc.add(obj_in);
		    		}
		    		//System.out.println("etc : " + etc);

		    		data.put("etc", etc);
		    	}

		    	data.put("trend", trendData);

		    	total.put("total", totalData);
		    	total.put("data", data);


		    	ArrayList arr = new ArrayList();

		    	jsonTotal.put("result", total);
		    	jsonTotal.put("arr", arr);

		    	String jsonString = JSONValue.toJSONString(jsonTotal);
		    	//System.out.println("RATIO jsonString : " + jsonString);
				return jsonString;
			}
		}
		else if(act.equals("DETAIL_HISTORY")) {
			String targetSource = request.getParameter("target");
			//System.out.println("targetSource : " + targetSource);

			Map<String, String> earnCode = new HashMap<String, String>();
			Map<String, String> conCode = new HashMap<String, String>();
			Map<String, String> etcCode = new HashMap<String, String>();

			earnCode.put("3", "3");
			earnCode.put("5", "5");
			earnCode.put("10", "Answer");
			earnCode.put("11", "Answerer");
			//earnCode.put("11", "'Answerer', 'Answerer_Admin'");

			earnCode.put("13", "Esti");
			earnCode.put("14", "Event");
			earnCode.put("15", "Join");
			earnCode.put("16", "Refund");
			earnCode.put("17", "ViewA");
			earnCode.put("18", "ViewQ");
			earnCode.put("19", "ViewRA");
			earnCode.put("20", "ViewRQ");
			earnCode.put("91", "Etc");

			//2020-12-01 조인현 팀장 추가
			earnCode.put("46", "Quiz");
			earnCode.put("48", "Roulette");
			earnCode.put("50", "Translate");
			earnCode.put("52", "QuizCancel");

			//2021-05-06 조인현 팀장 추가
			earnCode.put("57", "Reply");


			conCode.put("1", "1");
			conCode.put("2", "2");
			conCode.put("4", "4");
			conCode.put("43", "Question");
			conCode.put("44", "View");

			//2020-12-01 조인현 팀장 추가
			conCode.put("45", "QuizMinus");
			conCode.put("49", "TranslateMinus");
			conCode.put("51", "EtcMinus");
			conCode.put("53", "Ext");

			//2021-05-06 조인현 팀장 추가
			conCode.put("58", "ReplyMinus");

			etcCode.put("41", "Exchange");
			//etcCode.put("41", "'Exchange', 'ExchangeAlpay'");
			etcCode.put("31", "31");




			Map<String, String> tradeTypeCode = new HashMap<String, String>();
			tradeTypeCode.putAll(earnCode);
			tradeTypeCode.putAll(conCode);
			tradeTypeCode.putAll(etcCode);

			//System.out.println("tradeTypeCode : " + tradeTypeCode);
			//System.out.println("tradeTypeCode.get(11)] : " + tradeTypeCode.get("11"));

			//CommonUtil 클래스의 getFilterDecimalZero 함수에서 선언한것과 동일한 코드를 활용해야 합니다.
			String msg1 = messageSource.getMessage("msg_0728", null, locale);
			String msg2 = messageSource.getMessage("msg_0729", null, locale);
			String msg3 = messageSource.getMessage("msg_0722", null, locale);
			String msg4 = messageSource.getMessage("msg_0725", null, locale);
			String msg5 = messageSource.getMessage("msg_0730", null, locale);
			String msg6 = messageSource.getMessage("msg_0731", null, locale);
			String msg7 = messageSource.getMessage("msg_0732", null, locale);
			String msg8 = messageSource.getMessage("msg_0723", null, locale);
			String msg9 = messageSource.getMessage("msg_0724", null, locale);
			String msg10 = messageSource.getMessage("msg_0727", null, locale);
			String msg11 = messageSource.getMessage("msg_0726", null, locale);
			String msg12 = messageSource.getMessage("msg_0245", null, locale);
			String msg13 = messageSource.getMessage("msg_0728", null, locale);
			String msg14 = messageSource.getMessage("msg_0729", null, locale);
			String msg15 = messageSource.getMessage("msg_0722", null, locale);
			String msg16 = messageSource.getMessage("msg_1004", null, locale);
			String msg17 = messageSource.getMessage("msg_0733", null, locale);
			String msg18 = messageSource.getMessage("msg_0720", null, locale);
			String msg19 = messageSource.getMessage("msg_0720", null, locale);
			String msg20 = messageSource.getMessage("msg_1070", null, locale);

			Map<String, String> tradeTypeMapperOrg = new HashMap<String, String>();
			tradeTypeMapperOrg.put("3",  msg1); // msg1 활용
			tradeTypeMapperOrg.put("5",  msg2); // msg2 활용
			tradeTypeMapperOrg.put("Answer",  msg3); // msg3 활용
			tradeTypeMapperOrg.put("Esti",  msg4); // msg4 활용
			tradeTypeMapperOrg.put("Event",  msg5); // msg5 활용
			tradeTypeMapperOrg.put("Join",  msg6); // msg6 활용
			tradeTypeMapperOrg.put("Refund",  msg7); // msg7 활용
			tradeTypeMapperOrg.put("ViewA",  msg8); // msg8 활용
			tradeTypeMapperOrg.put("ViewQ",  msg9); // msg9 활용
			tradeTypeMapperOrg.put("ViewRA",  msg10); // msg10 활용
			tradeTypeMapperOrg.put("ViewRQ",  msg11); // msg11 활용
			tradeTypeMapperOrg.put("Answerer",  "ANSWERer");
			tradeTypeMapperOrg.put("Answerer_Admin",  "ANSWERer");
			tradeTypeMapperOrg.put("1",  msg12); // msg12 활용
			tradeTypeMapperOrg.put("2",  msg13); // msg13 활용
			tradeTypeMapperOrg.put("4",  msg14); // msg14 활용
			tradeTypeMapperOrg.put("Question",  msg15); // msg15 활용
			tradeTypeMapperOrg.put("View",  msg16); // msg16 활용
			tradeTypeMapperOrg.put("Etc",  msg17); // msg17 활용
			tradeTypeMapperOrg.put("Exchange",  msg18); // msg18 활용
			tradeTypeMapperOrg.put("ExchangeAlpay",  msg19); // msg19 활용
			tradeTypeMapperOrg.put("31",  msg20); // msg20 활용

			String msg21 = messageSource.getMessage("msg_0756", null, locale);
			String msg22 = messageSource.getMessage("msg_0757", null, locale);
			String msg23 = messageSource.getMessage("msg_1071", null, locale);
			String msg24 = messageSource.getMessage("msg_0735", null, locale);
			String msg25 = messageSource.getMessage("msg_0753", null, locale);
			String msg26 = messageSource.getMessage("msg_0754", null, locale);
			String msg27 = messageSource.getMessage("msg_0755", null, locale);
			String msg28 = messageSource.getMessage("msg_1184", null, locale); // 훈훈알(댓글)

			tradeTypeMapperOrg.put("QuizMinus",  msg21); // msg21 활용
			tradeTypeMapperOrg.put("TranslateMinus",  msg22); // msg22 활용
			tradeTypeMapperOrg.put("EtcMinus",  msg23); // msg23 활용
			tradeTypeMapperOrg.put("Ext",  msg17); // msg17 활용
			tradeTypeMapperOrg.put("Quiz",  msg24); // msg24 활용
			tradeTypeMapperOrg.put("QuizCancel",  msg25); // msg25 활용
			tradeTypeMapperOrg.put("Roulette",  msg26); // msg26 활용
			tradeTypeMapperOrg.put("Translate",  msg27); // msg27 활용

			tradeTypeMapperOrg.put("Reply",  msg28); // 수정중
			tradeTypeMapperOrg.put("ReplyMinus",  msg28); // 수정중

			/* 이후 진행 예정 258 줄 이후
			// MS-SQL LOG_ALMONEY 테이블에 TradeType 값이 대소문자 섞여있는 경우가 많기에
		    // 일괄적으로 소문자로 변경하여 비교한다.
		    foreach ($tradeTypeMapper as $key => $value) {
		        $tradeTypeMapper[strtolower($key)] = $value;
		    }
			*/
			Map<String, String> tradeTypeMapper = new HashMap<String, String>();
			Iterator<Map.Entry<String, String>> iteratorE = tradeTypeMapperOrg.entrySet().iterator();
			while (iteratorE.hasNext()) {
				Map.Entry<String, String> entry = iteratorE.next();
				String key = entry.getKey();
				String value = entry.getValue();
				//System.out.println(key + "  :  " + value);
				tradeTypeMapper.put(key.toLowerCase(),  value);
			}

		    /*
		     * MS-SQL 에서 in 연산자 사용을 위한 함수
		     * 클라이언트에서 json 형태로 조회를 원하는 거래유형을 전달하면
		     * 해당 거래유형들을 in 연산자에서 사용할 list 형태로 변환하는 function
		     */


			//targetSource = "[3,5]";

			String[] targetList = null;
			if(targetSource.equals("[]")) {
				Iterator<Map.Entry<String, String>> iterTarget = tradeTypeMapperOrg.entrySet().iterator();

				targetList = new String[tradeTypeMapperOrg.size()];
				int i = 0;
				while (iterTarget.hasNext()) {
					Map.Entry<String, String> entry = iterTarget.next();
					String key = entry.getKey();
					//String value = entry.getValue();

					targetList[i] = key;
					i++;
				}
			}
			else {
				String targetString = targetSource.replace("[", "").replace("]", "").replace("\"", "");
				String[] targetArr = targetString.split(",");

				targetList = new String[targetArr.length];
				for(int i = 0; i < targetArr.length; i++) {
					targetList[i] = tradeTypeCode.get(targetArr[i]);
				}
			}
			//System.out.println("targetList : " + targetList[0]);
			/*
			for (String elI : targetList) {
				System.out.print(elI+ ", ");
			}
			*/

			String pg = request.getParameter("pg");
			String rows = request.getParameter("rows");
			String sort = request.getParameter("sort");

			int page = pg == "" ? 1 : Integer.parseInt(pg);
			int maxRow = rows == "" ? 30 : Integer.parseInt(rows);

			String sortType = "asc";
			if(sort != "") { sortType = sort; }
			//System.out.println("dateBefore : " + request.getParameter("before"));
			String dateBefore = request.getParameter("before") == "" ? "0" : request.getParameter("before");
			//System.out.println("dateBefore : " +dateBefore);

			//$dateTarget = date('Y-m-d', time() - (86400 * $dateBefore));

			Calendar dt = Calendar.getInstance();
			dt.add(Calendar.DATE , -(Integer.parseInt(dateBefore) ) );
			String dateTarget = new SimpleDateFormat("yyyy-MM-dd").format(dt.getTime());

			//System.out.println("dateBefore : " + dateTarget);

			//$tradeTypeList = generateTradeTypeFilter($targetList);
			String tradeTypeList = CommonUtil.arrayToString(targetList);

			//$offset = ($page - 1) * $maxRow;
			int offset = (page - 1) * maxRow;


			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userSeq", userSeq);
			param.put("offset", offset);
			param.put("maxRow", maxRow);
			param.put("dateTarget", dateTarget);
			param.put("sortType", sortType);
			param.put("tradeTypeList", tradeTypeList);

			List<HashMap<String, Object>> myBankList = memberService.getMyBankList(param);

			int totalPage = 0;
			List<Map<String, Object>> iter = new ArrayList<Map<String, Object>>();

			for(int i = 0; i < myBankList.size(); i++) {
				//System.out.println("myBankList() : " + myBankList.get(i));
				if(i == 0) {
					JsonElement json = new Gson().toJsonTree(myBankList.get(i));
					//System.out.println("json : " + json.getAsJsonArray().size());

					for(int j = 0; j < json.getAsJsonArray().size(); j++) {
						JsonObject jsonObj = json.getAsJsonArray().get(j).getAsJsonObject();
		    			JSONParser jsonParse = new JSONParser();
		    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

		    	    	//System.out.println("jsonObj2 : " + jsonObj2);

		    	    	HashMap<String, Object> convData = CommonUtil.convertHistoryData(jsonObj2, earnCode, conCode, tradeTypeMapper);
		    	    	//System.out.println("convData : " + convData);

		    	    	iter.add(convData);
					}

				}
				if(i == 1) {
					JsonElement json = new Gson().toJsonTree(myBankList.get(i));
					JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
	    			JSONParser jsonParse = new JSONParser();
	    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

					//$totalPage = ceil((int)$r[1][0]['Total'] / $maxRow);
					int total = Integer.parseInt(jsonObj2.get("Total").toString());
					totalPage = Math.round(total / maxRow); // Math.ceil 은 double 형으로 반환
					//System.out.println("total : " + total);
					//System.out.println("totalPage : " + totalPage);
				}
			}

			HashMap<String, Object> paging = new HashMap<String, Object>();
			paging.put("total", totalPage);
			paging.put("cur", page);

			JSONObject jsonTotal = new JSONObject();
			jsonTotal.put("result", iter);
			jsonTotal.put("arr", paging);


			String jsonString = JSONValue.toJSONString(jsonTotal);
			//System.out.println("DETAIL_HISTORY jsonString : " + jsonString);

			return jsonString;
		}
		else if(act.equals("GRAPH")) {
			// chart.js 에서 호출 됨
			// JSON 결과 예시 : {0: 27, 1: 396, 2: 235.5, 3: 183.3, 4: 193, 5: 118.3, 6: 469}
			String iter = request.getParameter("iter");
			//iter = "2020-08-01"; // DETAIL_HISTORY 에서 넘어온 자료
			//System.out.println("iter : " + iter);


			Calendar week = Calendar.getInstance();
			week.add(Calendar.DATE , -49);
			String dtString = new SimpleDateFormat("yyyy-MM-dd").format(week.getTime());

			//System.out.println("  week :" +  week );
			//System.out.println("  dtString :" +  dtString );
			//dtString = "2020-08-12";


			//floor($currentTime->diff($iter)->days / 7);

			Date time = new Date();
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			String today = format1.format(time);

			/*
			String textDate = "2020-08-17";
			System.out.println("  today :" +  today );
			System.out.println("  textDate :" +  textDate );
			float diffDay = CommonUtil.GetDifferenceOfDate(today, textDate );
			System.out.println("  diffDay :" +  diffDay );
			*/


			//getGraphData
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userSeq", userSeq);
			param.put("dtString", dtString);

			List<Map<String, Object>> graphData = memberService.getGraphData(param);

			//System.out.println("graphData : " + graphData);




			List<Object> weekList = new ArrayList<Object>();
			List<Object> myGraphEarningData = new ArrayList<Object>();
			List<Object> myGraphConsumeData = new ArrayList<Object>();


			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
			Calendar cal0 = Calendar.getInstance() ;
			Date nDate0 = dateFormat.parse("2020-08-12");
			cal0.setTime(nDate0);
			int dayNum0 = cal0.get(Calendar.DAY_OF_WEEK);
			//System.out.println("2020-08-12 의 요일 : " + dayNum0);

			for(Map<String, Object> map: graphData) {

				Iterator<Map.Entry<String, Object>> iteratorE = map.entrySet().iterator();
				Calendar cal = Calendar.getInstance() ;

				while (iteratorE.hasNext()) {
					Map.Entry<String, Object> entry = iteratorE.next();
					String key = entry.getKey();
					Object value = entry.getValue();

					if(key.equals("regdate")) {
						//Date nDate = dateFormat.parse(value.toString());
						//cal.setTime(nDate);
						//int dayNum = cal.get(Calendar.DAY_OF_WEEK);

						//floor($currentTime->diff($iter)->days / 7);
						int diffDay = Math.round( CommonUtil.GetDifferenceOfDate(today, value.toString() ) / 7 );
						//System.out.println("  diffDay :" +  diffDay );

						weekList.add(diffDay);
					}
					if(key.equals("Earning")) {
						myGraphEarningData.add(Float.parseFloat( String.valueOf(value) ));
					}
					if(key.equals("Consume")) {
						myGraphConsumeData.add(Float.parseFloat( String.valueOf(value) ));
					}
				}
			}

			Map<Object, Object> myGraphEarningDatList = new HashMap<Object, Object>();
			for(int i = 0; i < weekList.size(); i++) {
				myGraphEarningDatList.put(weekList.get(i), myGraphEarningData.get(i));
			}

			Map<Object, Object> myGraphConsumeDataList = new HashMap<Object, Object>();
			for(int i = 0; i < weekList.size(); i++) {
				myGraphConsumeDataList.put(weekList.get(i), myGraphConsumeData.get(i));
			}



	    	HashMap<Object, Object> totalInfo = new HashMap<Object, Object>();
	    	totalInfo.put("earning", myGraphEarningDatList);
	    	totalInfo.put("consumption", myGraphConsumeDataList);


	    	ArrayList arr = new ArrayList();


	    	HashMap<String, Object> total = new HashMap<String, Object>();
	    	total.put("result", totalInfo);
	    	total.put("arr", arr);


	    	String jsonString = JSONValue.toJSONString(total);
	    	//System.out.println("jsonString : " + jsonString);

			return jsonString;
		}

		return null;
	}

	@RequestMapping("member/partnerSave")
	public void partnerSave(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		int partnerSeq = 0;

		String msg1 = messageSource.getMessage("msg_1072", null, locale);
		String msg2 = messageSource.getMessage("msg_1073", null, locale);
		String msg3 = messageSource.getMessage("msg_0168", null, locale);
		String msg4 = messageSource.getMessage("msg_1074", null, locale);
		String msg5 = messageSource.getMessage("msg_1075", null, locale);
		String msg6 = messageSource.getMessage("msg_1076", null, locale);
		String msg7 = messageSource.getMessage("msg_0318", null, locale);
		String msg8 = messageSource.getMessage("msg_0319", null, locale);

		String flagPartner = request.getParameter("FlagPartner");
		if(request.getParameter("PartnerSeq") != null) {
			partnerSeq = Integer.parseInt(request.getParameter("PartnerSeq"));
		}
		else {
			CommonUtil.jspAlert(response, msg1, "", ""); // msg1 활용
			return;
		}

		if(request.getParameter("FlagPartner") == null || request.getParameter("FlagPartner") == "") {
			CommonUtil.jspAlert(response, msg2, "", ""); // msg2 활용
			return;
		}

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq == 0) {
			CommonUtil.jspAlert(response, msg3, "", ""); // msg3 활용
			return;
		}
		if(userSeq ==  partnerSeq) {
			CommonUtil.jspAlert(response, msg4, "", ""); // msg4 활용
			return;
		}

		String format = "yyyy-MM-dd aa hh:mm:ss";
		Calendar todays = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String todayDate = type.format(todays.getTime());


		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("userSeq", userSeq);
		param.put("partnerSeq", partnerSeq);
		param.put("flagPartner", flagPartner);
		param.put("dateReg", todayDate);

		int returnCode = memberService.setPartnerSave(param);

		if(returnCode == 0) {
			CommonUtil.jspAlert(response, msg5, "", ""); // msg5 활용
		}
		else if(returnCode == 1) {
			CommonUtil.jspAlert(response, msg6, "", ""); // msg6 활용
		}
		else {
			CommonUtil.jspAlert(response, msg7 + "\\n" + msg8, "", ""); // msg7, msg8 활용
		}
	}

	@RequestMapping(value="/member/otherProfileInfo", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView otherProfileInfo(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		String msg1 = messageSource.getMessage("msg_1019", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1082", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_1083", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		String strMemberSeq ="";
		//System.out.println("aa : "+request.getParameter("MemberSeq"));
		if(request.getParameter("MemberSeq") == null || request.getParameter("MemberSeq").equals("")) {

			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용

			return null;
		}
		else {
			strMemberSeq = request.getParameter("MemberSeq");
		}

		HashMap<String, Object> mem = memberService.getMemberLvOrFlagBySeq(Integer.parseInt(strMemberSeq));
		String lv = String.valueOf(mem.get("Lv"));
		String flagProfileOpen = String.valueOf(mem.get("FlagProfileOpen"));
		mav.addObject("mlv", lv);

		if(Integer.parseInt(lv) > 90) {
			CommonUtil.jspAlert(response, msg2, "back", ""); // msg2 활용
			return null;
		}

		if(flagProfileOpen.equals("N")) {
			CommonUtil.jspAlert(response, msg3, "back", ""); // msg3 활용
			return null;
		}

		MemberVO memInfo = memberService.getMemberInfoViewBySeq(Integer.parseInt(strMemberSeq));

		int ReplyCount = memInfo.getQReplyCount() + memInfo.getAReplyCount();

		String photoURL = "";
		if( memInfo.getPhoto() == null || memInfo.getPhoto().equals("")) {
			photoURL = "/Common/images/img_thum_base099.jpg";
		}
		else {
			photoURL = "/UploadFile/Profile/"+memInfo.getPhoto();
		}

		int tagCountC = memInfo.getCountC();
		int tagCountA = memInfo.getCountChoicedA();

		DecimalFormat form = new DecimalFormat("#.#");


		String tagRateChoice = "0";
		String tagRateChoicedA = "0";
		int num_getCountQ = memInfo.getCountQ();
		int num_getCountA = memInfo.getCountA();
		if(num_getCountQ != 0) {
			tagRateChoice = form.format( (tagCountC / memInfo.getCountQ()) * 100 );
		}

		if(num_getCountA != 0) {
			tagRateChoicedA = form.format( (tagCountA / memInfo.getCountA()) * 100 );
		}

		mav.addObject("userSeq", userSeq);
		mav.addObject("memSeq", strMemberSeq);
		mav.addObject("memInfo", memInfo);
		mav.addObject("replyCount", ReplyCount);
		mav.addObject("photoURL", photoURL);
		mav.addObject("tagCountC", tagCountC);
		mav.addObject("tagCountA", tagCountA);
		mav.addObject("tagRateChoice", tagRateChoice);
		mav.addObject("tagRateChoicedA", tagRateChoicedA);

		return mav;
	}

	@RequestMapping(value="/member/otherQuestion", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView otherQuestion(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		String msg1 = messageSource.getMessage("msg_1019", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		ModelAndView mav = new ModelAndView();
		String strMemberSeq = request.getParameter("MemberSeq");
		int userSeq = 0;
		if(request.getParameter("MemberSeq") == null || request.getParameter("MemberSeq") == "") {

			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용

			return mav;
		}
		else {
			userSeq = Integer.parseInt(strMemberSeq);
		}

		String curPageName = "/member/otherQuestion";
		String req_TREC = "";
		String req_PG = "";
		String src_Sort = "";
		String src_OrderBy = "";
		String section1 = "";

		if(request.getParameter("trec") != null) {
			req_TREC = CommonUtil.fn_Word_In(request.getParameter("trec"));
		}
		if(request.getParameter("pg") != null) {
			req_PG = CommonUtil.fn_Word_In(request.getParameter("pg"));
		}
		if(request.getParameter("src_Sort") != null) {
			src_Sort = CommonUtil.fn_Word_In(request.getParameter("src_Sort"));
		}
		if(request.getParameter("src_OrderBy") != null) {
			src_OrderBy = CommonUtil.fn_Word_In(request.getParameter("src_OrderBy"));
		}
		if(request.getParameter("Section1") != null) {
			section1 = CommonUtil.fn_Word_In(request.getParameter("Section1"));
		}

		if(section1 == "") {
			section1 = "0";
		}

		if(req_PG == "") {
			req_PG = "1";
		}

		if(src_Sort == "") {
			src_Sort = "Seq";
		}

		if(src_OrderBy == "") {
			src_OrderBy = "DESC";
		}

		String s_tmp = "";
		int n_trec = 0;
		s_tmp = req_TREC;

		if(CommonUtil.isNumeric(s_tmp) && s_tmp.length() > 0) {
			n_trec = Integer.parseInt(s_tmp);
		}

		HashMap<String, Object> memParam = new HashMap<String, Object>();
		memParam.put("strData3", userSeq);
		memParam.put("strData4", "Y");
		memParam.put("strData5", "");
		memParam.put("section1", section1);

		int queCnt = questionService.getMyQuestionListCount(memParam);

		n_trec = queCnt;


		int n_pagesize = 30;
		int n_pagescnt = 10;
		int n_curpage = 1;
		int n_totalpage = 0;

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


		List<QuestionVO> myQueList = null;

		if(queCnt > 0) {
			HashMap<String, Object> memParam2 = new HashMap<String, Object>();
			memParam2.put("strData1", (n_pagesize * n_curpage));
			memParam2.put("strData2", src_Sort);
			memParam2.put("strData3", userSeq);
			memParam2.put("strData4", "Y");
			memParam2.put("strData5", "");
			memParam2.put("section1", section1);

			myQueList =  questionService.getMyQuestionListLimit(memParam2);
		}
		//System.out.println("myQueList.size() : " + myQueList.size());
		List<SectionVO> sectionVO = commonService.getBoardSection1();

		String url = curPageName + "?trec=" + req_TREC + "&src_Sort=" + src_Sort + "&src_OrderBy=" + src_OrderBy + "&Section1=" + section1;
		String paging = CommonUtil.get_Paging_Tag_New_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);

		mav.addObject("memberSeq", userSeq);
		mav.addObject("n_trec", n_trec);
		mav.addObject("curPageName", curPageName);
		mav.addObject("n_curpage", n_curpage);
		mav.addObject("myQueList", myQueList);
		mav.addObject("section1", section1);
		mav.addObject("sectionVO", sectionVO);
		mav.addObject("paging", paging);

		return mav;
	}

	@RequestMapping(value="/member/otherAnswer", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView otherAnswer(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		String msg1 = messageSource.getMessage("msg_1019", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		ModelAndView mav = new ModelAndView();
		String strMemberSeq = request.getParameter("MemberSeq");
		int userSeq = 0;
		if(request.getParameter("MemberSeq") == null || request.getParameter("MemberSeq") == "") {

			CommonUtil.jspAlert(response, msg1, "back", "");// msg1 활용

			return mav;
		}
		else {
			userSeq = Integer.parseInt(strMemberSeq);
		}

		String curPageName = "/member/otherAnswer";
		String req_TREC = "";
		String req_PG = "";
		String src_Sort = "";
		String src_OrderBy = "";
		String flagChoice = "";

		if(request.getParameter("trec") != null) {
			req_TREC = CommonUtil.fn_Word_In(request.getParameter("trec"));
		}
		if(request.getParameter("pg") != null) {
			req_PG = CommonUtil.fn_Word_In(request.getParameter("pg"));
		}
		if(request.getParameter("src_Sort") != null) {
			src_Sort = CommonUtil.fn_Word_In(request.getParameter("src_Sort"));
		}
		if(request.getParameter("src_OrderBy") != null) {
			src_OrderBy = CommonUtil.fn_Word_In(request.getParameter("src_OrderBy"));
		}
		if(request.getParameter("FlagChoice") != null) {
			flagChoice = CommonUtil.fn_Word_In(request.getParameter("FlagChoice"));
		}

		if(req_PG == "") {
			req_PG = "1";
		}

		if(src_Sort == "") {
			src_Sort = "Seq";
		}

		if(src_OrderBy == "") {
			src_OrderBy = "DESC";
		}

		String s_tmp = "";
		int n_trec = 0;
		s_tmp = req_TREC;

		if(CommonUtil.isNumeric(s_tmp) && s_tmp.length() > 0) {
			n_trec = Integer.parseInt(s_tmp);
		}

		HashMap<String, Object> memParam = new HashMap<String, Object>();
		memParam.put("strData3", userSeq);
		memParam.put("strData4", "Y");
		memParam.put("FlagChoice", flagChoice);

		int queCnt = answerService.getMyAnswerListCount(memParam);

		n_trec = queCnt;


		int n_pagesize = 30;
		int n_pagescnt = 10;
		int n_curpage = 1;
		int n_totalpage = 0;

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


		List<AnswerVO> myAnsList = null;

		if(queCnt > 0) {
			HashMap<String, Object> memParam2 = new HashMap<String, Object>();
			memParam2.put("strData1", (n_pagesize * n_curpage));
			memParam2.put("strData3", userSeq);
			memParam2.put("strData4", "Y");
			memParam2.put("FlagChoice", flagChoice);

			myAnsList =  answerService.getMyAnswerListLimit(memParam2);
		}

		String url = curPageName + "?trec=" + req_TREC + "&src_Sort=" + src_Sort + "&src_OrderBy=" + src_OrderBy + "&FlagChoice=" + flagChoice;
		String paging = CommonUtil.get_Paging_Tag_New_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);

		mav.addObject("memberSeq", userSeq);
		mav.addObject("n_trec", n_trec);
		mav.addObject("curPageName", curPageName);
		mav.addObject("n_curpage", n_curpage);
		mav.addObject("myAnsList", myAnsList);
		mav.addObject("flagChoice", flagChoice);
		mav.addObject("paging", paging);

		return mav;
	}

	@RequestMapping(value="/member/otherAdminReply", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView otherAdminReply(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();
		String strMemberSeq = request.getParameter("MemberSeq");
		final int userLv = cookieLoginService.getCookieUserLv(request, response);
		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		String msg1 = messageSource.getMessage("msg_1008", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1084", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		if(userSeq != 0) {
			if(userLv != 99) {
				CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용
				return null;
			}
		}


		if(request.getParameter("MemberSeq") == null || request.getParameter("MemberSeq") == "") {

			CommonUtil.jspAlert(response, msg2, "back", ""); // msg2 활용

			return mav;
		}
		else {
		}


		//회원 닉네임 조회
		MemberVO mem = memberService.getAlmoneyMemJoin(userSeq);

		String nickName = mem.getNickName();


		String curPageName = "/member/otherAdminReply";
		String req_TREC = "";
		String req_PG = "";
		String src_Sort = "";
		String src_OrderBy = "";
		String qa_Cls = "";
		String flagMe = "";

		if(request.getParameter("trec") != null) {
			req_TREC = CommonUtil.fn_Word_In(request.getParameter("trec"));
		}
		if(request.getParameter("pg") != null) {
			req_PG = CommonUtil.fn_Word_In(request.getParameter("pg"));
		}
		if(request.getParameter("src_Sort") != null) {
			src_Sort = CommonUtil.fn_Word_In(request.getParameter("src_Sort"));
		}
		if(request.getParameter("src_OrderBy") != null) {
			src_OrderBy = CommonUtil.fn_Word_In(request.getParameter("src_OrderBy"));
		}
		if(request.getParameter("QA_Cls") != null) {
			qa_Cls = CommonUtil.fn_Word_In(request.getParameter("QA_Cls"));
		}
		if(request.getParameter("FlagMe") != null) {
			flagMe = CommonUtil.fn_Word_In(request.getParameter("FlagMe"));
		}

		if(req_PG == "") {
			req_PG = "1";
		}

		if(src_Sort == "") {
			src_Sort = "Seq";
		}

		if(src_OrderBy == "") {
			src_OrderBy = "DESC";
		}

		if(qa_Cls == "") {
			qa_Cls = "Q";
		}

		String s_tmp = "";
		int n_trec = 0;
		s_tmp = req_TREC;

		if(CommonUtil.isNumeric(s_tmp) && s_tmp.length() > 0) {
			n_trec = Integer.parseInt(s_tmp);
		}


		int replCnt = 0;

		if(flagMe.equals("")) {
			replCnt = replyService.getOtherReplyListCount(userSeq);
		}
		else {
			replCnt = replyService.getReceivedReplyCount(userSeq);
		}


		n_trec = replCnt;


		int n_pagesize = 30;
		int n_pagescnt = 10;
		int n_curpage = 1;
		int n_totalpage = 0;

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


		List<ReplyVO> myReplyList = null;

		if(replCnt > 0) {
			HashMap<String, Object> memParam2 = new HashMap<String, Object>();
			memParam2.put("p_Option", (n_pagesize * n_curpage));
			memParam2.put("userSeq", userSeq);
			memParam2.put("psrc_Cond", src_Sort);

			if(flagMe.equals("")) {
				myReplyList =  replyService.getOtherReplyListLimit(memParam2);
			}
			else {
				myReplyList =  replyService.getReceivedReplyLimit(memParam2);
			}
		}

		List<SectionVO> sectionVO = commonService.getBoardSection1();

		String url = curPageName + "?trec=" + req_TREC + "&src_Sort=" + src_Sort + "&src_OrderBy=" + src_OrderBy + "&QA_Cls=" + qa_Cls + "&FlagMe=" + flagMe + "&MemberSeq=" + strMemberSeq;
		String paging = CommonUtil.get_Paging_Tag_New_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);

		mav.addObject("memberSeq", userSeq);
		mav.addObject("nickName", nickName);
		mav.addObject("n_trec", n_trec);
		mav.addObject("curPageName", curPageName);
		mav.addObject("n_curpage", n_curpage);
		mav.addObject("myReplyList", myReplyList);
		mav.addObject("flagMe", flagMe);
		mav.addObject("qa_Cls", qa_Cls);
		mav.addObject("sectionVO", sectionVO);
		mav.addObject("paging", paging);

		return mav;
	}

	@RequestMapping(value="/member/otherAdminView", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView otherAdminView(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();
		String strMemberSeq = request.getParameter("MemberSeq");
		int userSeq = 0;
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		String msg1 = messageSource.getMessage("msg_1008", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1084", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		HttpSession session = request.getSession(true);

		JSONObject global = null;

		if(userLv > 0) {
			userSeq = cookieLoginService.getCookieUserSeq(request, response);

			if(userLv != 99) {
				CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용
				return null;
			}
		}


		if(request.getParameter("MemberSeq") == null || request.getParameter("MemberSeq") == "") {

			CommonUtil.jspAlert(response, msg2, "back", ""); // msg2 활용

			return mav;
		}
		else {
			userSeq = Integer.parseInt(strMemberSeq);
		}


		//회원 닉네임 조회
		MemberVO mem = memberService.getAlmoneyMemJoin(userSeq);

		String nickName = mem.getNickName();


		String curPageName = "/member/otherAdminView";
		String req_TREC = "";
		String req_PG = "";
		String src_Sort = "";
		String src_OrderBy = "";
		String section1 = "";

		if(request.getParameter("trec") != null) {
			req_TREC = CommonUtil.fn_Word_In(request.getParameter("trec"));
		}
		if(request.getParameter("pg") != null) {
			req_PG = CommonUtil.fn_Word_In(request.getParameter("pg"));
		}
		if(request.getParameter("src_Sort") != null) {
			src_Sort = CommonUtil.fn_Word_In(request.getParameter("src_Sort"));
		}
		if(request.getParameter("src_OrderBy") != null) {
			src_OrderBy = CommonUtil.fn_Word_In(request.getParameter("src_OrderBy"));
		}
		if(request.getParameter("Section1") != null) {
			section1 = CommonUtil.fn_Word_In(request.getParameter("section1"));
		}

		if(req_PG == "") {
			req_PG = "1";
		}

		if(src_Sort == "") {
			src_Sort = "Seq";
		}

		if(src_OrderBy == "") {
			src_OrderBy = "DESC";
		}

		if(section1 == "") {
			section1 = "0";
		}

		String s_tmp = "";
		int n_trec = 0;
		s_tmp = req_TREC;

		if(CommonUtil.isNumeric(s_tmp) && s_tmp.length() > 0) {
			n_trec = Integer.parseInt(s_tmp);
		}


		int viewCnt = 0;

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("userSeq", userSeq);
		param.put("section1", section1);

		viewCnt = commonService.getOtherViewCount(param);


		n_trec = viewCnt;


		int n_pagesize = 30;
		int n_pagescnt = 10;
		int n_curpage = 1;
		int n_totalpage = 0;

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


		List<LogAlmoneyVO> myViewList = null;

		if(viewCnt > 0) {
			HashMap<String, Object> memParam2 = new HashMap<String, Object>();
			memParam2.put("p_Option", (n_pagesize * n_curpage));
			memParam2.put("userSeq", userSeq);
			memParam2.put("section1", section1);

			myViewList =  commonService.getOtherViewList(memParam2);
		}

		List<SectionVO> sectionVO = commonService.getBoardSection1();

		String url = curPageName + "?trec=" + req_TREC + "&src_Sort=" + src_Sort + "&src_OrderBy=" + src_OrderBy + "&Section1=" + section1 + "&MemberSeq=" + strMemberSeq;
		String paging = CommonUtil.get_Paging_Tag_New_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);

		mav.addObject("memberSeq", userSeq);
		mav.addObject("nickName", nickName);
		mav.addObject("n_trec", n_trec);
		mav.addObject("curPageName", curPageName);
		mav.addObject("n_curpage", n_curpage);
		mav.addObject("myViewList", myViewList);
		mav.addObject("section1", section1);
		mav.addObject("sectionVO", sectionVO);
		mav.addObject("paging", paging);

		return mav;
	}

	@RequestMapping(value="/interest/getInterest", produces="application/json;charset=UTF-8", method = RequestMethod.GET)
	public @ResponseBody String getInterest(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=UTF-8");
		String section = "";

		List<SectionVO> list = new ArrayList<SectionVO>();

		if(request.getParameter("Code4") != null) {
			section = request.getParameter("Code4");
			list = commonService.getMyInterestSection4Get(section);
		}
		else if(request.getParameter("Code3") != null) {
			section = request.getParameter("Code3");
			list = commonService.getMyInterestSection3Get(section);
		}
		else if(request.getParameter("Code2") != null) {
			section = request.getParameter("Code2");
			list = commonService.getMyInterestSection2Get(section);
		}
		else if(request.getParameter("Code1") != null) {
			section = request.getParameter("Code1");
			list = commonService.getMyInterestSection1Get(section);
		}
		else {
			list = commonService.getMyInterestExtGet();
		}

		return new Gson().toJson(list);
	}


	@RequestMapping("/member/myRecivedAnswer")
	public ModelAndView myRecivedAnswer(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();
		String viewFlag = request.getParameter("Flag");
		String curPageName = "/member/myRecivedAnswer";

		CodeUtil code = new CodeUtil(request);

		String format = "yyyy-MM-dd HH:mm:ss";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String dateReg = type.format(today.getTime());

		int n_pagesize = 10;
		int n_pagescnt = 5;
		int n_curpage = 1;
		int n_totalpage = 0;
		int n_totalCnt = 0;

		List<AnswerVO> answerAlarmList = new ArrayList<AnswerVO>();

		String pg = request.getParameter("pg");
		if(pg == null) {
			pg = "1";
		}

		if(viewFlag == null) {
			viewFlag = "";
		}

		n_curpage = Integer.parseInt( CommonUtil.fn_Word_In( String.valueOf(pg) ) );

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);


		if(userSeq > 0) {
			if(request.getParameter("CallAlarmFlag")!=null &&request.getParameter("CallAlarmFlag").equals("Y")) {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("userSeq", userSeq);
				param.put("alamCode", code.getCODE_MEM_ALARM_VIEW_FIELD_CD_ANS_REGIST());
				param.put("dateReg", dateReg);
				memberService.setMemberAlarmLog(param);
			}

			HashMap<String, Object> ansParam = new HashMap<String, Object>();
			ansParam.put("userSeq", userSeq);
			ansParam.put("n_curpage", n_curpage);
			ansParam.put("n_pagesize", n_pagesize);
			ansParam.put("viewFlag", viewFlag);

			n_totalCnt = answerService.getMemberReceivedAnswerCount(ansParam);
			n_totalpage = (int) Math.ceil( (n_totalCnt / n_pagesize) );

			if(n_totalpage == 0) { n_totalpage = 1; }

			answerAlarmList = answerService.getMemberReceivedAnswerList(ansParam);
		}

		String url = curPageName + "?Flag=" + viewFlag;
		String paging = CommonUtil.get_Paging_Tag_New_ver2(n_pagescnt, Integer.parseInt(pg), n_totalpage, "frm_sch", url);

		mav.addObject("CurPageName", curPageName);
		mav.addObject("viewFlag", viewFlag);
		mav.addObject("n_totalCnt", n_totalCnt);
		mav.addObject("answerAlarmList",answerAlarmList);
		mav.addObject("paging",paging);

		return mav;
	}

	@RequestMapping("/member/myInfo")
	public ModelAndView myInfo(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		String tab = request.getParameter("tab");
		if(tab == null || tab == "") { tab = "3"; }
		/*
		String format = "yyyy-MM-dd hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String dateReg = type.format(today.getTime());
		*/

		AlmoneyVO memberLogAlmoneyTotal = new AlmoneyVO();
		AlmoneyVO memberLogAlmoney = new AlmoneyVO();


		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		String userPhoto = cookieLoginService.getCookieUserPhoto(request, response);
		String userNickName = cookieLoginService.getCookieUserNickName(request, response);
		int userLv = cookieLoginService.getCookieUserLv(request, response);
		BigDecimal userAlmoney = cookieLoginService.getCookieUserAlmoney(request, response);

		if(userSeq > 0) {
			if(userPhoto.equals("")) {
				userPhoto = "img_thum_base099.jpg";
			}
		}

		String callAlarmFlag = "N";
		if(request.getParameter("CallAlarmFlag") != null) {
			callAlarmFlag = request.getParameter("CallAlarmFlag");
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
				alarmParam.put("alarm", code.getCODE_MEM_ALARM_VIEW_FIELD_CD_MEM_LEVEL_UP());
				alarmParam.put("dateReg", now);

				commonService.setAlarmLog(alarmParam);
			}
		}

		if(userSeq > 0) {
			//System.out.println("userSeq : " + userSeq);
			memberLogAlmoneyTotal = commonService.getMemberLogAlmoneyTotal(userSeq);
			memberLogAlmoney = commonService.getMemberLogAlmoney(userSeq);
		}

		String levelStr = CommonUtil.getLevelName(userLv, request);

		HashMap<String, Object> memberManagementInfo = memberService.getMemberManagementInfo(userSeq);

		HashMap<String, Object> mbMap = new HashMap<String, Object>();
		if(memberManagementInfo != null) {
			JsonElement json = new Gson().toJsonTree(memberManagementInfo);

			//JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
			JsonObject jsonObj = json.getAsJsonObject();
			JSONParser jsonParse = new JSONParser();
	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

	    	mbMap.put("CountQ", jsonObj2.get("CountQ"));
	    	mbMap.put("SumQ", jsonObj2.get("SumQ"));
	    	mbMap.put("CountNotAnswerQ", jsonObj2.get("CountNotAnswerQ"));
	    	mbMap.put("CountRecivedAnswer", jsonObj2.get("CountRecivedAnswer"));
	    	mbMap.put("CountC", jsonObj2.get("CountC"));
	    	mbMap.put("CountNotChoiceQ", jsonObj2.get("CountNotChoiceQ"));
	    	mbMap.put("CountA", jsonObj2.get("CountA"));
	    	mbMap.put("SumA", jsonObj2.get("SumA"));
	    	mbMap.put("CountChoicedA", jsonObj2.get("CountChoicedA"));
	    	mbMap.put("CountZzim", jsonObj2.get("CountZzim"));
	    	mbMap.put("CountV", jsonObj2.get("CountV"));
	    	mbMap.put("QReplyCount", jsonObj2.get("QReplyCount"));
	    	mbMap.put("AReplyCount", jsonObj2.get("AReplyCount"));
	    	mbMap.put("QRecivedReplyCount", jsonObj2.get("QRecivedReplyCount"));
	    	mbMap.put("ARecivedReplyCount", jsonObj2.get("ARecivedReplyCount"));

		}
		HashMap<String, Object> memParam_que = new HashMap<String, Object>();

		memParam_que.put("userSeq", userSeq);

    	int queCnt1 = memberService.getMyRecivedReplyCount(memParam_que);

		int queCnt2 = memberService.getMyReplyCount(memParam_que);

		mav.addObject("tab", tab);
		mav.addObject("memberLogAlmoneyTotal", memberLogAlmoneyTotal);
		mav.addObject("memberLogAlmoney", memberLogAlmoney);
		mav.addObject("photo", userPhoto);
		mav.addObject("userSeq", userSeq);
		mav.addObject("userNickName", userNickName);
		mav.addObject("userLv", userLv);
		mav.addObject("levelStr", levelStr);
		mav.addObject("userAlmoney", userAlmoney);
		mav.addObject("mbMap", mbMap);

		mav.addObject("queCnt1", queCnt1);
		mav.addObject("queCnt2", queCnt2);


		return mav;
	}

	@RequestMapping("/member/myInfoLv")
	public ModelAndView myInfoLv(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		String tab = request.getParameter("tab");
		if(tab == null || tab == "") { tab = "1"; }
		/*
		String format = "yyyy-MM-dd hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String dateReg = type.format(today.getTime());
		*/

		AlmoneyVO memberLogAlmoneyTotal = new AlmoneyVO();
		AlmoneyVO memberLogAlmoney = new AlmoneyVO();


		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		String userPhoto = cookieLoginService.getCookieUserPhoto(request, response);
		String userNickName = cookieLoginService.getCookieUserNickName(request, response);
		int userLv = cookieLoginService.getCookieUserLv(request, response);
		BigDecimal userAlmoney = cookieLoginService.getCookieUserAlmoney(request, response);



		if(userSeq > 0) {
			if(userPhoto.equals("")) {
				userPhoto = "img_thum_base099.jpg";
			}

			//System.out.println("userSeq : " + userSeq);
			memberLogAlmoneyTotal = commonService.getMemberLogAlmoneyTotal(userSeq);
			memberLogAlmoney = commonService.getMemberLogAlmoney(userSeq);
		}

		String levelStr = CommonUtil.getLevelName(userLv, request);

		HashMap<String, Object> myInfoParam = new HashMap<String, Object>();
		myInfoParam.put("param_lv", 0);
		myInfoParam.put("userSeq", userSeq);
		myInfoParam.put("isreturnCFG", 1);
		List<HashMap<String, Object>> myInfoLvList = commonService.getMemoLvUpReadySetSP(myInfoParam);

		HashMap<String, Object> data1 = new HashMap<String, Object>();
		HashMap<String, Object> data2 = new HashMap<String, Object>();
		for(int i = 0; i < myInfoLvList.size(); i++) {
			if(i == 0) {
    			JsonElement json = new Gson().toJsonTree(myInfoLvList.get(i));

    			JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
    			JSONParser jsonParse = new JSONParser();
    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

    	    	data1.put("LvUpPhoto", jsonObj2.get("LvUpPhoto"));
    	    	data1.put("LvUpBaseAlmoney", CommonUtil.convertToDecimal(jsonObj2.get("LvUpBaseAlmoney")));
    	    	data1.put("LvUpStampCnt", jsonObj2.get("LvUpStampCnt"));
    	    	data1.put("LvUpQusRegCnt", jsonObj2.get("LvUpQusRegCnt"));
    	    	data1.put("LvUpAnsRegCnt", jsonObj2.get("LvUpAnsRegCnt"));
    	    	data1.put("LvUpAnsChoicedCnt", jsonObj2.get("LvUpAnsChoicedCnt"));
    	    	data1.put("LvUpAnsEstiCnt", jsonObj2.get("LvUpAnsEstiCnt"));
    	    	data1.put("LvUpReplyCnt", jsonObj2.get("LvUpReplyCnt"));
    	    	data1.put("LvUpEducationCnt", jsonObj2.get("LvUpEducationCnt"));
    	    	data1.put("LvUpRecmdLv_1", jsonObj2.get("LvUpRecmdLv_1"));
    	    	data1.put("LvUpRecmdCnt_1", jsonObj2.get("LvUpRecmdCnt_1"));
    		}
    		if(i == 1) {
    			JsonElement json = new Gson().toJsonTree(myInfoLvList.get(i));

    			JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
    			JSONParser jsonParse = new JSONParser();
    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

    	    	data2.put("isUpOK", jsonObj2.get("isUpOK"));
    	    	data2.put("Photo", jsonObj2.get("Photo"));
    	    	data2.put("Almoney", jsonObj2.get("Almoney"));
    	    	data2.put("RecmdCnt", jsonObj2.get("RecmdCnt"));
    	    	data2.put("EducationCnt", jsonObj2.get("EducationCnt"));
    	    	data2.put("STAMP", jsonObj2.get("STAMP"));
    	    	data2.put("QusRegCnt", jsonObj2.get("QusRegCnt"));
    	    	data2.put("AnsRegCnt", jsonObj2.get("AnsRegCnt"));
    	    	data2.put("AnsChoicedCnt", jsonObj2.get("AnsChoicedCnt"));
    	    	data2.put("AnsEstiCnt", jsonObj2.get("AnsEstiCnt"));
    	    	data2.put("ReplyCnt", jsonObj2.get("ReplyCnt"));
    	    	data2.put("StartDate", jsonObj2.get("StartDate"));
    	    	data2.put("EndDate", jsonObj2.get("EndDate"));
    		}
		}


		mav.addObject("tab", tab);
		mav.addObject("memberLogAlmoneyTotal", memberLogAlmoneyTotal);
		mav.addObject("memberLogAlmoney", memberLogAlmoney);
		mav.addObject("photo", userPhoto);
		mav.addObject("userNickName", userNickName);
		mav.addObject("userSeq", userSeq);
		mav.addObject("userLv", userLv);
		mav.addObject("levelStr", levelStr);
		mav.addObject("userAlmoney", userAlmoney);
		mav.addObject("myInfoLvList_1", data1);
		mav.addObject("myInfoLvList_2", data2);

		return mav;
	}

	@RequestMapping("/member/myInfoExch")
	public ModelAndView myInfoExch(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		String tab = request.getParameter("tab");
		if(tab == null || tab == "") { tab = "2"; }
		/*
		String format = "yyyy-MM-dd hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String dateReg = type.format(today.getTime());
		*/

		AlmoneyVO memberLogAlmoneyTotal = new AlmoneyVO();
		AlmoneyVO memberLogAlmoney = new AlmoneyVO();


		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		String userPhoto = cookieLoginService.getCookieUserPhoto(request, response);
		String userNickName = cookieLoginService.getCookieUserNickName(request, response);
		int userLv = cookieLoginService.getCookieUserLv(request, response);
		BigDecimal userAlmoney = cookieLoginService.getCookieUserAlmoney(request, response);

		if(userSeq > 0) {
			if(userPhoto.equals("")) {
				userPhoto = "img_thum_base099.jpg";
			}

			memberLogAlmoneyTotal = commonService.getMemberLogAlmoneyTotal(userSeq);
			memberLogAlmoney = commonService.getMemberLogAlmoney(userSeq);
		}

		String levelStr = CommonUtil.getLevelName(userLv, request);

		HashMap<String, Object> myInfoParam = new HashMap<String, Object>();
		myInfoParam.put("param_lv", 0);
		myInfoParam.put("userSeq", userSeq);
		myInfoParam.put("isreturnCFG", 1);

		List<HashMap<String, Object>> myInfoExchList = commonService.getMemoExchReadySetCommSP(myInfoParam);

		HashMap<String, Object> exchData1 = new HashMap<String, Object>();
		HashMap<String, Object> exchData2 = new HashMap<String, Object>();
		for(int i = 0; i < myInfoExchList.size(); i++) {
			if(i == 0) {
    			JsonElement json = new Gson().toJsonTree(myInfoExchList.get(i));

    			JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
    			JSONParser jsonParse = new JSONParser();
    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

    	    	exchData1.put("ExchBaseAlmoney", CommonUtil.convertToDecimal(jsonObj2.get("ExchBaseAlmoney")));
    	    	exchData1.put("ExchLimitAlmoney", CommonUtil.convertToDecimal(jsonObj2.get("ExchLimitAlmoney")));
    	    	exchData1.put("ExchAlmoneyTexRate", jsonObj2.get("ExchAlmoneyTexRate"));
    	    	exchData1.put("ExchStampCnt", jsonObj2.get("ExchStampCnt"));
    	    	exchData1.put("ExchQusRegCnt", jsonObj2.get("ExchQusRegCnt"));
    	    	exchData1.put("ExchAnsRegCnt", jsonObj2.get("ExchAnsRegCnt"));
    	    	exchData1.put("ExchAnsChoicedCnt", jsonObj2.get("ExchAnsChoicedCnt"));
    	    	exchData1.put("ExchAnsEstiCnt", jsonObj2.get("ExchAnsEstiCnt"));
    		}
    		if(i == 1) {
    			JsonElement json = new Gson().toJsonTree(myInfoExchList.get(i));

    			JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
    			JSONParser jsonParse = new JSONParser();
    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

    	    	exchData2.put("isExchOK", jsonObj2.get("isExchOK"));
    	    	exchData2.put("Almoney", CommonUtil.convertToDecimal(jsonObj2.get("Almoney")));
    	    	exchData2.put("STAMP", jsonObj2.get("STAMP"));
    	    	exchData2.put("QusRegCnt", jsonObj2.get("QusRegCnt"));
    	    	exchData2.put("AnsRegCnt", jsonObj2.get("AnsRegCnt"));
    	    	exchData2.put("AnsChoicedCnt", jsonObj2.get("AnsChoicedCnt"));
    	    	exchData2.put("AnsEstiCnt", jsonObj2.get("AnsEstiCnt"));
    	    	exchData2.put("StartDate", jsonObj2.get("StartDate"));
    	    	exchData2.put("EndDate", jsonObj2.get("EndDate"));
    		}
		}


		mav.addObject("tab", tab);
		mav.addObject("memberLogAlmoneyTotal", memberLogAlmoneyTotal);
		mav.addObject("memberLogAlmoney", memberLogAlmoney);
		mav.addObject("photo", userPhoto);
		mav.addObject("userSeq", userSeq);
		mav.addObject("userNickName", userNickName);
		mav.addObject("userLv", userLv);
		mav.addObject("levelStr", levelStr);
		mav.addObject("userAlmoney", userAlmoney);
		mav.addObject("myInfoExchList_1", exchData1);
		mav.addObject("myInfoExchList_2", exchData2);

		return mav;
	}

	@RequestMapping(value="/member/myInfoLvAjax", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String myInfoLvAjax(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		int userSeq = 0;

		String act = "";
		String last = request.getParameter("LAST");

		if(request.getParameter("ACT") == null || request.getParameter("ACT") == "") {
			return null;
		}
		else {
			act = request.getParameter("ACT");
		}

		DecimalFormat df = new DecimalFormat("00");
		Calendar currentCalendar = Calendar.getInstance();


		currentCalendar.add(currentCalendar.DATE, (-7 * Integer.parseInt(last)));
		String strYear = Integer.toString(currentCalendar.get(Calendar.YEAR));
		String strMonth = df.format(currentCalendar.get(Calendar.MONTH) + 1);
		String strDay = df.format(currentCalendar.get(Calendar.DATE));
		String date_week = strYear + "-" + strMonth + "-" + strDay;

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		date = sdf.parse(date_week);

		Calendar cal = Calendar.getInstance(Locale.KOREA);
		cal.setTime(date);
		cal.add(Calendar.DATE, 2 - cal.get(Calendar.DAY_OF_WEEK));

		date_week = sdf.format(cal.getTime());


		if(userSeq == 0) {
			return null;
		}


		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("userSeq", userSeq);
        //  		System.out.println(date_week);
		param.put("date_week", date_week);

		List<HashMap<String, Object>> lst = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> myInoLvList = commonService.getMyInfoLvList(param);

		String result = null;
		if( myInoLvList != null && !myInoLvList.isEmpty() && myInoLvList.size() > 0 ) {
			myInoLvList.put("LAST", last);

			lst.add(myInoLvList);

			result = CommonUtil.libJsonArrExit(act, lst);
		}
		else {
			result = CommonUtil.libJsonArrExit(act, lst);
		}

		return result;
	}


	@RequestMapping(value="/member/myInfoExchAjax", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String myInfoExchAjax(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		String act = "";
		String last = request.getParameter("LAST");
		//System.out.println("last : " + last);
		if(request.getParameter("ACT") == null || request.getParameter("ACT") == "") {
			return null;
		}
		else {
			act = request.getParameter("ACT");
		}

		DecimalFormat df = new DecimalFormat("00");
		Calendar currentCalendar = Calendar.getInstance();

		currentCalendar.add(currentCalendar.DATE, (-7 * Integer.parseInt(last)));
		String strYear = Integer.toString(currentCalendar.get(Calendar.YEAR));
		String strMonth = df.format(currentCalendar.get(Calendar.MONTH) + 1);
		String strDay = df.format(currentCalendar.get(Calendar.DATE));
		String date_week = strYear + "-" + strMonth + "-" + strDay;

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		date = sdf.parse(date_week);

		Calendar cal = Calendar.getInstance(Locale.KOREA);
		cal.setTime(date);
		cal.add(Calendar.DATE, 2 - cal.get(Calendar.DAY_OF_WEEK));
		//System.out.println("첫번째 요일(월요일)날짜:"+sdf.format(cal.getTime()));

		date_week = sdf.format(cal.getTime());
		//System.out.println("date_week : " + date_week); // 2020-10-21 일 경우 2020-10-19


		if(userSeq == 0) {
			return null;
		}


		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("userSeq", userSeq);
		param.put("date_week", date_week);

		List<HashMap<String, Object>> lst = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> myInoExchList = commonService.getMyInfoExchList(param);
		//System.out.println("myInoExchList.size() : " + myInoExchList.size());
		HashMap<String, Object> data1 = new HashMap<String, Object>();
		HashMap<String, Object> data2 = new HashMap<String, Object>();

		for(int i = 0; i < myInoExchList.size(); i++) {
			if(i == 0) {
				try {
					JsonElement json = new Gson().toJsonTree(myInoExchList.get(i));
					JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
	    			JSONParser jsonParse = new JSONParser();
	    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

	    	    	data1.put("Lv", jsonObj2.get("Lv"));//int
	    	    	//data1.put("LvUpPhoto", jsonObj2.get("LvUpPhoto"));//String
	    	    	data1.put("LvUpBaseAlmoney", CommonUtil.convertToDecimal(jsonObj2.get("LvUpBaseAlmoney")));//BigDecimal
	    	    	data1.put("LvUpStampCnt", jsonObj2.get("LvUpStampCnt"));//int
	    	    	data1.put("LvUpQusRegCnt", jsonObj2.get("LvUpQusRegCnt"));//int
	    	    	data1.put("LvUpQusChoiceRate", jsonObj2.get("LvUpQusChoiceRate"));//float
	    	    	data1.put("LvUpAnsRegCnt", jsonObj2.get("LvUpAnsRegCnt"));//int
	    	    	data1.put("LvUpAnsChoicedCnt", jsonObj2.get("LvUpAnsChoicedCnt"));//int
	    	    	data1.put("LvUpAnsEstiCnt", jsonObj2.get("LvUpAnsEstiCnt"));//int
	    	    	data1.put("LvUpReplyCnt", jsonObj2.get("LvUpReplyCnt"));//int
	    	    	data1.put("LvUpRecmdLv_1", jsonObj2.get("LvUpRecmdLv_1"));//int
	    	    	data1.put("LvUpRecmdCnt_1", jsonObj2.get("LvUpRecmdCnt_1"));//int
	    	    	data1.put("LvUpEducationCnt", jsonObj2.get("LvUpEducationCnt"));//int
	    	    	data1.put("ExchBaseAlmoney", CommonUtil.convertToDecimal(jsonObj2.get("ExchBaseAlmoney")));//BigDecimal
	    	    	data1.put("ExchLimitAlmoney", CommonUtil.convertToDecimal(jsonObj2.get("ExchBaseAlmoney")));//BigDecimal
	    	    	data1.put("ExchAlmoneyTexRate", jsonObj2.get("ExchAlmoneyTexRate"));//float
	    	    	data1.put("ExchStampCnt", jsonObj2.get("ExchStampCnt"));//int
	    	    	data1.put("ExchQusRegCnt", jsonObj2.get("ExchQusRegCnt"));//int
	    	    	data1.put("ExchQusChoiceRate", jsonObj2.get("ExchQusChoiceRate"));//float
	    	    	data1.put("ExchAnsRegCnt", jsonObj2.get("ExchAnsRegCnt"));//int
	    	    	data1.put("ExchAnsChoicedCnt", jsonObj2.get("ExchAnsChoicedCnt"));
	    	    	data1.put("ExchAnsEstiCnt", jsonObj2.get("ExchAnsEstiCnt"));
				}
				catch(Exception e) {
					continue;
				}
    		}
    		if(i == 1) {
    			try {
	    			JsonElement json = new Gson().toJsonTree(myInoExchList.get(i));

	    			JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
	    			JSONParser jsonParse = new JSONParser();
	    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

	    	    	data2.put("weekStartDate", jsonObj2.get("weekStartDate"));
	    	    	data2.put("weekEndDate", jsonObj2.get("weekEndDate"));
	    	    	data2.put("QusRegCnt", jsonObj2.get("QusRegCnt"));
	    	    	data2.put("AnsRegCnt", jsonObj2.get("AnsRegCnt"));
	    	    	data2.put("AnsEstiCnt", jsonObj2.get("AnsEstiCnt"));
	    	    	data2.put("AnsChoicedCnt", jsonObj2.get("AnsChoicedCnt"));
	    	    	data2.put("StartDate", jsonObj2.get("StartDate"));
	    	    	data2.put("EndDate", jsonObj2.get("EndDate"));
    			}
    			catch(Exception e) {
    				continue;
    			}
    		}
		}



		String result = null;
		//System.out.println("data1.size() : " + data1.size());
		//System.out.println("data2.size() : " + data2.size());
		if(data2.size() > 0) {

			if(data1.size() == 0) {
				HashMap<String, Object> blankParam = new HashMap<String, Object>();
				lst.add(blankParam);
			}
			else {
				lst.add(data1);
			}

			lst.add(data2);

			HashMap<String, Object> lParam = new HashMap<String, Object>();
			lParam.put("LAST", last);
			lst.add(lParam);

			//for(int i = 0; i < lst.size(); i++ ) {
			//	System.out.println(i + " : " + lst.get(i));
			//}

			result = CommonUtil.libJsonArrExit(act, lst);
		}
		else {
			result = CommonUtil.libJsonArrExit(act, lst);
		}

		return result;
	}

	@RequestMapping("/member/myJoin")
	public ModelAndView myJoing(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		MD5Class md5 = new MD5Class();
		EncLibrary encLibrary = new EncLibrary();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq == 0) {

			mav.setViewName("redirect:/default/main");

			return mav;
		}

		MemberVO member = memberService.getAlmoneyMyJoin(userSeq);

		String email = member.getEmail();
		String phone = member.getPhone();
		String nation = member.getNation();
		String lang = member.getLang();

		String email1 = "";
		String email2 = "";
		if(email != null) {
			email = encLibrary.AlmoneyDecrypt(email);
			String[] emailT = email.split("@");

			if(emailT.length > 1) {
				email1 = emailT[0];
				email2 = emailT[1];
			}
			else {
				email1 = email;
			}
		}

		if(phone != null) {
			phone = encLibrary.AlmoneyDecrypt(phone);
		}

		if(nation != null) {
			nation = nation.toUpperCase();
		}


		String nationString = CommonUtil.getNationString(nation);

		mav.addObject("member", member);
		mav.addObject("email1", email1);
		mav.addObject("email2", email2);
		mav.addObject("phone", phone);

		mav.addObject("nationString", nationString);
		mav.addObject("lang", lang);

		return mav;
	}

	@RequestMapping("/member/myJoinSave")
	public void myJoinSave(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		MD5Class md5 = new MD5Class();
		EncLibrary encLibrary = new EncLibrary();

		String nickName = CommonUtil.fn_Word_In(request.getParameter("NickName"));
		String password = request.getParameter("Password");
		String newPassword1 = request.getParameter("NewPassword1");
		String newPassword2 = request.getParameter("NewPassword2");
		String email1 = CommonUtil.fn_Word_In(request.getParameter("Email1"));
		String email2 = CommonUtil.fn_Word_In(request.getParameter("Email2"));
		String intro = CommonUtil.fn_Word_In(request.getParameter("Intro"));
		String flagSave = CommonUtil.fn_Word_In(request.getParameter("FlagSave"));
		String lang = request.getParameter("lang");

		String userPW_md5 = md5.md5_encode(password);

		String msg1 = messageSource.getMessage("msg_0203", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1085", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_0999", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_1086", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg6 = messageSource.getMessage("msg_1087", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg7 = messageSource.getMessage("msg_1088", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg8 = messageSource.getMessage("msg_1089", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg9 = messageSource.getMessage("msg_1090", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		if(nickName == null || nickName == "") {
			CommonUtil.jspAlert(response, msg1, "", ""); // msg1 활용
			return;
		}

		if(password == null || password == "") {
			CommonUtil.jspAlert(response, msg2, "", ""); // msg2 활용
			return;
		}

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq == 0) {
			CommonUtil.jspAlert(response, msg3, "", ""); // msg3 활용
			return;
		}

		HashMap<String, Object> nickChkParam = new HashMap<String, Object>();
		nickChkParam.put("userSeq", userSeq);
		nickChkParam.put("nickName", nickName);

		int checkNick = memberService.getNickCheck(nickChkParam);

		if(checkNick > 0) {
			CommonUtil.jspAlert(response, msg4, "", ""); // msg4 활용
			return;
		}

		MemberVO member = memberService.getAlmoneyMyJoin(userSeq);

		if(!userPW_md5.equals(member.getPw())) {
			CommonUtil.jspAlert(response, msg5, "", ""); // msg5 활용
			return;
		}

		if(newPassword1 != "" || newPassword2 != "") {
			if(!newPassword1.equals(newPassword2)) {
				CommonUtil.jspAlert(response, msg6, "", ""); // msg6 활용
				return;
			}
			else {
				userPW_md5 = md5.md5_encode(newPassword1.trim());
			}
		}

		String email = encLibrary.AlmoneyEncrypt(email1 + "@" + email2);

		HashMap<String, Object> saveParam = new HashMap<String, Object>();
		saveParam.put("strData", flagSave);
		saveParam.put("userPW_md5", userPW_md5);
		saveParam.put("email", email);
		saveParam.put("intro", intro);
		saveParam.put("lang", lang);
		saveParam.put("userSeq", userSeq);

		memberService.updateMyJoinInfo(saveParam);


		if(flagSave.equals("E")) {
			CommonUtil.jspAlert(response, msg7 + "\\n" + msg8, "/default/logOut", "parent.parent"); // msg7, msg8 활용
		}
		else if(flagSave.equals("D")) {
			CommonUtil.jspAlert(response, msg9, "/default/logOut", "parent.parent"); // msg9 활용
		}
	}

	@RequestMapping("/member/myQuestion")
	public ModelAndView myQuestion(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		int userSeq = cookieLoginService.getCookieUserSeq(request, response);


		if(userSeq == 0) {
			String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용
			return null;
		}

		if(request.getParameter("userWho") != null) {
			userSeq = Integer.parseInt((CommonUtil.fn_Word_In(request.getParameter("userWho"))));
		}

		String curPageName = "/member/myQuestion";
		String req_TREC = "";
		String req_PG = "";
		String src_Sort = "";
		String src_OrderBy = "";
		String section1 = "";
		String viewFlag = "";
		String callAlarmFlag = "";

		if(request.getParameter("trec") != null) {
			req_TREC = CommonUtil.fn_Word_In(request.getParameter("trec"));
		}
		if(request.getParameter("pg") != null) {
			req_PG = CommonUtil.fn_Word_In(request.getParameter("pg"));
		}
		if(request.getParameter("src_Sort") != null) {
			src_Sort = CommonUtil.fn_Word_In(request.getParameter("src_Sort"));
		}
		if(request.getParameter("src_OrderBy") != null) {
			src_OrderBy = CommonUtil.fn_Word_In(request.getParameter("src_OrderBy"));
		}
		if(request.getParameter("Section1") != null) {
			section1 = CommonUtil.fn_Word_In(request.getParameter("Section1"));
		}
		if(request.getParameter("Flag") != null) {
			viewFlag = CommonUtil.fn_Word_In(request.getParameter("Flag"));
		}
		if(request.getParameter("CallAlarmFlag") != null) {
			callAlarmFlag = CommonUtil.fn_Word_In(request.getParameter("CallAlarmFlag"));
		}

		if(callAlarmFlag.equals("Y")) {
			if(viewFlag.equals("N")) {
				String format = "yyyy-MM-dd HH:mm:ss";
				Calendar today = Calendar.getInstance();
				SimpleDateFormat type = new SimpleDateFormat(format);
				String now = type.format(today.getTime());

				CodeUtil code = new CodeUtil(request);
				String codeMemAlarmViewFile = code.getCODE_MEM_ALARM_VIEW_FIELD_CD_ANS_CHOICE_READY();

				HashMap<String, Object> alarmParam = new HashMap<String, Object>();
				alarmParam.put("mem_seq", userSeq);
				alarmParam.put("alarm", codeMemAlarmViewFile);
				alarmParam.put("dateReg", now);

			//	System.out.println("mem_seq : " + alarmParam.get("mem_seq"));
			//	System.out.println("alarm : " + alarmParam.get("alarm"));
			//	System.out.println("dateReg : " + alarmParam.get("dateReg"));

				commonService.setAlarmLog(alarmParam);
			}
		}

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

		HashMap<String, Object> memParam = new HashMap<String, Object>();
		memParam.put("userSeq", userSeq);
		memParam.put("flagUse", "Y");
		memParam.put("section1", section1);
		memParam.put("viewFlag", viewFlag);

		int queCnt = memberService.getMyQuestionCount(memParam);

		n_trec = queCnt;


		int n_pagesize = 30;
		int n_pagescnt = 5;
		int n_curpage = 1;
		int n_totalpage = 0;

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



		List<QuestionVO> myQueList = null;

		if(queCnt > 0) {
			HashMap<String, Object> memParam2 = new HashMap<String, Object>();
			memParam2.put("strData1", ((n_pagesize * (n_curpage-1))+1));
			memParam2.put("strData2", (n_pagesize * (n_curpage)));
			memParam2.put("src_Sort", src_Sort);
			memParam2.put("userSeq", userSeq);
			memParam2.put("flagUse", "Y");
			memParam2.put("section1", section1);
			memParam2.put("viewFlag", viewFlag);
			if(viewFlag.equals("N")) {
				myQueList =  memberService.getMyQuestionListFlagN(memParam2);
			}else {
				myQueList =  memberService.getMyQuestionList(memParam2);
			}
		}
		//System.out.println("strData1 : "+((n_pagesize * (n_curpage-1))+1));
		//System.out.println("strData2 : "+(n_pagesize * (n_curpage)));

		List<SectionVO> sectionVO = commonService.getBoardSection1();

		String url = curPageName + "?trec=" + req_TREC + "&src_Sort=" + src_Sort + "&src_OrderBy=" + src_OrderBy + "&Section1=" + section1 + "&Flag=" + viewFlag + "&CallAlarmFlag=" + callAlarmFlag+"&userWho="+userSeq;
		String paging = CommonUtil.get_Paging_Tag_New_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);
		curPageName= curPageName+"?userWho="+userSeq;

		mav.addObject("viewFlag", viewFlag);
		mav.addObject("n_trec", n_trec);
		mav.addObject("curPageName", curPageName);
		mav.addObject("n_curpage", n_curpage);
		mav.addObject("myQueList", myQueList);
		mav.addObject("sectionVO", sectionVO);
		mav.addObject("paging", paging);
		mav.addObject("section1", section1);

		return mav;
	}

	@RequestMapping("/member/myAnswer")
	public ModelAndView myAnswer(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		int userSeq = cookieLoginService.getCookieUserSeq(request, response);


		if(userSeq == 0) {
			String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용
			return null;
		}

		if(request.getParameter("userWho") != null) {
			userSeq = Integer.parseInt((CommonUtil.fn_Word_In(request.getParameter("userWho"))));
		}


		String curPageName = "/member/myAnswer";
		String req_TREC = "";
		String req_PG = "";
		String src_Sort = "";
		String src_OrderBy = "";
		String flagChoice = "";
		String callAlarmFlag = "";

		if(request.getParameter("trec") != null) {
			req_TREC = CommonUtil.fn_Word_In(request.getParameter("trec"));
		}
		if(request.getParameter("pg") != null) {
			req_PG = CommonUtil.fn_Word_In(request.getParameter("pg"));
		}
		if(request.getParameter("src_Sort") != null) {
			src_Sort = CommonUtil.fn_Word_In(request.getParameter("src_Sort"));
		}
		if(request.getParameter("src_OrderBy") != null) {
			src_OrderBy = CommonUtil.fn_Word_In(request.getParameter("src_OrderBy"));
		}
		if(request.getParameter("FlagChoice") != null) {
			flagChoice = CommonUtil.fn_Word_In(request.getParameter("FlagChoice"));
		}
		//notW 는 flagChoice를 사용하지 않고 ( form 없이 ) href 만으로 처리하기 위해 사용합니다.
		if(request.getParameter("notW") != null) {
			flagChoice = CommonUtil.fn_Word_In(request.getParameter("notW"));
		}


		if(req_PG == "") {
			req_PG = "1";
		}

		if(src_Sort == "") {
			src_Sort = "Seq";
		}

		if(src_OrderBy == "") {
			src_OrderBy = "DESC";
		}

		if(request.getParameter("CallAlarmFlag") != null) {
			callAlarmFlag = CommonUtil.fn_Word_In(request.getParameter("CallAlarmFlag"));
		}

		if(callAlarmFlag.equals("Y")) {
			String format = "yyyy-MM-dd HH:mm:ss";
			Calendar today = Calendar.getInstance();
			SimpleDateFormat type = new SimpleDateFormat(format);
			String now = type.format(today.getTime());

			CodeUtil code = new CodeUtil(request);
			String codeMemAlarmViewFile = code.getCODE_MEM_ALARM_VIEW_FIELD_CD_ANS_CHOICE();

			HashMap<String, Object> alarmParam = new HashMap<String, Object>();
			alarmParam.put("mem_seq", userSeq);
			alarmParam.put("alarm", codeMemAlarmViewFile);
			alarmParam.put("dateReg", now);

			commonService.setAlarmLog(alarmParam);
		}

		String s_tmp = "";
		int n_trec = 0;
		s_tmp = req_TREC;

		if(CommonUtil.isNumeric(s_tmp) && s_tmp.length() > 0) {
			n_trec = Integer.parseInt(s_tmp);
		}

		HashMap<String, Object> memParam = new HashMap<String, Object>();
		memParam.put("userSeq", userSeq);
		memParam.put("flagChoice", flagChoice);
		memParam.put("flagUse", "Y");

		int ansCnt = memberService.getMyAnswerCount(memParam);

		n_trec = ansCnt;


		int n_pagesize = 30;
		int n_pagescnt = 5;
		int n_curpage = 1;
		int n_totalpage = 0;

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

		List<AnswerVO> myAnsList = null;
		if(ansCnt > 0) {
			HashMap<String, Object> memParam2 = new HashMap<String, Object>();
			memParam2.put("strData1", (n_pagesize * (n_curpage-1)+1));
			memParam2.put("strData2", (n_pagesize * (n_curpage-1)+30));
			memParam2.put("userSeq", userSeq);
			memParam2.put("flagChoice", flagChoice);
			memParam2.put("flagUse", "Y");

			myAnsList =  memberService.getMyAnswerList(memParam2);
		}

		String url = curPageName + "?trec=" + req_TREC + "&src_Sort=" + src_Sort + "&src_OrderBy=" + src_OrderBy + "&FlagChoice=" + flagChoice + "&CallAlarmFlag=" + callAlarmFlag+"&userWho="+userSeq;
		String paging = CommonUtil.get_Paging_Tag_New_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);
		curPageName= curPageName+"?userWho="+userSeq;

		mav.addObject("curPageName", curPageName);
		mav.addObject("n_curpage", n_curpage);
		mav.addObject("flagChoice", flagChoice);
		mav.addObject("myAnsList", myAnsList);
		mav.addObject("paging", paging);
		mav.addObject("n_trec", n_trec);

		return mav;
	}

	@RequestMapping("/member/myZzim")
	public ModelAndView myZzim(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq == 0) {
			String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용
			return null;
		}

		String curPageName = "/member/myZzim";
		String req_TREC = "";
		String req_PG = "";
		String src_Sort = "";
		String src_OrderBy = "";
		String section1 = "";

		if(request.getParameter("trec") != null) {
			req_TREC = CommonUtil.fn_Word_In(request.getParameter("trec"));
		}
		if(request.getParameter("pg") != null) {
			req_PG = CommonUtil.fn_Word_In(request.getParameter("pg"));
		}
		if(request.getParameter("src_Sort") != null) {
			src_Sort = CommonUtil.fn_Word_In(request.getParameter("src_Sort"));
		}
		if(request.getParameter("src_OrderBy") != null) {
			src_OrderBy = CommonUtil.fn_Word_In(request.getParameter("src_OrderBy"));
		}
		if(request.getParameter("Section1") != null) {
			section1 = CommonUtil.fn_Word_In(request.getParameter("Section1"));
		}

		if(section1 == "") {
			section1 = "0";
		}

		if(req_PG == "") {
			req_PG = "1";
		}

		if(src_Sort == "") {
			src_Sort = "Seq";
		}

		if(src_OrderBy == "") {
			src_OrderBy = "DESC";
		}

		String s_tmp = "";
		int n_trec = 0;
		s_tmp = req_TREC;

		if(CommonUtil.isNumeric(s_tmp) && s_tmp.length() > 0) {
			n_trec = Integer.parseInt(s_tmp);
		}

		HashMap<String, Object> zzimParam = new HashMap<String, Object>();
		zzimParam.put("userSeq", userSeq);
		zzimParam.put("section1", section1);
		int myZzimCnt = memberService.getMyZzimCount(zzimParam);

		n_trec = myZzimCnt;

		int n_pagesize = 30;
		int n_pagescnt = 10;
		int n_curpage = 1;
		int n_totalpage = 0;

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

		List<MyZzimVO> myZzimList = null;

		if(myZzimCnt > 0) {
			zzimParam.put("p_Option", (n_pagesize * n_curpage));
			zzimParam.put("section1", section1);
			zzimParam.put("psrc_Cond", src_Sort);

			myZzimList =  memberService.getMyZzimList(zzimParam);
		}

		List<SectionVO> sectionVO = commonService.getBoardSection1();

		String url = curPageName + "?trec=" + req_TREC + "&src_Sort=" + src_Sort + "&src_OrderBy=" + src_OrderBy + "&Section1=" + section1;
		String paging = CommonUtil.get_Paging_Tag_New_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);

		mav.addObject("n_trec", n_trec);
		mav.addObject("curPageName", curPageName);
		mav.addObject("n_curpage", n_curpage);
		mav.addObject("myZzimList", myZzimList);
		mav.addObject("sectionVO", sectionVO);
		mav.addObject("paging", paging);
		mav.addObject("section1", section1);

		return mav;
	}

	@RequestMapping("member/myZzimDel")
	public void myZzimDel(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		String questionSeq = request.getParameter("QuestionSeq");

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1091", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용


		if(userSeq == 0) {
			CommonUtil.jspAlert(response, msg1, "", ""); // msg1 활용
			return;
		}

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("questionSeq", questionSeq);
		param.put("userSeq", userSeq);

		commonService.deleteZzim(param);

		CommonUtil.jspAlert(response, msg2, "/member/myZzim", "parent.parent"); // msg2 활용
	}

	@RequestMapping("/member/myView")
	public ModelAndView myView(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);


		if(userSeq == 0) {
			String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용
			return null;
		}

		String curPageName = "/member/myView";
		String req_TREC = "";
		String req_PG = "";
		String src_Sort = "";
		String src_OrderBy = "";
		String section1 = "";

		if(request.getParameter("trec") != null) {
			req_TREC = CommonUtil.fn_Word_In(request.getParameter("trec"));
		}
		if(request.getParameter("pg") != null) {
			req_PG = CommonUtil.fn_Word_In(request.getParameter("pg"));
		}
		if(request.getParameter("src_Sort") != null) {
			src_Sort = CommonUtil.fn_Word_In(request.getParameter("src_Sort"));
		}
		if(request.getParameter("src_OrderBy") != null) {
			src_OrderBy = CommonUtil.fn_Word_In(request.getParameter("src_OrderBy"));
		}
		if(request.getParameter("Section1") != null) {
			section1 = CommonUtil.fn_Word_In(request.getParameter("Section1"));
		}

		if(section1 == "") {
			section1 = "0";
		}

		if(req_PG == "") {
			req_PG = "1";
		}

		if(src_Sort == "") {
			src_Sort = "Seq";
		}

		if(src_OrderBy == "") {
			src_OrderBy = "DESC";
		}

		String s_tmp = "";
		int n_trec = 0;
		s_tmp = req_TREC;

		if(CommonUtil.isNumeric(s_tmp) && s_tmp.length() > 0) {
			n_trec = Integer.parseInt(s_tmp);
		}

		HashMap<String, Object> memParam = new HashMap<String, Object>();
		memParam.put("userSeq", userSeq);
		memParam.put("section1", section1);
		int myViewCnt = memberService.getMyViewCount(memParam);

		n_trec = myViewCnt;

		int n_pagesize = 30;
		int n_pagescnt = 10;
		int n_curpage = 1;
		int n_totalpage = 0;

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

		List<LogViewVO> myViewList = null;

		if(myViewCnt > 0) {
			memParam.put("p_Option", (n_pagesize * n_curpage));

			myViewList = memberService.getMyViewList(memParam);
		}

		List<SectionVO> sectionVO = commonService.getBoardSection1();

		String url = curPageName + "?trec=" + req_TREC + "&src_Sort=" + src_Sort + "&src_OrderBy=" + src_OrderBy + "&Section1=" + section1;
		String paging = CommonUtil.get_Paging_Tag_New_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);

		mav.addObject("n_trec", n_trec);
		mav.addObject("curPageName", curPageName);
		mav.addObject("n_curpage", n_curpage);
		mav.addObject("myViewList", myViewList);
		mav.addObject("sectionVO", sectionVO);
		mav.addObject("paging", paging);
		mav.addObject("section1", section1);

		return mav;
	}

	@RequestMapping("/member/myReply")
	public ModelAndView myReply(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq == 0) {
			String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용
			return null;
		}

		if(request.getParameter("userWho") != null) {
			userSeq = Integer.parseInt((CommonUtil.fn_Word_In(request.getParameter("userWho"))));
		}


		String curPageName = "/member/myReply";
		String req_TREC = "";
		String req_PG = "";
		String src_Sort = "";
		String src_OrderBy = "";
		String section1 = "";
		String qa_Cls = "";
		String flagMe = "";
		String callAlarmFlag = "";

		if(request.getParameter("trec") != null) {
			req_TREC = CommonUtil.fn_Word_In(request.getParameter("trec"));
		}
		if(request.getParameter("pg") != null) {
			req_PG = CommonUtil.fn_Word_In(request.getParameter("pg"));
		}
		if(request.getParameter("src_Sort") != null) {
			src_Sort = CommonUtil.fn_Word_In(request.getParameter("src_Sort"));
		}
		if(request.getParameter("src_OrderBy") != null) {
			src_OrderBy = CommonUtil.fn_Word_In(request.getParameter("src_OrderBy"));
		}
		if(request.getParameter("Section1") != null) {
			section1 = CommonUtil.fn_Word_In(request.getParameter("Section1"));
		}
		if(request.getParameter("QA_Cls") != null) {
			qa_Cls = CommonUtil.fn_Word_In(request.getParameter("QA_Cls"));
		}
		if(request.getParameter("FlagMe") != null) {
			flagMe = CommonUtil.fn_Word_In(request.getParameter("FlagMe"));
		}
		if(request.getParameter("CallAlarmFlag") != null) {
			callAlarmFlag = CommonUtil.fn_Word_In(request.getParameter("CallAlarmFlag"));
		}
		if(callAlarmFlag.equals("Y")) {
			if(flagMe.equals("Y")) {
				String format = "yyyy-MM-dd HH:mm:ss";
				Calendar today = Calendar.getInstance();
				SimpleDateFormat type = new SimpleDateFormat(format);
				String now = type.format(today.getTime());

				//setAlarmLog
				CodeUtil code = new CodeUtil(request);
				String codeMemAlarmViewFile = code.getCODE_MEM_ALARM_VIEW_FIELD_CD_CMT_REGIST();

				HashMap<String, Object> alarmParam = new HashMap<String, Object>();
				alarmParam.put("mem_seq", userSeq);
				alarmParam.put("alarm", codeMemAlarmViewFile);
				alarmParam.put("dateReg", now);

				commonService.setAlarmLog(alarmParam);
			}
		}
		if(qa_Cls == "") {
			qa_Cls = "Q";
		}
		if(section1 == "") {
			section1 = "0";
		}

		if(req_PG == "") {
			req_PG = "1";
		}

		if(src_Sort == "") {
			src_Sort = "regdate";
		}

		if(src_OrderBy == "") {
			src_OrderBy = "DESC";
		}

		String s_tmp = "";
		int n_trec = 0;
		s_tmp = req_TREC;

		if(CommonUtil.isNumeric(s_tmp) && s_tmp.length() > 0) {
			n_trec = Integer.parseInt(s_tmp);
		}

		HashMap<String, Object> memParam = new HashMap<String, Object>();
		memParam.put("userSeq", userSeq);


		int queCnt = 0;

		if(qa_Cls.equals("A")) {
			queCnt = memberService.getMyRecivedReplyCount(memParam);
		}else {
			queCnt = memberService.getMyReplyCount(memParam);
		}

		n_trec = queCnt;

		int n_pagesize = 30;
		int n_pagescnt = 5;
		int n_curpage = 1;
		int n_totalpage = 0;

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

		List<ReplyVO> myReplyList = null;

		if(queCnt > 0) {
			memParam.put("p_Option", (n_pagesize * (n_curpage-1)));
			memParam.put("n_pagesize", (1+n_pagesize+(n_pagesize * (n_curpage-1))));
			memParam.put("psrc_Cond", src_Sort);
			if(qa_Cls.equals("A")) {
				myReplyList = memberService.getMyRecivedReplyList(memParam);
			}else {
				myReplyList = memberService.getMyReplyList(memParam);
			}
		}

		String url = curPageName + "?trec=" + req_TREC + "&src_Sort=" + src_Sort + "&src_OrderBy=" + src_OrderBy + "&QA_Cls=" + qa_Cls + "&FlagMe=" + flagMe + "&CallAlarmFlag=" + callAlarmFlag+"&userWho="+userSeq;
		String paging = CommonUtil.get_Paging_Tag_New_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);
		curPageName = curPageName+"?userWho="+userSeq;
		String who = ""+userSeq;
		mav.addObject("n_trec", n_trec);
		mav.addObject("curPageName", curPageName);
		mav.addObject("n_curpage", n_curpage);
		mav.addObject("myReplyList", myReplyList);
		mav.addObject("flagMe", flagMe);
		mav.addObject("qa_Cls", qa_Cls);
		mav.addObject("paging", paging);
		mav.addObject("who", who);

		String whoNick = memberService.getNickName(userSeq);
		mav.addObject("whoNick", whoNick);

		mav.addObject("userSeq", userSeq);

		return mav;
	}

	@RequestMapping("member/myReplyDel")
	public void myReplyDel(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		String qa_Cls = request.getParameter("QA_Cls");
		String seq = request.getParameter("Seq");
		String questionSeq = request.getParameter("QuestionSeq");
		String src_Target = request.getParameter("src_Target");
		String pg = request.getParameter("pg");


		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		String msg1 = messageSource.getMessage("msg_1092", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1093", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_1094", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_1095", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_0322", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		if(userSeq == 0) {
			CommonUtil.jspAlert(response, msg1, "", ""); // msg1 활용
			return;
		}

		if(qa_Cls == null || qa_Cls == "") {
			CommonUtil.jspAlert(response, msg2, "", ""); // msg2 활용
			return;
		}

		if(seq == null || seq == "") {
			CommonUtil.jspAlert(response, msg3, "", ""); // msg3 활용
			return;
		}

		if(questionSeq == null || questionSeq == "") {
			CommonUtil.jspAlert(response, msg4, "", ""); // msg4 활용
			return;
		}

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("seq", seq);
		param.put("userSeq", userSeq);

		if(qa_Cls.equals("Q")) {
			param.put("questionSeq", questionSeq);
			replyService.deleteReplyQuestion(param);
		}
		else {
			replyService.deleteReplyAnswer(param);
		}

		CommonUtil.jspAlert(response, msg5, "/member/myReply?QA_Cls=" + qa_Cls, "parent.parent"); // msg5 활용
	}

	@RequestMapping("/member/mySiren")
	public ModelAndView mySiren(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();
		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq == 0) {
			String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용
			return null;
		}

		HashMap<String, Object> myReqSirenCount = commonService.getMyReqSirenCount(userSeq);
		HashMap<String, Object> myResSirenCount = commonService.getMyResSirenCount(userSeq);

		HashMap<String, Object> myResSirenPoint = commonService.getMyResSirenPoint(userSeq);

		int cntMy = 0;
		int cntMyIng = 0;
		int cntMyReject = 0;

		if(myReqSirenCount.get("MyComplN") != null) {
			cntMy = Integer.parseInt( String.valueOf(myReqSirenCount.get("MyComplN")) );
		}
		if(myReqSirenCount.get("MyIngN") != null) {
			cntMyIng = Integer.parseInt( String.valueOf(myReqSirenCount.get("MyIngN")) );
		}
		if(myReqSirenCount.get("MyRejectN") != null) {
			cntMyReject = Integer.parseInt( String.valueOf(myReqSirenCount.get("MyRejectN")) );
		}


		int cntMe = 0;
		int cntMeIng = 0;
		int cntMeReject = 0;

		if(myReqSirenCount.get("MeComplN") != null) {
			cntMe = Integer.parseInt( String.valueOf(myResSirenCount.get("MeComplN")) );
		}
		if(myReqSirenCount.get("MeIngN") != null) {
			cntMeIng = Integer.parseInt( String.valueOf(myResSirenCount.get("MeIngN")) );
		}
		if(myReqSirenCount.get("MeRejectN") != null) {
			cntMeReject = Integer.parseInt( String.valueOf(myResSirenCount.get("MeRejectN")) );
		}

		int mPoint = 0;
		int cntMeDanger_Qus = 0;
		int cntMeWarning_Qus = 0;
		int cntMeDanger_Ans = 0;
		int cntMeWarning_Ans = 0;
		int cntMeDanger_Repl = 0;
		int cntMeWarning_Repl = 0;
		int pntMe_Qus = 0;
		int pntMe_Ans = 0;
		int pntMe_Repl = 0;

		if(myResSirenPoint.get("ReplCntWarning") != null) {
			mPoint = Integer.parseInt( String.valueOf(myResSirenPoint.get("ReplCntWarning")) );
		}
		if(myResSirenPoint.get("QusCntDanger") != null) {
			cntMeDanger_Qus = Integer.parseInt( String.valueOf(myResSirenPoint.get("QusCntDanger")) );
		}
		if(myResSirenPoint.get("QusCntWarning") != null) {
			cntMeWarning_Qus = Integer.parseInt( String.valueOf(myResSirenPoint.get("QusCntWarning")) );
		}
		if(myResSirenPoint.get("AnsCntDanger") != null) {
			cntMeDanger_Ans = Integer.parseInt( String.valueOf(myResSirenPoint.get("AnsCntDanger")) );
		}
		if(myResSirenPoint.get("AnsCntWarning") != null) {
			cntMeWarning_Ans = Integer.parseInt( String.valueOf(myResSirenPoint.get("AnsCntWarning")) );
		}
		if(myResSirenPoint.get("ReplCntDanger") != null) {
			cntMeDanger_Repl = Integer.parseInt( String.valueOf(myResSirenPoint.get("ReplCntDanger")) );
		}
		if(myResSirenPoint.get("ReplCntWarning") != null) {
			cntMeWarning_Repl = Integer.parseInt( String.valueOf(myResSirenPoint.get("ReplCntWarning")) );
		}

		if(myResSirenPoint.get("QusMPoint") != null) {
			pntMe_Qus = Integer.parseInt( String.valueOf(myResSirenPoint.get("QusMPoint")) );
		}
		if(myResSirenPoint.get("AnsMPoint") != null) {
			pntMe_Ans = Integer.parseInt( String.valueOf(myResSirenPoint.get("AnsMPoint")) );
		}
		if(myResSirenPoint.get("ReplMPoint") != null) {
			pntMe_Repl = Integer.parseInt( String.valueOf(myResSirenPoint.get("ReplMPoint")) );
		}

		mav.addObject("cntMy", cntMy);
		mav.addObject("cntMyIng", cntMyIng);
		mav.addObject("cntMyReject", cntMyReject);
		mav.addObject("cntMe", cntMe);
		mav.addObject("cntMeIng", cntMeIng);
		mav.addObject("cntMeReject", cntMeReject);
		mav.addObject("mPoint", mPoint);
		mav.addObject("cntMeDanger_Qus", cntMeDanger_Qus);
		mav.addObject("cntMeWarning_Qus", cntMeWarning_Qus);
		mav.addObject("cntMeDanger_Ans", cntMeDanger_Ans);
		mav.addObject("cntMeWarning_Ans", cntMeWarning_Ans);
		mav.addObject("cntMeDanger_Repl", cntMeDanger_Repl);
		mav.addObject("cntMeWarning_Repl", cntMeWarning_Repl);
		mav.addObject("pntMe_Qus", pntMe_Qus);
		mav.addObject("pntMe_Ans", pntMe_Ans);
		mav.addObject("pntMe_Repl", pntMe_Repl);

		return mav;
	}

	@RequestMapping(value="/member/myFriendAjax", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String myFriendAjax(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String result = null;
		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		int maxRow = 10;
		int pg = 1;
		String strPg = request.getParameter("PG");
		String act = "";
		CodeUtil code = new CodeUtil(request);
		Map<String, String > lvCd = code.getCODE_MEM_LV_NM();

		if(strPg != null) {
			pg = Integer.parseInt(strPg);
		}

		if(request.getParameter("ACT") == null || request.getParameter("ACT") == "") {
			return null;
		}
		else {
			act = request.getParameter("ACT");
		}

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("userSeq", userSeq);
		param.put("pg", pg);
		param.put("maxRow", maxRow);

		if(act.equals("LIST_FRIEND")) {
			List<HashMap<String, Object>> list = commonService.getMyPartnerList(param);
			HashMap<String, Object> data1 = new HashMap<String, Object>();

			List<HashMap<String, Object>> data1_list = new ArrayList<HashMap<String, Object>>();
			List<HashMap<String, Object>> data2_list = new ArrayList<HashMap<String, Object>>();

			for(int i = 0; i < list.size(); i++) {
				if(i == 0) {
					JsonElement json = new Gson().toJsonTree(list.get(i));

	    			JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
	    			JSONParser jsonParse = new JSONParser();
	    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

	    	    	data1.put("cnt", jsonObj2.get("cnt"));

	    	    	data1_list.add(data1);
				}
				if(i == 1) {
					JsonElement json = new Gson().toJsonTree(list.get(i));

	    	    	for(int j = 0; j < json.getAsJsonArray().size(); j++) {
	    	    		JsonObject jsonObj = json.getAsJsonArray().get(j).getAsJsonObject();
		    			JSONParser jsonParse = new JSONParser();
		    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

	    	    		HashMap<String, Object> data2_item = new HashMap<String, Object>();

						String lvNm = lvCd.get(jsonObj2.get("Lv"));
	    	    		if(jsonObj2.get("Lv").equals("12")) { lvNm = CommonUtil.getLangMsg(request, "msg_0149"); }

	    	    		data2_item.put("FlagPartner", jsonObj2.get("FlagPartner"));
	    	    		data2_item.put("MemberSeq", jsonObj2.get("MemberSeq"));
						data2_item.put("Lv", lvNm);
	    	    		data2_item.put("NickName", jsonObj2.get("NickName"));
	    	    		data2_item.put("Photo", jsonObj2.get("Photo"));
	    	    		data2_item.put("SumQ", jsonObj2.get("SumQ"));
	    	    		data2_item.put("SumA", jsonObj2.get("SumA"));
	    	    		data2_item.put("CountC", jsonObj2.get("CountC"));
	    	    		data2_item.put("CountQ", jsonObj2.get("CountQ"));
	    	    		data2_item.put("conDate", jsonObj2.get("conDate"));

	    	    		data2_list.add(data2_item);
	    	    	}
				}
			}

			List<List<HashMap<String, Object>>> joined = new ArrayList<List<HashMap<String, Object>>>();
			joined.add(data1_list);
			joined.add(data2_list);

			result = CommonUtil.libJsonArrExit(act, joined);
		}
		else if(act.equals("LIST_BLOCK")) {
			List<HashMap<String, Object>> list = commonService.getBlockPartnerList(param);

			HashMap<String, Object> data1 = new HashMap<String, Object>();

			List<HashMap<String, Object>> data1_list = new ArrayList<HashMap<String, Object>>();
			List<HashMap<String, Object>> data2_list = new ArrayList<HashMap<String, Object>>();

			for(int i = 0; i < list.size(); i++) {
				if(i == 0) {
					JsonElement json = new Gson().toJsonTree(list.get(i));

	    			JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
	    			JSONParser jsonParse = new JSONParser();
	    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

	    	    	data1.put("cnt", jsonObj2.get("cnt"));

	    	    	data1_list.add(data1);
				}
				if(i == 1) {
					JsonElement json = new Gson().toJsonTree(list.get(i));

	    	    	for(int j = 0; j < json.getAsJsonArray().size(); j++) {
	    	    		JsonObject jsonObj = json.getAsJsonArray().get(j).getAsJsonObject();
		    			JSONParser jsonParse = new JSONParser();
		    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

	    	    		HashMap<String, Object> data2_item = new HashMap<String, Object>();

	    	    		data2_item.put("MemberSeq", jsonObj2.get("MemberSeq"));
	    	    		data2_item.put("NickName", jsonObj2.get("NickName"));
	    	    		data2_item.put("Photo", jsonObj2.get("Photo"));
	    	    		data2_item.put("SumQ", jsonObj2.get("SumQ"));
	    	    		data2_item.put("SumA", jsonObj2.get("SumA"));
	    	    		data2_item.put("CountC", jsonObj2.get("CountC"));
	    	    		data2_item.put("CountQ", jsonObj2.get("CountQ"));
	    	    		data2_item.put("conDate", jsonObj2.get("conDate"));

	    	    		data2_list.add(data2_item);
	    	    	}
				}
			}

			List<List<HashMap<String, Object>>> joined = new ArrayList<List<HashMap<String, Object>>>();
			joined.add(data1_list);
			joined.add(data2_list);

			result = CommonUtil.libJsonArrExit(act, list);
		}
		else if(act.equals("SET_BLOCK")) {
			String memberSeq = request.getParameter("MemberSeq");
			if(memberSeq == null || memberSeq == "") {
				return null;
			}

			String setBlock = request.getParameter("setBlock");
			if(setBlock == null || setBlock == "") {
				return null;
			}

			HashMap<String, Object> param2 = new HashMap<String, Object>();
			param2.put("userSeq", userSeq);
			param2.put("targetMemSeq", memberSeq);

			if(setBlock.equals("Y")) {
				commonService.setMyBlockPartner(param2);
			}
			else {
				commonService.deleteMyBlockPartner(param2);
			}

			int myBlockPartnerCount = commonService.getMyBlockPartnerCount(userSeq);

			List<HashMap<String, Object>> lst = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> res = new HashMap<String, Object>();
			res.put("MemberSeq", memberSeq);
			res.put("isBlock", setBlock);
			res.put("cnt", myBlockPartnerCount);


			lst.add(res);

			result = CommonUtil.libJsonArrExit(act, lst);
		}

		return result;
	}

	@RequestMapping("/member/myFriend")
	public ModelAndView myFriend(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq == 0) {
			String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용
			return null;
		}

		return mav;
	}

	@RequestMapping("member/deleteFriend")
	public void deleteFriend(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		String friendSeq = request.getParameter("friendSeq");
		String flagPartner = request.getParameter("FlagPartner");


		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq == 0) {
			String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용
		}
		else {
			HashMap<String, Object> param = new HashMap<String, Object>();

			param.put("userSeq", userSeq);
			param.put("friendSeq", friendSeq);
			param.put("flagPartner", flagPartner);

			commonService.deleteMyPartner(param);
		}
	}

	@RequestMapping("/member/myPartner")
	public ModelAndView myPartner(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq == 0) {
			String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용
			return null;
		}

		String curPageName = "/member/myPartner";
		String req_TREC = "";
		String req_PG = "";
		String src_Sort = "";
		String src_OrderBy = "";
		String flagPartner = "";
		String flagFollower = "";
		String callAlarmFlag = "";

		if(request.getParameter("trec") != null) {
			req_TREC = CommonUtil.fn_Word_In(request.getParameter("trec"));
		}
		if(request.getParameter("pg") != null) {
			req_PG = CommonUtil.fn_Word_In(request.getParameter("pg"));
		}
		if(request.getParameter("src_Sort") != null) {
			src_Sort = CommonUtil.fn_Word_In(request.getParameter("src_Sort"));
		}
		if(request.getParameter("src_OrderBy") != null) {
			src_OrderBy = CommonUtil.fn_Word_In(request.getParameter("src_OrderBy"));
		}
		if(request.getParameter("FlagPartner") != null) {
			flagPartner = CommonUtil.fn_Word_In(request.getParameter("FlagPartner"));
		}
		if(request.getParameter("FlagFollower") != null) {
			flagFollower = CommonUtil.fn_Word_In(request.getParameter("FlagFollower"));
		}
		if(request.getParameter("CallAlarmFlag") != null) {
			callAlarmFlag = CommonUtil.fn_Word_In(request.getParameter("CallAlarmFlag"));
		}
		if(callAlarmFlag.equals("Y")) {
			if(flagPartner.equals("M") && flagFollower.equals("T")) {
				String format = "yyyy-MM-dd HH:mm:ss";
				Calendar today = Calendar.getInstance();
				SimpleDateFormat type = new SimpleDateFormat(format);
				String now = type.format(today.getTime());

				//setAlarmLog
				CodeUtil code = new CodeUtil(request);

				HashMap<String, Object> alarmParam = new HashMap<String, Object>();
				alarmParam.put("mem_seq", userSeq);
				alarmParam.put("alarm", code.getCODE_MEM_ALARM_VIEW_FIELD_CD_MENTEE());
				alarmParam.put("dateReg", now);

				commonService.setAlarmLog(alarmParam);

				HashMap<String, Object> alarmParam2 = new HashMap<String, Object>();
				alarmParam2.put("mem_seq", userSeq);
				alarmParam2.put("alarm", code.getCODE_MEM_ALARM_VIEW_FIELD_CD_MENTEE_UNSET());
				alarmParam2.put("dateReg", now);

				commonService.setAlarmLog(alarmParam2);
			}
		}

		if(flagPartner.equals("")) {
			flagPartner = "F";
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

		int mentoCnt = 0;

		HashMap<String, Object> memParam = new HashMap<String, Object>();
		memParam.put("userSeq", userSeq);
		memParam.put("flagPartner", flagPartner);

		if(flagFollower.equals("")) {
			mentoCnt = commonService.getMyMentorCount(memParam);
		}
		else {
			mentoCnt = commonService.getMyFollowerCount(memParam);
		}

		n_trec = mentoCnt;

		int n_pagesize = 30;
		int n_pagescnt = 10;
		int n_curpage = 1;
		int n_totalpage = 0;

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

		List<PartnerVO> lsit = null;

		if(mentoCnt > 0) {
			memParam.put("p_Option", (n_pagesize * n_curpage));

			if(flagFollower.equals("")) {
				lsit = commonService.getMyMentorList(memParam);
			}
			else {
				lsit = commonService.getMyFollowerList(memParam);
			}
		}

		String url = curPageName + "?trec=" + req_TREC + "&src_Sort=" + src_Sort + "&src_OrderBy=" + src_OrderBy + "&FlagPartner=" + flagPartner +"&FlagFollower=" + flagFollower +  "&CallAlarmFlag=" + callAlarmFlag;
		String paging = CommonUtil.get_Paging_Tag_New_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);

		mav.addObject("n_trec", n_trec);
		mav.addObject("curPageName", curPageName);
		mav.addObject("n_curpage", n_curpage);
		mav.addObject("flagPartner", flagPartner);
		mav.addObject("flagFollower", flagFollower);
		mav.addObject("lsit", lsit);
		mav.addObject("paging", paging);

		return mav;
	}

	@RequestMapping("/member/myRecommend")
	public ModelAndView myRecommend(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		int userLv = cookieLoginService.getCookieUserLv(request, response);
		String userDateReg = cookieLoginService.getCookieUserDateReg(request, response);

		if(userSeq == 0) {
			String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용
			return null;
		}

		String callAlarmFlag = "";
		if(request.getParameter("CallAlarmFlag") != null) {
			callAlarmFlag = CommonUtil.fn_Word_In(request.getParameter("CallAlarmFlag"));
		}
		if(callAlarmFlag == "Y") {
			String format = "yyyy-MM-dd HH:mm:ss";
			Calendar today = Calendar.getInstance();
			SimpleDateFormat type = new SimpleDateFormat(format);
			String now = type.format(today.getTime());
			CodeUtil code = new CodeUtil(request);

			HashMap<String, Object> alarmParam = new HashMap<String, Object>();
			alarmParam.put("mem_seq", userSeq);
			alarmParam.put("alarm", code.getCODE_MEM_ALARM_VIEW_FIELD_CD_RECOMM_MEM_JOIN());
			alarmParam.put("dateReg", now);

			commonService.setAlarmLog(alarmParam);
		}

		int memberSeq = 0;
		String memberNickName = "";
		String recommendCode = "";

		if(userSeq == 99 && request.getParameter("MemberSeq") != "") {
			memberSeq = Integer.parseInt( request.getParameter("MemberSeq") );

			if(memberSeq > 0) {
				memberNickName = memberService.getNickName(memberSeq);
			}
		}

		if(memberSeq == 0) {
			memberSeq = userSeq;
			memberNickName = cookieLoginService.getCookieUserNickName(request, response);

			recommendCode = String.valueOf(memberSeq).substring(0, 4) + "-" + String.valueOf(memberSeq).substring(String.valueOf(memberSeq).length() - 4, String.valueOf(memberSeq).length());
		}

		//나의 피추천인
		String recomNick = "";
		recomNick = memberService.getMyRecommendNick(memberSeq);

		//나의 추천인
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("memberSeq", memberSeq);
		param.put("userLv", userLv);

		List<MemberVO> member = memberService.getMyRecommendList(param);

		mav.addObject("memberNickName", memberNickName);
		mav.addObject("recommendCode", recommendCode);
		mav.addObject("userLv", userLv);
		mav.addObject("userDateReg", userDateReg);
		mav.addObject("recomNick", recomNick);
		mav.addObject("recomCnt", member.size());
		mav.addObject("member", member);

		return mav;
	}

	@RequestMapping("/member/myAnswerer")
	public ModelAndView myAnswerer(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq == 0) {
			String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용
			return null;
		}

		int answererCnt = commonService.getAnswererCnt(userSeq);

		List<MemberVO> answererList = null;

		try {
			answererList = commonService.getAnswererInfo(userSeq);
		}
		catch(ClassCastException e) {
			answererList = null;
		}
		catch(TooManyResultsException e) {
			answererList = null;
		}

		String masterName = memberService.getUserSname(userSeq);

		BigDecimal answererMoney = commonService.getEarnAnswerer(userSeq);

		mav.addObject("answererCnt", answererCnt);
		mav.addObject("answererList", answererList);
		mav.addObject("masterName", masterName);
		mav.addObject("answererMoney", answererMoney);

		return mav;
	}

	@RequestMapping("interest/myInterest")
	public ModelAndView myInterest(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq == 0) {
			String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용
			return null;
		}

		List<HashMap<String, Object>> interestList = commonService.getInterestList(userSeq);

		HashMap<String, Object> localeItem = CommonUtil.getLocale(locale);
    	String lang = String.valueOf(localeItem.get("lang"));

		mav.addObject("interestList", interestList);
		mav.addObject("userSeq", userSeq);
		mav.addObject("lang", lang);

		return mav;
	}

	@RequestMapping(value="interest/addInterest", method = RequestMethod.POST)
	public String addInterest(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		String result = "";

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq == 0) {
			String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용
		}
		else {
			String[] code = new String[6];
			String code1 = "";
			String code2 = "";
			String code3 = "";
			String code4 = "";
			String code5 = "";

			if(request.getParameter("Code1") != null) {
				code1 = request.getParameter("Code1");
			}
			if(request.getParameter("Code2") != null) {
				code2 = request.getParameter("Code2");
			}
			if(request.getParameter("Code3") != null) {
				code3 = request.getParameter("Code3");
			}
			if(request.getParameter("Code4") != null) {
				code4 = request.getParameter("Code4");
			}
			if(request.getParameter("Code5") != null) {
				code5 = request.getParameter("Code5");
			}

			for(int i = 1; i < 6; i++) {
				//code[i] =
				switch(i) {
					case 1:
						code[1] = code1;
						break;
					case 2:
						code[2] = code2;
						break;
					case 3:
						code[3] = code3;
						break;
					case 4:
						code[4] = code4;
						break;
					case 5:
						code[5] = code5;
						break;
				}
			}

			List<HashMap<String, Object>> myFavorite = commonService.getMyFavorite(userSeq);

			boolean check = false;
			boolean check1 = true;
			boolean check2 = true;
			boolean check3 = true;
			boolean check4 = true;
			boolean check5 = true;

			if(myFavorite.size() > 0) {
				String[] secCmp = new String[6];

				for(int i = 0; i < myFavorite.size(); i++) {
					for(int j = 1; j <= 5; j++) {
						if(j == 1) secCmp[j] = myFavorite.get(i).get("Section1").toString();
						if(j == 2) secCmp[j] = myFavorite.get(i).get("Section2").toString();
						if(j == 3) secCmp[j] = myFavorite.get(i).get("Section3").toString();
						if(j == 4) secCmp[j] = myFavorite.get(i).get("Section4").toString();
						if(j == 5) secCmp[j] = myFavorite.get(i).get("Section5").toString();
					}

					if(!code[1].equals("") && !secCmp[1].equals(code[1]) ) check1 = false;
					else check1 = true;
					if(!code[2].equals("") && !secCmp[2].equals(code[2]) ) check2 = false;
					else check2 = true;
					if(!code[3].equals("") && !secCmp[3].equals(code[3]) ) check3 = false;
					else check3 = true;
					if(!code[4].equals("") && !secCmp[4].equals(code[4]) ) check4 = false;
					else check4 = true;
					if(!code[5].equals("") && !secCmp[5].equals(code[5]) ) check5 = false;
					else check5 = true;

					if(check1 == true && check2 == true && check3 == true && check4 == true && check5 == true) {
						result = "already";
						check = true;
						break;
					}
				}
			}
			//System.out.println("check : " + check);
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("seq", userSeq);
			param.put("section1", code[1]);
			param.put("section2", code[2]);
			param.put("section3", code[3]);
			param.put("section4", code[4]);
			param.put("section5", code[5]);

			result += "<br/>";

			if(check == false) {
				commonService.setMyInterest(param);
			}
		}
		return result;
	}

	@RequestMapping("/member/myTemp")
	public ModelAndView myTemp(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq == 0) {
			String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용
		}

		String curPageName = "/member/myTemp";
		String req_TREC = "";
		String req_PG = "";
		String src_Sort = "";
		String src_OrderBy = "";
		String flagSelect = "";
		int flagSelectCode = 0;
		String section1 = "0";


		if(request.getParameter("trec") != null) {
			req_TREC = CommonUtil.fn_Word_In(request.getParameter("trec"));
		}
		if(request.getParameter("pg") != null) {
			req_PG = CommonUtil.fn_Word_In(request.getParameter("pg"));
		}
		if(request.getParameter("src_Sort") != null) {
			src_Sort = CommonUtil.fn_Word_In(request.getParameter("src_Sort"));
		}
		if(request.getParameter("src_OrderBy") != null) {
			src_OrderBy = CommonUtil.fn_Word_In(request.getParameter("src_OrderBy"));
		}
		if(request.getParameter("FlagSelect") != null) {
			flagSelect = CommonUtil.fn_Word_In(request.getParameter("FlagSelect"));
		}

		if(flagSelect.equals("")) {
			flagSelect = "ALL";
		}

		if(flagSelect.equals("ALL")) {
			flagSelectCode = 0;
		}
		else if(flagSelect.equals("Q")) {
			flagSelectCode = 1;
		}
		else {
			flagSelectCode = 2;
		}



		if(req_PG == "") {
			req_PG = "1";
		}

		if(src_Sort == "") {
			src_Sort = "Seq";
		}

		if(src_OrderBy == "") {
			src_OrderBy = "DESC";
		}

		int n_trec = 0;


		int n_pagesize = 30;
		int n_pagescnt = 10;
		int n_curpage = 1;
		int n_totalpage = 0;

		//SP2_MY_TEMP_QA_GET
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("flag", flagSelectCode);
		param.put("page_num", req_PG);
		param.put("page_size", n_pagesize);
		param.put("userSeq", userSeq);

		List<HashMap<String, Object>> myTempList = commonService.getMyTempList(param);

		List<HashMap<String, Object>> data_list = new ArrayList<HashMap<String, Object>>();

		for(int i = 0; i < myTempList.size(); i++) {
			if(i == 0) {
				JsonElement json = new Gson().toJsonTree(myTempList.get(i));

    			JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
    			JSONParser jsonParse = new JSONParser();
    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

    	    	n_trec = Integer.parseInt( String.valueOf( jsonObj2.get("COUNT") ) );
			}
			if(i == 1) {
				JsonElement json = new Gson().toJsonTree(myTempList.get(i));

    	    	for(int j = 0; j < json.getAsJsonArray().size(); j++) {
    	    		JsonObject jsonObj = json.getAsJsonArray().get(j).getAsJsonObject();
	    			JSONParser jsonParse = new JSONParser();
	    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

    	    		HashMap<String, Object> data1 = new HashMap<String, Object>();

    	    		String seq = String.valueOf( jsonObj2.get("Seq") );
    	    		String imgSrc = "";
    	    		String answerSeq = "";
    	    		String questionSeq = "";
    	    		String title = String.valueOf( jsonObj2.get("Title") );
    	    		title = title.replaceAll("<br>", " ").replaceAll("<br", "").replaceAll("<b", " ").replaceAll("<", " ");

    	    		String flag = String.valueOf( jsonObj2.get("Flag") );
    	    		String linkUrl = "";

    	    		if(flag.equals("Q")) {
    	    			linkUrl = "/question/questionWrite?QuestionSeq=" + seq;
    	    			imgSrc = "question";
    	    			questionSeq = seq;
    	    		}
    	    		else if(flag.equals("A")) {
    	    			if(title.length() > 60) {
    	    				title = title.substring(0, 60) + "..";
    	    			}
    	    			questionSeq = answerService.getQuestionSeq(Integer.parseInt(seq));
    	    			answerSeq = seq;

    	    			linkUrl = "/answer/answerWrite?answerSeq=" + seq + "&QuestionSeq" + questionSeq + "&CurPageName=questionList";
    	    			imgSrc = "answer";
    	    		}

    	    		data1.put("seq", seq);
    	    		data1.put("title", title);
    	    		data1.put("almoney", jsonObj2.get("Almoney"));
    	    		data1.put("readCount", jsonObj2.get("ReadCount"));
    	    		data1.put("dateReg", jsonObj2.get("DateReg"));
    	    		data1.put("answerCount", jsonObj2.get("AnswerCount"));
    	    		data1.put("flag", flag);

    	    		data1.put("answerSeq", answerSeq);
    	    		data1.put("questionSeq", questionSeq);
    	    		data1.put("linkUrl", linkUrl);
    	    		data1.put("imgSrc", imgSrc);

    	    		data_list.add(data1);
    	    	}
			}
		}

		if(CommonUtil.isNumeric(req_PG) == true && req_PG.length() > 0) {
			n_curpage = Integer.parseInt(req_PG);
		}

		n_totalpage = Math.round(n_trec / n_pagesize);

		if(n_trec % n_pagesize > 0) {
			n_totalpage = n_totalpage + 1;
		}

		if(n_curpage <= 0) {
			n_curpage = 1;
		}
		else if(n_curpage > n_totalpage) {
			n_curpage = n_totalpage;
		}

		String url = curPageName + "?trec=" + req_TREC + "&src_Sort=" + src_Sort + "&src_OrderBy=" + src_OrderBy + "&FlagSelect=" + flagSelect;
		String paging = CommonUtil.get_Paging_Tag_New_ver2(n_pagescnt, n_curpage, n_totalpage, "frm_sch", url);

		mav.addObject("curPageName", curPageName);
		mav.addObject("n_curpage", n_curpage);
		mav.addObject("n_trec", n_trec);
		mav.addObject("flagSelect", flagSelect);
		mav.addObject("paging", paging);
		mav.addObject("list", data_list);

		return mav;
	}

	@RequestMapping("/member/myBank")
	public ModelAndView myBank(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		//HttpSession session = request.getSession(true);


		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);


		if(userSeq == 0) {
			String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용
		}

		String flagBank = "";
		String Import = "";
		String Expense = "";
		String callAlarmFlag = "";
		String searchDateS = "";
		String searchDateE = "";

		if(request.getParameter("CallAlarmFlag") != null) {
			callAlarmFlag = request.getParameter("CallAlarmFlag");

			if(callAlarmFlag.equals("Y")) {
				String format = "yyyy-MM-dd HH:mm:ss";
				Calendar today = Calendar.getInstance();
				SimpleDateFormat type = new SimpleDateFormat(format);
				String now = type.format(today.getTime());

				//setAlarmLog
				CodeUtil code = new CodeUtil(request);

				HashMap<String, Object> alarmParam = new HashMap<String, Object>();
				alarmParam.put("mem_seq", userSeq);
				alarmParam.put("alarm", code.getCODE_MEM_ALARM_VIEW_FIELD_CD_ALMONEY_INCOME());
				alarmParam.put("dateReg", now);

				commonService.setAlarmLog(alarmParam);

				HashMap<String, Object> alarmParam2 = new HashMap<String, Object>();
				alarmParam2.put("mem_seq", userSeq);
				alarmParam2.put("alarm", code.getCODE_MEM_ALARM_VIEW_FIELD_CD_ALMONEY_PAYING());
				alarmParam2.put("dateReg", now);
				commonService.setAlarmLog(alarmParam2);
			}
		}

		if(request.getParameter("FlagBank") != null) {
			flagBank = request.getParameter("FlagBank");
		}

		if(flagBank.equals("")) {
			flagBank = "I";
		}




		HashMap<String, Object> almoneyLogTotal = memberService.getMyAlmoneyLogTotal(userSeq);

		Import = String.valueOf(almoneyLogTotal.get("Import"));
		Expense = String.valueOf(almoneyLogTotal.get("Expense"));

		if(Import.equals("")) {
			Import = "0";
		}

		if(Expense.equals("")) {
			Expense = "0";
		}

		if(request.getParameter("SearchDateS") != null) {
			searchDateS = request.getParameter("SearchDateS");
		}
		if(request.getParameter("SearchDateE") != null) {
			searchDateE = request.getParameter("SearchDateE");
		}


		String format = "yyyy-MM-dd";
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String now = type.format(today.getTime());

		if(searchDateS.equals("")) {
			searchDateS = now;
		}

		if(searchDateE.equals("")) {
			searchDateE = now;
		}


		MemberVO member = memberService.getAlmoneyMyJoin(userSeq);

		//session.setAttribute("UserAlmoney", member.getAlmoney());

		mav.addObject("userLv", userLv);
		mav.addObject("Import", Import);
		mav.addObject("Expense", Expense);
		mav.addObject("flagBank", flagBank);
		mav.addObject("UserAlmoney", member.getAlmoney());
		mav.addObject("searchDateS", searchDateS);
		mav.addObject("searchDateE", searchDateE);

		return mav;
	}

	@RequestMapping("/member/getBankReport")
	public ModelAndView getBankReport(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq == 0) {
			return null;
		}

		String searchDateS = "";
		String searchDateE = "";

		if(request.getParameter("SearchDateS") != null) {
			searchDateS = request.getParameter("SearchDateS");
		}
		if(request.getParameter("SearchDateE") != null) {
			searchDateE = request.getParameter("SearchDateE");
		}

		if(searchDateS.equals("")) {
			searchDateS = "1997-01-01";
		}

		String format = "yyyy-MM-dd";
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String now = type.format(today.getTime());

		if(searchDateS.equals("")) {
			searchDateS = "1997-01-01";
		}

		if(searchDateE.equals("")) {
			searchDateE = now;
		}

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("userSeq", userSeq);
		param.put("searchDateS", searchDateS);
		param.put("searchDateE", searchDateE);

		LogAlmoneyVO alg = commonService.getAlmoneyLogSum(param);

		BigDecimal sumTotal = new BigDecimal(0.00);
		BigDecimal zeroDecimal = new BigDecimal(0.00);

		BigDecimal sumJoin = alg.getSumJoin();
		BigDecimal sumAnswer = alg.getSumAnswer();
		BigDecimal sumViewQ = alg.getSumViewQ();
		BigDecimal sumViewA = alg.getSumViewA();
		BigDecimal sumViewRQ = alg.getSumViewRQ();
		BigDecimal sumViewRA = alg.getSumViewRA();
		BigDecimal sumEsti = alg.getSumEsti();
		BigDecimal sumRefund = alg.getSumRefund();
 		BigDecimal sumEvent = alg.getSumEvent();
 		BigDecimal sumEtc = alg.getSumEtc();


 		sumTotal = sumTotal.add(sumJoin).add(sumAnswer).add(sumViewQ).add(sumViewA).add(sumViewRQ).add(sumViewRA).add(sumEsti).add(sumRefund).add(sumEvent).add(sumEtc);


 		BigDecimal rateJoin = new BigDecimal(0.0);
 		BigDecimal rateAnswer = new BigDecimal(0.0);
 		BigDecimal rateViewQ = new BigDecimal(0.0);
 		BigDecimal rateViewA = new BigDecimal(0.0);
 		BigDecimal rateViewRQ = new BigDecimal(0.0);
 		BigDecimal rateViewRA = new BigDecimal(0.0);
 		BigDecimal rateEsti = new BigDecimal(0.0);
 		BigDecimal rateRefund = new BigDecimal(0.0);
 		BigDecimal rateEvent = new BigDecimal(0.0);
 		BigDecimal rateEtc = new BigDecimal(0.0);

 		BigDecimal rate100 = new BigDecimal(100.0);

 		if(sumTotal.compareTo(zeroDecimal) != 0) {
 			rateJoin = sumJoin.divide(sumTotal, 2, BigDecimal.ROUND_CEILING).multiply(rate100);
 			rateAnswer = sumAnswer.divide(sumTotal, 2, BigDecimal.ROUND_CEILING).multiply(rate100);
 			rateViewQ = sumViewQ.divide(sumTotal, 2, BigDecimal.ROUND_CEILING).multiply(rate100);
 			rateViewA = sumViewA.divide(sumTotal, 2, BigDecimal.ROUND_CEILING).multiply(rate100);
 			rateViewRQ = sumViewRQ.divide(sumTotal, 2, BigDecimal.ROUND_CEILING).multiply(rate100);
 			rateViewRA = sumViewRA.divide(sumTotal, 2, BigDecimal.ROUND_CEILING).multiply(rate100);
 			rateEsti = sumEsti.divide(sumTotal, 2, BigDecimal.ROUND_CEILING).multiply(rate100);
 			rateRefund = sumRefund.divide(sumTotal, 2, BigDecimal.ROUND_CEILING).multiply(rate100);
 			rateEvent = sumEvent.divide(sumTotal, 2, BigDecimal.ROUND_CEILING).multiply(rate100);
 			rateEtc = sumEtc.divide(sumTotal, 2, BigDecimal.ROUND_CEILING).multiply(rate100);
 		}

 		BigDecimal sumWithdraw = alg.getSumWithdraw();
 		BigDecimal sumView = alg.getSumView();
 		BigDecimal sumQuestion = alg.getSumQuestion();



 		sumTotal = new BigDecimal(0.00); // redim

 		sumTotal = sumTotal.add(sumWithdraw).add(sumView).add(sumQuestion);


 		BigDecimal rateWithdraw = new BigDecimal(0.0);
 		BigDecimal rateQuestion = new BigDecimal(0.0);
 		BigDecimal rateView = new BigDecimal(0.0);

 		if(sumTotal.compareTo(new BigDecimal(0.00)) != 0) {
 			rateWithdraw = sumWithdraw.divide(sumTotal, 2, BigDecimal.ROUND_CEILING).multiply(rate100);
 			rateQuestion = sumView.divide(sumTotal, 2, BigDecimal.ROUND_CEILING).multiply(rate100);
 			rateView = sumQuestion.divide(sumTotal, 2, BigDecimal.ROUND_CEILING).multiply(rate100);
 		}

 		mav.addObject("sumJoin", sumJoin);
 		mav.addObject("sumAnswer", sumAnswer);
 		mav.addObject("sumViewA", sumViewA);
 		mav.addObject("sumViewQ", sumViewQ);
 		mav.addObject("sumViewRQ", sumViewRQ);
 		mav.addObject("sumViewRA", sumViewRA);
 		mav.addObject("sumEsti", sumEsti);
 		mav.addObject("sumRefund", sumRefund);
 		mav.addObject("sumEvent", sumEvent);
 		mav.addObject("sumEtc", sumEtc);
 		mav.addObject("sumWithdraw", sumWithdraw);
 		mav.addObject("sumQuestion", sumQuestion);
 		mav.addObject("sumView", sumView);
 		mav.addObject("zeroDecimal", zeroDecimal);

 		mav.addObject("rateJoin", rateJoin);
 		mav.addObject("rateAnswer", rateAnswer);
 		mav.addObject("rateViewA", rateViewA);
 		mav.addObject("rateViewQ", rateViewQ);
 		mav.addObject("rateViewRQ", rateViewRQ);
 		mav.addObject("rateViewRA", rateViewRA);
 		mav.addObject("rateEsti", rateEsti);
 		mav.addObject("rateEvent", rateEvent);
 		mav.addObject("rateEtc", rateEtc);
 		mav.addObject("rateWithdraw", rateWithdraw);
 		mav.addObject("rateQuestion", rateQuestion);
 		mav.addObject("rateView", rateView);

		return mav;
	}

	@RequestMapping(value="/member/getBankReport_Admin", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView getBankReport_Admin(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		String memberSeq = "";

		if(userLv == 99) {
			memberSeq = String.valueOf(request.getParameter("MemberSeq"));
		}
		else {
			memberSeq = String.valueOf( userSeq );
		}
		//memberSeq = "10000076";

		String searchDateS = "";
		String searchDateE = "";

		if(request.getParameter("SearchDateS") != null) {
			searchDateS = request.getParameter("SearchDateS");
		}
		if(request.getParameter("SearchDateE") != null) {
			searchDateE = request.getParameter("SearchDateE");
		}

		if(searchDateS.equals("")) {
			searchDateS = "1997-01-01";
		}

		String format = "yyyy-MM-dd";
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String now = type.format(today.getTime());

		if(searchDateS.equals("")) {
			searchDateS = "1997-01-01";
		}

		if(searchDateE.equals("")) {
			searchDateE = now;
		}

		userSeq = Integer.parseInt(memberSeq);
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("userSeq", userSeq);
		param.put("searchDateS", searchDateS);
		param.put("searchDateE", searchDateE);

		LogAlmoneyVO alg = commonService.getAlmoneyLogSum(param);

		BigDecimal sumTotal = new BigDecimal(0.0000);
		BigDecimal zeroDecimal = new BigDecimal(0.0000);

		BigDecimal sumJoin = alg.getSumJoin();
		BigDecimal sumAnswer = alg.getSumAnswer();
		BigDecimal sumViewQ = alg.getSumViewQ();
		BigDecimal sumViewA = alg.getSumViewA();
		BigDecimal sumViewRQ = alg.getSumViewRQ();
		BigDecimal sumViewRA = alg.getSumViewRA();
		BigDecimal sumEsti = alg.getSumEsti();
		BigDecimal sumRefund = alg.getSumRefund();
 		BigDecimal sumEvent = alg.getSumEvent();
 		BigDecimal sumEtc = alg.getSumEtc();


 		sumTotal = sumTotal.add(sumJoin).add(sumAnswer).add(sumViewQ).add(sumViewA).add(sumViewRQ).add(sumViewRA).add(sumEsti).add(sumRefund).add(sumEvent).add(sumEtc);

 		BigDecimal rateJoin = new BigDecimal(0.0);
 		BigDecimal rateAnswer = new BigDecimal(0.0);
 		BigDecimal rateViewQ = new BigDecimal(0.0);
 		BigDecimal rateViewA = new BigDecimal(0.0);
 		BigDecimal rateViewRQ = new BigDecimal(0.0);
 		BigDecimal rateViewRA = new BigDecimal(0.0);
 		BigDecimal rateEsti = new BigDecimal(0.0);
 		BigDecimal rateRefund = new BigDecimal(0.0);
 		BigDecimal rateEvent = new BigDecimal(0.0);
 		BigDecimal rateEtc = new BigDecimal(0.0);

 		BigDecimal rate100 = new BigDecimal(100.0);

 		if(sumTotal.compareTo(zeroDecimal) != 0) {
 			rateJoin = sumJoin.divide(sumTotal, 2, BigDecimal.ROUND_CEILING).multiply(rate100);
 			rateAnswer = sumAnswer.divide(sumTotal, 2, BigDecimal.ROUND_CEILING).multiply(rate100);
 			rateViewQ = sumViewQ.divide(sumTotal, 2, BigDecimal.ROUND_CEILING).multiply(rate100);
 			rateViewA = sumViewA.divide(sumTotal, 2, BigDecimal.ROUND_CEILING).multiply(rate100);
 			rateViewRQ = sumViewRQ.divide(sumTotal, 2, BigDecimal.ROUND_CEILING).multiply(rate100);
 			rateViewRA = sumViewRA.divide(sumTotal, 2, BigDecimal.ROUND_CEILING).multiply(rate100);
 			rateEsti = sumEsti.divide(sumTotal, 2, BigDecimal.ROUND_CEILING).multiply(rate100);
 			rateRefund = sumRefund.divide(sumTotal, 2, BigDecimal.ROUND_CEILING).multiply(rate100);
 			rateEvent = sumEvent.divide(sumTotal, 2, BigDecimal.ROUND_CEILING).multiply(rate100);
 			rateEtc = sumEtc.divide(sumTotal, 2, BigDecimal.ROUND_CEILING).multiply(rate100);
 		}

 		BigDecimal sumWithdraw = alg.getSumWithdraw();
 		BigDecimal sumView = alg.getSumView();
 		BigDecimal sumQuestion = alg.getSumQuestion();



 		sumTotal = new BigDecimal(0.00); // redim

 		sumTotal = sumTotal.add(sumWithdraw).add(sumView).add(sumQuestion);


 		BigDecimal rateWithdraw = new BigDecimal(0.0);
 		BigDecimal rateQuestion = new BigDecimal(0.0);
 		BigDecimal rateView = new BigDecimal(0.0);

 		if(sumTotal.compareTo(new BigDecimal(0.00)) != 0) {
 			rateWithdraw = sumWithdraw.divide(sumTotal, 2, BigDecimal.ROUND_CEILING).multiply(rate100);
 			rateQuestion = sumView.divide(sumTotal, 2, BigDecimal.ROUND_CEILING).multiply(rate100);
 			rateView = sumQuestion.divide(sumTotal, 2, BigDecimal.ROUND_CEILING).multiply(rate100);
 		}

 		mav.addObject("sumJoin", sumJoin);
 		mav.addObject("sumAnswer", sumAnswer);
 		mav.addObject("sumViewA", sumViewA);
 		mav.addObject("sumViewQ", sumViewQ);
 		mav.addObject("sumViewRQ", sumViewRQ);
 		mav.addObject("sumViewRA", sumViewRA);
 		mav.addObject("sumEsti", sumEsti);
 		mav.addObject("sumRefund", sumRefund);
 		mav.addObject("sumEvent", sumEvent);
 		mav.addObject("sumEtc", sumEtc);
 		mav.addObject("sumWithdraw", sumWithdraw);
 		mav.addObject("sumQuestion", sumQuestion);
 		mav.addObject("sumView", sumView);
 		mav.addObject("zeroDecimal", zeroDecimal);

 		mav.addObject("rateJoin", rateJoin);
 		mav.addObject("rateAnswer", rateAnswer);
 		mav.addObject("rateViewA", rateViewA);
 		mav.addObject("rateViewQ", rateViewQ);
 		mav.addObject("rateViewRQ", rateViewRQ);
 		mav.addObject("rateViewRA", rateViewRA);
 		mav.addObject("rateEsti", rateEsti);
 		mav.addObject("rateEvent", rateEvent);
 		mav.addObject("rateEtc", rateEtc);
 		mav.addObject("rateWithdraw", rateWithdraw);
 		mav.addObject("rateQuestion", rateQuestion);
 		mav.addObject("rateView", rateView);

		return mav;
	}

	@RequestMapping(value="/member/getBankList", method = RequestMethod.POST)
	public ModelAndView getBankList(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq == 0) {
			return null;
		}

		String searchDateS = "";
		String searchDateE = "";
		int pageSize = 0;
		int pageNum = 0;

		if(request.getParameter("SearchDateS") != null) {
			searchDateS = CommonUtil.fn_Word_In( request.getParameter("SearchDateS") );
		}
		if(request.getParameter("SearchDateE") != null) {
			searchDateE = CommonUtil.fn_Word_In( request.getParameter("SearchDateE") );
		}
		if(request.getParameter("PageSize") != null) {
			pageSize = Integer.parseInt( CommonUtil.fn_Word_In( request.getParameter("PageSize") ) );
		}
		if(request.getParameter("PageNum") != null) {
			pageNum = Integer.parseInt( CommonUtil.fn_Word_In( request.getParameter("PageNum") ) );
		}

		if(searchDateS.equals("")) {
			searchDateS = "1997-01-01";
		}
		if(searchDateE.equals("")) {
			String format = "yyyy-MM-dd";
			Calendar today = Calendar.getInstance();
			SimpleDateFormat type = new SimpleDateFormat(format);
			String now = type.format(today.getTime());

			searchDateE = now;
		}
		if(pageSize == 0) {
			pageSize = 50;
		}
		if(pageNum == 0) {
			pageNum = 1;
		}

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("userSeq", userSeq);
		param.put("st_date", searchDateS);
		param.put("end_date", searchDateE);
		param.put("page_size", pageSize);
		param.put("page_num", pageNum);

		List<HashMap<String, Object>> myBankList = commonService.getMyBankDataSP(param);
		List<HashMap<String, Object>> data_list = new ArrayList<HashMap<String, Object>>();

		int listCount = 0;

		for(int i = 0; i < myBankList.size(); i++) {
			if(i == 0) {
				JsonElement json = new Gson().toJsonTree(myBankList.get(i));

    			JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
    			JSONParser jsonParse = new JSONParser();
    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

    	    	listCount = Integer.parseInt( String.valueOf( jsonObj2.get("Count") ) );
			}
			if(i == 1) {
				JsonElement json = new Gson().toJsonTree(myBankList.get(i));

				for(int j = 0; j < json.getAsJsonArray().size(); j++) {
					JsonObject jsonObj = json.getAsJsonArray().get(j).getAsJsonObject();
	    			JSONParser jsonParse = new JSONParser();
	    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

	    	    	HashMap<String, Object> data1 = new HashMap<String, Object>();

	    	    	data1.put("contentsSeq", jsonObj2.get("ContentsSeq"));
	    	    	data1.put("tradeType", jsonObj2.get("TradeType"));
	    	    	data1.put("almoney", jsonObj2.get("Almoney"));
	    	    	data1.put("balance", jsonObj2.get("Balance"));
	    	    	data1.put("regdate", jsonObj2.get("conRegdate"));
	    	    	data_list.add(data1);
				}
			}

		}

		mav.addObject("listCount", listCount);
		mav.addObject("myBankCnt", data_list.size());
		mav.addObject("myBank", data_list);

		return mav;
	}

	@RequestMapping(value="/member/getBankList_Admin", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView getBankList_Admin(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		String searchDateS = "";
		String searchDateE = "";
		int pageSize = 0;
		int pageNum = 0;

		String memberSeq = "";

		if(userLv == 99) {
			memberSeq = String.valueOf(request.getParameter("MemberSeq"));
		}
		else {
			memberSeq = String.valueOf( userSeq );
		}

		if(request.getParameter("SearchDateS") != null) {
			searchDateS = CommonUtil.fn_Word_In( request.getParameter("SearchDateS") );
		}
		if(request.getParameter("SearchDateE") != null) {
			searchDateE = CommonUtil.fn_Word_In( request.getParameter("SearchDateE") );
		}
		if(request.getParameter("PageSize") != null) {
			pageSize = Integer.parseInt( CommonUtil.fn_Word_In( request.getParameter("PageSize") ) );
		}
		if(request.getParameter("PageNum") != null) {
			pageNum = Integer.parseInt( CommonUtil.fn_Word_In( request.getParameter("PageNum") ) );
		}

		if(searchDateS.equals("")) {
			searchDateS = "1997-01-01";
		}
		if(searchDateE.equals("")) {
			String format = "yyyy-MM-dd";
			Calendar today = Calendar.getInstance();
			SimpleDateFormat type = new SimpleDateFormat(format);
			String now = type.format(today.getTime());

			searchDateE = now;
		}
		if(pageSize == 0) {
			pageSize = 50;
		}
		if(pageNum == 0) {
			pageNum = 1;
		}

		userSeq = Integer.parseInt(memberSeq);
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("userSeq", userSeq);
		param.put("st_date", searchDateS);
		param.put("end_date", searchDateE);
		param.put("page_size", pageSize);
		param.put("page_num", pageNum);

		List<HashMap<String, Object>> myBankList = commonService.getMyBankDataSP(param);
		List<HashMap<String, Object>> data_list = new ArrayList<HashMap<String, Object>>();

		int listCount = 0;

		for(int i = 0; i < myBankList.size(); i++) {
			if(i == 0) {
				JsonElement json = new Gson().toJsonTree(myBankList.get(i));

    			JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
    			JSONParser jsonParse = new JSONParser();
    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

    	    	listCount = Integer.parseInt( String.valueOf( jsonObj2.get("Count") ) );
			}
			if(i == 1) {
				JsonElement json = new Gson().toJsonTree(myBankList.get(i));

				for(int j = 0; j < json.getAsJsonArray().size(); j++) {
					JsonObject jsonObj = json.getAsJsonArray().get(j).getAsJsonObject();
	    			JSONParser jsonParse = new JSONParser();
	    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

	    	    	HashMap<String, Object> data1 = new HashMap<String, Object>();

	    	    	data1.put("contentsSeq", jsonObj2.get("ContentsSeq"));
	    	    	data1.put("tradeType", jsonObj2.get("TradeType"));
	    	    	data1.put("almoney", jsonObj2.get("Almoney"));
	    	    	data1.put("balance", jsonObj2.get("Balance"));
	    	    	data1.put("regdate", jsonObj2.get("conRegdate"));
	    	    	data_list.add(data1);
				}
			}

		}

		mav.addObject("listCount", listCount);
		mav.addObject("myBankCnt", data_list.size());
		mav.addObject("myBank", data_list);

		return mav;
	}

	@RequestMapping(value="account/exchange/exchangeAsk", method = RequestMethod.GET)
	public ModelAndView exchangeAsk(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		int userSeq = cookieLoginService.getCookieUserSeq(request, response);;
		int q_userSeq = 0;
		int userLevel = cookieLoginService.getCookieUserLv(request, response);


		if(userSeq == 0) {
			CommonUtil.jspAlert(response, "나비천사 이상의 등급부터 사용할 수 있습니다.", "back", "parent.parent");

			return null;
		}
		String msg1 = messageSource.getMessage("msg_1096", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1097", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		if(userLevel < 2) {
			CommonUtil.jspAlert(response, msg1, "back", "parent.parent"); // msg1 활용

			return null;
		}


		List<HashMap<String, Object>> myExchList = commonService.getExchangeAsk(userSeq);
		//List<HashMap<String, Object>> data_list = new ArrayList<HashMap<String, Object>>();

		HashMap<String, Object> data1 = new HashMap<String, Object>();
		HashMap<String, Object> data2 = new HashMap<String, Object>();
		HashMap<String, Object> data3 = new HashMap<String, Object>();


		//System.out.println("size : " + myExchList.size());
		for(int i = 0; i < myExchList.size(); i++) {
			if(i == 0) {
				JsonElement json = new Gson().toJsonTree(myExchList.get(i));

				JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();

    			JSONParser jsonParse = new JSONParser();
    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

    	    	data1.put("Lv", jsonObj2.get("Lv"));
    	    	data1.put("CntExchange", jsonObj2.get("CntExchange"));
    	    	data1.put("Almoney", jsonObj2.get("Almoney"));
    	    	data1.put("AlpayKR", jsonObj2.get("AlpayKR"));
    	    	data1.put("FlagRealName", jsonObj2.get("FlagRealName"));
    	    	data1.put("ExchRate", jsonObj2.get("ExchRate"));
			}
			if(i == 1) {
				JsonElement json = new Gson().toJsonTree(myExchList.get(i));

				JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();

    			JSONParser jsonParse = new JSONParser();
    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

    	    	data2.put("ExchBaseAlmoney", jsonObj2.get("ExchBaseAlmoney"));
    	    	data2.put("ExchLimitAlmoney", jsonObj2.get("ExchLimitAlmoney"));
    	    	data2.put("ExchAlmoneyTexRate", jsonObj2.get("ExchAlmoneyTexRate"));
    	    	data2.put("ExchStampCnt", jsonObj2.get("ExchStampCnt"));
    	    	data2.put("ExchQusRegCnt", jsonObj2.get("ExchQusRegCnt"));
    	    	data2.put("ExchAnsRegCnt", jsonObj2.get("ExchAnsRegCnt"));
    	    	data2.put("ExchAnsChoicedCnt", jsonObj2.get("ExchAnsChoicedCnt"));
    	    	data2.put("ExchAnsEstiCnt", jsonObj2.get("ExchAnsEstiCnt"));

			}
			if(i == 2) {
				JsonElement json = new Gson().toJsonTree(myExchList.get(i));

				JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();

    			JSONParser jsonParse = new JSONParser();
    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

    	    	data3.put("isExchOK", jsonObj2.get("isExchOK"));
    	    	data3.put("Almoney", jsonObj2.get("Almoney"));
    	    	data3.put("STAMP", jsonObj2.get("STAMP"));
    	    	data3.put("QusRegCnt", jsonObj2.get("QusRegCnt"));
    	    	data3.put("AnsRegCnt", jsonObj2.get("AnsRegCnt"));
    	    	data3.put("AnsChoicedCnt", jsonObj2.get("AnsChoicedCnt"));
    	    	data3.put("AnsEstiCnt", jsonObj2.get("AnsEstiCnt"));
    	    	data3.put("StartDate", jsonObj2.get("StartDate"));
    	    	data3.put("EndDate", jsonObj2.get("EndDate"));
			}

		}

		int userLv = Integer.parseInt( String.valueOf(data1.get("Lv")) );
		int cntExchge = Integer.parseInt( String.valueOf(data1.get("CntExchange")) );
		BigDecimal userAlmoney = new BigDecimal( String.valueOf(data1.get("Almoney")) );
		BigDecimal userAlpayKR = new BigDecimal( String.valueOf(data1.get("AlpayKR")) );
		String flagRealName = String.valueOf(data1.get("FlagRealName"));
		BigDecimal exchRate = new BigDecimal( String.valueOf(data1.get("ExchRate")) );
		//System.out.println("ExchBaseAlmoney : " + data2.get("ExchBaseAlmoney"));
		BigDecimal exchangeBaseDeductAlmoney = new BigDecimal( String.valueOf(data2.get("ExchBaseAlmoney")) );
		BigDecimal maxExchange = new BigDecimal( String.valueOf(data2.get("ExchLimitAlmoney")) );

		String isExchOK = String.valueOf(data3.get("isExchOK"));

		BigDecimal UserAlmoneyRedim = userAlmoney.setScale(0, BigDecimal.ROUND_FLOOR);


		BigDecimal exchangeLimitAlmoney = BigDecimal.ZERO;
		if(userLv == 99) { isExchOK = "Y"; }
		if(isExchOK.equals("Y")) {
			exchangeLimitAlmoney = userAlmoney.subtract(exchangeBaseDeductAlmoney);

			if(exchangeLimitAlmoney.compareTo(BigDecimal.ZERO) < 0) {
				exchangeLimitAlmoney = BigDecimal.ZERO;
			}
			else if(exchangeLimitAlmoney.compareTo(BigDecimal.ZERO) > 0) {
				exchangeLimitAlmoney = maxExchange;
			}
		}
		else {
			exchangeLimitAlmoney = BigDecimal.ZERO;
		}


		if(userLv < 2) {// || userLv > 90
			CommonUtil.jspAlert(response, msg1, "back", "parent.parent");// msg2 활용 --> msg1 활용함

			return null;
		}

		//compareTo 결과 -1:작다, 0:같다, 1:크다
		if(cntExchge == 0 && userAlmoney.compareTo(new BigDecimal("300000.0")) < 0 ) {
			CommonUtil.jspAlert(response, msg2, "back", "parent.parent"); // msg3, msg4 활용 --> msg2 활용함

			return null;
		}

		if(!flagRealName.equals("Y")) {
			CommonUtil.jspCertConfirm(response, request);

			return null;
		}

		String lvStr = CommonUtil.fn_LevelWithLocale(String.valueOf(userLevel), locale);

		mav.addObject("exchRate", exchRate);
		mav.addObject("exchRate_sub", exchRate.multiply(new BigDecimal("0.01")) ); // exchRate.multiply(new BigDecimal("0.01")) 대체

		mav.addObject("userLv", userLv);
		mav.addObject("userAlmoney", userAlmoney);
		mav.addObject("exchangeBaseDeductAlmoney", exchangeBaseDeductAlmoney);
		mav.addObject("maxExchange", maxExchange);
		mav.addObject("exchangeLimitAlmoney", exchangeLimitAlmoney);
		mav.addObject("lvStr", lvStr);

		return mav;
	}

	@RequestMapping(value="account/exchange/exchangeAskAjax", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String exchangeAskAjax(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String result = null;
		//ExchangeAsk.alt 131 줄 부터

		String act = "";

		if(request.getParameter("ACT") == null || request.getParameter("ACT") == "") {
			return null;
		}
		else {
			act = request.getParameter("ACT");
		}

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		String msg1 = messageSource.getMessage("msg_1096", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1098", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_1099", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_1100", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_1101", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg6 = messageSource.getMessage("msg_1102", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		if(userSeq == 0) {
			CommonUtil.jspAlert(response, msg1, "/member/myInfo", ""); // msg1 활용
			return null;
		}

		List<HashMap<String, Object>> lst = new ArrayList<HashMap<String, Object>>();
		if(act.equals("EXCHANGE")) {
			HashMap<String, Object> returnParam = new HashMap<String, Object>();

			List<HashMap<String, Object>> myExchList = commonService.getExchangeAsk(userSeq);

			HashMap<String, Object> data1 = new HashMap<String, Object>();
			HashMap<String, Object> data2 = new HashMap<String, Object>();
			HashMap<String, Object> data3 = new HashMap<String, Object>();

			for(int i = 0; i < myExchList.size(); i++) {
				if(i == 0) {
					JsonElement json = new Gson().toJsonTree(myExchList.get(i));

	    			JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
	    			JSONParser jsonParse = new JSONParser();
	    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

	    	    	data1.put("Lv", jsonObj2.get("Lv"));
	    	    	data1.put("CntExchange", jsonObj2.get("CntExchange"));
	    	    	data1.put("Almoney", jsonObj2.get("Almoney"));
	    	    	data1.put("AlpayKR", jsonObj2.get("AlpayKR"));
	    	    	data1.put("FlagRealName", jsonObj2.get("FlagRealName"));
	    	    	data1.put("ExchRate", jsonObj2.get("ExchRate"));
				}
				if(i == 1) {
					JsonElement json = new Gson().toJsonTree(myExchList.get(i));


					JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
	    			JSONParser jsonParse = new JSONParser();
	    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

	    	    	data2.put("ExchBaseAlmoney", jsonObj2.get("ExchBaseAlmoney"));
	    	    	data2.put("ExchLimitAlmoney", jsonObj2.get("ExchLimitAlmoney"));
	    	    	data2.put("ExchAlmoneyTexRate", jsonObj2.get("ExchAlmoneyTexRate"));
	    	    	data2.put("ExchStampCnt", jsonObj2.get("ExchStampCnt"));
	    	    	data2.put("ExchQusRegCnt", jsonObj2.get("ExchQusRegCnt"));
	    	    	data2.put("ExchAnsRegCnt", jsonObj2.get("ExchAnsRegCnt"));
	    	    	data2.put("ExchAnsChoicedCnt", jsonObj2.get("ExchAnsChoicedCnt"));
	    	    	data2.put("ExchAnsEstiCnt", jsonObj2.get("ExchAnsEstiCnt"));

				}
				if(i == 2) {
					JsonElement json = new Gson().toJsonTree(myExchList.get(i));

					JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
	    			JSONParser jsonParse = new JSONParser();
	    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

	    	    	data3.put("isExchOK", jsonObj2.get("isExchOK"));
	    	    	data3.put("Almoney", jsonObj2.get("Almoney"));
	    	    	data3.put("STAMP", jsonObj2.get("STAMP"));
	    	    	data3.put("QusRegCnt", jsonObj2.get("QusRegCnt"));
	    	    	data3.put("AnsRegCnt", jsonObj2.get("AnsRegCnt"));
	    	    	data3.put("AnsChoicedCnt", jsonObj2.get("AnsChoicedCnt"));
	    	    	data3.put("AnsEstiCnt", jsonObj2.get("AnsEstiCnt"));
	    	    	data3.put("StartDate", jsonObj2.get("StartDate"));
	    	    	data3.put("EndDate", jsonObj2.get("EndDate"));
				}

			}

			int userLv = Integer.parseInt( String.valueOf(data1.get("Lv")) );
			BigDecimal userAlmoney = new BigDecimal( String.valueOf(data1.get("Almoney")) );
			BigDecimal userAlpayKR = new BigDecimal( String.valueOf(data1.get("AlpayKR")) );
			BigDecimal exchRate = new BigDecimal( String.valueOf(data1.get("ExchRate")) );

			BigDecimal exchangeBaseDeductAlmoney = new BigDecimal( String.valueOf(data2.get("ExchBaseAlmoney")) );
			BigDecimal maxExchange = new BigDecimal( String.valueOf(data2.get("ExchLimitAlmoney")) );

			String isExchOK = String.valueOf(data3.get("isExchOK"));

			BigDecimal exchangeLimitAlmoney = BigDecimal.ZERO;
			if(userLv == 99) { isExchOK = "Y"; }
			if(isExchOK.equals("Y")) {
				exchangeLimitAlmoney = userAlmoney.subtract(exchangeBaseDeductAlmoney);

				if(exchangeLimitAlmoney.compareTo(BigDecimal.ZERO) < 0) {
					exchangeLimitAlmoney = BigDecimal.ZERO;
				}
				else if(exchangeLimitAlmoney.compareTo(BigDecimal.ZERO) > 0) {
					exchangeLimitAlmoney = maxExchange;
				}
			}
			else {
				exchangeLimitAlmoney = BigDecimal.ZERO;
			}

			int exchangeAlmoney = 0;
			int realMoney = 0;
			int h_realMoney = 0;

			if(request.getParameter("H_ExchangeAlmoney") != null && !request.getParameter("H_ExchangeAlmoney").equals("")) {
				exchangeAlmoney = Integer.parseInt(request.getParameter("H_ExchangeAlmoney"));
				realMoney = (int)( (exchangeAlmoney / 10) * (1 - exchRate.multiply( new BigDecimal("0.01") ).doubleValue() ) );
			}

			if(request.getParameter("H_ExchangeAlmoney") == null || request.getParameter("H_ExchangeAlmoney") == "") {
				returnParam.put("ReturnCode", 20);
				lst.add(returnParam);

				result = CommonUtil.libJsonArrExit(act, lst);
				return result;
			}
			if(exchangeAlmoney == 0 || request.getParameter("H_RealMoney") == null) {
				result = CommonUtil.libJsonArrExit(msg2, lst); // msg2 활용
				return result;
			} else {
				h_realMoney = Integer.parseInt( request.getParameter("H_RealMoney") );

				if(exchangeAlmoney != (exchangeAlmoney / 10000) * 10000 ) {
					result = CommonUtil.libJsonArrExit(msg3, lst); // msg3 활용
					return result;
				}

				if(h_realMoney != realMoney) {
					result = CommonUtil.libJsonArrExit(msg4, lst); // msg4 활용
					return result;
				}
			}

			DecimalFormat fmt = (DecimalFormat) NumberFormat.getInstance();
			fmt = new DecimalFormat("#,###");
			//System.out.println("exchangeLimitAlmoney : " + exchangeLimitAlmoney);
			//System.out.println("exchangeAlmoney : " + exchangeAlmoney);
			if(exchangeLimitAlmoney.intValue() < exchangeAlmoney ) {
				result = CommonUtil.libJsonArrExit(msg5 + fmt.format(exchangeLimitAlmoney) + msg6, lst); // msg5, msg6 활용
				return result;
			}


			String timeStamp = CommonUtil.getTimestamp();


			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("newSeq", timeStamp);
			param.put("userSeq", userSeq);
			param.put("exchangeAlmoney", exchangeAlmoney);
			param.put("realMoney", realMoney);
			param.put("alpay_balance", (userAlpayKR.intValue() + realMoney));
			param.put("store_code", 0);
			param.put("minusAlmoney", (-1 * exchangeAlmoney));
			/*
			System.out.println("newSeq : " + param.get("newSeq"));
			System.out.println("userSeq : " + param.get("userSeq"));
			System.out.println("exchangeAlmoney : " + param.get("exchangeAlmoney"));
			System.out.println("realMoney : " + param.get("realMoney"));
			System.out.println("alpay_balance : " + param.get("alpay_balance"));
			System.out.println("store_code : " + param.get("store_code"));
			System.out.println("minusAlmoney : " + param.get("minusAlmoney"));
			*/


			HashMap<String, Object> res = commonService.setExchange(param);

			returnParam.put("ReturnCode", res.get("ReturnCode"));
			returnParam.put("ErrText", res.get("ErrText"));

			//returnParam.put("ReturnCode", 1);
			//returnParam.put("ErrText", "테스트");
			lst.add(returnParam);

			//result = CommonUtil.libJsonArrExit("테스트중", lst);

			result = CommonUtil.libJsonArrExit(act, lst);
		}

		return result;
	}

	@RequestMapping("account/exchange/exchangeResult")
	public ModelAndView exchangeResult (HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq == 0) {
			mav.setViewName("redirect:/default/main");

			return mav;
		}


		return mav;
	}

	@RequestMapping(value="account/exchange/process/getLastAccount", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String getLastAccount(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		List<HashMap<String, Object>> lst = new ArrayList<HashMap<String, Object>>();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq == 0) {
			return null;
		}

		HashMap<String, Object> almoneyExchangeBank = commonService.getAlmoneyExchangeInfo(userSeq);

		return new Gson().toJson(almoneyExchangeBank);
	}

	@RequestMapping("interest/deleteInterest")
	public void deleteInterest(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1019", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		if(userSeq == 0) {
			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용
		}
		else {
			int seq = 0;
			if(request.getParameter("Seq") != null) {
				seq = Integer.parseInt( String.valueOf( request.getParameter("Seq") ) );

				commonService.deleteMyInterest(seq);
			}
			else {
				CommonUtil.jspAlert(response, msg2, "back", ""); // msg2 활용
			}
		}
	}


	@RequestMapping(value="/member/getLvProgressInfo", produces="application/json;charset=UTF-8", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getLvProgressInfo(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		int maxLv = 7;

		List<HashMap<String, Object>> lst = new ArrayList<HashMap<String, Object>>();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq == 0) {
			return null;
		}

		String format = "yyyy-MM-dd";
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String dateReg = type.format(today.getTime());

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("userSeq", userSeq);
		param.put("dateReg", dateReg);

		List<HashMap<String, Object>> memberLvData = memberService.getMeberLvDataSP(param);


		HashMap<String, Object> data1 = new HashMap<String, Object>();
		HashMap<String, Object> data2 = new HashMap<String, Object>();
		HashMap<String, Object> data3 = new HashMap<String, Object>();

		String msg1 = messageSource.getMessage("msg_0365", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0366", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0725", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_0261", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_1103", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg6 = messageSource.getMessage("msg_1104", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg7 = messageSource.getMessage("msg_1105", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg8 = messageSource.getMessage("msg_0676", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		for(int i = 0; i < memberLvData.size(); i++) {
			if(i == 0) {
				JsonElement json = new Gson().toJsonTree(memberLvData.get(i));

    			JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
    			JSONParser jsonParse = new JSONParser();
    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

    	    	int level = 0;
    	    	level = Integer.parseInt( String.valueOf( jsonObj2.get("currentLevel") ) );
    	    	int nexeLevel = level;
    	    	if(level < maxLv) {
    	    		nexeLevel = nexeLevel + 1;
    	    	}

    	    	data1.put("StampCnt", jsonObj2.get("StampCnt"));
    	    	data1.put("StampLimit", jsonObj2.get("StampLimit"));
    	    	data1.put("Persentage", jsonObj2.get("Persentage"));
    	    	data1.put("currentLevel", CommonUtil.fn_Level( String.valueOf(level), request ));
    	    	data1.put("nextLevel", CommonUtil.fn_Level( String.valueOf(nexeLevel), request ) );
    	    	data1.put("levelUpDateReg", jsonObj2.get("levelUpDateReg"));
			}
			if(i == 1) {
				JsonElement json = new Gson().toJsonTree(memberLvData.get(i));

				JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
    			JSONParser jsonParse = new JSONParser();
    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

    	    	data2.put(msg1, jsonObj2.get(msg1)); // msg1 활용, data2.put("질문" 처럼 키 부분만 바꾸어 줍니다. 이하 코딩 동일.
    	    	data2.put(msg2, jsonObj2.get(msg2)); // msg2 활용
    	    	data2.put(msg3, jsonObj2.get(msg3)); // msg3 활용
    	    	data2.put(msg4, jsonObj2.get(msg4)); // msg4 활용
    	    	data2.put(msg5, jsonObj2.get(msg5)); // msg5 활용. 누적 추천인 --> 누적 피추천인
    	    	data2.put(msg6, jsonObj2.get(msg6)); // msg6 활용
			}
			if(i == 2) {
				JsonElement json = new Gson().toJsonTree(memberLvData.get(i));

				JsonObject jsonObj = json.getAsJsonArray().get(0).getAsJsonObject();
    			JSONParser jsonParse = new JSONParser();
    	    	JSONObject jsonObj2 = (JSONObject) jsonParse.parse(jsonObj.toString());

    	    	data3.put(msg1, jsonObj2.get(msg1));  // msg1 활용, data2.put("질문" 처럼 키 부분만 바꾸어 줍니다. 이하 코딩 동일.
    	    	data3.put(msg2, jsonObj2.get(msg2)); // msg2 활용
    	    	data3.put(msg3, jsonObj2.get(msg3));
    	    	data3.put(msg8, jsonObj2.get(msg8));
    	    	data3.put(msg4, jsonObj2.get(msg4)); // msg4 활용
    	    	data3.put(msg5, jsonObj2.get(msg5)); // msg5 활용
    	    	data3.put(msg7, jsonObj2.get(msg7)); // msg7 활용
			}
		}

		lst.add(data1);
		lst.add(data2);
		lst.add(data3);

		//System.out.println("new Gson().toJson(lst) : " + new Gson().toJson(lst));
		return new Gson().toJson(lst);
	}

	@RequestMapping(value="/member/myBank_Admin", method = RequestMethod.GET)
	public ModelAndView myBank_Admin (HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		if(userSeq == 0) {
			mav.setViewName("redirect:/default/main");

			return mav;
		}

		if(userLv == 99) {
			userSeq = Integer.parseInt( request.getParameter("UserLv") );
		}

		String flagBank = "";
		BigDecimal Import = BigDecimal.ZERO;
		BigDecimal Expense = BigDecimal.ZERO;
		String searchDateS = "";
		String searchDateE = "";
		String memberName = "";
		String memberNickName = "";
		BigDecimal memberAlmoney =  BigDecimal.ZERO;

		if(request.getParameter("FlagBank") != null) {
			flagBank = request.getParameter("FlagBank");
		}

		if(flagBank.equals("")) {
			flagBank = "I";
		}

		if(request.getParameter("SearchDateS") != null) {
			searchDateS = CommonUtil.fn_Word_In( request.getParameter("SearchDateS") );
		}
		if(request.getParameter("SearchDateE") != null) {
			searchDateE = CommonUtil.fn_Word_In( request.getParameter("SearchDateE") );
		}

		String format = "yyyy-MM-dd";
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String now = type.format(today.getTime());

		if(searchDateS.equals("")) {
			searchDateS = now;
		}
		if(searchDateE.equals("")) {
			searchDateE = now;
		}


		HashMap<String, Object> logAlmoney = commonService.getAlmoneyLogTotal(userSeq);

		if(logAlmoney.get("Import") != null) {
			Import = new BigDecimal( String.valueOf( logAlmoney.get("Import") ) );
		}
		if(logAlmoney.get("Expense") != null) {
			Expense = new BigDecimal( String.valueOf( logAlmoney.get("Expense") ) );
		}

		MemberVO mem = memberService.getAlmoneyMemJoin(userSeq);

		memberName = mem.getName();
		memberNickName = mem.getNickName();
		memberAlmoney = mem.getAlmoney();

		mav.addObject("memberSeq", userSeq);
		mav.addObject("flagBank", flagBank);
		mav.addObject("Import", Import);
		mav.addObject("Expense", Expense);
		mav.addObject("searchDateS", searchDateS);
		mav.addObject("searchDateE", searchDateE);
		mav.addObject("memberName", memberName);
		mav.addObject("memberNickName", memberNickName);
		mav.addObject("memberAlmoney", memberAlmoney);

		return mav;
	}

	public String niceForm(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");

		HashMap<String, Object> resMsg = new HashMap<String, Object>();
		resMsg.put("message", "");
		resMsg.put("CertFlag", "0");
		resMsg.put("data", "");


		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);


		NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

		String sEncData = "";
		String sSiteCode = "BB965";
		String sSitePassword = "PnWuAfNp2nyN";
		String sAuthType = "M";					//없으면 기본 선택화면, M: 핸드폰, C: 카드, X: 공인인증서
		String popgubun = "N";					//Y : 취소버튼 있음, N : 취소버튼 없음
		String customize = "";					//없으면 기본 웹페이지 / Mobile : 모바일페이지
		String sGender = "";					//없으면 기본 선택 값, 0 : 여자, 1 : 남자

		String sReturnUrl = request.getScheme() + "://" + request.getServerName() + "/member/cert/index?ACT=SUCCESS_NICE_CERT";				//성공시 이동될 URL
		String sErrorUrl = "";		//싶래시 이동될 UR

		String sRequestNO = niceCheck.getRequestNO(sSiteCode);

		HashMap<String, Object> rParam = new HashMap<String, Object>();
		rParam.put("userSeq", userSeq);
		rParam.put("reqSeq", sRequestNO);

		memberService.setNiceReq(rParam);

		// 입력될 plain 데이타를 만든다.
		String sPlainData = "7:REQ_SEQ" + sRequestNO.getBytes().length + ":" + sRequestNO +
	            "8:SITECODE" + sSiteCode.getBytes().length + ":" + sSiteCode +
	            "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType +
	            "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl +
	            "7:ERR_URL" + sErrorUrl.getBytes().length + ":" + sErrorUrl +
	            "11:POPUP_GUBUN" + popgubun.getBytes().length + ":" + popgubun +
	            "9:CUSTOMIZE" + customize.getBytes().length + ":" + customize +
				"6:GENDER" + sGender.getBytes().length + ":" + sGender;


		int iReturn = niceCheck.fnEncode(sSiteCode, sSitePassword, sPlainData);
		String sMessage = "";

		String msg1 = messageSource.getMessage("msg_1066", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1067", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_1068", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_1069", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_0318", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg6 = messageSource.getMessage("msg_1106", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg7 = messageSource.getMessage("msg_1107", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg8 = messageSource.getMessage("msg_1108", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

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


		if(!sEncData.equals("")) {
			rParam.put("code", 0);
			rParam.put("message", msg6); // msg6 활용

			if(!sMessage.equals("")) {
				rParam.put("code", -1);
				rParam.put("message", msg7 + "(" + msg8 + " " + sEncData + ")"); // msg7, msg8 활용
			}
		}

		return sEncData;
	}

	@RequestMapping(value="cert/index", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView certIndex(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		String message = "";
		String flagNice = "false";
		String flagIdCard = "false";
		String flagAdInfo = "false";

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		if(userSeq == 0) {
			mav.setViewName("redirect:/default/main");

			return mav;
		}


		MemberVO mVO = memberService.getAlmoneyMemJoin(userSeq);
		int eCount = 0;
		try {
			eCount = memberService.getJoinLogEcount(userSeq);		// mapper null 문제 수정
		} catch(Exception e) {}
		int nCount = 0;
		try {
			nCount = memberService.getJoinLogNcount(userSeq);		// mapper null 문제 수정
		} catch(Exception e) {}

		mav.addObject("userSeq", userSeq);
		mav.addObject("userName", mVO.getName());
		mav.addObject("nickName", mVO.getNickName());
		mav.addObject("nation", mVO.getNation());
		mav.addObject("eCount", eCount);
		mav.addObject("nCount", nCount);



		if(request.getParameter("ACT") == null) {
			String encData  = niceForm(request, response, locale);
			mav.addObject("encData", encData);
		}

		HashMap<String, Object> resMsg = new HashMap<String, Object>();
		resMsg.put("message", message);
		resMsg.put("CertFlag", "0");
		resMsg.put("data", "");


		String act = "";
		if(request.getParameter("ACT") != null) {
			act = request.getParameter("ACT");
		}

		String msg1 = messageSource.getMessage("msg_1019", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1109", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_1110", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_1111", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_1112", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg6 = messageSource.getMessage("msg_1113", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg7 = messageSource.getMessage("msg_1114", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg8 = messageSource.getMessage("msg_1115", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg9 = messageSource.getMessage("msg_1116", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg10 = messageSource.getMessage("msg_1117", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg11 = messageSource.getMessage("msg_1118", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg12 = messageSource.getMessage("msg_0319", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg13 = messageSource.getMessage("msg_0760", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg14 = messageSource.getMessage("msg_0761", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		if(act.equals("SUCCESS_NICE_CERT")) {

			if(request.getParameter("EncodeData") == null) {
				CommonUtil.jspAlert(response, msg1, "/default/main", "parent.parent"); // msg1 활용
				return null;
			}

			NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

			String sEncodeData = CommonUtil.requestReplace(request.getParameter("EncodeData"), "encodeData");

			String sSiteCode = "BB965";			// NICE로부터 부여받은 사이트 코드
		    String sSitePassword = "PnWuAfNp2nyN";	// NICE로부터 부여받은 사이트 패스워드

			String sPlainData = "";
			String sCipherTime = "";			// 복호화한 시간
		    String sRequestNumber = "";			// 요청 번호
		    String sResponseNumber = "";		// 인증 고유번호
		    String sAuthType = "";
		    String sName = "";					// 성명
		    String sDupInfo = "";				// 중복가입 확인값 (DI_64 byte)
		    String sConnInfo = "";				// 연계정보 확인값 (CI_88 byte)
		    String sBirthDate = "";				// 생년월일(YYYYMMDD)
		    String sGender = "";
		    String sNationalInfo = "";			// 내/외국인정보 (개발가이드 참조)
			String sMobileNo = "";				// 휴대폰번호
			String sMobileCo = "";				// 통신사
		    String sMessage = "";

			int resultCode = 0;

			int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

			if( iReturn == 0 ) {
				sPlainData = niceCheck.getPlainData();
		        sCipherTime = niceCheck.getCipherDateTime();

		        // 오류 발견을 위한 빈 컴파일

		        // 데이타를 추출합니다.
		        java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);

		        sRequestNumber  = (String)mapresult.get("REQ_SEQ");
		        sResponseNumber = (String)mapresult.get("RES_SEQ");
		        sAuthType		= (String)mapresult.get("AUTH_TYPE");
				sName			= (String)mapresult.get("UTF8_NAME");
		        sBirthDate		= (String)mapresult.get("BIRTHDATE");
		        sGender			= (String)mapresult.get("GENDER");
		        sNationalInfo  	= (String)mapresult.get("NATIONALINFO");
		        sDupInfo		= (String)mapresult.get("DI");
		        sConnInfo		= (String)mapresult.get("CI");
		        sMobileNo		= (String)mapresult.get("MOBILE_NO");
		        sMobileCo		= (String)mapresult.get("MOBILE_CO");


				String cmpRequest = memberService.getUserPoneBySeq(userSeq);
				String[] cmpRequestSeqArr = null;
				if(cmpRequest != null || !cmpRequest.equals("")) {
					cmpRequestSeqArr = cmpRequest.split("\\|");
				}

				if(!sRequestNumber.equals(cmpRequestSeqArr[1])) {
					resMsg.put("code", "1");
					resMsg.put("message", msg2 + msg3); // msg2, msg3 활용
					resMsg.put("data", "");
				}
				else {
					resMsg.put("requestSeq", sRequestNumber);
					resMsg.put("name", sName);
					resMsg.put("birthDate", sBirthDate);
					resMsg.put("nationalInfo", sNationalInfo);
					resMsg.put("DI", sDupInfo);
					resMsg.put("mobileNumber", sMobileNo);
					resMsg.put("gender", sGender);

					HashMap<String, Object> setItem = new HashMap<String, Object>();
					setItem.put("mem_seq", userSeq);
					setItem.put("sName", sName);
					setItem.put("sDI", sDupInfo);
					setItem.put("sBirthDate", sBirthDate);
					setItem.put("sGender", sGender);

					commonService.setMembersCert(setItem);
				}

				sMessage = msg4; // msg4 활용
			}
			else if( iReturn == -1)
		    {
		        sMessage = msg5; // msg5 활용
		    }
		    else if( iReturn == -4)
		    {
		        sMessage = msg6; // msg6 활용
		    }
		    else if( iReturn == -5)
		    {
		        sMessage = msg7; // msg7 활용
		    }
		    else if( iReturn == -6)
		    {
		        sMessage = msg8; // msg8 활용
		    }
		    else if( iReturn == -9)
		    {
		        sMessage = msg9; // msg9 활용
		    }
		    else if( iReturn == -12)
		    {
		        sMessage = msg10; // msg10 활용
		    }
		    else
		    {
		        sMessage = msg11 + msg12 + iReturn; // msg11, msg12 활용
		    }

			mav.addObject("errorFlag", sMessage);

			String handleCertFinish = commonService.getCertStatus(userSeq);
			if(handleCertFinish.equals("1")) {
				resMsg.put("message", msg13 + msg14); // msg13, msg14 활용
			}

		}
		else {
			mav.addObject("errorFlag", "");
		}



		HashMap<String, Object> certFlag = memberService.getMemberCertFlag(userSeq);

		String certStatus = "0";
		String adminMessage = "";
		if(certFlag != null) {
			flagNice = 	String.valueOf(certFlag.get("flagNice"));
			flagIdCard = String.valueOf(certFlag.get("flagIdCard"));
			flagAdInfo = String.valueOf(certFlag.get("flagAdInfo"));
			certStatus = String.valueOf(certFlag.get("CertStatus"));
			adminMessage = String.valueOf(certFlag.get("actMessage"));
		}

		mav.addObject("flagNice", flagNice);
		mav.addObject("flagIdCard", flagIdCard);
		mav.addObject("flagAdInfo", flagAdInfo);
		mav.addObject("certStatus", certStatus);
		mav.addObject("adminMessage", adminMessage);
		mav.addObject("message", resMsg.get("message"));
		mav.addObject("act", act);


		return mav;
	}

	@RequestMapping(value="cert/certProc", produces="application/json;charset=UTF-8", method = {RequestMethod.POST})
	public @ResponseBody String certProc(MultipartHttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		String message = "";

		int resultCode = 0;

		String act = "";
		if(request.getParameter("ACT") != null) {
			act = request.getParameter("ACT");
		}

		HashMap<String, Object> resMsg = new HashMap<String, Object>();
		resMsg.put("message", message);
		resMsg.put("CertFlag", "0");
		resMsg.put("data", "");
		resMsg.put("error", "");


		if(act.equals("UPLOAD_ID_CARD")) {
			List<MultipartFile> fileList = request.getFiles("files");

			if(fileList.size() > 0) {

				UtilFile utilFile = new UtilFile();

		        String originFileName = "";

		        String ThumbFileSaveName = "";

		        Calendar cal = Calendar.getInstance();
		        int year = cal.get(Calendar.YEAR);
		    	int month = cal.get(Calendar.MONTH) + 1;
		        String msg1 = messageSource.getMessage("msg_1044", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		        String msg2 = messageSource.getMessage("msg_1119", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		        String msg3 = messageSource.getMessage("msg_1120", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		        String msg4 = messageSource.getMessage("msg_0760", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		        String msg5 = messageSource.getMessage("msg_0761", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		        for (MultipartFile mf : fileList) {
		        	originFileName = mf.getOriginalFilename(); // 원본 파일 명

		        	if(CommonUtil.fn_FileType_Check(originFileName).equals("N")) {
		        		CommonUtil.jspAlert(response, msg1, "", ""); // msg1 활용
					}

		        	Date time = new Date();
					SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmSS");
					String today = format1.format(time);

		        	String FileReName = userSeq + "_" + today;
		        	String FileExtension = FilenameUtils.getExtension(originFileName);
		        	String FileSaveName = FileReName + "." + FileExtension;

		        	//int year = YearMonth.now().getYear();
		        	//int month = YearMonth.now().getMonthValue();

		        	// 파일 업로드 결과값을 path로 받아온다(이미 fileUpload() 메소드에서 해당 경로에 업로드는 끝났음)
			        String defaultFolder = "Cert/"+ year + "/" + month;

			        String FilePath = utilFile.getSaveLocation(request, defaultFolder);


			        File fileDir = new File(FilePath);

			        if (!fileDir.exists()) {
			        	fileDir.mkdirs();
			        }

			        try { // 파일생성
			        	//원본 파일 크기로 업로드 처리
			        	File original = new File(FilePath, FileSaveName);
			        	mf.transferTo(original);

			        	if(original.exists()) {
				        	ThumbFileSaveName = FileReName + "." + FileExtension;

				        	File thumbnail = new File(FilePath, ThumbFileSaveName);
				        	mf.transferTo(thumbnail);

				        	//db 저장
				        	HashMap<String, Object> param = new HashMap<String, Object>();
				        	param.put("userId", userSeq);
				        	param.put("id_card", year+"/"+month+"/"+ThumbFileSaveName);

				        	//System.out.println("userId : " + param.get("userId"));
				        	//System.out.println("id_card : " + param.get("id_card"));
				        	memberService.setIdCard(param);

				        	if(thumbnail.exists()) {
				        		//업로드 원본 파일 삭제
				        		//if(original.delete()){
				        		//	utilFile.deleteFile(original);
				        		//}
				        	}

				        	resultCode = 0;
				        	resMsg.put("message", fileList.size() + msg2); // msg2 활용
			        	}
			        	else {
			        		resultCode = 2;
			        		resMsg.put("message", msg3);
				        	resMsg.put("error", msg3);
			        	}
			        } catch (Exception e) {
			        	resultCode = 2;
			        	resMsg.put("message", msg3); // msg3 활용
			        	resMsg.put("error", e.getMessage());
			        } finally {
			        	String handleCertFinish = commonService.getCertStatus(userSeq);

			        	if(handleCertFinish.equals("1")) {
							resMsg.put("message", msg4 + msg5); // msg4, msg5 활용
						}
			        }
		        }
			}
		}

		return CommonUtil.libJsonExit(resultCode, resMsg);
	}

	@RequestMapping(value="cert/certEtcProc", produces="application/json;charset=UTF-8", method = {RequestMethod.POST})
	public @ResponseBody String certEtcProc(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		int resultCode = 0;

		String act = "";
		if(request.getParameter("ACT") != null) {
			act = request.getParameter("ACT");
		}

		HashMap<String, Object> resMsg = new HashMap<String, Object>();
		resMsg.put("message", "");
		resMsg.put("CertFlag", "0");
		resMsg.put("data", "");
		resMsg.put("error", "");

		String msg1 = messageSource.getMessage("msg_1121", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1122", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0319", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_0760", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_0761", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		if(act.equals("SAVE_AD_INFO")) {
			try {
				String job = request.getParameter("job");
				String area = request.getParameter("area");
				String estate = request.getParameter("estate");
				String residence = request.getParameter("residence");
				String marriage = request.getParameter("marriage");
				String children = request.getParameter("children");
				String car = request.getParameter("car");
				String yearIncome = request.getParameter("yearIncome");
				String healthFlag = request.getParameter("healthFlag");
				String healthDetail = request.getParameter("healthDetail");
				String likeField = request.getParameter("likeField");
				String email = request.getParameter("email");

				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("mem_seq", userSeq);
				param.put("job", job);
				param.put("area", area);
				param.put("estate", estate);
				param.put("residence", residence);
				param.put("marriage", marriage);
				param.put("children", children);
				param.put("car", car);
				param.put("yearIncome", yearIncome);
				param.put("healthFlag", healthFlag);
				param.put("healthDetail", healthDetail);
				param.put("likeField", likeField);
				param.put("email", email);

				memberService.setCertEtc(param);

				resMsg.put("message", msg1); // msg1 활용
			} catch (Exception e) {
				resultCode = 2;
				resMsg.put("message", msg2 + msg3); // msg2, msg3 활용
				resMsg.put("error", e.getMessage());
			//	System.out.println("error : "  + e.getMessage());
			} finally {
				String handleCertFinish = commonService.getCertStatus(userSeq);

	        	if(handleCertFinish.equals("1")) {
					resMsg.put("message", msg4 + msg5); // msg4, msg5 활용
				}
			}
		}

		return CommonUtil.libJsonExit(resultCode, resMsg);
	}

	@RequestMapping(value="cert/certUserInfoProc", produces="application/json;charset=UTF-8", method = {RequestMethod.POST})
	public @ResponseBody String certUserInfoProc(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		int resultCode = 0;


		MemberVO mVO = memberService.getAlmoneyMemJoin(userSeq);

		String userName = request.getParameter("userName");
		String nickName = request.getParameter("nickName");
		String nation = request.getParameter("nation");

		if(mVO.getName() == null) {
			// 이름 입력 처리
			HashMap<String, Object> uParam = new HashMap<String, Object>();
			uParam.put("userName", userName);
			uParam.put("userSeq", userSeq);

			memberService.setMemberNameBySeq(uParam);
		}

		int joinCnt = memberService.getJoinLogCount(userSeq);
		if(joinCnt == 0) {
			//로그 생성
			HashMap<String, Object> iParam = new HashMap<String, Object>();
			iParam.put("userSeq", userSeq);
			iParam.put("nation", mVO.getNation());
			iParam.put("lang", mVO.getLang());
			iParam.put("nCount", 0);
			iParam.put("eCount", 0);
			memberService.addJoinFirstLog(iParam);
		}
		int eCount = memberService.getJoinLogEcount(userSeq);
		int nCount = memberService.getJoinLogNcount(userSeq);

		// 국적 수정 시
		// T_MEMBERS nation 과 현재 입력받은 nation 과 틀릴 경우
		if(eCount < 1) {
			HashMap<String, Object> eParam = new HashMap<String, Object>();
			eParam.put("userSeq", userSeq);
			eParam.put("eCount", 1);
			eParam.put("nation", nation);

			if(!nation.equals(mVO.getNation())) {
				memberService.setMemberNationBySeq(eParam);

				memberService.setJoinLogEcount(eParam);
			}
		}

		// 닉네임 수정 시
		// T_MEMBERS NickName 과 현재 입력받은 NickName이 틀릴 경우
		if(nCount < 1) {
			HashMap<String, Object> nParam = new HashMap<String, Object>();
			nParam.put("userSeq", userSeq);
			nParam.put("nCount", 1);
			nParam.put("nickName", nickName);

			if(!nickName.equals(mVO.getNickName())) {
				memberService.setMemberNickNameBySeq(nParam);

				memberService.setJoinLogNcount(nParam);
			}
		}

		HashMap<String, Object> resMsg = new HashMap<String, Object>();

		String msg1 = messageSource.getMessage("msg_1123", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		resMsg.put("message", msg1); // msg1 활용

		return CommonUtil.libJsonExit(resultCode, resMsg);
	}

	@RequestMapping(value="uploadProfileImg_n", method = RequestMethod.POST)
	public @ResponseBody String uploadProfileImg(MultipartHttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		List<MultipartFile> fileList = request.getFiles("photo");

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

				        //세션 정보에 Setting
			        	//session.setAttribute("UserPhoto", ThumbFileSaveName);

		        		//업로드 원본 파일 삭제
		        		if(original.delete()){
		        			utilFile.deleteFile(original);
		        		}
		        	}
		        	/*
		        	session.setAttribute("UserLv", global.get("UserLv"));
		    		session.setAttribute("Ver", global.get("Ver"));
		    		session.setAttribute("UserSeq", global.get("UserSeq"));
		    		session.setAttribute("UserNickName", global.get("UserNickName"));
		    		session.setAttribute("UserLv", global.get("UserLv"));
		    		session.setAttribute("UserDateReg", global.get("UserDateReg"));
		    		session.setAttribute("UserAlmoney", global.get("UserAlmoney"));
		    		session.setAttribute("UserPhoto", ThumbFileSaveName);
		    		session.setAttribute("RankQ", global.get("RankQ"));
		    		session.setAttribute("RankA", global.get("RankA"));
		    		session.setAttribute("FlagSelfAnswer", global.get("FlagSelfAnswer"));
		    		session.setAttribute("MemberType", global.get("MemberType"));
		    		session.setAttribute("ALARM_COUNT_SUM", global.get("ALARM_COUNT_SUM"));
		    		session.setAttribute("SessExpire", global.get("SessExpire"));
		    		session.setAttribute("AutoLogin", "Y");
		    		session.setAttribute("UserEmail", global.get("UserEmail")); // 암호화 되어 있음
		    		session.setAttribute("UserAlpayKR", global.get("UserAlpayKR"));
		    		session.setAttribute("Almaeng", global.get("Almaeng"));
		    		session.setAttribute("AlmaengCode", global.get("AlmaengCode"));

		    		String AdminSecu = "";

		    		AdminSecu = commonService.getAuthority(userSeq);

		    		if(AdminSecu != null) {
		    			session.setAttribute("AdminSecu", AdminSecu);
		    		}

		    		Cookie info = new Cookie("SESS", CallSecurity.Fn_CallPHP_Secure("ENCODE=" + URLEncoder.encode(CallSecurity.Fn_jsonEncode_sess(session),"UTF-8")) );
		    		info.setVersion( 0 );
		    		info.setMaxAge( Integer.parseInt(global.get("SessExpire").toString()) * 24 * 60 * 60 );
		    		info.setPath("/");

		    		response.addCookie(info);
		    		*/
		        } catch (Exception e) {
		        	return "nofile";
		        }

	        }

	        return ThumbFileSaveName;

		}

		return "nofile";
	}
}
