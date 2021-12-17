<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>

.csCenterWrapper{
    margin: 10px;
    padding: 15px;
    background-color: white;
    border: 1px solid grey;
    border-radius: 0.5em;
}
.csCenterItem{
    display: -ms-grid;
    display: grid;
    -ms-grid-columns: 20% 80%;
        grid-template-columns: 20% 80%;
    align-items: center;
    margin: 5px;
}
.csCenterItem > label {
    -ms-grid-column: 1;
        grid-column-start: 1;
    grid-column-end: 2;
    font-size: 14px;
    font-weight: bold;
    color: gray;
    letter-spacing: -1px;
}
.csCenterItem > div {
    -ms-grid-column: 2;
        grid-column-start: 2;
    grid-column-end: 3;
	font-size: 16px;
	font-weight: bold;
	margin-left: 5px;
}
.field {
    width: 100%;
    padding: 8px 10px;
    font-size: 14px;
    font-size: inherit;
    color: #3A3635;
    border-radius: 1px;
    border: 1px solid #c4c2c2;
    box-sizing: border-box;
    margin-top: 6px;
    vertical-align: middle;
}
.num1 {
    width: 30%;
    padding: 8px 10px;
    font-size: 14px;
    font-size: inherit;
    color: #3A3635;
    border-radius: 1px;
    border: 1px solid #c4c2c2;
    box-sizing: border-box;
    margin-top: 6px;
    vertical-align: middle;
}
.num2 {
    width: 45%;
    padding: 8px 10px;
    font-size: 14px;
    font-size: inherit;
    color: #3A3635;
    border-radius: 1px;
    border: 1px solid #c4c2c2;
    box-sizing: border-box;
    margin-top: 6px;
    vertical-align: middle;
}
.atm_base_wrapper1{
    max-width: 768px;
    margin: auto;
}
.ok_btn{
    background-color: #2ac1bc;
    border-color: #008d88;
}
.greyText{
	color: #a6a6a6;
}
.phone{
    width: 34%;
}
[name=emailDomain]{
        width: 95%;
}
@media (max-width: 768px) {
    .phone{
        width: 33%;
    }
    [name=emailDomain]{
        width: 94%;
    }
}
</style>
<script>
    $(function(){
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

                $('#selectSection option:last-child').before('<option value="'+optionName+'">'+optionName+'</option>');
                $('#selectSection').val(optionName).attr('selected', 'selected');
            }
        }
    })
</script>
</head>
<body style="background:#e6e6e6" onload="init();">
<script language="JavaScript">
<!--
var sell_price;
var stock;

function init () {
	sell_price = document.mailing.sell_price.value;
	stock = document.mailing.stock.value;
	document.mailing.amount.value = sell_price;
	change();
}

function add () {
	hm = document.mailing.stock;
	amount = document.mailing.amount;
	hm.value ++ ;

	amoount.value = parseInt(hm.value) * sell_price;
}

function del () {
	hm = document.mailing.stock;
	amount = document.mailing.amount;
		if (hm.value > 1) {
			hm.value -- ;
			amount.value = parseInt(hm.value) * sell_price;
		}
}

function change () {
	hm = document.mailing.stock;
	amount = document.mailing.amount;

		if (hm.value < 0) {
			hm.value = 0;
		}
	amount.value = parseInt(hm.value) * sell_price;
}  
//-->

</script>
    <div id="atm_wrapper">

        <div id="atm_wrapper">
            <%@ include file="/Common/include/MenuSub.jsp" %>
            <div class="atm_base_wrapper1">
                <!--wrapper start -->
                <div class="atm_edittop_ttbar">
                    <div class="atm_edittop_ttbar_pc">
                        <p class="atm_edittop_c1" style="height: 33px;">
                            주식 공모
                        </p>
                        <img src="/Common/images/btn_back_bold.png" class="atm_edittop_btn_L1" onclick="history.back()" />
                    </div>
                </div>
                <!-- 컨텐츠 영역 -->


<div class="csCenterWrapper">
<div class="userguide_scroll">
<table width="100%">
<tbody>
<tr>
<td style="max-width: 100%; height: auto !important; text-align: center;"><iframe src="https://www.youtube.com/embed/qNzOJSC0S8E" width="100%" height="150" frameborder="0" allowfullscreen="allowfullscreen"></iframe></td>
</tr>
<tr>
<td style="max-width: 100%; height: auto !important; text-align: center;"><a href="/Common/stock/th11.pdf" target="_blank" rel="noopener"><img src="/Common/stock/th11.png" width="100%" /></a></td>
</tr>


  <tr>
<td>&nbsp;</td>
</tr>
  <tr>
