<%@page import="com.altong.web.logic.question.QuestionVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@ page import="com.altong.web.logic.event.EventVO"%>
<%@ page import="com.altong.web.logic.util.CommonUtil"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp"%>
<%@ page import="org.json.simple.JSONObject"%>
<%
    String status = "";
    
    if(request.getAttribute("Status") != null) {
    	status = String.valueOf(request.getAttribute("Status"));
    }
    
    JSONObject global_info = (JSONObject) CommonUtil.getGlobal(request, response);
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<link rel="stylesheet" href="/pub/answer/rankAnswer/rankAnswer.css?ver=1.8">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.4">
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<div id="wrapper">
	<%@ include file="/Common/include/EventBanner.jsp"%>
	<!--wrapper start -->


	<div id="atm_ranka">
		<div class="center">
			<ul>
				<li><a href="/question/bestList"><spring:message code="msg_0358"/></a></li>
				<li><a href="/answer/questionList"><spring:message code="msg_0161"/></a></li>
				<%
					if (global_info != null) {
				%>
				<li><a href="javascript:void(0);" onClick="location.href='/answer/favoriteList';"><spring:message code="msg_0175"/></a></li>
				<%
					} else {
				%>
				<li><a href="javascript:void(0);" onClick="goConfirmLogin('frm_sch', '/default/login');"><spring:message code="msg_0175"/></a></li>
				<%}%>
				<li class="check"><a href="/question/eventList"><spring:message code="msg_0183"/></a></li>
				<li><a href="/answer/rankAnswer"><spring:message code="msg_0182"/></a></li>
			</ul>
		</div>
	</div>
	
	<div class="atm_rankq2_con">
		<div class="center">
			<div class="atm_ranka_tab2">
				<div class="atm_ranka_tab2_pc">
					<p class="atm_ranka_tabc6" onclick="location.href='/question/eventList?Status=1';"><spring:message code="msg_0863"/></p>
					<p class="atm_ranka_tabc7" onclick="location.href='/question/eventList?Status=0';"><spring:message code="msg_0934"/></p>
					<p class="atm_ranka_tabc8_on" onclick="location.href='/question/event/interviewList';"><spring:message code="msg_0935"/></p>
					<p class="atm_ranka_tabc9" onclick="location.href='/roulette/game';"><spring:message code="msg_0936"/></p>
				</div>
			</div>
		
			<div class="atm_rank_el_container">
				<div class="atm_rankq2_con">
				<c:forEach var="item" items="${interViewList}" varStatus="status">
					<div class="atm_ranka12_el atm_border" onclick="location.href='/question/event/interView?idx=${item.itv_idx}'">
						<div class="atm_ranka12_c5">
						${item.itv_title}
						</div>
						<p class="atm_ranka12_c7">
							${item.itv_date_reg}&nbsp;
							<span class="atm_whitespace"><img src="/Common/images/icon_view.svg" class="atm_viewicon"> ${item.itv_page_view}</span>
						</p>
					</div>
				</c:forEach>
				<!--<img src="/Common/images/banner.jpg" class="roulette_banner" onclick="location.href='/roulette/game'" />-->
				</div>
			</div>
		</div><!-- center end -->
	</div><!-- atm_rankq2_con end -->
</div>
<c:if test="${n_totalpage > 1}">
<div class="list_pasing">
${paging}
</div>
</c:if>
<div id="top_btn">
	<a href="#"> <span> <img
			src="/Common/images/top_btn_arrow.svg" alt="top_btn">
	</span>
	</a>
</div>
<script type="text/javascript" src="/pub/question/event/event.js?1.1" ></script>
</body>
</html>

