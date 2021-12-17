<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="com.altong.web.logic.util.CommonUtil"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp"%>
<head>
<c:if test="${ userSeq ne 0 }">
	<link rel="stylesheet" href="/pub/css/main_login.css?ver=1.7">
	<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=2.0">
</c:if>
<c:if test="${ userSeq eq 0 }">
	<link rel="stylesheet" href="/pub/css/main_logout.css?ver=1.7">
	<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=2.0">
</c:if>
<link rel="stylesheet" href="/pub/default/main/quiz.css?ver=1.1">

<link rel="stylesheet" href="/pub/css/languages_common.css?ver=1.4">
<link rel="stylesheet" href="/pub/css/languages_mediaQuery.css?ver=1.4">

<link rel="stylesheet" href="/pub/default/main/quiz.css?ver=1.1">
<link rel="stylesheet" href="/pub/css/main_lang_select.css">
</head>
<body>
<header>
	<div class="center">
		<div class="main_header">
			<a href="javascript:void(0)" id="menu_icon">
				<div class="icon_hamburger">
					<i></i> <i></i> <i></i> <span></span>
				</div>
			</a>
			<div class="header_almoney_all" onclick="javascript:location.href='/answer/rankMember?FlagOption=Money'">
				<h4 class="header_almoney_el1"><spring:message code="msg_0638"/></h4>
				<p class="header_almoney_el2">
					<span id="domAlmoneyAll">0</span> <span
						class="header_almoney_el3"><spring:message code="msg_0136"/></span>
				</p>
			</div>
			<div class="header_almoney_my" onclick="javascript:location.href='/member/bank/index'">
				<h4 class="header_almoney_el1"><spring:message code="msg_0639"/></h4>
				<p class="header_almoney_el2">
					<span id="domAlmoneyMy">0</span> <span class="header_almoney_el3"><spring:message code="msg_0136"/></span>
				</p>
			</div>
			<nav class="header_join">
				<ul>
					<li><a href="/default/login"><spring:message code="msg_0169"/></a></li>
					<li><a href="/default/joinRule"><spring:message code="msg_0170"/></a></li>
				</ul>
			</nav>
		</div>
	</div>
</header>
<div id="main_wrapper">
	<div class="center">
		<div class="mainsearch_container">
			<figure class="main_logo">
				<img src="/Common/src/default/main/images/main_logo_3.png" alt="main_logo" id="mainLogo">
				<!-- <figcaption><spring:message code="msg_0643"/></figcaption>-->
				<!-- 다국어 언어별 사이트 이동 메뉴 시작-->
                <div id="acronym">${mainLang}</div>
                <div id="lang_list">
                    <a href="/default/main?s_lang=ko_KR"><span>KO</span> 한글</a>
                    <a href="/default/main?s_lang=en_US"><span>EN</span> English</a>
                    <a href="/default/main?s_lang=ja_JP"><span>JA</span> 日本語</a>
                    <a href="/default/main?s_lang=zh_CN"><span>ZH</span> 中文</a>
                </div>
                <script type="text/javascript">
                    const mainLogo = document.getElementById("mainLogo");
                    const langButton = document.getElementById("acronym");
                    const langList = document.getElementById("lang_list");
                    const bodyClick = document.getElementById("main_wrapper");
                    mainLogo.onclick = (e) => {
                        if (langList.style.height === "124px") {
                            langList.style.height = "0";
                            langList.style.opacity = "0";
                            langList.style.width = "0";
                        } else {
                            langList.style.height = "124px";
                            langList.style.opacity = "1";
                            langList.style.width = "100px";
                        }
                        e.stopPropagation();
                    }
                    langButton.onclick = (e) => {
                        if (langList.style.height === "124px") {
                            langList.style.height = "0";
                            langList.style.opacity = "0";
                            langList.style.width = "0";
                        } else {
                            langList.style.height = "124px";
                            langList.style.opacity = "1";
                            langList.style.width = "100px";
                        }
                        e.stopPropagation();
                    }
                    langList.onclick = (e) => {
                        e.stopPropagation();
                    }
                    bodyClick.onclick = () => {
                        langList.style.height = "0";
                        langList.style.opacity = "0";
                        langList.style.width = "0";
                    }
                </script>
                <!-- 끝 -->
			</figure>
			<form action="/question/questionSearch" method="" id='frm'>
				<div class="mainsearch_inputwrapper" onblur="blurFocus();" >
					<input type="text"name="src_Text"
						placeholder='<spring:message code="msg_0640"/>'
						onkeydown="if (event.keyCode == 13) { if (this.form.src_Text.value) this.form.submit(); }">
					<a href="#">
						<div onClick="if ($('.mainsearch_inputwrapper').find('input[name=src_Text]').val()) document.getElementById('frm').submit()">
							<img src="/Common/src/default/main/images/icon_search.png" alt="main_search">
						</div>
					</a>
				</div>
				<div class="mainsearch_btnwrapper">
					<ul class="mainsearch_btn">
						<li>
							<span onclick="location.href='/question/questionWrite'"><img src="/pub/default/main/images/que_icon.svg" alt="question"></span>
							<span><spring:message code="msg_0641"/></span>
						</li>
						<li>
							<span onclick="location.href='/answer/questionList?Section1=0&src_Sort=DateReg&src_OrderBy=DESC'"><img src="/pub/default/main/images/list_icon.svg"alt="question"></span>
							<span><spring:message code="msg_0642"/></span>
						</li>
					</ul>
				</div>
			</form>
			<ul class="other_link_btn">
				<!-- <li><a href="#">알pay</a></li> -->
				<!-- <li><a href="/alaview/list">알라뷰<em>β</em></a></li> -->
				<li><a href="/default/userGuide?view=1"><spring:message code="msg_0645"/></a></li>
				<li><a href="/default/userGuide?view=2"><spring:message code="msg_0184"/></a></li>
				<li><a href="/default/cs/notice/notice?Page=1"><spring:message code="msg_0185"/></a></li>
				<li><a href="/roulette/game"><spring:message code="msg_0644"/></a></li>
			</ul>
			<div id="quizView" style="display:none;">
				<div class="qiuz_logo">
					<ul>
						<li>
							<span>LIVE</span>
						</li>
						<li>
							QUIZ
						</li>
						<li>
							SURVIV<b>AL</b>
						</li>
					</ul>
				</div>
				<div class="quizTime_count">
					<h6>실시간 퀴즈게임 남은시간</h6>
					<div class="quizTime_el">
						<div id="counter0">5일</div>
						<div id="counter1">2시간</div>
						<div id="counter2">35분</div>
						<div id="counter3">33초</div>
						<c:if test="${ userSeq eq 0 }">
							<a href="javascript:void(0);" onclick="javascript:alert('로그인후 참여 가능!!!');">퀴즈 게임 참여</a>
						</c:if>
						<c:if test="${ userSeq ne 0 }">
							<a href="javascript:void(0);" onclick="javascript:reqQuizGame();">퀴즈 게임 참여</a>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%
