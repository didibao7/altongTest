<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ include file="/Common/include/headerAdmin.jsp" %>
<%
CommonUtil.libIsAdmin(response, request, configInput, userSeq);
%>
<body>
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
						<p class="t_inputmenu" style="margin-top:15px;">기본 환경 설정 부분. (테이블명 : T_CONFIG)
						<%if(CommonUtil.libhasAuthority(configInput_new, userSeq)) {%> <a href="/aadmin/configInput_new">[신규 승천 시스템 환경 설정]</a> <%}%>
						</p>
					</td>
				</tr>
			</table>
		</div>
		<div id="list">
		<form name="frm_sch" method="post" onSubmit="return false;">
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"  class="rowboardList_top">
				<tr>
					<td width="20%" bgcolor="#efefef">회원가입시 지급 알머니</td>
					<td width="30%">
						<input type="text" name="AlmoneyJoin" value="${conf.almoneyJoin}" class="input_textarea" maxlength="10" style="width:100px;" onkeyup="checkNum(this);">
					</td>
					<td width="20%" bgcolor="#efefef">현금 인출 시 알머니 환전비율(현금 : 알머니)</td>
					<td width="30%"> 
						<input type="text" name="AlmoneyExchange" value="${conf.almoneyExchange}" class="input_textarea" maxlength="30" style="width:100px;" onkeyup="checkNum(this);">
					</td>
				</tr>
				<tr>
					<td bgcolor="#efefef">질문 등록시 베팅 최소 알머니</td>
					<td>
						<input type="text" name="QuestionMin" value="${conf.questionMin}" class="input_textarea" maxlength="10" style="width:100px;" onkeyup="checkNum(this);">
					</td>
					<td bgcolor="#efefef">질문 등록시 베팅 최대 알머니</td>
					<td>
						<input type="text" name="QuestionMax" value="${conf.questionMax}" class="input_textarea" maxlength="10" style="width:100px;" onkeyup="checkNum(this);">
					</td>
				</tr>
				<tr>
					<td bgcolor="#efefef">질문/답변 등록시 최대 입력 글자 수</td>
					<td>
						<input type="text" name="WriteMax" value="${conf.writeMax}" class="input_textarea" maxlength="10" style="width:100px;" onkeyup="checkNum(this);">
					</td>
					<td bgcolor="#efefef">질문/답변 등록시 최대 첨부파일 수</td>
					<td>
						<input type="text" name="FileMax" value="${conf.fileMax}" class="input_textarea" maxlength="10" style="width:100px;" onkeyup="checkNum(this);">
					</td>
				</tr>
				<tr>
					<td bgcolor="#efefef">답변 열람 비용</td>
					<td>
						<input type="text" name="ViewMoneySum" value="${conf.viewMoneySum}" class="input_textarea" maxlength="10" style="width:100px;" onkeyup="checkNum(this);">
					</td>
					<td bgcolor="#efefef">답변 열람시 지급되는 알머니</td>
					<td>
						질문자 : <input type="text" name="ViewMoneyQ" value="${conf.viewMoneyQ}" class="input_textarea" maxlength="10" style="width:50px;" onkeyup="checkNum(this);">
						답변자 : <input type="text" name="ViewMoneyA" value="${conf.viewMoneyA}" class="input_textarea" maxlength="10" style="width:50px;" onkeyup="checkNum(this);">
					</td>
				</tr>
				<tr>
					<td bgcolor="#efefef">알통수익 비용(질문시 회수비용)</td>
					<td colspan="3">
						<input type="text" name="MoneyCompany" value="${conf.moneyCompany}" class="input_textarea" maxlength="10" style="width:100px;" onkeyup="checkNum(this);">
					</td>
				</tr>
				<tr>
					<td bgcolor="#efefef">회원가입 인증 기준</td>
					<td colspan="3">
						<label for="select[name=MemJoinCertType]">인증 방식</label>
						<select name="MemJoinCertType">
						
						<c:forEach var="i" begin="1" end="${fn:length(codeCd)}" step="1">
						<c:set var="ni" value="${i}"/>
						<%
							String stri = String.valueOf( pageContext.getAttribute("ni") );
							pageContext.setAttribute("stri", stri);
						%>
						<option value="${codeCd.get(stri)}" <c:if test="${conf.memJoinCertType == codeCd.get(stri)}">selected</c:if>>${codeNm.get(stri)}</option>
						</c:forEach>
						</select>
						&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;
						<label for="select[name=MemJoinSmsLimitCnt]">SMS 발송 횟수(1일)</label>
						<select name="MemJoinSmsLimitCnt">
						<c:forEach var="i" begin="1" end="10" step="1">
						<option value="${i}" <c:if test="${conf.memJoinSmsLimitCnt == i}">selected</c:if>>${i}회</option>
						</c:forEach>
						<c:forEach var="i" begin="15" end="60" step="5">
						<option value="${i}" <c:if test="${conf.memJoinSmsLimitCnt == i}">selected</c:if>>${i}회</option>
						</c:forEach>
						</select>
						&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;
						<label for="select[name=MemJoinSmsTimeOut]">인증코드 유효 시간</label>
						<select name="MemJoinSmsTimeOut">
						<c:forEach var="i" begin="1" end="10" step="1">
						<option value="${i * 60}" <c:if test="${conf.memJoinSmsTimeOut == (i * 60)}">selected</c:if>>${i}회</option>
						</c:forEach>
						<c:forEach var="i" begin="15" end="60" step="5">
						<option value="${i * 60}" <c:if test="${conf.memJoinSmsTimeOut == (i * 60)}">selected</c:if>>${i}회</option>
						</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td bgcolor="#efefef">비밀번호 찾기 인증 기준</td>
					<td colspan="3">
						<label for="select[name=MemPwSmsLimitCnt]">SMS 발송 횟수(1일)</label>
						<select name="MemPwSmsLimitCnt">
						<c:forEach var="i" begin="1" end="10" step="1">
						<option value="${i}" <c:if test="${conf.memPwSmsLimitCnt == (i * 60)}">selected</c:if>>${i}회</option>
						</c:forEach>
						<c:forEach var="i" begin="15" end="60" step="5">
						<option value="${i}" <c:if test="${conf.memPwSmsLimitCnt == (i * 60)}">selected</c:if>>${i}회</option>
						</c:forEach>
						</select>
					</td>
				</tr>

                <!-- [추가(2018.03.14, 2018.02.26): 김현구] 회원 레벨별 글쓰기 제한 기준 설정 -->
                <tr>
                  <td bgcolor="#efefef">회원 레벨별 글쓰기 제한 기준</th>
                  <td colspan="3" style="padding-top: 20px; padding-bottom: 10px;">
                    <style type="text/css">
                    .cssFormFieldTable_Ul li {
                        display: table-cell;
                        width: 400px;
                    }
                    .cssFormFieldLabel {
                        font-size: 13px;
                    }
                    .cssFormFieldLabel_P {
                        padding: 5px 0px 3px;
                        font-size: 13px;
                        margin: 0;
                    }
                    .cssDivRowGap {
                        height: 10px;
                    }
                    </style>

                    <ul class="cssFormFieldTable_Ul">
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">1레벨(알천사) 글쓰기 제한</span></td>
                          </tr>
                          <tr>
                            <td width="170" class="cssFormFieldLabel">질문 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv1_LimitQueDayRegistCnt" value="${conf.lv1_LimitQueDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv1_LimitQueDayDupRegistCnt" value="${conf.lv1_LimitQueDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 연속등록 제한시간</td>
                            <td>
                              <select name="Lv1_LimitQueContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv1_LimitQueContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv1_LimitQueContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv1_LimitQueContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv1_LimitQueContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv1_LimitAnsDayRegistCnt" value="${conf.lv1_LimitAnsDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv1_LimitAnsDayDupRegistCnt" value="${conf.lv1_LimitAnsDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 연속등록 제한시간</td>
                            <td>
                              <select name="Lv1_LimitAnsContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv1_LimitAnsContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv1_LimitAnsContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv1_LimitAnsContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv1_LimitAnsContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv1_LimitRepDayRegistCnt" value="${conf.lv1_LimitRepDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv1_LimitRepDayDupRegistCnt" value="${conf.lv1_LimitRepDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 연속등록 제한시간</td>
                            <td>
                              <select name="Lv1_LimitRepContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv1_LimitRepContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv1_LimitRepContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv1_LimitRepContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv1_LimitRepContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                        </table>
                      </li>
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">2레벨(나비천사) 글쓰기 제한</span></td>
                          </tr>
                          <tr>
                            <td width="170" class="cssFormFieldLabel">질문 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv2_LimitQueDayRegistCnt" value="${conf.lv2_LimitQueDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv2_LimitQueDayDupRegistCnt" value="${conf.lv2_LimitQueDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 연속등록 제한시간</td>
                            <td>
                              <select name="Lv2_LimitQueContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv2_LimitQueContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv2_LimitQueContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv2_LimitQueContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv2_LimitQueContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv2_LimitAnsDayRegistCnt" value="${conf.lv2_LimitAnsDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv2_LimitAnsDayDupRegistCnt" value="${conf.lv2_LimitAnsDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 연속등록 제한시간</td>
                            <td>
                              <select name="Lv2_LimitAnsContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv2_LimitAnsContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv2_LimitAnsContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv2_LimitAnsContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv2_LimitAnsContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv2_LimitRepDayRegistCnt" value="${conf.lv2_LimitRepDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv2_LimitRepDayDupRegistCnt" value="${conf.lv2_LimitRepDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 연속등록 제한시간</td>
                            <td>
                              <select name="Lv2_LimitRepContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv2_LimitRepContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv2_LimitRepContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv2_LimitRepContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv2_LimitRepContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                        </table>
                      </li>
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">3레벨(미소천사) 글쓰기 제한</span></td>
                          </tr>
                          <tr>
                            <td width="170" class="cssFormFieldLabel">질문 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv3_LimitQueDayRegistCnt" value="${conf.lv3_LimitQueDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv3_LimitQueDayDupRegistCnt" value="${conf.lv3_LimitQueDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 연속등록 제한시간</td>
                            <td>
                              <select name="Lv3_LimitQueContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv3_LimitQueContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv3_LimitQueContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv3_LimitQueContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv3_LimitQueContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv3_LimitAnsDayRegistCnt" value="${conf.lv3_LimitAnsDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv3_LimitAnsDayDupRegistCnt" value="${conf.lv3_LimitAnsDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 연속등록 제한시간</td>
                            <td>
                              <select name="Lv3_LimitAnsContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv3_LimitAnsContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv3_LimitAnsContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv3_LimitAnsContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv3_LimitAnsContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv3_LimitRepDayRegistCnt" value="${conf.lv3_LimitRepDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv3_LimitRepDayDupRegistCnt" value="${conf.lv3_LimitRepDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 연속등록 제한시간</td>
                            <td>
                              <select name="Lv3_LimitRepContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv3_LimitRepContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv3_LimitRepContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv3_LimitRepContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv3_LimitRepContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                        </table>
                      </li>
                    </ul>
                    <div class="cssDivRowGap"></div>

                    <ul class="cssFormFieldTable_Ul">
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">4레벨(열혈천사) 글쓰기 제한</span></td>
                          </tr>
                          <tr>
                            <td width="170" class="cssFormFieldLabel">질문 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv4_LimitQueDayRegistCnt" value="${conf.lv4_LimitQueDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv4_LimitQueDayDupRegistCnt" value="${conf.lv4_LimitQueDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 연속등록 제한시간</td>
                            <td>
                              <select name="Lv4_LimitQueContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv4_LimitQueContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv4_LimitQueContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv4_LimitQueContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv4_LimitQueContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv4_LimitAnsDayRegistCnt" value="${conf.lv4_LimitAnsDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv4_LimitAnsDayDupRegistCnt" value="${conf.lv4_LimitAnsDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 연속등록 제한시간</td>
                            <td>
                              <select name="Lv4_LimitAnsContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv4_LimitAnsContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv4_LimitAnsContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv4_LimitAnsContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv4_LimitAnsContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv4_LimitRepDayRegistCnt" value="${conf.lv4_LimitRepDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv4_LimitRepDayDupRegistCnt" value="${conf.lv4_LimitRepDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 연속등록 제한시간</td>
                            <td>
                              <select name="Lv4_LimitRepContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv4_LimitRepContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv4_LimitRepContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv4_LimitRepContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv4_LimitRepContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                        </table>
                      </li>
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">5레벨(${codeMemLvNm.get("5")}) 글쓰기 제한</span></td>
                          </tr>
                          <tr>
                            <td width="170" class="cssFormFieldLabel">질문 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv5_LimitQueDayRegistCnt" value="${conf.lv5_LimitQueDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv5_LimitQueDayDupRegistCnt" value="${conf.lv5_LimitQueDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 연속등록 제한시간</td>
                            <td>
                              <select name="Lv5_LimitQueContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv5_LimitQueContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv5_LimitQueContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv5_LimitQueContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv5_LimitQueContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv5_LimitAnsDayRegistCnt" value="${conf.lv5_LimitAnsDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv5_LimitAnsDayDupRegistCnt" value="${conf.lv5_LimitAnsDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 연속등록 제한시간</td>
                            <td>
                              <select name="Lv5_LimitAnsContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv5_LimitAnsContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv5_LimitAnsContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv5_LimitAnsContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv5_LimitAnsContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv5_LimitRepDayRegistCnt" value="${conf.lv5_LimitRepDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv5_LimitRepDayDupRegistCnt" value="${conf.lv5_LimitRepDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 연속등록 제한시간</td>
                            <td>
                              <select name="Lv5_LimitRepContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv5_LimitRepContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv5_LimitRepContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv5_LimitRepContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv5_LimitRepContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                        </table>
                      </li>
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">6레벨(${codeMemLvNm.get("6")}) 글쓰기 제한</span></td>
                          </tr>
                          <tr>
                            <td width="170" class="cssFormFieldLabel">질문 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv6_LimitQueDayRegistCnt" value="${conf.lv6_LimitQueDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv6_LimitQueDayDupRegistCnt" value="${conf.lv6_LimitQueDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 연속등록 제한시간</td>
                            <td>
                              <select name="Lv6_LimitQueContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv6_LimitQueContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv6_LimitQueContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv6_LimitQueContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv6_LimitQueContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv6_LimitAnsDayRegistCnt" value="${conf.lv6_LimitAnsDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv6_LimitAnsDayDupRegistCnt" value="${conf.lv6_LimitAnsDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 연속등록 제한시간</td>
                            <td>
                              <select name="Lv6_LimitAnsContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv6_LimitAnsContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv6_LimitAnsContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv6_LimitAnsContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv6_LimitAnsContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv6_LimitRepDayRegistCnt" value="${conf.lv6_LimitRepDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv6_LimitRepDayDupRegistCnt" value="${conf.lv6_LimitRepDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 연속등록 제한시간</td>
                            <td>
                              <select name="Lv6_LimitRepContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv6_LimitRepContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv6_LimitRepContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv6_LimitRepContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv6_LimitRepContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                        </table>
                      </li>
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">7레벨(${codeMemLvNm.get("7")}) 글쓰기 제한</span></td>
                          </tr>
                          <tr>
                            <td width="170" class="cssFormFieldLabel">질문 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv7_LimitQueDayRegistCnt" value="${conf.lv7_LimitQueDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv7_LimitQueDayDupRegistCnt" value="${conf.lv7_LimitQueDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 연속등록 제한시간</td>
                            <td>
                              <select name="Lv7_LimitQueContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv7_LimitQueContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv7_LimitQueContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv7_LimitQueContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv7_LimitQueContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv7_LimitAnsDayRegistCnt" value="${conf.lv7_LimitAnsDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv7_LimitAnsDayDupRegistCnt" value="${conf.lv7_LimitAnsDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 연속등록 제한시간</td>
                            <td>
                              <select name="Lv7_LimitAnsContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv7_LimitAnsContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv7_LimitAnsContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv7_LimitAnsContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv7_LimitAnsContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv7_LimitRepDayRegistCnt" value="${conf.lv7_LimitRepDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv7_LimitRepDayDupRegistCnt" value="${conf.lv7_LimitRepDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 연속등록 제한시간</td>
                            <td>
                              <select name="Lv7_LimitRepContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv7_LimitRepContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv7_LimitRepContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv7_LimitRepContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv7_LimitRepContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                        </table>
                      </li>
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">8레벨(${codeMemLvNm.get("8")}) 글쓰기 제한</span></td>
                          </tr>
                          <tr>
                            <td width="170" class="cssFormFieldLabel">질문 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv8_LimitQueDayRegistCnt" value="${conf.lv8_LimitQueDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv8_LimitQueDayDupRegistCnt" value="${conf.lv8_LimitQueDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 연속등록 제한시간</td>
                            <td>
                              <select name="Lv8_LimitQueContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv8_LimitQueContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv8_LimitQueContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv8_LimitQueContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv8_LimitQueContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv8_LimitAnsDayRegistCnt" value="${conf.lv8_LimitAnsDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv8_LimitAnsDayDupRegistCnt" value="${conf.lv8_LimitAnsDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 연속등록 제한시간</td>
                            <td>
                              <select name="Lv8_LimitAnsContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv8_LimitAnsContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv8_LimitAnsContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv8_LimitAnsContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv8_LimitAnsContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv8_LimitRepDayRegistCnt" value="${conf.lv8_LimitRepDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv8_LimitRepDayDupRegistCnt" value="${conf.lv8_LimitRepDayRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 연속등록 제한시간</td>
                            <td>
                              <select name="Lv8_LimitRepContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv8_LimitRepContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv8_LimitRepContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv8_LimitRepContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv8_LimitRepContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                        </table>
                      </li>
					  </ul>
					  <div class="cssDivRowGap"></div>

                    <ul class="cssFormFieldTable_Ul">
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">9레벨(${codeMemLvNm.get("9")}) 글쓰기 제한</span></td>
                          </tr>
                          <tr>
                            <td width="170" class="cssFormFieldLabel">질문 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv9_LimitQueDayRegistCnt" value="${conf.lv9_LimitQueDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv9_LimitQueDayDupRegistCnt" value="${conf.lv9_LimitQueDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 연속등록 제한시간</td>
                            <td>
                              <select name="Lv9_LimitQueContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv9_LimitQueContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv9_LimitQueContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv9_LimitQueContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv9_LimitQueContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv9_LimitAnsDayRegistCnt" value="${conf.lv9_LimitAnsDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv9_LimitAnsDayDupRegistCnt" value="${conf.lv9_LimitAnsDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 연속등록 제한시간</td>
                            <td>
                              <select name="Lv9_LimitAnsContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv9_LimitAnsContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv9_LimitAnsContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv9_LimitAnsContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv9_LimitAnsContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv9_LimitRepDayRegistCnt" value="${conf.lv9_LimitRepDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv9_LimitRepDayDupRegistCnt" value="${conf.lv9_LimitRepDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 연속등록 제한시간</td>
                            <td>
                              <select name="Lv9_LimitRepContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv9_LimitRepContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv9_LimitRepContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv9_LimitRepContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv9_LimitRepContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                        </table>
                      </li>
					  <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">10레벨(${codeMemLvNm.get("10")}) 글쓰기 제한</span></td>
                          </tr>
                          <tr>
                            <td width="170" class="cssFormFieldLabel">질문 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv10_LimitQueDayRegistCnt" value="${conf.lv10_LimitQueDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv10_LimitQueDayDupRegistCnt" value="${conf.lv10_LimitQueDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 연속등록 제한시간</td>
                            <td>
                              <select name="Lv10_LimitQueContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv10_LimitQueContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv10_LimitQueContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv10_LimitQueContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv10_LimitQueContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv10_LimitAnsDayRegistCnt" value="${conf.lv10_LimitAnsDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv10_LimitAnsDayDupRegistCnt" value="${conf.lv10_LimitAnsDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 연속등록 제한시간</td>
                            <td>
                              <select name="Lv10_LimitAnsContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv10_LimitAnsContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv10_LimitAnsContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv10_LimitAnsContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv10_LimitAnsContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv10_LimitRepDayRegistCnt" value="${conf.lv10_LimitRepDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv10_LimitRepDayDupRegistCnt" value="${conf.lv10_LimitRepDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 연속등록 제한시간</td>
                            <td>
                              <select name="Lv10_LimitRepContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv10_LimitRepContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv10_LimitRepContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv10_LimitRepContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv10_LimitRepContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                        </table>
                      </li>
					  <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">11레벨(${codeMemLvNm.get("11")}) 글쓰기 제한</span></td>
                          </tr>
                          <tr>
                            <td width="170" class="cssFormFieldLabel">질문 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv11_LimitQueDayRegistCnt" value="${conf.lv11_LimitQueDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv11_LimitQueDayDupRegistCnt" value="${conf.lv11_LimitQueDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 연속등록 제한시간</td>
                            <td>
                              <select name="Lv11_LimitQueContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv11_LimitQueContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv11_LimitQueContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv11_LimitQueContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv11_LimitQueContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv11_LimitAnsDayRegistCnt" value="${conf.lv11_LimitAnsDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv11_LimitAnsDayDupRegistCnt" value="${conf.lv11_LimitAnsDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 연속등록 제한시간</td>
                            <td>
                              <select name="Lv11_LimitAnsContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv11_LimitAnsContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv11_LimitAnsContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv11_LimitAnsContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv11_LimitAnsContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 등록건수 제한 (1일)</td>
                            <td><input type="text" name="Lv11_LimitRepDayRegistCnt" value="${conf.lv11_LimitRepDayRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 중복등록 제한 (1일)</td>
                            <td><input type="text" name="Lv11_LimitRepDayDupRegistCnt" value="${conf.lv11_LimitRepDayDupRegistCnt}" maxlength="10" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 연속등록 제한시간</td>
                            <td>
                              <select name="Lv11_LimitRepContinueRegistTime">
                                <option value="0">--</option>
                                <%for(int i = 5; i <= 55; i += 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i}" <c:if test="${conf.lv11_LimitRepContinueRegistTime == i}">SELECTED</c:if>>${fn:substring(iStr, fn:length(iStr) - 2, fn:length(iStr) )}초</option>
                                <%}%>
                                <%for(int i = 1; i <= 9; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv11_LimitRepContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 10; i <= 55; i+= 5){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60}" <c:if test="${conf.lv11_LimitRepContinueRegistTime == (i * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                                <%for(int i = 1; i <= 23; i++){
                                	String iStr = String.valueOf( 100 + i);
                                	pageContext.setAttribute("iStr", iStr);
                                	pageContext.setAttribute("i", i);
                                %>
                                <option value="${i * 60 * 60}" <c:if test="${conf.lv11_LimitRepContinueRegistTime == (i * 60 * 60)}">SELECTED</c:if>>${fn:substring( iStr, fn:length(iStr) - 2, fn:length(iStr))}분</option>
                                <%}%>
                              </select>
                            </td>
                          </tr>
                        </table>
                      </li>

                      
					  </ul>
					



                  </td>
                </tr>

                <!-- [추가(2018.02.26): 김현구] 회원 레벨별 승천 조건 설정 -->
                <tr>
                  <td bgcolor="#efefef">회원 레벨별 승천 조건</th>
                  <td colspan="3" style="padding-top: 20px; padding-bottom: 10px;">
                    <script language="javascript">
                      function fnLevelUpAuto_Click(flag) {
                          var form = document.frm_sch;
                          if ( flag == "Y" ) {
                               for ( var i=2; i <= 3; i++ ) {
                                     eval("form.Lv" + i + "_LevelUpAuto_Yn.value = 'Y'");
                                     eval("form.Lv" + i + "_LevelUpAuto_Yn.disabled = false");
                               }
                          }
                          else {
                               for ( var i=2; i <= 3; i++ ) {
                                     eval("form.Lv" + i + "_LevelUpAuto_Yn.value = 'N'");
                                     eval("form.Lv" + i + "_LevelUpAuto_Yn.disabled = true");
                               }
                          }
                      }
                    </script> 

                    <div style="margin-bottom: 20px;">
                      <span style="font-weight:bold;">자동 승천 적용 여부</span> : &nbsp;
                      <input type="radio" name="Lv_LevelUpAuto_Yn" value="Y" onClick="fnLevelUpAuto_Click('Y')" <c:if test="${conf.lv_LevelUpAuto_Yn == 'Y'}">CHECKED</c:if>>적용 &nbsp;&nbsp;
                      <input type="radio" name="Lv_LevelUpAuto_Yn" value="N" onClick="fnLevelUpAuto_Click('N')" <c:if test="${conf.lv_LevelUpAuto_Yn != 'Y'}">CHECKED</c:if>>미적용
                    </div>

                    <ul class="cssFormFieldTable_Ul">
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6">
                              <span style="font-weight:bold;">2레벨 승천 조건 (알천사 -> 나비천사)</span>
                              <p class="cssFormFieldLabel_P">
                                ( 자동 승천</span>&nbsp;
                                <select name="Lv2_LevelUpAuto_Yn" <c:if test="${conf.lv_LevelUpAuto_Yn != 'Y'}">DISABLED</c:if>>
                                  <option value="Y" <c:if test="${conf.lv2_LevelUpAuto_Yn == 'Y'}">CHECKED</c:if>>적용</option>
                                  <option value="N" <c:if test="${conf.lv2_LevelUpAuto_Yn != 'Y'}">CHECKED</c:if>>미적용</option>
                                </select> )
                              </p>
                            </td>
                          </tr>
                          <tr>
                            <td width="170" class="cssFormFieldLabel">회원 가입 후 경과일</td>
                            <td>
                                <select name="Lv2_LimitJoinAfterDay">
                                  <option value="0">--</option>
                                  <c:forEach var="i" begin="1" end="12" step="1">
                               		<option value="${i}" <c:if test="${conf.lv2_LimitJoinAfterDay == i}">SELECTED</c:if>>${i}</option>
                                  </c:forEach>
                                </select> 개월
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">알머니 잔고 금액</td>
                            <td><input type="text" name="Lv2_LimitAlmoney" value="${conf.lv2_LimitAlmoney}" maxlength="10" class="input_textarea" style="width:100px;" onkeyup="checkNum(this);"> 알</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 평가 건수</td>
                            <td><input type="text" name="Lv2_LimitAnsEstiCnt" value="${conf.lv2_LimitAnsEstiCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">프로필 사진 등록</td>
                            <td>
                              <select name="Lv2_LimitPhotoYn">
                                <option value="Y" <c:if test="${conf.lv2_LimitPhotoYn == 'Y'}">CHECKED</c:if>>필수</option>
                                <option value="N" <c:if test="${conf.lv2_LimitPhotoYn == 'N'}">CHECKED</c:if>>필수아님</option>
                              </select>
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 건수</td>
                            <td><input type="text" name="Lv2_LimitReplyCnt" value="${conf.lv2_LimitReplyCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">추천인수</td>
                            <td><input type="text" name="Lv2_LimitChuMemCnt" value="${conf.lv2_LimitChuMemCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 명</td>
                          </tr>
                        </table>
                      </li>
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6">
                              <span style="font-weight:bold;">3레벨 승천 조건 (나비천사 -> 미소천사)</span>
                              <p class="cssFormFieldLabel_P">
                                ( 자동 승천</span>&nbsp;
                                <select name="Lv3_LevelUpAuto_Yn" <c:if test="${conf.lv_LevelUpAuto_Yn != 'Y'}">DISABLED</c:if>>
                                  <option value="Y" <c:if test="${conf.lv3_LevelUpAuto_Yn == 'Y'}">SELECTED</c:if>>적용</option>
                                  <option value="N" <c:if test="${conf.lv3_LevelUpAuto_Yn != 'Y'}">SELECTED</c:if>>미적용</option>
                                </select> )
                              </p>
                            </td>
                          </tr>
                          <tr>
                            <td width="200" class="cssFormFieldLabel">레벨 유지 기간<br>(조건 충족 필요 기간수)</td>
                            <td>
                                <select name="Lv3_LimitLvMtPeriod">
                                  <option value="0">--</option>
                                  <c:forEach var="i" begin="1" end="12" step="1">
                               		<option value="${i}" <c:if test="${conf.lv3_LimitLvMtPeriod == i}">SELECTED</c:if>>${i}</option>
                                  </c:forEach>
                                </select> 개월
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 등록 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv3_LimitQueRegistCnt" value="${conf.lv3_LimitQueRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv3_LimitAnsRegistCnt" value="${conf.lv3_LimitAnsRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 피채택 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv3_LimitAnsChoiceCnt" value="${conf.lv3_LimitAnsChoiceCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 평가 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv3_LimitAnsEstiCnt" value="${conf.lv3_LimitAnsEstiCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">네티즌 평가 점수 (승천 후 월)</td>
                            <td><input type="text" name="Lv3_LimitAnsEstiPoint" value="${conf.lv3_LimitAnsEstiPoint}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 점</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 건수</td>
                            <td><input type="text" name="Lv3_LimitReplyCnt" value="${conf.lv3_LimitReplyCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">추천인수 (누적)</td>
                            <td><input type="text" name="Lv3_LimitChuMemCnt" value="${conf.lv3_LimitChuMemCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 명</td>
                          </tr>
                        </table>
                      </li>
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6">
                              <span style="font-weight:bold;">4레벨 승천 조건 (미소천사 -> 열혈천사)</span>
                              <p class="cssFormFieldLabel_P">
                                ( 자동 승천</span>&nbsp;
                                <select name="Lv4_LevelUpAuto_Yn" <c:if test="${conf.lv_LevelUpAuto_Yn != 'Y'}">DISABLED</c:if>>
                                  <option value="Y" <c:if test="${conf.lv4_LevelUpAuto_Yn == 'Y'}">SELECTED</c:if>>적용</option>
                                  <option value="N" <c:if test="${conf.lv4_LevelUpAuto_Yn != 'Y'}">SELECTED</c:if>>미적용</option>
                                </select> )
                              </p>
                            </td>
                          </tr>
                          <tr>
                            <td width="200" class="cssFormFieldLabel">레벨 유지 기간<br>(조건 충족 필요 기간수)</td>
                            <td>
                                <select name="Lv4_LimitLvMtPeriod">
                                  <option value="0">--</option>
                                  <c:forEach var="i" begin="1" end="12" step="1">
                               		<option value="${i}" <c:if test="${conf.lv4_LimitLvMtPeriod == i}">SELECTED</c:if>>${i}</option>
                                  </c:forEach>
                                </select> 개월
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 등록 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv4_LimitQueRegistCnt" value="${conf.lv4_LimitQueRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv4_LimitAnsRegistCnt" value="${conf.lv4_LimitAnsRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 피채택 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv4_LimitAnsChoiceCnt" value="${conf.lv4_LimitAnsChoiceCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 평가 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv4_LimitAnsEstiCnt" value="${conf.lv4_LimitAnsEstiCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">네티즌 평가 점수 (승천 후 월)</td>
                            <td><input type="text" name="Lv4_LimitAnsEstiPoint" value="${conf.lv4_LimitAnsEstiPoint}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 점</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 건수</td>
                            <td><input type="text" name="Lv4_LimitReplyCnt" value="${conf.lv4_LimitReplyCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">추천인수 (누적)</td>
                            <td><input type="text" name="Lv4_LimitChuMemCnt" value="${conf.lv4_LimitChuMemCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 명</td>
                          </tr>
                        </table>
                      </li>
                    </ul>
                    <div class="cssDivRowGap"></div>

                    <ul class="cssFormFieldTable_Ul">
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6">
                              <span style="font-weight:bold;">5레벨 승천 조건 (${codeMemLvNm.get("4")} -> ${codeMemLvNm.get("5")})</span>
                              <p class="cssFormFieldLabel_P">
                                ( 자동 승천</span>&nbsp;
                                <select name="Lv5_LevelUpAuto_Yn" <c:if test="${conf.lv_LevelUpAuto_Yn != 'Y'}">DISABLED</c:if>>
                                  <option value="Y" <c:if test="${conf.lv5_LevelUpAuto_Yn == 'Y'}">SELECTED</c:if>>적용</option>
                                  <option value="N" <c:if test="${conf.lv5_LevelUpAuto_Yn != 'Y'}">SELECTED</c:if>>미적용</option>
                                </select> )
                              </p>
                            </td>
                          </tr>
                          <tr>
                            <td width="200" class="cssFormFieldLabel">레벨 유지 기간<br>(조건 충족 필요 기간수)</td>
                            <td>
                                <select name="Lv5_LimitLvMtPeriod">
                                  <option value="0">--</option>
                                  <c:forEach var="i" begin="1" end="12" step="1">
                               		<option value="${i}" <c:if test="${conf.lv5_LimitLvMtPeriod == i}">SELECTED</c:if>>${i}</option>
                                  </c:forEach>
                                </select> 개월
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 등록 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv5_LimitQueRegistCnt" value="${conf.lv5_LimitQueRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv5_LimitAnsRegistCnt" value="${conf.lv5_LimitAnsRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 피채택 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv5_LimitAnsChoiceCnt" value="${conf.lv5_LimitAnsChoiceCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 평가 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv5_LimitAnsEstiCnt" value="${conf.lv5_LimitAnsEstiCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">네티즌 평가 점수 (승천 후 월)</td>
                            <td><input type="text" name="Lv5_LimitAnsEstiPoint" value="${conf.lv5_LimitAnsEstiPoint}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 점</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 건수</td>
                            <td><input type="text" name="Lv5_LimitReplyCnt" value="${conf.lv5_LimitReplyCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">추천인수 (누적)</td>
                            <td><input type="text" name="Lv5_LimitChuMemCnt" value="${conf.lv5_LimitChuMemCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 명</td>
                          </tr>
                        </table>
                      </li>
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6">
                              <span style="font-weight:bold;">6레벨 승천 조건 (${codeMemLvNm.get("5")} -> ${codeMemLvNm.get("6")})</span>
                              <p class="cssFormFieldLabel_P">
                                ( 자동 승천</span>&nbsp;
                                <select name="Lv6_LevelUpAuto_Yn" <c:if test="${conf.lv_LevelUpAuto_Yn != 'Y'}">DISABLED</c:if>>
                                  <option value="Y" <c:if test="${conf.lv6_LevelUpAuto_Yn == 'Y'}">SELECTED</c:if>>적용</option>
                                  <option value="N" <c:if test="${conf.lv6_LevelUpAuto_Yn != 'Y'}">SELECTED</c:if>>미적용</option>
                                </select> )
                              </p>
                            </td>
                          </tr>
                          <tr>
                            <td width="200" class="cssFormFieldLabel">레벨 유지 기간<br>(조건 충족 필요 기간수)</td>
                            <td>
                                <select name="Lv6_LimitLvMtPeriod">
                                  <option value="0">--</option>
                                  <c:forEach var="i" begin="1" end="12" step="1">
                               		<option value="${i}" <c:if test="${conf.lv6_LimitLvMtPeriod == i}">SELECTED</c:if>>${i}</option>
                                  </c:forEach>
                                </select> 개월
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 등록 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv6_LimitQueRegistCnt" value="${conf.lv6_LimitQueRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv6_LimitAnsRegistCnt" value="${conf.lv6_LimitAnsRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 피채택 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv6_LimitAnsChoiceCnt" value="${conf.lv6_LimitAnsChoiceCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 평가 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv6_LimitAnsEstiCnt" value="${conf.lv6_LimitAnsEstiCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">네티즌 평가 점수 (승천 후 월)</td>
                            <td><input type="text" name="Lv6_LimitAnsEstiPoint" value="${conf.lv6_LimitAnsEstiPoint}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 점</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 건수</td>
                            <td><input type="text" name="Lv6_LimitReplyCnt" value="${conf.lv6_LimitReplyCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">추천인수 (누적)</td>
                            <td><input type="text" name="Lv6_LimitChuMemCnt" value="${conf.lv6_LimitChuMemCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 명</td>
                          </tr>
                        </table>
                      </li>
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6">
                              <span style="font-weight:bold;">7레벨 승천 조건 (${codeMemLvNm.get("6")} -> ${codeMemLvNm.get("7")})</span>
                              <p class="cssFormFieldLabel_P">
                                ( 자동 승천</span>&nbsp;
                                <select name="Lv7_LevelUpAuto_Yn" <c:if test="${conf.lv_LevelUpAuto_Yn != 'Y'}">DISABLED</c:if>>
                                  <option value="Y" <c:if test="${conf.lv7_LevelUpAuto_Yn == 'Y'}">SELECTED</c:if>>적용</option>
                                  <option value="N" <c:if test="${conf.lv7_LevelUpAuto_Yn != 'Y'}">SELECTED</c:if>>미적용</option>
                                </select> )
                              </p>
                            </td>
                          </tr>
                          <tr>
                            <td width="200" class="cssFormFieldLabel">레벨 유지 기간<br>(조건 충족 필요 기간수)</td>
                            <td>
                                <select name="Lv7_LimitLvMtPeriod">
                                  <option value="0">--</option>
                                  <c:forEach var="i" begin="1" end="12" step="1">
                               		<option value="${i}" <c:if test="${conf.lv7_LimitLvMtPeriod == i}">SELECTED</c:if>>${i}</option>
                                  </c:forEach>
                                </select> 개월
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 등록 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv7_LimitQueRegistCnt" value="${conf.lv7_LimitQueRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv7_LimitAnsRegistCnt" value="${conf.lv7_LimitAnsRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 피채택 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv7_LimitAnsChoiceCnt" value="${conf.lv7_LimitAnsChoiceCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 평가 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv7_LimitAnsEstiCnt" value="${conf.lv7_LimitAnsEstiCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">네티즌 평가 점수 (승천 후 월)</td>
                            <td><input type="text" name="Lv7_LimitAnsEstiPoint" value="${conf.lv7_LimitAnsEstiPoint}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 점</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 건수</td>
                            <td><input type="text" name="Lv7_LimitReplyCnt" value="${conf.lv7_LimitReplyCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">추천인수 (누적)</td>
                            <td><input type="text" name="Lv7_LimitChuMemCnt" value="${conf.lv7_LimitChuMemCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 명</td>
                          </tr>
                        </table>
                      </li>
                      <li>


                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6">
                              <span style="font-weight:bold;">8레벨 승천 조건 (${codeMemLvNm.get("7")} -> ${codeMemLvNm.get("8")})</span>
                              <p class="cssFormFieldLabel_P">
                                ( 자동 승천</span>&nbsp;
                                <select name="Lv8_LevelUpAuto_Yn" <c:if test="${conf.lv_LevelUpAuto_Yn != 'Y'}">DISABLED</c:if>>
                                  <option value="Y" <c:if test="${conf.lv8_LevelUpAuto_Yn == 'Y'}">SELECTED</c:if>>적용</option>
                                  <option value="N" <c:if test="${conf.lv8_LevelUpAuto_Yn != 'Y'}">SELECTED</c:if>>미적용</option>
                                </select> )
                              </p>
                            </td>
                          </tr>
                          <tr>
                            <td width="200" class="cssFormFieldLabel">레벨 유지 기간<br>(조건 충족 필요 기간수)</td>
                            <td>
                                <select name="Lv8_LimitLvMtPeriod">
                                  <option value="0">--</option>
                                  <c:forEach var="i" begin="1" end="12" step="1">
                               		<option value="${i}" <c:if test="${conf.lv8_LimitLvMtPeriod == i}">SELECTED</c:if>>${i}</option>
                                  </c:forEach>
                                </select> 개월
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 등록 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv8_LimitQueRegistCnt" value="${conf.lv8_LimitQueRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv8_LimitAnsRegistCnt" value="${conf.lv8_LimitAnsRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 피채택 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv8_LimitAnsChoiceCnt" value="${conf.lv8_LimitAnsChoiceCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 평가 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv8_LimitAnsEstiCnt" value="${conf.lv8_LimitAnsEstiCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">네티즌 평가 점수 (승천 후 월)</td>
                            <td><input type="text" name="Lv8_LimitAnsEstiPoint" value="${conf.lv8_LimitAnsEstiPoint}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 점</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 건수</td>
                            <td><input type="text" name="Lv8_LimitReplyCnt" value="${conf.lv8_LimitReplyCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">추천인수 (누적)</td>
                            <td><input type="text" name="Lv8_LimitChuMemCnt" value="${conf.lv8_LimitChuMemCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 명</td>
                          </tr>
                        </table>
                      </li>
 





                    </ul>

					<div class="cssDivRowGap"></div>

                    <ul class="cssFormFieldTable_Ul">
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6">
                              <span style="font-weight:bold;">9레벨 승천 조건 (${codeMemLvNm.get("8")} -> ${codeMemLvNm.get("9")})</span>
                              <p class="cssFormFieldLabel_P">
                                ( 자동 승천</span>&nbsp;
                                <select name="Lv9_LevelUpAuto_Yn" <c:if test="${conf.lv_LevelUpAuto_Yn != 'Y'}">DISABLED</c:if>>
                                  <option value="Y" <c:if test="${conf.lv9_LevelUpAuto_Yn == 'Y'}">SELECTED</c:if>>적용</option>
                                  <option value="N" <c:if test="${conf.lv9_LevelUpAuto_Yn != 'Y'}">SELECTED</c:if>>미적용</option>
                                </select> )
                              </p>
                            </td>
                          </tr>
                          <tr>
                            <td width="200" class="cssFormFieldLabel">레벨 유지 기간<br>(조건 충족 필요 기간수)</td>
                            <td>
                                <select name="Lv9_LimitLvMtPeriod">
                                  <option value="0">--</option>
                                  <c:forEach var="i" begin="1" end="12" step="1">
                               		<option value="${i}" <c:if test="${conf.lv9_LimitLvMtPeriod == i}">SELECTED</c:if>>${i}</option>
                                  </c:forEach>
                                </select> 개월
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 등록 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv9_LimitQueRegistCnt" value="${conf.lv9_LimitQueRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv9_LimitAnsRegistCnt" value="${conf.lv9_LimitAnsRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 피채택 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv9_LimitAnsChoiceCnt" value="${conf.lv9_LimitAnsChoiceCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 평가 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv9_LimitAnsEstiCnt" value="${conf.lv9_LimitAnsEstiCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">네티즌 평가 점수 (승천 후 월)</td>
                            <td><input type="text" name="Lv9_LimitAnsEstiPoint" value="${conf.lv9_LimitAnsEstiPoint}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 점</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 건수</td>
                            <td><input type="text" name="Lv9_LimitReplyCnt" value="${conf.lv9_LimitReplyCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">추천인수 (누적)</td>
                            <td><input type="text" name="Lv9_LimitChuMemCnt" value="${conf.lv9_LimitChuMemCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 명</td>
                          </tr>
                        </table>
                      </li>
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6">
                              <span style="font-weight:bold;">10레벨 승천 조건 (${codeMemLvNm.get("9")} -> ${codeMemLvNm.get("10")})</span>
                              <p class="cssFormFieldLabel_P">
                                ( 자동 승천</span>&nbsp;
                                <select name="Lv10_LevelUpAuto_Yn" <c:if test="${conf.lv_LevelUpAuto_Yn != 'Y'}">DISABLED</c:if>>
                                  <option value="Y" <c:if test="${conf.lv10_LevelUpAuto_Yn == 'Y'}">SELECTED</c:if>>적용</option>
                                  <option value="N" <c:if test="${conf.lv10_LevelUpAuto_Yn != 'Y'}">SELECTED</c:if>>미적용</option>
                                </select> )
                              </p>
                            </td>
                          </tr>
                          <tr>
                            <td width="200" class="cssFormFieldLabel">레벨 유지 기간<br>(조건 충족 필요 기간수)</td>
                            <td>
                                <select name="Lv10_LimitLvMtPeriod">
                                  <option value="0">--</option>
                                  <c:forEach var="i" begin="1" end="12" step="1">
                               		<option value="${i}" <c:if test="${conf.lv10_LimitLvMtPeriod == i}">SELECTED</c:if>>${i}</option>
                                  </c:forEach>
                                </select> 개월
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 등록 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv10_LimitQueRegistCnt" value="${conf.lv10_LimitQueRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv10_LimitAnsRegistCnt" value="${conf.lv10_LimitAnsRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 피채택 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv10_LimitAnsChoiceCnt" value="${conf.lv10_LimitAnsChoiceCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 평가 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv10_LimitAnsEstiCnt" value="${conf.lv10_LimitAnsEstiCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">네티즌 평가 점수 (승천 후 월)</td>
                            <td><input type="text" name="Lv10_LimitAnsEstiPoint" value="${conf.lv10_LimitAnsEstiPoint}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 점</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 건수</td>
                            <td><input type="text" name="Lv10_LimitReplyCnt" value="${conf.lv10_LimitReplyCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">추천인수 (누적)</td>
                            <td><input type="text" name="Lv10_LimitChuMemCnt" value="${conf.lv10_LimitChuMemCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 명</td>
                          </tr>
                        </table>
                      </li>
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6">
                              <span style="font-weight:bold;">11레벨 승천 조건 (${codeMemLvNm.get("10")} -> ${codeMemLvNm.get("11")})</span>
                              <p class="cssFormFieldLabel_P">
                                ( 자동 승천</span>&nbsp;
                                <select name="Lv11_LevelUpAuto_Yn" <c:if test="${conf.lv_LevelUpAuto_Yn != 'Y'}">DISABLED</c:if>>
                                  <option value="Y" <c:if test="${conf.lv11_LevelUpAuto_Yn == 'Y'}">SELECTED</c:if>>적용</option>
                                  <option value="N" <c:if test="${conf.lv11_LevelUpAuto_Yn != 'Y'}">SELECTED</c:if>>미적용</option>
                                </select> )
                              </p>
                            </td>
                          </tr>
                          <tr>
                            <td width="200" class="cssFormFieldLabel">레벨 유지 기간<br>(조건 충족 필요 기간수)</td>
                            <td>
                                <select name="Lv11_LimitLvMtPeriod">
                                  <option value="0">--</option>
                                  <c:forEach var="i" begin="1" end="12" step="1">
                               		<option value="${i}" <c:if test="${conf.lv11_LimitLvMtPeriod == i}">SELECTED</c:if>>${i}</option>
                                  </c:forEach>
                                </select> 개월
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 등록 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv11_LimitQueRegistCnt" value="${conf.lv11_LimitQueRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv11_LimitAnsRegistCnt" value="${conf.lv11_LimitAnsRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 피채택 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv11_LimitAnsChoiceCnt" value="${conf.lv11_LimitAnsChoiceCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 평가 건수 (승천 후 월)</td>
                            <td><input type="text" name="Lv11_LimitAnsEstiCnt" value="${conf.lv11_LimitAnsEstiCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">네티즌 평가 점수 (승천 후 월)</td>
                            <td><input type="text" name="Lv11_LimitAnsEstiPoint" value="${conf.lv11_LimitAnsEstiPoint}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 점</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">댓글 건수</td>
                            <td><input type="text" name="Lv11_LimitReplyCnt" value="${conf.lv11_LimitReplyCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">추천인수 (누적)</td>
                            <td><input type="text" name="Lv11_LimitChuMemCnt" value="${conf.lv11_LimitChuMemCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 명</td>
                          </tr>
                        </table>
                      </li>
                      
 





                    </ul>
                  </td>
                </tr>

                <!-- [수정(2018.03.13), 추가(2018.03.08): 김현구] 회원 레벨별 환전 신청 기준 설정 -->
                <tr>
                  <td bgcolor="#efefef">회원 레벨별 환전 신청 기준</td>
                  <td colspan="3" style="padding-top: 15px; padding-bottom: 15px;">

                    <ul class="cssFormFieldTable_Ul">
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">2레벨(나비천사) 환전 기준</span></td>
                          </tr>
                          <tr>
                            <td width="180" class="cssFormFieldLabel">환전 최소 유지 알머니<br>(환전 기본 제외 알머니)</td>
                            <td><input type="text" name="Lv2_ExchangeBaseDeductAlmoney" value="${conf.lv2_ExchangeBaseDeductAlmoney}" maxlength="8" class="input_textarea" style="width:85px;" onkeyup="checkNum(this);"> 알</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 한도 알머니 (월)</td>
                            <td><input type="text" name="Lv2_ExchangeLimitAlmoney" value="${conf.lv2_ExchangeLimitAlmoney}" maxlength="8" class="input_textarea" style="width:85px;" onkeyup="checkNum(this);"> 알</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 알머니 단위</td>
                            <td><select name="Lv2_ExchangeBaseUnitAlmoney">
                                  <option value="10"    <c:if test="${conf.lv2_ExchangeBaseUnitAlmoney == '10'}">SELECTED</c:if>>10</option>
                                  <option value="100"   <c:if test="${conf.lv2_ExchangeBaseUnitAlmoney == '100'}">SELECTED</c:if>>100</option>
                                  <option value="1000"  <c:if test="${conf.lv2_ExchangeBaseUnitAlmoney == '1000'}">SELECTED</c:if>>1000</option>
                                  <option value="10000" <c:if test="${conf.lv2_ExchangeBaseUnitAlmoney == '10000'}">SELECTED</c:if>>10000</option>
                                </select> 알
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 수수료율</td>
                            <td><input type="text" name="Lv2_ExchangeRealMoneyDeductRate" value="${conf.lv2_ExchangeRealMoneyDeductRate}" maxlength="5" class="input_textarea" style="width:50px;" onkeyup="checkNum(this);"> %</td>
                          </tr>
                        </table>
                      </li>
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">3레벨(미소천사) 환전 기준</span></td>
                          </tr>
                          <tr>
                            <td width="180" class="cssFormFieldLabel">환전 최소 유지 알머니<br>(환전 기본 제외 알머니)</td>
                            <td><input type="text" name="Lv3_ExchangeBaseDeductAlmoney" value="${conf.lv3_ExchangeBaseDeductAlmoney}" maxlength="8" class="input_textarea" style="width:85px;" onkeyup="checkNum(this);"> 알</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 한도 알머니 (월)</td>
                            <td><input type="text" name="Lv3_ExchangeLimitAlmoney" value="${conf.lv3_ExchangeLimitAlmoney}" maxlength="8" class="input_textarea" style="width:85px;" onkeyup="checkNum(this);"> 알</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 알머니 단위</td>
                            <td><select name="Lv3_ExchangeBaseUnitAlmoney">
                                  <option value="10"    <c:if test="${conf.lv3_ExchangeBaseUnitAlmoney == '10'}">SELECTED</c:if>>10</option>
                                  <option value="100"   <c:if test="${conf.lv3_ExchangeBaseUnitAlmoney == '100'}">SELECTED</c:if>>100</option>
                                  <option value="1000"  <c:if test="${conf.lv3_ExchangeBaseUnitAlmoney == '1000'}">SELECTED</c:if>>1000</option>
                                  <option value="10000" <c:if test="${conf.lv3_ExchangeBaseUnitAlmoney == '10000'}">SELECTED</c:if>>10000</option>
                                </select> 알
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 수수료율</td>
                            <td><input type="text" name="Lv3_ExchangeRealMoneyDeductRate" value="${conf.lv3_ExchangeRealMoneyDeductRate}" maxlength="5" class="input_textarea" style="width:50px;" onkeyup="checkNum(this);"> %</td>
                          </tr>
                        </table>
                      </li>
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">4레벨(열혈천사) 환전 기준</span></td>
                          </tr>
                          <tr>
                            <td width="180" class="cssFormFieldLabel">환전 최소 유지 알머니<br>(환전 기본 제외 알머니)</td>
                            <td><input type="text" name="Lv4_ExchangeBaseDeductAlmoney" value="${conf.lv4_ExchangeBaseDeductAlmoney}" maxlength="8" class="input_textarea" style="width:85px;" onkeyup="checkNum(this);"> 알</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 한도 알머니 (월)</td>
                            <td><input type="text" name="Lv4_ExchangeLimitAlmoney" value="${conf.lv4_ExchangeLimitAlmoney}" maxlength="8" class="input_textarea" style="width:85px;" onkeyup="checkNum(this);"> 알</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 알머니 단위</td>
                            <td><select name="Lv4_ExchangeBaseUnitAlmoney">
                                  <option value="10"    <c:if test="${conf.lv4_ExchangeBaseUnitAlmoney == '10'}">SELECTED</c:if>>10</option>
                                  <option value="100"   <c:if test="${conf.lv4_ExchangeBaseUnitAlmoney == '100'}">SELECTED</c:if>>100</option>
                                  <option value="1000"  <c:if test="${conf.lv4_ExchangeBaseUnitAlmoney == '1000'}">SELECTED</c:if>>1000</option>
                                  <option value="10000" <c:if test="${conf.lv4_ExchangeBaseUnitAlmoney == '10000'}">SELECTED</c:if>>10000</option>
                                </select> 알
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 수수료율</td>
                            <td><input type="text" name="Lv4_ExchangeRealMoneyDeductRate" value="${conf.lv4_ExchangeRealMoneyDeductRate}" maxlength="5" class="input_textarea" style="width:50px;" onkeyup="checkNum(this);"> %</td>
                          </tr>
                        </table>
                      </li>
                    </ul>
                    <div class="cssDivRowGap"></div>

                    <ul class="cssFormFieldTable_Ul">
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">5레벨(${codeMemLvNm.get("5")}) 환전 기준</span></td>
                          </tr>
                          <tr>
                            <td width="180" class="cssFormFieldLabel">환전 최소 유지 알머니<br>(환전 기본 제외 알머니)</td>
                            <td><input type="text" name="Lv5_ExchangeBaseDeductAlmoney" value="${conf.lv5_ExchangeBaseDeductAlmoney}" maxlength="8" class="input_textarea" style="width:85px;" onkeyup="checkNum(this);"> 알</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 한도 알머니 (월)</td>
                            <td><input type="text" name="Lv5_ExchangeLimitAlmoney" value="${conf.lv5_ExchangeLimitAlmoney}" maxlength="8" class="input_textarea" style="width:85px;" onkeyup="checkNum(this);"> 알</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 알머니 단위</td>
                            <td><select name="Lv5_ExchangeBaseUnitAlmoney">
                                  <option value="10"    <c:if test="${conf.lv5_ExchangeBaseUnitAlmoney == '10'}">SELECTED</c:if>>10</option>
                                  <option value="100"   <c:if test="${conf.lv5_ExchangeBaseUnitAlmoney == '100'}">SELECTED</c:if>>100</option>
                                  <option value="1000"  <c:if test="${conf.lv5_ExchangeBaseUnitAlmoney == '1000'}">SELECTED</c:if>>1000</option>
                                  <option value="10000" <c:if test="${conf.lv5_ExchangeBaseUnitAlmoney == '10000'}">SELECTED</c:if>>10000</option>
                                </select> 알
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 수수료율</td>
                            <td><input type="text" name="Lv5_ExchangeRealMoneyDeductRate" value="${conf.lv5_ExchangeRealMoneyDeductRate}" maxlength="5" class="input_textarea" style="width:50px;" onkeyup="checkNum(this);"> %</td>
                          </tr>
                        </table>
                      </li>
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">6레벨(${codeMemLvNm.get("6")}) 환전 기준</span></td>
                          </tr>
                          <tr>
                            <td width="180" class="cssFormFieldLabel">환전 최소 유지 알머니<br>(환전 기본 제외 알머니)</td>
                            <td><input type="text" name="Lv6_ExchangeBaseDeductAlmoney" value="${conf.lv6_ExchangeBaseDeductAlmoney}" maxlength="8" class="input_textarea" style="width:85px;" onkeyup="checkNum(this);"> 알</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 한도 알머니 (월)</td>
                            <td><input type="text" name="Lv6_ExchangeLimitAlmoney" value="${conf.lv6_ExchangeLimitAlmoney}" maxlength="8" class="input_textarea" style="width:85px;" onkeyup="checkNum(this);"> 알</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 알머니 단위</td>
                            <td><select name="Lv6_ExchangeBaseUnitAlmoney">
                                  <option value="10"    <c:if test="${conf.lv6_ExchangeBaseUnitAlmoney == '10'}">SELECTED</c:if>>10</option>
                                  <option value="100"   <c:if test="${conf.lv6_ExchangeBaseUnitAlmoney == '100'}">SELECTED</c:if>>100</option>
                                  <option value="1000"  <c:if test="${conf.lv6_ExchangeBaseUnitAlmoney == '1000'}">SELECTED</c:if>>1000</option>
                                  <option value="10000" <c:if test="${conf.lv6_ExchangeBaseUnitAlmoney == '10000'}">SELECTED</c:if>>10000</option>
                                </select> 알
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 수수료율</td>
                            <td><input type="text" name="Lv6_ExchangeRealMoneyDeductRate" value="${conf.lv6_ExchangeRealMoneyDeductRate}" maxlength="5" class="input_textarea" style="width:50px;" onkeyup="checkNum(this);"> %</td>
                          </tr>
                        </table>
                      </li>
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">7레벨(${codeMemLvNm.get("7")}) 환전 기준</span></td>
                          </tr>
                          <tr>
                            <td width="180" class="cssFormFieldLabel">환전 최소 유지 알머니<br>(환전 기본 제외 알머니)</td>
                            <td><input type="text" name="Lv7_ExchangeBaseDeductAlmoney" value="${conf.lv7_ExchangeBaseDeductAlmoney}" maxlength="8" class="input_textarea" style="width:85px;" onkeyup="checkNum(this);"> 알</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 한도 알머니 (월)</td>
                            <td><input type="text" name="Lv7_ExchangeLimitAlmoney" value="${conf.lv7_ExchangeLimitAlmoney}" maxlength="8" class="input_textarea" style="width:85px;" onkeyup="checkNum(this);"> 알</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 알머니 단위</td>
                            <td><select name="Lv7_ExchangeBaseUnitAlmoney">
                                  <option value="10"    <c:if test="${conf.lv7_ExchangeBaseUnitAlmoney == '10'}">SELECTED</c:if>>10</option>
                                  <option value="100"   <c:if test="${conf.lv7_ExchangeBaseUnitAlmoney == '100'}">SELECTED</c:if>>100</option>
                                  <option value="1000"  <c:if test="${conf.lv7_ExchangeBaseUnitAlmoney == '1000'}">SELECTED</c:if>>1000</option>
                                  <option value="10000" <c:if test="${conf.lv7_ExchangeBaseUnitAlmoney == '10000'}">SELECTED</c:if>>10000</option>
                                </select> 알
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 수수료율</td>
                            <td><input type="text" name="Lv7_ExchangeRealMoneyDeductRate" value="${conf.lv7_ExchangeRealMoneyDeductRate}" maxlength="5" class="input_textarea" style="width:50px;" onkeyup="checkNum(this);"> %</td>
                          </tr>
                        </table>
                      </li>
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">8레벨(${codeMemLvNm.get("8")}) 환전 기준</span></td>
                          </tr>
                          <tr>
                            <td width="180" class="cssFormFieldLabel">환전 최소 유지 알머니<br>(환전 기본 제외 알머니)</td>
                            <td><input type="text" name="Lv8_ExchangeBaseDeductAlmoney" value="${conf.lv8_ExchangeBaseDeductAlmoney}" maxlength="8" class="input_textarea" style="width:85px;" onkeyup="checkNum(this);"> 알</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 한도 알머니 (월)</td>
                            <td><input type="text" name="Lv8_ExchangeLimitAlmoney" value="${conf.lv8_ExchangeLimitAlmoney}" maxlength="8" class="input_textarea" style="width:85px;" onkeyup="checkNum(this);"> 알</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 알머니 단위</td>
                            <td><select name="Lv8_ExchangeBaseUnitAlmoney">
                                  <option value="10"    <c:if test="${conf.lv8_ExchangeBaseUnitAlmoney == '10'}">SELECTED</c:if>>10</option>
                                  <option value="100"   <c:if test="${conf.lv8_ExchangeBaseUnitAlmoney == '100'}">SELECTED</c:if>>100</option>
                                  <option value="1000"  <c:if test="${conf.lv8_ExchangeBaseUnitAlmoney == '1000'}">SELECTED</c:if>>1000</option>
                                  <option value="10000" <c:if test="${conf.lv8_ExchangeBaseUnitAlmoney == '10000'}">SELECTED</c:if>>10000</option>
                                </select> 알
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 수수료율</td>
                            <td><input type="text" name="Lv8_ExchangeRealMoneyDeductRate" value="${conf.lv8_ExchangeRealMoneyDeductRate}" maxlength="5" class="input_textarea" style="width:50px;" onkeyup="checkNum(this);"> %</td>
                          </tr>
                        </table>
                      </li>


                    </ul>

					<div class="cssDivRowGap"></div>

                    <ul class="cssFormFieldTable_Ul">
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">9레벨(${codeMemLvNm.get("9")}) 환전 기준</span></td>
                          </tr>
                          <tr>
                            <td width="180" class="cssFormFieldLabel">환전 최소 유지 알머니<br>(환전 기본 제외 알머니)</td>
                            <td><input type="text" name="Lv9_ExchangeBaseDeductAlmoney" value="${conf.lv9_ExchangeBaseDeductAlmoney}" maxlength="8" class="input_textarea" style="width:85px;" onkeyup="checkNum(this);"> 알</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 한도 알머니 (월)</td>
                            <td><input type="text" name="Lv9_ExchangeLimitAlmoney" value="${conf.lv9_ExchangeLimitAlmoney}" maxlength="8" class="input_textarea" style="width:85px;" onkeyup="checkNum(this);"> 알</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 알머니 단위</td>
                            <td><select name="Lv9_ExchangeBaseUnitAlmoney">
                                  <option value="10"    <c:if test="${conf.lv9_ExchangeBaseUnitAlmoney == '10'}">SELECTED</c:if>>10</option>
                                  <option value="100"   <c:if test="${conf.lv9_ExchangeBaseUnitAlmoney == '100'}">SELECTED</c:if>>100</option>
                                  <option value="1000"  <c:if test="${conf.lv9_ExchangeBaseUnitAlmoney == '1000'}">SELECTED</c:if>>1000</option>
                                  <option value="10000" <c:if test="${conf.lv9_ExchangeBaseUnitAlmoney == '10000'}">SELECTED</c:if>>10000</option>
                                </select> 알
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 수수료율</td>
                            <td><input type="text" name="Lv9_ExchangeRealMoneyDeductRate" value="${conf.lv9_ExchangeRealMoneyDeductRate}" maxlength="5" class="input_textarea" style="width:50px;" onkeyup="checkNum(this);"> %</td>
                          </tr>
                        </table>
                      </li>
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">10레벨(${codeMemLvNm.get("10")}) 환전 기준</span></td>
                          </tr>
                          <tr>
                            <td width="180" class="cssFormFieldLabel">환전 최소 유지 알머니<br>(환전 기본 제외 알머니)</td>
                            <td><input type="text" name="Lv10_ExchangeBaseDeductAlmoney" value="${conf.lv10_ExchangeBaseDeductAlmoney}" maxlength="8" class="input_textarea" style="width:85px;" onkeyup="checkNum(this);"> 알</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 한도 알머니 (월)</td>
                            <td><input type="text" name="Lv10_ExchangeLimitAlmoney" value="${conf.lv10_ExchangeLimitAlmoney}" maxlength="8" class="input_textarea" style="width:85px;" onkeyup="checkNum(this);"> 알</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 알머니 단위</td>
                            <td><select name="Lv10_ExchangeBaseUnitAlmoney">
                                  <option value="10"    <c:if test="${conf.lv10_ExchangeBaseUnitAlmoney == '10'}">SELECTED</c:if>>10</option>
                                  <option value="100"   <c:if test="${conf.lv10_ExchangeBaseUnitAlmoney == '100'}">SELECTED</c:if>>100</option>
                                  <option value="1000"  <c:if test="${conf.lv10_ExchangeBaseUnitAlmoney == '1000'}">SELECTED</c:if>>1000</option>
                                  <option value="10000" <c:if test="${conf.lv10_ExchangeBaseUnitAlmoney == '10000'}">SELECTED</c:if>>10000</option>
                                </select> 알
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 수수료율</td>
                            <td><input type="text" name="Lv10_ExchangeRealMoneyDeductRate" value="${conf.lv10_ExchangeRealMoneyDeductRate}" maxlength="5" class="input_textarea" style="width:50px;" onkeyup="checkNum(this);"> %</td>
                          </tr>
                        </table>
                      </li>
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">11레벨(${codeMemLvNm.get("11")}) 환전 기준</span></td>
                          </tr>
                          <tr>
                            <td width="180" class="cssFormFieldLabel">환전 최소 유지 알머니<br>(환전 기본 제외 알머니)</td>
                            <td><input type="text" name="Lv11_ExchangeBaseDeductAlmoney" value="${conf.lv11_ExchangeBaseDeductAlmoney}" maxlength="8" class="input_textarea" style="width:85px;" onkeyup="checkNum(this);"> 알</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 한도 알머니 (월)</td>
                            <td><input type="text" name="Lv11_ExchangeLimitAlmoney" value="${conf.lv11_ExchangeLimitAlmoney}" maxlength="8" class="input_textarea" style="width:85px;" onkeyup="checkNum(this);"> 알</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 알머니 단위</td>
                            <td><select name="Lv11_ExchangeBaseUnitAlmoney">
                                  <option value="10"    <c:if test="${conf.lv11_ExchangeBaseUnitAlmoney == '10'}">SELECTED</c:if>>10</option>
                                  <option value="100"   <c:if test="${conf.lv11_ExchangeBaseUnitAlmoney == '100'}">SELECTED</c:if>>100</option>
                                  <option value="1000"  <c:if test="${conf.lv11_ExchangeBaseUnitAlmoney == '1000'}">SELECTED</c:if>>1000</option>
                                  <option value="10000" <c:if test="${conf.lv11_ExchangeBaseUnitAlmoney == '10000'}">SELECTED</c:if>>10000</option>
                                </select> 알
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">환전 신청 수수료율</td>
                            <td><input type="text" name="Lv11_ExchangeRealMoneyDeductRate" value="${conf.lv11_ExchangeRealMoneyDeductRate}" maxlength="5" class="input_textarea" style="width:50px;" onkeyup="checkNum(this);"> %</td>
                          </tr>
                        </table>
                      </li>
                      


                    </ul>

                  </td>
                </tr>

                <!-- [추가(2018.03.14): 김현구] 회원 레벨별 환전 신청 체크 조건 설정 -->
                <tr>
                  <td bgcolor="#efefef">회원 레벨별 환전 신청 체크 조건</td>
                  <td colspan="3" style="padding-top: 15px; padding-bottom: 15px;">

                    <ul class="cssFormFieldTable_Ul">
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">2레벨(나비천사) 환전 조건</span></td>
                          </tr>
                          <tr>
                            <td width="190" class="cssFormFieldLabel">환전 조건 유지 기간<br>(이전월 체크 기간)</td>
                            <td>
                                <select name="Lv2_ExchangeMtPeriod">
                                  <option value="0">--</option>
                                  <c:forEach var="i" begin="1" end="12" step="1">
                               		<option value="${i}" <c:if test="${conf.lv2_ExchangeMtPeriod == i}">SELECTED</c:if>>${i}</option>
                                  </c:forEach>
                                </select> 개월
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 등록 건수 (월)</td>
                            <td><input type="text" name="Lv2_ExchangeQueRegistCnt" value="${conf.lv2_ExchangeQueRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록 건수 (월)</td>
                            <td><input type="text" name="Lv2_ExchangeAnsRegistCnt" value="${conf.lv2_ExchangeAnsRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 평가 건수 (월)</td>
                            <td><input type="text" name="Lv2_ExchangeEstiCnt" value="${conf.lv2_ExchangeEstiCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">네티즌 평가 점수 (월)</td>
                            <td><input type="text" name="Lv2_ExchangeEstiPoint" value="${conf.lv2_ExchangeEstiPoint}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 점</td>
                          </tr>
                        </table>
                      </li>
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">3레벨(미소천사) 환전 조건</span></td>
                          </tr>
                          <tr>
                            <td width="190" class="cssFormFieldLabel">환전 조건 유지 기간<br>(이전월 체크 기간)</td>
                            <td>
                                <select name="Lv3_ExchangeMtPeriod">
                                  <option value="0">--</option>
                                  <c:forEach var="i" begin="1" end="12" step="1">
                               		<option value="${i}" <c:if test="${conf.lv3_ExchangeMtPeriod == i}">SELECTED</c:if>>${i}</option>
                                  </c:forEach>
                                </select> 개월
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 등록 건수 (월)</td>
                            <td><input type="text" name="Lv3_ExchangeQueRegistCnt" value="${conf.lv3_ExchangeQueRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록 건수 (월)</td>
                            <td><input type="text" name="Lv3_ExchangeAnsRegistCnt" value="${conf.lv3_ExchangeAnsRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 평가 건수 (월)</td>
                            <td><input type="text" name="Lv3_ExchangeEstiCnt" value="${conf.lv3_ExchangeEstiCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">네티즌 평가 점수 (월)</td>
                            <td><input type="text" name="Lv3_ExchangeEstiPoint" value="${conf.lv3_ExchangeEstiPoint}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 점</td>
                          </tr>
                        </table>
                      </li>
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">4레벨(열혈천사) 환전 조건</span></td>
                          </tr>
                          <tr>
                            <td width="190" class="cssFormFieldLabel">환전 조건 유지 기간<br>(이전월 체크 기간)</td>
                            <td>
                                <select name="Lv4_ExchangeMtPeriod">
                                  <option value="0">--</option>
                                  <c:forEach var="i" begin="1" end="12" step="1">
                               		<option value="${i}" <c:if test="${conf.lv4_ExchangeMtPeriod == i}">SELECTED</c:if>>${i}</option>
                                  </c:forEach>
                                </select> 개월
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 등록 건수 (월)</td>
                            <td><input type="text" name="Lv4_ExchangeQueRegistCnt" value="${conf.lv4_ExchangeQueRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록 건수 (월)</td>
                            <td><input type="text" name="Lv4_ExchangeAnsRegistCnt" value="${conf.lv4_ExchangeAnsRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 평가 건수 (월)</td>
                            <td><input type="text" name="Lv4_ExchangeEstiCnt" value="${conf.lv4_ExchangeEstiCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">네티즌 평가 점수 (월)</td>
                            <td><input type="text" name="Lv4_ExchangeEstiPoint" value="${conf.lv4_ExchangeEstiPoint}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 점</td>
                          </tr>
                        </table>
                      </li>
                    </ul>
                    <div class="cssDivRowGap"></div>

                    <ul class="cssFormFieldTable_Ul">
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">5레벨(${codeMemLvNm.get("5")}) 환전 조건</span></td>
                          </tr>
                          <tr>
                            <td width="190" class="cssFormFieldLabel">환전 조건 유지 기간<br>(이전월 체크 기간)</td>
                            <td>
                                <select name="Lv5_ExchangeMtPeriod">
                                  <option value="0">--</option>
                                  <c:forEach var="i" begin="1" end="12" step="1">
                               		<option value="${i}" <c:if test="${conf.lv5_ExchangeMtPeriod == i}">SELECTED</c:if>>${i}</option>
                                  </c:forEach>
                                </select> 개월
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 등록 건수 (월)</td>
                            <td><input type="text" name="Lv5_ExchangeQueRegistCnt" value="${conf.lv5_ExchangeQueRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록 건수 (월)</td>
                            <td><input type="text" name="Lv5_ExchangeAnsRegistCnt" value="${conf.lv5_ExchangeAnsRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 평가 건수 (월)</td>
                            <td><input type="text" name="Lv5_ExchangeEstiCnt" value="${conf.lv5_ExchangeEstiCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">네티즌 평가 점수 (월)</td>
                            <td><input type="text" name="Lv5_ExchangeEstiPoint" value="${conf.lv5_ExchangeEstiPoint}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 점</td>
                          </tr>
                        </table>
                      </li>
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">6레벨(${codeMemLvNm.get("6")}) 환전 조건</span></td>
                          </tr>
                          <tr>
                            <td width="190" class="cssFormFieldLabel">환전 조건 유지 기간<br>(이전월 체크 기간)</td>
                            <td>
                                <select name="Lv6_ExchangeMtPeriod">
                                  <option value="0">--</option>
                                  <c:forEach var="i" begin="1" end="12" step="1">
                               		<option value="${i}" <c:if test="${fn:trim(conf.lv6_ExchangeMtPeriod) == i}">SELECTED</c:if>>${i}</option>
                                  </c:forEach>
                                </select> 개월
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 등록 건수 (월)</td>
                            <td><input type="text" name="Lv6_ExchangeQueRegistCnt" value="${conf.lv6_ExchangeQueRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록 건수 (월)</td>
                            <td><input type="text" name="Lv6_ExchangeAnsRegistCnt" value="${conf.lv6_ExchangeAnsRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 평가 건수 (월)</td>
                            <td><input type="text" name="Lv6_ExchangeEstiCnt" value="${conf.lv6_ExchangeEstiCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">네티즌 평가 점수 (월)</td>
                            <td><input type="text" name="Lv6_ExchangeEstiPoint" value="${conf.lv6_ExchangeEstiPoint}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 점</td>
                          </tr>
                        </table>
                      </li>
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">7레벨(${codeMemLvNm.get("7")}) 환전 조건</span></td>
                          </tr>
                          <tr>
                            <td width="190" class="cssFormFieldLabel">환전 조건 유지 기간<br>(이전월 체크 기간)</td>
                            <td>
                                <select name="Lv7_ExchangeMtPeriod">
                                  <option value="0">--</option>
                                  <c:forEach var="i" begin="1" end="12" step="1">
                               		<option value="${i}" <c:if test="${fn:trim(conf.lv7_ExchangeMtPeriod) == i}">SELECTED</c:if>>${i}</option>
                                  </c:forEach>
                                </select> 개월
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 등록 건수 (월)</td>
                            <td><input type="text" name="Lv7_ExchangeQueRegistCnt" value="${conf.lv7_ExchangeQueRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록 건수 (월)</td>
                            <td><input type="text" name="Lv7_ExchangeAnsRegistCnt" value="${conf.lv7_ExchangeAnsRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 평가 건수 (월)</td>
                            <td><input type="text" name="Lv7_ExchangeEstiCnt" value="${conf.lv7_ExchangeEstiCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">네티즌 평가 점수 (월)</td>
                            <td><input type="text" name="Lv7_ExchangeEstiPoint" value="${conf.lv7_ExchangeEstiPoint}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 점</td>
                          </tr>
                        </table>
                      </li>
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">8레벨(${codeMemLvNm.get("8")}) 환전 조건</span></td>
                          </tr>
                          <tr>
                            <td width="190" class="cssFormFieldLabel">환전 조건 유지 기간<br>(이전월 체크 기간)</td>
                            <td>
                                <select name="Lv8_ExchangeMtPeriod">
                                  <option value="0">--</option>
                                  <c:forEach var="i" begin="1" end="12" step="1">
                               		<option value="${i}" <c:if test="${fn:trim(conf.lv8_ExchangeMtPeriod) == i}">SELECTED</c:if>>${i}</option>
                                  </c:forEach>
                                </select> 개월
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 등록 건수 (월)</td>
                            <td><input type="text" name="Lv8_ExchangeQueRegistCnt" value="${conf.lv8_ExchangeQueRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록 건수 (월)</td>
                            <td><input type="text" name="Lv8_ExchangeAnsRegistCnt" value="${conf.lv8_ExchangeAnsRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 평가 건수 (월)</td>
                            <td><input type="text" name="Lv8_ExchangeEstiCnt" value="${conf.lv8_ExchangeEstiCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">네티즌 평가 점수 (월)</td>
                            <td><input type="text" name="Lv8_ExchangeEstiPoint" value="${conf.lv8_ExchangeEstiPoint}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 점</td>
                          </tr>
                        </table>
                      </li>

                    </ul>

					<div class="cssDivRowGap"></div>

                    <ul class="cssFormFieldTable_Ul">
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">9레벨(${codeMemLvNm.get("9")}) 환전 조건</span></td>
                          </tr>
                          <tr>
                            <td width="190" class="cssFormFieldLabel">환전 조건 유지 기간<br>(이전월 체크 기간)</td>
                            <td>
                                <select name="Lv9_ExchangeMtPeriod">
                                  <option value="0">--</option>
                                  <c:forEach var="i" begin="1" end="12" step="1">
                               		<option value="${i}" <c:if test="${fn:trim(conf.lv9_ExchangeMtPeriod) == i}">SELECTED</c:if>>${i}</option>
                                  </c:forEach>
                                </select> 개월
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 등록 건수 (월)</td>
                            <td><input type="text" name="Lv9_ExchangeQueRegistCnt" value="${conf.lv9_ExchangeQueRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록 건수 (월)</td>
                            <td><input type="text" name="Lv9_ExchangeAnsRegistCnt" value="${conf.lv9_ExchangeAnsRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 평가 건수 (월)</td>
                            <td><input type="text" name="Lv9_ExchangeEstiCnt" value="${conf.lv9_ExchangeEstiCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">네티즌 평가 점수 (월)</td>
                            <td><input type="text" name="Lv9_ExchangeEstiPoint" value="${conf.lv9_ExchangeEstiPoint}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 점</td>
                          </tr>
                        </table>
                      </li>
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">10레벨(${codeMemLvNm.get("10")}) 환전 조건</span></td>
                          </tr>
                          <tr>
                            <td width="190" class="cssFormFieldLabel">환전 조건 유지 기간<br>(이전월 체크 기간)</td>
                            <td>
                                <select name="Lv10_ExchangeMtPeriod">
                                  <option value="0">--</option>
                                  <c:forEach var="i" begin="1" end="12" step="1">
                               		<option value="${i}" <c:if test="${fn:trim(conf.lv10_ExchangeMtPeriod) == i}">SELECTED</c:if>>${i}</option>
                                  </c:forEach>
                                </select> 개월
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 등록 건수 (월)</td>
                            <td><input type="text" name="Lv10_ExchangeQueRegistCnt" value="${conf.lv10_ExchangeQueRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록 건수 (월)</td>
                            <td><input type="text" name="Lv10_ExchangeAnsRegistCnt" value="${conf.lv10_ExchangeAnsRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 평가 건수 (월)</td>
                            <td><input type="text" name="Lv10_ExchangeEstiCnt" value="${conf.lv10_ExchangeEstiCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">네티즌 평가 점수 (월)</td>
                            <td><input type="text" name="Lv10_ExchangeEstiPoint" value="${conf.lv10_ExchangeEstiPoint}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 점</td>
                          </tr>
                        </table>
                      </li>
                      <li>
                        <table width="320" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">11레벨(${codeMemLvNm.get("11")}) 환전 조건</span></td>
                          </tr>
                          <tr>
                            <td width="190" class="cssFormFieldLabel">환전 조건 유지 기간<br>(이전월 체크 기간)</td>
                            <td>
                                <select name="Lv11_ExchangeMtPeriod">
                                  <option value="0">--</option>
                                  <c:forEach var="i" begin="1" end="12" step="1">
                               		<option value="${i}" <c:if test="${fn:trim(conf.lv11_ExchangeMtPeriod) == i}">SELECTED</c:if>>${i}</option>
                                  </c:forEach>
                                </select> 개월
                            </td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">질문 등록 건수 (월)</td>
                            <td><input type="text" name="Lv11_ExchangeQueRegistCnt" value="${conf.lv11_ExchangeQueRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 등록 건수 (월)</td>
                            <td><input type="text" name="Lv11_ExchangeAnsRegistCnt" value="${conf.lv11_ExchangeAnsRegistCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">답변 평가 건수 (월)</td>
                            <td><input type="text" name="Lv11_ExchangeEstiCnt" value="${conf.lv11_ExchangeEstiCnt}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 건</td>
                          </tr>
                          <tr>
                            <td class="cssFormFieldLabel">네티즌 평가 점수 (월)</td>
                            <td><input type="text" name="Lv11_ExchangeEstiPoint" value="${conf.lv11_ExchangeEstiPoint}" maxlength="5" class="input_textarea" style="width:60px;" onkeyup="checkNum(this);"> 점</td>
                          </tr>
                        </table>
                      </li>

                    </ul>

                  </td>
                </tr>

                <!-- [추가(2018.03.13): 김현구] 회원 레벨별 질문 등록시 사례알 기준 설정 -->
                <tr>
                  <td bgcolor="#efefef">회원 레벨별 질문 등록시 사례알 기준 ( /로 구분)</td>
                  <td colspan="3" style="padding-top: 15px; padding-bottom: 15px;">
                    <table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td colspan="2" align="center" bgcolor="#F6F6F6" style="height:20px;"><font style="font-weight:bold;">질문 등록시 사례알 선택 기준</span></td>
                      </tr>
                      <tr>
                        <td width="130" class="cssFormFieldLabel">1레벨 (알천사)</td>
                        <td><input type="text" name="Lv1_QueRegAlmoney" value="${conf.lv1_QueRegAlmoney}" class="input_textarea" style="width:720px;"> 알</td>
                      </tr>
                      <tr>
                        <td class="cssFormFieldLabel">2레벨 (나비천사)</td>
                        <td><input type="text" name="Lv2_QueRegAlmoney" value="${conf.lv2_QueRegAlmoney}" class="input_textarea" style="width:720px;"> 알</td>
                      </tr>
                      <tr>
                        <td class="cssFormFieldLabel">3레벨 (미소천사)</td>
                        <td><input type="text" name="Lv3_QueRegAlmoney" value="${conf.lv3_QueRegAlmoney}" class="input_textarea" style="width:720px;"> 알</td>
                      </tr>
                      <tr>
                        <td class="cssFormFieldLabel">4레벨 (열혈천사)</td>
                        <td><input type="text" name="Lv4_QueRegAlmoney" value="${conf.lv4_QueRegAlmoney}" class="input_textarea" style="width:720px;"> 알</td>
                      </tr>
                      <tr>
                        <td class="cssFormFieldLabel">5레벨 (${codeMemLvNm.get(5)})</td>
                        <td><input type="text" name="Lv5_QueRegAlmoney" value="${conf.lv5_QueRegAlmoney}" class="input_textarea" style="width:720px;"> 알</td>
                      </tr>
                      <tr>
                        <td class="cssFormFieldLabel">6레벨 (${codeMemLvNm.get(6)})</td>
                        <td><input type="text" name="Lv6_QueRegAlmoney" value="${conf.lv6_QueRegAlmoney}" class="input_textarea" style="width:720px;"> 알</td>
                      </tr>
                      <tr>
                        <td class="cssFormFieldLabel">7레벨 (${codeMemLvNm.get(7)})</td>
                        <td><input type="text" name="Lv7_QueRegAlmoney" value="${conf.lv7_QueRegAlmoney}" class="input_textarea" style="width:720px;"> 알</td>
                      </tr>
                      <tr>
                        <td class="cssFormFieldLabel">8레벨 (${codeMemLvNm.get(8)})</td>
                        <td><input type="text" name="Lv8_QueRegAlmoney" value="${conf.lv8_QueRegAlmoney}" class="input_textarea" style="width:720px;"> 알</td>
                      </tr>
					  <tr>
                        <td class="cssFormFieldLabel">9레벨 (${codeMemLvNm.get(9)})</td>
                        <td><input type="text" name="Lv9_QueRegAlmoney" value="${conf.lv9_QueRegAlmoney}" class="input_textarea" style="width:720px;"> 알</td>
                      </tr>
					  <tr>
                        <td class="cssFormFieldLabel">10레벨 (${codeMemLvNm.get(10)})</td>
                        <td><input type="text" name="Lv10_QueRegAlmoney" value="${conf.lv10_QueRegAlmoney}" class="input_textarea" style="width:720px;"> 알</td>
                      </tr>
					  <tr>
                        <td class="cssFormFieldLabel">11레벨 (${codeMemLvNm.get(11)})</td>
                        <td><input type="text" name="Lv11_QueRegAlmoney" value="${conf.lv11_QueRegAlmoney}" class="input_textarea" style="width:720px;"> 알</td>
                      </tr>
                    </table>
                  </td>
                </tr>

                <!-- 닉네임 금지단어 설정 -->
                <tr>
                  <td bgcolor="#efefef">닉네임 금지단어 ( / 로 구분 )</th>
                  <td colspan="3"><input type="text" name="NickNotUse" value="${conf.nickNotUse}" class="input_textarea" style="width:900px;"></td>
                </tr>
			</table>
			<br><br>
			<p align="center">
			<button type="button" class="btn btn-default" onClick="javascript:goSubmit('frm_sch','/aadmin/configSave','ifrm');">등록하기</button>
			</p>
</form>
		</div>
	</div>
</div>
</form>
<iframe name="ifrm" width="100%" height="220" frameborder="0"></iframe>
</body>
</html>