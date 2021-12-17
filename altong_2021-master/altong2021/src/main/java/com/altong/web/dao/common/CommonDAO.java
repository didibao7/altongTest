package com.altong.web.dao.common;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.altong.web.logic.NoticeVO;
import com.altong.web.logic.V2RankVO;
import com.altong.web.logic.almoney.AlmoneyVO;
import com.altong.web.logic.common.TranslateVO;
import com.altong.web.logic.member.MemberVO;
import com.altong.web.logic.member.PartnerVO;

@Repository
public class CommonDAO {
	@Autowired
	public SqlSession sqlSession;

	public ResultSet getAlmoneyStatisticsView() {
		return (ResultSet) sqlSession.selectList("getAlmoneyStatisticsView");
	}

	public double getAlmoneyAll() {
		return sqlSession.selectOne("getAlmoneyAll");
	}

	public Map<String, Object> getAlmoneyBySeq(int Seq) {
		return sqlSession.selectOne("getAlmoneyBySeq", Seq);
	}

	public List<Map<String, Object>> getAlarmBySeq(int Seq) {
		return sqlSession.selectList("getAlarmBySeq", Seq);
	}

	public void setCommonAlmoneyStatus() {
		sqlSession.update("setCommonAlmoneyStatus");
	}
	public void setCommonEarnStatus() {
		sqlSession.update("setCommonEarnStatus");
	}
	public void setCommonOrderStatus() {
		sqlSession.update("setCommonOrderStatus");
	}

	public int getMsgBySeq(int Seq) {
		return sqlSession.selectOne("getMsgBySeq", Seq);
	}

	public String getQueRegAlmoneyByLv(String col) {
		return sqlSession.selectOne("getQueRegAlmoneyByLv", col);
	}

	public Map<String, Object> getMemLoginCh6(Map<String, Object> param) {
		return sqlSession.selectOne("getMemLoginCh6", param);
	}

	public String getAuthority(int userSeq) {
		return sqlSession.selectOne("getAuthority", userSeq);
	}

	public void setLogView(HashMap<String, Object> param) {
		sqlSession.update("setLogView", param);
	}

	public void setQuestionReadCount(int seq) {
		sqlSession.update("setQuestionReadCount", seq);
	}

	public String getEventStatus(int seq) {
		return sqlSession.selectOne("getEventStatus", seq);
	}

	public List<AlmoneyVO> getExtraAlmoneyList(HashMap<String, Object> param) {
		return sqlSession.selectList("getExtraAlmoneyList", param);
	}

	public AlmoneyVO getSumExtraAlmoneyInfo(HashMap<String, Object> param) {
		return sqlSession.selectOne("getSumExtraAlmoneyInfo", param);
	}

	public AlmoneyVO getExtraAlmoneyListUserTime(HashMap<String, Object> param) {
		return sqlSession.selectOne("getExtraAlmoneyListUserTime", param);
	}

	public HashMap<String, Object> setAlmoneyProcess(HashMap<String, Object> param) {
		return sqlSession.selectOne("setAlmoneyProcess", param);
	}

	public void setIpLog(HashMap<String, Object> param) {
		sqlSession.selectOne("setIpLog", param);
	}

	public HashMap<String, Object> getFlagChoice(Long questionSeq) {
		return sqlSession.selectOne("getFlagChoice", questionSeq);
	}

	public void setLogViewByMultiParam(HashMap<String, Object> param) {
		sqlSession.update("setLogViewByMultiParam", param);
	}

	public void setLogAd(HashMap<String, Object> param) {
		sqlSession.update("setLogAd", param);
	}

	public void setQuestionTempSave(HashMap<String, Object> param) {
		sqlSession.update("setQuestionTempSave", param);
	}

	public HashMap<String, Object> getQuestionTemp(int userSeq) {
		return sqlSession.selectOne("getQuestionTemp", userSeq);
	}

	public void setAnswerTempSave(HashMap<String, Object> param) {
		sqlSession.update("setAnswerTempSave", param);
	}

