'use strict';

var searchFilter = new Vue({
  el: '.filter-wrapper',
  data: {},
  methods: {
    onFormSubmit: function onFormSubmit(event) {
      event.preventDefault();
      this.$store.fetchNewData();
    }
  }
});