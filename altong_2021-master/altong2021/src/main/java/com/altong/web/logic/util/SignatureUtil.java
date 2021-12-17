package com.altong.web.logic.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignatureUtil {

	private static final Logger logger = LoggerFactory.getLogger(SignatureUtil.class);
	
	public static String createSignature(String userSeq) throws Exception {
		if(userSeq != null && !userSeq.isEmpty()) {
			final MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(userSeq.getBytes());
			
			final StringBuilder builder = new StringBuilder();
			final byte[] bytearr = md.digest();
			
		    for (final byte b: bytearr) {
		      builder.append(String.format("%02x", b));
		    }
		    
		    return builder.toString();
		}else {
			return null;
		}
	}
	
	public static boolean compareSignature(String signature, String userSeq) throws Exception {
		
		if(signature != null && !signature.isEmpty()) {
			if(userSeq != null && !userSeq.isEmpty()){
				return SignatureUtil.createSignature(userSeq).equals(signature);
			}else {
				if(userSeq == null) {
					logger.trace("userSeq is Null. signature : " + signature);
				}else {
					logger.trace("userSeq is Empty. signature : " + signature);
				}
			}
		}else {
			if(signature == null) {
				logger.trace("signature is Null. userSeq : " + userSeq);
			}else {
				logger.trace("signature is Empty. userSeq : " + userSeq);
			}
		}
		
		return false;
	}
	
	public static String getSignatureFromCookie(HttpServletRequest request) throws Exception {
		final Cookie cookie = new CookieBox(request).getCookie("SIGNATURE");
		return cookie != null ? cookie.getValue() : null;
	}
}
