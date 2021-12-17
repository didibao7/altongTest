$.fn.exists = function () {
    return this.length !== 0;
}

var cateaddHeightOrg

/* 이벤트 리스너 */
$(window).ready(function() {
    closeCategory(0)
})

$('.atm_cateeditbtn_add').on("click", function() {
    openCategory()
})

$('.atm_catecon_el').on("click", function() {
	if($(this).hasClass("last"))
		alert("해당 카테고리가 마지막 카테고리입니다.")
	$(this).parent().children().removeClass("cateon_on")
    $(this).addClass("cateon_on")
    var next = $(this).attr("value")
	if(!($("#" + next).exists()))
		$(this).addClass("last")
    var tmp = next.split("_")
    tmp = tmp.reverse()
    $("input[name=Sec" + (tmp.length) + "]").attr("value", tmp[0])
    if($("#" + next).exists() == false) {
        return
    }
    $('.atm_cateadd_navi').children(".catenavi_on").attr("value", next)
    //nav 를 옮겨 줘야지
    $('.atm_cateadd_navi').children(".catenavi_on").removeClass("catenavi_on")
        .next().addClass("catenavi_on").removeClass("catenavi_next")
        .next().addClass("catenavi_next")
    $(".con_now").removeClass("con_now").hide().next().addClass("con_now").show().children("#" + next).show()
})

$("#cancel_btn").on("click", function() {
    closeCategory()
})
/* 상단 Tab 클릭 시*/
$(".atm_cateadd_navi").children().on("click", function() {
    var depth = ($(this).attr("id")).toString()
    depth = depth.substring(depth.length - 1)
    if(depth > 1 && typeof($("#nav" + (depth - 1)).attr("value")) == "undefined") {
        alert("하위 카테고리를 설정해주세요.")
        return
    } else if($("#nav" + (depth - 1)).attr("value") == "all") {
        return
    }
    $(".atm_cateadd_navi").children().removeClass("catenavi_on").removeClass("catenavi_next")
    $(this).addClass("catenavi_on")
        .next().addClass("catenavi_next")
    $(".atm_cateadd_con_wrapper").children().removeClass("con_now").hide()
    $(".atm_cateadd_con_wrapper .atm_cateadd_con" + depth).addClass("con_now").show()
})



/* atm_cateadd 컨트롤 */
function openCategory() {
    $('.atm_cateedit_btn').hide()
    $('.atm_cateadd').children().show()
    $('.atm_cateadd').animate({height : cateaddHeightOrg}, 300)
}

function closeCategory(time) {
    cateaddHeightOrg = $('.atm_cateadd').height()
    $('.atm_cateadd').animate({height : 0}, time)
    $('.atm_cateadd').children().hide()
    $('.atm_cateedit_btn').show()
}



/* atm_cateadd_navi 컨트롤 */
function goNext() {
    if($('.atm_cateadd_navi').children(":last").hasClass("fifth catenavi_on"))
        return
    extendNav()
    $('.atm_cateadd_navi').children(".catenavi_on").removeClass("catenavi_on")
        .next().removeClass("catenavi_next").addClass("catenavi_on")
        .next().addClass("catenavi_next")
}

function extendNav() {
    if(!($('.atm_cateadd_navi').children(":last").hasClass("catenavi_next")))
        return
    if($('.atm_cateadd_navi').children(".atm_catenavi_el3").length > 0)
        nav3to4()
    else if($('.atm_cateadd_navi').children(".atm_catenavi_el4").length > 0)
        nav4to5()
    return
}

function nav3to4() {
    if(!$('.atm_cateadd_navi').children().hasClass("atm_catenavi_el3")) {
        console.error("error : \".atm_cateadd_navi\" doesn't have 3 depth category")
        return
    }
    $('.atm_cateadd_navi').children().removeClass("atm_catenavi_el3").addClass("atm_catenavi_el4")
    $('.atm_cateadd_navi').append("<div class=\"atm_catenavi_el4 fourth\">4단계</div>")
}

function nav4to5() {
    if(!$('.atm_cateadd_navi').children().hasClass("atm_catenavi_el4")) {
        console.error("error : \".atm_cateadd_navi\" doesn't have 4 depth category")
        return
    }
    $('.atm_cateadd_navi').children().removeClass("atm_catenavi_el4").addClass("atm_catenavi_el5")
    $('.atm_cateadd_navi').append("<div class=\"atm_catenavi_el5 fifth\">5단계</div>")
}
