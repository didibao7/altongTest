$(document).ready(function(){
    /* 다른사람번역 버튼클릭 효과 */
    $('.lang_view').hide();
    $('.languages_translation > div').click(function(){
        $('.languages_translation > div').removeClass('pick');
        $('.languages_translation > div .lang_view').stop().animate({width:'hide'}, 200, 'linear');
        $(this).addClass('pick');
        //$(this).find('.lang_view').show('slide', {direction:'right'}, 300);
        $(this).find('.lang_view').stop().animate({width:'show'}, 300, 'linear');
    });
    $('.other_lang dd').click(function(){
        $('.other_lang dd').removeClass('pick');
        $('.other_lang dd .lang_view').stop().hide();
        $(this).addClass('pick');
        $(this).find('.lang_view').stop().animate({width:'show'});
    });

    /* 번역 더보기 */
    $('.lang_bottom_btn').click(function(e){
        /* 
        $(this).attr('src', function(index, attr){
            if (attr.match('_on')) {
                return attr.replace('_on.svg', '_off.svg');
            } else {
                return attr.replace('_off.svg', '_on.svg');
            }
        }); */
        $(this).toggleClass('slide');
        if ($(this).hasClass('slide')) {
            $(this).attr('src', '/Common/images/lang_bottom_on.svg');
        } else {
            $(this).attr('src', '/Common/images/lang_bottom_off.svg');
        }

        var num = 20;
        if ($(window).width() < 480) {
            num = 0;
        }
        $(this).next().toggle();
        var profileLeft = $(this).parents('.languages_translation').find('.profileLeft').position().left + num;
        $(this).next().css({left:profileLeft});
        e.stopPropagation();
    });
    $('.other_lang').click(function(e){
        e.stopPropagation();
    });
    $('body').click(function(){
        $('.lang_bottom_btn').removeClass('slide');
        $('.lang_bottom_btn').attr('src', '/Common/images/lang_bottom_off.svg');
        $('.other_lang').hide();
        $('.other_lang dd').removeClass('pick');
        $('.other_lang dd .lang_view').stop().hide();
    });


    //번역 훈훈알 추가//////////////////
    $('.lang_view p img').click(function(){
        //$('.more_btn_list').stop().fadeIn();
        //$('.add_almoney_popup').stop().fadeIn();
    });    
    /* 훈훈알 숫자 클릭 시 */
    $('.lang_view p span').click(function(e){
        //$(this).parents('p').next('dl').toggle();
        e.stopPropagation();
    });
    $('body').click(function(){
        $('.lang_view dl').hide();
    });

    /* 답변 번역 아이콘 클릭 */
    $('.answer_lang').click(function(e){
        $(this).attr('src', function(index, attr){
            if (attr.match('_on')) {
                return attr.replace('language_on.svg', 'language.svg');
            } else {
                return attr.replace('language.svg', 'language_on.svg');
            }
        });
        e.stopPropagation();
        e.preventDefault();
    });

    /* 번역 프로필이미지 클릭 시 미니프로필 */
    $('.languages_translation > .lang_profile .lang_image').click(function(){
        //$(this).parents('.lang_profile').find('.profile_mini').stop().toggle();
        if ($(this).parents('.lang_profile').hasClass('pick')) {
            $(this).parents('.lang_profile').find('.profile_mini').stop().toggle();
            $(this).toggleClass('profile');
            $('#black_screen2').stop().toggle();
        } else {
            $(this).parents('.lang_profile').find('.profile_mini').stop().hide();
            $(this).removeClass('profile');
            $('#black_screen2').stop().hide();
        }
    });
    $('#black_screen2').click(function(){
        $('.lang_profile .lang_image').removeClass('profile');
        $(this).stop().hide();
        $('.lang_profile .profile_mini').stop().hide();
    });

    /* 번역에 대한 평가 */
    $('.lang_assessment .lang_good').click(function(){
        $(this).find('img').attr('src', '/Common/images/smile_on.svg');
        $(this).parents('.lang_assessment').find('.lang_bad > img').attr('src', '/Common/images/sad.svg');
        $(this).find('span').css({color:'#ff3300'});
        $(this).parents('.lang_assessment').find('.lang_bad > span').css({color:'#999999'});
    });
    $('.lang_assessment .lang_bad').click(function(){
        $(this).find('img').attr('src', '/Common/images/sad_on.svg');
        $(this).parents('.lang_assessment').find('.lang_good > img').attr('src', '/Common/images/smile.svg');
        $(this).find('span').css({color:'#ff3300'});
        $(this).parents('.lang_assessment').find('.lang_good > span').css({color:'#999999'});
    });

    /* 좋아요, 싫어요 
    $('.answer_reply_btn li .smile_icon').click(function(){
        $(this).parents('li').find('.sad_icon b').css({color:'#999999'});
        $(this).find('b').css({color:'#ff3300'});
        $(this).parents('li').find('.sad_icon').find('img').attr('src', '/Common/images/sad.svg');
        $(this).find('img').attr('src', '/Common/images/smile_on.svg');
    });
    $('.answer_reply_btn li .sad_icon').click(function(){
        $(this).parents('li').find('.smile_icon b').css({color:'#999999'});
        $(this).find('b').css({color:'#ff3300'});
        $(this).parents('li').find('.smile_icon').find('img').attr('src', '/Common/images/smile.svg');
        $(this).find('img').attr('src', '/Common/images/sad_on.svg');
    });
    $('.replydiv_user_list table .smile_icon').click(function(){
        $(this).parents('div').find('.sad_icon b').css({color:'#999999'});
        $(this).find('b').css({color:'#ff3300'});
        $(this).parents('div').find('.sad_icon').find('img').attr('src', '/Common/images/sad.svg');
        $(this).find('img').attr('src', '/Common/images/smile_on.svg');
    });
    $('.replydiv_user_list table .sad_icon').click(function(){
        $(this).parents('div').find('.smile_icon b').css({color:'#999999'});
        $(this).find('b').css({color:'#ff3300'});
        $(this).parents('div').find('.smile_icon').find('img').attr('src', '/Common/images/smile.svg');
        $(this).find('img').attr('src', '/Common/images/sad_on.svg');
    });*/

    /* 번역하기 클릭 */
    $('.lang_btn').click(function(){
        $('#lang_warning').show();
    });
    $('#lang_warning').click(function(){
        $(this).hide();
    });
    $('#lang_warning > div').click(function(e){
        e.stopPropagation();
    });
    $('#lang_warning > div .lang_next').click(function(){
        $('#lang_warning').hide();
        $('#lang_popup').show();
    });
    $('#lang_popup').click(function(){
        $(this).hide();
    });
    $('#lang_popup > div').click(function(e){
        e.stopPropagation();
    });

    /*
    setInterval (function(){
        var profileLeft = $('.profileLeft').position().left + 5;
        $('.other_lang').stop().animate({left:profileLeft}, 5);
    }); */

    /* 신고하기 버튼 */
    $('.lang_report').click(function () {
        $('.more_btn_list').stop().fadeIn();
        $('.submit_report').stop().fadeIn();
    });

    /* 댓글 훈훈알 클릭 */
    $('.reply_honhon_btn').click(function(e){
        $(this).next().toggle();
        e.stopPropagation();
    });
    $('body').click(function(){
        $('.reply_honhon_btn').next().hide();
    });
    /* 댓글 훈훈알 주기 */
    $('.reply_add_honhon').click(function(){
        $('.more_btn_list').stop().fadeIn();
        $('.add_almoney_popup').stop().fadeIn();
    });

    /* 번역날짜 보임 */
    $('.lang_view_el01 li b').click(function (e) {
        $(this).find('span').stop().toggle();
        $(this).toggleClass('date_underLine');
        e.stopPropagation();
    });
    $('body').click(function () {
        $('.lang_view_el01 li b span').stop().hide();
        $('.lang_view_el01 li b').removeClass('date_underLine');
    });
    
    /* 목록 언어 선택 */
    var count = 0;
    var langListSize = 3;
    var containerWidth = $('#languages_pack').width();
    var languages = $('.language_box');
    var maxSize = $('.language_box input').size();

    $('#right_lang').click(function(){
        if (count < maxSize / langListSize - 1) {
            count++;
            $('#left_lang i').fadeIn();
        } else {
            $('#right_lang i').fadeOut();
        }
        languages.animate({marginLeft:count*containerWidth*-1}, 300);
    });
    $('#left_lang').click(function(){
        if (count > 0) {
            count--;
            $('#right_lang i').fadeIn();
        } else {
            $('#left_lang i').fadeOut();
        }
        languages.animate({marginLeft:count*containerWidth*-1}, 300);
    });
    
});