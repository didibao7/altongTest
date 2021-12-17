package com.altong.web.service.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altong.web.dao.member.MemberDAO;
import com.altong.web.logic.LogViewVO;
import com.altong.web.logic.answer.AnswerVO;
import com.altong.web.logic.common.CommonLogQAVO;
import com.altong.web.logic.member.MemberAdminVO;
import com.altong.web.logic.member.MemberCertVO;
import com.altong.web.logic.member.MemberVO;
import com.altong.web.logic.member.MyZzimVO;
import com.altong.web.logic.question.QuestionVO;
import com.altong.web.logic.reply.ReplyVO;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberDAO memberDAO;

	@Override
	public List<Map<String, Object>> getLoginCheck(Map<String, Object> param) {
		return memberDAO.getLoginCheck(param);
	}

	@Override
	public Map<String, Object> getMemPhoneAndPwBySeq(Map<String, Object> param) {
		return memberDAO.getMemPhoneAndPwBySeq(param);
	}

	@Override
	public int getTotalMember() {
		return memberDAO.getTotalMember();
	}

	@Override
	public List<MemberVO> getUserRanking(Map<String, Object> param) {
		return memberDAO.getUserRanking(param);
	}

	@Override
	public List<MemberVO> getUserRankingSum(Map<String, Object> param) {
		return memberDAO.getUserRankingSum(param);
	}

	@Override
	public int getAnswerChoiceSum() {
		return memberDAO.getAnswerChoiceSum();
	}

	@Override
	public int getAnswerSumViewA() {
		return memberDAO.getAnswerSumViewA();
	}

	@Override
	public int getEarnAnsChoiceSum() {
		return memberDAO.getEarnAnsChoiceSum();
	}

	@Override
	public int getAnswerEarningSum() {
		return memberDAO.getAnswerEarningSum();
	}

	@Override
	public List<HashMap<String, Object>> myAlmoneyInfoAll(int userSeq) {
		return memberDAO.myAlmoneyInfoAll(userSeq);
	}

	@Override
	public int myAlmoneyInfo(int userSeq) {
		return memberDAO.myAlmoneyInfo(userSeq);
	}

	@Override
	public Map<String, Object> myeErnTotalList(int userSeq) {
		return memberDAO.myeErnTotalList(userSeq);
	}

	@Override
	public List<Map<String, Object>> getMyEarnList(int userSeq) {
		return memberDAO.getMyEarnList(userSeq);
	}

	@Override
	public List<HashMap<String, Object>> getMemerEtcData(int userSeq) {
		return memberDAO.getMemerEtcData(userSeq);
	}

	@Override
	public List<Map<String, Object>> getGraphData(Map<String, Object> param) {
		return memberDAO.getGraphData(param);
	}

	@Override
	public void setMemberAlarmLog(Map<String, Object> param) {
		memberDAO.setMemberAlarmLog(param);
	}

	@Override
	public List<HashMap<String, Object>> getMyBankList(Map<String, Object> param) {
		return memberDAO.getMyBankList(param);
	}

	@Override
	public HashMap<String, Object> getUserAlarmConfig(int userSeq) {
		return memberDAO.getUserAlarmConfig(userSeq);
	}

	@Override
	public HashMap<String, Object> getUserAlarmCount(int userSeq) {
		return memberDAO.getUserAlarmCount(userSeq);
	}

	@Override
	public void setUserProfileImg(HashMap<String, Object> param) {
		memberDAO.setUserProfileImg(param);
	}

	@Override
	public CommonLogQAVO getQuestionByQuestionSeq(int seq) {
		return memberDAO.getQuestionByQuestionSeq(seq);
	}

	@Override
	public int setPartnerSave(HashMap<String, Object> param) {
		return memberDAO.setPartnerSave(param);
	}

	@Override
	public HashMap<String, Object> getMemberLvOrFlagBySeq(int userSeq) {
		return memberDAO.getMemberLvOrFlagBySeq(userSeq);
	}

	@Override
	public MemberVO getMemberInfoViewBySeq(int userSeq) {
		return memberDAO.getMemberInfoViewBySeq(userSeq);
	}

	@Override
	public int getUserAlmoneyBySeq(int userSeq) {
		return memberDAO.getUserAlmoneyBySeq(userSeq);
	}

	@Override
	public void updateQuestionCount(int userSeq) {
		memberDAO.updateQuestionCount(userSeq);
	}

	@Override
	public HashMap<String, Object> getMemberManagementInfo(int userSeq) {
		return memberDAO.getMemberManagementInfo(userSeq);
	}

	@Override
	public MemberVO getAlmoneyMyJoin(int userSeq) {
		return memberDAO.getAlmoneyMyJoin(userSeq);
	}

	@Override
	public int getNickCheck(HashMap<String, Object> param) {
		return memberDAO.getNickCheck(param);
	}

	@Override
	public void updateMyJoinInfo(HashMap<String, Object> param) {
		memberDAO.updateMyJoinInfo(param);
	}

	@Override
	public int getMyQuestionCount(HashMap<String, Object> param) {
		return memberDAO.getMyQuestionCount(param);
	}

	@Override
	public List<QuestionVO> getMyQuestionList(HashMap<String, Object> param) {
		return memberDAO.getMyQuestionList(param);
	}

	@Override
	public List<QuestionVO> getMyQuestionListFlagN(HashMap<String, Object> param) {
		return memberDAO.getMyQuestionListFlagN(param);
	}

	@Override
	public int getMyAnswerCount(HashMap<String, Object> param) {
		return memberDAO.getMyAnswerCount(param);
	}

	@Override
	public List<AnswerVO> getMyAnswerList(HashMap<String, Object> param) {
		return memberDAO.getMyAnswerList(param);
	}

	@Override
	public int getMyZzimCount(HashMap<String, Object> param) {
		return memberDAO.getMyZzimCount(param);
	}

	@Override
	public List<MyZzimVO> getMyZzimList(HashMap<String, Object> param) {
		return memberDAO.getMyZzimList(param);
	}

	@Override
	public int getMyViewCount(HashMap<String, Object> param) {
		return memberDAO.getMyViewCount(param);
	}

	@Override
	public List<LogViewVO> getMyViewList(HashMap<String, Object> param) {
		return memberDAO.getMyViewList(param);
	}

	@Override
	public int getMyReplyCount(HashMap<String, Object> param) {
		return memberDAO.getMyReplyCount(param);
	}

	@Override
	public List<ReplyVO> getMyReplyList(HashMap<String, Object> param) {
		return memberDAO.getMyReplyList(param);
	}

	@Override
	public int getMyRecivedReplyCount(HashMap<String, Object> param) {
		return memberDAO.getMyRecivedReplyCount(param);
	}

	@Override
	public List<ReplyVO> getMyRecivedReplyList(HashMap<String, Object> param) {
		return memberDAO.getMyRecivedReplyList(param);
	}

	@Override
	public String getNickName(int memberSeq) {
		return memberDAO.getNickName(memberSeq);
	}

	@Override
	public String getMyRecommendNick(int memberSeq) {
		return memberDAO.getMyRecommendNick(memberSeq);
	}

	@Override
	public List<MemberVO> getMyRecommendList(HashMap<String, Object> param) {
		return memberDAO.getMyRecommendList(param);
	}

	@Override
	public String getUserSname(int userSeq) {
		return memberDAO.getUserSname(userSeq);
	}

	@Override
	public HashMap<String, Object> getMyAlmoneyLogTotal(int userSeq) {
		return memberDAO.getMyAlmoneyLogTotal(userSeq);
	}

	@Override
	public HashMap<String, Object> getMemberCertFlag(int userSeq) {
		return memberDAO.getMemberCertFlag(userSeq);
	}

	@Override
	public List<HashMap<String, Object>> getMeberLvDataSP(HashMap<String, Object> param) {
		return memberDAO.getMeberLvDataSP(param);
	}

	@Override
	public MemberVO getAlmoneyMemJoin(int userSeq) {
		return memberDAO.getAlmoneyMemJoin(userSeq);
	}

	@Override
	public MemberVO getAnswerMemberInfo(int answerMemberSeq) {
		return memberDAO.getAnswerMemberInfo(answerMemberSeq);
	}

	@Override
	public int getMemberJoinFlag(HashMap<String, Object> param) {
		return memberDAO.getMemberJoinFlag(param);
	}

	@Override
	public int getNickNameSelectCount(String nickName) {
		return memberDAO.getNickNameSelectCount(nickName);
	}

	@Override
	public int setMemberJoinSp(HashMap<String, Object> param) {
		return memberDAO.setMemberJoinSp(param);
	}

	@Override
	public MemberVO getMemberCertInfo(int userSeq) {
		return memberDAO.getMemberCertInfo(userSeq);
	}

	@Override
	public int setJoinCertSms(HashMap<String, Object> param) {
		return memberDAO.setJoinCertSms(param);
	}

	@Override
	public HashMap<String, Object> getMemSmsCntInfo(HashMap<String, Object> param) {
		return memberDAO.getMemSmsCntInfo(param);
	}

	@Override
	public void setMemSmsCnt(HashMap<String, Object> param) {
		memberDAO.setMemSmsCnt(param);
	}

	@Override
	public HashMap<String, Object> setMemberJoinSp2(HashMap<String, Object> param) {
		return memberDAO.setMemberJoinSp2(param);
	}

	@Override
	public void updatePwd(HashMap<String, Object> param) {
		memberDAO.updatePwd(param);
	}

	@Override
	public String getUserPoneBySeq(int userSeq) {
		return memberDAO.getUserPoneBySeq(userSeq);
	}

	@Override
	public void setNiceReq(HashMap<String, Object> param) {
		memberDAO.setNiceReq(param);
	}

	@Override
	public void setIdCard(HashMap<String, Object> param) {
		memberDAO.setIdCard(param);
	}

	@Override
	public void setCertEtc(HashMap<String, Object> param) {
		memberDAO.setCertEtc(param);
	}

	@Override
	public String getNickFind(String nick) {
		return memberDAO.getNickFind(nick);
	}

	@Override
	public int getMemberListCount(HashMap<String, Object> param) {
		return memberDAO.getMemberListCount(param);
	}

	@Override
	public List<MemberVO> getMemberListLimit(HashMap<String, Object> param) {
		return memberDAO.getMemberListLimit(param);
	}

	@Override
	public List<MemberVO> getMemberExcelListLimit(HashMap<String, Object> param) {
		return memberDAO.getMemberExcelListLimit(param);
	}

	@Override
	public List<MemberVO> getMemberList(HashMap<String, Object> param) {
		return memberDAO.getMemberList(param);
	}

	@Override
	public int getMemberList2Count(HashMap<String, Object> param) {
		return memberDAO.getMemberList2Count(param);
	}

	@Override
	public List<MemberVO> getMemberList2Limit(HashMap<String, Object> param) {
		return memberDAO.getMemberList2Limit(param);
	}

	@Override
	public MemberVO getMemberList2forSeq(int seq) {
		return memberDAO.getMemberList2forSeq(seq);
	}

	@Override
	public HashMap<String, Object> getChkNick(String nickName) {
		return memberDAO.getChkNick(nickName);
	}

	@Override
	public int setAdminMemUpdate(HashMap<String, Object> param) {
		return memberDAO.setAdminMemUpdate(param);
	}

	@Override
	public MemberVO getMemSeqAndName(int userSeq) {
		return memberDAO.getMemSeqAndName(userSeq);
	}
	@Override
	public int setMemUpdateLv(HashMap<String, Object> param) {
		return memberDAO.setMemUpdateLv(param);
	}
	@Override
	public void updateLeave(HashMap<String, Object> param) {
		memberDAO.updateLeave(param);
	}

	@Override
	public List<MemberCertVO> getMemCertListBySeq(HashMap<String, Object> param) {
		return memberDAO.getMemCertListBySeq(param);
	}
	@Override
	public List<MemberCertVO> getMemCertListAll(HashMap<String, Object> param) {
		return memberDAO.getMemCertListAll(param);
	}
	@Override
	public HashMap<String, Object> setMemCertConfirmBySeq(int seq) {
		return memberDAO.setMemCertConfirmBySeq(seq);
	}
	@Override
	public HashMap<String, Object> setMemCertRejectBySeq(HashMap<String, Object> param) {
		return memberDAO.setMemCertRejectBySeq(param);
	}
	@Override
	public MemberCertVO getMemCertAddInfo(int seq) {
		return memberDAO.getMemCertAddInfo(seq);
	}

	@Override
	public String getMemCertCancelMsgInfo(int seq) {
		return memberDAO.getMemCertCancelMsgInfo(seq);
	}
	@Override
	public String getMemCertCancelMsg(int seq) {
		return memberDAO.getMemCertCancelMsg(seq);
	}
	@Override
	public String getMemCertIdCard(int seq) {
		return memberDAO.getMemCertIdCard(seq);
	}
	@Override
	public List<MemberAdminVO> getAdmListAll(HashMap<String, Object> param) {
		return memberDAO.getAdmListAll(param);
	}
	@Override
	public int addAdmin(int seq) {
		return memberDAO.addAdmin(seq);
	}
	@Override
	public String getAuthorityBySeq(int seq) {
		return memberDAO.getAuthorityBySeq(seq);
	}
	@Override
	public int setAuthority(HashMap<String, Object> param) {
		return memberDAO.setAuthority(param);
	}
	@Override
	public int deleteAuthority(int seq) {
		return memberDAO.deleteAuthority(seq);
	}
	@Override
	public int getRecommendTotal() {
		return memberDAO.getRecommendTotal();
	}
	@Override
	public List<HashMap<String, Object>> getRecommendChildView(int root_node) {
		return memberDAO.getRecommendChildView(root_node);
	}
	@Override
	public String getRecommendSeq(HashMap<String, Object> param) {
		return memberDAO.getRecommendSeq(param);
	}
	@Override
	public String getRecommendParent(int memberSeq) {
		return memberDAO.getRecommendParent(memberSeq);
	}
	@Override
	public void setRecommendTreeReset() {
		memberDAO.setRecommendTreeReset();
	}
	@Override
	public HashMap<String, Object> setRecommendModeSwitch(HashMap<String, Object> param) {
		return memberDAO.setRecommendModeSwitch(param);
	}
	@Override
	public HashMap<String, Object> setRecommendModeChange(HashMap<String, Object> param) {
		return memberDAO.setRecommendModeChange(param);
	}
	@Override
	public HashMap<String, Object> setRecommendModeAppend(HashMap<String, Object> param) {
		return memberDAO.setRecommendModeAppend(param);
	}
	@Override
	public String setRecommendBackup(HashMap<String, Object> param) {
		return memberDAO.setRecommendBackup(param);
	}
	@Override
	public List<HashMap<String, Object>> getRecommendBackupList() {
		return memberDAO.getRecommendBackupList();
	}
	@Override
	public String setRecommendRestore(String target) {
		return memberDAO.setRecommendRestore(target);
	}

	@Override
	public List<MemberVO> getRankMember(HashMap<String, Object> param) {
		return memberDAO.getRankMember(param);
	}

	@Override
	public int getJoinLogCount(int userSeq) {
		return memberDAO.getJoinLogCount(userSeq);
	}

	@Override
	public void setJoinLogCount(int userSeq) {
		memberDAO.setJoinLogCount(userSeq);
	}

	@Override
	public int getUserSeqByNick(String nickName) {
		return memberDAO.getUserSeqByNick(nickName);
	}

	@Override
	public void addJoinFirstLog(HashMap<String, Object> param) {
		memberDAO.addJoinFirstLog(param);
	}

	@Override
	public int getJoinLogEcount(int userSeq) {
		return memberDAO.getJoinLogEcount(userSeq);
	}

	@Override
	public int getJoinLogNcount(int userSeq) {
		return memberDAO.getJoinLogNcount(userSeq);
	}

	@Override
	public void setJoinLogEcount(HashMap<String, Object> param) {
		memberDAO.setJoinLogEcount(param);
	}

	@Override
	public void setJoinLogNcount(HashMap<String, Object> param) {
		memberDAO.setJoinLogNcount(param);
	}

	@Override
	public void setMemberNameBySeq(HashMap<String, Object> param) {
		memberDAO.setMemberNameBySeq(param);
	}

	@Override
	public void setMemberNationBySeq(HashMap<String, Object> param) {
		memberDAO.setMemberNationBySeq(param);
	}

	@Override
	public void setMemberNickNameBySeq(HashMap<String, Object> param) {
		memberDAO.setMemberNickNameBySeq(param);
	}

	@Override
	public HashMap<String, Object> getMemberLoginInfoSp(int userSeq) {
		return memberDAO.getMemberLoginInfoSp(userSeq);
	}
}
