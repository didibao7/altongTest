"use strict";function _toConsumableArray(a){return _arrayWithoutHoles(a)||_iterableToArray(a)||_nonIterableSpread()}function _nonIterableSpread(){throw new TypeError("Invalid attempt to spread non-iterable instance")}function _iterableToArray(a){if(Symbol.iterator in Object(a)||"[object Arguments]"===Object.prototype.toString.call(a))return Array.from(a)}function _arrayWithoutHoles(a){if(Array.isArray(a)){for(var b=0,c=Array(a.length);b<a.length;b++)c[b]=a[b];return c}}document.addEventListener("DOMContentLoaded",function(){document.querySelectorAll(".atm_bank_tbl2").forEach(function(a){a.style.display="none"}),document.querySelector(".atm_bank_grpDiv").style.display="",document.querySelector("#atm_bank_detTbl_midDiv").style.display="none",axios.get("/member/bank/indexAjax",{params:{act:"DETAIL_HISTORY",target:getTradeTypeList(),pg:setPageNumber(1),rows:20,sort:getSortFlag(),before:getDateFlag()}}).then(function(a){var b=a.data;renderDetailHistory(b.result)}).catch(function(a){console.log('a : '+ a)}),axios.get("/member/bank/indexAjax?act=RATIO").then(function(a){var b=a.data;checkTotalAlmoney(b.result),checkCumulative(b.result),checkTotalIncrease(b.result),viewCumulative(b.result),viewAcceptance(b.result)}).catch(function(a){console.log(a)})});var slideUp=function(a,b){a.style.transitionProperty="height, margin, padding",a.style.transitionDuration=b+"ms",a.style.boxSizing="border-box",a.style.height=a.offsetHeight+"px",a.offsetHeight,a.style.overflow="hidden",a.style.height=0,a.style.paddingTop=0,a.style.paddingBottom=0,a.style.marginTop=0,a.style.marginBottom=0,window.setTimeout(function(){a.style.display="none",a.style.removeProperty("height"),a.style.removeProperty("padding-top"),a.style.removeProperty("padding-bottom"),a.style.removeProperty("margin-top"),a.style.removeProperty("margin-bottom"),a.style.removeProperty("overflow"),a.style.removeProperty("transition-duration"),a.style.removeProperty("transition-property")},b)},slideDown=function(a,b){a.style.removeProperty("display");var c=window.getComputedStyle(a).display;"none"===c&&(c=""),a.style.display=c;var d=a.offsetHeight;a.style.overflow="hidden",a.style.height=0,a.style.paddingTop=0,a.style.paddingBottom=0,a.style.marginTop=0,a.style.marginBottom=0,a.offsetHeight,a.style.boxSizing="border-box",a.style.transitionProperty="height, margin, padding",a.style.transitionDuration=b+"ms",a.style.height=d+"px",a.style.removeProperty("padding-top"),a.style.removeProperty("padding-bottom"),a.style.removeProperty("margin-top"),a.style.removeProperty("margin-bottom"),window.setTimeout(function(){a.style.removeProperty("height"),a.style.removeProperty("overflow"),a.style.removeProperty("transition-duration"),a.style.removeProperty("transition-property")},b)},ifZero=function(a){return 0==a?"- ":"\u25B2 "},isZero=function(a){return 0==a?"":a},addComma=function(a){return a.toString().replace(/\B(?=(\d{3})+(?!\d))/g,",")},getDateFlag=function(){var a=document.querySelector(".selectBox");return a.value},generateToggleHandler=function(a){var b=function(a){return{materialIcon:a.querySelector(".material-icons"),arrow:a.querySelector(".atm_bank_v"),detail:a.parentElement.parentElement.nextElementSibling}},c=function(a){var c=b(a),d=c.materialIcon,e=c.arrow,f=c.detail;d.innerHTML="remove_circle",e.innerHTML="\u2227",slideDown(f,200)},d=function(a){var c=b(a),d=c.materialIcon,e=c.arrow,f=c.detail;d.innerHTML="add_circle",e.innerHTML="\u2228",slideUp(f,200)},e=function(a){var e=b(a),f=e.materialIcon,g=e.arrow,h=e.detail,i="add_circle"==f.innerHTML;i?c(a):d(a)};return function(){e(a)}},checkTotalIncrease=function(a){var b=Math.abs,c=Object.values(a.data.trend.earn).reduce(function(a,b){return a+=parseFloat(b)},0),d=Object.values(a.data.trend.con).reduce(function(a,b){return a+=parseFloat(b)},0),e=document.querySelector(".totalIncrease");0<c+d?(e.parentElement.style.color="",e.innerHTML="\u25B2 "+addComma(b(c+d).toFixed(1))):0>c+d?(e.parentElement.style.color="#ed3c78",e.innerHTML="\u25BC "+addComma(b(c+d).toFixed(1))):(e.parentElement.style.color="#ccc",e.innerHTML="-")};document.querySelectorAll(".atm_bank_tbl_tr").forEach(function(a){a.addEventListener("click",generateToggleHandler(a))});var handleGraphDetail=function(){var a=document.querySelector(".atm_bank_graph .atm_bank_v "),b=document.querySelector(".atm_bank_graph").parentElement.querySelector(".atm_bank_grpDiv"),c=!("\u2227"!=a.innerHTML);c?(slideUp(b,200),a.innerHTML="\u2228"):(slideDown(b,200),a.innerHTML="\u2227")};document.querySelector(".atm_bank_graph").addEventListener("click",handleGraphDetail);var handleGubunChange=function(a){var b=document.querySelector("#atm_bank_detTbl_midDiv");return"atm_bank_rbA"==a.target.id||"atm_bank_rbD"==a.target.id?void slideUp(b,200):void(document.querySelectorAll(".atm_bank_inDetTbl .trade_type_selector").forEach(function(a){a.style.display="none"}),""==a.target.dataset.child?slideUp(b,200):(document.querySelector(a.target.dataset.child).style.display="",slideDown(b,200)))};document.querySelectorAll("#gubun input[type=radio]").forEach(function(a){return a.addEventListener("change",handleGubunChange)});var getTradeTypeList=function(){var a=Array.from(document.querySelectorAll(".trade_type_selector")).filter(function(a){return"none"!=a.style.display&&"none"!=a.parentElement.parentElement.parentElement.parentElement.style.display})[0];if(!a)return document.querySelector("#atm_bank_rbD").checked?"[\"41\"]":"[]";var b=Array.from(a.querySelectorAll("input[type=checkbox]")).filter(function(a){return a.checked}).reduce(function(a,b){return a=[].concat(_toConsumableArray(a),[b.dataset.type||""])},[]).filter(function(a){return""!==a});return JSON.stringify(b)},handleSwitchFlag=function(){var a=Array.from(document.querySelectorAll(".trade_type_selector")).filter(function(a){return"none"==a.style.display||"none"==a.parentElement.parentElement.parentElement.parentElement.parentElement.style.display});a.forEach(function(a){Array.from(a.querySelectorAll("input[type=checkbox]")).forEach(function(a){a.checked=!0})})};document.querySelectorAll("#gubun > input").forEach(function(a){return a.addEventListener("change",handleSwitchFlag)}),document.querySelector(".atm_bank_detailInDiv > span").addEventListener("click",function(){setPageNumber(1),axios.get("/member/bank/indexAjax",{params:{act:"DETAIL_HISTORY",target:getTradeTypeList(),pg:setPageNumber(1),rows:20,sort:getSortFlag(),before:getDateFlag()}}).then(function(a){var b=a.data;renderDetailHistory(b.result)}).catch(function(a){console.log(a)})}),document.querySelector(".atm_bank_recentDiv2 > p").addEventListener("click",function(){axios.get("/member/bank/indexAjax",{params:{act:"DETAIL_HISTORY",target:getTradeTypeList(),pg:getPageNumber()+1,rows:20,sort:getSortFlag(),before:getDateFlag()}}).then(function(a){var b=a.data;renderDetailHistory(b.result,!1),setPageNumber(getPageNumber()+1)}).catch(function(a){console.log(a)})}),document.querySelector(".atm_bank_recentDiv2 > p").addEventListener("click",function(){});var getSortFlag=function(){var a=document.querySelector("#rb2 .atm_bank_rb2All").checked;return!0==a?"desc":"asc"},getPageNumber=function(){return parseInt(document.querySelector(".atm_bank_recentDiv2").dataset.curPage)},setPageNumber=function(a){return document.querySelector(".atm_bank_recentDiv2").dataset.curPage=a},renderDetailHistory=function(a){var b=Math.abs,c=!(1<arguments.length&&void 0!==arguments[1])||arguments[1],d=document.querySelector(".atm_bank_reTbl tbody"),e=document.querySelector(".atm_bank_recentDiv2 > p");0==a.length?(e.style.display="",e.innerHTML="\uB370\uC774\uD130\uAC00 \uC874\uC7AC\uD558\uC9C0 \uC54A\uC2B5\uB2C8\uB2E4.",e.style.fontStyle="italic"):20==a.length?(e.style.display="",e.innerHTML="\uB354 \uBCF4\uAE30 \u2228"):e.style.display="none";var f=function(a){return 1==a?"atm_bank_blue":2==a?"atm_bank_red":"atm_bank_green"},g=a.reduce(function(a,c,d){var e=1==d%2?"atm_bank_mylist_gray":"";return c.link&&(c.TradeType="<a href=\"".concat(c.link,"\" target=\"_blank\">").concat(c.TradeType,"</a>")),a+="<tr class=\"atm_bank_reTblCon ".concat(e,"\">\n            <td onClick=\"$(this).text('").concat(c.regdate.slice(0,10),"')\">").concat(c.regdate.slice(0,10),"</td>\n            <td>").concat(c.TradeType,"</td>\n            <td class=\"").concat(f(c.flag),"\">").concat(function color(){return"atm_bank_blue"==f(c.flag)?"+":"-"}(c.Almoney)," ").concat(addComma(b(c.Almoney).toFixed(1)),"</td>\n            <td>").concat(addComma(b(c.Balance).toFixed(1)),"</td>\n          </tr>")},"");c?d.innerHTML=g:d.innerHTML+=g},checkTotalAlmoney=function(a){var b=document.querySelector(".atm_superbold"); return b.innerHTML=addComma(Math.abs("".concat(a.total.total)).toFixed(1))},checkCumulative=function(a){for(var b=Math.abs,c=document.querySelectorAll(".atm_bank_cumulative"),d=document.querySelectorAll(".atm_bank_titInc"),e=a.data.trend.earn,f=a.data.trend.con,g=Object.values(e).reduce(function(a,b){return a+parseFloat(b)},0),h=Object.values(f).reduce(function(a,b){return a+parseFloat(b)},0),j=0;3>j;j++)0==j&&(c[j].innerHTML=addComma("".concat(parseFloat(a.total.earn).toFixed(1))),d[j].innerHTML="\u25B2 "==ifZero("".concat(g.toFixed(1)))?ifZero("".concat(g.toFixed(1)))+"".concat(addComma(g.toFixed(1))):ifZero("".concat(g.toFixed(1)))),1==j&&(c[j].innerHTML=addComma(b("".concat(a.total.con)).toFixed(1)),d[j].innerHTML="\u25B2 "==ifZero("".concat(b(h).toFixed(1)))?ifZero("".concat(b(h).toFixed(1)))+addComma(b("".concat(h)).toFixed(1)):ifZero("".concat(b(h).toFixed(1)))),2==j&&(c[j].innerHTML=addComma(b(a.data.etc.reduce(function(a,b){return a+=parseFloat(b.Almoney||0)},0)).toFixed(1)))},viewCumulative=function(a){var b=Math.abs,c=document.querySelector("#atm_bank_tbl_divsInc"),d=document.querySelector("#atm_bank_tbl_divsDec"),e=function(a){return 0<a&&1>a?"0"+a.slice(0,-3):0==a?"0"+a.slice(0,-3):a.slice(0,-3)},f=a.data.earn,g=a.data.trend.earn,h=Object.keys(f).reduce(function(a,b,c){var d=1==c%2?"atm_bank_tbl_divB":"atm_bank_tbl_divA";return a+="<div class=\"".concat(d,"\">\n                      <div class=\"atm_bank_intit\">").concat(b,"</div>\n                      <div>\n                        <span>").concat(addComma(parseFloat(f[b]).toFixed(1)),"</span>\uC54C</br>\n                        <span class=\"atm_bank_blue\">").concat(ifZero(g[b])," ").concat(isZero(addComma(parseFloat(e(g[b])).toFixed(1))),"</span>\n                      </div>\n                    </div>")},""),i=a.data.con,j=a.data.trend.con,k=Object.keys(i).reduce(function(a,c,d){var f=1==d%2?"atm_bank_tbl_divB":"atm_bank_tbl_divA";return a+="<div class=\"".concat(f,"\">\n                      <div class=\"atm_bank_intit\">").concat(c,"</div>\n                      <div>\n                        <span>").concat(addComma(b(i[c]).toFixed(1)),"</span>\uC54C</br>\n                        <span class=\"atm_bank_red\">").concat(ifZero(j[c])," ").concat(isZero(addComma(b(e(j[c])).toFixed(1))),"</span>\n                      </div>\n                    </div>")},"");c.innerHTML=h,d.innerHTML=k},viewAcceptance=function(a){var b=document.querySelector("#atm_bank_inTblC"),c=a.data.etc,d=c.reduce(function(a,b){return a+="<tr>\n                  <td class=\"atm_bank_latDat\">".concat(b.regdate.slice(0,10),"</td>\n                  <td class=\"atm_bank_latAl\"><span>").concat(addComma(Math.abs(b.Almoney).toFixed(1)),"</span> \uC54C</td>\n                </tr>")},"");b.innerHTML=0==b.length?d+"<tr><td id=\"atm_bank_accText\" colspan=\"2\">\uB370\uC774\uD130\uAC00 \uC874\uC7AC\uD558\uC9C0 \uC54A\uC2B5\uB2C8\uB2E4.</td></tr>":d};document.querySelectorAll(".atm_bank_detCbAll").forEach(function(a){a.addEventListener("change",function(a){var b=Array.from(a.target.parentNode.querySelectorAll("input[type=checkbox]"));a.target.checked?b.forEach(function(a){a.checked=!0}):b.forEach(function(a){a.checked=!1})})}),document.querySelectorAll(".trade_type_selector input[type=checkbox]").forEach(function(a){return a.addEventListener("change",function(a){var b=Array.from(a.target.parentNode.querySelectorAll("input[type=checkbox]")),c=a.target.parentNode.querySelector(".atm_bank_detCbAll"),d=b.filter(function(a){return!a.classList.contains("atm_bank_detCbAll")}).reduce(function(a,b){return a&=b.checked},!0);a.target.checked?d&&(c.checked=!0):c.checked=!1})});