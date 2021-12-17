var answerJson
var configJson
var logJson
var imgJson

(function() {
	$.ajax({
		url: "/Common/Ajax/Config.asp",
		success:function(data) {
			configJson = $.parseJSON(data)
		}
	})
	$.ajax({
		url: "/Common/Ajax/AnswerLog.asp",
		success:function(data) {
			logJson = $.parseJSON(data)
		}
	})
})()
String.prototype.replaceAll = function(org, dest) {
    return this.split(org).join(dest);
}
function answerSlice(str) {
	var result = str
	var tmp = str
	if(str.length > 70) {
		result = str.slice(0, 70)
	} else if (str.length < 70 && str.length > 10) {
		result = result.slice(0, result.length / 2)
	} else if(tmp.replaceAll(".", "").length < 10){
		result = result.slice(0, 5)
	}
	result += "**********"
	return result
}
function loadTemplate(id) {
	return document.getElementById(id).innerHTML;
}
function replaceTemplate(tmplStr, data) {
	var result = tmplStr;
	for(var key in data) {
		if(key == "PointRank" && data[key] != 1 || answerJson.length <= 1) {
			result = result.replaceAll('<img src="/Common/images/choice_netizenB.png" class="atm_icon_amark_medal">', "")
		}
		if(key == "NickName" && data["FlagNickName"] == "N") {
			result = result.replaceAll("{NickName}", "비공개")
		}
		if(key == "Answer") {
			result = result.replaceAll("{" + key + "}", answerSlice(data[key]))
		}
		if (key == "FlagChoice" && data[key] == "N") {
			result = result.replaceAll('<img src="/Common/images/choice_askerB.png" class="atm_icon_amark_medal">', "")
			//result = result.replaceAll('<img src="/Common/images/choice_netizenB.png" class="atm_icon_amark_medal">', "")
		}
		result = result.replaceAll("{" + key + "}", data[key])
	}
	return result
}

function makeAnswerTemplateElement(data, idx) {
	var tmplStr = loadTemplate("AnswerTemplate");
	var result = replaceTemplate(tmplStr, data);
	result = result.replaceAll("{index}", idx)
	return result;
}

function makeAnswerTemplate(answerList) {
	answerJson = answerList
	for(var i = 0; i < answerList.length; i++) {
		var result = makeAnswerTemplateElement(answerList[i], i)
		$(".atm_av_con").append(result)
	}
}

function checkViewAnswer(answerIdx, userSeq) {
	if(userSeq == answerJson[answerIdx]["MemberSeq"]) return true
	var targetSeq = answerJson[answerIdx]["Seq"]
	for(var i = 0; i < logJson.length; i++) {
		if(logJson[i]["ContentsSeq"] == targetSeq) return true
	}
	return false
}
function loadImg(answerSeq, answerIdx){
	$.ajax({
		url: "/Answer/AnswerView/GetAnswerImg.asp?Seq=" + answerSeq,
		//parmeter: answerJson[answerIdx]["Seq"],
		success:function(data) {
			imgJson = $.parseJSON(data)
			for(var i = 0; i < imgJson.length; i++){
				var imgHtml = loadTemplate("ImagePart");
				imgHtml = replaceTemplate(imgHtml, imgJson[i]);
				$("#" + answerIdx).parent().children(".atm_addpop").before(imgHtml)
			}
		}
	})
}
function changeSomeBtn(answerSeq, answerIdx){
	var btnG2Html = loadTemplate("btnG2Part")
	var parameter = {
		"Seq" : answerSeq
	}
	btnG2Html = replaceTemplate(btnG2Html, parameter)
	$("#" + answerIdx).parent().find(".atm_estimation").before(btnG2Html)
	
	var estimationPartHtml = loadTemplate("estimationPart")
	//estimationPart = replaceTemplate(estimationPart, parameter)
	estimationPartHtml = estimationPartHtml.replaceAll("{Seq}", answerSeq)
	$("#" + answerIdx).parent().find(".atm_estimation").html(estimationPartHtml)
}
function viewFullAnswer(answerIdx, userSeq) {
	var target = "#" + answerIdx
	$(target).html(answerJson[answerIdx]["Answer"]).children(".atm_viewmore1").hide()
	loadImg(answerJson[answerIdx]["Seq"], answerIdx)	
	changeSomeBtn(answerJson[answerIdx]["Seq"], answerIdx)
	return
}
function viewAnswer(answerIdx, Al, userSeq, QuestionMemberSeq) {
	if(checkViewAnswer(answerIdx, userSeq) || userSeq == QuestionMemberSeq) {
		viewFullAnswer(answerIdx, userSeq)
		return
	}
	if(configJson[0]["ViewMoneySum"] <= Al)
		var msg = confirm("답변 열람시 알포인트가\n" + configJson[0]["ViewMoneySum"] + " 알 차감됩니다.\n열람을 원하시면 \n[확인]을 누르시기 바랍니다.\n현재 보유 알 : " + Al.toFixed(1) + "알\n")
	else {
		alert("보유 알이 부족하여 답변을 열람하실 수 없습니다.")
		return
	}
	
	if(msg) {
		var $form = $("<form></form>").attr({id:"form", method:"post"})
	
		var $QuestionMemberSeq = $("<input type='hidden' value='" + QuestionMemberSeq +"' name='QuestionMemberSeq'>")
		var $AnswerMemberSeq = $("<input type='hidden' value='" + answerJson[answerIdx]['MemberSeq'] +"' name='AnswerMemberSeq'>")
		var $AnswerSeq = $("<input type='hidden' value='" + answerJson[answerIdx]['Seq'] +"' name='AnswerSeq'>")
		var $QuestionSeq = $("<input type='hidden' value='" + answerJson[answerIdx]['QuestionSeq'] +"' name='QuestionSeq'>")

		$form.append($QuestionMemberSeq).append($AnswerMemberSeq).append($AnswerSeq).append($QuestionSeq)
		
		var check
		$.ajax({
			url: "/Answer/AnswerProcessTEST.asp",
			type: "post",
			data: $form.serialize(),
			sucess: function(data) {
			},
			error: function(){
				alert("오류가 발생하였습니다. 다시 시도해주세요")
				return false
			}
		})
		viewFullAnswer(answerIdx, userSeq)
	}
}