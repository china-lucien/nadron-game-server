package com.bbl.app.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bbl.app.domain.GameCmd;
import com.bbl.app.events.CustomEvent;
import com.bbl.app.states.RoomState;

import io.nadron.app.Player;
import io.nadron.app.PlayerSession;
import io.nadron.app.impl.GameRoomSession;
import io.nadron.event.Event;

public class SimpleRoom extends GameRoomSession{
	private static final Logger LOG = LoggerFactory.getLogger(SimpleRoom.class);

	private RoomState state;
	private Player master;

	public SimpleRoom(GameRoomSessionBuilder gameRoomSessionBuilder, Player master, RoomState state) {
		super(gameRoomSessionBuilder);
		this.master = master;
		this.state = state;
	}
	
	@Override
	public void onEvent(Event event) {
		CustomEvent customEvent = (CustomEvent) event;
		GameCmd cmd = GameCmd.CommandsEnum.fromInt(customEvent.getSource().getCmd());
		LOG.info("SimpleRoom new message cmd: {}", cmd.toString());
	}
	
	@Override
	public void onLogin(PlayerSession playerSession) {
		LOG.info("Room new session, sessions size: {}", getSessions().size());

	}

	public Player getMaster() {
		return master;
	}

	public RoomState getState() {
		return state;
	}

	public void setState(RoomState room) {
		this.state = room;
	}

}
