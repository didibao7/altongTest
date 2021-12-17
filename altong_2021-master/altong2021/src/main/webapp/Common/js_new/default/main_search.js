$(document).on('click','#overlay',function(){
    $(this).stop().fadeOut();
});

$(document).ready(function(){

    /* 메인 검색창 슬라이드 */
    var searchSlide = $('.mainsearch_inputwrapper');
    var searchIcon = $('.mainsearch_inputwrapper a');
    var searchBtn = $('.mainsearch_btnwrapper');
    
    var sb_search = $('#sb-search .sb-icon-search');

    sb_search.click(function(){
            func_click_sb_search();
    });

    searchSlide.click(function(){
        funcFocus();
    });


    /* 슬라이드 메뉴 나의 공간 */
    var myMenu = $('#slide_menu > li').eq(1).find('a').eq(0);
    var myMenuSlide = $('#slide_menu > li').eq(1);
	var myMenuPlus = $('#slide_menu > li > a').eq(1)

    myMenu.click(function(){
        myMenuSlide.toggleClass('menu_slide');
        myMenuSlide.find('ul').stop().slideToggle(250);
        myMenuPlus.toggleClass('plus');
    });

    /* 닉네임 검색 */
    $('#slide_menu > li').eq(2).click(function(){
        $('.nick_search').toggleClass('nick_slide');
    });
    $('.nick_search_bar button').click(function(){
        $(this).hide();
        $(this).next().show();
    });

    /* 사이드메뉴 슬라이드 */
	var menuBtn = $('#menu_icon');
	
    menuBtn.click(function(){
        $('#slidebar').fadeIn();
        $('#gnb_wrapper').css({left:0});
    });
    $('#slidebar_close').click(function(){
        $('#slidebar').fadeOut();
        $('#gnb_wrapper').css({left:'-362px'});
    });
    $('#slidebar').click(function(){
        $('#slidebar').fadeOut();
        $('#gnb_wrapper').css({left:'-362px'});
    });
    $('#gnb_wrapper').click(function(e){
        e.stopPropagation();
    });

    /* top btn */
    $('#top_btn').click(function(){
        $('html, body').stop().animate({scrollTop:0});
    });

    /* 헤더 고정 */
    var nt = 0;
	if($('header').length){
    	nt = $('header').offset().top;
	}

    $(window).scroll(function(){
        var wst = $(window).scrollTop();
        if (nt < wst) {
            $('header').addClass('fixed');
            $('#top_btn').stop().fadeIn(200);
        } else {
            $('header').removeClass('fixed');
            $('#top_btn').stop().fadeOut(200);
        }
    });

    /* 베너 */
    var checkBox = $('#eventBannerCheckbox');

    checkBox.click(function(){
        if ($(checkBox).is(':checked') == true) {
            $('#bannerContainer').stop().hide();
        }
    });

    /* 카테고리 */
    $('#swiper_container ul li a').click(function(){
        $('#swiper_container ul li a img').show();
        $('#swiper_container ul li a .color_icon').hide();
        $('#swiper_container ul li').removeClass('list_select');
        $(this).find('.color_icon').show();
        $(this).parents('li').addClass('list_select');
    });

    /* 댓글 토글 버튼 */
    $('.reply').click(function(e){
        $(this).parents('ol').next('.replydiv').slideToggle(300);
    });
	
    /* 훈훈알 클릭 */
    $('.addAlmoney').click(function(e){
        //$(this).find('ul').stop().toggle();
        //e.stopPropagation();
    });
/*
    $('body').click(function(){
        $('.addAlmoney ul').stop().hide();
    });
*/

    /* 광고버튼 */
    $('.answer_tab').click(function(){
        //$('#overlay').stop().fadeIn();
        //$(this).stop().hide();
        //$(this).next('.answer_slide').stop().slideDown();
    });
    

    /* 찜하기 버튼 */
    $('.atm_more_btn_wrap').click(function(e){
        $(this).find('ul').stop().show();
        e.stopPropagation();
    });
    $('body').click(function(){
        $('.atm_more_btn_wrap ul').stop().hide();
    });

    /* 답변리스트 클릭효과 */
    $('.sub_answer').click(function(){
        $('.sub_answer').removeClass('tab');
        $(this).addClass('tab');
    });

    /* more btn 
    $('.share').click(function(){
        $('.more_btn_list').stop().fadeIn();
        $('.shareWrap').stop().fadeIn();
    });
    */
    $('.add_almoney').click(function(){
        //$('.more_btn_list').stop().fadeIn();
        //$('.add_almoney_popup').stop().fadeIn();
    });
	//번역 훈훈알 추가 2020.02.22 //////////////////
    $('.lang_view p img').click(function(){
        //$('.more_btn_list').stop().fadeIn();
        //$('.add_almoney_popup').stop().fadeIn();
    });
    $('.submit_report_btn').click(function(){
        $('.more_btn_list').stop().fadeIn();
        $('.submit_report').stop().fadeIn();
    });
    /* 쪽지보내기 */
    $('.message_send').click(function(){
        //$('.more_btn_list').stop().fadeIn();
        //$('.user_message').stop().fadeIn();
    });
    /* 나의친구 쪽지보내기 */
    $('.openMessage').click(function(){
        //$('.more_btn_list').stop().fadeIn();
        //$('.user_message').stop().fadeIn();
    });
    $('.more_btn_list').click(function(){
        $(this).stop().fadeOut();
        $('.shareWrap').stop().fadeOut();
        $('.add_almoney_popup').stop().fadeOut();
        $('.submit_report').stop().fadeOut();
        $('.user_message').stop().fadeOut();
    });
    $('.shareWrap').click(function(e){
        e.stopPropagation();
    });
    $('.add_almoney_popup').click(function(e){
        e.stopPropagation();
    });
    $('.submit_report').click(function(e){
        e.stopPropagation();
    });
    $('.user_message').click(function(e){
        e.stopPropagation();
    });


    /* 좋아요 아이콘 체크하기*/
    $('.answer_slide ul li').click(function(){
        $('.answer_slide ul li').removeClass('check_icon');
        $(this).addClass('check_icon');
    }); 

    /* 질문상세페이지 프로필 이미지 클릭 */
    $('.atm_top_wrap figure').click(function(e){
        $('figure').removeClass('profile');
        $(this).toggleClass('profile');
        $('#black_screen').stop().toggle();
        $(this).parents('.atm_top_wrap').find('.profile_mini').stop().toggle();
        e.stopPropagation();
    });
    $('#black_screen').click(function(){
        $('figure').removeClass('profile');
        $('#black_screen').stop().hide();
        $('.profile_mini').stop().hide();
        $('.receive_popup').stop().hide();
        $('.send_popup').stop().hide();
    });

    $('.sub_answer_user figure').click(function(e){
        $('figure').removeClass('profile');
        $(this).toggleClass('profile');
        $('#black_screen').stop().toggle();
        $(this).parents('.sub_answer_user').next().stop().toggle();
        e.stopPropagation();
    });

    /* 닉네임 질문보기 토글 */
    $('.atm_faq_tt').click(function(){
        $('.atm_faq_con').stop().slideToggle();
        $('.atm_faq_tt i').stop().toggleClass('icon_ani');
    });

    /* 닉네임 공개 여부 */
	/*
    $('.nick_open ul li').click(function(){
        $('.nick_open ul li').removeClass('open_agree');
        $(this).addClass('open_agree');
    });
    $('.adult_open ul li').click(function(){
        $('.adult_open ul li').removeClass('open_agree');
        $(this).addClass('open_agree');
    });
	*/

    /* 댓글날짜 보임 */
    $('.replydiv_user_list table tr td b').click(function(e){
        $(this).find('span').stop().toggle();
        $(this).toggleClass('date_underLine');
        e.stopPropagation();
    });
    $('body').click(function(){
        $('.replydiv_user_list table tr td b span').stop().hide();
        $('.replydiv_user_list table tr td b').removeClass('date_underLine');
    });

    /* 질문날짜 */
    $('.answer_date').click(function(e){
        $(this).find('span').stop().toggle();
        $(this).toggleClass('date_underLine');
        e.stopPropagation();
    });
    $('body').click(function(){
        $('.answer_date span').stop().hide();
        $('.answer_date').removeClass('date_underLine');
    });
    
    /* 질문입력 등록하기 팝업 */
	/*
    $('.register').click(function(){
        $('#question_popup').stop().fadeIn();
    });
    $('#question_popup').click(function(){
        $(this).stop().fadeOut();
    });
    $('.que_pop_close p').click(function(){
        $('#question_popup').stop().fadeOut();
    });
    $('.question_popup_wrapper').click(function(e){
        e.stopPropagation();
    });
 	*/

    /* 마이페이지 잔고 클릭 */
    var sumMoney = $('#myinfo_wrapper .profile .info .info_money .sum_money span');
    sumMoney.click(function(){
        $('#infoMoneyMore').stop().toggle();
    });
    $('#infoMoneyMore').click(function(){
        $(this).stop().hide();
    });
	/* 마이페이지 기본정보 탭메뉴 */
    $('#menuContentWrap .atm_mf_con_tt0').click(function(){
        $(this).find('i').eq(1).toggleClass('change_plus');
        $(this).next('.atm_mf_con_slide').stop().slideToggle();
    });
    
    /* answerView */
    $('.atm_estimation .atm_esti_el').click(function(){
        $('.atm_estimation .atm_esti_el').removeClass('check_icon');
        $(this).addClass('check_icon');
    }); 
    $('.atm_addviewG .atm_viewbtnG_replybtn').click(function(){
        $('#replydiv_q').stop().toggle();
    });
    //$('.atm_viewbtnG2 .atm_viewbtnG_replybtn').click(function(){
    //    $('#replydiv_a').stop().toggle();
    //});

    $('.atm_reply_eltexts b').click(function(e){
        $(this).find('span').stop().toggle();
        $(this).toggleClass('date_underLine');
        e.stopPropagation();
    });
    $('body').click(function(){
        $('.atm_reply_eltexts b span').stop().hide();
        $('.atm_reply_eltexts b').removeClass('date_underLine');
    });
    
    /* 나의댓글 받은댓글 */
    $('.atm_temp_tab2_pc p').click(function(){
        $('.atm_temp_tab2_pc p').removeClass('temp_tab_on');
        $(this).addClass('temp_tab_on');
    });
    /* 나의 친구/쪽지차단 버튼 */
    $('.myPartner_friends .atm_mymentor_xbtn').click(function(e){
        //$(this).find('#menuFriend').stop().toggle();
        //e.stopPropagation();
    });
    $('body').click(function(){
        $('.atm_mymentor_xbtn #menuFriend').stop().hide();
    });
    $('.select_wrapper .atm_temp_tab p').click(function(){
        //$('.select_wrapper .atm_temp_tab p').removeClass('temp_tab_on');
        //$(this).addClass('temp_tab_on');
    });
    $('.select_wrapper .atm_temp_tab p').eq(0).click(function(){
        //$('#select_view01').stop().show();
        //$('#select_view02').stop().hide();
        //$('#select_view03').stop().hide(); //멘티 추가
        //$('.contentsWrapper').stop().show();// 추천인 추가
        //$('.contentsWrapper02').stop().hide();
    });
    $('.select_wrapper .atm_temp_tab p').eq(1).click(function(){
        //$('#select_view01').stop().hide();
        //$('#select_view02').stop().show();
        //$('#select_view03').stop().show(); //멘티 추가
        //$('.contentsWrapper').stop().hide();//추천인 추가
        //$('.contentsWrapper02').stop().show();
    });
    
    /* 쪽지보내기 */
    $('#message_wrapper #select_view01 .msg_el_con').click(function(){
        //$('.receive_popup').stop().show();
        //$('#black_screen').stop().show();
    });
    $('.user_message_close').click(function(){
        $('.receive_popup').stop().hide();
        $('.send_popup').stop().hide();
        $('#black_screen').stop().hide();
    });
    $('#message_wrapper #select_view02 .msg_el_con').click(function(){
        $('.send_popup').stop().show();
        $('#black_screen').stop().show();
    });
    
    /* 이용안내 */
    $('#select_innerwrapper .atm_faq_wrapper2 .atm_faq_div .atm_faq_ttt').click(function(){
        $(this).next('.atm_faq_con').stop().slideToggle();
        $(this).find('img').stop().toggleClass('up');
    });
    /* 약관동의 */
    $('#atm_memagree_wrapper0 .show-more').click(function(){
        $(this).parents('.atm_memagree_text').find('textarea').addClass('more_slide');
        $(this).addClass('more_slide');
    });
});


