<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/Common/include/headerAdmin.jsp" %>
<body>
	<div id="wrapper">
		<div id="header_wrapper">
		</div>
		<div id="M_wrapper">
			<%@ include file="/Common/include/menuAdmin.jsp" %>
			<br>
			<div id="list">
				<form name="frm_sch" method="post" onSubmit="return false;" enctype="multipart/form-data">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="rowboardList_top">
						<tr>
							<td height="50" colspan="2">&nbsp;</td>
						</tr>
						<tr>
							<td width="25%" bgcolor="#efefef">광고이미지</td>
							<td width="40%" colspan="3">
								<img src="/UploadFile/AD/${adInfo.adFile}" style="width:400px;">&nbsp;
							</td>
						</tr>
						<tr>
							<td bgcolor="#efefef">카테고리</td>
							<td>
								<input type="text" name="Section1Name" value="<c:choose><c:when test="${adInfo.section1 < 1}">전체</c:when><c:otherwise>${sect.get(adInfo.section1)}</c:otherwise></c:choose>" class="input_textarea" maxlength="20" style="width:200px;"
								 readonly>
								<input type="hidden" name="Section1" value="${adInfo.section1}" readonly>
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td bgcolor="#efefef">종류</td>
							<td>
								<input type="text" name="ADFileExt" value="${adInfo.adFileExt}" class="input_textarea" maxlength="10" style="width:200px;">
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td bgcolor="#efefef">게시일</td>
							<td>
								<input type="text" name="PeriodStart" value="${adInfo.periodStart}" class="input_textarea" maxlength="10" style="width:200px;">
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td bgcolor="#efefef">종료일</td>
							<td>
								<input type="text" name="PeriodEnd" value="${adInfo.periodEnd}" class="input_textarea" maxlength="10" style="width:200px;">
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td bgcolor="#efefef">사용여부</td>
							<td>
								<input type="text" name="FlagUse" value="${adInfo.flagUse}" class="input_textarea" maxlength="10" style="width:200px;">
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td bgcolor="#efefef">Url</td>
							<td>
								<input type="text" name="Url" value="${adInfo.url}" class="input_textarea" style="width:200px;">
							</td>
							<td>&nbsp;</td>
						</tr>
					</table>
					<br>
					<br>
					<p align="center">
						<button type="button" class="btn btn-default" onClick="javascript:goSubmit('frm_sch','/aadmin/adEdit?Seq=${adInfo.seq}','ifrm');">수정하기</button>
					</p>
			</div>
		</div>
	</div>
	</form>
	<iframe name="ifrm" width="100%" height="0" frameborder="0"></iframe>
</body>