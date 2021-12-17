<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %> 
<c:set var="userLv" value="${userLv}"/>
<c:set var="exchRate" value="${exchRate}"/>
<%
String userLv = String.valueOf(pageContext.getAttribute("userLv"));
BigDecimal exchRate = new BigDecimal(String.valueOf(pageContext.getAttribute("exchRate")));
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<!-- 상위 헤드 -->
<link rel="stylesheet" href="/pub/member/account/exchange/exchangeAsk/exchangeAsk.css?ver=1.2">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.4">
<script>
function fAjax_exch(url, frm, param) {
	if (document.xhr) {
		$('#Tip').text('<%=CommonUtil.getLangMsg(request, "msg_0207")%>').css('display', 'block');
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
				case 'EXCHANGE':
					switch (r.arr[0].ReturnCode)
					{
						case 1:
							location.replace('/member/account/exchange/exchangeResult');
							break;
						case 20:
							if (confirm('<%=CommonUtil.getLangMsg(request, "msg_0646")%>\n<%=CommonUtil.getLangMsg(request, "msg_0647")%>'))
								location = '/member/myInfo?tab=2';
							break;
						default:
							alert('<%=CommonUtil.getLangMsg(request, "msg_0648")%>\n\n<%=CommonUtil.getLangMsg(request, "msg_0649")%>');
							break;
					}
					break;
				default:
					if (r.result) alert(r.result);
					break;
			}
		},
		error: function (r, textStatus, err) {
			if (r.responseText && r.responseText.substr(0, 13) == '<!--LOGOFF-->') { top.location = '${MORMAL_SEND_URL}'; return; }
			console.log(r);
		},
		complete: function () {
			document.xhr = false;
		}
	});
}

function fSetNum()
{
	document.frm.H_RealMoney.value = Math.floor(document.frm.H_ExchangeAlmoney.value / 10 * (1 - ${exchRate_sub} ));
}
</script>
</head>
<body>
	<div id="Tip" style="position:absolute;top:0;width:100%;padding:10px;z-index:99;text-align:center;background-color:#fdd;display:none"></div>
	<%@ include file="/pub/menu/topMenu.jsp"%>

	<div id="atm_exAxk_wrapper">
	
		<div class="center">
		<!--center start -->
			<h1 class="atm_edittop_ttbar"><spring:message code="msg_0650"/></h1>
			<div class="contents-wrapper"><spring:message code="msg_0651"/></div>
			
			<article>
			<div class="contents-contain">
				<div class="account-top-wrapper">
					<p class="my_wallet"><img src="/Common/images/wallet.svg" alt='<spring:message code="msg_0652"/>'><spring:message code="msg_0652"/></p>
                     <p class="current-almoney">
                         <span class="almoney-value text_red">
							<fmt:formatNumber value="${userAlmoney}" pattern="#,###.#" />
                         </span>
                         <span class="almoney-unit"><spring:message code="msg_0136"/></span>
                     </p>
                 </div>
				
				<section class="exchange-status-section" style="margin-top:20px;">
					<div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title"><img src="/Common/images/exchange.svg" alt='<spring:message code="msg_0653"/>'><spring:message code="msg_0653"/></h3>
                        </div>
                        <div class="panel-body">
                            <div class="almoney-value-wrapper col-xs-8 col-sm-8 col-md-6 col-lg-6 col-xs-offset-2 col-sm-offset-2 col-md-offset-3 col-lg-offset-3">
                                <span id="exchange-limit-almoney" class="almoney-value text_red">
                                    <fmt:formatNumber value="${exchangeLimitAlmoney}" pattern="#,###" />
                                </span>
                                <span class="almoney-unit"><spring:message code="msg_0136"/></span>
                            </div>
                            <p>※ ${lvStr} <spring:message code="msg_0654"/><fmt:formatNumber value="${exchangeBaseDeductAlmoney}" pattern="#,###" /><spring:message code="msg_0655"/></p>
                            <p>※ ${lvStr} <spring:message code="msg_0656"/><fmt:formatNumber value="${maxExchange}" pattern="#,###" /><spring:message code="msg_0657"/></p>
                        </div>
                    </div>
				</section><!-- exchange-status-section end  -->
				<section class="account-form-section">
				<form name="frm">
				<div class="panel panel-default">
					<div class="panel-body">
                       <div class="exchange-almoney-field row">
							<div>
								<div class="col-sm-offset-1 col-md-offset-0 col-lg-offset-0 col-xs-9 col-sm-9 col-md-7 col-lg-7">
		                           <div class="col-xs-4 col-sm-4 col-md-2 col-lg-2">
		                               <label class="input-label"><spring:message code="msg_0658"/></label>
		                           </div>
		                           <input name="H_ExchangeAlmoney" class="form-control"
		                               step="10000" required="required" title="" 
		                               type="number" onKeyUp="fSetNum()" onChange="fSetNum()">
		                           <span><spring:message code="msg_0136"/></span>
		                           <div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
		                              <button type="button" class="btn btn-info" onClick="this.form.H_ExchangeAlmoney.value=${exchangeLimitAlmoney}; fSetNum();"><spring:message code="msg_0659"/>
		                                  <span class="hidden-xs hidden-sm"><spring:message code="msg_0660"/></span>
		                              </button>
		                           </div>
		                        </div>
							</div>                       
                       	
                           	<div>
                           		<div class="col-sm-offset-1 col-md-offset-0 col-lg-offset-0 col-xs-9 col-sm-9 col-md-7 col-lg-7">
	                           		<div class="col-xs-4 col-sm-4 col-md-2 col-lg-2">
		                               <label class="input-label"><spring:message code="msg_0661"/></label>
		                      		</div>
		                        	<input name="H_RealMoney" class="form-control" type="text" readonly>
		                            <span><spring:message code="msg_0662"/></span>
	                        	</div>
                           	</div>
                    	</div>
                	</div>
                </div>
				</section><!-- account-form-section end  -->
				<section class="exchange-charge-section">
					<div class="panel panel-warning">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <i class="material-icons">help_outline</i>
                                <spring:message code="msg_0663"/>
                            </h4>
                        </div>
                        <div class="panel-body">
						· <spring:message code="msg_0664"/>${exchRate}<spring:message code="msg_0665"/><br>
						<spring:message code="msg_0666"/>
                        </div>
                    </div>
				
                    <div class="panel panel-warning">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <i class="material-icons">help_outline</i>
                                <spring:message code="msg_0667"/>
                            </h4>
                        </div>
                        <div class="panel-body panel-body-row">
                            · <spring:message code="msg_0668"/>
                        </div>
                    </div>
                </section><!-- exchange-charge-section end -->
                
                <div class="row">
					<button type="button" onClick="fAjax_exch('/member/account/exchange/exchangeAskAjax', 'frm', 'ACT=EXCHANGE')" class="btn btn-success col-xs-4 col-xs-offset-4"><spring:message code="msg_0669"/></button>
                </div>
				</form>				
            </div><!-- contents-contain end -->
			</article><!-- 첫번째 article end  -->
			
		<!--center end -->
		</div><!-- atm_base_wrapper1 end -->
	
	</div><!-- atm_wrapper end -->

	<div id="top_btn">
        <a href="#">
            <span>
                <img src="/Common/images/top_btn_arrow.svg" alt="top_btn">
            </span>
        </a>
    </div>
</body>
</html>