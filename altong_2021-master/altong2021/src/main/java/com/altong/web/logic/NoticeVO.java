package com.altong.web.logic;

public class NoticeVO {
	private int seq;
	private int top_ch;
	private String title;
	private String contents;
	private String dateReg;
	private String editTimeReg;
	private int viewCount;
	private String lang;

	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getTop_ch() {
		return top_ch;
	}
	public void setTop_ch(int top_ch) {
		this.top_ch = top_ch;
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
	public String getDateReg() {
		return dateReg;
	}
	public void setDateReg(String dateReg) {
		this.dateReg = dateReg;
	}
	public String getEditTimeReg() {
		return editTimeReg;
	}
	public void setEditTimeReg(String editTimeReg) {
		this.editTimeReg = editTimeReg;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}


}
