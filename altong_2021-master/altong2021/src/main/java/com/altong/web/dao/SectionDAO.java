package com.altong.web.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.altong.web.logic.section.SectionVO;

@Repository
public class SectionDAO {
	@Autowired
	public SqlSession sqlSession;

	public List<SectionVO> getCategoryName(HashMap<String, Object> param) {
		return sqlSession.selectList("getCategoryName", param);
	}

	public List<SectionVO> getMyInterestExtGet() {
		return sqlSession.selectList("getMyInterestExtGet");
	}

	public List<SectionVO> getMyInterestSection1Get(String section) {
		return sqlSession.selectList("getMyInterestSection1Get", section);
	}

	public List<SectionVO> getMyInterestSection2Get(String section) {
		return sqlSession.selectList("getMyInterestSection2Get", section);
	}

	public List<SectionVO> getMyInterestSection3Get(String section) {
		return sqlSession.selectList("getMyInterestSection3Get", section);
	}

	public List<SectionVO> getMyInterestSection4Get(String section) {
		return sqlSession.selectList("getMyInterestSection4Get", section);
	}

	public List<SectionVO> getBoardSection1() {
		return sqlSession.selectList("getBoardSection1");
	}

	public List<SectionVO> getSectionCode() {
		return sqlSession.selectList("getSectionCode");
	}

	public void setConvCodeName(HashMap<String, Object> param) {
		sqlSession.update("setConvCodeName", param);
	}
}
