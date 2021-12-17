<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jasp.util.*" %>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<%
	JSONObject global_info = (JSONObject) CommonUtil.getGlobal(request, response);
%>
<head>
<link rel="stylesheet" href="/pub/answer/rankAnswer/rankAnswer.css?ver=1.8">
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
		var contentType = "A";
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
							//var tTitle = r.arr.tTitle;
							var tContents = r.arr.tContents;
							
							tContents = tContents.replace(/(<([^>]+)>)/ig,"");
							
							if(tContents.lenth > 20) {
								tContents = tContents.substr(0, 20) + '...';
							}
							
							$('#que_' + contentSeq).html(tContents);
							$('#qt_' + contentSeq).val(tSeq);
							
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
        	//원문 로딩
			$.ajax({
				type: 'post',
				url: '/translate/getAnsOrgTitle',
				data: { 'contentSeq' : contentSeq},
				dataType: 'json',
				crossDomain: true,
				success: function (r) {
					//console.log('r.result : ' + r.result);
					switch (r.result) {
						case 'Y':
							var tContents = r.arr.tContents;
							
							tContents = tContents.replace(/(<([^>]+)>)/ig,"");
							
							if(tContents.lenth > 20) {
								tContents = tContents.substr(0, 20) + '...';
							}
							
							$('#que_' + contentSeq).html(tContents);
							$('#qt_' + contentSeq).val(''); // 번역문 코드 제거
							
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
        
        e.stopPropagation();
        e.preventDefault();
    });
});

