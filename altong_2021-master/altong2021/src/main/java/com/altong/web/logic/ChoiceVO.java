package com.altong.web.logic;

import java.math.BigDecimal;

public class ChoiceVO {
	private Integer countC;
	private Integer countN;
	private BigDecimal sum_A_ChoicedAlmoney;
	private BigDecimal sum_A_ViewAlmoney;
	
	public Integer getCountC() {
		return countC;
	}
	public void setCountC(Integer countC) {
		this.countC = countC;
	}
	public Integer getCountN() {
		return countN;
	}
	public void setCountN(Integer countN) {
		this.countN = countN;
	}
	public BigDecimal getSum_A_ChoicedAlmoney() {
		return sum_A_ChoicedAlmoney;
	}
	public void setSum_A_ChoicedAlmoney(BigDecimal sum_A_ChoicedAlmoney) {
		this.sum_A_ChoicedAlmoney = sum_A_ChoicedAlmoney;
	}
	public BigDecimal getSum_A_ViewAlmoney() {
		return sum_A_ViewAlmoney;
	}
	public void setSum_A_ViewAlmoney(BigDecimal sum_A_ViewAlmoney) {
		this.sum_A_ViewAlmoney = sum_A_ViewAlmoney;
	}
	
}
