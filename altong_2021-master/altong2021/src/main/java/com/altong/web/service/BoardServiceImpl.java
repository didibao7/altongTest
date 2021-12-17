package com.altong.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altong.web.dao.BoardDAO;
import com.altong.web.logic.BoardVO;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardDAO boardDAO;
	
	public List<BoardVO> getBoardList() {
		return boardDAO.getBoardList();
	}
	
	@Override
	public int boardWrite(BoardVO board) {
		return boardDAO.boardWrite(board);
	}
	
	@Override
	public BoardVO getBoardDetail(int id) {
		return boardDAO.getBoardDetail(id);
	}

	@Override
	public void viewsUpdate(int id) {
		boardDAO.viewUpdate(id);
	}
	
}
