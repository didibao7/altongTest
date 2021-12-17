package com.altong.web.service.common;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altong.web.dao.AlmoneyDAO;
import com.altong.web.dao.ChoiceDAO;
import com.altong.web.dao.EventDAO;
import com.altong.web.dao.FileDAO;
import com.altong.web.dao.MyZzimDAO;
import com.altong.web.dao.SectionDAO;
import com.altong.web.dao.StatisticsDAO;
import com.altong.web.dao.alpay.AlpayDAO;
import com.altong.web.dao.common.AdDAO;
import com.altong.web.dao.common.CommonDAO;
import com.altong.web.dao.common.LogAlmoneyDAO;
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

@Service
public class CommonServiceImpl implements CommonService {
	@Autowired
	CommonDAO commonDAO;

	@Autowired
	AdDAO adDAO;

	@Autowired
	FileDAO fileDAO;

	@Autowired
	MyZzimDAO myZzipDAO;

	@Autowired
	LogAlmoneyDAO logAlmoneyDAO;

	@Autowired
	SectionDAO sectionDAO;

	@Autowired
	ChoiceDAO choiceDAO;

	@Autowired
	EventDAO eventDAO;

	@Autowired
	AlmoneyDAO almoneyDAO;

	@Autowired
	AlpayDAO alpayDAO;

	@Autowired
	StatisticsDAO statisticsDAO;

	@Override
	public ResultSet getAlmoneyStatisticsView() {
		return commonDAO.getAlmoneyStatisticsView();
	}

	@Override
	public double getAlmoneyAll() {
		return commonDAO.getAlmoneyAll();
	}

	@Override
	public Map<String, Object> getAlmoneyBySeq(int Seq) {
		return commonDAO.getAlmoneyBySeq(Seq);
	}

	@Override
	public List<Map<String, Object>> getAlarmBySeq(int Seq) {
		return commonDAO.getAlarmBySeq(Seq);
	}

	@Override
	public void setCommonAlmoneyStatus() {
		commonDAO.setCommonAlmoneyStatus();
	}

	@Override
	public void setCommonEarnStatus() {
		commonDAO.setCommonEarnStatus();
	}

	@Override
	public void setCommonOrderStatus() {
		commonDAO.setCommonOrderStatus();
	}

	@Override
	public int getMsgBySeq(int Seq) {
		return commonDAO.getMsgBySeq(Seq);
	}

	@Override
	public String getQueRegAlmoneyByLv(String col) {
		return commonDAO.getQueRegAlmoneyByLv(col);
	}

	@Override
	public Map<String, Object> getMemLoginCh6(Map<String, Object> param) {
		return  commonDAO.getMemLoginCh6(param);
	}

	@Override
	public String getAuthority(int userSeq) {
		return commonDAO.getAuthority(userSeq);
	}

	@Override
	public void setLogView(HashMap<String, Object> param) {
		commonDAO.setLogView(param);
	}

	@Override
	public void setQuestionReadCount(int seq) {
		commonDAO.setQuestionReadCount(seq);
	}

	@Override
	public String getEventStatus(int seq) {
		return commonDAO.getAuthority(seq);
	}

	@Override
	public List<AdVO> getAnswerAd(HashMap<String, Object> param) {
		return adDAO.getAnswerAd(param);
	}

	@Override
	public int getAnswerFilecount(int seq) {
		return fileDAO.getAnswerFilecount(seq);
	}

	@Override
	public List<AlmoneyVO> getExtraAlmoneyList(HashMap<String, Object> param) {
		return commonDAO.getExtraAlmoneyList(param);
	}

	@Override
	public AlmoneyVO getSumExtraAlmoneyInfo(HashMap<String, Object> param) {
		return commonDAO.getSumExtraAlmoneyInfo(param);
	}

	@Override
	public AlmoneyVO getExtraAlmoneyListUserTime(HashMap<String, Object> param) {
		return commonDAO.getExtraAlmoneyListUserTime(param);
	}

