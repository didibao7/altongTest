package com.altong.web.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.altong.web.logic.quiz.QuizAnsVO;
import com.altong.web.logic.quiz.QuizGameQueVO;
import com.altong.web.logic.quiz.QuizGameVO;
import com.altong.web.logic.quiz.QuizLogVO;
import com.altong.web.logic.quiz.QuizQueVO;
import com.altong.web.logic.util.CommonUtil;
import com.altong.web.service.CookieLoginService;
import com.altong.web.service.common.CommonService;
import com.altong.web.service.quiz.QuizService;

@Controller
@RequestMapping("quiz/*")
public class QuizController {

	@Autowired
	QuizService quizService;

	@Autowired
	CommonService commonService;

	@Autowired
	CookieLocaleResolver localResolver;

	@Autowired
	MessageSource messageSource;

	@Autowired
	CookieLoginService cookieLoginService;

	@RequestMapping("quizList")
	public ModelAndView quizList(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		ModelAndView mav = new ModelAndView();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		if(userSeq > 0) {
			int gqid = request.getParameter("gqid") != null ? Integer.parseInt(request.getParameter("gqid")) : 0;
			int queid = request.getParameter("queid") != null ? Integer.parseInt(request.getParameter("queid")) : 0;

			if(queid > 0) {

				if(userLv != 99) {
					//gqid && queid ??? ???????????? QUIZ_REQ ??????
					//
				}
				else {
					//???????????? QUIZ_REQ ??? ????????? ????????? ??????
				}

				QuizQueVO quiz = quizService.getQuizQue(queid);

				List<QuizAnsVO> ansList = null;
				if(quiz != null) {
					ansList = quizService.getQuizAnsList(queid);
				}

				mav.addObject("gqid", gqid);
				mav.addObject("queid", queid);
				mav.addObject("quiz", quiz);
				mav.addObject("ansList", ansList);
			}
		}
		else {
			String msg1 = messageSource.getMessage("hello", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
			CommonUtil.jspAlert(response, "?????? ????????? ????????????.", "/", "top"); // msg1 ??????
		}

		return mav;
	}

	@RequestMapping("game")
	public ModelAndView game(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		ModelAndView mav = new ModelAndView();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		String format = "yyyy-MM-dd hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(??????/?????? ??????)
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String createDt = type.format(today.getTime());

		if(userSeq > 0) {
			int uid = request.getParameter("uid") != null ? Integer.parseInt(request.getParameter("uid")) : 0;
			int ord = request.getParameter("ord") != null ? Integer.parseInt(request.getParameter("ord")) : 1;
			String view =  request.getParameter("view") != null ? request.getParameter("view") : "T";

			if(uid > 0) {
				QuizGameVO quiz = quizService.getQuizGame(uid);

				int quizGameQueCnt = quizService.getQuizGameQueCount(uid);

				HashMap<String, Object> rParam = new HashMap<String, Object>();
				rParam.put("gqid", uid);
				int reqLogCount = quizService.getQuizReqLogCount(rParam);

				mav.addObject("uid", uid);
				mav.addObject("current", ord);
				mav.addObject("max", quizGameQueCnt);
				mav.addObject("reqLogCount", reqLogCount);
				mav.addObject("userLv", userLv);

				if(view.equals("V")) {
					HashMap<String, Object> cParam = new HashMap<String, Object>();
					cParam.put("gqid", uid);
					cParam.put("userSeq", userSeq);
					cParam.put("updateDt", createDt);

					cParam.put("success", "V");

					quizService.setQuizLog(cParam);
				}

				if(ord <= quizGameQueCnt) {
					// getQuizGameQueList(uid, ord) quid(QuizGame uid), queid(QuestQue uid), quest
					HashMap<String, Object> param = new HashMap<String, Object>();
					param.put("quid", uid);
					param.put("ord", ord);

					QuizGameQueVO gameQue = quizService.getQuizGameQue(param);

					// uid, quest, rtime1, rtime2, stime1, stime2, correct(??????), hint, comment
					QuizQueVO quest = quizService.getQuizQue(gameQue.getQueid());

					// uid, ano, answer
					List<QuizAnsVO> ansList = quizService.getQuizAnsList(quest.getUid());

					//QUIZ_LOG
					HashMap<String, Object> qParam = new HashMap<String, Object>();
					qParam.put("gqid", uid);
					qParam.put("userSeq", userSeq);
					QuizLogVO quizLog = quizService.getQuizLog(qParam);

					mav.addObject("quiz", quiz);
					mav.addObject("gameQue", gameQue);
					mav.addObject("quest", quest);
					mav.addObject("ansList", ansList);
					mav.addObject("quizLog", quizLog);
					mav.addObject("next", (ord + 1));
				}
				else {

				}
			}
		}
		else {
			String msg1 = messageSource.getMessage("hello", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
			CommonUtil.jspAlert(response, "?????? ????????? ????????????.", "/", "parent.parent"); // msg1 ??????
		}

		return mav;
	}

	@RequestMapping("gameDemo")
	public ModelAndView gameDemo(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		ModelAndView mav = new ModelAndView();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		String format = "yyyy-MM-dd hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(??????/?????? ??????)
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String createDt = type.format(today.getTime());

		if(userSeq > 0) {
			int uid = request.getParameter("uid") != null ? Integer.parseInt(request.getParameter("uid")) : 0;
			int ord = request.getParameter("ord") != null ? Integer.parseInt(request.getParameter("ord")) : 1;
			String view =  request.getParameter("view") != null ? request.getParameter("view") : "T";

			if(uid > 0) {
				QuizGameVO quiz = quizService.getQuizGame(uid);

				int quizGameQueCnt = quizService.getQuizGameQueCount(uid);

				HashMap<String, Object> rParam = new HashMap<String, Object>();
				rParam.put("gqid", uid);
				int reqLogCount = quizService.getQuizReqLogCount(rParam);

				mav.addObject("uid", uid);
				mav.addObject("current", ord);
				mav.addObject("max", quizGameQueCnt);
				mav.addObject("reqLogCount", reqLogCount);
				mav.addObject("userLv", userLv);

				if(userLv == 99) {
					HashMap<String, Object> sParam = new HashMap<String, Object>();
					sParam.put("gqid", uid);
					sParam.put("userSeq", userSeq);

					int logCnt = quizService.getQuizLogCnt(sParam);

					if(logCnt == 0) {
						sParam.put("success", "S");
						sParam.put("attendAlmoney", quiz.getAttendAlmoney());
						sParam.put("createDt", createDt);
						sParam.put("updateDt", createDt);

						quizService.addQuizLog(sParam);
					}
				}

				if(view.equals("V")) {
					HashMap<String, Object> cParam = new HashMap<String, Object>();
					cParam.put("gqid", uid);
					cParam.put("userSeq", userSeq);
					cParam.put("updateDt", createDt);

					cParam.put("success", "V");

					quizService.setQuizLog(cParam);
				}

				if(ord <= quizGameQueCnt) {
					// getQuizGameQueList(uid, ord) quid(QuizGame uid), queid(QuestQue uid), quest
					HashMap<String, Object> param = new HashMap<String, Object>();
					param.put("quid", uid);
					param.put("ord", ord);

					QuizGameQueVO gameQue = quizService.getQuizGameQue(param);

					// uid, quest, rtime1, rtime2, stime1, stime2, correct(??????), hint, comment
					QuizQueVO quest = quizService.getQuizQue(gameQue.getQueid());

					// uid, ano, answer
					List<QuizAnsVO> ansList = quizService.getQuizAnsList(quest.getUid());

					//QUIZ_LOG
					HashMap<String, Object> qParam = new HashMap<String, Object>();
					qParam.put("gqid", uid);
					qParam.put("userSeq", userSeq);
					QuizLogVO quizLog = quizService.getQuizLog(qParam);

					mav.addObject("quiz", quiz);
					mav.addObject("gameQue", gameQue);
					mav.addObject("quest", quest);
					mav.addObject("ansList", ansList);
					mav.addObject("quizLog", quizLog);
					mav.addObject("next", (ord + 1));
				}
				else {

				}
			}
		}
		else {
			String msg1 = messageSource.getMessage("hello", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
			CommonUtil.jspAlert(response, "?????? ????????? ????????????.", "/", "parent.parent"); // msg1 ??????
		}

		return mav;
	}

	@RequestMapping(value="quizComplete", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String quizComplete(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		if(userLv > 0) {
			int uid = request.getParameter("uid") != null ? Integer.parseInt(request.getParameter("uid")) : 0;
			int ord = request.getParameter("current") != null ? Integer.parseInt(request.getParameter("current")) : 1;
			String queid = request.getParameter("queid") != null ? request.getParameter("queid") : "";
			int correct = request.getParameter("correct") != null ? Integer.parseInt(request.getParameter("correct")) : 0;

			String format = "yyyy-MM-dd hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(??????/?????? ??????)
			Calendar today = Calendar.getInstance();
			SimpleDateFormat type = new SimpleDateFormat(format);
			String createDt = type.format(today.getTime());

			HashMap<String, Object> qParam = new HashMap<String, Object>();
			qParam.put("gqid", uid);
			qParam.put("userSeq", userSeq);
			QuizLogVO quizLog = quizService.getQuizLog(qParam);

			if(uid > 0) {
				int max = quizService.getQuizGameQueCount(uid);

				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("quid", uid);
				param.put("ord", ord);

				QuizGameQueVO gameQue = quizService.getQuizGameQue(param);

				QuizQueVO quest = quizService.getQuizQue(gameQue.getQueid());

				//userSeq = #{userSeq} AND gqid = #{gqid} AND queid = #{queid}
				//QUIZ_REQ ??????	gqid=uid, userSeq, queid,
				HashMap<String, Object> reqParam = new HashMap<String, Object>();
				reqParam.put("userSeq", userSeq);
				reqParam.put("gqid", uid);
				reqParam.put("queid", queid);
				int reqCnt = quizService.getQuizReqOne(reqParam);

				String success = "N";

				if(ord == max) {
					HashMap<String, Object> cParam = new HashMap<String, Object>();
					cParam.put("gqid", uid);
					cParam.put("userSeq", userSeq);
					cParam.put("updateDt", createDt);

					if(correct == quest.getCorrect()) {
						success = "Y";
						cParam.put("success", "Y");
					}
					else {
						cParam.put("success", "N");
					}

					quizService.setQuizLog(cParam);


					cParam.put("queid", queid);
					cParam.put("createDt", createDt);
					if(reqCnt == 0) {
						quizService.addQuizReq(cParam);
					}

					List<QuizLogVO> succssList = quizService.getQuizLogAll(uid);

					r_res.put("succssList", succssList);
					result = CommonUtil.libJsonExit(success, r_res);
				}
				else {
					HashMap<String, Object> cParam = new HashMap<String, Object>();
					cParam.put("gqid", uid);
					cParam.put("userSeq", userSeq);
					cParam.put("updateDt", createDt);

					if(correct == quest.getCorrect()) {
						success = "Y";
						cParam.put("success", "Y");
					}
					else {
						String strSuccess = "";
						if(quizLog != null) { strSuccess = quizLog.getSuccess(); }
						if(strSuccess.equals("V")) {
							cParam.put("success", "V");
						}
						else {
							cParam.put("success", "N");
						}
						quizService.setQuizLog(cParam);
					}

					cParam.put("queid", queid);
					cParam.put("createDt", createDt);

					if(reqCnt == 0) {
						quizService.addQuizReq(cParam);
					}

					List<QuizLogVO> succssList = null;

					r_res.put("succssList", succssList);
					result = CommonUtil.libJsonExit(success, r_res);
				}
			}
		}

		return result;
	}

	@RequestMapping(value="quizDemoComplete", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String quizDemoComplete(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		if(userLv > 0) {
			int uid = request.getParameter("uid") != null ? Integer.parseInt(request.getParameter("uid")) : 0;
			int ord = request.getParameter("current") != null ? Integer.parseInt(request.getParameter("current")) : 1;
			String queid = request.getParameter("queid") != null ? request.getParameter("queid") : "";
			int correct = request.getParameter("correct") != null ? Integer.parseInt(request.getParameter("correct")) : 0;

			String format = "yyyy-MM-dd hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(??????/?????? ??????)
			Calendar today = Calendar.getInstance();
			SimpleDateFormat type = new SimpleDateFormat(format);
			String createDt = type.format(today.getTime());

			HashMap<String, Object> qParam = new HashMap<String, Object>();
			qParam.put("gqid", uid);
			qParam.put("userSeq", userSeq);
			QuizLogVO quizLog = quizService.getQuizLog(qParam);

			if(uid > 0) {
				int max = quizService.getQuizGameQueCount(uid);

				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("quid", uid);
				param.put("ord", ord);

				QuizGameQueVO gameQue = quizService.getQuizGameQue(param);

				QuizQueVO quest = quizService.getQuizQue(gameQue.getQueid());

				//userSeq = #{userSeq} AND gqid = #{gqid} AND queid = #{queid}
				//QUIZ_REQ ??????	gqid=uid, userSeq, queid,
				HashMap<String, Object> reqParam = new HashMap<String, Object>();
				reqParam.put("userSeq", userSeq);
				reqParam.put("gqid", uid);
				reqParam.put("queid", queid);
				int reqCnt = quizService.getQuizReqOne(reqParam);

				String success = "N";

				if(ord == max) {
					HashMap<String, Object> cParam = new HashMap<String, Object>();
					cParam.put("gqid", uid);
					cParam.put("userSeq", userSeq);
					cParam.put("updateDt", createDt);

					if(correct == quest.getCorrect()) {
						success = "Y";
						cParam.put("success", "Y");
					}
					else {
						cParam.put("success", "N");
					}

					quizService.setQuizLog(cParam);


					cParam.put("queid", queid);
					cParam.put("createDt", createDt);
					if(reqCnt == 0) {
						quizService.addQuizReq(cParam);
					}

					List<QuizLogVO> succssList = quizService.getQuizLogAll(uid);

					r_res.put("succssList", succssList);
					result = CommonUtil.libJsonExit(success, r_res);

					//????????? ????????? ?????? ????????? ??????
					if(userLv == 99) {
						HashMap<String, Object> dParam = new HashMap<String, Object>();
						dParam.put("userSeq", userSeq);
						dParam.put("gqid", uid);

						//QUIZ_LOG ??????
						quizService.deleteQuizLog(dParam);
						//QUIZ_REQ ??????
						quizService.deleteQuizReq(dParam);
					}
				}
				else {
					HashMap<String, Object> cParam = new HashMap<String, Object>();
					cParam.put("gqid", uid);
					cParam.put("userSeq", userSeq);
					cParam.put("updateDt", createDt);

					if(correct == quest.getCorrect()) {
						success = "Y";
						cParam.put("success", "Y");
					}
					else {
						String strSuccess = "";
						if(quizLog != null) { strSuccess = quizLog.getSuccess(); }
						if(strSuccess.equals("V")) {
							cParam.put("success", "V");
						}
						else {
							cParam.put("success", "N");
						}
						quizService.setQuizLog(cParam);
					}

					cParam.put("queid", queid);
					cParam.put("createDt", createDt);

					if(reqCnt == 0) {
						quizService.addQuizReq(cParam);
					}

					List<QuizLogVO> succssList = null;

					r_res.put("succssList", succssList);
					result = CommonUtil.libJsonExit(success, r_res);
				}
			}
		}

		return result;
	}

	@RequestMapping(value="quizStop", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String quizStop(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		if(userLv > 0) {
			int uid = request.getParameter("uid") != null ? Integer.parseInt(request.getParameter("uid")) : 0;
			int ord = request.getParameter("current") != null ? Integer.parseInt(request.getParameter("current")) : 0;
			int max = request.getParameter("max") != null ? Integer.parseInt(request.getParameter("max")) : 0;

			String format = "yyyy-MM-dd hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(??????/?????? ??????)
			Calendar today = Calendar.getInstance();
			SimpleDateFormat type = new SimpleDateFormat(format);
			String createDt = type.format(today.getTime());

			if(uid > 0) {
				HashMap<String, Object> cParam = new HashMap<String, Object>();
				cParam.put("gqid", uid);
				cParam.put("userSeq", userSeq);
				cParam.put("updateDt", createDt);
				cParam.put("success", "N");

				if(ord < max) {
					quizService.setQuizLog(cParam);
				}

				result = CommonUtil.libJsonExit("Y", r_res);
			}
		}
		result = CommonUtil.libJsonExit("N", r_res);

		return result;
	}

	@RequestMapping(value="quizDemoStop", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String quizDemoStop(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		if(userLv > 0) {
			int uid = request.getParameter("uid") != null ? Integer.parseInt(request.getParameter("uid")) : 0;
			int ord = request.getParameter("current") != null ? Integer.parseInt(request.getParameter("current")) : 0;
			int max = request.getParameter("max") != null ? Integer.parseInt(request.getParameter("max")) : 0;

			String format = "yyyy-MM-dd hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(??????/?????? ??????)
			Calendar today = Calendar.getInstance();
			SimpleDateFormat type = new SimpleDateFormat(format);
			String createDt = type.format(today.getTime());

			if(uid > 0) {
				HashMap<String, Object> cParam = new HashMap<String, Object>();
				cParam.put("gqid", uid);
				cParam.put("userSeq", userSeq);
				cParam.put("updateDt", createDt);
				cParam.put("success", "N");

				if(ord < max) {
					quizService.setQuizLog(cParam);
				}

				result = CommonUtil.libJsonExit("Y", r_res);

				if(userLv == 99) {
					HashMap<String, Object> dParam = new HashMap<String, Object>();
					dParam.put("userSeq", userSeq);
					dParam.put("gqid", uid);

					//QUIZ_LOG ??????
					quizService.deleteQuizLog(dParam);
					//QUIZ_REQ ??????
					quizService.deleteQuizReq(dParam);
				}
			}
		}
		result = CommonUtil.libJsonExit("N", r_res);

		return result;
	}

	@RequestMapping(value="quizEnd", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String quizEnd(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		if(userLv > 0) {
			int uid = request.getParameter("uid") != null ? Integer.parseInt(request.getParameter("uid")) : 0;

			if(uid > 0) {
				result = CommonUtil.libJsonExit("Y", r_res);
			}
		}
		result = CommonUtil.libJsonExit("N", r_res);

		return result;
	}

	@RequestMapping(value="quizDemoEnd", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String quizDemoEnd(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		if(userLv > 0) {
			int uid = request.getParameter("uid") != null ? Integer.parseInt(request.getParameter("uid")) : 0;

			if(uid > 0) {
				result = CommonUtil.libJsonExit("Y", r_res);

				if(userLv == 99) {
					HashMap<String, Object> dParam = new HashMap<String, Object>();
					dParam.put("userSeq", userSeq);
					dParam.put("gqid", uid);

					//QUIZ_LOG ??????
					quizService.deleteQuizLog(dParam);
					//QUIZ_REQ ??????
					quizService.deleteQuizReq(dParam);
				}
			}
		}
		result = CommonUtil.libJsonExit("N", r_res);

		return result;
	}

	@RequestMapping("demoEnd")
	public ModelAndView demoEnd(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		ModelAndView mav = new ModelAndView();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		String format = "yyyy-MM-dd hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(??????/?????? ??????)
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String createDt = type.format(today.getTime());

		if(userSeq > 0) {
			int uid = request.getParameter("uid") != null ? Integer.parseInt(request.getParameter("uid")) : 0;
			int ord = request.getParameter("ord") != null ? Integer.parseInt(request.getParameter("ord")) : 1;
			String view =  request.getParameter("view") != null ? request.getParameter("view") : "T";

			if(uid > 0) {
				QuizGameVO quiz = quizService.getQuizGame(uid);

				int quizGameQueCnt = quizService.getQuizGameQueCount(uid);

				HashMap<String, Object> rParam = new HashMap<String, Object>();
				rParam.put("gqid", uid);
				int reqLogCount = quizService.getQuizReqLogCount(rParam);

				mav.addObject("uid", uid);
				mav.addObject("current", ord);
				mav.addObject("max", quizGameQueCnt);
				mav.addObject("reqLogCount", reqLogCount);
				mav.addObject("userLv", userLv);

				if(userLv == 99) {
					HashMap<String, Object> sParam = new HashMap<String, Object>();
					sParam.put("gqid", uid);
					sParam.put("userSeq", userSeq);

					int logCnt = quizService.getQuizLogCnt(sParam);

					if(logCnt == 0) {
						sParam.put("success", "S");
						sParam.put("attendAlmoney", quiz.getAttendAlmoney());
						sParam.put("createDt", createDt);
						sParam.put("updateDt", createDt);

						quizService.addQuizLog(sParam);
					}
				}

				if(view.equals("V")) {
					HashMap<String, Object> cParam = new HashMap<String, Object>();
					cParam.put("gqid", uid);
					cParam.put("userSeq", userSeq);
					cParam.put("updateDt", createDt);

					cParam.put("success", "V");

					quizService.setQuizLog(cParam);
				}

				if(ord <= quizGameQueCnt) {
					// getQuizGameQueList(uid, ord) quid(QuizGame uid), queid(QuestQue uid), quest
					HashMap<String, Object> param = new HashMap<String, Object>();
					param.put("quid", uid);
					param.put("ord", ord);

					QuizGameQueVO gameQue = quizService.getQuizGameQue(param);

					// uid, quest, rtime1, rtime2, stime1, stime2, correct(??????), hint, comment
					QuizQueVO quest = quizService.getQuizQue(gameQue.getQueid());

					// uid, ano, answer
					List<QuizAnsVO> ansList = quizService.getQuizAnsList(quest.getUid());

					//QUIZ_LOG
					HashMap<String, Object> qParam = new HashMap<String, Object>();
					qParam.put("gqid", uid);
					qParam.put("userSeq", userSeq);
					QuizLogVO quizLog = quizService.getQuizLog(qParam);

					mav.addObject("quiz", quiz);
					mav.addObject("gameQue", gameQue);
					mav.addObject("quest", quest);
					mav.addObject("ansList", ansList);
					mav.addObject("quizLog", quizLog);
					mav.addObject("next", (ord + 1));
				}
				else {

				}
			}
		}
		else {
			String msg1 = messageSource.getMessage("hello", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
			CommonUtil.jspAlert(response, "?????? ????????? ????????????.", "/", "parent.parent"); // msg1 ??????
		}

		return mav;
	}

	@RequestMapping("start")
	public ModelAndView start(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		ModelAndView mav = new ModelAndView();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		if(userSeq > 0) {
			int uid = request.getParameter("uid") != null ? Integer.parseInt(request.getParameter("uid")) : 0;
			int ord = 1;

			if(uid > 0) {
				Map<String, Object> myAlmoney = commonService.getAlmoneyBySeq(userSeq);
				BigDecimal almoney = (BigDecimal)myAlmoney.get("Almoney");
				int nAlmoney = almoney.intValue();

				QuizGameVO quiz = quizService.getQuizGame(uid);

				int quizGameQueCnt = quizService.getQuizGameQueCount(uid);

				mav.addObject("uid", uid);
				mav.addObject("current", ord);
				mav.addObject("max", quizGameQueCnt);
				mav.addObject("userLv", userLv);

				HashMap<String, Object> sParam = new HashMap<String, Object>();
				sParam.put("gqid", uid);
				sParam.put("userSeq", userSeq);

				int logCnt = quizService.getQuizLogCnt(sParam);

				QuizLogVO quizLog = null;
				if(logCnt > 0) {
					HashMap<String, Object> qParam = new HashMap<String, Object>();
					qParam.put("gqid", uid);
					qParam.put("userSeq", userSeq);
					quizLog = quizService.getQuizLog(qParam);
				}

				mav.addObject("logCnt", logCnt);
				mav.addObject("quiz", quiz);
				mav.addObject("myAlmoney", nAlmoney);
				mav.addObject("quizLog", quizLog);
			}
		}
		else {
			String msg1 = messageSource.getMessage("hello", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
			CommonUtil.jspAlert(response, "?????? ????????? ????????????.", "/", "parent.parent"); // msg1 ??????
		}

		return mav;
	}

	@RequestMapping(value="quizGameReq", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String quizGameReq(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		String format = "yyyy-MM-dd hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(??????/?????? ??????)
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String createDt = type.format(today.getTime());

		if(userLv > 0) {
			int uid = request.getParameter("uid") != null ? Integer.parseInt(request.getParameter("uid")) : 0;

			if(uid > 0) {
				QuizGameVO quiz = quizService.getQuizGame(uid);

				HashMap<String, Object> rParam = new HashMap<String, Object>();
				rParam.put("gqid", uid);

				int attendAlmoney = quiz.getAttendAlmoney();

				BigDecimal mem_almoney = new BigDecimal( String.valueOf(attendAlmoney) );


				HashMap<String, Object> sParam = new HashMap<String, Object>();
				sParam.put("gqid", uid);
				sParam.put("userSeq", userSeq);
				int logCnt = quizService.getQuizLogCnt(sParam);

				String msg1 = messageSource.getMessage("hello", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
				String msg2 = messageSource.getMessage("hello", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
				String msg3 = messageSource.getMessage("hello", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
				String msg4 = messageSource.getMessage("hello", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
				String msg5 = messageSource.getMessage("hello", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????

				if(logCnt == 0) {
					// user Almoney ?????????
					Map<String, Object> myAlmoney = commonService.getAlmoneyBySeq(userSeq);
					BigDecimal almoney = (BigDecimal)myAlmoney.get("Almoney");

					// ??? ??????
					int returnCode = 0;
					String almoney_flag = "45";
					String errMsg = "";
					if(mem_almoney.compareTo(BigDecimal.ZERO) > 0) { // compareTo :::: -1??? ??????, 0??? ??????, 1??? ??????
						HashMap<String, Object> param = new HashMap<String, Object>();
						BigDecimal orgAlmoney = mem_almoney;
						BigDecimal minusNum = new BigDecimal(-1.0);
						if(almoney_flag.equals("45")) { mem_almoney = mem_almoney.multiply(minusNum); }

						if(orgAlmoney.compareTo(almoney) <= 0) { // compareTo :::: -1??? ??????, 0??? ??????, 1??? ??????
							param.put("mem_seq", userSeq);
							param.put("mem_almoney", mem_almoney);
							param.put("almoney_flag", almoney_flag);

							returnCode = commonService.setUpdateAlmoney(param);

							switch(returnCode) {
								case 1:
									break;
								case 2:
									errMsg = "???????????? ??????????????????."; // msg1 ??????
									break;
								default:
									errMsg = "???????????????.\\n?????? ?????? ????????? ???????????? ????????? ????????? ???????????? ???????????????."; // msg2, msg3 ??????
									break;
							}
						}
						else {
							errMsg = "?????? ???????????? ???????????????."; // msg4 ??????
						}
					}
					else {
						errMsg = "?????? ???????????? ???????????????."; // msg4 ??????
					}

					// ?????? ??????
					if(returnCode == 1) {
						sParam.put("success", "S");
						sParam.put("attendAlmoney", quiz.getAttendAlmoney());
						sParam.put("createDt", createDt);
						sParam.put("updateDt", createDt);

						quizService.addQuizLog(sParam);

						result = CommonUtil.libJsonExit("Y", r_res);
					}
					else {
						result = CommonUtil.libJsonExit(errMsg, r_res);
					}
				}
				else {
					result = CommonUtil.libJsonExit("?????? ?????? ?????????????????????.", r_res); // msg5 ??????
				}
			}
		}
		else {
			result = CommonUtil.libJsonExit("access", r_res);
		}

		return result;
	}

	@RequestMapping(value="quizGameReqCancel", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String quizGameReqCancel(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		//????????????????????????
		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		String format = "yyyy-MM-dd hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(??????/?????? ??????)
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String createDt = type.format(today.getTime());

		String msg1 = messageSource.getMessage("hello", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg2 = messageSource.getMessage("hello", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg3 = messageSource.getMessage("hello", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg4 = messageSource.getMessage("hello", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????
		String msg5 = messageSource.getMessage("hello", null, locale); // /resources/locale/messages_??.properties ??? ????????? ????????? hello ????????? ??????

		if(userLv > 0) {
			int uid = request.getParameter("uid") != null ? Integer.parseInt(request.getParameter("uid")) : 0;

			if(uid > 0) {
				QuizGameVO quiz = quizService.getQuizGame(uid);

				HashMap<String, Object> rParam = new HashMap<String, Object>();
				rParam.put("gqid", uid);

				int attendAlmoney = quiz.getAttendAlmoney();

				BigDecimal mem_almoney = new BigDecimal( String.valueOf(attendAlmoney) );

				HashMap<String, Object> sParam = new HashMap<String, Object>();
				sParam.put("gqid", uid);
				sParam.put("userSeq", userSeq);
				int logCnt = quizService.getQuizLogCnt(sParam);

				if(logCnt > 0) {
					// ??? ??????
					int returnCode = 0;
					String almoney_flag = "52";
					String errMsg = "";
					if(mem_almoney.compareTo(BigDecimal.ZERO) > 0) { // compareTo :::: -1??? ??????, 0??? ??????, 1??? ??????
						HashMap<String, Object> param = new HashMap<String, Object>();

						param.put("mem_seq", userSeq);
						param.put("mem_almoney", mem_almoney);
						param.put("almoney_flag", almoney_flag);

						returnCode = commonService.setUpdateAlmoney(param);

						switch(returnCode) {
							case 1:
								break;
							case 2:
								errMsg = "???????????? ??????????????????."; // msg1 ??????
								break;
							default:
								errMsg = "???????????????.\\n?????? ?????? ????????? ???????????? ????????? ????????? ???????????? ???????????????."; // msg2, msg3 ??????
								break;
						}
					}
					else {
						errMsg = "?????? ???????????? ???????????????."; // msg4 ??????
					}

					// ?????? ?????? ??????
					if(returnCode == 1) {
						HashMap<String, Object> dParam = new HashMap<String, Object>();
						dParam.put("userSeq", userSeq);
						dParam.put("gqid", uid);

						//QUIZ_LOG ??????
						quizService.deleteQuizLog(dParam);
						//QUIZ_REQ ??????
						quizService.deleteQuizReq(dParam);

						result = CommonUtil.libJsonExit("Y", r_res);
					}
					else {
						result = CommonUtil.libJsonExit(errMsg, r_res);
					}
				}
				else {
					result = CommonUtil.libJsonExit("?????? ?????? ????????? ????????????.", r_res); // msg5 ??????
				}
			}
		}
		else {
			result = CommonUtil.libJsonExit("access", r_res);
		}

		return result;
	}

	@RequestMapping(value="getTotalGameAlpay", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String getTotalReqAl(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		if(userLv > 0) {
			int uid = request.getParameter("uid") != null ? Integer.parseInt(request.getParameter("uid")) : 0;

			if(uid > 0) {
				QuizGameVO quiz = quizService.getQuizGame(uid);

				HashMap<String, Object> rParam = new HashMap<String, Object>();
				rParam.put("gqid", uid);

				int reqLogCount = quizService.getQuizReqLogCount(rParam);

				int total = quiz.getAdmAlmoney() + (quiz.getAttendAlmoney() * reqLogCount) + quiz.getCarryoverMoney();

				result = CommonUtil.libJsonExit(total, r_res);

			}
		}
		else {
			result = CommonUtil.libJsonExit(0, r_res);
		}

		return result;
	}

	@RequestMapping(value="getQuizGame", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String getQuizGame(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		String result = null;
		Map<String, Object> r_res = new HashMap<String, Object>();

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);

		//????????? ?????????
		int gameCnt = quizService.getQuizGameOnCountByNonUid();

		QuizGameVO quiz = null;

		if(gameCnt > 0) {
			quiz = quizService.getQuizGameLimit();

			r_res.put("uid", quiz.getUid());
			r_res.put("step", quiz.getStep());
			r_res.put("subject", quiz.getSubject());
			r_res.put("startYmd", quiz.getStartYmd());
			r_res.put("startDtType", quiz.getStartDtType());
			r_res.put("startDtH", quiz.getStartDtH());
			r_res.put("startDtM", quiz.getStartDtM());
		}

		if(userLv > 0) {
			if(gameCnt > 0) {
				// ??????????????? ????????????
				HashMap<String, Object> sParam = new HashMap<String, Object>();
				sParam.put("gqid", quiz.getUid());
				sParam.put("userSeq", userSeq);
				int reqCnt = quizService.getQuizReqCnt(sParam);

				int logCnt = quizService.getQuizLogCnt(sParam);
				r_res.put("logCnt", logCnt);

				if(reqCnt == 0) {
					result = CommonUtil.libJsonExit("1", r_res);
				}
				else {
					result = CommonUtil.libJsonExit("0", r_res);
				}
			}
			else {
				result = CommonUtil.libJsonExit("0", r_res);
			}
		}
		else {
			if(quiz != null) {
				result = CommonUtil.libJsonExit("1", r_res);
			}
			else {
				result = CommonUtil.libJsonExit("0", r_res);
			}
		}

		return result;
	}
}
