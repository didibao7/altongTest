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
                            온라인 간편 청약서
                        </p>
                        <img src="/Common/images/btn_back_bold.png" class="atm_edittop_btn_L1" onclick="history.back()" />
                    </div>
                </div>
                <!-- 컨텐츠 영역 -->
                <div class="csCenterWrapper"><font color=red><b>※ 배정 기준은 신청 순이 아니라 청약금 입금 순입니다. (청약금 납입 계좌: KB국민은행 0015-9017-328-964 (주)알통)</b></font><br><br>


<table>    
      <tr><td>상 호 : 주식회사 알통</td></tr>
      <tr><td>회사가 발행할 주식의 총수 : 100,000,000주</td></tr>
      <tr><td>1주의 금액(액면가) : 금 500원</td></tr>
      <tr><td>신주식의 종류와 수 : 보통주 45,000주</td></tr>
      <tr><td>신주식의 발행가액 : 1주 15,000원</td></tr>
      <tr><td>납입기일 : 2020년 6월 10일</td></tr>
      <tr><td>청약증거금 납입처	: 한국증권금융 본점</td></tr>
      <tr><td>납입 받을 금융기관 : 우리은행 구로디지털산단센터</td></tr>
      <tr><td>신주식 발행결의일	: 2020년 5월 18일</td></tr>
      <tr><td>주식의 인수방법 :	정관 제 9조 2항(일반공모증자의 방식으로 신주를 발행하는 경우)에 따라 일반(기존 주주 포함)에서 인수하기로 한다.</td></tr>
      
</table><br>

                    <form name="mailing">
                        <div class="csCenterItem">
                            <label>청약자 명</label>
                            <div>
                                <input type="text" name="user_name" class="field">
                            </div>
                        </div>
                        <div class="csCenterItem">
                            <label>주민등록번호 앞 6자리</label>
                            <div>
                                <input type="text" name="regno" class="field">
                            </div>
                        </div>
                        <div class="csCenterItem">
                            <label>휴대전화</label>
                            <div>
                                <input name="phone" type="text" class="field"> 
                            </div>
                        </div>
                        <div class="csCenterItem">
                            <label>이메일</label>
                            <div>
                                <input name="email2" type="text" class="field"> <input type="hidden" value="stock@stock.stock" name="email" class="field">
                            </div>
                        </div>
                        <div class="csCenterItem">
                            <label>주소</label>
                            <div>
                                <input name="address" type="text" class="field"> 
                            </div>
                        </div>

                        <div class="csCenterItem">
                            <label>청약 주식수<br>(금액은 자동 입력) </label>
                            <div><input type=hidden name="sell_price" value="15000">
                                <input type="text" name="stock" value="0" class="num1" onchange="change();"> <font color=gray><b>주</font></b>&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="amount" class="num2" style="color:#999999" readonly> <font color=#999999><b>원</font></b>
                            </div>
                        </div>
                        <div class="csCenterItem">
                            <label>입금자 명</label>
                            <div>
                                <input type="text" name="remitter" class="field">
                            </div>

                        </div>
<br>
<input type="checkbox" name="agree" checked disabled> 날인/서명 생략에 동의
<br><br>
<p><center>상기 내용을 확인한 후 청약합니다.</p><br>
청약일: ${regDate}<br>
</center><br>
                    </form>
                    <button class="btn alt-mint" style="width: 100%;padding: 15px;margin: auto;margin-top: 15px;">청약하기</button><br>


                </div>
                
            </div>
            <%@ include file="/Common/include/MenuItem.jsp" %>
        </div>
    </div>
    <script>


        $('div.csCenterWrapper > button').on('click', function() {
            const $form = $('form[name=mailing]')
            if($form.find('input[name=user_name]').val() === "") {
                alert('청약자 명을 입력해주세요.')
            } else if($form.find('input[name=regno]').val() === "") {
                alert('주민등록번호 앞 6자리를 입력해주세요.')
                $form.find('input[name=regno]').focus()
                $form.find('input[name=user_name]').focus()
            } else if($form.find('input[name=phone]').val() === "") {
                alert('휴대전화 번호를 입력해주세요.')
                $form.find('input[name=phone]').focus()
            } else if($form.find('input[name=address]').val() === "") {
                alert('주소를 입력해주세요.')
                $form.find('input[name=address]').focus()
            } else if($form.find('input[name=stock]').val() === "") {
                alert('청약 주식 수를 입력해주세요.')
                $form.find('input[name=stock]').focus()
            } else {
                $.ajax({
                    type: "POST",
                    url: "/stock/sendEmail",
                    data: $form.serialize(),
                    success: function() {
                        location.href = '/stock/thankyou';
                    },
                });
            }
        });
    </script>
</body>
</html>