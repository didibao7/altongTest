package com.altong.web.logic.answer;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class AnswerVO {
	private Long seq;
	private Long questionSeq;
	private Integer memberSeq;
	private String answer;
	private String  flagNickName;
	private String flagUse;
	private String flagChoice;
	private Integer pointCount1;
	private Integer pointCount2;
	private Integer pointCount3;
	private Integer pointCount4;
	private Integer pointCount5;
	private Integer pointCount6;
	private Integer readCount;
	private Long readAlmoney;
	private String dateReg;
	private Timestamp regdate;

	private Integer pointRank;
	private String section1;
	private Integer countA;

	private String intro;
	private int lv;
	private String photo;
	private Long q_Almoney;
	private Long a_Almoney;

	private String nickName;
	private String title;
	private String contents;

	private Long answerSeq;
	private Long idx;
	private BigDecimal almoney;
	private int answerCount;

	private String nation;
	private String lang;

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public Long getQuestionSeq() {
		return questionSeq;
	}

	public void setQuestionSeq(Long questionSeq) {
		this.questionSeq = questionSeq;
	}

	public Integer getMemberSeq() {
		return memberSeq;
	}

	public void setMemberSeq(Integer memberSeq) {
		this.memberSeq = memberSeq;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getFlagNickName() {
		return flagNickName;
	}

	public void setFlagNickName(String flagNickName) {
		this.flagNickName = flagNickName;
	}

	public String getFlagUse() {
		return flagUse;
	}

	public void setFlagUse(String flagUse) {
		this.flagUse = flagUse;
	}

	public String getFlagChoice() {
		return flagChoice;
	}

	public void setFlagChoice(String flagChoice) {
		this.flagChoice = flagChoice;
	}

	public Integer getPointCount1() {
		return pointCount1;
	}

	public void setPointCount1(Integer pointCount1) {
		this.pointCount1 = pointCount1;
	}

	public Integer getPointCount2() {
		return pointCount2;
	}

	public void setPointCount2(Integer pointCount2) {
		this.pointCount2 = pointCount2;
	}

	public Integer getPointCount3() {
		return pointCount3;
	}

	public void setPointCount3(Integer pointCount3) {
		this.pointCount3 = pointCount3;
	}

	public Integer getPointCount4() {
		return pointCount4;
	}

	public void setPointCount4(Integer pointCount4) {
		this.pointCount4 = pointCount4;
	}

	public Integer getPointCount5() {
		return pointCount5;
	}

	public void setPointCount5(Integer pointCount5) {
		this.pointCount5 = pointCount5;
	}

	public Integer getPointCount6() {
		return pointCount6;
	}

	public void setPointCount6(Integer pointCount6) {
		this.pointCount6 = pointCount6;
	}

	public Integer getReadCount() {
		return readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	public Long getReadAlmoney() {
		return readAlmoney;
	}

	public void setReadAlmoney(Long readAlmoney) {
		this.readAlmoney = readAlmoney;
	}

	public String getDateReg() {
		return dateReg;
	}

	public void setDateReg(String dateReg) {
		this.dateReg = dateReg;
	}

	public Timestamp getRegdate() {
		return regdate;
	}

	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}

	public Integer getPointRank() {
		return pointRank;
	}

	public void setPointRank(Integer pointRank) {
		this.pointRank = pointRank;
	}

	public String getSection1() {
		return section1;
	}

	public void setSection1(String section1) {
		this.section1 = section1;
	}

	public Integer getCountA() {
		return countA;
	}

	public void setCountA(Integer countA) {
		this.countA = countA;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Long getQ_Almoney() {
		return q_Almoney;
	}

	public void setQ_Almoney(Long q_Almoney) {
		this.q_Almoney = q_Almoney;
	}

	public Long getA_Almoney() {
		return a_Almoney;
	}

	public void setA_Almoney(Long a_Almoney) {
		this.a_Almoney = a_Almoney;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Long getAnswerSeq() {
		return answerSeq;
	}

	public void setAnswerSeq(Long answerSeq) {
		this.answerSeq = answerSeq;
	}

	public Long getIdx() {
		return idx;
	}

	public void setIdx(Long idx) {
		this.idx = idx;
	}

	public BigDecimal getAlmoney() {
		return almoney;
	}

	public void setAlmoney(BigDecimal almoney) {
		this.almoney = almoney;
	}

	public int getAnswerCount() {
		return answerCount;
	}

	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}



}
