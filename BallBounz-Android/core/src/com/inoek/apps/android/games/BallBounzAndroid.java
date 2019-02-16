package com.inoek.apps.android.games;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.inoek.apps.android.games.data.PlayedClass;
import com.inoek.apps.android.games.data.SoundManager;
import com.inoek.apps.android.games.screens.HighScorescreen;
import com.inoek.apps.android.games.screens.LevelCompleteScreen;
import com.inoek.apps.android.games.screens.LevelScreen;
import com.inoek.apps.android.games.screens.LoginScreen;
import com.inoek.apps.android.games.screens.Mainscreen;
import com.inoek.apps.android.games.screens.SplashScreen;
import com.inoek.apps.android.games.screens.firstscreen;
import com.inoek.apps.android.games.screens.gameoverscreen;
import com.inoek.apps.android.games.screens.instructionScreen;
import com.inoek.apps.android.games.screens.pausescreen;
import com.inoek.apps.android.games.screens.secondscreen;
import com.inoek.apps.android.games.screens.settingscreen;
import com.inoek.apps.android.games.screens.startScreen;
import com.inoek.apps.android.games.screens.statsScreen;

public class BallBounzAndroid extends Game {
	public enum State {
		run, pause, back, gameover, levelComplete
	}
	public State st;
	public firstscreen first;
	public startScreen startscreen;
	public pausescreen ps;
	public secondscreen second;
	public gameoverscreen go;
	public LevelScreen levelscreen;
	public LevelCompleteScreen levelcompletescreen;
	public statsScreen stats;
	public instructionScreen instruScreen;
	public HighScorescreen hs;
	public SplashScreen ss;
	public LoginScreen ls;
	public settingscreen set;
	public Mainscreen ms;
	public int delay1, delay2, delay3;
	public TextureAtlas atlas;
	public Skin skin;
	public float v;
	public int width, height, presentLevel;
	public AdsController adsController;
	public String[] achievementsArray;
	public PlayedClass plc;
	private SoundManager smanager;

	public interface MyGameCallBack {
		public void onStartActivityA();

		public void onStartActivityB();
	}

	public MyGameCallBack mygamecallback;

	public void setMygamecallback(MyGameCallBack mygamecallback) {
		this.mygamecallback = mygamecallback;
	}

	public BallBounzAndroid(AdsController adsController) {
		this.adsController = adsController;

	}
	
	public SoundManager getSoundManager() {
		return smanager;
	}

	@Override
	public void create() {
		achievementsArray = new String[] {"CgkIj73WhbAeEAIQAA","CgkIj73WhbAeEAIQAQ","CgkIj73WhbAeEAIQAg","CgkIj73WhbAeEAIQAw","CgkIj73WhbAeEAIQBA",
				"CgkIj73WhbAeEAIQBQ","CgkIj73WhbAeEAIQBg","CgkIj73WhbAeEAIQBw","CgkIj73WhbAeEAIQCA","CgkIj73WhbAeEAIQCQ",
				"CgkIj73WhbAeEAIQCg","CgkIj73WhbAeEAIQCw","CgkIj73WhbAeEAIQDA","CgkIj73WhbAeEAIQDQ","CgkIj73WhbAeEAIQDg",
				"CgkIj73WhbAeEAIQDw","CgkIj73WhbAeEAIQEA","CgkIj73WhbAeEAIQEQ","CgkIj73WhbAeEAIQEg","CgkIj73WhbAeEAIQEw",
				"CgkIj73WhbAeEAIQFA","CgkIj73WhbAeEAIQFQ","CgkIj73WhbAeEAIQFg","CgkIj73WhbAeEAIQFw","CgkIj73WhbAeEAIQGA"};
		st = State.run;
		plc = new PlayedClass();
		plc = Save.loadPlayerData();
		Save.SavePlayedClass(plc);
		smanager = new SoundManager();
		smanager.load();
		first = new firstscreen(this);
		startscreen = new startScreen(this);
		ps = new pausescreen(this);
		second = new secondscreen(this);
		levelscreen = new LevelScreen(this);
		levelcompletescreen = new LevelCompleteScreen(this);
		go = new gameoverscreen(this);
		hs = new HighScorescreen(this);
		ss = new SplashScreen(this);
		set = new settingscreen(this);
		ms = new Mainscreen(this);
		stats = new statsScreen(this);
		ls = new LoginScreen(this);
		instruScreen = new instructionScreen(this);
		atlas = new TextureAtlas("cricpack.pack");
		skin = new Skin(Gdx.files.internal("uijson.json"), atlas);
		if (adsController.isWifiConnected()) {
			adsController.showBannerAd();
		}
		delay1 = 0;
		delay2 = 0;
		delay3 = 0;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		setScreen(ms);
	}

	@Override
	public void dispose() {
		super.dispose();
		atlas.dispose();
		skin.dispose();
		smanager.dispose();
	}

	@Override
	public void render() {
		super.render();
		if (adsController.isWifiConnected() && !adsController.isBannerShowing()) {
			adsController.showBannerAd();
		}
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
		smanager.dispose();
	}

	@Override
	public void resume() {
		super.resume();
		smanager.load();
	}
}
