<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/Common/include/headerAdmin.jsp" %>
<%
pageContext.setAttribute("crcn", "\r\n"); //Space, Enter
pageContext.setAttribute("br", "<br>"); //br 태그
%>
<body>
<script>
function addItem() {
	var items = document.getElementsByName("ord[]");
	
	if(items.length < 15) {
		var html = "<tr>";
		html += '<td width=\"100\">문제<input type="text" name="ord[]" maxlength="1" value="" style="width:20px;" readonly /></td>';
		html += '<td><input type="button" value=" ^ " onclick="setHead($(this))"><input type="button" value=" v " onclick="setTail($(this))"></td>';
		html += '<td><input type="text" name="queid[]" value="" class="input_textarea" style="width:100px;"></td>';
		html += '<td><input type="text" name="quest[]" value="" class="input_textarea" maxlength="20" style="width:100%;" readonly></td>';
		html += '<td><input type="button" value="찾아보기" onclick="getQuiz($(this))"></td>';
		html += '<td><input type="button" value="확인" onclick="setQuiz($(this))"></td>';
		html += '<td><input type="button" value=" X " onclick="delItem($(this))"></td>';
		html += "</tr>";
		
		$('#ansItem').append(html);
		reGenerator();
	}
	else {
		alert('추가할 수 없습니다. 최대 답변갯수는 다섯개 입니다.');
	}
	
	return false;
}

function setHead(obj) {
	//위로 이동 기능 구현
	const thisObj = obj.parent().parent();
	const beforeObj = obj.parent().parent().prev(); //tb_h
	const beforeObj_first_title = beforeObj.children().filter(':first-child').text();
	
	if(beforeObj_first_title != '출제순서') {
		//console.log('beforeObj : ' + beforeObj_first_title);
		//위치 변경
		beforeObj.before(thisObj);
	}
		
	reGenerator();
}

function setTail(obj) {
	//아래로 이동 기능 구현
	const thisObj = obj.parent().parent();
	const nextObj = obj.parent().parent().next(); 
	const nextObj_first_title = nextObj.children().filter(':first-child').text();
	
	if(nextObj_first_title != '') {
		//console.log('nextObj : ' + nextObj_first_title);
		nextObj.after(thisObj);
	}
	
	reGenerator();
}

function delItem(obj) {
	var items = document.getElementsByName("ord[]");
	
	if(items.length > 5) { 
		obj.parent().parent().remove();
		reGenerator();
	}
	else if(items.length) {
		alert('삭제할 수 없습니다. 최소 퀴즈 갯수는 5 입니다.');
	}
	return false;
}


function reGenerator() {
	var items = document.getElementsByName("ord[]");
	
	for(var i = 0; i < items.length; i++) {
		document.getElementsByName("ord[]")[i].value = (i + 1);
	}
}

