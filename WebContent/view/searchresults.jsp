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
	background: url(images/travel.jpg) no-repeat center center fixed;
	-webkit-background-size: cover; /* For WebKit*/
	-moz-background-size: cover; /* Mozilla*/
	-o-background-size: cover; /* Opera*/
	background-size: cover; /* Generic*/
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
				<li class="active"><a href="search.jsp">Travel</a></li>
				<li><a href="">Sports</a></li>
				<li><a href="">Music</a></li>
				<li><a href="">Events</a></li>
				<li><a href="https://www.surveymonkey.com/s/FBSYGL5">Feedback</a></li>

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

<form class="navbar-search" action="search" method="post">
		
		<br> <input type="text" class="search-query span6"
			placeholder="Enter an activity" name="activity" id="activity">&nbsp;&nbsp;
		<button type="submit" value="submit" class="btn btn-inverse">
			Search <span class="glyphicon glyphicon-search"></span>
		</button>
	</form>
	<br><br><br><br><br>
<% 
ArrayList<SearchResult> searchList = new ArrayList<SearchResult>();
        searchList= (ArrayList<SearchResult>)request.getAttribute("search_results");
		if (searchList != null)	{
			for(int i=0; i<searchList.size();i++){
				SearchResult search = new SearchResult();	
				search = searchList.get(i);
%>
		<table class="table table-bordered table-striped ">
		<tr><th> 
		 <%= search.getName() %><br>
		 <span style="font-weight:normal;">
		<% out.println(search.getAddress()); %>
		<% if(search.getPhoneNo() != null) 
	    	out.println("Phone : "+search.getPhoneNo()); %><br>
		<a href="<%out.println(search.getURL()); %>"><%= search.getURL() %></a></span></th></tr>

	     <% } %>
	     <% } %></table>	</div>
</body>
</html>