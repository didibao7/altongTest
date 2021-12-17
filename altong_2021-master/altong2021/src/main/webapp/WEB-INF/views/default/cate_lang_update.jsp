<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.altong.web.logic.util.CommonUtil"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp"%>
<%@ page import="org.json.simple.JSONObject"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 전용 페이지 - 카테고리 외국어 적용</title>

<script>
$(function() {
	$("#btn").click(function(){
	    var form = $("#excelUpForm")[0];

	    var data = new FormData(form);
	    
	    var tbl = $("#tbl option:selected").val();
	    var col = $("#col option:selected").val();

	    if(tbl == '') {
	    	alert('테이블을 선택하여 주세요.');
	    	return false;
	    }
	    else if(col == '') {
	    	alert('컬럼을 선택하여 주세요.');
	    	return false;
	    }
	    else if($('#excelFile').val() == '') {
	    	alert('파일을 선택하여 주세요.');
	    	return false;
	    }
	    else {
		    $.ajax({
		       enctype:"multipart/form-data",
		       method:"POST",
		       url: '/default/excelCateUpdate',
		       processData: false,   
		       contentType: false,
		       cache: false,
		       data: data,
		       success: function(result){  
		           alert("업로드 성공!!");
		       }
		    });
	    }
	});
});
</script>
</head>
<body>
<c:if test="${ userSeq ne 0 }">
	<c:if test="${ userLv eq 99 }">
		엑셀업로드 : <br/>
		<form name="excelUpForm" id="excelUpForm" enctype="multipart/form-data" method="POST" action="./excelDown.do">
			<select id="tbl" name="tbl">
				<option value="">테이블 선택</option>
				<option value="T_SECTION_T">T_SECTION_T</option>
				<option value="T_SECTION2">T_SECTION2</option>
				<option value="T_SECTION3_1">T_SECTION3_1</option>
				<option value="T_SECTION4">T_SECTION4</option>
				<option value="T_SECTION5$">T_SECTION5$</option>
			</select>
			<br/>
			<select id="col" name="col">
				<option value="">컬럼 선택</option>
				<option value="codeName_en">codeName_en</option>
				<option value="codeName_zh">codeName_zh</option>
			</select>
		    <input type="file" id="excelFile" name="excleFile" value="엑셀 업로드" />
		    <input type="button" id="btn" value="전송" />
		</form>
	</c:if>
</c:if>
</body>
</html>