package com.queryquest.rest.jersey.Utility;

import java.util.ArrayList;

import com.queryquest.rest.jersey.domain.SearchResult;

import net.sf.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class RetrieveBusiness 
{

	public ArrayList<SearchResult> getBusinessNames(String email,String activity, String stateCode)
	//public static void main (String[] argv)
	{
		//String email ="sumanap85@gmail.com";
		//String activity ="hiking";
		String category = null;
		boolean rating = true;

		MongoQueries mongo = new MongoQueries();
		//mongo.mongoConnect();
		mongo.mongoConnect(2);

		ArrayList<SearchResult> result_arraylist = new ArrayList<SearchResult>();

		ArrayList<JSONObject> JSONObj_ArrayList = new ArrayList<JSONObject>();
		ArrayList<String> listBussName = new ArrayList<String>();
		JSONObject jsonObject = null;
		boolean found=false;
		JSONObj_ArrayList = mongo.mongoGetRatingByEmail(email);	
		//Step 1: find all business name from tbl_rating base on user enter keyword or preselected preferences
		for(int x=0; x < JSONObj_ArrayList.size(); x++)
		{
			jsonObject = (JSONObject) JSONObj_ArrayList.get(x);
			System.out.println("jsonObject:"+jsonObject);
			int score = jsonObject.getInt("rating");

			if(activity==null)
				break;
			//condition 1: get all business name where rating >= 3 and category = keyword;
			if (score >=3 )
			{
				String str = getBusinessName(jsonObject, activity, true);
				if (!str.equals(""))
				{
					listBussName.add(str);
					found=true;
					category=activity;
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

				if(activity == null)
					break;
				if (score < 3 )
				{
					String str = getBusinessName(jsonObject, activity, true);
					if (!str.equals(""))
					{
						listBussName.add(str);
						found=true;
						category=activity;
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
						found=false;
						category=null;
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
			/*mongo.mongoConnect(3);
					boolean found = mongo.mongoMatchCategory(activity);			
					System.out.println("Found:" + found);

					if(found)
					{
						category = activity;
					}*/

			System.out.println("--------------sush---------------");
			System.out.println("category "+category);
			System.out.println("business IDs : ");
			for(Long id:bussId_arraylist )
			{
				System.out.println(id);
			}
			System.out.println("-------------------------------------");
			//TODO: Call Sush's recommendation engine
			//Sush, uncomment this function when calling recommendation engine 

			try {
				remBussId_arraylist = ItemRecommendation(category, bussId_arraylist);
			} catch (TasteException e) {
				System.out.println(" error with taste recommendation ");
				e.printStackTrace();
				
			}

			//TODO: Call Sush's recommendation engine
			//Sush, uncomment this function when calling recommendation engine 


			//TODO: Call Sush's recommendation engine

			//for testing
			/*remBussId_arraylist.add((long) 391);
			remBussId_arraylist.add((long) 389);
			remBussId_arraylist.add((long) 960);
			remBussId_arraylist.add((long) 368);
			remBussId_arraylist.add((long) 370);
			remBussId_arraylist.add((long) 373);
			 */

			if(remBussId_arraylist!=null ){
				if (remBussId_arraylist.size() != 0 )				
				{
					SearchResult result = new SearchResult();

					mongo.mongoConnect(3);

					if (rating)
					{  
						int count=0;

						if(found==false && activity !=null){
							ArrayList<SearchResult> resultList= new ArrayList<SearchResult>();
							for (int i=0; i<30/*remBussId_arraylist.size()*/; i++)
							{
							   	
								if(count==5)
									break;
								result = mongo.mongoGetBussDetailByBId(remBussId_arraylist.get(i),activity);
								if (result!=null)
								{
                                  resultList.add(result);
									String result_category= result.getCategory();
									String result_statecode=result.getStateCode();
								
									if(result.getCategory()!=null){
										if(MatchCategory(activity,result_category)){
											if(result_statecode.equals(stateCode)){
												result_arraylist.add(result);
												count++;
											}
										}
									}
								}
							}
							System.out.println("result_arraylist:"+ result_arraylist.toString());
                            if(result_arraylist.isEmpty()){
                            	for (int i=0; i<resultList.size(); i++)
                            	{
    								if(count==5)
    									break;
    								result = resultList.get(i);
    								if (result!=null)
    								{

    									String result_category= result.getCategory();
    								
    									if(result.getCategory()!=null){
    										if(MatchCategory(activity,result_category)){
					    						result_arraylist.add(result);
    											count++;
    											
    										}
    									}
    								}
    							}
    							System.out.println("result_arraylist:"+ result_arraylist.toString());

                            }
						}else if(found==false && activity == null){

								for (int i=0; i<10; i++)
								{
									result = mongo.mongoGetBussDetailByBId(remBussId_arraylist.get(i),activity);
									if (result!=null)
									{
										result_arraylist.add(result);

									}	

								}
								System.out.println("result_arraylist:"+ result_arraylist.toString());


							}
	
						else if(found==true){
							count=0;
							for (int i=0; i<remBussId_arraylist.size(); i++)
							{
								if(count==5)
									break;
								result = mongo.mongoGetBussDetailByBId(remBussId_arraylist.get(i),activity);
								if (result!=null)
								{

									String result_statecode=result.getStateCode();
								
									if(result_statecode.equals(stateCode)){
										result_arraylist.add(result);
										count++;
									}

								}
							}
							System.out.println("result_arraylist:"+ result_arraylist.toString());
						}
					}
					else
					{
						// get from bottom 5 of the list
						int j = 0;
						for (int i=remBussId_arraylist.size()-1; i>=remBussId_arraylist.size()-10;i--)
						{
							if (j==5)
							{
								break;
							}

							result = mongo.mongoGetBussDetailByBId(remBussId_arraylist.get(i),activity);

							if (result!=null)
							{
								String result_statecode=result.getStateCode();
								
								if(result_statecode.equals(stateCode)){
									result_arraylist.add(result);
									j++;
								}

							}
						}

						System.out.println("result_arraylist:"+ result_arraylist.toString());
					}
				}
			}
		}
		else
		{
			return null;
			//No business name found in user rating table
			// no calling to recommendation engine in this case
			//return business list = null;
		}	

		return result_arraylist;

	}


	public boolean MatchCategory(String activity, String result_category)
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

		//System.out.println("activity_uppercase:"+activity_uppercase);
		//System.out.println("activity_lowercase:"+activity_lowercase);

		String[] result_actList= result_category.split(",");
		for(int i=0;i<result_actList.length;i++){
			//System.out.println("RESULT - "+result_actList[i].toLowerCase().replaceAll("\\W", "")+ " ACTIVITY - "+activity_lowercase);
			if(activity_lowercase.equals(result_actList[i].toLowerCase().replaceAll("\\W", ""))){
				//  System.out.println("TRUE");
				return true;
			}
		}

		return found;
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

		//System.out.println("new_catStr:"+new_catStr);				
		String[] cat_array= new_catStr.split(",");		

		found =  true;

		for (int b=0; b < cat_array.length; b++)
		{
			if (NoOppositeCat)
			{
				if (activity.toLowerCase().equals(cat_array[b].toLowerCase()))
				{
					System.out.println("category:"+cat_array[b]);						
					bussName = jsonObject.getString("business_name");
				}
			}
			else
			{
				if(activity==null){
					found=false;
					break;
				}

				else if (activity.equals(cat_array[b]))
				{
					System.out.println("category:"+cat_array[b]);						
					found = true;
					break;
				}
				else
				{
					if (!activity.equals(cat_array[b]))
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

	public static ArrayList<Long> ItemRecommendation(String category,ArrayList<Long> businessId ) throws TasteException
	{
		DataModel model = null;

		if(category!=null)
		{
			//String filename="/Users/maggie/Downloads/eclipse/dataset/"+category+".csv";
    		String filename="dataset/"+category+".csv"; 

			try 
			{

			//BufferedWriter b = new BufferedWriter(new FileWriter("DataSet/hello.txt"));		
			model = new FileDataModel(new File(filename));		
			} catch (IOException e) 
			{
				System.out.println("file not found - "+filename.toString());
				return null;		
			}		
	
		}	
		else 
		{
	
		try 
		{
	
		//model = new FileDataModel(new File("/Users/maggie/Downloads/eclipse/dataset/CleanData.csv"));
			model = new FileDataModel(new File("dataset/CleanData.csv"));

		} catch (IOException e) 
		{
			System.out.println("clean Dataset file not found");
			return null;	
		}
		}
	
		Set<Long> recomm =new HashSet<Long>();
		ItemSimilarity similarity = new LogLikelihoodSimilarity(model);
	
		GenericItemBasedRecommender recommender =new GenericItemBasedRecommender(model, similarity) ;
		for(long item : businessId)
		{
			List<RecommendedItem> recommendations = recommender.mostSimilarItems(item,50);
			for(RecommendedItem recommendation : recommendations)
			{
				System.out.println(item+ ","+recommendation.getItemID()+","+recommendation.getValue());
				recomm.add(recommendation.getItemID());
	
			}
	
		}
	
		ArrayList<Long> result = new ArrayList<Long>();
		for(long id: recomm)
		{
			result.add(id);
	
		//System.out.println(id);
		}
	
		return result;
		}

}
