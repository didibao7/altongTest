<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.altong.web.logic.util.CommonUtil"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<c:set var="flagNice" value="${flagNice}"/>
<c:set var="flagIdCard" value="${flagIdCard}"/>
<c:set var="flagAdInfo" value="${flagAdInfo}"/>
<c:set var="certStatus" value="${certStatus}"/>
<c:set var="adminMessage" value="${adminMessage}"/>
<%
String flagNice = String.valueOf( pageContext.getAttribute("flagNice") );
String flagIdCard = String.valueOf( pageContext.getAttribute("flagIdCard") );
String flagAdInfo = String.valueOf( pageContext.getAttribute("flagAdInfo") );
int certStatus = Integer.parseInt( String.valueOf( pageContext.getAttribute("certStatus") ) );
String adminMessage = String.valueOf( pageContext.getAttribute("adminMessage") );

String[] jobList = {
        CommonUtil.getLangMsg(request, "msg_0004"),
		CommonUtil.getLangMsg(request, "msg_0005"),
		CommonUtil.getLangMsg(request, "msg_0006"),
		CommonUtil.getLangMsg(request, "msg_0007"),
		CommonUtil.getLangMsg(request, "msg_0008"),
		CommonUtil.getLangMsg(request, "msg_0009"),
		CommonUtil.getLangMsg(request, "msg_0010"),
		CommonUtil.getLangMsg(request, "msg_0011"),
		CommonUtil.getLangMsg(request, "msg_0012"),
		CommonUtil.getLangMsg(request, "msg_0013"),
		CommonUtil.getLangMsg(request, "msg_0014"),
		CommonUtil.getLangMsg(request, "msg_0015"),
		CommonUtil.getLangMsg(request, "msg_0016"),
		CommonUtil.getLangMsg(request, "msg_0017"),
		CommonUtil.getLangMsg(request, "msg_0018"),
		CommonUtil.getLangMsg(request, "msg_0019"),
		CommonUtil.getLangMsg(request, "msg_0020"),
		CommonUtil.getLangMsg(request, "msg_0021")
};

String[] areaList = {
		CommonUtil.getLangMsg(request, "msg_0022"),
		CommonUtil.getLangMsg(request, "msg_0023"),
		CommonUtil.getLangMsg(request, "msg_0024"),
		CommonUtil.getLangMsg(request, "msg_0025"),
		CommonUtil.getLangMsg(request, "msg_0026"),
		CommonUtil.getLangMsg(request, "msg_0027"),
		CommonUtil.getLangMsg(request, "msg_0028"),
		CommonUtil.getLangMsg(request, "msg_0029"),
		CommonUtil.getLangMsg(request, "msg_0030"),
		CommonUtil.getLangMsg(request, "msg_0031"),
		CommonUtil.getLangMsg(request, "msg_0032"),
		CommonUtil.getLangMsg(request, "msg_0033"),
		CommonUtil.getLangMsg(request, "msg_0034"),
		CommonUtil.getLangMsg(request, "msg_0035"),
		CommonUtil.getLangMsg(request, "msg_0036"),
		CommonUtil.getLangMsg(request, "msg_0037"),
		CommonUtil.getLangMsg(request, "msg_0038"),
		CommonUtil.getLangMsg(request, "msg_0039"),
		CommonUtil.getLangMsg(request, "msg_0040")
};


String[] estateList = {
		CommonUtil.getLangMsg(request, "msg_0041"),
		CommonUtil.getLangMsg(request, "msg_0042"),
		CommonUtil.getLangMsg(request, "msg_0043"),
		CommonUtil.getLangMsg(request, "msg_0044")
};

String[] residenceList = {
		CommonUtil.getLangMsg(request, "msg_0045"),
		CommonUtil.getLangMsg(request, "msg_0046"),
		CommonUtil.getLangMsg(request, "msg_0047"),
		CommonUtil.getLangMsg(request, "msg_0048"),
		CommonUtil.getLangMsg(request, "msg_0049")
};

String[] marriageList = {
		CommonUtil.getLangMsg(request, "msg_0050"),
		CommonUtil.getLangMsg(request, "msg_0051"),
		CommonUtil.getLangMsg(request, "msg_0052")
};

String[] childrenList = {
		CommonUtil.getLangMsg(request, "msg_0053"),
		CommonUtil.getLangMsg(request, "msg_0054"),
		CommonUtil.getLangMsg(request, "msg_0055"),
		CommonUtil.getLangMsg(request, "msg_0056")
};

String[] carList = {
		CommonUtil.getLangMsg(request, "msg_0057"),
		CommonUtil.getLangMsg(request, "msg_0058"),
		CommonUtil.getLangMsg(request, "msg_0059"),
		CommonUtil.getLangMsg(request, "msg_0060"),
		CommonUtil.getLangMsg(request, "msg_0061"),
		CommonUtil.getLangMsg(request, "msg_0062")
};


