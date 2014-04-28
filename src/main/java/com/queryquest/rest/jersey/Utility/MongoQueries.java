package com.queryquest.rest.jersey.Utility;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import static java.lang.Math.abs;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.mongodb.BasicDBList;
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
  
  public ArrayList<JSONObject> mongoGetOldMemories(String email, String activity){
	  BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("email",email);
		DBCursor cursor = collection.find(whereQuery);
		ArrayList<JSONObject> JSONObj_ArrayList = new ArrayList<JSONObject>();
		  
		while (cursor.hasNext()) 
		{
			String result = cursor.next().toString();
			JSONObject jsonObj = JSONObject.fromObject(result);
			JSONObj_ArrayList.add(jsonObj);
		 }
		
		if(activity == null)
			return JSONObj_ArrayList;
		else{
			ArrayList<JSONObject> newJSONObj_ArrayList = new ArrayList<JSONObject>();
			JSONObject jsonObject = null;
			for(int x=0; x < JSONObj_ArrayList.size(); x++){
				jsonObject = (JSONObject) JSONObj_ArrayList.get(x);
				String act= jsonObject.getString("activity");
				String[] cat = act.split(",");
                int match=0;
				for(int z=0;z<cat.length;z++){
				if((cat[z].toLowerCase().replaceAll("\\W", "")).equals(activity.toLowerCase())){
					match=1;
					break;
				}
				if(((cat[z].toLowerCase().replaceAll("\\W", "")).contains(activity.toLowerCase()))){
					match=1; 
				//	System.out.println("CAT "+catList.get(i));
					break;

				}
				else if(activity.toLowerCase().contains(cat[z].toLowerCase().replaceAll("\\W", ""))){
					//System.out.println("acti " +activity.toLowerCase());
					//System.out.println("cat "+cat[j].toLowerCase().replaceAll("\\W", ""));
					match=1; 
		//			System.out.println("CATi "+catList.get(i));
					break;
				}
				}
				
				if (match==1){
					newJSONObj_ArrayList.add(jsonObject);
				}
			}
			
			return newJSONObj_ArrayList;
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
		  newDocument.put("activity",rating.getActivity());
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
   
  
  public SearchResult mongoGetBussDetailByBId(Long BId, String activity)
  {
	  BasicDBObject whereQuery = new BasicDBObject();
	  SearchResult result = new SearchResult();
	  
	  int i_BId;
	 
	  
	  i_BId = BId.intValue();
	  whereQuery.put("businessid", i_BId);
	  DBObject object = collection.findOne(whereQuery);
	  JSONObject jsonObj=null;	   
	  
	  System.out.println("i_BId"+i_BId);
	  jsonObj = JSONObject.fromObject(object);
	  System.out.println("jsonObj"+jsonObj.toString());
	  if (!jsonObj.isEmpty())
	  {
		  result.setName(jsonObj.getString("businessname"));
		  result.setAddress(jsonObj.getString("address"));
		  result.setCategory(jsonObj.getString("Category"));
		  result.setPhoneNo(jsonObj.getString("Phone"));
		  result.setStateCode(jsonObj.getString("statecode"));
		  result.setActivity(activity);
		  
	  }
	 
	  return result;
  }
  
  public ArrayList<SearchResult> mongoGetBussDetailByBId(ArrayList<Long> BidList,ArrayList<String> actList )
  {
	  ArrayList<SearchResult> result_list=  new ArrayList<SearchResult>();
	  SearchResult searchresult = new SearchResult();
	  int[] Bid_IntList = new int[BidList.size()];	  
	  for (int i=0; i<BidList.size();i++)
	  {
		  Bid_IntList[i] = BidList.get(i).intValue();
		  //System.out.println("Bid_IntList[i]"+Bid_IntList[i]);  
	  }
	  
	  String activity_lowercase = "";
	  String activity_uppercase = "";
	  String [][] activities = new String [actList.size()][2];
	  
	  for (int i=0; i<actList.size();i++)
	  {
		  char[] act_charArray = actList.get(i).toCharArray();
		  System.out.println("i: "+i);
		  if (!Character.isUpperCase(act_charArray[0]))
		  {
			  activity_lowercase = actList.get(i);
			  
			  for(int j=0; j<actList.get(i).length(); j++)
			  {
				  if (j==0)
				  {
					  activity_uppercase =Character.toString(Character.toUpperCase(act_charArray[0]));
				  }
				  else
					  activity_uppercase = activity_uppercase + act_charArray[j];
			  }		 
		  }
		  else
		  {
			  activity_uppercase = actList.get(i);
			  for(int k=0; k<actList.get(i).length(); k++)
			  {
				  if (k==0)
				  {
					  activity_lowercase =Character.toString(Character.toLowerCase(act_charArray[0]));
				  }
				  else
					  activity_lowercase = activity_lowercase + act_charArray[k];
			  }
			 
		  }
		  activities[i][0]=activity_uppercase;
		  activities[i][1]=activity_lowercase;

	  }
	  
	  DBObject clause1 = BasicDBObjectBuilder.start()
			  .push("businessid")
			  .add("$in", Bid_IntList)
			  .get();
	  
	  DBObject clause2 = BasicDBObjectBuilder.start()
			  .push("Category")
			  .add("$in", activities)
			  .get();
	  	  	  
	  BasicDBList and = new BasicDBList();
	  and.add(clause1);
	  and.add(clause2);
	  DBObject query = new BasicDBObject("$and", and);	  
	  
	  System.out.println("AND Clause:"+query.toString());
	  DBCursor cursor = collection.find(query);	  
	  HashMap<String, Integer> cache = new HashMap<String, Integer>();
	  //System.out.println("count"+cursor.count());	
	  while (cursor.hasNext()) 
	  {
		  
		  String result = cursor.next().toString();		
		JSONObject jsonObj = JSONObject.fromObject(result);
		if (!cache.containsValue((Integer) jsonObj.get("businessid")))
		{
			//System.out.println("Bid"+jsonObj.get("businessid"));
			searchresult.setName(jsonObj.getString("businessname"));
			searchresult.setAddress(jsonObj.getString("address"));
			searchresult.setCategory(jsonObj.getString("Category"));
			searchresult.setPhoneNo(jsonObj.getString("Phone"));
			searchresult.setStateCode(jsonObj.getString("statecode"));
			cache.put("businessid"+jsonObj.get("businessid"), (Integer) jsonObj.get("businessid"));	
			result_list.add(new SearchResult(searchresult.getName(), searchresult.getAddress(), searchresult.getCategory(), searchresult.getPhoneNo(), searchresult.getStateCode()));
			System.out.println("searchresult"+searchresult.toString());
		}
	    
	  } 
	 // System.out.println("cache: "+cache.toString());
	  
	  return result_list;
	  
	  //db.tbl_business.find({"businessid":{$in:[35,3827]},"Category": {$in: [["Campgrounds", "campgrounds"]]}})
	  //db.tbl_business.find({"businessid":{$in:[29,26]},"Category": {$in: [["Campgrounds", "campgrounds"]]}})

  }
  
  
  
}
