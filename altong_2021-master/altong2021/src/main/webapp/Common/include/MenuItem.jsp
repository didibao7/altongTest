<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
JSONObject i_info = (JSONObject)CommonUtil.getGlobal(request, response);
pageContext.setAttribute("global", i_info);
String sLv = "";
if(i_info != null) {
	int nLv = Integer.parseInt( String.valueOf( i_info.get("UserLv") ) );
	sLv = CommonUtil.getLevelName(nLv, request);
}
%>
<c:if test="${global.get('UserSeq') ne null}">
<script type="text/javascript">
$(document).ready(function() {
	$.ajax({
		type: 'post',
		url: '/common/getMemoCount',
		data: 'SESS=' + getCookie('SESS'),
		dataType: 'json',
		crossDomain: true,
		success: function (r) {
			if(r.result == "SUCCESS"){
				if(r.arr > 0){
					$("#memoCount > span").text(r.arr);
				}else{
					$("#memoCount").remove();
				}
			}else{
				$("#memoCount").remove();
			}
		}
	});
	
	mainLog();
});

var nextDt;
function getMin() {
	nextDt = new Date(Date.now() + (30 * 60 * 1000));
}

function mainLog() {
	dt = new Date();
	
	if(nextDt == undefined) {
		getMin();
	}
	//console.log('nextDt : ' + nextDt.getTime());
	//console.log('dt : ' + dt.getTime());
	//현재 시간과 nextDt 와 비교하여 같으면
	//로그 기록을 하고
	if(dt.getTime() < nextDt.getTime()) {
		setTimeout(function(){ mainLog(); }, 1000);
	}
	else {
		setLog();
	}
}

