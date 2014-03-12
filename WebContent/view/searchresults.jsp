<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<%@ page import="com.queryquest.rest.jersey.domain.SearchResult"%>
<%@ page import="java.util.ArrayList" %>
<body>
<% 
ArrayList<SearchResult> searchList = new ArrayList<SearchResult>();
        searchList= (ArrayList<SearchResult>)request.getAttribute("search_results");
		if (searchList != null)	{
			for(int i=0; i<searchList.size();i++){
				SearchResult search = new SearchResult();	
				search = searchList.get(i);
%>
		<h3><%= search.getName() %></h3>
		<% out.println(search.getAddress()); %><br />
		<% if(search.getPhoneNo() != null) 
	    	out.println(search.getPhoneNo()); %><br />
		<a href="<%out.println(search.getURL()); %>"><%= search.getURL() %></a>

	     <% } %>
	     <% } %>
</body>
</html>