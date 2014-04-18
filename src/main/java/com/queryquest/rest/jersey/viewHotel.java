package com.queryquest.rest.jersey;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.queryquest.rest.jersey.Utility.itineraryAttrAnalysis;
import com.queryquest.rest.jersey.domain.QHotel;
import com.queryquest.rest.jersey.domain.QItinerary;
import com.queryquest.rest.jersey.RunSearchAirFlight;
import com.queryquest.rest.jersey.SearchAirFlight;
import com.travelport.service.hotel_v17_0.HotelFaultMessage;

/**
 * Servlet implementation class BuildItinerary
 */
@WebServlet("/viewHotel")
public class viewHotel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public viewHotel() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String travelDestination = "";
		String airport = "";
		Object hotelList = new ArrayList<QHotel> ();
		Date startDate = new Date();
		Date endDate = new Date();
		long returnDayFromToday = 0;
		long departureDayFromToday = 1 ;
		final int totalNumber=5;
		
		itineraryAttrAnalysis itinAnalysis = new itineraryAttrAnalysis();
		
		hotelList = null;
		
		ArrayList arrList = new ArrayList<QHotel> ();
		arrList = (ArrayList) session.getAttribute("hotelList");
		
		if ((!request.getParameter("travelDestination").equals(session.getAttribute("travelDestination"))) || arrList.isEmpty())
		{
		
			System.out.println("travel destination:" + request.getParameter("travelDestination"));
			if(request.getParameter("travelDestination") != null)
			{
				travelDestination = request.getParameter("travelDestination");
					
				try 
				{
					airport = itinAnalysis.airPortLookUp(travelDestination);
					System.out.println("airport: "+airport);
					} 
					catch (SAXException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParserConfigurationException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
							
				SearchHotel searchHotel = new SearchHotel();
				try 
				{
					hotelList = searchHotel.searchHotel(airport);
					System.out.println("hotelList:"+hotelList);
				} 
				catch (HotelFaultMessage e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		else
		{
			hotelList = session.getAttribute("hotelList");
			travelDestination = (String) session.getAttribute("travelDestination");
		}
		
			session.setAttribute("hotelList", hotelList);
		session.setAttribute("travelDestination", travelDestination);
		
		System.out.println("travel destination:" + travelDestination);
		request.setAttribute("hotelList", hotelList);
		request.setAttribute("travelDestination", travelDestination);
		request.getRequestDispatcher("hotelResult.jsp").forward(request, response);

	}
				
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
