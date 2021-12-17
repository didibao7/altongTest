<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>퀴즈게임</title>
<link rel="stylesheet" type="text/css" href="/Common/CSS/jquery-ui.css">    
<script type="text/javascript" src="/Common/js/jquery.js"></script>    
<script type="text/javascript" src="/Common/js/jquery-ui.js"></script>    
<script>
var userLv = '${userLv}';
var uid = '${uid}';
var startYmd = '${quiz.startYmd}';
var startDtType = '${quiz.startDtType}';
var startDtH = '${quiz.startDtH}';
var startDtM = '${quiz.startDtM}';
var success = '${quizLog.success}';
var complete = '${quiz.complete}';

var begin;
var now;

function nowTime() {
	var xmlHttpRequest;
	if(window.XMLHttpRequest){// code for Firefox, Mozilla, IE7, etc.
		xmlHttpRequest = new XMLHttpRequest();
	}else if(window.ActiveXObject){// code for IE5, IE6
		xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
	}else{
		return;
	}

	xmlHttpRequest.open('HEAD', window.location.href.toString(), false);
	xmlHttpRequest.setRequestHeader("ContentType", "text/html");
	xmlHttpRequest.send('');
	var serverDate = xmlHttpRequest.getResponseHeader("Date");
	
	now = new Date(serverDate);
}

$(function() {
	if(startDtType == '오후') {
		switch(startDtH) {
		case '1':
			startDtH = '13';
			break;
		case '2':
			startDtH = '14';
			break;
		case '3':
			startDtH = '15';
			break;
		case '4':
			startDtH = '16';
			break;
		case '5':
			startDtH = '17';
			break;
		case '6':
			startDtH = '18';
			break;
		case '7':
			startDtH = '19';
			break;
		case '8':
			startDtH = '20';
			break;
		case '9':
			startDtH = '21';
			break;
		case '10':
			startDtH = '22';
			break;
		case '11':
			startDtH = '23';
			break;
		default:
			startDtH = '13';
			break;
		}
	}
	
	if(startDtType == '정오') {
		startDtH = '12';
	}
	
	var dayArr = startYmd.split('-');
	
	var st_dt = startYmd + ' ' + startDtH + ':' + startDtM + ':00';
	
	
	var dday = new Date(st_dt);
	nowTime();
		
	if(now < dday) {
		console.log('시작전');
	}
	else {
		//console.log('진행중');
		if(userLv != '99' && (success == 'N' || success == 'Y' || success == '' || complete == 'Y')) {
			//self.close();
			location.href = '/';
		}
	}
	
	begin = dday;
	
	getTime();
	
	//getTotalGameAlpay();
});

function addComma(num) {
	return (num * 1).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}
function getTotalGameAlpay() {
	$('#total_al').text('');
	var uid = '${uid}';
	$.ajax({
		type: 'post',
		url: '/quiz/getTotalGameAlpay',
		data: { 'uid' : uid },
		dataType: 'json',
		crossDomain: true,
		success: function (r) {
			var total = 0;
			
			if(r.result != '') {
				total = parseInt(r.result);
			}
			
			$('#total_al').text(addComma(total));
			
			window.setTimeout("getTime(); getTotalGameAlpay();", 1000);
		},
		error: function (r, textStatus, err) {
			//if (r.responseText && r.responseText.substr(0, 13) == '<!--LOGOFF-->') { top.location = '/'; return; } //http://www.altong.com/
			console.log(r);
		},
		complete: function () {
			document.xhr = false;
		}
	});
}


function getTime() { 
	var uid = '${uid}';
	var dday = begin;
	
	nowTime();
	
	// 원하는 날짜, 시간 정확하게 초단위까지 기입.
	var distance = dday.getTime() - now.getTime();
	
	if(distance > 0) {
		days = (dday - now) / 1000 / 60 / 60 / 24; 
		daysRound = Math.floor(days); 
		hours = (dday - now) / 1000 / 60 / 60 - (24 * daysRound); 
		hoursRound = Math.floor(hours); 
		minutes = (dday - now) / 1000 /60 - (24 * 60 * daysRound) - (60 * hoursRound); 
		minutesRound = Math.floor(minutes); 
		seconds = (dday - now) / 1000 - (24 * 60 * 60 * daysRound) - (60 * 60 * hoursRound) - (60 * minutesRound); 
		secondsRound = Math.round(seconds); 
	
		if(daysRound > 0) {
			$('#counter0').css('display', 'block');
			document.getElementById("counter0").innerHTML = daysRound + '일';	
		}
		else {
			$('#counter0').css('display', 'none');
		}
		if(hoursRound > 0) {
			$('#counter1').css('display', 'block');
			document.getElementById("counter1").innerHTML = hoursRound + '시간';	
		}
		else {
			$('#counter1').css('display', 'none');
		}
		if(minutesRound > 0) {
			$('#counter2').css('display', 'block');
			document.getElementById("counter2").innerHTML = minutesRound + '분';
		}
		else {
			$('#counter2').css('display', 'none');
		} 
		document.getElementById("counter3").innerHTML = secondsRound + '초'; 
		//console.log('distance : ' + distance);
		
		if(distance > 180000) {
			$('#req').css('display', 'none');
		}
		
		if(hoursRound == 0 && minutesRound == 3 && secondsRound == 0) {
			$('#req').css('display', 'block');
		}
	
		newtime = window.setTimeout("getTime();", 1000);
	}
	else {
		$('#req').css('display','none');
		$('#msg').css('display','block');
		var logCnt = '${logCnt}';
		if(parseInt(logCnt) > 0) {
			$('#counter0').css('display', 'none');
			$('#counter1').css('display', 'none');
			$('#counter2').css('display', 'none');
			$('#counter3').css('display', 'none');
			
			//userLv != '99' && 
			if(now <= dday) {
				$('#quizTm').css('display', 'none');
				location.href = '/quiz/game?uid=' + uid + '&ord=1';
			}
			else {
				//alert('게임 입장 시간 초과!!!');
			}
		}
	}
}

