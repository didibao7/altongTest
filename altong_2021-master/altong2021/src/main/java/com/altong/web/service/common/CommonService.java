package com.altong.web.service.common;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.altong.web.logic.ChoiceVO;
import com.altong.web.logic.LogAlmoneyVO;
import com.altong.web.logic.NoticeVO;
import com.altong.web.logic.StatisticsSrchVO;
import com.altong.web.logic.StatisticsVO;
import com.altong.web.logic.V2RankVO;
import com.altong.web.logic.ad.AdVO;
import com.altong.web.logic.almoney.AlmoneyVO;
import com.altong.web.logic.common.TranslateVO;
import com.altong.web.logic.event.EventVO;
import com.altong.web.logic.member.MemberVO;
import com.altong.web.logic.member.PartnerVO;
import com.altong.web.logic.section.SectionVO;

public interface CommonService {
	ResultSet getAlmoneyStatisticsView();

	double getAlmoneyAll();
	Map<String, Object> getAlmoneyBySeq(int Seq);
	List<Map<String, Object>> getAlarmBySeq(int Seq);
	void setCommonAlmoneyStatus();
	void setCommonEarnStatus();
	void setCommonOrderStatus();
	int getMsgBySeq(int Seq);
	String getQueRegAlmoneyByLv(String col);

	Map<String, Object> getMemLoginCh6(Map<String, Object> param);
	String getAuthority(int userSeq);
	void setLogView(HashMap<String, Object> param);
	void setQuestionReadCount(int seq);
	String getEventStatus(int seq);
	List<AdVO> getAnswerAd(HashMap<String, Object> param);
	int getAnswerFilecount(int seq);
	List<AlmoneyVO> getExtraAlmoneyList(HashMap<String, Object> param);
	AlmoneyVO getSumExtraAlmoneyInfo(HashMap<String, Object> param);
	AlmoneyVO getExtraAlmoneyListUserTime(HashMap<String, Object> param);

	int getAnswerZimChk(HashMap<String, Object> param);
	void setAnswerZzim(HashMap<String, Object> param);
	HashMap<String, Object> setAlmoneyProcess(HashMap<String, Object> param);
	void setIpLog(HashMap<String, Object> param);

	HashMap<String, Object> getFlagChoice(Long questionSeq);
	void setLogViewByMultiParam(HashMap<String, Object> param);
	void setLogAd(HashMap<String, Object> param);


	LogAlmoneyVO getAnswerViewLog(HashMap<String, Object>param);
	void setQuestionTempSave(HashMap<String, Object> param);
	HashMap<String, Object> getQuestionTemp(int userSeq);
	void setAnswerTempSave(HashMap<String, Object> param);
	String getAnswerTemp(HashMap<String, Object> param);
	int getQuestionMeberSeqBySeq(int questionSeq);
	int getAnswerCntForAnswer(HashMap<String, Object> param);
	int getIsAnswered(HashMap<String, Object> param);
	int getAnswerCnt2(HashMap<String, Object> param);
	String getAnswerDateReg(int userSeq);

	void setQuestionTempDelete(HashMap<String, Object> param);
	int getUserReplyCnt(HashMap<String, Object> param);
	int getUserReplyCnt2(HashMap<String, Object> param);
	int getDateDiffSecondForReply(int userSeq);

	String getSirenReporter(HashMap<String, Object> param);
	HashMap<String, Object> setRegSiren(HashMap<String, Object> param);

	List<SectionVO> getCategoryName(HashMap<String, Object> param);

	int setAlarmLog(HashMap<String, Object> param);
	List<HashMap<String, Object>> getMyFavoriteCategory(int mem_seq);

	int getQuestionCntForQuestion(int userSeq);
	int getQuestionCntForQuestion2(HashMap<String, Object> param);
	int getQuestionDiff(int userSeq);
	void setQuestionTempQueDelete(int userSeq);
	HashMap<String, Object> setChangeAlmoneySP(HashMap<String, Object> param);

	ChoiceVO getAnswerQuestionSpChoice();

	List<SectionVO> getMyInterestExtGet();
	List<SectionVO> getMyInterestSection1Get(String section);
	List<SectionVO> getMyInterestSection2Get(String section);
	List<SectionVO> getMyInterestSection3Get(String section);
	List<SectionVO> getMyInterestSection4Get(String section);


	List<EventVO> getEventForHeader();

	AlmoneyVO getMemberLogAlmoneyTotal(int userSeq);
	AlmoneyVO getMemberLogAlmoney(int userSeq);

	HashMap<String, Object> getMyInfoLvList(HashMap<String, Object> param);

	List<HashMap<String, Object>> getMemoLvUpReadySetSP(HashMap<String, Object> param);

	List<HashMap<String, Object>> getMyInfoExchList(HashMap<String, Object> param);

	List<HashMap<String, Object>> getMemoExchReadySetCommSP(HashMap<String, Object> param);

