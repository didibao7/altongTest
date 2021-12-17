/*JQuery 확장*/
String.prototype.replaceAll = function (org, dest) {
  return this.split(org).join(dest);
};

var global = {
  "code": {},
  "list": [],
  "page": 1,
  "pageCount": Math.ceil($.parseJSON($.ajax({
    url: "/aadmin/category/getQuestionCount?" + 'Rand=' + new Date(),
    cache: false,
    type: "GET",
    async: false
  }).responseText)["Cnt"])
};
var model = {
  "getCategoryData": function getCategoryData(Code) {
    var urlStr = "/member/interest/getInterest?";

    for (var key in Code) {
      urlStr += key + "=" + Code[key] + "&";
    }

    var ret = $.ajax({
      url: urlStr,
      async: false
    }).responseText;
    return $.parseJSON(ret);
  },
  'getQuestion': function getQuestion() {
	//console.log('global.page : ' + global.page);
    var urlStr = '/aadmin/category/getQuestion?Page=' + global.page + '&Rand=' + new Date();
    var ret = $.ajax({
      url: urlStr,
      type: "GET",
      cache: false,
      async: false
    }).responseText;
    return $.parseJSON(ret);
  }
};


function changeLang(seq) {
	//ajax 처리
	var sel_val = $('#sel_' + seq + ' option:selected').val();
	//console.log('sel_val : ' + sel_val);
	
	if(sel_val == '') { return false; }
	
	var urlStr = '/aadmin/category/changeLangQuestion';
	//console.log('urlStr : ' + urlStr);
	
	$.ajax({
		url: urlStr,
		type: "post",
		data: { 'seq' : seq, 'lang' : sel_val },
		dataType: 'json',
		crossDomain: true,
		success: function (r) {
			console.log('r : ' + r.result);
		},
		error: function (r, textStatus, err) {
			console.log(r);
		},
		complete: function () {
			document.xhr = false;
		}
	});
}

var viewQuestions = function viewQuestions() {
  var questionJson = model.getQuestion();
  var $table = $('.tg');
  $table.html('<tr class="index"><th class="tg-yw4l" style="width:30px;"></th><th class="tg-yw4l" style="width:60px;">번호</th><th class="tg-yw4l" style="width:150px;">언어</th><th class="tg-yw4l" style="width:60px;">번역보기</th><th class="tg-yw4l" style="width:40%;">제목</th><th class="tg-yw4l" style="width:40%;">내용</th></tr>');

  for (var i = 0; i < questionJson.length; ++i) {
    var htmlStr = '<tr><th class="tg-yw4l"><input type="hidden" name="seq" value="{Seq}"><input type="checkbox" name="mycheck"/><input type="hidden" id="qt_{Seq}" name="qtSeq" value=""/></th><th class="tg-yw4l"><a href="/answer/answerList?Seq={Seq}" target="_blank">{Seq}</a></th><th class="tg-yw4l" style="width:60px;"><select id="sel_{Seq}" onChange="changeLang({Seq})">{lang}</select></th><th class="tg-yw4l"><img class="answer_lang" src="/Common/images/language.svg" alt="질문 번역" data-seq="{Seq}" style="cursor: pointer;"></th><th class="tg-yw4l" id="que_{Seq}">{Title}</th><th class="tg-yw4l" id="cont_{Seq}">{Contents}</th></tr>';

    for (var key in questionJson[i]) {
    	if(key != 'lang') {
      		htmlStr = htmlStr.replaceAll('{' + key + '}', questionJson[i][key]);
    	}
    	else {
    		var selStr = '	<option value="" ' + (questionJson[i][key] == ''?  'selected="selected"' : '') +'>-- select one --</option>';
			selStr += '	<option value="ko" ' + (questionJson[i][key] == 'ko'?  'selected="selected"' : '') +'>한국어</option>';		
			selStr += '	<option value="en" ' + (questionJson[i][key] == 'en'?  'selected="selected"' : '') +'>English</option>';
			selStr += '	<option value="es" ' + (questionJson[i][key] == 'es'?  'selected="selected"' : '') +'>Español</option>';
			selStr += '	<option value="fr" ' + (questionJson[i][key] == 'fr'?  'selected="selected"' : '') +'>Français</option>';
			selStr += '	<option value="pt" ' + (questionJson[i][key] == 'pt'?  'selected="selected"' : '') +'>Português</option>';
			selStr += '	<option value="de" ' + (questionJson[i][key] == 'de'?  'selected="selected"' : '') +'>Deutsch</option>';
			selStr += '	<option value="ar" ' + (questionJson[i][key] == 'ar'?  'selected="selected"' : '') +'>العربية</option>';
			selStr += '	<option value="fa" ' + (questionJson[i][key] == 'fa'?  'selected="selected"' : '') +'>فارسی</option>';
			selStr += '	<option value="ru" ' + (questionJson[i][key] == 'ru'?  'selected="selected"' : '') +'>Русский</option>';
			selStr += '	<option value="ja" ' + (questionJson[i][key] == 'ja'?  'selected="selected"' : '') +'>日本語</option>';
			selStr += '	<option value="it" ' + (questionJson[i][key] == 'it'?  'selected="selected"' : '') +'>Italiano</option>';
			selStr += '	<option value="zh" ' + (questionJson[i][key] == 'zh'?  'selected="selected"' : '') +'>中文</option>';
			selStr += '	<option value="vi" ' + (questionJson[i][key] == 'vi'?  'selected="selected"' : '') +'>Tiếng Việt</option>';
			selStr += '	<option value="hi" ' + (questionJson[i][key] == 'hi'?  'selected="selected"' : '') +'>हिन्दी</option>';
			selStr += '	<option value="bn" ' + (questionJson[i][key] == 'bn'?  'selected="selected"' : '') +'>বাংলা</option>';
			selStr += '	<option value="id" ' + (questionJson[i][key] == 'id'?  'selected="selected"' : '') +'>Bahasa Indonesia</option>';
			selStr += '	<option value="ms" ' + (questionJson[i][key] == 'ms'?  'selected="selected"' : '') +'>Bahasa Melayu</option>';
			selStr += '	<option value="tr" ' + (questionJson[i][key] == 'tr'?  'selected="selected"' : '') +'>Türkçe</option>';
			selStr += '	<option value="th" ' + (questionJson[i][key] == 'th'?  'selected="selected"' : '') +'>ไทย</option>';
			selStr += '	<option value="mn" ' + (questionJson[i][key] == 'mn'?  'selected="selected"' : '') +'>Монгол хэл</option>';
			
			htmlStr = htmlStr.replaceAll('{' + key + '}', selStr);
    	}
    }

    $table.append(htmlStr);
  }
};

