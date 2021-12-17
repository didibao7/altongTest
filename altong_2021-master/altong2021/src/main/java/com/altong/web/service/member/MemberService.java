package com.altong.web.service.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.altong.web.logic.LogViewVO;
import com.altong.web.logic.answer.AnswerVO;
import com.altong.web.logic.common.CommonLogQAVO;
import com.altong.web.logic.member.MemberAdminVO;
import com.altong.web.logic.member.MemberCertVO;
import com.altong.web.logic.member.MemberVO;
import com.altong.web.logic.member.MyZzimVO;
import com.altong.web.logic.question.QuestionVO;
import com.altong.web.logic.reply.ReplyVO;


public interface MemberService {
	List<Map<String, Object>> getLoginCheck(Map<String, Object> param);

	Map<String, Object> getMemPhoneAndPwBySeq(Map<String, Object> param);

	int getTotalMember();

	List<MemberVO> getUserRanking(Map<String, Object> param);
	List<MemberVO> getUserRankingSum(Map<String, Object> param);

	int getAnswerChoiceSum();
	int getAnswerSumViewA();

	int getEarnAnsChoiceSum();
	int getAnswerEarningSum();
	int myAlmoneyInfo(int userSeq);
	List<HashMap<String, Object>> myAlmoneyInfoAll(int userSeq);
	List<HashMap<String, Object>> getMemerEtcData(int userSeq);

	Map<String, Object> myeErnTotalList(int userSeq);
	List<Map<String, Object>> getMyEarnList(int userSeq);

	List<Map<String, Object>> getGraphData(Map<String, Object> param);

	void setMemberAlarmLog(Map<String, Object> param);
	List<HashMap<String, Object>> getMyBankList(Map<String, Object> param);

	HashMap<String, Object> getUserAlarmConfig(int userSeq);
	HashMap<String, Object> getUserAlarmCount(int userSeq);

	void setUserProfileImg(HashMap<String, Object> param);
	CommonLogQAVO getQuestionByQuestionSeq(int seq);

	int setPartnerSave(HashMap<String, Object> param);

	HashMap<String, Object> getMemberLvOrFlagBySeq(int userSeq);

	MemberVO getMemberInfoViewBySeq(int userSeq);

	int getUserAlmoneyBySeq(int userSeq);

	void updateQuestionCount(int userSeq);

	HashMap<String, Object> getMemberManagementInfo(int userSeq);

	MemberVO getAlmoneyMyJoin(int userSeq);

	int getNickCheck(HashMap<String, Object> param);

	void updateMyJoinInfo(HashMap<String, Object> param);

	int getMyQuestionCount(HashMap<String, Object> param);

	List<QuestionVO> getMyQuestionList(HashMap<String, Object> param);
	List<QuestionVO> getMyQuestionListFlagN(HashMap<String, Object> param);

	int getMyAnswerCount(HashMap<String, Object> param);

	List<AnswerVO> getMyAnswerList(HashMap<String, Object> param);

	int getMyZzimCount(HashMap<String, Object> param);

	List<MyZzimVO> getMyZzimList(HashMap<String, Object> param);

	int getMyViewCount(HashMap<String, Object> param);

	List<LogViewVO> getMyViewList(HashMap<String, Object> param);

	int getMyReplyCount(HashMap<String, Object> param);

	List<ReplyVO> getMyReplyList(HashMap<String, Object> param);

	int getMyRecivedReplyCount(HashMap<String, Object> param);

	List<ReplyVO> getMyRecivedReplyList(HashMap<String, Object> param);

	String getNickName(int memberSeq);
	String getMyRecommendNick(int memberSeq);
	List<MemberVO> getMyRecommendList(HashMap<String, Object> param);

	String getUserSname(int userSeq);
	HashMap<String, Object> getMyAlmoneyLogTotal(int userSeq);

	HashMap<String, Object> getMemberCertFlag(int userSeq);

	List<HashMap<String, Object>> getMeberLvDataSP(HashMap<String, Object> param);