function goAnswerList(seq, queSeq) {
	var tSeq = $('#qt_' + seq).val();
	var url = '/answer/answerList?Seq=' + queSeq + '&AnswerSeq=' + seq + '&CurPageName=/answer/questionList&Section1=0&src_Sort=Seq&src_OrderBy=DESC';
	
	if(tSeq == '') {
		location.href = url;
	}
	else {
		location.href = url + '&tSeq=' + tSeq;
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
	if(confirm('getLangStr("jsm_0020")')) {
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
	var FlagOption = '${FlagOption}';
	var src_Sort = '${src_Sort}';
	var src_OrderBy = '${src_OrderBy}';
	var section1 = '${section1}';
	var pg = '${n_curpage}';
	
	///answer/rankAnswer?FlagOption=&trec=&src_Sort=&Section1=&pg=2
	var url = '/answer/rankAnswer?FlagOption=' + FlagOption + '&src_Sort=' + src_Sort + '&src_OrderBy=' + src_OrderBy + '&Section1=' + section1 + '&pg=' + pg + '&targetLang=' + lang;
	
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
				<li><a href="javascript:void(0);" onClick="goConfirmLogin('rankAnswer_frm_sch', '/default/login');"><spring:message code="msg_0175"/></a></li>
				<%}%>
				<li><a href="/question/eventList"><spring:message code="msg_0183"/></a></li>
				<li class="check"><a href="/answer/rankAnswer"><spring:message code="msg_0182"/></a></li>
			</ul>
		</div>
	</div>
	<div id="rank_gnb_btn">
		<div class="center">
			<ul>
				<li>
					<a href="/question/rankQuestion">
						<spring:message code="msg_0365"/>
					</a>
				</li>
				<li class="on">
					<a href="/answer/rankAnswer">
						<spring:message code="msg_0366"/>
					</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="atm_ranka31_con">
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
					<p class="atm_ranka_tabc7" onClick="javascript:location.href='/answer/rankMember?FlagOption=Money&targetLang=${targetLang}';"><spring:message code="msg_0368"/></p>
					<p class="atm_ranka_tabc8" onClick="javascript:location.href='/answer/rankMember?FlagOption=Count&targetLang=${targetLang}';"><spring:message code="msg_0369"/></p>
				</div>
			</div>
			<div class="atm_rank_el_container">
				<c:forEach var="data" items="${answerRankA2}" varStatus="status">
					<div class="atm_rank_el" >
						<c:set var="loop" value="${data.rownum - 1}" />
						<p class="atm_ranka_el_c1"> <span class="atm_ranka31_c6"><fmt:formatNumber type="number" maxFractionDigits="3" value="${data.readAlmoney}" /></span><spring:message code="msg_0136"/></p>
						<c:set var="rankaNo" value="${1 + loop}"/>
						<c:choose>
							<c:when test="${1 + loop <= 1}">
								<h4 class="first">${rankaNo}</h4>
							</c:when>
							<c:when test="${1 + loop == 2 or 1 + loop == 3}">
								<h4 class="other">${rankaNo}</h4>
							</c:when>
							<c:otherwise>
								<h4>${rankaNo}</h4>
							</c:otherwise>
						</c:choose>
						<input type="hidden" id="qt_${data.seq}" name="tSeq" value=""/>
						<c:set var="answerSource" value="${fn:replace(data.answer, '<br>', ' ')}"/>
						<c:set var="answer" value="${fn:substring(answerSource,0,20) }"/>
						<div class="atm_ranka31_eltexts">
							<p class="atm_ranka31_el_c2" onClick="javascript:goAnswerList('${data.seq}', '${data.questionSeq}')">
							<span class="answerTitle_trans" id="que_${data.seq}">${answer}</span>
							</p>
							<p class="atm_ranka31_el_c3">
							  	<span class="atm_ranka31_c2"><img src="/pub/css/images/icon_view.svg" class="atm_viewicon"/>${data.readCount}</span>&nbsp;
								<span class="atm_ranka31_c2"><i class="atm_viewicon"></i>${data.pointCount}</span>&nbsp;
							</p>
						</div>
						<c:if test="${data.lang != sourceLang}">
						<div class="language_icon">
		                   <img class="answer_lang" src="/Common/images/language.svg" alt='<spring:message code="msg_0362"/>' data-seq="${data.seq}">
		                </div>
		                </c:if>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	
	<div id="atm_wrapper">
		<div class="center">
			
			<div id="atm_ranka31_wrapper0">
				<form name="rankAnswer_frm_sch" method="post" onSubmit="return false;">
					<input type="hidden" name="src_Target" value="/answer/rankAnswer">
					<script>
						var para = document.location.href.split("?")[1];
						//console.log('para : ' + para );
						
						if(para != undefined) {
							$("input[name=src_Target]").attr("value", $("input[name=src_Target]").attr("value") + "?" + para);
						}
					</script>
					<input type="hidden" name="pg" value="${n_curpage}">
					<input type="hidden" name="Section1" value="${section1}">
					
					
				</form>
				<%
					JSONObject gl = (JSONObject)CommonUtil.getGlobal(request, response);
					
					String UserLv = "";
					if(gl != null) { 
						UserLv = gl.get("UserLv").toString();
					}
					if(UserLv.equals("99")) {%>
					<script>
					function goPageSrch() {
						var maxPage = '${n_totalpage}'
						var page = $('#srchPage').val();
						var flagOption = '${FlagOption}';
						var src_Sort = '${src_Sort}';
						var trec = '${req_trec}';
						var section1 = '${section1}';
						
						if(page != '') {
							if(parseInt(page, 10) > parseInt(maxPage, 10)) {
								alert('없는 페이지 입니다. 이동할수 있는 최대 페이지는 ' + maxPage + '입니다.');
								$('#srchPage').val(maxPage);
								return false;
							}
							else {
								location.href = '/answer/rankAnswer?FlagOption=' + flagOption + '&trec=' + trec + '&src_Sort=' + src_Sort + '&Section1=' + section1 + '&pg=' + page;
							}
						}
						else {
							alert('이동을 원하시는 페이지의 숫자를 적어주세요');
							return false;
						}
					}
					</script>
					<c:if test="${n_totalpage > 0}">
					<input type="text" id="srchPage"oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"><input type="button" value="이동" onclick="javascript:goPageSrch()" />
					</c:if>
				<%}%>
			</div>
		</div>
	</div>
</div>
<div class="list_pasing">
	${paging_Tag}
</div>
<div id="top_btn">
	<a href="javascript:void(0);"> <span> <img
			src="/Common/images/top_btn_arrow.svg" alt="top_btn">
	</span>
	</a>
</div>
<script type="text/javascript" src="/Common/src/answer/rankAnswer/rankAnswer.js?1.1" ></script>
</body>