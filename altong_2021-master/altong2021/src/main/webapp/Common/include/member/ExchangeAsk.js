'use strict';
loadProgressBar();
function onRequestBtnClick() {
  return !0 === CertificationVue.certFinish
    ? void (
        !0 === AccountFormVue.validate() &&
        axios.get('/member/exchange/process/requiredInfoCheck').then(function(b) {
          var c = b.data;
          '' !== c &&
            ((AdditionalInfo.showIdDoc = !c.IdDoc),
            (AdditionalInfo.showInfoForm = !c.RequiredInfo),
            c.IdDoc &&
              c.RequiredInfo &&
              axios
                .post(
                  '/member/exchange/process/exchangeAskSave',
                  AccountFormVue.formResult
                )
                .then(function(d) {
                  'success' === d.data.result
                    ? (alert(
                        '\uCD9C\uAE08\uC2E0\uCCAD\uC774 \uC644\uB8CC\uB418\uC5C8\uC2B5\uB2C8\uB2E4.'
                      ),
                      (location.href = '/default/main'))
                    : 'error' === d.data.result
                    ? (alert(
                        '\uCD9C\uAE08 \uC2E0\uCCAD \uC0AC\uD56D\uC5D0 \uBCC0\uB3D9\uC0AC\uD56D\uC774 \uC0DD\uACBC\uC2B5\uB2C8\uB2E4. \uD655\uC778 \uD6C4 \uB2E4\uC2DC \uC2DC\uB3C4\uD574\uC8FC\uC138\uC694.'
                      ),
                      console.log(d.data),
                      document
                        .querySelector('#amount-almoney')
                        .setAttribute('max', d.data.newLimit),
                      (document.querySelector(
                        '#exchange-limit-almoney'
                      ).innerHTML = d.data.newLimit),
                      AccountFormVue.onAllMoneyBtnClick())
                    : 'notCert' === d.data.result &&
                      (alert(
                        '\uBCF8\uC778\uD655\uC778\uC774 \uC644\uB8CC\uB418\uC9C0 \uC54A\uC558\uC2B5\uB2C8\uB2E4. \uC7A0\uC2DC \uD6C4 \uB2E4\uC2DC \uC2DC\uB3C4\uD574\uC8FC\uC138\uC694.'
                      ),
                      location.reload());
                }));
        })
      )
    : (alert(
        '\uBCF8\uC778 \uC778\uC99D\uC774 \uC644\uB8CC\uB418\uC9C0 \uC54A\uC558\uC2B5\uB2C8\uB2E4.'
      ),
      void (!0 === CertificationVue.certBtnClickCheck
        ? document.querySelector('#cert-code').focus()
        : document.querySelector('#cert-btn').focus()));
}
var CertificationVue = new Vue({
    el: '.certification-section',
    data: { certBtnClickCheck: !1, certCode: null, certFinish: !1 },
    methods: {
      onCertBtnClick: function onCertBtnClick() {
        !1 === this.certBtnClickCheck
          ? ((this.certBtnClickCheck = !0), fnPopup())
          : alert(
              '\uCC98\uB9AC \uC911 \uC785\uB2C8\uB2E4. \uC7A0\uC2DC\uB9CC \uAE30\uB2E4\uB824\uC8FC\uC138\uC694'
            );
      },
      onCertCheckBtnClick: function onCertCheckBtnClick(a) {
        var b = this;
        a.preventDefault(),
          axios
            .get('/member/exchange/process/cert/cert', {
              params: { CertCode: this.certCode },
            })
            .then(function(c) {
              'success' === c.data
                ? (b.certFinish = !0)
                : ((b.certFinish = !1),
                  alert(
                    '\uC720\uD6A8\uD558\uC9C0 \uC54A\uC740 \uC778\uC99D\uBC88\uD638\uC785\uB2C8\uB2E4. \uB2E4\uC2DC \uC2DC\uB3C4\uD574\uC8FC\uC138\uC694.'
                  ));
            });
      },
    },
  }),
  AccountFormVue = new Vue({
    el: '.account-form-section',
    data: {
      exchangeAlmoney: 0,
      animatedNumber: 0,
      bank: '',
      account: '',
      owner: '',
    },
    computed: {
      realExchangeAlmoney: function realExchangeAlmoney() {
        return Math.floor(0.95 * (this.exchangeAlmoney / 10));
      },
      formResult: function formResult() {
        return {
          BankName: this.bank,
          BankAccountNo: this.account,
          BankMemNm: this.owner,
          ExchangeAlmoney: this.exchangeAlmoney,
          RealMoney: this.realExchangeAlmoney,
        };
      },
    },
    methods: {
      limit: function limit() {
        var a = document.querySelector('#amount-almoney').getAttribute('max'),
          b = 10000 * Math.floor(parseInt(a) / 10000);
        return b;
      },
      onExchangeAlmoneyChange: function onExchangeAlmoneyChange() {
        (this.exchangeAlmoney =
          10000 * Math.floor(this.exchangeAlmoney / 10000)),
          this.exchangeAlmoney > this.limit() &&
            (this.exchangeAlmoney = this.limit());
      },
      onAllMoneyBtnClick: function onAllMoneyBtnClick() {
        this.exchangeAlmoney = this.limit();
      },
      onGetLastAccountBtnClick: function onGetLastAccountBtnClick(a) {
        var b = this;
        a.preventDefault(),
          axios.get('/member/account/exchange/process/getLastAccount').then(function(c) {
            c.data.hasOwnProperty('BankName') && '' !== c.data.BankName
              ? ((b.bank = c.data.BankName),
                (b.account = c.data.BankAccountNo),
                (b.owner = c.data.BankMemNm))
              : alert(
                  '\uC774\uC804 \uCD9C\uAE08 \uC2E0\uCCAD \uB0B4\uC5ED\uC774 \uC5C6\uC2B5\uB2C8\uB2E4.'
                );
          });
      },
      validate: function validate() {
        return '' === this.bank
          ? (alert('\uC740\uD589\uC744 \uC120\uD0DD\uD574\uC8FC\uC138\uC694.'),
            document.querySelector('select[name=bank]').focus(),
            !1)
          : '' === this.account
          ? (alert(
              '\uACC4\uC88C\uBC88\uD638\uB97C \uC785\uB825\uD574\uC8FC\uC138\uC694'
            ),
            document.querySelector('input[name=account]').focus(),
            !1)
          : '' === this.owner
          ? (alert(
              '\uC608\uAE08\uC8FC\uB97C \uC785\uB825\uD574\uC8FC\uC138\uC694.'
            ),
            document.querySelector('input[name=owner]').focus(),
            !1)
          : 0 !== this.exchangeAlmoney ||
            (alert(
              '\uCD9C\uAE08 \uC2E0\uCCAD \uC54C\uC744 \uC785\uB825\uD574\uC8FC\uC138\uC694.'
            ),
            document.querySelector('#amount-almoney').focus(),
            !1);
      },
    },
  }),
  AdditionalInfo = new Vue({
    el: '.additional-info',
    data: function data() {
      return { showIdDoc: !1, showInfoForm: !1 };
    },
    components: {},
  });
