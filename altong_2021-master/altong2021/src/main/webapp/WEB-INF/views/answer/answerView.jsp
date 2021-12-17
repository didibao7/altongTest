<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp"%>
<%
	int maxSiren = 3;

	JSONObject global = (JSONObject)CommonUtil.getGlobal(request, response);
	
	String msg_0224 = CommonUtil.getLangMsg(request, "msg_0224");
	String msg_0225 = CommonUtil.getLangMsg(request, "msg_0225");
	String msg_0226 = CommonUtil.getLangMsg(request, "msg_0226");
	String msg_0227 = CommonUtil.getLangMsg(request, "msg_0227");
	String msg_0236 = CommonUtil.getLangMsg(request, "msg_0236");
	String msg_0331 = CommonUtil.getLangMsg(request, "msg_0331");
	
	pageContext.setAttribute("maxSiren", maxSiren);
	
	pageContext.setAttribute("msg_0224", msg_0224);
	pageContext.setAttribute("msg_0225", msg_0225);
	pageContext.setAttribute("msg_0226", msg_0226);
	pageContext.setAttribute("msg_0227", msg_0227);
	pageContext.setAttribute("msg_0236", msg_0236);
	pageContext.setAttribute("msg_0331", msg_0331);
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<link rel="stylesheet" href="/Common/CSS_new/answerView.css?ver=1.1">
<link rel="stylesheet" href="/Common/CSS_new/mediaQuery.css?ver=1.1">
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<script>
function fautoURL(obj) {
	var urlRegex = /(((https?:\/\/)|(www\.))[^\s]+)/gi;
	var urlSpace = /(\s|\t|\r\n|\n|<br>|<br\/>|<br \/>|<\/p>)/gi;
	var myRegExp =/<(\/a|a)([^>]*)>/gi;
	obj.find('.prgContent_AR, .prgContent_QR, .prgContent_A, .prgContent_Q').each(function(index, item){
		var string = $(item).html();
		var temp;
		var temp2 = string.replace(myRegExp , '');
		var url;
		var urlTag;
		var url2;
		var result2 = "";
		var x = 0;

		if (temp2.search(urlRegex) < 0) {
			$(item).html(string)
			return;
		}

		while (temp2.search(urlRegex) >= 0 && x < 300){
			temp = temp2;
			temp3 = temp.substring(temp.search(urlRegex), temp.length);

			if (temp3.search(urlSpace) >= 0) {
				url = temp3.substring(0, temp3.search(urlSpace));
			} else {
				url = temp3;
			}

			result = temp.substring(0, temp.search(urlRegex) + url.length);
			temp2 = temp.substring(temp.search(urlRegex) + url.length, temp.length);

			if ( url.indexOf('http:') < 0 && url.indexOf('https:') < 0) {
				if (url.indexOf('www.') == 0 || url.indexOf('www.') < 0 ) {
					url2 = "http://" + url;
				}
			} else {
				url2 = url;
			} 

			urlTag = "<a href='"+url2+"' target='_blank'>"+url+"</a>";
			result2 += result.replace(url, urlTag);

			if (temp2.search(urlRegex) < 0 && temp2.length > 0) {
				result2 += temp2;
			}

			x++;
		}
		
		$(item).html(result2);
	});
}
$(function(){
	$('#replydiv_va').css('display', 'none');

	$('.atm_viewbtnG2 .atm_viewbtnG_replybtn').click(function(){
		//console.log('click!!!');
		var block = $('.answerView_repl').css('display');
		//console.log('block : ' + block);
		if(block == 'none')
	    	$('.answerView_repl').css('display', 'block');
	    else 
	    	$('.answerView_repl').css('display', 'none');
	});
});
function fViewQReply(seq, flag, load) {
	if(seq == undefined) {
		return false;
	}
	
	var base;
	var textCount;
	if (flag == "A") {
		base = $('#replyWrapper_'+seq);
		var ajaxURL = '/answer/answerList_AReply?TargetSeq=' + seq;
		textCount = base.find('.reply_submit').find('p');
	} else {
		base = $('input[value="'+seq+'"]').closest('#replyDiv');
		var ajaxURL = '/answer/answerList_QReply?TargetSeq=' + seq;
		textCount = base.find('.reply_submit').find('.atm_reply_c6');
	}
	
	var textarea = base.find('.replydiv_el').find('textarea');
	var target = base.find('.replydiv_user'); 

	$.ajax({
		url: ajaxURL ,
		type: 'post',
		success: function(data) {
			if (load == true) {
				target.html(data);
			} else {
				textCount.html('<span>0</span>/400');
				textarea.val('');
				target.html(data);
			}
			fautoURL($(base).find('.reply_box'));
			base.siblings('.answer_reply_btn').find('.reply').find('span').text(target.find('.replydiv_user_list').length);
			
			if (load != true) {
				fViewQReply(seq, flag, true);
			}
		},
		error: function() {
			//alert('<spring:message code="msg_0320"/>');
		}
	});
}

