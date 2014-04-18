package com.queryquest.rest.jersey;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.queryquest.rest.jersey.domain.QItinerary;
import com.travelport.tutorial.support.WSDLService;
import com.travelport.uapi.unit1.AirReq;
import com.travelport.uapi.unit1.Helper;
import com.travelport.schema.air_v18_0.*;
import com.travelport.schema.common_v15_0.PointOfSale;
import com.travelport.schema.common_v15_0.ResponseMessage;
import com.travelport.service.air_v18_0.AirFaultMessage;


public class SearchAirFlight
{
	public static final String MYAPP = "tut";
    static protected String username = "Universal API/uAPI-758840054";
    static protected String password = "A2sWEdZfkSeazc5ehaHQg4eqB";
    static protected String gdp = "1V";
    static protected String target_branch= "P106464";
	
	
	public ArrayList<QItinerary> SearchAirTix (String or, String dest, long departureDayFromToday, long returnDayFromToday) 
	{
	
		String origin = or;
		String destination = dest;
		int departureDaysInFuture = (int) departureDayFromToday;
		int returnDaysInFuture = (int) returnDayFromToday;
		
		//Collection<QItinerary> itiList;
		ArrayList<QItinerary> itiList = new ArrayList<QItinerary> ();
				
		LowFareSearchAsynchReq req = new LowFareSearchAsynchReq();
		// this creates the request parameters... and doesn't care if the
		// request is synchronous or asynch
		createLowFareSearchWithRail(req, origin, destination, departureDaysInFuture, returnDaysInFuture);
		AirSearchRsp rsp = null;
			
		try {
			System.out.println("waiting for first response from a provider...");
			//WSDLService.airShopAsync.showXML(true);
			LowFareSearchAsynchRsp lowCostRsp = WSDLService.airShopAsync.get().service(req);
			HashMap<String, Long> partMap = new HashMap<String, Long>();

			List<AsyncProviderSpecificResponse> specificResponses = lowCostRsp
					.getAsyncProviderSpecificResponse();

			// print out what we got from initial response... this is to print
			// the summary for all providers and set up the partMap for use in
			// our loop below
			for (Iterator<AsyncProviderSpecificResponse> specIter = specificResponses
					.iterator(); specIter.hasNext();) 
			{
				AsyncProviderSpecificResponse asynchRsp = (AsyncProviderSpecificResponse) specIter
						.next();
				partMap.put(asynchRsp.getProviderCode(), asynchRsp
						.getTotalParts().longValue());
				System.out.println("Provider " + asynchRsp.getProviderCode()
						+ " has a total of " + asynchRsp.getTotalParts()
						+ " parts");
						
			}

			// prepare for the loop... we print first and ask for the response
			// second we have to setup the values before entering the first time
			String searchId = lowCostRsp.getSearchId();
			String currentProvider = lowCostRsp.getProviderCode();
			long currentPart = lowCostRsp.getPartNumber().longValue();
			rsp = lowCostRsp; // so we can print it out
			while (partMap.isEmpty() == false) 
			{

				System.out.println("++++++++++++++++++++\n"
						+ "Response is from provider " + currentProvider + ":"
						+ " part " + currentPart + " of "
						+ partMap.get(currentProvider));
				
				//printSomeExampleResults(destination, rsp, 2);
				
				
				Helper.AirSegmentMap allAirSegments = null;

				if (rsp.getAirSegmentList() != null) 
				{
					allAirSegments = Helper.createAirSegmentMap(rsp.getAirSegmentList()
							.getAirSegment());
				}
				
				int airTotal = rsp.getAirPricingSolution().size();
				int count = 0;
				QItinerary iti = new QItinerary();
				System.out.println("Total number solutions: " + airTotal + " air.");

				// walk all the solutions and create a printable itinerary for each one
				// then print them out
				List<AirPricingSolution> airs = rsp.getAirPricingSolution();
				for (Iterator<AirPricingSolution> iterator = airs.iterator(); iterator
						.hasNext();) 
				{
					/*System.out.println("Example AIR Solution " + (count + 1) + " of "
							+ airTotal);
						*/	
					AirPricingSolution soln = (AirPricingSolution) iterator.next();
					//PrintableItinerary i = new PrintableItinerary(soln, allAirSegments,
					//		destination);
					
					//StringBuilder result = new StringBuilder();
					//Formatter fmt = new Formatter(result, Locale.US);
				
					List<AirSegmentRef> segKeys = soln.getAirSegmentRef();
					for (Iterator<AirSegmentRef> iterator1 = segKeys.iterator(); iterator1.hasNext();) 
					{
						AirSegmentRef airSegmentRef = (AirSegmentRef) iterator1.next();
						//looking the leg by its key
						AirSegment leg = allAirSegments.getByRef(airSegmentRef);
						
						//printLeg(leg, fmt);
						Date dep = Helper.dateFromISO8601(leg.getDepartureTime());
						
						//System.out.println("AIR Departing from "+ leg.getOrigin() + " to " +leg.getDestination() +" on " +  dep);
															
						System.out.println("Flight " + leg.getCarrier() + "[" + leg.getFlightNumber() + "]" + 
											". Flight time:" + leg.getFlightTime() + ". Arrive: " + leg.getArrivalTime());
						
						
						if (currentProvider.equalsIgnoreCase("1V"))
						{
							iti.setOrigin(leg.getOrigin());
							iti.setArrivalTime(leg.getArrivalTime());
							iti.setCarrier(leg.getCarrier());
							iti.setDepartureTime(dep);
							iti.setDestination(leg.getDestination());
							iti.setFlightTime(leg.getFlightTime());
							iti.setPrice(soln.getTotalPrice());
							iti.setFlightNo(leg.getFlightNumber());
							if ((!leg.getDestination().equals(origin)) && (!leg.getDestination().equals(destination)))
							{
								iti.setNonStop(false);
							}
							else
							{
								iti.setNonStop(true);
							}
							//System.out.println(iti.toString());
							itiList.add(new QItinerary(iti.getOrigin(), iti.getDestination(), iti.getDepartureTime(), iti.getCarrier(), iti.getFlightTime(), iti.getArrivalTime(), 
									iti.getPrice(), iti.getFlightNo(), iti.getNonStop())); 							
							
						}
						
						String roundTripTurnaround = destination;
						/*if (leg.getDestination().equals(roundTripTurnaround)) 
						{

				
						}
						 */
								
					}					
					++count;
					if (count == 2) {
						break;
					}
				}
				
				//System.out.println("\n-------------------");
							
				long total = partMap.get(currentProvider);
				if (total == currentPart) {
					// finished with that one
					partMap.remove(currentProvider);
					// more providers?
					if (partMap.isEmpty()) {
						continue; // just get out of the loop
					}
					// change to next provider from the partMap
					currentProvider = partMap.keySet().iterator().next();
					currentPart = 1;
				} else {
					// more parts left on this provider
					currentPart++;
				}
				// start the retreival of either the next part or the 1st part
				// from the next provider

				// just to show we can do something else while we wait
				try {
					System.out.println("Sleeping 1 secs before trying to "
							+ "request part " + currentPart + " from "
							+ currentProvider);
					Thread.sleep(1 * 1000);
				} catch (InterruptedException ignored) {
					/* wont happen */
				}
			    
				// sleep is finished, run the request for more data...
				RetrieveLowFareSearchReq retrieve = new RetrieveLowFareSearchReq();
				retrieve.setSearchId(searchId);
				retrieve.setProviderCode(currentProvider);
				retrieve.setPartNumber(BigInteger.valueOf(currentPart));
				AirReq.addPointOfSale(retrieve, MYAPP);
				WSDLService.airRetrieve.get();
				rsp = WSDLService.airRetrieve.get().service(retrieve);
				checkForErrorMessage(rsp);
			}
		} catch (AirFaultMessage e) {
			System.err.println("Fault trying send request to travelport:"
					+ e.getMessage());
		}
		
		for(Iterator <QItinerary> it =itiList.iterator(); it.hasNext(); )
		{
			QItinerary qi = (QItinerary) it.next();
			System.out.println(qi.toString());
		}
		return itiList;
	
	
	}

