loadProgressBar();

function onRequestBtnClick(event) {
  if (CertificationVue.certFinish !== true) {
    alert('본인 인증이 완료되지 않았습니다.');
    if (CertificationVue.certBtnClickCheck === true) {
      document.querySelector('#cert-code').focus();
    } else {
      document.querySelector('#cert-btn').focus();
    }
    return;
  }
  if (AccountFormVue.validate() === true) {
    axios.get('/member/exchange/process/requiredInfoCheck').then((response) => {
      const result = response.data;
      if (result !== '') {
        AdditionalInfo.showIdDoc = !result.IdDoc;
        AdditionalInfo.showInfoForm = !result.RequiredInfo;

        if (result.IdDoc && result.RequiredInfo) {
          // save the exchange request
          axios.post('./process/ExchangeAskSave.asp', AccountFormVue.formResult).then((response) => {
            if (response.data.result === 'success') {
              alert('출금신청이 완료되었습니다.');
              location.href = '/Default/Main.asp';
            } else {
              alert('출금 신청 사항에 변동사항이 생겼습니다. 확인 후 다시 시도해주세요.');
              console.log(response.data);
              document.querySelector('#amount-almoney').setAttribute('max', response.data.newLimit);
              document.querySelector('#exchange-limit-almoney').innerHTML = response.data.newLimit;
              AccountFormVue.onAllMoneyBtnClick();
            }
          });
        }
      }
    });
  }
}

const CertificationVue = new Vue({
  el: '.certification-section',
  data: {
    certBtnClickCheck: false,
    certCode: null,
    certFinish: false,
  },
  methods: {
    onCertBtnClick(event) {
      axios.get('/member/exchange/process/cert/sendSms').then((response) => {
        if (response.data === 'success') {
          this.certBtnClickCheck = true;
        } else if (response.data === 'sms_send_error') {
          alert('인증 문자 발송에 실패하였습니다. 잠시 후 다시 시도해주세요.');
        } else {
          alert('오류가 발생하였습니다. 잠시 후 다시 시도해주세요.');
          location.reload();
        }
      });
    },
    onCertCheckBtnClick(event) {
      event.preventDefault();
      axios.get('/member/exchange/process/cert/cert', {
        params: {
          CertCode: this.certCode,
        },
      }).then((response) => {
        if (response.data === 'success') {
          this.certFinish = true;
        } else {
          this.certFinish = false;
          alert('유효하지 않은 인증번호입니다. 다시 시도해주세요.');
        }
      });
    },
  },
});

const AccountFormVue = new Vue({
  el: '.account-form-section',
  data: {
    exchangeAlmoney: 0,
    animatedNumber: 0,
    bank: '',
    account: '',
    owner: '',
  },
  computed: {
    realExchangeAlmoney() {
      return Math.floor(this.exchangeAlmoney * 0.95);
    },
    formResult() {
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
    limit() {
      const maxValue = document.querySelector('#amount-almoney').getAttribute('max');
      const result = Math.floor(parseInt(maxValue) / 100) * 100;
      return result;
    },
    onExchangeAlmoneyChange(event) {
      this.exchangeAlmoney = Math.floor(this.exchangeAlmoney / 100) * 100;

      if (this.exchangeAlmoney > this.limit()) {
        this.exchangeAlmoney = this.limit();
      }
    },
    onAllMoneyBtnClick(event) {
      this.exchangeAlmoney = this.limit();
    },
    validate() {
      if (this.bank === '') {
        alert('은행을 선택해주세요.');
        document.querySelector('select[name=bank]').focus();
        return false;
      } else if (this.account === '') {
        alert('계좌번호를 입력해주세요');
        document.querySelector('input[name=account]').focus();
        return false;
      } else if (this.owner === '') {
        alert('예금주를 입력해주세요.');
        document.querySelector('input[name=owner]').focus();
        return false;
      } else if (this.exchangeAlmoney === 0) {
        alert('출금 신청 알을 입력해주세요.');
        document.querySelector('#amount-almoney').focus();
        return false;
      }
      return true;
    },
  },
});

const AdditionalInfo = new Vue({
  el: '.additional-info',
  data() {
    return {
      showIdDoc: false,
      showInfoForm: false,
    };
  },
  components: {

  },
});

Vue.component('id-doc', {
  template: '#id-doc-template',
  data() {
    return {
      fileList: [],
      imgList: [],
    };
  },
  methods: {
    onFileSelectClick(event) {
      const fileSelector = document.querySelector('#id-doc-file-selector');
      fileSelector.click();
    },
    onFileSelectorChange(event) {
      console.log(event.target.value);
      let tmpImgList = [];

      $.ajax({
        url: './process/IdDocUpload.asp',
        type: 'post',
        processData: false,
        contentType: false,
        async: false,
        data: new FormData(document.querySelector('#id-doc-form')),
        success(response) {
          alert('증빙자료가 업로드 되었습니다.');
          tmpImgList = $.parseJSON(response);
        },
        error(error) {
          alert('서버에서 오류가 발생하였습니다. 잠시 후 다시 시도해주세요.\n오류 지속시 관리자에게 문의하세요.');
        },
      });

      this.imgList = tmpImgList;
    },
  },
});

Vue.component('required-info', {
  template: '#required-info-template',
  data() {
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
    formResult() {
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
    onLikeFieldChange(value) {
      this.likeFieldValue = value;
    },
    onRequiredInfoSaveBtnClick(event) {
      event.preventDefault();

      for (const i in this.formResult) {
        if ((this.formResult[i] === '' && i !== 'HealthDetail') || this.formResult[i] === null || (i === 'HealthDetail' && this.formResult.HealthFlag === 'Y')) {
          alert('필수 입력항목을 모두 입력해주세요');
          window.scrollTo(0, document.querySelector('.additional-info>div>div.panel.panel-info>.panel-heading>.panel-title').offsetTop - 15);
          return;
        }
      }
      axios.post('/member/exchange/process/requiredInfoSave', this.formResult).then((response) => {
        alert('필수 정보가 저장되었습니다.');
        onRequestBtnClick();
      });
    },
  },
  components: {
    test: null,
    'like-field': {
      template: '#like-field-template',
      props: [],
      data() {
        return {
          selectedItems: [],
        };
      },
      computed: {
        likeFieldResult() {
          let resultString = '';

          for (const i in this.selectedItems) {
            resultString += `${this.selectedItems[i]},`;
          }

          return resultString;
        },
      },
      methods: {
        onLikeItemClick(event) {
          const likeItem = event.target.dataset.likeItem;

          if (event.target.classList.contains('selected')) {
            event.target.classList.remove('selected');
            this.selectedItems.splice(this.selectedItems.indexOf(likeItem), 1);
          } else {
            event.target.classList.add('selected');
            this.selectedItems.push(likeItem);
          }

          this.$emit('like-field-changed', this.likeFieldResult);
        },
      },
    },
  },
});
