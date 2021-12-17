<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.temporal.ChronoUnit" %>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="com.google.gson.Gson" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<head>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="/Common/crop/cropper.css">
	<link rel="stylesheet" href="/pub/member/myInfo/myInfo.css?ver=2.0">
	<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.9">
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<script src="/Common/crop/cropper.js"></script>
<script>
	var flagLAST = 0;
	function fmenuChange(target) {
		$('.content_wrap').hide();
		$(target).siblings().removeClass('on');
		$(target).addClass('on');

		$('html').removeAttr('class');
		//$('html').addClass('js');
		
		$('body').removeAttr('class'); //#atm_gray2
		$('body').addClass($(target).data('type')); //#atm_gray2
	}

	function fweekGraph() {
		var percentSum = 0;
		var percent = 0;
		$('.prgDetailCnt').each(function(index,item){
			percent = (parseInt($(this).text()) / parseInt($(this).siblings('.prgDetailMaxCnt').text())) * 100;
			percentSum += percent;
			$(this).parents('.box').find('.detail_graph').find('.graph').css('width', percent+'%');
		});
		
		percentSum = Math.round(percentSum / $('.prgDetailCnt').length);
		
		if (percentSum > 50) {
			$('section.week_mission').find('.sum_graph').find('.sum_txt').css('color','#fff');
		} else {
			$('section.week_mission').find('.sum_graph').find('.sum_txt').css('color','#ff9e07');
			$('section.week_mission.exchange').find('.sum_graph').find('.sum_txt').css('color','#20b0c4');
			$('section.week_mission.old_date').find('.sum_graph').find('.sum_txt').css('color','#909090');
		}
		$('section.week_mission').find('.sum_graph').find('.sum_txt').find('span').text(percentSum);
		$('section.week_mission').find('.sum_graph').find('.graph').css('width', percentSum + '%');

		if(flagLAST == 0 && percentSum == 100) {
			$('section.week_mission').find('.sum_success').slideDown();
		} else {
			$('section.week_mission').find('.sum_success').slideUp();
		}
	}
	function exch_fAjax(url, type, param)
	{
		if (document.xhr) {
			$('#Tip').text(getLangStr("jsm_0031")).css('display', 'block');
			setTimeout(function () { $('#Tip').stop().fadeOut(300) }, 3000);
			return;
		}
		
		document.xhr = $.ajax({
			type: 'post',
			url: url,
			data: param,
			dataType: type,
			success: function (r) {
				switch (r.result) {
					case 'GetLastWeek':
						console.log('r.arr : ' + r.arr[0].StartDate);
						if (r.arr.length == 0) {
							alert(getLangStr("jsm_0032"));
							return;
						}
						var arr = r.arr[0];
						$('#weekStartDate').text(arr.StartDate);
						$('#weekEndDate').text(arr.EndDate);
						$('#weekQCnt').text(arr.QusRegCnt ? (arr.QusRegCnt > parseInt($('#weekQMaxCnt').text(), 10) ? $('#weekQMaxCnt').text() : arr.QusRegCnt) : 0 );
						$('#weekACnt').text(arr.AnsRegCnt ? (arr.AnsRegCnt > parseInt($('#weekAMaxCnt').text(), 10) ? $('#weekAMaxCnt').text() : arr.AnsRegCnt) : 0 );
						$('#weekAEstiCnt').text(arr.AnsEstiCnt ? (arr.AnsEstiCnt > parseInt($('#weekAEstiMaxCnt').text(), 10) ? $('#weekAEstiMaxCnt').text() : arr.AnsEstiCnt) : 0 );
						$('#weekAChoicedCnt').text(arr.AnsChoicedCnt ? (r.arr.AnsChoicedCnt > parseInt($('#weekAChoicedMaxCnt').text(), 10) ? $('#weekAChoicedMaxCnt').text() : arr.AnsChoicedCnt) : 0 );
						$('#weekReplyCnt').text(arr.ReplyCnt ? ( arr.ReplyCnt > parseInt($('#weekReplyMaxCnt').text(), 10) ? $('#weekReplyMaxCnt').text() : arr.ReplyCnt) : 0 );

						$('#dateArrowL, #dateArrowR').css('visibility', 'hidden').removeAttr('onclick');
						parseInt(arr.LAST, 10) != 0 ? $('section.week_mission').addClass('old_date') : $('section.week_mission').removeClass('old_date');

						var prevNum = parseInt(arr.LAST) < 3 ? parseInt(arr.LAST) + 1 : -1;
						var nextNum = parseInt(arr.LAST) - 1;

						if (prevNum != -1)
							$('#dateArrowL').css('visibility', 'visible').attr("onclick", "exch_fAjax('/member/myInfoLvAjax', 'json', 'ACT=GetLastWeek&LAST="+ prevNum +"')");
						if (nextNum != -1)
							$('#dateArrowR').css('visibility', 'visible').attr("onclick", "exch_fAjax('/member/myInfoLvAjax', 'json', 'ACT=GetLastWeek&LAST="+ nextNum +"')");
						
						flagLAST = parseInt(arr.LAST, 10);
						fweekGraph();

						break;
					case 'ExchGetLastWeek':
						if (r.arr.length == 0) {
							alert(getLangStr("jsm_0033"));
							return;
						}
						
						var isExistsCondition = Object.keys(r.arr[0]).length;

						if (!r.arr[0].ExchQusRegCnt) r.arr[0].ExchQusRegCnt = 0;
						if (!r.arr[0].ExchAnsRegCnt) r.arr[0].ExchAnsRegCnt = 0;
						if (!r.arr[0].ExchAnsEstiCnt) r.arr[0].ExchAnsEstiCnt = 0;
						if (!r.arr[0].ExchAnsChoicedCnt) r.arr[0].ExchAnsChoicedCnt = 0;

						$('#weekStartDate').text(r.arr[1].StartDate);
						$('#weekEndDate').text(r.arr[1].EndDate);
						if (isExistsCondition) {
							$('#weekQMax').text(r.arr[0].ExchQusRegCnt);
							$('#weekAMax').text(r.arr[0].ExchAnsRegCnt);
							$('#weekAEstiMax').text(r.arr[0].ExchAnsEstiCnt);
							$('#weekAChoicedMax').text(r.arr[0].ExchAnsChoicedCnt);
						}

						if (!r.arr[1].QusRegCnt) r.arr[1].QusRegCnt = 0;
						if (!r.arr[1].AnsRegCnt) r.arr[1].AnsRegCnt = 0;
						if (!r.arr[1].AnsEstiCnt) r.arr[1].AnsEstiCnt = 0;
						if (!r.arr[1].AnsChoicedCnt) r.arr[1].AnsChoicedCnt = 0;

						$('#weekQCnt').text(r.arr[1].QusRegCnt > r.arr[0].ExchQusRegCnt ? r.arr[0].ExchQusRegCnt : r.arr[1].QusRegCnt);
						$('#weekACnt').text(r.arr[1].AnsRegCnt > r.arr[0].ExchAnsRegCnt ? r.arr[0].ExchAnsRegCnt : r.arr[1].AnsRegCnt);
						$('#weekAEstiCnt').text(r.arr[1].AnsEstiCnt > r.arr[0].ExchAnsEstiCnt ? r.arr[0].ExchAnsEstiCnt : r.arr[1].AnsEstiCnt);
						$('#weekAChoicedCnt').text(r.arr[1].AnsChoicedCnt > r.arr[0].ExchAnsChoicedCnt ? r.arr[0].ExchAnsChoicedCnt : r.arr[1].AnsChoicedCnt);

						$('#dateArrowL, #dateArrowR').css('visibility', 'hidden').removeAttr('onclick');
						parseInt(r.arr[2].LAST) != 0 ? $('section.week_mission').addClass('old_date') : $('section.week_mission').removeClass('old_date');

						var prevNum = parseInt(r.arr[2].LAST) < 3 ? parseInt(r.arr[2].LAST) + 1 : -1;
						var nextNum = parseInt(r.arr[2].LAST) - 1;
						if (prevNum != -1)
							$('#dateArrowL').css('visibility', 'visible').attr("onclick", "exch_fAjax('/member/myInfoExchAjax', 'json', 'ACT=ExchGetLastWeek&LAST="+ prevNum +"')");
						if (nextNum != -1)
							$('#dateArrowR').css('visibility', 'visible').attr("onclick", "exch_fAjax('/member/myInfoExchAjax', 'json', 'ACT=ExchGetLastWeek&LAST="+ nextNum +"')");
						
						flagLAST = parseInt(r.arr[2].LAST, 10);
						fweekGraph();
						break;
					default:
						$('.content_wrap').html(r).fadeIn(300);
						fweekGraph();

						if (url == '/Common/include/member/myInfoLv.jsp') flvupStamp();
						break;
				}
				
			},
			error: function (r, textStatus, err) {
				console.log(r);
			},
			complete: function () {
				if (!document.isShow) document.xhr = false;
			}
		});
	}
	
	function flvupStamp() {
		var stampMax = $('#lvupStampWrap').data('stampmax');
		var stampCnt = $('#lvupStampWrap').data('stampcnt');
		var halfWidth = $('#lvupStampWrap').find('.stamp_base').width() / 2;

		var incrs = 1;
		var top = 0;
		var left = 0;
		var roadLeft; 
		var roadTop;
		var topStep = 100 / Math.ceil(stampMax / 4);

		for (var i=0; i < stampMax; i++) {
			var stampRoad = $('#lvupStampWrap').find('.clone_el').find('.stamp_road').clone();
			var stampBase = $('#lvupStampWrap').find('.clone_el').find('.stamp_base').clone();
			var stamp = $('#lvupStampWrap').find('.clone_el').find('.stamp').clone();
			var stampArrow = $('#lvupStampWrap').find('.clone_el').find('.stamp_arrow').clone();

			if (i > 0) {
				if (i % 4) {
					// 가로길
					left += 25 * incrs;
					roadLeft = left + (incrs > 0 ? - halfWidth : halfWidth);
					roadTop = top;
					stampRoad.find('.box').css('height', '12px').css('width', '100%');
				} else {
					// 세로길
					roadLeft = left;
					roadTop = top + topStep/2;
					top += topStep;
					stampRoad.find('.box').css('width', '12px').css('height','100%');
					incrs *= -1;
				}
				stampRoad.css('top', roadTop+'%').css('left', roadLeft+'%');
				$('#lvupStampWrap').append(stampRoad);
			}			
			
			stampBase.css('top', top+'%').css('left', left+'%');
			$('#lvupStampWrap').append(stampBase);
			
			if (i <= stampCnt) {
				if (i < stampCnt) {
					stamp.css('top', top+'%').css('left', left+'%');
					$('#lvupStampWrap').append(stamp);
				}

				stampRoad.find('.box').css('background','#ffd492');

				if (i == stampCnt && stampCnt != 0) {
					if (i % 4) {
						stampArrow.css('padding-left','5px');
						incrs > 0 ? stampArrow.find('img').css('transform','rotate(0deg)') : stampArrow.find('img').css('transform','rotate(180deg)');						
					} else {
						stampArrow.find('img').css('transform','rotate(90deg)')
					}

					stampArrow.css('top', roadTop+'%').css('left', roadLeft+'%')

					$('#lvupStampWrap').append(stampArrow);
				}
			}
		}

		$('#lvupStampWrap').find('.clone_el').remove();
		$('#lvupStampWrap').removeAttr('data-stampcnt').removeAttr('data-stampmax');
	}

	$(document).on('click', '.atm_mf_btn_R1' , function() {
		var $btn = $(this),
		$target = $(this).parent().parent().find('.atm_mf_con_slide');

		if($target.hasClass('off')) {
			$target.removeClass('off').addClass('on');
			$target.children('.atm_mf_con_tt1').show();
			$target.animate({height : $target.data('height')},300);
			$btn.attr('src', '/Common/images/btn_minus0.png');
		} else {
			$target.removeClass('on').addClass('off');
			$target.data('height', $target.height());
			$target.animate({height : 0},300, function(){
				$target.children('.atm_mf_con_tt1').hide();
			});
			$btn.attr('src', '/Common/images/btn_plus0.png');
		}
	});
