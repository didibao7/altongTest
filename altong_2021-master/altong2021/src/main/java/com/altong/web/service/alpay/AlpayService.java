package com.altong.web.service.alpay;

import java.util.HashMap;
import java.util.List;

public interface AlpayService {
	int getUserAlpayKR(int userSeq);
	
	List<HashMap<String, Object>> getAlpayExchLimitBySeq(HashMap<String, Object> param);
	List<HashMap<String, Object>> getAlpayExchLimitAll(HashMap<String, Object> param);
	HashMap<String, Object> updateExch(HashMap<String, Object> param);
	HashMap<String, Object> rejectExch(HashMap<String, Object> param);
	List<HashMap<String, Object>> getAlpayExchList();
	HashMap<String, Object> changeExchangeAsk(HashMap<String, Object> param);
	void getMemoExchReadySetSP(int lv);
	int getMemoExchReadyCntBySeq(int seq);
	List<HashMap<String, Object>> getMemoExchReadyAllBySeq(HashMap<String, Object> param);
	int getMemoExchReadyCntByLv(int lv);
	List<HashMap<String, Object>> getMemoExchReadyAllByLv(HashMap<String, Object> param);
	List<HashMap<String, Object>> getAlpayAdmLogBySeq(HashMap<String, Object> param);
	List<HashMap<String, Object>> getAlpayAdmLogAll(HashMap<String, Object> param);
}
