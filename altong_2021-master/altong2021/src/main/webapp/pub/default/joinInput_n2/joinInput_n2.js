function NickNameReset() {
	document.join_frm_sch.FlagNickName.value = '';
}
function CertNumReset() {
	document.join_frm_sch.CertNum.value = '';
	document.join_frm_sch.FlagCertNum.value = '';
}
function JoinChk_n(FormName, URL) {
	var FormName = eval(FormName);
	if (FormName.btn_click.value == "Y") {
		alert(getLangStr("jsm_0084"));
		return false;
	}
	
	//know-recommend-code
	//ChuCode1
	//ChuCode2
	
	if($('#know-recommend-code').is(":checked") == true) {
		if(FormName.ChuCode1.value.length < 4) {
			alert(getLangStr("jsm_0085"));
			FormName.ChuCode1.focus();
			return false;
		}
		if(FormName.ChuCode2.value.length < 4) {
			alert(getLangStr("jsm_0085"));
			FormName.ChuCode2.focus();
			return false;
		}
	}
	
	var joinPhone0 = $('#joinPhone0 option:selected').val();
	var joinNation = $('#joinNation option:selected').val();
	var joinLang = $('#joinLang option:selected').val();
	
	if(joinPhone0 == "") {
		alert(getLangStr("jsm_0086"));
		FormName.Phone0.focus();
		return false;
	}
	else if (FormName.Phone1.value == "") {
		alert(getLangStr("jsm_0023"));
		FormName.Phone1.focus();
		return false;
	}
	else if (FormName.CertNum.value.length < 4) {
		alert(getLangStr("jsm_0087"));
		FormName.CertNum.focus();
		return false;
	}
	else if (FormName.FlagCertNum.value !== "Y") {
		alert('getLangStr("jsm_0088")\ngetLangStr("jsm_0089")');
		FormName.CertNum.focus();
		return false;
	}
	/*
	else if (FormName.sName.value == "") {
		alert(getLangStr("jsm_0090"));
		FormName.sName.focus();
		return false;
	}
	*/
	else if (FormName.NickName.value.length < 1) {
		alert(getLangStr("jsm_0037"));
		FormName.NickName.focus();
		return false;
	}
	else if (FormName.FlagNickName.value != "Y") {
		alert(getLangStr("jsm_0073"));
		FormName.NickName.focus();
		return false;
	}
	else if (FormName.Password1.value == "") {
		alert(getLangStr("jsm_0040"));
		FormName.Password1.focus();
		return false;
	}
	else if (checkPassword(FormName.Password1.value) === false) {
		alert(getLangStr("jsm_0091"));
		FormName.Password1.focus();
		return false;
	}
	else if (FormName.Password2.value == "") {
		alert(getLangStr("jsm_0092"));
		FormName.Password2.focus();
		return false;
	}
	else if (FormName.Password1.value != FormName.Password2.value) {
		alert(getLangStr("jsm_0093"));
		FormName.Password2.focus();
		return false;
	}
	else if (RecommendVue.radioPicked === 'know' && RecommendVue.recommenderCheck === false && RecommendVue.chuCodeFlag === false) {
		alert(getLangStr("jsm_0094"));
		return false;
	}
	else if(joinNation == "") {
		alert(getLangStr("jsm_0074"));
		FormName.nation.focus();
		return false;
	}
	else if(joinLang == "") {
		alert(getLangStr("jsm_0041"));
		FormName.lang.focus();
		return false;
	}
	else {
		var confirmAgain = false;
		if (RecommendVue.radioPicked === 'know') {
			confirmAgain = confirm('getLangStr("jsm_0098")\ngetLangStr("jsm_0099")')
		} else {
			confirmAgain = confirm('getLangStr("jsm_0097")\n' +
				'getLangStr("jsm_0098")\n' +
				'getLangStr("jsm_0099")');
		}

		if (confirmAgain) {
			FormName.btn_click.value = "Y";
			FormName.target = "_self";
			FormName.action = "" + URL + "";
			FormName.submit();
		}
	}
}
function checkPassword(pswdStr) {
	var numberOfPswdStr = pswdStr.replace(/[^0-9]/g, "");
	if (numberOfPswdStr.length > 0 && pswdStr.length >= 6 && numberOfPswdStr.length !== pswdStr.length) {
		return true;
	} else {
		return false;
	}
}
function div_OnOff(v, id) {
	// 라디오 버튼 value 값 조건 비교
	if (v == "2") {
		$('#phone-radio').attr('src', '/Common/images/btn_radio_on.png');
		$('#code-radio').attr('src', '/Common/images/btn_radio.png');
		$('.atm_celti_div1').css('display', 'none');
		document.getElementById(id).style.display = ""; // 보여줌
	} else {
		$('#phone-radio').attr('src', '/Common/images/btn_radio.png');
		$('#code-radio').attr('src', '/Common/images/btn_radio_on.png');
		$('.atm_celti_div1').css('display', 'block');
		document.getElementById(id).style.display = "none"; // 숨김
	}
}
function certChk() {
	if (document.certChk_ing) return;

	var form = document.join_frm_sch;
	if ($(form.Phone1).css('display') == 'inline-block' && form.Phone1.value == "") {
		alert(getLangStr("jsm_0023"));
		form.Phone1.focus();
	} else {
		document.certChk_ing = true;
		form.target = 'join_ifrm';
		form.action = "/default/cert/joinCert";
		form.submit();
	}
}
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
        alert(getLangStr("jsm_0022"));
        obj.value = excluChar(val);
        obj.focus();
        return false;
    }
    return true;
}
function certChk2() {
	var form = document.join_frm_sch;

	if (form.CertNum.value == "" || form.CertNum.value.length < 4) {
		alert(getLangStr("jsm_0087"));
	} else {
		form.target = 'join_ifrm';
		form.action = '/default/cert/cert';
		form.submit();
	}
}
function certChk3() {
	//NickNameChk('join_frm_sch','NickNameCheck_n2.asp');
	var form = document.join_frm_sch;
	form.target = 'join_ifrm';
	form.action = '/default/nickNameCheck_n2';
	form.submit();		
}
$(document).ready(function () {
	$("input[name=ChuCode1]").keyup(function () {
		var charLimit = $(this).attr("maxlength");
		if (this.value.length >= charLimit) {
			$("input[name=ChuCode2]").focus();
		}
	})
});
function timerStart(yy, dd, mm, hh, nn, ss) {
	var now = new Date();
	var dday = new Date(yy, dd, mm, hh, nn, ss);

	dday.setMinutes(dday.getMinutes() + 3);

	//setTimeout(timerStart(yy, dd, mm, hh, nn, ss), 1000); 
}

