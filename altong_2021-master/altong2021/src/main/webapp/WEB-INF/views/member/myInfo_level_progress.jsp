<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<style>
    .bold {
        font-weight: bold;
    }

    .active-stamp {
        background-color: #FF5001;
    }

    .deactive-stamp {
        background-color: gray;
    }

    #level-progress {
        background-color: white;
        padding: 15px;
        border: 1px solid #D6D6D6;
        border-radius: 5px;
        max-width: 500px;
        margin: auto;
        margin-bottom: 12px;
    }

    .condition-lack {
        width: 50%;
        margin-bottom: 3px;
    }

    .fill-condition {
        opacity: 0.4;
    }

    .no-padding {
        padding: 0px;
    }
</style>
<div v-if="nextLevel !== undefined" id="level-progress" style="display:none">
    <div class="row">
        <div id="progress-bar-wrapper" class="col-xs-12" style="margin-bottom: 10px;">
            <div class="progress" style="margin-bottom: 5px;">
                <div class="progress-bar" role="progressbar" style="min-width: 2em; background-color: rgb(255, 80, 1); width: 16.9%;font-weight: bold;font-size: 14px;"
                    v-bind:style="progressStyle">
                    {{currentPersentage}}%
                </div>
            </div>
            <span class="pull-left bold" id="current-level">{{currentLevel}}</span>
            <span class="pull-right bold" id="next-level" style="text-align: center;">
                {{nextLevel}}
                <p v-if="stampLimit > 1">({{stampCnt + 1}}/{{stampLimit}})</span>
            </span>
        </div>
        <div id="level-up-info-wrapper" class="col-xs-12" style="text-align: center;">
            <span v-if="currentLevel !== '알천사'" style="color: gray;font-size: 1em;">({{currentLevel}} 승천일 : {{levelUpDateReg}})</span>
            <p class="bold" style="margin: 0;margin-top:5px;font-size: 1.1em;">
                {{nextLevel}} 승천을 위해 앞으로 필요한 조건
                <span class="fas fa-question-circle" style="color: orange;" @click="onQuestionCircleClick" />
            </p>
        </div>
        <div id="level-up-status-wrapper" class="col-xs-12" style="padding: 5px; text-align: center;">
            <div id="stamp-wrapper" v-if="stampLimit > 1" class="col-xs-12" style="margin-bottom: 10px;">
                <span class="bold">충족 개월 수:</span>
                <span v-for="n in stampLimit" class="label" v-bind:class="{'active-stamp': (n <= stampCnt), 'deactive-stamp': (n > stampCnt)}"
                    style="margin: 3px;">{{n}}</span>
            </div>
            <div class="col-xs-12 no-padding" style="font-size: 0.9em;">
                <div v-for="(value, key, index) in leftCount" class="col-xs-6" :class="{'fill-condition': (value <= 0)}">
                    <span class="col-xs-8 no-padding" style="text-align: left;">
                        <i class="fas fa-gem" style="color: gold;"></i>
                        {{key}}
                    </span>
                    <span v-if="key !== '프로필 사진'" class="col-xs-3 no-padding" style="font-weight: bold; color: #f5011b;">
                        {{value}}
                    </span>
                    <span v-if="key !== '프로필 사진'" class="col-xs-1 no-padding">
                        {{unit[key]||'건'}}
                    </span>
                    <span v-else class="col-xs-3 no-padding">
                        {{unit[key]||'건'}}
                    </span>
                </div>
            </div>
        </div>
    </div>
    <modal v-if="showLevelCondition" @close="showLevelCondition = false">
        <h3 slot="header">
            {{nextLevel}}로의 승천 기준
        </h3>
        <div slot="body">
            <ul class="list-group" style="text-align: left;">
                <li v-for="(iter, key) in levelUpCondition" class="list-group-item">
                    <i class="fas fa-gem" style="color: #42b983;"></i>
                    {{key === '승천 후' ? currentLevel + ' ' : ''}}{{key}} {{key !== '프로필 사진' ? iter : ''}} {{unit[key]||'건'}}
                    <span class="badge"></span>
                </li>
            </ul>
        </div>
        <div slot="footer">
            <button type="button" @click="showLevelCondition = false" class="btn btn-default">확인</button>
        </div>
    </modal>
</div>
<script>
    "use strict";

    new Vue({
        el: '#level-progress',
        data: {
            stampCnt: 0,
            stampLimit: 0,
            currentPersentage: 0,
            currentLevel: '',
            nextLevel: '',
            levelUpDateReg: '',
            leftCount: {},
            levelUpCondition: {},
            unit: {
                '네티즌 평가': '점',
                '네티즌 평가점수': '점',
                '누적 추천인': '명',
                '가입 후': '개월 경과',
                '승천 후': '개월',
                '보유 알머니': '알',
                '프로필 사진': '등록',
                '남은 날짜': '일'
            },
            showLevelCondition: false
        },
        computed: {
            progressStyle: function () {
                return {
                    width: this.currentPersentage + '%'
                };
            }
        },
        methods: {
            onQuestionCircleClick: function (event) {
                this.showLevelCondition = !this.showLevelCondition;
            }
        },
        created: function () {
            var _this = this;

            axios.get('/member/getLvProgressInfo').then(function (res) {
                _this.stampCnt = res.data[0].StampCnt;
                _this.stampLimit = res.data[0].StampLimit;
                _this.currentPersentage = res.data[0].Persentage;
                _this.currentLevel = res.data[0].currentLevel;
                _this.nextLevel = res.data[0].nextLevel;
                _this.levelUpDateReg = res.data[0].levelUpDateReg;

                _this.leftCount = res.data[1];

                _this.levelUpCondition = res.data[2];

				setTimeout(function(){
					$('#level-progress').slideDown(1000);
				}, 1);
            });
        }
    });
</script>
</body>
</html>