function goSubmit() {
	var step = document.getElementsByName("step")[0].value;
	var subject = document.getElementsByName("subject")[0].value;
	
	var admAlmoney = document.getElementsByName("admAlmoney")[0].value;
	var attendAlmoney = document.getElementsByName("attendAlmoney")[0].value;
	var carryoverMoney = document.getElementsByName("carryoverMoney")[0].value;
	
	var startYmd = document.getElementsByName("startYmd")[0].value;
	
	if(admAlmoney == '') {
		document.getElementsByName("admAlmoney")[0].value = 0;
	}
	if(attendAlmoney == '') {
		attendAlmoney = 0;
	}
	if(carryoverMoney == '') {
		document.getElementsByName("carryoverMoney")[0].value = 0;
	}
	
	var startDtType = document.getElementsByName("startDtType")[0];
	var startDtTypeVal = startDtType.options[startDtType.selectedIndex].value;
	
	var startDtH = document.getElementsByName("startDtH")[0];
	var startDtHVal = startDtH.options[startDtH.selectedIndex].value;
	
	var startDtM = document.getElementsByName("startDtM")[0];
	var startDtMVal = startDtM.options[startDtM.selectedIndex].value;
	
	
	var items = document.getElementsByName("ord[]");
	var queErrCnt = 0;
	var qErrCnt = 0;
	for(var i = 0; i < items.length; i++) {
		if(document.getElementsByName("quest[]")[i].value == '') { queErrCnt++; }
		if(document.getElementsByName("queid[]")[i].value == '') { qErrCnt++; }
	}
	
	if(step == '') {
		alert('[회차]\n항목의 내용을 정확하게 기재해주세요.');
		document.getElementsByName("step")[0].focus();
		return false;
	}
	if(subject == '') {
		alert('[게임제목]\n항목의 내용을 정확하게 기재해주세요.');
		document.getElementsByName("subject")[0].focus();
		return false;
	}
	if(attendAlmoney == 0) {
		alert('[회원참가알]\n항목의 내용을 정확하게 기재해주세요.');
		document.getElementsByName("attendAlmoney")[0].focus();
		return false;
	}
	
	if(startYmd == '') {
		alert('[게임시작일]\n항목의 내용을 정확하게 기재해주세요.');
		document.getElementsByName("startYmd")[0].focus();
		return false;
	}
	
	if(startDtType == '') {
		alert('[오전/오후]\n항목의 내용을 정확하게 선택하여주세요.');
		document.getElementsByName("startDtType").focus();
		return false;
	}
	if(startDtHVal == '' && startDtTypeVal != '정오') {
		alert('[시간]\n항목의 내용을 정확하게 선택하여주세요.');
		document.getElementsByName("startDtH").focus();
		return false;
	}
	if(startDtMVal == '') {
		alert('[분]\n항목의 내용을 정확하게 선택하여주세요.');
		document.getElementsByName("startDtM").focus();
		return false;
	}
	
	if(qErrCnt > 0) {
		alert('[질문코드]\n항목의 내용을 정확하게 기재해주세요.');
		$('#ansItem').focus();
		return false;
	}
	
	if(queErrCnt > 0) {
		alert('[질문내용]\n항목의 내용을 정확하게 기재해주세요.');
		$('#ansItem').focus();
		return false;
	}
	
	$('#frm_quiz').attr('action', '/aadmin/quiz/quizGameSave');
	$('#frm_quiz').submit();
}

var dataObj;
function getQuiz(obj) {
	//alert('준비중!!!');
	
	const thisObj = obj.parent().parent();
	const thisObj_code = thisObj.find("input[name='queid[]']");
	const thisObj_quest = thisObj.find("input[name='quest[]']");
	
	dataObj = thisObj;
	//console.log('thisObj_code : ' + thisObj_code.val());
	
	//popup 창으로 thisObj 전달
	window.name = "parentForm";
	
	var openWin;
	openWin = window.open("/aadmin/quiz/getQuiz", "childForm", "width=800, height=500, resizable = no, scrollbars = no"); 
	openWin.focus();
	
	return false;
}

function getPopupData(uid, quest) {
	const thisObj_code = dataObj.find("input[name='queid[]']");
	const thisObj_quest = dataObj.find("input[name='quest[]']");
	
	var items = document.getElementsByName("ord[]");
	var qDblCnt = 0;
	for(var i = 0; i < items.length; i++) {
		if(document.getElementsByName("queid[]")[i].value == uid) { qDblCnt++; }
	}
	
	if(qDblCnt == 0) {
		thisObj_code.val(uid);
		thisObj_quest.val(quest);
	}
}

function setQuiz(obj) {
	const thisObj = obj.parent().parent();
	const thisObj_code = thisObj.find("input[name='queid[]']").val();
	const thisObj_quest = thisObj.find("input[name='quest[]']");
	
	$.ajax({
		type: 'post',
		url: '/aadmin/getQuizQueAjax',
		data: {'uid' : thisObj_code},
		dataType: 'json',
		crossDomain: true,
		success: function (r) {
			//console.log('result : ' + r.result);
			switch (r.result) {
				case 'false':
					alert('검색결과가 없습니다. 질문코드를 확인하여주세요.');
					thisObj_quest.val('');
					break;
				case 'access':
					alert('접근 권한이 없습니다. 다시 로그인하여 주세요.');
					thisObj_quest.val('');
					break;
				default:
					//if (r.result) alert(r.result);
					thisObj_quest.val(r.result);
					break;
			}
		},
		error: function (r, textStatus, err) {
			//if (r.responseText && r.responseText.substr(0, 13) == '<!--LOGOFF-->') { top.location = '/'; return; } //http://www.altong.com/
			console.log(r);
		},
		complete: function () {
			document.xhr = false;
		}
	});
	
}

