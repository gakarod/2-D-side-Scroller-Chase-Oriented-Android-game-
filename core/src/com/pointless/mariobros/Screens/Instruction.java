package com.pointless.mariobros.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;


/**
 * Created by Vaibhav Anand on 16-12-2017.
 */

public class Instruction extends ScreenAdapter{
    private static final float WORLD_WIDTH = 480;
    private static final float WORLD_HEIGHT = 320;
    private Stage stage;

    private Texture bgTexture;
    private Texture bUpTexture;
    private Texture bDnTexture;
    private Texture titleTexture;
    com.pointless.mariobros.Tools.Adhandler handler ;
    private final Game game;

    public Instruction(Game game , com.pointless.mariobros.Tools.Adhandler handler) {
        this.game = game;
        this.handler = handler ;
    }


    public void show(){
        stage = new Stage(new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        bgTexture = new Texture(Gdx.files.internal("inst.png"));
        Image bgImage = new Image(bgTexture);
        stage.addActor(bgImage);
        bUpTexture = new Texture(Gdx.files.internal("play.png"));
        bDnTexture = new Texture(Gdx.files.internal("playPress.png"));
        ImageButton play = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(bUpTexture)),
                new TextureRegionDrawable(new TextureRegion(bDnTexture))
        );

        // Add event listener to this button
        play.addListener(new ActorGestureListener(){
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                game.setScreen(new PlayScreen(game,handler));
                dispose();
            }
        });


        play.setPosition(WORLD_WIDTH/2,WORLD_HEIGHT/6, Align.center);
        stage.addActor(play);


    }



    public void resize(int w, int h){
        stage.getViewport().update(w,h,true);
    }

    public void render(float delta){
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        bDnTexture.dispose();
        bDnTexture.dispose();
        bUpTexture.dispose();
    }
}
