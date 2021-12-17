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
import com.altong.web.dao.answer.AnswerDAO;
import com.altong.web.logic.almoney.AlmoneyVO;
import com.altong.web.rest.service.RestAnswerService;
import com.altong.web.rest.service.RestCommonService;
import com.altong.web.rest.service.RestQuestionService;
import com.altong.web.rest.service.RestTransferService;
import com.altong.web.service.common.CommonService;
import com.google.common.collect.ImmutableMap;

@Service
public class RestQuestionServiceImpl implements RestQuestionService {

	@Autowired
	RestCommonService restCommonService;

	@Autowired
	RestAnswerService restAnswerService;

	@Autowired
	RestTransferService restTransferService;
	
	@Autowired
	CommonService commonService;

	@Autowired
	RestDAO restDAO;

	@Autowired
	AnswerDAO answerDAO;

	@Override
	public List<Map<String, Object>> restQeustionsAnswerList(String questions, HttpServletRequest request) {
		// 각 질문과 답변을 json으로 전달합니다. ( 여기서 각 질문,답변에는 댓글리스트를 각각 포함합니다. )
		try {
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> answerArr = new ArrayList<Map<String, Object>>();

			final Map<String, Object> ListParam = new HashMap<String, Object>() {
				private static final long serialVersionUID = -5129628467395047900L;
				{
					put("QuestionSeq", Integer.parseInt(questions));
				}
			};

			// 비지니스 서비스 처리
			final Map<String, Object> question = getOneRestQuestion(ListParam); // 질문 리스트
			final List<Map<String, Object>> questionReplys = getRestQuestionReplys(ListParam); // 질문의 댓글
			Map<String, Object> questionToJson = new HashMap<String, Object>();
			questionToJson.putAll(restCommonService.questionToJson(question));// 질문 json 형식 변환

			final List<Map<String, Object>> answers = restAnswerService.getRestAnswerList(ListParam); // 답글 리스트

			// 질문의 댓글 저장
			questionToJson.put("replys", questionReplys);
			questionToJson.put("pageSeq", questions);

			// 각 답변의 내용과 댓글리스트 처리
			answers.forEach(answer -> answerArr.add(restCommonService.AnswerToJson(answer)));

			// result 저장 처리
			result.add(questionToJson); // json에 질문 추가 (1개)
			answerArr.forEach(answer -> result.add(answer)); // json에 답변 추가 (n개)

			return result;
		} catch (Exception e) {
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			result.add(ImmutableMap.of("code", "error", "error", e));

			return result;
		}
	}

	@Override
	public Map<String, Object> restQuestionVote(String question) {
		final Map<String, Object> map = ImmutableMap.of("contentSeq", question, "contentType", "Q");
		return restCommonService.getRestVoteSum(map);
	}

	@Override
	public Map<String, Object> getRestQuestionExtraAlmoney(int seq) {
		return restDAO.getRestQuestionExtraAlmoney(ImmutableMap.of("QuestionSeq", seq));
	}

