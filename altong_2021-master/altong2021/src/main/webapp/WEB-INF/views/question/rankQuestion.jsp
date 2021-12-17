<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<%
	JSONObject global_info = (JSONObject) CommonUtil.getGlobal(request, response);
%>
<head>
<link rel="stylesheet" href="/pub/answer/rankAnswer/rankAnswer.css?ver=1.3">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.5">

<link rel="stylesheet" href="/pub/css/rankAnswer_lang.css?ver=1.1">
<script src="/Common/js_new/default/languages.js?ver=1.0"></script>
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<div id="wrapper">
	<%@ include file="/Common/include/EventBanner.jsp" %>
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
							
							$('#que_' + contentSeq).html(tTitle);
							$('#qt_' + contentSeq).val(tSeq);
							
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
							
							$('#que_' + contentSeq).html(tTitle);
							$('#qt_' + contentSeq).val(''); // 번역문 코드 제거
							
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
        
        e.stopPropagation();
        e.preventDefault();
    });
});

function goAnswerList(seq) {
	var qtSeq = $('#qt_' + seq).val();
	var url = '/answer/answerList?Seq=' + seq + '&CurPageName=/question/rankQuestion';
	
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
				resultDate = getLangStr("jsm_0105");
			} else {
				resultDate = Math.floor(gapSec) + getLangStr("jsm_0106");
			}
		} else if (Math.floor(gapHour) < 1) {
			resultDate = Math.floor(gapMin) + getLangStr("jsm_0107");
		} else {
			resultDate = Math.floor(gapHour) + getLangStr("jsm_0108");
		}
	} else {
		if (gapYear >= 1) {
			resultDate = Math.floor(gapYear) + getLangStr("jsm_0109");
		} else if (gapMonth >= 1) {
			resultDate = Math.floor(gapMonth) + getLangStr("jsm_0110");
		} else {
			resultDate = Math.floor(gapDay) + getLangStr("jsm_0111");
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

$(function(){
    $('.smile_icon').click(function(e){
    	e.stopPropagation();
        e.preventDefault();
    });
	$('.sad_icon').click(function(e){
		e.stopPropagation();
        e.preventDefault();
    });
});

function goLangSearch(lang) {
	var trec = '${trec}';
	var src_Sort = '${src_Sort}';
	var pg = '${n_curpage}';
	
	///question/rankQuestion?trec=null&src_Sort=ReadAlmoney&pg=2
	var url = '/question/rankQuestion?trec=' + trec + '&src_Sort=' + src_Sort + '&pg=' + pg + '&targetLang=' + lang;
	
	location.href = url;
}

function goLangChange() {
	var lang = $('#langSel option:selected').val();
	
	goLangSearch(lang)
}
</script>
	<div id="atm_ranka">
		<div class="center">
			<ul>
				<li><a href="/question/bestList"><spring:message code="msg_0358"/></a></li>
				<li><a href="/answer/questionList"><spring:message code="msg_0161"/></a></li>
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
				<li class="check"><a href="/answer/rankAnswer"><spring:message code="msg_0182"/></a></li>
			</ul>
		</div>
	</div>
	<div id="rank_gnb_btn">
		<div class="center">
			<ul>
				<li class="on">
					<a href="/question/rankQuestion">
						<spring:message code="msg_0365"/>
					</a>
				</li>
				<li>
					<a href="/answer/rankAnswer">
						<spring:message code="msg_0366"/>
					</a>
				</li>
			</ul>
		</div>
	</div>
	<input type="hidden" name="FlagOption" value="${FlagOption}">
	<div class="atm_rankq31_con">
		<div class="center">
			<div class="atm_ranka_tab2">
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
				<div class="atm_ranka_tab2_pc">
					<p class="atm_ranka_tabc6_on"><spring:message code="msg_0367"/></p>
					<p class="atm_ranka_tabc7" onClick="javascript:location.href='/question/rankUser?FlagOption=Money&targetLang=${targetLang}';"><spring:message code="msg_0368"/></p>
					<p class="atm_ranka_tabc8" onClick="javascript:location.href='/question/rankUser?FlagOption=Count&targetLang=${targetLang}';"><spring:message code="msg_0369"/></p>
				</div>
			</div>
			<div class="atm_rank_el_container">
				<c:forEach var="item" items="${rankList}" varStatus="status">
					<div class="atm_rank_el">
						<c:choose>
							<c:when test="${item.rownum <= 1}">
								<h4 class="first">${item.rownum}</h4>
							</c:when>
							<c:when test="${item.rownum == 2 or item.rownum == 3}">
								<h4 class="other">${item.rownum}</h4>
							</c:when>
							<c:otherwise>
								<h4>${item.rownum}</h4>
							</c:otherwise>
						</c:choose>
						<input type="hidden" id="qt_${item.seq}" name="tSeq" value=""/>
						<div class="atm_ranka31_eltexts">
							<p class="atm_ranka31_el_c2" onClick="javascript:goAnswerList('${item.seq}');">
								<span class="answerTitle_trans" id="que_${item.seq}">${item.title }</span>
							</p>
							<p class="atm_ranka31_el_c3">
								<span class="atm_ranka31_c2"><img src="/pub/css/images/icon_view.svg" class="atm_viewicon"/><fmt:formatNumber value="${item.readCount}" type="number" /></span>
								<span class="atm_ranka31_c2"><i class="atm_viewicon"></i><fmt:formatNumber value="${item.answCount}" type="number" /></span>
							</p>
						</div>
						<p class="atm_ranka_el_c1">
							<span class="atm_ranka31_c6">
								<fmt:formatNumber value="${item.readAlmoney}" type="number" />
							</span>
							<spring:message code="msg_0136"/>
						</p>
						<c:if test="${item.lang != sourceLang}">
						<div class="language_icon">
		                   <img class="answer_lang" src="/Common/images/language.svg" alt='<spring:message code="msg_0362"/>' data-seq="${item.seq}">
		                </div>
		                </c:if>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</div>
<div class="list_pasing">
	${pageStr}
</div>
<div id="top_btn">
	<a href="javascript:void(0);"> <span> <img
			src="/Common/images/top_btn_arrow.svg" alt="top_btn">
	</span>
	</a>
</div>
<script type="text/javascript" src="/Common/src/answer/rankAnswer/rankAnswer.js?1.1" ></script>
</body>