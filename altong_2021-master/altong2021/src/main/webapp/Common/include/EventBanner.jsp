<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.math.*" %>
<%@ page import="com.altong.web.logic.event.EventVO" %>  
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>     
<%
String eventURLCheck = request.getRequestURL().toString();
String bannerAllView = "on";

if(eventURLCheck.indexOf("eventList.jsp") != -1 || bannerAllView == "on") {
		
	//[추가(2018.01.10): 김현구] 이미지 파일 캐쉬문제 해결을 위해 추가
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
	response.setDateHeader("Expires", 0); // Proxies.
%>
<script type="text/javascript"
	src="/Common/include/TinySlider/script.js"></script>
<link rel="stylesheet" type="text/css"
	href="/Common/include/TinySlider/style.css?1.1" />
<!-- 베너  -->
<%
	List<EventVO> eventList = CommonUtil.getEventForHeader(request, response);

	Cookie[] cookies = request.getCookies();  

	String bannCheck = "";
	
	for(int i = 0 ; i<cookies.length; i++){
		if(cookies[i].getName().equals("altong.com_event_banner_check")) {
			bannCheck = cookies[i].getValue();
		}
	}
	if(!bannCheck.equals("1")) {
		if(eventList != null){
%>
<div id="bannerContainer">
	<div id="bannerWrapper" style="height:0;overflow:hidden" class="center">
		<div id="banner_slider">
			<div id="bannerBtn">
				<span><spring:message code="msg_0133"/></span> <input type="checkbox" id="eventBannerCheckbox">
			</div>
			<div id="wrapper_2">

				<div id="banner_images">
					<div id="slider">

						<ul>
							<li><a href="/answer/answerList?Seq=311440">
                                <img src="http://www.altong.com/UploadFile/Event/event_banner-153.png?time=" class='bannerImg' />
                            </a></li>

							<li style=""><a href="/question/event/interView?idx=85">
                                <img src="http://www.altong.com/UploadFile/Interview/interview-banner-147.png" class="bannerImg" />
                            </a></li>
                            
                            <li style=""><a href="/roulette/game">
                                <img src="/Common/images/roulette-banner.jpg" class="bannerImg" />
                            </a></li>
						</ul>

					</div>

					<ul id="pagination" class="pagination" style="display: none">
						<li onclick="slideshow.pos(0)">1</li>
						<li onclick="slideshow.pos(1)">2</li>
						<li onclick="slideshow.pos(2)">3</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
<style>
#namu{
	width:100%;
	max-width:800px;
	margin: 0 auto;
}
#namu img{
	width:100%;
}
</style>
<div id="namu">
	<img src="/pub/css/images/namu-banner.svg"/>
</div>
<%
		} // event list 유무 체크 종료
%>
<script type="text/javascript">

//<!-- [수정(2018.01.29): 김현구] 이미지 캐시 갱신이 안되는 문제로 수정 -->
var slideshow = new TINY.slider.slide('slideshow', {
    id: 'slider',
    auto: 1,
    resume: true,
    vertical: false,
    navid: 'pagination',
    activeclass: 'current',
    position: 0
});
</script>
<%
	}//"altong.com_event_banner_check end
%>
<script>
function fnGetCookie(sName) {
	var aCookie = document.cookie.split("; ");

	for (var i = 0; i < aCookie.length; i++) {
		var aCrumb = aCookie[i].split("=");
		if (sName == aCrumb[0])
			return unescape(aCrumb[1]);
	}

	return null;
}
$(window).load(function () {
	//console.log("height : "+$('.bannerImg').css('height'));
	$("namu").css('height',$('.bannerImg').css('height'));
    const $eventWrapper = $('#bannerContainer');
	const $bannerWrapper = $('#bannerWrapper');
    // [추가: 2017.12.12 - 김현구] 쿠키(Cookie)에 저장된 값 CHECK
    var sReturn = fnGetCookie('altong.com_event_banner_check');

    // [수정: 2017.12.12 - 김현구] 쿠키(Cookie) 설정에 따른 Show/Hide 제어
    if (false && sReturn == "1") {
        $eventWrapper.hide();
    }
    else {
        var tt = Math.min($(window).width(), 800);

        $('#slider li').css('width', tt);
        $('.bannerImg').css('width', tt);

        var imgHeight = $('.bannerImg').css('height');
        //if(parseInt(imgHeight, 10) < 171) { imgHeight = 171; }
        //console.log('imgHeight : ' + imgHeight); //189px
        //var wrapperHeight = $('#pagination').height();

        //$('#wrapper_2').css('height', imgHeight);
        $('#slider').css('height', imgHeight);
        $('#slider li').css('height', imgHeight);
        $('li#content').css('height', imgHeight);

        $bannerWrapper.css('overflow','visible');
        $eventWrapper.css({'display':'none','height':imgHeight}).slideDown(300);
        $('#namu').css({'height':imgHeight}).slideUp(300);

        $bannerWrapper.css('height',imgHeight);
        
	}
});

$(document).on('click', '#eventBannerCheckbox', function () {
    const $eventWrapper = $('#bannerContainer');
    if (this.checked == true) {
        fnSetCookie('altong.com_event_banner_check', '1');
        $eventWrapper.hide();
    }
})
function fnSetCookie(sName, sValue) {
    expday = new Date();
    expday.setDate(expday.getDate() + 1);
    expday.setHours(0, 0, 0); //[추가: 2018.01.03 차건환]자정에 삭제되도록 변경
    //document.cookie = sName + "=" + escape(sValue);
    document.cookie = sName + "=" + escape(sValue) + "; expires=" + expday.toGMTString() + "; path=/";
}
</script>
<%
	}//"altong.com_event_banner_check end
%>