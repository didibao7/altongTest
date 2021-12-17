<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp"%>

<head>
<link rel="stylesheet" href="/pub/answer/rankAnswer/rankAnswer.css?ver=1.3">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.5">
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<div id="wrapper">
	<%@ include file="/Common/include/EventBanner.jsp" %>
	<div id="atm_ranka">
		<div class="center">
			<ul>
				<li><a href="/question/bestList"><spring:message code="msg_0358"/></a></li>
				<li><a href="/answer/questionList"><spring:message code="msg_0161"/></a></li>
				<c:if test="${ userSeq ne 0 }">
					<li><a href="javascript:void(0);" onClick="location.href='/answer/favoriteList';"><spring:message code="msg_0175"/></a></li>
				</c:if>
				<c:if test="${ userSeq eq 0 }">
				<li><a href="javascript:void(0);" onClick="goConfirmLogin('frm_sch', '/default/login');"><spring:message code="msg_0175"/></a></li>
				</c:if>
				<li><a href="/question/eventList"><spring:message code="msg_0183"/></a></li>
				<li class="check"><a href="/answer/rankAnswer"><spring:message code="msg_0182"/></a></li>
			</ul>
		</div>
	</div>
	<div id="rank_gnb_btn">
		<div class="center">
			<ul>
				<li class="on">
					<a href="/question/rankQuestion">
						<spring:message code="msg_0365"/>
					</a>
				</li>
				<li>
					<a href="/answer/rankAnswer">
						<spring:message code="msg_0366"/>
					</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="atm_rankq31_con">
		<div class="center">
			<div class="atm_ranka_tab2">
				<div class="atm_ranka_tab2_pc">
					<c:choose>
						<c:when test="${flagOption eq 'Money'}">
							<p class="atm_ranka_tabc6" onClick="javascript:location.href='/question/rankQuestion';"><spring:message code="msg_0367"/></p>
							<p class="atm_ranka_tabc7_on"><spring:message code="msg_0368"/></p>
							<p class="atm_ranka_tabc8" onClick="javascript:location.href='/question/rankUser?FlagOption=Count';"><spring:message code="msg_0369"/></p>
						</c:when>
						<c:otherwise>
							<p class="atm_ranka_tabc6" onClick="javascript:location.href='/question/rankQuestion';"><spring:message code="msg_0367"/></p>
							<p class="atm_ranka_tabc7" onClick="javascript:location.href='/question/rankUser?FlagOption=Money';"><spring:message code="msg_0368"/></p>
							<p class="atm_ranka_tabc8_on"><spring:message code="msg_0369"/></p>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="atm_rank_el_container">
				<c:forEach var="rank" items="${rankList}" varStatus="status">
					<div class="atm_rank_el">
						<c:choose>
							<c:when test="${rank.idx <= 1}">
								<h4 class="first">${rank.idx}</h4>
							</c:when>
							<c:when test="${rank.idx == 2 or rank.idx == 3}">
								<h4 class="other">${rank.idx}</h4>
							</c:when>
							<c:otherwise>
								<h4>${rank.idx}</h4>
							</c:otherwise>
						</c:choose>
						<figure>
							<c:choose>
								<c:when test="${rank.photo == ''}">
									<c:set var="photo" value="/Common/images/img_thum_base0.jpg"/>
								</c:when>
								<c:otherwise>
									<c:set var="photo" value="/UploadFile/Profile/${rank.photo}"/>
								</c:otherwise>
							</c:choose>
								<img src="${photo}" class="atm_ranka32_img1" />
						</figure>
						<div class="atm_ranka32_eltexts">
							<p class="atm_ranka32_el_c2"onClick="javascript:location.href='/member/otherProfileInfo?MemberSeq=${rank.seq}';">
								${rank.nickName}
							</p>
							<p class="atm_ranka32_el_c3">
								<p class="atm_ranka32_c2"><spring:message code="msg_0365"/> <fmt:formatNumber value="${rank.countQ}" type="number" /><spring:message code="msg_0370"/></p>
							</p>
						</div>
						<p class="atm_ranka_el_c1">
							<span class="atm_ranka32_c6">
								<fmt:formatNumber value="${rank.sumQ}" type="number" />
							</span>
							알
						</p>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</div>

<div class="list_pasing">
	${paging}
</div>
<div id="top_btn">
	<a href="javascript:void(0);"> <span> <img
			src="/Common/images/top_btn_arrow.svg" alt="top_btn">
	</span>
	</a>
</div>
<script type="text/javascript" src="/Common/src/answer/rankAnswer/rankAnswer.js?1.1" ></script>
</body>