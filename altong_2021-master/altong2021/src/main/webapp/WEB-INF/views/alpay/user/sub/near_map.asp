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
<link rel="stylesheet" type="text/css" href="/alpay/common/css/style.css??v=2.1">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="/alpay/common/js/common.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=1a01b5ab9b618306676dc5a370a55cd0&libraries=services"></script>

<script>
if (history.pushState)
{
	history.pushState(null, document.title, location.href);

	window.addEventListener('popstate', function(event) {
		if ($('#payment_box').hasClass('show')) {
            $('#if_pay')[0].contentWindow.backBtn();
            history.pushState(null, document.title, location.href);
        }
		else if ($('#map_list').hasClass('show')) {
			$('#map_list').removeClass('show');
            history.pushState(null, document.title, location.href);
		}
		else {
			history.back();
        }
	});
}
</script>
<script>

var map, geocoder, markerMY
var markers = [];

$(document).ready(function() {
	map = new daum.maps.Map(document.getElementById('divMap'), { 
		center: new daum.maps.LatLng(37.482891670213625,126.89603556148784),
		level: 3 // 지도의 확대 레벨
	}); 
	geocoder = new daum.maps.services.Geocoder();
	markerMY = new daum.maps.Marker();

	// 중심 좌표나 확대 수준이 변경됐을 때 지도 중심 좌표에 대한 주소 정보를 표시하도록 이벤트를 등록합니다
	daum.maps.event.addListener(map, 'idle', function() {
		var lat = map.getCenter().getLat();
		var lon = map.getCenter().getLng();
		geocoder.coord2RegionCode(lon, lat, fDisplayCenterInfo);
		if (map.getLevel() < 4)
			fAjax('ACT=NEAR_SEARCH&H_lat=' + lat + '&H_lon=' + lon);
		else
			fDeleteNearStore();
	});

	fMyPoint();
});

function fMyPoint()
{
	<% if Request.Cookies("lat") <> "" and Request.Cookies("lon") <> "" then %>
		setTimeout(function(){
		var pos = new daum.maps.LatLng(<%=Request.Cookies("lat")%>, <%=Request.Cookies("lon")%>);
		if (map.getLevel() != 3) map.setLevel(3);
		//markerMY.setPosition(pos);
		//markerMY.setMap(map);
		map.panTo(pos);
		},1);
	<% else %>
		if (navigator.geolocation)
		{
			navigator.geolocation.getCurrentPosition(
				function(position)
				{
					var pos = new daum.maps.LatLng(position.coords.latitude, position.coords.longitude);
					if (map.getLevel() != 3) map.setLevel(3);
					markerMY.setPosition(pos);
					markerMY.setMap(map);
					map.panTo(pos);
				},
				function(error)
				{
					switch(error.code)
					{
						case error.PERMISSION_DENIED:
							console.log('getCurrentPosition: PERMISSION_DENIED');
							break;
						case error.POSITION_UNAVAILABLE:
							console.log('getCurrentPosition: POSITION_UNAVAILABLE');
							break;
						case error.TIMEOUT:
							console.log('getCurrentPosition: TIMEOUT');
							break;
						case error.UNKNOWN_ERROR:
							console.log('getCurrentPosition: UNKNOWN_ERROR');
							break;
						default:
							console.log('getCurrentPosition: ETC_ERROR');
							break;
					}
				}
			);
		}
	<% end if %>
}

function fDisplayCenterInfo(result, status)
{
	if (status === daum.maps.services.Status.OK)
	{
		for (var i = 0; i < result.length; i++) {
			if (result[i].region_type === 'H')
			{
				//$('.spnAddr').text(result[i].address_name).parent().css('display','block');
				$('.spnAddr').text(result[i].address_name).css('opacity','1').siblings('i').css('opacity','1');
				break;
			}
		}
	}
	else
		//$('#spnAddr').parent().css('display','none');
		$('.spnAddr').css('opacity','0').siblings('i').css('opacity','0');
}

function fDeleteNearStore()
{
	$('.rowDynamicStore').remove();
	for (var i=0; i<markers.length; i++)
		markers[i].setMap(null);
	markers = [];
}

