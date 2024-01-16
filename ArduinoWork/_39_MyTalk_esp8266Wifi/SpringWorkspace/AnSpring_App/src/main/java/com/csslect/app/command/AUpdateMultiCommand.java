package com.csslect.app.command;

import org.springframework.ui.Model;

import com.csslect.app.dao.ANDao;

public class AUpdateMultiCommand implements ACommand{	
	
	@Override
	public void execute(Model model) {
		int id = Integer.parseInt((String)model.asMap().get("id"));
		String name = (String)model.asMap().get("name");
		String date = (String)model.asMap().get("date");
		String dbImgPath= (String)model.asMap().get("dbImgPath");
		
		ANDao adao = new ANDao();
		
		adao.anUpdateMulti(id, name, date, dbImgPath);
		
		
	}	 

}