var goSubmit_origin = goSubmit;
var goSubmit = function (FormName, URL, Target, obj)
{
	var where;
	
	// 김주윤 20201222 관리자 이외 유저는 스크립트 작성 불가
	var contents = $('form[name=' + FormName + ']').find('textarea').val();
	if(contents != undefined) {
		<c:if test="${global.get('AdminSecu') eq null}">
			contents = contents.replace(/(<([^>]+)>)/ig,"");
			$('form[name=' + FormName + ']').find('textarea').val(contents);
			
			contents = $('form[name=' + FormName + ']').find('textarea').val();
		</c:if>
	}
	
	if (URL.indexOf('reply') != -1 && URL.indexOf('&Flag=A') != -1)
		where = 1; //[답변 댓글 등록]
	else if (URL.indexOf('reply') != -1 && URL.indexOf('&Flag=Q') != -1)
		where = 2;
	else
	{
		if(Target == '') {
			//goSubmit_origin(FormName, URL, Target);
			location.href = URL;
			return;
		}
	}
	
	//프론트쪽이고, 오류가 많이 발생하지 않을 것으로 보여서 비동기에 따른 변수 재처리를 고려하지 않음.
	$.ajax({
		url: URL,
		data: $('form[name=' + FormName + ']').serialize(),
		type: 'post',
		success: function(data) {
			//if (data.indexOf('<!--SUCCESS-->') != -1)
			if(data.result == 'SUCCESS')
			{
				switch (where)
				{
					case 1:
						var answerSeq = $('form[name=' + FormName + ']').find('input').attr('value');
						fViewQReply(answerSeq, "A");
						break;

					case 2:
						var questionSeq = $('form[name=' + FormName + ']').find('input').attr('value');
						fViewQReply(questionSeq, "Q");
						break;
				}
			}
			else
			{
				//var js = data.split('</head>');
				//$('<div style="display:hidden"></div>').appendTo('body').html(js[1]).remove();
				alert(data.result);
			}
		},
		error: function(request, status, error) {
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			//alert('<spring:message code="msg_0320"/>');
		}
	});
}

var goConfirm_origin = goConfirm;
var goConfirm = function (FormName, URL, Message, Target, obj)
{
	var where;
	if (URL.indexOf('replydel') != -1 && URL.indexOf('&Flag=A') != -1)
		where = 1; //[답변 댓글 등록]
	else if (URL.indexOf('replydel') != -1 && URL.indexOf('&Flag=Q') != -1)
		where = 2;
	else
	{
		goConfirm_origin(FormName, URL, Message, Target);
		location.href = URL;
		return;
	}
	
	var cMsg1 = '<%=CommonUtil.getLangMsg(request, "msg_0323")%>';
	var cMsg2 = '<%=CommonUtil.getLangMsg(request, "msg_0324")%>';
	var confirmMsg = cMsg1 + Message + cMsg2;
	if(!confirm(confirmMsg)) return;
	
	$.post({
		url: URL,
		data: $('form[name=' + FormName + ']').serialize(),
		type: 'post',
		success: function(data, status) {
			if (data.indexOf('<!--SUCCESS-->') != -1)
			{
				alert('<%=CommonUtil.getLangMsg(request, "msg_0322")%>');
				
				switch (where)
				{
					case 1:
						var answerSeq = $('form[name=' + FormName + ']').find('input').attr('value');
						//fViewQReply(answerSeq, 'A');
						break;

					case 2:
						var questionSeq = $('form[name=' + FormName + ']').find('input').attr('value');
						//fViewQReply(questionSeq, "Q");
						break;
				}
				location.reload();
			}
			else
			{
				var js = data.split('</head>');
				$('<div style="display:hidden"></div>').appendTo('body').html(js[1]).remove();
			}
		},
		error: function() { 
			alert('<%=CommonUtil.getLangMsg(request, "msg_0320")%>');
		}
	});
}

