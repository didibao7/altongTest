<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<body>
  	<link rel="stylesheet" href="/pub/member/bank/index/index.css?ver=1.3">
	<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.3">
	<script src="/pub/member/bank/index/uisearch.js"></script>
	
<%@ include file="/pub/menu/topMenu.jsp"%>
      
<div id="atm_wrapper">
      <div id="atm_bank_wrapper0" class="site-content">
        <!--wrapper start -->
        <div class="center">
	        <form name="frm_sch" method="post">
	          <div class="atm_bank_myaccount_pcgray">
	            <p class="atm_bank_tit"><spring:message code="msg_0174"/></p>
	            <div class="atm_bank_myaccount">
	              <p class="atm_bank_sp1"><div style="color:#5a5a5a;display:inline-block"><spring:message code="msg_0716"/>&nbsp&nbsp </div><span class="totalIncrease atm_bank_blue"></span></p>
	              <p class="atm_bank_c1">
	              	<img src="/Common/images/exchange_red.svg">
	                <span>
	                <span class="atm_color_org atm_superbold"
	                  ></span
	                >
	                <span class="atm_bank_al"><spring:message code="msg_0136"/></span></span>
	              </p>
	              <p class="atm_bank_sp2">
	                ※ <spring:message code="msg_0718"/>
	              </p>
	              <p class="atm_bank_exchange">
	              	<c:choose>
						<c:when test="${userLv < 2}">
	              			<img src="/Common/images/exchange.svg" onclick="alert('<spring:message code="msg_0721"/>'); return false;">
	              			<span style="color:#5a5a5a;"><spring:message code="msg_0720"/></span>
	              		</c:when>
	              		<c:otherwise>
	              			<img src="/Common/images/exchange.svg" onclick="location.href='/member/account/exchange/exchangeAsk';">
	              			<span style="color:#5a5a5a;"><spring:message code="msg_0720"/></span>
	              		</c:otherwise>
	              	</c:choose>
	              </p>
	            </div>
	          </div>
	          <div>
	            <div class="atm_bank_tblDiv">
	              <p class="atm_bank_inTitM atm_bank_inTitM_down"><spring:message code="msg_0719"/></p>
	              <div class="atm_bank_inDiv">
	                <table class="atm_bank_tbl">
	                  <tr class="atm_bank_tbl_tr1 atm_bank_tbl_tr" id="atm_bank_tbl_tr1">
	                    <td><i class="material-icons">add_circle</i></td>
	                    <td class="atm_bank_inTbl_tit"><spring:message code="msg_0694"/></td>
	                    <td><span class="atm_bank_blue atm_bank_cumulative atm_bank_increase"></span><spring:message code="msg_0136"/></td>
	                    <td class="atm_bank_blue atm_bank_titInc"></td>
	                    <td><span class="atm_bank_v"><img src="/pub/member/bank/index/bank_arrow.svg"></span></td>
	                  </tr>
	                </table>
	                <div class="atm_bank_tbl2">
	                  <table>
	                    <tr class="atm_bank_tbl_trBody" id="atm_bank_tbl_tr1_body">
	                      <td colspan="5" class="atm_bank_tbl_divs" id="atm_bank_tbl_divsInc">
	                        <div class="atm_bank_tbl_divA top_line">
	                          <div class="atm_bank_intit"><spring:message code="msg_0722"/></div>
	                          <div>
	                            <span data-type="10"></span><spring:message code="msg_0136"/></br>
	                            <span class="atm_bank_blue"></span>
	                          </div>
	                        </div>
	                        <div class="atm_bank_tbl_divB top_line">
	                          <div class="atm_bank_intit"><spring:message code="msg_0723"/></div>
	                          <div>
	                            <span data-type="17"></span><spring:message code="msg_0136"/></br>
	                            <span class="atm_bank_blue"></span>
	                          </div>
	                        </div>
	                        <div class="atm_bank_tbl_divA">
	                          <div class="atm_bank_intit"><spring:message code="msg_0724"/></div>
	                          <div>
	                            <span data-type="18"></span><spring:message code="msg_0136"/></br>
	                            <span class="atm_bank_blue"></span>
	                          </div>
	                        </div>
	                        <div class="atm_bank_tbl_divB">
	                          <div class="atm_bank_intit"><spring:message code="msg_0725"/></div>
	                          <div>
	                            <span data-type="13"></span><spring:message code="msg_0136"/></br>
	                            <span class="atm_bank_blue"></span>
	                          </div>
	                        </div>
	                        <div class="atm_bank_tbl_divA">
	                          <div class="atm_bank_intit"><spring:message code="msg_0726"/></div>
	                          <div>
	                            <span data-type="20"></span><spring:message code="msg_0136"/></br>
	                            <span class="atm_bank_blue"></span>
	                          </div>
	                        </div>
	                        <div class="atm_bank_tbl_divB">
	                          <div class="atm_bank_intit"><spring:message code="msg_0727"/></div>
	                          <div>
	                            <span data-type="19"></span><spring:message code="msg_0136"/></br>
	                            <span class="atm_bank_blue"></span>
	                          </div>
	                        </div>
	                        <div class="atm_bank_tbl_divA">
	                          <div class="atm_bank_intit">ANSWERer</div>
	                          <div>
	                            <span data-type="11"></span><spring:message code="msg_0136"/></br>
	                            <span class="atm_bank_blue"></span>
	                          </div>
	                        </div>
	                        <div class="atm_bank_tbl_divB">
	                          <div class="atm_bank_intit"><spring:message code="msg_0728"/></div>
	                          <div>
	                            <span data-type="3"></span><spring:message code="msg_0136"/></br>
	                            <span class="atm_bank_blue"></span>
	                          </div>
	                        </div>
	                        <div class="atm_bank_tbl_divA">
	                          <div class="atm_bank_intit"><spring:message code="msg_0729"/></div>
	                          <div>
	                            <span data-type="5"></span><spring:message code="msg_0136"/></br>
	                            <span class="atm_bank_blue"></span>
	                          </div>
	                        </div>
	                        <div class="atm_bank_tbl_divB">
	                          <div class="atm_bank_intit"><spring:message code="msg_0730"/></div>
	                          <div>
	                            <span data-type="14"></span><spring:message code="msg_0136"/></br>
	                            <span class="atm_bank_blue"></span>
	                          </div>
	                        </div>
	                        <div class="atm_bank_tbl_divA">
	                          <div class="atm_bank_intit"><spring:message code="msg_0731"/></div>
	                          <div>
	                            <span data-type="15"></span><spring:message code="msg_0136"/></br>
	                            <span class="atm_bank_blue"></span>
	                          </div>
	                        </div>
	                        <div class="atm_bank_tbl_divB">
	                          <div class="atm_bank_intit"><spring:message code="msg_0732"/></div>
	                          <div>
	                            <span data-type="16"></span><spring:message code="msg_0136"/></br>
	                            <span class="atm_bank_blue"></span>
	                          </div>
	                        </div>
	                        
	                        
	                        <div class="atm_bank_tbl_divA">
	                          <div class="atm_bank_intit"><spring:message code="msg_0735"/></div>
	                          <div>
	                            <span data-type="46"></span><spring:message code="msg_0136"/></br>
	                            <span class="atm_bank_blue"></span>
	                          </div>
	                        </div>
	                        <div class="atm_bank_tbl_divB">
	                          <div class="atm_bank_intit"><spring:message code="msg_0753"/></div>
	                          <div>
	                            <span data-type="52"></span><spring:message code="msg_0136"/></br>
	                            <span class="atm_bank_blue"></span>
	                          </div>
	                        </div>
	                        <div class="atm_bank_tbl_divA">
	                          <div class="atm_bank_intit"><spring:message code="msg_0754"/></div>
	                          <div>
	                            <span data-type="48"></span><spring:message code="msg_0136"/></br>
	                            <span class="atm_bank_blue"></span>
	                          </div>
	                        </div>
	                        <div class="atm_bank_tbl_divB">
	                          <div class="atm_bank_intit"><spring:message code="msg_0755"/></div>
	                          <div>
	                            <span data-type="50"></span><spring:message code="msg_0136"/></br>
	                            <span class="atm_bank_blue"></span>
	                          </div>
	                        </div>
	                      </td>
	                    </tr>
	                  </table>
	                  </div>
	
	                <table class="atm_bank_tbl">
	                  <tr class="atm_bank_tbl_tr2 atm_bank_tbl_tr" id="atm_bank_tbl_tr2">
	                    <td><i class="material-icons">add_circle</i></td>
	                    <td class="atm_bank_inTbl_tit"><spring:message code="msg_0695"/></td>
	                    <td><span class="atm_bank_red atm_bank_cumulative atm_bank_decrease"></span><spring:message code="msg_0136"/></td>
	                    <td class="atm_bank_red atm_bank_titInc"></td>
	                    <td><span class="atm_bank_v"><img src="/pub/member/bank/index/bank_arrow.svg"></span></td>
	                  </tr>
	                </table>
	                <div class="atm_bank_tbl2">
	                <table>
	                  <tr class="atm_bank_tbl_trBody" id="atm_bank_tbl_tr2_body">
	                    <td colspan="5" class="atm_bank_tbl_divs" id="atm_bank_tbl_divsDec">
	                      <div class="atm_bank_tbl_divA top_line">
	                        <div class="atm_bank_intit"><spring:message code="msg_0722"/></div>
	                        <div>
	                          <span data-type="43"></span><spring:message code="msg_0136"/></br>
	                          <span class="atm_bank_red"></span>
	                        </div>
	                      </div>
	                      <div class="atm_bank_tbl_divB top_line">
	                        <div class="atm_bank_intit"><spring:message code="msg_0723"/></div>
	                        <div>
	                          <span data-type="44"></span><spring:message code="msg_0136"/></br>
	                          <span class="atm_bank_red"></span>
	                        </div>
	                      </div>
	                      <div class="atm_bank_tbl_divA">
	                        <div class="atm_bank_intit"><spring:message code="msg_0726"/></div>
	                        <div>
	                          <span data-type="3"></span><spring:message code="msg_0136"/></br>
	                          <span class="atm_bank_red"></span>
	                        </div>
	                      </div>
	                      <div class="atm_bank_tbl_divB">
	                        <div class="atm_bank_intit"><spring:message code="msg_0729"/></div>
	                        <div>
	                          <span data-type="4"></span><spring:message code="msg_0136"/></br>
	                          <span class="atm_bank_red"></span>
	                        </div>
	                      </div>
	                      <div class="atm_bank_tbl_divA">
	                        <div class="atm_bank_intit"><spring:message code="msg_0245"/></div>
	                        <div>
	                          <span data-type="1"></span><spring:message code="msg_0136"/></br>
	                          <span class="atm_bank_red"></span>
	                        </div>
	                      </div>
	                      
	                      <div class="atm_bank_tbl_divB">
	                        <div class="atm_bank_intit"><spring:message code="msg_0756"/></div>
	                        <div>
	                          <span data-type="45"></span><spring:message code="msg_0136"/></br>
	                          <span class="atm_bank_red"></span>
	                        </div>
	                      </div>
	                      <div class="atm_bank_tbl_divA">
	                        <div class="atm_bank_intit"><spring:message code="msg_0757"/></div>
	                        <div>
	                          <span data-type="49"></span><spring:message code="msg_0136"/></br>
	                          <span class="atm_bank_red"></span>
	                        </div>
	                      </div>
	                      <div class="atm_bank_tbl_divB">
	                        <div class="atm_bank_intit"><spring:message code="msg_0733"/></div>
	                        <div>
	                          <span data-type="53"></span><spring:message code="msg_0136"/></br>
	                          <span class="atm_bank_red"></span>
	                        </div>
	                      </div>
	                    </td>
	                  </tr>
	                </table>
	              </table>
	            </div>
	
	                <table class="atm_bank_tbl">
	                  <tr class="atm_bank_tbl_tr3 atm_bank_tbl_tr" id="atm_bank_tbl_tr3">
	                    <td><i class="material-icons">add_circle</i></td>
	                    <td class="atm_bank_inTbl_tit"><spring:message code="msg_0720"/></td>
	                    <td><span class="atm_bank_green atm_bank_cumulative atm_bank_acceptance"></span><spring:message code="msg_0136"/></td>
	                    <td></td>
	                    <td><span class="atm_bank_v"><img src="/pub/member/bank/index/bank_arrow.svg"></span></td>
	                  </tr>
	                </table>
	                <div class="atm_bank_tbl2">
	                <table style="width:100%">
	                  <tr id="atm_bank_tbl_tr3_body">
	                    <td colspan="5">
	                      <table
	                        class="atm_bank_inTbl2 atm_bank_inTbl"
	                        id="atm_bank_inTblC">
	                            <p class="atm_bank_p1">
					                              <spring:message code="msg_0736"/>
	                            </p>
	                        <tr>
	                          <td class="atm_bank_latDat"></td>
	                          <td class="atm_bank_latAl"><span></span><spring:message code="msg_0136"/></td>
	                        </tr>
	                      </table>
	                    </td>
	                  </tr>
	                </table>
	              </div>
	              </div>
	              <p class="bot_line"></p>
	            </div>
	          </div>
	          <div class="atm_bank_grp">
	            <p class="atm_bank_inTitM atm_bank_graph"><spring:message code="msg_0737"/> <span class="atm_bank_v"><img src="/pub/member/bank/index/bank_arrow.svg"></span>
	            </p>
	            <div class="atm_bank_grpDiv"><div
	              id="almoney-chart-wrapper"
	              style="width:100%;background-color: white; padding:10px;">
	              <canvas id="almoney-chart"></canvas>
	            </div>
	          </div>
	            <p class="bot_line"></p>
	          <div>
	            <div class="atm_bank_detailDiv">
	              <p class="atm_bank_inTitM"><spring:message code="msg_0738"/></p>
	              <table class="atm_bank_detTbl">
	                <tr>
	                  <td><spring:message code="msg_0739"/></td>
	                  <td id="gubun">
	                    <input
	                      class="atm_bank_rbBtn"
	                      type="radio"
	                      name="atm_bank_detRb1"
	                      id="atm_bank_rbA"
	                      value="atm_bank_rbA"
	                      checked=""
	                      data-child=""
	                    /><label for="atm_bank_rbA"><spring:message code="msg_0161"/></label>
	                    <input
	                      class="atm_bank_rbBtn"
	                      type="radio"
	                      name="atm_bank_detRb1"
	                      id="atm_bank_rbB"
	                      value="atm_bank_rbB"
	                      data-child="#atm_bank_inDetTbl_td1"
	                    /><label for="atm_bank_rbB"><spring:message code="msg_0694"/></label>
	                    <input
	                      class="atm_bank_rbBtn"
	                      type="radio"
	                      name="atm_bank_detRb1"
	                      id="atm_bank_rbC"
	                      value="atm_bank_rbC"
	                      data-child="#atm_bank_inDetTbl_td2"
	                    /><label for="atm_bank_rbC"><spring:message code="msg_0695"/></label>
	                    <input
	                      class="atm_bank_rbBtn"
	                      type="radio"
	                      name="atm_bank_detRb1"
	                      id="atm_bank_rbD"
	                      value="atm_bank_rbD"
	                      data-child="#atm_bank_inDetTbl_td3"
	                    /><label for="atm_bank_rbD"><spring:message code="msg_0720"/></label>
	                  </td>
	                </tr>
	              </table>
	              <div id="atm_bank_detTbl_midDiv">
	              <table class="atm_bank_detTbl">
	                <tr class="atm_bank_inDetTbl">
	                  <td><spring:message code="msg_0740"/></td>
	                  <td id="atm_bank_inDetTbl_td1" class="trade_type_selector">
	                    <input type="checkbox" id="atm_bank_detCbA0" data-type="" class="atm_bank_detCbAll" checked/><label
	                      for="atm_bank_detCbA0"
	                      ><spring:message code="msg_0161"/></label
	                    >
	                    <input
	                      type="checkbox"
	                      id="atm_bank_detCbA1"
	                      data-type="10" checked
	                    /><label for="atm_bank_detCbA1"><spring:message code="msg_0722"/></label>
	                    <input
	                      type="checkbox"
	                      id="atm_bank_detCbA2"
	                      data-type="17" checked
	                    /><label for="atm_bank_detCbA2"><spring:message code="msg_0723"/></label>
	                    <input
	                      type="checkbox"
	                      id="atm_bank_detCbA3"
	                      data-type="18" checked
	                    /><label for="atm_bank_detCbA3"><spring:message code="msg_0724"/></label>
	                    <input
	                      type="checkbox"
	                      id="atm_bank_detCbA4"
	                      data-type="13" checked
	                    /><label for="atm_bank_detCbA4"><spring:message code="msg_0725"/></label>
	                    <input
	                      type="checkbox"
	                      id="atm_bank_detCbA5"
	                      data-type="20" checked
	                    /><label for="atm_bank_detCbA5"><spring:message code="msg_0726"/></label>
	                    <input
	                      type="checkbox"
	                      id="atm_bank_detCbA6"
	                      data-type="19" checked
	                    /><label for="atm_bank_detCbA6"><spring:message code="msg_0727"/></label>
	                    <input
	                      type="checkbox"
	                      id="atm_bank_detCbA7"
	                      data-type="11" checked
	                    /><label for="atm_bank_detCbA7">ANSWERer</label>
	                    <input
	                      type="checkbox"
	                      id="atm_bank_detCbA8"
	                      data-type="3" checked
	                    /><label for="atm_bank_detCbA8"><spring:message code="msg_0728"/></label>
	                    <input
	                      type="checkbox"
	                      id="atm_bank_detCbA9"
	                      data-type="5" checked
	                    /><label for="atm_bank_detCbA9"><spring:message code="msg_0729"/></label>
	                    <input
	                      type="checkbox"
	                      id="atm_bank_detCbA10"
	                      data-type="14" checked
	                    /><label for="atm_bank_detCbA10"><spring:message code="msg_0730"/></label>
	                    <input
	                      type="checkbox"
	                      id="atm_bank_detCbA10_48"
	                      data-type="48" checked
	                    /><label for="atm_bank_detCbA10_48"><spring:message code="msg_0754"/></label>
	                    <input
	                      type="checkbox"
	                      id="atm_bank_detCbA11"
	                      data-type="15" checked
	                    /><label for="atm_bank_detCbA11"><spring:message code="msg_0731"/></label>
	                    <input
	                      type="checkbox"
	                      id="atm_bank_detCbA12"
	                      data-type="16" checked
	                    /><label for="atm_bank_detCbA12"><spring:message code="msg_0732"/></label>
	                    <input
	                      type="checkbox"
	                      id="atm_bank_detCbA13"
	                      data-type="91" checked
	                    /><label for="atm_bank_detCbA13"><spring:message code="msg_0733"/></label>
	                  </td>
	                  <td id="atm_bank_inDetTbl_td2" class="trade_type_selector">
	                    <input type="checkbox" data-type="" id="atm_bank_detCbB0" class="atm_bank_detCbAll" checked/><label
	                      for="atm_bank_detCbB0"
	                      ><spring:message code="msg_0161"/></label
	                    >
	                    <input
	                      type="checkbox"
	                      id="atm_bank_detCbB1"
	                      data-type="43" checked
	                    /><label for="atm_bank_detCbB1"><spring:message code="msg_0722"/></label>
	                    <input
	                      type="checkbox"
	                      id="atm_bank_detCbB2"
	                      data-type="44" checked
	                    /><label for="atm_bank_detCbB2"><spring:message code="msg_0723"/></label>
	                    <input
	                      type="checkbox"
	                      id="atm_bank_detCbB3"
	                      data-type="2" checked
	                    /><label for="atm_bank_detCbB3"><spring:message code="msg_0728"/></label>
	                    <input
	                      type="checkbox"
	                      id="atm_bank_detCbB4"
	                      data-type="4" checked
	                    /><label for="atm_bank_detCbB4"><spring:message code="msg_0729"/></label>
	                    <input
	                      type="checkbox"
	                      id="atm_bank_detCbB5"
	                      data-type="1" checked
	                    /><label for="atm_bank_detCbB5"><spring:message code="msg_0245"/></label>
	                  </td>
	                  <td id="atm_bank_inDetTbl_td3" class="trade_type_selector" >
	                    <input type="checkbox" id="atm_bank_detCbC0" class="atm_bank_detCbAll" data-type="" checked/><label
	                      for="atm_bank_detCbC0"
	                      ><spring:message code="msg_0161"/></label
	                    >
	                    <input
	                      type="checkbox"
	                      id="atm_bank_detCbC1"
	                      data-type="41" checked
	                    /><label for="atm_bank_detCbC1"><spring:message code="msg_0660"/></label>
	                    <!-- <input
	                      type="checkbox"
	                      id="atm_bank_detCbC2"
	                      data-type=""
	                    /><label for="atm_bank_detCbC2">임시</label> -->
	                  </td>
	                </tr>
	              </table>
	            </div>
	            <table class="atm_bank_detTbl" style="border-bottom: 1px solid #ddd;">
	                <tr>
	                  <td><spring:message code="msg_0742"/></td>
	                  <td id="rb2">
	                    <input
	                      class="atm_bank_rb2All"
	                      type="radio"
	                      name="atm_bank_detRb2"
	                      id="atm_bank_rb2A"
	                      checked=""
	                    /><label for="atm_bank_rb2A"> <spring:message code="msg_0743"/></label>
	                    <input
	                      class="atm_bank_rb2All"
	                      type="radio"
	                      name="atm_bank_detRb2"
	                      id="atm_bank_rb2B"
	                    /><label for="atm_bank_rb2B"> <spring:message code="msg_0744"/></label>
	                    &nbsp;<select class="selectBox">
	                      <%
	                        SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy.MM.dd", Locale.KOREA );
	                        Date today = new Date ();
	                        
	                        for(int i = 0; i < 7; i++) {
	                        	Date tt = new Date();
	                        	tt.setTime(today.getTime() -  ( (long) 1000 * 60 * 60 * 24 * i) );
	                        	String dTime = formatter.format ( tt );
	
	                        	out.println("<option class='selectOpt' value='"+ i +"'>" + dTime + "</option>");
	                        }
	                      %>
	                    </select>
	                  </td>
	                </tr>
	              </table>
	              <div class="atm_bank_detailInDiv">
	                <span><spring:message code="msg_0746"/></span>
	              </div>
	
	              <div class="atm_bank_recent">
	                <!-- <p class="atm_bank_inTitM">최근 거래</p> -->
	                <div class="atm_bank_recentDiv">
	                  <table class="atm_bank_reTbl">
	                    <thead>
	                        <tr class="atm_bank_reTblTit">
	                        <td><spring:message code="msg_0748"/></td>
	                        <td><spring:message code="msg_0749"/></td>
	                        <td><spring:message code="msg_0750"/></td>
	                        <td><spring:message code="msg_0751"/></td>
	                        </tr>
	                    </thead>
	                    <tbody>
	                    </tbody>
	                  </table>
	                  <div class="atm_bank_recentDiv2" data-cur-page="1">
	                    <p><spring:message code="msg_0752"/></p>
	                  </div>
	                </div>
	              </div>
	            </div>
	          </div>
	        </form>
	        <!--wrapper end -->
	      </div>
      </div>
    </div>
    <form name="frm_bank" method="post"></form>
    <!-- 나중에 아래 있는 js pub으로 이전 -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0/dist/Chart.min.js"></script>
    <script src="/Common/js/axios.min.js"></script>
    <script src="/Common/bank/index.js?ver=4.4"></script><!-- es5/ -->
    <script src="/Common/bank/es5/chart.js?ver=1.0"></script><!-- es5/ -->
</body>