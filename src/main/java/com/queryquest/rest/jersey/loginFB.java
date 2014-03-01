package com.queryquest.rest.jersey;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.queryquest.rest.jersey.fbclient.FacebookApi;


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
		
		(new Thread(new FacebookApi(accessToken))).start();
		request.getRequestDispatcher("search.jsp").forward(request, response); 
		
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
