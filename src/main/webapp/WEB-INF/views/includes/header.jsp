<%@page import="org.lovi.psdemo.models.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;">
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no">

<!-- floating action button dependencies -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/normalize/3.0.1/normalize.min.css">
<link rel="stylesheet"
	href="http://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
<link
	href='http://fonts.googleapis.com/css?family=Raleway:100,200,300,400'
	rel='stylesheet' type='text/css'>
<link href="resources/floatingButton/mfb.css" rel="stylesheet">
<script src="resources/floatingButton/lib/modernizr.touch.js"></script>
<script src="resources/floatingButton/mfb.js"></script>
<!-- end floating action button dependencies -->

<!-- bootstrap dependencies -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet"
	type="text/css" />
<script src="resources/js/jquery.min.js" type="text/javascript"></script>
<script src="resources/js/bootstrap.min.js" type="text/javascript"></script>
<script src="resources/js/maps.js" type="text/javascript"></script>
<script src="resources/js/material.min.js" type="text/javascript"></script>
<script src="resources/js/ripples.min.js" type="text/javascript"></script>
<link href="resources/css/bootstrap-material-datetimepicker.css"
	rel="stylesheet" type="text/css" />
<link href="resources/css/material-fullpalette.min.css" rel="stylesheet"
	type="text/css" />
<link href="resources/css/ripples.min.css" rel="stylesheet"
	type="text/css" />
<link href="resources/css/roboto.min.css" rel="stylesheet"
	type="text/css" />
<!-- end bootstrap dependencies -->
<script
	src="cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js"
	type="text/javascript"></script>
<link
	href="cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.min.css" />
<link href="resources/css/custom.css" rel="stylesheet" type="text/css" />
<script src="resources/js/moment.min.js" type="text/javascript"></script>
<script src="resources/js/urljs.js" type="text/javascript"></script>
<script src="resources/js/bootstrap-material-datetimepicker.js"
	type="text/javascript"></script>

<script src="resources/js/effect.js" type="text/javascript"></script>
<script src="resources/js/trsNotify.js" type="text/javascript"></script>



<style type="text/css">
.mfb-component__button--main, .mfb-component__button--child {
	background-color: #009688;
}
</style>
<title>Perera and Sons</title>
</head>
<body>
	
	<!-- navigation bar -->
	<div class="navbar navbar-material-light-blue-500">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-material-light-blue-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="javascript:void(0)">
				<img style="width: 35px;height: 35px" src="resources/images/pnslogo.png">
			</a>
		</div>
		<div
			class="navbar-collapse collapse navbar-material-light-blue-collapse">
			
			<ul class="nav navbar-nav">
				<li class="active">
					<a href="javascript:void(0)">Perera and Sons</a>
				</li>
			</ul>
			
			<% 
				User user = (User) request.getAttribute("user");
				if(user != null){
			%>
			
			<ul class="nav navbar-nav navbar-right">
				<li class="active">
					<a href="javascript:void(0)"><%=user.getFirstName() %></a>
				</li>
				<li class="active">
					<a href="sign_out">Sign out</a>
				</li>
			</ul>
			
			<% } else{%>
			
			<ul class="nav navbar-nav navbar-right">
				<li class="active">
					<a href="javascript:void(0)"><span class="mdi-action-help"></span></a>
				</li>
			</ul>

			<%} %>
			
			
		</div>
	</div>
	<!-- end navigation bar -->