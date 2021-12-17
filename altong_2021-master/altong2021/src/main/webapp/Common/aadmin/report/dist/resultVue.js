'use strict';

var resultVue = new Vue({
  el: '#result-wrapper',
  methods: {
    onLinkClick: function onLinkClick(question) {
      var answer =
        arguments.length > 1 && arguments[1] !== undefined
          ? arguments[1]
          : null;

      var targetUrl = '/answer/answerList?Seq=' + question + '#' + answer;
      window.open(targetUrl);
    },
    makeClassObjcet: function makeClassObjcet(report) {
      return {
        warning: report.AdminStatusCD === 'HOLD',
        danger: report.AdminStatusCD === 'DELETE',
        active: report.AdminStatusCD === 'ETC',
      };
    },
  },
});
