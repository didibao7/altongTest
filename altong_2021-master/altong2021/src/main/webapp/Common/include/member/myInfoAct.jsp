<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@ page import="com.altong.web.logic.member.MemberVO" %>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ page import="org.json.simple.parser.JSONParser" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="com.google.gson.Gson" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %> 
<%
JSONObject global_my = (JSONObject)CommonUtil.getGlobal(request, response);
String data1 = request.getParameter("mbMap");
//System.out.println("data1 : " + data1);

Map<String, Object> dataHash1 = new Gson().fromJson(data1, Map.class);

/**/
int countQ = (int)Math.floor( Float.valueOf(String.valueOf( dataHash1.get("CountQ") ) ) );
BigDecimal sumQ = new BigDecimal( String.valueOf( dataHash1.get("SumQ") ) );
int countNotAnswerQ = (int)Math.floor( Float.valueOf(String.valueOf( dataHash1.get("CountNotAnswerQ") ) ) );
int countRecivedAnswer = (int)Math.floor( Float.valueOf(String.valueOf( dataHash1.get("CountRecivedAnswer") ) ) );
int countC = (int)Math.floor( Float.valueOf(String.valueOf( dataHash1.get("CountC") ) ) );
int countNotChoiceQ = (int)Math.floor( Float.valueOf(String.valueOf( dataHash1.get("CountNotChoiceQ") ) ) );
int countA = (int)Math.floor( Float.valueOf(String.valueOf( dataHash1.get("CountA") ) ) );
BigDecimal sumA = new BigDecimal( String.valueOf( dataHash1.get("SumA") ) );
int countChoicedA = (int)Math.floor( Float.valueOf(String.valueOf( dataHash1.get("CountChoicedA") ) ) );
int countZzim = (int)Math.floor( Float.valueOf(String.valueOf( dataHash1.get("CountZzim") ) ) );
int countV = (int)Math.floor( Float.valueOf(String.valueOf( dataHash1.get("CountV") ) ) );


//나의 댓글 누락
int qReplyCount = (int)Math.floor( Float.valueOf(String.valueOf( dataHash1.get("QReplyCount") ) ) );
int aReplyCount = (int)Math.floor( Float.valueOf(String.valueOf( dataHash1.get("AReplyCount") ) ) );
int countR = qReplyCount + aReplyCount;
//받은 댓글 누락
int qRecivedReplyCount = (int)Math.floor( Float.valueOf(String.valueOf( dataHash1.get("QRecivedReplyCount") ) ) );
int aRecivedReplyCount = (int)Math.floor( Float.valueOf(String.valueOf( dataHash1.get("ARecivedReplyCount") ) ) );
int countRecivedR = qRecivedReplyCount + aRecivedReplyCount;


pageContext.setAttribute("countQ", countQ);
pageContext.setAttribute("sumQ", sumQ);
pageContext.setAttribute("countNotAnswerQ", countNotAnswerQ);
pageContext.setAttribute("countRecivedAnswer", countRecivedAnswer);
pageContext.setAttribute("countC", countC);
pageContext.setAttribute("countNotChoiceQ", countNotChoiceQ);
pageContext.setAttribute("countA", countA);
pageContext.setAttribute("sumA", sumA);
pageContext.setAttribute("countChoicedA", countChoicedA);
pageContext.setAttribute("countZzim", countZzim);
pageContext.setAttribute("countV", countV);
pageContext.setAttribute("countR", countR);
pageContext.setAttribute("countRecivedR", countRecivedR);


