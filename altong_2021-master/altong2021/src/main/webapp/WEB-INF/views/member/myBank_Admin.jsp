<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<style type="text/css">

ul,li {list-style-type:none;}
img,p,li {border:0;margin:0;padding:0; vertical-align:middle;}

/*자산영역*/
.atm_bank_myaccount{ position:relative; padding:15px 0px; border-bottom:#bababa 0px solid; color:#5a5a5a; background-color:#f2f2f2; font-weight:bold;}
.atm_bank_myaccount2{position:relative; padding:5px 0px 5px; border-bottom:#bababa 0px solid; color:#5a5a5a; background-color:#ffffff; font-weight:bold;}
.atm_bank_line{ border-bottom:#d3d3d3 1px solid; margin:5px 17px 0 17px;; }
.atm_bank_color_gray{color:#d3d3d3; font-weight:normal;}
.atm_bank_color_gray2{color:#999999; font-weight:bold; font-size:12px;}

/*탭영역*/
.atm_ranka_tab1{  background-color:#545454; padding:0px 0px 0 0; text-align:center; position:relative; }
.atm_ranka_tabc1{   width:48%; font-size:12px; font-weight:bold; letter-spacing:-0.5px; padding:8px 0 ; color:#fff;    border:0;  position:absolute; left:2%; bottom:0px;  }
.atm_ranka_tabc2{   width:48%; font-size:12px; font-weight:bold; letter-spacing:-0.5px; padding:8px 0 ; color:#545454; border:0;  display:inline-block;  }
.atm_ranka_tabc3{   width:48%; font-size:12px; font-weight:bold; letter-spacing:-0.5px; padding:8px 0 ; color:#fff;    border:0;  position:absolute; right:2%; bottom:0px;  }
.atm_ranka_tabc1_on{width:100%; font-size:12px; font-weight:bold; letter-spacing:-0.5px; padding:8px 0; color:#ffde00; border:0; position:absolute; left:2%; bottom:0px;  }
.atm_ranka_tabc3_on{width:48%; font-size:12px; font-weight:bold; letter-spacing:-0.5px; padding:8px 0; color:#ffde00; border:0; position:absolute; right:2%; bottom:0px;  }

/*img*/

.atm_bankcal_btnx{position:absolute; right:4px; top:0px; }
.atm_directup_btnx{position:absolute; left:110px; top:-15px; }

/*p태그*/

.atm_bank_c1{ font-size:20px; font-weight:bold; letter-spacing:0px;}
.atm_bank_c2{ font-size:14px; font-weight:bold; letter-spacing:-1px; margin-top:8px; margin-bottom:5px;}
.atm_bank_c2b{font-size:14px; font-weight:bold; letter-spacing:-1px; margin-top:0px; margin-bottom:5px;}
.atm_bank_c3{    font-size:12px; font-weight:bold; padding:4px 8px; color:#737373; text-align:center; margin:0 0px; margin-top:5px;   display:inline-block; border:#949494 1px solid; background-color:#fff;  border-radius:5px;  width:21%; max-width: 150px; cursor:pointer; }
.atm_bank_c3a{    font-size:12px; font-weight:bold; padding:4px 8px; color:#ffffff; text-align:center; margin:0 0px; margin-top:5px;   display:inline-block; background-color:#fff;  border-radius:5px;  width:21%; max-width: 150px; background-color:#ea4335; cursor:pointer; }
.atm_bank_c3b{    font-size:12px; font-weight:bold; padding:4px 8px; color:#ffffff; text-align:center; margin:0 0px; margin-top:5px;   display:inline-block; background-color:#fff;  border-radius:5px;  width:21%; max-width: 150px; background-color:#2ac1bc; cursor:pointer; }
.atm_bank_c3c{    font-size:12px; font-weight:bold; padding:4px 8px; color:#ffffff; text-align:center; margin:0 0px; margin-top:5px;   display:inline-block; background-color:#fff;  border-radius:5px;  width:21%; max-width: 150px; background-color:#34a853; cursor:pointer; }
.atm_bank_c3d{    font-size:12px; font-weight:bold; padding:4px 8px; color:#ffffff; text-align:center; margin:0 0px; margin-top:5px;   display:inline-block; background-color:#fff;  border-radius:5px;  width:21%; max-width: 150px; background-color:#4285f4; cursor:pointer; }
.atm_bank_c4{    font-size:12px; font-weight:bold; padding:4px 8px; color:#737373; text-align:center; margin:0 0px; margin-top:15px;  display:inline-block; border:#949494 1px solid; background-color:#fff;  border-radius:5px;  width:21%; max-width: 150px; cursor:pointer; letter-spacing:-1px; }
.atm_bank_c4_on{ font-size:12px; font-weight:bold; padding:4px 8px; color:#ff5001; text-align:center; margin:0 0px; margin-top:15px;  display:inline-block; border:#ff5001 1px solid; background-color:#fff;  border-radius:5px;  width:21%; max-width: 150px; cursor:pointer; letter-spacing:-1px; }
.atm_bank_c5 {font-size:14px; font-weight:bold; letter-spacing:0px; color:#a7a7a7; display:inline-block; padding:15px 0; margin-left:5%;   }
.atm_bank_c6 {font-size:14px; font-weight:bold; letter-spacing:0px; color:#333333; display:inline-block; padding:5px 0; margin-right:3%; text-align:right; line-height:120%; width:22%; white-space:nowrap;   }
.atm_bank_c7 {font-size:12px; font-weight:bold; letter-spacing:0px; color:#fff; padding:15px 0; margin-right:0%; width:100%; background-color:#989898; display:inline-block;  }
.atm_bank_c8 {font-size:12px; font-weight:bold; letter-spacing:0px; color:#80828b; display:inline-block; padding:15px 10px 8px;  }
.atm_bank_c9 {font-size:12px; font-weight:bold; letter-spacing:0px; color:#333333; display:inline-block; padding:15px 10px 8px;  }
.atm_bank_c10{font-size:12px; font-weight:bold; letter-spacing:0px; color:#333333;}

.atm_bank_input1{ border:0; border-bottom: #d1d1d1 1px solid; width:80%; padding:8px 0px 3px; display:inline-block; text-align:center; margin:0 2% 0 0;}

/*그래프*/
.atm_bank_graph_wrapper{ text-align:center;}
.atm_bank_graph{ margin-top:10px; width:100%; display:inline-block; max-width:640px;}
.atm_bankgraph_el{ text-align:left;}
.atm_bank_graphwrapper{margin:0% 0  0 0%;  width:65%; display:inline-block; font-size:0; }
.atm_bank_frame{float:left; position:relative; border:#fff solid 1px; width:100%; margin-left:0%; background-color:#f5f5f5;}
.atm_bank_gaze_min{float:left; background-color:#2ac1bc; text-align:left; height:5px;}
.atm_bank_gaze_org{float:left; background-color:#ff5001; text-align:left; height:5px;}

/*상세내역테이블*/
.atm_bank_listwrapper{ padding:20px 14px; font-size:12px; text-align:center;}
.atm_bank_list{width:100%; display:inline-block; max-width:640px; }
.atm_bank_mylist_top{border:1px solid #006360; background-color:#006360;}
.atm_bank_mylist0 {width:100%; border:1px solid #c8c8c8; border-top:#006360; border-top:2px; border-collapse:collapse;}
.atm_bank_mylist1{border:1px solid #006360; text-align:center; background-color:#2ac1bc; font-weight:bold; color:#fff;}
.atm_bank_mylist2{border:1px solid #c8c8c8; text-align:center;}
.atm_bank_mylist_gray{ background-color:#f2f2f2;}

/*캘린더테이블*/
.atm_bank_callist0 {width:100%; border:1px solid #b4bdc4; border-collapse:collapse;}
.atm_bank_callist1{border:1px solid #b4bdc4; text-align:center; background-color:#34363d; font-weight:bold; color:#fff; padding:3px 0;}
.atm_bank_callist2{border:1px solid #b4bdc4; text-align:center; background-color:#80828b; font-weight:bold; color:#fff; padding:3px 0;}
.atm_bank_callist3{border:1px solid #b4bdc4; text-align:center; background-color:#e8ebed; font-weight:bold; color:#333; padding:8px 0;}
.atm_bank_callist4{border:1px solid #b4bdc4; text-align:center; background-color:#ffffff; font-weight:bold; color:#333; padding:8px 0;}
.atm_bank_callist4_on{border:1px solid #b4bdc4; text-align:center; background-color:#ff5001; font-weight:bold; color:#fff; padding:8px 0;}
.atm_bank_callist_gray{ background-color:#f2f2f2;}

/*본문부분*/
#atm_bank_wrapper0{ width:100%; padding:0 0 0 0; background-color:#fff;}


.atm_bank_date{ padding:0; text-align:center; position:relative; }
.atm_bank_date_el{ display:inline-block; width:140px;}
.atm_bankcal1{ position:absolute;  border-radius:5px; background-color:#fff; box-shadow:0px 0px 10px #000; width:250px; z-index:10; padding:5px 15px 15px; position:absolute; top:60px;; left: -30%; }
.atm_bankcal2{ position:absolute;  border-radius:5px; background-color:#fff; box-shadow:0px 0px 10px #000; width:250px; z-index:10; padding:5px 15px 15px; position:absolute; top:60px;; left: -15%; }

@media screen and (min-width: 800px) {
#atm_bank_wrapper0{ text-align:center;}
.atm_bank_myaccount_pcgray{ background-color:#f2f2f2;}
.atm_bank_myaccount{ width:800px; display:inline-block; }
.atm_bank_pcwidth{ width:800px; display:inline-block; }
.atm_ranka_tab1{ text-align:center;}
.atm_ranka_tab1_pc{ width:800px; display:inline-block;  position:relative; }
}

</style>

<script>
$(document).ready(function(){
	$.datepicker.regional['ko'] = {
		closeText: 'getLangStr("jsm_0077")',
		prevText: 'getLangStr("jsm_0078")',
		nextText: 'getLangStr("jsm_0079")',
		currentText: 'getLangStr("jsm_0080")',
		monthNames: ['getLangStr("jsm_0081")','getLangStr("jsm_0082")','getLangStr("jsm_0083")','getLangStr("jsm_0084")','getLangStr("jsm_0085")','getLangStr("jsm_0086")','getLangStr("jsm_0087")','getLangStr("jsm_0088")','getLangStr("jsm_0089")','getLangStr("jsm_0090")','getLangStr("jsm_0091")','getLangStr("jsm_0092")'],
		monthNamesShort: ['getLangStr("jsm_0081")','getLangStr("jsm_0082")','getLangStr("jsm_0083")','getLangStr("jsm_0084")','getLangStr("jsm_0085")','getLangStr("jsm_0086")','getLangStr("jsm_0087")','getLangStr("jsm_0088")','getLangStr("jsm_0089")','getLangStr("jsm_0090")','getLangStr("jsm_0091")','getLangStr("jsm_0092")'],
		dayNames: ['getLangStr("jsm_0093")','getLangStr("jsm_0094")','getLangStr("jsm_0095")','getLangStr("jsm_0096")','getLangStr("jsm_0097")','getLangStr("jsm_0098")','getLangStr("jsm_0099")'],
		dayNamesShort: ['getLangStr("jsm_0093")','getLangStr("jsm_0094")','getLangStr("jsm_0095")','getLangStr("jsm_0096")','getLangStr("jsm_0097")','getLangStr("jsm_0098")','getLangStr("jsm_0099")'],
		dayNamesMin: ['getLangStr("jsm_0093")','getLangStr("jsm_0094")','getLangStr("jsm_0095")','getLangStr("jsm_0096")','getLangStr("jsm_0097")','getLangStr("jsm_0098")','getLangStr("jsm_0099")'],
		weekHeader: 'Wk',
		dateFormat: 'yy-mm-dd',
		firstDay: 0,
		isRTL: false,
		showMonthAfterYear: true,
		yearSuffix: ''};
	$.datepicker.setDefaults($.datepicker.regional['ko']);
	/* 2017.9.14 캘린더 위치 수정 by 차건환 */
	$("#datepicker1").datepicker({
		numberOfMonths: 1,
		onSelect: function(selected) {
			$("#datepicker2").datepicker("option","minDate", selected)
		},
		beforeShow: function(input) {
			var i_offset= $(input).offset();
			setTimeout(function(){
				$('#ui-datepicker-div').css({'top':i_offset.top, 'bottom':'', 'left':'10px'});
			})
		}
	});

	$("#datepicker2").datepicker({
		numberOfMonths: 1,
		onSelect: function(selected) {
			$("#datepicker1").datepicker("option","maxDate", selected)
		},
		beforeShow: function(input) {
			var i_offset= $(input).offset();
			setTimeout(function(){
				$('#ui-datepicker-div').css({'top':i_offset.top, 'bottom':'', 'left':'10px'});
			})
		}
	});
	
	$('.ui-datepicker').css('font-size', '20px');
	/*
	goSubmit('frm_bank', 'MyBankList.asp?FlagBank=${flagBank}&FlagSearchDate=1', 'ifrm');
	atmBankOnOff(1);
	viewBankReport();
	viewBankList();
	*/
	viewMonthData(1);
});
</script>
<div id="atm_wrapper">
<%@ include file="/Common/include/MenuSub.jsp" %>

	<div id="atm_bank_wrapper0">
	<!--wrapper start -->
	<form name="frm_sch" method="post">
		<div class="atm_bank_myaccount_pcgray">
			<div class="atm_bank_myaccount" align="center">
				<!-- [수정(2018.03.31): 김현구] -->
				<!--
			    <p class="atm_bank_c1">내잔고 <span class="atm_color_org atm_superbold"><%//'=FormatNumber(Session("UserAlmoney"),1)%></span> 알</p>
			    -->
    			<p class="atm_bank_c1">${memberName} (${memberNickName} / ${memberSeq})님 잔고 <span class="atm_color_org atm_superbold"><fmt:formatNumber value="${memberAlmoney}" pattern="#,###.#" /></span><spring:message code="msg_0136"/></p>
			    <div class="atm_bank_line"></div>
				<p class="atm_bank_c2"><span class="atm_whitespace">총 수익 <span class="atm_color_min atm_superbold"><fmt:formatNumber value="${Import}" pattern="#,###.#" /></span><spring:message code="msg_0136"/></span> <span class="atm_bank_color_gray">&nbsp;|&nbsp;</span> <span class="atm_whitespace">총 지출 <span class="atm_color_org atm_superbold"><fmt:formatNumber value="${Expense}" pattern="#,###.#" /></span><spring:message code="msg_0136"/></span></p>
				<p class="atm_bank_c3a" onClick="alert('\'나비천사\' 이상의 등급부터 사용할 수 있습니다.');">대출</p>
				<p class="atm_bank_c3b" onClick="alert('\'나비천사\' 이상의 등급부터 사용할 수 있습니다.');">결제</p>
				<p class="atm_bank_c3c" onClick="alert('\'나비천사\' 이상의 등급부터 사용할 수 있습니다.');">이체</p>
				<p class="atm_bank_c3d" onClick="alert('\'나비천사\' 이상의 등급부터 사용할 수 있습니다.');">출금</p>
			</div><!--atm_bank_myaccount end -->
		</div><!-- atm_bank_myaccount_pcgray end -->
		<c:set var="flagBank" value="${flagBank}"/>
		<%
			String flagBank = String.valueOf(pageContext.getAttribute("flagBank"));
		%>
		<div class="atm_ranka_tab1">
			<div class="atm_ranka_tab1_pc">
				<p class="atm_ranka_tabc1<%if(flagBank.equals("I")) {%>_on<%}%>" onClick="javascript:location.href='/member/myBank_Admin?FlagBank=I';">수입 / 지출 내역</p>
				<p class="atm_ranka_tabc2">|</p>
			</div>
		</div>
		
		<div class="atm_bank_pcwidth">
			<div id="divBody" style="display:none">
				<div class="atm_bank_myaccount2" align="center">
					<p class="atm_bank_c4" id="atm_bank_1" onClick="viewMonthData(1)">1개월</p>
					<p class="atm_bank_c4" id="atm_bank_2" onClick="viewMonthData(2)">2개월</p>
					<p class="atm_bank_c4" id="atm_bank_3" onClick="viewMonthData(3)">3개월</p>
					<p class="atm_bank_c4" id="atm_bank_4" onClick="viewMonthData(4)">기간조회</p>
				</div><!-- atm_bank_myaccount2 end -->
				
				<div class="atm_bank_date">
					<div class="atm_bank_date_el">
						<span class="atm_whitespace">
							<input type="text" name="SearchDateS" value="${searchDateS}" id="datepicker1" class="atm_bank_input1" readonly>
						</span>
					</div>
					<div class="atm_bank_date_el">
						<span class="atm_whitespace">
							<p class="atm_bank_c8">~</p>
							<input type="text" name="SearchDateE" value="${searchDateE}" id="datepicker2" class="atm_bank_input1" readonly>
						</span>
					</div>
				</div>
				
				<div class="atm_bank_graph_wrapper" align="center">
					<div class="atm_bank_graph">
						<div class="atm_bank_income" id="BankReport"></div>
					</div>
				</div>
				
				<div class="atm_bank_listwrapper" align="center">
					<div class="atm_bank_list" id="BankList"></div>
				</div>
				
				<div class="atm_boardnavi">
				
				</div>
				
			</div><!-- divBody end -->
		</div><!-- atm_bank_pcwidth end -->
	</form>
	<%@ include file="/Common/include/MenuItem.jsp" %>
	<!--wrapper end -->
	</div>
</div><!-- atm_wrapper end -->
<form name="frm_bank" method="post">
</form>
<iframe name="ifrm" width="100%" height="0" frameborder="0"></iframe>
<script>
	function viewMonthData(flag) {
		const $start = $('input[name=SearchDateS]');
		var startDate = new Date();
		
		if(flag != 4) {
			startDate.setMonth(startDate.getMonth() - flag)
			console.log(getFormatDate(startDate))
			$start.val(getFormatDate(startDate))
		}

		viewBankReport();
		viewBankList();
		atmBankOnOff(flag);
	}
	function fShowBody()
	{
		if (document.isShow)
			$('#divBody').fadeIn();
			//$('divBody').slideDown(1000);
		else
			document.isShow = true;
	}
	function viewBankReport() {
		$.ajax({
			url : "/member/getBankReport_Admin?MemberSeq="+${memberSeq}+"&time=" + new Date(),
			data: $('form[name=frm_sch]').serialize(),
			success: function(data) {
				$('#BankReport').html(data)
				fShowBody();
			}
		});
	}
	function viewBankList(page) {
		page = page || 1;
		console.log("debug: " + $('form[name=frm_sch]').serialize() + '&PageNum=' + page)
		$.ajax({
			url: "/member/getBankList_Admin?MemberSeq="+${memberSeq}+"&time=" + new Date(),
			data: $('form[name=frm_sch]').serialize() + '&PageNum=' + page,
			type: "post",
			success: function(data) {
				var html = data.substr(0, data.indexOf("data:"));
				var count = data.substr(data.indexOf("data:"));
				count = count.substr(count.indexOf(':') + 1);
				count = parseInt(count, 10);
				//console.log(count)
				$('#BankList').html(html)
				fShowBody();
				
				//TODO: 페이징 코드 분리
				const pageCnt = 7, pageSize = 50;
				const $pagingWrapper = $('.atm_boardnavi');
				var pagingBtn = '<p class="atm_boardnavi_el{on}" value="{value}">{pageNumber}</p>';

				var startNum = Math.floor((page - 1) / pageCnt) * pageCnt + 1,
					endNum = startNum + (pageCnt - 1),
					totalPage = Math.ceil(count / pageSize);	
				endNum = Math.min(endNum, totalPage);
				if(page > totalPage) {
					page = 1;
				}
				
				$pagingWrapper.html('');
				if(startNum !== 1) {
					let btnHtml = pagingBtn.replaceAll('{on}', '').replaceAll('{pageNumber}', '<<').replaceAll('{value}', 1);
					$pagingWrapper.append(btnHtml);
					btnHtml = pagingBtn.replaceAll('{on}', '').replaceAll('{pageNumber}', '<').replaceAll('{value}', (startNum - 1));
					$pagingWrapper.append(btnHtml);
				}
				for(let i = startNum; i <= endNum; i++) {
					let btnHtml = pagingBtn;
					if(i == page) {btnHtml = btnHtml.replaceAll('{on}', '_on');}
					else {btnHtml = btnHtml.replaceAll('{on}', '');}
					btnHtml = btnHtml.replaceAll('{pageNumber}', i).replaceAll('{value}', i);
					$pagingWrapper.append(btnHtml);
				}
				if(endNum !== totalPage) {
					let btnHtml = pagingBtn.replaceAll('{on}', '').replaceAll('{pageNumber}', '>').replaceAll('{value}', (endNum + 1));
					$pagingWrapper.append(btnHtml);
					btnHtml = pagingBtn.replaceAll('{on}', '').replaceAll('{pageNumber}', '>>').replaceAll('{value}', totalPage);
					$pagingWrapper.append(btnHtml);
				}
			}
		});
	}
	function atmBankOnOff(current) {
		if(current == 4) {
			$('.atm_bank_date').css('display', 'block');
		}
		else {
			$('.atm_bank_date').css('display', 'none');
		}
		for(var i = 1; i <= 4; ++i) {
			var str1 = '#atm_bank_' + i;
			if(current != i) {
				$(str1).removeClass('atm_bank_c4_on').addClass('atm_bank_c4');
			} else {
				$(str1).addClass('atm_bank_c4_on').removeClass('atm_bank_c4');
			}
		}
	}
	$(document).on('click', '.atm_boardnavi_el', function() {
		const value = $(this).attr('value');
		viewBankList(value);
	})
</script>
</body>
</html>