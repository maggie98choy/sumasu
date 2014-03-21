package com.queryquest.rest.jersey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.queryquest.rest.jersey.Utility.MongoQueries;

/**
 * Servlet implementation class preferences
 */
@WebServlet("/preferences")
public class preferences extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public preferences() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		String activities[]= (String[])request.getParameterValues("activity");
		//System.out.println("Activites- "+activities[0]);
		if(activities != null){
			 MongoQueries mongoQuery = new MongoQueries();
		     mongoQuery.mongoConnect(1);
			
		     HttpSession session = request.getSession();
		     session = request.getSession(true);
			 String email=(String) session.getAttribute("email");
			 boolean isFirstTime =(boolean) session.getAttribute("firstTime");
			 mongoQuery.mongoUpdateActivities(activities, email,isFirstTime);
			 session.setAttribute("firstTime", false);
		}
		 request.getRequestDispatcher("search.jsp").forward(request, response);

		
	}

}
