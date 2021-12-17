<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/Common/include/headerAdmin.jsp" %>
<body>
<style>
    .border {
        border: 2px solid dodgerblue;
    }

    .action-btn-group>.btn {
        margin: 3px !important;
    }

    .admit-request {
        background-color: lightgray !important;
    }

    .admit-request:hover {
        background-color: darkgray !important;
    }

    .reject-request {
        background-color: lightcoral !important;
        color: white !important;
    }

    .postpone-request {
        background-color: lightgoldenrodyellow !important;
    }
</style>

    <div id="wrapper">
        <div id="M_wrapper">
            <%@ include file="/Common/include/menuAdmin.jsp" %>
			
                <div style="margin-left:10px;font-size:18px">
                <%if(CommonUtil.libhasAuthority(alpayLogCheck, userSeq)) {%>
                    <a href="/aadmin/exchange/exchReadyList">
                <%} else if(CommonUtil.libhasAuthority(alpayLogCheck, userSeq)) {%>
                    <a href="/aadmin/exchange/alpayLogCheck">
                <%} else if(CommonUtil.libhasAuthority(alpayExchList, userSeq)) {%>
                    <a href="/aadmin/exchange/alpayExchList">
                <%}%>
                [신규 출금 시스템]</a><br><br></div>
                
        </div>

		<div class="container-fluid">
            <table id="exchange-list-table" class="table table-hover">
                <thead>
                    <tr>
                        <th>Idx</th>
                        <th>회원 고유번호</th>
                        <th>회원 이름</th>
                        <th>닉네임</th>
                        <th>레벨</th>
                        <th>승천일자</th>
                        <th>은행</th>
                        <th>계좌번호</th>
                        <th>예금주</th>
                        <th>보유 알머니</th>
                        <th>출금 신청알</th>
                        <th>실수령액</th>
                        <th>신청 시간</th>
                        <th>인증 전화번호</th>
                        <th>신분증</th>
                        <th>상태</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="iter in exchangeList" :key="iter.ExchangeSeq" v-bind:class="{ 'success': (iter.ExchagneStatus === '출금완료'), 
                                                                                             'danger': (iter.ExchagneStatus === '출금취소'), 
                                                                                             'warning': (iter.ExchagneStatus === '출금보류') }">
                        <td>{{iter.Idx}}</td>
                        <td>
                            <a :href="'/aadmin/memberView?Seq=' + iter.Seq" target="_blank">{{iter.Seq}}</a>
                        </td>
                        <td>{{iter.Name}}</td>
                        <td>{{iter.NickName}}</td>
                        <td>{{iter.Lv}}</td>
                        <td>{{iter.DateLvUp}}</td>
                        <td>{{iter.BankName}}</td>
                        <td>{{iter.BankAccountNo}}</td>
                        <td>{{iter.BankMemNm}}</td>
                        <td>{{iter.Almoney}}</td>
                        <td>{{iter.ExchangeAlmoney}}</td>
                        <td>{{iter.RealMoney}}</td>
                        <td>{{iter.DateExchange}}</td>
                        <td>+{{iter.Country}} {{iter.Phone}}</td>
                        <td>
                            <button @click="onIdDocBtnClick(iter)" type="button" class="btn btn-default">확인</button>
                        </td>
                        <td>
                            {{iter.ExchagneStatus}}
                            <span v-if="iter.buttonFlag" class="input-group-btn action-btn-group">
                                <button @click="onActionBtnClick(iter, 'admit')" type="button" class="btn btn-success">승인</button>
                                <button @click="onActionBtnClick(iter, 'reject')" type="button" class="btn btn-danger">거부</button>
                                <button @click="onActionBtnClick(iter, 'postpone')" type="button" class="btn btn-warning">보류</button>
                            </span>
                            <span v-else>
                                <p>처리일자 : {{iter.DateResult}}</p>
                            </span>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>

<script>
    "use strict";

    var ExchangeListVue = new Vue({
        el: "#exchange-list-table",
        data: {
            exchangeList: []
        },
        methods: {
            getExchangeList: function () {
                var _this = this;

                axios.get("/aadmin/exchange/process/getExchangeList").then(function (res) {
                    _this.exchangeList = res.data;
                });
            },
            onIdDocBtnClick: function (item) {
                window.open("/aadmin/exchange/idCardView?MemberSeq=" + item.Seq);
            },
            onActionBtnClick: function (target, action) {
                var _this = this;
                var check = confirm('처리하시겠습니까?');
				console.log('action : ' + action);
                if (check) {
                    axios.post("/aadmin/exchange/process/changeExchangeAsk", {
                        ExchangeSeq: target.ExchangeSeq,
                        Action: action
                    }).then(function (res) {
						console.log(res);
						var r = res.data.split('***');
						switch (r[1])
						{
							case 'OK':
								_this.getExchangeList();
								break;
							case 'LACK':
								alert('회원님의 알머니가 부족하여 승인할 수 없습니다');
								break;
							case 'NOT_EXIST':
								alert('출금 신청 자료가 없습니다.');
								break;
						}
                    });
                }
            }
        },
        created: function () {
            this.getExchangeList();
        }
    });

</script>
</body>