package com.badlogic.bounce2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Random;

/**
 * Created by Toan on 11/19/2016.
 */
public class GameScreen extends Stage implements Screen {
    final Bounce2 game;

    private static String[] musicPlaylist = {"Datsik - Just Saiyan'.mp3", "Bassnectar - Level Up (feat. Macntaj).mp3", "Bird Peterson - Maybe.mp3", "Childish Gambino - 3005.mp3", "Flume - Pika.mp3", "Lido - Citi Bike.mp3", "Man Man - Harpoon Fever (Queequeg's Play)", "OVERWERK - Toccata.mp3", "Peking Duk - I Love to Rap (What So Not Remix).mp3", "Radiohead - Backdrifts (Honeymoon Is Over).mp3", "Ratatat - Cream on Chrome.mp3", "STRFKR - Sazed.mp3", "What So Not - Gemini feat. George Maple.mp3"};

    World world;
    Random rand;
    String currentSong;
    Music bgMusic;
    Sound bounceSound;
    Texture ballTexture;
    Rectangle ball;
    Texture barTex;
    Rectangle bar;
    Texture backgroundTexture;
    OrthographicCamera camera;

    Box2DDebugRenderer debugRenderer;

    Body body;
    Fixture ballFixture;
    Body groundBody;

    public GameScreen(Bounce2 gam) {
        this.game = gam;

        world = new World(new Vector2(0, -300), true);

        rand = new Random();
        currentSong = musicPlaylist[rand.nextInt(musicPlaylist.length)];
        bgMusic = Gdx.audio.newMusic(Gdx.files.internal(currentSong));
        bgMusic.setLooping(false);
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

        debugRenderer = new Box2DDebugRenderer();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(500, 1000);

        body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(250f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.05f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 60f;

        ballFixture = body.createFixture(fixtureDef);
        circle.dispose();

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(0, 200));

        // Create a body from the definition and add it to the world
        Body groundBody = world.createBody(groundBodyDef);

        // Create a polygon shape
        PolygonShape groundBox = new PolygonShape();
        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(camera.viewportWidth, 100.0f);
        // Create a fixture from our polygon shape and add it to our ground body
        groundBody.createFixture(groundBox, 0.0f);
        // Clean up after ourselves
        groundBox.dispose();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        if (!bgMusic.isPlaying()) {
            bgMusic.dispose();
            currentSong = musicPlaylist[rand.nextInt(musicPlaylist.length)];
            bgMusic = Gdx.audio.newMusic(Gdx.files.internal(currentSong));
            bgMusic.setLooping(false);
            bgMusic.play();
        }

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        //game.batch.draw(backgroundTexture, 0, 0, game.width, game.height);
        game.smallFont.draw(game.batch, ("Music:  " + currentSong.substring(0,currentSong.length()-4)), 0, 1900);
        game.batch.draw(barTex, bar.x, bar.y);
        game.batch.draw(ballTexture, body.getLocalCenter().x, body.getLocalCenter().y);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bar.x = touchPos.x - 540;
        }

        body.applyForceToCenter(0.0f, -1.0f*rand.nextFloat(), true);

        debugRenderer.render(world, camera.combined);
        world.step(1/45f, 6, 2);
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
