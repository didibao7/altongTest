<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.altong.web.logic.util.CommonUtil"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp"%>
<head>

<c:if test="${lang != 'en'}">
<link rel="stylesheet" href="/pub/member/myQuestion/myQuestion.css?ver=1.0">
</c:if>
<c:if test="${lang == 'en'}">
<link rel="stylesheet" href="/pub/member/myQuestion/myQuestion_en.css?ver=1.0">
</c:if>
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.3">
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<%@ include file="/Common/include/member/InterestTemplate.html" %>

<div id="atm_cateedit_wrapper">
	<div class="center">
		<div class="atm_edittop_ttbar">
			<div class="atm_edittop_ttbar_pc">
				<h1 class="atm_edittop_c1">
					<spring:message code="msg_0175"/>
				</h1>
			</div>
		</div>
		<div class="atm_cateedit_wrapper2">
			<c:set var="i" value="${fn:length(interestList)}"/>
			<script>
				var myInterestCount=${i}
			</script>
			<p class="atm_cateedit_c1"><spring:message code="msg_0772"/>&nbsp;(${i})</p>
			<c:choose>
				<c:when test="${i > 0}">
					<c:forEach var="intrest" items="${interestList}" varStatus="status">
						<c:set var="section1" value="${intrest.Section1}"/>
						<c:set var="section2" value="${intrest.Section2}"/>
						<c:set var="section3" value="${intrest.Section3}"/>
						<c:set var="section4" value="${intrest.Section4}"/>
						<c:set var="section5" value="${intrest.Section5}"/>
						
						<%
							String cate1 = String.valueOf(pageContext.getAttribute("section1"));
							String cate2 = String.valueOf(pageContext.getAttribute("section2"));
							String cate3 = String.valueOf(pageContext.getAttribute("section3"));
							String cate4 = String.valueOf(pageContext.getAttribute("section4"));
							String cate5 = String.valueOf(pageContext.getAttribute("section5"));
							
							String strCdNm = "";
							if(!cate5.equals("0") && !cate5.equals("")) {
								strCdNm = CommonUtil.getLangMsg(request, "cate5_" + cate5);
							}
							else if(!cate4.equals("0") && !cate4.equals("")) {
								strCdNm = CommonUtil.getLangMsg(request, "cate4_" + cate4);
							}
							else if(!cate3.equals("0") && !cate3.equals("")) { 
								strCdNm = CommonUtil.getLangMsg(request, "cate3_" + cate3);
							}
							else if(!cate2.equals("0") && !cate2.equals("")) {
								strCdNm = CommonUtil.getLangMsg(request, "cate2_" + cate2);
							}
							else if(!cate1.equals("0") && !cate1.equals("")) {
								strCdNm = CommonUtil.getLangMsg(request, "cate" + cate1);
							}
						%>
						
						
						<div class="atm_cateedit_el atm_border ${i}">
							<p class="atm_cateedit_c5">
					  			<%=strCdNm%>
						  	</p>
						  	<div class="atm_cateedit_xbtn" data-seq="${intrest.SEQNO}">
						  		<i></i>
						  		<i></i>
						  	</div>
						</div>
					<c:set var="i" value="${i + 1}"/>
					</c:forEach>
				</c:when>
				<c:otherwise>
				<div class="atm_cateedit_el atm_border ${i}">
					<p class="atm_cateedit_c5">
			        	<spring:message code="msg_0773"/>
			        </p>
				</div>
				</c:otherwise>
			</c:choose>
		</div><!--atm_cateedit_wrapper2 end -->
		<div class="atm_cateedit_G1">
			<p class="atm_cateedit_c2"><spring:message code="msg_0774"/></p>
			<div class="atm_cateadd_line"></div>
			<!--카테고리추가 -->
			
			<div class="atm_cateadd">
				<div class="atm_cateadd_navi">
					<div class="atm_catenavi_el3 naviNow" id="nav1"><spring:message code="msg_0775"/></div>
					<div class="atm_catenavi_el3 naviNext" id="nav2"><spring:message code="msg_0776"/></div>
					<div class="atm_catenavi_el3" id="nav3"><spring:message code="msg_0777"/></div>
					<div class="atm_catenavi_el3" id="nav4"><spring:message code="msg_0778"/></div>
					<div class="atm_catenavi_el3" id="nav5"><spring:message code="msg_0779"/></div>
	            </div>
	            <div class="frmWrapper">
	            	<div class="atm_cateadd_con_wrapper">
						<div class="atm_cateadd_con">
						</div>
						<!--카테고리 뜨우기 시작-->
						<!--카테고리 띄우기 완료-->
					</div><!-- atm_cateadd_con_wrapper end -->
					
					<div class="atm_cateadd_line"></div>
					
					<input type="hidden" name="UseSeq" value='${userSeq}'>
					<input type="hidden" name="Sec1" value="NULL">
					<input type="hidden" name="Sec2" value="NULL">
					<input type="hidden" name="Sec3" value="NULL">
					<input type="hidden" name="Sec4" value="NULL">
					<input type="hidden" name="Sec5" value="NULL">
					<div class="atm_cateadd_bot">
						<p class="atm_btn_gray" id="cancel_btn"><spring:message code="msg_0159"/></p>
						<p class="atm_btn_gre" id="add_btn"><spring:message code="msg_0782"/></p>
					</div>
	            </div><!--frmWrapper end -->
			</div><!--atm_cateadd end -->
			<!--카테고리추가 -->
		</div><!--atm_cateedit_G1 end -->
		
		<div class="atm_cateedit_btn atm_cateeditbtn_add">
		    <a href="#">
		    	<div>
		    		<h3>
		    			<b></b>
		    			<b></b>
		    		</h3>
		    		<p><spring:message code="msg_0780"/></p>
		    		<span><spring:message code="msg_0781"/></span>
		    	</div>
		    </a>
		</div>
		<iframe name="ifrm" width="100%" height="0" frameborder="0"></iframe>
		<!--wrapper end -->
	</div><!-- 두번째 atm_wrapper end -->
</div><!-- 첫번째 atm_wrapper end -->

<script src="/pub/member/myInterest/myInterest.js?ver=1.1"></script>
<script>
	view.initView()
</script>
</body>