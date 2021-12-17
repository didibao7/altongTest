// sidebar 제어 -- diana
const leftBarOpen = () => {
  const leftBar = document.querySelector(".univ_left_bar");
  const mask = document.querySelector(".univ_mask");
  leftBar.classList.add("open_LeftBar");
  mask.classList.remove("close_mask");
};

const leftBarClose = () => {
  const leftBar = document.querySelector(".univ_left_bar");
  const mask = document.querySelector(".univ_mask");
  leftBar.classList.remove("open_LeftBar");
  mask.classList.add("close_mask");
};

// slider + sidebar '나의 공간' slider -- diana
let slideUp = (target, duration) => {
  target.style.transitionProperty = "height, margin, padding";
  target.style.transitionDuration = duration + "ms";
  target.style.boxSizing = "border-box";
  target.style.height = target.offsetHeight + "px";
  target.offsetHeight;
  target.style.overflow = "hidden";
  target.style.height = 0;
  target.style.paddingTop = 0;
  target.style.paddingBottom = 0;
  target.style.marginTop = 0;
  target.style.marginBottom = 0;
  window.setTimeout(() => {
    target.style.display = "none";
    target.style.removeProperty("height");
    target.style.removeProperty("padding-top");
    target.style.removeProperty("padding-bottom");
    target.style.removeProperty("margin-top");
    target.style.removeProperty("margin-bottom");
    target.style.removeProperty("overflow");
    target.style.removeProperty("transition-duration");
    target.style.removeProperty("transition-property");
  }, duration);
};

let slideDown = (target, duration) => {
  target.style.removeProperty("display");
  let display = window.getComputedStyle(target).display;

  if (display === "none") display = "";

  target.style.display = display;
  let height = target.offsetHeight;
  target.style.overflow = "hidden";
  target.style.height = 0;
  target.style.paddingTop = 0;
  target.style.paddingBottom = 0;
  target.style.marginTop = 0;
  target.style.marginBottom = 0;
  target.offsetHeight;
  target.style.boxSizing = "border-box";
  target.style.transitionProperty = "height, margin, padding";
  target.style.transitionDuration = duration + "ms";
  target.style.height = height + "px";
  target.style.removeProperty("padding-top");
  target.style.removeProperty("padding-bottom");
  target.style.removeProperty("margin-top");
  target.style.removeProperty("margin-bottom");
  window.setTimeout(() => {
    target.style.removeProperty("height");
    target.style.removeProperty("overflow");
    target.style.removeProperty("transition-duration");
    target.style.removeProperty("transition-property");
  }, duration);
};

const leftBarInnerSlider = () => {
  const slideIcon = document.querySelector(".univ_menuItem_removeIcon");
  const slideList = document.querySelector(".univ_left_slideList");

  const isOpen = slideIcon.innerHTML == "remove" ? true : false;

  if (isOpen) {
    slideUp(slideList, 200);
    slideIcon.innerHTML = "add";
  } else {
    slideDown(slideList, 200);
    slideIcon.innerHTML = "remove";
  }
};
