package com.altong.web.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.altong.web.logic.BoardVO;

@Repository
public class BoardDAO {
	@Autowired
	public SqlSession sqlSession;
	
	public List<BoardVO> getBoardList() {
		return sqlSession.selectList("getBoardList");
	}
	
	public int boardWrite(BoardVO board) {
		return sqlSession.insert("boardwrite",board);
	}
	
	public BoardVO getBoardDetail(int id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return sqlSession.selectOne("getBoardDetail", map);
	}
	
	public void viewUpdate(int id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		sqlSession.update("viewUpdate", map);
	}
}
