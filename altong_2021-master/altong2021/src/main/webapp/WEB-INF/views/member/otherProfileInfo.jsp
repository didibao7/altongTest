<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ page import="org.json.simple.JSONObject"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<body>

<%@ include file="/pub/menu/topMenu.jsp"%>
<%

JSONObject global = (JSONObject) CommonUtil.getGlobal(request, response);
String UserSeq = "0";
if (global != null) {
	UserSeq = global.get("UserSeq").toString();
}
pageContext.setAttribute("UserSeq", UserSeq);

%>
	
	<link rel="stylesheet" href="/pub/member/otherProfileInfo/otherProfileInfo.css?ver=1.3">
	<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.1">
	<script>
	function msg_fAjax(url, frm_2, param) {
		if (document.xhr3) {
			$('#Tip').text(getLangStr("jsm_0031")).css('display', 'block');
			setTimeout(function () { $('#Tip').stop().fadeOut(300) }, 3000);
			return;
		}
		document.xhr3 = $.ajax({
			url: url,
			type: 'post',
			data: (frm_2 ? $('form[name=' + frm_2 + ']').serialize() + '&' : '') + param,
			dataType: 'json',
			success: function (r) {
				switch (r.result) {
					case 'SEND':
						alert(getLangStr("jsm_0042"));
						$('.message_cancle').click();
						break;
					default:
						if (r.result) alert(r.result);
						break;
				}
			},
			error: function (r, textStatus, err) {
				if (r.responseText && r.responseText.substr(0, 13) == '<!--LOGOFF-->') { top.location = '/'; return; }
				console.log(r);
			},
			complete: function () {
				document.xhr3 = false;
			}
		});
	}
	// 2020 .01 .30 오명훈 디바이스 체크 함수 추가
	function checkDevice(){
		// 접속한 디바이스가 모바일이면 True, 아니면 False를 반환합니다.
		const mobileKeyWords = new Array('Android', 'iPhone', 'iPod', 'MOT', 'LG', 'SAMSUNG', 'BlackBerry', 'Window CE', 'SonyEricsson');
		for(let info in mobileKeyWords){
			if(navigator.userAgent.match(mobileKeyWords[info]) != null){
				return true;
			}
		}
		return false;
	}
	
	function checkDeviceSize()
	{
		let msgDeviceWidth = 0;
		const mobileDevice = checkDevice();
		if(mobileDevice){
			// 모바일로 접속햇을 때 메시지 창의 크기를 지정해줍니다.
			msgDeviceWidth = 100 + "%";
		}else {
			// 모바일 환경이 아닌 상태에서 메시지 창의 크기를 지정해줍니다.
			msgDeviceWidth = 40 + "%";
		}
	}
	
	$(document).ready(function(){
		$("#openMessage").click(function(event){
			$(".message_mask").css("display","block");
			$(".user_message").css("display","block");
	
			$(".message_usertext").val('');
	
			$("body").css("position", "fixed");
			$('.more_btn_list').stop().fadeIn();

			checkDeviceSize();
		});
	
		$(".message_mask, .message_cancle").click(function(event){
			$(".message_mask").css("display","none");
			$(".more_btn_list").css("display","none");
	
			$("body").css("position", "");
		});
		
		$(window).resize(checkDeviceSize);
	});
	</script>
	
	
	<c:set var="mlv" value="${mlv}" />
	<c:set var="memSeq" value="${memSeq}" />
	<%
		String mLv="";
		if(pageContext.getAttribute("mlv")==null || pageContext.getAttribute("mlv")==""){
			
		}else{
			mLv = String.valueOf(pageContext.getAttribute("mlv"));
		}
		
		String memberSeq = String.valueOf(pageContext.getAttribute("memSeq"));
		
		String lv ="";
		
		if(mLv==""||mLv==null || mLv.isEmpty()){
			
		}else{
			lv = CommonUtil.getLevelName(Integer.parseInt(mLv), request);
		}
	
		pageContext.setAttribute("memberSeq", memberSeq);
		pageContext.setAttribute("userLv", lv);
	%>
	<div class="site">
		<div id="otherInfo_wrapper"  class="site-content">
			<div class="center">
				<div class="other_infomain">
					<figure>
						<img src="${photoURL}" alt='<spring:message code="msg_0162"/>' onerror="this.src='/Common/images/img_thum_base0.jpg'" >
					</figure>
					<ul>
						<li><span>${userLv}</span><p class="join_date"><spring:message code="msg_0920"/> ${fn:substring(memInfo.dateReg, 0, 10) }</p></li>
						<li>${memInfo.nickName}</li>
						<li>
							<p class="que_rank"><spring:message code="msg_0365"/> <spring:message code="msg_0164"/><b><fmt:formatNumber value="${memInfo.rankQ}" type="number"/></b><spring:message code="msg_0165"/></p>
							<p class="ans_rank"><spring:message code="msg_0366"/> <spring:message code="msg_0164"/><b><fmt:formatNumber value="${memInfo.rankA}" type="number"/></b><spring:message code="msg_0165"/></p>
						</li>
					</ul>
					<c:choose>
						<c:when test="${memberSeq != UserSeq}">
							<div class="other_info_edit">
								<img title='<spring:message code="msg_0246"/>' src="/pub/css/profile/addFriends.svg" onClick="javascript:goSubmit('frm_2', '/member/partnerSave?PartnerSeq=<%=memberSeq%>&FlagPartner=F', 'profile_ifrm');" alt='<spring:message code="msg_0246"/>'>
								<img title='<spring:message code="msg_0247"/>' src="/pub/css/profile/addMento.svg" onClick="javascript:goSubmit('frm_2', '/member/partnerSave?PartnerSeq=<%=memberSeq%>&FlagPartner=M', 'profile_ifrm');" alt='<spring:message code="msg_0247"/>'>
								<img title='<spring:message code="msg_0248"/>' src="/pub/css/profile/message.svg" id="openMessage" alt='<spring:message code="msg_0248"/>'>
							</div>
						</c:when>
					</c:choose>
				</div>
				<h2 class="other_nick">${memInfo.nickName}<spring:message code="msg_0921"/></h2>
				<div class="other_active">
					<ol>
						<li><div><spring:message code="msg_0805"/></div><p><fmt:formatNumber value="${memInfo.earnTotal}" type="number"/><spring:message code="msg_0136"/></p></li>
						<li><div><spring:message code="msg_0365"/></div><p onclick="location.href='/member/myQuestion?userWho=<%=memberSeq%>'"><span><fmt:formatNumber value="${memInfo.countQ}" type="number"/></span><spring:message code="msg_0370"/> - <span><fmt:formatNumber value="${memInfo.sumQ}" type="number"/></span><spring:message code="msg_0136"/></p></li>
						<li><div><spring:message code="msg_0366"/></div><p onclick="location.href='/member/myAnswer?userWho=<%=memberSeq%>'"><span><fmt:formatNumber value="${memInfo.countA}" type="number"/></span><spring:message code="msg_0370"/> - <span><fmt:formatNumber value="${memInfo.sumA}" type="number"/></span><spring:message code="msg_0136"/></p></li>
						<li><div><spring:message code="msg_0833"/></div><p><fmt:formatNumber value="${memInfo.countV}" type="number"/><spring:message code="msg_0370"/></p></li>
						<li><div><spring:message code="msg_0261"/></div><p onclick="location.href='/member/myReply?userWho=<%=memberSeq%>'"><span><fmt:formatNumber value="${replyCount}" type="number"/></span><spring:message code="msg_0370"/></p></li>
						<li><div><spring:message code="msg_0252"/></div><p>${tagCountC}<spring:message code="msg_0814"/> (<spring:message code="msg_0260"/> <fmt:formatNumber value="${memInfo.countQ > 0 ? tagCountC / memInfo.countQ * 100  : 0}" pattern="#,###.0" />%)</p></li>
						<li><div><spring:message code="msg_0676"/></div><p>${tagCountA}<spring:message code="msg_0370"/> (<spring:message code="msg_0272"/> <fmt:formatNumber value="${memInfo.countA > 0 ? tagCountA / memInfo.countA * 100 : 0}" pattern="#,###.0" />%)</p></li>
						<li><div><spring:message code="msg_0522"/></div><p><fmt:formatNumber value="${memInfo.RMoney}" type="number"/><spring:message code="msg_0136"/></p></li>
					</ol>
				</div>
			</div>
		</div>
	</div>
	
	<div class="more_btn_list" style="display: none;">
		<div class="user_message">
			<h5 class="message_user"><span class="msg_name">${memInfo.nickName}</span><spring:message code="msg_0309"/></h5>
			<form name="frm_msgSend">
				<input type="hidden" name="MemberSeq" value="<%=memberSeq%>">
				<textarea name="Contents" class="message_usertext"></textarea>
			</form>
			<p class="message_btns"><span class="message_cancle"><spring:message code="msg_0159"/></span><span class="message_send" onClick="msg_fAjax('/message/messageAjax', 'frm_msgSend', 'ACT=SEND')"><spring:message code="msg_0311"/></span></p>
		</div>
	</div>
	<form name="frm_2" method="post"></form>
	
	<iframe name="profile_ifrm" width="100%" height="0" frameborder="0"></iframe>
	
	<script type="text/javascript" src="/Common/src/member/otherProfileInfo/otherProfileInfo.js?1.0" ></script>
	
</body>