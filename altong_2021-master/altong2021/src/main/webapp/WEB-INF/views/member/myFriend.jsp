<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp"%>
<head>
<link rel="stylesheet" href="/pub/member/myQuestion/myQuestion.css?ver=1.4">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.4">
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<script>

function fAjax_m(url, frm, param) {
	if (document.xhr2) {
		//$('#Tip').text('이전 작업이 진행중입니다.').css('display', 'block');
		//setTimeout(function () { $('#Tip').stop().fadeOut(300) }, 3000);
		//return;
	}
	
	var async = false;
	
	document.xhr2 = $.ajax({
		url: url,
		type: 'post',
		data: (frm ? $('form[name=' + frm + ']').serialize() + '&' : '') + param,
		dataType: 'json',
		async: async,
		success: function (r) {
			switch (r.result) {
				case 'SEND':
					alert(getLangStr("jsm_0078"));
					$('.message_cancle').click();
					break;
				case 'SET_BLOCK':
					if (r.arr[0].isBlock == 'N')
					{
						alert('getLangStr("jsm_0079")');
						$('#select_view02 .atm_mycatebar_c2').text(r.arr[0].cnt);
						$('#select_view02 .atm_profile_el[member_seq=' + r.arr[0].MemberSeq + ']').slideUp(function(){$(this).remove()});
					}
					break;
				default:
					if (r.result) alert(r.result);
					break;
			}
		},
		error: function (r, textStatus, err) {
			if (r.responseText && r.responseText.substr(0, 13) == '<!--LOGOFF-->') { top.location = '/'; return; }
			console.log(r);
		},
		complete: function () {
			document.xhr2 = false;
		}
	});
}

var pg = 0;
function fAjax_List(data) {
	if (document.xhr2) return;

	if (pg++)
	{
		$('#divProg').slideDown(300);
		setTimeout(fMakeRow, 1000);
	}
	else fMakeRow();

	document.xhr2 = $.ajax({
		url: '/member/myFriendAjax',
		type: 'post',
		data: 'PG=' + pg + (data ? '&' + data : ''),
		dataType: 'json',
		success: function (r) {
			switch (r.result)
			{
				case 'LIST_FRIEND':
				case 'LIST_BLOCK':
					document.r = r;
					fMakeRow();
					break;
			}
		},
		error: function (r, textStatus, err) {
			$('#divProg').slideUp();
			$(window).unbind("scroll");
			console.log(r);
		},
	});
}
function fMakeRow()
{
	if (!document.isShow)
	{
		document.isShow = true;
		return;
	}
	
	var $frame = (document.r.result == 'LIST_FRIEND') ? $('#select_view01') : $('#select_view02');
	//console.log(document.r.arr[0][0].cnt);
	$frame.find('.atm_mycatebar_c2').text(document.r.arr[0][0].cnt);

	var newObj, target = $frame.find('.atm_profile_el:first');
	
	document.r.arr[1].forEach(function(v) {
		newObj = target.clone();
		newObj.attr('member_seq', v.MemberSeq);
		newObj.find('.atm_profile_img1').attr('src', '/UploadFile/Profile/' + (v.Photo ? v.Photo : 'img_thum_base0.jpg'));
		
		if(document.r.result == 'LIST_FRIEND') {
			newObj.find('.nick').html('<span class="atm_profile_el_rank">' + v.Lv + '</span>' + v.NickName);
			newObj.find('.nick').attr('value',v.NickName);
		}
		else {
			newObj.find('.nick').text(v.NickName);
			newObj.find('.nick').attr('value',v.NickName);
		}
		newObj.find('.regdate').text(v.conDate);
		newObj.find('.SumQ').text(fCommas(v.SumQ*1));
		newObj.find('.SumA').text(fCommas(v.SumA*1));
		newObj.find('.CountC').text(fCommas(v.CountC*1));
		newObj.find('.Rate').text(v.CountQ ? (v.CountC/v.CountQ*100).toFixed(1) : 0);
		newObj.appendTo($frame).slideDown(100);
	});
	

	$(window).unbind("scroll");
	if (document.r.arr[0].length)
	{
		$(window).scroll(function() {
			if($(window).scrollBottom() < 150) fAjax_List('ACT=' + document.r.result);
		});
	}


	$('#divProg').slideUp();
	document.isShow = false;
	document.xhr2 = false;
}

function fCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function fGoProfile(obj)
{
	window.open('/member/otherProfileInfo?MemberSeq=' + $(obj).parent().attr('member_seq'));
}

