"use strict";window.onload=function(){var a=document.getElementById("almoney-chart").getContext("2d");axios.get("/member/bank/indexAjax?act=GRAPH").then(function(b){var c=b.data,d=c.result.earning;console.log(d);for(var f=1;6>=f;f++)d.hasOwnProperty(f)||(d[f]=0);var e={labels:Object.keys(d).map(function(a){return"".concat(a, getLangStr("jsm_0128"))}).reverse(),datasets:[{label:"",backgroundColor:"#228be6",borderColor:"#228be6",borderWidth:1,data:Object.values(d).reverse(),fill:!1}]};renderAlmoneyChart(a,e)}).catch(function(a){console.error(a)})};var renderAlmoneyChart=function(a,b){new Chart(a,{type:"line",data:b,options:{legend:{display:!1},responsive:!0,scales:{xAxes:[{display:!0}],yAxes:[{type:"linear",display:!0,position:"left",scaleLabel:{display:!1,labelString:"\uC218\uC775"}}]}}})};