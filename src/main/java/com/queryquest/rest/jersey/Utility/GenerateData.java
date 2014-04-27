package com.queryquest.rest.jersey.Utility;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.queryquest.rest.jersey.domain.SearchResult;
import com.queryquest.rest.jersey.fbclient.Yelp;

public class GenerateData {
	private static final String consumerKey = "S2b1Z5akbX2Ep6IUKXxTZw";
	private static final String consumerSecret = "KSpUspbiBY7_C4wcMWyS3xwLYe4";
	private static final String token = "Z3QmxmmVosore5XczhtllzgP6lBVFlHY";
	private static final String tokenSecret = "f3bwOxHPiXpywmQ3K3bAMK9ikrE";
	private static  long businessId=0L;
	/*private static final String[] Activities={"Aquarium","Archery","Art","Badminton","Basketball Court","Bowling","Boating","Beaches","Beauty Spa", "Go Kart","Golf","Gun Range","Rifle Range","Gymnastic","Hang Gliding","Horse Racing", "Horseback Riding", "Hot Air Balloon","Kiteboarding",  "Lake", "Leisure Center", "Mini Golf", "Mountain Biking","Paddleboarding","Paintball","Park" ,"Playground","Rafting", "Kayaking",
	    "Recreation Center", "Rock Climbing", "Skating Rink","Skydiving", "Sledding", "Soccer", "Sports Club","Sport Club", "Squash", "Summer Camp", "Surfing","Swimming Pool" ,"Tennis","Trampoline Park","Tubing",  "Arcade","Art Gallery", "Art Galleries", "Botanical Garden", "Cabaret","Casino" ,"Cinema","movie theater","Cultural Center", "Festival", "Jazz  Blue", "Music Venue", "Opera & Ballet","Performing Art","Professional Sport Team","Psychics  Astrologers",
	    "Race Track", "Social Club", "Stadium","Arena","Ticket Sale","Wineries","Barber", "Cosmetic", "Beauty Supply","Eyelash Service","Hair Extension", "Hair Removal", "Laser Hair Removal","Hair Salon","Blow Dry/Out Service", "Hair Stylist","Mens Hair Salon", "Makeup Artist","Massage", "Medical Spa","Nail Salon", "Permanent Makeup","Piercing","Rolfing","Skin Care", "Tanning","Tattoo","Bagel","Bakeries","Bakery","Beer", "Wine", "Spirit","Breweries","Bubble Tea","Butcher","CSA","Coffee & Tea", "Convenience Store","Cupcake","Dessert", "Distilleries",  "Donut","Farmers Market", "Food Delivery Service", "Food Truck","Gelato","Grocery",
	    "Ice Cream", "Frozen Yogurt","Internet Cafe","Juice Bar", "Smoothy","Smoothies","Pretzel","Shaved Ice", "Specialty Food","Candy Store","Cheese Shop","Chocolatiers", "Shop", "Ethnic Food","Fruits & Veggies","Health Market ","Herb", "Spice", "Meat Shop","Seafood Market","Street Vendor","Tea Room","Wineries","Winery", "Airport","Bed & Breakfast", "Car Rental","Guest House","Health Retreat ","Hostel", "Hotel","Motorcycle Rental", "RV Park","RV Rental", 
	    "Resort","Tour","Train Station", "Airport Shuttle", "Limo","Public Transportation", "Taxi","Travel Service","Vacation Rental Agent","Vacation Rental","Adult Entertainment","Bar","Comedy Club","Country Dance Hall","Jazz & Blues","Karaoke","Music Venue","Piano","Pool Hall","Campground","Casino","Day Camp","Disc Golf","Diving","Events this weekend","Fishing","Fitness","Golf","Hiking","Museums","Nightlife","Rv park","Scuba","Shopping","Sport bar","Zoo"};*/
	private static final String[] Activities = {"Aquariums","Archery","Beaches","Campgrounds","Casinos","Fishing","Golf","Hiking","Museums","Nightlife","Scuba","Shopping","Wineries","Zoos"};
	//private static final String[] Activities ={"Scuba","Aquariums","Beaches","Casinos","Diving","Fishing","Golf","Archery","Hiking","Campgrounds","Museums","Nightlife","Shopping","Wineries","Zoos"};
	//private static final String[] Cities={"San Jose"};
	/*private static final String[] Cities ={"San Jose","Sunnyvale","Santa Clara", "San Francisco",
		"Fremount", "Palo Alto", "Campbell","Los Gatos", "Los Angeles","Las Vegas"};*/

