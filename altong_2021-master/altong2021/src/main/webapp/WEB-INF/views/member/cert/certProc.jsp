<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="/Common/js/polyfill.min.js?2019-04-26-2"></script>
<script>
$(function(){
	var flagNice = '${flagNice}';
	var message = '${message}';
	var errorFlag = '${errorFlag}';
	var certFlag = '${certFlag}';
	
	if(message != '') {
		alert(message);
	}
	
	if(opener && opener.afterNiceCert) {
		opener.afterNiceCert($errorFlag, $certFlag);
        window.close();
	}
	else {
		location.href = "/member/cert/index";
	}
});
</script>
</head>
<body>

</body>
</html>