</script>
<div id="atm_wrapper">
	<div id="myinfo_wrapper" class="site">
	<!--wrapper start -->
		<div class="center">
			<form name="frm_sch" method="post">
				<input style="display: none;" type="file" name="photo" accept="image/*">
			</form>
			<section class="top">
				<div class="wrapper">
					<div class="profile">
						<div class="photo">
							<label class="label" data-toggle="tooltip" title='<spring:message code="msg_0153"/>'>
								<c:choose>
									<c:when test="${photo != ''}">
										<img id="avatar" src="/UploadFile/Profile/${photo}" onerror="this.src='/pub/css/profile/img_thum_base0.jpg'">
									</c:when>
									<c:otherwise>
										<img id="avatar" src="/pub/css/profile/img_thum_base0.jpg">
									</c:otherwise>
								</c:choose>
								<input type="file" class="sr-only" id="input" name="image" accept="image/*">
							</label>
						<div class="aprogress">
							<div class="aprogress-bar progress-bar-striped progress-bar-animated" role="aprogressbar" aria-valuenow="0"
							aria-valuemin="0" aria-valuemax="100">0%</div>
						</div>
						<div class="alert" role="alert"></div>
						
					</div>
					<div class="info">
						<p class="info_edit" onclick="location.href='/member/myJoin'"><img src="${libIMG_URL}/Common/images/myadmin_edit_icon.png" alt='<spring:message code="msg_0819"/>'></p>
						<div class="info_default">
							<p class="info_name">
								<strong>${userNickName}</strong>
								<span class="lv">${levelStr}</span>
							</p>
							<p><spring:message code="msg_0163"/> <spring:message code="msg_0164"/><fmt:formatNumber value="${memberLogAlmoneyTotal.rankQ}" pattern="#,###" /><spring:message code="msg_0165"/> | <spring:message code="msg_0166"/> <spring:message code="msg_0164"/><fmt:formatNumber value="${memberLogAlmoneyTotal.rankA}" pattern="#,###" /><spring:message code="msg_0165"/></p>
						</div>
						<div class="info_money">
							<h3 class="sum_money" onclick="$('#infoMoneyMore').toggleClass('show')">
								<spring:message code="msg_0652"/><span> <fmt:formatNumber value="${userAlmoney}" pattern="#,###.#" /></span><spring:message code="msg_0136"/>
							</h3>
							<div onclick="$(this).removeClass('show')" class="more_money_wrap" id="infoMoneyMore">
								<h3 class="sum_money"><spring:message code="msg_0652"/><span> <fmt:formatNumber value="${userAlmoney}" pattern="#,###.0" /></span><spring:message code="msg_0136"/></h3>
								<div class="more_money_table">
									<p class="more_money">
										<span><spring:message code="msg_0805"/></span>
										<span>+ <fmt:formatNumber value="${memberLogAlmoney.imports}" pattern="#,###.0" /><spring:message code="msg_0136"/></span>
									</p>
									<p class="more_money">
										<span><spring:message code="msg_0806"/></span>
										<span>- <fmt:formatNumber value="${memberLogAlmoney.expense}" pattern="#,###.0" /><spring:message code="msg_0136"/></span>
									</p>																	
								</div>
							</div>
						</div>
					</div>
					<div class="menu">
						<p id="tab_3" onclick="location.href='/member/myInfo';  return false;" data-type="default"><spring:message code="msg_0820"/></p>
						<p id="tab_1" onclick="location.href='/member/myInfoLv'; return false;" data-type="lvup"><spring:message code="msg_0821"/></p>
						<c:choose>
							<c:when test="${userLv < 2}">
								<p id="tab_2" class="bottom_on" onclick="alert(getLangStr("jsm_0081")); return false;" data-type="exchange" class="on"><spring:message code="msg_0822"/></p>
							</c:when>
							<c:otherwise>
								<p id="tab_2" class="bottom_on" onclick="location.href='/member/myInfoExch'; return false;" data-type="exchange" class="on"><spring:message code="msg_0822"/></p>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<!-- 모달 -->
						<div class="modal fade" id="modal1" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<h5 class="modal-title" id="modalLabel"><spring:message code="msg_0157"/></h5>
										<button type="button" class="close" data-dismiss="modal" aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</div>
									<div class="modal-body">
										<div class="img-container" style="max-height:250px!important;">
											<img id="image" src="" class="cropper-hidden">
										</div>
										<div class="btn-group">
											<button type="button" class="btn btn-primary" data-method="rotate" data-option="-45" title="Rotate Left" id="rrotateImg">
												<span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="cropper.rotate(-45)">
													<span class="fa fa-rotate-left"><spring:message code="msg_0158"/></span>
												</span>
											</button>
										</div>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="msg_0159"/></button>
										<button type="button" class="btn btn-primary" id="crop"><spring:message code="msg_0160"/></button>
									</div>
								</div>
							</div>
						</div>
					<!-- // 모달 -->
				<%
					int tabs = Integer.parseInt( String.valueOf( request.getAttribute("tab") ) );
					String exchData1 = String.valueOf( request.getAttribute("myInfoExchList_1") );
					String exchData2 = String.valueOf( request.getAttribute("myInfoExchList_2") );
					
					JSONObject global_my = (JSONObject)CommonUtil.getGlobal(request, response);
	
					Map<String, Object> dataHash1 = new Gson().fromJson(exchData1, Map.class);
					Map<String, Object> dataHash2 = new Gson().fromJson(exchData2, Map.class);
					
					int userSeq = Integer.parseInt( String.valueOf( global_my.get("UserSeq") ) );
					int userLv = Integer.parseInt( String.valueOf( global_my.get("UserLv") ) );
	
					String startDate = String.valueOf( dataHash2.get("StartDate") );
					String endDate = String.valueOf( dataHash2.get("EndDate") );
					String[] splitDt = endDate.split("-");
	
					int splitY = 0;
					int splitM = 0;
					int splitS = 0;
					if(dataHash2.size() > 0) {
						splitY = Integer.parseInt(splitDt[0]);
						splitM = Integer.parseInt(splitDt[1]);
						splitS = Integer.parseInt(splitDt[2]);
					}
					else {
						Date date = new Date(); // your date
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						
						splitY = cal.get(Calendar.YEAR);
						splitM = cal.get(Calendar.MONTH);
						splitS = cal.get(Calendar.DAY_OF_MONTH);
					}	
						
					LocalDate nowTime = LocalDate.now();
					LocalDate weekEndTime = LocalDate.of(splitY, splitM, splitS);
					//System.out.println("weekEndTime : " + weekEndTime);
					long deadLine = ChronoUnit.DAYS.between(nowTime, weekEndTime);
	
					BigDecimal exchBaseAlmoney = new BigDecimal( dataHash1.size() > 0 ? String.valueOf( dataHash1.get("ExchBaseAlmoney") ) : "0.0" );
					BigDecimal exchLimitAlmoney = new BigDecimal( dataHash1.size() > 0 ? String.valueOf( dataHash1.get("ExchLimitAlmoney") ) : "0.0" );
					float exchAlmoneyTexRate = dataHash1.size() > 0 ? Float.valueOf(String.valueOf( dataHash1.get("ExchAlmoneyTexRate") ) ) :  0.0f;
					int exchStampCnt = dataHash1.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash1.get("ExchStampCnt") ) ) ) : 0;
					int exchQusRegCnt = dataHash1.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash1.get("ExchQusRegCnt") ) ) ) : 0;
					int exchAnsRegCnt = dataHash1.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash1.get("ExchAnsRegCnt") ) ) ) : 0;
					int exchAnsChoicedCnt = dataHash1.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash1.get("ExchAnsChoicedCnt") ) ) ) : 0;
					int exchAnsEstiCnt = dataHash1.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash1.get("ExchAnsEstiCnt") ) ) ) : 0;
					String isExchOK = dataHash2.size() > 0 ? String.valueOf( dataHash2.get("isExchOK") ) : "";
					BigDecimal almoney = new BigDecimal( dataHash2.size() > 0 ? String.valueOf( dataHash2.get("Almoney") ) : "0.0" );
					int stamp = dataHash2.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash2.get("STAMP") ) ) ) :  0;
					int qusRegCnt = dataHash2.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash2.get("QusRegCnt") ) ) ) :  0;
					int ansRegCnt = dataHash2.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash2.get("AnsRegCnt") ) ) ) :  0;
					int ansChoicedCnt = dataHash2.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash2.get("AnsChoicedCnt") ) ) ) :  0;
					int ansEstiCnt = dataHash2.size() > 0 ? Math.round( Float.valueOf(String.valueOf( dataHash2.get("AnsEstiCnt") ) ) ) :  0;
	
	
					pageContext.setAttribute("exchBaseAlmoney", exchBaseAlmoney);
					pageContext.setAttribute("exchLimitAlmoney", exchLimitAlmoney);
					pageContext.setAttribute("exchAlmoneyTexRate", exchAlmoneyTexRate);
					pageContext.setAttribute("exchStampCnt", exchStampCnt);
					pageContext.setAttribute("exchQusRegCnt", exchQusRegCnt);
					pageContext.setAttribute("exchAnsRegCnt", exchAnsRegCnt);
					pageContext.setAttribute("exchAnsChoicedCnt", exchAnsChoicedCnt);
					pageContext.setAttribute("exchAnsEstiCnt", exchAnsEstiCnt);
					pageContext.setAttribute("isExchOK", isExchOK);
					pageContext.setAttribute("almoney", almoney);
					pageContext.setAttribute("stamp", stamp);
					pageContext.setAttribute("qusRegCnt", qusRegCnt);
					pageContext.setAttribute("ansRegCnt", ansRegCnt);
					pageContext.setAttribute("ansChoicedCnt", ansChoicedCnt);
					pageContext.setAttribute("ansEstiCnt", ansEstiCnt);
				%>
			</div>
		</section>
		<div class="content_wrap" id="menuContentWrap">
			<section class="week_mission exchange">
				<div class="section_top">
					<p class="title"><spring:message code="msg_0837"/></p>
				<% if (deadLine == 0) { %>
					<p class="sub_txt"><spring:message code="msg_0838"/><strong><spring:message code="msg_0839"/></strong><spring:message code="msg_0840"/></p>
				<% } else { %>			
					<p class="sub_txt"><spring:message code="msg_0841"/><strong><%=deadLine%><spring:message code="msg_0842"/></strong><spring:message code="msg_0843"/></p>
				<% }%>
					<div class="date_box">
						<div class="date_btn">
							<p class="arrow arrow_L" id="dateArrowL" onclick="exch_fAjax('/member/myInfoExchAjax', 'json', 'ACT=ExchGetLastWeek&LAST=1');"><img src="${libIMG_URL}/Common/images/myadmin_date_arrowL.png" alt=""></p>
							<p class="content">
								<span id="weekStartDate"><%=startDate != "null" ? startDate : "" %></span> ~ <span id="weekEndDate"><%=endDate != "null" ? endDate : "" %></span>
							</p>
							<p class="arrow arrow_R" id="dateArrowR"><img src="${libIMG_URL}/Common/images/myadmin_date_arrowR.png" alt=""></p>
						</div>
					</div>
				</div>
				<div class="white_wrap">
					<div class="sum">
						<p class="sub_txt"><spring:message code="msg_0844"/></p>
						<div class="sum_graph">
							<p class="graph"></p>
							<p class="sum_txt"><span></span>%</p>
						</div>
					</div>
					<div class="detail">
						<div class="detail_table cell2">
						<% if (exchQusRegCnt != 0) { %>
							<div class="cell">
								<div class="box">
									<div class="detail_graph">
										<p class="graph_bg"></p>
										<p class="graph"></p>
									</div>
									<div class="detail_content">
										<p><spring:message code="msg_0365"/></p>
										<p class="count"><span class="prgDetailCnt" id="weekQCnt"><%=qusRegCnt > exchQusRegCnt ? exchQusRegCnt : qusRegCnt%></span>/<span class="prgDetailMaxCnt" id="weekQMax"><%=exchQusRegCnt%></span></p>
									</div>
								</div>
							</div>
						<% } %>
						<% if (exchAnsRegCnt != 0) { %>
							<div class="cell">
								<div class="box">
									<div class="detail_graph">
										<p class="graph_bg"></p>
										<p class="graph"></p>
									</div>
									<div class="detail_content">
										<p><spring:message code="msg_0366"/></p>
										<p class="count"><span class="prgDetailCnt" id="weekACnt"><%=ansRegCnt > exchAnsRegCnt ? exchAnsRegCnt : ansRegCnt %></span>/<span class="prgDetailMaxCnt" id="weekAMax"><%=exchAnsRegCnt%></span></p>
									</div>
								</div>
							</div>
						<% } %>
						</div>
						<div class="detail_table cell3">
						<% if (exchAnsEstiCnt != 0) { %>
							<div class="cell">
								<div class="box">
									<div class="detail_graph">
										<p class="graph_bg"></p>
										<p class="graph"></p>
									</div>
									<div class="detail_content">
										<p><spring:message code="msg_0847"/></p>
										<p class="count"><span class="prgDetailCnt" id="weekAEstiCnt"><%=ansEstiCnt > exchAnsEstiCnt ? exchAnsEstiCnt : ansEstiCnt %></span>/<span class="prgDetailMaxCnt" id="weekAEstiMax"><%=exchAnsEstiCnt%></span></p>
									</div>
								</div>
							</div>
						<% } %>
						<% if (exchAnsChoicedCnt != 0) { %>
							<div class="cell">
								<div class="box">
									<div class="detail_graph">
										<p class="graph_bg"></p>
										<p class="graph"></p>
									</div>
									<div class="detail_content">
										<p><spring:message code="msg_0848"/></p>
										<p class="count"><span class="prgDetailCnt" id="weekAChoicedCnt"><%=ansChoicedCnt > exchAnsChoicedCnt ? exchAnsChoicedCnt : ansChoicedCnt %></span>/<span class="prgDetailMaxCnt" id="weekAChoicedMax"><%=exchAnsChoicedCnt%></span></p>
									</div>
								</div>
							</div>
						<% } %>
						</div>
					</div>
				</div>
			</section>
			<section class="my_exchange">
				<div class="section_top">
					<p class="title"><spring:message code="msg_0849"/></p>
				<% if (exchStampCnt <= stamp) { %>
					<p class="sub_txt"><strong><spring:message code="msg_0850"/></strong></p>
				<% } else { %>
					<p class="sub_txt"><spring:message code="msg_0851"/><strong><%=exchStampCnt - stamp%><spring:message code="msg_0370"/></strong><spring:message code="msg_0852"/></p>
				<% } %>
				</div>
				
				<div class="white_wrap">
					<div class="exchange_sum">
					<%
						for (int i = 0; i < exchStampCnt; i++) {
							if (i < stamp) {
					%>
						<p class="graph fill"></p>
					<%
							} else {
					%>
						<p class="graph"></p>
					<%
							}
						}		
					%>
					</div>
					<% if (isExchOK == "Y") { %>
						<div class="exch_btn_wrap">
							<p class="exch_btn">
								<a href="/member/account/exchange/exchangeAlpay"><spring:message code="msg_0853"/> <i class="material-icons">keyboard_arrow_right</i></a>
							</p>
						</div>
				    <% } else if (exchStampCnt <= stamp) { %>
				        <div class="exch_btn_wrap" style="margin-top:25px;color:#777">
				            <p><spring:message code="msg_0854"/></p>
				            <p><spring:message code="msg_0855"/><strong><spring:message code="msg_0856"/></strong><spring:message code="msg_0857"/></p>
				        </div>
					<% } %>
					</div>
				</section>
			</div>
		</div>
	</div>
