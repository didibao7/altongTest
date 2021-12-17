document.addEventListener("DOMContentLoaded", function() {
  document.querySelectorAll(".atm_bank_tbl2").forEach(function(iter) {
    iter.style.display = "none";
  });

  document.querySelector(".atm_bank_grpDiv").style.display = "";
  document.querySelector("#atm_bank_detTbl_midDiv").style.display = "none";

  axios
    .get("/member/bank/indexAjax", {
      params: {
        act: "DETAIL_HISTORY",
        target: getTradeTypeList(),
        pg: setPageNumber(1),
        rows: 20,
        sort: getSortFlag(),
        before: getDateFlag()
      }
    })
    .then(res => {
      const data = res.data;
      renderDetailHistory(data.result);
    })
    .catch(res => {
      console.log(res);
    });

  axios
    .get("/member/bank/indexAjax?act=RATIO")
    .then(res => {
      const data = res.data;
      checkTotalAlmoney(data.result);
      checkCumulative(data.result);
      checkTotalIncrease(data.result);
      viewCumulative(data.result);
      viewAcceptance(data.result);
    })
    .catch(res => {
      console.log(res);
    });
});

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

const ifZero = iter => {
  if (iter == 0) return "- ";
  else return "▲ ";
};

const isZero = iter => {
  if (iter == 0) return "";
  else return iter;
};

const addComma = value => {
  return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
};

const getDateFlag = () => {
  const dateFlag = document.querySelector(".selectBox");

  return dateFlag.value;
};

const generateToggleHandler = function(target) {
  const getElements = function(section) {
    return {
      materialIcon: section.querySelector(".material-icons"),
      arrow: section.querySelector(".atm_bank_v"),
      detail: section.parentElement.parentElement.nextElementSibling
    };
  };
  const handleDetailOpen = function(section) {
    const { materialIcon, arrow, detail } = getElements(section);

    materialIcon.innerHTML = "remove_circle";
    arrow.innerHTML = "∧";
    slideDown(detail, 200);
  };
  const handleDetailClose = section => {
    const { materialIcon, arrow, detail } = getElements(section);

    materialIcon.innerHTML = "add_circle";
    arrow.innerHTML = "∨";
    slideUp(detail, 200);
  };
  const changeDom = function(section) {
    const { materialIcon, arrow, detail } = getElements(section);
    const isOpen = materialIcon.innerHTML == "add_circle";

    if (isOpen) {
      handleDetailOpen(section);
    } else {
      handleDetailClose(section);
    }
  };
  return function(e) {
    changeDom(target);
  };
};

const checkTotalIncrease = data => {
  const increaseA = Object.values(data.data.trend.earn).reduce((acc, cur) => {
    return (acc += parseFloat(cur));
  }, 0);
  const increaseB = Object.values(data.data.trend.con).reduce((acc, cur) => {
    return (acc += parseFloat(cur));
  }, 0);

  const totalIncrease = document.querySelector(".totalIncrease");

  if (increaseA + increaseB > 0) {
    totalIncrease.parentElement.style.color = "#5a5a5a";
    totalIncrease.innerHTML =
      "▲ " + addComma(Math.abs(increaseA + increaseB).toFixed(1));
  } else if (increaseA + increaseB < 0) {
    totalIncrease.parentElement.style.color = "#ed3c78";
    totalIncrease.innerHTML =
      "▼ " + addComma(Math.abs(increaseA + increaseB).toFixed(1));
  } else {
    totalIncrease.parentElement.style.color = "#ccc";
    totalIncrease.innerHTML = "-";
  }
};

document.querySelectorAll(".atm_bank_tbl_tr").forEach(function(iter) {
  iter.addEventListener("click", generateToggleHandler(iter));
});

const handleGraphDetail = e => {
  const arrow = document.querySelector(".atm_bank_graph .atm_bank_v ");
  const detail = document
    .querySelector(".atm_bank_graph")
    .parentElement.querySelector(".atm_bank_grpDiv");

  const isOpen = arrow.innerHTML == "∧" ? true : false;

  if (isOpen) {
    slideUp(detail, 200);
    arrow.innerHTML = "∨";
  } else {
    slideDown(detail, 200);
    arrow.innerHTML = "∧";
  }
};

document
  .querySelector(".atm_bank_graph")
  .addEventListener("click", handleGraphDetail);

