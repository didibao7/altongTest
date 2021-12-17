package com.altong.web.rest.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altong.web.rest.service.RestCommonService;
import com.altong.web.rest.service.RestUserService;
import com.altong.web.service.MessageService;
import com.altong.web.service.member.MemberService;
import com.google.common.collect.ImmutableMap;

@Service
public class RestUserServiceImpl implements RestUserService{
	
	@Autowired
	RestCommonService restCommonService;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	MessageService messageService;
		
	@Override
	public Map<String, Object> restGetUser(HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restCommonService.getRestCookieUser(request, response);
			final String userNick = restCommonService.getRestCookieUserNick(request, response);
			final int userLv = restCommonService.getRestCookieUserLv(request, response);
			
			return ImmutableMap.of("seq",userSeq, "nick",userNick, "lv",userLv);
			
		}catch (Exception e) {
			return ImmutableMap.of("message",e);
		}
	}
	
	@Override
	public Map<String, Object> restMemberPartnerSave(Integer partnerSeq, String flagPartner, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restCommonService.getRestCookieUser(request, response);

			if(userSeq == 0) return ImmutableMap.of( "code", "notlogin" );

			String format = "yyyy-MM-dd aa hh:mm:ss";
			Calendar todays = Calendar.getInstance();
			SimpleDateFormat type = new SimpleDateFormat(format);
			String todayDate = type.format(todays.getTime());


			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("userSeq", userSeq);
			param.put("partnerSeq", partnerSeq);
			param.put("flagPartner", flagPartner);
			param.put("dateReg", todayDate);

			final int returnCode = memberService.setPartnerSave(param);

			switch(returnCode) {
				case 0:
					return ImmutableMap.of( "msg", "정상적으로 추가되었습니다." );
				case 1:
					return ImmutableMap.of( "msg","이미 등록된 회원입니다." );
				default:
					return ImmutableMap.of( "msg","처리 중 오류가 발생하였습니다.\n잠시 후 다시 시도해주세요." );
			}
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}
	
	@Override
	public Map<String, Object> restMessageSend(Map<String, Object> param, Integer receiverSeq, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restCommonService.getRestCookieUser(request, response);

			String contents = param.get("Contents") != null ? String.valueOf(param.get("Contents")) : "";


			if(userSeq == 0) return ImmutableMap.of( "code", "notlogin" );
			if(receiverSeq == userSeq) return ImmutableMap.of( "msg", "자신에게는 쪽지를 보낼 수 없습니다." );
			if(contents.equals("")) return ImmutableMap.of( "msg", "내용을 입력하세요." );

			HashMap<String, Object> param1 = new HashMap<String, Object>();
			param1.put("userSeq", userSeq);
			param1.put("blockUserSeq", receiverSeq);
			param1.put("contents", contents);

			final int returnCode = messageService.setSendMsg(param1);

			switch(returnCode) {
				case 1:
					return ImmutableMap.of( "msg", "차단하신 회원님께는 쪽지를 발송하실 수 없습니다." );
				case 2:
					return ImmutableMap.of( "msg","회원님으로부터의 쪽지 수신을 차단한 상태이므로 \n해당 회원님께 쪽지를 발송하실 수 없습니다." );
				case 3:
					return ImmutableMap.of( "msg", "알 수 없는 이유로 쪽지 전송에 실패했습니다." );
				default:
					return ImmutableMap.of( "msg", true );
			}
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}
}
