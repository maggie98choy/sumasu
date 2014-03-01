package com.queryquest.rest.jersey.Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.queryquest.rest.jersey.domain.Login;

public class SQLQueries {
	static Connection con = null;
	static ResultSet rs = null;
	static PreparedStatement pstmt = null;
	
	public static final String SEARCH_USER_QUERY= "select count(*) from tbl_login where user_name =?";
	public static final String ADD_USER_QUERY = "insert into tbl_login values (default, ?, ?, ?, ?)";
	
	
	public SQLQueries(){
		
		try{
		      try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      //con = DriverManager.getConnection("jdbc:mysql://54.193.76.21:3306/QueryQuest","cmpe295b","cmpe295b");
		      con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/QueryQuest","admin","sa");
		   }
		   catch (SQLException e) {
			       System.out.println("ERRROR");
					e.printStackTrace();
					
			}
		
	}
	
	public void releaseConnection(){
		try{
			if(pstmt !=null)
				pstmt.close();
			if(con != null)
				con.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public int insertUser(Login login){
		
		try{
			if(!isUserAdded(login)){
				pstmt = con.prepareStatement(ADD_USER_QUERY);
				 
			     pstmt.setString(1, login.getEmail().substring(0, login.getEmail().indexOf("@")));
			     pstmt.setString(2, "");
			     pstmt.setString(3, login.getName());
			     pstmt.setString(4, login.getEmail());
			     pstmt.executeUpdate();
			     return 0; // User added
			}
			else
				return 1; // User already present
			
			
		}catch (SQLException e){
			e.printStackTrace();
			return 3; //DB ERROR
		}
		
	}
	
	
	public boolean isUserAdded(Login login){
		try {
			 pstmt = con.prepareStatement(SEARCH_USER_QUERY);
			 pstmt.setString(1, login.getEmail().substring(0, login.getEmail().indexOf("@")));
			 rs =pstmt.executeQuery();
			 int count =0;
			 
			 while(rs.next()){
				 count = rs.getInt(1);
			 }
			 
			 if(count == 0 )
				 return false;
			 else 
				 return true;
			 
		}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
		}
	}	
	

}
