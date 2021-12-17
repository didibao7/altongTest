package com.altong.web.logic.report;

public class ReportVO {
	private Long seq;
	private Long contentsSeq;
	private String contentsType;
	private Integer reportMemberSeq;
	private String reportReason;
	private String reportEtc;
	private String reportUrl;
	private String dateReg;
	private String adminStatus;
	private String adminEtc;
	private Integer resultMemberSeq;
	private String dateResult;
	
	
	private String reporter;		// MEMBERS nickName 컬럼
	private Integer reporterSeq;	// = reportMemberSeq
	private String reason;			// = reportReason
	private String comment;			// = reportEtc
	private String url;				// = reportUrl
	private String date;			// = dateReg
	private String adminComment;	// = adminEtc
	private Integer charge;			// = resultMemberSeq
	private String resultDate;		// = dateResult
	
	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}
	public Long getContentsSeq() {
		return contentsSeq;
	}
	public void setContentsSeq(Long contentsSeq) {
		this.contentsSeq = contentsSeq;
	}
	public String getContentsType() {
		return contentsType;
	}
	public void setContentsType(String contentsType) {
		this.contentsType = contentsType;
	}
	public Integer getReportMemberSeq() {
		return reportMemberSeq;
	}
	public void setReportMemberSeq(Integer reportMemberSeq) {
		this.reportMemberSeq = reportMemberSeq;
	}
	public String getReportReason() {
		return reportReason;
	}
	public void setReportReason(String reportReason) {
		this.reportReason = reportReason;
	}
	public String getReportEtc() {
		return reportEtc;
	}
	public void setReportEtc(String reportEtc) {
		this.reportEtc = reportEtc;
	}
	public String getReportUrl() {
		return reportUrl;
	}
	public void setReportUrl(String reportUrl) {
		this.reportUrl = reportUrl;
	}
	public String getDateReg() {
		return dateReg;
	}
	public void setDateReg(String dateReg) {
		this.dateReg = dateReg;
	}
	public String getAdminStatus() {
		return adminStatus;
	}
	public void setAdminStatus(String adminStatus) {
		this.adminStatus = adminStatus;
	}
	public String getAdminEtc() {
		return adminEtc;
	}
	public void setAdminEtc(String adminEtc) {
		this.adminEtc = adminEtc;
	}
	public Integer getResultMemberSeq() {
		return resultMemberSeq;
	}
	public void setResultMemberSeq(Integer resultMemberSeq) {
		this.resultMemberSeq = resultMemberSeq;
	}
	public String getDateResult() {
		return dateResult;
	}
	public void setDateResult(String dateResult) {
		this.dateResult = dateResult;
	}
	
	/*
	 * getReportDetail.asp
	 * */
	public String getReporter() {
		return reporter;
	}
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	public Integer getReporterSeq() {
		return reporterSeq;
	}
	public void setReporterSeq(Integer reporterSeq) {
		this.reporterSeq = reporterSeq;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAdminComment() {
		return adminComment;
	}
	public void setAdminComment(String adminComment) {
		this.adminComment = adminComment;
	}
	public Integer getCharge() {
		return charge;
	}
	public void setCharge(Integer charge) {
		this.charge = charge;
	}
	public String getResultDate() {
		return resultDate;
	}
	public void setResultDate(String resultDate) {
		this.resultDate = resultDate;
	}
	
	
}
