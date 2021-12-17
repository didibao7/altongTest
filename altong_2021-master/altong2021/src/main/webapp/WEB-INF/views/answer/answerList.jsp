<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.Math"%>
<%@ page import="com.altong.web.logic.util.CommonUtil"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="com.altong.web.logic.V2RankVO"%>
<%@ page import="com.altong.web.logic.ad.AdVO"%>
<%@ page import="java.util.Map.Entry"%>
<%@ page import="static com.lyncode.jtwig.functions.util.HtmlUtils.stripTags"%>
<%@ page import="java.nio.ByteBuffer"%>
<%@ page import="java.nio.CharBuffer"%>
<%@ page import="java.nio.charset.Charset"%>
<%@ page import="java.nio.charset.CharsetDecoder"%>
<%@ page import="java.nio.charset.CodingErrorAction"%>
<%@ page import="java.io.UnsupportedEncodingException"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp"%>

<script src="http://code.jquery.com/jquery-1.12.4.js"></script>
<%!public static String substring(String parameterName, int maxLength) {
		int DB_FIELD_LENGTH = maxLength;

		Charset utf8Charset = Charset.forName("UTF-8");
		CharsetDecoder cd = utf8Charset.newDecoder();

		try {
			byte[] sba = parameterName.getBytes("UTF-8");
			// Ensure truncating by having byte buffer = DB_FIELD_LENGTH
			ByteBuffer bb = ByteBuffer.wrap(sba, 0, DB_FIELD_LENGTH); // len in [B]
			CharBuffer cb = CharBuffer.allocate(DB_FIELD_LENGTH); // len in [char] <= # [B]
			// Ignore an incomplete character
			cd.onMalformedInput(CodingErrorAction.IGNORE);
			cd.decode(bb, cb, true);
			cd.flush(cb);
			parameterName = new String(cb.array(), 0, cb.position());
		} catch (UnsupportedEncodingException e) {
			System.err.println("### 지원하지 않는 인코딩입니다." + e);
		}

		return parameterName;
	}

	public static String substrib(String str, int beginIndex, int endIndex, int bytesForDB) {
		if (str == null)
			return "";

		String tmp = str;
		int slen = 0, blen = 0;
		char c;

		if (tmp.getBytes().length > endIndex - 1) {
			while (blen + 1 < endIndex - 1) {
				c = tmp.charAt(slen);
				blen++;
				slen++;

				if (c > 127)
					blen = blen + (bytesForDB - 1); // 2 byte character
			}

			tmp = tmp.substring(beginIndex, slen);
		}

		return tmp;
	}

	// 문자열 인코딩에 따라서 글자수 체크
	public static int length(CharSequence sequence) {
		int count = 0;
		for (int i = 0, len = sequence.length(); i < len; i++) {
			char ch = sequence.charAt(i);

			if (ch <= 0x7F) {
				count++;
			} else if (ch <= 0x7FF) {
				count += 2;
			} else if (Character.isHighSurrogate(ch)) {
				count += 4;
				++i;
			} else {
				count += 3;
			}
		}
		return count;
	}

	public static Map<Integer, Integer> getSortMap(Map<Integer, Integer> pointArr) {
		final boolean order = false; // false:내림차순(큰(위)->작은(아래)), true;오름차순(작(위)<- 큰(아래))
		List<Entry<Integer, Integer>> list = new LinkedList<Entry<Integer, Integer>>(pointArr.entrySet());
		Collections.sort(list, new Comparator<Entry<Integer, Integer>>() {
			public int compare(Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) {
				if (order) {
					return o1.getValue().compareTo(o2.getValue());
				} else {
					return o2.getValue().compareTo(o1.getValue());

				}
			}
		});
		Map<Integer, Integer> sortedMap = new LinkedHashMap<Integer, Integer>();
		for (Entry<Integer, Integer> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
	
	// 문자열 인코딩에 따라서 글자수 체크
    public static int contLength(CharSequence sequence) {
        int count = 0;
        for (int i = 0, len = sequence.length(); i < len; i++) {
            char ch = sequence.charAt(i);
 			/*
            if (ch <= 0x7F) {
                count++;
            } else if (ch <= 0x7FF) {
                count += 2;
            } else if (Character.isHighSurrogate(ch)) {
                count += 4;
                ++i;
            } else {
                count += 3;
            }*/
            count++;
        }
        //if(count > 3) { count = count / 3; }
        return count;
    }
%>
<%
	String Aseq = String.valueOf(request.getAttribute("Aseq"));
	String QRSeq = String.valueOf(request.getAttribute("QRSeq"));
	String ARSeq = String.valueOf(request.getAttribute("ARSeq"));
	String CSeq = String.valueOf(request.getAttribute("CSeq"));
	String developSeq = String.valueOf(request.getAttribute("developSeq"));

	String UserSeq = request.getAttribute("UserSeq").toString();
	String UserLv = request.getAttribute("UserLv").toString();
	String FlagSelfAnswer = request.getAttribute("FlagSelFAnswer") == null ? "" : request.getAttribute("FlagSelFAnswer").toString();
	String MemberType = request.getAttribute("MemberType").toString();
	String UserNickName = request.getAttribute("UserNickName").toString();
	int UserAlmoney = (int) Double.parseDouble( request.getAttribute("UserAlmoney").toString() );


	pageContext.setAttribute("UserSeq", request.getAttribute("UserSeq").toString());
	pageContext.setAttribute("UserLv", request.getAttribute("UserLv").toString());
	pageContext.setAttribute("FlagSelFAnswer", request.getAttribute("FlagSelFAnswer") == null ? "" : request.getAttribute("FlagSelFAnswer").toString() );
	pageContext.setAttribute("MemberType", request.getAttribute("MemberType").toString());
	pageContext.setAttribute("UserNickName", request.getAttribute("UserNickName").toString());
	pageContext.setAttribute("UserAlmoney", request.getAttribute("UserAlmoney").toString());
	pageContext.setAttribute("AdminSecu", request.getAttribute("AdminSecu").toString());
	
	String msg_0223 = CommonUtil.getLangMsg(request, "msg_0223");
	String msg_0224 = CommonUtil.getLangMsg(request, "msg_0224");
	String msg_0225 = CommonUtil.getLangMsg(request, "msg_0225");
	String msg_0226 = CommonUtil.getLangMsg(request, "msg_0226");
	String msg_0227 = CommonUtil.getLangMsg(request, "msg_0227");
	String msg_0230 = CommonUtil.getLangMsg(request, "msg_0230");
	String msg_0235 = CommonUtil.getLangMsg(request, "msg_0235");
	String msg_0236 = CommonUtil.getLangMsg(request, "msg_0236");
	String msg_0237 = CommonUtil.getLangMsg(request, "msg_0237");
	String msg_0255 = CommonUtil.getLangMsg(request, "msg_0255");
	String msg_0331 = CommonUtil.getLangMsg(request, "msg_0331");
	String msg_0332 = CommonUtil.getLangMsg(request, "msg_0332");
	
	pageContext.setAttribute("msg_0223", msg_0223);
	pageContext.setAttribute("msg_0224", msg_0224);
	pageContext.setAttribute("msg_0225", msg_0225);
	pageContext.setAttribute("msg_0226", msg_0226);
	pageContext.setAttribute("msg_0227", msg_0227);
	pageContext.setAttribute("msg_0230", msg_0230);
	pageContext.setAttribute("msg_0235", msg_0235);
	pageContext.setAttribute("msg_0236", msg_0236);
	pageContext.setAttribute("msg_0237", msg_0237);
	pageContext.setAttribute("msg_0255", msg_0255);
	pageContext.setAttribute("msg_0331", msg_0331);
	pageContext.setAttribute("msg_0332", msg_0332);
	
	pageContext.setAttribute("UserSeq", UserSeq);
	pageContext.setAttribute("UserLv", UserLv);
	pageContext.setAttribute("FlagSelfAnswer", FlagSelfAnswer);
	pageContext.setAttribute("MemberType", MemberType);
	pageContext.setAttribute("UserNickName", UserNickName);
	pageContext.setAttribute("UserAlmoney", UserAlmoney);
	%>
<head>
<!-- 상위 헤드 -->
<link rel="stylesheet" href="/pub/answer/answerList/answerList.css?ver=3.8">
<link rel="stylesheet" href="/pub/css/mediaQuery.css?ver=2.1">
<link rel="stylesheet" href="/pub/css/languages_common.css?ver=1.2">
<link rel="stylesheet" href="/pub/css/languages_answerList.css?ver=1.5">
<link rel="stylesheet" href="/pub/css/languages_mediaQuery.css?ver=1.2">

<script type="text/javascript" src="/Common/js_new/default/languages.js?ver=1.7"></script>   
</head>
<body>
<script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
<script type="text/javascript" src="https://platform.twitter.com/widgets.js"></script>
<script async defer src="https://connect.facebook.net/ko_KR/sdk.js#xfbml=1&version=v3.2&appId=2128664037179612&autoLogAppEvents=1"></script>
<script src="/pub/answer/answerList/ShareSNS.js?ver=2.0"></script>
<script src="/Common/js/axios.min.js"></script>
<script>
	//TODO: Popup on/off + overlay on/off 함수 만들기
	function fautoURL(obj) {
		var urlRegex = /(((https?:\/\/)|(www\.))[^\s]+)/gi;
		var urlSpace = /(\s|\t|\r\n|\n|<br>|<br\/>|<br \/>|<\/p>)/gi;
		var myRegExp =/<(\/a|a)([^>]*)>/gi;
		obj.find('.prgContent_AR, .prgContent_QR, .prgContent_A, .prgContent_Q').each(function(index, item){

			var string = $(item).html();
			
			var temp;
			var temp2 = string;//string.replace(myRegExp , '');
			var url;
			var urlTag;
			var url2;
			var result2 = "";
			var x = 0;
			//console.log("temp2 : "+temp2);
			if (temp2.search(urlRegex) < 0) {
				//console.log("return item html"+string);
				$(item).html(string)
				return;
			}
			/*
			<a 태그는 skip 을 하고, 나머지는 while 문 안에서 후 처리
			*/

			while (temp2.search(urlRegex) >= 0 && x < 300){
				temp = temp2;
				temp3 = temp.substring(temp.search(urlRegex), temp.length);
				
				if (temp3.search(urlSpace) >= 0) {
					url = temp3.substring(0, temp3.search(urlSpace));
				} else {
					url = temp3;
				}
				//console.log('x : ' + x + ' / url : ' + url);
				result = temp.substring(0, temp.search(urlRegex) + url.length);
				temp2 = temp.substring(temp.search(urlRegex) + url.length, temp.length);
				//if(temp2.trim() == '</p>') { temp2 = ''; }
				
				if ( url.indexOf('http:') < 0 && url.indexOf('https:') < 0) {
					if (url.indexOf('www.') == 0 || url.indexOf('www.') < 0 ) {
						url2 = "http://" + url;
					}
				} else {
					url2 = url;
				}
				//console.log('result : ' + result);
				//console.log('url2 : ' + url2.contains('<a'));
				if(result.search('<a') < 0) {
					urlTag = "<a href='"+url2+"' target='_blank'>"+url+" </a>";
					result2 += result.replace(url, urlTag);
				}
				else {
					result2 += result;
				}
				//console.log("urlTag : "+urlTag);
				//console.log("result2 : "+result2);
				
				
				if (temp2.search(urlRegex) < 0 && temp2.length > 0) {
					result2 += temp2;
				}

				x++;
			}

			//console.log("item.result2 : "+result2);
			$(item).html(result2);
			
		});
	}
	
	function formatDateAsText(v) {
		//TODO: fautoURL 함수랑 같은 방식으로 변경하기
		var curDate = v;
		var nowDate, gap, gapSec, gapMin, gapHour, gapDay, gapMonth, gapYear;
		var nowDate = new Date();
		var curDate = new Date(curDate.replace(/-/g, '/'));

		gap 		= nowDate.getTime() - curDate.getTime();
		gapSec		= gap / 1000;
		gapMin		= gapSec / 60;
		gapHour 	= gapMin / 60;
		gapDay		= gapHour / 24;
		gapMonth 	= gapDay / 30
		gapYear 	= gapMonth / 12;

		if (gapHour <= 24) {
			if (Math.floor(gapMin) < 1) {
				if (Math.floor(gapSec) <= 0) {
					resultDate = '<%=CommonUtil.getLangMsg(request, "msg_0216")%>';
				} else {
					resultDate = Math.floor(gapSec) + '<%=CommonUtil.getLangMsg(request, "msg_0217")%>';
				}
			} else if (Math.floor(gapHour) < 1) {
				resultDate = Math.floor(gapMin) + '<%=CommonUtil.getLangMsg(request, "msg_0218")%>';
			} else {
				resultDate = Math.floor(gapHour) + '<%=CommonUtil.getLangMsg(request, "msg_0219")%>';
			}
		} else {
			if (gapYear >= 1) {
				resultDate = Math.floor(gapYear) + '<%=CommonUtil.getLangMsg(request, "msg_0220")%>';
			} else if (gapMonth >= 1) {
				resultDate = Math.floor(gapMonth) + '<%=CommonUtil.getLangMsg(request, "msg_0221")%>';
			} else {
				resultDate = Math.floor(gapDay) + '<%=CommonUtil.getLangMsg(request, "msg_0222")%>';
			}
		}
		return resultDate;
	}

	function formatDate(v) {
		//TODO: fautoURL 함수랑 같은 방식으로 변경하기
		var v = $("#" + v);
		var curDate = v.text();
		var nowDate, gap, gapSec, gapMin, gapHour, gapDay, gapMonth, gapYear;
		var nowDate = new Date();
		var curDate = new Date(curDate.replace(/-/g, '/'));

		gap 		= nowDate.getTime() - curDate.getTime();
		gapSec		= gap / 1000;
		gapMin		= gapSec / 60;
		gapHour 	= gapMin / 60;
		gapDay		= gapHour / 24;
		gapMonth 	= gapDay / 30
		gapYear 	= gapMonth / 12;

		if (gapHour <= 24) {
			if (Math.floor(gapMin) < 1) {
				if (Math.floor(gapSec) <= 0) {
					resultDate = '<%=CommonUtil.getLangMsg(request, "msg_0216")%>';
				} else {
					resultDate = Math.floor(gapSec) + '<%=CommonUtil.getLangMsg(request, "msg_0217")%>';
				}
			} else if (Math.floor(gapHour) < 1) {
				resultDate = Math.floor(gapMin) + '<%=CommonUtil.getLangMsg(request, "msg_0218")%>';
			} else {
				resultDate = Math.floor(gapHour) + '<%=CommonUtil.getLangMsg(request, "msg_0219")%>';
			}
		} else {
			if (gapYear >= 1) {
				resultDate = Math.floor(gapYear) + '<%=CommonUtil.getLangMsg(request, "msg_0220")%>';
			} else if (gapMonth >= 1) {
				resultDate = Math.floor(gapMonth) + '<%=CommonUtil.getLangMsg(request, "msg_0221")%>';
			} else {
				resultDate = Math.floor(gapDay) + '<%=CommonUtil.getLangMsg(request, "msg_0222")%>';
			}
		}
		v.text(resultDate);
	}
	
	function formatDateForSpan(v) {
		//TODO: fautoURL 함수랑 같은 방식으로 변경하기
		//console.log('v : ' + v);
		var v = $("#" + v);
		var curDate1 = v.find('span').text();
		var nowDate, gap, gapSec, gapMin, gapHour, gapDay, gapMonth, gapYear;
		var nowDate = new Date();
		var curDate = new Date(curDate1.replace(/-/g, '/'));

		gap 		= nowDate.getTime() - curDate.getTime();
		gapSec		= gap / 1000;
		gapMin		= gapSec / 60;
		gapHour 	= gapMin / 60;
		gapDay		= gapHour / 24;
		gapMonth 	= gapDay / 30
		gapYear 	= gapMonth / 12;

		if (gapHour <= 24) {
			if (Math.floor(gapMin) < 1) {
				if (Math.floor(gapSec) <= 0) {
					resultDate = '<%=CommonUtil.getLangMsg(request, "msg_0216")%>';
				} else {
					resultDate = Math.floor(gapSec) + '<%=CommonUtil.getLangMsg(request, "msg_0217")%>';
				}
			} else if (Math.floor(gapHour) < 1) {
				resultDate = Math.floor(gapMin) + '<%=CommonUtil.getLangMsg(request, "msg_0218")%>';
			} else {
				resultDate = Math.floor(gapHour) + '<%=CommonUtil.getLangMsg(request, "msg_0219")%>';
			}
		} else {
			if (gapYear >= 1) {
				resultDate = Math.floor(gapYear) + '<%=CommonUtil.getLangMsg(request, "msg_0220")%>';
			} else if (gapMonth >= 1) {
				resultDate = Math.floor(gapMonth) + '<%=CommonUtil.getLangMsg(request, "msg_0221")%>';
			} else {
				resultDate = Math.floor(gapDay) + '<%=CommonUtil.getLangMsg(request, "msg_0222")%>';
			}
		}
		v.html(resultDate + '<span>' + curDate1 + '</span>');
	}

	function fProfileAdd(el, id) {
		var target = $(el).parents('.sub_answer').find('.profileData').find('p');

		// 2020. 01. 29 오명훈 메시지 보내기 기능 코드 추가
		const nickName = target.data('nick');
		const memberSeq = target.data('aseq');

		$('#profileBoxA').find('.qmark_nickname').text(target.data('nick'));
		$('#profileBoxA').find('.qmark_Lv > span').text(target.data('lv'));
		$('#profileBoxA').find('.info_intro').text(target.data('intro'));
		$('#profileBoxA').find('.answer_Qmoney > span').text(target.data('qmoney'));
		$('#profileBoxA').find('.answer_Amoney > span').text(target.data('amoney'));
		$('#profileBoxA').find('.answer_countC > span').text(target.data('countc'));
		$('#profileBoxA').find('.answer_rateChoice > span').text(target.data('ratec'));
		$('#profileBoxA').find('.qmark_direct_btn').attr('onClick',target.data('btn'));
		$('#profileBoxA').find('.qmark_EarnTotal > span').text(target.data('earntotal'));
		$('#profileBoxA').find('#box_top_btn1 > img').attr('onClick',target.data('addfriends'));
		$('#profileBoxA').find('#box_top_btn2 > img').attr('onClick',target.data('addmento'));
		$('#profileBoxA').find('#box_top_btn3 > img').attr('class', 'openMessage');

		// 2020. 01. 29 오명훈 메시지 보내기 기능 코드 추가
		$('#profileBoxA').find('.openMessage').attr('nickName', nickName);
		$('#profileBoxA').find('.openMessage').attr('memberSeq', memberSeq);
		//

		
		$(el).parent('.sub_answer_top').before($('#anp_' + id));

		fProfileMore(el, id);
	}

	var profileAnswerOn;

	function fProfileMore(el, id) {
		var base = $(el);
		var overlay = $('.profile_overlay');
		
		var profileBox = base.parents('.atm_av_el').find('.atm_icon_qmark_box');
		if (id != undefined) {
			profileBox = $('#anp_' + id);//$('#profileBoxA');
			profileAnswerOn = true;
		}

		if (!el) {
			//overlay 클릭시
			var base = $('.atm_icon_qmark_wrap.on');
			overlay.fadeOut('fast').removeAttr('onClick');
			base.find('.atm_profile_grade').fadeIn('fast');

			if (profileAnswerOn == true) {
				$('#profileBoxA').fadeOut('fast');
				profileAnswerOn = false;
			} else {
				$('.atm_av_el').find('.atm_icon_qmark_box').fadeOut('fast');
			}

			if (base.find('.atm_icon_amark.atm_icon_qmark')) {
				base.find('.atm_icon_amark.atm_icon_qmark').find('.atm_amark_border').fadeIn('fast');
				setTimeout(function(){
					base.find('.atm_icon_amark.atm_icon_qmark').removeClass('atm_icon_qmark');
				},150);
			} 

			setTimeout(function(){
				base.removeClass('on');
			},150);
			
		} else {
			if (profileBox.css('display') == 'block') {

				if (base.find('.atm_icon_amark')) {
					base.find('.atm_icon_amark').find('.atm_amark_border').fadeIn('fast');
					setTimeout(function(){
						base.find('.atm_icon_amark').removeClass('atm_icon_qmark');
					},150);
					profileAnswerOn = false;
				}

				profileBox.fadeOut('fast');
				overlay.fadeOut('fast').removeAttr('onClick');
				base.find('.atm_profile_grade').fadeIn('fast');
				setTimeout(function(){
					base.removeClass('on');
				},150);
			} else {
				
				//if (base.find('.atm_icon_amark')) {
				//	base.find('.atm_icon_amark').addClass('atm_icon_qmark');
				//	base.find('.atm_icon_amark').find('.atm_amark_border').hide();
				//}
				
				//base.find('.atm_profile_grade').fadeOut('fast');
				//base.addClass('on');
				//profileBox.fadeIn('fast');
				
				profileBox.fadeOut('fast');
		        
				overlay.fadeIn('fast').attr('onClick','fProfileMore()');
				
			}
		} 
			
	}

	function fAjax_answer(url, frm, param) {
		$(".more_btn_list").css("display","none");
		var flag = false;
		if(param == 'ACT=ExtraAlmoney') {
			var pay = $('form[name="frmExtra"] input[name="ExtraAlmoney"]').val();
			
			// async : true(기본:비동기), false:동기
			$.ajax({
				type: 'post',
				url: '/common/getChkUseAlmoney',
				data: { 'act' : 'E', 'pay' : pay },
				dataType: 'json',
				async: false,
				crossDomain: true,
				success: function (r) {
					switch (r.result) {
						case 'over':
							alert(r.arr.userNick + getLangStr("jsm_0053") + '\n' + getLangStr("jsm_0054") + ' ' + r.arr.availPay.toLocaleString() + getLangStr("jsm_0055"));
							
							break;
						default:
							//처리
							var availpay = r.arr.availPay.toLocaleString();
							$('#hunhunal_num').text(availpay);
							
							flag = true;
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
			flag = true;
		}
		
		if(flag == true) {
			if (document.xhr) {
				$('#Tip').text(getLangStr("jsm_0031")).css('display', 'block');
				setTimeout(function () { $('#Tip').stop().fadeOut(300) }, 3000);
				return;
			}
			
			var data = (frm ? $('form[name=' + frm + ']').serialize() + '&' : '') + param;
			if(frm == 'frm_msgSend') {
				if($('#sndMsg').val() == '') {
					return;
				}
				
				data = 'MemberSeq=' + $('#recvSeq').val() + '&Contents=' + $('#sndMsg').val() + '&' + param;
			}
			
			document.xhr = $.ajax({
				type: 'post',
				url: url,
				data: data,
				dataType: 'json',
				crossDomain: true,
				success: function (r) {
					//console.log('r.result : ' + r.result);
					//if (r.arr[0].msg) alert(r.arr[0].msg);
	
					switch (r.result) {
						case 'SEND':
							alert(getLangStr("jsm_0042"));
							$('.message_cancle').click();
							break;
						case 'CheckSiren':
							$('#atm_gray .profile_overlay').fadeIn().attr('onClick','fsubmitReport()');
							$('.submit_report_wrap').fadeIn();
							$('.more_btn_list').fadeIn();
							$('.submit_report').fadeIn();
							break;
						case 'RegSiren':
							fsubmitReport();
							break;
						case 'GoTopQuestion':
							location = '/answer/questionList';
							break;
						case 'ExtraAlmoney':
							fAddAlmoney();
							var base;
							//console.log('r.arr : '+r.arr[0].ContentsSeq);
							if (r.arr[0].ticketReplChk=="Y"){
								var msg = getLangStr("jsm_0056") + '\n\n' + getLangStr("jsm_0057") + '\n' + getLangStr("jsm_0058") + '\n' + getLangStr("jsm_0059");
								if(confirm(msg)) {
									location.href = '/roulette/game';
								}
							}
							if (r.arr[0].Type == "A") {
								base = $('#ansList_'+ r.arr[0].ContentsSeq);
								var param = base.find('.answer_choice').attr('value').split('on');
	
								if ($('#replyWrapper_' + param[0]).css('display') != 'block') {
									reply($(base.find('.atm_question_bottom').find('.atm_viewbtnG_c1'))[0], 'replyWrapper_' + param[0] );
								} else {
									var res = $('#replyWrapper_' + param[0]).find('textarea').offset().top - window.innerHeight + $('#replyWrapper_' + param[0]).find('textarea').height() + 130;
									if ($(document).scrollTop() < res) {
										setTimeout(function(){
											$('html,body').stop().animate({scrollTop : res}, function(){$('#replyWrapper_' + param[0]).find('textarea').focus()});//.focus()
										}, 100);
									} else {
										$('#replyWrapper_' + param[0]).find('textarea');//.focus()
									}
									$('#replyWrapper_' + param[0]).find('.reply_renew').find('p').trigger('click');
								}
								$('#addAlmoneyFlag').attr('onclick','fShowExtraAlmoneyList(this, "'+ r.arr[0].contentsSeq +'")');
	
							} else if (r.arr[0].Type == "Q") {
								//base = $('input[name="QuestionSeq"]').parents('.atm_av_el');
								base = $('#addAlmoneyFlag');
	
								base.find('.atm_question_bottom').find('.atm_viewbtnG_c1').addClass('on');
	
								if ($('#replydiv').css('display') != 'block') {
									reply($(base.find('.atm_question_bottom').find('.atm_viewbtnG_c1'))[0], 'replydiv');
								}
	
								fViewQReply(r.arr[0].ContentsSeq, 'Q');
								
								$('#replyDiv').find('.replydiv_el').find('.reply_renew').trigger('click');
								
								$('#replyDiv').siblings('.answer_reply_btn').find('.reply').find('a').trigger('click');
	
								$('#addAlmoneyFlag').attr('onclick','fShowExtraAlmoneyList(this)');
							}
	
							if (base.find('.choice_wrap').length <= 0 || base.find('.atm_top_header').length <= 0){
								if (base.find('.choice_wrap').length <= 0) {
									// 훈훈알 영역자체가 없을때
									if (r.arr[0].Type == "Q") {
										base.prepend('<div class="choice_wrap addAlmoney" id="addAlmoneyList" onclick="fShowExtraAlmoneyList(this)"></div>');
										base.find('#addAlmoneyList').append('<img src="/pub/answer/answerList/images/answer_almoney.svg" alt="<%=msg_0230%>"><span></span><ul></ul>');
										base.find('#addAlmoneyList').find('span').text(numberWithCommas(r.arr[0].sumExtraAlmoney * 1));
									}
									else {
										base.prepend('<div class="choice_wrap addAlmoney" id="ans_' + r.arr[0].ContentsSeq + '" onclick="fShowExtraAlmoneyList(this, \'' + r.arr[0].ContentsSeq + '\')"></div>');
										base.find('#ans_' + r.arr[0].ContentsSeq).append('<img src="/pub/answer/answerList/images/answer_almoney.svg" alt="<%=msg_0230%>"><span></span><ul></ul>');
										base.find('#ans_' + r.arr[0].ContentsSeq).find('span').text(numberWithCommas(r.arr[0].sumExtraAlmoney * 1));
									}
								}
								
								//base.find('.choice_wrap').append($('#addAlmoneyFlag').clone());
								//base.find('.atm_top_header').css('display','inline-block').removeAttr('id');
							}
							if (r.arr[0].Type == "Q") {
								base.find('#addAlmoneyList').find('span').text(numberWithCommas(r.arr[0].sumExtraAlmoney * 1));
							}
							else {
								base.find('.choice_wrap').find('span').text(numberWithCommas(r.arr[0].sumExtraAlmoney * 1));
							}
							break;
						case 'tExtraAlmoney':
							alert(getLangStr("jsm_0060"));
							break;
						case 'ExtraAlmoney_focus':
							alert(r.arr[0].msg);
							$('form[name="frmExtra"] input[name="ExtraAlmoney"]').focus().select();
							break;
	                    case 'ExtraAlmoneyList':
	                        var Almoney;
	                        var sumExtraAlmoney = 0;
							//console.log('r.userSeq : ' + r.arr.length);
							
							if(r.arr.length == 0) {
								return;
							}
							
							
							for (var i = r.arr.length-1; i >= 0; i--) {
								for (var j = i-1; j >= 0; j--) {
									if (r.arr[i].userSeq == r.arr[j].userSeq) {
										r.arr[j].almoney = r.arr[j].almoney * 1 + r.arr[i].almoney * 1;
										r.arr.splice(i, 1);
										break;
									}
								}
							}
	
							r.arr.sort(function(a, b) {	return b.almoney - a.almoney; })
							
							var divId = 'addAlmoneyList';
							
							if($('form[name="frmExtra"]').find('input[name="AnswerSeq"]').val() != '0') {
								var id = $('form[name="frmExtra"]').find('input[name="AnswerSeq"]').val();
								//divId = $('#ans_'+id);
								divId = 'ans_'+id;
							}
							//console.log('divId : ' + divId);
							$('#' + divId).find('ul').empty();
	
							if(r.arr.length > 0) {
								$('#' + divId).find('ul').append('<li>${msg_0235}</li>');
							}
	                        for (var i = 0; i < r.arr.length; i++)
	                        {
	                            Almoney = r.arr[i].almoney * 1;
	                            sumExtraAlmoney += Almoney;
								var list = '<li><a href="/member/otherProfileInfo?MemberSeq='+r.arr[i].userSeq+'"><b>'
									+ r.arr[i].nickname + ' (' + numberWithCommas(Almoney) + getLangStr("jsm_0003") + ')</b></a>'+ formatDateAsText(r.arr[i].conDate) +'</li>'
								
								$('#' + divId).find('ul').append(list);
							}
	
							$('#' + divId).find('li').each(function(index, item){
								//formatDate($(item).find('span').attr('id'));
							});
							
							//console.log($('#addAlmoneyList'));
	                        $(document.ExtraAlmoney).after($('#' + divId));
							//$(document.ExtraAlmoney).addClass('layerOn');
							$(document.ExtraAlmoney).find('span').text(numberWithCommas(sumExtraAlmoney));
							//console.log(divId + ': ' +  $('#' + divId).html());
							$('#' + divId).find('ul').stop().toggle();
							if($('form[name="frmExtra"]').find('input[name="AnswerSeq"]').val() != '0') {
								$('form[name="frmExtra"]').find('input[name="AnswerSeq"]').val('');
							}
							event.stopPropagation();
	                        break;
	                    case 'tExtraAlmoneyList':
	                    	var Almoney;
	                        var sumExtraAlmoney = 0;
	                        var nation;
							//console.log('r.arr.length : ' + r.arr.length);
							
							if(r.arr.length == 0) {
								return;
							}
							
							
							for (var i = r.arr.length-1; i >= 0; i--) {
								for (var j = i-1; j >= 0; j--) {
									if (r.arr[i].userSeq == r.arr[j].userSeq) {
										r.arr[j].almoney = r.arr[j].almoney * 1 + r.arr[i].almoney * 1;
										r.arr.splice(i, 1);
										break;
									}
								}
							}
	
							r.arr.sort(function(a, b) {	return b.almoney - a.almoney; })
							
							var divId = 'addAlmoneyList';
							
							if($('form[name="frmExtraT"]').find('input[name="tSeq"]').val() != '0') {
								var id = $('form[name="frmExtraT"]').find('input[name="tSeq"]').val();
								//divId = $('#ans_'+id);
								divId = 't_'+id;
							}
							//console.log('divId : ' + divId);
							$('#' + divId).parent().find('dl').empty();
	
							if(r.arr.length > 0) {
								$('#' + divId).parent().find('dl').append('<dt>${msg_0235}</dt>');
							}
	                        for (var i = 0; i < r.arr.length; i++)
	                        {
	                        	//<img src="/Common/images/nation/us.svg" alt="">
	                            Almoney = r.arr[i].almoney * 1;
	                            sumExtraAlmoney += Almoney;
	                            nation = r.arr[i].nation;
	                            //<dd><img src="/Common/images/nation/us.svg" alt=""><b>영웅본색 (10,000알) </b>8개월 전</dd>
								var list = '<dd><a href="/member/otherProfileInfo?MemberSeq='+r.arr[i].userSeq+'"><img src="/Common/images/nation/' + nation + '.svg" alt=""><b>'
									+ r.arr[i].nickname + ' (' + numberWithCommas(Almoney) + getLangStr("jsm_0003") + ')</b></a>'+ formatDateAsText(r.arr[i].conDate) +'</dd>'
								
								$('#' + divId).parent().find('dl').append(list);
							}
	
							$('#' + divId).parent().find('dd').each(function(index, item){
								//formatDate($(item).find('span').attr('id'));
							});
							
							//console.log($('#addAlmoneyList'));
	                        $(document.ExtraAlmoney).after($('#' + divId));
							//$(document.ExtraAlmoney).addClass('layerOn');
							$(document.ExtraAlmoney).find('span').text(numberWithCommas(sumExtraAlmoney));
							//console.log(divId + ': ' +  $('#' + divId).html());
							$('#' + divId).parent().find('dl').stop().toggle();
							if($('form[name="frmExtraT"]').find('input[name="tSeq"]').val() != '0') {
								$('form[name="frmExtraT"]').find('input[name="tSeq"]').val('');
							}
							event.stopPropagation();
	                        break;
						default:
							if (r.result) alert(r.result);
							break;
					}
				},
				error: function (r, textStatus, err) {
					if (r.responseText && r.responseText.substr(0, 13) == '<!--LOGOFF-->') { top.location = '/'; return; } //http://www.altong.com/
					console.log(r);
				},
				complete: function () {
					document.xhr = false;
				}
			});
		
		}
	}

	function numberWithCommas(x) {
		return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}

	function fShowExtraAlmoneyList(obj, seq)
	{
		if (seq) {
			$('form[name="frmExtra"]').find('input[name="AnswerSeq"]').val(seq);
		} else {
			$('form[name="frmExtra"]').find('input[name="AnswerSeq"]').val('0');
		}

		document.ExtraAlmoney = obj;

		fAjax_answer('/answer/extraAlmoney', 'frmExtra', 'ACT=ExtraAlmoneyList');
	}
	
	// tType - 1:기계번역, 2:사람번역
	function ftShowExtraAlmoneyList(obj, seq)
	{
		//console.log('start!!!');
		$('form[name="frmExtraT"]').find('input[name="tSeq"]').val(seq);

		document.ExtraAlmoney = obj;

		fAjax_answer('/answer/extraAlmoney', 'frmExtraT', 'ACT=tExtraAlmoneyList');
	}
	
	function ftShowExtraAlmoneyList_new(obj, seq, trnType)
	{
		var qt_seq = $('#' + trnType + '_' + seq).val();
		//console.log('qt_seq : ' + qt_seq);
		$('form[name="frmExtraT"]').find('input[name="tSeq"]').val(qt_seq);

		document.ExtraAlmoney = obj;

		fAjax_answer('/answer/extraAlmoney', 'frmExtraT', 'ACT=tExtraAlmoneyList');
	}

	function fshareSNS(target, type, seq) {
		console.log('fshareSNS')
		var popup = $('.shareWrap');
		var overlay = $('#atm_gray > .overlay');

		if (popup.css('display') == 'block') {
			overlay.fadeOut('fast').removeAttr('onClick');
			popup.fadeOut('fast');
		} else {
			popup.attr('data-type', type);
			popup.attr('data-seq', seq);
			//$(target).closest('.atm_av_el').prepend(popup);
			//overlay.fadeIn().attr('onClick','fshareSNS()');
			//popup.fadeIn();

	        $('.more_btn_list').stop().fadeIn();
	        $('.shareWrap').stop().fadeIn();
		}
	}
	
	function goQuestionVote_new(act, seq, contentType, reqType) {
		//console.log('seq : ' + seq);
		var contentSeq = $('#' + reqType + '_' + seq).val();
		//console.log('contentSeq : ' + contentSeq);
		
		$.ajax({
			type: 'post',
			url: '/common/questionVote',
			data: { 'act' : act, 'contentSeq' : contentSeq, 'contentType' : contentType },
			dataType: 'json',
			crossDomain: true,
			success: function (r) {
				//console.log('r.result : ' + r.result);
				switch (r.result) {
					case 'true':
						var good = numberWithCommas(r.arr.good);
						var bad = numberWithCommas(r.arr.bad);
						
						if(contentType == 'Q') {
							$('#queVoteG').text(good);
							$('#queVoteB').text(bad);
						}
						if(contentType == 'A') {
							$('#ansVoteG_' + seq).text(good);
							$('#ansVoteB_' + seq).text(bad);
						}
						else {
							if(reqType == 'qt') {	// 질문 번역
								$('#lang_good_' + seq).text(good);
								$('#lang_bad_' + seq).text(bad);
							}
							else if(reqType == 'at') { // 답글 번역
								$('#at_lang_good_' + seq).text(good);
								$('#at_lang_bad_' + seq).text(bad);
							}
							else if(reqType == 'rt') {	// 댓글 번역
								$('#rt_lang_good_' + seq).text(good);
								$('#rt_lang_bad_' + seq).text(bad);
							}
						}
						
						break;
					case 'false':
						alert(getLangStr("jsm_0020"));
						location.href="/default/login";
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
	
	function goQuestionVote(act, contentSeq, contentType) {
		$.ajax({
			type: 'post',
			url: '/common/questionVote',
			data: { 'act' : act, 'contentSeq' : contentSeq, 'contentType' : contentType },
			dataType: 'json',
			crossDomain: true,
			success: function (r) {
				//console.log('r.result : ' + r.result);
				switch (r.result) {
					case 'true':
						var good = numberWithCommas(r.arr.good);
						var bad = numberWithCommas(r.arr.bad);
						
						if(contentType == 'Q') {
							$('#queVoteG').text(good);
							$('#queVoteB').text(bad);
						}
						else if(contentType == 'A') {
							$('#ansVoteG_' + contentSeq).text(good);
							$('#ansVoteB_' + contentSeq).text(bad);
						}
						else {
							$('#lang_good_' + contentSeq).text(good);
							$('#lang_bad_' + contentSeq).text(bad);
						}
						
						break;
					case 'false':
						alert(getLangStr("jsm_0020"));
						location.href="/default/login";
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
</script>
<script>
function checkDevice(){
	// 접속한 디바이스가 모바일이면 True, 아니면 False를 반환합니다.
	const mobileKeyWords = new Array('Android', 'iPhone', 'iPod', 'BlackBerry', 'Windows CE', 'SAMSUNG', 'LG', 'MOT', 'SonyEricsson');
	for(let info in mobileKeyWords){
		if(navigator.userAgent.match(mobileKeyWords[info]) != null){
			console.log(info);
			return true;
		}
	}
	return false;
}

function checkDeviceSize()
{
	let msgDeviceWidth = 0;
	const mobileDevice = checkDevice();
	if(mobileDevice){
		// 모바일로 접속했을 때 메시지 창의 크기를 지정해줍니다.
		msgDeviceWidth = 100 + "%";
	} else {
		// 모바일 환경이 아닌 상태에서 메시지 창의 크기를 지정해줍니다.
		msgDeviceWidth = 400 + "px";
	}

	const msg= document.querySelector(".user_message");
	msg.style.width = msgDeviceWidth;
	if(msg.offsetWidth < 400){
		msg.style.width = 90 + "%";
	}
	const msgWidth  = msg.offsetWidth;
	const msgHeight = msg.offsetHeight;
	const deviceWidth = window.innerWidth;
	const deviceHeight= window.innerHeight;

	msg.style.top = (deviceHeight-msgHeight)/2 + "px";
	msg.style.left= (deviceWidth-msgWidth)/2 + "px";

}
// 2020. 01. 29 오명훈 메시지 보내기 기능 코드 추가
function openMessageWindow(event){
	// 2020. 01. 29 오명훈 메시지 보내기 기능 코드 추가
	const attributes = event.target.attributes;
	
	var userSeq = '<%=UserSeq%>';
	
	if(userSeq != '0' && attributes['nickname'].value != getLangStr("jsm_0063")) {
		$('.more_btn_list').stop().fadeIn();
		$('.user_message').stop().fadeIn();
		// 2020. 01. 29 오명훈 메시지 보내기 기능 코드 추가
		$(".message_usertext").val('');
		$(".message_username").text(attributes['nickname'].value);
		//$("form").find("input").attr('value', attributes['memberseq'].value);
		$('#recvSeq').val(attributes['memberseq'].value);

		//checkDeviceSize();
	}
}
//
$(document).ready(function(){
	$(".openMessage").click(function(event){
		// 2020. 01. 29 오명훈 메시지 보내기 기능 코드 수정
		var userSeq = '<%=UserSeq%>';
		if(userSeq != '0') {
			openMessageWindow(event);
		}
		else {
			alert(getLangStr("jsm_0020"));
			return false;
		}
	});

	$(".message_cancle").click(function(event){
		$(".user_message").css("display","none");
		$(".more_btn_list").css("display","none");
	});
	
	$(".sub_answer").click(function() {
		var disp = $(this).find('.answer_slide').css('display');
		//console.log('.answer_slide display : ' + disp);
		
		if(disp == 'block') {
			$(this).find('#overlay').stop().fadeOut();
			$(this).find('.answer_tab').stop().hide();
			$(this).find('.addpop').fadeOut();
		}
	});
	//$(window).resize(checkDeviceSize);
	
	var ticketQueChk = '${ticketQueChk}';
	var ticketAnsChk = '${ticketAnsChk}';
	//console.log('ticketQueChk : ' + ticketQueChk);
	//console.log('ticketAnsChk : ' + ticketAnsChk);
	
	if(ticketQueChk == 'Y' || ticketAnsChk == 'Y') {
		if(confirm(getLangStr("jsm_0056") + '\n\n' + getLangStr("jsm_0057") + '\n' + getLangStr("jsm_0058") + '\n' +getLangStr("jsm_0059"))) {
			location.href = '/roulette/game';
		}	
	}
	
	/* 번역에 대한 평가 */
	$('.lang_assessment .lang_good').click(function(){
	    $(this).find('img').attr('src', '/Common/images/smile_on.svg');
	    $(this).parents('.lang_assessment').find('.lang_bad > img').attr('src', '/Common/images/sad.svg');
	    $(this).find('span').css({color:'#ff3300'});
	    $(this).parents('.lang_assessment').find('.lang_bad > span').css({color:'#999999'});
	});
	$('.lang_assessment .lang_bad').click(function(){
	    $(this).find('img').attr('src', '/Common/images/sad_on.svg');
	    $(this).parents('.lang_assessment').find('.lang_good > img').attr('src', '/Common/images/smile.svg');
	    $(this).find('span').css({color:'#ff3300'});
	    $(this).parents('.lang_assessment').find('.lang_good > span').css({color:'#999999'});
	});
	
	$('.answer_reply_btn li .smile_icon').click(function(){
        $(this).parents('li').find('.sad_icon b').css({color:'#999999'});
        $(this).find('b').css({color:'#fd0031'});
        $(this).parents('li').find('.sad_icon').find('img').attr('src', '/pub/answer/answerList/images/sad.svg');
        $(this).find('img').attr('src', '/pub/answer/answerList/images/smile_red.svg');
    });
    $('.answer_reply_btn li .sad_icon').click(function(){
        $(this).parents('li').find('.smile_icon b').css({color:'#999999'});
        $(this).find('b').css({color:'#fd0031'});
        $(this).parents('li').find('.smile_icon').find('img').attr('src', '/pub/answer/answerList/images/smile.svg');
        $(this).find('img').attr('src', '/pub/answer/answerList/images/sad_red.svg');
    });
});


$.fn.selectRange = function(start, end) {

    return this.each(function() {

         if(this.setSelectionRange) {

             this.focus();

             this.setSelectionRange(start, end);

         } else if(this.createTextRange) {

             var range = this.createTextRange();

             range.collapse(true);

             range.moveEnd('character', end);

             range.moveStart('character', start);

             range.select();

         }

     });

 };

function replyQueRegi(nick) {
	var nickStr = '[' + nick + ' ' + getLangStr("jsm_0061") + '] ';
	
	$('#replyDiv').find('textarea').text('');
	$('#replyDiv').find('textarea').text(nickStr);
	$('#replyDiv').find('textarea').selectRange(nickStr.length, nickStr.length);
}

function replyAnsRegi(id, nick) {
	var nickStr = '[' + nick + ' ' + getLangStr("jsm_0061")+ '] ';
	
	$('#replyWrapper_' + id).find('textarea').text('');
	$('#replyWrapper_' + id).find('textarea').text(nickStr);
	$('#replyWrapper_' + id).find('textarea').selectRange(nickStr.length, nickStr.length);
}

function goAnswer(seq, contentType) {
	//if(contentType == 'qt') {
	//	location.href = '/answer/answerList?Seq=' + seq + '&amp;CurPageName=/answer/questionList&Section1=0&src_Sort=Seq&src_OrderBy=DESC';
	//}
	//else {
		var type = 'Q';
		if(contentType == 'at') { type = 'A'; }
		else if(contentType == 'rq') { type = 'RQ'; }
		else if(contentType == 'ra') { type = 'RA'; }
		//console.log('type : ' + type);
		$.ajax({
			type: 'post',
			url: '/common/getSingleConents',
			data: { 'contentSeq' : seq, 'contentType' : type },
			dataType: 'json',
			crossDomain: true,
			success: function (r) {
				var tContents = r.arr.contents;
				//console.log('contentType : ' + contentType);
				//console.log('r.result : ' + r.result);
				//console.log('tContents : ' + tContents);
				switch (r.result) {
					case 'true':
						if(contentType == 'qt') {
							var tTitle = r.arr.title;
							tTitle = tTitle.replace(/(<([^>]+)>)/ig,"");
							
							$('#lang_que_' + seq).css('display', 'none');
							$('#lang_view_que_' + seq).css('display', 'none');
							
							$('#tit_' + seq).text(tTitle);
							$('.prgContent_Q').html(tContents);
							
							$('#qT_lang_ai').removeClass('pick');
							$('#qT_lang_origin' + seq).addClass('pick');
						}
						else if(contentType == 'at') { // 답글
							$('#lang_ans_' + seq).css('display', 'none');
							$('#lang_view_ans_' + seq).css('display', 'none');
							$('#ansT_' + seq).html(tContents);
							
							$('#ansT_ai_' + seq).removeClass('pick');
							$('#ansT_origin_' + seq).addClass('pick');
						}
						else if(contentType == 'rq'){ // 댓글
							$('#rq_lang_report_' + seq).css('display', 'none');
							$('#rq_repl_' + seq).html(tContents);
							
							//$('#ansT_ai_' + seq).removeClass('pick');
							//$('#ansT_origin_' + seq).addClass('pick');
						}
						else if(contentType == 'ra'){ // 댓글
							$('#ra_lang_report_' + seq).css('display', 'none');
							$('#ra_repl_' + seq).html(tContents);
							
							//$('#ansT_ai_' + seq).removeClass('pick');
							//$('#ansT_origin_' + seq).addClass('pick');
						}
						
						break;
					case 'false':
						//alert(getLangStr("jsm_0020"));
						//location.href="/default/login";
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
	//}
}

function goTranslate(contentSeq, contentType) {
	
	var lv = '${Lv}';
	var memberSeq;
	var tMaxSiren = '${maxSiren}';
	
	if(contentType == 'Q') {
		memberSeq = '${MemberSeq}';
	}
	else {
		memberSeq = '${AnswerSeq}';
	}
	
	var type = contentType;
	
	
	if(contentType == 'B') {
		type = "A";	
	}
	
	$.ajax({
		type: 'post',
		url: '/translate/trans',
		data: { 'contentSeq' : contentSeq, 'contentType' : type },
		dataType: 'json',
		crossDomain: true,
		success: function (r) {
			//console.log('r.result : ' + r.result);
			switch (r.result) {
				case 'Y':
					var tSeq = r.arr.tSeq;
					var tContents = r.arr.tContents;
					
					if(contentType == 'Q') {
						var tTitle = r.arr.tTitle;
						tTitle = tTitle.replace(/(<([^>]+)>)/ig,"");
						
						$('#tit_' + contentSeq).html(tTitle);
						$('.prgContent_Q').html(tContents);
						$('#qt_' + contentSeq).val(tSeq);
						
						var good = numberWithCommas(r.arr.qtGood);
						var bad = numberWithCommas(r.arr.qtBad);
						
						var count = numberWithCommas(r.arr.tCount);
						var extraAlmoney = numberWithCommas(r.arr.tExtraAlmoney);
						var qtUserSeq = r.arr.tUserSeq;
						var qtUserLv = r.arr.tUserLv;
						var qtSirenN = r.arr.tSirenN;
						
						
						$('#lang_good_' + contentSeq).text(good);
						$('#lang_bad_' + contentSeq).text(bad);
						
						$('#qt_count_' + contentSeq).text(count);
						$('#qt_extraAl_' + contentSeq).text(extraAlmoney);
						
						$('#lang_que_' + contentSeq).css('display', 'block');
						
						
						if(parseInt(lv) < 99 && qtUserSeq != memberSeq && qtUserSeq < 90 && parseInt(qtSirenN) < parseInt(tMaxSiren)) {
							$('#lang_report_' + contentSeq).css('display', 'block');
						}
						else {
							$('#lang_report_' + contentSeq).css('display', 'none');
						}
					}
					else if(contentType == 'A') {
						//answerList 에서 상세 구현
						$('#at_' + contentSeq).val(tSeq);
						$('#ansT_' + contentSeq).html(tContents);
						
						var good = numberWithCommas(r.arr.qtGood);
						var bad = numberWithCommas(r.arr.qtBad);
						
						var count = numberWithCommas(r.arr.tCount);
						var extraAlmoney = numberWithCommas(r.arr.tExtraAlmoney);
						var qtUserSeq = r.arr.tUserSeq;
						var qtUserLv = r.arr.tUserLv;
						var qtSirenN = r.arr.tSirenN;
						
						$('#at_lang_good_' + contentSeq).text(good);
						$('#at_lang_bad_' + contentSeq).text(bad);
						
						$('#at_count_' + contentSeq).text(count);
						$('#at_extraAl_' + contentSeq).text(extraAlmoney);
						
						$('#lang_ans_' + contentSeq).css('display', 'block');
						$('#lang_view_' + contentSeq).css('display', 'block');
						
						$('#ansT_origin_' + contentSeq).removeClass('pick');
						$('#ansT_ai_' + contentSeq).addClass('pick');
						$('#lang_view_ans_' + contentSeq).css('display', 'block');
						
						
						if(parseInt(lv) < 99 && qtUserSeq != memberSeq && qtUserSeq < 90 && parseInt(qtSirenN) < parseInt(tMaxSiren)) {
							$('#at_lang_report_' + contentSeq).css('display', 'block');
						}
						else {
							$('#at_lang_report_' + contentSeq).css('display', 'none');
						}
					}
					else if(contentType == 'B') { // 답변 목록 미리 보기 상태에서 기계 번역
						//answerList 에서 상세 구현
						tContents.replace(/(<([^>]+)>)/ig,"");
						if(tContents.length > 200) {
							tContents = tContents.substring(0, 200) + '...';
						}
						
						$('#bAnst_' + contentSeq).val(tSeq);
						$('#bAns_' + contentSeq).html(tContents);
					}
					
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

function goTranslate_ans(contentSeq, contentType) {
	var trn_chk;
	
	if(contentType == 'R') {
		trn_chk = $('#bAnst_' + contentSeq).val();
	}
	else {
		trn_chk = $('#bAnst_' + contentSeq).val();
	}
	if(contentType == 'B') {
		type = "A";	
	}
	
	if(trn_chk == '0' || trn_chk == '') {
		$.ajax({
			type: 'post',
			url: '/translate/trans',
			data: { 'contentSeq' : contentSeq, 'contentType' : type },
			dataType: 'json',
			crossDomain: true,
			success: function (r) {
				//console.log('r.result : ' + r.result);
				switch (r.result) {
					case 'Y':
						var tSeq = r.arr.tSeq;
						var tContents = r.arr.tContents;
						
						tContents = tContents.replace(/(<([^>]+)>)/ig,"");
						
						$('#txt_length_' + contentSeq).text(tContents.length);
						
						if(tContents.length > 60) {
							tContents = tContents.substring(0, 60) + '...';
						}
						
						$('#bAnst_' + contentSeq).val(tSeq);
						$('#bAns_' + contentSeq).html(tContents);
						
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
	else {
		$.ajax({
			type: 'post',
			url: '/translate/getAnsOrgTitle',
			data: { 'contentSeq' : contentSeq, 'contentType' : contentType },
			dataType: 'json',
			crossDomain: true,
			success: function (r) {
				//console.log('r.result : ' + r.result);
				switch (r.result) {
					case 'Y':
						var tContents = r.arr.tContents;
						
						tContents = tContents.replace(/(<([^>]+)>)/ig,"");
						
						$('#txt_length_' + contentSeq).text(tContents.length);
						
						if(tContents.length > 60) {
							tContents = tContents.substring(0, 60) + '...';
						}
						
						$('#bAnst_' + contentSeq).val('0');
						$('#bAns_' + contentSeq).html(tContents);
						
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
}

function goTranslate_qr_ar(contentSeq, contentType) {
	var trn_chk;
	
	if(contentType == 'R') {
		trn_chk = $('#tq_reply_trn_' + contentSeq).val();
	}
	else {
		trn_chk = $('#ta_reply_trn_' + contentSeq).val();
	}
	
	if(trn_chk == 'N') {
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
						var tContents = r.arr.tContents;
						
						if(contentType == 'R'){
							$('#tq_reply_trn_' + contentSeq).val('Y');
							$('#tq_reply_' + contentSeq).html(tContents);
						}
						else if(contentType == 'S'){
							$('#ta_reply_trn_' + contentSeq).val('Y');
							$('#ta_reply_' + contentSeq).html(tContents);
						}
						
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
	else {
		$.ajax({
			type: 'post',
			url: '/translate/getReplyOrgTitle',
			data: { 'contentSeq' : contentSeq, 'contentType' : contentType },
			dataType: 'json',
			crossDomain: true,
			success: function (r) {
				//console.log('r.result : ' + r.result);
				switch (r.result) {
					case 'Y':
						var tContents = r.arr.tContents;
						
						if(contentType == 'R'){
							$('#tq_reply_trn_' + contentSeq).val('N');
							$('#tq_reply_' + contentSeq).html(tContents);
						}
						else if(contentType == 'S'){
							$('#ta_reply_trn_' + contentSeq).val('N');
							$('#ta_reply_' + contentSeq).html(tContents);
						}
						
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
}
</script>

	<div class="overlay profile_overlay" onclick="fProfileMore()"></div>
    <%@ include file="/pub/menu/topMenu.jsp"%>
    <div class="site">
        <div id="wrapper" class="site-content">
            <div id="answerList">
                <div class="center">
                    <div class="main_answer question_box" data-seq="${QuestionSeq}">
                        <div class="atm_top_header" id="addAlmoneyFlag">
							<c:if test="${ExtraAlmoney > 0}">
                            <div class="choice_wrap addAlmoney" id="addAlmoneyList" onclick="fShowExtraAlmoneyList(this)">
                                <img src="/pub/answer/answerList/images/answer_almoney.svg" alt='<spring:message code="msg_0230"/>'><span><fmt:formatNumber value="${ExtraAlmoney}" pattern="#,###" /></span>
                                <ul>
                                </ul>
                            </div>
							</c:if>
                        </div>
                        <div class="atm_top_wrap">
						<%
							String onQueMarkClick = "null";
							int Lv = (int) request.getAttribute("Lv");
							String[] lvArr = {msg_0236, msg_0237};
							if (!(Lv > 90)) {
								if (CommonUtil.useArraysBinarySearch(lvArr, request.getAttribute("NickName").toString())) {
									onQueMarkClick = "alert(getLangStr(\"jsm_0062\")); event.stopPropagation();";
								} else {
									onQueMarkClick = "fProfileMore(this);";
								}
							}
							// 프로필 접근 가능하도록 수정함
							if(Lv == 99) { onQueMarkClick = "alert(getLangStr(\"jsm_0062\")); event.stopPropagation();"; }
							pageContext.setAttribute("onQueMarkClick", onQueMarkClick);
						%>
                            <figure>
								<c:choose>
									<c:when test="${Photo ne ''}">
										<img src="/UploadFile/Profile/${Photo}" onerror="this.src='/pub/css/profile/img_thum_base0.jpg';" onclick='${onQueMarkClick}'>
									</c:when>
									<c:otherwise>
										<img src="/pub/css/profile/img_thum_base0.jpg" onclick='${onQueMarkClick}'>
									</c:otherwise>
								</c:choose>
								<c:if test="${MemberSeq != 10000691 and  MemberSeq != 10003513}">
								<img class="nation_flag" src="/Common/images/nation/${queNation}.svg" alt="${queNation}">
								<figcaption>${queNation}</figcaption>
								</c:if>
                            </figure>
                            <h2>Q.
								<c:choose>
									<c:when test="${Almoney >= 5000}"><span><fmt:formatNumber value="${Almoney}" pattern="#,###" /></span></c:when>
									<c:otherwise><span class="yellow"><fmt:formatNumber value="${Almoney}" pattern="#,###" /></span></c:otherwise>
								</c:choose>
							</h2>
                            <ul>
                                <li><% if( !UserSeq.equals("0") && UserLv.equals("99") ) { %> ${nickNameCheat} <% } else { %>
									<c:if test="${Lv ne 99 and NickName != msg_0236}">
									<span>
									<c:choose>
										<c:when test="${NickName eq msg_0237}">
											<spring:message code="msg_0237"/>
										</c:when>
										<c:otherwise>
											<c:if test="${Lv == 1}"><spring:message code="msg_0137"/></c:if>
											<c:if test="${Lv == 2}"><spring:message code="msg_0138"/></c:if>
											<c:if test="${Lv == 3}"><spring:message code="msg_0139"/></c:if>
											<c:if test="${Lv == 4}"><spring:message code="msg_0140"/></c:if>
											<c:if test="${Lv == 5}"><spring:message code="msg_0141"/></c:if>
											<c:if test="${Lv == 6}"><spring:message code="msg_0142"/></c:if>
											<c:if test="${Lv == 7}"><spring:message code="msg_0143"/></c:if>
											<c:if test="${Lv == 8}"><spring:message code="msg_0144"/></c:if>
											<c:if test="${Lv == 9}"><spring:message code="msg_0145"/></c:if>
											<c:if test="${Lv == 10}"><spring:message code="msg_0146"/></c:if>
											<c:if test="${Lv == 11}"><spring:message code="msg_0147"/></c:if>
											<c:if test="${Lv == 0}"><spring:message code="msg_0236"/></c:if>
										</c:otherwise>
									</c:choose>
									</span>
									</c:if>
									<strong class="prgNickname_Q">
									<c:choose><c:when test="${Lv == 0}"><spring:message code="msg_0236"/></c:when><c:otherwise>${NickName}</c:otherwise></c:choose> <% } %></strong><spring:message code="msg_0238"/>
								</li>
                                <li id="tit_${QuestionSeq}">${Title}</li>
                                <li>
								<c:if test="${NickName ne msg_0237 and FlagNickNameQ eq 'Y' and Lv < 90 }">
								<spring:message code="msg_0239"/> <b>${RateChoice}%</b> · 
								</c:if>
								<div class="answer_date" id="QusD_${QuestionSeq}">${DateReg}<span>${DateReg} UTC+9</span></div> 
								· <img src="../Common/images/icon_view.svg" alt="viewicon">${ReadCount}</li>
                            </ul>
                            <div class="atm_more_btn_wrap atm_more_btn_box">
                                <i></i>
                                <i></i>
                                <i></i>
                                <ul class="more_btn_menu">
									<%
										String MemberSeq = request.getAttribute("MemberSeq").toString();
										String onMoreMenu2Click;
										String onMoreMenu3Click;
										String onMoreMenu1Click = onMoreMenu2Click = onMoreMenu3Click = "alert(getLangStr(\"jsm_0020\"));";
										String AlmoneyMoveQuestion = request.getAttribute("AlmoneyMoveQuestion").toString();
	
										if ( !UserSeq.equals("0") ) {
											onMoreMenu1Click = "javascript:goSubmit(\"frm\", \"/answer/questionZzim\", \"answerList_ifrm\", \"\");";
											
											String msgs = "\"" + CommonUtil.getLangMsg(request, "msg_0240") + "\\n" + CommonUtil.getLangMsg(request, "msg_0241") + " " +  AlmoneyMoveQuestion + ""+CommonUtil.getLangMsg(request, "msg_0242") + "\\n" + CommonUtil.getLangMsg(request, "msg_0243") +"\"";
											onMoreMenu2Click = "if ( confirm( " + msgs + " ) ) fAjax_answer(\"/answer/moveQuestion\", \"\", \"ACT=GoTopQuestion&QuestionSeq=" + request.getAttribute("QuestionSeq").toString() + "\");";
											onMoreMenu3Click = "fAddAlmoney(\"Q\");";
										}
	
										pageContext.setAttribute("onMoreMenu1Click", onMoreMenu1Click);
										pageContext.setAttribute("onMoreMenu2Click", onMoreMenu2Click);
										pageContext.setAttribute("onMoreMenu3Click", onMoreMenu3Click);
									%>
                                    <li><a href="javascript:void(0)" onclick='${onMoreMenu1Click}'><span></span><spring:message code="msg_0176"/></a></li>
                                    <li class="share" onclick="fshareSNS(this, 'Q', ${QuestionSeq});"><a><span></span><spring:message code="msg_0244"/></a></li>
                                    <li><a href="javascript:void(0)" onclick='${onMoreMenu2Click}'><span></span><spring:message code="msg_0245"/></a></li>
                                    <li class="add_almoney"><a href="javascript:void(0)" onclick='${onMoreMenu3Click}'><span></span><spring:message code="msg_0230"/></a></li>
                                    <c:if test="${global != '' and Lv < 90 and MemberSeq != UserSeq and UserLv < 90}">
									<li class="submit_report_btn"><a href="javascript:void(0)" onclick="fsubmitReport($(this).parents('.question_box'),'Q')"><span></span><spring:message code="msg_0229"/></a></li>
									</c:if>
                                </ul>
                            </div>
							<%
								//if (!CommonUtil.useArraysBinarySearch(lvArr, request.getAttribute("NickName").toString())) {
								//System.out.println("lv : " + Lv);
								String lvl = CommonUtil.getLevelName(Lv, request);
							%>
                            <div class="profile_mini" id="profileBoxA">
                            	<table class="profile_mini_info">
                                    <tr>
                                        <th>
											<c:if test="${Lv ne 99 and NickName != msg_0331}">
											<span>
											<c:choose>
												<c:when test="${NickName eq msg_0237}">
													<spring:message code="msg_0237"/>
												</c:when>
												<c:otherwise>
											<c:if test="${Lv == 1}"><spring:message code="msg_0137"/></c:if>
											<c:if test="${Lv == 2}"><spring:message code="msg_0138"/></c:if>
											<c:if test="${Lv == 3}"><spring:message code="msg_0139"/></c:if>
											<c:if test="${Lv == 4}"><spring:message code="msg_0140"/></c:if>
											<c:if test="${Lv == 5}"><spring:message code="msg_0141"/></c:if>
											<c:if test="${Lv == 6}"><spring:message code="msg_0142"/></c:if>
											<c:if test="${Lv == 7}"><spring:message code="msg_0143"/></c:if>
											<c:if test="${Lv == 8}"><spring:message code="msg_0144"/></c:if>
											<c:if test="${Lv == 9}"><spring:message code="msg_0145"/></c:if>
											<c:if test="${Lv == 10}"><spring:message code="msg_0146"/></c:if>
											<c:if test="${Lv == 11}"><spring:message code="msg_0147"/></c:if>
											<c:if test="${Lv == 0}"><spring:message code="msg_0236"/></c:if>
												</c:otherwise>
											</c:choose>
											</span>
											</c:if>
											<c:choose><c:when test="${Lv == 0}"><spring:message code="msg_0236"/></c:when><c:otherwise>${NickName}</c:otherwise></c:choose></th>
                                        <c:choose><c:when test="${Lv == 0}"></c:when><c:otherwise><th rowspan="2"><img title='<spring:message code="msg_0246"/>' src="/pub/css/profile/addFriends.svg" onClick="javascript:goSubmit_2('frm', '/member/partnerSave?PartnerSeq=${MemberSeq}&FlagPartner=F', 'answerList_ifrm');" alt='<spring:message code="msg_0246"/>'></th></c:otherwise></c:choose>
                                        <c:choose><c:when test="${Lv == 0}"></c:when><c:otherwise><th rowspan="2"><img title='<spring:message code="msg_0247"/>' src="/pub/css/profile/addMento.svg" onClick="javascript:goSubmit_2('frm', '/member/partnerSave?PartnerSeq=${MemberSeq}&FlagPartner=M', 'answerList_ifrm');" alt='<spring:message code="msg_0247"/>'></th></c:otherwise></c:choose>
                                        <c:choose><c:when test="${Lv == 0}"></c:when><c:otherwise><th rowspan="2"><img title='<spring:message code="msg_0248"/>' src="/pub/css/profile/message.svg" class="openMessage" nickname="${NickName}" memberseq="${MemberSeq}" onclick="javascript:openMessageWindow(event);" alt='<spring:message code="msg_0248"/>'></th></c:otherwise></c:choose>
                                    </tr>
                                    <tr>
                                        <c:choose><c:when test="${Lv == 0}"></c:when><c:otherwise><td><spring:message code="msg_0249"/> : <span><fmt:formatNumber value="${EarnTotal}" pattern="#,###" /></span><spring:message code="msg_0136"/></td></c:otherwise></c:choose>
                                    </tr>
                                </table>
								<c:if test="${Intro != '' and Intro != null}">
								<c:if test="${Lv ne 99 and NickName != msg_0331}">
                                <p>"${Intro}"</p>
                                </c:if>
								</c:if>
								<c:if test="${Lv ne 99 and NickName != msg_0331}">
                                <i></i>
                                <c:choose><c:when test="${Lv == 0}"></c:when><c:otherwise>
                                <table class="profile_mini_almoney">
                                    <tr>
                                        <td><spring:message code="msg_0250"/></td>
                                        <td><spring:message code="msg_0251"/></td>
                                        <td><spring:message code="msg_0252"/></td>
                                        <td><spring:message code="msg_0239"/></td>
                                    </tr>
                                    <tr>
                                        <th><fmt:formatNumber value="${QuestionMoney}" pattern="#,###" /><spring:message code="msg_0136"/></th>
                                        <th><fmt:formatNumber value="${AnswerMoney}" pattern="#,###" /></th>
                                        <th>${CountC}</th>
                                        <th>${RateChoice}%</th>
                                    </tr>
                                </table>
								</c:otherwise></c:choose>
                                
                                <%
									String onQueMarkDirBtnClick = MemberSeq.equals(UserSeq)
											? "location.href='/member/myInfo';"
											: "goSubmit('frm', '/member/otherProfileInfo?MemberSeq=" + MemberSeq + "', '');";
									
									pageContext.setAttribute("onQueMarkDirBtnClick", onQueMarkDirBtnClick);
								%>
								<c:choose><c:when test="${Lv == 0}"></c:when><c:otherwise><a href="javascript:void(0)" onclick="${onQueMarkDirBtnClick}"><spring:message code="msg_0253"/></a></c:otherwise></c:choose>
                                
                                </c:if>
                            </div>
                        </div>
                        <%
							//}
						%>
                        <div class="answer_text prgContent_Q">${Contents}</div>
                        
                        <input type="hidden" id="qt_${QuestionSeq}" value="${qtSeq}" />
						<% if( !UserSeq.equals("0") ){ %>
						<div class="lang_assessment" <c:if test="${qtSeq == 0}">style="display:none"</c:if> id="lang_que_${QuestionSeq}">
						    <em><spring:message code="msg_0254"/></em>
						    <div class="lang_good">
						    	<img src="/Common/images/smile.svg" alt='<spring:message code="msg_0231"/>' onclick="goQuestionVote_new('G','${QuestionSeq}', 'T', 'qt');">
						    	<span id="lang_good_${QuestionSeq}"><fmt:formatNumber value="${qtGood}" pattern="#,###" /></span>
						    </div>
						    <div class="lang_bad">
						    	<img src="/Common/images/sad.svg" alt='<spring:message code="msg_0232"/>' onclick="goQuestionVote_new('B','${QuestionSeq}', 'T', 'qt');">
						    	<span id="lang_bad_${QuestionSeq}"><fmt:formatNumber value="${qtBad}" pattern="#,###" /></span>
						    </div>
						    <c:if test="${Lv < 99 and qtUserSeq ne MemberSeq and qtUserLv < 90 and qtSirenN < tMaxSiren}">
						    <img class="lang_report" id="lang_report_${QuestionSeq}" src="/pub/answer/answerList/images/atm_more_3.svg" alt='<spring:message code="msg_0229"/>' onclick="fsubmitReport_new($(this).parents('.lang_assessment'),'T')");">
						    </c:if>
						</div>
						<% }else{ %>
						<div class="lang_assessment" <c:if test="${qtSeq == 0}">style="display:none"</c:if> id="lang_que_${QuestionSeq}">
						    <em><spring:message code="msg_0254"/></em>
						    <div class="lang_good">
						         <img src="/Common/images/smile.svg" alt='<spring:message code="msg_0231"/>' onclick='alert(getLangStr("jsm_0020"));'>
						         <span id="lang_good_${QuestionSeq}"><fmt:formatNumber value="${qtGood}" pattern="#,###" /></span>
						    </div>
						    <div class="lang_bad">
						         <img src="/Common/images/sad.svg" alt='<spring:message code="msg_0232"/>' onclick='alert(getLangStr("jsm_0020"));'>
						         <span id="lang_good_${QuestionSeq}"><fmt:formatNumber value="${qtBad}" pattern="#,###" /></span>
						    </div>
							<img class="lang_report" id="lang_report_${QuestionSeq}"  src="/pub/answer/answerList/images/atm_more_3.svg" alt='<spring:message code="msg_0229"/>' onclick='alert(getLangStr("jsm_0020"));'>
						</div>
						<% } %>
                        
                        
						<%
							int SumReply = Integer.parseInt(request.getAttribute("SumReply").toString());
							pageContext.setAttribute("SumReply", SumReply);
						%>
						<c:if test="${(UserSeq eq MemberSeq and AnswerCnt == '0') or UserLv eq '99'}">
                        <div class="atm_question_more">
                        	<%
	                        	String onEditBtnClick = "location='/question/questionWrite?QuestionSeq="
										+ request.getAttribute("QuestionSeq").toString() + "&SP="
										+ ((request.getParameter("SP") == null || request.getParameter("SP").equals("null"))
										? ""
										: request.getParameter("SP"))
										+ "';";
								String onMngEditBtnClick = "location='/question/questionWriteMng?QuestionSeq="
										+ request.getAttribute("QuestionSeq").toString() + "&SP="
										+ ((request.getParameter("SP") == null || request.getParameter("SP").equals("null"))
										? ""
										: request.getParameter("SP"))
										+ "';";
								if ( Integer.parseInt(request.getAttribute("AnswerCnt").toString()) > 0 && UserLv.equals(99) ) {
									onEditBtnClick = "alert('" + msg_0255 + "');";
								}
	
								pageContext.setAttribute("onEditBtnClick", onEditBtnClick);
								pageContext.setAttribute("onMngEditBtnClick", onMngEditBtnClick);
							%>
                            <p onclick="goConfirm('frm', '/question/questionDelete?SP=<%=request.getParameter("SP") == "null" || request.getParameter("SP") == null ? "" : request.getParameter("SP")%>', '삭제', 'answerList_ifrm');"><spring:message code="msg_0228"/></p>
                            <p onclick="${onEditBtnClick}"><spring:message code="msg_0256"/></p>
							<%
								if (UserLv.equals("99")) {

								int BestNumber = Integer.parseInt(request.getAttribute("BestNumber").toString());

								String onBestBtnClick = "";
								String bestBtnText = "";

								if (BestNumber == 0) {
									onBestBtnClick = "goSubmit('frm', '/answer/bestQuestionSet?BestRank=' + prompt('순서를 입력해주세요.'), 'answerList_ifrm');";
									bestBtnText = "베스트 지정";
								} else {
									onBestBtnClick = "goSubmit('frm', '/answer/bestQuestionSet', 'answerList_ifrm');";
									bestBtnText = "베스트 지정 해제";
								}
							%>
                            <p onclick="<%=onBestBtnClick%>"><%=bestBtnText%></p>
                            <p onclick="<%=onMngEditBtnClick%>">관리자 수정</p>
                            <%
								}
							%>
                        </div>
						</c:if>
						
						
						<div class="languages_translation">
                            <div class="lang_origin<c:if test='${qtSeq == 0}'> pick</c:if>" id="qT_lang_origin" onclick="goAnswer('${QuestionSeq}', 'qt')"><spring:message code="msg_0257"/></div>
                            <c:if test="${machineCnt > 0}"></c:if>
                            <div class="lang_ai<c:if test='${qtSeq > 0 and qtTrnType == 1}'> pick</c:if>" id="qT_lang_ai" onclick="goTranslate('${QuestionSeq}', 'Q');">
                                <div class="lang_image">AI</div>
                               <!-- 
                                <div class="lang_view" id="lang_view_que_${QuestionSeq}">
                                    <ul class="lang_view_el01">
                                        <li><img src="/Common/images/icon_view.svg" alt=""><span id="qt_count_${QuestionSeq}"><fmt:formatNumber value="${qtCount}" pattern="#,###" /></span></li>
                                    </ul>
                                    <dl>
                                    </dl>
                                </div>
                                 -->
                            </div>
                            
                            <!--// 사람 번역 구현(2차 개발)
                            <div class="lang_profile profileLeft">
                                <div class="lang_image">
                                    <img src="../Common/images/awssss.png" alt="프로필 이미지">
                                    <img class="nation_flag" src="/Common/images/nation/ca.svg" alt="캐나다">
                                </div>
                                <div class="lang_view">
                                    <ul class="lang_view_el01">
                                        <li><b>3분 전<span>2020-12-20 17:15:30</span></b> · <img src="/Common/images/icon_view.svg" alt=""><span>50</span></li>
                                        <li class="lang_nick">Ronaldo</li>
                                    </ul>
                                    <p class="lang_view_el02"><img src="/pub/answer/answerList/images/answer_almoney.svg" alt='<spring:message code="msg_0230"/>'><span>3,000</span></p>
                                    <dl>
                                        <dt><spring:message code="msg_0235"/></dt>
                                        <dd><img src="/Common/images/nation/us.svg" alt=""><b>영웅본색 (10,000알) </b>8개월 전</dd>
                                        <dd><img src="/Common/images/nation/cn.svg" alt=""><b>관리자 (1,000알) </b>7일 전</dd>
                                        <dd><img src="/Common/images/nation/au.svg" alt=""><b>오즈큐 (1,000알) </b>2개월 전</dd>
                                    </dl>
                                </div>
                                <div class="profile_mini">
                                    <span>수호천사</span>
                                    <table class="profile_mini_info">
                                        <tr>
                                            <th>BTS_jjjang</th>
                                            <th rowspan="2"><img src="../Common/images/addFriends.svg" alt='<spring:message code="msg_0246"/>'></th>
                                            <th rowspan="2"><img src="../Common/images/addMento.svg" alt='<spring:message code="msg_0247"/>'></th>
                                            <th rowspan="2" class="message_send"><img src="../Common/images/message.svg" alt='<spring:message code="msg_0248"/>'></th>
                                        </tr>
                                        <tr>
                                            <td><spring:message code="msg_0249"/> : <span>13,962,921</span><spring:message code="msg_0136"/></td>
                                        </tr>
                                    </table>
                                    <p><spring:message code="msg_0258"/></p>
                                    <i></i>
                                    <table class="profile_mini_almoney">
                                        <tr>
                                            <td><spring:message code="msg_0250"/></td>
                                            <td><spring:message code="msg_0251"/></td>
                                            <td><spring:message code="msg_0259"/></td>
                                            <td><spring:message code="msg_0260"/></td>
                                        </tr>
                                        <tr>
                                            <th>4,212,873알</th>
                                            <th>1,725알</th>
                                            <th>397</th>
                                            <th>97.6%</th>
                                        </tr>
                                    </table>
                                    <a href="#"><spring:message code="msg_0253"/></a>
                                </div>    
                            </div>
                            <div class="lang_profile">
                                <div class="lang_image">
                                    <img src="../Common/images/awssss.png" alt="프로필 이미지">
                                    <img class="nation_flag" src="/Common/images/nation/de.svg" alt="독일">
                                </div>
                                <div class="lang_view">
                                    <ul class="lang_view_el01">
                                        <li><b>3분 전<span>2020-12-20 17:15:30</span></b> · <img src="/Common/images/icon_view.svg" alt=""><span>50</span></li>
                                        <li class="lang_nick">Ronaldo</li>
                                    </ul>
                                    <p class="lang_view_el02"><img src="/pub/answer/answerList/images/answer_almoney.svg" alt='<spring:message code="msg_0230"/>'><span>3,000</span></p>
                                    <dl>
                                        <dt><spring:message code="msg_0235"/></dt>
                                        <dd><img src="/Common/images/nation/us.svg" alt=""><b>영웅본색 (10,000알) </b>8개월 전</dd>
                                        <dd><img src="/Common/images/nation/cn.svg" alt=""><b>관리자 (1,000알) </b>7일 전</dd>
                                        <dd><img src="/Common/images/nation/au.svg" alt=""><b>오즈큐 (1,000알) </b>2개월 전</dd>
                                    </dl>
                                </div>
                                <div class="profile_mini">
                                    <span>수호천사</span>
                                    <table class="profile_mini_info">
                                        <tr>
                                            <th>BTS_jjjang</th>
                                            <th rowspan="2"><img src="../Common/images/addFriends.svg" alt='<spring:message code="msg_0246"/>'></th>
                                            <th rowspan="2"><img src="../Common/images/addMento.svg" alt='<spring:message code="msg_0247"/>'></th>
                                            <th rowspan="2" class="message_send"><img src="../Common/images/message.svg" alt='<spring:message code="msg_0248"/>'></th>
                                        </tr>
                                        <tr>
                                            <td><spring:message code="msg_0249"/> : <span>13,962,921</span><spring:message code="msg_0136"/></td>
                                        </tr>
                                    </table>
                                    <p><spring:message code="msg_0258"/></p>
                                    <i></i>
                                    <table class="profile_mini_almoney">
                                        <tr>
                                            <td><spring:message code="msg_0250"/></td>
                                            <td><spring:message code="msg_0251"/></td>
                                            <td><spring:message code="msg_0259"/></td>
                                            <td><spring:message code="msg_0260"/></td>
                                        </tr>
                                        <tr>
                                            <th>4,212,873알</th>
                                            <th>1,725알</th>
                                            <th>397</th>
                                            <th>97.6%</th>
                                        </tr>
                                    </table>
                                    <a href="#"><spring:message code="msg_0253"/></a>
                                </div>    
                            </div>
                            -->
                        </div>
                        
                        <ol class="answer_reply_btn">
                            <li class="reply">
							<c:if test="${developSeq eq null}">
							<a href="javascript:void(0)" onclick="reply(this, 'replydiv');$(this).toggleClass('on')"><img src="../Common/images/icon_reply.svg" alt="replyicon"><span>${SumReply}</span></a>
							</c:if>
							</li>
							
                            <li>
							<c:if test="${UserLv != ''}">
							<c:choose>
								<c:when test="${MemberSeq != UserSeq}">
									<div class="smile_icon" onclick="goQuestionVote('G','${QuestionSeq}', 'Q');">
										<img src="/pub/answer/answerList/images/smile.svg">
										<b id="queVoteG">
											<fmt:formatNumber value="${good}" pattern="#,###" />
										</b>
									</div>
									<div class="sad_icon" onclick="goQuestionVote('B','${QuestionSeq}', 'Q');">
										<img src="/pub/answer/answerList/images/sad.svg">
										<b id="queVoteB">
											<fmt:formatNumber value="${bad}" pattern="#,###" />
										</b>
									</div>
								</c:when>
								<c:otherwise>
									<div class="smile_icon" onclick="javascript:void(0);">
										<img src="/pub/answer/answerList/images/smile.svg">
										<b id="queVoteG">
											<fmt:formatNumber value="${good}" pattern="#,###" />
										</b>
									</div>
									<div class="sad_icon" onclick="javascript:void(0);">
										<img src="/pub/answer/answerList/images/sad.svg">
										<b id="queVoteB">
											<fmt:formatNumber value="${bad}" pattern="#,###" />
										</b>
									</div>
								</c:otherwise>
							</c:choose>
							</c:if>
							<c:if test="${UserLv == ''}">
								<div class="smile_icon" onclick='alert(getLangStr("jsm_0020"));'>
									<img src="/pub/answer/answerList/images/smile.svg">
									<b id="queVoteG">
										<fmt:formatNumber value="${good}" pattern="#,###" />
									</b>
								</div>
								<div class="sad_icon" onclick='alert(getLangStr("jsm_0020"));'>
									<img src="/pub/answer/answerList/images/sad.svg">
									<b id="queVoteB">
										<fmt:formatNumber value="${bad}" pattern="#,###" />
									</b>
								</div>
							</c:if>
							</li>
							
							
							<c:if test="${developSeq == null}">
							<%
								// 추가 버튼 영역: [즐겨찾기, 공유, 신고]

							String onAnswerBtnClick = "alert(getLangStr(\"jsm_0020\"));location.href=\"/default/login\";";
							//String MemberSeq = String.valueOf(request.getAttribute("MemberSeq"));
							String QuestionSeq = String.valueOf(request.getAttribute("QuestionSeq"));
							String CurPageName = String.valueOf(request.getAttribute("CurPageName"));
							String Section1 = String.valueOf(request.getAttribute("Section1"));
							String src_Sort = String.valueOf(request.getAttribute("src_Sort"));
							String src_OrderBy = String.valueOf(request.getAttribute("src_OrderBy"));

							if ( !UserSeq.equals("0") && !(MemberSeq.equals(UserSeq) || FlagSelfAnswer == "Y" || MemberType == "2")
									&& (developSeq == null || developSeq == "null" || developSeq == ""))
								onAnswerBtnClick = "location.href=\"/answer/answerWrite?QuestionSeq=" + QuestionSeq + "&CurPageName=" + CurPageName
								+ "&Section1=" + Section1 + "&src_Sort=" + src_Sort + "&src_OrderBy=" + src_OrderBy + "\";";
							%>
                            <li id="notAnswerbtn" style="display: none;">
                            	<% if ( !UserSeq.equals("0") && !MemberSeq.equals(UserSeq) ) { %>
                            	<a href="javascript:void(0)" onclick='<%=onAnswerBtnClick%>'><spring:message code="msg_0262"/></a>
                            	<% } %>
                            	<!-- 2차 개발 항목 --> <a class="lang_btn" href="javascript:void(0)" onclick='javascript:alert(getLangStr("jsm_0065") + "\n" +  getLangStr("jsm_0066") + "\n" +  getLangStr("jsm_0067") + "\n" +  getLangStr("jsm_0068"))'><spring:message code="msg_0233"/></a>
                            </li>
							<li id="isAnswerbtn" style="display: none;">
								<a href="javascript:void(0)" style="background-color: #e8e8e8;color: #333333;border: 1px solid #fff;"><spring:message code="msg_0263"/></a>
								<!-- 2차 개발 항목 --> <a class="lang_btn" href="javascript:void(0)" onclick='javascript:alert(getLangStr("jsm_0065") + "\n" +  getLangStr("jsm_0066") + "\n" +  getLangStr("jsm_0067") + "\n" +  getLangStr("jsm_0068"))'><spring:message code="msg_0233"/></a>
							</li>
							</c:if>
                        </ol>
						<%
							String onReplyDivClick = UserSeq.equals("0") ? "alert(getLangStr(\"jsm_0020\")); location.href=\"/default/login\";" : "null";
						%>
                        <div class="replydiv" id="replyDiv" onclick='<%=onReplyDivClick%>'>
							<form name="sch" method="post" onSubmit="return false;">
							<input type="hidden" name="QuestionSeq" value="${QuestionSeq}">
                            <div class="replydiv_el">
								<%
									String replyPlaceholder = CommonUtil.getLangMsg(request, "msg_0168");
									if ( !UserSeq.equals("0") ) {
										replyPlaceholder = UserNickName + CommonUtil.getLangMsg(request, "msg_0264");
									}
								%>
                                <textarea <%=UserSeq == "" ? "disabled" : ""%> onkeyup="freplyResize(this)" name="Contents" id="" maxlength="400" placeholder="<%=replyPlaceholder%>"></textarea>
                                <div class="reply_renew" onclick="fViewQReply('${QuestionSeq}', 'Q', true)"><p><img src="/pub/answer/answerList/images/autorenew.svg" alt=""><spring:message code="msg_0265"/></p></div>
                                <div class="reply_submit">
                                    <p class="atm_reply_c6"><span>0</span>/400</p>
                                    <button type="button" onclick="goSubmit('sch','/answer/reply?TargetSeq=${QuestionSeq}&Flag=Q','answerList_ifrm');"><img src="../Common/images/modify03.svg" alt=<spring:message code="msg_0267"/>><i><spring:message code="msg_0266"/></i></button>
                                </div>
                            </div>
							</form>
                            <div class="replydiv_user">
							<c:forEach var="item" items="${replyList}" varStatus="status">
                                <div class="replydiv_user_list atm_reply_el" data-seq="${item.seq}">
									<c:choose>
										<c:when test="${item.memberSeq eq UserSeq}">
											<c:set var="onReplyThClick"
												value="location.href='/member/myInfo'" />
											<c:set var="replyMemberProfileUrl" value="/member/myInfo" />
										</c:when>
										<c:otherwise>
											<c:set var="onReplyThClick"
												value="goSubmit('frm', '/member/otherProfileInfo?MemberSeq=${item.memberSeq}', '');" />
											<c:set var="replyMemberProfileUrl"
												value="/member/otherProfileInfo?MemberSeq=${item.memberSeq}" />
										</c:otherwise>
									</c:choose>

									<c:choose>
										<c:when test="${item.lv eq '98'}">
											<c:set var="classLv" value="system_message" />
										</c:when>
										<c:otherwise>
											<c:set var="classLv" value="" />
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${item.sirenN >= maxSiren}">
											<c:set var="classLv2" value="blind_txt" />
											<c:set var="content" value='${msg_0227}' />
										</c:when>
										<c:otherwise>
											<c:set var="classLv2" value="prgContent_QR" />
											<c:if test="${item.nick1 == null}">
												<c:set var="content" value="${item.reply}" />
											</c:if>
											<c:if test="${item.nick1 != null}">
												<fmt:formatNumber var="repAl" value="${item.almoney}"
													pattern="#,###" />
												<c:set var="content"
													value='${item.nick1}${msg_0224}${item.nick2}${msg_0225}${repAl}${msg_0226}' />
											</c:if>
										</c:otherwise>
									</c:choose>
									<table>
                                        <tr>
                                            <th><a href="javascript:void(0)" onclick="${onReplyThClick}">
                                            <c:choose>
											<c:when test="${item.photo ne '' and fn:length(item.photo) > 0}">
												<img src="/UploadFile/Profile/${item.photo}" onerror="this.src='/pub/css/profile/img_thum_base0.jpg';" />
											</c:when>
											<c:otherwise>
												<img src="/pub/css/profile/img_thum_base0.jpg" />
											</c:otherwise>
											</c:choose>
											<c:if test="${item.memberSeq != 10000691 and  item.memberSeq != 10003513}">
											<img class="nation_flag" src="/Common/images/nation/${item.nation}.svg" alt="${item.nation}">
											<div class="reply_nation">${item.nation}</div>
											</c:if>
											</a>
											</th>
											<c:if test="${item.lv == '98'}">
                                            	<th class="system_message" id="tq_reply_${item.seq}">${content}</th>
											</c:if>
											<c:if test="${item.lv ne '98'}">
                                            	<th id="tq_reply_${item.seq}">${content}</th>
											</c:if>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td><a href="javascript:replyQueRegi('${item.nickName}')">${item.nickName}</a> · <b id="reply${item.seq}">${item.conDate} <span>${item.conDate}</span></b> 
											<c:if test="${item.memberSeq eq UserSeq or UserLv == '99' and item.memberSeq != '10003513'}">
											. <i onclick="goConfirm('frm', '/answer/replydel?ReplySeq=${item.seq}&Flag=Q', '<spring:message code="msg_0228"/>', 'answerList_ifrm');"><spring:message code="msg_0228"/></i>
											</c:if>
											<c:if test="${item.memberSeq != '' and UserLv < 99 and item.memberSeq ne UserSeq and item.lv < 90 and item.sirenN < maxSiren}">
											· <i onclick="fsubmitReport($(this).parents('.atm_reply_el'),'QR')"><spring:message code="msg_0229"/></i>
											</c:if>
											<!--//2차 개발
											<div>
						                         <img class="reply_add_honhon" src="/pub/answer/answerList/images/answer_almoney.svg" alt='<spring:message code="msg_0230"/>'>
						                         <span class="reply_honhon_btn">3,000</span>
						                         <dl class="reply_honhon">
						                         </dl>                
						                     </div>
						                     <div>
						                         <div class="smile_icon"><img src="/Common/images/smile.svg" alt='<spring:message code="msg_0231"/>'><b>30</b></div>
						                         <div class="sad_icon"><img src="/Common/images/sad.svg" alt='<spring:message code="msg_0232"/>'><b>1</b></div>                                                   
						                     </div>
						                     -->
						                     <input type="hidden" id="tq_reply_trn_${item.seq}" value="N"/>
						                     <c:if test="${item.lang != targetLang}">
						                     <div class="reply_lang_icon"><img class="answer_lang" src="/Common/images/language.svg" alt='<spring:message code="msg_0233"/>' id="qrT_ai_${item.seq}" onclick="goTranslate_qr_ar('${item.seq}', 'R');"></div>
						                     </c:if>
											</td>
                                        </tr>
                                    </table>
                                </div>
								<script>formatDateForSpan('reply${item.seq}');</script>
							</c:forEach>
                            </div>
                        </div>
                    </div>

					<script>formatDateForSpan('QusD_'+${QuestionSeq});fautoURL($('.question_box'));</script>
					<script>
					function freplyResize(commentInput) {
						if (commentInput.scrollHeight >= 200) {
							commentInput.style.overflow="scroll";
							return;
						}

						commentInput.style.height = "41px";
						commentInput.style.height = commentInput.scrollHeight + 'px';

						// placeholder가 2줄 있을 땐 아래 padding을 제외하고 계산되어 미관적으로 좋게끔 px값 수정
						if (commentInput.scrollHeight == "52") {
							//commentInput.style.height = "60px";
						}
					}

					function fSetAnswer() {
						var base = $('.atm_av_el').last().find('.atm-ui-tab1');
						var more = base.find('.answer_more');
						if (!more.length) return;
						var answer = base.find('.answer_text');
						var baseTop = base.offset().top + 77;


						//더보기가 보이지않거나 왼쪽 첫번째 칸에 위치할때
						var nn;
						for (var i=answer.html().length; i>0; i--) //무한루프 방지를 위해 while 사용하지 않음
						{
							if ((baseTop < more.offset().top + 22 || more.offset().left < 20) && answer.text().length > 1)
							{
								nn = answer.html().length;
								answer.html(answer.html().substring(0, answer.html().length - 1));
								if (nn == answer.html().length)
									answer.html(answer.html().substring(0, answer.html().length - 5));
							}
							else { break; }
						}

						// 이미지 있을때 텍스트 보이게
						var aImg = base.siblings('.answer_info').find('answer_number').find('.answer_imgN');
						var aImgCount = aImg.find('span').text();
						aImgCount = parseInt(aImgCount);
						
						if (aImgCount > 0) {
							aImg.css('display', 'inline-block');
							aImg.siblings('.answer_textN').append(',');
						}
					}

					function sortAnswer() {
						var me = $('.sub_answer:last');
						for(var i=$('.sub_answer').length-2; i>=0; i--) {
							if (parseFloat(me.data('sortv')) > parseFloat($('.sub_answer').eq(i).data('sortv'))) {
								$('.sub_answer').eq(i).insertAfter(me);
							} else {
								continue;
							}
						}
					}
				</script>
				<!-- 1366 줄 이후 진행 예정 answerList-->

				<!-- // 카테고리별 외부 광고 조회 adData -->
				<c:set var="isAnswered" value="false" />
				<!-- 답변 중복작성 여부-->
				<c:forEach var="answerList" items="${answerList}" varStatus="status">
					<c:if test="${not loop_flag}">
						<c:if test="${status.index == 0}">
							<c:set var="loop_flag" value="true" />
						</c:if>
					</c:if>

					<c:choose>
						<c:when test="${answerList.nickName eq ''}">
							<c:set var="AnswerNickName" value='${msg_0237}' />
						</c:when>
						<c:otherwise>
							<c:set var="AnswerNickName" value="${answerList.nickName}" />
						</c:otherwise>
					</c:choose>

					<c:set var="AnswerSeq" value="${answerList.seq}" />
					<c:set var="AnswerMemberSeq" value="${answerList.memberSeq}" />
					<c:set var="ASirenN" value="${answerList.sirenN}" />
					<c:set var="Answer" value="${answerList.answer}" />
					<c:set var="FlagChoice" value="${answerList.flagChoice}" />
					<c:set var="FlagNickName" value="${answerList.flagNickName}" />
					<c:set var="PointCount1" value="${answerList.pointCount1}" />
					<c:set var="PointCount2" value="${answerList.pointCount2}" />
					<c:set var="PointCount3" value="${answerList.pointCount3}" />
					<c:set var="PointCount4" value="${answerList.pointCount4}" />
					<c:set var="PointCount5" value="${answerList.pointCount5}" />
					<c:set var="PointCount6" value="${answerList.pointCount6}" />
					<c:set var="MyPointCount" value="${answerList.pointCountNo}" />
					<c:set var="PointCount6_Yn" value="${answerList.pointCount6_Yn}" />
					<c:set var="CNTSUMReplayanswer" value="${answerList.cNTSUMReplayanswer}" />
					<c:set var="AnswerReadCount" value="${answerList.readCount}" />
					<c:set var="AnswerDateReg" value="${answerList.regdate}" />
					<c:set var="AnswerNickName" value="${answerList.nickName}" />
					<c:set var="PointRank" value="${answerList.pointRank2}" />
					<c:set var="AnswerPhoto" value="${answerList.photo}" />
					<c:set var="AnswerLv" value="${answerList.lv}" />
					<c:set var="AnswerIntro" value="${answerList.intro}" />
					<c:set var="AnswerQMoney" value="${answerList.q_Almoney}" />
					<c:set var="AnswerAMoney" value="${answerList.a_Almoney}" />
					<c:set var="EarnTotal" value="${answerList.earnTotal}" />
					<c:set var="AnswerCountC" value="${answerList.a_ChoicedCount}" />
					<c:set var="AnswerCountN" value="${answerList.a_Count}" />
					<c:set var="AnswerExtraAlmoney" value="${answerList.extraAlmoney}" />
					<c:set var="tSeq" value="${answerList.tSeq}" />

					<c:set var="answerFileCount" value="0" />
					<c:if test="${developSeq == null or developSeq eq AnswerSeq}">


						<!-- 1505~1513줄 
						CommonUtil 을 이용하여 특정 카운터 가져오기 getAnswerFilecount(seq)
						-->
						<%
							int nAnswerSeq = Integer.valueOf(pageContext.getAttribute("AnswerSeq").toString());
							int answerFileCount = CommonUtil.getAnswerFilecount(nAnswerSeq);
							pageContext.setAttribute("answerFileCount", answerFileCount);
						%>
						<c:set var="FlagNickName" value="${answerList.flagNickName}" />
						<c:if test="${UserLv eq '99'}">
							<c:if test="${FlagNickName eq 'N'}">
								<c:set var="AnswerNickName" value='${msg_0236}' />
							</c:if>
							<c:set var="FlagNickName" value="Y" />
						</c:if>

						<c:if test="${FlagNickName eq 'N'}">
							<c:choose>
								<c:when test="${AnswerMemberSeq eq UserSeq}">
									<c:set var="AnswerNickName" value='${msg_0236}' />
								</c:when>
								<c:otherwise>
									<c:set var="AnswerNickName" value='${msg_0236}' />
									<c:set var="AnswerPhoto" value="" />
									<c:set var="AnswerLv" value="00" />
									<c:set var="AnswerQMoney" value="0" />
									<c:set var="AnswerAMoney" value="0" />
									<c:set var="AnswerCountC" value="0" />
									<c:set var="EarnTotal" value="0" />
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:if>

					<c:choose>
						<c:when test="${AnswerCountN eq '0'}">
							<c:set var="AnswerRateChoice" value="0" />
						</c:when>
						<c:otherwise>
							<c:set var="AnswerRateChoice"
								value="${(AnswerCountC / AnswerCountN) * 100}" />
						</c:otherwise>
					</c:choose>

					<fmt:formatNumber var="AnswerRateChoice"
						value="${AnswerRateChoice}" pattern=".0" />

					<fmt:formatNumber var="AnswerReadCount" value="${AnswerReadCount}"
						type="number" />

					<c:choose>
						<c:when test="${AnswerPhoto ne '' or fn:length(AnswerPhoto) > 0}">
							<c:set var="AnswerPhoto" value="${AnswerPhoto}" />
						</c:when>
						<c:otherwise>
							<c:set var="AnswerPhoto" value="img_thum_base0.jpg" />
						</c:otherwise>
					</c:choose>

					<%
						int pointCount1 = Integer.valueOf(pageContext.getAttribute("PointCount1").toString());
						int pointCount2 = Integer.valueOf(pageContext.getAttribute("PointCount2").toString());
						int pointCount3 = Integer.valueOf(pageContext.getAttribute("PointCount3").toString());
						int pointCount4 = Integer.valueOf(pageContext.getAttribute("PointCount4").toString());
						int pointCount5 = Integer.valueOf(pageContext.getAttribute("PointCount5").toString());
						int pointCount6 = Integer.valueOf(pageContext.getAttribute("PointCount6").toString());
						int pointCount7 = 0;
						int myPointCount = Integer.valueOf(pageContext.getAttribute("MyPointCount").toString());

						String pointCount6_Yn = pageContext.getAttribute("PointCount6_Yn").toString();

						Map<Integer, Integer> pointArr = new HashMap<Integer, Integer>();
						pointArr.put(1, pointCount1);
						pointArr.put(2, pointCount2);
						pointArr.put(3, pointCount3);
						pointArr.put(4, pointCount4);
						pointArr.put(5, pointCount5);
						pointArr.put(6, pointCount6);
						pointArr.put(7, pointCount7);

						Map<Integer, Integer> sortedMap = getSortMap(pointArr);

						if (pointCount6_Yn == "Y" && myPointCount == 0)
							myPointCount = 6;
						//System.out.println("pointCount6_Yn : " + pointCount6_Yn);
						//System.out.println("myPointCount : " + myPointCount);
						/**/
						Iterator<Entry<Integer, Integer>> iterator = sortedMap.entrySet().iterator();
						Map<Integer, Integer> pointArr2 = new HashMap<Integer, Integer>();
						while (iterator.hasNext()) {
							Map.Entry<Integer, Integer> entry = iterator.next();
							//System.out.println("Key : " + entry.getKey() + " Value :" + entry.getValue() + " : myChk : " + myPointCount);
							if(entry.getKey() == myPointCount) { continue; }
							if(entry.getValue() == 0) { continue; }
							//System.out.println(entry.getKey() + " = " + entry.getValue());
							pointArr2.put(entry.getKey(), entry.getValue());
						}

						sortedMap = getSortMap(pointArr2);
						
						/*
						Iterator<Entry<Integer, Integer>> iterator2 = sortedMap.entrySet().iterator();
						while (iterator2.hasNext()) {
							Map.Entry<Integer, Integer> entry = iterator2.next();
							System.out.println("Key : " + entry.getKey() + " Value :" + entry.getValue() + " : myChk : " + myPointCount);
						}
						*/

						int PointCount = pointCount1 + pointCount2 + pointCount3;
						int PointSum = (pointCount1 * 7) + (pointCount2 * 3) + (pointCount3 * 1) + (pointCount4 * -1) + (pointCount5 * -3)
								+ (pointCount6 * 0);

						pageContext.setAttribute("PointCount", PointCount);
						pageContext.setAttribute("PointSum", PointSum);
						pageContext.setAttribute("sortedMap", sortedMap);
					%>
					<!--<c:set var="pointArr">PointCount1, PointCount2, PointCount3, PointCount4, PointCount5, PointCount6, PointCount7</c:set>-->

					<!-- // <br> 태그를 chr(1) 로 치환하여, 답변 컷팅 시 <br> 태그 짤리지 않게끔 -->

					<%
						int ASirenN = Integer.parseInt(pageContext.getAttribute("ASirenN").toString());
						int maxSiren = Integer.parseInt(request.getAttribute("maxSiren").toString());
						String Answer = pageContext.getAttribute("Answer").toString();

						int AnswerText = 0;
						if (ASirenN < maxSiren) {
							Answer = Answer.replaceAll("<br>", String.valueOf((char) 1));
							Answer = Answer.replaceAll("(^\\p{Z}+|\\p{Z}+$)", "");
							//Answer = Answer.replaceAll("\\p{Z}", ""); //https://puttico.tistory.com/72
							Answer = stripTags(Answer, "");

							AnswerText = contLength(Answer);

							if (AnswerText > 100) {
								Answer = substrib(Answer, 0, 100, 2); //Answer.substring(0, 100);
							} else {
								Answer = substrib(Answer, 0, AnswerText, 2);
							}
							Answer = Answer.replaceAll(String.valueOf((char) 1), "<br>");
						} else {
							AnswerText = 0;
						}

						pageContext.setAttribute("Answer", Answer);
						pageContext.setAttribute("AnswerText", AnswerText);

						List<AdVO> adData = (List<AdVO>) request.getAttribute("adData");

						int ADSeq = 0;
						String ADFile = "14_11.jpg";
						String ADFileExt = "jpg";
						String ADUrl = "";
						int ADFlag = 0;

						int RandomIdx = (int) Math.round(java.lang.Math.random() * (adData.size() - 1));
						for (int i = 0; i < adData.size(); i++) {
							ADSeq = 0;
							ADFile = "14_11.jpg";
							ADFileExt = "jpg";
							ADUrl = "";
							ADFlag = 0;
						}
						if (adData != null && adData.size() > 0) {
							ADSeq = adData.get(RandomIdx).getSeq();
							ADFile = adData.get(RandomIdx).getAdFile();
							ADFileExt = adData.get(RandomIdx).getAdFileExt();
							ADUrl = adData.get(RandomIdx).getUrl();
							ADFlag = adData.get(RandomIdx).getFlagAd();
						}

						pageContext.setAttribute("ADSeq", ADSeq);
						pageContext.setAttribute("ADFile", ADFile);
						pageContext.setAttribute("ADFileExt", ADFileExt);
						pageContext.setAttribute("ADUrl", ADUrl);
						pageContext.setAttribute("ADFlag", ADFlag);
					%>
					<!-- 1583 이후부터 개발 -->
					<!-- // 광고. 상위에서 $adData 변수에 load 해놓음 -->

					<!-- al = $ADFlag == 0 ? 0 : ViewMoneySum; -->

					<c:choose>
						<c:when test="${ADFlag eq 0}">
							<c:set var="al" value="0" />
						</c:when>
						<c:otherwise>
							<c:set var="al" value="${ViewMoneySum}" />
						</c:otherwise>
					</c:choose>


					<c:set var="availableView" value="false" />

					<c:choose>
						<c:when test="${global != ''}">
							<c:set var="AlmoneyCnt" value="${answerList.almoneyCnt}" />
							<c:set var="availableView" value="${(UserAlmoney - al) >= 0 }" />
						</c:when>
						<c:otherwise>
							<c:set var="AlmoneyCnt" value="0" />
							<c:set var="availableView" value="true" />
						</c:otherwise>
					</c:choose>

					<c:choose>
						<c:when test="${AnswerText eq 0}">
							<c:set var="sortV" value="0" />
						</c:when>
						<c:when test="${AnswerText > 300}">
							<c:set var="sortV" value="${300 * 0.1 + PointSum}" />
						</c:when>
						<c:otherwise>
							<c:set var="sortV" value="${AnswerText * 0.1 + PointSum}" />
						</c:otherwise>
					</c:choose>

					<c:choose>
						<c:when test="${AnswerExtraAlmoney > 50000}">
							<c:set var="sortV" value="${sortV + 200}" />
						</c:when>
						<c:when test="${AnswerExtraAlmoney > 10000}">
							<c:set var="sortV" value="${sortV + 100}" />
						</c:when>
						<c:when test="${AnswerExtraAlmoney >= 3000}">
							<c:set var="sortV" value="${sortV + 50}" />
						</c:when>
					</c:choose>

					<c:if test="${UserSeq == AnswerMemberSeq}">
						<c:set var="isAnswered" value="true" />
					</c:if>
					
					
                    <div class="sub_answer atm_av_el" data-seq="${AnswerSeq}" id="ansList_${AnswerSeq}" data-sortv="${sortV}">
						<div id="overlay" ><!-- onclick="fADstop();" -->
							<div class="addpop">
								<%
									String[] fileArr = {"jpg", "png", "gif"};
									if (ADFileExt == "mp4") {
								%>
								<div>
									<a class="layerOnVideo atm_addpop_img" href="${ADUrl}"
										target="_blank"
										style="position: absolute; height: 50%; width: 100%; left: 0; z-index: 99999"></a>
									<video controls class="atm_addpop_img">
										<source src="/UploadFile/AD/${ADFile}" type="video/mp4"
											class="atm_addpop_img">
										Not support HTML5 video.
									</video>
								</div>
								<%
									} else if (CommonUtil.useArraysBinarySearch(fileArr, ADFileExt)) {
								%>
								<img src="/UploadFile/AD/${ADFile}" style="width: 100%;" alt="광고" />
								<%
									}
								%>
								<c:if test="${ADUrl != ''}">
								<p class="ad_direct">
								<a href="${ADUrl}" target="_blank"><span><spring:message code="msg_0268"/></span></a>
								</p>
								</c:if>
							</div>
						</div>
						<%
							String AnswerIntro = pageContext.getAttribute("AnswerIntro") != null && !pageContext.getAttribute("AnswerIntro").equals("") ? String.valueOf(pageContext.getAttribute("AnswerIntro")) : "";
							String AnswerNickName = String.valueOf(pageContext.getAttribute("AnswerNickName"));
							String AnswerMemberSeq = pageContext.getAttribute("AnswerMemberSeq").toString();
							int AnswerLv = 0;
							if (pageContext.getAttribute("AnswerLv") != null) {
								AnswerLv = Integer.parseInt(pageContext.getAttribute("AnswerLv").toString());
							}
							String aLv = CommonUtil.getLevelName(AnswerLv, request);
						%>
						
                        <div class="sub_answer_top">
                            <h3>
                            	<c:if test="${AnswerExtraAlmoney > 0.0000}">
                                <div class="choice_wrap addAlmoney" id="ans_${AnswerSeq}" onclick="fShowExtraAlmoneyList(this, '${AnswerSeq}')">
                                    <img src="/pub/answer/answerList/images/answer_almoney.svg" alt='<spring:message code="msg_0230"/>'><span><fmt:formatNumber value="${AnswerExtraAlmoney}" pattern="#,###" /></span>
                                    <ul>
                                    </ul>
                                </div>
                                </c:if>
                                <div class="answer_choice " id="${AnswerSeq}" value="${AnswerSeq}on${PointRank}on${ADSeq}" data-seq="${AnswerSeq}">
                                	<c:if test="${FlagChoice eq 'Y'}">
                                    <p><img src="../Common/images/choice_askerC.png" alt='<spring:message code="msg_0269"/>'><spring:message code="msg_0269"/></p>
                                    </c:if>
                                    <c:if test="${PointRank eq 1 and PointSum >= 30}">
                                    <p class="choice_netizen_p"><img src="../Common/images/choice_netizenC.png" alt='<spring:message code="msg_0270"/>'><spring:message code="msg_0270"/></p>
                                    </c:if>
                                </div>
                            </h3>
                            <%
								int AnswerSeq = Integer.parseInt(pageContext.getAttribute("AnswerSeq").toString());
								
								String onAmarkClick = "";
								if ((int) AnswerLv > 90) {
									onAmarkClick = "alert(getLangStr(\"jsm_0062\")); event.stopPropagation();";
								} else if (CommonUtil.useArraysBinarySearch(lvArr, AnswerNickName)) {
									onAmarkClick = "alert(getLangStr(\"jsm_0062\")); event.stopPropagation();";
								} else {
									onAmarkClick = "fProfileAdd(this, '" + AnswerSeq + "');";
								}

								pageContext.setAttribute("onAmarkClick", onAmarkClick);
							%>
                            <table class="sub_answer_user">
                                <tr>
                                    <th rowspan="2">
                                        <figure>
                                            <c:choose>
												<c:when test="${AnswerLv ne 0 and AnswerPhoto ne ''}">
													<img src="/UploadFile/Profile/${AnswerPhoto}"  onerror="this.src='/pub/css/profile/img_thum_base0.jpg';" onclick="${onAmarkClick}">
												</c:when>
												<c:otherwise>
													<img src="/pub/css/profile/img_thum_base0.jpg" onclick="${onAmarkClick}">
												</c:otherwise>
											</c:choose>
											<c:if test="${answerList.memberSeq != 10000691 and  answerList.memberSeq != 10003513}">
											<img src="/Common/images/nation/${answerList.nation}.svg" alt="${answerList.nation}">
											</c:if>
                                        </figure>
                                        <c:if test="${answerList.memberSeq != 10000691 and  answerList.memberSeq != 10003513}">
                                        <span>${answerList.nation}</span>
                                        </c:if>
                                    </th>
                                    <th>
                                    <c:if test="${AnswerLv != 99 and AnswerNickName != msg_0236}">
                                    <span>
                                    <c:choose>
										<c:when test="${AnswerNickName eq msg_0237}">
											<spring:message code="msg_0237"/>
										</c:when>
										<c:otherwise>
											<c:if test="${AnswerLv == 1}"><spring:message code="msg_0137"/></c:if>
											<c:if test="${AnswerLv == 2}"><spring:message code="msg_0138"/></c:if>
											<c:if test="${AnswerLv == 3}"><spring:message code="msg_0139"/></c:if>
											<c:if test="${AnswerLv == 4}"><spring:message code="msg_0140"/></c:if>
											<c:if test="${AnswerLv == 5}"><spring:message code="msg_0141"/></c:if>
											<c:if test="${AnswerLv == 6}"><spring:message code="msg_0142"/></c:if>
											<c:if test="${AnswerLv == 7}"><spring:message code="msg_0143"/></c:if>
											<c:if test="${AnswerLv == 8}"><spring:message code="msg_0144"/></c:if>
											<c:if test="${AnswerLv == 9}"><spring:message code="msg_0145"/></c:if>
											<c:if test="${AnswerLv == 10}"><spring:message code="msg_0146"/></c:if>
											<c:if test="${AnswerLv == 11}"><spring:message code="msg_0147"/></c:if>
										</c:otherwise>
									</c:choose>
									</span>
									</c:if>
									<div style="display:none"  class="prgNickname_A">${AnswerNickName}</div>
									<strong>${AnswerNickName}<spring:message code="msg_0271"/></strong></th>
                                    <th rowspan="2" class="atm_more_btn_wrap">
                                        <i></i>
                                        <i></i>
                                        <i></i>
                                        <ul>
                                        	<%
												String onHunHunBtnClick = "alert('" + CommonUtil.getLangMsg(request, "msg_0168") + "');";

												if ( !UserSeq.equals("0") ) {
													onHunHunBtnClick = "fAddAlmoney('A','" + pageContext.getAttribute("AnswerSeq").toString() + "');";
												}
											%>
                                            <li class="share" onclick="fshareSNS(this, 'A', ${AnswerSeq})"><a ><span></span><spring:message code="msg_0244"/></a></li>
                                            <li class="add_almoney"><a href="javascript:void(0)" onclick="<%=onHunHunBtnClick%>"><span></span><spring:message code="msg_0230"/></a></li>
                                            <%
												//int AnswerMemberSeq = Integer.parseInt(pageContext.getAttribute("AnswerMemberSeq").toString());
												if ( !UserSeq.equals("0") && AnswerLv < 90 && !AnswerMemberSeq.equals(UserSeq) && Integer.parseInt(!UserLv.equals("") ? UserLv : "0") < 90) {
											%>
                                            <li class="submit_report_btn"><a href="javascript:void(0)" onclick="fsubmitReport($(this).parents('.atm_av_el'),'A')"><span></span><spring:message code="msg_0229"/></a></li>
                                            <%
												}
											%>
                                        </ul>
                                    </th>
                                </tr>
                                <tr>
                                    <td>
                                    	<c:if test="${AnswerNickName ne msg_0237 and FlagNickName ne 'N' and AnswerLv < 90}">
                                    	<spring:message code="msg_0272"/> <b>${AnswerRateChoice}%</b> · 
                                    	</c:if>
                                    	<div class="answer_date" id="AnsD_${AnswerSeq}">${AnswerDateReg} <span>${AnswerDateReg} UTC+9</span></div> · <img src="../Common/images/icon_view.svg" alt="viewicon">${AnswerReadCount} <img src="../Common/images/icon_reply.svg" alt="replyicon">${CNTSUMReplayanswer}</td>
                                </tr>
                            </table>
                            
                            <div class="profile_mini" id='anp_${AnswerSeq}'>
                            	<c:if test="${AnswerLv != 99 and AnswerNickName != msg_0236}">
                                <table class="profile_mini_info">
                                    <tr>
                                        <th>
                                        <span>
		                                <c:choose>
											<c:when test="${AnswerNickName eq msg_0237}">
												<spring:message code="msg_0237"/>
											</c:when>
											<c:otherwise>
												<c:if test="${AnswerLv == 1}"><spring:message code="msg_0137"/></c:if>
												<c:if test="${AnswerLv == 2}"><spring:message code="msg_0138"/></c:if>
												<c:if test="${AnswerLv == 3}"><spring:message code="msg_0139"/></c:if>
												<c:if test="${AnswerLv == 4}"><spring:message code="msg_0140"/></c:if>
												<c:if test="${AnswerLv == 5}"><spring:message code="msg_0141"/></c:if>
												<c:if test="${AnswerLv == 6}"><spring:message code="msg_0142"/></c:if>
												<c:if test="${AnswerLv == 7}"><spring:message code="msg_0143"/></c:if>
												<c:if test="${AnswerLv == 8}"><spring:message code="msg_0144"/></c:if>
												<c:if test="${AnswerLv == 9}"><spring:message code="msg_0145"/></c:if>
												<c:if test="${AnswerLv == 10}"><spring:message code="msg_0146"/></c:if>
												<c:if test="${AnswerLv == 11}"><spring:message code="msg_0147"/></c:if>
											</c:otherwise>
										</c:choose>
		                                </span>
                                        ${AnswerNickName}</th>
                                        <th rowspan="2"><img title='<spring:message code="msg_0246"/>' src="/pub/css/profile/addFriends.svg" onClick="javascript:goSubmit_2('frm', '/member/partnerSave?PartnerSeq=${AnswerMemberSeq}&FlagPartner=F', 'answerList_ifrm');" alt='<spring:message code="msg_0246"/>'></th>
                                        <th rowspan="2"><img title='<spring:message code="msg_0247"/>' src="/pub/css/profile/addMento.svg" onClick="javascript:goSubmit_2('frm', '/member/partnerSave?PartnerSeq=${AnswerMemberSeq}&FlagPartner=M', 'answerList_ifrm');" alt='<spring:message code="msg_0247"/>'></th>
                                        <th rowspan="2"><img title='<spring:message code="msg_0248"/>' src="/pub/css/profile/message.svg" class="openMessage" nickname="${AnswerNickName}" memberseq="${AnswerMemberSeq}" onclick="openMessageWindow(event)" alt='<spring:message code="msg_0248"/>'></th>
                                    </tr>
                                    <tr>
                                        <td>누적수익 : <span><fmt:formatNumber value="${EarnTotal}" pattern="#,###" /></span><spring:message code="msg_0136"/></td>
                                    </tr>
                                </table>                                
                                <p><%=!AnswerIntro.equals("") ? "\"" + AnswerIntro + "\"" : "" %></p>
                                
                                <i></i>
                                <table class="profile_mini_almoney">
                                    <tr>
                                        <td><spring:message code="msg_0250"/></td>
                                        <td><spring:message code="msg_0251"/></td>
                                        <td><spring:message code="msg_0273"/></td>
                                        <td><spring:message code="msg_0272"/></td>
                                    </tr>
                                    <tr>
                                        <th><fmt:formatNumber value="${AnswerQMoney}" pattern="#,###" /><spring:message code="msg_0136"/></th>
                                        <th><fmt:formatNumber value="${AnswerAMoney}" pattern="#,###" /><spring:message code="msg_0136"/></th>
                                        <th>${AnswerCountC}</th>
                                        <th>${AnswerRateChoice}%</th>
                                    </tr>
                                </table>
                                <%
									String onAnsMarkDirBtnClick = AnswerMemberSeq.equals(UserSeq)
											? "location.href='/member/myInfo';"
											: "goSubmit('frm', '/member/otherProfileInfo?MemberSeq=" + AnswerMemberSeq + "', '');";
									pageContext.setAttribute("onAnsMarkDirBtnClick", onAnsMarkDirBtnClick);
								%>
                                <a href="javascript:void(0)" onclick="<%=onAnsMarkDirBtnClick%>"><spring:message code="msg_0253"/></a>
                                </c:if>
                            </div>
                        </div>
                        <div class="answer_tab">
                            <p class="answer_text_prev${ASirenN < maxSiren ? ' atm-ui-tab1' : ''}" rule="${AnswerSeq}" oncontextmenu="return false;"
									ondragstart="return false;" onselect="return false;"
									style="background-attachment: fixed; cursor: auto;"> 
                                <span class="preContents" id="bAns_${AnswerSeq}"><%=stripTags(Answer, "")%>...</span>
                                <input type="hidden" id="bAnst_${AnswerSeq}" value="${answerList.tSeq}" /> 
                                <%
									if (ASirenN >= maxSiren) {
								%>
								<span class="answer_slideDown blind_img"><img
										src="${libIMG_URL}/Common/images/blind_txt.png" alt=""></span>
                                <% } else {
								 	//String MemberSeq = String.valueOf(request.getAttribute("MemberSeq"));
									int AlmoneyCnt = Integer.parseInt(pageContext.getAttribute("AlmoneyCnt").toString());
									int al = Integer.parseInt(pageContext.getAttribute("al").toString());
									String answerMoreBtnClass = "";
									String answerMoreBtnText = "";
									String[] seqArr = {MemberSeq, String.valueOf(AnswerMemberSeq)};
									if (AlmoneyCnt <= 0 && CommonUtil.useArraysBinarySearch(seqArr, UserSeq) == false) {
										answerMoreBtnClass = " payment";
										answerMoreBtnText = al > 0 ? "(-" + al + CommonUtil.getLangMsg(request, "msg_0136") : "";
									}
								%>
                                <span id="answer_slideDown"><spring:message code="msg_0274"/></span> 
                                <span class="<%=answerMoreBtnClass%>" style="display:none"></span>
                                <c:set var="ans_on" value=""/>
                                <c:if test="${answerList.tSeq != '0'}"><c:set var="ans_on" value="_on"/></c:if>
                                <c:if test="${answerList.lang != targetLang}">
                                <img class="answer_lang" src="/Common/images/language${ans_on}.svg" alt='<spring:message code="msg_0233"/>' onclick="goTranslate_ans('${AnswerSeq}', 'B');">
                                </c:if>
                                <%} %>
                            </p>
                            
                            <ol>
                                <li>
                                    <div class="alscore">
                                    <%
									int MyPointCount = Integer.parseInt(pageContext.getAttribute("MyPointCount").toString());
									if (sortedMap != null || MyPointCount != 0) {
										//out.println("MyPointCount : " + MyPointCount + "<br/>");
									%>
										<%
											int i;
											if (MyPointCount > 0) {
										%>
                                        <img src="../Common/images/esti_<%=MyPointCount%>_b.png?ver=1.1" alt="">
                                        <%
                                        		i = 1;
                                        	}
											else {
												i = 0;
											}
											
											for (Entry<Integer, Integer> entry : sortedMap.entrySet()) {
												//if(i == 1 && entry.getKey() == 1) continue;
												i++;
												if (i > 3)
													break;
										%>
                                        <img src="../Common/images/esti_<%=entry.getKey()%>_b.png?ver=1.1" alt="">
                                        <%
											}
                                        %>
                                    <%}%>
                                        <span><%=PointSum%><spring:message code="msg_0275"/></span>
                                    </div>
                                    
                                </li>
                                <%
									int answerFileCount = Integer.parseInt(pageContext.getAttribute("answerFileCount").toString());
									if (ASirenN < maxSiren) {
								%>
                                <li><spring:message code="msg_0276"/><strong id="txt_length_${AnswerSeq}"><%=AnswerText%></strong><spring:message code="msg_0277"/></li>
                                <%
									}
                                %>
                            </ol>
                            
                            <div class="profileData" style="display: none;">
								<p
									data-nick="<%=AnswerNickName.replaceAll(String.valueOf((char)39), String.valueOf((char)34))%>"
									data-lv="<%=aLv%>"
									data-intro="<%=AnswerIntro.replaceAll(String.valueOf((char)39), String.valueOf((char)34))%>"
									data-qmoney="<fmt:formatNumber value="${AnswerQMoney}" pattern="#,###" />"
									data-amoney="<fmt:formatNumber value="${AnswerAMoney}" pattern="#,###" />"
									data-countc="${AnswerCountC}" data-ratec="${AnswerRateChoice}"
									data-btn="<%=AnswerMemberSeq.equals(UserSeq) ? "location.href='/member/myInfo'" : "goSubmit('frm', '/member/otherProfileInfo?MemberSeq="+AnswerMemberSeq+"', '');"%>"
									data-aseq="${AnswerMemberSeq}"
									data-addfriends="javascript:goSubmit('frm', '/member/partnerSave?PartnerSeq=${AnswerMemberSeq}&FlagPartner=F', 'answerList_ifrm');"
									data-addmento="javascript:goSubmit('frm', '/member/partnerSave?PartnerSeq=${AnswerMemberSeq}&FlagPartner=M', 'answerList_ifrm');"
									data-earntotal="<fmt:formatNumber value="${EarnTotal}" pattern="#,###" />"></p>
							</div>
                        </div>
                        <div class="answer_slide"></div>
						
						<input type="hidden" value="${ADSeq}" name="ADSeq" />
						<input type="hidden" value="${tSeq}" name="tSeq" />
                    </div>
                    
					<script>
						fSetAnswer();
						sortAnswer();
						formatDateForSpan('AnsD_'+${AnswerSeq});
					</script>
					</c:forEach>
					
					<%
					boolean isAnswered = Boolean.parseBoolean(pageContext.getAttribute("isAnswered").toString());
					if (isAnswered == false) {
					%>
					<script>
						document.getElementById('notAnswerbtn').style.display = 'inline-block';
						document.getElementById("isAnswerbtn").remove();
					</script>
					<%
						} else {
					%>
					<script>
						document.getElementById('isAnswerbtn').style.display = 'inline-block';
						document.getElementById("notAnswerbtn").remove();
					</script>
					<%
						}
					%>
					<%
						if (developSeq != "null" && developSeq != null && developSeq != "") {
					%>
					<div class="develop_origin_btn">
						(<%=developSeq%>)
						<p onclick="location.href='/answer/answerList?Seq=${QuestionSeq}';"><spring:message code="msg_0278"/></p>
					</div>
					<%
						}
					%>
					<!--
                    <div class="answer_list_next">
                        <ul>
                            <li><a href="javascript:history.back()"><img src="../Common/images/list_icon_c.svg" alt='<spring:message code="msg_0279"/>'></a></li>
                        </ul>
                    </div>
                    -->
                </div>
            </div>
            
        </div>

    </div>
	
    <div class="more_btn_list">
        <div id="shareWrap" class="shareWrap" <c:if test="${UserSeq ne ''}">data-recmd='${RecommendCode}'</c:if>>
            <table>
                <tr>
                    <td><a href="javascript:void(0)" title='<spring:message code="msg_0280"/>' class="prgSnsKakaoT"><img src="https://developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_medium.png" alt='<spring:message code="msg_0281"/>'><spring:message code="msg_0281"/></a></td>
                    <td><a href="javascript:void(0)" title='<spring:message code="msg_0282"/>' class="prgSnsKakaoS"><img src="${libIMG_URL}/Common/images/share_kakaoS.png" alt='<spring:message code="msg_0283"/>'><spring:message code="msg_0283"/></a></td>
                    <td><a href="javascript:void(0)" title='<spring:message code="msg_0284"/>' class="prgShareNaver"><img src="${libIMG_URL}/Common/images/share_naver.png" alt='<spring:message code="msg_0285"/>'><spring:message code="msg_0285"/></a></td>
                </tr>
                <tr>
                    <td><a href="javascript:void(0)" title='<spring:message code="msg_0286"/>' class="prgShareFB"><img src="${libIMG_URL}/Common/images/share_FB.png" alt='<spring:message code="msg_0287"/>'><spring:message code="msg_0287"/></a></td>
                    <td><a href="javascript:void(0)" title='<spring:message code="msg_0288"/>' class="prgShareTwitter"><img src="${libIMG_URL}/Common/images/share_twitter.png" alt='<spring:message code="msg_0289"/>'><spring:message code="msg_0289"/></a></td>
                    <td><a onclick="copyclipboard(this)" title='<spring:message code="msg_0290"/>' id="copyContent"  class="copyContent"
							data-source="http://www.altong.com/answer/answerList?Seq=${QuestionSeq}<c:if test="${UserSeq ne ''}">&recmd=${RecommendCode}</c:if>">
							<img src="${libIMG_URL}/Common/images/share_link.png" alt='<spring:message code="msg_0290"/>'><spring:message code="msg_0290"/></a></td>
                </tr>
            </table>
        </div>
		<form name="frmExtra" style="margin: 0;" onsubmit="return false;">
		<input type="hidden" name="QuestionSeq" value="${QuestionSeq}" />
		<input type="hidden" name="AnswerSeq" />
        <div class="add_almoney_popup" id="AddAlmoneyPopup">
            <ul>
                <li>
                    <h3><spring:message code="msg_0291"/></h3>
                </li>
                <li>
                    <p><spring:message code="msg_0292"/> <br> <spring:message code="msg_0293"/> <span id="hunhunal_num"><fmt:formatNumber value="${no_money}" pattern="#,###"/></span><spring:message code="msg_0294"/></p>
                </li>
                <li class="add_almoney_input">
                <input name="ExtraAlmoney" type="number" placeholder="300~10,000" min="300" step="100" autocomplete="off" autofocus><spring:message code="msg_0136"/>
                </li>
                <li>
                    <button type="reset" onclick="fAddAlmoney();"><spring:message code="msg_0159"/></button>
                </li>
                <li>
                    <button type="button" onclick="blur();fAjax_answer('/answer/extraAlmoney', 'frmExtra', 'ACT=ExtraAlmoney');"><spring:message code="msg_0160"/></button>
                </li>
            </ul>
        </div>
		</form>
		
		<form name="frmExtraT" style="margin: 0;" onsubmit="return false;">
		<input type="hidden" name="tSeq" />
        <div class="add_almoney_popup_t" id="tAddAlmoneyPopup" style="display:none">
            <ul>
                <li>
                    <h3><spring:message code="msg_0291"/></h3>
                </li>
                <li>
                    <p><spring:message code="msg_0292"/> <br> <spring:message code="msg_0293"/> <span id="hunhunal_num"><fmt:formatNumber value="${no_money}" pattern="#,###"/></span><spring:message code="msg_0294"/></p>
                </li>
                <li class="add_almoney_input">
                <input name="ExtraAlmoney" type="number" placeholder="300~10,000" step="100" autocomplete="off" autofocus><spring:message code="msg_0136"/>
                </li>
                <li>
                    <button type="reset" onclick="fAddAlmoney();"><spring:message code="msg_0159"/></button>
                </li>
                <li>
                    <button type="button" onclick="blur();fAjax_answer('/answer/extraAlmoney', 'frmExtra', 'ACT=ExtraAlmoney');"><spring:message code="msg_0160"/></button>
                </li>
            </ul>
        </div>
		</form>
		
		<form name="frmSiren" onSubmit="return false;">
		<!-- target seq -->
			<input type="hidden" name="H_Seq">
			<!-- target type -->
			<input type="hidden" name="H_Type">
        <div class="submit_report">
        	
            <h4><img src="/pub/answer/answerList/images/atm_more_3.png" alt='<spring:message code="msg_0229"/>'><spring:message code="msg_0229"/></h4>
            <div class="report_wrap">
                <div class="report_target">
                    <table>
                        <tr>
                            <th><spring:message code="msg_0295"/></th>
                            <td class="prgSirenTarget"></td>
                        </tr>
                        <tr>
                            <th><spring:message code="msg_0296"/></th>
                            <td class="prgSirenContent"></td>
                        </tr>
                    </table>
                </div>
                <div class="report_reason">
                    <p>- <spring:message code="msg_0297"/></p>
                    <table>
                        <tr>
                            <td>
                                <label>
                                    <input type="radio" name="H_Reason" value="1"> <spring:message code="msg_0298"/>
                                </label>
                            </td>
                            <td>
                                <label>
                                    <input type="radio" name="H_Reason" value="2"> <spring:message code="msg_0299"/>
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label>
                                    <input type="radio" name="H_Reason" value="3"> <spring:message code="msg_0300"/>
                                </label>
                            </td>
                            <td>
                                <label>
                                    <input type="radio" name="H_Reason" value="4"> <spring:message code="msg_0301"/>
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label>
                                    <input type="radio" name="H_Reason" value="5"> <spring:message code="msg_0302"/>
                                </label>
                            </td>
                            <td>
                                <label>
                                    <input type="radio" name="H_Reason" value="4"> <spring:message code="msg_0303"/>
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label>
                                    <input type="radio" name="H_Reason" value="5"> <spring:message code="msg_0304"/>
                                </label>
                            </td>
                            <td>
                                <label>
                                    <input type="radio" name="H_Reason" value="4"> <spring:message code="msg_0021"/>
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <textarea name="H_Reason_txt" maxlength="1000" placeholder='<spring:message code="msg_0305"/>'></textarea>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="report_bottom">
                    <p><i class="material-icons">warning</i></p>
                    <p><spring:message code="msg_0306"/><br> <spring:message code="msg_0307"/></p>
                </div>
                <div class="report_btn">
                    <table>
                        <tr>
                            <td>
                                <input type="reset" value='<spring:message code="msg_0159"/>' onClick="fsubmitReport();">
                            </td>
                        	<td>
                                <input type="submit" value='<spring:message code="msg_0308"/>' onclick="fAjax_answer('/answer/siren', 'frmSiren', 'ACT=RegSiren');">
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        </form>
        
        <div class="user_message">
			<h5>
			<span class="message_username" style="max-width: 100px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; display: inline-block; vertical-align: middle; margin-bottom: 4px"><spring:message code="msg_0310"/></span><spring:message code="msg_0309"/>
	        </h5>
	        <form action="frm_msgSend" onSubmit="return false;">
	            <input type="hidden" name="MemberSeq" id="recvSeq">
	            <textarea name="Contents" id="sndMsg" class="message_usertext"></textarea>
	        </form>
	        <p class="message_btns">
	            <span class="message_cancle"><spring:message code="msg_0159"/></span>
	            <span class="message_send" onclick="fAjax_answer('/message/messageAjax', 'frm_msgSend', 'ACT=SEND')"><spring:message code="msg_0311"/></span>
	        </p>
	    </div>
    </div>
    
<!--
    <div id="overlay">
        <div class="addpop">
            <img src="http://image.altong.com/UploadFile/AD/581_0.png" alt=<spring:message code="msg_0312"/>>
            <a href="#"><span><spring:message code="msg_0268"/></span></a>
        </div>
    </div>
-->
	
    <div id="top_btn">
        <a href="#">
            <span>
                <img src="/Common/images/top_btn_arrow.svg" alt="top_btn">
            </span>
        </a>
    </div>
    <div id="black_screen"></div>
	<form name="frm" method="post">
		<input type="hidden" name="QuestionSeq" value="${QuestionSeq}" />
	</form>
	<form name="frm_esti" method="post"></form>
	<iframe name="answerList_ifrm" width="100%" height="0" frameborder="0"></iframe>
	<iframe name="answerList_ifrm2" width="100%" height="0" frameborder="0"></iframe>

<script>
$(document).ready(function() {
	$('video').width($('.atm_av_con').width() - 18)
	$('video').height(($('.atm_av_con').width() - 18) * (3/4))
	
	const answerTarget = location.hash;
	$(answerTarget).css('background-color', 'lightyellow');
	history.replaceState(null, null, location.pathname + location.search);
});

$(document).on('touchstart click', document, function(e){
	if ($(e.target).hasClass('layerOn')){
		$(document).find('.layerOn').not(e.target).removeClass('layerOn');
		return;
	}
	
	if ($(e.target).parents().siblings('.layerOn').length > 0) {
		setTimeout(function(){
			$(document).find('.layerOn').removeClass('layerOn');
		}, 200);
		return;
	}
	
	$(document).find('.layerOn').removeClass('layerOn');
})

function fsubmitReport(obj, type) {
	var popup = $('.submit_report');
	var overlay = $('.profile_overlay');

	if (popup.css('display') == 'block') {
		overlay.fadeOut('fast').removeAttr('onClick');
		popup.fadeOut('fast');
		popup.parents('form[name="frmSiren"]').find('input[name="H_Seq"]').removeAttr('value');
		popup.parents('form[name="frmSiren"]').find('input[name="H_Type"]').removeAttr('value');
		document.frmSiren.reset();
	} else {
		if (type) {
			if (type == "A" && $( '#'+ obj.data('seq') ).find('.atm-ui-tab1').length > 0) {
				alert (getLangStr("jsm_0069"));
				return false;
			}
			//console.log("type : "+type)
			//console.log("obj.data('seq') : "+obj.data('seq'))
			
			popup.parents('form[name="frmSiren"]').find('input[name="H_Seq"]').val( obj.data('seq') );
			popup.parents('form[name="frmSiren"]').find('input[name="H_Type"]').val(type);
			popup.find('.prgSirenTarget').text( obj.find('.prgNickname_' + type).text() );
			popup.find('.prgSirenContent').text( obj.find('.prgContent_' + type).text() );
		}
		fAjax_answer('/answer/siren', 'frmSiren', 'ACT=CheckSiren');
	}
	$(".more_btn_list").css("display","none");
}


function fAddAlmoney(type, seq) {
	var overlay = $('.profile_overlay');
	var popup = $('#AddAlmoneyPopup');
	var content = $('.more_btn_list');

	if (popup.css('display') == 'block') {
		overlay.fadeOut('fast').removeAttr('onClick');
		popup.fadeOut('fast');
		popup.parents('form[name="frmExtra"]').find('input[name="AnswerSeq"]').removeAttr('value');
		popup.find('input[name="ExtraAlmoney"]').val('');
		content.fadeOut('fast');
	} else {
		if (type) {
			if (type == "Q") {
				$('.more_btn_list').stop().fadeIn();
		        $('.add_almoney_popup').stop().fadeIn();
				popup.parents('form[name="frmExtra"]').find('input[name="AnswerSeq"]').val('0');
			} else {
				//console.log('length : ' + $('#ansList_'+seq).find('.answer_slide').length);
				if ($('#ansList_'+seq).find('.answer_slide').css('display') != 'block') {
					alert (getLangStr("jsm_0069"));
					return false;
				}
				$('.more_btn_list').stop().fadeIn();
		        $('.add_almoney_popup').stop().fadeIn();
				popup.parents('form[name="frmExtra"]').find('input[name="AnswerSeq"]').val(seq);
			}
		}
		overlay.fadeIn().attr('onClick','fAddAlmoney()');
		popup.fadeIn();
	}
}

function goEstimate(url) {
	$.ajax({
		url: url,
		type: 'post',
		success: function(jsonOrg) {
			var data = $.parseJSON(jsonOrg);

			//답변 평가 카운트 <span> 태그
			const $target = $('#' + data.answerSeq + '_' + data.estiIdx)

			switch(data.returnCode) {
				case -1:
					alert(getLangStr("jsm_0020"));
					location.href = '/default/login';
					break;
				case -2:
					alert('<%=CommonUtil.getLangMsg(request, "msg_0314")%>');
					break;
				case 0:
					var replChk = data.ticketReplChk;
					
					$target.html(data.PointCount);
					$target.parent().parent().parent().attr('class','check_icon');
					if(data.estiIdx !== 6) {
						$target.parent().parent().parent().children('.atm_esti_directup_text').html('<%=CommonUtil.getLangMsg(request, "msg_0315")%>');
					}
					if(replChk == 'Y') {
						var confirmMsg = getLangStr("jsm_0056") + '\n\n' + getLangStr("jsm_0057") + '\n' + getLangStr("jsm_0058") + '\n' + getLangStr("jsm_0059");
						if(confirm(confirmMsg)) {
							location.href = '/roulette/game';
						}
					}
					break;
				case 1:
					alert('<%=CommonUtil.getLangMsg(request, "msg_0316")%>');
					break;
				case 2:
					alert('<%=CommonUtil.getLangMsg(request, "msg_0317")%>');
					break;
				default:
					alert('<%=CommonUtil.getLangMsg(request, "msg_0318")%>\n<%=CommonUtil.getLangMsg(request, "msg_0319")%>')
					break;
			}
		}
	});
}

function writeAdLog(adSeq, actionType) {
	var formData = new FormData();

	formData.append('AdSeq', adSeq);
	formData.append('ActionType', actionType);

	axios.post("/answer/answerAdLog", formData)
		.then(function(res) {})
		.catch(function(res) {});
	return;
	$.ajax({
		url: "/answer/answerAdLog",
		type: "post",
		data: formData,
		processData: false
	})
};

$('.atm_mymenu_xbtn').click({});

$('.sub_answer .atm_av_el').click(function(){
	$('.sub_answer .atm_av_el').css('border','2px solid #fff').css('border-bottom', '#d3d3d3 1px solid');
	$(this).css('border', '2px solid #FD8D0D');
})

function animationRan(array) {
	return array[Math.floor(Math.random() * array.length)];
}

var clickCount = false;
var clickStartTime;
var clickEndTime;

// 일정 시간 후에 광고 사라지는 코드
function fADinterval(v, c){
	var target = v;
	var count = c;
	
	document.countdown = setInterval(function(){
		if (clickCount == true) {
			clearInterval(document.countdown);
			return;
		}
		//target.prev('.atm_addpop_bgwhite').find('.ad_text').find('strong').text(count);
		if (count == 0) {
			target.find('#overlay').stop().fadeOut();
			//$(this).stop().hide();
			target.find('.answer_tab').stop().hide();
			target.find('.addpop').fadeOut(500);
			
			clearInterval(document.countdown);
		}
		count --;
	}, 0);
}
// fADstop( v, a )
/*
$('#overlay').click(function(){
	$(this).fadeOut(300);
});
*/
function fADstop(){
	//console.log("닫기 실행");
	//$(this).fadeOut(300);

	/*
	if (a == 'overlay') {
		var target = $(v).next('.addpop');
	} else {
		var target = $(v);
	}

	if (clickCount == false) {
		clearInterval(document.countdown);
		//target.find('.ad_text').hide();
		target.find('.ad_direct').fadeIn(200);
		clickCount = true;
		clickStartTime = new Date().getTime();
	}else if (clickCount == true) {
		clickEndTime = new Date().getTime();
		var clickTime = clickEndTime - clickStartTime;
		
		if (clickTime < 200) {
			return;
		}
		
		target.fadeOut(300);
		target.prev('.overlay').fadeOut(300);
		// clickCount = false;
	}
	*/
}

$('.answer_text_prev').click(function(){
	/*[18.01.10-차건환] video 자동재생 추가*/
	$(this).parent().hide()
	const $video = $(this).next().find('video')
	if($video.length != 0) {
		$video.prop('muted', true);
		const $url = $(this).next().find('a')
		if($url != undefined)
			$url.css('height', $video.height() * 0.75);
		$video[0].play();
		$video[0].onpause = function(event) {
			$video[0].play();
		}
	}

	var target = $(this).closest('.sub_answer');
	var $addpop = target.find('.addpop');
	var $addpop_img = $addpop.find('img');

	const adSeq = target.find('input[name=ADSeq]').val();
	const tSeq = target.find('input[name=tSeq]').val();
	//console.log('adSeq : ' + adSeq);
	writeAdLog(adSeq, 0);
	//return false;
	<%--/*
	var aniArray = [
		"bounce", "flash", "pulse", "rubberBand", "shake", "headShake", "swing", "tada", "wobble", "jello", "bounceIn",
		"bounceInDown", "bounceInLeft", "bounceInRight", "bounceInUp", "fadeIn", "fadeInDown", "fadeInDownBig", "fadeInLeft", "fadeInLeftBig",
		"fadeInRight", "fadeInRightBig", "fadeInUp", "fadeInUpBig", "flipInX", "flipInY", "lightSpeedIn", "rotateIn", "rotateInDownLeft",
		"rotateInDownRight", "rotateInUpLeft", "rotateInUpRight", "jackInTheBox", "rollIn", "zoomIn", "zoomInDown", "zoomInLeft",
		"zoomInRight", "zoomInUp","slideInDown", "slideInLeft", "slideInRight", "slideInUp", "heartBeat"
	]
	var aniRan;
	*/--%>

	target.find('#overlay').stop().fadeIn();
	//$(this).stop().hide();
	<%--
	setTimeout(function(){//$addpop_img.load(function(){
		if (window.frameElement ) {	
			var varUA = navigator.userAgent.toLowerCase(); //userAgent 값 얻기 
			if (varUA.indexOf("iphone")>-1||varUA.indexOf("ipad")>-1||varUA.indexOf("ipod")>-1) { 
				//IOS 일때 처리
				$addpop.css('position','fixed!important');
			} 
		}

		if ($addpop_img.height() > window.innerHeight) {//$(window).innerHeight()
			$addpop_img.height(window.innerHeight - 120);
			$addpop_img.css('width', $addpop_img.height() * 0.6).css('min-width', 315).css('min-height', 525);
			var addImgH = $addpop_img.height() + 40;
			var addImgW = $addpop_img.width();
		} else {
			var addImgH = $addpop_img.height() - 40;
			var addImgW = $addpop_img.width() - 55;
		}

		$addpop.css('height',addImgH).css('width',addImgW);
	},1);
	--%>
	clickStartTime = new Date().getTime();
	
	var imgUrl = '${libIMG_URL}';
	const parameter = target.find('.answer_choice').attr('value').split('on');
	
	//이미 열람을 했던 경우
	if(target.find('.payment').length <= 0) {
		fViewAnswer(target);
	} else {
		var userAlmoney = parseInt('<%=UserAlmoney%>' || 0),
			userSeq = 0;
		$.ajax({
			url: '/answer/answerProcess?AnswerSeq=' + parameter[0] + '&AdSeq=' + parameter[2],
			type: 'get',
			dataType: 'json',
			success: function(data) {
				fViewAnswer(target);
			},
			error: function() {
				alert('<%=CommonUtil.getLangMsg(request, "msg_0320")%>');
			}
		});
	}
});

$('.ad_direct').click(function() {
	const adSeq = $(this).parents('.sub_answer').find('input[name=ADSeq]').val();
	writeAdLog(adSeq, 2);
});
$('#replyDiv').find('textarea').on('keyup', function() {
	var count = $(this).val().length;
	const counter = $('#replyDiv').find('p.atm_reply_c6');
	if (count >= 400) {
		count = '400';
	}
	counter.html(( '<span>' + count + '</span>/400'));
});

function fViewAnswer(target)
{
	// 프론트쪽이고, 오류가 많이 발생하지 않을 것으로 보여서 비동기에 따른 변수 재처리를 고려하지 않음.
	const parameter = target.find('.answer_choice').attr('value').split('on'); // 0 : AnswerSeq, 1 : PointRank, 2 : ADSeq
	var questionSeq = '${QuestionSeq}';
	var tSeq = target.find('input[name=tSeq]').val();
	//console.log('url : /answer/answerView/getFullAnswer?AnswerSeq=' + parameter[0] + '&QuestionSeq=' + questionSeq + '&PointRank=' + parameter[1]);
	//return false;
	//console.log('parameter[0] : ' + parameter[0]);
	var bAnst = $('#bAnst_' + parameter[0]).val();
	//console.log('bAnst : ' + bAnst);
	if(bAnst > 0) {
		tSeq = bAnst;
	}
	else {
		tSeq = 0;
	}
	//console.log('tSeq : ' + tSeq);
	$.ajax({
		url: '/answer/answerView/getFullAnswer?AnswerSeq=' + parameter[0] + '&QuestionSeq=' + questionSeq + '&PointRank=' + parameter[1] + '&tSeq=' + tSeq,
		type: 'get',
		success: function(data) {
			const setAnswer = function() {
				if (target.find('.atm_addpop').is(':animated')) {
					setTimeout(setAnswer, 100);
				}
				else
				{
					if (target.parents('.answer_text_prev').find('.atm-ui-tab1').length)
					{
						target.find('.answer_slide').html(data).hide();
					}
					else 
					{
						target.find('.answer_slide').html(data);
					}
				}
			}
			setAnswer();
			fautoURL($('.sub_answer'));
			//댓글을 자동으로 열어야 하는 경우
			<%if (ARSeq != null) {%>
				var aRseq = '<%=ARSeq%>';
				if (parameter[0] == aRseq)
				{
					target.find('.answerReply').trigger('click');
					$.each(target.find('.reply_box'), function(){
						if ($(this).attr('data-seq') == aRseq)
						{
							//$(this).find('.atm_reply_con').css('background-color','#f59f00');
							//$(this).find('.atm_reply_c4').css('color','#000');
						}
					});
				}
			<%}%>
			//console.log('show!!!');
			target.find('.answer_slide').stop().slideDown();
			//fADinterval(target, 1);
		},
		error: function(request, status, error) {
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			alert('<%=CommonUtil.getLangMsg(request, "msg_0320")%>');
		}
	});
}

$(document).on('click', '.answerReply', function() {
	var target = $(this).closest('.atm_av_el');
	var parameter = target.attr('value').split('on')

	reply(this, 'replyWrapper_' + parameter[0]);
});

$(document).on('click', '.imgFile', function() {
	var img = new Image();
	img.src = $(this).attr('src');
})

function fIncreaseBodyHeight(target)
{
	var nextAnsN = target.nextAll('.atm_av_el').length;
	if (nextAnsN < 2)
	{
		if (!document.IncreaseBodyHeight) document.IncreaseBodyHeight = Object();
		if (document.IncreaseBodyHeight[nextAnsN]) return;
		document.IncreaseBodyHeight[nextAnsN] = true;
		var pb = parseInt($('body').css('padding-bottom'));
		$('body').css('background-color','#f2f2f2');
		$('body').css('padding-bottom', (nextAnsN ? 100:400) + pb + 'px');
	}
}
function reply(btn, idMyDiv){
	/*
	var objDiv = $('#' + idMyDiv);
	if(objDiv.css('display') == 'block') {
		//objDiv.slideUp();
	} else {
		objDiv.slideDown(function(){
			freplyResize($(this).find('textarea')[0]);		

			var res = $(this).find('textarea').offset().top - window.innerHeight + $(this).find('textarea').height() + 130;
			if ($(document).scrollTop() < res) {
				$('html,body').animate({scrollTop : res}, function(){$(this).find('textarea')} );//.focus()
			} else {
				$(this).find('textarea');//.focus()
			}

		});
		//console.log('replyWrapper : ' + idMyDiv.indexOf('replyWrapper'))
		if(idMyDiv.indexOf('replyWrapper') > -1) {
			objDiv.find('.reply_renew').find('p').trigger('click');
		}
	}
	*/
}
$(document).ready(function() {
	$(document).bind('DOMSubtreeModified', function() {
		var $target = $('.report-btn');

		$target.each(function(index){
			if($(this).data('member-report-cnt') > 0) {
				$(this).addClass('report-already-done');
			}
		});
	});
});

// 20201223 김주윤 - 질문 답변 훈훈알 소숫점 및 다른 글자 입력 못하게 수정
$(document).ready(function(){
	$(".add_almoney_input input").on("keyup", function() {
	   $(this).val($(this).val().replace(/[^0-9]/g,""));
	});
	
	<%
	if(!CSeq.equals("0")) {%>
		var cSeq = '<%=CSeq%>';
		
		if ($('#ansList_'+cSeq).length)
		{
			$('#ansList_'+cSeq).addClass('tab');
		}
	<%}
	%>
	
	<%// 답변이 자동으로 열려야 하는 경우
	if (!Aseq.equals("0")) {%>
		var aSeq = '<%=Aseq%>';
		if ($('#ansList_'+aSeq).length)
		{
			$('#ansList_'+aSeq).addClass('tab');
			$('#ansList_'+aSeq).find('.answer_text_prev ').trigger('click');
			
			var offset = $('#ansList_'+aSeq).offset();
			$('html, body').animate({scrollTop:offset.top}, 1000, function(){
				
			});
		}
	<%}

	//질문의 댓글을 자동으로 열어야 하는 경우
	if (!QRSeq.equals("0")) {%>
		var qRSeq = '<%=QRSeq%>';
		
		$('#replyDiv').css('display', 'block');
		
		$.each($('.replydiv_user_list'), function(index){
			if ($(this).attr('data-seq') == qRSeq)
			{
				//console.log('qRSeq : ' + $(this).attr('data-seq'));
				//console.log('index : ' + index);
				//console.log('offset : ' + $(this).offset().top);
				var offset = $(this).offset();
				$('html, body').animate({scrollTop:offset.top - 60}, 1000, function(){
					
				});
			}
		});
	<%}%>
});


$(document).on('click', '.report-btn', function() {
	var memberReportCnt = $(this).data('member-report-cnt');
	var targetSeq = $(this).data('target-seq');
	var targetType = $(this).data('target-type');

	if(memberReportCnt === 0) {
		openReportModal(targetSeq, targetType);
	} else {
		alert('<%=CommonUtil.getLangMsg(request, "msg_0321")%>');
	}
});

function fViewQReply(seq, flag, load) {
	if(seq == undefined) {
		return false;
	}
	
	var base;
	var textCount;
	if (flag == "A") {
		base = $('#replyWrapper_'+seq);
		var ajaxURL = '/answer/answerList_AReply?TargetSeq=' + seq;
		textCount = base.find('.reply_submit').find('p');
	} else {
		base = $('input[value="'+seq+'"]').closest('#replyDiv');
		var ajaxURL = '/answer/answerList_QReply?TargetSeq=' + seq;
		textCount = base.find('.reply_submit').find('.atm_reply_c6');
	}
	
	var textarea = base.find('.replydiv_el').find('textarea');
	var target = base.find('.replydiv_user'); 

	$.ajax({
		url: ajaxURL ,
		type: 'post',
		success: function(data) {
			if (load == true) {
				target.html(data);
			} else {
				textCount.html('<span>0</span>/400');
				textarea.val('');
				target.html(data);
			}
			fautoURL($(base).find('.reply_box'));
			base.siblings('.answer_reply_btn').find('.reply').find('span').text(target.find('.replydiv_user_list').length);
			
			if (load != true) {
				fViewQReply(seq, flag, true);
			}
		},
		error: function() {
			//alert('오류가 발생하여 정상적으로 처리되지 않았습니다.');
		}
	});
}

var goSubmit_origin = goSubmit;

var goSubmit = function (FormName, URL, Target, obj)
{
	var where;
	
	// 김주윤 20201222 관리자 이외 유저는 스크립트 작성 불가
	var contents = $('form[name=' + FormName + ']').find('textarea').val();
	if(contents != undefined) {
		<c:if test="${AdminSecu eq null}">
			contents = contents.replace(/(<([^>]+)>)/ig,"");
			$('form[name=' + FormName + ']').find('textarea').val(contents);
			
			contents = $('form[name=' + FormName + ']').find('textarea').val();
		</c:if>
	}
	
	if (URL.indexOf('reply') != -1 && URL.indexOf('&Flag=A') != -1)
		where = 1; //[답변 댓글 등록]
	else if (URL.indexOf('reply') != -1 && URL.indexOf('&Flag=Q') != -1)
		where = 2;
	else
	{
		if(Target == '') {
			//goSubmit_origin(FormName, URL, Target);
			location.href = URL;
			return;
		}
	}
	
	//프론트쪽이고, 오류가 많이 발생하지 않을 것으로 보여서 비동기에 따른 변수 재처리를 고려하지 않음.
	$.ajax({
		url: URL,
		data: $('form[name=' + FormName + ']').serialize(),
		type: 'post',
		success: function(data) {
			//if (data.indexOf('<!--SUCCESS-->') != -1)
			if(data.result == 'SUCCESS')
			{
				switch (where)
				{
					case 1:
						var answerSeq = $('form[name=' + FormName + ']').find('input').attr('value');
						fViewQReply(answerSeq, "A");
						break;

					case 2:
						var questionSeq = $('form[name=' + FormName + ']').find('input').attr('value');
						fViewQReply(questionSeq, "Q");
						break;
				}
				
				var replChk = data.arr;
				//console.log('arr : ' + replChk);
				if(replChk == 'Y') {
					var confirmMsg = getLangStr("jsm_0056") + '\n\n' + getLangStr("jsm_0057") + '\n' + getLangStr("jsm_0058") + '\n' + getLangStr("jsm_0059");
					if(confirm(confirmMsg)) {
						location.href = '/roulette/game';
					}
				}
			}
			else
			{
				//var js = data.split('</head>');
				//$('<div style="display:hidden"></div>').appendTo('body').html(js[1]).remove();
				alert(data.result);
			}
		},
		error: function(request, status, error) {
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			//alert('<spring:message code="msg_0320"/>');
		}
	});
}

var goConfirm_origin = goConfirm;
var goConfirm = function (FormName, URL, Message, Target, obj)
{
	var where;
	if (URL.indexOf('replydel') != -1 && URL.indexOf('&Flag=A') != -1)
		where = 1; //[답변 댓글 등록]
	else if (URL.indexOf('replydel') != -1 && URL.indexOf('&Flag=Q') != -1)
		where = 2;
	else
	{
		if(Target == '') {
			//goConfirm_origin(FormName, URL, Target);
			location.href = URL;
			return;
		}
	}

	if(!confirm( getLangStr("jsm_0129") ) ) return;
	
	$.post({
		url: URL,
		data: $('form[name=' + FormName + ']').serialize(),
		type: 'post',
		success: function(data, status) {
			if (data.indexOf('<!--SUCCESS-->') != -1)
			{
				alert('<%=CommonUtil.getLangMsg(request, "msg_0322")%>');
				
				switch (where)
				{
					case 1:
						var answerSeq = $('form[name=' + FormName + ']').find('input').attr('value');
						fViewQReply(answerSeq, 'A');
						break;

					case 2:
						var questionSeq = $('form[name=' + FormName + ']').find('input').attr('value');
						fViewQReply(questionSeq, "Q");
						break;
				}
			}
			else
			{
				var js = data.split('</head>');
				$('<div style="display:hidden"></div>').appendTo('body').html(js[1]).remove();
			}
		},
		error: function() { 
			alert('<%=CommonUtil.getLangMsg(request, "msg_0320")%>');
		}
	});
}
function goSubmit_2(FormName, URL, Target) {
    //console.log('before eval : ' + FormName)
    var FormName = eval(FormName);
    //console.log('after eval : ' + FormName)
    FormName.target = Target;
    FormName.action = "" + URL + "";
    FormName.submit();
}
</script>

</body>