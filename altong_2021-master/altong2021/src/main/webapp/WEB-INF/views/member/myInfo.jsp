<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@ page import="com.altong.web.logic.member.MemberVO" %>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ page import="org.json.simple.parser.JSONParser" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="com.google.gson.Gson" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %> 
<head>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="/Common/crop/cropper.css">
	<link rel="stylesheet" href="/pub/member/myInfo/myInfo.css?ver=2.0">
	<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.9">
	<script src="/Common/crop/cropper.js"></script>
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<div id="myinfo_wrapper" class="site">
	<div class="center">
		<form name="frm_sch" method="post">
			<input style="display: none;" type="file" id="btn-open-dialog" name="photo" accept="image/*">
		</form>
		<section class="top">
			<div class="wrapper">
				<div class="profile">
					<div class="photo">
						<label class="label" data-toggle="tooltip" title='<spring:message code="msg_0153"/>'>
							<c:choose>
								<c:when test="${photo != ''}">
									<img id="avatar" src="/UploadFile/Profile/${photo}" onerror="this.src='/pub/css/profile/img_thum_base0.jpg'" >
								</c:when>
								<c:otherwise>
									<img id="avatar" src="/pub/css/profile/img_thum_base0.jpg">
								</c:otherwise>
							</c:choose>
							<input type="file" class="sr-only" id="input" name="image" accept="image/*">
						</label>
						<div class="aprogress">
							<div class="aprogress-bar progress-bar-striped progress-bar-animated" role="aprogressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100">0%</div>
						</div>
						<div class="alert" role="alert"></div>
						
					</div>
					<div class="info">
						<p class="info_edit" onclick="location.href='/member/myJoin'"><img src="${libIMG_URL}/Common/images/myadmin_edit_icon.png" alt='<spring:message code="msg_0819"/>'></p>
						<div class="info_default">
							<p class="info_name">
								<strong>${userNickName}</strong>
								<span class="lv">${levelStr}</span>
							</p>
							<p><spring:message code="msg_0163"/> <spring:message code="msg_0164"/><fmt:formatNumber value="${memberLogAlmoneyTotal.rankQ}" pattern="#,###" /><spring:message code="msg_0165"/> | <spring:message code="msg_0166"/> <spring:message code="msg_0164"/><fmt:formatNumber value="${memberLogAlmoneyTotal.rankA}" pattern="#,###" /><spring:message code="msg_0165"/></p>
						</div>
						<div class="info_money">
							<h3 class="sum_money" onclick="$('#infoMoneyMore').toggleClass('show')"><spring:message code="msg_0652"/><span> <fmt:formatNumber value="${userAlmoney}" pattern="#,###.#" /></span><spring:message code="msg_0136"/>
							</h3>
							<div onclick="$(this).removeClass('show')" class="more_money_wrap" id="infoMoneyMore">
								<h3 class="sum_money"><spring:message code="msg_0652"/><span> <fmt:formatNumber value="${userAlmoney}" pattern="#,###.0" /></span><spring:message code="msg_0136"/></h3>
								<div class="more_money_table">
									<p class="more_money">
										<span><spring:message code="msg_0805"/></span>
										<span>+ <fmt:formatNumber value="${memberLogAlmoney.imports}" pattern="#,###.0" /><spring:message code="msg_0136"/></span>
									</p>
									<p class="more_money">
										<span><spring:message code="msg_0806"/></span>
										<span>- <fmt:formatNumber value="${memberLogAlmoney.expense}" pattern="#,###.0" /><spring:message code="msg_0136"/></span>
									</p>																	
								</div>
							</div>
						</div>
					</div>
					<div class="menu">
						<p id="tab_3" class="bottom_on" onclick="location.href='/member/myInfo'; return false;" data-type="default" class="on"><spring:message code="msg_0820"/></p>
						<p id="tab_1" onclick="location.href='/member/myInfoLv'; return false;" data-type="lvup" ><spring:message code="msg_0821"/></p>
						<c:choose>
							<c:when test="${userLv < 2}">
							<p id="tab_2" onclick="alert(getLangStr("jsm_0081")); return false;" data-type="exchange"><spring:message code="msg_0822"/></p>
							</c:when>
							<c:otherwise>
							<p id="tab_2" onclick="location.href='/member/myInfoExch'; return false;" data-type="exchange"><spring:message code="msg_0822"/></p>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
		<!-- 모달 -->
				<div class="modal fade" id="modal1" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="modalLabel"><spring:message code="msg_0157"/></h5>
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">
								<div class="img-container" style="max-height:250px!important;">
									<img id="image" src="" class="cropper-hidden">
								</div>
								<div class="btn-group">
									<button type="button" class="btn btn-primary" data-method="rotate" data-option="-45" title="Rotate Left" id="rrotateImg">
										<span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="cropper.rotate(-45)">
											<span class="fa fa-rotate-left"><spring:message code="msg_0158"/></span>
										</span>
									</button>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="msg_0159"/></button>
								<button type="button" class="btn btn-primary" id="crop"><spring:message code="msg_0160"/></button>
							</div>
						</div>
					</div>
				</div>
			<!-- // 모달 -->
				<%
				int tabs = Integer.parseInt( String.valueOf( request.getAttribute("tab") ) );
				String mbMap = String.valueOf( request.getAttribute("mbMap") );
				
				JSONObject global_my = (JSONObject)CommonUtil.getGlobal(request, response);

				Map<String, Object> dataHash1 = new Gson().fromJson(mbMap, Map.class);
				int countQ = (int)Math.floor( Float.valueOf(String.valueOf( dataHash1.get("CountQ") ) ) );
				BigDecimal sumQ = new BigDecimal( String.valueOf( dataHash1.get("SumQ") ) );
				int countNotAnswerQ = (int)Math.floor( Float.valueOf(String.valueOf( dataHash1.get("CountNotAnswerQ") ) ) );
				int countRecivedAnswer = (int)Math.floor( Float.valueOf(String.valueOf( dataHash1.get("CountRecivedAnswer") ) ) );
				int countC = (int)Math.floor( Float.valueOf(String.valueOf( dataHash1.get("CountC") ) ) );
				int countNotChoiceQ = (int)Math.floor( Float.valueOf(String.valueOf( dataHash1.get("CountNotChoiceQ") ) ) );
				int countA = (int)Math.floor( Float.valueOf(String.valueOf( dataHash1.get("CountA") ) ) );
				BigDecimal sumA = new BigDecimal( String.valueOf( dataHash1.get("SumA") ) );
				int countChoicedA = (int)Math.floor( Float.valueOf(String.valueOf( dataHash1.get("CountChoicedA") ) ) );
				int countZzim = (int)Math.floor( Float.valueOf(String.valueOf( dataHash1.get("CountZzim") ) ) );
				int countV = (int)Math.floor( Float.valueOf(String.valueOf( dataHash1.get("CountV") ) ) );


				//나의 댓글 누락
				int qReplyCount = (int)Math.floor( Float.valueOf(String.valueOf( dataHash1.get("QReplyCount") ) ) );
				int aReplyCount = (int)Math.floor( Float.valueOf(String.valueOf( dataHash1.get("AReplyCount") ) ) );
				int countR = qReplyCount + aReplyCount;
				//받은 댓글 누락
				int qRecivedReplyCount = (int)Math.floor( Float.valueOf(String.valueOf( dataHash1.get("QRecivedReplyCount") ) ) );
				int aRecivedReplyCount = (int)Math.floor( Float.valueOf(String.valueOf( dataHash1.get("ARecivedReplyCount") ) ) );
				int countRecivedR = qRecivedReplyCount + aRecivedReplyCount;

				pageContext.setAttribute("countQ", countQ);
				pageContext.setAttribute("sumQ", sumQ);
				pageContext.setAttribute("countNotAnswerQ", countNotAnswerQ);
				pageContext.setAttribute("countRecivedAnswer", countRecivedAnswer);
				pageContext.setAttribute("countC", countC);
				pageContext.setAttribute("countNotChoiceQ", countNotChoiceQ);
				pageContext.setAttribute("countA", countA);
				pageContext.setAttribute("sumA", sumA);
				pageContext.setAttribute("countChoicedA", countChoicedA);
				pageContext.setAttribute("countZzim", countZzim);
				pageContext.setAttribute("countV", countV);
				pageContext.setAttribute("countR", countR);
				pageContext.setAttribute("countRecivedR", countRecivedR);

				String userNickName = String.valueOf( global_my.get("UserNickName") );
				%>				
			</div>
		</section>
		
		<div class="content_wrap" id="menuContentWrap">
			<section class="default_setting">
				<div class="atm_mf_pcwidth">
					<div class="atm_mf_con_tt0" style="cursor:pointer;">
						<p class="atm_mf_c0">
							<spring:message code="msg_0365"/>
							- <span class="atm_mf_color_gre"><fmt:formatNumber value="${countQ > 0 ? countQ : 0}" pattern="#,###" /></span><spring:message code="msg_0370"/> 
							- <span class="atm_mf_color_gre"><fmt:formatNumber value="${sumQ}" pattern="#,###.0" /></span><spring:message code="msg_0136"/>
						</p>
						<strong>
							<i></i>
							<i></i>
						</strong>
					</div>
					<div class="atm_mf_con_slide">
						<div class="atm_mf_con_tt1" onClick="location.href='/member/myQuestion';">
							<p class="atm_mf_c1">
								<spring:message code="msg_0365"/>
							- <span class="atm_mf_color_gre"><fmt:formatNumber value="${countQ > 0 ? countQ : 0}" pattern="#,###" /></span><spring:message code="msg_0370"/> 
							</p>
						</div>
						<div class="atm_mf_con_tt1" onClick="location.href='/member/myQuestion?Flag=answered'">
							<p class="atm_mf_c1">
								<spring:message code="msg_0823"/> - <span class="atm_mf_color_gre"><fmt:formatNumber value="${(countQ > 0 ? countQ : 0) - countNotAnswerQ}" pattern="#,###" /></span><spring:message code="msg_0370"/>
							</p>                
						</div>
						<div class="atm_mf_con_tt1" onClick="location.href='/member/myQuestion?Flag=notAnswered'">
							<p class="atm_mf_c1">
								<spring:message code="msg_0824"/> - <span class="atm_mf_color_gre"><fmt:formatNumber value="${countNotAnswerQ}" pattern="#,###" /></span><spring:message code="msg_0370"/>
							</p>
						</div>
						<div class="atm_mf_con_tt1" onClick="location.href='myRecivedAnswer'">
							<p class="atm_mf_c1">
								<spring:message code="msg_0825"/> - <span class="atm_mf_color_gre"><fmt:formatNumber value="${countRecivedAnswer}" pattern="#,###" /></span><spring:message code="msg_0370"/>
							</p>
						</div>
						<div class="atm_mf_con_tt1" onClick="location.href='myRecivedAnswer?Flag=choice'">
							<p class="atm_mf_c1">
								<spring:message code="msg_0826"/> - <span class="atm_mf_color_gre"><fmt:formatNumber value="${countC}" pattern="#,###" /></span><spring:message code="msg_0370"/>
							</p>
						</div>
						<div class="atm_mf_con_tt1" onClick="location.href='/member/myQuestion?Flag=N'">
							<p class="atm_mf_c1">
								<spring:message code="msg_0827"/> - <span class="atm_mf_color_gre"><fmt:formatNumber value="${countNotChoiceQ}" pattern="#,###" /></span><spring:message code="msg_0370"/>
							</p>
						</div>
						<div class="atm_mf_con_tt1">
							<p class="atm_mf_c1">
								<spring:message code="msg_0828"/> - <span class="atm_mf_color_gre"><fmt:formatNumber value="${countQ > 0 ? countC / countQ * 100 : 0}" pattern="#,###.0" /></span>%
							</p>
						</div>
					</div>
				</div>
				<div style="height:12px;"></div>
				<div class="atm_mf_pcwidth">
					<div class="atm_mf_con_tt0" style="cursor:pointer;">
						<p class="atm_mf_c0">
							<spring:message code="msg_0366"/> 
							- <span class="atm_mf_color_gre"><fmt:formatNumber value="${countA}" pattern="#,###" /></span><spring:message code="msg_0370"/> 
							- <span class="atm_mf_color_gre"><fmt:formatNumber value="${sumA}" pattern="#,###.0" /></span><spring:message code="msg_0136"/>
						</p>
						<strong>
							<i></i>
							<i></i>
						</strong>
					</div>
					<div class="atm_mf_con_slide">
						<div class="atm_mf_con_tt1" onClick="location.href='/member/myAnswer';">
							<p class="atm_mf_c1">
								<spring:message code="msg_0366"/> 
							- <span class="atm_mf_color_gre"><fmt:formatNumber value="${countA}" pattern="#,###" /></span><spring:message code="msg_0370"/> 
							</p>
						</div>
						<div class="atm_mf_con_tt1" onClick="location.href='/member/myAnswer?FlagChoice=Y'">
							<p class="atm_mf_c1">
								<spring:message code="msg_0829"/> 
								- <span class="atm_mf_color_gre"><fmt:formatNumber value="${countChoicedA}" pattern="#,###" /></span><spring:message code="msg_0370"/>
							</p>
						</div>
						<div class="atm_mf_con_tt1" onClick="location.href='/member/myAnswer?FlagChoice=Y&notW=N'">
							<p class="atm_mf_c1">
								<spring:message code="msg_0830"/> 
								- <span class="atm_mf_color_gre"><fmt:formatNumber value="${countA-countChoicedA}" pattern="#,###" /></span><spring:message code="msg_0370"/>
							</p>
						</div>
						<div class="atm_mf_con_tt1">
							<p class="atm_mf_c1">
								<spring:message code="msg_0831"/> 
								- <span class="atm_mf_color_gre"><fmt:formatNumber value="${countA > 0 ? countChoicedA / countA * 100 : 0}" pattern="#,###.0" /></span>%
							</p>
						</div>
					</div>
				</div>
				<div style="height:12px;" id="white_space1"></div>
				<div class="atm_mf_pcwidth">
					<div class="atm_mf_con_tt0">
						<p class="atm_mf_c0"><spring:message code="msg_0021"/></p>
						<strong>
							<i></i>
							<i></i>
						</strong>
					</div>
					<div class="atm_mf_con_slide"> 
						<div class="atm_mf_con_tt1" onClick="location.href='/member/myZzim';">
							<p class="atm_mf_c1"><spring:message code="msg_0832"/> - <span class="atm_mf_color_gre"><fmt:formatNumber value="${countZzim}" pattern="#,###" /></span><spring:message code="msg_0370"/></p>
						</div>
						<div class="atm_mf_con_tt1" onClick="location.href='/member/myView';">
							<p class="atm_mf_c1"><spring:message code="msg_0833"/> - <span class="atm_mf_color_gre"><fmt:formatNumber value="${countV}" pattern="#,###" /></span><spring:message code="msg_0814"/></p>
						</div>
						<div class="atm_mf_con_tt1" onClick="location.href='/member/myReply';">
							<p class="atm_mf_c1"><spring:message code="msg_0834"/> - <span class="atm_mf_color_gre"><fmt:formatNumber value="${queCnt2}" pattern="#,###" /></span><spring:message code="msg_0370"/></p>
						</div>
						<div class="atm_mf_con_tt1" onClick="location.href='/member/myReply?FlagMe=Y&QA_Cls=A';">
							<p class="atm_mf_c1"><spring:message code="msg_0835"/> - <span class="atm_mf_color_gre"><fmt:formatNumber value="${queCnt1}" pattern="#,###" /></span><spring:message code="msg_0370"/></p>
						</div>
						<%
							String alt = "-야로-슈크-디디바오-관리자-누리-목련-영웅본색-나래-알폰스-알스-구름-낙타커핑-";
							if (alt.contains("-"+userNickName+"-") == true) {
						%>
						<div class="atm_mf_con_tt1" onClick="location.href='/member/mySiren';" style="display:none">
							<p class="atm_mf_c1"><spring:message code="msg_0836"/></p>
						</div>
						<% } %>
					</div>
				</div>
				
			</section>
		</div>
	<!--wrapper end -->
	</div><!-- myinfo_wrapper end -->
</div>
<script src="/Common/js/bootstrap.min.js"></script>
<script src="/pub/member/myInfo/myInfo.js?ver=1.3"></script>
<script>
$(document).ready(function() {
	console.log("image : ${photo}");
});
</script>
</body>