	public String getAnswerTemp(HashMap<String, Object> param) {
		return sqlSession.selectOne("getAnswerTemp", param);
	}

	public int getQuestionMeberSeqBySeq(int questionSeq) {
		return sqlSession.selectOne("getQuestionMeberSeqBySeq", questionSeq);
	}

	public int getAnswerCntForAnswer(HashMap<String, Object> param) {
		return sqlSession.selectOne("getAnswerCntForAnswer", param);
	}

	public int getIsAnswered(HashMap<String, Object> param) {
		return sqlSession.selectOne("getIsAnswered", param);
	}

	public int getAnswerCnt2(HashMap<String, Object> param) {
		return sqlSession.selectOne("getAnswerCnt2", param);
	}

	public String getAnswerDateReg(int userSeq) {
		return sqlSession.selectOne("getAnswerDateReg", userSeq);
	}

	public void setQuestionTempDelete(HashMap<String, Object> param) {
		sqlSession.delete("setQuestionTempDelete", param);
	}

	public int getUserReplyCnt(HashMap<String, Object> param) {
		return sqlSession.selectOne("getUserReplyCnt", param);
	}

	public int getUserReplyCnt2(HashMap<String, Object> param) {
		return sqlSession.selectOne("getUserReplyCnt2", param);
	}

	public int getDateDiffSecondForReply(int userSeq) {
		return sqlSession.selectOne("getDateDiffSecondForReply", userSeq);
	}

	public String getSirenReporter(HashMap<String, Object> param) {
		return sqlSession.selectOne("getSirenReporter", param);
	}

	public HashMap<String, Object> setRegSiren(HashMap<String, Object> param) {
		return sqlSession.selectOne("setRegSiren", param);
	}

	public int setAlarmLog(HashMap<String, Object> param) {
		return sqlSession.selectOne("setAlarmLog", param);
	}

	public List<HashMap<String, Object>> getMyFavoriteCategory(int mem_seq) {
		return sqlSession.selectList("getMyFavoriteCategory", mem_seq);
	}

	public int getQuestionCntForQuestion(int userSeq) {
		return sqlSession.selectOne("getQuestionCntForQuestion", userSeq);
	}

	public int getQuestionCntForQuestion2(HashMap<String, Object> param) {
		return sqlSession.selectOne("getQuestionCntForQuestion2", param);
	}

	public int getQuestionDiff(int userSeq) {
		return sqlSession.selectOne("getQuestionDiff", userSeq);
	}

	public void setQuestionTempQueDelete(int userSeq) {
		sqlSession.delete("setQuestionTempQueDelete", userSeq);
	}

	public HashMap<String, Object> setChangeAlmoneySP(HashMap<String, Object> param) {
		return sqlSession.selectOne("setChangeAlmoneySP", param);
	}

	public HashMap<String, Object> getMyInfoLvList(HashMap<String, Object> param) {
		return sqlSession.selectOne("getMyInfoLvList", param);
	}

	public List<HashMap<String, Object>> getMemoLvUpReadySetSP(HashMap<String, Object> param) {
		return sqlSession.selectList("getMemoLvUpReadySetSP", param);
	}

	public List<HashMap<String, Object>> getMyInfoExchList(HashMap<String, Object> param) {
		return sqlSession.selectList("getMyInfoExchList", param);
	}

	public List<HashMap<String, Object>> getMemoExchReadySetCommSP(HashMap<String, Object> param) {
		return sqlSession.selectList("getMemoExchReadySetCommSP", param);
	}

	public HashMap<String, Object> getMyReqSirenCount(int userSeq) {
		return sqlSession.selectOne("getMyReqSirenCount", userSeq);
	}

	public HashMap<String, Object> getMyResSirenCount(int userSeq) {
		return sqlSession.selectOne("getMyResSirenCount", userSeq);
	}

	public HashMap<String, Object> getMyResSirenPoint(int userSeq) {
		return sqlSession.selectOne("getMyResSirenPoint", userSeq);
	}

