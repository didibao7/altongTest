<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.altong.web.logic.util.CodeUtil" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<body>
<link rel="stylesheet" href="/pub/member/alarm/alarm/alarm.css?ver=1.4">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.1">

<%@ include file="/pub/menu/topMenu.jsp"%>
<div id="atm_wrapper">
    <div id="atm_wrapper" style="overflow-x:hidden;">
        <div class="center site-content">
            <!--wrapper start -->
            <div id="alarmConFig_wrapper" class="site">
            <h1><spring:message code="msg_0714"/></h1>
            <div class="atm_edittop_ttbar">
            </div>
            <div class="settingWrapper">
                <div class="settingItem settingItem_all">
                    <p><spring:message code="msg_0715"/></p>
                    <input type="checkbox" name="checkAll" id="checkAll">
                    <label class="checkmark" for="checkAll"></label>
                </div>
            </div>
            <form class="settingWrapper">
                <% 
                CodeUtil codeObj = new CodeUtil(request);

                String code = "";
                String codeName = "";
                String dbColName = "";
                String dbCol = "";

                //out.println(request.getAttribute("alamrConfig"));

                HashMap<String, Object> map = (HashMap<String, Object>)request.getAttribute("alamrConfig");
                //out.println(map);
                for(int i = 1; i <= codeObj.getCODE_MEM_ALARM_VIEW_FIELD_CD().size(); i++) {
				//getCODE_MEM_ALARM_VIEW_FIELD_NM
				code = codeObj.getCODE_MEM_ALARM_VIEW_FIELD_CD().get(String.valueOf(i));
				codeName = codeObj.getCODE_MEM_ALARM_VIEW_FIELD_NM().get(String.valueOf(i));
				
				//out.println("code : " + code + "<br/>");
				if(code == "ALARM"){
					dbColName = "Message_Alarm_Yn".toUpperCase();
				}
				else if(code == "NOTICE") {
					dbColName = "Notice_Alarm_Yn".toUpperCase();
				}
				else {
					dbColName = code.replaceAll("_", "") + "_Alarm_Yn".toUpperCase();
				}
				
				//Notice_ALARM_YN
				
				//out.println("dbColName : " + dbColName + "<br/>");
				
				dbCol = map.get(dbColName).toString();
				//out.println("dbCol : " + dbCol + "<br/>");
				
				String imgLink = "/Common/images/alarm/icon_"+ code.toLowerCase() +".png";
				if(code == "ALARM") {
					imgLink = "/Common/images/alarm/icon_message.png";
				}
                %>
                <div class="settingItem">
                    <img src="<%=imgLink%>"/>
                    <p <% if(code == "AnsChoice_Alarm") { %>class="greyText" <% } %>><%=codeName%></p>
                    <input type="checkbox" id="<%=code%>" name="<%=code%>" class="notiCheck" <% if(dbCol.equals("Y")) { %>checked <% } %> <% if(code.equals("ANS_CHOICE_READY")) { %>checked disabled <% } %> <%if(code.equals("CMT_REGIST")){%>disabled<%}%>>
                    <label class="checkmark" for="<%=code%>"></label>
                </div>
                <%
                	}
                %>
            </form>
            <button class="btn btn-primary ok_btn"><spring:message code="msg_0160"/></button>
        </div>
    </div>
    <div id="top_btn">
        <a href="javascript:void(0);">
            <span>
                <img src="/Common/images/top_btn_arrow.svg" alt="top_btn">
            </span>
        </a>
    </div>
</div>
<script>
	$('#checkAll').change(function() {
		const $this = $(this);
		var code = '<%=codeObj.getCODE_MEM_ALARM_VIEW_FIELD_CD_ANS_CHOICE_READY()%>';
		if($this.is(":checked")) {
			$('.notiCheck').prop("checked", true);
		} else {
			$('.notiCheck').prop("checked", false);
			$('input[name="'+code+'"]').prop("checked", true);
		}
		$('#CMT_REGIST').prop("checked", false);
	});
	
	$('.notiCheck').change(function() {
		if($(this).is(":checked") === false) {
			$('#checkAll').prop("checked", false);
		}
	});
	
	$('.ok_btn').click(function(){
		$.ajax({
			'url': '/member/alarm/alarmConfigSave',
			'data': $('.settingWrapper').serialize(),
			'type': 'post',
			success: function(){
				alert(getLangStr("jsm_0071"));
			location.href = '/member/alarm/alarm?t=' + new Date();
			}
		});
	});
</script>
</body>