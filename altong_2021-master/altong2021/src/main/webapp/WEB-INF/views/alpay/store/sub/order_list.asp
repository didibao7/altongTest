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
</head>

<script>
	$(document).ready(function () {
		fAjax('ACT=ORDER_NEW');
	});

	function fRequest(requestList, requestType) {
		var pay_seq = $(requestList).parents('li').attr('pay_seq');
		if (requestType == 'accept') {
			$(requestList).parents('li').addClass('request');
			fAjax('ACT=ORDER_ACCEPT&pay_seq=' + pay_seq);

		} else if (requestType == 'refuse') {
			if (confirm('해당 주문요청을 삭제하시겠습니까?')) {
				fAjax('ACT=ORDER_REFUSE&pay_seq=' + pay_seq);
				$(requestList).parents('li').addClass('request').addClass('del');
			}
		}
	}

	function fAddComma(v) {
		v = String(v);
		return v.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
	}

	function searchDate(type) {
		if (type == 'inputDate') {
			var input_1 = $('#datePicker1').val();
			var input_2 = $('#datePicker2').val();
			fAjax('ACT=ORDER_HISTORY&S_date='+ type +'&S_input1='+ input_1 +'&S_input2='+ input_2);
		} else {
			fAjax('ACT=ORDER_HISTORY&S_date=' + type);		
		}
	}

	function fAjax(param) {
		if (document.xhr) {
			$('#Tip').text('이전 검색이 진행중입니다.').css('display', 'block');
			setTimeout(function () { $('#Tip').stop().fadeOut(300) }, 3000);
			return;
		}

		document.xhr = $.ajax({
			type: 'post',
			url: '<%=Request.ServerVariables("SCRIPT_NAME") %>',
			data: param,
			dataType: 'json',
			success: function (r) {
				switch (r.result) {
					case 'ORDER_NEW':
						{
							$('.order_list .order_nothing').hide();
							$('.rowDynamicOrderR').remove();
							if (r.arr.length > 0) {

								for (var i = 0; i < r.arr.length; i++) {
									var orderList = $('#tab_orderRequest > ul > li:first-child').clone();
									orderList.addClass('rowDynamicOrderR').attr('pay_seq', r.arr[i].pay_seq);
									orderList.find('.regdate').text(r.arr[i].regdate);
									orderList.find('.pay_seq').text(r.arr[i].pay_seq);
									orderList.find('.user_nickname').text(r.arr[i].user_nickname);
									orderList.find('.alpay_amount').text(fAddComma(r.arr[i].alpay_amount));
									orderList.css('display', 'block').appendTo($('#tab_orderRequest > ul'));
								}
							} else {
								// 주문확인->신규요청이 없는 경우
								$('.order_list .order_nothing').show();
								$('.order_list .order_nothing .order_nothing_msg').text('신규 요청이 없습니다.').attr('onclick','location.reload();');
								console.log('신규요청이 없습니다');
							}
							break;
						}
					case 'ORDER_HISTORY':
						{
							$('.order_list .order_nothing').hide();
							$('.order_list').animate({ scrollTop: 0 }, 500);
							$('.rowDynamicOrderL').remove();
							
							if (r.arr.length > 0){

								for (var i = 0; i < r.arr.length; i++) {
									var orderList = $('#tab_orderList > ul > li:first-child').clone();
									orderList.addClass('rowDynamicOrderL').attr('pay_seq', r.arr[i].pay_seq);
									orderList.find('.regdate').text(r.arr[i].regdate);
									orderList.find('.pay_seq').text(r.arr[i].pay_seq);
									orderList.find('.user_nickname').text(r.arr[i].user_nickname);
									orderList.find('.alpay_amount').text(fAddComma(-1 * parseInt(r.arr[i].alpay_amount),10));
									orderList.css('display', 'block').appendTo($('#tab_orderList > ul'));
								}
							} else {
								$('.order_list .order_nothing').show();
								$('.order_list .order_nothing .order_nothing_msg').text('승인 내역이 없습니다.').attr('onclick',"fAjax('ACT=ORDER_HISTORY&S_date=weeks');");
								console.log('승인내역이 없습니다');
							}
							break;
						}
					case 'ORDER_ACCEPT':
						{
							$('#tab_orderRequest').find('li.request').addClass('accept');
							// 페이지 새로고침 시 승인완료로 뜨는 box 다 삭제
							break;
						}
					case 'ORDER_REFUSE':
						{
							$('#tab_orderRequest').find('li.request.del').slideUp();
							location.reload();
							break;
						}
					default:
						if (r.result) alert(r.result);
						break;
				}
			},
			error: function (r, textStatus, err) {
				if (r.responseText && r.responseText.substr(0, 13) == '<!--LOGOFF-->') { top.location = '<%=MORMAL_SEND_URL%>'; return; }

				alert('서버와의 통신에 실패하였습니다.');
				var str = '';
				for (var key in r) str += key + '=' + r[key] + '\n';
				console.log(str);
			},
			complete: function () { document.xhr = false; }
		});
	}
