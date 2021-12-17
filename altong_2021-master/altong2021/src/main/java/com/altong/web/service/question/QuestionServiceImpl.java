package com.altong.web.service.question;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altong.web.dao.question.QuestionDAO;
import com.altong.web.logic.V2RankVO;
import com.altong.web.logic.question.QuestionVO;

@Service
public class QuestionServiceImpl implements QuestionService {
	@Autowired
	QuestionDAO questionDAO;

	@Override
	public int getQestionBestCount() {
		return questionDAO.getQestionBestCount();
	}

	@Override
	public List<QuestionVO> getQestionBestList(HashMap<String, Object> param) {
		return questionDAO.getQestionBestList(param);
	}

	@Override
	public int getQuestionBestCnt(HashMap<String, Object> param) {
		return questionDAO.getQuestionBestCnt(param);
	}

	@Override
	public HashMap<String, Object> getAlmoneyOne() {
		return questionDAO.getAlmoneyOne();
	}

	@Override
	public List<QuestionVO> getEventList(HashMap<String, Object> param) {
		return questionDAO.getEventList(param);
	}

	@Override
	public List<QuestionVO> getInterviewList(HashMap<String, Object> param) {
		return questionDAO.getInterviewList(param);
	}

	@Override
	public String getBestNumberBySeq(int seq) {
		return questionDAO.getBestNumberBySeq(seq);
	}

	@Override
	public QuestionVO getInterview(int idx) {
		return questionDAO.getInterview(idx);
	}

	@Override
	public void plusPageView(int idx) {
		questionDAO.plusPageView(idx);
	}

	@Override
	public List<HashMap<String, Object>> rankList() {
		return questionDAO.rankList();
	}

	@Override
	public List<V2RankVO> getRankList(HashMap<String, Object> param) {
		return questionDAO.getRankList(param);
	}

	@Override
	public QuestionVO getQuestionDetailBySeq(int contentSeq) {
		return questionDAO.getQuestionDetailBySeq(contentSeq);
	}

	@Override
	public void setBestChoiceMulti(HashMap<String, Object> param) {
		questionDAO.setBestChoiceMulti(param);
	}

	@Override
	public void setBestChoiceZero(int questionSeq) {
		questionDAO.setBestChoiceZero(questionSeq);
	}

	@Override
	public int getRankCount(HashMap<String, Object> param) {
		return questionDAO.getRankCount(param);
	}

	@Override
	public QuestionVO getQuestionInfoBySeq(HashMap<String, Object> param) {
		return questionDAO.getQuestionInfoBySeq(param);
	}

	@Override
	public int getQuestionMaxSeq() {
		return questionDAO.getQuestionMaxSeq();
	}

	@Override
	public void setQuestion(HashMap<String, Object> param) {
		questionDAO.setQuestion(param);
	}

	@Override
	public QuestionVO getMemberQuestionAlmoney(HashMap<String, Object> param) {
		return questionDAO.getMemberQuestionAlmoney(param);
	}

	@Override
	public void updateQuestionAdmin(HashMap<String, Object> param) {
		questionDAO.updateQuestionAdmin(param);
	}

	@Override
	public void updateQuestion(HashMap<String, Object> param) {
		questionDAO.updateQuestion(param);
	}

	@Override
	public int getQuestionNewSeq() {
		return questionDAO.getQuestionNewSeq();
	}

	@Override
	public void deleteQuestionAsParams(HashMap<String, Object> param) {
		questionDAO.deleteQuestionAsParams(param);
	}

	@Override
	public String deleteQuestionSpAsParams(HashMap<String, Object> param) {
		return questionDAO.deleteQuestionSpAsParams(param);
	}

	@Override
	public List<QuestionVO> getQestionSpList(HashMap<String, Object> param) {
		return questionDAO.getQestionSpList(param);
	}

	@Override
	public int getSearchKeyWordChCount(String src_Text) {
		return questionDAO.getSearchKeyWordChCount(src_Text);
	}

	@Override
	public List<QuestionVO> getSearchKeyWordChData(HashMap<String, Object> keyParam) {
		return questionDAO.getSearchKeyWordChData(keyParam);
	}

	@Override
	public int getSearchDataCnt(String search_str) {
		return questionDAO.getSearchDataCnt(search_str);
	}
	@Override
	public List<QuestionVO> getSearchData(HashMap<String, Object> param) {
		return questionDAO.getSearchData(param);
	}

	@Override
	public int getMyQuestionListCount(HashMap<String, Object> param) {
		return questionDAO.getMyQuestionListCount(param);
	}

	@Override
	public List<QuestionVO> getMyQuestionListLimit(HashMap<String, Object> param) {
		return questionDAO.getMyQuestionListLimit(param);
	}

	@Override
	public QuestionVO getGetQuestionSP3(String questionSeq) {
		return questionDAO.getGetQuestionSP3(questionSeq);
	}

	@Override
	public void updateQuestionAdminBySeq(HashMap<String, Object> param) {
		questionDAO.updateQuestionAdminBySeq(param);
	}

	@Override
	public int getMemberQlistCount(HashMap<String, Object> param) {
		return questionDAO.getMemberQlistCount(param);
	}

	@Override
	public List<QuestionVO> getMemberQlistLimit(HashMap<String, Object> param) {
		return questionDAO.getMemberQlistLimit(param);
	}

	@Override
	public int getQuestionCountAdm() {
		return questionDAO.getQuestionCountAdm();
	}

	@Override
	public List<HashMap<String, Object>> getQuestionAdm(int page) {
		return questionDAO.getQuestionAdm(page);
	}

	@Override
	public void setCategoryQuestionAdm(HashMap<String, Object> param) {
		questionDAO.setCategoryQuestionAdm(param);
	}

	@Override
	public int getEventCount(HashMap<String, Object> param) {
		return questionDAO.getEventCount(param);
	}

	@Override
	public int getInterviewCount() {
		return questionDAO.getInterviewCount();
	}

	@Override
	public QuestionVO getQueInfoForTransBySeq(int seq) {
		return questionDAO.getQueInfoForTransBySeq(seq);
	}

	@Override
	public QuestionVO getAnswerQuestionInfoN(int questionSeq) {
		return questionDAO.getAnswerQuestionInfoN(questionSeq);
	}

	@Override
	public void setChangeLangQuestion(HashMap<String, Object> param) {
		questionDAO.setChangeLangQuestion(param);
	}
}
