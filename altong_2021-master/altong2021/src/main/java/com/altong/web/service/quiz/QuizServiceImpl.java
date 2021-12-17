package com.altong.web.service.quiz;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altong.web.dao.quiz.QuizDAO;
import com.altong.web.logic.quiz.QuizAnsVO;
import com.altong.web.logic.quiz.QuizGameQueVO;
import com.altong.web.logic.quiz.QuizGameVO;
import com.altong.web.logic.quiz.QuizLogVO;
import com.altong.web.logic.quiz.QuizQueVO;

@Service
public class QuizServiceImpl implements QuizService {
	@Autowired
	QuizDAO quizDAO;

	@Override
	public int getQuizQueCount() {
		return quizDAO.getQuizQueCount();
	}

	@Override
	public List<QuizQueVO> getQuizQueList(HashMap<String, Object> param) {
		return quizDAO.getQuizQueList(param);
	}

	@Override
	public QuizQueVO getQuizQue(int uid) {
		return quizDAO.getQuizQue(uid);
	}

	@Override
	public void addQuizQue(HashMap<String, Object> param) {
		quizDAO.addQuizQue(param);
	}

	@Override
	public void updateQuizQue(HashMap<String, Object> param) {
		quizDAO.updateQuizQue(param);
	}

	@Override
	public void deleteOneQuizQue(int uid) {
		quizDAO.deleteOneQuizQue(uid);
	}

	@Override
	public List<QuizAnsVO> getQuizAnsList(int quid) {
		return quizDAO.getQuizAnsList(quid);
	}

	@Override
	public void addQuizAns(HashMap<String, Object> param) {
		quizDAO.addQuizAns(param);
	}

	@Override
	public void deleteAllQuizAns(int quid) {
		quizDAO.deleteAllQuizAns(quid);
	}

	@Override
	public void deleteOneQuizAns(int uid) {
		quizDAO.deleteOneQuizAns(uid);
	}

	@Override
	public String getQuizQueQuest(int uid) {
		return quizDAO.getQuizQueQuest(uid);
	}

	@Override
	public int getQuizGameCount() {
		return quizDAO.getQuizGameCount();
	}

	@Override
	public List<QuizGameVO> getQuizGameList(HashMap<String, Object> param) {
		return quizDAO.getQuizGameList(param);
	}

	@Override
	public QuizGameVO getQuizGame(int uid) {
		return quizDAO.getQuizGame(uid);
	}

	@Override
	public void addQuizGame(HashMap<String, Object> param) {
		quizDAO.addQuizGame(param);
	}

	@Override
	public void updateQuizGame(HashMap<String, Object> param) {
		quizDAO.updateQuizGame(param);
	}

	@Override
	public void deleteOneQuizGame(int uid) {
		quizDAO.deleteOneQuizGame(uid);
	}

	@Override
	public List<QuizGameQueVO> getQuizGameQueList(int quid) {
		return quizDAO.getQuizGameQueList(quid);
	}

	@Override
	public void addQuizGameQue(HashMap<String, Object> param) {
		quizDAO.addQuizGameQue(param);
	}

	@Override
	public void deleteAllQuizGameQue(int quid) {
		quizDAO.deleteAllQuizGameQue(quid);
	}

	@Override
	public void deleteOneQuizGameQue(int uid) {
		quizDAO.deleteOneQuizGameQue(uid);
	}

	@Override
	public int getCarryoverMoney() {
		return quizDAO.getCarryoverMoney();
	}

	@Override
	public int getQuizGameQueCount(int uid) {
		return quizDAO.getQuizGameQueCount(uid);
	}

	@Override
	public QuizGameQueVO getQuizGameQue(HashMap<String, Object> param) {
		return quizDAO.getQuizGameQue(param);
	}

	@Override
	public QuizLogVO getQuizLog(HashMap<String, Object> param) {
		return quizDAO.getQuizLog(param);
	}

	@Override
	public void setQuizLog(HashMap<String, Object> param) {
		quizDAO.setQuizLog(param);
	}

	@Override
	public void addQuizLog(HashMap<String, Object> param) {
		quizDAO.addQuizLog(param);
	}

	@Override
	public void addQuizReq(HashMap<String, Object> param) {
		quizDAO.addQuizReq(param);
	}

	@Override
	public List<QuizLogVO> getQuizLogAll(int gqid) {
		return quizDAO.getQuizLogAll(gqid);
	}

	@Override
	public int getQuizReqOne(HashMap<String, Object> param) {
		return quizDAO.getQuizReqOne(param);
	}

	@Override
	public void deleteQuizLog(HashMap<String, Object> param) {
		quizDAO.deleteQuizLog(param);
	}

	@Override
	public void deleteQuizReq(HashMap<String, Object> param) {
		quizDAO.deleteQuizReq(param);
	}

	@Override
	public int getQuizLogCnt(HashMap<String, Object> param) {
		return quizDAO.getQuizLogCnt(param);
	}

	@Override
	public int getQuizSuccessLogCount(HashMap<String, Object> param) {
		return quizDAO.getQuizSuccessLogCount(param);
	}

	@Override
	public List<QuizLogVO> getQuizSuccessLogList(HashMap<String, Object> param) {
		return quizDAO.getQuizSuccessLogList(param);
	}

	@Override
	public int getQuizReqLogCount(HashMap<String, Object> param) {
		return quizDAO.getQuizReqLogCount(param);
	}

	@Override
	public List<QuizLogVO> getQuizReqLogList(HashMap<String, Object> param) {
		return quizDAO.getQuizReqLogList(param);
	}

	@Override
	public List<QuizLogVO> getQuizSuccessLogListAll(HashMap<String, Object> param) {
		return quizDAO.getQuizSuccessLogListAll(param);
	}

	@Override
	public void updateQuizGameComplete(HashMap<String, Object> param) {
		quizDAO.updateQuizGameComplete(param);
	}

	@Override
	public void setQuizGameComplete(int uid) {
		quizDAO.setQuizGameComplete(uid);
	}

	@Override
	public int getCarryoverMoneyCnt() {
		return quizDAO.getCarryoverMoneyCnt();
	}

	@Override
	public int getQuizGameOnCountByUid(int uid) {
		return quizDAO.getQuizGameOnCountByUid(uid);
	}

	@Override
	public int getQuizGameOnCountByNonUid() {
		return quizDAO.getQuizGameOnCountByNonUid();
	}

	@Override
	public void setQuizGameEnd(int uid) {
		quizDAO.setQuizGameEnd(uid);
	}

	@Override
	public QuizGameVO getQuizGameLimit() {
		return quizDAO.getQuizGameLimit();
	}

	@Override
	public int getQuizReqCnt(HashMap<String, Object> param) {
		return quizDAO.getQuizReqCnt(param);
	}
}
