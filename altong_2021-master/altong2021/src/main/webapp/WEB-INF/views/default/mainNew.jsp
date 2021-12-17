<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jasp.util.*" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<body>
<%
//[추가(2018.02.12): 김현구] 로그인 후 프로필 포토 CHECK
String LoginFlag = "N";

if(request.getAttribute("LoginFlag") != null) {
	LoginFlag = request.getAttribute("LoginFlag").toString();
}
long a = System.currentTimeMillis();
//out.println("currentTimeMillis : 1:" + (System.currentTimeMillis() - a));

if(LoginFlag.equals("Y")) {
%>
<%@ include file="/WEB-INF/views/default/profile/ProfilePhoto_Check.jsp" %>
<%} %>
<div id="atm_wrapper" style="height: 90%;">
		<%@ include file="/Common/include/MenuMain.jsp" %>
        <div id="atm_mainsearch_wrapper0">
<!--wrapper start -->
<div class="atm_mainsearch_con">
    <div class="atm_mainsearch_logowrapper" style="position: relative;">
        <img src="/Common/images/main_logo_2.png?time=" class="atm_logo_mainsearch">
<%
	//[추가(2018.02.12): 김현구] 로그인 후 프로필 포토 CHECK
	String MORMAL_SEND_URL = null;
	String DELAY_URL = null;
	boolean EndTimer = false;

	int userSeq = 0;
	
	//JSONObject global_info = (JSONObject)CommonUtil.getGlobal(request, response);

    if (global_info != null) {
    	userSeq = Integer.parseInt(global_info.get("UserSeq").toString());
%>
        <div onclick="location.href='/member/alarm/alarm';" style="padding:10px 25px;padding-top:25px;position: absolute;right:-10px;bottom:0;z-index:10;display:none">
            <div id="alarm-icon">
                <img src="/Common/images/icon_alarm_new.png?time=">
                <span class="alarmCnt"></span>
            </div>
        </div>
<%
    }
%>
    </div>
    <form name="frm_sch" method="get" action="/question/questionSearch" onSubmit="return false;">
        <div class="atm_mainsearch_inputwrapper" align="center">
                <div class="atm_mainsearch_inputwrapper2" align="center">
                    <input type="text" name="src_Text" class="atm_mainsearch_input1" placeholder="검색어를 입력해 주세요." 
                            onFocus="this.value='';return true;" 
                            onKeyDown="if (event.keyCode == 13) { if (this.form.src_Text.value) this.form.submit(); }"/>
                </div>
                <a onClick="if (document.frm_sch.src_Text.value) document.frm_sch.submit();"><img src="/Common/images/icon_search.png" class="atm_mainsearch_btn_R"/></a>
        </div>
        <div class="atm_mainsearch_btnwrapper">
            <div class="atm_mainsearch_btn">
                <p class="atm_mainsearch_btn_c1" onClick="location.href='/question/bestList'">질문</p>
                <p class="atm_mainsearch_btn_c2" onClick="location.href='/answer/questionList?Section1=0&src_Sort=DateReg&src_OrderBy=DESC'">답변</p>
            </div>
        </div>
    </form>

    <div class="other_link_btn">

<%
    if (global_info == null) {
%>
        <p><a href="javascript:alert('로그인 후 이용하시기 바랍니다.');location.href='/default/login';" target="_top">알pay<!--<sup><b><i><font color="red">β</font></i></b></sup>--></a></p>
<%
    } else {
%>
        <p><a href="/alpay/user/alpayUserMain" target="_top">알pay<!--<sup><b><i><font color="red">β</font></i></b></sup>--></a></p>
<%
    }
%>
		<!-- /alaview/list.alt -->
        <p><a href="/alaview/list">알라뷰<span style="color:red; font-weight:600; font-size:0.85em; font-style:italic;">β</span></a></p>
        <p><a class="altong_guide" href="/default/userGuide"><!--<span class="altong_new">N</span>-->알통 사용법</a></p>
        <!-- <p class="main_develop_list" style="min-width:78px;"><a href="/Question/DevelopList.jsp">지식발전소</a></p> -->
        <p><a href="/stock/ads"><!--<img src="/Univ/img/logo_hongik_univ.png" height="28">-->광고 모음</a></p>

    </div>

<%
	//알맹이 시작 - 용도 알수 없음
    if (global_info != null) {
%>
<%
        if (((global_info.get("UserSeq").equals(10010065) || global_info.get("UserSeq").equals(10010185) || global_info.get("UserSeq").equals(10000076) || global_info.get("UserSeq").equals(10000073) || global_info.get("UserSeq").equals(10000075) || global_info.get("UserSeq").equals(10000076) || global_info.get("UserSeq").equals(10000097) || global_info.get("UserSeq").equals(10000110) || global_info.get("UserSeq").equals(10000074) || global_info.get("UserSeq").equals(10010355) || global_info.get("UserSeq").equals(10000077)))) {
%>
<%
        }
%>
<%
    }
