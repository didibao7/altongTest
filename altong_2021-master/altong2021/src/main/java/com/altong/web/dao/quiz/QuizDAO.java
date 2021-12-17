package com.altong.web.dao.quiz;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.altong.web.logic.quiz.QuizAnsVO;
import com.altong.web.logic.quiz.QuizGameQueVO;
import com.altong.web.logic.quiz.QuizGameVO;
import com.altong.web.logic.quiz.QuizLogVO;
import com.altong.web.logic.quiz.QuizQueVO;

@Repository
public class QuizDAO {
	@Autowired
	public SqlSession sqlSession;

	public int getQuizQueCount() {
		return sqlSession.selectOne("getQuizQueCount");
	}

	public List<QuizQueVO> getQuizQueList(HashMap<String, Object> param) {
		return sqlSession.selectList("getQuizQueList", param);
	}

	public QuizQueVO getQuizQue(int uid) {
		return sqlSession.selectOne("getQuizQue", uid);
	}

	public void addQuizQue(HashMap<String, Object> param) {
		sqlSession.insert("addQuizQue", param);
	}

	public void updateQuizQue(HashMap<String, Object> param) {
		sqlSession.update("updateQuizQue", param);
	}

	public void deleteOneQuizQue(int uid) {
		sqlSession.delete("deleteOneQuizQue", uid);
	}

	public List<QuizAnsVO> getQuizAnsList(int quid) {
		return sqlSession.selectList("getQuizAnsList", quid);
	}

	public void addQuizAns(HashMap<String, Object> param) {
		sqlSession.insert("addQuizAns", param);
	}

	public void deleteAllQuizAns(int quid) {
		sqlSession.delete("deleteAllQuizAns", quid);
	}

	public void deleteOneQuizAns(int uid) {
		sqlSession.delete("deleteOneQuizAns", uid);
	}

	public int getQuizGameCount() {
		return sqlSession.selectOne("getQuizGameCount");
	}

	public List<QuizGameVO> getQuizGameList(HashMap<String, Object> param) {
		return sqlSession.selectList("getQuizGameList", param);
	}

	public QuizGameVO getQuizGame(int uid) {
		return sqlSession.selectOne("getQuizGame", uid);
	}

	public void addQuizGame(HashMap<String, Object> param) {
		sqlSession.insert("addQuizGame", param);
	}

	public void updateQuizGame(HashMap<String, Object> param) {
		sqlSession.update("updateQuizGame", param);
	}

	public void deleteOneQuizGame(int uid) {
		sqlSession.delete("deleteOneQuizGame", uid);
	}

	public List<QuizGameQueVO> getQuizGameQueList(int quid) {
		return sqlSession.selectList("getQuizGameQueList", quid);
	}

	public void addQuizGameQue(HashMap<String, Object> param) {
		sqlSession.insert("addQuizGameQue", param);
	}

	public void deleteAllQuizGameQue(int quid) {
		sqlSession.delete("deleteAllQuizGameQue", quid);
	}

	public void deleteOneQuizGameQue(int uid) {
		sqlSession.delete("deleteOneQuizGameQue", uid);
	}

	public String getQuizQueQuest(int uid) {
		return sqlSession.selectOne("getQuizQueQuest", uid);
	}

	public int getCarryoverMoney() {
		return sqlSession.selectOne("getCarryoverMoney");
	}

	public QuizAnsVO getQuizAns(int uid) {
		return sqlSession.selectOne("getQuizAns", uid);
	}

	public int getQuizGameQueCount(int uid) {
		return sqlSession.selectOne("getQuizGameQueCount", uid);
	}

	public QuizGameQueVO getQuizGameQue(HashMap<String, Object> param) {
		return sqlSession.selectOne("getQuizGameQue", param);
	}

	public QuizLogVO getQuizLog(HashMap<String, Object> param) {
		return sqlSession.selectOne("getQuizLog", param);
	}

	public void setQuizLog(HashMap<String, Object> param) {
		sqlSession.update("setQuizLog", param);
	}

	public void addQuizLog(HashMap<String, Object> param) {
		sqlSession.insert("addQuizLog", param);
	}

	public void addQuizReq(HashMap<String, Object> param) {
		sqlSession.insert("addQuizReq", param);
	}

	public List<QuizLogVO> getQuizLogAll(int gqid) {
		return sqlSession.selectList("getQuizLogAll", gqid);
	}

	public int getQuizReqOne(HashMap<String, Object> param) {
		return sqlSession.selectOne("getQuizReqOne", param);
	}

	public void deleteQuizLog(HashMap<String, Object> param) {
		sqlSession.delete("deleteQuizLog", param);
	}

	public void deleteQuizReq(HashMap<String, Object> param) {
		sqlSession.delete("deleteQuizReq", param);
	}

	public int getQuizLogCnt(HashMap<String, Object> param) {
		return sqlSession.selectOne("getQuizLogCnt", param);
	}

	public int getQuizSuccessLogCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getQuizSuccessLogCount", param);
	}

	public List<QuizLogVO> getQuizSuccessLogList(HashMap<String, Object> param) {
		return sqlSession.selectList("getQuizSuccessLogList", param);
	}

	public int getQuizReqLogCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getQuizReqLogCount", param);
	}

	public List<QuizLogVO> getQuizReqLogList(HashMap<String, Object> param) {
		return sqlSession.selectList("getQuizReqLogList", param);
	}

	public List<QuizLogVO> getQuizSuccessLogListAll(HashMap<String, Object> param) {
		return sqlSession.selectList("getQuizSuccessLogListAll", param);
	}

	public void updateQuizGameComplete(HashMap<String, Object> param) {
		sqlSession.update("updateQuizGameComplete", param);
	}

	public void setQuizGameComplete(int uid) {
		sqlSession.update("setQuizGameComplete", uid);
	}

	public int getCarryoverMoneyCnt() {
		return sqlSession.selectOne("getCarryoverMoneyCnt");
	}

	public int getQuizGameOnCountByUid(int uid) {
		return sqlSession.selectOne("getQuizGameOnCountByUid", uid);
	}

	public int getQuizGameOnCountByNonUid() {
		return sqlSession.selectOne("getQuizGameOnCountByNonUid");
	}

	public void setQuizGameEnd(int uid) {
		sqlSession.update("setQuizGameEnd", uid);
	}

	public QuizGameVO getQuizGameLimit() {
		return sqlSession.selectOne("getQuizGameLimit");
	}

	public int getQuizReqCnt(HashMap<String, Object> param) {
		return sqlSession.selectOne("getQuizReqCnt", param);
	}
}
