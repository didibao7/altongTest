<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<%
	pageContext.setAttribute("crcn", "\r\n"); //Space, Enter
    pageContext.setAttribute("br", "<br/>"); //br 태그
%>
<head>
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/styles/monokai-sublime.min.css" />
<link rel="stylesheet" href="//cdn.quilljs.com/1.3.6/quill.snow.css" />
<link rel="stylesheet" href="/pub/answer/answerWrite/answerWrite.css?ver=1.5">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.2">

<script>
function goTranslate_que() {
	var trn_chk;
	var contentSeq = '${QuestionSeq}';
	//console.log('contentSeq : ' + contentSeq);
	trn_chk = $('#bAnst_' + contentSeq).val();
	//console.log('trn_chk : ' + trn_chk);
	if(trn_chk == '0' || trn_chk == '') {
		$.ajax({
			type: 'post',
			url: '/translate/trans',
			data: { 'contentSeq' : contentSeq, 'contentType' : 'Q' },
			dataType: 'json',
			crossDomain: true,
			success: function (r) {
				//console.log('r.result : ' + r.result);
				switch (r.result) {
					case 'Y':
						var tSeq = r.arr.tSeq;
						var tTitle = r.arr.tTitle;
						var tContents = r.arr.tContents;
						
						$('#bAnst_' + contentSeq).val(tSeq);
						
						$('#qns_info').html('<h3><span>Q.</span>' + tTitle + '</h3><p>' + tContents + '</p>');
						
						break;
					case 'N':
						alert('<%=CommonUtil.getLangMsg(request, "msg_0223")%>');
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
			url: '/translate/getQuestionOrgTitle',
			data: { 'contentSeq' : contentSeq, 'contentType' : 'Q' },
			dataType: 'json',
			crossDomain: true,
			success: function (r) {
				//console.log('r.result : ' + r.result);
				switch (r.result) {
					case 'Y':
						var tContents = r.arr.tContents;
						var tTitle = r.arr.tTitle;
						
						$('#bAnst_' + contentSeq).val('0');
						
						$('#qns_info').html('<h3><span>Q.</span>' + tTitle + '</h3><p>' + tContents + '</p>');
						
						break;
					case 'N':
						alert('<%=CommonUtil.getLangMsg(request, "msg_0223")%>');
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
</script>
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<div id="atm_wrapper">
	<div id="atm_wa_wrapper0">
		<div id="atm_wa_wrapper">
			<div class="center">
				<h1 class="atm_wa_ttbar"><spring:message code="msg_0326"/></h1>
			</div>
			<form name="frm_qa" method="post" onSubmit="return false;" enctype="multipart/form-data">
				<input type="hidden" name="Seq" value="${Seq}">
				<input type="hidden" name="FlagUse" value="">
				<input type="hidden" name="QuestionSeq" value="${QuestionSeq}">
				<input type="hidden" name="CurPageName" value="${CurPageName}">
				<input type="hidden" name="Section1" value="${Section1}">
				<input type="hidden" name="src_Sort" value="${src_Sort}">
				<input type="hidden" name="src_OrderBy" value="${src_OrderBy}">
				<input type="hidden" id="bAnst_${QuestionSeq}" value="0" />
				
				<!-- [추가(2018.02.22): 김현구] 답변 수정에서 질문 보이기 -->
				<div class="atm_wa_con" id="faq1" style="padding: 15px;">
					<div class="atm_faq_tt">
						<img src="/Common/images/icon_question.png" class="atm_icon_qmark">
						<p class="atm_faq_c2"><%=CommonUtil.getLangMsg(request, "msg_0326")%></p><img class="answer_lang" src="/Common/images/language.svg" alt='<spring:message code="msg_0233"/>' onclick="goTranslate_que();">
						<i></i>
					</div>
					<div class="atm_faq_con" style="display: none;">
						<div class="atm_faq_line"></div>
						<div class="atm_faq_slidG">
                			<div id="qns_info">
                 				<h3 class="atm_memagree_c1" id="a"><%=CommonUtil.getLangMsg(request, "msg_0327")%> : ${Title}</h3>
                    			<p>${Contents}</p>
                            </div>
                        </div>
					</div>
				</div>
				
				<div id="scroller" class="textCountWarp" style="position:absolute;margin:-25px -27px">
					<div class="textCount" id="lengthCnt" style="position:absolute;padding:0 3px 0 3px;right:35px;color:#aaa;background-color:#fff;">0/10000</div>
					&nbsp;
				</div>
				<div id="standalone-container">
					<div id="toolbar-container">
						<span class="ql-formats">
						  <select class="ql-size"></select>
						  <button class="ql-bold"></button>
						  <button class="ql-underline"></button>
						  <select class="ql-color"></select>
						  <select class="ql-align"></select>
						  <button class="ql-image"></button>
						 <button id="custom-button">
						 <svg class="MuiSvgIcon-root jss269" focusable="false" viewBox="0 0 24 24" aria-hidden="true" role="presentation" tabindex="-1" title="SettingsBackupRestore"><path d="M14 12c0-1.1-.9-2-2-2s-2 .9-2 2 .9 2 2 2 2-.9 2-2zm-2-9c-4.97 0-9 4.03-9 9H0l4 4 4-4H5c0-3.87 3.13-7 7-7s7 3.13 7 7-3.13 7-7 7c-1.51 0-2.91-.49-4.06-1.3l-1.42 1.44C8.04 20.3 9.94 21 12 21c4.97 0 9-4.03 9-9s-4.03-9-9-9z"></path></svg>
						 </button>
						</span>
					</div>
					<div id="editor-container">${fn:replace(Answer, br, crcn)}</div>
				</div>
				<textarea name="Contents" rows="20" class="atm_wa_input2" style="display:none;">${fn:replace(Answer, br, crcn)}</textarea>
				<div class="atm_wa_addimg">
					<div class="atm_wq_addimg_pc_wrapper">
						<div class="atm_wq_addimg_pc">
							<% int fCnt = 0; %>
							<c:forEach var="file" items="${files}" varStatus="status">
								<c:set var="fSeq" value="${file.seq}"/>
								<%
									String fSeq = pageContext.getAttribute("fSeq").toString();
									String Seq = String.valueOf(request.getAttribute("seq"));
									String CurPageName = String.valueOf(request.getAttribute("CurPageName"));
									String QuestionSeq = String.valueOf(request.getAttribute("QuestionSeq"));
									fCnt++;
								%>
								<button onclick="goSubmit('frm_qa','/default/fileDelete?FileSeq=<%=fSeq%>&ParentName=Answer&ParentSeq=<%=Seq%>&CurPageName=<%=CurPageName%>&QuestionSeq=<%=QuestionSeq%>','edit_ifrm');"><spring:message code="msg_0328"/></button><br><br>
							</c:forEach>
								<input type="hidden" name="FileMax" value="${FileMax}">
								
							<%
								int fileMax = Integer.parseInt( String.valueOf( request.getAttribute("FileMax") ) );
								int writeMax = Integer.parseInt( String.valueOf( request.getAttribute("WriteMax") ) );
								for(int i = 1; i <= (fileMax - fCnt); i++) {
							%>
							<div style="position:relative; margin-bottom:5px;display:none;">
		  						<input type="text" id="atm_fileNamee<%=i%>" class="atm_wq_input3" value="" readonly="readonly" />
				 				<div class="atm_wq_c5">
				  					<input type="button" value=<spring:message code="msg_0329"/> class="atm_wq_c5_button" />  
				  					<input name="File<%=i%>" accept="image/*" type="file" class="atm_wq_c5_hidden" onChange="javascript: document.getElementById('atm_fileNamee<%=i%>').value = this.value" />
				  				</div>
							</div>
							<%
								}
							%>
						</div>
					</div>
				</div>
				<div class="nick_open">
		            <p><spring:message code="msg_0336"/></p>
		            <ul>
			            <li answer="y" class="<c:if test="${FlagNickName == 'N'}">open_agree</c:if>"><spring:message code="msg_0331"/>
				            <input name="FlagNickName" type="radio"  class="frm-hiddenL" style="display:none;" value="N" <c:if test="${FlagNickName == 'N'}">"open_agree"</c:if>/>
			            </li>
			            <li class="<c:if test="${FlagNickName == 'Y' or FlagNickName == ''}">open_agree</c:if>" answer="n"><spring:message code="msg_0332"/>
				            <input name="FlagNickName" type="radio" class="frm-hiddenR" style="display:none;" value="Y" <c:if test="${FlagNickName == 'Y' or FlagNickName == ''}">checked</c:if>/>
			            </li>
		            </ul>
	            </div>
				<div class="atm_wa_btng2">
					<button class="register" onClick="javascript:NewAnswerChk('frm_qa','/answer/answerSave_E', 'Y');"><spring:message code="msg_0333"/></button>
				</div>
			</form>	
		</div>
<script type="text/javascript">
$(document).ready(function(){
	$(".ql-editor").keyup(function(){
		fAnswerCount(this);
	});
});

function fAnswerCount(answerInput) {
	var writeMax = parseInt('${WriteMax}', 10);
	var delta = quill.getContents();
	var lengthSize = quill.getLength();
	var contents = quill.getText(0, lengthSize);
	var html = JSON.stringify(delta, null, 2);
	var AnswerCount = contents.split(" ").join("").split('\n').join("").length;

	if(AnswerCount > writeMax){
		alert('<%=CommonUtil.getLangMsg(request, "msg_0337")%>');
		$(answerInput).html($(answerInput).html().substring(0, writeMax));
	}
	$("#lengthCnt").text(AnswerCount + '/10000');
}

$(document).ready(function(){
	$('.nick_open ul li').click(function(){
		$('.nick_open ul').children().attr('class','');
		$(this).attr('class','open_agree');
		$('.nick_open ul li').find('input[type="radio"]').prop("checked", false);
		$(this).children('input').prop("checked", true);
	});
    $('#atm_wa_wrapper0 > form > div.atm_wa_con > textarea').focus();
	$('div.atm-ui-checkbox1').click(function(){
    var offset = $(this).index();
	var status;
	$('div.atm-ui-checkbox1').each(function(i){
        status = $.trim($(this).attr("class").replace("atm-ui-checkbox1", ""));
		if(i == offset){
			if(status.search("_on") > 0) status = status.replace("_on", "");
			$(this).removeClass(status).addClass(status+'_on');
			$(this).find('input[type="radio"]').prop("checked", true);
		} else {
			$(this).removeClass(status).addClass(status.replace("_on", ""));
			$(this).find('input[type="radio"]').prop("checked", false);
		} // end of else
	});
});
$('div.atm-ui-checkbox2').click(function(){
    var offset = $(this).index();
	var status;
    $('div.atm-ui-checkbox2').each(function(i){
        status = $.trim($(this).attr("class").replace("atm-ui-checkbox2", ""));
		if(i == offset){
			if(status.search("_on") > 0) status = status.replace("_on", "");
			$(this).removeClass(status).addClass(status+'_on');
			$(this).find('input[type="radio"]').prop("checked", true);
		} else {
			$(this).removeClass(status).addClass(status.replace("_on", ""));
			$(this).find('input[type="radio"]').prop("checked", false);
		} // end of else
	});
});
});

function NewAnswerChk(FormName, URL, StrFlag) {
	if (document.isAnswered) return;

    var FormName = eval(FormName);
	var delta = quill.getContents();
	var myEditor = document.querySelector('#editor-container');
	var html = myEditor.children[0].innerHTML;	
	var lengthSize = quill.getLength();
	var contents = quill.getText(0, lengthSize);
	var AnswerCount = contents.split(" ").join("").split('\n').join("").length;

	const text = delta.filter((op) => typeof op.insert === 'string').map((op) => op.insert).join('');

	FormName.Contents.value = html;

    if (AnswerCount == 0) {
        alert('<%=CommonUtil.getLangMsg(request, "msg_0335")%>');
        $("#editor-container").focus();
        return false;
    }
    else if (contents.split(" ").join("") == "") {
        alert(getLangStr("jsm_0038"));
        $("#editor-container").focus();
        return false;
    }
    else if (AnswerCount < 100) {
        alert('<%=CommonUtil.getLangMsg(request, "msg_0343")%>');
        $("#editor-container").focus();
        return false;
    }

	document.isAnswered = true;

	FormName.FlagUse.value = StrFlag;
    FormName.target = "edit_ifrm";
    FormName.action = "" + URL + "";
    FormName.submit();
}
</script>	
	</div>
</div>

<iframe name="edit_ifrm" width="100%" height="0" frameborder="0"></iframe>

<script src="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/highlight.min.js"></script>
<script src="//cdn.quilljs.com/1.3.6/quill.min.js"></script>
<script style="text/javascript">
var quill = new Quill('#editor-container', {
	modules: {
		syntax: true,
		toolbar: '#toolbar-container',
		history: {
		  delay: 2000,
		  maxStack: 500,
		  userOnly: true
		}
	},
	placeholder:'<%=CommonUtil.getLangMsg(request, "msg_0338")%>'
	, theme: 'snow'
});

var customButton = document.querySelector('#custom-button');
customButton.addEventListener('click', function() {
	quill.history.undo();
});

</script>
    
</body>