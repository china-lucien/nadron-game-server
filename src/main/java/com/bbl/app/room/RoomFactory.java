package com.bbl.app.room;

import com.bbl.app.states.RoomState;

import io.nadron.app.Game;
import io.nadron.app.Player;
import io.nadron.app.impl.GameRoomSession;
import io.nadron.protocols.Protocol;

public interface RoomFactory {

	GameRoomSession createRoom(Player player, RoomState room);

	void setGame(Game game);

	Game getGame();

	void setProtocol(Protocol protocol);

	Protocol getProtocol();

}
