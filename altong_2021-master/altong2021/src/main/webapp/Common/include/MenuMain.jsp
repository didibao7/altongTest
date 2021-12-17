<style>
@media screen and (min-width: 800px) {
.atm_mainsearch_top_pc{text-align:center;}
.atm_mainsearch_top{ width:800px; display:inline-block; position:relative; padding:18px 0px; text-align:left;}
}
</style>
<div class="atm_mainsearch_top_pc"> 
<div class="atm_mainsearch_top" id="navbar-main"> 
<div class="atm_mainsearch_subtextwrap" > 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ page import="org.json.simple.JSONObject" %>
<% 
JSONObject global_info = (JSONObject)CommonUtil.getGlobal(request, response);
Integer UserAlmoney = 0;
%>﻿
<%
	int userSeq2 = 0;
	if(global_info != null) {
		userSeq2 = Integer.parseInt(global_info.get("UserSeq").toString());
	}
	
    if (userSeq2 == 0) {
%>
<a href="#" id="sidebar-main-trigger" class="icon float-left"><img src="/Common/images/icon_menu2.png" class="atm_logobar_btn_L0"/></a>
    <div class="atm_mainsearch_el1" >
    	<p class="atm_mainsearch_c1">온통</p><p class="atm_mainsearch_c2"><span id="domAlmoneyAll">0</span><span class="atm_mainsearch_c3">알</span></p> 
    </div>
    <div class="atm_mainsearch_el2">
    	<p class="atm_mainsearch_c4" onClick="javascript:location.href='/default/joinRule';">회원가입</p>
    	<p class="atm_mainsearch_c5" onClick="javascript:location.href='/default/login';">로그인</p>
    </div>
<%
    } else {
%>
    <a href="#" id="sidebar-main-trigger" class="icon float-left">
		<img src="/Common/images/icon_menu2.png" class="atm_logobar_btn_L0"/>
	</a>
    <div class="atm_mainsearch_el1" style="width:170px" onClick="javascript:location.href='/answer/rankMember?FlagOption=Money'">
    	<p class="atm_mainsearch_c1">온통</p><p class="atm_mainsearch_c2"><span id="domAlmoneyAll">0</span><span class="atm_mainsearch_c3">알</span></p> 
    </div>
    <div class="atm_mainsearch_el3" style="width:170px" onClick="javascript:location.href='/member/bank/index'"><!-- index.alt -->
<%
		if(userSeq2 != 0) {
			UserAlmoney = Integer.parseInt(global_info.get("UserAlmoney").toString());
		}
%>

    	<p class="atm_mainsearch_c1b">나</p><p class="atm_mainsearch_c2"><span id="domAlmoneyMy">0</span><span class="atm_mainsearch_c3">알</span></p> 
    </div>
<%
    }
%>
</div>
</div>
<script>
document.timerID = Object();
function fAnimAlmoney(dom, fixedAl)
{
	$('#'+dom).text(parseInt(Math.random() * 10) + fixedAl);
	document.timerID[dom] = setTimeout(function() {fAnimAlmoney(dom, fixedAl);}, 30);
}
function fSetAlmoney(AlmoneyAll, AlmoneyMy, x)
{
	if (!AlmoneyAll) AlmoneyAll = '0';
	if (!AlmoneyMy) AlmoneyMy = '0';

	if (document.timerID['domAlmoneyAll'])
		clearTimeout(document.timerID['domAlmoneyAll']);
	
	if (document.timerID['domAlmoneyMy'])
		clearTimeout(document.timerID['domAlmoneyMy']);
	
	x++;
	var loop = false;
	
	if (AlmoneyAll.length >= x)
	{
		$('#domAlmoneyAll').text(AlmoneyAll.substr(AlmoneyAll.length - x, x-2));
		if (AlmoneyAll.length > x)
		{
			fAnimAlmoney('domAlmoneyAll', $('#domAlmoneyAll').text());
			loop = true;
		}
	}
	//console.log('AlmoneyMy.length >= x : '+ (AlmoneyMy.length >= x));
	if (AlmoneyMy.length >= x)
	{
		$('#domAlmoneyMy').text(AlmoneyMy.substr(AlmoneyMy.length - x, ((AlmoneyMy.length <= 10)? x:x-2)   ));
		if (AlmoneyMy.length > x)
		{
			fAnimAlmoney('domAlmoneyMy', $('#domAlmoneyMy').text());
			loop = true;
		}
	}
	
	if (loop)
		setTimeout(function() { fSetAlmoney(AlmoneyAll, AlmoneyMy, x);}, 200);
}
fAnimAlmoney('domAlmoneyAll', '');
fAnimAlmoney('domAlmoneyMy', '');
</script>