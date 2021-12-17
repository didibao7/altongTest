package com.altong.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

import com.altong.web.logic.MessageVO;
import com.altong.web.logic.util.CommonUtil;
import com.altong.web.service.CookieLoginService;
import com.altong.web.service.MessageService;
import com.altong.web.service.member.MemberService;

@Controller
@RequestMapping("message/*")
public class MessageController {
	@Autowired
	MessageService messageService;

	@Autowired
	MemberService memberService;

	@Autowired
	CookieLocaleResolver localResolver;

	@Autowired
	MessageSource messageSource;

	@Autowired
	CookieLoginService cookieLoginService;

	@RequestMapping("message/message")
	public ModelAndView rankAnswer(HttpServletRequest request
			, HttpServletResponse response, Locale locale) throws Exception {

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(request.getParameter("CallAlarmFlag") != null) {
			String format = "yyyy-MM-dd HH:mm:ss";
			Calendar todays = Calendar.getInstance();
			SimpleDateFormat type = new SimpleDateFormat(format);
			String currentTime = type.format(todays.getTime());

			String CallAlarmFlag = request.getParameter("CallAlarmFlag");

			if( userSeq > 0 && CallAlarmFlag.equals("Y")) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("userSeq", userSeq);
				param.put("alamCode", "ALARM");
				param.put("dateReg", currentTime);
				memberService.setMemberAlarmLog(param);

			}
		}

		ModelAndView mav = new ModelAndView();

