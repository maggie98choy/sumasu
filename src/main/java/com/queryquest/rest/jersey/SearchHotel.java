package com.queryquest.rest.jersey;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;

import com.travelport.schema.common_v15_0.NextResultReference;
import com.travelport.schema.common_v15_0.TypeReserveRequirement;
import com.travelport.schema.hotel_v17_0.HotelLocation;
import com.travelport.schema.hotel_v17_0.HotelMediaLinksReq;
import com.travelport.schema.hotel_v17_0.HotelProperty;
import com.travelport.schema.hotel_v17_0.HotelRating;
import com.travelport.schema.hotel_v17_0.HotelSearchAvailabilityReq;
import com.travelport.schema.hotel_v17_0.HotelSearchAvailabilityRsp;
import com.travelport.schema.hotel_v17_0.HotelSearchModifiers;
import com.travelport.schema.hotel_v17_0.HotelSearchResult;
import com.travelport.schema.hotel_v17_0.HotelStay;
import com.travelport.schema.hotel_v17_0.TypeHotelAvailability;
import com.travelport.service.hotel_v17_0.HotelFaultMessage;
import com.travelport.service.hotel_v17_0.HotelMediaLinksServicePortType;
import com.travelport.service.hotel_v17_0.HotelSearchServicePortType;
import com.travelport.tutorial.support.WSDLService;
import com.travelport.uapi.unit1.Helper;
import com.travelport.uapi.unit1.Helper.VendorLocMap;
import com.travelport.uapi.unit2.Lesson4;
import com.queryquest.rest.jersey.domain.QHotel;
import com.queryquest.rest.jersey.domain.QItinerary;

public class SearchHotel 
{

	 static protected String username = "Universal API/uAPI-758840054";
	 static protected String password = "A2sWEdZfkSeazc5ehaHQg4eqB";
	 static protected String gdp = "1V";
	 static protected String target_branch = "P106464";
	    
