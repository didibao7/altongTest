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
#atm_myjjim_wrapper0{ width:100%; padding:0 0 0 0; background-color:#f2f2f2; font-family:'Nanum Gothic', sans-serif; }}

.atm_myjjim_ttbar1{  background-color:#e8e8e8; padding:10px 0px; text-align:center;  }


.atm_myjjim_con{ padding:4px 0 11px; text-align:left; position:relative; font-size:0; }
.atm_myjjim_el{ position:relative; background-color:#fff; margin-top:7px; padding:15px 14px; border-bottom:#bbbbbb 1px solid; cursor:pointer;}

.beefup__head { 
	font-size:14px; 
	font-weight:bold; 
	letter-spacing:0px;  
	color:#333333; 
	line-height:20px;

	display: inline-block; 
	width: 100%; 
	white-space: nowrap; 
	overflow: hidden; 
	text-overflow: ellipsis; 

	white-space: normal; 
	line-height: 1.5; 
	height: 4.5em; 
	text-align: left; 
	word-wrap: break-word; 
	display: -webkit-box; 
	-webkit-line-clamp: 3; 
	-webkit-box-orient: vertical; 
}

@media screen and (min-width: 800px) {
#atm_myjjim_wrapper0{ text-align:center;}
.atm_myjjim_con{ width:800px; display:inline-block; }
.atm_mycatebar1{ text-align:center; }
.atm_mycatebar1_pc{ width:800px; display:inline-block; text-align:left; position:relative; }
.atm_mycatebar_sel{ top:-2px;}
}

</style>
<div id="atm_wrapper">
<%@ include file="/Common/include/MenuSub.jsp" %>

	<div id="atm_myjjim_wrapper0">
	<!--wrapper start -->
	<form name="frm_sch" method="post" onSubmit="return false;">
	<input type="hidden" name="src_Target" value="${curPageName}">
	<input type="hidden" name="pg" value="${n_curpage}">
	
	<div class="atm_mycatebar1">
		<div class="atm_mycatebar1_pc">
			<p class="atm_mycatebar_c1">[${nickName}] 님의 열람한 답변 : <span class="atm_mycatebar_c2">${n_trec}</span> 개</p>
			<select name="Section1" class="atm_mycatebar_sel" onChange="javascript:goSubmit('frm_sch','${curPageName}','_self');">
				<option value="" <c:if test="${section1 != '0'}">selected</c:if>>카테고리별 보기</option>
			<c:forEach var="sec" items="${sectionVO}" varStatus="status">
				<option value="${sec.code}" <c:if test="${section1 == sec.code}">selected</c:if>>${sec.codeName}</option>
			</c:forEach>
			</select>
		</div><!--atm_mycatebar1_pc end -->
	</div><!--atm_mycatebar1 end -->

	<div class="atm_myjjim_con">
	<c:forEach var="vue" items="${myViewList}" varStatus="status">
		<div class="atm_myjjim_el atm_border">
			<div class="atm_myjjim_c7" onClick="javascript:location.href='/answer/answerView?AnswerSeq=${vue.answerSeq}&QuestionSeq=${vue.questionSeq}&CurPageName=/member/otherAdminView&Section1=${vue.section1}';">${vue.regdate} 에 열람한 답변입니다.</div><br />
			<p class="beefup__head" onClick="javascript:location.href='/answer/answerView?AnswerSeq=${vue.answerSeq}&QuestionSeq=${vue.questionSeq}&CurPageName=/Member/otherAdminView&Section1=${vue.section1}';"><img src="/Common/images/icon_answer.png" class="atm_icon_amark"/>${vue.answer.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>","")}</p>
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
	</div>
</div><!-- atm_wrapper end -->
</body>
</html>