package com.altong.web.service;

import java.util.List;

import com.altong.web.logic.BoardVO;

public interface BoardService {
	List<BoardVO> getBoardList();
	
	int boardWrite(BoardVO board);
	
	BoardVO getBoardDetail(int id);
	void viewsUpdate(int id);
}
