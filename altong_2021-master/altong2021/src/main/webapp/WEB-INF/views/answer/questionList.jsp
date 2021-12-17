<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@ page import="com.altong.web.logic.event.EventVO"%>
<%@ page import="com.altong.web.logic.util.CommonUtil"%>
<%@ page import="com.altong.web.logic.util.CodeUtil"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp"%>
<%@ page import="org.json.simple.JSONObject"%>
<%
String msg_0149 = CommonUtil.getLangMsg(request, "msg_0149");
String msg_0161 = CommonUtil.getLangMsg(request, "msg_0161");
String msg_0216 = CommonUtil.getLangMsg(request, "msg_0216");
String msg_0217 = CommonUtil.getLangMsg(request, "msg_0217");
String msg_0218 = CommonUtil.getLangMsg(request, "msg_0218");
String msg_0219 = CommonUtil.getLangMsg(request, "msg_0219");
String msg_0220 = CommonUtil.getLangMsg(request, "msg_0220");
String msg_0221 = CommonUtil.getLangMsg(request, "msg_0221");
String msg_0222 = CommonUtil.getLangMsg(request, "msg_0222");
String msg_0223 = CommonUtil.getLangMsg(request, "msg_0223");
String msg_0224 = CommonUtil.getLangMsg(request, "msg_0224");
String msg_0225 = CommonUtil.getLangMsg(request, "msg_0225");
String msg_0226 = CommonUtil.getLangMsg(request, "msg_0226");
String msg_0227 = CommonUtil.getLangMsg(request, "msg_0227");
String msg_0230 = CommonUtil.getLangMsg(request, "msg_0230");
String msg_0235 = CommonUtil.getLangMsg(request, "msg_0235");
String msg_0236 = CommonUtil.getLangMsg(request, "msg_0236");
String msg_0237 = CommonUtil.getLangMsg(request, "msg_0237");
String msg_0255 = CommonUtil.getLangMsg(request, "msg_0255");
String msg_0318 = CommonUtil.getLangMsg(request, "msg_0318");
String msg_0331 = CommonUtil.getLangMsg(request, "msg_0331");
String msg_0332 = CommonUtil.getLangMsg(request, "msg_0332");

pageContext.setAttribute("msg_0149", msg_0149);
pageContext.setAttribute("msg_0161", msg_0161);
pageContext.setAttribute("msg_0216", msg_0216);
pageContext.setAttribute("msg_0217", msg_0217);
pageContext.setAttribute("msg_0218", msg_0218);
pageContext.setAttribute("msg_0219", msg_0219);
pageContext.setAttribute("msg_0220", msg_0220);
pageContext.setAttribute("msg_0221", msg_0221);
pageContext.setAttribute("msg_0222", msg_0222);
pageContext.setAttribute("msg_0223", msg_0223);
pageContext.setAttribute("msg_0224", msg_0224);
pageContext.setAttribute("msg_0225", msg_0225);
pageContext.setAttribute("msg_0226", msg_0226);
pageContext.setAttribute("msg_0227", msg_0227);
pageContext.setAttribute("msg_0230", msg_0230);
pageContext.setAttribute("msg_0235", msg_0235);
pageContext.setAttribute("msg_0236", msg_0236);
pageContext.setAttribute("msg_0237", msg_0237);
pageContext.setAttribute("msg_0255", msg_0255);
pageContext.setAttribute("msg_0331", msg_0331);
pageContext.setAttribute("msg_0332", msg_0332);
%>
<head>
<c:if test="${lang != 'en'}">
<link rel="stylesheet" href="/pub/answer/questionList/questionList.css?ver=1.3">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.2">
</c:if>
<c:if test="${lang == 'en'}">
<link rel="stylesheet" href="/pub/answer/questionList/questionList_en.css?ver=1.3">
<link rel="stylesheet" href="/pub/css/mediaQuery_en.css?ver=1.2">
</c:if>
<link rel="stylesheet" href="/pub/css/languages_common.css?ver=1.3">

