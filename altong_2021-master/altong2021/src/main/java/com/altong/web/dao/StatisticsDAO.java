package com.altong.web.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.altong.web.logic.StatisticsSrchVO;
import com.altong.web.logic.StatisticsVO;

@Repository
public class StatisticsDAO {
	@Autowired
	public SqlSession sqlSession;
	
	public StatisticsVO getStatData() {
		return sqlSession.selectOne("getStatData");
	}
	
	public StatisticsSrchVO getStatSearchData(HashMap<String, Object> param) {
		return sqlSession.selectOne("getStatSearchData", param);
	}
}
