<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.altong.web.logic.util.CommonUtil"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<c:set var="exchangeLimitAlpay" value="${exchangeLimitAlpay}"/>
<c:set var="exchRate" value="${exchRate}"/>
<c:set var="amountAlpay" value="${(exchangeLimitAlpay / 1000) * 1000}"/>
<c:set var="realAmountAlpay" value="${((exchangeLimitAlpay / 1000) * 1000 * (1 - exchRate))}"/>
<html>
<head>
<link rel="stylesheet" href="/Common/CSS_new/exchange.css?ver=1.0">
<link rel="stylesheet" href="/Common/CSS_new/mediaQuery.css?ver=1.1">
<script>
function fAjax_exch(url, exchange_frm, param, obj) {
	if (document.exchange_xhr) {
		$('#Tip').text(getLangStr("jsm_0031")).css('display', 'block');
		setTimeout(function () { $('#Tip').stop().fadeOut(300) }, 3000);
		return;
	}
	
	document.exchange_xhr = $.ajax({
		type: 'post',
		url: url,
		data: (exchange_frm ? $('form[name=' + exchange_frm + ']').serialize() + '&' : '') + param,
		dataType: 'json',
		crossDomain: true,
		success: function (r) {
			switch (r.result) {
				case 'GetLastAccount':
					{
						$('#BankName').val(r.arr.BankName);
						$('#BankAccountNo').val(r.arr.BankAccountNo);
						$('#BankMemNm').val(r.arr.BankMemNm);
						break;
					}
				case 'EXCHANGE':
					{
						alert(getLangStr("jsm_0121"));
						location = '/default/main';
						break;
					}
				default:
					if (r.result) alert(r.result);
					break;
			}
		},
		error: function(r, textStatus, err){
			if (r.responseText && r.responseText.substr(0,13) == '<!--LOGOFF-->') {top.location='/';return;}
			if (r.statusText && r.statusText == 'abort') return;
			if (!err) return;
			alert(getLangStr("jsm_0122"));

			var str = '';
			for (var key in r) str += key + '=' + r[key] + '\n';
			//console.log(str);
		},
		complete: function () { document.exchange_xhr = false; }
	});
}
function onAllMoneyBtnClick() {	
	$('#AmountAlpay').val( parseInt( ${amountAlpay}, 10) );
	$('#RealAmountAlpay').val( parseInt( ${realAmountAlpay}, 10) );
}
function fSetNum()
{
	document.exchange_frm.RealAmountAlpay.value = Math.floor(document.exchange_frm.AmountAlpay.value * (1 - ${exchRate}) );
}
</script>
<link rel="stylesheet" type="text/css" href="https://cdn.rawgit.com/rikmms/progress-bar-4-axios/0a3acf92/dist/nprogress.css"/>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
</head>
<body>
<div id="Tip" style="position:absolute;top:0;width:100%;padding:10px;z-index:99;text-align:center;background-color:#fdd;display:none"></div>
<%@ include file="/pub/menu/topMenu.jsp"%>
<div id="exchange_wrap">
	<div class="center">
		<h1><spring:message code="msg_0672"/></h1>
		<section class="pay_Exch">
			<div class="non_scroll">
				<section class="alpay-status-section">
					<div class="alpay-status">
						<p class="my_wallet"><img src="/Common/images/wallet.svg" alt='<spring:message code="msg_0751"/>'><spring:message code="msg_0751"/></p>
						<p class="current-almoney">
							<span class="almoney-value text_red"><fmt:formatNumber value="${exchangeLimitAlpay}" pattern="#,###" /></span>
							<span class="almoney-unit"><spring:message code="msg_0136"/></span>
						</p>
					</div>		
				</section>
				<section class="account-form-section">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">
								<spring:message code="msg_1161"/>
								<c:if test="${ userSeq ne 0}">
									<button type="button" onClick="fAjax_exch('/alpay/user/sub/exchangeAjax', '', 'ACT=GetLastAccount')" class="btn btn-default"><spring:message code="msg_1162"/></button>
								</c:if>
								<c:if test="${ userSeq eq 0 }">
									<button type="button" onClick="alert('<spring:message code="msg_0168"/>');" class="btn btn-default"><spring:message code="msg_1162"/></button>
								</c:if>
							</h3>
						</div>
						<form name="exchange_frm">
						<div class="panel-body">
							<div class="account-form input-group-horizion">
								<div class="account-form-item row">
									<div class="account-form-item-con">
										<div class="col-xs-4 col-sm-4 col-md-2 col-lg-2">
	                                        <label class="input-label"><spring:message code="msg_1163"/></label>
	                                    </div>
										<div class="col-sm-offset-1 col-md-offset-0 col-lg-offset-0 col-xs-12 col-sm-10 col-md-10 col-lg-10">
											<select class="form-control" required="required" name="BankName" id="BankName">
												<option value="" readonly><spring:message code="msg_0118"/></option>
												<%
												String[] bank = {CommonUtil.getLangMsg(request, "msg_0973"), CommonUtil.getLangMsg(request, "msg_0974"), CommonUtil.getLangMsg(request, "msg_0975"), CommonUtil.getLangMsg(request, "msg_0976"), CommonUtil.getLangMsg(request, "msg_0977"), CommonUtil.getLangMsg(request, "msg_0978"), CommonUtil.getLangMsg(request, "msg_0979"), CommonUtil.getLangMsg(request, "msg_0980"), CommonUtil.getLangMsg(request, "msg_0981"), CommonUtil.getLangMsg(request, "msg_0982"), CommonUtil.getLangMsg(request, "msg_0983"), CommonUtil.getLangMsg(request, "msg_0984"), CommonUtil.getLangMsg(request, "msg_0985"), CommonUtil.getLangMsg(request, "msg_0986"), CommonUtil.getLangMsg(request, "msg_0987"), CommonUtil.getLangMsg(request, "msg_0988"), CommonUtil.getLangMsg(request, "msg_0989"), CommonUtil.getLangMsg(request, "msg_0990"), CommonUtil.getLangMsg(request, "msg_0991"), CommonUtil.getLangMsg(request, "msg_0992"), CommonUtil.getLangMsg(request, "msg_0993"), CommonUtil.getLangMsg(request, "msg_0994")};
												for(String v : bank) {
												%>
													<option value="<%=v%>"><%=v%></option>
												<%}%>
											</select>
										</div>
									</div>
								</div>
								<div class="account-form-item row">
									<div class="account-form-item-con">
										<div class="col-sm-offset-1 col-md-offset-0 col-lg-offset-0 col-xs-12 col-sm-10 col-md-10 col-lg-10">
											<div class="col-xs-4 col-sm-4 col-md-2 col-lg-2">
												<p class="input-label"><spring:message code="msg_1164"/></p>
											</div>
											<input type="text" name="BankMemNm" id="BankMemNm" class="form-control field" value="">
										</div>
									</div>
								</div>
								<div class="account-form-item row">
									<div class="account-form-itemDiv">
										<label class="col-xs-4 col-sm-4 col-md-2 col-lg-2">
											<p class="input-label"><spring:message code="msg_1165"/></p>
										</label>
										<div class="col-sm-offset-1 col-md-offset-0 col-lg-offset-0 col-xs-12 col-sm-10 col-md-10 col-lg-10">
											<input type="text" name="BankAccountNo" id="BankAccountNo" class="form-control field" placeholder='<spring:message code="msg_1166"/>'>
										</div>
									</div>
								</div>
							</div>
							<div class="exchange-alpay-field row">
								<div>
									<div class="col-sm-offset-1 col-md-offset-0 col-lg-offset-0 col-xs-9 col-sm-9 col-md-7 col-lg-7">
										<div class="col-xs-4 col-sm-4 col-md-2 col-lg-2">
											<label class="input-label"><spring:message code="msg_1167"/></label>
										</div>
										<input id="AmountAlpay" name="H_AmountAlpay" onKeyUp="fSetNum()" class="form-control" step="1000" type="number">
										<span><spring:message code="msg_0662"/></span>	
										<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
											<button type="button" class="btn btn-info" onclick="onAllMoneyBtnClick()"><spring:message code="msg_0659"/>
                                                <span class="hidden-xs hidden-sm"><spring:message code="msg_1006"/></span>
                                            </button>		
										</div>
									</div>
								</div>
								
								<div>
									<div class="col-sm-offset-1 col-md-offset-0 col-lg-offset-0 col-xs-9 col-sm-9 col-md-7 col-lg-7">
										<div class="col-xs-4 col-sm-4 col-md-2 col-lg-2">
											<label class="input-label"><spring:message code="msg_1168"/></label>
										</div>
										<input class="form-control" id="RealAmountAlpay" type="text" readonly>
										<span><spring:message code="msg_0662"/></span>
									</div>
								</div>
							</div>
						</div>
					</div>
				</section>
				<section class="exchange-charge-section">
					<div class="panel panel-warning">
						<div class="panel-heading">
							<h4 class="panel-title">
								<i class="material-icons">help_outline</i>
								<spring:message code="msg_1169"/>
							</h4>
						</div>
						<div class="panel-body">
							· <spring:message code="msg_1170"/><br><br>
						</div>
						<div class="panel-heading">
							<h4 class="panel-title">
								<i class="material-icons">help_outline</i>
								<spring:message code="msg_1171"/>
							</h4>
						</div>
						<div class="panel-body">
							· <spring:message code="msg_1172"/><br>
                            &nbsp;&nbsp;&nbsp;<spring:message code="msg_1173"/><br>
                            · <spring:message code="msg_1174"/><br><br>
                            · <spring:message code="msg_1175"/><span><a href="/default/userGuide?view=2&go=faq5"><spring:message code="msg_0184"/></a></span><spring:message code="msg_1176"/>
						</div>
					</div>
				</section>
				<div class="row" style="margin-bottom: 20px;">
					<c:if test="${ userSeq eq 0}">
						<button type="button" onClick="<spring:message code="msg_0168"/>" class="btn btn-success col-xs-4 col-xs-offset-4"><spring:message code="msg_1177"/></button>
					</c:if>
					<c:if test="${ userSeq ne 0 }">
						<button type="button" onClick="fAjax_exch('/alpay/user/sub/exchangeAjax', 'exchange_frm', 'ACT=EXCHANGE')" class="btn btn-success col-xs-4 col-xs-offset-4"><spring:message code="msg_1177"/></button>
					</c:if>
				</div>
				</form>
			</div>
		</section>
	</div>
</div>	
</body>