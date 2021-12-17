var global = {}
global.Json = {
	"config" : null,
	"answer" : null,
	"answerImg" : null,
	"log" : null,
	"question" : null,
	"questionMember" : null
};
global.Var = {
	"UserSeq" : null,
	"UserAlmoney" : null,
	"QuestionSeq" : null,
	"QuestionMemberSeq" : null,
	"Almoney" : null
}
var func
var answer
var question

function loadAjax() {
	$.ajax({
		url : "/Common/Ajax/Config.asp",
		success : function(data) {
			global.Json.config = $.parseJSON(data)
		}
	})
	$.ajax({
		url: "/Common/Ajax/AnswerLog.asp",
		success : function(data) {
			global.Json.log = $.parseJSON(data)
		}
	})
	$.ajax({
		url: "./AnswerGet.asp?Seq=" + global.Var.QuestionSeq,
		success : function(data) {
			global.Json.answer = $.parseJSON(data)
			answer.preview()
		}
	});
	$.ajax({
		url : "/Answer/AnswerView/GetQuestion.asp?Seq=" + global.Var.QuestionSeq,
		success : function(data) {
			global.Json.question = $.parseJSON(data)
		}
	})
	$.ajax({
		url : "/Answer/AnswerView/GetQuestionMemberInfo.asp?Seq=" + global.Var.QuestionMemberSeq,
		success : function(data) {
			global.Json.questionMember = $.parseJSON(data)
		}
	})
}

