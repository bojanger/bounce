package com.badlogic.bounce2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Toan on 11/19/2016.
 */
public class GameScreen extends Stage implements Screen {
    final Bounce2 game;

    Music bgMusic;
    Sound bounceSound;
    Texture ballTexture;
    Rectangle ball;
    Texture barTex;
    Rectangle bar;
    Texture backgroundTexture;
    OrthographicCamera camera;

    public GameScreen(Bounce2 gam) {
        this.game = gam;

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("Datsik - Just Saiyan'.mp3"));
        bgMusic.setLooping(true);
        bounceSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));

        ballTexture = new Texture(Gdx.files.internal("ball_128_102.png"));

        barTex = new Texture(Gdx.files.internal("ingame-gap-bar.png"));

        bar = new Rectangle();
        bar.y = game.height/2;
        bar.width = 1080;
        bar.height = 20;

        backgroundTexture = new Texture(Gdx.files.internal("playstate-hud.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.width, game.height);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, game.width, game.height);
        game.batch.draw(barTex, bar.x, bar.y);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bar.x = touchPos.x - 540;
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        bgMusic.play();
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
        backgroundTexture.dispose();
        barTex.dispose();
        bgMusic.dispose();
        bounceSound.dispose();
        ballTexture.dispose();
    }

}
