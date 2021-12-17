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
function delQuizGame(uid) {
	if(confirm('게임을 삭제할 경우 다시 복구 할 수 없으며, \n 해당 게임과 관련된 모든 데이터가 삭제 됩니다. \n 정말 삭제하시겠습니까?')) {
		
		$.ajax({
			type: "POST",
			url: "/aadmin/quiz/quizGameDel",
			data: {"uid" : uid},
			success: function() {
				alert('삭제되었습니다');
				location.reload();
			}
		});
		
		return false;
	}
}

function procPop(uid) {
	window.name = "gForm";
	
	var openWin;
	openWin = window.open("/aadmin/quiz/getQuizWinner?uid="+uid, "childForm", "width=800, height=500, resizable = no, scrollbars = no"); 
	openWin.focus();
}

function quizPop(uid) {
window.name = "gForm";
	
	var openWin;
	openWin = window.open("/quiz/gameDemo?uid=" + uid + "&ord=1", "childForm", "width=800, height=500, resizable = no, scrollbars = no"); 
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
	<%@ include file="/Common/include/menuAdmin.jsp" %>
		<br>
		<div class="title-wrapper row">
				<div class="title-name col-md-4">
					<a href="/aadmin/quiz/quizGameList"></a>퀴즈게임 리스트
				</div>
				<div class="title-name col-md-4">
					<a href="/aadmin/quiz/quizList">[퀴즈 리스트]</a>
				</div>
		</div>
		<br>
		<div class="board_input8">
			<table width="100%" border="0" align="center"  cellpadding="0" cellspacing="0">
				<tr>
					<td width="95%">
						<p class="t_inputmenu" style="margin-top:15px;">
						<label for="input01" id="label_input01">퀴즈게임 리스트 </label>
						</p>
					</td>
					<td>
						<button type="button" class="btn btn-warning btn-sm" onClick="javascript:location.href='/aadmin/quiz/quizGameInput';">퀴즈게임 등록하기</button>
					</td>
				</tr>
			</table>
		</div>

		<div id="list">
			<p>전체 회원 : ${n_trec} 명</p>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="boardList01">
				<tr>
					<th width="">No<br></th>
					<th width="">게임코드</th>
					<th width="">게임시작일시</th>
					<th width="">회차</th>
					<th width="" style="text-align:left;">게임제목</th>
					<th width="">게임현황</th>
					<th width="">우승할지급</th>
					<th width="">미리보기</th>
					<th width="">삭제</th>
				</tr>

			<c:if test="${n_trec > 0}">
			<c:forEach var="m" items="${quizList}" varStatus="status">
				<fmt:formatNumber var="stDtH" minIntegerDigits="2" value="${m.startDtH}" type="number"/>
				<fmt:formatNumber var="stDtM" minIntegerDigits="2" value="${m.startDtM}" type="number"/>
				<tr style='cursor:pointer;' onmouseover='this.style.background="#FFE0B3";' onmouseleave='this.style.background="#FFFFFF";'>
					<td class="cssLinkMemberSeq"><fmt:formatNumber value="${(n_trec - status.index) - ( (n_curpage - 1)  *  n_pagesize ) }" pattern="#,###" /></td>
					<td>${m.uid}</td>
					<td>${m.startYmd} ${m.startDtType} <c:choose><c:when test="${m.startDtType == '정오'}">12</c:when><c:otherwise>${stDtH}</c:otherwise></c:choose>시 ${stDtM}분</td>
					<td>${m.step}</td>
					<td style="text-align:left;"><a href="/aadmin/quiz/quizGameInput?uid=${m.uid}">${m.subject}</a></td>
					<td><!-- 게임현황의 완료 상태 정의 필요함 -->
					<c:choose>
						<c:when test="${m.useChk == 'Y' and m.reqTotal == 0}"><input type="button" value="준비중" /></c:when>
						<c:when test="${m.useChk == 'Y' and m.reqTotal > 0}"><input type="button" value="진행중" /></c:when>
						<c:when test="${m.useChk == 'N' and m.complete == 'N'}"><input type="button" value="OFF" /></c:when>
						<c:when test="${m.complete == 'Y'}"><input type="button" value="완료" /></c:when>
						<c:when test="${m.complete == 'C'}"><input type="button" value="완료" /></c:when>
						<c:otherwise><input type="button" value="OFF" /></c:otherwise>
					</c:choose>
					</td>
					<td>
					<c:choose>
						<c:when test="${m.complete == 'Y'}"><input type="button" value=" 완료 " onclick="javascript:procPop('${m.uid}')" /></c:when>
						<c:when test="${m.complete == 'C'}"><input type="button" value=" 이월 " onclick="javascript:procPop('${m.uid}')" /></c:when>
						<c:otherwise><input type="button" value="지급전" onclick="javascript:procPop('${m.uid}')" /></c:otherwise>
					</c:choose>
					</td>
					
					<td><a href="javascript:void(0);" onclick="javascript:quizPop('${m.uid}');">미리보기</a></td><!-- 게임실행화면 -->
					<td>
					<c:choose>
            		<c:when test="${m.reqTotal == 0}">
            			<a href="javascript:void(0);" onclick="javascript:delQuizGame('${m.uid}')">X</a>
            		</c:when>
            		<c:otherwise>
            			<a href="javascript:void(0);" onClick="javascript:alert('삭제할 수 없습니다.');">X</a>
            		</c:otherwise>
            		</c:choose>
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