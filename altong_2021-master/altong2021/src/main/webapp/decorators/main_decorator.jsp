<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jasp.util.*"%>
<%@ page import="java.math.*" %>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ page import="java.net.*"%>
<%@ include file="/Common/include/global.jsp"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp"%>
<%@ page import="com.altong.web.logic.util.CommonUtil"%>
<%@ page import="com.altong.web.logic.LogAlmoneyVO" %>  
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

JSONObject global_info = (JSONObject) CommonUtil.getGlobal(request, response);
Integer UserAlmoney = 0;
int userSeq = 0;// 사용자 레벨


final String UserPhoto = global_info != null ? (
		global_info.get("UserSeq") != null ? 
				CommonUtil.getProfilePhoto( Integer.parseInt(global_info.get("UserSeq").toString()) ) : ""
		) : "";
pageContext.setAttribute("UserPhoto", UserPhoto);

final String UserLv = global_info != null ? (
			global_info.get("UserSeq") != null ? 
				CommonUtil.getLevelName( Integer.parseInt(CommonUtil.getUserLV( Integer.parseInt(global_info.get("UserSeq").toString())) ), request ) : ""
		) : "";


if(request.getAttribute("lvNameDefault") != null) {
	pageContext.setAttribute("UserLv", request.getAttribute("lvNameDefault") );
}
else {
	pageContext.setAttribute("UserLv", UserLv );
}
%>
<title><spring:message code="msg_0193"/></title>
<%
	if (global_info != null) {
%>
<link rel="stylesheet"
	href="/pub/css/main_menu_login.css?ver=2.0">
<%
	} else {
%>
<link rel="stylesheet"
	href="/pub/css/main_menu_logout.css?ver=2.0">
<%
	}
%>
<link rel="stylesheet" href="/pub/css/common.css?ver=2.9">
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/styles/monokai-sublime.min.css" />    
<link rel="stylesheet" href="//cdn.quilljs.com/1.3.6/quill.snow.css" />
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=2.5">
<link rel="stylesheet" href="/pub/css/languages_common.css?ver=1.3">

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

<script type="text/javascript" src="/lang/lang_ko.js?ver=1.1"></script>
<script type="text/javascript" src="/lang/lang_en.js?ver=1.1"></script>
<script type="text/javascript" src="/lang/lang_zh.js?ver=1.1"></script>
<script type="text/javascript" src="/lang/lang_ja.js?ver=1.1"></script>

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
<body>

<script type="text/javascript">
// 김주윤 20201230 : 메인 페이지 외의 페이지에서는 fsetalmoney가 아무일도 안함.
function fSetAlmoney(AlmoneyAll, AlmoneyMy, x) {}
</script>

<decorator:body />

<footer>
	<p>
		<spring:message code="msg_0197"/> <a href="tel:02-330-3000"><b><spring:message code="msg_0198"/></b></a> | <spring:message code="msg_0199"/>:<b> admin@altong.com</b></a>
	</p>
	<p>
		<a href="/default/rule#a"><spring:message code="msg_0200"/></a> | <a href="/default/rule#b"><spring:message code="msg_0201"/></a> | <a href="/default/cs/customerService"><spring:message code="msg_0202"/></a>
	</p>
	<small>Copyleft 2017 Altong, Inc.</small>
</footer>

