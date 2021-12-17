function fnGetCookie(sName) {
	var aCookie = document.cookie.split("; ");

	for (var i = 0; i < aCookie.length; i++) {
		var aCrumb = aCookie[i].split("=");
		if (sName == aCrumb[0])
			return unescape(aCrumb[1]);
	}

	return null;
}

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
}

function showToast(message) {
	message = message || getLangStr("jsm_0019");
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
	//console.log('retJson : ' + retJson);
	
	for(let i = 0; i < retJson.length; i++) {
		retJson[i].codeName = retJson[i].codeName.replace(/;/gi, '<br/>');
		retJson[i].codeName = retJson[i].codeName.replace(/@/gi, '');
		//console.log('codeName : ' + retJson[i].codeName);
	}
	
	return retJson;
}

function attatchCategoryItem() {
	const $targetToAppend = $('.favorite_list');
	var parameter = makeParameterCategory(arguments[0]);
	var nextData = getCategoryData(parameter);
	$('.fl_item').css('color','');
	$("li[name="+String(arguments[0]).replace(/,/gi,'on')+"]").css('color','rgb(253, 0, 49)');
	var nextDataCnt = nextData.length;
	if(arguments[0].length == 5) { nextDataCnt = 0; }
	if(nextDataCnt == 0) {
		return;
	}
	var currentHtml = $('.favorite_list').html();
	
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
		
		var valArr = value.split('on');
		let varArrCnt = valArr.length;
		let varItemId = varArrCnt - 1;
		//console.log('varArrCnt :  ' + (varArrCnt));
		//console.log('valArr[' + (varItemId) + '] = ' + valArr[varItemId]);
			
		let cateNm;
		//cateNm = codeName;
		
		if(varArrCnt == 2) {
			cateNm = getLangStr("cate2_" + valArr[varItemId]);
		}
		else if(varArrCnt == 3) {
			cateNm = getLangStr("cate3_" + valArr[varItemId]);
		}
		else if(varArrCnt == 4) {
			cateNm = getLangStr("cate4_" + valArr[varItemId]);
		}
		else if(varArrCnt == 5) {
			cateNm = getLangStr("cate5_" + valArr[varItemId]);
		}
		
		cateItem = '<li class="fl_item" name="{value}" value="{value}">{codeName}</li>';
		
		cateItem = cateItem.replace(/{codeName}/gi, cateNm);
		cateItem = cateItem.replace(/{value}/gi, value);
		//console.log("'"+String(arguments[0]).replaceAll(',','on')+"'");
		//console.log(value)
		//if(value == String(arguments[0]).replaceAll(',','on')){
		//	console.log(value)
		//}
		
		/* [추가(2017.12.26): 김현구] 브라우저 종류에 따른 Setting */
		// 모바일 접속 CHECK
		if ( /iphone/.test(ua) ||/android/.test(ua) ||/opera/.test(ua) ||/bada/.test(ua)||/rim/.test(ua) ) {
				cateItem = cateItem.replace(/{width}/gi, "80px");
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
					cateItem = cateItem.replace(/{width}/gi, "150px");
				
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
						cateItem = "<li class='fl_item' ></li><li class='fl_item'></li><li class='fl_item' ></li>";
						break;
					case 2:
						cateItem = "<li class='fl_item' ></li><li class='fl_item'></li>";
						break;
				}
			}
			// cateItem = cateItem.replaceAll('{width}', "80px");
			//console.log("1. "+cateItem);
			//$targetToAppend.append(cateItem);
	}
	else { 
		// Google Chrome 브라우저가 아닌 경우
		if ( !window.chrome ) {
			if ( ((i+1) % 4) != 0 ) {
				switch ( (i+1) % 4 ) {
					case 1:
						cateItem = "<li class='fl_item' ></li><li class='fl_item' ></li><li class='fl_item'></li><li class='fl_item'></li>";
						break;
					case 2:
						cateItem = "<li class='fl_item'></li><li class='fl_item'></li><li class='fl_item'></li>";
						break;
					case 3:
						cateItem = "<li class='fl_item'></li><li class='fl_item'></li>";
						break;
				}
				cateItem = cateItem.replace(/{width}/gi, "150px");
				//console.log("2. "+cateItem);
				$targetToAppend.append(cateItem);
			}
		}
	}
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
	$(this).css('style','color: rgb(253, 0, 49);')
	const $this = $(this);
	const $wrapper = $this.parent();
	//console.log($this.attr('value'));
	var id = $this.attr('value').split('on');
	var tmp = getUrlParams();
	//console.log('id : ' + id);
	//console.log('tmp : ' + tmp);
	var parameter = [];
	for(let key in tmp) {
		//console.log('key : ' + key);
		if(key.indexOf('Code') !== -1) {
			parameter.push(tmp[key]);
		}
	}
	//console.log('compare : ' + compare(id,parameter));
	if(compare(id,parameter)) {
		//showToast();
		//return;
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

$(document).on('click', '#selectFinishXbtn', function() {
	var tmp = getUrlParams();
	var parameter = [];
	var cd = 1;
	for(let key in tmp) {
		if(key.indexOf('Code') !== -1) {
			parameter.push(tmp[key]);
			//console.log('key : ' + key);
			//console.log('tmp[key] : ' + tmp[key]);
			if(key == 'Code1')
			{
				if(tmp[key] == '1') cd = 2;
				if(tmp[key] == '2') cd = 3;
				if(tmp[key] == '3') cd = 4;
				if(tmp[key] == '4') cd = 5;
				if(tmp[key] == '5') cd = 6;
				if(tmp[key] == '6') cd = 7;
				if(tmp[key] == '7') cd = 8;
				if(tmp[key] == '8') cd = 9;
				if(tmp[key] == '9') cd = 10;
				if(tmp[key] == '10') cd = 11;
			}
		}
	}
	/* 카테고리 아이콘 컬러 변경 */
	for(let i = 1; i <= 11; i++) {
		let t = $('li.swiper-slide:nth-child(' + i + ') > a > img:nth-child(1)').attr('src').replace('_on', '');
		$('li.swiper-slide:nth-child(' + i + ') > a > img:nth-child(1)').attr('src', t);
		$('li.swiper-slide:nth-child(' + i + ')').attr('class').replace('list_select','')
	}
	var tCode = cd;
	//김주윤
	var t = $('li.swiper-slide:nth-child(' + tCode + ') > a > img:nth-child(1)').attr('src').replace('.svg', '_on.svg');
	$('li.swiper-slide:nth-child(' + tCode + ') > a > img:nth-child(1)').attr('src', t);
	$('li.swiper-slide').css('color','#fff');
	$('li.swiper-slide:nth-child(' + tCode + ')').css('color','yellow');
	//viewCateoriedQuestion(parameter);
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
	var cd = 1;
	for(let key in tmp) {
		if(key.indexOf('Code') !== -1) {
			parameter.push(tmp[key]);
			//console.log('key : ' + key);
			//console.log('tmp[key] : ' + tmp[key]);
			if(key == 'Code1')
			{
				if(tmp[key] == '1') cd = 2;
				if(tmp[key] == '2') cd = 3;
				if(tmp[key] == '3') cd = 4;
				if(tmp[key] == '4') cd = 5;
				if(tmp[key] == '5') cd = 6;
				if(tmp[key] == '6') cd = 7;
				if(tmp[key] == '7') cd = 8;
				if(tmp[key] == '8') cd = 9;
				if(tmp[key] == '9') cd = 10;
				if(tmp[key] == '10') cd = 11;
			}
		}
	}
	/* 카테고리 아이콘 컬러 변경 */
	for(let i = 1; i <= 11; i++) {
		let t = $('li.swiper-slide:nth-child(' + i + ') > a > img:nth-child(1)').attr('src').replace('_on', '');
		$('li.swiper-slide:nth-child(' + i + ') > a > img:nth-child(1)').attr('src', t);
		$('li.swiper-slide:nth-child(' + i + ')').attr('class').replace('list_select','')
	}
	var tCode = cd;
	//김주윤
	var t = $('li.swiper-slide:nth-child(' + tCode + ') > a > img:nth-child(1)').attr('src').replace('.svg', '_on.svg');
	$('li.swiper-slide:nth-child(' + tCode + ') > a > img:nth-child(1)').attr('src', t);
	$('li.swiper-slide').css('color','#fff');
	$('li.swiper-slide:nth-child(' + tCode + ')').css('color','yellow');
	//viewCateoriedQuestion(parameter);
});

function goConfirmLogin(FormName, URL) {
    var msg = confirm(getLangStr("jsm_0020") + '\n' +  getLangStr("jsm_0021"));
    var FormName = eval(FormName);

    if (msg) {
        FormName.action = "" + URL + "";
        FormName.submit();
    }
}