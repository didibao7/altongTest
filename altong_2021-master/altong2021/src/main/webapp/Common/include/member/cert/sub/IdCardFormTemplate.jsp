<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<c:set var="flagIdCard" value="${flagIdCard}"/>
<%
String flagIdCard = String.valueOf( pageContext.getAttribute("flagIdCard") );
%>
<style>
  #picked-file-list {
    margin: 0;
    padding: 0;
  }

  .file-list-item {
    padding: 10px;
  }

  #id-card-form {
    padding-left: 15px;
    padding-right: 15px;
  }

  #id-card-form span {
    display: block;
    height: 34px;
    padding: 6px 12px;
    font-size: 14px;
    line-height: 1.42857143;
    color: #555;
    background-color: #fff;
    background-image: none;
    border: 1px solid #ccc;
    border-radius: 4px;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
    -webkit-transition: border-color ease-in-out 0.15s,
      -webkit-box-shadow ease-in-out 0.15s;
    -o-transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
    transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
    background-color: #eee;
    opacity: 1;
    font-size: 0.9em;
  }

  #picked-files-label {
    border: 1px solid rgba(33, 47, 41, 0.2);
    width: 200px;
    display: inline-block;
    -o-text-overflow: ellipsis;
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
    vertical-align: top;
  }
</style>
<% if (flagIdCard.equals("true")) { %>
<div class="alert alert-success" role="alert" style="text-align: center;">
  <spring:message code="msg_0001"/>
</div>
<% } else { %>
<form id="id-card-form" class="form-inline row" enctype="multipart/form-data">
  <input type="file" id="files" name="files" style="display:none;" />
  <ul id="picked-file-list" class="row"></ul>
  <button
    id="custom-btn"
    class="btn btn-default col-xs-4 col-xs-offset-4"
    style="margin-top:10px"
  >
    <spring:message code="msg_0002"/>
  </button>
  <input
    type="submit"
    id="upload-btn"
    class="btn btn-success col-xs-3"
    value='<spring:message code="msg_0003"/>'
    style="display:none;"
  />
</form>
<% } %>
<script type="text/templaet" id="file-list-item-template">
  <li class="row file-list-item" data-file-name="#name#">
      <div class="col-xs-8 col-xs-offset-2">
          <img src="#src#" class="img-thumbnail" style="width:100%;"/>
      </div>
      <!--
      <div class="col-xs-4">
          <i class="fas fa-trash-alt delete-btn"></i>
      </div>
      -->
  </li>
</script>

