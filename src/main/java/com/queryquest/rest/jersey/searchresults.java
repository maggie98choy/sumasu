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
		//System.out.println("INDEX "+index);
	    int division = (Integer)session.getAttribute("division");
	    ArrayList<String> actList = (ArrayList<String>)session.getAttribute("actList");
	    ArrayList<SearchResult> searchList= (ArrayList<SearchResult>)session.getAttribute("search_results");
	    
	    searchList.get(index).setNoOfStars(star);
	   
	    Rating rating = new Rating();
	    rating.setEmail((String)session.getAttribute("email"));
	    rating.setBusinessName(searchList.get(index).getName());
	    rating.setRating(searchList.get(index).getNoOfStars());
	    MongoQueries mongoQueries = new MongoQueries();
	    mongoQueries.mongoConnect(2);
	    mongoQueries.mongoUpdateStar(rating);
	    /*SearchResult searchResult = searchList.get(index);
	    searchResult.setNoOfStars(star);
	    searchList.remove(index);
	    searchList.add(index,searchResult);*/
	    
	    System.out.println(searchList.toString());
	    request.setAttribute("search_results", searchList);
		request.setAttribute("activities", actList);
		request.setAttribute("division", division);
		request.getRequestDispatcher("searchresults.jsp").forward(request, response);
		 
	}

}
