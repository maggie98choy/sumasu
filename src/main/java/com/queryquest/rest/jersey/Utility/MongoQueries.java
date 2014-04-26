package com.queryquest.rest.jersey.Utility;

import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import static java.lang.Math.abs;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;
import com.queryquest.rest.jersey.domain.Rating;
import com.queryquest.rest.jersey.domain.SearchResult;




public class MongoQueries {
	public DB db;
    public DBCollection collection;
    private static final int ACTIVITY_TABLE= 1;
    private static final int RATING_TABLE =2;
    private static final int BUSINESS_TABLE=3;
    public void mongoConnect(){
		try{
			MongoClient mongoClient = new MongoClient("ec2-54-193-76-21.us-west-1.compute.amazonaws.com",27017);
			//MongoClient mongoClient = new MongoClient("127.0.0.1",27017);
			db = mongoClient.getDB("QueryQuest");
			collection = db.getCollection("tbl_user");
			
		}catch(UnknownHostException e){
			System.out.println("Exception : "+e);
		}
	}
    
    public void mongoConnect(int SecondTable){
    	try{
			MongoClient mongoClient = new MongoClient("ec2-54-193-76-21.us-west-1.compute.amazonaws.com",27017);
			//MongoClient mongoClient = new MongoClient("127.0.0.1",27017);
			db = mongoClient.getDB("QueryQuest");
			
			if(SecondTable == ACTIVITY_TABLE)
				collection = db.getCollection("tbl_activity");
			else if(SecondTable == RATING_TABLE)
				collection = db.getCollection("tbl_rating");
			else if(SecondTable == BUSINESS_TABLE)
				collection=db.getCollection("tbl_business");
			
		}catch(UnknownHostException e){
			System.out.println("Exception : "+e);
		}
    }
    
    
    
    public void mongoInsert(net.sf.json.JSONObject jsonObj){
    	BasicDBObject document = new BasicDBObject();
    	document.putAll(jsonObj);
    	collection.insert(document);
    }
    
   public void mongoUpdate(net.sf.json.JSONObject jsonObj, String email){
	   BasicDBObject newDocument = new BasicDBObject();
		BasicDBObject searchQuery = new BasicDBObject().append("email", email);
		newDocument.append("$set", jsonObj);
		collection.update(searchQuery, newDocument);
   }
   
   public String mongoGetLocation(String email){
	   BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("email",email);
		BasicDBObject fields = new BasicDBObject();
		fields.put("current_location", 1);
		DBCursor cursor = collection.find(whereQuery, fields);
		while (cursor.hasNext()) {
			String result = cursor.next().toString();
			JSONObject jsonObj = JSONObject.fromObject(result);
			return JSONObject.fromObject(jsonObj.get("current_location")).getString("name");
		}
		return "";
   }
    
    
  public void mongoUpdateActivities(String[] activities, String email){
	  
	  BasicDBObject whereQuery = new BasicDBObject();
	  BasicDBObject newDocument = new BasicDBObject();

	  whereQuery.put("email", email);
	  DBCursor cursor = collection.find(whereQuery);
	  if(cursor.hasNext()){
		newDocument.append("$set", new BasicDBObject().append("activities",activities));
		collection.update(whereQuery, newDocument);      
	  }else{
		  newDocument.put("email", email);
		  newDocument.put("activities", activities);
		  collection.insert(newDocument);
	  }	 
	  
  }
  
  public ArrayList<String> mongoGetActivities(String email){
	  BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("email",email);
		BasicDBObject fields = new BasicDBObject();
		fields.put("activities", 1);
		DBCursor cursor = collection.find(whereQuery, fields);
		ArrayList<String> activities = new ArrayList<String>();
		while (cursor.hasNext()) {
			String result = cursor.next().toString();
			JSONArray jsonArray = (JSONArray) JSONObject.fromObject(result).get("activities");
			
			System.out.println(result);
			if(!jsonArray.isEmpty()){
				for(int i=0;i<jsonArray.size();i++)
					activities.add(jsonArray.getString(i));
			}
			System.out.println(activities.toString());
			
		}
	    return activities; 
  }
  
  public void mongoUpdateStar(Rating rating){
	  BasicDBObject whereQuery = new BasicDBObject();
	  BasicDBObject newDocument = new BasicDBObject();

	  whereQuery.put("email", rating.getEmail());
	  whereQuery.put("business_name", rating.getBusinessName());
	  DBCursor cursor = collection.find(whereQuery);
	  if(cursor.hasNext()){
		  newDocument.append("$set", new BasicDBObject().append("rating", rating.getRating()));
		  collection.update(whereQuery, newDocument);      
	  }else{
		  newDocument.put("email", rating.getEmail());
		  newDocument.put("business_name", rating.getBusinessName());
		  newDocument.put("rating", rating.getRating());
		  newDocument.put("category", rating.getCategory());
		  collection.insert(newDocument);
	  }
  }
  
  public float mongoGetRecommendedRating(String businessName){
	  float avgRating =0; 
	  int num=0,rating=0;
	  BasicDBObject whereQuery= new BasicDBObject();
	  BasicDBObject fields = new BasicDBObject();
	  
	  whereQuery.put("business_name", businessName);
	  DBCursor cursor = collection.find(whereQuery);
	  
	  while(cursor.hasNext()){
		  num++;
		  rating += (Integer) cursor.next().get("rating");
	  }
	  
	  if(num ==0 ){
		//  System.out.println("Num is zero");
	  }
	  else  
		  avgRating = (float)rating/num;
	  
	  //System.out.println("Avg Rating "+avgRating);
	  return avgRating;
  }
  