const handleGubunChange = e => {
  const mid = document.querySelector("#atm_bank_detTbl_midDiv");

  if (e.target.id == "atm_bank_rbA" || e.target.id == "atm_bank_rbD") {
    slideUp(mid, 200);
    return;
  }

  document
    .querySelectorAll(".atm_bank_inDetTbl .trade_type_selector")
    .forEach(iter => {
      iter.style.display = "none";
    });

  if (e.target.dataset.child != "") {
    document.querySelector(e.target.dataset.child).style.display = "";
    slideDown(mid, 200);
  } else {
    slideUp(mid, 200);
  }
};

document
  .querySelectorAll("#gubun input[type=radio]")
  .forEach(iter => iter.addEventListener("change", handleGubunChange));

const getTradeTypeList = () => {
  const visibleItemWrapper = Array.from(
    document.querySelectorAll(".trade_type_selector")
  ).filter(iter => {
    return (
      iter.style.display != "none" &&
      iter.parentElement.parentElement.parentElement.parentElement.style
        .display != "none"
    );
  })[0];

  if (!visibleItemWrapper) {
    if (document.querySelector("#atm_bank_rbD").checked) return '["41"]';
    else return "[]";
  }

  const pickedItemList = Array.from(
    visibleItemWrapper.querySelectorAll("input[type=checkbox]")
  )
    .filter(iter => iter.checked)
    .reduce((acc, cur) => {
      return (acc = [...acc, cur.dataset.type || ""]);
    }, [])
    .filter(iter => iter !== "");

  return JSON.stringify(pickedItemList);
};

const handleSwitchFlag = e => {
  const unvisibleItemWrapper = Array.from(
    document.querySelectorAll(".trade_type_selector")
  ).filter(
    iter =>
      iter.style.display == "none" ||
      iter.parentElement.parentElement.parentElement.parentElement.parentElement
        .style.display == "none"
  );

  unvisibleItemWrapper.forEach(iter => {
    Array.from(iter.querySelectorAll("input[type=checkbox]")).forEach(iter => {
      iter.checked = true;
    });
  });
};

document
  .querySelectorAll("#gubun > input")
  .forEach(iter => iter.addEventListener("change", handleSwitchFlag));

document
  .querySelector(".atm_bank_detailInDiv > span")
  .addEventListener("click", e => {
    setPageNumber(1);
    axios
      .get("/member/bank/indexAjax", {
        params: {
          act: "DETAIL_HISTORY",
          target: getTradeTypeList(),
          pg: setPageNumber(1),
          rows: 20,
          sort: getSortFlag(),
          before: getDateFlag()
        }
      })
      .then(res => {
        const data = res.data;
        renderDetailHistory(data.result);
      })
      .catch(res => {
        console.log(res);
      });
  });

document
  .querySelector(".atm_bank_recentDiv2 > p")
  .addEventListener("click", e => {
    axios
      .get("/member/bank/indexAjax", {
        params: {
          ACT: "DETAIL_HISTORY",
          target: getTradeTypeList(),
          pg: getPageNumber() + 1,
          rows: 20,
          sort: getSortFlag(),
          before: getDateFlag()
        }
      })
      .then(res => {
        const data = res.data;
        renderDetailHistory(data.result, false);
        setPageNumber(getPageNumber() + 1);
      })
      .catch(res => {
        console.log(res);
      });
  });

document
  .querySelector(".atm_bank_recentDiv2 > p")
  .addEventListener("click", e => {});

const getSortFlag = () => {
  const wrapper = document.querySelector("#rb2 .atm_bank_rb2All").checked;

  if (wrapper == true) return "desc";
  else return "asc";
};

const getPageNumber = () =>
  parseInt(document.querySelector(".atm_bank_recentDiv2").dataset.curPage);

const setPageNumber = n =>
  (document.querySelector(".atm_bank_recentDiv2").dataset.curPage = n);

