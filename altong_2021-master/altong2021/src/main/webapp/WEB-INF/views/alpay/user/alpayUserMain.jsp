<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jasp.util.*" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<body>
<script>
function fAjax(param)
{
	if (document.xhr) document.xhr.abort();

	document.xhr = $.ajax({
		type : 'post',
		url: '/alpay/user/alpayUserMainAjax',
		data: param,
		dataType: 'json',
		success: function(r) {
			//console.log(r.result);
			switch (r.result)
			{
				case 'SET_HongUni':
				case 'SET_SeoulUni':
				case 'SET_Guro':
				case 'SET_GangNam':
				{
					location = 'alpayUser';
					break;
				}
				default:
					if (r.result) alert(r.result);
					break;
			}
		},
		error: function(r, textStatus, err){
			if (r.responseText && r.responseText.substr(0,13) == '<!--LOGOFF-->') {top.location='${MORMAL_SEND_URL}';return;}
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
	<section class="index">
		<p class="sub_text"><i class="material-icons">room</i> 이용 지역을 선택해주세요.</p>
		<div class="index_btn">
			<p><a href="javascript:void(0)" onClick="fAjax('ACT=SET_Guro')">구로디지털단지 <span>&#8640;</span></a></p>
			<p class="disabled"><a href="javascript:void(0)" onClick="//fAjax('ACT=SET_HongUni')">홍대입구 <span>&#8640;</span></a></p>
			<p class="disabled"><a href="javascript:void(0)" onClick="//fAjax('ACT=SET_GangNam')">강남/역삼/선릉 <span>&#8640;</span></a></p>
			<p class="disabled"><a href="javascript:void(0)" onClick="//fAjax('ACT=SET_SeoulUni')">서울대입구 <span>&#8640;</span></a></p>
		</div>
		<!--<br><p class="sub_text" style="color:#777;font-size:14px">(지금은 알Pay 베타 서비스 기간입니다.)</p>-->
	</section>

</body>