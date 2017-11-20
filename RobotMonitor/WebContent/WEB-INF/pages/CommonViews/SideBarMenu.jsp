<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/resources/images/Robots/J1.png" var="RobotPosition"/>
<c:url value="/resources/images/load_s.png" var="Load"/>
<c:url value="/resources/images/speed.png" var="Speed"/>
<c:url value="/resources/images/torque.png" var="Torque"/>
	<div class="row">
		<div class="col-sm-3 col-md-12">
			<label>手臂: </label>
			<label id="robotNO">TCR-1000C-001</label>
		</div>
	</div>
	
	<div class="row">
		<div class="col-sm-3 col-md-12">
			<label>狀態: </label>
			<label id="robotStatus"></label>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-3 col-md-12">
			<label>溫度: </label>
			<label id="robotTempature"></label>
		</div>
	</div>

	
	<div class="row">
		<div class="col-sm-3 col-md-5">
			<div class="dropdown">
  				<button class="btn btn-default dropdown-toggle" type="button" id="RobotMenu" data-toggle="dropdown" aria-expanded="true">
    				手臂列表
    				<span class="caret"></span>
  				</button>
  				<ul id="RobotList" class="dropdown-menu" role="menu" aria-labelledby="RobotMenu">
    				<li role="presentation"><a role="menuitem" tabindex="-1" href="#" class="robotListOption">Action</a></li>
    				<li role="presentation"><a role="menuitem" tabindex="-1" href="#" class="robotListOption">Another action</a></li>
    				<li role="presentation"><a role="menuitem" tabindex="-1" href="#" class="robotListOption">Something else here</a></li>
    				<li role="presentation"><a role="menuitem" tabindex="-1" href="#" class="robotListOption">Separated link</a></li>
  				</ul>
			</div>
		</div>
		
		<div class="col-sm-3 col-md-5">
			<div class="dropdown">
  				<button class="btn btn-default dropdown-toggle" type="button" id="RobotMonitorMenu" data-toggle="dropdown" aria-expanded="true">
    				監測資訊
    				<span class="caret"></span>
  				</button>
  				<ul id="RobotList" class="dropdown-menu" role="menu" aria-labelledby="RobotMonitorMenu">
    				<!-- <li role="presentation"><a role="menuitem" tabindex="-1" href="#" class="robotMonitorOption">電控箱溫度</a></li> -->
    				<li role="presentation"><a role="menuitem" tabindex="-1" href="#" class="robotMonitorOption">機械手臂負載</a></li>
    				<li role="presentation"><a role="menuitem" tabindex="-1" href="#" class="robotMonitorOption">機械手臂轉速</a></li>
    				<li role="presentation"><a role="menuitem" tabindex="-1" href="#" class="robotMonitorOption">機械手臂轉矩</a></li>
  				</ul>
			</div>
		</div>
		
	</div>
	
	<!-- <div class="row">
		<div class="col-sm-3 col-md-6">
			<div class="dropdown">
  				<button class="btn btn-default dropdown-toggle" type="button" id="RobotMonitorMenu" data-toggle="dropdown" aria-expanded="true">
    				監測資訊
    				<span class="caret"></span>
  				</button>
  				<ul id="RobotList" class="dropdown-menu" role="menu" aria-labelledby="RobotMonitorMenu">
    				<li role="presentation"><a role="menuitem" tabindex="-1" href="#" class="robotMonitorOption">電控箱溫度</a></li>
    				<li role="presentation"><a role="menuitem" tabindex="-1" href="#" class="robotMonitorOption">機械手臂負載</a></li>
    				<li role="presentation"><a role="menuitem" tabindex="-1" href="#" class="robotMonitorOption">機械手臂轉速</a></li>
    				<li role="presentation"><a role="menuitem" tabindex="-1" href="#" class="robotMonitorOption">機械手臂轉矩</a></li>
  				</ul>
			</div>
		</div>
	</div> -->
	
	<!-- <div class="row">
		<div class="col-sm-3 col-md-6">
			<div class="dropdown">
  				<button class="btn btn-default dropdown-toggle" type="button" id="RobotPositionMenu" data-toggle="dropdown" aria-expanded="true">
    				位置
    				<span class="caret"></span>
  				</button>
  				<ul class="dropdown-menu" role="menu" aria-labelledby="RobotPositionMenu">
    				<li role="presentation"><a role="menuitem"  href="#" class="robotPositionOption">J1</a></li>
    				<li role="presentation"><a role="menuitem"  href="#" class="robotPositionOption">J2</a></li>
    				<li role="presentation"><a role="menuitem" href="#" class="robotPositionOption">J3</a></li>
    				<li role="presentation"><a role="menuitem"  href="#" class="robotPositionOption">J4</a></li>
  					<li role="presentation"><a role="menuitem" href="#" class="robotPositionOption">J5</a></li>
  					<li role="presentation"><a role="menuitem" href="#" class="robotPositionOption">J6</a></li>
  				</ul>
			</div>
		</div>	
	</div> -->
	<div class="row">
		<div class="col-sm-3 col-md-12">
			<br>
			<img id="robotImg" src="${RobotPosition}" class="img-responsive">
		</div>
	</div>

		

