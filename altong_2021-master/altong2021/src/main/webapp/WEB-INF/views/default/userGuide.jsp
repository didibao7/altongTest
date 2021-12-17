<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<head>
<link rel="stylesheet" href="/pub/member/myQuestion/myQuestion.css?ver=1.1">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.4">
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<c:set var="view" value="${param.view}" />
<div id="select_innerwrapper">
   <div class="user_guidewrapper center">
		<div class="atm_edittop_ttbar">
			<h1 class="atm_edittop_c1"><spring:message code="msg_0184"/></h1>
		</div>
		<div class="select_wrapper">
			<div class="select_div">
				<div class="atm_temp_tab">
					<p class="temp_tab_on">
						<spring:message code="msg_0441"/>
					</p>
				</div>
				<div class="atm_temp_tab">
					<p>
						<spring:message code="msg_0442"/>
					</p>
				</div>
			</div>
		</div>
		<div class="select_view" id="select_view01">
	      <div class="userguide_scroll">
	      	<c:if test="${lang == 'ko'}">
	        <img src="/Common/images/tutorial_pic1_ko.png?ver=1.0" class="userguide_img"><br>
	        <img src="/Common/images/tutorial_pic2_ko.png?ver=1.0" class="userguide_img">
	        </c:if>
	        <c:if test="${lang == 'zh'}">
	        <img src="/Common/images/tutorial_pic1_zh.png?ver=1.0" class="userguide_img"><br>
	        <img src="/Common/images/tutorial_pic2_zh.png?ver=1.0" class="userguide_img">
	        </c:if>
	        <c:if test="${lang == 'ja'}">
	        <img src="/Common/images/tutorial_pic1_ja.png?ver=1.0" class="userguide_img"><br>
	        <img src="/Common/images/tutorial_pic2_ja.png?ver=1.0" class="userguide_img">
	        </c:if>
	        <c:if test="${lang != 'ko' and lang != 'zh' and lang != 'ja'}">
	        <img src="/Common/images/tutorial_pic1_en.png?ver=1.0" class="userguide_img"><br>
	        <img src="/Common/images/tutorial_pic2_en.png?ver=1.0" class="userguide_img">
	        </c:if>
	      </div>
	   </div>
	   <div class="select_view" id="select_view02">
			<div class="atm_base_wrapper1">
				<div class="atm_faq_slidG">
					<div class="atm_faq_c1">
						<div class="atm_faq_c1b"><b><spring:message code="msg_0443"/></b><br />
						<spring:message code="msg_0444"/>
						</div>
					</div>
	         	</div>
			</div>
			<div class="atm_faq_wrapper2">
				<div class="atm_faq_div" id="faq1">
					<div class="atm_faq_ttt">
						<p class="atm_faq_c2"><spring:message code="msg_0445"/></p>
						<img src="/pub/default/userGuide/images/btn_direct_down2.png?ver=1.0" class="atm_faq_up" />
						<img src="/pub/default/userGuide/images/btn_direct_down2.png" class="atm_faq_up" />
					</div>
					<div class="atm_faq_con">
						<div class="atm_faq_slidG">
							<div class="atm_faq_c4"><spring:message code="msg_0446"/><b><spring:message code="msg_0447"/></b><spring:message code="msg_0448"/></div>
						</div>
			        </div>
				</div>
				<div class="atm_faq_div" id="faq2">
					<div class="atm_faq_ttt">
						<p class="atm_faq_c2"><spring:message code="msg_0449"/></p> 
						<img src="/pub/default/userGuide/images/btn_direct_down2.png?ver=1.0" class="atm_faq_up" />
					</div>
					<div class="atm_faq_con">
						<div class="atm_faq_slidG">
							<div class="atm_faq_c4"><spring:message code="msg_0450"/><br />
								<spring:message code="msg_0451"/><br /><br />
								<spring:message code="msg_0452"/>,<br />
								<spring:message code="msg_0453"/><br />
								<spring:message code="msg_0454"/><br />
								<spring:message code="msg_0455"/><br />
								<spring:message code="msg_0456"/><br />
								<spring:message code="msg_0457"/><br />
								<spring:message code="msg_0458"/><br />
								<spring:message code="msg_0459"/></br><br />
								<spring:message code="msg_0460"/><br /><br />
								※ <spring:message code="msg_0461"/>
							</div>
							<p class="atm_faq_c4">&nbsp;</p>
						</div>
					</div>
				</div>
				<div class="atm_faq_div" id="faq3">
					<div class="atm_faq_ttt">
						<p class="atm_faq_c2"><spring:message code="msg_0170"/></p> 
						<img src="/pub/default/userGuide/images/btn_direct_down2.png?ver=1.0" class="atm_faq_up" />
					</div>
					<div class="atm_faq_con">
						<div class="atm_faq_slidG">
							<div class="atm_faq_c4">* <spring:message code="msg_0462"/><br />
								* <spring:message code="msg_0463"/><br />
								* <spring:message code="msg_0464"/><b><spring:message code="msg_0465"/></b><spring:message code="msg_0466"/><br /> 
								* <spring:message code="msg_0467"/><br />
								* <spring:message code="msg_0468"/><br />
							<!--&gt;&gt; 이미지 (본인 인증 화면)<br /> -->
								* <spring:message code="msg_0469"/><b><spring:message code="msg_0470"/></b><spring:message code="msg_0471"/>
							</div>
						</div>
					</div>
				</div>
				<div class="atm_faq_div" id="faq4">
					<div class="atm_faq_ttt">
						<p class="atm_faq_c2"><spring:message code="msg_0472"/></p> 
						<img src="/pub/default/userGuide/images/btn_direct_down2.png?ver=1.0" class="atm_faq_up" />
					</div>
					<div class="atm_faq_con">
						<div class="atm_faq_slidG">
							<div class="atm_faq_c4">
								<spring:message code="msg_0473"/><br />
								<b>1. <spring:message code="msg_0474"/></b><br />
								* <spring:message code="msg_0475"/><br />
								* <spring:message code="msg_0476"/><br />
								* <spring:message code="msg_0477"/><br />
								* <spring:message code="msg_0478"/><br />
								* <spring:message code="msg_0479"/><br />
								* <spring:message code="msg_0480"/><br />
								<br />
								<b>2. ANSWERer</b><br />
								* <spring:message code="msg_0481"/><br /> 
								* <spring:message code="msg_0482"/><b><spring:message code="msg_0470"/></b><spring:message code="msg_0483"/><br />  
								* <spring:message code="msg_0484"/><br />
								* <spring:message code="msg_0485"/><b><spring:message code="msg_0486"/></b><spring:message code="msg_0487"/>
								* <spring:message code="msg_0488"/><br />
								<!--&gt;&gt; 이미지 (고유코드/추천(추천인) 페이지) -->
							</div>
	         			</div>
					</div>
				</div>
				<div class="atm_faq_div" id="faq5">
					<div class="atm_faq_ttt">
						<p class="atm_faq_c2"><spring:message code="msg_0489"/></p>
						<img src="/pub/default/userGuide/images/btn_direct_down2.png?ver=1.0" class="atm_faq_up" />
					</div>
					<div class="atm_faq_con">
						<div class="atm_faq_slidG">
							<div class="atm_faq_c4">
								* <spring:message code="msg_0490"/><b><spring:message code="msg_0491"/></b><spring:message code="msg_0492"/><br />
								* <spring:message code="msg_0493"/><b><spring:message code="msg_0494"/></b><spring:message code="msg_0466"/><br />
								* <spring:message code="msg_0495"/><b><spring:message code="msg_0496"/></b><spring:message code="msg_0497"/><br />
								* <spring:message code="msg_0498"/><br />
								* <spring:message code="msg_0499"/><br />
								* <spring:message code="msg_0500"/><b>3.5%</b><spring:message code="msg_0501"/><br />
								* <spring:message code="msg_0502"/><br />
								* <spring:message code="msg_0503"/><br />
								* <spring:message code="msg_0504"/><br /><br />
								<c:if test="${lang == 'ko'}"><img src="/Common/images/withdrawal_ko.png" width="100%"></c:if>
								<c:if test="${lang == 'zh'}"><img src="/Common/images/withdrawal_zh.png" width="100%"></c:if>
								<c:if test="${lang == 'ja'}"><img src="/Common/images/withdrawal_ja.png" width="100%"></c:if>
								<c:if test="${lang != 'ko' and lang != 'zh' and lang != 'ja'}"><img src="/Common/images/withdrawal_en.png?ver=1.0" width="100%"></c:if>
								<br /><br>
							</div>
						</div>
					</div>
				</div>
				<div class="atm_faq_div" id="faq6">
					<div class="atm_faq_ttt">
						<p class="atm_faq_c2"><spring:message code="msg_0505"/></p> 
						<img src="/pub/default/userGuide/images/btn_direct_down2.png?ver=1.0" class="atm_faq_up" />
					</div>
					<div class="atm_faq_con">
						<div class="atm_faq_slidG">
							<div class="atm_faq_c4"><spring:message code="msg_0506"/><br />
								<b>1. <spring:message code="msg_0250"/></b><br />
								<spring:message code="msg_0507"/><b><spring:message code="msg_0508"/></b><spring:message code="msg_0509"/><br />
								<spring:message code="msg_0510"/><br />
								<spring:message code="msg_0511"/><br />
								<spring:message code="msg_0512"/><br />
								※ <spring:message code="msg_0513"/><br />
								<b>2. <spring:message code="msg_0251"/></b><br />
								<spring:message code="msg_0514"/><b><spring:message code="msg_0515"/></b><spring:message code="msg_0516"/><br />
								<spring:message code="msg_0517"/><br />
								<b>3. <spring:message code="msg_0518"/></b><br />
								<spring:message code="msg_0519"/><b><spring:message code="msg_0520"/></b><spring:message code="msg_0521"/><br />
								<b>4. <spring:message code="msg_0522"/></b><br />
								<spring:message code="msg_0523"/><b>10%</b><spring:message code="msg_0524"/><b><spring:message code="msg_0525"/></b><spring:message code="msg_0526"/><b><spring:message code="msg_0527"/></b><spring:message code="msg_0528"/><b><spring:message code="msg_0529"/></b><spring:message code="msg_0466"/><br />
								<spring:message code="msg_0530"/><br />
								<b>5. <spring:message code="msg_0531"/></b><br />
								<spring:message code="msg_0532"/><b><spring:message code="msg_0533"/></b><spring:message code="msg_0534"/><br />
								<b>6. <spring:message code="msg_0535"/></b><br />
								<spring:message code="msg_0536"/><br />
								<b>7. <spring:message code="msg_0537"/></b><br />
								<spring:message code="msg_0538"/><br /><br />
								<div class="atm_faq_conbox"><strong>TIP!</strong><br />
									1. <spring:message code="msg_0539"/><br />
									2. <spring:message code="msg_0540"/><br  />
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="atm_faq_div" id="faq7">
					<div class="atm_faq_ttt">
						<p class="atm_faq_c2"><spring:message code="msg_0541"/></p>
						<img src="/pub/default/userGuide/images/btn_direct_down2.png?ver=1.0" class="atm_faq_up" />
					</div>
					<div class="atm_faq_con">
						<div class="atm_faq_slidG">
							<div class="atm_faq_c4">
								<p>- <strong><spring:message code="msg_0542"/></strong><br />
									* <spring:message code="msg_0543"/> <br />
									<!--  &gt;&gt; 이미지 (첫 화면 중앙 검색 텍스트 폼 및 우측 상단 돋보기)<br /> -->
								</p><br />
								<p>- <strong><spring:message code="msg_0544"/></strong><br />
									* <spring:message code="msg_0545"/><br  />
									<!--  &gt;&gt; 이미지 (답변 클릭 전/클릭 후/‘답변 열람하기’ 버튼 누른 후)<br /> -->
									* <spring:message code="msg_0546"/><b><spring:message code="msg_0547"/></b><spring:message code="msg_0548"/><b><spring:message code="msg_0549"/></b><spring:message code="msg_0550"/><br />
									※ <spring:message code="msg_0551"/><br />
								</p><br />
								<p>- <strong><spring:message code="msg_0552"/></strong><br />
									* <spring:message code="msg_0553"/><b><spring:message code="msg_0554"/></b><spring:message code="msg_0483"/> <br />
									<!--  &gt;&gt; 이미지 (평가 아이콘)<br /> -->
									* <spring:message code="msg_0555"/><br />
									* <spring:message code="msg_0556"/><br />
									* <spring:message code="msg_0557"/><br />
									* <spring:message code="msg_0558"/>
								</p><br />
								<p>- <strong><spring:message code="msg_0559"/></strong><br />
									* <spring:message code="msg_0560"/><br />
									* <spring:message code="msg_0561"/><br />
									* <spring:message code="msg_0562"/><b><spring:message code="msg_0563"/></b><spring:message code="msg_0564"/><br />
									<!--  &gt;&gt; 이미지 (임시저장 아이콘)<br /> -->
									※ <spring:message code="msg_0565"/>
								</p><br />
								<p>- <strong><spring:message code="msg_0566"/></strong><br />
									* <spring:message code="msg_0567"/><br />
									<!--  &gt;&gt; 이미지 (‘채택하기’ 버튼)<br />
									&gt;&gt; 이미지 1차--&gt;2차--&gt;3차 등록 순서도 -->
								</p><br />
				                <p>- <strong><spring:message code="msg_0568"/></strong><br />
									* <spring:message code="msg_0569"/><br />
									* <spring:message code="msg_0570"/><b><spring:message code="msg_0571"/></b><spring:message code="msg_0572"/><br />
									<!--  &gt;&gt; 이미지 (‘답변하기’버튼)<br /> -->
									* <spring:message code="msg_0573"/><br />
									※ <spring:message code="msg_0574"/>
								</p>
							</div>
						</div>
					</div>
				</div>
				<div class="atm_faq_div" id="faq8">
					<div class="atm_faq_ttt">
						<p class="atm_faq_c2"><spring:message code="msg_0575"/></p> 
						<img src="/pub/default/userGuide/images/btn_direct_down2.png?ver=1.0" class="atm_faq_up" />
					</div>
					<div class="atm_faq_con">
						<div class="atm_faq_slidG">
							<div class="atm_faq_c4"><spring:message code="msg_0576"/><br /><br />
								<c:if test="${lang == 'ko'}"><img src="/Common/images/season_1_ko.png?ver=1.0" width="100%"></c:if>
								<c:if test="${lang == 'zh'}"><img src="/Common/images/season_1_zh.png?ver=1.0" width="100%"></c:if>
								<c:if test="${lang == 'ja'}"><img src="/Common/images/season_1_ja.png?ver=1.0" width="100%"></c:if>
								<c:if test="${lang != 'ko' and lang != 'zh' and lang != 'ja'}"><img src="/Common/images/season_1_en.png?ver=1.0" width="100%"></c:if>
								<br /><br />
								* <spring:message code="msg_0577"/><b><spring:message code="msg_0578"/></b><spring:message code="msg_0579"/><br />
								* <spring:message code="msg_0580"/><br />
								* <spring:message code="msg_0581"/><br />
								* <spring:message code="msg_0582"/><br />
								※ <spring:message code="msg_0583"/>
							</div>
						</div>
					</div>
				</div>
				<div class="atm_faq_div" id="faq9">
					<div class="atm_faq_ttt">
						<p class="atm_faq_c2"><spring:message code="msg_0584"/></p>
						<img src="/pub/default/userGuide/images/btn_direct_down2.png?ver=1.0" class="atm_faq_up" />
					</div>
					<div class="atm_faq_con">
						<div class="atm_faq_slidG">
							<div class="atm_faq_c4"><spring:message code="msg_0585"/><b><spring:message code="msg_0586"/></b><spring:message code="msg_0587"/></div>
						</div>
					</div>
				</div>
				<div class="atm_faq_div" id="faq10">
					<div class="atm_faq_ttt">
						<p class="atm_faq_c2"><spring:message code="msg_0588"/></p>
						<img src="/pub/default/userGuide/images/btn_direct_down2.png?ver=1.0" class="atm_faq_up" />
					</div>
					<div class="atm_faq_con">
						<div class="atm_faq_slidG">
							<div class="atm_faq_c4">* <spring:message code="msg_0589"/><br />
								* <spring:message code="msg_0590"/><br />
								* <spring:message code="msg_0591"/>
							</div>
						</div>
					</div>
				</div>
				<div class="atm_faq_div" id="faq11">
					<div class="atm_faq_ttt">
						<p class="atm_faq_c2"><spring:message code="msg_0592"/></p>
						<img src="/pub/default/userGuide/images/btn_direct_down2.png?ver=1.0" class="atm_faq_up" />
					</div>
					<div class="atm_faq_con">
						<div class="atm_faq_slidG">
							<div class="atm_faq_c4"><spring:message code="msg_0593"/></div>
						</div>
					</div>
				</div>
				<div class="atm_faq_div" id="faq12">
					<div class="atm_faq_ttt">
						<p class="atm_faq_c2"><spring:message code="msg_0594"/></p>
						<img src="/pub/default/userGuide/images/btn_direct_down2.png?ver=1.0" class="atm_faq_up" />
					</div>
					<div class="atm_faq_con">
						<div class="atm_faq_slidG">
							<div class="atm_faq_c4"><spring:message code="msg_0595"/><br />
								- <spring:message code="msg_0596"/><br />
								- <spring:message code="msg_0597"/><br />
								- <spring:message code="msg_0598"/><br />
								- <spring:message code="msg_0599"/><br />
								- <spring:message code="msg_0600"/><br />
								&nbsp;&nbsp;* <spring:message code="msg_0601"/><br />
								&nbsp;&nbsp;* <spring:message code="msg_0602"/><br />
								&nbsp;&nbsp;* <spring:message code="msg_0603"/><br />
								&nbsp;&nbsp;* <spring:message code="msg_0604"/><br />
								&nbsp;&nbsp;* <spring:message code="msg_0605"/><br />
								&nbsp;&nbsp;* <spring:message code="msg_0606"/><br /> 
								&nbsp;&nbsp;* <spring:message code="msg_0607"/><br />
								&nbsp;&nbsp;* <spring:message code="msg_0608"/><br />
								&nbsp;&nbsp;* <spring:message code="msg_0609"/><br />
								&nbsp;&nbsp;* <spring:message code="msg_0610"/><br /> 
								&nbsp;&nbsp;* <spring:message code="msg_0611"/><br />
								&nbsp;&nbsp;* <spring:message code="msg_0612"/><br />
								&nbsp;&nbsp;* <spring:message code="msg_0613"/><br />
								- <spring:message code="msg_0614"/><br />
								- <spring:message code="msg_0615"/><br />
								- <spring:message code="msg_0616"/><br />
								- <spring:message code="msg_0617"/><br />
								- <spring:message code="msg_0618"/><br />
								- <spring:message code="msg_0619"/><br />
								- <spring:message code="msg_0620"/><br />
								- <spring:message code="msg_0621"/><br />
								- <spring:message code="msg_0622"/><br />
								- <spring:message code="msg_0623"/><br />
								- <spring:message code="msg_0624"/>
							</div>
						</div>
					</div>
				</div>
				<div class="atm_faq_div" id="faq13">
					<div class="atm_faq_ttt">
						<p class="atm_faq_c2"><spring:message code="msg_0625"/></p>
						<img src="/pub/default/userGuide/images/btn_direct_down2.png?ver=1.0" class="atm_faq_up" />
					</div>
					<div class="atm_faq_con">
						<div class="atm_faq_slidG">
							<div class="atm_faq_c4">
								<spring:message code="msg_0626"/></br >
								* <spring:message code="msg_0628"/></br >
								* <spring:message code="msg_0629"/></br >
								* <spring:message code="msg_0630"/></br >
								
							</div>
						</div>
					</div>
				</div>
				<div class="atm_faq_div" id="faq14">
					<div class="atm_faq_ttt">
						<p class="atm_faq_c2"><spring:message code="msg_0631"/></p>
						<img src="/pub/default/userGuide/images/btn_direct_down2.png?ver=1.0" class="atm_faq_up" />
					</div>
					<div class="atm_faq_con">
						<div class="atm_faq_slidG">
							<div class="atm_faq_c4">
								<p>* <spring:message code="msg_0632"/></p>
								<p>* <spring:message code="msg_0633"/><strong>2.5%</strong><spring:message code="msg_0634"/><strong>3.5%</strong><spring:message code="msg_0635"/></p>
								<p>* <spring:message code="msg_0636"/></p>
							</div>
						</div>
					</div>
				</div>
				<!--wrapper end -->
			</div>
		</div>
	</div>
