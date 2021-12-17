$(document).ready(function(){
    /* 나의 친구/쪽지차단 버튼 */
    $('.myPartner_friends .atm_mymentor_xbtn').click(function(e){
        e.stopPropagation();
        $(this).next().stop().toggle();
    });
    
	$('.message_cancle').click(function(e){
		$('.user_message').stop().hide();
		$('.more_btn_list').stop().hide();
		e.stopPropagation();
	});
	
});


function fHideMenu()
{
	$('#menuFriend').appendTo('Body').hide();
}

function fAjax_m(url, frm, param) {
	console.log("url : "+url+", frm : "+ frm)
	var async = false;
	
	document.xhr2 = $.ajax({
		url: url,
		type: 'post',
		data: (frm ? $('form[name=' + frm + ']').serialize() + '&' : '') + param,
		dataType: 'json',
		async: async,
		success: function (r) {
			switch (r.result) {
				case 'SEND':
					alert(getLangStr("jsm_0042"));
					$('.message_cancle').click();
					break;
				case 'SET_BLOCK':
					if (r.arr[0].isBlock == 'N')
					{
						alert(getLangStr("jsm_0043"));
						$('#select_view02 .atm_mycatebar_c2').text(r.arr[0].cnt);
						$('#select_view02 .atm_profile_el[member_seq=' + r.arr[0].MemberSeq + ']').slideUp(function(){$(this).remove()});
					}
					break;
				default:
					if (r.result) alert(r.result);
					break;
			}
		},
		error: function (r, textStatus, err) {
			if (r.responseText && r.responseText.substr(0, 13) == '<!--LOGOFF-->') { top.location = '/'; return; }
			console.log(r);
		},
		complete: function () {
			document.xhr2 = false;
		}
	});
}