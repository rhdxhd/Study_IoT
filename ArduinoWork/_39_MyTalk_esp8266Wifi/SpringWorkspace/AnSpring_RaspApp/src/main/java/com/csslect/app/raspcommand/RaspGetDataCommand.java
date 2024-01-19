package com.csslect.app.raspcommand;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.csslect.app.ardudao.ArduDao;
import com.csslect.app.ardudto.ArduDto;
import com.csslect.app.dao.ANDao;
import com.csslect.app.dto.ANDto;
import com.csslect.app.raspdao.RaspDao;
import com.csslect.app.raspdto.RaspDto;


public class RaspGetDataCommand implements RaspCommand{

	@Override
	public void execute(Model model) {			
		RaspDao adao = new RaspDao();
		ArrayList<RaspDto> adtos = adao.raspGetData();
		
		model.addAttribute("raspGetData", adtos); 
		
	}
	
}