%>


</div>
<!--wrapper end -->
</div>
<%@ include file="/Common/include/MenuItem.jsp" %>
</div>


<div id="myModal" class="modal"></div>

<script>
var modal = document.getElementById('myModal');
var bodyS = document.body;
var span = document.getElementsByClassName("close")[0];

/*
span.onclick = function() {
    modal.style.display = "none";
}

closeCCheck.onclick = function() {
    modal.style.display = "none";
	noticCsetCookie(noticCname, '1', '1');
}
*/

window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}



// return [쿠키 존재여부 true/false,쿠키 value];
function CheckOpenWinCkookie() {
    cookie_value = null; // 쿠키 value;
    cookie_exist = false;
    tmp = document.cookie || '';

    if (document.cookie == "") {
        return [cookie_exist,cookie_value];
    }
    cookie_sp = tmp.split(';');
    for (i = 0 ; i < cookie_sp.length ; i++) {
        var c_sp_i = cookie_sp[i].split('=');
        
        if (noticCname == c_sp_i[0].trim()){
            cookie_exist = true;
            cookie_value = c_sp_i[1];
        }
    }
    return [cookie_exist,cookie_value];
}


function noticCsetCookie(cname, cvalue, exdays) { // 공지 쿠키 
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
    return true;
}


var noticCname = "noticWin"; // 공지 쿠키 이름 0 계속 뜨고 1면 안뜬다 
var noticCcheck = null;

/*
(function() {
    
	function _openwin_start() {
		if ( document.readyState === "complete" ) {

            noticCcheck = CheckOpenWinCkookie();
            if (noticCcheck[0] == false) {
                noticCsetCookie(noticCname, '0', '1');
				modal.style.display = "block";
            }else {
				if (noticCcheck[1] == 0 ) {
					modal.style.display = "block";
				} else {
					modal.style.display = "none";
				}
			}

		} else {
			return setTimeout(_openwin_start, 1 ); // 재귀
		}
	}
	
     _openwin_start();

})( window );
*/

function fAjax(url, param) {
	if (document.xhr) {
		$('#Tip').text('이전 작업이 진행중입니다.').css('display', 'block');
		setTimeout(function () { $('#Tip').stop().fadeOut(300) }, 3000);
		return;
	}
	//console.log('url : '+url);
	document.xhr = $.ajax({
		type: 'post',
		url: url,
		data: param,
		dataType: 'json',
		crossDomain: true,
		success: function (r) {
			//console.log('result : '+r.result);
			//return;
			switch (r.result) {
				case 'GET_ALMONEY':
					{
						fSetAlmoney(r.arr.AlmoneyAll, r.arr.Almoney, 0);
						//console.log('AlmoneyAll : '+ r.arr.AlmoneyAll);
						//console.log('Almoney :'+ r.arr.Almoney);
						
<%
    if (global_info != null) {
%>
						//console.log('r.arr.FlagDel : ' + r.arr.FlagDel);
						if (r.arr.FlagDel)
						{
							location.replace('/default/logOut');
							//return;
						}
						$('.alarmCnt').text(r.arr.AlarmCNT).parent().parent().fadeIn();
						if (r.arr.SESS)
						{
							$.cookie('SESS', r.arr.SESS, {expires:r.arr.SessExpire, path:'/'});
							$.cookie('SESC', r.arr.Ver, {expires:r.arr.SessExpire, path:'/'});
						}
<%
    }
%>
						break;
					}
				default:
					if (r.result) alert(r.result);
					break;
			}
		},
		error: function (r, textStatus, err) {
			if (r.responseText && r.responseText.substr(0, 13) == '<!--LOGOFF-->') { top.location = '<%= MORMAL_SEND_URL %>'; return; }

			//alert('서버와의 통신에 실패하였습니다.');
			var str = '';
			for (var key in r) str += key + '=' + r[key] + '\n';
			console.log(str);
		},
		complete: function () {
			document.xhr = false;
		}
	});
}

$(document).ready(function() {
	var host = '<%= DELAY_URL %>';
	if(host == 'null') {
		host = '';
	}
	
	fAjax(host + '/common/getAlmoneyAlarm', 'SESS=' + getCookie('SESS'));
	$('<img src="'+host+'/common/setStatus" style="display:none">');
});