function formatDateForSpan(v) {
	//TODO: fautoURL 함수랑 같은 방식으로 변경하기
	var v = $("#" + v);
	var curDate1 = v.find('span').text();
	var nowDate, gap, gapSec, gapMin, gapHour, gapDay, gapMonth, gapYear;
	var nowDate = new Date();
	var curDate = new Date(curDate1.replace(/-/g, '/'));

	gap 		= nowDate.getTime() - curDate.getTime();
	gapSec		= gap / 1000;
	gapMin		= gapSec / 60;
	gapHour 	= gapMin / 60;
	gapDay		= gapHour / 24;
	gapMonth 	= gapDay / 30
	gapYear 	= gapMonth / 12;

	if (gapHour <= 24) {
		if (Math.floor(gapMin) < 1) {
			if (Math.floor(gapSec) <= 0) {
				resultDate = '<%=CommonUtil.getLangMsg(request, "msg_0216")%>';
			} else {
				resultDate = Math.floor(gapSec) + '<%=CommonUtil.getLangMsg(request, "msg_0217")%>';
			}
		} else if (Math.floor(gapHour) < 1) {
			resultDate = Math.floor(gapMin) + '<%=CommonUtil.getLangMsg(request, "msg_0218")%>';
		} else {
			resultDate = Math.floor(gapHour) + '<%=CommonUtil.getLangMsg(request, "msg_0219")%>';
		}
	} else {
		if (gapYear >= 1) {
			resultDate = Math.floor(gapYear) + '<%=CommonUtil.getLangMsg(request, "msg_0220")%>';
		} else if (gapMonth >= 1) {
			resultDate = Math.floor(gapMonth) + '<%=CommonUtil.getLangMsg(request, "msg_0221")%>';
		} else {
			resultDate = Math.floor(gapDay) + '<%=CommonUtil.getLangMsg(request, "msg_0222")%>';
		}
	}
	v.html(resultDate + '<span>' + curDate1 + '</span>');
}

$.fn.selectRange = function(start, end) {

    return this.each(function() {

         if(this.setSelectionRange) {

             this.focus();

             this.setSelectionRange(start, end);

         } else if(this.createTextRange) {

             var range = this.createTextRange();

             range.collapse(true);

             range.moveEnd('character', end);

             range.moveStart('character', start);

             range.select();

         }

     });

 };


function replyQueRegi(nick) {
	var disp = $('#replydiv_q').css('display');
	if(disp == 'none') { $('#replydiv_q').css('display', 'block'); }

	setTimeout(function(){
		var nickStr = '['+nick+' ' + getLangStr("jsm_0061") + '] ';
		
		$('#replydiv_q').find('textarea').text('');
		$('#replydiv_q').find('textarea').text(nickStr);
		
		$('#replydiv_q').find('textarea').selectRange(nickStr.length, nickStr.length);
	}, 1000);
	
}