	List<SectionVO> getBoardSection1();

	void deleteZzim(HashMap<String, Object> param);

	HashMap<String, Object> getMyReqSirenCount(int userSeq);

	HashMap<String, Object> getMyResSirenCount(int userSeq);

	HashMap<String, Object> getMyResSirenPoint(int userSeq);


	int getMyPartnerCount(int userSeq);
	List<HashMap<String, Object>> getMyPartnerList(HashMap<String, Object> param);
	int getBlockParterCount(int userSeq);
	List<HashMap<String, Object>> getBlockPartnerList(HashMap<String, Object> param);
	void setMyBlockPartner(HashMap<String, Object> param);
	void deleteMyBlockPartner(HashMap<String, Object> param);
	int getMyBlockPartnerCount(int userSeq);
	void deleteMyPartner(HashMap<String, Object> param);

	int getMyMentorCount(HashMap<String, Object> param);
	List<PartnerVO> getMyMentorList(HashMap<String, Object> param);
	int getMyFollowerCount(HashMap<String, Object> param);
	List<PartnerVO> getMyFollowerList(HashMap<String, Object> param);

	int getAnswererCnt(int userSeq);
	List<MemberVO> getAnswererInfo(int userSeq);
	BigDecimal getEarnAnswerer(int userSeq);
	List<HashMap<String, Object>> getInterestList(int userSeq);
	List<HashMap<String, Object>> getMyFavorite(int userSeq);
	void setMyInterest(HashMap<String, Object> param);
	void deleteMyInterest(int seq);

	List<HashMap<String, Object>> getMyTempList(HashMap<String, Object> param);
	LogAlmoneyVO getAlmoneyLogSum(HashMap<String, Object>param);
	List<HashMap<String, Object>> getMyBankDataSP(HashMap<String, Object> param);

	List<HashMap<String, Object>> getExchangeAsk(int userSeq);

	HashMap<String, Object> setExchange(HashMap<String, Object> param);

	HashMap<String, Object> getAlmoneyExchangeInfo(int userSeq);

	String getCertStatus(int userSeq);

	HashMap<String, Object> getAlmoneyLogTotal(int userSeq);

	int getOtherViewCount(HashMap<String, Object> param);
	List<LogAlmoneyVO> getOtherViewList(HashMap<String, Object> param);

	ChoiceVO getAnswerQuestionChoice(HashMap<String, Object> param);

	void setJoinIpLog(String ipAddr);

	int getNoticeCount(String lang);
	List<NoticeVO> getNoticeList(HashMap<String, Object> param);

	int getNoticeAdmCount();
	List<NoticeVO> getNoticeAdmList(HashMap<String, Object> param);

	List<NoticeVO> getNoticeListBySeq(int seq);
	void setNoticeCountBySeq(int seq);

	NoticeVO getNoticeBySeq(int seq);

	void setMembersCert(HashMap<String, Object> param);

	HashMap<String, Object> getAlpayLastAccount(int userSeq);

	int setAlpayExchange(HashMap<String, Object> param);

	StatisticsVO getStatData();
	StatisticsSrchVO getStatSearchData(HashMap<String, Object> param);

	int setUpdateAlmoney(HashMap<String, Object> param);

	HashMap<String, Object> getTotalAlmoney();

	List<HashMap<String, Object>> getExchWeekData(HashMap<String, Object> param);
	HashMap<String, Object> getExchIncreassStamp(HashMap<String, Object> param);

	List<SectionVO> getSectionCode();

	int setEventMake(HashMap<String, Object> param);

	int getAdLimiCount(HashMap<String, Object> param);

	List<AdVO> getAdLimitList(HashMap<String, Object> param);
	int getAdMaxCode();
	void setAd(HashMap<String, Object> param);
	AdVO getAdView(int seq);
	void updateAd(HashMap<String, Object> param);
	void deleteAd(int seq);

	int getAdmAlmoneyCount();
	List<LogAlmoneyVO> getAdmAlmoneyLimit(HashMap<String, Object> param);

	int getMemoLvReadySetSP(int lv);

	int getMemoLvReadyCntBySeq(int seq);

	List<HashMap<String, Object>> getMemoLvReadyAllBySeq(HashMap<String, Object> param);

	int getMemoLvReadyCntByLv(int lv);

	List<HashMap<String, Object>> getMemoLvReadyAllByLv(HashMap<String, Object> param);

	List<HashMap<String, Object>> getLvWeekData(HashMap<String, Object> param);

	List<HashMap<String, Object>> setEducation(HashMap<String, Object> param);

	HashMap<String, Object> getLvIncreassStamp(HashMap<String, Object> param);

	void deleteNoticeBySeq(int seq);
	void updateNoticeBySeq(HashMap<String, Object> param);
	void setNotice(HashMap<String, Object> param);