	@Override
	public int getAnswerZimChk(HashMap<String, Object> param) {
		return myZzipDAO.getAnswerZimChk(param);
	}

	@Override
	public void setAnswerZzim(HashMap<String, Object> param) {
		myZzipDAO.setAnswerZzim(param);
	}

	@Override
	public HashMap<String, Object> setAlmoneyProcess(HashMap<String, Object> param) {
		return commonDAO.setAlmoneyProcess(param);
	}
	@Override
	public void setIpLog(HashMap<String, Object> param) {
		commonDAO.setIpLog(param);
	}

	@Override
	public HashMap<String, Object> getFlagChoice(Long questionSeq) {
		return commonDAO.getFlagChoice(questionSeq);
	}

	@Override
	public void setLogViewByMultiParam(HashMap<String, Object> param) {
		commonDAO.setLogViewByMultiParam(param);
	}

	@Override
	public void setLogAd(HashMap<String, Object> param) {
		commonDAO.setLogAd(param);
	}

	@Override
	public LogAlmoneyVO getAnswerViewLog(HashMap<String, Object>param) {
		return logAlmoneyDAO.getAnswerViewLog(param);
	}

	@Override
	public void setQuestionTempSave(HashMap<String, Object> param) {
		commonDAO.setQuestionTempSave(param);
	}

	@Override
	public HashMap<String, Object> getQuestionTemp(int userSeq) {
		return commonDAO.getQuestionTemp(userSeq);
	}

	@Override
	public void setAnswerTempSave(HashMap<String, Object> param) {
		commonDAO.setAnswerTempSave(param);
	}

	@Override
	public String getAnswerTemp(HashMap<String, Object> param) {
		return commonDAO.getAnswerTemp(param);
	}

	@Override
	public int getQuestionMeberSeqBySeq(int questionSeq) {
		return commonDAO.getQuestionMeberSeqBySeq(questionSeq);
	}

	@Override
	public int getAnswerCntForAnswer(HashMap<String, Object> param) {
		return commonDAO.getAnswerCntForAnswer(param);
	}

	@Override
	public int getIsAnswered(HashMap<String, Object> param) {
		return commonDAO.getIsAnswered(param);
	}

	@Override
	public int getAnswerCnt2(HashMap<String, Object> param) {
		return commonDAO.getAnswerCnt2(param);
	}

	@Override
	public String getAnswerDateReg(int userSeq) {
		return commonDAO.getAnswerDateReg(userSeq);
	}

	@Override
	public void setQuestionTempDelete(HashMap<String, Object> param) {
		commonDAO.setQuestionTempDelete(param);
	}

	@Override
	public int getUserReplyCnt(HashMap<String, Object> param) {
		return commonDAO.getUserReplyCnt(param);
	}

	@Override
	public int getUserReplyCnt2(HashMap<String, Object> param) {
		return commonDAO.getUserReplyCnt2(param);
	}

	@Override
	public int getDateDiffSecondForReply(int userSeq) {
		return commonDAO.getDateDiffSecondForReply(userSeq);
	}

	@Override
	public String getSirenReporter(HashMap<String, Object> param) {
		return commonDAO.getSirenReporter(param);
	}

	@Override
	public HashMap<String, Object> setRegSiren(HashMap<String, Object> param) {
		return commonDAO.setRegSiren(param);
	}

	@Override
	public List<SectionVO> getCategoryName(HashMap<String, Object> param) {
		return sectionDAO.getCategoryName(param);
	}

	@Override
	public int setAlarmLog(HashMap<String, Object> param) {
		return commonDAO.setAlarmLog(param);
	}

	@Override
	public List<HashMap<String, Object>> getMyFavoriteCategory(int mem_seq) {
		return commonDAO.getMyFavoriteCategory(mem_seq);
	}

	@Override
	public int getQuestionCntForQuestion(int userSeq) {
		return commonDAO.getQuestionCntForQuestion(userSeq);
	}

