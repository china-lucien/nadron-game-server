package com.bbl;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.bbl.app.GameManager;
import com.bbl.config.SpringConfig;

import io.nadron.server.ServerManager;

public class GameServer {

	public static void main(String[] args) {
		
		PropertyConfigurator.configure("log4j.properties");
	
		if (System.getProperty("environment.type") == null) {
			System.setProperty("environment.type", "dev");
		}

		AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
		// For the destroy method to work.
		ctx.registerShutdownHook();
		
		// Load all game data
		GameManager gameManager = ctx.getBean(GameManager.class);
		gameManager.load();

		// Start the main game server
		ServerManager serverManager = ctx.getBean(ServerManager.class);
		try {
			serverManager.startServers();
		} catch (Exception e) {
			e.printStackTrace();
		}		
 
	}

}