	private static final String[] Cities ={"New York",	"Los Angeles", "Chicago", "Houston", "Philadelphia", "Phoenix","San Antonio", 	"San Diego", "Dallas", 	"San Jose", "Austin", "Jacksonville", "Indianapolis","San Francisco",	"Columbus", 	"Fort Worth", 	"Charlotte", "Detroit", "El Paso", 	"Memphis", "Boston" ,	"Seattle","Denver", "Washington", "Nashville",	"Baltimore", 	"Louisville", 	"Portland", "Oklahoma City", 	"Milwaukee", "Las Vegas", "Albuquerque", "Tucson","Fresno","Sacramento", "Long Beach", "Kansas City", "Mesa",
	"Virginia Beach","Atlanta", "Colorado Springs",  "Raleigh", "Omaha",  "Miami", "Oakland", "Tulsa","Minneapolis", "Cleveland",  "Wichita", "Arlington", "New Orleans", "Bakersfield", 	"Tampa", 	"Honolulu",	"Anaheim", 
	"Aurora", "Santa Ana" ,	"St. Louis",	"Riverside",  	"Corpus Christi", 	"Pittsburgh",  	"Lexington", 	"Anchorage",
	 	"Stockton" , "Cincinnati", 	"Saint Paul",  	"Toledo", 	"Newark",  "Greensboro",  "Plano",  	"Henderson",
 	"Lincoln", 	"Buffalo",  "Fort Wayne",  	"Jersey City",  	"Chula Vista", 	"Orlando", 	"St. Petersburg", 
 	 	"Norfolk", 	"Chandler",  	"Laredo",  	"Madison", 	"Durham",  	"Lubbock",  "Winston–Salem", "Garland", 
 	 	"Hialeah", 	"Reno",  	"Baton Rouge", 	"Irvine", 		"Chesapeake",	"Irving",  	"Scottsdale",  "North Las Vegas",
 	 	"Fremont", 	"Gilbert", 	"San Bernardino", 	"Boise",	"Birmingham", 	"Rochester", 	"Richmond",	"Spokane", 
 	"Des Moines", 	"Montgomery",	"Modesto", 	"Tacoma", "Shreveport", 	"Fontana", 	"Oxnard", "Aurora", 	"Moreno", 	"Akron", 
 		"Yonkers", 	"Columbus",	"Augusta",	"Little Rock", "Amarillo", "Mobile", 	"Alabama", "Huntington Beach" ,"Glendale",
 	"Grand Rapids", "Salt Lake City", 	"Tallahassee", 	"Huntsville", 	"Worcester", 	"Knoxville", 	"Grand Prairie", 
 	"Newport", "News Brownsville",  "anta Clarita", "Overland Park", "Providence", "Jackson", 	"Garden Grove", "Oceanside",
 	"Chattanooga", 	"Fort Lauderdale", 	"Rancho Cucamonga", "Santa Rosa",  "Port St. Lucie", "Ontario", 
 		"Tempe", "Vancouver", "Springfield",  "Cape Coral", 	"Pembroke", "Pines", "Sioux Falls", "Peoria","Lancaster",
 	"Elk Grove" 	,	"Corona" , "Eugene" ,"Salem", "Palmdale" ,		"Salinas" 		,"Springfield" ,
 		"Pasadena" ,"Rockford", "Pomona" 	,"Hayward" ,	"Fort Collins" 	,"Joliet" ,	"Escondido","Kansas City" , "Torrance","Bridgeport" ,"Alexandria" ,"Sunnyvale" ,"Cary" ,"Lakewood" ,"Hollywood" ,"Paterson", 
	"Syracuse",   "Naperville", 	"McKinney",	"Mesquite", 	"Clarksville", 	"Dayton",  "Orange", "Fullerton", 
"Pasadena", "Hampton",  "McAllen"  , "Killeen" ,	"Warren", 	"Michigan" ,	"West Valley City" ,	"Columbia" ,	"New Haven" ,	"Sterling Heights" ,		"Olathe", "Miramar" ,	"Thousand Oaks" ,"Frisco" ,	"Cedar", "Rapids" ,	"Topeka" ,	"Visalia",  "Waco"	,"Elizabeth",	"Bellevue", 		"Gainesville", "Simi Valley" 	,"Charleston" ,	"Carrollton" ,	"Coral Springs" , "Hartford", "Concord" ,"Roseville", "Thornton", "Kent" ,"Lafayette" , "Surprise" 	,"Denton" ,	"Victorville" ,"Evansville" ,"Midland" ,"Santa Clara" ,"Athens" ,"Allentown", "Abilene", "Beaumont" 	,"Vallejo" ,	"Independence" 	,	"Springfield" ,	"Ann Arbor" ,	"Provo" 	,"Peoria Norman", "Berkeley", "El Monte", 	"Murfreesboro", 	"Lansing", "Columbia" ,	"Downey" 	,	"Costa Mesa" ,	"Inglewood" ,	"Miami Gardens", "Manchester", "Elgin" ,"Wilmington","Waterbury", "Fargo" ,"Arvada" ,"Carlsbad" ,"Westminster" ,"Rochester" ,"Gresham" ,"Clearwater" ,"Lowell", "Massachusetts", 	
"West Jordan" ,"Pueblo" ,"Ventura","Fairfield" ,"West Covina" ,"Billings" ,"Murrieta" ,"High Point" ,"Round Rock", "Richmond", "Cambridge", "Norwalk", "Odessa" ,"Antioch", "Temecula", "Green Bay", 
	"Everett","North Charleston", "West Palm Beach",	"Boulder",	"Rialto",	"Santa Maria", 
"El Cajon" ,"Davenport" ,"Erie","Las Cruces","South Bend","Flint","Kenosha"};


