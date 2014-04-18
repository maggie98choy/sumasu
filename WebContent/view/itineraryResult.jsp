<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>QQ FlightResults</title>
<!-- Stylesheets -->
<link type="text/css" rel="stylesheet"
	href="bootstrap/css/bootstrap.css">
<link type="text/css" rel="stylesheet"
	href="bootstrap/css/bootstrap.min.css">
<link href="star/css/star-rating.css" media="all" rel="stylesheet"
	type="text/css" />
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="star/js/star-rating.js" type="text/javascript"></script>
<style>
html,body {
	background: url(images/airport.jpg) no-repeat center center fixed;
	-webkit-background-size: cover; /* For WebKit*/
	-moz-background-size: cover; /* Mozilla*/
	-o-background-size: cover; /* Opera*/
	background-size: cover; /* Generic*/
}

.panel-heading a:after {
	font-family: 'Glyphicons Halflings';
	content: "\e114";
	float: right;
	color: grey;
}

hr{
color: wheat;
background-color: wheat;
height: 6px;
width: 100%;
}

.panel-heading a.collapsed:after {
	content: "\e080";
}
</style>
</head>
<%@ page
	import="com.queryquest.rest.jersey.domain.QItinerary,java.util.ArrayList,com.queryquest.rest.jersey.domain.QHotel;"%>
<body>
	<!-- JS-->
	<script src="bootstrap/js/bootstrap.js"></script>

	<!-- navigation bar -->
	<div class="container">
		<!-- Navigation bar -->
		<nav class="navbar navbar-inner navbar-fixed-top" role="navigation">
		<ul class="nav navbar-nav nav-pills">
			<li><img src="images/logo.png" alt="Citystory" width="50"
				height="50"></li>
			<li class="active"><a href="search.jsp">Travel</a></li>
			<li><a href="">Sports</a></li>
			<li><a href="">Music</a></li>
			<li><a href="">Events</a></li>
			<li><a href="preferences.jsp">Preferences</a></li>
			<li><a href="">Sign Off</a></li>
		</ul>
		&nbsp; <a href="https://www.facebook.com/citystorysf"
			title="Become a fan"><img src="images/facebook.jpg" height="50"
			width="50"></a> </nav>
		<br> <br> <br> <br>

		<!-- panel -->

		<div class="row">
			<div class="col-sm-11">
				<div class="panel panel-default panel-warning">
					<div class="panel-heading">
						<h3 class="panel-title">Flight Itinerary</h3>
					</div>
					<div class="panel-body">


						<div class="panel-group" id="accordion">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion"
											href="#collapseOne"> Availability </a>
									</h4>
								</div>
								<div id="collapseOne" class="panel-collapse collapse">
									<div class="panel-body" style="background-color: wheat;">
										<!--Body-->

										<%ArrayList<QItinerary> itiList = new ArrayList<QItinerary>();
											itiList = (ArrayList<QItinerary>) request.getAttribute("itiList");
											String airport1 = (String) request.getAttribute("airport1");
										//int division = (Integer)request.getAttribute("division");
										%>

										
										<div class="well" style="background-color:#FFF7EC;">
											<div class="row">
												
													<%int j = 0;
										if (itiList != null)
										{
											for (int i = 0; i < itiList.size(); i++) 
											{
 												QItinerary itin = new QItinerary();
 												itin = itiList.get(i);
									   %>
									  <br>
										<div class="col-md-6">
										From : 
										<strong><%out.println(itin.getOrigin()); %></strong><br> </div>
										<div class="col-md-4"> 
										To :
										<strong><%out.println(itin.getDestination());%></strong> </div>
										
										<div class="col-md-6"> 
										Departure :
										<%out.println(itin.getDepartureTime());%>
										<br> 
										</div>
										
										<div class="col-md-6"> 
										Arrival :
										<%out.println(itin.getArrivalTime());%><br>
										</div>
										<div class="col-md-6"> 
										Flight :
										<strong> <%out.println(itin.getCarrier());%>
										<%out.println(itin.getFlightNo());%></strong>
										<br>
										</div>
										<br> 
										<div class="col-md-4"> 
										Total time :
										<strong>
										<%out.println(itin.getFlightTime());%>
										minutes</strong></div><br>
										
										<%
											if (itin.getNonStop()) {%>
										<br>
										<%
 										}
 										%>
										<%
 											if (itin.getDestination().equals(
 											(String) request.getAttribute("airport1"))) {
 										%><br>
 										<div class="col-md-4"> 
										Price :
										<strong><%out.println(itin.getPrice());%></strong><br></div>
										<br> <br>
										
										<hr>
								
										<%
 	} 
 								

 		 } %>
 		 
 		</div>
			</div>
 	<% } %>										

										<!--end of 1st body-->
								
								</div>
							</div>
							</div>	


						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>