<%@page import="com.csslect.app.dto.ANDto"%>

<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.gson.JsonObject"%>

<%@page import="org.springframework.ui.Model"%>
<%@page import="java.sql.*, java.sql.Date, javax.sql.*, javax.naming.*, 
					java.util.*, java.io.PrintWriter" %>

<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>   


<%		
	Gson gson = new Gson();
	String json = gson.toJson((ArrayList<ANDto>)request.getAttribute("anSelectMulti"));
	
	out.println(json);	

 	/* try{
 		out.println("<lists>"); 
		
		for(ANDto dto  : (ArrayList<ANDto>)request.getAttribute("anSelectMulti")){
			out.println("<list>");
			out.println("<id>" + dto.getId() + "</id>");
			out.println("<name>" + dto.getName() + "</name>");
			out.println("<date>" + dto.getHire_date() + "</date>");
			out.println("<image>" + dto.getImage1() + "</image>");
			out.println("<uploadtype>" + dto.getUploadtype() + "</uploadtype>");
			
			if(dto.getUploadtype().equals("video")){
				String fileNamePath = (dto.getImage1().split("/")[dto.getImage1().split("/").length-1]);
				String replacePath = (dto.getImage1().split("/")[dto.getImage1().split("/").length-1]).replace(".", "_");
				System.out.println("replacePath :" + replacePath);				
				String videoImagePath = dto.getImage1().replace(fileNamePath, "videoImages/" + replacePath) + ".jpg";
				System.out.println("videoImagePath :" + videoImagePath);
				
				out.println("<videoimage>" + videoImagePath + "</videoimage>");				
			
			}else if(dto.getUploadtype().equals("image")){
				out.println("<videoimage>" + "novideo" + "</videoimage>");
			}
			
			out.println("</list>");
		}
 		out.println("</lists>");
 	}catch(Exception e) {
 		System.out.println("select list failed" + e.getMessage());
 	}	 */
		
%>

