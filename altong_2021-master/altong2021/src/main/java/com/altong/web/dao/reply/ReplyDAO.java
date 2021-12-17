package com.altong.web.dao.reply;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.altong.web.logic.reply.ReplyVO;

@Repository
public class ReplyDAO {
	@Autowired
	public SqlSession sqlSession;

	public int getQuestionSeqBySeq(int seq) {
		return sqlSession.selectOne("getQuestionSeqBySeq", seq);
	}

	public ReplyVO getAnswerSeqBySeq(int seq) {
		return sqlSession.selectOne("getAnswerSeqBySeq", seq);
	}

	public int getReplyQuestionCntBySeq(int seq) {
		return sqlSession.selectOne("getReplyQuestionCntBySeq", seq);
	}

	public List<ReplyVO> getReplyQuestionListBySeq(int seq) {
		return sqlSession.selectList("getReplyQuestionListBySeq", seq);
	}

	public List<ReplyVO> getReplyList(HashMap<String, Object> param) {
		return sqlSession.selectList("getReplyList", param);
	}

	public List<ReplyVO> getReplyListOrg(HashMap<String, Object> param) {
		return sqlSession.selectList("getReplyListOrg", param);
	}

	public int getReplySum(HashMap<String, Object> param) {
		return sqlSession.selectOne("getReplySum", param);
	}

	public List<ReplyVO> getReplyListInfo(HashMap<String, Object> param) {
		return sqlSession.selectList("getReplyListInfo", param);
	}

	public void setReply(HashMap<String, Object> param) {
		sqlSession.insert("setReply", param);
	}

	public void deleteReply(HashMap<String, Object> param) {
		sqlSession.update("deleteReply", param);
	}

	public void deleteReplyQuestion(HashMap<String, Object> param) {
		sqlSession.delete("deleteReplyQuestion", param);
	}

	public void deleteReplyAnswer(HashMap<String, Object> param) {
		sqlSession.delete("deleteReplyAnswer", param);
	}

	public int getOtherReplyListCount(int userSeq) {
		return sqlSession.selectOne("getOtherReplyListCount", userSeq);
	}

	public List<ReplyVO> getOtherReplyListLimit(HashMap<String, Object> param) {
		return sqlSession.selectList("getOtherReplyListLimit", param);
	}

	public int getReceivedReplyCount(int userSeq) {
		return sqlSession.selectOne("getReceivedReplyCount", userSeq);
	}

	public List<ReplyVO> getReceivedReplyLimit(HashMap<String, Object> param) {
		return sqlSession.selectList("getOtherReplyListLimit", param);
	}

	public int getAnswerSumView(HashMap<String, Object> param) {
		return sqlSession.selectOne("getAnswerSumViewForAnswer", param);
	}

	public ReplyVO getReplyForTrnInfo(HashMap<String, Object> param) {
		return sqlSession.selectOne("getReplyListInfo", param);
	}

	public ReplyVO getReplyInfo(HashMap<String, Object> param) {
		return sqlSession.selectOne("getReplyInfo", param);
	}
}
