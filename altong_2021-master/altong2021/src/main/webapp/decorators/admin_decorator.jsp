<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ page import="java.net.InetAddress" %>
<%@ include file="/Common/include/global.jsp" %>
<c:if test="${global ne null and global.get('AutoLogin') != 'Y'}">
	<%@ include file="/WEB-INF/views/default/cookiesCheck.jsp" %>
</c:if>
<c:if test="${global ne null and global.get('UserLv') != '99'}">
<!--LOGOFF-->
<script>alert('관리자만 이용가능합니다.');top.location='/';</script>
</c:if>
<%
String HTTP_PROTOCOL = "";
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

LocalAddr = Inet4Address.getLocalHost().getHostAddress().substring(0,3);
String http_host = InetAddress.getLocalHost().getHostAddress();

Enumeration<NetworkInterface> nienum = NetworkInterface.getNetworkInterfaces();
while (nienum.hasMoreElements()) {
    NetworkInterface ni = nienum.nextElement();
    Enumeration<InetAddress> kk = ni.getInetAddresses();
    
    while (kk.hasMoreElements()) {
    	InetAddress inetAddress = (InetAddress) kk.nextElement();
		//out.println("<br/>" + inetAddress.getHostName()+" : "+inetAddress.getHostAddress() + "<br/>");
		if(inetAddress.getHostName().equals("203.245.8.35")) {
			LocalAddr = inetAddress.getHostAddress().substring(0,3);
			http_host = inetAddress.getHostAddress();
			//out.println("LocalAddr : "+ LocalAddr + "<br/>");
			break;
		}
	}
}

srvPortt = request.getServerPort();
protocol = request.getProtocol();
boolean secure = request.isSecure();	// true: https, false: http

//out.println("getRemoteAddr : " + Inet4Address.getLocalHost().getHostAddress() + "<br/>");
//out.println("LocalAddr : [" + LocalAddr.trim() + "]<br/>");
if (!LocalAddr.trim().equals("192") && !LocalAddr.trim().equals("127") && !LocalAddr.trim().equals("203") && !LocalAddr.trim().equals("::1")) //테스트 완료후 203 코드 제거
{
	SSL_CERT_SEND_URL = "http://www.altong.com/";
	MORMAL_SEND_URL   = "http://www.altong.com/";
	
	if(srvPortt == 80) {
		DELAY_URL  = "http://altong.com";
		IMG_URL    = "http://www.altong.com";
	}
	else {
		DELAY_URL  = "https://altong.com";
		IMG_URL    = "https://www.altong.com";
	}
	//out.println("Test1");
}
else {
	if(secure == true) { protocol = "https"; }
	else { protocol = "http"; }
	
	SSL_CERT_SEND_URL 	=  protocol.toLowerCase() + "://" + http_host + "/";
	MORMAL_SEND_URL 	=  protocol.toLowerCase() + "://" + http_host + "/";
	
	DELAY_URL  = protocol.toLowerCase() + "://" + http_host;
	IMG_URL    = protocol.toLowerCase() + "://" + http_host;
	//out.println("Else");
}
//out.println("IMG_URL : " + IMG_URL);

if(SSL_CERT_SEND_URL == "" ){ SSL_CERT_SEND_URL = "/"; }
if(MORMAL_SEND_URL == "" ){ MORMAL_SEND_URL = "/"; }

pageContext.setAttribute("SSL_CERT_SEND_URL", SSL_CERT_SEND_URL);
pageContext.setAttribute("MORMAL_SEND_URL", MORMAL_SEND_URL);
pageContext.setAttribute("DELAY_URL", DELAY_URL);
pageContext.setAttribute("IMG_URL", IMG_URL);
pageContext.setAttribute("referer", referer);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi">

<meta property="og:url" content="http://www.altong.com/default/main">
<meta property="og:title" content="알통 바로가기">  
<meta property="og:type" content="website">
<meta property="og:image" content="<%=IMG_URL%>/images/share_sns/k_feedimg.jpg">
<meta property="og:image:secure_url" content="<%=IMG_URL%>/Common/images/share_sns/k_feedimg.jpg">
<meta property="og:image:type"       content="image/png">
<meta property="og:image:width"      content="200">
<meta property="og:image:height"     content="500">
<meta property="og:description" content="알통은 인터넷 상의 모든 지식•정보에 가치를 부여, 그 가치를 기여자들과 공유하는 수익형 지식공유 플랫폼입니다.">
<meta property="fb:app_id" content="2128664037179612">