	@Override
	public int getQuestionCntForQuestion2(HashMap<String, Object> param) {
		return commonDAO.getQuestionCntForQuestion2(param);
	}

	@Override
	public int getQuestionDiff(int userSeq) {
		return commonDAO.getQuestionDiff(userSeq);
	}

	@Override
	public void setQuestionTempQueDelete(int userSeq) {
		commonDAO.setQuestionTempQueDelete(userSeq);
	}

	@Override
	public HashMap<String, Object> setChangeAlmoneySP(HashMap<String, Object> param) {
		return commonDAO.setChangeAlmoneySP(param);
	}

	@Override
	public ChoiceVO getAnswerQuestionSpChoice() {
		return choiceDAO.getAnswerQuestionSpChoice();
	}

	@Override
	public List<SectionVO> getMyInterestExtGet() {
		return sectionDAO.getMyInterestExtGet();
	}

	@Override
	public List<SectionVO> getMyInterestSection1Get(String section) {
		return sectionDAO.getMyInterestSection1Get(section);
	}

	@Override
	public List<SectionVO> getMyInterestSection2Get(String section) {
		return sectionDAO.getMyInterestSection2Get(section);
	}

	@Override
	public List<SectionVO> getMyInterestSection3Get(String section) {
		return sectionDAO.getMyInterestSection3Get(section);
	}

	@Override
	public List<SectionVO> getMyInterestSection4Get(String section) {
		return sectionDAO.getMyInterestSection4Get(section);
	}

	@Override
	public List<EventVO> getEventForHeader() {
		return eventDAO.getEventForHeader();
	}

	@Override
	public AlmoneyVO getMemberLogAlmoneyTotal(int userSeq) {
		return almoneyDAO.getMemberLogAlmoneyTotal(userSeq);
	}

	@Override
	public AlmoneyVO getMemberLogAlmoney(int userSeq) {
		return almoneyDAO.getMemberLogAlmoney(userSeq);
	}

	@Override
	public HashMap<String, Object> getMyInfoLvList(HashMap<String, Object> param) {
		return commonDAO.getMyInfoLvList(param);
	}

	@Override
	public List<HashMap<String, Object>> getMemoLvUpReadySetSP(HashMap<String, Object> param) {
		return commonDAO.getMemoLvUpReadySetSP(param);
	}

	@Override
	public List<HashMap<String, Object>> getMyInfoExchList(HashMap<String, Object> param) {
		return commonDAO.getMyInfoExchList(param);
	}

	@Override
	public List<HashMap<String, Object>> getMemoExchReadySetCommSP(HashMap<String, Object> param) {
		return commonDAO.getMemoExchReadySetCommSP(param);
	}

	@Override
	public List<SectionVO> getBoardSection1() {
		return sectionDAO.getBoardSection1();
	}

	@Override
	public void deleteZzim(HashMap<String, Object> param) {
		myZzipDAO.deleteZzim(param);
	}

	@Override
	public HashMap<String, Object> getMyReqSirenCount(int userSeq) {
		return commonDAO.getMyReqSirenCount(userSeq);
	}

	@Override
	public HashMap<String, Object> getMyResSirenCount(int userSeq) {
		return commonDAO.getMyResSirenCount(userSeq);
	}

	@Override
	public HashMap<String, Object> getMyResSirenPoint(int userSeq) {
		return commonDAO.getMyResSirenPoint(userSeq);
	}

	@Override
	public int getMyPartnerCount(int userSeq) {
		return commonDAO.getMyPartnerCount(userSeq);
	}

	@Override
	public List<HashMap<String, Object>> getMyPartnerList(HashMap<String, Object> param) {
		return commonDAO.getMyPartnerList(param);
	}

	@Override
	public int getBlockParterCount(int userSeq) {
		return commonDAO.getBlockParterCount(userSeq);
	}

	@Override
	public List<HashMap<String, Object>> getBlockPartnerList(HashMap<String, Object> param) {
		return commonDAO.getBlockPartnerList(param);
	}