var ps = new daum.maps.services.Places();
function fSearchAddr(keyword)
{
    if (keyword.replace(/^\s+|\s+$/g, ''))
	{
		ps.keywordSearch(keyword, function placesSearchCB(data, status, pagination) {
			if (status === daum.maps.services.Status.OK)
			{
				$('.rowDynamicAddr').remove();

				for (var i=0; i<data.length; i++)
				{
					var newitem = $('#rowSamAddr').clone();
					newitem.addClass('rowDynamicAddr').attr('lat',data[i].y).attr('lon',data[i].x);

					var s = data[i].place_name + '<br><p style="padding-left:30px;color:#aaa;font-weight:1">'
					if (data[i].road_address_name)
						s += data[i].road_address_name + '<br>';
					s += data[i].address_name + '<br>';
					s += data[i].phone + '</p>';

					newitem.find('#store_addr').html(s);
					newitem.css('display','block').appendTo($('#rowSamAddr').parent());


					newitem.click(function(){
						var pos = new daum.maps.LatLng($(this).attr('lat'), $(this).attr('lon'));
						if (map.getLevel() != 3) map.setLevel(3);
						map.setCenter(pos);
						$('#map_list').removeClass('show');
					});


				}

				$('#map_list').addClass('show');
				$('#map_list').find('.address_search').show();
				$('#map_list').find('.map_search').hide();
				$('#map_list').find('.map_list_header .map_present_title').hide();
				$('#map_list').find('.map_list_header .map_search_title').show();
				$('#map_list').find('.map_search_title > span').text(keyword);
				$('header.map_header .nav_search input').blur();
			}
		});
	}

	return false;
}

function fNearStoreList()
{
	$('#map_list').addClass('show');
	$('#map_list').find('.map_search').show();
	$('#map_list').find('.address_search').hide();
	$('#map_list').find('.map_list_header .map_present_title').show();
	$('#map_list').find('.map_list_header .map_search_title').hide();
}

function fSetMarkerInfo(v)
{
	var obj = $('#' + v);
	$('#map_smallInfo').find('#store_name').text(obj.find('#store_name').text());
	$('#map_smallInfo').find('#store_code').text(v);
	$('#map_smallInfo').find('#store_addr').text(obj.find('#store_addr').text());
	$('#map_smallInfo').find('#store_tel').text(obj.find('#store_tel').text());
	$('#map_smallInfo').find('#store_meter').text(obj.find('#store_meter').text());
	if (obj.find('#store_FavColr').hasClass('add'))
	{
		$('#map_smallInfo').find('#store_FavColr').addClass('add');
		$('#map_smallInfo').find('#store_Fav').text('favorite');
	}
	else
	{
		$('#map_smallInfo').find('#store_FavColr').removeClass('add');
		$('#map_smallInfo').find('#store_Fav').text('favorite_border');
	}

	$('#modal_back').css('display','block');
	$('#map_smallInfo').css('display','block');
}

function fMarkerInfoPay()
{
	var markerPayText = $('#map_smallInfo').find('.text_wrap');
	$('#payment_box').addClass('show');
	var storeName = markerPayText.find('#store_name').text();
	var storeCode = markerPayText.find('#store_code').text();
	document.getElementById('if_pay').contentWindow.fGoStep0(2, storeName, storeCode);
}

function fFavorite(favBtn, t)
{
	var fav_code;
	var fav_addr;

	if (t == "marker") {
		fav_code = $(favBtn).siblings('.text_wrap').find('#store_code').text();
		fav_addr = $(favBtn).siblings('.text_wrap').find('#store_addr').text();
	} else if (t == "list") {
		fav_code = $(favBtn).parent('li').attr('id');
		fav_addr = $(favBtn).siblings('.text_wrap').find('#store_addr').text();
	}

	if ($(favBtn).hasClass('add')) {
		fAjax('ACT=FAV_DEL&F_code='+ fav_code);
	} else {
		fAjax('ACT=FAV_ADD&F_code='+ fav_code);
	}
}

function displayMarker(v)
{
	if (!document.marker) document.marker = new Object();
	var pos = new daum.maps.LatLng(v.store_lat, v.store_lon);
	var icon = new daum.maps.MarkerImage('/img/common/icon/marker_' + v.store_cate + '.png', new daum.maps.Size(57, 35));
	var marker = new daum.maps.Marker({  
		map: map, 
		position: pos,
		clickable: true,
		image: icon
	});
	markers.push(marker);

	daum.maps.event.addListener(marker, 'click', function() {
		fSetMarkerInfo(v.store_code);
	});
}

