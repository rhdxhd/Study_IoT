package com.csslect.app.arducontroller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.csslect.app.arducommand.ArduCommand;
import com.csslect.app.arducommand.ArduGetLedCommand;
import com.csslect.app.arducommand.ArduSetLedCommand;

@Controller
public class ArduController {
	
	ArduCommand command;
	
	@RequestMapping(value="/arduGetLed", method = {RequestMethod.GET, RequestMethod.POST}  )
	public String arduGetLed(HttpServletRequest req, Model model){
		System.out.println("arduGetLed()");
		
		command = new ArduGetLedCommand();
		command.execute(model);
		
		return "arduGetLed";
	}
	
	@RequestMapping(value = "/arduSetLed", method = {RequestMethod.GET, RequestMethod.POST})
	public String arduSetLed(HttpServletRequest req, Model model) {
	     System.out.println("id : " + req.getParameter("id"));     
	     System.out.println("value : " + req.getParameter("value")); 
	     
	     String id = req.getParameter("id");
	     String value = req.getParameter("value");
	     
	     model.addAttribute("id", id);
	     model.addAttribute("value", value);
	     
	     command = new ArduSetLedCommand();
	     command.execute(model); 
		
	     return "arduSetLed";
	}
	
	/*@RequestMapping(value="/anInsertMulti", method = {RequestMethod.GET, RequestMethod.POST}  )
	public String anInsertMulti(HttpServletRequest req, Model model){
		System.out.println("anInsertMulti()");	
		
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		
		String id = (String) req.getParameter("id");
		String name = (String) req.getParameter("name");
		String date = (String) req.getParameter("date");
		String dbImgPath = (String) req.getParameter("dbImgPath");
		String uploadtype = (String) req.getParameter("uploadType");
		
		System.out.println(id);
		System.out.println(name);
		System.out.println(date);
		System.out.println(dbImgPath);
		System.out.println(uploadtype);
		
		model.addAttribute("id", id);
		model.addAttribute("name", name);
		model.addAttribute("date", date);	
		model.addAttribute("dbImgPath", dbImgPath);	
		model.addAttribute("uploadtype", uploadtype);
		
		MultipartRequest multi = (MultipartRequest)req;
		MultipartFile file = null;
		
		if(uploadtype.equals("image")) {
			file = multi.getFile("image");
		}else if(uploadtype.equals("video")) {
			file = multi.getFile("video");	
		}
			
		if(file != null) {
			String fileName = file.getOriginalFilename();
			System.out.println(fileName);
			
			// 디렉토리 존재하지 않으면 생성
			makeDir(req);	
				
			if(file.getSize() > 0){			
				String realImgPath = req.getSession().getServletContext()
						.getRealPath("/resources/");
				
				System.out.println( fileName + " : " + realImgPath);
				System.out.println( "fileSize : " + file.getSize());					
												
			 	try {
			 		// 이미지파일, 비디오 파일 저장
					file.transferTo(new File(realImgPath, fileName));
					
					String videoImageName = "";
					String videoImagePath = "";
					
					// 비디오 파일이면 10프레임 이미지 가져와 비디오 이미지로 저장
					if(uploadtype.equals("video")) {
						videoImageName = makeVideoToImage(req, realImgPath, fileName);
						String fileNamePath = dbImgPath.split("/")[dbImgPath.split("/").length-1];
						videoImagePath = dbImgPath.replace(fileNamePath, videoImageName);
						
						System.out.println("videoimagepath : " + videoImagePath);
						
					}else if(uploadtype.equals("image")) {
						videoImagePath = "novideo";
					}
					// model에 비디오 이미지 경로에 추가
					model.addAttribute("videoImagePath", videoImagePath);
					
				} catch (Exception e) {
					e.printStackTrace();
				} 
									
			}else{
				fileName = "FileFail.jpg";
				String realImgPath = req.getSession().getServletContext()
						.getRealPath("/resources/" + fileName);
				System.out.println(fileName + " : " + realImgPath);
						
			}			
			
		}
				
		command = new AInsertMultiCommand();
		command.execute(model);
		
		return "anInsertMulti";
	}
		
	public void makeDir(HttpServletRequest req){
		File f = new File(req.getSession().getServletContext()
				.getRealPath("/resources"));
		if(!f.isDirectory()){
		f.mkdir();
		}	
	}
	
	//동영상에서 이미지파일 만들어 저장하기
	public String makeVideoToImage(HttpServletRequest req, String realImgPath, String fileName) {
		long time = System.currentTimeMillis();
        int frameNumber = 10;
        String videoImageName = "";
        String videoImagePath = "";
        
		try {
			File f3 = new File(req.getSession().getServletContext()
					.getRealPath("/resources"));
			if(!f3.isDirectory()){
				f3.mkdir();
			}
									
			Picture picture;
			try {
				picture = FrameGrab.getFrameFromFile(new File(realImgPath, fileName), frameNumber);
				videoImageName = fileName.replace(".", "_") + ".jpg";
				videoImagePath = f3 + "/" + videoImageName;
				System.out.println("videoimagepath1 : " + videoImagePath);
				
				BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
		    	ImageIO.write(bufferedImage, "jpg", new File(videoImagePath));
			}catch (IOException e) {				
				e.printStackTrace();
			}            
            
		} catch (JCodecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		            
        
        System.out.println("Time Used:" + (System.currentTimeMillis() - time)+" Milliseconds");
	
        // 비디오 이미지 파일명만 리턴
        return videoImageName;
	}
	
	private  BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
	
	@RequestMapping(value="/anUpdateMulti", method = {RequestMethod.GET, RequestMethod.POST})
	public void anUpdateMulti(HttpServletRequest req, Model model){
		System.out.println("anUpdateMulti()");	
		
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		String id = (String) req.getParameter("id");
		String name = (String) req.getParameter("name");
		String date = (String) req.getParameter("date");
		String uploadtype = (String) req.getParameter("uploadType");
		String pUploadtype = (String) req.getParameter("pUploadType");
		String dbImgPath = (String) req.getParameter("dbImgPath");
		String pDbImgPath = (String) req.getParameter("pDbImgPath");
		
		System.out.println(id);
		System.out.println(name);
		System.out.println(date);
		System.out.println(uploadtype);
		System.out.println(pUploadtype);
		System.out.println("Sub1Update:dbImgPath " + dbImgPath);
		System.out.println("Sub1Update:pDbImgPath " + pDbImgPath);
		
		model.addAttribute("id", id);
		model.addAttribute("name", name);
		model.addAttribute("date", date);
		model.addAttribute("uploadtype", uploadtype);	
		model.addAttribute("dbImgPath", dbImgPath);
		
		// 이미지가 서로 같으면 삭제하지 않고 다르면 기존이미지 삭제
		if(!dbImgPath.equals(pDbImgPath)){			
			
			String pFileName = req.getParameter("pDbImgPath").split("/")[req.getParameter("pDbImgPath").split("/").length -1];
			String delDbImgPath = req.getSession().getServletContext().getRealPath("/resources/" + pFileName);
			
			File delfile = new File(delDbImgPath);
			System.out.println(delfile.getAbsolutePath());
			
	        if(delfile.exists()) {
	        	boolean deleteFile = false;
	            while(deleteFile != true){
	            	deleteFile = delfile.delete();
	            }
	            
	            if(pUploadtype.equals("video")){
	            	String videoImage = pFileName.replace(".","_") + ".jpg";
	            	System.out.println("Sub1Update:videoImgFilename : " + videoImage);
	            	
	            	String delvideoImage = req.getSession().getServletContext()
	            			.getRealPath("/resources/" + videoImage);
	            	
	            	File delfile2 = new File(delvideoImage);
	            	System.out.println("Sub1Update:delFile2 : " + delfile2.getAbsolutePath());
	            	
	            	 if(delfile2.exists()) {
	                     System.out.println("Sub1Update:delFile2 : " + delfile2.exists());
	                     boolean deleteFile2 = false;
	                     while(deleteFile2 != true){
	                    	 deleteFile2 = delfile2.delete();
	                     } 
	            	 }
	            }//if(pUploadtype.equals("video"))
	            
	        }//if(delfile.exists())
		
		}//if(!dbImgPath.equals(pDbImgPath))  
		
		MultipartRequest multi = (MultipartRequest)req;
		MultipartFile file = null;
		
		if(uploadtype.equals("image")) {
			file = multi.getFile("image");
		}else if(uploadtype.equals("video")) {
			file = multi.getFile("video");	
		}
			
		if(file != null) {
			String fileName = file.getOriginalFilename();
			System.out.println(fileName);
			
			// 디렉토리 존재하지 않으면 생성
			makeDir(req);	
				
			if(file.getSize() > 0){			
				String realImgPath = req.getSession().getServletContext()
						.getRealPath("/resources/");
				
				System.out.println( fileName + " : " + realImgPath);
				System.out.println( "fileSize : " + file.getSize());					
												
			 	try {
			 		// 이미지파일, 비디오 파일 저장
					file.transferTo(new File(realImgPath, fileName));
					
					String videoImageName = "";
					String videoImagePath = "";
					
					// 비디오 파일이면 10프레임 이미지 가져와 비디오 이미지로 저장
					if(uploadtype.equals("video")) {
						videoImageName = makeVideoToImage(req, realImgPath, fileName);
						String fileNamePath = dbImgPath.split("/")[dbImgPath.split("/").length-1];
						videoImagePath = dbImgPath.replace(fileNamePath, videoImageName);
						
						System.out.println("videoimagepath2 : " + videoImagePath);
						
					}else if(uploadtype.equals("image")) {
						videoImagePath = "novideo";
					}
					// model에 비디오 이미지 경로에 추가
					model.addAttribute("videoImagePath", videoImagePath);
					
				} catch (Exception e) {
					e.printStackTrace();
				} 
									
			}else{
				fileName = "FileFail.jpg";
				String realImgPath = req.getSession().getServletContext()
						.getRealPath("/resources/" + fileName);
				System.out.println(fileName + " : " + realImgPath);
						
			}			
			
		}
		
		command = new AUpdateMultiCommand();
		command.execute(model);		
		
	}
	
	@RequestMapping(value="/anUpdateMultiNo", method = {RequestMethod.GET, RequestMethod.POST})
	public void anUpdateMultiNo(HttpServletRequest req, Model model){
		System.out.println("anUpdateMultiNo()");	
		
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		String id = (String) req.getParameter("id");
		String name = (String) req.getParameter("name");
		String date = (String) req.getParameter("date");		
		
		model.addAttribute("id", id);
		model.addAttribute("name", name);
		model.addAttribute("date", date);
		
		command = new AUpdateMultiNoCommand();
		command.execute(model);		
		
	}
		
	
	@RequestMapping(value="/anDeleteMulti", method = {RequestMethod.GET, RequestMethod.POST})
	public void anDeleteMulti(HttpServletRequest req, Model model){
		System.out.println("anDeleteMulti()");		
		
		model.addAttribute("id", req.getParameter("id"));		
				
		System.out.println((String)req.getParameter("id"));
		System.out.println((String)req.getParameter("delDbImgPath"));
		System.out.println((String)req.getParameter("selUploadType"));
		
		
		String pFileName = req.getParameter("delDbImgPath").split("/")[req.getParameter("delDbImgPath").split("/").length -1];
		String delDbImgPath = req.getSession().getServletContext().getRealPath("/resources/" + pFileName);		
		
		// 이미지 파일지우기
		File delfile = new File(delDbImgPath);
		System.out.println(delfile.getAbsolutePath());
		
        if(delfile.exists()) {
            System.out.println("Sub1Del:pDelImagePath " + delfile.exists());
            boolean deleteFile = false;
            while(deleteFile != true){
            	deleteFile = delfile.delete();
            } 
            
            if(((String)req.getParameter("selUploadType")).equals("video")){
            	String videoImage = pFileName.replace(".","_") + ".jpg";
            	System.out.println("videoImagePath : " + videoImage);
            	
            	String delvideoImage = req.getSession().getServletContext()
            			.getRealPath("/resources/" + videoImage);
            	
            	File delfile2 = new File(delvideoImage);
            	System.out.println("delFile2 : " + delfile2.getAbsolutePath());
            	
            	 if(delfile2.exists()) {
                     System.out.println("Sub1Del:pDelImagePath " + delfile2.exists());
                     boolean deleteFile2 = false;
                     while(deleteFile2 != true){
                    	 deleteFile2 = delfile2.delete();
                     } 
            	 }
            }
        }	
		
		command = new ADeleteMultiCommand();
		command.execute(model);	
		
	}*/

}
