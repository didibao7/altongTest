<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp"%>
<%@ include file="/Common/include/global.jsp"%>
<head>
<link rel="stylesheet" href="/pub/member/myQuestion/myQuestion.css?ver=1.1">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.4">
<link rel="stylesheet" href="/pub/member/myInfo/myInfo.css?ver=1.1">
<link rel="stylesheet" href="/Common/crop/cropper.css?ver=1.1">
<link rel="stylesheet" href="/pub/css/languages_common.css?ver=1.2">
<link rel="stylesheet" href="/pub/css/lang_myJoin.css?ver=1.2">
<link rel="stylesheet" href="/pub/css/languages_mediaQuery.css?ver=1.3">
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<div id="atm_menjoin_wrapper">
	<div class="center">
		<div class="atm_graytop_ttbar">
		    <div class="atm_graytop_ttbar_pc">
		        <h1 class="atm_graytop_c1"><spring:message code="msg_0167"/></h1>
		    </div>
		</div>
		<div class="atm_memjoin_con">
			<form name="myjoin_frm_sch" method="post" onSubmit="return false;">
			    <div class="profile">
			        <p class="atm_memjoin_c2"><spring:message code="msg_0153"/></p>
			        <div class="photo">
			            <label class="label" data-toggle="tooltip" title='<spring:message code="msg_0153"/>' for="input">
				            <c:choose>
								<c:when test="${global.get('UserPhoto') ne '' && not empty global.get('UserPhoto')}">
									<img id="avatar" src="/UploadFile/Profile/${global.get('UserPhoto')}">
								</c:when>
								<c:otherwise>
									<img id="avatar" src="/Common/images/img_thum_base0.jpg">
								</c:otherwise>
							</c:choose>
			            </label>
						<input type="file" class="sr-only" id="input" name="image" accept="image/*">
			            <div class="aprogress">
			                <div class="aprogress-bar progress-bar-striped progress-bar-animated" role="aprogressbar" aria-valuenow="0"
			                aria-valuemin="0" aria-valuemax="100">0%</div>
			            </div>
			            <div class="alert" role="alert"></div>
			        </div>
			    </div>	
			    <!-- 모달 -->
				<div class="modal fade" id="modal1" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="modalLabel"><spring:message code="msg_0157"/></h5>
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">
								<div class="img-container" style="max-height:250px!important;">
									<img id="image" src="" class="cropper-hidden">
								</div>
								<div class="btn-group">
									<button type="button" class="btn btn-primary" data-method="rotate" data-option="-45" title="Rotate Left" id="rrotateImg">
										<span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="cropper.rotate(-45)">
											<span class="fa fa-rotate-left"><spring:message code="msg_0158"/></span>
										</span>
									</button>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="msg_0159"/></button>
								<button type="button" class="btn btn-primary" id="crop"><spring:message code="msg_0160"/></button>
							</div>
						</div>
					</div>
				</div>
			<!-- // 모달 -->
			    <div class="atm_memjoin_top atm_memjoin_opt">
			        <p class="atm_memjoin_c2"><spring:message code="msg_0884"/></p>
			        <p class="atm_memjoin_c1">+${member.country}</p>
			        <p class="atm_memjoin_c1">${phone}</p>    
			    </div>
			    
			    <div class="atm_memjoin_con_el atm_border">
			        <div class="atm_memjoin_opt">
			            <label class="atm_memjoin_c2" for="NickName"><spring:message code="msg_0310"/></label>
			            <input name="NickName" type="text" value="${member.nickName}" id="NickName" maxlength="20" class="atm_memjoin_input5" readonly />
			        </div>
			    
			        <div class="atm_memjoin_opt">
			            <label class="atm_memjoin_c2" for="pre_password"><spring:message code="msg_0885"/></label>
			            <input name="Password" type="password" id="pre_password" class="atm_memjoin_input5">
			        </div>
			    
			        <div class="atm_memjoin_opt">
			            <label class="atm_memjoin_c2" for="new_password"><spring:message code="msg_0886"/></label>
			            <input name="NewPassword1" type="password" id="new_password" class="atm_memjoin_input5" placeholder='<spring:message code="msg_0415"/>'>
			        </div>
			    
			        <div class="atm_memjoin_opt">
			            <label class="atm_memjoin_c2" for="new_password_com"><spring:message code="msg_0887"/></label>
			            <input name="NewPassword2" type="password" id="new_password_com" class="atm_memjoin_input5" placeholder='<spring:message code="msg_0415"/>'>
			        </div>
			        
			        <div class="atm_memjoin_opt">
			            <label class="atm_memjoin_c2" for="my_nation"><spring:message code="msg_0888"/></label>
			            <input name="myNation" type="text" value="${nationString}" id="my_nation" class="atm_memjoin_input5" readonly>
			        </div>

			        <div class="atm_memjoin_opt">
			            <label class="atm_memjoin_c2" for="my_lang"><spring:message code="msg_0418"/></label>
			            <select name="lang" id="my_lang">
                            <option value="">-- select one --</option>
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
			        </div>
			    
			        <div class="atm_memjoin_opt atm_memjoin_opt_mail">
			            <label class="atm_memjoin_c2" for="email_text"><spring:message code="msg_0129"/></label>
			            <div>
			                <input name="Email1" type="text" value="${email1}" id="email_text" class="atm_memjoin_input3">
			                <span class="atm_memjoin_c3">@</span>    
			                <input name="Email2" type="text" value="${email2}" id="Email2" class="atm_memjoin_input3" maxlength="20">
			                <select id="email_domain" class="atm_memjoin_input4"  onchange="$('#Email2').val($('#email_domain').val());">
			                    <option selected="selected" value=""><spring:message code="msg_0889"/></option>
			                    <option value="naver.com">naver.com</option>
			                    <option value="hanmail.net">hanmail.net</option>
			                    <option value="hotmail.com">hotmail.com</option>
			                    <option value="gmail.com">gmail.com</option>
			                </select> 
			            </div>
			        </div>
			    
			        <div class="atm_memjoin_opt">
			            <label class="atm_memjoin_c2" for="myIntro"><spring:message code="msg_0890"/></label>
			            <input name="Intro" type="text" value="${member.intro}" maxlength="50" id="myIntro" class="atm_memjoin_input5" placeholder='<spring:message code="msg_0891"/>'>
			        </div>
			    </div><!-- atm_memjoin_con_el atm_border end -->
			    <div class="atm_memjoin_btn">
			        <p class="atm_memjoin_btn1 atm_radius" onClick="javascript:location.href='/default/main';"><spring:message code="msg_0159"/></p>
			        <p class="atm_memjoin_btn2 atm_radius" onClick="javascript:MyJoinChk('myjoin_frm_sch', '/member/myJoinSave?FlagSave=E');"><spring:message code="msg_0256"/></p>
			    </div>
		    </form>
		</div>
	</div>
</div><!-- atm_wrapper end -->
<iframe name="ifrm" width="100%" height="0" frameborder="0"></iframe>
<!-- <iframe name="ifrm" width="100%" height="0" frameborder="0"></iframe>
<script>
	function fleavebtn() {
		var text = "회원탈퇴는 (02)330-3000으로 전화를 통해 신청을 부탁드립니다.\n\
참고로 회원탈퇴는 매우 신중히 결정하시기를 권해드립니다.\n\
탈퇴 시 회원님의 보유 알은 소멸되고 향후 계속 수익이 발생할 경우 이 수익 역시 (주)알통에 귀속됩니다.\n\
만약 이후 재가입을 원하실 경우 최소 6개월 경과 후에야 가능하므로 탈퇴 전에 신중에 신중을 거듭하여 신청해 주십시오.\n\
(재가입 관련 정책은 추후 변경될 수 있습니다.)";
		if (confirm(text))
			location.href='/default/CS/customerService';
	}
</script> -->
<script src="/pub/member/myJoin/myJoin.js?ver=1.2"></script>
<script src="/Common/js/bootstrap.min.js"></script>
<script src="/Common/crop/cropper.js"></script>
</body>