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
<script>
 window.fbAsyncInit = function() 
 {
  FB.init({
    appId      : '359402037530890',
    status     : true, // check login status
    cookie     : true, // enable cookies to allow the server to access the session
    xfbml      : true  // parse XFBML
  });
 
  // Additional init code here
  FB.getLoginStatus(function(response) 
  {
   if (response.status === 'connected') {
   // connected
   

   } else if (response.status === 'not_authorized') {
    // not_authorized
    ///log function will change div visibility to "visible"
    //log();

   } else {
   // not_logged_in
    //log();
   }
  });//closes fb.getLoginStatus
 };//closes fbAsyncInit
 
 (function(d){
	   var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
	   if (d.getElementById(id)) {return;}
	   js = d.createElement('script'); js.id = id; js.async = true;
	   js.src = "//connect.facebook.net/en_US/all.js";
	   ref.parentNode.insertBefore(js, ref);
	  }(document));


function fblogout()
{	
	 FB.logout(function(response) 
	{ 		 	 
	});
}
</script>
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
				
				<li class="active"><a href="preferences.jsp">Preferences</a></li>
				<li><a href="signOff" onclick="fblogout()" >Sign Off</a></li>
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
		
		<td> <input type="checkbox" name="activity" value="Aquariums"/>  Aquarium </td>
		<td><input type="checkbox" name="activity" value="Archery"/>  Archery</td>
		<td> <input type="checkbox" name="activity" value="Beaches"/>  Beach </td>
	    <td> <input type="checkbox" name="activity" value="Wineries"/>  Wineries </td>
		
		
	</tr>
	<tr>
		
		<td><input type="checkbox" name="activity" value="Campgrounds"/> Camp Grounds  </td>
		<td><input type="checkbox" name="activity" value="Casinos"/> Casino  </td>
		<td><input type="checkbox" name="activity" value="Fishing"/>  Fishing </td>	
		
	
	</tr>
	
	<tr>
		<td><input type="checkbox" name="activity" value="Golf"/>  Golf  </td>
		<td><input type="checkbox" name="activity" value="Hiking"/>  Hiking </td>
	    <td><input type="checkbox" name="activity" value="Museums"/>  Museums  </td>
				<td><input type="checkbox" name="activity" value="Zoos"/>  Zoo </td>
		
		
	</tr>
		
	<tr>
	<tr>
		
		<td><input type="checkbox" name="activity" value="Nightlife"/>Night Life  </td>
		<td> <input type="checkbox" name="activity" value="Scuba"/> Scuba </td>
				<td><input type="checkbox" name="activity" value="Shopping"/> Shopping </td>	
		
		
	</tr>

		
		
	
</table>
<button type="submit" value="submit" class="btn">Save</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<button type="reset" value="reset"  class="btn">Clear</button>
</form>


</div></span>
	
</body>
</html>