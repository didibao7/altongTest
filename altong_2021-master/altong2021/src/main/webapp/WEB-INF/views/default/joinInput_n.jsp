<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NICE신용평가정보 - CheckPlus 안심본인인증</title>
</head>
<body>
<style type="text/css">

ul,li {list-style-type:none;}
img,p,li {border:0;margin:0;padding:0; vertical-align:middle;}


/*img*/
.atm_memjoin_img{ width:45px; margin-left:10px}
.atm_memjoin_btn { background-color:#2ac1bc; padding:15px 0; color:#fff; font-weight:bold; font-size:14px;margin-top:25px; text-align:center; margin:20px 0px; border-radius:5px;}
.atm_celti_img { width:15px; margin-right:3px; }

/*p태그*/

.atm_memjoin_c1{ font-size:12px; font-weight:bold; letter-spacing:-1px; margin:7px 0 0; color:#8f8f8f; display:inline-block; position:absolute; left:65px; top:10px;  }
.atm_memjoin_c2{ font-size:14px; font-weight:bold; letter-spacing:-1px; margin:7px 0 5px; color:#5a5a5a;  }
.atm_memjoin_c3{ font-size:16px; font-weight:bold; letter-spacing:-1.5px; padding:15px 0; color:#5a5a5a; display:inline-block;  }
.atm_memjoin_c4{ font-size:12px; font-weight:bold; letter-spacing:0px; margin:0 4px; color:#fff; background-color:#ff5001; padding:10px 0; border-radius:100px; display:inline-block;  width:120px;}
.atm_memjoin_c5{ font-size:12px; font-weight:bold; letter-spacing:0px; margin:0 4px; color:#fff; background-color:#2ac1bc; padding:10px 0; border-radius:100px; display:inline-block;  width:120px;}
.atm_memjoin_c6{ font-size:12px; font-weight:bold; letter-spacing:0px; margin:0 4px; color:#fff; background-color:#2ac1bc; padding:10px 0; border-radius:100px; display:inline-block;  width:150px;}
.atm_memjoin_c_R{ font-size:12px; font-weight:bold; letter-spacing:-1px; color:#5a5a5a; position:absolute; right:0; top:2px  }

.atm_celti_ridio{ display:none; }

/*본문부분*/
#atm_memjoin_wrapper0{ width:100%; padding:0 0 0 0; background-color:#f2f2f2;}

.atm_memjoin_con{ padding:4px 14px 11px; text-align:left; position:relative; background-color:#f2f2f2;}
.atm_memjoin_con_el{position:relative; background-color:#fff; margin-top:7px; padding:15px 14px;}
.atm_memjoin_top{ margin:10px 10px; position:relative;}
.atm_memjoin_btnG{ margin-top:25px; text-align:center; }

.atm_memjoin_opt{ margin:20px 0; position:relative; }
.atm_memjoin_opt_F{ margin:10px 0 20px; position:relative; }
.atm_memjoin_opt_L{ margin:20px 0 10px; position:relative; }
.atm_memjoin_input1{ font-size:14px; border:0; border-bottom: #d1d1d1 1px solid; width:30%;; padding:5px 0 0 ; display:inline-block; text-align:center; margin:0 1%; font-weight:bold; color:#5a5a5a;}
.atm_memjoin_input2{ font-size:14px; border:0; border-bottom: #d1d1d1 1px solid; width:30%;  padding:5px 0 0 ; display:inline-block; text-align:center; margin:0 1%; font-weight:bold; color:#5a5a5a;}
.atm_memjoin_input3{ font-size:14px; border:0; border-bottom: #d1d1d1 1px solid; width:42%;  padding:5px 0 0 ; display:inline-block; text-align:center; margin:0 1%; font-weight:bold; color:#5a5a5a;}
.atm_memjoin_input4{ font-size:14px; border:0;        border: #d1d1d1 1px solid; width:96%;  padding:5px 0 0 ; display:inline-block; text-align:center; margin:0 1%; font-weight:bold; color:#a7a7a7;}
.atm_memjoin_input5{ font-size:14px; border:0; border-bottom: #d1d1d1 1px solid; width:96%;  padding:5px 0 0 ; display:inline-block; text-align:left; margin:0 1%; font-weight:bold; color:#5a5a5a;}
input[type="text"].atm_memjoin_input5::-webkit-input-placeholder { color:#8f8f8f; }
input[type="text"].atm_memjoin_input5::-moz-placeholder { color:#8f8f8f; }
input[type="text"].atm_memjoin_input5:-ms-input-placeholder { color:#8f8f8f; }
input[type="text"].atm_memjoin_input5:-moz-placeholder { color:#8f8f8f; }
input[type="text"].atm_memjoin_input5:focus { outline:0; }

@media screen and (min-width: 800px) {
.atm_memjoin_con{ width:500px; display:inline-block; }
form{ text-align:center;}
}

</style>

<script>
	function NickNameReset()
	{
		document.frm_sch.FlagNickName.value = '';
	}

	function phoneReset()
	{
		document.frm_sch.phone.value = '';
	}
	
</script>
<script type="text/javascript">
	function JoinChk_n(FormName, URL)
{
var FormName = eval(FormName);

if(FormName.btn_click.value=="Y"){
	alert("현재 회원가입이 진행중 입니다.");
	return false;
}

if (FormName.FlagNickName.value != "Y")
{
    alert("닉네임 중복 확인을 해주세요.");
    FormName.NickName.focus();
    return false;
}
else if (FormName.Password1.value == "")
{
    alert("비밀번호를 입력하세요.");
    FormName.Password1.focus();
    return false;
}
else if (FormName.Password2.value == "")
{
    alert("비밀번호확인을 입력하세요.");
    FormName.Password2.focus();
    return false;
}
else if (FormName.Password2.value.length < 6 )
{
    alert("비밀번호확인을 6자 이상 입력하세요.");
    FormName.Password2.focus();
    return false;
}
else if (FormName.Password1.value != FormName.Password2.value)
{
    alert("동일한 비밀번호를 입력하세요.");
    FormName.Password2.focus();
    return false;
}
else if (FormName.Email1.value == "")
{
    alert("메일주소를 입력하세요.");
    FormName.Email1.focus();
    return false;
}
else if (FormName.Email2.value == "")
{
    alert("메일주소를 입력하세요.");
    FormName.Email2.focus();
    return false;
}
else{
		FormName.btn_click.value="Y";
		FormName.target = "_self";
		FormName.action = "" + URL + "";
		FormName.submit();
	}
}

function attenChk(FormName, URL)
{
var FormName = eval(FormName);

//FormName.target = "ifrm";
FormName.action = "" + URL + "";
FormName.submit();

}



function check_password(){

	var frmS = document.form1;
	var sPwdMsg;
	var nPwdVal = 0;

	sPwdMsg = "비밀번호는 2가지 종류 이상을 섞어서 입력바랍니다.";
	sPwdMsg = sPwdMsg + "\n\n\n* 비밀번호 설정시 유의사항";
	sPwdMsg = sPwdMsg + "\n\n- 영문 대문자 / 영문 소문자 / 숫자 / 특수기호 사용";
	sPwdMsg = sPwdMsg + "\n\n- 2가지 종류 사용시 8자리 이상 설정";
	sPwdMsg = sPwdMsg + "\n\n- 3가지 종류 사용시 6자리 이상 설정";

	nPwdVal = check_pass_val( frmS.uPass.value);

	if ( nPwdVal<2 ){
		alert(sPwdMsg);
		return false;
	}
	else if ( nPwdVal==2 ){
		if ( getByteLength(frmS.uPass.value) < 8 ){
			alert(sPwdMsg);
			return false;
		}
	}
	else{
		if ( getByteLength(frmS.uPass.value) < 6 ){
			alert(sPwdMsg);
			return false;
		}
	}

	if ( getByteLength(frmS.uPass.value) > 15 ){
		alert ("비밀번호는 15자 이하로 넣어주세요.");
		frmS.uPass.value = "";
		frmS.uPass.focus();
		return false;
	}
	return true;
}



function check_pass_val( sWord ){

	var i;
	var ch_char;
	var ch_num;
	var nReturn=0;

	// 영문대문자
	for( i=0; i < sWord.length; i++) {
		ch_char = sWord.charAt(i);
		ch_num  = ch_char.charCodeAt();
		if(  ch_num >= 65 && ch_num <= 90 ){
			nReturn = nReturn + 1;
			break;
		}
	}

	// 영문소문자
	for( i=0; i < sWord.length; i++) {
		ch_char = sWord.charAt(i);
		ch_num  = ch_char.charCodeAt();
		if(  ch_num >= 97 && ch_num <= 122 ){
			nReturn = nReturn + 1;
			break;
		}
	}

	// 숫자
	for( i=0; i < sWord.length; i++) {
		ch_char = sWord.charAt(i);
		ch_num  = ch_char.charCodeAt();
		if(  ch_num >= 48 && ch_num <= 57 ){
			nReturn = nReturn + 1;
			break;
		}
	}

	// 특수문자
	for( i=0; i < sWord.length; i++) {
		ch_char = sWord.charAt(i);
		ch_num  = ch_char.charCodeAt();
		if( ( ch_num >= 33 && ch_num <= 47 ) || ( ch_num >= 58 && ch_num <= 64 ) || ( ch_num >= 91 && ch_num <= 96 ) || ( ch_num >= 123 && ch_num <= 126 )  ){
			nReturn = nReturn + 1;
			break;
		}
	}
	return nReturn;
}

function getByteLength( data ) {
	var len = 0;
	var str = data.substring(0);
	if ( str == null ) return 0;
	for(var i=0; i < str.length; i++) {
		var ch = escape(str.charAt(i));
		if( ch.length == 1 ){
			len++;
		}
		else if( ch.indexOf("%u") != -1 ){
			len += 2;//Db가 한글을 3byte로 인식하여 2->3
		}
		else if( ch.indexOf("%") != -1 ) {
			len += ch.length/3;
		}
	}
	return len;
}


function div_OnOff(v,id){
 // 라디오 버튼 value 값 조건 비교
 if(v == "2"){
  $('#phone-radio').attr('src', '/Common/images/btn_radio_on.png');
  $('#code-radio').attr('src', '/Common/images/btn_radio.png');
  $('.atm_celti_div1').css('display', 'none');
  document.getElementById(id).style.display = ""; // 보여줌
 }else{
  $('#phone-radio').attr('src', '/Common/images/btn_radio.png');
  $('#code-radio').attr('src', '/Common/images/btn_radio_on.png');
  $('.atm_celti_div1').css('display', 'block');
  document.getElementById(id).style.display = "none"; // 숨김
 }
}
</script>

<div id="atm_wrapper" style="overflow-x:hidden;">
<%@ include file="/Common/include/MenuSub.jsp" %>

<div id="atm_memjoin_wrapper0">
<!--wrapper start -->
<div class="atm_graytop_ttbar" >
	<p class="atm_graytop_c1">회원가입</p>
	<a href="JoinRule.asp"><img src="/Common/images/btn_back.png" class="atm_graytop_btn_L1"/></a>
</div>
<form name="frm_sch" method="post" onSubmit="return false;">
<input type="hidden" name="btn_click" value="">
<input type="hidden" name="ch_encodeData" value="${sEncodeData}">
<input name="FlagCertNum" type="hidden" value="">
<input name="FlagNickName" type="hidden" value="">
<input name="sMobileCo" type="hidden" value="${sMobileCo}">
<input name="sAuthType" type="hidden" value="${sAuthType}">
<input name="sName" type="hidden" value="${sName}">
<input name="sBirthDate" type="hidden" value="${sBirthDate}">
<input name="sGender" type="hidden" value="${sGender}">
<input name="sCipherTime" type="hidden" value="${sCipherTime}">
<input name="sRequestNumber" type="hidden" value="${sRequestNumber}">
<input name="sResponseNumber" type="hidden" value="${sResponseNumber}">
<input name="sNationalInfo" type="hidden" value="${sNationalInfo}">

<div class="atm_memjoin_con">
	<div class="atm_memjoin_top">
		<img src="/Common/images/member_tt2.png" class="atm_memjoin_img" />
    	<p class="atm_memjoin_c1">알통의 회원이 되기 위해 간단한 정보를 입력해주세요.</p>
    </div>
	
<div class="atm_memjoin_con_el">

	<div class="atm_memjoin_opt">
    	<p class="atm_memjoin_c2">휴대폰 </p>
		<input name="Phone" type="text" class="atm_memjoin_input5" value="${sMobileNo}"  readonly >
		<!--div class="atm_memjoin_btnG">
   		  <p class="atm_memjoin_c6" onClick="javascript:phoneChk('frm_sch','PhoneCheck.asp');">휴대폰 중복가입 확인</p>
		</div-->

	<div class="atm_memjoin_opt">
    	<p class="atm_memjoin_c2">닉네임</p>
		<input name="NickName" type="text" class="atm_memjoin_input5" id="NickName" maxlength="20" onKeyUp="javascript:NickNameReset();">
		<div class="atm_memjoin_btnG">
   		  <p class="atm_memjoin_c5" onClick="javascript:NickNameChk('frm_sch','/default/nickNameCheck');">중복 확인</p>
		</div>
    </div>

	<div class="atm_memjoin_opt">
    	<p class="atm_memjoin_c2">비밀번호 (<font color="red">&nbsp;영문,숫자 포함 6자리 이상</font>&nbsp;)</p>
		<input name="Password1" type="password" class="atm_memjoin_input5" maxlength="16" />
    </div>

	<div class="atm_memjoin_opt">
    	<p class="atm_memjoin_c2">비밀번호 확인</p>
		<input name="Password2" type="password" class="atm_memjoin_input5" maxlength="16" />
    </div>
    
	<div class="atm_memjoin_opt">
    	<p class="atm_memjoin_c2">이메일</p>
		<input name="Email1" type="text" class="atm_memjoin_input3" maxlength="20" />
    	<p class="atm_memjoin_c3">@&nbsp;</p>  
			<input name="Email2" type="text" id="Email2" class="atm_memjoin_input3" maxlength="20" />
			<select id="email_domain" class="atm_memjoin_input4" onChange="$('#Email2').val($('#email_domain').val());">
				<option selected="selected" value="">직접입력</option>
				<option value="naver.com">naver.com</option>
				<option value="gmail.com">gmail.com</option>
				<option value="hanmail.net">hanmail.net</option>
				<option value="nate.com">nate.com</option>
				<option value="yahoo.com">yahoo.com</option>
			</select> 
    </div>
	<div class="atm_memjoin_opt">
		<p class="atm_memjoin_c2">추천인 입력 (선택사항)</p>
		<p class="atm_memjoin_c_R">
        		<input name="atm_celti" id="atm_celti1" type="radio" class="atm_celti_ridio" value="2" align="absmiddle" onclick="div_OnOff(this.value,'con');"><label for="atm_celti1"><img  src="/Common/images/btn_radio.png" class="atm_celti_img" id="phone-radio"/>전화번호</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="atm_celti" id="atm_celti2" type="radio" class="atm_celti_ridio" align="absmiddle"  value="1" onclick="div_OnOff(this.value,'con');"><label for="atm_celti2"><img  src="/Common/images/btn_radio_on.png" class="atm_celti_img" id="code-radio"/>추천인 코드</label>
        </p>
        <div class="atm_celti_div1">
				<input name="ChuCode1" type="text" class="atm_memjoin_input3" maxlength="4" value="${chuCode1}" onKeyUp="javascript:checkNum(this);">
				<p class="atm_memjoin_c3">-&nbsp;</p>  
				<input name="ChuCode2" type="text" class="atm_memjoin_input3" maxlength="4" value="${chuCode2}" onKeyUp="javascript:checkNum(this);">
        </div>
		<div id="con" style="display:none">
        <div class="atm_celti_div2">
				<select name="Phone1" class="atm_memjoin_input1">
            		<option value="010" selected="selected">010</option>
            		<option value="011">011</option>
            		<option value="016">016</option>
            		<option value="017">017</option>
            		<option value="019">019</option>
				</select>    	
				<input name="Phone2" type="text" class="atm_memjoin_input2" maxlength="4" onKeyUp="javascript:checkNum(this);">
				<input name="Phone3" type="text" class="atm_memjoin_input2" maxlength="4" onKeyUp="javascript:checkNum(this);">
        </div>
		</div>
    </div>

</div>
    <p class="atm_memjoin_btn" onClick="javascript:JoinChk_n('frm_sch','/default/joinSave_n');">회원등록</p>
</div>
</form>
<!--wrapper end -->
</div>
</div>
<iframe name="ifrm" width="100%" height="0" frameborder="0"></iframe>
<script>
	$(document).ready(function() {
		$("input[name=ChuCode1]").keyup(function() {
			var charLimit = $(this).attr("maxlength");
			if(this.value.length >= charLimit) {
				$("input[name=ChuCode2]").focus();
			}
		})
	});
	$
</script>
</body>
</html>