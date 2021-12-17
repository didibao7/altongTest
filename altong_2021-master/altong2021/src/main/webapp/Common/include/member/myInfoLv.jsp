<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.temporal.ChronoUnit" %>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="com.google.gson.Gson" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %> 
<%
JSONObject global_my = (JSONObject)CommonUtil.getGlobal(request, response);

String tab = request.getParameter("tab");
//System.out.println("global_my : " + global_my);
String data1 = request.getParameter("myInfoLvList_1");
String data2 = request.getParameter("myInfoLvList_2");
//System.out.println("data1 : " + data1);
Map<String, Object> dataHash1 = new Gson().fromJson(data1, Map.class);
Map<String, Object> dataHash2 = new Gson().fromJson(data2, Map.class);

int userSeq = Integer.parseInt( String.valueOf( global_my.get("UserSeq") ) );
int userLv = Integer.parseInt( String.valueOf( global_my.get("UserLv") ) );

String date2 = String.valueOf(dataHash2.get("EndDate"));
String[] splitDt = date2.split("-");

int splitY = 0;
int splitM = 0;
int splitS = 0;
if(dataHash2.size() > 0) {
	splitY = Integer.parseInt(splitDt[0]);
	splitM = Integer.parseInt(splitDt[1]);
	splitS = Integer.parseInt(splitDt[2]);
}
else {
	Date date = new Date(); // your date
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	
	splitY = cal.get(Calendar.YEAR);
	splitM = cal.get(Calendar.MONTH);
	splitS = cal.get(Calendar.DAY_OF_MONTH);
}

LocalDate nowTime = LocalDate.now();
LocalDate weekEndTime = LocalDate.of(splitY, splitM, splitS);
//System.out.println("weekEndTime : " + weekEndTime);
long deadLine = ChronoUnit.DAYS.between(nowTime, weekEndTime);
//System.out.println("DeadLine : " + DeadLine);
pageContext.setAttribute("global", global_my);

int lvUpQusRegCnt = dataHash1.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash1.get("LvUpQusRegCnt") ) ) ) : 0;
int lvUpAnsRegCnt = dataHash1.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash1.get("LvUpAnsRegCnt") ) ) ) : 0;
int lvUpAnsEstiCnt = dataHash1.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash1.get("LvUpAnsEstiCnt") ) ) ) : 0;
int lvUpAnsChoicedCnt = dataHash1.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash1.get("LvUpAnsChoicedCnt") ) ) ) : 0;
int lvUpReplyCnt = dataHash1.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash1.get("LvUpReplyCnt") ) ) ) : 0;

float fLvUpStampCnt = dataHash1.size() > 0 ? Float.valueOf(String.valueOf( dataHash1.get("LvUpStampCnt") ) ) : 0;
int lvUpStampCnt = dataHash1.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash1.get("LvUpStampCnt") ) ) ) : 0;
int lvUpBaseAlmoney = dataHash1.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash1.get("LvUpBaseAlmoney") ) ) ) : 0;
int lvUpEducationCnt = dataHash1.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash1.get("LvUpEducationCnt") ) ) ) : 0;
int lvUpRecmdCnt_1 = dataHash1.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash1.get("LvUpRecmdCnt_1") ) ) ) : 0;

int qusRegCnt = dataHash2.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash2.get("QusRegCnt") ) ) ) : 0;
int ansRegCnt = dataHash2.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash2.get("AnsRegCnt") ) ) ) : 0;
int ansEstiCnt = dataHash2.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash2.get("AnsEstiCnt") ) ) ) : 0;
int ansChoicedCnt = dataHash2.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash2.get("AnsChoicedCnt") ) ) ) : 0;
int replyCnt = dataHash2.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash2.get("ReplyCnt") ) ) ) : 0;