String userNickName = String.valueOf( global_my.get("UserNickName") );
%>
<section class="default_setting">
	<div class="atm_mf_pcwidth">
		<div class="atm_mf_con_tt0" style="cursor:pointer;">
			<p class="atm_mf_c0" onClick="location.href='/member/myQuestion';">
				질문
				- <span class="atm_mf_color_gre"><fmt:formatNumber value="${countQ > 0 ? countQ : 0}" pattern="#,###" /></span> 건 
				- <span class="atm_mf_color_gre"><fmt:formatNumber value="${sumQ}" pattern="#,###.0" /></span> 알
			</p>
			<img src="/Common/images/btn_minus0.png" class="atm_mf_btn_R1 on"/>
		</div>
		<div class="atm_mf_con_slide">
			<div class="atm_mf_con_tt1" onClick="location.href='/member/myQuestion?Flag=notAnswered'">
				<p class="atm_mf_c1">
					답변 미등록된 질문 - <span class="atm_mf_color_gre"><fmt:formatNumber value="${countNotAnswerQ}" pattern="#,###" /></span> 건
				</p>
			</div>
			<div class="atm_mf_con_tt1" onClick="location.href='/member/myQuestion?Flag=answered'">
				<p class="atm_mf_c1">
					답변 등록된 질문 - <span class="atm_mf_color_gre"><fmt:formatNumber value="${(countQ > 0 ? countQ : 0) - countNotAnswerQ}" pattern="#,###" /></span> 건
				</p>                
			</div>
			<div class="atm_mf_con_tt1" onClick="location.href='myRecivedAnswer'">
				<p class="atm_mf_c1">
					등록된 답변 갯수 - <span class="atm_mf_color_gre"><fmt:formatNumber value="${countRecivedAnswer}" pattern="#,###" /></span> 개
				</p>
			</div>
			<div class="atm_mf_con_tt1" onClick="location.href='myRecivedAnswer?Flag=choice'">
				<p class="atm_mf_c1">
					채택한 답변 - <span class="atm_mf_color_gre"><fmt:formatNumber value="${countC}" pattern="#,###" /></span> 건
				</p>
			</div>
			<div class="atm_mf_con_tt1" onClick="location.href='/member//myQuestion?Flag=N'">
				<p class="atm_mf_c1">
					채택 대기중 - <span class="atm_mf_color_gre"><fmt:formatNumber value="${countNotChoiceQ}" pattern="#,###" /></span> 건
				</p>
			</div>
			<div class="atm_mf_con_tt1">
				<p class="atm_mf_c1">
					질문 채택률 - <span class="atm_mf_color_gre"><fmt:formatNumber value="${countQ > 0 ? countQ / countC * 100 : 0}" pattern="#,###.0" /></span> %
				</p>
			</div>
		</div>
	</div>
	<div style="height:12px;"></div>
	<div class="atm_mf_pcwidth">
		<div class="atm_mf_con_tt0" style="cursor:pointer;">
			<p class="atm_mf_c0" onClick="location.href='/member/myAnswer';">
				답변 
				- <span class="atm_mf_color_gre"><fmt:formatNumber value="${countA}" pattern="#,###" /></span> 건 
				- <span class="atm_mf_color_gre"><fmt:formatNumber value="${sumA}" pattern="#,###.0" /></span> 알
			</p>
			<img src="/Common/images/btn_minus0.png" class="atm_mf_btn_R1 on"/>
		</div>
		<div class="atm_mf_con_slide">
			<div class="atm_mf_con_tt1" onClick="location.href='/member/myAnswer?FlagChoice=Y'">
				<p class="atm_mf_c1">
					채택된 답변 
					- <span class="atm_mf_color_gre"><fmt:formatNumber value="${countChoicedA}" pattern="#,###" /></span> 건
				</p>
			</div>
			<div class="atm_mf_con_tt1">
				<p class="atm_mf_c1">
					답변 채택률 
					- <span class="atm_mf_color_gre"><fmt:formatNumber value="${countA > 0 ? countChoicedA / countA * 100 : 0}" pattern="#,###.0" /></span> %
				</p>
			</div>
		</div>
	</div>
	<div style="height:12px;" id="white_space1"></div>
	<div class="atm_mf_pcwidth">
		<div class="atm_mf_con_tt0">
			<p class="atm_mf_c0">활동</p>
			<img src="/Common/images/btn_minus0.png" class="atm_mf_btn_R1 on"/>
		</div>
		<div class="atm_mf_con_slide"> 
			<div class="atm_mf_con_tt1" onClick="location.href='/member/myZzim';">
				<p class="atm_mf_c1">찜 - <span class="atm_mf_color_gre"><fmt:formatNumber value="${countZzim}" pattern="#,###" /></span> 건</p>
			</div>
			<div class="atm_mf_con_tt1" onClick="location.href='/member/myView';">
				<p class="atm_mf_c1">열람 - <span class="atm_mf_color_gre"><fmt:formatNumber value="${countV}" pattern="#,###" /></span> 건</p>
			</div>
			<div class="atm_mf_con_tt1" onClick="location.href='/member/myReply';">
				<p class="atm_mf_c1">나의 댓글 - <span class="atm_mf_color_gre"><fmt:formatNumber value="${countR}" pattern="#,###" /></span> 건</p>
			</div>
			<div class="atm_mf_con_tt1" onClick="location.href='/member/myReply?FlagMe=Y';">
				<p class="atm_mf_c1">받은 댓글 - <span class="atm_mf_color_gre"><fmt:formatNumber value="${countRecivedR}" pattern="#,###" /></span> 건</p>
			</div>
			<%
				String alt = "-야로-슈크-디디바오-관리자-누리-목련-영웅본색-나래-알폰스-알스-구름-";
				if (alt.contains("-"+userNickName+"-") == true) {
			%>
			<div class="atm_mf_con_tt1" onClick="location.href='/member/mySiren';">
				<p class="atm_mf_c1">신고 / 벌점</p>
			</div>
			<% } %>
		</div>
	</div>
	<div style="height:12px;" id="white_space1"></div>
	<div class="atm_mf_pcwidth">
		<div class="atm_mf_con_tt0">
			<p class="atm_mf_c0">이웃</p>
			<img src="/Common/images/btn_minus0.png" class="atm_mf_btn_R1 on"/>
		</div>
		<div class="atm_mf_con_slide">
			<div class="atm_mf_con_tt1" onClick="javascript:location.href='/member/myFriend';">
				<p class="atm_mf_c1"> 친구 / 쪽지 차단</p>
			</div>
			<div class="atm_mf_con_tt1" onClick="javascript:location.href='/member/myPartner?FlagPartner=M';">
				<p class="atm_mf_c1"> 멘토 / 멘티</p>
			</div>
			<div class="atm_mf_con_tt1" onClick="javascript:location.href='/member/myRecommend';">
				<p class="atm_mf_c1">추천인 / ANSWERer</p>
			</div>
		</div>
	</div>
	<div style="height:12px;" id="white_space2"></div>
	<div class="atm_mf_pcwidth">
		<div class="atm_mf_con_tt0">
			<p class="atm_mf_c0">설정</p>
			<img src="/Common/images/btn_minus0.png" class="atm_mf_btn_R1 on"/>
		</div>
		<div class="atm_mf_con_slide">
			<div class="atm_mf_con_tt1" onClick="javascript:location.href='/member/myJoin';">
				<p class="atm_mf_c1">정보 수정</p>
			</div>
			<!-- [수정(2018.02.07): 김현구] '프로필 관리' 메뉴 제거
				<div class="atm_mf_con_tt1" onClick="javascript:location.href='MyProfile.asp';">
					<p class="atm_mf_c1">프로필 관리</p>
				</div>
				--> 
			<div class="atm_mf_con_tt1" onClick="javascript:location.href='/member/interest/myInterest';">
				<p class="atm_mf_c1">관심분야</p>
			</div>
			<div class="atm_mf_con_tt1">
				<p class="atm_mf_c1">광고 설정</p>
			</div>
		</div>
	</div>
</section>