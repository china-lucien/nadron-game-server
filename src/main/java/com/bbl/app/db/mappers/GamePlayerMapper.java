package com.bbl.app.db.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bbl.app.domain.GamePlayer;

public class GamePlayerMapper implements RowMapper<GamePlayer> {

	@Override
	public GamePlayer mapRow(ResultSet rs, int rowNum) throws SQLException {
		GamePlayer player = new GamePlayer();
		player.setId(rs.getLong("id"));
		player.setName(rs.getString("name"));
		player.setEmailId(rs.getString("mail"));
		player.setVkKey(rs.getString("vk_key"));
		player.setVkUid(rs.getString("vk_uid"));
		player.setMoney(rs.getInt("money"));
		player.setRating(rs.getInt("rating"));
		player.setClanId(rs.getLong("clan_id"));
		return player;
	}


}