function replyAnsRegi(nick) {
	var disp = $('#replydiv_va').css('display');
	if(disp == 'none') { $('#replydiv_va').css('display', 'block'); }
	
	setTimeout(function(){
		var nickStr = '['+nick+' ' + getLangStr("jsm_0061") + '] ';
		
		$('#replydiv_va').find('textarea').text('');
		$('#replydiv_va').find('textarea').text(nickStr);
		
		$('#replydiv_va').find('textarea').selectRange(nickStr.length, nickStr.length);
	});
}
</script>
<div id="atm_av_con">
        <div class="center">
            <!--el덩어리 -->
            <div class="atm_av_el">
                <div class="atm_av_c9">
                    <h3>Q.</h3>
                    <div class="atm_icon_score1"><sapn class="atm_icon_score2"><fmt:formatNumber value="${almoney}" pattern="#,###" /></sapn>${title}</div>
                    <p class="atm_av_c10">${dateReg}<span class="atm_whitespace"><img src="/Common/images/icon_view.svg" class="atm_viewicon"><fmt:formatNumber value="${readCount}" pattern="#,###" /></span></p>
                </div>	
                <div class="atm_av_c6">
                    <p>${contents}</p>
                </div>
                <div class="atm_addviewG">
                    <p class="atm_viewbtnG_c1" onClick="javascript:goSubmit('frm', '/answer/questionZzim', 'answer_ifrm');"><spring:message code="msg_0344"/></p>
                    <p class="atm_viewbtnG_c1 atm_viewbtnG_replybtn"><spring:message code="msg_0261"/><span class="atm_viewbtnG_c4"><fmt:formatNumber value="${replyCnt}" pattern="#,###" /></span></p>
                </div><!-- atm_addviewG end -->
                <div class="atm_profile_wrapper_addview">
                    <div class="atm_profile_el">
                    	<c:set var="myInfoLink" value="javascript:location.href='/member/myInfo';" />
                        <c:if test="${memberSeq != userSeq}">
                        	<c:set var="myInfoLink" value="javascript:goSubmit('frm_esti', '/member/otherProfileInfo?MemberSeq=${memberSeq}', '');" />
                        	<c:if test="${flagNickNameQ == 'N'}">
                        	<c:set var="myInfoLink" value="javascript:alertPopUp();" />
                        	<script>
								function alertPopUp() {
									alert(getLangStr("jsm_0062"));
								}
							</script>
                        	</c:if>
                        </c:if>
                        <figure>
                            <img src="/UploadFile/Profile/${photo}" alt='<spring:message code="msg_0162"/>' onclick="${myInfoLink}" onerror="this.src='/pub/css/profile/img_thum_base0.jpg'">
                        </figure>
                        
                        
                        <div class="atm_profile_eltexts" onclick="${myInfoLink}">
                            <p class="atm_profile_el_c2">${nickName}<span class="atm_profile_grade2">${lv}</span></p>
                            <p class="atm_profile_el_c3"><span class="atm_whitespace"><spring:message code="msg_0250"/> <fmt:formatNumber value="${questionMoney}" pattern="#,###" /><spring:message code="msg_0136"/></span>&nbsp;<span class="atm_normal">|</span>&nbsp;<span class="atm_whitespace"><spring:message code="msg_0251"/> <fmt:formatNumber value="${answerMoney}" pattern="#,###" /><spring:message code="msg_0136"/></span></p>
                            <p class="atm_profile_el_c3"><span class="atm_whitespace"><spring:message code="msg_0252"/> <fmt:formatNumber value="${countC}" pattern="#,###" /></span>&nbsp;<span class="atm_normal">|</span>&nbsp;<span class="atm_whitespace"><spring:message code="msg_0260"/> <fmt:formatNumber value="${rateChoice}" pattern="#,###.#" /> %</span></p>
                        </div>
                    </div>
                </div>
                <c:if test="${userSeq != 0 and ( memberSeq != userSeq or userFlagSelfAnswer != 'Y' or userMemberType == '2')}">
                <div class="atm_botbtnG">
                    <p class="atm_botbtnG_c3 atm_ttbar3_btn" onClick="javascript:location.href='/answer/answerWrite?QuestionSeq=${questionSeq}&CurPageName=${curPageName}&Section1=${section1}&src_Sort=${src_Sort}&src_OrderBy=${src_OrderBy}';"><spring:message code="msg_0262"/></p>
                </div>
                </c:if>
                <div id="replydiv_q">
                    <form name="sch_q" method="post" onsubmit="return false;">
                        <input type="hidden" name="QuestionSeq" value="${questionSeq}">
                        <div class="atm_reply_wrapper">
                            <div class="atm_reply_write2">
                                <div class="atm_reply_writebox1">
                                    <div>
                                        <p class="atm_reply_c5">${userNick} <spring:message code="msg_0346"/></p>
                                    </div>
                                    <textarea name="Contents" class="atm_reply_input2" maxlength="400" placeholder='<spring:message code="msg_0345"/>'></textarea>
                                </div>
                                <div class="atm_reply_writebox2">
                                    <p class="atm_reply_c6">0/400</p>
                                    <p class="atm_reply_c7" onclick="goSubmit('sch_q', '/answer/reply?TargetSeq=${questionSeq}&Flag=Q', 'answer_ifrm');"><img src="/Common/images/modify03.svg" alt='<spring:message code="msg_0267"/>'><spring:message code="msg_0266"/></p>
                                </div>
                            </div>
                        </div>
                    </form>
                </div><!-- replydiv_q  end -->
                <c:if test="${replyCnt > 0}">
                <div class="atm_reply_wrapper">
                	<c:forEach var="r" items="${replyList}" varStatus="status">
                	<c:set var="content" value="" />
                	<c:choose>
						<c:when test="${r.sirenN < maxSiren}">
							<c:if test="${r.nick1 == ''}"><c:set var="content" value="${r.reply}" /></c:if>
							<c:if test="${r.nick1 != ''}">
								<fmt:formatNumber var="repAl" value="${r.almoney}" pattern="#,###" />
								<c:set var="content" value='${r.nick1} ${msg_0224} ${r.nick2} ${msg_0225}${repAl}${msg_0226}' />
							</c:if>
						</c:when>
						<c:otherwise>
							<c:set var="content" value='${msg_0227}' />
						</c:otherwise>
					</c:choose>
                	<div class="atm_reply">
                        <div class="atm_reply_el">
                            <figure>
                                <c:choose>
									<c:when test="${r.photo != null and fn:length(r.photo) > 0}">
									<img src="/UploadFile/Profile/${r.photo}" class="atm_reply_img1" onerror="this.src='/pub/css/profile/img_thum_base0.jpg'" />
									</c:when>
									<c:otherwise>
									<img src="/pub/css/profile/img_thum_base0.jpg" class="atm_reply_img1" onerror="this.src='/pub/css/profile/img_thum_base0.jpg'" />
									</c:otherwise>
								</c:choose>
                            </figure>
                            <div>
                            	<div class="atm_reply_con">
                                    <div class="atm_reply_c4">${content}
                                    	<c:if test="${r.nickName == userNick}">
                                    	<div class="atm_mymenu_xbtn" onclick="javascript:goConfirm('frm', '/answer/replydel?ReplySeq=${r.seq}&Flag=Q', '<spring:message code="msg_0328"/>', 'answer_ifrm');"><i></i><i></i></div>
                                    	</c:if>
                                    </div>
                                </div>
                                <div class="atm_reply_eltexts">
                                    <a href="javascript:replyQueRegi('${r.nickName}')" class="atm_reply_c2">${r.nickName}</a> · <b id="${answerSeq}_reply${status.index}">${r.conDate}<span>${r.conDate}</span></b>
                                </div><!-- atm_reply_eltexts end -->
                            </div>
                        </div><!-- atm_reply_el end -->
                    </div><!-- atm_reply end -->
                    <script>formatDateForSpan("${answerSeq}_reply${status.index}");</script>
                	</c:forEach>
                </div><!-- atm_reply_wrapper end -->
                </c:if>
            </div>
            <!--el덩어리 -->
            
            <c:set var="pointCount" value="${answerView.pointCount1 + answerView.pointCount2 + answerView.pointCount3 + answerView.pointCount4 + answerView.pointCount5}"/>
			<c:set var="originNickName" value="${answerView.nickName}"/>
			<c:set var="answerNickName" value="${answerView.nickName}"/>
			<c:if test="${flagNickName == 'N'}">
				<c:set var="originNickName" value="${answerView.nickName}"/>
				<c:set var="answerNickName" value='${msg_0236}'/>
			</c:if>
            <!--el덩어리 -->
            <div class="atm_av_el">
                <div class="atm_av_ttG">
                    <h3>A.</h3>
                    <div class="atm_av_tt_el">
                        <div class="atm_av_c5">${answerNickName}<spring:message code="msg_0271"/></div>
                        <p class="atm_av_c7">${answerView.dateReg}<span class="atm_whitespace"><img src="/Common/images/icon_view.svg" class="atm_viewicon"><fmt:formatNumber value="${answerView.readCount}" pattern="#,###" /></span></p>
                    </div>
                </div><!-- atm_av_ttG end -->
                <div class="beefup__body">
                    <div class="atm_av_c6">
                        ${answerView.answer}
                    </div>
                    <!-- answer file  ansFileList-->
                </div><!-- beefup__body end -->
                
                <!-- answerReplysum -->
                <form name="frm_ans" method="post">
                    <input type="hidden" name="AnswerSeq" value="${answerSeq}">
                </form>
                <div class="atm_viewbtnG2">
                    <p class="atm_viewbtnG_c1  atm_viewbtnG_replybtn" id="rpl_btn"><spring:message code="msg_0261"/><span class="atm_viewbtnG_c4"><fmt:formatNumber value="${answerReplysum}" pattern="#,###" /></span></p>
                </div><!-- atm_viewbtnG2 end -->
                <div class="atm_estimation_pc">
                    <div class="atm_estimation">
                        <div class="atm_esti_el<c:if test="${pointCountNo == '1'}"> check_icon</c:if>">
                            <img src="/Common/images/esti_1.png?ver=1.3" class="atm_esti_img1" onclick="goEstimate('/answer/answerEstimate?Gubun=A&amp;EstimateSeq=${answerSeq}_1&amp;AnswerMemberSeq=${answerView.memberSeq}&amp;QuestionSeq=${questionSeq}');">
                            <p class="atm_esti_c1"><spring:message code="msg_0347"/><span class="atm_esti_c2" id="${answerSeq}_1">${answerView.pointCount1}</span></p>
                        </div>
                        <div class="atm_esti_el"<c:if test="${pointCountNo == '2'}"> check_icon</c:if>>
                            <img src="/Common/images/esti_2.png?ver=1.2" class="atm_esti_img1" onclick="goEstimate('/answer/answerEstimate?Gubun=A&amp;EstimateSeq=${answerSeq}_2&amp;AnswerMemberSeq=${answerView.memberSeq}&amp;QuestionSeq=${questionSeq}');">
                            <p class="atm_esti_c1"><spring:message code="msg_0348"/><span class="atm_esti_c2" id="${answerSeq}_2">${answerView.pointCount2}</span></p>
                        </div>
                        <div class="atm_esti_el"<c:if test="${pointCountNo == '3'}"> check_icon</c:if>>
                            <img src="/Common/images/esti_3.png?ver=1.2" class="atm_esti_img1" onclick="goEstimate('/answer/answerEstimate?Gubun=A&amp;EstimateSeq=${answerSeq}_3&amp;AnswerMemberSeq=${answerView.memberSeq}&amp;QuestionSeq=${questionSeq}');">
                            <p class="atm_esti_c1"><spring:message code="msg_0349"/><span class="atm_esti_c2" id="${answerSeq}_3">${answerView.pointCount3}</span></p>
                        </div>
                        <div class="atm_esti_el"<c:if test="${pointCountNo == '4'}"> check_icon</c:if>>
                            <img src="/Common/images/esti_4.png?ver=1.2" class="atm_esti_img1" onclick="goEstimate('/answer/answerEstimate?Gubun=A&amp;EstimateSeq=${answerSeq}_4&amp;AnswerMemberSeq=${answerView.memberSeq}&amp;QuestionSeq=${questionSeq}');">
                            <p class="atm_esti_c1"><spring:message code="msg_0350"/><span class="atm_esti_c2" id="${answerSeq}_4">${answerView.pointCount4}</span></p>
                        </div>
                        <div class="atm_esti_el"<c:if test="${pointCountNo == '5'}"> check_icon</c:if>>
                            <img src="/Common/images/esti_5.png?ver=1.2" class="atm_esti_img1" onclick="goEstimate('/answer/answerEstimate?Gubun=A&amp;EstimateSeq=${answerSeq}_5&amp;AnswerMemberSeq=${answerView.memberSeq}&amp;QuestionSeq=${questionSeq}');">
                            <p class="atm_esti_c1"><spring:message code="msg_0351"/><span class="atm_esti_c2" id="${answerSeq}_5">${answerView.pointCount5}</span></p>
                        </div>
                        <div class="atm_esti_el"<c:if test="${pointCountNo == '6'}"> check_icon</c:if>>
                            <img src="/Common/images/esti_6.png?ver=1.1" class="atm_esti_img1" onclick="goEstimate('/answer/answerEstimate?Gubun=A&amp;EstimateSeq=${answerSeq}_6&amp;AnswerMemberSeq=${answerView.memberSeq}&amp;QuestionSeq=${questionSeq}');">
                            <p class="atm_esti_c1"><spring:message code="msg_0352"/><span class="atm_esti_c2" id="${answerSeq}_6">${answerView.pointCount6}</span></p>
                        </div>
                    </div>
                </div><!-- atm_estimation_pc end -->
                
                <c:set var="tagLv" value="${mInfo.lv}"/>
				<c:set var="tagPhoto" value="${mInfo.photo}"/>
				<c:set var="tagQuestionMoney" value="${mInfo.questionMoney}"/>
				<c:set var="tagAnswerMoney" value="${mInfo.answerMoney}"/>
				<%
					String tagLv = String.valueOf( pageContext.getAttribute("tagLv") );
				
					String lvNm = CommonUtil.fn_Level(tagLv, request);
					pageContext.setAttribute("lvNm", lvNm);
				%>
				<c:if test="${tagQuestionMoney == ''}">
					<c:set var="tagQuestionMoney" value="0"/>
				</c:if>
				<c:if test="${tagAnswerMoney == ''}">
					<c:set var="tagAnswerMoney" value="0"/>
				</c:if>
				
				<c:set var="tagCountC" value="${choice.countC}"/>
				<c:set var="tagCountN" value="${choice.countN}"/>
				
				<c:if test="${tagCountC == ''}">
					<c:set var="tagCountC" value="0"/>
				</c:if>
				<c:if test="${tagCountN == ''}">
					<c:set var="tagCountN" value="0"/>
				</c:if>
				
				<c:set var="tagCountAnswer" value="${tagCountC + tagCountN}"/>
				
				<c:set var="tagRateChoice" value="0"/>
				<c:if test="${tagCountC > 0}">
					<c:set var="tagRateChoice" value="${ (tagCountC / tagCountAnswer) * 100 }"/>
				</c:if>
				
				<c:choose>
					<c:when test="${flagNickName == 'N'}">
						<c:if test="${answerView.memberSeq == userSeq}">
						<c:set var="answerNickName" value="${originNickName}(${msg_0331})"/>
						</c:if>
						
						<c:set var="tagPhoto" value=""/>
						<c:set var="tagLv" value='${msg_0331}'/>
						<c:set var="tagQuestionMoney" value="0"/>
						<c:set var="tagAnswerMoney" value="0"/>
						<c:set var="tagCountC" value="0"/>
						<c:set var="tagRateChoice" value="0"/>
					</c:when>
					<c:otherwise>
						<c:set var="tagLv" value="${lvNm}"/>
					</c:otherwise>
				</c:choose>
                <div class="atm_profile_wrapper_addview">
                    <div class="atm_profile_el">
                    	<c:set var="myInfoLink2" value="javascript:location.href='/member/myInfo';" />
                        <c:if test="${answerView.memberSeq != userSeq}">
                        	<c:set var="myInfoLink2" value="javascript:goSubmit('frm_esti', '/member/otherProfileInfo?MemberSeq=${answerView.memberSeq}', '');" />
                        	<c:if test="${flagNickNameQ == 'N'}">
                        	<c:set var="myInfoLink2" value="javascript:alertPopUp();" />
                        	<script>
								function alertPopUp() {
									alert(getLangStr("jsm_0062"));
								}
							</script>
                        	</c:if>
                        </c:if>
                        <figure>
                            <c:choose>
								<c:when test="${tagPhoto == ''}">
								<img src="/pub/css/profile/img_thum_base0.jpg'" onclick="${myInfoLink2}" />
								</c:when>
								<c:otherwise>
								<img src="/UploadFile/Profile/${tagPhoto}" onclick="${myInfoLink2}" onerror="this.src='/pub/css/profile/img_thum_base0.jpg'" />
								</c:otherwise>
							</c:choose>
                        </figure>
                        <div class="atm_profile_eltexts" onclick="${myInfoLink2}">
                            <p class="atm_profile_el_c2">${answerNickName}<span class="atm_profile_grade2">${tagLv}</span></p>
                            <p class="atm_profile_el_c3"><span class="atm_whitespace"><spring:message code="msg_0250"/> <fmt:formatNumber value="${tagQuestionMoney}" pattern="#,###" /><spring:message code="msg_0136"/></span><span class="atm_normal"> | </span><span class="atm_whitespace"><spring:message code="msg_0251"/> <fmt:formatNumber value="${tagAnswerMoney}" pattern="#,###" /><spring:message code="msg_0136"/></span></p>
                            <p class="atm_profile_el_c3"><span class="atm_whitespace"><spring:message code="msg_0273"/> <fmt:formatNumber value="${tagCountC}" pattern="#,###" /></span><span class="atm_normal"> | </span><span class="atm_whitespace"><spring:message code="msg_0272"/> <fmt:formatNumber value="${tagRateChoice}" pattern="#,###.#" /> %</span></p>
                        </div>
                    </div>
                </div>
                <div id="replydiv_va" class="answerView_repl">
                    <form name="sch_a" method="post" onsubmit="return false;">
                        <input type="hidden" name="AnswerSeq" value="${answerSeq}">
                        <div class="atm_reply_wrapper">
                            <div class="atm_reply_write2">
                                <div class="atm_reply_writebox1">
                                    <div>
                                        <p class="atm_reply_c5">${userNick}</p>
                                    </div>
                                    <textarea name="Contents" class="atm_reply_input2" maxlength="400" placeholder='<spring:message code="msg_0345"/>'></textarea>
                                </div>
                                <div class="atm_reply_writebox2">
                                    <p class="atm_reply_c6">0/400</p>
                                    <p class="atm_reply_c7" onclick="goSubmit('sch_a', '/answer/reply.asp?TargetSeq=${answerSeq}&Flag=A', 'answer_ifrm');"><img src="/Common/images/modify03.svg" alt='<spring:message code="msg_0267"/>'><spring:message code="msg_0266"/></p>
                                </div>
                            </div>
                        </div>
                    </form>
                </div><!-- replydiv_a end -->
                <c:if test="${fn:length(ansReplList) > 0}">
                <div class="atm_reply_wrapper">
                	<c:forEach var="ansR" items="${ansReplList}" varStatus="status">
                	<c:set var="replyNickName" value="${ansR.nickName}"/>
					<c:set var="content" value="${ansR.reply}"/>
					<c:set var="replyDateReg" value="${ansR.conDate}"/>
					<c:set var="replySeq" value="${ansR.seq}"/>
					<c:set var="replyPhoto" value="${ansR.photo}"/>
					
					<c:set var="reply" value="" />
                	<c:choose>
						<c:when test="${ansR.sirenN < maxSiren}">
							<c:if test="${ansR.nick1 == ''}"><c:set var="reply" value="${ansR.reply}" /></c:if>
							<c:if test="${ansR.nick1 != ''}">
								<fmt:formatNumber var="repAl" value="${ansR.almoney}" pattern="#,###" />
								<c:set var="reply" value='${ansR.nick1} ${msg_0224} ${ansR.nick2} ${msg_0225}${repAl}${msg_0226}' />
							</c:if>
						</c:when>
						<c:otherwise>
							<c:set var="reply" value='${msg_0227}' />
						</c:otherwise>
					</c:choose>
					
                	<div class="atm_reply">
                        <div class="atm_reply_el">
                            <figure>
                                <c:choose>
									<c:when test="${replyPhoto != '' and fn:length(replyPhoto) > 0}">
									<img src="/UploadFile/Profile/${replyPhoto}" class="atm_reply_img1" onerror="this.src='/pub/css/profile/img_thum_base0.jpg'"/>
									</c:when>
									<c:otherwise>
									<img src="/pub/css/profile/img_thum_base0.jpg" class="atm_reply_img1" />
									</c:otherwise>
								</c:choose>
                            </figure>
                            <div>
                                <div class="atm_reply_con">
                                    <div class="atm_reply_c4">${reply}
                                    	<c:if test="${replyNickName == userNick}">
                                    	<div class="atm_mymenu_xbtn" onclick="javascript:goConfirm('frm', '/answer/replydel?ReplySeq=${replySeq}&Flag=A', '<spring:message code="msg_0228"/>', 'answer_ifrm');"><i></i><i></i></div>
                                    	</c:if>
                                    </div>
                                </div>
                                <div class="atm_reply_eltexts">
                                    <a href="javascript:replyAnsRegi('${replyNickName}')" class="atm_reply_c2">${replyNickName}</a> · <b id="${replySeq}_reply${status.index}">${replyDateReg}<span>${replyDateReg}</span>
                                </div><!-- atm_reply_eltexts end -->
                            </div>
                        </div>
                    </div><!-- atm_reply end -->
                    <script>formatDateForSpan("${replySeq}_reply${status.index}");</script>
                	</c:forEach>
                </div>
                </c:if>
            </div>
        </div>
	</div><!-- atm_profile_wrapper_addview end -->

