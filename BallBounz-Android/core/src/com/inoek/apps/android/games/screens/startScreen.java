package com.inoek.apps.android.games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.inoek.apps.android.games.BallBounzAndroid;
import com.inoek.apps.android.games.Save;
import com.inoek.apps.android.games.BallBounzAndroid.State;
import com.inoek.apps.android.games.data.AchievementsTracker;
import com.inoek.apps.android.games.data.PlayedClass;
import com.inoek.apps.android.games.data.levelDecider;

public class startScreen implements Screen {
	BallBounzAndroid game;
	Stage stage;
	Table table;			
	TextButton loadGame,newGame,highScores,settings,exit, ok,no,ok1,no1,instruction;
	levelDecider ld;
	Dialog dialog,dialog1;
	PlayedClass plc;
	AchievementsTracker atracker;
	public startScreen(BallBounzAndroid game)
	{
		this.game=game;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		stage = new Stage();
		ld = new levelDecider();
		ld = Save.loadLevelDecider();
		plc = new PlayedClass();
		plc = Save.loadPlayerData();
		atracker = new AchievementsTracker();
		atracker = Save.loadAchievemntsData();
		table = new Table(game.skin);
		table.setBackground("uibackground");
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		ok  = new TextButton("yes",game.skin);
		ok.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				Gdx.input.vibrate(75);
				Save.delete1();
				Gdx.app.exit();
			}
		});
		no = new TextButton("no",game.skin);
		no.addListener(new ClickListener() {
			
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				dialog.remove();
				Gdx.input.vibrate(75);
				loadGame.setVisible(true);
				newGame.setVisible(true);
				settings.setVisible(true);
				highScores.setVisible(true);
				exit.setVisible(true);
				instruction.setVisible(true);
			}
		});
		dialog = new Dialog("", game.skin, "dialog"){
			{
			text("Are you sure to Quit ?");
			getButtonTable().add(ok).size(0.2f*game.height, 0.16f*game.height).left();
			getButtonTable().add(no).size(0.2f*game.height, 0.16f*game.height).right();
			}
		};
		
		ok1  = new TextButton("yes",game.skin);
		ok1.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				Gdx.input.vibrate(75);
				Save.delete1();
				ld.reset(atracker.getAchievementNumber());
				Save.SaveLevelDecider(ld);
				Gdx.input.setInputProcessor(null);
				game.st = State.run;
				game.setScreen(game.levelscreen);
			}
		});
		no1 = new TextButton("no",game.skin);
		no1.addListener(new ClickListener() {
			
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				dialog1.remove();
				Gdx.input.vibrate(75);
				loadGame.setVisible(true);
				newGame.setVisible(true);
				settings.setVisible(true);
				highScores.setVisible(true);
				exit.setVisible(true);
				instruction.setVisible(true);
			}
		});
		dialog1 = new Dialog("", game.skin, "dialog"){
			{
			text("Lose your Progress ?");
			getButtonTable().add(ok1).size(0.2f*game.height, 0.16f*game.height).left();
			getButtonTable().add(no1).size(0.2f*game.height, 0.16f*game.height).right();
			}
		};
		
		loadGame = new TextButton("", game.skin, "loadgame");
		loadGame.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				Gdx.input.vibrate(75);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				stage.addAction(Actions.sequence(
						Actions.moveBy(-stage.getWidth(), 0, .5f),
						Actions.run(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								Gdx.input.setInputProcessor(null);
								game.setScreen(game.levelscreen);
							}
						})));

			}
		});
		table.add(loadGame).size(0.5729167f * game.width,0.146806f * game.height).row();
		
		newGame = new TextButton("", game.skin, "newgame");
		newGame.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				Gdx.input.vibrate(75);
				loadGame.setVisible(false);
				newGame.setVisible(false);
				settings.setVisible(false);
				highScores.setVisible(false);
				exit.setVisible(false);
				instruction.setVisible(false);
				Gdx.input.setInputProcessor(null);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dialog1.show(stage);
			}
		});
		table.add(newGame).size(0.5729167f * game.width,0.146806f * game.height).row();


		highScores = new TextButton("", game.skin, "stats");
		highScores.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				Gdx.input.vibrate(75);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				stage.addAction(Actions.sequence(
						Actions.moveBy(-stage.getWidth(), 0, .5f),
						Actions.run(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								Gdx.input.setInputProcessor(null);
								game.setScreen(game.stats);
							}
						})));

			}
		});
		table.add(highScores).size(0.5729167f * game.width,0.146806f * game.height).row();

		settings = new TextButton("", game.skin, "setting");
		settings.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				Gdx.input.vibrate(75);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				stage.addAction(Actions.sequence(
						Actions.moveBy(-stage.getWidth(), 0, .5f),
						Actions.run(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								Gdx.input.setInputProcessor(null);
								game.setScreen(game.set);
							}
						})));

			}
		});
		table.add(settings).size(0.5729167f * game.width,0.146806f * game.height).row();

		instruction = new TextButton("", game.skin, "instructions");
		instruction.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				Gdx.input.vibrate(75);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				stage.addAction(Actions.sequence(
						Actions.moveBy(-stage.getWidth(), 0, .5f),
						Actions.run(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								Gdx.input.setInputProcessor(null);
								game.setScreen(game.instruScreen);
							}
						})));

			}
		});
		table.add(instruction).size(0.5729167f * game.width,0.146806f * game.height).row();

		exit = new TextButton("", game.skin, "exit");
		exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				Gdx.input.vibrate(75);
				loadGame.setVisible(false);
				newGame.setVisible(false);
				settings.setVisible(false);
				highScores.setVisible(false);
				exit.setVisible(false);
				instruction.setVisible(false);
				if (game.adsController.isWifiConnected()) {
				    game.adsController.showInterstitialAd(new Runnable() {
				        @Override
				        public void run() {
				                
				        }
				    });
				} else {
				  
				}
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dialog.show(stage);
			}
		});
		table.add(exit).size(0.5729167f * game.width,0.146806f * game.height).row();
		table.setFillParent(true);
		stage.addActor(table);
		stage.addAction(Actions.sequence(Actions.moveTo(-stage.getWidth(), 0),
				Actions.moveTo(0, 0, .5f)));
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		Gdx.input.setInputProcessor(stage);
		stage.getBatch().begin();
		stage.getBatch().draw(game.skin.getRegion("UIBackground"), 0, 0,
				Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.getBatch().end();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
	}
	
}
