$('.contents-wrapper').fadeIn(1000);

$(function(){
	fweekGraph();
	flvupStamp();
	
	$('body').addClass('lvup');
})

function imagerotate(ro) {
	cropper = new Cropper(image, {
		aspectRatio: 1,
		viewMode: 1,
		viewMode: 1,
		rotate: -90
	});
}

window.addEventListener('DOMContentLoaded', function () {
	var avatar = document.getElementById('avatar');
	var image = document.getElementById('image');
	var input = document.getElementById('input');
	var $progress = $('.aprogress');
	var $progressBar = $('.aprogress-bar');
	var $alert = $('.alert');
	var $modal = $('#modal1');
	var cropper;

	input.addEventListener('change', function (e) {
		var files = e.target.files;
		var done = function (url) {
			input.value = '';
			image.src = url;
			$alert.hide();
			$modal.modal('show');
		};
		var reader;
		var file;
		var url;

		if (files && files.length > 0) {
			file = files[0];

			if (URL) {
				done(URL.createObjectURL(file));
			} else if (FileReader) {
				reader = new FileReader();
				reader.onload = function (e) {
					done(reader.result);
				};
				reader.readAsDataURL(file);
			}
		}
	});

	document.getElementById('rrotateImg').addEventListener('click', function () {
		cropper.rotate(-90);
	});

	$modal.on('shown.bs.modal', function () {
		cropper = new Cropper(image, {
			aspectRatio: 1,
			viewMode: 1,
		});
	}).on('hidden.bs.modal', function () {
		cropper.destroy();
		cropper = null;
	});

	document.getElementById('crop').addEventListener('click', function () {
		var initialAvatarURL;
		var canvas;

		$modal.modal('hide');

		if (cropper) {
			canvas = cropper.getCroppedCanvas({
				width: 80,
				height: 80,
			});

			initialAvatarURL = avatar.src;
			avatar.src = canvas.toDataURL();
			$progress.hide();
			$alert.removeClass('alert-success alert-warning');
			canvas.toBlob(function (blob) {
				var formData = new FormData();

				formData.append('photo', blob, "blob.png");

				$.ajax('/member/uploadProfileImg_n', {
					method: 'POST',
					data: formData,
					processData: false,
					contentType: false,

					xhr: function () {
						var xhr = new XMLHttpRequest();

						xhr.upload.onprogress = function (e) {
							var percent = '0';
							var percentage = '0%';

							if (e.lengthComputable) {
								percent = Math.round((e.loaded / e.total) * 100);
								percentage = percent + '%';
								$progressBar.width(percentage).attr('aria-valuenow', percent).text(percentage);
							}
						};

						return xhr;
					},

					success: function (result) {
						$alert.show().addClass('alert-success').text('Upload success');
						$alert.hide();
					},

					error: function () {
						avatar.src = initialAvatarURL;
						$alert.show().addClass('alert-warning').text('Upload error');
						$alert.hide();
					},

					complete: function () {
						$progress.hide();
					},
				});
			});
		}
	});
});
var flagLAST = 0;
function fmenuChange(target) {
	$('.content_wrap').hide();
	$(target).siblings().removeClass('on');
	$(target).addClass('on');

	$('html').removeAttr('class');
	//$('html').addClass('js');
	
	$('body').removeAttr('class'); //#atm_gray2
	$('body').addClass($(target).data('type')); //#atm_gray2
}

function fweekGraph() {
	var percentSum = 0;
	var percent = 0;
	$('.prgDetailCnt').each(function(index,item){
		percent = (parseInt($(this).text()) / parseInt($(this).siblings('.prgDetailMaxCnt').text())) * 100;
		percentSum += percent;
		$(this).parents('.box').find('.detail_graph').find('.graph').css('width', percent+'%');
	});
	
	percentSum = Math.round(percentSum / $('.prgDetailCnt').length);
	
	if (percentSum > 50) {
		$('section.week_mission').find('.sum_graph').find('.sum_txt').css('color','#fff');
	} else {
		$('section.week_mission').find('.sum_graph').find('.sum_txt').css('color','#ff9e07');
		$('section.week_mission.exchange').find('.sum_graph').find('.sum_txt').css('color','#20b0c4');
		$('section.week_mission.old_date').find('.sum_graph').find('.sum_txt').css('color','#909090');
	}
	$('section.week_mission').find('.sum_graph').find('.sum_txt').find('span').text(percentSum);
	$('section.week_mission').find('.sum_graph').find('.graph').css('width', percentSum + '%');

	if(flagLAST == 0 && percentSum == 100) {
		$('section.week_mission').find('.sum_success').slideDown();
	} else {
		$('section.week_mission').find('.sum_success').slideUp();
	}

}

