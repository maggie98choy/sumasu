<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>QQ SearchResults</title>
<!-- Stylesheets -->
<link type="text/css" rel="stylesheet" href="bootstrap/css/bootstrap.css">
<style>
html,body {
	background: url(images/profile.jpg) no-repeat center center fixed;
	-webkit-background-size: cover; /* For WebKit*/
	-moz-background-size: cover; /* Mozilla*/
	-o-background-size: cover; /* Opera*/
	background-size: cover; /* Generic*/
}
.borderless td {
    border: none;
}
.table-nonfluid {
   width: auto;
}

</style>
</head>
<%@ page
	import="com.queryquest.rest.jersey.domain.SearchResult,java.util.ArrayList;"%>
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
				<li><a href="search.jsp">Travel</a></li>
				<li><a href="">Sports</a></li>
				<li><a href="">Music</a></li>
				<li><a href="">Events</a></li>
				<li class="active"><a href="preferences.jsp">Preferences</a></li>
				<li><a href="">Sign Off</a></li>
			</ul>
			&nbsp; <a href="https://www.facebook.com/citystorysf"
				title="Become a fan"><img src="images/facebook.jpg" height="50"
				width="50"></a>
		</nav>
	<br>
	<br>
	<br>
	<br>
	<span style="color: white">
	<form name="form1" method="post" action="preferences">
	<h3>Select your favorite Activity</h3>
	<table class="table borderless table-nonfluid">  
	<tr> 
		
		<td> <input type="checkbox" name="activity" value="Aquarium"/>  Aquarium </td>
		<td><input type="checkbox" name="activity" value="Archery"/>  Archery</td>
		<td><input type="checkbox" name="activity" value="Art"/>  Arts </td>
		<td> <input type="checkbox" name="activity" value="Beaches"/>  Beach </td>
		
	</tr>
	<tr>
		
		<td><input type="checkbox" name="activity" value="Beauty Spa"/>  Beauty & Spas </td>
		<td><input type="checkbox" name="activity" value="Campground"/> Camp Grounds  </td>
		<td><input type="checkbox" name="activity" value="Casino"/> Casino  </td>
		<td><input type="checkbox" name="activity" value="Events this weekend"/> Events this weekend </td>
		
	</tr>
	
	<tr>
		<td><input type="checkbox" name="activity" value="Fishing"/>  Fishing </td>	
		<td><input type="checkbox" name="activity" value="Fitness Gyms"/>  Fitness  </td>
		<td><input type="checkbox" name="activity" value="Golf"/>  Golf  </td>
		<td><input type="checkbox" name="activity" value="Hiking"/>  Hiking </td>
		
	</tr>
		
	<tr>
	<tr>
		
		<td><input type="checkbox" name="activity" value="Museum"/>  Museums  </td>
		<td><input type="checkbox" name="activity" value="Nightlife"/>Night Life  </td>
		<td><input type="checkbox" name="activity" value="Rv park"/>  RV Parks</td>	
		<td> <input type="checkbox" name="activity" value="Scuba"/> Scuba </td>
		
	</tr>
		<tr>
		<td><input type="checkbox" name="activity" value="Shopping"/> Shopping </td>	
		<td><input type="checkbox" name="activity" value="Sport bar"/> Sports Bars </td>
		<td><input type="checkbox" name="activity" value="Winery"/>  Wineries </td>
		<td><input type="checkbox" name="activity" value="Zoo"/>  Zoo </td>
		
		</tr>
		
		
	
</table>
<button type="submit" value="submit" class="btn">Save</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<button type="reset" value="reset"  class="btn">Clear</button>
</form>


</div></span>
	
</body>
</html>