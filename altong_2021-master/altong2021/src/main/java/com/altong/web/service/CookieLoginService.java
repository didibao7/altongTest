package com.altong.web.service;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CookieLoginService {

	int getCookieInfo(HttpServletRequest request, HttpServletResponse response, String target);

	String getCookieInfoString(HttpServletRequest request, HttpServletResponse response, String target);

	int getCookieUserSeq(HttpServletRequest request, HttpServletResponse response);

	int getCookieUserLv(HttpServletRequest request, HttpServletResponse response);

	String getCookieUniv(HttpServletRequest request, HttpServletResponse response);

	String getCookieChuCode(HttpServletRequest request, HttpServletResponse response);

	String getCookieSessExpire(HttpServletRequest request, HttpServletResponse response);

	int getCookieVer(HttpServletRequest request, HttpServletResponse response);

	String getCookieAnswer(HttpServletRequest request, HttpServletResponse response);

	String getCookieFlagSelfAnswer(HttpServletRequest request, HttpServletResponse response);

	int getCookieMemberType(HttpServletRequest request, HttpServletResponse response);

	String getCookieUserNickName(HttpServletRequest request, HttpServletResponse response);


	String getCookieUserPhoto(HttpServletRequest request, HttpServletResponse response);
	String getCookieUserDateReg(HttpServletRequest request, HttpServletResponse response);
	BigDecimal getCookieUserAlmoney(HttpServletRequest request, HttpServletResponse response);
	BigDecimal getCookieUserAlpayKR(HttpServletRequest request, HttpServletResponse response);
	int getCookieAlarmCount(HttpServletRequest request, HttpServletResponse response);
	int getCookieRankQ(HttpServletRequest request, HttpServletResponse response);
	int getCookieRankA(HttpServletRequest request, HttpServletResponse response);
	String getCookieUserNation(HttpServletRequest request, HttpServletResponse response);
	String getCookieUserLang(HttpServletRequest request, HttpServletResponse response);
	String getCookieAdminSecu(HttpServletRequest request, HttpServletResponse response);

	int getAlarmRefreshTime(HttpServletRequest request, HttpServletResponse response);

	String getCookieAlmaeng(HttpServletRequest request, HttpServletResponse response);
	String getCookieAlmaengCode(HttpServletRequest request, HttpServletResponse response);

}