function myinfo_fAjax(url, type, param)
{
	if (document.xhr) {
		$('#Tip').text(getLangStr("jsm_0031")).css('display', 'block');
		setTimeout(function () { $('#Tip').stop().fadeOut(300) }, 3000);
		return;
	}
	
	document.xhr = $.ajax({
		type: 'post',
		url: url,
		data: param,
		dataType: type,
		success: function (r) {
			switch (r.result) {
				case 'GetLastWeek':
					console.log('r.arr : ' + r.arr[0].StartDate);
					if (r.arr.length == 0) {
						alert(getLangStr("jsm_0032"));
						return;
					}
					var arr = r.arr[0];
					$('#weekStartDate').text(arr.StartDate);
					$('#weekEndDate').text(arr.EndDate);
					$('#weekQCnt').text(arr.QusRegCnt ? (arr.QusRegCnt > parseInt($('#weekQMaxCnt').text(), 10) ? $('#weekQMaxCnt').text() : arr.QusRegCnt) : 0 );
					$('#weekACnt').text(arr.AnsRegCnt ? (arr.AnsRegCnt > parseInt($('#weekAMaxCnt').text(), 10) ? $('#weekAMaxCnt').text() : arr.AnsRegCnt) : 0 );
					$('#weekAEstiCnt').text(arr.AnsEstiCnt ? (arr.AnsEstiCnt > parseInt($('#weekAEstiMaxCnt').text(), 10) ? $('#weekAEstiMaxCnt').text() : arr.AnsEstiCnt) : 0 );
					$('#weekAChoicedCnt').text(arr.AnsChoicedCnt ? (r.arr.AnsChoicedCnt > parseInt($('#weekAChoicedMaxCnt').text(), 10) ? $('#weekAChoicedMaxCnt').text() : arr.AnsChoicedCnt) : 0 );
					$('#weekReplyCnt').text(arr.ReplyCnt ? ( arr.ReplyCnt > parseInt($('#weekReplyMaxCnt').text(), 10) ? $('#weekReplyMaxCnt').text() : arr.ReplyCnt) : 0 );

					$('#dateArrowL, #dateArrowR').css('visibility', 'hidden').removeAttr('onclick');
					parseInt(arr.LAST, 10) != 0 ? $('section.week_mission').addClass('old_date') : $('section.week_mission').removeClass('old_date');

					var prevNum = parseInt(arr.LAST) < 3 ? parseInt(arr.LAST) + 1 : -1;
					var nextNum = parseInt(arr.LAST) - 1;

					if (prevNum != -1)
						$('#dateArrowL').css('visibility', 'visible').attr("onclick", "myinfo_fAjax('/member/myInfoLvAjax', 'json', 'ACT=GetLastWeek&LAST="+ prevNum +"')");
					if (nextNum != -1)
						$('#dateArrowR').css('visibility', 'visible').attr("onclick", "myinfo_fAjax('/member/myInfoLvAjax', 'json', 'ACT=GetLastWeek&LAST="+ nextNum +"')");
					
					flagLAST = parseInt(arr.LAST, 10);
					fweekGraph();

					break;
				case 'ExchGetLastWeek':
					if (r.arr.length == 0) {
						alert(getLangStr("jsm_0033"));
						return;
					}
					
					var isExistsCondition = Object.keys(r.arr[0]).length;

					if (!r.arr[0].ExchQusRegCnt) r.arr[0].ExchQusRegCnt = 0;
					if (!r.arr[0].ExchAnsRegCnt) r.arr[0].ExchAnsRegCnt = 0;
					if (!r.arr[0].ExchAnsEstiCnt) r.arr[0].ExchAnsEstiCnt = 0;
					if (!r.arr[0].ExchAnsChoicedCnt) r.arr[0].ExchAnsChoicedCnt = 0;

					$('#weekStartDate').text(r.arr[1].StartDate);
					$('#weekEndDate').text(r.arr[1].EndDate);
					if (isExistsCondition) {
						$('#weekQMax').text(r.arr[0].ExchQusRegCnt);
						$('#weekAMax').text(r.arr[0].ExchAnsRegCnt);
						$('#weekAEstiMax').text(r.arr[0].ExchAnsEstiCnt);
						$('#weekAChoicedMax').text(r.arr[0].ExchAnsChoicedCnt);
					}

					if (!r.arr[1].QusRegCnt) r.arr[1].QusRegCnt = 0;
					if (!r.arr[1].AnsRegCnt) r.arr[1].AnsRegCnt = 0;
					if (!r.arr[1].AnsEstiCnt) r.arr[1].AnsEstiCnt = 0;
					if (!r.arr[1].AnsChoicedCnt) r.arr[1].AnsChoicedCnt = 0;

					$('#weekQCnt').text(r.arr[1].QusRegCnt > r.arr[0].ExchQusRegCnt ? r.arr[0].ExchQusRegCnt : r.arr[1].QusRegCnt);
					$('#weekACnt').text(r.arr[1].AnsRegCnt > r.arr[0].ExchAnsRegCnt ? r.arr[0].ExchAnsRegCnt : r.arr[1].AnsRegCnt);
					$('#weekAEstiCnt').text(r.arr[1].AnsEstiCnt > r.arr[0].ExchAnsEstiCnt ? r.arr[0].ExchAnsEstiCnt : r.arr[1].AnsEstiCnt);
					$('#weekAChoicedCnt').text(r.arr[1].AnsChoicedCnt > r.arr[0].ExchAnsChoicedCnt ? r.arr[0].ExchAnsChoicedCnt : r.arr[1].AnsChoicedCnt);

					$('#dateArrowL, #dateArrowR').css('visibility', 'hidden').removeAttr('onclick');
					parseInt(r.arr[2].LAST) != 0 ? $('section.week_mission').addClass('old_date') : $('section.week_mission').removeClass('old_date');

					var prevNum = parseInt(r.arr[2].LAST) < 3 ? parseInt(r.arr[2].LAST) + 1 : -1;
					var nextNum = parseInt(r.arr[2].LAST) - 1;
					if (prevNum != -1)
						$('#dateArrowL').css('visibility', 'visible').attr("onclick", "myinfo_fAjax('/member/myInfoExchAjax', 'json', 'ACT=ExchGetLastWeek&LAST="+ prevNum +"')");
					if (nextNum != -1)
						$('#dateArrowR').css('visibility', 'visible').attr("onclick", "myinfo_fAjax('/member/myInfoExchAjax', 'json', 'ACT=ExchGetLastWeek&LAST="+ nextNum +"')");
					
					flagLAST = parseInt(r.arr[2].LAST, 10);
					fweekGraph();
					break;
				default:
					$('.content_wrap').html(r).fadeIn(300);
					fweekGraph();

					if (url == '/Common/include/member/myInfoLv.jsp') flvupStamp();
					break;
			}
			
		},
		error: function (r, textStatus, err) {
			console.log(r);
		},
		complete: function () {
			if (!document.isShow) document.xhr = false;
		}
	});
}
function flvupStamp() {
	var stampMax = $('#lvupStampWrap').data('stampmax');
	var stampCnt = $('#lvupStampWrap').data('stampcnt');
	var halfWidth = $('#lvupStampWrap').find('.stamp_base').width() / 2;

	var incrs = 1;
	var top = 0;
	var left = 0;
	var roadLeft; 
	var roadTop;
	var topStep = 100 / Math.ceil(stampMax / 4);

	for (var i=0; i < stampMax; i++) {
		var stampRoad = $('#lvupStampWrap').find('.clone_el').find('.stamp_road').clone();
		var stampBase = $('#lvupStampWrap').find('.clone_el').find('.stamp_base').clone();
		var stamp = $('#lvupStampWrap').find('.clone_el').find('.stamp').clone();
		var stampArrow = $('#lvupStampWrap').find('.clone_el').find('.stamp_arrow').clone();

		if (i > 0) {
			if (i % 4) {
				// 가로길
				left += 25 * incrs;
				roadLeft = left + (incrs > 0 ? - halfWidth : halfWidth);
				roadTop = top;
				stampRoad.find('.box').css('height', '12px').css('width', '100%');
			} else {
				// 세로길
				roadLeft = left;
				roadTop = top + topStep/2;
				top += topStep;
				stampRoad.find('.box').css('width', '12px').css('height','100%');
				incrs *= -1;
			}
			stampRoad.css('top', roadTop+'%').css('left', roadLeft+'%');
			$('#lvupStampWrap').append(stampRoad);
		}			
		
		stampBase.css('top', top+'%').css('left', left+'%');
		$('#lvupStampWrap').append(stampBase);
		
		if (i <= stampCnt) {
			if (i < stampCnt) {
				stamp.css('top', top+'%').css('left', left+'%');
				$('#lvupStampWrap').append(stamp);
			}

			stampRoad.find('.box').css('background','#ffd492');

			if (i == stampCnt && stampCnt != 0) {
				if (i % 4) {
					stampArrow.css('padding-left','5px');
					incrs > 0 ? stampArrow.find('img').css('transform','rotate(0deg)') : stampArrow.find('img').css('transform','rotate(180deg)');						
				} else {
					stampArrow.find('img').css('transform','rotate(90deg)')
				}

				stampArrow.css('top', roadTop+'%').css('left', roadLeft+'%')

				$('#lvupStampWrap').append(stampArrow);
			}
		}
	}
	$('#lvupStampWrap').find('.clone_el').remove();
	$('#lvupStampWrap').removeAttr('data-stampcnt').removeAttr('data-stampmax');
}

$(document).on('click', '.atm_mf_btn_R1' , function() {
	var $btn = $(this),
	$target = $(this).parent().parent().find('.atm_mf_con_slide');

	if($target.hasClass('off')) {
		$target.removeClass('off').addClass('on');
		$target.children('.atm_mf_con_tt1').show();
		$target.animate({height : $target.data('height')},300);
		$btn.attr('src', '/Common/images/btn_minus0.png');
	} else {
		$target.removeClass('on').addClass('off');
		$target.data('height', $target.height());
		$target.animate({height : 0},300, function(){
			$target.children('.atm_mf_con_tt1').hide();
		});
		$btn.attr('src', '/Common/images/btn_plus0.png');
	}
});