<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/pub/default/joinInput_n2/join.css?ver=1.1">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.5">
</head>
<body>>
<%@ include file="/pub/menu/topMenu.jsp"%>
<div id="passwordReset_wrapper">
	<div class="center">
		<div class="atm_edittop_ttbar">
			<div class="atm_edittop_ttbar_pc">
				<h1 class="atm_edittop_c1">
					<spring:message code="msg_0388"/>
				</h1>
			</div>
		</div>
		<div class="atm_login_con">
			<form name="frm_sch" method="post" onsubmit="return false;">
				<p>
					<spring:message code="msg_0393"/><br>
					<spring:message code="msg_0394"/><br><br>
					<spring:message code="msg_0395"/>                                 
				</p>
				<p class="atm_login_btn" onclick="location.href='/default/login'"><spring:message code="msg_0169"/></p>
				<div class="atm_login_line"></div>
				<p class="atm_login_c4">
					<span class="atm_whitespace">
						<a href="/default/joinRule">
							<span class="atm_login_c4"><spring:message code="msg_0170"/></span>
						</a>
						&nbsp;|&nbsp;
						<a href="/default/password/passwordReset">
							<span class="atm_login_c4"><spring:message code="msg_0392"/></span>
						</a>
					</span>
				</p>
			</form>
		</div>
	</div>
</div>
</body>
</html>