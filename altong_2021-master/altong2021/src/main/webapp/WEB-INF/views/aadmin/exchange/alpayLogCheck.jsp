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
function fAjax(url, frm, param) {
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
				case 'GetAlpayLog':
					document.r = r;
					fMakeRow();
					break;
				default:
					if (r.result) alert(r.result);
					break;
			}
		},
		error: function (r, textStatus, err) {
			if (r.responseText && r.responseText.substr(0, 13) == '<!--LOGOFF-->') {top.location = 'http://www.altong.com/'; return;}
			$('#divProg').slideUp();
			$(window).unbind("scroll");
			console.log(r);
		},
		complete: function () {
			if (!document.isShow) document.xhr = false;
		}
	});
}
function fMakeDataHTML(item)
{
	item.Almoney *= 1;
	var html = $('#samDataRow').html();
	html = html.replace(/#MemberSeq#/gi, item.MemberSeq);
	html = html.replace('#Nickname#', item.NickName);
	html = html.replace('#Name#', item.Name);
	html = html.replace('#ExchangeAlmoney#', addComma(item.ExchangeAlmoney));
	html = html.replace('#RealMoney#', addComma(item.RealMoney));
	html = html.replace('#Almoney#', addComma(item.Almoney));
	html = html.replace('#AlpayKR#', addComma(item.AlpayKR));
	html = html.replace('#regdate#', item.regdate.substring(0,10));

	return html;
}



//????????????
function fScrolling()
{
	if (!document.xhr) {
		$('#divProg').slideDown(300);
		setTimeout(fMakeRow, 1000);//?????? ?????? ????????? ?????? ???????????? ?????? ?????? ??? ????????? ?????? ?????? ??????????????? ???????????????.

		fAjax('/aadmin/exchange/alpayLogCheckAjax', 'frm', 'ACT=GetAlpayLog&pg=' + (document.pg + 1));
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

		$.each(document.r.arr.rows, function(index, item){
			Clone = $('<tr class="DataRow" align="center">' + fMakeDataHTML(item) + '</tr>');
			Clone.appendTo('#DataTable table');
		});

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
	var MemberSeq = $(obj).attr('MemberSeq');
	window.open('/aadmin/memberView?Seq=' + MemberSeq, 'UserInfo');
}

//????????????
function addComma(num) {
	return (num * 1).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}
</script>

    <div id="wrapper">
        <div id="M_wrapper">
			<%@ include file="/Common/include/menuAdmin.jsp" %>
		</div>
        <div class="container-fluid border" style="border-bottom:none">
            <div class="title-wrapper row">
            	<%if(CommonUtil.libhasAuthority(exchReadyList, userSeq)) {%>
				<div class="title-name col-md-4">
					<a href="/aadmin/exchange/exchReadyList">[?????? ????????? ?????? ????????? ??????]</a>
				</div>
				<% } %>
				<div class="title-name col-md-4">
                    ????????? -> ????????? ?????? Log
				</div>
				<%if(CommonUtil.libhasAuthority(alpayExchList, userSeq)) {%>
				<div class="title-name col-md-4">
					<a href="/aadmin/exchange/alpayExchList">[?????? ?????? ?????? ??????]</a>
				</div>
				<% } %>
            </div>
            <form name="frm" class="filter-wrapper row" style="margin-top:60px;padding-top:8px;" autocomplete="off">
                <div class="row" style="position:relative;padding:5px;">
					<div class="col-xs-2"></div>
                    <div class="col-xs-10" style="text-align:right;">
						<span style="margin-left:50px">????????????:</span>
						<input type="input" name="H_Seq" class="filter-flag form-control" style="margin-left:10px;padding:0 10px;width:100px;text-align:center" onKeyPress="if(event.keyCode == 13) fAjax('/aadmin/exchange/alpayLogCheckAjax', 'frm', 'ACT=GetAlpayLog&pg=1')">
						<span style="margin-left:50px">??? ?????? ????????? ROW ???</span>
						<input type="input" name="H_MaxRow" class="filter-flag form-control" style="margin-left:10px;padding:0 10px;width:100px;text-align:center" value="${maxRow}">
						<input type="button" class="filter-flag form-control" value="??????" style="margin-left:50px;background-color:#286090;boder-color:#204d74;width:100px;color:#fff" onClick="fAjax('/aadmin/exchange/alpayLogCheckAjax', 'frm', 'ACT=GetAlpayLog&pg=1')">
                    </div>
                </div>
			</form>
			<div id="DataTable" style="margin-top:50px;display:none">
			<table class="table table-bordered table-hover">
				<tr class="data_header">
					<td width="130">????????????</td>
					<td width="220">?????????</td>
					<td width="120">??????</td>
					<td>?????? ?????????</td>
					<td>?????? ?????????</td>
					<td>????????? ??????</td>
					<td>????????? ??????</td>
					<td width="150">?????????</td>
				</tr>
				<tr id="samDataRow" style="display:none">
					<td onClick="fClickMemInfo(this)" MemberSeq="#MemberSeq#"><a href="javascript:void(0)">#MemberSeq#</a></td>
					<td>#Nickname#</td>
					<td>#Name#</td>
					<td>#ExchangeAlmoney#</td>
					<td>#RealMoney#</td>
					<td>#Almoney#</td>
					<td>#AlpayKR#</td>
					<td>#regdate#</td>
				</tr>
			</table>
			</div>
			<div id="divProg" style="width:100%;display:none"><div style="width:30px;margin:auto"><img src="/Common/images/search_nick_loader.gif" style="width:100%"></div></div>
			<p id="NotExist" style="margin-top:50px;text-align:center;display:none">?????? ????????? ???????????? ????????? ????????????.</p>
        </div>
    </div>
	<br><br>
</body>