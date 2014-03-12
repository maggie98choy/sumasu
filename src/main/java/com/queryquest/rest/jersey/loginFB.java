package com.queryquest.rest.jersey;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MultivaluedMap;

import net.sf.json.JSONObject;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.queryquest.rest.jersey.Utility.MongoQueries;
import com.queryquest.rest.jersey.Utility.SQLQueries;
import com.queryquest.rest.jersey.domain.Login;
import com.queryquest.rest.jersey.fbclient.FB4j;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.core.util.MultivaluedMapImpl;


/**
 * Servlet implementation class loginFB
 * 
 */
@WebServlet("/loginFB")
public class loginFB extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginFB() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//PrintWriter out= response.getWriter();
		String accessToken = request.getParameter("accessT");
		
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,Boolean.TRUE);
		Client client= Client.create(clientConfig);
		WebResource webResource = client.resource("https://graph.facebook.com/me/");
		
	     MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
	     queryParams.add("access_token", accessToken);
	    
	     String s = webResource.queryParams(queryParams).get(String.class);
	     System.out.println("url = "+s);
	     
	     JSONParser parser = new JSONParser();
	     Object obj = null;
		try {
			obj = parser.parse(s);
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) obj;
		Login login = new Login();
	    
	     if ((String) jsonObject.get("name")!=null)
	     {
	    	 login.setName((String) jsonObject.get("name"));
	     }
	     if ((String) jsonObject.get("email")!=null)
	     {
	    
	    	 login.setEmail((String) jsonObject.get("email"));
	     }
	    
	     
	     SQLQueries sqlQuery = new SQLQueries();
	     if(sqlQuery.isUserAdded(login)){
	    	 HttpSession session = request.getSession(true);
			 session.setAttribute("email", (String) jsonObject.get("email"));
	    	 MongoQueries mongoQuery = new MongoQueries();
		     mongoQuery.mongoConnect();
		     FB4j fb4j = new FB4j();
		     String str= fb4j.getDataFromUser(accessToken,login);
		     JSONObject jsonObj = JSONObject.fromObject(str);
		     mongoQuery.mongoUpdate(jsonObj,(String)jsonObject.get("email") );
			 request.getRequestDispatcher("search.jsp").forward(request, response);
			 return;

	     }
	     else
	     {
	    	 request.setAttribute("login_status", "Not Registered to login!");
	    	 request.getRequestDispatcher("loginFB.jsp").forward(request, response);
	    	 return;
	     }
		
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	//	HttpSession session = request.getSession();
		//String accessToken = (String) session.getAttribute("accessToken");
		
		/*String accessToken = request.getParameter("accesscode");
		String randomText = request.getParameter("search");
		
		PrintWriter out= response.getWriter();
		out.println(" Access code and random text = "+accessToken+"  "+randomText);
		
		//FacebookApi fb = new FacebookApi(accessToken);
		(new Thread(new FacebookApi(accessToken))).start();*/
		
	}

}
