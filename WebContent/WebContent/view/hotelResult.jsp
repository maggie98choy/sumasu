<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>QQ HotelResults</title>
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
	background: url(images/hotel.jpg) no-repeat center center fixed;
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
	import="java.util.ArrayList,com.queryquest.rest.jersey.domain.QHotel;"%>
<body>
	<!-- JS-->
	<script src="bootstrap/js/bootstrap.js"></script>

	<!-- navigation bar -->
	<div class="container">
		<!-- Navigation bar -->
		<nav class="navbar navbar-inner navbar-fixed-top" role="navigation">
		<ul class="nav navbar-nav nav-pills">
			<li><img src="images/logo.png" width="50"
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
				<div class="panel panel-danger">
					<div class="panel-heading">
						<h3 class="panel-title">Hotel Reservations</h3>
					</div>
					<div class="panel-body">
					
					<% ArrayList<QHotel> hotelList = new ArrayList<QHotel>();
						hotelList = (ArrayList<QHotel>)request.getAttribute("hotelList");	
						String travelDestination = (String) request.getAttribute("travelDestination");%>

		<div class="panel-group" id="accordion">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion"
											href="#collapseOne"> Hotels in <%out.println(travelDestination);%> </a>
									</h4>
								</div>
								
								<div id="collapseOne" class="panel-collapse collapse">
									<div class="panel-body" style="background-color: #FFDAE6;">
										<!--Body-->
										<div class="well" style="background-color: #FFF5F5;">
											<div class="row">
										<%
		if (hotelList != null)	
		{
			for(int i=0; i<hotelList.size();i++)
			{
				QHotel hotel = new QHotel();	
				hotel = hotelList.get(i);	%>			
				<strong>
			   <% out.println(hotel.getHotelName());%><br> </strong>
			    <%out.println(hotel.getHotelAddress()); %>
			    <%if (hotel.getHotelMinAmt() != null) 
			    {%><br> <%out.println("Price range "+hotel.getHotelMinAmt()+" to "+hotel.getHotelMaxAmt()); %><br><br>
			    <%}
			    else
			    {%>
				<br><br>				    
			    <%}
			
		    } 
	 }
	 %>
</div>
			</div>
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