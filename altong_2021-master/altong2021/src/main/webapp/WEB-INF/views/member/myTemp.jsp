<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body id="atm_gray">
<style type="text/css">

ul,li {list-style-type:none;}
img,p,li {border:0;margin:0;padding:0; vertical-align:middle;}

/*img*/

/*p태그*/

.atm_myjjim_c1{ font-size:14px; font-weight:bold; letter-spacing:-1px;  color:#333333; display:inline-block;}
.atm_myjjim_c2{ white-space:nowrap; }
.atm_myjjim_c3{ font-size:11px; font-weight:bold; letter-spacing:-0.5px; padding:5px 7px; color:#595959; border:#949494 1px solid; display:inline-block; background-color:#fff; }
.atm_myjjim_c4{ font-size:12px; font-weight:bold; letter-spacing:-0.5px;                  color:#595959; display:inline-block;  }
.atm_myjjim_c5{ font-size:14px; font-weight:bold; letter-spacing:-0.5px;  color:#333333; line-height:17px;}
.atm_myjjim_c6{ font-size:12px; font-weight:normal; letter-spacing:0px;  color:#333333; line-height:20px; margin-top:5px;}
.atm_myjjim_c7{ font-size:12px; font-weight:normal; letter-spacing:0px;  color:#9e9e9e; display:inline-block; margin-top:5px; width:100%;}

/*본문부분*/
#atm_myjjim_wrapper0{ width:100%; padding:0 0 0 0; background-color:#f2f2f2;}

.atm_myjjim_ttbar1{  background-color:#e8e8e8; padding:10px 0px; text-align:center;  }


.atm_myjjim_con{ padding:4px 0 11px; text-align:left; position:relative; font-size:0; }
.atm_myjjim_el{ position:relative; background-color:#fff; margin-top:7px; padding:15px 14px; border-bottom:#bbbbbb 1px solid; cursor:pointer;}

.atm_temp_tab2 {
    background-color: #ffffff;
    padding: 0px 0px;
    text-align: center;
    position: relative;
    border-bottom: #d5d5d5 1px solid;
    font-size: 0;
}

.atm_temp_tab {
    width: 30%;
    font-size: 12px;
    font-weight: bold;
    letter-spacing: -0.5px;
    padding: 8px 0 8px 0;
    color: #8f8f8f;
    border-bottom: #ffffff 0px solid;
    left: 2%;
    bottom: 0px;
    display: inline-block;
}

.temp_tab_on {
	color: #ff5001;
}

@media screen and (min-width: 800px) {
#atm_myjjim_wrapper0{ text-align:center;}
.atm_myjjim_con{ width:800px; display:inline-block; }
.atm_mycatebar1{ text-align:center; }
.atm_mycatebar1_pc{ width:800px; display:inline-block; text-align:left; position:relative; }
.atm_mycatebar_sel{ top:-2px;}
.atm_temp_tab2_pc {
    width: 800px;
    display: inline-block;
    position: relative;
}
.atm_temp_tabc4_on {
    font-size: 14px;
}
.atm_temp_tabc_line2 {
    font-size: 14px;
}
.atm_temp_tabc4 {
    font-size: 14px;
}
}


</style>
<script language="javascript">
// [추가(2017.12.20): 김현구] 질문 확인 후, 삭제 처리
function fnDelete(FlagSelect, QuestionSeq,AnswerSeq) {
    var ans1 = confirm("임시저장 글을 삭제하시겠습니까?");
    if ( ans1 == false ) return;
	
    //goSubmit('frm_sch', '/Question/QuestionDelete.asp?QuestionSeq=' + QuestionSeq + '&Flag=T', 'ifrm');
	document.location.href = "/question/questionDelete?FlagSelect=" + FlagSelect + "&QuestionSeq=" + QuestionSeq + "&AnswerSeq=" + AnswerSeq + "&Flag=T"
}
</script>

<div id="atm_wrapper" style="overflow-x:hidden;">
<%@ include file="/Common/include/MenuSub.jsp" %>

	<div id="atm_myjjim_wrapper0">
	<!--wrapper start -->
	<form name="frm_sch" method="post" onSubmit="return false;">
	<input type="hidden" name="src_Target" value="${curPageName}">
	<input type="hidden" name="pg" value="${n_curpage}">
		
		<div class="atm_mycatebar1">
			<div class="atm_mycatebar1_pc">
				<p class="atm_mycatebar_c1">내 임시저장 <span class="atm_mycatebar_c2">${n_trec}</span> 개</p>
				<!--
				<select name="FlagSelect" class="atm_mycatebar_sel" onChange="javascript:goSubmit('frm_sch','${curPageName}','_self');">
					<option value="Q" <c:if test="${flagSelect == 'Q'}">selected</c:if>>질문</option>
					<option value="A" <c:if test="${flagSelect == 'A'}">selected</c:if>>답변</option>
				</select>
				-->
			</div>
		</div><!--atm_mycatebar1 end -->
	
		<div class="atm_temp_tab2">
			<div class="atm_temp_tab2_pc">
				<p class="atm_temp_tab <c:if test="${flagSelect == 'ALL'}">temp_tab_on</c:if>" onclick="location.href='/member/myTemp?FlagSelect=ALL';">전체</p>
				<p class="atm_temp_tab <c:if test="${flagSelect == 'Q'}">temp_tab_on</c:if>" onclick="location.href='/member/myTemp?FlagSelect=Q';">질문</p>
				<p class="atm_temp_tab <c:if test="${flagSelect == 'A'}">temp_tab_on</c:if>" onclick="location.href='/member/myTemp?FlagSelect=A';">답변</p>
			</div>
		</div>
		<div class="atm_myjjim_con">
		<c:forEach var="item" items="${list}" varStatus="status">
			<div class="atm_myjjim_el atm_border">
				<div class="atm_myjjim_c5" onClick="javascript:location.href='${item.linkURL}';">
					<img src="/Common/images/icon_${item.imgSrc}.png" class="atm_icon_qmark"/>
					<c:if test="${item.flag == 'Q'}">
						<p class="atm_icon_score">${item.almoney}</p>
					</c:if>
					${item.title}
				</div><br/>
				<p class="atm_myjjim_c7">${item.dateReg} &nbsp;
				</p>
				<a href="javascript:fnDelete('${item.flag}','${item.questionSeq}','${item.answerSeq}')"><img src="/Common/images/btn_x_1.png" class="atm_mymenu_xbtn"></a>
			</div>
		</c:forEach>
		</div><!--atm_myjjim_con end -->
		
		<div class="atm_boardnavi">
		${paging}
		</div>
	</form>
	
	<iframe name="ifrm" width="100%" height="0" frameborder="0"></iframe>
	<%@ include file="/Common/include/MenuItem.jsp" %>
	<!--wrapper end -->
	</div><!--atm_myjjim_wrapper0 end -->

</div><!-- atm_wrapper end -->


</body>
</html>