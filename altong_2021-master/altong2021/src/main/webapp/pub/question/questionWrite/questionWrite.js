var flagContents = true;

$(function() {
	$("#amountSelector").change(function() {
		$("#amount").val($("#amountSelector option:selected").val())
	})
});

function syncContents() {
	//if(!flagContents) {
	if(flagContents) {
		//$('input[name=Contents]').val($('input[name=Title]').val());
		var title = $('input[name=Title]').val();
		var contents = $('.ql-editor').text();
		//console.log('title length : ' + title.length);
		//console.log('contents length : ' + contents.length);
		//console.log(title.length >= contents.length);
		
		// 내용과 같은 경우에만 반영, 내용이 길면 반영 되지 않음
		if(title.length >= contents.length) {
			//console.log(title);
			$('.ql-editor').text(title);
		}
	}
}

function setCookie(name, value, exp)
{
    var date = new Date();
    date.setTime(date.getTime() + exp*3600*24*1000);
    document.cookie = name + '=' + value + ';expires=' + date.toUTCString() + ';path=/';
}

function getCookie(name)
{
    var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
    return value? value[2] : null;
}

function fPopCloseAndQuePost() {
	$('#question_popup').hide();
	$('#question_popup_wrapper').hide();
	
	quePost();
}

function fPopCloseQue(v)
{
	if (v) setCookie('queConfirm', 1, 1);
	$('#question_popup').hide();
	$('#question_popup_wrapper').hide();
}

function NewQuestionChk(FormName, URL, StrFlag) {
    var FormName = eval(FormName);
	var delta = quill.getContents();
	var myEditor = document.querySelector('#editor-container');
	var html = myEditor.children[0].innerHTML;	
	var lengthSize = quill.getLength();
	var contents = quill.getText(0, lengthSize);
	var QuestionCount = contents.split(" ").join("").split('\n').join("").length;

	const text = delta.filter((op) => typeof op.insert === 'string').map((op) => op.insert).join('');

	FormName.Contents.value = html;

    if (FormName.Title.value == "") {
        alert(getLangStr("jsm_0044"));
        FormName.Title.focus();
        return false;
    }
    else if (FormName.Title.value.split(" ").join("") == "") {
        alert(getLangStr("jsm_0038"));
        FormName.Title.focus();
        return false;
    }
    else if (FormName.Contents.value == "") {
        alert(getLangStr("jsm_0045"));
        FormName.Contents.focus();
        return false;
    }
    else if (contents.split(" ").join("") == "") {
        alert(getLangStr("jsm_0038"));
        FormName.Contents.focus();
        return false;
    }
    else if (FormName.Title.value.split(" ").join("").length < 1 && StrFlag !== 'T') {
        alert(getLangStr("jsm_0046"));
        FormName.Contents.focus();
        return false;
    }
    else if (QuestionCount < 1 && StrFlag !== 'T') {
        alert(getLangStr("jsm_0047"));
        FormName.Contents.focus();
        return false;
    }
    else if (FormName.Section2.value == "" && StrFlag !== 'T') {
        alert(getLangStr("jsm_0048"));
        FormName.Section2.focus();
        return false;
    }
    else
        FormName.FlagUse.value = StrFlag;
        FormName.target = "question_ifrm";
        FormName.action = "" + URL + "";
        FormName.submit();
}



