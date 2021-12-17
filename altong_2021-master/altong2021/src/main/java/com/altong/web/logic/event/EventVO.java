package com.altong.web.logic.event;

import java.math.BigDecimal;
import java.math.BigInteger;

public class EventVO {
	private Integer seq;
	private String startDate;
	private String endDate;
	private String bannerImg;
	private Integer flagUse;
	private Integer flagType;
	private Integer status;
	private BigInteger q_seq;
	private Integer ev_seq;
	private Integer section1;
	private Integer section2;
	private Integer section3;
	private String title;
	private String contents;
	private BigDecimal almoney;
	private Integer readCount;
	private String dateReg;
	private Integer answerCount;

	private String nation;

	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getBannerImg() {
		return bannerImg;
	}
	public void setBannerImg(String bannerImg) {
		this.bannerImg = bannerImg;
	}
	public Integer getFlagUse() {
		return flagUse;
	}
	public void setFlagUse(Integer flagUse) {
		this.flagUse = flagUse;
	}
	public Integer getFlagType() {
		return flagType;
	}
	public void setFlagType(Integer flagType) {
		this.flagType = flagType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public BigInteger getQ_seq() {
		return q_seq;
	}
	public void setQ_seq(BigInteger q_seq) {
		this.q_seq = q_seq;
	}
	public Integer getEv_seq() {
		return ev_seq;
	}
	public void setEv_seq(Integer ev_seq) {
		this.ev_seq = ev_seq;
	}
	public Integer getSection1() {
		return section1;
	}
	public void setSection1(Integer section1) {
		this.section1 = section1;
	}
	public Integer getSection2() {
		return section2;
	}
	public void setSection2(Integer section2) {
		this.section2 = section2;
	}
	public Integer getSection3() {
		return section3;
	}
	public void setSection3(Integer section3) {
		this.section3 = section3;
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
	public Integer getAnswerCount() {
		return answerCount;
	}
	public void setAnswerCount(Integer answerCount) {
		this.answerCount = answerCount;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}


}
