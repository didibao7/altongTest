package com.altong.web.service.reply;

import java.util.HashMap;
import java.util.List;

import com.altong.web.logic.reply.ReplyVO;

public interface ReplyService {
	int getQuestionSeqBySeq(int seq);
	ReplyVO getAnswerSeqBySeq(int seq);
	int getReplyQuestionCntBySeq(int seq);
	List<ReplyVO> getReplyQuestionListBySeq(int seq);
	List<ReplyVO> getReplyList(HashMap<String, Object> param);
	List<ReplyVO> getReplyListOrg(HashMap<String, Object> param);
	int getReplySum(HashMap<String, Object> param);
	List<ReplyVO> getReplyListInfo(HashMap<String, Object> param);
	void setReply(HashMap<String, Object> param);
	void deleteReply(HashMap<String, Object> param);
	void deleteReplyQuestion(HashMap<String, Object> param);
	void deleteReplyAnswer(HashMap<String, Object> param);

	int getOtherReplyListCount(int userSeq);
	List<ReplyVO> getOtherReplyListLimit(HashMap<String, Object> param);

	int getReceivedReplyCount(int userSeq);
	List<ReplyVO> getReceivedReplyLimit(HashMap<String, Object> param);
	int getAnswerSumView(HashMap<String, Object> param);

	ReplyVO getReplyForTrnInfo(HashMap<String, Object> param);

	ReplyVO getReplyInfo(HashMap<String, Object> param);
}
