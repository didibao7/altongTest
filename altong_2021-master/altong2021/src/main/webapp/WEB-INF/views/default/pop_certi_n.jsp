<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NICE신용평가정보 - CheckPlus 안심본인인증</title>
</head>
<body>
<style>
ul,li {list-style-type:none;}
img,p,li {border:0;margin:0;padding:0; vertical-align:middle;}
html,body {width:100%;height:100%;padding:0;margin:0;color:#444444; list-style:none; font-size:12px;  font-family:'Nanum Gothic', sans-serif; }

.atm_certi_img{ width:100%;}

.atm_gnb_btnx{ position:absolute; right:5px; top:5px; width:44px;}
.atm_pop_wrapper{ width:100%; padding:0 0 0 0; display:table; position:absolute; height:100%; background-image:url(/Common/images/bg_alpha80.png);}
.atm_pop_con{text-align:center;position:relative; height:100%; display: table-cell; vertical-align: middle; color:#fff; position:relative;}
.atm_pop_certiInfo{ display:inline-block; width:90%;}
</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="/Common/js/mobile.js"></script> 

<script language='javascript'>
	function fnPopup() {
        // [수정(2017.12.22): 김현구]
		/*
		window.open('', 'popupChk', 'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');		
		document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";
		document.form_chk.target = "popupChk";
		document.form_chk.submit();
		*/
		var m          = document.form_chk.m.value;
		var EncodeData = document.form_chk.EncodeData.value;

		window.open('https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb?m=' + m + '&EncodeData=' + EncodeData, 'popupChk', 'width=420, height=730, top=50, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbars=yes, resizable=yes');
	}
</script>


<!-- 본인인증 서비스 팝업을 호출하기 위해서는 다음과 같은 form이 필요합니다. -->
<form name="form_chk" method="post">
  <input type="hidden" name="m" value="checkplusSerivce">						<!-- 필수 데이타로, 누락하시면 안됩니다. -->
  <input type="hidden" name="EncodeData" value="${sEncData}">		<!-- 위에서 업체정보를 암호화 한 데이타입니다. -->
  
  <div class="atm_pop_wrapper">
    <!--wrapper start -->
    <div class="atm_pop_con">
      <a href="/default/joinRule"><img src="/Common/images/btn_x_3.png" class="atm_gnb_btnx" /></a>
      <div class="atm_pop_certiInfo" id="target">
       	<img src="/Common/images/pop_certi_info_1.png" class="atm_certi_img" />
       	<img src="/Common/images/pop_certi_info_2_gif.gif" class="atm_certi_img" />
       	<img src="/Common/images/pop_certi_info_3.png" class="atm_certi_img" />
       	<a href="javascript:fnPopup();"><img src="/Common/images/pop_certi_info_4.png" class="atm_certi_img" /></a>
      </div>
	</div>
    <!--wrapper end -->
  </div>	
</form>
</body>

<script>
	if(!isMobile()) {
		$('.atm_pop_certiInfo').css("maxWidth", "608px");
	}
</script>
</body>
</html>