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
	<input type="hidden" name="MemberSeq" value="${memberSeq}">
		<div class="atm_mycatebar1">
			<div class="atm_mycatebar1_pc">
				<p class="atm_mycatebar_c1">작성한 답변 <span class="atm_mycatebar_c2">${n_trec}</span> 개</p>
				<select name="FlagChoice" class="atm_mycatebar_sel" onChange="javascript:goSubmit('frm_sch','${curPageName}','_self');">
					<option value="" <c:if test="${flagChoice == ''}">selected</c:if>>전체 답변</option>
					<option value="Y" <c:if test="${flagChoice == 'Y'}">selected</c:if>>채택된 답변</option>
					<option value="N" <c:if test="${flagChoice == 'N'}">selected</c:if>>미채택된 답변</option>
				</select>
			</div>
		</div><!-- atm_mycatebar1 end -->
		
		<div class="atm_myjjim_con">
		<c:forEach var="ans" items="${myAnsList}" varStatus="status">
			<c:set var="answerCut" value="${fn:substring(ans.answer, 0, 50)}"/>
			<c:if test="${answerCut != ans.answer}">
				<c:set var="answerCut" value="${answerCut}..."/>
			</c:if>
			
			<c:if test="ans.flagNickName != 'N'">
			<div class="atm_myjjim_el" onClick="location.href='/answer/answerList?Seq=${ans.questionSeq}&CurPageName=/member/otherAnswer&src_Sort=ReadCount&src_OrderBy=DESC'">
				<div class="atm_myjjim_c5" style="color:#000;font-weight:normal"><img src="/Common/images/icon_question.png" class="atm_icon_qmark"/><p class="atm_icon_score"><fmt:formatNumber value="${ans.almoney}" pattern="#,###.0" /></p><i>${ans.title}</i></div><br />
				<p class="atm_myjjim_c7" style="color:#000">${answerCut}</p>
				<p class="atm_myjjim_c7">${ans.dateReg} &nbsp;
					<span class="atm_whitespace"><img src="/Common/images/icon_view.png" class="atm_viewicon"/><fmt:formatNumber value="${ans.readCount}" pattern="#,###" /></span>&nbsp;
					<span class="atm_whitespace"><img src="/Common/images/icon_answer_micro.png" class="atm_viewicon"/>1/<fmt:formatNumber value="${ans.answerCount}" pattern="#,###" /></span>
				</p>
			</div>
			</c:if>
		</c:forEach>
		</div><!-- atm_myjjim_con end -->
		
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