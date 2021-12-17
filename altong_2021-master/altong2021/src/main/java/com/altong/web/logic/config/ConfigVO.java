package com.altong.web.logic.config;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class ConfigVO {
	private Integer seqNo;
	private Integer almoneyJoin;
	private Integer almoneyExchange;
	private Integer almoneyMoveQuestion;
	private Integer questionMin;
	private Integer questionMax;
	private Integer writeMax;
	private Integer fileMax;
	private String nickNotUse;
	private Integer viewMoneySum;
	private Integer viewMoneyQ;
	private Integer viewMoneyA;
	private Integer moneyCompany;
	private BigDecimal donationMoney;
	private BigDecimal chuCodeQ;
	private BigDecimal chuCodeA;
	private String memJoinCertType;
	private Integer memJoinSmsLimitCnt;
	private Integer memJoinSmsTimeOut;
	private Integer memPwSmsLimitCnt;
	private Integer lv1_LimitQueDayRegistCnt;
	private Integer lv1_LimitQueDayDupRegistCnt;
	private Integer lv1_LimitQueContinueRegistTime;
	private Integer lv1_LimitAnsDayRegistCnt;
	private Integer lv1_LimitAnsDayDupRegistCnt;
	private Integer lv1_LimitAnsContinueRegistTime;
	private Integer lv1_LimitRepDayRegistCnt;
	private Integer lv1_LimitRepDayDupRegistCnt;
	private Integer lv1_LimitRepContinueRegistTime;
	private Integer lv2_LimitQueDayRegistCnt;
	private Integer lv2_LimitQueDayDupRegistCnt;
	private Integer lv2_LimitQueContinueRegistTime;
	private Integer lv2_LimitAnsDayRegistCnt;
	private Integer lv2_LimitAnsDayDupRegistCnt;
	private Integer lv2_LimitAnsContinueRegistTime;
	private Integer lv2_LimitRepDayRegistCnt;
	private Integer lv2_LimitRepDayDupRegistCnt;
	private Integer lv2_LimitRepContinueRegistTime;
	private Integer lv3_LimitQueDayRegistCnt;
	private Integer lv3_LimitQueDayDupRegistCnt;
	private Integer lv3_LimitQueContinueRegistTime;
	private Integer lv3_LimitAnsDayRegistCnt;
	private Integer lv3_LimitAnsDayDupRegistCnt;
	private Integer lv3_LimitAnsContinueRegistTime;
	private Integer lv3_LimitRepDayRegistCnt;
	private Integer lv3_LimitRepDayDupRegistCnt;
	private Integer lv3_LimitRepContinueRegistTime;
	private Integer lv4_LimitQueDayRegistCnt;
	private Integer lv4_LimitQueDayDupRegistCnt;
	private Integer lv4_LimitQueContinueRegistTime;
	private Integer lv4_LimitAnsDayRegistCnt;
	private Integer lv4_LimitAnsDayDupRegistCnt;
	private Integer lv4_LimitAnsContinueRegistTime;
	private Integer lv4_LimitRepDayRegistCnt;
	private Integer lv4_LimitRepDayDupRegistCnt;
	private Integer lv4_LimitRepContinueRegistTime;
	private Integer lv5_LimitQueDayRegistCnt;
	private Integer lv5_LimitQueDayDupRegistCnt;
	private Integer lv5_LimitQueContinueRegistTime;
	private Integer lv5_LimitAnsDayRegistCnt;
	private Integer lv5_LimitAnsDayDupRegistCnt;
	private Integer lv5_LimitAnsContinueRegistTime;
	private Integer lv5_LimitRepDayRegistCnt;
	private Integer lv5_LimitRepDayDupRegistCnt;
	private Integer lv5_LimitRepContinueRegistTime;
	private String lv_LevelUpAuto_Yn;
	private String lv2_LevelUpAuto_Yn;
	private String lv3_LevelUpAuto_Yn;
	private String lv4_LevelUpAuto_Yn;
	private String lv5_LevelUpAuto_Yn;
	private Integer lv2_LimitJoinAfterDay;
	private BigDecimal lv2_LimitAlmoney;
	private Integer lv2_LimitAnsEstiCnt;
	private Integer lv2_LimitChuMemCnt;
	private String lv2_LimitPhotoYn;
	private Integer lv2_LimitReplyCnt;
	private Integer lv3_LimitLvMtPeriod;
	private Integer lv3_LimitQueRegistCnt;
	private Integer lv3_LimitAnsRegistCnt;
	private Integer lv3_LimitAnsChoiceCnt;
	private Integer lv3_LimitAnsEstiCnt;
	private Integer lv3_LimitAnsEstiPoint;
	private Integer lv3_LimitChuMemCnt;
	private Integer lv3_LimitReplyCnt;
	private Integer lv4_LimitLvMtPeriod;
	private Integer lv4_LimitQueRegistCnt;
	private Integer lv4_LimitAnsRegistCnt;
	private Integer lv4_LimitAnsChoiceCnt;
	private Integer lv4_LimitAnsEstiCnt;
	private Integer lv4_LimitAnsEstiPoint;
	private Integer lv4_LimitChuMemCnt;
	private Integer lv4_LimitReplyCnt;
	private Integer lv5_LimitLvMtPeriod;
	private Integer lv5_LimitQueRegistCnt;
	private Integer lv5_LimitAnsRegistCnt;
	private Integer lv5_LimitAnsChoiceCnt;
	private Integer lv5_LimitAnsEstiCnt;
	private Integer lv5_LimitAnsEstiPoint;
	private Integer lv5_LimitChuMemCnt;
	private Integer lv5_LimitReplyCnt;
	private BigDecimal lv2_ExchangeBaseDeductAlmoney;
	private BigDecimal lv2_ExchangeLimitAlmoney;
	private BigDecimal lv2_ExchangeBaseUnitAlmoney;
	private BigDecimal lv2_ExchangeRealMoneyDeductRate; // DecimalFormat 이용  자료) https://androidtest.tistory.com/47
	private BigDecimal lv3_ExchangeBaseDeductAlmoney;
	private BigDecimal lv3_ExchangeLimitAlmoney;
	private BigDecimal lv3_ExchangeBaseUnitAlmoney;
	private BigDecimal lv3_ExchangeRealMoneyDeductRate; // DecimalFormat 이용  자료) https://androidtest.tistory.com/47
	private BigDecimal lv4_ExchangeBaseDeductAlmoney;
	private BigDecimal lv4_ExchangeLimitAlmoney;
	private BigDecimal lv4_ExchangeBaseUnitAlmoney;
	private BigDecimal lv4_ExchangeRealMoneyDeductRate; // DecimalFormat 이용  자료) https://androidtest.tistory.com/47
	private BigDecimal lv5_ExchangeBaseDeductAlmoney;
	private BigDecimal lv5_ExchangeLimitAlmoney;
	private BigDecimal lv5_ExchangeBaseUnitAlmoney;
	private BigDecimal lv5_ExchangeRealMoneyDeductRate; // DecimalFormat 이용  자료) https://androidtest.tistory.com/47
	private Integer lv2_ExchangeMtPeriod;
	private Integer lv2_ExchangeQueRegistCnt;
	private Integer lv2_ExchangeAnsRegistCnt;
	private Integer lv2_ExchangeEstiCnt;
	private Integer lv2_ExchangeEstiPoint;
	private Integer lv3_ExchangeMtPeriod;
	private Integer lv3_ExchangeQueRegistCnt;
	private Integer lv3_ExchangeAnsRegistCnt;
	private Integer lv3_ExchangeEstiCnt;
	private Integer lv3_ExchangeEstiPoint;
	private Integer lv4_ExchangeMtPeriod;
	private Integer lv4_ExchangeQueRegistCnt;
	private Integer lv4_ExchangeAnsRegistCnt;
	private Integer lv4_ExchangeEstiCnt;
	private Integer lv4_ExchangeEstiPoint;
	private Integer lv5_ExchangeMtPeriod;
	private Integer lv5_ExchangeQueRegistCnt;
	private Integer lv5_ExchangeAnsRegistCnt;
	private Integer lv5_ExchangeEstiCnt;
	private Integer lv5_ExchangeEstiPoint;
	private String lv1_QueRegAlmoney;
	private String lv2_QueRegAlmoney;
	private String lv3_QueRegAlmoney;
	private String lv4_QueRegAlmoney;
	private String lv5_QueRegAlmoney;
	
	
	private Integer lv6_LimitQueDayRegistCnt;
	private Integer lv6_LimitQueDayDupRegistCnt;
	private Integer lv6_LimitQueContinueRegistTime;
	private Integer lv6_LimitAnsDayRegistCnt;
	private Integer lv6_LimitAnsDayDupRegistCnt;
	private Integer lv6_LimitAnsContinueRegistTime;
	private Integer lv6_LimitRepDayRegistCnt;
	private Integer lv6_LimitRepDayDupRegistCnt;
	private Integer lv6_LimitRepContinueRegistTime;
	private String lv6_LevelUpAuto_Yn;
	private Integer lv6_LimitAnsEstiCnt;
	private Integer lv6_LimitChuMemCnt;	
	private String lv6_LimitLvMtPeriod;
	private Integer lv6_LimitQueRegistCnt;
	private Integer lv6_LimitAnsRegistCnt;
	private Integer lv6_LimitAnsChoiceCnt;
	private Integer lv6_LimitAnsEstiPoint;
	private Integer lv6_LimitReplyCnt;
	private BigDecimal lv6_ExchangeBaseDeductAlmoney;
	private BigDecimal lv6_ExchangeLimitAlmoney;
	private BigDecimal lv6_ExchangeBaseUnitAlmoney;
	private float lv6_ExchangeRealMoneyDeductRate;
	private String lv6_ExchangeMtPeriod;
	private Integer lv6_ExchangeQueRegistCnt;
	private Integer lv6_ExchangeAnsRegistCnt;
	private Integer lv6_ExchangeEstiCnt;
	private Integer lv6_ExchangeEstiPoint;
	private String lv6_QueRegAlmoney;
	
	private Integer lv7_LimitQueDayRegistCnt;
	private Integer lv7_LimitQueDayDupRegistCnt;
	private Integer lv7_LimitQueContinueRegistTime;
	private Integer lv7_LimitAnsDayRegistCnt;
	private Integer lv7_LimitAnsDayDupRegistCnt;
	private Integer lv7_LimitAnsContinueRegistTime;
	private Integer lv7_LimitRepDayRegistCnt;
	private Integer lv7_LimitRepDayDupRegistCnt;
	private Integer lv7_LimitRepContinueRegistTime;
	private String lv7_LevelUpAuto_Yn;
	private Integer lv7_LimitAnsEstiCnt;
	private Integer lv7_LimitChuMemCnt;	
	private String lv7_LimitLvMtPeriod;
	private Integer lv7_LimitQueRegistCnt;
	private Integer lv7_LimitAnsRegistCnt;
	private Integer lv7_LimitAnsChoiceCnt;
	private Integer lv7_LimitAnsEstiPoint;
	private Integer lv7_LimitReplyCnt;
	private BigDecimal lv7_ExchangeBaseDeductAlmoney;
	private BigDecimal lv7_ExchangeLimitAlmoney;
	private BigDecimal lv7_ExchangeBaseUnitAlmoney;
	private float lv7_ExchangeRealMoneyDeductRate;
	private String lv7_ExchangeMtPeriod;
	private Integer lv7_ExchangeQueRegistCnt;
	private Integer lv7_ExchangeAnsRegistCnt;
	private Integer lv7_ExchangeEstiCnt;
	private Integer lv7_ExchangeEstiPoint;
	private String lv7_QueRegAlmoney;
	
	
	private Integer lv8_LimitQueDayRegistCnt;
	private Integer lv8_LimitQueDayDupRegistCnt;
	private Integer lv8_LimitQueContinueRegistTime;
	private Integer lv8_LimitAnsDayRegistCnt;
	private Integer lv8_LimitAnsDayDupRegistCnt;
	private Integer lv8_LimitAnsContinueRegistTime;
	private Integer lv8_LimitRepDayRegistCnt;
	private Integer lv8_LimitRepDayDupRegistCnt;
	private Integer lv8_LimitRepContinueRegistTime;
	private String lv8_LevelUpAuto_Yn;
	private Integer lv8_LimitAnsEstiCnt;
	private Integer lv8_LimitChuMemCnt;	
	private String lv8_LimitLvMtPeriod;
	private Integer lv8_LimitQueRegistCnt;
	private Integer lv8_LimitAnsRegistCnt;
	private Integer lv8_LimitAnsChoiceCnt;
	private Integer lv8_LimitAnsEstiPoint;
	private Integer lv8_LimitReplyCnt;
	private BigDecimal lv8_ExchangeBaseDeductAlmoney;
	private BigDecimal lv8_ExchangeLimitAlmoney;
	private BigDecimal lv8_ExchangeBaseUnitAlmoney;
	private float lv8_ExchangeRealMoneyDeductRate;
	private String lv8_ExchangeMtPeriod;
	private Integer lv8_ExchangeQueRegistCnt;
	private Integer lv8_ExchangeAnsRegistCnt;
	private Integer lv8_ExchangeEstiCnt;
	private Integer lv8_ExchangeEstiPoint;
	private String lv8_QueRegAlmoney;
	
	private Integer lv9_LimitQueDayRegistCnt;
	private Integer lv9_LimitQueDayDupRegistCnt;
	private Integer lv9_LimitQueContinueRegistTime;
	private Integer lv9_LimitAnsDayRegistCnt;
	private Integer lv9_LimitAnsDayDupRegistCnt;
	private Integer lv9_LimitAnsContinueRegistTime;
	private Integer lv9_LimitRepDayRegistCnt;
	private Integer lv9_LimitRepDayDupRegistCnt;
	private Integer lv9_LimitRepContinueRegistTime;
	private String lv9_LevelUpAuto_Yn;
	private Integer lv9_LimitAnsEstiCnt;
	private Integer lv9_LimitChuMemCnt;	
	private String lv9_LimitLvMtPeriod;
	private Integer lv9_LimitQueRegistCnt;
	private Integer lv9_LimitAnsRegistCnt;
	private Integer lv9_LimitAnsChoiceCnt;
	private Integer lv9_LimitAnsEstiPoint;
	private Integer lv9_LimitReplyCnt;
	private BigDecimal lv9_ExchangeBaseDeductAlmoney;
	private BigDecimal lv9_ExchangeLimitAlmoney;
	private BigDecimal lv9_ExchangeBaseUnitAlmoney;
	private float lv9_ExchangeRealMoneyDeductRate;
	private String lv9_ExchangeMtPeriod;
	private Integer lv9_ExchangeQueRegistCnt;
	private Integer lv9_ExchangeAnsRegistCnt;
	private Integer lv9_ExchangeEstiCnt;
	private Integer lv9_ExchangeEstiPoint;
	private String lv9_QueRegAlmoney;
	
	private Integer lv10_LimitQueDayRegistCnt;
	private Integer lv10_LimitQueDayDupRegistCnt;
	private Integer lv10_LimitQueContinueRegistTime;
	private Integer lv10_LimitAnsDayRegistCnt;
	private Integer lv10_LimitAnsDayDupRegistCnt;
	private Integer lv10_LimitAnsContinueRegistTime;
	private Integer lv10_LimitRepDayRegistCnt;
	private Integer lv10_LimitRepDayDupRegistCnt;
	private Integer lv10_LimitRepContinueRegistTime;
	private String lv10_LevelUpAuto_Yn;
	private Integer lv10_LimitAnsEstiCnt;
	private Integer lv10_LimitChuMemCnt;	
	private String lv10_LimitLvMtPeriod;
	private Integer lv10_LimitQueRegistCnt;
	private Integer lv10_LimitAnsRegistCnt;
	private Integer lv10_LimitAnsChoiceCnt;
	private Integer lv10_LimitAnsEstiPoint;
	private Integer lv10_LimitReplyCnt;
	private BigDecimal lv10_ExchangeBaseDeductAlmoney;
	private BigDecimal lv10_ExchangeLimitAlmoney;
	private BigDecimal lv10_ExchangeBaseUnitAlmoney;
	private float lv10_ExchangeRealMoneyDeductRate;
	private String lv10_ExchangeMtPeriod;
	private Integer lv10_ExchangeQueRegistCnt;
	private Integer lv10_ExchangeAnsRegistCnt;
	private Integer lv10_ExchangeEstiCnt;
	private Integer lv10_ExchangeEstiPoint;
	private String lv10_QueRegAlmoney;
	
	private Integer lv11_LimitQueDayRegistCnt;
	private Integer lv11_LimitQueDayDupRegistCnt;
	private Integer lv11_LimitQueContinueRegistTime;
	private Integer lv11_LimitAnsDayRegistCnt;
	private Integer lv11_LimitAnsDayDupRegistCnt;
	private Integer lv11_LimitAnsContinueRegistTime;
	private Integer lv11_LimitRepDayRegistCnt;
	private Integer lv11_LimitRepDayDupRegistCnt;
	private Integer lv11_LimitRepContinueRegistTime;
	private String lv11_LevelUpAuto_Yn;
	private Integer lv11_LimitAnsEstiCnt;
	private Integer lv11_LimitChuMemCnt;	
	private String lv11_LimitLvMtPeriod;
	private Integer lv11_LimitQueRegistCnt;
	private Integer lv11_LimitAnsRegistCnt;
	private Integer lv11_LimitAnsChoiceCnt;
	private Integer lv11_LimitAnsEstiPoint;
	private Integer lv11_LimitReplyCnt;
	private BigDecimal lv11_ExchangeBaseDeductAlmoney;
	private BigDecimal lv11_ExchangeLimitAlmoney;
	private BigDecimal lv11_ExchangeBaseUnitAlmoney;
	private float lv11_ExchangeRealMoneyDeductRate;
	private String lv11_ExchangeMtPeriod;
	private Integer lv11_ExchangeQueRegistCnt;
	private Integer lv11_ExchangeAnsRegistCnt;
	private Integer lv11_ExchangeEstiCnt;
	private Integer lv11_ExchangeEstiPoint;
	private String lv11_QueRegAlmoney;
	
	
	public Integer getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}
	public Integer getAlmoneyJoin() {
		return almoneyJoin;
	}
	public void setAlmoneyJoin(Integer almoneyJoin) {
		this.almoneyJoin = almoneyJoin;
	}
	public Integer getAlmoneyExchange() {
		return almoneyExchange;
	}
	public void setAlmoneyExchange(Integer almoneyExchange) {
		this.almoneyExchange = almoneyExchange;
	}
	public Integer getAlmoneyMoveQuestion() {
		return almoneyMoveQuestion;
	}
	public void setAlmoneyMoveQuestion(Integer almoneyMoveQuestion) {
		this.almoneyMoveQuestion = almoneyMoveQuestion;
	}
	public Integer getQuestionMin() {
		return questionMin;
	}
	public void setQuestionMin(Integer questionMin) {
		this.questionMin = questionMin;
	}
	public Integer getQuestionMax() {
		return questionMax;
	}
	public void setQuestionMax(Integer questionMax) {
		this.questionMax = questionMax;
	}
	public Integer getWriteMax() {
		return writeMax;
	}
	public void setWriteMax(Integer writeMax) {
		this.writeMax = writeMax;
	}
	public Integer getFileMax() {
		return fileMax;
	}
	public void setFileMax(Integer fileMax) {
		this.fileMax = fileMax;
	}
	public String getNickNotUse() {
		return nickNotUse;
	}
	public void setNickNotUse(String nickNotUse) {
		this.nickNotUse = nickNotUse;
	}
	public Integer getViewMoneySum() {
		return viewMoneySum;
	}
	public void setViewMoneySum(Integer viewMoneySum) {
		this.viewMoneySum = viewMoneySum;
	}
	public Integer getViewMoneyQ() {
		return viewMoneyQ;
	}
	public void setViewMoneyQ(Integer viewMoneyQ) {
		this.viewMoneyQ = viewMoneyQ;
	}
	public Integer getViewMoneyA() {
		return viewMoneyA;
	}
	public void setViewMoneyA(Integer viewMoneyA) {
		this.viewMoneyA = viewMoneyA;
	}
	public Integer getMoneyCompany() {
		return moneyCompany;
	}
	public void setMoneyCompany(Integer moneyCompany) {
		this.moneyCompany = moneyCompany;
	}
	public BigDecimal getDonationMoney() {
		return donationMoney;
	}
	public void setDonationMoney(BigDecimal donationMoney) {
		this.donationMoney = donationMoney;
	}
	public BigDecimal getChuCodeQ() {
		return chuCodeQ;
	}
	public void setChuCodeQ(BigDecimal chuCodeQ) {
		this.chuCodeQ = chuCodeQ;
	}
	public BigDecimal getChuCodeA() {
		return chuCodeA;
	}
	public void setChuCodeA(BigDecimal chuCodeA) {
		this.chuCodeA = chuCodeA;
	}
	public String getMemJoinCertType() {
		return memJoinCertType;
	}
	public void setMemJoinCertType(String memJoinCertType) {
		this.memJoinCertType = memJoinCertType;
	}
	public Integer getMemJoinSmsLimitCnt() {
		return memJoinSmsLimitCnt;
	}
	public void setMemJoinSmsLimitCnt(Integer memJoinSmsLimitCnt) {
		this.memJoinSmsLimitCnt = memJoinSmsLimitCnt;
	}
	public Integer getMemJoinSmsTimeOut() {
		return memJoinSmsTimeOut;
	}
	public void setMemJoinSmsTimeOut(Integer memJoinSmsTimeOut) {
		this.memJoinSmsTimeOut = memJoinSmsTimeOut;
	}
	public Integer getMemPwSmsLimitCnt() {
		return memPwSmsLimitCnt;
	}
	public void setMemPwSmsLimitCnt(Integer memPwSmsLimitCnt) {
		this.memPwSmsLimitCnt = memPwSmsLimitCnt;
	}
	public Integer getLv1_LimitQueDayRegistCnt() {
		return lv1_LimitQueDayRegistCnt;
	}
	public void setLv1_LimitQueDayRegistCnt(Integer lv1_LimitQueDayRegistCnt) {
		this.lv1_LimitQueDayRegistCnt = lv1_LimitQueDayRegistCnt;
	}
	public Integer getLv1_LimitQueDayDupRegistCnt() {
		return lv1_LimitQueDayDupRegistCnt;
	}
	public void setLv1_LimitQueDayDupRegistCnt(Integer lv1_LimitQueDayDupRegistCnt) {
		this.lv1_LimitQueDayDupRegistCnt = lv1_LimitQueDayDupRegistCnt;
	}
	public Integer getLv1_LimitQueContinueRegistTime() {
		return lv1_LimitQueContinueRegistTime;
	}
	public void setLv1_LimitQueContinueRegistTime(Integer lv1_LimitQueContinueRegistTime) {
		this.lv1_LimitQueContinueRegistTime = lv1_LimitQueContinueRegistTime;
	}
	public Integer getLv1_LimitAnsDayRegistCnt() {
		return lv1_LimitAnsDayRegistCnt;
	}
	public void setLv1_LimitAnsDayRegistCnt(Integer lv1_LimitAnsDayRegistCnt) {
		this.lv1_LimitAnsDayRegistCnt = lv1_LimitAnsDayRegistCnt;
	}
	public Integer getLv1_LimitAnsDayDupRegistCnt() {
		return lv1_LimitAnsDayDupRegistCnt;
	}
	public void setLv1_LimitAnsDayDupRegistCnt(Integer lv1_LimitAnsDayDupRegistCnt) {
		this.lv1_LimitAnsDayDupRegistCnt = lv1_LimitAnsDayDupRegistCnt;
	}
	public Integer getLv1_LimitAnsContinueRegistTime() {
		return lv1_LimitAnsContinueRegistTime;
	}
	public void setLv1_LimitAnsContinueRegistTime(Integer lv1_LimitAnsContinueRegistTime) {
		this.lv1_LimitAnsContinueRegistTime = lv1_LimitAnsContinueRegistTime;
	}
	public Integer getLv1_LimitRepDayRegistCnt() {
		return lv1_LimitRepDayRegistCnt;
	}
	public void setLv1_LimitRepDayRegistCnt(Integer lv1_LimitRepDayRegistCnt) {
		this.lv1_LimitRepDayRegistCnt = lv1_LimitRepDayRegistCnt;
	}
	public Integer getLv1_LimitRepDayDupRegistCnt() {
		return lv1_LimitRepDayDupRegistCnt;
	}
	public void setLv1_LimitRepDayDupRegistCnt(Integer lv1_LimitRepDayDupRegistCnt) {
		this.lv1_LimitRepDayDupRegistCnt = lv1_LimitRepDayDupRegistCnt;
	}
	public Integer getLv1_LimitRepContinueRegistTime() {
		return lv1_LimitRepContinueRegistTime;
	}
	public void setLv1_LimitRepContinueRegistTime(Integer lv1_LimitRepContinueRegistTime) {
		this.lv1_LimitRepContinueRegistTime = lv1_LimitRepContinueRegistTime;
	}
	public Integer getLv2_LimitQueDayRegistCnt() {
		return lv2_LimitQueDayRegistCnt;
	}
	public void setLv2_LimitQueDayRegistCnt(Integer lv2_LimitQueDayRegistCnt) {
		this.lv2_LimitQueDayRegistCnt = lv2_LimitQueDayRegistCnt;
	}
	public Integer getLv2_LimitQueDayDupRegistCnt() {
		return lv2_LimitQueDayDupRegistCnt;
	}
	public void setLv2_LimitQueDayDupRegistCnt(Integer lv2_LimitQueDayDupRegistCnt) {
		this.lv2_LimitQueDayDupRegistCnt = lv2_LimitQueDayDupRegistCnt;
	}
	public Integer getLv2_LimitQueContinueRegistTime() {
		return lv2_LimitQueContinueRegistTime;
	}
	public void setLv2_LimitQueContinueRegistTime(Integer lv2_LimitQueContinueRegistTime) {
		this.lv2_LimitQueContinueRegistTime = lv2_LimitQueContinueRegistTime;
	}
	public Integer getLv2_LimitAnsDayRegistCnt() {
		return lv2_LimitAnsDayRegistCnt;
	}
	public void setLv2_LimitAnsDayRegistCnt(Integer lv2_LimitAnsDayRegistCnt) {
		this.lv2_LimitAnsDayRegistCnt = lv2_LimitAnsDayRegistCnt;
	}
	public Integer getLv2_LimitAnsDayDupRegistCnt() {
		return lv2_LimitAnsDayDupRegistCnt;
	}
	public void setLv2_LimitAnsDayDupRegistCnt(Integer lv2_LimitAnsDayDupRegistCnt) {
		this.lv2_LimitAnsDayDupRegistCnt = lv2_LimitAnsDayDupRegistCnt;
	}
	public Integer getLv2_LimitAnsContinueRegistTime() {
		return lv2_LimitAnsContinueRegistTime;
	}
	public void setLv2_LimitAnsContinueRegistTime(Integer lv2_LimitAnsContinueRegistTime) {
		this.lv2_LimitAnsContinueRegistTime = lv2_LimitAnsContinueRegistTime;
	}
	public Integer getLv2_LimitRepDayRegistCnt() {
		return lv2_LimitRepDayRegistCnt;
	}
	public void setLv2_LimitRepDayRegistCnt(Integer lv2_LimitRepDayRegistCnt) {
		this.lv2_LimitRepDayRegistCnt = lv2_LimitRepDayRegistCnt;
	}
	public Integer getLv2_LimitRepDayDupRegistCnt() {
		return lv2_LimitRepDayDupRegistCnt;
	}
	public void setLv2_LimitRepDayDupRegistCnt(Integer lv2_LimitRepDayDupRegistCnt) {
		this.lv2_LimitRepDayDupRegistCnt = lv2_LimitRepDayDupRegistCnt;
	}
	public Integer getLv2_LimitRepContinueRegistTime() {
		return lv2_LimitRepContinueRegistTime;
	}
	public void setLv2_LimitRepContinueRegistTime(Integer lv2_LimitRepContinueRegistTime) {
		this.lv2_LimitRepContinueRegistTime = lv2_LimitRepContinueRegistTime;
	}
	public Integer getLv3_LimitQueDayRegistCnt() {
		return lv3_LimitQueDayRegistCnt;
	}
	public void setLv3_LimitQueDayRegistCnt(Integer lv3_LimitQueDayRegistCnt) {
		this.lv3_LimitQueDayRegistCnt = lv3_LimitQueDayRegistCnt;
	}
	public Integer getLv3_LimitQueDayDupRegistCnt() {
		return lv3_LimitQueDayDupRegistCnt;
	}
	public void setLv3_LimitQueDayDupRegistCnt(Integer lv3_LimitQueDayDupRegistCnt) {
		this.lv3_LimitQueDayDupRegistCnt = lv3_LimitQueDayDupRegistCnt;
	}
	public Integer getLv3_LimitQueContinueRegistTime() {
		return lv3_LimitQueContinueRegistTime;
	}
	public void setLv3_LimitQueContinueRegistTime(Integer lv3_LimitQueContinueRegistTime) {
		this.lv3_LimitQueContinueRegistTime = lv3_LimitQueContinueRegistTime;
	}
	public Integer getLv3_LimitAnsDayRegistCnt() {
		return lv3_LimitAnsDayRegistCnt;
	}
	public void setLv3_LimitAnsDayRegistCnt(Integer lv3_LimitAnsDayRegistCnt) {
		this.lv3_LimitAnsDayRegistCnt = lv3_LimitAnsDayRegistCnt;
	}
	public Integer getLv3_LimitAnsDayDupRegistCnt() {
		return lv3_LimitAnsDayDupRegistCnt;
	}
	public void setLv3_LimitAnsDayDupRegistCnt(Integer lv3_LimitAnsDayDupRegistCnt) {
		this.lv3_LimitAnsDayDupRegistCnt = lv3_LimitAnsDayDupRegistCnt;
	}
	public Integer getLv3_LimitAnsContinueRegistTime() {
		return lv3_LimitAnsContinueRegistTime;
	}
	public void setLv3_LimitAnsContinueRegistTime(Integer lv3_LimitAnsContinueRegistTime) {
		this.lv3_LimitAnsContinueRegistTime = lv3_LimitAnsContinueRegistTime;
	}
	public Integer getLv3_LimitRepDayRegistCnt() {
		return lv3_LimitRepDayRegistCnt;
	}
	public void setLv3_LimitRepDayRegistCnt(Integer lv3_LimitRepDayRegistCnt) {
		this.lv3_LimitRepDayRegistCnt = lv3_LimitRepDayRegistCnt;
	}
	public Integer getLv3_LimitRepDayDupRegistCnt() {
		return lv3_LimitRepDayDupRegistCnt;
	}
	public void setLv3_LimitRepDayDupRegistCnt(Integer lv3_LimitRepDayDupRegistCnt) {
		this.lv3_LimitRepDayDupRegistCnt = lv3_LimitRepDayDupRegistCnt;
	}
	public Integer getLv3_LimitRepContinueRegistTime() {
		return lv3_LimitRepContinueRegistTime;
	}
	public void setLv3_LimitRepContinueRegistTime(Integer lv3_LimitRepContinueRegistTime) {
		this.lv3_LimitRepContinueRegistTime = lv3_LimitRepContinueRegistTime;
	}
	public Integer getLv4_LimitQueDayRegistCnt() {
		return lv4_LimitQueDayRegistCnt;
	}
	public void setLv4_LimitQueDayRegistCnt(Integer lv4_LimitQueDayRegistCnt) {
		this.lv4_LimitQueDayRegistCnt = lv4_LimitQueDayRegistCnt;
	}
	public Integer getLv4_LimitQueDayDupRegistCnt() {
		return lv4_LimitQueDayDupRegistCnt;
	}
	public void setLv4_LimitQueDayDupRegistCnt(Integer lv4_LimitQueDayDupRegistCnt) {
		this.lv4_LimitQueDayDupRegistCnt = lv4_LimitQueDayDupRegistCnt;
	}
	public Integer getLv4_LimitQueContinueRegistTime() {
		return lv4_LimitQueContinueRegistTime;
	}
	public void setLv4_LimitQueContinueRegistTime(Integer lv4_LimitQueContinueRegistTime) {
		this.lv4_LimitQueContinueRegistTime = lv4_LimitQueContinueRegistTime;
	}
	public Integer getLv4_LimitAnsDayRegistCnt() {
		return lv4_LimitAnsDayRegistCnt;
	}
	public void setLv4_LimitAnsDayRegistCnt(Integer lv4_LimitAnsDayRegistCnt) {
		this.lv4_LimitAnsDayRegistCnt = lv4_LimitAnsDayRegistCnt;
	}
	public Integer getLv4_LimitAnsDayDupRegistCnt() {
		return lv4_LimitAnsDayDupRegistCnt;
	}
	public void setLv4_LimitAnsDayDupRegistCnt(Integer lv4_LimitAnsDayDupRegistCnt) {
		this.lv4_LimitAnsDayDupRegistCnt = lv4_LimitAnsDayDupRegistCnt;
	}
	public Integer getLv4_LimitAnsContinueRegistTime() {
		return lv4_LimitAnsContinueRegistTime;
	}
	public void setLv4_LimitAnsContinueRegistTime(Integer lv4_LimitAnsContinueRegistTime) {
		this.lv4_LimitAnsContinueRegistTime = lv4_LimitAnsContinueRegistTime;
	}
	public Integer getLv4_LimitRepDayRegistCnt() {
		return lv4_LimitRepDayRegistCnt;
	}
	public void setLv4_LimitRepDayRegistCnt(Integer lv4_LimitRepDayRegistCnt) {
		this.lv4_LimitRepDayRegistCnt = lv4_LimitRepDayRegistCnt;
	}
	public Integer getLv4_LimitRepDayDupRegistCnt() {
		return lv4_LimitRepDayDupRegistCnt;
	}
	public void setLv4_LimitRepDayDupRegistCnt(Integer lv4_LimitRepDayDupRegistCnt) {
		this.lv4_LimitRepDayDupRegistCnt = lv4_LimitRepDayDupRegistCnt;
	}
	public Integer getLv4_LimitRepContinueRegistTime() {
		return lv4_LimitRepContinueRegistTime;
	}
	public void setLv4_LimitRepContinueRegistTime(Integer lv4_LimitRepContinueRegistTime) {
		this.lv4_LimitRepContinueRegistTime = lv4_LimitRepContinueRegistTime;
	}
	public Integer getLv5_LimitQueDayRegistCnt() {
		return lv5_LimitQueDayRegistCnt;
	}
	public void setLv5_LimitQueDayRegistCnt(Integer lv5_LimitQueDayRegistCnt) {
		this.lv5_LimitQueDayRegistCnt = lv5_LimitQueDayRegistCnt;
	}
	public Integer getLv5_LimitQueDayDupRegistCnt() {
		return lv5_LimitQueDayDupRegistCnt;
	}
	public void setLv5_LimitQueDayDupRegistCnt(Integer lv5_LimitQueDayDupRegistCnt) {
		this.lv5_LimitQueDayDupRegistCnt = lv5_LimitQueDayDupRegistCnt;
	}
	public Integer getLv5_LimitQueContinueRegistTime() {
		return lv5_LimitQueContinueRegistTime;
	}
	public void setLv5_LimitQueContinueRegistTime(Integer lv5_LimitQueContinueRegistTime) {
		this.lv5_LimitQueContinueRegistTime = lv5_LimitQueContinueRegistTime;
	}
	public Integer getLv5_LimitAnsDayRegistCnt() {
		return lv5_LimitAnsDayRegistCnt;
	}
	public void setLv5_LimitAnsDayRegistCnt(Integer lv5_LimitAnsDayRegistCnt) {
		this.lv5_LimitAnsDayRegistCnt = lv5_LimitAnsDayRegistCnt;
	}
	public Integer getLv5_LimitAnsDayDupRegistCnt() {
		return lv5_LimitAnsDayDupRegistCnt;
	}
	public void setLv5_LimitAnsDayDupRegistCnt(Integer lv5_LimitAnsDayDupRegistCnt) {
		this.lv5_LimitAnsDayDupRegistCnt = lv5_LimitAnsDayDupRegistCnt;
	}
	public Integer getLv5_LimitAnsContinueRegistTime() {
		return lv5_LimitAnsContinueRegistTime;
	}
	public void setLv5_LimitAnsContinueRegistTime(Integer lv5_LimitAnsContinueRegistTime) {
		this.lv5_LimitAnsContinueRegistTime = lv5_LimitAnsContinueRegistTime;
	}
	public Integer getLv5_LimitRepDayRegistCnt() {
		return lv5_LimitRepDayRegistCnt;
	}
	public void setLv5_LimitRepDayRegistCnt(Integer lv5_LimitRepDayRegistCnt) {
		this.lv5_LimitRepDayRegistCnt = lv5_LimitRepDayRegistCnt;
	}
	public Integer getLv5_LimitRepDayDupRegistCnt() {
		return lv5_LimitRepDayDupRegistCnt;
	}
	public void setLv5_LimitRepDayDupRegistCnt(Integer lv5_LimitRepDayDupRegistCnt) {
		this.lv5_LimitRepDayDupRegistCnt = lv5_LimitRepDayDupRegistCnt;
	}
	public Integer getLv5_LimitRepContinueRegistTime() {
		return lv5_LimitRepContinueRegistTime;
	}
	public void setLv5_LimitRepContinueRegistTime(Integer lv5_LimitRepContinueRegistTime) {
		this.lv5_LimitRepContinueRegistTime = lv5_LimitRepContinueRegistTime;
	}
	public String getLv_LevelUpAuto_Yn() {
		return lv_LevelUpAuto_Yn;
	}
	public void setLv_LevelUpAuto_Yn(String lv_LevelUpAuto_Yn) {
		this.lv_LevelUpAuto_Yn = lv_LevelUpAuto_Yn;
	}
	public String getLv2_LevelUpAuto_Yn() {
		return lv2_LevelUpAuto_Yn;
	}
	public void setLv2_LevelUpAuto_Yn(String lv2_LevelUpAuto_Yn) {
		this.lv2_LevelUpAuto_Yn = lv2_LevelUpAuto_Yn;
	}
	public String getLv3_LevelUpAuto_Yn() {
		return lv3_LevelUpAuto_Yn;
	}
	public void setLv3_LevelUpAuto_Yn(String lv3_LevelUpAuto_Yn) {
		this.lv3_LevelUpAuto_Yn = lv3_LevelUpAuto_Yn;
	}
	public String getLv4_LevelUpAuto_Yn() {
		return lv4_LevelUpAuto_Yn;
	}
	public void setLv4_LevelUpAuto_Yn(String lv4_LevelUpAuto_Yn) {
		this.lv4_LevelUpAuto_Yn = lv4_LevelUpAuto_Yn;
	}
	public String getLv5_LevelUpAuto_Yn() {
		return lv5_LevelUpAuto_Yn;
	}
	public void setLv5_LevelUpAuto_Yn(String lv5_LevelUpAuto_Yn) {
		this.lv5_LevelUpAuto_Yn = lv5_LevelUpAuto_Yn;
	}
	public Integer getLv2_LimitJoinAfterDay() {
		return lv2_LimitJoinAfterDay;
	}
	public void setLv2_LimitJoinAfterDay(Integer lv2_LimitJoinAfterDay) {
		this.lv2_LimitJoinAfterDay = lv2_LimitJoinAfterDay;
	}
	public BigDecimal getLv2_LimitAlmoney() {
		return lv2_LimitAlmoney;
	}
	public void setLv2_LimitAlmoney(BigDecimal lv2_LimitAlmoney) {
		this.lv2_LimitAlmoney = lv2_LimitAlmoney;
	}
	public Integer getLv2_LimitAnsEstiCnt() {
		return lv2_LimitAnsEstiCnt;
	}
	public void setLv2_LimitAnsEstiCnt(Integer lv2_LimitAnsEstiCnt) {
		this.lv2_LimitAnsEstiCnt = lv2_LimitAnsEstiCnt;
	}
	public Integer getLv2_LimitChuMemCnt() {
		return lv2_LimitChuMemCnt;
	}
	public void setLv2_LimitChuMemCnt(Integer lv2_LimitChuMemCnt) {
		this.lv2_LimitChuMemCnt = lv2_LimitChuMemCnt;
	}
	public String getLv2_LimitPhotoYn() {
		return lv2_LimitPhotoYn;
	}
	public void setLv2_LimitPhotoYn(String lv2_LimitPhotoYn) {
		this.lv2_LimitPhotoYn = lv2_LimitPhotoYn;
	}
	public Integer getLv2_LimitReplyCnt() {
		return lv2_LimitReplyCnt;
	}
	public void setLv2_LimitReplyCnt(Integer lv2_LimitReplyCnt) {
		this.lv2_LimitReplyCnt = lv2_LimitReplyCnt;
	}
	public Integer getLv3_LimitLvMtPeriod() {
		return lv3_LimitLvMtPeriod;
	}
	public void setLv3_LimitLvMtPeriod(Integer lv3_LimitLvMtPeriod) {
		this.lv3_LimitLvMtPeriod = lv3_LimitLvMtPeriod;
	}
	public Integer getLv3_LimitQueRegistCnt() {
		return lv3_LimitQueRegistCnt;
	}
	public void setLv3_LimitQueRegistCnt(Integer lv3_LimitQueRegistCnt) {
		this.lv3_LimitQueRegistCnt = lv3_LimitQueRegistCnt;
	}
	public Integer getLv3_LimitAnsRegistCnt() {
		return lv3_LimitAnsRegistCnt;
	}
	public void setLv3_LimitAnsRegistCnt(Integer lv3_LimitAnsRegistCnt) {
		this.lv3_LimitAnsRegistCnt = lv3_LimitAnsRegistCnt;
	}
	public Integer getLv3_LimitAnsChoiceCnt() {
		return lv3_LimitAnsChoiceCnt;
	}
	public void setLv3_LimitAnsChoiceCnt(Integer lv3_LimitAnsChoiceCnt) {
		this.lv3_LimitAnsChoiceCnt = lv3_LimitAnsChoiceCnt;
	}
	public Integer getLv3_LimitAnsEstiCnt() {
		return lv3_LimitAnsEstiCnt;
	}
	public void setLv3_LimitAnsEstiCnt(Integer lv3_LimitAnsEstiCnt) {
		this.lv3_LimitAnsEstiCnt = lv3_LimitAnsEstiCnt;
	}
	public Integer getLv3_LimitAnsEstiPoint() {
		return lv3_LimitAnsEstiPoint;
	}
	public void setLv3_LimitAnsEstiPoint(Integer lv3_LimitAnsEstiPoint) {
		this.lv3_LimitAnsEstiPoint = lv3_LimitAnsEstiPoint;
	}
	public Integer getLv3_LimitChuMemCnt() {
		return lv3_LimitChuMemCnt;
	}
	public void setLv3_LimitChuMemCnt(Integer lv3_LimitChuMemCnt) {
		this.lv3_LimitChuMemCnt = lv3_LimitChuMemCnt;
	}
	public Integer getLv3_LimitReplyCnt() {
		return lv3_LimitReplyCnt;
	}
	public void setLv3_LimitReplyCnt(Integer lv3_LimitReplyCnt) {
		this.lv3_LimitReplyCnt = lv3_LimitReplyCnt;
	}
	public Integer getLv4_LimitLvMtPeriod() {
		return lv4_LimitLvMtPeriod;
	}
	public void setLv4_LimitLvMtPeriod(Integer lv4_LimitLvMtPeriod) {
		this.lv4_LimitLvMtPeriod = lv4_LimitLvMtPeriod;
	}
	public Integer getLv4_LimitQueRegistCnt() {
		return lv4_LimitQueRegistCnt;
	}
	public void setLv4_LimitQueRegistCnt(Integer lv4_LimitQueRegistCnt) {
		this.lv4_LimitQueRegistCnt = lv4_LimitQueRegistCnt;
	}
	public Integer getLv4_LimitAnsRegistCnt() {
		return lv4_LimitAnsRegistCnt;
	}
	public void setLv4_LimitAnsRegistCnt(Integer lv4_LimitAnsRegistCnt) {
		this.lv4_LimitAnsRegistCnt = lv4_LimitAnsRegistCnt;
	}
	public Integer getLv4_LimitAnsChoiceCnt() {
		return lv4_LimitAnsChoiceCnt;
	}
	public void setLv4_LimitAnsChoiceCnt(Integer lv4_LimitAnsChoiceCnt) {
		this.lv4_LimitAnsChoiceCnt = lv4_LimitAnsChoiceCnt;
	}
	public Integer getLv4_LimitAnsEstiCnt() {
		return lv4_LimitAnsEstiCnt;
	}
	public void setLv4_LimitAnsEstiCnt(Integer lv4_LimitAnsEstiCnt) {
		this.lv4_LimitAnsEstiCnt = lv4_LimitAnsEstiCnt;
	}
	public Integer getLv4_LimitAnsEstiPoint() {
		return lv4_LimitAnsEstiPoint;
	}
	public void setLv4_LimitAnsEstiPoint(Integer lv4_LimitAnsEstiPoint) {
		this.lv4_LimitAnsEstiPoint = lv4_LimitAnsEstiPoint;
	}
	public Integer getLv4_LimitChuMemCnt() {
		return lv4_LimitChuMemCnt;
	}
	public void setLv4_LimitChuMemCnt(Integer lv4_LimitChuMemCnt) {
		this.lv4_LimitChuMemCnt = lv4_LimitChuMemCnt;
	}
	public Integer getLv4_LimitReplyCnt() {
		return lv4_LimitReplyCnt;
	}
	public void setLv4_LimitReplyCnt(Integer lv4_LimitReplyCnt) {
		this.lv4_LimitReplyCnt = lv4_LimitReplyCnt;
	}
	public Integer getLv5_LimitLvMtPeriod() {
		return lv5_LimitLvMtPeriod;
	}
	public void setLv5_LimitLvMtPeriod(Integer lv5_LimitLvMtPeriod) {
		this.lv5_LimitLvMtPeriod = lv5_LimitLvMtPeriod;
	}
	public Integer getLv5_LimitQueRegistCnt() {
		return lv5_LimitQueRegistCnt;
	}
	public void setLv5_LimitQueRegistCnt(Integer lv5_LimitQueRegistCnt) {
		this.lv5_LimitQueRegistCnt = lv5_LimitQueRegistCnt;
	}
	public Integer getLv5_LimitAnsRegistCnt() {
		return lv5_LimitAnsRegistCnt;
	}
	public void setLv5_LimitAnsRegistCnt(Integer lv5_LimitAnsRegistCnt) {
		this.lv5_LimitAnsRegistCnt = lv5_LimitAnsRegistCnt;
	}
	public Integer getLv5_LimitAnsChoiceCnt() {
		return lv5_LimitAnsChoiceCnt;
	}
	public void setLv5_LimitAnsChoiceCnt(Integer lv5_LimitAnsChoiceCnt) {
		this.lv5_LimitAnsChoiceCnt = lv5_LimitAnsChoiceCnt;
	}
	public Integer getLv5_LimitAnsEstiCnt() {
		return lv5_LimitAnsEstiCnt;
	}
	public void setLv5_LimitAnsEstiCnt(Integer lv5_LimitAnsEstiCnt) {
		this.lv5_LimitAnsEstiCnt = lv5_LimitAnsEstiCnt;
	}
	public Integer getLv5_LimitAnsEstiPoint() {
		return lv5_LimitAnsEstiPoint;
	}
	public void setLv5_LimitAnsEstiPoint(Integer lv5_LimitAnsEstiPoint) {
		this.lv5_LimitAnsEstiPoint = lv5_LimitAnsEstiPoint;
	}
	public Integer getLv5_LimitChuMemCnt() {
		return lv5_LimitChuMemCnt;
	}
	public void setLv5_LimitChuMemCnt(Integer lv5_LimitChuMemCnt) {
		this.lv5_LimitChuMemCnt = lv5_LimitChuMemCnt;
	}
	public Integer getLv5_LimitReplyCnt() {
		return lv5_LimitReplyCnt;
	}
	public void setLv5_LimitReplyCnt(Integer lv5_LimitReplyCnt) {
		this.lv5_LimitReplyCnt = lv5_LimitReplyCnt;
	}
	public BigDecimal getLv2_ExchangeBaseDeductAlmoney() {
		return lv2_ExchangeBaseDeductAlmoney;
	}
	public void setLv2_ExchangeBaseDeductAlmoney(BigDecimal lv2_ExchangeBaseDeductAlmoney) {
		this.lv2_ExchangeBaseDeductAlmoney = lv2_ExchangeBaseDeductAlmoney;
	}
	public BigDecimal getLv2_ExchangeLimitAlmoney() {
		return lv2_ExchangeLimitAlmoney;
	}
	public void setLv2_ExchangeLimitAlmoney(BigDecimal lv2_ExchangeLimitAlmoney) {
		this.lv2_ExchangeLimitAlmoney = lv2_ExchangeLimitAlmoney;
	}
	public BigDecimal getLv2_ExchangeBaseUnitAlmoney() {
		return lv2_ExchangeBaseUnitAlmoney;
	}
	public void setLv2_ExchangeBaseUnitAlmoney(BigDecimal lv2_ExchangeBaseUnitAlmoney) {
		this.lv2_ExchangeBaseUnitAlmoney = lv2_ExchangeBaseUnitAlmoney;
	}
	
	// DecimalFormat 이용  자료) https://androidtest.tistory.com/47
	public BigDecimal getLv2_ExchangeRealMoneyDeductRate() {
		return lv2_ExchangeRealMoneyDeductRate;
	}
	// DecimalFormat 이용  자료) https://androidtest.tistory.com/47
	public void setLv2_ExchangeRealMoneyDeductRate(BigDecimal lv2_ExchangeRealMoneyDeductRate) {
		this.lv2_ExchangeRealMoneyDeductRate = lv2_ExchangeRealMoneyDeductRate;
	}
	public BigDecimal getLv3_ExchangeBaseDeductAlmoney() {
		return lv3_ExchangeBaseDeductAlmoney;
	}
	public void setLv3_ExchangeBaseDeductAlmoney(BigDecimal lv3_ExchangeBaseDeductAlmoney) {
		this.lv3_ExchangeBaseDeductAlmoney = lv3_ExchangeBaseDeductAlmoney;
	}
	public BigDecimal getLv3_ExchangeLimitAlmoney() {
		return lv3_ExchangeLimitAlmoney;
	}
	public void setLv3_ExchangeLimitAlmoney(BigDecimal lv3_ExchangeLimitAlmoney) {
		this.lv3_ExchangeLimitAlmoney = lv3_ExchangeLimitAlmoney;
	}
	public BigDecimal getLv3_ExchangeBaseUnitAlmoney() {
		return lv3_ExchangeBaseUnitAlmoney;
	}
	public void setLv3_ExchangeBaseUnitAlmoney(BigDecimal lv3_ExchangeBaseUnitAlmoney) {
		this.lv3_ExchangeBaseUnitAlmoney = lv3_ExchangeBaseUnitAlmoney;
	}
	
	// DecimalFormat 이용  자료) https://androidtest.tistory.com/47
	public BigDecimal getLv3_ExchangeRealMoneyDeductRate() {
		return lv3_ExchangeRealMoneyDeductRate;
	}
	// DecimalFormat 이용  자료) https://androidtest.tistory.com/47
	public void setLv3_ExchangeRealMoneyDeductRate(BigDecimal lv3_ExchangeRealMoneyDeductRate) {
		this.lv3_ExchangeRealMoneyDeductRate = lv3_ExchangeRealMoneyDeductRate;
	}
	public BigDecimal getLv4_ExchangeBaseDeductAlmoney() {
		return lv4_ExchangeBaseDeductAlmoney;
	}
	public void setLv4_ExchangeBaseDeductAlmoney(BigDecimal lv4_ExchangeBaseDeductAlmoney) {
		this.lv4_ExchangeBaseDeductAlmoney = lv4_ExchangeBaseDeductAlmoney;
	}
	public BigDecimal getLv4_ExchangeLimitAlmoney() {
		return lv4_ExchangeLimitAlmoney;
	}
	public void setLv4_ExchangeLimitAlmoney(BigDecimal lv4_ExchangeLimitAlmoney) {
		this.lv4_ExchangeLimitAlmoney = lv4_ExchangeLimitAlmoney;
	}
	public BigDecimal getLv4_ExchangeBaseUnitAlmoney() {
		return lv4_ExchangeBaseUnitAlmoney;
	}
	public void setLv4_ExchangeBaseUnitAlmoney(BigDecimal lv4_ExchangeBaseUnitAlmoney) {
		this.lv4_ExchangeBaseUnitAlmoney = lv4_ExchangeBaseUnitAlmoney;
	}
	
	// DecimalFormat 이용  자료) https://androidtest.tistory.com/47
	public BigDecimal getLv4_ExchangeRealMoneyDeductRate() {
		//final DecimalFormat df = new DecimalFormat("#####.##");
		//df.format(lv4_ExchangeRealMoneyDeductRate);
		return lv4_ExchangeRealMoneyDeductRate;
	}
	// DecimalFormat 이용  자료) https://androidtest.tistory.com/47
	public void setLv4_ExchangeRealMoneyDeductRate(BigDecimal lv4_ExchangeRealMoneyDeductRate) {
		this.lv4_ExchangeRealMoneyDeductRate = lv4_ExchangeRealMoneyDeductRate;
	}
	public BigDecimal getLv5_ExchangeBaseDeductAlmoney() {
		return lv5_ExchangeBaseDeductAlmoney;
	}
	public void setLv5_ExchangeBaseDeductAlmoney(BigDecimal lv5_ExchangeBaseDeductAlmoney) {
		this.lv5_ExchangeBaseDeductAlmoney = lv5_ExchangeBaseDeductAlmoney;
	}
	public BigDecimal getLv5_ExchangeLimitAlmoney() {
		return lv5_ExchangeLimitAlmoney;
	}
	public void setLv5_ExchangeLimitAlmoney(BigDecimal lv5_ExchangeLimitAlmoney) {
		this.lv5_ExchangeLimitAlmoney = lv5_ExchangeLimitAlmoney;
	}
	public BigDecimal getLv5_ExchangeBaseUnitAlmoney() {
		return lv5_ExchangeBaseUnitAlmoney;
	}
	public void setLv5_ExchangeBaseUnitAlmoney(BigDecimal lv5_ExchangeBaseUnitAlmoney) {
		this.lv5_ExchangeBaseUnitAlmoney = lv5_ExchangeBaseUnitAlmoney;
	}
	
	// DecimalFormat 이용  자료) https://androidtest.tistory.com/47
	public BigDecimal getLv5_ExchangeRealMoneyDeductRate() {
		return lv5_ExchangeRealMoneyDeductRate;
	}
	// DecimalFormat 이용  자료) https://androidtest.tistory.com/47
	public void setLv5_ExchangeRealMoneyDeductRate(BigDecimal lv5_ExchangeRealMoneyDeductRate) {
		this.lv5_ExchangeRealMoneyDeductRate = lv5_ExchangeRealMoneyDeductRate;
	}
	public Integer getLv2_ExchangeMtPeriod() {
		return lv2_ExchangeMtPeriod;
	}
	public void setLv2_ExchangeMtPeriod(Integer lv2_ExchangeMtPeriod) {
		this.lv2_ExchangeMtPeriod = lv2_ExchangeMtPeriod;
	}
	public Integer getLv2_ExchangeQueRegistCnt() {
		return lv2_ExchangeQueRegistCnt;
	}
	public void setLv2_ExchangeQueRegistCnt(Integer lv2_ExchangeQueRegistCnt) {
		this.lv2_ExchangeQueRegistCnt = lv2_ExchangeQueRegistCnt;
	}
	public Integer getLv2_ExchangeAnsRegistCnt() {
		return lv2_ExchangeAnsRegistCnt;
	}
	public void setLv2_ExchangeAnsRegistCnt(Integer lv2_ExchangeAnsRegistCnt) {
		this.lv2_ExchangeAnsRegistCnt = lv2_ExchangeAnsRegistCnt;
	}
	public Integer getLv2_ExchangeEstiCnt() {
		return lv2_ExchangeEstiCnt;
	}
	public void setLv2_ExchangeEstiCnt(Integer lv2_ExchangeEstiCnt) {
		this.lv2_ExchangeEstiCnt = lv2_ExchangeEstiCnt;
	}
	public Integer getLv2_ExchangeEstiPoint() {
		return lv2_ExchangeEstiPoint;
	}
	public void setLv2_ExchangeEstiPoint(Integer lv2_ExchangeEstiPoint) {
		this.lv2_ExchangeEstiPoint = lv2_ExchangeEstiPoint;
	}
	public Integer getLv3_ExchangeMtPeriod() {
		return lv3_ExchangeMtPeriod;
	}
	public void setLv3_ExchangeMtPeriod(Integer lv3_ExchangeMtPeriod) {
		this.lv3_ExchangeMtPeriod = lv3_ExchangeMtPeriod;
	}
	public Integer getLv3_ExchangeQueRegistCnt() {
		return lv3_ExchangeQueRegistCnt;
	}
	public void setLv3_ExchangeQueRegistCnt(Integer lv3_ExchangeQueRegistCnt) {
		this.lv3_ExchangeQueRegistCnt = lv3_ExchangeQueRegistCnt;
	}
	public Integer getLv3_ExchangeAnsRegistCnt() {
		return lv3_ExchangeAnsRegistCnt;
	}
	public void setLv3_ExchangeAnsRegistCnt(Integer lv3_ExchangeAnsRegistCnt) {
		this.lv3_ExchangeAnsRegistCnt = lv3_ExchangeAnsRegistCnt;
	}
	public Integer getLv3_ExchangeEstiCnt() {
		return lv3_ExchangeEstiCnt;
	}
	public void setLv3_ExchangeEstiCnt(Integer lv3_ExchangeEstiCnt) {
		this.lv3_ExchangeEstiCnt = lv3_ExchangeEstiCnt;
	}
	public Integer getLv3_ExchangeEstiPoint() {
		return lv3_ExchangeEstiPoint;
	}
	public void setLv3_ExchangeEstiPoint(Integer lv3_ExchangeEstiPoint) {
		this.lv3_ExchangeEstiPoint = lv3_ExchangeEstiPoint;
	}
	public Integer getLv4_ExchangeMtPeriod() {
		return lv4_ExchangeMtPeriod;
	}
	public void setLv4_ExchangeMtPeriod(Integer lv4_ExchangeMtPeriod) {
		this.lv4_ExchangeMtPeriod = lv4_ExchangeMtPeriod;
	}
	public Integer getLv4_ExchangeQueRegistCnt() {
		return lv4_ExchangeQueRegistCnt;
	}
	public void setLv4_ExchangeQueRegistCnt(Integer lv4_ExchangeQueRegistCnt) {
		this.lv4_ExchangeQueRegistCnt = lv4_ExchangeQueRegistCnt;
	}
	public Integer getLv4_ExchangeAnsRegistCnt() {
		return lv4_ExchangeAnsRegistCnt;
	}
	public void setLv4_ExchangeAnsRegistCnt(Integer lv4_ExchangeAnsRegistCnt) {
		this.lv4_ExchangeAnsRegistCnt = lv4_ExchangeAnsRegistCnt;
	}
	public Integer getLv4_ExchangeEstiCnt() {
		return lv4_ExchangeEstiCnt;
	}
	public void setLv4_ExchangeEstiCnt(Integer lv4_ExchangeEstiCnt) {
		this.lv4_ExchangeEstiCnt = lv4_ExchangeEstiCnt;
	}
	public Integer getLv4_ExchangeEstiPoint() {
		return lv4_ExchangeEstiPoint;
	}
	public void setLv4_ExchangeEstiPoint(Integer lv4_ExchangeEstiPoint) {
		this.lv4_ExchangeEstiPoint = lv4_ExchangeEstiPoint;
	}
	public Integer getLv5_ExchangeMtPeriod() {
		return lv5_ExchangeMtPeriod;
	}
	public void setLv5_ExchangeMtPeriod(Integer lv5_ExchangeMtPeriod) {
		this.lv5_ExchangeMtPeriod = lv5_ExchangeMtPeriod;
	}
	public Integer getLv5_ExchangeQueRegistCnt() {
		return lv5_ExchangeQueRegistCnt;
	}
	public void setLv5_ExchangeQueRegistCnt(Integer lv5_ExchangeQueRegistCnt) {
		this.lv5_ExchangeQueRegistCnt = lv5_ExchangeQueRegistCnt;
	}
	public Integer getLv5_ExchangeAnsRegistCnt() {
		return lv5_ExchangeAnsRegistCnt;
	}
	public void setLv5_ExchangeAnsRegistCnt(Integer lv5_ExchangeAnsRegistCnt) {
		this.lv5_ExchangeAnsRegistCnt = lv5_ExchangeAnsRegistCnt;
	}
	public Integer getLv5_ExchangeEstiCnt() {
		return lv5_ExchangeEstiCnt;
	}
	public void setLv5_ExchangeEstiCnt(Integer lv5_ExchangeEstiCnt) {
		this.lv5_ExchangeEstiCnt = lv5_ExchangeEstiCnt;
	}
	public Integer getLv5_ExchangeEstiPoint() {
		return lv5_ExchangeEstiPoint;
	}
	public void setLv5_ExchangeEstiPoint(Integer lv5_ExchangeEstiPoint) {
		this.lv5_ExchangeEstiPoint = lv5_ExchangeEstiPoint;
	}
	public String getLv1_QueRegAlmoney() {
		return lv1_QueRegAlmoney;
	}
	public void setLv1_QueRegAlmoney(String lv1_QueRegAlmoney) {
		this.lv1_QueRegAlmoney = lv1_QueRegAlmoney;
	}
	public String getLv2_QueRegAlmoney() {
		return lv2_QueRegAlmoney;
	}
	public void setLv2_QueRegAlmoney(String lv2_QueRegAlmoney) {
		this.lv2_QueRegAlmoney = lv2_QueRegAlmoney;
	}
	public String getLv3_QueRegAlmoney() {
		return lv3_QueRegAlmoney;
	}
	public void setLv3_QueRegAlmoney(String lv3_QueRegAlmoney) {
		this.lv3_QueRegAlmoney = lv3_QueRegAlmoney;
	}
	public String getLv4_QueRegAlmoney() {
		return lv4_QueRegAlmoney;
	}
	public void setLv4_QueRegAlmoney(String lv4_QueRegAlmoney) {
		this.lv4_QueRegAlmoney = lv4_QueRegAlmoney;
	}
	public String getLv5_QueRegAlmoney() {
		return lv5_QueRegAlmoney;
	}
	public void setLv5_QueRegAlmoney(String lv5_QueRegAlmoney) {
		this.lv5_QueRegAlmoney = lv5_QueRegAlmoney;
	}
	public Integer getLv6_LimitQueDayRegistCnt() {
		return lv6_LimitQueDayRegistCnt;
	}
	public void setLv6_LimitQueDayRegistCnt(Integer lv6_LimitQueDayRegistCnt) {
		this.lv6_LimitQueDayRegistCnt = lv6_LimitQueDayRegistCnt;
	}
	public Integer getLv6_LimitQueDayDupRegistCnt() {
		return lv6_LimitQueDayDupRegistCnt;
	}
	public void setLv6_LimitQueDayDupRegistCnt(Integer lv6_LimitQueDayDupRegistCnt) {
		this.lv6_LimitQueDayDupRegistCnt = lv6_LimitQueDayDupRegistCnt;
	}
	public Integer getLv6_LimitQueContinueRegistTime() {
		return lv6_LimitQueContinueRegistTime;
	}
	public void setLv6_LimitQueContinueRegistTime(Integer lv6_LimitQueContinueRegistTime) {
		this.lv6_LimitQueContinueRegistTime = lv6_LimitQueContinueRegistTime;
	}
	public Integer getLv6_LimitAnsDayRegistCnt() {
		return lv6_LimitAnsDayRegistCnt;
	}
	public void setLv6_LimitAnsDayRegistCnt(Integer lv6_LimitAnsDayRegistCnt) {
		this.lv6_LimitAnsDayRegistCnt = lv6_LimitAnsDayRegistCnt;
	}
	public Integer getLv6_LimitAnsDayDupRegistCnt() {
		return lv6_LimitAnsDayDupRegistCnt;
	}
	public void setLv6_LimitAnsDayDupRegistCnt(Integer lv6_LimitAnsDayDupRegistCnt) {
		this.lv6_LimitAnsDayDupRegistCnt = lv6_LimitAnsDayDupRegistCnt;
	}
	public Integer getLv6_LimitAnsContinueRegistTime() {
		return lv6_LimitAnsContinueRegistTime;
	}
	public void setLv6_LimitAnsContinueRegistTime(Integer lv6_LimitAnsContinueRegistTime) {
		this.lv6_LimitAnsContinueRegistTime = lv6_LimitAnsContinueRegistTime;
	}
	public Integer getLv6_LimitRepDayRegistCnt() {
		return lv6_LimitRepDayRegistCnt;
	}
	public void setLv6_LimitRepDayRegistCnt(Integer lv6_LimitRepDayRegistCnt) {
		this.lv6_LimitRepDayRegistCnt = lv6_LimitRepDayRegistCnt;
	}
	public Integer getLv6_LimitRepDayDupRegistCnt() {
		return lv6_LimitRepDayDupRegistCnt;
	}
	public void setLv6_LimitRepDayDupRegistCnt(Integer lv6_LimitRepDayDupRegistCnt) {
		this.lv6_LimitRepDayDupRegistCnt = lv6_LimitRepDayDupRegistCnt;
	}
	public Integer getLv6_LimitRepContinueRegistTime() {
		return lv6_LimitRepContinueRegistTime;
	}
	public void setLv6_LimitRepContinueRegistTime(Integer lv6_LimitRepContinueRegistTime) {
		this.lv6_LimitRepContinueRegistTime = lv6_LimitRepContinueRegistTime;
	}
	public String getLv6_LevelUpAuto_Yn() {
		return lv6_LevelUpAuto_Yn;
	}
	public void setLv6_LevelUpAuto_Yn(String lv6_LevelUpAuto_Yn) {
		this.lv6_LevelUpAuto_Yn = lv6_LevelUpAuto_Yn;
	}
	public Integer getLv6_LimitAnsEstiCnt() {
		return lv6_LimitAnsEstiCnt;
	}
	public void setLv6_LimitAnsEstiCnt(Integer lv6_LimitAnsEstiCnt) {
		this.lv6_LimitAnsEstiCnt = lv6_LimitAnsEstiCnt;
	}
	public Integer getLv6_LimitChuMemCnt() {
		return lv6_LimitChuMemCnt;
	}
	public void setLv6_LimitChuMemCnt(Integer lv6_LimitChuMemCnt) {
		this.lv6_LimitChuMemCnt = lv6_LimitChuMemCnt;
	}
	public String getLv6_LimitLvMtPeriod() {
		return lv6_LimitLvMtPeriod;
	}
	public void setLv6_LimitLvMtPeriod(String lv6_LimitLvMtPeriod) {
		this.lv6_LimitLvMtPeriod = lv6_LimitLvMtPeriod;
	}
	public Integer getLv6_LimitQueRegistCnt() {
		return lv6_LimitQueRegistCnt;
	}
	public void setLv6_LimitQueRegistCnt(Integer lv6_LimitQueRegistCnt) {
		this.lv6_LimitQueRegistCnt = lv6_LimitQueRegistCnt;
	}
	public Integer getLv6_LimitAnsRegistCnt() {
		return lv6_LimitAnsRegistCnt;
	}
	public void setLv6_LimitAnsRegistCnt(Integer lv6_LimitAnsRegistCnt) {
		this.lv6_LimitAnsRegistCnt = lv6_LimitAnsRegistCnt;
	}
	public Integer getLv6_LimitAnsChoiceCnt() {
		return lv6_LimitAnsChoiceCnt;
	}
	public void setLv6_LimitAnsChoiceCnt(Integer lv6_LimitAnsChoiceCnt) {
		this.lv6_LimitAnsChoiceCnt = lv6_LimitAnsChoiceCnt;
	}
	public Integer getLv6_LimitAnsEstiPoint() {
		return lv6_LimitAnsEstiPoint;
	}
	public void setLv6_LimitAnsEstiPoint(Integer lv6_LimitAnsEstiPoint) {
		this.lv6_LimitAnsEstiPoint = lv6_LimitAnsEstiPoint;
	}
	public Integer getLv6_LimitReplyCnt() {
		return lv6_LimitReplyCnt;
	}
	public void setLv6_LimitReplyCnt(Integer lv6_LimitReplyCnt) {
		this.lv6_LimitReplyCnt = lv6_LimitReplyCnt;
	}
	public BigDecimal getLv6_ExchangeBaseDeductAlmoney() {
		return lv6_ExchangeBaseDeductAlmoney;
	}
	public void setLv6_ExchangeBaseDeductAlmoney(BigDecimal lv6_ExchangeBaseDeductAlmoney) {
		this.lv6_ExchangeBaseDeductAlmoney = lv6_ExchangeBaseDeductAlmoney;
	}
	public BigDecimal getLv6_ExchangeLimitAlmoney() {
		return lv6_ExchangeLimitAlmoney;
	}
	public void setLv6_ExchangeLimitAlmoney(BigDecimal lv6_ExchangeLimitAlmoney) {
		this.lv6_ExchangeLimitAlmoney = lv6_ExchangeLimitAlmoney;
	}
	public BigDecimal getLv6_ExchangeBaseUnitAlmoney() {
		return lv6_ExchangeBaseUnitAlmoney;
	}
	public void setLv6_ExchangeBaseUnitAlmoney(BigDecimal lv6_ExchangeBaseUnitAlmoney) {
		this.lv6_ExchangeBaseUnitAlmoney = lv6_ExchangeBaseUnitAlmoney;
	}
	public float getLv6_ExchangeRealMoneyDeductRate() {
		return lv6_ExchangeRealMoneyDeductRate;
	}
	public void setLv6_ExchangeRealMoneyDeductRate(float lv6_ExchangeRealMoneyDeductRate) {
		this.lv6_ExchangeRealMoneyDeductRate = lv6_ExchangeRealMoneyDeductRate;
	}
	public String getLv6_ExchangeMtPeriod() {
		return lv6_ExchangeMtPeriod;
	}
	public void setLv6_ExchangeMtPeriod(String lv6_ExchangeMtPeriod) {
		this.lv6_ExchangeMtPeriod = lv6_ExchangeMtPeriod;
	}
	public Integer getLv6_ExchangeQueRegistCnt() {
		return lv6_ExchangeQueRegistCnt;
	}
	public void setLv6_ExchangeQueRegistCnt(Integer lv6_ExchangeQueRegistCnt) {
		this.lv6_ExchangeQueRegistCnt = lv6_ExchangeQueRegistCnt;
	}
	public Integer getLv6_ExchangeAnsRegistCnt() {
		return lv6_ExchangeAnsRegistCnt;
	}
	public void setLv6_ExchangeAnsRegistCnt(Integer lv6_ExchangeAnsRegistCnt) {
		this.lv6_ExchangeAnsRegistCnt = lv6_ExchangeAnsRegistCnt;
	}
	public Integer getLv6_ExchangeEstiCnt() {
		return lv6_ExchangeEstiCnt;
	}
	public void setLv6_ExchangeEstiCnt(Integer lv6_ExchangeEstiCnt) {
		this.lv6_ExchangeEstiCnt = lv6_ExchangeEstiCnt;
	}
	public Integer getLv6_ExchangeEstiPoint() {
		return lv6_ExchangeEstiPoint;
	}
	public void setLv6_ExchangeEstiPoint(Integer lv6_ExchangeEstiPoint) {
		this.lv6_ExchangeEstiPoint = lv6_ExchangeEstiPoint;
	}
	public String getLv6_QueRegAlmoney() {
		return lv6_QueRegAlmoney;
	}
	public void setLv6_QueRegAlmoney(String lv6_QueRegAlmoney) {
		this.lv6_QueRegAlmoney = lv6_QueRegAlmoney;
	}
	public Integer getLv7_LimitQueDayRegistCnt() {
		return lv7_LimitQueDayRegistCnt;
	}
	public void setLv7_LimitQueDayRegistCnt(Integer lv7_LimitQueDayRegistCnt) {
		this.lv7_LimitQueDayRegistCnt = lv7_LimitQueDayRegistCnt;
	}
	public Integer getLv7_LimitQueDayDupRegistCnt() {
		return lv7_LimitQueDayDupRegistCnt;
	}
	public void setLv7_LimitQueDayDupRegistCnt(Integer lv7_LimitQueDayDupRegistCnt) {
		this.lv7_LimitQueDayDupRegistCnt = lv7_LimitQueDayDupRegistCnt;
	}
	public Integer getLv7_LimitQueContinueRegistTime() {
		return lv7_LimitQueContinueRegistTime;
	}
	public void setLv7_LimitQueContinueRegistTime(Integer lv7_LimitQueContinueRegistTime) {
		this.lv7_LimitQueContinueRegistTime = lv7_LimitQueContinueRegistTime;
	}
	public Integer getLv7_LimitAnsDayRegistCnt() {
		return lv7_LimitAnsDayRegistCnt;
	}
	public void setLv7_LimitAnsDayRegistCnt(Integer lv7_LimitAnsDayRegistCnt) {
		this.lv7_LimitAnsDayRegistCnt = lv7_LimitAnsDayRegistCnt;
	}
	public Integer getLv7_LimitAnsDayDupRegistCnt() {
		return lv7_LimitAnsDayDupRegistCnt;
	}
	public void setLv7_LimitAnsDayDupRegistCnt(Integer lv7_LimitAnsDayDupRegistCnt) {
		this.lv7_LimitAnsDayDupRegistCnt = lv7_LimitAnsDayDupRegistCnt;
	}
	public Integer getLv7_LimitAnsContinueRegistTime() {
		return lv7_LimitAnsContinueRegistTime;
	}
	public void setLv7_LimitAnsContinueRegistTime(Integer lv7_LimitAnsContinueRegistTime) {
		this.lv7_LimitAnsContinueRegistTime = lv7_LimitAnsContinueRegistTime;
	}
	public Integer getLv7_LimitRepDayRegistCnt() {
		return lv7_LimitRepDayRegistCnt;
	}
	public void setLv7_LimitRepDayRegistCnt(Integer lv7_LimitRepDayRegistCnt) {
		this.lv7_LimitRepDayRegistCnt = lv7_LimitRepDayRegistCnt;
	}
	public Integer getLv7_LimitRepDayDupRegistCnt() {
		return lv7_LimitRepDayDupRegistCnt;
	}
	public void setLv7_LimitRepDayDupRegistCnt(Integer lv7_LimitRepDayDupRegistCnt) {
		this.lv7_LimitRepDayDupRegistCnt = lv7_LimitRepDayDupRegistCnt;
	}
	public Integer getLv7_LimitRepContinueRegistTime() {
		return lv7_LimitRepContinueRegistTime;
	}
	public void setLv7_LimitRepContinueRegistTime(Integer lv7_LimitRepContinueRegistTime) {
		this.lv7_LimitRepContinueRegistTime = lv7_LimitRepContinueRegistTime;
	}
	public String getLv7_LevelUpAuto_Yn() {
		return lv7_LevelUpAuto_Yn;
	}
	public void setLv7_LevelUpAuto_Yn(String lv7_LevelUpAuto_Yn) {
		this.lv7_LevelUpAuto_Yn = lv7_LevelUpAuto_Yn;
	}
	public Integer getLv7_LimitAnsEstiCnt() {
		return lv7_LimitAnsEstiCnt;
	}
	public void setLv7_LimitAnsEstiCnt(Integer lv7_LimitAnsEstiCnt) {
		this.lv7_LimitAnsEstiCnt = lv7_LimitAnsEstiCnt;
	}
	public Integer getLv7_LimitChuMemCnt() {
		return lv7_LimitChuMemCnt;
	}
	public void setLv7_LimitChuMemCnt(Integer lv7_LimitChuMemCnt) {
		this.lv7_LimitChuMemCnt = lv7_LimitChuMemCnt;
	}
	public String getLv7_LimitLvMtPeriod() {
		return lv7_LimitLvMtPeriod;
	}
	public void setLv7_LimitLvMtPeriod(String lv7_LimitLvMtPeriod) {
		this.lv7_LimitLvMtPeriod = lv7_LimitLvMtPeriod;
	}
	public Integer getLv7_LimitQueRegistCnt() {
		return lv7_LimitQueRegistCnt;
	}
	public void setLv7_LimitQueRegistCnt(Integer lv7_LimitQueRegistCnt) {
		this.lv7_LimitQueRegistCnt = lv7_LimitQueRegistCnt;
	}
	public Integer getLv7_LimitAnsRegistCnt() {
		return lv7_LimitAnsRegistCnt;
	}
	public void setLv7_LimitAnsRegistCnt(Integer lv7_LimitAnsRegistCnt) {
		this.lv7_LimitAnsRegistCnt = lv7_LimitAnsRegistCnt;
	}
	public Integer getLv7_LimitAnsChoiceCnt() {
		return lv7_LimitAnsChoiceCnt;
	}
	public void setLv7_LimitAnsChoiceCnt(Integer lv7_LimitAnsChoiceCnt) {
		this.lv7_LimitAnsChoiceCnt = lv7_LimitAnsChoiceCnt;
	}
	public Integer getLv7_LimitAnsEstiPoint() {
		return lv7_LimitAnsEstiPoint;
	}
	public void setLv7_LimitAnsEstiPoint(Integer lv7_LimitAnsEstiPoint) {
		this.lv7_LimitAnsEstiPoint = lv7_LimitAnsEstiPoint;
	}
	public Integer getLv7_LimitReplyCnt() {
		return lv7_LimitReplyCnt;
	}
	public void setLv7_LimitReplyCnt(Integer lv7_LimitReplyCnt) {
		this.lv7_LimitReplyCnt = lv7_LimitReplyCnt;
	}
	public BigDecimal getLv7_ExchangeBaseDeductAlmoney() {
		return lv7_ExchangeBaseDeductAlmoney;
	}
	public void setLv7_ExchangeBaseDeductAlmoney(BigDecimal lv7_ExchangeBaseDeductAlmoney) {
		this.lv7_ExchangeBaseDeductAlmoney = lv7_ExchangeBaseDeductAlmoney;
	}
	public BigDecimal getLv7_ExchangeLimitAlmoney() {
		return lv7_ExchangeLimitAlmoney;
	}
	public void setLv7_ExchangeLimitAlmoney(BigDecimal lv7_ExchangeLimitAlmoney) {
		this.lv7_ExchangeLimitAlmoney = lv7_ExchangeLimitAlmoney;
	}
	public BigDecimal getLv7_ExchangeBaseUnitAlmoney() {
		return lv7_ExchangeBaseUnitAlmoney;
	}
	public void setLv7_ExchangeBaseUnitAlmoney(BigDecimal lv7_ExchangeBaseUnitAlmoney) {
		this.lv7_ExchangeBaseUnitAlmoney = lv7_ExchangeBaseUnitAlmoney;
	}
	public float getLv7_ExchangeRealMoneyDeductRate() {
		return lv7_ExchangeRealMoneyDeductRate;
	}
	public void setLv7_ExchangeRealMoneyDeductRate(float lv7_ExchangeRealMoneyDeductRate) {
		this.lv7_ExchangeRealMoneyDeductRate = lv7_ExchangeRealMoneyDeductRate;
	}
	public String getLv7_ExchangeMtPeriod() {
		return lv7_ExchangeMtPeriod;
	}
	public void setLv7_ExchangeMtPeriod(String lv7_ExchangeMtPeriod) {
		this.lv7_ExchangeMtPeriod = lv7_ExchangeMtPeriod;
	}
	public Integer getLv7_ExchangeQueRegistCnt() {
		return lv7_ExchangeQueRegistCnt;
	}
	public void setLv7_ExchangeQueRegistCnt(Integer lv7_ExchangeQueRegistCnt) {
		this.lv7_ExchangeQueRegistCnt = lv7_ExchangeQueRegistCnt;
	}
	public Integer getLv7_ExchangeAnsRegistCnt() {
		return lv7_ExchangeAnsRegistCnt;
	}
	public void setLv7_ExchangeAnsRegistCnt(Integer lv7_ExchangeAnsRegistCnt) {
		this.lv7_ExchangeAnsRegistCnt = lv7_ExchangeAnsRegistCnt;
	}
	public Integer getLv7_ExchangeEstiCnt() {
		return lv7_ExchangeEstiCnt;
	}
	public void setLv7_ExchangeEstiCnt(Integer lv7_ExchangeEstiCnt) {
		this.lv7_ExchangeEstiCnt = lv7_ExchangeEstiCnt;
	}
	public Integer getLv7_ExchangeEstiPoint() {
		return lv7_ExchangeEstiPoint;
	}
	public void setLv7_ExchangeEstiPoint(Integer lv7_ExchangeEstiPoint) {
		this.lv7_ExchangeEstiPoint = lv7_ExchangeEstiPoint;
	}
	public String getLv7_QueRegAlmoney() {
		return lv7_QueRegAlmoney;
	}
	public void setLv7_QueRegAlmoney(String lv7_QueRegAlmoney) {
		this.lv7_QueRegAlmoney = lv7_QueRegAlmoney;
	}
	public Integer getLv8_LimitQueDayRegistCnt() {
		return lv8_LimitQueDayRegistCnt;
	}
	public void setLv8_LimitQueDayRegistCnt(Integer lv8_LimitQueDayRegistCnt) {
		this.lv8_LimitQueDayRegistCnt = lv8_LimitQueDayRegistCnt;
	}
	public Integer getLv8_LimitQueDayDupRegistCnt() {
		return lv8_LimitQueDayDupRegistCnt;
	}
	public void setLv8_LimitQueDayDupRegistCnt(Integer lv8_LimitQueDayDupRegistCnt) {
		this.lv8_LimitQueDayDupRegistCnt = lv8_LimitQueDayDupRegistCnt;
	}
	public Integer getLv8_LimitQueContinueRegistTime() {
		return lv8_LimitQueContinueRegistTime;
	}
	public void setLv8_LimitQueContinueRegistTime(Integer lv8_LimitQueContinueRegistTime) {
		this.lv8_LimitQueContinueRegistTime = lv8_LimitQueContinueRegistTime;
	}
	public Integer getLv8_LimitAnsDayRegistCnt() {
		return lv8_LimitAnsDayRegistCnt;
	}
	public void setLv8_LimitAnsDayRegistCnt(Integer lv8_LimitAnsDayRegistCnt) {
		this.lv8_LimitAnsDayRegistCnt = lv8_LimitAnsDayRegistCnt;
	}
	public Integer getLv8_LimitAnsDayDupRegistCnt() {
		return lv8_LimitAnsDayDupRegistCnt;
	}
	public void setLv8_LimitAnsDayDupRegistCnt(Integer lv8_LimitAnsDayDupRegistCnt) {
		this.lv8_LimitAnsDayDupRegistCnt = lv8_LimitAnsDayDupRegistCnt;
	}
	public Integer getLv8_LimitAnsContinueRegistTime() {
		return lv8_LimitAnsContinueRegistTime;
	}
	public void setLv8_LimitAnsContinueRegistTime(Integer lv8_LimitAnsContinueRegistTime) {
		this.lv8_LimitAnsContinueRegistTime = lv8_LimitAnsContinueRegistTime;
	}
	public Integer getLv8_LimitRepDayRegistCnt() {
		return lv8_LimitRepDayRegistCnt;
	}
	public void setLv8_LimitRepDayRegistCnt(Integer lv8_LimitRepDayRegistCnt) {
		this.lv8_LimitRepDayRegistCnt = lv8_LimitRepDayRegistCnt;
	}
	public Integer getLv8_LimitRepDayDupRegistCnt() {
		return lv8_LimitRepDayDupRegistCnt;
	}
	public void setLv8_LimitRepDayDupRegistCnt(Integer lv8_LimitRepDayDupRegistCnt) {
		this.lv8_LimitRepDayDupRegistCnt = lv8_LimitRepDayDupRegistCnt;
	}
	public Integer getLv8_LimitRepContinueRegistTime() {
		return lv8_LimitRepContinueRegistTime;
	}
	public void setLv8_LimitRepContinueRegistTime(Integer lv8_LimitRepContinueRegistTime) {
		this.lv8_LimitRepContinueRegistTime = lv8_LimitRepContinueRegistTime;
	}
	public String getLv8_LevelUpAuto_Yn() {
		return lv8_LevelUpAuto_Yn;
	}
	public void setLv8_LevelUpAuto_Yn(String lv8_LevelUpAuto_Yn) {
		this.lv8_LevelUpAuto_Yn = lv8_LevelUpAuto_Yn;
	}
	public Integer getLv8_LimitAnsEstiCnt() {
		return lv8_LimitAnsEstiCnt;
	}
	public void setLv8_LimitAnsEstiCnt(Integer lv8_LimitAnsEstiCnt) {
		this.lv8_LimitAnsEstiCnt = lv8_LimitAnsEstiCnt;
	}
	public Integer getLv8_LimitChuMemCnt() {
		return lv8_LimitChuMemCnt;
	}
	public void setLv8_LimitChuMemCnt(Integer lv8_LimitChuMemCnt) {
		this.lv8_LimitChuMemCnt = lv8_LimitChuMemCnt;
	}
	public String getLv8_LimitLvMtPeriod() {
		return lv8_LimitLvMtPeriod;
	}
	public void setLv8_LimitLvMtPeriod(String lv8_LimitLvMtPeriod) {
		this.lv8_LimitLvMtPeriod = lv8_LimitLvMtPeriod;
	}
	public Integer getLv8_LimitQueRegistCnt() {
		return lv8_LimitQueRegistCnt;
	}
	public void setLv8_LimitQueRegistCnt(Integer lv8_LimitQueRegistCnt) {
		this.lv8_LimitQueRegistCnt = lv8_LimitQueRegistCnt;
	}
	public Integer getLv8_LimitAnsRegistCnt() {
		return lv8_LimitAnsRegistCnt;
	}
	public void setLv8_LimitAnsRegistCnt(Integer lv8_LimitAnsRegistCnt) {
		this.lv8_LimitAnsRegistCnt = lv8_LimitAnsRegistCnt;
	}
	public Integer getLv8_LimitAnsChoiceCnt() {
		return lv8_LimitAnsChoiceCnt;
	}
	public void setLv8_LimitAnsChoiceCnt(Integer lv8_LimitAnsChoiceCnt) {
		this.lv8_LimitAnsChoiceCnt = lv8_LimitAnsChoiceCnt;
	}
	public Integer getLv8_LimitAnsEstiPoint() {
		return lv8_LimitAnsEstiPoint;
	}
	public void setLv8_LimitAnsEstiPoint(Integer lv8_LimitAnsEstiPoint) {
		this.lv8_LimitAnsEstiPoint = lv8_LimitAnsEstiPoint;
	}
	public Integer getLv8_LimitReplyCnt() {
		return lv8_LimitReplyCnt;
	}
	public void setLv8_LimitReplyCnt(Integer lv8_LimitReplyCnt) {
		this.lv8_LimitReplyCnt = lv8_LimitReplyCnt;
	}
	public BigDecimal getLv8_ExchangeBaseDeductAlmoney() {
		return lv8_ExchangeBaseDeductAlmoney;
	}
	public void setLv8_ExchangeBaseDeductAlmoney(BigDecimal lv8_ExchangeBaseDeductAlmoney) {
		this.lv8_ExchangeBaseDeductAlmoney = lv8_ExchangeBaseDeductAlmoney;
	}
	public BigDecimal getLv8_ExchangeLimitAlmoney() {
		return lv8_ExchangeLimitAlmoney;
	}
	public void setLv8_ExchangeLimitAlmoney(BigDecimal lv8_ExchangeLimitAlmoney) {
		this.lv8_ExchangeLimitAlmoney = lv8_ExchangeLimitAlmoney;
	}
	public BigDecimal getLv8_ExchangeBaseUnitAlmoney() {
		return lv8_ExchangeBaseUnitAlmoney;
	}
	public void setLv8_ExchangeBaseUnitAlmoney(BigDecimal lv8_ExchangeBaseUnitAlmoney) {
		this.lv8_ExchangeBaseUnitAlmoney = lv8_ExchangeBaseUnitAlmoney;
	}
	public float getLv8_ExchangeRealMoneyDeductRate() {
		return lv8_ExchangeRealMoneyDeductRate;
	}
	public void setLv8_ExchangeRealMoneyDeductRate(float lv8_ExchangeRealMoneyDeductRate) {
		this.lv8_ExchangeRealMoneyDeductRate = lv8_ExchangeRealMoneyDeductRate;
	}
	public String getLv8_ExchangeMtPeriod() {
		return lv8_ExchangeMtPeriod;
	}
	public void setLv8_ExchangeMtPeriod(String lv8_ExchangeMtPeriod) {
		this.lv8_ExchangeMtPeriod = lv8_ExchangeMtPeriod;
	}
	public Integer getLv8_ExchangeQueRegistCnt() {
		return lv8_ExchangeQueRegistCnt;
	}
	public void setLv8_ExchangeQueRegistCnt(Integer lv8_ExchangeQueRegistCnt) {
		this.lv8_ExchangeQueRegistCnt = lv8_ExchangeQueRegistCnt;
	}
	public Integer getLv8_ExchangeAnsRegistCnt() {
		return lv8_ExchangeAnsRegistCnt;
	}
	public void setLv8_ExchangeAnsRegistCnt(Integer lv8_ExchangeAnsRegistCnt) {
		this.lv8_ExchangeAnsRegistCnt = lv8_ExchangeAnsRegistCnt;
	}
	public Integer getLv8_ExchangeEstiCnt() {
		return lv8_ExchangeEstiCnt;
	}
	public void setLv8_ExchangeEstiCnt(Integer lv8_ExchangeEstiCnt) {
		this.lv8_ExchangeEstiCnt = lv8_ExchangeEstiCnt;
	}
	public Integer getLv8_ExchangeEstiPoint() {
		return lv8_ExchangeEstiPoint;
	}
	public void setLv8_ExchangeEstiPoint(Integer lv8_ExchangeEstiPoint) {
		this.lv8_ExchangeEstiPoint = lv8_ExchangeEstiPoint;
	}
	public String getLv8_QueRegAlmoney() {
		return lv8_QueRegAlmoney;
	}
	public void setLv8_QueRegAlmoney(String lv8_QueRegAlmoney) {
		this.lv8_QueRegAlmoney = lv8_QueRegAlmoney;
	}
	public Integer getLv9_LimitQueDayRegistCnt() {
		return lv9_LimitQueDayRegistCnt;
	}
	public void setLv9_LimitQueDayRegistCnt(Integer lv9_LimitQueDayRegistCnt) {
		this.lv9_LimitQueDayRegistCnt = lv9_LimitQueDayRegistCnt;
	}
	public Integer getLv9_LimitQueDayDupRegistCnt() {
		return lv9_LimitQueDayDupRegistCnt;
	}
	public void setLv9_LimitQueDayDupRegistCnt(Integer lv9_LimitQueDayDupRegistCnt) {
		this.lv9_LimitQueDayDupRegistCnt = lv9_LimitQueDayDupRegistCnt;
	}
	public Integer getLv9_LimitQueContinueRegistTime() {
		return lv9_LimitQueContinueRegistTime;
	}
	public void setLv9_LimitQueContinueRegistTime(Integer lv9_LimitQueContinueRegistTime) {
		this.lv9_LimitQueContinueRegistTime = lv9_LimitQueContinueRegistTime;
	}
	public Integer getLv9_LimitAnsDayRegistCnt() {
		return lv9_LimitAnsDayRegistCnt;
	}
	public void setLv9_LimitAnsDayRegistCnt(Integer lv9_LimitAnsDayRegistCnt) {
		this.lv9_LimitAnsDayRegistCnt = lv9_LimitAnsDayRegistCnt;
	}
	public Integer getLv9_LimitAnsDayDupRegistCnt() {
		return lv9_LimitAnsDayDupRegistCnt;
	}
	public void setLv9_LimitAnsDayDupRegistCnt(Integer lv9_LimitAnsDayDupRegistCnt) {
		this.lv9_LimitAnsDayDupRegistCnt = lv9_LimitAnsDayDupRegistCnt;
	}
	public Integer getLv9_LimitAnsContinueRegistTime() {
		return lv9_LimitAnsContinueRegistTime;
	}
	public void setLv9_LimitAnsContinueRegistTime(Integer lv9_LimitAnsContinueRegistTime) {
		this.lv9_LimitAnsContinueRegistTime = lv9_LimitAnsContinueRegistTime;
	}
	public Integer getLv9_LimitRepDayRegistCnt() {
		return lv9_LimitRepDayRegistCnt;
	}
	public void setLv9_LimitRepDayRegistCnt(Integer lv9_LimitRepDayRegistCnt) {
		this.lv9_LimitRepDayRegistCnt = lv9_LimitRepDayRegistCnt;
	}
	public Integer getLv9_LimitRepDayDupRegistCnt() {
		return lv9_LimitRepDayDupRegistCnt;
	}
	public void setLv9_LimitRepDayDupRegistCnt(Integer lv9_LimitRepDayDupRegistCnt) {
		this.lv9_LimitRepDayDupRegistCnt = lv9_LimitRepDayDupRegistCnt;
	}
	public Integer getLv9_LimitRepContinueRegistTime() {
		return lv9_LimitRepContinueRegistTime;
	}
	public void setLv9_LimitRepContinueRegistTime(Integer lv9_LimitRepContinueRegistTime) {
		this.lv9_LimitRepContinueRegistTime = lv9_LimitRepContinueRegistTime;
	}
	public String getLv9_LevelUpAuto_Yn() {
		return lv9_LevelUpAuto_Yn;
	}
	public void setLv9_LevelUpAuto_Yn(String lv9_LevelUpAuto_Yn) {
		this.lv9_LevelUpAuto_Yn = lv9_LevelUpAuto_Yn;
	}
	public Integer getLv9_LimitAnsEstiCnt() {
		return lv9_LimitAnsEstiCnt;
	}
	public void setLv9_LimitAnsEstiCnt(Integer lv9_LimitAnsEstiCnt) {
		this.lv9_LimitAnsEstiCnt = lv9_LimitAnsEstiCnt;
	}
	public Integer getLv9_LimitChuMemCnt() {
		return lv9_LimitChuMemCnt;
	}
	public void setLv9_LimitChuMemCnt(Integer lv9_LimitChuMemCnt) {
		this.lv9_LimitChuMemCnt = lv9_LimitChuMemCnt;
	}
	public String getLv9_LimitLvMtPeriod() {
		return lv9_LimitLvMtPeriod;
	}
	public void setLv9_LimitLvMtPeriod(String lv9_LimitLvMtPeriod) {
		this.lv9_LimitLvMtPeriod = lv9_LimitLvMtPeriod;
	}
	public Integer getLv9_LimitQueRegistCnt() {
		return lv9_LimitQueRegistCnt;
	}
	public void setLv9_LimitQueRegistCnt(Integer lv9_LimitQueRegistCnt) {
		this.lv9_LimitQueRegistCnt = lv9_LimitQueRegistCnt;
	}
	public Integer getLv9_LimitAnsRegistCnt() {
		return lv9_LimitAnsRegistCnt;
	}
	public void setLv9_LimitAnsRegistCnt(Integer lv9_LimitAnsRegistCnt) {
		this.lv9_LimitAnsRegistCnt = lv9_LimitAnsRegistCnt;
	}
	public Integer getLv9_LimitAnsChoiceCnt() {
		return lv9_LimitAnsChoiceCnt;
	}
	public void setLv9_LimitAnsChoiceCnt(Integer lv9_LimitAnsChoiceCnt) {
		this.lv9_LimitAnsChoiceCnt = lv9_LimitAnsChoiceCnt;
	}
	public Integer getLv9_LimitAnsEstiPoint() {
		return lv9_LimitAnsEstiPoint;
	}
	public void setLv9_LimitAnsEstiPoint(Integer lv9_LimitAnsEstiPoint) {
		this.lv9_LimitAnsEstiPoint = lv9_LimitAnsEstiPoint;
	}
	public Integer getLv9_LimitReplyCnt() {
		return lv9_LimitReplyCnt;
	}
	public void setLv9_LimitReplyCnt(Integer lv9_LimitReplyCnt) {
		this.lv9_LimitReplyCnt = lv9_LimitReplyCnt;
	}
	public BigDecimal getLv9_ExchangeBaseDeductAlmoney() {
		return lv9_ExchangeBaseDeductAlmoney;
	}
	public void setLv9_ExchangeBaseDeductAlmoney(BigDecimal lv9_ExchangeBaseDeductAlmoney) {
		this.lv9_ExchangeBaseDeductAlmoney = lv9_ExchangeBaseDeductAlmoney;
	}
	public BigDecimal getLv9_ExchangeLimitAlmoney() {
		return lv9_ExchangeLimitAlmoney;
	}
	public void setLv9_ExchangeLimitAlmoney(BigDecimal lv9_ExchangeLimitAlmoney) {
		this.lv9_ExchangeLimitAlmoney = lv9_ExchangeLimitAlmoney;
	}
	public BigDecimal getLv9_ExchangeBaseUnitAlmoney() {
		return lv9_ExchangeBaseUnitAlmoney;
	}
	public void setLv9_ExchangeBaseUnitAlmoney(BigDecimal lv9_ExchangeBaseUnitAlmoney) {
		this.lv9_ExchangeBaseUnitAlmoney = lv9_ExchangeBaseUnitAlmoney;
	}
	public float getLv9_ExchangeRealMoneyDeductRate() {
		return lv9_ExchangeRealMoneyDeductRate;
	}
	public void setLv9_ExchangeRealMoneyDeductRate(float lv9_ExchangeRealMoneyDeductRate) {
		this.lv9_ExchangeRealMoneyDeductRate = lv9_ExchangeRealMoneyDeductRate;
	}
	public String getLv9_ExchangeMtPeriod() {
		return lv9_ExchangeMtPeriod;
	}
	public void setLv9_ExchangeMtPeriod(String lv9_ExchangeMtPeriod) {
		this.lv9_ExchangeMtPeriod = lv9_ExchangeMtPeriod;
	}
	public Integer getLv9_ExchangeQueRegistCnt() {
		return lv9_ExchangeQueRegistCnt;
	}
	public void setLv9_ExchangeQueRegistCnt(Integer lv9_ExchangeQueRegistCnt) {
		this.lv9_ExchangeQueRegistCnt = lv9_ExchangeQueRegistCnt;
	}
	public Integer getLv9_ExchangeAnsRegistCnt() {
		return lv9_ExchangeAnsRegistCnt;
	}
	public void setLv9_ExchangeAnsRegistCnt(Integer lv9_ExchangeAnsRegistCnt) {
		this.lv9_ExchangeAnsRegistCnt = lv9_ExchangeAnsRegistCnt;
	}
	public Integer getLv9_ExchangeEstiCnt() {
		return lv9_ExchangeEstiCnt;
	}
	public void setLv9_ExchangeEstiCnt(Integer lv9_ExchangeEstiCnt) {
		this.lv9_ExchangeEstiCnt = lv9_ExchangeEstiCnt;
	}
	public Integer getLv9_ExchangeEstiPoint() {
		return lv9_ExchangeEstiPoint;
	}
	public void setLv9_ExchangeEstiPoint(Integer lv9_ExchangeEstiPoint) {
		this.lv9_ExchangeEstiPoint = lv9_ExchangeEstiPoint;
	}
	public String getLv9_QueRegAlmoney() {
		return lv9_QueRegAlmoney;
	}
	public void setLv9_QueRegAlmoney(String lv9_QueRegAlmoney) {
		this.lv9_QueRegAlmoney = lv9_QueRegAlmoney;
	}
	public Integer getLv10_LimitQueDayRegistCnt() {
		return lv10_LimitQueDayRegistCnt;
	}
	public void setLv10_LimitQueDayRegistCnt(Integer lv10_LimitQueDayRegistCnt) {
		this.lv10_LimitQueDayRegistCnt = lv10_LimitQueDayRegistCnt;
	}
	public Integer getLv10_LimitQueDayDupRegistCnt() {
		return lv10_LimitQueDayDupRegistCnt;
	}
	public void setLv10_LimitQueDayDupRegistCnt(Integer lv10_LimitQueDayDupRegistCnt) {
		this.lv10_LimitQueDayDupRegistCnt = lv10_LimitQueDayDupRegistCnt;
	}
	public Integer getLv10_LimitQueContinueRegistTime() {
		return lv10_LimitQueContinueRegistTime;
	}
	public void setLv10_LimitQueContinueRegistTime(Integer lv10_LimitQueContinueRegistTime) {
		this.lv10_LimitQueContinueRegistTime = lv10_LimitQueContinueRegistTime;
	}
	public Integer getLv10_LimitAnsDayRegistCnt() {
		return lv10_LimitAnsDayRegistCnt;
	}
	public void setLv10_LimitAnsDayRegistCnt(Integer lv10_LimitAnsDayRegistCnt) {
		this.lv10_LimitAnsDayRegistCnt = lv10_LimitAnsDayRegistCnt;
	}
	public Integer getLv10_LimitAnsDayDupRegistCnt() {
		return lv10_LimitAnsDayDupRegistCnt;
	}
	public void setLv10_LimitAnsDayDupRegistCnt(Integer lv10_LimitAnsDayDupRegistCnt) {
		this.lv10_LimitAnsDayDupRegistCnt = lv10_LimitAnsDayDupRegistCnt;
	}
	public Integer getLv10_LimitAnsContinueRegistTime() {
		return lv10_LimitAnsContinueRegistTime;
	}
	public void setLv10_LimitAnsContinueRegistTime(Integer lv10_LimitAnsContinueRegistTime) {
		this.lv10_LimitAnsContinueRegistTime = lv10_LimitAnsContinueRegistTime;
	}
	public Integer getLv10_LimitRepDayRegistCnt() {
		return lv10_LimitRepDayRegistCnt;
	}
	public void setLv10_LimitRepDayRegistCnt(Integer lv10_LimitRepDayRegistCnt) {
		this.lv10_LimitRepDayRegistCnt = lv10_LimitRepDayRegistCnt;
	}
	public Integer getLv10_LimitRepDayDupRegistCnt() {
		return lv10_LimitRepDayDupRegistCnt;
	}
	public void setLv10_LimitRepDayDupRegistCnt(Integer lv10_LimitRepDayDupRegistCnt) {
		this.lv10_LimitRepDayDupRegistCnt = lv10_LimitRepDayDupRegistCnt;
	}
	public Integer getLv10_LimitRepContinueRegistTime() {
		return lv10_LimitRepContinueRegistTime;
	}
	public void setLv10_LimitRepContinueRegistTime(Integer lv10_LimitRepContinueRegistTime) {
		this.lv10_LimitRepContinueRegistTime = lv10_LimitRepContinueRegistTime;
	}
	public String getLv10_LevelUpAuto_Yn() {
		return lv10_LevelUpAuto_Yn;
	}
	public void setLv10_LevelUpAuto_Yn(String lv10_LevelUpAuto_Yn) {
		this.lv10_LevelUpAuto_Yn = lv10_LevelUpAuto_Yn;
	}
	public Integer getLv10_LimitAnsEstiCnt() {
		return lv10_LimitAnsEstiCnt;
	}
	public void setLv10_LimitAnsEstiCnt(Integer lv10_LimitAnsEstiCnt) {
		this.lv10_LimitAnsEstiCnt = lv10_LimitAnsEstiCnt;
	}
	public Integer getLv10_LimitChuMemCnt() {
		return lv10_LimitChuMemCnt;
	}
	public void setLv10_LimitChuMemCnt(Integer lv10_LimitChuMemCnt) {
		this.lv10_LimitChuMemCnt = lv10_LimitChuMemCnt;
	}
	public String getLv10_LimitLvMtPeriod() {
		return lv10_LimitLvMtPeriod;
	}
	public void setLv10_LimitLvMtPeriod(String lv10_LimitLvMtPeriod) {
		this.lv10_LimitLvMtPeriod = lv10_LimitLvMtPeriod;
	}
	public Integer getLv10_LimitQueRegistCnt() {
		return lv10_LimitQueRegistCnt;
	}
	public void setLv10_LimitQueRegistCnt(Integer lv10_LimitQueRegistCnt) {
		this.lv10_LimitQueRegistCnt = lv10_LimitQueRegistCnt;
	}
	public Integer getLv10_LimitAnsRegistCnt() {
		return lv10_LimitAnsRegistCnt;
	}
	public void setLv10_LimitAnsRegistCnt(Integer lv10_LimitAnsRegistCnt) {
		this.lv10_LimitAnsRegistCnt = lv10_LimitAnsRegistCnt;
	}
	public Integer getLv10_LimitAnsChoiceCnt() {
		return lv10_LimitAnsChoiceCnt;
	}
	public void setLv10_LimitAnsChoiceCnt(Integer lv10_LimitAnsChoiceCnt) {
		this.lv10_LimitAnsChoiceCnt = lv10_LimitAnsChoiceCnt;
	}
	public Integer getLv10_LimitAnsEstiPoint() {
		return lv10_LimitAnsEstiPoint;
	}
	public void setLv10_LimitAnsEstiPoint(Integer lv10_LimitAnsEstiPoint) {
		this.lv10_LimitAnsEstiPoint = lv10_LimitAnsEstiPoint;
	}
	public Integer getLv10_LimitReplyCnt() {
		return lv10_LimitReplyCnt;
	}
	public void setLv10_LimitReplyCnt(Integer lv10_LimitReplyCnt) {
		this.lv10_LimitReplyCnt = lv10_LimitReplyCnt;
	}
	public BigDecimal getLv10_ExchangeBaseDeductAlmoney() {
		return lv10_ExchangeBaseDeductAlmoney;
	}
	public void setLv10_ExchangeBaseDeductAlmoney(BigDecimal lv10_ExchangeBaseDeductAlmoney) {
		this.lv10_ExchangeBaseDeductAlmoney = lv10_ExchangeBaseDeductAlmoney;
	}
	public BigDecimal getLv10_ExchangeLimitAlmoney() {
		return lv10_ExchangeLimitAlmoney;
	}
	public void setLv10_ExchangeLimitAlmoney(BigDecimal lv10_ExchangeLimitAlmoney) {
		this.lv10_ExchangeLimitAlmoney = lv10_ExchangeLimitAlmoney;
	}
	public BigDecimal getLv10_ExchangeBaseUnitAlmoney() {
		return lv10_ExchangeBaseUnitAlmoney;
	}
	public void setLv10_ExchangeBaseUnitAlmoney(BigDecimal lv10_ExchangeBaseUnitAlmoney) {
		this.lv10_ExchangeBaseUnitAlmoney = lv10_ExchangeBaseUnitAlmoney;
	}
	public float getLv10_ExchangeRealMoneyDeductRate() {
		return lv10_ExchangeRealMoneyDeductRate;
	}
	public void setLv10_ExchangeRealMoneyDeductRate(float lv10_ExchangeRealMoneyDeductRate) {
		this.lv10_ExchangeRealMoneyDeductRate = lv10_ExchangeRealMoneyDeductRate;
	}
	public String getLv10_ExchangeMtPeriod() {
		return lv10_ExchangeMtPeriod;
	}
	public void setLv10_ExchangeMtPeriod(String lv10_ExchangeMtPeriod) {
		this.lv10_ExchangeMtPeriod = lv10_ExchangeMtPeriod;
	}
	public Integer getLv10_ExchangeQueRegistCnt() {
		return lv10_ExchangeQueRegistCnt;
	}
	public void setLv10_ExchangeQueRegistCnt(Integer lv10_ExchangeQueRegistCnt) {
		this.lv10_ExchangeQueRegistCnt = lv10_ExchangeQueRegistCnt;
	}
	public Integer getLv10_ExchangeAnsRegistCnt() {
		return lv10_ExchangeAnsRegistCnt;
	}
	public void setLv10_ExchangeAnsRegistCnt(Integer lv10_ExchangeAnsRegistCnt) {
		this.lv10_ExchangeAnsRegistCnt = lv10_ExchangeAnsRegistCnt;
	}
	public Integer getLv10_ExchangeEstiCnt() {
		return lv10_ExchangeEstiCnt;
	}
	public void setLv10_ExchangeEstiCnt(Integer lv10_ExchangeEstiCnt) {
		this.lv10_ExchangeEstiCnt = lv10_ExchangeEstiCnt;
	}
	public Integer getLv10_ExchangeEstiPoint() {
		return lv10_ExchangeEstiPoint;
	}
	public void setLv10_ExchangeEstiPoint(Integer lv10_ExchangeEstiPoint) {
		this.lv10_ExchangeEstiPoint = lv10_ExchangeEstiPoint;
	}
	public String getLv10_QueRegAlmoney() {
		return lv10_QueRegAlmoney;
	}
	public void setLv10_QueRegAlmoney(String lv10_QueRegAlmoney) {
		this.lv10_QueRegAlmoney = lv10_QueRegAlmoney;
	}
	public Integer getLv11_LimitQueDayRegistCnt() {
		return lv11_LimitQueDayRegistCnt;
	}
	public void setLv11_LimitQueDayRegistCnt(Integer lv11_LimitQueDayRegistCnt) {
		this.lv11_LimitQueDayRegistCnt = lv11_LimitQueDayRegistCnt;
	}
	public Integer getLv11_LimitQueDayDupRegistCnt() {
		return lv11_LimitQueDayDupRegistCnt;
	}
	public void setLv11_LimitQueDayDupRegistCnt(Integer lv11_LimitQueDayDupRegistCnt) {
		this.lv11_LimitQueDayDupRegistCnt = lv11_LimitQueDayDupRegistCnt;
	}
	public Integer getLv11_LimitQueContinueRegistTime() {
		return lv11_LimitQueContinueRegistTime;
	}
	public void setLv11_LimitQueContinueRegistTime(Integer lv11_LimitQueContinueRegistTime) {
		this.lv11_LimitQueContinueRegistTime = lv11_LimitQueContinueRegistTime;
	}
	public Integer getLv11_LimitAnsDayRegistCnt() {
		return lv11_LimitAnsDayRegistCnt;
	}
	public void setLv11_LimitAnsDayRegistCnt(Integer lv11_LimitAnsDayRegistCnt) {
		this.lv11_LimitAnsDayRegistCnt = lv11_LimitAnsDayRegistCnt;
	}
	public Integer getLv11_LimitAnsDayDupRegistCnt() {
		return lv11_LimitAnsDayDupRegistCnt;
	}
	public void setLv11_LimitAnsDayDupRegistCnt(Integer lv11_LimitAnsDayDupRegistCnt) {
		this.lv11_LimitAnsDayDupRegistCnt = lv11_LimitAnsDayDupRegistCnt;
	}
	public Integer getLv11_LimitAnsContinueRegistTime() {
		return lv11_LimitAnsContinueRegistTime;
	}
	public void setLv11_LimitAnsContinueRegistTime(Integer lv11_LimitAnsContinueRegistTime) {
		this.lv11_LimitAnsContinueRegistTime = lv11_LimitAnsContinueRegistTime;
	}
	public Integer getLv11_LimitRepDayRegistCnt() {
		return lv11_LimitRepDayRegistCnt;
	}
	public void setLv11_LimitRepDayRegistCnt(Integer lv11_LimitRepDayRegistCnt) {
		this.lv11_LimitRepDayRegistCnt = lv11_LimitRepDayRegistCnt;
	}
	public Integer getLv11_LimitRepDayDupRegistCnt() {
		return lv11_LimitRepDayDupRegistCnt;
	}
	public void setLv11_LimitRepDayDupRegistCnt(Integer lv11_LimitRepDayDupRegistCnt) {
		this.lv11_LimitRepDayDupRegistCnt = lv11_LimitRepDayDupRegistCnt;
	}
	public Integer getLv11_LimitRepContinueRegistTime() {
		return lv11_LimitRepContinueRegistTime;
	}
	public void setLv11_LimitRepContinueRegistTime(Integer lv11_LimitRepContinueRegistTime) {
		this.lv11_LimitRepContinueRegistTime = lv11_LimitRepContinueRegistTime;
	}
	public String getLv11_LevelUpAuto_Yn() {
		return lv11_LevelUpAuto_Yn;
	}
	public void setLv11_LevelUpAuto_Yn(String lv11_LevelUpAuto_Yn) {
		this.lv11_LevelUpAuto_Yn = lv11_LevelUpAuto_Yn;
	}
	public Integer getLv11_LimitAnsEstiCnt() {
		return lv11_LimitAnsEstiCnt;
	}
	public void setLv11_LimitAnsEstiCnt(Integer lv11_LimitAnsEstiCnt) {
		this.lv11_LimitAnsEstiCnt = lv11_LimitAnsEstiCnt;
	}
	public Integer getLv11_LimitChuMemCnt() {
		return lv11_LimitChuMemCnt;
	}
	public void setLv11_LimitChuMemCnt(Integer lv11_LimitChuMemCnt) {
		this.lv11_LimitChuMemCnt = lv11_LimitChuMemCnt;
	}
	public String getLv11_LimitLvMtPeriod() {
		return lv11_LimitLvMtPeriod;
	}
	public void setLv11_LimitLvMtPeriod(String lv11_LimitLvMtPeriod) {
		this.lv11_LimitLvMtPeriod = lv11_LimitLvMtPeriod;
	}
	public Integer getLv11_LimitQueRegistCnt() {
		return lv11_LimitQueRegistCnt;
	}
	public void setLv11_LimitQueRegistCnt(Integer lv11_LimitQueRegistCnt) {
		this.lv11_LimitQueRegistCnt = lv11_LimitQueRegistCnt;
	}
	public Integer getLv11_LimitAnsRegistCnt() {
		return lv11_LimitAnsRegistCnt;
	}
	public void setLv11_LimitAnsRegistCnt(Integer lv11_LimitAnsRegistCnt) {
		this.lv11_LimitAnsRegistCnt = lv11_LimitAnsRegistCnt;
	}
	public Integer getLv11_LimitAnsChoiceCnt() {
		return lv11_LimitAnsChoiceCnt;
	}
	public void setLv11_LimitAnsChoiceCnt(Integer lv11_LimitAnsChoiceCnt) {
		this.lv11_LimitAnsChoiceCnt = lv11_LimitAnsChoiceCnt;
	}
	public Integer getLv11_LimitAnsEstiPoint() {
		return lv11_LimitAnsEstiPoint;
	}
	public void setLv11_LimitAnsEstiPoint(Integer lv11_LimitAnsEstiPoint) {
		this.lv11_LimitAnsEstiPoint = lv11_LimitAnsEstiPoint;
	}
	public Integer getLv11_LimitReplyCnt() {
		return lv11_LimitReplyCnt;
	}
	public void setLv11_LimitReplyCnt(Integer lv11_LimitReplyCnt) {
		this.lv11_LimitReplyCnt = lv11_LimitReplyCnt;
	}
	public BigDecimal getLv11_ExchangeBaseDeductAlmoney() {
		return lv11_ExchangeBaseDeductAlmoney;
	}
	public void setLv11_ExchangeBaseDeductAlmoney(BigDecimal lv11_ExchangeBaseDeductAlmoney) {
		this.lv11_ExchangeBaseDeductAlmoney = lv11_ExchangeBaseDeductAlmoney;
	}
	public BigDecimal getLv11_ExchangeLimitAlmoney() {
		return lv11_ExchangeLimitAlmoney;
	}
	public void setLv11_ExchangeLimitAlmoney(BigDecimal lv11_ExchangeLimitAlmoney) {
		this.lv11_ExchangeLimitAlmoney = lv11_ExchangeLimitAlmoney;
	}
	public BigDecimal getLv11_ExchangeBaseUnitAlmoney() {
		return lv11_ExchangeBaseUnitAlmoney;
	}
	public void setLv11_ExchangeBaseUnitAlmoney(BigDecimal lv11_ExchangeBaseUnitAlmoney) {
		this.lv11_ExchangeBaseUnitAlmoney = lv11_ExchangeBaseUnitAlmoney;
	}
	public float getLv11_ExchangeRealMoneyDeductRate() {
		return lv11_ExchangeRealMoneyDeductRate;
	}
	public void setLv11_ExchangeRealMoneyDeductRate(float lv11_ExchangeRealMoneyDeductRate) {
		this.lv11_ExchangeRealMoneyDeductRate = lv11_ExchangeRealMoneyDeductRate;
	}
	public String getLv11_ExchangeMtPeriod() {
		return lv11_ExchangeMtPeriod;
	}
	public void setLv11_ExchangeMtPeriod(String lv11_ExchangeMtPeriod) {
		this.lv11_ExchangeMtPeriod = lv11_ExchangeMtPeriod;
	}
	public Integer getLv11_ExchangeQueRegistCnt() {
		return lv11_ExchangeQueRegistCnt;
	}
	public void setLv11_ExchangeQueRegistCnt(Integer lv11_ExchangeQueRegistCnt) {
		this.lv11_ExchangeQueRegistCnt = lv11_ExchangeQueRegistCnt;
	}
	public Integer getLv11_ExchangeAnsRegistCnt() {
		return lv11_ExchangeAnsRegistCnt;
	}
	public void setLv11_ExchangeAnsRegistCnt(Integer lv11_ExchangeAnsRegistCnt) {
		this.lv11_ExchangeAnsRegistCnt = lv11_ExchangeAnsRegistCnt;
	}
	public Integer getLv11_ExchangeEstiCnt() {
		return lv11_ExchangeEstiCnt;
	}
	public void setLv11_ExchangeEstiCnt(Integer lv11_ExchangeEstiCnt) {
		this.lv11_ExchangeEstiCnt = lv11_ExchangeEstiCnt;
	}
	public Integer getLv11_ExchangeEstiPoint() {
		return lv11_ExchangeEstiPoint;
	}
	public void setLv11_ExchangeEstiPoint(Integer lv11_ExchangeEstiPoint) {
		this.lv11_ExchangeEstiPoint = lv11_ExchangeEstiPoint;
	}
	public String getLv11_QueRegAlmoney() {
		return lv11_QueRegAlmoney;
	}
	public void setLv11_QueRegAlmoney(String lv11_QueRegAlmoney) {
		this.lv11_QueRegAlmoney = lv11_QueRegAlmoney;
	}
	
	
}
