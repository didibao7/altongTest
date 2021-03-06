<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/Common/include/headerAdmin.jsp" %>
<body style="overflow-y:scroll">
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
        /*color: dodgerblue;*/
        font-weight: bold !important;
    }

    #ifrm {
        display: none;
    }

	a:link {
		color:#333;
	} 

	a:visited {
		color:#333;
	}

	a:hover {
		color:#ff0000;
	}
</style>
<style>
	.data_header td {border: 1px solid #aaa !important;background-color:#eee;text-align:center}
	td {vertical-align:middle !important;}
</style>
<script>
var cfgExch = JSON.parse('${cfgExch}');
function fAjax(url, frm, param, obj) {
	if (document.xhr) {
		$('#Tip').text('?????? ????????? ??????????????????.').css('display', 'block');
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

			if (r.arr.msg) alert(r.arr.msg);

			switch (r.result) {
				case 'GetExchStamp':
					document.r = r;
					fMakeRow();
					break;
				case 'GetWeekData':
					if (r.arr.length)
					{
						var baseNode = $('.DataRow[MemberSeq=' + r.arr[0].MemberSeq + ']');
						baseNode.nextAll('.DataRow[MemberSeq=' + r.arr[0].MemberSeq + ']').remove();

						document.isShow = true;//slideDown ???????????? ?????? ?????? ?????? ??????
						$('#samNullRow').insertAfter(baseNode).css('height', (baseNode.height() * r.arr.length) + 'px').slideDown(100, function(){
							var Clone;
							var Lv = baseNode.attr('Lv');

							$.each(r.arr, function(index, item){
								item.regdate = item.regdate.substring(0,10);
								if (item.isExtra == 'Y') item.regdate = 'EXTRA (' + item.regdate.substring(5,5) + ')';
							});

							r.arr.sort(function(a, b) {
								return a.regdate < b.regdate ? -1 : a.regdate > b.regdate ? 1 : 0;
							});

							$.each(r.arr, function(index, item){
								//item.Lv = Lv; ????????? ????????? ?????? ????????? ?????? ?????? ????????? ????????? ?????? ??? ???????????? ????????? ????????? ??? ?????? ?????? ????????? ???????????? ??????.

								Clone = $('<tr class="DataRow" align="center" bgcolor="#f0fff0">' + fMakeWeekDataHTML(item) + '</tr>');
								Clone.insertAfter(baseNode)
									.attr('MemberSeq', item.MemberSeq)
									.attr('XDate', item.regdate);
							});

							document.isShow = false;
							document.xhr = false;
						});

						$('#samNullRow').slideUp(0);
					}
					else alert('???????????? ????????????.');
					break;
				case 'IncreaseStamp':
					{
						//var MemberSeq = $(obj).closest('tr').attr('MemberSeq');
						$('.DataRow[MemberSeq=' + r.arr.MemberSeq + ']:gt(0)').remove();
						r.arr.ExchDate.date = r.arr.ExchDate.substring(0,10);

						$(obj).closest('tr').html(fMakeDataHTML(r.arr))
							.attr('bgcolor', r.arr.isExchOK == 'Y' ? '#ecec93' : '#fff');
					}
					break;
				default:
					if (r.result) alert(r.result);
					break;
			}
		},
		error: function (r, textStatus, err) {
			if (r.responseText && r.responseText.substring(0, 13) == '<!--LOGOFF-->') {top.location = 'http://www.altong.com/'; return;}
			$('#divProg').slideUp();
			$(window).unbind("scroll");
			console.log(r);
		},
		complete: function () {
			if (!document.isShow) document.xhr = false;
		}
	});
}
function fSetCntColor(item, colName, byForce)
{
	var conVal = colName == 'Almoney' ? addComma(item[colName]) : item[colName];
	return (byForce || item[colName] >= cfgExch[item.Lv]['Exch'+colName]) ? conVal : '<span style="color:#ff9999;font-weight:bold">' + conVal + '</span>';
}
function fMakeWeekDataHTML(item)
{
	console.log('rate : ' + (item.preQusChoiceRate && item.preQusChoiceRate >= cfgExch[item.Lv]['ExchQusChoiceRate']));
	var html = $('#samDataWeekRow').html();
	html = html.replace('#Lv#', item.Lv);
	html = html.replace('#StampCnt#', item.ExchStamp);
	html = html.replace('#QusRegCnt#', fSetCntColor(item, 'QusRegCnt'));
	//item.preQusChoiceRate = item.preQusRegCnt ? Math.round(item.preQusChoiceCnt / item.preQusRegCnt * 1000) / 10 : 0; // ???????????? ????????? ????????? ???????????? ??????  ?????????/????????? -> ?????????(???????????? ??????)
	//item.preQusChoiceRate_str = item.preQusChoiceCnt + '/' + item.preQusRegCnt;
	item.preQusChoiceRate_str = '-';
	//html = html.replace('#QusChoiceRate#', fSetCntColor(item, 'preQusChoiceRate_str', (item.preQusChoiceRate && item.preQusChoiceRate >= cfgExch[item.Lv]['ExchQusChoiceRate'])));
	html = html.replace('#QusChoiceRate#', fSetCntColor(item, 'preQusChoiceRate_str', false));
	html = html.replace('#AnsRegCnt#', fSetCntColor(item, 'AnsRegCnt'));
	html = html.replace('#AnsChoicedCnt#', fSetCntColor(item, 'AnsChoicedCnt'));
	html = html.replace('#AnsEstiCnt#', fSetCntColor(item, 'AnsEstiCnt'));
	html = html.replace('#regdate#', item.regdate);

	return html;
}
function fMakeDataHTML(item)
{
	item.Almoney *= 1;
	var html = $('#samDataRow').html();
	html = html.replace(/#MemberSeq#/gi, item.MemberSeq);
	html = html.replace('#Nickname#', item.Nickname);
	html = html.replace('#Name#', item.Name);
	html = html.replace('#Lv#', item.Lv);
	html = html.replace('#Almoney#', fSetCntColor(item, 'Almoney'));
	html = html.replace('#StampCnt#', fSetCntColor(item, 'StampCnt'));
	html = html.replace('#QusRegCnt#', item.QusRegCnt);
	html = html.replace('#QusChoiceRate#', item.QusChoiceCnt + '/' + item.QusRegCnt);
	html = html.replace('#AnsRegCnt#', item.AnsRegCnt);
	html = html.replace('#AnsChoicedCnt#', item.AnsChoicedCnt);
	html = html.replace('#AnsEstiCnt#', item.AnsEstiCnt);
	html = html.replace('#ExchDate#', item.ExchDate);
	html = html.replace('#isOK#', item.isExchOK);

	return html;
}



//????????????
function fScrolling()
{
	if (!document.xhr) {
		$('#divProg').slideDown(300);
		setTimeout(fMakeRow, 1000);//?????? ?????? ????????? ?????? ???????????? ?????? ?????? ??? ????????? ?????? ?????? ??????????????? ???????????????.

		fAjax('/aadmin/exchange/process/refreshExchReadyData', 'frm', 'ACT=GetExchStamp&pg=' + (document.pg + 1));
	}

	document.isScrolling = true;
}
function fMakeRow()
{
	if (document.isScrolling)
	{
		if (!document.isShow)
		{
			document.isShow = true;
			return;
		}
	}
	else
		$('.DataRow').remove();

	document.pg = parseInt(document.r.arr.pg, 10);

	if (document.r.arr.rows.length)
	{
		var Clone;

		$('select[name=H_Lv]').val(document.r.arr.rows[0].Lv);
		fSetConditionExch();

		var EducationCnt = 0;
		$.each(document.r.arr.rows, function(index, item){
			EducationCnt += item.EducationCnt;
		});

		$.each(document.r.arr.rows, function(index, item){
			item.ExchDate.date = item.ExchDate.substring(0,10);
			Clone = $('<tr class="DataRow" align="center"' + (item.isExchOK == 'Y' ? ' bgcolor="#ecec93"' : '') + '>' + fMakeDataHTML(item, EducationCnt) + '</tr>');
			Clone.appendTo('#DataTable table')
				.attr('MemberSeq', item.MemberSeq)
				.attr('Lv', item.Lv)
				.attr('XDate', item.ExchDate.substring(0,10));
		});

		if (document.pg == 1)
			$('#tcnt').text(document.r.arr.tcnt);

		$((document.pg == 1 ? '#DataTable,' : '') + '#NotExist').css('display','none');
		$('#DataTable').slideDown();

		$(window).scroll(function() {
			if($(window).scrollBottom() < 150) fScrolling();
		});
	}
	else
	{
		if (document.r.arr.pg == 1)
		{
			$('#DataTable,#NotExist').css('display','none');
			$('#NotExist').slideDown();
		}

		$(window).unbind("scroll");
	}

	$('#divProg').slideUp();
	document.isShow = false;
	document.xhr = false;
	document.isScrolling = false;
}
$.fn.scrollBottom = function() { 
    return $(document).height() - this.scrollTop() - this.height(); 
};



//????????? ????????? ?????? ?????? ?????????
function fClickMemInfo(obj)
{
	var MemberSeq = $(obj).closest('tr').attr('MemberSeq');
	window.open('/aadmin/memberView?Seq=' + MemberSeq, 'UserInfo');
}
function fClickDate(obj)
{
	var MemberSeq = $(obj).closest('tr').attr('MemberSeq');

	var WeekRows = $('.DataRow[MemberSeq=' + MemberSeq + ']:gt(0)');
	if (WeekRows.length)
	{
		WeekRows.remove();
		return;
	}

	var XDate = $(obj).closest('tr').attr('XDate');
	fAjax('/aadmin/exchange/process/refreshExchReadyData', '', 'ACT=GetWeekData&H_MemberSeq=' + MemberSeq + '&H_XDate=' + XDate);
}
function fClickStamp(obj)
{
	var MemberSeq = $(obj).closest('tr').attr('MemberSeq');
	var XDate = $(obj).closest('tr').attr('XDate');
	var Increase;

	if (confirm('????????? 1?????? ?????????????????????????'))
		Increase = 1;
	else if (confirm('????????? 1?????? ?????????????????????????\n(???????????? ????????? ???????????? ???????????? ??? ????????????.)'))
		Increase = -1;

	if (Increase)
		fAjax('/aadmin/exchange/process/refreshExchReadyData', '', 'ACT=IncreaseStamp&H_MemberSeq=' + MemberSeq + '&H_XDate=' + XDate + '&H_Increase=' + Increase, obj);
}



//????????????
function addComma(num) {
	return (num * 1).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}



//????????? ?????? ?????? ??????
function fSetConditionExch()
{
	var lv = $('select[name=H_Lv]').val();
	$('#ExchBaseAlmoney').text(cfgExch[lv] ? addComma(cfgExch[lv].ExchAlmoney) : '-');
	$('#ExchLimitAlmoney').text(cfgExch[lv] ? addComma(cfgExch[lv].ExchLimitAlmoney) : '-');
	$('#ExchAlmoneyTexRate').text(cfgExch[lv] ? addComma(cfgExch[lv].ExchAlmoneyTexRate) : '-');
	$('#ExchStampCnt').text(cfgExch[lv] ? cfgExch[lv].ExchStampCnt : '-');
	$('#ExchQusRegCnt').text(cfgExch[lv] ? cfgExch[lv].ExchQusRegCnt : '-');
	$('#ExchQusChoiceRate').text(cfgExch[lv] ? cfgExch[lv].ExchQusChoiceRate : '-');
	$('#ExchAnsRegCnt').text(cfgExch[lv] ? cfgExch[lv].ExchAnsRegCnt : '-');
	$('#ExchAnsChoicedCnt').text(cfgExch[lv] ? cfgExch[lv].ExchAnsChoicedCnt : '-');
	$('#ExchAnsEstiCnt').text(cfgExch[lv] ? cfgExch[lv].ExchAnsEstiCnt : '-');
}
$(document).ready(function(){
	fSetConditionExch();
});

</script>

    <div id="wrapper">
        <div id="M_wrapper">
			<%@ include file="/Common/include/menuAdmin.jsp" %>
        </div>
        <div class="container-fluid border" style="border-bottom:none">
            <div class="title-wrapper row">
                <div class="title-name col-md-4">
                    ?????? ????????? ?????? ????????? ??????
				</div>
				<%if(CommonUtil.libhasAuthority(alpayLogCheck, userSeq)) {%>
				<div class="title-name col-md-4">
					<a href="/aadmin/exchange/alpayLogCheck">[????????? -> ????????? ?????? Log]</a>
				</div>
				<% } %>
				<%if(CommonUtil.libhasAuthority(alpayExchList, userSeq)) {%>
				<div class="title-name col-md-4">
					<a href="/aadmin/exchange/alpayExchList">[?????? ?????? ?????? ??????]</a>
				</div>
				<% } %>
            </div>
			<div class="col-xs-12" style="margin-top:30px;padding:5px">
				<p style="position:absolute;right:0px;top:0px;cursor:pointer" onClick="fAjax('/aadmin/exchange/process/refreshExchReadyData', '', 'ACT=SetExchStamp')">
					<i class="fas fa-undo"></i>
					????????? ??????
				</p>
			</div>
            <form name="frm" class="filter-wrapper row" style="margin-top:60px;padding-top:8px;" autocomplete="off">
                <div class="row" style="position:relative;border-bottom:1px solid #bab9b9;padding:5px;padding-bottom:16px;">
					<div class="col-xs-2" style="font-weight:bolder;font-size:18px;line-height:32px">?????? ?????? <a href="javascript:void(0)" onClick="$('#tip').toggle(100)" style="color:#999;font-weight:bold">???</a></div>
					<div id="tip" style="top:40px;left:100px;position:absolute;padding:10px;background-color:#e5f2ff;border: 2px solid #abd6fb;font-size:12px;z-index:99;display:none">
						* ???????????? ?????? ?????? ????????? ???????????? ?????? ????????? ?????? ??????????????? ????????? ??? ?????? ????????? ????????? ?????? ????????? ??? ??? ????????????.<br>
						* ???????????? ???????????? ?????? ????????? ?????? ?????? ????????? ??? ??? ????????????. (??? ????????? ????????? ????????? ???????????? ???????????? ????????? ???????????????.)<br>
						* ?????? ?????? ????????? ????????? ???????????? ???????????? ???????????? ?????? ??? ????????? ??? ????????????. (???????????? ????????? ????????? ?????? ???????????????.)<br>
					</div>
                    <div class="col-xs-10" style="text-align:right;">
						<span style="display:inline-block;color:#0081B1">?????? ??????:</span>
						<select name="H_Lv" class="filter-flag form-control" style="margin-left:10px;padding:0 10px;width:200px" onChange="fSetConditionExch()">
						<c:forEach var="i" begin="1" end="${fn:length(codeNm)}" step="1" varStatus="status">
							<c:if test="${i > 1 and i < 90}">
								<c:set var="n" value="${i}"/>
								<%
									String n = String.valueOf( pageContext.getAttribute("n") );
									pageContext.setAttribute("n", n);
								%>
								<option value="${codeCd.get(n)}">${codeCd.get(n)}?????? (${codeNm.get(n)})</option>
							</c:if>
						</c:forEach>
						</select>
						<span style="margin-left:50px">????????????:</span>
						<input type="input" name="H_Seq" class="filter-flag form-control" style="margin-left:10px;padding:0 10px;width:100px;text-align:center" onKeyPress="if(event.keyCode == 13) fAjax('process/refreshExchReadyData', 'frm', 'ACT=GetExchStamp&pg=1')">
						<span style="margin-left:50px">??? ?????? ????????? ROW ???</span>
						<input type="input" name="H_MaxRow" class="filter-flag form-control" style="margin-left:10px;padding:0 10px;width:100px;text-align:center" value="${maxRow}">
						<input type="button" class="filter-flag form-control" value="??????" style="margin-left:50px;background-color:#286090;boder-color:#204d74;width:100px;color:#fff" onClick="fAjax('process/refreshExchReadyData', 'frm', 'ACT=GetExchStamp&pg=1')">
                    </div>
                </div>
                <div class="search-condition-set" style="padding:5px;margin-top:20px">
					<table class="table table-bordered" style="margin:0;width:100%">
						<tr class="data_header">
							<td rowspan="2">?????? ?????? ???</td>
							<td rowspan="2" width="11%">?????? ?????? ???</td>
							<td rowspan="2" width="11%">????????? (%)</td>
							<td colspan="7">????????? ?????? ??????</td>
						</tr>
						<tr class="data_header">
							<td width="11%">????????? ???</td>
							<td width="11%">?????? ?????? ???</td>
							<td width="11%">????????? ?????????</td>
							<td width="11%">?????? ?????? ???</td>
							<td width="11%">?????? ????????? ???</td>
							<td width="11%">?????? ?????? ???</td>
						</tr>
						<tr align="center">
							<td id="ExchBaseAlmoney">-</td>
							<td id="ExchLimitAlmoney">-</td>
							<td id="ExchAlmoneyTexRate">-</td>
							<td id="ExchStampCnt">-</td>
							<td id="ExchQusRegCnt">-</td>
							<td><span id="ExchQusChoiceRate">-</span>%</td>
							<td id="ExchAnsRegCnt">-</td>
							<td id="ExchAnsChoicedCnt">-</td>
							<td id="ExchAnsEstiCnt">-</td>
						</tr>
					</table>
				</div>
			</form>
			<div id="DataTable" style="margin-top:50px;display:none">
			<div style="text-align:right">?????? ?????? ??? : <span id="tcnt"></span></div>
			<table class="table table-bordered table-hover">
				<tr class="data_header">
					<td rowspan="2" width="130">????????????</td>
					<td rowspan="2" width="220">?????????</td>
					<td rowspan="2" width="120">??????</td>
					<td rowspan="2">???????????????</td>
					<td rowspan="2">??????</td>
					<td colspan="6">????????? ?????? ??????</td>
					<td rowspan="2" width="150">?????????<br>/<br><i style="color:#00a000">WEEK ?????????</i></td>
					<td rowspan="2" width="120" style="vertical-align:middle;color:dodgerblue">??????????????????</td>
				</tr>
				<tr class="data_header">
					<td width="120">?????????</td>
					<td width="120">?????? ??????</td>
					<td width="120">????????? ??????</td>
					<td width="120">?????? ??????</td>
					<td width="120">?????? ?????????</td>
					<td width="120">?????? ??????</td>
				</tr>
				<tr id="samDataRow" style="display:none">
					<td onClick="fClickMemInfo(this)"><a href="javascript:void(0)">#MemberSeq#</a></td>
					<td>#Nickname#</td>
					<td>#Name#</td>
					<td>#Almoney#</td>
					<td>#Lv#</td>
					<td onClick="fClickStamp(this)"><a href="javascript:void(0)">#StampCnt#</a></td>
					<td>#QusRegCnt#</td>
					<td>#QusChoiceRate#</td>
					<td>#AnsRegCnt#</td>
					<td>#AnsChoicedCnt#</td>
					<td>#AnsEstiCnt#</td>
					<td onClick="fClickDate(this)"><a href="javascript:void(0)">#ExchDate#</a></td>
					<td>#isOK#</td>
				</tr>
				<tr id="samDataWeekRow" style="display:none">
					<td colspan="4">&nbsp;</td>
					<td>#Lv#</td>
					<td>#StampCnt#</td>
					<td>#QusRegCnt#</td>
					<td>#QusChoiceRate#</td>
					<td>#AnsRegCnt#</td>
					<td>#AnsChoicedCnt#</td>
					<td>#AnsEstiCnt#</td>
					<td style="color:#00a000;font-size:14px"><i>#regdate#</i></td>
					<td>&nbsp;</td>
				</tr>
				<tr id="samNullRow" bgcolor="#f0fff0" style="display:none">
					<td colspan="11" style="height:0;padding:0;line-height:0"></td>
				</tr>
			</table>
			</div>
			<div id="divProg" style="width:100%;display:none"><div style="width:30px;margin:auto"><img src="/Common/images/search_nick_loader.gif" style="width:100%"></div></div>
			<p id="NotExist" style="margin-top:50px;text-align:center;display:none">?????? ????????? ???????????? ????????? ????????????.</p>
            <div id="pagination" style="text-align: center;"></div>
        </div>
    </div>
	<br><br>
</body>