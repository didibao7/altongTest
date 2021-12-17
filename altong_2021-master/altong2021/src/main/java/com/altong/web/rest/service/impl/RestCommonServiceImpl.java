package com.altong.web.rest.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altong.web.dao.MyZzimDAO;
import com.altong.web.dao.RestDAO;
import com.altong.web.logic.util.CommonUtil;
import com.altong.web.logic.util.CookieBox;
import com.altong.web.rest.service.RestAnswerService;
import com.altong.web.rest.service.RestCommonService;
import com.altong.web.rest.service.RestQuestionService;
import com.altong.web.service.common.CommonService;
import com.altong.web.service.reply.ReplyService;
import com.google.common.collect.ImmutableMap;

@Service
public class RestCommonServiceImpl implements RestCommonService{
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	RestCommonService restCommonService;
	
	@Autowired
	RestQuestionService restQuestionService;
	
	@Autowired
	RestAnswerService restAnswerService;
	
	@Autowired
	MyZzimDAO myZzimDAO;
	
	@Autowired
	RestDAO restDAO;
	
	@Autowired
	ReplyService replyService;
	
	@Override
	public int getRestCookieUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return getRestCookieUserInfo(request, response, "UserSeq");
	}
	
	@Override
	public int getRestCookieUserLv(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return getRestCookieUserInfo(request, response, "UserLv");
	}
	
	@Override
	public int getRestCookieUserInfo(HttpServletRequest request, HttpServletResponse response, String target) throws Exception {
		final JSONObject global = new CookieBox(request).getCookie("SESS") != null ?
				CommonUtil.getGlobal(request, response) : null;
				
		return global != null? Integer.parseInt( String.valueOf(global.get(target) ) ) : 0;
	}
	
	@Override
	public Map<String, Object> getRestGameRouletteGiveTicketCountUpService(Map<String, Object> param) {
		
		// 증정시 1000원 이상이라면 오늘 게임 스택 1회 증가 - 20210225 김주윤 - 시작
		final int userSeq = (int) param.get("userSeq");
		final String XCount = (String) param.get("XCount");
		final int CountConfig = (int) commonService.getTicketConfig().get(XCount);
		final String flag = (String) param.get("flag");
		final int targetSeq = (int) param.get("targetSeq");
		final String mode = (String) param.get("mode");
		
		final ArrayList<String> settingCount = new ArrayList<String>() { private static final long serialVersionUID = 2837628376811L;
			{		// 원리 어레이(컬렉션)에 각 사용할 XCount 문자열을 넣어 초기화합니다.
					// ***만약 디비 컬럼이 추가되면 해당 값도 추가해야합니다.***						******************** 중요 ******************** 
				add("queCount");
				add("ansCount");
				add("replCount");
				add("hunCount");
				add("estiCount");
			}
		};
		final HashMap<String, Object> cParam = new HashMap<String, Object>() {
			private static final long serialVersionUID = -18798798924L;
			{	put("flag", flag); put("targetSeq", targetSeq);	}
		};
		final String sourceDate = commonService.getContentsDateBySeq(cParam);
		boolean checkRouletteKind;
		try {
			checkRouletteKind = CommonUtil.checkRoulettePublish(sourceDate);
			if(commonService.getTicketStackCnt(userSeq) > 0) {
				final HashMap<String, Object> sParam = new HashMap<String, Object>() {
					private static final long serialVersionUID = -18798798924L;
					{	put("userSeq", userSeq); put("mode", mode);	}
				};
				if(checkRouletteKind)	commonService.setAddTickStackUse(sParam);
			} else {
				HashMap<String, Object> sParam = new HashMap<String, Object>() {
					private static final long serialVersionUID = -358296967L;
					{	put("userSeq", userSeq);
						settingCount.forEach( (val) -> { if(val.equals(XCount)){ put(val, 1); } else { put(val, 0);	} } );	}
				};
				if(checkRouletteKind)	commonService.addTicketStack(sParam);
			}
			if( (int)commonService.getTicketStack(userSeq).get(XCount) >= CountConfig) {
				final String startDt = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
				
				HashMap<String, Object> tParam = new HashMap<String, Object>() {
					private static final long serialVersionUID = -135756798924L;
					{	put("userSeq", userSeq);
						put("startDt", startDt);
						put("endDt", startDt); // 당일 23시 59분 59초 이내로 설정 변경
						put("gType", 1); // 1: 알머니, 2:사은품
						put("rType", mode);	}
				};
				// 이용권 추가
				commonService.addTicket(tParam);
				
				HashMap<String, Object> sParam = new HashMap<String, Object>() {
					private static final long serialVersionUID = -1879807870524L;
					{	put("userSeq", userSeq);
						put("mode", mode);
						put( XCount , CountConfig);	}
				};
				// 스택 차감
				commonService.setSubTickStackUse(sParam);
				return new HashMap<String, Object>() {		// 
					private static final long serialVersionUID = -876868686524L;
					{	put("code","goGame");	}
				};
			}
			
			
			return new HashMap<String, Object>() {
				private static final long serialVersionUID = -87974936524L;
				{	put("code","stack");
					put("xcount", (int)commonService.getTicketStack(userSeq).get(XCount));
					put("CountConfig", CountConfig);	}
			};
			
		} catch (Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}
	
	@Override
	public String restGetLevelName(int userLv) {
		String[] Level = new String[100];
		Level[0] = "알";
		Level[1] = "알천사";
		Level[2] = "나비천사";
		Level[3] = "미소천사";
		Level[4] = "열혈천사";
		Level[5] = "황금천사";
		Level[6] = "수호천사";
		Level[7] = "빛의천사";
		Level[8] = "천사장";
		Level[9] = "대천사";
		Level[10] = "대천사장";
		Level[11] = "알통폐인";
		Level[98] = "알돌이";
		Level[99] = "관리자";
		return Level[userLv].toString();
	}
	
	@Override
	public Map<String, Object> putAnswerQuestionVote(Map<String, Object> param) {
		final int userSeq = (int) param.get("userSeq");
		final String contentType = (String) param.get("contentType");
		final int contentSeq = Integer.parseInt((String) param.get("contentSeq"));
		final String estiSeq = (String) param.get("estiSeq");
		
		if(userSeq == 0) return ImmutableMap.of( "code", "notlogin" );
		
		if(contentSeq < 1) return ImmutableMap.of( "code", "notcontent" );
		
		final HashMap<String, Object> vote = commonService.getQuestionVote(new HashMap<String, Object>() {
			private static final long serialVersionUID = -45398534124L;
			{	put("contentSeq", contentSeq);	//int
				put("contentType", contentType);	//string
				put("userSeq", userSeq);	//int	
			}
		});
		
		if(commonService.getQuestionVoteCount(
			new HashMap<String, Object>() {
				private static final long serialVersionUID = -45398534124L;
				{	put("contentSeq", contentSeq);	//int
					put("contentType", contentType);	//string
					put("userSeq", userSeq);	//int	
				}
			} ) > 0)
		{
			final int good = estiSeq.equals("G") ? (  Integer.parseInt(Long.toString((long)vote.get("good"))) == 0 ? 
					Integer.parseInt(Long.toString((long)vote.get("good"))) + 1 : Integer.parseInt(Long.toString((long)vote.get("good"))) ) :
				( Integer.parseInt(Long.toString((long)vote.get("good"))) > 0 ?
						Integer.parseInt(Long.toString((long)vote.get("good"))) - 1 : Integer.parseInt(Long.toString((long)vote.get("good"))) );
			final int bad = estiSeq.equals("G") ? ( Integer.parseInt(Long.toString((long)vote.get("bad"))) > 0 ?
					Integer.parseInt(Long.toString((long)vote.get("bad"))) - 1 : Integer.parseInt(Long.toString((long)vote.get("bad"))) ) :
				( Integer.parseInt(Long.toString((long)vote.get("bad"))) == 0 ?
						Integer.parseInt(Long.toString((long)vote.get("bad"))) + 1 : Integer.parseInt(Long.toString((long)vote.get("bad"))) );
			
			commonService.setQuestionVote(new HashMap<String, Object>() {
				private static final long serialVersionUID = -45396839124L;
				{	put("contentSeq", contentSeq);	//int
					put("contentType", contentType);	//string
					put("userSeq", userSeq);	//int	
					put("good", good);
					put("bad", bad);
				}
			});
			

			final HashMap<String, Object> voteItem = commonService.getQuestionVoteSum(new HashMap<String, Object>() {
				private static final long serialVersionUID = -45396839124L;
				{	put("contentSeq", contentSeq);	//int
					put("contentType", contentType);	//string
				}
			});
			
			return new HashMap<String, Object>() {
				private static final long serialVersionUID = -453982549824L;
				{	
					put("code", "success");
					put("good", voteItem.get("good"));
					put("bad", voteItem.get("bad"));
				}
			};
			
		}else {
			commonService.addQuestionVote(new HashMap<String, Object>() {
				private static final long serialVersionUID = -45396839124L;
				{	put("contentSeq", contentSeq);	//int
					put("contentType", contentType);	//string
					put("userSeq", userSeq);	//int	
					put("good", estiSeq.equals("G") ? 1 : 0 );
					put("bad", estiSeq.equals("G") ? 0 : 1 );
				}
			});
			final HashMap<String, Object> voteItem = commonService.getQuestionVoteSum(new HashMap<String, Object>() {
				private static final long serialVersionUID = -45396839124L;
				{	put("contentSeq", contentSeq);	//int
					put("contentType", contentType);	//string
				}
			});
			
			return new HashMap<String, Object>() {
				private static final long serialVersionUID = -453982549824L;
				{	
					put("code", "success");
					put("good", voteItem.get("good"));
					put("bad", voteItem.get("bad"));
				}
			};
		}
	}
	
	/*
	 * nulldata :  
	 * continuetime :
	 * */
	@Override
	public Map<String, Object> putRestReplyQA(Map<String, Object> param) {
		
		try {
			
				
			final String flag = (String)param.get("Flag");
			final String contentSeq = (String)param.get("contentSeq");
			final int userSeq = (int)param.get("userSeq");
			final int userLv = (int)param.get("userLv");
			final String text = param.get("text") != null ? 
					( null == commonService.getAuthority(userSeq) ? 
					((String) param.get("text")).replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "")
					: (String)param.get("text") ) : "";
			
			final String regDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
			
			if( null == text || "" == text ) {
				return new HashMap<String, Object>() {
					private static final long serialVersionUID = -117374624L;
					{	put("code",	"nulldata");	};
				};
			} else if( 0 == userSeq ) {
				return new HashMap<String, Object>() {
					private static final long serialVersionUID = -11235377864L;
					{	put("code",	"notlogin");	};
				};
			}
			
			if( userLv >= 1 && userLv <= 11 ) {
				final Map<String, Object> config = restDAO.getRestBoardConfig();
				final int lv_LimitDayRegCnt = (int) config.get("Lv"+userLv+"_LimitRepDayRegistCnt");
				final int lv_LimitDayRegDupCnt = (int) config.get("Lv"+userLv+"_LimitRepDayDupRegistCnt");
				final int lv_LimitRegContinueCntTime = (int) config.get("Lv"+userLv+"_LimitRepContinueRegistTime");
				
				// day limit
				if( commonService.getUserReplyCnt(new HashMap<String, Object>() {
					private static final long serialVersionUID = -1542782814L;
					{	put("userSeq",	userSeq);	
						put("regDate",	regDate);	}
					}) > lv_LimitDayRegCnt ) {
					return new HashMap<String, Object>() {
						private static final long serialVersionUID = -11657226264L;
						{	put("code",	"daylimit");
							put("num", lv_LimitDayRegCnt); }
					};
				} else	// day dup
					if( commonService.getUserReplyCnt2(new HashMap<String, Object>() {
					private static final long serialVersionUID = -1542782814L;
					{	put("userSeq",	userSeq);	
						put("regDate",	regDate);	
						put("contents", text);		}
					}) > lv_LimitDayRegDupCnt ) {
					return new HashMap<String, Object>() {
						private static final long serialVersionUID = -11657226264L;
						{	put("code",	"daydup");
							put("num", lv_LimitDayRegDupCnt); }
					};
				} else 
					if ( 0 != commonService.getDateDiffSecondForReply(userSeq) &&
							lv_LimitRegContinueCntTime >= commonService.getDateDiffSecondForReply(userSeq)
						) {
					return new HashMap<String, Object>() {
						private static final long serialVersionUID = -11657226264L;
						{	put("code",	"continuetime");
							put("num", lv_LimitRegContinueCntTime); }
					};
				}
				
				final String dateReg = new SimpleDateFormat("yyyy-MM-dd aa hh:mm:ss").format(Calendar.getInstance().getTime());
				final String detectLangVal = CommonUtil.detectLanguage(text);
				final String detectLang = !detectLangVal.equals("und") ? ( detectLangVal.contains("-")
						? detectLangVal.split("-")[0] : detectLangVal ) : restDAO.getRestUserLang(userSeq);
				
				replyService.setReply(new HashMap<String, Object>() {
					private static final long serialVersionUID = -54328673246814L;
					{	put("flag",			flag);	
						put("targetSeq",	contentSeq);	
						put("userSeq",		userSeq);	
						put("contents",		text);
						put("lang",			detectLang);	
						put("dateReg",		dateReg);	}
					});
				
				final Map<String, Object> gameMap = new HashMap<String, Object>() {
					private static final long serialVersionUID = -345721175477L;
					{	put("userSeq", userSeq);
						put("mode","repl");
						put("XCount", "replCount");
						put("flag",flag);
						put("targetSeq", Integer.parseInt(contentSeq));	}
				};
				return new HashMap<String, Object>() {
					private static final long serialVersionUID = -11657226264L;
					{	put("code",	"success");
						put("game", restCommonService.getRestGameRouletteGiveTicketCountUpService(gameMap));
						put("replys", flag=="Q" ? restQuestionService.restGetQuestionReplys(contentSeq) : restAnswerService.restGetAnswerReplys(contentSeq) ); }
				};
			}
			
			return new HashMap<String, Object>() {
				private static final long serialVersionUID = -174537527264L;
				{	put("code",	"exit"); }
			};
		} catch (IOException e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}
	
	@Override
	public Map<String, Object> questionToJson(Map<String, Object> param) {
		final String seqComponent =  "Q";
		final int seqId =  (int) param.get("MemberSeq");

		final Map<String, Object> head4 = restDAO.getRestUserHead(ImmutableMap.of( "Seq", param.get("MemberSeq") ));
		final String contents =  (String) param.get("Contents");
		final String good =  "good";
		final String bad =  "bad";
		final String almoney =  "almoney";      // 훈훈알 수정
		
		return ImmutableMap.<String, Object> builder()
				.put("seqComponent", seqComponent )
				.put("seqId", seqId )
				.put("head", ImmutableMap.<String, Object> builder()
							.put("profile", head4.get("profile") == null ? "" : head4.get("profile") )
							.put("locale", head4.get("locale") )
							.put("uLv", head4.get("uLv") )
							.put("nick", head4.get("nick") )
							.put("thankAlmoney", param.get("Almoney") )
							.put("title", param.get("Title") )
							.put("persent", "persent" ) // 감사알 지급률 수정 
							.put("date", param.get("regdate") )
							.put("readCount", param.get("ReadCount") )
							.build() )
				.put("contents", contents )
				.put("good", good )
				.put("bad", bad )
				.put("almoney", almoney )
				.put("mini", ImmutableMap.<String, Object> builder()
							.put("nick", head4.get("nick") )
							.put("uLv", head4.get("uLv") )
							.put("uHref", "uHref" )
							.put("qBenefit", restDAO.getRestTotalQuestionBenefit((int)param.get("MemberSeq")) )
							.put("ABenefit", restDAO.getRestTotalAnswerBenefit((int)param.get("MemberSeq")) )
							.put("giveThankNum", head4.get("QCount") )
							.put("giveThankRate", (int)head4.get("QChoice") / (int)head4.get("QCount") )
							.put("descript", head4.get("Intro")==null? "" : head4.get("Intro") )
							.put("alBenefit", restDAO.getRestTotalBenefit((int)param.get("MemberSeq")) )
							.build() )
				.build();
	}

	@Override
	public Map<String, Object> AnswerToJson(Map<String, Object> param) {
		final String seqComponent =  "A";
		final int seqId = (int) param.get("MemberSeq");
		final long pageSeq =  (long) param.get("Seq");
		
		final String contents =  (String) param.get("Answer");
		final String good =  "good";
		final String bad =  "bad";
		final String almoney =  "almoney";      // 훈훈알 수정

		final Map<String, Object> head4 = restDAO.getRestUserHead(ImmutableMap.of( "Seq", param.get("MemberSeq") ));
		
		final Map<String, Object> result =new HashMap<String, Object>() {
			private static final long serialVersionUID = -5129628467395047901L;
			{	put("pageSeq", pageSeq);
				put("seqComponent", seqComponent );
				put("seqId", seqId );
				put("head",	ImmutableMap.<String, Object> builder()
							.put("profile", head4.get("profile") )
							.put("locale", head4.get("locale") )
							.put("uLv", head4.get("uLv") )
							.put("nick", head4.get("nick") )
							.put("persent", "persent" ) 
							.put("date", param.get("regdate") )
							.put("readCount", param.get("ReadCount") )
							.build() );
				put("contents", contents );
				put("good", good );
				put("bad", bad );
				put("replys", restAnswerService.restGetAnswerReplys(Long.toString(pageSeq)));
				put("almoney", almoney );
				put("etimate", 	ImmutableMap.<String, Object> builder()
								.put("v1", param.get("PointCount1"))
								.put("v2", param.get("PointCount2"))
								.put("v3", param.get("PointCount3"))
								.put("v4", param.get("PointCount4"))
								.put("v5", param.get("PointCount5"))
								.put("v6", param.get("PointCount6"))
								.build() );
				put("mini", ImmutableMap.<String, Object> builder()
						.put("nick", head4.get("nick") )
						.put("uLv", head4.get("uLv") )
						.put("uHref", "uHref" )
						.put("qBenefit", restDAO.getRestTotalQuestionBenefit((int)param.get("MemberSeq")) )
						.put("ABenefit", restDAO.getRestTotalAnswerBenefit((int)param.get("MemberSeq")) )
						.put("giveThankNum", head4.get("QCount") )
						.put("giveThankRate", (int)head4.get("QChoice") / (int)head4.get("QCount") )
						.put("descript", head4.get("Intro") == null ? "" : head4.get("Intro") )
						.put("alBenefit", restDAO.getRestTotalBenefit((int)param.get("MemberSeq")) )
						.build() );
				put("choice", param.get("FlagChoice").equals("Y")? true : false );
				put("netizen", false );	}
		};
		
		return result;
	}
	
	@Override
	public Map<String, Object> getRestVoteSum(Map<String, Object> param){
		return restDAO.getRestVoteSum(param);
	}
	
	@Override
	public int getRestZzimCountUp(HashMap<String, Object> param) {
		final int count =  myZzimDAO.getAnswerZimChk(param);
		if(count == 0) myZzimDAO.setAnswerZzim(param);
		return count;
	}
	
	@Override
	public int getRestTodayUseAl(int userSeq) {
		final String act = "E";

		final BigDecimal Balmoney = commonService.getChkUseAlmoney(
			new HashMap<String, Object>() {	// 답변 훈훈알
				private static final long serialVersionUID = -138437684763297L;
				{	
					put("userSeq", userSeq);
					put("act", act);
					put("regDate", new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
				}
			}
		);
		return Balmoney.compareTo(BigDecimal.ZERO) < 0 ? Balmoney.intValue() * (-1) : Balmoney.intValue();
	}
	
	@Override
	public String getRestCookieUserNick(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		final JSONObject global = cookies1 != null? CommonUtil.getGlobal(request, response) : null;
		final String userNick = global != null? (String) global.get("UserNickName") : "";
		
		return userNick;
	}

	@Override
	public Map<String, Object> restExtraAlmoney(HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userLv = getRestCookieUserLv(request, response);
			if(userLv < 2) 
				return ImmutableMap.<String, Object> builder()
						.put( "message", restGetLevelName(userLv) + " 이상의 등급만 이용할 수 있습니다." )
						.put( "code","rowlv" )
						.build();

			final int userSeq = getRestCookieUser(request, response);
			final int MaxMoney = userLv > 3 ? 300000 : 30000;
			
			final int useAvaibleAlmoney = userSeq > 0? MaxMoney - getRestTodayUseAl(userSeq) : -1;
			
			return ImmutableMap.<String, Object> builder()
					.put( "message","" )
					.put( "almoney", useAvaibleAlmoney )
					.put( "code",useAvaibleAlmoney==-1? "no_user":"find" )
					.build();
		
		}catch (Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}
}
