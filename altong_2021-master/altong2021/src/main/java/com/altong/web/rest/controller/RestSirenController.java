package com.altong.web.rest.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altong.web.rest.service.RestSirenService;

@RestController
@RequestMapping("/api/siren")
public class RestSirenController {
	
	@Autowired
	RestSirenService service;
		
	// POST 신고(작업 완료) param = ACT, H_Type, H_Seq, H_Reason, H_Reason_txt
	@PostMapping("/{seq}")
	public Map<String, Object> restPostCommonSiren(@RequestBody Map<String, Object> param, @PathVariable("seq") Integer seq, HttpServletRequest request, HttpServletResponse response)
	{
		return service.restCommonSiren(param, seq, request, response);
	}
}
