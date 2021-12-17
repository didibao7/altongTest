package com.altong.web.logic.member;

import java.math.BigDecimal;

public class MyZzimVO {
	private Integer seq;
	private Integer memberSeq;
	private Long questionSeq;

	private String section1;
	private String title;
	private String contents;
	private BigDecimal almoney;
	private String flagUse;
	private Integer readCount;
	private String dateReg;
	private int answerCount;

	private String nickName;
	private int lv;
	private String photo;

	private String flagNickName;

	private String nation;

	public String getFlagNickName() {
		return flagNickName;
	}
	public void setFlagNickName(String flagNickName) {
		this.flagNickName = flagNickName;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public Integer getMemberSeq() {
		return memberSeq;
	}
	public void setMemberSeq(Integer memberSeq) {
		this.memberSeq = memberSeq;
	}
	public Long getQuestionSeq() {
		return questionSeq;
	}
	public void setQuestionSeq(Long questionSeq) {
		this.questionSeq = questionSeq;
	}
	public String getSection1() {
		return section1;
	}
	public void setSection1(String section1) {
		this.section1 = section1;
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
	public BigDecimal getAlmoney() {
		return almoney;
	}
	public void setAlmoney(BigDecimal almoney) {
		this.almoney = almoney;
	}
	public String getFlagUse() {
		return flagUse;
	}
	public void setFlagUse(String flagUse) {
		this.flagUse = flagUse;
	}
	public Integer getReadCount() {
		return readCount;
	}
	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}
	public String getDateReg() {
		return dateReg;
	}
	public void setDateReg(String dateReg) {
		this.dateReg = dateReg;
	}
	public int getAnswerCount() {
		return answerCount;
	}
	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
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
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
}