var uploadedImg = "";
const $photo = $('input[name="Photo"]');
$('.atm_wq_c5_button_p').click(function () {
	$photo.click();
});
$photo.change(function () {
	$.ajax({
		url: "/default/uploadProfileImg_n2",
		type: "post",
		data: new FormData($('form[name="join_frm_sch"]')[0]),
		processData: false,
		contentType: false,
		async: false,
		success: function (data, textStatus, jqXHR) {
			uploadedImg = data;
		},
		error: function (request, err, ex) {
			alert(err + " ===> " + ex);
		}
	});
	//console.log(uploadedImg)
	if(uploadedImg != "") {
		$('img.atm_wq_c5_img_prof').attr('src', ('/Uploadfile/Profile/' + uploadedImg))
		$('#idPhoto').val(uploadedImg)
		
		$('p.atm_wq_c5_button_p').css('background-color', '#2ac1bc')
		$('p.atm_wq_c5_button_p').text('<%=CommonUtil.getLangMsg(request, "msg_0153")%>')
	}
	else {
		$('img.atm_wq_c5_img_prof').attr('src', ('/Uploadfile/Profile/' + uploadedImg))
		$('#idPhoto').val(uploadedImg)
		
		$('p.atm_wq_c5_button_p').css('background-color', '#2ac1bc')
		$('p.atm_wq_c5_button_p').text('<%=CommonUtil.getLangMsg(request, "msg_0154")%>')
	}
});

'use strict';
var RecommendVue = new Vue({
	el: '.recommend-code-input',
	data: {
		radioPicked: 'know',
		recommenderConfirm: '',
		recommenderCheck: false,
		chuCode1: document.querySelector('.recommend-code-input').dataset.chucode1,
		chuCode2: document.querySelector('.recommend-code-input').dataset.chucode2,
		chuCodeFlag: eval(document.querySelector('.recommend-code-input').dataset.chucodeFlag)
	},
	methods: {
		checkRecommender: function () {
			var _this = this;
			if (this.chuCode1 === '' || this.chuCode2 === '') {
				alert(getLangStr("jsm_0085"));
				$('#recomdInfo').parent().siblings('div.col-xs-10').find('input').first().focus();
				return;
			}
			axios.get('/default/join/checkRecommender', { params: { 'RecommenderSeq': this.chuCode1 + this.chuCode2 } }).then(function (res) {
				if (res.data === '') {
					if (!alert(getLangStr("jsm_0100"))) {
						$('#know-recommend-code').siblings('div').find('input[name=ChuCode1]').select();
					}
					return;
				}
				$('#know-recommend-code').siblings('div').find('input.atm_memjoin_input3').attr('readonly','readonly');
				_this.recommenderConfirm = res.data.name + ' (' + res.data.phone + ')';
			});
		},
		onChucodeKeyUp: function (event) {
			if (event.key === 'Enter') {
				this.checkRecommender();
				return;
			}

			var key = parseInt(event.key.replace(/[^0-9]/g, ""));

			if (event.target.name === 'ChuCode1') {
				this.chuCode1 = event.target.value.replace(/[^0-9]/g, '');
				if (this.chuCode1.length >= event.target.maxLength) {
					document.querySelector('[name=ChuCode2]').select();
				}
			} else {
				this.chuCode2 = event.target.value.replace(/[^0-9]/g, '');
			}
		},
		onCorrectRecommenderClick: function (event) {
			if (this.recommenderConfirm !== '') {
				this.recommenderCheck = true;
				// document.querySelector('[name=ChuCode1]').readOnly = true;
				// document.querySelector('[name=ChuCode2]').readOnly = true;
			} else {
				alert(getLangStr("jsm_0100"));
			}
		},
		onWrongRecommenderClick: function (event) {
			this.recommenderCheck = false;
			this.recommenderConfirm = '';
			this.chuCodeFlag = false;
			this.chuCode1 = '';
			this.chuCode2 = '';
			$('#know-recommend-code').siblings('div').find('input.atm_memjoin_input3').removeAttr('readonly');
			$('#know-recommend-code').siblings('div').find('input[name=ChuCode1]').select();
		}
	},
	created: function () {
		if (this.chuCodeFlag) {
			this.checkRecommender();
		}
	}
});
function selectPhone0() {
	var form = document.join_frm_sch;
	if (form.Phone0.value == "82") {
		$(form).find('.atm_memjoin_opt').removeClass('Phone_notKorea');
		form.Phone1.focus();
	} else {
		$(form).find('.atm_memjoin_opt').addClass('Phone_notKorea');
		form.Phone4.select();
	}
}

