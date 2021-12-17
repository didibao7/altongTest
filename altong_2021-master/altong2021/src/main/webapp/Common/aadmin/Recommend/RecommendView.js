"use strict";

function isBeingChildData(memberSeq) {
  return (
    bus.ajaxMemo.includes(memberSeq.toString()) ||
    bus.members.filter(function(iter) {
      return iter.ParentSeq == memberSeq;
    }).length > 0 ||
    bus.members.filter(function(iter) {
      return iter.Seq == memberSeq && iter.ChildCnt == 0;
    }).length > 0
  );
}

var bus = new Vue({
  data: {
    members: [],
    ajaxMemo: []
  },
  methods: {
    updateData: function updateData() {
      var _this = this;

      var flag = arguments[0] || false;
      axios.get("/aadmin/getTreeData?rootNode=0").then(function(response) {
        _this.members = [];
        _this.ajaxMemo = [];
        response.data.map(function(iter) {
          iter.toggleChildren = false;
          return iter;
        });
        _this.members = response.data;
        console.log(_this.members);

        if (flag) alert("데이터가 갱신되었습니다.");
      });
    }
  }
});

var SearchConditionVue = new Vue({
  el: ".search-condition",
  data: {
    searchType: "sName",
    searchTarget: "",
    order: {
      childCnt: "",
      earning: "",
      joinDate: ""
    },
    startDate: getFormatDate(new Date("2017.08.01")), // '/Common/js/Common.js' -> getFormatDate
    endDate: getFormatDate(new Date()),
    userLv: 1
  },
  methods: {
    onSearchInputKeyUp: function onSearchInputKeyUp(event) {
      if (event.keyCode === 13) {
        this.onSearchButtonClick(event);
      }
    },
    onSearchButtonClick: function onSearchButtonClick(event) {
      if (this.searchTarget.length === 0) {
        alert("검색할 회원을 입력해주세요.");
        document.querySelector("input[name=searchTarget]").focus();
      } else {
        axios
          .get("/aadmin/getMemberSeq", {
            params: {
              searchType: this.searchType,
              searchTarget: this.searchTarget
            }
          })
          .then(function(response) {
            if (response.data === "" || response.data.MemberSeq == 0) {
              alert("검색되는 회원이 없습니다.");
            } else {
              SearchResultVue.targetRoot = parseInt(response.data.MemberSeq);
              axios
                .get("/aadmin/getTreeData", {
                  params: {
                    rootNode: response.data.ParentSeq
                  }
                })
                .then(function(response) {
                  if (response.data !== "") {
                    response.data.map(function(iter) {
                      iter.toggleChildren = false;
                      return iter;
                    });
                    bus.members = bus.members.concat(
                      response.data.filter(function(iter) {
                        return (
                          bus.members.filter(function(iter2) {
                            return iter.Seq === iter2.Seq;
                          }).length === 0
                        );
                      })
                    );
                  }
                  bus.ajaxMemo.push(parent);
                });
            }
          });
      }
    }
  },
  computed: {
    searchQueryString: function searchQueryString() {
      return (
        "searchType=" + this.searchType + "&searchTarget=" + this.searchTarget
      );
    }
  }
});

var TreeControlVue = new Vue({
  el: ".tree-control",
  data: {
    action: "",
    target1: null,
    target2: null
  },
  methods: {
    onTreeResetBtnClick: function onTreeResetBtnClick() {
      var checkReset = confirm("정말로 리셋하시겠습니까?");

      if (checkReset) {
        axios.post("/aadmin/resetTree").then(function() {
          alert("트리가 초기화 되었습니다.");
          location.reload();
        });
      }
    },
    onTreeControlBtnClick: function onTreeControlBtnClick(evnet) {
      if (this.action === "" || !this.target1 || !this.target2) {
        alert("입력을 확인해주세요");
        return;
      }

      var checkChange = confirm("정말로 수정하시겠습니까?");

      if (checkChange) {
        axios
          .post(
            "/aadmin/changeTree?action=" +
              this.action +
              "&target1=" +
              this.target1 +
              "&target2=" +
              this.target2
          )
          .then(function(res) {
            var resCode = res.data;

            switch (resCode) {
              case 0:
                location.reload();
                // bus.updateData(true);
                break;
              case 1:
                alert("유효하지 않은 고유번호가 입력되었습니다.");
                break;
              case 2:
                alert("추천인-피추천인 간 자식을 유지한채 위치를 변경할 수 없습니다.");
                break;
              default:
                alert("DB 오류가 발생하였습니다. 잠시 후 다시 시도해주세요.");
            }
          });
      }
    }
  }
});