</script>

<body>
	<header>
		<div>
			<p><a href="/alpay/store/AlpayStore.asp"><i class="material-icons">arrow_back</i></a></p>
		</div>
		<div>
			<h3>주문 확인</h3>
		</div>
	</header>
	
	<section class="order_list">
		<div class="non_scroll_tab">
			<ul class="tabs">
				<li class="tab" data-tab="tab_orderRequest" onclick="fAjax('ACT=ORDER_NEW');"><a href="javascript:void(0);">신규 요청</a></li>
				<li class="tab slide" data-tab="tab_orderList" onclick="fAjax('ACT=ORDER_HISTORY&S_date=weeks');"><a href="javascript:void(0);">승인 내역</a></li>
			</ul>
		</div>
		<div class="scroll_wrap">
			<div id="tab_orderRequest" class="tab_content">
				<ul>
					<li style="display:none;">
						<table>
							<tbody>
								<tr>
									<td class="regdate">2018.08.23 13:05</td>
									<td class="pay_seq">결제코드</td>
								</tr>
								<tr class="orderR_content">
									<td colspan="2">'<span class="user_nickname">홍길동</span>'님 <span class="alpay_amount">0</span>원<p class="accept_box">승인 완료</p></td>
								</tr>
								<tr class="orderR_btn">
									<td><button type="button" onclick="fRequest(this, 'accept');"><i class="material-icons">check</i></button></td>
									<td><button type="button" onclick="fRequest(this, 'refuse');"><i class="material-icons">close</i></button></td>
								</tr>
							</tbody>
						</table>
					</li>
				</ul>
			</div>
			<div id="tab_orderList" class="tab_content">
				<div class="tab_dateSearch">
					<form action="">
						<button type="button" class="active" onclick="dateSearchBtn(this);searchDate('weeks');">1주일</button>
						<button type="button" onclick="dateSearchBtn(this);searchDate('month');">1개월</button>
						<button type="button" onclick="dateSearchBtn(this);searchDate('month_6');">6개월</button>
						<button type="button" class="date_input" onclick="dateSearchBtn(this);$('.order_list').animate({ scrollTop: 0 }, 500)">조건검색</button>
					</form>
				</div>
				<div class="dateSearch_input">
					<form action="">
						<p><input type="date" id="datePicker1" min="2000-01-01"></p>
						<span>~</span>
						<p><input type="date" id="datePicker2" min="2000-01-01"></p>
						<button type="button" onclick="searchDate('inputDate');"><i class="material-icons">search</i></button>
					</form>
				</div>
				<script>
					document.getElementById('datePicker1').valueAsDate = new Date();
					document.getElementById('datePicker2').valueAsDate = new Date();

					var today = new Date();
					var dd = today.getDate();
						var mm = today.getMonth() + 1; //January is 0!
						var yyyy = today.getFullYear();
						if (dd < 10) {
							dd = '0' + dd
						}
						if (mm < 10) {
							mm = '0' + mm
						}

						today = yyyy + '-' + mm + '-' + dd;
					document.getElementById('datePicker1').setAttribute("max", today);
					document.getElementById('datePicker2').setAttribute("max", today);

				</script>
				<ul>
					<li style="display:none;">
						<table>
							<tbody>
								<tr>
									<td class="regdate">2018.08.23 13:05</td>
									<td class="pay_seq">결제코드</td>
								</tr>
								<tr class="orderR_content">
									<td colspan="2">'<span class="user_nickname">홍길동</span>'님 <span class="alpay_amount">0</span>원</td>
								</tr>
							</tbody>
						</table>
					</li>
				</ul>
			</div>
			<div class="order_nothing">
				<p class="order_refresh" onclick="location.reload();"><i class="material-icons">cached</i></p>
				<p class="order_nothing_msg">신규 요청이 없습니다.</p>
			</div>
		</div>
	</section>


</body>

</html>