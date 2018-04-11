package com.bbl.app.room;

import com.bbl.app.states.RoomState;

import io.nadron.app.Game;
import io.nadron.app.Player;
import io.nadron.app.impl.GameRoomSession;
import io.nadron.app.impl.GameRoomSession.GameRoomSessionBuilder;
import io.nadron.protocols.Protocol;

public class SimpleRoomFactory implements RoomFactory{

	private static final String GAME_ROOM_NAME = "gameRoom";
	private static final GameRoomSessionBuilder GAME_SESSION_BUILDER = new GameRoomSessionBuilder();

	private Game game;
	private Protocol protocol;
	
	@Override
	public GameRoomSession createRoom(Player player, RoomState roomData) {
		GAME_SESSION_BUILDER.parentGame(game).gameRoomName(GAME_ROOM_NAME + String.valueOf(player.getId())).protocol(protocol);
		return new SimpleRoom(GAME_SESSION_BUILDER, player, roomData);
	}

	@Override
	public void setGame(Game game) {
		this.game = game;		
	}

	@Override
	public Game getGame() {
		return this.game;
	}

	@Override
	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}

	@Override
	public Protocol getProtocol() {
		return protocol;
	}
}
