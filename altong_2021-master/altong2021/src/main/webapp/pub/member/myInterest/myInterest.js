/*JQuery 확장*/
String.prototype.replaceAll = function(org, dest) {
  return this.split(org).join(dest);
};
var selectedItem = {};
var memo = [];
(function() {
  for (let i = 0; i < 5; i++) {
    memo.push({ html: null, status: "off" });
  }
})();

var model = {
  data: null,
  loadData: function(Code) {
    var urlStr = "/member/interest/getInterest?";
    for (let key in Code) {
      urlStr += key + "=" + Code[key] + "&";
    }
    var ret = $.ajax({
      url: urlStr,
      async: false
    }).responseText;
    var retJson = $.parseJSON(ret);
    //console.log(retJson);
    var cate_nm;
    for (let i = 0; i < retJson.length; i++) {
    	//console.log('cate5 : ' + retJson[i].section5Code);
    	if(retJson[i].section4Code != undefined && retJson[i].section4Code != '0' && retJson[i].section4Code != '') {
    		//console.log('cate5 : ' + getLangStr("cate5_" + retJson[i].code));
    		cate_nm = getLangStr("cate5_" + retJson[i].code);
    	}
    	else if(retJson[i].section3Code != undefined && retJson[i].section3Code != '0' && retJson[i].section3Code != '') {
    		//console.log('cate4 : ' + getLangStr("cate4_" + retJson[i].code));
    		cate_nm = getLangStr("cate4_" + retJson[i].code);
    	}
    	else if(retJson[i].section2Code != undefined && retJson[i].section2Code != '0' && retJson[i].section2Code != '') {
    		//console.log('cate3 : ' + getLangStr("cate3_" + retJson[i].code));
    		cate_nm = getLangStr("cate3_" + retJson[i].code);
    	}
    	else if(retJson[i].section1Code != undefined && retJson[i].section1Code != '0' && retJson[i].section1Code != '') {
    		//console.log('cate2 : ' + getLangStr("cate2_" + retJson[i].code));
    		cate_nm = getLangStr("cate2_" + retJson[i].code);
    	}
    	else {
    		//console.log('cate1 : ' + getLangStr("cate" + retJson[i].code));
    		cate_nm = getLangStr("cate" + retJson[i].code);
    	}
		//retJson[i].codeName = retJson[i].codeName.replaceAll(";", " ");
		//retJson[i].codeName = retJson[i].codeName.replaceAll("@", "");
		retJson[i].codeName = cate_nm;
    }
    return retJson;
  }
};
var template = {
  loadTemplate: function(id) {
    return $("#" + id).html();
  },
  replaceTemplate: function(target, data) {
    for (var key in data) {
      target = target.replaceAll("{" + key + "}", data[key]);
    }
    return target;
  }
};
var view = {
  showCategoryItem: function(data) {
    const $targetToAdd = $(".atm_cateadd_con");
    $targetToAdd.html("");
    for (let i = 0; i < data.length; i++) {
      var htmlStr = template.loadTemplate("CategoryElementTemplate");
      htmlStr = template.replaceTemplate(htmlStr, data[i]);
      $targetToAdd.append(htmlStr);
    }
  },
  initView: function() {
    $(".atm_cateadd").hide();
  }
};

/*이벤트 리스너*/
$(document).on("click", ".atm_cateeditbtn_add", function() {
  $(".atm_cateadd").show();
  $(this).hide();
  const data = model.loadData({});
  view.showCategoryItem(data);
});

var LastFlag = 0;
$(document).on("click", ".atm_catecon_el", function() {
  const $this = $(this);
  const idx =
    $(".naviNow")
      .attr("id")
      .charAt(3) - 1;
  const nowCode = $this.attr("id");

  $this
    .parent()
    .children()
    .removeClass("cateon_on");
  $this.addClass("cateon_on");
  selectedItem["Code" + (idx + 1)] = nowCode;
  for (let key in selectedItem) {
    if (parseInt(key.charAt(4)) > idx + 1) delete selectedItem[key];
  }

  memo[idx].html = $this.parent().html();
  memo[idx].status = "on";
  for (let i = idx + 1; i < memo.length; i++) {
    memo[i].status = "off";
  }

  if (idx < 5 - 1) {
    const nextData = model.loadData(selectedItem);

    if (nextData.length == 0) {
      if (LastFlag != 0)
        alert(getLangStr("jsm_0034"));
      lastFlag = 1;
      return;
    }

    const $now = $(".naviNow");
    const $next = $(".naviNext");

    $now.removeClass("naviNow");
    $next
      .removeClass("naviNext")
      .addClass("naviNow")
      .next()
      .addClass("naviNext");

    view.showCategoryItem(nextData);
  }
});

$(document).on("click", ".atm_catenavi_el3", function() {
  const $this = $(this);
  const idx = $this.attr("id").charAt(3) - 1;

  if (memo[idx].status == "off") {
    //alert("하위 카테고리를 먼저 선택해주세요.")
    alert(getLangStr("jsm_0035"));
    return;
  }

  $this
    .addClass()
    .parent()
    .children()
    .removeClass("naviNow")
    .removeClass("naviNext");
  $this
    .addClass("naviNow")
    .next()
    .addClass("naviNext");

  const $cateElementWrapper = $(".atm_cateadd_con");
  $cateElementWrapper.html(memo[idx].html);
});

$(document).on("click", ".atm_btn_gre", function() {
  if (myInterestCount >= 10) {
    alert(getLangStr("jsm_0036"));
    return;
  }
  var urlStr = "/member/interest/addInterest?";
  for (var key in selectedItem) {
    urlStr += key + "=" + selectedItem[key] + "&";
  }
//console.log('urlStr : ' + urlStr);
  var ret = $.ajax({
    url: urlStr,
    type: "post",
    async: false
  }).responseText;
  location.reload();
});

$(document).on("click", ".atm_btn_gray", function() {
  $(".atm_cateadd").hide();
  $(".atm_cateadd_navi")
    .children()
    .removeClass("naviNow")
    .removeClass("naviNext");
  $(".atm_cateadd_navi")
    .children("#nav1")
    .addClass("naviNow")
    .next()
    .addClass("naviNext");
  $(".atm_cateeditbtn_add").show();
});

$(document).on("click", ".atm_cateedit_xbtn", function() {
  var check = confirm(getLangStr("jsm_0008"));
  console.log(check);
  if (check) {
    var seq = $(this).data("seq");
    $.ajax({
      url: "/member/interest/deleteInterest",
      data: {
        Seq: seq
      },
      success: function(data) {
        location.reload();
      }
    });
  }
});
