<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
JSONObject g_info = (JSONObject)CommonUtil.getGlobal(request, response);
pageContext.setAttribute("global", g_info);
%>
<style>
    .menu-wrapper {
        padding: 20px;
    }

    .menu-wrapper>a>button {
        margin: 5px;
        width: 124px;
		height: 50px;
		text-align: center;
    }

    .menu-wrapper>a {
        text-decoration: none;
    }

    .btn.btn-primary {
        margin: 5px;
        width: 124px;
        height: 50px;
        text-align: center;
        color: #fff;
        background-color: #337ab7;
        border-color: #2e6da4;
        border: 1px solid transparent;
        border-radius: 4px;
        cursor: pointer;
    }
    
    .btn.btn-primary:hover {
        background-color: #28608f;
    }


</style>
<div class="row menu-wrapper">
<%-- libIsAdmin(HttpServletResponse response, int pageName, int userSeq) --%>

	<%if(CommonUtil.libhasAuthority(configInput, userSeq)) {%>
		<a href="/aadmin/configInput"><!-- .alt 도 있음 : configInput_new -->
            <button type="button" class="btn btn-primary">환경 설정</button>
        </a>
	<%}%>
	<%if(CommonUtil.libhasAuthority(memberList, userSeq) || CommonUtil.libhasAuthority(memberCertList, userSeq)) {%>
		<%if(CommonUtil.libhasAuthority(memberList, userSeq)) {%>
		<a href="/aadmin/memberList">
		<%} else if(CommonUtil.libhasAuthority(memberCertList, userSeq)) {%>
		<a href="/aadmin/memberCertList"><!-- .alt -->
		<%}%>
			<button type="button" class="btn btn-primary">회원 관리</button>
		</a>
	<%}%>
	<%if(CommonUtil.libhasAuthority(recommendView, userSeq) || CommonUtil.libhasAuthority(exchReadyList, userSeq) || CommonUtil.libhasAuthority(alpayLogCheck, userSeq) || CommonUtil.libhasAuthority(alpayExchList, userSeq)) {%>
		<%if(CommonUtil.libhasAuthority(recommendView, userSeq)) {%>
		<a href="/aadmin/recommend/recommendView">
		<%} else if(CommonUtil.libhasAuthority(exchReadyList, userSeq)) {%>
		<a href="/aadmin/exchange/exchReadyList"><!-- .alt -->
		<%} else if(CommonUtil.libhasAuthority(alpayLogCheck, userSeq)) {%>
		<a href="/aadmin/exchange/alpayLogCheck"><!-- .alt -->
		<%} else if(CommonUtil.libhasAuthority(alpayExchList, userSeq)) {%>
		<a href="/aadmin/exchange/alpayExchList"><!-- .alt -->
		<%}%>
			<button type="button" class="btn btn-primary">추천인 관리</button>
		</a>
	<%}%>
	<%if(CommonUtil.libhasAuthority(exchangeAskList, userSeq)) {%>
		<a href="/aadmin/exchange/exchangeAskList">
            <button type="button" class="btn btn-primary">출금 관리</button>
        </a>
	<%}%>
	<%if(CommonUtil.libhasAuthority(sectionList, userSeq)) {%>
		<!--
		<a href="/aadmin/sectionList">
            <button type="button" class="btn btn-primary">카테고리 관리</button>
        </a>
        -->
	<%}%>
	<%if(CommonUtil.libhasAuthority(eventList, userSeq)) {%>
		<a href="/aadmin/eventList">
            <button type="button" class="btn btn-primary">이벤트 관리</button>
        </a>
	<%}%>
	<%if(CommonUtil.libhasAuthority(adList, userSeq)) {%>
		<a href="/aadmin/adList">
            <button type="button" class="btn btn-primary">광고 관리</button>
        </a>
	<%}%>
	<%if(CommonUtil.libhasAuthority(almoneyList, userSeq)) {%>
		<a href="/aadmin/almoneyList">
            <button type="button" class="btn btn-primary">알머니 현황</button>
        </a>
	<%}%>
	<%if(CommonUtil.libhasAuthority(levelUpReadyList, userSeq) || CommonUtil.libhasAuthority(levelUpReadyList_new, userSeq)) {%>
		<%if(CommonUtil.libhasAuthority(levelUpReadyList_new, userSeq)) {%>
		<a href="/aadmin/levelup/levelUpReadyList_new"><!-- .alt -->
		<%} else if(CommonUtil.libhasAuthority(levelUpReadyList, userSeq)) {%>
		<a href="/aadmin/levelup/levelUpReadyList">
		<%} %>
			<button type="button" class="btn btn-primary">회원레벨 관리</button>
		</a>
	<%}%>
	<%if(CommonUtil.libhasAuthority(notice, userSeq)) {%>
		<a href="/aadmin/notice?Page=1">
            <button type="button" class="btn btn-primary">공지 사항</button>
        </a>
	<%}%>
	<%if(CommonUtil.libhasAuthority(reportList, userSeq)) {%>
		<a href="/aadmin/report/reportList">
            <button type="button" class="btn btn-primary">신고</button>
        </a>
	<%}%>
	<%if(CommonUtil.libhasAuthority(storeInput, userSeq) || CommonUtil.libhasAuthority(storePrepayment, userSeq)) {%>
		<%if(CommonUtil.libhasAuthority(storeInput, userSeq)) {%>
		<a href="/aadmin/alpay/storeInput">
		<%} else if(CommonUtil.libhasAuthority(storePrepayment, userSeq)) {%>
		<a href="/aadmin/alpay/storePrepayment">
		<%}%>
			<button type="button" class="btn btn-primary">알맹이</button>
		</a>
	<%}%>
	
	<a href="/aadmin/quiz/quizGameList">
		<button type="button" class="btn btn-primary">퀴즈 게임</button>
	</a>
	
	<a href="/aadmin/quiz/roulletteStatistics">
		<button type="button" class="btn btn-primary">룰렛 이벤트 통계</button>
	</a>

</div><!-- row menu-wrapper end -->

