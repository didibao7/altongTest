package com.altong.web.logic.ad;

import java.math.BigInteger;
import java.sql.Timestamp;

public class AdVO {
	private Integer seq;
	private String section1;
	private String adFile;
	private String adFileExt;
	private String periodStart;
	private String periodEnd;
	private String flagUse;
	private String url;
	private String maket; // market 의 오타인지 구분이 어려움
	private Integer flagAd;
	
	private Integer viewCount;
	private Integer payCount;
	private Integer clickCount;
	
	
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getSection1() {
		return section1;
	}
	public void setSection1(String section1) {
		this.section1 = section1;
	}
	public String getAdFile() {
		return adFile;
	}
	public void setAdFile(String adFile) {
		this.adFile = adFile;
	}
	public String getAdFileExt() {
		return adFileExt;
	}
	public void setAdFileExt(String adFileExt) {
		this.adFileExt = adFileExt;
	}
	public String getPeriodStart() {
		return periodStart;
	}
	public void setPeriodStart(String periodStart) {
		this.periodStart = periodStart;
	}
	public String getPeriodEnd() {
		return periodEnd;
	}
	public void setPeriodEnd(String periodEnd) {
		this.periodEnd = periodEnd;
	}
	public String getFlagUse() {
		return flagUse;
	}
	public void setFlagUse(String flagUse) {
		this.flagUse = flagUse;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMaket() {
		return maket;
	}
	public void setMaket(String maket) {
		this.maket = maket;
	}
	public Integer getFlagAd() {
		return flagAd;
	}
	public void setFlagAd(Integer flagAd) {
		this.flagAd = flagAd;
	}
	public Integer getViewCount() {
		return viewCount;
	}
	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}
	public Integer getPayCount() {
		return payCount;
	}
	public void setPayCount(Integer payCount) {
		this.payCount = payCount;
	}
	public Integer getClickCount() {
		return clickCount;
	}
	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}
	
	
}
