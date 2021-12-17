function selectView() {
  // 탭 컨텐츠 hide 후 현재 탭메뉴 페이지만 보이기
  $(".select_view").hide();
  $(
    $(".select_btn_selected")
      .find("a")
      .attr("href")
  ).show();

  // 탭 메뉴 클릭 이벤트 생성
  $(".select_div").click(function(event) {
    let tabName = event.target.tagName; // 현재 선택된 태그네임
    let selectedBtnTab =
      tabName.toString() == "A"
        ? $(event.target).parent("div")
        : $(event.target);
    let selectedTab = $("div[class ~= select_btn_selected]"); // 현재 select_btn_selected 클래스를 가진 탭
    let isSelected = false;

    // 현재 클릭된 탭이 select_btn_selected를 가졌는지 확인
    isSelected = $(selectedBtnTab).hasClass("select_btn_selected");

    // select_btn_selected를 가지지 않았을 경우만 실행
    if (!isSelected) {
      $(
        $(selectedTab)
          .find("a")
          .attr("href")
      ).hide();
      $(selectedTab).removeClass("select_btn_selected");

      $(selectedBtnTab).addClass("select_btn_selected");
      $(
        $(selectedBtnTab)
          .find("a")
          .attr("href")
      ).show();
    }

    return false;
  });
}

$(function() {
  selectView();
});
