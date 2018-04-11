package com.bbl.app.domain;

import java.util.HashMap;
import java.util.Map;

public enum GameCmd {
	UNKNOWN(0),
	ERROR(1),
	PLAYER_DATA(2),
	GET_OPEN_ROOMS(5),
	ROOM_LIST(6),
	CREATE_GAME(10),
	GAME_CREATED(11),
	JOIN_ROOM(20),
	ROOM_DATA(21);
	
	final int code;
	GameCmd(int code) {
		this.code = code;
	}
	public int getCode() {
		return code;
	}
	
	public static class CommandsEnum
	{
		private static final Map<Integer, GameCmd> INT_COMMAND_MAP;
		static {
			INT_COMMAND_MAP = new HashMap<>();
			for(GameCmd command: GameCmd.values()){
				INT_COMMAND_MAP.put(command.getCode(), command);
			}
		}
		
		public static GameCmd fromInt(Integer i)
		{
			GameCmd command = INT_COMMAND_MAP.get(i);
			if(null == command){
				command = GameCmd.UNKNOWN;
			}
			return command;
		}
	}
}
