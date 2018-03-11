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
import com.pointless.mariobros.MarioBros;
import com.pointless.mariobros.Tools.Adhandler;

/**
 * Created by Vaibhav Anand on 29-11-2017.
 */
public class CongratsScreen implements Screen{
    private Viewport viewport;
    private Stage stage;
    private Texture bgTexture;
    BitmapFont font ;
    private Game game;
    Adhandler adhandler;

    public CongratsScreen(Game game , Integer Time , Adhandler adhandler){
        this.game = game;
        this.adhandler = adhandler ;
        if(adhandler != null) {
                adhandler.showInterstitialAd(new Runnable() {

                    public void run() {
                        System.out.println("Interstitial app closed");
                    }
                });
            } else {
                System.out.println("Interstitial ad not (yet) loaded");
            }
        viewport = new FitViewport(MarioBros.V_WIDTH, MarioBros.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((MarioBros) game).batch);
        bgTexture = new Texture(Gdx.files.internal("gamewon.png"));
        Image bgImage = new Image(bgTexture);
        font = new BitmapFont(Gdx.files.internal("jelly.fnt"));
        Label.LabelStyle label1Style = new Label.LabelStyle();
        label1Style.font = font ;

        String scoreAsString = Integer.toString(Time);
        stage.addActor(bgImage);

        Label timeLabel = new Label("SCORE  " + scoreAsString, label1Style) ;
        timeLabel.setPosition(MarioBros.V_WIDTH/2,(2*MarioBros.V_HEIGHT)/3, Align.center);
        stage.addActor(timeLabel);

    }

    @Override
    public void show() {
       // Image bgImage = new Image(bgTexture);
     //   stage.addActor(bgImage);
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()) {
            game.setScreen(new PlayScreen( game , adhandler));
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


