<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.net.*"%>
<%
Cookie[] cookies = request.getCookies();

String cookies1 = null;
String cookies2 = null;
String SESS = null;
if(cookies != null) {	
	for (Cookie c : cookies) { 
		if ("Cookies1".equals(c.getName())) { cookies1 = c.getValue(); }
		if ("Cookies2".equals(c.getName())) { cookies2 = c.getValue(); }
		if ("SESS".equals(c.getName())) { SESS = c.getValue(); }
	} 
}

if (cookies1 != null && cookies2 != null) {

	out.println("<script>");
	out.println("location.href='/default/LoginCheckAuto';");
	out.println("</script>");	

}
else {
    Cookie kc1 = new Cookie("Cookies1", null);
    kc1.setMaxAge(0);
    Cookie kc2 = new Cookie("Cookies1", null);
    kc2.setMaxAge(0);
    response.addCookie(kc1);
    response.addCookie(kc2);
    
  	//쿠키를 이용하여 세션 저장 (Global.jsp에서 이 작업을 이미 하고있기 때문에 사실상 필요없을 것 같다.)
    if (SESS != "") {
        session.setAttribute("SessExpire", 1);
        //php와 통신이 안될경우 SESS 쿠키까지 지워지는 것을 방지하기 위한 안전장치.
        //Response.Cookies("SESS").setExpires(vb.CDbl(vb.DateAdd("d", Session.getItem("SessExpire").toDouble(), vb.Now())));
        Cookie kc3 = new Cookie("SESS", null);
        kc3.setMaxAge(0);
        response.addCookie(kc3);
    }
}
//세션을 이용하여 AutoLogin 반복 확인 방지
session.setAttribute("AutoLogin", "Y");
%>
