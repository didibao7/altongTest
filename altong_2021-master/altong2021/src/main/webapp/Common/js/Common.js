// 숫자체크
function checkNum(obj) {
    function isNumeric(str) {
        for (i = 0; i < str.length; i++)
            if (str.charAt(i) < '0' || str.charAt(i) > '9') return false;
        return true;
    }

    function excluChar(str) {
        var val = "";
        for (i = 0; i < str.length; i++)
            if (str.charAt(i) >= '0' && str.charAt(i) <= '9') val += str.charAt(i);
        return val;
    }

    var val = obj.value;

    if (!isNumeric(val)) {
        alert("숫자만 입력 가능합니다.");
        obj.value = excluChar(val);
        obj.focus();
        return false;
    }
    return true;
}



//엔터
function EnterChk(FormName, Target) {
    var FormName = eval(FormName);

    if (event.keyCode == 13) {
        LoginChk(FormName, Target);
    }
}



//로그인
function LoginChk(FormName, URL) {
    var FormName = eval(FormName);

    if (FormName.UserPhone.value == "") {
        alert("아이디(휴대폰번호)를 입력하여 주세요.");
        FormName.UserPhone.focus();
        return false;
    }

    else if (FormName.UserPassword.value == "") {
        alert("비밀번호를 입력하여 주세요.");
        FormName.UserPassword.focus();
        return false;
    }
    else
        FormName.target = "ifrm";
    FormName.action = "" + URL + "";
    FormName.submit();
}


//패스워드찾기
function passChk(FormName, URL) {
    var FormName = eval(FormName);

    if (FormName.UserPhone.value == "") {
        alert("아이디(휴대폰번호)를 입력하여 주세요.");
        FormName.UserPhone.focus();
        return false;
    }


    else
        FormName.target = "ifrm";
    FormName.action = "" + URL + "";
    FormName.submit();
}



//전송
function goSubmit(FormName, URL, Target) {
    console.log('before eval : ' + FormName)
    var FormName = eval(FormName);
    console.log('after eval : ' + FormName)
    FormName.target = Target;
    FormName.action = "" + URL + "";
    FormName.submit();
}




//확인
function goConfirm(FormName, URL, Message, Target) {
    var msg = confirm("정말로 " + Message + "하시겠습니까?\n[확인]을 누르시면 " + Message + "됩니다.");
    var FormName = eval(FormName);

    if (msg) {
        FormName.target = Target;
        FormName.action = "" + URL + "";
        FormName.submit();
    }
}


function goConfirmLogin(FormName, URL) {
    var msg = confirm("로그인 후 이용할 수 있습니다.\n[확인]을 누르시면 로그인 화면으로 이동합니다.");
    var FormName = eval(FormName);

    if (msg) {
        FormName.action = "" + URL + "";
        FormName.submit();
    }
}


function goConfirmAnswer(URL, Message, Al) {
    var msg = confirm("답변 열람시 알포인트가\n" + Message + " 알 차감됩니다.\n열람을 원하시면 \n[확인]을 누르시기 바랍니다.\n현재 보유 알 : " + Al + "알\n");
    var FormName = eval(FormName);

    if (msg) {
        location.href = "" + URL + "";
    }
}



//페이지
function go_list_page(FormName, PageNumber) {
    var FormName = eval(FormName);

    FormName.pg.value = PageNumber;
    FormName.action = FormName.src_Target.value;
    FormName.submit();
}



// 검색
function SearchChk(FormName, URL) {
    var FormName = eval(FormName);

    if (FormName.src_Text.value == "") {
        alert("검색어를 입력하세요.");
        FormName.src_Text.focus();
        return false;
    }
    else if (FormName.src_Text.value.split(" ").join("") == "") {
        alert("공백만 입력하시면 안됩니다.");
        FormName.src_Text.focus();
        return false;
    }
    else if (FormName.src_Text.value.split(" ").join("").length < 2 && FormName.src_Kind.value !== 'Lv') {
        var obj = document.getElementById("input4")
        var index = obj.selectedIndex
        var value = obj.options[index].value
        console.log(value)
        if (value == "MemberType") {
            FormName.action = "" + URL + "";
            FormName.submit();
            return;
        }
        alert("최소 검색글자는 2자입니다.");
        console.log(FormName.src_Text)
        FormName.src_Text.focus();
        return false;
    }
    else {
        FormName.action = "" + URL + "";
        FormName.submit();
    }
}



