<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<c:url value="/resources/css/bootstrap.css" var="bootstrapCSS"/>
	<c:url value="/resources/css/StatusMonitor.css" var="statusMonitorCSS"/>
	<c:url value="/resources/js/jquery-1.11.2.min.js" var="jqueryJS"/>
	<c:url value="/resources/js/bootstrap.js" var="bootstrapJS"/>
	<c:url value="/resources/js/Project/Common.Utils.js" var="commonJS"/>
	<c:url value="/resources/js/Project/Status.Monitor.js" var="statusMonitorJS"/>
	<c:url value="/resources/js/HighCharts/highcharts.js" var="highChartsJS"/>
	<c:url value="/resources/js/HighCharts/modules/exporting.js" var="exportingJS"/>
	<!-- CSS -->
	<link href="${bootstrapCSS}" rel="stylesheet" type="text/css">
	<link href="${statusMonitorCSS}" rel="stylesheet" type="text/css">
	<!-- javaScript -->
<!-- 	<script src="https://code.highcharts.com/highcharts.js"></script>
	<script src="https://code.highcharts.com/modules/exporting.js"></script> -->
	<script src="${jqueryJS}" type="text/javascript"></script>
	<script src="${bootstrapJS}" type="text/javascript"></script>
	<script src="${commonJS}" type="text/javascript"></script>
	<script src="${statusMonitorJS}" type="text/javascript"></script>
	<script src="${highChartsJS}" type="text/javascript"></script>
	<script src="${exportingJS}" type="text/javascript"></script>
	
<title>FoxLink 機械手臂監控平台</title>
</head>
<body>
	<jsp:include page="./CommonViews/Header.jsp"/>
	<div class="contaimer-fluid">
		<div class="row">
			<div class="col-sm-2 col-md-2 col-lg-2 Side">
				<jsp:include page="./CommonViews/SideBarMenu.jsp"/>
			</div>
			
			<div class="col-sm-10 col-md-10 col-lg-10 Content">
				<div class="row placeholders">
            		<div class="col-md-7 col-sm-7 col-lg-7 placeholder">
              			<div id="trendChart" class="col-sm-12 col-md-12 col-lg-12">
						</div>
           		 	</div>
           		 	<div class="col-md-5 col-sm-5 col-lg-5 placeholder">
          				<div class="row">
          					<h3>手臂基本資訊</h3>
          					<table id="robotMasterTable" class="table table-hover table-responsive " >
          						<thead>
          							<tr>
          								<th>手臂序號</th>
          								<th>所在廠區</th>
          								<th>所在車間</th>
          								<th>線別</th>
          								<th>生產機種</th>
          							</tr>
          						</thead>
          						<tbody></tbody>
          					</table>
          				</div>
          				<div class="row">
          					<h3>機械手臂當前狀態</h3>
           		 			<table id="robotStatusTable" class="table table-hover table-responsive " >
           		 				<thead>
           		 					<tr>
           		 						<th>時間</th>
           		 						<th>機械手臂狀態</th>
           		 						<th>錯誤訊息</th>
           		 					</tr>
           		 				</thead>
           		 				<tbody>
           		 			
           		 				</tbody>
           		 			</table>
          				</div>
           		 	</div>
          		</div>
          		<div class="row tables">
           		 	<div class="col-sm-6 col-md-6 col-lg-6">
           		 		<h3>機械手臂當前資訊</h3>
           		 			<table id="robotCurrentDataTable" class="table table-hover table-responsive">
           		 				<thead>
           		 					<tr>
           		 						<th>時間</th>
           		 						<th>最大值</th>
           		 						<th>平均值</th>
           		 						<th>最小值</th>
           		 					</tr>
           		 				</thead>
           		 				<tbody>
           		 				</tbody>
           		 			</table>
           		 	</div>
           		 	<div class="col-sm-6 col-md-6 col-lg-6 placeholder">
           		 		<h3>機械手臂近十分鐘資訊</h3>
           		 		<table id="robotHistoryDataTable" class="table table-hover table-responsive">
           		 			<thead>
           		 				<tr>
           		 					<th>時間</th>
           		 					<th>最大值</th>
           		 					<th>平均值</th>
           		 					<th>最小值</th>
           		 				</tr>
           		 			</thead>
           		 			<tbody>
           		 			</tbody>
           		 		</table>
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
					<p style="color:#F5F5F5">&copy; Cheng Uei Precision Industry Co., Ltd. All rights reserved. HQ: 王鈞毅(21250)  KS: 宋亞茹(55684)</p>
				</div>
				<div class="col-sm-2 col-md-2 col-lg-2"></div>
			</div>
		</div>
	</nav>
</body>
</html>