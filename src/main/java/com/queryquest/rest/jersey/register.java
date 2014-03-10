package com.queryquest.rest.jersey;

import java.io.Console;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MultivaluedMap;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.sf.json.JSONObject;

import com.queryquest.rest.jersey.Utility.MongoQueries;
import com.queryquest.rest.jersey.Utility.SQLQueries;
import com.queryquest.rest.jersey.domain.Login;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONException;


/**
 * Servlet implementation class register
 */
@WebServlet("/register")
public class register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		HttpSession session = request.getSession();
		String accessToken = request.getParameter("accessT");			
	     
		//(new Thread(new FacebookApi_Register(accessToken))).start();
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,Boolean.TRUE);
		Client client= Client.create(clientConfig);
		WebResource webResource = client.resource("https://graph.facebook.com/me/");
				     
	     MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
	     queryParams.add("access_token", accessToken);
	    
	     String s = webResource.queryParams(queryParams).get(String.class);
	     
	     JSONParser parser = new JSONParser();
	     Object obj = null;
		try {
			obj = parser.parse(s);
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) obj;
	     
	     Login login = new Login();
	     
	     if ((String) jsonObject.get("name")!=null)
	     {
	    	 login.setName((String) jsonObject.get("name"));
	     }
	     if ((String) jsonObject.get("email")!=null)
	     {
	    	 login.setEmail((String) jsonObject.get("email"));
	     }
	    
	     /*if ((String) jsonObject.get("password")!=null)
	     {
	    	 login.setPassword((String) jsonObject.get("password"));
	     }
	     */
	     
	     SQLQueries sqlQuery = new SQLQueries();
	     if(!sqlQuery.isUserAdded(login))
	     {
		     sqlQuery.insertUser(login);
		     sqlQuery.releaseConnection();
		     
		     MongoQueries mongoQuery = new MongoQueries();
		     mongoQuery.mongoConnect();
		     
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
		         JSONObject jsonObj = JSONObject.fromObject(str);
		         //System.out.println("Json Object: "+ jsonObj);
				 mongoQuery.mongoInsert(jsonObj);
				 
				 request.getRequestDispatcher("search.jsp").forward(request, response); 		
	     }
	     else
	     {
	    	 request.setAttribute("register_status", "already registered!");
	    	 request.getRequestDispatcher("loginFB.jsp").forward(request, response);
	     }
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
