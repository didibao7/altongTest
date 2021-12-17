<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jasp.buildin.*" %>
<%@ page import="jasp.util.*" %>
<%@ page import="jasp.vbs.*" %>
<%@ page extends="jasp.servlet.JspBase" %>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ page import="jasp.util.*"%>
<% 
        //프로필 사진 정보 조회
    variant s_cls_SQL = new variant();
    String Photo = "";
	int userSeq2 = 0;
	if(session.getAttribute("UserSeq") != null) {
		userSeq2 = Integer.parseInt(session.getAttribute("UserSeq").toString());
	}
	//out.println("userSeq2 : " + userSeq2);
    if (userSeq2 > 0) {
        Photo = CommonUtil.getProfilePhoto(userSeq2);
        //프로필 사진이 없는 경우 CHECK
        if (Photo == null || Photo.equals("")) {
%>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="/Common/crop/cropper.css">
<link rel="stylesheet" href="/pub/member/myInfo/myInfo.css">
<script src="/Common/crop/cropper.js"></script>

	
<div id="divProfilePhotoWrapper" style="display:none">
	<div id="divProfilePhoto">
		<div id="divProfilePhotoClose">
			<a href="javascript:void(0);" onClick="fnClose_ProfilePhoto(0)">
				<i></i>
				<i></i>
			</a>
		</div>
		<div id="divProfilePhotoTitle">
			<p><spring:message code="msg_0150"/></p>
		</div>
		<div id="divProfilePhotoContent">
			<p class="atm_wq_c5_msg">
				<spring:message code="msg_0151"/> <br>
				<spring:message code="msg_0152"/> 
			</p>
			<div>
				<figure>
					<label class="label" data-toggle="tooltip" title='<spring:message code="msg_0153"/>'>
						<img id="avatar" src="/pub/css/profile/img_thum_base0.jpg" style="width:80px; height:80px; cursor: pointer;">
						<input type="file" class="sr-only" id="input" name="image" accept="image/*">
					</label>
				</figure>
				<!-- <p class="atm_wq_c5_button_p"><spring:message code="msg_0154"/></p> -->
				<form name="frm_photo" method="post" enctype="multipart/form-data">
					<input type="hidden" name="PhotoName" id="idPhoto" value="">
				</form>
				<p class="atm_wq_c5_msg">
				<spring:message code="msg_0155"/>
				</p>
				<div class="aprogress" style="display:none">
					<div class="aprogress-bar progress-bar-striped progress-bar-animated" role="aprogressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100">0%</div>
				</div>
			</div>
		</div>
		<div id="divProfilePhotoCloseToday">
			<p onclick="fnClose_ProfilePhoto(1)"><spring:message code="msg_0156"/></p>
		</div>
	</div>
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
</div>



<script language="javascript">
	document.addEventListener("DOMContentLoaded", function(){
		var cookieValue = fnGetCookie_PhotoProfile('cookie_profile_photo');
  		//console.log("cookieValue : " + cookieValue)
	      if (cookieValue == null || cookieValue != 'false') {
			$('#divProfilePhotoWrapper').css('display','block')
	      }
	});
  // 쿠키(Cookie) 값 '1일' 저장 ('자정'에 삭제)
  function fnSetCookie_ProfilePhoto(sName, sValue) {
      expday = new Date();
      expday.setDate(expday.getDate()+1);
      expday.setHours(0,0,0,0);
  
      document.cookie = sName + "=" + escape(sValue) + "; path=/; expires=" + expday.toGMTString() + ";";
  }
  // 쿠키(Cookie) 값 읽기
  function fnGetCookie_PhotoProfile(sName) {
      var aCookie = document.cookie.split("; ");
      for ( var i=0; i < aCookie.length; i++ ) {
            var aCrumb = aCookie[i].split("=");
            if ( sName == aCrumb[0] )
                 return unescape(aCrumb[1]);
      }
      return null;
  }
  /* 팝업 레이어 제어 (프로필 사진 등록) */
  const $PopUpLayer_ProfilePhoto = $('#divProfilePhotoWrapper');
  // 팝업 레이어 보여주기
  function fnPopup_ProfilePhoto() {
      var cookieValue = fnGetCookie_PhotoProfile('cookie_profile_photo');
      $PopUpLayer_ProfilePhoto.hide();
  
      if (cookieValue != 'false') {
          $PopUpLayer_ProfilePhoto.show();
          $('html > body').css('overflow', 'hidden');
      }
  }
  // 팝업 레이어 닫기
  function fnClose_ProfilePhoto(flag) {
      if ( flag == 1 ) {
           fnSetCookie_ProfilePhoto('cookie_profile_photo', false);
      }

      $PopUpLayer_ProfilePhoto.hide();

      $('html > body').css('overflow', 'auto');
  }

  /* 프로필 사진 업로드 처리 */
  var uploadedImg = "";

  /*
  const $photo = $('input[name="Photo"]');

  $('.atm_wq_c5_button_p').click(function() {
      $photo.click();
  });

  $photo.change(function() {
      $.ajax({
          url: "/default/profile/uploadProfileImg",
          type: "post",
          enctype: 'multipart/form-data',
          data: new FormData($('form[name="frm_photo"]')[0]),
          processData: false,
          contentType: false,
          async: false,
          success: function(data, textStatus, jqXHR) {
              uploadedImg = data;
              
              //console.log('data : ' + data)
              if(uploadedImg == 'nofile') {
            	  //alert('등록할 수 없는 파일입니다.');
              } 
              else if(uploadedImg == 'N') {
            	  alert('등록할 수 없는 파일입니다.');
              }
              else {
        	      $('img.atm_wq_c5_img_prof').attr('src', ('/Uploadfile/Profile/' + uploadedImg))
        	      $('#idPhoto').val(uploadedImg)
        	
        	      $('p.atm_wq_c5_button_p').css('background-color', '#2ac1bc')
        	      $('p.atm_wq_c5_button_p').text("사진 변경")
        	      $('p.atm_wq_c5_msg').text("프로필 사진이 등록되었습니다. 다른 사진을 원하시면 [사진 변경] 버튼을 클릭하세요.")
        	  }
          },
          error: function(request, err, ex) {
              alert(err + " ===> " + ex);
          }
      });
  });
  */
  //fnSetCookie_ProfilePhoto('cookie_profile_photo', true);
  
  
  //fnPopup_ProfilePhoto();
</script>
<script src="/Common/js/bootstrap.min.js"></script>
<script src="/pub/default/logincheck/logincheck.js"></script>

<%
        }
    }
%>