package com.badlogic.bounce2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Bounce2 extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public int width = 1080;
	public int height = 1920;
	public int fontSize = 72;

	@Override
	public void create () {
		batch = new SpriteBatch();
		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("gameFont.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = fontSize;
		font = fontGen.generateFont(parameter);
		fontGen.dispose();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}

}
