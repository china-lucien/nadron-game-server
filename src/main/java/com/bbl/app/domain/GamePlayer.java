package com.bbl.app.domain;

import java.time.LocalDateTime;

import io.nadron.app.impl.DefaultPlayer;

public class GamePlayer extends DefaultPlayer {

	private String vkUid;
	private String vkKey;
	private String picture;
	private String password;
	private String mail;
	private String ref;
	private Integer sex;
	private Integer rating;
	private Integer money;
	private LocalDateTime created;
	private Long clanId;

	public GamePlayer() {
	}

	public GamePlayer(Object id, String name, String emailId) {
		super(id, name, emailId);
	}

	public String getVkUid() {
		return vkUid;
	}

	public void setVkUid(String vkUid) {
		this.vkUid = vkUid;
	}

	public String getVkKey() {
		return vkKey;
	}

	public void setVkKey(String vkKey) {
		this.vkKey = vkKey;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public Long getClanId() {
		return clanId;
	}

	public void setClanId(Long clanId) {
		this.clanId = clanId;
	}

}
