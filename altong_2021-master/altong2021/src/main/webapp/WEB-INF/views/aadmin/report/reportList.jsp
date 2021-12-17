<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/Common/include/headerAdmin.jsp" %>
<body>
<script src="https://unpkg.com/vuejs-paginate@0.9.0"></script>
<script src="/Common/aadmin/report/dist/store.js"></script>
<script src="/Common/aadmin/report/dist/bus.js"></script>
<style>
    .border {
        border: 1px solid #bab9b9;
    }

    label {
        font-weight: normal;
    }

    div.container-fluid {
        padding: 15px;
        padding-left: 5%;
        padding-right: 5%;
    }

    div.title-wrapper {
        border-bottom: 1px solid #888888;
    }

    div.title-wrapper>.title-name {
        padding-bottom: 5px;
        font-size: 20px;
        font-weight: bold;
        color: #111111;
    }

    div.title-wrapper>.site-hitory {
        text-align: right;
        font-size: 13px;
        color: #888888;
    }

    span.condition-label {
        display: inline-block;
        width: 35%;
    }

    .filter-value {
        display: inline-block;
        padding: 0px 8px;
        width: 30%;
    }

    .filter-flag {
        display: inline-block;
        padding: 0;
        width: 20%;
    }

    span.unit {
        width: 10%;
        display: inline-block;
    }

    .search-condition-set>.row>* {
        margin-top: 5px;
    }

    .filter-wrapper {
        margin-top: 30px;
        border: 3px solid #bab9b9;
        padding: 15px;
    }

    .filter-label {
        margin-right: 10px;
    }

    .filter-label>select,
    .filter-label>input {
        display: inline-block;
        width: auto;
    }

    .search-btn-wrapper {
        height: 115px;
        line-height: 115px;
    }

    .search-btn-wrapper>button {
        height: 50px;
        margin: 5px;
    }

    .available-level-up {
        background-color: #f0ad4e;
        color: white;
    }

    .available-level-up:hover {
        color: black;
    }

    .available-level-up>* {
        font-weight: bold !important;
    }
    table {border-radius: 5px; overflow: hidden;}
    table tr th,
    table tr td {text-align: center; vertical-align: middle!important;font-size: 15px;word-break: break-all;}
    table thead tr th {white-space: nowrap;background:#55565d; color:#fff;}
    table thead .head_rowspan th {font-size: 14px;background:#37373a}
    /* table tbody tr {display: none;} */
    table input[type="checkbox"] {margin:0;background:none;border:none;vertical-align: middle;display:inline-block;height: 30px;width:20px;}
    table label {margin:0;width:100%;height:100%;vertical-align: middle;}
    table tbody tr .targetMinusScore,
    table tbody tr .targetMinusPoint > span {font-weight: bold;}
    table tbody tr.not(.success_tbody) .targetMinusScore,
    table tbody tr.not(.success_tbody) .targetMinusPoint > span {color:red}
    table .sirenSelect {padding-left:0; padding-right:0;}
    table .sirenSelect input[type="button"] {display: block;width:100%;margin:10px 0;}
    table .tbody_clone {display: none;}
    table .sirenResult {/*display:none;*/}
    table .sirenResult span {line-height: 84px;color:#fff;}
    table .sirenResult input[type="button"] {display: block; text-align: center; width:100%; margin-top:10px; padding:3px 0;}
    
    .scroll_thead {display: none;position: fixed; top:0;}

    table tbody:hover:not(.success_tbody) tr {background-color:#fff9e1;}
    table tbody.success_tbody tr,
    table tbody.success_tbody tr + .add_tr {color:#aaa; background:#f7f7f7;}
    table tbody.success_tbody tr a,
    table tbody.success_tbody tr + .add_tr a {color:#aaa;}

    table tbody.success_tbody.delete_tbody tr,
    table tbody.success_tbody.delete_tbody tr + .add_tr {background:#d0ebff;color:#676767}
    table tbody.success_tbody.delete_tbody tr a,
    table tbody.success_tbody.delete_tbody tr + .add_tr a {color:#676767;}

    .black_list_btn {background:#000; color:#fff!important;text-decoration: none!important;}
    .black_list_btn:hover {background:#37373a}
</style>

    <div id="wrapper">
        <div id="M_wrapper">
            <%@ include file="/Common/include/menuAdmin.jsp" %>
        </div>
        <div class="container-fluid border">
            <div class="title-wrapper row">
                <div class="title-name col-md-6">
                    신고 현황
                </div>
                <div class="site-hitory col-md-6">
                    <img src="/Common/images/icon_sitehistory_home.png">
                    <span onclick="location.href='/';" style="cursor:pointer;">Home</span> &gt;
                    <span onclick="location.href='/aadmin/configInput';" style="cursor:pointer;">관리자 화면</span> &gt; 신고 현황
                </div>
            </div>
           
            <!-- 리스트 테이블 -->
            <section id="result-wrapper" style="margin-top: 50px;">
                <input type="button" class="btn btn-default" value="새로고침" onclick="location.reload();"><!-- fReload(); -->
                <%if(CommonUtil.libhasAuthority(blackList, userSeq)) {%>
                <a class="btn pull-right black_list_btn" href="/aadmin/report/blackList">블랙리스트 보기</a>
                <% } %>
                <div class="table-responsive scroll_thead">
                    <table class="table table-bordered table-hover">
                        <thead>
                            <tr>
                                <th colspan="3" style="border-bottom:0;background:#37373a">신고 대상</th>
                                <th colspan="3" style="border-bottom:0;background:#37373a">신고 게시물</th>
                                <th rowspan="2" width="50px" style="background:#37373a">블라인드<br>여부</th>
                                <th rowspan="2" width="70px">신고자</th>
                                <th rowspan="2" width="100px">위반 유형</th>
                                <th rowspan="2" style="min-width: 140px;">신고자 코멘트</th>
                                <th rowspan="2" width="110px">신고<br>일시</th>
                                <th rowspan="2" width="100px">처리</th>
                                <th rowspan="2" width="110px">처리<br>일시</th>
                            </tr>
                            <tr class="head_rowspan">
                                <th width="71px">닉네임</th>
                                <th>벌점 (삭제된 벌점)</th>
                                <th width="82px">제제 차수</th>
                                <th width="68px">번호</th>
                                <th width="71px">유형</th>
                                <th width="648px">내용</th>
                            </tr>
                        </thead>                
                    </table>
                </div>
                <form name="frmReport">
                    <input type="hidden" name="H_ContentsSeq" value="0">
                    <input type="hidden" name="H_Type" value="0">
                    <div class="table-responsive">
                        <table class="table table-bordered" style="margin-top: 15px; border-collapse: collapse;">
                            <thead>
                                <tr>
                                    <th colspan="3" style="border-bottom:0;background:#37373a">신고 대상</th>
                                    <th colspan="3" style="border-bottom:0;background:#37373a">신고 게시물</th>
                                    <th rowspan="2" width="50px" style="background:#37373a">블라인드<br>여부</th>
                                    <th rowspan="2" width="70px">신고자</th>
                                    <th rowspan="2" width="100px">위반 유형</th>
                                    <th rowspan="2" style="min-width: 140px;">신고자 코멘트</th>
                                    <th rowspan="2" width="110px">신고<br>일시</th>
                                    <th rowspan="2" width="100px">처리</th>
                                    <th rowspan="2" width="110px">처리<br>일시</th>
                                </tr>
                                <tr class="head_rowspan">
                                    <th width="70px">닉네임</th>
                                    <th>벌점 (삭제된 벌점)</th>
                                    <th width="80px">제제 차수</th>
                                    <th width="70px">번호</th>
                                    <th width="60px">유형</th>
                                    <th style="min-width: 140px;">내용</th>
                                </tr>
                            </thead>
                            <tbody class="tbody_clone reporter">
                                <tr>
                                    <td class="reportNickname notSpan"></td>
                                    <td class="reportType notSpan"></td>
                                    <td class="reportComment notSpan"></td>
                                    <td class="reportDate notSpan"></td>
                                </tr>
                            </tbody>
                           <c:forEach var="rep" items="${list}">
                           	<c:set var="MPointSum" value="${(rep.MPoint - rep.MPointDel) } (${rep.MPointDel})"/>
                           	<fmt:parseNumber var="MPoint" integerOnly="true" value="${rep.MPoint}" />
                           	<c:set var="scoreChk" value=""/>
                           	<c:choose>
                           		<c:when test="${MPoint >= 200}"><c:set var="scoreChk" value="5차"/></c:when>
                           		<c:when test="${MPoint >= 150}"><c:set var="scoreChk" value="4차"/></c:when>
                           		<c:when test="${MPoint >= 100}"><c:set var="scoreChk" value="3차"/></c:when>
                           		<c:when test="${MPoint >= 50}"><c:set var="scoreChk" value="2차"/></c:when>
                           		<c:when test="${MPoint >= 25}"><c:set var="scoreChk" value="1차"/></c:when>
                           		<c:otherwise><c:set var="scoreChk" value="-"/></c:otherwise>
                           	</c:choose>
                           	
                           	<c:set var="targetSeq" value="Seq=${rep.ContentsSeq}"/>
                           	<c:if test="${rep.Type != 'Q'}">
                           	<c:set var="targetSeq" value="${rep.Type}Seq=${rep.ContentsSeq}"/>
                           	</c:if>
                           	
                           	<c:set var="TypeNm" value="기타"/>
                           	<c:choose>
                           		<c:when test="${rep.Type == 'A'}"><c:set var="TypeNm" value="답변"/></c:when>
                           		<c:when test="${rep.Type == 'AR'}"><c:set var="TypeNm" value="답변댓글"/></c:when>
                           		<c:when test="${rep.Type == 'Q'}"><c:set var="TypeNm" value="질문"/></c:when>
                           		<c:when test="${rep.Type == 'QR'}"><c:set var="TypeNm" value="질문댓글"/></c:when>
                           		<c:otherwise></c:otherwise>
                           	</c:choose>
                           	
                           	<c:set var="Contents" value="${rep.Contents}"/>
                           	<c:if test="${fn:length(rep.Contents) > 40}">
                           	<c:set var="Contents" value="${fn:substring(rep.Contents, 0, 40)}..."/>
                           	</c:if>
                           	
                           	<c:set var="reportType" value="기타"/>
                           	<c:choose>
                           		<c:when test="${rep.reporter[0].Reason == '1'}"><c:set var="reportType" value="홍보성"/></c:when>
                           		<c:when test="${rep.reporter[0].Reason == '2'}"><c:set var="reportType" value="유해성"/></c:when>
                           		<c:when test="${rep.reporter[0].Reason == '3'}"><c:set var="reportType" value="장난성"/></c:when>
                           		<c:when test="${rep.reporter[0].Reason == '4'}"><c:set var="reportType" value="중복성"/></c:when>
                           		<c:when test="${rep.reporter[0].Reason == '5'}"><c:set var="reportType" value="비속어/반말"/></c:when>
                           		<c:when test="${rep.reporter[0].Reason == '6'}"><c:set var="reportType" value="비 정보·지식"/></c:when>
                           		<c:when test="${rep.reporter[0].Reason == '7'}"><c:set var="reportType" value="음해/비방"/></c:when>
                           		<c:otherwise></c:otherwise>
                           	</c:choose>
                           	
                           	<c:set var="reporterLen" value="1"/>
                           	<c:if test="${fn:length(rep.reporter) > 1}"><c:set var="reporterLen" value="${fn:length(rep.reporter)}"/></c:if>
                           	
                            <tbody class="list<c:if test="${rep.Result == '1' or rep.Result == '2' or rep.Result == '3'}"> success_tbody</c:if>"><!-- tbody_clone -->
                                <tr>
                                    <td class="targetNickname" <c:if test="${reporterLen > 1}">rowspan="${reporterLen}"</c:if>>
                                    <a href="/aadmin/memberView?Seq=${rep.MemberSeq}" target="_blank">${rep.NickName}</a>
                                    </td>
                                    <td class="targetMinusPoint" <c:if test="${reporterLen > 1}">rowspan="${reporterLen}"</c:if>>
                                    <c:choose>
                                    	<c:when test="${MPoint < 25}">${MPointSum}</c:when>
                                    	<c:otherwise><span>${MPointSum}</span></c:otherwise>
                                    </c:choose>
                                    </td>
                                    <td class="targetMinusScore" <c:if test="${reporterLen > 1}">rowspan="${reporterLen}"</c:if>>
                                    ${scoreChk}
                                    </td>
                                    <td class="targetSeq" <c:if test="${reporterLen > 1}">rowspan="${reporterLen}"</c:if>>
                                    <a href="/answer/answerList?${targetSeq}" target="_blank">${rep.ContentsSeq}</a>
                                    </td>
                                    <td class="targetType" val="${rep.Type}" style="white-space: nowrap" <c:if test="${reporterLen > 1}">rowspan="${reporterLen}"</c:if>>${TypeNm}</td>
                                    <td class="targetContent" <c:if test="${reporterLen > 1}">rowspan="${reporterLen}"</c:if>><span><c:out value="${Contents}" escapeXml="true" /></span></td>
                                    <td class="targetBlind <c:if test="${reporterLen >= 3}">reject_blind</c:if>" <c:if test="${reporterLen >= 3}">style="background: #bfbfbf;" data-blind="true"</c:if> <c:if test="${reporterLen > 1}">rowspan="${reporterLen}"</c:if>></td>
                                    <td class="reportNickname notSpan">
                                    <a href="/aadmin/memberView?Seq=${rep.reporter[0].MemberSeq}" target="_blank">${rep.reporter[0].NickName}</a>
                                    </td>
                                    <td class="reportType notSpan">${reportType}</td>
                                    <td class="reportComment notSpan">${rep.reporter[0].Reason_txt}</td>
                                    <td class="reportDate notSpan">${rep.reporter[0].conDate}</td>
                                    
                                    <c:if test="${rep.Result == 0 or rep.Result == ''}">
                                    <td class="sirenSelect" <c:if test="${reporterLen > 1}">rowspan="${reporterLen}"</c:if>>
                                        <input type="button" class="btn btn-danger" value="위험" onclick="fsubmitReport(this);">
                                        <input type="button" class="btn btn-warning" value="경고" onclick="fsubmitReport(this);">
                                        <input type="button" class="btn btn-success" value="무고" onclick="fsubmitReport(this);">
                                        <input type="button" class="btn btn-primary" value="삭제" onclick="fsubmitReport(this);">
                                    </td>
                                    </c:if>
                                    
                                    <c:if test="${rep.Result == 1 || rep.Result == 2 || rep.Result == 3}">
                                    <c:set var="sirenResBack" value="" />
                                    <c:choose>
                                    	<c:when test="${rep.Result == 1}"><c:set var="sirenResBack" value="style='background-color:#d9534f'" /></c:when>
                                    	<c:when test="${rep.Result == 2}"><c:set var="sirenResBack" value="style='background-color:#f0ad4e'" /></c:when>
                                    	<c:when test="${rep.Result == 3}"><c:set var="sirenResBack" value="style='background-color:#5cb85c'" /></c:when>
                                    </c:choose>
                                    <td class="sirenResult" <c:if test="${reporterLen > 1}">rowspan="${reporterLen}"</c:if> ${sirenResBack}>
                                        <span>
                                        <c:choose>
                                        	<c:when test="${rep.Result == 1}">위험</c:when>
                                        	<c:when test="${rep.Result == 2}">경고</c:when>
                                        	<c:when test="${rep.Result == 3}">무고</c:when>
                                        </c:choose>
                                        </span>
                                        <input type="button" class="btn btn-default" value="취소" onclick="fsubmitReport(this);">
                                    </td>
                                    </c:if>
                                    <td class="sirenDate" <c:if test="${reporterLen > 1}">rowspan="${reporterLen}"</c:if>>${rep.conDate}</td>
                                </tr>
                                <c:if test="${reporterLen > 1}">
                                <c:forEach var="re" items="${rep.reporter}" varStatus="status">
                                <c:if test="${status.index > 0}">
                                
                                <c:set var="reportType2" value="기타"/>
	                           	<c:choose>
	                           		<c:when test="${re.Reason == '1'}"><c:set var="reportType2" value="홍보성"/></c:when>
	                           		<c:when test="${re.Reason == '2'}"><c:set var="reportType2" value="유해성"/></c:when>
	                           		<c:when test="${re.Reason == '3'}"><c:set var="reportType2" value="장난성"/></c:when>
	                           		<c:when test="${re.Reason == '4'}"><c:set var="reportType2" value="중복성"/></c:when>
	                           		<c:when test="${re.Reason == '5'}"><c:set var="reportType2" value="비속어/반말"/></c:when>
	                           		<c:when test="${re.Reason == '6'}"><c:set var="reportType2" value="비 정보·지식"/></c:when>
	                           		<c:when test="${re.Reason == '7'}"><c:set var="reportType2" value="음해/비방"/></c:when>
	                           		<c:otherwise></c:otherwise>
	                           	</c:choose>
                                
                                <tr class="add_tr">
                                	<td class="reportNickname">
                                	<a href="/aadmin/memberView?Seq=${re.MemberSeq}" target="_blank">${re.NickName}</a>
                                	</td>
                                	<td class="reportType">${reportType2}</td>
                                	<td class="reportComment">${re.Reason_txt}</td>
                                	<td class="reportDate">${re.conDate}</td>
                                </tr>
                                </c:if>
                                </c:forEach>
                                </c:if>
                                
                            </tbody>
                            </c:forEach>
                            
                        </table>
                    </div>
                </form>
            </section>
            
            <p class="paging">
			<span class="num">
			<!-- paging -->
			${paging}
			<!-- paging// -->
			</span>
        </div>
    </div>


<script src="/Common/aadmin/report/dist/resultVue.js"></script>
<script src="/Common/aadmin/report/dist/searchFilter.js"></script>
<script>
    var $store = Vue.prototype.$store;
    loadProgressBar();
    Vue.component('paginate', VuejsPaginate);
    new Vue({
        el: '#pagination-section'
    });

    function fsubmitReport(target) {
        frmReport.H_ContentsSeq.value = $(target).parents('tr').find('.targetSeq').text();
        frmReport.H_Type.value = $(target).parents('tr').find('.targetType').attr('val');

        switch (target.value) {
            case '위험' :
                fAjax('/aadmin/report/reportListAjax', 'frmReport', 'ACT=SetDanger', target);
                break;
            case '경고' :
                fAjax('/aadmin/report/reportListAjax', 'frmReport', 'ACT=SetWarning', target);
                break;
            case '무고' :
                fAjax('/aadmin/report/reportListAjax', 'frmReport', 'ACT=SetReject', target);
                break;
            case '삭제' :
                if (confirm('삭제 시 복구가 불가능합니다. 삭제하시겠습니까?')) {
                    fAjax('/aadmin/report/reportListAjax', 'frmReport', 'ACT=SetDelete', target);
                } else {
                    return false;
                }                
                break;
            case '취소' :
                fAjax('/aadmin/report/reportListAjax', 'frmReport', 'ACT=Cancel', target);
                break;
        }
    }

    function fresultReport(target, date) {
        if (date || target.value == '삭제') {
            var color;
            switch (target.value) {
                case '위험' :
                    color = "#d9534f";
                    break;
                case '경고' :
                    color = "#f0ad4e";
                    break;
                case '무고' :
                    color = "#5cb85c";
                    break;
                case '삭제' :
                    color = "#337ab7";
                    break;
            }

            $(target).parents('.sirenSelect').hide();
            $(target).parents('.sirenSelect').siblings('.sirenResult').show().css('background-color', color).find('span').text(target.value);                
            $(target).parents('tbody').addClass('success_tbody');
            
            if (target.value == '삭제') {
                $(target).parents('tbody').addClass('delete_tbody');
                $(target).parents('.sirenSelect').siblings('.sirenResult').find('input[type=button]').remove();
                alert('새로 고침을 하면 삭제 신고 건이 보이지 않습니다.\n업무 착오 방지를 위해 현재 화면 스크린샷을 권해드립니다.');
            } else {
                $(target).parents('.sirenSelect').siblings('.sirenDate').text(date);
            }

        } else {
            $(target).parents('.sirenResult').hide().css('background-color','#fff');
            $(target).parents('.sirenResult').siblings('.sirenSelect').show();
            $(target).parents('.sirenResult').siblings('.sirenDate').text('');
            $(target).parents('tbody').removeClass('success_tbody');
        }
    }

    function fReload() {
        $('table').find('tbody').not('.tbody_clone').remove();
        fAjax('/aadmin/report/reportListAjax', '', 'ACT=GetList');
    }

    function fMScoreCheck(mpoint) {
        if (mpoint >= 200) return "5차";
        else if (mpoint >= 150) return "4차";
        else if (mpoint >= 100) return "3차";
        else if (mpoint >= 50) return "2차";
        else if (mpoint >= 25) return "1차";
        else return "-";
    }

    function fAjax(url, frm, param, obj) {
		if (document.xhr) {
			$('#Tip').text('이전 작업이 진행중입니다.').css('display', 'block');
			setTimeout(function () { $('#Tip').stop().fadeOut(300) }, 3000);
			return;
		}
		
        var targetType = {A:"답변", AR:"답변댓글", Q:"질문", QR:"질문댓글"};
        var reportType = {1:"홍보성", 2:"유해성", 3:"장난성", 4:"중복성", 5:"비속어/반말", 6:"비 정보·지식", 7:"음해/비방", 99:"기타"};

		document.xhr = $.ajax({
			type: 'post',
			url: url,
			data: (frm ? $('form[name=' + frm + ']').serialize() + '&' : '') + param,
			dataType: 'json',
			crossDomain: true,
			success: function (r) {
				switch (r.result) {
                    case 'GetList':
                        var bodyClone;
                        var ReporterRow;
                        $.each(r.arr, function(index, item){
                            bodyClone = $('form[name="frmReport"]').find('.tbody_clone.list').clone();
                            bodyClone.removeClass('tbody_clone');
                            
                            bodyClone.find('.targetNickname').html('<a href="/aadmin/memberView?Seq='+ r.arr[index].MemberSeq +'" target="_blank">'+ r.arr[index].NickName +'</a>');
                            var MPointSum = (r.arr[index].MPoint - r.arr[index].MPointDel) + ' (' + r.arr[index].MPointDel + ')';
                            bodyClone.find('.targetMinusPoint').html(parseInt(r.arr[index].MPoint) < 25 ? MPointSum : "<span>"+MPointSum+"</span>");
                            bodyClone.find('.targetMinusScore').text(fMScoreCheck(parseInt(r.arr[index].MPoint)));

                            var targetSeq = (r.arr[index].Type=='Q' ? '':r.arr[index].Type) + 'Seq=' + r.arr[index].ContentsSeq;
                            bodyClone.find('.targetSeq').html('<a href="/answer/answerList?'+ targetSeq +'" target="_blank">'+ r.arr[index].ContentsSeq +'</a>');
                            bodyClone.find('.targetType').attr('val', r.arr[index].Type).text(targetType[r.arr[index].Type]);
                            bodyClone.find('.targetContent').text(r.arr[index].Contents);
                            if (bodyClone.find('.targetContent').text().length > 40) {
                                bodyClone.find('.targetContent').text(bodyClone.find('.targetContent').text().substr(0,40)+'...');
                            }

                            switch (r.arr[index].Result) {
                                case 1 :
                                    bodyClone.find('.sirenSelect').hide();
                                    bodyClone.find('.sirenResult').show().css('background-color', '#d9534f').find('span').text('위험');
                                    bodyClone.find('.sirenDate').text(r.arr[index].conDate);
                                    bodyClone.addClass('success_tbody');
                                    break;
                                case 2 :
                                    bodyClone.find('.sirenSelect').hide();
                                    bodyClone.find('.sirenResult').show().css('background-color', '#f0ad4e').find('span').text('경고');
                                    bodyClone.find('.sirenDate').text(r.arr[index].conDate);
                                    bodyClone.addClass('success_tbody');
                                    break;
                                case 3 :
                                    bodyClone.find('.sirenSelect').hide();
                                    bodyClone.find('.sirenResult').show().css('background-color', '#5cb85c').find('span').text('무고');
                                    bodyClone.find('.sirenDate').text(r.arr[index].conDate);
                                    bodyClone.find('.targetBlind').addClass('reject_blind');
                                    bodyClone.addClass('success_tbody');
                                    break;
                            }

                            bodyClone.find('.reportNickname').html('<a href="/aadmin/memberView?Seq='+ r.arr[index].reporter[0].MemberSeq +'" target="_blank">'+ r.arr[index].reporter[0].NickName +'</a>');
                            bodyClone.find('.reportType').text(reportType[r.arr[index].reporter[0].Reason]);
                            bodyClone.find('.reportComment').text(r.arr[index].reporter[0].Reason_txt);
                            bodyClone.find('.reportDate').text(r.arr[index].reporter[0].conDate);

                            if (r.arr[index].reporter.length > 1) {
                                bodyClone.find('td').not('.notSpan').attr('rowspan', r.arr[index].reporter.length);

                                for (var i = 1; i < r.arr[index].reporter.length; i++) {
                                    ReporterRow = $('form[name="frmReport"]').find('.tbody_clone.reporter').find('tr').clone();

                                    ReporterRow.find('.reportNickname').html('<a href="/aadmin/memberView?Seq='+ r.arr[index].reporter[i].MemberSeq +'" target="_blank">'+ r.arr[index].reporter[i].NickName +'</a>');
                                    ReporterRow.find('.reportType').text(reportType[r.arr[index].reporter[i].Reason]);
                                    ReporterRow.find('.reportComment').text(r.arr[index].reporter[i].Reason_txt);
                                    ReporterRow.find('.reportDate').text(r.arr[index].reporter[i].conDate);
                                    ReporterRow.addClass('add_tr');

                                    bodyClone.append(ReporterRow);
                                }

                                if (bodyClone.find('tr').length >= 3){
                                    bodyClone.find('.targetBlind').attr("data-blind", "true");
                                    bodyClone.find('.targetBlind').not('.reject_blind').css('background','#bfbfbf');
                                }
                            }
                            
                            $('form[name="frmReport"]').find('table').append(bodyClone);
                        })
                        $('form[name="frmReport"]').find('tbody').not('.tbody_clone').fadeIn();
                        break;
                    case 'Cancel':
                        fresultReport(obj);
                        $(obj).parents('tr').find('.targetMinusPoint').text(r.arr.MPoint);
                        $(obj).parents('tr').find('.targetMinusPoint').html(parseInt(r.arr.MPoint) < 25 ? r.arr.MPoint : "<span>"+r.arr.MPoint+"</span>")
                        $(obj).parents('tr').find('.targetMinusScore').text(fMScoreCheck(parseInt(r.arr.MPoint)));
                        if ($(obj).parents('tr').find('.targetBlind').data("blind") == true) {
                            $(obj).parents('tr').find('.targetBlind').css('background','#bfbfbf');
                        }
						break;
                    case 'SetDanger':
                    case 'SetWarning':
                    case 'SetReject':
                        fresultReport(obj, r.arr.conDate);
                        $(obj).parents('tr').find('.targetMinusPoint').text(r.arr.MPoint);
                        $(obj).parents('tr').find('.targetMinusPoint').html(parseInt(r.arr.MPoint) < 25 ? r.arr.MPoint : "<span>"+r.arr.MPoint+"</span>")
                        $(obj).parents('tr').find('.targetMinusScore').text(fMScoreCheck(parseInt(r.arr.MPoint)));
                        break;
                    case 'SetDelete':
                        fresultReport(obj);
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
				document.xhr = false;
			}
		});
	}

    $(function(){
        //fAjax('/aadmin/report/reportListAjax', '', 'ACT=GetList');
    });

    $(window).scroll(function(){
        if ($(this).scrollTop() >= 333) {
            $('.scroll_thead').fadeIn('fast');
        } else {
            $('.scroll_thead').fadeOut('fast');
        }
    })
</script>
</body>