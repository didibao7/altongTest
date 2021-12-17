<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.math.*" %>
<%@ page import="com.altong.web.logic.LogAlmoneyVO" %>  
<%@ page import="com.altong.web.logic.util.CommonUtil" %>   
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %> 
<%
	String StrFileName = request.getRequestURL().toString();
	pageContext.setAttribute("StrFileName", StrFileName);
	
	List<HashMap<String, Object>> answerSection1 = new ArrayList<HashMap<String, Object>>();
	String FlagSectionALL = "N";
	
	if(StrFileName.contains("favoriteList")) {
		//[SP2_MYFAVORITE_CATEGORY_GET] " & Session("UserSeq") 'fn_SQL_Answer_Favorite()
		answerSection1 = CommonUtil.getMyFavoriteCategoryGetSp(request, response);
		//검색 결과 0 이면, FlagSectionALL = "Y";
		
		if(answerSection1.size() == 0) {
			FlagSectionALL = "Y";
		}
	}
	else {
		answerSection1 = CommonUtil.geAnswer_Section1();
		FlagSectionALL = "Y";
	}
	
	String CurPageName = "";
	if(request.getAttribute("CurPageName") != null && request.getAttribute("CurPageName") != "" && request.getAttribute("CurPageName") != "null") {
		String curPgNm = String.valueOf(request.getAttribute("CurPageName"));
		if(curPgNm.equals("rankMember")) {
			CurPageName = "/answer/questionList";
		}
		else {
			CurPageName = curPgNm;
		}
	}
	
	String strSection1 = "";
	if(request.getAttribute("Section1") != null) {
		strSection1 = String.valueOf( request.getAttribute("Section1") );
	}
	//System.out.println("strSection1 : " + strSection1);
%>

<div class="atm_ranktopQ_1">
	<span class="atm_ranktopA_c2"><p class="atm_ranktopA_c3">답변채택수익</p>&nbsp;<p class="atm_ranktopA_c4"><span id="choice"></span>&nbsp;알</p></span>&nbsp;
	<span class="atm_ranktopA_c2"><p class="atm_ranktopA_c3">답변열람수익</p>&nbsp;<p class="atm_ranktopA_c4"><span id="viewA"></span>&nbsp;알</p></span>
