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
<link rel="stylesheet" type="text/css" href="/alpay/common/css/style.css?v=2.1">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="/alpay/common/js/common.js"></script>
<script>
if (history.pushState) {
	history.pushState(null, document.title, location.href);

	window.addEventListener('popstate', function (event) {
		if ($('#payment_box').hasClass('show')) {
			if ($('#if_pay')[0].contentWindow.backBtn())
				history.pushState(null, document.title, location.href);
		}
		else {
			history.back();
		}
	});
}
$(document).ready(function () {
	fAjax('ACT=FAV_HISTORY');
});

function favDel(DelList) {
	$(DelList).parents('tr').addClass('del');
	var fav_code = $(DelList).parents('tr').attr('store_code');
	fAjax('ACT=FAV_DEL&F_code=' + fav_code);
}

function favPay(payList) {
	$('#payment_box').addClass('show');
	var storeName = $(payList).parent().siblings('.shop_info').find('.store_name').text();
	var storeCode = $(payList).parents('tr').attr('store_code');
	document.getElementById('if_pay').contentWindow.fGoStep0(2, storeName, storeCode);
}

function fAjax(param) {
	if (document.xhr)
	{   
		$('#Tip').text('이전 작업이 진행중입니다.').css('display','block').stop().fadeIn(0, function(){$(this).fadeOut(3000)});
		return;
	}

	document.xhr = $.ajax({
		type: 'post',
		url: '<%=Request.ServerVariables("SCRIPT_NAME") %>',
		data: param,
		dataType: 'json',
		success: function (r) {
			switch (r.result) {
				case 'FAV_HISTORY':
					{
						for (var i = 0; i < r.arr.length; i++) {
							var favlist = $('.fav_list table tbody tr:first-child').clone();
							favlist.addClass('rowDynamicfav').attr('store_code', r.arr[i].store_code);
							favlist.find('.store_name').text(r.arr[i].store_name);
							favlist.find('.store_addr').text(r.arr[i].store_addr);
							favlist.css('display', 'table').appendTo($('.fav_list table tbody'));
						}
						break;
					}
				case 'FAV_DEL':
					{
						console.log('adf');
						var favoriteDel =$('.fav_list table tbody tr.del');
						$('#layer_popup').find('.txt').text('내 관심매장에서 삭제되었습니다.');
						$('#layer_popup').fadeIn();	
						console.log(favoriteDel);

						$('#layer_popup').find('.close_btn').click(function () {
							layerPopDel(favoriteDel);
							console.log('adf33');
						})

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

</script>
</head>

<body>
    <div class="overlay"></div>
    <div id="layer_popup">
        <div class="layer_wrap">
            <div class="text_wrap">
                <p class="txt">내 관심매장에 추가되었습니다.</p>
                <p class="close_btn"><a href="javascript:void(0);">확인</a></p>
            </div>
        </div>
    </div>

    <header>
        <div>
            <p><a href="/alpay/user/AlpayUserMain.asp"><i class="material-icons">arrow_back</i></a></p>
        </div>
        <div>
            <h3>내 관심 매장</h3>
        </div>
    </header>

    <section class="fav_list">
        <table>
            <tbody>
                <tr style="display:none">
                    <td>
                        <p class="favorite add" onclick="favDel(this);"><a href="javascript:void(0)"><i class="material-icons">favorite</i></a></p>
                    </td>
                    <td class="shop_info">
                        <p class="store_name">빽다방 구로 코오릉점</p>
                        <p class="store_addr">서울 구로구 디지털로 30길 31 코오릉 디지털 빌란트2</p>
                    </td>
                    <td>
                        <button type="button" onclick="favPay(this);">결제하기 <i class="material-icons">keyboard_arrow_right</i></button>
                    </td>
                </tr>
            </tbody>
        </table>
    </section>

    <!-- 결제창 -->
    <div id="payment_box">
        <iframe id="if_pay" src="../payment.asp" frameborder="0" width="100%" height="100%" marginheight="0" marginwidth="0"></iframe>
    </div>
</body>

</html>