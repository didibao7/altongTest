package com.altong.web.rest.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altong.web.dao.RestDAO;
import com.altong.web.logic.ad.AdVO;
import com.altong.web.logic.almoney.AlmoneyVO;
import com.altong.web.logic.answer.AnswerVO;
import com.altong.web.logic.reply.ReplyVO;
import com.altong.web.logic.util.CommonUtil;
import com.altong.web.rest.service.RestAnswerService;
import com.altong.web.rest.service.RestCommonService;
import com.altong.web.rest.service.RestQuestionService;
import com.altong.web.rest.service.RestTransferService;
import com.altong.web.service.answer.AnswerService;
import com.altong.web.service.common.CommonService;
import com.altong.web.service.question.QuestionService;
import com.altong.web.service.reply.ReplyService;
import com.google.common.collect.ImmutableMap;

@Service
public class RestAnswerServiceImpl implements RestAnswerService {

	@Autowired
	QuestionService questionService;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	AnswerService answerService;
	
	@Autowired
	ReplyService replyService;
	
	@Autowired
	RestQuestionService restQuestionService;
	
	@Autowired
	RestCommonService restCommonService;
	
	@Autowired
	RestTransferService restTransferService;
	
	@Autowired 
	RestDAO restDAO;
	
	@Override
	public Map<String, Object> restAnswerVote(String answer) {
		final Map<String, Object> map = ImmutableMap.of("contentSeq",answer, "contentType", "A");
		return restDAO.getRestVoteSum(map);
	}

	@Override
	public Map<String, Object> getRestAnswerExtraAlmoney(int seq) {
		return restDAO.getRestAnswerExtraAlmoney( ImmutableMap.of( "AnswerSeq", seq ) );
	}

