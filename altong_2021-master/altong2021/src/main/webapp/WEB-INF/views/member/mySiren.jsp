<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<!-- 상위 헤드 -->
<link rel="stylesheet" href="/pub/member/myQuestion/myQuestion.css?ver=1.1">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=1.4">
</head>
<body>
<%@ include file="/pub/menu/topMenu.jsp"%>
<div id="atm_mySiren_wrapper">

	<div class="center">
	<!--center start -->
		<div class="atm_mycatebar1">
			<div class="atm_mycatebar1_pc">
				<p class="atm_mycatebar_c1"> 신고 / 벌점 내역 </p>
			</div>
		</div>
		<div class="content_wrap">
			<div class="siren_count">
				<h3>[ 신고 ]</h3>
				<table>
					<tbody>
					<tr>
						<th>구분</th>
						<th>처리 완료</th>
						<th>미처리</th>
						<th>무고</th>
					</tr>
					<tr>
						<td>내가<br class="mobile_br"> 한 신고</td>
						<td><span>${cntMy}</span>건</td>
						<td><span>${cntMyIng}</span>건</td>
						<td>
						<span><c:choose><c:when test="${cntMyReject > 0}"><fmt:formatNumber value="${cntMyReject/cntMy * 100}" pattern="#,###.#" /></c:when><c:otherwise>0</c:otherwise></c:choose></span>%
						<br class="mobile_br"> (${cntMyReject}건)
						</td>
					</tr>
					<tr>
						<td>내가<br class="mobile_br"> 당한 신고</td>
						<td><span>${cntMe}</span>건</td>
						<td><span>${cntMeIng}</span>건</td>
						<td><span><c:choose><c:when test="${cntMeReject > 0}"><fmt:formatNumber value="${cntMeReject/cntMe * 100}" pattern="#,###.#" /></c:when><c:otherwise>0</c:otherwise></c:choose></span>%
						<br class="mobile_br"> (${cntMeReject}건)
						</td>
					</tr>
					</tbody>
				</table>
				<div class="guide">
					<p><strong>※ '무고'</strong>란 위험 또는 경고로 처리되지 않은 게시물, 즉 신고 사유에 해당하지 않는 게시물이 신고되었을 경우를 뜻합니다.</p>
				</div>				
			</div>
			
			<div class="siren_score">
				<h3>[ 벌점 ]</h3>
				<table>
					<tr>
						<th>구분</th>
						<th>위험</th>
						<th>경고</th>
						<th>벌점</th>
					</tr>
					<tr>
						<td>질문</td>
						<td><strong>${cntMeDanger_Qus}</strong>건</td>
						<td><strong>${cntMeWarning_Qus}</strong>건</td>
						<td><strong>${pntMe_Qus}</strong>점</td>
					</tr>
					<tr>
						<td>답변</td>
						<td><strong>${cntMeDanger_Ans}</strong>건</td>
						<td><strong>${cntMeWarning_Ans}</strong>건</td>
						<td><strong>${pntMe_Ans}</strong>점</td>
					</tr>
					<tr>
						<td>댓글</td>
						<td><strong>${cntMeDanger_Repl}</strong>건</td>
						<td><strong>${cntMeWarning_Repl}</strong>건</td>
						<td><strong>${pntMe_Repl}</strong>점</td>
					</tr>
					<tr>
						<td colspan="4"><strong>벌점 누계 : <span><fmt:formatNumber value="${pntMe_Qus + pntMe_Ans + pntMe_Repl}" pattern="#,###" /></span>점</strong></td>
					</tr>
				</table>

				<div class="guide">
					<p><strong>※ 벌점 부과 규정</strong></p>
					<ul>
						<li>단일 질문 또는 답변: '위험' 판정 시 10점, '경고' 판정 시 3점 부과</li>
						<li>단일 댓글: '위험' 판정 시 3점, '경고' 판정 시 1점 부과</li>
					</ul>
				</div>
			</div>
			
		</div><!--content_wrap end -->
		
	<!--center end -->
	</div>
</div><!-- atm_mySiren_wrapper end -->
<div id="top_btn">
    <a href="#">
        <span>
            <img src="/Common/images/top_btn_arrow.svg" alt="top_btn">
        </span>
    </a>
</div>
</body>
</html>