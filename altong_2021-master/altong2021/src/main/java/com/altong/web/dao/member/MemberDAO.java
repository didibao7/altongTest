package com.altong.web.dao.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.altong.web.logic.LogViewVO;
import com.altong.web.logic.answer.AnswerVO;
import com.altong.web.logic.common.CommonLogQAVO;
import com.altong.web.logic.member.MemberAdminVO;
import com.altong.web.logic.member.MemberCertVO;
import com.altong.web.logic.member.MemberVO;
import com.altong.web.logic.member.MyZzimVO;
import com.altong.web.logic.question.QuestionVO;
import com.altong.web.logic.reply.ReplyVO;

@Repository
public class MemberDAO {
	@Autowired
	public SqlSession sqlSession;

	public List<Map<String, Object>> getLoginCheck(Map<String, Object> param) {
		return sqlSession.selectOne("getLoginCheck", param);
	}
	public Map<String, Object> getMemPhoneAndPwBySeq(Map<String, Object> param) {
		return sqlSession.selectOne("getMemPhoneAndPwBySeq", param);
	}
	public int getTotalMember() {
		return sqlSession.selectOne("getTotalMember");
	}

	public List<MemberVO> getUserRanking(Map<String, Object> param) {
		return sqlSession.selectList("getUserRanking", param);
	}

	public List<MemberVO> getUserRankingSum(Map<String, Object> param) {
		return sqlSession.selectList("getUserRankingSum", param);
	}

	public int getAnswerChoiceSum() {
		return sqlSession.selectOne("getAnswerChoiceSum");
	}

	public int getAnswerSumViewA() {
		return sqlSession.selectOne("getAnswerSumViewA");
	}

	public int getEarnAnsChoiceSum() {
		return sqlSession.selectOne("getEarnAnsChoiceSum");
	}

	public int getAnswerEarningSum() {
		return sqlSession.selectOne("getAnswerEarningSum");
	}

	public List<HashMap<String, Object>> myAlmoneyInfoAll(int userSeq) {
		return sqlSession.selectList("myAlmoneyInfoAll", userSeq);
	}

	public int myAlmoneyInfo(int userSeq) {
		return sqlSession.selectOne("getAnswerEarningSum", userSeq);
	}

	public Map<String, Object> myeErnTotalList(int userSeq) {
		return sqlSession.selectOne("myeErnTotalList", userSeq);
	}

	public List<Map<String, Object>> getMyEarnList(int userSeq) {
		return sqlSession.selectList("getMyEarnList", userSeq);
	}

	public List<HashMap<String, Object>> getMemerEtcData(int userSeq) {
		return sqlSession.selectList("getMemerEtcData", userSeq);
	}

	public List<Map<String, Object>> getGraphData(Map<String, Object> param) {
		return sqlSession.selectList("getGraphData", param);
	}

	public void setMemberAlarmLog(Map<String, Object> param) {
		sqlSession.selectOne("setMemberAlarmLog", param);
	}

	public List<HashMap<String, Object>> getMyBankList(Map<String, Object> param) {
		return sqlSession.selectList("getMyBankList", param);
	}

	public HashMap<String, Object> getUserAlarmConfig(int userSeq) {
		return sqlSession.selectOne("getUserAlarmConfig", userSeq);
	}

	public HashMap<String, Object> getUserAlarmCount(int userSeq) {
		return sqlSession.selectOne("getUserAlarmCount", userSeq);
	}

	public void setUserProfileImg(HashMap<String, Object> param) {
		sqlSession.update("setUserProfileImg", param);
	}

	public CommonLogQAVO getQuestionByQuestionSeq(int seq) {
		return sqlSession.selectOne("getQuestionByQuestionSeq", seq);
	}

	public int setPartnerSave(HashMap<String, Object> param) {
		return sqlSession.selectOne("setPartnerSave", param);
	}

	public HashMap<String, Object> getMemberLvOrFlagBySeq(int userSeq) {
		return sqlSession.selectOne("getMemberLvOrFlagBySeq", userSeq);
	}

	public MemberVO getMemberInfoViewBySeq(int userSeq) {
		return sqlSession.selectOne("getMemberInfoViewBySeq", userSeq);
	}

