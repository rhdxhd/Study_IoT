package com.csslect.app.raspdto;

public class RaspDto {
	
	String store_id;
	String store_name;	
	String table_num;
	String start_time;
	String end_time;
	String accupation_time;
	
	// 가게 아이디, 가게이름, 좌석번호, 좌석입장시간, 좌석퇴장시간, 좌석점유시간 
	public RaspDto(String store_id, String store_name, String table_num, String start_time, String end_time,
			String accupation_time) {
		super();
		this.store_id = store_id;
		this.store_name = store_name;
		this.table_num = table_num;
		this.start_time = start_time;
		this.end_time = end_time;
		this.accupation_time = accupation_time;
	}
	

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getTable_num() {
		return table_num;
	}

	public void setTableNum(String table_num) {
		this.table_num = table_num;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getAccupation_time() {
		return accupation_time;
	}

	public void setAccupation_time(String accupation_time) {
		this.accupation_time = accupation_time;
	}
	

	
	
	
}