	@Override
	public void setMyBlockPartner(HashMap<String, Object> param) {
		commonDAO.setMyBlockPartner(param);
	}

	@Override
	public void deleteMyBlockPartner(HashMap<String, Object> param) {
		commonDAO.deleteMyBlockPartner(param);
	}

	@Override
	public int getMyBlockPartnerCount(int userSeq) {
		return commonDAO.getMyBlockPartnerCount(userSeq);
	}

	@Override
	public void deleteMyPartner(HashMap<String, Object> param) {
		commonDAO.deleteMyPartner(param);
	}

	@Override
	public int getMyMentorCount(HashMap<String, Object> param) {
		return commonDAO.getMyMentorCount(param);
	}

	@Override
	public List<PartnerVO> getMyMentorList(HashMap<String, Object> param) {
		return commonDAO.getMyMentorList(param);
	}

	@Override
	public int getMyFollowerCount(HashMap<String, Object> param) {
		return commonDAO.getMyFollowerCount(param);
	}

	@Override
	public List<PartnerVO> getMyFollowerList(HashMap<String, Object> param) {
		return commonDAO.getMyFollowerList(param);
	}

	@Override
	public int getAnswererCnt(int userSeq) {
		return commonDAO.getAnswererCnt(userSeq);
	}

	@Override
	public List<MemberVO> getAnswererInfo(int userSeq) {
		return commonDAO.getAnswererInfo(userSeq);
	}

	@Override
	public BigDecimal getEarnAnswerer(int userSeq) {
		return almoneyDAO.getEarnAnswerer(userSeq);
	}

	@Override
	public List<HashMap<String, Object>> getInterestList(int userSeq) {
		return commonDAO.getInterestList(userSeq);
	}

	@Override
	public List<HashMap<String, Object>> getMyFavorite(int userSeq) {
		return commonDAO.getMyFavorite(userSeq);
	}

	@Override
	public void setMyInterest(HashMap<String, Object> param) {
		commonDAO.setMyInterest(param);
	}

	@Override
	public void deleteMyInterest(int seq) {
		commonDAO.deleteMyInterest(seq);
	}

	@Override
	public List<HashMap<String, Object>> getMyTempList(HashMap<String, Object> param) {
		return commonDAO.getMyTempList(param);
	}

	@Override
	public LogAlmoneyVO getAlmoneyLogSum(HashMap<String, Object>param) {
		return logAlmoneyDAO.getAlmoneyLogSum(param);
	}

	@Override
	public List<HashMap<String, Object>> getMyBankDataSP(HashMap<String, Object> param) {
		return commonDAO.getMyBankDataSP(param);
	}

	@Override
	public List<HashMap<String, Object>> getExchangeAsk(int userSeq) {
		return commonDAO.getExchangeAsk(userSeq);
	}

	@Override
	public HashMap<String, Object> setExchange(HashMap<String, Object> param) {
		return almoneyDAO.setExchange(param);
	}

	@Override
	public HashMap<String, Object> getAlmoneyExchangeInfo(int userSeq) {
		return commonDAO.getAlmoneyExchangeInfo(userSeq);
	}

	@Override
	public String getCertStatus(int userSeq) {
		return commonDAO.getCertStatus(userSeq);
	}

	@Override
	public HashMap<String, Object> getAlmoneyLogTotal(int userSeq) {
		return logAlmoneyDAO.getAlmoneyLogTotal(userSeq);
	}

	@Override
	public int getOtherViewCount(HashMap<String, Object> param) {
		return logAlmoneyDAO.getOtherViewCount(param);
	}

	@Override
	public List<LogAlmoneyVO> getOtherViewList(HashMap<String, Object> param) {
		return logAlmoneyDAO.getOtherViewList(param);
	}

	@Override
	public ChoiceVO getAnswerQuestionChoice(HashMap<String, Object> param) {
		return choiceDAO.getAnswerQuestionChoice(param);
	}