</div>
<style>
.atm_ranka_icon{ display:block; width:32px; padding: 0; margin:auto; padding-bottom:0px; }
.swiper-container { background-color:#2ac1bc; text-align:center; position:relative;  padding:2px 0px 0px 0px; padding-left:5px; }
.swiper-wrapper{ white-space:nowrap; text-align:center; padding:0px 0px 5px 0px; overflow-y: hidden; float: left;
<% if(!StrFileName.contains("favoriteList")) { %>
width: 100%;
<%}%>
}
.swiper-slide {
<% if(!StrFileName.contains("favoriteList")) { %>
	width:9% !important; 
<%} else {%>
	width:10% !important; 
<%}%>
	background-color:#2ac1bc; text-align:center; position:relative;  padding:5px 0px 3px 0px;  font-size:11px; font-weight:bold; letter-spacing:-0.5px; color:#fff; cursor:pointer;}
.favorite-swiper-slide { width:21% !important; }
.atm_direct_R1{ width:40px; position:relative; /*top:-15px;*/
<% if(!StrFileName.contains("favoriteList")) { %>
	display: none;
<%}%>
}

@media screen and (min-width: 800px) {
.atm_direct_R1 { display: none; }
.atm_ranka_icon{ width:50px; padding: 0; margin:auto; padding-bottom:0px; }
.swiper-slide { width:65px !important; background-color:#2ac1bc; text-align:center; position:relative;  padding:5px 0px 3px 0px;  font-size:11px; font-weight:bold; letter-spacing:-0.5px; color:#fff; cursor:pointer;}
.swiper-container_pc{  position:relative; background-color:#2ac1bc; }
.swiper-container{ width:800px;}
.swiper-wrapper {width: 100%;}

/*랭킹탭*/
.atm_ranka_tabc1   {font-size:14px;}
.atm_ranka_tabc2   {font-size:14px;}
.atm_ranka_tabc3   {font-size:14px;}
.atm_ranka_tabc1_on{font-size:14px;}
.atm_ranka_tabc2_on{font-size:14px;}
.atm_ranka_tabc3_on{font-size:14px;}
.atm_ranka_tabc4   {font-size:14px;}
.atm_ranka_tabc5   {font-size:14px;}
.atm_ranka_tabc4_on{font-size:14px;}
.atm_ranka_tabc5_on{font-size:14px;}
.atm_ranka_tabc6   {font-size:14px;}
.atm_ranka_tabc7   {font-size:14px;}
.atm_ranka_tabc8   {font-size:14px;}
.atm_ranka_tabc6_on{font-size:14px;}
.atm_ranka_tabc7_on{font-size:14px;}
.atm_ranka_tabc8_on{font-size:14px;}
.atm_ranka_tabc_line{font-size:14px;}
.atm_ranka_tabc_line2{font-size:14px;}

/*답변랭크탑*/
.atm_ranktopA_c3{ font-size:12px; }
.atm_ranktopA_c4{ font-size:14px; }

}
</style>
<div class="atm_ranktopA_1">
</div>
<div class="swiper-container_pc">
<div class="swiper-container">
	<div class="swiper-wrapper">
	<% if(FlagSectionALL == "Y"){ %>
	<div class="swiper-slide" onClick="javascript:location.href='<%=CurPageName%>';"  style="text-align:center;">
	<img src="${IMG_URL}/UploadFile/Section/category/icon_0<% if(strSection1 == "0") {%>_on<% }%>.png" class="atm_ranka_icon"/>전체</div>
	<%} %>
	
	<%
	String strCd = "";
	String strCdNm = "";
	String sectionImgOn = "";
	String sectionImgOff = "";
	String sectionImg = "";
	//System.out.println("answerSection1 : " + answerSection1);
	String forcedSelect1th = "";
	if(request.getParameter("forcedSelect1th") != null) {
		forcedSelect1th = String.valueOf( request.getParameter("forcedSelect1th") );
	}
	
	if(answerSection1.size() > 0) {
		for(int i = 0; i < answerSection1.size(); i++) {
			if(!StrFileName.contains("favoriteList.jsp")) {
				strCd = answerSection1.get(i).get("SectionCode1").toString();
				strCdNm = answerSection1.get(i).get("SectionName1").toString();
				sectionImgOn = "category/icon_" + strCd + "_on.png";
				sectionImgOff = "category/icon_" + strCd + ".png";
				
				if(strSection1 == strCd){
					sectionImg = sectionImgOn;
				}
				else {
					sectionImg = sectionImgOff;
				}
	%>
		<%if(strSection1 == strCd){ %>
			<div class="swiper-slide notFavorite" id="categoryCode<%=strCd%>" onClick="/*javascript:location.href='<%=CurPageName%>?Section1=<%=strCd%>';*/"><img src="${IMG_URL}/UploadFile/Section/<%=sectionImg%>" class="atm_ranka_icon"/><font color="yellow"><%=strCdNm%></font></div>
		<%} else {%>
			<div class="swiper-slide notFavorite" id="categoryCode<%=strCd%>" onClick="/*javascript:location.href='<%=CurPageName%>?Section1=<%=strCd%>';*/"><img src="${IMG_URL}/UploadFile/Section/<%=sectionImg%>" class="atm_ranka_icon"/><%=strCdNm%></div>
		<%} %>
	
	<%		}
			else {
				boolean bYelloFlag = false;
				String urlParam = "";
				/**/
				for(int j = 1; j <= 5; j++) {
					String tmpCodeName = "";
					String targetCdNm = "";
					switch(j) {
						case 1:
							targetCdNm = answerSection1.get(i).get("SectionName1").toString();
							break;
						case 2:
							targetCdNm = answerSection1.get(i).get("SectionName2").toString();
							break;
						case 3:
							targetCdNm = answerSection1.get(i).get("SectionName3").toString();
							break;
						case 4:
							targetCdNm = answerSection1.get(i).get("SectionName4").toString();
							break;
						case 5:
							targetCdNm = answerSection1.get(i).get("SectionName5").toString();
							break;
					}
					
					tmpCodeName = targetCdNm;
					
					if(tmpCodeName.equals("") || tmpCodeName.equals("null") || tmpCodeName == null) { continue; }
					if(tmpCodeName != "") {
						strCdNm = tmpCodeName;
					}
				}
				
				strCd = answerSection1.get(i).get("SectionCode1").toString();
				sectionImgOn = "category/icon_" + strCd + "_on.png";
				sectionImgOff = "category/icon_" + strCd + ".png";
				
				bYelloFlag = true;
				urlParam = "";
				
				for(int j = 1; j <= 5; j++) {
					/**/
					String targetCd = "";
					switch(j) {
						case 1:
							targetCd = answerSection1.get(i).get("SectionCode1").toString();
							break;
						case 2:
							targetCd = answerSection1.get(i).get("SectionCode2").toString();
							break;
						case 3:
							targetCd = answerSection1.get(i).get("SectionCode3").toString();
							break;
						case 4:
							targetCd = answerSection1.get(i).get("SectionCode4").toString();
							break;
						case 5:
							targetCd = answerSection1.get(i).get("SectionCode5").toString();
							break;
					}
					if(targetCd == "" || targetCd == null) { break; }
					strCd = targetCd;
					
					urlParam += "SectionCode" + j + "=" + strCd + "&";
					
					
					switch(j) {
						case 0:
							if(!strCd.equals(request.getParameter("SectionCode1"))) {
								//FavoriteList.asp에서 강제로 첫 번째 관심분야가 선택되었을 경우는 첫 번째 관심분야의 YelloFlag를 false 시키지 않는다. (by 김태환 20181031)
								if(i != 0 || forcedSelect1th != "Y") {
									bYelloFlag = false;
								}
							}
							break;
						case 1:
							if(!strCd.equals(request.getParameter("SectionCode2"))) {
								//FavoriteList.asp에서 강제로 첫 번째 관심분야가 선택되었을 경우는 첫 번째 관심분야의 YelloFlag를 false 시키지 않는다. (by 김태환 20181031)
								if(i != 0 || forcedSelect1th != "Y") {
									bYelloFlag = false;
								}
							}
							break;
						case 2:
							if(!strCd.equals(request.getParameter("SectionCode3"))) {
								//FavoriteList.asp에서 강제로 첫 번째 관심분야가 선택되었을 경우는 첫 번째 관심분야의 YelloFlag를 false 시키지 않는다. (by 김태환 20181031)
								if(i != 0 || forcedSelect1th != "Y") {
									bYelloFlag = false;
								}
							}
							break;
						case 3:
							if(!strCd.equals(request.getParameter("SectionCode4"))) {
								//FavoriteList.asp에서 강제로 첫 번째 관심분야가 선택되었을 경우는 첫 번째 관심분야의 YelloFlag를 false 시키지 않는다. (by 김태환 20181031)
								if(i != 0 || forcedSelect1th != "Y") {
									bYelloFlag = false;
								}
							}
							break;
						case 4:
							if(!strCd.equals(request.getParameter("SectionCode5"))) {
								//FavoriteList.asp에서 강제로 첫 번째 관심분야가 선택되었을 경우는 첫 번째 관심분야의 YelloFlag를 false 시키지 않는다. (by 김태환 20181031)
								if(i != 0 || forcedSelect1th != "Y") {
									bYelloFlag = false;
								}
							}
							break;
					}
						
					
					if(!strCd.equals(request.getParameter("SectionCode"+j))) {
						//FavoriteList.asp에서 강제로 첫 번째 관심분야가 선택되었을 경우는 첫 번째 관심분야의 YelloFlag를 false 시키지 않는다. (by 김태환 20181031)
						if(i != 0 || forcedSelect1th != "Y") {
							bYelloFlag = false;
						}
					}
						
				}
				
				if(bYelloFlag == true) {
					sectionImg = sectionImgOn;
				}
				else {
					sectionImg = sectionImgOff;
				}
				
				strCdNm = strCdNm.replaceAll(";", "<br/>").replaceAll("@", "");
				
				
				if(bYelloFlag == true) {
				%>
				<div class="favorite-swiper-slide swiper-slide" onClick="javascript:location.href='<%=CurPageName%>?<%=urlParam%>';"><img src="${IMG_URL}/UploadFile/Section/<%=sectionImg%>" class="atm_ranka_icon"/><font color="yellow"><%=strCdNm%></font></div>
				<%
				}
				else {
				%>
				<div class="favorite-swiper-slide swiper-slide" onClick="javascript:location.href='<%=CurPageName%>?<%=urlParam%>';"><img src="${IMG_URL}/UploadFile/Section/<%=sectionImg%>" class="atm_ranka_icon"/><%=strCdNm%></div>
				<%
				}
			}
		}
	}
	%>
	</div>
	</div>
	</div>

<script type="text/javascript">
var FlagSectionALL;

$(document).ready(function(){
	FlagSectionALL = "Y";
	getChoiceAndViewA();
	//getQuestionCategory();
	
	
	const $direct_R1 = $('.atm_direct_R1');
	const $swiper = $('.swiper-wrapper');
	$direct_R1.height($swiper.height());
	$direct_R1.width($swiper.width()/10);
});

$('.atm_direct_R1').click(function() {
	const $swiper = $('.swiper-wrapper');
	var current = $swiper.scrollLeft();
	$swiper.scrollLeft(current + $swiper.width()/10)
});

//답변채택수익, 열람수익 가져오기
function getChoiceAndViewA(){
	$.ajax({
		type: 'post',
		url: '/answer/getChoiceAndViewA',
		dataType: 'json',
		success: function (r) {
			$("#choice").html(numberWithCommas(r.SUM_A_ChoicedAlmoney));
			$("#viewA").html(numberWithCommas(r.SUM_A_ViewAlmoney));
		}
	});
}

//카테고리 가져오기
function getQuestionCategory(){
	
	$.ajax({
		type: 'post',
		url: '/answer/fn_SQL_Answer_Section1',
		dataType: 'json',
		success: function (r) {
			for(var i = 0; i < r.length; i++){
				
				var code =  r[i]["SectionCode1"];
				var codeName =  r[i]["SectionName1"];
				var sectionImgOn = "/icon_" + code + "_on.png"
				var sectionImgOff = "/icon_" + code + ".png"
			
				var text = '<div class="swiper-slide notFavorite" id="categoryCode' + code + '" onclick="javascript:location.href=\'/answer/questionList?Section1=' + code +  '\'">';
				text += '<img src = "/UploadFile/Section/' + sectionImgOff + '" class="atm_ranka_icon"/><font>' + codeName + "</font></div>";
				$(".swiper-wrapper").append(text);
				
			}
		}
	});
}

function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

</script>	
