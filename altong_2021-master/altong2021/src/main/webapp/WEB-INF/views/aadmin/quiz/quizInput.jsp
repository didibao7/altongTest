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
	var items = document.getElementsByName("correct");
	
	if(items.length < 5) {
		var html = "<tr>";
		html += "<td width=\"273\">";
		html += '<input type="radio" name="correct" value="1" class="input_textarea" maxlength="20" style="width:200px;">';
		html += "</td>";
		html += "<td>";
		html += '<input type="hidden" name="ano[]" value="1" /><input type="text" name="answer[]" value="" class="input_textarea" maxlength="20" style="width:100%;">';
		html += "</td>";
		html += "<td>";
		html += '<input type="button" value=" X " onclick="delItem($(this))">';
		html += "</td>";
		html += "</tr>";
		
		$('#ansItem').append(html);
		reGenerator();
	}
	else {
		alert('추가할 수 없습니다. 최대 답변갯수는 다섯개 입니다.');
	}
	
	return false;
}

function delItem(obj) {
	var items = document.getElementsByName("correct");
	
	if(items.length > 2) { 
		obj.parent().parent().remove();
		reGenerator();
	}
	else {
		alert('삭제할 수 없습니다. 최소 답변갯수는 2 입니다.');
	}
	return false;
}


function reGenerator() {
	var items = document.getElementsByName("correct");
	
	for(var i = 0; i < items.length; i++) {
		document.getElementsByName("correct")[i].value = (i + 1);
		document.getElementsByName("ano[]")[i].value = (i + 1);
	}
}

function goSubmit() {
	var rtime1 = document.getElementsByName("rtime1")[0].value;
	var rtime2 = document.getElementsByName("rtime2")[0].value;
	
	var stime1 = document.getElementsByName("stime1")[0].value;
	var stime1 = document.getElementsByName("stime2")[0].value;
	
	var quest = document.getElementsByName("quest")[0].value;
	
	var ansErrCnt = 0;
	
	var items = document.getElementsByName("correct");
	var chkCnt = 0;
	for(var i = 0; i < items.length; i++) {
		if(document.getElementsByName("answer[]")[i].value == '') { ansErrCnt++; }
		if(document.getElementsByName("correct")[i].checked == true) { chkCnt++; }
	}
	
	if(rtime1 == '' && rtime2 == '') {
		alert('[문제출제시간]\n항목의 내용을 정확하게 기재해주세요.');
		
		if(rtime1 == ''){
			document.getElementsByName("rtime1")[0].focus();
		}
		else if(rtime2 == '') {
			document.getElementsByName("rtime2")[0].focus();
		}
		
		return false;
	}
	if(stime1 == '' && stime2 == '') {
		alert('[결과노출시간]\n항목의 내용을 정확하게 기재해주세요.');
		
		if(stime1 == ''){
			document.getElementsByName("stime1")[0].focus();
		}
		else if(stime2 == '') {
			document.getElementsByName("stime2")[0].focus();
		}
		
		return false;
	}
	if(quest == '') {
		alert('[질문]\n항목의 내용을 정확하게 기재해주세요.');
		document.getElementsByName("quest")[0].focus();
		return false;
	}
	
	if(chkCnt == 0) {
		alert('정답을 선택하여 주세요.');
		$('#ansItem').focus();
		return false;
	}
	
	if(ansErrCnt > 0) {
		alert('[답변]\n항목의 내용을 정확하게 기재해주세요.');
		$('#ansItem').focus();
		return false;
	}
	
	$('#frm_quiz').attr('action', '/aadmin/quiz/quizQueSave');
	$('#frm_quiz').submit();
}

