<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/Common/CSS/jquery-ui.css">    
<script type="text/javascript" src="/Common/js/jquery.js"></script>    
<script type="text/javascript" src="/Common/js/jquery-ui.js"></script>    
<script>
/* 배너에서의 dday 는 https://mcatcher.github.io/2018/01/24/dday.html 참고 */
var userLv = '${userLv}';

var uid = '${uid}';
var ord = '${ord}';
var current = '${current}';
var max = '${max}';
var next = '${next}';
var startYmd = '${quiz.startYmd}';
var startDtType = '${quiz.startDtType}';
var startDtH = '${quiz.startDtH}';
var startDtM = '${quiz.startDtM}';
var success = '${quizLog.success}';
var complete = '${quiz.complete}';
var userLv = '${userLv}';

var begin;
var chkDay;
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

$(function(){
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
	
	var hh = parseInt(startDtH);
	var mm = parseInt(startDtM);
	 
	var dday = new Date(st_dt);
	
	nowTime();
	
	var today = now;
	
		
	if(today < dday) {
		console.log('시작전');
		if(userLv != '99') {
			self.close();
		}
	}
	else {
		console.log('진행중');
		if(userLv != '99' && (success == 'N' || success == 'Y' || success == '' || complete == 'Y')) {
			self.close();
		}
	}
	
	nowTime();
	var today2 = now;
	
	var rt1 = '${quest.rtime1}';
	var rt2 = '${quest.rtime2}';
	var st1 = '${quest.stime1}';
	var st2 = '${quest.stime2}';
	
	if(parseInt(rt1) > 0) {
		if(parseInt(rt2) > 0) {
			today2.setMinutes(today2.getMinutes() + parseInt(rt1));
			today2.setSeconds(today2.getSeconds() + parseInt(rt2)); 
		}
		else {
			today2.setMinutes(today2.getMinutes() + parseInt(rt1));
		}
	}
	else {
		today2.setSeconds(today2.getSeconds() + parseInt(rt2));
	}
	
	begin = today2;
	
	getTime();
});

function getTime() { 
	var dday = begin;
	//now = new Date();
	nowTime();
	
	// 원하는 날짜, 시간 정확하게 초단위까지 기입.
	var distance = dday.getTime() - now.getTime();
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
		document.getElementById("counter1").innerHTML = hoursRound + '시';	
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
	
	if(distance > 0) {
		newtime = window.setTimeout("getTime();", 1000);
	}
	else {
		chkTest();
	}
}

function chkTest() {
	chkDay = new Date();
	
	var st1 = '${quest.stime1}';
	var st2 = '${quest.stime2}';
	
	if(parseInt(st1) > 0) {
		if(parseInt(st2) > 0) {
			chkDay.setMinutes(chkDay.getMinutes() + parseInt(st1));
			chkDay.setSeconds(chkDay.getSeconds() + parseInt(st2)); 
		}
		else {
			chkDay.setMinutes(chkDay.getMinutes() + parseInt(st1));
		}
	}
	else {
		chkDay.setSeconds(chkDay.getSeconds() + parseInt(st2));
	}
	
	//몇초 후에 정답비교
	getResultData();
}

function nextQuizTime(view) {
	//now = new Date();
	nowTime();
	
	var distance = chkDay.getTime() - now.getTime();
	
	days = (chkDay - now) / 1000 / 60 / 60 / 24; 
	daysRound = Math.floor(days); 
	hours = (chkDay - now) / 1000 / 60 / 60 - (24 * daysRound); 
	hoursRound = Math.floor(hours); 
	minutes = (chkDay - now) / 1000 /60 - (24 * 60 * daysRound) - (60 * hoursRound); 
	minutesRound = Math.floor(minutes); 
	seconds = (chkDay - now) / 1000 - (24 * 60 * 60 * daysRound) - (60 * 60 * hoursRound) - (60 * minutesRound); 
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
		document.getElementById("counter1").innerHTML = hoursRound + '시';
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
		
	if(distance > 0) {
		newtime = window.setTimeout("nextQuizTime('"+view+"');", 1000);
	}
	else {
		if(view == 'Y') {
			if(parseInt(next) <= parseInt(max)) {
				location.href = '/quiz/game?uid=' + uid + '&ord=' + next + '&view=T';
			}
			else {
				$('#counter0').css('display', 'none');
				$('#counter1').css('display', 'none');
				$('#counter2').css('display', 'none');
				$('#counter3').css('display', 'none');
				//축하합니다 디자인 노출
				console.log('최후 승자 안내!');
			}
		}
		else {
			if(parseInt(next) <= parseInt(max)) {
				if(success != 'V') {
					if(confirm('계속 볼래요?')) {
						location.href = '/quiz/game?uid=' + uid + '&ord=' + next + '&view=V';
					}
					else {
						//중단
						quizStop();
					}
				}
				else {
					location.href = '/quiz/game?uid=' + uid + '&ord=' + next + '&view=V';
				}
			}
			else {
				console.log('최후 승자 안내!');
			}
		}
	}
}

