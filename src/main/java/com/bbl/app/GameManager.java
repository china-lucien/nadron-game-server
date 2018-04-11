package com.bbl.app;

import org.springframework.beans.factory.annotation.Autowired;

import com.bbl.app.db.GameDao;

public class GameManager {

	@Autowired
	private GameDao gameDao;
	
	public GameManager() {
		
	}
	
	public void load() {
		
		// Загрузка данных из БД
	}
	
}
