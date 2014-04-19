package com.queryquest.rest.jersey.Utility;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.queryquest.rest.jersey.domain.SearchAnalysis;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;


public class SearchParser {

	private static final String[] Activities={"Aquarium","Archery","Art","Badminton","Basketball Court","Bowling","Boating","Beaches","Beauty Spa", "Go Kart","Golf","Gun Range","Rifle Range","Gymnastic","Hang Gliding","Horse Racing", "Horseback Riding", "Hot Air Balloon","Kiteboarding",  "Lake", "Leisure Center", "Mini Golf", "Mountain Biking","Paddleboarding","Paintball","Park" ,"Playground","Rafting", "Kayaking",
    "Recreation Center", "Rock Climbing", "Skating Rink","Skydiving", "Sledding", "Soccer", "Sports Club","Sport Club", "Squash", "Summer Camp", "Surfing","Swimming Pool" ,"Tennis","Trampoline Park","Tubing",  "Arcade","Art Gallery", "Art Galleries", "Botanical Garden", "Cabaret","Casino" ,"Cinema","movie theater","Cultural Center", "Festival", "Jazz  Blue", "Music Venue", "Opera & Ballet","Performing Art","Professional Sport Team","Psychics  Astrologers",
    "Race Track", "Social Club", "Stadium","Arena","Ticket Sale","Wineries","Barber", "Cosmetic", "Beauty Supply","Eyelash Service","Hair Extension", "Hair Removal", "Laser Hair Removal","Hair Salon","Blow Dry/Out Service", "Hair Stylist","Mens Hair Salon", "Makeup Artist","Massage", "Medical Spa","Nail Salon", "Permanent Makeup","Piercing","Rolfing","Skin Care", "Tanning","Tattoo","Bagel","Bakeries","Bakery","Beer", "Wine", "Spirit","Breweries","Bubble Tea","Butcher","CSA","Coffee & Tea", "Convenience Store","Cupcake","Dessert", "Distilleries",  "Donut","Farmers Market", "Food Delivery Service", "Food Truck","Gelato","Grocery",
    "Ice Cream", "Frozen Yogurt","Internet Cafe","Juice Bar", "Smoothy","Smoothies","Pretzel","Shaved Ice", "Specialty Food","Candy Store","Cheese Shop","Chocolatiers", "Shop", "Ethnic Food","Fruits & Veggies","Health Market ","Herb", "Spice", "Meat Shop","Seafood Market","Street Vendor","Tea Room","Wineries","Winery", "Airport","Bed & Breakfast", "Car Rental","Guest House","Health Retreat ","Hostel", "Hotel","Motorcycle Rental", "RV Park","RV Rental", 
    "Resort","Tour","Train Station", "Airport Shuttle", "Limo","Public Transportation", "Taxi","Travel Service","Vacation Rental Agent","Vacation Rental","Adult Entertainment","Bar","Comedy Club","Country Dance Hall","Jazz & Blues","Karaoke","Music Venue","Piano","Pool Hall","Campground","Casino","Day Camp","Disc Golf","Diving","Events this weekend","Fishing","Fitness","Golf","Hiking","Museums","Nightlife","Rv park","Scuba","Shopping","Sport bar","Zoo"};
	
	private static final String[] ignore={"vacation", "trip"};
	private static final int ACTSIZE =3;
	
