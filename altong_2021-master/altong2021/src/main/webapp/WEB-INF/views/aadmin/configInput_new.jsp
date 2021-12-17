<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ include file="/Common/include/headerAdmin.jsp" %>
<%
CommonUtil.libIsAdmin(response, request, configInput_new, userSeq);
%>
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
	.lv_up input {width:90px;text-align:center}
	.data_header td {border: 1px solid #aaa !important;background-color:#eee;text-align:center}
	td {vertical-align:middle !important;}
</style>
<script>
function fAjax(url, frm, param) {
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
				case 'SetLvUp':
				case 'SetExch':
					alert('정상적으로 수정되었습니다.');
					location.reload();
					break;
				default:
					if (r.result) alert(r.result);
					break;
			}
		},
		error: function (r, textStatus, err) {
			if (r.responseText && r.responseText.substr(0, 13) == '<!--LOGOFF-->') {top.location = 'http://www.altong.com/'; return;}
			console.log(r);
		},
		complete: function () {
			if (!document.isShow) document.xhr = false;
		}
	});
}
</script> 
    <div id="wrapper">
        <div id="M_wrapper">
			<%@ include file="/Common/include/menuAdmin.jsp" %>
		</div>
        <div class="container-fluid border" style="border-bottom:none">
            <div class="title-wrapper row">
                <div class="title-name col-md-6">
                    환경 설정 (승천/환전 조건)
                </div>
            </div>
            <form name="frmLvUp" onSubmit="return false" class="filter-wrapper row" style="margin-top:60px;padding-top:8px;" autocomplete="off">
				<div class="col-xs-2" style="font-weight:bolder;font-size:18px;line-height:32px">승천 조건</div>
                <div class="search-condition-set" style="padding:5px;margin-top:40px">
					<table class="lv_up table table-bordered table-hover" style="margin:0;width:100%">
						<tr class="data_header">
							<td rowspan="2">레벨</td>
							<td rowspan="2">사진 등록</td>
							<td rowspan="2">보유 알</td>
							<td colspan="2">피추천인</td>
							<td colspan="7">스탬프 적용 정보</td>
							<td rowspan="2">교육</td>
						</tr>
						<tr class="data_header">
							<td>요구레벨</td>
							<td>인원수</td>
							<td>레벨 스탬프</td>
							<td>질문 등록</td>
							<td>답변 채택률(%)</td>
							<td>답변 등록</td>
							<td>답변 피채택</td>
							<td>답변 평가</td>
							<td>댓글</td>
						</tr>
						<c:forEach var="con" items="${conf}" varStatus="status">
						<tr align="center" <c:if test="${con.Lv == '0'}">style="display:none"</c:if>>
							<c:set var="oLv" value="${con.Lv}"/>
							<%
								int nLv = Integer.parseInt( String.valueOf( pageContext.getAttribute("oLv") ) );
								String lvNm = CommonUtil.getLevelName(nLv, request);
								pageContext.setAttribute("lvNm", lvNm);
							%>
							<td>${con.Lv} =&gt; (${con.Lv + 1}) (${lvNm})</td>
							<td><select name="H_Photo[]"><option value="Y">유</option><option value="N" <c:if test="${con.LvUpPhoto == 'N'}">selected</c:if>>무</option></select></td>
							<td><input type="input" name="H_BaseAlmoney[]" value="${con.LvUpBaseAlmoney * 1}"></td>
							<td><input type="input" name="H_LvUpRecmdLv_1[]" value="${con.LvUpRecmdLv_1}"></td>
							<td><input type="input" name="H_LvUpRecmdCnt_1[]" value="${con.LvUpRecmdCnt_1}"></td>
							<td><input type="input" name="H_Stamp[]" value="${con.LvUpStampCnt}"></td>
							<td><input type="input" name="H_QusReg[]" value="${con.LvUpQusRegCnt}"></td>
							<td><input type="input" name="H_QusChoiceRate[]" value="${con.LvUpQusChoiceRate}"></td>
							<td><input type="input" name="H_AnsReg[]" value="${con.LvUpAnsRegCnt}"></td>
							<td><input type="input" name="H_AnsChoiced[]" value="${con.LvUpAnsChoicedCnt}"></td>
							<td><input type="input" name="H_AnsEsti[]" value="${con.LvUpAnsEstiCnt}"></td>
							<td><input type="input" name="H_Reply[]" value="${con.LvUpReplyCnt}"></td>
							<td><input type="input" name="H_Education[]" value="${con.LvUpEducationCnt}"></td>
						</tr>
						</c:forEach>
					</table>
					<div style="padding:20px 0 0 0;text-align:right">
					<input type="button" class="filter-flag form-control" style="background-color:#286090;boder-color:#204d74;width:100px;color:#fff" value="저장" onClick="fAjax('/aadmin/configInputAjax', this.form.name, 'ACT=SetLvUp')">
					</div>
				</div>
			</form>
            <form name="frmExch" onSubmit="return false" class="filter-wrapper row" style="margin-top:60px;padding-top:8px;" autocomplete="off">
				<div class="col-xs-2" style="font-weight:bolder;font-size:18px;line-height:32px">환전 조건 (알 -> 알Pay)</div>
                <div class="search-condition-set" style="padding:5px;margin-top:40px">
					<table class="lv_up table table-bordered table-hover" style="margin:0;width:100%">
						<tr class="data_header">
							<td rowspan="2">레벨</td>
							<td rowspan="2">최소 유지 알</td>
							<td rowspan="2">최대 환전 알</td>
							<td rowspan="2">환전율 (%)</td>
							<td colspan="6">게이지 적용 정보</td>
						</tr>
						<tr class="data_header">
							<td>환전 게이지</td>
							<td>질문 등록</td>
							<td>답변 채택률(%)</td>
							<td>답변 등록</td>
							<td>답변 피채택</td>
							<td>답변 평가</td>
						</tr>
						<c:forEach var="con" items="${conf}" varStatus="status">
						<tr align="center" <c:if test="${con.Lv <= 1}">style="display:none"</c:if>>
							<c:set var="oLv2" value="${con.Lv}"/>
							<%
								int nLv2 = Integer.parseInt( String.valueOf( pageContext.getAttribute("oLv2") ) );
								String lvNm2 = CommonUtil.getLevelName(nLv2, request);
								pageContext.setAttribute("lvNm2", lvNm2);
							%>
							<td>${con.Lv} (${con.Lv + 1}) (${lvNm2})</td>
							<td><input type="input" name="H_BaseAlmoney[]" value="${con.ExchBaseAlmoney * 1}"></td>
							<td><input type="input" name="H_LimitAlmoney[]" value="${con.ExchLimitAlmoney * 1}"></td>
							<td><input type="input" name="H_AlmoneyTexRate[]" value="${con.ExchAlmoneyTexRate * 1}"></td>
							<td><input type="input" name="H_Stamp[]" value="${con.ExchStampCnt}"></td>
							<td><input type="input" name="H_QusReg[]" value="${con.ExchQusRegCnt}"></td>
							<td><input type="input" name="H_QusChoiceRate[]" value="${con.ExchQusChoiceRate}"></td>
							<td><input type="input" name="H_AnsReg[]" value="${con.ExchAnsRegCnt}"></td>
							<td><input type="input" name="H_AnsChoiced[]" value="${con.ExchAnsChoicedCnt}"></td>
							<td><input type="input" name="H_AnsEsti[]" value="${con.LvUpAnsEstiCnt}"></td>
						</tr>
						</c:forEach>
					</table>
					<div style="padding:20px 0 0 0;text-align:right">
					<input type="button" class="filter-flag form-control" style="background-color:#286090;boder-color:#204d74;width:100px;color:#fff" value="저장" onClick="fAjax('/aadmin/configInputAjax', this.form.name, 'ACT=SetExch')">
					</div>
				</div>
			</form>
        </div>
    </div>
	<br><br>
</body>
</html>
