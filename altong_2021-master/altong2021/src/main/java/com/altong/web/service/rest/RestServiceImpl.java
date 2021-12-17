package com.altong.web.service.rest;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altong.web.dao.MyZzimDAO;
import com.altong.web.dao.RestDAO;
import com.altong.web.logic.almoney.AlmoneyVO;
import com.altong.web.logic.util.CommonUtil;
import com.altong.web.logic.util.CookieBox;
import com.altong.web.service.answer.AnswerService;
import com.altong.web.service.common.CommonService;
import com.altong.web.service.reply.ReplyService;
import com.google.common.collect.ImmutableMap;

@Service
public class RestServiceImpl implements RestService {

	@Autowired RestDAO restDAO;
	@Autowired MyZzimDAO myZzimDAO;
	@Autowired CommonService commonService;
	@Autowired ReplyService replyService;
	@Autowired AnswerService answerService;

	@Override
	public Map<String, Object> getOneRestQuestion(Map<String, Object> param){
		return restDAO.getOneRestQuestion(param);
	}

	@Override
	public List<Map<String, Object>> getRestQuestionReplys(Map<String, Object> param) {
		final List<Map<String, Object>> replys = new ArrayList<Map<String,Object>>() {
			private static final long serialVersionUID = -5129628467395041234L;
			{
				restDAO.getRestQeustionReplys(param).forEach(list -> {
					final Map<String, Object> reply = new HashMap<String, Object>() {
						private static final long serialVersionUID = -5129628467359821234L;
						{
							Map<String, Object> getProfile = restDAO.getRestUserHead(ImmutableMap.of( "Seq", list.get("MemberSeq") ) );
							
							put("id", list.get("id"));
							put("profile", ImmutableMap.<String, Object> builder()
									.put("seqId", list.get("MemberSeq"))
									.put("img", getProfile.get("profile") == null ? "" : getProfile.get("profile") )
									.put("locale", getProfile.get("locale"))
									.put("nick", getProfile.get("nick"))
									.build() );
							put("content", list.get("Reply"));
							put("time", "time" );
							put("date", list.get("DateReg") );
							put("nick1",list.get("nick1") == null ? "" : list.get("nick1"));
							put("nick2",list.get("nick2") == null ? "" : list.get("nick2"));
							put("almoney",list.get("almoney"));
							put("seq", list.get("Seq"));
						}
					};
					add(reply);
				});
			}
		};
		return replys;
	}