	private void writeToLog(String s, String filename) throws IOException {
		System.out.println("Filename "+filename);
		BufferedWriter aWriter = new BufferedWriter(new FileWriter(filename,true));
		aWriter.write(s);
		aWriter.newLine();
		aWriter.flush();
		aWriter.close();
	}	

	public int getRandomNumberFrom(int min, int max) {
		Random foo = new Random();
		int randomNumber = foo.nextInt((max + 1) - min) + min;

		return randomNumber;

	}

	public boolean checkInUser(int[] users,int user){
		for (int i=0;i<users.length;i++){
			if(user == users[i])
				return true;
		}	
		return false;
	}

	public void generate(){
		
		//System.out.println("Act size "+Activities.length);
		//System.out.println("cities size "+Cities.length);
		Yelp yelp = new Yelp(consumerKey, consumerSecret, token, tokenSecret);
        Set<String> bName= new HashSet<String>();
		Map<String,Long> map= new HashMap<String,Long>();

		for(int k=0;k<Cities.length;k++){
			for(int j=0;j<Activities.length;j++){

				String out=yelp.search(Activities[j], Cities[k]);

				JSONObject jsonObj = JSONObject.fromObject(out);
				JSONArray msg = (JSONArray) jsonObj.get("businesses");
				if(msg != null){
					Iterator<JSONObject> iterator = msg.iterator();
					while (iterator.hasNext()){
						String dataset1="", dataset2="", dataset3="";
						int[] users= new int[20];
						for(int i=0;i<users.length;i++){
							int user=getRandomNumberFrom(1, 10000);
							while(checkInUser(users,user))
								user=getRandomNumberFrom(1, 10000);
							users[i]=user;
						}
						
					//	for(int i=0;i<users.length;i++)
						//	System.out.print(users[i]+" ");
	

						SearchResult search = new SearchResult();
						JSONObject business = iterator.next();
						//System.out.println(JSONObject.fromObject(business));
						String name = (String) business.get("name");
						//System.out.println("name "+name);
                        
						List<String> catList = new ArrayList<String>();
						if((business.get("categories"))instanceof JSONArray){
							JSONArray categories = business.getJSONArray("categories");
							for(int i=0;i<categories.size();i++)
								catList.add(categories.getString(i));
						}
						
						int match=0;
						
						for(int i=0;i<catList.size();i++){
							
							//System.out.println("ALL "+catList.get(i)+"  "+activity);
							String[] cat = catList.get(i).split(",");
							for(int z=0;z<cat.length;z++){
								//if(name.equals("Carmel Bay Divers"));
									//System.out.println("ooo "+cat[z].toLowerCase().replaceAll("\\W", "")+" act-  "+Activities[j].toLowerCase());
								if((cat[z].toLowerCase().replaceAll("\\W", "")).equals(Activities[j].toLowerCase())){
									match=1;
									break;
								}
								if(((cat[z].toLowerCase().replaceAll("\\W", "")).contains(Activities[j].toLowerCase()))){
									match=1; 
								//	System.out.println("CAT "+catList.get(i));
									break;

								}
								else if(Activities[j].toLowerCase().contains(cat[z].toLowerCase().replaceAll("\\W", ""))){
									//System.out.println("acti " +activity.toLowerCase());
									//System.out.println("cat "+cat[j].toLowerCase().replaceAll("\\W", ""));
									match=1; 
						//			System.out.println("CATi "+catList.get(i));
									break;
								}
							}
						}

						if(match==0)
							continue;
						
						System.out.println("Activities at --- "+Activities[j]);
						String activityFileName="./".concat((Activities[j].concat(".csv"))); 

						
					    JSONObject location= JSONObject.fromObject(business.get("location"));
						String addr1 = JSONObject.fromObject(business.get("location")).getString("display_address");
						String addr2 = addr1.replaceAll("[^0-9a-zA-Z\\s]", "");
						String addr = "\""+addr2+"\"";
						String url = (String)business.get("url");
						String phone ="\""+(String)business.get("phone")+"\"";
						String city= "\""+(String)location.get("city")+"\"";
						String zipcode= "\""+(String)location.get("postal_code")+"\"";
						String state ="\""+(String)location.get("state_code")+"\"";
						
						
						Long b=businessId++;
						if(!bName.add(name)){
                        	System.out.println("ALREADY EXISTING!!!!! - "+name);
                        	b=map.get(name);
                        }
						else{
							map.put(name, b);
						}
						
						name="\""+name+"\"";
						
						
						
						
						for(int i=0;i<users.length;i++){
							int rate=getRandomNumberFrom(1, 5);
							dataset1=("{\"userid\":"+users[i]+",\"businessid\":"+b+",\"rating\":"+rate+",\"businessname\":"+name+",\"address\":"+addr+",\"city\":"+city+",\"statecode\":"+state+",\"zipcode\":"+zipcode+",\"Phone\":"+phone+",\"Category\":"+catList.toString())+"}";
							dataset2=(users[i]+","+b+","+rate);
							dataset3=(users[i]+","+b+","+rate);
							
							try {
								writeToLog(dataset1,"./YelpDataSet.txt");
								//writeToLog(dataset2,"./CleanData.csv");
								//writeToLog(dataset3,activityFileName);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							

							try {
								//writeToLog(dataset1,"./YelpDataSet.txt");
								writeToLog(dataset2,"./CleanData.csv");
								//writeToLog(dataset3,activityFileName);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							try {
								//writeToLog(dataset1,"./YelpDataSet.txt");
								//writeToLog(dataset2,"./CleanData.csv");
								writeToLog(dataset3,activityFileName);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							//System.out.println("ANSWER  "+dataset1);
						}

					}
				}
			}
		}





	}

}
