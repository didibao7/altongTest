package com.altong.web.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.altong.web.logic.ad.AdVO;
import com.altong.web.logic.answer.AnswerVO;

@Repository
public class RestDAO {

	@Autowired
	public SqlSession sqlSession;

	public List<Map<String, Object>> getRestAnswerList(Map<String, Object> param){
		return sqlSession.selectList("getRestAnswerList", param);
	}

	public Map<String, Object> getRestAnswerEstimate(Map<String, Object> param){
		return sqlSession.selectOne("getRestAnswerEstimate", param);
	}

	//getOneRestQuestion
	public Map<String, Object> getOneRestQuestion(Map<String, Object> param){
		return sqlSession.selectOne("getOneRestQuestion", param);
	}

	public Map<String, Object> getRestUserHead(Map<String, Object> param){
		return sqlSession.selectOne("getRestUserHead", param);
	}

	public List<Map<String, Object>> getRestQeustionReplys(Map<String, Object> param){
		return sqlSession.selectList("getRestQeustionReplys", param);
	}

	public List<Map<String, Object>> getRestAnswerReplys(Map<String, Object> param){
		return sqlSession.selectList("getRestAnswerReplys", param);
	}

	public Map<String, Object> getRestVoteSum(Map<String, Object> param){
		return sqlSession.selectOne("getRestVoteSum", param);
	}

	public Map<String, Object> getRestAnswerExtraAlmoney(Map<String, Object> param){
		return sqlSession.selectOne("getRestAnswerExtraAlmoney", param);
	}

	public Map<String, Object> getRestQuestionExtraAlmoney(Map<String, Object> param){
		return sqlSession.selectOne("getRestQuestionExtraAlmoney", param);
	}

	public Map<String, Object> getRestBoardConfig(){
		return sqlSession.selectOne("getRestBoardConfig");
	}

	// user 정보에 포함되어 있기 때문에 써놓기만함.
	public int getRestTotalQuestionBenefit(int user) {
		final Map<String, Object> MemberSeq =new HashMap<String, Object>() {
			private static final long serialVersionUID = -510512058041234L;
			{
				put("MemberSeq", user );
			}
		};
		return sqlSession.selectOne("getRestTotalQuestionBenefit",MemberSeq);
	}
	public int getRestTotalAnswerBenefit(int user) {
		final Map<String, Object> MemberSeq =new HashMap<String, Object>() {
			private static final long serialVersionUID = -51347378041234L;
			{
				put("MemberSeq", user );
			}
		};
		return sqlSession.selectOne("getRestTotalAnswerBenefit",MemberSeq);
	}
	public int getRestTotalBenefit(int user) {
		final Map<String, Object> MemberSeq =new HashMap<String, Object>() {
			private static final long serialVersionUID = -5105120125215125L;
			{
				put("MemberSeq", user );
			}
		};
		return sqlSession.selectOne("getRestTotalBenefit",MemberSeq);
	}




	public int getRestTotalTkank(int user) {
		return sqlSession.selectOne("getRestTotalTkank",user);
	}
	public int getRestTotalGiveCount(int user) {
		return sqlSession.selectOne("getRestTotalGiveCount",user);
	}
	public int getRestTotalSelectedCount(int user) {
		return sqlSession.selectOne("getRestTotalSelectedCount",user);
	}
	public int getRestTotalAnswerCount(int user) {
		return sqlSession.selectOne("getRestTotalAnswerCount",user);
	}

	public Map<String, Object> getRestConvertSumExtraAlmoneyInfo(Map<String, Object> param){
		return sqlSession.selectOne("getRestConvertSumExtraAlmoneyInfo", param);
	}

	public String getRestUserLang(int user) {
		return sqlSession.selectOne("getRestUserLang",user);
	}

	public int getRestUserExtimate(Map<String, Object> param) {
		return sqlSession.selectOne("getRestUserExtimate",param);
	}

	public int getAnswerLogViewInfo(Map<String, Object> param) {
		return sqlSession.selectOne("getAnswerLogViewInfo", param);
	}

	public int getRestQuestionMemberSeq(int questionSeq) {
		return sqlSession.selectOne("getRestQuestionMemberSeq", questionSeq);
	}

	public AnswerVO getRestAnswerMemberInfo(int answerSeq) {
		return sqlSession.selectOne("getRestAnswerMemberInfo", answerSeq);
	}

	public AdVO getRestAnswerAd(Map<String, Object> param){
		return sqlSession.selectOne("getRestAnswerAd", param);
	}

	public int getRestAnsweredCheck(Map<String, Object> param){
		return sqlSession.selectOne("getRestAnsweredCheck", param);
	}
}
