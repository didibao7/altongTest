package com.altong.web.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("stock/*")
public class StockController {
	@RequestMapping("/stock/index")
	public ModelAndView index(HttpServletRequest request) throws UnsupportedEncodingException {
		
		ModelAndView mav = new ModelAndView();
		
		
		return mav;
	}
	
	@RequestMapping("/stock/ads")
	public ModelAndView ads(HttpServletRequest request) throws UnsupportedEncodingException {
		
		ModelAndView mav = new ModelAndView();
		
		
		return mav;
	}
	
	@RequestMapping("/stock/thankyou")
	public ModelAndView thankyou(HttpServletRequest request) throws UnsupportedEncodingException {
		
		ModelAndView mav = new ModelAndView();
		
		
		return mav;
	}
	
	@RequestMapping("/stock/subscription")
	public ModelAndView subscription(HttpServletRequest request) throws UnsupportedEncodingException {
		
		ModelAndView mav = new ModelAndView();
		
		String format = "yyyy-MM-dd";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String regDate = type.format(today.getTime());
		
		mav.addObject("regDate", regDate);
		
		return mav;
	}

	@RequestMapping(value="/stock/sendEmail", method = RequestMethod.POST)
	public @ResponseBody String sendEmail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		// 수정 중
		try {
		  Message msg = new MimeMessage(session);
		  msg.setFrom(new InternetAddress("webmail.altong.com", "altong.com Admin"));
		  msg.addRecipient(Message.RecipientType.TO,
		                   new InternetAddress("admin@altong.com", "Mr. User"));
		  msg.setSubject("Your Example.com account has been activated");
		  msg.setText("This is a test");
		  Transport.send(msg);
		} catch (AddressException e) {
		  // ...
		} catch (MessagingException e) {
		  // ...
		} catch (UnsupportedEncodingException e) {
		  // ...
		}
		
		return null;
	}
	
}
