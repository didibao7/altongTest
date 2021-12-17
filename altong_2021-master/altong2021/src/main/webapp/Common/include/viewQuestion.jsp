<!-- #include virtual = "/Common/Code/Code_Mem_Lv.asp" -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.altong.web.logic.util.CodeUtil" %>
<%@ page import="java.util.*" %>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>    
<%
	CodeUtil code = new CodeUtil(request);
	
	Map<String, String> lvCd = code.getCODE_MEM_LV_CD();
	Map<String, String> lvNm = code.getCODE_MEM_LV_NM();
	
	String lvName = "0,";
	for(int i = 1; i <= lvCd.size(); i++) {
		lvName = lvName + "'" + lvNm.get(i) + "',";
	}
	
	out.println("<script>var lvName = [" + lvName + "];</script>");
%>
<style>
    .categoryDetails{
        background-color : #ffffff;/*#2ac1bc;*/
        width: 97%;
		max-width: 750px;
        display: inline-block;
        border-radius: 10px;
        padding: 15px;
		padding-bottom: 30px;
        display: none;
		position: absolute;
		z-index: 999999;
		box-shadow: 0px 0px 10px 1px grey;
		left: 0;
		right: 0;
		margin-left: auto;
		margin-right: auto;
    }
    .atm_cateadd_navi {
        font-size: 1em;
        border-bottom: black 2px solid;
        background-color: #ffffff;
        padding-bottom: 10px;
        font-weight: bold;
        text-align: left;
        width: 90%;
        margin: auto;
		display: grid;
   		grid-template-columns: 9fr 1fr;
		display: -ms-grid;
		-ms-grid-columns: 9fr 1fr;
    }
    .atm_cateadd_navi p {
        display: inline;
    }
    .atm_cateadd_con{
        width: 90%;
        margin: auto;
        margin-top: 25px;
    }

    .favorite_list {
	    /* [추가(2017.12.26): 김현구] */
	    border: 0px solid #000000;
		width: 100%;

        content: "";
        display: grid;
        margin: 0 auto;
        padding: 0px;
        grid-template-columns: 1fr 1fr 1fr;
        grid-gap: 1em;
        border-collapse:collapse;
    }

    .fl_item {
	    /* [추가(2017.12.26): 김현구] */
	    border: 0px solid #000000;
		display: table-cell;
		text-align:left;
        height: 30px;
        /* 
		width: 150px; 
		*/

        position: relative;
        vertical-align: middle;
        margin: 0 auto;
        padding: 0px;
		cursor: pointer;
		/*
        border-left: 1px solid #f5f5f5;
        border-right: 1px solid #f5f5f5;
        border-bottom:  1px solid #f5f5f5;
        border-top:  1px solid #f5f5f5;
		*/
    }
	
	#selectFinish{
		background-color: gainsboro;
		padding: 10px 0;
		width: 220px;
		color: black;
		font-weight: bold;
		font-size: 14px;
		display: inline-block;
		text-align: center;
		border-radius: 100px;
		margin-left: 0px;
		cursor: pointer;
		margin-top: 10px;
	}
	.toast { width: 250px; height: 20px; height:auto; position: fixed; left: 50%; margin-left:-125px; bottom: 100px; z-index: 9999; background-color: #383838; color: #F0F0F0; font-family: Calibri; font-size: 15px; padding: 10px; text-align:center; border-radius: 2px; -webkit-box-shadow: 0px 0px 24px -1px rgba(56, 56, 56, 1); -moz-box-shadow: 0px 0px 24px -1px rgba(56, 56, 56, 1); box-shadow: 0px 0px 24px -1px rgba(56, 56, 56, 1); }
	@media screen and (min-width: 800px) {
		.favorite_list {
			content: "";
			display: grid;
			padding: 0px;
			grid-template-columns: 1fr 1fr 1fr 1fr;
			grid-gap: 1em;
			border-collapse:collapse;
    	}
	}
	.cateNavi {padding:5px 3px; font-size:15px;}
</style>
<div class="categoryDetails">
	<div class="atm_cateadd_navi">
		<div style="-ms-grid-column: 1;"><p>전체</p></div>
		<img id="selectFinishXbtn" style="float: right;padding-bottom: 10px; height: 30px; width:20px; -ms-grid-column: 2;" src="/Common/images/btn_x_4.png">
	</div>
	<div class="atm_cateadd_con" align="center">
		<ul class="favorite_list"></ul>
		<p id="selectFinish">
			현재 범주의 질문 보기
		</p>
	</div>
</div>
<div class='toast' style='display:none'></div>

<script>
	function compare(a, b) {
		var type = typeof a, i, j;
		
		if( type == "object" ){
			if( a === null ) return a === b;
			else if( Array.isArray(a) ){ //배열인 경우
		
			//기본필터
			if( !Array.isArray(b) || a.length != b.length ) return false;
		
			//요소를 순회하면서 재귀적으로 검증한다.
			for( i = 0, j = a.length ; i < j ; i++ ){
				if(!compare(a[i], b[i]))return false;
			}
			return true;
		}
	}
	
	return a === b;
	};
	function showToast(message) {
		message = message || '하위 범주가 존재하지 않습니다.';
		$('.toast').text(message).fadeIn(400).delay(1000).fadeOut(400);
	}
	function getUrlParams() {
		var params = {};
		window.location.search.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(str, key, value) { params[key] = value; });
		return params;
	}
	function makeParameterCategory(arg) {
		var ret = {};
		for(let i = 0; i < arg.length; i++) {
			ret['Code' + (i + 1)] = arg[i];
		}
		return ret;
	}
	function getCategoryData(code) {
		var urlStr = "/member/interest/getInterest?"
		for(let key in code) {
			urlStr += key + "=" + code[key] + "&"
		}
		var ret = $.ajax({
			url : urlStr + '&time=' + new Date(),
			async: false
		}).responseText;
		var retJson = $.parseJSON(ret);
		
		for(let i = 0; i < retJson.length; i++) {
			retJson[i].codeName = retJson[i].codeName.replaceAll(';', '<br/>');
			retJson[i].codeName = retJson[i].codeName.replaceAll('@', '');
			//console.log('codeName : ' + retJson[i].codeName);
		}
		
		return retJson;
	};


	function attatchCategoryItem() {
		const $targetToAppend = $('.favorite_list');
		var parameter = makeParameterCategory(arguments[0]);
		var nextData = getCategoryData(parameter);

		if(nextData.length == 0) {
			return;
		}
		
		$targetToAppend.html('');

        /* [추가(2017.12.26): 김현구] 브라우저 종류에 따른 Setting */
        // 모바일 기기접속 CHECK
        var ua = window.navigator.userAgent.toLowerCase();
		//console.log(nextData.length);
		for(var i=0; i < nextData.length; i++) {
			let codeName = nextData[i].codeName,
				value = '';

			for(let key in nextData[i]) {
				if(key.indexOf('section') != -1) {
					value += nextData[i][key]  + 'on';
				}
			}
			
			value += nextData[i].code;
			
            cateItem = '<li class="fl_item" style="width:{width};" value="{value}">{codeName}</li>';		
			cateItem = cateItem.replaceAll('{codeName}', codeName);
			cateItem = cateItem.replaceAll('{value}', value);


            /* [추가(2017.12.26): 김현구] 브라우저 종류에 따른 Setting */
            // 모바일 접속 CHECK
            if ( /iphone/.test(ua) ||/android/.test(ua) ||/opera/.test(ua) ||/bada/.test(ua)||/rim/.test(ua) ) {
			     cateItem = cateItem.replaceAll('{width}', "80px");

                 // Google Chrome 브라우저가 아닌 경우
                 if ( !window.chrome ) {
			          if ( ((i+1) % 3) == 0 ) {
                           cateItem = cateItem + "</ul><ul class='favorite_list'>";
                      }
			     }
            }
            else {			
			     // Google Chrome 브라우저가 아닌 경우
                 if ( !window.chrome ) {
			          cateItem = cateItem.replaceAll('{width}', "150px");
			     
			     	  if ( ((i+1) % 4) == 0 ) {
                           cateItem = cateItem + "</ul><ul class='favorite_list'>";
                      }
                 }
            }
			//console.log(cateItem);
			$targetToAppend.append(cateItem);
		}

        /* [추가(2017.12.26): 김현구] 브라우저 종류에 따른 Setting */
        // 모바일 접속 CHECK
        if ( /iphone/.test(ua) ||/android/.test(ua) ||/opera/.test(ua) ||/bada/.test(ua)||/rim/.test(ua) ) {
             if ( ((i+1) % 3) == 0 ) {
		          switch ( (i+1) % 3 ) {
		               case 1:
                            cateItem = "<li class='fl_item' style='width:{width}'></li><li class='fl_item' style='width:{width}'></li><li class='fl_item' style='width:{width}'></li>";
			                break;
		               case 2:
                            cateItem = "<li class='fl_item' style='width:{width}'></li><li class='fl_item' style='width:{width}'></li>";
			                break;
                  }
		     }

             cateItem = cateItem.replaceAll('{width}', "80px");
             $targetToAppend.append(cateItem);
		}
        else { 
		     // Google Chrome 브라우저가 아닌 경우
             if ( !window.chrome ) {
                  if ( ((i+1) % 4) != 0 ) {
				       switch ( (i+1) % 4 ) {
		                   case 1:
                                cateItem = "<li class='fl_item' style='width:{width}'></li><li class='fl_item' style='width:{width}'></li><li class='fl_item' style='width:{width}'></li><li class='fl_item' style='width:{width}'></li>";
			                    break;
			               case 2:
                                cateItem = "<li class='fl_item' style='width:{width}'></li><li class='fl_item' style='width:{width}'></li><li class='fl_item' style='width:{width}'></li>";
			                    break;
			               case 3:
                                cateItem = "<li class='fl_item' style='width:{width}'></li><li class='fl_item' style='width:{width}'></li>";
			                    break;
				       }

                       cateItem = cateItem.replaceAll('{width}', "150px");
					   $targetToAppend.append(cateItem);
				  }
			 }
		}
	}

	function viewCateoriedQuestion() {
		const $questionWrapper = $('.atm_ranka12_con');
		const $navWrapper = $('.atm_cateadd_navi > div');
		
		var parameter = makeParameterCategory(arguments[0]);
		var formParameter = '';
		for(let key in parameter) formParameter += (key + '=' + parameter[key] + '&');
		var questionCount, questionData;
		var page =  arguments[1] || (parseInt(getUrlParams().pg || 1));
		var pageSize = ${n_pagesize} || 30;
		var pageCnt = ${n_pagescnt} || 7;

		/* 카테고리 아이콘 컬러 변경 */
		for(let i = 1; i <= 11; i++) {
			//console.log(i + ' : ' + $('div.swiper-slide:nth-child(' + i + ') > img:nth-child(1)').attr('src'));
			//if($('div.swiper-slide:nth-child(' + i + ') > img:nth-child(1)').attr('src') == undefined) { continue; }
			let t = $('div.swiper-slide:nth-child(' + i + ') > img:nth-child(1)').attr('src').replaceAll('_on', '');
			$('div.swiper-slide:nth-child(' + i + ') > img:nth-child(1)').attr('src', t);
		}
		var tCode = (1 + parseInt(arguments[0][0] || 0));
		var t = $('div.swiper-slide:nth-child(' + tCode + ') > img:nth-child(1)').attr('src').replaceAll('.png', '_on.png');
		$('div.swiper-slide:nth-child(' + tCode + ') > img:nth-child(1)').attr('src', t);
		$('div.swiper-slide').css('color','#fff');
		$('div.swiper-slide:nth-child(' + tCode + ')').css('color','yellow');

		/*네비 텍스트 변경*/
		$navWrapper.html('<p class="cateNavi" value="0" onClick="fcateNaviClick(this)">전체&nbsp;</p>');
		
		if (formParameter) {
			var categoryCodeName;
			$.ajax({
				url: '/answer/getCategoryCodeName?' + formParameter +  '&time=' + new Date(),
				type: 'post',
				async: false,
				success: function(data) {
					//console.log('data : ' + data);
					categoryCodeName = data[0];//$.parseJSON(data)[0];
					//console.log('categoryCodeName : ' + categoryCodeName);
				},
				error: function() {
					alert('오류발생, 다시 시도해주세요');
				}
			});
			let cnt = 0;
			for(let key in categoryCodeName) {
				if(categoryCodeName[key] === '')
					break;
				let value = "";
				for(let i = 0; i <= cnt; i++) {
					value += arguments[0][i] + "_";
				}
				$navWrapper.append('<p>&nbsp;>&nbsp;</p>');
				$navWrapper.append('<p class="cateNavi" value="' + value + '" onClick="fcateNaviClick(this)">' + categoryCodeName[key].replaceAll(';', '').replaceAll('@', '') + '</p>')
				cnt++;
			}
			$navWrapper.children(':last-child').css('color', '#ff5001')
		}
		
		var src_Sort = '<%= request.getAttribute("src_Sort")%>';
		if(src_Sort == 'null' || src_Sort == null) { src_Sort = 'DateReg'; }
		var url = '/answer/answerView/getQuestion?' + formParameter + 'pg=' + page + '&page_size=' + pageSize + '&src_Sort=' + src_Sort + '&time=';
		$.ajax({
			url: url,
			type: 'get',
			success: function(data) {
				var tmp = data;//$.parseJSON(data);
				questionCount = tmp[0][0]["count"];
				questionData = tmp[1];
				
				$questionWrapper.html('');

				var QuestionTemplate = $('#QuestionTemplate').html();
				
				for(let i = 0; i < questionData.length; i++) {
					var content = QuestionTemplate;

					for(let k in questionData[i])
					{
						if (k == 'lv')
						{
							if (questionData[i]['flagNickName'] == 'N')
								questionData[i][k] = '비공개';
							else
							{
								if (questionData[i][k] == 99)
									questionData[i][k] = '관리자';
								else
									questionData[i][k] = lvName[questionData[i][k]];
							}
						}
						else if (k == 'photo')
						{
							if (!questionData[i][k] || questionData[i]['flagNickName'] == 'N')
							questionData[i][k] = 'img_thum_base0.jpg';
						}
						else if (k == 'nickName')
						{
							if (questionData[i]['flagNickName'] == 'N')
								questionData[i][k] = '비공개';
							else
								questionData[i][k] = questionData[i][k] + '님';
						}

						content = content.replaceAll('{' + k + '}', questionData[i][k]);
					}

					$questionWrapper.append(content);

				}
		
				const $pagingWrapper = $('.atm_boardnavi');
				var pagingBtn = '<p class="atm_boardnavi_el{on}" value="{value}">{pageNumber}</p>';

				var startNum = Math.floor((page - 1) / pageCnt) * pageCnt + 1,
					endNum = startNum + (pageCnt - 1),
					totalPage = Math.ceil(questionCount / pageSize);	
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

				$(window).scrollTop(0);
				history.replaceState(null, null, '/answer/questionList?' + formParameter + 'pg=' + page + '&src_Sort=' + src_Sort);
			}
		});
	}
	
	$('.notFavorite').click(function(){
		$('.categoryDetails').css('display', 'block');
		const $this = $(this);
		
		var id = $this.attr('id');
		
		id = id.replace('categoryCode', '');
		id = parseInt(id);
		//console.log(id);
		attatchCategoryItem([id]);
		viewCateoriedQuestion([id], 1);
	});
	$(document).on('click', '.fl_item', function() {
		const $this = $(this);
		const $wrapper = $this.parent();

		//console.log($this.attr('value'))
		
		var id = $this.attr('value').split('on');
		var tmp = getUrlParams();
		var parameter = [];
		for(let key in tmp) {
			if(key.indexOf('Code') !== -1) {
				parameter.push(tmp[key]);
			}
		}
		if(compare(id,parameter)) {
			showToast();
			return;
		}
		attatchCategoryItem(id);
		viewCateoriedQuestion(id);
	});
	$(document).on('click', '.atm_boardnavi_el', function() {
		const $this = $(this);
		var tmp = getUrlParams();
		var parameter = [];
		for(let key in tmp) {
			if(key.indexOf('Code') !== -1) {
				parameter.push(tmp[key]);
			}
		}
		viewCateoriedQuestion(parameter, $this.attr('value'));
	});
	$(document).on('click', '#selectFinish', function() {
		$('.categoryDetails').css('display', 'none');
	});
	$(document).on('click', '#selectFinishXbtn', function() {
		$('.categoryDetails').css('display', 'none');
	});

	function fcateNaviClick(v) {
		const $this = $(v);
		var id = $this.attr('value').split('_');
		id.pop();
		attatchCategoryItem(id);
		viewCateoriedQuestion(id);
	}

	$(document).ready(function() {
		var tmp = getUrlParams();
		var parameter = [];
		for(let key in tmp) {
			if(key.indexOf('Code') !== -1) {
				parameter.push(tmp[key]);
			}
		}
		viewCateoriedQuestion(parameter);
	});
</script>