var init = function init() {
  var cate1Json = model.getCategoryData();

  for (var i = 0; i < cate1Json.length; i++) {
    var htmlStr = '<option value="{code}">{codeName}</option>';

    for (var key in cate1Json[i]) {
      htmlStr = htmlStr.replaceAll('{' + key + '}', cate1Json[i][key]);
    }

    $('select[name=Section1]').append(htmlStr);
  }

  var $pagingInput = $('input[name=paging]');
  $pagingInput.attr({
    "min": 1,
    "max": global.pageCount,
    "value": 1
  });
  viewQuestions();
};

$(document).on('change', 'select', function () {
  var $this = $(this);
  var $next = $this.next();
  //console.log('id: ' + $(this).attr('id'));
  if($(this).attr('id') != undefined){ return false; }
  var idx = parseInt($(this).attr('name').charAt(7));
  var value = parseInt($('option:selected', $this).val());
  global.code['Code' + idx] = value;

  for (var i = idx + 1; i <= 5; ++i) {
    delete global.code['Code' + i];
  }

  var nextData = model.getCategoryData(global.code);
  $next.html('<option value="0">선택</option>');

  for (var _i = 0, iter = $next; _i < 4; ++_i) {
    if (!iter.hasClass('saveBtn') && !iter.hasClass('pagingNumbering') && !iter.hasClass('pagingBtn')) iter.html('<option value="0">선택</option>');
    iter = iter.next();
  }

  for (var _i2 = 0; _i2 < nextData.length; _i2++) {
    var htmlStr = '<option value="{code}">{codeName}</option>';

    for (var key in nextData[_i2]) {
    	htmlStr = htmlStr.replaceAll('{' + key + '}', nextData[_i2][key]);
    }

    $next.append(htmlStr);
  }
});
$(document).on('click', '.saveBtn', function () {

	var urlStr = '/aadmin/category/setCategory?';
	//console.log('code : ' + global.code);
  	for (var key in global.code) {
    	urlStr += key + '=' + global.code[key] + '&';
  	}
	//console.log('global : ' + global.list[0]);
	
	var total = global.list.length;
	var cnt = 0;
  	for (var i = 0; i < global.list.length; ++i) {
  		//console.log(i + ' : global -  ' + global.list[i]);
  		var url = urlStr + 'Seq=' + global.list[i];
  		//console.log('url : ' + url);
    	$.ajax({
      		url: url,
      		type: "post",
      		async: false,
      		success: function (r) {
      			cnt++;
      			//console.log('cnt : ' + cnt);
      		}
    	});
  	}
  	
	if(total > 0 && cnt == total) { location.reload(); }
});
$(document).on('click', '.tmpBtn', function () {
  var urlStr = '/aadmin/category/setCategory?';
  urlStr += 'Code1=99&Code2=99&Code3=99&Code4=99&Code5=99&';

  for (var i = 0; i < global.list.length; ++i) {
    $.ajax({
      url: urlStr + 'Seq=' + global.list[i],
      type: "post"
    });
  }

  location.reload();
});
$(document).on('click', 'input[name=mycheck]', function () {
  var $this = $(this);
  var seq = $this.parent().children('input[name=seq]').val();

  if ($this.prop('checked')) {
    global.list.push(seq);
  } else {
    global.list.splice(global.list.indexOf(seq), 1);
  }
});
$(document).on('click', '#goOtherPageBtn', function () {
  var $pagingInput = $('input[name=paging]');
  global.page = $pagingInput.val();
  viewQuestions();
});
$(document).on('click', '#goPrevPageBtn', function () {
  var $pagingInput = $('input[name=paging]');

  if ($pagingInput.val() == 1) {
    alert('이전 페이지가 존재하지 않습니다');
    return;
  }

  global.page -= 1;
  $pagingInput.attr('value', global.page);
  viewQuestions();
});
$(document).on('click', '#goNextPageBtn', function () {
  var $pagingInput = $('input[name=paging]');

  if ($pagingInput.val() == $pagingInput.attr('max')) {
    alert('다음 페이지가 존재하지 않습니다');
    return;
  }

  global.page += 1;
  $pagingInput.attr('value', global.page);
  viewQuestions();
  $('body').scrollTop('0');
});
