<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String act = "";
if(request.getParameter("act") != null) {
	act = request.getParameter("act");
}

if(act == "") {
%>

<%@ include file="/WEB-INF/views/member/bank/index.template.jsp" %>
<%
}
%>
