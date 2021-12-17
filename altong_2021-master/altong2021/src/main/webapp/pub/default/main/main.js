document.timerID = Object();

$(document).ready(function() {
	getQuiz();
});
function fAnimAlmoney(dom, fixedAl) {
	$('#' + dom).text(parseInt(Math.random() * 10) + fixedAl);
	document.timerID[dom] = setTimeout(function() {
		fAnimAlmoney(dom, fixedAl);
	}, 30);
}
function fSetAlmoney(AlmoneyAll, AlmoneyMy, x) {
	if (!AlmoneyAll)
		AlmoneyAll = '0';
	if (!AlmoneyMy)
		AlmoneyMy = '0';

	if (document.timerID['domAlmoneyAll'])
		clearTimeout(document.timerID['domAlmoneyAll']);

	if (document.timerID['domAlmoneyMy'])
		clearTimeout(document.timerID['domAlmoneyMy']);

	x++;
	var loop = false;

	if (AlmoneyAll.length >= x) {
		$('#domAlmoneyAll').text(
				AlmoneyAll.substr(AlmoneyAll.length - x, x - 2));
		if (AlmoneyAll.length > x) {
			fAnimAlmoney('domAlmoneyAll', $('#domAlmoneyAll').text());
			loop = true;
		}
	}
	//console.log('AlmoneyMy.length >= x : '+ (AlmoneyMy.length >= x));
	if (AlmoneyMy.length >= x) {
		$('#domAlmoneyMy').text(
				AlmoneyMy.substr(AlmoneyMy.length - x,
						((AlmoneyMy.length <= 10) ? x : x - 2)));
		if (AlmoneyMy.length > x) {
			fAnimAlmoney('domAlmoneyMy', $('#domAlmoneyMy').text());
			loop = true;
		}
	}

	if (loop)
		setTimeout(function() {
			fSetAlmoney(AlmoneyAll, AlmoneyMy, x);
		}, 200);
}
function getQuiz() {
	var logCnt = 0;
	$.ajax({
		type: 'post',
		url: '/quiz/getQuizGame',
		dataType: 'json',
		crossDomain: true,
		success: function (r) {
			//console.log('result : ' + r.result);
			var disp = $('#quizView').css('display');
			
			if(r.result != '0') {
				if(disp == 'none') {
					$('#quizView').css('display', 'block');
				}
				
				uid = r.arr.uid;
				step = r.arr.step;
				subject = r.arr.subject;
				startYmd = r.arr.startYmd;
				startDtType = r.arr.startDtType;
				startDtH = r.arr.startDtH;
				startDtM = r.arr.startDtM;
				logCnt = r.arr.logCnt;
				
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
				//console.log('startDtH : ' + startDtH);
				var dayArr = startYmd.split('-');
				
				var st_dt = startYmd + ' ' + startDtH + ':' + startDtM + ':00';
				
				
				var dday = new Date(st_dt);
				nowTime();
				
				
				var ddayTime = dday.getTime();
				var nowTimes = now.getTime();
				var distance = ddayTime - nowTimes;
				
				//console.log(dday.getTime() + ':' + now.getTime());
				
				if(nowTimes < ddayTime) {
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
							$('#counter0').css('display', 'inline-block');
							document.getElementById("counter0").innerHTML = daysRound + '일';
						}
						else {
							$('#counter0').css('display', 'none');
						}
						if(hoursRound > 0) {
							$('#counter1').css('display', 'inline-block');
							document.getElementById("counter1").innerHTML = hoursRound + '시간';	
						}
						else {
							$('#counter1').css('display', 'none');
						}
						if(minutesRound > 0) {
							$('#counter2').css('display', 'inline-block');
							document.getElementById("counter2").innerHTML = minutesRound + '분';
						}
						else {
							$('#counter2').css('display', 'none');
						} 
						document.getElementById("counter3").innerHTML = secondsRound + '초';
					
						newtime = window.setTimeout("getQuiz();", 1000);
					}
					else {
						if(disp == 'block') {
							$('#quizView').css('display', 'none');
						}
					}
				}
				else if(nowTimes == ddayTime) {
					if(parseInt(logCnt) >  0) {
						//window.name = "gsForm";						
						//var openGWin;
						//openGWin = window.open("/quiz/game?uid=" + uid + "&ord=1", "childForm", "width=800, height=500, resizable = no, scrollbars = no");
						location.href = "/quiz/game?uid=" + uid + "&ord=1";
					}
					
					$('#quizView').css('display', 'none');
				}
				else {
					$('#quizView').css('display', 'none');
				}
				
			}
			else {
				uid = '';
				$('#quizView').css('display', 'none');
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
function reqQuizGame() {
	if(uid != '') {
		//window.name = "gForm";
		//var openWin;
		//openWin = window.open("/quiz/start?uid="+uid, "childForm", "width=800, height=500, resizable = no, scrollbars = no"); 
		//openWin.focus();
		location.href = "/quiz/start?uid=" + uid;
	}
}
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