function getCarryoverMoney() {
	document.getElementsByName("carryoverMoney")[0].value = 0;
	$.ajax({
		type: 'post',
		url: '/aadmin/getCarryoverMoney',
		dataType: 'json',
		crossDomain: true,
		success: function (r) {
			//console.log('result : ' + r.result);
			switch (r.result) {
				case 'access':
					alert('접근 권한이 없습니다. 다시 로그인하여 주세요.');
					break;
				default:
					//if (r.result) alert(r.result);
					document.getElementsByName("carryoverMoney")[0].value = r.result; 
					break;
			}
		},
		error: function (r, textStatus, err) {
			//if (r.responseText && r.responseText.substr(0, 13) == '<!--LOGOFF-->') { top.location = '/'; return; } //http://www.altong.com/
			console.log(r);
		},
		complete: function () {
			document.xhr = false;
		}
	});
}

function quizGameEnableChk() {
	var uid = '${uid}';
	$.ajax({
		type: 'post',
		url: '/aadmin/quizGameEnableChk',
		dataType: 'json',
		data: {'uid' : uid },
		crossDomain: true,
		success: function (r) {
			//console.log('result : ' + r.result);
			if(r.result == 'access') {
				alert('접근 권한이 없습니다. 다시 로그인하여 주세요.');
			}
			else {
				var useChk = document.getElementsByName("useChk");
				var cnt = parseInt(r.result);
				if(cnt > 0) {
					alert('진행중인 게임이 있습니다.');
					
					useChk[0].checked = false;
					useChk[1].checked = true;
				}
			}
		},
		error: function (r, textStatus, err) {
			//if (r.responseText && r.responseText.substr(0, 13) == '<!--LOGOFF-->') { top.location = '/'; return; } //http://www.altong.com/
			console.log(r);
		},
		complete: function () {
			document.xhr = false;
		}
	});
}

