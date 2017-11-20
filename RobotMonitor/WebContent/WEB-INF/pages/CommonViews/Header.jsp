<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/logout" var="logoutUrl" />
<c:url value="/resources/images/title_s.png" var="TitleImage"/>
<c:url value="/resources/images/logout.png" var="LogoutImage"/>
<c:url value="/resources/images/setup.png" var="SetUpImage"/>
<c:url value="/resources/images/watching.png" var="MonitorImage"/>
<nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid">
        <div id="navbar" class="navbar-collapse collapse">
        	<form id="logoutForm" action="${logoutUrl }" method="post">
        		<div class="col-md-8 col-lg-8">
        			<img alt="" src="${TitleImage}"  class="img-responsive titleImage" >
        		</div>
        		<div class="col-md-4 col-lg-4">
        			<div id="logoutDiv" class="col-md-3 col-lg-3">
            		</div>
        			<div id="setupDiv" class="col-md-3 col-lg-3">
            					<a id="setupBtn" href="#" onclick="location.replace('../Maintain/')"  class="btn  navbar-btn">
            						<img src="${SetUpImage}" class="img-responsive logOutImage">
            					</a>
            				</div>
            				<div id="MonitorDiv" class="col-md-3 col-lg-3">
            					<a id="setupBtn"  href="#" onclick="location.replace('../Monitor/SevenAxis')"  class="btn  navbar-btn">
            						<img src="${MonitorImage}" class="img-responsive logOutImage">
            					</a>
            				</div>
            				<div id="logoutDiv" class="col-md-3 col-lg-3">
            					<a href="#" onclick="document.forms['logoutForm'].submit(); return false;" class="btn  navbar-btn">
            					<img src="${LogoutImage}" class="img-responsive logOutImage"></a>
            				</div>
            				
        		</div>
          		
          		<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
         	 </form>
          <script>
 			$('#logoutForm').submit(function(event){
			if(!confirm('確定離開?')){
				event.preventDefault();
			}
			});
		</script>
        </div>
      </div>
    </nav>