package com.altong.web.service.file;

import java.util.HashMap;
import java.util.List;

import com.altong.web.logic.files.FileVO;

public interface FileService {
	String getFileNameBySeq(int seq);
	List<FileVO> getFile(HashMap<String, Object> param);
	List<FileVO> getBoardFile(HashMap<String, Object> param);
	int getBoardMaxFileSeq();
	void setBoardFile(HashMap<String, Object> param);
	List<FileVO> getQuestionFile(HashMap<String, Object> param);
	List<FileVO> getOldFiles(int seq);
}
