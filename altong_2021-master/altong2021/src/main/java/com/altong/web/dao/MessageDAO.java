package com.altong.web.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.altong.web.logic.MessageVO;

@Repository
public class MessageDAO {
	@Autowired
	public SqlSession sqlSession;


	public List<MessageVO> getReceiveMsg(HashMap<String, Object> param) {
		return sqlSession.selectList("getReceiveMsg", param);
	}

	public List<MessageVO> getSendMsg(HashMap<String, Object> param) {
		return sqlSession.selectList("getSendMsg", param);
	}

	public int setSendMsg(HashMap<String, Object> param) {
		return sqlSession.selectOne("setSendMsg", param);
	}


	public void setDelMsg(HashMap<String, Object> param) {
		sqlSession.update("setDelMsg", param);
	}

	public void setReadMsg(HashMap<String, Object> param) {
		sqlSession.update("setReadMsg", param);
	}

	public MessageVO getMsg(HashMap<String, Object> param) {
		return sqlSession.selectOne("getMsg", param);
	}

	public int getReceiverMsgSeqBegin(HashMap<String, Object> param) {
		return sqlSession.selectOne("getReceiverMsgSeqBegin", param);
	}

	public int getReceiverMsgSeqEnd(HashMap<String, Object> param) {
		return sqlSession.selectOne("getReceiverMsgSeqEnd", param);
	}

	public int getSendMsgSeqBegin(HashMap<String, Object> param) {
		return sqlSession.selectOne("getSendMsgSeqBegin", param);
	}

	public int getSendMsgSeqEnd(HashMap<String, Object> param) {
		return sqlSession.selectOne("getSendMsgSeqEnd", param);
	}
}
