<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp"%>
<%@ page import="com.altong.web.logic.util.CommonUtil"%>
<%@ page import="org.json.simple.JSONObject"%>
<head>
	<link rel="stylesheet" href="/pub/member/myQuestion/myQuestion.css?ver=1.2">
	<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.5">
	
	<link rel="stylesheet" href="/pub/css/myPage_lang.css?ver=1.0">
	<script src="/Common/js_new/default/languages.js?ver=1.0"></script>
	
	<script>
	function goTranslate_qr(contentSeq, replSeq, contentType) {
		var qt_seq = '';
		
		if(contentType == 'R') {
			qt_seq = $('#qt_' + contentSeq + '_' + replSeq).val();
		}
		else {
			qt_seq = $('#bAnst_' + contentSeq + '_' + replSeq).val();
		}
		//console.log('qt_seq : ' + qt_seq);
		//console.log('replSeq : ' + replSeq);
		//console.log('contentType : ' + contentType);
		// 이미 번역 상태라면 반대로 동작시키기
		if(qt_seq != '') {
	        $.ajax({
				type: 'post',
				url: '/translate/trans',
				data: { 'contentSeq' : replSeq, 'contentType' : contentType },
				dataType: 'json',
				crossDomain: true,
				success: function (r) {
					//console.log('r.result : ' + r.result);
					switch (r.result) {
						case 'Y':
							var tContents = r.arr.tContents;
							
							if(contentType == 'R') {
								$('#replQ_' + contentSeq + '_' + replSeq).html('<img src="/Common/images/icon_reply.png" class="atm_viewicon_reply"/>' + tContents);
							}
							else {
								$('#replA_' + contentSeq + '_' + replSeq).html('<img src="/Common/images/icon_reply.png" class="atm_viewicon_reply"/>' + tContents);
							}
							
							break;
						case 'N':
							//alert(getLangStr("jsm_0083"));
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
				url: '/translate/getReplyOrgTitle',
				data: { 'contentSeq' : replSeq, 'contentType' : contentType},
				dataType: 'json',
				crossDomain: true,
				success: function (r) {
					//console.log('r.result : ' + r.result);
					switch (r.result) {
						case 'Y':
							var tContents = r.arr.tContents;
							
							if(contentType == 'R') {
								$('#replQ_' + contentSeq + '_' + replSeq).html('<img src="/Common/images/icon_reply.png" class="atm_viewicon_reply"/>' + tContents);
							}
							else {
								$('#replA_' + contentSeq + '_' + replSeq).html('<img src="/Common/images/icon_reply.png" class="atm_viewicon_reply"/>' + tContents);
							}
							
							break;
						case 'N':
							//alert(getLangStr("jsm_0083"));
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
	}
	
	function goTranslate_que(contentSeq, replSeq) {
		//번역 유무에 따라서
		//번역이 없는 경우 기계 번역 실행후 결과를 가져와서 tSeq 를 가져온다.
		//번역이 있는 경우 사람번역이 있으면 사람번역의 tSeq 를 가져온다. 
	    
	    //console.log('contentSeq : ' + contentSeq);
	    //alert('서비스 준비중입니다!');
	    var contentType = 'Q';
	    
	    var qt_seq = $('#qt_' + contentSeq + '_' + replSeq).val();
	    
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
							
							//tSeq 설정
							//tTitle 만교체(span id="que_") / qt_
							tTitle = tTitle.replace(/(<([^>]+)>)/ig,"");
							tContents = tContents.replace(/(<([^>]+)>)/ig,"");
							
							$('#que_' + contentSeq + '_' + replSeq).html(tTitle);
							$('#cont_' + contentSeq + '_' + replSeq).html(tContents);
							$('#qt_' + contentSeq + '_' + replSeq).val(tSeq);
							
							goTranslate_qr(contentSeq, replSeq, 'R');
							
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
							
							tContents = tContents.replace(/(<([^>]+)>)/ig,"");
							
							$('#que_' + contentSeq + '_' + replSeq).html(tTitle);
							$('#cont_' + contentSeq + '_' + replSeq).text(tContents);
							$('#qt_' + contentSeq + '_' + replSeq).val(''); // 번역문 코드 제거
							
							goTranslate_qr(contentSeq, replSeq, 'R');
							
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
	}
	
	function goTranslate_ans(contentSeq, replSeq) {
		var trn_chk = $('#bAnst_' + contentSeq + '_' + replSeq).val();
		//console.log('contentSeq : ' + contentSeq);
		//console.log('trn_chk : ' + trn_chk);
		var type = "A";	
		
		if(trn_chk == '') {
			$.ajax({
				type: 'post',
				url: '/translate/trans',
				data: { 'contentSeq' : contentSeq, 'contentType' : type },
				dataType: 'json',
				crossDomain: true,
				success: function (r) {
					//console.log('r.result : ' + r.result);
					switch (r.result) {
						case 'Y':
							var tSeq = r.arr.tSeq;
							var tContents = r.arr.tContents; // 답변 내용
							
							tContents = tContents.replace(/(<([^>]+)>)/ig,"");
							//console.log('tContents : ' + tContents);
							$('#bAnst_' + contentSeq + '_' + replSeq).val(tSeq);
							$('#bAns_' + contentSeq + '_' + replSeq).text(tContents);
							
							setTimeout(function(){
								goTranslate_qr(contentSeq, replSeq, 'S');
							}, 2000);
							
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
			$.ajax({
				type: 'post',
				url: '/translate/getAnsOrgTitle',
				data: { 'contentSeq' : contentSeq, 'contentType' : type },
				dataType: 'json',
				crossDomain: true,
				success: function (r) {
					//console.log('r.result : ' + r.result);
					switch (r.result) {
						case 'Y':
							var tContents = r.arr.tContents;
							
							tContents = tContents.replace(/(<([^>]+)>)/ig,"");
							
							$('#bAnst_' + contentSeq + '_' + replSeq).val('');
							$('#bAns_' + contentSeq + '_' + replSeq).text(tContents);
							
							goTranslate_qr(contentSeq, replSeq, 'S');
							
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
	}
	
	function goAnswerList(seq) {
		var qtSeq = $('#qt_' + seq).val();
		var url = '/answer/answerList?Seq=' + seq + '&CurPageName=/member/myZzim';
		
		if(qtSeq == '') {
			location.href = url;
		}
		else {
			location.href = url + '&qtSeq=' + qtSeq;
		}
	}
	
	function goAnswerByAnswerSeq(aSeq, qSeq, section1) {
		var tSeq = $('#bAnst_' + aSeq).val();
		
		var url = "";
		
		if(tSeq == '' || tSeq == '0') {
			url = '/answer/answerView?QuestionSeq=' + qSeq + '&AnswerSeq=' + aSeq + '&CurPageName=/member/myView&Section1=' + section1;
		}
		else {
			url = '/answer/answerView?QuestionSeq=' + qSeq + '&AnswerSeq=' + aSeq + '&tSeq=' + tSeq + '&CurPageName=/member/myView&Section1=' + section1;
		}
		
		location.href = url;
	}
	</script>
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<%
	String flagMe = String.valueOf(request.getAttribute("flagMe"));
%>
<div id="atm_myjjim_wrapper0">
	<div class="atm_mycatebar1">
		<div class="center">
			<div class="atm_mycatebar1_pc">
				<p class="atm_mycatebar_c1">
				<c:choose>
					<c:when test="${flagMe != ''}"><spring:message code="msg_0835"/></c:when>
					<c:otherwise><spring:message code="msg_0834"/></c:otherwise>
				</c:choose> <span class="atm_mycatebar_c2">${n_trec}</span><spring:message code="msg_0370"/>
				</p>
				<input name="FlagMe" type="hidden" value="${flagMe}">
			</div>
		</div>
	</div>
	<div class="atm_temp_tab2">
		<div class="center">
			<div class="atm_temp_tab2_pc">
				<p class="atm_temp_tab <%if(flagMe == "") {%>temp_tab_on<%}%>" onclick="location.href='/member/myReply?QA_Cls=Q&userWho=${who}';"><spring:message code="msg_0834"/></p>
				<p class="atm_temp_tab <%if(flagMe != "") {%>temp_tab_on<%}%>" onclick="location.href='/member/myReply?FlagMe=Y&QA_Cls=A&userWho=${who}';"><spring:message code="msg_0835"/></p>
			</div>
		</div>
	</div>
	<div class="atm_myjjim_con">
		<div class="center">
			<form name="myreply_frm_sch" method="post" onSubmit="return false;">
				<input type="hidden" name="src_Target" value="${curPageName}">
				<input type="hidden" name="pg" value="${n_curpage}">
				<script language="javascript">
					  // [추가(2017.12.23): 김현구] 질문 확인 후, 삭제 처리
					  function fnDelete(Seq, QuestionSeq) {
						  <%
						  	String who = String.valueOf( request.getAttribute("who") );
						  	JSONObject global_info = (JSONObject) CommonUtil.getGlobal(request, response);
						  	
						  	if (global_info == null || !global_info.get("UserSeq").toString().equals(who)) {
						  %>
						  if(true){
							  alert(getLangStr("jsm_0101"));
							  return
						  }
						  <% } %>
						var qa_Cls = '${qa_Cls}';
						var ans1 = confirm(getLangStr("jsm_0008") + '\n' + getLangStr("jsm_0102"));
					    if ( ans1 == false ) return;
					
					  	goSubmit('myreply_frm_sch', '/member/myReplyDel?QA_Cls=' + qa_Cls + '&Seq=' + Seq + '&QuestionSeq=' + QuestionSeq, 'myreply_ifrm');
					  }
				</script>
				<c:forEach var="repl" items="${myReplyList}" varStatus="status">
				<c:choose>
					<c:when test="${repl.flagDel == 'N'}">
					</c:when>
						<c:otherwise>
							<div class="atm_myjjim_el atm_border atm_myjjim_delete">
								<c:choose>
									<c:when test="${repl.flag == 'Q'}">
										<c:if test="${repl.flagUse == 'Y'}">
											<div class="atm_myjjim_c5" onClick="javascript:location.href='/answer/answerList?Seq=${repl.questionSeq}&CurPageName=/member/myReply';"><h3>Q.</h3><p><span id="que_${repl.questionSeq}_${repl.seq}">${repl.title}</span></p></div>
											<div class="beefup_head italic" id="cont_${repl.questionSeq}_${repl.seq}" onclick="javascript:location.href='/answer/answerList?Seq=${repl.questionSeq}&CurPageName=/Member/MyReply';">${repl.contents}</div>
											<p class="beefup__head" id="replQ_${repl.questionSeq}_${repl.seq}" onClick="javascript:location.href='/answer/answerList?Seq=${repl.questionSeq}&CurPageName=/member/myReply';"><img src="/Common/images/icon_reply.png" class="atm_viewicon_reply"/>${repl.reply }</p>
										</c:if>
										<c:if test="${repl.flagUse != 'Y'}">
											<!-- <div class="atm_myjjim_c5" onClick="alert('삭제된 질문의 댓글입니다!');"><h3>Q.</h3><p><em class="atm_myjjim_q_delete">삭제된 질문입니다.<i>[삭제]</i></em></p></div> -->
											
											<p class="beefup__head" onClick="alert('<%=CommonUtil.getLangMsg(request, "msg_0103")%>');"><img src="/Common/images/icon_reply.png" class="atm_viewicon_reply"/><span id="bAns_${repl.questionSeq}_${repl.seq}">${repl.reply}</span></p>
										</c:if>
									</c:when>
									<c:otherwise>
										<c:if test="${repl.flagUse == 'Y'}">
											<div class="atm_myjjim_c5" onClick="javascript:location.href='/answer/answerList?CSeq=${repl.contentSeq}&Seq=${repl.questionSeq}&CurPageName=/member/myReply#${repl.contentSeq}';"><h3 class="atm_myjjim_answer">A.</h3><p>
											<%if(flagMe == "") {%>
											<c:if test="${repl.nickName != ''}">${repl.nickName}</c:if><c:if test="${repl.nickName == ''}">${whoNick}</c:if>
											<%}else{%>
												${whoNick}
											<%}%><%=CommonUtil.getLangMsg(request, "msg_0271")%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="atm_myjjim_answer_q" id="que_${repl.contentSeq}_${repl.seq}"><strong>Q.</strong>&nbsp;${repl.title}</span></p></div>
											<div class="beefup_head italic" id="bAns_${repl.contentSeq}_${repl.seq}" onclick="javascript:location.href='/answer/answerList?CSeq=${repl.contentSeq}&Seq=${repl.questionSeq}&CurPageName=/Member/MyReply#${repl.contentSeq}';">${repl.answer}</div>
											<p class="beefup__head" id="replA_${repl.contentSeq}_${repl.seq}" onClick="javascript:location.href='/answer/answerList?CSeq=${repl.contentSeq}&Seq=${repl.questionSeq}&CurPageName=/member/myReply#${repl.contentSeq}';"><img src="/Common/images/icon_reply.png" class="atm_viewicon_reply"/>${repl.reply}</p>
										</c:if>
										<c:if test="${repl.flagUse != 'Y'}">
											<div class="atm_myjjim_c5" onClick="alert('<%=CommonUtil.getLangMsg(request, "msg_0104")%>');"><h3 class="atm_myjjim_answer">A.</h3><p><em><%=CommonUtil.getLangMsg(request, "msg_0918")%><i>[<%=CommonUtil.getLangMsg(request, "msg_0228")%>]</i></em></p></div>
											<p class="beefup__head" onClick="alert('<%=CommonUtil.getLangMsg(request, "msg_0104")%>');"><img src="/Common/images/icon_reply.png" class="atm_viewicon_reply"/>${repl.reply}</p>
										</c:if>
									</c:otherwise>
								</c:choose>
								<ul class="atm_myjjim_c7">
									<li>
									${repl.regdate} &nbsp;&nbsp;
									<c:if test="${flagMe != ''}">
										|&nbsp;&nbsp;
										${repl.nickName}
									</c:if>
									</li>
								</ul>
								<!-- 
								<c:if test="${flagMe == ''}">
									<button class="atm_mymenu_xbtn" onclick="javascript:fnDelete('${repl.seq}','${repl.questionSeq}');"><i></i><i></i></button>
								</c:if>
								-->
								<c:choose>
									<c:when test="${repl.flag == 'Q'}">
										<input type="hidden" id="qt_${repl.questionSeq}_${repl.seq}" name="qtSeq" value=""/>
										<c:if test="${repl.flagUse == 'Y'}">
										<span class="atm_myJJim_c33"><img class="answer_lang" src="/Common/images/language.svg" alt="" onclick="goTranslate_que('${repl.questionSeq}', '${repl.seq}');"></span>
										</c:if>
									</c:when>
									<c:otherwise>
										<input type="hidden" id="bAnst_${repl.contentSeq}_${repl.seq}" name="tSeq" value="" /> 
										<c:if test="${repl.flagUse == 'Y'}">
										<span class="atm_myJJim_c33"><img class="answer_lang" src="/Common/images/language.svg" alt="" onclick="goTranslate_ans('${repl.contentSeq}', '${repl.seq}');"></span>
										</c:if>
									</c:otherwise>
								</c:choose>	
								
							</div><!--atm_myjjim_el atm_border end -->
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</form>
		</div>
	</div><!--atm_myjjim_con end -->

	<div class="list_pasing">
		${paging}
	</div>
	<div id="top_btn" style="display: block; opacity: 1;">
        <a href="javascript:void(0);">
            <span>
                <img src="/Common/images/top_btn_arrow.svg" alt="top_btn">
            </span>
        </a>
    </div>
	<iframe name="myreply_ifrm" width="100%" height="0" frameborder="0"></iframe>
	<!--wrapper end -->
</div>
<script type="text/javascript" src="/pub/member/myReply/myReply.js?1.1" ></script>
</body>