<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function onchange_search(){
		document.stat_form.submit();
	}
</script>
</head>
<body>
<c:set var="nYear" value="${nYear}"/>
<c:set var="nMonth" value="${nMonth}"/>
<%
	int nYear = Integer.parseInt( String.valueOf( pageContext.getAttribute("nYear") ) );
	int nMonth = Integer.parseInt( String.valueOf( pageContext.getAttribute("nMonth") ) );
%>

	<table width="100%">
	<form method="post" action="almoney_stat.asp" name="stat_form">
	<tr>
		<td width="10%">날짜</td>
		<td width="10%">
		<select name="ser_year" onchange="onchange_search()">
		<%for(int yy = nYear; yy >= 2017; yy--){ %>
			<option value="<%=yy%>"><%=yy%>년</option>
		<%} %>
		</select>
		</td>
		<td width="10%">
		<select name="ser_month"  onchange="onchange_search()">
		<%for(int mm = 1; mm <= 12; mm++){ 
			String mmv = String.valueOf(mm);
			
			if(mm < 10) {
				mmv = "0" + String.valueOf(mm);
			}
		%>
			<option value="<%=mmv%>" <%if(mmv.equals(nMonth)){ %>selected<%} %>><%=mm%>월</option>
		<%} %>
		</select>
		</td>
		<td></td>
	</tr>
	</form>
	</table>
 <table width="100%" border="1">
  <tr style="background-color:#c9c9c9">
	<td colspan="2">회원가입</td>
	<td colspan="2">질문</td>
	<td colspan="2">답변채택</td>
	<td colspan="2">답변수익</td>
	<td colspan="2">답변평가</td>
	<td colspan="2">기부금</td>

 </tr>
 <tr>
	<td>수</td>
	<td>알머니</td>

	<td>수</td>
	<td>알머니</td>

	<td>수</td>
	<td>알머니</td>

	<td>수</td>
	<td>알머니</td>

	<td>수</td>
	<td>알머니</td>

	<td>수</td>
	<td>알머니</td>
 </tr>
   <tr>
	<td><fmt:formatNumber value="${stat_cnt09}" pattern="#,###" /></td>
	<td><fmt:formatNumber value="${stat_sum09}" pattern="#,###.#" /></td>

	<td><fmt:formatNumber value="${stat_cnt05}" pattern="#,###" /></td>
	<td><fmt:formatNumber value="${stat_sum05}" pattern="#,###.#" /></td>

	<td><fmt:formatNumber value="${stat_cnt04}" pattern="#,###" /></td>
	<td><fmt:formatNumber value="${stat_sum04}" pattern="#,###.#" /></td>

	<td><fmt:formatNumber value="${stat_cnt07}" pattern="#,###" /></td>
	<td><fmt:formatNumber value="${stat_sum07}" pattern="#,###.#" /></td>


	<td><fmt:formatNumber value="${stat_cnt08}" pattern="#,###" /></td>
	<td><fmt:formatNumber value="${stat_sum08}" pattern="#,###.#" /></td>


	<td><fmt:formatNumber value="${stat_cnt01}" pattern="#,###" /></td>
	<td><fmt:formatNumber value="${stat_sum01}" pattern="#,###.#" /></td>
 </tr>
 <tr>
	<td colspan="12">&nbsp;</td>
 </tr>

  <tr style="background-color:#c9c9c9">
	<td colspan="2">추천인(답변)</td>
	<td colspan="2">추천인(질문)</td>
	<td colspan="2">질문열람</td>
	<td colspan="2">열람</td>
	<td colspan="2">낙전</td>
	<td colspan="2">회사적립</td>

 </tr>
 <tr>
	<td>수</td>
	<td>알머니</td>
	<td>수</td>
	<td>알머니</td>
	<td>수</td>
	<td>알머니</td>
	<td>수</td>
	<td>알머니</td>
	<td>수</td>
	<td>알머니</td>
	<td>수</td>
	<td>알머니</td>
 </tr>
   <tr>
	<td><fmt:formatNumber value="${stat_cnt03}" pattern="#,###" /></td>
	<td><fmt:formatNumber value="${stat_sum03}" pattern="#,###.#" /></td>

	<td><fmt:formatNumber value="${stat_cnt12}" pattern="#,###" /></td>
	<td><fmt:formatNumber value="${stat_sum12}" pattern="#,###.#" /></td>

	<td><fmt:formatNumber value="${stat_cnt10}" pattern="#,###" /></td>
	<td><fmt:formatNumber value="${stat_sum10}" pattern="#,###.#" /></td>

	<td><fmt:formatNumber value="${stat_cnt06}" pattern="#,###" /></td>
	<td><fmt:formatNumber value="${stat_sum06}" pattern="#,###.#" /></td>

	<td><fmt:formatNumber value="${stat_cnt11}" pattern="#,###" /></td>
	<td><fmt:formatNumber value="${stat_sum11}" pattern="#,###.#" /></td>

	<td><fmt:formatNumber value="${stat_cnt02}" pattern="#,###" /></td>
	<td><fmt:formatNumber value="${stat_sum02}" pattern="#,###.#" /></td>
 </tr>
 <tr>
	<td colspan="12">&nbsp;</td>
 </tr>
 </table>
</body>
</html>