package com.bbl.app.handlers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bbl.app.domain.GameCmd;
import com.bbl.app.events.CustomEvent;
import com.bbl.app.room.LobbyRoom;
import com.bbl.app.room.SimpleRoom;
import com.bbl.app.states.LobbyState;
import com.bbl.app.states.RoomState;

import io.nadron.app.GameRoom;
import io.nadron.app.PlayerSession;
import io.nadron.app.impl.InvalidCommandException;
import io.nadron.event.Event;
import io.nadron.event.impl.DefaultEventContext;
import io.nadron.event.impl.SessionMessageHandler;

/**
 * @author Andronov
 *
 */
@Component
public class LobbySessionHandler extends SessionMessageHandler {

	private static final Logger LOG = LoggerFactory.getLogger(LobbySessionHandler.class);

	private LobbyRoom lobby;

	public LobbySessionHandler(LobbyRoom session) {
		super(session);
		this.lobby = session;
	}

	@Override
	public void onEvent(Event event) {

		CustomEvent customEvent = (CustomEvent) event;
		GameCmd cmd = GameCmd.CommandsEnum.fromInt(customEvent.getSource().getCmd());
		try {
			switch (cmd) {
			case CREATE_GAME:
				createRoom(customEvent);
				break;
			case GET_OPEN_ROOMS:
				broadcastRoomList(customEvent);
				break;
			case JOIN_ROOM:
				connectToRoom(customEvent);
				break;
			default:
				LOG.error("Received invalid command {}", cmd);
				throw new InvalidCommandException("Received invalid command" + cmd);
			}
		} catch (InvalidCommandException e) {
			e.printStackTrace();
			LOG.error("{}", e);
		}
	}
	
	private void connectToRoom(CustomEvent event) {
		DefaultEventContext dec = (DefaultEventContext) event.getEventContext();
		LobbyState state = (LobbyState) lobby.getStateManager().getState();
		PlayerSession playerSession = (PlayerSession) dec.getSession();
		
		@SuppressWarnings("unchecked")
		LinkedHashMap<String, String> source = (LinkedHashMap<String, String>) event.getSource().getData();
		SimpleRoom room = state.getRoom(source.get("id"));
		
		if(null == room) {
			playerSession.onEvent(CustomEvent.networkEvent(GameCmd.ROOM_DATA, room));
			return;
		}
		
		//lobby.disconnectSession(playerSession);
		//room.connectSession(playerSession);
		changeRoom(playerSession, room);
		
		playerSession.onEvent(CustomEvent.networkEvent(GameCmd.ROOM_DATA, room));
	}
	
	private void changeRoom(PlayerSession playerSession, GameRoom room) {
		playerSession.getGameRoom().disconnectSession(playerSession);
		room.connectSession(playerSession);
	}

	private void broadcastRoomList(CustomEvent event) {
		DefaultEventContext dec = (DefaultEventContext) event.getEventContext();
		LobbyState state = (LobbyState) lobby.getStateManager().getState();
		PlayerSession playerSession = (PlayerSession) dec.getSession();

		
		List<RoomState> roomList = new ArrayList<>();
		state.getRooms().values().forEach(sr -> roomList.add(sr.getState()));
		playerSession.onEvent(CustomEvent.networkEvent(GameCmd.ROOM_LIST, roomList));
	}

	private void createRoom(CustomEvent event) {
		@SuppressWarnings("unchecked")
		Map<String, String> source = (LinkedHashMap<String, String>) event.getSource().getData();
		DefaultEventContext dec = (DefaultEventContext) event.getEventContext();
		PlayerSession playerSession = (PlayerSession) dec.getSession();

		String name = String.valueOf(source.get("name"));
		int maxPlayers = Integer.valueOf(source.get("maxPlayers"));

		RoomState roomState = new RoomState(name, maxPlayers);
		SimpleRoom room = (SimpleRoom) lobby.getRoomFactory().createRoom(playerSession.getPlayer(), roomState);

		LobbyState state = (LobbyState) lobby.getStateManager().getState();
		state.addRoom(room);
		
		lobby.disconnectSession(playerSession);
		room.connectSession(playerSession);
		
		List<RoomState> roomList = new ArrayList<>();
		state.getRooms().values().forEach(sr -> roomList.add(sr.getState()));
		lobby.sendBroadcast(CustomEvent.networkEvent(GameCmd.ROOM_LIST, roomList));
	
		playerSession.onEvent(CustomEvent.networkEvent(GameCmd.ROOM_DATA, room));
	}

}
