package com.altong.web.logic.section;

public class SectionVO {
	private Integer code;
	private Integer section1Code;	// T_SECTION2 테이블 컬럼
	private Integer section2Code;	// T_SECTION3/T_SECTION3_1 테이블 컬럼
	private Integer section3Code;	// T_SECTION4 테이블 컬럼
	private Integer section4Code;	// T_SECTION5/T_SECTION5$ 테이블 컬럼
	private Integer section5Code;
	private String codeName;
	private String codeName1;		// ASP 소스 fn_SQL_Get_Category_Name 함수에서 지정한 컬럼명
	private String codeName2;		// ASP 소스 fn_SQL_Get_Category_Name 함수에서 지정한 컬럼명
	private String codeName3;		// ASP 소스 fn_SQL_Get_Category_Name 함수에서 지정한 컬럼명
	private String codeName4;		// ASP 소스 fn_SQL_Get_Category_Name 함수에서 지정한 컬럼명
	private String codeName5;		// ASP 소스 fn_SQL_Get_Category_Name 함수에서 지정한 컬럼명
	private Integer sortNumber;
	private String sectionImgOn;
	private String sectionImgOff;
	private String flagUse;
	
	private Integer sectionCode1;	// ASP 소스 fn_SQL_Answer_Section1 함수에서 사용한 컬럼명
	private String sectionName1;	// ASP 소스 fn_SQL_Answer_Section1 함수에서 사용한 컬럼명
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Integer getSection1Code() {
		return section1Code;
	}
	public void setSection1Code(Integer section1Code) {
		this.section1Code = section1Code;
	}
	public Integer getSection2Code() {
		return section2Code;
	}
	public void setSection2Code(Integer section2Code) {
		this.section2Code = section2Code;
	}
	public Integer getSection3Code() {
		return section3Code;
	}
	public void setSection3Code(Integer section3Code) {
		this.section3Code = section3Code;
	}
	public Integer getSection4Code() {
		return section4Code;
	}
	public void setSection4Code(Integer section4Code) {
		this.section4Code = section4Code;
	}
	
	public Integer getSection5Code() {
		return section5Code;
	}
	public void setSection5Code(Integer section5Code) {
		this.section5Code = section5Code;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public String getCodeName1() {
		return codeName1;
	}
	public void setCodeName1(String codeName1) {
		this.codeName1 = codeName1;
	}
	public String getCodeName2() {
		return codeName2;
	}
	public void setCodeName2(String codeName2) {
		this.codeName2 = codeName2;
	}
	public String getCodeName3() {
		return codeName3;
	}
	public void setCodeName3(String codeName3) {
		this.codeName3 = codeName3;
	}
	public String getCodeName4() {
		return codeName4;
	}
	public void setCodeName4(String codeName4) {
		this.codeName4 = codeName4;
	}
	public String getCodeName5() {
		return codeName5;
	}
	public void setCodeName5(String codeName5) {
		this.codeName5 = codeName5;
	}
	public Integer getSortNumber() {
		return sortNumber;
	}
	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
	}
	public String getSectionImgOn() {
		return sectionImgOn;
	}
	public void setSectionImgOn(String sectionImgOn) {
		this.sectionImgOn = sectionImgOn;
	}
	public String getSectionImgOff() {
		return sectionImgOff;
	}
	public void setSectionImgOff(String sectionImgOff) {
		this.sectionImgOff = sectionImgOff;
	}
	public String getFlagUse() {
		return flagUse;
	}
	public void setFlagUse(String flagUse) {
		this.flagUse = flagUse;
	}
	public Integer getSectionCode1() {
		return sectionCode1;
	}
	public void setSectionCode1(Integer sectionCode1) {
		this.sectionCode1 = sectionCode1;
	}
	public String getSectionName1() {
		return sectionName1;
	}
	public void setSectionName1(String sectionName1) {
		this.sectionName1 = sectionName1;
	}
	
	
}