<nav id="slidebar">
	<div id="gnb_wrapper">
		<h1>
			<div id="slidebar_close">
				<i></i> <i></i>
			</div>
		</h1>

		<div class="gnb_info">
			<div class="gnb_info_login">
				<figure>
					<div class="info_pic">
						<img id="avatar" src="/UploadFile/Profile/${UserPhoto}" onerror="this.src='/pub/css/profile/img_thum_base0.jpg'">
					</div>
					<figcaption onclick="location.href='/member/myInfo';">${UserLv}</figcaption>
				</figure>
				<div class="gnb_info_name">
					<div onclick="location.href='/member/myInfo';">
						<h2>${global.get('UserNickName')}</h2>
						<p>
							<span><spring:message code="msg_0163"/> <spring:message code="msg_0164"/><fmt:formatNumber type="number"
									maxFractionDigits="3" value="${global.get('RankQ')}" /><spring:message code="msg_0165"/>
							</span> <span><spring:message code="msg_0166"/> <spring:message code="msg_0164"/><fmt:formatNumber type="number"
									maxFractionDigits="3" value="${global.get('RankA')}" /><spring:message code="msg_0165"/>
							</span>
						</p>
					</div>
				</div>
				<div class="gnb_info_modify" onclick="location.href='/member/myJoin';" ><spring:message code="msg_0167"/></div>
			</div>
			<div class="gnb_info_logout">
				<h3>
					<spring:message code="msg_0168"/>
					</h1>
					<ul>
						<li><a href="/default/login"><spring:message code="msg_0169"/></a></li>
						<li><a href="/default/joinRule"><spring:message code="msg_0170"/></a></li>
					</ul>
			</div>
		</div>
		<ul id="slide_menu">

			<li><a href="/member/alarm/alarm"><b></b><span><spring:message code="msg_0171"/></span><i class="alarmCnt"></i></a></li>
			<li><a id="myspace" href="#"><b></b><span><spring:message code="msg_0172"/></span></a>
				<ul>
					<li><a href="/member/myInfo">· <spring:message code="msg_0173"/></a></li>
					<li><a href="/member/bank/index">· <spring:message code="msg_0174"/></a></li>
					<li><a href="/alpay/user/sub/exchange">· <spring:message code="msg_0209"/></a></li>
					<li><a href="/member/interest/myInterest">· <spring:message code="msg_0175"/></a></li>
					<li><a href="/member/myZzim">· <spring:message code="msg_0176"/></a></li>
					<li><a href="/member/myPartner?FlagPartner=M">· <spring:message code="msg_0177"/></a></li>
					<li><a href="/member/myFriend">· <spring:message code="msg_0178"/></a></li>
					<li><a href="/message/message">· <spring:message code="msg_0179"/></a></li>
				</ul></li>
			<li><a href="#" id="nickName"><b></b><span><spring:message code="msg_0180"/></span></a></li>
				<li class="nick_search">
					<form id="frm_search" action="">
						<div class="nick_search_bar">
							<input type="hidden" name="ACT" value="SEARCH_NICK">
							<input type="search" name="H_nick" placeholder='<spring:message code="msg_0203"/>' required autocomplete="off" outfocus>
							<button type="submit" onclick="fSearchNick(this)"> <img src="/Common/images/icon_search.png" alt='<spring:message code="msg_0180"/>'></button>
							<span class="nick_search_loader">
								<img src="/Common/images/search_nick_loader02.gif" alt='<spring:message code="msg_0204"/>'>
							</span>
						</div>
					</form>
				</li>			
			<li><a href="/member/myRecommend"><b></b><span><spring:message code="msg_0181"/></span></a></li>

			<li><a href="/question/rankQuestion"><b></b><span><spring:message code="msg_0182"/></span></a></li>
			<li><a href="/question/eventList"><b></b><span><spring:message code="msg_0183"/></span></a></li>
			<li><a href="/default/userGuide"><b></b><span><spring:message code="msg_0184"/></span></a></li>
			<li><a href="/default/cs/customerService"><b></b><span><spring:message code="msg_0202"/></span></a></li>
			<li><a href="javascript:void(0);" onclick="togglecookie(this);"><b></b><i></i><span id="keycontents"><spring:message code="msg_0186"/></span></a></li>
			<c:if test="${global.get('UserSeq') == '10000110' || global.get('UserSeq') == '10000564' || global.get('UserSeq') == '10000092' || global.get('UserSeq') == '10000703' || global.get('UserLv') == '99'}">
			<li><a href="javascript:void(0);"  onclick="javascript:location.href='/aadmin/category/classify';"><b></b><i></i><span><spring:message code="msg_0205"/></span></a></li>
			<li><a href="javascript:void(0);" onclick="javascript:location.href='/aadmin/index';"><b></b><i></i><span><spring:message code="msg_0206"/></span></a></li>
			</c:if>
			<div id="languages_menu">
				<span><a href="/default/main?s_lang=ko_KR">한글</a></span>/<span><a href="/default/main?s_lang=en_US">EN</a></span>/<span><a href="/default/main?s_lang=ja_JP">日本語</a></span>/<span><a href="/default/main?s_lang=zh_CN">中文</a></span>
			</div>
		</ul>

		<div class="gnb_footer">
			<p>
				<a href="/default/logOut"><spring:message code="msg_0187"/></a> <a href="javascript:void(0);" onclick="fleavebtn();"><spring:message code="msg_0188"/></a>
			</p>
		</div>
	</div>
