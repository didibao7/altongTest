package com.altong.web.service;

import java.util.HashMap;
import java.util.List;

import com.altong.web.logic.MessageVO;

public interface MessageService {
	List<MessageVO> getReceiveMsg(HashMap<String, Object> param);
	List<MessageVO> getSendMsg(HashMap<String, Object> param);
	int setSendMsg(HashMap<String, Object> param);
	void setDelMsg(HashMap<String, Object> param);
	void setReadMsg(HashMap<String, Object> param);
	MessageVO getMsg(HashMap<String, Object> param);

	int getReceiverMsgSeqBegin(HashMap<String, Object> param);
	int getReceiverMsgSeqEnd(HashMap<String, Object> param);
	int getSendMsgSeqBegin(HashMap<String, Object> param);
	int getSendMsgSeqEnd(HashMap<String, Object> param);
}