int stamp = dataHash2.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash2.get("STAMP") ) ) ) : 0;
int almoney = dataHash2.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash2.get("Almoney") ) ) ) : 0;
int educationCnt = dataHash2.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash2.get("EducationCnt") ) ) ) : 0;
int recmdCnt = dataHash2.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash2.get("RecmdCnt") ) ) ) : 0;
String isUpOK = dataHash2.size() > 0 ? String.valueOf( dataHash2.get("isUpOK") ) : "";
%>
<section class="week_mission">
	<div class="section_top">
		<p class="title">주간 활동 미션</p>
		<%if(deadLine == 0) { %>
		<p class="sub_txt"><strong>금일 24시</strong>에 미션이 마감됩니다.</p>
		<%} else {%>
		<p class="sub_txt">미션 마감까지 <strong><%=deadLine%>일</strong> 남았습니다.</p>
		<%} %>
		<div class="date_box">
			<div class="date_btn">
				<p class="arrow arrow_L" id="dateArrowL" onclick="fAjax('/member/myInfoLvAjax', 'json', 'ACT=GetLastWeek&LAST=1');"><img src="${libIMG_URL}/Common/images/myadmin_date_arrowL.png" alt=""></p>
				<p class="content">
					<span id="weekStartDate"><%=dataHash2.get("StartDate")%></span> ~ <span id="weekEndDate"><%=dataHash2.get("EndDate")%></span>
				</p>
				<p class="arrow arrow_R" id="dateArrowR"><img src="${libIMG_URL}/Common/images/myadmin_date_arrowR.png" alt=""></p>
			</div><!-- date_btn end -->
		</div><!-- date_box end -->
	</div>	
		<div class="white_wrap">
			<div class="sum">
				<p class="sub_txt">종합 달성률</p>
				<div class="sum_graph">
					<p class="graph"></p>
					<p class="sum_txt"><span></span>%</p>
				</div>
			</div><!-- sum end -->
			<div class="detail">
				<div class="detail_table">
					<%if(lvUpQusRegCnt != 0) { %>
						<div class="cell">
							<div class="box">
								<div class="detail_graph">
									<p class="graph_bg"></p>
									<p class="graph"></p>
								</div>
								<div class="detail_content">
									<p>질문</p>
									<p class="count"><span class="prgDetailCnt" id="weekQCnt"><%= qusRegCnt > lvUpQusRegCnt ? lvUpQusRegCnt : qusRegCnt %></span>/<span class="prgDetailMaxCnt" id="weekQMaxCnt"><%=lvUpQusRegCnt%></span></p>
								</div>
							</div>
						</div>
					<%}%>
					<%if(lvUpAnsRegCnt != 0) { %>
						<div class="cell">
							<div class="box">
								<div class="detail_graph">
									<p class="graph_bg"></p>
									<p class="graph"></p>
								</div>
								<div class="detail_content">
									<p>답변</p>
									<p class="count"><span class="prgDetailCnt" id="weekACnt"><%= ansRegCnt > lvUpAnsRegCnt ? lvUpAnsRegCnt : ansRegCnt %></span>/<span class="prgDetailMaxCnt" id="weekAMaxCnt"><%=lvUpAnsRegCnt%></span></p>
								</div>
							</div>
						</div>
					<%} %>
				</div><!-- detail_table end -->
				
				<div class="detail_table">
				<%if(lvUpAnsEstiCnt != 0) { %>
					<div class="cell">
						<div class="box">
							<div class="detail_graph">
								<p class="graph_bg"></p>
								<p class="graph"></p>
							</div>
							<div class="detail_content">
								<p>답변<br>평가</p>
								<p class="count"><span class="prgDetailCnt" id="weekAEstiCnt"><%= ansEstiCnt > lvUpAnsEstiCnt ? lvUpAnsEstiCnt : ansEstiCnt%></span>/<span class="prgDetailMaxCnt" id="weekAEstiMaxCnt"><%=lvUpAnsEstiCnt%></span></p>
							</div>
						</div>
					</div>
				<%}%>
				<%if(lvUpAnsChoicedCnt != 0) { %>
					<div class="cell">
						<div class="box">
							<div class="detail_graph">
								<p class="graph_bg"></p>
								<p class="graph"></p>
							</div>
							<div class="detail_content">
								<p>답변<br>피채택</p>
								<p class="count"><span class="prgDetailCnt" id="weekAChoicedCnt"><%= ansChoicedCnt > lvUpAnsChoicedCnt ? lvUpAnsChoicedCnt : ansChoicedCnt %></span>/<span class="prgDetailMaxCnt" id="weekAChoicedMaxCnt"><%=lvUpAnsChoicedCnt%></span></p>
							</div>
						</div>
					</div>
				<%}%>
				<%if(lvUpReplyCnt != 0) { %>
					<div class="cell">
						<div class="box">
							<div class="detail_graph">
								<p class="graph_bg"></p>
								<p class="graph"></p>
							</div>
							<div class="detail_content">
								<p><br>댓글</p>
								<p class="count"><span class="prgDetailCnt" id="weekReplyCnt"><%= replyCnt > lvUpReplyCnt ? lvUpReplyCnt : replyCnt %></span>/<span class="prgDetailMaxCnt" id="weekReplyMaxCnt"><%=lvUpReplyCnt%></span></p>
							</div>
						</div>
					</div>
				<%}%>
			</div><!-- detail_table 2 end -->
		</div><!-- detail end -->
	</div><!-- white_wrap end -->
