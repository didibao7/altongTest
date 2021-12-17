<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.altong.web.logic.answer.AnswerVO" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<%@ page import="com.altong.web.logic.util.CommonUtil"%>
<%
	String Seq = String.valueOf(request.getAttribute("seq"));
	String CurPageName = "";
	if(request.getAttribute("curPageName") != null && request.getAttribute("curPageName") != "") {
		CurPageName = String.valueOf(request.getAttribute("curPageName"));
	}
	String QuestionSeq = "";
	if(request.getAttribute("questionSeq") != null && request.getAttribute("questionSeq") != "") {
		QuestionSeq = String.valueOf(request.getAttribute("questionSeq"));
	}
	String EEvent = "";
	if(request.getAttribute("eEvent") != null && request.getAttribute("eEvent") != "") {
		EEvent = String.valueOf(request.getAttribute("eEvent"));
	}
%>
<head>
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/styles/monokai-sublime.min.css" />    
<link rel="stylesheet" href="//cdn.quilljs.com/1.3.6/quill.snow.css" />
<link rel="stylesheet" href="/pub/answer/answerWrite/answerWrite.css?ver=1.5">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.2">

<script type="text/javascript" src="/Common/js_new/default/languages.js?ver=1.7"></script>   

<script>
function goTranslate_que() {
	var trn_chk;
	var contentSeq = '${questionSeq}';
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
<div id="atm_wrapper" style="overflow-x:hidden;">
    <div id="atm_wa_wrapper0">
        <div id="atm_wa_wrapper">
            <div class="center">
                <h1 class="atm_wa_ttbar"><spring:message code="msg_0353"/></h1>
                <form name="frm_qa" method="post" onSubmit="return false;" enctype="multipart/form-data">
                    <input type="hidden" name="Seq" value="${seq}">
                    <input type="hidden" name="FlagUse" value="">
                    <input type="hidden" name="QuestionSeq" value="${questionSeq}">
                    <input type="hidden" name="CurPageName" value="${curPageName}">
                    <input type="hidden" name="Section1" value="${section1}">
                    <input type="hidden" name="src_Sort" value="${src_Sort}">
                    <input type="hidden" name="src_OrderBy" value="${src_OrderBy}">
                    <input type="hidden" id="bAnst_${questionSeq}" value="0" />
                    <!-- 김주윤 미완 2021-01-05 -->
                	<div class="atm_wa_ttbar">
                		<div class="atm_wa_ttbar_pc" style="display:none;">
                			<a href="javascript:history.back();"><img src="/Common/images/btn_back_bold.png" class="atm_wa_btn_L1"/></a>
                			<a href="javascript: fGoTSave('ACT=TA_SAVE&QuestionSeq=<%=request.getAttribute("questionSeq")%>')"><img src="/Common/images/btn_save.png" class="atm_wa_btn_R2"/></a>
                			<div class="clear_both"></div>
                		</div>
                	</div>
                	<%
                        String qusNickName = "";
                        String qusFlagNickName = "";
		                    //answerInfo
		                AnswerVO answerInfo = (AnswerVO)request.getAttribute("answerInfo");
		
                		qusNickName = answerInfo.getNickName();
                        qusFlagNickName = answerInfo.getFlagNickName();
                        if(qusNickName.equals("")) {
                            qusNickName = CommonUtil.getLangMsg(request, "msg_0237");
                        }
                        if(qusFlagNickName.equals("N")) {
                            qusNickName = CommonUtil.getLangMsg(request, "msg_0236");
                        }
                        if(qusNickName != CommonUtil.getLangMsg(request, "msg_0149")) {
                            qusNickName = qusNickName + CommonUtil.getLangMsg(request, "msg_0354");
                        }
                    %>
	                <div class="atm_wa_con" id="faq1">
		                <div class="atm_faq_tt">
			                <img src="/Common/images/que_ico.png" class="atm_faq_up">
			                <p class="atm_faq_c2"><%=qusNickName%><spring:message code="msg_0355"/></p>
			                <c:if test="${answerInfo.lang != sourceLang}">
			                <img class="answer_lang" src="/Common/images/language.svg" alt='<spring:message code="msg_0233"/>' onclick="goTranslate_que();">
			                </c:if>
	                		<i></i>
                		</div>
            		    <div class="atm_faq_con" style="display: none;">
			                <div class="atm_faq_line"></div>
			                <div class="atm_faq_slidG">
				                <div id="qns_info">
	                    			<h3><span>Q.</span>${answerInfo.title}</h3>
				                    <p>${answerInfo.contents}</p>
                                </div>
                            </div>
                        </div>
	                </div>
	                <div class="atm_wa_con" style="border: 1px solid #fff;">
						<select name="lang" id="joinLang">
						  	<option value="" <c:if test="${sourceLang == ''}">selected="selected"</c:if>>-- select one --</option>
							<option value="ko" <c:if test="${sourceLang == 'ko'}">selected="selected"</c:if>>한국어</option>					
							<option value="en" <c:if test="${sourceLang == 'en'}">selected="selected"</c:if>>English</option>
							<option value="es" <c:if test="${sourceLang == 'es'}">selected="selected"</c:if>>Español</option>
							<option value="fr" <c:if test="${sourceLang == 'fr'}">selected="selected"</c:if>>Français</option>
							<option value="pt" <c:if test="${sourceLang == 'pt'}">selected="selected"</c:if>>Português</option>
							<option value="de" <c:if test="${sourceLang == 'de'}">selected="selected"</c:if>>Deutsch</option>
							<option value="ar" <c:if test="${sourceLang == 'ar'}">selected="selected"</c:if>>العربية</option>
							<option value="fa" <c:if test="${sourceLang == 'fa'}">selected="selected"</c:if>>فارسی</option>
							<option value="ru" <c:if test="${sourceLang == 'ru'}">selected="selected"</c:if>>Русский</option>
							<option value="ja" <c:if test="${sourceLang == 'ja'}">selected="selected"</c:if>>日本語</option>
							<option value="it" <c:if test="${sourceLang == 'it'}">selected="selected"</c:if>>Italiano</option>
							<option value="zh" <c:if test="${sourceLang == 'zh'}">selected="selected"</c:if>>中文</option>
							<option value="vi" <c:if test="${sourceLang == 'vi'}">selected="selected"</c:if>>Tiếng Việt</option>
							<option value="hi" <c:if test="${sourceLang == 'hi'}">selected="selected"</c:if>>हिन्दी</option>
							<option value="bn" <c:if test="${sourceLang == 'bn'}">selected="selected"</c:if>>বাংলা</option>
							<option value="id" <c:if test="${sourceLang == 'id'}">selected="selected"</c:if>>Bahasa Indonesia</option>
							<option value="ms" <c:if test="${sourceLang == 'ms'}">selected="selected"</c:if>>Bahasa Melayu</option>
							<option value="tr" <c:if test="${sourceLang == 'tr'}">selected="selected"</c:if>>Türkçe</option>
							<option value="th" <c:if test="${sourceLang == 'th'}">selected="selected"</c:if>>ไทย</option>
							<option value="mn" <c:if test="${sourceLang == 'mn'}">selected="selected"</c:if>>Монгол хэл</option>
						</select>
						<span><spring:message code="msg_0419"/></span>
					</div>
	                <div id="standalone-container">
	                	<div id="scroller_2" class="textCountWarp" style="">
			                <div class="textCount" id="lengthCnt" style="float:right;">0/10000</div>&nbsp;
                        </div>
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
			            <div id="editor-container">${fn:replace(answer, br, crcn)}</div>
		            </div>
		            <div class="nick_open">
			            <p><spring:message code="msg_0336"/></p>
			            <ul>
				            <li answer="y"><spring:message code="msg_0331"/>
					            <input name="FlagNickName" type="radio"  class="frm-hiddenL" style="display:none;" value="N" <c:if test="${flagNickName == 'N'}">checked</c:if>/>
				            </li>
				            <li class="open_agree" answer="n"><spring:message code="msg_0332"/>
					            <input name="FlagNickName" type="radio" class="frm-hiddenR" style="display:none;" value="Y" <c:if test="${flagNickName == 'Y' or flagNickName == ''}">checked</c:if>/>
				            </li>
			            </ul>
		            </div>
		            <button class="register" onClick="javascript:NewAnswerChk('frm_qa','/answer/answerSave?Event=<%=EEvent%>', 'Y')"><spring:message code="msg_0356"/></button>
	                <%
        	            pageContext.setAttribute("crcn", "\r\n"); //Space, Enter
                        pageContext.setAttribute("br", "<br/>"); //br 태그
	                %>
	                <div class="atm_wa_con" style="display:none">
		                <style>
			                .textCountWarp {width:100%}
			                @media screen and (min-width:828px) {.textCountWarp {width:828px}}
		                </style>
		                <div id="scroller" class="textCountWarp" style="position:absolute;margin:-25px -27px">
			                <div class="textCount" id="lengthCnt" style="position:absolute;padding:0 3px 0 3px;right:35px;color:#aaa;background-color:#fff;">0/10000</div>&nbsp;
                        </div>
		                <textarea name="Contents" rows="20" class="atm_wa_input2" style="display:none;">${fn:replace(answer, br, crcn)}</textarea>
		                <div style="height:10px;"></div>
		                <div class="atm_wa_addimg">
			                <div class="atm_wq_addimg_pc_wrapper">
				                <div class="atm_wq_addimg_pc">
					                <c:forEach var="file" items="${files}" varStatus="status">
						                <c:set var="fSeq" value="${file.seq}"/>
						                <%
							                String fSeq = pageContext.getAttribute("fSeq").toString();
						                %>
						                <a href="javascript:goSubmit('frm_qa','/default/fileDelete?FileSeq=<%=fSeq%>&ParentName=Answer&ParentSeq=<%=Seq%>&CurPageName=<%=CurPageName%>&QuestionSeq=<%=QuestionSeq%>','answer_ifrm');"><spring:message code="msg_0228"/></a>&nbsp;&nbsp;&nbsp;&nbsp;${file.fileName}<br><br>
					                </c:forEach>
						            <input type="hidden" name="FileMax" value="${fileMax}">
    					            <%
	    					            int fileMax = Integer.parseInt(request.getAttribute("fileMax").toString());
		    				            int writeMax = Integer.parseInt(request.getAttribute("writeMax").toString());
			    			            for(int i = 1; i <= fileMax; i++) {
				    	            %>
					                <div style="position:relative; margin-bottom:5px;display:none;">
  						                <input type="text" id="atm_fileNamee<%=i%>" class="atm_wq_input3" value="" readonly="readonly" />
		 				                <div class="atm_wq_c5">
		  					                <input type="button" value='<spring:message code="msg_0329"/>' class="atm_wq_c5_button" />  
		  					                <input name="File<%=i%>" accept="image/*" type="file" class="atm_wq_c5_hidden" onChange="javascript: document.getElementById('atm_fileNamee<%=i%>').value = this.value" />
    		  				            </div>
	    				            </div>
		    			            <%}%>
			    	            </div>
			                </div>
    			        <div style="height:30px;"></div>
	    		        <div class="atm_wa_G3">
		    		        <div class="atm_wa_opt">
			    	        </div>
			            </div>
    		        </div>
	    	        <div class="atm_wa_btng2">
		    	        <p class="atm_btn_gray" onClick="fGoTSave('ACT=TA_SAVE&QuestionSeq=<%=QuestionSeq%>')"><spring:message code="msg_0357"/></p>
			            <p class="atm_btn_org" onClick="javascript:NewAnswerChk('frm_qa','/answer/answerSave?Event=<%=EEvent%>', 'Y')"><spring:message code="msg_0266"/></p>
    		        </div>
                </form>
                <script type="text/javascript">
                    var MORMAL_SEND_URL = '${MORMAL_SEND_URL}';
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
	                    if(AnswerCount > 10000){
                    		alert('<%=CommonUtil.getLangMsg(request, "msg_0334")%>');
                    		$(answerInput).html($(answerInput).html().substring(0, 10000));
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
                <% if(Seq == "") {%>
                	function fGoTSave(param)
                	{
                		alert('<%=CommonUtil.getLangMsg(request, "msg_0340")%>');
                	}
                <%} else {%>
                	function fGoTSave(param)
                	{
		                var FormName = eval(FormName);
                		var delta = quill.getContents();
                		var myEditor = document.querySelector('#editor-container');
                		var html = myEditor.children[0].innerHTML;	
                		var lengthSize = quill.getLength();
                		var content = quill.getText(0, lengthSize);
                		var AnswerCount = content.split(" ").join("").split('\n').join("").length;
                		const text = delta.filter((op) => typeof op.insert === 'string').map((op) => op.insert).join('');
                		$('form[name=frm_qa]')[0].Contents.value = html;
                		if ($('form[name=frm_qa]')[0].Contents.value)
                			answer_fAjax('/common/tempSave', 'frm_qa', param);
                    	}
	                function answer_fAjax(url, frm, param) {
                		if (document.xhr) {
                			$('#Tip').text('<%=CommonUtil.getLangMsg(request, "msg_0341")%>').css('display', 'block');
                			setTimeout(function () { $('#Tip').stop().fadeOut(300) }, 3000);
                			return;
                		}
		                var myEditor = document.querySelector('#editor-container');
                		var html = myEditor.children[0].innerHTML;
                		$('form[name='+frm+']')[0].Contents.value = html;
                		document.xhr = $.ajax({
                			type: 'post',
                			url: url,
                			data: (frm ? $('form[name=' + frm + ']').serialize() + '&' : '') + param,
                			dataType: 'json',
                			crossDomain: true,
                			success: function (r) {
                				switch (r.result) {
                					case 'TA_LOAD':
                						if (r.arr[0] && r.arr[0].contents)
                						{
                							var obj = $('form[name='+frm+']')[0].contents;
                							myEditor.children[0].innerHTML =  r.arr[0].contents;
                							fAnswerCount(obj);
                						}
                						break;
                					default:
                						if (r.result) alert(r.result);
                						break;
                    			}
                			},
    			            error: function (r, textStatus, err) {
    	            			if (r.responseText && r.responseText.substr(0, 13) == '<!--LOGOFF-->') { top.location = MORMAL_SEND_URL; return; }
                				console.log(r);
                			},
                			complete: function () {
            				    document.xhr = false;
            			    }
            		    });
                	}
                	$(document).ready(function(){
                		answer_fAjax('/common/tempSave', 'frm_qa', 'ACT=TA_LOAD&QuestionSeq=<%=QuestionSeq%>');
                	});
                	$(window).on('beforeunload', function(){if (!document.isSaved) fGoTSave('ACT=TA_SAVE&QuestionSeq=<%=QuestionSeq%>&isAUTO=Y')});
                	setInterval(function(){fGoTSave('ACT=TA_SAVE&QuestionSeq=<%=QuestionSeq%>&isAUTO=Y')}, 60000);
                    <% } %>
                </script>
            </div>
        </div>
    </div>
</div>
<iframe name="answer_ifrm" width="100%" height="0" frameborder="0"></iframe>
<script>
    function moveScroller()
    {
    	var scroller = $('#scroller');
    	var anchor = scroller.parent();
	    var move = function() {
		var st = $(window).scrollTop();
		var ot = anchor.offset().top;
		if(st > ot)
			scroller.css({position:'fixed',top:'35px'});
		else
			scroller.css({position:'absolute',top:'unset'});
    	};
    	$(window).scroll(move);
    }
    moveScroller();
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
    		  maxStack: 10000,
    		  userOnly: true
    		}
    	},
    	placeholder:'<%=CommonUtil.getLangMsg(request, "msg_0335")%>'
    	, theme: 'snow'
    });
    //var Block = quill.import('blots/block');
    //Block.tagName = 'SPAN';
    //quill.register(Block, true);
    quill.getModule('toolbar').addHandler('image', function(){
    	selectLocalImage();
    });
    var customButton = document.querySelector('#custom-button');
    customButton.addEventListener('click', function() {
    	quill.history.undo();
    });
    //퀼 이미지 콜백함수
    function selectLocalImage() {
    	var url = '${libIMG_URL}';
    	const input = document.createElement("input");
    	input.setAttribute("type", 'file');
    	input.setAttribute("accept", '.jpg,.jpeg,.png');
    	input.click();
    	//console.log('파일 업로드');
    	// Listen upload local image and save to server
    	input.onchange = function() {
    		const fd = new FormData();
    		const file = $(this)[0].files[0];
    		fd.append('image', file);
    		$.ajax({
    			type: 'post',
    			encType: 'multipart/form-data',
    			url: '/common/quillFileUpload',
    			data: fd,
    			processData: false,
    			contentType: false,
    			beforeSend: function(xhr) {
    				//xhr.setRequestHeader( $('#_csrf_header').val(), $('#_csrf').val(); );
    			},
    			success: function(data) {
    				const range = quill.getSelection();
    				quill.insertEmbed(range.index, 'image', url+'/UploadFile/quill/upload/'+data);
    			},
    			error: function(err) {
    				console.error('Error:::' + err);
    			}
    		});
    	};
    }
</script>
<script src="http://code.jquery.com/jquery-1.12.4.js"></script>
<script type="text/javascript" src="/Common/src/answer/answerWrite/answerWrite.js?1.0" ></script>
</body>
