<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta charset="utf-8" />
<title>QueryQuest</title>
<!-- Stylesheets -->
	<link type="text/css" rel="stylesheet" href="bootstrap/css/bootstrap.css">
    <style>
        html,body { 
            background: url(images/firstpage.jpg) no-repeat center center fixed; 
            -webkit-background-size: cover;
            -moz-background-size: cover;
            -o-background-size: cover;
            background-size: cover;
        }        
    </style>
</head>


<body>
 <!-- Bootstrap  -->
	<script src="bootstrap/js/bootstrap.js"></script>
 <!-- JS  -->
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.js"></script>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>QueryQuest</title>
</head>
<body>

<div id="fb-root"></div>
<script>
 window.fbAsyncInit = function() 
 {
  FB.init({
    appId      : '359402037530890',
    status     : true, // check login status
    cookie     : true, // enable cookies to allow the server to access the session
    xfbml      : true  // parse XFBML
  });
 
  // Additional init code here
  FB.getLoginStatus(function(response) 
  {
   if (response.status === 'connected') {
   // connected
   

   } else if (response.status === 'not_authorized') {
    // not_authorized
    ///log function will change div visibility to "visible"
    //log();

   } else {
   // not_logged_in
    //log();
   }
  });//closes fb.getLoginStatus
 };//closes fbAsyncInit
 
 (function(d){
	   var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
	   if (d.getElementById(id)) {return;}
	   js = d.createElement('script'); js.id = id; js.async = true;
	   js.src = "//connect.facebook.net/en_US/all.js";
	   ref.parentNode.insertBefore(js, ref);
	  }(document));
 </script>
 

  <div class="container-fluid">
 		   
        <form class="well span5 offset5" action="loginFB" method=POST>
        	<p style="text-align:center; margin-top:0px; margin-bottom:0px; padding:0px;">
                    <img src="images/logo.png" alt="queryquest" width=100 height=100/><br>
                    QueryQuest
                </p>   
            <br><label>Username</label>
            <input type="email" class="span3" autofocus="autofocus" placeholder="example@xyz.com" name="user" required/> </br>
            <label>Password </label>
            <input type="password" class="span3" placeholder="*********" name="password" required/></br>
            <button  type="submit" value="submit" class="btn btn-primary">Submit </button>
            <button type="reset" class="btn">Clear</button></br></br>
            <input type="checkbox" id="remember" value="remember" />&nbsp;&nbsp; 
            <span> Remember me on this computer </span></br> </br> 
            
         <p style="text-align:left; margin-top:0px; margin-bottom:0px; padding:0px;"></p>
         <img border="0" src="images/login_facebook.png" onclick="fblogin()"/>  &nbsp;&nbsp;&nbsp;&nbsp;   
   		 <a href="registration.html"><img src="images/rainbow.gif"></a><br/><br/>
   		  <%String register_status  = (String)request.getAttribute("register_status");
		if (register_status != null)	
	    	out.println("<font size=\"4\" color=\"red\">Failed: User has already registered</font>");
	  %>
	  <%String login_status  = (String)request.getAttribute("login_status");
		if (login_status != null)	
	    	out.println("<font size=\"4\" color=\"red\">Failed: User has not registered!</font>");
	  %>
	  
	<p class="forgot">New user? <a href="registration.html">Register</a></p>
   		 </form>
    </div> 
 <script>
 function fblogin(){
	 
 FB.login(function(response) {
	 if (response.authResponse) {
		   FB.getLoginStatus(function(response){
		    	console.log('Access Token = '+response.authResponse.accessToken);
		    	 window.location= "loginFB?accessT="+response.authResponse.accessToken;
		    });

	   
	  }
	}, {scope: 'publish_stream' ,display : 'touch'});
 }
   </script> 
  

</body>
</html>