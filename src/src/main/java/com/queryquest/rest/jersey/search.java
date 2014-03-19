package com.queryquest.rest.jersey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.queryquest.rest.jersey.Utility.MongoQueries;
import com.queryquest.rest.jersey.domain.SearchResult;
import com.queryquest.rest.jersey.fbclient.Yelp;

/**
 * Servlet implementation class search
 */
@WebServlet("/search")
public class search extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String consumerKey = "ji0in1zmhgsL9bxJ4VMjIQ";
	private String consumerSecret = "xxKohKewFrjhLoOpm-0xsyUMNYA";
	private String token = "ImKKW1IiwL-7VkAWNONADx51qutF_PyE";
	private String tokenSecret = "KS_PJKojQZuYjhAFDR7l7wFmLbo";   
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
		
		String activity = request.getParameter("activity");
		MongoQueries mongo = new MongoQueries();
		mongo.mongoConnect();
		
		HttpSession session = request.getSession(true);
		String email=(String) session.getAttribute("email");
		String location = mongo.mongoGetLocation(email);
		Yelp yelp = new Yelp(consumerKey, consumerSecret, token, tokenSecret);
		String out = yelp.search(activity, location);
		System.out.print("hello12"+email);
		System.out.print("hello12"+location);

		ArrayList<SearchResult> searchList = new ArrayList<SearchResult>();
		JSONObject jsonObj = JSONObject.fromObject(out);
		JSONArray msg = (JSONArray) jsonObj.get("businesses");
		if(msg != null){
			
			Iterator<JSONObject> iterator = msg.iterator();
			while (iterator.hasNext()) {
				
				SearchResult search = new SearchResult();
				JSONObject business = iterator.next();
				System.out.println(JSONObject.fromObject(business));
				String name = (String) business.get("name");
				String addr1 = JSONObject.fromObject(business.get("location")).getString("display_address");
				String addr2 = addr1.replace("[", "");
				String addr = addr2.replace("]", "");
				String url = (String)business.get("url");
				String phone =(String)business.get("phone");

				search.setAddress(addr);
				search.setName(name);
				search.setURL(url);
				search.setPhoneNo(phone);
				searchList.add(search);
				System.out.println(search.toString());
				

			}
		}

		request.setAttribute("search_results", searchList);
		request.getRequestDispatcher("searchresults.jsp").forward(request, response);
	}
	

}
