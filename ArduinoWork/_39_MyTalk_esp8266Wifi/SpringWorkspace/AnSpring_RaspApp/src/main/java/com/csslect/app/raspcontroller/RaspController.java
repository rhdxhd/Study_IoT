package com.csslect.app.raspcontroller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.csslect.app.command.ADeleteMultiCommand;
import com.csslect.app.command.AInsertMultiCommand;
import com.csslect.app.raspcommand.RaspCommand;
import com.csslect.app.raspcommand.RaspGetDataCommand;
import com.csslect.app.raspcommand.RaspSetDataCommand;

@Controller
public class RaspController {
	
	RaspCommand command;
	
	@RequestMapping(value="/raspGetData", method = {RequestMethod.GET, RequestMethod.POST}  )
	public String arduGetLed(HttpServletRequest req, Model model){
		System.out.println("raspGetData()");
		
		command = new RaspGetDataCommand();
		command.execute(model);
		
		return "raspGetData";
	}
	
	@RequestMapping(value = "/raspSetData", method = {RequestMethod.GET, RequestMethod.POST})
	public String arduSetLed(HttpServletRequest req, Model model) {
		
	     System.out.println("store_id : " + req.getParameter("store_id"));     
	     System.out.println("store_name : " + req.getParameter("store_name")); 
	     System.out.println("table_num : " + req.getParameter("table_num")); 
	     System.out.println("table_value : " + req.getParameter("table_value")); 
	     
	     String store_id = req.getParameter("store_id");
	     String store_name = req.getParameter("store_name");
	     String table_num = req.getParameter("table_num");
	     String table_value = req.getParameter("table_value");
	     
	     model.addAttribute("store_id", store_id);
	     model.addAttribute("store_name", store_name);
	     model.addAttribute("table_num", table_num);
	     model.addAttribute("table_value", table_value);
	     
	     command = new RaspSetDataCommand();
	     command.execute(model); 
		
	     return "raspSetData";
	}
	
	@RequestMapping(value="/raspSendPhoto", method = {RequestMethod.GET, RequestMethod.POST}  )
	public String anInsertMulti(HttpServletRequest req, Model model){
		System.out.println("raspSendPhoto()");	
		
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		MultipartRequest multi = (MultipartRequest)req;
		MultipartFile file = multi.getFile("image");
		MultipartFile file2 = multi.getFile("video");
			
		if(file != null) {
			String fileName = file.getOriginalFilename();
			System.out.println(fileName);
			
			// 디렉토리 존재하지 않으면 생성
			makeDir(req);	
				
			if(file.getSize() > 0){			
				String realImgPath = req.getSession().getServletContext()						
						.getRealPath("/resources/");
				
				//System.out.println( fileName + " : " + realImgPath);
				System.out.println( "fileSize : " + file.getSize());					
												
			 	try {
			 		// 이미지파일 저장
					file.transferTo(new File(realImgPath, "MyPicture.jpg"));										
				} catch (Exception e) {
					e.printStackTrace();
				} 
									
			}else{
				// 이미지파일 실패시
				fileName = "FileFail.jpg";
				String realImgPath = req.getSession().getServletContext()
						.getRealPath("/resources/" + fileName);
				System.out.println(fileName + " : " + realImgPath);
						
			}			
			
		}
		
		if(file2 != null) {
			// 비디오 파일 크기 1024X768로 바꾸어 저장하자
			//ffmpeg -i input.avi -vf scale=320:240 output.avi			
			
			String fileName = file2.getOriginalFilename();
			System.out.println(fileName);	
			
			// 디렉토리 존재하지 않으면 생성
			makeDir(req);	
				
			if(file2.getSize() > 0){			
				String realVideoPath = req.getSession().getServletContext()						
						.getRealPath("/resources/");
				
				//System.out.println( fileName + " : " + realImgPath);
				System.out.println( "fileSize : " + file2.getSize());					
												
			 	try {
			 		// 이미지파일 저장
					file2.transferTo(new File(realVideoPath, "MyVideo_tmp.mp4"));										
				} catch (Exception e) {
					e.printStackTrace();
				} 
			 	
			 	convert(realVideoPath + "\\MyVideo_tmp.mp4", realVideoPath + "MyVideo.mp4");
			 	
			 	File delfile = new File(realVideoPath, "MyVideo_tmp.mp4");
			 	if(delfile.exists()) {
		            System.out.println("Sub1Del:pDelImagePath " + delfile.exists());
		            boolean deleteFile = false;
		            while(deleteFile != true){
		            	deleteFile = delfile.delete();
		            }     
		        }	
									
			}else{
				// 이미지파일 실패시
				fileName = "FileFail.mp4";
				String realImgPath = req.getSession().getServletContext()
						.getRealPath("/resources/" + fileName);
				System.out.println(fileName + " : " + realImgPath);
						
			}			
			
		}
		
		return "raspSendPhoto";
	}
		
