package com.altong.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.altong.web.service.CookieLoginService;

@Controller
@RequestMapping("alaview/*")
public class AlaviewController {
	@Autowired
	CookieLoginService cookieLoginService;

	@RequestMapping("list")
	public ModelAndView alaviewList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		response.setContentType("text/html; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		mav.addObject("libAlaview", 1);

		return mav;
	}
}
