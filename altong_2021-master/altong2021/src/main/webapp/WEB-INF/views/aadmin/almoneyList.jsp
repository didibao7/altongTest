<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/Common/include/headerAdmin.jsp" %>
<body>
<form name="frm_sch" method="post" onSubmit="return false;">
<input type="hidden" name="src_Target" value="${curPageName}">
<input type="hidden" name="pg" value="${n_curpage}">

<div id="wrapper">
	<div id="header_wrapper">
	</div>
	<div id="M_wrapper">
	<%@ include file="/Common/include/menuAdmin.jsp" %>
		<br>
		<div class="board_input8">
			<table width="100%" border="0" align="center"  cellpadding="0" cellspacing="0">
				<tr>
					<td width="400">
						<p class="t_inputmenu" style="margin-top:15px;">알머니 적립/지출 현황 (테이블명 : LOG_ALMONEY / TradeType 필드 : ViewQ - 답변열람시 질문자에 지급, ViewA - 답변열람시 답변자에 지급, ViewC 는 회사에 적립되는 알머니)</p>
					</td>
				</tr>
			</table>
		</div>
		<div id="list">
			<p>전체 현황 : <fmt:formatNumber value="${n_trec}" pattern="#,###" /> 건</p>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="boardList01">
				<tr>
					<th width="10%">회원코드<br></th>
					<th width="10%">질문/답변 Seq</th>
					<th width="10%">구분</th>
					<th width="40%">내용</th>
					<th width="10%">알머니</th>
					<th width="20%">발생일자</th>
				</tr>
			<c:forEach var="a" items="${list}" varStatus="status">
				<tr style='cursor:pointer;' onmouseover='this.style.background="#FFE0B3";' onmouseleave='this.style.background="#FFFFFF";'>
					<td>${a.memberSeq}</td>
					<td>${a.contentsSeq}</td>
					<td>${a.tradeType}</td>
					<td>${a.contents}</td>
					<td><fmt:formatNumber value="${a.almoney}" pattern="#,###" /></td>
					<td>${a.regdate}</td>
				</tr>
			</c:forEach>
			</table>
			<p class="paging">
			<span class="num">
			<!-- paging -->
			${paging}
			<!-- paging// -->
			</span>
			</p>
			<br><br>
		</div>
	</div>
</div>
</form>
</body>