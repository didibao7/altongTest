<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.altong.web.logic.util.CommonUtil"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp"%>
<head>
<!-- 상위 헤드 -->
<link rel="stylesheet" href="/pub/member/myQuestion/myQuestion.css?ver=1.9">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.3">

<link rel="stylesheet" href="/pub/css/myPage_lang.css">
<script src="/Common/js_new/default/languages.js"></script>

<script>
$(function(){
	$('.answer_lang').click(function(e){
		//번역 유무에 따라서
		//번역이 없는 경우 기계 번역 실행후 결과를 가져와서 tSeq 를 가져온다.
		//번역이 있는 경우 사람번역이 있으면 사람번역의 tSeq 를 가져온다. 
        var contentSeq = $(this).attr('data-seq');
		var contentType = "Q";
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
							
							if(contentType == 'Q') {
								//tSeq 설정
								//tTitle 만교체(span id="que_") / qt_
								tTitle = tTitle.replace(/(<([^>]+)>)/ig,"");
								
								$('#que_' + contentSeq).html(tTitle);
								$('#cont_' + contentSeq).html(tContents);
								$('#qt_' + contentSeq).val(tSeq);
							}
							else if(contentType == 'A') {
								//answerList 에서 상세 구현
							}
							else {
								//answerList 에서 상세 구현
							}
							
							break;
						case 'N':
							alert(getLangStr("jsm_0083"));
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
				url: '/translate/getQuestionOrgTitle',
				data: { 'contentSeq' : contentSeq},
				dataType: 'json',
				crossDomain: true,
				success: function (r) {
					//console.log('r.result : ' + r.result);
					switch (r.result) {
						case 'Y':
							var tTitle = r.arr.tTitle;
							var tContents = r.arr.tContents;
							
							tTitle = tTitle.replace(/(<([^>]+)>)/ig,"");
							
							$('#que_' + contentSeq).html(tTitle);
							$('#cont_' + contentSeq).html(tContents);
							$('#qt_' + contentSeq).val(''); // 번역문 코드 제거
							
							break;
						case 'N':
							alert(getLangStr("jsm_0083"));
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

function goAnswerList(seq) {
	var qtSeq = $('#qt_' + seq).val();
	var url = '/answer/answerList?Seq=' + seq + '&CurPageName=/member/myQuestion';
	
	if(qtSeq == '') {
		location.href = url;
	}
	else {
		location.href = url + '&qtSeq=' + qtSeq;
	}
}
</script>
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>

<div id="atm_myjjim_wrapper0">
	<!--wrapper start -->
	<form name="frm_sch" method="post" onSubmit="return false;">
	<input type="hidden" name="src_Target" value="${curPageName}">
	<input type="hidden" name="pg" value="${n_curpage}">
	
	<div class="atm_mycatebar1">
		<div class="center">
			<div class="atm_mycatebar1_pc">
				<p class="atm_mycatebar_c1">
					<c:choose>
						<c:when test="${viewFlag == 'N'}">
						<spring:message code="msg_0691"/>
						</c:when>
						<c:when test="${viewFlag == 'answered'}">
						<spring:message code="msg_0823"/>
						</c:when>
						<c:when test="${viewFlag == 'notAnswered'}">
						<spring:message code="msg_0824"/>
						</c:when>
						<c:otherwise>
						<spring:message code="msg_0894"/>
						</c:otherwise>
					</c:choose>
					<span class="atm_mycatebar_c2">${n_trec}</span><spring:message code="msg_0370"/>
				</p>
				<c:choose>
					<c:when test="${viewFlag == 'N'}">
					</c:when>
					<c:when test="${viewFlag == 'answered'}">
					</c:when>
					<c:when test="${viewFlag == 'notAnswered'}">
					</c:when>
					<c:otherwise>
					<div class="atm_mycatebar_box">
						<span><spring:message code="msg_0895"/></span>
						<select name="Section1" class="atm_mycatebar_sel" onChange="javascript:goSubmit('frm_sch','${curPageName}','_self');">
							<option value="" <c:if test="${section1 == '0'}">selected</c:if>><spring:message code="msg_0161"/></option>
						<c:forEach var="sec" items="${sectionVO}" varStatus="status">
							<c:set var="code" value="${sec.code}"/>
							<%
								String code = String.valueOf( pageContext.getAttribute("code") );
								String codeNm = CommonUtil.getLangMsg(request, "cate" + code);
							%>
							<option value="${sec.code}" <c:if test="${section1 == sec.code}">selected</c:if>><%=codeNm%></option>
						</c:forEach>
						</select>
					</div><!-- atm_mycatebar_box end -->
					</c:otherwise>
				</c:choose>
			</div><!--atm_mycatebar1_pc end -->
		</div><!-- center end -->
	</div><!--atm_mycatebar1 end -->
	
	<div class="atm_myjjim_con">
		<div class="center">
		<c:forEach var="que" items="${myQueList}" varStatus="status">
			<div class="atm_myjjim_el atm_border">
				<div class="atm_myjjim_c5" onClick="javascript:goAnswerList('${que.seq}');">
					<h3>Q.</h3>
					<c:set var="que_almoney" value="${que.almoney}" />
					<fmt:parseNumber var="que_almoney_2" type="number" value="${que_almoney}"></fmt:parseNumber>
					<p><span class="atm_icon_score<% 
								int usr_color = Integer.parseInt(String.valueOf(pageContext.getAttribute("que_almoney_2")));
								if (usr_color >= 5000) { %>_red<% } %>"><fmt:formatNumber value="${que.almoney}" pattern="#,###" /></span><span id="que_${que.seq}">${que.title}</span></p>
				</div>
				<p class="beefup_head" id="cont_${que.seq}" onClick="javascript:goAnswerList('${que.seq}');">${fn:substring(  fn:trim(que.contents.replaceAll("<.*?>","") ), 0, 280)}</p>
				<ul class="atm_myjjim_c7">
					<li>${que.dateReg}</li>
					<li><img src="/Common/images/icon_view.svg" class="viewicon"/><fmt:formatNumber value="${que.readCount}" pattern="#,###" /></li>
					<li><span>A</span><fmt:formatNumber value="${que.answerCount}" pattern="#,###" /></li>
				</ul>
				<input type="hidden" id="qt_${que.seq}" name="qtSeq" value=""/>
				<span class="atm_myJJim_c33"><img class="answer_lang" src="/Common/images/language.svg" alt="" data-seq="${que.seq}"></span>
			</div>
		</c:forEach>
		</div>
	</div><!--atm_myjjim_con end -->
	
	<div class="list_pasing">
		${paging}
	</div>
	</form>
	<iframe name="ifrm" width="100%" height="0" frameborder="0"></iframe>
	<!--wrapper end -->
</div><!-- atm_myjjim_wrapper0 end -->
<div id="top_btn">
    <a href="javascript:void(0);">
        <span>
            <img src="/Common/images/top_btn_arrow.svg" alt="top_btn">
        </span>
    </a>
</div>
<script src="/pub/member/myQuestion/myQuestion.js"></script>
</body>