<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.altong.web.logic.util.CommonUtil"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<head>
	<link rel="stylesheet" href="/pub/member/myQuestion/myQuestion.css?ver=2.0">
	<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.6">
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<div id="atm_myjjim_wrapper0">
<form name="myAnswer_frm_sch" method="post" onSubmit="return false;">
<input type="hidden" name="src_Target" value="${curPageName}">
<input type="hidden" name="pg" value="${n_curpage}">
<div class="atm_mycatebar1">
	<div class="center">
		<div class="atm_mycatebar1_pc">
			<p class="atm_mycatebar_c1">
			<c:choose>
				<c:when test="${flagChoice == ''}"><spring:message code="msg_0783"/></c:when>
				<c:when test="${flagChoice == 'Y'}"><spring:message code="msg_0784"/></c:when>
				<c:otherwise><spring:message code="msg_0785"/></c:otherwise>
			</c:choose><spring:message code="msg_0366"/> <span class="atm_mycatebar_c2"> ${n_trec}</span><spring:message code="msg_0370"/></p>
			<div class="atm_mycatebar_box">
				<select name="FlagChoice" class="atm_mycatebar_sel atm_mycatebar_answer" onChange="javascript:myAnswer_goSubmit('myAnswer_frm_sch','${curPageName}','_self');">
					<option value="" <c:if test="${flagChoice == ''}">selected</c:if>><spring:message code="msg_0786"/></option>
					<option value="Y" <c:if test="${flagChoice == 'Y'}">selected</c:if>><spring:message code="msg_0787"/></option>
					<option value="N" <c:if test="${flagChoice == 'N'}">selected</c:if>><spring:message code="msg_0788"/></option>
				</select>
			</div>
		</div>
	</div>
</div><!-- atm_mycatebar1 end -->
<div class="atm_myjjim_con">
	<div class="center">
	<c:forEach var="ans" items="${myAnsList}" varStatus="status">
		<div class="atm_myjjim_el atm_border" onClick="location.href='/answer/answerList?Seq=${ans.seq}';">
			<div class="atm_myjjim_c5" style="color:#000;font-weight:normal">
				<h3>Q.</h3>
				<p class="atm_myjjim_answer">
					<c:set var="ans_almoney" value="${ans.almoney}" />
					<fmt:parseNumber var="ans_almoney_2" type="number" value="${ans_almoney}"></fmt:parseNumber>
					<span class="atm_icon_score<% 
								int usr_color = Integer.parseInt(String.valueOf(pageContext.getAttribute("ans_almoney_2")));
								if (usr_color >= 5000) { %>_red<% } %>">
								<fmt:formatNumber value="${ans.almoney}" pattern="#,###" /></span>
					${ans.title}
				</p>
			</div>
			<p class="beefup_head">${fn:substring(  fn:trim(ans.answer.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>","") ), 0, 280)}</p>
			<ul class="atm_myjjim_c7"> &nbsp;
				<li>${ans.dateReg}</li>
				<li><img src="/Common/images/icon_view.svg" class="atm_viewicon"/><fmt:formatNumber value="${ans.readCount}" pattern="#,###" /></li>
				<li><span>A</span>1/<fmt:formatNumber value="${ans.answerCount}" pattern="#,###" /></li>
			</p>
		</div>
	</c:forEach>
	</div>
</div>
<div class="list_pasing">
	${paging}
</div>
</form>
<iframe name="ifrm" width="100%" height="0" frameborder="0"></iframe>
</div>
<script type="text/javascript" src="/Common/src/member/myAnswer/myAnswer.js?ver=1.2" ></script>
</body>