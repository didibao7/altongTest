package com.altong.web.rest.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altong.web.rest.service.RestCommonService;
import com.altong.web.rest.service.RestUserService;

@RestController
@RequestMapping("/api")
public class RestUserController{
	
	@Autowired
	RestUserService service;
		
	@Autowired
	RestCommonService restCommonService;
	
	// 계정정보 가져오기
	@GetMapping("/user/info")
	public Map<String, Object> restGetUser(HttpServletRequest request, HttpServletResponse response )
	{
		return service.restGetUser(request, response);
	}
	
	// Patch 친구추가/멘토추가(작업 완료)
	@PatchMapping("/users/{user}/{flagPartner}")
	public Map<String, Object> restPatchMemberPartnerSave(@PathVariable("user") Integer partnerSeq, @PathVariable("flagPartner") String flagPartner,
														HttpServletRequest request, HttpServletResponse response)
	{
		return service.restMemberPartnerSave(partnerSeq, flagPartner, request, response);
	}
	
	// Get 질문글 훈훈알 현재 사용가능 알수 (작업 완료)
	@GetMapping("/user/almoney/extra")
	public Map<String, Object> restGetQeustionExtraAlmoney(HttpServletRequest request, HttpServletResponse response )
	{
		return restCommonService.restExtraAlmoney(request, response);
	}
	
	// Post 쪽지 보내기 (작업 완료) param = Contents
	@PostMapping("users/{user}/message")
	public Map<String, Object> restPostMessageSend(@RequestBody Map<String, Object> param, @PathVariable("user") Integer receiverSeq,
														HttpServletRequest request, HttpServletResponse response)
	{
		return service
				.restMessageSend(param, receiverSeq, request, response);
	}
}