function changeDtType() {
	var startDtType = document.getElementsByName("startDtType")[0];
	var startDtTypeVal = startDtType.options[startDtType.selectedIndex].value;
	
	var startDtH = document.getElementsByName("startDtH")[0];
	
	if(startDtTypeVal == '정오') {
		startDtH.options[0].selected = true;
		startDtH.disabled = true;
	}
	else {
		startDtH.disabled = false;
	}
}
</script>
<div id="wrapper">
	<div id="header_wrapper">
	</div>
	<div id="M_wrapper">
	<%@ include file="/Common/include/menuAdmin.jsp" %>
		<br>
		<div class="title-wrapper row">
				<div class="title-name col-md-4">
					<a href="/aadmin/quiz/quizGameList"></a>[퀴즈게임 리스트]
				</div>
				<div class="title-name col-md-4">
					<a href="/aadmin/quiz/quizList">[퀴즈 리스트]</a>
				</div>
		</div>
		<br>
		
		<div id="list">
			<form id="frm_quiz" name="frm_sch" method="post">

            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="rowboardList_top">
				<tr>
                	<td colspan="2" height="50"><b><font size="4">퀴즈게임 등록</font></td>
              	</tr>
			    <tr>
			        <td colspan="2" BORDER="0">&nbsp;</td>
			    </tr>
			    
              	
              	<tr>
                  <td bgcolor="#efefef">문제코드</td>
                  <td><input type="text" name="uid" value="${uid}" class="input_textarea" maxlength="20" style="width:200px;" readonly></td>
              	</tr>
              	<tr>
                  <td bgcolor="#efefef">회차</td>
                  <td><input type="text" name="step" value="${quiz.step}" class="input_textarea" maxlength="20" style="width:200px;">회</td>
              	</tr>
              	<tr>
                    <td bgcolor="#efefef">게임제목</td>
                    <td><input type="text" name="subject" class="input_textarea" style="width:100%;" value="${quiz.subject}"></td>
                </tr>
                
                <tr>
                  <td bgcolor="#efefef">관리자 지급 알</td>
                  <td><input type="text" name="admAlmoney" value="${quiz.admAlmoney}" class="input_textarea" style="width:200px;" onkeyup="checkNum(this);"></td>
              	</tr>
              	<tr>
                  <td bgcolor="#efefef">회원 참가알</td>
                  <td><input type="text" name="attendAlmoney" value="${quiz.attendAlmoney}" class="input_textarea" style="width:200px;" onkeyup="checkNum(this);"></td>
              	</tr>
              	<tr>
                  <td bgcolor="#efefef">이월우승상금</td>
                  <td>
                  	<input type="text" name="carryoverMoney" value="${quiz.carryoverMoney}" class="input_textarea" style="width:200px;" onkeyup="checkNum(this);">
                  	<input type="button" value="가져오기" onclick="getCarryoverMoney()">
                  </td>
              	</tr>
              	
              	<tr>
                  <td bgcolor="#efefef">게임시작일</td>
                  <td>
                  	<input type="date" name="startYmd" value="${quiz.startYmd}" class="input_textarea" style="width:200px;"><!-- 달력 - 캘린더 연동 -->
                  	<select name="startDtType" onchange="changeDtType()">
                  		<option value="" <c:if test="${quiz.startDtType == ''}">selected</c:if>>선택
                  		<option value="오전" <c:if test="${quiz.startDtType == '오전'}">selected</c:if>>오전
                  		<option value="오후" <c:if test="${quiz.startDtType == '오후'}">selected</c:if>>오후
                  		<option value="정오" <c:if test="${quiz.startDtType == '정오'}">selected</c:if>>정오
                  	</select>
                  	
                  	<select name="startDtH" <c:if test="${quiz.startDtType == '정오'}">disabled</c:if>>
                  		<option value="" <c:if test="${quiz.startDtH == ''}">selected</c:if>>선택
                  		<c:forEach var="i" begin="1" end="11" step="1">
                  		<option value="${i}" <c:if test="${quiz.startDtH == i}">selected</c:if>>${i}
                  		</c:forEach>
                  	</select>
                  	시
                  	<select name="startDtM">
                  		<option value="" <c:if test="${quiz.startDtM == ''}">selected</c:if>>선택
                  		<c:forEach var="i" begin="0" end="59" step="1">
                  		<option value="${i}" <c:if test="${quiz.startDtM == i}">selected</c:if>>${i}
                  		</c:forEach>
                  	</select>
                  	분 
                  </td>
              	</tr>
                
                <tr>
                  <td bgcolor="#efefef">게임 ON/OFF</td>
                  <td>
                  <input type="radio" name="useChk" value="Y" class="input_textarea" maxlength="20" style="width:200px;" onclick="javascript:quizGameEnableChk()" <c:if test="${quiz.useChk == 'Y'}">checked</c:if>> ON  
                  <input type="radio" name="useChk" value="N" class="input_textarea" maxlength="20" style="width:200px;" <c:if test="${quiz == null or quiz.useChk == 'N'}">checked</c:if>> OFF
                  </td>
              	</tr>
				
				<!-- 정답체크 -->
                <!-- 답변입력 -->
                <!-- 답변삭제-논리적 삭제 -->
                <!-- 항목추가 버튼 -->
                <tr>
                	<td rowspan="2">퀴즈문제</td>
                </tr>
                <tr>
                	<td>
                	<table id="ansItem" style="width:100%;">
                		<tr id="tb_h">
		                    <td width="100">출제순서</td>
		                    <td>순서바꾸기</td>
		                    <td>질문코드</td>
		                    <td>질문내용</td>
		                    <td>질문찾기</td>
		                    <td>질문코드적용</td>
		                    <td>삭제</td>
		                </tr>
                		 <c:choose>
		                	<c:when test="${ansList == null}">
		                	<tr>
			                    <td width="100">문제<input type="text" name="ord[]" maxlength="1" value="1" style="width:20px;" readonly /></td>
			                    <td><input type="button" value=" ^ " onclick="setHead($(this))"><input type="button" value=" v " onclick="setTail($(this))"></td>
			                    <td><input type="text" name="queid[]" value="" class="input_textarea" style="width:100px;"></td>
			                    <td><input type="text" name="quest[]" value="" class="input_textarea" style="width:100%;" readonly></td>
			                    <td><input type="button" value="찾아보기" onclick="getQuiz($(this))"></td>
			                    <td><input type="button" value="확인" onclick="setQuiz($(this))"></td>
			                    <td><input type="button" value=" X " onclick="delItem($(this))"></td>
			                </tr>
			                <tr>
			                    <td width="100">문제<input type="text" name="ord[]" maxlength="1" value="2" style="width:20px;" readonly /></td>
			                    <td><input type="button" value=" ^ " onclick="setHead($(this))"><input type="button" value=" v " onclick="setTail($(this))"></td>
			                    <td><input type="text" name="queid[]" value="" class="input_textarea" style="width:100px;"></td>
			                    <td><input type="text" name="quest[]" value="" class="input_textarea" style="width:100%;" readonly></td>
			                    <td><input type="button" value="찾아보기" onclick="getQuiz($(this))"></td>
			                    <td><input type="button" value="확인" onclick="setQuiz($(this))"></td>
			                    <td><input type="button" value=" X " onclick="delItem($(this))"></td>
			                </tr>
			                <tr>
			                    <td width="100">문제<input type="text" name="ord[]" maxlength="1" value="3" style="width:20px;" readonly /></td>
			                    <td><input type="button" value=" ^ " onclick="setHead($(this))"><input type="button" value=" v " onclick="setTail($(this))"></td>
			                    <td><input type="text" name="queid[]" value="" class="input_textarea" style="width:100px;"></td>
			                    <td><input type="text" name="quest[]" value="" class="input_textarea" style="width:100%;" readonly></td>
			                    <td><input type="button" value="찾아보기" onclick="getQuiz($(this))"></td>
			                    <td><input type="button" value="확인" onclick="setQuiz($(this))"></td>
			                    <td><input type="button" value=" X " onclick="delItem($(this))"></td>
			                </tr>
			                <tr>
			                    <td width="100">문제<input type="text" name="ord[]" maxlength="1" value="4" style="width:20px;" readonly /></td>
			                    <td><input type="button" value=" ^ " onclick="setHead($(this))"><input type="button" value=" v " onclick="setTail($(this))"></td>
			                    <td><input type="text" name="queid[]" value="" class="input_textarea" style="width:100px;"></td>
			                    <td><input type="text" name="quest[]" value="" class="input_textarea" style="width:100%;" readonly></td>
			                    <td><input type="button" value="찾아보기" onclick="getQuiz($(this))"></td>
			                    <td><input type="button" value="확인" onclick="setQuiz($(this))"></td>
			                    <td><input type="button" value=" X " onclick="delItem($(this))"></td>
			                </tr>
			                <tr>
			                    <td width="100">문제<input type="text" name="ord[]" maxlength="1" value="5" style="width:20px;" readonly /></td>
			                    <td><input type="button" value=" ^ " onclick="setHead($(this))"><input type="button" value=" v " onclick="setTail($(this))"></td>
			                    <td><input type="text" name="queid[]" value="" class="input_textarea" style="width:100px;"></td>
			                    <td><input type="text" name="quest[]" value="" class="input_textarea" style="width:100%;" readonly></td>
			                    <td><input type="button" value="찾아보기" onclick="getQuiz($(this))"></td>
			                    <td><input type="button" value="확인" onclick="setQuiz($(this))"></td>
			                    <td><input type="button" value=" X " onclick="delItem($(this))"></td>
			                </tr>
		                	</c:when>
		                	<c:otherwise>
		                	<c:forEach var="ans" items="${ansList}" varStatus="status">
		                		<tr>
				                    <td width="100">문제<input type="text" name="ord[]" maxlength="1" value="${ans.ord}" style="width:20px;" readonly /></td>
				                    <td><input type="button" value=" ^ " onclick="setHead($(this))"><input type="button" value=" v " onclick="setTail($(this))"></td>
				                    <td><input type="text" name="queid[]" value="${ans.queid}" class="input_textarea" style="width:100px;"></td>
				                    <td><input type="text" name="quest[]" value="${ans.quest}" class="input_textarea" style="width:100%;" readonly></td>
				                    <td><input type="button" value="찾아보기" onclick="getQuiz($(this))"></td>
				                    <td><input type="button" value="확인" onclick="setQuiz($(this))"></td>
				                	<td><input type="button" value=" X " onclick="delItem($(this))"></td>
				                </tr>
		                	</c:forEach>
		                	</c:otherwise>
		                </c:choose>
                	</table>
                	</td>
                </tr>
                
                <tr>
                    <td colspan="2"><input type="button" value="항목추가" onclick="javascript:addItem(); return false;" /></td>
                </tr>
            </table>
            <br><br>
            <p align="center">
            	<c:choose>
            		<c:when test="${quiz == null or quiz.reqTotal == 0}">
            		<button type="button" class="btn btn-default" onClick="javascript:goSubmit()">저장하기</button>
            		</c:when>
            		<c:otherwise>
            		<button type="button" class="btn btn-default" onClick="javascript:alert('수정할 수 없습니다.');">저장하기</button>
            		</c:otherwise>
            	</c:choose>
                &nbsp;
                <button type="button" class="btn btn-default" onClick="javascript:history.back();">취소</button>
            </p>
        </form>
			
		</div>
	</div>
</div>
</form>
<iframe name="ifrm" width="100%" height="0" frameborder="0"></iframe>			
</body>
</html>