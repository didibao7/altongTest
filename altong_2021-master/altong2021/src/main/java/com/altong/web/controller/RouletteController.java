package com.altong.web.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.altong.web.logic.util.CommonUtil;
import com.altong.web.service.CookieLoginService;
import com.altong.web.service.common.CommonService;
import com.altong.web.service.member.MemberService;
import com.google.gson.Gson;

@Controller
@RequestMapping("roulette/*")
public class RouletteController {

	@Autowired
	MemberService memberService;

	@Autowired
	CommonService commonService;

	@Autowired
	CookieLocaleResolver localResolver;

	@Autowired
	MessageSource messageSource;

	@Autowired
	CookieLoginService cookieLoginService;

	@RequestMapping("game")
	public ModelAndView game(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		ModelAndView mav = new ModelAndView();

		if(request.getParameter("LoginFlag") != "") {
			mav.addObject("LoginFlag", request.getParameter("LoginFlag"));
		}

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

	    if(userSeq == 0) {
	    	String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
	    	CommonUtil.jspAlert(response, msg1, "/default/login", "parent.parent"); // msg1 활용
			return null;
		}

	    HashMap<String, Object> gConfig = commonService.getTicketConfig();
		int queCountConfig = Integer.parseInt( String.valueOf( gConfig.get("queCount") ) );
		int ansCountConfig = Integer.parseInt( String.valueOf( gConfig.get("ansCount") ) );
		int replCountConfig = Integer.parseInt( String.valueOf( gConfig.get("replCount") ) );
		int hunCountConfig = Integer.parseInt( String.valueOf( gConfig.get("hunCount") ) );
		int estiCountConfig = Integer.parseInt( String.valueOf( gConfig.get("estiCount") ) );

		mav.addObject("userSeq", userSeq);
		HashMap<String, Object> localeItem = CommonUtil.getLocale(locale);
    	String lang = String.valueOf(localeItem.get("lang"));

		mav.addObject("queCountConfig", queCountConfig);
		mav.addObject("ansCountConfig", ansCountConfig);
		mav.addObject("replCountConfig", replCountConfig);
		mav.addObject("hunCountConfig", hunCountConfig);
		mav.addObject("estiCountConfig", estiCountConfig);
		mav.addObject("lang", lang);

		return mav;
	}

	@RequestMapping(value="/roulette/getTicket", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String getTicket(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String result = "";

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq == 0) {
			String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용

			result = "{\"returnCode\" : -1}";

			return new Gson().toJson(result);
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)

		Calendar cal = Calendar.getInstance();
		String toDay = formatter.format(cal.getTime());

		/*
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		//해당 주차 시작일과의 차이 구하기용
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - cal.getFirstDayOfWeek();
		//해당 주차의 첫날 세팅
		cal.add(Calendar.DAY_OF_MONTH, - dayOfWeek);
		//해당 주차의 첫일자
		String startDt = formatter.format(cal.getTime());
		//해당 주차의 마지막 세팅
		cal.add(Calendar.DAY_OF_MONTH, 6);
		//해당 주차의 마지막일자
		String endDt = formatter.format(cal.getTime());
		*/

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("mem_seq", userSeq);
		//param.put("startDt", startDt); // 일주일의 시작 일자
		param.put("endDt", toDay); // 일주일의 종료 일자( 변경 : 오늘 일자 기준 종료 되지 않는 이용권이 있는지 검색)

		int seq = commonService.getTicket(param);

		String returnMSG = "";


		if(seq > 0) { returnMSG = "OK"; }

		result = "{\"returnCode\" : " + seq + ", \"returnMSG\" : \"" + returnMSG + "\"}";

		return new Gson().toJson(result);
	}

