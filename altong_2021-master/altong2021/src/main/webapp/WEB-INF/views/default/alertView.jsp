<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<body>
<script type="text/javascript" src="/Common/js/jquery.js"></script> 
<script>
$(document).ready(function(){
	var strMsg = '${strMsg}';
	var strUrl = '${strUrl}';
	//var strTarget = '${strTarget}';
	//console.log('strMsg : '+strMsg);
	//console.log('strUrl : '+strUrl);
	//console.log('strTarget : '+strTarget);
	
	setTimeout(
			goPage(strMsg, strUrl)
	, 1000);
});

function goPage(strMsg, strUrl) {
	if(strMsg != '') {
		alert(strMsg);
	}
	if(strUrl == 'back') {
		history.back();
	}
	else if(strUrl != '') {
		location.href = strUrl;
	}
}
</script>
</body>