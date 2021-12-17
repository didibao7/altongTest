<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp"%>
<%@ page import="com.altong.web.logic.util.CommonUtil"%>
<%@ page import="com.altong.web.logic.util.CodeUtil"%>
<%@ page import="java.util.*"%>
<% 

	CodeUtil code = new CodeUtil(request);
	Map<String, String> lvCd = code.getCODE_MEM_LV_CD();
	Map<String, String> lvNm = code.getCODE_MEM_LV_NM();
	
	String lvName = "0,";
	for (int i = 1; i <= lvCd.size(); i++) {
		lvName = lvName + "'" + lvNm.get(String.valueOf(i)) + "',";
		//System.out.println(i + " : " + lvName);
	}
	
	
	String msg_0236 = CommonUtil.getLangMsg(request, "msg_0236");
	String msg_0149 = CommonUtil.getLangMsg(request, "msg_0149");
	
	pageContext.setAttribute("msg_0236", msg_0236);
	pageContext.setAttribute("msg_0149", msg_0149);
%>
<head>
	<link rel="stylesheet" href="/pub/member/myQuestion/myQuestion.css?ver=1.4">
	<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.5">
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<div id="atm_mymentor_wrapper0">
	<div class="center">
		<form name="mypartner_frm_sch" method="post" onSubmit="return false;">
			<input type="hidden" name="src_Target" value="${curPageName}">
			<input type="hidden" name="pg" value="${n_curpage}">
			<input type="hidden" name="FlagPartner" value="${flagPartner}">
			<div class="atm_edittop_ttbar_pc">
				<p class="atm_edittop_c1"><spring:message code="msg_0177"/></p>
			</div>
			<c:if test="${flagPartner == 'M'}">
				<div class="select_wrapper">
					<div class="select_div">
						<div class="atm_temp_tab">
							<p  onclick="location.href='/member/myPartner?FlagPartner=M';" class="<c:if test="${flagFollower == ''}">temp_tab_on</c:if>">
								<spring:message code="msg_0892"/>
							</p>
						</div>
						<div class="atm_temp_tab">
							<p onclick="location.href='/member/myPartner?FlagPartner=M&FlagFollower=T';" class="<c:if test="${flagFollower != ''}">temp_tab_on</c:if>">
								<spring:message code="msg_0893"/>
							</p>
						</div>
					</div>
				</div>
			</c:if>
			<div class="myPartner_wrapper select_innerDiv">
				<div class="myPartner_friends select_view" id="select_view01">
					<p class="altong_title">내 
						<c:choose>
							<c:when test="${flagPartner == 'F'}"><spring:message code="msg_0810"/></c:when>
							<c:otherwise>
								<c:if test="${flagFollower == ''}"><spring:message code="msg_0892"/></c:if>
								<c:if test="${flagFollower != ''}"><spring:message code="msg_0893"/></c:if>
							</c:otherwise>
						</c:choose>
						<span class="atm_mycatebar_c2">${n_trec}</span><spring:message code="msg_0803"/></p>
					</p>
					<c:forEach var="item" items="${lsit}" varStatus="status">
						<c:set var="photo" value="${item.photo}"/>
						<c:if test="${photo == ''}">
							<c:set var="photo" value="img_thum_base0.jpg"/>
						</c:if>
						<c:set var="questionMoney" value="0"/>
						<c:if test="${item.sumQ != ''}">
							<c:set var="questionMoney" value="${item.sumQ}"/>
						</c:if>
						<c:set var="answerMoney" value="0"/>
						<c:if test="${item.sumA != ''}">
							<c:set var="answerMoney" value="${item.sumA}"/>
						</c:if>
						<c:set var="choiceRate" value="0"/>
						<c:if test="${item.countQ > 0}">
							<c:set var="choiceRate" value="${(item.countC/item.countQ) * 100}"/>
						</c:if>
						<div class="atm_profile_el atm_profile_el_mp" member_seq="${item.memberSeq}" nick_name="${item.nickName}" >
							<figure>
								<img src="/UploadFile/Profile/${photo}" class="atm_profile_img1" onerror="this.src='/pub/css/profile/img_thum_base0.jpg'" />
							</figure>
							<c:set var="usrLv" value="${item.lv}" />
							<%
								int lv = Integer.parseInt(String.valueOf(pageContext.getAttribute("usrLv")));
					
								String[] lvArr = lvName.split(",");
								String lvN = "";
								if (lv != 99) {
									lvN = lvArr[lv];
								}
								pageContext.setAttribute("lvN", lvN);
							
							%>
							<c:set var="lv" value="${lvN}" /><c:set var="lv" value="${lvN}" />
							<c:if test="${item.nickName == 'N' }">
								<c:set var="lv" value='${msg_0236}' />
							</c:if>
							<c:if test="${item.nickName != 'N' }">
								<c:if test="${usrLv == '99' }">
									<c:set var="lv" value='${msg_0149}' />
								</c:if>
								<c:if test="${usrLv != '99' }">
									<c:set var="lv" value="${lvN}" />
								</c:if>
							</c:if>
							<div class="atm_profile_eltexts" onclick="location.href='/member/otherProfileInfo?MemberSeq=${item.memberSeq}';">
								<p class="atm_profile_el_c2 nick"><span class="atm_profile_el_rank">${fn:replace(lv, "'", "")}</span>${item.nickName}</p>
								<em><spring:message code="msg_0253"/></em>
							</div>
							<div class="atm_profile_el_center">
								<p class="atm_profile_el_c3"><span class="atm_whitespace"><spring:message code="msg_0250"/> <span class="SumQ"><fmt:formatNumber value="${questionMoney}" pattern="#,###" /></span><spring:message code="msg_0136"/></span><span class="atm_normal"></span><span class="atm_whitespace"><spring:message code="msg_0251"/> <fmt:formatNumber value="${answerMoney}" pattern="#,###" /><spring:message code="msg_0136"/></span></p>
								<p class="atm_profile_el_c3"><span class="atm_whitespace"><spring:message code="msg_0252"/><span class="SumA"><fmt:formatNumber value="${item.countC}" pattern="#,###" /></span><spring:message code="msg_0814"/></span><span class="atm_normal"></span><span class="atm_whitespace"><spring:message code="msg_0239"/> <fmt:formatNumber value="${choiceRate}" pattern="#,###.#" />%</span></p>
							</div>
							<div class="atm_mymentor_xbtn">
								<i></i>
								<i></i>
								<i></i>
								
							</div>
							<div id="menuFriend" class="atm_profile_edit atm_profile_edit_mp">
								<div onclick="deleteFriend(${item.memberSeq})" class="atm_profile_c6">
									<img  src="/Common/images/delete.png"><spring:message code="msg_0228"/>
								</div>
								<div class="atm_profile_c6 openMessage">
									<img src="/Common/images/message_gg.png"><spring:message code="msg_0815"/>
								</div>
							</div>
							<div class="atm_profile_el_clear"></div>
							<p class="atm_av_c10 atm_av_c10_mp">
								<c:if test="${item.dateReg != ''}">
								<spring:message code="msg_0816"/> : <span class="regdate">{item.dateReg}</span>
								</c:if>
							</p>
						</div>
					</c:forEach>
				</div>
			</div>
		</form>
	</div>
