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

			if (r.arr.msg) alert(r.arr.msg);

			switch (r.result) {
				case 'GetMemberCert':
					document.r = r;
					fMakeRow();
					break;
				case 'CertOK':
					if (r.arr.result) {
						alert("실명인증 처리되었습니다.");
						$(obj).closest('td').html('인증완료<br>' + r.arr.actdate.substring(0,16));
					} else {
						alert("해당 정보를 찾을 수 없습니다.");
					}
					break;
				case 'CertNO':
					if (r.arr.result) {
						alert("인증거부 처리되었습니다.");
						$('tr[MemberSeq='+ r.arr.MemberSeq +']').find('.frontStatus').html('<a onClick=fAjax("/aadmin/memberCertListAjax","","ACT=GetCancelMsg&H_MemberSeq=' + r.arr.MemberSeq + '") style="color:#333">인증거부</a><br>' + r.arr.actdate.substring(0,16));
					} else {
						alert("해당 정보를 찾을 수 없습니다.");
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
						$("#AHealthFlag").text("양호함");
					} else { $("#AHealthFlag").text("건강이상"); }
					$("#AHealthDetail").text(r.arr.healthDetail ? "(" + r.arr.healthDetail + ")" : "");
					$("#ALikeField").text(r.arr.likeField);
					$("#ALikeFieldEtc").text(r.arr.likeFieldEtc ? ": " + r.arr.likeFieldEtc : "");
					fAddiInfoModal();
					break;
				case 'GetCancelMsg':
					$("#CancelMsgView").html(r.arr == "" ? "<i>내용없음</i>" : r.arr);
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
	html = html.replace('#actdate#', (item.actdate ? item.actdate.substr(0,16) : "미처리"));

	

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


//스크롤링
function fScrolling()
{
	if (!document.xhr) {
		$('#divProg').slideDown(300);
		setTimeout(fMakeRow, 1000);//너무 빨리 응답이 와도 비쥬얼이 좋지 않은 것 같아서 일정 시간 모래시계를 유지시킨다.

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
						vText = '인증완료';
						vDate = item.actdate.substr(0,16);
						break;
					case 3:
						vColor = '#db2828';
						vText = '<a onClick=fAjax("/aadmin/memberCertListAjax","","ACT=GetCancelMsg&H_MemberSeq=' + item.mmberSeq + '") style="color : #d9534f; font-weight:bold">인증거부</a>';
						vDate = item.actdate.substr(0,16);
						break;
					default:
						//vColor = '#fbbd08';
						//vText = 'Status : ' + item.certStatus;
						//vDate = '오류';
						chk = true;
						vColor = '#db2828';
						vText = '<a onClick=fAjax("/aadmin/memberCertListAjax","","ACT=CertOK&H_MemberSeq=' + item.mmberSeq + '") style="color : #d9534f; font-weight:bold">승인</a>';
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
			if (item.CertStatus == 1 && (item.actdate.date.substr(0,4) > '1970')){  // DB에서 actdate의 초기값이 NULL이 들어가지 않아 초기값을 1970년으로 판단
				Clone.find('.frontStatus').append('<br>거부내역 : ' + item.actdate.substr(0,16));
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

//데이터 클릭시 기능 구현 함수들
function fClickMemInfo(obj)
{
	var MemberSeq = $(obj).attr('MemberSeq');
	window.open('/aadmin/memberView?Seq=' + MemberSeq, 'UserInfo');
}

//모달 폼 스크립트

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

//신분증 확인
function fIDCardCheck(obj) {
	window.open("/aadmin/exchange/idCardView?MemberSeq=" + $(obj).closest('tr').attr('MemberSeq'));
}

//범용 fajax 함수
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
						<a href="/aadmin/memberList">[회원 관리]</a>
					</div>
				<% } %>
				<div class="title-name col-md-4">
					회원 실명인증 관리
				</div>
				<%if(CommonUtil.libhasAuthority(0, userSeq)) {%>
					<div class="title-name col-md-4">
						<a href="/aadmin/adminConfig">[관리자 설정]</a>
					</div>
				<% } %>
			</div>
            <form name="frm" class="filter-wrapper row" style="margin-top:60px;padding-top:8px;" autocomplete="off">
                <div class="row" style="position:relative;border-bottom: none;padding:5px;">
					<div class="col-xs-2"></div>
                    <div class="col-xs-10" style="text-align:right;">
						<span style="margin-left:50px">회원번호:</span>
						<input type="input" name="H_Seq" class="filter-flag form-control" style="margin-left:10px;padding:0 10px;width:100px;text-align:center" onKeyPress="if(event.keyCode == 13) fAjax('/aadmin/memberCertListAjax', 'frm', 'ACT=GetMemberCert&pg=1')">
						<span style="margin-left:50px">한 번에 불러올 ROW 수</span>
						<input type="input" name="H_MaxRow" class="filter-flag form-control" style="margin-left:10px;padding:0 10px;width:100px;text-align:center" value="${maxRow}">
						<input type="button" class="filter-flag form-control" value="검색" style="margin-left:50px;background-color:#286090;boder-color:#204d74;width:100px;color:#fff" onClick="fAjax('/aadmin/memberCertListAjax', 'frm', 'ACT=GetMemberCert&pg=1')">
                    </div>
                </div>
			</form>
			<div id="DataTable" style="margin-top:50px;display:none">
			<table class="table table-bordered table-hover">
				<tr class="data_header">
					<td width="130">회원번호</td>
					<td width="220">닉네임</td>
					<td width="120">이름</td>
					<td>신분증</td>
					<td>부가정보</td>
					<td width="150">신청일</td>
					<td width="230" style="vertical-align:middle;color:dodgerblue">처리 상황</td>
				</tr>
				<tr id="samDataRow" style="display:none">
					<td onClick="fClickMemInfo(this)" MemberSeq="#MemberSeq#"><a href="javascript:void(0)">#MemberSeq#</a></td>
					<td>#Nickname#</td>
					<td>#Name#</td>
					<td><input type="button" class="filter-flag form-control btnCheck" value="신분증 확인" onClick="fIDCardCheck(this)"></td>
					<td><input type="button" class="filter-flag form-control btnCheck" value="부가정보 확인" onClick="fAction('GetAddiInfo',this)"></td>
					<td>#regdate#</td>
					<td width="150" class="frontStatus">
						<input type="button" class="filter-flag form-control btnConfirm" value="승인" onClick="fAction('CertOK', this)">
						<input type="button" class="filter-flag form-control btnCancel" NickName="#NickName#" value="거부" onClick="fCancelMsgModal(this)">
					</td>
				</tr>				
			</table>
			</div>
			<div id="divProg" style="width:100%;display:none"><div style="width:30px;margin:auto"><img src="/Common/images/search_nick_loader.gif" style="width:100%"></div></div>
			<p id="NotExist" style="margin-top:50px;text-align:center;display:none">검색 조건에 만족하는 회원이 없습니다.</p>
        </div>
	</div>
	<!-- 부가정보 확인 Modal Form -->
	<div id="AddiInfoModal" class="modal">
		<div class="modal-content">
			<span class="close" onClick="AddiInfoModalClose()">&times;</span>
			<span class="AddiTitle">닉네임</span>: <span id="ANickName"></span><br>
			<span class="AddiTitle">직업</span>: <span id="AJob"></span><br>
			<span class="AddiTitle">거주 지역</span>: <span id="AArea"></span><br>
			<span class="AddiTitle">부동산</span>: <span id="ARealEstate"></span><br>
			<span class="AddiTitle">거주형태</span>: <span id="AResidence"></span><br>
			<span class="AddiTitle">결혼여부</span>: <span id="AMarriage"></span><br>
			<span class="AddiTitle">자녀</span>: <span id="AChildren"></span><br>		
			<span class="AddiTitle">자동차</span>: <span id="ACar"></span><br>
			<span class="AddiTitle">연 소득</span>: <span id="AYearIncome"></span><br>
			<span class="AddiTitle">건강상태</span>: <span id="AHealthFlag"></span> <span id="AHealthDetail"></span><br>
			<span class="AddiTitle">흥미분야</span>: <span id="ALikeField"></span> <span id="ALikeFieldEtc"></span>
		</div>
	</div>
	<!-- 인증 거부 메시지 입력 Modal Form -->
	<div id="CancelMsgModal" class="modal">
		<div class="modal-content">
			<span class="close" onClick="CancelMsgModalClose()">&times;</span>
			<form name="frm">
			<div><span id="CNickName" style="font-weight : bold"></span>님께 보여질 실명인증 거부 사유</div>
			<input type="checkbox" name="chk_IDCard" value="Y"> 신분증&nbsp;&nbsp;
			<input type="checkbox" name="chk_AddiInfo" value="Y"> 부가정보&nbsp;&nbsp;&nbsp; 
			<input type="input" name="H_actMessage" style="margin-left: 5px; padding:0 10px;width:50%;text-align:left" maxlength="100" placeholder="100자 이내로 입력해주세요."><input type="button" class="filter-flag form-control" style="margin-left:50px;background-color:#d9534f;boder-color:#d43f3a;width:100px;color:#fff" value="거부하기" MemberSeq="#MemberSeq#"  onClick="fAjax('/aadmin/memberCertListAjax', 'frm', 'ACT=CertNO&H_MemberSeq='+$('#CancelMsgModal').attr('MemberSeq'))">
			</form>
		</div>
	</div>
	<?//인증 거부 메시지 재확인 Modal Form ?>
	<div id="CancelMsgFindModal" class="modal">
		<div class="modal-content">
			<span class="close" onClick="CancelMsgFindModalClose()">&times;</span>
			거부 사유 : <span id="CancelMsgView" style="width:80%; overflow:auto; display:inline-block; vertical-align:bottom;"></span>
			</div>
	</div>
	<br><br>
</body>