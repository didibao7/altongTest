<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat"  %>
<%@ page import="java.util.*"  %>
<%@ page import="java.time.*"  %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%
//Date now = new Date();
/*
Calendar cal = Calendar.getInstance();
int sYear = cal.get(Calendar.YEAR);
int sMonth = cal.get(Calendar.MONTH) + 1;
int sDy = cal.get(Calendar.DAY_OF_MONTH);
int sHour = cal.get(Calendar.HOUR_OF_DAY);
int sMin = cal.get(Calendar.MINUTE);
int sSec = cal.get(Calendar.SECOND);

LocalDateTime today = LocalDateTime.of(sYear, sMonth, sDy, sHour, sMin, sSec);
*/
JSONObject global = (JSONObject)CommonUtil.getGlobal(request, response);
pageContext.setAttribute("global", global);

LocalDateTime now = LocalDateTime.now();

String sessNickName = null;
if(global != null) {
	sessNickName = global.get("UserNickName").toString();
}
pageContext.setAttribute("sessNickName", sessNickName);

//날짜 시간 관련 자료 : https://m.blog.naver.com/PostView.nhn?blogId=kj930519&logNo=221440967773&proxyReferer=https:%2F%2Fwww.google.com%2F
// blockExpire 을 date 형식으로 수정해야 함
//blockExpire = "2019-07-19 오후 7:00";

if(sessNickName != null) {
	LocalDateTime blockExpire = LocalDateTime.of(2019, 7, 19, 19, 0, 0);
	//blockExpire = "2020-04-20 오후 12:11"
	if (sessNickName.equals("학이네엄마") && now.isBefore(blockExpire)) {
		out.println("<script>alert('" + CommonUtil.getLangMsg(request, "msg_0134") + " " + blockExpire + CommonUtil.getLangMsg(request, "msg_0135") + "');location.replace('/default/logOut');</script>");
		return;
	}
	
	LocalDateTime blockExpire2 = LocalDateTime.of(2021, 3, 27, 13, 29, 59);
	if (sessNickName.equals("성원") && now.isBefore(blockExpire2)) { //now < blockExpire
		out.println("<script>alert('" + CommonUtil.getLangMsg(request, "msg_0134") + " " + blockExpire2 + CommonUtil.getLangMsg(request, "msg_0135") + "');location.replace('/default/logOut');</script>");
		return;
	}
}

String[] Level = new String[100];
Level[0] = CommonUtil.getLangMsg(request, "msg_0136");
Level[1] = CommonUtil.getLangMsg(request, "msg_0137");
Level[2] = CommonUtil.getLangMsg(request, "msg_0138");
Level[3] = CommonUtil.getLangMsg(request, "msg_0139");
Level[4] = CommonUtil.getLangMsg(request, "msg_0140");
Level[5] = CommonUtil.getLangMsg(request, "msg_0141");
Level[6] = CommonUtil.getLangMsg(request, "msg_0142");
Level[7] = CommonUtil.getLangMsg(request, "msg_0143");
Level[8] = CommonUtil.getLangMsg(request, "msg_0144");
Level[9] = CommonUtil.getLangMsg(request, "msg_0145");
Level[10] = CommonUtil.getLangMsg(request, "msg_0146");
Level[11] = CommonUtil.getLangMsg(request, "msg_0147");
Level[98] = CommonUtil.getLangMsg(request, "msg_0148");
Level[99] = CommonUtil.getLangMsg(request, "msg_0149");

pageContext.setAttribute("Level", Level);
%>