<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.altong.web.logic.util.CommonUtil"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<!-- 상위 헤드 -->
<link rel="stylesheet" href="/pub/member/myQuestion/myQuestion.css?ver=1.1">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.4">

<link rel="stylesheet" href="/pub/css/myPage_lang.css?ver=1.0">
<script src="/Common/js_new/default/languages.js?ver=1.0"></script>

<script>
function translate_que(contentSeq) {
	//번역 유무에 따라서
	//번역이 없는 경우 기계 번역 실행후 결과를 가져와서 tSeq 를 가져온다.
	//번역이 있는 경우 사람번역이 있으면 사람번역의 tSeq 를 가져온다. 
    
    //console.log('contentSeq : ' + contentSeq);
    //alert('서비스 준비중입니다!');
    var contentType = 'Q';
    
    var qt_seq = $('#qt_' + contentSeq).val();
    
    if(qt_seq == '') {
        $.ajax({
			type: 'post',
			url: '/translate/trans',
			data: { 'contentSeq' : contentSeq, 'contentType' : contentType },
			dataType: 'json',
			crossDomain: true,
			success: function (r) {
				//console.log('r.result : ' + r.result);
				switch (r.result) {
					case 'Y':
						var tSeq = r.arr.tSeq;
						var tTitle = r.arr.tTitle;
						var tContents = r.arr.tContents;
						
						//tSeq 설정
						//tTitle 만교체(span id="que_") / qt_
						tTitle = tTitle.replace(/(<([^>]+)>)/ig,"");
						
						$('#que_' + contentSeq).html(tTitle);
						$('#cont_' + contentSeq).html(tContents);
						$('#qt_' + contentSeq).val(tSeq);
						
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
    	//원문 로딩
		$.ajax({
			type: 'post',
			url: '/translate/getQuestionOrgTitle',
			data: { 'contentSeq' : contentSeq},
			dataType: 'json',
			crossDomain: true,
			success: function (r) {
				//console.log('r.result : ' + r.result);
				switch (r.result) {
					case 'Y':
						var tTitle = r.arr.tTitle;
						var tContents = r.arr.tContents;
						
						tTitle = tTitle.replace(/(<([^>]+)>)/ig,"");
						
						$('#que_' + contentSeq).html(tTitle);
						$('#cont_' + contentSeq).html(tContents);
						$('#qt_' + contentSeq).val(''); // 번역문 코드 제거
						
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

function goTranslate_ans(contentSeq) {
	var trn_chk = $('#bAnst_' + contentSeq).val();
	
	var type = "A";	
	
	if(trn_chk == '') {
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
						
						$('#bAnst_' + contentSeq).val(tSeq);
						$('#bAns_' + contentSeq).text(tContents);
						
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
			data: { 'contentSeq' : contentSeq, 'contentType' : type },
			dataType: 'json',
			crossDomain: true,
			success: function (r) {
				//console.log('r.result : ' + r.result);
				switch (r.result) {
					case 'Y':
						var tContents = r.arr.tContents;
						
						tContents = tContents.replace(/(<([^>]+)>)/ig,"");
						
						$('#bAnst_' + contentSeq).val('');
						$('#bAns_' + contentSeq).text(tContents);
						
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

function goAnswerList(seq) {
	var qtSeq = $('#qt_' + seq).val();
	var url = '/answer/answerList?Seq=' + seq + '&CurPageName=/member/myZzim';
	
	if(qtSeq == '') {
		location.href = url;
	}
	else {
		location.href = url + '&qtSeq=' + qtSeq;
	}
}

function goAnswerByAnswerSeq(aSeq, qSeq, section1) {
	var tSeq = $('#bAnst_' + aSeq).val();
	
	var url = "";
	
	if(tSeq == '' || tSeq == '0') {
		url = '/answer/answerView?QuestionSeq=' + qSeq + '&AnswerSeq=' + aSeq + '&CurPageName=/member/myView&Section1=' + section1;
	}
	else {
		url = '/answer/answerView?QuestionSeq=' + qSeq + '&AnswerSeq=' + aSeq + '&tSeq=' + tSeq + '&CurPageName=/member/myView&Section1=' + section1;
	}
	
	location.href = url;
}
</script>
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>

<script>
//전송
function goSubmit_z(FormName, URL, Target) {
    //console.log('before eval : ' + FormName)
    var FormName = eval(FormName);
    //console.log('after eval : ' + FormName)
    FormName.target = Target;
    FormName.action = "" + URL + "";
    FormName.submit();
}
</script>

<div id="atm_myjjim_wrapper0">
	<!--wrapper start -->
	<form name="frm_sch" method="post" onSubmit="return false;">
	<input type="hidden" name="src_Target" value="${curPageName}">
	<input type="hidden" name="pg" value="${n_curpage}">
	
		<div class="atm_mycatebar1">
			<div class="center">
				<div class="atm_mycatebar1_pc">
					<p class="atm_mycatebar_c1"><spring:message code="msg_1178"/> <span class="atm_mycatebar_c2">${n_trec}</span><spring:message code="msg_0370"/></p>
					<div class="atm_mycatebar_box">
                        <span><spring:message code="msg_0895"/></span>
						<select name="Section1" class="atm_mycatebar_sel" onChange="javascript:goSubmit_z('frm_sch','${curPageName}','_self');">
							<option value="0" <c:if test="${section1 == '0'}">selected</c:if>><spring:message code="msg_0161"/></option>
						<c:forEach var="sec" items="${sectionVO}" varStatus="status">
							<c:set var="code" value="${sec.code}"/>
							<%
								String code = String.valueOf( pageContext.getAttribute("code") );
								String codeNm = CommonUtil.getLangMsg(request, "cate" + code);
							%>
							<option value="${sec.code}" <c:if test="${section1 == sec.code}">selected</c:if>><%=codeNm%></option>
						</c:forEach>
						</select>
					</div><!-- atm_mycatebar_box end -->
				</div><!--atm_mycatebar1_pc end -->
			</div><!-- center end -->
		</div><!--atm_mycatebar1 end -->
		
		<div class="atm_myjjim_con">
			<div class="center">
			<c:forEach var="vue" items="${myViewList}" varStatus="status">
				<div class="atm_myjjim_el atm_border atm_myjjim_view" onclick="javascript:goAnswerByAnswerSeq('${vue.answerSeq}','${vue.questionSeq}','${vue.section1}');">
                    <p class="atm_myjjim_c7"><span>${vue.conDate}</span><spring:message code="msg_1179"/></p>
                    <div class="atm_myjjim_c5">
                        <h3 class="atm_myjjim_answer">A.</h3>                       
                        <p><span id="bAns_${vue.answerSeq}">${fn:substring(  fn:trim(vue.answer.replaceAll("<.*?>","") ), 0, 280)}</span></p>
                    </div>
                    <input type="hidden" id="bAnst_${vue.answerSeq}" name="tSeq" value="" /> 
					<span class="atm_myJJim_c33"><img class="answer_lang" src="/Common/images/language.svg" alt="" onclick="goTranslate_ans('${vue.answerSeq}');"></span>
                </div>
			</c:forEach>
			</div>
		</div><!--atm_myjjim_con end -->
		<div class="list_pasing">
		${paging}
		</div>
	</form>
	<iframe name="ifrm" width="100%" height="0" frameborder="0"></iframe>
	<!--wrapper end -->
</div><!--atm_myjjim_wrapper0 end -->

<div id="top_btn">
    <a href="#">
        <span>
            <img src="/Common/images/top_btn_arrow.svg" alt="top_btn">
        </span>
    </a>
</div>
</body>
</html>