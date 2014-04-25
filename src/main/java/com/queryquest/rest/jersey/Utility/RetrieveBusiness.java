package com.queryquest.rest.jersey.Utility;

import java.util.ArrayList;
import com.queryquest.rest.jersey.domain.SearchResult;
import net.sf.json.JSONObject;


public class RetrieveBusiness 
{
	
	//public ArrayList<SearchResult> getBusinessNames(String email, ArrayList<String> actList)
	public static void main (String[] argv)
	{
		String email ="sumanap85@gmail.com";
		String activity ="hiking";
		String category = null;
		boolean rating = true;
				
		MongoQueries mongo = new MongoQueries();
		mongo.mongoConnect();
		mongo.mongoConnect(2);
		
		ArrayList<JSONObject> JSONObj_ArrayList = new ArrayList<JSONObject>();
		ArrayList<String> listBussName = new ArrayList<String>();
		JSONObject jsonObject = null;		 	
		JSONObj_ArrayList = mongo.mongoGetRatingByEmail(email);	
		 	
		//Step 1: find all business name from tbl_rating base on user enter keyword or preselected preferences
		for(int x=0; x < JSONObj_ArrayList.size(); x++)
		{
			jsonObject = (JSONObject) JSONObj_ArrayList.get(x);
			System.out.println("jsonObject:"+jsonObject);
			int score = jsonObject.getInt("rating");
			
			//condition 1: get all business name where rating >= 3 and category = keyword;
			if (score >=3 )
			{
				String str = getBusinessName(jsonObject, activity, true);
				if (!str.equals(""))
				{
					listBussName.add(str);
				}	
		 	}
		}
	  	 		
		if (listBussName.size() != 0)
		{
			System.out.println("Bussiness name in condition 1: rating >=3 and category found in tbl_rating:"+listBussName.toString());
		}
		else
		{
			
			//condition 2: Get all business name where rating < 3 and category = keyword;
			for(int y=0; y < JSONObj_ArrayList.size(); y++)
			{
				jsonObject = (JSONObject) JSONObj_ArrayList.get(y);	
				int score = jsonObject.getInt("rating");

				if (score < 3 )
				{
					String str = getBusinessName(jsonObject, activity, true);
					if (!str.equals(""))
					{
						listBussName.add(str);
						rating = false;
					}	
				}
			}
			
			if (listBussName.size() != 0)
				System.out.println("Bussiness name in condition 2: rating <3 and category found in tbl_rating:"+listBussName.toString());
			
		}
			
		if (listBussName.size() == 0)	
		{
			//condition 3: get all business name where category != keyword and rating>3 ;
			for(int y=0; y < JSONObj_ArrayList.size(); y++)
			{
				jsonObject = (JSONObject) JSONObj_ArrayList.get(y);	
				int score = jsonObject.getInt("rating");

				if (score >=3 )
				{
					String str = getBusinessName(jsonObject, activity, false);
					if (!str.equals(""))
					{
						listBussName.add(str);
					}
				}
			}
			if (listBussName.size() != 0)
			{
				System.out.println("Bussiness name in condition 3: rating >3 and category not found in tbl_rating"+listBussName.toString());
			}
		}
		
		
		//Step 2: search all businessId by found businessName array list
		if (listBussName.size() != 0)
		{
			mongo.mongoConnect(3);
			jsonObject = null;
			ArrayList<Long> bussId_arraylist = new ArrayList<Long>();	
			ArrayList<Long> remBussId_arraylist = new ArrayList<Long>();
			
			for(int i=0; i<listBussName.size(); i++)
			{
				Long bussId = Long.parseLong(mongo.mongoGetBussIdByBussName(listBussName.get(i)));
				if (!bussId.equals(""))
				{
					bussId_arraylist.add(bussId);
				}
			}
			System.out.println("bussId_arraylist:"+ bussId_arraylist.toString());
			
			// check if user entered keyword found in mongodb 
			mongo.mongoConnect(3);
			boolean found = mongo.mongoMatchCategory(activity);			
			System.out.println("Found:" + found);
			
			if(found)
			{
				category = activity;
			}
			
			//TODO: Call Sush's recommendation engine
			//Sush, uncomment this function when calling recommendation engine 
			
			//remBussId_arraylist = recommendation(category, bussId_arraylist);
			
			//TODO: Call Sush's recommendation engine
			
			//for testing
			/*remBussId_arraylist.add((long) 391);
			remBussId_arraylist.add((long) 389);
			remBussId_arraylist.add((long) 960);
			remBussId_arraylist.add((long) 368);
			remBussId_arraylist.add((long) 370);
			remBussId_arraylist.add((long) 373);
			 */
			
			if (remBussId_arraylist.size() != 0 )				
			{
				ArrayList<SearchResult> result_arraylist = new ArrayList<SearchResult>();
				SearchResult result = new SearchResult();
				
				mongo.mongoConnect(3);
				
				if (rating)
				{
					for (int i=0; i<remBussId_arraylist.size(); i++)
					{
						result = mongo.mongoGetBussDetailByBId(remBussId_arraylist.get(i));
						if (result!=null)
						{
							result_arraylist.add(result);
						}
					}
					System.out.println("result_arraylist:"+ result_arraylist.toString());
				}
				else
				{
				    // get from bottom 5 of the list
					int j = 0;
					for (int i=remBussId_arraylist.size()-1; i>=0;i--)
					{
						if (j==5)
						{
							break;
						}
						
						result = mongo.mongoGetBussDetailByBId(remBussId_arraylist.get(i));
						
						if (result!=null)
						{
							result_arraylist.add(result);
						}
						j++;
					}
				
					System.out.println("result_arraylist:"+ result_arraylist.toString());
				}
			}
						
		}
		else
		{
			//No business name found in user rating table
			// no calling to recommendation engine in this case
			//return business list = null;
		}	
		
	}
		

	public static String getBusinessName(JSONObject jsonObject, String activity, boolean NoOppositeCat)
	{
		
		String catstr = jsonObject.getString("category");
		String bussName = ""; 
		System.out.println("catstr:"+catstr);
		char[] cat_charArray = catstr.toCharArray();
		String new_catStr = "";
		boolean found = true;
		
		for(int c=0; c<catstr.length(); c++)
		{
			if(cat_charArray[c] != '[' && cat_charArray[c] !='"' && cat_charArray[c]!=']')
			{
				new_catStr = new_catStr + cat_charArray[c] ;
			}
		}
		
		System.out.println("new_catStr:"+new_catStr);				
		String[] cat_array= new_catStr.split(",");		

		found =  true;

		for (int b=0; b < cat_array.length; b++)
		{
			if (NoOppositeCat)
			{
				if (activity.equals(cat_array[b]))
				{
					System.out.println("category:"+cat_array[b]);						
					bussName = jsonObject.getString("business_name");
				}
			}
			else
			{
				if (activity.equals(cat_array[b]))
				{
					System.out.println("category:"+cat_array[b]);						
					found = true;
					break;
				}
				else
				{
					if (activity.equals(cat_array[b]))
					{
						System.out.println("Opposite category:"+cat_array[b]);
						found = false;
					}
				}
			}
		}	
		if (!found)
		{
			bussName = jsonObject.getString("business_name");
		}		
		
		return bussName;
	}
	
}
