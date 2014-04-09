package com.queryquest.rest.jersey;

import com.queryquest.rest.jersey.SearchAirFlight;
import com.travelport.uapi.unit1.Lesson3;

public class RunSearchAirFlight 
{
	
	//public RunSearchAirFlight(String air1, String air2)
	public static void main(String[] argv)
	{
		//SearchAirFlight searchAirFlight = new SearchAirFlight();
		//searchAirFlight.SearchAirTix(air1, air2);
		Lesson3 l3 = new Lesson3();
	    l3.RunLesson3("SFO", "LGA", 7, 10);
	}
	
}
