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
var uid = '${uid}';
var current = '${current}';
var max = '${max}';

$(function(){
	quizQuit();
});

function quizQuit() {
	$.ajax({
		type: 'post',
		url: '/quiz/quizDemoEnd',
		data: { 'uid' : uid, 'current' : current, 'max' : max },
		dataType: 'json',
		crossDomain: true,
		success: function (r) {
			//console.log('result : ' + r.result);
			switch (r.result) {
				case 'Y':
					self.close();
					break;
				case 'N':
					self.close();
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

</body>
</html>