<form name="frm_choice" method="post">
<input type="hidden" name="AnswerSeq" value="${answerSeq}">
<input type="hidden" name="QuestionSeq" value="${questionSeq}">
<input type="hidden" name="CurPageName" value="${curPageName}">
<input type="hidden" name="Section1" value="${section1}">
<input type="hidden" name="src_Sort" value="${src_Sort}">
<input type="hidden" name="src_OrderBy" value="${src_OrderBy}">
<input type="hidden" name="Almoney" value="${almoney}">
<input type="hidden" name="AnswerMemberSeq" value="${answerView.memberSeq}">
</form>
<iframe name="answer_ifrm" width="100%" height="0" frameborder="0"></iframe>
<iframe name="hiddenfrm" width="100%" height="0" frameborder="0"></iframe>
<form name="frm" method="post">
<input type="hidden" name="QuestionSeq" value="${questionSeq}">
</form>
<script>
	function goEstimate(url) {
    <%--[2018.01.30 차건환 수정] history.back() 발생 시 iframe 으로 호출한 AnswerEstimate.asp 페이지로 돌아가는 것 수정, ajax로 변경--%>
	$.ajax({
		'url': url,
		'type': 'post',
		success: function(jsonOrg) {
			var data = $.parseJSON(jsonOrg);

			//답변 평가 카운트 <span> 태그
			const $target = $('#' + data.answerSeq + '_' + data.estiIdx)

			switch(data.returnCode) {
				case -1:
					alert(getLangStr("jsm_0020"));
					break;
				case -2:
					alert('<%=CommonUtil.getLangMsg(request, "msg_0314")%>');
					break;
				case 0:
					$target.html(data.PointCount);
					$target.parent().parent().children('.atm_esti_img0').show();
					if(data.estiIdx !== 6) {
						$target.parent().parent().parent().children('.atm_esti_directup_text').html('<%=CommonUtil.getLangMsg(request, "msg_0315")%>');
					}
					break;
				case 1:
					alert('<%=CommonUtil.getLangMsg(request, "msg_0316")%>');
					break;
				case 2:
					alert('<%=CommonUtil.getLangMsg(request, "msg_0317")%>');
					break;
				default:
					alert('<%=CommonUtil.getLangMsg(request, "msg_0318")%>\n<%=CommonUtil.getLangMsg(request, "msg_0319")%>')
					break;
			}
		}
	});
}
$('#atm_av_wrapper0 > div.atm_av_con > div:nth-child(1) > div.atm_addviewG > p').on('click', function() {
	const $replyDiv = $('#replydiv_q')
	if($replyDiv.css('display') == 'none') {
			$replyDiv.show();
	} else {
			$replyDiv.hide();
	}
});
$('div.atm_viewbtnG2 > p.atm_viewbtnG_c1').on('click', function(){
	const $replyDiv = $('#replydiv_a')
	if($replyDiv.css('display') == 'none') {
           $replyDiv.show();
       } else {
           $replyDiv.hide();
       }
});
$('#replydiv_q > form > div > div > div.atm_reply_writebox1 > textarea').on('keyup', function() {
	var count = $(this).val().length
	const $counter = $('#replydiv_q > form > div > div > div.atm_reply_writebox2 > p.atm_reply_c6')
	$counter.html((count + '/400'))
});
$('#replydiv_va > form > div > div > div.atm_reply_writebox1 > textarea').on('keyup', function() {
	var count = $(this).val().length
	const $counter = $('#replydiv_va > form > div > div > div.atm_reply_writebox2 > p.atm_reply_c6')
	$counter.html((count + '/400'))
});
</script>
<div id="top_btn">
    <a href="#">
        <span>
            <img src="/Common/images/top_btn_arrow.svg" alt="top_btn">
        </span>
    </a>
</div>
</body>
</html>