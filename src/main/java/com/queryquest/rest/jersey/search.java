package com.queryquest.rest.jersey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.queryquest.rest.jersey.Utility.DistanceCalculation;
import com.queryquest.rest.jersey.Utility.MongoQueries;
import com.queryquest.rest.jersey.Utility.SearchParser;
import com.queryquest.rest.jersey.domain.SearchAnalysis;
import com.queryquest.rest.jersey.domain.SearchResult;
import com.queryquest.rest.jersey.fbclient.Yelp;

/**
 * Servlet implementation class search
 */
@WebServlet("/search")
public class search extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String consumerKey = "ji0in1zmhgsL9bxJ4VMjIQ";
	private static final String consumerSecret = "xxKohKewFrjhLoOpm-0xsyUMNYA";
	private static final String token = "ImKKW1IiwL-7VkAWNONADx51qutF_PyE";
	private static final String tokenSecret = "KS_PJKojQZuYjhAFDR7l7wFmLbo";
	private static final int totalNumber=15;

	private class FinalResults{
		private ArrayList<SearchResult> recommendedResults;
		private ArrayList<SearchResult> ratedResults;
		public ArrayList<SearchResult> getRecommendedResults() {
			return recommendedResults;
		}
		public void setRecommendedResults(ArrayList<SearchResult> recommendedResults) {
			this.recommendedResults = recommendedResults;
		}
		public ArrayList<SearchResult> getRatedResults() {
			return ratedResults;
		}
		public void setRatedResults(ArrayList<SearchResult> ratedResults) {
			this.ratedResults = ratedResults;
		}
	}
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public search() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Hello WOrld");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		String searchTerm = request.getParameter("searchTerm");
		SearchParser searchParser = new SearchParser();
		System.out.println("hello "+searchTerm);
		String startdate = request.getParameter("datetimepicker6");
		String enddate = request.getParameter("datetimepicker7");

		System.out.println("start date:"+startdate);
		System.out.println("end date:"+enddate);

		String currentLocation;
		String travelDestination;

		SearchAnalysis searchAnalysis = searchParser.stringParser(searchTerm);

		HttpSession session = request.getSession(true);
		String email=(String) session.getAttribute("email");
		MongoQueries mongo = new MongoQueries();


		String location="";
		//Activities
		ArrayList<String> actList=null;
		if(searchAnalysis.getActivity().isEmpty()){
			mongo.mongoConnect(1);
			actList=mongo.mongoGetActivities(email);
		}else
			actList=searchAnalysis.getActivity();

		mongo.mongoConnect();
		//location
		if(searchAnalysis.getLocation() == null)
		{

			location = mongo.mongoGetLocation(email);
		}else
			location=searchAnalysis.getLocation();

		currentLocation = mongo.mongoGetLocation(email);
		travelDestination = location;
           
		
		Yelp yelp = new Yelp(consumerKey, consumerSecret, token, tokenSecret);
		ArrayList<SearchResult> recomSearchList = new ArrayList<SearchResult>();
		ArrayList<SearchResult> ratedSearchList = new ArrayList<SearchResult>();

		for(int i=0;i<actList.size();i++){
		//	mongo.mongoConnect(3);
		//	mongo.mongoGetBusiness(location, actList.get(i));
			String out=yelp.search(actList.get(i), location);
			ArrayList<SearchResult> tmpSearchList = new ArrayList<SearchResult>();
			//String out = yelp.search(activity, "hiking");
			//System.out.println("hello ="+out);
			JSONObject jsonObj = JSONObject.fromObject(out);
			JSONArray msg = (JSONArray) jsonObj.get("businesses");
			if(msg != null){
				FinalResults finalResults= new FinalResults();
				finalResults = parseYelpResult(msg,(totalNumber/actList.size()),actList.get(i),email);
				recomSearchList.addAll(finalResults.getRecommendedResults());
				ratedSearchList.addAll(finalResults.getRatedResults());

			}
		}

		//Get distance in miles between 2 cities		
		DistanceCalculation dc = new DistanceCalculation();
		int distance = 0;
		try 
		{
			distance =dc.calculateDistance(currentLocation, travelDestination);
		} catch (ParserConfigurationException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//remove space in city string
		char[] charArrayCurrentLocation = currentLocation.toCharArray();
		String new_currentLocation = "";
		for (int i=0; i<currentLocation.length(); i++)
		{
			if (charArrayCurrentLocation[i] != ' ')
			{
				new_currentLocation = new_currentLocation + charArrayCurrentLocation[i];
			}
		}

		request.setAttribute("distance", distance);
		request.setAttribute("recom_search_results", recomSearchList);
		request.setAttribute("rated_search_results", ratedSearchList);
		request.setAttribute("startdate", startdate);
		request.setAttribute("enddate", enddate);
		request.setAttribute("currentLocation", new_currentLocation);
		request.setAttribute("travelDestination", travelDestination);	
		request.getRequestDispatcher("searchresults.jsp").forward(request, response);

	}

	private FinalResults parseYelpResult(JSONArray msg,int count, String activity, String email){
		Iterator<JSONObject> iterator = msg.iterator();

		ArrayList<SearchResult> finalSearchList = new ArrayList<SearchResult>();
		ArrayList<SearchResult> recomSearchList = new ArrayList<SearchResult>();
		ArrayList<SearchResult> ratedSearchList = new ArrayList<SearchResult>();

		MongoQueries mongo = new MongoQueries();
		mongo.mongoConnect(2);

		while (iterator.hasNext()&&count>0) {
			SearchResult search = new SearchResult();
			JSONObject business = iterator.next();
		//	System.out.println(JSONObject.fromObject(business));
			String name = (String) business.get("name");
			//System.out.println("name "+name);
			
			List<String> catList = new ArrayList<String>();
			if((business.get("categories"))instanceof JSONArray){
				JSONArray categories = business.getJSONArray("categories");
			for(int i=0;i<categories.size();i++){
				catList.add(categories.getString(i));
				
			}
			System.out.println("categories "+catList.toString());
			}
			int match=0;
			for(int i=0;i<catList.size();i++){
				//System.out.println("ALL "+catList.get(i)+"  "+activity);
				String[] cat = catList.get(i).split(",");
				for(int j=0;j<cat.length;j++){
					//System.out.println("ooo "+cat[j].toLowerCase()+" "+activity.toLowerCase());
					if(((cat[j].toLowerCase().replaceAll("\\W", "")).contains(activity.toLowerCase()))){
						match=1; 
					//	System.out.println("CAT "+catList.get(i));
						break;

					}
					else if(activity.toLowerCase().contains(cat[j].toLowerCase().replaceAll("\\W", ""))){
						//System.out.println("acti " +activity.toLowerCase());
						//System.out.println("cat "+cat[j].toLowerCase().replaceAll("\\W", ""));
						match=1; 
			//			System.out.println("CATi "+catList.get(i));
						break;
					}
				}
			}

			if(match==0)
				continue;

			int rating= mongo.mongoGetRating(email,name);
			float avgRating;
			if(rating == 0){
				avgRating = mongo.mongoGetRecommendedRating(name);
				search.setRecommended(true);
				search.setRecommendedRating(avgRating);
			}else
				search.setRecommended(false);

			//if(rating == 1 || rating == 2)
			//continue;
			String addr1 = JSONObject.fromObject(business.get("location")).getString("display_address");
			String addr2 = addr1.replace("[", "");
			String addr = addr2.replace("]", "");
			String url = (String)business.get("url");
			String phone =(String)business.get("phone");

			search.setAddress(addr);
			search.setName(name);
			search.setURL(url);
			search.setPhoneNo(phone);
			search.setNoOfStars(rating);
			search.setActivity(activity);
			search.setCategory(catList.toString());
	    	System.out.println(search.toString());

			if(search.isRecommended()){
				recomSearchList.add(search);
				count--;
			}
			else
				ratedSearchList.add(search);
		}

		FinalResults finalResults = new FinalResults();
		//SortSearchResults sort = new SortSearchResults();
		finalResults.setRecommendedResults(sortRating(recomSearchList,true));
		finalResults.setRatedResults(sortRating(ratedSearchList,false));
		return finalResults;				
	}

	private ArrayList<SearchResult> sortRating(ArrayList<SearchResult> searchList, boolean isRecom){
		int nElems = searchList.size();
		int inner, outer;
		SearchResult tmp;

		int h=1;
		while(h< nElems/3)
			h=h*3+1;

		while(h>0){
			for(outer=h; outer<nElems;outer++){
				tmp = searchList.get(outer);
				inner=outer;

				while(inner >h-1 && (isRecom? searchList.get(inner-h).getRecommendedRating():searchList.get(inner-h).getNoOfStars())< (isRecom?tmp.getRecommendedRating():tmp.getNoOfStars())){
					searchList.remove(inner);
					searchList.add(inner, searchList.get(inner-h));
					inner-=h;
				}
				searchList.remove(inner);
				searchList.add(inner,tmp);
			}
			h=(h-1)/3;
		}
		if(isRecom==false){
			for(int i=0;i<searchList.size();i++){
				//   System.out.println(searchList.get(i).toString());

			}
		}
		return searchList;
	}

}
