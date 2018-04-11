package com.bbl.app.services;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.bbl.app.GameManager;
import com.bbl.app.MyGame;
import com.bbl.app.db.GameDao;
import com.bbl.app.domain.GameCredentials;
import com.bbl.app.domain.GamePlayer;
import com.bbl.app.room.LobbyRoom;
import com.bbl.utils.MD5;

import io.nadron.app.GameRoom;
import io.nadron.app.Player;
import io.nadron.service.impl.SimpleLookupService;
import io.nadron.util.Credentials;

public class GameLookupService extends SimpleLookupService{
	
	@Autowired
	private MyGame myGame;
	
	@Autowired
	private GameDao gameDao;
	
	@Autowired
	private LobbyRoom lobby;
	
	@Autowired
	private GameManager gameManager;
	
	@Override
	public Player playerLookup(Credentials loginDetail) {
		Optional<GamePlayer> player = Optional.empty();
		GameCredentials credentials = (GameCredentials) loginDetail;
		
		String authKey = myGame.getAppId() + '_' + credentials.getVkUid() + '_' + myGame.getAppSecret();
		try {
			// Проверка ключа
			if (Objects.equals(credentials.getVkKey().toUpperCase(), MD5.encode(authKey))) {
				player = gameDao.fetchPlayerByVk(credentials.getVkUid(), credentials.getVkKey());
				if(!player.isPresent()){
					// Создаем аккаунт
					player = Optional.of(cratePlayer((GameCredentials) loginDetail));
					gameDao.createPlayer(player.get());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return player.orElse(null);
	}

	private GamePlayer cratePlayer(GameCredentials loginDetail) {
		GamePlayer player = new GamePlayer();
		player.setVkUid(loginDetail.getVkUid());
		player.setVkKey(loginDetail.getVkKey());
		player.setName(loginDetail.getUsername());
		player.setPicture(loginDetail.getPicture());
		player.setSex(loginDetail.getSex());
		player.setRef(loginDetail.getHash());
		player.setMail("");
		player.setCreated(LocalDateTime.now());		
		player.setRating(0);
		player.setMoney(10);
		player.setClanId(0L);
		return player;
	}

	@Override
	public GameRoom gameRoomLookup(Object gameContextKey) {
		return lobby;
	}

}
