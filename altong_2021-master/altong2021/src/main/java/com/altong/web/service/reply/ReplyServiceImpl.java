package com.altong.web.service.reply;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altong.web.dao.reply.ReplyDAO;
import com.altong.web.logic.reply.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService {
	@Autowired
	ReplyDAO replyDAO;

	@Override
	public int getQuestionSeqBySeq(int seq) {
		return replyDAO.getQuestionSeqBySeq(seq);
	}

	@Override
	public ReplyVO getAnswerSeqBySeq(int seq) {
		return replyDAO.getAnswerSeqBySeq(seq);
	}

	@Override
	public int getReplyQuestionCntBySeq(int seq) {
		return replyDAO.getReplyQuestionCntBySeq(seq);
	}

	@Override
	public List<ReplyVO> getReplyQuestionListBySeq(int seq) {
		return replyDAO.getReplyQuestionListBySeq(seq);
	}

	@Override
	public List<ReplyVO> getReplyList(HashMap<String, Object> param) {
		return replyDAO.getReplyList(param);
	}

	@Override
	public List<ReplyVO> getReplyListOrg(HashMap<String, Object> param) {
		return replyDAO.getReplyListOrg(param);
	}

	@Override
	public int getReplySum(HashMap<String, Object> param) {
		return replyDAO.getReplySum(param);
	}

	@Override
	public List<ReplyVO> getReplyListInfo(HashMap<String, Object> param) {
		return replyDAO.getReplyListInfo(param);
	}

	@Override
	public void setReply(HashMap<String, Object> param) {
		replyDAO.setReply(param);
	}

	@Override
	public void deleteReply(HashMap<String, Object> param) {
		replyDAO.deleteReply(param);
	}

	@Override
	public void deleteReplyQuestion(HashMap<String, Object> param) {
		replyDAO.deleteReplyQuestion(param);
	}

	@Override
	public void deleteReplyAnswer(HashMap<String, Object> param) {
		replyDAO.deleteReplyAnswer(param);
	}

	@Override
	public int getOtherReplyListCount(int userSeq) {
		return replyDAO.getOtherReplyListCount(userSeq);
	}

	@Override
	public List<ReplyVO> getOtherReplyListLimit(HashMap<String, Object> param) {
		return replyDAO.getOtherReplyListLimit(param);
	}

	@Override
	public int getReceivedReplyCount(int userSeq) {
		return replyDAO.getReceivedReplyCount(userSeq);
	}

	@Override
	public List<ReplyVO> getReceivedReplyLimit(HashMap<String, Object> param) {
		return replyDAO.getReceivedReplyLimit(param);
	}

	@Override
	public int getAnswerSumView(HashMap<String, Object> param) {
		return replyDAO.getAnswerSumView(param);
	}

	@Override
	public ReplyVO getReplyForTrnInfo(HashMap<String, Object> param) {
		return replyDAO.getReplyForTrnInfo(param);
	}

	@Override
	public ReplyVO getReplyInfo(HashMap<String, Object> param) {
		return replyDAO.getReplyInfo(param);
	}
}
