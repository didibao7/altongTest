<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.lang.Math" %>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<head>
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=2.5">
<link rel="stylesheet" href="/pub/css/languages_common.css?ver=1.3">
<script type="text/javascript" src="/Common/js_new/default/languages.js?ver=1.6"></script>
</head>
<body>
<%
	JSONObject global_info = (JSONObject) CommonUtil.getGlobal(request, response);

	int pg = 1;
	if(request.getParameter("pg") != null) {
		pg = Integer.parseInt( String.valueOf( request.getParameter("pg") ) );
	}
	
	String[] Level = new String[100];
	Level[0] = CommonUtil.getLangMsg(request, "msg_0136");
	Level[1] = CommonUtil.getLangMsg(request, "msg_0137");
	Level[2] = CommonUtil.getLangMsg(request, "msg_0138");
	Level[3] = CommonUtil.getLangMsg(request, "msg_0139");
	Level[4] = CommonUtil.getLangMsg(request, "msg_0140");
	Level[5] = CommonUtil.getLangMsg(request, "msg_0141");
	Level[6] = CommonUtil.getLangMsg(request, "msg_0142");
	Level[7] = CommonUtil.getLangMsg(request, "msg_0143");
	Level[8] = CommonUtil.getLangMsg(request, "msg_0144");
	Level[9] = CommonUtil.getLangMsg(request, "msg_0145");
	Level[10] = CommonUtil.getLangMsg(request, "msg_0146");
	Level[11] = CommonUtil.getLangMsg(request, "msg_0147");
	Level[98] = CommonUtil.getLangMsg(request, "msg_0148");
	Level[99] = CommonUtil.getLangMsg(request, "msg_0149");
	
	pageContext.setAttribute("Level", Level);
%>
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
	var url = '/answer/answerList?Seq=' + seq;
	
	if(qtSeq == '') {
		location.href = url;
	}
	else {
		location.href = url + '&qtSeq=' + qtSeq;
	}
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

$(function(){
	/* 답변 번역 아이콘 클릭 */
    $('.answer_lang').click(function(e){
        $(this).attr('src', function(index, attr){
            if (attr.match('_on')) {
                //return attr.replace('language_on.svg', 'language.svg');
            } else {
                return attr.replace('language.svg', 'language_on.svg');
            }
        });
        e.stopPropagation();
        e.preventDefault();
    });
});

function goLangSearch(lang) {
	var pg = '${pg}';
	var url = '/question/bestList?src_Sort=&pg=' + pg + '&targetLang=' + lang;
	
	location.href = url;
}

function goLangChange() {
	var lang = $('#langSel option:selected').val();
	
	goLangSearch(lang)
}
</script>
<%@ include file="/pub/menu/topMenu.jsp"%>
<link rel="stylesheet" href="/pub/question/bestList/bestList.css?ver=1.2">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.1">


