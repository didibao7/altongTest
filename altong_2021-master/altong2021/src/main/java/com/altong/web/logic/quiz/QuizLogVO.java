package com.altong.web.logic.quiz;

import java.math.BigDecimal;

public class QuizLogVO {
	private int uid;
	private int gqid;
	private int userSeq;
	private String success;
	private BigDecimal attendAlmoney;
	private String createDt;
	private String updateDt;

	private String name;
	private String nickName;
	private String memberType;
	private int lv;
	private String Phone;

	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getGqid() {
		return gqid;
	}
	public void setGqid(int gqid) {
		this.gqid = gqid;
	}
	public int getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public BigDecimal getAttendAlmoney() {
		return attendAlmoney;
	}
	public void setAttendAlmoney(BigDecimal attendAlmoney) {
		this.attendAlmoney = attendAlmoney;
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


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	public int getLv() {
		return lv;
	}
	public void setLv(int lv) {
		this.lv = lv;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}

}
