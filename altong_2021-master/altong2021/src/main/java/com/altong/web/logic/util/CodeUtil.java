package com.altong.web.logic.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class CodeUtil {
	//@Autowired
	//private ApplicationContext appContext;

	//-----------------------------------------------------------------------------
	//프로그램 ID   :  Code_Mem_LV.jsp
	//프로그램 명칭 :  회원 레벨 코드 정의 페이지
	//-----------------------------------------------------------------------------
	Map<String, String> CODE_MEM_LV_CD = null;
	Map<String, String> CODE_MEM_LV_NM = null;

	//-----------------------------------------------------------------------------
	//프로그램 ID   :  Code_Almoney_Exchange_Status.jsp
	//프로그램 명칭 :  출금신청 진행상태 코드 정의 페이지
	//-----------------------------------------------------------------------------
	Map<String, String> CODE_ALMONEY_EXCHANGE_STATUS_CD = null;


	Map<String, String> CODE_ALMONEY_EXCHANGE_STATUS_NM = null;

	//-----------------------------------------------------------------------------
	//프로그램 ID   :  Code_Answer_Almoney.jsp
	//프로그램 명칭 :  사례알머니(알/원) 코드 정의 페이지
	//-----------------------------------------------------------------------------
	Map<Object, String> CODE_ANSWER_ALMONEY = null;

	//-----------------------------------------------------------------------------
	//프로그램 ID   :  Code_Area.jsp
	//프로그램 명칭 :  거주 지역 코드 정의 페이지
	//-----------------------------------------------------------------------------
	Map<String, String> CODE_AREA_NM = null;

	//-----------------------------------------------------------------------------
	//프로그램 ID   :  Code_Bank.jsp
	//프로그램 명칭 :  은행 코드 정의 페이지
	//-----------------------------------------------------------------------------
	Map<String, String> CODE_BANK_NM = null;

	//-----------------------------------------------------------------------------
	//프로그램 ID   :  Code_Car.jsp
	//프로그램 명칭 :  자동차 코드 정의 페이지--------------------------------------------------
	Map<String, String> CODE_CAR_NM = null;

	//-----------------------------------------------------------------------------
	//프로그램 ID   :  Code_Children.jsp
	//프로그램 명칭 :  자녀 코드 정의 페이지
	//-----------------------------------------------------------------------------
	Map<String, String> CODE_CHILDREN_NM = null;


	//이하 부터 재정의


	//-----------------------------------------------------------------------------
	//프로그램 ID   :  Code_Email.jsp
	//프로그램 명칭 :  메일 주소 코드 정의 페이지
	//-----------------------------------------------------------------------------
	Map<String, String> CODE_EMAIL = null;

	//-----------------------------------------------------------------------------
	//프로그램 ID   :  Code_Health_Detail.jsp
	//프로그램 명칭 :  건강 이상 코드 정의 페이지
	//-----------------------------------------------------------------------------
	Map<String, String> CODE_HEALTH_DETAIL_NM = null;

	//-----------------------------------------------------------------------------
	//프로그램 ID   :  Code_Job.jsp
	//프로그램 명칭 :  직업 코드 정의 페이지
	//-----------------------------------------------------------------------------
	Map<String, String> CODE_JOB_NM = null;

	//-----------------------------------------------------------------------------
	//프로그램 ID   :  Code_Like_Field.jsp
	//프로그램 명칭 :  흥미분야 코드 정의 페이지
	//-----------------------------------------------------------------------------
	Map<String, String> CODE_LIKE_FIELD_NM = null;

	//-----------------------------------------------------------------------------
	//프로그램 ID   :  Code_Marriage.jsp
	//프로그램 명칭 :  결혼여부 코드 정의 페이지
	//-----------------------------------------------------------------------------
	Map<String, String> CODE_MARRIAGE_NM = null;

	//-----------------------------------------------------------------------------
	// 김태환 2019년 7월 3일
	//-----------------------------------------------------------------------------
	Map<String, String> CODE_MEM_ALARM_VIEW_FIELD_CD = null;
	Map<String, String> CODE_MEM_ALARM_VIEW_FIELD_NM = null;
	String CODE_MEM_ALARM_VIEW_FIELD_CD_ANS_CHOICE = null;
	String CODE_MEM_ALARM_VIEW_FIELD_CD_ANS_REGIST = null;
	String CODE_MEM_ALARM_VIEW_FIELD_CD_FAVORITE_QUE_REGIST = null;
	String CODE_MEM_ALARM_VIEW_FIELD_CD_CMT_REGIST = null;
	String CODE_MEM_ALARM_VIEW_FIELD_CD_ANS_CHOICE_READY = null;
	String CODE_MEM_ALARM_VIEW_FIELD_CD_ALMONEY_INCOME = null;
	String CODE_MEM_ALARM_VIEW_FIELD_CD_ALMONEY_PAYING = null;
	String CODE_MEM_ALARM_VIEW_FIELD_CD_MEM_LEVEL_UP = null;
	String CODE_MEM_ALARM_VIEW_FIELD_CD_REPORT = null;
	String CODE_MEM_ALARM_VIEW_FIELD_CD_MENTEE = null;
	String CODE_MEM_ALARM_VIEW_FIELD_CD_MENTEE_UNSET = null;
	String CODE_MEM_ALARM_VIEW_FIELD_CD_RECOMM_MEM_JOIN = null;
	String CODE_MEM_ALARM_VIEW_FIELD_CD_NOTICE = null;
	String CODE_MEM_ALARM_VIEW_FIELD_CD_ALARM = null;

	//-----------------------------------------------------------------------------
	//프로그램 ID   :  Code_Mem_Join_Cert_Type.jsp
	//프로그램 명칭 :  회원가입 인증 방식 코드 정의 페이지
	//-----------------------------------------------------------------------------
	Map<String, String> CODE_MEM_JOIN_CERT_TYPE_CD = null;
	Map<String, String> CODE_MEM_JOIN_CERT_TYPE_NM = null;

	//-----------------------------------------------------------------------------
	//프로그램 ID   :  Code_Real_Estate.jsp
	//프로그램 명칭 :  부동산 코드 정의 페이지
	//-----------------------------------------------------------------------------
	Map<String, String> CODE_REAL_ESTATE_NM = null;

	//-----------------------------------------------------------------------------
	//프로그램 ID   :  Code_Report_Admin_Status.jsp
	//프로그램 명칭 :  신고 처리 진행 상태 코드 정의 페이지
	//-----------------------------------------------------------------------------
	Map<String, String> CODE_REPORT_ADMIN_STATUS_CD = null;
	Map<String, String> CODE_REPORT_ADMIN_STATUS_NM = null;
	String CODE_REPORT_ADMIN_STATUS_DEFAULT = null;
	String CODE_REPORT_ADMIN_STATUS_READY = null;
	String CODE_REPORT_ADMIN_STATUS_LOCK = null;
	String CODE_REPORT_ADMIN_STATUS_DELETE = null;
	String CODE_REPORT_ADMIN_STATUS_HOLD = null;
	String CODE_REPORT_ADMIN_STATUS_ETC = null;


	//-----------------------------------------------------------------------------
	//프로그램 ID   :  Code_Report_Contents.jsp
	//프로그램 명칭 :  게시글 유형 코드 정의 페이지
	//-----------------------------------------------------------------------------
	Map<String, String> CODE_REPORT_CONTENTS_CD = null;
	Map<String, String> CODE_REPORT_CONTENTS_NM = null;
	String CODE_REPORT_CONTENTS_QUE = null;
	String CODE_REPORT_CONTENTS_QUE_REPLY = null;
	String CODE_REPORT_CONTENTS_ANS = null;
	String CODE_REPORT_CONTENTS_ANS_REPLY = null;

	//-----------------------------------------------------------------------------
	//프로그램 ID   :  Code_Report_Reason.jsp
	//프로그램 명칭 :  신고 유형(사유) 코드 정의 페이지
	//-----------------------------------------------------------------------------
	Map<String, String> CODE_REPORT_REASON_CD = null;
	Map<String, String> CODE_REPORT_REASON_NM = null;
	String CODE_REPORT_REASON_CD_SPAM = null;
	String CODE_REPORT_REASON_CD_ADULTS = null;
	String CODE_REPORT_REASON_CD_TRICKS = null;
	String CODE_REPORT_REASON_CD_ETC = null;

	//-----------------------------------------------------------------------------
	//프로그램 ID   :  Code_Residence.jsp
	//프로그램 명칭 :  거주형태 코드 정의 페이지
	//-----------------------------------------------------------------------------
	Map<String, String> CODE_RESIDENCE_NM = null;

	//-----------------------------------------------------------------------------
	//프로그램 ID   :  Code_Year_Income.jsp
	//프로그램 명칭 :  연 소득 코드 정의 페이지
	//----------------------------------------------------------------------------
	Map<String, String> CODE_YEAR_INCOME_NM = null;


	Locale locale = null;
	MessageSource messageSource = null;

	public CodeUtil(HttpServletRequest request) throws FileNotFoundException, IOException {
		String lang = request.getLocale().getLanguage();

		CookieBox cookieBox = new CookieBox(request);
		Cookie s_lang = cookieBox.getCookie("s_lang");

		if(s_lang != null) {
			lang = s_lang.getValue();
		}

		if(lang.contains("_")) {
			String[] langArr = lang.split("_");
			lang = langArr[0];
		}

		messageSource = CommonUtil.messageSource(lang);

		locale = new Locale(lang);

		setCODE_ALMONEY_EXCHANGE_STATUS();
		setCODE_ANSWER_ALMONEY(request);
		setCODE_AREA_NM();
		setCODE_BANK_NM();
		setCODE_CAR_NM();
		setCODE_CHILDREN_NM();
		setCODE_EMAIL();
		setCODE_HEALTH_DETAIL_NM();
		setCODE_JOB_NM();
		setCODE_LIKE_FIELD_NM();
		setCODE_MARRIAGE_NM();
		setCODE_MEM_ALARM_VIEW_FIELD();
		setCODE_MEM_JOIN_CERT_TYPE();
		setCODE_MEM_LV();
		setCODE_REAL_ESTATE_NM();
		setCODE_REPORT_ADMIN_STATUS();
		setCODE_REPORT_CONTENTS();
		setCODE_REPORT_REASON();
		setCODE_RESIDENCE_NM();
		setCODE_YEAR_INCOME_NM();
	}

	public Map<String, String> getCODE_MEM_LV_CD() {
		return CODE_MEM_LV_CD;
	}
	public Map<String, String> getCODE_MEM_LV_NM() {
		return CODE_MEM_LV_NM;
	}
	void setCODE_MEM_LV() {
		CODE_MEM_LV_CD = new HashMap<String, String>();
		CODE_MEM_LV_NM = new HashMap<String, String>();

		CODE_MEM_LV_CD.put("1", "1");
		CODE_MEM_LV_CD.put("2", "2");
		CODE_MEM_LV_CD.put("3", "3");
		CODE_MEM_LV_CD.put("4", "4");
		CODE_MEM_LV_CD.put("5", "5");
		CODE_MEM_LV_CD.put("6", "6");
		CODE_MEM_LV_CD.put("7", "7");
		CODE_MEM_LV_CD.put("8", "8");
		CODE_MEM_LV_CD.put("9", "9");
		CODE_MEM_LV_CD.put("10", "10");
		CODE_MEM_LV_CD.put("11", "11");

		String msg1 = messageSource.getMessage("msg_0137", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0138", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0139", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_0140", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_0141", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg6 = messageSource.getMessage("msg_0142", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg7 = messageSource.getMessage("msg_0143", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg8 = messageSource.getMessage("msg_0144", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg9 = messageSource.getMessage("msg_0145", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg10 = messageSource.getMessage("msg_0146", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg11 = messageSource.getMessage("msg_0147", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		//System.out.println("msg1 : " + msg1);
		CODE_MEM_LV_NM.put("1", msg1); // msg1 활용
		CODE_MEM_LV_NM.put("2", msg2); // msg2 활용
		CODE_MEM_LV_NM.put("3", msg3); // msg3 활용
		CODE_MEM_LV_NM.put("4", msg4); // msg4 활용
		CODE_MEM_LV_NM.put("5", msg5); // msg5 활용
		CODE_MEM_LV_NM.put("6", msg6); // msg6 활용
		CODE_MEM_LV_NM.put("7", msg7); // msg7 활용
		CODE_MEM_LV_NM.put("8", msg8); // msg8 활용
		CODE_MEM_LV_NM.put("9", msg9); // msg9 활용
		CODE_MEM_LV_NM.put("10", msg10); // msg10 활용
		CODE_MEM_LV_NM.put("11", msg11); // msg11 활용
	}

	public Map<String, String> getCODE_ALMONEY_EXCHANGE_STATUS_CD() {
		return CODE_ALMONEY_EXCHANGE_STATUS_CD;
	}

	public Map<String, String> getCODE_ALMONEY_EXCHANGE_STATUS_NM() {
		return CODE_ALMONEY_EXCHANGE_STATUS_NM;
	}

	void setCODE_ALMONEY_EXCHANGE_STATUS() {
		CODE_ALMONEY_EXCHANGE_STATUS_CD = new HashMap<String, String>();
		CODE_ALMONEY_EXCHANGE_STATUS_NM = new HashMap<String, String>();

		CODE_ALMONEY_EXCHANGE_STATUS_CD.put("1", "10");
		CODE_ALMONEY_EXCHANGE_STATUS_CD.put("2", "20");
		CODE_ALMONEY_EXCHANGE_STATUS_CD.put("3", "30");
		CODE_ALMONEY_EXCHANGE_STATUS_CD.put("4", "40");

		String msg1 = messageSource.getMessage("msg_0209", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0970", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0971", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_0972", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		//System.out.println("msg1 : " + msg1);
		CODE_ALMONEY_EXCHANGE_STATUS_NM.put("1", msg1); // msg1 활용
		CODE_ALMONEY_EXCHANGE_STATUS_NM.put("2", msg2); // msg2 활용
		CODE_ALMONEY_EXCHANGE_STATUS_NM.put("3", msg3); // msg3 활용
		CODE_ALMONEY_EXCHANGE_STATUS_NM.put("4", msg4); // msg4 활용
	}

	public Map<Object, String> getCODE_ANSWER_ALMONEY() {
		return CODE_ANSWER_ALMONEY;
	}

	public void setCODE_ANSWER_ALMONEY(HttpServletRequest request) throws FileNotFoundException, IOException {
		CODE_ANSWER_ALMONEY = new HashMap<Object, String>();

		//코드 정의 및 코드값 할당
		CODE_ANSWER_ALMONEY.put("1", "100");
		CODE_ANSWER_ALMONEY.put("2", "300");
		CODE_ANSWER_ALMONEY.put("3", "500");
		CODE_ANSWER_ALMONEY.put("4", "1000");
		CODE_ANSWER_ALMONEY.put("5", "5000");
		CODE_ANSWER_ALMONEY.put("6", "10000");

		//관리자 사례알(이벤트) 추가
		CODE_ANSWER_ALMONEY.put("7", "50000");
		CODE_ANSWER_ALMONEY.put("8", "100000");
		CODE_ANSWER_ALMONEY.put("9", "150000");
		CODE_ANSWER_ALMONEY.put("10", "200000");

		int Lv = 0;
		int libMaxLv = 7;

		HttpSession session = request.getSession(true);

		if(session.getAttribute("UserLv") != null){
			Lv = Integer.parseInt(session.getAttribute("UserLv").toString());
		}
		if(Lv > libMaxLv || Lv < 1) {
			Lv = libMaxLv;
		}

		//CONCAT ( 'Lv', #{lv}, '_QueRegAlmoney')
		String col =  "Lv"+Lv+"_QueRegAlmoney";
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		SimpleDriverDataSource ds = (SimpleDriverDataSource) context.getBean("dataSource");

		/*
		System.out.println("getDriver : " + ds.getDriver().getClass());
		System.out.println("getUrl : " + ds.getUrl());
		System.out.println("getUsername : " + ds.getUsername());
		System.out.println("getPassword : " + ds.getPassword());
		*/

		DataSource source = ds;
		JdbcTemplate jt = new JdbcTemplate(source);
		SqlRowSet  rs = null;
		rs = jt.queryForRowSet("SELECT "+ col  +" FROM T_CONFIG");

		if(rs.next()) {
			//System.out.println("col : " + rs.getString(col));

			String lvStr = rs.getString(col);

			if(lvStr.length() > 0) {
				String[] CODE_ANSWER_ALMONEY_arr = lvStr.split("/");

				int j = 1;
				for(int i = 0; i < CODE_ANSWER_ALMONEY_arr.length; i++) {
					//out.println("j : " + j);
					CODE_ANSWER_ALMONEY.replace(j, CODE_ANSWER_ALMONEY_arr[i]);
					j++;
				}
			}
		}
	}

	public Map<String, String> getCODE_AREA_NM() {
		return CODE_AREA_NM;
	}

	void setCODE_AREA_NM() {
		CODE_AREA_NM = new HashMap<String, String>();

		String msg1 = messageSource.getMessage("msg_0022", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0023", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0024", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_0025", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_0026", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg6 = messageSource.getMessage("msg_0027", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg7 = messageSource.getMessage("msg_0028", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg8 = messageSource.getMessage("msg_0029", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg9 = messageSource.getMessage("msg_0030", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg10 = messageSource.getMessage("msg_0031", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg11 = messageSource.getMessage("msg_0032", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg12 = messageSource.getMessage("msg_0033", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg13 = messageSource.getMessage("msg_0034", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg14 = messageSource.getMessage("msg_0035", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg15 = messageSource.getMessage("msg_0036", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg16 = messageSource.getMessage("msg_0037", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg17 = messageSource.getMessage("msg_0038", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg18 = messageSource.getMessage("msg_0039", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg19 = messageSource.getMessage("msg_0040", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		CODE_AREA_NM.put("1", msg1); // msg1 활용
		CODE_AREA_NM.put("2", msg2); // msg2 활용
		CODE_AREA_NM.put("3", msg3); // msg3 활용
		CODE_AREA_NM.put("4", msg4); // msg4 활용
		CODE_AREA_NM.put("5", msg5); // msg5 활용
		CODE_AREA_NM.put("6", msg6); // msg6 활용
		CODE_AREA_NM.put("7", msg7); // msg7 활용
		CODE_AREA_NM.put("8", msg8); // msg8 활용
		CODE_AREA_NM.put("9", msg9); // msg9 활용
		CODE_AREA_NM.put("10", msg10); // msg10 활용
		CODE_AREA_NM.put("11", msg11); // msg11 활용
		CODE_AREA_NM.put("12", msg12); // msg12 활용
		CODE_AREA_NM.put("13", msg13); // msg13 활용
		CODE_AREA_NM.put("14", msg14); // msg14 활용
		CODE_AREA_NM.put("15", msg15); // msg15 활용
		CODE_AREA_NM.put("16", msg16); // msg16 활용
		CODE_AREA_NM.put("17", msg17); // msg17 활용
		CODE_AREA_NM.put("18", msg18); // msg18 활용
		CODE_AREA_NM.put("19", msg19); // msg19 활용
	}

	public Map<String, String> getCODE_BANK_NM() {
		return CODE_BANK_NM;
	}

	void setCODE_BANK_NM() {
		CODE_BANK_NM = new HashMap<String, String>();

		String msg1 = messageSource.getMessage("msg_0973", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0974", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0975", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_0976", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_0977", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg6 = messageSource.getMessage("msg_0978", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg7 = messageSource.getMessage("msg_0979", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg8 = messageSource.getMessage("msg_0980", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg9 = messageSource.getMessage("msg_0981", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg10 = messageSource.getMessage("msg_0982", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg11 = messageSource.getMessage("msg_0983", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg12 = messageSource.getMessage("msg_0984", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg13 = messageSource.getMessage("msg_0985", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg14 = messageSource.getMessage("msg_0986", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg15 = messageSource.getMessage("msg_0987", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg16 = messageSource.getMessage("msg_0988", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg17 = messageSource.getMessage("msg_0989", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg18 = messageSource.getMessage("msg_0990", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg19 = messageSource.getMessage("msg_0991", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg20 = messageSource.getMessage("msg_0992", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg21 = messageSource.getMessage("msg_0993", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg22 = messageSource.getMessage("msg_0994", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		CODE_BANK_NM.put("1", msg1); // msg1 활용
		CODE_BANK_NM.put("2", msg2); // msg2 활용
		CODE_BANK_NM.put("3", msg3); // msg3 활용
		CODE_BANK_NM.put("4", msg4); // msg4 활용
		CODE_BANK_NM.put("5", msg5); // msg5 활용
		CODE_BANK_NM.put("6", msg6); // msg6 활용
		CODE_BANK_NM.put("7", msg7); // msg7 활용
		CODE_BANK_NM.put("8", msg8); // msg8 활용
		CODE_BANK_NM.put("9", msg9); // msg9 활용
		CODE_BANK_NM.put("10", msg10); // msg10 활용
		CODE_BANK_NM.put("11", msg11); // msg11 활용
		CODE_BANK_NM.put("12", msg12); // msg12 활용
		CODE_BANK_NM.put("13", msg13); // msg13 활용
		CODE_BANK_NM.put("14", msg14); // msg14 활용
		CODE_BANK_NM.put("15", msg15); // msg15 활용
		CODE_BANK_NM.put("16", msg16); // msg16 활용
		CODE_BANK_NM.put("17", msg17); // msg17 활용
		CODE_BANK_NM.put("18", msg18); // msg18 활용
		CODE_BANK_NM.put("19", msg19); // msg19 활용
		CODE_BANK_NM.put("20", msg20); // msg20 활용
		CODE_BANK_NM.put("21", msg21); // msg21 활용
		CODE_BANK_NM.put("22", msg22); // msg22 활용
	}

	public Map<String, String> getCODE_CAR_NM() {
		return CODE_CAR_NM;
	}

	void setCODE_CAR_NM() {
		CODE_CAR_NM = new HashMap<String, String>();

		String msg1 = messageSource.getMessage("msg_0057", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0058", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0059", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_0060", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_0061", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg6 = messageSource.getMessage("msg_0062", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		CODE_CAR_NM.put("1", msg1); // msg1 활용
		CODE_CAR_NM.put("2", msg2); // msg2 활용
		CODE_CAR_NM.put("3", msg3); // msg3 활용
		CODE_CAR_NM.put("4", msg4); // msg4 활용
		CODE_CAR_NM.put("5", msg5); // msg5 활용
		CODE_CAR_NM.put("6", msg6); // msg6 활용
	}

	public Map<String, String> getCODE_CHILDREN_NM() {
		return CODE_CHILDREN_NM;
	}

	void setCODE_CHILDREN_NM() {
		CODE_CHILDREN_NM = new HashMap<String, String>();

		String msg1 = messageSource.getMessage("msg_0053", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0054", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0055", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_0056", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		CODE_CHILDREN_NM.put("1", msg1); // msg1 활용
		CODE_CHILDREN_NM.put("2", msg2); // msg2 활용
		CODE_CHILDREN_NM.put("3", msg3); // msg3 활용
		CODE_CHILDREN_NM.put("4", msg4); // msg4 활용
	}

	public Map<String, String> getCODE_EMAIL() {
		return CODE_EMAIL;
	}
	void setCODE_EMAIL() {
		CODE_EMAIL = new HashMap<String, String>();

		String msg1 = messageSource.getMessage("msg_0889", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		CODE_EMAIL.put("1", msg1); // msg1 활용
		CODE_EMAIL.put("2", "hanmail.net");
		CODE_EMAIL.put("3", "naver.com");
		CODE_EMAIL.put("4", "nate.com");
		CODE_EMAIL.put("5", "gmail.com");
		CODE_EMAIL.put("6", "outlook.com");
	}

	public Map<String, String> getCODE_HEALTH_DETAIL_NM() {
		return CODE_HEALTH_DETAIL_NM;
	}
	void setCODE_HEALTH_DETAIL_NM() {
		CODE_HEALTH_DETAIL_NM = new HashMap<String, String>();

		String msg1 = messageSource.getMessage("msg_0069", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0070", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0071", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_0072", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_0073", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg6 = messageSource.getMessage("msg_0074", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg7 = messageSource.getMessage("msg_0075", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg8 = messageSource.getMessage("msg_0076", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg9 = messageSource.getMessage("msg_0077", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg10 = messageSource.getMessage("msg_0078", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg11 = messageSource.getMessage("msg_0079", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg12 = messageSource.getMessage("msg_0080", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg13 = messageSource.getMessage("msg_0081", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg14 = messageSource.getMessage("msg_0082", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg15 = messageSource.getMessage("msg_0083", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg16 = messageSource.getMessage("msg_0084", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg17 = messageSource.getMessage("msg_0085", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg18 = messageSource.getMessage("msg_0086", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		CODE_HEALTH_DETAIL_NM.put("1", msg1); // msg1 활용
		CODE_HEALTH_DETAIL_NM.put("2", msg2); // msg2 활용
		CODE_HEALTH_DETAIL_NM.put("3", msg3); // msg3 활용
		CODE_HEALTH_DETAIL_NM.put("4", msg4); // msg4 활용
		CODE_HEALTH_DETAIL_NM.put("5", msg5); // msg5 활용
		CODE_HEALTH_DETAIL_NM.put("6", msg6); // msg6 활용
		CODE_HEALTH_DETAIL_NM.put("7", msg7); // msg7 활용
		CODE_HEALTH_DETAIL_NM.put("8", msg8); // msg8 활용
		CODE_HEALTH_DETAIL_NM.put("9", msg9); // msg9 활용
		CODE_HEALTH_DETAIL_NM.put("10", msg10); // msg10 활용
		CODE_HEALTH_DETAIL_NM.put("11", msg11); // msg11 활용
		CODE_HEALTH_DETAIL_NM.put("12", msg12); // msg12 활용
		CODE_HEALTH_DETAIL_NM.put("13", msg13); // msg13 활용
		CODE_HEALTH_DETAIL_NM.put("14", msg14); // msg14 활용
		CODE_HEALTH_DETAIL_NM.put("15", msg15); // msg15 활용
		CODE_HEALTH_DETAIL_NM.put("16", msg16); // msg16 활용
		CODE_HEALTH_DETAIL_NM.put("17", msg17); // msg17 활용
		CODE_HEALTH_DETAIL_NM.put("18", msg18); // msg18 활용
	}

	public Map<String, String> getCODE_JOB_NM() {
		return CODE_JOB_NM;
	}
	void setCODE_JOB_NM() {
		CODE_JOB_NM = new HashMap<String, String>();

		String msg1 = messageSource.getMessage("msg_0004", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0005", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0006", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_0007", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_0008", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg6 = messageSource.getMessage("msg_0009", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg7 = messageSource.getMessage("msg_0010", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg8 = messageSource.getMessage("msg_0011", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg9 = messageSource.getMessage("msg_0012", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg10 = messageSource.getMessage("msg_0013", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg11 = messageSource.getMessage("msg_0014", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg12 = messageSource.getMessage("msg_0015", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg13 = messageSource.getMessage("msg_0016", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg14 = messageSource.getMessage("msg_0017", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg15 = messageSource.getMessage("msg_0018", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg16 = messageSource.getMessage("msg_0019", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg17 = messageSource.getMessage("msg_0020", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg18 = messageSource.getMessage("msg_0021", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용


		CODE_JOB_NM.put("1", msg1); // msg1 활용
		CODE_JOB_NM.put("2", msg2); // msg2 활용
		CODE_JOB_NM.put("3", msg3); // msg3 활용
		CODE_JOB_NM.put("4", msg4); // msg4 활용
		CODE_JOB_NM.put("5", msg5); // msg5 활용
		CODE_JOB_NM.put("6", msg6); // msg6 활용
		CODE_JOB_NM.put("7", msg7); // msg7 활용
		CODE_JOB_NM.put("8", msg8); // msg8 활용
		CODE_JOB_NM.put("9", msg9); // msg9 활용
		CODE_JOB_NM.put("10", msg10); // msg10 활용
		CODE_JOB_NM.put("11", msg11); // msg11 활용
		CODE_JOB_NM.put("12", msg12); // msg12 활용
		CODE_JOB_NM.put("13", msg13); // msg13 활용
		CODE_JOB_NM.put("14", msg14); // msg14 활용
		CODE_JOB_NM.put("15", msg15); // msg15 활용
		CODE_JOB_NM.put("16", msg16); // msg16 활용
		CODE_JOB_NM.put("17", msg17); // msg17 활용
		CODE_JOB_NM.put("18", msg18); // msg18 활용
	}

	public Map<String, String> getCODE_LIKE_FIELD_NM() {
		return CODE_LIKE_FIELD_NM;
	}
	void setCODE_LIKE_FIELD_NM() {
		CODE_LIKE_FIELD_NM =  new HashMap<String, String>();

		String msg1 = messageSource.getMessage("msg_0087", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0088", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0089", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_0090", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_0091", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg6 = messageSource.getMessage("msg_0092", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg7 = messageSource.getMessage("msg_0093", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg8 = messageSource.getMessage("msg_0094", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg9 = messageSource.getMessage("msg_0095", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg10 = messageSource.getMessage("msg_0096", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg11 = messageSource.getMessage("msg_0097", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg12 = messageSource.getMessage("msg_0098", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg13 = messageSource.getMessage("msg_0099", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg14 = messageSource.getMessage("msg_0100", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg15 = messageSource.getMessage("msg_0101", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg16 = messageSource.getMessage("msg_0102", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg17 = messageSource.getMessage("msg_0103", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg18 = messageSource.getMessage("msg_0104", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg19 = messageSource.getMessage("msg_0105", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg20 = messageSource.getMessage("msg_0106", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg21 = messageSource.getMessage("msg_0107", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg22 = messageSource.getMessage("msg_0108", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg23 = messageSource.getMessage("msg_0109", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg24 = messageSource.getMessage("msg_0110", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg25 = messageSource.getMessage("msg_0111", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg26 = messageSource.getMessage("msg_0112", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg27 = messageSource.getMessage("msg_0113", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg28 = messageSource.getMessage("msg_0114", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg29 = messageSource.getMessage("msg_0115", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		CODE_LIKE_FIELD_NM.put("1", msg1); // msg1 활용
		CODE_LIKE_FIELD_NM.put("2", msg2); // msg2 활용
		CODE_LIKE_FIELD_NM.put("3", msg3); // msg3 활용
		CODE_LIKE_FIELD_NM.put("4", msg4); // msg4 활용
		CODE_LIKE_FIELD_NM.put("5", msg5); // msg5 활용
		CODE_LIKE_FIELD_NM.put("6", msg6); // msg6 활용
		CODE_LIKE_FIELD_NM.put("7", msg7); // msg7 활용
		CODE_LIKE_FIELD_NM.put("8", msg8); // msg8 활용
		CODE_LIKE_FIELD_NM.put("9", msg9); // msg9 활용
		CODE_LIKE_FIELD_NM.put("10", msg10); // msg10 활용
		CODE_LIKE_FIELD_NM.put("11", msg11); // msg11 활용
		CODE_LIKE_FIELD_NM.put("12", msg12); // msg12 활용
		CODE_LIKE_FIELD_NM.put("13", msg13); // msg13 활용
		CODE_LIKE_FIELD_NM.put("14", msg14); // msg14 활용
		CODE_LIKE_FIELD_NM.put("15", msg15); // msg15 활용
		CODE_LIKE_FIELD_NM.put("16", msg16); // msg16 활용
		CODE_LIKE_FIELD_NM.put("17", msg17); // msg17 활용
		CODE_LIKE_FIELD_NM.put("18", msg18); // msg18 활용
		CODE_LIKE_FIELD_NM.put("19", msg19); // msg19 활용
		CODE_LIKE_FIELD_NM.put("20", msg20); // msg20 활용
		CODE_LIKE_FIELD_NM.put("21", msg21); // msg21 활용
		CODE_LIKE_FIELD_NM.put("22", msg22); // msg22 활용
		CODE_LIKE_FIELD_NM.put("23", msg23); // msg23 활용
		CODE_LIKE_FIELD_NM.put("24", msg24); // msg24 활용
		CODE_LIKE_FIELD_NM.put("25", msg25); // msg25 활용
		CODE_LIKE_FIELD_NM.put("26", msg26); // msg26 활용
		CODE_LIKE_FIELD_NM.put("27", msg27); // msg27 활용
		CODE_LIKE_FIELD_NM.put("28", msg28); // msg28 활용
		CODE_LIKE_FIELD_NM.put("29", msg29); // msg29 활용
	}

	public Map<String, String> getCODE_MARRIAGE_NM() {
		return CODE_MARRIAGE_NM;
	}
	void setCODE_MARRIAGE_NM() {
		CODE_MARRIAGE_NM = new HashMap<String, String>();

		String msg1 = messageSource.getMessage("msg_0050", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0051", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0052", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		CODE_MARRIAGE_NM.put("1", msg1); // msg1 활용
		CODE_MARRIAGE_NM.put("2", msg2); // msg2 활용
		CODE_MARRIAGE_NM.put("3", msg3); // msg3 활용
	}

	public Map<String, String> getCODE_MEM_ALARM_VIEW_FIELD_CD() {
		return CODE_MEM_ALARM_VIEW_FIELD_CD;
	}
	public Map<String, String> getCODE_MEM_ALARM_VIEW_FIELD_NM() {
		return CODE_MEM_ALARM_VIEW_FIELD_NM;
	}
	public String getCODE_MEM_ALARM_VIEW_FIELD_CD_ANS_CHOICE() {
		return CODE_MEM_ALARM_VIEW_FIELD_CD_ANS_CHOICE;
	}
	public String getCODE_MEM_ALARM_VIEW_FIELD_CD_ANS_REGIST() {
		return CODE_MEM_ALARM_VIEW_FIELD_CD_ANS_REGIST;
	}
	public String getCODE_MEM_ALARM_VIEW_FIELD_CD_FAVORITE_QUE_REGIST() {
		return CODE_MEM_ALARM_VIEW_FIELD_CD_FAVORITE_QUE_REGIST;
	}
	public String getCODE_MEM_ALARM_VIEW_FIELD_CD_CMT_REGIST() {
		return CODE_MEM_ALARM_VIEW_FIELD_CD_CMT_REGIST;
	}
	public String getCODE_MEM_ALARM_VIEW_FIELD_CD_ANS_CHOICE_READY() {
		return CODE_MEM_ALARM_VIEW_FIELD_CD_ANS_CHOICE_READY;
	}
	public String getCODE_MEM_ALARM_VIEW_FIELD_CD_ALMONEY_INCOME() {
		return CODE_MEM_ALARM_VIEW_FIELD_CD_ALMONEY_INCOME;
	}
	public String getCODE_MEM_ALARM_VIEW_FIELD_CD_ALMONEY_PAYING() {
		return CODE_MEM_ALARM_VIEW_FIELD_CD_ALMONEY_PAYING;
	}
	public String getCODE_MEM_ALARM_VIEW_FIELD_CD_MEM_LEVEL_UP() {
		return CODE_MEM_ALARM_VIEW_FIELD_CD_MEM_LEVEL_UP;
	}
	public String getCODE_MEM_ALARM_VIEW_FIELD_CD_REPORT() {
		return CODE_MEM_ALARM_VIEW_FIELD_CD_REPORT;
	}
	public String getCODE_MEM_ALARM_VIEW_FIELD_CD_MENTEE() {
		return CODE_MEM_ALARM_VIEW_FIELD_CD_MENTEE;
	}
	public String getCODE_MEM_ALARM_VIEW_FIELD_CD_MENTEE_UNSET() {
		return CODE_MEM_ALARM_VIEW_FIELD_CD_MENTEE_UNSET;
	}
	public String getCODE_MEM_ALARM_VIEW_FIELD_CD_RECOMM_MEM_JOIN() {
		return CODE_MEM_ALARM_VIEW_FIELD_CD_RECOMM_MEM_JOIN;
	}
	public String getCODE_MEM_ALARM_VIEW_FIELD_CD_NOTICE() {
		return CODE_MEM_ALARM_VIEW_FIELD_CD_NOTICE;
	}
	public String getCODE_MEM_ALARM_VIEW_FIELD_CD_ALARM() {
		return CODE_MEM_ALARM_VIEW_FIELD_CD_ALARM;
	}
	void setCODE_MEM_ALARM_VIEW_FIELD() {
		CODE_MEM_ALARM_VIEW_FIELD_CD = new HashMap<String, String>();
		CODE_MEM_ALARM_VIEW_FIELD_NM = new HashMap<String, String>();

		CODE_MEM_ALARM_VIEW_FIELD_CD.put("1", "ANS_CHOICE");
		CODE_MEM_ALARM_VIEW_FIELD_CD.put("2", "ANS_REGIST");
		CODE_MEM_ALARM_VIEW_FIELD_CD.put("3", "FAVORITE_QUE_REGIST");
		CODE_MEM_ALARM_VIEW_FIELD_CD.put("4", "CMT_REGIST");
		CODE_MEM_ALARM_VIEW_FIELD_CD.put("5", "ANS_CHOICE_READY");
		CODE_MEM_ALARM_VIEW_FIELD_CD.put("6", "ALMONEY_INCOME");
		CODE_MEM_ALARM_VIEW_FIELD_CD.put("7", "ALMONEY_PAYING");
		CODE_MEM_ALARM_VIEW_FIELD_CD.put("8", "MEM_LEVEL_UP");
		CODE_MEM_ALARM_VIEW_FIELD_CD.put("9", "REPORT");
		CODE_MEM_ALARM_VIEW_FIELD_CD.put("10", "MENTEE");
		CODE_MEM_ALARM_VIEW_FIELD_CD.put("11", "MENTEE_UNSET");
		CODE_MEM_ALARM_VIEW_FIELD_CD.put("12", "RECOMM_MEM_JOIN");
		CODE_MEM_ALARM_VIEW_FIELD_CD.put("13", "NOTICE");
		CODE_MEM_ALARM_VIEW_FIELD_CD.put("14", "ALARM");


		String msg1 = messageSource.getMessage("msg_0676", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0677", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0678", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_0679", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_0691", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg6 = messageSource.getMessage("msg_0694", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg7 = messageSource.getMessage("msg_0695", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg8 = messageSource.getMessage("msg_0699", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg9 = messageSource.getMessage("msg_0229", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg10 = messageSource.getMessage("msg_0704", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg11 = messageSource.getMessage("msg_0706", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg12 = messageSource.getMessage("msg_0708", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg13 = messageSource.getMessage("msg_0185", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg14 = messageSource.getMessage("msg_0688", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용


		CODE_MEM_ALARM_VIEW_FIELD_NM.put("1", msg1); // msg1 활용
		CODE_MEM_ALARM_VIEW_FIELD_NM.put("2", msg2); // msg2 활용
		CODE_MEM_ALARM_VIEW_FIELD_NM.put("3", msg3); // msg3 활용
		CODE_MEM_ALARM_VIEW_FIELD_NM.put("4", msg4); // msg4 활용
		CODE_MEM_ALARM_VIEW_FIELD_NM.put("5", msg5); // msg5 활용
		CODE_MEM_ALARM_VIEW_FIELD_NM.put("6", msg6); // msg6 활용
		CODE_MEM_ALARM_VIEW_FIELD_NM.put("7", msg7); // msg7 활용
		CODE_MEM_ALARM_VIEW_FIELD_NM.put("8", msg8); // msg8 활용
		CODE_MEM_ALARM_VIEW_FIELD_NM.put("9", msg9); // msg9 활용
		CODE_MEM_ALARM_VIEW_FIELD_NM.put("10", msg10); // msg10 활용
		CODE_MEM_ALARM_VIEW_FIELD_NM.put("11", msg11); // msg11 활용
		CODE_MEM_ALARM_VIEW_FIELD_NM.put("12", msg12); // msg12 활용
		CODE_MEM_ALARM_VIEW_FIELD_NM.put("13", msg13); // msg13 활용
		CODE_MEM_ALARM_VIEW_FIELD_NM.put("14", msg14); // msg14 활용

		//코드 키워드 정의
		CODE_MEM_ALARM_VIEW_FIELD_CD_ANS_CHOICE 			= CODE_MEM_ALARM_VIEW_FIELD_CD.get("1");
		CODE_MEM_ALARM_VIEW_FIELD_CD_ANS_REGIST 			= CODE_MEM_ALARM_VIEW_FIELD_CD.get("2");
		CODE_MEM_ALARM_VIEW_FIELD_CD_FAVORITE_QUE_REGIST = CODE_MEM_ALARM_VIEW_FIELD_CD.get("3");
		CODE_MEM_ALARM_VIEW_FIELD_CD_CMT_REGIST 			= CODE_MEM_ALARM_VIEW_FIELD_CD.get("4");
		CODE_MEM_ALARM_VIEW_FIELD_CD_ANS_CHOICE_READY 	= CODE_MEM_ALARM_VIEW_FIELD_CD.get("5");
		CODE_MEM_ALARM_VIEW_FIELD_CD_ALMONEY_INCOME 		= CODE_MEM_ALARM_VIEW_FIELD_CD.get("6");
		CODE_MEM_ALARM_VIEW_FIELD_CD_ALMONEY_PAYING 		= CODE_MEM_ALARM_VIEW_FIELD_CD.get("7");
		CODE_MEM_ALARM_VIEW_FIELD_CD_MEM_LEVEL_UP 		= CODE_MEM_ALARM_VIEW_FIELD_CD.get("8");
		CODE_MEM_ALARM_VIEW_FIELD_CD_REPORT 				= CODE_MEM_ALARM_VIEW_FIELD_CD.get("9");
		CODE_MEM_ALARM_VIEW_FIELD_CD_MENTEE 				= CODE_MEM_ALARM_VIEW_FIELD_CD.get("10");
		CODE_MEM_ALARM_VIEW_FIELD_CD_MENTEE_UNSET 		= CODE_MEM_ALARM_VIEW_FIELD_CD.get("11");
		CODE_MEM_ALARM_VIEW_FIELD_CD_RECOMM_MEM_JOIN 	= CODE_MEM_ALARM_VIEW_FIELD_CD.get("12");
		CODE_MEM_ALARM_VIEW_FIELD_CD_NOTICE 				= CODE_MEM_ALARM_VIEW_FIELD_CD.get("13");
		CODE_MEM_ALARM_VIEW_FIELD_CD_ALARM 				= CODE_MEM_ALARM_VIEW_FIELD_CD.get("14");
	}

	public Map<String, String> getCODE_MEM_JOIN_CERT_TYPE_CD() {
		return CODE_MEM_JOIN_CERT_TYPE_CD;
	}
	public Map<String, String> getCODE_MEM_JOIN_CERT_TYPE_NM() {
		return CODE_MEM_JOIN_CERT_TYPE_NM;
	}
	void setCODE_MEM_JOIN_CERT_TYPE() {
		CODE_MEM_JOIN_CERT_TYPE_CD = new HashMap<String, String>();
		CODE_MEM_JOIN_CERT_TYPE_NM = new HashMap<String, String>();

		CODE_MEM_JOIN_CERT_TYPE_CD.put("1", "N");
		CODE_MEM_JOIN_CERT_TYPE_CD.put("2", "S");

		String msg1 = messageSource.getMessage("msg_0995", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0996", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		CODE_MEM_JOIN_CERT_TYPE_NM.put("1", msg1); // msg1 활용
		CODE_MEM_JOIN_CERT_TYPE_NM.put("2", msg2); // msg2 활용
	}


	public Map<String, String> getCODE_REAL_ESTATE_NM() {
		return CODE_REAL_ESTATE_NM;
	}

	void setCODE_REAL_ESTATE_NM() {
		CODE_REAL_ESTATE_NM = new HashMap<String, String>();

		String msg1 = messageSource.getMessage("msg_0041", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0042", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0043", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_0044", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		CODE_REAL_ESTATE_NM.put("1", msg1); // msg1 활용
		CODE_REAL_ESTATE_NM.put("2", msg2); // msg2 활용
		CODE_REAL_ESTATE_NM.put("3", msg3); // msg3 활용
		CODE_REAL_ESTATE_NM.put("4", msg4); // msg4 활용
	}



	public Map<String, String> getCODE_REPORT_ADMIN_STATUS_CD() {
		return CODE_REPORT_ADMIN_STATUS_CD;
	}
	public Map<String, String> getCODE_REPORT_ADMIN_STATUS_NM() {
		return CODE_REPORT_ADMIN_STATUS_NM;
	}
	public String getCODE_REPORT_ADMIN_STATUS_DEFAULT() {
		return CODE_REPORT_ADMIN_STATUS_DEFAULT;
	}
	public String getCODE_REPORT_ADMIN_STATUS_READY() {
		return CODE_REPORT_ADMIN_STATUS_READY;
	}
	public String getCODE_REPORT_ADMIN_STATUS_LOCK() {
		return CODE_REPORT_ADMIN_STATUS_LOCK;
	}
	public String getCODE_REPORT_ADMIN_STATUS_DELETE() {
		return CODE_REPORT_ADMIN_STATUS_DELETE;
	}
	public String getCODE_REPORT_ADMIN_STATUS_HOLD() {
		return CODE_REPORT_ADMIN_STATUS_HOLD;
	}
	public String getCODE_REPORT_ADMIN_STATUS_ETC() {
		return CODE_REPORT_ADMIN_STATUS_ETC;
	}
	void setCODE_REPORT_ADMIN_STATUS() {
		CODE_REPORT_ADMIN_STATUS_CD = new HashMap<String, String>();
		CODE_REPORT_ADMIN_STATUS_NM = new HashMap<String, String>();

		CODE_REPORT_ADMIN_STATUS_CD.put("1", "READY");
		CODE_REPORT_ADMIN_STATUS_CD.put("2", "LOCK");
		CODE_REPORT_ADMIN_STATUS_CD.put("3", "DELETE");
		CODE_REPORT_ADMIN_STATUS_CD.put("4", "HOLD");
		CODE_REPORT_ADMIN_STATUS_CD.put("5", "ETC");

		String msg1 = messageSource.getMessage("hello", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("hello", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("hello", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("hello", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("hello", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		CODE_REPORT_ADMIN_STATUS_NM.put("1", "접수 (처리 전)"); // msg1 활용
		CODE_REPORT_ADMIN_STATUS_NM.put("2", "처리 (글 잠금)"); // msg2 활용
		CODE_REPORT_ADMIN_STATUS_NM.put("3", "처리 (글 삭제)"); // msg3 활용
		CODE_REPORT_ADMIN_STATUS_NM.put("4", "처리 보류"); // msg4 활용
		CODE_REPORT_ADMIN_STATUS_NM.put("5", "기타"); // msg5 활용

		CODE_REPORT_ADMIN_STATUS_DEFAULT = CODE_REPORT_ADMIN_STATUS_CD.get("1");
		CODE_REPORT_ADMIN_STATUS_READY = CODE_REPORT_ADMIN_STATUS_CD.get("1");
		CODE_REPORT_ADMIN_STATUS_LOCK = CODE_REPORT_ADMIN_STATUS_CD.get("2");
		CODE_REPORT_ADMIN_STATUS_DELETE = CODE_REPORT_ADMIN_STATUS_CD.get("3");
		CODE_REPORT_ADMIN_STATUS_HOLD = CODE_REPORT_ADMIN_STATUS_CD.get("4");
		CODE_REPORT_ADMIN_STATUS_ETC = CODE_REPORT_ADMIN_STATUS_CD.get("5");
	}

	public Map<String, String> getCODE_REPORT_CONTENTS_CD() {
		return CODE_REPORT_CONTENTS_CD;
	}
	public Map<String, String> getCODE_REPORT_CONTENTS_NM() {
		return CODE_REPORT_CONTENTS_NM;
	}
	public String getCODE_REPORT_CONTENTS_QUE() {
		return CODE_REPORT_CONTENTS_QUE;
	}
	public String getCODE_REPORT_CONTENTS_QUE_REPLY() {
		return CODE_REPORT_CONTENTS_QUE_REPLY;
	}
	public String getCODE_REPORT_CONTENTS_ANS() {
		return CODE_REPORT_CONTENTS_ANS;
	}
	public String getCODE_REPORT_CONTENTS_ANS_REPLY() {
		return CODE_REPORT_CONTENTS_ANS_REPLY;
	}
	void setCODE_REPORT_CONTENTS() {
		CODE_REPORT_CONTENTS_CD = new HashMap<String, String>();
		CODE_REPORT_CONTENTS_NM = new HashMap<String, String>();

		CODE_REPORT_CONTENTS_CD.put("1", "Question");				// Question 질문글
		CODE_REPORT_CONTENTS_CD.put("2", "QuestionReply");		// ReplyQuestion 질문글 댓글
		CODE_REPORT_CONTENTS_CD.put("3", "Answer");				// Answer 답변글
		CODE_REPORT_CONTENTS_CD.put("4", "AnswerReply");		// ReplyAnswer 답변글 댓글

		String msg1 = messageSource.getMessage("msg_0365", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0997", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0366", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_0998", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		CODE_REPORT_CONTENTS_NM.put("1", msg1); // msg1 활용
		CODE_REPORT_CONTENTS_NM.put("2", msg2); // msg2 활용
		CODE_REPORT_CONTENTS_NM.put("3", msg3); // msg3 활용
		CODE_REPORT_CONTENTS_NM.put("4", msg4); // msg4 활용

		CODE_REPORT_CONTENTS_QUE = CODE_REPORT_CONTENTS_CD.get("1");
		CODE_REPORT_CONTENTS_QUE_REPLY = CODE_REPORT_CONTENTS_CD.get("2");
		CODE_REPORT_CONTENTS_ANS = CODE_REPORT_CONTENTS_CD.get("3");
		CODE_REPORT_CONTENTS_ANS_REPLY = CODE_REPORT_CONTENTS_CD.get("4");
	}


	public Map<String, String> getCODE_REPORT_REASON_CD() {
		return CODE_REPORT_REASON_CD;
	}
	public Map<String, String> getCODE_REPORT_REASON_NM() {
		return CODE_REPORT_REASON_NM;
	}
	public String getCODE_REPORT_REASON_CD_SPAM() {
		return CODE_REPORT_REASON_CD_SPAM;
	}
	public String getCODE_REPORT_REASON_CD_ADULTS() {
		return CODE_REPORT_REASON_CD_ADULTS;
	}
	public String getCODE_REPORT_REASON_CD_TRICKS() {
		return CODE_REPORT_REASON_CD_TRICKS;
	}
	public String getCODE_REPORT_REASON_CD_ETC() {
		return CODE_REPORT_REASON_CD_ETC;
	}
	void setCODE_REPORT_REASON() {
		CODE_REPORT_REASON_CD = new HashMap<String, String>();
		CODE_REPORT_REASON_NM = new HashMap<String, String>();

		CODE_REPORT_REASON_CD.put("1", "Question");
		CODE_REPORT_REASON_CD.put("2", "QuestionReply");
		CODE_REPORT_REASON_CD.put("3", "Answer");
		CODE_REPORT_REASON_CD.put("4", "AnswerReply");

		String msg1 = messageSource.getMessage("msg_0365", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0997", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0366", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_0998", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		CODE_REPORT_REASON_NM.put("1", msg1);		// Question 질문글 // msg1 활용
		CODE_REPORT_REASON_NM.put("2", msg2);	// ReplyQuestion 질문글 댓글 // msg2 활용
		CODE_REPORT_REASON_NM.put("3", msg3);		// Answer 답변글 // msg3 활용
		CODE_REPORT_REASON_NM.put("4", msg4);	// ReplyAnswer 답변글 댓글 // msg4 활용

		CODE_REPORT_REASON_CD_SPAM 		= CODE_REPORT_REASON_CD.get("1");
		CODE_REPORT_REASON_CD_ADULTS 	= CODE_REPORT_REASON_CD.get("2");
		CODE_REPORT_REASON_CD_TRICKS 	= CODE_REPORT_REASON_CD.get("3");
		CODE_REPORT_REASON_CD_ETC	= CODE_REPORT_REASON_CD.get("4");
	}



	public Map<String, String> getCODE_RESIDENCE_NM() {
		return CODE_RESIDENCE_NM;
	}
	void setCODE_RESIDENCE_NM() {
		CODE_RESIDENCE_NM = new HashMap<String, String>();

		String msg1 = messageSource.getMessage("msg_0045", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0046", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0047", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_0048", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_0049", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		CODE_RESIDENCE_NM.put("1", msg1); // msg1 활용
		CODE_RESIDENCE_NM.put("2", msg2); // msg2 활용
		CODE_RESIDENCE_NM.put("3", msg3); // msg3 활용
		CODE_RESIDENCE_NM.put("4", msg4); // msg4 활용
		CODE_RESIDENCE_NM.put("5", msg5); // msg5 활용
	}


	public Map<String, String> getCODE_YEAR_INCOME_NM() {
		return CODE_YEAR_INCOME_NM;
	}
	void setCODE_YEAR_INCOME_NM() {
		CODE_YEAR_INCOME_NM = new HashMap<String, String>();

		String msg1 = messageSource.getMessage("msg_0063", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_0064", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_0065", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_0066", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_0067", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg6 = messageSource.getMessage("msg_0068", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		CODE_YEAR_INCOME_NM.put("1", msg1); // msg1 활용
		CODE_YEAR_INCOME_NM.put("2", msg2); // msg2 활용
		CODE_YEAR_INCOME_NM.put("3", msg3); // msg3 활용
		CODE_YEAR_INCOME_NM.put("4", msg4); // msg4 활용
		CODE_YEAR_INCOME_NM.put("5", msg5); // msg5 활용
		CODE_YEAR_INCOME_NM.put("6", msg6); // msg6 활용
	}
}
