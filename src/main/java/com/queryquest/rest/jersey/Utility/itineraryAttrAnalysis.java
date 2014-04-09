package com.queryquest.rest.jersey.Utility;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.ArrayUtils;
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

public class itineraryAttrAnalysis 
{

	public String airPortLookUp(String cityName) throws SAXException, IOException, ParserConfigurationException
	{
		
		
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,Boolean.TRUE);
		Client client= Client.create(clientConfig);
		Node target_node = null;
		Node carrier_node = null;
		char[] charArrayCityName = cityName.toCharArray();
		//Character[] charObjectArray = ArrayUtils.toObject(charArrayCityName);		
		String sCityName = ""; 
		int tmp_noCarrier = 0 ;
		
		//To substitude space for '%'
		for (int i=0; i<cityName.length(); i++)
		{
			if (charArrayCityName[i] != ' ')
			{
				sCityName = sCityName + charArrayCityName[i];
			}
			else
			{
				sCityName = sCityName + "%20";
			}
		}
			
		WebResource webResource = client.resource("http://airports.pidgets.com/v1/airports?near="+sCityName+"+&n=10");
			 	
		String s = webResource.get(String.class);
			
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(s));
		Document doc = dBuilder.parse(is);
		doc.getDocumentElement().normalize();

		NodeList root_nodes = doc.getElementsByTagName("airport");

		 for (int i = 0; i < root_nodes.getLength(); i++) 
		 {
			 Node node = root_nodes.item(i);

			 if (node.getNodeType() == Node.ELEMENT_NODE) 
			 {
				 Element element = (Element) node;
				 NodeList nodes1 = element.getElementsByTagName("carriers").item(0).getChildNodes();
				 carrier_node = (Node) nodes1.item(0);
				 
				 if (Integer.parseInt(carrier_node.getNodeValue()) > tmp_noCarrier )
				 {
					 NodeList nodes2 = element.getElementsByTagName("code").item(0).getChildNodes();
					 target_node = (Node) nodes2.item(0);
					 tmp_noCarrier = Integer.parseInt(carrier_node.getNodeValue()) ;
				 }
				 
			 }
		 }
			return target_node.getNodeValue();			
	}
}
