naver.kin.mobile.Report=eg.Class({_$element:null,_oOption:null,_bIsReportProcessing:false,_nReportReasonNo:0,
_nReportReasonTextLimit:300,_MSG:{REPORT_REASON_DEFAULT_TEXT:"\uc2e0\uace0\uc0ac\uc720 \uc124\uba85\uc774 \ucd94\uac00\ub85c \ud544\uc694\ud558\uc2e4 \uacbd\uc6b0\uc5d0\ub9cc \uc791\uc131\ud574 \uc8fc\uc138\uc694. (300\uc790 \uc774\ub0b4)",
PLEASE_SELECT_REASON:"\uc2e0\uace0\uc0ac\uc720\ub97c \uc120\ud0dd\ud558\uc138\uc694.",REASON_TEXT_OVER_LIMIT:"\uc0ac\uc720\ub294 300\uc790 \uc774\ub0b4\ub85c \uc791\uc131\ud574 \uc8fc\uc138\uc694.",
REPORT_SUCCESS_MSG:"\uc2e0\uace0\uac00 \uc815\uc0c1\uc801\uc73c\ub85c \ub4f1\ub85d\ub418\uc5c8\uc2b5\ub2c8\ub2e4. \uac80\ud1a0 \ud6c4 \uc2e0\uc18d\ud788 \ucc98\ub9ac\ud558\uaca0\uc2b5\ub2c8\ub2e4. \uac10\uc0ac\ud569\ub2c8\ub2e4.",
REPORT_FAIL_MSG:"\uc2e0\uace0\uc5d0 \uc2e4\ud328\ud558\uc600\uc2b5\ub2c8\ub2e4. \uc7a0\uc2dc \ud6c4 \ub2e4\uc2dc \uc2dc\ub3c4\ud574\uc8fc\uc138\uc694."},
construct:function(htOption){this._$element={};this._oOption=htOption;this._setElement();this._setEvent();
this._setReportReasonNo();$(window).on("unload",this.destroy.bind(this))},
_setElement:function(){this._$element["ct"]=$("#ct");this._$element["textarea"]=$.getSingle("div._txt_area",
this._$element["ct"]);this._$element["reportReasonText"]=$.getSingle("textarea._reportReasonText",this._$element["ct"]);
this._$element["reportReasonNo"]=$("input._reportReasonNo",this._$element["ct"]);this._$element["submit"]=
$.getSingle("a._submit",this._$element["ct"]);this._$element["reportReasonMore"]=$("a._more_btn",this._$element["ct"]);
this._$element["reportReasonMoreInfo"]=$("ul._more_info",this._$element["ct"])},
_setEvent:function(){this._$element["reportReasonNo"].on({"click":this._toggleRadio.bind(this)});this._$element["reportReasonText"].on({"click":this._onDefaultValue.bind(this)});
this._$element["reportReasonMore"].on({"click":this._onClickMore.bind(this)});this._$element["submit"].on({"click":this._submit.bind(this)})},
_setReportReasonNo:function(){if(!this._$element["reportReasonNo"].existElements())return;for(var i=0,
len=this._$element["reportReasonNo"].length;i<len;i++)if(this._$element["reportReasonNo"][i].checked){this._nReportReasonNo=
this._$element["reportReasonNo"][i].value;return}},
_submit:function(oEvent){if(this._bIsReportProcessing)return;if(!this._nReportReasonNo)alert(this._MSG.PLEASE_SELECT_REASON);
else if(this._$element["reportReasonText"].val().length>this._nReportReasonTextLimit){alert(this._MSG.REASON_TEXT_OVER_LIMIT);
this._$element["reportReasonText"].focus()}else{if(this._$element["reportReasonText"].val()==this._MSG.REPORT_REASON_DEFAULT_TEXT)this._$element["reportReasonText"].val("");
this._registerReportAjax()}oEvent.preventDefault();oEvent.stopPropagation()},
_registerReportAjax:function(){var oParam={type:this._oOption.type,svc:this._oOption.svc,dirId:this._oOption.dirId,
docId:this._oOption.docId,answerNo:this._oOption.answerNo,reportReasonNo:this._nReportReasonNo,reportReasonText:this._$element["reportReasonText"].val(),
mdu:this._oOption.mdu,ts:(new Date).getTime()};if(this._oOption.commentNo)oParam["commentNo"]=this._oOption.commentNo;
var oOption={url:"/mobile/qna/registerReportAjax.nhn",method:"post",data:oParam,dataType:"json",timeout:5E3,
beforeSend:function(){this._bIsReportProcessing=true;naver.kin.mobile.common.showLoadingIndicator()}.bind(this),
complete:function(){this._bIsReportProcessing=false;naver.kin.mobile.common.hideLoadingIndicator()}.bind(this),
success:function(oResponse){if(oResponse)if(oResponse.isSuccess)alert(this._MSG.REPORT_SUCCESS_MSG);else if(oResponse.errorMsg)alert(oResponse.errorMsg);
else alert(this._MSG.REPORT_FAIL_MSG);else alert(this._MSG.REPORT_FAIL_MSG);history.back()}.bind(this),
error:function(jqXHR,textStatus,errorThrown){if(textStatus==="timeout")alert(naver.kin.mobile.common.oMessage.TIMEOUT);
else alert(naver.kin.mobile.common.oMessage.ERROR)}.bind(this)};
$.ajax(oOption)},
_onDefaultValue:function(oEvent){var $el=$(oEvent.currentTarget);if(this._$element["reportReasonText"].val()==
this._MSG.REPORT_REASON_DEFAULT_TEXT)this._$element["reportReasonText"].val("").addClass("on")},
_toggleRadio:function(oEvent){var $el=$(oEvent.currentTarget);if(/_openTextArea/.test($el.attr("class"))){this._$element["textarea"].show();
this._$element["reportReasonText"].val(this._MSG.REPORT_REASON_DEFAULT_TEXT).removeClass("on")}else{this._$element["textarea"].hide();
this._$element["reportReasonText"].val("")}this._nReportReasonNo=$el.attr("value");if(!this._$element["reportReasonNo"].existElements())return;
var nLengthOfReportReasonNo=this._$element["reportReasonNo"].length;for(var i=0;i<nLengthOfReportReasonNo;i++){var $elReason=
this._$element["reportReasonNo"].eq(i);if(!$elReason)continue;$elReason.prop("checked",false)}$el.prop("checked",
true)},
_onClickMore:function(oEvent){var $el=$(oEvent.currentTarget);var elMoreText=$el.parent().next();if(!$el)return;
$el.toggleClass("on");if($el.hasClass("on")){$el.html("\ub2eb\uae30\x3ci class\x3d'ico'\x3e\x3c/i\x3e");
elMoreText.show()}else{$el.html("\ub354\ubcf4\uae30\x3ci class\x3d'ico'\x3e\x3c/i\x3e");elMoreText.hide()}},
destroy:function(){if(this._$element){if(this._$element["reportReasonNo"].existElements())this._$element["reportReasonNo"].off("click");
if(this._$element["reportReasonText"].existElements())this._$element["reportReasonText"].off("click");
if(this._$element["reportReasonMore"].existElements())this._$element["reportReasonMore"].off("click");
if(this._$element["submit"].existElements())this._$element["submit"].off("click")}this._$element=this._oOption=
this._bIsReportProcessing=this._nReportReasonNo=this._nReportReasonTextLimit=null;this._MSG=null}});
