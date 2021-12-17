<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<c:set var="flagNice" value="${flagNice}"/>
<c:set var="flagIdCard" value="${flagIdCard}"/>
<c:set var="flagAdInfo" value="${flagAdInfo}"/>
<c:set var="certStatus" value="${certStatus}"/>
<c:set var="adminMessage" value="${adminMessage}"/>
<%
String flagNice = String.valueOf( pageContext.getAttribute("flagNice") );
String flagIdCard = String.valueOf( pageContext.getAttribute("flagIdCard") );
String flagAdInfo = String.valueOf( pageContext.getAttribute("flagAdInfo") );
int certStatus = Integer.parseInt( String.valueOf( pageContext.getAttribute("certStatus") ) );
String adminMessage = String.valueOf( pageContext.getAttribute("adminMessage") );
%>
<head>
<link rel="stylesheet" href="/pub/member/cert/index/cert.css?ver=1.4">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.0">
<link rel="stylesheet" href="/pub/css/lang_cert.css?ver=1.1">
<style>
#ad-info-save-btn2 {
  width: 100%;
  border: 1px solid #c4c4c4;
  background: #fff;
  font-size: 16px;
  font-weight: bold;
  color: #666;
  padding: 4px 0;
  border-radius: 20px;
  margin: 40px 0;
  cursor: pointer;
  transition: all 0.3s;
}
</style>
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<link
  rel="stylesheet"
  href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
/>
<script src="/Common/js/polyfill.min.js?2019-04-26-2"></script>
<script language='javascript'>
window.name ="Parent_window";