	MemberVO getAlmoneyMemJoin(int userSeq);

	MemberVO getAnswerMemberInfo(int answerMemberSeq);

	int getMemberJoinFlag(HashMap<String, Object> param);

	int getNickNameSelectCount(String nickName);

	int setMemberJoinSp(HashMap<String, Object> param);

	MemberVO getMemberCertInfo(int userSeq);

	int setJoinCertSms(HashMap<String, Object> param);

	HashMap<String, Object> getMemSmsCntInfo(HashMap<String, Object> param);

	void setMemSmsCnt(HashMap<String, Object> param);

	HashMap<String, Object> setMemberJoinSp2(HashMap<String, Object> param);

	void updatePwd(HashMap<String, Object> param);

	String getUserPoneBySeq(int userSeq);

	void setNiceReq(HashMap<String, Object> param);

	void setIdCard(HashMap<String, Object> param);

	void setCertEtc(HashMap<String, Object> param);

	String getNickFind(String nick);

	int getMemberListCount(HashMap<String, Object> param);
	List<MemberVO> getMemberListLimit(HashMap<String, Object> param);
	List<MemberVO> getMemberExcelListLimit(HashMap<String, Object> param);
	List<MemberVO> getMemberList(HashMap<String, Object> param);
	int getMemberList2Count(HashMap<String, Object> param);
	List<MemberVO> getMemberList2Limit(HashMap<String, Object> param);
	MemberVO getMemberList2forSeq(int seq);
	HashMap<String, Object> getChkNick(String nickName);
	int setAdminMemUpdate(HashMap<String, Object> param);
	MemberVO getMemSeqAndName(int userSeq);
	int setMemUpdateLv(HashMap<String, Object> param);
	void updateLeave(HashMap<String, Object> param);

	List<MemberCertVO> getMemCertListBySeq(HashMap<String, Object> param);
	List<MemberCertVO> getMemCertListAll(HashMap<String, Object> param);
	HashMap<String, Object> setMemCertConfirmBySeq(int seq);
	HashMap<String, Object> setMemCertRejectBySeq(HashMap<String, Object> param);
	MemberCertVO getMemCertAddInfo(int seq);
	String getMemCertCancelMsgInfo(int seq);
	String getMemCertCancelMsg(int seq);
	String getMemCertIdCard(int seq);
	List<MemberAdminVO> getAdmListAll(HashMap<String, Object> param);
	int addAdmin(int seq);
	String getAuthorityBySeq(int seq);
	int setAuthority(HashMap<String, Object> param);
	int deleteAuthority(int seq);
	int getRecommendTotal();

	List<HashMap<String, Object>> getRecommendChildView(int root_node);
	String getRecommendSeq(HashMap<String, Object> param);
	String getRecommendParent(int memberSeq);
	void setRecommendTreeReset();
	HashMap<String, Object> setRecommendModeSwitch(HashMap<String, Object> param);
	HashMap<String, Object> setRecommendModeChange(HashMap<String, Object> param);
	HashMap<String, Object> setRecommendModeAppend(HashMap<String, Object> param);
	String setRecommendBackup(HashMap<String, Object> param);
	List<HashMap<String, Object>> getRecommendBackupList();
	String setRecommendRestore(String target);
	List<MemberVO> getRankMember(HashMap<String, Object> param);

	int getJoinLogCount(int userSeq);
	void setJoinLogCount(int userSeq);
	int getUserSeqByNick(String nickName);
	void addJoinFirstLog(HashMap<String, Object> param);

	int getJoinLogEcount(int userSeq);
	int getJoinLogNcount(int userSeq);

	void setJoinLogEcount(HashMap<String, Object> param);
	void setJoinLogNcount(HashMap<String, Object> param);

	void setMemberNameBySeq(HashMap<String, Object> param);
	void setMemberNationBySeq(HashMap<String, Object> param);
	void setMemberNickNameBySeq(HashMap<String, Object> param);


	HashMap<String, Object> getMemberLoginInfoSp(int userSeq);
}