function getResultData() {
	var queid = '${gameQue.queid}';
	
	var correct = 0;
	var items = document.getElementsByName("ano");
	for(var i = 0; i < items.length; i++) {
		if(document.getElementsByName("ano")[i].checked == true) { correct = document.getElementsByName("ano")[i].value; break;}
	}
	
	$.ajax({
		type: 'post',
		url: '/quiz/quizComplete',
		data: { 'uid' : uid, 'current' : current, 'correct' : correct, 'queid' : queid },
		dataType: 'json',
		crossDomain: true,
		success: function (r) {
			//console.log('result : ' + r.result);
			switch (r.result) {
				case 'Y':
					console.log('정답!!!');
					
					nextQuizTime('Y');
					
					break;
				case 'N':
					console.log('오답!!!');
					
					nextQuizTime('N');
					
					break;
				default:
					//if (r.result) alert(r.result);
					break;
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

function quizStop() {
	$('#counter0').css('display', 'none');
	$('#counter1').css('display', 'none');
	$('#counter2').css('display', 'none');
	$('#counter3').css('display', 'none');
	
	$.ajax({
		type: 'post',
		url: '/quiz/quizStop',
		data: { 'uid' : uid },
		dataType: 'json',
		crossDomain: true,
		success: function (r) {
			//console.log('result : ' + r.result);
			switch (r.result) {
				case 'Y':
					//종료처리
					console.log('종료 처리 디자인');
					
					break;
				case 'N':
					//
					
					break;
				default:
					//if (r.result) alert(r.result);
					break;
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

function quizEnd() {
	$('#counter0').css('display', 'none');
	$('#counter1').css('display', 'none');
	$('#counter2').css('display', 'none');
	$('#counter3').css('display', 'none');
	
	if(current < max) {
		if(confirm('게임에서 나기실 경우\n참여가 취소되며\n참가알은 반환되지 않습니다.\n그래도 나가시겠습니까?')) {
			quizQuit();
		}
	}
	else {
		quizQuit();
	}
}

function quizQuit() {
	$.ajax({
		type: 'post',
		url: '/quiz/quizEnd',
		data: { 'uid' : uid, 'current' : current, 'max' : max },
		dataType: 'json',
		crossDomain: true,
		success: function (r) {
			//console.log('result : ' + r.result);
			switch (r.result) {
				case 'Y':
					//종료처리
					console.log('종료 처리 디자인');
					
					//self.close();
					location.href = '/';
					break;
				case 'N':
					//self.close();
					location.href = '/';
					break;
				default:
					//if (r.result) alert(r.result);
					self.close();
					break;
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
총상금알 : <span id="total_al"><fmt:formatNumber value="${quiz.admAlmoney + (quiz.attendAlmoney * reqLogCount) + quiz.carryoverMoney}" pattern="#,###" /> 알</span><br/>
<input type="button" value=" 게임 나가기 " onclick="javascript:quizEnd();"/><br/><br/>

<br/>
Q : ${quest.quest}<br/>
<c:if test="${quest.hint != ''}">hint : ${quest.hint}<br/></c:if>
<c:if test="${quest.comment != ''}">해설 : ${quest.comment}<br/></c:if>

남은시간:
<div id='counter0' style="display:none;"></div>
<div id='counter1' style="display:none;"></div>
<div id='counter2' style="display:none;"></div>
<div id='counter3'></div>
<br/>
보기 :<br/>
<c:forEach var="ans" items="${ansList}" varStatus="status">
<input type="radio" name="ano" value="${ans.ano}" <c:if test="${quizLog.success != 'S'}">disabled</c:if>/>${ans.ano}. ${ans.answer}<br/>
</c:forEach>
<br/>

<div style="width:100%; text-align:center;">2020 Altong. all rights reserved.</div>
</body>
</html>