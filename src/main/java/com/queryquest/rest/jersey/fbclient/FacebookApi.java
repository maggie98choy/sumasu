package com.queryquest.rest.jersey.fbclient;


import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.oauth.signature.OAuthParameters;

public class FacebookApi implements Runnable {
	private String accessToken;
	
	public FacebookApi(String accessToken){
		this.accessToken= accessToken;
	}
	
    public void run() {
    	facebookBasic();
    }
	
	public void facebookBasic(){
	
	try{	
		//String accessToken = "CAACEdEose0cBAG26PlzcMVxqNNrxNGVZCdFxC4rrAM8FvBYVndcNYg8IoInjqgQwPZALoA32F85jMUOq2EVlLylScoTRxzPzb9TlxZAcMuSEuaeZAwGd1Raey5iA6ojTjtfoJGKKoH5D2XA4tjxuyezh0UACFGIaa59RDJPlIjHOjomxiFZBu4tYZCGBKat9kZD";
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,Boolean.TRUE);
		Client client= Client.create(clientConfig);
		WebResource webResource = client.resource("https://graph.facebook.com/me/");
		
	     MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
	     queryParams.add("access_token", accessToken);
	    
	     String s = webResource.queryParams(queryParams).get(String.class);
	     System.out.println("url = "+s);
	    /* ClientResponse response= webResource.get(ClientResponse.class);
	     
	     
	     String output = response.getEntity(String.class);
	     System.out.println("Server output...");
	     System.out.println (output);*/
	}catch(Exception e){
		e.printStackTrace();
	}
 
		
	}
}