	public static void checkForErrorMessage(AirSearchRsp rsp) {
		boolean die = false;

		List<ResponseMessage> msgs = rsp.getResponseMessage();
		if (msgs.size() != 0) {
			for (Iterator<ResponseMessage> iter = msgs.iterator(); iter
					.hasNext();) {

				ResponseMessage msg = (ResponseMessage) iter.next();
				// no data available for a particular date or city pair?
				if ((msg.getType().equals("Error"))
						&& (msg.getCode().equals(BigInteger.ZERO))) {
					if (msg.getProviderCode().equals(Helper.LOW_COST_PROVIDER)) {
						System.out
								.println("No data available from low cost provider?");
						continue;
					}
				}

				if (msg.getType().equals("Error")) {
					die = true;
				}
				String supplier = "";
				if (msg.getSupplierCode() != null) {
					supplier = "[" + msg.getSupplierCode() + "] ";
				}
				System.out.print("Response Message From Provider "
						+ msg.getProviderCode() + supplier + " : "
						+ msg.getType() + " : " + msg.getValue() + " -- ");
				System.out.println("Error Code = " + msg.getCode());
			}
		}
		if (die) {
			throw new RuntimeException("Unable to handle error from provider!");
		}
	}
	
	
	public static void createLowFareSearchWithRail(
			BaseLowFareSearchReq request, String originAirportcode,
			String destAirportCode, int departureDaysInFuture,
			int returnDaysInFuture) {

		// add in the tport branch code
		request.setTargetBranch(target_branch);

		// set the providers (including real and low cost) via a search modifier
		AirSearchModifiers modifiers = AirReq.createModifiersWithProviders(gdp, 
				//Helper.RAIL_PROVIDER,
				Helper.LOW_COST_PROVIDER);
		request.setAirSearchModifiers(modifiers);

		// need a POS as of v18_0
		AirReq.addPointOfSale(request, MYAPP);

		// we need to create a search leg but we do with some slack plus we use
		// the city code for london
		SearchAirLeg outbound = AirReq.createLeg(originAirportcode, destAirportCode);

		AirReq.addDepartureDate(outbound,
				Helper.daysInFuture(departureDaysInFuture));
		AirReq.addEconomyPreferred(outbound);

		// coming back, again something near these...
		SearchAirLeg ret = AirReq.createLeg(destAirportCode, originAirportcode);
		AirReq.addDepartureDate(ret, Helper.daysInFuture(returnDaysInFuture));
		AirReq.addEconomyPreferred(ret);

		// put them in the request
		List<SearchAirLeg> legs = request.getSearchAirLeg();
		legs.add(outbound);
		legs.add(ret);

		// one adult passenger
		AirReq.addAdultPassengers(request, 1);

		// get the point of sale right
		PointOfSale gdsPOS = new PointOfSale();
		gdsPOS.setProviderCode(gdp);
		gdsPOS.setPseudoCityCode(originAirportcode);

		PointOfSale railPOS = new PointOfSale();
		railPOS.setProviderCode(Helper.RAIL_PROVIDER);
		railPOS.setPseudoCityCode(originAirportcode);
		
		PointOfSale lccPOS = new PointOfSale();
		lccPOS.setProviderCode(Helper.LOW_COST_PROVIDER);
		lccPOS.setPseudoCityCode(originAirportcode);
		
		request.getPointOfSale().add(gdsPOS);
		request.getPointOfSale().add(railPOS);
		request.getPointOfSale().add(lccPOS);
	}
}