	@Override
	public Map<String, Object> restZzimAdd(String question, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restCommonService.getRestCookieUser(request, response);

			final HashMap<String, Object> param = new HashMap<String, Object>() {
				private static final long serialVersionUID = -8413519823124L;
				{
					put("userSeq", Integer.toString(userSeq));
					put("questionSeq", question);
				}
			};

			final String messageDuple = "이미 찜에 등록되어 있습니다.";
			final String messageChoice = userSeq > 0 ? "찜에 등록되었습니다." : "로그인 후 이용해주시기 바랍니다.";

			final int zzimCount = !(userSeq > 0) ? -1 : restCommonService.getRestZzimCountUp(param);
			final String message = zzimCount > 0 ? messageDuple : messageChoice;

			return ImmutableMap.of("message", message);

		} catch (Exception e) {
			return ImmutableMap.of("message", e);
		}
	}

	@Override
	public Map<String, Object> restQuestionPutAlmoney(int extraAlmoney, String question, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			final int userSeq = restCommonService.getRestCookieUser(request, response);
			final int userLv = restCommonService.getRestCookieUserLv(request, response);

			final int MinExtraAlmoney = 300;
			final int MaxExtraAlmoney = 10000;
			Calendar cal = Calendar.getInstance();

			final Map<String, Object> exception = getRestQuestionRunExcept(new HashMap<String, Object>() {
				private static final long serialVersionUID = 3298762396823911L;
				{
					put("UserLv", userLv);
					put("ExtraAlmoney", Integer.toString(extraAlmoney));
					put("UserSeq", userSeq);
					put("QuestionSeq", Integer.parseInt(question));
				}
			}, cal);
			if (exception.get("code").equals("success")) {
			} else if (exception.get("code").equals("fail") || exception.get("code").equals("error")) {
				return exception;
			}

			final Map<String, Object> restQeustionExtraAlmoney = restCommonService.restExtraAlmoney(request, response);
			if (restQeustionExtraAlmoney.get("code") != "find")
				return restQeustionExtraAlmoney;
			final Map<String, Object> map = new HashMap<String, Object>() {
				private static final long serialVersionUID = -81612611778124L;
				{
					put("contentsSeq", question);
					put("userSeq", Integer.toString(userSeq));
					put("typeMe", 2);
					put("extraAlmoney", extraAlmoney);
					put("MinExtraAlmoney", MinExtraAlmoney);
					put("MaxExtraAlmoney", MaxExtraAlmoney);
				}
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
								{
									put("userSeq", userSeq);
									put("mode", "hun");
									put("XCount", "hunCount");
									put("flag", "Q");
									put("targetSeq", Integer.parseInt(question));
								}
							};
							put("game", restCommonService.getRestGameRouletteGiveTicketCountUpService(gameMap));
						} else {
							put("game", "no");
						}
						put("code", "good");
						break;
					case 2:
						put("code", "row");
						break;
					case 51:
						put("code", "noExist");
						break;
					case 52:
						put("code", "me");
						break;
					case 53:
						put("code", "tomuch");
						break;
					default:
						put("code", "error");
						break;
					}
				}
			};
			return resultMap;
		} catch (Exception e) {
			return ImmutableMap.of("code", "errorMap", "error", e);
		}
	}

	@Override
	public Map<String, Object> restQuestionReplyPut(String question, HttpServletRequest request,
			HttpServletResponse response, String text) {
		try {
			final int userSeq = restCommonService.getRestCookieUser(request, response);
			final int userLv = restCommonService.getRestCookieUserLv(request, response);

			final Map<String, Object> map = new HashMap<String, Object>() {
				private static final long serialVersionUID = -86348724L;
				{
					put("Flag", "Q");
					put("contentSeq", question);
					put("text", text);
					put("userSeq", userSeq);
					put("userLv", userLv);
				}
			};

			return restCommonService.putRestReplyQA(map);

		} catch (Exception e) {
			final Map<String, Object> map = new HashMap<String, Object>() {
				private static final long serialVersionUID = -843122412L;
				{
					put("message", e);
				}
			};
			return map;
		}
	}

	@Override
	public List<Map<String, Object>> restGetQuestionReplys(String question) {
		final Map<String, Object> ListParam = ImmutableMap.of("QuestionSeq", Integer.parseInt(question));

		final List<Map<String, Object>> questionReplys = getRestQuestionReplys(ListParam); // 질문의 댓글
		return questionReplys;
	}

	@Override
	public Map<String, Object> restPutQuestionVote(String question, HttpServletRequest request,
			HttpServletResponse response, String estiSeq) {
		try {
			final int userSeq = restCommonService.getRestCookieUser(request, response);

			return restCommonService.putAnswerQuestionVote(new HashMap<String, Object>() {
				private static final long serialVersionUID = -84354373573547L;
				{
					put("userSeq", userSeq); // int
					put("contentType", "Q"); // String
					put("contentSeq", question); // String
					put("estiSeq", estiSeq); // String
				}
			});

		} catch (Exception e) {
			return ImmutableMap.of("code", "error", "error", e);
		}
	}

	@Override
	public Map<String, Object> restQuestionPutMoveTop(Integer question, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			final int userSeq = restCommonService.getRestCookieUser(request, response);

			if (userSeq == 0)
				return ImmutableMap.of("code", "notlogin");

			// 이 기능의 사용 빈도가 많아지면 아래의 쿼리를 프로시저로 만들자.
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("userSeq", userSeq);
			param.put("questionSeq", question);

			HashMap<String, Object> r = answerDAO.setMoveQuestion(param);

			final int returnCode = r != null ? Integer.parseInt(r.get("ReturnCode").toString()) : 0;

			switch (returnCode) {
			case 1:
				return ImmutableMap.of("msg", true);
			case 2:
				return ImmutableMap.of("msg", "알머니 잔액이 부족합니다.");
			case 51:
				return ImmutableMap.of("msg", "대상 글이 존재하지 않습니다.");
			case 52:
				return ImmutableMap.of("msg", "더 이상 올릴 수 없습니다.");
			default:
				return ImmutableMap.of("msg", "예상치 못한 오류가 발생하였습니다.");
			}

		} catch (Exception e) {
			return ImmutableMap.of("code", "error", "error", e);
		}
	}

	@Override
	public Map<String, Object> getOneRestQuestion(Map<String, Object> param) {
		return restDAO.getOneRestQuestion(param);
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
						put("msg", restCommonService.restGetLevelName(2) + " 이상의 등급만 이용할 수 있습니다.");
						put("code", "fail");
					} else if (ExtraAlmoney.equals("") || ExtraAlmoney.equals(null) || ExtraAlmoney.equals("0")) {
						put("msg", "훈훈알을 입력해주세요.");
						put("code", "fail");
					} else if (Integer.parseInt(ExtraAlmoney) < 300) {
						put("msg", "최소 훈훈알은 300알입니다.");
						put("code", "fail");
					} else if (Integer.parseInt(ExtraAlmoney) > 10000) {
						put("msg", "최대 훈훈알은 10000알입니다.");
						put("code", "fail");
					} else if (Integer.parseInt(ExtraAlmoney) != n) {
						put("msg", "100알 단위로 입력해주세요.");
						put("code", "fail");
					} else {
						// 같은 유저가 n초(default:10) 안에 두번 이상 알을 주지 못하도록 설정 (2021-02-03 김주윤)
						final AlmoneyVO extraAlmoneyListUserTime = commonService
								.getExtraAlmoneyListUserTime(new HashMap<String, Object>() {
									private static final long serialVersionUID = -1879807870524L;
									{
										put("contentsSeq", QuestionSeq);
										put("cType", "Q");
										put("userSeq", userSeq);
									}
								});
						Date time_me = new Date();
						cal.setTime(time_me);
						cal.add(Calendar.SECOND, -30);
						time_me = cal.getTime();

						if (extraAlmoneyListUserTime != null) {
							final Date userTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.parse(extraAlmoneyListUserTime.getConDate());
							if (userTime.getTime() > time_me.getTime()) {
								put("msg", "이미 훈훈알을 증정하셨습니다. \n다시 증정하시려면 "
										+ (userTime.getTime() - time_me.getTime()) / 1000 + "초 뒤에 시도해주세요.");
								put("code", "fail");
							} else {
								put("code", "success");
							}
						} else {
							put("code", "success");
						}
					}
				}
			};

			return rParam;

		} catch (Exception e) {
			return ImmutableMap.of("code", "error", "error", e);
		}
	}

	@Override
	public Map<String, Object> getRestConvertSumExtraAlmoneyInfo(Map<String, Object> param) {

		final DecimalFormat nf = new DecimalFormat("###,###");
		final String contentsSeq = (String) param.get("contentsSeq");
		final String userSeq = (String) param.get("userSeq");
		final int typeMe = 2;
		final int extraAlmoney = (int) param.get("extraAlmoney");
		final int minusExtraAlmoney = 0 - extraAlmoney;
		final int typeYou = 3;
		final String qusAns = "QUESTION";
		final String cType = "Q";
		final String extraAlmoneyFmt = nf.format(extraAlmoney);
		final int maxExtraAlmoney = (int) param.get("MaxExtraAlmoney");
		final String tbl = "T_REPLY_QUESTION";
		final String qusAnsTbl = "T_QUESTION";
		final String qusAnsCol = "QUESTIONSeq";

		final HashMap<String, Object> map = new HashMap<String, Object>() {
			private static final long serialVersionUID = -11111111124L;
			{
				put("contentsSeq", contentsSeq);
				put("userSeq", userSeq);
				put("typeMe", typeMe);
				put("minusExtraAlmoney", minusExtraAlmoney);
				put("extraAlmoney", extraAlmoney);
				put("typeYou", typeYou);
				put("qusAns", qusAns);
				put("cType", cType);
				put("extraAlmoneyFmt", extraAlmoneyFmt);
				put("maxExtraAlmoney", maxExtraAlmoney);
				put("tbl", tbl);
				put("qusAnsTbl", qusAnsTbl);
				put("qusAnsCol", qusAnsCol);
			}
		};

		return restDAO.getRestConvertSumExtraAlmoneyInfo(map);
	}

	@Override
	public List<Map<String, Object>> getRestQuestionReplys(Map<String, Object> param) {
		final List<Map<String, Object>> replys = new ArrayList<Map<String, Object>>() {
			private static final long serialVersionUID = -5129628467395041234L;
			{
				restDAO.getRestQeustionReplys(param).forEach(list -> {
					final Map<String, Object> reply = new HashMap<String, Object>() {
						private static final long serialVersionUID = -5129628467359821234L;
						{
							Map<String, Object> getProfile = restDAO
									.getRestUserHead(ImmutableMap.of("Seq", list.get("MemberSeq")));

							put("id", list.get("id"));
							put("profile", ImmutableMap.<String, Object>builder().put("seqId", list.get("MemberSeq"))
									.put("img", getProfile.get("profile") == null ? "" : getProfile.get("profile"))
									.put("locale", getProfile.get("locale")).put("nick", getProfile.get("nick"))
									.build());
							put("content", list.get("Reply"));
							put("time", "time");
							put("date", list.get("DateReg"));
							put("nick1", list.get("nick1") == null ? "" : list.get("nick1"));
							put("nick2", list.get("nick2") == null ? "" : list.get("nick2"));
							put("almoney", list.get("almoney"));
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
	public Map<String, Object> questionVote(String act, int contentSeq, String contentType, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			final int userSeq = restCommonService.getRestCookieUser(request, response);

			if (userSeq > 0 && contentSeq > 0) {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("contentSeq", contentSeq);
				param.put("contentType", contentType);
				param.put("userSeq", userSeq);
				int voteCount = commonService.getQuestionVoteCount(param);

				HashMap<String, Object> vote = null;
				int good = 0;
				int bad = 0;
				if (voteCount > 0) {
					vote = commonService.getQuestionVote(param);
					good = Integer.parseInt(String.valueOf(vote.get("good")));
					bad = Integer.parseInt(String.valueOf(vote.get("bad")));
				}

				HashMap<String, Object> vParam = new HashMap<String, Object>();
				vParam.put("userSeq", userSeq);
				vParam.put("contentSeq", contentSeq);
				vParam.put("contentType", contentType);

				if (voteCount > 0) {
					if (act.equals("G")) {
						if (bad > 0) {
							vParam.put("bad", (bad - 1));
						} else {
							vParam.put("bad", bad);
						}
						if (good == 0) {
							vParam.put("good", (good + 1));
						} else {
							vParam.put("good", good);
						}
					} else {
						if (good > 0) {
							vParam.put("good", (good - 1));
						} else {
							vParam.put("good", good);
						}
						if (bad == 0) {
							vParam.put("bad", (bad + 1));
						} else {
							vParam.put("bad", bad);
						}
					}

					commonService.setQuestionVote(vParam);
				} else {
					if (act.equals("G")) {
						vParam.put("good", 1);
						vParam.put("bad", 0);
					} else {
						vParam.put("good", 0);
						vParam.put("bad", 1);
					}
					commonService.addQuestionVote(vParam);
				}

				HashMap<String, Object> voteItem = commonService.getQuestionVoteSum(vParam);

				final Map<String, Object> result = ImmutableMap.<String, Object>builder().put("code", "success")
						.put("qtGood", voteItem.get("good")).put("qtBad", voteItem.get("bad")).build();

				return result;

			} else {
				return ImmutableMap.of("code", "error", "error", "잘못된 접근입니다.");
			}
		} catch (Exception e) {
			return ImmutableMap.of("code", "error", "error", e);
		}
	}
	
	@Override
	public Map<String, Object> restQuestionGetExtraUsers(Integer contentsSeq, String contentsType, HttpServletRequest request, HttpServletResponse response) {
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
	public Map<String, Object> restQuestionGetTransfer(Integer question, HttpServletRequest request, HttpServletResponse response, Locale locale) {
		try {
			final String contentType = "Q";

			Map<String, Object> transResult = restTransferService.transProcess(question, contentType, request, response, locale);

			return transResult;
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}
	
	@Override
	public Map<String, Object> restQuestionGetTransferReply(Integer rseq, HttpServletRequest request, HttpServletResponse response, Locale locale) {
		try {
			final String contentType = "R";

			Map<String, Object> transResult = restTransferService.transProcess(rseq, contentType, request, response, locale);

			return transResult;
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}
}