const renderDetailHistory = (data, option = true) => {
  const wrapper = document.querySelector(".atm_bank_reTbl tbody");
  const changeText = document.querySelector(".atm_bank_recentDiv2 > p");

  if (data.length == 0) {
    changeText.style.display = "";
    changeText.innerHTML = "데이터가 존재하지 않습니다."; // "데이터가 존재하지 않습니다." 를 getLangStr("문구에 해당하는 코드") 로 대체 => changeText.innerHTML = getLangStr("문구에 해당하는 코드");
    changeText.style.fontStyle = "italic";
  } else if (data.length == 20) {
    changeText.style.display = "";
    changeText.innerHTML = "더 보기 ∨";				// "더 보기 ∨" 를 getLangStr("문구에 해당하는 코드") 로 대체 => changeText.innerHTML = getLangStr("문구에 해당하는 코드");
  } else {
    changeText.style.display = "none";
  }

  const flags = flag => {
    if (flag == 1) {
      return "atm_bank_blue";
    } else if (flag == 2) {
      return "atm_bank_red";
    } else {
      return "atm_bank_green";
    }
  };

  const newMarkup = data.reduce((acc, cur, idx) => {
    const rowGray = idx % 2 == 1 ? "atm_bank_mylist_gray" : "";
    const color = () => {
      if (flags(cur.flag) == "atm_bank_blue") return "+";
      else return "-";
    };
    if (cur.link)
      cur.TradeType = `<a href="${cur.link}" target="_blank">${
        cur.TradeType
      }</a>`;
    return (acc += `<tr class="atm_bank_reTblCon ${rowGray}">
            <td onClick="$(this).text('${cur.regdate.slice(
              0,
              19
            )}')">${cur.regdate.slice(0, 10)}</td>
            <td>${cur.TradeType}</td>
            <td class="${flags(cur.flag)}">${color(cur.Almoney)} ${addComma(
      Math.abs(cur.Almoney).toFixed(1)
    )}</td>
            <td>${addComma(Math.abs(cur.Balance).toFixed(1))}</td>
          </tr>`);
  }, "");

  if (option) wrapper.innerHTML = newMarkup;
  else wrapper.innerHTML += newMarkup;
};

const checkTotalAlmoney = data => {
  const totalAlmoney = document.querySelector(".atm_superbold");
  return (totalAlmoney.innerHTML = addComma(
    Math.abs(`${data.total.total}`).toFixed(1)
  ));
};

const checkCumulative = data => {
  const wrapper = document.querySelectorAll(".atm_bank_cumulative");
  const increaseWrapper = document.querySelectorAll(".atm_bank_titInc");

  const datasInc = data.data.trend.earn;
  const datasDec = data.data.trend.con;

  const incCumulative = Object.values(datasInc).reduce((acc, cur, idx) => {
    return acc + parseFloat(cur);
  }, 0);

  const decCumulative = Object.values(datasDec).reduce((acc, cur, idx) => {
    return acc + parseFloat(cur);
  }, 0);

  for (let i = 0; i < 3; i++) {
    if (i == 0) {
      wrapper[i].innerHTML = addComma(
        `${parseFloat(data.total.earn).toFixed(1)}`
      );
      if (ifZero(`${incCumulative.toFixed(1)}`) == "▲ ")
        increaseWrapper[i].innerHTML =
          ifZero(`${incCumulative.toFixed(1)}`) +
          `${addComma(incCumulative.toFixed(1))}`;
      else increaseWrapper[i].innerHTML = ifZero(`${incCumulative.toFixed(1)}`);
    }
    if (i == 1) {
      wrapper[i].innerHTML = addComma(Math.abs(`${data.total.con}`).toFixed(1));
      if (ifZero(`${Math.abs(decCumulative).toFixed(1)}`) == "▲ ")
        increaseWrapper[i].innerHTML =
          ifZero(`${Math.abs(decCumulative).toFixed(1)}`) +
          addComma(Math.abs(`${decCumulative}`).toFixed(1));
      else
        increaseWrapper[i].innerHTML = ifZero(
          `${Math.abs(decCumulative).toFixed(1)}`
        );
    }
    if (i == 2) {
      wrapper[i].innerHTML = addComma(
        Math.abs(
          data.data.etc.reduce(
            (acc, cur) => (acc += parseFloat(cur.Almoney || 0)),
            0
          )
        ).toFixed(1)
      );
    }
  }
};

