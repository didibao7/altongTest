<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp"%>
<head>
<link rel="stylesheet" href="/pub/default/joinInput_n2/join.css?ver=1.1">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.5">
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<div id="joinOk_wrap">
	<div class="center"">
		<h1 class="atm_graytop_c1"><spring:message code="msg_0420"/></h1>
		<div class="atm_wrapper2_container">
			<div class="atm_wrapper2_msg_celebration">
				<p><spring:message code="msg_0421"/></p>
			</div>			
			<div class="atm_wrapper2_msg_app">
				<p><spring:message code="msg_0422"/></p>
				<p><spring:message code="msg_0423"/></p>
				<p><spring:message code="msg_0424"/></p>
			</div>
			<div class="atm_wrapper2_button">
				<div onclick="location.href='https://play.google.com/store/apps/details?id=jj.kimmk.altong';">
					<img src="/pub/default/joinOk_n2/images/google-play.png">
					<dl>
						<dt>Google play</dt>
						<dd><spring:message code="msg_0425"/></dd>
					</dl>
				</div>
				<div onclick="location.href='https://itunes.apple.com/us/app/%EC%95%8C%ED%86%B5/id1462406955?l=ko';">
					<img src="/pub/default/joinOk_n2/images/appleStore.png">
					<dl>
						<dt>App Store</dt>
						<dd><spring:message code="msg_0425"/></dd>
					</dl>
				</div>
			</div>
		</div>
	</div>
</div>
</body>