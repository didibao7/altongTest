<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
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
pageContext.setAttribute("global", global_my);

String data1 = request.getParameter("myInfoExchList_1");
String data2 = request.getParameter("myInfoExchList_2");

Map<String, Object> dataHash1 = new Gson().fromJson(data1, Map.class);
Map<String, Object> dataHash2 = new Gson().fromJson(data2, Map.class);


int userSeq = Integer.parseInt( String.valueOf( global_my.get("UserSeq") ) );
int userLv = Integer.parseInt( String.valueOf( global_my.get("UserLv") ) );

String startDate = String.valueOf( dataHash2.get("StartDate") );
String endDate = String.valueOf( dataHash2.get("EndDate") );
String[] splitDt = endDate.split("-");

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

BigDecimal exchBaseAlmoney = new BigDecimal( dataHash1.size() > 0 ? String.valueOf( dataHash1.get("ExchBaseAlmoney") ) : "0.0" );
BigDecimal exchLimitAlmoney = new BigDecimal( dataHash1.size() > 0 ? String.valueOf( dataHash1.get("ExchLimitAlmoney") ) : "0.0" );
float exchAlmoneyTexRate = dataHash1.size() > 0 ? Float.valueOf(String.valueOf( dataHash1.get("ExchAlmoneyTexRate") ) ) :  0.0f;
int exchStampCnt = dataHash1.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash1.get("ExchStampCnt") ) ) ) : 0;
int exchQusRegCnt = dataHash1.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash1.get("ExchQusRegCnt") ) ) ) : 0;
int exchAnsRegCnt = dataHash1.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash1.get("ExchAnsRegCnt") ) ) ) : 0;
int exchAnsChoicedCnt = dataHash1.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash1.get("ExchAnsChoicedCnt") ) ) ) : 0;
int exchAnsEstiCnt = dataHash1.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash1.get("ExchAnsEstiCnt") ) ) ) : 0;
String isExchOK = dataHash2.size() > 0 ? String.valueOf( dataHash2.get("isExchOK") ) : "";
BigDecimal almoney = new BigDecimal( dataHash2.size() > 0 ? String.valueOf( dataHash2.get("Almoney") ) : "0.0" );
int stamp = dataHash2.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash2.get("STAMP") ) ) ) :  0;
int qusRegCnt = dataHash2.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash2.get("QusRegCnt") ) ) ) :  0;
int ansRegCnt = dataHash2.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash2.get("AnsRegCnt") ) ) ) :  0;
int ansChoicedCnt = dataHash2.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash2.get("AnsChoicedCnt") ) ) ) :  0;
int ansEstiCnt = dataHash2.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash2.get("AnsEstiCnt") ) ) ) :  0;


