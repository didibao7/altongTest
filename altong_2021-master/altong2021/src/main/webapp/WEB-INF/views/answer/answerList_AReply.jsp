<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<%
	int maxSiren = 3;

	JSONObject global = (JSONObject)CommonUtil.getGlobal(request, response);
	String AnswerSeq = request.getAttribute("AnswerSeq").toString();
	pageContext.setAttribute("maxSiren", maxSiren);
	pageContext.setAttribute("AnswerSeq", AnswerSeq);
	
	pageContext.setAttribute("userSeq", global != null ? global.get("UserSeq") : 0 );
	pageContext.setAttribute("userLv", global != null ? global.get("UserLv") : 0 );
	
	int Loopa = Integer.parseInt(request.getAttribute("loopCnt").toString());
	
	String msg_0224 = CommonUtil.getLangMsg(request, "msg_0224");
	String msg_0225 = CommonUtil.getLangMsg(request, "msg_0225");
	String msg_0226 = CommonUtil.getLangMsg(request, "msg_0226");
	String msg_0227 = CommonUtil.getLangMsg(request, "msg_0227");
	
	pageContext.setAttribute("msg_0224", msg_0224);
	pageContext.setAttribute("msg_0225", msg_0225);
	pageContext.setAttribute("msg_0226", msg_0226);
	pageContext.setAttribute("msg_0227", msg_0227);