</nav>
</body>
</html>

<script>
// 메인 아약스
function fAjax(url, param) {
if (document.xhr) {
	$('#Tip').text('<%=CommonUtil.getLangMsg(request, "msg_0207")%>').css('display', 'block');
	setTimeout(function () { $('#Tip').stop().fadeOut(300) }, 3000);
	return;
}

document.xhr = $.ajax({
	type: 'post',
	url: url,
	data: param,
	dataType: 'json',
	crossDomain: true,
	success: function (r) {
		
		switch (r.result) {
			case 'GET_ALMONEY':
			{
				fSetAlmoney(r.arr.AlmoneyAll, r.arr.Almoney, 0);
				<%if (global_info != null) {%>
				//console.log('r.arr.FlagDel : ' + r.arr.FlagDel);
				if (r.arr.FlagDel)
				{
					location.replace('/default/logOut');
					//return;
				}
				$('.alarmCnt').text(r.arr.AlarmCNT).parent().parent().fadeIn();
				if (r.arr.SESS)
				{
					$.cookie('SESS', r.arr.SESS, {expires:r.arr.SessExpire, path:'/'});
					$.cookie('SESC', r.arr.Ver, {expires:r.arr.SessExpire, path:'/'});
				}
				<%}%>	
					break;
				}
			default:
				if (r.result) alert(r.result);
				break;
			}
		},
		error: function (r, textStatus, err) {
			if (r.responseText && r.responseText.substr(0, 13) == '<!--LOGOFF-->') { top.location = '<%=MORMAL_SEND_URL%>'; return; }

			//alert('<spring:message code="msg_0215"/>');
			var str = '';
			for (var key in r) str += key + '=' + r[key] + '\n';
			console.log(str);
		},
		complete: function () {
			document.xhr = false;
		}
	});
}

$(document).ready(function() {
var host = '<%=DELAY_URL%>';
	if (host == 'null') {
		host = '';
	}
	//getQuiz();
	// 호스트 지움(요청한 세션으로 부터만 부)
	//fAjax(host + '/common/getAlmoneyAlarm', 'SESS=' + getCookie('SESS'));
	fAjax('/common/getAlmoneyAlarm', 'SESS=' + getCookie('SESS'));
});
</script>

<c:if test="${global.get('UserSeq') ne null}">
<script type="text/javascript">
$(document).ready(function() {
	mainLog();
});

var nextDt;
function getMin() {
	nextDt = new Date(Date.now() + (30 * 60 * 1000));
}
function mainLog() {
	dt = new Date();
	
	if(nextDt == undefined) {
		getMin();
	}
	//console.log('nextDt : ' + nextDt.getTime());
	//console.log('dt : ' + dt.getTime());
	//현재 시간과 nextDt 와 비교하여 같으면
	//로그 기록을 하고
	if(dt.getTime() < nextDt.getTime()) {
		setTimeout(function(){ mainLog(); }, 1000);
	}
	else {
		setLog();
	}
}
function setLog() {
	//ajax 으로 /common/setLog 를 호출하여 로 그 기록을 진행한다.
	$.ajax({
		type: 'post',
		url: '/common/setLog',
		dataType: 'json',
		crossDomain: true,
		success: function (r) {
			//console.log('r.result : ' + r.result);
			switch (r.result) {
				case 'true':
					//성공
					//console.log('완료!!!');
					getMin();
					mainLog();
					break;
				default:
					break;
			}
		},
		error: function (r, textStatus, err) {
			console.log(r);
		},
		complete: function () {
			document.xhr = false;
		}
	});
}


