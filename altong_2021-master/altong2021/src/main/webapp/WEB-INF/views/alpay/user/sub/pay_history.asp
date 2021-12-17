<!-- #include virtual = "/Common/Global.asp" -->
<!-- #include virtual = "/Common/Function/Fnc_CallPHP.asp" -->
<!-- #include virtual = "/Common/Function/Fnc_Common.asp" -->
<%


if Request.Form("ACT") <> "" then
	Fn_CallPHP(6000)
end if


if Request.Cookies("lat") = "" or Request.Cookies("lon") = "" then
	response.write "<script>location.replace('/alpay/user/AlpayUserMain.asp')</script>"
	response.end
end if
%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>알pay</title>
<link rel="apple-touch-icon" sizes="57x57" href="/alpay/user/favicon/apple-icon-57x57.png">
<link rel="apple-touch-icon" sizes="60x60" href="/alpay/user/favicon/apple-icon-60x60.png">
<link rel="apple-touch-icon" sizes="72x72" href="/alpay/user/favicon/apple-icon-72x72.png">
<link rel="apple-touch-icon" sizes="76x76" href="/alpay/user/favicon/apple-icon-76x76.png">
<link rel="apple-touch-icon" sizes="114x114" href="/alpay/user/favicon/apple-icon-114x114.png">
<link rel="apple-touch-icon" sizes="120x120" href="/alpay/user/favicon/apple-icon-120x120.png">
<link rel="apple-touch-icon" sizes="144x144" href="/alpay/user/favicon/apple-icon-144x144.png">
<link rel="apple-touch-icon" sizes="152x152" href="/alpay/user/favicon/apple-icon-152x152.png">
<link rel="apple-touch-icon" sizes="180x180" href="/alpay/user/favicon/apple-icon-180x180.png">
<link rel="icon" type="image/png" sizes="192x192" href="/alpay/user/favicon/android-icon-192x192.png">
<link rel="icon" type="image/png" sizes="32x32" href="/alpay/user/favicon/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="96x96" href="/alpay/user/favicon/favicon-96x96.png">
<link rel="icon" type="image/png" sizes="16x16" href="/alpay/user/favicon/favicon-16x16.png">
<link rel="stylesheet" type="text/css" href="/alpay/common/css/style.css?v=2.2">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="/alpay/common/js/common.js"></script>
<script>
function fAjax(param) {
	if (document.xhr) {
		$('#Tip').text('이전 작업이 진행중입니다.').css('display', 'block');
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
				case 'PAY_HISTORY':
					{
						document.r = r;
						fMakeRow();
						break;
					}
				default:
					if (r.result) alert(r.result);
					break;
			}
		},
		error: function(r, textStatus, err){
			if (r.responseText && r.responseText.substr(0,13) == '<!--LOGOFF-->') {top.location='<%=MORMAL_SEND_URL%>';return;}
			if (r.statusText && r.statusText == 'abort') return;
			if (!err) return;
			alert('서버와의 통신에 실패하였습니다.');

			var str = '';
			for (var key in r) str += key + '=' + r[key] + '\n';
			console.log(str);
		},
		complete: function () { document.xhr = false; }
	});
}

function fMakeRow()
{
	if (!document.isShow)
	{
		document.isShow = true;
		return;
	}

	document.pg = parseInt(document.r.arr.pg, 10);

	if (document.r.arr.rows.length)
	{
		if (!document.pg) $('tr.rowDynamicPay').remove();
		for (var i = 0; i < document.r.arr.rows.length; i++)
		{
			var paylist = $('table.scroll_table tbody tr:first').clone();

			paylist.addClass('rowDynamicPay').empty();                                    
			paylist.append('<td>'+ document.r.arr.rows[i].condate +'</td>')
				.append('<td>' + document.r.arr.rows[i].alpay_text +'</td>')
				.append('<td>' + fAddComma(document.r.arr.rows[i].alpay_amount) + '원</td>')
				.append('<td>' + fAddComma(document.r.arr.rows[i].alpay_balance) + '원</td>');
			paylist.css('display', 'table');

			paylist.appendTo($('table.scroll_table tbody'));
		}

		$('.pay_history').scroll(function() {
			if($('.pay_history').scrollBottom() < 10) fScrolling(1000);
		});
	}
	else
		$('.pay_history').unbind("scroll");

	$('#divProg').slideUp();
	document.isScrolling = false;
	document.isShow = false;
	document.xhr = false;
}

$.fn.scrollBottom = function() { 
    return $('.scroll_table').height() - this.scrollTop() - this.height();
};

function fScrolling(delay)
{
	if (!document.xhr && !document.isScrolling) {
		document.isScrolling = true;

		$('#divProg').slideDown(300);
		setTimeout(fMakeRow, delay);

		fAjax('ACT=PAY_HISTORY&pg=' + (document.pg + 1) + '&H_sort=' + $('#history_sort').val());
	}
}

function fAddComma(v) {
	v = String(parseInt(0 + v));
	return (v == 0) ? 0 : v.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}

$(document).ready(function () {
	document.pg = -1;
	fScrolling(0);

	$('#history_sort').on('change',function(){
		document.pg = -1;
		fScrolling(0);
	});

});
</script>
</head>

<body>
<div id="Tip" style="position:absolute;top:0;width:100%;padding:10px;z-index:99;text-align:center;background-color:#fdd;display:none"></div>
<header>
	<div>
		<p><a href="/alpay/user/AlpayUser.asp"><i class="material-icons">arrow_back</i></a></p>
	</div>
	<div>
		<h3>사용 내역</h3>
	</div>
</header>

<section class="pay_history">
	<div class="non_scroll">
		<form onSubmit="return false">
		<select name="" id="history_sort">
			<option value="new">최신순</option>
			<option value="old">오래된순</option>
		</select>
		</form>

		<table>
			<thead>
				<tr>
					<th>날짜</th>
					<th>내용</th>
					<th>거래액</th>
					<th>누계</th>
				</tr>
			</thead>
		</table>
	</div>

	<table class="scroll_table">
		<tbody>
			<tr style="display:none;">
				<td>2018-08-20</td>
				<td>알통</td>
				<td>1,000원</td>
				<td>1,000원</td>
			</tr>
		</tbody>
	</table>
	<div id="divProg" style="margin-top:15px;width:100%;display:none"><div style="width:30px;margin:auto"><img src="/Common/images/search_nick_loader.gif" style="width:100%"></div></div>
	<br><br><br>
</section>
</body>
</html>