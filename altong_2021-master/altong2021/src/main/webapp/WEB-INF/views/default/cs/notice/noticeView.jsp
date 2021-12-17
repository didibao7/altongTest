<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.altong.web.logic.util.CommonUtil"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<%
String msg_0223 = CommonUtil.getLangMsg(request, "msg_0223");

pageContext.setAttribute("msg_0223", msg_0223);
%>
<head>
<link rel="stylesheet" href="/pub/default/cs/customerService/customer.css?ver=1.2">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.6">

<link rel="stylesheet" href="/pub/css/languages_common.css?ver=1.3">
<style>
.language_icon {
	top: 10px;
	position: relative;
	cursor: default;
}
.answer_lang {
	float: right;
	cursor: default;
}
</style>
</head>
<body>
<script type="text/javascript" src="/Common/js_new/default/languages.js?ver=1.6"></script>
<script>
$(function(){
	$('.language_icon img').click(function(e){
		//번역 유무에 따라서
		//번역이 없는 경우 기계 번역 실행후 결과를 가져와서 tSeq 를 가져온다.
		//번역이 있는 경우 사람번역이 있으면 사람번역의 tSeq 를 가져온다. 
        var contentSeq = $(this).attr('data-seq');
		var contentType = "N";
        //console.log('contentSeq : ' + contentSeq);
        //alert('서비스 준비중입니다!');
        
        var qt_seq = $('#qt_' + contentSeq).val();
        
        if(qt_seq == '') {
	        $.ajax({
				type: 'post',
				url: '/translate/trans',
				data: { 'contentSeq' : contentSeq, 'contentType' : contentType },
				dataType: 'json',
				crossDomain: true,
				success: function (r) {
					//console.log('r.result : ' + r.result);
					switch (r.result) {
						case 'Y':
							var tSeq = r.arr.tSeq;
							var tTitle = r.arr.tTitle;
							var tContents = r.arr.tContents;
							
							if(contentType == 'N') {
								//tSeq 설정
								//tTitle 만교체(span id="que_") / qt_
								tTitle = tTitle.replace(/(<([^>]+)>)/ig,"");
								
								$('#que_' + contentSeq).html(tTitle);
								$('#cont_' + contentSeq).html(tContents);
								$('#qt_' + contentSeq).val(tSeq);
							}
							
							break;
						case 'N':
							alert('${msg_0223}');
							return false;
							break;
						default:
							break;
					}
				},
				error: function (r, textStatus, err) {
					console.log(r);
				},
				complete: function () {
					document.xhr = false;
				}
			});
        
        }
        else {
        	//원문 로딩
			$.ajax({
				type: 'post',
				url: '/translate/getNoticeOrgTitle',
				data: { 'contentSeq' : contentSeq},
				dataType: 'json',
				crossDomain: true,
				success: function (r) {
					//console.log('r.result : ' + r.result);
					switch (r.result) {
						case 'Y':
							var tTitle = r.arr.tTitle;
							var tContents = r.arr.tContents;
							
							$('#que_' + contentSeq).html(tTitle);
							$('#cont_' + contentSeq).html(tContents);
							$('#qt_' + contentSeq).val(''); // 번역문 코드 제거
							
							break;
						case 'N':
							alert('${msg_0223}');
							return false;
							break;
						default:
							break;
					}
				},
				error: function (r, textStatus, err) {
					console.log(r);
				},
				complete: function () {
					document.xhr = false;
				}
			});
        }
        
        e.stopPropagation();
        e.preventDefault();
    });
});
</script>
<script id="noticeViewTemplate" type="text/x-template">
	<div class="atm_av_el">
<c:if test="${lang != 'ko'}">
		<input type="hidden" id="qt_{seq}" name="qtSeq" value=""/>
		<div class="language_icon">
			<img class="answer_lang" src="/Common/images/language.svg" alt='<spring:message code="msg_0362"/>' data-seq="{seq}">
		</div>
</c:if>
		<div class="atm_av_c9" id="que_{seq}">
			{title}
		</div>
		<p class="atm_av_c10">{editTimeReg} &nbsp;
			<span class="atm_whitespace">
				<img src="/Common/images/icon_view.png" class="atm_viewicon">{viewCount}
			</span>
		</p>

		<p class="atm_av_c6" id="cont_{seq}">{contents}</p>

	</div>
</script>
<script id="noticeTemplate" type="text/x-template">
	<div class="atm_ranka12_el atm_border" onclick="location.href='/default/cs/notice/noticeView?Seq={seq}&Page=${page}';">
		<div class="atm_ranka12_c5">
			{title}
		</div>
        <p class="atm_ranka12_c7">{editTimeReg} &nbsp;
			<span class="atm_whitespace">
			<img src="/Common/images/icon_view.png" class="atm_viewicon">{viewCount}
			</span>&nbsp;
		</p>
	</div>
</script>
<script id="pagingButton" type="text/x-template">
	<p class="atm_boardnavi_el" onclick="location.href='/default/cs/notice/notice?Page={Number}'">{Number}</p>
</script>
<%@ include file="/pub/menu/topMenu.jsp"%>
<div class="atm_ranka12">
	<div class="center">
		<div class="atm_edittop_ttbar" >
			<div class="atm_edittop_ttbar_pc" >
				<h1 class="atm_edittop_c1"><spring:message code="msg_0185"/></h1>
			</div>
		</div>
		<div class="atm_av_el list">
			<ul>
				<li style="cursor:default;">
				</li>
				<li>
					<a href="javascript:history.back();">
						<img src="/pub/default/cs/notice/noticeView/list_icon_c.svg">
					</a>
					<span><spring:message code="msg_0279"/></span>
				</li>
				<li style="cursor:default;">
				</li>
			</ul>
		</div>
	</div>
</div>
<div id="top_btn">
	<a href="javascript:void(0);">
		<span>
			<img src="/Common/images/top_btn_arrow.svg" alt="top_btn">
		</span>
	</a>
</div>
<script charset='euc-kr'>
	(function() {
		var htmlCode = $('#noticeViewTemplate').html();
		var seq = '${seq}';
		var noticeData = $.parseJSON(
			$.ajax({
				async: false,
				url: "/default/cs/notice/getNotice?Seq=" + seq,
				contentType: "application/x-www-form-urlencoded; charset=utf-8"
			}).responseText
		);
		noticeData[0]['editTimeReg'] = noticeData[0]['editTimeReg'].substring(0, 10);

		for(let key in noticeData[0]) {
			// [추가(2018.03.17): 김현구] HTML Image TAG 표시되게 처리
			if ( key == 'contents' ) {
				noticeData[0][key] = noticeData[0][key].replaceAll('&lt;', '<');
				noticeData[0][key] = noticeData[0][key].replaceAll('&gt;', '>');
				noticeData[0][key] = noticeData[0][key].replaceAll('&quot;', '\"');
				noticeData[0][key] = noticeData[0][key].replaceAll('<img', '<img width=100%');
				//alert(noticeData[0][key]);
			}
			htmlCode = htmlCode.replaceAll('{' + key + '}', noticeData[0][key])
		}
		$('.list').before(htmlCode)
     })();
 </script>
</body>
</html>