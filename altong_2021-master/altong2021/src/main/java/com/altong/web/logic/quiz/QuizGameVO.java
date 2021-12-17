package com.altong.web.logic.quiz;

public class QuizGameVO {
	private int uid;
	private String step;
	private String subject;
	private int admAlmoney;
	private int attendAlmoney;
	private int carryoverMoney;
	private String startYmd;
	private String startDtType; //오전/오후
	private String startDtH;
	private String startDtM;
	private String useChk;
	private String complete;
	private String createDt;
	private String updateDt;
	private int reqTotal;

	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getAdmAlmoney() {
		return admAlmoney;
	}
	public void setAdmAlmoney(int admAlmoney) {
		this.admAlmoney = admAlmoney;
	}
	public int getAttendAlmoney() {
		return attendAlmoney;
	}
	public void setAttendAlmoney(int attendAlmoney) {
		this.attendAlmoney = attendAlmoney;
	}
	public int getCarryoverMoney() {
		return carryoverMoney;
	}
	public void setCarryoverMoney(int carryoverMoney) {
		this.carryoverMoney = carryoverMoney;
	}
	public String getStartYmd() {
		return startYmd;
	}
	public void setStartYmd(String startYmd) {
		this.startYmd = startYmd;
	}
	public String getStartDtType() {
		return startDtType;
	}
	public void setStartDtType(String startDtType) {
		this.startDtType = startDtType;
	}
	public String getStartDtH() {
		return startDtH;
	}
	public void setStartDtH(String startDtH) {
		this.startDtH = startDtH;
	}
	public String getStartDtM() {
		return startDtM;
	}
	public void setStartDtM(String startDtM) {
		this.startDtM = startDtM;
	}
	public String getUseChk() {
		return useChk;
	}
	public void setUseChk(String useChk) {
		this.useChk = useChk;
	}
	public String getComplete() {
		return complete;
	}
	public void setComplete(String complete) {
		this.complete = complete;
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
	public int getReqTotal() {
		return reqTotal;
	}
	public void setReqTotal(int reqTotal) {
		this.reqTotal = reqTotal;
	}

}
