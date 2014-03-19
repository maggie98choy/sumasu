package com.queryquest.rest.jersey.domain;

public class Login 
{
	private String user_name;
	private String password;
	private String name;
	//private String first_name;
	//private String last_name;
	private String email;
	
	public Login()
	{}
	
	public void setUserName(String username)
	{
		this.user_name = username;
	}
	
	public String getUserName()
	{
		return user_name;		
	}
	
	public void setPassword(String pwd)
	{
		this.password = pwd;
	}
	
	public String getPassword()
	{
		return password;		
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;		
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String getEmail()
	{
		return this.email;		
	}
	
}
