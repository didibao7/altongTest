<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<body>
<%@ include file="/Common/include/vueModal.jsp" %>
<style>
    .table-cell-head {
        width: 25%;
        background-color: #42b983;
        color: white;
    }

    .table-cell-contents {
        width: 25%;
    }

    .report-info {
        padding: 15px;
    }

    .report-info>* {
        border: 1px solid #ddd;
        border-collapse: collapse;
        padding-top: 1rem;
        padding-bottom: 1rem;
    }

    .report-info-table>tbody>tr>td {
        width: 40%;
    }

    .header {
        background-color: #42b983;
        color: white;
        width: 10% !important;
        text-align: center;
    }
</style>
<div id="detail-modal">
    <modal v-if="showModal" @close="showModal = false" width="90%">
        <h3 slot="header">
            <span style="font-size: 1.1em;">{{reporter}}</span>님의 신고내용입니다.
        </h3>
        <div slot="body">
            <table class="table table-bordered report-info-table">
                <tbody>
                    <tr>
                        <td class="header">신고사유</td>
                        <td>{{reason}}</td>
                        <td class="header">코멘트</td>
                        <td>{{comment || '코멘트가 없습니다.'}}</td>
                    </tr>
                    <tr>
                        <td class="header">게시물 유형</td>
                        <td>{{content.typeName}}</td>
                        <td class="header">게시물 작성자</td>
                        <td>{{content.memberNickName}}</td>
                    </tr>
                    <tr>
                        <td class="header">게시물 제목</td>
                        <td colspan="3">{{content.title}}</td>
                    </tr>
                    <tr>
                        <td class="header">게시물 내용</td>
                        <td colspan="3">{{content.text}}</td>
                    </tr>
                </tbody>
            </table>
            <h3>관리자 처리 현황</h3>
            <table class="table table-bordered report-info-table">
                <tbody>
                    <tr>
                        <td class="header">진행 상태</td>
                        <td>{{adminStatus}}</td>
                        <td v-if="resultDate" class="header">처리 시간</td>
                        <td v-if="resultDate">{{resultDate}}</td>
                    </tr>
                    <tr>
                        <td class="header">관리자 코멘트</td>
                        <td colspan="3">
                            <div v-if="editComment" style="padding: 5px;">
                                <textarea v-model="adminCommentWriting" rows="3" style="width: 100%; resize: none;"></textarea>
                                <button type="button" @click="onAdminCommentSave" class="btn btn-success">저장</button>
                                <button type="button" @click="editComment = false" class="btn btn-default">취소</button>
                            </div>
                            <p v-else>
                                {{adminComment}}
                                <span style="float: right;margin-right: 2rem;" @click="onAdminCommentEditClick">
                                    <i class="fas fa-edit"></i>
                                </span>
                            </p>
                        </td>
                    </tr>
                </tbody>
            </table>
            <div>
                <button v-if="adminStatus !== this.$store.code.adminStatus.DELETE" type="button" @click="onChargeClick('DELETE')" class="btn btn-danger">게시물 삭제</button>
                <button type="button" @click="onChargeClick('HOLD')" class="btn btn-warning">보류</button>
                <button type="button" @click="onChargeClick('ETC')" class="btn btn-disabled">기타</button>
            </div>
        </div>
        <div slot="footer">
            <button type="button" @click="showModal = false" class="btn btn-default">닫기</button>
        </div>
    </modal>
</div>
<script src="/Common/aadmin/report/dist/detail_modal.js"></script>
</body>