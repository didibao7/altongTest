<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="atm_logobar_top">
  <div class="atm_logobar_top_pc">
    <div class="atm_logobar_div100">
      <a href="/alaview/list"
        ><img src="/Common/images/alaview_logo.png" class="atm_logo_img"
      /></a>
    </div>
    <a href="#" id="sidebar-main-trigger" class="icon float-left"
      ><img src="/Common/images/alaview_menu.png" class="atm_logobar_btn_L0"
    /></a>
    <div class="column">
      <div id="sb-search" class="sb-search">
        <form action="/question/questionSearch">
          <input
            class="sb-search-input"
            placeholder="검색단어를 입력 후 엔터..."
            type="text"
            value=""
            name="src_Text"
            id="search"
          />
          <input class="sb-search-submit" type="submit" value="" />
          <span class="sb-icon-search"
            ><img
              src="/Common/images/alaview_search.png"
              class="atm_logobar_btn_R0"
          /></span>
        </form>
      </div>
    </div>
    <p>
      <img src="/Common/images/alaview_video.png" class="atm_logobar_btn_R1" />
    </p>
    <script>
      new UISearch(document.getElementById("sb-search"));
    </script>
  </div>
</div>

<div class="alaview_topwrapper">
  <div class="swiper-slide alaview_topBtns">
    <div class="alaview_topBtn">
      <img src="/UploadFile/Section/category/icon_0.png" /></br>
      전체
    </div>
    <div class="alaview_topBtn">
      <img src="/UploadFile/Section/category/icon_1.png" /></br>
      배움
    </div>
    <div class="alaview_topBtn">
      <img src="/UploadFile/Section/category/icon_2.png" /></br>
      생활
    </div>
    <div class="alaview_topBtn">
      <img src="/UploadFile/Section/category/icon_3.png" /></br>
      건강
    </div>
    <div class="alaview_topBtn">
      <img src="/UploadFile/Section/category/icon_4.png" /></br>
      고민
    </div>
    <div class="alaview_topBtn">
      <img src="/UploadFile/Section/category/icon_5.png" /></br>
      문예
    </div>
    <div class="alaview_topBtn">
      <img src="/UploadFile/Section/category/icon_6.png" /></br>
      세상
    </div>
    <div class="alaview_topBtn">
      <img src="/UploadFile/Section/category/icon_7.png" /></br>
      돈
    </div>
    <div class="alaview_topBtn">
      <img src="/UploadFile/Section/category/icon_8.png" /></br>
      컴/폰
    </div>
    <div class="alaview_topBtn">
      <img src="/UploadFile/Section/category/icon_9.png" /></br>
      스포츠
    </div>
    <div class="alaview_topBtn">
      <img src="/UploadFile/Section/category/icon_10.png" /></br>
      게임
    </div>
  </div>
</div>