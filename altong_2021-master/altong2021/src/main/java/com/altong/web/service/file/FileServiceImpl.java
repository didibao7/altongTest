package com.altong.web.service.file;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altong.web.dao.FileDAO;
import com.altong.web.logic.files.FileVO;

@Service
public class FileServiceImpl implements FileService {
	@Autowired
	FileDAO fileDAO;
	
	public String getFileNameBySeq(int seq) {
		return fileDAO.getFileNameBySeq(seq);
	}
	
	public List<FileVO> getFile(HashMap<String, Object> param) {
		return fileDAO.getFile(param);
	}
	
	public List<FileVO> getBoardFile(HashMap<String, Object> param) {
		return fileDAO.getBoardFile(param);
	}
	
	public int getBoardMaxFileSeq() {
		return fileDAO.getBoardMaxFileSeq();
	}
	
	public void setBoardFile(HashMap<String, Object> param) {
		fileDAO.setBoardFile(param);
	}
	
	public List<FileVO> getQuestionFile(HashMap<String, Object> param) {
		return fileDAO.getQuestionFile(param);
	}
	
	public List<FileVO> getOldFiles(int seq) {
		return fileDAO.getOldFiles(seq);
	}
}
