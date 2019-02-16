package com.inoek.apps.android.games.data;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

public class PlayedClass implements Serializable {

	boolean playedFlag;
	
	public PlayedClass()
	{
		playedFlag = false;
	}
	

	public boolean isPlayedFlag() {
		return playedFlag;
	}
	
	public void setPlayedFlag(boolean playedFlag) {
		this.playedFlag = playedFlag;
	}
	
	@Override
	public void write(Json json) {
		// TODO Auto-generated method stub
		json.writeValue("playedFlag", playedFlag);
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		// TODO Auto-generated method stub
		playedFlag = json.readValue("playedFlag",boolean.class, jsonData);
	}

}
