<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>알통 現況板</title>
<style>
  body::-webkit-scrollbar {
    display:none;
  }
td{
		padding: 0;
	}
	table{
		padding: 0px;
		height: 100%;
		widows: 100%;
		background-image: url(/Common/statistics/img/bg.jpg);
		background-size: 100%;
		color: aliceblue;
	}
	.dataTable{
		background-image: url(/Common/statistics/img/bar.jpg);
		background-size: cover;
		color: #faec8b;
		text-align: right;
		height: 57px;
		max-height:57px;
    font-size: 2em;
	}
	.unit{
		display: inline; 
		color: white;
		margin-left: 4px;
		margin-right: 15px;
		max-height:57px;
	}
  .cell{
    width: 100%; 
    height: 57px;
    vertical-align: middle;
  }
  .title1{
    background-image: url(/Common/statistics/img/left_title_01.png);
		background-size: cover;
    color: #faec8b;
    text-align: center;
    font-size: 2em;
    letter-spacing:1em;
  }
  .title2{
    background-image: url(/Common/statistics/img/left_title_02.png);
		background-size: cover;
    color: #faec8b;
    text-align: center;
    font-size: 2em;
    letter-spacing:0.5em;
  }
  .cell {
    line-height: 2em;
  }
</style>

<!-- The main CSS file -->
<link href="/Common/statistics/assets/css/style.css" rel="stylesheet" />

<!--[if lt IE 9]>
	<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->

<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.0.0/moment.min.js"></script>
</head>

