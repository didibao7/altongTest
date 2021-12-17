<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<%
JSONObject bot_info = (JSONObject)CommonUtil.getGlobal(request, response);
pageContext.setAttribute("global", bot_info);
%>
<style>
@media screen and (min-width: 800px) {
.atm_btn_question {position:fixed;bottom:20px;left:50%;padding:0;margin-left:290px;width:200px;display:table}
}
</style>
<c:choose>
	<c:when test="${fn:trim(libWriteURL) ne ''}">
		<c:if test="${fn:indexOf(libWriteURL, 'questionWrite') ne -1}">
			<a href="${libWriteURL}"><img src="/Common/images/btn_ask3.png" class="atm_btn_question" id="sticky"/></a>
		</c:if>
		<c:if test="${fn:indexOf(libWriteURL, 'questionWrite') eq -1}">
			<a href="${libWriteURL}"><img src="/Common/images/btn_ask.png" class="atm_btn_question" id="sticky"/></a>
		</c:if>
	</c:when>
	<c:otherwise>
		<c:if test="${global != null}">
		<a href="/question/questionWrite"><img src="/Common/images/btn_ask.png" class="atm_btn_question" id="sticky"/></a>
		</c:if>
		<c:if test="${global == null}">
		<a href="javascript:void(0);" onclick="alert('글 입력이 불가능한 레벨입니다.\n운영팀에 문의하여 주시기 바랍니다.'); location.href='/default/login';"><img src="/Common/images/btn_ask.png" class="atm_btn_question" id="sticky"/></a>
		</c:if>
	</c:otherwise>
</c:choose>
<%@ include file="/Common/include/MenuItem.jsp" %>
<!--2017.9.8 차건환 질문하기 아이콘 스크롤 작업-->
<script>
$('.atm_btn_question').fadeIn('slow');
$.fn.scrollBottom = function() { 
    return $(document).height() - this.scrollTop() - this.height(); 
};

$(document).ready(function(){
	$('#footer').css('background-color','#f2f2f2');
});
</script>