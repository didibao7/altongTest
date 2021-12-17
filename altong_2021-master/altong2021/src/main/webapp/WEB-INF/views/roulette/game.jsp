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

<!-- <link rel="stylesheet" href="/Common/CSS_new/roulette.css?ver=1.4"> -->
<!-- <link rel="stylesheet" href="/pub/css/media.css?ver=1.5"> -->

<c:if test="${lang == 'ko'}">
<link rel="stylesheet" href="/pub/roulette/game/style.css?ver=1.0">
<link rel="stylesheet" href="/pub/roulette/game/media.css?ver=1.0">
</c:if>
<c:if test="${lang == 'zh'}">
<link rel="stylesheet" href="/pub/roulette/game/style_zh.css?ver=1.0">
<link rel="stylesheet" href="/pub/roulette/game/media_zh.css?ver=1.0">
</c:if>
<c:if test="${lang == 'ja'}">
<link rel="stylesheet" href="/pub/roulette/game/style_ja.css?ver=1.0">
<link rel="stylesheet" href="/pub/roulette/game/media_ja.css?ver=1.0">
</c:if>
<c:if test="${lang != 'ko' and lang != 'zh' and lang != 'ja'}">
<link rel="stylesheet" href="/pub/roulette/game/style_en.css?ver=1.2">
<link rel="stylesheet" href="/pub/roulette/game/media_en.css?ver=1.3">
</c:if>
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<form name="frm_sch" method="post" onSubmit="return false;"></form>
<div id="wrapper">
	<%//@ include file="/Common/include/EventBanner.jsp"%>
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
			    	<h3><!--<spring:message code="msg_0183"/>--></h3>
			        <p class="atm_ranka_tabc6<% if(status.equals("1")){  %>_on<% } %>" onclick="location.href='/question/eventList?Status=1';"><spring:message code="msg_0863"/></p>
			        <p class="atm_ranka_tabc7<% if(status.equals("0")){  %>_on<% } %>" onclick="location.href='/question/eventList?Status=0';"><spring:message code="msg_0934"/></p>
			        <p class="atm_ranka_tabc8" onclick="location.href='/question/event/interviewList';"><spring:message code="msg_0935"/></p>
			        <p class="atm_ranka_tabc9_on" onclick="location.href='/roulette/game';"><spring:message code="msg_0936"/></p>
			    </div>
			</div>
	
			<div class="atm_rank_el_container">
				<div class="atm_rankq2_con">
					<!-- 룰렛 게임 시작 -->
				    <div class="pop1">
				        <div class="popUp">
				            <h1><spring:message code="msg_0947"/></h1>
				            <p><span></span></p>
				            <h3><spring:message code="msg_0948"/></h3>
				        </div>
				    </div>
				    <div class="pop2">
				        <div class="popUp">
				            <h1><spring:message code="msg_0949"/></h1>
            				<h3><spring:message code="msg_0950"/><br><spring:message code="msg_0951"/><br><spring:message code="msg_0952"/></h3>
				        </div>
				    </div>
				    <div id="wrap">
				        <div class="main">
				            <h1 class="mw1"><spring:message code="msg_0953"/></h1>
				            <h1 class="mw2"><spring:message code="msg_0953"/></h1>
				            <p class="ev"><spring:message code="msg_0954"/></p>
				        </div>
				        <div class="roulette">
				            <img class="niddle" src="/pub/roulette/img/new-niddle.png" alt="niddle">
				            <img class="roul" src="/pub/roulette/img/Roulette.png?ver=1.1" alt="Roulette">
				            <img src="/pub/roulette/img/start-button.png" alt="start" class="start">
				            <h3 class="ticket"><spring:message code="msg_0955"/> : <span>0</span><spring:message code="msg_0956"/></h3>
				        </div>
				        <div class="status">
				            <fieldset>
				                <legend><spring:message code="msg_0957"/></legend>
                                <div class="firstQ1"><spring:message code="msg_0365"/> <span id="queCount">0</span><spring:message code="msg_0370"/> ⊕ <spring:message code="msg_0366"/> <span id="ansCount">0</span><spring:message code="msg_0370"/> </div>
                                <div class="or">OR</div>
                            </fieldset>
                            <fieldset class="field1">
                                <div class="firstQ2"><spring:message code="msg_0261"/> <span id="replCount">0</span><spring:message code="msg_0370"/> ⊕ <spring:message code="msg_0725"/> <span id="estiCount">0</span><spring:message code="msg_0370"/> </div>
                                <div class="or1">OR</div>
                            </fieldset>
                            <fieldset class="field2">
                                <div class="firstQ3"><spring:message code="msg_0958"/> <span id="hunCount">0</span><spring:message code="msg_0814"/></div>
                            </fieldset>
				        </div> 
				        <div class="caution">
				            <p><spring:message code="msg_0959"/></p>
                            <p>* <spring:message code="msg_0960"/><span>${queCountConfig}</span><spring:message code="msg_0961"/><spring:message code="msg_0163"/>&nbsp;+
                                                                  <span>${ansCountConfig}</span><spring:message code="msg_0961"/><spring:message code="msg_0366"/><spring:message code="msg_0962"/>
                                                                  <span>${replCountConfig}</span><spring:message code="msg_0961"/><spring:message code="msg_0261"/>&nbsp;+
                                                                  <span>${estiCountConfig}</span><spring:message code="msg_0961"/><spring:message code="msg_0725"/><spring:message code="msg_0962"/>
                                                                  <span>${hunCountConfig}</span><spring:message code="msg_0963"/><spring:message code="msg_0958"/><spring:message code="msg_0964"/><spring:message code="msg_0965"/>
                            </p>
				            <p>* <spring:message code="msg_0966"/><span><spring:message code="msg_0967"/></span></p>
				            <p>* <spring:message code="msg_0968"/></p>
				            <p><span>※ <spring:message code="msg_0969"/></span></p>
				        </div>
				    </div>
				    <!-- 룰렛 게임 종료 -->
				</div>
			</div>
			<!--wrapper end -->
	
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

<script src="/Common/js_new/event/jQueryRotateCompressed.js"></script>
<script src="/Common/js_new/event/roulette.js?ver=2.0"></script>
<script type="text/javascript" src="/pub/question/event/event.js?1.2" ></script>
</body>
</html>