function CertSendChk(FormName, URL) {
    var FormName = eval(FormName);

    if (FormName.Phone2.value == "") {
        alert("휴대폰 번호를 입력하세요.");
        FormName.Phone2.focus();
        return false;
    }
    else if (FormName.Phone2.value.split(" ").join("") == "") {
        alert("공백만 입력하시면 안됩니다.");
        FormName.Phone2.focus();
        return false;
    }
    else if (FormName.Phone2.value.split(" ").join("").length < 3) {
        alert("최소 입력글자는 3자입니다.");
        FormName.Phone2.focus();
        return false;
    }
    else if (FormName.Phone3.value == "") {
        alert("휴대폰 번호를 입력하세요.");
        FormName.Phone3.focus();
        return false;
    }
    else if (FormName.Phone3.value.split(" ").join("") == "") {
        alert("공백만 입력하시면 안됩니다.");
        FormName.Phone3.focus();
        return false;
    }
    else if (FormName.Phone3.value.split(" ").join("").length < 4) {
        alert("최소 입력글자는 4자입니다.");
        FormName.Phone3.focus();
        return false;
    }
    else
        FormName.target = "ifrm";
    FormName.action = "" + URL + "";
    FormName.submit();
}


function NickNameChk(FormName, URL) {
    var FormName = eval(FormName);

    if (FormName.NickName.value == "") {
        alert("닉네임을 입력하세요.");
        FormName.NickName.focus();
        return false;
    }
    else if (FormName.NickName.value.split(" ").join("") == "") {
        alert("공백만 입력하시면 안됩니다.");
        FormName.NickName.focus();
        return false;
    }
    else if (FormName.NickName.value.split(" ").join("").length < 2) {
        alert("최소 입력글자는 2자입니다.");
        FormName.NickName.focus();
        return false;
    }
    else
        FormName.target = "ifrm";
    FormName.action = "" + URL + "";
    FormName.submit();
}

function phoneChk(FormName, URL) {
    var FormName = eval(FormName);


    FormName.target = "ifrm";
    FormName.action = "" + URL + "";
    FormName.submit();
}



function JoinChk(FormName, URL) {
    var FormName = eval(FormName);



    if (FormName.FlagNickName.value != "Y") {
        alert("닉네임 중복 확인을 해주세요.");
        FormName.NickName.focus();
        return false;
    }
    else if (FormName.Password1.value == "") {
        alert("비밀번호를 입력하세요.");
        FormName.Password1.focus();
        return false;
    }
    else if (FormName.Password2.value == "") {
        alert("비밀번호확인을 입력하세요.");
        FormName.Password2.focus();
        return false;
    }
    else if (FormName.Password1.value != FormName.Password2.value) {
        alert("동일한 비밀번호를 입력하세요.");
        FormName.Password2.focus();
        return false;
    }
    else if (FormName.Email1.value == "") {
        alert("메일주소를 입력하세요.");
        FormName.Email1.focus();
        return false;
    }
    else if (FormName.Email2.value == "") {
        alert("메일주소를 입력하세요.");
        FormName.Email2.focus();
        return false;
    }
    else
        FormName.target = "ifrm";
    FormName.action = "" + URL + "";
    FormName.submit();
}

function attenChk(FormName, URL) {
    var FormName = eval(FormName);

    FormName.target = "ifrm";
    FormName.action = "" + URL + "";
    FormName.submit();

}

function QuestionChk(FormName, URL, StrFlag) {
    var FormName = eval(FormName);

    if (FormName.Title.value == "") {
        alert("제목을 입력하세요.");
        FormName.Title.focus();
        return false;
    }
    else if (FormName.Title.value.split(" ").join("") == "") {
        alert("공백만 입력하시면 안됩니다.");
        FormName.Title.focus();
        return false;
    }
    else if (FormName.Contents.value == "") {
        alert("질문 내용을 입력하세요.");
        FormName.Contents.focus();
        return false;
    }
    else if (FormName.Contents.value.split(" ").join("") == "") {
        alert("공백만 입력하시면 안됩니다.");
        FormName.Contents.focus();
        return false;
    }
    else if (FormName.Title.value.split(" ").join("").length < 1 && StrFlag !== 'T') {
        alert("제목을 한 글자 이상 입력해주세요.");
        FormName.Contents.focus();
        return false;
    }
    else if (FormName.Contents.value.split(" ").join("").length < 1 && StrFlag !== 'T') {
        alert("내용을 한 글자 이상 입력해주세요.");
        FormName.Contents.focus();
        return false;
    }
    else if (FormName.Section2.value == "" && StrFlag !== 'T') {
        alert("사례알을 설정해주세요.");
        FormName.Section2.focus();
        return false;
    }
    else
        FormName.FlagUse.value = StrFlag;
        FormName.target = "ifrm";
        FormName.action = "" + URL + "";
        FormName.submit();
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
        alert("제목을 입력하세요.");
        FormName.Title.focus();
        return false;
    }
    else if (FormName.Title.value.split(" ").join("") == "") {
        alert("공백만 입력하시면 안됩니다.");
        FormName.Title.focus();
        return false;
    }
    else if (FormName.Contents.value == "") {
        alert("질문 내용을 입력하세요.");
        FormName.Contents.focus();
        return false;
    }
    else if (contents.split(" ").join("") == "") {
        alert("공백만 입력하시면 안됩니다.");
        FormName.Contents.focus();
        return false;
    }
    else if (FormName.Title.value.split(" ").join("").length < 1 && StrFlag !== 'T') {
        alert("제목을 한 글자 이상 입력해주세요.");
        FormName.Contents.focus();
        return false;
    }
    else if (QuestionCount < 1 && StrFlag !== 'T') {
        alert("내용을 한 글자 이상 입력해주세요.");
        FormName.Contents.focus();
        return false;
    }
    else if (FormName.Section2.value == "" && StrFlag !== 'T') {
        alert("사례알을 설정해주세요.");
        FormName.Section2.focus();
        return false;
    }
    else
        FormName.FlagUse.value = StrFlag;
        FormName.target = "ifrm";
        FormName.action = "" + URL + "";
        FormName.submit();
}