String LoginFlag = "N";

if (request.getAttribute("LoginFlag") != null) {
	LoginFlag = request.getAttribute("LoginFlag").toString();
}
//System.out.println("LoginFlag : " + LoginFlag);
if (LoginFlag.equals("Y")) {
%>
	<c:if test="${ userSeq ne 0 }">
		<%@ include file="/Common/include/profilePhotoCheck.jsp" %>
	</c:if>
<%
}
%>
<script src="/pub/default/main/main.js?ver=1.3"></script>


    <style>
        #event_popup_overlay {background:rgba(0, 0, 0, 0.68);width:100%;height:100%;position:fixed;top:0;left:0;display:none;z-index:11;}
        #event_popup_wrap {position: fixed; top:0;left:0;bottom:0;right:0;width:90%; margin:auto;z-index:12;display: none;height:400px;}/*270px*/
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
				<div style="position:absolute;color:rgba(0,0,0,0.01);word-break:break-all">-<span id="checkSize1"></span><span id="checkSize2"></span>-</div>
                <h3>중요 공지사항</h3>
            </div>

			<!-- 공지 하나 버전
            <div class="popup_content" onclick="location.href='/default/cs/notice/noticeView?Seq=2295'">
                <div class="popup_wrap">
                    <p class="one_content">
                        신주발행 유상증자 안내
                    </p>
                    <p class="one_btn">
                        <a href="#none">바로가기 ></a>
                    </p>
                </div>
            </div> -->
            <div class="popup_content" style="height:340px;"><!-- 235px -->
                <div style="padding:0px 30px;padding-top:10px;cursor:pointer;" onclick="location.href='http://www.altong.com/default/cs/notice/noticeView?Seq=3426&Page=1'">
                    <li>
                        <span class="content_number"">1</span>
                        <span>바로가기 ></span>
                        광고 단가 인상에 관한 안내
                    </li>
                </div>
                <div style="padding:0px 30px;cursor:pointer;" onclick="location.href='http://www.altong.com/default/cs/notice/noticeView?Seq=3423&Page=1'">
                    <li style="border-bottom:0;">
                        <spann class="content_number">2</spann>
                        <span>바로가기 ></span>
                        룰렛 이용권 획득 요건 조정 안내
                    </li>
                </div>
                <div style="padding:0px 30px;cursor:pointer;" onclick="location.href='http://www.altong.com/default/cs/notice/noticeView?Seq=3422&Page=1'">
                    <li style="border-bottom:0;">
                        <spann class="content_number">3</spann>
                        <span>바로가기 ></span>
                        영어, 일본어, 중국어 서비스 안내
                    </li>
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
	function noticeGetCookie(name)
    {
            var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
            return value? value[2] : null;
    }

	function noticCsetCookie(cname, cvalue, exdays) { // 공지 쿠키 
	    var d = new Date();
	    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
	    var expires = "expires="+d.toUTCString();
	    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
	    return true;
	}
	
	function fPopClose(v)
	{
		if (v) noticCsetCookie('POP20210526', 1, 1);
		$('#event_popup_wrap').hide();
		$('#event_popup_overlay').hide();
    }
	
    if (!noticeGetCookie('POP20210526'))
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


<div id="footer_languages">
    <p><a href="/default/main?s_lang=ko_KR">한글</a>/<a href="/default/main?s_lang=en_US">EN</a>/<a href="/default/main?s_lang=ja_JP">日本語</a>/<a href="/default/main?s_lang=zh_CN">中文</a></p>
</div>
</body>