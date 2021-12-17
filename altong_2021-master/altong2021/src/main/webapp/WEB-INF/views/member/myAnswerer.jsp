<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.altong.web.logic.util.CommonUtil"%>
<%@ page import="com.altong.web.logic.util.EncLibrary" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp"%>
<%@ page import="java.util.*"%>
<%
EncLibrary enc = new EncLibrary();
%>
<head>
<link rel="stylesheet" href="/pub/member/myQuestion/myQuestion.css?ver=1.4">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.4">
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<div class="atm_base_wrapper1">
	<div class="center">
		<div class="atm_edittop_ttbar">
			<h1 class="atm_edittop_c1"><spring:message code="msg_0474"/> / ANSWERer</h1>
		</div>
		<div class="select_wrapper">
			<div class="select_div">
				<div class="atm_temp_tab" onclick="location.href='/member/myRecommend';">
					<p>
						<spring:message code="msg_0474"/>
					</p>
				</div>
				<div class="atm_temp_tab">
					<p class="temp_tab_on">
						ANSWERer
					</p>
				</div>
			</div>
		</div>
		<div class="contentsWrapper02" style="display: block;">
           	<div class="mainContents">
           		<h2 class="main-title">ANSWERer</h2>
                  <span>(Altong Network System Worth Enthusiastic Referrals)</span>
                  
                  <p class="description">
                  <spring:message code="msg_0790"/><span><spring:message code="msg_0791"/></span><spring:message code="msg_0792"/>
                  </p>
                  <p>
                                 <spring:message code="msg_0793"/> 
                  <span><spring:message code="msg_0794"/></span> 
                                  <spring:message code="msg_0795"/>
                  </p>
                  <div class="tree-diagram">
                  	<div class="tree-diagram-top">
                  		<p>
                            <span class="earning-badge2">+</span>
                            <span class="earning-badge"><spring:message code="msg_0791"/></span>
                            <span><spring:message code="msg_0796"/></span>
                        </p>
                        <div>
                        	<div>
                        		<div class="icon-member">
                        			<i></i>
                        			<span></span>
                        		</div>
                        		<p>
                        			<span class="self member-label">${masterName}<spring:message code="msg_0346"/></span>
                        		</p>
                        	</div>
                        </div>
                  	</div>
                  	<div class="tree-diagram-bottom">
						<c:forEach var="ans" items="${answererList}" varStatus="status">
							<c:set var = "phone" value = "${ans.phone}" />
							<%
	                          	String phone = "";
	                          	if(pageContext.getAttribute("phone") != null) {
	                          		phone = enc.AlmoneyDecrypt( String.valueOf(pageContext.getAttribute("phone")) );
	                          		//phone = "**" + phone.substring(phone.length() - 2, phone.length());
	                          		if(phone.length() >= 10) {
	                          			phone = phone.substring(0, 4) + "*" + phone.substring(6, 7) + "*" + phone.substring(8, 9) + "*";
	                          		}
	                          		if(phone.length() == 5) {
	                          			phone = phone.substring(0, 2) + "*" + phone.substring(3, 4) + "*";
	                          		}
	                          	}
							%>
							<c:if test="${status.index < 3}">
								<div>
		                  			<div>
		                  				<div class="icon-member">
		                  					<i></i>
		                  					<span></span>
		                  				</div>
		                  				<p class="member-label">
		                  					<span>${ans.nickName}<spring:message code="msg_0346"/>(<%=phone%>)<br>
		                  					<spring:message code="msg_0797"/><fmt:formatNumber value="${ans.childCnt}" pattern="#,###" /><spring:message code="msg_0798"/></span>
		                  				</p>
		                  			</div>
		                  		</div>
							</c:if>
						</c:forEach>
						<c:if test="${fn:length(answererList)<3}"><c:set var="dif" value="${fn:length(answererList)}"></c:set></c:if>
						<c:if test="${fn:length(answererList)>=3}"><c:set var="dif" value="3"></c:set></c:if>
						<c:forEach var="ans" begin="${dif}" end="2" varStatus="status">
							<div>
	                  			<div>
	                  				<div class="icon-member">
	                  					<i></i>
	                  					<span></span>
	                  				</div>
	                  				<p class="member-label">
	                  					<span><spring:message code="msg_0799"/></span>
	                  				</p>
	                  			</div>
	                  		</div>
						</c:forEach>
                  	</div>
				</div>
              </div>
              <div class="mainContents_line"></div>
              <div class="table-diagram">
              	<p class="title"><spring:message code="msg_0800"/></p>
              	<div>
              		<p>
              			<strong><spring:message code="msg_0801"/></strong>
              			<span><fmt:formatNumber value="${answererCnt}" pattern="#,###" /><spring:message code="msg_0803"/></span>
              		</p>
              		<p>
              			<strong><spring:message code="msg_0802"/></strong>
              			<span><fmt:formatNumber value="${answererMoney}" pattern="#,###" /><spring:message code="msg_0136"/></span>
              		</p>
              	</div>
              	
              </div>
              
           </div>
	</div>
</div>
<script>
</script>
</body>
</html>