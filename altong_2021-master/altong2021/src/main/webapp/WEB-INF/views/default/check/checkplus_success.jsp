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
    <p><p><p><p>
    <spring:message code="msg_0387"/><br>
    <table border=1>
        <tr>
            <td><spring:message code="msg_0383"/></td>
            <td>${sCipherTime} (YYMMDDHHMMSS)</td>
        </tr>
        <tr>
            <td><spring:message code="msg_0384"/></td>
            <td>${sRequestNumber}</td>
        </tr>            
        <tr>
            <td>NICE응답 번호</td>
            <td>${sResponseNumber}</td>
        </tr>            
        <tr>
            <td><spring:message code="msg_0386"/></td>
            <td>${sAuthType}</td>
        </tr>
		 <tr>
            <td>인증번호</td>
            <td>${sRequestNumber}</td>
        </tr>
        <tr>
            <td>성명</td>
            <td>${sName}</td>
        </tr>
		<tr>
            <td>성별</td>
            <td>${sGender}</td>
        </tr>
		<tr>
            <td>생년월일(YYYYMMDD)</td>
            <td>${sBirthDate}</td>
        </tr>
		<tr>
            <td>내/외국인 정보</td>
            <td>${sNationalInfo}</td>
        </tr>
		<tr>
            <td>DI</td>
            <td>${sDupInfo}</td>
        </tr>
		<tr>
            <td>CI</td>
            <td>${sConnInfo}</td>
        </tr>
		<tr>
			<td>휴대폰번호</td>
            <td>${sMobileNo}</td>
        </tr>
		<tr>
			<td>통신사</td>
			<td>${sMobileCo}</td>
        </tr>
		<tr>
			<td colspan="2">인증 후 결과값은 내부 설정에 따른 값만 리턴받으실 수 있습니다. <br>
			일부 결과값이 null로 리턴되는 경우 관리담당자 또는 계약부서(02-2122-4615)로 문의바랍니다.</td>
		</tr>

   
    </table>
    </center>
</body>
</html>