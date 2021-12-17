<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><spring:message code="msg_0381"/></title>
</head>
<body>
	<center>
	${sResultMessage}
    <p><p><p><p>
    <spring:message code="msg_0382"/><br>
    <table width=500 border=1>
        <tr>
            <td><spring:message code="msg_0383"/></td>
            <td>${sCipherTime} (YYMMDDHHMMSS)</td>
        </tr>
        <tr>
            <td><spring:message code="msg_0384"/></td>
            <td>${sRequestNumber}</td>
        </tr>            
        <tr>
            <td><spring:message code="msg_0385"/></td>
            <td>${sErrorCode}</td>
        </tr>            
        <tr>
            <td><spring:message code="msg_0386"/></td>
            <td>${sAuthType}</td>
        </tr>
    </table>
    </center>
</body>
</html>