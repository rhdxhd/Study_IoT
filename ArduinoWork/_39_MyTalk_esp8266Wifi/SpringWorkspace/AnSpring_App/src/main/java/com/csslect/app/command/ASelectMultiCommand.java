package com.csslect.app.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.csslect.app.dao.ANDao;
import com.csslect.app.dto.ANDto;


public class ASelectMultiCommand implements ACommand{

	@Override
	public void execute(Model model) {			
		ANDao adao = new ANDao();
		ArrayList<ANDto> adtos = adao.anSelectMulti();
		
		model.addAttribute("anSelectMulti", adtos); 
		
	}
	
}
