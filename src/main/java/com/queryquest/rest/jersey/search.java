package com.queryquest.rest.jersey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
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
		//System.out.println("hello "+searchTerm);

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

		//location
		if(searchAnalysis.getLocation() == null){
			mongo.mongoConnect();
			location = mongo.mongoGetLocation(email);
		}else
			location=searchAnalysis.getLocation();


		Yelp yelp = new Yelp(consumerKey, consumerSecret, token, tokenSecret);
		ArrayList<SearchResult> searchList = new ArrayList<SearchResult>();

		for(int i=0;i<actList.size();i++){
			String out=yelp.search(actList.get(i), location);
			ArrayList<SearchResult> tmpSearchList = new ArrayList<SearchResult>();
			//String out = yelp.search(activity, "hiking");
			//System.out.println("hello ="+out);
			JSONObject jsonObj = JSONObject.fromObject(out);
			JSONArray msg = (JSONArray) jsonObj.get("businesses");
			if(msg != null){
				tmpSearchList = parseYelpResult(msg,(totalNumber/actList.size()),email);
				searchList.addAll(tmpSearchList);

			}
		}

		request.setAttribute("search_results", searchList);
		request.setAttribute("activities", actList);
		request.setAttribute("division", totalNumber/actList.size());
		request.getRequestDispatcher("searchresults.jsp").forward(request, response);
	}

	private ArrayList<SearchResult> parseYelpResult(JSONArray msg,int count, String email){
		Iterator<JSONObject> iterator = msg.iterator();
		ArrayList<SearchResult> searchList = new ArrayList<SearchResult>();
		
		MongoQueries mongo = new MongoQueries();
		mongo.mongoConnect(2);
		
		while (iterator.hasNext()&&count>0) {
			SearchResult search = new SearchResult();
			JSONObject business = iterator.next();
			//System.out.println(JSONObject.fromObject(business));
			String name = (String) business.get("name");
			int rating= mongo.mongoGetRating(email,name);
			
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
			//System.out.println(search.toString());
 
			searchList.add(search);
			count--;
		}
		
		
		return sortRating(searchList);
	}
	
	
	private ArrayList<SearchResult> sortRating(ArrayList<SearchResult> searchList){
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
				
				while(inner >h-1 && searchList.get(inner-h).getNoOfStars()< tmp.getNoOfStars()){
					searchList.remove(inner);
					searchList.add(inner, searchList.get(inner-h));
					inner-=h;
				}
				searchList.remove(inner);
                searchList.add(inner,tmp);
			}
			h=(h-1)/3;
		}
	
		return searchList;
	}
}
