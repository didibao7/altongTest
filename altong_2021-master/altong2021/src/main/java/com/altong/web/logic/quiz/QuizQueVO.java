package com.altong.web.logic.quiz;

public class QuizQueVO {
	private int uid;
	private String quest;
	private int rtime1;
	private int rtime2;
	private int stime1;
	private int stime2;
	private int correct;
	private String useChk;
	private String hint;
	private String comment;
	private String createDt;
	private String updateDt;

	private int acount;
	private int reqTotal;
	private int correctCnt;
	private int pubCnt;

	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getQuest() {
		return quest;
	}
	public void setQuest(String quest) {
		this.quest = quest;
	}
	public int getRtime1() {
		return rtime1;
	}
	public void setRtime1(int rtime1) {
		this.rtime1 = rtime1;
	}
	public int getRtime2() {
		return rtime2;
	}
	public void setRtime2(int rtime2) {
		this.rtime2 = rtime2;
	}
	public int getStime1() {
		return stime1;
	}
	public void setStime1(int stime1) {
		this.stime1 = stime1;
	}
	public int getStime2() {
		return stime2;
	}
	public void setStime2(int stime2) {
		this.stime2 = stime2;
	}
	public int getCorrect() {
		return correct;
	}
	public void setCorrect(int correct) {
		this.correct = correct;
	}
	public String getUseChk() {
		return useChk;
	}
	public void setUseChk(String useChk) {
		this.useChk = useChk;
	}
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCreateDt() {
		return createDt;
	}
	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}
	public String getUpdateDt() {
		return updateDt;
	}
	public void setUpdateDt(String updateDt) {
		this.updateDt = updateDt;
	}
	public int getAcount() {
		return acount;
	}
	public void setAcount(int acount) {
		this.acount = acount;
	}
	public int getReqTotal() {
		return reqTotal;
	}
	public void setReqTotal(int reqTotal) {
		this.reqTotal = reqTotal;
	}
	public int getCorrectCnt() {
		return correctCnt;
	}
	public void setCorrectCnt(int correctCnt) {
		this.correctCnt = correctCnt;
	}
	public int getPubCnt() {
		return pubCnt;
	}
	public void setPubCnt(int pubCnt) {
		this.pubCnt = pubCnt;
	}

}