var cd = false;
function funcFocus(){

    var searchSlide = $('.mainsearch_inputwrapper');
    var searchIcon = $('.mainsearch_inputwrapper a');
    var searchBtn = $('.mainsearch_btnwrapper');

    searchSlide.toggleClass('wide');
    searchIcon.toggleClass('trans');
    searchBtn.toggleClass('scale');

	if (cd == false) {
        cd = true;
    }
    else {
        cd = false;
    }
}

function blurFocus(){

    var searchSlide = $('.mainsearch_inputwrapper');
    var searchIcon = $('.mainsearch_inputwrapper a');
    var searchBtn = $('.mainsearch_btnwrapper');

    if($(".mainsearch_inputwrapper").is(".wide") == true) {
        searchSlide.removeClass('wide');
        searchIcon.removeClass('trans');
        searchBtn.removeClass('scale');
		cd = false;
    }
}



function func_click_sb_search(){
    var sb_search= $('#sb-search .sb-search-input')
    var header_logo= $('.header_logo')
	
	if(sb_search.hasClass("search-slide")){
		$('#sb-search form').submit()
		return 0;
	}
    sb_search.toggleClass('search-slide')
    header_logo.toggleClass('change')

    sb_search.focus();
}

// 클릭시 클래스의 이름을 바꿔줌.
function blur_click_sb_search(){
    var sb_search= $('#sb-search .sb-search-input')
    var header_logo= $('.header_logo')
	
	setTimeout(function(){
		sb_search.removeClass('search-slide');
    	header_logo.removeClass('change')
	},200);
}


//console.log('s_lang : ' + getCookie('s_lang'));
//console.log('hello(javascript data2) : ' + getLangStr("title") );
//console.log('jsm_000001 : ' + getLangStr("jsm_000001"));
//console.log('jsm_000002 : ' + getLangStr("jsm_000002"));
//alert(getLangStr("jsm_000001") + '\n' + getLangStr("jsm_000002"));