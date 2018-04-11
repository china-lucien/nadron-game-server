package com.bbl.config;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bbl.app.GameManager;
import com.bbl.app.MyGame;
import com.bbl.app.db.GameDao;
import com.bbl.app.room.LobbyRoom;
import com.bbl.app.room.RoomFactory;
import com.bbl.app.room.SimpleRoomFactory;
import com.bbl.app.services.GameLookupService;
import com.bbl.net.GameServerManager;
import com.bbl.net.protocol.GameWebsocketProtocol;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import io.nadron.app.impl.GameRoomSession.GameRoomSessionBuilder;
import io.nadron.context.AppContext;
import io.nadron.handlers.netty.TextWebsocketDecoder;
import io.nadron.handlers.netty.TextWebsocketEncoder;
import io.nadron.protocols.impl.WebSocketProtocol;
import io.nadron.server.ServerManager;
import io.nadron.service.LookupService;

@Configuration
@PropertySource("classpath:config-${environment.type}.properties")
@ImportResource("classpath:/beans/beans.xml")
public class SpringConfig {

	@Autowired
	Environment env;

	// --------------- NET ---------------
	@Autowired
	@Qualifier("textWebsocketDecoder")
	private TextWebsocketDecoder textWebsocketDecoder;
	
	@Autowired
	@Qualifier("textWebsocketEncoder")
	private TextWebsocketEncoder textWebsocketEncoder;
	
	@Bean(name = "gameWebSocketProtocol")
	public GameWebsocketProtocol webSocketProtocol() {
		GameWebsocketProtocol protocol = new GameWebsocketProtocol();
		protocol.setTextWebsocketDecoder(textWebsocketDecoder);
		protocol.setTextWebsocketEncoder(textWebsocketEncoder);
		return protocol;
	}
	
	@Autowired
	@Qualifier("webSocketProtocol")
	private WebSocketProtocol webSocketProtocol;

	@Bean(name = AppContext.SERVER_MANAGER)
	public ServerManager serverManager() {
		return new GameServerManager();
	}

	@Bean
	public LookupService lookupService() {
		return new GameLookupService();
	}

	// --------------- APP ---------------

	@Bean(name = "myGame")
	public MyGame myGame() {
		MyGame game = new MyGame(1, MyGame.GAME_NAME);
		game.setAppId(env.getProperty("pf.app_id"));
		game.setAppSecret(env.getProperty("pf.app_secret"));
		return game;
	}
	
	@Bean(name = "roomFactory")
	public RoomFactory roomFactory() {
		RoomFactory roomFactory = new SimpleRoomFactory();
		roomFactory.setGame(myGame());
		roomFactory.setProtocol(webSocketProtocol());
		return roomFactory;
	}

	@Bean(name = "lobbyRoom")
	public LobbyRoom lobbyRoom() {
		GameRoomSessionBuilder sessionBuilder = new GameRoomSessionBuilder();
		sessionBuilder.parentGame(myGame()).gameRoomName("lobby").protocol(webSocketProtocol());
		LobbyRoom lobby = new LobbyRoom(sessionBuilder);
		lobby.setRoomFactory(roomFactory());
		return lobby;
	}
	// --------------- DATA ---------------
	@Bean(name = "gameManager")
	public GameManager gameManager() {
		return new GameManager();
	}

	@Bean(name = "gameDao")
	public GameDao gameDao() {
		return new GameDao();
	}

	@Bean
	public JdbcTemplate jdbcTemplate() throws PropertyVetoException {
		return new JdbcTemplate(dataSource());
	}

	@Bean
	public DataSource dataSource() throws PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass("");
		dataSource.setJdbcUrl(env.getProperty("db.url"));
		dataSource.setUser(env.getProperty("db.user"));
		dataSource.setPassword(env.getProperty("db.pass"));
		dataSource.setMinPoolSize(5);
		dataSource.setAcquireIncrement(5);
		dataSource.setMaxPoolSize(20);
		dataSource.setMaxStatements(180);
		dataSource.setTestConnectionOnCheckout(true);
		dataSource.setPreferredTestQuery("SELECT 1");

		return dataSource;
	}

	@Bean(initMethod = "migrate")
	Flyway flyway() throws PropertyVetoException {
		Flyway flyway = new Flyway();
		flyway.setBaselineOnMigrate(true);
		flyway.setLocations("/db/migration/");
		flyway.setDataSource(dataSource());
		return flyway;
	}

}
