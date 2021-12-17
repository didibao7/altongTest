package com.altong.web.dao.answer;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.altong.web.logic.answer.AnswerVO;
import com.altong.web.logic.question.QuestionVO;

@Repository
public class AnswerDAO {
	@Autowired
	public SqlSession sqlSession;

	public int getAnserForQuestionSeqBySeq(int seq) {
		return sqlSession.selectOne("getAnserForQuestionSeqBySeq", seq);
	}

	public HashMap<String, Object> getCountCAndCountN(int seq) {
		return sqlSession.selectOne("getCountCAndCountN", seq);
	}

	public HashMap<String, Object> getChoiceAndViewA(){
		return sqlSession.selectOne("getChoiceAndViewA");
	}
	public List<HashMap<String, Object>> fn_SQL_Answer_Section1() {
		return sqlSession.selectList("fn_SQL_Answer_Section1");
	}

	public int getQuestionCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getQuestionCount", param);
	}
	public int getSpQuestionCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getSpQuestionCount", param);
	}
	public List<QuestionVO> getSpQuestionList(HashMap<String, Object> param) {
		return sqlSession.selectList("getSpQuestionList", param);
	}
	public List<QuestionVO> getQuestion(HashMap<String, Object> param) {
		return sqlSession.selectList("getQuestion", param);
	}

	public HashMap<String, Object> setMoveQuestion(HashMap<String, Object> param) {
		return sqlSession.selectOne("setMoveQuestion", param);
	}

	public AnswerVO getQuestionAnswerViewSP(BigInteger answerSeq) {
		return sqlSession.selectOne("getQuestionAnswerViewSP", answerSeq);
	}

	public AnswerVO getQuestionAnswerView(int answerSeq) {
		return sqlSession.selectOne("getQuestionAnswerView", answerSeq);
	}

	public void setAnswerReadUpdate(HashMap<String, Object> param) {
		sqlSession.update("setAnswerReadUpdate", param);
	}

	public AnswerVO getAnswerView(HashMap<String, Object> param) {
		return sqlSession.selectOne("getAnswerView", param);
	}

	public AnswerVO getAnswerBySeq(int seq) {
		return sqlSession.selectOne("getAnswerBySeq", seq);
	}

	public int getAnswerMaxSeq() {
		return sqlSession.selectOne("getAnswerMaxSeq");
	}

	public void setAnswer(HashMap<String, Object> param) {
		sqlSession.insert("setAnswer", param);
	}

	public void setAnswerUpdate(HashMap<String, Object> param) {
		sqlSession.update("setAnswerUpdate", param);
	}

	public AnswerVO getAnswerChoiceInfo(HashMap<String, Object> param) {
		return sqlSession.selectOne("getAnswerChoiceInfo", param);
	}

	public HashMap<String, Object> setAnswerCancelOrDelete(HashMap<String, Object> param) {
		return sqlSession.selectOne("setAnswerCancelOrDelete", param);
	}

	public void answerDelete(HashMap<String, Object> param) {
		sqlSession.update("answerDelete", param);
	}

	public HashMap<String, Object> setAnswerEstimate(HashMap<String, Object> param) {
		return sqlSession.selectOne("setAnswerEstimate", param);
	}

	public HashMap<String, Object> setAnswerChoice(HashMap<String, Object> param) {
		return sqlSession.selectOne("setAnswerChoice", param);
	}

	public int getAnswerCnt(int questionSeq) {
		return sqlSession.selectOne("getAnswerCnt", questionSeq);
	}

	public void deleteAnswerAsParams(HashMap<String, Object> param) {
		sqlSession.delete("deleteAnswerAsParams", param);
	}

	public int getMemberReceivedAnswerCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getMemberReceivedAnswerCount", param);
	}
	public List<AnswerVO> getMemberReceivedAnswerList(HashMap<String, Object> param) {
		return sqlSession.selectList("getMemberReceivedAnswer", param);
	}

	public String getQuestionSeq(int seq) {
		return sqlSession.selectOne("getQuestionSeq", seq);
	}

	public int getMyAnswerListCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getMyAnswerListCount", param);
	}

	public List<AnswerVO> getMyAnswerListLimit(HashMap<String, Object> param) {
		return sqlSession.selectList("getMyAnswerListLimit", param);
	}

	public int getMemberAlistCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getMemberAdmAlistCount", param);
	}

	public List<AnswerVO> getMemberAlistLimit(HashMap<String, Object> param) {
		return sqlSession.selectList("getMemberAdmAlistLimit", param);
	}

	public int getMemberVlistCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getMemberAdmVlistCount", param);
	}

	public List<AnswerVO> getMemberVlistLimit(HashMap<String, Object> param) {
		return sqlSession.selectList("getMemberAdmVlistLimit", param);
	}

	public AnswerVO getAnsInfoForTransBySeq(int seq) {
		return sqlSession.selectOne("getAnsInfoForTransBySeq", seq);
	}
	
	public int getSpQuestionCountByLang(HashMap<String, Object> param) {
		return sqlSession.selectOne("getSpQuestionCountByLang", param);
	}
	public List<QuestionVO> getSpQuestionListByLang(HashMap<String, Object> param) {
		return sqlSession.selectList("getSpQuestionListByLang", param);
	}
}