		return mav;
	}

	@RequestMapping(value="/message/messageAjax", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String messageAjax(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		String act = request.getParameter("ACT");
		int maxRow = 30;
		int pg = 1;

		if(request.getParameter("pg") != null && !request.getParameter("pg").equals("")) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		String msg1 = messageSource.getMessage("msg_1077", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg2 = messageSource.getMessage("msg_1045", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg3 = messageSource.getMessage("msg_1078", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg4 = messageSource.getMessage("msg_1079", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg5 = messageSource.getMessage("msg_1080", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
		String msg6 = messageSource.getMessage("msg_1081", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용

		if(act.equals("LIST_RECEIVE")) {
			//getReceiveMsg
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("receiver", userSeq);
			param.put("pg", pg);
			param.put("maxRow", maxRow);

			List<MessageVO> list = messageService.getReceiveMsg(param);

			return CommonUtil.libJsonArrExit(act, list);
		}
		else if(act.equals("LIST_SEND")) {
			//getSendMsg
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("sender", userSeq);
			param.put("pg", pg);
			param.put("maxRow", maxRow);

			List<MessageVO> list = messageService.getSendMsg(param);

			return CommonUtil.libJsonArrExit(act, list);
		}
		else if(act.equals("SEND")) {
			List<HashMap<String, Object>> list =  new ArrayList<HashMap<String, Object>>();

			String targetMemSeq = request.getParameter("MemberSeq");
			String contents = request.getParameter("Contents");

			if(targetMemSeq == null || targetMemSeq == "") { return null; }

			if(targetMemSeq.equals( String.valueOf(userSeq))  )  { return CommonUtil.libJsonArrExit(msg1, list); }
			if(request.getParameter("Contents") == null && request.getParameter("Contents") == "") { return CommonUtil.libJsonArrExit(msg2, list); }

			//setSendMsg
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("userSeq", userSeq);
			param.put("blockUserSeq", targetMemSeq);
			param.put("contents", contents);

			int result = messageService.setSendMsg(param);

			switch(result) {
				case 1:
					return CommonUtil.libJsonArrExit(msg3, list); // msg3 활용
				case 2:
					return CommonUtil.libJsonArrExit(msg4 + "\\n" + msg5, list); // msg4, msg5 활용
				case 3:
					return CommonUtil.libJsonArrExit(msg6, list); // msg6 활용
				default:
					return CommonUtil.libJsonArrExit(act, list);
			}
		}
		else if(act.equals("MSG_DEL")) {
			//setDelMsg
			if(request.getParameter("MsgSeq") == null || request.getParameter("MsgSeq") == "") {
				return null;
			}

			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("userSeq", userSeq);
			param.put("msgSeq", request.getParameter("MsgSeq"));

			messageService.setDelMsg(param);

			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("MsgSeq", request.getParameter("MsgSeq"));
			List<HashMap<String, Object>> list =  new ArrayList<HashMap<String, Object>>();
			list.add(data);

			return CommonUtil.libJsonArrExit(act, list);
		}
		else if(act.equals("SET_READ")) {
			//setReadMsg
			if(request.getParameter("MsgSeq") == null || request.getParameter("MsgSeq") == "") {
				return null;
			}

			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("receiver", userSeq);
			param.put("seq", request.getParameter("MsgSeq"));

			messageService.setReadMsg(param);

			return CommonUtil.libJsonArrExit(null, null);
		}
		else if(act.equals("GET_MSG")) {
			if(request.getParameter("MsgSeq") == null || request.getParameter("MsgSeq") == "") {
				return null;
			}

			String orderby = "";
			String sign = "";
			int begin = 0;
			int end = 0;

			HashMap<String, Object> param = new HashMap<String, Object>();

			switch(request.getParameter("way")) {
				case "NEXT":
					orderby = "order by S.Seq asc";
					sign = ">";
					break;
				case "PREV":
					orderby = "order by S.Seq desc";
					sign = "<";
					break;
				default:
					orderby = "";
					sign = "=";
					break;
			}

			int msgSeq = Integer.parseInt( request.getParameter("MsgSeq") );
			String tab = request.getParameter("tab");

			param.put("userSeq", userSeq);
			param.put("msgSeq", msgSeq);
			param.put("orderby", orderby);
			param.put("sign", sign);
			param.put("tab", tab);

			HashMap<String, Object> mParam = new HashMap<String, Object>();
			if(tab.equals("RECEIVE")) {
				mParam.put("receiver", userSeq);

				begin = messageService.getReceiverMsgSeqBegin(mParam);
				end = messageService.getReceiverMsgSeqEnd(mParam);
			}
			else {
				mParam.put("sender", userSeq);

				begin = messageService.getSendMsgSeqBegin(mParam);
				end = messageService.getSendMsgSeqEnd(mParam);
			}

			MessageVO data = null;
			if(request.getParameter("way").equals("PREV") && msgSeq < begin)
			{
				data = new MessageVO();
			}
			else if(request.getParameter("way").equals("NEXT") && msgSeq > end) {
				data = new MessageVO();
			}
			else {
				data = new MessageVO();
				data = messageService.getMsg(param);
			}

			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("msg", data);
			item.put("tab", request.getParameter("tab"));
			item.put("way", request.getParameter("way"));

			List<HashMap<String, Object>> list =  new ArrayList<HashMap<String, Object>>();

			list.add(item);

			//libJsonExit($_POST['ACT'], array('msg'=>(is_array($r) ? $r : array()), 'tab'=>$_POST['tab'], 'way'=>$_POST['way']));


			return CommonUtil.libJsonArrExit(act, list);
		}

		/*
		 * 저장 프로시저 SP2_MEMBER_ALARM_LOG_INSERT_2 가 없음, 기존 기능 비활성 처리
		if ($_GET['CallAlarmFlag'] == 'Y' || $GLOBALS['SESS']['CNT_MSG'])
		{
			$sql = "exec SP2_MEMBER_ALARM_LOG_INSERT_2 ?, 14";
			libWriteSql('altong', $sql, array($GLOBALS['SESS']['UserSeq']));

			$GLOBALS['SESS']['Ver'] = 0;
			$GLOBALS['SESS']['CNT_MSG'] = 0;
			$GLOBALS['SESS']['ALARM_REFRESH_TIME'] = 0;
			libSetSessCookie();
		}
		*/
		return CommonUtil.libJsonArrExit(null, null);

	}

}
