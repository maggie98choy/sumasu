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
@WebServlet("/viewItinerary")
public class viewItinerary extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public viewItinerary() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String currentLocation = "";
		String travelDestination = "";
		String airport1 = "";
		String airport2 = "";
		ArrayList<QItinerary> itiList = new ArrayList<QItinerary> ();
		ArrayList<QHotel> hotelList = new ArrayList<QHotel> ();
		Date startDate = new Date();
		Date endDate = new Date();
		long returnDayFromToday = 0;
		long departureDayFromToday = 1 ;
		final int totalNumber=5;
		
		itineraryAttrAnalysis itinAnalysis = new itineraryAttrAnalysis();
		
		itiList = null;
		hotelList = null;
		
		if(request.getParameter("currentLocation") != null)
		{
			currentLocation = request.getParameter("currentLocation");
			
			try 
			{
				airport1 = itinAnalysis.airPortLookUp(currentLocation);
				System.out.println("airport1: "+airport1);
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
		
			if(request.getParameter("travelDestination") != null)
			{
				travelDestination = request.getParameter("travelDestination");
				
				try 
				{
					airport2 = itinAnalysis.airPortLookUp(travelDestination);
					System.out.println("airport2: "+airport2);
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
				
				if(request.getParameter("startDate") != null)
				{
					try 
					{
						startDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("startDate"));
					} catch (ParseException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Date date = new Date();
					departureDayFromToday = TimeUnit.DAYS.convert(startDate.getTime() -date.getTime(), TimeUnit.MILLISECONDS);
					//System.out.println("departureDayFromToday: " +departureDayFromToday);
					if (departureDayFromToday==0)
					{
						departureDayFromToday = 1;
					}
					System.out.println("departure Day From Today:"+ departureDayFromToday);
				}
				
				if(request.getParameter("endDate") != null)
				{
					try 
					{
						endDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("endDate"));
					} catch (ParseException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					returnDayFromToday  = TimeUnit.DAYS.convert(endDate.getTime() - startDate.getTime(), TimeUnit.MILLISECONDS) + departureDayFromToday;
					System.out.println("Days:"+ returnDayFromToday);
	
				}
			}
			
			
			SearchAirFlight searchAirFlight = new SearchAirFlight();
			itiList = searchAirFlight.SearchAirTix(airport1, airport2, departureDayFromToday, returnDayFromToday);			
			
			/*try {
				Thread.sleep(15000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			*/
			/*SearchHotel searchHotel = new SearchHotel();
			try 
			{
				hotelList = searchHotel.searchHotel(airport2);
			} 
			catch (HotelFaultMessage e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			
		}
		
		request.setAttribute("itiList", itiList);
		request.setAttribute("hotelList", hotelList);
		request.setAttribute("airport1", airport1);
		//request.setAttribute("division", totalNumber/itiList.size());	
		request.getRequestDispatcher("itineraryResult.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