function checkTimeNum(obj) {
    function isNumeric(str) {
        for (i = 0; i < str.length; i++)
            if (str.charAt(i) < '0' || str.charAt(i) > '9') return false;
        return true;
    }

    function excluChar(str) {
        var val = "";
        for (i = 0; i < str.length; i++)
            if (str.charAt(i) >= '0' && str.charAt(i) <= '9') val += str.charAt(i);
        return val;
    }

    var val = obj.value;

    if (!isNumeric(val)) {
        alert("숫자만 입력 가능합니다.");
        obj.value = excluChar(val);
        obj.focus();
        return false;
    }
    if(val > 59) {
		obj.value = 59;
	}
    return true;
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
					<a href="/aadmin/quiz/quizGameList">[퀴즈게임 리스트]</a>
				</div>
				<div class="title-name col-md-4">
					<a href="/aadmin/quiz/quizList"></a>퀴즈 리스트
				</div>
		</div>
		<br>
		
		<div id="list">
			<form id="frm_quiz" name="frm_sch" method="post">

            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="rowboardList_top">
				<tr>
                	<td colspan="2" height="50"><b><font size="4">퀴즈 등록</font></td>
              	</tr>
			    <tr>
			        <td colspan="2" BORDER="0">&nbsp;</td>
			    </tr>
			    
              	<tr>
                  <td bgcolor="#efefef">출제가능여부</td>
                  <td>
                  <input type="radio" name="useChk" value="Y" class="input_textarea" maxlength="20" style="width:200px;" <c:if test="${quiz.useChk == 'Y'}">checked</c:if>> 가능  
                  <input type="radio" name="useChk" value="N" class="input_textarea" maxlength="20" style="width:200px;" <c:if test="${quiz == null or quiz.useChk == 'N'}">checked</c:if>> 불가능
                  </td>
              	</tr>
              	<tr>
                  <td bgcolor="#efefef">문제코드</td>
                  <td><input type="text" name="uid" value="${uid}" class="input_textarea" maxlength="20" style="width:200px;" readonly></td>
              	</tr>
              	<tr>
                  <td bgcolor="#efefef">문제출제시간</td>
                  <td><input type="text" name="rtime1" value="${quiz.rtime1}" class="input_textarea" maxlength="2" style="width:200px;" onkeyup="checkTimeNum(this);">분  <input type="text" name="rtime2" value="${quiz.rtime2}" class="input_textarea" maxlength="2" style="width:200px;" onkeyup="checkTimeNum(this);">초</td>
              	</tr>
              	<tr>
                  <td bgcolor="#efefef">결과노출시간</td>
                  <td><input type="text" name="stime1" value="${quiz.stime1}" class="input_textarea" maxlength="2" style="width:200px;" onkeyup="checkTimeNum(this);">분  <input type="text" name="stime2" value="${quiz.stime2 != null ? quiz.stime2 : '10' }" class="input_textarea" maxlength="2" style="width:200px;" onkeyup="checkTimeNum(this);">초</td>
              	</tr>
				
				<tr>
                    <td bgcolor="#efefef">질 문</td>
                    <td><textarea name="quest" style="width:100%;height:200px"><c:if test="${quiz != null}">${fn:replace(quiz.quest, br, crcn)}</c:if></textarea></td>
                </tr>
				
				<!-- 정답체크 -->
                <!-- 답변입력 -->
                <!-- 답변삭제-논리적 삭제 -->
                <!-- 항목추가 버튼 -->
                <tr>
                    <td bgcolor="#efefef" style="text-align: center;">정 답</td>
                    <td bgcolor="#efefef">답 변</td>
                </tr>
                <tr>
                	<td colspan="2">
                	<table id="ansItem" style="width:100%;">
                		 <c:choose>
		                	<c:when test="${ansList == null}">
		                	<tr>
			                    <td width="273"><input type="radio" name="correct" value="1" class="input_textarea" maxlength="1" style="width:200px;" <c:if test="${quiz.correct == null or quiz.correct == '1'}">checked</c:if>></td>
			                    <td><input type="hidden" name="ano[]" value="1" /><input type="text" name="answer[]" value="" class="input_textarea" maxlength="20" style="width:100%;"></td>
			                    <td><input type="button" value=" X " onclick="delItem($(this))"></td>
			                </tr>
			                <tr>
			                    <td width="273"><input type="radio" name="correct" value="2" class="input_textarea" maxlength="1" style="width:200px;" <c:if test="${quiz.correct == '2'}">checked</c:if></td>
			                    <td><input type="hidden" name="ano[]" value="2" /><input type="text" name="answer[]" value="" class="input_textarea" maxlength="20" style="width:100%;"></td>
			                    <td><input type="button" value=" X " onclick="delItem($(this))"></td>
			                </tr>
		                	</c:when>
		                	<c:otherwise>
		                	<c:forEach var="ans" items="${ansList}" varStatus="status">
		                		<tr>
				                    <td width="273"><input type="radio" name="correct" value="${ans.ano}" class="input_textarea" maxlength="1" style="width:200px;" <c:if test="${quiz.correct == ans.ano}">checked</c:if>></td>
				                    <td><input type="hidden" name="ano[]" value="${ans.ano}" /><input type="text" name="answer[]" value="${ans.answer}" class="input_textarea" maxlength="20" style="width:100%;"></td>
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
                
                <tr>
                    <td bgcolor="#efefef">Hint</td>
                    <td><textarea name="hint" style="width:100%;height:200px"><c:if test="${quiz != null}">${fn:replace(quiz.hint, br, crcn)}</c:if></textarea></td>
                </tr>
				<tr>
                    <td bgcolor="#efefef">해 설</td>
                    <td><textarea name="comment" style="width:100%;height:200px"><c:if test="${quiz != null}">${fn:replace(quiz.comment, br, crcn)}</c:if></textarea></td>
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