	 public ArrayList<QHotel> searchHotel(String cityCode) throws HotelFaultMessage 
	 //public static void main(String[] argv) throws HotelFaultMessage
	 {
/*		
	
		HotelSearchServicePortType port = WSDLService.hotelShop.get();
        
        This port ends up not being provided by some providers so it isn't
        used by the lesson's code
        HotelMediaLinksServicePortType media = WSDLService.hotelMedia.get();
        WSDLService.hotelMedia.showXML(true);

        the hotel search parametrs
        int numAdults=2, numRooms=2, distanceInKm=25, maxScreens=4;
        int daysToCheckin = 90, daysToDeparture = 97;
        Collection<QHotel> qhotelList = null; 
        qhotelList = new ArrayList<QHotel> ();
        QHotel qhotel = new QHotel();
        String pointOfInterestName="Mall of America";
        String pointOfInterestName = ptOfInterestName;
      
        try {
            
            WSDLService.hotelShop.showXML(true);
            
            HotelSearchResult[] result= 
                findLowestPriceAndClosestToAttraction(port, pointOfInterestName, distanceInKm,
                maxScreens, numAdults, numRooms, daysToCheckin, 
                daysToDeparture, /*don't care about guaranteefalse);
        
            printHotel(result[0], pointOfInterestName, media);
           printHotel(result[1], pointOfInterestName, media);
            
            
            for (int i= 0 ; i<result.length; i++ )
            {
            HotelMediaLinksReq req = new HotelMediaLinksReq();
	         req.setBillingPointOfSaleInfo(Helper.tutorialBPOSInfo(6, 2));
	         req.getHotelProperty().add(result[i].getHotelProperty()); 
	         HotelMediaLinksRsp rsp = media.service(req);
	         

	        at the moment, it doesn't appear that there is any really useful
	       information in the vendor location map... that may vary by provider
	        HotelProperty p = result[i].getHotelProperty();
	        	        
	        qhotel.setHotelAddress(p.getPropertyAddress().getAddress().get(0));
	        qhotel.setHotelCode(p.getHotelCode());
	        qhotel.setHotelChain(p.getHotelChain());
	        qhotel.setHotelName(p.getName());
	        qhotel.setHotelRating(flattenRatingsToText(result[0].getHotelProperty().getHotelRating()));
	        qhotel.setHotelMinAmt(result[i].getMinimumAmount());
	        qhotel.setHotelMaxAmt(result[i].getMaximumAmount());
	        qhotel.setHotelAttraction(pointOfInterestName);
	        qhotel.setHotelDistance(p.getDistance().getValue());
	        if (result[i].getHotelProperty().getCoordinateLocation() != null) 
	        {
	        	qhotel.setHotelLatitude(result[i].getHotelProperty().getCoordinateLocation().getLatitude());
	        	qhotel.setHotelLongtitude(result[i].getHotelProperty().getCoordinateLocation().getLongitude());
	        }
	        qhotelList.add(qhotel);
	        
	        Formatter fmt = new Formatter(System.out);
	        fmt.format("%-30s [%s:%s]\n", p.getName(), p.getHotelChain(), p.getHotelCode());

	        String addr = p.getPropertyAddress().getAddress().get(0);
	        fmt.format("%10s %s\n", "", addr);

	        String ratingsText = flattenRatingsToText(result[i].getHotelProperty().getHotelRating());
	        fmt.format("%10s %-10s to %-10s \n", "", result[i].getMinimumAmount(), result[i].getMaximumAmount());
	        fmt.format("%10s %s%s from %s\n", "", p.getDistance().getValue(), 
	                p.getDistance().getUnits(), pointOfInterestName);
	        if (!ratingsText.equals("")) {
	            fmt.format("%10s %s\n", "", ratingsText);
	        }
	        fmt.format("%10s RESERVATION REQUIREMENT IS %s\n", "", result[0].getHotelProperty().getReserveRequirement());

	        if (result[i].getHotelProperty().getCoordinateLocation() != null) {
	            float lat = result[i].getHotelProperty().getCoordinateLocation().getLatitude();
	            float lon = result[i].getHotelProperty().getCoordinateLocation().getLongitude();
	            fmt.format("%5s http://maps.google.com/?q=%.6f,%.6f\n", "", lat, lon);
	        }
            }
            
            searchVehicle("CDG", daysToCheckin, daysToDeparture);

        } catch (NumberFormatException e) {
            System.err.println("unable to parse hotel price: " + e.getMessage());
        } catch (HotelFaultMessage e) {
            System.err.println("error reading hotel data: " + e.getMessage());
        } catch (VehicleFaultMessage e) {
            System.err.println("error reading vehicle data: " + e.getMessage());
       }	
        
        return qhotelList;
        
        */
		 //Collection<QHotel> qhotelList = null; 
		 ArrayList<QHotel> qhotelList = new ArrayList<QHotel> ();
	     QHotel qhotel = new QHotel();
	        
	    HotelSearchServicePortType port = WSDLService.hotelShop.get();
        HotelSearchAvailabilityReq req = new HotelSearchAvailabilityReq();
        
        //business trip, 2 travellers with own room
        HotelSearchModifiers mods = Lesson4.createModifiers(2, 2);
        
        //have to give our branch id
        req.setTargetBranch(target_branch);
        
        //hotel location is paris france
        HotelLocation location = Lesson4.createCityLocation("LAS");
        //HotelLocation location= Lesson4.createCityLocation(cityCode);
        //near the eiffel tower
        req.setHotelLocation(location);
        //mods.setReferencePoint("EIFFEL TOWER");
        
        //within 1000m of the tower?
        //Lesson4.addDistanceModifier(mods, 1);
        
        //when staying?
        HotelStay stay = Lesson4.createCheckInAndOut(Lesson4.futureDateAsXML(45),
                Lesson4.futureDateAsXML(47));
        req.setHotelStay(stay);
        
        //point of sale
        req.setBillingPointOfSaleInfo(Helper.tutorialBPOSInfo(6, 2));
        
        //where we store results
        Helper.VendorLocMap locMap = new Helper.VendorLocMap();
        HotelSearchAvailabilityRsp rsp=null;
        //int screens = 0;
        
        //do {
            rsp = port.service(req);
            //assertThat(locMap.mergeAll(rsp.getVendorLocation()), is(0));
            if (rsp.getNextResultReference().size()>1) {
                fail("not sure what it means to have more than 1 next result ref!");
            }
            if (rsp.getNextResultReference().size()==1) {
                req.getNextResultReference().clear();
                //hook the previous result to this next request
                req.getNextResultReference().add(rsp.getNextResultReference().get(0));
                
                List<HotelSearchResult> result = rsp.getHotelSearchResult();
                //System.out.println("result size: "+result.size());
                
                for (int i=0 ; i<result.size(); i++)
                {	
                	
	            	HotelSearchResult res = result.get(i);
                	HotelProperty p = res.getHotelProperty();
    
                	qhotel.setHotelAddress(p.getPropertyAddress().getAddress().toString());
	    	        qhotel.setHotelCode(p.getHotelCode());
	    	        qhotel.setHotelChain(p.getHotelChain());
	    	        qhotel.setHotelName(p.getName());
	    	        qhotel.setHotelRating(flattenRatingsToText(p.getHotelRating()));
	    	        
	    	        qhotel.setHotelMinAmt(res.getMaximumAmount());
	    	        qhotel.setHotelMaxAmt(res.getMaximumAmount());
	    	        //qhotel.setHotelAttraction(pointOfInterestName);
	    	        qhotel.setHotelDistance(p.getDistance().getValue());
	    	        if (p.getCoordinateLocation() != null) 
	    	        {
	    	        	qhotel.setHotelLatitude(p.getCoordinateLocation().getLatitude());
	    	        	qhotel.setHotelLongtitude(p.getCoordinateLocation().getLongitude());
	    	        }
	    	        
	    	        System.out.println(qhotel.toString());
	    	        qhotelList.add(qhotel);
	    	        qhotelList.add(new QHotel(qhotel.getHotelName(),qhotel.getHotelChain(), qhotel.getHotelCode(),qhotel.getHotelAddress(),
	    	        		qhotel.getHotelMinAmt(),qhotel.getHotelMaxAmt(), qhotel.getHotelRating(), qhotel.getHotelDistance(),
	    	        		qhotel.getHotelAttraction(), qhotel.getHotelLongtitude(), qhotel.getHotelLatitude()));
	    	        
                }
    	        
            }
          /*  ++screens;
            if (screens==5) {
                break;
            }
        } while (rsp.getNextResultReference().size()>0);
        */
            
            return qhotelList;
    }
      

	
	   private void QHotel(String hotelName, String hotelChain, String hotelCode,
			String hotelAddress, String hotelMinAmt, String hotelMaxAmt,
			String hotelRating, BigInteger hotelDistance,
			String hotelAttraction, float hotelLongtitude, float hotelLatitude) {
		// TODO Auto-generated method stub
		
	}



