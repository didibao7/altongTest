package com.altong.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.altong.web.logic.member.MemberVO;
import com.altong.web.logic.util.CommonUtil;
import com.altong.web.service.CookieLoginService;
import com.altong.web.service.alpay.AlpayService;
import com.altong.web.service.common.CommonService;
import com.altong.web.service.member.MemberService;

@Controller
@RequestMapping("alpay/*")
public class AlpayController {
	@Autowired
	CommonService commonService;

	@Autowired
	AlpayService alpayService;

	@Autowired
	MemberService memberService;

	@Autowired
	CookieLocaleResolver localResolver;

	@Autowired
	MessageSource messageSource;

	@Autowired
	CookieLoginService cookieLoginService;

	@RequestMapping("user/alpayUserMain")
	public ModelAndView alpayUserMain(HttpServletRequest request, HttpServletResponse response) throws Exception {

		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq == 0) {
			mav.setViewName("redirect:/default/main");

			return mav;
		}


		String ACT = request.getParameter("ACT");

		return mav;
	}

	@RequestMapping(value="user/alpayUserMainAjax", method = RequestMethod.POST)
	public @ResponseBody String alpayUserMainAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");

		String ACT = request.getParameter("ACT");
		JSONObject jsonTotal = new JSONObject();
		ArrayList arr = new ArrayList();
		String jsonObj= null;

		if(String.valueOf(ACT).equals("SET_HongUni")) {
			/*
			if (!$r) $r = array();
			libJsonExit($_POST['ACT'], $r);
			*/

	    	jsonObj = CommonUtil.convertJsonToString(ACT, arr);
			//System.out.println("SET_HongUni jsonObj : " + JSONValue.toJSONString(jsonObj) );
			//return jsonString;

		}
		else if(String.valueOf(ACT).equals("SET_SeoulUni")) {
			/*
			if (!$r) $r = array();
			libJsonExit($_POST['ACT'], $r);
			*/

	    	jsonObj = CommonUtil.convertJsonToString(ACT, arr);
			//System.out.println("SET_HongUni jsonObj : " + JSONValue.toJSONString(jsonObj) );
		}
		else if(String.valueOf(ACT).equals("SET_Guro")) {
			/*
			$lat = 37.48398245621276;
			$lon = 126.89995941824564;

			libJsonExit($_POST['ACT']);
			*/

			String sessExpire = !cookieLoginService.getCookieSessExpire(request, response).equals("") ? cookieLoginService.getCookieSessExpire(request, response) : "0";

			if(sessExpire.equals("")) { sessExpire = "1"; }

			Cookie lat = new Cookie("lat", "37.48398245621276" );
			lat.setVersion( 0 );
			lat.setMaxAge( Integer.parseInt(sessExpire) * 24 * 60 * 60 );
			lat.setPath("/");

			Cookie lon = new Cookie("lon", "126.89995941824564" );
			lon.setVersion( 0 );
			lon.setMaxAge( Integer.parseInt(sessExpire) * 24 * 60 * 60 );
			lon.setPath("/");

			response.addCookie(lat);
			response.addCookie(lon);

	    	jsonObj = CommonUtil.convertJsonToString(ACT, arr);
			//System.out.println("SET_HongUni jsonObj : " + JSONValue.toJSONString(jsonObj) );
		}
		else if(String.valueOf(ACT).equals("SET_GangNam")) {
			/*
			if (!$r) $r = array();
			libJsonExit($_POST['ACT'], $r);
			*/

	    	jsonObj = CommonUtil.convertJsonToString(ACT, arr);
			//System.out.println("SET_HongUni jsonObj : " + JSONValue.toJSONString(jsonObj) );
		}
		else {
			//libJsonExit('');
		}

		return jsonObj;
	}

	@RequestMapping("user/alpayUser")
	public ModelAndView alpayUser(HttpServletRequest request, HttpServletResponse response) throws Exception {

		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq == 0) {
			mav.setViewName("redirect:/default/main");

			return mav;
		}

		return mav;
	}

	@RequestMapping(value="user/alpayUserAjax", method = RequestMethod.POST)
	public @ResponseBody String alpayUserAjax(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		String ACT = request.getParameter("ACT");
		HashMap<String, Object> arr = new HashMap<String, Object>();
		String jsonObj= null;

		if(String.valueOf(ACT).equals("UserAlpayKR")) {
			/*
			select AlpayKR from T_MEMBERS where Seq=?	userSeq
			$UserAlpayKR = (int)$r;

			libJsonExit($_POST['ACT'], array('UserAlpayKR'=>number_format($UserAlpayKR)));
			*/
			int userAlpayKR = alpayService.getUserAlpayKR(userSeq);

			arr.put("UserAlpayKR", userAlpayKR);

	    	jsonObj = CommonUtil.convertJsonToString(ACT, arr);
			//System.out.println("SET_HongUni jsonObj : " + JSONValue.toJSONString(jsonObj) );
			//return jsonString;

		}

		return jsonObj;
	}

	@RequestMapping("user/sub/exchange")
	public ModelAndView alpayUserExchange(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		BigDecimal exchRate = new BigDecimal(0.035);


		//알페이 잔고 불러오기
		MemberVO mem = memberService.getAlmoneyMyJoin(userSeq);
		BigDecimal exchangeLimitAlpay = mem.getAlpayKR();

		if(exchangeLimitAlpay.compareTo(BigDecimal.ZERO) < 0) {
			exchangeLimitAlpay = BigDecimal.ZERO;
		}

		mav.addObject("exchRate", exchRate);
		mav.addObject("exchangeLimitAlpay", exchangeLimitAlpay);

		mav.addObject("userSeq", userSeq);
		return mav;
	}

	@RequestMapping(value="user/sub/exchangeAjax", produces="application/json;charset=UTF-8", method = {RequestMethod.POST})
	public @ResponseBody String alpayUserExchangeAjax(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		String msg1 = messageSource.getMessage("msg_1010", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1011", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_1012", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_1013", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_1014", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg6 = messageSource.getMessage("msg_1015", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg7 = messageSource.getMessage("msg_1016", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg8 = messageSource.getMessage("msg_0648", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg9 = messageSource.getMessage("msg_1017", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		String act = "";
		if(request.getParameter("ACT") != null) {
			act = String.valueOf( request.getParameter("ACT") );
		}
		HashMap<String, Object> blankHash = new HashMap<String, Object>();
		if(act.equals("GetLastAccount")) {
			HashMap<String, Object> r = commonService.getAlpayLastAccount(userSeq);

			if(r != null && r.get("BankName") != null) {
				return CommonUtil.libJsonExit(act, r);
			}
			else {
				return CommonUtil.libJsonExit(msg1, blankHash); // msg1 활용
			}
		}
		if(act.equals("EXCHANGE")) {
			int exchangeAlpay = Integer.parseInt( request.getParameter("H_AmountAlpay") != null && !request.getParameter("H_AmountAlpay").equals("") ? request.getParameter("H_AmountAlpay") : "0" );
			String bankName = request.getParameter("BankName");
			String bankAccountNo = request.getParameter("BankAccountNo");
			String bankMemNm = request.getParameter("BankMemNm");
			int realMoney = (int)(exchangeAlpay * (1 - 0.035));

			if (bankName.equals(""))
				return CommonUtil.libJsonExit(msg2, blankHash); // msg2 활용

			if (bankAccountNo.equals(""))
				return CommonUtil.libJsonExit(msg3, blankHash); // msg3 활용

			if (bankMemNm.equals(""))
				return CommonUtil.libJsonExit(msg4, blankHash); // msg4 활용

			if (exchangeAlpay < 1000)
				return CommonUtil.libJsonExit(msg5, blankHash); // msg5 활용

			if (exchangeAlpay != exchangeAlpay / 1000 * 1000)
				return CommonUtil.libJsonExit(msg6, blankHash); // msg6 활용

			// ExchangeStatus = 1 : 출금대기, 2 : 출금완료, 3 : 출금거부

			String newSeq = CommonUtil.getTimestamp();
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("userSeq", userSeq);
			param.put("newSeq", newSeq); //list($microtime,$timestamp) = explode(' ',microtime());
			param.put("exchangeAlpay", exchangeAlpay);
			param.put("store_code", 0);
			param.put("bankName", bankName);
			param.put("bankAccountNo", bankAccountNo);
			param.put("bankMemNm", bankMemNm);
			param.put("realMoney", realMoney);



			int r = commonService.setAlpayExchange(param);

			switch(r) {
				case 1:
					return CommonUtil.libJsonExit(act, blankHash);
				case 2:
					return CommonUtil.libJsonExit(msg7, blankHash); // msg7 활용
				default:
					return CommonUtil.libJsonExit(msg8 + "\\n" + msg9, blankHash); // msg8, msg9 활용
			}
		}


		return null;

	}
}
