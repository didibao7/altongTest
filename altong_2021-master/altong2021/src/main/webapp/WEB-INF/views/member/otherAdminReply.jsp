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
.atm_viewicon_reply{ margin-right:2px; width:20px; }
.atm_mymenu_xbtn{ position:absolute; top:9px; right:14px; width:29px;}

/*p태그*/

.atm_myjjim_c1{ font-size:14px; font-weight:bold; letter-spacing:-1px;  color:#333333; display:inline-block;}
.atm_myjjim_c2{ white-space:nowrap; }
.atm_myjjim_c3{ font-size:11px; font-weight:bold; letter-spacing:-0.5px; padding:5px 7px; color:#595959; border:#949494 1px solid; display:inline-block; background-color:#fff; }
.atm_myjjim_c4{ font-size:12px; font-weight:bold; letter-spacing:-0.5px;                  color:#595959; display:inline-block;  }
.atm_myjjim_c5{ font-size:14px; font-weight:bold; letter-spacing:-0.5px;  color:#333333; line-height:17px; margin-bottom:3px;}
.atm_myjjim_c6{ font-size:12px; font-weight:normal; letter-spacing:0px;  color:#333333; line-height:20px; margin-top:5px;}
.atm_myjjim_c7{ font-size:12px; font-weight:normal; letter-spacing:0px;  color:#9e9e9e; display:inline-block; margin-top:5px; width:100%;}

/*본문부분*/
#atm_myjjim_wrapper0{ width:100%; padding:0 0 0 0; background-color:#f2f2f2;}

.atm_myjjim_ttbar1{  background-color:#e8e8e8; padding:10px 0px; text-align:center;  }


.atm_myjjim_con{ padding:4px 0 11px; text-align:left; position:relative; font-size:0; }
.atm_myjjim_el{ position:relative; background-color:#fff; margin-top:7px; padding:15px 14px; cursor:pointer;}

.beefup__head { 
	font-size:12px; 
	font-weight:normal; 
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
	text-align: left; 
	word-wrap: break-word; 
	display: -webkit-box; 
	-webkit-line-clamp: 3; 
	-webkit-box-orient: vertical; 
}
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
<div id="atm_wrapper">
<%@ include file="/Common/include/MenuSub.jsp" %>

	<div id="atm_myjjim_wrapper0">
	<!--wrapper start -->
	<form name="frm_sch" method="post" onSubmit="return false;">
	<input type="hidden" name="src_Target" value="${curPageName}">
	<input type="hidden" name="pg" value="${n_curpage}">

		<div class="atm_mycatebar1">
			<div class="atm_mycatebar1_pc">
				<p class="atm_mycatebar_c1">[${nickName}] 님의 댓글 : <span class="atm_mycatebar_c2">${n_trec}</span> 개</p>
				<input name="FlagMe" type="hidden" value="${flagMe}">
			</div><!-- atm_mycatebar1_pc end -->
		</div><!-- atm_mycatebar1 end -->
		
		<div class="atm_myjjim_con">
			<script language="javascript">
			  // [추가(2017.12.23): 김현구] 질문 확인 후, 삭제 처리
			  function fnDelete(Seq, QuestionSeq) {
				var ans1 = confirm("정말로 삭제하시겠습니까? \n[확인]을 누르시면 삭제됩니다.");
			    if ( ans1 == false ) return;
				
			    goSubmit('frm_sch', '/member/myReplyDel.asp?QA_Cls=${qa_Cls}&Seq=' + Seq + '&QuestionSeq=' + QuestionSeq, 'ifrm');
			  }
			</script>
			
			<c:forEach var="repl" items="${myReplyList}" varStatus="status">
			<div class="atm_myjjim_el atm_border">
			<c:choose>
			<c:when test="${repl.flag == 'Q'}">
				<c:if test="${repl.flagUse == 'Y'}">
				<div class="atm_myjjim_c5" onClick="javascript:location.href='/answer/answerList?Seq=${repl.questionSeq}&CurPageName=/member/otherAdminReply';"><img src="/Common/images/icon_question.png" class="atm_icon_qmark"/>${repl.title}</div><br />
				<p class="beefup__head" onClick="javascript:location.href='/answer/answerList?Seq=${repl.questionSeq}&CurPageName=/member/otherAdminReply';"><img src="/Common/images/icon_reply.png" class="atm_viewicon_reply"/>${repl.reply }</p>
				</c:if>
				<c:if test="${repl.flagUse != 'Y'}">
				<div class="atm_myjjim_c5" onClick="alert('삭제된 질문의 댓글입니다!');"><img src="/Common/images/icon_question.png" class="atm_icon_qmark"/><span style="color:#888888;"><i>삭제된 질문입니다.</i></span> <span style="position:relative; left:7px; color:#FF0000; font-weight:bold;">[삭제]</span></div><br />
				<p class="beefup__head" onClick="alert('삭제된 질문의 댓글입니다!');"><img src="/Common/images/icon_reply.png" class="atm_viewicon_reply"/>${repl.reply}</p>
				</c:if>
			</c:when>
			<c:otherwise>
				<c:if test="${repl.flagUse == 'Y'}">
				<div class="atm_myjjim_c5" onClick="javascript:location.href='/answer/answerList?Seq=${repl.questionSeq}&CurPageName=/member/otherAdminReply#${repl.contentSeq}#${repl.contentSeq}';"><img src="/Common/images/icon_answer.png" class="atm_icon_qmark"/><c:if test="${flagMe != ''}">${sessionScope.UserNickName}</c:if><c:if test="${flagMe == ''}">${repl.nickName}</c:if> 님의 답변입니다.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size:12px;color:#9e9e9e;font-weight: normal;"><img src="/Common/images/icon_reply_question_w17.png"/>&nbsp;${repl.title}</span></div><br />
				<p class="beefup__head" onClick="javascript:location.href='/answer/answerList?Seq=${repl.questionSeq}&CurPageName=/member/otherAdminReply#${repl.contentSeq}';"><img src="/Common/images/icon_reply.png" class="atm_viewicon_reply"/>${repl.reply}</p>
				</c:if>
				<c:if test="${repl.flagUse != 'Y'}">
				<div class="atm_myjjim_c5" onClick="alert('삭제된 답변의 댓글입니다!');"><img src="/Common/images/icon_answer.png" class="atm_icon_qmark"/><span style="color:#888888;"><i>삭제된 답변입니다.</i></span> <span style="position:relative; left:7px; color:#FF0000; font-weight:bold;">[삭제]</span></div><br />
				<p class="beefup__head" onClick="alert('삭제된 답변의 댓글입니다!');"><img src="/Common/images/icon_reply.png" class="atm_viewicon_reply"/>${repl.reply}</p>
				</c:if>
			</c:otherwise>
		</c:choose>
	
			<p class="atm_myjjim_c7">
				${repl.regdate} &nbsp;&nbsp;
				<c:if test="${flagMe != ''}">
				|&nbsp;&nbsp;
				${repl.nickName}
				</c:if>
			</p>
			
			<c:if test="${flagMe == ''}">
			<a href="javascript:fnDelete('${repl.seq}','${repl.questionSeq}');"><img src="/Common/images/btn_x_1.png" class="atm_mymenu_xbtn"/></a>
			</c:if>
	
			</div><!-- atm_myjjim_el atm_border end -->
			</c:forEach>
		</div><!-- atm_myjjim_con end -->
		
		<div class="atm_boardnavi">
		${paging}
		</div>
	</form>
	<iframe name="ifrm" width="100%" height="0" frameborder="0"></iframe>
	<%@ include file="/Common/include/MenuItem.jsp" %>
	<!--wrapper end -->
	</div><!-- atm_myjjim_wrapper0 end -->
</div><!-- atm_wrapper end -->

</body>
</html>