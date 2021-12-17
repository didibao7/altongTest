<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.altong.web.logic.util.CommonUtil"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<link rel="stylesheet" href="/pub/css/join.css?ver=1.1">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.4">
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<div id="atm_memagree_wrapper0">

<c:set var="memJoinCertType" value="${memJoinCertType}" />
<%
String memJoinCertType = String.valueOf( request.getAttribute("memJoinCertType") );
%>
	<div class="center">
		<form name="frm">
		<!--wrapper start -->
		<div class="atm_graytop_ttbar" >
			<div class="atm_graytop_ttbar_pc" >
				<p class="atm_graytop_c1"><spring:message code="msg_0426"/></p>
			</div>
		</div>
		
		<div class="atm_memagree_con">
			<div class="atm_memagree_con_el atm_border">
				<div class="atm_memagree_G1">
					<div class="atm_memagree_opt">
						<p class="atm_memagree_c1"><spring:message code="msg_0427"/></p>
						<p class="atm_memagree_c2"><spring:message code="msg_0428"/></p>
						<div class="atm_memagree_optR2">
							<label class="with-font-mint" class="agree-check-box-all">
								<input type="checkbox" id="agree-all" class="agree-check-box-all"/>
								<label class="with-font-mint" for="agree-all"></label>
							</label>
						</div>						
					</div>
				</div>
				<div class="atm_memagree_G2">
					<div class="atm_memagree_opt">
						<p class="atm_memagree_c1"><spring:message code="msg_0429"/></p>
						<div class="atm_memagree_optR2">
							<input type="checkbox" id="agree-service" class="agree-check-box" data-section='<spring:message code="msg_0430"/>'/>
							<label class="with-font-mint" for="agree-service"></label>
						</div>
					</div>
					<div class="atm_memagree_text">
						<textarea readonly class="atm_memagree_c3"><%@ include file="/WEB-INF/views/default/rule_1.jsp" %></textarea>
						<span class="show-more"><spring:message code="msg_0431"/></span>
					</div>
				</div>
				<div class="atm_memagree_G3">
					<div class="atm_memagree_opt">
						<p class="atm_memagree_c1"><spring:message code="msg_0432"/></p>
						<div class="atm_memagree_optR2">
							<input type="checkbox" id="agree-private-info" class="agree-check-box" data-section='<spring:message code="msg_0433"/>'
							/>
							<label class="with-font-mint" for="agree-private-info"></label>
						</div>
					</div>
					<div class="atm_memagree_text">	
						<textarea readonly class="atm_memagree_c3"><%@ include file="/WEB-INF/views/default/rule_2.jsp" %></textarea>
							<span class="show-more"><spring:message code="msg_0431"/></span>
						</div>
					</div>
					<div class="atm_memagree_G4">
						<div class="atm_memagree_opt">
							<p class="atm_memagree_c1"><spring:message code="msg_0434"/></p>
							<div class="atm_memagree_optR2">
								<input type="checkbox" id="agree-marketing" class="agree-check-box" data-section='<spring:message code="msg_0435"/>'
								/>
								<label class="with-font-mint" for="agree-marketing"></label>
							</div>
						</div>	
						<div class="atm_memagree_text">
							<textarea readonly class="atm_memagree_c3"><%@ include file="/WEB-INF/views/default/rule_3.jsp" %></textarea>
							<span class="show-more"><spring:message code="msg_0431"/></span>
						</div>
					</div>	
				</div>
				<input type="hidden" name="m" value="checkplusSerivce">						<!-- 필수 데이타로, 누락하시면 안됩니다. -->
				<input type="hidden" name="EncodeData" value="${sEncData}">		<!-- 위에서 업체정보를 암호화 한 데이타입니다. -->
				<!--<p class="atm_memagreePhone_btn atm_bolder" onClick="javascript:fnPopup();"> 휴대폰 본인인증</p>-->
				<p class="atm_memagreePhone_btn atm_bolder" id="certify-btn"><spring:message code="msg_0436"/></p>

		</div>
	
	</form>
	<!--wrapper end -->
	
	</div><!-- atm_memagree_wrapper0 end -->

</div>
<div id="top_btn">
	<a href="#"> <span> <img
			src="/Common/images/top_btn_arrow.svg" alt="top_btn">
	</span>
	</a>
</div>
<script>
	/*
	$('.show-more').click(function() {
		$(this).prev().css('overflow-y', 'scroll');
		$(this).prev().css('height', '400px');
		$(this).remove();
	});
	*/
	$(document).on('click', '#agree-all', function() {
		var checked = $(this).prop('checked');
		
		$('.agree-check-box').prop('checked', checked);
	});

	
	$(document).on('click', '.agree-check-box', function() {
		var allCheckBoxCnt = $('.agree-check-box').length;
		var checkedBoxCnt = $('.agree-check-box:checked').length;

		if(allCheckBoxCnt === checkedBoxCnt) {
			$('.agree-check-box-all').prop('checked', true);
		} else {
			$('.agree-check-box-all').prop('checked', false);
		}
	});
	
	$('#certify-btn').click(function() {
		if(!$('#agree-service').is(':checked')) {
			alert('<%=CommonUtil.getLangMsg(request, "msg_0437")%>');
		} else if(!$('#agree-private-info').is(':checked')) {
			alert('<%=CommonUtil.getLangMsg(request, "msg_0438")%>');
		} else {
			<%if(memJoinCertType.equals("N")){%>
			location.href = '/default/pop_certi_n';
			<%} else {%>
			location.href = '/default/joinInput_n2';
			<%}%>
		}
	});
</script>
</body>
</html>