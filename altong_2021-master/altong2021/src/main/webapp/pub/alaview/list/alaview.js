var thumbH;
$(document).ready(function(){
	//$('body').css("background", "#e6e6e6");
	$('.alaview').each(function(idx){
		var obj = $(this);
		if (!idx) thumbH = obj.width() * 0.75;
		var ajaxUrl = 'http://vimeo.com/api/v2/video/' + obj.attr('alaview_id') + '.json';

		obj.find('.alaview_height').css('padding-top', thumbH);

		$.ajax({
			url: ajaxUrl,
			dataType: 'jsonp',
			crossDomain: true,
			success: function (r) {
				obj.prepend('<img src="' + r[0].thumbnail_large + '" class="img_thumb" style="position:absolute;top:0;left:0;width:100%">').find('.img_thumb').load(function(){
					var imgH = $(this).height();
					if (imgH > thumbH)
                    {
                        $(this).css({'width':'unset', 'height':thumbH}).attr('h',1);
                        $(this).css({'margin-left':(obj.width()-$(this).width())/2});
                        imgH = thumbH;
                    }
					$(this).css('top', (thumbH-imgH)/2);
				});
				obj.find('.data_tit').text(r[0].title);
				obj.find('.data_usr').text(r[0].user_name);
			},
			error: function (r, textStatus, err) {
				console.log(r);
			},
			complete: function () {
			}
		});
	});

	$(".alaview_mask").click(fClosePop);
	$(".popup_close").click(fClosePop);
});

$(window).resize(function(){
	$('.alaview').each(function(idx){
		var obj = $(this);
		var img = obj.find('.img_thumb');
		if (!idx) thumbH = obj.width() * 0.75;
		var imgH;

		if (img.attr('h'))
		{
			img.css({'width':'unset', 'height':thumbH});
			img.css({'margin-left':(obj.width()-img.width())/2});
			imgH = thumbH;
		}
		else
			imgH = img.height();

		obj.find('.alaview_height').css('padding-top', thumbH);
		img.css('top', (thumbH-imgH)/2);
	});
});

function fClosePop()
{
	$(".alaview_popup").hide();
	$(".alaview_mask").hide();
	$('.viewer').remove();
}

function fPlay(obj)
{
	if (!confirm('100알을 차감하여 동영상을 보시겠습니까?')) return;

	var obj = $(obj);
	var vid = obj.attr('alaview_id');

	$(".alaview_popup").show();
	$(".alaview_mask").show();

	if (!$('#v_'+vid).length)
		$('<div id="v_' + vid + '" class="viewer" style="position:absolute;top:' + obj.find('.img_thumb').css('top') + ';width:100%"></div>').appendTo('.popup_div');

    var options = {
        id: vid,
        width: obj.find('.popup_div').width(),
        loop: true,
		autoplay: true
    };
    var player = new Vimeo.Player('v_'+vid, options);

	player.on('play', function(){
        //console.log('played the video!');
    });

}