</div>
<div class="more_btn_list" style="display: none;">
	<div class="user_message" style="display: none;">
		<h5>
			<span id="msg_name">닉네임</span><spring:message code="msg_0309"/>
		</h5>
		<form name="frm_msgSend">
			<input id="MemberSeq" type="hidden" name="MemberSeq" value="">
			<textarea name="Contents" class="message_usertext"></textarea>
		</form>
		<p class="message_btns">
			<span class="message_cancle">
				<spring:message code="msg_0159"/>
			</span>
			<span class="message_send" onclick="fAjax_m('/message/messageAjax', 'frm_msgSend', 'ACT=SEND')">
				<spring:message code="msg_0311"/>
			</span>
		</p>
	</div>

</div>
<div id="top_btn" style="display: block; opacity: 1;">
	<a href="javascript:void(0);">
		<span>
			<img src="/Common/images/top_btn_arrow.svg" alt="top_btn">
		</span>
	</a>
</div>
<script>
const deleteFriend = function(friendSeq) {
    // [추가(2018.01.24): 김현구]
    var ans = confirm(getLangStr("jsm_0008"));
    if ( ans == false ) return;

	var $form = $("<form></form>").attr({id:"form", method:"post"})
	var $friendSeq = $("<input type='hidden' value='" + friendSeq +"' name='friendSeq'>")

	$form.append($friendSeq)

	$.ajax({
		type: "POST",
		url: "/member/deleteFriend?FlagPartner=${flagPartner}",
		data: $form.serialize(),
		success: function() {
            //[수정(2018.01.24): 김현구]
			//alert('친구가 삭제되었습니다');
			location.reload();
		}
	});
};
$(document).ready(function(){
	$(".openMessage").click(function(e){
		var src = $(this).closest('.atm_profile_el');
		$('#msg_name').text(src.attr('nick_name'));
		$('.message_usertext').val('');
		
		fHideMenu();
		$('.user_message input[name=MemberSeq]').val(src.attr('member_seq'));
		
		$('.more_btn_list').stop().fadeIn();
		$('.user_message').stop().fadeIn();
	
	});
});
</script>
<script type="text/javascript" src="/pub/member/myPartner/myPartner.js?1.4" ></script>
</body>