	@Override
	public List<Map<String, Object>> getRestAnswerList(Map<String, Object> param){
		return restDAO.getRestAnswerList(param);
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
				put("replys", restGetAnswerReplys(Long.toString(pageSeq)));
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
	public Map<String, Object> getRestQuestionExtraAlmoney(int seq) {
		return restDAO.getRestQuestionExtraAlmoney( ImmutableMap.of( "QuestionSeq", seq) );
	}

	@Override
	public Map<String, Object> getRestAnswerExtraAlmoney(int seq) {
		return restDAO.getRestAnswerExtraAlmoney( ImmutableMap.of( "AnswerSeq", seq ) );
	}

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
	public int getRestZzimCountUp(HashMap<String, Object> param) {
		final int count =  myZzimDAO.getAnswerZimChk(param);
		if(count == 0) myZzimDAO.setAnswerZzim(param);
		return count;
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
	public Map<String, Object> restQeustionExtraAlmoney(String question, HttpServletRequest request, HttpServletResponse response) {
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

	@Override
	public Map<String, Object> restAnswerExtraAlmoney(String answer, HttpServletRequest request,
			HttpServletResponse response) {
		return restQeustionExtraAlmoney( "", request, response );
	}

	@Override
	public Map<String, Object> getRestConvertSumExtraAlmoneyInfo(Map<String, Object> param) {

		final DecimalFormat nf = new DecimalFormat("###,###");
		final String contentsSeq =(String) param.get("contentsSeq");
		final String userSeq =(String) param.get("userSeq");
		final int typeMe = 2;
		final int extraAlmoney = (int) param.get("extraAlmoney");
		final int minusExtraAlmoney = 0 - extraAlmoney;
		final int typeYou = 3;
		final String qusAns ="QUESTION";
		final String cType ="Q";
		final String extraAlmoneyFmt = nf.format( extraAlmoney );
		final int maxExtraAlmoney = (int) param.get("MaxExtraAlmoney");
		final String tbl ="T_REPLY_QUESTION";
		final String qusAnsTbl ="T_QUESTION";
		final String qusAnsCol ="QUESTIONSeq";
		
		final HashMap<String, Object> map = new HashMap<String, Object>() {
			private static final long serialVersionUID = -11111111124L;
			{	put("contentsSeq", 			contentsSeq);
				put("userSeq", 				userSeq);
				put("typeMe", 				typeMe);
				put("minusExtraAlmoney", 	minusExtraAlmoney);
				put("extraAlmoney", 		extraAlmoney);
				put("typeYou", 				typeYou);
				put("qusAns", 				qusAns);
				put("cType", 				cType);
				put("extraAlmoneyFmt", 		extraAlmoneyFmt);
				put("maxExtraAlmoney", 		maxExtraAlmoney);
				put("tbl", 					tbl);
				put("qusAnsTbl", 			qusAnsTbl);
				put("qusAnsCol", 			qusAnsCol);	}
		};
		
		return restDAO.getRestConvertSumExtraAlmoneyInfo(map);
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
	public Map<String, Object> getRestQuestionRunExcept(Map<String, Object> map, Calendar cal) {
		final String ExtraAlmoney = (String) map.get("ExtraAlmoney"); 
		final int n = (int) (Math.floor(Integer.parseInt(ExtraAlmoney)));
		final int userSeq = (int) map.get("UserSeq"); 
		final int QuestionSeq = (int) map.get("QuestionSeq"); 
		
		try {
			final HashMap<String, Object> rParam = new HashMap<String, Object>() {
				private static final long serialVersionUID = -88878780524L;
				{
					if ((int) map.get("UserLv") < 2) {
						put("msg", restGetLevelName(2) + " 이상의 등급만 이용할 수 있습니다.");
						put("code", "fail");
					}else if( ExtraAlmoney.equals("") || ExtraAlmoney.equals(null) || ExtraAlmoney.equals("0") ) {
						put("msg", "훈훈알을 입력해주세요.");
						put("code", "fail");
					}else if( Integer.parseInt(ExtraAlmoney) < 300 ) {
						put("msg", "최소 훈훈알은 300알입니다.");
						put("code", "fail");
					}else if( Integer.parseInt(ExtraAlmoney) > 10000 ) {
						put("msg", "최대 훈훈알은 10000알입니다.");
						put("code", "fail");
					}else if( Integer.parseInt(ExtraAlmoney) != n) {
						put("msg", "100알 단위로 입력해주세요.");
						put("code", "fail");
					}else {
						// 같은 유저가 n초(default:10) 안에 두번 이상 알을 주지 못하도록 설정 (2021-02-03 김주윤)
						final AlmoneyVO extraAlmoneyListUserTime = commonService.getExtraAlmoneyListUserTime(new HashMap<String, Object>() {
							private static final long serialVersionUID = -1879807870524L;
							{	put("contentsSeq", QuestionSeq);
								put("cType", "Q");
								put("userSeq", userSeq);	}
						});
						Date time_me = new Date();
						cal.setTime(time_me);
						cal.add(Calendar.SECOND, -30);
						time_me = cal.getTime();
	
						if(extraAlmoneyListUserTime != null) {
							final Date userTime = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss").parse(extraAlmoneyListUserTime.getConDate());
							if(userTime.getTime() > time_me.getTime()) {
								put("msg", "이미 훈훈알을 증정하셨습니다. \n다시 증정하시려면 "+(userTime.getTime() - time_me.getTime())/1000 + "초 뒤에 시도해주세요.");
								put("code", "fail");
							}else {	put("code", "success");	}
						}else {	put("code", "success");	}
					}
				}
			};
			
			return rParam;
			
		}catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	@Override
	public Map<String, Object> restQuestionPutAlmoney( int extraAlmoney, String question, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = getRestCookieUser(request, response);
			final int userLv = getRestCookieUserLv(request, response);
			
			final int MinExtraAlmoney = 300;
			final int MaxExtraAlmoney = 10000;
			Calendar cal = Calendar.getInstance();
			
			final Map<String, Object> exception = getRestQuestionRunExcept(
				new HashMap<String, Object>(){
					private static final long serialVersionUID = 3298762396823911L;{
						put("UserLv",userLv);
						put("ExtraAlmoney",Integer.toString(extraAlmoney));
						put("UserSeq",userSeq);
						put("QuestionSeq", Integer.parseInt(question));
					}
				}, cal
			);
			if(exception.get("code").equals("success")) {
			}else if(exception.get("code").equals("fail") || exception.get("code").equals("error")) {
				return exception;
			}
			
			final Map<String,Object> restQeustionExtraAlmoney = restQeustionExtraAlmoney(question, request, response);
			if ( restQeustionExtraAlmoney.get("code") != "find" )	return restQeustionExtraAlmoney;
			final Map<String, Object> map = new HashMap<String, Object>() {
				private static final long serialVersionUID = -81612611778124L;
				{	put("contentsSeq", question);
					put("userSeq", Integer.toString(userSeq) );
					put("typeMe", 2 );
					put("extraAlmoney", extraAlmoney );
					put("MinExtraAlmoney", MinExtraAlmoney );
					put("MaxExtraAlmoney", MaxExtraAlmoney );	}
			};
			
			final int recode = (int) getRestConvertSumExtraAlmoneyInfo(map).get("ReturnCode");
			final Map<String, Object> resultMap = new HashMap<String, Object>() {
				private static final long serialVersionUID = -81612611778124L;
				{
					switch (recode) {
					case 1:
						if (extraAlmoney > 1000) {
							final Map<String, Object> gameMap = new HashMap<String, Object>() {
								private static final long serialVersionUID = -395723697287L;
								{	put("userSeq", userSeq );
									put("mode","hun");
									put("XCount", "hunCount");
									put("flag","Q");
									put("targetSeq", Integer.parseInt(question));	}
							};
							put("game", getRestGameRouletteGiveTicketCountUpService(gameMap));
						}else {	put("game","no");	}
								put("code","good");		break;
					case 2 :	put("code","row");		break;
					case 51 :	put("code","noExist");	break;
					case 52 :	put("code","me");		break;
					case 53 :	put("code","tomuch");	break;
					default:	put("code","error");	break;
					} 
				}
			};
			return resultMap;
		}catch (Exception e) {
			return ImmutableMap.of( "code", "errorMap", "error", e );
		}
	}

	@Override
	public Map<String, Object> restAnswerPutAlmoney( int extraAlmoney, String answer,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = getRestCookieUser(request, response);
			final int userLv = getRestCookieUserLv(request, response);
			
			final int MinExtraAlmoney = 300;
			final int MaxExtraAlmoney = 10000;
			Calendar cal = Calendar.getInstance();
			
			final Map<String, Object> exception = getRestAnswerRunExcept(
				new HashMap<String, Object>(){
					private static final long serialVersionUID = 3298762396823911L;{
						put("UserLv",userLv);
						put("ExtraAlmoney",Integer.toString(extraAlmoney));
						put("UserSeq",userSeq);
						put("AnswerSeq", Integer.parseInt(answer));
					}
				}, cal
			);
			if(exception.get("code").equals("success")) {
			}else if(exception.get("code").equals("fail") || exception.get("code").equals("error")) {
				return exception;
			}
			
			final Map<String,Object> restAnswerExtraAlmoney = restAnswerExtraAlmoney(answer, request, response);
			if ( restAnswerExtraAlmoney.get("code") != "find" )	return restAnswerExtraAlmoney;
			final Map<String, Object> map = new HashMap<String, Object>() {
				private static final long serialVersionUID = -81612611778124L;
				{	put("contentsSeq", answer);
					put("userSeq", Integer.toString(userSeq) );
					put("typeMe", 4 );
					put("extraAlmoney", extraAlmoney );
					put("MinExtraAlmoney", MinExtraAlmoney );
					put("MaxExtraAlmoney", MaxExtraAlmoney );	}
			};
			
			final int recode = (int) getRestAnswerConvertSumExtraAlmoneyInfo(map).get("ReturnCode");
			final Map<String, Object> resultMap = new HashMap<String, Object>() {
				private static final long serialVersionUID = -81612611778124L;
				{
					switch (recode) {
					case 1:
						if (extraAlmoney > 1000) {
							final Map<String, Object> gameMap = new HashMap<String, Object>() {
								private static final long serialVersionUID = -395723697287L;
								{	put("userSeq", userSeq );
									put("mode","hun");
									put("XCount", "hunCount");
									put("flag","A");
									put("targetSeq", Integer.parseInt(answer));	}
							};

								put("game", getRestGameRouletteGiveTicketCountUpService(gameMap));
						}else {	put("game","no");	}
								put("code","good");		break;
					case 2 :	put("code","row");		break;
					case 51 :	put("code","noExist");	break;
					case 52 :	put("code","me");		break;
					case 53 :	put("code","tomuch");	break;
					default:	put("code","error");	break;
					} 
				}
			};
			return resultMap;
		}catch (Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	@Override
	public Map<String, Object> getRestAnswerRunExcept(Map<String, Object> map, Calendar cal) {
		final String ExtraAlmoney = (String) map.get("ExtraAlmoney"); 
		final int n = (int) (Math.floor(Integer.parseInt(ExtraAlmoney)));
		final int userSeq = (int) map.get("UserSeq"); 
		final int AnswerSeq = (int) map.get("AnswerSeq"); 
		
		try {
			final HashMap<String, Object> rParam = new HashMap<String, Object>() {
				private static final long serialVersionUID = -88878780524L;
				{
					if ((int) map.get("UserLv") < 2) {
						put("msg", restGetLevelName(2) + " 이상의 등급만 이용할 수 있습니다.");
						put("code", "fail");
					}else if( ExtraAlmoney.equals("") || ExtraAlmoney.equals(null) || ExtraAlmoney.equals("0") ) {
						put("msg", "훈훈알을 입력해주세요.");
						put("code", "fail");
					}else if( Integer.parseInt(ExtraAlmoney) < 300 ) {
						put("msg", "최소 훈훈알은 300알입니다.");
						put("code", "fail");
					}else if( Integer.parseInt(ExtraAlmoney) > 10000 ) {
						put("msg", "최대 훈훈알은 10000알입니다.");
						put("code", "fail");
					}else if( Integer.parseInt(ExtraAlmoney) != n) {
						put("msg", "100알 단위로 입력해주세요.");
						put("code", "fail");
					}else {
						// 같은 유저가 n초(default:10) 안에 두번 이상 알을 주지 못하도록 설정 (2021-02-03 김주윤)
						final AlmoneyVO extraAlmoneyListUserTime = commonService.getExtraAlmoneyListUserTime(new HashMap<String, Object>() {
							private static final long serialVersionUID = -187980348345724L;
							{	put("contentsSeq", AnswerSeq);
								put("cType", "A");
								put("userSeq", userSeq);	}
						});
						Date time_me = new Date();
						cal.setTime(time_me);
						cal.add(Calendar.SECOND, -30);
						time_me = cal.getTime();
	
						if(extraAlmoneyListUserTime != null) {
							final Date userTime = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss").parse(extraAlmoneyListUserTime.getConDate());
							if(userTime.getTime() > time_me.getTime()) {
								put("msg", "이미 훈훈알을 증정하셨습니다. \n다시 증정하시려면 "+(userTime.getTime() - time_me.getTime())/1000 + "초 뒤에 시도해주세요.");
								put("code", "fail");
							}else {	put("code", "success");	}
						}else {	put("code", "success");	}
					}
				}
			};
			
			return rParam;
			
		}catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	@Override
	public Map<String, Object> getRestAnswerConvertSumExtraAlmoneyInfo(Map<String, Object> param) {

		final DecimalFormat nf = new DecimalFormat("###,###");
		final String contentsSeq =(String) param.get("contentsSeq");
		final String userSeq =(String) param.get("userSeq");
		final int typeMe = 4;
		final int extraAlmoney = (int) param.get("extraAlmoney");
		final int minusExtraAlmoney = 0 - extraAlmoney;
		final int typeYou = 5;
		final String qusAns ="ANSWER";
		final String cType ="A";
		final String extraAlmoneyFmt = nf.format( extraAlmoney );
		final int maxExtraAlmoney = (int) param.get("MaxExtraAlmoney");
		final String tbl ="T_REPLY_ANSWER";
		final String qusAnsTbl ="T_ANSWER";
		final String qusAnsCol ="ANSWERSeq";
		
		final HashMap<String, Object> map = new HashMap<String, Object>() {
			private static final long serialVersionUID = -11111111124L;
			{	put("contentsSeq", 			contentsSeq);
				put("userSeq", 				userSeq);
				put("typeMe", 				typeMe);
				put("minusExtraAlmoney", 	minusExtraAlmoney);
				put("extraAlmoney", 		extraAlmoney);
				put("typeYou", 				typeYou);
				put("qusAns", 				qusAns);
				put("cType", 				cType);
				put("extraAlmoneyFmt", 		extraAlmoneyFmt);
				put("maxExtraAlmoney", 		maxExtraAlmoney);
				put("tbl", 					tbl);
				put("qusAnsTbl", 			qusAnsTbl);
				put("qusAnsCol", 			qusAnsCol);	}
		};
		
		return restDAO.getRestConvertSumExtraAlmoneyInfo(map);
	}

	@Override
	public String getRestCookieUserNick(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CookieBox cookieBox = new CookieBox(request);
		Cookie cookies1 = cookieBox.getCookie("SESS");
		final JSONObject global = cookies1 != null? CommonUtil.getGlobal(request, response) : null;
		final String userNick = global != null? (String) global.get("UserNickName") : "";
		
		return userNick;
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
						put("game", getRestGameRouletteGiveTicketCountUpService(gameMap));
						put("replys", flag=="Q" ? restGetQuestionReplys(contentSeq) : restGetAnswerReplys(contentSeq) ); }
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
	public Map<String, Object> restQuestionReplyPut(String question, HttpServletRequest request, HttpServletResponse response, String text) {
		try {
			final int userSeq = getRestCookieUser(request, response);
			final int userLv = getRestCookieUserLv(request, response);
			
			final Map<String, Object> map = new HashMap<String, Object>() {
				private static final long serialVersionUID = -86348724L;
				{
					put("Flag","Q");
					put("contentSeq", question);
					put("text",text);
					put("userSeq",userSeq);
					put("userLv",userLv);
				}
			};
			
			return putRestReplyQA(map);
		
		}catch (Exception e) {
			final Map<String, Object> map = new HashMap<String, Object>() {
				private static final long serialVersionUID = -843122412L;
				{
					put("message",e);
				}
			};
			return map;
		}
	}

	@Override
	public Map<String, Object> restAnswerReplyPut(String answer, HttpServletRequest request,
			HttpServletResponse response, String text) {
		try {
			final int userSeq = getRestCookieUser(request, response);
			final int userLv = getRestCookieUserLv(request, response);
			
			final Map<String, Object> map = new HashMap<String, Object>() {
				private static final long serialVersionUID = -86348724L;
				{
					put("Flag","A");
					put("contentSeq", answer);
					put("text",text);
					put("userSeq",userSeq);
					put("userLv",userLv);
				}
			};
			
			return putRestReplyQA(map);
		
		}catch (Exception e) {
			final Map<String, Object> map = new HashMap<String, Object>() {
				private static final long serialVersionUID = -843122412L;
				{
					put("message",e);
				}
			};
			return map;
		}
	}

	
	@Override
	public Map<String, Object> putAnswerEstimate(Map<String, Object> param) {
		try {
			final String answerSeq = (String) param.get("answer");
			final String userSeq = Integer.toString(((int) param.get("userSeq")));
			final String estimateSt = Integer.toString(((int) param.get("estimateSt")));
			final String gubun = "A";
			
			if(userSeq=="0") return new HashMap<String, Object>() {
				private static final long serialVersionUID = -35268684543L;
				{
					put("code","notlogin");
				}
			};
			
			if(answerSeq == "" || answerSeq == "0" || answerSeq == null) return new HashMap<String, Object>() {
				private static final long serialVersionUID = -35268684543L;
				{
					put("code","not answerSeq");
				}
			};
			
			
			// 평가 실행
			
			final HashMap<String, Object> estimate = answerService.setAnswerEstimate(new HashMap<String, Object>() {
				private static final long serialVersionUID = -98962784543L;
				{
					put("gubun", gubun);
					put("ans_seq", Integer.parseInt(answerSeq));
					put("mem_seq", Integer.parseInt(userSeq));
					put("point_count_no", Integer.parseInt(estimateSt));
					put("date_reg", "");
				}
			});
			
			final String returnCode = String.valueOf( estimate.get("ReturnCode") );
			final String PointCount = String.valueOf( estimate.get("PointCount") );
			final String returnMSG = String.valueOf( estimate.get("ReturnMsg") );
			
			
			final HashMap<String, Object> result = new HashMap<String, Object>() {
				private static final long serialVersionUID = -935829582393L;
				{
					if(returnCode.equals("0")) {
						final Map<String, Object> gameMap = new HashMap<String, Object>() {
							private static final long serialVersionUID = -395723697287L;
							{	put("userSeq", Integer.parseInt(userSeq));
								put("mode","esti");
								put("XCount", "estiCount");
								put("flag","A");
								put("targetSeq", Integer.parseInt(answerSeq));	}
						};
						put("game", getRestGameRouletteGiveTicketCountUpService(gameMap));
					}else {
						put("game","no");
					}
					put("returnCode",returnCode);
					put("PointCount",PointCount);
					put("returnMSG",returnMSG);
					
				}
			};
			
			return result;
		}catch(Exception e) {
			return new HashMap<String, Object>(){
				private static final long serialVersionUID = -98962784543L;
				{
					final String answerSeq = (String) param.get("answer");
					final String userSeq = Integer.toString(((int) param.get("userSeq")));
					final String estimateSt = Integer.toString(((int) param.get("estimateSt")));
					final String gubun = "A";
					
					put("code","inerror");
					put("msg", e);
					put("gubun", gubun);
					put("ans_seq", Integer.parseInt(answerSeq));
					put("mem_seq", Integer.parseInt(userSeq));
					put("point_count_no", Integer.parseInt(estimateSt));
					put("date_reg", "");
				}
			};
		}
	}

	@Override
	public Map<String, Object> restPutAnswerEstimate(String answer, HttpServletRequest request, HttpServletResponse response, int estimateSt) {
		try {
			final int userSeq = getRestCookieUser(request, response);
			
			return putAnswerEstimate(
				new HashMap<String, Object>() {
					private static final long serialVersionUID = -84354373573547L;
					{
						//string
						put("answer", answer);
						//int
						put("userSeq",userSeq);
						put("estimateSt", estimateSt);
					}
			});
			
			
		}catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
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

	@Override
	public Map<String, Object> restPutQuestionVote(String question, HttpServletRequest request, HttpServletResponse response, String estiSeq)
	{
		try {
			final int userSeq = getRestCookieUser(request, response);
			
			return putAnswerQuestionVote(new HashMap<String, Object>() {
				private static final long serialVersionUID = -84354373573547L;
				{
					put("userSeq", userSeq);		// int
					put("contentType", "Q");		// String
					put("contentSeq", question);	// String
					put("estiSeq", estiSeq);		// String
				}
			});
			
		} catch (Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	@Override
	public Map<String, Object> restPutAnswerVote(String answer, HttpServletRequest request, HttpServletResponse response, String estiSeq)
	{
		try {
			final int userSeq = getRestCookieUser(request, response);
			
			return putAnswerQuestionVote(new HashMap<String, Object>() {
				private static final long serialVersionUID = -84354373573547L;
				{
					put("userSeq", userSeq);		// int
					put("contentType", "A");		// String
					put("contentSeq", answer);	// String
					put("estiSeq", estiSeq);		// String
				}
			});
			
		} catch (Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	@Override
	public List<Map<String, Object>> restQeustionsAnswerList(String questions, HttpServletRequest request) {
		// 각 질문과 답변을 json으로 전달합니다. ( 여기서 각 질문,답변에는 댓글리스트를 각각 포함합니다. )
		try {
			List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> answerArr = new ArrayList<Map<String,Object>>();
			
			final Map<String, Object> ListParam =new HashMap<String, Object>() {
				private static final long serialVersionUID = -5129628467395047900L;
				{	put("QuestionSeq", Integer.parseInt(questions) );	}
			};
	
			//          비지니스 서비스 처리
			final Map<String, Object> question = getOneRestQuestion(ListParam); // 질문 리스트
			final List<Map<String, Object>> questionReplys = getRestQuestionReplys(ListParam);  // 질문의 댓글
			Map<String, Object> questionToJson = new HashMap<String, Object>();
			questionToJson.putAll(questionToJson(question));// 질문 json 형식 변환
			
			final List<Map<String, Object>> answers = getRestAnswerList(ListParam);     // 답글 리스트
			
			//          질문의 댓글 저장
			questionToJson.put("replys", questionReplys);
			questionToJson.put("pageSeq",questions);
			
			// 각 답변의 내용과 댓글리스트 처리
			answers.forEach(answer -> answerArr.add( AnswerToJson(answer)) );
			
			//          result 저장 처리
			result.add(questionToJson);                                 // json에 질문 추가 (1개)
			answerArr.forEach( answer -> result.add(answer) );          // json에 답변 추가 (n개)
			
			return result;
		}catch(Exception e) {
			List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
			result.add(ImmutableMap.of("code", "error", "error", e ));

			return result;
		}
	}

	@Override
	public Map<String, Object> restQuestionVote(String question) {
		final Map<String, Object> map = ImmutableMap.of("contentSeq",question, "contentType", "Q");
		return getRestVoteSum(map);
	}

	@Override
	public Map<String, Object> restAnswerVote(String answer) {
		final Map<String, Object> map = ImmutableMap.of("contentSeq",answer, "contentType", "A");
		return getRestVoteSum(map);
	}

	@Override
	public Map<String, Object> restZzimAdd(String question, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = getRestCookieUser(request, response);
			
			final HashMap<String, Object> param = new HashMap<String, Object>() {
				private static final long serialVersionUID = -8413519823124L;
				{	put("userSeq", Integer.toString(userSeq));
					put("questionSeq", question);	}
			};
	
			final String messageDuple = "이미 찜에 등록되어 있습니다.";
			final String messageChoice = userSeq > 0 ? "찜에 등록되었습니다." : "로그인 후 이용해주시기 바랍니다.";
			
			final int zzimCount = !(userSeq > 0) ? -1 : getRestZzimCountUp(param);
			final String message = zzimCount > 0 ? messageDuple : messageChoice;
			
			return ImmutableMap.of( "message", message);
		
		}catch (Exception e) {
			return ImmutableMap.of( "message", e);
		}
	}

	@Override
	public Map<String, Object> restGetUser(HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = getRestCookieUser(request, response);
			final String userNick = getRestCookieUserNick(request, response);
			final int userLv = getRestCookieUserLv(request, response);
			
			return ImmutableMap.of("seq",userSeq, "nick",userNick, "lv",userLv);
			
		}catch (Exception e) {
			return ImmutableMap.of("message",e);
		}
	}

	@Override
	public List<Map<String, Object>> restGetQuestionReplys(String question) {
		final Map<String, Object> ListParam = ImmutableMap.of("QuestionSeq", Integer.parseInt(question));
		
		final List<Map<String, Object>> questionReplys = getRestQuestionReplys(ListParam);  // 질문의 댓글
		return questionReplys;
	}

	@Override
	public List<Map<String, Object>> restGetAnswerReplys(String answer) {
		final Map<String, Object> AnswerSeq = ImmutableMap.of("AnswerSeq", Integer.parseInt(answer));
		
		final List<Map<String, Object>> replys = new ArrayList<Map<String,Object>>() {
			private static final long serialVersionUID = -512962896741234L;
			{
				restDAO.getRestAnswerReplys(AnswerSeq).forEach(list -> {
					final Map<String, Object> reply = new HashMap<String, Object>() {
						private static final long serialVersionUID = -512284626346234L;
						{
							final Map<String, Object> ReplyMemberSeq = ImmutableMap.of("Seq",list.get("MemberSeq"));
							
							Map<String, Object> getProfile = restDAO.getRestUserHead(ReplyMemberSeq);
		
							final Map<String, Object> profile = ImmutableMap.<String, Object> builder()
								.put( "seqId", list.get("MemberSeq") )
								.put( "img", getProfile.get("profile") == null ? "" : getProfile.get("profile") )
								.put( "locale", getProfile.get("locale") )
								.put( "nick", getProfile.get("nick") ).build();
							
							
							put("id", list.get("id"));
							put("profile", profile);
							put("content", list.get("Reply"));
							put("time", "time" );
							put("date", list.get("DateReg") );
							put("nick1",list.get("nick1"));
							put("nick2",list.get("nick2"));
							put("almoney",list.get("almoney"));
							put("seq", list.get("Seq"));
						}
					};
					add(reply);
				});
			}
		};
		return replys;
	}

	@Override
	public Map<String, Object> restGetAnswerEstimate(String answer) {
		try {
			return restDAO.getRestAnswerEstimate(ImmutableMap.of( "AnswerSeq", Integer.parseInt(answer) ));
			
		}catch (Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}
	
	
	
	
	
}

