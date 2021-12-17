<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jasp.buildin.*" %>
<%@ page import="jasp.util.*" %>
<%@ page import="jasp.vbs.*" %>
<%@ page extends="jasp.servlet.JspBase" %>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<% 
        //프로필 사진 정보 조회
    variant s_cls_SQL = new variant();
    String Photo = "";
%>
﻿<%
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

<style tyle="text/css">
  #divProfilePhotoWrapper {
      display: none;
      position: absolute;
      background-color: rgba(0,0,0,0.5);
      height: 100%;
      width: 100%;
      left: 0;
      right: 0;
      margin: auto;
      z-index: 999;
  }
  
  #divProfilePhoto {
      position: absolute;
      background-color: rgba(255,255,255,1.0);
      z-index: 200;
      width: 90%;
      height: 80%;
      max-width: 350px;
      max-height: 300px;
      left: 0;
      right: 0;
      top: 0;
      bottom: 0;
      margin: auto;
      border-radius: 1em;
      display: block;
      padding: 10px;
  }
  
  #divProfilePhotoTitle {
      margin-bottom: 6%;
  }
  
  #divProfilePhotoTitle > p {
      text-align: center;
      font-size: 2.0em;
      font-weight: bold;
  }
  
  #divProfilePhotoContent {
      margin: auto;
      text-align: center;
      word-break: keep-all;
      font-size: 14px;
      font-weight: bold;
      color: #5A5A5A;
  }
 
  #divProfilePhotoClose {
      text-align: right;
      bottom: 1.0em;
  }

  #divProfilePhotoClose > a > img {
      width: 23px;
      height: 23px;
  }
 
  #divProfilePhotoCloseToday {
      width: 90%;
      border-top: 2px solid;
      position: absolute;
      bottom: 1.0em;
  }
  
  #divProfilePhotoCloseToday > p {
      text-align: right;
      font-weight: bold;
      font-size: 13px;
      color: #3A3A3A;
      cursor: pointer;
  }
  
  .atm_wq_c5_img_prof  { border-radius:15px; width:80px; height:80px; margin-right:10px; }
  .atm_wq_c5_input     { display:inline-block; width:50%; height:55%; padding:7px 0px; margin-right:5px; border:#d1d1d1 1px solid; }
  .atm_wq_c5_button    { display:inline-block; width:27%; height:30%; position:absolute; top:52px; right:10; font-size:12px; font-weight:bold; letter-spacing:-1px; padding:7px 0; color:#ffffff; background-color:#9e9e9e; text-align:center; border:0; }
  .atm_wq_c5_button_p  { display:inline-block; width:120px; margin:0 4px; padding:10px 0; border-radius:100px; font-size:12px; font-weight:bold; letter-spacing:0px; text-align:center; color:#fff; background-color:#ff5001; cursor:pointer; }
  .atm_wq_c5_button_p1 { display:inline-block; width:120px; margin:0 4px; padding:10px 0; border-radius:100px; font-size:12px; font-weight:bold; letter-spacing:0px; text-align:center; color:#fff; background-color:#ff5001; cursor:pointer; }
  .atm_wq_c5_button_p2 { display:inline-block; width:120px; margin:0 4px; padding:10px 0; border-radius:100px; font-size:12px; font-weight:bold; letter-spacing:0px; text-align:center; color:#fff; background-color:#2ac1bc; cursor:pointer; }
  .atm_wq_c5_hidden    { width:0; position: absolute; right: 0px; top: 0px; opacity: 0; filter: alpha(opacity=0); -ms-filter: "alpha(opacity=0)"; -khtml-opacity: 0; -moz-opacity: 0; }
</style>

<div id="divProfilePhotoWrapper">
  <div id="divProfilePhoto">
    <div id="divProfilePhotoClose">
      <a href="#" onClick="fnClose_ProfilePhoto(0)"><img src="/Common/images/btn_popup_close.png" border="0" title="창닫기" /></a>
    </div>
    <div id="divProfilePhotoTitle">
      <p>프로필 사진 안내</p>
    </div>
    <div id="divProfilePhotoContent" align="center">
      <p class="atm_wq_c5_msg">
        프로필 사진이 등록되지 않았습니다. <br>
        본인의 얼굴 사진을 등록해 주세요! 
      </p>

      <div style="display:inline-block; width:75%; margin-top:25px; border:0px solid #000000;">
        <img src="/Common/images/img_thum_base0.jpg" class="atm_wq_c5_img_prof" />
        <p class="atm_wq_c5_button_p">사진 등록</p>
        <form name="frm_photo" method="post" enctype="multipart/form-data"> 
          <input type="file" name="Photo" class="atm_wq_c5_hidden" accept="image/*" />
          <input type="hidden" name="PhotoName" id="idPhoto" value="">
        </form>
      </div>
    </div>
    <div id="divProfilePhotoCloseToday">
      <p onclick="fnClose_ProfilePhoto(1)">오늘 하루동안 보지 않기 X</p>
    </div>
  </div>
</div>

<script language="javascript">
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
      //console.log(cookieValue);
  
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

  //fnSetCookie_ProfilePhoto('cookie_profile_photo', true);
  fnPopup_ProfilePhoto();
</script>
<%
        }
    }
%>