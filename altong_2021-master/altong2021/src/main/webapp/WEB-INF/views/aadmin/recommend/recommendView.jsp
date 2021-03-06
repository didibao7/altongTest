<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/Common/include/headerAdmin.jsp" %>
<body>
        <style type="text/css">
            #nprogress .bar {
                background: red !important;
            }

            #nprogress .peg {
                box-shadow: 0 0 10px red, 0 0 5px red !important;
            }

            #nprogress .spinner-icon {
                border-top-color: red !important;
                border-left-color: red !important;
            }
        </style>

        <style>
            .border {
                border: 1px solid lightslategray;
                min-height: 100px;
            }

            .main-wrapper {
                width: 90%;
                margin: auto;
                margin-top: 15px;
                padding: 10px;
                min-height: 600px;
            }

            .status-and-setting {
                display: -ms-grid;
                display: grid;
                -ms-grid-columns: 3fr 7fr;
                grid-template-columns: 3fr 7fr;
                grid-gap: 10px;
            }

            .title {
                background-color: lightslategray;
                color: white;
                padding: 5px;
                font-size: 1.2em;
                font-weight: bold;
            }

            .recommend-status {
                display: block;
                width: 100%;
                -ms-grid-column: 1;
                -ms-grid-column-span: 1;
                grid-column: 1/2;
            }

            div.recommend-status>div.data {
                display: -ms-grid;
                display: grid;
                -ms-grid-columns: 4fr 6fr;
                grid-template-columns: 4fr 6fr;

                padding-top: 25px;
                padding-bottom: 25px;
            }

            div.recommend-status>div.data>div:nth-child(1) {
                -ms-grid-column: 1;
                -ms-grid-column-span: 1;
                grid-column: 1/2;
            }

            div.recommend-status>div.data>div:nth-child(2) {
                -ms-grid-column: 2;
                -ms-grid-column-span: 1;
                grid-column: 2/3;
            }

            div.recommend-status>div.data>div>p {
                display: inline;
                color: dodgerblue;
                font-size: 1.4em;
                font-weight: bold;
            }

            div.recommend-status>div.data>div>span.unit {
                display: inline;
                color: gray;
                font-size: 1.2em;
                font-weight: bold;
            }

            div.recommend-status>div.data>div>span.name {
                display: block;
                font-size: 1.2em;
                font-weight: bold;
            }

            div.search-condition {
                -ms-grid-column: 2;
                -ms-grid-column-span: 1;
                grid-column: 2/3;

                padding: 10px;
                min-width: 100%;
                min-height: 100%;
                /*display: grid;
grid-template-columns: repeat(8, 1fr);
grid-template-rows: repeat(5, 1fr);*/
            }

            div.search-condition>div.row {
                margin-top: 5px;
                margin-bottom: 5px;
            }

            div.search-condition>table {
                min-width: 100%;
                min-height: 100%;
            }

            div.search-condition>table>tbody>tr>th,
            div.search-condition>table>tbody>tr>td {
                padding: 5px;
            }

            div.search-result {
                margin-top: 15px;
            }

            div.search-result>div.title>div {
                display: inline;
                padding: 2px;
                margin: 2px;
            }

            div.search-result>div.title>div.on {
                border-bottom: 3px solid white;
            }

            div.tree-table>table {
                width: 100%;
            }

            li.member-info:hover {
                /*background-color: DarkGreen;*/
            }

            div.tree-control {
                margin-top: 10px;
            }

            i.blue-dot-box {
                width: 12px;
                height: 12px;
                background-color: dodgerblue;
                display: inline-block;
                margin-left: 5px;
                margin-right: 5px;
            }

            .ghost-member {
                background-color: lightslategray;
                color: white;
            }

            .alba-member {
                background-color: #f0ad4e;
                color: white;
            }
        </style>

        <div id="wrapper">
            <div id="header_wrapper">
            </div>
            <div id="M_wrapper">
                <%@ include file="/Common/include/menuAdmin.jsp" %>
            </div>
            <div class="main-wrapper border">
                <div class="status-and-setting">
                    <div class="recommend-status border">
                        <div class="title">
                            ??????
                        </div>
                        <div class="data">
                            <div align="center" style="border-right: 1px solid lightgray;">
                                <p>
                                    <!-- TO-DO: ASP ?????? -->
                                    <fmt:formatNumber value="${memberCnt}" pattern="#,###" />
                                </p>
                                <span class="unit">???</span>
                                <span class="name">????????????</span>
                            </div>
                            <div align="center" style="border-left: 1px solid lightgray;">
                                <p>
                                    <!-- TO-DO: ASP ?????? -->
                                    <fmt:formatNumber value="${totalAlmoney}" pattern="#,###" />
                                </p>
                                <span class="unit">???</span>
                                <span class="name">?????? ????????????</span>
                            </div>
                        </div>
                    </div>
                    <div class="search-condition border">
                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                <i class="blue-dot-box"></i>?????? ??????
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
                                <select v-model="searchType" name="" id="inputTp" class="form-control" required="required">
                                    <option value="sName">??????</option>
                                    <option value="NickName">?????????</option>
                                    <option value="Phone">?????????</option>
                                    <option value="Seq">????????????</option>
                                </select>
                            </div>
                            <div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
                                <input v-model="searchTarget" @keyup="onSearchInputKeyUp" type="text" name="searchTarget" id="inputKey" class="form-control"
                                    value="" required="required" pattern="" title="">
                            </div>
                            <div class="col-xs-1 col-sm-1 col-md-1 col-lg-1">
                                <button type="button" class="btn btn-warning" @click="onSearchButtonClick">??????</button>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                <i class="blue-dot-box"></i>??????
                            </div>
                        </div>


                        <div class="row">
                            <div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
                                <label for="select1">?????????</label>
                                <select v-model="order.childCnt" name="select1" id="inputRecom" class="form-control" required="required">
                                    <option value="">???????????????</option>
                                    <option value="asc">?????????</option>
                                    <option value="desc">?????????</option>
                                </select>
                            </div>
                            <div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
                                <label for="select1">??????</label>
                                <select v-model="order.earning" name="select1" id="inputComm" class="form-control" required="required">
                                    <option value="">?????? ?????????</option>
                                    <option value="asc">?????? ???</option>
                                    <option value="desc">?????? ???</option>
                                </select>
                            </div>
                            <div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
                                <label for="date1">?????? ?????? ??????</label>
                                <input v-model="startDate" type="date" name="date1" id="inputSdt" class="form-control" value="" required="required"
                                    title="">
                            </div>
                            <div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
                                <label for="date2">?????? ?????? ??????</label>
                                <input v-model="endDate" type="date" name="date2" id="inputEdt" class="form-control" value="" required="required"
                                    title="">
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
                                <label for="select1">??????</label>
                                <select v-model="userLv" name="select1" id="inputLv" class="form-control" required="required">
                                    <option value="1">${codeMemLvNm.get("1")}</option>
                                    <option value="2">${codeMemLvNm.get("2")}</option>
                                    <option value="3">${codeMemLvNm.get("3")}</option>
                                    <option value="4">${codeMemLvNm.get("4")}</option>
                                    <option value="5">${codeMemLvNm.get("5")}</option>
                                    <option value="6">${codeMemLvNm.get("6")}</option>
                                    <option value="7">${codeMemLvNm.get("7")}</option>
                                    <option value="8">${codeMemLvNm.get("8")}</option>
                                    <option value="9">${codeMemLvNm.get("9")}</option>
                                    <option value="10">${codeMemLvNm.get("10")}</option>
                                    <option value="11">${codeMemLvNm.get("11")}</option>
                                    <!-- TO-DO: ASP ?????? -->
                                </select>
                            </div>
                            <div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
                                <label for="select1">??????</label>
                                <select name="select1" id="inputGrp" class="form-control" required="required">
                                    <option value="">???????????????</option>
                                    <option value="0">??????</option>
                                    <option value="1">1??????</option>
                                    <!-- TO-DO: ASP ?????? -->
                                </select>
                            </div>
                            <div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
                                <label for="date1">?????????</label>
                                <select v-model="order.joinDate" name="select1" id="inputJoinDt" class="form-control" required="required">
                                    <option value="">???????????????</option>
                                    <option value="desc">?????? ???</option>
                                    <option value="asc">????????? ???</option>
                                </select>
                            </div>
                            <div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
                                <button type="button" class="btn btn-warning" style="margin: auto; width: 100%; margin-top: 9%;">??????</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="tree-control">
                    <div class="title">?????? ??????</div>

                    <div class="row" style="padding: 10px;">

                        <div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
                            <select v-model="action" name="" id="inputLocation" class="form-control" required="required">
                                <option value="">???????????????.</option>
                                <option value="switch2nodeWithChild">?????? ????????????(????????? ??????)</option>
                                <option value="switch2nodeWithOutChild">?????? ????????????(????????? X)</option>
                                <option value="changeNode">1??? ????????? 2 INS</option>
                                <option value="append">1 ???????????? 2 ??????</option>
                                <!--<option value="">3</option>-->
                            </select>
                        </div>

                        <div class="col-xs-9 col-sm-9 col-md-9 col-lg-9 row">
                            <div class="col-xs-1 col-sm-1 col-md-1 col-lg-1">
                                ??????1
                            </div>
                            <div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">
                                <input v-model="target1" type="text" class="form-control" aria-label="...">
                            </div>

                            <div class="col-xs-1 col-sm-1 col-md-1 col-lg-1">
                                ??????2
                            </div>
                            <div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">
                                <input v-model="target2" type="text" class="form-control" aria-label="...">
                            </div>
                        </div>

                        <div class="col-xs-1 col-sm-1 col-md-1 col-lg-1">
                            <button @click="onTreeControlBtnClick" type="button" class="btn btn-danger">??????</button>
                        </div>

                    </div>

                </div>
                <div class="tree-back-up">
                    <div class="title">?????? ?????? & ??????</div>
                    <div class="row" style="padding: 10px;">
                        <div class="col-md-12 col-lg-12" style="margin-top:15px; margin-bottom:15px;">
                            <button @click="onTreeBackUpBtnClick" type="button" class="btn btn-success">?????? ?????? ??????</button>
                        </div>

                        <div class="col-xs-8 col-sm-8 col-md-10 col-lg-10">
                            <select v-model="targetBackUp" class="form-control">
                                <option value="" readonly>???????????????</option>
                                <option v-for="backUp in backUpList" :value="backUp.TableName">????????????:{{backUp.DateReg}}&nbsp;&nbsp;&nbsp;??????&nbsp;:&nbsp;{{backUp.Memo}}</option>
                            </select>
                        </div>
                        <div class="col-xs-4 col-sm-4 col-md-2 col-lg-2">
                            <button @click="onRestoreBtnClick" type="button" class="btn btn-default">????????????</button>
                        </div>


                    </div>
                </div>
                <div class="search-result">
                    <div class="title">
                        <!--<div :class="{ 'on': !toggle}" @click="setToggle(false)">?????????</div>-->
                        <div :class="{ 'on': toggle}" @click="setToggle(true)">????????????</div>

                        <button type="button" @click="showAllTree" class="btn btn-success">????????????</button>

                    </div>
                    <div v-if="!toggle" class="list-table"></div>
                    <div v-else class="tree-table">
                        <div class="row">
                            <div class="col-xs-1 col-sm-1 col-md-1 col-lg-1">
                                ????????????
                            </div>
                            <div class="col-xs-1 col-sm-1 col-md-1 col-lg-1">
                                ?????????
                            </div>
                            <div class="col-xs-1 col-sm-1 col-md-1 col-lg-1">
                                ?????????
                            </div>
                            <div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
                                ??????
                            </div>
                            <div class="col-lg-1 hidden-xs hidden-sm hidden-md">
                                ????????????
                            </div>
                            <div class="col-xs-3 col-sm-3 col-md-3 col-lg-2">
                                ?????????
                            </div>
                            <div class="col-xs-1 col-sm-1 col-md-1 col-lg-1">
                                ANSWERer
                            </div>
                            <div class="col-xs-1 col-sm-1 col-md-1 col-lg-1">
                                ?????????(???)
                            </div>
                        </div>
                        <ul v-if="resultList.length > 0" class="list-group">
                            <tree-table-row :key="member.Seq" v-for="(member, index) in resultList" :member="member" :gen="0"></tree-table-row>
                        </ul>
                        <div v-else>
                            ?????? ????????? ????????????.
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <template id="tree-table-row-template">
            <div>
                <li v-if="member.FlagDel !== 'Y'" class="list-group-item member-info" v-bind:class="{'ghost-member': member.MemberType == 99, 'alba-member': member.MemberType == 1}">
                    <div class="row">
                        <div @click="onMemberSeqClick" class="col-xs-1">
                            {{member.Seq}}
                        </div>
                        <div class="col-xs-1 col-sm-1 col-md-1 col-lg-1">
                            {{member.Phone}}
                        </div>
                        <div class="col-xs-1 col-sm-1 col-md-1 col-lg-1">
                            {{member.NickName}}
                        </div>
                        <div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
                            <span v-for="i in gen">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                            <i v-if="member.ChildCnt > 0" :class="{'fas fa-plus': !member.toggleChildren, 'fas fa-minus': member.toggleChildren}"
                                @click="reverseChildrenToggle(member)"></i>
                            <span v-else>&nbsp;&nbsp;&nbsp;</span>
                            {{gen}}.&nbsp;{{member.Name}}
                            <span v-if="isAbleToShowParent" @click="onShowParentClick">
                                [?????? ??????]
                            </span>
                        </div>
                        <div class="col-lg-1 hidden-xs hidden-sm hidden-md">
                            {{member.RealParentSeq}}
                        </div>
                        <div class="col-xs-3 col-sm-3 col-md-3 col-lg-2">
                            {{member.DateReg}}
                        </div>
                        <div class="col-xs-1 col-sm-1 col-md-1 col-lg-1">
                            {{member.ChildCnt}}
                        </div>
                        <div class="col-xs-1 col-sm-1 col-md-1 col-lg-1">
                            {{member.Earning}}
                        </div>
                    </div>
                </li>
                <li v-else class="list-group-item" style="background-color: mistyrose;">
                    ????????? ??????
                </li>
                <tree-table-row :key="child.Seq" v-if="member.toggleChildren" v-for="child in children" :member="child" :gen="gen + 1"></tree-table-row>
            </div>
        </template>
        <script>
            loadProgressBar();
        </script>
        <script src="/Common/aadmin/Recommend/RecommendView.js"></script>
</body>