function AnswerChk(FormName, URL, StrFlag) {
	if (document.isAnswered) return;

    var FormName = eval(FormName);

    if (FormName.Contents.value == "") {
        alert("답변 내용을 입력하세요.");
        FormName.Contents.focus();
        return false;
    }
    else if (FormName.Contents.value.split(" ").join("") == "") {
        alert("공백만 입력하시면 안됩니다.");
        FormName.Contents.focus();
        return false;
    }
    else if (FormName.Contents.value.split(" ").join("").split('\n').join("").length < 100) {
        alert("답변 내용을 100자 이상 입력해주세요.");
        FormName.Contents.focus();
        return false;
    }

	document.isAnswered = true;

	FormName.FlagUse.value = StrFlag;
    FormName.target = "ifrm";
    FormName.action = "" + URL + "";
    FormName.submit();
}


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

    if (AnswerCount == 0) {
        alert("답변 내용을 입력하세요.");
        $("#editor-container").focus();
        return false;
    }
    else if (contents.split(" ").join("") == "") {
        alert("공백만 입력하시면 안됩니다.");
        $("#editor-container").focus();
        return false;
    }
    else if (AnswerCount < 60) {
        alert("답변 내용을 60자 이상 입력해주세요.");
        $("#editor-container").focus();
        return false;
    }

	document.isAnswered = true;

	FormName.FlagUse.value = StrFlag;
    FormName.target = "ifrm";
    FormName.action = "" + URL + "";
    FormName.submit();
}

function MyJoinChk(FormName, URL) {
    var FormName = eval(FormName);

    if (FormName.NickName.value == "") {
        alert("닉네임을 입력하세요.");
        FormName.NickName.focus();
        return false;
    }
    else if (FormName.NickName.value.split(" ").join("") == "") {
        alert("공백만 입력하시면 안됩니다.");
        FormName.NickName.focus();
        return false;
    }
    else if (FormName.NickName.value.split(" ").join("").length < 2) {
        alert("최소 입력글자는 2자입니다.");
        FormName.NickName.focus();
        return false;
    }
    else if (FormName.Password.value == "") {
        alert("비밀번호를 입력하세요.");
        FormName.Password.focus();
        return false;
    }
    else
        FormName.target = "ifrm";
    FormName.action = "" + URL + "";
    FormName.submit();
}

String.prototype.replaceAll = function (org, dest) {
    return this.split(org).join(dest);
};

function isMobile() {
    var filter = "win16|win32|win64|mac|macintel|linux";
    if (navigator.platform) {
        if (filter.indexOf(navigator.platform.toLowerCase()) < 0) {
            return true;
        } else {
            return false;
        }
    }
}

window.onpageshow = function (event) {
    if (event.persisted) {
        document.location.reload();
    }
};

function getFormatDate(date) {
    var year = date.getFullYear();
    var month = (1 + date.getMonth());

    month = month >= 10 ? month : '0' + month;

    var day = date.getDate();

    day = day >= 10 ? day : '0' + day;

    return year + '-' + month + '-' + day;
}

function setCookie(name, value, exp)
{
    var date = new Date();
    date.setTime(date.getTime() + exp*3600*24*1000);
    document.cookie = name + '=' + value + ';expires=' + date.toUTCString() + ';path=/' + ';domain=.altong.com';
}

function getCookie(name)
{
    var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
    return value? value[2] : null;
}
