package com.altong.web.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.altong.web.service.translate.TranslateService;

@Controller
@RequestMapping("translate/*")
public class TranslateController {

	@Autowired
	TranslateService translateService;
	
	@RequestMapping(value="trans", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String transCount(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		return translateService.transProcess(request, response, locale);
	}

	@RequestMapping(value="getQuestionOrgTitle", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String getQuestionOrgTitle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return translateService.getQuestionOrgTitle(request, response);
	}

	@RequestMapping(value="getAnsOrgTitle", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String getAnsOrgTitle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return translateService.getAnsOrgTitle(request, response);
	}

	@RequestMapping(value="getReplyOrgTitle", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String getReplyOrgTitle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return translateService.getReplyOrgTitle(request, response);
	}

	@RequestMapping(value="getNoticeOrgTitle", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String getNoticeOrgTitle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return translateService.getNoticeOrgTitle(request, response);
	}


	// 이하 테스트 코드
	/*
	@RequestMapping(value="join_ai", method = {RequestMethod.GET, RequestMethod.POST})
	public void joinSave_n2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		EncLibrary en = new EncLibrary();
		MD5Class md5 = new MD5Class();

		String userPone = "";
		String country = "";
		String phone = "";
		String userPhone = "";
		String nickName = "";
		String password1 = "";
		String password2 = "";
		String email1 = "";
		String email2 = "";
		String email = "";
		String phoneUse = "";
		String chuphone = "";
		String chuphone1 = "";
		String chuCode = "";
		String userPW_md5 = "";

		String almoneyJoin = "";
		String error_no = "";
		String memberSeq = "";
		String ch_encodeData = "";

		String chuCode1 = "";
		String chuCode2 = "";

		String sName = "";
		String sGender = "";
		String sBirthDate = "";

		String sAuthType = "";
		String sMobileCo = "";
		String sCipherTime = "";
		String sRequestNumber = "";
		String sResponseNumber = "";
		String sNationalInfo = "";
		String sDupInfo = "";

		String photo = "";
		String certNum = "";

		String result_code = "";
		String result_msg = "";

		String nation = "";
		String lang = "";

		String format = "yyyy-MM-dd aa hh:mm:ss";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String now = type.format(today.getTime());


		country = "82";

		phone = "01000000006";

		if(country.equals("82") && phone.length() > 0 && phone.substring(0, 1).equals("0")) {
			phone = phone.substring( 1, phone.length());
		}

		nickName = "AI_T";
		password1 = "alfree3131";
		password2 = "alfree3131";


		userPhone = en.AlmoneyEncrypt( phone );
		photo =  "";

		email = "";

		nation = "KOR";
		lang = "ko";

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("userPhone", userPhone);
		param.put("country", country);
		//System.out.println("userPhone : " + param.get("userPhone"));
		//System.out.println("country : " + param.get("country"));

		HashMap<String, Object> memSmsCntInfo = memberService.getMemSmsCntInfo(param);

		String randCode = null;

		if(memSmsCntInfo != null) {
			randCode = String.valueOf( memSmsCntInfo.get("secCode") );
		}
		certNum = request.getParameter("CertNum");

		//테스트후 주석처리
		//randCode = "1111";
		//certNum = "1111";

		userPW_md5 = md5.md5_encode(password1);

		String nickNotUse = configService.getNickNameCheck();


		if(!password1.equals(password2) || nickName.length() == 0) {
			CommonUtil.jspAlert(response, "올바르지 않은 접속입니다." , "/default/main", "parent.parent");
			//System.out.println("(1) : ");
		}
		else if(CommonUtil.nickNameChk(nickName, nickNotUse) == 1) {
			CommonUtil.jspAlert(response, "올바르지 않은 접속입니다." , "back", "");
			//System.out.println("(2) : ");
		}
		//SP2_MEMBER_DATA_INPUT3
		HashMap<String, Object> mParam = new HashMap<String, Object>();
		mParam.put("mem_country", country);
		mParam.put("mem_phone", userPhone);
		mParam.put("mem_pw", userPW_md5);
		mParam.put("mem_email", null);
		mParam.put("mem_name", "번역AI");
		mParam.put("mem_birth", null);
		mParam.put("mem_gender", null);
		mParam.put("mem_nickName", nickName);
		mParam.put("now_date", now);
		mParam.put("mem_chucode", chuCode);
		mParam.put("mem_type", 0);
		mParam.put("chu_phone", null);
		mParam.put("chu_photo", photo);

		mParam.put("host", "ko");
		mParam.put("nation", nation);
		mParam.put("lang", lang);

		HashMap<String, Object> join = memberService.setMemberJoinSp2(mParam);

		result_code = String.valueOf(join.get("RESULTCODE"));
		result_msg = String.valueOf(join.get("RESULTMSG"));
		//System.out.println("result_code : " + result_code);
		//System.out.println("result_msg : " + result_msg);


		if(result_code.equals("0")) {
			commonService.setJoinIpLog(request.getRemoteAddr());

			//최초 로그 기록
			int userSeq = memberService.getUserSeqByNick(nickName);

			int joinCount = memberService.getJoinLogCount(userSeq);

			if(joinCount == 0) {
				HashMap<String, Object> jParam = new HashMap<String, Object>();
				jParam.put("userSeq", userSeq);
				jParam.put("nation", nation.toLowerCase());
				jParam.put("lang", lang);
				jParam.put("nCount", 0);
				jParam.put("eCount", 0);

				memberService.addJoinFirstLog(jParam);
			}

			//System.out.println("(3) : ");
			CommonUtil.jspAlert(response, "" , "/default/joinOk_n2", "parent.parent");
		}
		else if(result_code.equals("1")) {
			//System.out.println("(4) : ");
			CommonUtil.jspAlert(response, "이미 회원에 가입되어 있으십니다." , "back", "");
		}
		else if(result_code.equals("2")) {
			//System.out.println("(5) : ");
			CommonUtil.jspAlert(response, "이미 회원에 가입되어 있으십니다." , "back", "");
		}
		else {
			CommonUtil.jspAlert(response, "가입 처리중 오류가 발생하였습니다.\\n잠시 후 다시 진행하여주세요. " + result_msg, "/default/main", "parent.parent");
		}
	}
	*/

}
