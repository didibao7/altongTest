package com.altong.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.altong.web.service.CookieLoginService;

@Controller
@RestController
@RequestMapping("/cookie")
public class CookieInfoController {
	
	@Autowired
	CookieLoginService cookieLoginService;
	
	@RequestMapping( value = "/user", method = RequestMethod.GET )
	public int cookieGetUser(HttpServletRequest request, HttpServletResponse response)
	{
		return cookieLoginService.getCookieUserSeq(request, response);
	}
	
	
}