	@RequestMapping(value="/roulette/getTicketCount", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String getTicketCount(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String result = "";

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq == 0) {
			String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용

			result = "{\"returnCode\" : -1}";

			return new Gson().toJson(result);
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)

		Calendar cal = Calendar.getInstance();
		String toDay = formatter.format(cal.getTime());

		/*
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		//해당 주차 시작일과의 차이 구하기용
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - cal.getFirstDayOfWeek();
		//해당 주차의 첫날 세팅
		cal.add(Calendar.DAY_OF_MONTH, - dayOfWeek);
		//해당 주차의 첫일자
		String startDt = formatter.format(cal.getTime());
		//해당 주차의 마지막 세팅
		cal.add(Calendar.DAY_OF_MONTH, 6);
		//해당 주차의 마지막일자
		String endDt = formatter.format(cal.getTime());
		*/

		HashMap<String, Object> gConfig = commonService.getTicketConfig();
		//System.out.println(gConfig.toString());
		int queCountConfig = Integer.parseInt( String.valueOf( gConfig.get("queCount") ) );
		int ansCountConfig = Integer.parseInt( String.valueOf( gConfig.get("ansCount") ) );
		int replCountConfig = Integer.parseInt( String.valueOf( gConfig.get("replCount") ) );
		int hunCountConfig = Integer.parseInt( String.valueOf( gConfig.get("hunCount") ) );
		int estiCountConfig = Integer.parseInt( String.valueOf( gConfig.get("estiCount") ) );
		//System.out.println("hunCountConfig : " + hunCountConfig);
		//System.out.println("estiCountConfig : " + estiCountConfig);

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("userSeq", userSeq);
		//param.put("startDt", startDt); // 일주일의 시작 일자
		param.put("endDt", toDay); // 일주일의 종료 일자( 변경 : 오늘 일자 기준 종료 되지 않는 이용권이 있는지 검색)

		int count = commonService.getTicketCount(param);
		//System.out.println("count : " + count);
		int stackCnt = commonService.getTicketStackCnt(userSeq);
		//System.out.println("stackCnt : " + stackCnt);
		int queCount = 0;
		int ansCount = 0;
		int replCount = 0;
		int hunCount = 0;
		int estiCount = 0;

		if(stackCnt > 0) {
			HashMap<String, Object> stackInfo =  commonService.getTicketStack(userSeq);

			queCount = Integer.parseInt( String.valueOf( stackInfo.get("queCount") ) );
			ansCount = Integer.parseInt( String.valueOf( stackInfo.get("ansCount") ) );
			replCount = Integer.parseInt( String.valueOf( stackInfo.get("replCount") ) );
			hunCount = Integer.parseInt( String.valueOf( stackInfo.get("hunCount") ) );
			estiCount = Integer.parseInt( String.valueOf( stackInfo.get("estiCount") ) );
		}
		//System.out.println("hunCount : " +hunCount);
		//System.out.println("estiCount : " +estiCount);

		queCount = queCountConfig - queCount;
		ansCount = ansCountConfig - ansCount;
		replCount = replCountConfig - replCount;
		hunCount = hunCountConfig - hunCount;
		estiCount = estiCountConfig - estiCount;

		//System.out.println("hunCount : " +hunCount);
		//System.out.println("estiCount : " +estiCount);


		result = "{\"count\" : " + count + ", \"queCount\" : " + queCount + ", \"ansCount\" : " + ansCount + ", \"replCount\" : " + replCount +", \"hunCount\" : " + hunCount + ", \"estiCount\" : " + estiCount + "}";

		return new Gson().toJson(result);
	}

	@RequestMapping(value="/roulette/setTicketUse", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String getTicketUse(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String result = "";

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		int divPrice = 0; // 당첨금 정의

		if(request.getParameter("money") != "") {
			divPrice = Integer.parseInt(request.getParameter("money") );
		}

		if(userSeq == 0) {
			String msg1 = messageSource.getMessage("msg_0168", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
			CommonUtil.jspAlert(response, msg1, "back", ""); // msg1 활용

			result = "{\"returnCode\" : -1}";

			return new Gson().toJson(result);
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)

		Calendar cal = Calendar.getInstance();
		String toDay = formatter.format(cal.getTime());

		/*
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		//해당 주차 시작일과의 차이 구하기용
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - cal.getFirstDayOfWeek();
		//해당 주차의 첫날 세팅
		cal.add(Calendar.DAY_OF_MONTH, - dayOfWeek);
		//해당 주차의 첫일자
		String startDt = formatter.format(cal.getTime());
		//해당 주차의 마지막 세팅
		cal.add(Calendar.DAY_OF_MONTH, 6);
		//해당 주차의 마지막일자
		String endDt = formatter.format(cal.getTime());
		*/

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("userSeq", userSeq);
		//param.put("startDt", startDt); // 일주일의 시작 일자
		param.put("endDt", toDay); // 일주일의 종료 일자( 변경 : 오늘 일자 기준 종료 되지 않는 이용권이 있는지 검색)

		int seq = commonService.getTicket(param);
		//System.out.println("seq : " + seq);

		// 룰렛 당첨금 처리
		HashMap<String, Object> mParam = new HashMap<String, Object>();
		mParam.put("mem_seq", userSeq);
		mParam.put("mem_almoney", divPrice); // 당첨 금 알
		mParam.put("almoney_flag", "48"); //SP_CHANGE_ALMONEY Roullette 수입
		// 회원별 당첨알 지급
		commonService.setUpdateAlmoney(mParam);

		// 이용권 완료 처리
		HashMap<String, Object> uParam = new HashMap<String, Object>();
		uParam.put("seq", seq);
		uParam.put("userSeq", userSeq);
		commonService.setTickUse(uParam);

		int count = commonService.getTicketCount(param);
		//System.out.println("count : " + count);
		int stackCnt = commonService.getTicketStackCnt(userSeq);
		//System.out.println("stackCnt : " + stackCnt);
		int queCount = 0;
		int ansCount = 0;
		int replCount = 0;

		if(stackCnt > 0) {
			HashMap<String, Object> stackInfo =  commonService.getTicketStack(userSeq);

			queCount = Integer.parseInt( String.valueOf( stackInfo.get("queCount") ) );
			ansCount = Integer.parseInt( String.valueOf( stackInfo.get("ansCount") ) );
			replCount = Integer.parseInt( String.valueOf( stackInfo.get("replCount") ) );
		}

		result = "{\"count\" : " + count + ", \"queCount\" : " + queCount + ", \"ansCount\" : " + ansCount + ", \"replCount\" : " + replCount + ", \"returnCode\" : 0}";

		return new Gson().toJson(result);
	}
}
