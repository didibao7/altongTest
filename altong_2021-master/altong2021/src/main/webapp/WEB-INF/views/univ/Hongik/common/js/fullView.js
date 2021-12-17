function heightCheck() {
  let deviceHeight = window.innerHeight;
  let topHeight = document.querySelector(".univ_wrap").offsetHeight;

  let viewHeight = deviceHeight - topHeight;
  document.querySelector(".not_bottom_div").style.height = viewHeight + "px";
}

document.addEventListener("DOMContentLoaded", heightCheck);

$(window).resize(heightCheck);