<script type="text/javascript" src="/Common/js_new/default/languages.js?ver=1.6"></script>
<script>
$(function(){
	$('.language_icon img').click(function(e){
		//번역 유무에 따라서
		//번역이 없는 경우 기계 번역 실행후 결과를 가져와서 tSeq 를 가져온다.
		//번역이 있는 경우 사람번역이 있으면 사람번역의 tSeq 를 가져온다. 
        var contentSeq = $(this).attr('data-seq');
		var contentType = "Q";
        //console.log('contentSeq : ' + contentSeq);
        //alert('서비스 준비중입니다!');
        
        var qt_seq = $('#qt_' + contentSeq).val();
        
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
							
							if(contentType == 'Q') {
								//tSeq 설정
								//tTitle 만교체(span id="que_") / qt_
								tTitle = tTitle.replace(/(<([^>]+)>)/ig,"");
								
								$('#que_' + contentSeq).html(tTitle);
								$('#qt_' + contentSeq).val(tSeq);
							}
							else if(contentType == 'A') {
								//answerList 에서 상세 구현
							}
							else {
								//answerList 에서 상세 구현
							}
							
							break;
						case 'N':
							alert('${msg_0223}');
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
							
							$('#que_' + contentSeq).html(tTitle);
							$('#qt_' + contentSeq).val(''); // 번역문 코드 제거
							
							break;
						case 'N':
							alert('${msg_0223}');
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
        
        e.stopPropagation();
        e.preventDefault();
    });
});

function goAnswerList(seq) {
	var qtSeq = $('#qt_' + seq).val();
	var url = '/answer/answerList?Seq=' + seq + '&CurPageName=/answer/questionList&Section1=0&src_Sort=Seq&src_OrderBy=DESC';
	
	if(qtSeq == '') {
		location.href = url;
	}
	else {
		location.href = url + '&qtSeq=' + qtSeq;
	}
}

function formatDateForSpan(v) {
	//TODO: fautoURL 함수랑 같은 방식으로 변경하기
	var v = $("#" + v);
	var curDate1 = v.text();
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
				resultDate = '${msg_0216}';
			} else {
				resultDate = Math.floor(gapSec) + '${msg_0217}';
			}
		} else if (Math.floor(gapHour) < 1) {
			resultDate = Math.floor(gapMin) + '${msg_0218}';
		} else {
			resultDate = Math.floor(gapHour) +'${msg_0219}';
		}
	} else {
		if (gapYear >= 1) {
			resultDate = Math.floor(gapYear) + '${msg_0220}';
		} else if (gapMonth >= 1) {
			resultDate = Math.floor(gapMonth) + '${msg_0221}';
		} else {
			resultDate = Math.floor(gapDay) + '${msg_0222}';
		}
	}
	v.html(resultDate);
}

