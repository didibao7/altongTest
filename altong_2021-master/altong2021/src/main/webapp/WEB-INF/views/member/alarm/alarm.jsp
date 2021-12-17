<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jasp.util.*" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<body>
<link rel="stylesheet" href="/pub/member/alarm/alarm/alarm.css?ver=1.4">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.2">
<%@ include file="/pub/menu/topMenu.jsp"%>
<style>
.section-title{
	display:none;
}
</style>
<div id="alarm_wrapper" class="site">
	<div class="center site-content">
		<div id="atm_wrapper">
			<h1 class="atm-title-bar"><spring:message code="msg_0674"/><a href="javascript:void(0);" onclick="location.href='/member/alarm/alarmConfig?CallAlarmFlag=Y'"><img src="/Common/images/atm_add_edit2.png" alt='<spring:message code="msg_0675"/>'></a></h1>
			<div class="atm_base_wrapper10">
				<div class="content-wrapper">
					<% if(request.getAttribute("CODE_MEM_ALARM_VIEW_FIELD_CD_ANS_CHOICE").equals(true)) { %>
					<div class="alarm-section">
						<p class="section-title"><spring:message code="msg_0676"/></p>
						<div class="alarm-content" onclick="location.href='/member/myAnswer?FlagChoice=Y&CallAlarmFlag=Y'">
							<img src="/Common/images/alarm/icon_ans_choice.png">
							<div>
								<p class="alarm-text"><spring:message code="msg_0680"/><span class="alarm-text-count"><%=request.getAttribute("ANS_CHOICE")%></span><spring:message code="msg_0681"/></p>
								<p class="alarm-text-date"><%=request.getAttribute("ANS_CHOICE_DATE_REG") == null ? "" : request.getAttribute("ANS_CHOICE_DATE_REG")%></p>
							</div>
						</div>
					</div>
					<% } %>
					<% if(request.getAttribute("CODE_MEM_ALARM_VIEW_FIELD_CD_ANS_REGIST").equals(true)) { %>
					<div class="alarm-section">
						<p class="section-title"><spring:message code="msg_0677"/></p>
						<div class="alarm-content" onclick="location.href='/member/myRecivedAnswer?CallAlarmFlag=Y'">
							<img src="/Common/images/alarm/icon_ans_regist.png">
							<div>
								<p class="alarm-text"><spring:message code="msg_0682"/><span class="alarm-text-count"><%=request.getAttribute("ANS_REGIST")%></span><spring:message code="msg_0683"/></p>
								<p class="alarm-text-date"><%=request.getAttribute("ANS_REGIST_DATE_REG") == null ? "" : request.getAttribute("ANS_REGIST_DATE_REG")%></p>
							</div>
						</div>
					</div>
					<% } %>
					<% if(request.getAttribute("CODE_MEM_ALARM_VIEW_FIELD_CD_FAVORITE_QUE_REGIST").equals(true)) { %>
					<div class="alarm-section">
						<p class="section-title"><spring:message code="msg_0678"/></p>
						<div class="alarm-content" onclick="location.href='/answer/favoriteList?CallAlarmFlag=Y'">
							<img src="/Common/images/alarm/icon_favorite_que_regist.png">
							<div>
								<p class="alarm-text"><spring:message code="msg_0684"/><span class="alarm-text-count"><%=request.getAttribute("FAVORITE_QUE_REGIST")%></span><spring:message code="msg_0685"/></p>
								<p class="alarm-text-date"><%=request.getAttribute("FAVORITE_QUE_REGIST_DATE_REG") == null ? "" : request.getAttribute("FAVORITE_QUE_REGIST_DATE_REG")%></p>
							</div>
						</div>
					</div>
					<% } %>
					<% if(request.getAttribute("CODE_MEM_ALARM_VIEW_FIELD_CD_CMT_REGIST").equals(true)) { %>
					<div class="alarm-section">
						<p class="section-title"><spring:message code="msg_0679"/></p>
						<div class="alarm-content" onclick="location.href='/member/myReply?FlagMe=Y&QA_Cls=A&CallAlarmFlag=Y'">
							<img src="/Common/images/alarm/icon_cmt_regist.png">
							<div>
								<p class="alarm-text"><spring:message code="msg_0686"/><span class="alarm-text-count"><%=request.getAttribute("CMT_REGIST")%></span><spring:message code="msg_0687"/></p>
								<p class="alarm-text-date"><%=request.getAttribute("CMT_REGIST_DATE_REG") == null ? "" : request.getAttribute("CMT_REGIST_DATE_REG")%></p>
							</div>
						</div>
					</div>
					<% } %>
					<% if(request.getAttribute("CODE_MEM_ALARM_VIEW_FIELD_CD_ALARM").equals(true)) { %>
					<div class="alarm-section">
						<p class="section-title"><spring:message code="msg_0688"/></p>
						<div class="alarm-content" onclick="location.href='/message/message?CallAlarmFlag=Y'">
							<img src="/Common/images/alarm/icon_message.png">
							<div>
								<p class="alarm-text"><spring:message code="msg_0689"/><span class="alarm-text-count"><%=request.getAttribute("ALARM")%></span><spring:message code="msg_0690"/></p>
								<p class="alarm-text-date"><%=request.getAttribute("ALARM_DATE_REG") == null ? "" : request.getAttribute("ALARM_DATE_REG")%></p>
							</div>
						</div>
					</div>
					<% } %>
					<% if(request.getAttribute("CODE_MEM_ALARM_VIEW_FIELD_CD_ANS_CHOICE_READY").equals(true)) { %>
					<div class="alarm-section">
						<p class="section-title"><spring:message code="msg_0691"/></p>
						<div class="alarm-content" onclick="location.href='/member/myQuestion?Flag=N&CallAlarmFlag=Y'">
							<img src="/Common/images/alarm/icon_ans_choice_ready.png">
							<div>
								<p class="alarm-text"><spring:message code="msg_0692"/><span class="alarm-text-count"><%=request.getAttribute("ANS_CHOICE_READY")%></span><spring:message code="msg_0693"/></p>
								<p class="alarm-text-date"><%=request.getAttribute("ANS_CHOICE_READY_DATE_REG") == null ? "" : request.getAttribute("ANS_CHOICE_READY_DATE_REG")%></p>
							</div>
						</div>
					</div>
					<% } %>
					<% if(request.getAttribute("CODE_MEM_ALARM_VIEW_FIELD_CD_ALMONEY_INCOME").equals(true)) { %>
					<div class="alarm-section">
						<p class="section-title"><spring:message code="msg_0694"/></p>
						<div class="alarm-content" onclick="location.href='/member/bank/index?CallAlarmFlag=Y'">
							<img src="/Common/images/alarm/icon_almoney_income.png">
							<div>
								<p class="alarm-text"><spring:message code="msg_0696"/><span class="alarm-text-count"><%=request.getAttribute("ALMONEY_INCOME")%></span><spring:message code="msg_0697"/></p>
								<p class="alarm-text-date"><%=request.getAttribute("ALMONEY_INCOME_DATE_REG") == null ? "" : request.getAttribute("ALMONEY_INCOME_DATE_REG")%></p>
							</div>
						</div>
					</div>
					<% } %>
					<% if(request.getAttribute("CODE_MEM_ALARM_VIEW_FIELD_CD_ALMONEY_PAYING").equals(true)) { %>
					<div class="alarm-section">
						<p class="section-title"><spring:message code="msg_0695"/></p>
						<div class="alarm-content" onclick="location.href='/member/bank/index?CallAlarmFlag=Y'">
							<img src="/Common/images/alarm/icon_almoney_paying.png">
							<div>
								<p class="alarm-text"><spring:message code="msg_0698"/><span class="alarm-text-count"><%=request.getAttribute("ALMONEY_PAYING")%></span><spring:message code="msg_0697"/></p>
								<p class="alarm-text-date"><%=request.getAttribute("ALMONEY_PAYING_DATE_REG") == null ? "" : request.getAttribute("ALMONEY_PAYING_DATE_REG")%></p>
							</div>
						</div>
					</div>
					<% } %>
					<% if(request.getAttribute("CODE_MEM_ALARM_VIEW_FIELD_CD_MEM_LEVEL_UP").equals(true) && request.getAttribute("MEM_LEVEL_UP") != "0") { %>
					<div class="alarm-section">
							<p class="section-title"><spring:message code="msg_0699"/></p>
							<div class="alarm-content" onclick="location.href='/member/myInfo?CallAlarmFlag=Y'">
							<img src="/Common/images/alarm/icon_mem_level_up.png">
							<div>
								<p class="alarm-text"><spring:message code="msg_0700"/></p>
								<p class="alarm-text-date"><%=request.getAttribute("MEM_LEVEL_UP_DATE_REG") == null ? "" : request.getAttribute("MEM_LEVEL_UP_DATE_REG")%></p>
							</div>
						</div>
					</div>
					<% } %>
					<% if(request.getAttribute("CODE_MEM_ALARM_VIEW_FIELD_CD_REPORT").equals(true)) { %>
					<div class="alarm-section">
						<p class="section-title"><spring:message code="msg_0229"/></p>
						<div class="alarm-content" onclick="alert('<spring:message code="msg_0701"/>')">
						<img src="/Common/images/alarm/icon_report.png">
						<div>
							<p class="alarm-text"><spring:message code="msg_0702"/><span class="alarm-text-count"><%=request.getAttribute("CD_REPORT")%></span><spring:message code="msg_0703"/></p>
							<p class="alarm-text-date"><%=request.getAttribute("REPORT_DATE_REG") == null ? "" : request.getAttribute("REPORT_DATE_REG")%></p>
						</div>
					</div>
					</div>
					<% } %>
					<% if(request.getAttribute("CODE_MEM_ALARM_VIEW_FIELD_CD_MENTEE").equals(true)) { %>						
					<div class="alarm-section">
						<p class="section-title"><spring:message code="msg_0704"/></p>
						<div class="alarm-content" onclick="location.href='/member/myPartner?FlagPartner=M&FlagFollower=T&CallAlarmFlag=Y'">
							<img src="/Common/images/alarm/icon_mentee.png">
							<div>
								<p class="alarm-text"><span class="alarm-text-count"><%=request.getAttribute("MENTEE")%></span><spring:message code="msg_0705"/></p>
								<p class="alarm-text-date"><%=request.getAttribute("MENTEE_DATE_REG") == null ? "" : request.getAttribute("MENTEE_DATE_REG")%></p>
							</div>
						</div>
					</div>
					<% } %>
					<% if(request.getAttribute("CODE_MEM_ALARM_VIEW_FIELD_CD_MENTEE_UNSET").equals(true)) { %>
					<div class="alarm-section">
						<p class="section-title"><spring:message code="msg_0706"/></p>
						<div class="alarm-content" onclick="location.href='/member/myPartner?FlagPartner=M&FlagFollower=T&CallAlarmFlag=Y'">
							<img src="/Common/images/alarm/icon_mentee_unset.png">
							<div>
								<p class="alarm-text"><span class="alarm-text-count"><%=request.getAttribute("MENTEE_UNSET")%></span><spring:message code="msg_0707"/></p>
								<p class="alarm-text-date"><%=request.getAttribute("MENTEE_UNSET_DATE_REG") == null ? "" : request.getAttribute("MENTEE_UNSET_DATE_REG")%></p>
							</div>
						</div>
					</div>
					<% } %>
					<% if(request.getAttribute("CODE_MEM_ALARM_VIEW_FIELD_CD_RECOMM_MEM_JOIN").equals(true)) { %>
					<div class="alarm-section">
						<p class="section-title"><spring:message code="msg_0708"/></p>
						<div class="alarm-content" onclick="location.href='/member/myRecommend?CallAlarmFlag=Y'">
							<img src="/Common/images/alarm/icon_recomm_mem_join.png">
							<div>
								<p class="alarm-text"><spring:message code="msg_0709"/><span class="alarm-text-count"><%=request.getAttribute("RECOMM_MEM_JOIN")%></span><spring:message code="msg_0710"/></p>
								<p class="alarm-text-date"><%=request.getAttribute("RECOMM_MEM_JOIN_DATE_REG") == null ? "" : request.getAttribute("RECOMM_MEM_JOIN_DATE_REG")%></p>
							</div>
						</div>
					</div>
					<% } %>
					<% if(request.getAttribute("CODE_MEM_ALARM_VIEW_FIELD_CD_NOTICE").equals(true)) { %>
					<div class="alarm-section">
						<p class="section-title"><spring:message code="msg_0185"/></p>
						<div class="alarm-content" onclick="location.href='/default/cs/notice/notice?Page=1&CallAlarmFlag=Y'">
							<img src="/Common/images/alarm/icon_notice.png">
							<div>
								<p class="alarm-text"><spring:message code="msg_0711"/><span class="alarm-text-count"><%=request.getAttribute("NOTICE")%></span><spring:message code="msg_0712"/></p>
								<p class="alarm-text-date"><%=request.getAttribute("NOTICE_DATE_REG") == null ? "" : request.getAttribute("NOTICE_DATE_REG")%></p>
							</div>
						</div>
					</div>
					<% } %>
				</div>
				<p class="alarm-footnote">※ <spring:message code="msg_0713"/></p>
			</div>
		</div>
		<div id="top_btn">
	        <a href="javascript:void(0);">
	            <span>
	                <img src="/Common/images/top_btn_arrow.svg" alt="top_btn">
	            </span>
	        </a>
	    </div>
    </div>
</div>
</body>