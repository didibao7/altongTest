package com.altong.web.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.altong.web.logic.files.FileVO;

@Repository
public class FileDAO {
	@Autowired
	public SqlSession sqlSession;
	
	public String getFileNameBySeq(int seq) {
		return sqlSession.selectOne("getFileNameBySeq", seq);
	}
	
	public int getAnswerFilecount(int seq) {
		return sqlSession.selectOne("getAnswerFilecount", seq);
	}
	
	public List<FileVO> getFile(HashMap<String, Object> param) {
		return sqlSession.selectList("getFile", param);
	}
	
	public List<FileVO> getBoardFile(HashMap<String, Object> param) {
		return sqlSession.selectList("getBoardFile", param);
	}
	
	public int getBoardMaxFileSeq() {
		return sqlSession.selectOne("getBoardMaxFileSeq");
	}
	
	public void setBoardFile(HashMap<String, Object> param) {
		sqlSession.insert("setBoardFile", param);
	}
	
	public List<FileVO> getQuestionFile(HashMap<String, Object> param) {
		return sqlSession.selectList("getQuestionFile", param);
	}
	
	public List<FileVO> getOldFiles(int seq) {
		return sqlSession.selectList("getOldFiles", seq);
	}
}
