<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<%
	pageContext.setAttribute("crcn", "\r\n"); //Space, Enter
    pageContext.setAttribute("br", "<br/>"); //br 태그
    
    String Title = request.getAttribute("title").toString();
    
    Title = CommonUtil.htmlSpecialChars(Title);
    
    String SP = String.valueOf(request.getAttribute("SP"));
    int nSP = 0;
    if(SP != "") { nSP = Integer.parseInt(SP); }
    String Seq = String.valueOf(request.getAttribute("Seq"));
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/styles/monokai-sublime.min.css" />
<link rel="stylesheet" href="//cdn.quilljs.com/1.3.6/quill.snow.css" />
<link rel="stylesheet" href="/Common/src/question/questionWrite/questionWrite.css" />
</head>
<body>
<style type="text/css">
<% if (nSP == 1) { %>
.atm_wq_ttbar {background-color:#0E3567;}
.atm_wq_input2 {border:solid 1px #0E3567;}
.atm_btn_gre {background-color: #0E3567;}
.atm_checkbtn_c2_on {color:#0E3567;border:solid 2px #0E3567;}
.atm_checkbtn_c3_on {color:#0E3567;border:solid 2px #0E3567;}
<% } %>
</style>

<script>
var flagContents = false;
$(function() {
	$("#amountSelector").change(function() {
		$("#amount").val($("#amountSelector option:selected").val())
	})
});
function syncContents() {
	if(!flagContents) {
		$('input[name=Contents]').val($('input[name=Title]').val());
	}
}
</script>
<%@ include file="/pub/menu/topMenu.jsp"%>
<div id="atm_wrapper" style="overflow-x:hidden;">
	<div id="atm_wa_wrapper">
		<div class="center">
			<h1 class="atm_wa_ttbar"><spring:message code="msg_0938"/></h1>
			<div id="atm_wq_wrapper0">
				<div class="atm_wq_ttbar">
					<div class="atm_wq_ttbar_pc">
						<a href="javascript:history.back();"><img src="/Common/images/btn_back_bold.png" class="atm_wq_btn_L1"/></a>
						<a href="javascript: fGoTSave('ACT=TQ_SAVE')"><img src="/Common/images/btn_save.png" class="atm_wq_btn_R1"/></a>
						<div class="clear_both"></div>
					</div>
				</div>
				<form name="frm_qa" method="post" onSubmit="return false;" enctype="multipart/form-data">
				<input type="hidden" name="Seq" value="<%=Seq%>">
				<input type="hidden" name="FlagUse" value="">
				<div class="atm_wq_con">
					<input type="text" name="Title" placeholder='<spring:message code="msg_0327"/>' class="atm_wq_input1" maxlength="50" value="<%=Title%>" autocomplete="off" onkeyup="syncContents();if($(this).val().length >= $(this).attr('maxlength')){alert(getLangStr("jsm_0112"));$(this).val($(this).val().substring(0, $(this).attr('maxlength')));}"/>
					<div style="height:10px;"></div>
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
						<div id="editor-container">${fn:replace(contents, br, crcn)}</div>
					</div>
					<input type="hidden" name="Contents" id="Contents" value="${fn:replace(contents, br, crcn)}">
					<div style="height:10px;"></div>
					<div class="atm_wq_addimg" style="display:none;">
						<p class="atm_wq_c16">Attach files</p>
						<div class="atm_wq_addimg_pc_wrapper">
							<div class="atm_wq_addimg_pc">
								<%
									String FlagNickName = String.valueOf(request.getAttribute("flagNickName"));
									String FlagMinor = String.valueOf(request.getAttribute("flagMinor"));
									int uploadedFileN = 0;
								%>
								<c:forEach var="file" items="${files}" varStatus="status">
									<c:set var="FileSeq" value="${file.seq}"/>
									<%
										String FileSeq = pageContext.getAttribute("FileSeq").toString();
										uploadedFileN++;
									%>
									${file.fileName}&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="frm_qa.FileDelYn.value='Y'; goSubmit('frm_qa','/default/fileDelete?FileSeq=<%=FileSeq%>&ParentName=Question&ParentSeq=<%=Seq%>','ifrm');">삭제</button>
			        				<br><br>
								</c:forEach>
								<input type="hidden" name="FileMax" value="${fileMax}">
								<input type="hidden" name="FileDelYn" value="">
								<%
									int fileMax = Integer.parseInt( String.valueOf( request.getAttribute("fileMax") ) );
									int writeMax = Integer.parseInt( String.valueOf( request.getAttribute("writeMax") ) );
									for(int i = 1; i <= (fileMax - uploadedFileN); i++) {
								%>
								<div style="position:relative; margin-bottom:5px">
									<input type="text" id="atm_fileNamee<%=i%>" class="atm_wq_input3" value="" readonly="readonly" />
									<div class="atm_wq_c5">
										<input type="button" value="Search class="atm_wq_c5_button" />  
										<input name="File<%=i%>" accept="image/*" type="file" class="atm_wq_c5_hidden" onChange="javascript: document.getElementById('atm_fileNamee<%=i%>').value = this.value" />
									</div>
								</div>
								<%
									}
								%>
							</div><!-- atm_wq_addimg_pc end -->
						</div><!-- atm_wq_addimg_pc_wrapper end -->
					</div><!-- atm_wq_addimg end -->
					<div class="atm_wq_G1">
						<div class="atm_wq_bet">
							<p class="atm_wq_c16"><spring:message code="msg_0722"/></p>
							<div class="thank_almoney">
								<select name="Section2" class="atm_wq_input5" id="amountSelector">
									<option value=""><spring:message code="msg_0118"/></option>
									<%
										String questionBet = request.getAttribute("questionBet").toString();
									%>
									<c:forEach var="code" items="${CODE_ANSWER_ALMONEY}" varStatus="status">
										<c:set var="v" value="${code}"/>
										<%
											String code = pageContext.getAttribute("v").toString();
										%>
										<option value="<%=code%>" <%=(code.equals(questionBet) && Seq != null) ? " selected" : ""%>><fmt:formatNumber value="<%=code%>" type="number" /><spring:message code="msg_0136"/></option>
									</c:forEach>
								</select>
							</div>
						</div><!-- atm_wq_bet end -->
					</div><!-- atm_wq_G1 end -->
					<div class="atm_wq_G3">
						<div class="atm_wq_opt nick_open">
							<p class="atm_wq_c8"><spring:message code="msg_0336"/></p>
							<ul>
								<li><spring:message code="msg_0331"/><input name="FlagNickName" type="radio" class="frm-hiddenL" style="display:none" value="N" <%= FlagNickName.equals("N") ? " checked":"" %>/></li>
								<li class="open_agree"><spring:message code="msg_0332"/><input name="FlagNickName" type="radio" class="frm-hiddenR" style="display:none" value="Y" <%= FlagNickName.equals("Y") || FlagNickName.equals("") ? " checked":"" %>/></li>
							</ul>
					    </div>
						<div class="atm_wq_opt adult_open">
					    	<p class="atm_wq_c8"><spring:message code="msg_0939"/></p>
					    	<ul>
					    		<li><spring:message code="msg_0331"/><input name="FlagMinor" type="radio" style="display:none" class="frm-hiddenL" value="N" <%= FlagMinor.equals("N") ? " checked":"" %>/></li>
					    		<li class="open_agree"><spring:message code="msg_0332"/><input name="FlagMinor" style="display:none" type="radio" class="frm-hiddenR" value="Y" <%= FlagMinor.equals("Y") || FlagMinor.equals("") ? " checked":"" %>/></li>
					    	</ul>
					    </div>
				    </div><!-- atm_wq_G3 end -->
				</div><!-- atm_wq_con end -->
				
				<div class="atm_wq_btng2">
					<button class="atm_btn_gre register"><spring:message code="msg_0256"/></button>
				</div>
				<input name="EditFlag" type="hidden" value="${seq}">
				<input name="Orin_Almoney" type="hidden" value="${questionBet}">
				</form>
<script type="text/javascript">
$(document).ready(function(){
	$(".ql-editor").keyup(function(){
		fAnswerCount(this);
	});
});

function fAnswerCount(answerInput) {
	var delta = quill.getContents();
	var lengthSize = quill.getLength();
	var contents = quill.getText(0, lengthSize);
	var html = JSON.stringify(delta, null, 2);
	var AnswerCount = contents.split(" ").join("").split('\n').join("").length;

	if(AnswerCount > <%=writeMax%>){
		alert(getLangStr("jsm_0112"));
		$(answerInput).html($(answerInput).html().substring(0, <%=writeMax%>));
	}
}

function NewQuestionChk(FormName, URL, StrFlag) {
    var FormName = eval(FormName);
	var delta = quill.getContents();
	var myEditor = document.querySelector('#editor-container');
	var html = myEditor.children[0].innerHTML;	
	var lengthSize = quill.getLength();
	var contents = quill.getText(0, lengthSize);
	var QuestionCount = contents.split(" ").join("").split('\n').join("").length;

	const text = delta.filter((op) => typeof op.insert === 'string').map((op) => op.insert).join('');

	FormName.Contents.value = html;

    if (FormName.Title.value == "") {
        alert(getLangStr("jsm_0044"));
        FormName.Title.focus();
        return false;
    }
    else if (FormName.Title.value.split(" ").join("") == "") {
        alert(getLangStr("jsm_0038"));
        FormName.Title.focus();
        return false;
    }
    else if (FormName.Contents.value == "") {
        alert(getLangStr("jsm_0045"));
        FormName.Contents.focus();
        return false;
    }
    else if (contents.split(" ").join("") == "") {
        alert(getLangStr("jsm_0038"));
        FormName.Contents.focus();
        return false;
    }
    else if (FormName.Title.value.split(" ").join("").length < 1 && StrFlag !== 'T') {
        alert(getLangStr("jsm_0046"));
        FormName.Contents.focus();
        return false;
    }
    else if (QuestionCount < 1 && StrFlag !== 'T') {
        alert(getLangStr("jsm_0047"));
        FormName.Contents.focus();
        return false;
    }
    else if (FormName.Section2.value == "" && StrFlag !== 'T') {
        alert(getLangStr("jsm_0048"));
        FormName.Section2.focus();
        return false;
    }
    else
        FormName.FlagUse.value = StrFlag;
        FormName.target = "ifrm";
        FormName.action = "" + URL + "";
        FormName.submit();
}


$(document).ready(function(){
	$('.atm_btn_gre').attr("value", 0)
	$('.atm_btn_gre').click(function() {
		console.log($(this).attr("value"))
		if($(this).attr("value") !== 2) {
			NewQuestionChk('frm_qa','/question/questionSaveMng?SP=<%=SP%>', 'Y');
			//$(this).attr("value", 1);
		}
		else {
			alert(getLangStr("jsm_0118"))
		}
	});
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

<% if(Seq != "") { %>
	function fGoTSave(param)
	{
		alert(getLangStr("jsm_0119"));
	}
<% } else { %>
	function fGoTSave(param)
	{
		if ($('form[name=frm_qa]')[0].Title.value || $('form[name=frm_qa]')[0].Contents.value)
			fAjax('/common/tempSave', 'frm_qa', param);
	}

	function fAjax(url, frm, param) {
		if (document.xhr) {
			$('#Tip').text(getLangStr("jsm_0031")).css('display', 'block');
			setTimeout(function () { $('#Tip').stop().fadeOut(300) }, 3000);
			return;
		}
		var myEditor = document.querySelector('#editor-container');
		var html = myEditor.children[0].innerHTML;	

		$('form[name='+frm+']')[0].Contents.value = html;
		document.xhr = $.ajax({
			type: 'post',
			url: url,
			data: (frm ? $('form[name=' + frm).serialize() + '&' : '') + param,
			dataType: 'json',
			crossDomain: true,
			success: function (r) {
				switch (r.result) {
					case 'TQ_LOAD':
						//console.log(r.arr[0]);
						if (r.arr[0] && r.arr[0].Title) $('form[name='+frm+']')[0].Title.value = r.arr[0].Title;
						if (r.arr[0] && r.arr[0].Contents)
						{
							$('form[name='+frm+']')[0].Contents.value = r.arr[0].Contents;
							myEditor.children[0].innerHTML =  r.arr[0].Contents;
							flagContents = true;
						}
						break;
					default:
						if (r.result) alert(r.result);
						break;
				}
			},
			error: function (r, textStatus, err) {
				if (r.responseText && r.responseText.substr(0, 13) == '<!--LOGOFF-->') { top.location = '/'; return; }
				console.log(r);
			},
			complete: function () {
				document.xhr = false;
			}
		});
	}
	$(document).ready(function(){
		fAjax('/common/tempSave', 'frm_qa', 'ACT=TQ_LOAD');
	});
	$(window).on('beforeunload', function(){if (!document.isSaved) fGoTSave('ACT=TQ_SAVE&isAUTO=Y')});
	setInterval(function(){fGoTSave('ACT=TQ_SAVE&isAUTO=Y')}, 60000);
<% } %>
</script>

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
	placeholder:getLangStr("jsm_0120")
	, theme: 'snow'
});

var customButton = document.querySelector('#custom-button');
customButton.addEventListener('click', function() {
	quill.history.undo();
});

</script>
			</div><!-- atm_wq_wrapper0 end -->
		</div><!-- center end -->
	</div><!-- atm_wa_wrapper end -->
</div><!-- atm_wrapper end -->
<iframe name="ifrm" width="100%" height="0" frameborder="0"></iframe>
</body>
</html>