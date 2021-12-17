package com.altong.web.rest.controller;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altong.web.rest.service.RestTransferService;

@RestController
@RequestMapping("/api/transfers")
public class RestTransferController{
	
	@Autowired
	RestTransferService service;
		
	// GET 질문 번역 좋아요/싫어요 수 가져오기(작업 완료), param = transfer(번역글 seq), contentType(T:기계번역, Q:질문, A:답변)
	@GetMapping("/{transfer}/{contentType}/vote")
	public Map<String, Object> restGetQuestionTransferVote(@PathVariable("transfer") Integer transfer, @PathVariable("contentType") String contentType,
			HttpServletRequest request, HttpServletResponse response, Locale locale)
	{
		return service.restTransferGetTransferVote(transfer, contentType, request, response, locale);
	}
	
	// PATCH 질문 번역에 대한 평가 클릭하기(작업 완료), param = transfer(번역글 seq), act(G:좋아요, B:싫어요), contentType(T:기계번역, Q:질문, A:답변)
	@PatchMapping("/{transfer}/{act}/{contentType}/vote")
	public Map<String, Object> restPatchQuestionTransferVote(@PathVariable("transfer") Integer transfer, @PathVariable("act") String act, @PathVariable("contentType") String contentType,
			HttpServletRequest request, HttpServletResponse response, Locale locale)
	{
		return service.restTransferPutTransferVote(transfer, act, contentType, request, response, locale);
	}
}
