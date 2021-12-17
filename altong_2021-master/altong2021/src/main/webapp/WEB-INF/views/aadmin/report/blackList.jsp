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
    table tbody tr {display: none;}
    table input[type="checkbox"] {margin:0;background:none;border:none;vertical-align: middle;display:inline-block;height: 30px;width:20px;}
    table label {margin:0;width:100%;height:100%;vertical-align: middle;}
    table .sirenSelect {padding-left:0; padding-right:0;}
    table .sirenSelect input[type="button"] {display: block;width:100%}
    table .sirenSelect input[type="button"]:nth-child(2) {margin:10px 0;}
    table .tr_clone {display: none;}
    table .sirenResult {display:none;}
    table .sirenResult span {line-height: 84px;color:#fff;}
    table .sirenResult input[type="button"] {display: block; text-align: center; width:100%; margin-top:10px; padding:3px 0;}
    
    .scroll_thead {display: none;position: fixed; top:0;}

    .table-hover>tbody>tr:hover:not(.success_tr):not(.add_tr) {background-color:#fff9e1;}

    table tr.success_tr,
    table tr.success_tr + .add_tr {color:#aaa; background:#f7f7f7;}
    table tr.success_tr a,
    table tr.success_tr + .add_tr a {color:#aaa;}

</style>

    <div id="wrapper">
        <div id="M_wrapper">
            <%@ include file="/Common/include/menuAdmin.jsp" %>
        </div>
        <div class="container-fluid border">
            <div class="title-wrapper row">
                <div class="title-name col-md-6">
                    블랙리스트
                </div>
                <div class="site-hitory col-md-6">
                    <img src="/Common/images/icon_sitehistory_home.png">
                    <span onclick="location.href='/';" style="cursor:pointer;">Home</span> &gt;
                    <span onclick="location.href='/aadmin/configInput';" style="cursor:pointer;">관리자 화면</span> &gt; 신고 현황
                </div>
            </div>
            <!--회원 조회 조건 설정 영역-->
           
            <!-- 리스트 테이블 -->
            <section id="result-wrapper" style="margin-top: 50px;">
                <input type="button" class="btn btn-default" value="새로고침" onclick="fReload();">
                <%if(CommonUtil.libhasAuthority(reportList, userSeq)) {%>
                <a class="btn pull-right btn-warning" style="color:#fff;text-decoration: none;" href="/aadmin/report/reportList">신고 현황 보기</a>
                <% } %>
				<div id="idSiren" class="table-responsive">
					<br><br>[피신고자]
					<table class="table table-bordered table-hover">
						<thead>
							<tr>
								<th width="">회원번호</th>
								<th width="">닉네임</th>
								<th width="">피신고물 (개)</th>
								<th width="">위험 (개)</th>
								<th width="">위험비율 (%)</th>
								<th width="">경고 (개)</th>
								<th width="">경고비율 (%)</th>
								<th width="">무고</th>
								<th width="">무고비율 (%)</th>
								<th width="">피신고 (회)</th>
								<th width="">신고자 (명)</th>
								<th width="">벌점 (삭제된벌점)</th>
								<th width="">게시물</th>
							</tr>
						</thead>
						<tbody>
							<tr class="tr_clone list">
								<td class="tgMemberSeq"></td>
								<td class="tgNickname"></td>
								<td class="tgSirenN"></td>
								<td class="tgDanger"></td>
								<td class="tgDangerR"></td>
								<td class="tgWarning"></td>
								<td class="tgWarningR"></td>
								<td class="tgReject"></td>
								<td class="tgRejectR"></td>
								<td class="tgReportN"></td>
								<td class="tgReporterN"></td>
								<td class="tgMPoint"></td>
								<td class="tgBoard"></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div id="idReporter" class="table-responsive">
					<br><br>[신고자]
					<table class="table table-bordered table-hover">
						<thead>
							<tr>
								<th width="">회원번호</th>
								<th width="">닉네임</th>
								<th width="">신고게시물 (개)</th>
								<th width="">위험 (개)</th>
								<th width="">위험비율 (%)</th>
								<th width="">경고 (개)</th>
								<th width="">경고비율 (%)</th>
								<th width="">무고</th>
								<th width="">무고비율 (%)</th>
								<th width="">피신고게시물 (개)</th>
								<th width="">피신고 (회)</th>
								<th width="">벌점 (삭제된벌점)</th>
								<th width="">게시물</th>
							</tr>
						</thead>
						<tbody>
							<tr class="tr_clone list">
								<td class="tgMemberSeq"></td>
								<td class="tgNickname"></td>
								<td class="tgMySirenContsN"></td>
								<td class="tgMyDangerN"></td>
								<td class="tgMyDangerR"></td>
								<td class="tgMyWarningN"></td>
								<td class="tgMyWarningR"></td>
								<td class="tgMyRejectN"></td>
								<td class="tgMyRejectR"></td>
								<td class="tgMeSirenContsN"></td>
								<td class="tgMeSirenN"></td>
								<td class="tgMeMPoint"></td>
								<td class="tgBoard"></td>
							</tr>
						</tbody>
					</table>
				</div>
            </section>

            <!-- <div id="pagination-section" style="text-align: center;">
                <paginate :page-count="$store.pagination.totalPage" :click-handler="$store.movePage" :page-range="10" prev-text="이전" next-text="다음"
                    class="pagination" :page-class="'page-item'"></paginate>
            </div> -->
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

	function fReload() {
		$('table').find('tbody').find('tr').not('.tr_clone').remove();
		fAjax('/aadmin/report/reportListAjax', '', 'ACT=GetBlackList_Siren');
	}

    function fAjax(url, frm, param, obj) {
		if (document.xhr) {
			$('#Tip').text('이전 작업이 진행중입니다.').css('display', 'block');
			setTimeout(function () { $('#Tip').stop().fadeOut(300) }, 3000);
			return;
		}

		document.xhr = $.ajax({
			type: 'post',
			url: url,
			data: (frm ? $('form[name=' + frm + ']').serialize() + '&' : '') + param,
			dataType: 'json',
			crossDomain: true,
			success: function (r) {
				switch (r.result) {
                    case 'GetBlackList_Siren':
					{
                        var listRow;
                        $.each(r.arr, function(index, item){
                            listRow = $('#idSiren').find('.tr_clone.list').clone();
                            listRow.removeAttr('class');
                            
                            listRow.find('.tgMemberSeq').text(r.arr[index].MemberSeq);
                            listRow.find('.tgNickname').text(r.arr[index].NickName);
                            listRow.find('.tgSirenN').text(r.arr[index].SirenN);
                            listRow.find('.tgDanger').text(r.arr[index].DangerN);
                            listRow.find('.tgDangerR').text(Math.round(r.arr[index].DangerN/r.arr[index].SirenN*100) + '%');
                            listRow.find('.tgWarning').text(r.arr[index].WarningN);
                            listRow.find('.tgWarningR').text(Math.round(r.arr[index].WarningN/r.arr[index].SirenN*100) + '%');
                            listRow.find('.tgReject').text(r.arr[index].RejectN);
                            listRow.find('.tgRejectR').text(Math.round(r.arr[index].RejectN/r.arr[index].SirenN*100) + '%');
                            listRow.find('.tgReportN').text(r.arr[index].ReportN);
                            listRow.find('.tgReporterN').text(r.arr[index].ReporterN);
                            listRow.find('.tgMPoint').text((r.arr[index].MPoint - r.arr[index].MPointDel) + ' (' + r.arr[index].MPointDel + ')');
                            listRow.find('.tgBoard').html('&nbsp;');

							$('#idSiren').find('table').append(listRow);
                        })
                        $('#idSiren').find('tbody').find('tr').not('.tr_clone').fadeIn();

						setTimeout(function(){fAjax('/aadmin/report/reportListAjax', '', 'ACT=GetBlackList_Reporter')}, 1);
						break;
					}
                    case 'GetBlackList_Reporter':
					{
                        var listRow;
                        $.each(r.arr, function(index, item){
                            listRow = $('#idReporter').find('.tr_clone.list').clone();
                            listRow.removeAttr('class');
                            
                            listRow.find('.tgMemberSeq').text(r.arr[index].MemberSeq);
                            listRow.find('.tgNickname').text(r.arr[index].NickName);
                            listRow.find('.tgMySirenContsN').text(r.arr[index].MySirenContsN);
                            listRow.find('.tgMyDangerN').text(r.arr[index].MyDangerN);
                            listRow.find('.tgMyDangerR').text(Math.round(r.arr[index].MyDangerN/r.arr[index].MySirenContsN*100) + '%');
                            listRow.find('.tgMyWarningN').text(r.arr[index].MyWarningN);
                            listRow.find('.tgMyWarningR').text(Math.round(r.arr[index].MyWarningN/r.arr[index].MySirenContsN*100) + '%');
                            listRow.find('.tgMyRejectN').text(r.arr[index].MyRejectN);
                            listRow.find('.tgMyRejectR').text(Math.round(r.arr[index].MyRejectN/r.arr[index].MySirenContsN*100) + '%');
                            listRow.find('.tgMeSirenContsN').text(r.arr[index].MeSirenContsN);
                            listRow.find('.tgMeSirenN').text(r.arr[index].MeSirenN);
                            listRow.find('.tgMeMPoint').text((r.arr[index].MeMPoint - r.arr[index].MeMPointDel) + ' (' + r.arr[index].MeMPointDel + ')');
                            listRow.find('.tgBoard').html('&nbsp;');

							$('#idReporter').find('table').append(listRow);
                        })
                        $('#idReporter').find('tbody').find('tr').not('.tr_clone').fadeIn();

						//setTimeout(function(){fAjax('ReportList.alt', '', 'ACT=GetBlackList_Reporter')}, 1);
						break;
					}
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
		fAjax('/aadmin/report/reportListAjax', '', 'ACT=GetBlackList_Siren');
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
</html>