package com.csslect.app.command;

import org.springframework.ui.Model;

import com.csslect.app.dao.ANDao;


public class ADeleteMultiCommand implements ACommand{	
	
	@Override
	public void execute(Model model) {
		int id = Integer.parseInt((String)model.asMap().get("id"));
		
		ANDao adao = new ANDao();
		adao.anDeleteMulti(id);		
			
	}	 

}