	public static HotelSearchResult[] findLowestPriceAndClosestToAttraction(HotelSearchServicePortType port, 
	            String pointOfInterest, int searchRadiusInKM, int maxScreens, 
	            int numAdults, int numRooms, int daysInFutureForCheckIn, 
	            int daysInFutureForCheckOut, boolean noDepositOrGuarantee) throws HotelFaultMessage {

	        HotelSearchAvailabilityReq req = new HotelSearchAvailabilityReq();

	        HotelSearchModifiers mods = Lesson4.createModifiers(numAdults, numRooms);

	        /*
	         * bedding requests can't be handled by 1G or 1V
	         */ 
	        /*
	         HotelBedding parents = new
	         HotelBedding(); parents.setType(TypeBedding.QUEEN);
	         parents.setNumberOfBeds(1); mods.getHotelBedding().add(parents);
	          
	         HotelBedding kids = new HotelBedding();
	         kids.setType(TypeBedding.TWIN); kids.setNumberOfBeds(2);
	         mods.getHotelBedding().add(kids);
	       */  

	        // have to give our branch id
	        req.setTargetBranch(target_branch);

	        mods.setReferencePoint(pointOfInterest);

	        // within certain radius of attraction
	        Lesson4.addDistanceModifier(mods, searchRadiusInKM);
	        req.setHotelSearchModifiers(mods);

	        // when staying?
	        HotelStay stay = Lesson4.createCheckInAndOut(Lesson4.futureDateAsXML(daysInFutureForCheckIn),
	                Lesson4.futureDateAsXML(daysInFutureForCheckOut));
	        req.setHotelStay(stay);

	        // point of sale
	        req.setBillingPointOfSaleInfo(Helper.tutorialBPOSInfo(6, 2));

	        // where we store results
	        HotelSearchAvailabilityRsp rsp = null;
	        int screens = 0;

	        HotelSearchResult closest = null, cheapest = null;
	        int lowestDistance = Integer.MAX_VALUE;
	        double lowestPrice = Double.MAX_VALUE;
	        // we walk through each result
	        do {
	            NextResultReference next = null;
	            VendorLocMap NOT_USED = new VendorLocMap();

	            // run the request, possibly from some middle point
	            rsp = port.service(req);
	            // merge everyone into the map
	            NOT_USED.mergeAll(rsp.getVendorLocation());

	            List<HotelSearchResult> results = rsp.getHotelSearchResult();
	            for (Iterator<HotelSearchResult> iterator = results.iterator(); iterator.hasNext();) {
	                HotelSearchResult r = (HotelSearchResult) iterator.next();
	                HotelProperty p = r.getHotelProperty();
	                if ((p.getAvailability() == null) || (!p.getAvailability().equals(TypeHotelAvailability.AVAILABLE))) {
	                    continue;
	                }
	                //we don't want to have to use a credit card or cash to guarantee resv
	                if ((noDepositOrGuarantee) && (!p.getReserveRequirement().equals(TypeReserveRequirement.OTHER))) {
	                    continue;
	                }
	                
	                // get the price, check for lowest...
	                double min = Helper.parseNumberWithCurrency(r.getMinimumAmount());
	                //some places offer a min price of ZERO which is clearly not
	                //available so we use half max price just to make the output
	                //halfway meaningful
	                if (min==0.0) {
	                    min = Helper.parseNumberWithCurrency(r.getMaximumAmount())/2;
	                }
	                if (min < lowestPrice) {
	                    cheapest = r;
	                    lowestPrice = min;
	                }
	                // check for closest
	                int dist = p.getDistance().getValue().intValue();
	                if (dist < lowestDistance) {
	                    closest = r;
	                    lowestDistance = dist;
	                }

	            }
	            // is there more?
	            if (rsp.getNextResultReference().size() > 0) {
	                // there is, so prepare for it
	                next = rsp.getNextResultReference().get(0);
	            }
	            // keep track of number of times we've hit the server
	            ++screens;
	            if (next == null) {
	                // no more data
	                break;
	            }
	            // prepare for next round by setting the next value into this
	            // request
	            req.getNextResultReference().clear();
	            req.getNextResultReference().add(next);

	        } while (screens != maxScreens);

	        if (closest == null) {
	            return null;
	        } 
	        
	        HotelSearchResult[] result = new HotelSearchResult[2];
	        result[0]=cheapest;
	        result[1]=closest;
	        return result;
	    }
	   