<div id="wrapper">
	<%@ include file="/Common/include/EventBanner.jsp"%>
	<div id="atm_ranka">
		<div class="center">
			<ul>
				<li class="check"><a href="/question/bestList"><spring:message code="msg_0358"/></a></li>
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
				<li><a href="/answer/rankAnswer"><spring:message code="msg_0182"/></a></li>
			</ul>
		</div>
	</div>
	<form name="frm_sch" method="post" onSubmit="return false;">
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
                </div>
            </div>
        </div>
		<div id="section_list" >
	 		<div class="center">
	 		<!-- 정적 리스트 작성 -->
			<c:forEach var="item" items="${questionBestList}" varStatus="status">
				<section class="atm_rank_el" onclick="javascript:goAnswerList('${item.seq}')">
					<a href="javascript:void(0);" class="section_list_el">
						<div class="list_profile">
							<figure class="best_list">
								<img src="${libIMG_URL}/UploadFile/Profile/${item.photo}">
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
									<li id="QusD_${item.seq}">
										${item.conDate}
									</li>
									<li>
										<ul>
											<li><img src="/pub/css/images/icon_view_q.svg" alt="viewicon"><fmt:formatNumber value="${item.readCount}" pattern="#,###" /></li>
											<li><span class="${item.flagChoice}"></span><fmt:formatNumber value="${item.answCount}" pattern="#,###" /></li>
											<li><img src="/pub/css/images/icon_reply_q.svg" alt="replyicon"><fmt:formatNumber value="${item.replyCount}" pattern="#,###" /></li>
										</ul>
									</li>
								
								</ul>
								<div class="lang_smile">
									<c:if test="${item.extraAlmoney > 0}"><p class="honhonAl"><img src="/pub/answer/answerList/images/answer_almoney.svg" alt='<%=CommonUtil.getLangMsg(request, "msg_0230")%>'></p></c:if>
								
									<c:if test="${userLv > 0}">	
										<c:choose>
											<c:when test="${item.memberSeq != userSeq}">
												<p class="smile_icon"><img src="/pub/css/images/smile_q.svg" alt='<%=CommonUtil.getLangMsg(request, "msg_0231")%>'><span id="good_${item.seq}"><fmt:formatNumber value="${item.good}" pattern="#,###" /></span></p>
												<p class="sad_icon"><img src="/pub/css/images/sad_q.svg" alt='<%=CommonUtil.getLangMsg(request, "msg_0232")%>'><span id="bad_${item.seq}"><fmt:formatNumber value="${item.bad}" pattern="#,###" /></span></p>
											</c:when>
											<c:otherwise>
												<p class="smile_icon"><img src="/pub/css/images/smile_q.svg" alt='<%=CommonUtil.getLangMsg(request, "msg_0231")%>'><span><fmt:formatNumber value="${item.good}" pattern="#,###" /></span></p>
												<p class="sad_icon"><img src="/pub/css/images/sad_q.svg" alt='<%=CommonUtil.getLangMsg(request, "msg_0232")%>'><span><fmt:formatNumber value="${item.bad}" pattern="#,###" /></span></p>
											</c:otherwise>
										</c:choose>
									</c:if>
									<c:if test="${userLv == 0}">
										<p class="smile_icon"><img src="/Common/images/smile.svg" alt='<%=CommonUtil.getLangMsg(request, "msg_0231")%>'><span><fmt:formatNumber value="${item.good}" pattern="#,###" /></span></p>
										<p class="sad_icon"><img src="/Common/images/sad.svg" alt='<%=CommonUtil.getLangMsg(request, "msg_0232")%>'><span><fmt:formatNumber value="${item.bad}" pattern="#,###" /></span></p>
									</c:if>
								</div>
							</li>
							<li>
								<c:if test="${item.memberSeq != 10000691 and  item.memberSeq != 10003513}">
								<img src="/Common/images/nation/${item.nation}.svg" alt="${item.nation}">
								</c:if>
								<b>${Level[item.lv]}</b>
								<span>${item.nickName}</span>
							</li>
						</ul>
						<c:if test="${item.lang != sourceLang}">
						<div class="language_icon">
							<img class="answer_lang" src="/Common/images/language.svg" alt='<%=CommonUtil.getLangMsg(request, "msg_0362")%>' data-seq="${item.seq}">
						</div>
						</c:if>
						<div class="atm_rankq31_eltexts" style="display:none;">
							<p id="Title" class="atm_rankq31_el_c2">${item.title}</p><br />
							<p style="display:inline-block;color:#159490;font-size:11px;font-weight:bold;letter-spacing:-0.5px;"><span style="font-weight:normal"></span> <span id="NickName">${item.nickName}<%--<c:if test="${item.flagChoice eq 'Y'}"> 님이 답변채택</c:if>--%> </span>
								<span id="Lv" style="border:1px solid #159490;border-radius:20px;padding:0 4px;line-height:13px;display:inline-block;color:#8e8e8e;font-size:10px;font-weight:normal">${Level[item.lv]}</span>
							</p>
							<p class="atm_rankq31_el_c3">
								<img src="${libIMG_URL}/Common/images/icon_view.png" class="atm_viewicon"/><span id="ReadCount" class="atm_rankq31_c2"><fmt:formatNumber value="${item.readCount}" pattern="#,###" /></span>&nbsp;
								<c:choose>
									<c:when test="${item.flagChoice == 'Y'}">
										<img src="${libIMG_URL}/Common/images/icon_answer_micro_Y.png" class="atm_viewicon"/><span id="AnswCount" class="atm_rankq31_c2"><fmt:formatNumber value="${item.answCount}" pattern="#,###" /></span>
									</c:when>
									<c:otherwise>
										<img src="${libIMG_URL}/Common/images/icon_answer_micro_N.png" class="atm_viewicon"/><span id="AnswCount" class="atm_rankq31_c2"><fmt:formatNumber value="${item.answCount}" pattern="#,###" /></span>
									</c:otherwise>
								</c:choose>
							</p>
						</div>
					</a>
				</section>
				<script>formatDateForSpan('QusD_${item.seq}');</script>
			</c:forEach> 
			</div>
			<c:if test="${n_totalpage > 1}">
			<div class="list_pasing">
				${paging}
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
					var src_Sort = '${src_Sort}';
					
					if(page != '') {
						if(parseInt(page, 10) > parseInt(maxPage, 10)) {
							alert('없는 페이지 입니다. 이동할수 있는 최대 페이지는 ' + maxPage + '입니다.');
							$('#srchPage').val(maxPage);
							return false;
						}
						else {
							location.href = '/question/bestList?src_Sort=' + src_Sort + '&pg=' + page;
						}
					}
					else {
						alert('이동을 원하시는 페이지의 숫자를 적어주세요');
						return false;
					}
				}
				</script>
				<c:if test="${n_totalpage > 0}">
					<input type="text" id="srchPage" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"><input type="button" value="이동" onclick="javascript:goPageSrch()" />
				</c:if>
				<%}%>
				<!--wrapper end -->
			</div>
			</c:if>
		</div>
	</form>
