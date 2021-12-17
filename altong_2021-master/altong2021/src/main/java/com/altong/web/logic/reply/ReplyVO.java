package com.altong.web.logic.reply;

import java.sql.Timestamp;

public class ReplyVO {
	private Long seq;
	private Long answerSeq;
	private Long questionSeq;
	private Integer memberSeq;
	private String reply;
	private String dateReg;
	private String flagUse;
	private Timestamp regdate;

	private String nickName;
	private String conDate;
	private String photo;
	private Integer lv;
	private Integer sirenN;

	private String title;
	private String flag;
	private Integer contentSeq;

	private String nick1;
	private String nick2;
	private int almoney;

	private String nation;
	private String lang;
	private String flagDel;

	private String contents;
	private String answer;


	public String getFlagDel() {
		return flagDel;
	}
	public void setFlagDel(String flagDel) {
		this.flagDel = flagDel;
	}
	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}
	public Long getAnswerSeq() {
		return answerSeq;
	}
	public void setAnswerSeq(Long answerSeq) {
		this.answerSeq = answerSeq;
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
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public String getDateReg() {
		return dateReg;
	}
	public void setDateReg(String dateReg) {
		this.dateReg = dateReg;
	}
	public String getFlagUse() {
		return flagUse;
	}
	public void setFlagUse(String flagUse) {
		this.flagUse = flagUse;
	}
	public Timestamp getRegdate() {
		return regdate;
	}
	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getConDate() {
		return conDate;
	}
	public void setConDate(String conDate) {
		this.conDate = conDate;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Integer getLv() {
		return lv;
	}
	public void setLv(Integer lv) {
		this.lv = lv;
	}
	public Integer getSirenN() {
		return sirenN;
	}
	public void setSirenN(Integer sirenN) {
		this.sirenN = sirenN;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Integer getContentSeq() {
		return contentSeq;
	}
	public void setContentSeq(Integer contentSeq) {
		this.contentSeq = contentSeq;
	}
	public String getNick1() {
		return nick1;
	}
	public void setNick1(String nick1) {
		this.nick1 = nick1;
	}
	public String getNick2() {
		return nick2;
	}
	public void setNick2(String nick2) {
		this.nick2 = nick2;
	}
	public int getAlmoney() {
		return almoney;
	}
	public void setAlmoney(int almoney) {
		this.almoney = almoney;
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
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}


}
