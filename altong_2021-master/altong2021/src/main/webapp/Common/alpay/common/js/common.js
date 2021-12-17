// sideMenu slide right & overlay fadeIn
$(function () {
  
    // header scroll
    var lastScrollTop = 0, delta = 15;
    $(this).find('section').scroll(function(event){
        var st = $(this).scrollTop();

        if (Math.abs(lastScrollTop - st) <= delta)
            return; // 스크롤값을 받아서 리턴한다.
        if ((st > lastScrollTop) && (lastScrollTop > 0)) {
            // downscroll code
            $('header:not(.non_box)').addClass('up');
        } else {
            // upscroll code
            $('header:not(.non_box)').removeClass('up');
        }
        lastScrollTop = st;
    })

    $('#gnb_btn').click(function () {
        $('nav').addClass('show');
        $('.overlay').fadeIn();
    });
    $('.overlay').click(function () {
        $('nav').removeClass('show');
        $('.overlay').fadeOut();
    })
    $('.fav_list button').click(function(){
        // 내 관심 매장 -> 결제
        $('#payment_box').addClass('show');
        var choseShop = $(this).parent('td').siblings('td.shop_info').children('p:first-child').text();
        document.getElementById('if_pay').contentWindow.fGoStep2(choseShop);
	})

	
});

function layerPopClose() {
    $('#layer_popup').fadeOut();
}

function layerPopDel(favDel) {
    $('#layer_popup').fadeOut();
    favDel
        .children('td, th')
        .animate({
            padding: 0,
            opacity: 0
        })
        .wrapInner('<div />')
        .children()
        .slideUp(function () {
            $(this).closest('tr').remove();
        });   
}

// store
$(function(){

	$('.tabs .tab').click(function () {
		// tab클릭 이벤트
		$('.tab_content').hide();
		var activeTab = $(this).attr("data-tab");
		$("#" + activeTab).fadeIn().css('display', 'inline-block');
	
		if ($(this).hasClass('slide')) {
			$('.tabs').addClass('slide');
			$('.order_list .non_scroll_tab').css({ 'padding-bottom': '0', 'box-shadow': 'none' })
		} else {
			$('.tabs').removeClass('slide');
			$('.order_list .non_scroll_tab').css({ 'padding-bottom': '15px', 'box-shadow': '0px 2px 14px white' })
		}
	})
})

function dateSearchBtn(clickBtn) {
	$(clickBtn).siblings().removeClass('active');
	$(clickBtn).addClass('active');
	if ($(clickBtn).hasClass('date_input')) {
		$('#tab_orderList').find('.dateSearch_input').show();
	} else {
		$('#tab_orderList').find('.dateSearch_input').hide();
	}
}
