<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.altong.web.logic.util.CookieBox" %>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<body>
<%

Cookie[] cookies = request.getCookies() ;

String lat = "";
String lon = "";
if(cookies != null) {
	for(int i = 0; i < cookies.length; i++) {
		Cookie c = cookies[i];
		
		String cName = c.getName();
		String cValue = c.getValue();
		
		if(cName.equals("lat")) lat = c.getValue();
		if(cName.equals("lon")) lon = c.getValue();
		
	}
}


//out.println("lat : " + lat);
//out.println("lon : " + lon);

//lat = "";
//lon = "";

if(lat.equals("") || lon.equals("")) {
	out.print("<script>location.replace('/alpay/user/alpayUserMain')</script>");
    return;
}
%>

<script>
if (history.pushState)
{
	history.pushState(null, document.title, location.href);
	window.addEventListener('popstate', function(event) {
		if ($('#payment_box').hasClass('show')) {
            if ($('#if_pay')[0].contentWindow.backBtn())
				history.pushState(null, document.title, location.href);
        }
		else {
			history.back();
        }
	});
}

$(document).ready(fSetUserAlpayKR);

function fSetUserAlpayKR()
{
	fAjax('ACT=UserAlpayKR');
}

function fAjax(param)
{
	if (document.xhr)
	{   
		$('#Tip').text('이전 작업이 진행중입니다.').css('display','block').stop().fadeIn(0, function(){$(this).fadeOut(3000)});
		return;
	}

	document.xhr = $.ajax({
		type : 'post',
		url: '/alpay/user/alpayUserAjax',
		data: param,
		dataType: 'json',
		success: function(r) {
			console.log(r.arr.UserAlpayKR);
			switch (r.result)
			{
				case 'UserAlpayKR':
				{
					$('#UserAlpayKR').text(r.arr.UserAlpayKR);
					break;
				}
				default:
					if (r.result) alert(r.result);
					break;
			}
		},
		error: function(r, textStatus, err){
			if (r.responseText && r.responseText.substr(0,13) == '<!--LOGOFF-->') {top.location='${MORMAL_SEND_URL}';return;}
			if (r.statusText && r.statusText == 'abort') return;
			if (!err) return;
			alert('서버와의 통신에 실패하였습니다.');

			var str = '';
			for(var key in r) str += key + '=' + r[key] + '\n';
			console.log(str);
		},
		complete: function() {document.xhr=false;}
	});
}
</script>

<div class="overlay"></div>

    <header class="non_box">
        <div>
            <p class="header_btn" id="gnb_btn"><a href="javascript:void(0);" onclick="return false;"><i class="material-icons">menu</i></a></p>
        </div>
        <div>
            <h1><a href="/alpay/user/alpayUserMain"><img src="/Common/alpay/common/img/alpay_logo.png" alt="알pay" width="100px"></a></h1>
        </div>
    </header>

<%
String sessUserLv = "";
String userLvStr = "0";
if(session.getAttribute("UserLv") != null) { 
	sessUserLv = session.getAttribute("UserLv").toString(); 
	userLvStr = CommonUtil.fn_Level(sessUserLv, request);
}
pageContext.setAttribute("userLv", userLvStr);
%>
    <div class="nav_wrapper">
        <div class="wrapper">
            <nav class="left">
                <div class="non_scroll">
                    <div class="top">
                        <a href="#" class="noti_btn top_btn"><i class="material-icons">notifications</i></a>
                        <a href="#" class="setting_btn top_btn"><i class="material-icons">settings</i></a>
                        <p class="profile_img"><a href="#none"><i class="material-icons">account_circle</i></a></p>
                        <p class="profile_txt"><span>${userLv}</span> <span>${sessionScope.UserNickName}</span>님</p>
                        <p class="usable_money">나의 알머니 <span><fmt:formatNumber type="number" maxFractionDigits="3" pattern=".0" value="${sessionScope.UserAlmoney}" /></span>알</p>
                    </div>
                    <div class="direct_altong" onClick="top.location.replace('${MORMAL_SEND_URL}')">알통 메인으로 <i class="material-icons">keyboard_arrow_right</i></div>
                </div>
                <div class="scroll_wrap">
                    <ul>
                        <li><a href="/alpay/user/sub/pay_history">사용 내역</a></li>
                        <li><a href="/alpay/user/sub/fav_list">내 관심 매장</a></li>
                        <li class="disable"><a href="javascript:void(0)">모집 가맹점</a></li>
                        <li class="disable"><a href="javascript:void(0)">이벤트</a></li>
                        <li><a href="/alpay/user/sub/exchange">현금 출금</a></li>
                    </ul>
                    <div class="bottom">
                        <div class="wrapper">
                            <p class="cs_btn"><a href="${MORMAL_SEND_URL}Default/CS/CustomerService" target="_blank">고객센터</a></p>
                            <p class="copyright">Copyright © 2017 Altong.<br>All Rights Reserved.</p>
                        </div>
                    </div>
                </div>
            </nav>
        </div>
    </div>
    <!-- 메인 페이지 -->
    <section class="main">
        <div class="intro">
            <p>안녕하세요,</p>
            <p><span>${sessionScope.UserNickName}</span>님</p>
        </div>

        <div class="money_info">
            <a href="javascript:void(0);" class="sync_btn" onclick="$('#UserAlpayKR').text(0);fAjax('ACT=UserAlpayKR');return false;"><i class="material-icons">sync</i></a>
            <p class="money_title">사용 가능 금액</p>
            <a href="${MORMAL_SEND_URL}member/account/Exchange/ExchangeAsk" class="charge_btn">충전하기</a>
            <div class="usable_money">
                <a href="sub/pay_history.asp">
                    <p class="money"><span><strong id="UserAlpayKR"><fmt:formatNumber type="number" maxFractionDigits="3" value="${sessionScope.UserAlpayKR}" /></strong></span>원</p>
                    <i class="material-icons">keyboard_arrow_right</i>            
                </a>
            </div>
        </div>
        <div class="main_btn">
            <p id="shop_btn"><a href="javascript:void(0)" onclick="location='/alpay/user/sub/near_map'"><i class="material-icons">location_searching</i> 알맹이 찾기</a></p>
            <p id="payment_btn"><a href="javascript:void(0)" onclick="$('#if_pay')[0].contentWindow.fGoStep0(1)"><i class="material-icons">payment</i> 결제하기</a></p>
        </div>
    </section>

	<div style="position:absolute;bottom:10px;width:100%;font-size:13px;color:#aaa;text-align:center">질문/답변을 통해 획득한 알머니를<br>주변 매장(알맹이)에서 바로 사용하세요.</div>

    <!-- 결제창 -->
    <div id="payment_box">
        <iframe id="if_pay" src="/alpay/user/payment" frameborder="0" width="100%" height="100%" marginheight="0" marginwidth="0"></iframe>
    </div>


</body>