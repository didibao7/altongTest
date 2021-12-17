<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body id="atm_gray2">
<style type="text/css">
ul,li {list-style-type:none;}
img,p,li {border:0;margin:0;padding:0; vertical-align:middle;}

#atm_gray2{ background-color:#ffffff;}
/*img*/

.atm_memagree_btn_L1{ position:absolute; left:0px; top:8px;  }
.atm_memagree_btn_R1{ position:absolute; right:14px; top:13px; width:25px;  }
.atm_memagreePhone_btn { background-color:#2ac1bc; padding:15px 0; width:100%;  color:#fff; font-weight:bold; font-size:14px;margin-top:10px; text-align:center; border-radius:5px; cursor:pointer;}

/*p태그*/

.atm_memagree_c1{ font-size:14px; font-weight:bold; letter-spacing:-1px; padding:10px 5px 0px 0; color:#5a5a5a; border-radius:100px; display:inline-block;  border:#fff 1px solid; width:80%; }
.atm_memagree_c2{ font-size:12px; font-weight:bold; letter-spacing:-1px; padding:10px 5px 14px 0; color:#a7a7a7; border-radius:100px; display:inline-block;  border:#fff 1px solid; }
.atm_memagree_c3{ font-size:12px; font-weight:bold; letter-spacing:-1px; padding:10px 25px; color:#737373; border:#e5e5e5 1px solid; background-color:#f2f2f2; margin-top:10px; overflow-y:scroll; }

/*본문부분*/
#atm_memagree_wrapper0{ width:100%; padding:0 0 0 0; background-color:#ffffff;}

.atm_memagree_con{ padding:4px 10px 11px; text-align:left; position:relative; }
.atm_memagree_con_el{position:relative; background-color:#fff; margin-top:7px; padding:15px 14px;}

.atm_memagree_opt{ position:relative; }
.atm_memagree_opt2{ position:relative; }
.atm_memagree_optR{ display:inline-block; position:absolute; right:0px; top:0px;}
.atm_memagree_optR2{ display:inline-block; position:absolute; right:0px; top:6px;}
.atm_memagree_G1{ padding-top:15px; }
.atm_memagree_G2{ padding-top:35px; }
.atm_memagree_G3{ padding-top:25px; padding-bottom:30px; }

.atm_memagree_div { background-color:#fff; padding:15px 14px; font-weight:bold; font-size:14px;margin-top:10px; text-align:left; border-radius:5px; position:relative; cursor:pointer;}

input[type="checkbox"].frm-hidden { display:none; }

/*라디오버튼B*/
.atm_pushswitchB_frame{     border-radius:100px; background-color:#ababab; width:35px; padding:3px 3px; height:8px; position:absolute; top:11px; right:0;}
.atm_pushswitchB_button{    border-radius:100px; background-color:#ffffff; width:20px; height:20px; display:inline-block;border:#ababab 1px solid; position:absolute; top:-6px; left:0;}
.atm_pushswitchB_frame_on{  border-radius:100px; background-color:#2ac1bc; width:35px; padding:3px 3px; height:8px; position:absolute; top:11px; right:0; text-align:right; }
.atm_pushswitchB_button_on{ border-radius:100px; background-color:#ffffff; width:20px; height:20px; display:inline-block; border:#2ac1bc 1px solid; position:absolute; top:-6px; right:0;}

@media screen and (min-width: 800px) {
.atm_memagree_con{ width:800px; display:inline-block; }
form{ text-align:center;}
.atm_graytop_ttbar{ text-align:center;}
.atm_graytop_ttbar_pc{ width:800px; display:inline-block;  position:relative; }
}

</style>
<%@ include file="/pub/menu/topMenu.jsp"%>
<div id="atm_wrapper">

<div id="atm_memagree_wrapper0">
<form name="frm" style="text-align:center;">
<!--wrapper start -->
		<div class="atm_graytop_ttbar" >
		<div class="atm_graytop_ttbar_pc" >
			<p class="atm_graytop_c1"><spring:message code="msg_0439"/>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<spring:message code="msg_0440"/></p>
			<a href="/default/main"><img src="/Common/images/btn_back_bold.png" class="atm_graytop_btn_L1"/></a>
		</div>
		</div>
		<div class="atm_memagree_con" align="center">
			<div class="atm_memagree_con_el atm_border">
				<div class="atm_memagree_G2">
					<div class="atm_memagree_opt">
						<p class="atm_memagree_c1" id="a"><spring:message code="msg_0439"/></p>
							<br />
							<textarea readonly class="atm_memagree_c3" style="width: 100%; height: 600px; resize:none;"><%@ include file="/WEB-INF/views/default/rule_1.jsp" %></textarea>
					</div>
				</div>
				<div class="atm_memagree_G3">
					<div class="atm_memagree_opt">
						<p class="atm_memagree_c1" id="b"><spring:message code="msg_0440"/></p>
							<br />
							<textarea readonly class="atm_memagree_c3" style="width: 100%; height: 600px; resize:none;"><%@ include file="/WEB-INF/views/default/rule_2.jsp" %></textarea>
					</div>
				</div>
			</div>
		</div>

</form>

<!--wrapper end -->
</div>
</div>
<div id="top_btn">
    <a href="#">
        <span>
            <img src="/Common/images/top_btn_arrow.svg" alt="top_btn">
        </span>
    </a>
</div>
</body>
</html>