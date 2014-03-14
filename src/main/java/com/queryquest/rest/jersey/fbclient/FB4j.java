package com.queryquest.rest.jersey.fbclient;

import com.queryquest.rest.jersey.domain.Login;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONException;

public class FB4j {
	
	public String getDataFromUser(String accessToken,Login login){
		
		Facebook facebook = new FacebookFactory().getInstance();
	     facebook.setOAuthAppId("359402037530890", "6042f096a39b44c8b923c7dff1f15bc9");
	     facebook.setOAuthAccessToken(new AccessToken(accessToken, null));
	     System.out.println("acess token:"+accessToken);
	     
	     String query = "SELECT email, name, birthday_date, current_location,affiliations,tv,music,religion, sex, hometown_location, relationship_status, political, "
	     		      + "activities, interests, tv, movies, books, quotes, about_me, hs_info, education_history, work_history, notes_count, status, online_presence, "
	     		      + "locale, proxied_email, profile_url, family  from user where uid = me()";
	     JSONArray jsonArray = null;
		try {
			jsonArray = facebook.executeFQL(query);
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		facebook4j.internal.org.json.JSONObject jsonObject1 = null;
	     for (int i = 0; i < jsonArray.length(); i++) 
	     {
	    	try 
			{
				 jsonObject1 = jsonArray.getJSONObject(i);										
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    
			//System.out.println(jsonObject1);
	     }
	     try {
			jsonObject1.put("user_name",login.getEmail().substring(0, login.getEmail().indexOf("@")).toString());
		 } catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	     
	      query = "select name,type from page where page_id in (SELECT page_id from page_fan where uid = me())";
	      jsonArray = null;
			 
	      try 
			 {
				jsonArray = facebook.executeFQL(query);
			} catch (FacebookException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         
		  
	        try {
				jsonObject1.put("fan", jsonArray.toString() );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         String str = jsonObject1.toString();
	         return str;
	}
	

}