</div>
<script>
	var pg = '<%=pg%>';
	
	function best_fAjax() {
		if (document.xhr) return;
		//console.log('fAjax');
		pg++;
		$('#divProg').slideDown(300);
		setTimeout(fMakeRow, 1000);//너무 빨리 응답이 와도 비쥬얼이 좋지 않은 것 같아서 일정 시간 모래시계를 유지시킨다.
	
		document.xhr = $.ajax({
			type: 'get',
			url: '/question/bestListAjax',
			data: 'ACT=JSON&pg=' + pg,
			dataType: 'json',
			crossDomain: true,
			success: function (r) {
				//console.log('r.result : ' + r.result);
				if (r.result == 'SUCCESS' && r.arr)
				{
					document.arrRows = r.arr;
					fMakeRow();
				}
				else
				{
					$('#divProg').slideUp();
					$(window).unbind("scroll");
				}
			},
			error: function (r, textStatus, err) {
				$('#divProg').slideUp();
				$(window).unbind("scroll");
				console.log(r);
			},
			complete: function () {
				//$('#divProg').slideUp();
			}
		});
	}
	
	function fMakeRow()
	{
		if (!document.isShow)
		{
			document.isShow = true;
			return;
		}
		var libIMG_URL = '${libIMG_URL}';
		var Level = new Object();
		<%
		for(int i = 0; i < Level.length; i++) {
			out.println("Level["+i+"] = '"+Level[i]+"';");
			if(i > 11 && i < 98) { continue; }
		}
		%>
		console.log('level[0] : ', Level[0]);
		$('.atm_boardnavi').slideUp(500);
	
		var newObj, target = $('.atm_rank_el:first');
		document.arrRows.forEach(function(v) {
			newObj = target.clone();
			newObj.appendTo(target.parent()).attr('onClick','location=\'/answer/answerList?Seq=' + v.seq+'\'').css('display','none').slideDown();
			newObj.find('#Title').html(v.title);
			//newObj.find('#NickName').text(v.nickName + ((v.flagChoice == 'Y') ? ' 님이 답변 채택' : '') );
			newObj.find('#NickName').text(v.nickName);
			newObj.find('#Lv').text(Level[v.lv]);
			newObj.find('#ReadCount').text(v.readCount);
			newObj.find('#AnswCount').text(v.answCount);
			newObj.find('#Photo').attr('src', libIMG_URL+'/UploadFile/Profile/' + ( (v.photo != null && v.photo != '') ? v.photo : 'img_thum_base0.jpg'));
		});
	
		$('#divProg').slideUp();
		document.isShow = false;
		document.xhr = false;
	}
	
	$(window).scroll(function() {
		var scrollBottom = $(document).height() - $(window).height() - $(window).scrollTop();
		//if(scrollBottom < 150) fAjax();
	});
</script>
<script type="text/javascript" src="/pub/question/bestList/bestList.js?1.2" ></script>
</body>