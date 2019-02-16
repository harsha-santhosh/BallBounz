package com.inoek.apps.android.games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.inoek.apps.android.games.BallBounzAndroid;
import com.inoek.apps.android.games.BallBounzAndroid.State;
import com.inoek.apps.android.games.Save;
import com.inoek.apps.android.games.data.GameData;
import com.inoek.apps.android.games.data.gamename;
import com.inoek.apps.android.games.data.levelDecider;

public class gameoverscreen implements Screen {

	BallBounzAndroid game;
	GameData gd;
	gamename gn;
	levelDecider ld;
	Stage stage;
	long s;
	Table table;
	TextButton button, button1, button4;
	Label label, score;
	Image ima;
	
	public gameoverscreen(BallBounzAndroid game)
	{
		this.game = game;
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		game.getSoundManager().stopGameMusic();
		gd = new GameData();
		gd = Save.load();
		ld = new levelDecider();
		ld = Save.loadLevelDecider();
		game.st = State.run;
		Save.delete1();
		stage = new Stage();
		s = gd.getTempscore();
		table = new Table(game.skin);
		table.setBackground("uibackground");
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		ima = new Image(game.skin.getRegion("gameoverimage"));
		table.add(ima).size(0.8334f*game.width,0.28409f*game.height).row();
		table.row();
		score = new Label(String.valueOf(s), game.skin);
		score.scaleBy((game.height / game.width) + 1);
		table.add(score).padBottom(75).center().row();
		table.row();
		Table table1 = new Table(game.skin);
		table1.setBounds(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight() / 4);
		button = new TextButton("", game.skin, "restart");
		button.addListener(new ClickListener() {
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
						Actions.moveBy(0, stage.getHeight(), .5f),
						Actions.run(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								Gdx.input.setInputProcessor(null);
								game.setScreen(game.second);
							}
						})));

			}
		});
		table1.add(button).size(0.2f*game.height, 0.16f*game.height).left();
		button4 = new TextButton("Share", game.skin, "sharebutton");
		button4.addListener(new ClickListener() {
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
				game.mygamecallback.onStartActivityA();

			}
		});
		table1.add(button4).size(0.2f*game.height, 0.16f*game.height)
				.center();
		button1 = new TextButton("Menu", game.skin);
		button1.addListener(new ClickListener() {
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
						Actions.moveBy(0, stage.getHeight(), .5f),
						Actions.run(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								Gdx.input.setInputProcessor(null);
								
								if(ld.getCompletedLevels()==1)
									  game.setScreen(game.first);
									else
									  game.setScreen(game.startscreen);
							}
						})));
			}
		});
		table1.add(button1).size(0.2f*game.height, 0.16f*game.height)
				.right();
		table1.row();
		table.add(table1).row();
		table.row();
		table.setFillParent(true);
		stage.addActor(table);
		stage.addAction(Actions.sequence(Actions.moveTo(0, stage.getHeight()),
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
