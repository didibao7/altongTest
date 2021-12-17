<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp"%>
<head>
<link rel="stylesheet" href="/pub/stock/ads/ads.css?ver=1.0">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.4">
</head>
<body>
	<%@ include file="/pub/menu/topMenu.jsp"%>
	<div class="csCenterWrapper">
        <div class="center">
            <h1>광고모음</h1>
            <div class="userguide_scroll">
                <div class="video_wrap">
                    <iframe src="https://www.youtube.com/embed/qNzOJSC0S8E" frameborder="0" allowfullscreen="allowfullscreen"></iframe>
                </div>
                <div>
                    <a href="http://www.altong.com/Common/stock/article.pdf" target="_blank" rel="noopener">
                        <img src="http://www.altong.com/Common/stock/article.png" width="100%">
                    </a>
                </div>
            </div>
            <p><strong>알통의 신문광고 둘러보기</strong></p>
            <p class="bottom">아래의 광고들은 2019년부터 알통이 여러 주요 일간지에 게재해오고 있는 광고 중 일부입니다.<br> 저희 알통의 경영철학과 이념을 이해하시는 데 조금이나마 도움이 되시길 바랍니다.</p>
            <table>
                <tr>
                    <th>PDF</th>
                    <th>제목</th>
                    <th>게재 신문</th>
                </tr>
                <tr>
                    <td><a href="/Common/stock/ad01.pdf" target="_blank" rel="noopener"><img src="/Common/stock/th01.png"></a></td>
                    <td><b>손정의 회장님께 드리는 글(1)</b><a href="/Common/stock/ad_text.html#ad01">하늘천 따지 검을현 누를황 검은 암흑의 우주를 북극성을 등대 삼아 지구호를 타고 우리는 기나...</a> </td>
                    <td>조선</td>
                </tr>
                <tr>
                    <td><a href="/Common/stock/ad02.pdf" target="_blank" rel="noopener"><img src="/Common/stock/th02.png"></a></td>
                    <td><b>국민교육헌장</b><a href="http://www.altong.com/Common/stock/ad_text.html#ad02">국민교육헌장이 나올 즈음 대한민국은 일제강점기와 참담한 동족 간의 전쟁으로 완전 폐허가 되었고... </a></td>
                    <td>조선/중앙/동아/매경/한경/경향/문화/전자</td>
                </tr>
                <tr>
                    <td><a href="/Common/stock/ad04.pdf" target="_blank" rel="noopener"><img src="/Common/stock/th04.png"></a></td>
                    <td><b>기미독립선언서</b><a href="/Common/stock/ad_text.html#ad04">우리는 여기에 우리 조선이 독립국임과 조선인이 자주민임을 선언하노라. 이로써 세계만방에 인류...</a></td>
                    <td>조선/중앙/동아/매경/한경/경향/문화</td>
                </tr>
                <tr>
                    <td><a href="/Common/stock/ad05.pdf" target="_blank" rel="noopener"><img src="/Common/stock/th05.png"></a></td>
                    <td><b>힘내요, 대한민국</b><a href="/Common/stock/ad_text.html#ad05">코로나19가 소중한 생명까지 넘보며 온 나라가 몸살을 앓고 있습니다. 모두들 힘들고 어려운 상황...</a></td>
                    <td>조선/중앙/동아/매경/한경/경향/문화</td>
                </tr>
                <tr>
                    <td><a href="/Common/stock/ad06.pdf" target="_blank" rel="noopener"><img src="/Common/stock/th06.png"></a></td>
                    <td><b>촛불과 태극기</b><a href="/Common/stock/ad_text.html#ad06">정의란 무엇인가 ? 구글에서 ‘정의란 무엇인가’를 검색해 보면 온통 마이클 샌델의 저서 ‘정의...</a></td>
                    <td>조선/중앙/동아/매경/한경/경향/문화</td>
                </tr>
                <tr>
                    <td><a href="/Common/stock/ad07.pdf" target="_blank" rel="noopener"><img src="/Common/stock/th07.png"></a></td>
                    <td><b>손정의 회장님께 드리는 글(2)</b><a href="/Common/stock/ad_text.html#ad07">약간의 이설이 있습니다만 1946년 최초의 컴퓨터인 '에니악(ENIAC)'이 미국 펜실베이니아 대학...</a></td>
                    <td>조선/중앙/매경/한경/경향/문화</td>
                </tr>
                <tr>
                    <td><a href="/Common/stock/ad08.pdf" target="_blank" rel="noopener"><img src="/Common/stock/th08.png"></a></td>
                    <td><b>미래는 누구의 것인가</b><a href="/Common/stock/ad_text.html#ad08">天천. 地지. 人 인. 以玄默爲大이현묵위대. 以蓄藏爲大이축장위대. 以知能爲大이지능위대. 普圓보원...</a></td>
                    <td>조선/중앙/매경/한경/경향/문화</td>
                </tr>
                <tr>
                    <td><a href="/Common/stock/ad11.pdf" target="_blank" rel="noopener"><img src="/Common/stock/th11.png"></a></td>
                    <td><b>대한민국은 모든 나라의 희망이 되는 세계 최초의 국가</b><a href="/Common/stock/ad_text.html#ad11">다들 코로나 이전의 세상으로 돌아가기는 어렵다고 말합니다. 코로나 이후의 세상을 예측할 방법도...</a></td>
                    <td>조선/중앙/동아/한경/한겨레/문화/서울</td>
                </tr>
            </table>
        </div>
    </div>


</body>
<script>
	$(function() {
		var loadURL = $(location).attr('search');

		if (loadURL) {
			var v = 'cmd';
			var vIndex = loadURL.indexOf(v + "=");
			var base = loadURL.substring(vIndex, (loadURL.length));

			var a = (v.length + 1);
			var b;

			if (base.indexOf('&') != -1)
				b = base.indexOf('&');
			else
				b = base.length;

			var result = base.substring(a, b);

			if (v == 'cmd') {
				var optionName;

				if (result == 'leave') {
					optionName = '회원탈퇴';
				}

				$('#selectSection option:last-child').before(
						'<option value="'+optionName+'">' + optionName
								+ '</option>');
				$('#selectSection').val(optionName)
						.attr('selected', 'selected');
			}
		}
	})

	<!--
	var sell_price;
	var stock;

	function init() {
		sell_price = document.mailing.sell_price.value;
		stock = document.mailing.stock.value;
		document.mailing.amount.value = sell_price;
		change();
	}

	function add() {
		hm = document.mailing.stock;
		amount = document.mailing.amount;
		hm.value++;

		amoount.value = parseInt(hm.value) * sell_price;
	}

	function del() {
		hm = document.mailing.stock;
		amount = document.mailing.amount;
		if (hm.value > 1) {
			hm.value--;
			amount.value = parseInt(hm.value) * sell_price;
		}
	}

	function change() {
		hm = document.mailing.stock;
		amount = document.mailing.amount;

		if (hm.value < 0) {
			hm.value = 0;
		}
		amount.value = parseInt(hm.value) * sell_price;
	}
//-->
</script>
</html>