	public int getUserAlmoneyBySeq(int userSeq) {
		return sqlSession.selectOne("getUserAlmoneyBySeq", userSeq);
	}

	public void updateQuestionCount(int userSeq) {
		sqlSession.update("updateQuestionCount", userSeq);
	}

	public HashMap<String, Object> getMemberManagementInfo(int userSeq) {
		return sqlSession.selectOne("getMemberManagementInfo", userSeq);
	}

	public MemberVO getAlmoneyMyJoin(int userSeq) {
		return sqlSession.selectOne("getAlmoneyMyJoin", userSeq);
	}

	public int getNickCheck(HashMap<String, Object> param) {
		return sqlSession.selectOne("getNickCheck", param);
	}

	public void updateMyJoinInfo(HashMap<String, Object> param) {
		sqlSession.update("updateMyJoinInfo", param);
	}

	public int getMyQuestionCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getMyQuestionCount", param);
	}

	public List<QuestionVO> getMyQuestionList(HashMap<String, Object> param) {
		return sqlSession.selectList("getMyQuestionList2", param);
	}

	public List<QuestionVO> getMyQuestionListFlagN(HashMap<String, Object> param) {
		return sqlSession.selectList("getMyQuestionListFlagN", param);
	}

	public int getMyAnswerCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getMyAnswerCount", param);
	}

	public List<AnswerVO> getMyAnswerList(HashMap<String, Object> param) {
		return sqlSession.selectList("getMyAnswerList", param);
	}

	public int getMyZzimCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getMyZzimCount", param);
	}

	public List<MyZzimVO> getMyZzimList(HashMap<String, Object> param) {
		return sqlSession.selectList("getMyZzimList", param);
	}

	public int getMyViewCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getMyViewCount", param);
	}

	public List<LogViewVO> getMyViewList(HashMap<String, Object> param) {
		return sqlSession.selectList("getMyViewList", param);
	}

	public int getMyReplyCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getMyReplyCount", param);
	}

	public List<ReplyVO> getMyReplyList(HashMap<String, Object> param) {
		return sqlSession.selectList("getMyReplyList", param);
	}

	public int getMyRecivedReplyCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getMyRecivedReplyCount", param);
	}

	public List<ReplyVO> getMyRecivedReplyList(HashMap<String, Object> param) {
		return sqlSession.selectList("getMyRecivedReplyList", param);
	}

	public String getNickName(int memberSeq) {
		return sqlSession.selectOne("getNickName", memberSeq);
	}

	public String getMyRecommendNick(int memberSeq) {
		return sqlSession.selectOne("getMyRecommendSelect", memberSeq);
	}

	public List<MemberVO> getMyRecommendList(HashMap<String, Object> param) {
		return sqlSession.selectList("getMyRecommendList", param);
	}

	public String getUserSname(int userSeq) {
		return sqlSession.selectOne("getUserSname", userSeq);
	}

	public HashMap<String, Object> getMyAlmoneyLogTotal(int userSeq) {
		return sqlSession.selectOne("getMyAlmoneyLogTotal", userSeq);
	}

	public HashMap<String, Object> getMemberCertFlag(int userSeq) {
		return sqlSession.selectOne("getMemberCertFlag", userSeq);
	}

	public List<HashMap<String, Object>> getMeberLvDataSP(HashMap<String, Object> param) {
		return sqlSession.selectList("getMeberLvDataSP", param);
	}

	public MemberVO getAlmoneyMemJoin(int userSeq) {
		return sqlSession.selectOne("getAlmoneyMemJoin", userSeq);
	}

	public MemberVO getAnswerMemberInfo(int answerMemberSeq) {
		return sqlSession.selectOne("getAnswerMemberInfo", answerMemberSeq);
	}

	public int getMemberJoinFlag(HashMap<String, Object> param) {
		return sqlSession.selectOne("getMemberJoinFlag", param);
	}

	public int getNickNameSelectCount(String nickName) {
		return sqlSession.selectOne("getNickNameSelectCount", nickName);
	}

	public int setMemberJoinSp(HashMap<String, Object> param) {
		return sqlSession.selectOne("setMemberJoinSp", param);
	}

	public MemberVO getMemberCertInfo(int userSeq) {
		return sqlSession.selectOne("getMemberCertInfo", userSeq);
	}

	public int setJoinCertSms(HashMap<String, Object> param) {
		return sqlSession.selectOne("setJoinCertSms", param);
	}

	public HashMap<String, Object> getMemSmsCntInfo(HashMap<String, Object> param) {
		return sqlSession.selectOne("getMemSmsCntInfo", param);
	}

	public void setMemSmsCnt(HashMap<String, Object> param) {
		sqlSession.update("setMemSmsCnt", param);
	}

	public HashMap<String, Object> setMemberJoinSp2(HashMap<String, Object> param) {
		return sqlSession.selectOne("setMemberJoinSp2", param);
	}

	public void updatePwd(HashMap<String, Object> param) {
		sqlSession.selectOne("updatePwd", param);
	}

	public String getUserPoneBySeq(int userSeq) {
		return sqlSession.selectOne("getUserPoneBySeq", userSeq);
	}

	public void setNiceReq(HashMap<String, Object> param) {
		sqlSession.insert("setNiceReq", param);
	}

	public void setIdCard(HashMap<String, Object> param) {
		sqlSession.update("setIdCard", param);
	}

	public void setCertEtc(HashMap<String, Object> param) {
		sqlSession.update("setCertEtc", param);
	}

	public String getNickFind(String nick) {
		return sqlSession.selectOne("getNickFind", nick);
	}

	public int getMemberListCount(HashMap<String, Object> param) {
		return sqlSession.selectOne("getMemberListCount", param);
	}

	public List<MemberVO> getMemberListLimit(HashMap<String, Object> param) {
		return sqlSession.selectList("getMemberListLimit", param);
	}

	public List<MemberVO> getMemberExcelListLimit(HashMap<String, Object> param) {
		return sqlSession.selectList("getMemberExcelListLimit", param);
	}

	public List<MemberVO> getMemberList(HashMap<String, Object> param) {
		return sqlSession.selectList("getMemberList", param);
	}

	public int getMemberList2Count(HashMap<String, Object> param) {
		return sqlSession.selectOne("getMemberList2Count", param);
	}

	public List<MemberVO> getMemberList2Limit(HashMap<String, Object> param) {
		return sqlSession.selectList("getMemberList2Limit", param);
	}

	public MemberVO getMemberList2forSeq(int seq) {
		return sqlSession.selectOne("getMemberList2forSeq", seq);
	}

	public HashMap<String, Object> getChkNick(String nickName) {
		return sqlSession.selectOne("getChkNick", nickName);
	}

	public int setAdminMemUpdate(HashMap<String, Object> param) {
		return sqlSession.selectOne("setAdminMemUpdate", param);
	}

	public MemberVO getMemSeqAndName(int userSeq) {
		return sqlSession.selectOne("getMemSeqAndName", userSeq);
	}

	public int setMemUpdateLv(HashMap<String, Object> param) {
		return sqlSession.selectOne("setMemUpdateLv", param);
	}

	public void updateLeave(HashMap<String, Object> param) {
		sqlSession.update("updateLeave", param);
	}

	public List<MemberCertVO> getMemCertListBySeq(HashMap<String, Object> param) {
		return sqlSession.selectList("getMemCertListBySeq", param);
	}

	public List<MemberCertVO> getMemCertListAll(HashMap<String, Object> param) {
		return sqlSession.selectList("getMemCertListAll", param);
	}

	public HashMap<String, Object> setMemCertConfirmBySeq(int seq) {
		return sqlSession.selectOne("setMemCertConfirmBySeq", seq);
	}

	public HashMap<String, Object> setMemCertRejectBySeq(HashMap<String, Object> param) {
		return sqlSession.selectOne("setMemCertRejectBySeq", param);
	}

	public MemberCertVO getMemCertAddInfo(int seq) {
		return sqlSession.selectOne("getMemCertAddInfo", seq);
	}

	public String getMemCertCancelMsgInfo(int seq) {
		return sqlSession.selectOne("getMemCertCancelMsgInfo", seq);
	}

	public String getMemCertCancelMsg(int seq) {
		return sqlSession.selectOne("getMemCertCancelMsg", seq);
	}

	public String getMemCertIdCard(int seq) {
		return sqlSession.selectOne("getMemCertIdCard", seq);
	}

	public List<MemberAdminVO> getAdmListAll(HashMap<String, Object> param) {
		return sqlSession.selectList("getAdmListAll", param);
	}

	public int addAdmin(int seq) {
		return sqlSession.selectOne("addAdmin", seq);
	}

	public String getAuthorityBySeq(int seq) {
		return sqlSession.selectOne("getAuthorityBySeq", seq);
	}

	public int setAuthority(HashMap<String, Object> param) {
		return sqlSession.selectOne("setAuthority", param);
	}

	public int deleteAuthority(int seq) {
		return sqlSession.selectOne("deleteAuthority", seq);
	}

	public int getRecommendTotal() {
		return sqlSession.selectOne("getRecommendTotal");
	}

	public List<HashMap<String, Object>> getRecommendChildView(int root_node) {
		return sqlSession.selectList("getRecommendChildView", root_node);
	}

	public String getRecommendSeq(HashMap<String, Object> param) {
		return sqlSession.selectOne("getRecommendSeq", param);
	}

	public String getRecommendParent(int memberSeq) {
		return sqlSession.selectOne("getRecommendParent", memberSeq);
	}

	public void setRecommendTreeReset() {
		sqlSession.insert("setRecommendTreeReset");
	}

	public HashMap<String, Object> setRecommendModeSwitch(HashMap<String, Object> param) {
		return sqlSession.selectOne("setRecommendModeSwitch", param);
	}

	public HashMap<String, Object> setRecommendModeChange(HashMap<String, Object> param) {
		return sqlSession.selectOne("setRecommendModeChange", param);
	}

	public HashMap<String, Object> setRecommendModeAppend(HashMap<String, Object> param) {
		return sqlSession.selectOne("setRecommendModeAppend", param);
	}

	public String setRecommendBackup(HashMap<String, Object> param) {
		return sqlSession.selectOne("setRecommendBackup", param);
	}

	public List<HashMap<String, Object>> getRecommendBackupList() {
		return sqlSession.selectList("getRecommendBackupList");
	}

	public String setRecommendRestore(String target) {
		return sqlSession.selectOne("setRecommendRestore", target);
	}

	public List<MemberVO> getRankMember(HashMap<String, Object> param) {
		return sqlSession.selectList("getRankMember", param);
	}

	public int getJoinLogCount(int userSeq) {
		return sqlSession.selectOne("getJoinLogCount", userSeq);
	}

	public void setJoinLogCount(int userSeq) {
		sqlSession.update("setJoinLogCount", userSeq);
	}

	public int getUserSeqByNick(String nickName) {
		return sqlSession.selectOne("getUserSeqByNick", nickName);
	}

	public void addJoinFirstLog(HashMap<String, Object> param) {
		sqlSession.insert("addJoinFirstLog", param);
	}

	public int getJoinLogEcount(int userSeq) {
		return sqlSession.selectOne("getJoinLogEcount", userSeq);
	}

	public int getJoinLogNcount(int userSeq) {
		return sqlSession.selectOne("getJoinLogNcount", userSeq);
	}

	public void setJoinLogEcount(HashMap<String, Object> param) {
		sqlSession.update("setJoinLogEcount", param);
	}

	public void setJoinLogNcount(HashMap<String, Object> param) {
		sqlSession.update("setJoinLogNcount", param);
	}

	public void setMemberNameBySeq(HashMap<String, Object> param) {
		sqlSession.update("setMemberNameBySeq", param);
	}

	public void setMemberNationBySeq(HashMap<String, Object> param) {
		sqlSession.update("setMemberNationBySeq", param);
	}

	public void setMemberNickNameBySeq(HashMap<String, Object> param) {
		sqlSession.update("setMemberNickNameBySeq", param);
	}

	public HashMap<String, Object> getMemberLoginInfoSp(int userSeq) {
		return sqlSession.selectOne("getMemberLoginInfoSp", userSeq);
	}
}
