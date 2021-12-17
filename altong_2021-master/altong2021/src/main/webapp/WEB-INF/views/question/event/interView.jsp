<%@page import="com.altong.web.logic.question.QuestionVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<link rel="stylesheet" href="/pub/answer/rankAnswer/rankAnswer.css?ver=1.3">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.5">
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<div id="atm_av_wrapper0">
	<div class="center">
		<div class="atm_av_con">
            <div class="atm_av_el">
            	<div>
                <img src="/Common/images/icon_intervew.png" class="atm_icon_qmark_big"/>
                <div class="atm_av_c9">
                    ${interview.itv_title}
                </div>
                <p class="atm_av_c10">${interview.itv_date_reg } &nbsp;
                    <span class="atm_whitespace"><img src="/Common/images/icon_view.png" class="atm_viewicon"/>${interview.itv_page_view}</span>
                </p>
                </div>
                ${interview.itv_contents }
            </div>
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
</body>