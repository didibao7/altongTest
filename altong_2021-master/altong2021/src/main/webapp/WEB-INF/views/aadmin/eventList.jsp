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
		<form name="frm" method="post" onSubmit="return false;"><!--  enctype="multipart/form-data" -->
			<table width="100%" border="0" align="center"  cellpadding="0" cellspacing="0">
				<tr>
					<td width="150">
						이벤트 배너 : <input type="file" name="BannerImg">
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
					<td width="400">
						질문 고유 번호 :
						<input type="text" name="QuestionSeq" id="urlInput" class="input_textarea" style="width:300px;height:28px;" > 
					</td>
					<td>
						<button type="button" class="btn btn-default" onClick="javascript:goSubmit('frm','/aadmin/eventSave','ifrm');">등록하기</button><br>
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

	</div>
</div>
<iframe name="ifrm" width="100%" height="0" frameborder="0"></iframe>
</body>