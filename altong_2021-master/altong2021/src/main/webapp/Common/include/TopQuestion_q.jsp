<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.math.*" %>
<%@ page import="com.altong.web.logic.LogAlmoneyVO" %>  
<%@ page import="com.altong.web.logic.util.CommonUtil" %>   
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %> 
<%
//SELECT SUM_Q_Almoney, SUM_Q_ViewAlmoney FROM T_STATUS
//CommonUtil   getAlmoneyOne
/*
$SumQRegAlmoney = number_format(round($r_lib['SUM_Q_Almoney']));
	$SumQViewAlmoney = number_format(round($r_lib['SUM_Q_ViewAlmoney']));
*/
LogAlmoneyVO almoneyOne = CommonUtil.getAlmoneyOne();

BigDecimal SumQRegAlmoney = almoneyOne.getSum_Q_Almoney();
BigDecimal SumQViewAlmoney = almoneyOne.getSum_Q_ViewAlmoney();

//BigDecimal SumQRegAlmoney = new BigDecimal("0.0");
//BigDecimal SumQViewAlmoney = new BigDecimal("0.0");

pageContext.setAttribute("SumQRegAlmoney", SumQRegAlmoney);
pageContext.setAttribute("SumQViewAlmoney", SumQViewAlmoney);
%>
<div class="atm_ranktopQ_1">
	<span class="atm_rankq_c2"><p class="atm_ranktopQ_c3">총 질문 금액</p>&nbsp;<p class="atm_ranktopQ_c4"><fmt:formatNumber value="${SumQRegAlmoney}" pattern="#,###" />&nbsp;알</p></span>&nbsp;
	<span class="atm_rankq_c2"><p class="atm_ranktopQ_c3">질문열람수익</p>&nbsp;<p class="atm_ranktopQ_c4"><fmt:formatNumber value="${SumQViewAlmoney}" pattern="#,###" />&nbsp;알</p></span>
</div>
<style>
body {
	background-color: #f2f2f2;
}
.atm_bestQ_ttbar2{  background-color:#2ac1bc; text-align:center; position:relative;}
.atm_bestQ_btn_L1{ position:absolute; left:0px; top:2px; width:30px; }
.atm_bestQ_c1{ font-size:14px; font-weight:bold; letter-spacing:-0.5px; padding:8px 0; color:#ffffff; display:inline-block; cursor:pointer;}
.atm_ttbar_btn_Q{ width:18px;}
@media screen and (min-width: 800px) {
.atm_rankq_tab1{ text-align:center;}
.atm_rankq_tab1_pc{ width:800px; display:inline-block;  position:relative; }
.atm_bestQ_ttbar2{ text-align:center;}
.atm_bestQ_ttbar2_pc{ width:800px; display:inline-block;  position:relative; }
/*랭킹탭*/
.atm_rankq_tabc1   {font-size:14px;}
.atm_rankq_tabc2   {font-size:14px;}
.atm_rankq_tabc3   {font-size:14px;}
.atm_rankq_tabc1_on{font-size:14px;}
.atm_rankq_tabc2_on{font-size:14px; }
.atm_rankq_tabc3_on{font-size:14px;}
.atm_bestQ_c1{ font-size:14px;}
.atm_ttbar_btn_Q{ width:18px;}

/*질문랭크탑*/
.atm_ranktopQ_c2{white-space:nowrap; }
.atm_ranktopQ_c3{ font-size:12px;   }
.atm_ranktopQ_c4{ font-size:14px; ;  }
}

</style>
<div class="atm_bestQ_ttbar2" >
<div class="atm_bestQ_ttbar2_pc" >
	<a href="#" ><img src="/Common/images/icon_question_white.png" class="atm_ttbar_btn_Q"/></a>
	<a href="/question/questionWrite"><p class="atm_bestQ_c1">질문하기</p></a>
	<a onClick="history.back()"><img src="/Common/images/btn_back_bold.png" class="atm_bestQ_btn_L1"/></a>
</div>
</div>
<div class="atm_rankq_tab1">
<%@ include file="/Common/include/EventBanner.jsp" %>
<%
String uriPath = request.getServletPath();
%>
<div class="atm_rankq_tab1_pc">
	<p class="atm_rankq_tabc1<%if(uriPath.contains("bestList")){%>_on<%}%>" onClick="location.href='/question/bestList';">베스트 질문</p>
	<p class="atm_rankq_tabc2<%if(uriPath.contains("eventList")){%>_on<%}%>" onClick="location.href='/question/eventList'">이벤트</p>
	<p class="atm_rankq_tabc3<%if(uriPath.contains("rankQuestion")){%>_on<%}%>" onClick="location.href='/question/rankQuestion';">랭킹</p>
</div>
</div>