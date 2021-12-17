<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.lang.Math" %>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<head>
<link rel="stylesheet" href="/pub/answer/rankAnswer/rankAnswer.css?ver=1.7">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.4">

<link rel="stylesheet" href="/pub/css/rankAnswer_lang.css?ver=1.0">
<script src="/Common/js_new/default/languages.js"></script>
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<div id="wrapper">
	<%@ include file="/Common/include/EventBanner.jsp"%>
	<div id="atm_ranka">
		<div class="center">
			<ul>
				<li><a href="/question/bestList"><spring:message code="msg_0358"/></a></li>
				<li><a href="/answer/questionList"><spring:message code="msg_0161"/></a></li>
				<li class="check"><a href="javascript:void(0);"><spring:message code="msg_0175"/></a></li>
				<li><a href="/question/eventList"><spring:message code="msg_0183"/></a></li>
				<li><a href="/answer/rankAnswer"><spring:message code="msg_0182"/></a></li>
			</ul>
		</div>
	</div>
	<%@ include file="/Common/include_new/topAnswer.jsp"%>
<script>
function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

$(function(){
	$('.language_icon img').click(function(e){
		//번역 유무에 따라서
		//번역이 없는 경우 기계 번역 실행후 결과를 가져와서 tSeq 를 가져온다.
		//번역이 있는 경우 사람번역이 있으면 사람번역의 tSeq 를 가져온다. 
        var contentSeq = $(this).attr('data-seq');
		var contentType = "Q";
        //console.log('contentSeq : ' + contentSeq);
        //alert(getLangStr("jsm_0060"));
        
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

function goAnswerList(seq) {
	var qtSeq = $('#qt_' + seq).val();
	var curPageName = '${CurPageName}';
	var section1 = '${Section1}';
	var src_Sort = '${src_Sort}';
	var src_OrderBy = '${src_OrderBy}';
	
	var url = '/answer/answerList?Seq=' + seq + '&amp;CurPageName=' + curPageName + '&Section1=' + section1 + '&src_Sort=' + src_Sort + '&src_OrderBy=' + src_OrderBy;
	
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
	var sectionCode1 = '${SectionCode1}';
	var sectionCode2 = '${SectionCode2}';
	var sectionCode3 = '${SectionCode3}';
	var sectionCode4 = '${SectionCode4}';
	var sectionCode5 = '${SectionCode5}';
	
	var url = '/answer/favoriteList?SectionCode1=' + sectionCode1 + '&SectionCode2=' + sectionCode2 + '&SectionCode3=' + sectionCode3 + '&SectionCode4=' + sectionCode4 + '&SectionCode5=' + sectionCode5 + '&targetLang=' + lang;
	
	location.href = url;
}

function goLangChange() {
	var lang = $('#langSel option:selected').val();
	
	goLangSearch(lang)
}
</script>
	<div class="atm_ranka12_con">
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
					<p class="atm_ranka_tabc6<c:if test="${src_Sort == 'DateReg'}">_on</c:if>"
						onClick="javascript:location.href='/answer/favoriteList?SectionCode1=${Section1}&SectionCode2=${Section2}&SectionCode3=${Section3}&SectionCode4=${Section4}&SectionCode5=${Section5}&src_Sort=DateReg&src_OrderBy=DESC&targetLang=${targetLang}';">
						<spring:message code="msg_0359"/>
					</p>
					<p class="atm_ranka_tabc7<c:if test="${src_Sort == 'AnswerCount'}">_on</c:if>"
						onClick="javascript:location.href='/answer/favoriteList?SectionCode1=${Section1}&SectionCode2=${Section2}&SectionCode3=${Section3}&SectionCode4=${Section5}&SectionCode5=${Section5}&src_Sort=AnswerCount&src_OrderBy=ASC&targetLang=${targetLang}';">
						<spring:message code="msg_0360"/>
					</p>
				</div>
			</div>
			<div class="atm_rank_el_container">
				<c:choose>
					<c:when test="${listCount > 0}">
						<c:forEach var="item" items="${list}" varStatus="status">
							<div class="atm_ranka12_el atm_border">
								<div class="atm_ranka12_c5" onClick="javascript:goAnswerList('${item.seq}');">
									<div class="atm_icon_score1"><span class="atm_icon_score2"><fmt:formatNumber value="${item.almoney}" type="number" /></span></div>
									<span class="answerTitle_trans" id="que_${item.seq}">${item.title}</span>
									<input type="hidden" id="qt_${item.seq}" name="qtSeq" value=""/>
								</div>
								<p class="atm_ranka12_c7">${item.conDate} &nbsp;
									<span class="atm_whitespace"><img src="/Common/images/icon_view.svg" class="atm_viewicon"/><fmt:formatNumber value="${item.readCount}" type="number" /></span>&nbsp;
									<span class="atm_whitespace"><i class="${item.flagChoice}"></i><fmt:formatNumber value="${item.answCount}" type="number" /></span>
								</p>
								<c:if test="${item.lang != sourceLang}">
								<div class="language_icon">
			                        <img class="answer_lang" src="/Common/images/language.svg" alt='<spring:message code="msg_0362"/>' data-seq="${item.seq}">
			                    </div>
			                    </c:if>
							</div>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<p>
							<spring:message code="msg_0361"/>
						</p>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</div>
<div class="list_pasing">
	<%=request.getAttribute("pageStr").toString()%>
</div>
<div id="top_btn">
	<a href="javascript:void(0);"> <span> <img src="/Common/images/top_btn_arrow.svg" alt="top_btn">
	</span>
	</a>
</div>
<script type="text/javascript" src="/pub/answer/rankAnswer/rankAnswer.js" ></script>
</body>