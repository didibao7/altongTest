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
var cfgLvUp = JSON.parse('${cfgLvUp}');
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
				case 'GetLvStamp':
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
								if (item.isExtra == 'Y') item.regdate.date = 'EXTRA';
							});

							r.arr.sort(function(a, b) {
								return a.regdate.date < b.regdate.date ? -1 : a.regdate.date > b.regdate.date ? 1 : 0;
							});

							$.each(r.arr, function(index, item){
								item.Lv = Lv;
								//console.log("item.regdate.date : " + item.regdate);
								Clone = $('<tr class="DataRow" align="center" bgcolor="#f0fff0">' + fMakeWeekDataHTML(item) + '</tr>');
								Clone.insertAfter(baseNode)
									.attr('Lv', item.Lv)
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
				case 'SetEducation':
					{
						var MemberSeq = $(obj).closest('tr').attr('MemberSeq');
						r.arr[0].Lv = $('.DataRow[MemberSeq=' + MemberSeq + ']:first').attr('Lv');

						$(obj).closest('tr').html(fMakeWeekDataHTML(r.arr[0]));
						$('.DataRow[MemberSeq=' + MemberSeq + ']:first').html(fMakeDataHTML(r.arr[1]))
							.attr('bgcolor', r.arr[1].isUpOK == 'Y' ? '#ecec93' : '#fff');
					}
					break;
				case 'IncreaseStamp':
					{
						//var MemberSeq = $(obj).closest('tr').attr('MemberSeq');
						$('.DataRow[MemberSeq=' + r.arr.MemberSeq + ']:gt(0)').remove();

						$(obj).closest('tr').html(fMakeDataHTML(r.arr))
							.attr('bgcolor', r.arr.isUpOK == 'Y' ? '#ecec93' : '#fff');
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
			$('#divModal').slideUp(0);
		}
	});
}
function fSetCntColor(item, colName, byForce)
{
	var conVal = colName == 'Almoney' ? addComma(item[colName]) : item[colName];
	return (byForce || item[colName] >= cfgLvUp[item.Lv]['LvUp'+colName]) ? conVal : '<span style="color:#ff9999;font-weight:bold">' + conVal + '</span>';
}
function fMakeWeekDataHTML(item)
{
	var html = $('#samDataWeekRow').html();
	html = html.replace('#StampCnt#', item.LvUpStamp);
	html = html.replace('#QusRegCnt#', fSetCntColor(item, 'QusRegCnt'));
	html = html.replace('#AnsRegCnt#', fSetCntColor(item, 'AnsRegCnt'));
	html = html.replace('#AnsChoicedCnt#', fSetCntColor(item, 'AnsChoicedCnt'));
	html = html.replace('#AnsEstiCnt#', fSetCntColor(item, 'AnsEstiCnt'));
	html = html.replace('#ReplyCnt#', fSetCntColor(item, 'ReplyCnt'));
	html = html.replace('#EducationCnt#', item.EducationCnt);
	html = html.replace('#regdate#', item.regdate.substring(0,10));

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
	html = html.replace('#Photo#', fSetCntColor(item, 'Photo', (cfgLvUp[item.Lv]['LvUpPhoto'] != 'Y' || item.Photo == 'Y')));
	html = html.replace('#Almoney#', fSetCntColor(item, 'Almoney'));
	html = html.replace('#StampCnt#', fSetCntColor(item, 'StampCnt'));
	html = html.replace('#QusRegCnt#', fSetCntColor(item, 'QusRegCnt'));
	html = html.replace('#AnsRegCnt#', fSetCntColor(item, 'AnsRegCnt'));
	html = html.replace('#AnsChoicedCnt#', fSetCntColor(item, 'AnsChoicedCnt'));
	html = html.replace('#AnsEstiCnt#', fSetCntColor(item, 'AnsEstiCnt'));
	html = html.replace('#ReplyCnt#', fSetCntColor(item, 'ReplyCnt'));
	html = html.replace('#EducationCnt#', fSetCntColor(item, 'EducationCnt'));
	html = html.replace('#RecmdLv_1#', item.RecmdLv_1);
	html = html.replace('#RecmdCnt_1#', fSetCntColor(item, 'RecmdCnt_1'));
	html = html.replace('#LvDate#', item.LvDate.substring(0,10));
	html = html.replace('#isOK#', item.isUpOK);

	return html;
}



