<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>QQ Itinerary Results</title>
<!-- Stylesheets -->
<link type="text/css" rel="stylesheet" href="bootstrap/css/bootstrap.css">
<link type="text/css" rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<link href="star/css/star-rating.css" media="all" rel="stylesheet" type="text/css"/>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="star/js/star-rating.js" type="text/javascript"></script>
<style>
html,body {
	background: url(images/travel.jpg) no-repeat center center fixed;
	-webkit-background-size: cover; /* For WebKit*/
	-moz-background-size: cover; /* Mozilla*/
	-o-background-size: cover; /* Opera*/
	background-size: cover; /* Generic*/
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

		<!-- CANNT DO THIS HERE : GIVES ERROR  
  <form class="navbar-search" action="search" method="post">
		
		<br> <input type="text" class="search-query span6"
			placeholder="Enter an activity" name="activity" id="activity">&nbsp;&nbsp;
		<button type="submit" value="submit" class="btn btn-inverse">
			Search <span class="glyphicon glyphicon-search"></span>
		</button>
	</form>
	<br><br><br><br><br> -->
	</div>
	<% 
	ArrayList<QItinerary> itiList = new ArrayList<QItinerary>();
	itiList = (ArrayList<QItinerary>)request.getAttribute("itiList");
	String airport1 = (String) request.getAttribute("airport1");
	//int division = (Integer)request.getAttribute("division");
    
	%><font size="6" color="white">	Air Flight</font><br><br><br>
	
	<font size="3" color="white">
	<%
	int j=0;
		if (itiList != null)	
		{
			for(int i=0; i<itiList.size();i++)
			{
				QItinerary itin = new QItinerary();	
				itin = itiList.get(i);				
				%>
			    AIR Departing from <% out.println(itin.getOrigin()); %> to <% out.println(itin.getDestination()); %> on 
				<% out.println(itin.getDepartureTime()); %><br>
					&nbsp;&nbsp; Flight <% out.println(itin.getCarrier()); %><% out.println(itin.getFlightNo()); %> Flight time: <% out.println(itin.getFlightTime()); %> minutes<br>
						      &nbsp;&nbsp;&nbsp;&nbsp; Arrive <% out.println(itin.getArrivalTime()); %><br>
				<% if(itin.getNonStop())
				{
				 %> <br> <%
				}
				%>
				<%		      
				if (itin.getDestination().equals((String) request.getAttribute("airport1")))
				{%>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Base Price: <% out.println(itin.getPrice());%><br>
					<%out.println("------------------------------------------------------------------------"); %><br><br>
					
			    <%}
			
		   } 
	 } %></font>

</body>
</html>