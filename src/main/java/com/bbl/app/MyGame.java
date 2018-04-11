package com.bbl.app;

import io.nadron.app.GameCommandInterpreter;
import io.nadron.app.impl.SimpleGame;

public class MyGame extends SimpleGame {

	public static final String GAME_NAME = "game";
	public static final String LOBBY_NAME = "lobby";
	
	private String appId;
	private String appSecret;

	public MyGame(Object id, String gameName) {
		super(id, gameName);
	}

	public MyGame(Object id, String gameName, GameCommandInterpreter gameCommandInterpreter) {
		super(id, gameName, gameCommandInterpreter);
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

}