<meta name="description" content="알통은 인터넷 상의 모든 지식•정보에 가치를 부여, 그 가치를 기여자들과 공유하는 수익형 지식공유 플랫폼입니다.">
<meta name="keywords" content="알통,Altong,지식,수익,가치,경제,공유,플랫폼">

<meta name="twitter:card" content="summary">
<meta name="twitter:url" content="http://www.altong.com/default/main">
<meta name="twitter:title" content="알통 바로가기">
<meta name="twitter:description" content="알통 수익형 지식 경제 공유 플랫폼">
<meta name="twitter:image" content="<%=IMG_URL%>/Common/images/share_sns/k_feedimg.jpg">

<title>Management</title>
<link rel="stylesheet" type="text/css" href="/Common/CSS/css.css">
<link rel="stylesheet" type="text/css" href="/Common/CSS/layout.css">
<link rel="stylesheet" type="text/css" href="/Common/CSS/board.css">
<link rel="stylesheet" type="text/css" href="/Common/CSS/jquery-ui.css">    
<link rel="stylesheet" type="text/css" href="/Common/CSS/bootstrap.min.css?1.1">
<link rel="stylesheet" type="text/css" href="/Common/CSS/fullcalendar.css">
<link rel="stylesheet" type="text/css" href="/Common/CSS/fullcalendar.print.css" media='print'>
<script type="text/javascript" src="/Common/js/Common.js"></script>
<script type="text/javascript" src="/Common/js/jquery.js"></script>    
<script type="text/javascript" src="/Common/js/jquery-ui.js"></script>    
<link rel="stylesheet" type="text/css" href="/Common/CSS/bootstrap.min.css?1.1">
<link rel="stylesheet" type="text/css" href="https://cdn.rawgit.com/rikmms/progress-bar-4-axios/0a3acf92/dist/nprogress.css"/>
<script type="text/javascript" src="/Common/js/moment.min.js"></script>
<script type="text/javascript" src="/Common/js/fullcalendar.js"></script>
<script src="https://unpkg.com/vuejs-paginate@0.9.0"></script>
<!--<script src="https://unpkg.com/vue@2.5.16/dist/vue.min.js"></script>-->
<!--<script src="https://unpkg.com/axios/dist/axios.min.js"></script>-->

<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link rel="apple-touch-icon" sizes="57x57" href="/Common/images/icon/apple-icon-57x57.png">
<link rel="apple-touch-icon" sizes="60x60" href="/Common/images/icon/apple-icon-60x60.png">
<link rel="apple-touch-icon" sizes="72x72" href="/Common/images/icon/apple-icon-72x72.png">
<link rel="apple-touch-icon" sizes="76x76" href="/Common/images/icon/apple-icon-76x76.png">
<link rel="apple-touch-icon" sizes="114x114" href="/Common/images/icon/apple-icon-114x114.png">
<link rel="apple-touch-icon" sizes="120x120" href="/Common/images/icon/apple-icon-120x120.png">
<link rel="apple-touch-icon" sizes="144x144" href="/Common/images/icon/apple-icon-144x144.png">
<link rel="apple-touch-icon" sizes="152x152" href="/Common/images/icon/apple-icon-152x152.png">
<link rel="apple-touch-icon" sizes="180x180" href="/Common/images/icon/apple-icon-180x180.png">
<link rel="icon" type="image/png" sizes="192x192"  href="/Common/images/icon/android-icon-192x192.png">
<link rel="icon" type="image/png" sizes="32x32" href="/Common/images/icon/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="96x96" href="/Common/images/icon/favicon-96x96.png">
<link rel="icon" type="image/png" sizes="16x16" href="/Common/images/icon/favicon-16x16.png">

<script src="/Common/js/vue.min.js"></script>
<script src="/Common/js/axios.min.js"></script>
<script src="https://cdn.rawgit.com/rikmms/progress-bar-4-axios/0a3acf92/dist/index.js"></script>
<script src="https://cdn.jsdelivr.net/npm/lodash@4.17.5/lodash.min.js"></script>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.9/css/all.css" integrity="sha384-5SOiIsAziJl6AWe0HWRKTXlfcSHKmYV4RBF18PPJ173Kzn7jzMyFuTtk8JA7QQG1" crossorigin="anonymous">
<script src="/Common/js/polyfill.min.js"></script>

<decorator:head />

</head>

<decorator:body />