  public int mongoGetRating(String email, String businessName){
	  BasicDBObject whereQuery = new BasicDBObject();
	  BasicDBObject fields = new BasicDBObject();

	  whereQuery.put("email", email);
	  whereQuery.put("business_name", businessName);
	  DBCursor cursor = collection.find(whereQuery);
	  if(cursor.hasNext()){
			fields.put("rating", 1);
			cursor = collection.find(whereQuery, fields);
			if(cursor.hasNext()){
				 int rating = (Integer) cursor.next().get("rating");
				/*System.out.println("RESULT "+result);
		  		JSONObject jsonObj = JSONObject.fromObject(result);
		  		String rating = JSONObject.fromObject(jsonObj.getInt("rating")).toString();
		  		System.out.println("rating "+rating);
		  		return Integer.parseInt(rating);*/
				 return rating;
			}
			
			return 0; 	
	  }
	  else{
		  return 0;
	  }
  }
  
  public ArrayList<JSONObject> mongoGetRatingByEmail(String email)
  {
	  BasicDBObject whereQuery = new BasicDBObject();

	  whereQuery.put("email", email);
	  DBCursor cursor = collection.find(whereQuery);		  
	  ArrayList<JSONObject> JSONObj_ArrayList = new ArrayList<JSONObject>();
	  
		while (cursor.hasNext()) 
		{
			String result = cursor.next().toString();
			JSONObject jsonObj = JSONObject.fromObject(result);
			JSONObj_ArrayList.add(jsonObj);
		 }   
		return JSONObj_ArrayList;
  }
  
  
  public String mongoGetBussIdByBussName(String bussName)
  {
	  BasicDBObject whereQuery = new BasicDBObject();
	  String bussId = "";
	
	  whereQuery.put("businessname", bussName);	  
	  DBObject obj = collection.findOne(whereQuery);  
	  
	  JSONObject jsonObj = JSONObject.fromObject(obj);
	  if (!jsonObj.isEmpty())
	  {
		  bussId = jsonObj.getString("businessid");
	  }
	 
	  return bussId;
  }
  
  
  public boolean mongoMatchCategory(String activity)
  {
	  boolean found = false;
	  
	  //manipulate keyword
	  char[] act_charArray = activity.toCharArray();
	  String activity_uppercase = "";
	  String activity_lowercase = "";
	  
	  if (!Character.isUpperCase(act_charArray[0]))
	  {
		  activity_lowercase = activity;
		  
		  for(int i=0; i<activity.length(); i++)
		  {
			  if (i==0)
			  {
				  activity_uppercase =Character.toString(Character.toUpperCase(act_charArray[0]));
			  }
			  else
				  activity_uppercase = activity_uppercase + act_charArray[i];
		  }
	  }
	  else
	  {
		  activity_uppercase = activity;
		  for(int i=0; i<activity.length(); i++)
		  {
			  if (i==0)
			  {
				  activity_lowercase =Character.toString(Character.toLowerCase(act_charArray[0]));
			  }
			  else
				  activity_lowercase = activity_lowercase + act_charArray[i];
		  }
	  }
	  
	  System.out.println("activity_uppercase:"+activity_uppercase);
	  System.out.println("activity_lowercase:"+activity_lowercase);
	  
	  //String[][] item =  {{"campgrounds","Campgrounds"}};
	  String[][] item =  {{activity_uppercase,activity_lowercase}};
	  DBObject inClause = BasicDBObjectBuilder.start()
			  .push("Category")
			  .add("$in", item)
			  .get();
	  
	  System.out.println("inClause:"+inClause.toString());
	  DBObject obj = collection.findOne(inClause);
	  
	  JSONObject jsonObj = JSONObject.fromObject(obj);
	  
	  //db.tbl_business.find({Category: {$in: [["Campgrounds", "campgrounds"]]}});
	  System.out.println("jsonObj"+jsonObj.toString());
	  if (!jsonObj.toString().equals("null"))
	  {
		  found = true;
	  }
	 
	  return found;
  }
  
  public SearchResult mongoGetBussDetailByBId(Long BId)
  {
	  BasicDBObject whereQuery = new BasicDBObject();
	  SearchResult result = new SearchResult();
	  
	  int i_BId;
	  int total_rating = 0 ;
	  int avg_rating = 0;
	  int count = 0;
	  
	  i_BId = BId.intValue();
	  whereQuery.put("businessid", i_BId);
	  DBCursor cursor = collection.find(whereQuery);
	  JSONObject jsonObj=null;	   
	  while(cursor.hasNext())
	  {
		  String queryResult = cursor.next().toString();
		  jsonObj = JSONObject.fromObject(queryResult);
		  //count++;
		  //total_rating += (Integer) cursor.next().get("rating");
		//  System.out.println(cursor.curr().get("businessname") +" is " +"rating:" +cursor.curr().get("rating"));
		  
	  }
	  
	  //float a = (float)total_rating/count;
	 // System.out.println("float a  :" +a);
	  //double value = (double) total_rating/count;
	 // avg_rating = (int) Math.rint(value);
	  
	 // System.out.println("rounded rating :" +avg_rating);
	 
	 // result.setNoOfStars(avg_rating);
	  //String queryResult = cursor.next().toString();
	 // String queryResult = cursor.curr().toString();
	  //JSONObject jsonObj = JSONObject.fromObject(queryResult);
	
	  //System.out.println("obj: "+jsonObj);

	  if (jsonObj != null)
	  {
		  result.setName(jsonObj.getString("businessname"));
		  result.setAddress(jsonObj.getString("address"));
		  result.setCategory(jsonObj.getString("Category"));
		  result.setPhoneNo(jsonObj.getString("Phone"));
	  }
	 
	  return result;
  }
  
  
}