	public SearchAnalysis stringParser(String searchTerm){
		SearchAnalysis searchAnalysis = new SearchAnalysis();
		

		// GET the no_of_days
		Pattern pattern = Pattern.compile("(\\d+\\s*[wW]eek).*");
		Matcher m= pattern.matcher(searchTerm);
		m = pattern.matcher(searchTerm);
		if(m.matches()){

			searchAnalysis.setNumOfDays(Integer.parseInt(m.group(1).replaceAll("[\\D]", "")));
			searchTerm = searchTerm.replace(m.group(1), "");
			//System.out.println("String now is "+searchTerm);

		}else {
		    pattern = Pattern.compile(".*(\\d+\\s*[wW]eek).*");
		     m = pattern.matcher(searchTerm);
		     if(m.matches()){

					searchAnalysis.setNumOfDays(Integer.parseInt(m.group(1).replaceAll("[\\D]", "")));
					searchTerm = searchTerm.replace(m.group(1), "");
		     }
		}
		
	    pattern = Pattern.compile("(\\d+\\s*[dD]ay).*");
		m = pattern.matcher(searchTerm);
		if(m.matches()){

			searchAnalysis.addNumOfDays(Integer.parseInt(m.group(1).replaceAll("[\\D]", "")));
			searchTerm = searchTerm.replace(m.group(1), "");
			//System.out.println("String now is "+searchTerm);

		}else {
		    pattern = Pattern.compile(".*(\\d+\\s*[dD]ay).*");
		     m = pattern.matcher(searchTerm);
		     if(m.matches()){

					searchAnalysis.addNumOfDays(Integer.parseInt(m.group(1).replaceAll("[\\D]", "")));
					searchTerm = searchTerm.replace(m.group(1), "");
		     }
		}
		

		StringTokenizer st = new StringTokenizer(searchTerm);
		ArrayList<String> search = new ArrayList<String>(); 
		while(st.hasMoreElements()){
			String sub = (String) st.nextElement();
			if(sub.length() >= ACTSIZE)
				search.add(sub);	
		}
		
		for(int j=0;j<search.size();j++){
			for(int i=0; i< ignore.length;i++){
				if((ignore[i].toLowerCase().equals(search.get(j).toLowerCase()))){
					search.remove(j);
				}
				else if (ignore[i].toLowerCase().concat("s").equals(search.get(j).toLowerCase())){
					search.remove(j);
				}
			}
		}
		
		//GET the activity 
		ArrayList<String> actList = new ArrayList<String>();
		for(int i=0;i<Activities.length;i++){

			for(int j=0;j<search.size();j++){
				//String act= Activities[i].toLowerCase();
				//String actGiven = search.get(j).toLowerCase()+((j==search.size()-1)?"":search.get(j+1).toLowerCase());
				
				//System.out.println("ACT "+act+"actGiven "+actGiven);
				if((Activities[i].toLowerCase().replaceAll("\\W","")).equals((search.get(j).toLowerCase()+ ((j==search.size()-1)?"":(search.get(j+1).toLowerCase()))))){
                	actList.add(Activities[i]);
					System.out.println("Activity entered is "+search.get(j)+((j==search.size()-1)?"":search.get(j+1).toLowerCase()));
					String str1 = search.get(j);
					String str2 = (j==search.size()-1)?"":search.get(j+1);
					search.remove(str1);
					search.remove(str2);
                }
				else if ((search.get(j).toLowerCase()+((j==search.size()-1)?"":(search.get(j+1).toLowerCase()))).matches((Activities[i].toLowerCase()).replaceAll("\\W","").concat("s"))){
                //else if(((Activities[i].toLowerCase())+"[s][es]").matches((search.get(j).toLowerCase()+((j==search.size()-1)?"":(search.get(j+1).toLowerCase()))+".{1,2}"))){
                	actList.add(Activities[i]);
                	System.out.println("Activity entered is "+search.get(j)+((j==search.size()-1)?"":search.get(j+1).toLowerCase()));
					String str1 = search.get(j);
					String str2 = (j==search.size()-1)?"":search.get(j+1);
					search.remove(str1);
					search.remove(str2);
                }
			}
			
			for(int j=0;j<search.size();j++){
				//System.out.println("activities - "+Activities[i]+ "search - "+search.get(j));
                if(Activities[i].toLowerCase().replaceAll("\\W","").equals(search.get(j).toLowerCase())){
                	actList.add(Activities[i]);
					System.out.println("Activity entered is "+search.get(j));
					search.remove(j);
                }
                else if((Activities[i].toLowerCase().replaceAll("\\W","").concat("s")).matches((search.get(j).toLowerCase()))){
                	actList.add(Activities[i]);
					System.out.println("Activity entered is "+search.get(j));
					search.remove(j);
                }
			}                	
                
				/*if(Activities[i].toLowerCase().contains(search.get(j).toLowerCase())){
					actList.add(Activities[i]);
					System.out.println("Activity entered is "+search.get(j));
					search.remove(j);

				}
				else if(search.get(j).toLowerCase().contains(Activities[i].toLowerCase())){
					actList.add(Activities[i]);
					System.out.println("Activity1 entered is "+search.get(j));
					search.remove(j);

				}*/
			
		}
		
		
		//GET the location
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,Boolean.TRUE);
		Client client= Client.create(clientConfig);

		//Location is two string
		for(int i=0;i+1<search.size();i++){
			System.out.println(" substring "+search.get(i)+search.get(i+1));
			WebResource webResource = client.resource("http://query.yahooapis.com/v1/public/yql?q=select%20%2a%20from%20geo.places%20where%20text='"+search.get(i)+"+"+search.get(i+1)+"'&format=json");
			String s = webResource.get(String.class);
			System.out.println("ouput "+s);

			JSONObject json=null;
			try {
				json = (JSONObject) new JSONParser().parse(s);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(((JSONObject)json.get("query")).get("count") != null)
			{
				JSONObject j = (JSONObject) json.get("query");
				Long count= (Long)j.get("count");
				System.out.println("output - "+count);

				if(count >0){
					searchAnalysis.setLocation(search.get(i)+search.get(i+1));
					String str1 = search.get(i);
					String str2 = search.get(i+1);
					search.remove(str1);
					search.remove(str2);
					break;
				}
			}
		}
		
		//location is single string
        if(searchAnalysis.getLocation()==null){
        	for(int i=0;i<search.size();i++){
    			System.out.println(" substring "+search.get(i));

    			WebResource webResource = client.resource("http://query.yahooapis.com/v1/public/yql?q=select%20%2a%20from%20geo.places%20where%20text='"+search.get(i)+"'&format=json");
    			String s = webResource.get(String.class);
    			System.out.println("ouput "+s);

    			JSONObject json=null;
    			try {
    				json = (JSONObject) new JSONParser().parse(s);
    			} catch (ParseException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}

    			if(((JSONObject)json.get("query")).get("count") != null)
    			{
    				JSONObject j = (JSONObject) json.get("query");
    				Long count= (Long)j.get("count");
    				System.out.println("output - "+count);

    				if(count >0){
    					searchAnalysis.setLocation(search.get(i));
    					search.remove(i);
    					break;
    				}
    			}
    		}
        }
        

		
		searchAnalysis.setActivity(actList);

		
        System.out.println(searchAnalysis.toString());
        return searchAnalysis;
	}
}
