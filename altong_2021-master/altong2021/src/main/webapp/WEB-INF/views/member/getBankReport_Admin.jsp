<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<c:if test="${sumEtc != '0.0000'}">
	<div class='atm_bankgraph_el'><p class='atm_bank_c6'>${sumJoin} 알<br><span class='atm_bank_color_gray2'>가입금</span></p>
	<div class='atm_bank_graphwrapper'><div class='atm_bank_frame'><div class='atm_bank_gaze_min' style='width:${rateJoin}%;'>&nbsp;
	</div></div></div></div>
</c:if>
<c:if test="${sumAnswer != '0.0000'}">
	<div class='atm_bankgraph_el'><p class='atm_bank_c6' onClick="javascript:goSubmit('frm_sch','/member/myBankList?FlagBank=I&FlagType=Answer','ifrm');">${sumAnswer} 알<br><span class='atm_bank_color_gray2'>답변채택</span></p>
	<div class='atm_bank_graphwrapper'><div class='atm_bank_frame'><div class='atm_bank_gaze_min' style='width:${rateAnswer}%;'>&nbsp;
	</div></div></div></div>
</c:if>
<c:if test="${sumViewA != '0.0000'}">
	<div class='atm_bankgraph_el'><p class='atm_bank_c6' onClick="javascript:goSubmit('frm_sch','/member/myBankList?FlagBank=I&FlagType=ViewA','ifrm');">${sumViewA} 알<br><span class='atm_bank_color_gray2'>답변열람</span></p>
 	<div class='atm_bank_graphwrapper'><div class='atm_bank_frame'><div class='atm_bank_gaze_min' style='width:${rateViewA}%;'>&nbsp;
 	</div></div></div></div>
</c:if>
<c:if test="${sumViewQ != '0.0000'}">
	<div class='atm_bankgraph_el'><p class='atm_bank_c6' onClick="javascript:goSubmit('frm_sch','/member/myBankList?FlagBank=I&FlagType=ViewQ','ifrm');">${sumViewQ} 알<br><span class='atm_bank_color_gray2'>질문열람</span></p>
 	<div class='atm_bank_graphwrapper'><div class='atm_bank_frame'><div class='atm_bank_gaze_min' style='width:${rateViewQ}%;'>&nbsp;
 	</div></div></div></div>
</c:if>
<c:if test="${sumViewRQ != '0.0000'}">
	<div class='atm_bankgraph_el'><p class='atm_bank_c6' onClick="javascript:goSubmit('frm_sch','/member/myBankList?FlagBank=E&FlagType=View','ifrm');">${sumViewRQ} 알<br><span class='atm_bank_color_gray2'>추천인(질문)</span></p>
 	<div class='atm_bank_graphwrapper'><div class='atm_bank_frame'><div class='atm_bank_gaze_min' style='width:${rateViewRQ}%;'>&nbsp;
 	</div></div></div></div>
</c:if>
<c:if test="${sumViewRA != '0.0000'}">
	<div class='atm_bankgraph_el'><p class='atm_bank_c6' onClick="javascript:goSubmit('frm_sch','/member/myBankList?FlagBank=E&FlagType=View','ifrm');">${sumViewRA} 알<br><span class='atm_bank_color_gray2'>추천인(답변)</span></p>
 	<div class='atm_bank_graphwrapper'><div class='atm_bank_frame'><div class='atm_bank_gaze_min' style='width:${rateViewRA}%;'>&nbsp;
 	</div></div></div></div>
</c:if>
<c:if test="${sumEsti != '0.0000'}">
	<div class='atm_bankgraph_el'><p class='atm_bank_c6' onClick="javascript:goSubmit('frm_sch','/member/myBankList?FlagBank=E&FlagType=View','ifrm');">${sumEsti} 알<br><span class='atm_bank_color_gray2'>답변평가</span></p>
 	<div class='atm_bank_graphwrapper'><div class='atm_bank_frame'><div class='atm_bank_gaze_min' style='width:${rateEsti}%;'>&nbsp;
 	</div></div></div></div>
</c:if>
<c:if test="${sumRefund != '0.0000'}">
	<div class='atm_bankgraph_el'><p class='atm_bank_c6' onClick="javascript:goSubmit('frm_sch','/member/myBankList?FlagBank=E&FlagType=View','ifrm');">${sumRefund} 알<br><span class='atm_bank_color_gray2'>질문 환급</span></p>
 	</div></div></div></div>
</c:if>
<c:if test="${sumEvent != '0.0000'}">
	<div class='atm_bankgraph_el'><p class='atm_bank_c6' onClick="javascript:goSubmit('frm_sch','/member/myBankList?FlagBank=E&FlagType=View','ifrm');">${sumEvent} 알<br><span class='atm_bank_color_gray2'>이벤트</span></p>
 	<div class='atm_bank_graphwrapper'><div class='atm_bank_frame'><div class='atm_bank_gaze_min' style='width:${rateEvent}%;'>&nbsp;
 	</div></div></div></div>
</c:if>
<c:if test="${sumEtc != '0.0000'}">
	<div class='atm_bankgraph_el'><p class='atm_bank_c6' onClick="javascript:goSubmit('frm_sch','/member/myBankList?FlagBank=E&FlagType=View','ifrm');">${sumEtc} 알<br><span class='atm_bank_color_gray2'>기타</span></p>
 	<div class='atm_bank_graphwrapper'><div class='atm_bank_frame'><div class='atm_bank_gaze_min' style='width:${rateEtc}%;'>&nbsp;
 	</div></div></div></div>
</c:if>
<c:if test="${sumWithdraw != '0.0000'}">
	<div class='atm_bankgraph_el'><p class='atm_bank_c6' onClick="javascript:goSubmit('frm_sch','/member/myBankList?FlagBank=E&FlagType=Question','ifrm');">${sumWithdraw} 알<br><span class='atm_bank_color_gray2'>출금</span></p>
 	<div class='atm_bank_graphwrapper'><div class='atm_bank_frame'><div class='atm_bank_gaze_org' style='width:${rateWithdraw}%;'>&nbsp;
 	</div></div></div></div>
</c:if>
<c:if test="${sumQuestion != '0.0000'}">
	<div class='atm_bankgraph_el'><p class='atm_bank_c6' onClick="javascript:goSubmit('frm_sch','/member/myBankList?FlagBank=E&FlagType=Question','ifrm');">${sumQuestion} 알<br><span class='atm_bank_color_gray2'>질문비용</span></p>
 	<div class='atm_bank_graphwrapper'><div class='atm_bank_frame'><div class='atm_bank_gaze_org' style='width:${rateQuestion}%;'>&nbsp;
 	</div></div></div></div>
</c:if>
<c:if test="${sumView != '0.0000'}">
	<div class='atm_bankgraph_el'><p class='atm_bank_c6' onClick="javascript:goSubmit('frm_sch','/member/myBankList?FlagBank=E&FlagType=View','ifrm');">${sumView} 알<br><span class='atm_bank_color_gray2'>열람비용</span></p>
 	<div class='atm_bank_graphwrapper'><div class='atm_bank_frame'><div class='atm_bank_gaze_org' style='width:${rateView}%;'>&nbsp;
 	</div></div></div></div>
</c:if>