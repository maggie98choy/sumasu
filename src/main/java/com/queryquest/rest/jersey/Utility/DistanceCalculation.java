package com.queryquest.rest.jersey.Utility;


import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class DistanceCalculation
{
	
	final String GOOGLE_MAP_API_KEY ="AIzaSyDi2fc_tU8lT3TZcsoV8yFTphKLqLsB3IA";
	  
	public int calculateDistance (String city1, String city2) throws IOException, SAXException, ParserConfigurationException
	{	
		
		//String citya ="SanFrancisco,California";
		//String cityb = "Losangeles";
		
		String s_distance="";
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,Boolean.TRUE);
		Client client= Client.create(clientConfig);

		String cityName1 = city1; 
		char[] charArrayCityName1 = cityName1.toCharArray();
		String sCityName1 = ""; 
		
		//To substitude space for '%'
		for (int i=0; i<cityName1.length(); i++)
		{
			if (charArrayCityName1[i] != ' ')
			{
				sCityName1 = sCityName1 + charArrayCityName1[i];
			}

		}
		
		String cityName2 = city2; 
		char[] charArrayCityName2 = cityName2.toCharArray();
		String sCityName2 = ""; 
		
		//To substitude space for '%'
		for (int i=0; i<cityName2.length(); i++)
		{
			if (charArrayCityName2[i] != ' ')
			{
				sCityName2 = sCityName2 + charArrayCityName2[i];
			}

		}
		
		WebResource webResource = client.resource("http://maps.googleapis.com/maps/api/distancematrix/xml?origins="+sCityName1+"&units=imperial&destinations="+sCityName2+"&sensor=false");
		String s = webResource.get(String.class);
		
		System.out.println("webResource: "+webResource);	
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(s));
		Document doc = dBuilder.parse(is);
		doc.getDocumentElement().normalize();
	
		NodeList root_nodes = doc.getElementsByTagName("distance");		
		
		for (int i = 0; i < root_nodes.getLength(); i++) 
		{
			 Node node = root_nodes.item(i);

			 if (node.getNodeType() == Node.ELEMENT_NODE) 
			 {
				 Element element = (Element) node;
				 //distance = Integer.parseInt(element.getElementsByTagName("text").item(0).getTextContent());				 
				 s_distance = element.getElementsByTagName("text").item(0).getTextContent();
			 }
		 }	
		 		
		char[] charArrayDistance = s_distance.toCharArray();
		String new_s_distance = "";
		//substring s_distance to integer only value
		for (int i=0; i<s_distance.length(); i++)
		{
			if (charArrayDistance[i] != ',' && charArrayDistance[i] != ' ' && charArrayDistance[i] != 'm' && charArrayDistance[i] != '.')
			{
				new_s_distance = new_s_distance + charArrayDistance[i];
			}
			else
			{
				if (charArrayDistance[i] == ' ' || charArrayDistance[i] == 'm' ||charArrayDistance[i] == '.')
				{
					break;
				}
			}
		}
		
		System.out.println("new distance:"+new_s_distance);
		
		if (new_s_distance.equals("0"))
		{
			new_s_distance="1";
		}
		
		if (new_s_distance.equals(""))
		{
			new_s_distance="0";
		}
		
		return Integer.parseInt(new_s_distance);
		
	}
	    
}
