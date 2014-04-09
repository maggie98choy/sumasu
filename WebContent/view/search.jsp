<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>QQ Travel</title>
<!-- Stylesheets -->
<link type="text/css" rel="stylesheet" href="bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" />
 <script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<style>
#round-image {
	border-radius: 100%;
	-o-border-radius: 100%;
	-webkit-border-radius: 100%;
	-moz-border-radius: 100%;
}

html,body {
	background: url(images/travel.jpg) no-repeat center center fixed;
	-webkit-background-size: cover; /* For WebKit*/
	-moz-background-size: cover; /* Mozilla*/
	-o-background-size: cover; /* Opera*/
	background-size: cover; /* Generic*/
}
</style>
<style type="text/css">
p {
	font-family: fantasy, cursive, Lucida;
	font-size: 18px;
}
</style>
</head>


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
				width="50"></a>
		</nav>
	</div>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>

	<!-- Search box -->

	<form class="well navbar-search offset5" action="search" method="post">
		&nbsp;&nbsp;
		<p>Where do you want to go?</p>
		<input type="text" class="search-query span6" placeholder="2 days hiking to Las Vegas" name="searchTerm" id="searchTerm">
		<button type="submit" value="submit" class="btn btn-inverse">
			Search <span class="glyphicon glyphicon-search"></span>
			</button><br>
			
	 	<details>
		<summary>Date</summary>
      	<br>Start Date: <input type="date" id="startdate" name="startdate"> 
      	<br>End  Date:  &nbsp;<input type="date" id="enddate" name="enddate">
   		</details>
</form>

	
</body>
</html>