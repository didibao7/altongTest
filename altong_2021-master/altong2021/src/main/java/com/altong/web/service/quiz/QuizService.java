package com.altong.web.service.quiz;

import java.util.HashMap;
import java.util.List;

import com.altong.web.logic.quiz.QuizAnsVO;
import com.altong.web.logic.quiz.QuizGameQueVO;
import com.altong.web.logic.quiz.QuizGameVO;
import com.altong.web.logic.quiz.QuizLogVO;
import com.altong.web.logic.quiz.QuizQueVO;

public interface QuizService {
	int getQuizQueCount();
	List<QuizQueVO> getQuizQueList(HashMap<String, Object> param);
	QuizQueVO getQuizQue(int uid);
	void addQuizQue(HashMap<String, Object> param);
	void updateQuizQue(HashMap<String, Object> param);
	void deleteOneQuizQue(int uid);
	List<QuizAnsVO> getQuizAnsList(int quid);
	void addQuizAns(HashMap<String, Object> param);
	void deleteAllQuizAns(int quid);
	void deleteOneQuizAns(int uid);
	String getQuizQueQuest(int uid);

	int getQuizGameCount();
	List<QuizGameVO> getQuizGameList(HashMap<String, Object> param);
	QuizGameVO getQuizGame(int uid);
	void addQuizGame(HashMap<String, Object> param);
	void updateQuizGame(HashMap<String, Object> param);
	void deleteOneQuizGame(int uid);
	List<QuizGameQueVO> getQuizGameQueList(int quid);
	void addQuizGameQue(HashMap<String, Object> param);
	void deleteAllQuizGameQue(int quid);
	void deleteOneQuizGameQue(int uid);

	int getCarryoverMoney();

	int getQuizGameQueCount(int uid);
	QuizGameQueVO getQuizGameQue(HashMap<String, Object> param);
	QuizLogVO getQuizLog(HashMap<String, Object> param);
	void setQuizLog(HashMap<String, Object> param);
	void addQuizLog(HashMap<String, Object> param);
	void addQuizReq(HashMap<String, Object> param);
	List<QuizLogVO> getQuizLogAll(int gqid);
	int getQuizReqOne(HashMap<String, Object> param);
	void deleteQuizLog(HashMap<String, Object> param);
	void deleteQuizReq(HashMap<String, Object> param);
	int getQuizLogCnt(HashMap<String, Object> param);
	int getQuizSuccessLogCount(HashMap<String, Object> param);
	List<QuizLogVO> getQuizSuccessLogList(HashMap<String, Object> param);
	int getQuizReqLogCount(HashMap<String, Object> param);
	List<QuizLogVO> getQuizReqLogList(HashMap<String, Object> param);
	List<QuizLogVO> getQuizSuccessLogListAll(HashMap<String, Object> param);
	void updateQuizGameComplete(HashMap<String, Object> param);
	void setQuizGameComplete(int uid);
	int getCarryoverMoneyCnt();

	int getQuizGameOnCountByUid(int uid);
	int getQuizGameOnCountByNonUid();
	void setQuizGameEnd(int uid);
	QuizGameVO getQuizGameLimit();
	int getQuizReqCnt(HashMap<String, Object> param);
}
