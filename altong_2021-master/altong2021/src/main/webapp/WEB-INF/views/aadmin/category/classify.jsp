<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %> 
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi">
	<title>Altong</title>
<%
JSONObject i_info = (JSONObject)CommonUtil.getGlobal(request, response);
pageContext.setAttribute("global", i_info);

if(i_info.get("UserSeq").equals("10000110") || i_info.get("UserSeq").equals("10000564") || i_info.get("UserSeq").equals("10000092") || i_info.get("UserLv").equals("99") || i_info.get("UserSeq").equals("10000703") || i_info.get("UserSeq").equals("10010318") ) {
%>
	<script type="text/javascript" src="/Common/js/jquery.js"></script>
	<script src="/Common/aadmin/category/Classify.js?2"></script>
	<style>
		button {line-height: 33px;border-radius: 34px; border:1px solid #aaa; background: #e8e8e8;padding:0 13px;font-size: 13px;}
		.atm_wq_input5{height : 35px;background: #fff;border:1px solid #aaa;}
		.questions,table,tr,tbody{width:100%;}
		.tg  {border-collapse:collapse;border-spacing:0;box-sizing: border-box;table-layout: fixed;}
		.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;word-break:break-all;}
		.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;word-break:break-all;}
		.tg .tg-yw4l{vertical-align:middle; }

		.setCategory {margin-top:20px; margin-bottom:15px;text-align: center;}

		input[name=paging]{width:3em;line-height:23px;text-align: center;border:1px solid #aaa;}
		body {margin:0;}
		.select_box {display: inline-block; vertical-align: middle;}
		.select_btn {display: inline-block;}
		.select_btn button {font-weight: bold;}
		.select_btn .saveBtn {background: #41c721; color:#fff;padding:0 25px; }
		.select_btn .tmpBtn {background: #827a7a; color:#fff; margin-right:10px;margin-left: 20px;}
		.pageBtn_wrap button {line-height: 30px; border-radius: 7px;}
		.questions tr.index {background: #eee;}
		th a {color:#000;}
		@media (min-width:640px) {
			.questions{padding:0 10px;box-sizing: border-box;}
			.questions .tg .index th:first-child {min-width: 20px;}
		}
		@media (max-width:640px) {
			.setCategory {padding-top:10px; padding-bottom:13px;position: fixed; margin:0; top:0; width:100%; background: #fff;}
			body {margin-top:190px;}
			.select_box {width:100%; text-align: center;margin-bottom:16px;}
			.atm_wq_input5 {width:18%;}
			.select_btn{display: block;text-align: center;}
			.select_btn .saveBtn {width: 47%;}
			.select_btn .tmpBtn {width:47%;margin-left: 0; }
			.select_btn button {line-height: 35px}
		}
		input[type="checkbox"] {margin:0;}
	</style>
</head>
<body>
<div class="Wrapper">
	<div class="setCategory">
		<div style="text-align: left; padding:0 10px;">
			<div class="select_box">
				<select name="Section1" class="atm_wq_input5">
					<option value="0">??????</option>
				</select>
				<select name="Section2" class="atm_wq_input5">
					<option value="0">??????</option>
				</select>
				<select name="Section3" class="atm_wq_input5">
					<option value="0">??????</option>
				</select>
				<select name="Section4" class="atm_wq_input5">
					<option value="0">??????</option>
				</select>
				<select name="Section5" class="atm_wq_input5">
					<option value="0">??????</option>
				</select>
			</div>
			<div class="select_btn">
				<button class="tmpBtn">???????????????</button>
				<button class="saveBtn" >??? ???</button>
			</div>
		</div>
		<div class="pageBtn_wrap" style="text-align: right; padding:0 10px; padding-top:30px;">
			<div class="pagingNumbering" style="display:inline-block;">
				<input type="number" name="paging"></input>
				<script>document.write(" / " + (Math.floor(global.pageCount / 30 + 1)))</script>
				<button id="goOtherPageBtn" style="margin-left:4px;">??????</button>
			</div>
			<div class="pagingBtn" style="display:inline;">
			|
				<button id="goPrevPageBtn">??????</button>
				<button id="goNextPageBtn">??????</button>
			</div>
		</div>
	</div>
	<div class="questions">
		<table class="tg">
			<tr class="index">
				<th class="tg-yw4l" style="width:30px;"></th>
				<th class="tg-yw4l" style="width:60px;">??????</th>
				<th class="tg-yw4l" style="width:60px;">??????</th>
				<th class="tg-yw4l" style="width:60px;">????????????</th>
				<th class="tg-yw4l" style="width:40%;">??????</th>
				<th class="tg-yw4l" style="width:40%;">??????</th>
			</tr>
		</table>
	</div>
<div>
<script>
	init();
	
	$(function(){
		$('.tg-yw4l img').click(function(e){
			//?????? ????????? ?????????
			//????????? ?????? ?????? ?????? ?????? ????????? ????????? ???????????? tSeq ??? ????????????.
			//????????? ?????? ?????? ??????????????? ????????? ??????????????? tSeq ??? ????????????. 
		    var contentSeq = $(this).attr('data-seq');
			var contentType = "Q";
		    //console.log('contentSeq : ' + contentSeq);
		    //alert('????????? ??????????????????!');
		    
		    var qt_seq = $('#qt_' + contentSeq).val();
		    
		    if(qt_seq == '') {
		        $.ajax({
					type: 'post',
					url: '/translate/trans',
					data: { 'contentSeq' : contentSeq, 'contentType' : contentType },
					dataType: 'json',
					crossDomain: true,
					success: function (r) {
						//console.log('r.result : ' + r.result);
						switch (r.result) {
							case 'Y':
								var tSeq = r.arr.tSeq;
								var tTitle = r.arr.tTitle;
								var tContents = r.arr.tContents;
								
								if(contentType == 'Q') {
									//tSeq ??????
									//tTitle ?????????(span id="que_") / qt_
									tTitle = tTitle.replace(/(<([^>]+)>)/ig,"");
									
									$('#que_' + contentSeq).html(tTitle);
									$('#cont_' + contentSeq).html(tContents);
									$('#qt_' + contentSeq).val(tSeq);
								}
								else if(contentType == 'A') {
									//answerList ?????? ?????? ??????
								}
								else {
									//answerList ?????? ?????? ??????
								}
								
								break;
							case 'N':
								alert('????????? ???????????? ????????? ???????????????.');
								return false;
								break;
							default:
								break;
						}
					},
					error: function (r, textStatus, err) {
						console.log(r);
					},
					complete: function () {
						document.xhr = false;
					}
				});
		    
		    }
		    else {
		    	//?????? ??????
				$.ajax({
					type: 'post',
					url: '/translate/getQuestionOrgTitle',
					data: { 'contentSeq' : contentSeq},
					dataType: 'json',
					crossDomain: true,
					success: function (r) {
						//console.log('r.result : ' + r.result);
						switch (r.result) {
							case 'Y':
								var tTitle = r.arr.tTitle;
								var tContents = r.arr.tContents;
								
								$('#que_' + contentSeq).html(tTitle);
								$('#cont_' + contentSeq).html(tContents);
								$('#qt_' + contentSeq).val(''); // ????????? ?????? ??????
								
								break;
							case 'N':
								alert('${msg_0223}');
								return false;
								break;
							default:
								break;
						}
					},
					error: function (r, textStatus, err) {
						console.log(r);
					},
					complete: function () {
						document.xhr = false;
					}
				});
		    }
		    
		    e.stopPropagation();
		    e.preventDefault();
		});	
	});
</script>

<%} else { %>
<script>
	alert("????????? ??? ????????????.")
	location.replace("/default/main")
</script>
<%} //?????? if ?????? %>
</body>