<body>
<table width="100%" height="100%" border="0" cellpadding="0px" cellspacing="1px">
  <tbody>
    <tr>
      <td width="10%">&nbsp;</td>
      <td colspan="3" width="60%" id="title"><img src="/Common/statistics/img/title.png" style="width: 100%;"></td>
      <td>
			&nbsp;&nbsp;&nbsp;
			<div id="clock" class="light">
				<div class="display">
					<div class="digits"></div>
				</div>
			</div>
			<script src="/Common/statistics/assets/js/script.js"></script>
      </td>
    </tr>
    <tr>
      <td width="15%">&nbsp;</td>
      <td width="17%"><div style="color: #fff; font-size: 2em;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Today: ${fn:replace(now, '-', '.')}</td>
      <td width="17%"><div style="color: #faec8b; font-size: 2em;"><center>누&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;계</center></td>
      <td width="17%"><div style="color: #faec8b; font-size: 2em;"><center>전&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;일</center></td>
      <td width="17%"><div style="color: #ffffff; font-size: 2em;"><center>금&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;일</center></td>
      <td width="15%">&nbsp;</td>
    </tr>
    <tr>
      <td height="18">&nbsp;</td>
      <td><div class="cell title1">&nbsp;회원</div></td>
      <td class="dataTable"><span id="members1"></span><div class="unit">명</div></td>
      <td class="dataTable"><span id="members2"></span><div class="unit">명</div></td>
      <td class="dataTable"><span id="members3"></span><div class="unit">명</div></td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td height="18">&nbsp;</td>
      <td><div class="cell title1">&nbsp;금액</div></td>
      <td class="dataTable"><span id="almoney1"></span><div class="unit">알</div></td>
      <td class="dataTable"><span id="almoney2"></span><div class="unit">알</div></td>
      <td class="dataTable"><span id="almoney3"></span><div class="unit">알</div></td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td height="18">&nbsp;</td>
      <td><div class="cell title1">&nbsp;질문</div></td>
      <td class="dataTable"><span id="question1"></span><div class="unit">개</div></td>
      <td class="dataTable"><span id="question2"></span><div class="unit">개</div></td>
      <td class="dataTable"><span id="question3"></span><div class="unit">개</div></td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td height="18">&nbsp;</td>
      <td><div class="cell title2">&nbsp;- 질문금액</div></td>
      <td class="dataTable"><span id="questionMoney1"></span><div class="unit">알</div></td>
      <td class="dataTable"><span id="questionMoney2"></span><div class="unit">알</div></td>
      <td class="dataTable"><span id="questionMoney3"></span><div class="unit">알</div></td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td height="18">&nbsp;</td>
      <td><div class="cell title2">&nbsp;- 열람수익</div></td>
      <td class="dataTable"><span id="questionEarning1"></span><div class="unit">알</div></td>
      <td class="dataTable"><span id="questionEarning2"></span><div class="unit">알</div></td>
      <td class="dataTable"><span id="questionEarning3"></span><div class="unit">알</div></td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td height="18">&nbsp;</td>
      <td><div class="cell title2">&nbsp;- 미채택</div></td>
      <td class="dataTable"><span id="questionNotCohiceAnswer1"></span><div class="unit">개</div></td>
      <td class="dataTable"><span id="questionNotCohiceAnswer2"></span><div class="unit">개</div></td>
      <td class="dataTable"><span id="questionNotCohiceAnswer3"></span><div class="unit">개</div></td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td height="18">&nbsp;</td>
      <td><div class="cell title1">&nbsp;답변</div></td>
      <td class="dataTable"><span id="answer1"></span><div class="unit">개</div></td>
      <td class="dataTable"><span id="answer2"></span><div class="unit">개</div></td>
      <td class="dataTable"><span id="answer3"></span><div class="unit">개</div></td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td height="18">&nbsp;</td>
      <td><div class="cell title2">&nbsp;- 채택수익</div></td>
      <td class="dataTable"><span id="answerChoice1"></span><div class="unit">알</div></td>
      <td class="dataTable"><span id="answerChoice2"></span><div class="unit">알</div></td>
      <td class="dataTable"><span id="answerChoice3"></span><div class="unit">알</div></td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td height="18">&nbsp;</td>
      <td><div class="cell title2">&nbsp;- 열람수익</div></td>
      <td class="dataTable"><span id="answerEarning1"></span><div class="unit">알</div></td>
      <td class="dataTable"><span id="answerEarning2"></span><div class="unit">알</div></td>
      <td class="dataTable"><span id="answerEarning3"></span><div class="unit">알</div></td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td height="18">&nbsp;</td>
      <td><div class="cell title2">&nbsp;- 미답변</div></td>
      <td class="dataTable"><span id="notAnsweredQuestion1"></span><div class="unit">개</div></td>
      <td class="dataTable"><span id="notAnsweredQuestion2"></span><div class="unit">개</div></td>
      <td class="dataTable"><span id="notAnsweredQuestion3"></span><div class="unit">개</div></td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td height="18">&nbsp;</td>
      <td><div class="cell title1">&nbsp;노출</div></div></td>
      <td class="dataTable"><span id="adShow1"></span><div class="unit">회</div></td>
      <td class="dataTable"><span id="adShow2"></span><div class="unit">회</div></td>
      <td class="dataTable"><span id="adShow3"></span><div class="unit">회</div></td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td height="18">&nbsp;</td>
      <td><div class="cell title1">&nbsp;평가</div></td>
      <td class="dataTable"><span id="estimate1"></span><div class="unit">회</div></td>
      <td class="dataTable"><span id="estimate2"></span><div class="unit">회</div></td>
      <td class="dataTable"><span id="estimate3"></span><div class="unit">회</div></td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td height="18">&nbsp;</td>
      <td><div class="cell title1">&nbsp;클릭</div></td>
      <td class="dataTable"><span id="adClick1"></span><div class="unit">회</div></td>
      <td class="dataTable"><span id="adClick2"></span><div class="unit">회</div></td>
      <td class="dataTable"><span id="adClick3"></span><div class="unit">회</div></td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td height="18">&nbsp;</td>
      <td><div class="cell title1">&nbsp;기타</div></td>
      <td class="dataTable"><span id="unCate"></span><div class="unit">알</div></td>
      <td class="dataTable">&nbsp;</td>
      <td class="dataTable">&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td height="18">&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
  </tbody>
</table>
<script>
function formatNumber(x) {
	return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

var blink;
function writeData(){
	var data;
	$.ajax({
		url: '/statistics/getStatData',
		success: function(json){

			if (blink)
			{
				clearInterval(blink);
				$(".digits").show(0);
			}

			data = json; //$.parseJSON(json)[0];
			//console.log(json);
			
			for(key in data) {
				$('#' + key).html(formatNumber(data[key]));
			}

			if ($("#almoney1").text().indexOf('.') > 0) {
				var AlmoneySum = $("#almoney1").text().split('.');
				AlmoneySum[1] = AlmoneySum[1].substr(0,1);
				$("#almoney1").text(AlmoneySum[0] + '.' + AlmoneySum[1]);
			}
		},
		error: function (r, textStatus, err) {
			if (blink) clearInterval(blink);
			blink = setInterval(function(){
			  $(".digits").toggle();
			}, 500);
			//console.log(r);
		}
	});
}
writeData();
setInterval(writeData, 1000 * 60);
</script>
</body>
</html>