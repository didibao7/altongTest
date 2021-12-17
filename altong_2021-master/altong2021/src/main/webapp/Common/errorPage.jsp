<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.altong.web.logic.util.CommonUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error Page</title>
</head>
<body>
<script src="http://code.jquery.com/jquery-1.12.4.js"></script>
	<c:if test="${requestScope['javax.servlet.error.status_code'] == 400}">
		<p><spring:message code="msg_0189"/></p>
		<a href="/">HOME</a>
	</c:if>

	<c:if test="${requestScope['javax.servlet.error.status_code'] == 404}">
		<!--  <p>요청하신 페이지를 찾을 수 없습니다.</p> -->
		<script>
			$(document).ready(function() {
				location.href = '/';
			});
		</script>
	</c:if>

	<c:if test="${requestScope['javax.servlet.error.status_code'] == 405}">
		<p><spring:message code="msg_0190"/></p>
		<a href="/">HOME</a>
	</c:if>

	<c:if test="${requestScope['javax.servlet.error.status_code'] == 500}">
		<p><spring:message code="msg_0191"/></p>
		<script>
			function setCookie(name, value, expiredays) {
				var cookie = name + "=" + escape(value) + "; path=/;"
				if (typeof expiredays != 'undefined') {
					var todayDate = new Date();
					todayDate.setDate(todayDate.getDate() + expiredays);
					cookie += "expires=" + todayDate.toGMTString() + ";"
				}
				document.cookie = cookie;
			}

			function deleteCookie(name) {
				setCookie(name, "", -1);
			}

			$(document).ready(function() {
				/*
				deleteCookie("SESC");
				deleteCookie("SESS");
				deleteCookie("keypresssound");
				deleteCookie("JSESSIONID");
				*/
				//location.href = '/';
				history.back();
			});
		</script>
	</c:if>

	<c:if test="${requestScope['javax.servlet.error.status_code'] == 503}">
		<p><spring:message code="msg_0192"/></p>
		<script>
			function setCookie(name, value, expiredays) {
				var cookie = name + "=" + escape(value) + "; path=/;"
				if (typeof expiredays != 'undefined') {
					var todayDate = new Date();
					todayDate.setDate(todayDate.getDate() + expiredays);
					cookie += "expires=" + todayDate.toGMTString() + ";"
				}
				document.cookie = cookie;
			}

			function deleteCookie(name) {
				setCookie(name, "", -1);
			}

			$(document).ready(function() {
				/*
				deleteCookie("SESC");
				deleteCookie("SESS");
				deleteCookie("keypresssound");
				deleteCookie("JSESSIONID");
				*/
				location.href = '/';
			});
		</script>
	</c:if>

	<!--  <a href="/">HOME</a> -->
</body>

<script>
	$(document).ready(function() {
		location.href = '/';
	});
</script>

</html>