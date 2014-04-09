<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>QQ Hotel Results</title>
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
	import="java.util.ArrayList,com.queryquest.rest.jersey.domain.QHotel;"%>
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
	ArrayList<QHotel> hotelList = new ArrayList<QHotel>();
	hotelList = (ArrayList<QHotel>)request.getAttribute("hotelList");	
	String travelDestination = (String) request.getAttribute("travelDestination");
    
	%><font size="6" color="white">Hotels in <%out.println(travelDestination);%></font><br><br><br>
	
	<font size="3" color="white">
	<%
		if (hotelList != null)	
		{
			for(int i=0; i<hotelList.size();i++)
			{
				QHotel hotel = new QHotel();	
				hotel = hotelList.get(i);				
			
			    out.println(hotel.getHotelName());%><br> 
			    <%out.println(hotel.getHotelAddress()); %><br>
			    from <%out.println(hotel.getHotelMinAmt()); %> to <%out.println(hotel.getHotelMaxAmt()); %><br><br>
			
		   <%} 
	 }
	 %>
	</font>
	
</body>
</html>