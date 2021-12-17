<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.altong.web.logic.util.EncLibrary" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp"%>
<%@ page import="java.util.*"%>
<%
EncLibrary enc = new EncLibrary();
int recomCnt = Integer.parseInt(request.getAttribute("recomCnt").toString());
%>
<head>
<link rel="stylesheet" href="/pub/member/myQuestion/myQuestion.css?ver=1.1">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.4">
<script src="/pub/member/myRecommend/clipboard.min.js"></script><!--min.-->
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<div class="atm_base_wrapper1">
	<div class="center">
		<div class="atm_edittop_ttbar">
			<h1 class="atm_edittop_c1"><spring:message code="msg_0181"/></h1>
		</div>
		<div class="select_wrapper">
			<div class="select_div">
				<div class="atm_temp_tab">
					<p class="temp_tab_on">
						<spring:message code="msg_0789"/>
					</p>
				</div>
				<div class="atm_temp_tab" onclick="location.href='/member/myAnswerer';">
					<p>
						ANSWERer
					</p>
				</div>
			</div>
		</div>
		<div class="contentsWrapper">
			<div class="description-recommend">
				<div>
					<p><span><spring:message code="msg_0896"/></span></p>
					<p><spring:message code="msg_0897"/><br><spring:message code="msg_0898"/></p>
				</div>
				<p><spring:message code="msg_0899"/><br><spring:message code="msg_0900"/></p>
				<p><spring:message code="msg_0901"/></p>
			</div>
			<ul class="description-images-wrapper">
				<li>
				    <div>
				        <img src="/pub/member/myRecommend/images/send.svg">
				        <strong><spring:message code="msg_0902"/></strong>
				        <span><spring:message code="msg_0903"/></span>
				    </div>
				</li>
				<li>
					<div>
						<img src="/pub/member/myRecommend/images/friend_join.svg">
						<strong><spring:message code="msg_0904"/></strong>
						<span><spring:message code="msg_0905"/></span>
					</div>
				</li>
				<li>
					<div>
						<img src="/pub/member/myRecommend/images/pc_active.svg">
						<strong><spring:message code="msg_0906"/></strong>
						<span><spring:message code="msg_0907"/></span>
					</div>
				</li>
				<li class="recommend_arrow right">
					<div>
						<b></b><i></i><i></i><i></i>
					</div>
				</li>
				<li class="recommend_arrow bottom">
					<div>
						<b></b><i></i><i></i><i></i>
					</div>
				</li>
				<li class="recommend_arrow left">
					<div>
						<b></b><i></i><i></i><i></i>
					</div>
				</li>
			</ul>
			<div class="images-wrapper-line"></div>
			<div class="info-my-recommend">
				<p class="title"><spring:message code="msg_0908"/></p>
				<div class="atm_reco_wrapper2">
					<div class="atm_reco_wrapper_el">
						<div class="atm_reco_wrapper_el01">
							<div class="atm_reco_div">
								<div class="atm_reco_tt">
									<!-- [수정(2018.02.06): 김현구] -->
									<p class="atm_reco_c1">${memberNickName}<spring:message code="msg_0909"/></p>
								</div>
								<div class="atm_reco_con">
									<p class="atm_reco_c2 atm_superbold">
										${recommendCode}
									</p>
								</div>
							</div><!-- atm_reco_div end -->
							<div class="atm_reco_div">
								<div class="atm_reco_tt">
									<p class="atm_reco_c1" style="width:70%;"><span>${memberNickName}</span><spring:message code="msg_0910"/></p>
									
									<script>
										var clipboard = new Clipboard('.atm_reco_c3');
										if (navigator.userAgent.match(/ipad|ipod|iphone/i)) {
											$('.atm_reco_c3').on('click', function (e) {
												select_all_and_copy(document.getElementById("clipboardText"));
											});
										} else {
											clipboard.on('success', function (e) {
												console.info('Action:', e.action);
												console.info('Text:', e.text);
												console.info('Trigger:', e.trigger);
												alert('CommonUtil.getLangMsg(request, "msg_0911")');
												e.clearSelection();
											});
											clipboard.on('error', function (e) {
												console.error('Action:', e.action);
												console.error('Trigger:', e.trigger);
											});
										}
									</script>
								</div><!-- atm_reco_tt end -->
								<div class="atm_reco_con">
									<a href="/default/ioin/invite?Code=${recommendCode}" class="atm_reco_c4">
										http://www.altong.com/default/join/invite?Code=${recommendCode}
									</a>
									<button class="atm_reco_c3" id="clipboardText" data-clipboard-text="http://www.altong.com/default/join/invite?Code=${recommendCode}"
												 onFocus="this.blur()"><img src="/pub/member/myRecommend/images/link.svg"><spring:message code="msg_0290"/></button>
								</div>
							</div><!-- atm_reco_div end -->
						</div>
						
						<%-- [수정(2018.02.06): 김현구] 관리자가 아닌 경우 CHECK --%>
						<c:if test="${userLv != 99 or memberSeq == 0}">
							<div class="atm_reco_wrapper_el02">
								<div class="atm_reco_div">
									<div class="atm_reco_tt">
										<p class="">
											<span>${memberNickName}</span>
											 <spring:message code="msg_0913"/>
										</p>
									</div><!-- atm_reco_tt end -->
								</div><!-- atm_reco_div end -->
								
								<div class="atm_reco_con">
									<c:choose>
										<c:when test="${recomNick != null}">
											<p class="recommend_no">${fn:substring(userDateReg,0,10)}&nbsp;
											<c:if test="${fn:substring(userDateReg,11,13) == '오전'}">
											AM
											</c:if>
											<c:if test="${fn:substring(userDateReg,11,13) == '오후'}">
											PM
											</c:if>
											&nbsp;${fn:substring(userDateReg,13,fn:length(userDateReg))}&nbsp;${recomNick}<spring:message code="msg_0346"/></p>
										</c:when>
										<c:when test="${recomNick == null}">
											<p class="recommend_no">${memberNickName}<spring:message code="msg_0914"/></p>
										</c:when>
									</c:choose>
								</div><!-- atm_reco_con end -->
							</div>
						</c:if>
						<div class="atm_reco_wrapper_el03">
							<div class="atm_reco_div_L">
								<div class="atm_reco_tt">
									<p class="atm_reco_c1"><span>${memberNickName}</span><spring:message code="msg_0915"/> (${recomCnt}<spring:message code="msg_0803"/>)</p>
								</div><!-- atm_reco_tt end -->
							</div><!-- atm_reco_div_L end -->
						</div>
						<div class="atm_recotable_div">
							<table class="atm_recotable0">
								<tr>
									<th class="atm_recotable1"><spring:message code="msg_0310"/> & <spring:message code="msg_0916"/></th>
									<th class="atm_recotable1"><spring:message code="msg_0365"/></th>
									<th class="atm_recotable1"><spring:message code="msg_0366"/></th>
									<th class="atm_recotable1"><spring:message code="msg_0250"/></th>
									<th class="atm_recotable1"><spring:message code="msg_0251"/></th>
								</tr>
								
								<%
									if (recomCnt != 0) {
								%>
								
								<c:forEach var="mem" items="${member}" varStatus="status">
									<c:set var="MemSeq" value="${mem.seq}"/>
									<c:set var="MemNickName" value="${mem.nickName}"/>
									<c:set var="MemDateReg" value="${mem.dateReg}"/>
									<c:set var="MemQuestionCount" value="${mem.q_Count}"/>
									<c:set var="MemQuestionMoney" value="${mem.q_Almoney}"/>
									<c:set var="MemAnswerCount" value="${mem.a_Count}"/>
									<c:set var="MemAnswerMoney" value="${mem.a_Almoney}"/>
									<c:set var="MemberName" value="${mem.sName}"/>
									<c:set var="MemberPhone" value = "${mem.phone}" />
									
									<c:set var="legthIf" value="0"/>
									<c:if test="${fn:length(MemberName) > 2}">
									<c:set var="legthIf" value="1"/>
									</c:if>
									<fmt:parseNumber var="NameLen" value="${fn:length(MemberName) / 2.0 + legthIf}"/>
									<c:set var="blindStr" value="*"/>
									<c:forEach var="loopT" begin="1" end="${fn:length(MemberName) - NameLen}">
										<c:set var="blindStr" value="${blindStr}*"/>
									</c:forEach>
									<c:set var="MemberName" value="${fn:substring(MemberName, 0, NameLen)}${blindStr}"/>
									
									
									<cif test="${MemQuestionMoney == ''}">
										<c:set var="MemQuestionMoney" value="0"/>
									</cif>
									<cif test="${MemAnswerMoney == ''}">
										<c:set var="MemAnswerMoney" value="0"/>
									</cif>
									<cif test="${MemQuestionMoney != '0'}">
										<fmt:formatNumber var="MemAnswerMoney" value="${MemAnswerMoney}" pattern="#,###" />
									</cif>
									<cif test="${MemAnswerMoney != '0'}">
										<fmt:formatNumber var="MemAnswerMoney" value="${MemAnswerMoney}" pattern="#,###" />
									</cif>
									<%
			                          	String phone = "";
			                          	if(pageContext.getAttribute("MemberPhone") != null) {
			                          		phone = enc.AlmoneyDecrypt( String.valueOf(pageContext.getAttribute("MemberPhone")) );
			                          		//phone = "**" + phone.substring(phone.length() - 2, phone.length());
			                          		
			                          		if(phone.length() >= 10) {
			                          			phone = phone.substring(0, 4) + "*" + phone.substring(6, 7) + "*" + phone.substring(8, 9) + "*";
			                          		}
			                          		if(phone.length() == 5) {
			                          			phone = phone.substring(0, 2) + "*" + phone.substring(3, 4) + "*";
			                          		}
			                          	}
									%>
									
									<tr class="atm_recotable<c:if test="${status.index % 2 == 1}">_gray</c:if>">
										<td class="atm_recotable2">
											<a href="/member/otherProfileInfo.asp?MemberSeq=${MemSeq}" target="_blank">
											${MemNickName}<br>
											(<%=phone%>)<br>
											</a>
											${fn:substring(MemDateReg,0,10)}&nbsp
											<c:if test="${fn:substring(MemDateReg,11,13) == '오전'}">
											AM
											</c:if>
											<c:if test="${fn:substring(MemDateReg,11,13) == '오후'}">
											PM
											</c:if>
											&nbsp${fn:substring(MemDateReg,14,fn:length(MemDateReg))}
										</td>
										<td>${MemQuestionCount}<spring:message code="msg_0370"/></td>
										<td>${MemAnswerCount}<spring:message code="msg_0370"/></td>
										<td>${MemQuestionMoney}<spring:message code="msg_0136"/></td>
										<td>${MemAnswerMoney}<spring:message code="msg_0136"/></td>
									</tr>
								</c:forEach>
								<%
									}else{
								%>
								<tr>
									<td colspan="5">${memberNickName}<spring:message code="msg_0917"/></td>
								</tr>								
								<%	
									}
								%>
							</table>	
						</div><!-- atm_recotable_div end -->
					</div><!-- atm_reco_wrapper_el end -->
				</div><!-- atm_reco_wrapper2 end -->
			</div>
		</div><!-- contentsWrapper end -->
		<!--wrapper end -->	
	</div>
</div>
<script>
function atmRecommandToggle() {
	var target = $('.atm_ui_recommand');
	if ($(target).hasClass('atm_reco_up') == true) {
		$(target).parent().parent().parent().parent().parent().parent().find('.atm_recotable_div').css('display', 'none');
		$(target).addClass('atm_reco_down').removeClass('atm_reco_up');
		$(target).attr('src', '/Common/images/btn_direct_down2.png');
	} else {
		$(target).parent().parent().parent().parent().parent().parent().find('.atm_recotable_div').css('display', 'block');
		$(target).addClass('atm_reco_up').removeClass('atm_reco_down');
		$(target).attr('src', '/Common/images/btn_direct_up2.png');
	}
}
</script>
<div id="top_btn">
    <a href="javascript:void(0);">
        <span>
            <img src="/Common/images/top_btn_arrow.svg" alt="top_btn">
        </span>
    </a>
</div>
</body>