<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>QQ SearchResults</title>
<!-- Stylesheets -->
<link type="text/css" rel="stylesheet"
	href="bootstrap/css/bootstrap.css">
<link type="text/css" rel="stylesheet"
	href="bootstrap/css/bootstrap.min.css">
<link href="star/css/star-rating.css" media="all" rel="stylesheet"
	type="text/css" />
	<link href="bootstrap/css/bootstrap.icon-large.min.css" rel="stylesheet">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="star/js/star-rating.js" type="text/javascript"></script>
<style>
html,body {
	background: url(images/travel.jpg) no-repeat center center fixed;
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

.panel-heading a.collapsed:after {
	content: "\e080";
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
			<li><a href="preferences.jsp">Preferences</a></li>

			<li><a href="">Sign Off</a></li>
		</ul>
		&nbsp; <a href="https://www.facebook.com/citystorysf"
			title="Become a fan"><img src="images/facebook.jpg" height="50"
			width="50"></a> </nav>
		<br> <br> <br> <br>

		<div class="row">
			<div class="col-sm-11">
				<div class="panel panel-default panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title">Places</h3>
					</div>
					<div class="panel-body">


						<div class="panel-group" id="accordion">
							<div class="panel panel-default panel-info">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion"
											href="#collapseOne"> New Adventures </a>
									</h4>
								</div>
								<div id="collapseOne" class="panel-collapse collapse">
									<div class="panel-body" style="background-color: #AFEEEE;">
										<!--Body-->
										<%
											ArrayList<SearchResult> recomSearchList = new ArrayList<SearchResult>();
											ArrayList<SearchResult> ratedSearchList = new ArrayList<SearchResult>();

											String startDate = (String) request.getAttribute("startdate");
											String endDate = (String) request.getAttribute("enddate");
											String currentLocation = (String) request
													.getAttribute("currentLocation");
											String travelDestination = (String) request
													.getAttribute("travelDestination");

											recomSearchList = (ArrayList<SearchResult>) request.getAttribute("recom_search_results");
											ratedSearchList = (ArrayList<SearchResult>) request.getAttribute("rated_search_results");
											String act = "";

											//*****************RECOMMENDED RESULTS **********
											if (recomSearchList != null) {
												for (int i = 0; i < recomSearchList.size(); i++) {
													SearchResult search = new SearchResult();
													search = recomSearchList.get(i);
													if (!act.equals(search.getActivity())) {
														act = search.getActivity();
										%>
										<h4 style="color: black;">
											<%
												out.println(act);
														}
											%>
										</h4>
												<div class="well"  style="background-color: #F0F8FF;">
												<div class="row">
												<div class="col-md-8">
												<strong><%=search.getName()%></strong> <br> <br>
												<%out.println("Address: " + search.getAddress());%><br>
  												<%if (search.getPhoneNo() != null)out.println("Phone: " + search.getPhoneNo());%><br><br>
												</div> 
												<div class="col-md-4">
												<!-- star rating --> 
												<% session.setAttribute("recom_search_results",recomSearchList);
											    session.setAttribute("rated_search_results",ratedSearchList);
												session.setAttribute("traveldestination",travelDestination);
												session.setAttribute("currentLocation",currentLocation);
												session.setAttribute("startdate",startDate);
												session.setAttribute("enddate",endDate);
												%>
												
													<form action="searchresults" method="post">
														<input type="hidden" id="index" name="index"
															value=<%=i + 100%>> <input id="input-21"
															name="star" type="number" class="rating"
															data-show-clear="false"
															data-star-captions='{"1": "Very Poor", "2": "Poor", "3": "Ok", "4": "Good", "5": "Excellent"}'
															data-show-caption=“true” data-size="xs"><button class="btn btn-primary btn-xs">Save</button>
													</form><strong> <%if (search.isRecommended())out.println("QQ Rating: "+ search.getRecommendedRating());%> </strong>
												</div>
												</div>
											
												<div class="row"> 
												<% int distance = (Integer) request.getAttribute("distance");
												if (distance > 500) 
												{
												%>
												<div class="col-md-2">
												<font size="3"><a href="viewItinerary?currentLocation=<%=currentLocation%>&travelDestination=<%=travelDestination%>&startDate=<%=startDate%>&endDate=<%=endDate%>">Airflight <span class="icon-large icon-plane"></span></a></font>
												</div>
												<%} 
												else 
												{%>
												<div class="col-md-4">
												<font size="3"><a href="mapDirection.jsp?currentLocation=<%=currentLocation%>&travelDestination=<%=travelDestination%>"  target="_blank">Map Directions <span class="icon-large icon-car"></span></a></font>
												</div>
												<%} 
												session.setAttribute("distance",distance);
												%>
												<div class="col-md-2">
												<font size="3"><a href="viewHotel?travelDestination=<%=travelDestination%>&startDate=<%=startDate%>&endDate=<%=endDate%>">Hotel <span class="icon-large icon-cutlery"></span></a></font>
												</div>											
												
												</div>																													
												</div>
											<%
												}
											%>
											<%
												}
											%>

										<!--end of 1st body-->
									</div>
								</div>
							</div>

							<!--second panel-->
							<div class="panel panel-default panel-info">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion"
											href="#collapseTwo"> Old Memories </a>
									</h4>
								</div>
								<div id="collapseTwo" class="panel-collapse collapse">
									<div class="panel-body" style="background-color: #AFEEEE;">
										<!--second body-->
										<%
											if (ratedSearchList != null) {
												act = "";
												for (int i = 0; i < ratedSearchList.size(); i++) {
													SearchResult search = new SearchResult();
													search = ratedSearchList.get(i);
													String activity = ratedSearchList.get(i).getActivity();
													if (!act.equals(activity)) {
										%>
										<h4 style="color: black;">
											<%
												out.println(activity);
															act = new String(activity);
														} else
															System.out.println(search.toString());
											%>
										</h4>
												<div class="well" style="background-color: #F0F8FF;">
												<div class="row">
												<div class="col-md-8">
													<strong><%=search.getName()%></strong><br> <br>
													<%out.println("Address: "+search.getAddress());%><br> 
													<%if (search.getPhoneNo() != null) out.println("Phone: " + search.getPhoneNo());%><br><br>
													</div>
													<div class="col-md-4">
													<!-- star rating -->
												    <%session.setAttribute("recom_search_results",recomSearchList); 
													session.setAttribute("rated_search_results",ratedSearchList);
													session.setAttribute("traveldestination",travelDestination);
												session.setAttribute("currentLocation",currentLocation);
												session.setAttribute("startdate",startDate);
												session.setAttribute("enddate",endDate);%>
													<form action="searchresults" method="post">
														<input type="hidden" id="index" name="index" value=<%=i%>>
														<input id="input-21" name="star"
															value="<%=search.getNoOfStars()%>" type="number"
															class="rating" data-show-clear="false"
															data-star-captions='{"1": "Very Poor", "2": "Poor", "3": "Ok", "4": "Good", "5": "Excellent"}'
															data-show-caption=“true” data-size="xs">
														<button class="btn btn-primary btn-xs">Save</button>
													</form> 
													</div>
													</div>
												<div class="row"> 
												<% int distance = (Integer) request.getAttribute("distance");
												if (distance > 500) 
												{
												%>
												<div class="col-md-2">
												<font size="3"><a href="viewItinerary?currentLocation=<%=currentLocation%>&travelDestination=<%=travelDestination%>&startDate=<%=startDate%>&endDate=<%=endDate%>">Airflight <span class="icon-large icon-plane"></span></a></font>
												</div>
												<%} 
												else 
												{%>
												<div class="col-md-4">
												<font size="3"><a href="mapDirection.jsp?currentLocation=<%=currentLocation%>&travelDestination=<%=travelDestination%>"  target="_blank">Map Directions <span class="icon-large icon-car"></span></a></font>
												</div>
												<%}
												session.setAttribute("distance",distance);%>
												<div class="col-md-2">
												<font size="3"><a href="viewHotel?travelDestination=<%=travelDestination%>&startDate=<%=startDate%>&endDate=<%=endDate%>">Hotel <span class="icon-large icon-cutlery"></span></a></font>
												</div>											
												
												</div>														
												</div>

											<%
												}
											%>
											<%
												}
											%>
									


										<!--end of second body-->
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