function viewControllerInit(UserSeq, UserAlmoney, QuestionSeq, QuestionMemberSeq, Almoney) {
	global.Var = {
		"UserSeq" : UserSeq,
		"UserAlmoney" : UserAlmoney,
		"QuestionSeq" : QuestionSeq,
		"QuestionMemberSeq" : QuestionMemberSeq,
		"Almoney" : Almoney
	}
	loadAjax();
	func = {
		"calcAnswerValue" :
			function(targetIdx) {
				var t = global.Json.answer[targetIdx]
				return (t.PointCount1 * 7 + t.PointCount2 * 3 + t.PointCount3 * 1 - t.PointCount4 * 1 - t.PointCount5 * 3)
			},
		"replaceTemplate" : 
			function(target, data) {
				for(var key in data) {
					target = target.replaceAll("{" + key + "}", data[key])
				}
				return target
			},
		"replaceAnswer" :
			function(target, data) {
				for(var key in data) {
					if(key == "PointRank" && data[key] != 1 || global.Json.answer.length <= 1 || func.calcAnswerValue(answer.idx) < 10) {
						target = target.replaceAll('<img src="/Common/images/choice_netizenB.png" class="atm_icon_amark_medal">', "")
					}
					if(key == "NickName" && data["FlagNickName"] == "N") {
						target = target.replaceAll("{NickName}", "비공개")
					}
					if(key == "Answer") {
						target = target.replaceAll("{" + key + "}", answerSlice(data[key]))
					}
					if (key == "FlagChoice" && data[key] == "N") {
						target = target.replaceAll('<img src="/Common/images/choice_askerB.png" class="atm_icon_amark_medal">', "")
						//target = target.replaceAll('<img src="/Common/images/choice_netizenB.png" class="atm_icon_amark_medal">', "")
					}
					target = target.replaceAll("{" + key + "}", data[key])
				}
				target = target.replaceAll("{index}", answer.idx)
				return target
			},
		"loadTemplate" :
			function(id) {
				return $("#" + id).html()
			},
		"makeAnswer" :
			function() {
				var result = func.loadTemplate("AnswerTemplate")
				result = func.replaceAnswer(result, global.Json.answer[answer.idx])
				return result
			}
	}
	answer = {
		"preview" : 
			function() {
				for(var i = 0; i < global.Json.answer.length; i++) {
					answer.idx = i
					var result = func.makeAnswer()
					$(".atm_av_con").append(result)
				}
			},
		"idx" : null,
		"view" : 
			function(answerIdx) {
				answer.idx = answerIdx
				var wantRead = answer.isAbleToRead()
				if(!answer.isAbleToRead()) {
					if(global.Var.UserAlmoney < global.Json.config[0]["ViewMoneySum"]) {alert("보유 알이 부족하여 답변을 열람하실 수 없습니다."); return}
					wantRead = confirm("답변 열람시 알포인트가\n" + global.Json.config[0]["ViewMoneySum"] + " 알 차감됩니다.\n열람을 원하시면 \n[확인]을 누르시기 바랍니다.\n현재 보유 알 : " + global.Var.UserAlmoney.toFixed(1) + "알\n")
					if(wantRead) {
						var $form = $("<form></form>").attr({id:"form", method:"post"})
		
						var $QuestionMemberSeq = $("<input type='hidden' value='" + global.Var.QuestionMemberSeq +"' name='QuestionMemberSeq'>")
						var $AnswerMemberSeq = $("<input type='hidden' value='" + global.Json.answer[answer.idx]['MemberSeq'] +"' name='AnswerMemberSeq'>")
						var $AnswerSeq = $("<input type='hidden' value='" + global.Json.answer[answer.idx]['Seq'] +"' name='AnswerSeq'>")
						var $QuestionSeq = $("<input type='hidden' value='" + global.Json.answer[answer.idx]['QuestionSeq'] +"' name='QuestionSeq'>")

						$form.append($QuestionMemberSeq).append($AnswerMemberSeq).append($AnswerSeq).append($QuestionSeq)

						$.ajax({
							url: "/Answer/AnswerProcessTEST.asp",
							type: "post",
							data: $form.serialize(),
							sucess: function(data) {}
						})
					}
				}
				if(wantRead) {
					//console.log(11)
					var target = "#" + answer.idx
					$.ajax({
						url: "/Answer/AnswerReadCountUp.asp?AnswerSeq=" + global.Json.answer[answer.idx]["Seq"],
						type: "post",
						success: function() {}
					})
					$(target).html(global.Json.answer[answer.idx]["Answer"]).children(".atm_viewmore1").hide()
					answer.viewImg()
					answer.changeBtn()
				}
			},
		"isAbleToRead" :
			function() {
				if(global.Var.UserSeq == global.Json.answer[answer.idx]["MemberSeq"] || global.Var.UserSeq == global.Var.QuestionMemberSeq) return true
				else {
					for(var i = 0; i < global.Json.log.length; i++) {
						if(global.Json.log[i]["ContentsSeq"] == global.Json.answer[answer.idx]["Seq"]) return true
					}
					return false
				}
			},
		"viewImg" :
			function() {
				$.ajax({
					url: "/Answer/AnswerView/GetAnswerImg.asp?Seq=" + global.Json.answer[answer.idx]["Seq"],
					success:function(data) {
						var imgJson = $.parseJSON(data)
						for(var i = 0; i < imgJson.length; i++){
							var imgHtml = func.loadTemplate("ImagePart");
							imgHtml = func.replaceTemplate(imgHtml, imgJson[i]);
							$("#" + answer.idx).parent().children(".atm_addpop").before(imgHtml)
						}
					}
				})
			},
		"isAbleToChoice" :
			function() {
				for(var i = 0; i < global.Json.answer.length; i++) {
					if(global.Json.answer[i]["FlagChoice"] == "Y")
						return false
				}
				return true
			},
		"changeBtn" :
			function() {
				if(global.Var.UserSeq == global.Json.answer[answer.idx]["MemberSeq"]) {
					var btnG2Html = func.loadTemplate("btnG2Part")
					var parameter = {
						"Seq" : global.Json.answer[answer.idx]["Seq"]
					}
					btnG2Html = func.replaceTemplate(btnG2Html, parameter)
					$("#" + answer.idx).parent().find(".atm_estimation").before(btnG2Html)
				}
				
				var estimationPartHtml = func.loadTemplate("estimationPart")
				estimationPartHtml = func.replaceTemplate(estimationPartHtml, parameter)
				estimationPartHtml = func.replaceTemplate(estimationPartHtml, global.Json.answer[answer.idx])
				$("#" + answer.idx).parent().find(".atm_estimation").html(estimationPartHtml)

				if(global.Var.UserSeq == global.Var.QuestionMemberSeq && answer.isAbleToChoice()) {
					var choiceBtnHtml = '<div class="atm_botbtnG"><p class="atm_botbtnG_c4" id="choice_' + answer.idx + '">채택하기</p></div>'
					$("#" + answer.idx).parent().find(".atm_estimation").parent().append(choiceBtnHtml)
					/*
					
					*/
				}
			},
		"choice" : 
			function(target) {
				var $form = $("<form></form>").attr({id:"form", method:"post"})
		
				//var $QuestionMemberSeq = $("<input type='hidden' value='" + global.Var.QuestionMemberSeq +"' name='QuestionMemberSeq'>")
				var $AnswerSeq = $("<input type='hidden' name='AnswerSeq' value='" + global.Json.answer[target]["Seq"] + "'>")
				var $QuestionSeq = $("<input type='hidden' name='QuestionSeq' value='" + global.Var.QuestionSeq + "'>")
				var $AnswerMemberSeq = $("<input type='hidden' name='AnswerMemberSeq' value='" + global.Json.answer[target]["MemberSeq"] +"'>")
				var $Almoney = $("<input type='hidden' name='Almoney' value='" + global.Var.Almoney + "'>")

				$form.append($AnswerSeq).append($QuestionSeq).append($AnswerMemberSeq).append($Almoney)
				
				$.ajax({
					url: "/Answer/AnswerChoiceT.asp",
					type: "post",
					data: $form.serialize(),
					sucess: function(data) {}
				})
			}
	}

	question = {
		
	}
	
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