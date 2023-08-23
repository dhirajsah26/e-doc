package com.example.edoc.dao;

import com.example.edoc.models.Settings;

public interface SettingDao {
	
	
	public Boolean save(Settings setting);
	
	public Boolean update(Settings setting);
	
	public Settings findDate();
	

}
