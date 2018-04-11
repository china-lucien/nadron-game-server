package com.bbl.app.db;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Transactional;

import com.bbl.app.db.mappers.GamePlayerMapper;
import com.bbl.app.domain.GamePlayer;

public class GameDao {

	@Autowired
	private JdbcTemplate jdbc;

	// ------------- PLAYER --------------
	@Transactional
	public Optional<GamePlayer> createPlayer(GamePlayer player) {
		
		final SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbc);
		insertActor.withTableName("players");
		insertActor.usingGeneratedKeyColumns("id");

		final MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("mail", player.getMail());
		params.addValue("name", player.getName());
		params.addValue("password", player.getPassword());
		params.addValue("vk_uid", player.getVkUid());
		params.addValue("vk_key", player.getVkKey());
		params.addValue("ref", player.getRef());
		params.addValue("sex", player.getSex());
		params.addValue("money", player.getMoney());
		params.addValue("clan_id", player.getClanId());
		params.addValue("rating", player.getRating());
		params.addValue("created", Date.from(player.getCreated().atZone(ZoneId.systemDefault()).toInstant()));

		final Long id = (Long) insertActor.executeAndReturnKey(params);
		player.setId(id);

		return Optional.of(player);
	}

	@Transactional(readOnly = true)
	public Optional<GamePlayer> fetchPlayerByVk(String uid, String key) {
		String sql = "SELECT * FROM players WHERE vk_uid=? AND vk_key=? ";
		Object[] params = { uid, key };
		List<GamePlayer> players = jdbc.query(sql, params, new GamePlayerMapper());
		return players.stream().findFirst();
	}	
	// -----------------------------------


}
