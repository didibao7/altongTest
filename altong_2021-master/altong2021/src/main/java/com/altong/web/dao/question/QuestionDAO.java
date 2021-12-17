package com.altong.web.dao.question;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.altong.web.logic.V2RankVO;
import com.altong.web.logic.question.QuestionVO;

@Repository
public class QuestionDAO {
	@Autowired
	public SqlSession sqlSession;

	public List<QuestionVO> getQestionBestList(HashMap<String, Object> param) {
		return sqlSession.selectList("getQestionBestList", param);
	}

	public int getQuestionBestCnt(HashMap<String, Object> param) {
		return sqlSession.selectOne("getQuestionBestCnt", param);
	}

	public HashMap<String, Object> getAlmoneyOne() {
		return sqlSession.selectOne("getAlmoneyOne");
	}

	public int getEventCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getEventInQuestionCount", param);
	}
	public List<QuestionVO> getEventList(HashMap<String, Object> param) {
		return sqlSession.selectList("getEventListInQuestion", param);
	}

	public int getInterviewCount() {
		return sqlSession.selectOne("getInterviewCount");
	}
	public List<QuestionVO> getInterviewList(HashMap<String, Object> param) {
		return sqlSession.selectList("getInterviewList", param);
	}

	public String getBestNumberBySeq(int seq) {
		return sqlSession.selectOne("getBestNumberBySeq", seq);
	}

	public QuestionVO getInterview(int idx) {
		return sqlSession.selectOne("getInterview", idx);
	}

	public void plusPageView(int idx) {
		sqlSession.update("plusPageView", idx);
	}

	public List<HashMap<String, Object>> rankList() {
		return sqlSession.selectList("rankList");
	}

	public int getRankCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getRankCount", param);
	}
	public List<V2RankVO> getRankList(HashMap<String, Object> param) {
		return sqlSession.selectList("getRankList", param);
	}

	public QuestionVO getQuestionDetailBySeq(int contentSeq) {
		return sqlSession.selectOne("getQuestionDetailBySeq", contentSeq);
	}

	public void setBestChoiceMulti(HashMap<String, Object> param) {
		sqlSession.update("setBestChoiceMulti", param);
	}

	public void setBestChoiceZero(int questionSeq) {
		sqlSession.update("setBestChoiceZero", questionSeq);
	}

	public QuestionVO getQuestionInfoBySeq(HashMap<String, Object> param) {
		return sqlSession.selectOne("getQuestionInfoBySeq", param);
	}

	public int getQuestionMaxSeq() {
		return sqlSession.selectOne("getQuestionMaxSeq");
	}

	public void setQuestion(HashMap<String, Object> param) {
		sqlSession.insert("setQuestion", param);
	}

	public QuestionVO getMemberQuestionAlmoney(HashMap<String, Object> param) {
		return sqlSession.selectOne("getMemberQuestionAlmoney", param);
	}

	public void updateQuestionAdmin(HashMap<String, Object> param) {
		sqlSession.update("updateQuestionAdmin", param);
	}

	public void updateQuestion(HashMap<String, Object> param) {
		sqlSession.update("updateQuestion", param);
	}

	public int getQuestionNewSeq() {
		return sqlSession.selectOne("getQuestionNewSeq");
	}

	public void deleteQuestionAsParams(HashMap<String, Object> param) {
		sqlSession.delete("deleteQuestionAsParams", param);
	}

	public String deleteQuestionSpAsParams(HashMap<String, Object> param) {
		return sqlSession.selectOne("deleteQuestionSpAsParams", param);
	}

	public List<QuestionVO> getQestionSpList(HashMap<String, Object> param) {
		return sqlSession.selectList("getQestionSpList", param);
	}

	public int getSearchKeyWordChCount(String src_Text) {
		return sqlSession.selectOne("getSearchKeyWordChCount", src_Text);
	}

	public List<QuestionVO> getSearchKeyWordChData(HashMap<String, Object> keyParam) {
		return sqlSession.selectList("getSearchKeyWordChData", keyParam);
	}

	public int getSearchDataCnt(String search_str) {
		return sqlSession.selectOne("getSearchDataCnt", search_str);
	}

	public List<QuestionVO> getSearchData(HashMap<String, Object> param) {
		return sqlSession.selectList("getSearchData", param);
	}

	public int getMyQuestionListCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getMyQuestionListCount", param);
	}

	public List<QuestionVO> getMyQuestionListLimit(HashMap<String, Object> param) {
		return sqlSession.selectList("getMyQuestionListLimit", param);
	}

	public QuestionVO getGetQuestionSP3(String questionSeq) {
		return sqlSession.selectOne("getGetQuestionSP3", questionSeq);
	}

	public void updateQuestionAdminBySeq(HashMap<String, Object> param) {
		sqlSession.selectList("updateQuestionAdminBySeq", param);
	}

	public int getMemberQlistCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getMemberQlistCount", param);
	}

	public List<QuestionVO> getMemberQlistLimit(HashMap<String, Object> param) {
		return sqlSession.selectList("getMemberQlistLimit", param);
	}

	public int getQuestionCountAdm() {
		return sqlSession.selectOne("getQuestionCountAdm");
	}

	public List<HashMap<String, Object>> getQuestionAdm(int page) {
		return sqlSession.selectList("getQuestionAdm", page);
	}

	public void setCategoryQuestionAdm(HashMap<String, Object> param) {
		sqlSession.update("setCategoryQuestionAdm", param);
	}

	public int getQestionBestCount() {
		return sqlSession.selectOne("getQestionBestCount");
	}

	public QuestionVO getQueInfoForTransBySeq(int seq) {
		return sqlSession.selectOne("getQueInfoForTransBySeq", seq);
	}

	public QuestionVO getAnswerQuestionInfoN(int questionSeq) {
		return sqlSession.selectOne("getAnswerQuestionInfoN", questionSeq);
	}

	public void setChangeLangQuestion(HashMap<String, Object> param) {
		sqlSession.update("setChangeLangQuestion", param);
	}
}
