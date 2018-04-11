package com.bbl.app.states;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.bbl.app.room.SimpleRoom;

public class LobbyState implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Map<String, SimpleRoom> rooms = new HashMap<>();

	public LobbyState() {
	}

	public void addRoom(SimpleRoom room) {
		rooms.put(room.getGameRoomName(), room);
	}

	public SimpleRoom removeRoom(String name) {
		return rooms.remove(name);
	}
	
	public SimpleRoom getRoom(String name) {
		return rooms.get(name);
	}

	public Map<String, SimpleRoom> getRooms() {
		return rooms;
	}

}
