package com.queryquest.rest.jersey;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.queryquest.rest.jersey.Utility.MongoQueries;
import com.queryquest.rest.jersey.domain.Rating;
import com.queryquest.rest.jersey.domain.SearchResult;

/**
 * Servlet implementation class searchresults
 */
@WebServlet("/searchresults")
public class searchresults extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public searchresults() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String star= request.getParameter("star");
		 System.out.println(star);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		int star= Integer.parseInt(request.getParameter("star"));
		//int index = (Integer)session.getAttribute("index");
		int index = Integer.parseInt(request.getParameter("index"));

		
	    ArrayList<SearchResult> recomSearchList= (ArrayList<SearchResult>)session.getAttribute("recom_search_results");
	    ArrayList<SearchResult> ratedSearchList=(ArrayList<SearchResult>)session.getAttribute("rated_search_results");
	    int distance = (Integer)session.getAttribute("distance");
	    String travelDestination= (String)session.getAttribute("traveldestination");
		String currentLocation=(String)session.getAttribute("currentLocation");
		String startDate =(String)session.getAttribute("startdate");
		String endDate= (String)session.getAttribute("enddate");
		
	    Rating rating = new Rating();
	    rating.setEmail((String)session.getAttribute("email"));
	     if(index >= 100 ){ // RECOMMENDED RESULTS ARE RATED 
	    	// System.out.println("I M HERE");
	    	 index=index-100;
	    	 SearchResult searchResult = recomSearchList.get(index);
	    	 recomSearchList.remove(index);
	    	 searchResult.setNoOfStars(star);
	    	 searchResult.setRecommended(false);
	    	ratedSearchList.add(searchResult);
	    	 
	 	    rating.setEmail((String)session.getAttribute("email"));
	 	    rating.setBusinessName(searchResult.getName());
		    rating.setRating(searchResult.getNoOfStars());
	    	 
	     }
	     else { //RATED RESULTS ARE ALTERED
	    	 //System.out.println("I M HERE TOO");
	    	 ratedSearchList.get(index).setNoOfStars(star);
	    	 rating.setBusinessName(ratedSearchList.get(index).getName());
	    	 rating.setRating(ratedSearchList.get(index).getNoOfStars());
	     }
	    	 MongoQueries mongoQueries = new MongoQueries();
	    	 mongoQueries.mongoConnect(2);
	    	 mongoQueries.mongoUpdateStar(rating);
	    /*SearchResult searchResult = searchList.get(index);
	    searchResult.setNoOfStars(star);
	    searchList.remove(index);
	    searchList.add(index,searchResult);*/
	    
	    //System.out.println(searchList.toString());
	   // SortSearchResults sort = new SortSearchResults();
	    request.setAttribute("distance", distance);
	    request.setAttribute("travelDestination",travelDestination);
		request.setAttribute("currentLocation",currentLocation);
		request.setAttribute("startdate",startDate);
		request.setAttribute("enddate",endDate);
	    request.setAttribute("recom_search_results", (recomSearchList));
	    request.setAttribute("rated_search_results", (ratedSearchList));
		request.getRequestDispatcher("searchresults.jsp").forward(request, response);
		 
	}

}