//????????????
function fScrolling()
{
	if (!document.xhr) {
		$('#divProg').slideDown(300);
		setTimeout(fMakeRow, 1000);//?????? ?????? ????????? ?????? ???????????? ?????? ?????? ??? ????????? ?????? ?????? ??????????????? ???????????????.

		fAjax('/aadmin/levelup/process/refreshLvReadyData_new_Ajax', 'frm', 'ACT=GetLvStamp&pg=' + (document.pg + 1));
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
		fSetConditionLvUp();

		var EducationCnt = 0;
		$.each(document.r.arr.rows, function(index, item){
			EducationCnt += item.EducationCnt;
		});

		$.each(document.r.arr.rows, function(index, item){
			Clone = $('<tr class="DataRow" align="center"' + (item.isUpOK == 'Y' ? ' bgcolor="#ecec93"' : '') + '>' + fMakeDataHTML(item, EducationCnt) + '</tr>');
			Clone.appendTo('#DataTable table')
				.attr('MemberSeq', item.MemberSeq)
				.attr('Lv', item.Lv)
				.attr('XDate', item.LvDate.substring(0,10));
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
	//console.log('XDate : ' + XDate);
	fAjax('/aadmin/levelup/process/refreshLvReadyData_new_Ajax', '', 'ACT=GetWeekData&H_MemberSeq=' + MemberSeq + '&H_XDate=' + XDate);
}
function fClickEducation(obj)
{
	var MemberSeq = $(obj).closest('tr').attr('MemberSeq');
	var XDate = $(obj).closest('tr').attr('XDate');
	//console.log('MemberSeq : ' + MemberSeq);
	//console.log('XDate : ' + XDate);
	//return;
	if (XDate == 'EXTRA')
		alert('EXTRA????????? ????????? ????????? ??? ????????????.');
	else
		fAjax('/aadmin/levelup/process/refreshLvReadyData_new_Ajax', '', 'ACT=SetEducation&H_MemberSeq=' + MemberSeq + '&H_XDate=' + XDate, obj);
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
		fAjax('/aadmin/levelup/process/refreshLvReadyData_new_Ajax', '', 'ACT=IncreaseStamp&H_MemberSeq=' + MemberSeq + '&H_XDate=' + XDate + '&H_Increase=' + Increase, obj);
}



//????????????
function addComma(num) {
	return (num * 1).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}



//????????? ?????? ?????? ??????
function fSetConditionLvUp()
{
	var lv = $('select[name=H_Lv]').val();
	$('#LvUpPhoto').text(cfgLvUp[lv] ? cfgLvUp[lv].LvUpPhoto : '-');
	$('#LvUpBaseAlmoney').text(cfgLvUp[lv] ? addComma(cfgLvUp[lv].LvUpAlmoney) : '-');
	$('#LvUpStampCnt').text(cfgLvUp[lv] ? cfgLvUp[lv].LvUpStampCnt : '-');
	$('#LvUpQusRegCnt').text(cfgLvUp[lv] ? cfgLvUp[lv].LvUpQusRegCnt : '-');
	$('#LvUpAnsRegCnt').text(cfgLvUp[lv] ? cfgLvUp[lv].LvUpAnsRegCnt : '-');
	$('#LvUpAnsChoicedCnt').text(cfgLvUp[lv] ? cfgLvUp[lv].LvUpAnsChoicedCnt : '-');
	$('#LvUpAnsEstiCnt').text(cfgLvUp[lv] ? cfgLvUp[lv].LvUpAnsEstiCnt : '-');
	$('#LvUpReplyCnt').text(cfgLvUp[lv] ? cfgLvUp[lv].LvUpReplyCnt : '-');
	$('#LvUpEducationCnt').text(cfgLvUp[lv] ? cfgLvUp[lv].LvUpEducationCnt : '-');
	$('#LvUpRecmdLv_1').text(cfgLvUp[lv] ? cfgLvUp[lv].LvUpRecmdLv_1 : '-');
	$('#LvUpRecmdCnt_1').text(cfgLvUp[lv] ? cfgLvUp[lv].LvUpRecmdCnt_1 : '-');
}
$(document).ready(function(){
	fSetConditionLvUp();
});
</script>

    <div id="wrapper">
        <div id="M_wrapper">
			<%@ include file="/Common/include/menuAdmin.jsp" %>
        </div>
        <div class="container-fluid border" style="border-bottom:none">
            <div class="title-wrapper row">
                <div class="title-name col-md-6">
                    ?????? ????????? ?????? ????????? ??????
                </div>
            </div>
			<div class="col-xs-12" style="margin-top:30px;padding:5px">
				<!--<p style="position:absolute;right:26rem;top:0px;cursor:pointer" @click="onExcelBtnClick">
					<i class="fas fa-file-excel"></i>
					?????? ????????????
				</p>-->
				<p style="position:absolute;right:15rem;top:0px;cursor:pointer" onClick="$('#divModal').slideDown(0);fAjax('/aadmin/levelup/process/refreshLvReadyData_new_Ajax', '', 'ACT=SetLvStamp&H_Lv=1')">
					<i class="fas fa-undo"></i>
					1?????? ?????? ??????
				</p>
				<p style="position:absolute;right:0px;top:0px;cursor:pointer" onClick="$('#divModal').slideDown(0);fAjax('/aadmin/levelup/process/refreshLvReadyData_new_Ajax', '', 'ACT=SetLvStamp&H_Lv=2')">
					<i class="fas fa-undo"></i>
					2?????? ?????? ??????
				</p>
			</div>
            <form name="frm" class="filter-wrapper row" style="margin-top:60px;padding-top:8px;" autocomplete="off">
                <div class="row" style="position:relative;border-bottom:1px solid #bab9b9;padding:5px;padding-bottom:16px;">
					<div class="col-xs-2" style="font-weight:bolder;font-size:18px;line-height:32px">?????? ?????? <a href="javascript:void(0)" onClick="$('#tip').toggle(100)" style="color:#999;font-weight:bold">???</a></div>
					<div id="tip" style="top:40px;left:100px;position:absolute;padding:10px;background-color:#e5f2ff;border: 2px solid #abd6fb;font-size:12px;z-index:99;display:none">
						* ???????????? ?????? ?????? ????????? ???????????? ?????? ????????? ?????? ??????????????? ????????? ??? ?????? ????????? ????????? ?????? ????????? ??? ??? ????????????.<br>
						* ?????? ???????????? ???????????? ?????? ????????? ?????? ?????? ????????? ??? ??? ????????????. (??? ????????? ????????? ????????? ???????????? ???????????? ????????? ???????????????.)<br>
						* ?????? ?????? ????????? ????????? ???????????? ???????????? ???????????? ?????? ??? ????????? ??? ????????????. (???????????? ????????? ????????? ?????? ???????????????.)<br>
						* ????????? ???????????? ????????? ????????? ?????? ?????? ??????????????? ???????????????, (?????? ???????????? ?????? ????????? ?????? ?????? ????????? ????????? ??? ????????????.)<br>
					</div>
                    <div class="col-xs-10" style="text-align:right;">
						<span style="display:inline-block;color:#0081B1">?????? ??????:</span>
						<select name="H_Lv" class="filter-flag form-control" style="margin-left:10px;padding:0 10px;width:200px" onChange="fSetConditionLvUp()">
						<c:forEach var="i" begin="1" end="${fn:length(codeNm)}" step="1" varStatus="status">
							<c:if test="${i > 0 and i < 90}">
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
						<input type="input" name="H_Seq" class="filter-flag form-control" style="margin-left:10px;padding:0 10px;width:100px;text-align:center" onKeyPress="if(event.keyCode == 13) fAjax('/aadmin/levelup/process/refreshLvReadyData_new_Ajax', 'frm', 'ACT=GetLvStamp&pg=1')">
						<span style="margin-left:50px">??? ?????? ????????? ROW ???</span>
						<input type="input" name="H_MaxRow" class="filter-flag form-control" style="margin-left:10px;padding:0 10px;width:100px;text-align:center" value="${maxRow}">
						<input type="button" class="filter-flag form-control" value="??????" style="margin-left:50px;background-color:#286090;boder-color:#204d74;width:100px;color:#fff" onClick="fAjax('/aadmin/levelup/process/refreshLvReadyData_new_Ajax', 'frm', 'ACT=GetLvStamp&pg=1')">
                    </div>
                </div>
                <div class="search-condition-set" style="padding:5px;margin-top:20px">
					<table class="table table-bordered" style="margin:0;width:100%">
						<tr class="data_header">
							<td rowspan="2">?????? ??????</td>
							<td rowspan="2" width="9%">?????? ???</td>
							<td colspan="2">????????????</td>
							<td colspan="6">????????? ?????? ??????</td>
							<td rowspan="2" width="9%">?????? ??????</td>
						</tr>
						<tr class="data_header">
							<td width="9%">?????? ??????</td>
							<td width="9%">?????? ???</td>
							<td width="9%">????????? ???</td>
							<td width="9%">?????? ?????? ???</td>
							<td width="9%">?????? ?????? ???</td>
							<td width="9%">?????? ????????? ???</td>
							<td width="9%">?????? ?????? ???</td>
							<td width="9%">?????? ???</td>
						</tr>
						<tr align="center">
							<td id="LvUpPhoto">-</td>
							<td id="LvUpBaseAlmoney">-</td>
							<td id="LvUpRecmdLv_1">-</td>
							<td id="LvUpRecmdCnt_1">-</td>
							<td id="LvUpStampCnt">-</td>
							<td id="LvUpQusRegCnt">-</td>
							<td id="LvUpAnsRegCnt">-</td>
							<td id="LvUpAnsChoicedCnt">-</td>
							<td id="LvUpAnsEstiCnt">-</td>
							<td id="LvUpReplyCnt">-</td>
							<td id="LvUpEducationCnt">-</td>
						</tr>
					</table>
				</div>
			</form>
			<div id="DataTable" style="margin-top:50px;display:none">
			<div style="text-align:right">?????? ?????? ??? : <span id="tcnt"></span></div>
			<table class="table table-bordered table-hover">
				<tr class="data_header">
					<td rowspan="2" width="100">????????????</td>
					<td rowspan="2" width="220">?????????</td>
					<td rowspan="2" width="100">??????</td>
					<td rowspan="2">??????</td>
					<td rowspan="2">??????</td>
					<td rowspan="2">???????????????</td>
					<td colspan="2">????????????</td>
					<td colspan="6">????????? ?????? ??????</td>
					<td rowspan="2">??????</td>
					<td rowspan="2" width="130">?????????<br><i>(????????? ?????????)</i></td>
					<td rowspan="2" width="90" style="vertical-align:middle;color:dodgerblue">????????????</td>
				</tr>
				<tr class="data_header">
					<td width="100">????????????</td>
					<td width="100">?????????</td>
					<td width="100">?????????</td>
					<td width="100">????????????</td>
					<td width="100">????????????</td>
					<td width="100">?????? ?????????</td>
					<td width="100">????????????</td>
					<td>??????</td>
				</tr>
				<tr id="samDataRow" style="display:none">
					<td onClick="fClickMemInfo(this)"><a href="javascript:void(0)">#MemberSeq#</a></td>
					<td>#Nickname#</td>
					<td>#Name#</td>
					<td>#Lv#</td>
					<td>#Photo#</td>
					<td>#Almoney#</td>
					<td>#RecmdLv_1#</td>
					<td>#RecmdCnt_1#</td>
					<td onClick="fClickStamp(this)"><a href="javascript:void(0)">#StampCnt#</a></td>
					<td>#QusRegCnt#</td>
					<td>#AnsRegCnt#</td>
					<td>#AnsChoicedCnt#</td>
					<td>#AnsEstiCnt#</td>
					<td>#ReplyCnt#</td>
					<td>#EducationCnt#</td>
					<td onClick="fClickDate(this)"><a href="javascript:void(0)">#LvDate#</a></td>
					<td>#isOK#</td>
				</tr>
				<tr id="samDataWeekRow" style="display:none">
					<td colspan="8">&nbsp;</td>
					<td>#StampCnt#</td>
					<td>#QusRegCnt#</td>
					<td>#AnsRegCnt#</td>
					<td>#AnsChoicedCnt#</td>
					<td>#AnsEstiCnt#</td>
					<td>#ReplyCnt#</td>
					<td onClick="fClickEducation(this)"><a href="javascript:void(0)">#EducationCnt#</a></td>
					<td><i>(#regdate#)</i></td>
					<td>&nbsp;</td>
				</tr>
				<tr id="samNullRow" bgcolor="#f0fff0" style="display:none">
					<td colspan="15" style="height:0;padding:0;line-height:0"></td>
				</tr>
			</table>
			</div>
			<div id="divProg" style="width:100%;display:none"><div style="width:30px;margin:auto"><img src="/Common/images/search_nick_loader.gif" style="width:100%"></div></div>
			<p id="NotExist" style="margin-top:50px;text-align:center;display:none">?????? ????????? ???????????? ????????? ????????????.</p>
            <div id="pagination" style="text-align: center;"></div>
        </div>
    </div>
	<br><br>
	<div id="divModal" style="position:fixed;top:0;width:100%;height:100%;background-color:rgba(0,0,0,0.2);display:none">
		<img src="/Common/images/search_nick_loader.gif" style="position:absolute;width:30px;top:50%;left:50%;transform: translate(-50%,-50%)">
	</div>
</body>