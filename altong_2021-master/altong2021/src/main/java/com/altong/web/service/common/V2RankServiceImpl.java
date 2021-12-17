package com.altong.web.service.common;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altong.web.dao.common.V2RankDAO;
import com.altong.web.logic.V2RankVO;

@Service
public class V2RankServiceImpl implements V2RankService {
	@Autowired
	V2RankDAO v2rankDAO;

	@Override
	public List<V2RankVO> getAnswerRank2(Map<String, Object> param) {
		return v2rankDAO.getAnswerRank2( param);
	}

	@Override
	public List<V2RankVO> getPointRankByUserSeq(Map<String, Object> param) {
		return v2rankDAO.getPointRankByUserSeq( param);
	}

	@Override
	public int getAnswerRankCount(Map<String, Object> param) {
		return v2rankDAO.getAnswerRankCount( param);
	}

	@Override
	public List<V2RankVO> getAnswerRank(Map<String, Object> param) {
		return v2rankDAO.getAnswerRank( param);
	}

	@Override
	public List<V2RankVO> getPointRankByUserSeqTop3(Map<String, Object> param) {
		return v2rankDAO.getPointRankByUserSeqTop3( param);
	}
}
