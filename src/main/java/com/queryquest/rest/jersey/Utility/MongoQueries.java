package com.queryquest.rest.jersey.Utility;

import java.net.UnknownHostException;






import org.bson.BSONObject;
import net.sf.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;




public class MongoQueries {
	public DB db;
    public DBCollection collection;
    
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
			return JSONObject.fromObject(jsonObj.get("current_location")).getString("state");
		}
		return "";
   }
    

}
