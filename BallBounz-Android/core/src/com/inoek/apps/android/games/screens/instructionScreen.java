package com.inoek.apps.android.games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.inoek.apps.android.games.BallBounzAndroid;
import com.inoek.apps.android.games.Save;
import com.inoek.apps.android.games.data.levelDecider;

public class instructionScreen implements Screen {

	BallBounzAndroid game;
	Stage stage;
	Label description;
	Table parentTable;
	String desc;
	TextButton button,video;
	levelDecider ld;
	ScrollPane pane;
	
	public instructionScreen(BallBounzAndroid game) {
		this.game = game;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		stage = new Stage();
		ld = new levelDecider();
		ld = Save.loadLevelDecider();
		desc = "1. There are two movements in game , bat and ball.\n"
				+

				"2. Three hit positions , 'Left- L' 'Right- R' and 'Middle -M'\n"
				+

				"3. Ball movements are random ,for eg. left to middle or right to left.\n"
				+

				"4. Bat can have any one of three position (L,R,M) based on gamer touch.\n"
				+

				"5. Touch can be once if the ball returns in same position or touch should be twice, first touch for bat position another touch to hit the ball.\n"
				+

				"6. If there are three continous touch gamer will loose the game.\n"
				+

				"7. If the target score is not reached within the time bound the gamer will loose the game.\n"
				+

				"8. If the touch is misplaced the gamer will loose the game.\n"
				+

				"9. Always try to touch the ball below the tip of the bat to fetch maximum score.\n"
				+

				"10. Score is lowest for middle position touch by bat.\n"
				+

				"11. Score + Score for top touch by bat \n"
				+

				"12. Score + 2 times Score for Below tip touch by bat \n"
				+

				"13. There is time bound to complete each level.\n"
				+

				"14. The faster completion of target score, lesser will be the target score for next level completion.\n"
				+

				"15. The number of levels is 25 for now\n\n\n" +

				"For Video description click ";
		parentTable = new Table(game.skin);
		parentTable.setBackground("uibackground");
		parentTable.setBounds(0, 0, game.width, game.height);
		description = new Label(desc, game.skin);
		description.setBounds(0, 0, game.width, game.height);
		description.setWrap(true);
		description.setAlignment(Align.topLeft);
		pane = new ScrollPane(description);
		parentTable.add(pane).size(game.width, game.height/2).top().row();
		video = new TextButton("", game.skin, "video");
		video.addListener(new ClickListener() {
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
						Gdx.input.setInputProcessor(null);
						Gdx.net.openURI("https://www.youtube.com/watch?v=xcPGrynmYig");
			}

		});
		parentTable.add(video).size(0.5729167f * game.width,0.146806f * game.height).row();
		button = new TextButton("", game.skin, "backbutton");
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
						Actions.moveBy(stage.getWidth(), 0, .5f),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								Gdx.input.setInputProcessor(null);
								if (ld.getCompletedLevels() == 1)
									game.setScreen(game.first);
								else
									game.setScreen(game.startscreen);
							}
						})));

			}

		});
		parentTable.add(button).size(0.5729167f * game.width,0.146806f * game.height).row();
		stage.addActor(parentTable);
		stage.addAction(Actions.sequence(Actions.moveTo(stage.getWidth(), 0),
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
				game.width, game.height);
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