String[] yearIncomeList = {
		CommonUtil.getLangMsg(request, "msg_0063"),
		CommonUtil.getLangMsg(request, "msg_0064"),
		CommonUtil.getLangMsg(request, "msg_0065"),
		CommonUtil.getLangMsg(request, "msg_0066"),
		CommonUtil.getLangMsg(request, "msg_0067"),
		CommonUtil.getLangMsg(request, "msg_0068")
};


String[] healthDetailList = {
		CommonUtil.getLangMsg(request, "msg_0069"),
		CommonUtil.getLangMsg(request, "msg_0070"),
		CommonUtil.getLangMsg(request, "msg_0071"),
		CommonUtil.getLangMsg(request, "msg_0072"),
		CommonUtil.getLangMsg(request, "msg_0073"),
		CommonUtil.getLangMsg(request, "msg_0074"),
		CommonUtil.getLangMsg(request, "msg_0075"),
		CommonUtil.getLangMsg(request, "msg_0076"),
		CommonUtil.getLangMsg(request, "msg_0077"),
		CommonUtil.getLangMsg(request, "msg_0078"),
		CommonUtil.getLangMsg(request, "msg_0079"),
		CommonUtil.getLangMsg(request, "msg_0080"),
		CommonUtil.getLangMsg(request, "msg_0081"),
		CommonUtil.getLangMsg(request, "msg_0082"),
		CommonUtil.getLangMsg(request, "msg_0083"),
		CommonUtil.getLangMsg(request, "msg_0084"),
		CommonUtil.getLangMsg(request, "msg_0085"),
		CommonUtil.getLangMsg(request, "msg_0086")
};