function goQuestionVote(act, contentSeq, contentType) {
	$.ajax({
		type: 'post',
		url: '/common/questionVote',
		data: { 'act' : act, 'contentSeq' : contentSeq, 'contentType' : contentType },
		dataType: 'json',
		crossDomain: true,
		success: function (r) {
			//console.log('r.result : ' + r.result);
			switch (r.result) {
				case 'true':
					var good = numberWithCommas(r.arr.good);
					var bad = numberWithCommas(r.arr.bad);
					
					$('#good_' + contentSeq).text(good);
					$('#bad_' + contentSeq).text(bad);
					
					
					break;
				case 'false':
					alert(getLangStr("jsm_0020"));
					location.href="/default/login";
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

function votePaus() {
	if(confirm(getLangStr("jsm_0020"))) {
		location.href = "/default/login";
	}
}

function goLangSearch(lang) {
	var Section1 = '${Section1}';
	var src_Sort = '${src_Sort}';
	var src_OrderBy = '${src_OrderBy}';
	
	var url = '/answer/questionList?Section1=' + Section1 + '&src_Sort=' + src_Sort + '&src_OrderBy=' + src_OrderBy + '&targetLang=' + lang;
	
	location.href = url;
}

function goLangChange() {
	var lang = $('#langSel option:selected').val();
	
	goLangSearch(lang)
}
</script>
</head>
<body>
<%
CodeUtil code = new CodeUtil(request);

Map<String, String> lvCd = code.getCODE_MEM_LV_CD();
Map<String, String> lvNm = code.getCODE_MEM_LV_NM();

String lvName = "0,";
for (int i = 1; i <= lvCd.size(); i++) {
	lvName = lvName + "'" + lvNm.get(String.valueOf(i)) + "',";
	//System.out.println(i + " : " + lvName);
}

JSONObject global_info = (JSONObject) CommonUtil.getGlobal(request, response);

%>
<%@ include file="/pub/menu/topMenu.jsp"%>
<div id="wrapper">
<%@ include file="/Common/include/EventBanner.jsp"%>
	<div id="atm_ranka">
		<div class="center">
			<ul>
				<li><a href="/question/bestList"><spring:message code="msg_0358"/></a></li>
				<li class="check"><a href="/answer/questionList"><spring:message code="msg_0161"/></a></li>
				<%
					if (global_info != null) {
				%>
				<li><a href="javascript:void(0);" onClick="location.href='/answer/favoriteList';"><spring:message code="msg_0175"/></a></li>
				<%
					} else {
				%>
				<li><a href="javascript:void(0);" onClick="goConfirmLogin('frm_sch', '/default/login');"><spring:message code="msg_0175"/></a></li>
				<%}%>
				<li><a href="/question/eventList"><spring:message code="msg_0183"/></a></li>
				<li><a href="/answer/rankAnswer"><spring:message code="msg_0182"/></a></li>
			</ul>
		</div>
	</div>
	<%@ include file="/Common/include_new/topAnswer.jsp"%>
	<form name="frm_sch" method="post" onSubmit="return false;">
		<div class="categoryDetails">
			<div class="atm_cateadd_navi">
				<div style="-ms-grid-column: 1;">
					<p><spring:message code="msg_0161"/></p>
				</div>
				<img id="selectFinishXbtn"
					style="float: right; padding-bottom: 10px; height: 30px; width: 20px; -ms-grid-column: 2;"
					src="/Common/images/btn_x_4.png">
			</div>
			<div class="atm_cateadd_con" align="center">
				<ul class="favorite_list"></ul>
				<p id="selectFinish"><spring:message code="msg_0363"/></p>
			</div>
		</div>
		<div class='toast' style='display: none'></div>
		<input type="hidden" id="src_param">
		<input type="hidden"
			name="src_Target" value="${CurPageName}">
		
		<script>
			var para = document.location.href.split("?")[1];
			$("input[name=src_Target]").attr("value", $("input[name=src_Target]").attr("value") + "?" + para)
		</script>
	
		<input type="hidden" name="pg" value="${n_curpage}">
		<input type="hidden" name="Section1" value="${Section1}">
		<div id="section_question">
			<div class="center">
				<div class="sq_tit">
					<div class="lang_container">
						<div id="left_lang">
							<i></i>
						</div>	
						<div id="languages_pack">
							<div class="language_box">
								${langString}
							</div>
						</div>
						<div id="right_lang">
							<i></i>
						</div>
					</div>
					<div class="lang_mobile">
                    	${langSel}
                    </div>
					<ul class="sq_tab">
						<li><a href="javascript:void(0);" onClick="javascript:location.href='/answer/questionList?Section1=&src_Sort=DateReg&src_OrderBy=DESC&targetLang=${targetLang}';" class="<c:if test="${src_Sort == 'DateReg' or src_Sort == ''}">on</c:if>"><spring:message code="msg_0359"/></a></li>
						<li><a href="javascript:void(0);"  onClick="javascript:location.href='/answer/questionList?Section1=&src_Sort=AnswerCount&src_OrderBy=ASC&targetLang=${targetLang}';" class="<c:if test="${src_Sort == 'AnswerCount'}">on</c:if>"><spring:message code="msg_0360"/></a></li>
						<li><a href="javascript:void(0);" onClick="javascript:location.href='/answer/questionList?Section1=&src_Sort=TransCount&src_OrderBy=ASC&targetLang=${targetLang}';" class="<c:if test="${src_Sort == 'TransCount'}">on</c:if>"><spring:message code="msg_0364"/></a></li>
					</ul>
				</div>
			</div>
		</div>
	
		<div id="section_list">
			<div class="center">
			<c:forEach var="item" items="${questionList}" varStatus="status">
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
				<c:set var="lv" value="${lvN}" />
				<c:if test="${item.flagNickName == 'N' }">
					<c:set var="lv" value='${msg_0331}' />
				</c:if>
				<c:if test="${item.flagNickName != 'N' }">
					<c:if test="${usrLv == '99' }">
						<c:set var="lv" value='${msg_0149}' />
					</c:if>
					<c:if test="${usrLv != '99' }">
						<c:set var="lv" value="${lvN}" />
					</c:if>
				</c:if>
				<c:set var="photo" value="${item.photo}" />
				<c:if test="${item.photo == '' or item.flagNickName == 'N'}">
					<c:set var="photo" value="img_thum_base0.jpg" />
				</c:if>
				<c:if test="${item.nickName == null}">
					<c:set var="photo" value="img_thum_base0.jpg" />
				</c:if>
	
				<c:set var="nick" value="${item.nickName}" />
				<c:if test="${item.nickName == null}">
					<c:set var="nick" value='${msg_0237}' />
				</c:if>
				<c:if test="${item.flagNickName == 'N'}">
					<c:set var="nick" value='${msg_0331}' />
				</c:if>
				<section>
					<a 
						href="javascript:goAnswerList('${item.seq}')"
						class="section_list_el">
						<div class="list_profile">
							<figure class="">
								<img src="/UploadFile/Profile/${photo}" onerror="this.src='/pub/css/profile/img_thum_base0.jpg'">
								<c:if test="${item.memberSeq != 10000691 and  item.memberSeq != 10003513}">
								<figcaption class="nation_n">${item.nation}</figcaption>
								</c:if>
							</figure>
						</div>
						<ul>
							<li>
							<c:set var="item_almoney" value="${item.almoney}" />
							<fmt:parseNumber var="item_almoney_2" type="number" value="${item_almoney}"></fmt:parseNumber>
							<% 
								int usr_color = Integer.parseInt(String.valueOf(pageContext.getAttribute("item_almoney_2")));
							
								if (usr_color < 5000) { %>
									<span class="yellow_almoney">
										<fmt:formatNumber value="${item.almoney}" pattern="#,###" />
									</span><span class="answerTitle_trans" id="que_${item.seq}">${item.title}</span>
							<% }else{ %>
									<span class="red_almoney">
										<fmt:formatNumber value="${item.almoney}" pattern="#,###" />
									</span><span class="answerTitle_trans" id="que_${item.seq}">${item.title}</span>
							<% } %>
							<input type="hidden" id="qt_${item.seq}" name="qtSeq" value=""/>
							</li>
							<li>
								<ul>
									<li id="QusD_${item.seq}">${item.conDate}</li>
									<li>
										<ul>
											<li><img src="/pub/css/images/icon_view_q.svg"
												alt="viewicon"><fmt:formatNumber value="${item.readCount  + item.readCount_Answ}" pattern="#,###" /></li>
											<li><span class="${item.flagChoice}"></span><fmt:formatNumber value="${item.answCount}" pattern="#,###" /></li>
											<li><img src="/pub/css/images/icon_reply_q.svg"
													alt="replyicon"> <fmt:formatNumber
													value="${item.replyCount}" pattern="#,###" /></li>
										</ul>
									</li>
								</ul>
								<div class="lang_smile">
									<c:if test="${item.extraAlmoney > 0}"><p class="honhonAl"><img src="/pub/answer/answerList/images/answer_almoney.svg" alt='<spring:message code="msg_0230"/>'></p></c:if>
								
									<c:if test="${userLv > 0}">	
										<c:choose>
											<c:when test="${item.memberSeq != userSeq}">
												<p class="smile_icon"><img src="/pub/css/images/smile_q.svg" alt='<spring:message code="msg_0231"/>'><span id="good_${item.seq}"><fmt:formatNumber value="${item.good}" pattern="#,###" /></span></p>
												<p class="sad_icon"><img src="/pub/css/images/sad_q.svg" alt='<spring:message code="msg_0232"/>'><span id="bad_${item.seq}"><fmt:formatNumber value="${item.bad}" pattern="#,###" /></span></p>
											</c:when>
											<c:otherwise>
												<p class="smile_icon"><img src="/pub/css/images/smile_q.svg" alt='<spring:message code="msg_0231"/>'><span><fmt:formatNumber value="${item.good}" pattern="#,###" /></span></p>
												<p class="sad_icon"><img src="/pub/css/images/sad_q.svg" alt='<spring:message code="msg_0232"/>'><span><fmt:formatNumber value="${item.bad}" pattern="#,###" /></span></p>
											</c:otherwise>
										</c:choose>
									</c:if>
									<c:if test="${userLv == 0}">
										<p class="smile_icon"><img src="/Common/images/smile.svg" alt='<spring:message code="msg_0231"/>'><span><fmt:formatNumber value="${item.good}" pattern="#,###" /></span></p>
										<p class="sad_icon"><img src="/Common/images/sad.svg" alt='<spring:message code="msg_0232"/>'><span><fmt:formatNumber value="${item.bad}" pattern="#,###" /></span></p>
									</c:if>
								</div>
							</li>
							<li>
								<c:if test="${item.memberSeq != 10000691 and  item.memberSeq != 10003513}">
								<img src="/Common/images/nation/${item.nation}.svg" alt="${item.nation}">
								</c:if>
								<b><%if(lv==0){%><spring:message code="msg_0237"/><%}else{%>${fn:replace(lv, "'", "")}<%}%></b><span>${nick}</span>
							</li>
						</ul>
						<c:if test="${item.lang != sourceLang}">
						<div class="language_icon">
							<img class="answer_lang" src="/Common/images/language.svg" alt='<spring:message code="msg_0362"/>' data-seq="${item.seq}">
						</div>
						</c:if>
					</a>
				</section>
				<script>formatDateForSpan('QusD_'+${item.seq});</script>
			</c:forEach>
			</div>
		</div>
	
	<div class="list_pasing">
		${pageStr}
	</div>
</form>


<div id="top_btn">
	<a href="javascript:void(0);"> <span> <img src="/Common/images/top_btn_arrow.svg" alt="top_btn">
	</span>
	</a>
</div>

<script>
function viewCateoriedQuestion() {
	const $questionWrapper = $('.atm_ranka12_con');
	const $navWrapper = $('.atm_cateadd_navi > div');
	
	var parameter = makeParameterCategory(arguments[0]);
	var formParameter = '';
	for(let key in parameter) formParameter += (key + '=' + parameter[key] + '&');
	var questionCount, questionData;
	var page =  arguments[1] || (parseInt(getUrlParams().pg || 1));
	var pageSize = <%=request.getAttribute("n_pagesize")%> || 30;
	var pageCnt = <%=request.getAttribute("n_pagescnt")%> || 7;
	/* 카테고리 아이콘 컬러 변경 */
	for(let i = 1; i <= 11; i++) {
		let t = $('li.swiper-slide:nth-child(' + i + ') > a > img:nth-child(1)').attr('src').replace(/_on/gi, '');

		$('li.swiper-slide:nth-child(' + i + ')').removeClass('list_select')
		$('li.swiper-slide:nth-child(' + i + ') > a > img:nth-child(1)').attr('src', t);
		
	}
	var tCode = (1 + parseInt(arguments[0][0] || 0));
	var t = $('li.swiper-slide:nth-child(' + tCode + ') > a > img:nth-child(1)').attr('src').replace(/.svg/gi, '_on.svg');

	$('li.swiper-slide:nth-child(' + tCode + ')').addClass('list_select');
	$('li.swiper-slide:nth-child(' + tCode + ') > a > img:nth-child(1)').attr('src', t);
	$('li.swiper-slide').css('color','#fff');
	$('li.swiper-slide:nth-child(' + tCode + ')').css('color','yellow');
	/*네비 텍스트 변경*/
	$navWrapper.html('<p class="cateNavi" value="0" onClick="fcateNaviClick(this)">${msg_0161}&nbsp;</p>'); // 전체 -> ' + getLangStr("cate0") + '
	//console.log('실행중2');
	if (formParameter) {
		var categoryCodeName;
		$.ajax({
			url: '/answer/getCategoryCodeName?' + formParameter +  '&time=' + new Date(),
			type: 'post',
			async: false,
			success: function(data) {
				//console.log('data : ' + data);
				categoryCodeName = data[0];//$.parseJSON(data)[0];
				//console.log('categoryCodeName : ' + categoryCodeName);
			},
			error: function() {
				alert('${msg_0318}');
			}
		});
		let cnt = 0;
		for(let key in categoryCodeName) {
			if(categoryCodeName[key] === '')
				break;
			let value = "";
			for(let i = 0; i <= cnt; i++) {
				value += arguments[0][i] + "_";
			}
			
			var valArr = value.split('_');
			
			let varArrCnt = valArr.length - 1;
			let varItemId = varArrCnt - 1;
			//console.log('valArr.length :  ' + (valArr.length - 1));
			//console.log('valArr[' + (varItemId) + '] = ' + valArr[varItemId]);
			
			let cateNm;
			//cateNm = categoryCodeName[key].replace(/;/gi, '').replace(/@/, '');
			
			if(varArrCnt == 1) {
				cateNm = getLangStr("cate" + valArr[varItemId]);
			}
			else if(varArrCnt == 2) {
				cateNm = getLangStr("cate2_" + valArr[varItemId]);
			}
			else if(varArrCnt == 3) {
				cateNm = getLangStr("cate3_" + valArr[varItemId]);
			}
			else if(varArrCnt == 4) {
				cateNm = getLangStr("cate4_" + valArr[varItemId]);
			}
			else if(varArrCnt == 5) {
				cateNm = getLangStr("cate5_" + valArr[varItemId]);
			}
			
			$navWrapper.append('<p>&nbsp;>&nbsp;</p>');
			$navWrapper.append('<p class="cateNavi" value="' + value + '" onClick="fcateNaviClick(this)">' + cateNm + '</p>')
			cnt++;
		}
		$navWrapper.children(':last-child').css('color', '#fd0031')
	}
	$("#src_param").val(formParameter);
	
	$(document).on('click', '#selectFinish', function() {
		//$('.categoryDetails').css('display', 'none');
		
		var src_Sort = '<%=request.getAttribute("src_Sort")%>';
		if(src_Sort == 'null' || src_Sort == null) { src_Sort = 'DateReg'; }
		
		var formParameter = $("#src_param").val();
		var page =  arguments[1] || (parseInt(getUrlParams().pg || 1));
		var pageSize = <%=request.getAttribute("n_pagesize")%> || 30;
		//page
		var url = '/answer/questionList?' + formParameter + 'pg=1'  + '&page_size=' + pageSize + '&src_Sort='+src_Sort + '&time=';
		
		location.href = url;
	});
}
</script>
<script type="text/javascript" src="/pub/answer/questionList/questionList.js?ver=2.6" ></script>
</div>
</body>
