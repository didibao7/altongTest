<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.altong.web.logic.util.CommonUtil"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<%!public String fnBank(String strData) {
	String tradeText = "";
	
	if(strData != "") {
		switch(strData) {
			case "Join":
				tradeText = "가입금";
				break;
			case "Question":
				tradeText = "질문비용";
				break;
			case "Answer":
				tradeText = "답변채택";
				break;
			case "View":
				tradeText = "열람";
				break;
			case "ViewQ":
				tradeText = "질문열람";
				break;
			case "ViewA":
				tradeText = "답변수익";
				break;
			case "Esti":
				tradeText = "답변평가";
				break;
			case "ViewRQ":
				tradeText = "추천인(질문)";
				break;
			case "ViewRA":
				tradeText = "추천인(답변)";
				break;
			case "Refund":
				tradeText = "질문 삭제 환급";
				break;
			case "Event":
				tradeText = "이벤트";
				break;
			case "Withdraw":
				tradeText = "이벤트 출금";
				break;
			case "Exchange":
				tradeText = "출금";
				break;
			case "ExchangeAlpay":
				tradeText = "알페이 충전";
				break;
			case "Answerer":
				tradeText = "Answerer";
				break;
			case "Answerer_Admin":
				tradeText = "Answerer";
				break;
			case "Loan":
				tradeText = "대출";
				break;
			case "1":
				tradeText = "꼭대기로";
				break;
			case "2":
				tradeText = "훈훈알";
				break;
			case "3":
				tradeText = "훈훈알";
				break;
			case "4":
				tradeText = "훈훈알";
				break;
			case "5":
				tradeText = "훈훈알";
				break;
			default:
				tradeText = "기타";
				break;
		}
	}
	
	return tradeText;
}

%>

<table width="100%" class="atm_bank_mylist0" cellpadding="0" cellspacing="0" bordercolor="#c8c8c8">
	<colgroup>
		<col width="20%"/>
		<col width="20%"/>
		<col width="20%"/>
		<col width="20%"/>
	</colgroup>
	<tr><td colspan="5" class="atm_bank_mylist_top" style="height:2px;"></td></tr>
	<tr>
		<td height="30" class="atm_bank_mylist1">일시</td>
		<td class="atm_bank_mylist1">적요</td>
		<td class="atm_bank_mylist1">수입 / 지출</td>
		<td class="atm_bank_mylist1">잔액</td>
	</tr>	

<c:if test="${myBankCnt != '0'}">
	<c:set var="prevDateReg" value=""/>
	<c:forEach var="bnk" items="${myBank}" varStatus="status">
		<fmt:formatNumber var="almoney" value="${bnk.almoney}" pattern="#,###.#" />
		<fmt:formatNumber var="balance" value="${bnk.balance}" pattern="#,###.#" />
		
		<c:set var="tradeType" value="${bnk.tradeType}"/>
		<%
			String tradeType = String.valueOf(pageContext.getAttribute("tradeType"));
		%>
		<c:if test="${bnk.regdate != prevDateReg}">
		<tr><td colspan="4" style="border: 1px solid #c8c8c8; background-color: #c8c8c8; height:2px;"></td></tr>
		</c:if>
		
	<c:if test="${status.index % 2 != 0}">
	<tr class="atm_bank_mylist_gray">
	</c:if>
	<c:if test="${status.index % 2 == 0}">
	<tr class="atm_bank_mylist">
	</c:if>
		<!-- 로그 내용 출력 시작 -->
		<!-- 일시 -->
		<td height="30" class="atm_bank_mylist2">${bnk.regdate}</td>
	
		<!-- 적요 -->
		<!-- [2017.12.15: 수정 - 김현구] 거래구분(ViewA: 답변수익, Esti: 답변평가, View: 답변열람, Answer: 답변채택)에 따른 처리 -->
		<!-- 거래구분(ViewRQ: 추천인(질문), ViewRA: 추천인(답변), Refund: 질문삭제환급, Event: 이벤트, Etc: 기타, Join: 가입금)에 따른 처리 -->
		<!-- [2017.12.26: 수정 - 차건환] 거래구분 추가(Withdraw: 출금) -->
	<c:choose>
		<c:when test="${bnk.tradeType == '4' || bnk.tradeType == '5'}">
		<td class="atm_bank_mylist2"><a href="/answer/answerList?ASeq=${bnk.contentsSeq}&devSeq=${bnk.contentsSeq}" target="_blank"><p class="atm_bank_c10"><u><%=fnBank(tradeType)%></u></p></a></td>
		</c:when>
		<c:when test="${bnk.tradeType == 'ViewA' || bnk.tradeType == 'Esti' || bnk.tradeType == 'View' || bnk.tradeType == 'Answer'}">
		<td class="atm_bank_mylist2"><a href="/answer/answerList?ASeq=${bnk.contentsSeq}" target="_blank"><p class="atm_bank_c10"><u><%=fnBank(tradeType)%></u></p></a></td>
		</c:when>
		<c:when test="${bnk.contentsSeq == '0' || bnk.tradeType == 'ViewRQ' || bnk.tradeType == 'ViewRA' || bnk.tradeType == 'Refund' || bnk.tradeType == 'Event' || bnk.tradeType == 'Etc' || bnk.tradeType == 'etc' || bnk.tradeType == 'Join' || bnk.tradeType == 'Withdraw' || bnk.tradeType == 'Answerer' || bnk.tradeType == 'Exchange' || bnk.tradeType == 'ExchangeAlpay' || bnk.tradeType == '31'}">
		<td class="atm_bank_mylist2"><p class="atm_bank_c10"><%=fnBank(tradeType)%></p></td>
		</c:when>
		<c:otherwise>
		<td class="atm_bank_mylist2"><a href="/answer/answerList?Seq=${bnk.contentsSeq}" target="_blank"><p class="atm_bank_c10"><u><%=fnBank(tradeType)%></u></p></a></td>
		</c:otherwise>
	</c:choose>
	
	<!-- 수입 / 지출  -->
	<c:set var="minusAlmoney" value="${bnk.almoney}"/>
	<c:if test="${bnk.almoney < 0}"><c:set var="minusAlmoney" value="${minusAlmoney * -1}"/></c:if>
	<c:choose>
		<c:when test="${bnk.almoney < 0 || bnk.tradeType == 'View' || bnk.tradeType == 'Question' || bnk.tradeType == 'Withdraw' || bnk.tradeType == 'Exchange' || bnk.tradeType == '1' || bnk.tradeType == '2' || bnk.tradeType == '3' || bnk.tradeType == 'ExchangeAlpay'}">
		<td class="atm_bank_mylist2" style="color: #f00; text-align: right; padding-right:5px;">- <fmt:formatNumber value="${minusAlmoney}" pattern="#,###.#" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		</c:when>
		<c:otherwise>
		<td class="atm_bank_mylist2" style="color: #00f; text-align: right; padding-right:5px;">+ <fmt:formatNumber value="${minusAlmoney}" pattern="#,###.#" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		</c:otherwise>
	</c:choose>
	
		<!-- 잔액 -->
		<td class="atm_bank_mylist2"><fmt:formatNumber value="${bnk.balance}" pattern="#,###.#" /></td>
	</tr>
	<c:set var="prevDateReg" value="${bnk.regdate}"/>
	</c:forEach>
</c:if>
<c:if test="${myBankCnt == '0'}">
	<tr class="atm_bank_mylist_gray"><td height="30" colspan="4" align="center">내용이 없습니다.${myBankCnt == '0'}</td></tr>
</c:if>
</table>
data:${listCount}