<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/Common/include/headerAdmin.jsp" %>
<body style="overflow-y:scroll">
<script>
var authority_Cnt = '${authority_Cnt}';
if(authority_Cnt == '') { authority_Cnt = 0; }

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
				case 'GetAdminInfo':
					document.r = r;
					fMakeRow();
					break;
				case 'AddAdmin':
					if (r.arr) {
						alert("관리자가 추가되었습니다.");
						location.reload();
					} else {
						alert("해당 정보를 찾을 수 없습니다.");
					}
					break;
				case 'AuthorityModify':
					var j = 0;
					for (i = 0; i < authority_Cnt; i++){
						if (r.arr[i] == 'true') {
							$("input[name='chk_Authority[]']:eq(" + i + ")").prop('checked', true);
							j++;
						}
					}
					
					if (j == authority_Cnt)
						$('input[type=checkbox]:eq(0)').prop('checked', true);


					break;
				case 'AuthorityModifyOK':
					if (r.arr) {
						alert("권한이 수정되었습니다.");
						location.reload();
					} else {
						alert("해당 정보를 찾을 수 없습니다.");
					}
					break;
				case 'DeleteAdmin':
					if (r.arr) {
						alert("관리자가 삭제되었습니다.");
						location.reload();
					} else {
						alert("해당 정보를 찾을 수 없습니다.");
					}
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
	var hasAuthority = "";
	var Cnt = 0;

	html = html.replace(/#MemberSeq#/gi, item.memberSeq);
	html = html.replace(/#Nickname#/gi, item.nickName);
	html = html.replace('#Name#', item.name);

	if (item.authority == "*") { html = html.replace('#Authority#', "슈퍼 관리자"); }
	else {
		for (i = 0; i < authority_Cnt; i++)
			if (item.authority & Math.pow(2, i))  Cnt++;
		
		html = html.replace('#Authority#', Cnt + '/' + authority_Cnt + '개');
	}	

	return html;
}


function fMakeRow()
{
	
	if (document.r.arr.rows.length)
	{
		var Clone;

		$.each(document.r.arr.rows, function(index, item){
			Clone = $('<tr class="DataRow" align="center" MemberSeq="' + item.memberSeq + '">' + fMakeDataHTML(item) + '</tr>');
			Clone.appendTo('#DataTable table');
		});
	}

};

//전체선택
function fAllChecked(v) {
	$('input[type=checkbox]').prop('checked', v);
}


//스크롤링
function fScrolling()
{
	if (!document.xhr) {
		$('#divProg').slideDown(300);
		setTimeout(fMakeRow, 1000);//너무 빨리 응답이 와도 비쥬얼이 좋지 않은 것 같아서 일정 시간 모래시계를 유지시킨다.

		fAjax('/aadmin/adminConfigAjax', 'frm', 'ACT=GetAdminInfo&pg=' + (document.pg + 1));
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


//모달 폼 스크립트

function AdminModifyModalClose() {
	$('input[type=checkbox]').prop('checked', false);
	$('#AdminModifyModal').hide();
}

window.onclick = function(event) {
	if (event.target == document.getElementById('AdminModifyModal')) {
		AdminModifyModalClose();
	}
}

function fAdminModifyModal(obj)
{
	$('#CNickName').text($(obj).attr('NickName'));
	var ms = $(obj).attr('MemberSeq');
	fAjax('/aadmin/adminConfigAjax','','ACT=AuthorityModify&H_MemberSeq=' + ms);
	$('#AdminModifyModal').attr('MemberSeq', ms).show();
}
</script> 
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

	.filter-flag.form-control.btnConfirm {
		color: #fff;
		background-color: #5cb85c;
		border-color: #4cae4c;
		width : 80px;
		margin : 3px;
	}

	.filter-flag.form-control.btnCancel {
		color: #fff;
		background-color: #d9534f;
		border-color: #d43f3a;
		width : 60px;
		margin : 3px;
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
		top : 200px;
		left : 10%;
		background-color: #fefefe;
		margin: 15% auto; 
		padding: 20px;
		border: 1px solid #888;
		width: 80%; 
		text-align : left;
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

	.status-btn .complete {
		color: #ffffff primary;
	}

	input[type="checkbox"] {margin-left : 15px;}

</style>
<style>
	.data_header td {border: 1px solid #aaa !important;background-color:#eee;text-align:center}
	td {vertical-align:middle !important;}
</style> 
    <div id="wrapper">
        <div id="M_wrapper">
			<%@ include file="/Common/include/menuAdmin.jsp" %>
        </div>
        <div class="container-fluid border" style="border-bottom:none">
			<div class="title-wrapper row">
				<div class="title-name col-md-4">
					<a href="/aadmin/memberList">[회원 관리]</a>
				</div>
				<div class="title-name col-md-4">
					<a href="/aadmin/memberCertList">[회원 실명인증 관리]</a>
				</div>
				<div class="title-name col-md-4">관리자 설정</div>
			</div>
			<form name="frmAddAdmin">
				<div style="width:100%; padding-top:10px;">
					<div style="text-align:right;">
						<input type="input" name="H_AddAdminSeq" class="filter-flag form-control" style="margin-left:10px;padding:0 10px;width:100px;text-align:center" placeholder="회원번호">
						<input type="button" class="filter-flag form-control" value="관리자 추가" style="background-color:#286090;boder-color:#204d74;width:100px;color:#fff" onClick="fAjax('/aadmin/adminConfigAjax', 'frmAddAdmin', 'ACT=AddAdmin')">
					</div>
				</div>
			</form>
			<form name="frm" class="filter-wrapper row" style="margin-top:10px;padding-top:8px;" autocomplete="off">
                <div class="row" style="position:relative;border-bottom: none;padding:5px;">
					<div class="col-xs-2"></div>
                    <div class="col-xs-10" style="text-align:right;">
						<span style="margin-left:50px">한 번에 불러올 ROW 수</span>
						<input type="input" name="H_MaxRow" class="filter-flag form-control" style="margin-left:10px;padding:0 10px;width:100px;text-align:center" value="${maxRow}">
						<input type="button" class="filter-flag form-control" value="검색" style="margin-left:50px;background-color:#286090;boder-color:#204d74;width:100px;color:#fff" onClick="fAjax('/aadmin/adminConfigAjax', 'frm', 'ACT=GetAdminInfo&pg=1')">
                    </div>
                </div>
			</form>
			<div id="DataTable" style="margin-top:50px;display:none">
				<table class="table table-bordered table-hover">
					<tr class="data_header">
						<td width="130">회원번호</td>
						<td width="220">닉네임</td>
						<td width="120">이름</td>
						<td>권한</td>
						<td width="100" style="vertical-align:middle;color:dodgerblue">권한 수정</td>
					</tr>
					<tr id="samDataRow" style="display:none">
						<td>#MemberSeq#</td>
						<td>#Nickname#</td>
						<td>#Name#</td>
						<td>#Authority#</td>
						<td width="100" class="frontStatus">
							<input type="button" class="filter-flag form-control btnConfirm" value="권한 수정" NickName="#NickName#" MemberSeq="#MemberSeq#" onClick="fAdminModifyModal(this)">
						</td>
					</tr>
				</table>
			</div>
        </div>
			<div id="divProg" style="width:100%;display:none"><div style="width:30px;margin:auto"><img src="/Common/images/search_nick_loader.gif" style="width:100%"></div></div>
			<p id="NotExist" style="margin-top:50px;text-align:center;display:none">검색 조건에 만족하는 회원이 없습니다.</p>
        </div>
    </div>
	<%//권한 수정 Modal Form %>
	<div id="AdminModifyModal" class="modal">
		<div class="modal-content">
			<span class="close" onClick="AdminModifyModalClose()">&times;</span>
			<form name="frm">
			<div><span id="CNickName" style="font-weight : bold"></span>님의 관리자 권한 
			<input type="button" class="filter-flag form-control" style="margin:10px;background-color:#5cb85c;boder-color:#4cae4c;width:100px;color:#fff" value="수정하기" onClick="fAjax('/aadmin/adminConfigAjax', 'frm', 'ACT=AuthorityModifyOK&H_MemberSeq='+$('#AdminModifyModal').attr('MemberSeq'))">
			<input type="button" class="filter-flag form-control" style="margin:10px;background-color:#d9534f;boder-color:#d43f3a;width:100px;color:#fff" value="관리자 삭제" onClick="fAjax('/aadmin/adminConfigAjax', 'frm', 'ACT=DeleteAdmin&H_MemberSeq='+$('#AdminModifyModal').attr('MemberSeq'))">
			
			</div>			
			<input type="checkbox" onClick="fAllChecked(this.checked)" style="font-weight:bold"> 전체선택<br>
			<input type="checkbox" name="chk_Authority[]" value="0"> 환경설정
			<input type="checkbox" name="chk_Authority[]" value="1"> 환경설정/신규승천시스템<br>
			<input type="checkbox" name="chk_Authority[]" value="2"> 회원관리
			<input type="checkbox" name="chk_Authority[]" value="3"> 회원관리/실명인증관리<br>
			<input type="checkbox" name="chk_Authority[]" value="4"> 추천인관리<br>
			<input type="checkbox" name="chk_Authority[]" value="5"> 출금관리
			<input type="checkbox" name="chk_Authority[]" value="6"> 출금관리/신규출금시스템
			<input type="checkbox" name="chk_Authority[]" value="7"> 출금관리/알페이전환Log
			<input type="checkbox" name="chk_Authority[]" value="8"> 출금관리/알페이환전관리<br>
			<input type="checkbox" name="chk_Authority[]" value="9"> 카테고리관리<br>
			<input type="checkbox" name="chk_Authority[]" value="10"> 이벤트관리<br>
			<input type="checkbox" name="chk_Authority[]" value="11"> 광고관리<br>
			<input type="checkbox" name="chk_Authority[]" value="12"> 알머니현황<br>
			<input type="checkbox" name="chk_Authority[]" value="13"> 회원레벨관리/레벨별승천대기현황
			<input type="checkbox" name="chk_Authority[]" value="14"> 회원레벨관리/신규승천시스템<br>
			<input type="checkbox" name="chk_Authority[]" value="15"> 공지사항<br>
			<input type="checkbox" name="chk_Authority[]" value="16"> 신고
			<input type="checkbox" name="chk_Authority[]" value="17"> 신고/블랙리스트<br>
			<input type="checkbox" name="chk_Authority[]" value="18"> 알맹이/입력
			<input type="checkbox" name="chk_Authority[]" value="19"> 알맹이/선입금<br>
			<input type="checkbox" name="chk_Authority[]" value="20"> 회원정보수정/탈퇴
			<input type="checkbox" name="chk_Authority[]" value="21"> 회원정보/레벨변경
			<input type="checkbox" name="chk_Authority[]" value="22"> 회원정보/알머니지급
			</form>
		</div>
	</div>
	<br><br>
</body>