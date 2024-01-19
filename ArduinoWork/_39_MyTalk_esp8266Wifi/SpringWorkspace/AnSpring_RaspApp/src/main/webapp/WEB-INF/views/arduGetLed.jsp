<%@page import="com.csslect.app.ardudto.ArduDto"%>

<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.gson.JsonObject"%>

<%@page import="org.springframework.ui.Model"%>
<%@page import="java.sql.*, java.sql.Date, javax.sql.*, javax.naming.*, 
					java.util.*, java.io.PrintWriter" %>

<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>



<%	

	Gson gson = new Gson();
	String json = gson.toJson((ArrayList<ArduDto>)request.getAttribute("arduGetLed"));
	
	out.println(json);	
	
	// 아두이노로 값보내기
  	
	 
    BufferedReader in = null; 
    try { 
   	 URL obj = new URL("http://192.168.0.102/bb?" + json); // 호출할 url 
	     HttpURLConnection con = (HttpURLConnection)obj.openConnection(); 
	     con.setRequestMethod("GET"); 
	     in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8")); 
	     
	     String line; 
	     while((line = in.readLine()) != null) { // response를 차례대로 출력 
	    	 System.out.println(line); 
	     } 
    } catch(Exception e) { 
   	 e.printStackTrace(); 
	 } finally { 
		 if(in != null) {
			 try { 
				 in.close(); 
			 } catch(Exception e) {	 
				 e.printStackTrace(); 
			 } 
		 }
    }     

		
%>

