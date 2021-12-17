<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<%
	pageContext.setAttribute("crcn", "\r\n"); //Space, Enter
    pageContext.setAttribute("br", "<br>"); //br 태그
%>
<head>
<link rel="stylesheet" href="/pub/member/myQuestion/myQuestion.css?ver=1.1">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.4">

<link rel="stylesheet" href="/pub/css/myPage_lang.css?ver=1.0">
<script src="/Common/js_new/default/languages.js?ver=1.0"></script>
<script>
function goTranslate_ans(contentSeq, contentType) {
	var trn_chk = $('#bAnst_' + contentSeq).val();
	
	type = "A";	
	
	if(trn_chk == '0' || trn_chk == '') {
		$.ajax({
			type: 'post',
			url: '/translate/trans',
			data: { 'contentSeq' : contentSeq, 'contentType' : type },
			dataType: 'json',
			crossDomain: true,
			success: function (r) {
				//console.log('r.result : ' + r.result);
				switch (r.result) {
					case 'Y':
						var tSeq = r.arr.tSeq;
						var tContents = r.arr.tContents;
						
						tContents = tContents.replace(/(<([^>]+)>)/ig,"");
						
						//$('#txt_length_' + contentSeq).text(tContents.length);
						
						/*if(tContents.length > 60) {
							tContents = tContents.substring(0, 60) + '...';
						}*/
						
						$('#bAnst_' + contentSeq).val(tSeq);
						$('#bAns_' + contentSeq).html(tContents);
						
						break;
					case 'N':
						alert(getLangStr("jsm_0083"));
						return false;
						break;
					default:
						break;
				}
			},
			error: function (r, textStatus, err) {
				console.log(r);
			},
			complete: function () {
				document.xhr = false;
			}
		});
	}
	else {
		$.ajax({
			type: 'post',
			url: '/translate/getAnsOrgTitle',
			data: { 'contentSeq' : contentSeq, 'contentType' : contentType },
			dataType: 'json',
			crossDomain: true,
			success: function (r) {
				//console.log('r.result : ' + r.result);
				switch (r.result) {
					case 'Y':
						var tContents = r.arr.tContents;
						
						tContents = tContents.replace(/(<([^>]+)>)/ig,"");
						
						/*if(tContents.length > 60) {
							tContents = tContents.substring(0, 60) + '...';
						}*/
						
						$('#bAnst_' + contentSeq).val('0');
						$('#bAns_' + contentSeq).html(tContents);
						
						break;
					case 'N':
						alert(getLangStr("jsm_0083"));
						return false;
						break;
					default:
						break;
				}
			},
			error: function (r, textStatus, err) {
				console.log(r);
			},
			complete: function () {
				document.xhr = false;
			}
		});
	}
}

function goAnswerList(seq, aSeq) {
	var tSeq = $('#bAnst_' + aSeq).val();
	
	var url = "";
	if(tSeq == '' || tSeq == '0') {
		url = '/answer/answerList?Seq=' + seq + '&ASeq=' + aSeq + '&CurPageName=/member/myReply#' + aSeq;
	}
	else {
		url = '/answer/answerList?Seq=' + seq + '&ASeq=' + aSeq + '&tSeq=' + tSeq + '&CurPageName=/member/myReply#' + aSeq;
	}
	
	location.href = url;
}
</script>
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<div id="atm_myjjim_wrapper0">
	<form name="recived_frm_sch" method="post" onSubmit="return false;">
		<input type="hidden" name="src_Target" value="${CurPageName}">
		<input type="hidden" name="pg" value="${n_curpage}">
		<div class="atm_mycatebar1">
			<div class="center">
				<div class="atm_mycatebar1_pc">
					<p class="atm_mycatebar_c1">
						<c:choose>
							<c:when test="${viewFlag eq 'choice'}">
							<spring:message code="msg_0826"/>
							</c:when>
							<c:otherwise>
							<spring:message code="msg_0825"/>
							</c:otherwise>
						</c:choose>
						<span class="atm_mycatebar_c2">${n_totalCnt}</span><spring:message code="msg_0370"/>
					</p>
				</div>
			</div>
		</div>
		<div class="atm_myjjim_con">
			<div class="center">
			<c:forEach var="item" items="${answerAlarmList}" varStatus="status">
				<div class="atm_myjjim_el atm_border">
					<div class="atm_myjjim_c5" onclick="javascript:goAnswerList('${item.questionSeq}','${item.answerSeq}');">
						<h3 class="atm_myjjim_answer">A.</h3>
						<p>${item.nickName}<spring:message code="msg_0271"/>
						<span class="atm_myjjim_answer_q">
							<strong>Q.</strong>
							${item.title}
						</span>
						</p>
					</div><!-- atm_myjjim_c5 end -->
					<div class="beefup_head" id="bAns_${item.answerSeq}" onclick="javascript:goAnswerList('${item.questionSeq}','${item.answerSeq}');">${item.answer}</div>
					<ul class="atm_myjjim_c7">
						<li>${item.dateReg}</li>
					</ul>
					<input type="hidden" id="bAnst_${item.answerSeq}" value="0" /> 
					<span class="atm_myJJim_c33"><img class="answer_lang" src="/Common/images/language.svg" alt="" onclick="goTranslate_ans('${item.answerSeq}', 'B');"></span>
				</div><!-- atm_myjjim_el atm_border end -->
			</c:forEach>
			</div>
		</div><!-- atm_myjjim_con end -->
		
		<div class="list_pasing">
			${paging}
		</div>
	</form>
	<iframe name="myRecived_ifrm" width="100%" height="0" frameborder="0"></iframe>
</div>
</body>