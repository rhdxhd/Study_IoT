package com.csslect.app.arducommand;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.csslect.app.ardudao.ArduDao;
import com.csslect.app.ardudto.ArduDto;


public class ArduSetLedCommand implements ArduCommand{

	@Override
	public void execute(Model model) {
		
		String id = (String)model.asMap().get("id");
		String value = (String)model.asMap().get("value");
		
		ArduDao adao = new ArduDao();
		
		ArrayList<ArduDto> adtos = adao.arduSetLed(id, value);
		
		//model.addAttribute("arduSetLed", adtos); 
		
	}
	
}
