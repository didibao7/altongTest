<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ include file="/Common/include/headerAdmin.jsp" %>
<%
pageContext.setAttribute("crcn", "\r\n"); //Space, Enter
pageContext.setAttribute("br", "<br>"); //br 태그
%>
<body>

<div id="wrapper">
	<div id="header_wrapper">
	</div>
	<div id="M_wrapper">
	<%@ include file="/Common/include/menuAdmin.jsp" %>
		<br>
		
		<div id="list">
			<form name="frm_sch" method="post" onSubmit="return false;">

            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="rowboardList_top">
              <tr>
                <td height="50"><b><font size="4">${mem.name}</b>님의 회원정보입니다.</font></td>
              </tr>
    <tr>
        <td colspan="4" BORDER="0">&nbsp;</td>
    </tr>
              <tr>
                  <td width="20%" bgcolor="#efefef">회원번호</td>
                  <td width="30%"><input type="text" name="Seq" value="${mem.seq}" class="input_textarea" maxlength="30" style="width:200px; background-color:#EEEEEE;" READONLY></td>
                  <td width="20%" bgcolor="#efefef">추천인코드</td>
                  <td width="30%"><input type="text" name="ChuCode" value="${mem.chuCode}" class="input_textarea" maxlength="30" style="width:200px;"></td>
              </tr>
              <tr>
                  <td bgcolor="#efefef">회원성명/ PW</td>
                  <td><input type="text" name="Name" value="${mem.name}" class="input_textarea" maxlength="10" style="width:200px;" > / <input type="text" name="Password" value="" class="input_textarea" maxlength="10" style="width:200px;"></td>
                  <td bgcolor="#efefef">닉네임</td>
                  <td>
                      <input type="text" name="NickName" value="${mem.nickName}" class="input_textarea" maxlength="30" style="width:200px;" >
                      <input type="hidden" name="NickNameOrg"  value="${mem.nickName}"/>
                  </td>
              </tr>
              <tr>
                  <td bgcolor="#efefef">메일주소</td>
                  <td><input type="text" name="Email" value="${mem.email}" class="input_textarea" maxlength="20" style="width:200px;"></td>
                  <td bgcolor="#efefef">휴대번호</td>
                  <td><input type="text" name="Phone" value="${mem.phone}" class="input_textarea" maxlength="20" style="width:200px; -background-color:#EEEEEE;" -READONLY></td>
              </tr>
              <tr>
                  <td bgcolor="#efefef">레벨</td>
                  <td>
						<input type="text" name="Lv" value="${mem.lv}" class="input_textarea" maxlength="10" style="width:200px; background-color:#EEEEEE;" READONLY>
						<%if(CommonUtil.libhasAuthority(memberViewLv, userSeq)) {%>
                        <a style="padding : 3px" href="/aadmin/memberViewLv?Seq=${mem.seq}"> [레벨 수정] </a><!-- alt -->
                        <%}%>
                        </td>
                    <td bgcolor="#efefef">보유 알머니</td>
                    <td>
						<input type="text" value="${mem.almoney}" class="input_textarea" maxlength="10" style="width:200px; background-color:#EEEEEE;" READONLY>
					</td>
				</tr>
                <tr>
                    <td bgcolor="#efefef">유저유형</td>
                    <td><input type="text" name="MemberType" value="${mem.memberType}" class="input_textarea" maxlength="10" style="width:200px;">
                        <p>0: 일반회원, 1: RS팀, 2: 천사단, 99: 고스트(GHOST)</p>
                    </td>
                    <td bgcolor="#efefef">
                    <%if(CommonUtil.libhasAuthority(memberViewAlmoney, userSeq)) {%>
                    <a href="/aadmin/memberViewAlmoney?Seq=${mem.seq}" style="padding : 3px">[알머니 지급하기]</a>
                    <%}%>
                    </td>
                    <td>
					</td>
                </tr>
				<tr>
                    <td bgcolor="#efefef">관리자 메모</td>
                    <td colspan="3"><textarea name="Memo" style="width:100%;height:200px">${fn:replace(mem.memo, br, crcn)}</textarea></td>
                </tr>
            </table>
            <br><br>
            <p align="center">
                <button type="button" class="btn btn-default" onClick="javascript:goSubmit('frm_sch','/aadmin/memberSave','ifrm');">수정하기</button>
                <!-- 회원 탈퇴처리 [추가(2018.03.15): 김현구] -->
                &nbsp;
                <button type="button" class="btn btn-default" onClick="fnMember_Leave();">탈퇴처리</button>
                <script language="javascript">
                function fnMember_Leave() {
                    var ans1 = confirm("정말로 탈퇴 처리하시겠습니까? \n\n[확인]을 누르시면 탈퇴 처리됩니다.");
                    if ( ans1 == false ) return;
                    
                    goSubmit('frm_sch','/aadmin/memberLeave?Seq=${mem.seq}','ifrm');
                }
                </script>
            </p>
        </form>

  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"  class="rowboardList_top">
    <tr>
        <td colspan="4" BORDER="0">&nbsp;</td>
    </tr>
    <tr>
        <td width="20%" bgcolor="#efefef">NICE 본인인증</td>
        <td width="30%"><c:choose>
        	<c:when test="${mem.sAuthType == 'M'}">인증완료</c:when>
        	<c:otherwise>미인증</c:otherwise>
        </c:choose></td>
        <td width="20%" bgcolor="#efefef">성인인증</td>
        <td width="30%"><input type="text" name="FlagAdult" value="${mem.flagAdult}" class="input_textarea" maxlength="10" style="width:200px;"></td>
    </tr>
    <tr>
        <td bgcolor="#efefef"><p>복호화한 시간(YYMMDDHHMMSS)</p></td>
        <td>
						<input type="text" name="sCipherTime" value="${mem.sCipherTime}" class="input_textarea" maxlength="30" style="width:250px;" >
					</td>
        <td bgcolor="#efefef">NICE 응답번호</td>
        <td>
						<input type="text" name="sRequestNumber" value="${mem.sRequestNumber}" class="input_textarea" maxlength="30" style="width:250px;" >
					</td>
				</tr>
    <tr>
        <td bgcolor="#efefef">인증수단</td>
        <td>
						<c:if test="${mem.sAuthType == 'M'}">핸드폰</c:if>
					</td>
        <td bgcolor="#efefef">성별</td>
        <td>
        				<c:choose>
        					<c:when test="${mem.sGender == '0'}">여자</c:when>
        					<c:when test="${mem.sGender == '1'}">남자</c:when>
        					<c:otherwise></c:otherwise>
        				</c:choose>
					</td>
				</tr>
    <tr>
        <td bgcolor="#efefef">생년월일</td>
        <td>
            <input type="text" name="sBirthDate" value="${mem.sBirthDate}" class="input_textarea" maxlength="10" style="width:200px;" >
        </td>
        <td bgcolor="#efefef">내/외국인정보</td>
        <td>
        	<c:choose>
        		<c:when test="${mem.sNationalInfo == '0'}">내국인</c:when>
        		<c:otherwise>외국인</c:otherwise>
        	</c:choose>
        </td>
    </tr>
    <tr>
        <td bgcolor="#efefef">통신사</td>
        <td>
						<input type="text" name="sMobileCo" value="${mem.sMobileCo}" class="input_textarea" maxlength="10" style="width:200px;" >
					</td>
        
				</tr>
				
    
			</table>
			<table width="100%" align="center">
			<tr>
			<td height="100"><font size="4"><a href="/aadmin/memberList " >목록으로</a></font></td>
			<td height="100"><font size="4"><a href="/aadmin/member_q?Seq=${mem.seq}&NickName=${mem.nickName}" target="_blank">작성한 질문</a></font></td>

			<td height="100"><font size="4"><a href="/aadmin/member_a?Seq=${mem.seq}&NickName=${mem.nickName}" target="_blank">작성한 답변</a></font></td>
			<td height="100"><font size="4"><a href="/aadmin/member_v?Seq=${mem.seq}&NickName=${mem.nickName}" target="_blank">열람한 답변</a></font></td>
			<td height="100"><font size="4"><a href="/member/myBank_Admin?MemberSeq=${mem.seq}" target="_blank">뱅크</a></font></td>
				</tr></table>
			
		</div>
	</div>
</div>
</form>
<iframe name="ifrm" width="100%" height="0" frameborder="0"></iframe>			
</body>
</html>