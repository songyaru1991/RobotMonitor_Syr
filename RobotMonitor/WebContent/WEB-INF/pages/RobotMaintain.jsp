<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<c:url value="/resources/css/bootstrap.css" var="bootstrapCSS"/>
	<c:url value="/resources/css/Robot.Maintain.css" var="robotMaintainCSS"/>
	<c:url value="/resources/js/jquery-1.11.2.min.js" var="jqueryJS"/>
	<c:url value="/resources/js/bootstrap.js" var="bootstrapJS"/>
	<c:url value="/resources/js/Project/Common.Utils.js" var="commonJS"/>
	<c:url value="/resources/js/Project/Robot.Maintain.js" var="maintainJS"/>
	<!-- CSS -->
	<link href="${bootstrapCSS}" rel="stylesheet" type="text/css">
	<link href="${robotMaintainCSS}" rel="stylesheet" type="text/css">
	<!-- javaScript -->
	<script src="${jqueryJS}" type="text/javascript"></script>
	<script src="${bootstrapJS}" type="text/javascript"></script>
	<script src="${commonJS}" type="text/javascript"></script>
	<script src="${maintainJS}" type="text/javascript"></script>
<title>FoxLink 機械手臂監控平台</title>
</head>
<body>
	<jsp:include page="./CommonViews/Header.jsp"/>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-2 col-md-2 col-lg-2 Side">
				<jsp:include page="./CommonViews/SideBarMenu.jsp"/>
			</div>
			<div class="col-sm-10 col-md-10 col-lg-10 Content">
				<h3>機械手臂基本資訊</h3>
				<div class="row">
					<div class="col-sm-10 col-md-10 col-lg-10">
					</div>
					<div class="col-sm-2 col-md-2 col-lg-2">
						<!-- 新增按鈕 -->
						<a id="btnAddNewMemberByGroups" href="#CreateRobotDialog" role="button"
						class="btn btn-primary btn-sm" data-toggle="modal">新增手臂</a>
					</div>
				</div>
				
				<div class="row">
					<table id="robotMasterTable" class="table table-hover table-responsive " >
          			<thead>
          				<tr>
          					<th>手臂序號</th>
          					<th>所在廠區</th>
          					<th>所在車間</th>
          					<th>線別</th>
          					<th>生產機種</th>
          					<th></th>
          				</tr>
          			</thead>
          			<tbody></tbody>
          		</table>
				</div>
				<div class="row">
					<div id="RobotMasterPagination" class="col-sm-12 col-sm-12">
					
					</div>
				</div>
			</div>
		</div>
	</div>
	<nav class="navbar navbar-inverse navbar-fixed-bottom" role="navigation">
		<div class="container-fluid">
			<div class="row"></div>
			<div class="row">
				<div class="col-sm-2 col-md-2 col-lg-4"></div>
				<div class="col-sm-8 col-md-8 col-lg-6">
					<p style="color:#F5F5F5;font-size:20px">&copy; FoxLink IT. All rights reserved. HQ: 王鈞毅(21250)  KS: 宋亞茹(55684)</p>
				</div>
				<div class="col-sm-2 col-md-2 col-lg-2"></div>
			</div>
		</div>
	</nav>
	<jsp:include page="./PopOutViews/CreateNewRobot.jsp"/>
</body>
</html>