package com.altong.web.logic;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class LogAlmoneyVO {
	private Long seq;
	private Integer memberSeq;
	private Integer contentsSeq;
	private String contents;
	private String tradeType;
	private BigDecimal almoney;
	private BigDecimal balance;
	private Timestamp regdate;

	private BigDecimal sumJoin;
	private BigDecimal sumAnswer;
	private BigDecimal sumViewQ;
	private BigDecimal sumViewA;
	private BigDecimal sumViewRQ;
	private BigDecimal sumViewRA;
	private BigDecimal sumEsti;
	private BigDecimal sumRefund;
	private BigDecimal sumEvent;
	private BigDecimal sumEtc;
	private BigDecimal sumWithdraw;
	private BigDecimal sumQuestion;
	private BigDecimal sumView;

	private BigDecimal sum_Q_Almoney;
	private BigDecimal sum_Q_ViewAlmoney;
	private Integer answerSeq;
	private Integer questionSeq;
	private String section1;
	private String answer;

	private BigDecimal sumViewTrnQ;
	private BigDecimal sumViewTrnA;
	private BigDecimal sumReply;


	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}
	public Integer getMemberSeq() {
		return memberSeq;
	}
	public void setMemberSeq(Integer memberSeq) {
		this.memberSeq = memberSeq;
	}
	public Integer getContentsSeq() {
		return contentsSeq;
	}
	public void setContentsSeq(Integer contentsSeq) {
		this.contentsSeq = contentsSeq;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public BigDecimal getAlmoney() {
		return almoney;
	}
	public void setAlmoney(BigDecimal almoney) {
		this.almoney = almoney;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public Timestamp getRegdate() {
		return regdate;
	}
	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}
	public BigDecimal getSumJoin() {
		return sumJoin;
	}
	public void setSumJoin(BigDecimal sumJoin) {
		this.sumJoin = sumJoin;
	}
	public BigDecimal getSumAnswer() {
		return sumAnswer;
	}
	public void setSumAnswer(BigDecimal sumAnswer) {
		this.sumAnswer = sumAnswer;
	}
	public BigDecimal getSumViewQ() {
		return sumViewQ;
	}
	public void setSumViewQ(BigDecimal sumViewQ) {
		this.sumViewQ = sumViewQ;
	}
	public BigDecimal getSumViewA() {
		return sumViewA;
	}
	public void setSumViewA(BigDecimal sumViewA) {
		this.sumViewA = sumViewA;
	}
	public BigDecimal getSumViewRQ() {
		return sumViewRQ;
	}
	public void setSumViewRQ(BigDecimal sumViewRQ) {
		this.sumViewRQ = sumViewRQ;
	}
	public BigDecimal getSumViewRA() {
		return sumViewRA;
	}
	public void setSumViewRA(BigDecimal sumViewRA) {
		this.sumViewRA = sumViewRA;
	}
	public BigDecimal getSumEsti() {
		return sumEsti;
	}
	public void setSumEsti(BigDecimal sumEsti) {
		this.sumEsti = sumEsti;
	}
	public BigDecimal getSumRefund() {
		return sumRefund;
	}
	public void setSumRefund(BigDecimal sumRefund) {
		this.sumRefund = sumRefund;
	}
	public BigDecimal getSumEvent() {
		return sumEvent;
	}
	public void setSumEvent(BigDecimal sumEvent) {
		this.sumEvent = sumEvent;
	}
	public BigDecimal getSumEtc() {
		return sumEtc;
	}
	public void setSumEtc(BigDecimal sumEtc) {
		this.sumEtc = sumEtc;
	}
	public BigDecimal getSumWithdraw() {
		return sumWithdraw;
	}
	public void setSumWithdraw(BigDecimal sumWithdraw) {
		this.sumWithdraw = sumWithdraw;
	}
	public BigDecimal getSumQuestion() {
		return sumQuestion;
	}
	public void setSumQuestion(BigDecimal sumQuestion) {
		this.sumQuestion = sumQuestion;
	}
	public BigDecimal getSumView() {
		return sumView;
	}
	public void setSumView(BigDecimal sumView) {
		this.sumView = sumView;
	}
	public BigDecimal getSum_Q_Almoney() {
		return sum_Q_Almoney;
	}
	public void setSum_Q_Almoney(BigDecimal sum_Q_Almoney) {
		this.sum_Q_Almoney = sum_Q_Almoney;
	}
	public BigDecimal getSum_Q_ViewAlmoney() {
		return sum_Q_ViewAlmoney;
	}
	public void setSum_Q_ViewAlmoney(BigDecimal sum_Q_ViewAlmoney) {
		this.sum_Q_ViewAlmoney = sum_Q_ViewAlmoney;
	}
	public Integer getAnswerSeq() {
		return answerSeq;
	}
	public void setAnswerSeq(Integer answerSeq) {
		this.answerSeq = answerSeq;
	}
	public Integer getQuestionSeq() {
		return questionSeq;
	}
	public void setQuestionSeq(Integer questionSeq) {
		this.questionSeq = questionSeq;
	}
	public String getSection1() {
		return section1;
	}
	public void setSection1(String section1) {
		this.section1 = section1;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public BigDecimal getSumViewTrnQ() {
		return sumViewTrnQ;
	}
	public void setSumViewTrnQ(BigDecimal sumViewTrnQ) {
		this.sumViewTrnQ = sumViewTrnQ;
	}
	public BigDecimal getSumViewTrnA() {
		return sumViewTrnA;
	}
	public void setSumViewTrnA(BigDecimal sumViewTrnA) {
		this.sumViewTrnA = sumViewTrnA;
	}
	public BigDecimal getSumReply() {
		return sumReply;
	}
	public void setSumReply(BigDecimal sumReply) {
		this.sumReply = sumReply;
	}

}
