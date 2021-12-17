function fCloseAddCouponDiv() {
  $("#AddCouponDiv").slideUp();
}

// 모달 폼 스크립트
function AddCouponModalClose() {
  $("#AddCouponModal").hide();
}

window.onclick = function(event) {
  if (event.target == document.getElementById("AddCouponModal")) {
    $("#AddCouponModal").hide();
  }

  if (event.target == document.getElementById("UseCouponModal")) {
    $("#UseCouponModal").hide();
  }
};

function fAddCouponModal() {
  $("#AddCouponModal").show();
}

function UseCouponModalClose() {
  $("#UseCouponModal").hide();
}

function UseCouponLoadingModalClose() {
  $("#UseCouponLoadingModal").hide();
}

window.onclick = function(event) {
  if (event.target == document.getElementById("AddCouponModal")) {
    $("#AddCouponModal").hide();
  }

  if (event.target == document.getElementById("UseCouponModal")) {
    $("#UseCouponModal").hide();
  }
};

function fUseCouponModal() {
  $("#UseCouponModal").show();
}

function fUseCoupon() {
  UseCouponModalClose();
  $("#UseCouponLoadingModal").show();
}
