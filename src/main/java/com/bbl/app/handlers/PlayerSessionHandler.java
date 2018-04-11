package com.bbl.app.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.nadron.app.PlayerSession;
import io.nadron.event.Event;
import io.nadron.event.impl.DefaultEventContext;
import io.nadron.event.impl.DefaultSessionEventHandler;

public class PlayerSessionHandler extends DefaultSessionEventHandler {

	private static final Logger LOG = LoggerFactory.getLogger(LobbySessionHandler.class);
	private PlayerSession playerSession;

	public PlayerSessionHandler(PlayerSession playerSession) {
		super(playerSession);
		this.playerSession = playerSession;
	}

	@Override
	protected void onDataIn(Event event) {
		if (null != event.getSource()) {
			event.setEventContext(new DefaultEventContext(playerSession, null));
			playerSession.getGameRoom().send(event);
			
		}
	}

}
