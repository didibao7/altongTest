package com.altong.web.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.altong.web.logic.util.CommonUtil;
import com.altong.web.logic.util.UtilFile;
import com.altong.web.service.CookieLoginService;
import com.altong.web.service.common.CommonService;
import com.altong.web.service.member.MemberService;

import net.coobird.thumbnailator.Thumbnails;

@Controller
@RequestMapping("common/*")
public class CommonController {
	@Autowired
	CommonService commonService;

	@Autowired
	MemberService memberService;

	@Autowired
	CookieLocaleResolver localResolver;

	@Autowired
	MessageSource messageSource;

	@Autowired
	CookieLoginService cookieLoginService;

	@RequestMapping(value="common/getAlmoneyAlarm", method = RequestMethod.POST)
	public @ResponseBody String getAlmoneyAlarm(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		//Map<String , Object> result = new HashMap<String, Object>();
		String result = null;
		boolean isSetSessCookie = false;

		Map<String, Object> r_res = new HashMap<String, Object>();

		String notAlmoneyAll = null;
		String notAlarm = null;
		String ElapseTime = "";
		Long start_time = System.currentTimeMillis();

		ElapseTime += "1:" + Long.toString(System.currentTimeMillis() - start_time);

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);


		DecimalFormat formatter = new DecimalFormat("###,###.#");

		if(request.getAttribute("notAlmoneyAll") != null) {
			notAlmoneyAll = request.getAttribute("notAlmoneyAll").toString();
		}
		if(request.getAttribute("notAlarm") != null) {
			notAlmoneyAll = request.getAttribute("notAlarm").toString();
		}


		if(notAlmoneyAll != null) {

		}
		else {
			double almoneyAll = commonService.getAlmoneyAll();
			//System.out.println("globalVal: " + globalVal);
			ElapseTime += ", 2:" + Long.toString(System.currentTimeMillis() - start_time);
			r_res.put("AlmoneyAll", formatter.format(almoneyAll));
		}

