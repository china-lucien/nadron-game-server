package com.bbl.net;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.nadron.context.AppContext;
import io.nadron.server.ServerManager;
import io.nadron.server.netty.AbstractNettyServer;

public class GameServerManager implements ServerManager {

	private Set<AbstractNettyServer> servers;
	private static final Logger LOG = LoggerFactory.getLogger(GameServerManager.class);

	public GameServerManager() {
		servers = new HashSet<AbstractNettyServer>();
	}

	@Override
	public void startServers(int tcpPort, int flashPort, int udpPort) throws Exception {

	}

	@Override
	public void startServers() throws Exception {

		AbstractNettyServer tcpServer = (AbstractNettyServer) AppContext.getBean(AppContext.TCP_SERVER);
		tcpServer.startServer();
		servers.add(tcpServer);
	}

	@Override
	public void stopServers() throws Exception {
		for (AbstractNettyServer nettyServer : servers) {
			try {
				nettyServer.stopServer();
			} catch (Exception e) {
				LOG.error("Unable to stop server {} due to error {}", nettyServer, e);
				throw e;
			}
		}

	}

}