function reqQuiz() {
	var myAlmoney = '${myAlmoney}';
	var attendAlmoney = '${quiz.attendAlmoney}';
	
	if(parseInt(attendAlmoney) <= parseInt(myAlmoney)) {
		var uid = '${uid}';
		$.ajax({
			type: 'post',
			url: '/quiz/quizGameReq',
			data: { 'uid' : uid },
			dataType: 'json',
			crossDomain: true,
			success: function (r) {
				//console.log('result : ' + r.result);
				if(r.result == 'Y') {
					alert('[${quiz.subject}(${quiz.step})] 에 정상적으로 참가신청 되었습니다.\n감사합니다.');
					location.reload();
				}
				else {
					alert(r.result);
					//self.close();
					location.href = '/';
				}
			},
			error: function (r, textStatus, err) {
				//if (r.responseText && r.responseText.substr(0, 13) == '<!--LOGOFF-->') { top.location = '/'; return; } //http://www.altong.com/
				console.log(r);
			},
			complete: function () {
				document.xhr = false;
			}
		});
	}
	else {
		alert('[${quiz.subject}(${quiz.step})]의 참가비알은 ' + addComma(attendAlmoney) + ' 입니다.\n 가지고 계신 알이 참가비알보다 적어서 퀴즈 게임에 참여가 불가합니다.');
		location.reload();
	}
}

function reqCancelQuiz() {
	var uid = '${uid}';
	$.ajax({
		type: 'post',
		url: '/quiz/quizGameReqCancel',
		data: { 'uid' : uid },
		dataType: 'json',
		crossDomain: true,
		success: function (r) {
			//console.log('result : ' + r.result);
			if(r.result == 'Y') {
				alert('[${quiz.subject}(${quiz.step})]의 게임 참가신청이 취소 되었습니다.\n감사합니다.');
				location.reload();
			}
			else {
				alert(r.result);
				//self.close();
				location.href = '/';
			}
		},
		error: function (r, textStatus, err) {
			//if (r.responseText && r.responseText.substr(0, 13) == '<!--LOGOFF-->') { top.location = '/'; return; } //http://www.altong.com/
			console.log(r);
		},
		complete: function () {
			document.xhr = false;
		}
	});
}
</script>
</head>
<body>
<%-- 총상금알 : <span id="total_al"><fmt:formatNumber value="${quiz.admAlmoney + (quiz.attendAlmoney * reqLogCount) + quiz.carryoverMoney}" pattern="#,###" /></span><br/> --%>
${quiz.subject}(${quiz.step}) 참여 신청 페이지<br/><br/>

<div id="quizTm">
퀴즈 게임 시작까지<br/><br/>
<div id='counter0' style="display:none;"></div>
<div id='counter1' style="display:none;"></div>
<div id='counter2' style="display:none;"></div>
<div id='counter3'></div>
<br/>남았습니다.	
</div>			
<br/><br/>

<div id="req">
퀴즈게임으 참가비알은 <fmt:formatNumber value="${quiz.attendAlmoney}" pattern="#,###" />알 입니다.<br/><br/>
참가비알은 [확인]버튼을 클릭하시면 즉시 차감되며<br/>
이를 동의하지 않으실 경우는 게임에 참가하실 수 없습니다.<br/><br/>
게임 참가에 동의 하시나요?
<br/><br/>
<c:if test="${logCnt == 0}"><a href="javascript:void(0);" onclick="javascript:reqQuiz()">확인</a></c:if> <c:if test="${logCnt > 0}"><a href="javascript:void(0);" onclick="javascript:reqCancelQuiz()">참가 취소</a></c:if>
</div>



<div id="msg" style="display:none;">종료</div>
</body>
</html>