	@Override
	public void setJoinIpLog(String ipAddr) {
		commonDAO.setJoinIpLog(ipAddr);
	}

	@Override
	public int getNoticeCount(String lang) {
		return commonDAO.getNoticeCount(lang);
	}

	@Override
	public List<NoticeVO> getNoticeList(HashMap<String, Object> param) {
		return commonDAO.getNoticeList(param);
	}

	@Override
	public int getNoticeAdmCount() {
		return commonDAO.getNoticeAdmCount();
	}

	@Override
	public List<NoticeVO> getNoticeAdmList(HashMap<String, Object> param) {
		return commonDAO.getNoticeAdmList(param);
	}

	@Override
	public List<NoticeVO> getNoticeListBySeq(int seq) {
		return commonDAO.getNoticeListBySeq(seq);
	}

	@Override
	public NoticeVO getNoticeBySeq(int seq) {
		return commonDAO.getNoticeBySeq(seq);
	}

	@Override
	public void setNoticeCountBySeq(int seq) {
		commonDAO.setNoticeCountBySeq(seq);
	}

	@Override
	public void setMembersCert(HashMap<String, Object> param) {
		commonDAO.setMembersCert(param);
	}

	@Override
	public HashMap<String, Object> getAlpayLastAccount(int userSeq) {
		return alpayDAO.getAlpayLastAccount(userSeq);
	}

	@Override
	public int setAlpayExchange(HashMap<String, Object> param) {
		return alpayDAO.setAlpayExchange(param);
	}

	@Override
	public StatisticsVO getStatData() {
		return statisticsDAO.getStatData();
	}

	@Override
	public StatisticsSrchVO getStatSearchData(HashMap<String, Object> param) {
		return statisticsDAO.getStatSearchData(param);
	}

	@Override
	public int setUpdateAlmoney(HashMap<String, Object> param) {
		return almoneyDAO.setUpdateAlmoney(param);
	}

	@Override
	public HashMap<String, Object> getTotalAlmoney() {
		return logAlmoneyDAO.getTotalAlmoney();
	}

	@Override
	public List<HashMap<String, Object>> getExchWeekData(HashMap<String, Object> param) {
		return commonDAO.getExchWeekData(param);
	}

	@Override
	public HashMap<String, Object> getExchIncreassStamp(HashMap<String, Object> param) {
		return commonDAO.getExchIncreassStamp(param);
	}

	@Override
	public List<SectionVO> getSectionCode() {
		return sectionDAO.getSectionCode();
	}

	@Override
	public int setEventMake(HashMap<String, Object> param) {
		return commonDAO.setEventMake(param);
	}

	@Override
	public int getAdLimiCount(HashMap<String, Object> param) {
		return adDAO.getAdLimiCount(param);
	}

	@Override
	public List<AdVO> getAdLimitList(HashMap<String, Object> param) {
		return adDAO.getAdLimitList(param);
	}

	@Override
	public int getAdMaxCode() {
		return adDAO.getAdMaxCode();
	}

	@Override
	public void setAd(HashMap<String, Object> param) {
		adDAO.setAd(param);
	}

	@Override
	public AdVO getAdView(int seq) {
		return adDAO.getAdView(seq);
	}

	@Override
	public void updateAd(HashMap<String, Object> param) {
		adDAO.updateAd(param);
	}

	@Override
	public void deleteAd(int seq) {
		adDAO.deleteAd(seq);
	}

	@Override
	public int getAdmAlmoneyCount() {
		return logAlmoneyDAO.getAdmAlmoneyCount();
	}

	@Override
	public List<LogAlmoneyVO> getAdmAlmoneyLimit(HashMap<String, Object> param) {
		return logAlmoneyDAO.getAdmAlmoneyLimit(param);
	}

	@Override
	public int getMemoLvReadySetSP(int lv) {
		return commonDAO.getMemoLvReadySetSP(lv);
	}

	@Override
	public int getMemoLvReadyCntBySeq(int seq) {
		return commonDAO.getMemoLvReadyCntBySeq(seq);
	}