function fleavebtn() {
	var text = getLangStr("jsm_0049") + '\n' + getLangStr("jsm_0050") + '\n' + getLangStr("jsm_0051") + '\n' + getLangStr("jsm_0052");
	
	if (confirm(text))
		location.href='/default/cs/customerService';
}

// 닉네임 검색
function fShowNickSearch(v){
	if ($(v).hasClass('nick_show')) {
		$(v).removeClass('nick_show');
	} else {
		$(v).addClass('nick_show');
		setTimeout(function(){
			fSearchNickFocus(0, 1);
		},400);
	}
}

function fSearchNickFocus(setBlur, setFocus) {
	var varUA = navigator.userAgent.toLowerCase(); //userAgent 값 얻기
	
	if (setBlur)
		$('input[name="H_nick"]').blur();
	if (varUA.match('android') != null) {
		//안드로이드 일때 처리
		$('.atm_gnb_scroll_menu').animate({ scrollTop: 295 }, 400, function(){
			if (setFocus)f
				$('input[name="H_nick"]').focus().select();
		});
	} else {
		if (setFocus)
			$('input[name="H_nick"]').focus().select();
	}
}

function fSearchNick(v)
{
	var nickVal = $(v).siblings('input[name=H_nick]').val();
	var frm = $('<form></form>');
	var act = $('<input type="hidden" name="ACT" value="SEARCH_NICK">');
	var Hnick = $('<input type="text" name="H_nick" value="'+ nickVal +'">')
	frm.append(act).append(Hnick);
	libAjax(frm.serialize());
}
function libAjax(param) {
	if (document.libXHR) {
		$('#Tip').text('<%=CommonUtil.getLangMsg(request, "msg_0214")%>').css('display', 'block');
		setTimeout(function () { $('#Tip').stop().fadeOut(300) }, 3000);
		return;
	}
	
	var isRemove = true;
	$('.nick_show').siblings('.nick_search').find('.nick_search_bar').addClass('nick_loader').find('input[name="H_nick"]').attr('readonly','true');
	//searchNick.php
	document.libXHR = $.ajax({
		type: 'post',
		url: '/common/searchNick',
		data: param,
		dataType: 'json',
		success: function (r) {
			switch (r.result) {
				case 'SEARCH_NICK':
					{
						isRemove = false;
						location.href = "/member/otherProfileInfo?MemberSeq=" + r.arr.seq;
						break;
					}
				default:
					if (r.result) alert(r.result);
					break;
			}
		},
		error: function (r, textStatus, err) {
			if (r.responseText && r.responseText.substr(0, 13) == '<!--LOGOFF-->') { top.location = '${libIMG_URL}'; return; }
			alert('<%=CommonUtil.getLangMsg(request, "msg_0215")%>');
			var str = '';
			for (var key in r) str += key + '=' + r[key] + '\n';
			//console.log(str);
		},
		complete: function () {
			document.libXHR = false;
			if (isRemove) {
				$('.nick_show').siblings('.nick_search').find('.nick_search_bar').removeClass('nick_loader').find('input[name="H_nick"]').removeAttr('readonly');
				fSearchNickFocus(1, 1);
			}
		}
	});
}
</script>
</c:if>
<script  type="text/javascript" src="/Common/js_new/default/keypress.js?ver=1.4" ></script>
<script type="text/javascript" src="/Common/js_new/default/main_search.js?ver=2.2"></script>