pageContext.setAttribute("exchBaseAlmoney", exchBaseAlmoney);
pageContext.setAttribute("exchLimitAlmoney", exchLimitAlmoney);
pageContext.setAttribute("exchAlmoneyTexRate", exchAlmoneyTexRate);
pageContext.setAttribute("exchStampCnt", exchStampCnt);
pageContext.setAttribute("exchQusRegCnt", exchQusRegCnt);
pageContext.setAttribute("exchAnsRegCnt", exchAnsRegCnt);
pageContext.setAttribute("exchAnsChoicedCnt", exchAnsChoicedCnt);
pageContext.setAttribute("exchAnsEstiCnt", exchAnsEstiCnt);
pageContext.setAttribute("isExchOK", isExchOK);
pageContext.setAttribute("almoney", almoney);
pageContext.setAttribute("stamp", stamp);
pageContext.setAttribute("qusRegCnt", qusRegCnt);
pageContext.setAttribute("ansRegCnt", ansRegCnt);
pageContext.setAttribute("ansChoicedCnt", ansChoicedCnt);
pageContext.setAttribute("ansEstiCnt", ansEstiCnt);
%>
<section class="week_mission exchange">
	<div class="section_top">
		<p class="title">?????? ?????? ??????</p>
	<% if (deadLine == 0) { %>
		<p class="sub_txt"><strong>?????? 24???</strong>??? ????????? ???????????????.</p>
	<% } else { %>			
		<p class="sub_txt">?????? ???????????? <strong><%=deadLine%>???</strong> ???????????????.</p>
	<% }%>
		<div class="date_box">
			<div class="date_btn">
				<p class="arrow arrow_L" id="dateArrowL" onclick="fAjax('/member/myInfoExchAjax', 'json', 'ACT=ExchGetLastWeek&LAST=1');"><img src="${libIMG_URL}/Common/images/myadmin_date_arrowL.png" alt=""></p>
				<p class="content">
					<span id="weekStartDate"><%=startDate%></span> ~ <span id="weekEndDate"><%=endDate%></span>
				</p>
				<p class="arrow arrow_R" id="dateArrowR"><img src="${libIMG_URL}/Common/images/myadmin_date_arrowR.png" alt=""></p>
			</div>
		</div>
	</div>
	<div class="white_wrap">
		<div class="sum">
			<p class="sub_txt">?????? ?????????</p>
			<div class="sum_graph">
				<p class="graph"></p>
				<p class="sum_txt"><span></span>%</p>
			</div>
		</div>
		<div class="detail">
			<div class="detail_table cell2">
			<% if (exchQusRegCnt != 0) { %>
				<div class="cell">
					<div class="box">
						<div class="detail_graph">
							<p class="graph_bg"></p>
							<p class="graph"></p>
						</div>
						<div class="detail_content">
							<p>??????</p>
							<p class="count"><span class="prgDetailCnt" id="weekQCnt"><%=qusRegCnt > exchQusRegCnt ? exchQusRegCnt : qusRegCnt%></span>/<span class="prgDetailMaxCnt" id="weekQMax"><%=exchQusRegCnt%></span></p>
						</div>
					</div>
				</div>
			<% } %>
			<% if (exchAnsRegCnt != 0) { %>
				<div class="cell">
					<div class="box">
						<div class="detail_graph">
							<p class="graph_bg"></p>
							<p class="graph"></p>
						</div>
						<div class="detail_content">
							<p>??????</p>
							<p class="count"><span class="prgDetailCnt" id="weekACnt"><%=ansRegCnt > exchAnsRegCnt ? exchAnsRegCnt : ansRegCnt %></span>/<span class="prgDetailMaxCnt" id="weekAMax"><%=exchAnsRegCnt%></span></p>
						</div>
					</div>
				</div>
			<% } %>
			</div>
			<div class="detail_table cell3">
			<% if (exchAnsEstiCnt != 0) { %>
				<div class="cell">
					<div class="box">
						<div class="detail_graph">
							<p class="graph_bg"></p>
							<p class="graph"></p>
						</div>
						<div class="detail_content">
							<p>??????<br>??????</p>
							<p class="count"><span class="prgDetailCnt" id="weekAEstiCnt"><%=ansEstiCnt > exchAnsEstiCnt ? exchAnsEstiCnt : ansEstiCnt %></span>/<span class="prgDetailMaxCnt" id="weekAEstiMax"><%=exchAnsEstiCnt%></span></p>
						</div>
					</div>
				</div>
			<% } %>
			<% if (exchAnsChoicedCnt != 0) { %>
				<div class="cell">
					<div class="box">
						<div class="detail_graph">
							<p class="graph_bg"></p>
							<p class="graph"></p>
						</div>
						<div class="detail_content">
							<p>??????<br>?????????</p>
							<p class="count"><span class="prgDetailCnt" id="weekAChoicedCnt"><%=ansChoicedCnt > exchAnsChoicedCnt ? exchAnsChoicedCnt : ansChoicedCnt %></span>/<span class="prgDetailMaxCnt" id="weekAChoicedMax"><%=exchAnsChoicedCnt%></span></p>
						</div>
					</div>
				</div>
			<% } %>
			</div>
		</div>
	</div>
</section>

<section class="my_exchange">
	<div class="section_top">
		<p class="title">?????? ?????????</p>
	<% if (exchStampCnt <= stamp) { %>
		<p class="sub_txt"><strong>?????? ?????? ?????? !</strong></p>
	<% } else { %>
		<p class="sub_txt">?????? ?????? ???????????? ????????? <strong><%=exchStampCnt - stamp%>???</strong> ???????????????.</p>
	<% } %>
	</div>
	
	<div class="white_wrap">
		<div class="exchange_sum">
		<%
			for (int i = 0; i < exchStampCnt; i++) {
				if (i < stamp) {
		%>
			<p class="graph fill"></p>
		<%
				} else {
		%>
			<p class="graph"></p>
		<%
				}
			}		
		%>
		</div>
	<% if (isExchOK == "Y") { %>
		<div class="exch_btn_wrap">
			<p class="exch_btn">
				<a href="/member/account/exchange/exchangeAlpay">???????????? <i class="material-icons">keyboard_arrow_right</i></a>
			</p>
		</div>
    <% } else if (exchStampCnt <= stamp) { %>
        <div class="exch_btn_wrap" style="margin-top:25px;color:#777">
            <p>?????? ???????????? ?????? ??????????????????.</p>
            <p><strong>????????? 0???</strong> ????????? ????????? ???????????????.</p>
        </div>
	<% } %>
	</div>
</section>