		//JSONObject global = null;
		if(userSeq > 0) {
			Map<String, Object> m_res = commonService.getAlmoneyBySeq(userSeq);
			ElapseTime += ", 3:" + Long.toString(System.currentTimeMillis() - start_time);
			//System.out.println("Almoney : " + m_res.get("Almoney"));
			if(m_res.size() > 0) {
				BigDecimal zero = new BigDecimal("0.00");
				BigDecimal Almoney = (BigDecimal)m_res.get("Almoney");
				BigDecimal AlpayKR = (BigDecimal)m_res.get("AlpayKR");

				Almoney = Almoney.setScale(0, BigDecimal.ROUND_FLOOR);
				AlpayKR = AlpayKR.setScale(0, BigDecimal.ROUND_FLOOR);

				BigDecimal uAlmoney = cookieLoginService.getCookieUserAlmoney(request, response);
				BigDecimal uAlpayKR = cookieLoginService.getCookieUserAlpayKR(request, response);

				uAlmoney = uAlmoney.setScale(0, BigDecimal.ROUND_FLOOR);
				uAlpayKR = uAlpayKR.setScale(0, BigDecimal.ROUND_FLOOR);

				r_res.put("UserAlmoney", Almoney.setScale(0, BigDecimal.ROUND_FLOOR));
				r_res.put("UserAlpayKR", AlpayKR.setScale(0, BigDecimal.ROUND_FLOOR));
				/*if(uAlmoney != Almoney || uAlpayKR != AlpayKR) {

					r_res.put("UserAlmoney", Almoney.setScale(0, BigDecimal.ROUND_FLOOR));
					r_res.put("UserAlpayKR", AlpayKR.setScale(0, BigDecimal.ROUND_FLOOR));
					isSetSessCookie = true;
				}
				*/
				//System.out.println("UserAlmoney : " + r_res.get("UserAlmoney"));
				//System.out.println("UserAlpayKR : " + r_res.get("UserAlpayKR"));
				//System.out.println("uAlmoney : " + uAlmoney);
				//System.out.println("uAlpayKR : " + uAlpayKR);

				if(uAlmoney != zero) {
					BigDecimal UserAlmoney = uAlmoney;
					r_res.put("Almoney", formatter.format(UserAlmoney.setScale(0, BigDecimal.ROUND_FLOOR)));
				}
				else {
					r_res.put("Almoney", 0);
				}
				if(uAlpayKR != zero) {
					BigDecimal UserAlpayKR = uAlpayKR;
					r_res.put("AlpayKR", formatter.format(UserAlpayKR.setScale(0, BigDecimal.ROUND_FLOOR)));
				}
				else {
					r_res.put("AlpayKR", 0);
				}


				if(request.getAttribute("notAlarm") == null) {
					int alarm_after_time = cookieLoginService.getAlarmRefreshTime(request, response);

					if(alarm_after_time < System.currentTimeMillis()) {

						List<Map<String, Object>> alarmList = commonService.getAlarmBySeq(userSeq);
						ElapseTime += ", 4:" + Long.toString(System.currentTimeMillis() - start_time);
						int alarmCnt = 0;

						for(int i = 0; i < alarmList.size(); i++) {
							if(alarmList.get(i).get("ANS_CHOICE") != null) { alarmCnt += Integer.parseInt( String.valueOf( alarmList.get(i).get("ANS_CHOICE") ) ); }
							if(alarmList.get(i).get("ANS_REGIST") != null) { alarmCnt += Integer.parseInt( String.valueOf( alarmList.get(i).get("ANS_REGIST") ) ); }
							if(alarmList.get(i).get("FAVORITE_QUE_REGIST") != null) { alarmCnt += Integer.parseInt( String.valueOf( alarmList.get(i).get("FAVORITE_QUE_REGIST") ) ); }
							if(alarmList.get(i).get("CMT_REGIST") != null) { alarmCnt += Integer.parseInt( String.valueOf( alarmList.get(i).get("CMT_REGIST") ) ); }
							if(alarmList.get(i).get("ANS_CHOICE_READY") != null) { alarmCnt += Integer.parseInt( String.valueOf( alarmList.get(i).get("ANS_CHOICE_READY") ) ); }
							if(alarmList.get(i).get("ALMONEY_INCOME") != null) { alarmCnt += Integer.parseInt( String.valueOf( alarmList.get(i).get("ALMONEY_INCOME") ) ); }
							if(alarmList.get(i).get("ALMONEY_PAYING") != null) { alarmCnt += Integer.parseInt( String.valueOf( alarmList.get(i).get("ALMONEY_PAYING") ) ); }
							if(alarmList.get(i).get("MEM_LEVEL_UP") != null) { alarmCnt += Integer.parseInt( String.valueOf( alarmList.get(i).get("MEM_LEVEL_UP") ) ); }
							if(alarmList.get(i).get("REPORT") != null) { alarmCnt += Integer.parseInt( String.valueOf( alarmList.get(i).get("REPORT") ) ); }
							if(alarmList.get(i).get("MENTEE") != null) { alarmCnt += Integer.parseInt( String.valueOf( alarmList.get(i).get("MENTEE") ) ); }
							if(alarmList.get(i).get("MENTEE_UNSET") != null) { alarmCnt += Integer.parseInt( String.valueOf( alarmList.get(i).get("MENTEE_UNSET") ) ); }
							if(alarmList.get(i).get("RECOMM_MEM_JOIN") != null) { alarmCnt += Integer.parseInt( String.valueOf( alarmList.get(i).get("RECOMM_MEM_JOIN") ) ); }
							if(alarmList.get(i).get("NOTICE") != null) { alarmCnt += Integer.parseInt( String.valueOf( alarmList.get(i).get("NOTICE") ) ); }
						}

						r_res.put("ALARM_REFRESH_TIME", System.currentTimeMillis() + 100);
						r_res.put("ALARM_COUNT_SUM", alarmCnt);
						isSetSessCookie = true;
					}
					r_res.put("AlarmCNT", formatter.format(r_res.get("ALARM_COUNT_SUM")));
				}
			}
			else {
				Map<String, Object> a_res = new HashMap<String, Object>();
				a_res.put("FlagDel", true);
				a_res.put("Almoney", "");
				a_res.put("AlpayKR", "");

				result = CommonUtil.libJsonExit("GET_ALMONEY", a_res);

			}

		}
		else {
			r_res.put("FlagDel", true);
			r_res.put("Almoney", "");
			r_res.put("AlpayKR", "");
		}

