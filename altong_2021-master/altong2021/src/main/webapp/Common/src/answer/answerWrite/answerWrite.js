

function NewAnswerChk(FormName, URL, StrFlag) {
	if (document.isAnswered) return;

    var FormName = eval(FormName);
	var delta = quill.getContents();
	var myEditor = document.querySelector('#editor-container');
	var html = myEditor.children[0].innerHTML;	
	var lengthSize = quill.getLength();
	var contents = quill.getText(0, lengthSize);
	var AnswerCount = contents.split(" ").join("").split('\n').join("").length;

	const text = delta.filter((op) => typeof op.insert === 'string').map((op) => op.insert).join('');

	FormName.Contents.value = html;
	var lang = $('#joinLang option:selected').val();

    if (AnswerCount == 0) {
        alert(getLangStr("jsm_0123"));
        $("#editor-container").focus();
        return false;
    }
    else if (contents.split(" ").join("") == "") {
        alert(getLangStr("jsm_0038"));
        $("#editor-container").focus();
        return false;
    }
    else if (AnswerCount < 100) {
        alert(getLangStr("jsm_0124"));
        $("#editor-container").focus();
        return false;
    }
    if(lang == '') {
		alert(getLangStr("jsm_0041"));
		return false;
	}

	document.isAnswered = true;

	FormName.FlagUse.value = StrFlag;
    FormName.target = "answer_ifrm";
    FormName.action = "" + URL + "";
    FormName.submit();
}