<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/Common/include/headerAdmin.jsp" %>
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
<script>
function delQuiz(uid) {
	if(confirm('질문을 삭제할 경우 다시 복구 할 수 없으며, \n 해당 질문과 연관된 퀴즈에서도 삭제 됩니다. \n 정말 삭제하시겠습니까?')) {
		
		$.ajax({
			type: "POST",
			url: "/aadmin/quiz/quizQueDel",
			data: {"uid" : uid},
			success: function() {
				alert('삭제되었습니다');
				location.reload();
			}
		});
		
		return false;
	}
}

function setOpener(p_uid, p_quest) {
	opener.getPopupData(p_uid, p_quest);
	self.close();
}

function fnPopQuiz(uid) {
	window.name = "childForm";
	
	var openPop;
	openPop = window.open("/quiz/quizList?queid="+uid, "quizForm", "width=800, height=500, resizable = no, scrollbars = no");
	openWin.focus();
}
</script>

<form name="frm_sch" method="post" onSubmit="return false;">
<input type="hidden" name="src_Target" value="${curPageName}">
<input type="hidden" name="pg" value="${n_curpage}">

<div id="wrapper">
	<div id="header_wrapper">
	</div>
	<div id="M_wrapper">
		<br>
		<div class="board_input8">
			<table width="100%" border="0" align="center"  cellpadding="0" cellspacing="0">
				<tr>
					<td width="90%">
						<p class="t_inputmenu" style="margin-top:15px;">
						<label for="input01" id="label_input01">퀴즈 리스트 </label>
						</p>
					</td>
					<td>
						<button type="button" class="btn btn-warning btn-sm" onClick="self.close();">닫기</button>
					</td>
				</tr>
			</table>
		</div>
		<br>

		<div id="list">
			<p>전체 회원 : ${n_trec} 명</p>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="boardList01">
				<tr>
					<th width="">No<br></th>
					<th width="">코드</th>
					<th width="">일문</th>
					<th width="">문제출제시간</th>
					<th width="">선택지</th>
					<th width="">문제출제횟수</th>
					<th width="">정답률</th>
					<th width="">출제가능여부</th>
					<th width="">미리보기</th>
					<th width="">적용하기</th>
				</tr>

			<c:if test="${n_trec > 0}">
			<c:forEach var="m" items="${quizList}" varStatus="status">
				<c:set var="per" value="0"/>
				<c:if test="${m.reqTotal > 0}">
				<c:set var="per" value="${ (m.correctCnt / m.reqTotal) * 100 }"/>
				</c:if>
				
				<tr style='cursor:pointer;' onmouseover='this.style.background="#FFE0B3";' onmouseleave='this.style.background="#FFFFFF";'>
					<td class="cssLinkMemberSeq"><fmt:formatNumber value="${(n_trec - status.index) - ( (n_curpage - 1)  *  n_pagesize ) }" pattern="#,###" /></td>
					<td>${m.uid}</td>
					<td style="text-align:left;">${m.quest}</td>
					<td><fmt:formatNumber minIntegerDigits="2" value="${m.rtime1}" type="number"/>:<fmt:formatNumber minIntegerDigits="2" value="${m.rtime2}" type="number"/></td>
					<td><fmt:formatNumber value="${m.acount}" pattern="#,###" /></td>					
					<td><fmt:formatNumber value="${m.pubCnt}" pattern="#,###" /></td>
					<td><fmt:formatNumber value="${per}" pattern="#,###" />%</td>
					<td>
					<c:choose>
						<c:when test="${m.useChk == 'Y'}"><input type="button" value="가능" /></c:when>
						<c:otherwise><input type="button" value="불가" /></c:otherwise>
					</c:choose>
					</td>
					<td><a href="javascript:void(0)" onclick="javascript:fnPopQuiz('${m.uid}')">미리보기</a></td>
					<td>
					<c:if test="${m.useChk == 'Y'}">
					<a href="javascript:void(0)" onclick="javascript:setOpener('${m.uid}','${m.quest}')">적용</a>
					</c:if>
					</td>
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
			<br><br>

		</div>
	</div>
</div>
</form>


</body>
</html>