<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>

<nav id="slidebar">
	<div id="gnb_wrapper">
		<h1>
			<div id="slidebar_close">
				<i></i> <i></i>
			</div>
		</h1>

		<div class="gnb_info">
			<div class="gnb_info_login">
				<figure>
					<div class="info_pic">
						<img src="${libIMG_URL}/Common/images/img_thum_base0.jpg"
							alt='<spring:message code="msg_0162"/>'>
					</div>
					<figcaption><%=sLv%></figcaption>
				</figure>
				<div class="gnb_info_name">
					<div>
						<h2>${global.get('UserNickName')}</h2>
						<p>
							<span><spring:message code="msg_0163"/> <spring:message code="msg_0164"/><fmt:formatNumber type="number"
									maxFractionDigits="3" value="${global.get('RankQ')}" /><spring:message code="msg_0165"/>
							</span> <span><spring:message code="msg_0166"/> <spring:message code="msg_0164"/><fmt:formatNumber type="number"
									maxFractionDigits="3" value="${global.get('RankA')}" /><spring:message code="msg_0165"/>
							</span>
						</p>
					</div>
				</div>
				<div class="gnb_info_modify"><spring:message code="msg_0167"/></div>
			</div>
			<div class="gnb_info_logout">
				<h3>
					<spring:message code="msg_0168"/>
					</h1>
					<ul>
						<li><a href="/default/login"><spring:message code="msg_0169"/></a></li>
						<li><a href="#"><spring:message code="msg_0170"/></a></li>
					</ul>
			</div>
		</div>

		<ul id="slide_menu">

			<li><a href="#"><b></b><span><spring:message code="msg_0171"/></span><i class="alarmCnt"></i></a></li>
			<li><a href="#"><b></b><span><spring:message code="msg_0172"/></span></a>
				<ul>
					<li><a href="#"><spring:message code="msg_0173"/></a></li>
					<li><a href="#"><spring:message code="msg_0174"/></a></li>
					<li><a href="#"><spring:message code="msg_0175"/></a></li>
					<li><a href="#"><spring:message code="msg_0176"/></a></li>
					<li><a href="#"><spring:message code="msg_0177"/></a></li>
					<li><a href="#"><spring:message code="msg_0178"/></a></li>
					<li><a href="#"><spring:message code="msg_0179"/></a></li>
				</ul></li>
			<li><a href="#"><b></b><span><spring:message code="msg_0180"/></span></a></li>
			<li><a href="#"><b></b><span><spring:message code="msg_0181"/></span></a></li>



			<li><a href="#"><b></b><span><spring:message code="msg_0182"/></span></a></li>
			<li><a href="#"><b></b><span><spring:message code="msg_0183"/></span></a></li>
			<li><a href="#"><b></b><span><spring:message code="msg_0184"/></span></a></li>
			<li><a href="#"><b></b><span><spring:message code="msg_0185"/></span></a></li>
			<li><a href="#"><b></b><i></i><span><spring:message code="msg_0186"/></span></a></li>
		</ul>

		<div class="gnb_footer">
			<p>
				<a href="/default/logOut"><spring:message code="msg_0187"/></a> <a href="#"><spring:message code="msg_0188"/></a>

			</p>

		</div>
	</div>
</nav>

<script type="text/javascript" src="/Common/js_new/default/main_search.js"></script>    