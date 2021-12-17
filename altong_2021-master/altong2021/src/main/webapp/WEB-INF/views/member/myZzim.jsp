<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.altong.web.logic.util.CommonUtil"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp"%>
<%
String msg_0237 = CommonUtil.getLangMsg(request, "msg_0237");

pageContext.setAttribute("msg_0237", msg_0237);
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<!-- 상위 헤드 -->
<link rel="stylesheet" href="/pub/member/myQuestion/myQuestion.css?ver=1.1">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.4">

<link rel="stylesheet" href="/pub/css/myPage_lang.css?ver=1.0">
<script src="/Common/js_new/default/languages.js?ver=1.0"></script>

<script>
$(function(){
	$('.answer_lang').click(function(e){
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
								$('#cont_' + contentSeq).html(tContents);
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
							var tContents = r.arr.tContents;
							
							tTitle = tTitle.replace(/(<([^>]+)>)/ig,"");
							
							$('#que_' + contentSeq).html(tTitle);
							$('#cont_' + contentSeq).html(tContents);
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
	var url = '/answer/answerList?Seq=' + seq + '&CurPageName=/member/myZzim';
	
	if(qtSeq == '') {
		location.href = url;
	}
	else {
		location.href = url + '&qtSeq=' + qtSeq;
	}
}
</script>
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<script>
//전송
function goSubmit_z(FormName, URL, Target) {
    //console.log('before eval : ' + FormName)
    var FormName = eval(FormName);
    //console.log('after eval : ' + FormName)
    FormName.target = Target;
    FormName.action = "" + URL + "";
    FormName.submit();
}
</script>

<div id="atm_myjjim_wrapper0">
<!--wrapper start -->
	<form name="frm_sch" method="post" onSubmit="return false;">
	<input type="hidden" name="src_Target" value="${curPageName}">
	<input type="hidden" name="pg" value="${n_curpage}">
	
	<div class="atm_mycatebar1">
		<div class="center">
			<div class="atm_mycatebar1_pc">
				<p class="atm_mycatebar_c1"><spring:message code="msg_0919"/> <span class="atm_mycatebar_c2">${n_trec}</span><spring:message code="msg_0370"/></p>
				
				<div class="atm_mycatebar_box">
					 <span><spring:message code="msg_0895"/></span>
					<select name="Section1" class="atm_mycatebar_sel" onChange="javascript:goSubmit_z('frm_sch','${curPageName}','_self');">
						<option value="0" <c:if test="${section1 == '0'}">selected</c:if>><spring:message code="msg_0161"/></option>
					<c:forEach var="sec" items="${sectionVO}" varStatus="status">
						<c:set var="code" value="${sec.code}"/>
						<%
							String code = String.valueOf( pageContext.getAttribute("code") );
							String codeNm = CommonUtil.getLangMsg(request, "cate" + code);
						%>
						<option value="${sec.code}" <c:if test="${section1 == sec.code}">selected</c:if>><%=codeNm%></option>
					</c:forEach>
					</select>
				</div><!-- atm_mycatebar_box end -->
			</div><!-- atm_mycatebar1_pc end -->
		</div><!-- center end -->
	</div><!-- atm_mycatebar1 end -->
	
	<div class="atm_myjjim_con">
		<div class="center">
			<script language="javascript">
			  // [추가(2017.12.21): 김현구] 질문 확인 후, 삭제 처리
			  function fnDelete(QuestionSeq) {
			    var ans1 = confirm(getLangStr("jsm_0008") + '\n' + getLangStr("jsm_0102"));
			    if ( ans1 == false ) return;
				
				//document.location.href = "/Member/MyZzimDel.asp?QuestionSeq=" + QuestionSeq;         
			    goSubmit_z('frm_sch', '/member/myZzimDel?QuestionSeq=' + QuestionSeq, 'ifrm');
			  }
			 </script>
			 
		<c:forEach var="item" items="${myZzimList}" varStatus="status">
			<div class="atm_myjjim_el atm_border atm_myjjim_delete">
				<div class="atm_myjjim_c5 atm_myjjim_pro" onClick="javascript:goAnswerList('${item.questionSeq}');">
                    <figure>
						<c:choose>
							<c:when test="${ item.flagNickName ne 'N' and item.photo ne ''}">
								<img src="/UploadFile/Profile/${item.photo}" onError="this.src='/pub/css/profile/img_thum_base0.jpg'" />
							</c:when>
							<c:otherwise>
								<img src="/pub/css/profile/img_thum_base0.jpg" />
							</c:otherwise>
						</c:choose>
						<figcaption>${item.nation}</figcaption>
                    </figure>
					<div>
						<c:set var="item_almoney" value="${item.almoney}" />
						<fmt:parseNumber var="item_almoney_2" type="number" value="${item_almoney}"></fmt:parseNumber>
						<% int usr_color = Integer.parseInt(String.valueOf(pageContext.getAttribute("item_almoney_2"))); %> 
						<p><span class="atm_icon_score<%if (usr_color >= 5000) { %>_red<% } %>"><fmt:formatNumber value="${item.almoney}" pattern="#,###.0" /></span><span id="que_${item.questionSeq}">${item.title}</span></p>
						<ul class="atm_myjjim_c7">
                            <li>${item.dateReg}</li>
                            <li><img src="/Common/images/icon_view.svg" alt="viewicon"><fmt:formatNumber value="${item.readCount}" pattern="#,###" /></li>
                            <li><span>A</span><fmt:formatNumber value="${item.answerCount}" pattern="#,###" /></li>
                        </ul>
                        <a href="#">
                        	<img src="/Common/images/nation/${item.nation}.svg" alt="">
                       		<c:choose>
								<c:when test="${item.flagNickName eq 'N'}">
									<spring:message code="msg_0236"/>
								</c:when>
								<c:otherwise>
                        			${item.nickName}
								</c:otherwise>
							</c:choose>
                        	<c:if test="${item.lv ne 99}">
	                        <b>
								<c:choose>
									<c:when test="${item.nickName eq msg_0237}">
										<spring:message code="msg_0237"/>
									</c:when>
									<c:otherwise>
										<c:if test="${item.flagNickName != 'N' and item.lv == 1}"><spring:message code="msg_0137"/></c:if>
										<c:if test="${item.flagNickName != 'N' and item.lv == 2}"><spring:message code="msg_0138"/></c:if>
										<c:if test="${item.flagNickName != 'N' and item.lv == 3}"><spring:message code="msg_0139"/></c:if>
										<c:if test="${item.flagNickName != 'N' and item.lv == 4}"><spring:message code="msg_0140"/></c:if>
										<c:if test="${item.flagNickName != 'N' and item.lv == 5}"><spring:message code="msg_0141"/></c:if>
										<c:if test="${item.flagNickName != 'N' and item.lv == 6}"><spring:message code="msg_0142"/></c:if>
										<c:if test="${item.flagNickName != 'N' and item.lv == 7}"><spring:message code="msg_0143"/></c:if>
										<c:if test="${item.flagNickName != 'N' and item.lv == 8}"><spring:message code="msg_0144"/></c:if>
										<c:if test="${item.flagNickName != 'N' and item.lv == 9}"><spring:message code="msg_0145"/></c:if>
										<c:if test="${item.flagNickName != 'N' and item.lv == 10}"><spring:message code="msg_0146"/></c:if>
										<c:if test="${item.flagNickName != 'N' and item.lv == 11}"><spring:message code="msg_0147"/></c:if>
										<c:if test="${item.flagNickName == 'N'}"><spring:message code="msg_0236"/></c:if>
									</c:otherwise>
								</c:choose>
	                        </b>
	                        </c:if>
                        </a>
					</div>
				</div>	
				<p class="beefup_head" id="cont_${item.questionSeq}"  onClick="javascript:goAnswerList('${item.questionSeq}');">
				${fn:substring(  fn:trim(item.contents.replaceAll("<.*?>","") ), 0, 280)}
				</p>
                <input type="hidden" id="qt_${item.questionSeq}" name="qtSeq" value=""/>
				<span class="atm_myJJim_c33"><img class="answer_lang" src="/Common/images/language.svg" alt="" data-seq="${item.questionSeq}"></span>
			</div>
		</c:forEach>
	
		</div><!-- center end -->
	</div><!-- atm_myjjim_con end -->
	
	<div class="list_pasing">
	${paging}
	</div>
	</form>

<iframe name="ifrm" width="100%" height="0" frameborder="0"></iframe>

<!--wrapper end -->
</div><!--atm_myjjim_wrapper0 end -->
<div id="top_btn">
    <a href="#">
        <span>
            <img src="/Common/images/top_btn_arrow.svg" alt="top_btn">
        </span>
    </a>
</div>
</body>
</html>