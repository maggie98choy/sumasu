package com.queryquest.rest.jersey.Utility;

import java.net.UnknownHostException;




import org.json.simple.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;




public class MongoQueries {
	public DB db;
    public DBCollection collection;
    
    public void mongoConnect(){
		try{
			//MongoClient mongoClient = new MongoClient("ec2-54-193-76-21.us-west-1.compute.amazonaws.com",27017);
			MongoClient mongoClient = new MongoClient("127.0.0.1",27017);
			db = mongoClient.getDB("QueryQuest");
			collection = db.getCollection("tbl_user");
			
		}catch(UnknownHostException e){
			System.out.println("Exception : "+e);
		}
	}
    
    public void mongoInsert(JSONObject jsonObject){
    	BasicDBObject document = new BasicDBObject();
    	document.putAll(jsonObject);
    	collection.insert(document);
    }
    
    

}
