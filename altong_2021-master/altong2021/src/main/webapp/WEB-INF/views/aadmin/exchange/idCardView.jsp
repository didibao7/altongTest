<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/Common/include/headerAdmin.jsp" %>
<div class="container">
    <div class="row">
    <c:choose>
    	<c:when test="${fCnt > 0}">
    	<c:forEach var="f" items="${fileList}" varStatus="status">
    	<img src="${prefix}${f}" class="img-responsive col-sm-6 col-md-4"/>
    	</c:forEach>
    	</c:when>
    	<c:otherwise>
    	신분증이 업로드 되지 않았습니다.
    	</c:otherwise>
    </c:choose>
    </div>
</div>