function fShowMenu(e, obj)
{
	//e.stopPropagation();

	var menuBox = $('#menuFriend');

	if ($(obj).next().attr('id') == 'menuFriend')
		fHideMenu();
	else 
		menuBox.insertAfter(obj).show();
}

function fHideMenu()
{
	$('#menuFriend').appendTo('Body').hide();
}

function fDeleteFriend(e, obj)
{
	e.stopPropagation();

	var src = $(obj).closest('.atm_profile_el');
	fHideMenu();

	if (confirm(src.find('.nick').attr('value') + getLangStr("jsm_0080")))
	{
		$.ajax({
			type: "POST",
			url: "/member/deleteFriend",
			data: 'FlagPartner=F&friendSeq=' + src.attr('member_seq'),
			success: function() {
				src.remove();
			}
		});
	}
};

function fSetBlock(obj)
{
	var MemberSeq = $(obj).closest('.atm_profile_el').attr('member_seq');
	fAjax_m('/member/myFriendAjax', '', 'ACT=SET_BLOCK&MemberSeq=' + MemberSeq + '&setBlock=N');
}

function checkDeviceSize()
{
	const msg= document.querySelector(".user_message");
	const msgWidth  = msg.offsetWidth;
	const msgHeight = msg.offsetHeight;
	const deviceWidth = window.innerWidth;
	const deviceHeight= window.innerHeight;

	msg.style.top = (deviceHeight-msgHeight)/2 + "px";
	msg.style.left= (deviceWidth-msgWidth)/2 + "px";
}

function selectView() {

	$('#select_view01').stop().show();
	
	$('.select_div').click(function(event) {
	    let selectedBtnTab = $(event.target);
		if (!selectedBtnTab.attr('tar_obj'))
			 selectedBtnTab = selectedBtnTab.closest('.atm_temp_tab');
	    let idName = selectedBtnTab.attr('tar_obj');
	    
	    if (!idName) return;
	    if (!selectedBtnTab.find('p').hasClass('temp_tab_on')) {
	      $('.select_view').hide();
	      $('.atm_temp_tab').find('p').removeClass('temp_tab_on');
	
	      selectedBtnTab.find('p').addClass('temp_tab_on');
	      $('#' + idName).stop().show();
	
	      if (typeof libCallBackSelectView == 'function') {
	        libCallBackSelectView(idName);
	      }
	    }
	});
}

function libCallBackSelectView(idName) {
	pg = 0;
	$('#' + idName).find('.atm_profile_el:not(:first)').remove();
	
	switch (idName) {
		case 'select_view01':
			fAjax_List('ACT=LIST_FRIEND');
			break;
		case 'select_view02':
			fAjax_List('ACT=LIST_BLOCK');
		break;
	}
}

$(document).ready(function(){
	selectView();
	fAjax_List('ACT=LIST_FRIEND');

	$(".openMessage").click(function(e){
		var src = $(this).closest('.atm_profile_el');
		fHideMenu();

		$('#msgPhoto').attr('src', src.find('.atm_profile_img1').attr('src'))
		$('.user_message .message_username').text(src.find('.nick').attr('value'));
		$('.user_message input[name=MemberSeq]').val(src.attr('member_seq'));

		$('.more_btn_list').stop().fadeIn();
		$('.user_message').stop().fadeIn();

		e.stopPropagation();
	});
	
	$('.message_cancle').click(function(e){
		$('.user_message').stop().hide();
		$('.more_btn_list').stop().hide();
		e.stopPropagation();
	});
	
	$('.select_wrapper .atm_temp_tab p').eq(0).click(function(){
		$('#select_view01').stop().show();
		$('#select_view02').stop().hide();
		selectView();
    });
    $('.select_wrapper .atm_temp_tab p').eq(1).click(function(){
    	$('#select_view01').stop().hide();
    	$('#select_view02').stop().show();
    	selectView();
    });

	$(".message_mask, .message_cancle").click(function(e){
		$(".message_mask").css("display","none");
		$('.user_message').css("display","none");

		$("body").css("position", "");
	});

	$.fn.scrollBottom = function() { 
		return $(document).height() - this.scrollTop() - this.height(); 
	};
})
</script>


