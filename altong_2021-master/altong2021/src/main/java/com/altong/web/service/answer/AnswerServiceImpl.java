package com.altong.web.service.answer;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altong.web.dao.answer.AnswerDAO;
import com.altong.web.logic.answer.AnswerVO;
import com.altong.web.logic.question.QuestionVO;

@Service
public class AnswerServiceImpl implements AnswerService {
	@Autowired
	AnswerDAO answerDAO;

	@Override
	public int getAnserForQuestionSeqBySeq(int seq) {
		return answerDAO.getAnserForQuestionSeqBySeq(seq);
	}

	@Override
	public HashMap<String, Object> getCountCAndCountN(int seq) {
		return answerDAO.getCountCAndCountN(seq);
	}

	@Override
	public HashMap<String, Object> getChoiceAndViewA() {
		return answerDAO.getChoiceAndViewA();
	}

	@Override
	public List<HashMap<String, Object>> fn_SQL_Answer_Section1() {
		return answerDAO.fn_SQL_Answer_Section1();
	}

	@Override
	public int getQuestionCount(HashMap<String, Object> param) {
		return answerDAO.getQuestionCount(param);
	}
	@Override
	public int getSpQuestionCount(HashMap<String, Object> param) {
		return answerDAO.getSpQuestionCount(param);
	}
	@Override
	public List<QuestionVO> getSpQuestionList(HashMap<String, Object> param) {
		return answerDAO.getSpQuestionList(param);
	}
	@Override
	public List<QuestionVO> getQuestion(HashMap<String, Object> param) {
		return answerDAO.getQuestion(param);
	}

	@Override
	public HashMap<String, Object> setMoveQuestion(HashMap<String, Object> param) {
		return answerDAO.setMoveQuestion(param);
	}

	@Override
	public AnswerVO getQuestionAnswerViewSP(BigInteger answerSeq) {
		return answerDAO.getQuestionAnswerViewSP(answerSeq);
	}

	@Override
	public AnswerVO getQuestionAnswerView(int answerSeq) {
		return answerDAO.getQuestionAnswerView(answerSeq);
	}

	@Override
	public void setAnswerReadUpdate(HashMap<String, Object> param) {
		answerDAO.setAnswerReadUpdate(param);
	}

	@Override
	public AnswerVO getAnswerView(HashMap<String, Object> param) {
		return answerDAO.getAnswerView(param);
	}

	@Override
	public AnswerVO getAnswerBySeq(int seq) {
		return answerDAO.getAnswerBySeq(seq);
	}

	@Override
	public int getAnswerMaxSeq() {
		return answerDAO.getAnswerMaxSeq();
	}

	@Override
	public void setAnswer(HashMap<String, Object> param) {
		answerDAO.setAnswer(param);
	}

	@Override
	public void setAnswerUpdate(HashMap<String, Object> param) {
		answerDAO.setAnswerUpdate(param);
	}

	@Override
	public AnswerVO getAnswerChoiceInfo(HashMap<String, Object> param) {
		return answerDAO.getAnswerChoiceInfo(param);
	}

	@Override
	public HashMap<String, Object> setAnswerCancelOrDelete(HashMap<String, Object> param) {
		return answerDAO.setAnswerCancelOrDelete(param);
	}

	@Override
	public void answerDelete(HashMap<String, Object> param) {
		answerDAO.answerDelete(param);
	}

	@Override
	public HashMap<String, Object> setAnswerEstimate(HashMap<String, Object> param) {
		return answerDAO.setAnswerEstimate(param);
	}

	@Override
	public HashMap<String, Object> setAnswerChoice(HashMap<String, Object> param) {
		return answerDAO.setAnswerChoice(param);
	}

	@Override
	public int getAnswerCnt(int questionSeq) {
		return answerDAO.getAnswerCnt(questionSeq);
	}

	@Override
	public void deleteAnswerAsParams(HashMap<String, Object> param) {
		answerDAO.deleteAnswerAsParams(param);
	}

	@Override
	public int getMemberReceivedAnswerCount(HashMap<String, Object> param) {
		return answerDAO.getMemberReceivedAnswerCount(param);
	}

	@Override
	public List<AnswerVO> getMemberReceivedAnswerList(HashMap<String, Object> param) {
		return answerDAO.getMemberReceivedAnswerList(param);
	}

	@Override
	public String getQuestionSeq(int seq) {
		return answerDAO.getQuestionSeq(seq);
	}

	@Override
	public int getMyAnswerListCount(HashMap<String, Object> param) {
		return answerDAO.getMyAnswerListCount(param);
	}

	@Override
	public List<AnswerVO> getMyAnswerListLimit(HashMap<String, Object> param) {
		return answerDAO.getMyAnswerListLimit(param);
	}

	@Override
	public int getMemberAlistCount(HashMap<String, Object> param) {
		return answerDAO.getMemberAlistCount(param);
	}

	@Override
	public List<AnswerVO> getMemberAlistLimit(HashMap<String, Object> param) {
		return answerDAO.getMemberAlistLimit(param);
	}

	@Override
	public int getMemberVlistCount(HashMap<String, Object> param) {
		return answerDAO.getMemberVlistCount(param);
	}

	@Override
	public List<AnswerVO> getMemberVlistLimit(HashMap<String, Object> param) {
		return answerDAO.getMemberVlistLimit(param);
	}

	@Override
	public AnswerVO getAnsInfoForTransBySeq(int seq) {
		return answerDAO.getAnsInfoForTransBySeq(seq);
	}
	
	public int getSpQuestionCountByLang(HashMap<String, Object> param) {
		return answerDAO.getSpQuestionCountByLang(param);
	}
	
	public List<QuestionVO> getSpQuestionListByLang(HashMap<String, Object> param) {
		return answerDAO.getSpQuestionListByLang(param);
	}
}
