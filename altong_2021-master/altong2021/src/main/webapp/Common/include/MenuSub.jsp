<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>

.atm_logobar_btn_R0{ position:absolute; right:6px; top:7px; width:37px;  }
.atm_logobar_btn_R1{ position:absolute; right:45px; top:0px; width:37px;  }
.sb-icon-search,
#sb-search > form > span { width:37px;}

@media screen and (min-width: 800px) {
.atm_logo_img,
#atm_wrapper > div.atm_logobar_top > div > div.atm_logobar_div100 > a > img {  width:80px; margin-top:-11px;}
.atm_logobar_top, 
#atm_wrapper > div.atm_logobar_top { text-align:center;}
.atm_logobar_top_pc,
#atm_wrapper > div.atm_logobar_top > div{ width:800px; display:inline-block; position:relative; padding:5px 18px 0;  }
.atm_logobar_btn_R0,
#sb-search > form > span > img { position:absolute; right:6px; top:2px; width:37px;  }
.atm_logobar_btn_R1,
#atm_wrapper > div.atm_logobar_top > div > p > img { position:absolute; right:35px; top:5px; width:37px;  }
}

</style>
<%
String libAlaview = "";
if(request.getAttribute("libAlaview") != null)
{
	libAlaview = request.getAttribute("libAlaview").toString();
}
pageContext.setAttribute("libAlaview", libAlaview);
%>
<c:if test="${libAlaview == '1'}">
	<%@ include file="/Common/alaview/alaview_top.jsp" %>
</c:if>
<c:choose>
	<c:when test="${libAlaview eq '1'}">
		<%//@ include file="/WEB-INF/views/univ/Hongik/common/top.phtml" %>
	</c:when>
	<c:otherwise>
		<div class="atm_logobar_top" >
		<div class="atm_logobar_top_pc" >
			<div class="atm_logobar_div100"><a href="/default/main"><img src="${libIMG_URL}/Common/images/logo3.png" class="atm_logo_img" /></a></div>
			<a href="#" id="sidebar-main-trigger" class="icon float-left"><img src="${libIMG_URL}/Common/images/icon_menu.png" class="atm_logobar_btn_L0"/></a>
			<div class="column">
				<div id="sb-search" class="sb-search">
					<form action="/question/questionSearch">
						<input class="sb-search-input" placeholder="검색단어를 입력 후 엔터..." type="text" value="" name="src_Text" id="search">
						<input class="sb-search-submit" type="submit" value="">
						<span class="sb-icon-search"><img src="${libIMG_URL}/Common/images/icon_search.png" class="atm_logobar_btn_R0"/></span>
					</form>
				</div>
			</div>
			<%
				String self_uri = request.getRequestURI();
			
				pageContext.setAttribute("self_uri", self_uri);
			%>
			<c:choose>
				<c:when test="${fn:indexOf(self_uri,'/question/') eq -1}">
					<p onClick="location.href='/question/bestList'"><img src="${libIMG_URL}/Common/images/icon_top_question3.png" class="atm_logobar_btn_R1"/></p>
				</c:when>
				<c:otherwise>
					<a href="/answer/questionList"><img src="${libIMG_URL}/Common/images/icon_top_answer3.png" class="atm_logobar_btn_R1"/></a>
				</c:otherwise>
			</c:choose>
			<script>new UISearch(document.getElementById('sb-search'));</script>
		</div>
		</div>
	</c:otherwise>
</c:choose>