	    public static void printHotel(HotelSearchResult r, 
	            String attractionName, HotelMediaLinksServicePortType media)
	            throws HotelFaultMessage {

	        
	         HotelMediaLinksReq req = new HotelMediaLinksReq();
	         req.setBillingPointOfSaleInfo(Helper.tutorialBPOSInfo(6, 2));
	         req.getHotelProperty().add(r.getHotelProperty()); 
	        // HotelMediaLinksRsp rsp = media.service(req);
	         

	        // at the moment, it doesn't appear that there is any really useful
	        // information in the vendor location map... that may vary by provider
	        HotelProperty p = r.getHotelProperty();
	        Formatter fmt = new Formatter(System.out);
	        fmt.format("%-30s [%s:%s]\n", p.getName(), p.getHotelChain(), p.getHotelCode());

	        String addr = p.getPropertyAddress().getAddress().get(0);
	        fmt.format("%10s %s\n", "", addr);

	        String ratingsText = flattenRatingsToText(r.getHotelProperty().getHotelRating());
	        fmt.format("%10s %-10s to %-10s \n", "", r.getMinimumAmount(), r.getMaximumAmount());
	        fmt.format("%10s %s%s from %s\n", "", p.getDistance().getValue(), 
	                p.getDistance().getUnits(), attractionName);
	        if (!ratingsText.equals("")) {
	            fmt.format("%10s %s\n", "", ratingsText);
	        }
	        fmt.format("%10s RESERVATION REQUIREMENT IS %s\n", "", r.getHotelProperty().getReserveRequirement());

	        if (r.getHotelProperty().getCoordinateLocation() != null) {
	            float lat = r.getHotelProperty().getCoordinateLocation().getLatitude();
	            float lon = r.getHotelProperty().getCoordinateLocation().getLongitude();
	            fmt.format("%5s http://maps.google.com/?q=%.6f,%.6f\n", "", lat, lon);
	        }
	    }
	    
	    public static String flattenRatingsToText(List<HotelRating> ratings) {
	        StringBuilder builder = new StringBuilder();
	        for (Iterator<HotelRating> iterator = ratings.iterator(); iterator.hasNext();) {
	            HotelRating rating = (HotelRating) iterator.next();
	            List<BigInteger> nums = rating.getRating();
	            if (nums.isEmpty()) {
	                continue;
	            }
	            builder.append(rating.getRatingProvider() + " says ");
	            for (Iterator<BigInteger> numIter = nums.iterator(); numIter.hasNext();) {
	                BigInteger num = (BigInteger) numIter.next();
	                builder.append(num + " ");
	            }
	            if (rating.getRatingRange()!=null) {
	                builder.append(" on scale of " + rating.getRatingRange().getMinimumRating() + " to "
	                    + rating.getRatingRange().getMaximumRating());
	            }
	        }
	        return builder.toString();
	    }	
		
	

}
