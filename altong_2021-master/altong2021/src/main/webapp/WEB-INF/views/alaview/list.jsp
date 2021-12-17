<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<%
	int[] a_arr = {341716552, 234220850, 26223406, 116527759, 338919380, 242586579, 213020928, 36402510, 146345890, 173873673};
	int[] b_arr = {307189851, 29551582, 34275855, 39958893, 49627421, 321938623, 305199866, 277764554, 131386182, 209086202};
	int[] c_arr = {38766750, 40794237, 253934301, 189108499, 340791289};
%>
<head>
<link rel="stylesheet" href="/pub/alaview/list/alaview.css?ver=1.0">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.4">
</head>
<body>
<header>
	<div class="center">
		<div class="main_header">
			<a href="javascript:void(0);" id="menu_icon">
				<div class="icon_hamburger">
					<i></i>
					<i></i>
					<i></i>
					<span></span>
				</div>
			</a>
		</div>
		<h1 class="header_logo">
			<a href="javascript:void(0);">
				<img src="/pub/alaview/images/alaview_logo.png" alt="header_logo">
			</a>
		</h1>
		<div class="column">
			<div id="sb-search" >
				<form action="">
					<input class="sb-search-input " type="text" value="" name="src_Text" id="search" placeholder="검색어를 입력해 주세요." onblur="blur_click_sb_search()">
					<input class="sb-search-submit" type="submit" value="">
					<span class="sb-icon-search">
						<img src="/pub/alaview/images/nicksearch.svg" class="atm_logobar_btn_R0" />
					</span>
				</form>
			</div>
			<p>
				<a href="javascript:void(0);">
					<img src="/pub/alaview/images/alaview_video.png" alt="" class="atm_logobar_btn_R1">
				</a>
			</p>
		</div>
	</div>
</header>
<div id="wrapper">
	<div id="swiper_container">
		<div class="center">
			<ul>
				<li class="list_select"><a href="javascript:void(0);"><img src="/Common/images/category/category01_off.svg" alt="전체"><span>전체</span></a></li>
				<li><a href="javascript:void(0);"><img src="/Common/images/category/category02_off.svg" alt="배움"><span>배움</a></li>
				<li><a href="javascript:void(0);"><img src="/Common/images/category/category03_off.svg" alt="생활"><span>생활</span></a></li>
				<li><a href="javascript:void(0);"><img src="/Common/images/category/category04_off.svg" alt="건강"><span>건강</span></a></li>
				<li><a href="javascript:void(0);"><img src="/Common/images/category/category05_off.svg" alt="고민"><span>고민</span></a></li>
				<li><a href="javascript:void(0);"><img src="/Common/images/category/category06_off.svg" alt="문예"><span>문예</span></a></li>
				<li><a href="javascript:void(0);"><img src="/Common/images/category/category07_off.svg" alt="세상"><span>세상</span></a></li>
				<li><a href="javascript:void(0);"><img src="/Common/images/category/category08_off.svg" alt="돈"><span>돈</span></a></li>
				<li><a href="javascript:void(0);"><img src="/Common/images/category/category09_off.svg" alt="컴/폰"><span>컴/폰</span></a></li>
				<li><a href="javascript:void(0);"><img src="/Common/images/category/category10_off.svg" alt="스포츠"><span>스포츠</span></a></li>
				<li><a href="javascript:void(0);"><img src="/Common/images/category/category11_off.svg" alt="게임"><span>게임</span></a></li>
			</ul>
		</div>
	</div>
	<div class="atm_faq_slidG">
		<div class="center">
			<div class="atm_faq_c1">
				<h3>구독 동영상</h3>
				<div class="atm_faq_c1b" style="margin-bottom:10px">
					<% for (int v : a_arr) { %>
					<div alaview_id="<%=v%>" class="alaview" onClick="fPlay(this)">
						<div class="alaview_height"></div>
						<div class="alaview_tit alaview_memo"><span class="data_tit">&nbsp;</span><img class="gra_right" src="/Common/images/gra_right.png"></div>
						<div class="alaview_usr alaview_memo"><span class="data_usr">&nbsp;</span><img class="gra_right" src="/Common/images/gra_right.png"></div>
					</div>
					<% } %>
					<div class="clear_both"></div>
				</div>
				<h3>맞춤 동영상</h3>
				<div class="atm_faq_c1b" style="margin-bottom:10px">
					<% for (int v : b_arr) { %>
					<div alaview_id="<%=v%>" class="alaview" onClick="fPlay(this)">
						<div class="alaview_height"></div>
						<div class="alaview_tit alaview_memo"><span class="data_tit">&nbsp;</span><img class="gra_right" src="/Common/images/gra_right.png"></div>
						<div class="alaview_usr alaview_memo"><span class="data_usr">&nbsp;</span><img class="gra_right" src="/Common/images/gra_right.png"></div>
					</div>
					<% } %>
					<div class="clear_both"></div>
				</div>
				<h3>최신 동영상</h3>
				<div class="atm_faq_c1b" style="margin-bottom:10px">
					<% for (int v : c_arr) { %>
					<div alaview_id="<%=v%>" class="alaview" onClick="fPlay(this)">
						<div class="alaview_height"></div>
						<div class="alaview_tit alaview_memo"><span class="data_tit">&nbsp;</span><img class="gra_right" src="/Common/images/gra_right.png"></div>
						<div class="alaview_usr alaview_memo"><span class="data_usr">&nbsp;</span><img class="gra_right" src="/Common/images/gra_right.png"></div>
					</div>
					<% } %>
					<div class="clear_both"></div>
				</div>
			</div>
		</div>
	</div>
	<div class="alaview_mask"></div>
	<div class="alaview_popup">
		<div class="popup_div">
			<i class="material-icons popup_close">clear</i>
		</div>
	</div>
</div>
<div id="top_btn">
    <a href="javascript:void(0);">
        <span>
            <img src="/Common/images/top_btn_arrow.svg" alt="top_btn">
        </span>
    </a>
</div>
<script src="https://player.vimeo.com/api/player.js"></script>
<script src="/pub/alaview/list/alaview.js?ver=1.0"></script>
</body>