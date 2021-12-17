const mask = document.querySelector('.mask');
const sendMsg = document.querySelector('.send_popup');
const receiveMsg = document.querySelector('.receive_popup');

const addToast = document.querySelector('.block_add_toast');
const removeToast = document.querySelector('.block_remove_toast');

function checkDeviceSize() {
  const receiveWidth = receiveMsg.offsetWidth;
  const receiveHeight = receiveMsg.offsetHeight;
  const sendWidth = sendMsg.offsetWidth;
  const sendHeight = sendMsg.offsetHeight;
  const deviceWidth = window.innerWidth;
  const deviceHeight = window.innerHeight;

  addToast.style.left = (deviceWidth - addToast.offsetWidth) / 2 + 'px';
  addToast.style.top = (deviceHeight - addToast.offsetHeight) / 2 + 'px';

  removeToast.style.left = (deviceWidth - removeToast.offsetWidth) / 2 + 'px';
  removeToast.style.top = (deviceHeight - removeToast.offsetHeight) / 2 + 'px';

  receiveMsg.style.left = (deviceWidth - receiveWidth) / 2 + 'px';
  receiveMsg.style.top = (deviceHeight - receiveHeight) / 2 + 'px';
  sendMsg.style.left = (deviceWidth - sendWidth) / 2 + 'px';
  sendMsg.style.top = (deviceHeight - sendHeight) / 2 + 'px';
}

function openMessage(target) {
  if ($(target).closest('#select_view01').length) {
    document.querySelector('.msg_write_ta01').value = '';
    receiveMsg.style.display = 'block';
    sendMsg.style.display = 'none';
    
    $('.receive_popup').stop().show();
    $('#black_screen').stop().show();
    
    fSetMessage(target);
  } else {
    document.querySelector('#select_view02').value = '';
    receiveMsg.style.display = 'none';
    sendMsg.style.display = 'block';
    
    $('.send_popup').stop().show();
    $('#black_screen').stop().show();
    
    fSetMessage(target);
  }
}

function selectView() {
  $('.select_view').hide();
  $('#' + $('.temp_tab_on').attr('tar_obj')).fadeIn(300);

  $('.select_div').click(function(event) {
    let selectedBtnTab = $(event.target);
	if (!selectedBtnTab.attr('tar_obj'))
		 selectedBtnTab = selectedBtnTab.closest('.select_btn');
    let idName = selectedBtnTab.attr('tar_obj');
    if (!idName) return;
    if (!selectedBtnTab.hasClass('temp_tab_on')) {
      $('.select_view').hide();
      $('.select_btn').removeClass('temp_tab_on');

      selectedBtnTab.addClass('temp_tab_on');
      $('#' + idName).fadeIn(300);

      if (typeof libCallBackSelectView == 'function') {
        libCallBackSelectView(idName);
      }
    }
  });
}

function libCallBackSelectView(idName) {
  pg = 0;
  $('#' + idName).find('.msg_el_con:not(:first)').remove();

  switch (idName) {
    case 'select_view01':
      fAjax_List('ACT=LIST_RECEIVE');
      break;
    case 'select_view02':
      fAjax_List('ACT=LIST_SEND');
      break;
  }
}



function fAjax_msg(url, frm, param) {
	if (document.xhr2) {
		//$('#Tip').text('이전 작업이 진행중입니다.').css('display', 'block');
		//setTimeout(function () { $('#Tip').stop().fadeOut(300) }, 3000);
		//return;
	}
	var async = false;
	
	if(url == '') {
		url = '/message/messageAjax';
		//console.log('ACT=GET_MSG : ' + param.indexOf('ACT=GET_MSG'));
		if(param.indexOf('ACT=GET_MSG') > -1) {
			async = true;
		}
	}
	
	document.xhr2 = $.ajax({
		url: url,
		type: 'post',
		data: (frm ? $('form[name=' + frm + ']').serialize() + '&' : '') + param,
		dataType: 'json',
		async: async,
		success: function (r) {
			//console.log('result : ' + r.result);
			switch (r.result) {
				case 'SEND':
					alert(getLangStr("jsm_0009"));
					setTimeout(function(){
						$('.select_btn[tar_obj=select_view02]').removeClass('temp_tab_on').click();
						$('.msg_write_ta01').val('');
						$('.msg_write_ta02').val('');
						$('.receive_popup').stop().hide();
				        $('.send_popup').stop().hide();
				        $('#black_screen').stop().hide();
					}, 1);
					break;
				case 'MSG_DEL':
					$('.msg_el_con[msg_seq=' + r.arr[0].MsgSeq + ']').slideUp(300, function(){$(this).remove()});
					$('.receive_popup').stop().hide();
					$('.send_popup').stop().hide();
					$('#black_screen').stop().hide();
					break;
				case 'SET_BLOCK':
					if (r.arr[0].isBlock == 'Y')
					{
						fShowBlock(true, true);
						$('.msg_el_con[member_seq=' + r.arr[0].MemberSeq + ']').css('border-color','red');
					}
					else
					{
						fShowBlock(false, true);
						$('.msg_el_con[member_seq=' + r.arr[0].MemberSeq + ']').css('border-color','#e3e3e3');
					}
					break;
				case 'GET_MSG':
					fSetMsg(r);
					break;
				default:
					if (r.result) alert(r.result);
					break;
			}
		},
		error: function (r, textStatus, err) {
			if (r.responseText && r.responseText.substr(0, 13) == '<!--LOGOFF-->') { top.location = '/'; return; }
			//console.log(r);
		},
		complete: function () {
			document.xhr2 = false;
		}
	});
}