</section><!-- week_mission end -->

<section class="my_stamp">
	<div class="section_top">
		<p class="title">나의 도장</p>
	</div>
	<div class="white_wrap">
		<div class="content_box">
			<div class="top">
				<p class="title">활동 미션</p>
				<hr>
			</div>
			<div class="stamp_wrap" id="lvupStampWrap" style="height:<%= Math.ceil(fLvUpStampCnt / 4) * 75 %>px" data-stampcnt="<%=stamp%>" data-stampmax="<%=lvUpStampCnt%>">
				<div class="clone_el">
					<div class="stamp_road">
						<p class="cell"><span class="box"></span></p>
					</div>
					<p class="stamp_base"><img src="${libIMG_URL}/Common/images/myadmin_lvup_stamp_bg.png" alt="도장"></p>
					<p class="stamp"><img src="${libIMG_URL}/Common/images/myadmin_lvup_stamp.png" alt="획득한 도장"></p>
					<div class="stamp_arrow">
						<p class="cell"><span class="box"><img src="${libIMG_URL}/Common/images/myadmin_stamp_arrow.png" alt="진행중"></span></p>
					</div>
				</div>
				<!-- 지도 -->
			</div>
			<div class="section_top" style="margin:0 0 5px 0">
				<p class="sub_txt" style="text-align:center">
				<% if (lvUpStampCnt <= stamp) {%>
					 <strong>활동 미션 도장 모두 획득!</strong>
				<% } else { %>
					 활동 미션 달성까지 
					 <strong>도장 <%=lvUpStampCnt - stamp%>개</strong> 남았습니다.
				<% } %>
				</p>
			</div>
		</div><!-- content_box end -->
		<% if (lvUpBaseAlmoney != 0) {
			pageContext.setAttribute("almoney", almoney);
			pageContext.setAttribute("lvUpBaseAlmoney", lvUpBaseAlmoney);
		%>
			<div class="additional">
				<p class="more_icon<%=almoney >= lvUpBaseAlmoney ? " success" : ""%>">+</p>
				<div class="content_box">
					<p class="title">보유 알머니</p>
					<hr>
					<div class="edu_stamp_wrap">
						<p class="almoney_stamp<%=lvUpBaseAlmoney <= almoney ? " success" : ""%>">
							<strong><fmt:formatNumber value="${almoney}" pattern="#,###" />알</strong> / <fmt:formatNumber value="${lvUpBaseAlmoney}" pattern="#,###" />알
						</p>
					</div>
				</div>
			</div>
		<%}%>
		
		<% if (lvUpEducationCnt != 0) {%>
			<div class="additional">
				<p class="more_icon<%=lvUpEducationCnt == educationCnt ? " success" : ""%>">+</p>
				<div class="content_box">
					<p class="title">본사 교육</p>
					<hr>

					<div class="edu_stamp_wrap">
			<%	for (int i = 0; i < lvUpEducationCnt; i++) {
					if (i < educationCnt) {
			%>
						<p class="stamp"><img src="${libIMG_URL}/Common/images/myadmin_seminar_stamp.png" alt="본사교육 도장"></p>						
			<%		} else { %>
						<p class="stamp_base"><img src="${libIMG_URL}/Common/images/myadmin_seminar_stamp_bg.png" alt="획득한 본사교육 도장"></p>
			<%		} %>
			<% } %>
					</div>

					<div class="section_top" style="margin:27px 0 5px 0">
						<p class="sub_txt" style="text-align:center">
						<% if (lvUpEducationCnt <= educationCnt) { %>
							 <strong>본사 교육 도장 모두 획득!</strong>
						<% } else { %>
							 본사 교육
							 <strong>도장 <%=lvUpEducationCnt - educationCnt%>개</strong> 남았습니다.
						<% } %>
						</p>
					</div>
				</div>
			</div>
		<%}%>
		
		<% if (lvUpRecmdCnt_1 != 0) {%>
			<div class="additional">
				<p class="more_icon<%=lvUpRecmdCnt_1 == recmdCnt ? " success" : ""%>">+</p>
				<div class="content_box">
					<p class="title">피추천인(<%=CommonUtil.getLevelName(lvUpRecmdCnt_1, request)%>↑)</p>
					<hr>

					<div class="edu_stamp_wrap">
			<%	for (int i = 0; i < lvUpRecmdCnt_1; i++) {
					if (i < recmdCnt) {
			%>
						<p class="stamp"><img src="${libIMG_URL}/Common/images/myadmin_member_stamp.png" alt="피추천인 도장"></p>						
			<%		} else { %>
						<p class="stamp_base"><img src="${libIMG_URL}/Common/images/myadmin_member_stamp_bg.png" alt="획득한 피추천인 도장"></p>
			<%		} %>
			<% } %>
					</div>

					<div class="section_top" style="margin:27px 0 5px 0">
						<p class="sub_txt" style="text-align:center">
						<% if (lvUpRecmdCnt_1 <= recmdCnt) {%>
							 <strong>피추천인 도장 모두 획득!</strong>
						<% } else { %>
							 피추천인
							 <strong>도장 <%=lvUpRecmdCnt_1 - recmdCnt%>개</strong> 남았습니다.
						<% } %>
						</p>
					</div>
				</div>
			</div>
		<%}%>
		
		<% if (isUpOK == "Y") {%>
			<div class="finish success">
				<img src="${libIMG_URL}/Common/images/myadmin_finish_arrow_2.png" alt="승천">
				<p class="finish_lv"><span><%=(userLv != 99) ? CommonUtil.getLevelName(userLv + 1, request) : ""%></span></p>
				<p class="sub_txt"><%if(userLv != 99){%>승천 대기중입니다.<%}%></p>
			</div>
		<%} else {%>
			<div class="finish">
				<img src="${libIMG_URL}/Common/images/myadmin_finish_arrow.png" alt="승천">
				<p class="finish_lv"><span><%=(userLv != 99) ? CommonUtil.getLevelName(userLv + 1, request) : ""%></span></p>
			</div>
	        <div style="margin-top:25px;color:#777">
	            <p>모든 미션이 완료되면</p>
	            <p><strong>월요일 0시</strong> 이후에 관리자 심사 후 승천됩니다.</p>
	        </div>
		<%}%>
	</div><!-- white_wrap end -->
</section><!-- my_stamp end -->