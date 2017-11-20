<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<c:url value="/resources/css/bootstrap.css" var="bootstrapCSS"/>
	<c:url value="/resources/js/jquery-1.11.2.min.js" var="jqueryJS"/>
	<c:url value="/resources/js/bootstrap.js" var="bootstrapJS"/>
	<c:url value="/resources/js/Project/Common.Utils.js" var="commonJS"/>
	<c:url value="/resources/css/Monitor.Seven.Axis.css" var="monitorSevenAxisCSS"/>
	<c:url value="/resources/js/Project/Monitor.Seven.Axis.js" var="monitorSevenAxisJS"/>
	<c:url value="/resources/js/HighCharts/highcharts.js" var="highchartsJS"/>
	<c:url value="/resources/js/HighCharts/themes/dark-unica.js" var="darkThemeJS"/>
	<c:url value="/resources/js/HighCharts/highcharts-more.js" var="highchartsMoreJS"/>
	<c:url value="/resources/js/HighCharts/modules/exporting.js" var="exportingJS"/>
	<c:url value="/resources/js/HighCharts/modules/solid-gauge.js" var="solidGaugeJS"/>
	<!-- CSS -->
	<link href="${bootstrapCSS}" rel="stylesheet" type="text/css">
	<link href="${monitorSevenAxisCSS}" rel="stylesheet" type="text/css">
	<!-- javaScript -->
	<script src="${highchartsJS}" type="text/javascript"></script>
	<script src="${darkThemeJS}" type="text/javascript"></script>
	<script src="${highchartsMoreJS}" type="text/javascript"></script>
	<script src="${exportingJS}" type="text/javascript"></script>
	<script src="${solidGaugeJS}" type="text/javascript"></script>
	<script src="${jqueryJS}" type="text/javascript"></script>
	<script src="${bootstrapJS}" type="text/javascript"></script>
	<script src="${commonJS}" type="text/javascript"></script>
	<script src="${monitorSevenAxisJS}" type="text/javascript"></script>
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
				<div class="row ChartRow">
					<div id="J1" class="gauge col-sm-4 col-md-4 col-lg-4">
					
					</div>
					<div id="J2" class="gauge col-sm-4 col-md-4 col-lg-4">
					
					</div>
					<div id="J3" class="gauge col-sm-4 col-md-4 col-lg-4">
					
					</div>
				</div>
				<div class="row ChartRow">
					<div id="J4" class="gauge col-sm-4 col-md-4 col-lg-4">
					
					</div>
					
					<div id="J5" class="gauge col-sm-4 col-md-4 col-lg-4">
					
					</div>
					<div id="J6" class="gauge col-sm-4 col-md-4 col-lg-4">
					
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
	<jsp:include page="./PopOutViews/MonitorTrendChart.jsp"/>
</body>
</html>