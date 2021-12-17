<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header>
	<div class="center">
		<div class="main_header">
			<a href="javascript:void(0);" id="menu_icon">
				<div class="icon_hamburger">
					<i></i> <i></i> <i></i> <span></span>
				</div>
			</a>
		</div>
		<h1 class="header_logo">
			<a href="/"> <img src="/Common/images/logo3.png"
				alt="header_logo">
			</a>
		</h1>
		<div class="column">
			<div id="sb-search">
				<form action="/question/questionSearch">
					<input class="sb-search-input " type="text" value=""
							name="src_Text" id="search" placeholder='<spring:message code="msg_0640"/>'
							onblur="blur_click_sb_search()">
					<input class="sb-search-submit" type="submit" value="">
					<span class="sb-icon-search">
						<img src="/Common/images/mainico/nicksearch.svg" class="atm_logobar_btn_R0" />
					</span>
				</form>
			</div>
			<p>
				<a href="/question/questionWrite">
					<img src="/Common/images/que_icon.svg" alt="">
				</a>
				<a href="/answer/questionList" class="questionList_go">
                    <img src="/pub/default/main/images/list_icon.svg" alt="">
                </a>
			</p>
		</div>
	</div>
</header>