function isBeingChildData(memberSeq) {
  return (
    bus.ajaxMemo.includes(memberSeq.toString()) ||
    bus.members.filter(iter => iter.ParentSeq == memberSeq).length > 0 ||
    bus.members.filter(iter => iter.Seq == memberSeq && iter.ChildCnt == 0).length > 0);
}

const bus = new Vue({
  data: {
    members: [],
    ajaxMemo: [],
  },
  methods: {
    updateData() {
      const flag = arguments[0] || false;
      axios.get('/aadmin/recommend/getTreeData?rootNode=0').then((response) => {
        this.members = [];
        this.ajaxMemo = [];
        response.data.map((iter) => {
          iter.toggleChildren = false;
          return iter;
        });
        this.members = response.data;
        console.log(this.members);

        if (flag) alert('데이터가 갱신되었습니다.');
      });
    },
  },
});

const SearchConditionVue = new Vue({
  el: '.search-condition',
  data: {
    searchType: 'sName',
    searchTarget: '',
    order: {
      childCnt: '',
      earning: '',
      joinDate: '',
    },
    startDate: getFormatDate(new Date('2017.08.01')), // '/Common/js/Common.js' -> getFormatDate
    endDate: getFormatDate(new Date()),
    userLv: 1,
  },
  methods: {
    onSearchInputKeyUp(event) {
      if (event.keyCode === 13) {
        this.onSearchButtonClick(event);
      }
    },
    onSearchButtonClick(event) {
      if (this.searchTarget.length === 0) {
        alert('검색할 회원을 입력해주세요.');
        document.querySelector('input[name=searchTarget]').focus();
      } else {
        axios.get('/aadmin/recommend/getMemberSeq', {
          params: {
            searchType: this.searchType,
            searchTarget: this.searchTarget,
          },
        }).then((response) => {
          if (response.data === '' || response.data.MemberSeq == 0) {
            alert('검색되는 회원이 없습니다.');
          } else {
            SearchResultVue.targetRoot = parseInt(response.data.MemberSeq);
            axios.get('/aadmin/recommend/getTreeData', {
              params: {
                rootNode: response.data.ParentSeq,
              },
            }).then((response) => {
              if (response.data !== '') {
                response.data.map((iter) => {
                  iter.toggleChildren = false;
                  return iter;
                });
                bus.members = bus.members.concat(response.data.filter(iter => bus.members.filter(iter2 => iter.Seq === iter2.Seq).length === 0));
              }
              bus.ajaxMemo.push(parent);
            });
          }
        });
      }
    },
  },
  computed: {
    searchQueryString() {
      return `searchType=${this.searchType}&searchTarget=${this.searchTarget}`;
    },
  },
});

const TreeControlVue = new Vue({
  el: '.tree-control',
  data: {
    action: '',
    target1: null,
    target2: null,
  },
  methods: {
    onTreeResetBtnClick() {
      const checkReset = confirm('정말로 리셋하시겠습니까?');

      if (checkReset) {
        axios.post('/aadmin/recommend/resetTree').then(() => {
          alert('트리가 초기화 되었습니다.');
          location.reload();
        });
      }
    },
    onTreeControlBtnClick(evnet) {
      if (this.action === '' || !this.target1 || !this.target2) {
        alert('입력을 확인해주세요');
        return;
      }

      const checkChange = confirm('정말로 수정하시겠습니까?');

      if (checkChange) {
        axios.post(`/aadmin/recommend/changeTree?action=${this.action}&target1=${this.target1}&target2=${this.target2}`).then((res) => {
          const resCode = res.data;

          switch (resCode) {
            case 0:
              location.reload();
              // bus.updateData(true);
              break;
            case 1:
              alert('유효하지 않은 고유번호가 입력되었습니다.');
              break;
            case 2:
              alert('추천인-피추천인 간 자식을 유지한채 위치를 변경할 수 없습니다.');
              break;
            default:
              alert('DB 오류가 발생하였습니다. 잠시 후 다시 시도해주세요.');
          }
        });
      }
    },
  },
});

