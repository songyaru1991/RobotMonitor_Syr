<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<c:url value="/resources/css/bootstrap.css" var="bootstrapCSS"/>
	<c:url value="/resources/css/signin.css" var="signinCSS"/>
	<c:url value="/resources/js/jquery-1.11.2.min.js" var="jqueryJS"/>
	<c:url value="/resources/js/bootstrap.js" var="bootstrapJS"/>
	<c:url value="/resources/images/title.png" var="foxlinkTitle"/>
	<c:url value="/resources/js/jquery.validate.min.js" var="jqueryValidate"/>
	<c:url value="/resources/js/offcanvas.js" var="offcanvas"/>
	<!-- CSS -->
	<link href="${ bootstrapCSS }" rel="stylesheet" type="text/css"/>
	<link href="${signinCSS}" rel="stylesheet" type="text/css"/>
	<!-- javaScript -->
	<script src="${jqueryJS}" type="text/javascript"></script>
	<script src="${bootstrapJS}" type="text/javascript"></script>
	<script src="${jqueryValidate}" type="text/javascript"></script>
	<script src="${offcanvas}" type="text/javascript"></script>
	<script>
		$(document).ready(function(){
			$('#loginForm').validate();
		});
	</script>
	<c:if test="${not empty executeResult }">
		<script>alert('${executeResult}');</script>
	</c:if>
	<title>FoxLink 機械手臂監控平台</title>
</head>
<body>
	<div class="container-fluid">
		 <nav class="navbar navbar-default navbar-fixed-top header" role="navigation">
		 </nav>

		<div class="Foxlinklogo row">
				<div class="col-md-4 col-lg-4"></div>
				<div class="col-md-4 col-lg-4">
					<img alt="" src="${foxlinkTitle}" class="img-rounded" width="550" height="200">
				</div>
				<div class="col-md-4 col-lg-4"></div>
		</div>
		<div class="loginForm row">
				<div class="col-md-4 col-lg-4">
				</div>
				<div class="col-md-4 col-lg-4">
					<div class="row">
						<c:if test="${not empty error}">
							<div class="error">${error}</div>
						</c:if>
						<c:if test="${not empty msg}">
							<div class="msg">${msg}</div>
						</c:if>
					</div>
					<div class="row">
						<form id="loginForm" action="<c:url value='/login' />" method="POST" class="form-signin" role="form">
							<input type="text" id="username" name="username" class="form-control required" placeholder="工號">
							<input type="password" id="password" name="password" class="form-control required" placeholder="密碼">
							<button type="submit" class="btn btn-lg btn-default btn-block">登入</button>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						</form> 
					</div>
				</div>
				<div class="col-md-4 col-lg-4"></div>
		</div>
		
		<nav class="navbar navbar-default navbar-fixed-bottom footer" role="navigation">
			<div class="col-sm-5 col-md-5 col-lg-5"></div>
			<div class="col-sm-2 col-md-2 col-lg-2">
				<p class="Signoff" style="color:white;font-size:16px">資訊工程處  ｜  技術發展處</p>
			</div>
			<div class="col-sm-5 col-md-5 col-lg-5"></div>
		</nav>	
	</div>
</body>
</html>