</div>
<div id="top_btn">
    <a href="javascript:void(0);">
        <span>
            <img src="/Common/images/top_btn_arrow.svg?ver=1.0" alt="top_btn">
        </span>
    </a>
</div>
<c:set var="go" value="${go}" />

<script>
	$('.select_wrapper .atm_temp_tab p').click(function(){
	    $('.select_wrapper .atm_temp_tab p').removeClass('temp_tab_on');
	    $(this).addClass('temp_tab_on');
	    let tmp = String($('.select_wrapper .atm_temp_tab p').index(this)+1);
	    $('.select_view').hide();
	    $('#select_view0'+tmp).show();
	    
	});
	/* 이용안내
    $('#select_innerwrapper .atm_faq_wrapper2 .atm_faq_div .atm_faq_ttt').click(function(){
        $(this).next('.atm_faq_con').stop().slideToggle();
        $(this).find('img').stop().toggleClass('up');
    }); */
	
	$(function(){
		var view = '${view}';
		//console.log('view : ' + view);
		
		if(view == '1') {
			$('.select_wrapper .atm_temp_tab:first').find('p').trigger('click');
		}
		else if(view == '2') {
			$('.select_wrapper .atm_temp_tab:last').find('p').trigger('click');
		}
	});
</script>
<% 
	String go = String.valueOf(pageContext.getAttribute("go"));
	
	if(go != "null"){
%>
<script>
document.addEventListener("DOMContentLoaded",function(){
	var offset = $('#<%=go %>').offset();
	$('html').animate({scrollTop:offset.top-80},400);
	$('#<%=go %> > div > p').trigger('click');
});
</script>
<%
	}
%>
</body>