function setLog() {
	//ajax 으로 /common/setLog 를 호출하여 로 그 기록을 진행한다.
	$.ajax({
		type: 'post',
		url: '/common/setLog',
		dataType: 'json',
		crossDomain: true,
		success: function (r) {
			//console.log('r.result : ' + r.result);
			switch (r.result) {
				case 'true':
					//성공
					console.log('완료!!!');
					getMin();
					mainLog();
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
</script>
</c:if>
<style>
ul,li {list-style-type:none;}
img,p,li {border:0;margin:0;padding:0; vertical-align:middle;}
.atm_gnb_con_slide{ display:block;}
#sidebar-menu-item {
	overflow-x: hidden;
	overflow-y: scroll;
	background-color: #ffffff;
	height: 1000px;
}
#alarmCount {
	display: inline-block;
	position: absolute;
	top: 13px;
	left:40px;
}
#alarmCount > img {
	width: 13px;
}
#alarmCount > span {
	color:white;
	font-size: 0.7em;
	position: absolute;
	left: 8px;
	top: -6px;
	font-weight: 400;
	background-color: #f00;
	padding: 1px 3px;
	border-radius:10px;
}
#memoCount {
	display: inline-block;
	position: absolute;
	top: 13px;
	left:45px;
}
#memoCount > img {
	width: 13px;
}
#memoCount > span {
	color:white;
	font-size: 0.7em;
	position: absolute;
	left: 8px;
	top: -6px;
	font-weight: 400;
	background-color: #f00;
	padding: 1px 3px;
	border-radius:10px;
}
/*div::-webkit-scrollbar {display: none; } */
.atm_gnb_btnx { position:absolute; top:8px; right:-42px; width:44px; display: none;}
.atm_gnb_con_home_btn{font-size:0.95em; letter-spacing:-1px; text-align:center;}
.atm_gnb_con_home_btn > i{vertical-align:bottom; font-size:1.5em; margin-right:3px; color:#28C0BB;}

</style>

<script src="/Common/crop/cropper.js"></script>

<div class="sidebar main left" id="sidebar-main" style="display:none">
	<nav>
		<div id="atm_gnb_wrapper0">

			<%if(i_info != null) { %>
			<img src="${libIMG_URL}/Common/images/btn_x_3.png" class="atm_gnb_btnx" />
			<div class="atm_gnb_con_infomain">
                <!-- [추가(2018.02.06): 김현구] '나의 설정 > 관리' 페이지로 LINK 처리 -->
                
				<c:choose>
					<c:when test="${global.get('UserPhoto') eq ''}">
						<img src="${libIMG_URL}/Common/images/img_thum_base0.jpg" class="atm_gnb_img_prof" style="cursor:pointer;" onClick="location.href='/member/myInfo';" /><br>
					</c:when>
					<c:otherwise>
						<img src="${libIMG_URL}/UploadFile/Profile/${global.get('UserPhoto')}" class="atm_gnb_img_prof" style="cursor:pointer;" onClick="location.href='/member/myInfo';" /><br>
					</c:otherwise>
				</c:choose>

				<p class="atm_gnb_c2" style="cursor:pointer;font-size:16px;" onClick="location.href='/member/myInfo';">${global.get('UserNickName')}</p>
                <p class="atm_gnb_c3" style="cursor:pointer;" onClick="location.href='/member/myInfo';"><%=sLv%></p><br /><!-- MyInfo.alt -->
				<p class="atm_gnb_c5">
				<span class="atm_whitespace">질문순위 : <span class="atm_gnb_color_whi"><fmt:formatNumber type="number" maxFractionDigits="3" value="${global.get('RankQ')}" /></span> 위</span>
				<span class="atm_gnb_color_gray">&nbsp;|&nbsp;</span>
				<span class="atm_whitespace">답변순위 : <span class="atm_gnb_color_whi"><fmt:formatNumber type="number" maxFractionDigits="3" value="${global.get('RankA')}" /></span> 위</span>
                </p>
				<a href="/member/myJoin"><img src="${libIMG_URL}/Common/images/btn_edit_mf.png" class="atm_gnb_img_edit" /></a>
				<!-- <p class="atm_gnb_c4" onClick="location.href='/default/logOut';">LOG OUT</p> -->
			</div>
			<%--
			<c:if test="${libUniv ne ''}">
				<div class="atm_gnb_con_tt0 atm_gnb_con_home_btn" onclick="location.href='/default/main';"><i class="material-icons">home</i>알통 메인으로 가기</div>
			</c:if>
			--%>
			<div class="atm_gnb_con_tt0" onClick="location.href='/member/alarm/alarm';">
				<p class="atm_gnb_c0">알림</p>
				<c:if test="${global.get('ALARM_COUNT_SUM') != ''}">
					<div id="alarmCount"><img src="/Common/images/icon_alarm_new.png?time="><span>${global.get("ALARM_COUNT_SUM")}</span></div>
				</c:if>
			</div>
			<div class="atm_gnb_scroll_menu" id="sidebar-menu-item">
				<div class="atm_gnb_con_tt0">
					<p class="atm_gnb_c0">나의 공간</p>
					<img src="${libIMG_URL}/Common/images/btn_minus0.png" class="atm_gnb_btn_R1" id="toggle-btn"/>
				</div>
				<div class="atm_gnb_slide_item" id="slide_item">
					<div class="atm_gnb_con_slide on" id="con_slide">
						<div class="atm_gnb_con_tt1" onClick="javascript:location.href='/member/myInfo';">
							<p class="atm_gnb_c1">- 활동</p>
						</div>
						<div class="atm_gnb_con_tt1" onClick="javascript:location.href='/member/bank/index';">
							<p class="atm_gnb_c1">- 알뱅크</p>
						</div>
						<div class="atm_gnb_con_tt1" onClick="javascript:location.href='/alpay/user/sub/exchange';">
							<p class="atm_gnb_c1">- 출금신청</p>
						</div>
						<div class="atm_gnb_con_tt1" onClick="javascript:location.href='/member/interest/myInterest';">
						<!--<div class="atm_gnb_con_tt1" onClick="alert('점검 중 입니다.')">-->
							<p class="atm_gnb_c1">- 관심분야</p>
						</div>
						<div class="atm_gnb_con_tt1" onClick="javascript:location.href='/member/myZzim';">
							<p class="atm_gnb_c1">- 찜</p>
						</div>
						<div class="atm_gnb_con_tt1" onClick="javascript:location.href='/member/myPartner?FlagPartner=M';">
							<p class="atm_gnb_c1">- 멘토 / 멘티</p>
						</div>
						<div class="atm_gnb_con_tt1" onClick="javascript:location.href='/member/myFriend';">
							<p class="atm_gnb_c1">- 친구 / 쪽지 차단</p>
						</div>
						<div class="atm_gnb_con_tt1" onClick="javascript:location.href='/message/message';">
							<p class="atm_gnb_c1">- 쪽지</p>
							<c:if test="${global.get('UserSeq') != null}">
								<div id="memoCount">
									<img src="/Common/images/icon_alarm_new.png?time=">
									<span></span>
								</div>
							</c:if>
						</div>
					</div>
					<div class="atm_gnb_con_tt0" onclick="fShowNickSearch(this);" style="border-bottom:none;">
						<p class="atm_gnb_c1">닉네임 검색 <i class="material-icons nick_icon">search</i></p>
					</div>
					<div class="atm_gnb_con_tt0 nick_search">
						<div class="nick_search_bar">
							<input type="hidden" name="ACT" value="SEARCH_NICK">
							<input type="search" name="H_nick" placeholder="닉네임을 입력해주세요." required autocomplete="off" autofocus onclick="fSearchNickFocus(0, 0)" >
							<button type="submit" onclick="fSearchNick(this)"><img src="${libIMG_URL}/Common/images/icon_search.png" alt="닉네임 검색"></button>
							<span class="nick_search_loader"><img src="${libIMG_URL}/Common/images/search_nick_loader.gif" alt="검색중입니다"></span>
						</div>
					</div>
					<style>
						.atm_gnb_con_tt0.nick_search {padding:0;transition: all 0.5s;}
						.nick_search_bar {border-radius:20px; background:#fff;font-size:12px; height: 0;transition: all 0.2s; padding-left:10px; padding-right:10px;padding-top:0;padding-bottom:0; opacity: 0; position: relative;visibility: hidden;text-align: left;}
						.nick_search_bar input {border:none; width:85%;line-height:20px;letter-spacing: -0.5px;height:0;transition: all 0.2s;opacity: 0;}
						.nick_search_bar input:focus,
						.nick_search_bar button:focus {outline:none!important; box-shadow: none!important;}
						.nick_search_bar button {position:absolute;right:0;top:0;background:0;border:0;padding:0;}
						.nick_search_bar button img {width:28px; margin-right: 3px;}
						.nick_show {padding-bottom: 0; color:#22b3ae;transition: all 0.2s;}
						.nick_show + .nick_search {padding:9px 8px;transition: all 0.5s; padding-bottom:11px;}
						.nick_show + .nick_search .nick_search_bar{height: auto;visibility: visible; transition: all 0.2s;border:2px solid #2ac1bc;padding-top:5px;padding-bottom:5px; opacity: 1;}
						.nick_show + .nick_search .nick_search_bar input {height:auto;transition: all 0.2s;opacity: 1;}
						.atm_gnb_c1 .nick_icon {vertical-align: top;font-size: 21px;margin-left:3px;line-height: 16px;opacity: 1;transition: all 0.3s;}
						.atm_gnb_c1 > img{height:20px;}
						.nick_show .atm_gnb_c1 .nick_icon {opacity: 0; transition:all 0.3s;}
						.nick_search_loader {display:none; width:23px;position: absolute; right:0; top:0;box-sizing: content-box; padding-right:7px; padding-top:4px;}
						.nick_search_loader img {width:100%;}
						.nick_search_bar.nick_loader .nick_search_loader {display: inline-block;}
						.nick_search_bar.nick_loader button {display: none;}
						.nick_search_bar.nick_loader input {color:#2ac1bc;}
					</style>
					<script>
						$(function(){
							//ncik_search_bar를 form이 감싸지 않을 때, 감싸는 form을 동적으로 생성
							if (! $(this).find('form').find('.nick_search_bar').length) {
								$(this).find('.nick_search_bar').wrap('<form onsubmit="return false"></form>');
							}
						});

						function addComma(value){
							return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
						}

						function fSearchNickFocus(setBlur, setFocus) {
							var varUA = navigator.userAgent.toLowerCase(); //userAgent 값 얻기
							
							if (setBlur)
								$('input[name="H_nick"]').blur();

							if (varUA.match('android') != null) {
								//안드로이드 일때 처리
								$('.atm_gnb_scroll_menu').animate({ scrollTop: 295 }, 400, function(){
									if (setFocus)f
										$('input[name="H_nick"]').focus().select();
								});
							} else {
								if (setFocus)
									$('input[name="H_nick"]').focus().select();
							}
						}

						function fShowNickSearch(v){
							if ($(v).hasClass('nick_show')) {
								$(v).removeClass('nick_show');
							} else {
								$(v).addClass('nick_show');
								setTimeout(function(){
									fSearchNickFocus(0, 1);
								},400);
							}
						}

						function fSearchNick(v)
						{
							var nickVal = $(v).siblings('input[name=H_nick]').val();
							var frm = $('<form></form>');
							var act = $('<input type="hidden" name="ACT" value="SEARCH_NICK">');
							var Hnick = $('<input type="text" name="H_nick" value="'+ nickVal +'">')
							frm.append(act).append(Hnick);

							libAjax(frm.serialize());
						}
						function libAjax(param) {
							if (document.libXHR) {
								$('#Tip').text('이전 검색이 진행중입니다.').css('display', 'block');
								setTimeout(function () { $('#Tip').stop().fadeOut(300) }, 3000);
								return;
							}
							
							var isRemove = true;
							$('.nick_show').siblings('.nick_search').find('.nick_search_bar').addClass('nick_loader').find('input[name="H_nick"]').attr('readonly','true');

							//searchNick.php
							document.libXHR = $.ajax({
								type: 'post',
								url: '/common/searchNick',
								data: param,
								dataType: 'json',
								success: function (r) {
									switch (r.result) {
										case 'SEARCH_NICK':
											{
												isRemove = false;
												location.href = "/member/otherProfileInfo?MemberSeq=" + r.arr.seq;
												break;
											}
										default:
											if (r.result) alert(r.result);
											break;
									}
								},
								error: function (r, textStatus, err) {
									if (r.responseText && r.responseText.substr(0, 13) == '<!--LOGOFF-->') { top.location = '${libIMG_URL}'; return; }

									alert('서버와의 통신에 실패하였습니다.');
									var str = '';
									for (var key in r) str += key + '=' + r[key] + '\n';
									console.log(str);
								},
								complete: function () {
									document.libXHR = false;
									if (isRemove) {
										$('.nick_show').siblings('.nick_search').find('.nick_search_bar').removeClass('nick_loader').find('input[name="H_nick"]').removeAttr('readonly');
										fSearchNickFocus(1, 1);
									}
								}
							});
						}
					</script>

			<%} else {%>
			<div class="atm_gnb_con_infomain">
				<p class="atm_gnb_c6" onclick="javascript:location.href='/default/login';">로그인이 필요합니다.</span></p><br />
				<p class="atm_gnb_c5">
				<span class="atm_whitespace">질문순위 : <span class="atm_gnb_color_whi">-</span> 위</span>
				<span class="atm_gnb_color_gray">&nbsp;|&nbsp;</span>
				<span class="atm_whitespace">답변순위 : <span class="atm_gnb_color_whi">-</span> 위</span>
				</p>
			</div>
			<div class="atm_gnb_scroll_menu" id="sidebar-menu-item">
				<div class="atm_gnb_slide_item" id="slide_item">

			<%} %>
					<c:if test="${global.get('UserSeq') != null}">
					<div class="atm_gnb_con_tt0" onClick="javascript:location.href='/member/myRecommend';">
						<p class="atm_gnb_c0">추천인 / ANSWERer</p>
					</div>
					</c:if>
					<div class="atm_gnb_con_tt0 deadline" onClick="javascript:location.href='/question/rankQuestion';">
						<p class="atm_gnb_c0">랭킹</p>
					</div>
					<div class="atm_gnb_con_tt0" onClick="location.href='/question/eventList';">
						<p class="atm_gnb_c0">이벤트</p>
					</div>
					<div class="atm_gnb_con_tt0" onClick="javascript:location.href='/default/userGuide';">
						<p class="atm_gnb_c0">이용안내</p>
					</div>
					<div class="atm_gnb_con_tt0" onClick="location.href='/default/cs/customerService'">
						<p class="atm_gnb_c0">공지사항</p>
					</div>
					<!-- <div class="atm_gnb_con_tt0">
						<p class="atm_gnb_c0">설정</p>
					</div> -->
					<div class="atm_gnb_con_tt0" id="keysoundToggleBtn" onclick="togglecookie(this);">
						<p class="atm_gnb_c0">Keypress</p>
					</div>
					
					<c:if test="${global.get('UserSeq') != null}">
					<div class="atm_gnb_con_tt0" >
						<p class="atm_gnb_c0" onClick="fleavebtn();">회원탈퇴</p>
					</div>
					</c:if>
					<c:if test="${global.get('UserSeq') eq '10000110' or global.get('UserSeq') eq '10000564' or global.get('UserSeq') eq '10000092' or global.get('UserSeq') eq '10000703' or global.get('UserLv') eq '99'}">
					<div class="atm_gnb_con_tt0" onClick="javascript:location.href='/aadmin/category/classify';">
						<p class="atm_gnb_c0">카테고리 설정</p>
					</div>
					</c:if>
					<c:if test="${global.get('UserLv') eq '99'}">
					<div class="atm_gnb_con_tt0" onClick="javascript:location.href='/aadmin/index';">
						<p class="atm_gnb_c0">관리자 페이지</p>
					</div>
					</c:if>
					<c:if test="${global.get('Almaeng') ne null}">
					<!-- <div class="atm_gnb_con_tt0" onClick="javascript:top.location.href='/alpay/store/alpayStore';">
						<p class="atm_gnb_c0">알맹이</p>
					</div> -->
					</c:if>
					<div id="languages_menu">
						<span><a href="http://ko.altong.com">한글</a></span>/<span><a href="javascript:void(0)" onclick="javascript:alert('서비스 준비중 입니다!')">EN</a></span>/<span><a href="javascript:void(0)" onclick="javascript:alert('서비스 준비중 입니다!')">中文</a></span>
					</div>
					<c:if test="${global.get('UserSeq') ne null}">
					<div class="atm_gnb_con_tt0" onClick="javascript:top.location.href='/default/logOut';" style=" color:#5a5a5a; background:#f3f3f3;">
						<p class="atm_gnb_c0"><i class="material-icons" style="font-size:17px; vertical-align: middle;margin-top:-3px;font-weight: bold;">power_settings_new</i> 로그아웃</p>
					</div>
					</c:if>
					<div style="position: relative; padding: 12px 17px 12px 17px; background-color: #ffffff;">
						<p class="atm_gnb_c0" id="white-space-in-nav" style="height:1000px;">&nbsp;</p>
					</div>
				</div>
			</div>
		</div>
	</nav>
</div>

<script>
	var heightbak;
	var heightValue = $(window).height() - ($('#atm_gnb_wrapper0').height() - 1000);
	$('#white-space-in-nav').height(heightValue * 2);
	$(document).ready(function() {
		$('#sidebar-main').css('display','block');
		$('#toggle-btn').click(function() {
			var toggle = $('#toggle-btn');
			var target = toggle.parent().parent().find('.atm_gnb_con_slide');
			if(target.hasClass('on')) {
				target.addClass('off').removeClass('on');
				heightbak = target.height();
				target.animate({height : 0}, 300);
				target.children('.atm_gnb_con_tt1').css('display', 'none');
				toggle.attr('src', '/Common/images/btn_plus0.png');
			} else {
				target.addClass('on').removeClass('off');
				target.animate({height : heightbak}, 300);
				target.children('.atm_gnb_con_tt1').css('display', 'block');
				toggle.attr('src', '/Common/images/btn_minus0.png');
			}
		});
	});
</script>
<script src="/Common/lib/iscroll.js">
	function loaded() {
		var iscroll = new iScroll('sidebar-menu-item', {
			hScroll:false,
			vScrollbar:false
			});
	}
	document.addEventListener('DOMContentLoaded', loaded, false);
</script>
<script>
function fleavebtn() {
	var text = "회원탈퇴는 (02)330-3000으로 전화를 통해 신청을 부탁드립니다.\n\
참고로 회원탈퇴는 매우 신중히 결정하시기를 권해드립니다.\n\
탈퇴 시 회원님의 보유 알은 소멸되고 향후 계속 수익이 발생할 경우 이 수익 역시 (주)알통에 귀속됩니다.\n\
만약 이후 재가입을 원하실 경우 최소 6개월 경과 후에야 가능하므로 탈퇴 전에 신중에 신중을 거듭하여 신청해 주십시오.\n\
(재가입 관련 정책은 추후 변경될 수 있습니다.)"
	if (confirm(text))
		location.href='/default/cs/customerService';
}
</script>
<script type="text/javascript">
	var dragFlag = false;
	var x, y, pre_x, pre_y;
	$(function () {
		$('#sidebar-menu-item').mousedown(
			function (e) {
				dragFlag = true;
				var obj = $(this);
				x = obj.scrollLeft();
				y = obj.scrollTop();

				pre_x = e.screenX;
				pre_y = e.screenY;					

				$(this).css("cursor", "pointer");

				//$('#result').text("x:" + x + "," + "y:" + y + "," + "pre_x:" + pre_x + "," + "pre_y:" + pre_y);
				$('#result').text(dragFlag);

			}
		);

		$('#sidebar-menu-item').mousemove(
			function (e) {
				if (dragFlag) {
					var obj = $(this);
					obj.scrollLeft(x - e.screenX + pre_x);
					obj.scrollTop(y - e.screenY + pre_y);

					//$('#result').text((x - e.screenX + pre_x) + "," + (y - e.screenY + pre_y));
					return false;
				}

			}
		);

		$('#sidebar-menu-item').mouseup(
			function () {
				dragFlag = false;
				//$('#result').text("x:" + x + "," + "y:" + y + "," + "pre_x:" + pre_x + "," + "pre_y:" + pre_y);
				$('#result').text(dragFlag);
				$(this).css("cursor", "default");
			}


		);

		$('body').mouseup(
			function () {
			dragFlag = false;					
			$('#result').text(dragFlag);
			$(this).css("cursor", "default");
		}
	);
});
</script>
