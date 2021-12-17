<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/Common/include/headerAdmin.jsp" %>
<body>
<script>
$(document).ready(function(){
	$.datepicker.regional['ko'] = {
		closeText: '닫기',
		prevText: '이전달',
		nextText: '다음달',
		currentText: '오늘',
		monthNames: ['1월(JAN)','2월(FEB)','3월(MAR)','4월(APR)','5월(MAY)','6월(JUN)','7월(JUL)','8월(AUG)','9월(SEP)','10월(OCT)','11월(NOV)','12월(DEC)'],
		monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		dayNames: ['일','월','화','수','목','금','토'],
		dayNamesShort: ['일','월','화','수','목','금','토'],
		dayNamesMin: ['일','월','화','수','목','금','토'],
		weekHeader: 'Wk',
		dateFormat: 'yy-mm-dd',
		firstDay: 0,
		isRTL: false,
		showMonthAfterYear: true,
		yearSuffix: ''};
	$.datepicker.setDefaults($.datepicker.regional['ko']);

	$("#datepicker1").datepicker({
		numberOfMonths: 1,
		onSelect: function(selected) {
			$("#datepicker2").datepicker("option","minDate", selected)
		}
	});

	$("#datepicker2").datepicker({
		numberOfMonths: 1,
		onSelect: function(selected) {
			$("#datepicker1").datepicker("option","maxDate", selected)
		}
	});
});
</script>

<div id="wrapper">
	<div id="header_wrapper">
	</div>
	<div id="M_wrapper">
	<%@ include file="/Common/include/menuAdmin.jsp" %>
		<br>
		<div class="board_input8">
<form name="frm" method="post" onSubmit="return false;" enctype="multipart/form-data">
			<table width="100%" border="0" align="center"  cellpadding="0" cellspacing="0">
				<tr>
					<td width="250">
						카테고리 : 
						<select name="Section1" class="input_select">
							<option value="0">전체</option>
						<c:forEach var="cd" items="${section}" varStatus="status">
							<option value="${cd.code}">${cd.codeName}</option>
						</c:forEach>
						</select>
					</td>
					<td width="150">
						광고이미지 : <input type="file" name="ADFile">
					</td>
					<td width="450">
						게시기간 :
						<input type="text" name="PeriodStart" id="datepicker1" class="input_textarea" style="width:100px;height:28px;" readonly> ~ 
						<input type="text" name="PeriodEnd" id="datepicker2" class="input_textarea" style="width:100px;height:28px;" readonly>
					</td>
					<td width="200">
						사용여부 : 
						<select name="FlagUse" class="input_select" style="width:80px;">
							<option value="Y">사용</option>
							<option value="N">미사용</option>
						</select>
					</td>
					<td width="200">
						광고여부 : 
						<select name="FlagAd" class="input_select" style="width:80px;">
							<option value="1">비광고</option>
							<option value="0">광고</option>
						</select>
					</td>
					<td width="400">
						Url :
						<input type="text" name="Url" id="urlInput" class="input_textarea" style="width:300px;height:28px;" > 
					</td>
					<td>
						<button type="button" class="btn btn-default" onClick="javascript:goSubmit('frm','/aadmin/adSave','ifrm');">등록하기</button><br>
					</td>
				</tr>
				<tr>
					<td colspan="5">
						광고에 대한 카테고리, 이미지(동영상), 게시기간, 사용설정을 관리 (테이블명 : T_AD)
					</td>
				</tr>
			</table>
</form>
		</div>
		<div id="list">
<form name="frm_sch" method="post" onSubmit="return false;">
<input type="hidden" name="src_Target" value="${curPageName}">
<input type="hidden" name="pg" value="${n_curpage}">
			<p>전체 광고 : ${n_trec} 건</p>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="boardList01">
				<tr>
					<th width="8%">수정|삭제</th>
					<th width="30%">광고이미지<br></th>
					<th width="5%">카테고리</th>
					<th width="3%">종류</th>
					<th width="15%">게시시작일</th>
					<th width="15%">게시종료일</th>
					<th width="10%">알 차감여부</th>
					<th width="5%">노출 수</th>
					<th width="5%">열람 수</th>
					<th width="5%">클릭 수</th>
				</tr>
			<c:forEach var="ad" items="${adList}" varStatus="status">
				<c:set var="adStatus" value="미차감"/>
				<c:if test="${ad.flagAd != '0'}"><c:set var="adStatus" value="차감"/></c:if>
				<tr style='cursor:pointer;' onmouseover='this.style.background="#FFE0B3";' onmouseleave='this.style.background="#FFFFFF";'>
				<td style="border-bottom:1px solid #aaa;"><a href="adView.asp?Seq=${ad.seq}">수정</a>/ <a href="javascript:goSubmit('frm_sch', '/aadmin/adDel?Seq=${ad.seq}', 'ifrm');">삭제</A></td>
					<td style="border-bottom:1px solid #aaa;"><img src="/UploadFile/AD/${ad.adFile}" style="max-width:400px;max-height:100px;"></td>
					<td style="border-bottom:1px solid #aaa;"><c:choose><c:when test="${ad.section1 < 1}">전체</c:when><c:otherwise>${sect.get(ad.section1)}</c:otherwise></c:choose></td>
					<td style="border-bottom:1px solid #aaa;">${ad.adFileExt}</td>
					<td style="border-bottom:1px solid #aaa;">${ad.periodStart}</td>
					<td style="border-bottom:1px solid #aaa;">${ad.periodEnd}</td>
					<td style="border-bottom:1px solid #aaa;">${adStatus}</td>					
					<td style="border-bottom:1px solid #aaa;"><c:choose><c:when test="${ad.viewCount != null}">${ad.viewCount}</c:when><c:otherwise>0</c:otherwise></c:choose></td>					
					<td style="border-bottom:1px solid #aaa;"><c:choose><c:when test="${ad.payCount != null}">${ad.payCount}</c:when><c:otherwise>0</c:otherwise></c:choose></td>					
					<td style="border-bottom:1px solid #aaa;"><c:choose><c:when test="${ad.clickCount != null}">${ad.clickCount}</c:when><c:otherwise>0</c:otherwise></c:choose></td>
				</tr>
			</c:forEach>

			</table>
			<p class="paging">
			<span class="num">
			<!-- paging -->
			${paging}
			<!-- paging// -->
			</span>
			</p>
			<br><br>
</form>
		</div>
	</div>
</div>
<iframe name="ifrm" width="100%" height="0" frameborder="0"></iframe>
</body>