	List<HashMap<String, Object>> setSirenLog(HashMap<String, Object> param);
	HashMap<String, Object> setSirenDelete(HashMap<String, Object> param);
	List<HashMap<String, Object>> getBlackList_Reporter();
	List<HashMap<String, Object>> getBlackList_Siren();
	List<HashMap<String, Object>> getReportListAdm();
	List<HashMap<String, Object>> getReportListPgAdm(HashMap<String, Object> param);
	int getReportListPgCntAdm();
	List<HashMap<String, Object>> getReporter(HashMap<String, Object> param);

	List<HashMap<String, Object>> getReportListAjax(HashMap<String, Object> param);
	HashMap<String, Object> getReportDetailAdm(int reportSeq);
	HashMap<String, Object> getReportDetailQue(int contentSeq);
	HashMap<String, Object> getReportDetailQueRepl(int contentSeq);
	HashMap<String, Object> getReportDetailAns(int contentSeq);
	HashMap<String, Object> getReportDetailAnsRepl(int contentSeq);
	int getReportDetailAnsSeq(int contentSeq);
	void setReportAdm(HashMap<String, Object> param);
	void setReportChankAdm(HashMap<String, Object> param);
	int deleteReportContentSP(HashMap<String, Object> param);

	BigDecimal getChkUseAlmoney(HashMap<String, Object> param);

	int getQuestionVoteCount(HashMap<String, Object> param);
	HashMap<String, Object> getQuestionVote(HashMap<String, Object> param);
	int getQuestionVoteCountSum(HashMap<String, Object> param);
	HashMap<String, Object> getQuestionVoteSum(HashMap<String, Object> param);
	void addQuestionVote(HashMap<String, Object> param);
	void setQuestionVote(HashMap<String, Object> param);

	void setConvCodeName(HashMap<String, Object> param);

	// 룰렛 게임 구현 시작
	HashMap<String, Object> getTicketConfig();
	int getTicket(HashMap<String, Object> param);
	int getTicketCount(HashMap<String, Object> param);

	int getTicketStackCnt(int userSeq);
	HashMap<String, Object> getTicketStack(int userSeq);

	void setTickUse(HashMap<String, Object> param);

	void addTicket(HashMap<String, Object> param);
	void addTicketStack(HashMap<String, Object> param);
	void setAddTickStackUse(HashMap<String, Object> param);
	void setSubTickStackUse(HashMap<String, Object> param);
	// 룰렛 게임 구현 종료

	String getContentsDateBySeq(HashMap<String, Object> param);

	// 룰렛 통계 구현 시작
	int getRulTicketTotalCount();
	int getRulAlmoneyTotalSum();
	int getRulUserTotalCount();
	int getRulDisUseTotalCount();
	int getRulTicketUseTotalCount();

	HashMap<String, Object> getRulTicketLVCountS();
	HashMap<String, Object> getRulTicketALCountS();
	HashMap<String, Object> getRulUseUserCountS();
	HashMap<String, Object> getRulPartCountS();
	HashMap<String, Object> getRulPartMoneyCountS();

	List<HashMap<String, Object>> getTopStatistics(HashMap<String, Object> param);
	List<HashMap<String, Object>> getDateStatistics(HashMap<String, Object> param);

	HashMap<String, Object> getRulDatePartCountS(HashMap<String, Object> param);
	HashMap<String, Object> getRulPartDateMoneyCountS(HashMap<String, Object> param);


	int getRulDateTicketTotalCount(HashMap<String, Object> param);
	int getRulDateAlmoneyTotalSum(HashMap<String, Object> param);
	int getRulDateUserTotalCount(HashMap<String, Object> param);
	int getRulDateDisUseTotalCount(HashMap<String, Object> param);

	List<HashMap<String, Object>> getDateTopStatistics(HashMap<String, Object> param);

	List<HashMap<String, Object>> getChartStatistics();
	// 룰렛 통계 구현 종료
	// 번역 기능 구현 시작
	int getTrnslateCntBySeq(HashMap<String, Object> param);
	TranslateVO getTrnslateBySeq(HashMap<String, Object> param);
	TranslateVO getTrnslateViewBySeq(HashMap<String, Object> param);
	void addTranslate(HashMap<String, Object> param);
	void setTranslate(HashMap<String, Object> param);

	int getUserSeqForTrnslateByNick(String nickName);

	V2RankVO getUserInfoBySeq(HashMap<String, Object> param);
	V2RankVO getQtUserInfoBySeq(HashMap<String, Object> param);

	void setTranslateCount(int seq);

	int getTrnslateTotalCntBySeq(HashMap<String, Object> param);

	int getSaveEventSubCount(HashMap<String, Object> param);

	int getMachineTrnsCount(HashMap<String, Object> param);

	String getSingleTitle(HashMap<String, Object> param);
	String getSingleConents(HashMap<String, Object> param);
	// 번역 기능 구현 종료

	int getChangeQueAlmoneyCount(HashMap<String, Object> param);
}