		if(isSetSessCookie) {
			int ver = cookieLoginService.getCookieVer(request, response);

			//global.put("Ver",ver + 1);
			//if(ver > 9) { global.put("Ver",  1);  }

			if(userSeq > 0) {
				//CommonUtil.libSetSessCookie(globalVal.get("SESS"), response); // 쿠키 제어 하지 않기로 함
			}
			else {
				//ObjectMapper mapper = new ObjectMapper();
				//JsonNode jsonObject = mapper.readTree(CommonUtil.libDecode(global.get("SESS").toString()));

				//r_res.put("SESS", global);
				//r_res.put("SessExpire", global.get("SessExpire"));
				//r_res.put("Ver", global.get("Ver"));
			}

		}

		ElapseTime += ", 5:" + Long.toString(System.currentTimeMillis() - start_time);
		r_res.put("ElapseTime", ElapseTime);


		result = CommonUtil.libJsonExit("GET_ALMONEY", r_res);


		//System.out.println("result : " + result);
		return result;
	}

	@RequestMapping(value="common/setStatus", method = RequestMethod.GET)
	public @ResponseBody void getSetStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//Map<String , Object> result = new HashMap<String, Object>();

		switch(3) {
			case 1:
				commonService.setCommonAlmoneyStatus();
				//result.put("result", "1");;
				break;
			case 2:
				commonService.setCommonEarnStatus();
				//result.put("result", "1");;
				break;
			case 3:
				commonService.setCommonOrderStatus();
				//result.put("result", "1");;
				break;
			default:
				//result.put("result", "0");;

		}

		//return result;
	}

	@RequestMapping(value="common/getMemoCount", method = RequestMethod.POST)
	public @ResponseBody String getMemoCount(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String result = "";

		String ElapseTime = "";
		Long start_time = System.currentTimeMillis();
		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		ElapseTime += "1:" + Long.toString(System.currentTimeMillis() - start_time);


		if(userSeq > 0) {
			int m_count = commonService.getMsgBySeq(userSeq);
			ElapseTime += ", 3:" + Long.toString(System.currentTimeMillis() - start_time);

			result = CommonUtil.libJsonExit("SUCCESS", m_count);
		}

		return result;
	}

	//common/searchNick
	@RequestMapping(value="common/searchNick", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String searchNick(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String result = "";
		String act = request.getParameter("ACT");
		String nick = request.getParameter("H_nick");

		if(act.equals("SEARCH_NICK")) {
			String nickChk = memberService.getNickFind(nick);

			HashMap<String, Object> obj = new HashMap<String, Object>();
			if(nickChk != null) {
				obj.put("seq", nickChk);
				result = CommonUtil.libJsonExit(act, obj);
			}
			else {
				String msg1 = messageSource.getMessage("msg_1064", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
				String msg2 = messageSource.getMessage("msg_1065", null, locale); // /resources/locale/messages_??.properties 에 선언된 변수를 hello 자리에 적용
				result = CommonUtil.libJsonExit(msg1 + "(" + nick + ")" + msg2, obj); // msg1, msg2 활용
			}
		}

		return result;
	}

	@RequestMapping(value="common/tempSave", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String tempSave(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		String act = request.getParameter("ACT");
		String isAUTO = request.getParameter("isAUTO");

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		if(userSeq == 0) {
			return "<!--LOGOFF-->";
		}

		if(act != null && act.equals("TQ_SAVE")) {
			String randBool = "Y";
			int randmonInt = (int)(Math.random() * 15);
			if(randmonInt == 0) { randBool = "N"; }

			//이 기능의 사용 빈도가 많아지면 아래의 쿼리를 프로시저로 만들자.
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("userSeq", userSeq);
			param.put("title", request.getParameter("Title"));
			param.put("contents", request.getParameter("Contents"));
			param.put("randBool", randBool);
			commonService.setQuestionTempSave(param);

			if(isAUTO == "Y") {
				return CommonUtil.libJsonArrExit("", null);
			}
			else {
				//return CommonUtil.libJsonArrExit("작성중인 질문이 임시 저장되었습니다.\n질문이 정상적으로 등록되면 임시 저장된 글은 자동으로 삭제됩니다.\n등록하지 않았을 경우 신규 질문을 입력할 때 자동으로 임시 저장된 글이 로드됩니다.\n(일정 기간이 지나면 자동 삭제되며, 첨부 파일은 임시 저장되지 않습니다.)", null);
			}
		}
		else if(act != null && act.equals("TQ_LOAD")) {
			HashMap<String, Object> temp = commonService.getQuestionTemp(userSeq);

			List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			list.add(temp);

			return CommonUtil.libJsonArrExit("TQ_LOAD", list);
		}
		else if(act != null && act.equals("TA_SAVE")) {
			//System.out.println("UserSeq : " + global.get("UserSeq").toString());
			//System.out.println("UserSeq : " + request.getParameter("QuestionSeq"));
			//System.out.println("UserSeq : " +  request.getParameter("Contents"));
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("userSeq", userSeq);
			param.put("questionSeq", request.getParameter("QuestionSeq"));
			param.put("contents", request.getParameter("Contents"));
			commonService.setAnswerTempSave(param);

			if(isAUTO == "Y") {
				return CommonUtil.libJsonArrExit("", null);
			}
			else {
				// return CommonUtil.libJsonArrExit("작성중인 답변이 임시 저장되었습니다.\n답변이 정상적으로 등록되면 임시 저장된 글은 자동으로 삭제됩니다.\n등록하지 않았을 경우 해당 질문의 신규 답변을 입력할 때 자동으로 임시 저장된 글이 로드됩니다.\n(일정 기간이 지나면 자동 삭제되며, 첨부 파일은 임시 저장되지 않습니다.)", null);
			}
		}
		else if(act != null && act.equals("TA_LOAD")) {
			String questionSeq = request.getParameter("QuestionSeq").toString();


			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("userSeq", userSeq);
			param.put("questionSeq", questionSeq);

			String contents = commonService.getAnswerTemp(param);

			HashMap<String, Object> temp = new HashMap<String, Object>();
			temp.put("contents", contents);

			List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			list.add(temp);

			return CommonUtil.libJsonArrExit("TA_LOAD", list);
		}

		return null;
	}

	@RequestMapping(value="common/quillFileUpload", method = RequestMethod.POST)
	public @ResponseBody String quillFileUpload(MultipartHttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		response.setContentType("text/html; charset=UTF-8");

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		List<MultipartFile> fileList = request.getFiles("image");

		if(fileList.size() > 0) {
			UtilFile utilFile = new UtilFile();

			String originFileName = "";

			String ThumbFileSaveName = "";


			for (MultipartFile mf : fileList) {
				originFileName = mf.getOriginalFilename(); // 원본 파일 명

				Date time = new Date();
				SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmSS");
				String today = format1.format(time);

	        	String FileReName = userSeq + "_" + today;
	        	String FileExtension = FilenameUtils.getExtension(originFileName);
	        	String FileSaveName = FileReName + "." + FileExtension;

	        	// 파일 업로드 결과값을 path로 받아온다(이미 fileUpload() 메소드에서 해당 경로에 업로드는 끝났음)
		        String defaultFolder = "quill/upload";

		        String FilePath = utilFile.getSaveLocation(request, defaultFolder);


		        File fileDir = new File(FilePath);

		        if (!fileDir.exists()) {
		        	fileDir.mkdirs();
		        }

		        try { // 파일생성
		        	//원본 파일 크기로 업로드 처리
		        	File original = new File(FilePath, FileSaveName);
		        	mf.transferTo(original);

		        	BufferedImage srcImg = ImageIO.read(original);

		        	int ow = srcImg.getWidth();
		        	int oh = srcImg.getHeight();

		        	int rw = ow;
		        	int rh = oh;
		        	if(ow > 900) {
		        		float fw = ow;
		        		rw = 900;

		        		float v = (rw / fw);
		        		//System.out.println("v : " + v);
		        		rh = (int)(oh * v);
		        	}
		        	//System.out.println("rw : " + rw);
		        	//System.out.println("rh : " + rh);

		        	ThumbFileSaveName = FileReName + "_thumb." + FileExtension;


		        	File thumbnail = new File(FilePath, ThumbFileSaveName);

		        	Thumbnails.of(original).size(rw, rh).outputFormat(FileExtension).toFile(thumbnail);

		        	if(thumbnail.exists()) {
			        	// 해당 경로만 받아 db에 저장
			        	//HashMap<String, Object> param = new HashMap<String, Object>();
			        	//param.put("thumbFileSaveName", ThumbFileSaveName);
			        	//param.put("userseq", userSeq);
				        //memberService.setUserProfileImg(param);

		        		//업로드 원본 파일 삭제
		        		if(original.delete()){
		        			utilFile.deleteFile(original);
		        		}
		        	}
		        } catch (Exception e) {
		        	return "";
		        }
			}

			return ThumbFileSaveName;
		}

		return "";
	}


	@RequestMapping(value="common/getChkUseAlmoney", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String getChkUseAlmoney(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String result = "";
		String act = request.getParameter("act");
		int pay = request.getParameter("pay") != "" ? Integer.parseInt(request.getParameter("pay")) : 0;

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);
		String userNick = cookieLoginService.getCookieUserNickName(request, response);

		int maxMoney = 0;
		if(userLv > 3) {
			maxMoney= 300000;
		}else {
			maxMoney = 30000;
		}

		if(userSeq > 0) {
			String format = "yyyy-MM-dd";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
			Calendar today = Calendar.getInstance();
			SimpleDateFormat type = new SimpleDateFormat(format);
			String regDate = type.format(today.getTime());

			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("userSeq", userSeq);
			param.put("act", act);
			param.put("regDate", regDate);


			HashMap<String, Object> obj = new HashMap<String, Object>();

			//if(userLv != 99) {
				int almoney = 0;
				BigDecimal Balmoney = commonService.getChkUseAlmoney(param);
				if(Balmoney.compareTo(BigDecimal.ZERO) < 0) {
					almoney = Balmoney.intValue() * (-1);
				}
				else {
					almoney = Balmoney.intValue();
				}
				//System.out.println("almoney : " + almoney);
				int total = pay + almoney;
				//System.out.println("total : " + total);

				if(total <= maxMoney) {
					int avalilPay = 0;
					avalilPay = maxMoney - total;
					obj.put("availPay", avalilPay);
					result = CommonUtil.libJsonExit("true", obj);
				}
				else {
					int avalilPay = 0;
					if(total > maxMoney) {
						if(almoney <= maxMoney) {
							avalilPay = maxMoney - almoney;
							obj.put("availPay", avalilPay);
							obj.put("userNick", userNick);
							//System.out.println("avalilPay : " + avalilPay);
						}
					}
					else {
						obj.put("availPay", 0);
						obj.put("userNick", userNick);
					}

					result = CommonUtil.libJsonExit("over", obj);
				}
			//}
			//else {
			//	result = CommonUtil.libJsonExit("true", obj);
			//}
		}

		return result;
	}


	@RequestMapping(value="common/questionVote", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String questionVote(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String result = "";
		String act = request.getParameter("act");
		int contentSeq = request.getParameter("contentSeq") != "" ? Integer.parseInt(request.getParameter("contentSeq")) : 0;
		String contentType = request.getParameter("contentType") != "" ? request.getParameter("contentType") : "Q";

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);
		final int userLv = cookieLoginService.getCookieUserLv(request, response);
		String userNick = cookieLoginService.getCookieUserNickName(request, response);

		HashMap<String, Object> obj = new HashMap<String, Object>();
		if(userSeq > 0 && contentSeq > 0) {
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("contentSeq", contentSeq);
			param.put("contentType", contentType);
			param.put("userSeq", userSeq);
			int voteCount = commonService.getQuestionVoteCount(param);

			HashMap<String, Object> vote = null;
			int good = 0;
			int bad = 0;
			if(voteCount > 0) {
				vote = commonService.getQuestionVote(param);
				good = Integer.parseInt( String.valueOf( vote.get("good") ) );
				bad = Integer.parseInt( String.valueOf( vote.get("bad") ) );
			}


			HashMap<String, Object> vParam = new HashMap<String, Object>();
			vParam.put("userSeq", userSeq);
			vParam.put("contentSeq", contentSeq);
			vParam.put("contentType", contentType);

			if(voteCount > 0) {
				if(act.equals("G")) {
					if(bad > 0) {
						vParam.put("bad", (bad - 1));
					}
					else {
						vParam.put("bad", bad);
					}
					if(good == 0) {
						vParam.put("good", (good + 1));
					}
					else {
						vParam.put("good", good);
					}
				}
				else {
					if(good > 0) {
						vParam.put("good", (good - 1));
					}
					else {
						vParam.put("good", good);
					}
					if(bad == 0) {
						vParam.put("bad", (bad + 1));
					}
					else {
						vParam.put("bad", bad);
					}
				}

				commonService.setQuestionVote(vParam);
			}
			else {
				if(act.equals("G")) {
					vParam.put("good", 1);
					vParam.put("bad", 0);
				}
				else {
					vParam.put("good", 0);
					vParam.put("bad", 1);
				}
				commonService.addQuestionVote(vParam);
			}

			//jsonObj 데이터 만들기
			HashMap<String, Object> voteItem = commonService.getQuestionVoteSum(vParam);

			obj.put("good", voteItem.get("good"));
			obj.put("bad", voteItem.get("bad"));

			result = CommonUtil.libJsonExit("true", obj);
		}else {
			result = CommonUtil.libJsonExit("false", obj);
		}

		return result;
	}

	@RequestMapping(value="common/getSingleConents", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String getSingleConents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String result = "";		int contentSeq = request.getParameter("contentSeq") != "" ? Integer.parseInt(request.getParameter("contentSeq")) : 0;
		String contentType = request.getParameter("contentType") != "" ? request.getParameter("contentType") : "Q";


		HashMap<String, Object> obj = new HashMap<String, Object>();
		if(contentSeq > 0) {
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("contentSeq", contentSeq);
			param.put("contentType", contentType);

			String title = "";
			if(contentType.equals("Q")) {
				title = commonService.getSingleTitle(param);
			}

			String contents = commonService.getSingleConents(param);

			obj.put("title", title);
			obj.put("contents", contents);

			result = CommonUtil.libJsonExit("true", obj);
		} else {
			result = CommonUtil.libJsonExit("false", obj);
		}

		return result;
	}


	@RequestMapping(value="common/setLog", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String setLog(HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		String result = "";

		final int userSeq = cookieLoginService.getCookieUserSeq(request, response);

		HashMap<String, Object> obj = new HashMap<String, Object>();
		if(userSeq > 0 ) {
			CommonUtil.setLog(request, response);

			result = CommonUtil.libJsonExit("true", obj);
		}

		return result;
	}

}