<td style="max-width: 100%; height: auto !important; text-align: center;"><a href="/Common/stock/article.pdf" target="_blank" rel="noopener"><img src="/Common/stock/article.png" width="100%" /></a></td>
</tr>




</tbody>
</table>
</div>
<br />
<p style="text-align: center;">&nbsp;</p>
<p style="text-align: center;"><strong>알통의 신문광고 둘러보기</strong></p>
<p style="text-align: center;">아래의 광고들은 2019년부터 알통이 여러 주요 일간지에 게재해오고 있는 광고 중 일부입니 다. 저희 알통의 경영철학과 이념을 이해하시는 데 조금이나마 도움이 되시길 바랍니다.</p>
<p style="text-align: center;">&nbsp;</p>
<table style="border-color: a9a9a9;" border="0" width="100%" cellpadding="20" align="center">
<tbody>
<tr style="height: 11px;">
<td style="width: 11%; text-align: center; height: 11px;"><strong>제목</strong></td>
<td style="width: 10%; text-align: center; height: 11px;"><strong>PDF</strong></td>
<td style="width: 38%; text-align: center; height: 11px;"><strong>내용</strong></td>
<td style="width: 15%; text-align: center; height: 11px;"><strong>매체명</strong></td>
</tr>
<tr style="height: 128px;">
<td style="width: 11%; text-align: center; height: 128px;">세계경영</td>
<td style="width: 10%; text-align: center; height: 128px;" align="center"><a href="/Common/stock/ad10.pdf" target="_blank" rel="noopener"><img src="/Common/stock/th10.png" width="90" /></a></td>
<td style="width: 38%; text-align: center; height: 128px;">21세기의 석유라고 할 수 있는 데이터 확보를 위해 대한민국이 서 둘러 인터넷 영토 확장에 힘써야 함을 역설</td>
<td style="width: 15%; text-align: center; height: 128px;">조선/중앙/동아/매경/한경/경향/문화</td>
</tr>
<tr style="height: 128px;">
<td style="width: 11%; text-align: center; height: 128px;">힘내요, 대한민국</td>
<td style="width: 10%; text-align: center; height: 128px;" align="center"><a href="/Common/stock/ad05.pdf" target="_blank" rel="noopener"><img src="/Common/stock/th05.png" width="90" /></a></td>
<td style="width: 38%; text-align: center; height: 128px;">코로나19에 맞서 온 국민이 힘을 내자는 취지의 광고. 이 광고를 필두로 각 대기업들의 코로나 관련 광고가 게재되기 시작함</td>
<td style="width: 15%; text-align: center; height: 128px;">조선/중앙/동아/매경/한경/경향/문화</td>
</tr>
<tr style="height: 128px;">
<td style="width: 11%; text-align: center; height: 128px;">손정의 회장님께 드리는 글</td>
<td style="width: 10%; text-align: center; height: 128px;" align="center"><a href="/Common/stock/ad07.pdf" target="_blank" rel="noopener"><img src="/Common/stock/th07.png" width="90" /></a></td>
<td style="width: 38%; text-align: center; height: 128px;">알통의 웅대한 포부를 동종 IT업계의 손정의 회장에게 알리고자 하는 목적으로 작성된 광고</td>
<td style="width: 15%; text-align: center; height: 128px;">조선/중앙/동아/매경/한경/경향/문화</td>
</tr>
</tbody>
</table>
<p style="text-align: center;">&nbsp;</p>
<p style="text-align: center;">&nbsp;</p>
<p style="text-align: center;"><strong>제 2차 신주발행 유상증자</strong></p>
<p style="text-align: center;"><br />당사는 2020년 5월 18일 이사회 결의에 의해 아래와 같이 신주발행 유상증자를 실시하오 니 알통과 미래를 함께하실 분들의 많은 관심을 부탁드립니다.&nbsp;</p>
<p style="text-align: center;">&nbsp;</p>
<p style="text-align: left;">1. 공모 개요&nbsp;</p>
<p style="text-align: left;">&nbsp; ▣ 발행 회사명: 주식회사 알통&nbsp;</p>
<p style="text-align: left;">&nbsp; ▣ 모집 증권의 종류 및 수: 기명식 보통주 45,000주&nbsp;</p>
<p style="text-align: left;">&nbsp; ▣ 모집가액: 15,000원 &nbsp;</p>
<p style="text-align: left;">&nbsp; ▣ 청약일시: 2020년 6월 2일(화) ~ 6월 5일(금) 16시</p>
<p style="text-align: left;">&nbsp;</p>
<p style="text-align: left;">2. 청약 관련 사항&nbsp;</p>
<p style="text-align: left;">&nbsp; - 청약 방법: 당사 방문 신청 또는 전화 문의.<!-- <a href="/stock/subscription" rel="noopener">온라인 간편 청약서 (링크)</a></p> -->
<p style="text-align: left;">&nbsp; ▣ 청약 증거금: 청약 금액의 100%로 함&nbsp;</p>
<p style="text-align: left;">&nbsp; ※ 청약 증거금은 주금 납입일에 납입금으로 대체. 이자 無&nbsp;</p>
<p style="text-align: left;">&nbsp; ▣ 청약 수량: 1인당 최소 청약 수량은 200주, 최대 청약 가능 수량은 6,000주임 (청약 단위는 100주)</p>
<p style="text-align: left;">&nbsp; ▣ 청약 취급처: 서울시 구로구 디지털로30길 31 (코오롱디지털빌란트2차) 905호&nbsp;㈜ 알통 경영기획팀&nbsp;</p>
<p style="text-align: left;">&nbsp; &nbsp; 전화: 02-330-3000&nbsp; &nbsp; 팩스: 02-330-3001&nbsp;</p>
<p style="text-align: left;">&nbsp; ▣ 청약 증거금 납입 계좌: KB국민은행 0015-9017-328-964 (주)알통</p>
<p style="text-align: left;">&nbsp;</p>
<p style="text-align: left;">3. 배정 및 환불 관련 사항</p>
<p style="text-align: left;">&nbsp; ▣ 배정 방법: 초과 청약 시 청약 증거금 입금 순으로 배정&nbsp;</p>
<p style="text-align: left;">&nbsp; ※ 공모 후 청약 주식 수가 공모 주식 수를 초과하는 경우 청약 증거금 입금 선착순에 따 라 배정이 되며 최후 순위 배정자까지의 공모 총액이 6억 7,500만원을 초과할 수 없으므로 최후 순위 배정자의 경우 청약 주식 수보다 적은 수의 주식이 배정될 수 있습니다. 또한 잔여 주식이 발생할 경우 최고 청약자로부터 순차적으로 우선 배정하되 동순 위 최고 청약자가 최종 잔여 주식보다 많은 경우 추첨에 의하여 우선 배정합니다.</p>
<p style="text-align: left;">&nbsp; ▣ 초과 청약금 환불 및 주금 납입기일: 2020년 6월 10일(수)</p>
<p style="text-align: left;">&nbsp; ※ 주식 청약금 및 초과 청약금 환불 등에 관한 사항: 자본시장과 금융투자업에 관한 법률 시행령 제137조 3의 2조를 반영하여, 당사는 한국증권금융(주)와 청약증거금 관리계약을 체결하여, 해당 청약 증거금에 대하여 반환 전까지 초과 청약금 인출 목적 외의 인출을 제한하며, 초과 청약금 환불일에 청약 증거금 관리점에서 청약인에게 일괄 송금될 예정입니다.&nbsp;</p>
<p style="text-align: left;">&nbsp;</p>
<!-- <h4 style="text-align: center;"><a href="/stock/subscription" rel="noopener">&gt;&gt; 온라인에서 청약서 작성</a></h4> -->
<h5>&nbsp;</h5>
<p style="text-align: center;"><img src="/Common/images/logo3.png" width="150" /></p>
<div style="border: 1px dashed; padding: 10px;">인터넷상의 모든 지식과 정보에 금전적인 가치를 매긴다면 과연 얼마나 될까요? 알통의 출발점은 바로 공짜로 인식되어 온 이러한 인터넷상의 지식이나 정보에도 폰트나 사진처럼 지적재산권을 부여한다는 역발상입니다. 예를 들어 &lsquo;하늘은 왜 파랄까요?&rsquo;라는 질문에도 지적재산권을 부여하여 이 질문을 한 사람과 답변을 단 사람에게 수익을 배분해 주는 구조로, 여지껏 답변자에게 수익을 제공하는 서비스는 있었지만 질문자에게도 수익 제공을 하는 서비스는 없었습니다. 게다가 이 수익은 일회성이 아니라 검색을 통해 유입되는 불특정 다수에 의해 조회될 때마다 창출되는 반영구적인 수입입니다.<br /> 지적(知的) 활동의 소산인 &lsquo;알&rsquo;은 본인 계좌로 입금을 받을 수 있고 현재 진행 중인 교통카드와의 시스템 연동이나 올해부터 본격 모집에 나설 오프라인 가맹점이 확충되면 어디서나 편리하게 현금 대신 사용할 수 있게 됩니다.<br /> 아직 알통의 형태는 지식금융 플랫폼이지만 정보의 양이 늘어날수록 점차 검색엔진의 형태로 진화해 나아갈 것입니다. 지식자본주의를 향한 알통의 도전은 지금부터 시작입니다.</div>
</div>
</div>

</body>
</html>