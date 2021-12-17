<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<head>
	<script src="http://code.jquery.com/jquery-1.12.4.js"></script>
	<link rel="stylesheet" href="/pub/default/login/login.css?ver=1.3">
	<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=2.2">
	
	<!-- 210526휴대폰 나라별 코드 수정 -->
	<link rel="stylesheet" href="/Common/CSS_new/dd.css">
	<script src="/Common/js_new/default/jquery.dd.min.js"></script>
	<script>
	    $(document).ready(function (){
	        try {
	            $('.atm_login_select').msDropDown();
	        } catch (e) {
	            alert(e.message);
	        }
	    });
	</script>
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<div id="atm_login_wrapper" class="site">
	<div class="center site-content">
		<!--wrapper start -->
		<div class="atm_login_con">
			<form name="frm_sch" id="frm_sch" method="post" onSubmit="return false;">
				<h1 class="atm_loginmain">Login</h1>
				
				<div class="atm_login_id">
					<select name="UserCountry" class="atm_login_select" onchange="document.frm_sch.UserPhone.value = ''; document.frm_sch.UserPhone.select();">
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
						<option value="691" data-image="/Common/images/nation/FSM.svg"  <c:if test="${areaCode == '691'}">selected="selected"</c:if>>F.S. Micronesia(+691)</option>
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
						<option value="265"  data-image="/Common/images/nation/MWI.svg"<c:if test="${areaCode == '265'}">selected="selected"</c:if>>Malawi(+265)</option>
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
						<option value="58" data-image="/Common/images/nation/VEN.svg" <c:if test="${areaCode == '58'}">selected="selected"</c:if>>Venezuela(+58)</option>
						<option value="84" data-image="/Common/images/nation/VNM.svg" <c:if test="${areaCode == '84'}">selected="selected"</c:if>>Việt Nam(+84)</option>
						<option value="967" data-image="/Common/images/nation/YEM.svg" <c:if test="${areaCode == '967'}">selected="selected"</c:if>>Yemen(+967)</option>
						<option value="260" data-image="/Common/images/nation/ZMB.svg" <c:if test="${areaCode == '260'}">selected="selected"</c:if>>Zambia(+260)</option>
						<option value="263" data-image="/Common/images/nation/ZWE.svg" <c:if test="${areaCode == '263'}">selected="selected"</c:if>>Zimbabwe(+263)</option>
					</select>
					<input type="text" name="UserPhone" class="atm_login_input1" placeholder='<spring:message code="msg_0391"/>' onFocus="this.select()" onKeyUp="checkNum(this);"/>
				</div>
				<input type="password" name="UserPassword" class="atm_login_input1" placeholder='<spring:message code="msg_0412"/>' onFocus="this.select()" />
				<div class="atm_login_keep">
					<input type="checkbox" name="LoginDefault" id="LoginDefault" value="Y" checked>
					<label class="checkbox-wrap" for="LoginDefault"></label>
					<p><spring:message code="msg_0637"/></p>
				</div>
				<br>
				<p class="atm_login_btn" onClick="procLogin()"><spring:message code="msg_0169"/></p><!-- LoginChk_ssl('frm_sch','/default/loginCheck_n2'); -->
				<div class="atm_login_line"></div>
				<p class="atm_login_c4">
					<span class="atm_whitespace">
						<a href="/default/joinRule">
							<span class="atm_login_c4"><spring:message code="msg_0170"/></span>
						</a>
						&nbsp;|&nbsp;
						<a href="/default/password/passwordReset">
							<span class="atm_login_c4"><spring:message code="msg_0392"/></span>
						</a>
					</span>
					<!--<span class="atm_login_color_gray">&nbsp;|&nbsp;</span>
					<span class="atm_whitespace"><a href="id_pass.asp"><span class="atm_login_c4"><spring:message code="msg_0392"/></span></a></span>-->
				</p>
			</form>
		</div>
		<!--wrapper end -->
	</div>
</div>

<iframe name="login_ifrm" width="100%" height="0" frameborder="0"></iframe>
	
<script>
	//var referer = document.referrer;
	//console.log('${referrer}');
	function LoginChk_ssl(form, url) {
		frm_sch.action = url;
		frm_sch.submit();
	}
	function procLogin() {
		var formData = $("#frm_sch").serialize();
		/*https://m.blog.naver.com/PostView.nhn?blogId=racoon_z&logNo=221080162986&proxyReferer=https:%2F%2Fwww.google.com%2F */
		$.ajax({
			url: '/default/loginCheck_n2',
			cache : false,
			type: 'POST',
			data: formData,
			dataType: 'json',
			success : function(data, status, xhr) {
				//console.log(data.arr);
				var strMsg = data.arr.strMsg;
				var strUrl = data.arr.strUrl;
				var strTarget = data.arr.strTarget;
				
				goPage(strMsg, strUrl);
			},
			error : function(error) {
		    	alert(getLangStr("jsm_0070"));
			}
		});
	}
	function goPage(strMsg, strUrl) {
		if(strMsg != '') {
			alert(strMsg);
		}
		if(strUrl == 'back') {
			history.back();
		}
		else if(strUrl != '') {
			location.href = strUrl;
		}
	}
	$("input[name=UserPhone]").on('keydown', function (event) {
		if (event.keyCode == 13) {
			//LoginChk_ssl('frm_sch', '/default/loginCheck_n2'); 
			procLogin();
		}
	});
	$("input[name=UserPassword]").on('keydown', function (event) {
		if (event.keyCode == 13) {
			//LoginChk_ssl('frm_sch', '/default/loginCheck_n2');
			procLogin();
		}
	});
</script>
<script src="/pub/default/login/login.js?ver=1.1"></script>
</body>