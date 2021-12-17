package com.altong.web.logic.member;

public class PartnerVO {
	private Integer seq;
	private Integer memberSeq;
	private Integer partnerSeq;
	private String flagPartner;
	private String dateReg;
	private String flagUse;
	private String unSetDateReg;
	
	
	/*
	 * V_MEMBERS 의 멤버 변수
	 * */
	private String nickName;
	private String photo;
	private Integer sumQ;
	private Integer sumA;
	private Integer countC;
	private Integer countQ;
	private Integer lv;
	
	public Integer getLv() {
		return lv;
	}
	public void setLv(Integer lv) {
		this.lv = lv;
	}
	private Integer cnt;
	
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
	public Integer getPartnerSeq() {
		return partnerSeq;
	}
	public void setPartnerSeq(Integer partnerSeq) {
		this.partnerSeq = partnerSeq;
	}
	public String getFlagPartner() {
		return flagPartner;
	}
	public void setFlagPartner(String flagPartner) {
		this.flagPartner = flagPartner;
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
	public String getUnSetDateReg() {
		return unSetDateReg;
	}
	public void setUnSetDateReg(String unSetDateReg) {
		this.unSetDateReg = unSetDateReg;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Integer getSumQ() {
		return sumQ;
	}
	public void setSumQ(Integer sumQ) {
		this.sumQ = sumQ;
	}
	public Integer getSumA() {
		return sumA;
	}
	public void setSumA(Integer sumA) {
		this.sumA = sumA;
	}
	public Integer getCountC() {
		return countC;
	}
	public void setCountC(Integer countC) {
		this.countC = countC;
	}
	public Integer getCountQ() {
		return countQ;
	}
	public void setCountQ(Integer countQ) {
		this.countQ = countQ;
	}
	public Integer getCnt() {
		return cnt;
	}
	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}
}
