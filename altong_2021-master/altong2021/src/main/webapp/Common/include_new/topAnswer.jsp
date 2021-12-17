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



<div class="swiper-container" id="swiper_container">
<div class="center">
	<ul>
	<% if(FlagSectionALL == "Y"){ %>
	<li class="swiper-slide" onClick="javascript:location.href='<%=CurPageName%>';"  style="text-align:center;">
	<a href="javascript:void(0);">
	<img src="/pub/css/category/category11<% if(strSection1 == "0") {%><% }%>.svg" /><span><spring:message code="msg_0161"/></span><!-- CommonUtil.getLangMsg(request, "cate0"); -->
	</a>
	
	</li>
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
				//System.out.println("strCd : " + strCd);
				strCdNm = CommonUtil.getLangMsg(request, "cate"+strCd);; //  answerSection1.get(i).get("SectionName1").toString()
				sectionImgOn = "category/category" + strCd + "_on.svg";
				sectionImgOff = "category/category" + strCd + ".svg";
				
				//System.out.println("strCd : " + strCd);
				//System.out.println("strCdNm : " + strCdNm);
				
				if(strSection1 == strCd){
					sectionImg = sectionImgOn;
				}
				else {
					sectionImg = sectionImgOff;
				}
				if(strSection1 == strCd){ %>
					<li class="swiper-slide notFavorite" id="categoryCode<%=strCd%>" onClick=""><a href="javascript:void(0);"><img src="/pub/css/<%=sectionImg%>" /><font color="yellow"><span><%=strCdNm%></span></font></a></li>
				<%} else {%>
					<li class="swiper-slide notFavorite" id="categoryCode<%=strCd%>" onClick=""><a href="javascript:void(0);"><img src="/pub/css/<%=sectionImg%>" /><span><%=strCdNm%></span></a></li>
				<%} 
			}else {
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
				sectionImgOn = "category/category" + strCd + "_on.svg";
				sectionImgOff = "category/category" + strCd + ".svg";
				
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
				
				if(!urlParam.equals("")) {
					String[] urlParamArr = urlParam.split("&");
					
					String cate1 = "";
					String cate2 = "";
					String cate3 = "";
					String cate4 = "";
					String cate5 = "";
					for(int j = 0; j < urlParamArr.length; j++) {
						String[] usrParamValArr = urlParamArr[j].split("=");
						
						String parma = usrParamValArr[0];
						String vals =  usrParamValArr[1];
						
						if(parma.equals("SectionCode1")) {
							cate1 = vals;
						}
						if(parma.equals("SectionCode2")) {
							cate2 = vals;
						}
						if(parma.equals("SectionCode3")) {
							cate3 = vals;
						}
						if(parma.equals("SectionCode4")) {
							cate4 = vals;
						}
						if(parma.equals("SectionCode5")) {
							cate5 = vals;
						}
					}
					
					if(!cate5.equals("0")) {
						//System.out.println("cate5 : " + cate5);
						strCdNm = CommonUtil.getLangMsg(request, "cate5_" + cate5);
					}
					else if(!cate4.equals("0")) {
						//System.out.println("cate4 : " + cate4);
						strCdNm = CommonUtil.getLangMsg(request, "cate4_" + cate4);
					}
					else if(!cate3.equals("0")) {
						//System.out.println("cate3 : " + cate3);
						strCdNm = CommonUtil.getLangMsg(request, "cate3_" + cate3);
					}
					else if(!cate2.equals("0")) {
						//System.out.println("cate2 : " + cate2);
						strCdNm = CommonUtil.getLangMsg(request, "cate2_" + cate2);
					}
					else if(!cate1.equals("0")) {
						//System.out.println("cate1 : " + cate1);
						strCdNm = CommonUtil.getLangMsg(request, "cate" + cate1);
					}
					
				}
				
				strCdNm = strCdNm.replaceAll(";", "<br/>").replaceAll("@", "");
				//System.out.println("strCdNm : " + strCdNm);
				
				if(bYelloFlag == true) {
				%>
				<li>
					<div class="favorite-swiper-slide swiper-slide">
						<a href="<%=CurPageName%>?<%=urlParam%>'">
							<img src="/pub/css/<%=sectionImg%>" class="atm_ranka_icon"/>
							<span><%=strCdNm%></span>
						</a>
					</div>
				</li>
				<%
				}
				else {
				%>
				<li>
					<div class="favorite-swiper-slide swiper-slide">
						<a href="<%=CurPageName%>?<%=urlParam%>'">
							<img src="/pub/css/<%=sectionImg%>" class="atm_ranka_icon"/>
							<span><%=strCdNm%></span>
						</a>
					</div>
				</li>
				<%
				}
			}
		}
	}
	%>
	</ul>
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
	//console.log('실행중!');
	$.ajax({
		type: 'post',
		url: '/answer/fn_SQL_Answer_Section1',
		dataType: 'json',
		success: function (r) {
			for(var i = 0; i < r.length; i++){
				
				var code =  r[i]["SectionCode1"];
				var codeName =  r[i]["SectionName1"];
				var sectionImgOn = "/category/icon_" + code + "_on.png"
				var sectionImgOff = "/category/icon_" + code + ".png"
				//console.log('code : ' + code);
				//console.log('codeName : ' + codeName);
				var text = '<div class="swiper-slide notFavorite" id="categoryCode' + code + '" onclick="javascript:location.href=\'/answer/questionList?Section1=' + code +  '\'">';
				text += '<img src = "/UploadFile/Section/' + sectionImgOff + '" /><font>' + codeName + "</font></div>";
				$(".swiper-wrapper").append(text);
				
			}
		}
	});
}
function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
</script>