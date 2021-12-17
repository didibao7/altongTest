package com.altong.web.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altong.web.logic.member.MemberVO;
import com.altong.web.logic.util.CommonUtil;
import com.altong.web.logic.util.CookieBox;
import com.altong.web.service.common.CommonService;
import com.altong.web.service.member.MemberService;

@Service
public class CookieLoginServiceImpl implements CookieLoginService {
	@Autowired
	MemberService memberService;

	@Autowired
	CommonService commonService;

	@Override
	public int getCookieUserSeq(HttpServletRequest request, HttpServletResponse response) {
		try {
			return getCookieInfo(request, response, "UserSeq");
		}catch(Exception e) {
			return 0;
		}
	}

	@Override
	public int getCookieUserLv(HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = getCookieUserSeq(request, response);

			// DB 처리
			MemberVO mem = memberService.getMemberInfoViewBySeq(userSeq);

			return Integer.parseInt(mem.getLv());
		}catch(Exception e) {
			return 0;
		}
	}

	@Override
	public String getCookieChuCode(HttpServletRequest request, HttpServletResponse response) {
		try {
			return getCookieInfoString(request, response, "Code");
		}catch(Exception e) {
			return "";
		}
	}

	@Override
	public String getCookieSessExpire(HttpServletRequest request, HttpServletResponse response) {
		try {
			return getCookieInfoString(request, response, "SessExpire");
		}catch(Exception e) {
			return "";
		}
	}

	@Override
	public int getCookieVer(HttpServletRequest request, HttpServletResponse response) {
		try {
			return getCookieInfo(request, response, "Ver");
		}catch(Exception e) {
			return 0;
		}
	}

	@Override
	public String getCookieUniv(HttpServletRequest request, HttpServletResponse response) {
		try {
			return getCookieInfoString(request, response, "Univ");
		}catch(Exception e) {
			return "";
		}
	}

	@Override
	public String getCookieAnswer(HttpServletRequest request, HttpServletResponse response) {
		try {
			return getCookieInfoString(request, response, "Answer");
		}catch(Exception e) {
			return "";
		}
	}

	@Override
	public String getCookieFlagSelfAnswer(HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = getCookieUserSeq(request, response);

			// DB 처리
			MemberVO mem = memberService.getMemberInfoViewBySeq(userSeq);

			return mem.getFlagSelfAnswer();
		}catch(Exception e) {
			return "";
		}
	}

	@Override
	public int getCookieMemberType(HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = getCookieUserSeq(request, response);

			// DB 처리
			MemberVO mem = memberService.getMemberInfoViewBySeq(userSeq);

			return mem.getMemberType();
		}catch(Exception e) {
			return 0;
		}
	}

	@Override
	public String getCookieUserNickName(HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = getCookieUserSeq(request, response);

			// DB 처리
			MemberVO mem = memberService.getMemberInfoViewBySeq(userSeq);

			return mem.getNickName();
		}catch(Exception e) {
			return "";
		}
	}

	@Override
	public String getCookieUserPhoto(HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = getCookieUserSeq(request, response);

			// DB 처리
			MemberVO mem = memberService.getMemberInfoViewBySeq(userSeq);

			return mem.getPhoto() != null ? mem.getPhoto() : "";
		}catch(Exception e) {
			return "";
		}
	}

	@Override
	public String getCookieUserDateReg(HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = getCookieUserSeq(request, response);

			// DB 처리
			MemberVO mem = memberService.getMemberInfoViewBySeq(userSeq);

			return mem.getDateReg();
		}catch(Exception e) {
			return "";
		}
	}

	@Override
	public BigDecimal getCookieUserAlmoney(HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = getCookieUserSeq(request, response);

			Map<String, Object> loginInfo = memberService.getMemberLoginInfoSp(userSeq);

			BigDecimal userAlmoney = new BigDecimal( String.valueOf( loginInfo.get("Almoney") ) );
			//System.out.println("userAlmoney : " + userAlmoney);
			return userAlmoney;
		}catch(Exception e) {
			return new BigDecimal("0.00");
		}
	}

	@Override
	public BigDecimal getCookieUserAlpayKR(HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = getCookieUserSeq(request, response);

			Map<String, Object> loginInfo = memberService.getMemberLoginInfoSp(userSeq);

			BigDecimal alpayKR = new BigDecimal( String.valueOf( loginInfo.get("AlpayKR") ) );

			return alpayKR;
		}catch(Exception e) {
			return new BigDecimal("0.0");
		}
	}

	@Override
	public int getCookieAlarmCount(HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = getCookieUserSeq(request, response);

			HashMap<String, Object> alamrCount = memberService.getUserAlarmCount(userSeq);

			int alarmCnt = Integer.parseInt( alamrCount.get("ANS_CHOICE").toString() )
					+ Integer.parseInt( alamrCount.get("ANS_REGIST").toString() )
					+ Integer.parseInt( alamrCount.get("FAVORITE_QUE_REGIST").toString() )
					+ Integer.parseInt( alamrCount.get("CMT_REGIST").toString() )
					+ Integer.parseInt( alamrCount.get("ANS_CHOICE_READY").toString() )
					+ Integer.parseInt( alamrCount.get("ALMONEY_INCOME").toString() )
					+ Integer.parseInt( alamrCount.get("ALMONEY_PAYING").toString() )
					+ Integer.parseInt( alamrCount.get("MEM_LEVEL_UP").toString() )
					+ Integer.parseInt( alamrCount.get("REPORT").toString() )
					+ Integer.parseInt( alamrCount.get("MENTEE").toString() )
					+ Integer.parseInt( alamrCount.get("MENTEE_UNSET").toString() )
					+ Integer.parseInt( alamrCount.get("RECOMM_MEM_JOIN").toString() )
					+ Integer.parseInt( alamrCount.get("NOTICE").toString() )
					+ Integer.parseInt( alamrCount.get("ALARM").toString() );

			return alarmCnt;
		}catch(Exception e) {
			return 0;
		}
	}

	@Override
	public int getCookieRankQ(HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = getCookieUserSeq(request, response);

			Map<String, Object> loginInfo = memberService.getMemberLoginInfoSp(userSeq); //SP_MEMBER_LOGIN_INFO

			final int rankQ = loginInfo.get("RankQ") != null ? Integer.parseInt(String.valueOf( loginInfo.get("RankQ") )) : 0;

			return rankQ;
		}catch(Exception e) {
			return 0;
		}
	}

	@Override
	public int getCookieRankA(HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = getCookieUserSeq(request, response);

			Map<String, Object> loginInfo = memberService.getMemberLoginInfoSp(userSeq); //SP_MEMBER_LOGIN_INFO

			final int rankA = loginInfo.get("RankA") != null ? Integer.parseInt(String.valueOf( loginInfo.get("RankA") )) : 0;

			return rankA;
		}catch(Exception e) {
			return 0;
		}
	}

	@Override
	public String getCookieUserNation(HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = getCookieUserSeq(request, response);

			Map<String, Object> loginInfo = memberService.getMemberLoginInfoSp(userSeq); //SP_MEMBER_LOGIN_INFO

			final String nation = loginInfo.get("nation") != null ? String.valueOf( loginInfo.get("nation") ) : "kor";

			return nation;
		}catch(Exception e) {
			return "KOR";
		}
	}

	@Override
	public String getCookieUserLang(HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = getCookieUserSeq(request, response);

			Map<String, Object> loginInfo = memberService.getMemberLoginInfoSp(userSeq); //SP_MEMBER_LOGIN_INFO

			final String lang = loginInfo.get("lang") != null ? String.valueOf( loginInfo.get("lang") ) : "ko";

			return lang;
		}catch(Exception e) {
			return "ko";
		}
	}

	@Override
	public String getCookieAdminSecu(HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = getCookieUserSeq(request, response);

			final String adminSecu = commonService.getAuthority(userSeq) != null ? commonService.getAuthority(userSeq) : "";

			return adminSecu;
		}catch(Exception e) {
			return "";
		}
	}

	@Override
	public int getAlarmRefreshTime(HttpServletRequest request, HttpServletResponse response) {
		try {
			return getCookieInfo(request, response, "ALARM_REFRESH_TIME");
		}catch(Exception e) {
			return 0;
		}
	}

	@Override
	public String getCookieAlmaeng(HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = getCookieUserSeq(request, response);

			Map<String, Object> loginInfo = memberService.getMemberLoginInfoSp(userSeq);

			return String.valueOf( loginInfo.get("Almaeng") ) ;
		}catch(Exception e) {
			return "0";
		}
	}

	@Override
	public String getCookieAlmaengCode(HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = getCookieUserSeq(request, response);

			Map<String, Object> loginInfo = memberService.getMemberLoginInfoSp(userSeq);

			return String.valueOf(loginInfo.get("AlmaengCode"));
		}catch(Exception e) {
			return "";
		}
	}

	@Override
	public int getCookieInfo(HttpServletRequest request, HttpServletResponse response, String target) {
		try {
			final JSONObject global = new CookieBox(request).getCookie("SESS") != null ? CommonUtil.getGlobal(request, response) : null;

			return global != null? Integer.parseInt( String.valueOf(global.get(target) ) ) : 0;
		}catch(Exception e) {
			return 0;
		}
	}

	@Override
	public String getCookieInfoString(HttpServletRequest request, HttpServletResponse response, String target) {
		try {
			final JSONObject global = new CookieBox(request).getCookie("SESS") != null ? CommonUtil.getGlobal(request, response) : null;

			return global != null? String.valueOf( global.get(target) )  : "";
		}catch(Exception e) {
			return "";
		}
	}
}
