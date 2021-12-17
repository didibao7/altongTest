<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.net.*" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.altong.web.logic.util.CommonUtil"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp"%>
<%
String HTTP_PROTOCOL = "";//데코레이터 변수 및 데이터
String SSL_CERT_SEND_URL = "";
String MORMAL_SEND_URL = "";
String ORIGIN_URL = "";
String DELAY_URL = "";
String ALT_URL = "";
String IMG_URL = "";
String LocalAddr = "";
int srvPortt = 80;
String protocol = "http";
String referer = request.getHeader("referer");
LocalAddr = Inet4Address.getLocalHost().getHostAddress().substring(0, 3);
String http_host = InetAddress.getLocalHost().getHostAddress();
Enumeration<NetworkInterface> nienum = NetworkInterface.getNetworkInterfaces();
while (nienum.hasMoreElements()) {
	NetworkInterface ni = nienum.nextElement();
	Enumeration<InetAddress> kk = ni.getInetAddresses();
	while (kk.hasMoreElements()) {
		InetAddress inetAddress = (InetAddress) kk.nextElement();	//out.println("<br/>" + inetAddress.getHostName()+" : "+inetAddress.getHostAddress() + "<br/>");
		if(inetAddress.getHostName().equals("203.245.8.35")) {
			LocalAddr = inetAddress.getHostAddress().substring(0, 3);
			http_host = inetAddress.getHostAddress();	//out.println("LocalAddr : "+ LocalAddr + "<br/>");
			break;
		}
	}
}
srvPortt = request.getServerPort();
protocol = request.getProtocol();
boolean secure = request.isSecure();	// true: https, false: http//out.println("getRemoteAddr : " + Inet4Address.getLocalHost().getHostAddress() + "<br/>");
if (!LocalAddr.trim().equals("192") && !LocalAddr.trim().equals("127") && !LocalAddr.trim().equals("203")	//out.println("LocalAddr : [" + LocalAddr.trim() + "]<br/>");
		&& !LocalAddr.trim().equals("::1") && false) //테스트 완료후 203 코드 제거
{
	SSL_CERT_SEND_URL = "http://www.altong.com/";
	MORMAL_SEND_URL = "http://www.altong.com/";
	if (srvPortt == 80) {
		DELAY_URL = "http://altong.com";
		IMG_URL = "http://www.altong.com";
	} else {
		DELAY_URL = "https://altong.com";
		IMG_URL = "https://www.altong.com";
	}	//out.println("Test1");
} else {
	if (secure == true) {
		protocol = "https";
	} else {
		protocol = "http";
	}
	SSL_CERT_SEND_URL = protocol.toLowerCase() + "://" + http_host + "/";
	MORMAL_SEND_URL = protocol.toLowerCase() + "://" + http_host + "/";
	DELAY_URL = protocol.toLowerCase() + "://" + http_host;
	IMG_URL = protocol.toLowerCase() + "://" + http_host;	//out.println("Else");
}	//out.println("IMG_URL : " + IMG_URL);
if (SSL_CERT_SEND_URL == "") {
	SSL_CERT_SEND_URL = "/";
}
if (MORMAL_SEND_URL == "") {
	MORMAL_SEND_URL = "/";
}
pageContext.setAttribute("SSL_CERT_SEND_URL", SSL_CERT_SEND_URL);
pageContext.setAttribute("MORMAL_SEND_URL", MORMAL_SEND_URL);
pageContext.setAttribute("DELAY_URL", DELAY_URL);
pageContext.setAttribute("IMG_URL", IMG_URL);
pageContext.setAttribute("referer", referer);
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<!-- <meta name="theme-color" content="#FD0031"> -->
<meta name="theme-color" content="#FFF">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=12.0, minimum-scale=0.25, user-scalable=yes, target-densitydpi=medium-dpi">

<meta property="og:url" content="http://www.altong.com/default/main">
<meta property="og:title" content='<spring:message code="msg_0193"/>'>  
<meta property="og:type" content="website">

<meta property="og:image:type"       content="image/png">
<meta property="og:image:width"      content="200">
<meta property="og:image:height"     content="500">
<meta property="og:description" content='<spring:message code="msg_0194"/>'>
<meta property="fb:app_id" content="2128664037179612">

<meta name="description" content='<spring:message code="msg_0194"/>'>
<meta name="keywords" content='<spring:message code="msg_0195"/>'>
<meta name="twitter:card" content="summary">
<meta name="twitter:url" content="http://www.altong.com/default/main">
<meta name="twitter:title" content='<spring:message code="msg_0193"/>'>
<meta name="twitter:description" content='<spring:message code="msg_0196"/>'>

<meta property="og:image" content="/Common/images/share_sns/k_feedimg.jpg">
<meta property="og:image:secure_url" content="/Common/images/share_sns/k_feedimg.jpg">
<meta name="twitter:image" content="/Common/images/share_sns/k_feedimg.jpg">

<title><spring:message code="msg_0193"/></title>

<link rel="stylesheet" href="/pub/css/common.css?ver=2.8">
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/styles/monokai-sublime.min.css" />    
<link rel="stylesheet" href="//cdn.quilljs.com/1.3.6/quill.snow.css" />
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=2.4">
<link rel="stylesheet" href="/pub/css/languages_common.css?ver=1.2">

<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

<link rel="icon" type="image/png" sizes="192x192"
	href="/Common/images/icon/android-icon-192x192.png">
<link rel="icon" type="image/png" sizes="96x96"
	href="/Common/images/icon/android-icon-96x96.png">
<link rel="icon" type="image/png" sizes="32x32"
	href="/Common/images/icon/android-icon-32x32.png">
<link rel="icon" type="image/png" sizes="16x16"
	href="/Common/images/icon/android-icon-16x16.png">

<script src="http://code.jquery.com/jquery-1.12.4.js"></script>

<script type="text/javascript" src="/lang/lang_ko.js?ver=1.0"></script>
<script type="text/javascript" src="/lang/lang_en.js?ver=1.0"></script>
<script type="text/javascript" src="/lang/lang_zh.js?ver=1.0"></script>
<script>
//언어별 렌더링
function getLangStr(code) {
	var s_lang = getCookie('s_lang');
	if(s_lang == null) { s_lang = 'ko'; }
	else { var lang_arr = s_lang.split('_'); s_lang = lang_arr[0]; }
	//console.log('lang : ' + s_lang);
	
	return eval('lang_' + s_lang + '.' + code);
}
function getCookie(name) {
	var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
	return value ? value[2] : null;
}
</script>
<decorator:head />

</head>


<decorator:body />