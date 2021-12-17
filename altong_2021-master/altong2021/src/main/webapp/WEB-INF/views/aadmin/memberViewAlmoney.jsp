<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/Common/include/headerAdmin.jsp" %>
<body>
<script>
function fAjax(url, frm, param) {
	if (document.xhr) {
		$('#Tip').text('이전 작업이 진행중입니다.').css('display', 'block');
		setTimeout(function () { $('#Tip').stop().fadeOut(300) }, 3000);
		return;
	}
	var seq = '${seq}';
	document.xhr = $.ajax({
		type: 'post',
		url: url,
		data: (frm ? $('form[name=' + frm + ']').serialize() + '&' : '') + param,
		dataType: 'json',
		crossDomain: true,
		success: function (r) {
			//console.log('result : ' + r.arr.result);//
			if (r.arr.msg) alert(r.arr.msg);

			switch (r.result) {
				case 'UpdateAlmoney':
					if (r.arr.result) {
						alert("알머니가 지급되었습니다.");
						location = '/aadmin/memberView?Seq=' + seq;
					}
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
	<div id="header_wrapper">
	</div>
	<div id="M_wrapper">
		<%@ include file="/Common/include/menuAdmin.jsp" %>
		<br>
		
		<div id="list">
			<form name="frm" method="post" onSubmit="return false;">

            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="rowboardList_top">
              	<tr>
               		<td height="50"><b><font size="4">${mame}</b>님께 알머니 지급</font></td>
				</tr>
					<tr>
						<td colspan="4" BORDER="0">&nbsp;</td>
					</tr>
				<tr>
                  <td width="20%" bgcolor="#efefef">회원번호</td>
                  <td width="30%"><input type="text" name="Seq" value="${seq}" class="input_textarea" maxlength="30" style="width:200px; background-color:#EEEEEE;" READONLY></td>
                  <td bgcolor="#efefef">지급할 알머니</td>
				<td>
					<input type="text" name="Almoney" value="0" class="input_textarea" maxlength="10" style="width:200px;" >
					<select name='AlmoneyFlag'>
						<option value='14'>이벤트</option>
						<option value='41'>환전 출금</option>
						<option value='12'>Answerer</option>
						<option value='91'>기타</option>
						<option value='51'>관리자차감</option>
					</select>
				</td>
            </table>
            <br><br>
            <p align="center">
                <button type="button" class="btn btn-default" onClick="fAjax('/aadmin/memberViewAlmoneyAjax', 'frm', 'ACT=UpdateAlmoney')">지급하기</button>
            </p>
        </form>

  		</div>
	</div>
</div>
</body>