	public void makeDir(HttpServletRequest req){
		File f = new File(req.getSession().getServletContext()
				.getRealPath("/resources"));
		if(!f.isDirectory()){
			f.mkdir();
		}	
	}
	
	@RequestMapping(value="/raspDelFile", method = {RequestMethod.GET, RequestMethod.POST})
	public void anDeleteMulti(HttpServletRequest req, Model model){
		System.out.println("raspDelFile()");	
		
		String id = req.getParameter("id");
		System.out.println((String)req.getParameter("id"));	
		
		// 파일 경로 찾기
		String delDbImgPath = "";
		if(id.equals("PIC")) {
			delDbImgPath = req.getSession().getServletContext().getRealPath("/resources/MyPicture.jpg");	
		}else if(id.equals("VIDEO")) {
			delDbImgPath = req.getSession().getServletContext().getRealPath("/resources/MyVideo.mp4");	
		}
		
		// 이미지 파일지우기
		File delfile = new File(delDbImgPath);
		System.out.println(delfile.getAbsolutePath());
		
        if(delfile.exists()) {
            System.out.println("Sub1Del:pDelImagePath " + delfile.exists());
            boolean deleteFile = false;
            while(deleteFile != true){
            	deleteFile = delfile.delete();
            }     
        }	        
				
	}
	
	// 비디오 파일 1024X768로 변환시키기
	public void convert(String fOriginal, String fResult) {

		String ffmpegPath = "D:\\CHOSS21\\ffmpeg-20200515\\bin\\ffmpeg.exe";  //"ffmpeg 파일이 있는 경로";    //예) /work/ffmpeg
		//String fOriginal = "/work/upload.mp4";  //실시간으로 업로드되는 파일
		//String fResult = "/work/upload.flv";      // 인코딩하고 저장 할 파일위치

		String[] cmdLine = new String[]{ffmpegPath,
                                           "-i",                           // 변환시킬 파일위치
                                           fOriginal,      
                                           "-ar",
                                           "44100",     
                                           "-ab",
                                           "32",                      
                                           "-s",
                                           "1024x768",     //화면 사이즈
                                           "-b",
                                           "768k",          //비트레이트
                                           "-r",
                                           "24",           //영상 프레임
                                           "-y",
                                           "-f",
                                           "mp4",            // flv파일 형태로 출력
                                           fResult};  // 저장하는 위치입니다.
		 
		// 프로세스 속성을 관리하는 ProcessBuilder 생성.
		ProcessBuilder pb = new ProcessBuilder(cmdLine);
		pb.redirectErrorStream(true);
		Process p = null;
		try { 
		     // 프로세스 작업을 실행
		     p = pb.start();
		} catch (Exception e) {         
		     e.printStackTrace();
		     p.destroy();
		     //return null;
		}

    	exhaustInputStream(p.getInputStream());   // 자식 프로세스에서 발생되는 inputstrem를 소비시켜야합니다.

		try {
            // p의 자식 프로세스의 작업이 완료될 동안 p를 대기시킴
	        p.waitFor();

	    } catch (InterruptedException e) {
            p.destroy();
    	}


		// 정상 종료가 되지 않았을 경우
		if (p.exitValue() != 0) {
	        System.out.println("변환 중 에러 발생");
	        //return null;
	    }

		// 변환을 하는 중 에러가 발생하여 파일의 크기가 0일 경우
		if (fResult.length() == 0) {
		    System.out.println("변환된 파일의 사이즈가 0임");
		    //return null;
		}
	
		p.destroy();
		
		//return new File(fResult);

	}

	private void exhaustInputStream(final InputStream is) {
	    // InputStream.read() 에서 블럭상태에 빠지기 때문에 따로 쓰레드를 구현하여 스트림을 소비한다
	    try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String cmd = null;

            while((cmd = br.readLine()) != null) { // 읽어들일 라인이 없을때까지 계속 반복
                //System.out.println(cmd);
            }

            br.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

   }

			/*
			 * public static void main(String[] args) { File f = new
			 * File("실시간으로 올라오는 파일위치"); if(f.exists()){ convert(); }
			 * 
			 * }
			 */

}
