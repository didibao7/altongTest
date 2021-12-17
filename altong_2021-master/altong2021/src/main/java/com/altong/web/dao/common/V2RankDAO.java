package com.altong.web.dao.common;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.altong.web.logic.V2RankVO;

@Repository
public class V2RankDAO {
	@Autowired
	public SqlSession sqlSession;

	public List<V2RankVO> getAnswerRank2(Map<String, Object> param) {
		return sqlSession.selectList("getAnswerRank2", param);
	}

	public List<V2RankVO> getPointRankByUserSeq(Map<String, Object> param) {
		return sqlSession.selectList("getPointRankByUserSeq", param);
	}

	public int getAnswerRankCount(Map<String, Object> param) {
		return sqlSession.selectOne("getAnswerRankCount", param);
	}

	public List<V2RankVO> getAnswerRank(Map<String, Object> param) {
		return sqlSession.selectList("getAnswerRank", param);
	}

	public List<V2RankVO> getPointRankByUserSeqTop3(Map<String, Object> param) {
		return sqlSession.selectList("getPointRankByUserSeqTop3", param);
	}
}
