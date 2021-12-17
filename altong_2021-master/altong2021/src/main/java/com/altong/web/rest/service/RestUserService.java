package com.altong.web.rest.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RestUserService {
	Map<String, Object> restGetUser(HttpServletRequest request, HttpServletResponse response);												// Controller GET 계정정보 가져오기	 					/user
	Map<String, Object> restMemberPartnerSave(Integer partnerSeq, String flagPartner, HttpServletRequest request, HttpServletResponse response);
	Map<String, Object> restMessageSend(Map<String, Object> param, Integer receiverSeq, HttpServletRequest request, HttpServletResponse response);
}
