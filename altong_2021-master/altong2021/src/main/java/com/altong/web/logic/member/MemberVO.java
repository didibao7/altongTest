package com.altong.web.logic.member;

import java.math.BigDecimal;

public class MemberVO {
	private Integer seq;
	private String password;
	private String pw;
	private String name;
	private String nickName;
	private String email;
	private String lv;
	private Integer country;
	private String phone;
	private String phone_ori;
	private String hp;
	private String photo;
	private String photoWidth;
	private String photoHeight;
	private String intro;
	private String job;
	private String area;
	private String realEstate;
	private String residence;
	private String marriage;
	private String children;
	private String car;
	private String yearIncome;
	private String healthFlag;
	private String healthDetail;
	private String likeField;
	private String likeFieldEtc;
	private BigDecimal almoney;
	private BigDecimal alpayKR;
	private BigDecimal al_Total;
	private BigDecimal al_Question;
	private BigDecimal al_Answer;
	private BigDecimal al_Answerer;
	private BigDecimal al_Recmd;
	private BigDecimal q_Almoney;
	private BigDecimal a_Almoney;
	private Integer q_ChoiceCount;
	private Integer q_Count;
	private Integer a_ChoicedCount;
	private Integer a_Count;
	private String flagRealName;
	private String flagCert;
	private String flagAdult;
	private String flagEmail;
	private String flagSMS;
	private String flagProfileOpen;
	private String flagSelfAnswer;
	private String flagDel;
	private String dateLogin;
	private String dateReg;
	private String regdate;
	private String chuCode;
	private Integer questionCount;
	private Long questionMoney;
	private Integer answerCount;
	private Long answerMoney;
	private String appID;
	private String memo;
	private Integer univ;
	private Integer univ_Student;
	private String univ_ID;
	private String univ_Major;
	private String univ_Email;
	private String sAuthType;
	private String sName;
	private String sDI;
	private String sBirthDate;
	private String sGender;
	private String sMobileCo;
	private String sCipherTime;
	private String sRequestNumber;
	private String sResponseNumber;
	private String sNationalInfo;
	private Integer memberType;
	private String login_Lock;
	private String dateDel;

	private int rankQ;
	private int rankA;
	private int countA;
	private int sumA;
	private int idx;

	private int QReplyCount;
	private int AReplyCount;
	private int sumQ;
	private int countV;
	private BigDecimal RMoney;
	private BigDecimal earnTotal;
	private int countC;
	private int countChoicedA;
	private int countQ;

	private String dateReg24h;
	private int countNotChoiceQ;
	private int countNotAnswerQ;
	private int countRecivedAnswer;
	private int qRecivedReplyCount;
	private int aRecivedReplyCount;
	private int countZzim;
	private int countTemp;
	private int childCnt;

	private int rownum;

