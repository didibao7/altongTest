package com.altong.web.service.answer;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import com.altong.web.logic.answer.AnswerVO;
import com.altong.web.logic.question.QuestionVO;

public interface AnswerService {
	int getAnserForQuestionSeqBySeq(int seq);
	HashMap<String, Object> getCountCAndCountN(int seq);
	HashMap<String, Object> getChoiceAndViewA();
	List<HashMap<String, Object>> fn_SQL_Answer_Section1();
	int getQuestionCount(HashMap<String, Object> param);
	int getSpQuestionCount(HashMap<String, Object> param);
	List<QuestionVO> getSpQuestionList(HashMap<String, Object> param);
	List<QuestionVO> getQuestion(HashMap<String, Object> param);
	HashMap<String, Object> setMoveQuestion(HashMap<String, Object> param);
	AnswerVO getQuestionAnswerViewSP(BigInteger answerSeq);
	AnswerVO getQuestionAnswerView(int answerSeq);
	void setAnswerReadUpdate(HashMap<String, Object> param);
	AnswerVO getAnswerView(HashMap<String, Object> param);
	AnswerVO getAnswerBySeq(int seq);
	int getAnswerMaxSeq();
	void setAnswer(HashMap<String, Object> param);
	void setAnswerUpdate(HashMap<String, Object> param);
	AnswerVO getAnswerChoiceInfo(HashMap<String, Object> param);
	HashMap<String, Object> setAnswerCancelOrDelete(HashMap<String, Object> param);
	void answerDelete(HashMap<String, Object> param);
	HashMap<String, Object> setAnswerEstimate(HashMap<String, Object> param);
	HashMap<String, Object> setAnswerChoice(HashMap<String, Object> param);
	int getAnswerCnt(int questionSeq);
	void deleteAnswerAsParams(HashMap<String, Object> param);

	int getMemberReceivedAnswerCount(HashMap<String, Object> param);

	List<AnswerVO> getMemberReceivedAnswerList(HashMap<String, Object> param);

	String getQuestionSeq(int seq);

	int getMyAnswerListCount(HashMap<String, Object> param);
	List<AnswerVO> getMyAnswerListLimit(HashMap<String, Object> param);

	int getMemberAlistCount(HashMap<String, Object> param);
	List<AnswerVO> getMemberAlistLimit(HashMap<String, Object> param);

	int getMemberVlistCount(HashMap<String, Object> param);
	List<AnswerVO> getMemberVlistLimit(HashMap<String, Object> param);
	AnswerVO getAnsInfoForTransBySeq(int seq);
	
	int getSpQuestionCountByLang(HashMap<String, Object> param);
	List<QuestionVO> getSpQuestionListByLang(HashMap<String, Object> param);
}