<div id="atm_mymentor_wrapper0">
	<div class="center">
	<form name="friend_frm_sch" method="post" onSubmit="return false;">
	<input type="hidden" name="src_Target" value="">
	<input type="hidden" name="pg" value="">
	<input type="hidden" name="FlagPartner" value="">
	
	<div class="atm_edittop_ttbar_pc" >
	    <p class="atm_edittop_c1"><spring:message code="msg_0178"/></p>
	</div>
	
	<div class="select_wrapper">
		<div class="select_div">
			<div class="atm_temp_tab" tar_obj="select_view01">
				<p class="temp_tab_on"><spring:message code="msg_0810"/></p>
			</div>
			<!-- <div class="select_btn" tar_obj="select_view02"> -->
			<div class="atm_temp_tab" tar_obj="select_view02">
				<p><spring:message code="msg_0811"/></p>
			</div>
		</div>
	</div>
	
    <div class="myPartner_wrapper select_innerDiv">
        <div class="myPartner_friends select_view" id="select_view01">
            <p class="altong_title"><spring:message code="msg_0812"/> <span class="atm_mycatebar_c2"> 00</span><spring:message code="msg_0803"/></p>
            <div class="atm_profile_el" style="display:none;">
                <figure>
                    <img class="atm_profile_img1" onerror="this.src='/pub/css/profile/img_thum_base0.jpg'">
                </figure>
				<div class="atm_profile_eltexts" onClick="fGoProfile(this)">
					<p class="atm_profile_el_c2 nick"><span class="atm_profile_el_rank"><spring:message code="msg_0813"/></span><spring:message code="msg_0310"/></p>
					<em><spring:message code="msg_0253"/></em>
				</div>
				<div class="atm_profile_el_center">
                    <p class="atm_profile_el_c3">
                        <span class="atm_whitespace"><spring:message code="msg_0250"/> <span class="SumQ">00</span><spring:message code="msg_0136"/></span>
                        <span class="atm_whitespace"><spring:message code="msg_0251"/> <span class="SumA">00</span><spring:message code="msg_0136"/></span>
                    </p>
                    <p class="atm_profile_el_c3">
                        <span class="atm_whitespace"><spring:message code="msg_0252"/> <span class="CountC">00</span><spring:message code="msg_0814"/></span>
                        <span class="atm_whitespace"><spring:message code="msg_0260"/> <span class="Rate">00</span>%</span>
                    </p>
                </div><!--event, this -->
                <div class="atm_mymentor_xbtn" onClick="fShowMenu(event,this)">
                    <i></i>
                    <i></i>
                    <i></i>
                    <div id="menuFriend" class="atm_profile_edit">
                        <div class="atm_profile_c6" onclick="fDeleteFriend(event, this)"><img src="/Common/images/delete.png" alt='<spring:message code="msg_0228"/>'> <spring:message code="msg_0228"/></div>
                        <div class="atm_profile_c6 openMessage"><img src="/Common/images/message_gg.png" alt='<spring:message code="msg_0815"/>'> <spring:message code="msg_0815"/></div>
                    </div>
                </div>
				<p class="atm_av_c10"><spring:message code="msg_0816"/> : <span class="regdate">0000-00-00</span></p>
            </div>  
        </div>
        
        <div class="myPartner_block select_view" id="select_view02">
             <p class="altong_title"><spring:message code="msg_0811"/> <span class="atm_mycatebar_c2">00</span><spring:message code="msg_0803"/></p>
             <div class="atm_profile_el" style="display:none;">
                 <div class="atm_profile_eltexts">
                     <p class="atm_profile_el_c2 nick"><spring:message code="msg_0310"/></p>
                     <p class="atm_profile_el_c3">
                         <span class="atm_whitespace"><spring:message code="msg_0817"/> : <span class="regdate">0000-00-00</span></span>
                     </p>
                     <p class="atm_profile_el_c3">
                         <span class="atm_whitespace" onclick="fSetBlock(this)"><spring:message code="msg_0818"/></span>
                     </p>
                 </div>
             </div>  
         </div>

		<div id="divProg" >
            <div><img src="/Common/images/search_nick_loader02.gif"></div>
        </div>   
	</div>

	</form>

	</div><!-- center end -->
</div><!-- atm_mymentor_wrapper0 end -->
<div class="more_btn_list">
    <div class="user_message">
        <h5><span class="message_username"></span><spring:message code="msg_0309"/></h5>
        <form name="frm_msgSend">
            <input type="hidden" name="MemberSeq">
            <textarea name="Contents" class="message_usertext"></textarea>
        </form>
        <p class="message_btns">
            <span class="message_cancle"><spring:message code="msg_0159"/></span>
            <span class="message_send" onClick="fAjax_m('/message/messageAjax', 'frm_msgSend', 'ACT=SEND')"><spring:message code="msg_0311"/></span>
        </p>
    </div>
</div>
<div id="top_btn">
    <a href="javascript:void(0);">
        <span>
            <img src="/Common/images/top_btn_arrow.svg" alt="top_btn">
        </span>
    </a>
</div>
<script src="/pub/member/myFriend/myFriend.js?ver=1.3"></script>
</body>