</div>
<script>
	$('.contents-wrapper').fadeIn(1000);

	$(function(){
		fweekGraph();
		flvupStamp();
		
		$('body').addClass('lvup');
	})

	function imagerotate(ro) {
		cropper = new Cropper(image, {
			aspectRatio: 1,
			viewMode: 1,
			viewMode: 1,
			rotate: -90
		});
	}

	window.addEventListener('DOMContentLoaded', function () {
		var avatar = document.getElementById('avatar');
		var image = document.getElementById('image');
		var input = document.getElementById('input');
		var $progress = $('.aprogress');
		var $progressBar = $('.aprogress-bar');
		var $alert = $('.alert');
		var $modal = $('#modal1');
		var cropper;

		input.addEventListener('change', function (e) {
			var files = e.target.files;
			var done = function (url) {
				input.value = '';
				image.src = url;
				$alert.hide();
				$modal.modal('show');
			};
			var reader;
			var file;
			var url;

			if (files && files.length > 0) {
				file = files[0];

				if (URL) {
					done(URL.createObjectURL(file));
				} else if (FileReader) {
					reader = new FileReader();
					reader.onload = function (e) {
						done(reader.result);
					};
					reader.readAsDataURL(file);
				}
			}
		});

		document.getElementById('rrotateImg').addEventListener('click', function () {
			cropper.rotate(-90);
		});

		$modal.on('shown.bs.modal', function () {
			cropper = new Cropper(image, {
				aspectRatio: 1,
				viewMode: 1,
			});
		}).on('hidden.bs.modal', function () {
			cropper.destroy();
			cropper = null;
		});

		document.getElementById('crop').addEventListener('click', function () {
			var initialAvatarURL;
			var canvas;

			$modal.modal('hide');

			if (cropper) {
				canvas = cropper.getCroppedCanvas({
					width: 80,
					height: 80,
				});

				initialAvatarURL = avatar.src;
				avatar.src = canvas.toDataURL();
				$progress.hide();
				$alert.removeClass('alert-success alert-warning');
				canvas.toBlob(function (blob) {
					var formData = new FormData();

					formData.append('photo', blob, "blob.png");

					$.ajax('/member/uploadProfileImg_n', {
						method: 'POST',
						data: formData,
						processData: false,
						contentType: false,

						xhr: function () {
							var xhr = new XMLHttpRequest();

							xhr.upload.onprogress = function (e) {
								var percent = '0';
								var percentage = '0%';

								if (e.lengthComputable) {
									percent = Math.round((e.loaded / e.total) * 100);
									percentage = percent + '%';
									$progressBar.width(percentage).attr('aria-valuenow', percent).text(percentage);
								}
							};

							return xhr;
						},

						success: function (result) {
							$alert.show().addClass('alert-success').text('Upload success');
							$alert.hide();
						},

						error: function () {
							avatar.src = initialAvatarURL;
							$alert.show().addClass('alert-warning').text('Upload error');
							$alert.hide();
						},

						complete: function () {
							$progress.hide();
						},
					});
				});
			}
		});
	});
</script>
<script src="/Common/js/bootstrap.min.js"></script>
</body>