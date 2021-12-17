<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ page import="com.altong.web.logic.util.EncLibrary" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<%
EncLibrary enc = new EncLibrary();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/Common/CSS/jquery-ui.css">    
<script type="text/javascript" src="/Common/js/jquery.js"></script>    
<script type="text/javascript" src="/Common/js/jquery-ui.js"></script>    
<script>
function popClose() {
	location.reload();
	self.close();
}
</script>
</head>
<body>
<form name="frm_sch" method="post" onSubmit="return false;">
<input type="hidden" name="src_Target" value="${curPageName}">
<input type="hidden" name="pg" value="${n_curpage}">

<div id="wrapper">
	<div id="header_wrapper">
	</div>
	<div id="M_wrapper">
		<div class="board_input8">
			<table width="100%" border="0" align="center"  cellpadding="0" cellspacing="0">
				<tr>
					<td width="95%">
						<p class="t_inputmenu" style="margin-top:15px;">
						<label for="input01" id="label_input01">${quiz.step} 퀴즈 게임</label>
						</p>
					</td>
					<td>
						<button type="button" class="btn btn-warning btn-sm" onClick="javascript:void(0);">
						<c:choose>
							<c:when test="${quiz.complete == 'Y'}">상금알 지급완료</c:when>
							<c:when test="${quiz.complete == 'C'}">상금알 이월</c:when>
							<c:otherwise>지급전</c:otherwise>
						</c:choose>
						</button>
					</td>
				</tr>
			</table>
		</div>
		
		<c:set var="total" value="${quiz.admAlmoney + (quiz.attendAlmoney * reqLogCount) + quiz.carryoverMoney}"/>
		<c:set var="divPrice" value="0"/>
		<c:if test="${succssLogCount > 0}">
		<c:set var="divPrice" value="${total / succssLogCount}"/>
		</c:if>
		<table width="100%" border="0" align="center"  cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="3">총 상금알 : <fmt:formatNumber value="${total}" pattern="#,###" />			
				</td>
			</tr>
			<tr>
				<td>관리자 지급알 : <fmt:formatNumber value="${quiz.admAlmoney}" pattern="#,###" />	</td>
				<td>이월된 알: <fmt:formatNumber value="${quiz.carryoverMoney}" pattern="#,###" /></td>
				<td>총 참가알 : <fmt:formatNumber value="${quiz.attendAlmoney * reqLogCount}" pattern="#,###" /></td>
			</tr>
			<tr>
				<td>참가인원 : ${reqLogCount}</td>
				<td>우승인원 : ${succssLogCount}</td>
				<td>우승자 1인당 지급알 : <fmt:formatNumber value="${divPrice}" pattern="#,###" /></td>
			</tr>
		</table>

		<div id="list">
			<p><a href="/aadmin/quiz/getQuizWinner?uid=${uid}">퀴즈 우승자 : ${n_trec} 명</a> | 퀴즈 참가자</p>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="boardList01">
				<tr>
					<th width="">No</th>
					<th width="">회원번호</th>
					<th width="">닉네임</th>
					<th width="">이름</th>
					<th width="">회원유형</th>
					<th width="">레벨</th>
					<th width="">휴대폰번호</th>
				</tr>

			<c:if test="${n_trec > 0}">
			<c:forEach var="m" items="${quizLogList}" varStatus="status">
				<c:set var="phone_src" value="${m.phone}"/>
				<c:set var="mType_src" value="${m.memberType}"/>
				<c:set var="lv_src" value="${m.lv}"/>
				<%
					String phone_src = String.valueOf( pageContext.getAttribute("phone_src") );
					String mType_src = String.valueOf( pageContext.getAttribute("mType_src") );
					String lv_src = String.valueOf( pageContext.getAttribute("lv_src") );
					
					if(lv_src.equals("99")) {
						mType_src = "99";
					}
					
					String phone = enc.AlmoneyDecrypt(phone_src);
					String sMemberType = CommonUtil.fn_memberType(mType_src, request);
					String lv = CommonUtil.fn_Level(lv_src, request);
					
					pageContext.setAttribute("phone", phone);
					pageContext.setAttribute("sMemberType", sMemberType);
					pageContext.setAttribute("lv", lv);
				%>
				<tr style='cursor:pointer;' onmouseover='this.style.background="#FFE0B3";' onmouseleave='this.style.background="#FFFFFF";'>
					<td class="cssLinkMemberSeq"><fmt:formatNumber value="${(n_trec - status.index) - ( (n_curpage - 1)  *  n_pagesize ) }" pattern="#,###" /></td>
					<td>${m.userSeq}</td>
					<td>${m.nickName}</td>
					<td>${m.name}</td>
					<td>${sMemberType}</td>
					<td>${lv}</td>
					<td>${phone}</td>
				</tr>
                <tr>
                  <td colspan="9" style="margin:0; padding:0; height:1px; border-bottom: 1px solid #EAEAEA;"></td>
                </tr>
			</c:forEach>
			</c:if>
			</table>
			<p class="paging">
			<span class="num">
			<!-- paging -->
			${paging}
			<!-- paging// -->
			</span>
			</p>
			<c:if test="${quiz.updateDt != null}"><div style='float:right; color:blue;'>상금알 지급일시 : ${quiz.updateDt}</div></c:if>
			<br><br>
			
		</div>
	</div>
</div>
</form>

<div style="text-align:center;">
<button type="button" class="btn btn-warning btn-sm" onClick="javascript:popClose();">닫기</button>
</div>
</body>
</html>