	@Override
	public List<HashMap<String, Object>> getMemoLvReadyAllBySeq(HashMap<String, Object> param) {
		return commonDAO.getMemoLvReadyAllBySeq(param);
	}

	@Override
	public int getMemoLvReadyCntByLv(int lv) {
		return commonDAO.getMemoLvReadyCntByLv(lv);
	}

	@Override
	public List<HashMap<String, Object>> getMemoLvReadyAllByLv(HashMap<String, Object> param) {
		return commonDAO.getMemoLvReadyAllByLv(param);
	}

	@Override
	public List<HashMap<String, Object>> getLvWeekData(HashMap<String, Object> param) {
		return commonDAO.getLvWeekData(param);
	}

	@Override
	public List<HashMap<String, Object>> setEducation(HashMap<String, Object> param) {
		return commonDAO.setEducation(param);
	}

	@Override
	public HashMap<String, Object> getLvIncreassStamp(HashMap<String, Object> param) {
		return commonDAO.getLvIncreassStamp(param);
	}

	@Override
	public void deleteNoticeBySeq(int seq) {
		commonDAO.deleteNoticeBySeq(seq);
	}

	@Override
	public void updateNoticeBySeq(HashMap<String, Object> param) {
		commonDAO.updateNoticeBySeq(param);
	}

	@Override
	public void setNotice(HashMap<String, Object> param) {
		commonDAO.setNotice(param);
	}

	@Override
	public List<HashMap<String, Object>> setSirenLog(HashMap<String, Object> param) {
		return commonDAO.setSirenLog(param);
	}

	@Override
	public HashMap<String, Object> setSirenDelete(HashMap<String, Object> param) {
		return commonDAO.setSirenDelete(param);
	}

	@Override
	public List<HashMap<String, Object>> getBlackList_Reporter() {
		return commonDAO.getBlackList_Reporter();
	}

	@Override
	public List<HashMap<String, Object>> getBlackList_Siren() {
		return commonDAO.getBlackList_Siren();
	}

	@Override
	public List<HashMap<String, Object>> getReportListAdm() {
		return commonDAO.getReportListAdm();
	}

	@Override
	public List<HashMap<String, Object>> getReportListPgAdm(HashMap<String, Object> param) {
		return commonDAO.getReportListPgAdm(param);
	}

	@Override
	public int getReportListPgCntAdm() {
		return commonDAO.getReportListPgCntAdm();
	}

	@Override
	public List<HashMap<String, Object>> getReporter(HashMap<String, Object> param) {
		return commonDAO.getReporter(param);
	}

	@Override
	public List<HashMap<String, Object>> getReportListAjax(HashMap<String, Object> param) {
		return commonDAO.getReportListAjax(param);
	}

	@Override
	public HashMap<String, Object> getReportDetailAdm(int reportSeq) {
		return commonDAO.getReportDetailAdm(reportSeq);
	}

	@Override
	public HashMap<String, Object> getReportDetailQue(int contentSeq) {
		return commonDAO.getReportDetailQue(contentSeq);
	}

	@Override
	public HashMap<String, Object> getReportDetailQueRepl(int contentSeq) {
		return commonDAO.getReportDetailQueRepl(contentSeq);
	}

	@Override
	public HashMap<String, Object> getReportDetailAns(int contentSeq) {
		return commonDAO.getReportDetailAns(contentSeq);
	}

	@Override
	public HashMap<String, Object> getReportDetailAnsRepl(int contentSeq) {
		return commonDAO.getReportDetailAnsRepl(contentSeq);
	}

	@Override
	public int getReportDetailAnsSeq(int contentSeq) {
		return commonDAO.getReportDetailAnsSeq(contentSeq);
	}

	@Override
	public void setReportAdm(HashMap<String, Object> param) {
		commonDAO.setReportAdm(param);
	}

	@Override
	public void setReportChankAdm(HashMap<String, Object> param) {
		commonDAO.setReportChankAdm(param);
	}

