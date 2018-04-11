package com.bbl.app.room;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bbl.app.domain.GameCmd;
import com.bbl.app.events.CustomEvent;
import com.bbl.app.handlers.LobbySessionHandler;
import com.bbl.app.handlers.PlayerSessionHandler;
import com.bbl.app.states.LobbyState;

import io.nadron.app.PlayerSession;
import io.nadron.app.impl.GameRoomSession;

public class LobbyRoom extends GameRoomSession {

	private static final Logger LOG = LoggerFactory.getLogger(LobbyRoom.class);

	private RoomFactory roomFactory;

	public LobbyRoom(GameRoomSessionBuilder gameRoomSessionBuilder) {
		super(gameRoomSessionBuilder);
		this.addHandler(new LobbySessionHandler(this));
		getStateManager().setState(new LobbyState());
	}

	@Override
	public void onLogin(PlayerSession playerSession) {
		LOG.info("sessions size: " + getSessions().size());
		playerSession.addHandler(new PlayerSessionHandler(playerSession));
		
		try {
			playerSession.onEvent(CustomEvent.networkEvent(GameCmd.PLAYER_DATA, playerSession.getPlayer()));
		} catch (JSONException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public RoomFactory getRoomFactory() {
		return roomFactory;
	}

	public void setRoomFactory(RoomFactory roomFactory) {
		this.roomFactory = roomFactory;
	}

}