String[] likeFieldList = {
		CommonUtil.getLangMsg(request, "msg_0087"),
		CommonUtil.getLangMsg(request, "msg_0088"),
		CommonUtil.getLangMsg(request, "msg_0089"),
		CommonUtil.getLangMsg(request, "msg_0090"),
		CommonUtil.getLangMsg(request, "msg_0091"),
		CommonUtil.getLangMsg(request, "msg_0092"),
		CommonUtil.getLangMsg(request, "msg_0093"),
		CommonUtil.getLangMsg(request, "msg_0094"),
		CommonUtil.getLangMsg(request, "msg_0095"),
		CommonUtil.getLangMsg(request, "msg_0096"),
		CommonUtil.getLangMsg(request, "msg_0097"),
		CommonUtil.getLangMsg(request, "msg_0098"),
		CommonUtil.getLangMsg(request, "msg_0099"),
		CommonUtil.getLangMsg(request, "msg_0100"),
		CommonUtil.getLangMsg(request, "msg_0101"),
		CommonUtil.getLangMsg(request, "msg_0102"),
		CommonUtil.getLangMsg(request, "msg_0103"),
		CommonUtil.getLangMsg(request, "msg_0104"),
		CommonUtil.getLangMsg(request, "msg_0105"),
		CommonUtil.getLangMsg(request, "msg_0106"),
		CommonUtil.getLangMsg(request, "msg_0107"),
		CommonUtil.getLangMsg(request, "msg_0108"),
		CommonUtil.getLangMsg(request, "msg_0109"),
		CommonUtil.getLangMsg(request, "msg_0110"),
		CommonUtil.getLangMsg(request, "msg_0111"),
		CommonUtil.getLangMsg(request, "msg_0112"),
		CommonUtil.getLangMsg(request, "msg_0113"),
		CommonUtil.getLangMsg(request, "msg_0114"),
		CommonUtil.getLangMsg(request, "msg_0115")
};
%>
<% if(flagAdInfo.equals("true")) { %>
  <div class="alert alert-success" role="alert"><spring:message code="msg_0116"/>
  </div>
<% } else { %>
  <form id="required-info-form" role="form" >
    <div class="form-group row">
      <label for="job" class="col-md-2"><%=CommonUtil.getLangMsg(request, "msg_0117")%></label>
      <div class="col-md-10">
        <select name="job" id="job" class="form-control" required="required">
          <option value="" readonly><%=CommonUtil.getLangMsg(request, "msg_0118")%></option>
          <% for(String iter : jobList) { %>
          <option value="<%= iter %>">
            <%= iter %>
          </option>
          <% } %>
        </select>
      </div>
    </div>
    <div class="form-group row">
      <label for="area" class="col-md-2"><%=CommonUtil.getLangMsg(request, "msg_0119")%></label>
      <div class="col-md-10">
        <select name="area" id="area" class="form-control" required="required">
          <option value="" readonly><%=CommonUtil.getLangMsg(request, "msg_0118")%></option>
          <% for(String iter : areaList) { %>
          <option value="<%= iter %>">
            <%= iter %>
          </option>
          <% } %>
        </select>
      </div>
    </div>
    <div class="form-group row">
      <label for="real-estate" class="col-md-2"><%=CommonUtil.getLangMsg(request, "msg_0120")%></label>
      <div class="col-md-10">
        <% for(String iter : estateList) { %>
        <label class="radio-label">
        <input name="estate" type="radio" value="<%= iter %>" />
          <%= iter %>
        </label>
        <% } %>
      </div>
    </div>
    <div class="form-group row">
      <label for="residenceType" class="col-md-2"><%=CommonUtil.getLangMsg(request, "msg_0121")%></label>
      <div class="col-md-10">
        <select
          name="residence"
          id="residenceType"
          class="form-control"
          required="required">
          <option value="" readonly><%=CommonUtil.getLangMsg(request, "msg_0118")%></option>
          <% for(String iter : residenceList) { %>
          <option value="<%= iter %>">
            <%= iter %>
          </option>
          <% } %>
        </select>
      </div>
    </div>
    <div class="form-group row">
      <label for="marriage" class="col-md-2"><%=CommonUtil.getLangMsg(request, "msg_0122")%></label>
      <div class="col-md-10">
        <% for(String iter : marriageList) { %>
        <label class="radio-label">
        	<input name="marriage" type="radio" value="<%= iter %>" />
          <%= iter %>
        </label>
        <% } %>
      </div>
    </div>
    <div class="form-group row">
      <label for="childrenCnt" class="col-md-2"><%=CommonUtil.getLangMsg(request, "msg_0123")%></label>
      <div class="col-md-10">
        <% for(String iter : childrenList) { %>
        <label class="radio-label">
        	<input name="children" type="radio" value="<%= iter %>" />
          <%= iter %>
        </label>
        <% } %>
      </div>
    </div>
    <div class="form-group row">
      <label for="carType" class="col-md-2"><%=CommonUtil.getLangMsg(request, "msg_0124")%></label>
      <div class="col-md-10">
        <select name="car" id="carType" class="form-control" required="required">
          <option value="" readonly><%=CommonUtil.getLangMsg(request, "msg_0118")%></option>
          <% for(String iter : carList) { %>
          <option value="<%= iter %>">
            <%= iter %>
          </option>
          <% } %>
        </select>
      </div>
    </div>
    <div class="form-group row">
      <label for="year-income" class="col-md-2"><%=CommonUtil.getLangMsg(request, "msg_0125")%></label>
      <div class="col-md-10">
        <select
          name="yearIncome"
          id="year-income"
          class="form-control"
          required="required">
          <option value="" readonly><%=CommonUtil.getLangMsg(request, "msg_0118")%></option>
          <% for(String iter : yearIncomeList) { %>
          <option value="<%= iter %>">
            <%= iter %>
          </option>
          <% } %>
        </select>
      </div>
    </div>
    <div class="form-group row">
      <label for="health" class="col-md-2"><%=CommonUtil.getLangMsg(request, "msg_0126")%></label>
      <div class="col-md-10">
        <input
          name="healthFlag"
          type="radio"
          name="healthCheck"
          id="health-ok"
          value="N"
          style="display:none;"
        />
        <label for="health-ok" class="radio-label"><%=CommonUtil.getLangMsg(request, "msg_0127")%></label>
        <input
          name="healthFlag"
          type="radio"
          name="healthCheck"
          id="health-not-ok"
          value="Y"
          style="display:none;"
          required
        />
        <label for="health-not-ok" class="radio-label"><%=CommonUtil.getLangMsg(request, "msg_0128")%></label>
        <select
          name="healthDetail"
          id="health-detail"
          class="form-control"
          style="display: none;">
          <option value="" readonly><%=CommonUtil.getLangMsg(request, "msg_0118")%></option>
          <% for(String iter : healthDetailList) { %>
          <option value="<%= iter %>">
            <%= iter %>
          </option>
          <% } %>
        </select>
      </div>
    </div>
    <div class="form-group row">
      <label for="email" class="col-md-2"><%=CommonUtil.getLangMsg(request, "msg_0129")%></label>
      <div class="col-md-10">
        <input
          name="email"
          type="email"
          id="email"
          class="form-control"
          required="required"
        />
      </div>
    </div>
    <div class="form-group row like-field-wrapper">
      <label for="like-field" class="col-md-2"><%=CommonUtil.getLangMsg(request, "msg_0130")%></label>
      <span><%=CommonUtil.getLangMsg(request, "msg_0131")%></span>
      <div class="col-xs-12 col-md-10" id="selected-list"></div>
      <div class="col-xs-12 clearfix like-field-table">
        <% for(String iter: likeFieldList) { %>
        <div
          class="col-xs-6 col-sm-4 col-md-3 like-field-item"
          data-item="<%= iter %>"><%= iter %></div>
        <% } %>
      </div>
    </div>
    <button
      type="submit"
      class="btn btn-primary col-xs-12 col-md-4 col-md-offset-4"
      id="ad-info-save-btn">
      <%=CommonUtil.getLangMsg(request, "msg_0132")%>
    </button>
  </form>
<% } %>