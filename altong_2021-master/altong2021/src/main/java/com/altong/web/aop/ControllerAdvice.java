package com.altong.web.aop;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.altong.web.logic.util.SignatureUtil;
import com.altong.web.service.rest.RestService;

@Component
@Aspect
public class ControllerAdvice {

	private final Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);
	
	@Autowired
	private RestService service;
	
	@Before("execution(* com.altong.web.controller.*.*(..))")
	public void methodStartUserSeqLog(JoinPoint jp) throws Exception{
		final HttpServletRequest request = 
				 ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		  
		final int userSeq = service.getRestCookieUser(request, null);
		final String signature = SignatureUtil.getSignatureFromCookie(request);
		
		if(userSeq > 0 && signature != null) {
			final String className = jp.getTarget().getClass().getName();
			final String methodName = jp.getSignature().getName();
			
			logger.trace("[Before] ["+className +":" + methodName +"] userSeq : " + userSeq + ", Signature : " + signature + ", Check Signature : " + SignatureUtil.compareSignature(signature, String.valueOf(userSeq)));
		}
	}
	
	@Before("execution(* com.altong.web.controller.*.*(..))")
	public void methodEndUserSeqLog(JoinPoint jp) throws Exception{
		final HttpServletRequest request = 
				 ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		  
		final int userSeq = service.getRestCookieUser(request, null);
		final String signature = SignatureUtil.getSignatureFromCookie(request);
		
		if(userSeq > 0 && signature != null) {
			final String className = jp.getTarget().getClass().getName();
			final String methodName = jp.getSignature().getName();
			
			logger.trace("[After] ["+className +":" + methodName +"] userSeq : " + userSeq + ", Signature : " + signature + ", Check Signature : " + SignatureUtil.compareSignature(signature, String.valueOf(userSeq)));
		}
	}
}