	@Override
	public int deleteReportContentSP(HashMap<String, Object> param) {
		return commonDAO.deleteReportContentSP(param);
	}

	@Override
	public BigDecimal getChkUseAlmoney(HashMap<String, Object> param) {
		return commonDAO.getChkUseAlmoney(param);
	}



	@Override
	public int getQuestionVoteCount(HashMap<String, Object> param) {
		return commonDAO.getQuestionVoteCount(param);
	}

	@Override
	public HashMap<String, Object> getQuestionVote(HashMap<String, Object> param) {
		return commonDAO.getQuestionVote(param);
	}

	@Override
	public int getQuestionVoteCountSum(HashMap<String, Object> param) {
		return commonDAO.getQuestionVoteCountSum(param);
	}

	@Override
	public HashMap<String, Object> getQuestionVoteSum(HashMap<String, Object> param) {
		return commonDAO.getQuestionVoteSum(param);
	}

	@Override
	public void addQuestionVote(HashMap<String, Object> param) {
		commonDAO.addQuestionVote(param);
	}

	@Override
	public void setQuestionVote(HashMap<String, Object> param) {
		commonDAO.setQuestionVote(param);
	}

	@Override
	public void setConvCodeName(HashMap<String, Object> param) {
		sectionDAO.setConvCodeName(param);
	}

	@Override
	public HashMap<String, Object> getTicketConfig() {
		return eventDAO.getTicketConfig();
	}

	@Override
	public int getTicket(HashMap<String, Object> param) {
		return eventDAO.getTicket(param);
	}

	@Override
	public int getTicketCount(HashMap<String, Object> param) {
		return eventDAO.getTicketCount(param);
	}

	@Override
	public int getTicketStackCnt(int userSeq) {
		return eventDAO.getTicketStackCnt(userSeq);
	}

	@Override
	public HashMap<String, Object> getTicketStack(int userSeq) {
		return eventDAO.getTicketStack(userSeq);
	}

	@Override
	public void setTickUse(HashMap<String, Object> param) {
		eventDAO.setTickUse(param);
	}

	@Override
	public void addTicket(HashMap<String, Object> param) {
		eventDAO.addTicket(param);
	}

	@Override
	public void addTicketStack(HashMap<String, Object> param) {
		eventDAO.addTicketStack(param);
	}

	@Override
	public void setAddTickStackUse(HashMap<String, Object> param) {
		eventDAO.setAddTickStackUse(param);
	}

	@Override
	public void setSubTickStackUse(HashMap<String, Object> param) {
		eventDAO.setSubTickStackUse(param);
	}

	@Override
	public String getContentsDateBySeq(HashMap<String, Object> param) {
		return commonDAO.getContentsDateBySeq(param);
	}

	@Override
	public int getRulTicketTotalCount() {
		return eventDAO.getRulTicketTotalCount();
	}

	@Override
	public int getRulAlmoneyTotalSum() {
		return eventDAO.getRulAlmoneyTotalSum();
	}

	@Override
	public int getRulUserTotalCount() {
		return eventDAO.getRulUserTotalCount();
	}

	@Override
	public int getRulDisUseTotalCount() {
		return eventDAO.getRulDisUseTotalCount();
	}

	@Override
	public int getRulTicketUseTotalCount() {
		return eventDAO.getRulTicketUseTotalCount();
	}

	@Override
	public HashMap<String, Object> getRulTicketLVCountS() {
		return eventDAO.getRulTicketLVCountS();
	}

	@Override
	public HashMap<String, Object> getRulTicketALCountS() {
		return eventDAO.getRulTicketALCountS();
	}

	@Override
	public HashMap<String, Object> getRulUseUserCountS() {
		return eventDAO.getRulUseUserCountS();
	}

	@Override
	public HashMap<String, Object> getRulPartCountS() {
		return eventDAO.getRulPartCountS();
	}

