package com.altong.web.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altong.web.dao.MessageDAO;
import com.altong.web.logic.MessageVO;

@Service
public class MessageServiceImpl implements MessageService {
	@Autowired
	MessageDAO messageDAO;

	@Override
	public List<MessageVO> getReceiveMsg(HashMap<String, Object> param) {
		return messageDAO.getReceiveMsg(param);
	}

	@Override
	public List<MessageVO> getSendMsg(HashMap<String, Object> param) {
		return messageDAO.getSendMsg(param);
	}

	@Override
	public int setSendMsg(HashMap<String, Object> param) {
		return messageDAO.setSendMsg(param);
	}

	@Override
	public void setDelMsg(HashMap<String, Object> param) {
		messageDAO.setDelMsg(param);
	}

	@Override
	public void setReadMsg(HashMap<String, Object> param) {
		messageDAO.setReadMsg(param);
	}

	@Override
	public MessageVO getMsg(HashMap<String, Object> param) {
		return messageDAO.getMsg(param);
	}

	@Override
	public int getReceiverMsgSeqBegin(HashMap<String, Object> param) {
		return messageDAO.getReceiverMsgSeqBegin(param);
	}

	@Override
	public int getReceiverMsgSeqEnd(HashMap<String, Object> param) {
		return messageDAO.getReceiverMsgSeqEnd(param);
	}

	@Override
	public int getSendMsgSeqBegin(HashMap<String, Object> param) {
		return messageDAO.getSendMsgSeqBegin(param);
	}

	@Override
	public int getSendMsgSeqEnd(HashMap<String, Object> param) {
		return messageDAO.getSendMsgSeqEnd(param);
	}
}
