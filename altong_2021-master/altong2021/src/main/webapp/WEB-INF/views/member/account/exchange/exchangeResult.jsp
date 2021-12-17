<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp"%>
<!DOCTYPE html>
<html>
<html lang="ko">
<head>
<!-- 상위 헤드 -->
<link rel="stylesheet" href="/Common/CSS_new/exchange.css?ver=1.1">
<link rel="stylesheet" href="/Common/CSS_new/mediaQuery.css?ver=1.1">
</head>
<body>
	<%@ include file="/pub/menu/topMenu.jsp"%>
    
    <div id="ex_result_wrapper" class="site">
        <div class="center site-content">
            <h1><spring:message code="msg_0660"/></h1>
            <div>
                <p><spring:message code="msg_0670"/></p>
                <p><spring:message code="msg_0671"/></p>
                <ul>
                    <li><a href="/alpay/user/sub/exchange"><spring:message code="msg_0672"/></a></li>
                    <li><a href="/default/main"><spring:message code="msg_0673"/></a></li>
                </ul>
            </div>
        </div>
    </div>
</body>
</html>