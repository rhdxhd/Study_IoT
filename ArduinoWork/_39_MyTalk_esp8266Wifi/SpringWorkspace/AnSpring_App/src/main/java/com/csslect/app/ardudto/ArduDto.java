package com.csslect.app.ardudto;

public class ArduDto {
	
	String id;
	String name;	
	String value;
	String updatetime;
		
	public ArduDto() {
		
	}

	public ArduDto(String id, String name, String value, String updatetime) {		
		this.id = id;
		this.name = name;		
		this.value = value;
		this.updatetime = updatetime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}	

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	
	
	
}
