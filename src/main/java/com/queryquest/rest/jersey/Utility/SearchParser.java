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

	private static final String[] Activities={"Aquarium","Archery","Arts","Beaches","Beauty Spa","Campgrounds","Casino","Events this weekend","Fishing spots","Fitness","Golf","Hiking","Museums","Nightlife","Rv park","Scuba","Shopping","Sport bars","Winery","Zoo"};
	private static final int ACTSIZE =3;
	
	public SearchAnalysis stringParser(String searchTerm){
		SearchAnalysis searchAnalysis = new SearchAnalysis();
		

		// GET the no_of_days
		Pattern pattern = Pattern.compile("(\\d+\\s*[dD]ay).*");
		Matcher m = pattern.matcher(searchTerm);
		if(m.matches()){

			searchAnalysis.setNumOfDays(Integer.parseInt(m.group(1).replaceAll("[\\D]", "")));
			searchTerm = searchTerm.replace(m.group(1), "");
			//System.out.println("String now is "+searchTerm);

		}else {
		    pattern = Pattern.compile(".*(\\d+\\s*[dD]ay).*");
		     m = pattern.matcher(searchTerm);
		     if(m.matches()){

					searchAnalysis.setNumOfDays(Integer.parseInt(m.group(1).replaceAll("[\\D]", "")));
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

		//GET the activity 
		ArrayList<String> actList = new ArrayList<String>();
		for(int i=0;i<Activities.length;i++){

			for(int j=0;j<search.size();j++){
				//System.out.println("activities - "+Activities[i]+ "search - "+search.get(j));

				if(Activities[i].contains(search.get(j))){
					actList.add(Activities[i]);
					System.out.println("Activity entered is "+search.get(j));
					search.remove(j);

				}
				else if(search.get(j).contains(Activities[i])){
					actList.add(Activities[i]);
					System.out.println("Activity1 entered is "+search.get(j));
					search.remove(j);

				}
			}
		}
		searchAnalysis.setActivity(actList);

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
    					break;
    				}
    			}
    		}
        }
        
        System.out.println(searchAnalysis.toString());
        return searchAnalysis;
	}
}
