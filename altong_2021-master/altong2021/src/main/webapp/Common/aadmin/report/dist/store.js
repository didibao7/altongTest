'use strict';

Vue.prototype.$store = new Vue({
  data: function data() {
    return {
      reportList: [],
      params: {
        contentsType: '',
        reportReason: '',
        startDate: '',
        endDate: '',
        adminStatus: ''
      },
      pagination: {
        pageSize: 30,
        pageCursor: 1,
        totalPage: 0
      },
      code: {
        adminStatus: {},
        contents: {},
        reason: {}
      },
      totalCount: 0
    };
  },

  methods: {
    fetchNewData: function fetchNewData() {
      var _this = this;

      axios.get('/aadmin/report/process/getReportList', { params: Object.assign({}, this.params, this.pagination) }).then(function (res) {
        _this.reportList = res.data.list;
        _this.pagination.totalPage = res.data.totalPage;
        _this.totalCount = res.data.totalCount;
      });
    }
  },
  created: function created() {
    var _this2 = this;

    this.fetchNewData();
    axios.get('/aadmin/report/process/getCode').then(function (res) {
      _this2.code = res.data;
    });
  }
});