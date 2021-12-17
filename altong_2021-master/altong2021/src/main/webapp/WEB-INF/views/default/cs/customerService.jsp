<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<head>
<link rel="stylesheet" href="/pub/default/cs/customerService/customer.css?ver=1.4">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.6">
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>

<div class="csWrapper">
	<div class="center">
		<div class="atm_edittop_ttbar" >
			<div class="atm_edittop_ttbar_pc" >
				<h1 class="atm_edittop_c1"><spring:message code="msg_0202"/></h1>
			</div>
		</div>
		<div class="atm_cs_info atm_cs_btn" onclick="location.href='tel:82-2-330-3000'">
			<div class="atm_phone_number_wrapper">
				<img class="cs_btn_img01" src="/pub/default/cs/customerService/headphones.svg">
				<p><spring:message code="msg_0197"/></p>
				<h3><spring:message code="msg_0198"/></h3>
				<span><spring:message code="msg_0375"/></span>
				<span><spring:message code="msg_0376"/></span>
			</div>
			<a class="atm_phone_call_btn_wrapper cs_btn">
				<spring:message code="msg_0377"/>
				<i></i>
			</a>
		</div>
		<div class="atm_cs_btn" onclick="location.href='/default/cs/notice/notice?Page=1'">
			<div class="cs_btn">
				<img class="cs_btn_img02" src="/pub/default/cs/customerService/notice_gg.svg">
				<p><spring:message code="msg_0185"/></p>
				<span><spring:message code="msg_0378"/></span>
				<a>
					<spring:message code="msg_0380"/>
					<i></i>
				</a>
			</div>
		</div>
		<div class="atm_cs_btn" onclick="location.href='/default/userGuide'">
			<div class="cs_btn">
				<img class="cs_btn_img02" src="/pub/default/cs/customerService/icon_faq.svg">
				<p><spring:message code="msg_0184"/></p>
				<span><spring:message code="msg_0379"/></span>
				<a>
					<spring:message code="msg_0380"/>
					<i></i>
				</a>
			</div>
		</div>
	</div>
</div>
<script>
	function isMobile() {
		var filter = "win16|win32|win64|mac|macintel|linux";
		if (navigator.platform) {
			if (filter.indexOf(navigator.platform.toLowerCase()) < 0) {
				return true;
			} else {
				return false;
			}
		}
	}
	if(!isMobile()) {
		document.getElementsByClassName('atm_phone_call_btn_wrapper')[0].style.display = 'none';
	}
</script>
</body>
</html>