%>
<script>
$(function(){
	/* 답변 번역 아이콘 클릭 */
    $('.answer_lang').click(function(e){
        $(this).attr('src', function(index, attr){
            if (attr.match('_on')) {
                return attr.replace('language_on.svg', 'language.svg');
            } else {
                return attr.replace('language.svg', 'language_on.svg');
            }
        });
        e.stopPropagation();
        e.preventDefault();
    });
});
function formatDate_ar(v) {
	//TODO: fautoURL 함수랑 같은 방식으로 변경하기
	//console.log('v: ' + v);
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

/* 댓글날짜 보임 */
$('.date_format2').click(function(e){
    $(this).find('span').stop().toggle();
    $(this).toggleClass('date_underLine');
    e.stopPropagation();
});
$('body').click(function(){
    $('.date_format2').find('span').stop().hide();
    $('.date_format2').removeClass('date_underLine');
});

function goTranslate_ar(contentSeq, contentType) {
	var trn_chk;
	if(contentType == 'R') {
		trn_chk = $('#tq_reply_trn_' + contentSeq).val();
	}
	else {
		trn_chk = $('#ta_reply_trn_' + contentSeq).val();
	}
	
	if(trn_chk == 'N') {
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
						var tContents = r.arr.tContents;
						
						if(contentType == 'R'){
							$('#tq_reply_trn_' + contentSeq).val('Y');
							$('#tq_reply_' + contentSeq).html(tContents);
						}
						else if(contentType == 'S'){
							$('#ta_reply_trn_' + contentSeq).val('Y');
							$('#ta_reply_' + contentSeq).html(tContents);
						}
						
						break;
					case 'N':
						alert('<%=CommonUtil.getLangMsg(request, "msg_0223")%>');
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
			url: '/translate/getReplyOrgTitle',
			data: { 'contentSeq' : contentSeq, 'contentType' : contentType },
			dataType: 'json',
			crossDomain: true,
			success: function (r) {
				//console.log('r.result : ' + r.result);
				switch (r.result) {
					case 'Y':
						var tContents = r.arr.tContents;
						
						if(contentType == 'R'){
							$('#tq_reply_trn_' + contentSeq).val('N');
							$('#tq_reply_' + contentSeq).html(tContents);
						}
						else if(contentType == 'S'){
							$('#ta_reply_trn_' + contentSeq).val('N');
							$('#ta_reply_' + contentSeq).html(tContents);
						}
						
						break;
					case 'N':
						alert('<%=CommonUtil.getLangMsg(request, "msg_0223")%>');
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
</script>
<c:forEach var="item" items="${answerList}" varStatus="status">
	<%
		Loopa--;
	%>
	<c:set var="content" value=""/>
	<c:choose>
		<c:when test="${item.sirenN < maxSiren}">
			<c:if test="${item.nick1 == null}"><c:set var="content" value="${item.reply}" /></c:if>
			<c:if test="${item.nick1 != null}">
				<fmt:formatNumber var="repAl" value="${item.almoney}" pattern="#,###" />
				<c:set var="content" value='${item.nick1}${msg_0224}${item.nick2}${msg_0225}${repAl}${msg_0226}' />
			</c:if>
		</c:when>
		<c:otherwise>
			<c:set var="content" value='${msg_0227}'/>
		</c:otherwise>
	</c:choose>
	<div class="replydiv_user_list atm_reply_el" data-seq="${item.seq}">
		<c:choose>
			<c:when test="${item.memberSeq eq UserSeq}">
				<c:set var="onReplyThClick" value="location.href='/member/myInfo'" />
				<c:set var="replyMemberProfileUrl" value="/member/myInfo" />
			</c:when>
			<c:otherwise>
				<c:set var="onReplyThClick" value="goSubmit('frm', '/member/otherProfileInfo?MemberSeq=${item.memberSeq}', '');" />
				<c:set var="replyMemberProfileUrl" value="/member/otherProfileInfo?MemberSeq=${item.memberSeq}" />
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${item.lv eq '98'}">
				<c:set var="classLv" value="system_message" />
			</c:when>
			<c:otherwise>
				<c:set var="classLv" value="" />
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${item.sirenN >= maxSiren}">
				<c:set var="classLv2" value="blind_txt" />
				<c:set var="content" value='${msg_0227}' />
			</c:when>
			<c:otherwise>
				<c:set var="classLv2" value="prgContent_QR" />
				<c:if test="${item.nick1 == null}">
					<c:set var="content" value="${item.reply}" />
				</c:if>
				<c:if test="${item.nick1 != null}">
					<fmt:formatNumber var="repAl" value="${item.almoney}"
						pattern="#,###" />
					<c:set var="content"
						value='${item.nick1}${msg_0224}${item.nick2}${msg_0225}${repAl}${msg_0226}' />
				</c:if>
			</c:otherwise>
		</c:choose>
		<table>
			<tr>
	            <th><a href="javascript:void(0)" onclick="${onReplyThClick}">
		            <c:choose>
					<c:when test="${item.photo != '' and fn:length(item.photo) > 0}">
						<img src="/UploadFile/Profile/${item.photo}" onerror="this.src='/pub/css/profile/img_thum_base0.jpg';" />
					</c:when>
					<c:otherwise>
						<img src="/pub/css/profile/img_thum_base0.jpg" />
					</c:otherwise>
					</c:choose>
					<c:if test="${item.memberSeq != 10000691 and  item.memberSeq != 10003513}">
					<img class="nation_flag" src="/Common/images/nation/${item.nation}.svg" alt="${item.nation}">
					<div class="reply_nation">${item.nation}</div>
					</c:if>
				</a>
				</th>
	            <c:if test="${item.nick1 != null}">
					<th class="system_message" id="ta_reply_${item.seq}">${content}</th>
				</c:if>
				<c:if test="${item.nick1 == null}">
					<th id="ta_reply_${item.seq}">${content}</th>
				</c:if>
	         </tr>
	        <tr>
	            <td></td>
	            <td><a href="${replyMemberProfileUrl}">${item.nickName}</a> · <b class="date_format2" id="${AnswerSeq}_rreply${status.index}">${item.conDate} <span>${item.conDate}</span></b> 
				<c:if test="${(item.memberSeq eq userSeq or userLv eq '99') and item.memberSeq ne '10003513' }">
				. <i onclick="goConfirm('sch_a_${AnswerSeq}', '/answer/replydel?ReplySeq=${item.seq}&Flag=A', '<spring:message code="msg_0328"/>', 'answerList_ifrm', this);"><spring:message code="msg_0228"/></i>
				</c:if>
				<c:if test="${item.memberSeq ne userSeq and item.lv < 90 and userLv < 90 and userseq ne '' and item.sirenN < maxSiren}">
				· <i onclick="fsubmitReport($(this).parents('.atm_reply_el'),'AR')"><spring:message code="msg_0229"/></i>
				</c:if>
				<!--//2차 개발
				 <div>
                    <img class="reply_add_honhon" src="/pub/answer/answerList/images/answer_almoney.svg" alt=<spring:message code="msg_0230"/>>
                    <span class="reply_honhon_btn">3,000</span>
                    <dl class="reply_honhon">
                    </dl>                
                </div>
                <div>
                    <div class="smile_icon"><img src="/Common/images/smile.svg" alt=<spring:message code="msg_0231"/>><b>30</b></div>
                    <div class="sad_icon"><img src="/Common/images/sad.svg" alt=<spring:message code="msg_0232"/>><b>1</b></div>                                                   
                </div>
                -->
                <input type="hidden" id="ta_reply_trn_${item.seq}" value="N"/>
                <c:if test="${item.lang != targetLang}">
                <div class="reply_lang_icon"><img class="answer_lang" src="/Common/images/language.svg" alt='<spring:message code="msg_0233"/>' id="arT_ai_${item.seq}" onclick="goTranslate_ar('${item.seq}', 'S');"></div>
                </c:if>
				</td>
	         </tr>
		</table>
	</div>
	<script>formatDate_ar('${AnswerSeq}_rreply${status.index}');</script>
</c:forEach>