<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/Common/include/headerAdmin.jsp"%>
<head>
<link rel="stylesheet" type="text/css"
	href="/pub/admin/roulletteStatistics.css">
<link rel="stylesheet" href="/pub/admin/billboard.css">
<link rel="stylesheet" href="/pub/admin/insight.min.css">
</head>
<body>
	<div id="wrapper">
		<div id="header_wrapper"></div>
		<%@ include file="/Common/include/menuAdmin.jsp"%>
		<div id="roullette_event">
			<h2>&#60;룰렛 이벤트 통계 - 누계&#62;</h2>
			<p>누계</p>
			<table class="all_statistics">
				<tr>
					<td>총 발행 이용권: <span><fmt:formatNumber value="${total}" pattern="#,###.##"/></span>장
					</td>
					<td>총 지급 당첨 알: <span><fmt:formatNumber value="${totalSum}" pattern="#,###.##"/></span>알
					</td>
					<td>총 이용권 획득 회원: <span><fmt:formatNumber value="${userCount}" pattern="#,###.##"/></span>명
					</td>
					<td>소멸 이용권: <span><fmt:formatNumber value="${disCount}" pattern="#,###.##"/></span>장
					</td>
				</tr>
			</table>
			<table class="all_rank">
				<tr>
					<th>등급</th>
					<th>알천사</th>
					<th>나비천사</th>
					<th>미소천사</th>
					<th>열혈천사</th>
					<th>황금천사</th>
					<th>수호천사</th>
					<th>빛의천사</th>
					<th>천사장</th>
					<th>대천사</th>
					<th>대천사장</th>
					<th>알통폐인</th>
				</tr>
				<tr>
					<th>이용권&#40;장&#41;</th>
					<td><fmt:formatNumber value="${LVcountS.Lv1}" pattern="#,###.##"/></td>
					<td><fmt:formatNumber value="${LVcountS.Lv2}" pattern="#,###.##"/></td>
					<td><fmt:formatNumber value="${LVcountS.Lv3}" pattern="#,###.##"/></td>
					<td><fmt:formatNumber value="${LVcountS.Lv4}" pattern="#,###.##"/></td>
					<td><fmt:formatNumber value="${LVcountS.Lv5}" pattern="#,###.##"/></td>
					<td><fmt:formatNumber value="${LVcountS.Lv6}" pattern="#,###.##"/></td>
					<td><fmt:formatNumber value="${LVcountS.Lv7}" pattern="#,###.##"/></td>
					<td><fmt:formatNumber value="${LVcountS.Lv8}" pattern="#,###.##"/></td>
					<td><fmt:formatNumber value="${LVcountS.Lv9}" pattern="#,###.##"/></td>
					<td><fmt:formatNumber value="${LVcountS.Lv10}" pattern="#,###.##"/></td>
					<td><fmt:formatNumber value="${LVcountS.Lv11}" pattern="#,###.##"/></td>
				</tr>
				<tr>
					<th>당첨알&#40;알&#41;</th>
					<td><fmt:formatNumber value="${LVcountS2.Lv1}" pattern="#,###.##"/></td>
					<td><fmt:formatNumber value="${LVcountS2.Lv2}" pattern="#,###.##"/></td>
					<td><fmt:formatNumber value="${LVcountS2.Lv3}" pattern="#,###.##"/></td>
					<td><fmt:formatNumber value="${LVcountS2.Lv4}" pattern="#,###.##"/></td>
					<td><fmt:formatNumber value="${LVcountS2.Lv5}" pattern="#,###.##"/></td>
					<td><fmt:formatNumber value="${LVcountS2.Lv6}" pattern="#,###.##"/></td>
					<td><fmt:formatNumber value="${LVcountS2.Lv7}" pattern="#,###.##"/></td>
					<td><fmt:formatNumber value="${LVcountS2.Lv8}" pattern="#,###.##"/></td>
					<td><fmt:formatNumber value="${LVcountS2.Lv9}" pattern="#,###.##"/></td>
					<td><fmt:formatNumber value="${LVcountS2.Lv10}" pattern="#,###.##"/></td>
					<td><fmt:formatNumber value="${LVcountS2.Lv11}" pattern="#,###.##"/></td>
				</tr>
				<tr>
					<th>인원수&#40;명&#41;</th>
					<td>${LVcountS3.Lv1}</td>
					<td>${LVcountS3.Lv2}</td>
					<td>${LVcountS3.Lv3}</td>
					<td>${LVcountS3.Lv4}</td>
					<td>${LVcountS3.Lv5}</td>
					<td>${LVcountS3.Lv6}</td>
					<td>${LVcountS3.Lv7}</td>
					<td>${LVcountS3.Lv8}</td>
					<td>${LVcountS3.Lv9}</td>
					<td>${LVcountS3.Lv10}</td>
					<td>${LVcountS3.Lv11}</td>
				</tr>
			</table>
			<div>
				<table>
					<tr>
						<th colspan="5">요건 별 발행 이용권 &#40;누계&#41;</th>
					</tr>
					<tr>
						<td>질문 등록</td>
						<td>답변 등록</td>
						<td>댓글 등록</td>
						<td>훈훈알 증정</td>
						<td>답변 평가</td>
					</tr>
					<tr>
						<td><fmt:formatNumber value="${part1.que}" pattern="#,###.##"/>장</td>
						<td><fmt:formatNumber value="${part1.ans}" pattern="#,###.##"/>장</td>
						<td><fmt:formatNumber value="${part1.repl}" pattern="#,###.##"/>장</td>
						<td><fmt:formatNumber value="${part1.hun}" pattern="#,###.##"/>장</td>
						<td><fmt:formatNumber value="${part1.esti}" pattern="#,###.##"/>장</td>
					</tr>
					<tr>
						<td><fmt:formatNumber value="${ 100 * part1.que / (part1.que + part1.ans + part1.repl + part1.hun + part1.esti)}" pattern="#,###.##"/>&#37;</td>
						<td><fmt:formatNumber value="${ 100 * part1.ans / (part1.que + part1.ans + part1.repl + part1.hun + part1.esti)}" pattern="#,###.##"/>&#37;</td>
						<td><fmt:formatNumber value="${ 100 * part1.repl / (part1.que + part1.ans + part1.repl + part1.hun + part1.esti)}" pattern="#,###.##"/>&#37;</td>
						<td><fmt:formatNumber value="${ 100 * part1.hun / (part1.que + part1.ans + part1.repl + part1.hun + part1.esti)}" pattern="#,###.##"/>&#37;</td>
						<td><fmt:formatNumber value="${ 100 * part1.esti / (part1.que + part1.ans + part1.repl + part1.hun + part1.esti)}" pattern="#,###.##"/>&#37;</td>
					</tr>
				</table>
				<table>
					<tr>
						<th colspan="6">상금 별 발행 이용권 &#40;누계&#41;</th>
					</tr>
					<tr>
						<td>1,000알</td>
						<td>2,000알</td>
						<td>3,000알</td>
						<td>5,000알</td>
						<td>10,000알</td>
						<td>30,000알</td>
					</tr>
					<tr>
						<td><fmt:formatNumber value="${part2.m1000}" pattern="#,###.##"/>장</td>
						<td><fmt:formatNumber value="${part2.m2000}" pattern="#,###.##"/>장</td>
						<td><fmt:formatNumber value="${part2.m3000}" pattern="#,###.##"/>장</td>
						<td><fmt:formatNumber value="${part2.m5000}" pattern="#,###.##"/>장</td>
						<td><fmt:formatNumber value="${part2.m10000}" pattern="#,###.##"/>장</td>
						<td><fmt:formatNumber value="${part2.m30000}" pattern="#,###.##"/>장</td>
					</tr>
					<tr>
						<td><fmt:formatNumber value="${ 100 * part2.m1000 / (part2.m1000 + part2.m2000 + part2.m3000 + part2.m5000 + part2.m10000 + part2.m30000)}" pattern="#,###.##"/>&#37;</td>
						<td><fmt:formatNumber value="${ 100 * part2.m2000 / (part2.m1000 + part2.m2000 + part2.m3000 + part2.m5000 + part2.m10000 + part2.m30000)}" pattern="#,###.##"/>&#37;</td>
						<td><fmt:formatNumber value="${ 100 * part2.m3000 / (part2.m1000 + part2.m2000 + part2.m3000 + part2.m5000 + part2.m10000 + part2.m30000)}" pattern="#,###.##"/>&#37;</td>
						<td><fmt:formatNumber value="${ 100 * part2.m5000 / (part2.m1000 + part2.m2000 + part2.m3000 + part2.m5000 + part2.m10000 + part2.m30000)}" pattern="#,###.##"/>&#37;</td>
						<td><fmt:formatNumber value="${ 100 * part2.m10000 / (part2.m1000 + part2.m2000 + part2.m3000 + part2.m5000 + part2.m10000 + part2.m30000)}" pattern="#,###.##"/>&#37;</td>
						<td><fmt:formatNumber value="${ 100 * part2.m30000 / (part2.m1000 + part2.m2000 + part2.m3000 + part2.m5000 + part2.m10000 + part2.m30000)}" pattern="#,###.##"/>&#37;</td>
					</tr>
				</table>

			</div>
			<div id="chart"></div>
		</div>

		<div id="roullette_event" class="roullette_list">
			<br>
			<div class="roullette_search">
			<script>
			$(document).on('click', '#topRankbtn', function() {
				console.log('topRank : '+$('#topRank').val());
				if($('#topRank').val() == ''){
					return 0;
				}
				$.ajax({
					url: '/aadmin/quiz/getTopStatistics',
					data: {'topRank': $('#topRank').val()},
					type: 'post',
					success: function(data) {
						if(data.length==0){
							return 0;
						}
						var src ='<tr>'+ $("#tableTop tr:nth-child(1)").html()+ "</tr><tr>" + $("#tableTop tr:nth-child(2)").html() + '</tr>';
						
						for( i=0 ; i < data.length ; i++ ){
							src = src + '<tr><td>'+data[i].NickName+'</td>'+ '<td>'+data[i].Lv+'</td>'
							+ '<td>'+data[i].Ticket.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td>'+ '<td>'+data[i].al.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td>'
							+ '<td>'+data[i].que.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td>'+ '<td>'+data[i].ans.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td>'
							+ '<td>'+data[i].repl.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td>'+ '<td>'+data[i].hun.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td>'
							+ '<td>'+data[i].esti.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td>'+ '<td>'+data[i].Almoney.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td></tr>';
						}

						$("#tableTop tbody").html(src);
					},
					error: function() {
						alert('오류발생, 다시 시도해주세요');
					}
				});
			});
			
			</script>
				<span>총 당첨알 상위</span> <input id='topRank' name='topRank' type="number"> <span>위까지의
					리스트</span> <input id='topRankbtn' type="Button" value="출력">
			</div>
			<table id='tableTop'>
				<tr>
					<th rowspan="2">닉네임</th>
					<th rowspan="2">회원등급</th>
					<th rowspan="2">이용권&#40;장&#41;</th>
					<th rowspan="2">당첨알&#40;알&#41;</th>
					<th colspan="5">요건 당 이용권 수</th>
					<th rowspan="2">현 보유알&#40;알&#41;</th>
				</tr>
				<tr>
					<th>질문 등록</th>
					<th>답변 등록</th>
					<th>댓글 등록</th>
					<th>훈훈알 증정</th>
					<th>답변 평가</th>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</table>
		</div>
		<div id="roullette_event" class="date">
			<h2>&#60;룰렛 이벤트 통계 - 특정기간&#62;</h2>

			<p>특정기간</p>
			<script>
				$(document).on('click', '#dateBtn', function() {
					
					$.ajax({
						url: '/aadmin/quiz/getDateStatistics',
						data: {
							'datePre': $('#datePre').val(),
							'datePOST': $('#datePOST').val(),
						},
						type: 'post',
						success: function(data) {
							
							if(data.length==0){
								return 0;
							}
							var src ='<tr>'+ $("#Tabledate tr:nth-child(1)").html()+ "</tr>";
							const th = ['이용권(장)','당첨알(알)','인원수(명)'];
							for( i=0 ; i < 3 ; i++ ){
								src = src + '<tr> <th> '+th[i]+' </th> <td>'+data[i].Lv1.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td>'+ '<td>'+data[i].Lv2.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td>'
								+ '<td>'+data[i].Lv3.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td>'+ '<td>'+data[i].Lv4.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td>'
								+ '<td>'+data[i].Lv5.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td>'+ '<td>'+data[i].Lv6.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td>'
								+ '<td>'+data[i].Lv7.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td>'+ '<td>'+data[i].Lv8.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td>'
								+ '<td>'+data[i].Lv9.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td>'+ '<td>'+data[i].Lv10.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td>'+ '<td>'+data[i].Lv11.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td></tr>';
							}

							$("#Tabledate tbody").html(src);
							
							src ='<tr>'+ $("#Tabledate2 tr:nth-child(1)").html()+ "</tr>"+ '<tr>'+ $("#Tabledate2 tr:nth-child(2)").html()+ "</tr>";
							for( i=3 ; i < 4 ; i++ ){
								src = src + '<tr><td>'+data[i].que.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'장</td>'+ '<td>'+data[i].ans.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'장</td>'
								+ '<td>'+data[i].repl.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'장</td>'+ '<td>'+data[i].hun.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'장</td>'
								+ '<td>'+data[i].hun.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'장</td>'+'</td></tr>';
								src = src + '<tr><td>'+((data[i].que/(data[i].que+data[i].ans+data[i].esti+data[i].repl+data[i].hun))*100).toFixed(2)+'%</td>'
								+ '<td>'+((data[i].ans/(data[i].que+data[i].ans+data[i].esti+data[i].repl+data[i].hun))*100).toFixed(2)+'%</td>'
								+ '<td>'+((data[i].repl/(data[i].que+data[i].ans+data[i].esti+data[i].repl+data[i].hun))*100).toFixed(2)+'%</td>'
								+ '<td>'+((data[i].hun/(data[i].que+data[i].ans+data[i].esti+data[i].repl+data[i].hun))*100).toFixed(2)+'%</td>'
								+ '<td>'+((data[i].esti/(data[i].que+data[i].ans+data[i].esti+data[i].repl+data[i].hun))*100).toFixed(2)+'%</td>'+'</tr>';
								
							}
							$("#Tabledate2 tbody").html(src);
							
							src ='<tr>'+ $("#Tabledate3 tr:nth-child(1)").html()+ "</tr>"+'<tr>'+ $("#Tabledate3 tr:nth-child(2)").html()+ "</tr>";
							for( i=4 ; i < 5 ; i++ ){
								src = src + '<tr><td>'+data[i].m1000.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'장</td>'+ '<td>'+data[i].m2000.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'장</td>'
								+ '<td>'+data[i].m3000.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'장</td>'+ '<td>'+data[i].m5000.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'장</td>'+ '<td>'+data[i].m10000.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'장</td>'
								+ '<td>'+data[i].m30000.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'장</td>'+'</td></tr>';

								src = src + '<tr><td>'+((data[i].m1000/(data[i].m1000+data[i].m2000+data[i].m3000+data[i].m5000+data[i].m10000+data[i].m30000))*100).toFixed(2)+'%</td>'
								+ '<td>'+((data[i].m2000/(data[i].m1000+data[i].m2000+data[i].m3000+data[i].m5000+data[i].m10000+data[i].m30000))*100).toFixed(2)+'%</td>'
								+ '<td>'+((data[i].m3000/(data[i].m1000+data[i].m2000+data[i].m3000+data[i].m5000+data[i].m10000+data[i].m30000))*100).toFixed(2)+'%</td>'
								+ '<td>'+((data[i].m5000/(data[i].m1000+data[i].m2000+data[i].m3000+data[i].m5000+data[i].m10000+data[i].m30000))*100).toFixed(2)+'%</td>'
								+ '<td>'+((data[i].m10000/(data[i].m1000+data[i].m2000+data[i].m3000+data[i].m5000+data[i].m10000+data[i].m30000))*100).toFixed(2)+'%</td>'
								+ '<td>'+((data[i].m30000/(data[i].m1000+data[i].m2000+data[i].m3000+data[i].m5000+data[i].m10000+data[i].m30000))*100).toFixed(2)+'%</td>'
								+ '</tr>';
								
							}
							$("#Tabledate3 tbody").html(src);
							
							$("#dateTotal").html(data[5].total.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
							$("#dateAl").html(data[5].totalSum.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
							$("#dateUser").html(data[5].userCount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
							$("#dateDel").html(data[5].disCount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));

						},
						error: function() {
							alert('오류발생, 다시 시도해주세요');
						}
					});
				});

				$(function(){
					$.datepicker.setDefaults({
		                dateFormat: 'yy-mm-dd' //Input Display Format 변경
		                ,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
		                ,showMonthAfterYear:true //년도 먼저 나오고, 뒤에 월 표시
		                ,changeYear: true //콤보박스에서 년 선택 가능
		                ,changeMonth: true //콤보박스에서 월 선택 가능                
		                ,showOn: "both" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시 ^ both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시  
		                ,buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif" //버튼 이미지 경로
		                ,buttonImageOnly: true //기본 버튼의 회색 부분을 없애고, 이미지만 보이게 함
		                ,buttonText: "선택" //버튼에 마우스 갖다 댔을 때 표시되는 텍스트                
		                ,yearSuffix: "년" //달력의 년도 부분 뒤에 붙는 텍스트
		                ,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'] //달력의 월 부분 텍스트
		                ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
		                ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
		                ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
		                ,minDate: "-5Y" //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
		                ,maxDate: "+1M" //최대 선택일자(+1D:하루후, -1M:한달후, -1Y:일년후)                    
		            });
					//input을 datepicker로 선언
		            $("#datePre").datepicker();                    
		            $("#datePOST").datepicker();
					
					
					$('#datePre').datepicker( 'setDate' , '-1D' );
					$('#datePOST').datepicker( 'setDate' , '-1D' );
					$('#dateBtn').trigger("click");
					
					console.log('datePre : ' + $('#datePre').val());
					console.log('datePOST : ' + $('#datePOST').val());
					
				});
				
			</script>
			
			<div class="date_select">
				기간 <input id='datePre' type="text"> ~ <input id='datePOST' type="text">
				<input id='dateBtn'
					type="button" value="출력">
			</div>
			<table class="all_statistics">
				<tr>
					<td>총 발행 이용권: <span id='dateTotal'>0</span>장
					</td>
					<td>총 지급 당첨 알: <span id='dateAl'>0</span>알
					</td>
					<td>총 이용권 획득 회원: <span id='dateUser'>0</span>명
					</td>
					<td>소멸 이용권: <span id='dateDel'>0</span>장
					</td>
				</tr>
			</table>
			<table id='Tabledate' class="all_rank">
				<tr>
					<th>등급</th>
					<th>알천사</th>
					<th>나비천사</th>
					<th>미소천사</th>
					<th>열혈천사</th>
					<th>황금천사</th>
					<th>수호천사</th>
					<th>빛의천사</th>
					<th>천사장</th>
					<th>대천사</th>
					<th>대천사장</th>
					<th>알통폐인</th>
				</tr>
				<tr>
					<th>이용권&#40;장&#41;</th>
					<td>0</td>
					<td>0</td>
					<td>0</td>
					<td>0</td>
					<td>0</td>
					<td>0</td>
					<td>0</td>
					<td>0</td>
					<td>0</td>
					<td>0</td>
					<td>0</td>
				</tr>
				<tr>
					<th>당첨알&#40;알&#41;</th>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<th>인원수&#40;명&#41;</th>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</table>
			<div>
				<table id='Tabledate2'>
					<tr>
						<th colspan="5">요건 별 발행 이용권 &#40;누계&#41;</th>
					</tr>
					<tr>
						<td>질문 등록</td>
						<td>답변 등록</td>
						<td>댓글 등록</td>
						<td>훈훈알 증정</td>
						<td>답변 평가</td>
					</tr>
					<tr>
						<td>장</td>
						<td>장</td>
						<td>장</td>
						<td>장</td>
						<td>장</td>
					</tr>
					<tr>
						<td>&#37;</td>
						<td>&#37;</td>
						<td>&#37;</td>
						<td>&#37;</td>
						<td>&#37;</td>
					</tr>
				</table>
				<table id='Tabledate3'>
					<tr>
						<th colspan="6">상금 별 발행 이용권 &#40;누계&#41;</th>
					</tr>
					<tr>
						<td>1,000알</td>
						<td>2,000알</td>
						<td>3,000알</td>
						<td>5,000알</td>
						<td>10,000알</td>
						<td>30,000알</td>
					</tr>
					<tr>
						<td>장</td>
						<td>장</td>
						<td>장</td>
						<td>장</td>
						<td>장</td>
						<td>장</td>
					</tr>
					<tr>
						<td>&#37;</td>
						<td>&#37;</td>
						<td>&#37;</td>
						<td>&#37;</td>
						<td>&#37;</td>
						<td>&#37;</td>
					</tr>
				</table>
				<div id="chart2"></div>
			</div>
		</div>
		<div id="roullette_event" class="roullette_list date">
			<br>
			<div class="roullette_search">
			<script>
			$(document).on('click', '#DateTopBtn', function() {
				console.log('DateTop : '+$('#DateTop').val());
				if($('#DateTop').val() == ''){
					return 0;
				}
				$.ajax({
					url: '/aadmin/quiz/getDateTopStatistics',
					data: {'DateTop': $('#DateTop').val(),
						'datePre': $('#datePre').val(),
						'datePOST': $('#datePOST').val(),	
					},
					type: 'post',
					success: function(data) {
						if(data.length==0){
							return 0;
						}
						var src ='<tr>'+ $("#tableDateTop tr:nth-child(1)").html()+ "</tr><tr>" + $("#tableDateTop tr:nth-child(2)").html() + '</tr>';
						
						for( i=0 ; i < data.length ; i++ ){
							src = src + '<tr><td>'+data[i].NickName+'</td>'+ '<td>'+data[i].Lv.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td>'
							+ '<td>'+data[i].Ticket.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td>'+ '<td>'+data[i].al.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td>'
							+ '<td>'+data[i].que.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td>'+ '<td>'+data[i].ans.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td>'
							+ '<td>'+data[i].repl.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td>'+ '<td>'+data[i].hun.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td>'
							+ '<td>'+data[i].esti.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td>'+ '<td>'+data[i].Almoney.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</td></tr>';
						}

						$("#tableDateTop tbody").html(src);
					},
					error: function() {
						alert('오류발생, 다시 시도해주세요');
					}
				});
			});
			
			</script>
				<span>총 당첨알 상위</span> <input id='DateTop' type="number"> <span>위까지의
					리스트</span> <input id='DateTopBtn' type="button" value="출력">
			</div>
			<table id="tableDateTop">
				<tr>
					<th rowspan="2">닉네임</th>
					<th rowspan="2">회원등급</th>
					<th rowspan="2">이용권&#40;장&#41;</th>
					<th rowspan="2">당첨알&#40;알&#41;</th>
					<th colspan="5">요건 당 이용권 수</th>
					<th rowspan="2">현 보유알&#40;알&#41;</th>
				</tr>
				<tr>
					<th>질문 등록</th>
					<th>답변 등록</th>
					<th>댓글 등록</th>
					<th>훈훈알 증정</th>
					<th>답변 평가</th>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</table>
		</div>

		<script src="https://d3js.org/d3.v5.min.js"></script>
		<script src="/pub/admin/billboard.js"></script>
		<script>
			$(document).ready(
				
				function() { // 차트 그리기 작업 전임
					/*
					$.ajax({
						url: '/aadmin/quiz/getChartStatistics',
						data: {	},
						type: 'post',
						success: function(data) {
							if(data.length==0){
								return 0;
							}

							return 0;
						},
						error: function() {
							alert('오류발생, 다시 시도해주세요');
						}
					});
					*/
					var chart = bb.generate({
						bindto : "#chart",
						data : {
							type : "spline",
							x: "날짜",
							columns : [
								["날짜",  "2021-02-19", "2021-02-20", "2021-02-21", "2021-02-22", "2013-01-05", "2013-01-06"],
								[ "이용권", 30, 20, 10, 17,150, 2500 ],
								[ "당첨알", 13000, 10000, 14000, 35000, 110000, 50000 ], 
								[ "인원수", 130, 100, 140, 35,110, 50 ],
								],
							axes: {
								이용권: "y2",
								당첨알: "y",
								인원수: "y2"
							}
						},
						axis : {
							y : {
							},
							y2 : {
								show:true,
							},
							x: {
							      type: "timeseries",
							      tick: {
							        count: 4,
							        format: "%Y-%m-%d"
							      }
							    }
						}
					});
				});
			
			$(document).ready(
					function() {
						var chart = bb.generate({
							bindto : "#chart2",
							data : {
								type : "spline",
								columns : [
									[ "이용권", 30, 20, 10, 17,150, 2500 ],
									[ "당첨알", 13000, 10000, 14000, 35000,110000, 50000 ], 
									[ "인원수", 130, 100, 140, 35,110, 50 ],
									],
								axes: {
									이용권: "y2",
									당첨알: "y",
									인원수: "y2"
								}
							},
							axis : {
								y : {
								},
								y2 : {
									show:true,
								},
								
							}
						});
					});
			
			
		</script>
	</div>
</body>