	public int getMyPartnerCount(int userSeq) {
		return sqlSession.selectOne("getMyPartnerCount", userSeq);
	}

	public List<HashMap<String, Object>> getMyPartnerList(HashMap<String, Object> param) {
		return sqlSession.selectList("getMyPartnerList", param);
	}

	public int getBlockParterCount(int userSeq) {
		return sqlSession.selectOne("getBlockParterCount", userSeq);
	}

	public List<HashMap<String, Object>> getBlockPartnerList(HashMap<String, Object> param) {
		return sqlSession.selectList("getBlockPartnerList", param);
	}

	public void setMyBlockPartner(HashMap<String, Object> param) {
		sqlSession.insert("setMyBlockPartner", param);
	}

	public void deleteMyBlockPartner(HashMap<String, Object> param) {
		sqlSession.delete("deleteMyBlockPartner", param);
	}

	public int getMyBlockPartnerCount(int userSeq) {
		return sqlSession.selectOne("getMyBlockPartnerCount", userSeq);
	}

	public void deleteMyPartner(HashMap<String, Object> param) {
		sqlSession.delete("deleteMyPartner", param);
	}

	public int getMyMentorCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getMyMentorCount", param);
	}

	public List<PartnerVO> getMyMentorList(HashMap<String, Object> param) {
		return sqlSession.selectList("getMyMentorList", param);
	}

	public int getMyFollowerCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getMyFollowerCount", param);
	}

	public List<PartnerVO> getMyFollowerList(HashMap<String, Object> param) {
		return sqlSession.selectList("getMyFollowerList", param);
	}

	public int getAnswererCnt(int userSeq) {
		return sqlSession.selectOne("getAnswererCnt", userSeq);
	}

	public List<MemberVO> getAnswererInfo(int userSeq) {
		return sqlSession.selectList("getAnswererInfo", userSeq);
	}

	public List<HashMap<String, Object>> getInterestList(int userSeq) {
		return sqlSession.selectList("getInterestList", userSeq);
	}

	public List<HashMap<String, Object>> getMyFavorite(int userSeq) {
		return sqlSession.selectList("getMyFavorite", userSeq);
	}

	public void setMyInterest(HashMap<String, Object> param) {
		sqlSession.insert("setMyInterest", param);
	}

	public void deleteMyInterest(int seq) {
		sqlSession.delete("delMyInterest", seq);
	}

	public List<HashMap<String, Object>> getMyTempList(HashMap<String, Object> param) {
		return sqlSession.selectList("getMyTempList", param);
	}

	public List<HashMap<String, Object>> getMyBankDataSP(HashMap<String, Object> param) {
		return sqlSession.selectList("getMyBankDataSP", param);
	}

	public List<HashMap<String, Object>> getExchangeAsk(int userSeq) {
		return sqlSession.selectList("getExchangeAsk", userSeq);
	}

	public HashMap<String, Object> getAlmoneyExchangeInfo(int userSeq) {
		return sqlSession.selectOne("getAlmoneyExchangeInfo", userSeq);
	}

	public String getCertStatus(int userSeq) {
		return sqlSession.selectOne("getCertStatus", userSeq);
	}

	public void setJoinIpLog(String ipAddr) {
		sqlSession.insert("setJoinIpLog", ipAddr);
	}

	public int getNoticeCount(String lang) {
		return sqlSession.selectOne("getNoticeCount", lang);
	}

	public List<NoticeVO> getNoticeList(HashMap<String, Object> param) {
		return sqlSession.selectList("getNoticeList", param);
	}

	public int getNoticeAdmCount() {
		return sqlSession.selectOne("getNoticeAdmCount");
	}

	public List<NoticeVO> getNoticeAdmList(HashMap<String, Object> param) {
		return sqlSession.selectList("getNoticeAdmList", param);
	}

	public List<NoticeVO> getNoticeListBySeq(int seq) {
		return sqlSession.selectList("getNoticeListBySeq", seq);
	}

	public NoticeVO getNoticeBySeq(int seq) {
		return sqlSession.selectOne("getNoticeListBySeq", seq);
	}

	public void setNoticeCountBySeq(int seq) {
		sqlSession.update("setNoticeCountBySeq", seq);
	}

	public void setMembersCert(HashMap<String, Object> param) {
		sqlSession.insert("setMembersCert", param);
	}

	public List<HashMap<String, Object>> getExchWeekData(HashMap<String, Object> param) {
		return sqlSession.selectList("getExchWeekData", param);
	}

	public HashMap<String, Object> getExchIncreassStamp(HashMap<String, Object> param) {
		return sqlSession.selectOne("getExchIncreassStamp", param);
	}

	public int setEventMake(HashMap<String, Object> param) {
		return sqlSession.selectOne("setEventMake", param);
	}

	public int getMemoLvReadySetSP(int lv) {
		return sqlSession.selectOne("getMemoLvReadySetSP", lv);
	}

	public int getMemoLvReadyCntBySeq(int seq) {
		return sqlSession.selectOne("getMemoLvReadyCntBySeq", seq);
	}

	public List<HashMap<String, Object>> getMemoLvReadyAllBySeq(HashMap<String, Object> param) {
		return sqlSession.selectList("getMemoLvReadyAllBySeq", param);
	}

	public int getMemoLvReadyCntByLv(int lv) {
		return sqlSession.selectOne("getMemoLvReadyCntByLv", lv);
	}

	public List<HashMap<String, Object>> getMemoLvReadyAllByLv(HashMap<String, Object> param) {
		return sqlSession.selectList("getMemoLvReadyAllByLv", param);
	}

	public List<HashMap<String, Object>> getLvWeekData(HashMap<String, Object> param) {
		return sqlSession.selectList("getLvWeekData", param);
	}

	public List<HashMap<String, Object>> setEducation(HashMap<String, Object> param) {
		return sqlSession.selectList("setEducation", param);
	}

	public HashMap<String, Object> getLvIncreassStamp(HashMap<String, Object> param) {
		return sqlSession.selectOne("getLvIncreassStamp", param);
	}

	public void deleteNoticeBySeq(int seq) {
		sqlSession.delete("deleteNoticeBySeq", seq);
	}

	public void updateNoticeBySeq(HashMap<String, Object> param) {
		sqlSession.update("updateNoticeBySeq", param);
	}

	public void setNotice(HashMap<String, Object> param) {
		sqlSession.insert("setNotice", param);
	}

	public List<HashMap<String, Object>> setSirenLog(HashMap<String, Object> param) {
		return sqlSession.selectList("setSirenLog", param);
	}

	public HashMap<String, Object> setSirenDelete(HashMap<String, Object> param) {
		return sqlSession.selectOne("setSirenDelete", param);
	}

	public List<HashMap<String, Object>> getBlackList_Reporter() {
		return sqlSession.selectList("getBlackList_Reporter");
	}

	public List<HashMap<String, Object>> getBlackList_Siren() {
		return sqlSession.selectList("getBlackList_Siren");
	}

	public List<HashMap<String, Object>> getReportListAdm() {
		return sqlSession.selectList("getReportListAdm");
	}

	public List<HashMap<String, Object>> getReportListPgAdm(HashMap<String, Object> param) {
		return sqlSession.selectList("getReportListPgAdm", param);
	}

	public int getReportListPgCntAdm() {
		return sqlSession.selectOne("getReportListPgCntAdm");
	}

	public List<HashMap<String, Object>> getReporter(HashMap<String, Object> param) {
		return sqlSession.selectList("getReporter", param);
	}

	public List<HashMap<String, Object>> getReportListAjax(HashMap<String, Object> param) {
		return sqlSession.selectList("getReportListAjax", param);
	}

	public HashMap<String, Object> getReportDetailAdm(int reportSeq) {
		return sqlSession.selectOne("getReportDetailAdm", reportSeq);
	}

	public HashMap<String, Object> getReportDetailQue(int contentSeq) {
		return sqlSession.selectOne("getReportDetailQue", contentSeq);
	}

	public HashMap<String, Object> getReportDetailQueRepl(int contentSeq) {
		return sqlSession.selectOne("getReportDetailQueRepl", contentSeq);
	}

	public HashMap<String, Object> getReportDetailAns(int contentSeq) {
		return sqlSession.selectOne("getReportDetailAns", contentSeq);
	}

	public HashMap<String, Object> getReportDetailAnsRepl(int contentSeq) {
		return sqlSession.selectOne("getReportDetailAnsRepl", contentSeq);
	}

	public int getReportDetailAnsSeq(int contentSeq) {
		return sqlSession.selectOne("getReportDetailAnsSeq", contentSeq);
	}

	public void setReportAdm(HashMap<String, Object> param) {
		sqlSession.update("setReportAdm", param);
	}

	public void setReportChankAdm(HashMap<String, Object> param) {
		sqlSession.update("setReportChankAdm", param);
	}

	public int deleteReportContentSP(HashMap<String, Object> param) {
		return sqlSession.selectOne("deleteReportContentSP", param);
	}

	public BigDecimal getChkUseAlmoney(HashMap<String, Object> param) {
		return sqlSession.selectOne("getChkUseAlmoney", param);
	}

	public int getQuestionVoteCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getQuestionVoteCount", param);
	}

	public int getQuestionVoteCountSum(HashMap<String, Object> param) {
		return sqlSession.selectOne("getQuestionVoteCountSum", param);
	}

	public HashMap<String, Object> getQuestionVote(HashMap<String, Object> param) {
		return sqlSession.selectOne("getQuestionVote", param);
	}

	public HashMap<String, Object> getQuestionVoteSum(HashMap<String, Object> param) {
		return sqlSession.selectOne("getQuestionVoteSum", param);
	}

	public void addQuestionVote(HashMap<String, Object> param) {
		sqlSession.insert("addQuestionVote", param);
	}

	public void setQuestionVote(HashMap<String, Object> param) {
		sqlSession.update("setQuestionVote", param);
	}

	public String getContentsDateBySeq(HashMap<String, Object> param) {
		return sqlSession.selectOne("getContentsDateBySeq", param);
	}

	public int getTrnslateCntBySeq(HashMap<String, Object> param) {
		return sqlSession.selectOne("getTrnslateCntBySeq", param);
	}

	public TranslateVO getTrnslateBySeq(HashMap<String, Object> param) {
		return sqlSession.selectOne("getTrnslateBySeq", param);
	}

	public TranslateVO getTrnslateViewBySeq(HashMap<String, Object> param) {
		return sqlSession.selectOne("getTrnslateViewBySeq", param);
	}

	public void addTranslate(HashMap<String, Object> param) {
		sqlSession.insert("addTranslate", param);
	}

	public void setTranslate(HashMap<String, Object> param) {
		sqlSession.update("setTranslate", param);
	}

	public int getUserSeqForTrnslateByNick(String nickName) {
		return sqlSession.selectOne("getUserSeqForTrnslateByNick", nickName);
	}

	public V2RankVO getUserInfoBySeq(HashMap<String, Object> param) {
		return sqlSession.selectOne("getUserInfoBySeq", param);
	}

	public V2RankVO getQtUserInfoBySeq(HashMap<String, Object> param) {
		return sqlSession.selectOne("getQtUserInfoBySeq", param);
	}

	public void setTranslateCount(int seq) {
		sqlSession.update("setTranslateCount", seq);
	}

	public int getTrnslateTotalCntBySeq(HashMap<String, Object> param) {
		return sqlSession.selectOne("getTrnslateTotalCntBySeq", param);
	}

	public int getMachineTrnsCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getMachineTrnsCount", param);
	}

	public String getSingleTitle(HashMap<String, Object> param) {
		return sqlSession.selectOne("getSingleTitle", param);
	}

	public String getSingleConents(HashMap<String, Object> param) {
		return sqlSession.selectOne("getSingleConents", param);
	}

	public int getChangeQueAlmoneyCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getChangeQueAlmoneyCount", param);
	}
}
