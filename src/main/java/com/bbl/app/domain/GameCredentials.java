package com.bbl.app.domain;

import io.nadron.util.Credentials;

public class GameCredentials implements Credentials {

	private String username;
	private String password;
	private String vkUid;
	private String vkKey;
	private String picture;
	private String hash;
	private Integer sex;

	public GameCredentials(String username, String vkUid, String vkKey) {
		this.username = username;
		this.vkUid = vkUid;
		this.vkKey = vkKey;
	}
	

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
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

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@Override
	public String toString() {
		return "vkUid: " + vkUid + "  vkKey:" + vkKey;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

}
