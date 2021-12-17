<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<!-- 상위 헤드 -->
<link rel="stylesheet" href="/pub/member/myQuestion/myQuestion.css?ver=1.1">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.4">
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<div id="message_wrapper">
	<div class="center">
		<div class="atm_edittop_ttbar_pc" >
			<h1 class="atm_edittop_c1"><spring:message code="msg_0179"/></h1>
		</div>
		<div class="select_wrapper">
			<div class="select_div">
				<div class="atm_temp_tab"><p class="select_btn temp_tab_on" tar_obj="select_view01"><spring:message code="msg_0688"/></p></div>
				<div class="atm_temp_tab"><p class="select_btn" tar_obj="select_view02"><spring:message code="msg_0922"/></p></div>
			</div>
		</div>
		<div class="select_innerwrapper">
			<div class="msg_wrapper">
				<div class="receive_message select_view" id="select_view01" style="display:none">
					<p class="msg_el_tit">
						<span class="msg_tit01"><spring:message code="msg_0924"/></span>
						<span class="msg_tit02"><spring:message code="msg_0925"/></span>
						<span class="msg_tit03"><spring:message code="msg_0926"/></span>
					</p>
					<div class="msg_el_con" onClick="openMessage(this)" style="display:none">
						<div class="msg_el user_receive_msg">
							<div class="msg_user_profile">
                                <figure>
                                    <img class="msg_user_img" src="/Common/images/awssss.png">
                                </figure>
                                <span class="msg_user_name"><spring:message code="msg_0310"/></span>
                            </div>
							<span class="msg_user_text"></span>
							<span class="msg_time">'00.00.00 00:00</span>
							<span class="msg_img" onClick="fMsgDel(event, this)"><div class="msg_delete"><i></i><i></i></div></span>
						</div>
					</div>
				</div>
				<div class="send_message select_view" id="select_view02" style="display:none">
					<p class="msg_el_tit">
						<span class="msg_tit01"><spring:message code="msg_0923"/></span>
						<span class="msg_tit02"><spring:message code="msg_0925"/></span>
						<span class="msg_tit03"><spring:message code="msg_0926"/></span>
						<span class="msg_tit04"><spring:message code="msg_0833"/></span>
					</p>
					<div class="msg_el_con" onClick="openMessage(this)" style="display:none">
						<div class="msg_el user_receive_msg">
							<div class="msg_user_profile">
                                <figure>
                                    <img class="msg_user_img" src="../Common/images/awssss.png">
                                </figure>
                                <span class="msg_user_name"><spring:message code="msg_0310"/></span>
                            </div>
							<span class="msg_user_text"></span>
							<span class="msg_time">00.00.00 00:00</span>
							<span class="msg_user_read">Y</span>
							<span class="msg_img" onClick="fMsgDel(event, this)"><div class="msg_delete"><i></i><i></i></div></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		
	</div><!-- center end -->
</div><!-- message_wrapper end -->



<div class="user_message receive_popup">
	<i class="material-icons user_message_close"><b></b><b></b></i>
	<div class="user_message_top">
		<figure>
            <img src="../Common/images/awssss.png" alt='<spring:message code="msg_0162"/>'>
        </figure>
		<div class="user_message_top_text">
			<p class="user_message_top_p">
				<span class="user_name_block"></span>
				<span class="user_name">젤리맴☆</span><spring:message code="msg_0927"/>
			</p>
			<p class="user_message_top_p"><span class="msg_time">0000년 00월 00일 수요일 오전 00시 00분</span></p>
		</div>
	</div>
	<form onSubmit="return false">
	<div class="user_message_mid">
		<input type="hidden" name="MsgSeq">
		<input type="hidden" name="MemberSeq">
		<div><textarea class="msg_read_ta" readonly>자전거도로에서 자전거에 치어 다쳤다면, 보상받을수 있나요~? 산책길과 자전거도로 나눠져있는 곳에서 산책길로 달려오는 자전거 피하다가 자전거도로로 달려오는 자전거에 치어 다쳤다면 보상받을수 있나요~? 받을수 있다면 누구한테 받는건가요~?</textarea></div>
		
	</div>
	<div>
		<textarea name="Contents" class="msg_write_ta msg_write_ta01" placeholder='<spring:message code="msg_0928"/>'></textarea>
		<span class="user_send_btn"><img src="/Common/images/message_ww.png" alt='<spring:message code="msg_0248"/>'></span>
	</div>
	</form>
	<div class="user_message_bot">
		<div class="user_message_bot_img msg_del_btn">
			<img src="/Common/images/trash.png"></br>
			<span><spring:message code="msg_0228"/></span>
		</div>
		<div class="user_message_bot_img user_message_bot_img02 user_message_block">
			<i class="material-icons">block</i></br>
			<span><spring:message code="msg_0929"/></span>
		</div>
		
	</div>
	<div class="user_message_arrow user_message_arrow_left">
		<i class="material-icons" onClick="fGetMsg(this, 'NEXT')">arrow_back_ios</i>
	</div>
	<div class="user_message_arrow user_message_arrow_right">
		<i class="material-icons" onClick="fGetMsg(this, 'PREV')">arrow_forward_ios</i>
	</div>
	<div class="block_add_toast block_toast">
		<spring:message code="msg_0930"/></br></br><spring:message code="msg_0931"/>
	</div>
	<div class="block_remove_toast block_toast">
		<spring:message code="msg_0932"/>
	</div>
</div>
<div id="black_screen"></div>

<div class="user_message send_popup">
	<i class="material-icons user_message_close"><b></b><b></b></i>
	<div class="user_message_top">
		<figure>
            <img src="/Common/images/awssss.png" alt="">
        </figure>
		<div class="user_message_top_text">
			<p class="user_message_top_p">
				<span class="user_name_block"></span>
				<span class="user_name">닉네임</span><spring:message code="msg_0933"/>
			</p>
			<p class="user_message_top_p"><spring:message code="msg_0926"/> : <span class="msg_time">0000년 00월 00일 수요일 오전 00시 00분</span></p>
		</div>
	</div>
	<form onSubmit="return false">
		<div class="user_message_mid">
			<input type="hidden" name="MsgSeq">
			<input type="hidden" name="MemberSeq">
			<div><textarea class="msg_read_ta" readonly></textarea></div>
		</div>
		<div>
			<textarea name="Contents" class="msg_write_ta msg_write_ta02" placeholder='<spring:message code="msg_0928"/>'></textarea>
			<span class="user_send_btn"><img src="/Common/images/message_ww.png" alt='<spring:message code="msg_0248"/>'></span>
		</div>
	</form>
	<div class="user_message_bot">
		<div class="user_message_bot_img msg_del_btn">
			<img src="/Common/images/trash.png"></br>
			<span><spring:message code="msg_0228"/></span>
		</div>
	</div>
	<div class="user_message_arrow user_message_arrow_left">
		<i class="material-icons" onClick="fGetMsg(this, 'NEXT')">arrow_back_ios</i>
	</div>
	<div class="user_message_arrow user_message_arrow_right">
		<i class="material-icons" onClick="fGetMsg(this, 'PREV')">arrow_forward_ios</i>
	</div>
</div>

<div id="divProg" style="width:100%;display:none"><div style="width:30px;margin:auto"><img src="/Common/images/search_nick_loader.gif" style="width:100%"></div></div>

<div id="top_btn">
    <a href="#">
        <span>
            <img src="/Common/images/top_btn_arrow.svg" alt="top_btn">
        </span>
    </a>
</div>
<script type="text/javascript" src="/Common/message/js/msg.js?ver=1.6"></script>
</body>
</html>