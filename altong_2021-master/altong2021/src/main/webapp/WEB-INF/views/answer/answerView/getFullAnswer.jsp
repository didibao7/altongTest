<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<script src="http://code.jquery.com/jquery-1.12.4.js"></script>
<%
	int maxSiren = 3;

	JSONObject global = (JSONObject)CommonUtil.getGlobal(request, response);
	
	String AnswerSeq = request.getAttribute("AnswerSeq").toString();
	
	pageContext.setAttribute("maxSiren", maxSiren);
	
	if(global != null) {
		pageContext.setAttribute("userSeq", String.valueOf(global.get("UserSeq")));
		pageContext.setAttribute("userLv", String.valueOf(global.get("UserLv")));
		pageContext.setAttribute("UserNickName", String.valueOf(global.get("UserNickName")));
	}
	else {
		pageContext.setAttribute("userSeq", "");
		pageContext.setAttribute("userLv", "");
		pageContext.setAttribute("UserNickName", "");
	}
	pageContext.setAttribute("pointCountNo", request.getAttribute("pointCountNo").toString());
	
	
	
	String msg_0224 = CommonUtil.getLangMsg(request, "msg_0224");
	String msg_0225 = CommonUtil.getLangMsg(request, "msg_0225");
	String msg_0226 = CommonUtil.getLangMsg(request, "msg_0226");
	String msg_0227 = CommonUtil.getLangMsg(request, "msg_0227");
	
	pageContext.setAttribute("msg_0224", msg_0224);
	pageContext.setAttribute("msg_0225", msg_0225);
	pageContext.setAttribute("msg_0226", msg_0226);
	pageContext.setAttribute("msg_0227", msg_0227);
%>
<c:set var="QuestionSeq" value="${answer.questionSeq}"/>
<%
	String questionSeq = pageContext.getAttribute("QuestionSeq").toString();
%>
<div class="answer_tab_el2 prgContent_A" id="ansT_${AnswerSeq}">
${answer.answer}
</div>
<form name="frm_ans_${AnswerSeq}" method="post">
	<input type="hidden" name="AnswerSeq" value="${AnswerSeq}">
</form>

