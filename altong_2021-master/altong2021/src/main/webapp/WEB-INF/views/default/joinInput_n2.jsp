<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp"%>
<head>
<link rel="stylesheet" href="/pub/default/joinInput_n2/join.css?ver=1.9">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=2.1">

<link rel="stylesheet" href="/pub/css/languages_common.css?ver=1.2">
<link rel="stylesheet" href="/pub/css/languages_answerList.css?ver=1.2">
<link rel="stylesheet" href="/pub/css/languages_mediaQuery.css?ver=1.2">
<link rel="stylesheet" href="/pub/default/joinInput_n2/lang_join.css?ver=1.9">

<script type="text/javascript" src="/Common/js_new/default/languages.js?ver=1.6"></script>

<!-- 210526휴대폰 나라별 코드 수정 -->
<link rel="stylesheet" href="/Common/CSS_new/dd.css">
<script src="/Common/js_new/default/jquery.dd.min.js"></script>
<script>
    $(document).ready(function () {
        try {
            $('#joinPhone0').msDropDown();
        } catch (e) {
            alert(e.message);
        }

        try {
            $('#joinNation').msDropDown();
        } catch (e) {
            alert(e.message);
        }
    });
</script>
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<script src="/Common/js/polyfill.min.js"></script>
<!--<script src="https://unpkg.com/vue@2.5.16/dist/vue.min.js"></script>-->
<!--<script src="https://unpkg.com/axios/dist/axios.min.js"></script>-->
<script src="/Common/js/vue.min.js"></script>
<script src="/Common/js/axios.min.js"></script>
<script src="https://cdn.rawgit.com/rikmms/progress-bar-4-axios/0a3acf92/dist/index.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.rawgit.com/rikmms/progress-bar-4-axios/0a3acf92/dist/nprogress.css"/>
<div id="atm_memjoin_wrapper0">
	<div class="center">
		<div class="atm_edittop_ttbar_pc" >
			<h1 class="atm_edittop_c1"><spring:message code="msg_0170"/></h1>
		</div>
		<form name="join_frm_sch" method="post" onSubmit="return false;">
			<input type="hidden" name="btn_click" value="">
			<input type="hidden" name="ch_encodeData" value="${sEncodeData}">
			<input name="FlagCertNum" type="hidden" value="">
			<input name="FlagNickName" type="hidden" value="">
			<input type="hidden" name="sDupInfo" value="<${now}">
			<div class="atm_memjoin_con">
				<div class="atm_memjoin_con_el">
					<div class="atm_memjoin_opt_F recommend-code-input row" data-chucode1="${chuCode1}" data-chucode2="${chuCode2}" data-chucode-flag="${chuCodeFlag}">
						<p class="atm_memjoin_c2"><spring:message code="msg_0396"/><span v-if="!chuCodeFlag && !recommenderCheck">(<spring:message code="msg_0397"/>)</span></p>
						<div>
							<div class="col-xs-12">
								<input type="radio" v-if="!recommenderCheck" v-model="radioPicked" value="know" id="know-recommend-code">
								<label v-if="!recommenderCheck" for="know-recommend-code"><spring:message code="msg_0398"/></label>
								<input type="radio" v-model="radioPicked" value="unknow" id="unknow-recommend-code">
								<label v-if="!recommenderCheck" for="unknow-recommend-code"><spring:message code="msg_0399"/></label>
								<div v-if="radioPicked === 'know' && !(!recommenderCheck && recommenderConfirm == '')" style="padding-right:0px;">
									<div v-if="!recommenderCheck" class="col-xs-10">
										<input name="ChuCode1" v-model="chuCode1" type="tel" class="atm_memjoin_input3" maxlength="4" @keyup="onChucodeKeyUp" readonly>
										<span>-</span>
										<input name="ChuCode2" v-model="chuCode2" type="tel" class="atm_memjoin_input3" maxlength="4" @keyup="onChucodeKeyUp" readonly>
									</div>
									<button v-if="!recommenderCheck && recommenderConfirm == ''" @click="checkRecommender" type="button" class="btn btn-default col-xs-2"><spring:message code="msg_0160"/></button>
									<p id="recomdInfo" v-if="!recommenderCheck && recommenderConfirm !== ''" >
										{{recommenderConfirm}}
									</p>
									<p v-if="!recommenderCheck && recommenderConfirm == ''">
										<spring:message code="msg_0400"/>
										<br><spring:message code="msg_0401"/>
									</p>
									<div v-if="(radioPicked === 'know' || chuCodeFlag) && !recommenderCheck && recommenderConfirm !== ''" class="col-xs-12 chucode-confirm-btn-wrapper" id="chucodeConfirmBtn">
										<button type="button" @click="onCorrectRecommenderClick" class="btn btn-confirm"><spring:message code="msg_0402"/></button>
										<button type="button" @click="onWrongRecommenderClick" class="btn btn-cancle"><spring:message code="msg_0403"/></button>
									</div>
								</div>
								<div v-if="radioPicked === 'know' && (!recommenderCheck && recommenderConfirm == '')" >
									<div v-if="!recommenderCheck" class="col-xs-10">
										<input name="ChuCode1" v-model="chuCode1" type="tel" class="atm_memjoin_input3" maxlength="4" @keyup="onChucodeKeyUp">
										<span>-</span>
										<input name="ChuCode2" v-model="chuCode2" type="tel" class="atm_memjoin_input3" maxlength="4" @keyup="onChucodeKeyUp">
									</div>
									<button v-if="!recommenderCheck && recommenderConfirm == ''" @click="checkRecommender" type="button" class="btn btn-default col-xs-2"><spring:message code="msg_0160"/></button>
									<p id="recomdInfo" v-if="!recommenderCheck && recommenderConfirm !== ''" >
										{{recommenderConfirm}}
									</p>
									<p v-if="!recommenderCheck && recommenderConfirm == ''">
										<spring:message code="msg_0400"/>
										<br><spring:message code="msg_0401"/>
									</p>
									<div v-if="(radioPicked === 'know' || chuCodeFlag) && !recommenderCheck && recommenderConfirm !== ''" class="col-xs-12 chucode-confirm-btn-wrapper" id="chucodeConfirmBtn">
										<button type="button" @click="onCorrectRecommenderClick" class="btn btn-confirm"><spring:message code="msg_0402"/></button>
										<button type="button" @click="onWrongRecommenderClick" class="btn btn-cancle"><spring:message code="msg_0403"/></button>
									</div>
								</div>
							</div>
						</div>
						<div v-if="chuCodeFlag">
							<div class="col-xs-12">
								{{chuCode1}} - {{chuCode2}} &nbsp;&nbsp;/&nbsp;&nbsp; {{recommenderConfirm}}
								<input type="hidden" name="ChuCode1" v-model="chuCode1">
								<input type="hidden" name="ChuCode2" v-model="chuCode2">
							</div>
							
						</div>
						<div v-else>
							<div class="col-xs-12" v-if="recommenderCheck" style="background: rgb(238, 238, 238); color: rgb(130, 130, 130); line-height: 35px; letter-spacing: -0.5px; font-weight: bold;">
								{{chuCode1}} - {{chuCode2}} &nbsp;&nbsp;/&nbsp;&nbsp; {{recommenderConfirm}}
								<input type="hidden" name="ChuCode1" v-model="chuCode1">
								<input type="hidden" name="ChuCode2" v-model="chuCode2">
							</div>
							<!--  추천인 있음 없음, 입력 등 -->
							
						</div>
					</div>
					<%--
					<div class="atm_memjoin_opt">
						<p class="atm_memjoin_c2">성명 (실명을 입력해주세요.)</p>
						<input name="sName" placeholder="실명을 입력해주세요." type="name" class="atm_memjoin_input5" maxlength="16" />
					</div>
					--%>
					<div class="atm_memjoin_opt">
						<p class="atm_memjoin_c2"><spring:message code="msg_0310"/></p>
						<input name="NickName" type="text" class="atm_memjoin_input5" id="NickName" maxlength="20" onkeyup="javascript:NickNameReset();" placeholder='<spring:message code="msg_0414"/>'
						/>
						<span class="atm_memjoin_c4" onclick="javascript:certChk3();"><spring:message code="msg_0404"/></span>
						<p id="atm_flag_nickname_check" style="color:#ff5001;font-weight:bold;font-size: 12px;"></p>
					</div>
					
					<div class="atm_memjoin_opt">
						<p class="atm_memjoin_c2"><spring:message code="msg_0405"/></p>
						<select name="Phone0" id="joinPhone0" class="atm_memjoin_input1">
							<option value="93" data-image="/Common/images/nation/AFG.svg" <c:if test="${areaCode == '93'}">selected="selected"</c:if>>Afghanistan(+93)</option>
							<option value="355" data-image="/Common/images/nation/ALB.svg" <c:if test="${areaCode == '355'}">selected="selected"</c:if>>Shqipëria(+355)</option>
							<option value="213" data-image="/Common/images/nation/DZA.svg" <c:if test="${areaCode == '213'}">selected="selected"</c:if>>Algeria(+213)</option>
							<option value="376" data-image="/Common/images/nation/AND.svg" <c:if test="${areaCode == '376'}">selected="selected"</c:if>>Andorra(+376)</option>
							<option value="244" data-image="/Common/images/nation/AGO.svg" <c:if test="${areaCode == '244'}">selected="selected"</c:if>>Angola(+244)</option>
							<option value="54" data-image="/Common/images/nation/ARG.svg" <c:if test="${areaCode == '54'}">selected="selected"</c:if>>Argentina(+54)</option>
							<option value="374" data-image="/Common/images/nation/ARM.svg" <c:if test="${areaCode == '374'}">selected="selected"</c:if>>Հայաստան(+374)</option>
							<option value="61" data-image="/Common/images/nation/AUS.svg" <c:if test="${areaCode == '61'}">selected="selected"</c:if>>Australia(+61)</option>
							<option value="672" data-image="/Common/images/nation/NFK.svg" <c:if test="${areaCode == '672'}">selected="selected"</c:if>>Australian Ext. Terr.(+672)</option>
							<option value="43" data-image="/Common/images/nation/AUT.svg" <c:if test="${areaCode == '43'}">selected="selected"</c:if>>Österreich(+43)</option>
							<option value="994" data-image="/Common/images/nation/AZE.svg" <c:if test="${areaCode == '994'}">selected="selected"</c:if>>Azərbaycan(+994)</option>
							<option value="1242" data-image="/Common/images/nation/BHS.svg" <c:if test="${areaCode == '1242'}">selected="selected"</c:if>>The Bahamas(+1242)</option>
							<option value="973" data-image="/Common/images/nation/BHR.svg" <c:if test="${areaCode == '973'}">selected="selected"</c:if>>Bahrain(+973)</option>
							<option value="880" data-image="/Common/images/nation/BGD.svg" <c:if test="${areaCode == '880'}">selected="selected"</c:if>>বাংলাদেশ(+880)</option>
							<option value="1246" data-image="/Common/images/nation/BRB.svg" <c:if test="${areaCode == '1246'}">selected="selected"</c:if>>Barbados(+1246)</option>
							<option value="375" data-image="/Common/images/nation/BLR.svg" <c:if test="${areaCode == '375'}">selected="selected"</c:if>>Беларусь(+375)</option>
							<option value="32" data-image="/Common/images/nation/BEL.svg" <c:if test="${areaCode == '32'}">selected="selected"</c:if>>Belgium(+32)</option>
							<option value="501" data-image="/Common/images/nation/BLZ.svg" <c:if test="${areaCode == '501'}">selected="selected"</c:if>>Belize(+501)</option>
							<option value="229" data-image="/Common/images/nation/BEN.svg" <c:if test="${areaCode == '229'}">selected="selected"</c:if>>Bénin(+229)</option>
							<option value="975" data-image="/Common/images/nation/BTN.svg" <c:if test="${areaCode == '975'}">selected="selected"</c:if>>འབྲུག་ཡུལ(+975)</option>
							<option value="591" data-image="/Common/images/nation/BOL.svg" <c:if test="${areaCode == '591'}">selected="selected"</c:if>>Bolivia(+591)</option>
							<option value="387" data-image="/Common/images/nation/BIH.svg" <c:if test="${areaCode == '387'}">selected="selected"</c:if>>Bosnia and Herzegovina(+387)</option>
							<option value="267" data-image="/Common/images/nation/BWA.svg" <c:if test="${areaCode == '267'}">selected="selected"</c:if>>Botswana(+267)</option>
							<option value="55" data-image="/Common/images/nation/BRA.svg" <c:if test="${areaCode == '55'}">selected="selected"</c:if>>Brasil(+55)</option>
							<option value="1284" data-image="/Common/images/nation/VGB.svg" <c:if test="${areaCode == '1284'}">selected="selected"</c:if>>British Virgin Islands(+1284)</option>
							<option value="673" data-image="/Common/images/nation/BRN.svg" <c:if test="${areaCode == '673'}">selected="selected"</c:if>>Brunei(+673)</option>
							<option value="359" data-image="/Common/images/nation/BGR.svg" <c:if test="${areaCode == '359'}">selected="selected"</c:if>>Bulgaria(+359)</option>
							<option value="226" data-image="/Common/images/nation/BFA.svg" <c:if test="${areaCode == '226'}">selected="selected"</c:if>>Burkina Faso(+226)</option>
							<option value="257" data-image="/Common/images/nation/BDI.svg" <c:if test="${areaCode == '257'}">selected="selected"</c:if>>Burundi(+257)</option>
							<option value="855" data-image="/Common/images/nation/KHM.svg" <c:if test="${areaCode == '855'}">selected="selected"</c:if>>កម្ពុជា(+855)</option>
							<option value="237" data-image="/Common/images/nation/CMR.svg" <c:if test="${areaCode == '237'}">selected="selected"</c:if>>Cameroon(+237)</option>
							<option value="238" data-image="/Common/images/nation/CPV.svg" <c:if test="${areaCode == '238'}">selected="selected"</c:if>>Cabo Verde(+238)</option>
							<option value="236" data-image="/Common/images/nation/CAF.svg" <c:if test="${areaCode == '236'}">selected="selected"</c:if>>Central African Rep.(+236)</option>
							<option value="235" data-image="/Common/images/nation/TCD.svg" <c:if test="${areaCode == '235'}">selected="selected"</c:if>>Chad(+235)</option>
							<option value="56" data-image="/Common/images/nation/CHL.svg" <c:if test="${areaCode == '56'}">selected="selected"</c:if>>Chile(+56)</option>
							<option value="86" data-image="/Common/images/nation/CHN.svg" <c:if test="${areaCode == '86'}">selected="selected"</c:if>>中国(+86)</option>
							<option value="57" data-image="/Common/images/nation/COL.svg" <c:if test="${areaCode == '57'}">selected="selected"</c:if>>Colombia(+57)</option>
							<option value="242" data-image="/Common/images/nation/COG.svg" <c:if test="${areaCode == '242'}">selected="selected"</c:if>>Republic of the Congo(+242)</option>
							<option value="506" data-image="/Common/images/nation/CRI.svg" <c:if test="${areaCode == '506'}">selected="selected"</c:if>>Costa Rica(+506)</option>
							<option value="225" data-image="/Common/images/nation/CIV.svg" <c:if test="${areaCode == '225'}">selected="selected"</c:if>>Côte d'Ivoire(+225)</option>
							<option value="385" data-image="/Common/images/nation/HRV.svg" <c:if test="${areaCode == '385'}">selected="selected"</c:if>>Hrvatska(+385)</option>
							<option value="53" data-image="/Common/images/nation/CUB.svg" <c:if test="${areaCode == '53'}">selected="selected"</c:if>>Cuba(+53)</option>
							<option value="357" data-image="/Common/images/nation/CYP.svg" <c:if test="${areaCode == '357'}">selected="selected"</c:if>>Cyprus(+357)</option>
							<option value="420" data-image="/Common/images/nation/CZE.svg" <c:if test="${areaCode == '420'}">selected="selected"</c:if>>Česko(+420)</option>
							<option value="45" data-image="/Common/images/nation/DNK.svg" <c:if test="${areaCode == '45'}">selected="selected"</c:if>>Denmark(+45)</option>
							<option value="253" data-image="/Common/images/nation/DJI.svg" <c:if test="${areaCode == '253'}">selected="selected"</c:if>>Djibouti(+253)</option>
							<option value="1767" data-image="/Common/images/nation/DMA.svg" <c:if test="${areaCode == '1767'}">selected="selected"</c:if>>Dominica(+1767)</option>
							<option value="1809" data-image="/Common/images/nation/DOM.svg" <c:if test="${areaCode == '1809'}">selected="selected"</c:if>>República Dominicana(+1809)</option>
							<option value="670" data-image="/Common/images/nation/TLS.svg" <c:if test="${areaCode == '670'}">selected="selected"</c:if>>East Timor(+670)</option>
							<option value="593" data-image="/Common/images/nation/ECU.svg" <c:if test="${areaCode == '593'}">selected="selected"</c:if>>Ecuador(+593)</option>
							<option value="20" data-image="/Common/images/nation/EGY.svg" <c:if test="${areaCode == '20'}">selected="selected"</c:if>>Egypt(+20)</option>
							<option value="503" data-image="/Common/images/nation/SLV.svg" <c:if test="${areaCode == '503'}">selected="selected"</c:if>>El Salvador(+503)</option>
							<option value="240" data-image="/Common/images/nation/GNQ.svg" <c:if test="${areaCode == '240'}">selected="selected"</c:if>>Equatorial Guinea(+240)</option>
							<option value="291" data-image="/Common/images/nation/ERI.svg" <c:if test="${areaCode == '291'}">selected="selected"</c:if>>Eritrea(+291)</option>
							<option value="372" data-image="/Common/images/nation/EST.svg" <c:if test="${areaCode == '372'}">selected="selected"</c:if>>Eesti(+372)</option>
							<option value="268" data-image="/Common/images/nation/SWZ.svg" <c:if test="${areaCode == '268'}">selected="selected"</c:if>>Eswatini(+268)</option>
							<option value="251" data-image="/Common/images/nation/ETH.svg" <c:if test="${areaCode == '251'}">selected="selected"</c:if>>Ethiopia(+251)</option>
							<option value="691" data-image="/Common/images/nation/FSM.svg" <c:if test="${areaCode == '691'}">selected="selected"</c:if>>F.S. Micronesia(+691)</option>
							<option value="679" data-image="/Common/images/nation/FJI.svg" <c:if test="${areaCode == '679'}">selected="selected"</c:if>>Fiji(+679)</option>
							<option value="358" data-image="/Common/images/nation/FIN.svg" <c:if test="${areaCode == '358'}">selected="selected"</c:if>>Suomi(+358)</option>
							<option value="33" data-image="/Common/images/nation/FRA.svg" <c:if test="${areaCode == '33'}">selected="selected"</c:if>>France(+33)</option>
							<option value="241" data-image="/Common/images/nation/GAB.svg" <c:if test="${areaCode == '241'}">selected="selected"</c:if>>République gabonaise(+241)</option>
							<option value="220" data-image="/Common/images/nation/GMB.svg" <c:if test="${areaCode == '220'}">selected="selected"</c:if>>The Gambia(+220)</option>
							<option value="995" data-image="/Common/images/nation/GEO.svg" <c:if test="${areaCode == '995'}">selected="selected"</c:if>>საქართველო(+995)</option>
							<option value="49" data-image="/Common/images/nation/DEU.svg" <c:if test="${areaCode == '49'}">selected="selected"</c:if>>Deutschland(+49)</option>
							<option value="233" data-image="/Common/images/nation/GHA.svg" <c:if test="${areaCode == '233'}">selected="selected"</c:if>>Ghana(+233)</option>
							<option value="350" data-image="/Common/images/nation/GIB.svg" <c:if test="${areaCode == '350'}">selected="selected"</c:if>>Gibraltar(+350)</option>
							<option value="30" data-image="/Common/images/nation/GRC.svg" <c:if test="${areaCode == '30'}">selected="selected"</c:if>>Ελλάς(+30)</option>
							<option value="299" data-image="/Common/images/nation/GRL.svg" <c:if test="${areaCode == '299'}">selected="selected"</c:if>>Kalaallit Nunaat(+299)</option>
							<option value="1473" data-image="/Common/images/nation/GRD.svg" <c:if test="${areaCode == '1473'}">selected="selected"</c:if>>Grenada(+1473)</option>
							<option value="502" data-image="/Common/images/nation/GTM.svg" <c:if test="${areaCode == '502'}">selected="selected"</c:if>>Guatemala(+502)</option>
							<option value="594" data-image="/Common/images/nation/GUF.svg" <c:if test="${areaCode == '594'}">selected="selected"</c:if>>Guiana (French)(+594)</option>
							<option value="224" data-image="/Common/images/nation/GIN.svg" <c:if test="${areaCode == '224'}">selected="selected"</c:if>>Guinea(+224)</option>
							<option value="245" data-image="/Common/images/nation/GNB.svg" <c:if test="${areaCode == '245'}">selected="selected"</c:if>>Guiné-Bissau(+245)</option>
							<option value="592" data-image="/Common/images/nation/GUY.svg" <c:if test="${areaCode == '592'}">selected="selected"</c:if>>Guyana(+592)</option>
							<option value="509" data-image="/Common/images/nation/HTI.svg" <c:if test="${areaCode == '509'}">selected="selected"</c:if>>Haiti(+509)</option>
							<option value="504" data-image="/Common/images/nation/HND.svg" <c:if test="${areaCode == '504'}">selected="selected"</c:if>>Honduras(+504)</option>
							<option value="852" data-image="/Common/images/nation/HKG.svg" <c:if test="${areaCode == '852'}">selected="selected"</c:if>>香港(+852)</option>
							<option value="36" data-image="/Common/images/nation/HUN.svg" <c:if test="${areaCode == '36'}">selected="selected"</c:if>>Magyarország(+36)</option>
							<option value="354" data-image="/Common/images/nation/ISL.svg" <c:if test="${areaCode == '354'}">selected="selected"</c:if>>Ísland(+354)</option>
							<option value="91" data-image="/Common/images/nation/IND.svg" <c:if test="${areaCode == '91'}">selected="selected"</c:if>>India(+91)</option>
							<option value="62" data-image="/Common/images/nation/IDN.svg" <c:if test="${areaCode == '62'}">selected="selected"</c:if>>Indonesia(+62)</option>
							<option value="98" data-image="/Common/images/nation/IRN.svg" <c:if test="${areaCode == '98'}">selected="selected"</c:if>>Iran(+98)</option>
							<option value="964" data-image="/Common/images/nation/IRQ.svg" <c:if test="${areaCode == '964'}">selected="selected"</c:if>>Iraq(+964)</option>
							<option value="353" data-image="/Common/images/nation/IRL.svg" <c:if test="${areaCode == '353'}">selected="selected"</c:if>>Éire(+353)</option>
							<option value="972" data-image="/Common/images/nation/ISR.svg" <c:if test="${areaCode == '972'}">selected="selected"</c:if>>Israel(+972)</option>
							<option value="39" data-image="/Common/images/nation/ITA.svg" <c:if test="${areaCode == '39'}">selected="selected"</c:if>>Italia(+39)</option>
							<option value="1876" data-image="/Common/images/nation/JAM.svg" <c:if test="${areaCode == '1876'}">selected="selected"</c:if>>Jamaica(+1876)</option>
							<option value="81" data-image="/Common/images/nation/JPN.svg" <c:if test="${areaCode == '81'}">selected="selected"</c:if>>日本(+81)</option>
							<option value="962" data-image="/Common/images/nation/JOR.svg" <c:if test="${areaCode == '962'}">selected="selected"</c:if>>Jordan(+962)</option>
							<option value="254" data-image="/Common/images/nation/KEN.svg" <c:if test="${areaCode == '254'}">selected="selected"</c:if>>Kenya(+254)</option>
							<option value="686" data-image="/Common/images/nation/KIR.svg" <c:if test="${areaCode == '686'}">selected="selected"</c:if>>Kiribati(+686)</option>
							<option value="850" data-image="/Common/images/nation/PRK.svg" <c:if test="${areaCode == '850'}">selected="selected"</c:if>>북한(+850)</option>
							<option value="82" data-image="/Common/images/nation/KOR.svg" <c:if test="${areaCode == '82'}">selected="selected"</c:if>>한국(+82)</option>
							<option value="965" data-image="/Common/images/nation/KWT.svg" <c:if test="${areaCode == '965'}">selected="selected"</c:if>>Kuwait(+965)</option>
							<option value="996" data-image="/Common/images/nation/KGZ.svg" <c:if test="${areaCode == '996'}">selected="selected"</c:if>>Кыргызстан(+996)</option>
							<option value="856" data-image="/Common/images/nation/LAO.svg" <c:if test="${areaCode == '856'}">selected="selected"</c:if>>ປະເທດລາວ(+856)</option>
							<option value="371" data-image="/Common/images/nation/LVA.svg" <c:if test="${areaCode == '371'}">selected="selected"</c:if>>Latvija(+371)</option>
							<option value="961" data-image="/Common/images/nation/LBN.svg" <c:if test="${areaCode == '961'}">selected="selected"</c:if>>Lebanon(+961)</option>
							<option value="266" data-image="/Common/images/nation/LSO.svg" <c:if test="${areaCode == '266'}">selected="selected"</c:if>>Lesotho(+266)</option>
							<option value="231" data-image="/Common/images/nation/LBR.svg" <c:if test="${areaCode == '231'}">selected="selected"</c:if>>Liberia(+231)</option>
							<option value="218" data-image="/Common/images/nation/LBY.svg" <c:if test="${areaCode == '218'}">selected="selected"</c:if>>Libya(+218)</option>
							<option value="423" data-image="/Common/images/nation/LIE.svg" <c:if test="${areaCode == '423'}">selected="selected"</c:if>>Liechtenstein(+423)</option>
							<option value="370" data-image="/Common/images/nation/LTU.svg" <c:if test="${areaCode == '370'}">selected="selected"</c:if>>Lithuania(+370)</option>
							<option value="352" data-image="/Common/images/nation/LUX.svg" <c:if test="${areaCode == '352'}">selected="selected"</c:if>>Luxembourg(+352)</option>
							<option value="261" data-image="/Common/images/nation/MDG.svg" <c:if test="${areaCode == '261'}">selected="selected"</c:if>>Madagascar(+261)</option>
							<option value="265" data-image="/Common/images/nation/MWI.svg" <c:if test="${areaCode == '265'}">selected="selected"</c:if>>Malawi(+265)</option>
							<option value="60" data-image="/Common/images/nation/MYS.svg" <c:if test="${areaCode == '60'}">selected="selected"</c:if>>Malaysia(+60)</option>
							<option value="960" data-image="/Common/images/nation/MDV.svg" <c:if test="${areaCode == '960'}">selected="selected"</c:if>>Maldives(+960)</option>
							<option value="223" data-image="/Common/images/nation/MLI.svg" <c:if test="${areaCode == '223'}">selected="selected"</c:if>>Mali(+223)</option>
							<option value="356" data-image="/Common/images/nation/MLT.svg" <c:if test="${areaCode == '356'}">selected="selected"</c:if>>Malta(+356)</option>
							<option value="692" data-image="/Common/images/nation/MHL.svg" <c:if test="${areaCode == '692'}">selected="selected"</c:if>>Marshall Islands(+692)</option>
							<option value="222" data-image="/Common/images/nation/MRT.svg" <c:if test="${areaCode == '222'}">selected="selected"</c:if>>Mauritania(+222)</option>
							<option value="230" data-image="/Common/images/nation/MUS.svg" <c:if test="${areaCode == '230'}">selected="selected"</c:if>>Mauritius(+230)</option>
							<option value="52" data-image="/Common/images/nation/MEX.svg" <c:if test="${areaCode == '52'}">selected="selected"</c:if>>Mexico(+52)</option>
							<option value="373" data-image="/Common/images/nation/MDA.svg" <c:if test="${areaCode == '373'}">selected="selected"</c:if>>Moldova(+373)</option>
							<option value="377" data-image="/Common/images/nation/MCO.svg" <c:if test="${areaCode == '377'}">selected="selected"</c:if>>Monaco(+377)</option>
							<option value="976" data-image="/Common/images/nation/MNG.svg" <c:if test="${areaCode == '976'}">selected="selected"</c:if>>Монгол Улс(+976)</option>
							<option value="382" data-image="/Common/images/nation/MNE.svg" <c:if test="${areaCode == '382'}">selected="selected"</c:if>>Crna Gora(+382)</option>
							<option value="212" data-image="/Common/images/nation/MAR.svg" <c:if test="${areaCode == '212'}">selected="selected"</c:if>>Morocco(+212)</option>
							<option value="258" data-image="/Common/images/nation/MOZ.svg" <c:if test="${areaCode == '258'}">selected="selected"</c:if>>Moçambique(+258)</option>
							<option value="95" data-image="/Common/images/nation/MMR.svg" <c:if test="${areaCode == '95'}">selected="selected"</c:if>>Myanmar (Burma)(+95)</option>
							<option value="264" data-image="/Common/images/nation/NAM.svg" <c:if test="${areaCode == '264'}">selected="selected"</c:if>>Namibia(+264)</option>
							<option value="674" data-image="/Common/images/nation/NRU.svg" <c:if test="${areaCode == '674'}">selected="selected"</c:if>>Nauru(+674)</option>
							<option value="977" data-image="/Common/images/nation/NPL.svg" <c:if test="${areaCode == '977'}">selected="selected"</c:if>>Nepal(+977)</option>
							<option value="31" data-image="/Common/images/nation/NLD.svg" <c:if test="${areaCode == '31'}">selected="selected"</c:if>>Netherlands(+31)</option>
							<option value="64" data-image="/Common/images/nation/NZL.svg" <c:if test="${areaCode == '64'}">selected="selected"</c:if>>New Zealand(+64)</option>
							<option value="505" data-image="/Common/images/nation/NIC.svg" <c:if test="${areaCode == '505'}">selected="selected"</c:if>>Nicaragua(+505)</option>
							<option value="227" data-image="/Common/images/nation/NER.svg" <c:if test="${areaCode == '227'}">selected="selected"</c:if>>Niger(+227)</option>
							<option value="234" data-image="/Common/images/nation/NGA.svg" <c:if test="${areaCode == '234'}">selected="selected"</c:if>>Nigeria(+234)</option>
							<option value="683" data-image="/Common/images/nation/NIU.svg" <c:if test="${areaCode == '683'}">selected="selected"</c:if>>Niue(+683)</option>
							<option value="389" data-image="/Common/images/nation/MKD.svg" <c:if test="${areaCode == '389'}">selected="selected"</c:if>>North Macedonia(+389)</option>
							<option value="47" data-image="/Common/images/nation/NOR.svg" <c:if test="${areaCode == '47'}">selected="selected"</c:if>>Norway(+47)</option>
							<option value="968" data-image="/Common/images/nation/OMN.svg" <c:if test="${areaCode == '968'}">selected="selected"</c:if>>Oman(+968)</option>
							<option value="92" data-image="/Common/images/nation/PAK.svg" <c:if test="${areaCode == '92'}">selected="selected"</c:if>>Pakistan(+92)</option>
							<option value="680" data-image="/Common/images/nation/PLW.svg" <c:if test="${areaCode == '680'}">selected="selected"</c:if>>Palau(+680)</option>
							<option value="970" data-image="/Common/images/nation/PSE.svg" <c:if test="${areaCode == '970'}">selected="selected"</c:if>>Palestine(+970)</option>
							<option value="507" data-image="/Common/images/nation/PAN.svg" <c:if test="${areaCode == '507'}">selected="selected"</c:if>>Panamá(+507)</option>
							<option value="675" data-image="/Common/images/nation/PNG.svg" <c:if test="${areaCode == '675'}">selected="selected"</c:if>>Papua New Guinea(+675)</option>
							<option value="595" data-image="/Common/images/nation/PRY.svg" <c:if test="${areaCode == '595'}">selected="selected"</c:if>>Paraguay(+595)</option>
							<option value="51" data-image="/Common/images/nation/PER.svg" <c:if test="${areaCode == '51'}">selected="selected"</c:if>>Peru(+51)</option>
							<option value="63" data-image="/Common/images/nation/PHL.svg" <c:if test="${areaCode == '63'}">selected="selected"</c:if>>Philippines(+63)</option>
							<option value="48" data-image="/Common/images/nation/POL.svg" <c:if test="${areaCode == '48'}">selected="selected"</c:if>>Polska(+48)</option>
							<option value="351" data-image="/Common/images/nation/PRT.svg" <c:if test="${areaCode == '351'}">selected="selected"</c:if>>Portugal(+351)</option>
							<option value="1787" data-image="/Common/images/nation/PRI.svg" <c:if test="${areaCode == '1787'}">selected="selected"</c:if>>Puerto Rico(+1787)</option>
							<option value="974" data-image="/Common/images/nation/QAT.svg" <c:if test="${areaCode == '974'}">selected="selected"</c:if>>Qatar(+974)</option>
							<option value="40" data-image="/Common/images/nation/ROU.svg" <c:if test="${areaCode == '40'}">selected="selected"</c:if>>România(+40)</option>
							<option value="7" data-image="/Common/images/nation/RUS.svg" <c:if test="${areaCode == '7'}">selected="selected"</c:if>>Russia, Kazakhstan(+7)</option>
							<option value="250" data-image="/Common/images/nation/RWA.svg" <c:if test="${areaCode == '250'}">selected="selected"</c:if>>Rwanda(+250)</option>
							<option value="685" data-image="/Common/images/nation/WSM.svg" <c:if test="${areaCode == '685'}">selected="selected"</c:if>>Western Samoa(+685)</option>
							<option value="966" data-image="/Common/images/nation/SAU.svg" <c:if test="${areaCode == '966'}">selected="selected"</c:if>>Saudi Arabia(+966)</option>
							<option value="221" data-image="/Common/images/nation/SEN.svg" <c:if test="${areaCode == '221'}">selected="selected"</c:if>>Senegal(+221)</option>
							<option value="381" data-image="/Common/images/nation/SRB.svg" <c:if test="${areaCode == '381'}">selected="selected"</c:if>>Србија(+381)</option>
							<option value="248" data-image="/Common/images/nation/SYC.svg" <c:if test="${areaCode == '248'}">selected="selected"</c:if>>Seychelles(+248)</option>
							<option value="232" data-image="/Common/images/nation/SLE.svg" <c:if test="${areaCode == '232'}">selected="selected"</c:if>>Sierra Leone(+232)</option>
							<option value="65" data-image="/Common/images/nation/SGP.svg" <c:if test="${areaCode == '65'}">selected="selected"</c:if>>Singapore(+65)</option>
							<option value="421" data-image="/Common/images/nation/SVK.svg" <c:if test="${areaCode == '421'}">selected="selected"</c:if>>Slovensko(+421)</option>
							<option value="386" data-image="/Common/images/nation/SVN.svg" <c:if test="${areaCode == '386'}">selected="selected"</c:if>>Slovenija(+386)</option>
							<option value="677" data-image="/Common/images/nation/SLB.svg" <c:if test="${areaCode == '677'}">selected="selected"</c:if>>Solomon Islands(+677)</option>
							<option value="252" data-image="/Common/images/nation/SOM.svg" <c:if test="${areaCode == '252'}">selected="selected"</c:if>>Somalia(+252)</option>
							<option value="27" data-image="/Common/images/nation/ZAF.svg" <c:if test="${areaCode == '27'}">selected="selected"</c:if>>South Africa(+27)</option>
							<option value="211" data-image="/Common/images/nation/SSD.svg" <c:if test="${areaCode == '211'}">selected="selected"</c:if>>South Sudan(+211)</option>
							<option value="34" data-image="/Common/images/nation/ESP.svg" <c:if test="${areaCode == '34'}">selected="selected"</c:if>>España(+34)</option>
							<option value="94" data-image="/Common/images/nation/LKA.svg" <c:if test="${areaCode == '94'}">selected="selected"</c:if>>Sri Lanka(+94)</option>
							<option value="249" data-image="/Common/images/nation/SDN.svg" <c:if test="${areaCode == '249'}">selected="selected"</c:if>>Sudan(+249)</option>
							<option value="597" data-image="/Common/images/nation/SUR.svg" <c:if test="${areaCode == '597'}">selected="selected"</c:if>>Suriname(+597)</option>
							<option value="46" data-image="/Common/images/nation/SWE.svg" <c:if test="${areaCode == '46'}">selected="selected"</c:if>>Sverige(+46)</option>
							<option value="41" data-image="/Common/images/nation/CHE.svg" <c:if test="${areaCode == '41'}">selected="selected"</c:if>>Switzerland(+41)</option>
							<option value="963" data-image="/Common/images/nation/SYR.svg" <c:if test="${areaCode == '963'}">selected="selected"</c:if>>Syria(+963)</option>
							<option value="886" data-image="/Common/images/nation/TWN.svg" <c:if test="${areaCode == '886'}">selected="selected"</c:if>>臺灣(+886)</option>
							<option value="992" data-image="/Common/images/nation/TJK.svg" <c:if test="${areaCode == '992'}">selected="selected"</c:if>>Тоҷикистон(+992)</option>
							<option value="255" data-image="/Common/images/nation/TZA.svg" <c:if test="${areaCode == '255'}">selected="selected"</c:if>>Tanzania(+255)</option>
							<option value="66" data-image="/Common/images/nation/THA.svg" <c:if test="${areaCode == '66'}">selected="selected"</c:if>>ไทย(+66)</option>
							<option value="228" data-image="/Common/images/nation/TGO.svg" <c:if test="${areaCode == '228'}">selected="selected"</c:if>>Togo(+228)</option>
							<option value="676" data-image="/Common/images/nation/TON.svg" <c:if test="${areaCode == '676'}">selected="selected"</c:if>>Tonga(+676)</option>
							<option value="1868" data-image="/Common/images/nation/TTO.svg" <c:if test="${areaCode == '1868'}">selected="selected"</c:if>>Trinidad and Tobago(+1868)</option>
							<option value="216" data-image="/Common/images/nation/TUN.svg" <c:if test="${areaCode == '216'}">selected="selected"</c:if>>Tunisia(+216)</option>
							<option value="90" data-image="/Common/images/nation/TUR.svg" <c:if test="${areaCode == '90'}">selected="selected"</c:if>>Türkiye(+90)</option>
							<option value="993" data-image="/Common/images/nation/TKM.svg" <c:if test="${areaCode == '993'}">selected="selected"</c:if>>Türkmenistan(+993)</option>
							<option value="688" data-image="/Common/images/nation/TUV.svg" <c:if test="${areaCode == '688'}">selected="selected"</c:if>>Tuvalu(+688)</option>
							<option value="256" data-image="/Common/images/nation/UGA.svg" <c:if test="${areaCode == '256'}">selected="selected"</c:if>>Uganda(+256)</option>
							<option value="380" data-image="/Common/images/nation/UKR.svg" <c:if test="${areaCode == '380'}">selected="selected"</c:if>>Україна(+380)</option>
							<option value="971" data-image="/Common/images/nation/ARE.svg" <c:if test="${areaCode == '971'}">selected="selected"</c:if>>United Arab Emirates(+971)</option>
							<option value="44" data-image="/Common/images/nation/GBR.svg" <c:if test="${areaCode == '44'}">selected="selected"</c:if>>United Kingdom(+44)</option>
							<option value="1" data-image="/Common/images/nation/CAN.svg" <c:if test="${areaCode == '1'}">selected="selected"</c:if>>United States/Canada(+1)</option>
							<option value="598" data-image="/Common/images/nation/URY.svg" <c:if test="${areaCode == '598'}">selected="selected"</c:if>>Uruguay(+598)</option>
							<option value="998" data-image="/Common/images/nation/UZB.svg" <c:if test="${areaCode == '998'}">selected="selected"</c:if>>Ўзбекистон(+998)</option>
							<option value="678" data-image="/Common/images/nation/VUT.svg" <c:if test="${areaCode == '678'}">selected="selected"</c:if>>Vanuatu(+678)</option>
							<option value="58" data-image="/Common/images/nation/VEN.svg"> <c:if test="${areaCode == '58'}">selected="selected"</c:if>>Venezuela(+58)</option>
							<option value="84" data-image="/Common/images/nation/VNM.svg" <c:if test="${areaCode == '84'}">selected="selected"</c:if>>Việt Nam(+84)</option>
							<option value="967" data-image="/Common/images/nation/YEM.svg" <c:if test="${areaCode == '967'}">selected="selected"</c:if>>Yemen(+967)</option>
							<option value="260" data-image="/Common/images/nation/ZMB.svg" <c:if test="${areaCode == '260'}">selected="selected"</c:if>>Zambia(+260)</option>
							<option value="263" data-image="/Common/images/nation/ZWE.svg" <c:if test="${areaCode == '263'}">selected="selected"</c:if>>Zimbabwe(+263)</option>
						</select>
						<input name="Phone1" type="text" class="atm_memjoin_input2" onKeyUp="javascript:checkNum(this);">
						<p><spring:message code="msg_0406"/></p>
						<div class="atm_memjoin_btnG">
							<p class="atm_memjoin_c4" onClick="javascript:certChk();"><spring:message code="msg_0407"/></p>
						</div>
					</div>
					<div class="atm_memjoin_opt">
						<input name="CertNum" type="text" class="atm_memjoin_input5" id="CertNum" maxlength="5" placeholder='<spring:message code="msg_0408"/> ${smsTimeLimit}<spring:message code="msg_0409"/>'
						/>
						<div>
							<p class="atm_memjoin_time_left" id="timer1"></p>
							<p class="atm_memjoin_time_left" id="timer2"></p>
						</div>
						<div class="atm_memjoin_btnG">
							<p class="atm_memjoin_c5" onClick="javascript:certChk2();"><spring:message code="msg_0410"/></p>
						</div>
					</div>
					
					<div class="remove" style="position: absolute; bottom: 0px; opacity: 0.001; display: none;" ><input type="text" name="id-tric"><input type="password" name="pw-tric"></div>
					<script>setTimeout(function(){$('.remove').hide()},1000)</script>

	
					<div class="atm_memjoin_opt">
						<p class="atm_memjoin_c2"><spring:message code="msg_0412"/></p>
						<input name="Password1" type="password" class="atm_memjoin_input5" maxlength="16" placeholder='<spring:message code="msg_0415"/>'
						/>
					</div>
	
					<div class="atm_memjoin_opt">
						<p class="atm_memjoin_c2"><spring:message code="msg_0413"/></p>
						<input name="Password2" type="password" class="atm_memjoin_input5" maxlength="16" placeholder='<spring:message code="msg_0416"/>'
						/>
					</div>
					<div class="atm_memjoin_opt">
						<p class="atm_memjoin_c2">국적</p>
						<select name="nation" id="joinNation">
							<option value="" <c:if test="${nation == ''}">selected="selected"</c:if>>-- select one --</option>
							<option value="AFG" data-image="/Common/images/nation/AFG.svg" <c:if test="${nation == 'AFG'}">selected="selected"</c:if>>افغانستان</option>
							<option value="ALB" data-image="/Common/images/nation/ALB.svg" <c:if test="${nation == 'ALB'}">selected="selected"</c:if>>Shqipëria</option>
							<option value="DZA" data-image="/Common/images/nation/DZA.svg" <c:if test="${nation == 'DZA'}">selected="selected"</c:if>>الجزائر</option>
							<option value="AND" data-image="/Common/images/nation/AND.svg" <c:if test="${nation == 'AND'}">selected="selected"</c:if>>Andorra</option>
							<option value="AGO" data-image="/Common/images/nation/AGO.svg" <c:if test="${nation == 'AGO'}">selected="selected"</c:if>>Angola</option>
							<option value="ATG" data-image="/Common/images/nation/ATG.svg" <c:if test="${nation == 'ATG'}">selected="selected"</c:if>>Antiguans</option>
							<option value="ARG" data-image="/Common/images/nation/ARG.svg" <c:if test="${nation == 'ARG'}">selected="selected"</c:if>>Argentina</option>
							<option value="ARM" data-image="/Common/images/nation/ARM.svg" <c:if test="${nation == 'ARM'}">selected="selected"</c:if>>Հայաստան</option>
							<option value="AUS" data-image="/Common/images/nation/AUS.svg" <c:if test="${nation == 'AUS'}">selected="selected"</c:if>>Australia</option>
							<option value="AUT" data-image="/Common/images/nation/AUT.svg" <c:if test="${nation == 'AUT'}">selected="selected"</c:if>>Österreich</option>
							<option value="AZE" data-image="/Common/images/nation/AZE.svg" <c:if test="${nation == 'AZE'}">selected="selected"</c:if>>Azərbaycan</option>
							<option value="BHS" data-image="/Common/images/nation/BHS.svg" <c:if test="${nation == 'BHS'}">selected="selected"</c:if>>The Bahamas</option>
							<option value="BHR" data-image="/Common/images/nation/BHR.svg" <c:if test="${nation == 'BHR'}">selected="selected"</c:if>>البحرين</option>
							<option value="BGD" data-image="/Common/images/nation/BGD.svg" <c:if test="${nation == 'BGD'}">selected="selected"</c:if>>বাংলাদেশ</option>
							<option value="BRB" data-image="/Common/images/nation/BRB.svg" <c:if test="${nation == 'BRB'}">selected="selected"</c:if>>Barbados</option>
							<option value="BLR" data-image="/Common/images/nation/BLR.svg" <c:if test="${nation == 'BLR'}">selected="selected"</c:if>>Беларусь</option>
							<option value="BEL" data-image="/Common/images/nation/BEL.svg" <c:if test="${nation == 'BEL'}">selected="selected"</c:if>>Belgium</option>
							<option value="BLZ" data-image="/Common/images/nation/BLZ.svg" <c:if test="${nation == 'BLZ'}">selected="selected"</c:if>>Belize</option>
							<option value="BEN" data-image="/Common/images/nation/BEN.svg" <c:if test="${nation == 'BEN'}">selected="selected"</c:if>>Bénin</option>
							<option value="BTN" data-image="/Common/images/nation/BTN.svg" <c:if test="${nation == 'BTN'}">selected="selected"</c:if>>འབྲུག་ཡུལ</option>
							<option value="BOL" data-image="/Common/images/nation/BOL.svg" <c:if test="${nation == 'BOL'}">selected="selected"</c:if>>Bolivia</option>
							<option value="BIH" data-image="/Common/images/nation/BIH.svg" <c:if test="${nation == 'BIH'}">selected="selected"</c:if>>Bosnia and Herzegovina</option>
							<option value="BWA" data-image="/Common/images/nation/BWA.svg" <c:if test="${nation == 'BWA'}">selected="selected"</c:if>>Botswana</option>
							<option value="BRA" data-image="/Common/images/nation/BRA.svg" <c:if test="${nation == 'BRA'}">selected="selected"</c:if>>Brasil</option>
							<option value="VGB" data-image="/Common/images/nation/VGB.svg" <c:if test="${nation == 'VGB'}">selected="selected"</c:if>>British Virgin Islands</option>
							<option value="BRN" data-image="/Common/images/nation/BRN.svg" <c:if test="${nation == 'BRN'}">selected="selected"</c:if>>Brunei</option>
							<option value="BGR" data-image="/Common/images/nation/BGR.svg" <c:if test="${nation == 'BGR'}">selected="selected"</c:if>>Bulgaria</option>
							<option value="BFA" data-image="/Common/images/nation/BFA.svg" <c:if test="${nation == 'BFA'}">selected="selected"</c:if>>Burkina Faso</option>
							<option value="BDI" data-image="/Common/images/nation/BDI.svg" <c:if test="${nation == 'BDI'}">selected="selected"</c:if>>Burundi</option>
							<option value="KHM" data-image="/Common/images/nation/KHM.svg" <c:if test="${nation == 'KHM'}">selected="selected"</c:if>>កម្ពុជា</option>
							<option value="CMR" data-image="/Common/images/nation/CMR.svg" <c:if test="${nation == 'CMR'}">selected="selected"</c:if>>Cameroon</option>
							<option value="CAN" data-image="/Common/images/nation/CAN.svg" <c:if test="${nation == 'CAN'}">selected="selected"</c:if>>Canada</option>
							<option value="CPV" data-image="/Common/images/nation/CPV.svg" <c:if test="${nation == 'CPV'}">selected="selected"</c:if>>Cabo Verde</option>
							<option value="CAF" data-image="/Common/images/nation/CAF.svg" <c:if test="${nation == 'CAF'}">selected="selected"</c:if>>Central African Rep.</option>
							<option value="TCD" data-image="/Common/images/nation/TCD.svg" <c:if test="${nation == 'TCD'}">selected="selected"</c:if>>Chad</option>
							<option value="CHL" data-image="/Common/images/nation/CHL.svg" <c:if test="${nation == 'CHL'}">selected="selected"</c:if>>Chile</option>
							<option value="CHN" data-image="/Common/images/nation/CHN.svg" <c:if test="${nation == 'CHN'}">selected="selected"</c:if>>中国</option>
							<option value="COL" data-image="/Common/images/nation/COL.svg" <c:if test="${nation == 'COL'}">selected="selected"</c:if>>Colombia</option>
							<option value="COG" data-image="/Common/images/nation/COG.svg" <c:if test="${nation == 'COG'}">selected="selected"</c:if>>Republic of the Congo</option>
							<option value="COD" data-image="/Common/images/nation/COD.svg" <c:if test="${nation == 'COD'}">selected="selected"</c:if>>Democratic Republic of the Congo</option>
							<option value="CRI" data-image="/Common/images/nation/CRI.svg" <c:if test="${nation == 'CRI'}">selected="selected"</c:if>>Costa Rica</option>
							<option value="CIV" data-image="/Common/images/nation/CIV.svg" <c:if test="${nation == 'CIV'}">selected="selected"</c:if>>Côte d'Ivoire</option>
							<option value="HRV" data-image="/Common/images/nation/HRV.svg" <c:if test="${nation == 'HRV'}">selected="selected"</c:if>>Hrvatska</option>
							<option value="CUB" data-image="/Common/images/nation/CUB.svg" <c:if test="${nation == 'CUB'}">selected="selected"</c:if>>Cuba</option>
							<option value="CYP" data-image="/Common/images/nation/CYP.svg" <c:if test="${nation == 'CYP'}">selected="selected"</c:if>>Cyprus</option>
							<option value="CZE" data-image="/Common/images/nation/CZE.svg" <c:if test="${nation == 'CZE'}">selected="selected"</c:if>>Česko</option>
							<option value="DNK" data-image="/Common/images/nation/DNK.svg" <c:if test="${nation == 'DNK'}">selected="selected"</c:if>>Denmark</option>
							<option value="DJI" data-image="/Common/images/nation/DJI.svg" <c:if test="${nation == 'DJI'}">selected="selected"</c:if>>Djibouti</option>
							<option value="DMA" data-image="/Common/images/nation/DMA.svg" <c:if test="${nation == 'DMA'}">selected="selected"</c:if>>Dominica</option>
							<option value="DOM" data-image="/Common/images/nation/DOM.svg" <c:if test="${nation == 'DOM'}">selected="selected"</c:if>>República Dominicana</option>
							<option value="TLS" data-image="/Common/images/nation/TLS.svg" <c:if test="${nation == 'TLS'}">selected="selected"</c:if>>East Timor</option>
							<option value="ECU" data-image="/Common/images/nation/ECU.svg" <c:if test="${nation == 'ECU'}">selected="selected"</c:if>>Ecuador</option>
							<option value="EGY" data-image="/Common/images/nation/EGY.svg" <c:if test="${nation == 'EGY'}">selected="selected"</c:if>>مصر</option>
							<option value="SLV" data-image="/Common/images/nation/SLV.svg" <c:if test="${nation == 'SLV'}">selected="selected"</c:if>>El Salvador</option>
							<option value="GNQ" data-image="/Common/images/nation/GNQ.svg" <c:if test="${nation == 'GNQ'}">selected="selected"</c:if>>Equatorial Guinea</option>
							<option value="ERI" data-image="/Common/images/nation/ERI.svg" <c:if test="${nation == 'ERI'}">selected="selected"</c:if>>Eritrea</option>
							<option value="EST" data-image="/Common/images/nation/EST.svg" <c:if test="${nation == 'EST'}">selected="selected"</c:if>>Eesti</option>
							<option value="SWZ" data-image="/Common/images/nation/SWZ.svg" <c:if test="${nation == 'SWZ'}">selected="selected"</c:if>>Eswatini</option>
							<option value="ETH" data-image="/Common/images/nation/ETH.svg" <c:if test="${nation == 'ETH'}">selected="selected"</c:if>>Ethiopia</option>
							<option value="FSM" data-image="/Common/images/nation/FSM.svg" <c:if test="${nation == 'FSM'}">selected="selected"</c:if>>F.S. Micronesia</option>
							<option value="FJI" data-image="/Common/images/nation/FJI.svg" <c:if test="${nation == 'FJI'}">selected="selected"</c:if>>Fiji</option>
							<option value="FIN" data-image="/Common/images/nation/FIN.svg" <c:if test="${nation == 'FIN'}">selected="selected"</c:if>>Suomi</option>
							<option value="FRA" data-image="/Common/images/nation/FRA.svg" <c:if test="${nation == 'FRA'}">selected="selected"</c:if>>France</option>
							<option value="GAB" data-image="/Common/images/nation/GAB.svg" <c:if test="${nation == 'GAB'}">selected="selected"</c:if>>République gabonaise</option>
							<option value="GMB" data-image="/Common/images/nation/GMB.svg" <c:if test="${nation == 'GMB'}">selected="selected"</c:if>>The Gambia</option>
							<option value="GEO" data-image="/Common/images/nation/GEO.svg" <c:if test="${nation == 'GEO'}">selected="selected"</c:if>>საქართველო</option>
							<option value="DEU" data-image="/Common/images/nation/DEU.svg" <c:if test="${nation == 'DEU'}">selected="selected"</c:if>>Deutschland</option>
							<option value="GHA" data-image="/Common/images/nation/GHA.svg" <c:if test="${nation == 'GHA'}">selected="selected"</c:if>>Ghana</option>
							<option value="GIB" data-image="/Common/images/nation/GIB.svg" <c:if test="${nation == 'GIB'}">selected="selected"</c:if>>Gibraltar</option>
							<option value="GRC" data-image="/Common/images/nation/GRC.svg" <c:if test="${nation == 'GRC'}">selected="selected"</c:if>>Ελλάς</option>
							<option value="GRL" data-image="/Common/images/nation/GRL.svg" <c:if test="${nation == 'GRL'}">selected="selected"</c:if>>Kalaallit Nunaat</option>
							<option value="GRD" data-image="/Common/images/nation/GRD.svg" <c:if test="${nation == 'GRD'}">selected="selected"</c:if>>Grenada</option>
							<option value="GTM" data-image="/Common/images/nation/GTM.svg" <c:if test="${nation == 'GTM'}">selected="selected"</c:if>>Guatemala</option>
							<option value="GUF" data-image="/Common/images/nation/GUF.svg" <c:if test="${nation == 'GUF'}">selected="selected"</c:if>>Guiana (French)</option>
							<option value="GIN" data-image="/Common/images/nation/GIN.svg" <c:if test="${nation == 'GIN'}">selected="selected"</c:if>>Guinea</option>
							<option value="GNB" data-image="/Common/images/nation/GNB.svg" <c:if test="${nation == 'GNB'}">selected="selected"</c:if>>Guiné-Bissau</option>
							<option value="GUY" data-image="/Common/images/nation/GUY.svg" <c:if test="${nation == 'GUY'}">selected="selected"</c:if>>Guyana</option>
							<option value="HTI" data-image="/Common/images/nation/HTI.svg" <c:if test="${nation == 'HTI'}">selected="selected"</c:if>>Haiti</option>
							<option value="HND" data-image="/Common/images/nation/HND.svg" <c:if test="${nation == 'HND'}">selected="selected"</c:if>>Honduras</option>
							<option value="HKG" data-image="/Common/images/nation/HKG.svg" <c:if test="${nation == 'HKG'}">selected="selected"</c:if>>香港</option>
							<option value="HUN" data-image="/Common/images/nation/HUN.svg" <c:if test="${nation == 'HUN'}">selected="selected"</c:if>>Magyarország</option>
							<option value="ISL" data-image="/Common/images/nation/ISL.svg" <c:if test="${nation == 'ISL'}">selected="selected"</c:if>>Ísland</option>
							<option value="IND" data-image="/Common/images/nation/IND.svg" <c:if test="${nation == 'IND'}">selected="selected"</c:if>>India</option>
							<option value="IDN" data-image="/Common/images/nation/IDN.svg" <c:if test="${nation == 'IDN'}">selected="selected"</c:if>>Indonesia</option>
							<option value="IRN" data-image="/Common/images/nation/IRN.svg" <c:if test="${nation == 'IRN'}">selected="selected"</c:if>>ایران</option>
							<option value="IRQ" data-image="/Common/images/nation/IRQ.svg" <c:if test="${nation == 'IRQ'}">selected="selected"</c:if>>العراق</option>
							<option value="IRL" data-image="/Common/images/nation/IRL.svg" <c:if test="${nation == 'IRL'}">selected="selected"</c:if>>Éire</option>
							<option value="ISR" data-image="/Common/images/nation/ISR.svg" <c:if test="${nation == 'ISR'}">selected="selected"</c:if>>Israel</option>
							<option value="ITA" data-image="/Common/images/nation/ITA.svg" <c:if test="${nation == 'ITA'}">selected="selected"</c:if>>Italia</option>
							<option value="JAM" data-image="/Common/images/nation/JAM.svg" <c:if test="${nation == 'JAM'}">selected="selected"</c:if>>Jamaica</option>
							<option value="JPN" data-image="/Common/images/nation/JPN.svg" <c:if test="${nation == 'JPN'}">selected="selected"</c:if>>日本</option>
							<option value="JOR" data-image="/Common/images/nation/JOR.svg" <c:if test="${nation == 'JOR'}">selected="selected"</c:if>>الأردن</option>
							<option value="KAZ" data-image="/Common/images/nation/KAZ.svg" <c:if test="${nation == 'KAZ'}">selected="selected"</c:if>>Қазақстан</option>
							<option value="KEN" data-image="/Common/images/nation/KEN.svg" <c:if test="${nation == 'KEN'}">selected="selected"</c:if>>Kenya</option>
							<option value="KIR" data-image="/Common/images/nation/KIR.svg" <c:if test="${nation == 'KIR'}">selected="selected"</c:if>>Kiribati</option>
							<option value="PRK" data-image="/Common/images/nation/PRK.svg" <c:if test="${nation == 'PRK'}">selected="selected"</c:if>>북한</option>
							<option value="KOR" data-image="/Common/images/nation/KOR.svg" <c:if test="${nation == 'KOR'}">selected="selected"</c:if>>한국</option>
							<option value="KWT" data-image="/Common/images/nation/KWT.svg" <c:if test="${nation == 'KWT'}">selected="selected"</c:if>>الكويت</option>
							<option value="KGZ" data-image="/Common/images/nation/KGZ.svg" <c:if test="${nation == 'KGZ'}">selected="selected"</c:if>>Кыргызстан</option>
							<option value="LAO" data-image="/Common/images/nation/LAO.svg" <c:if test="${nation == 'LAO'}">selected="selected"</c:if>>ປະເທດລາວ</option>
							<option value="LVA" data-image="/Common/images/nation/LVA.svg" <c:if test="${nation == 'LVA'}">selected="selected"</c:if>>Latvija</option>
							<option value="LBN" data-image="/Common/images/nation/LBN.svg" <c:if test="${nation == 'LBN'}">selected="selected"</c:if>>لبنان</option>
							<option value="LSO" data-image="/Common/images/nation/LSO.svg" <c:if test="${nation == 'LSO'}">selected="selected"</c:if>>Lesotho</option>
							<option value="LBR" data-image="/Common/images/nation/LBR.svg" <c:if test="${nation == 'LBR'}">selected="selected"</c:if>>Liberia</option>
							<option value="LBY" data-image="/Common/images/nation/LBY.svg" <c:if test="${nation == 'LBY'}">selected="selected"</c:if>>ليبيا</option>
							<option value="LIE" data-image="/Common/images/nation/LIE.svg" <c:if test="${nation == 'LIE'}">selected="selected"</c:if>>Liechtenstein</option>
							<option value="LTU" data-image="/Common/images/nation/LTU.svg" <c:if test="${nation == 'LTU'}">selected="selected"</c:if>>Lithuania</option>
							<option value="LUX" data-image="/Common/images/nation/LUX.svg" <c:if test="${nation == 'LUX'}">selected="selected"</c:if>>Luxembourg</option>
							<option value="MDG" data-image="/Common/images/nation/MDG.svg" <c:if test="${nation == 'MDG'}">selected="selected"</c:if>>Madagascar</option>
							<option value="MWI" data-image="/Common/images/nation/MWI.svg" <c:if test="${nation == 'MWI'}">selected="selected"</c:if>>Malawi</option>
							<option value="MYS" data-image="/Common/images/nation/MYS.svg" <c:if test="${nation == 'MYS'}">selected="selected"</c:if>>Malaysia</option>
							<option value="MDV" data-image="/Common/images/nation/MDV.svg" <c:if test="${nation == 'MDV'}">selected="selected"</c:if>>ދިވެހިރާއްޖެ</option>
							<option value="MLI" data-image="/Common/images/nation/MLI.svg" <c:if test="${nation == 'MLI'}">selected="selected"</c:if>>Mali</option>
							<option value="MLT" data-image="/Common/images/nation/MLT.svg" <c:if test="${nation == 'MLT'}">selected="selected"</c:if>>Malta</option>
							<option value="MHL" data-image="/Common/images/nation/MHL.svg" <c:if test="${nation == 'MHL'}">selected="selected"</c:if>>Marshall Islands</option>
							<option value="MRT" data-image="/Common/images/nation/MRT.svg" <c:if test="${nation == 'MRT'}">selected="selected"</c:if>>موريتانيا</option>
							<option value="MUS" data-image="/Common/images/nation/MUS.svg" <c:if test="${nation == 'MUS'}">selected="selected"</c:if>>Mauritius</option>
							<option value="MEX" data-image="/Common/images/nation/MEX.svg" <c:if test="${nation == 'MEX'}">selected="selected"</c:if>>Mexico</option>
							<option value="MDA" data-image="/Common/images/nation/MDA.svg" <c:if test="${nation == 'MDA'}">selected="selected"</c:if>>Moldova</option>
							<option value="MCO" data-image="/Common/images/nation/MCO.svg" <c:if test="${nation == 'MCO'}">selected="selected"</c:if>>Monaco</option>
							<option value="MNG" data-image="/Common/images/nation/MNG.svg" <c:if test="${nation == 'MNG'}">selected="selected"</c:if>>Монгол Улс</option>
							<option value="MNE" data-image="/Common/images/nation/MNE.svg" <c:if test="${nation == 'MNE'}">selected="selected"</c:if>>Crna Gora</option>
							<option value="MAR" data-image="/Common/images/nation/MAR.svg" <c:if test="${nation == 'MAR'}">selected="selected"</c:if>>Morocco</option>
							<option value="MOZ" data-image="/Common/images/nation/MOZ.svg" <c:if test="${nation == 'MOZ'}">selected="selected"</c:if>>Moçambique</option>
							<option value="MMR" data-image="/Common/images/nation/MMR.svg" <c:if test="${nation == 'MMR'}">selected="selected"</c:if>>Myanmar (Burma)</option>
							<option value="NAM" data-image="/Common/images/nation/NAM.svg" <c:if test="${nation == 'NAM'}">selected="selected"</c:if>>Namibia</option>
							<option value="NRU" data-image="/Common/images/nation/NRU.svg" <c:if test="${nation == 'NRU'}">selected="selected"</c:if>>Nauru</option>
							<option value="NPL" data-image="/Common/images/nation/NPL.svg" <c:if test="${nation == 'NPL'}">selected="selected"</c:if>>Nepal</option>
							<option value="NLD" data-image="/Common/images/nation/NLD.svg" <c:if test="${nation == 'NLD'}">selected="selected"</c:if>>Netherlands</option>
							<option value="NZL" data-image="/Common/images/nation/NZL.svg" <c:if test="${nation == 'NZL'}">selected="selected"</c:if>>New Zealand</option>
							<option value="NIC" data-image="/Common/images/nation/NIC.svg" <c:if test="${nation == 'NIC'}">selected="selected"</c:if>>Nicaragua</option>
							<option value="NER" data-image="/Common/images/nation/NER.svg" <c:if test="${nation == 'NER'}">selected="selected"</c:if>>Niger</option>
							<option value="NGA" data-image="/Common/images/nation/NGA.svg" <c:if test="${nation == 'NGA'}">selected="selected"</c:if>>Nigeria</option>
							<option value="NIU" data-image="/Common/images/nation/NIU.svg" <c:if test="${nation == 'NIU'}">selected="selected"</c:if>>Niue</option>
							<option value="MKD" data-image="/Common/images/nation/MKD.svg" <c:if test="${nation == 'MKD'}">selected="selected"</c:if>>North Macedonia</option>
							<option value="NOR" data-image="/Common/images/nation/NOR.svg" <c:if test="${nation == 'NOR'}">selected="selected"</c:if>>Norway</option>
							<option value="OMN" data-image="/Common/images/nation/OMN.svg" <c:if test="${nation == 'OMN'}">selected="selected"</c:if>>عُمان</option>
							<option value="PAK" data-image="/Common/images/nation/PAK.svg" <c:if test="${nation == 'PAK'}">selected="selected"</c:if>>پاکستان</option>
							<option value="PLW" data-image="/Common/images/nation/PLW.svg" <c:if test="${nation == 'PLW'}">selected="selected"</c:if>>Palau</option>
							<option value="PSE" data-image="/Common/images/nation/PSE.svg" <c:if test="${nation == 'PSE'}">selected="selected"</c:if>>فلسطين</option>
							<option value="PAN" data-image="/Common/images/nation/PAN.svg" <c:if test="${nation == 'PAN'}">selected="selected"</c:if>>Panamá</option>
							<option value="PNG" data-image="/Common/images/nation/PNG.svg" <c:if test="${nation == 'PNG'}">selected="selected"</c:if>>Papua New Guinea</option>
							<option value="PRY" data-image="/Common/images/nation/PRY.svg" <c:if test="${nation == 'PRY'}">selected="selected"</c:if>>Paraguay</option>
							<option value="PER" data-image="/Common/images/nation/PER.svg" <c:if test="${nation == 'PER'}">selected="selected"</c:if>>Peru</option>
							<option value="PHL" data-image="/Common/images/nation/PHL.svg" <c:if test="${nation == 'PHL'}">selected="selected"</c:if>>Philippines</option>
							<option value="POL" data-image="/Common/images/nation/POL.svg" <c:if test="${nation == 'POL'}">selected="selected"</c:if>>Polska</option>
							<option value="PRT" data-image="/Common/images/nation/PRT.svg" <c:if test="${nation == 'PRT'}">selected="selected"</c:if>>Portugal</option>
							<option value="PRI" data-image="/Common/images/nation/PRI.svg" <c:if test="${nation == 'PRI'}">selected="selected"</c:if>>Puerto Rico</option>
							<option value="QAT" data-image="/Common/images/nation/QAT.svg" <c:if test="${nation == 'QAT'}">selected="selected"</c:if>>قطر</option>
							<option value="ROU" data-image="/Common/images/nation/ROU.svg" <c:if test="${nation == 'ROU'}">selected="selected"</c:if>>România</option>
							<option value="RUS" data-image="/Common/images/nation/RUS.svg" <c:if test="${nation == 'RUS'}">selected="selected"</c:if>>Россия</option>
							<option value="RWA" data-image="/Common/images/nation/RWA.svg" <c:if test="${nation == 'RWA'}">selected="selected"</c:if>>Rwanda</option>
							<option value="WSM" data-image="/Common/images/nation/WSM.svg" <c:if test="${nation == 'WSM'}">selected="selected"</c:if>>Samoa</option>
							<option value="SAU" data-image="/Common/images/nation/SAU.svg" <c:if test="${nation == 'SAU'}">selected="selected"</c:if>>العربية السعودية</option>
							<option value="SEN" data-image="/Common/images/nation/SEN.svg" <c:if test="${nation == 'SEN'}">selected="selected"</c:if>>Senegal</option>
							<option value="SRB" data-image="/Common/images/nation/SRB.svg" <c:if test="${nation == 'SRB'}">selected="selected"</c:if>>Србија</option>
							<option value="SYC" data-image="/Common/images/nation/SYC.svg" <c:if test="${nation == 'SYC'}">selected="selected"</c:if>>Seychelles</option>
							<option value="SLE" data-image="/Common/images/nation/SLE.svg" <c:if test="${nation == 'SLE'}">selected="selected"</c:if>>Sierra Leone</option>
							<option value="SGP" data-image="/Common/images/nation/SGP.svg" <c:if test="${nation == 'SGP'}">selected="selected"</c:if>>Singapore</option>
							<option value="SVK" data-image="/Common/images/nation/SVK.svg" <c:if test="${nation == 'SVK'}">selected="selected"</c:if>>Slovensko</option>
							<option value="SVN" data-image="/Common/images/nation/SVN.svg" <c:if test="${nation == 'SVN'}">selected="selected"</c:if>>Slovenija</option>
							<option value="SLB" data-image="/Common/images/nation/SLB.svg" <c:if test="${nation == 'SLB'}">selected="selected"</c:if>>Solomon Islands</option>
							<option value="SOM" data-image="/Common/images/nation/SOM.svg" <c:if test="${nation == 'SOM'}">selected="selected"</c:if>>Somalia</option>
							<option value="ZAF" data-image="/Common/images/nation/ZAF.svg" <c:if test="${nation == 'ZAF'}">selected="selected"</c:if>>South Africa</option>
							<option value="SSD" data-image="/Common/images/nation/SSD.svg" <c:if test="${nation == 'SSD'}">selected="selected"</c:if>>South Sudan</option>
							<option value="ESP" data-image="/Common/images/nation/ESP.svg" <c:if test="${nation == 'ESP'}">selected="selected"</c:if>>España</option>
							<option value="LKA" data-image="/Common/images/nation/LKA.svg" <c:if test="${nation == 'LKA'}">selected="selected"</c:if>>Sri Lanka</option>
							<option value="SDN" data-image="/Common/images/nation/SDN.svg" <c:if test="${nation == 'SDN'}">selected="selected"</c:if>>السودان</option>
							<option value="SUR" data-image="/Common/images/nation/SUR.svg" <c:if test="${nation == 'SUR'}">selected="selected"</c:if>>Suriname</option>
							<option value="SWE" data-image="/Common/images/nation/SWE.svg" <c:if test="${nation == 'SWE'}">selected="selected"</c:if>>Sverige</option>
							<option value="CHE" data-image="/Common/images/nation/CHE.svg" <c:if test="${nation == 'CHE'}">selected="selected"</c:if>>Switzerland</option>
							<option value="SYR" data-image="/Common/images/nation/SYR.svg" <c:if test="${nation == 'SYR'}">selected="selected"</c:if>>سوريا</option>
							<option value="TWN" data-image="/Common/images/nation/TWN.svg" <c:if test="${nation == 'TWN'}">selected="selected"</c:if>>臺灣</option>
							<option value="TJK" data-image="/Common/images/nation/TJK.svg" <c:if test="${nation == 'TJK'}">selected="selected"</c:if>>Тоҷикистон</option>
							<option value="TZA" data-image="/Common/images/nation/TZA.svg" <c:if test="${nation == 'TZA'}">selected="selected"</c:if>>Tanzania</option>
							<option value="THA" data-image="/Common/images/nation/THA.svg" <c:if test="${nation == 'THA'}">selected="selected"</c:if>>ไทย</option>
							<option value="TGO" data-image="/Common/images/nation/TGO.svg" <c:if test="${nation == 'TGO'}">selected="selected"</c:if>>Togo</option>
							<option value="TON" data-image="/Common/images/nation/TON.svg" <c:if test="${nation == 'TON'}">selected="selected"</c:if>>Tonga</option>
							<option value="TTO" data-image="/Common/images/nation/TTO.svg" <c:if test="${nation == 'TTO'}">selected="selected"</c:if>>Trinidad and Tobago</option>
							<option value="TUN" data-image="/Common/images/nation/TUN.svg" <c:if test="${nation == 'TUN'}">selected="selected"</c:if>>Tunisia</option>
							<option value="TUR" data-image="/Common/images/nation/TUR.svg" <c:if test="${nation == 'TUR'}">selected="selected"</c:if>>Türkiye</option>
							<option value="TKM" data-image="/Common/images/nation/TKM.svg" <c:if test="${nation == 'TKM'}">selected="selected"</c:if>>Türkmenistan</option>
							<option value="TUV" data-image="/Common/images/nation/TUV.svg" <c:if test="${nation == 'TUV'}">selected="selected"</c:if>>Tuvalu</option>
							<option value="UGA" data-image="/Common/images/nation/UGA.svg" <c:if test="${nation == 'UGA'}">selected="selected"</c:if>>Uganda</option>
							<option value="UKR" data-image="/Common/images/nation/UKR.svg" <c:if test="${nation == 'UKR'}">selected="selected"</c:if>>Україна</option>
							<option value="ARE" data-image="/Common/images/nation/ARE.svg" <c:if test="${nation == 'ARE'}">selected="selected"</c:if>>United Arab Emirates</option>
							<option value="GBR" data-image="/Common/images/nation/GBR.svg" <c:if test="${nation == 'GBR'}">selected="selected"</c:if>>United Kingdom</option>
							<option value="USA" data-image="/Common/images/nation/USA.svg" <c:if test="${nation == 'USA'}">selected="selected"</c:if>>United States</option>
							<option value="URY" data-image="/Common/images/nation/URY.svg" <c:if test="${nation == 'URY'}">selected="selected"</c:if>>Uruguay</option>
							<option value="UZB" data-image="/Common/images/nation/UZB.svg" <c:if test="${nation == 'UZB'}">selected="selected"</c:if>>Ўзбекистон</option>
							<option value="VUT" data-image="/Common/images/nation/VUT.svg" <c:if test="${nation == 'VUT'}">selected="selected"</c:if>>Vanuatu</option>
							<option value="VEN" data-image="/Common/images/nation/VEN.svg" <c:if test="${nation == 'VEN'}">selected="selected"</c:if>>Venezuela</option>
							<option value="VNM" data-image="/Common/images/nation/VNM.svg" <c:if test="${nation == 'VNM'}">selected="selected"</c:if>>Việt Nam</option>
							<option value="YEM" data-image="/Common/images/nation/YEM.svg" <c:if test="${nation == 'YEM'}">selected="selected"</c:if>>Yemen</option>
							<option value="ZMB" data-image="/Common/images/nation/ZMB.svg" <c:if test="${nation == 'ZMB'}">selected="selected"</c:if>>Zambia</option>
							<option value="ZWE" data-image="/Common/images/nation/ZWE.svg" <c:if test="${nation == 'ZWE'}">selected="selected"</c:if>>Zimbabwe</option>
						</select>
						<span><spring:message code="msg_0417"/></span>
					</div>
					
					<div class="atm_memjoin_opt">
						<p class="atm_memjoin_c2"><spring:message code="msg_0418"/></p>
						<select name="lang" id="joinLang">
						  	<option value="" <c:if test="${lang == ''}">selected="selected"</c:if>>-- select one --</option>
							<option value="ko" <c:if test="${lang == 'ko'}">selected="selected"</c:if>>한국어</option>					
							<option value="en" <c:if test="${lang == 'en'}">selected="selected"</c:if>>English</option>
							<option value="es" <c:if test="${lang == 'es'}">selected="selected"</c:if>>Español</option>
							<option value="fr" <c:if test="${lang == 'fr'}">selected="selected"</c:if>>Français</option>
							<option value="pt" <c:if test="${lang == 'pt'}">selected="selected"</c:if>>Português</option>
							<option value="de" <c:if test="${lang == 'de'}">selected="selected"</c:if>>Deutsch</option>
							<option value="ar" <c:if test="${lang == 'ar'}">selected="selected"</c:if>>العربية</option>
							<option value="fa" <c:if test="${lang == 'fa'}">selected="selected"</c:if>>فارسی</option>
							<option value="ru" <c:if test="${lang == 'ru'}">selected="selected"</c:if>>Русский</option>
							<option value="ja" <c:if test="${lang == 'ja'}">selected="selected"</c:if>>日本語</option>
							<option value="it" <c:if test="${lang == 'it'}">selected="selected"</c:if>>Italiano</option>
							<option value="zh" <c:if test="${lang == 'zh'}">selected="selected"</c:if>>中文</option>
							<option value="vi" <c:if test="${lang == 'vi'}">selected="selected"</c:if>>Tiếng Việt</option>
							<option value="hi" <c:if test="${lang == 'hi'}">selected="selected"</c:if>>हिन्दी</option>
							<option value="bn" <c:if test="${lang == 'bn'}">selected="selected"</c:if>>বাংলা</option>
							<option value="id" <c:if test="${lang == 'id'}">selected="selected"</c:if>>Bahasa Indonesia</option>
							<option value="ms" <c:if test="${lang == 'ms'}">selected="selected"</c:if>>Bahasa Melayu</option>
							<option value="tr" <c:if test="${lang == 'tr'}">selected="selected"</c:if>>Türkçe</option>
							<option value="th" <c:if test="${lang == 'th'}">selected="selected"</c:if>>ไทย</option>
							<option value="mn" <c:if test="${lang == 'mn'}">selected="selected"</c:if>>Монгол хэл</option>
						</select>
						<span><spring:message code="msg_0419"/></span>
					</div>
					
					
					<div class="atm_memjoin_opt">
						
					</div>
				</div>
				<div>
					<p class="atm_memjoin_btn" onClick="javascript:JoinChk_n('join_frm_sch','/default/joinSave_n2');"><spring:message code="msg_0420"/></p>
				</div>
			</div>
		</form>
	</div>
</div>
<div id="top_btn">
	<a href="javascript:void(0);"> 
		<span>
			<img src="/Common/images/top_btn_arrow.svg" alt="top_btn">
		</span>
	</a>
</div>
<iframe name="join_ifrm" width="100%" height="0" frameborder="0"></iframe>
<script src="/pub/default/joinInput_n2/joinInput_n2.js?ver=1.3"></script>
</body>