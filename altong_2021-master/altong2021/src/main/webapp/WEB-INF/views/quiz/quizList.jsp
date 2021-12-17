<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
${quiz.useChk}<br/>
${quiz.uid}<br/>
${quiz.rtime1}<br/>
${quiz.rtime2}<br/>
${quiz.stime1}<br/>
${quiz.stime2}<br/>

${quiz.quest}<br/>
${quiz.correct}<br/>
--------------------<br/><br/>
<c:forEach var="ans" items="${ansList}" varStatus="status">
${ans.ano} : ${ans.answer}<br/>
</c:forEach>
--------------------<br/><br/>
${quiz.hint}<br/>
${quiz.comment}<br/>
</body>
</html>