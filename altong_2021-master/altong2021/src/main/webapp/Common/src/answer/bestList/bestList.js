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

$(document).on('click', '.fl_item', function() {
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

function fcateNaviClick(v) {
	const $this = $(v);
	var id = $this.attr('value').split('_');
	id.pop();
	attatchCategoryItem(id);
	viewCateoriedQuestion(id);
}

function goConfirmLogin(FormName, URL) {
    var msg = confirm("로그인 후 이용할 수 있습니다.\n[확인]을 누르시면 로그인 화면으로 이동합니다.");
    var FormName = eval(FormName);

    if (msg) {
        FormName.action = "" + URL + "";
        FormName.submit();
    }
}