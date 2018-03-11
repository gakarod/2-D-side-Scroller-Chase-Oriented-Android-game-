package com.pointless.mariobros.Scenes;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input;

import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import com.badlogic.gdx.scenes.scene2d.InputListener;

import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Vaibhav Anand on 14-12-2017.
 */
public class Controller {Viewport viewport;

    Stage stage;

    boolean upPressed, downPressed, leftPressed, rightPressed;

    OrthographicCamera cam;



    public Controller(SpriteBatch sb){

        cam = new OrthographicCamera();

        viewport = new FitViewport(800, 480, cam);

        stage = new Stage(viewport, sb);



        stage.addListener(new InputListener(){



            @Override

            public boolean keyDown(InputEvent event, int keycode) {

                switch(keycode){

                    case Input.Keys.UP:

                        upPressed = true;

                        break;

                    case Input.Keys.DOWN:

                        downPressed = true;

                        break;

                    case Input.Keys.LEFT:

                        leftPressed = true;

                        break;

                    case Input.Keys.RIGHT:

                        rightPressed = true;

                        break;

                }

                return true;

            }



            @Override

            public boolean keyUp(InputEvent event, int keycode) {

                switch(keycode){

                    case Input.Keys.UP:

                        upPressed = false;

                        break;

                    case Input.Keys.DOWN:

                        downPressed = false;

                        break;

                    case Input.Keys.LEFT:

                        leftPressed = false;

                        break;

                    case Input.Keys.RIGHT:

                        rightPressed = false;

                        break;

                }

                return true;

            }

        });



        Gdx.input.setInputProcessor(stage);



        Table table = new Table();

        table.left().bottom();



        Image upImg = new Image(new Texture("flatDark25.png"));
        Image upImgs = new Image(new Texture("flatDark25.png"));

        upImg.setSize(50, 50);
        upImgs.setSize(50, 50);

        upImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                upPressed = true;
                return true;
            }

            @Override

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                upPressed = false;

            }

        });

        upImgs.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                upPressed = true;
                return true;
            }

            @Override

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                upPressed = false;

            }

        });



        Image downImg = new Image(new Texture("shoot.png"));

        downImg.setSize(50, 50);

        downImg.addListener(new InputListener() {



            @Override

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                downPressed = true;

                return true;

            }





            @Override

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                downPressed = false;

            }

        });

        Image downImgs = new Image(new Texture("shoot.png"));

        downImgs.setSize(50, 50);

        downImgs.addListener(new InputListener() {



            @Override

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                downPressed = true;

                return true;

            }





            @Override

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                downPressed = false;

            }

        });



        Image rightImg = new Image(new Texture("flatDark24.png"));

        rightImg.setSize(50, 50);

        rightImg.addListener(new InputListener() {



            @Override

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                rightPressed = true;

                return true;

            }



            @Override

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                rightPressed = false;

            }

        });

        Image rightImgs = new Image(new Texture("flatDark24.png"));

        rightImgs.setSize(50, 50);

        rightImgs.addListener(new InputListener() {



            @Override

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                rightPressed = true;

                return true;

            }



            @Override

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                rightPressed = false;

            }

        });



        Image leftImg = new Image(new Texture("flatDark23.png"));

        leftImg.setSize(50, 50);

        leftImg.addListener(new InputListener() {



            @Override

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                leftPressed = true;

                return true;

            }



            @Override

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                leftPressed = false;

            }

        });

        Image leftImgs = new Image(new Texture("flatDark23.png"));

        leftImgs.setSize(50, 50);

        leftImgs.addListener(new InputListener() {



            @Override

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                leftPressed = true;

                return true;

            }



            @Override

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                leftPressed = false;

            }

        });


        table.add();

        table.add(upImg).size(upImg.getWidth(), upImg.getHeight());

        table.add();

        table.row().pad(5, 5, 5, 5);

        table.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());

        table.add();

        table.row().padBottom(5);

        table.add();

        table.add(downImg).size(downImg.getWidth(), downImg.getHeight());

        table.add();

upImgs.setPosition(7.5f*(com.pointless.mariobros.MarioBros.V_WIDTH)/5,4*(com.pointless.mariobros.MarioBros.V_HEIGHT)/9, Align.center);
        leftImgs.setPosition(7*(com.pointless.mariobros.MarioBros.V_WIDTH)/5,(com.pointless.mariobros.MarioBros.V_HEIGHT)/3, Align.center);
        rightImgs.setPosition(8*(com.pointless.mariobros.MarioBros.V_WIDTH)/5,(com.pointless.mariobros.MarioBros.V_HEIGHT)/3, Align.center);
        downImgs.setPosition(7.5f*(com.pointless.mariobros.MarioBros.V_WIDTH)/5,3*(com.pointless.mariobros.MarioBros.V_HEIGHT)/16, Align.center);


        stage.addActor(table);
        stage.addActor(upImgs);
   //     stage.addActor(leftImgs);
        stage.addActor(rightImgs);
        stage.addActor(downImgs);
    }



    public void draw(){

        stage.draw();

    }



    public boolean isUpPressed() {

        return upPressed;

    }



    public boolean isDownPressed() {

        return downPressed;

    }



    public boolean isLeftPressed() {

        return leftPressed;

    }



    public boolean isRightPressed() {

        return rightPressed;

    }



    public void resize(int width, int height){

        viewport.update(width, height);

    }

}
