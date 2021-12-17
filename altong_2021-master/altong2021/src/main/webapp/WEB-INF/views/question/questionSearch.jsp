<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.altong.web.logic.util.CommonUtil"%>
<%@ page import="com.altong.web.logic.util.CodeUtil"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<%
	pageContext.setAttribute("crcn", "\r\n"); //Space, Enter
    pageContext.setAttribute("br", "<br>"); //br 태그
    
    CodeUtil code = new CodeUtil(request);

    Map<String, String> lvCd = code.getCODE_MEM_LV_CD();
    Map<String, String> lvNm = code.getCODE_MEM_LV_NM();

    String lvName = "0,";
    for (int i = 1; i <= lvCd.size(); i++) {
    	lvName = lvName + "'" + lvNm.get(String.valueOf(i)) + "',";
    }
    
    String msg_0149 = CommonUtil.getLangMsg(request, "msg_0149");
    String msg_0236 = CommonUtil.getLangMsg(request, "msg_0236");
    
    pageContext.setAttribute("msg_0149", msg_0149);
    pageContext.setAttribute("msg_0236", msg_0236);
%>
<head>
<link rel="stylesheet" href="/pub/question/questionSearch/questionSearch.css?ver=1.2">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.4">
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<div class="atm_ranka11_con">
	<p class="noResult"><spring:message code="msg_0937"/></p>
	<div class="center search_result_box">
	<!--wrapper start -->
	
		
<c:if test="${kwyword_count > 0}">	
	<c:if test="${pageTotalcnt > 0}">
	
		<!-- forEach start -->
		<c:forEach var="item" items="${searchData}" varStatus="status">
		<div class="atm_ranka11_el" onClick="javascript:location.href='/answer/answerList?Seq=${item.seq}&CurPageName=${curPageName}&src_Sort=${src_Sort}&src_OrderBy=DESC&src_Text=${src_Text}';">
			<c:set var="title" value="${item.title}"/>
			<c:set var="contents" value="${fn:replace(item.contents, br, ' ')}"/>
			<c:forEach var="keyData"  items="${keyWordStr}">
				<c:set var="concatTitleStr" value="<strong>${keyData}</strong>"/>
				<c:set var="title" value="${fn:replace(title, keyData, concatTitleStr)}"/>
			</c:forEach>
			<c:forEach var="keyData2" items="${keyWordStr}">
				<c:set var="concatContentsStr" value="<strong>${keyData2}</strong>"/>
				<c:set var="contents" value="${fn:replace(contents, keyData2, concatContentsStr)}"/>
			</c:forEach>
			
			<c:set var="photo" value="${item.photo}" />
			<c:if test="${item.photo == '' or item.flagNickName == 'N'}">
				<c:set var="photo" value="img_thum_base0.jpg" />
			</c:if>
			<c:set var="nickName" value="${item.nickName}" />
			<c:if test="${item.flagNickName == 'N'}">
				<c:set var="nickName" value='${msg_0236}' />
			</c:if>
			
			<c:set var="usrLv" value="${item.lv}" />
			<%
				int lv = Integer.parseInt(String.valueOf(pageContext.getAttribute("usrLv")));
				
				String[] lvArr = lvName.split(",");
				String lvN = "";
				try {
					if (lv != 99) {
						//System.out.println("lv : " + lv);
						lvN = lvArr[lv];
					}
				}
				catch(Exception e) {
					//System.out.println("error : " + e.getMessage());
				}
				pageContext.setAttribute("lvN", lvN);
			
			%>
			<c:set var="lv" value="${lvN}" />
			<c:if test="${item.flagNickName == 'N' }">
				<c:set var="lv" value='${msg_0236}' />
			</c:if>
			<c:if test="${item.flagNickName != 'N' }">
				<c:if test="${usrLv == '99' }">
					<c:set var="lv" value='${msg_0149}' />
				</c:if>
				<c:if test="${usrLv != '99' }">
					<c:set var="lv" value="${lvN}" />
				</c:if>
			</c:if>
			
			<div class="atm_ranka11_c5">
                <figure>
                    <img src="/UploadFile/Profile/${photo}">
                </figure>
				<c:set var="item_almoney" value="${item.almoney}" />
				<fmt:parseNumber var="item_almoney_2" type="number" value="${item_almoney}"></fmt:parseNumber>
                <div class="atm_icon_score1"><sapn class="atm_icon_score2<% 
								int usr_color = Integer.parseInt(String.valueOf(pageContext.getAttribute("item_almoney_2")));
								if (usr_color >= 5000) { %>_red<% } %>"><fmt:formatNumber value="${item.almoney}" type="number" /></sapn></div>
                ${title}
                <p>${nickName} <span>${fn:replace(lv, "'", "")}</span></p>
            </div>
            <p class="beefup__head">${contents}</p>
            <p class="atm_ranka11_c7">${item.dateReg}
                <span class="atm_whitespace"><img src="/Common/images/icon_view.svg" class="atm_viewicon"><fmt:formatNumber value="${item.readCount}" type="number" /></span>
                <span class="atm_whitespace"><img src="/Common/images/icon_reply.svg" class="atm_viewicon"><fmt:formatNumber value="${item.answerCount}" type="number" /></span>
            </p>
		</div><!-- atm_ranka11_el end -->
		</c:forEach>
		<!-- forEach end -->
	</c:if>		
</c:if>
	</div><!-- center search_result_box end -->
</div><!-- atm_ranka11_con end -->

<c:if test="${pageTotalcnt > 0}">
<div class="list_pasing">
${paging}
</div>
</c:if>
<div id="top_btn">
    <a href="#">
        <span>
            <img src="/Common/images/top_btn_arrow.svg" alt="top_btn">
        </span>
    </a>
</div>
<script>
	$(window).load(function() {
		if($('.search_result_box').children().length == 0) {
			$('.noResult').stop().show();
		}
	});
</script>
</body>