	@Override
	public Map<String, Object> restAnswerPutAlmoney( int extraAlmoney, String answer,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restCommonService.getRestCookieUser(request, response);
			final int userLv = restCommonService.getRestCookieUserLv(request, response);
			
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
			
			final Map<String,Object> restAnswerExtraAlmoney = restCommonService.restExtraAlmoney(request, response);
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

								put("game", restCommonService.getRestGameRouletteGiveTicketCountUpService(gameMap));
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
	public Map<String, Object> restAnswerReplyPut(String answer, HttpServletRequest request,
			HttpServletResponse response, String text) {
		try {
			final int userSeq = restCommonService.getRestCookieUser(request, response);
			final int userLv = restCommonService.getRestCookieUserLv(request, response);
			
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
			
			return restCommonService.putRestReplyQA(map);
		
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

	@Override
	public Map<String, Object> restPutAnswerEstimate(String answer, HttpServletRequest request, HttpServletResponse response, int estimateSt) {
		try {
			final int userSeq = restCommonService.getRestCookieUser(request, response);
			
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
	public Map<String, Object> restPutAnswerVote(String answer, HttpServletRequest request, HttpServletResponse response, String estiSeq)
	{
		try {
			final int userSeq = restCommonService.getRestCookieUser(request, response);
			
			return restCommonService.putAnswerQuestionVote(new HashMap<String, Object>() {
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
	public Map<String, Object> restAnswerPutChoice(Integer answer, Integer memberSeq, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restCommonService.getRestCookieUser(request, response);

			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("ans_seq", answer);
			param.put("answerMemberSeq", memberSeq);

			HashMap<String, Object> choice = answerService.setAnswerChoice(param);

			final String returnCode = String.valueOf(choice.get("RETURN"));

			switch(returnCode) {
			case "0" :
				return ImmutableMap.of( "msg", "success" );
			default :
				return ImmutableMap.of( "msg", "fail" );
			}
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	@Override
	public Map<String, Object> restAnswerPutReplyDel(Integer reply, String flag, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restCommonService.getRestCookieUser(request, response);

			if(userSeq > 0) {

				//본인 댓글인지 체크
				HashMap<String, Object> sourceParam = new HashMap<String, Object>();
				sourceParam.put("flag", flag);
				sourceParam.put("targetSeq", reply);
				ReplyVO replyVo = replyService.getReplyInfo(sourceParam);
				//System.out.println("replyVo memberSeq : " + replyVo.getMemberSeq());
				//System.out.println("userSeq : " + userSeq);
				if(replyVo.getMemberSeq() == userSeq) {
					HashMap<String, Object> replyParam = new HashMap<String, Object>();
					replyParam.put("flag", flag);
					replyParam.put("replySeq", reply);

					replyService.deleteReply(replyParam);

					return ImmutableMap.of( "msg", "success" );
				}
				else {
					return ImmutableMap.of( "msg", "fail" );
				}
			}
			else {
				return ImmutableMap.of( "msg", "fail" );
			}
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	@Override
	public Map<String, Object> restAnswerGetEstimate(Integer seq, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restCommonService.getRestCookieUser(request, response);

			if(userSeq == 0) {
				return ImmutableMap.of( "code", "error", "returnCode", "not login");
			}
			else {

				HashMap<String, Object> eParam = new HashMap<String, Object>();
				eParam.put("gubun", "A");
				eParam.put("contentSeq", seq);
				eParam.put("userSeq", userSeq);

				int myEstimateNo = restDAO.getRestUserExtimate(eParam);
				System.out.println("myEstimateNo : " + myEstimateNo);
				final Map<String, Object> result = ImmutableMap.<String, Object> builder()
						.put( "code", "success" )
						.put( "myEstimateNo", myEstimateNo ).build();

				return result;
			}
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	@Override
	public Map<String, Object> restAnswerGetExtraUsers(Integer contentsSeq, String contentsType, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restCommonService.getRestCookieUser(request, response);

			if(userSeq == 0) {
				return ImmutableMap.of( "code", "error", "returnCode", "not login");
			}
			else {
				HashMap<String ,Object> param = new HashMap<String, Object>();
				param.put("contentsSeq", contentsSeq);
				param.put("cType", contentsType);

				//System.out.println("contentsSeq : " + param.get("contentsSeq"));
				//System.out.println("cType : " + param.get("cType"));

				List<AlmoneyVO> extraAlmoneyList = commonService.getExtraAlmoneyList(param);
				//System.out.println("extraAlmoneyList.size() : " + extraAlmoneyList.size());

				final Map<String, Object> result = ImmutableMap.<String, Object> builder()
						.put( "code", "success" )
						.put( "value", "array" )
						.put( "ExtraAlmoneyList", extraAlmoneyList ).build();

				return result;
			}
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	@Override
	public Map<String, Object> restAnswerPutBestSet(Integer question, String bestRank, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restCommonService.getRestCookieUser(request, response);
			final int userLv = restCommonService.getRestCookieUserLv(request, response);

			if(userSeq == 0) {
				return ImmutableMap.of( "code", "error", "error", "로그인 후에 다시 글을 등록하여 주십시오!" );
			}

			if(userLv != 99) {
				return ImmutableMap.of( "code", "error", "error", "관리자만 수정이 가능합니다!" );
			}

			if(bestRank == null) {
				bestRank = "0";
			}
			int nBestRank = Integer.parseInt(bestRank);

			if(nBestRank < 1) nBestRank = 1;

			String bestNumber = questionService.getBestNumberBySeq(question);

			if(bestNumber == null || bestNumber.equals("0")) {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("bestRank", nBestRank);
				param.put("questionSeq", question);

				questionService.setBestChoiceMulti(param);

				final Map<String, Object> result = ImmutableMap.<String, Object> builder()
						.put( "code", "success" )
						.build();

				return result;
			}
			else {
				final Map<String, Object> result = ImmutableMap.<String, Object> builder()
						.put( "code", "success" )
						.build();

				return result;
			}
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	@Override
	public Map<String, Object> restAnswerPutBestCancel(Integer question, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restCommonService.getRestCookieUser(request, response);
			final int userLv = restCommonService.getRestCookieUserLv(request, response);

			if(userSeq == 0) {
				return ImmutableMap.of( "code", "error", "error", "로그인 후에 다시 글을 등록하여 주십시오!" );
			}

			if(userLv != 99) {
				return ImmutableMap.of( "code", "error", "error", "관리자만 수정이 가능합니다!" );
			}

			questionService.setBestChoiceZero(question);
			
			final Map<String, Object> result = ImmutableMap.<String, Object> builder()
					.put( "code", "success" )
					.build();

			return result;
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	@Override
	public Map<String, Object> restAnswerGetAnsweredCheck(Integer question, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restCommonService.getRestCookieUser(request, response);

			if(userSeq == 0) {
				return ImmutableMap.of( "code", "error", "error", "로그인 후에 다시 글을 등록하여 주십시오!" );
			}
			else {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("seq", question);
				param.put("userSeq", userSeq);

				int answeredCheck = restDAO.getRestAnsweredCheck(param);

				final boolean check = answeredCheck > 0 ? false : true;

				final Map<String, Object> result = ImmutableMap.<String, Object> builder()
						.put( "check", check )
						.build();

				return result;
			}
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}

	@Override
	public Map<String, Object> restAnswerGetChoicedCheck(Integer answer, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restCommonService.getRestCookieUser(request, response);

			if(userSeq == 0) {
				return ImmutableMap.of( "code", "error", "error", "로그인 후에 다시 글을 등록하여 주십시오!" );
			}
			else {
				AnswerVO answerVo = restDAO.getRestAnswerMemberInfo(answer);
				int question = Integer.parseInt(answerVo.getQuestionSeq().toString());
				int questionMemberNo = restDAO.getRestQuestionMemberSeq(question);

				if(userSeq == questionMemberNo) {
					// 채택 가능
					// 이미 채택 한것인지 체크
					final boolean check = answerVo.getFlagChoice().equals("N") ? true : false;

					final Map<String, Object> result = ImmutableMap.<String, Object> builder()
							.put( "check", check )
							.build();

					return result;
				}
				else {
					// 채택 불가능
					return ImmutableMap.of( "code", "error", "error", "질문자만 채택 가능합니다." );
				}
			}
		}
		catch(Exception e) {
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
						put("msg", restCommonService.restGetLevelName(2) + " 이상의 등급만 이용할 수 있습니다.");
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
						put("game", restCommonService.getRestGameRouletteGiveTicketCountUpService(gameMap));
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
	public List<Map<String, Object>> getRestAnswerList(Map<String, Object> param){
		return restDAO.getRestAnswerList(param);
	}
	
	@Override
	public Map<String, Object> restAdsGet() {
		try {
			String format = "yyyy-MM-dd";
			Calendar today = Calendar.getInstance();
			SimpleDateFormat type = new SimpleDateFormat(format);
			String todayDate = type.format(today.getTime());

			final Map<String, Object> adPparam = ImmutableMap.<String, Object> builder()
					.put( "startDt", todayDate )
					.put( "endDt", todayDate)
					.put( "section1", "").build();
			AdVO answerAd = restDAO.getRestAnswerAd(adPparam);

			int adSeq = answerAd.getSeq();
			String adFile = answerAd.getAdFile();
			String adFileExt = answerAd.getAdFileExt();
			String adUrl = !answerAd.getUrl().equals("") ? answerAd.getUrl() : "";
			int flagAd = answerAd.getFlagAd();

			final Map<String, Object> adData = ImmutableMap.<String, Object> builder()
					.put( "adSeq", adSeq )
					.put( "adFile", adFile)
					.put( "adFileExt", adFileExt)
					.put( "adUrl", adUrl)
					.put( "flagAd", flagAd)
					.build();


			return adData;

		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}
	
	@Override
	public Map<String, Object> restAnswerPutAdsView(Integer answer, Integer ads, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restCommonService.getRestCookieUser(request, response);

			AnswerVO answerVo = restDAO.getRestAnswerMemberInfo(answer);
			int question = Integer.parseInt(answerVo.getQuestionSeq().toString());
			int questionMemberNo = restDAO.getRestQuestionMemberSeq(question);
			int answerMemberNo = answerVo.getMemberSeq();
			int retCode = 0;

			boolean viewFlag = true; // 광고를 열람한 상태

			if(userSeq > 0) {
				HashMap<String, Object> vParam = new HashMap<String, Object>();
				vParam.put("answerSeq", answer);
				vParam.put("userSeq", userSeq);
				int almoneyCnt = restDAO.getAnswerLogViewInfo(vParam);

				// 질문작성자, 답변작성자는 광고 열람 유무 제외

				String[] seqArr = {String.valueOf(questionMemberNo), String.valueOf(answerMemberNo)};
				if (almoneyCnt <= 0 && CommonUtil.useArraysBinarySearch(seqArr, String.valueOf(userSeq)) == false) {
					viewFlag = false; // 광고를 열람하지 않은 상태
				}

				if(viewFlag == false) {
					// 카운트 증가
					HashMap<String ,Object> aParam = new HashMap<String, Object>();
					aParam.put("aSeq", answer);
					aParam.put("userSeq", userSeq);
					aParam.put("adSeq", ads);

					HashMap<String, Object> processInfo = commonService.setAlmoneyProcess(aParam);

					if(processInfo != null) {
						retCode = Integer.parseInt(processInfo.get("result_code").toString());
						viewFlag = true;
					}
				}
			}

			HashMap<String ,Object> param2 = new HashMap<String, Object>();
			param2.put("answerSeq", answer);
			param2.put("ipAddr", request.getRemoteAddr());
			commonService.setIpLog(param2);

			final Map<String, Object> result = ImmutableMap.<String, Object> builder()
					.put( "code", "success" )
					.put( "viewFlag", viewFlag)
					.put( "retCode", retCode)
					.build();


			return result;
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}
	
	@Override
	public Map<String, Object> restAnswerGetTransfer(Integer answer, HttpServletRequest request, HttpServletResponse response, Locale locale) {
		try {
			final String contentType = "A";

			Map<String, Object> transResult = restTransferService.transProcess(answer, contentType, request, response, locale);

			return transResult;
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}
	
	@Override
	public Map<String, Object> restAnswerGetTransferReply(Integer rseq, HttpServletRequest request, HttpServletResponse response, Locale locale) {
		try {
			final String contentType = "S";

			Map<String, Object> transResult = restTransferService.transProcess(rseq, contentType, request, response, locale);

			return transResult;
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}
}