function fnPopup(){
	window.open('', 'popupChk', 'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
	document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";
	document.form_chk.target = "popupChk";
	document.form_chk.submit();
}

$(function(){
	var flagNice = '${flagNice}';
	var message = '${message}';
	var errorFlag = '${errorFlag}';
	var certFlag = '${certFlag}';
	var act = '${act}';
	
	if(message != '') {
		alert(message);
	}
	//console.log('act : ' + act);
	
	//message = '';
	if(act == 'SUCCESS_NICE_CERT') {
		var childWindow = window.parent;
		var parentWindow = childWindow.opener;
		parentWindow.location.href = '/member/cert/index'
		
		self.close();
	}
});

function step1_proc() {
	var eCount = '${eCount}';
	var nCount = '${nCount}';
	
	var intEcount = parseInt(eCount);
	var intNcount = parseInt(nCount);
	
	var userName = $('#userName').val();
	var nickName = $('#nickName').val();
	var h_nickName = $('#h_nickName').val();
	if(nickName == h_nickName) { $('#FlagNickName').val('Y'); }
	var FlagNickName = $('#FlagNickName').val();
	
	var joinNation = $('#joinNation option:selected').val();
	
	if(userName == '') {
		alter(getLangStr("jsm_0072"));
		$('#userName').focus();
		return false;
	}
	if(nickName == '') {
		alter(getLangStr("jsm_0037"));
		$('#nickName').focus();
		return false;
	}
	if(FlagNickName != 'Y') {
		alert(getLangStr("jsm_0073"));
		$('#nickName').focus();
		return false;
	}
	if(joinNation == "") {
		alert(getLangStr("jsm_0074"));
		$('#joinNation').focus();
		return false;
	}
	
	if(intEcount > 0 && intNcount > 0) {
		alert(getLangStr("jsm_0075"));
		return false;
	}
	
	if(confirm(getLangStr("jsm_0076"))) {
		$.ajax({
			url: "/member/cert/certUserInfoProc",
			type: "post",
			data: new FormData($('form[name="firstFrm"]')[0]),
			processData: false,
			contentType: false,
			async: false,
			success: function (data, textStatus, jqXHR) {
				alert(getLangStr("jsm_0075"));
				location.reload();
			},
			error: function (request, err, ex) {
				alert(err + " ===> " + ex);
			}
		});
	}
	return false;
}

function certChk3() {
	var form = document.firstFrm;
	form.target = 'join_ifrm';
	form.action = '/default/nickNameCheck_n4';
	form.submit();		
}
</script>
<div class="container-fluid contents-wrapper">
	<div class="center">
		<div class="atm_edittop_ttbar">
			<h1 class="atm_edittop_c1"><spring:message code="msg_0759"/></h1><!-- 본인 인증 -->
		</div>
		
		
		<% if (certStatus == 3) { %>
		<div class="alert alert-danger" role="alert">
			<%= adminMessage %>
		</div>
		<% } %>
		<% if (flagIdCard.equals("true") && flagAdInfo.equals("true") && certStatus == 1) { //flagNice.equals("true") && %>
		<div class="alert alert-warning" role="alert">
			<spring:message code="msg_0760"/><br />
			<spring:message code="msg_0761"/>
		</div>
		<% } %>
		<div class="col-sm-12">
			<%--
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">1단계: NICE 본인 인증</h3>
				</div>
				<div class="panel-body">
					<p>실제 본인 여부를 확인하기 위한 절차입니다.</p>
					<p>입력하신 전화번호의 휴대폰으로 인증번호가 발신됩니다.</p>
					<div id="nice-cert-wrapper">
						<% if (flagNice.equals("true")) { %>
						<div class="alert alert-success" role="alert">
							본인 인증이 완료 되었습니다.
						</div>
						<%} else { %>
							<button
								onclick="fnPopup();"
								class="btn btn-default col-xs-4 col-xs-offset-4">
								본인 인증
							</button>
						<% } %>
					</div>
				</div>
			</div>
			--%>
			<c:set var="FlagNickName" value="N" />
			<c:if test="${nCount > 0}">
			<c:set var="FlagNickName" value="Y" />
			</c:if>
			<div class="panel panel-default">
	            <div class="panel-heading">
	                <h3 class="panel-title"><spring:message code="msg_0762"/></h3>
	            </div>
	            <form name="firstFrm" action="">
	            	<input type="hidden" name="h_nickName" id="h_nickName" value="${nickName}" />
	            	<input type="hidden" name="FlagNickName" id="FlagNickName" value="${FlagNickName}" />
	                <div class="lang_name">
	                    <h5><spring:message code="msg_0763"/></h5>
	                    <input type="text" name="userName" id="userName" value="${userName}" <c:if test="${userName != '' and userName != null}">readonly</c:if>>
	                    <p><spring:message code="msg_0764"/></p>
	                </div>
	                <div class="lang_nick">
	                    <h5><spring:message code="msg_0310"/></h5>
	                    <div>
	                        <input type="text" name="nickName" id="nickName" value="${nickName}" <c:if test="${nCount > 0}">readonly</c:if>>
	                        <c:if test="${nCount < 1}">
	                        <button onclick="javascript:certChk3();"><spring:message code="msg_0404"/></button>
	                        </c:if>
	                        <c:if test="${nCount > 0}">
	                        <button onclick="javascript:void(0);"><spring:message code="msg_0404"/></button>
	                        </c:if>
	                    </div>
	                    <p><spring:message code="msg_0765"/></p>
	                </div>
	                <div class="lang_nation">
	                    <h5>국적</h5>
	                    <select name="nation" id="joinNation" <c:if test="${eCount > 0}">disabled</c:if>>
	                        <option value="" <c:if test="${nation == ''}">selected="selected"</c:if>>-- select one --</option>
							<option value="AFG" <c:if test="${nation == 'AFG'}">selected="selected"</c:if>>افغانستان</option>
							<option value="ALB" <c:if test="${nation == 'ALB'}">selected="selected"</c:if>>Shqipëria</option>
							<option value="DZA" <c:if test="${nation == 'DZA'}">selected="selected"</c:if>>الجزائر</option>
							<option value="AND" <c:if test="${nation == 'AND'}">selected="selected"</c:if>>Andorra</option>
							<option value="AGO" <c:if test="${nation == 'AGO'}">selected="selected"</c:if>>Angola</option>
							<option value="ATG" <c:if test="${nation == 'ATG'}">selected="selected"</c:if>>Antiguans</option>
							<option value="ARG" <c:if test="${nation == 'ARG'}">selected="selected"</c:if>>Argentina</option>
							<option value="ARM" <c:if test="${nation == 'ARM'}">selected="selected"</c:if>>Հայաստան</option>
							<option value="AUS" <c:if test="${nation == 'AUS'}">selected="selected"</c:if>>Australia</option>
							<option value="AUT" <c:if test="${nation == 'AUT'}">selected="selected"</c:if>>Österreich</option>
							<option value="AZE" <c:if test="${nation == 'AZE'}">selected="selected"</c:if>>Azərbaycan</option>
							<option value="BHS" <c:if test="${nation == 'BHS'}">selected="selected"</c:if>>The Bahamas</option>
							<option value="BHR" <c:if test="${nation == 'BHR'}">selected="selected"</c:if>>البحرين</option>
							<option value="BGD" <c:if test="${nation == 'BGD'}">selected="selected"</c:if>>বাংলাদেশ</option>
							<option value="BRB" <c:if test="${nation == 'BRB'}">selected="selected"</c:if>>Barbados</option>
							<option value="BLR" <c:if test="${nation == 'BLR'}">selected="selected"</c:if>>Беларусь</option>
							<option value="BEL" <c:if test="${nation == 'BEL'}">selected="selected"</c:if>>Belgium</option>
							<option value="BLZ" <c:if test="${nation == 'BLZ'}">selected="selected"</c:if>>Belize</option>
							<option value="BEN" <c:if test="${nation == 'BEN'}">selected="selected"</c:if>>Bénin</option>
							<option value="BTN" <c:if test="${nation == 'BTN'}">selected="selected"</c:if>>འབྲུག་ཡུལ</option>
							<option value="BOL" <c:if test="${nation == 'BOL'}">selected="selected"</c:if>>Bolivia</option>
							<option value="BIH" <c:if test="${nation == 'BIH'}">selected="selected"</c:if>>Bosnia and Herzegovina</option>
							<option value="BWA" <c:if test="${nation == 'BWA'}">selected="selected"</c:if>>Botswana</option>
							<option value="BRA" <c:if test="${nation == 'BRA'}">selected="selected"</c:if>>Brasil</option>
							<option value="IVB" <c:if test="${nation == 'IVB'}">selected="selected"</c:if>>British Virgin Islands</option>
							<option value="BRN" <c:if test="${nation == 'BRN'}">selected="selected"</c:if>>Brunei</option>
							<option value="BGR" <c:if test="${nation == 'BGR'}">selected="selected"</c:if>>Bulgaria</option>
							<option value="BFA" <c:if test="${nation == 'BFA'}">selected="selected"</c:if>>Burkina Faso</option>
							<option value="BDI" <c:if test="${nation == 'BDI'}">selected="selected"</c:if>>Burundi</option>
							<option value="KHM" <c:if test="${nation == 'KHM'}">selected="selected"</c:if>>កម្ពុជា</option>
							<option value="CMR" <c:if test="${nation == 'CMR'}">selected="selected"</c:if>>Cameroon</option>
							<option value="CAN" <c:if test="${nation == 'CAN'}">selected="selected"</c:if>>Canada</option>
							<option value="CPV" <c:if test="${nation == 'CPV'}">selected="selected"</c:if>>Cabo Verde</option>
							<option value="CAF" <c:if test="${nation == 'CAF'}">selected="selected"</c:if>>Central African Rep.</option>
							<option value="TCD" <c:if test="${nation == 'TCD'}">selected="selected"</c:if>>Chad</option>
							<option value="CHL" <c:if test="${nation == 'CHL'}">selected="selected"</c:if>>Chile</option>
							<option value="CHN" <c:if test="${nation == 'CHN'}">selected="selected"</c:if>>中国</option>
							<option value="COL" <c:if test="${nation == 'COL'}">selected="selected"</c:if>>Colombia</option>
							<option value="COG" <c:if test="${nation == 'COG'}">selected="selected"</c:if>>Republic of the Congo</option>
							<option value="COD" <c:if test="${nation == 'COD'}">selected="selected"</c:if>>Democratic Republic of the Congo</option>
							<option value="CRI" <c:if test="${nation == 'CRI'}">selected="selected"</c:if>>Costa Rica</option>
							<option value="CIV" <c:if test="${nation == 'CIV'}">selected="selected"</c:if>>Côte d'Ivoire</option>
							<option value="HRV" <c:if test="${nation == 'HRV'}">selected="selected"</c:if>>Hrvatska</option>
							<option value="CUB" <c:if test="${nation == 'CUB'}">selected="selected"</c:if>>Cuba</option>
							<option value="CYP" <c:if test="${nation == 'CYP'}">selected="selected"</c:if>>Cyprus</option>
							<option value="CZE" <c:if test="${nation == 'CZE'}">selected="selected"</c:if>>Česko</option>
							<option value="DNK" <c:if test="${nation == 'DNK'}">selected="selected"</c:if>>Denmark</option>
							<option value="DJI" <c:if test="${nation == 'DJI'}">selected="selected"</c:if>>Djibouti</option>
							<option value="DMA" <c:if test="${nation == 'DMA'}">selected="selected"</c:if>>Dominica</option>
							<option value="DOM" <c:if test="${nation == 'DOM'}">selected="selected"</c:if>>República Dominicana</option>
							<option value="TLS" <c:if test="${nation == 'TLS'}">selected="selected"</c:if>>East Timor</option>
							<option value="ECU" <c:if test="${nation == 'ECU'}">selected="selected"</c:if>>Ecuador</option>
							<option value="EGY" <c:if test="${nation == 'EGY'}">selected="selected"</c:if>>مصر</option>
							<option value="SLV" <c:if test="${nation == 'SLV'}">selected="selected"</c:if>>El Salvador</option>
							<option value="GNQ" <c:if test="${nation == 'GNQ'}">selected="selected"</c:if>>Equatorial Guinea</option>
							<option value="ERI" <c:if test="${nation == 'ERI'}">selected="selected"</c:if>>Eritrea</option>
							<option value="EST" <c:if test="${nation == 'EST'}">selected="selected"</c:if>>Eesti</option>
							<option value="SWZ" <c:if test="${nation == 'SWZ'}">selected="selected"</c:if>>Eswatini</option>
							<option value="ETH" <c:if test="${nation == 'ETH'}">selected="selected"</c:if>>Ethiopia</option>
							<option value="FSM" <c:if test="${nation == 'FSM'}">selected="selected"</c:if>>F.S. Micronesia</option>
							<option value="FJI" <c:if test="${nation == 'FJI'}">selected="selected"</c:if>>Fiji</option>
							<option value="FIN" <c:if test="${nation == 'FIN'}">selected="selected"</c:if>>Suomi</option>
							<option value="FRA" <c:if test="${nation == 'FRA'}">selected="selected"</c:if>>France</option>
							<option value="GAB" <c:if test="${nation == 'GAB'}">selected="selected"</c:if>>République gabonaise</option>
							<option value="GMB" <c:if test="${nation == 'GMB'}">selected="selected"</c:if>>The Gambia</option>
							<option value="GEO" <c:if test="${nation == 'GEO'}">selected="selected"</c:if>>საქართველო</option>
							<option value="DEU" <c:if test="${nation == 'DEU'}">selected="selected"</c:if>>Deutschland</option>
							<option value="GHA" <c:if test="${nation == 'GHA'}">selected="selected"</c:if>>Ghana</option>
							<option value="GIB" <c:if test="${nation == 'GIB'}">selected="selected"</c:if>>Gibraltar</option>
							<option value="GRC" <c:if test="${nation == 'GRC'}">selected="selected"</c:if>>Ελλάς</option>
							<option value="GRL" <c:if test="${nation == 'GRL'}">selected="selected"</c:if>>Kalaallit Nunaat</option>
							<option value="GRD" <c:if test="${nation == 'GRD'}">selected="selected"</c:if>>Grenada</option>
							<option value="GTM" <c:if test="${nation == 'GTM'}">selected="selected"</c:if>>Guatemala</option>
							<option value="GUF" <c:if test="${nation == 'GUF'}">selected="selected"</c:if>>Guiana (French)</option>
							<option value="GIN" <c:if test="${nation == 'GIN'}">selected="selected"</c:if>>Guinea</option>
							<option value="GNB" <c:if test="${nation == 'GNB'}">selected="selected"</c:if>>Guiné-Bissau</option>
							<option value="GUY" <c:if test="${nation == 'GUY'}">selected="selected"</c:if>>Guyana</option>
							<option value="HTI" <c:if test="${nation == 'HTI'}">selected="selected"</c:if>>Haiti</option>
							<option value="HND" <c:if test="${nation == 'HND'}">selected="selected"</c:if>>Honduras</option>
							<option value="HKG" <c:if test="${nation == 'HKG'}">selected="selected"</c:if>>香港</option>
							<option value="HUN" <c:if test="${nation == 'HUN'}">selected="selected"</c:if>>Magyarország</option>
							<option value="ISL" <c:if test="${nation == 'ISL'}">selected="selected"</c:if>>Ísland</option>
							<option value="IND" <c:if test="${nation == 'IND'}">selected="selected"</c:if>>India</option>
							<option value="IDN" <c:if test="${nation == 'IDN'}">selected="selected"</c:if>>Indonesia</option>
							<option value="IRN" <c:if test="${nation == 'IRN'}">selected="selected"</c:if>>ایران</option>
							<option value="IRQ" <c:if test="${nation == 'IRQ'}">selected="selected"</c:if>>العراق</option>
							<option value="IRL" <c:if test="${nation == 'IRL'}">selected="selected"</c:if>>Éire</option>
							<option value="ISR" <c:if test="${nation == 'ISR'}">selected="selected"</c:if>>Israel</option>
							<option value="ITA" <c:if test="${nation == 'ITA'}">selected="selected"</c:if>>Italia</option>
							<option value="JAM" <c:if test="${nation == 'JAM'}">selected="selected"</c:if>>Jamaica</option>
							<option value="JPN" <c:if test="${nation == 'JPN'}">selected="selected"</c:if>>日本</option>
							<option value="JOR" <c:if test="${nation == 'JOR'}">selected="selected"</c:if>>الأردن</option>
							<option value="KAZ" <c:if test="${nation == 'KAZ'}">selected="selected"</c:if>>Қазақстан</option>
							<option value="KEN" <c:if test="${nation == 'KEN'}">selected="selected"</c:if>>Kenya</option>
							<option value="KIR" <c:if test="${nation == 'KIR'}">selected="selected"</c:if>>Kiribati</option>
							<option value="PRK" <c:if test="${nation == 'PRK'}">selected="selected"</c:if>>북한</option>
							<option value="KOR" <c:if test="${nation == 'KOR'}">selected="selected"</c:if>>한국</option>
							<option value="KWT" <c:if test="${nation == 'KWT'}">selected="selected"</c:if>>الكويت</option>
							<option value="KGZ" <c:if test="${nation == 'KGZ'}">selected="selected"</c:if>>Кыргызстан</option>
							<option value="LAO" <c:if test="${nation == 'LAO'}">selected="selected"</c:if>>ປະເທດລາວ</option>
							<option value="LVA" <c:if test="${nation == 'LVA'}">selected="selected"</c:if>>Latvija</option>
							<option value="LBN" <c:if test="${nation == 'LBN'}">selected="selected"</c:if>>لبنان</option>
							<option value="LSO" <c:if test="${nation == 'LSO'}">selected="selected"</c:if>>Lesotho</option>
							<option value="LBR" <c:if test="${nation == 'LBR'}">selected="selected"</c:if>>Liberia</option>
							<option value="LBY" <c:if test="${nation == 'LBY'}">selected="selected"</c:if>>ليبيا</option>
							<option value="LIE" <c:if test="${nation == 'LIE'}">selected="selected"</c:if>>Liechtenstein</option>
							<option value="LTU" <c:if test="${nation == 'LTU'}">selected="selected"</c:if>>Lithuania</option>
							<option value="LUX" <c:if test="${nation == 'LUX'}">selected="selected"</c:if>>Luxembourg</option>
							<option value="MDG" <c:if test="${nation == 'MDG'}">selected="selected"</c:if>>Madagascar</option>
							<option value="MWI" <c:if test="${nation == 'MWI'}">selected="selected"</c:if>>Malawi</option>
							<option value="MYS" <c:if test="${nation == 'MYS'}">selected="selected"</c:if>>Malaysia</option>
							<option value="MDV" <c:if test="${nation == 'MDV'}">selected="selected"</c:if>>ދިވެހިރާއްޖެ</option>
							<option value="MLI" <c:if test="${nation == 'MLI'}">selected="selected"</c:if>>Mali</option>
							<option value="MLT" <c:if test="${nation == 'MLT'}">selected="selected"</c:if>>Malta</option>
							<option value="MHL" <c:if test="${nation == 'MHL'}">selected="selected"</c:if>>Marshall Islands</option>
							<option value="MRT" <c:if test="${nation == 'MRT'}">selected="selected"</c:if>>موريتانيا</option>
							<option value="MUS" <c:if test="${nation == 'MUS'}">selected="selected"</c:if>>Mauritius</option>
							<option value="MEX" <c:if test="${nation == 'MEX'}">selected="selected"</c:if>>Mexico</option>
							<option value="MDA" <c:if test="${nation == 'MDA'}">selected="selected"</c:if>>Moldova</option>
							<option value="MCO" <c:if test="${nation == 'MCO'}">selected="selected"</c:if>>Monaco</option>
							<option value="MNG" <c:if test="${nation == 'MNG'}">selected="selected"</c:if>>Монгол Улс</option>
							<option value="MNE" <c:if test="${nation == 'MNE'}">selected="selected"</c:if>>Crna Gora</option>
							<option value="MAR" <c:if test="${nation == 'MAR'}">selected="selected"</c:if>>Morocco</option>
							<option value="MOZ" <c:if test="${nation == 'MOZ'}">selected="selected"</c:if>>Moçambique</option>
							<option value="MMR" <c:if test="${nation == 'MMR'}">selected="selected"</c:if>>Myanmar (Burma)</option>
							<option value="NAM" <c:if test="${nation == 'NAM'}">selected="selected"</c:if>>Namibia</option>
							<option value="NRU" <c:if test="${nation == 'NRU'}">selected="selected"</c:if>>Nauru</option>
							<option value="NPL" <c:if test="${nation == 'NPL'}">selected="selected"</c:if>>Nepal</option>
							<option value="NLD" <c:if test="${nation == 'NLD'}">selected="selected"</c:if>>Netherlands</option>
							<option value="NZL" <c:if test="${nation == 'NZL'}">selected="selected"</c:if>>New Zealand</option>
							<option value="NIC" <c:if test="${nation == 'NIC'}">selected="selected"</c:if>>Nicaragua</option>
							<option value="NER" <c:if test="${nation == 'NER'}">selected="selected"</c:if>>Niger</option>
							<option value="NGA" <c:if test="${nation == 'NGA'}">selected="selected"</c:if>>Nigeria</option>
							<option value="NIU" <c:if test="${nation == 'NIU'}">selected="selected"</c:if>>Niue</option>
							<option value="MKD" <c:if test="${nation == 'MKD'}">selected="selected"</c:if>>North Macedonia</option>
							<option value="NOR" <c:if test="${nation == 'NOR'}">selected="selected"</c:if>>Norway</option>
							<option value="OMN" <c:if test="${nation == 'OMN'}">selected="selected"</c:if>>عُمان</option>
							<option value="PAK" <c:if test="${nation == 'PAK'}">selected="selected"</c:if>>پاکستان</option>
							<option value="PLW" <c:if test="${nation == 'PLW'}">selected="selected"</c:if>>Palau</option>
							<option value="PSE" <c:if test="${nation == 'PSE'}">selected="selected"</c:if>>فلسطين</option>
							<option value="PAN" <c:if test="${nation == 'PAN'}">selected="selected"</c:if>>Panamá</option>
							<option value="PNG" <c:if test="${nation == 'PNG'}">selected="selected"</c:if>>Papua New Guinea</option>
							<option value="PRY" <c:if test="${nation == 'PRY'}">selected="selected"</c:if>>Paraguay</option>
							<option value="PER" <c:if test="${nation == 'PER'}">selected="selected"</c:if>>Peru</option>
							<option value="PHL" <c:if test="${nation == 'PHL'}">selected="selected"</c:if>>Philippines</option>
							<option value="POL" <c:if test="${nation == 'POL'}">selected="selected"</c:if>>Polska</option>
							<option value="PRT" <c:if test="${nation == 'PRT'}">selected="selected"</c:if>>Portugal</option>
							<option value="PRI" <c:if test="${nation == 'PRI'}">selected="selected"</c:if>>Puerto Rico</option>
							<option value="QAT" <c:if test="${nation == 'QAT'}">selected="selected"</c:if>>قطر</option>
							<option value="ROU" <c:if test="${nation == 'ROU'}">selected="selected"</c:if>>România</option>
							<option value="RUS" <c:if test="${nation == 'RUS'}">selected="selected"</c:if>>Россия</option>
							<option value="RWA" <c:if test="${nation == 'RWA'}">selected="selected"</c:if>>Rwanda</option>
							<option value="WSM" <c:if test="${nation == 'WSM'}">selected="selected"</c:if>>Samoa</option>
							<option value="SAU" <c:if test="${nation == 'SAU'}">selected="selected"</c:if>>العربية السعودية</option>
							<option value="SEN" <c:if test="${nation == 'SEN'}">selected="selected"</c:if>>Senegal</option>
							<option value="SRB" <c:if test="${nation == 'SRB'}">selected="selected"</c:if>>Србија</option>
							<option value="SYC" <c:if test="${nation == 'SYC'}">selected="selected"</c:if>>Seychelles</option>
							<option value="SLE" <c:if test="${nation == 'SLE'}">selected="selected"</c:if>>Sierra Leone</option>
							<option value="SGP" <c:if test="${nation == 'SGP'}">selected="selected"</c:if>>Singapore</option>
							<option value="SVK" <c:if test="${nation == 'SVK'}">selected="selected"</c:if>>Slovensko</option>
							<option value="SVN" <c:if test="${nation == 'SVN'}">selected="selected"</c:if>>Slovenija</option>
							<option value="SLB" <c:if test="${nation == 'SLB'}">selected="selected"</c:if>>Solomon Islands</option>
							<option value="SOM" <c:if test="${nation == 'SOM'}">selected="selected"</c:if>>Somalia</option>
							<option value="ZAF" <c:if test="${nation == 'ZAF'}">selected="selected"</c:if>>South Africa</option>
							<option value="SSD" <c:if test="${nation == 'SSD'}">selected="selected"</c:if>>South Sudan</option>
							<option value="ESP" <c:if test="${nation == 'ESP'}">selected="selected"</c:if>>España</option>
							<option value="LKA" <c:if test="${nation == 'LKA'}">selected="selected"</c:if>>Sri Lanka</option>
							<option value="SDN" <c:if test="${nation == 'SDN'}">selected="selected"</c:if>>السودان</option>
							<option value="SUR" <c:if test="${nation == 'SUR'}">selected="selected"</c:if>>Suriname</option>
							<option value="SWE" <c:if test="${nation == 'SWE'}">selected="selected"</c:if>>Sverige</option>
							<option value="CHE" <c:if test="${nation == 'CHE'}">selected="selected"</c:if>>Switzerland</option>
							<option value="SYR" <c:if test="${nation == 'SYR'}">selected="selected"</c:if>>سوريا</option>
							<option value="TWN" <c:if test="${nation == 'TWN'}">selected="selected"</c:if>>臺灣</option>
							<option value="TJK" <c:if test="${nation == 'TJK'}">selected="selected"</c:if>>Тоҷикистон</option>
							<option value="TZA" <c:if test="${nation == 'TZA'}">selected="selected"</c:if>>Tanzania</option>
							<option value="THA" <c:if test="${nation == 'THA'}">selected="selected"</c:if>>ไทย</option>
							<option value="TGO" <c:if test="${nation == 'TGO'}">selected="selected"</c:if>>Togo</option>
							<option value="TON" <c:if test="${nation == 'TON'}">selected="selected"</c:if>>Tonga</option>
							<option value="TTO" <c:if test="${nation == 'TTO'}">selected="selected"</c:if>>Trinidad and Tobago</option>
							<option value="TUN" <c:if test="${nation == 'TUN'}">selected="selected"</c:if>>Tunisia</option>
							<option value="TUR" <c:if test="${nation == 'TUR'}">selected="selected"</c:if>>Türkiye</option>
							<option value="TKM" <c:if test="${nation == 'TKM'}">selected="selected"</c:if>>Türkmenistan</option>
							<option value="TUV" <c:if test="${nation == 'TUV'}">selected="selected"</c:if>>Tuvalu</option>
							<option value="UGA" <c:if test="${nation == 'UGA'}">selected="selected"</c:if>>Uganda</option>
							<option value="UKR" <c:if test="${nation == 'UKR'}">selected="selected"</c:if>>Україна</option>
							<option value="ARE" <c:if test="${nation == 'ARE'}">selected="selected"</c:if>>United Arab Emirates</option>
							<option value="GBR" <c:if test="${nation == 'GBR'}">selected="selected"</c:if>>United Kingdom</option>
							<option value="USA" <c:if test="${nation == 'USA'}">selected="selected"</c:if>>United States</option>
							<option value="URY" <c:if test="${nation == 'URY'}">selected="selected"</c:if>>Uruguay</option>
							<option value="UZB" <c:if test="${nation == 'UZB'}">selected="selected"</c:if>>Ўзбекистон</option>
							<option value="VUT" <c:if test="${nation == 'VUT'}">selected="selected"</c:if>>Vanuatu</option>
							<option value="VEN" <c:if test="${nation == 'VEN'}">selected="selected"</c:if>>Venezuela</option>
							<option value="VNM" <c:if test="${nation == 'VNM'}">selected="selected"</c:if>>Việt Nam</option>
							<option value="YEM" <c:if test="${nation == 'YEM'}">selected="selected"</c:if>>Yemen</option>
							<option value="ZMB" <c:if test="${nation == 'ZMB'}">selected="selected"</c:if>>Zambia</option>
							<option value="ZWE" <c:if test="${nation == 'ZWE'}">selected="selected"</c:if>>Zimbabwe</option>
	                    </select>
	                    <p><spring:message code="msg_0417"/><br><spring:message code="msg_0766"/></p>
	                </div>
	                <button type="button" class="btn btn-primary col-xs-12 col-md-4 col-md-offset-4" id="ad-info-save-btn2" onclick="step1_proc()"><spring:message code="msg_0132"/></button>
	            </form>
	            <iframe name="join_ifrm" width="100%" height="0" frameborder="0"></iframe>
	        </div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title"><spring:message code="msg_0767"/></h3>
				</div>
				<div class="panel-body">
					<p>
						<spring:message code="msg_0768"/>
					</p>
					<p>
						<spring:message code="msg_0769"/>
					</p>
					<div>
						<jsp:include page="/Common/include/member/cert/sub/IdCardFormTemplate.jsp">
							<jsp:param name="flagIdCard" value="${flagIdCard}"></jsp:param>
						</jsp:include>
					</div>
	  			</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title"><spring:message code="msg_0770"/></h3>
				</div>
				<div class="panel-body">
					<p>
						<spring:message code="msg_0771"/>
					</p>
					<jsp:include page="/Common/include/member/cert/sub/AdInfoFormTemplate.jsp">
						<jsp:param name="flagNice" value="${flagNice}"></jsp:param>
						<jsp:param name="flagIdCard" value="${flagIdCard}"></jsp:param>
						<jsp:param name="flagAdInfo" value="${flagAdInfo}"></jsp:param>
						<jsp:param name="certStatus" value="${certStatus}"></jsp:param>
						<jsp:param name="adminMessage" value="${adminMessage}"></jsp:param>
					</jsp:include>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 본인인증 서비스 팝업을 호출하기 위해서는 다음과 같은 form이 필요합니다. -->
<form name="form_chk" method="post">
	<input type="hidden" name="m" value="checkplusService">		<!-- 필수 데이타로, 누락하시면 안됩니다. -->
	<input type="hidden" name="EncodeData" value="${encData}">		<!-- 위에서 업체정보를 암호화 한 데이타입니다. -->
</form>
<script src="/Common/include/member/cert/es5/upload-id-card.js?2019-04-26-3"></script>
<script src="/Common/include/member/cert/es5/ad-info-form.js?2019-04-26-3"></script>
<script src="/Common/include/member/cert/es5/nice-cert.js?2019-04-26-3"></script>
<%--
// Nice 인증모듈 초기화 및 form 작성
certSuccessUrl = '/member/cert/index?ACT=SUCCESS_NICE_CERT';
certErrorUrl = '';
libNiceCertInit(certSuccessUrl, certErrorUrl);
--%>
<div id="top_btn">
	<a href="#">
		<span>
			<img src="/Common/images/top_btn_arrow.svg" alt="top_btn">
		</span>
	</a>
</div>
<script src="/Common/js/axios.min.js"></script>
</body>