	@Override
	public HashMap<String, Object> getRulPartMoneyCountS() {
		return eventDAO.getRulPartMoneyCountS();
	}


	@Override
	public List<HashMap<String, Object>>  getTopStatistics(HashMap<String, Object> param) {
		return eventDAO.getTopStatistics(param);
	}

	@Override
	public List<HashMap<String, Object>>  getDateStatistics(HashMap<String, Object> param) {
		return eventDAO.getDateStatistics(param);
	}

	@Override
	public HashMap<String, Object> getRulDatePartCountS(HashMap<String, Object> param) {
		return eventDAO.getRulDatePartCountS(param);
	}

	@Override
	public HashMap<String, Object> getRulPartDateMoneyCountS(HashMap<String, Object> param) {
		return eventDAO.getRulPartDateMoneyCountS(param);
	}

	@Override
	public int getRulDateTicketTotalCount(HashMap<String, Object> param) {
		return eventDAO.getRulDateTicketTotalCount(param);
	}

	@Override
	public int getRulDateAlmoneyTotalSum(HashMap<String, Object> param) {
		return eventDAO.getRulDateAlmoneyTotalSum(param);
	}

	@Override
	public int getRulDateUserTotalCount(HashMap<String, Object> param) {
		return eventDAO.getRulDateUserTotalCount(param);
	}

	@Override
	public int getRulDateDisUseTotalCount(HashMap<String, Object> param) {
		return eventDAO.getRulDateDisUseTotalCount(param);
	}

	@Override
	public List<HashMap<String, Object>>  getDateTopStatistics(HashMap<String, Object> param) {
		return eventDAO.getDateTopStatistics(param);
	}

	@Override
	public List<HashMap<String, Object>> getChartStatistics() {
		return eventDAO.getChartStatistics();
	}

	@Override
	public int getTrnslateCntBySeq(HashMap<String, Object> param) {
		return commonDAO.getTrnslateCntBySeq(param);
	}

	@Override
	public TranslateVO getTrnslateBySeq(HashMap<String, Object> param) {
		return commonDAO.getTrnslateBySeq(param);
	}

	@Override
	public TranslateVO getTrnslateViewBySeq(HashMap<String, Object> param) {
		return commonDAO.getTrnslateViewBySeq(param);
	}

	@Override
	public void addTranslate(HashMap<String, Object> param) {
		commonDAO.addTranslate(param);
	}

	@Override
	public void setTranslate(HashMap<String, Object> param) {
		commonDAO.setTranslate(param);
	}

	@Override
	public int getUserSeqForTrnslateByNick(String nickName) {
		return commonDAO.getUserSeqForTrnslateByNick(nickName);
	}

	@Override
	public V2RankVO getUserInfoBySeq(HashMap<String, Object> param) {
		return commonDAO.getUserInfoBySeq(param);
	}

	@Override
	public V2RankVO getQtUserInfoBySeq(HashMap<String, Object> param) {
		return commonDAO.getQtUserInfoBySeq(param);
	}

	@Override
	public void setTranslateCount(int seq) {
		commonDAO.setTranslateCount(seq);
	}

	@Override
	public int getTrnslateTotalCntBySeq(HashMap<String, Object> param) {
		return commonDAO.getTrnslateTotalCntBySeq(param);
	}

	@Override
	public int getSaveEventSubCount(HashMap<String, Object> param) {
		return eventDAO.getSaveEventSubCount(param);
	}

	@Override
	public int getMachineTrnsCount(HashMap<String, Object> param) {
		return commonDAO.getMachineTrnsCount(param);
	}

	@Override
	public String getSingleTitle(HashMap<String, Object> param) {
		return commonDAO.getSingleTitle(param);
	}

	@Override
	public String getSingleConents(HashMap<String, Object> param) {
		return commonDAO.getSingleConents(param);
	}

	@Override
	public int getChangeQueAlmoneyCount(HashMap<String, Object> param) {
		return commonDAO.getChangeQueAlmoneyCount(param);
	}
}
