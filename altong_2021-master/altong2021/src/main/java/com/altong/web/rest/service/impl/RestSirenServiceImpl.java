package com.altong.web.rest.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altong.web.rest.service.RestCommonService;
import com.altong.web.rest.service.RestSirenService;
import com.altong.web.service.common.CommonService;
import com.google.common.collect.ImmutableMap;

@Service
public class RestSirenServiceImpl implements RestSirenService {
	
	@Autowired
	RestCommonService restCommonService;
	
	@Autowired
	CommonService commonService;
	
	// param = ACT, H_Type, H_Reason, H_Reason_txt
	@Override
	public Map<String, Object> restCommonSiren(Map<String, Object> param, Integer seq, HttpServletRequest request, HttpServletResponse response) {
		try {
			final int userSeq = restCommonService.getRestCookieUser(request, response);

			if(userSeq == 0) return ImmutableMap.of( "code", "notlogin" );

			String act = String.valueOf(param.get("ACT"));
			String hType = String.valueOf(param.get("H_Type"));
			String hReason = String.valueOf(param.get("H_Reason"));
			String hReasonTxt = String.valueOf(param.get("H_Reason_txt"));

			String table = "";
			switch(hType) {
				case "A" :
					table = "T_ANSWER";
					break;
				case "AR" :
					table = "T_REPLY_ANSWER";
					break;
				case "Q" :
					table = "T_QUESTION";
					break;
				case "QR" :
					table = "T_REPLY_QUESTION";
					break;
				case "T":
					table = "T_TRANSLATE";
					break;
				default :
					table = "";
			}


			if(act.equals("CheckSiren")) {
				HashMap<String, Object> param1 = new HashMap<String, Object>();
				param1.put("hType", hType);
				param1.put("hSeq", seq);
				param1.put("userSeq", userSeq);

				String sType = commonService.getSirenReporter(param1);

				if(sType != null) {
					return ImmutableMap.of( "msg", "이미 신고한 글입니다." );
				}
				else {
					return ImmutableMap.of( "msg", true );
				}
			}
			else if(act.equals("RegSiren")) {
				if(request.getParameter("H_Reason") == "" || request.getParameter("H_Reason") == null) {
					return ImmutableMap.of( "msg", "신고 사유를 선택하여주세요." );
				}

				HashMap<String, Object> param2 = new HashMap<String, Object>();
				param2.put("hType", hType);
				param2.put("hSeq", seq);
				param2.put("userSeq", userSeq);
				param2.put("hReason", hReason);
				param2.put("hReasonTxt", hReasonTxt);
				param2.put("table", table);

				HashMap<String, Object> regSiren = commonService.setRegSiren(param2);

				final String regSirenCode = String.valueOf(regSiren.get("ReturnCode"));

				switch(regSirenCode) {
					case "1":
						return ImmutableMap.of( "msg", "신고 처리가 완료되었습니다." );
					case "51":
						return ImmutableMap.of( "msg", "이미 신고한 글 입니다." );
					case "52":
						return ImmutableMap.of( "msg", "대상 글이 존재하지 않습니다." );
					case "53":
						return ImmutableMap.of( "msg", "본인의 글은 신고할 수 없습니다." );
					default:
						return ImmutableMap.of( "msg", "예상치 못한 오류가 발생하였습니다." );
				}
			}
			return ImmutableMap.of( "msg", "예상치 못한 오류가 발생하였습니다." );
		}
		catch(Exception e) {
			return ImmutableMap.of( "code", "error", "error", e );
		}
	}
}
