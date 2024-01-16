package com.csslect.app.command;

import org.springframework.ui.Model;

import com.csslect.app.dao.ANDao;

public class AUpdateMultiNoCommand implements ACommand{	
	
	@Override
	public void execute(Model model) {
		int id = Integer.parseInt((String)model.asMap().get("id"));
		String name = (String)model.asMap().get("name");
		String date = (String)model.asMap().get("date");		
		
		ANDao adao = new ANDao();
		
		adao.anUpdateMultiNo(id, name, date);
		
		
	}	 

}
