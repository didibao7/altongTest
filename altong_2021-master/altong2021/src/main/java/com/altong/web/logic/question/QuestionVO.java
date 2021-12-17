package com.altong.web.logic.question;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class QuestionVO {
	private Long seq;
	private Long seq_Order;
	private Integer memberSeq;
	private Integer section_Special;
	private Integer section1;
	private Integer section2;
	private Integer section3;
	private Integer section4;
	private Integer section5;
	private String title;
	private String contents;
	private BigDecimal almoney;
	private String flagNickName;
	private String flagMinor;
	private String flagUse;
	private String flagChoice;
	private Integer flagEvent;
	private Integer bestNumber;
	private Integer readCount;
	private Integer readAlmoney;
	private String dateReg;
	private Timestamp regdate;
	private Integer readCount_Answ;
	private Integer answCount;
	private String photo;
	private String nickName;
	private int lv;
	private int ev_seq;
	private int q_Seq;
	private String ev_title;
	private String conDate;
	private int ev_readCount;
	private int ev_answCount;
	private int ev_alMoney;
	private int itv_idx;
	private String itv_title;
	private int itv_member_seq;
	private String itv_contents;
	private Timestamp itv_date_reg;
	private int itv_page_view;

	private int count;
	private int replyCount;
	private String questionBet;

	private String keyword;
	private int group_id;
	private int phrase_id;
	private int occurrence;
	private String special_term;
	private String display_term;
	private String expansion_tye;
	private String source_term;
	private int answerCount;
	private int questionSeq;

	private BigDecimal thankAlmoney;
	private String intro;
	private BigDecimal q_almoney;
	private BigDecimal a_almoney;
	private int rownum;

	private int flagNickPrice;

	private String nation;
	private String lang;

	private int good;
	private int bad;
	private BigDecimal extraAlmoney;
	private int transCount;


	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}
	public Long getSeq_Order() {
		return seq_Order;
	}
	public void setSeq_Order(Long seq_Order) {
		this.seq_Order = seq_Order;
	}
	public Integer getMemberSeq() {
		return memberSeq;
	}
	public void setMemberSeq(Integer memberSeq) {
		this.memberSeq = memberSeq;
	}
	public Integer getSection_Special() {
		return section_Special;
	}
	public void setSection_Special(Integer section_Special) {
		this.section_Special = section_Special;
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
	public Integer getSection4() {
		return section4;
	}
	public void setSection4(Integer section4) {
		this.section4 = section4;
	}
	public Integer getSection5() {
		return section5;
	}
	public void setSection5(Integer section5) {
		this.section5 = section5;
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
	public String getFlagNickName() {
		return flagNickName;
	}
	public void setFlagNickName(String flagNickName) {
		this.flagNickName = flagNickName;
	}
	public String getFlagMinor() {
		return flagMinor;
	}
	public void setFlagMinor(String flagMinor) {
		this.flagMinor = flagMinor;
	}
	public String getFlagUse() {
		return flagUse;
	}
	public void setFlagUse(String flagUse) {
		this.flagUse = flagUse;
	}
	public String getFlagChoice() {
		return flagChoice;
	}
	public void setFlagChoice(String flagChoice) {
		this.flagChoice = flagChoice;
	}
	public Integer getFlagEvent() {
		return flagEvent;
	}
	public void setFlagEvent(Integer flagEvent) {
		this.flagEvent = flagEvent;
	}
	public Integer getBestNumber() {
		return bestNumber;
	}
	public void setBestNumber(Integer bestNumber) {
		this.bestNumber = bestNumber;
	}
	public Integer getReadCount() {
		return readCount;
	}
	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}
	public Integer getReadAlmoney() {
		return readAlmoney;
	}
	public void setReadAlmoney(Integer readAlmoney) {
		this.readAlmoney = readAlmoney;
	}
	public String getDateReg() {
		return dateReg;
	}
	public void setDateReg(String dateReg) {
		this.dateReg = dateReg;
	}
	public Timestamp getRegdate() {
		return regdate;
	}
	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}
	public Integer getReadCount_Answ() {
		return readCount_Answ;
	}
	public void setReadCount_Answ(Integer readCount_Answ) {
		this.readCount_Answ = readCount_Answ;
	}
	public Integer getAnswCount() {
		return answCount;
	}
	public void setAnswCount(Integer answCount) {
		this.answCount = answCount;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getLv() {
		return lv;
	}
	public void setLv(int lv) {
		this.lv = lv;
	}
	public int getEv_seq() {
		return ev_seq;
	}
	public void setEv_seq(int ev_seq) {
		this.ev_seq = ev_seq;
	}
	public int getQ_Seq() {
		return q_Seq;
	}
	public void setQ_Seq(int q_Seq) {
		this.q_Seq = q_Seq;
	}
	public String getEv_title() {
		return ev_title;
	}
	public void setEv_title(String ev_title) {
		this.ev_title = ev_title;
	}
	public String getConDate() {
		return conDate;
	}
	public void setConDate(String conDate) {
		this.conDate = conDate;
	}
	public int getEv_readCount() {
		return ev_readCount;
	}
	public void setEv_readCount(int ev_readCount) {
		this.ev_readCount = ev_readCount;
	}
	public int getEv_answCount() {
		return ev_answCount;
	}
	public void setEv_answCount(int ev_answCount) {
		this.ev_answCount = ev_answCount;
	}
	public int getEv_alMoney() {
		return ev_alMoney;
	}
	public void setEv_alMoney(int ev_alMoney) {
		this.ev_alMoney = ev_alMoney;
	}
	public int getItv_idx() {
		return itv_idx;
	}
	public void setItv_idx(int itv_idx) {
		this.itv_idx = itv_idx;
	}
	public String getItv_title() {
		return itv_title;
	}
	public void setItv_title(String itv_title) {
		this.itv_title = itv_title;
	}
	public int getItv_member_seq() {
		return itv_member_seq;
	}
	public void setItv_member_seq(int itv_member_seq) {
		this.itv_member_seq = itv_member_seq;
	}
	public String getItv_contents() {
		return itv_contents;
	}
	public void setItv_contents(String itv_contents) {
		this.itv_contents = itv_contents;
	}
	public Timestamp getItv_date_reg() {
		return itv_date_reg;
	}
	public void setItv_date_reg(Timestamp itv_date_reg) {
		this.itv_date_reg = itv_date_reg;
	}
	public int getItv_page_view() {
		return itv_page_view;
	}
	public void setItv_page_view(int itv_page_view) {
		this.itv_page_view = itv_page_view;
	}

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}


	public int getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}


	public String getQuestionBet() {
		return questionBet;
	}
	public void setQuestionBet(String questionBet) {
		this.questionBet = questionBet;
	}





	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getGroup_id() {
		return group_id;
	}
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
	public int getPhrase_id() {
		return phrase_id;
	}
	public void setPhrase_id(int phrase_id) {
		this.phrase_id = phrase_id;
	}
	public int getOccurrence() {
		return occurrence;
	}
	public void setOccurrence(int occurrence) {
		this.occurrence = occurrence;
	}
	public String getSpecial_term() {
		return special_term;
	}
	public void setSpecial_term(String special_term) {
		this.special_term = special_term;
	}
	public String getDisplay_term() {
		return display_term;
	}
	public void setDisplay_term(String display_term) {
		this.display_term = display_term;
	}
	public String getExpansion_tye() {
		return expansion_tye;
	}
	public void setExpansion_tye(String expansion_tye) {
		this.expansion_tye = expansion_tye;
	}
	public String getSource_term() {
		return source_term;
	}
	public void setSource_term(String source_term) {
		this.source_term = source_term;
	}


	public int getAnswerCount() {
		return answerCount;
	}
	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}



	public int getQuestionSeq() {
		return questionSeq;
	}
	public void setQuestionSeq(int questionSeq) {
		this.questionSeq = questionSeq;
	}




	public BigDecimal getThankAlmoney() {
		return thankAlmoney;
	}
	public void setThankAlmoney(BigDecimal thankAlmoney) {
		this.thankAlmoney = thankAlmoney;
	}





	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}






	public BigDecimal getQ_almoney() {
		return q_almoney;
	}
	public void setQ_almoney(BigDecimal q_almoney) {
		this.q_almoney = q_almoney;
	}
	public BigDecimal getA_almoney() {
		return a_almoney;
	}
	public void setA_almoney(BigDecimal a_almoney) {
		this.a_almoney = a_almoney;
	}



	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}



	public int getFlagNickPrice() {
		return flagNickPrice;
	}
	public void setFlagNickPrice(int flagNickPrice) {
		this.flagNickPrice = flagNickPrice;
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



	public int getGood() {
		return good;
	}
	public void setGood(int good) {
		this.good = good;
	}
	public int getBad() {
		return bad;
	}
	public void setBad(int bad) {
		this.bad = bad;
	}
	public BigDecimal getExtraAlmoney() {
		return extraAlmoney;
	}
	public void setExtraAlmoney(BigDecimal extraAlmoney) {
		this.extraAlmoney = extraAlmoney;
	}
	
	public int getTransCount() {
		return transCount;
	}
	public void setTransCount(int transCount) {
		this.transCount = transCount;
	}
	
	
	@Override
	public String toString() {
		return "QuestionVO [seq=" + seq + ", seq_Order=" + seq_Order + ", memberSeq=" + memberSeq + ", section_Special="
				+ section_Special + ", section1=" + section1 + ", section2=" + section2 + ", section3=" + section3
				+ ", section4=" + section4 + ", section5=" + section5 + ", title=" + title + ", contents=" + contents
				+ ", almoney=" + almoney + ", flagNickName=" + flagNickName + ", flagMinor=" + flagMinor + ", flagUse="
				+ flagUse + ", flagChoice=" + flagChoice + ", flagEvent=" + flagEvent + ", bestNumber=" + bestNumber
				+ ", readCount=" + readCount + ", readAlmoney=" + readAlmoney + ", dateReg=" + dateReg + ", regdate="
				+ regdate + ", readCount_Answ=" + readCount_Answ + ", answCount=" + answCount + ", photo=" + photo
				+ ", nickName=" + nickName + ", lv=" + lv + ", ev_seq=" + ev_seq + ", q_Seq=" + q_Seq + ", ev_title="
				+ ev_title + ", conDate=" + conDate + ", ev_readCount=" + ev_readCount + ", ev_answCount="
				+ ev_answCount + ", ev_alMoney=" + ev_alMoney + ", itv_idx=" + itv_idx + ", itv_title=" + itv_title
				+ ", itv_member_seq=" + itv_member_seq + ", itv_contents=" + itv_contents + ", itv_date_reg="
				+ itv_date_reg + ", itv_page_view=" + itv_page_view + "]";
	}



}
