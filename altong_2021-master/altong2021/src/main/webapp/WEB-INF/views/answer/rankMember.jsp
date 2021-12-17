<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jasp.util.*"%>
<%@ page import="com.altong.web.logic.util.CommonUtil"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp"%>
<head>
<link rel="stylesheet" href="/pub/answer/rankAnswer/rankAnswer.css?ver=1.3">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.5">
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<div id="wrapper">
	<%@ include file="/Common/include/EventBanner.jsp"%>
	<div id="atm_ranka">
		<div class="center">
			<ul>
				<li><a href="/question/bestList"><spring:message code="msg_0358"/></a></li>
				<li><a href="/answer/questionList"><spring:message code="msg_0161"/></a></li>
				<c:if test="${ userSeq eq 0 }">
					<li><a href="javascript:void(0);" onClick="goConfirmLogin('frm_sch', '/default/login');"><spring:message code="msg_0175"/></a></li>
				</c:if>
				<c:if test="${ userSeq ne 0 }">
					<li><a href="javascript:void(0);" onClick="location.href='/answer/favoriteList';"><spring:message code="msg_0175"/></a></li>
				</c:if>
				<li><a href="/question/eventList"><spring:message code="msg_0183"/></a></li>
				<li class="check"><a href="/answer/rankAnswer"><spring:message code="msg_0182"/></a></li>
			</ul>
		</div>
	</div>
	<div id="rank_gnb_btn">
		<div class="center">
			<ul>
				<li>
					<a href="/question/rankQuestion">
						<spring:message code="msg_0365"/>
					</a>
				</li>
				<li class="on">
					<a href="/answer/rankAnswer">
						<spring:message code="msg_0366"/>
					</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="atm_ranka32_con">
		<div class="center">
			<div class="atm_ranka_tab2">
				<div class="atm_ranka_tab2_pc">
					<c:choose>
						<c:when test="${FlagOption eq 'Count'}">
							<p class="atm_ranka_tabc6" onClick="javascript:location.href='/answer/rankAnswer';"><spring:message code="msg_0367"/></p>
							<p class="atm_ranka_tabc7" onClick="javascript:location.href='/answer/rankMember?FlagOption=Money';" ><spring:message code="msg_0368"/></p>
							<p class="atm_ranka_tabc8_on"><spring:message code="msg_0369"/></p>
						</c:when>
						<c:otherwise>
							<p class="atm_ranka_tabc6" onClick="javascript:location.href='/answer/rankAnswer';"><spring:message code="msg_0367"/></p>
							<p class="atm_ranka_tabc7_on"><spring:message code="msg_0368"/></p>
							<p class="atm_ranka_tabc8" onClick="javascript:location.href='/answer/rankMember?FlagOption=Count';"><spring:message code="msg_0369"/></p>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="atm_rank_el_container">
				<c:forEach var="data" items="${userRanking}" varStatus="status">
					<div class="atm_rank_el" onClick="javascript:location.href='/member/otherProfileInfo?MemberSeq=${data.seq}';">
						<c:choose>
							<c:when test="${data.rankA <= 1}">
								<h4 class="first">${data.rankA}</h4>
							</c:when>
							<c:when test="${data.rankA == 2 or data.rankA == 3}">
								<h4 class="other">${data.rankA}</h4>
							</c:when>
							<c:otherwise>
								<h4>${data.rankA}</h4>
							</c:otherwise>
						</c:choose>
						<figure>
							<c:choose>
								<c:when test="${data.photo ne ''}">
									<img src="/UploadFile/Profile/${data.photo}"
										class="atm_ranka32_img1" />
								</c:when>
								<c:otherwise>
									<img src="/Common/images/img_thum_base0.jpg"
										class="atm_ranka32_img1" />
								</c:otherwise>
							</c:choose>
						</figure>
						<c:choose>
							<c:when test="${FlagOption eq 'Count'}">
								<p class="atm_ranka_el_c1">
									<span class="atm_ranka32_c6">&nbsp;&nbsp;<fmt:formatNumber
											type="number" maxFractionDigits="3" value="${data.countA}" /></span>개
								</p>
							</c:when>
							<c:otherwise>
								<p class="atm_ranka_el_c1">
									<span class="atm_ranka32_c6"><fmt:formatNumber type="number" maxFractionDigits="3"
										value="${data.sumA}" /></span><spring:message code="msg_0136"/>
								</p>
							</c:otherwise>
						</c:choose>
						<div class="atm_ranka32_eltexts">
							<c:choose>
								<c:when test="${FlagOption eq 'Money'}">
									<p class="atm_ranka32_el_c2">${data.nickName}</p>
								</c:when>
								<c:otherwise>
									<p class="atm_ranka32_el_c2">${data.nickName}</p>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${FlagOption eq 'Count'}">
									<c:choose>
										<c:when test="${FlagOption eq 'Count'}">
											<p class="atm_ranka32_el_c3">
												<fmt:formatNumber type="number" maxFractionDigits="3"
													value="${data.sumA}" /><spring:message code="msg_0136"/>
											</p>
										</c:when>
										<c:otherwise>
											<p class="atm_ranka32_el_c3">
												<fmt:formatNumber type="number" maxFractionDigits="3"
													value="${data.sumA}" /><spring:message code="msg_0136"/>
											</p>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${FlagOption eq 'Count'}">
											<p class="atm_ranka32_el_c3">
												<fmt:formatNumber type="number" maxFractionDigits="3"
													value="${data.countA}" /><spring:message code="msg_0370"/>
											</p>
										</c:when>
										<c:otherwise>
											<p class="atm_ranka32_el_c3">
												<spring:message code="msg_0366"/>
												<fmt:formatNumber type="number" maxFractionDigits="3"
													value="${data.countA}" /><spring:message code="msg_0370"/>
											</p>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	
	<div id="atm_ranka32_wrapper0">
		<form name="frm_sch" method="post" onSubmit="return false;">
			<input type="hidden" name="src_Target" value="/answer/rankMember">
			<script>
				var para = document.location.href.split("?")[1];
				$("input[name=src_Target]").attr("value", $("input[name=src_Target]").attr("value") + "?" + para)
			</script>
			<input type="hidden" name="pg" value="${n_curpage}"> <input
				type="hidden" name="FlagOption" value="${FlagOption}">
			

			

			<!-- Paging 시작 -->
			<div class="list_pasing">${paging_Tag}</div>
		</form>
		<%
			JSONObject gl = (JSONObject) CommonUtil.getGlobal(request, response);

			String UserLv = "";
			if (gl != null) {
				UserLv = gl.get("UserLv").toString();
			}
			if (UserLv.equals("99")) {
			%>
		<script>
			function goPageSrch() {
				var maxPage = '${n_totalpage}'
				var page = $('#srchPage').val();
				var flagOption = '${FlagOption}';
				var src_Sort = '${src_Sort}';
				var trec = '${trec}';
				var section1 = '${section1}';
				
				if(page != '') {
					if(parseInt(page, 10) > parseInt(maxPage, 10)) {
						alert('없는 페이지 입니다. 이동할수 있는 최대 페이지는 ' + maxPage + '입니다.');
						$('#srchPage').val(maxPage);
						return false;
					}
					else {
						location.href = '/answer/rankMember?FlagOption=' + flagOption + '&trec=' + trec + '&src_Sort=' + src_Sort + '&Section1=' + section1 + '&pg=' + page;
					}
				}
				else {
					alert('이동을 원하시는 페이지의 숫자를 적어주세요');
					return false;
				}
			}
		</script>
		<c:if test="${n_totalpage > 0}">
			<input type="text" id="srchPage"
				oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
			<input type="button" value="이동" onclick="javascript:goPageSrch()" />
		</c:if>
		<%
			}
		%>
	</div>
</div>
	<script type="text/javascript"
		src="/Common/src/answer/rankAnswer/rankAnswer.js?1.1"></script>
</body>