package com.altong.web.service.question;

import java.util.HashMap;
import java.util.List;

import com.altong.web.logic.V2RankVO;
import com.altong.web.logic.question.QuestionVO;

public interface QuestionService {
	int getQestionBestCount();
	List<QuestionVO> getQestionBestList(HashMap<String, Object> param);
	int getQuestionBestCnt(HashMap<String, Object> param);
	HashMap<String, Object> getAlmoneyOne();
	List<QuestionVO> getEventList(HashMap<String, Object> param);
	List<QuestionVO> getInterviewList(HashMap<String, Object> param);
	QuestionVO getInterview(int idx);
	String getBestNumberBySeq(int seq);
	void plusPageView(int idx);
	List<HashMap<String, Object>> rankList();
	QuestionVO getQuestionDetailBySeq(int contentSeq);
	void setBestChoiceMulti(HashMap<String, Object> param);
	void setBestChoiceZero(int questionSeq);
	List<V2RankVO> getRankList(HashMap<String, Object> param);
	int getRankCount(HashMap<String, Object> param);
	QuestionVO getQuestionInfoBySeq(HashMap<String, Object> param);
	int getQuestionMaxSeq();
	void setQuestion(HashMap<String, Object> param);
	QuestionVO getMemberQuestionAlmoney(HashMap<String, Object> param);
	void updateQuestionAdmin(HashMap<String, Object> param);
	void updateQuestion(HashMap<String, Object> param);
	int getQuestionNewSeq();
	void deleteQuestionAsParams(HashMap<String, Object> param);
	String deleteQuestionSpAsParams(HashMap<String, Object> param);
	List<QuestionVO> getQestionSpList(HashMap<String, Object> param);
	int getSearchKeyWordChCount(String src_Text);
	List<QuestionVO> getSearchKeyWordChData(HashMap<String, Object> keyParam);

	int getSearchDataCnt(String search_str);
	List<QuestionVO> getSearchData(HashMap<String, Object> param);

	int getMyQuestionListCount(HashMap<String, Object> param);

	List<QuestionVO> getMyQuestionListLimit(HashMap<String, Object> param);

	QuestionVO getGetQuestionSP3(String questionSeq);

	void updateQuestionAdminBySeq(HashMap<String, Object> param);

	int getMemberQlistCount(HashMap<String, Object> param);
	List<QuestionVO> getMemberQlistLimit(HashMap<String, Object> param);
	int getQuestionCountAdm();
	List<HashMap<String, Object>> getQuestionAdm(int page);
	void setCategoryQuestionAdm(HashMap<String, Object> param);

	int getEventCount(HashMap<String, Object> param);

	int getInterviewCount();

	QuestionVO getQueInfoForTransBySeq(int seq);

	QuestionVO getAnswerQuestionInfoN(int questionSeq);

	void setChangeLangQuestion(HashMap<String, Object> param);
}
