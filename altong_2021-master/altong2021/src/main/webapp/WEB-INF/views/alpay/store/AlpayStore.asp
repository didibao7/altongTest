<!-- #include virtual = "/Common/Global.asp" -->
<!-- #include virtual = "/Common/Function/Fnc_CallPHP.asp" -->
<!-- #include virtual = "/Common/Function/Fnc_Common.asp" -->
<%


if Request.Form("ACT") <> "" then
	Fn_CallPHP(6000)
end if


%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>알pay (알맹이)</title>
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/alpay/common/css/style.css?v=2.1">
<link rel="stylesheet" type="text/css" href="/alpay/store/css/store.css?v=2">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="apple-touch-icon" sizes="57x57" href="/alpay/store/favicon/apple-icon-57x57.png">
<link rel="apple-touch-icon" sizes="60x60" href="/alpay/store/favicon/apple-icon-60x60.png">
<link rel="apple-touch-icon" sizes="72x72" href="/alpay/store/favicon/apple-icon-72x72.png">
<link rel="apple-touch-icon" sizes="76x76" href="/alpay/store/favicon/apple-icon-76x76.png">
<link rel="apple-touch-icon" sizes="114x114" href="/alpay/store/favicon/apple-icon-114x114.png">
<link rel="apple-touch-icon" sizes="120x120" href="/alpay/store/favicon/apple-icon-120x120.png">
<link rel="apple-touch-icon" sizes="144x144" href="/alpay/store/favicon/apple-icon-144x144.png">
<link rel="apple-touch-icon" sizes="152x152" href="/alpay/store/favicon/apple-icon-152x152.png">
<link rel="apple-touch-icon" sizes="180x180" href="/alpay/store/favicon/apple-icon-180x180.png">
<link rel="icon" type="image/png" sizes="192x192" href="/alpay/store/favicon/android-icon-192x192.png">
<link rel="icon" type="image/png" sizes="32x32" href="/alpay/store/favicon/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="96x96" href="/alpay/store/favicon/favicon-96x96.png">
<link rel="icon" type="image/png" sizes="16x16" href="/alpay/store/favicon/favicon-16x16.png">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="/alpay/common/js/common.js"></script>
<script>
$(document).ready(function(){
	fAjax('ACT=store_money');
});

function fAjax(param)
{
	if (document.xhr)
	{   
		$('#Tip').text('이전 검색이 진행중입니다.').css('display','block');
		setTimeout(function(){$('#Tip').stop().fadeOut(300)}, 3000);
		return;
	}

	document.xhr = $.ajax({
		type : 'post',
		url: '<%=Request.ServerVariables("SCRIPT_NAME") %>',
		data: param,
		dataType: 'json',
		success: function(r) {
			switch (r.result)
			{
				case 'store_money':
				{
					$('.store_money').text(r.arr.store_money);
					$('.percentage').text(r.arr.percentage);
					break;
				}
				default:
					if (r.result) alert(r.result);
					break;
			}
		},
		error: function(r, textStatus, err){
			if (r.responseText && r.responseText.substr(0,13) == '<!--LOGOFF-->') {top.location='<%=MORMAL_SEND_URL%>';return;}

			alert('서버와의 통신에 실패하였습니다.');
			var str = '';
			for(var key in r) str += key + '=' + r[key] + '\n';
			console.log(str);
		},
		complete: function() {document.xhr=false;}
	});
}
</script>
</head>
<body>
<div id="Tip" style="position:absolute;top:0;width:100%;padding:10px;z-index:99;text-align:center;background-color:#fdd;display:none"></div>
<div class="overlay"></div>

<header class="non_box">
	<div>
		<p class="header_btn" id="gnb_btn"><a href="javascript:void(0);" onclick="return false;"><i class="material-icons">menu</i></a></p>
	</div>
	<div>
		<h1><img src="/alpay/common/img/alpay_logo.png" alt="알pay" width="100px"></h1>
	</div>
</header>

<div class="nav_wrapper">
	<div class="wrapper">
		<nav class="left">
			<div class="non_scroll">
				<div class="top">
					<a href="#" class="noti_btn top_btn"><i class="material-icons">notifications</i></a>
					<a href="#" class="setting_btn top_btn"><i class="material-icons">settings</i></a>
					<p class="profile_img"><a href="#none"><i class="material-icons">account_circle</i></a></p>
					<p class="profile_txt"><span>가맹점주</span> <span><%=Session("UserNickName")%></span>님</p>
					<p class="profile_code">가맹점 코드 : <span><%=Session("AlmaengCode")%></span></p>
					<p class="usable_money">남은 선입금 금액 <span class="store_money">0</span>원</p>
				</div>
				<div class="direct_altong"><a href="http://www.altong.com" target="_top">알통 메인으로 <i class="material-icons">keyboard_arrow_right</i></a></div>
			</div>
			<div class="scroll_wrap">
				<ul>
					<li><a href="/alpay/store/sub/order_list.asp">주문확인</a></li>
					<li><a href="/alpay/store/sub/sale_list.alt">매출확인</a></li>
					<li class="disable"><a href="javascirpt:void(0)">이벤트</a></li>
					<li><a href="/alpay/store/sub/member_set.asp">직원관리</a></li>
				</ul>
				<div class="bottom">
					<div class="wrapper">
						<p class="cs_btn"><a href="http://www.altong.com/Default/CS/CustomerService.asp" target="_blank">고객센터</a></p>
						<p class="copyright">Copyright © 2017 Altong.<br>All Rights Reserved.</p>
					</div>
				</div>
			</div>
		</nav>
	</div>
</div>
<section class="main">
	<div class="intro">
		<p>안녕하세요,</p>
		<p><span><%=Session("UserNickName")%></span> <% if Session("Almaeng") = 1 then response.write "가맹점주" %>님</p>
		<!--<div class="sub_box"">가맹점 코드 : <%=Session("AlmaengCode")%></div>-->
	</div>
	<div class="money_info">
		<a href="javascript:void(0);" class="sync_btn" onclick="fAjax('ACT=store_money')"><i class="material-icons">sync</i></a>
		<p class="money_title">남은 선입금 금액</p>
		<div class="usable_money">
			<a href="sub/sale_list.alt">
				<p class="money"><span><strong class="store_money">0</strong></span>원</p>
				<p><span class="percentage">0%</span> 남았습니다.</p>
			</a>
		</div>
	</div>
	<div class="new_message">
		<a href="sub/order_list.asp">
			<i class="material-icons">notifications_active</i>
			주문확인 바로가기
		</a>
	</div>
</section>

</body>
</html>