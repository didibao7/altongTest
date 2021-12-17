<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ page import="com.altong.web.logic.util.EncLibrary" %>
<%@ include file="/Common/include/headerAdmin.jsp" %>
<%
EncLibrary enc = new EncLibrary();
%>
<body>
<!-- [추가(2018.02.06): 김현구] -->
<style type="text/css">
  .cssLinkMemberSeq  A:link    { font-size: 14px; color: #656565; text-decoration: underline; }
  .cssLinkMemberSeq  A:visited { font-size: 14px; color: #656565; text-decoration: underline; }
  .cssLinkMemberSeq  A:active  { font-size: 14px; color: #656565; text-decoration: underline; }
	.cssLinkMemberSeq  A:hover   { font-size: 14px; color: #656565; text-decoration: underline; }
	div.title-wrapper {
        border-bottom: 1px solid #888888;
    }

    div.title-wrapper>.title-name {
        padding-bottom: 5px;
        font-size: 20px;
        font-weight: bold;
        color: #111111;
    }

    div.title-wrapper>.site-hitory {
        text-align: right;
        font-size: 13px;
        color: #888888;
    }
</style>

<form name="frm_sch" method="post" onSubmit="return false;">
<input type="hidden" name="src_Target" value="${curPageName}">
<input type="hidden" name="pg" value="${n_curpage}">

<div id="wrapper">
	<div id="header_wrapper">
	</div>
	<div id="M_wrapper">
	<%@ include file="/Common/include/menuAdmin.jsp" %>
		<br>
		<div class="title-wrapper row">
				<div class="title-name col-md-4">
					회원 관리
				</div>
				<%if(CommonUtil.libhasAuthority(memberCertList, userSeq)) {%>
				<div class="title-name col-md-4">
					<a href="/aadmin/memberCertList">[회원 실명인증 관리]</a>
				</div>
				<% } %>
				<%if(CommonUtil.libhasAuthority(0, userSeq)) {%>
				<div class="title-name col-md-4">
					<a href="/aadmin/adminConfig">[관리자 설정]</a>
				</div>
				<% } %>
		</div>
		<br>
		<div class="board_input8">
			<table width="100%" border="0" align="center"  cellpadding="0" cellspacing="0">
				<tr>
					<td width="450">
						<p class="t_inputmenu" style="margin-top:15px;">
						<label for="input01" id="label_input01">검색 : </label>
						<select name="src_Kind" class="input_select" id="input4" accesskey="L">
							<option value="sName" <c:if test="${src_Kind == 'sName'}">selected</c:if>>이름</option>
							<option value="Phone" <c:if test="${src_Kind == 'Phone'}">selected</c:if>>휴대폰</option>
							<option value="NickName" <c:if test="${src_Kind == 'NickName'}">selected</c:if>>닉네임</option>
							<option value="MemberType" <c:if test="${src_Kind == 'MemberType'}">selected</c:if>>유저유형</option>
							<option value="Seq" <c:if test="${src_Kind == 'Seq'}">selected</c:if>>고유번호</option>
							<option value="Lv" <c:if test="${src_Kind == 'Lv'}">selected</c:if>>레벨</option>
						</select>
						<input type="text" name="src_Text" value="${src_Text}" class="input_textarea" id="id8" accesskey="L" onKeyDown="if (event.keyCode == 13) { SearchChk('frm_sch','/aadmin/memberList'); }">
						</p>
					</td>
					<td>
						<button type="button" class="btn btn-warning btn-sm" onClick="javascript:SearchChk('frm_sch', '${curPageName}');">검 색</button>&nbsp;&nbsp;&nbsp;
						<c:if test="${src_Text != ''}">
						<button type="button" class="btn btn-warning btn-sm" onClick="javascript:location.href='/aadmin/${curPageName}';">전 체</button>
						</c:if>
					</td>
				</tr>
			</table>
		</div>

		<div id="list">
			<p>전체 회원 : ${n_trec} 명</p>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="boardList01">
				<tr>
					<th width="">회원번호<br></th>
					<th width="">이름<br>닉네임</th>
					<th width="">회원유형<br>레벨</th>
					<th width="">고유코드<br>추천인코드</th>
					<th width="">계좌<br>알머니</th>
					<th width="">휴대폰번호<br>메일주소</th>
					<th width="">질문수<br>질문수익</th>
					<th width="">답변수<br>답변수익</th>
					<th width="">본인인증<br>성인인증</th>
					<th width="">메일수신<br>휴대폰수신</th>
					<th width="">프로필공개<br>셀프답변권한</th>
					<th width="">회원가입일<br>마지막로그인</th>
				</tr>

			<c:if test="${n_trec > 0}">
			<c:set var="loopK" value="1"/>
			<c:forEach var="m" items="${memList}" varStatus="status">
				<c:set var="email_src" value="${m.email}"/>
				<c:set var="phone_src" value="${m.phone}"/>
				<c:set var="seq_src" value="${m.seq}"/>
				<c:set var="mType_src" value="${m.memberType}"/>
				<c:set var="lv_src" value="${m.lv}"/>
				<%
					String email_src = String.valueOf( pageContext.getAttribute("email_src") );
					String phone_src = String.valueOf( pageContext.getAttribute("phone_src") );
					String seq_src = String.valueOf( pageContext.getAttribute("seq_src") );
					String mType_src = String.valueOf( pageContext.getAttribute("mType_src") );
					String lv_src = String.valueOf( pageContext.getAttribute("lv_src") );
					
					String email = "";
					if(email_src != null && !email_src.equals("") && !email_src.equals("null")) {
						email = enc.AlmoneyDecrypt(email_src);
					}
					
					String phone = enc.AlmoneyDecrypt(phone_src);
					
					String sMemCode = seq_src.substring(0, 4) + "-" + seq_src.substring(4, 8);
					
					String sMemberType = CommonUtil.fn_memberType(mType_src, request);
					
					String lv = CommonUtil.fn_Level(lv_src, request);
					
					pageContext.setAttribute("email", email);
					pageContext.setAttribute("phone", phone);
					pageContext.setAttribute("sMemCode", sMemCode);
					pageContext.setAttribute("sMemberType", sMemberType);
					pageContext.setAttribute("lv", lv);
				%>
				<tr style='cursor:pointer;' onmouseover='this.style.background="#FFE0B3";' onmouseleave='this.style.background="#FFFFFF";'>
                    <!-- [추가(2018.02.06): 김현구] 회원번호에 추천인들 조회 링크 기능 추가 -->
					<td class="cssLinkMemberSeq">${m.seq}</td>
					<td><a href="/aadmin/memberView?Seq=${m.seq}&sName=${m.sName}&NickName=${m.nickName}&Phone=${phone}">${m.sName}<br>${m.nickName}</a></td>
					<td>${sMemberType}(${m.memberType})<br>${lv}(Lv${m.lv})</td>
					<td><a href="/member/myRecommend?MemberSeq=${m.seq}" target="_blank">${sMemCode}</a><br>${m.chuCode}</td>
					<td><a href="/member/myBank_Admin?MemberSeq=${m.seq}" target="_blank">[계좌]</a><br><fmt:formatNumber value="${m.almoney}" pattern="#,###" /></td>
					<td>+${m.country}-${phone}<br>${email}</td>
					<td><fmt:formatNumber value="${m.countQ}" pattern="#,###" /><br><fmt:formatNumber value="${m.sumQ}" pattern="#,###" /></td>
					<td><fmt:formatNumber value="${m.countA}" pattern="#,###" /><br><fmt:formatNumber value="${m.sumA}" pattern="#,###" /></td>
					<td><c:if test="${m.sAuthType == 'M'}">인증완료</c:if><br>${m.flagAdult}</td>
					<td>${m.flagEmail}<br>${m.flagSMS}</td>
					<td>${m.flagProfileOpen}<br>${m.flagSelfAnswer}</td>
					<td>${m.dateReg}<br>${m.dateLogin}</td>
				</tr>
                <tr>
                  <td colspan="12" style="margin:0; padding:0; height:1px; border-bottom: 1px solid #EAEAEA;"></td>
                </tr>
				<c:set var="loopK" value="${loopK + 1}"/>
			</c:forEach>
			</c:if>
			<c:set var="pagesize" value="${n_pagesize}"/>
			<%
				//int loopK = Integer.parseInt( String.valueOf( pageContext.getAttribute("loopK") ) );
				//int pagesize = Integer.parseInt( String.valueOf( pageContext.getAttribute("pagesize") ) );
			
				//while(loopK < pagesize) {
			%>
			<!--
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			-->
			<%
				//}
			%>
			</table>
			<p class="paging">
			<span class="num">
			<!-- paging -->
			${paging}
			<!-- paging// -->
			</span>
			</p>
			<br><br>
			<!-- 엑셀다운로득 기능 구현 보류 - 검색 기능 개선후 엑셀 다운로드 재개발 예정(후 순위 개발 - 2020-11-6) -->
			<!-- <A HREF="javascript:goExcelPrint()">엑셀다운 </a> -->
<script>
  function goExcelPrint() {   document.location="/aadmin/excel"  } <!--asp-->
</script>

		</div>
	</div>
</div>
</form>


</body>
</html>