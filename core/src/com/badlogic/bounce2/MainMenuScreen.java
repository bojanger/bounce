package com.badlogic.bounce2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MainMenuScreen extends Stage implements Screen {
    final Bounce2 game;
    OrthographicCamera camera;
    private Texture background;

    public MainMenuScreen(final Bounce2 gam) {
        game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.width, game.height);

        background = new Texture(Gdx.files.internal("menu-background.jpg"));

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(background, 0,0, game.width, game.height);
        game.font.draw(game.batch, "Welcome to Project Bounce", 50, 940);
        game.font.draw(game.batch, "Tap anywhere to begin", 50, 1000);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}