var TreeBackUpVue = new Vue({
  el: ".tree-back-up",
  data: {
    targetBackUp: "",
    backUpList: []
  },
  methods: {
    onTreeBackUpBtnClick: function onTreeBackUpBtnClick() {
      var checkBackUp = confirm("백업 하시겠습니까?");

      if (checkBackUp) {
        axios
          .post("/aadmin/backUpTree?memo=" + prompt("백업본에 메모를 해주세요"))
          .then(function() {
            var _this2 = this;
			
            alert("트리가 백업되었습니다.");
            axios.get("/aadmin/getBackUpList").then(function(response) {
              //_this2.backUpList = response.data;
              location.reload();
            });
          });
      }
    },
    onRestoreBtnClick: function onRestoreBtnClick() {
      var checkRestore = confirm("복원 하시겠습니까?");

      if (checkRestore) {
        axios
          .post("/aadmin/restoreTree?target=" + this.targetBackUp)
          .then(function(response) {
            alert("백업본이 적용되었습니다.");
            location.reload();
          });
      }
    }
  },
  mounted: function mounted() {
    var _this3 = this;

    axios.get("/aadmin/getBackUpList").then(function(response) {
      _this3.backUpList = response.data;
    });
  }
});

var SearchResultVue = new Vue({
  el: ".search-result",
  data: {
    toggle: true, // true -> 트리구조, false -> 리스트
    targetRoot: 0
  },
  computed: {
    resultList: function resultList() {
      var _this4 = this;

      if (this.targetRoot !== 0) {
        return bus.members.filter(function(iter) {
          return iter.Seq == _this4.targetRoot;
        });
      }
      return bus.members.filter(function(iter) {
        return iter.ParentSeq == _this4.targetRoot;
      });
    }
  },
  methods: {
    setToggle: function setToggle(value) {
      this.toggle = value;
    },
    showAllTree: function showAllTree() {
      bus.members.map(function(iter) {
        iter.toggleChildren = true;
        return iter;
      });
    }
  },
  mounted: function mounted() {
    bus.updateData();
  }
});

Vue.component("tree-table-row", {
  name: "tree-table-row",
  template: "#tree-table-row-template",
  props: ["member", "gen", ""],
  computed: {
    isAbleToShowParent: function isAbleToShowParent() {
      return (
        this.member.ParentSeq != 0 &&
        SearchResultVue.targetRoot != 0 &&
        SearchResultVue.targetRoot != this.member.ParentSeq
      );
    }
  },
  methods: {
    onMemberSeqClick: function onMemberSeqClick(event) {
      window.open("/aadmin/memberView?Seq=" + this.member.Seq);
    },
    onShowParentClick: function onShowParentClick(event) {
      var _this5 = this;

      SearchResultVue.targetRoot = this.member.ParentSeq;

      axios
        .get("/aadmin/getMemberSeq", {
          params: {
            searchType: "Seq",
            searchTarget: this.member.ParentSeq
          }
        })
        .then(function(response) {
          if (response.data !== "" && response.data.ParentSeq != -1) {
            axios
              .get("/aadmin/getTreeData", {
                params: {
                  rootNode: response.data.ParentSeq
                }
              })
              .then(function(response2) {
                if (response2.data !== "") {
                  response2.data.map(function(iter) {
                    iter.toggleChildren = true;
                    return iter;
                  });
                  bus.members = bus.members.concat(
                    response2.data.filter(function(iter) {
                      return (
                        bus.members.filter(function(iter2) {
                          return iter.Seq === iter2.Seq;
                        }).length === 0
                      );
                    })
                  );
                }
                bus.ajaxMemo.push(_this5.member.ParentSeq);
              });
          }
        });
    },
    reverseChildrenToggle: function reverseChildrenToggle(member) {
      bus.members
        .filter(function(iter) {
          return iter.Seq == member.Seq;
        })
        .map(function(iter) {
          iter.toggleChildren = !iter.toggleChildren;
          return iter;
        });
    },
    getChildren: function getChildren(parent) {
      var _this6 = this;

      if (!isBeingChildData(parent)) {
        axios
          .get("/aadmin/getTreeData", { params: { rootNode: parent } })
          .then(function(response) {
            if (response.data !== "") {
              response.data.map(function(iter) {
                iter.toggleChildren = false;
                return iter;
              });
            }

            bus.ajaxMemo.push(parent);
            bus.members = bus.members.concat(
              response.data.filter(function(iter) {
                return (
                  bus.members.filter(function(iter2) {
                    return iter.Seq === iter2.Seq;
                  }).length === 0
                );
              })
            );

            _this6.children = bus.members.filter(function(iter) {
              return iter.ParentSeq == parent;
            });
          });
      } else {
        this.children = bus.members.filter(function(iter) {
          return iter.ParentSeq == parent;
        });
      }
    }
  },
  data: function data() {
    return {
      children: []
    };
  },
  mounted: function mounted() {
    this.getChildren(this.member.Seq);
  }
});