<input type="hidden" id="at_${AnswerSeq}" value="${tSeq}" />
<c:if test="${answerLv != ''}">
<script>
    $(function(){
    	/* 번역에 대한 평가 */
        $('.lang_assessment .lang_good').click(function(){
            $(this).find('img').attr('src', '/Common/images/smile_on.svg');
            $(this).parents('.lang_assessment').find('.lang_bad > img').attr('src', '/Common/images/sad.svg');
            $(this).find('span').css({color:'#ff3300'});
            $(this).parents('.lang_assessment').find('.lang_bad > span').css({color:'#999999'});
        });
        $('.lang_assessment .lang_bad').click(function(){
            $(this).find('img').attr('src', '/Common/images/sad_on.svg');
            $(this).parents('.lang_assessment').find('.lang_good > img').attr('src', '/Common/images/smile.svg');
            $(this).find('span').css({color:'#ff3300'});
            $(this).parents('.lang_assessment').find('.lang_good > span').css({color:'#999999'});
        });
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
</script> 
<div class="lang_assessment" <c:if test="${tSeq == 0}">style="display:none"</c:if> id="lang_ans_${AnswerSeq}">
    <em><spring:message code="msg_0254"/></em>
    <div class="lang_good">
    	<img src="/Common/images/smile.svg" alt='<spring:message code="msg_0231"/>' onclick="goQuestionVote_new('G','${AnswerSeq}', 'T', 'at');">
    	<span id="at_lang_good_${AnswerSeq}"><fmt:formatNumber value="${tGood}" pattern="#,###" /></span>
    </div>
    <div class="lang_bad">
    	<img src="/Common/images/sad.svg" alt='<spring:message code="msg_0232"/>' onclick="goQuestionVote_new('B','${AnswerSeq}', 'T', 'at');">
    	<span id="at_lang_bad_${AnswerSeq}"><fmt:formatNumber value="${tBad}" pattern="#,###" /></span>
    </div>
    <c:if test="${answerLv < 99 and tUserSeq ne aUserSeq and tUserLv < 90 and tSirenN < tMaxSiren}">
    <img class="lang_report" id="at_lang_report_${AnswerSeq}" src="/pub/answer/answerList/images/atm_more_3.svg" alt="신고" onclick="fsubmitReport($(this).parents('.lang_assessment'),'T')");">
    </c:if>
</div>
</c:if>
<c:if test="${answerLv == ''}">
<div class="lang_assessment" <c:if test="${tSeq == 0}">style="display:none"</c:if> id="lang_ans_${AnswerSeq}">
    <em><spring:message code="msg_0254"/></em>
    <div class="lang_good">
         <img src="/Common/images/smile.svg" alt='<spring:message code="msg_0231"/>' onclick="alert('<spring:message code="msg_0168"/>');">
         <span id="at_lang_good_${AnswerSeq}"><fmt:formatNumber value="${tGood}" pattern="#,###" /></span>
    </div>
    <div class="lang_bad">
         <img src="/Common/images/sad.svg" alt='<spring:message code="msg_0232"/>' onclick="alert('<spring:message code="msg_0168"/>');">
         <span id="at_lang_bad_${AnswerSeq}"><fmt:formatNumber value="${tBad}" pattern="#,###" /></span>
    </div>
	<img class="lang_report" id="at_lang_report_${AnswerSeq}" src="/pub/answer/answerList/images/atm_more_3.svg" alt="신고" onclick="alert('<spring:message code="msg_0168"/>');">
</div>
</c:if>

<div class="atm_answer_more">
<c:if test="${userSeq eq answer.memberSeq or userLv == '99'}">
    <c:choose>
		<c:when test="${answer.flagChoice eq 'Y' and userLv != '99'}">
			<p onClick="alert('<spring:message code="msg_0371"/>');"><spring:message code="msg_0256"/></p>
			<!-- <p onClick="alert('<spring:message code="msg_0372"/>');"><spring:message code="msg_0328"/></p> -->
		</c:when>
		<c:otherwise>
			<p onClick="javascript:location.href='/answer/answerEdit?Seq=<%=AnswerSeq%>&QuestionSeq=<%=questionSeq%>';"><spring:message code="msg_0256"/></p>
			<!-- <p onClick="goConfirm('frm_ans_<%=AnswerSeq%>', '/answer/answerDelete?AnswerSeq=<%=AnswerSeq%>', '<spring:message code="msg_0328"/>', 'answerList_ifrm');"><spring:message code="msg_0328"/></p> -->
		</c:otherwise>
	</c:choose>
</c:if>
</div>
    <ul class="altong_icon">
    <li class="<c:if test="${pointCountNo eq '1'}">check_icon</c:if>"><a href="javascript:void(0)" onClick="goEstimate('/answer/answerEstimate?Gubun=A&EstimateSeq=<%=AnswerSeq%>_1&AnswerMemberSeq=${answer.memberSeq}&QuestionSeq=<%=questionSeq%>');"><img src="../Common/images/esti_1.png" alt='<spring:message code="msg_0347"/>'><span><spring:message code="msg_0347"/><em id="<%=AnswerSeq%>_1">${answer.pointCount1}</em></span></a></li>
    <li class="<c:if test="${pointCountNo eq '2'}">check_icon</c:if>"><a href="javascript:void(0)" onClick="goEstimate('/answer/answerEstimate?Gubun=A&EstimateSeq=<%=AnswerSeq%>_2&AnswerMemberSeq=${answer.memberSeq}&QuestionSeq=<%=questionSeq%>');"><img src="../Common/images/esti_2.png" alt='<spring:message code="msg_0348"/>'><span><spring:message code="msg_0348"/><em id="<%=AnswerSeq%>_2">${answer.pointCount2}</em></span></a></li>
    <li class="<c:if test="${pointCountNo eq '3'}">check_icon</c:if>"><a href="javascript:void(0)" onClick="goEstimate('/answer/answerEstimate?Gubun=A&EstimateSeq=<%=AnswerSeq%>_3&AnswerMemberSeq=${answer.memberSeq}&QuestionSeq=<%=questionSeq%>');"><img src="../Common/images/esti_3.png" alt='<spring:message code="msg_0349"/>'><span><spring:message code="msg_0349"/><em id="<%=AnswerSeq%>_3">${answer.pointCount3}</em></span></a></li>
    <li class="<c:if test="${pointCountNo eq '4'}">check_icon</c:if>"><a href="javascript:void(0)" onClick="goEstimate('/answer/answerEstimate?Gubun=A&EstimateSeq=<%=AnswerSeq%>_4&AnswerMemberSeq=${answer.memberSeq}&QuestionSeq=<%=questionSeq%>');"><img src="../Common/images/esti_4.png" alt='<spring:message code="msg_0350"/>'><span><spring:message code="msg_0350"/><em id="<%=AnswerSeq%>_4">${answer.pointCount4}</em></span></a></li>
    <li class="<c:if test="${pointCountNo eq '5'}">check_icon</c:if>"><a href="javascript:void(0)" onClick="goEstimate('/answer/answerEstimate?Gubun=A&EstimateSeq=<%=AnswerSeq%>_5&AnswerMemberSeq=${answer.memberSeq}&QuestionSeq=<%=questionSeq%>');"><img src="../Common/images/esti_5.png" alt='<spring:message code="msg_0351"/>'><span><spring:message code="msg_0351"/><em id="<%=AnswerSeq%>_5">${answer.pointCount5}</em></span></a></li>
    <li class="<c:if test="${pointCountNo eq '6'}">check_icon</c:if>"><a href="javascript:void(0)" onClick="goEstimate('/answer/answerEstimate?Gubun=A&EstimateSeq=<%=AnswerSeq%>_6&AnswerMemberSeq=${answer.memberSeq}&QuestionSeq=<%=questionSeq%>');"><img src="../Common/images/esti_6.png" alt='<spring:message code="msg_0352"/>'><span><spring:message code="msg_0352"/><em id="<%=AnswerSeq%>_6">${answer.pointCount6}</em></span></a></li>
</ul>
<script>
	$(document).ready(function() {
		function resizeEsti() {
			const $estiWrapper = $(".atm_estimation").parent();
			const $estiDirectUp = $(".atm_esti_directup");
			const $estiDirectUpText = $(".atm_esti_directup_text");

			var leftOffset = ($estiWrapper.width() - 170) / 2;

			$estiDirectUp.css('left', leftOffset);
			$estiDirectUpText.css('left', leftOffset);
		}

		//resizeEsti();
		//$(window).resize(resizeEsti);
	});
	/* 댓글 토글 버튼 */
    $('.a_reply${AnswerSeq}').click(function(){
        $(this).parents('ol').next('.replydiv').stop().slideToggle(300);
    });
</script>


<div class="languages_translation">
   <div class="lang_origin<c:if test='${tSeq == 0}'> pick</c:if>" id="ansT_origin_${AnswerSeq}" onclick="goAnswer('${AnswerSeq}', 'at')"><spring:message code="msg_0257"/></div>
   <c:if test="${ansMachineCnt > 0}"></c:if>
   <div class="lang_ai<c:if test='${tSeq > 0 and trnType == 1}'> pick</c:if>"  id="ansT_ai_${AnswerSeq}" onclick="goTranslate('${AnswerSeq}', 'A');">
       <div class="lang_image">AI</div>
       <!-- 
       <div class="lang_view" id="lang_view_ans_${AnswerSeq}">
           <ul class="lang_view_el01">
               <li><img src="/Common/images/icon_view.svg" alt=""><span id="at_count_${AnswerSeq}"><fmt:formatNumber value="${tAiCount}" pattern="#,###" /></span></li>
           </ul>
           <dl>
           </dl>
       </div>
        -->
   </div>
   
   <!--// 사람 번역 구현(2차 개발)
                            <div class="lang_profile profileLeft">
                                <div class="lang_image">
                                    <img src="../Common/images/awssss.png" alt="프로필 이미지">
                                    <img class="nation_flag" src="/Common/images/nation/ca.svg" alt="캐나다">
                                </div>
                                <div class="lang_view">
                                    <ul class="lang_view_el01">
                                        <li><b>3분 전<span>2020-12-20 17:15:30</span></b> · <img src="/Common/images/icon_view.svg" alt=""><span>50</span></li>
                                        <li class="lang_nick">Ronaldo</li>
                                    </ul>
                                    <p class="lang_view_el02"><img src="/pub/answer/answerList/images/answer_almoney.svg" alt='<spring:message code="msg_0230"/>'><span>3,000</span></p>
                                    <dl>
                                        <dt><spring:message code="msg_0235"/></dt>
                                        <dd><img src="/Common/images/nation/us.svg" alt=""><b>영웅본색 (10,000알) </b>8개월 전</dd>
                                        <dd><img src="/Common/images/nation/cn.svg" alt=""><b>관리자 (1,000알) </b>7일 전</dd>
                                        <dd><img src="/Common/images/nation/au.svg" alt=""><b>오즈큐 (1,000알) </b>2개월 전</dd>
                                    </dl>
                                </div>
                                <div class="profile_mini">
                                    <span>수호천사</span>
                                    <table class="profile_mini_info">
                                        <tr>
                                            <th>BTS_jjjang</th>
                                            <th rowspan="2"><img src="../Common/images/addFriends.svg" alt='<spring:message code="msg_0246"/>'></th>
                                            <th rowspan="2"><img src="../Common/images/addMento.svg" alt='<spring:message code="msg_0247"/>'></th>
                                            <th rowspan="2" class="message_send"><img src="../Common/images/message.svg" alt='<spring:message code="msg_0248"/>'></th>
                                        </tr>
                                        <tr>
                                            <td><spring:message code="msg_0249"/> : <span>13,962,921</span><spring:message code="msg_0136"/></td>
                                        </tr>
                                    </table>
                                    <p>"<spring:message code="msg_0258"/>"</p>
                                    <i></i>
                                    <table class="profile_mini_almoney">
                                        <tr>
                                            <td><spring:message code="msg_0250"/></td>
                                            <td><spring:message code="msg_0251"/></td>
                                            <td><spring:message code="msg_0259"/></td>
                                            <td><spring:message code="msg_0260"/></td>
                                        </tr>
                                        <tr>
                                            <th>4,212,873알</th>
                                            <th>1,725알</th>
                                            <th>397</th>
                                            <th>97.6%</th>
                                        </tr>
                                    </table>
                                    <a href="#"><spring:message code="msg_0253"/></a>
                                </div>    
                            </div>
                            <div class="lang_profile">
                                <div class="lang_image">
                                    <img src="../Common/images/awssss.png" alt="프로필 이미지">
                                    <img class="nation_flag" src="/Common/images/nation/de.svg" alt="독일">
                                </div>
                                <div class="lang_view">
                                    <ul class="lang_view_el01">
                                        <li><b>3분 전<span>2020-12-20 17:15:30</span></b> · <img src="/Common/images/icon_view.svg" alt=""><span>50</span></li>
                                        <li class="lang_nick">Ronaldo</li>
                                    </ul>
                                    <p class="lang_view_el02"><img src="/pub/answer/answerList/images/answer_almoney.svg" alt='<spring:message code="msg_0230"/>'><span>3,000</span></p>
                                    <dl>
                                        <dt><spring:message code="msg_0235"/></dt>
                                        <dd><img src="/Common/images/nation/us.svg" alt=""><b>영웅본색 (10,000알) </b>8개월 전</dd>
                                        <dd><img src="/Common/images/nation/cn.svg" alt=""><b>관리자 (1,000알) </b>7일 전</dd>
                                        <dd><img src="/Common/images/nation/au.svg" alt=""><b>오즈큐 (1,000알) </b>2개월 전</dd>
                                    </dl>
                                </div>
                                <div class="profile_mini">
                                    <span>수호천사</span>
                                    <table class="profile_mini_info">
                                        <tr>
                                            <th>BTS_jjjang</th>
                                            <th rowspan="2"><img src="../Common/images/addFriends.svg" alt='<spring:message code="msg_0246"/>'></th>
                                            <th rowspan="2"><img src="../Common/images/addMento.svg" alt='<spring:message code="msg_0247"/>'></th>
                                            <th rowspan="2" class="message_send"><img src="../Common/images/message.svg" alt='<spring:message code="msg_0248"/>'></th>
                                        </tr>
                                        <tr>
                                            <td><spring:message code="msg_0249"/> : <span>13,962,921</span><spring:message code="msg_0136"/></td>
                                        </tr>
                                    </table>
                                    <p>"<spring:message code="msg_0258"/>"</p>
                                    <i></i>
                                    <table class="profile_mini_almoney">
                                        <tr>
                                            <td><spring:message code="msg_0250"/></td>
                                            <td><spring:message code="msg_0251"/></td>
                                            <td><spring:message code="msg_0259"/></td>
                                            <td><spring:message code="msg_0260"/></td>
                                        </tr>
                                        <tr>
                                            <th>4,212,873알</th>
                                            <th>1,725알</th>
                                            <th>397</th>
                                            <th>97.6%</th>
                                        </tr>
                                    </table>
                                    <a href="#"><spring:message code="msg_0253"/></a>
                                </div>    
                            </div>
                            -->
</div>


<ol class="answer_reply_btn">
    <li class="reply a_reply${AnswerSeq}"><a href="javascript:void(0)" onclick="reply(this, 'replydiv');$(this).toggleClass('on')"><img src="/Common/images/icon_reply.svg" alt="replyicon"><span>${answerReplyCount}</span></a></li>
    <li></li>
    <li class="answer_lang_btn">
	    <c:if test="${FlagChoiceQ ne 'Y' and  MemberSeq eq userSeq}">
	    <a class="choose" href="javascript:void(0)" onClick="javascript:goConfirm('frm_esti', '/answer/answerChoice?AnswerSeq=${AnswerSeq}&MemberSeq=${answer.memberSeq}', '<spring:message code="msg_0374"/>', 'answerList_ifrm');"><spring:message code="msg_0373"/> </a>
	    </c:if>
		<a class="lang_btn" href="javascript:void(0)" onclick='javascript:alert(getLangStr("jsm_0065") + "\n" +  getLangStr("jsm_0066") + "\n" +  getLangStr("jsm_0067") + "\n" + getLangStr("jsm_0068"))'><spring:message code="msg_0233"/></a>
    </li>
</ol>
<div class="replydiv" id="replyWrapper_<%=AnswerSeq%>">
	<!-- [추가(2018.01.24): 김현구] 댓글 글자 입력수 CHECK -->
	<script language="javascript">
		$('#replyWrapper_<%=AnswerSeq%>').find('textarea').on('keyup', function() {
			var count = $(this).val().length;
			const counter = $('#replyWrapper_<%=AnswerSeq%>').find('.reply_submit').find('p');
			if (count >= 400) {
				count = '400';
			}
			counter.html(( '<span>' + count + '</span>/400'));
		});
	</script>
	<form name="sch_a_<%=AnswerSeq%>" method="post" onsubmit="return false;">
	<input type="hidden" name="AnswerSeq" value="${AnswerSeq}">
    <div class="replydiv_el">
    	<c:if test="${userSeq != ''}">
        <textarea name="Contents" onkeyup="freplyResize(this)" maxlength="400" placeholder='${UserNickName}<spring:message code="msg_0264"/>'></textarea>
        </c:if>
        <c:if test="${userSeq == ''}">
        <textarea name="Contents" onkeyup="freplyResize(this)" maxlength="400" placeholder='<spring:message code="msg_0168"/>'></textarea>
        </c:if>
        <div class="reply_renew"><p onclick="fViewQReply('<%=AnswerSeq%>', 'A', true)"><img src="/pub/answer/answerList/images/autorenew.svg" alt=""><spring:message code="msg_0265"/></p></div>
        <div class="reply_submit">
            <p><span>0</span>/400</p>
            <c:if test="${userSeq != ''}">
            <button type="submit" onclick="goSubmit('sch_a_<%=AnswerSeq%>', '/answer/reply?TargetSeq=<%=AnswerSeq%>&Flag=A', 'answerList_ifrm', this);"><img src="../Common/images/modify03.svg" alt='<spring:message code="msg_0267"/>'><i><spring:message code="msg_0266"/></i></button>
            </c:if>
            <c:if test="${userSeq == ''}">
            <button type="submit" onclick='alert(getLangStr("jsm_0020"));'><img src="../Common/images/modify03.svg" alt='<spring:message code="msg_0267"/>'><i><spring:message code="msg_0266"/></i></button>
            </c:if>
        </div>
    </div>
    </form>
    <div class="replydiv_user">
        <div class="replydiv_user_list">
        <c:set var="content" value="" />
        <c:forEach var="r" items="${replyList}" varStatus="status">
        <c:choose>
			<c:when test="${r.sirenN < maxSiren}">
				<c:if test="${r.nick1 == null}"><c:set var="content" value="${r.reply}" /></c:if>
				<c:if test="${r.nick1 != null}">
					<fmt:formatNumber var="repAl" value="${r.almoney}" pattern="#,###" />
					<c:set var="content" value='${r.nick1}${msg_0224}${r.nick2}${msg_0225}${repAl}${msg_0226}' />
				</c:if>
			</c:when>
			<c:otherwise>
				<c:set var="content" value='${msg_0227}' />
			</c:otherwise>
		</c:choose>
		<c:set var="replySeq" value="${r.seq}"/>
		<c:set var="memberSeq" value="${r.memberSeq}"/>
		<%
			String ReplySeq = pageContext.getAttribute("replySeq").toString();
			String MemberSeq = pageContext.getAttribute("memberSeq").toString();
		%>
            <table>
                <tr>
                    <th>
                    <c:choose>
						<c:when test="${r.memberSeq == userSeq}">
							<a href="javascript:void(0)" onClick="location.href='/member/myInfo'">
							<c:choose>
								<c:when test="${r.photo != '' and fn:length(r.photo) > 0}">
									<img src="/UploadFile/Profile/${r.photo}"  onerror="this.src='/pub/css/profile/img_thum_base0.jpg';"/>
								</c:when>
								<c:otherwise>
									<img src="/pub/css/profile/img_thum_base0.jpg" />
								</c:otherwise>
							</c:choose>
							<c:if test="${r.memberSeq != 10000691 and  r.memberSeq != 10003513}">
							<img class="nation_flag" src="/Common/images/nation/${r.nation}.svg" alt="${r.nation}">
							<div class="reply_nation">${r.nation}</div>
							</c:if>
							</a>
						</c:when>
						<c:otherwise>
							<a href="javascript:void(0)" onClick="javascript:goSubmit('frm', '/member/otherProfileInfo?MemberSeq=' + ${r.memberSeq}, '');">
							<c:choose>
								<c:when test="${r.photo != '' and fn:length(r.photo) > 0}">
									<img src="/UploadFile/Profile/${r.photo}"  onerror="this.src='/pub/css/profile/img_thum_base0.jpg';"/>
								</c:when>
								<c:otherwise>
									<img src="/pub/css/profile/img_thum_base0.jpg" />
								</c:otherwise>
							</c:choose>
							<c:if test="${r.memberSeq != 10000691 and  r.memberSeq != 10003513}">
							<img class="nation_flag" src="/Common/images/nation/${r.nation}.svg" alt="${r.nation}">
							<div class="reply_nation">${r.nation}</div>
							</c:if>
							</a>
						</c:otherwise>
					</c:choose>
                    </th>
                    <c:if test="${r.nick1 != null}">
						<th class="system_message" id="ta_reply_${r.seq}">${content}</th>
					</c:if>
					<c:if test="${r.nick1 == null}">
						<th id="ta_reply_${r.seq}">${content}</th>
					</c:if>
                </tr>
                <tr>
                    <td></td>
                    <td>
                    <a href="javascript:replyAnsRegi('<%=AnswerSeq%>','${r.nickName}')">${r.nickName}</a>
					 · <b class="date_format" id="<%=AnswerSeq%>_reply${status.index}">${r.conDate} <span>${r.conDate}</span></b>
					 <c:if test="${(r.memberSeq eq userSeq or userLv eq '99') and r.memberSeq ne '10003513'}">
					 · <i onClick="goConfirm('sch_a_<%=AnswerSeq%>', '/answer/replydel?ReplySeq=<%=ReplySeq%>&Flag=A', '<spring:message code="msg_0228"/>', 'answerList_ifrm', this);"><spring:message code="msg_0228"/></i>
					 </c:if>
					 <c:if test="${r.memberSeq ne userSeq and r.lv < 90 and userLv < 90 and userSeq ne '' and r.sirenN < maxSiren}">
					 · <i onclick="fsubmitReport($(this).parents('.atm_reply_el'),'AR');"><spring:message code="msg_0229"/></i>
					 </c:if>
					 <!--//2차 개발
					 <div>
                         <img class="reply_add_honhon" src="/pub/answer/answerList/images/answer_almoney.svg" alt='<spring:message code="msg_0230"/>'>
                         <span class="reply_honhon_btn">3,000</span>
                         <dl class="reply_honhon">
                         </dl>                
                     </div>
                     <div>
                         <div class="smile_icon"><img src="/Common/images/smile.svg" alt='<spring:message code="msg_0231"/>'><b>30</b></div>
                         <div class="sad_icon"><img src="/Common/images/sad.svg" alt='<spring:message code="msg_0232"/>'><b>1</b></div>                                                   
                     </div>
                     -->
                     <input type="hidden" id="ta_reply_trn_${r.seq}" value="N"/>
                     <div class="reply_lang_icon"><img class="answer_lang" src="/Common/images/language.svg" alt='<spring:message code="msg_0233"/>' id="arT_ai_${r.seq}" onclick="goTranslate_qr_ar('${r.seq}', 'S');"></div>
					 </td>
                </tr>
            </table>
            <script>formatDateForSpan("<%=AnswerSeq%>_reply${status.index}");</script>
        </c:forEach>   
        </div>
    </div>
</div>