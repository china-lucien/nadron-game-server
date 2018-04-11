package com.bbl.app.states;

public class RoomState {

	private String name;
	private int maxPlayers;

	public RoomState() {
	}

	public RoomState(String name, int maxPlayers) {
		super();
		this.name = name;
		this.maxPlayers = maxPlayers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

}
