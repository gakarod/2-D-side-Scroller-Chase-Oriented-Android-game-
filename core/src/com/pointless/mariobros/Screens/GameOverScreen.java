package com.pointless.mariobros.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by brentaureli on 10/8/15.
 */
public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Texture bgTexture;
    BitmapFont font ;
    private Game game;
    com.pointless.mariobros.Tools.Adhandler adhandler ;

    public GameOverScreen(Game game , Integer Time , com.pointless.mariobros.Tools.Adhandler adhandler){
        this.game = game;
        this.adhandler = adhandler;
        if(adhandler != null) {
                adhandler.showInterstitialAd(new Runnable() {

                    public void run() {
                        System.out.println("Interstitial app closed");
                    }
                });
            } else {
                System.out.println("Interstitial ad not (yet) loaded");
            };
        viewport = new FitViewport(com.pointless.mariobros.MarioBros.V_WIDTH, com.pointless.mariobros.MarioBros.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((com.pointless.mariobros.MarioBros) game).batch);
        bgTexture = new Texture(Gdx.files.internal("gamelost.png"));
        Image bgImage = new Image(bgTexture);
        font = new BitmapFont(Gdx.files.internal("jelly.fnt"));
        Label.LabelStyle label1Style = new Label.LabelStyle();
        label1Style.font = font ;


        String scoreAsString = Integer.toString(Time);
        Label timeLabel = new Label("SCORE     " + scoreAsString, label1Style) ;
        timeLabel.setPosition(com.pointless.mariobros.MarioBros.V_WIDTH/3,(3* com.pointless.mariobros.MarioBros.V_HEIGHT)/5, Align.center);


        stage.addActor(bgImage);
        stage.addActor(timeLabel);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()) {
            game.setScreen(new PlayScreen( game, adhandler));
            dispose();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