</script>
    <style>
        #event_popup_overlay {background:rgba(0, 0, 0, 0.68);width:100%;height:100%;position:fixed;top:0;left:0;display:none;z-index:11;}
        #event_popup_wrap {position: fixed; top:0;left:0;bottom:0;right:0;width:90%; margin:auto;z-index:12;display: none;height:270px;}
        #event_popup_wrap .event_popup {background:#fff;position:relative;border-radius:20px;overflow: hidden; margin:auto; max-width:350px;box-shadow: 0px 1px 11px #313030bf;text-align: center;cursor: pointer;}
        #event_popup_wrap .event_popup .popup_header {background:#db3b3b; color:#fff;position: relative;}
        #event_popup_wrap .event_popup .popup_header h3 {margin:0;text-align: left;padding:17px 0 14px 20px;font-size:15px;letter-spacing: -1px;}

        #event_popup_wrap .event_popup .popup_wrap {padding:0px 30px;padding-bottom:80px;cursor:pointer;}

        #event_popup_wrap .event_popup .popup_content {font-size:15px;}
        #event_popup_wrap .event_popup .popup_content li {text-align:left;font-size:15px;padding-bottom:15px;border-bottom:1px solid #eee;margin-bottom:15px;line-height: 22px;font-weight: bold;letter-spacing: -0.5px;}
        #event_popup_wrap .event_popup .popup_content li .content_number {display:inline-block;width:50%;font-size:28px;font-weight:bold;line-height: 38px;color:#db3b3b;}
        #event_popup_wrap .event_popup .popup_content li .content_number + span {display:inline-block;width:48%;font-size:14px;line-height: 20px; color:#db3b3b;letter-spacing: -0.5px;text-align: right;vertical-align: super;font-weight: normal;}

        #event_popup_wrap .event_popup .popup_content .one_content {font-size: 18px;padding:24px 5px;line-height: 30px;font-weight: bold;}
        #event_popup_wrap .event_popup .popup_content .one_btn a {color:#fff;background: #db3b3b; padding:10px 30px; border-radius: 20px; letter-spacing: -0.5px;}

        #event_popup_wrap .event_popup .popup_bottom {background:#e2e2e2; position: absolute; bottom:0; width:100%;overflow: auto; cursor:pointer;}
    </style>
    

    <div id="event_popup_overlay"></div>
    <div id="event_popup_wrap">
        <div class="event_popup">
            <div class="popup_header">
				<div style="position:absolute;color:rgba(0,0,0,0.01);word-break:break-all">-<span id="checkSize1"></span>---------------------------------------------------<span id="checkSize2"></span>-</div>
                <h3>공지사항</h3>
            </div>

			<!-- 공지 하나 버전 -->
            <div class="popup_content" onclick="location.href='/default/cs/notice/noticeView?Seq=2295'">
                <div class="popup_wrap">
                    <p class="one_content">
                        신주발행 유상증자 안내
                    </p>
                    <p class="one_btn">
                        <a href="#none">바로가기 ></a>
                    </p>
                </div>
            </div>
            
            <!-- 공지 여러개 버전 -->
			<!--
            <div class="popup_content" style="height:235px;">
                <div style="padding:0px 30px;padding-top:10px;cursor:pointer;" onclick="location.href='/default/cs/notice/noticeView?Seq=2212'">
                    <li>
                        <span class="content_number"">1</span>
                        <span>바로가기 ></span>
                        알의 가치 조정 & ANSWER 연장 시행
                    </li>
                </div>
                <div style="padding:0px 30px;cursor:pointer;" onclick="location.href='/default/cs/notice/noticeView?Seq=2206'">
                    <li style="border-bottom:0;">
                        <spann class="content_number">2</spann>
                        <span>바로가기 ></span>
                        승천 및 출금 제도 등의 변경 사항
                    </li>
                </div>
            </div>-->

			<div class="popup_bottom">
                <p onclick="fPopClose(true);" style="padding:14px 0; text-align: center; width:54%;box-sizing: border-box;float:left;background:#f1f1f1;letter-spacing: -0.5px;"><i class="material-icons" style="line-height:0; position:relative; top:6px; margin-right:3px;font-size:22px;">check_circle_outline</i>하루동안 보지 않기</p>
                <p onclick="fPopClose(false)" style="padding:14px 0; text-align: center; width:46%;box-sizing: border-box;float:right;">닫기</p>
            </div>
        </div>
    </div>

	<script>
	function fPopClose(v)
	{
		if (v) setCookie('POP20190425', 1, 1);
		$('#event_popup_wrap').hide();
		$('#event_popup_overlay').hide();
    }
	
    if (!getCookie('POP20190425'))
	{
		$('#event_popup_wrap').slideDown();
        $('#event_popup_overlay').fadeIn('fast');
        
        var ua = window.navigator.userAgent;

        if (ua.indexOf('MSIE ') > 0 || ua.indexOf('Trident/') > 0 || ua.indexOf('Edge/') > 0) {
            // IE 10 , IE 11, Edge (IE 12+)
        } else {
            setTimeout(function(){
                if($("#checkSize1").offset().top != $("#checkSize2").offset().top){
                    fPopClose(false);
                }
            });
        }
    }
	
	//setCookie('POP20190425', '', 0);
	</script>

</body>
</html>
<%
    EndTimer = true;
%>