<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>QQ Travel</title>
<!-- Stylesheets -->
<link type="text/css" rel="stylesheet"
	href="bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" />

<script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script type="text/javascript" src="date/moment.js"></script>

<link rel="stylesheet" href="date/bootstrap-datetimepicker.min.css" />
<script type="text/javascript"
	src="date/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="date/jquery-2.1.0.min.js"></script>
<link rel="stylesheet" href="date/bootstrap-datetimepicker.css" />
<script type="text/javascript" src="date/bootstrap-datetimepicker.js"></script>



<style>
#round-image {
	border-radius: 100%;
	-o-border-radius: 100%;
	-webkit-border-radius: 100%;
	-moz-border-radius: 100%;
}

html,body {
	background: url(images/search.jpg) no-repeat center center fixed;
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

	<form class="well navbar-search offset5" style="background-color: #fdf5e6;" action="search" method="post">
		&nbsp;&nbsp;
		<p>Where do you want to go?</p>
		<input type="text" class="search-query span6"
			placeholder="2 days hiking to Las Vegas" name="searchTerm"
			id="searchTerm">
		<button type="submit" value="submit" class="btn btn-inverse">
			Search <span class="glyphicon glyphicon-search"></span>
		</button>
		<br>
		
			
			<details>
			<summary> Date</summary>
				<br>

				<div class="row">
					<div class='col-sm-4'>
						Start Date: <input type='text' class="form-control"
							id='datetimepicker6' name="datetimepicker6" data-date-format="YYYY/MM/DD" />
					</div>
					<script type="text/javascript">
						$(function() {
							$('#datetimepicker6').datetimepicker();
							var today = new Date();
							$('#datetimepicker6').data("DateTimePicker")
									.setMinDate(today);
						});
					</script>
				</div>


				<div class="row">
					<div class='col-sm-4'>
						End Date: <input type='text' class="form-control"
							id='datetimepicker7' name="datetimepicker7" data-date-format="YYYY/MM/DD" />
					</div>
					<script type="text/javascript">
						$(function() {
							$('#datetimepicker7').datetimepicker();
							var today = new Date();
							$('#datetimepicker7').data("DateTimePicker")
									.setMinDate(today);
						});
					</script>
				</div>


			</details>
		
	</form>
	<!--JS for current date-->




</body>
</html>