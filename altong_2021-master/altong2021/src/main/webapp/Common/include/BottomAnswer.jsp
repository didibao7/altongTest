<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jasp.util.*" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<style>
.atm_btn_top {z-index:99;position:fixed;right:4px;bottom:20px;width:60px;display:none;}
@media screen and (min-width: 800px) {
.atm_btn_top {wpadding:0 0 0 0;display:table;position:fixed;bottom:20px;left:50%;margin-left:335px;}
}
</style>
<a href="#"><img src="/Common/images/btn_top.png" class="atm_btn_top"></a>
<div id="white-space-answer"></div>
<script>
//$.fn.scrollBottom = function() { 
//    return $(document).height() - this.scrollTop() - this.height(); 
//};
var scrollBottom;
	
var deadLine;
$(window).load(function() {
	$(".atm_btn_top").fadeIn("slow")
	deadLine = $(window).height() - $(".atm_btn_top").position().top - $(".atm_btn_top").height()
});
$(document).ready(function(){
	$('#footer').css('background-color','#f2f2f2');
});
$(window).resize(function() {
	scrollBottom = $(document).height() - $(window).height() - $(window).scrollTop();
	deadLine = $(window).height() - $(".atm_btn_top").position().top - $(".atm_btn_top").height()
});
$(window).scroll(function() {
	//var scrollBottom = $(document).height() - $(window).height() - $(window).scrollTop();
	if(scrollBottom <= deadLine) {//$(window).scrollBottom()
		$(".atm_btn_top").fadeOut("fast")	
	} else {
		$(".atm_btn_top").fadeIn("slow")
	}
});
</script>
<%@ include file="/Common/include/MenuItem.jsp" %>
<style>
#footer {
	width:100%;
	position:absolute;
	left:0;
	bottom:0;
	text-align:center;
	font-size:0.85em;
	color: grey;
	padding:20px 0;
}
#footer a {
    color: grey;
}
@media screen and (min-width: 500px) {
	/* #footer {
		margin-left:37.5%;
	} */
}
@media (max-height:480px) {
    #footer {position: relative;}
}
</style>
<!-- <div style="height:2em;"></div> -->
<footer id="footer"> 
	  <div>
고객만족 콜센터 <a href="tel:02-330-3000"><b>(02)330-3000</b></a><br>
    <a href="/default/rule#a">이용약관</a> |<!-- /default/rule.alt#a -->
    <a href="/default/rule#b">개인정보취급방침</a> |<!-- /default/rule.alt#b -->
    <a href="/default/cs/customerService">공지사항</a><!-- customerService.alt -->
    <br />
    <small>
      <!-- Copyright &copy; 2017 Altong. All Rights Reserved. -->
      Copyleft 2017 Altong Co., Ltd.
    </small>
  </div>
	
<!-- <div style="height:2em;"></div> -->
</footer>
<script>
$(document).ready(function(){
	$('body').css('padding-bottom',$('#footer').css('height'));
	//$('#footer').css('bottom', -$(document).height() / 1.6)
});
</script>