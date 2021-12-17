package com.altong.web.logic.almoney;

import java.math.BigDecimal;

public class AlmoneyVO {
	private int userSeq;
	private String nickname;
	private BigDecimal almoney;
	private String conDate;

	private int returnCode;
	private String errText;
	private BigDecimal sumExtraAlmoney;

	private int rankQ;
	private int rankA;
	private int expense;
	private int imports;
	private String nation;

	public int getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public BigDecimal getAlmoney() {
		return almoney;
	}
	public void setAlmoney(BigDecimal almoney) {
		this.almoney = almoney;
	}
	public String getConDate() {
		return conDate;
	}
	public void setConDate(String conDate) {
		this.conDate = conDate;
	}

	public int getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}
	public String getErrText() {
		return errText;
	}
	public void setErrText(String errText) {
		this.errText = errText;
	}
	public BigDecimal getSumExtraAlmoney() {
		return sumExtraAlmoney;
	}
	public void setSumExtraAlmoney(BigDecimal sumExtraAlmoney) {
		this.sumExtraAlmoney = sumExtraAlmoney;
	}
	public int getRankQ() {
		return rankQ;
	}
	public void setRankQ(int rankQ) {
		this.rankQ = rankQ;
	}
	public int getRankA() {
		return rankA;
	}
	public void setRankA(int rankA) {
		this.rankA = rankA;
	}
	public int getExpense() {
		return expense;
	}
	public void setExpense(int expense) {
		this.expense = expense;
	}
	public int getImports() {
		return imports;
	}
	public void setImports(int imports) {
		this.imports = imports;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}


}