var pg = 0;
function fAjax_List(data) {
  //if (document.xhr2) return;

  if (pg++) {
    //$('#divProg').slideDown(300);
    setTimeout(fMakeRow, 1000);
  } else fMakeRow();
	
  document.xhr2 = $.ajax({
	url: '/message/messageAjax',
    type: 'post',
    data: 'pg=' + pg + (data ? '&' + data : ''),
    dataType: 'json',
    success: function(r) {
	  //console.log("r : ")
      switch (r.result) {
        case 'LIST_RECEIVE':
        case 'LIST_SEND':
          r.arr.sort(function(a,b){
            return b["seq"] - a["seq"];
          })
          document.r = r;
          fMakeRow();
          break;
      }
    },
    error: function(r, textStatus, err) {
      $('#divProg').slideUp();
      $(window).unbind('scroll');
      //console.log(r);
    }
  });
}

function fMakeRow() {
  if (!document.isShow) {
    document.isShow = true;
    return;
  }

  var $frame = (document.r.result == 'LIST_RECEIVE') ? $('#select_view01') : $('#select_view02');

  var newObj, target = $frame.find('.msg_el_con:first');
  document.r.arr.forEach(function(v) {
    newObj = target.clone();
    newObj.attr('msg_seq', v.seq).attr('member_seq', v.writerSeq);
    
    if (v.blockMemberSeq) {
  	  newObj.css('border-color', 'red');
  	}
    newObj.find('.msg_user_img').attr('src', '/UploadFile/Profile/' +(v.writerPhoto ? v.writerPhoto : ''));
    newObj.find('.msg_user_img').attr('onerror', "this.src='/pub/css/profile/img_thum_base0.jpg';");
    newObj.find('.msg_user_name').text(v.writerNic);
    newObj.find('.msg_time').text(v.conDate);
    newObj.find('.msg_user_text').text(v.contents);
    newObj.find('.msg_user_read').text(v.is_read);
    newObj.appendTo($frame).slideDown();
  });
  
  $(window).unbind('scroll');
  if (document.r.arr.length)
  {
  	$(window).scroll(function() {
  	  if ($(window).scrollBottom() < 150) fAjax_List('ACT=' + document.r.result);
  	});
  }

  $('#divProg').slideUp();
  document.isShow = false;
  document.xhr2 = false;
}

function fMsgDel(e, obj)
{
	e.stopPropagation();

	if (confirm(getLangStr("jsm_0008")))
	{
		var MsgSeq = $(obj).closest('.msg_el_con').attr('msg_seq');
		fAjax_msg('', '', 'ACT=MSG_DEL&MsgSeq=' + MsgSeq);
	}
}

function closeAll() {
  sendMsg.style.display = 'none';
  receiveMsg.style.display = 'none';
}

function fGetMsg(obj, way)
{
	var $frame = $(obj).closest('.user_message');
	var MsgSeq = $frame.find('input[name=MsgSeq]').val();
	var tab = $frame.hasClass('receive_popup') ? 'RECEIVE' : 'SEND';
	
	fAjax_msg('', '', 'ACT=GET_MSG&MsgSeq=' + MsgSeq + '&tab=' + tab + '&way=' + way);
}

function fSetMsg(r) {
	if (!r.arr[0].msg.seq)
	{
		switch (r.arr[0].way)
		{
			case 'NEXT':
			case 'PREV':
				alert(getLangStr("jsm_0010"));
				break;
			default:
				alert(getLangStr("jsm_0011"));
				break;
		}

		return;
	}

	var $msgViewer = (r.arr[0].tab == 'RECEIVE') ? $('.receive_popup') : $('.send_popup');
	
	$msgViewer.find('input[name=MsgSeq]').val(r.arr[0].msg.seq);
	$msgViewer.find('input[name=MemberSeq]').val(r.arr[0].msg.writerSeq);
	$msgViewer.find('.user_message_top > img').attr('src', '/UploadFile/Profile/' +(r.arr[0].msg.writerPhoto ? r.arr[0].msg.writerPhoto : 'img_thum_base0.jpg'));
	$msgViewer.find('.user_name').text(r.arr[0].msg.writerNic);
	$msgViewer.find('.msg_time').text(r.arr[0].msg.conDate);
	$msgViewer.find('.msg_read_ta').text(r.arr[0].msg.contents);
	$msgViewer.find('.msg_write_ta').attr('placeholder', r.arr[0].msg.writerNic + getLangStr("jsm_0012"));
	
	fShowBlock(r.arr[0].msg.blockMemberSeq ? true : false);
}

