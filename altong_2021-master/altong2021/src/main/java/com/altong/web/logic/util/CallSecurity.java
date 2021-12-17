package com.altong.web.logic.util;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;

import jasp.util.pVector;
import jasp.buildin.*;
import jasp.vbs.*;

//김태환 (20181120)
//ASP의 문자열을 PHP의 함수를 활용하여 암호화한다.
//주로 PHP에서 사용될 로그인 정보 쿠키를 ASP에서 세팅하기위해 쓰인다.
//세션을 이용하여 쿠키 저장
//dim enText
//enText = Fn_CallPHP_Secure("ENCODE=" & Server.URLEncode(Fn_jsonEncode(Session.Contents)))
//Response.Cookies("SESS") = enText
//쿠키를 이용하여 세션 저장
//deText = Fn_CallPHP_Secure("DECODE=" & Server.URLEncode(request.cookies("sess")))
//Fn_jsonDecode_sess(deText)
//주의: 세션 혹은 쿠키가 존재하는지 확인 후 시행할 것.

public class CallSecurity {
	public static String Fn_CallPHP_Secure(String src) throws Exception {
        
        String[] params = src.split("=");
        String result = "";
        if (src.contains("ENCODE"))
        {
        	result =  CommonUtil.libEncode(params[1]);
        }
        else if (src.contains("DECODE"))
        {
        	result = CommonUtil.libDecode(params[1]);
        }
        
        return result;
    }

    @SuppressWarnings("unchecked")
	public static String Fn_jsonEncode(IVariantDictionary obj) throws Exception {
                
        String result = "";
        
        //JSONParse에 json데이터를 넣어 파싱한 다음 JSONObject로 변환한다. 
        JSONObject jsonObj = new JSONObject();
        ObjectMapper mapper = new ObjectMapper();
        
        String iName = "";
        Object iValue = "";
        for(int i = 0; i < obj.getCount(); i++) {
        	iName = obj.getKey(i);
        	iValue = obj.getItem(iName);
        	
        	jsonObj.put(iName, ""+iValue+"");
        	//jsonObj.put(iName, iValue);
        }
        
        result = jsonObj.toString();
        
        //System.out.println("jsonObj : " + result);
        
        return result;
    }
    
	@SuppressWarnings("unchecked")
	public static IVariantDictionary Fn_jsonDecode(String srl) throws Exception {
    	JSONParser jsonParse = new JSONParser();
    	JSONObject jsonObj = (JSONObject) jsonParse.parse(srl);
        
    	IVariantDictionary Fn_jsonDecode = new IVariantDictionary();
    	Set<String> key = jsonObj.keySet();
    	Iterator<String> iter = key.iterator();
    	
    	
        while(iter.hasNext()) {
        	String keyname = iter.next();
        	String val = jsonObj.get(keyname).toString();
        	
        	Fn_jsonDecode.setItem(keyname, val);
        }
        
        return Fn_jsonDecode;
    }

    //session을 json serial로
    public static String Fn_jsonEncode_sess(HttpSession session) throws Exception {
    	String jsonEncode_sess = "";
    	IVariantDictionary dict = new IVariantDictionary();
    	
    	String sName = "";
    	Object sValue = "";
    	Enumeration attEnum = session.getAttributeNames();
    	while(attEnum.hasMoreElements()) {
    	     sName = (String)attEnum.nextElement();
    	     sValue = session.getAttribute(sName);
    	     //System.out.println(sName + " : " + sValue);
    	     dict.setItem(sName, sValue);
    	}
    	
    	
    	jsonEncode_sess = Fn_jsonEncode(dict);
    	
        return jsonEncode_sess;
    }

    //json serial을 session으로
    @SuppressWarnings("unchecked")
	public static void Fn_jsonDecode_sess(HttpSession session, String srl) throws Exception {
        JSONParser jsonParse = new JSONParser();
    	JSONObject jsonObj = (JSONObject) jsonParse.parse(srl);
    	
    	Set<String> key = jsonObj.keySet();
    	Iterator<String> iter = key.iterator();
    	
        while(iter.hasNext()) {
        	String keyname = iter.next();
        	String val = jsonObj.get(keyname).toString();
        	//System.out.println("Fn_jsonDecode_sess = " + keyname + " = " + val);
        	session.setAttribute(keyname, val);
        }
    }
    /*
    JSON = (ADODB.Stream)null;
        return Fn_jsonDecode_sess;
    }
    */
    public static String Fn_jsonUpdate(String srl, String k, String v) throws Exception {
    	JSONParser jsonParse = new JSONParser();
    	JSONObject jsonObj = (JSONObject) jsonParse.parse(srl);
        
        jsonObj.put(k, v);
        
        String enText = "";
        enText = jsonObj.toString();
        
        return enText;
    }
}
