<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.altong.web.logic.util.CommonUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body id="atm_gray">
<style type="text/css">

ul,li {list-style-type:none;}
img,p,li {border:0;margin:0;padding:0; vertical-align:middle;}


/*img*/

.atm_rankq_btn_L1{ position:absolute; left:0px; top:5px;  }
.atm_rankq_btnx{}
.atm_rankq_img_L1{ position:absolute; left:10px; top:20%; }

/*p태그*/

.atm_rankq_c1{ font-size:12px; font-weight:bold; letter-spacing:-0.5px; padding:8px 20px; color:#ddfffe; border:#96eae7 1px solid; display:inline-block; }
.atm_rankq_c2{white-space:nowrap; }
.atm_rankq_c3{ font-size:11px; font-weight:bold; letter-spacing:-0.5px; padding:5px 7px; color:#595959; border:#949494 1px solid; display:inline-block; background-color:#fff; }
.atm_rankq_c4{ font-size:12px; font-weight:bold; letter-spacing:-0.5px;                  color:#595959; display:inline-block;  }
.atm_rankq_c5{ font-size:12px; color:#008d88; margin-right:3px;}
.atm_rankq31_c6{ font-size:12px; color:#ff5001; margin-left:3px;}

.atm_rankq_el_c1{ font-size:16px; font-weight:bold; letter-spacing:-1px; padding:5px 7px; color:#ff5001; display:inline-block; position:absolute; right:10px; top:36%;}
.atm_rankq31_el_c2{ font-size:14px; font-weight:bold; letter-spacing:-1px;  color:#333333; display:inline-block; cursor:pointer;}
.atm_rankq31_el_c3{ font-size:12px; font-weight:bold; letter-spacing:-0.5px;  color:#9e9e9e; display:inline-block; margin-left:5px;}
.atm_rankq_el_c4a{ font-size:24px; font-weight:bold; letter-spacing:-0.5px;  color:#fff ; position:absolute; left:10px; top:33%;  border-radius:200px; background:#bb8d00; padding:5px 12px; border:#fff 3px solid;}
.atm_rankq_el_c4b{ font-size:24px; font-weight:bold; letter-spacing:-0.5px;  color:#fff ; position:absolute; left:10px; top:33%;  border-radius:200px; background:#aaaaaa; padding:5px 12px; border:#fff 3px solid;}
.atm_rankq_el_c4c{ font-size:24px; font-weight:bold; letter-spacing:-0.5px;  color:#fff ; position:absolute; left:10px; top:33%;  border-radius:200px; background:#bd5d00; padding:5px 12px; border:#fff 3px solid;}
.atm_rankq_el_c4d{ font-size:24px; font-weight:bold; letter-spacing:-0.5px;  color:#fff ; position:absolute; left:10px; top:24%;  border-radius:200px; background:#2ac1bc; padding:5px 12px; border:#fff 3px solid;}


/*본문부분*/
#atm_rankq_wrapper0{ width:100%; padding:0 0 0 0; background-color:#f2f2f2;}

.atm_rankq_ttbar1{  background-color:#e8e8e8; padding:10px 0px; text-align:center; }
.atm_rankq_ttbar2{  background-color:#2ac1bc; padding:10px 10px; text-align:right; position:relative; }
.atm_rankq_con{text-align:left; position:relative; }
.atm_rankq_el{ position:relative; background-color:#fff; margin-top:7px; padding:15px 14px; border-bottom:#bbbbbb 1px solid; border-left:#dcdcdc 1px solid; border-right:#dcdcdc 1px solid;}
.atm_rankq31_eltexts{ padding:10px 0;}

@media screen and (min-width: 800px) {
.atm_rankq_con{ width:800px; display:inline-block; }
}

.atm_rankimg_best {box-sizing: content-box; top:10px; left:12px; z-index: 1;width: 39px; overflow: hidden; border-radius: 9px;}
.atm_rankimg_best:before {
	-webkit-animation:border-gradient 1.5s linear infinite;animation:border-gradient 1.5s linear infinite;-moz-animation:border-gradient 1.5s linear infinite;
	background:linear-gradient(to right, #f9f4c2 0%, #bf9705 100%);content:"";position: absolute; top:-50%; left:-50%; width:200%;height:200%; z-index: -1;
}
/* .atm_rankimg_best:before {
	background:linear-gradient(to right, #f8f2ce 0%, #e3c03e 50%, #6e5c36 100%);
} */

.atm_rankimg_best img { width:35px; height:35px;border-radius: 7px; margin:2px;}
.rankimg_crown {width:19px;position: absolute; top:0; left:0;margin-left:7px; margin-top:6px;transform: rotate(-25deg);z-index: 2;}


.wrap_Qlv {border:1px solid #0E3567;border-radius:20px; padding:0 4px;line-height:13px;display:inline-block;color:#0E3567;font-size:10px;font-weight:normal;}
.atm_icon_score1 {background:#ffee75;padding:1px 4px 0 4px;border:1px solid #ffda23;border-radius:24px;}
.atm_icon_score1 .atm_icon_score2 {color:#000;font-size:10px;font-weight:bold;letter-spacing: 0;}
.atm_icon_score1.lv_color_change {background:#ff0024; border:1px solid #881327}
.atm_icon_score1.lv_color_change .atm_icon_score2 {color:#fff;}

ul,li {list-style-type:none;}
img,p,li {border:0;margin:0;padding:0; vertical-align:middle;}

/*img*/

/*p태그*/

.atm_ranka12_c1{ font-size:14px; font-weight:bold; letter-spacing:-1px;  color:#333333; display:inline-block;}
.atm_ranka12_c2{ white-space:nowrap; }
.atm_ranka12_c3{ font-size:11px; font-weight:bold; letter-spacing:-0.5px; padding:5px 7px; color:#595959; border:#949494 1px solid; display:inline-block; background-color:#fff; }
.atm_ranka12_c4{ font-size:13px; font-weight:bold; letter-spacing:-0.5px;                  color:#595959; display:inline-block;  }
.atm_ranka12_c5{ font-size:14px; font-weight:bold; letter-spacing:-0.5px;  color:#333333; line-height:17px; text-overflow:ellipsis; overflow:hidden; white-space:nowrap;}
.atm_ranka12_c6{ font-size:13px; font-weight:normal; letter-spacing:0px;  color:#333333; line-height:17px; margin-top:5px;}
.atm_ranka12_c7{ font-size:12px; font-weight:normal; letter-spacing:0px;  color:#9e9e9e; display:inline-block; margin-top:0px; width:100%; }

/*본문부분*/
#atm_ranka12_wrapper0{ width:100%; padding:0 0 0 0; background-color:#f2f2f2;}

.atm_ranka12_ttbar1{  background-color:#e8e8e8; padding:10px 0px; text-align:center;  }

.atm_ranka12_con{ padding:0px 0 11px; text-align:left; position:relative; font-size:0; }
.atm_ranka12_el{ position:relative; background-color:#fff; margin-top:7px; padding:10px 14px; border-bottom:#bbbbbb 1px solid; cursor:pointer; }
@media screen and (min-width: 800px) {
#atm_ranka12_wrapper0{ text-align:center;}
.atm_ranka12_con{ width:800px; display:inline-block; }
.atm_ranka_tab1_pc{ width:800px; display:inline-block;  position:relative; }
.atm_ranka_tab2_pc{ width:800px; display:inline-block;  position:relative; }
}

.atm_icon_score2{
  word-break:keep-all;
  white-space:-moz-pre-wrap;
  white-space:-pre-wrap;
  white-space:-o-pre-wrap;
  word-wrap:break-word;
}

a:focus, a:hover {text-decoration: none;}
.atm_ranka12_con a:focus .atm_ranka12_c5,
.atm_ranka12_con a:hover .atm_ranka12_c5 {color:#12b1ac;}
.atm_boardnavi_el,.atm_boardnavi_el_on {padding:0 11px; margin:0 6px;min-width:50px;}
.atm_boardnavi {padding-bottom:12px;}
.atm_boardnavi_arrow {margin-bottom:40px; text-align: center; display: block;padding:0;}
.atm_boardnavi_arrow .atm_boardnavi_el {width:35px; padding:0; min-width:30px;border-color:#dadada;}
body {
	background-color: #f2f2f2;
}

@-webkit-keyframes border-gradient {
	100% {-webkit-transform: rotate(360deg);}
}
@keyframes border-gradient {
	100% {transform: rotate(360deg);}
}

</style>
<script>
var pg = ${pg};
var Level = new Object();
<%
String[] Level = new String[100];
Level[0] = "알";
Level[1] = "알천사";
Level[2] = "나비천사";
Level[3] = "미소천사";
Level[4] = "열혈천사";
Level[5] = "황금천사";
Level[6] = "수호천사";
Level[7] = "빛의천사";
Level[8] = "천사장";
Level[9] = "대천사";
Level[10] = "대천사장";
Level[11] = "알통폐인";
Level[98] = "알돌이";
Level[99] = "관리자";

for(int i = 0; i < Level.length; i++) {
	if(i > 11 && i < 98) { continue; }
	out.println("Level[" + i + "] = '" + Level[i] + "';");
}
%>

function fAjax() {
	if (document.xhr) return;

	pg++;
	$('#divProg').slideDown(300);
	setTimeout(fMakeRow, 1000); //너무 빨리 응답이 와도 비쥬얼이 좋지 않은 것 같아서 일정 시간 모래시계를 유지시킨다.

	document.xhr = $.ajax({
		type: 'get',
		url: '/answer/questionList_SpAjax',
		data: 'ACT=JSON&pg=' + pg,
		dataType: 'json',
		crossDomain: true,
		success: function (r) {
			if (r.result == 'SUCCESS' && r.arr)
			{
				document.arrRows = r.arr;
				fMakeRow();
			}
			else
			{
				$('#divProg').slideUp();
				$(window).unbind("scroll");
			}
		},
		error: function (r, textStatus, err) {
			$('#divProg').slideUp();
			$(window).unbind("scroll");
			console.log(r);
		},
		complete: function () {
			//$('#divProg').slideUp();
		}
	});
}
function fMakeRow()
{
	if (!document.isShow)
	{
		document.isShow = true;
		return;
	}

	$('.atm_boardnavi').slideUp(500);

	var newObj, target = $('.atm_rank_el:first');
	document.arrRows.forEach(function(v) {
		newObj = target.clone();
		newObj.appendTo(target.parent()).attr('onClick','location=\'/answer/answerList?Seq=' + v.Seq + '&SP=<?=$SP?>' + '\'').css('display','none').slideDown();
		newObj.find('#Title').html(v.Title);
		newObj.find('#NickName').text(v.NickName);
		newObj.find('#Lv').text(Level[v.Lv]);
		newObj.find('#ReadCount').text(v.ReadCount);
		newObj.find('#AnswCount').text(v.AnswCount);
		newObj.find('#Photo').attr('src', '${libIMG_URL}/UploadFile/Profile/' + (v.Photo ? v.Photo : 'img_thum_base0.jpg'));
	});

	$('#divProg').slideUp();
	document.isShow = false;
	document.xhr = false;
}

$(window).scroll(function() {
	if($(window).scrollBottom() < 150) fAjax();
});

</script>
<div id="atm_wrapper">
<%@ include file="/Common/include/MenuSub.jsp" %>


	<div id="atm_rankq_wrapper0" align="center">
	<!--wrapper start -->
		<div class="atm_ranktopQ_1">
			<span class="atm_rankq_c2"><p class="atm_ranktopQ_c3">답변채택수익</p>&nbsp;<p class="atm_ranktopQ_c4"><fmt:formatNumber value="${sum_a_choicedAlmoney}" pattern="#,###" />&nbsp;알</p></span>&nbsp;
			<span class="atm_rankq_c2"><p class="atm_ranktopQ_c3">답변열람수익</p>&nbsp;<p class="atm_ranktopQ_c4"><fmt:formatNumber value="${sum_a_viewAlmoney}" pattern="#,###" />&nbsp;알</p></span>
		</div>

		<div class="atm_rankq_con">
		<c:forEach var="item" items="${questionList}" varStatus="status">
			<c:set var="lvColor" value=""/>
			<c:set var="photo" value="${item.photo}"/>
			
			<fmt:formatNumber var="almoney" value="${item.almoney}" pattern="#,###" />
			<fmt:formatNumber var="readCount" value="${item.readCount}" pattern="#,###" />
			<fmt:formatNumber var="answCount" value="${item.answCount}" pattern="#,###" />
			<fmt:formatNumber var="replyCount" value="${item.replyCount}" pattern="#,###" />
			
			<c:set var="lv" value="${item.lv}"/>
			<%
				int lv = Integer.parseInt( String.valueOf( pageContext.getAttribute("lv") ) );
				String lvl = CommonUtil.getLevelName(lv, request);
				
				pageContext.setAttribute("lvl", lvl);
			%>
			
			<c:if test="${item.almoney >= 5000}">
				<c:set var="lvColor" value="lv_color_change"/>
			</c:if>
			
			<c:if test="${item.photo == '' or item.photo == null}">
				<c:set var="photo" value="img_thum_base0.jpg"/>
			</c:if>
			
			<div class="atm_ranka12_el atm_border" onClick="location='/answer/answerList?Seq=${item.seq}&SP=${sp}'">
				<div style="display:inline-block;width:43px;vertical-align:middle;">
					<img src="${IMG_URL}/UploadFile/Profile/${photo}" style="width:35px;height:35px;border-radius:7px;">
				</div>
				<div style="display:inline-block;vertical-align:middle; width:80%;">
					<div class="atm_ranka12_c5"><div class="atm_icon_score1 ${lvColor}"><span class="atm_icon_score2">${almoney}</span></div>${item.title}</div>
					<p class="atm_ranka12_c7" style="letter-spacing:-0.5px">${item.conDate}<span style="width:6px; height:1px;display:inline-block;"></span>
						<span class="atm_whitespace" style="margin-right:1px;"><img src="/Common/images/icon_view.png" class="atm_viewicon" style="margin:0;">${readCount}</span>
						<span class="atm_whitespace" style="margin-right:1px;"><img src="/Common/images/icon_answer_micro.png" class="atm_viewicon" style="margin:0;">${answCount}</span>
						<span class="atm_whitespace"><img src="/Common/images/icon_reply.png" class="atm_viewicon" style="margin:0;">${replyCount}</span>
					</p>
					<p style="display:block;color:#0E3567;font-size:11px;font-weight:bold;letter-spacing:-0.5px;">
						<span style="font-weight:normal;">질문자:</span> ${item.nickName}
						<span class="wrap_Qlv">${lvl}</span>
					</p>
				</div>
			</div>
		</c:forEach>
		</div><!--atm_rankq_con end -->
		
		<div id="divProg" style="width:100%;display:none"><div style="width:30px;margin:auto"><img src="${IMG_URL}/Common/images/search_nick_loader.gif" style="width:100%"></div></div>
		
		<script>
		$('.atm_boardnavi,.atm_boardnavi_arrow').on('click', 'p.atm_boardnavi_el', function(){
			location = '/answer/questionList_SP?pg=' + $(this).attr('value');
		});
		</script>
		<%
			String SP = "";
			if(request.getAttribute("sp") != null) {
				SP = String.valueOf(request.getAttribute("sp"));
			}
			String libWriteURL = "/question/questionWrite?SP=" + SP;
		%>
		<%@ include file="/Common/include/BottomAnswer.jsp" %>
	</div><!--wrapper end -->
</div><!-- atm_wrapper end -->
</body>
</html>