	private String host;		// 가입 사이트 URL
	private String nation;		// 가입 국적
	private String lang;		// 언어 선택

	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLv() {
		return lv;
	}
	public void setLv(String lv) {
		this.lv = lv;
	}
	public Integer getCountry() {
		return country;
	}
	public void setCountry(Integer country) {
		this.country = country;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhone_ori() {
		return phone_ori;
	}
	public void setPhone_ori(String phone_ori) {
		this.phone_ori = phone_ori;
	}
	public String getHp() {
		return hp;
	}
	public void setHp(String hp) {
		this.hp = hp;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getPhotoWidth() {
		return photoWidth;
	}
	public void setPhotoWidth(String photoWidth) {
		this.photoWidth = photoWidth;
	}
	public String getPhotoHeight() {
		return photoHeight;
	}
	public void setPhotoHeight(String photoHeight) {
		this.photoHeight = photoHeight;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getRealEstate() {
		return realEstate;
	}
	public void setRealEstate(String realEstate) {
		this.realEstate = realEstate;
	}
	public String getResidence() {
		return residence;
	}
	public void setResidence(String residence) {
		this.residence = residence;
	}
	public String getMarriage() {
		return marriage;
	}
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	public String getChildren() {
		return children;
	}
	public void setChildren(String children) {
		this.children = children;
	}
	public String getCar() {
		return car;
	}
	public void setCar(String car) {
		this.car = car;
	}
	public String getYearIncome() {
		return yearIncome;
	}
	public void setYearIncome(String yearIncome) {
		this.yearIncome = yearIncome;
	}
	public String getHealthFlag() {
		return healthFlag;
	}
	public void setHealthFlag(String healthFlag) {
		this.healthFlag = healthFlag;
	}
	public String getHealthDetail() {
		return healthDetail;
	}
	public void setHealthDetail(String healthDetail) {
		this.healthDetail = healthDetail;
	}
	public String getLikeField() {
		return likeField;
	}
	public void setLikeField(String likeField) {
		this.likeField = likeField;
	}
	public String getLikeFieldEtc() {
		return likeFieldEtc;
	}
	public void setLikeFieldEtc(String likeFieldEtc) {
		this.likeFieldEtc = likeFieldEtc;
	}
	public BigDecimal getAlmoney() {
		return almoney;
	}
	public void setAlmoney(BigDecimal almoney) {
		this.almoney = almoney;
	}
	public BigDecimal getAlpayKR() {
		return alpayKR;
	}
	public void setAlpayKR(BigDecimal alpayKR) {
		this.alpayKR = alpayKR;
	}
	public BigDecimal getAl_Total() {
		return al_Total;
	}
	public void setAl_Total(BigDecimal al_Total) {
		this.al_Total = al_Total;
	}
	public BigDecimal getAl_Question() {
		return al_Question;
	}
	public void setAl_Question(BigDecimal al_Question) {
		this.al_Question = al_Question;
	}
	public BigDecimal getAl_Answer() {
		return al_Answer;
	}
	public void setAl_Answer(BigDecimal al_Answer) {
		this.al_Answer = al_Answer;
	}
	public BigDecimal getAl_Answerer() {
		return al_Answerer;
	}
	public void setAl_Answerer(BigDecimal al_Answerer) {
		this.al_Answerer = al_Answerer;
	}
	public BigDecimal getAl_Recmd() {
		return al_Recmd;
	}
	public void setAl_Recmd(BigDecimal al_Recmd) {
		this.al_Recmd = al_Recmd;
	}
	public BigDecimal getQ_Almoney() {
		return q_Almoney;
	}
	public void setQ_Almoney(BigDecimal q_Almoney) {
		this.q_Almoney = q_Almoney;
	}
	public BigDecimal getA_Almoney() {
		return a_Almoney;
	}
	public void setA_Almoney(BigDecimal a_Almoney) {
		this.a_Almoney = a_Almoney;
	}
	public Integer getQ_ChoiceCount() {
		return q_ChoiceCount;
	}
	public void setQ_ChoiceCount(Integer q_ChoiceCount) {
		this.q_ChoiceCount = q_ChoiceCount;
	}
	public Integer getQ_Count() {
		return q_Count;
	}
	public void setQ_Count(Integer q_Count) {
		this.q_Count = q_Count;
	}
	public Integer getA_ChoicedCount() {
		return a_ChoicedCount;
	}
	public void setA_ChoicedCount(Integer a_ChoicedCount) {
		this.a_ChoicedCount = a_ChoicedCount;
	}
	public Integer getA_Count() {
		return a_Count;
	}
	public void setA_Count(Integer a_Count) {
		this.a_Count = a_Count;
	}
	public String getFlagRealName() {
		return flagRealName;
	}
	public void setFlagRealName(String flagRealName) {
		this.flagRealName = flagRealName;
	}
	public String getFlagCert() {
		return flagCert;
	}
	public void setFlagCert(String flagCert) {
		this.flagCert = flagCert;
	}
	public String getFlagAdult() {
		return flagAdult;
	}
	public void setFlagAdult(String flagAdult) {
		this.flagAdult = flagAdult;
	}
	public String getFlagEmail() {
		return flagEmail;
	}
	public void setFlagEmail(String flagEmail) {
		this.flagEmail = flagEmail;
	}
	public String getFlagSMS() {
		return flagSMS;
	}
	public void setFlagSMS(String flagSMS) {
		this.flagSMS = flagSMS;
	}
	public String getFlagProfileOpen() {
		return flagProfileOpen;
	}
	public void setFlagProfileOpen(String flagProfileOpen) {
		this.flagProfileOpen = flagProfileOpen;
	}
	public String getFlagSelfAnswer() {
		return flagSelfAnswer;
	}
	public void setFlagSelfAnswer(String flagSelfAnswer) {
		this.flagSelfAnswer = flagSelfAnswer;
	}
	public String getFlagDel() {
		return flagDel;
	}
	public void setFlagDel(String flagDel) {
		this.flagDel = flagDel;
	}
	public String getDateLogin() {
		return dateLogin;
	}
	public void setDateLogin(String dateLogin) {
		this.dateLogin = dateLogin;
	}
	public String getDateReg() {
		return dateReg;
	}
	public void setDateReg(String dateReg) {
		this.dateReg = dateReg;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getChuCode() {
		return chuCode;
	}
	public void setChuCode(String chuCode) {
		this.chuCode = chuCode;
	}
	public Integer getQuestionCount() {
		return questionCount;
	}
	public void setQuestionCount(Integer questionCount) {
		this.questionCount = questionCount;
	}
	public Long getQuestionMoney() {
		return questionMoney;
	}
	public void setQuestionMoney(Long questionMoney) {
		this.questionMoney = questionMoney;
	}
	public Integer getAnswerCount() {
		return answerCount;
	}
	public void setAnswerCount(Integer answerCount) {
		this.answerCount = answerCount;
	}
	public Long getAnswerMoney() {
		return answerMoney;
	}
	public void setAnswerMoney(Long answerMoney) {
		this.answerMoney = answerMoney;
	}
	public String getAppID() {
		return appID;
	}
	public void setAppID(String appID) {
		this.appID = appID;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Integer getUniv() {
		return univ;
	}
	public void setUniv(Integer univ) {
		this.univ = univ;
	}
	public Integer getUniv_Student() {
		return univ_Student;
	}
	public void setUniv_Student(Integer univ_Student) {
		this.univ_Student = univ_Student;
	}
	public String getUniv_ID() {
		return univ_ID;
	}
	public void setUniv_ID(String univ_ID) {
		this.univ_ID = univ_ID;
	}
	public String getUniv_Major() {
		return univ_Major;
	}
	public void setUniv_Major(String univ_Major) {
		this.univ_Major = univ_Major;
	}
	public String getUniv_Email() {
		return univ_Email;
	}
	public void setUniv_Email(String univ_Email) {
		this.univ_Email = univ_Email;
	}
	public String getsAuthType() {
		return sAuthType;
	}
	public void setsAuthType(String sAuthType) {
		this.sAuthType = sAuthType;
	}
	public String getsName() {
		return sName;
	}
	public void setsName(String sName) {
		this.sName = sName;
	}
	public String getsDI() {
		return sDI;
	}
	public void setsDI(String sDI) {
		this.sDI = sDI;
	}
	public String getsBirthDate() {
		return sBirthDate;
	}
	public void setsBirthDate(String sBirthDate) {
		this.sBirthDate = sBirthDate;
	}
	public String getsGender() {
		return sGender;
	}
	public void setsGender(String sGender) {
		this.sGender = sGender;
	}
	public String getsMobileCo() {
		return sMobileCo;
	}
	public void setsMobileCo(String sMobileCo) {
		this.sMobileCo = sMobileCo;
	}
	public String getsCipherTime() {
		return sCipherTime;
	}
	public void setsCipherTime(String sCipherTime) {
		this.sCipherTime = sCipherTime;
	}
	public String getsRequestNumber() {
		return sRequestNumber;
	}
	public void setsRequestNumber(String sRequestNumber) {
		this.sRequestNumber = sRequestNumber;
	}
	public String getsResponseNumber() {
		return sResponseNumber;
	}
	public void setsResponseNumber(String sResponseNumber) {
		this.sResponseNumber = sResponseNumber;
	}
	public String getsNationalInfo() {
		return sNationalInfo;
	}
	public void setsNationalInfo(String sNationalInfo) {
		this.sNationalInfo = sNationalInfo;
	}
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
	public String getLogin_Lock() {
		return login_Lock;
	}
	public void setLogin_Lock(String login_Lock) {
		this.login_Lock = login_Lock;
	}
	public String getDateDel() {
		return dateDel;
	}
	public void setDateDel(String dateDel) {
		this.dateDel = dateDel;
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
	public int getCountA() {
		return countA;
	}
	public void setCountA(int countA) {
		this.countA = countA;
	}
	public int getSumA() {
		return sumA;
	}
	public void setSumA(int sumA) {
		this.sumA = sumA;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}

	public int getQReplyCount() {
		return QReplyCount;
	}
	public void setQReplyCount(int qReplyCount) {
		QReplyCount = qReplyCount;
	}
	public int getAReplyCount() {
		return AReplyCount;
	}
	public void setAReplyCount(int aReplyCount) {
		AReplyCount = aReplyCount;
	}
	public int getSumQ() {
		return sumQ;
	}
	public void setSumQ(int sumQ) {
		this.sumQ = sumQ;
	}
	public int getCountV() {
		return countV;
	}
	public void setCountV(int countV) {
		this.countV = countV;
	}

	public BigDecimal getRMoney() {
		return RMoney;
	}
	public void setRMoney(BigDecimal rMoney) {
		RMoney = rMoney;
	}
	public BigDecimal getEarnTotal() {
		return earnTotal;
	}
	public void setEarnTotal(BigDecimal earnTotal) {
		this.earnTotal = earnTotal;
	}
	public int getCountC() {
		return countC;
	}
	public void setCountC(int countC) {
		this.countC = countC;
	}
	public int getCountChoicedA() {
		return countChoicedA;
	}
	public void setCountChoicedA(int countChoicedA) {
		this.countChoicedA = countChoicedA;
	}
	public int getCountQ() {
		return countQ;
	}
	public void setCountQ(int countQ) {
		this.countQ = countQ;
	}
	public String getDateReg24h() {
		return dateReg24h;
	}
	public void setDateReg24h(String dateReg24h) {
		this.dateReg24h = dateReg24h;
	}
	public int getCountNotChoiceQ() {
		return countNotChoiceQ;
	}
	public void setCountNotChoiceQ(int countNotChoiceQ) {
		this.countNotChoiceQ = countNotChoiceQ;
	}
	public int getCountNotAnswerQ() {
		return countNotAnswerQ;
	}
	public void setCountNotAnswerQ(int countNotAnswerQ) {
		this.countNotAnswerQ = countNotAnswerQ;
	}
	public int getCountRecivedAnswer() {
		return countRecivedAnswer;
	}
	public void setCountRecivedAnswer(int countRecivedAnswer) {
		this.countRecivedAnswer = countRecivedAnswer;
	}
	public int getqRecivedReplyCount() {
		return qRecivedReplyCount;
	}
	public void setqRecivedReplyCount(int qRecivedReplyCount) {
		this.qRecivedReplyCount = qRecivedReplyCount;
	}
	public int getaRecivedReplyCount() {
		return aRecivedReplyCount;
	}
	public void setaRecivedReplyCount(int aRecivedReplyCount) {
		this.aRecivedReplyCount = aRecivedReplyCount;
	}
	public int getCountZzim() {
		return countZzim;
	}
	public void setCountZzim(int countZzim) {
		this.countZzim = countZzim;
	}
	public int getCountTemp() {
		return countTemp;
	}
	public void setCountTemp(int countTemp) {
		this.countTemp = countTemp;
	}
	public int getChildCnt() {
		return childCnt;
	}
	public void setChildCnt(int childCnt) {
		this.childCnt = childCnt;
	}
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
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


}
