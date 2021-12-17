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
<body>
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/styles/monokai-sublime.min.css" />
<link rel="stylesheet" href="//cdn.quilljs.com/1.3.6/quill.snow.css" />
<link rel="stylesheet" href="/pub/question/questionWrite/questionWrite.css?ver=1.2" />
<script>
var msg001 = getLangStr("jsm_0112");
</script>
<%@ include file="/pub/menu/topMenu.jsp"%>
<div id="atm_wrapper" style="overflow-x:hidden;">
	<div id="atm_wa_wrapper">
		<div class="center">
			<h1 class="atm_wa_ttbar"><spring:message code="msg_0938"/></h1>
			<div id="atm_wq_wrapper0">
				<div class="atm_wq_ttbar">
					<div class="atm_wq_ttbar_pc" style="display:none;">
						<a href="javascript:history.back();"><img src="/Common/images/btn_back_bold.png" class="atm_wq_btn_L1"/></a>
						<a href="javascript: fGoTSave('ACT=TQ_SAVE')"><img src="/Common/images/btn_save.png" class="atm_wq_btn_R1"/></a>
						<div class="clear_both"></div>
					</div>
				</div>
				<form name="frm_qa" method="post" onSubmit="return false;" enctype="multipart/form-data">
					<input type="hidden" name="Seq" value="<%=Seq%>">
					<input type="hidden" name="FlagUse" value="">
					<input type="hidden" id="flagNickPrice" name="flagNickPrice" value="${flagNickPrice}">
					<div class="atm_wq_con"><!-- 최대 글자수를 기존 50글자에서 150글자로 수정 -->
						<input type="text" name="Title" placeholder='<spring:message code="msg_0327"/>' class="atm_wq_input1" maxlength="150" value="<%=Title%>" autocomplete="off" onkeyup="syncContents();if($(this).val().length >= $(this).attr('maxlength')){alert(msg001);$(this).val($(this).val().substring(0, $(this).attr('maxlength')));}"/>
						<div></div>
						<div class="atm_wq_input1" style="border-bottom: 1px solid #fff;">
							<select name="lang" id="joinLang">
							  	<option value="" <c:if test="${lang == ''}">selected="selected"</c:if>>-- select one --</option>
								<option value="ko" <c:if test="${lang == 'ko'}">selected="selected"</c:if>>한국어</option>					
								<option value="en" <c:if test="${lang == 'en'}">selected="selected"</c:if>>English</option>
								<option value="es" <c:if test="${lang == 'es'}">selected="selected"</c:if>>Español</option>
								<option value="fr" <c:if test="${lang == 'fr'}">selected="selected"</c:if>>Français</option>
								<option value="pt" <c:if test="${lang == 'pt'}">selected="selected"</c:if>>Português</option>
								<option value="de" <c:if test="${lang == 'de'}">selected="selected"</c:if>>Deutsch</option>
								<option value="ar" <c:if test="${lang == 'ar'}">selected="selected"</c:if>>العربية</option>
								<option value="fa" <c:if test="${lang == 'fa'}">selected="selected"</c:if>>فارسی</option>
								<option value="ru" <c:if test="${lang == 'ru'}">selected="selected"</c:if>>Русский</option>
								<option value="ja" <c:if test="${lang == 'ja'}">selected="selected"</c:if>>日本語</option>
								<option value="it" <c:if test="${lang == 'it'}">selected="selected"</c:if>>Italiano</option>
								<option value="zh" <c:if test="${lang == 'zh'}">selected="selected"</c:if>>中文</option>
								<option value="vi" <c:if test="${lang == 'vi'}">selected="selected"</c:if>>Tiếng Việt</option>
								<option value="hi" <c:if test="${lang == 'hi'}">selected="selected"</c:if>>हिन्दी</option>
								<option value="bn" <c:if test="${lang == 'bn'}">selected="selected"</c:if>>বাংলা</option>
								<option value="id" <c:if test="${lang == 'id'}">selected="selected"</c:if>>Bahasa Indonesia</option>
								<option value="ms" <c:if test="${lang == 'ms'}">selected="selected"</c:if>>Bahasa Melayu</option>
								<option value="tr" <c:if test="${lang == 'tr'}">selected="selected"</c:if>>Türkçe</option>
								<option value="th" <c:if test="${lang == 'th'}">selected="selected"</c:if>>ไทย</option>
								<option value="mn" <c:if test="${lang == 'mn'}">selected="selected"</c:if>>Монгол хэл</option>
							</select>
							<span><spring:message code="msg_0419"/></span>
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
										%>${file.fileName}&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="frm_qa.FileDelYn.value='Y'; goSubmit('frm_qa','/default/fileDelete?FileSeq=<%=FileSeq%>&ParentName=Question&ParentSeq=<%=Seq%>','question_ifrm');"><spring:message code="msg_0228"/></button>
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
											<input type="button" value="Search" class="atm_wq_c5_button" />  
											<input name="File<%=i%>" accept="image/*" type="file" class="atm_wq_c5_hidden" onChange="javascript: document.getElementById('atm_fileNamee<%=i%>').value = this.value" />
										</div>
									</div>
									<%}%>
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
									<li class="<%= FlagNickName.equals("N") ? "open_agree":"" %>"><spring:message code="msg_0331"/><input name="FlagNickName" id="flagNickNchk" type="radio" class="frm-hiddenL" style="display:none" value="N" <%= FlagNickName.equals("N") ? " checked":"" %>/></li>
									<li class="<%= FlagNickName.equals("Y") || FlagNickName.equals("") ? "open_agree":"" %>"><spring:message code="msg_0332"/><input name="FlagNickName" type="radio" class="frm-hiddenR" style="display:none" value="Y" <%= FlagNickName.equals("Y") || FlagNickName.equals("") ? " checked":"" %>/></li>
								</ul>
						    </div>
							<div class="atm_wq_opt adult_open">
						    	<p class="atm_wq_c8"><spring:message code="msg_0939"/></p>
						    	<ul>
						    		<li class="<%= FlagMinor.equals("N") ? "open_agree":"" %>"><spring:message code="msg_0331"/><input name="FlagMinor" type="radio" style="display:none" class="frm-hiddenL" value="N" <%= FlagMinor.equals("N") ? " checked":"" %>/></li>
						    		<li class="<%= FlagMinor.equals("Y") || FlagMinor.equals("") ? "open_agree":"" %>"><spring:message code="msg_0332"/><input name="FlagMinor" style="display:none" type="radio" class="frm-hiddenR" value="Y" <%= FlagMinor.equals("Y") || FlagMinor.equals("") ? " checked":"" %>/></li>
						    	</ul>
						    </div>
			    		</div><!-- atm_wq_G3 end -->
					</div><!-- atm_wq_con end -->
					<div class="atm_wq_btng2">
						<button class="atm_btn_gre register"><spring:message code="msg_0356"/></button>
					</div>
					<input name="EditFlag" type="hidden" value="${Seq}">
					<input name="Orin_Almoney" type="hidden" value="${questionBet}">
				</form>
				<script type="text/javascript">
					$(document).ready(function(){
						$(".ql-editor").keyup(function(){
							fAnswerCount(this);
						});
						
						/* 닉네임 공개 여부 */
						$('.nick_open ul li').click(function(){
						    
						    $('.nick_open ul li').find('input[type="radio"]').prop("checked", false);
							//$(this).children('input').prop("checked", true);
							$('.nick_open ul li').removeClass('open_agree');
				    		$('.nick_open ul li:nth-child(2)').addClass('open_agree');
							$('.nick_open ul li:nth-child(2) input').prop("checked", true);
							$('.nick_open ul li').find("input:radio[name='FlagNickName']:radio[value='Y']").attr('checked', true);
							
							var v = $(this).children('input').attr('value');
							//console.log('v : ' + v);
							if(v == 'N') {
								 $('.nick_open ul li').find('input[type="radio"]').prop("checked", false);
								if(confirm(getLangStr("jsm_0113") + '\n' + getLangStr("jsm_0114") + '\n\n' + getLangStr("jsm_0115"))) {
									//FlagNickName N check
									$('.nick_open ul li').removeClass('open_agree');
						    		$('.nick_open ul li:nth-child(1)').addClass('open_agree');
									$('.nick_open ul li:nth-child(1) input').prop("checked", true);
									return;
								}
								else {
									//FlagNickName Y check
									
									return;
								}
							}
							else {
								$(this).children('input').prop("checked", true);
							}
							
						});

						/* 미성년 공개 여부 */
						$('.adult_open ul li').click(function(){
						    $('.adult_open ul li').removeClass('open_agree');
						    $(this).addClass('open_agree');
						    $('.adult_open ul li').find('input[type="radio"]').prop("checked", false);
							$(this).children('input').prop("checked", true);
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
					
					function numberWithCommas(x) {
						return x.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
					}
					
					function quePost() {
						var pay = $("#amountSelector option:selected").val();
						var nickPay = 100;
						//FlagNickName 비공개 여부
						var flagNickChk = $('input[name=FlagNickName]:checked').val();
						var flagNickPrice = '${flagNickPrice}';
						if(flagNickChk == undefined) { flagNickChk = 'Y'; }
						if(pay == '') {
							alert(getLangStr("jsm_0048"));
							return false;
						}
						else {
							if(flagNickChk != 'Y' && parseInt(flagNickPrice) == 0) {
								pay = parseInt(pay) + parseInt(nickPay);
							}
							//console.log('pay : ' + pay);
							//return false;
							$.ajax({
								type: 'post',
								url: '/common/getChkUseAlmoney',
								data: { 'act' : 'Q', 'pay' : pay },
								dataType: 'json',
								crossDomain: true,
								success: function (r) {
									switch (r.result) {
										case 'over':
											//console.log('availPay : ' + r.arr.availPay);
											if(flagNickChk != 'Y' && parseInt(flagNickPrice) == 0) {
												alert(getLangStr("jsm_0116") + '\n' + getLangStr("jsm_0117"));
											}
											else {
												alert(r.arr.userNick + getLangStr("jsm_0053") + '\n' + getLangStr("jsm_0054") + numberWithCommas(r.arr.availPay) + getLangStr("jsm_0055"));
											}
											break;
										default:
											if($(this).attr("value") !== 2) {
												NewQuestionChk('frm_qa','/question/questionSave?SP=<%=SP%>', 'Y');
											}
											else {
												alert(getLangStr("jsm_0118"))
											}
											
											break;
									}
								},
								error: function (r, textStatus, err) {
									console.log(r);
								},
								complete: function () {
									document.xhr2 = false;
								}
							});
						}
					}
					
					$(document).ready(function(){
						$('.atm_btn_gre').attr("value", 0)
						$('.atm_btn_gre').click(function() {
							var pay = $("#amountSelector option:selected").val();
							if(pay == '') {
								alert(getLangStr("jsm_0048"));
								return false;
							}
							var lang = $('#joinLang option:selected').val();
							if(lang == '') {
								alert(getLangStr("jsm_0041"));
								return false;
							}
							
							var seq = '${Seq}';
							//setCookie('queConfirm', 1, 1);
							//setCookie('queConfirm', '', 0);
							var queConfirm = getCookie('queConfirm');
							//console.log('queConfirm : ' + queConfirm);
							//return false;
							if(queConfirm == null) {
								if(seq == '') {
									//console.log("1");
									$('#question_popup_wrapper').slideDown();
									$('#question_popup').fadeIn('fast');
								}
								else {
									quePost();
								}
							}
							else {
								if(queConfirm != '1') { // 다시 보지 않은 미체크시
									if(seq == '') {
										console.log("2");
										$('#question_popup_wrapper').slideDown();
										$('#question_popup').fadeIn('fast');
									}
									else {
										quePost();
									}
								}
								else {
									quePost();
								}
							}
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
								question_fAjax('/common/tempSave', 'frm_qa', param);
						}
					
						function question_fAjax(url, frm, param) {
							if (document.xhr2) {
								$('#Tip').text(getLangStr("jsm_0031")).css('display', 'block');
								setTimeout(function () { $('#Tip').stop().fadeOut(300) }, 3000);
								return;
							}
							var myEditor = document.querySelector('#editor-container');
							var html = myEditor.children[0].innerHTML;	
					
							$('form[name='+frm+']')[0].Contents.value = html;
							document.xhr2 = $.ajax({
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
									document.xhr2 = false;
								}
							});
						}
						
						$(document).ready(function(){
							question_fAjax('/common/tempSave', 'frm_qa', 'ACT=TQ_LOAD');
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
			</div><!-- atm_wq_wrapper0 end -->
		</div><!-- atm_wrapper end -->
	</div>
</div>
<iframe name="question_ifrm" width="100%" height="0" frameborder="0"></iframe>
<div id="question_popup">
	<div class="question_popup_wrapper">
		<h3><spring:message code="msg_0943"/></h3>
		<p><spring:message code="msg_0940"/><br><spring:message code="msg_0941"/><br><br><spring:message code="msg_0942"/></p>
		<div class="que_pop_btn">
			<a href="javascript:void(0);" onclick="javascript:fPopCloseQue(false); return false;"><spring:message code="msg_0945"/></a>
			<a href="javascript:void(0);" onclick="javascript:fPopCloseAndQuePost(); return false;"><spring:message code="msg_0946"/></a>
		</div>
		<div class="que_pop_close">
			<p onclick="fPopCloseQue(true);"><span><i class="material-icons">check_circle_outline</i><spring:message code="msg_0133"/></span></p>
			<p onclick="fPopCloseQue(false)"><span><spring:message code="msg_0944"/></span></p>
		</div>
	</div>
</div>

<script type="text/javascript" src="/pub/question/questionWrite/questionWrite.js?1.1" ></script>
	
    
</body>