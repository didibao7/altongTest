<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/Common/include/headerAdmin.jsp" %>
<body style="overflow-y:scroll">
<style>
    .border {
        border: 1px solid #bab9b9;
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

    .filter-flag {
        display: inline-block;
        padding: 0;
        width: 20%;
    }

    .filter-wrapper {
        margin-top: 30px;
        border: 3px solid #bab9b9;
        padding: 15px;
    }

	.filter-flag.form-control.btnConfirm {
		color: #fff;
		background-color: #5cb85c;
		border-color: #4cae4c;
		width : 60px;
		margin : 3px;
	}

	.filter-flag.form-control.btnCancel {
		color: #fff;
		background-color: #d9534f;
		border-color: #d43f3a;
		width : 60px;
		margin : 3px;
	}

	.filter-flag.form-control.btnCheck {
		color: #fff;
		background-color: #49769c;
		width : 200px;
		margin : 3px;
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

	.modal {
		display : none;
		position : fixed;
		z-index : 1;
		left : 0;
		top : 0;
		width : 100%;
		height : 100%;
		overflow : auto;
		background-color: rgb(0,0,0); 
		background-color: rgba(0,0,0,0.4);
	}

	.modal-content {
		position : fixed;
		top : 300px;
		left : 10%;
		background-color: #fefefe;
		margin: 15% auto; 
		padding: 20px;
		border: 1px solid #888;
		width: 80%; 
		text-align : left;
	}

	.close {
		position : fixed;
		right : 12%;
		color: black;
		float: right;
		font-size: 28px;
		font-weight: bold;
	}

	.close:hover,
	.close:focus {
		color: black;
		text-decoration: none;
		cursor: pointer;
	}
	.data_header td {border: 1px solid #aaa !important;background-color:#eee;text-align:center}
	td {vertical-align:middle !important;}

	span.AddiTitle {
		width : 130px;
		font-weight: bold;
		display : inline-block;
	}

</style>
<script>
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
				case 'GetMemberCert':
					document.r = r;
					fMakeRow();
					break;
				case 'CertOK':
					if (r.arr.result) {
						alert("???????????? ?????????????????????.");
						$(obj).closest('td').html('????????????<br>' + r.arr.actdate.substring(0,16));
					} else {
						alert("?????? ????????? ?????? ??? ????????????.");
					}
					break;
				case 'CertNO':
					if (r.arr.result) {
						alert("???????????? ?????????????????????.");
						$('tr[MemberSeq='+ r.arr.MemberSeq +']').find('.frontStatus').html('<a onClick=fAjax("/aadmin/memberCertListAjax","","ACT=GetCancelMsg&H_MemberSeq=' + r.arr.MemberSeq + '") style="color:#333">????????????</a><br>' + r.arr.actdate.substring(0,16));
					} else {
						alert("?????? ????????? ?????? ??? ????????????.");
					}
					CancelMsgModalClose();
					break;
				case 'GetAddiInfo':
					$("#ANickName").text(r.arr.nickName);
					$("#AJob").text(r.arr.job);
					$("#AArea").text(r.arr.area);
					$("#ARealEstate").text(r.arr.realEstate);
					$("#AResidence").text(r.arr.residence);
					$("#AMarriage").text(r.arr.marriage);
					$("#AChildren").text(r.arr.children);
					$("#ACar").text(r.arr.car);
					$("#AYearIncome").text(r.arr.yearIncome);
					if (r.arr.healthFlag == 'N') {
						$("#AHealthFlag").text("?????????");
					} else { $("#AHealthFlag").text("????????????"); }
					$("#AHealthDetail").text(r.arr.healthDetail ? "(" + r.arr.healthDetail + ")" : "");
					$("#ALikeField").text(r.arr.likeField);
					$("#ALikeFieldEtc").text(r.arr.likeFieldEtc ? ": " + r.arr.likeFieldEtc : "");
					fAddiInfoModal();
					break;
				case 'GetCancelMsg':
					$("#CancelMsgView").html(r.arr == "" ? "<i>????????????</i>" : r.arr);
					fCancelMsgFindModal();
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
	var html = $('#samDataRow').html();

	html = html.replace(/#MemberSeq#/gi, item.memberSeq);
	//html = html.replace(/#Nickname#/gi, item.NickName);
	html = html.replace(/#Nickname#/gi, item.nickName);
	html = html.replace('#Name#', item.name);
	html = html.replace('#IDCard#', item.id_card);
	html = html.replace('#AdditionalInfo#', item.bankMemNm);
	html = html.replace('#regdate#', item.regdate.substr(0,16));
	html = html.replace('#actdate#', (item.actdate ? item.actdate.substr(0,16) : "?????????"));

	

	return html;
}
function escapeHtml(text) {
  return text
      .replace(/&/g, "&amp;")
      .replace(/</g, "&lt;")
      .replace(/>/g, "&gt;")
      .replace(/"/g, "&quot;")
      .replace(/'/g, "&#039;");
}


//????????????
function fScrolling()
{
	if (!document.xhr) {
		$('#divProg').slideDown(300);
		setTimeout(fMakeRow, 1000);//?????? ?????? ????????? ?????? ???????????? ?????? ?????? ??? ????????? ?????? ?????? ??????????????? ???????????????.

		fAjax('/aadmin/memberCertListAjax', 'frm', 'ACT=GetMemberCert&pg=' + (document.pg + 1));
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
			Clone = $('<tr class="DataRow" align="center" MemberSeq="' + item.memberSeq + '">' + fMakeDataHTML(item) + '</tr>');
			var chk = false;
			if (item.certStatus != 1)
			{
				var vColor, vText, vDate;
				switch (item.certStatus)
				{
					case 2:
						vColor = '#2185d0';
						vText = '????????????';
						vDate = item.actdate.substr(0,16);
						break;
					case 3:
						vColor = '#db2828';
						vText = '<a onClick=fAjax("/aadmin/memberCertListAjax","","ACT=GetCancelMsg&H_MemberSeq=' + item.mmberSeq + '") style="color : #d9534f; font-weight:bold">????????????</a>';
						vDate = item.actdate.substr(0,16);
						break;
					default:
						//vColor = '#fbbd08';
						//vText = 'Status : ' + item.certStatus;
						//vDate = '??????';
						chk = true;
						vColor = '#db2828';
						vText = '<a onClick=fAjax("/aadmin/memberCertListAjax","","ACT=CertOK&H_MemberSeq=' + item.mmberSeq + '") style="color : #d9534f; font-weight:bold">??????</a>';
						vDate = item.actdate.substr(0,16);
						break;
				}
				if(chk == false) {
					Clone.find('.frontStatus').html(vText + '<br>' + vDate);
					Clone.find('.frontStatus')
						.css('color', vColor)
						.css('font-weight', 'bold');
				}
			}
			if (item.CertStatus == 1 && (item.actdate.date.substr(0,4) > '1970')){  // DB?????? actdate??? ???????????? NULL??? ???????????? ?????? ???????????? 1970????????? ??????
				Clone.find('.frontStatus').append('<br>???????????? : ' + item.actdate.substr(0,16));
			}
			Clone.appendTo('#DataTable table');
		});

		if (document.pg == 1)

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

//?????? ??? ????????????

function AddiInfoModalClose() {
	$('#AddiInfoModal').hide();
}

function CancelMsgModalClose() {
	$('input[name=chk_IDCard]').prop('checked', false);
	$('input[name=chk_AddiInfo]').prop('checked', false);
	$('input[name=H_actMessage]').val('');
	$('#CancelMsgModal').hide();
}

function CancelMsgFindModalClose() {
	$('#CancelMsgFindModal').hide();
}

window.onclick = function(event) {
	if (event.target == document.getElementById('AddiInfoModal')) {
		$('#AddiInfoModal').hide();
	}

	if (event.target == document.getElementById('CancelMsgModal')) {
		$('input[name=chk_IDCard]').prop('checked', false);
		$('input[name=chk_AddiInfo]').prop('checked', false);
		$('input[name=H_actMessage]').val('');
		$('#CancelMsgModal').hide();
	}

	if (event.target == document.getElementById('CancelMsgFindModal')) {
		$('#CancelMsgFindModal').hide();
	}
}

function fAddiInfoModal()
{
	$('#AddiInfoModal').show();
}

function fCancelMsgFindModal()
{
	$('#CancelMsgFindModal').show();
}

function fCancelMsgModal(obj)
{
	$('#CNickName').text($(obj).attr('NickName'));
	var ms = $(obj).closest('tr').attr('MemberSeq');
	$('#CancelMsgModal').attr('MemberSeq', ms).show();
}

//????????? ??????
function fIDCardCheck(obj) {
	window.open("/aadmin/exchange/idCardView?MemberSeq=" + $(obj).closest('tr').attr('MemberSeq'));
}

//?????? fajax ??????
function fAction(fname, obj) {
	fAjax('/aadmin/memberCertListAjax', '', 'ACT='+fname+'&H_MemberSeq='+$(obj).closest('tr').attr('MemberSeq'), obj);
}
</script>
    <div id="wrapper">
        <div id="M_wrapper">
			<%@ include file="/Common/include/menuAdmin.jsp" %>
        </div>
        <div class="container-fluid border" style="border-bottom:none">
            <div class="title-wrapper row">
            	<%if(CommonUtil.libhasAuthority(memberList, userSeq)) {%>
					<div class="title-name col-md-4">
						<a href="/aadmin/memberList">[?????? ??????]</a>
					</div>
				<% } %>
				<div class="title-name col-md-4">
					?????? ???????????? ??????
				</div>
				<%if(CommonUtil.libhasAuthority(0, userSeq)) {%>
					<div class="title-name col-md-4">
						<a href="/aadmin/adminConfig">[????????? ??????]</a>
					</div>
				<% } %>
			</div>
            <form name="frm" class="filter-wrapper row" style="margin-top:60px;padding-top:8px;" autocomplete="off">
                <div class="row" style="position:relative;border-bottom: none;padding:5px;">
					<div class="col-xs-2"></div>
                    <div class="col-xs-10" style="text-align:right;">
						<span style="margin-left:50px">????????????:</span>
						<input type="input" name="H_Seq" class="filter-flag form-control" style="margin-left:10px;padding:0 10px;width:100px;text-align:center" onKeyPress="if(event.keyCode == 13) fAjax('/aadmin/memberCertListAjax', 'frm', 'ACT=GetMemberCert&pg=1')">
						<span style="margin-left:50px">??? ?????? ????????? ROW ???</span>
						<input type="input" name="H_MaxRow" class="filter-flag form-control" style="margin-left:10px;padding:0 10px;width:100px;text-align:center" value="${maxRow}">
						<input type="button" class="filter-flag form-control" value="??????" style="margin-left:50px;background-color:#286090;boder-color:#204d74;width:100px;color:#fff" onClick="fAjax('/aadmin/memberCertListAjax', 'frm', 'ACT=GetMemberCert&pg=1')">
                    </div>
                </div>
			</form>
			<div id="DataTable" style="margin-top:50px;display:none">
			<table class="table table-bordered table-hover">
				<tr class="data_header">
					<td width="130">????????????</td>
					<td width="220">?????????</td>
					<td width="120">??????</td>
					<td>?????????</td>
					<td>????????????</td>
					<td width="150">?????????</td>
					<td width="230" style="vertical-align:middle;color:dodgerblue">?????? ??????</td>
				</tr>
				<tr id="samDataRow" style="display:none">
					<td onClick="fClickMemInfo(this)" MemberSeq="#MemberSeq#"><a href="javascript:void(0)">#MemberSeq#</a></td>
					<td>#Nickname#</td>
					<td>#Name#</td>
					<td><input type="button" class="filter-flag form-control btnCheck" value="????????? ??????" onClick="fIDCardCheck(this)"></td>
					<td><input type="button" class="filter-flag form-control btnCheck" value="???????????? ??????" onClick="fAction('GetAddiInfo',this)"></td>
					<td>#regdate#</td>
					<td width="150" class="frontStatus">
						<input type="button" class="filter-flag form-control btnConfirm" value="??????" onClick="fAction('CertOK', this)">
						<input type="button" class="filter-flag form-control btnCancel" NickName="#NickName#" value="??????" onClick="fCancelMsgModal(this)">
					</td>
				</tr>				
			</table>
			</div>
			<div id="divProg" style="width:100%;display:none"><div style="width:30px;margin:auto"><img src="/Common/images/search_nick_loader.gif" style="width:100%"></div></div>
			<p id="NotExist" style="margin-top:50px;text-align:center;display:none">?????? ????????? ???????????? ????????? ????????????.</p>
        </div>
	</div>
	<!-- ???????????? ?????? Modal Form -->
	<div id="AddiInfoModal" class="modal">
		<div class="modal-content">
			<span class="close" onClick="AddiInfoModalClose()">&times;</span>
			<span class="AddiTitle">?????????</span>: <span id="ANickName"></span><br>
			<span class="AddiTitle">??????</span>: <span id="AJob"></span><br>
			<span class="AddiTitle">?????? ??????</span>: <span id="AArea"></span><br>
			<span class="AddiTitle">?????????</span>: <span id="ARealEstate"></span><br>
			<span class="AddiTitle">????????????</span>: <span id="AResidence"></span><br>
			<span class="AddiTitle">????????????</span>: <span id="AMarriage"></span><br>
			<span class="AddiTitle">??????</span>: <span id="AChildren"></span><br>		
			<span class="AddiTitle">?????????</span>: <span id="ACar"></span><br>
			<span class="AddiTitle">??? ??????</span>: <span id="AYearIncome"></span><br>
			<span class="AddiTitle">????????????</span>: <span id="AHealthFlag"></span> <span id="AHealthDetail"></span><br>
			<span class="AddiTitle">????????????</span>: <span id="ALikeField"></span> <span id="ALikeFieldEtc"></span>
		</div>
	</div>
	<!-- ?????? ?????? ????????? ?????? Modal Form -->
	<div id="CancelMsgModal" class="modal">
		<div class="modal-content">
			<span class="close" onClick="CancelMsgModalClose()">&times;</span>
			<form name="frm">
			<div><span id="CNickName" style="font-weight : bold"></span>?????? ????????? ???????????? ?????? ??????</div>
			<input type="checkbox" name="chk_IDCard" value="Y"> ?????????&nbsp;&nbsp;
			<input type="checkbox" name="chk_AddiInfo" value="Y"> ????????????&nbsp;&nbsp;&nbsp; 
			<input type="input" name="H_actMessage" style="margin-left: 5px; padding:0 10px;width:50%;text-align:left" maxlength="100" placeholder="100??? ????????? ??????????????????."><input type="button" class="filter-flag form-control" style="margin-left:50px;background-color:#d9534f;boder-color:#d43f3a;width:100px;color:#fff" value="????????????" MemberSeq="#MemberSeq#"  onClick="fAjax('/aadmin/memberCertListAjax', 'frm', 'ACT=CertNO&H_MemberSeq='+$('#CancelMsgModal').attr('MemberSeq'))">
			</form>
		</div>
	</div>
	<?//?????? ?????? ????????? ????????? Modal Form ?>
	<div id="CancelMsgFindModal" class="modal">
		<div class="modal-content">
			<span class="close" onClick="CancelMsgFindModalClose()">&times;</span>
			?????? ?????? : <span id="CancelMsgView" style="width:80%; overflow:auto; display:inline-block; vertical-align:bottom;"></span>
			</div>
	</div>
	<br><br>
</body>