<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %> 
<%
JSONObject h_info = (JSONObject)CommonUtil.getGlobal(request, response);
pageContext.setAttribute("global", h_info);

int userSeq = 0;

if(h_info != null) {
	userSeq = Integer.parseInt( String.valueOf( h_info.get("UserSeq") ) );
}

final int configInput 			= 0b00000000000000000000001;		//환경설정
final int configInput_new 		= 0b00000000000000000000010;	//환경설정 - 신규 승천 시스템 환경 설정
final int memberList	 		= 0b00000000000000000000100;	//회원관리 - 회원관리
final int memberCertList	 	= 0b00000000000000000001000;	//회원관리 - 회원 실명인증 관리
final int recommendView		 	= 0b00000000000000000010000;	//추천인 관리
final int exchangeAskList	 	= 0b00000000000000000100000;	//출금 관리
final int exchReadyList		 	= 0b00000000000000001000000;	//출금관리 - 회원 레벨별 출금 대기자 현황
final int alpayLogCheck		 	= 0b00000000000000010000000;	//출금관리 - 알머니 -> 알페이 전환 Log
final int alpayExchList		 	= 0b00000000000000100000000;	//출금관리 - 현금 출금 신청 현황
final int sectionList	 		= 0b00000000000001000000000;	//카테고리 관리
final int eventList		 		= 0b00000000000010000000000;	//이벤트 관리
final int adList		 		= 0b00000000000100000000000;	//광고 관리
final int almoneyList	 		= 0b00000000001000000000000;	//알머니 현황
final int levelUpReadyList		= 0b00000000010000000000000;	//회원레벨 관리 - 회원 레벨별 승천 대기자 현황
final int levelUpReadyList_new	= 0b00000000100000000000000;	//회원레벨 관리 - 신규 승천 시스템
final int notice				= 0b00000001000000000000000;	//공지 사항
final int reportList			= 0b00000010000000000000000;	//신고
final int blackList				= 0b00000100000000000000000;	//신고 - 블랙리스트 보기
final int storeInput			= 0b00001000000000000000000;	//알맹이 - 알맹이 입력
final int storePrepayment		= 0b00010000000000000000000;	//알맹이 - 선입금 관리
final int memberView			= 0b00100000000000000000000;	//회원정보 수정/탈퇴
final int memberViewLv			= 0b01000000000000000000000;	//회원정보 - 레벨 수정
final int memberViewAlmoney		= 0b10000000000000000000000;	//회원정보 - 알머니 지급
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Management</title>
<%
if(userSeq == 0) {
%>
<script>
	$(function(){
		alert('접근 권한이 없습니다.');
		location.href = '/default/main';
	});
</script>
<%
}
%>
</head>