function fAjax(param)
{
	if (document.xhr) document.xhr.abort();

	document.xhr = $.ajax({
		type : 'post',
		url: '<%=Request.ServerVariables("SCRIPT_NAME") %>',
		data: param,
		dataType: 'json',
		success: function(r) {
			switch (r.result)
			{
				case 'NEAR_SEARCH':
				{
					fDeleteNearStore();
					for (var i=0; i<r.arr.length; i++)
					{
						var newitem = $('#rowSamStore').clone();

						newitem.addClass('rowDynamicStore').attr('id', r.arr[i].store_code);
						//if (!r.arr[i].fav)
						if (document.fav && !document.fav[r.arr[i].store_code])
						{
							newitem.find('#store_FavColr').removeClass('add');
							newitem.find('#store_Fav').text('favorite_border');
						}
						newitem.find('#store_name').text(r.arr[i].store_name);
						newitem.find('#store_addr').text(r.arr[i].store_addr);
						newitem.find('#store_tel').text(r.arr[i].store_tel);
						newitem.find('#store_meter').text(r.arr[i].distance);

						newitem.css('display','block').appendTo($('#rowSamStore').parent());

						//마커표시
						displayMarker(r.arr[i]);

						//store 이미지가 없는 경우 이미지 객체 삭제
						newitem.find('.shop_img').remove();

						//슬라이드 이벤트 등록
						newitem.click(function(){
						// 181011수정_김성희
							if (!$(this).hasClass('show'))
							{
								$(this).siblings().removeClass('show').find('.shop_more').slideUp().removeClass('show');
								$(this).addClass('show').find('.shop_more').slideDown().addClass('show');
							}
						})

						//결제 이벤트 등록
						newitem.find('.shop_select').click(function(){
							$('#payment_box').addClass('show');
							var storeName = $(this).closest('li').find('#store_name').text();
							var storeCode = $(this).closest('li').attr('id');
							document.getElementById('if_pay').contentWindow.fGoStep0(2, storeName, storeCode);
						})

					}

					//관심매장 목록을 아직 받지 않았다면 요청한다.
					if (!document.fav) setTimeout(function(){
						fAjax('ACT=GET_FAV');
					},500);

					break;
				}
				case 'FAV_ADD' :
				{	
					$('#layer_popup').find('.txt').text('내 관심매장에 추가되었습니다.');
					$('#layer_popup').fadeIn();					
					
					$('#map_smallInfo .favorite').addClass('add').find('i.material-icons').text('favorite');
					$('#'+r.arr.store_code+' .favorite').addClass('add').find('i.material-icons').text('favorite');

					if (document.fav)
						document.fav[r.arr.store_code] = true;

					break;
				}
				case 'FAV_DEL' :
				{
					$('#layer_popup').find('.txt').text('내 관심매장에서 삭제되었습니다.');
					$('#layer_popup').fadeIn();

					$('#map_smallInfo .favorite').removeClass('add').find('i.material-icons').text('favorite_border');
					$('#'+r.arr.store_code+' .favorite').removeClass('add').find('i.material-icons').text('favorite_border');					

					if (document.fav)
						document.fav[r.arr.store_code] = false;

					break;
				}
				case 'GET_FAV' :
				{
					if (!document.fav) document.fav = new Object();
					for (var i=0; i<r.arr.length; i++)
						document.fav[r.arr[i].store_code] = true;
					$('.rowDynamicStore').each(function(i){
						var obj = $(this);
						if (!document.fav[obj.attr('id')])
						{
							obj.find('#store_FavColr').removeClass('add');
							obj.find('#store_Fav').text('favorite_border');
						}
					});
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
			for(var key in r) str += key + '=' + r[key] + '\n';
			console.log(str);
		},
		complete: function() {document.xhr=false;}
	});
}
</script>
</head>

<body class="near_map">
    <div class="overlay"></div>
    <div id="layer_popup">
        <div class="layer_wrap">
            <div class="text_wrap">
                <p class="txt">내 관심매장에 추가되었습니다.</p>
                <p class="close_btn"><a href="javascript:layerPopClose()">확인</a></p>
            </div>
        </div>
	</div>
	
    <header class="non_box map_header">
		<div>
			<p class="header_btn"><a href="/alpay/user/AlpayUser.asp"><i class="material-icons">arrow_back</i></a></p>
		</div>
		<div>
			<form onSubmit="return fSearchAddr($('header.map_header input').val());">
				<p class="nav_search">
					<input type="search" placeholder="지역 검색" required="" oninvalid="this.setCustomValidity('검색할 위치를 입력해주세요.')" oninput="setCustomValidity('')">
					<button type="submit"><i class="material-icons">search</i></button>
				</p>
			</form>
		</div>
	</header>
	
	<div class="map_menu">
		<div class="gps_info_box"><i class="material-icons">place</i><span class="spnAddr"></span></div>
		<div class="wrap" style="position:relative">
			<div style="position:absolute;width:50%;height:100%;top:0;left:0;background-color:rgba(255,255,255,0.1);" onClick="fNearStoreList()"></div>
			<div style="position:absolute;width:50%;height:100%;top:0;right:0;background-color:rgba(255,255,255,0.1);" onClick="fMyPoint()"></div>
			<div class="map_list_btn">
				<p><i class="material-icons">list</i></p>
				<p>목록 보기</p>			
			</div>
			<div class="map_gps_btn">
				<p><i class="material-icons">gps_not_fixed</i></p>
				<p>기본 위치</p>
			</div>
		</div>
	</div>

	<div id="modal_back" style="position:absolute;top:0;left:0;right:0;bottom:0;background:transparent;z-index:5;display:none"></div>
	<div class="map_smallInfo" id="map_smallInfo" style="z-index:6;display:none">
		<div class="map_smallInfo_wrap">
			<p id="store_FavColr" class="favorite add" onclick="fFavorite(this, 'marker');"><a href="javascript:void(0);"><i class="material-icons" id="store_Fav">favorite</i></a></p>
			<div class="text_wrap">
				<h3><span class="shop_name" id="store_name">알통 구로점</span><span id="store_code" style="display:none"></span></h3>
				<p id="store_addr">서울 구로구 구로동</p>
			</div>
			<p class="shop_more_info">
				전화번호 : <span id="store_tel">00-000-0000</span>
				<span id="store_meter">36M</span>
			</p>
			<p class="shop_img"></p>
			<p class="shop_select" onclick="fMarkerInfoPay();"><a href="javascript:void(0);">결제하기</a></p>
		</div>
	</div>
	<script>
	$('#modal_back').click(function(){
		$('#modal_back').css('display','none');
		$('#map_smallInfo').css('display','none');
	});
	</script>

	<div class="map_list" id="map_list">
		<div class="map_list_header">
			<a href="javascript:void(0);" onclick="$('#map_list').removeClass('show');return false;"><i class="material-icons">arrow_back</i></a>
			<p class="map_present_title"><i class="material-icons">place</i><span class="spnAddr"></span></p>
			<p class="map_search_title"><i class="material-icons" style="margin-top:-2px;margin-right:2px;">search</i>"<span></span>" 검색 결과</p>
		</div>
		<div class="scroll_wrap">
			<ul class="address_search">
				<li id="rowSamAddr" style="display:none"><i class="material-icons">search</i><span id="store_addr">서울 구로구</span></li>
			</ul>
			<ul class="map_search">
				<li id="rowSamStore" style="display:none">
					<p class="favorite add" id="store_FavColr" onclick="fFavorite(this, 'list');"><a href="javascript:void(0);"><i class="material-icons" id="store_Fav">favorite</i></a></p>
					<div class="text_wrap">
						<h3><span class="li_num"><i class="material-icons">place</i></span><span id="store_name">알통 구로점</span></h3>
						<p id="store_addr">서울 구로구</p>
					</div>
					<div class="shop_more">
						<p class="shop_more_info">전화번호 : <span id="store_tel">00-000-0000</span><span id="store_meter">36M</span></p>
						<p class="shop_img"></p>
						<p class="shop_select"><a href="javascript:void(0);">결제하기</a></p>
					</div>
				</li>
			</ul>
		</div>
	</div>

	<div style="height:100%;box-sizing:border-box;">
		<div id="divMap" style="height:100%"></div>
	</div>

	<!-- 결제창 -->
	<div id="payment_box">
		<iframe id="if_pay" src="../payment.asp" frameborder="0" width="100%" height="100%" marginheight="0" marginwidth="0"></iframe>
	</div>
</body>

</html>