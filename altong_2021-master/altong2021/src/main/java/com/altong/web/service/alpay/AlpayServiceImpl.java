package com.altong.web.service.alpay;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altong.web.dao.alpay.AlpayDAO;

@Service
public class AlpayServiceImpl implements AlpayService {
	@Autowired
	AlpayDAO alpayDao;
	
	public int getUserAlpayKR(int userSeq) {
		return alpayDao.getUserAlpayKR(userSeq);
	}
	
	public List<HashMap<String, Object>> getAlpayExchLimitBySeq(HashMap<String, Object> param) {
		return alpayDao.getAlpayExchLimitBySeq(param);
	}
	
	public List<HashMap<String, Object>> getAlpayExchLimitAll(HashMap<String, Object> param) {
		return alpayDao.getAlpayExchLimitAll(param);
	}
	
	public HashMap<String, Object> updateExch(HashMap<String, Object> param) {
		return alpayDao.updateExch(param);
	}
	
	public HashMap<String, Object> rejectExch(HashMap<String, Object> param) {
		return alpayDao.rejectExch(param);
	}
	
	public List<HashMap<String, Object>> getAlpayExchList() {
		return alpayDao.getAlpayExchList();
	}
	
	public HashMap<String, Object> changeExchangeAsk(HashMap<String, Object> param) {
		return alpayDao.changeExchangeAsk(param);
	}
	
	public void getMemoExchReadySetSP(int lv) {
		alpayDao.getMemoExchReadySetSP(lv);
	}
	
	public int getMemoExchReadyCntBySeq(int seq) {
		return alpayDao.getMemoExchReadyCntBySeq(seq);
	}
	
	public List<HashMap<String, Object>> getMemoExchReadyAllBySeq(HashMap<String, Object> param) {
		return alpayDao.getMemoExchReadyAllBySeq(param);
	}
	
	public int getMemoExchReadyCntByLv(int lv) {
		return alpayDao.getMemoExchReadyCntByLv(lv);
	}
	
	public List<HashMap<String, Object>> getMemoExchReadyAllByLv(HashMap<String, Object> param) {
		return alpayDao.getMemoExchReadyAllByLv(param);
	}
	
	public List<HashMap<String, Object>> getAlpayAdmLogBySeq(HashMap<String, Object> param) {
		return alpayDao.getAlpayAdmLogBySeq(param);
	}
	
	public List<HashMap<String, Object>> getAlpayAdmLogAll(HashMap<String, Object> param) {
		return alpayDao.getAlpayAdmLogAll(param);
	}
}
