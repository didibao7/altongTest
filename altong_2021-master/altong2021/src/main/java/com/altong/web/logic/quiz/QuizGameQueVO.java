package com.altong.web.logic.quiz;

public class QuizGameQueVO {
	private int uid;
	private int quid;	//QuizGame uid(퀴즈게임리스트)
	private int ord;	//정렬순서, 상위:1, ...
	private int queid;	//QuizQue uid(퀴즈리스트)
	private String quest;
	private String createDt;

	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getQuid() {
		return quid;
	}
	public void setQuid(int quid) {
		this.quid = quid;
	}
	public int getOrd() {
		return ord;
	}
	public void setOrd(int ord) {
		this.ord = ord;
	}
	public int getQueid() {
		return queid;
	}
	public void setQueid(int queid) {
		this.queid = queid;
	}
	public String getQuest() {
		return quest;
	}
	public void setQuest(String quest) {
		this.quest = quest;
	}
	public String getCreateDt() {
		return createDt;
	}
	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}

}
