package com.bbl.net.protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.nadron.app.PlayerSession;
import io.nadron.handlers.netty.DefaultToServerHandler;
import io.nadron.handlers.netty.LoginProtocol;
import io.nadron.handlers.netty.TextWebsocketDecoder;
import io.nadron.handlers.netty.TextWebsocketEncoder;
import io.nadron.protocols.AbstractNettyProtocol;
import io.nadron.protocols.impl.WebSocketProtocol;
import io.nadron.util.NettyUtils;
import io.netty.channel.ChannelPipeline;

public class GameWebsocketProtocol extends AbstractNettyProtocol {

	private static final Logger LOG = LoggerFactory.getLogger(WebSocketProtocol.class);

	private static final String TEXT_WEBSOCKET_DECODER = "textWebsocketDecoder";
	private static final String TEXT_WEBSOCKET_ENCODER = "textWebsocketEncoder";
	private static final String EVENT_HANDLER = "eventHandler";

	private TextWebsocketDecoder textWebsocketDecoder;
	private TextWebsocketEncoder textWebsocketEncoder;

	public GameWebsocketProtocol() {
		super("GAME_WEB_SOCKET_PROTOCOL");
	}

	@Override
	public void applyProtocol(PlayerSession playerSession, boolean clearExistingProtocolHandlers) {

		applyProtocol(playerSession);

		if (clearExistingProtocolHandlers) {
			ChannelPipeline pipeline = NettyUtils.getPipeLineOfConnection(playerSession);
			if (pipeline.get(LoginProtocol.LOGIN_HANDLER_NAME) != null)
				pipeline.remove(LoginProtocol.LOGIN_HANDLER_NAME);
			if (pipeline.get(AbstractNettyProtocol.IDLE_STATE_CHECK_HANDLER) != null)
				pipeline.remove(AbstractNettyProtocol.IDLE_STATE_CHECK_HANDLER);
		}
	}

	@Override
	public void applyProtocol(PlayerSession playerSession) {

		LOG.trace("Going to apply {} on session: {}", getProtocolName(), playerSession);
		ChannelPipeline pipeline = NettyUtils.getPipeLineOfConnection(playerSession);
		if (pipeline.get(TEXT_WEBSOCKET_DECODER) == null)
			pipeline.addLast(TEXT_WEBSOCKET_DECODER, textWebsocketDecoder);
		if (pipeline.get(EVENT_HANDLER) == null)
			pipeline.addLast(EVENT_HANDLER, new DefaultToServerHandler(playerSession));
		if (pipeline.get(TEXT_WEBSOCKET_ENCODER) == null)
			pipeline.addLast(TEXT_WEBSOCKET_ENCODER, textWebsocketEncoder);
	}

	public TextWebsocketDecoder getTextWebsocketDecoder() {
		return textWebsocketDecoder;
	}

	public void setTextWebsocketDecoder(TextWebsocketDecoder textWebsocketDecoder) {
		this.textWebsocketDecoder = textWebsocketDecoder;
	}

	public TextWebsocketEncoder getTextWebsocketEncoder() {
		return textWebsocketEncoder;
	}

	public void setTextWebsocketEncoder(TextWebsocketEncoder textWebsocketEncoder) {
		this.textWebsocketEncoder = textWebsocketEncoder;
	}
}