Vue.component('id-doc', {
  template: '#id-doc-template',
  data: function data() {
    return { fileList: [], imgList: [] };
  },
  methods: {
    onFileSelectClick: function onFileSelectClick() {
      var b = document.querySelector('#id-doc-file-selector');
      b.click();
    },
    onFileSelectorChange: function onFileSelectorChange(a) {
      console.log(a.target.value);
      var b = [];
      $.ajax({
        url: '/member/exchange/process/idDocUpload',
        type: 'post',
        processData: !1,
        contentType: !1,
        async: !1,
        data: new FormData(document.querySelector('#id-doc-form')),
        success: function success(c) {
          alert(
            '\uC99D\uBE59\uC790\uB8CC\uAC00 \uC5C5\uB85C\uB4DC \uB418\uC5C8\uC2B5\uB2C8\uB2E4.'
          ),
            (b = $.parseJSON(c));
        },
        error: function error() {
          alert(
            '\uC11C\uBC84\uC5D0\uC11C \uC624\uB958\uAC00 \uBC1C\uC0DD\uD558\uC600\uC2B5\uB2C8\uB2E4. \uC7A0\uC2DC \uD6C4 \uB2E4\uC2DC \uC2DC\uB3C4\uD574\uC8FC\uC138\uC694.\n\uC624\uB958 \uC9C0\uC18D\uC2DC \uAD00\uB9AC\uC790\uC5D0\uAC8C \uBB38\uC758\uD558\uC138\uC694.'
          );
        },
      }),
        (this.imgList = b);
    },
  },
}),
  Vue.component('required-info', {
    template: '#required-info-template',
    data: function data() {
      return {
        job: '',
        area: '',
        estate: '',
        residence: '',
        marriage: '',
        children: '',
        car: '',
        yearIncome: '',
        email: '',
        healthFlag: 'N',
        healthDetail: '',
        likeFieldValue: '',
      };
    },
    computed: {
      formResult: function formResult() {
        return {
          Job: this.job,
          Area: this.area,
          Estate: this.estate,
          Residence: this.residence,
          Marriage: this.marriage,
          Children: this.children,
          Car: this.car,
          YearIncome: this.yearIncome,
          Email: this.email,
          HealthFlag: this.healthFlag,
          HealthDetail: this.healthDetail,
          likeFieldValue: this.likeFieldValue,
        };
      },
    },
    methods: {
      onLikeFieldChange: function onLikeFieldChange(a) {
        this.likeFieldValue = a;
      },
      onRequiredInfoSaveBtnClick: function onRequiredInfoSaveBtnClick(a) {
        for (var b in (a.preventDefault(), this.formResult))
          if (
            ('' === this.formResult[b] && 'HealthDetail' !== b) ||
            null === this.formResult[b] ||
            ('HealthDetail' === b && 'Y' === this.formResult.HealthFlag)
          )
            return (
              alert(
                '\uD544\uC218 \uC785\uB825\uD56D\uBAA9\uC744 \uBAA8\uB450 \uC785\uB825\uD574\uC8FC\uC138\uC694'
              ),
              void window.scrollTo(
                0,
                document.querySelector(
                  '.additional-info>div>div.panel.panel-info>.panel-heading>.panel-title'
                ).offsetTop - 15
              )
            );
        axios
          .post('/member/exchange/process/requiredInfoSave', this.formResult)
          .then(function() {
            alert(
              '\uD544\uC218 \uC815\uBCF4\uAC00 \uC800\uC7A5\uB418\uC5C8\uC2B5\uB2C8\uB2E4.'
            ),
              onRequestBtnClick();
          });
      },
    },
    components: {
      test: null,
      'like-field': {
        template: '#like-field-template',
        props: [],
        data: function data() {
          return { selectedItems: [] };
        },
        computed: {
          likeFieldResult: function likeFieldResult() {
            var a = '';
            for (var b in this.selectedItems) a += this.selectedItems[b] + ',';
            return a;
          },
        },
        methods: {
          onLikeItemClick: function onLikeItemClick(a) {
            var b = a.target.dataset.likeItem;
            a.target.classList.contains('selected')
              ? (a.target.classList.remove('selected'),
                this.selectedItems.splice(this.selectedItems.indexOf(b), 1))
              : (a.target.classList.add('selected'),
                this.selectedItems.push(b)),
              this.$emit('like-field-changed', this.likeFieldResult);
          },
        },
      },
    },
  });
