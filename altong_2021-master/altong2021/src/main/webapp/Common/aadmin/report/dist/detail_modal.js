'use strict';

var detailModal = new Vue({
  el: '#detail-modal',
  data: {
    showModal: false,
    editComment: false,
    seq: 0,
    contentSeq: 0,
    reporter: '',
    reporterSeq: 0,
    reason: '',
    comment: '',
    url: '',
    date: '',
    adminStatus: '',
    adminComment: '',
    charge: '',
    resultDate: '',
    content: {},
    adminCommentWriting: ''
  },
  methods: {
    fetchReportDetail: function fetchReportDetail(reportSeq) {
      var _this = this;

      axios.get('/aadmin/report/process/getReportDetail?Seq=' + reportSeq).then(function (res) {
        _this.seq = res.data.seq;
        _this.contentSeq = res.data.contentSeq;
        _this.reporter = res.data.reporter;
        _this.reporterSeq = res.data.reporterSeq;
        _this.reason = res.data.reason;
        _this.comment = res.data.comment;
        _this.url = res.data.url;
        _this.date = res.data.date;
        _this.adminStatus = res.data.adminStatus;
        _this.adminComment = res.data.adminComment;
        _this.charge = res.data.charge;
        _this.resultDate = res.data.resultDate;
        _this.content = res.data.content;

        _this.adminCommentWriting = _this.adminComment;
      });
    },
    onReportClick: function onReportClick(reportSeq) {
      this.showModal = true;
      this.fetchReportDetail(reportSeq);
    },
    onAdminCommentEditClick: function onAdminCommentEditClick() {
      this.editComment = true;
      this.adminCommentWriting = this.adminComment;
    },
    onAdminCommentSave: function onAdminCommentSave() {
      var _this2 = this;

      axios.post('/aadmin/report/process/updateComment', {
        reportSeq: this.seq,
        adminComment: this.adminCommentWriting
      }).then(function (res) {
        _this2.adminComment = _this2.adminCommentWriting;
        _this2.editComment = false;
      });
    },
    onChargeClick: function onChargeClick(action) {
      var _this3 = this;

      axios.post('/aadmin/report/process/chargeReport', {
        reportSeq: this.seq,
        chargeAction: action
      }).then(function (res) {
        _this3.adminStatus = _this3.$store.code.adminStatus[action];
        _this3.$store.fetchNewData();
        if (action === 'DELETE') {
          alert('게시물이 삭제되었습니다.');
        }
      });
    }
  },
  created: function created() {
    this.$bus.$on('report-click', this.onReportClick);
  }
});