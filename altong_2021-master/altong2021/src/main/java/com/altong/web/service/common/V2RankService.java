package com.altong.web.service.common;

import java.util.List;
import java.util.Map;

import com.altong.web.logic.V2RankVO;

public interface V2RankService {
	List<V2RankVO> getAnswerRank2(Map<String, Object> param);
	List<V2RankVO> getPointRankByUserSeq(Map<String, Object> param);
	int getAnswerRankCount(Map<String, Object> param);
	List<V2RankVO> getAnswerRank(Map<String, Object> param);

	List<V2RankVO> getPointRankByUserSeqTop3(Map<String, Object> param);
}