const viewCumulative = data => {
  const wrapperA = document.querySelector("#atm_bank_tbl_divsInc");
  const wrapperB = document.querySelector("#atm_bank_tbl_divsDec");

  const utilAlmoneyFormat = almoney => {
    if (almoney > 0 && almoney < 1) {
      return "0" + almoney.slice(0, -3);
    } else if (almoney == 0) {
      return "0" + almoney.slice(0, -3);
    } else {
      return almoney.slice(0, -3);
    }
  };

  const { earn } = data.data;
  const earnTrend = data.data.trend.earn;

  const markupA = Object.keys(earn).reduce((acc, cur, idx) => {
    const secondDiv = idx % 2 == 1 ? "atm_bank_tbl_divB" : "atm_bank_tbl_divA";
    return (acc += `<div class="${secondDiv}">
                      <div class="atm_bank_intit">${cur}</div>
                      <div>
                        <span>${addComma(
                          parseFloat(earn[cur]).toFixed(1)
                        )}</span>알</br>
                        <span class="atm_bank_blue">${ifZero(
                          earnTrend[cur]
                        )} ${isZero(
      addComma(parseFloat(utilAlmoneyFormat(earnTrend[cur])).toFixed(1))
    )}</span>
                      </div>
                    </div>`);
  }, "");

  const { con } = data.data;
  const conTrend = data.data.trend.con;

  const markupB = Object.keys(con).reduce((acc, cur, idx) => {
    const secondDiv = idx % 2 == 1 ? "atm_bank_tbl_divB" : "atm_bank_tbl_divA";
    return (acc += `<div class="${secondDiv}">
                      <div class="atm_bank_intit">${cur}</div>
                      <div>
                        <span>${addComma(
                          Math.abs(con[cur]).toFixed(1)
                        )}</span>알</br>
                        <span class="atm_bank_red">${ifZero(
                          conTrend[cur]
                        )} ${isZero(
      addComma(Math.abs(utilAlmoneyFormat(conTrend[cur])).toFixed(1))
    )}</span>
                      </div>
                    </div>`);
  }, "");

  wrapperA.innerHTML = markupA;
  wrapperB.innerHTML = markupB;
};

const viewAcceptance = data => {
  const acceptanceWrapper = document.querySelector("#atm_bank_inTblC");
  const acceptData = data.data.etc;
  
  var msg1 = getLangStr("문구에 해당하는 코드");
  var msg2 = getLangStr("문구에 해당하는 코드");

  const acceptMarkup = acceptData.reduce((acc, cur, idx) => {
    return (acc += `<tr>
                  <td class="atm_bank_latDat">${cur.regdate.slice(
                    0,
                    10
                  )}</td>
                  <td class="atm_bank_latAl"><span>${addComma(
                    Math.abs(cur.Almoney).toFixed(1)
                  )}</span> 알</td>
                </tr>`);
  }, ""); // 알 => ${msg1}

  if (acceptanceWrapper.length == 0) {
    acceptanceWrapper.innerHTML =
      acceptMarkup +
      `<tr><td id="atm_bank_accText" colspan="2">데이터가 존재하지 않습니다.</td></tr>`; // 데이터가 존재하지 않습니다. => ${msg2}
  } else {
    acceptanceWrapper.innerHTML = acceptMarkup;
  }
};

document.querySelectorAll(".atm_bank_detCbAll").forEach(iter => {
  iter.addEventListener("change", e => {
    const sibilingCheckbox = Array.from(
      e.target.parentNode.querySelectorAll("input[type=checkbox]")
    );
    if (e.target.checked) {
      sibilingCheckbox.forEach(iter => {
        iter.checked = true;
      });
    } else {
      sibilingCheckbox.forEach(iter => {
        iter.checked = false;
      });
    }
  });
});

document
  .querySelectorAll(".trade_type_selector input[type=checkbox]")
  .forEach(iter =>
    iter.addEventListener("change", e => {
      const sibilingCheckbox = Array.from(
        e.target.parentNode.querySelectorAll("input[type=checkbox]")
      );
      const sibilingAllCheckbox = e.target.parentNode.querySelector(
        ".atm_bank_detCbAll"
      );

      const isOtherChecked = sibilingCheckbox
        .filter(iter => !iter.classList.contains("atm_bank_detCbAll"))
        .reduce((acc, cur) => (acc &= cur.checked), true);

      if (!e.target.checked) {
        sibilingAllCheckbox.checked = false;
      } else if (isOtherChecked) {
        sibilingAllCheckbox.checked = true;
      }
    })
  );
