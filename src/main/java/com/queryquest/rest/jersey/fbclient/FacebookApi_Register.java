package com.queryquest.rest.jersey.fbclient;

import javax.ws.rs.core.MultivaluedMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;








/*import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;*/
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.core.util.MultivaluedMapImpl;
//import com.queryquest.rest.jersey.Utility.DBConnection;
import com.queryquest.rest.jersey.Utility.MongoQueries;
import com.queryquest.rest.jersey.Utility.SQLQueries;
import com.queryquest.rest.jersey.domain.*;

import facebook4j.Account;
import facebook4j.Checkin;
import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.ResponseList;
import facebook4j.auth.AccessToken;
import facebook4j.internal.org.json.JSONArray;

public class FacebookApi_Register implements Runnable {
	private String accessToken;
	//private static DBConnection dbConn = null;
	//private static Connection connect = null;
	//private static DB db;
	//private static DBCollection table;
	//private static BasicDBObject document;
	
	public FacebookApi_Register(String accessToken){
		this.accessToken= accessToken;

	}
	
    public void run()
    {
    	facebookBasic();
    }
	
	public void facebookBasic() 
	{
	
	try{	
		
		//String accessToken = "CAACEdEose0cBAG26PlzcMVxqNNrxNGVZCdFxC4rrAM8FvBYVndcNYg8IoInjqgQwPZALoA32F85jMUOq2EVlLylScoTRxzPzb9TlxZAcMuSEuaeZAwGd1Raey5iA6ojTjtfoJGKKoH5D2XA4tjxuyezh0UACFGIaa59RDJPlIjHOjomxiFZBu4tYZCGBKat9kZD";
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,Boolean.TRUE);
		Client client= Client.create(clientConfig);
		WebResource webResource = client.resource("https://graph.facebook.com/me/");
				     
	     MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
	     queryParams.add("access_token", accessToken);
	    
	     String s = webResource.queryParams(queryParams).get(String.class);
	     
	     JSONParser parser = new JSONParser();
	     Object obj = parser.parse(s);
	     JSONObject jsonObject = (JSONObject) obj;
	     
	     Login login = new Login();
	     
	     if ((String) jsonObject.get("name")!=null)
	     {
	    	 login.setName((String) jsonObject.get("name"));
	     }
	     if ((String) jsonObject.get("email")!=null)
	     {
	    	 login.setEmail((String) jsonObject.get("email"));
	     }
	    /* if ((String) jsonObject.get("password")!=null)
	     {
	    	 login.setEmail((String) jsonObject.get("password"));
	     }
	     */
	     SQLQueries sqlQuery = new SQLQueries();
	     sqlQuery.insertUser(login);
	     sqlQuery.releaseConnection();
	     
	     MongoQueries mongoQuery = new MongoQueries();
	     mongoQuery.mongoConnect();
	     mongoQuery.mongoInsert(jsonObject);
	     //Establish connection to MYSQL DB
		 /*dbConn = new DBConnection();	     
		 connect = dbConn.getMySqlDB();*/
		 
		 //Check if user_name exist in tbl_login
		 /*String qry = "select count(*) from tbl_login where user_name = '"+login.getEmail().substring(0, login.getEmail().indexOf("@"))+"'";
		 Statement stmt;
		 stmt = connect.createStatement();
		 ResultSet rs = stmt.executeQuery(qry);
		 
		 int count = 0 ;
		 while (rs.next())
		 {
			 count = rs.getInt(1);
		 }
		 
		 if (count==0)
		 {
		     PreparedStatement preparedStatement = null;
		     
		     preparedStatement = connect.prepareStatement("insert into tbl_login values (default, ?, ?, ?, ?)");*/
		     //QueryQuest.tbl_login (login_id,user_name,password,name,email)
		     
		   /*  preparedStatement.setString(1, login.getEmail().substring(0, login.getEmail().indexOf("@")));
		     preparedStatement.setString(2, "");
		     preparedStatement.setString(3, login.getName());
		     preparedStatement.setString(4, login.getEmail());
		     preparedStatement.executeUpdate();
	     
		     //Establish connection to MongoDB
		     db = dbConn.getMongoDB();
		     table = db.getCollection("tbl_user");
		     document = new BasicDBObject();		 
		     document.putAll(jsonObject);
		     table.insert(document);
		}
		 if (connect != null)
		 {
			connect.close();
		 }*/
	     
	     Facebook facebook = new FacebookFactory().getInstance();
	     facebook.setOAuthAppId("359402037530890", "6042f096a39b44c8b923c7dff1f15bc9");
	     facebook.setOAuthAccessToken(new AccessToken(accessToken, null));
	     System.out.println("acess token:"+accessToken);
	     
	  // Single FQL
	     //String query = "SELECT name FROM user WHERE uid IN (SELECT uid1 FROM friend WHERE uid2=me()) AND 'San Jose' IN education";
	     String query = "SELECT uid from user where uid = me()";
	     JSONArray jsonArray = facebook.executeFQL(query);
	     for (int i = 0; i < jsonArray.length(); i++) {
	         facebook4j.internal.org.json.JSONObject jsonObject1 = jsonArray.getJSONObject(i);
	         System.out.println(jsonObject1.get("uid"));
	     }

	}catch(Exception e){
		e.printStackTrace();
	}
}
		
	
}