function fSetMessage(obj) {
  var $target = $(obj);
  var $msgViewer = $target.closest('#select_view01').length ? $('.receive_popup') : $('.send_popup');

  $msgViewer.find('input[name=MsgSeq]').val($target.attr('msg_seq'));
  $msgViewer.find('input[name=MemberSeq]').val($target.attr('member_seq'));
  $msgViewer.find('.user_message_top img').attr('src', $target.find('.msg_user_img').attr('src'));
  $msgViewer.find('.user_name').text($target.find('.msg_user_name').text());
  $msgViewer.find('.msg_time').text($target.find('.msg_time').text());
  $msgViewer.find('.msg_read_ta').text($target.find('.msg_user_text').text());
  $msgViewer.find('.msg_write_ta').attr('placeholder', $target.find('.msg_user_name').text() + getLangStr("jsm_0012"));
  
  var isBlock = $target.css('border-color');
  
  if(isBlock == 'red' || isBlock == 'rgb(255, 0, 0)') {
  	fShowBlock(true, false);
  }
  else {
  	//fShowBlock($target.hasClass('msg_el_block'));
  	fShowBlock(false, false);
  }

  fAjax_msg('', '', 'ACT=SET_READ&MsgSeq=' + $target.attr('msg_seq'));
}

function fShowBlock(isBlock, isTip)
{
	const $target = $('.user_message');

	const getElements = function() {
		return {
			userName: $target.find('.user_name'),
			userNameSpan: $target.find('.user_name_block'),
			icon: $target.find('.user_message_block .material-icons'),
			iconText: $target.find('.user_message_block span')
		};
	};

	const removeBlock = function() {
		const newObject  = getElements();

		//newObject.userName.css('color', '#000080');
		newObject.userNameSpan.text('');
		//newObject.icon.css('color', '#333');
		newObject.iconText.text(getLangStr('jsm_0125'));//'차단'.css('color', '#333')
	};
	const addBlock = function() {
		const newObject  = getElements();

		//newObject.userName.css('color', 'red');
		newObject.userNameSpan.html("<i class='material-icons'>block</i>");
		//newObject.icon.css('color', 'red');
		newObject.iconText.text(getLangStr('jsm_0127'));//'해제'.css('color', 'red')
	};

	$('.block_remove_toast').stop().hide();
	$('.block_add_toast').stop().hide();
	//console.log('isBlock : ' + isBlock);
	if (isBlock)
	{
		addBlock();
		$target.addClass('is_block');
		if (isTip)
			$('.block_add_toast').stop().fadeIn(400).delay(2000).fadeOut(400);
	} else
	{
		removeBlock();
		$target.removeClass('is_block');
		if (isTip)
			$('.block_remove_toast').stop().fadeIn(400).delay(2000).fadeOut(400);
	}

	//checkDeviceSize();
}

function fSetBlock()
{
	var MemberSeq = $('.receive_popup input[name=MemberSeq]').val();
	var setBlock = $('.receive_popup').hasClass('is_block') ? 'N' : 'Y';
	//console.log('setBlock : ' + setBlock);
	fAjax_msg('/member/myFriendAjax', '', 'ACT=SET_BLOCK&MemberSeq=' + MemberSeq + '&setBlock=' + setBlock);
}



$('.user_send_btn').click(function(){
	var $frm = $(this).closest('.user_message').find('form');
	fAjax_msg('', '', 'ACT=SEND&' + $frm.serialize());
});

$('.msg_del_btn').click(function(){
	if (confirm(getLangStr("jsm_0013")))
	{
		var $frm = $(this).closest('.user_message').find('form');
		fAjax_msg('', '', 'ACT=MSG_DEL&' + $frm.serialize());
	}
});

$('.user_message_block').click(fSetBlock);
//$('.user_message_close').click(closeAll);

$(document).ready(function() {
	selectView();
	fAjax_List('ACT=LIST_RECEIVE');
	
	$('.select_div').click(function(event) {
	    let selectedBtnTab = $(event.target);
		if (!selectedBtnTab.attr('tar_obj'))
			 selectedBtnTab = selectedBtnTab.closest('.select_btn');
	    let idName = selectedBtnTab.attr('tar_obj');
	    if (!idName) return;
	    if(idName == 'select_view01') {
	    	fAjax_List('ACT=LIST_RECEIVE');
	    }
	    else {
	    	fAjax_List('ACT=LIST_SEND');
	    }
	    
	    if (!selectedBtnTab.hasClass('temp_tab_on')) {
	      $('.select_view').hide();
	      $('.select_btn').removeClass('temp_tab_on');
	
	      selectedBtnTab.addClass('temp_tab_on');
	      $('#' + idName).fadeIn(300);
	
	      if (typeof libCallBackSelectView == 'function') {
	        libCallBackSelectView(idName);
	      }
	    }
	});

	$.fn.scrollBottom = function() { 
		return $(document).height() - this.scrollTop() - this.height(); 
	};

	//$(window).resize(checkDeviceSize);
});
