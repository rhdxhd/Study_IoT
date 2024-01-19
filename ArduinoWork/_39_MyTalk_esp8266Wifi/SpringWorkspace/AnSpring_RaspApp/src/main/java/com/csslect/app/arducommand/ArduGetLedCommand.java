package com.csslect.app.arducommand;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.csslect.app.ardudao.ArduDao;
import com.csslect.app.ardudto.ArduDto;
import com.csslect.app.dao.ANDao;
import com.csslect.app.dto.ANDto;


public class ArduGetLedCommand implements ArduCommand{

	@Override
	public void execute(Model model) {			
		ArduDao adao = new ArduDao();
		ArrayList<ArduDto> adtos = adao.arduGetLed();
		
		model.addAttribute("arduGetLed", adtos); 
		
	}
	
}
