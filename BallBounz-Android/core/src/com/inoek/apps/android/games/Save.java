package com.inoek.apps.android.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.inoek.apps.android.games.data.AchievementsTracker;
import com.inoek.apps.android.games.data.GameData;
import com.inoek.apps.android.games.data.PlayedClass;
import com.inoek.apps.android.games.data.Preferences;
import com.inoek.apps.android.games.data.gameclass;
import com.inoek.apps.android.games.data.gamename;
import com.inoek.apps.android.games.data.levelDecider;

public class Save {
	public static GameData gd;
	public static gameclass gc;
	public static gamename gname;
	public static Preferences pref;
	public static levelDecider leveldecider;
	public static PlayedClass pl;
    public static AchievementsTracker at;
	
	public static void saveAchievementsTrack(AchievementsTracker atr)
	{
		Json json = new Json();
		at = atr;
		FileHandle file = Gdx.files.local("data/achievementsTracker.dat");
		file.writeString(Base64Coder.encodeString(json.toJson(at)), false);
	}

	public static void savepreferences(Preferences pf) {
		Json json = new Json();
		pref = pf;
		FileHandle file = Gdx.files.local("data/preference.dat");
		file.writeString(Base64Coder.encodeString(json.toJson(pref)), false);
	}

	public static void save(GameData gd1) {
		Json json = new Json();
		gd = gd1;
		FileHandle file = Gdx.files.local("data/players.dat");
		file.writeString(Base64Coder.encodeString(json.toJson(gd)), false);
	}

	public static void savename(gamename gn1) {
		Json json = new Json();
		gname = gn1;
		FileHandle file = Gdx.files.local("data/tapdaballname.dat");
		file.writeString(Base64Coder.encodeString(json.toJson(gname)), false);
	}

	public static void save1(gameclass gc1) {
		Json json = new Json();
		gc = gc1;
		FileHandle file = Gdx.files.local("data/gameprogress.dat");
		file.writeString(Base64Coder.encodeString(json.toJson(gc)), false);

	}

	public static void SaveLevelDecider(levelDecider ld) {
		Json json = new Json();
		leveldecider = ld;
		FileHandle file = Gdx.files.local("data/levelDecider.dat");
		file.writeString(Base64Coder.encodeString(json.toJson(leveldecider)),
				false);
	}
	
	public static void SavePlayedClass(PlayedClass plc) {
		Json json = new Json();
		pl = plc;
		FileHandle file = Gdx.files.local("data/playfile.dat");
		file.writeString(Base64Coder.encodeString(json.toJson(pl)),
				false);
	}
	
	public static Preferences loadpreferences() {
		if (!PreferenceFileExists()) {
			initpref();
			return pref;
		}
		Json json = new Json();
		FileHandle file = Gdx.files.local(("data/preference.dat"));
		pref = json.fromJson(Preferences.class,
				Base64Coder.decodeString(file.readString()));
		return pref;
	}

	public static GameData load() {
		if (!SaveFileExists()) {
			init();
			return gd;
		}
		Json json = new Json();
		FileHandle file = Gdx.files.local("data/players.dat");
		gd = json.fromJson(GameData.class,
				Base64Coder.decodeString(file.readString()));
		return gd;
	}

	public static gamename loadname() {
		if (!SaveFileExists2()) {
			initname();
			return gname;
		}
		Json json = new Json();
		FileHandle file = Gdx.files.local("data/tapdaballname.dat");
		gname = json.fromJson(gamename.class,
				Base64Coder.decodeString(file.readString()));
		return gname;
	}

	public static gameclass load1() {
		if (!SaveFileExists1()) {
			init1();
			return gc;
		}
		Json json = new Json();
		FileHandle file = Gdx.files.local("data/gameprogress.dat");
		gc = json.fromJson(gameclass.class,Base64Coder.decodeString(file.readString()));
		return gc;

	}

	public static levelDecider loadLevelDecider() {
		if (!levelDeciderFileExists()) {
			initlevelDecider();
			return leveldecider;
		}
		Json json = new Json();
		FileHandle file = Gdx.files.local("data/levelDecider.dat");
		leveldecider = json.fromJson(levelDecider.class,
				Base64Coder.decodeString(file.readString()));
		return leveldecider;
	}
	
	public static PlayedClass loadPlayerData() {
		if (!PlayedFileExists()) {
			initPlayedFile();
			return pl;
		}
		Json json = new Json();
		FileHandle file = Gdx.files.local("data/playfile.dat");
		pl = json.fromJson(PlayedClass.class,
				Base64Coder.decodeString(file.readString()));
		return pl;
	}
	
	public static AchievementsTracker loadAchievemntsData() {
		if (!AchievementFileExists()) {
			initAchievementsFile();
			return at;
		}
		Json json = new Json();
		FileHandle file = Gdx.files.local("data/achievementsTracker.dat");
		at = json.fromJson(AchievementsTracker.class,Base64Coder.decodeString(file.readString()));
		return at;
	}
	
	public static void delete1() {
		if (SaveFileExists1()) {
			FileHandle file = Gdx.files.local("data/gameprogress.dat");
			file.delete();
		}
	}

	public static boolean PreferenceFileExists() {
		FileHandle f = Gdx.files.local(("data/preference.dat"));
		return f.exists();
	}

	public static boolean SaveFileExists() {
		FileHandle f = Gdx.files.local("data/players.dat");
		return f.exists();
	}

	public static boolean levelDeciderFileExists() {
		FileHandle f = Gdx.files.local("data/levelDecider.dat");
		return f.exists();
	}
	
	public static boolean PlayedFileExists() {
		FileHandle f = Gdx.files.local("data/playfile.dat");
		return f.exists();
	}
	
	public static boolean AchievementFileExists() {
		FileHandle f = Gdx.files.local("data/achievementsTracker.dat");
		return f.exists();
	}
	
	public static void init() {
		gd = new GameData();
		gd.init();
		save(gd);
	}

	public static boolean SaveFileExists1() {
		FileHandle f = Gdx.files.local("data/gameprogress.dat");
		return f.exists();
	}

	public static boolean SaveFileExists2() {
		FileHandle f = Gdx.files.local("data/tapdaballname.dat");
		return f.exists();
	}

	public static void init1() {
		gc = new gameclass();
		save1(gc);
	}

	public static void initpref() {
		pref = new Preferences();
		savepreferences(pref);
	}

	public static void initname() {
		gname = new gamename();
		savename(gname);
	}

	public static void initlevelDecider() {
		leveldecider = new levelDecider();
		SaveLevelDecider(leveldecider);
	}
	
	public static void initPlayedFile(){
		pl = new PlayedClass();
		SavePlayedClass(pl);
	}
	
	public static void initAchievementsFile(){
		at = new AchievementsTracker();
		saveAchievementsTrack(at);
	}
}
