package com.csslect.app.raspcommand;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.csslect.app.ardudao.ArduDao;
import com.csslect.app.ardudto.ArduDto;
import com.csslect.app.raspdao.RaspDao;
import com.csslect.app.raspdto.RaspDto;


public class RaspSetDataCommand implements RaspCommand{

	@Override
	public void execute(Model model) {
		
		String store_id = (String)model.asMap().get("store_id");
		String store_name = (String)model.asMap().get("store_name");
		String table_num = (String)model.asMap().get("table_num");
		String table_value = (String)model.asMap().get("table_value");
		
		RaspDao adao = new RaspDao();
		
		ArrayList<RaspDto> adtos = adao.RaspSetData(store_id, store_name, table_num, table_value);
		
		//model.addAttribute("arduSetLed", adtos); 
		
	}
	
}
