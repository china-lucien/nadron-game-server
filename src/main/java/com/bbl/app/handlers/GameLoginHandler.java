package com.bbl.app.handlers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bbl.app.MyGame;
import com.bbl.app.domain.GameCredentials;

import io.nadron.app.Player;
import io.nadron.app.PlayerSession;
import io.nadron.event.Event;
import io.nadron.event.Events;
import io.nadron.event.impl.DefaultEvent;
import io.nadron.handlers.netty.WebSocketLoginHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class GameLoginHandler extends WebSocketLoginHandler {

	private static final Logger LOG = LoggerFactory.getLogger(GameLoginHandler.class);
	
	@Override
	public void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
		Channel channel = ctx.channel();
		String data = frame.text();
		Event event = getJackson().readValue(data, DefaultEvent.class);
		int type = event.getType();
		if (Events.LOG_IN == type) {
			LOG.trace("Login attempt from {}", channel.remoteAddress());
			@SuppressWarnings("unchecked")
			Player player = lookupPlayer((LinkedHashMap<String, Object>) event.getSource());
			handleLogin(player, channel);
			if(null != player)
				handleGameRoomJoin(player, channel, MyGame.LOBBY_NAME);
		} else if (type == Events.RECONNECT) {
			LOG.debug("Reconnect attempt from {}", channel.remoteAddress());
			PlayerSession playerSession = lookupSession((String) event.getSource());
			handleReconnect(playerSession, channel);
		} else {
			LOG.error("Invalid event {} sent from remote address {}. " + "Going to close channel {}",
					new Object[] { event.getType(), channel.remoteAddress(), channel });
			closeChannelWithLoginFailure(channel);
		}
	}

	public Player lookupPlayer(Map<String, Object> source) throws Exception {
		String user = String.valueOf(source.get("user"));
		String vkUid = String.valueOf(source.get("uid"));
		String vkKey = String.valueOf(source.get("key"));
		
		GameCredentials credentials = new GameCredentials(user, vkUid, vkKey);		
		credentials.setPicture(String.valueOf(source.get("picture")));
		credentials.setSex(Integer.valueOf(String.valueOf(source.get("sex"))));
		credentials.setHash(String.valueOf(source.get("hash")));	
		Player player = getLookupService().playerLookup(credentials);
		if (null == player) {
			LOG.error("Invalid credentials provided by user: {}", credentials);
		}
		return player;
	}

}