const TreeBackUpVue = new Vue({
  el: '.tree-back-up',
  data: {
    targetBackUp: '',
    backUpList: [],
  },
  methods: {
    onTreeBackUpBtnClick() {
      const checkBackUp = confirm('백업 하시겠습니까?');

      if (checkBackUp) {
        axios.post(`/aadmin/recommend/backUp/backUpTree?memo=${prompt('백업본에 메모를 해주세요')}`).then(function () {
          alert('트리가 백업되었습니다.');
          axios.get('./BackUp/getBackUpList.asp').then((response) => {
            this.backUpList = response.data;
          });
        });
      }
    },
    onRestoreBtnClick() {
      const checkRestore = confirm('복원 하시겠습니까?');

      if (checkRestore) {
        axios.post(`/aadmin/recommend/backUp/restoreTree?target=${this.targetBackUp}`).then((response) => {
          alert('백업본이 적용되었습니다.');
          location.reload();
        });
      }
    },
  },
  mounted() {
    axios.get('/aadmin/recommend/backUp/getBackUpList').then((response) => {
      this.backUpList = response.data;
    });
  },
});

const SearchResultVue = new Vue({
  el: '.search-result',
  data: {
    toggle: true, // true -> 트리구조, false -> 리스트
    targetRoot: 0,
  },
  computed: {
    resultList() {
      if (this.targetRoot !== 0) {
        return bus.members.filter(iter => iter.Seq == this.targetRoot);
      }
      return bus.members.filter(iter => iter.ParentSeq == this.targetRoot);
    },
  },
  methods: {
    setToggle(value) {
      this.toggle = value;
    },
    showAllTree() {
      bus.members.map((iter) => {
        iter.toggleChildren = true;
        return iter;
      });
    },
  },
  mounted() {
    bus.updateData();
  },
});

Vue.component('tree-table-row', {
  name: 'tree-table-row',
  template: '#tree-table-row-template',
  props: ['member', 'gen', ''],
  computed: {
    isAbleToShowParent() {
      return this.member.ParentSeq != 0 && SearchResultVue.targetRoot != 0 && SearchResultVue.targetRoot != this.member.ParentSeq;
    },
  },
  methods: {
    onMemberSeqClick(event) {
      window.open(`/aadmin/memberView?Seq=${this.member.Seq}`);
    },
    onShowParentClick(event) {
      SearchResultVue.targetRoot = this.member.ParentSeq;

      axios.get('/aadmin/recommend/getMemberSeq', {
        params: {
          searchType: 'Seq',
          searchTarget: this.member.ParentSeq,
        },
      }).then((response) => {
        if (response.data !== '' && response.data.ParentSeq != -1) {
          axios.get('/aadmin/recommend/getTreeData', {
            params: {
              rootNode: response.data.ParentSeq,
            },
          }).then((response2) => {
            if (response2.data !== '') {
              response2.data.map((iter) => {
                iter.toggleChildren = true;
                return iter;
              });
              bus.members = bus.members.concat(response2.data.filter(iter => bus.members.filter(iter2 => iter.Seq === iter2.Seq).length === 0));
            }
            bus.ajaxMemo.push(this.member.ParentSeq);
          });
        }
      });
    },
    reverseChildrenToggle(member) {
      bus.members.filter(iter => iter.Seq == member.Seq).map((iter) => {
        iter.toggleChildren = !iter.toggleChildren;
        return iter;
      });
    },
    getChildren(parent) {
      if (!isBeingChildData(parent)) {
        axios.get('/aadmin/recommend/getTreeData', {
          params: {
            rootNode: parent,
          },
        }).then((response) => {
          if (response.data !== '') {
            response.data.map((iter) => {
              iter.toggleChildren = false;
              return iter;
            });
          }

          bus.ajaxMemo.push(parent);
          bus.members = bus.members.concat(response.data.filter(iter => bus.members.filter(iter2 => iter.Seq === iter2.Seq).length === 0));

          this.children = bus.members.filter(iter => iter.ParentSeq == parent);
        });
      } else {
        this.children = bus.members.filter(iter => iter.ParentSeq == parent);
      }
    },
  },
  data() {
    return {
      children: [],
    };
  },
  mounted() {
    this.getChildren(this.member.Seq);
  },
});
