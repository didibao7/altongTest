package com.altong.web.logic.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jasp.buildin.*;
import jasp.util.*;
import jasp.vbs.*;

public class MD5Class {

    public String md5_encode(String str){

    	String MD5 = ""; 
    	//System.out.println("<br/> testMD5 str : " + str);
    	try{

    		MessageDigest md = MessageDigest.getInstance("MD5"); 

    		md.update(str.getBytes()); 

    		byte byteData[] = md.digest();

    		StringBuffer sb = new StringBuffer(); 

    		for(int i = 0 ; i < byteData.length ; i++){

    			sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));

    		}

    		MD5 = sb.toString();

    		
    		//System.out.println("<br/> testMD5 MD5 : " + MD5);
    	}catch(NoSuchAlgorithmException e){

    		e.printStackTrace(); 

    		MD5 = null; 
    		//System.out.println("<br/> testMD5 MD5 error");
    	}

    	return MD5;

    }
    

}
